package com.tencent.smtt.webkit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webview.chromium.tencent.TencentWebViewDatabaseAdapter;
import android.webview.chromium.tencent.TencentWebViewDatabaseAdapter.AdInfo;
import android.webview.chromium.tencent.TencentWebViewDatabaseAdapter.AdStatus;
import android.webview.chromium.tencent.TencentWebViewDatabaseAdapter.Values;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.Md5Utils;
import com.tencent.smtt.livelog.LiveLog;
import com.tencent.smtt.net.m;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.UsedByReflection;

@JNINamespace("tencent")
public class AdInfoManager
{
  private static Hashtable<String, TencentWebViewDatabaseAdapter.AdStatus> AdInfoQuickCheckTable = new Hashtable();
  public static final byte CMD_CLEAR = 2;
  public static final byte CMD_TOUCH = 1;
  public static final int DIRECT_ADBLOCK_SWITCHER_CLOSE_BUT_WHITELIST = 1;
  public static final int DIRECT_ADBLOCK_SWITCHER_DEFINITELY_CLOSE = 0;
  public static final int DIRECT_ADBLOCK_SWITCHER_OPEN_ENTIRETY = 3;
  public static final int DIRECT_ADBLOCK_SWITCHER_OPEN_ONLY_HTTP = 2;
  public static int DIRECT_ADBLOCK_SWITCHER_STATUS = 0;
  public static int DIRECT_ADBLOCK_SWITCHER_STATUS_FOR_MINIQB = 0;
  private static final String FILE_SAFJ = "safj.inf";
  private static String TAG;
  private static long TouchTypeCheckInterval = 1800000L;
  private static boolean adBlockSourceInitating = false;
  public static String adFilterString;
  private static String currentFileMD5 = "";
  private static final boolean enableLog = false;
  private static String filterScriptTemplate;
  private static boolean initating;
  private static boolean initializeException;
  private static boolean initialized = false;
  private static boolean initializedAdBlockSourceFiles;
  private static boolean initializedSmartFiles;
  private static boolean initializedSubsetAdRuleFiles;
  private static ArrayList novelSiteList;
  private static boolean subsetAdRuleInitating;
  private Hashtable<String, TencentWebViewDatabaseAdapter.AdInfo> mAdInfoMap = new Hashtable();
  private Context mAppContext = null;
  protected TencentWebViewDatabaseAdapter mDataBase = null;
  private long mLastUpdate;
  private SyncWorkerThread mSyncWorker = null;
  
  static
  {
    initializeException = false;
    initializedSmartFiles = false;
    initializedSubsetAdRuleFiles = false;
    initializedAdBlockSourceFiles = false;
    TAG = "ADBLOCK";
    DIRECT_ADBLOCK_SWITCHER_STATUS = -1;
    DIRECT_ADBLOCK_SWITCHER_STATUS_FOR_MINIQB = -1;
    initating = false;
    adBlockSourceInitating = false;
    subsetAdRuleInitating = false;
    filterScriptTemplate = null;
    adFilterString = "";
    novelSiteList = new ArrayList();
  }
  
  public static void adblockLog(String paramString) {}
  
  @CalledByNative
  @UsedByReflection("X5CoreInit.java")
  public static AdInfoManager getInstance()
  {
    try
    {
      AdInfoManager localAdInfoManager = a.a;
      return localAdInfoManager;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  private static native String nativeCheckIfAds(String paramString);
  
  private static native int nativeInitAdBlockSourceFiles(String paramString1, String paramString2);
  
  private static native int nativeInitFiles(String paramString);
  
  private static native int nativeInitSubsetAdRuleFiles(String paramString1, String paramString2);
  
  @CalledByNative
  public boolean ShouldSmartAdFilterScriptRunInIFrame(String paramString)
  {
    if ((WebSettingsExtension.getRemoveAds()) && (paramString != null) && (e.a().n()))
    {
      String str = adFilterString;
      if ((str != null) && (str.length() > 0) && ((SmttServiceProxy.getInstance().getIsAdFilterEnhance()) || (!SmttServiceProxy.getInstance().shouldNotExecuteSmartAdFilter(paramString))) && (getInstance().getAdFeatureRules(paramString) != null) && (getInstance().getAdFeatureRules(paramString).startsWith("1"))) {
        return true;
      }
    }
    return false;
  }
  
  @JavascriptInterface
  public String checkIfAds(String paramString)
  {
    if (initializeException) {
      return "";
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("@JavascriptInterface checkIfAds feature=");
    localStringBuilder.append(paramString);
    adblockLog(localStringBuilder.toString());
    paramString = nativeCheckIfAds(paramString);
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("@JavascriptInterface checkIfAds ret=");
    localStringBuilder.append(paramString);
    adblockLog(localStringBuilder.toString());
    return paramString;
  }
  
  public void clearAllAdInfo()
  {
    this.mAdInfoMap.clear();
    AdInfoQuickCheckTable.clear();
  }
  
  void deleteAdInfo(ArrayList<String> paramArrayList)
  {
    int i = 0;
    try
    {
      while (i < paramArrayList.size())
      {
        this.mAdInfoMap.remove(paramArrayList.get(i));
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("mAdInfoMap.remove host=");
        localStringBuilder.append((String)paramArrayList.get(i));
        adblockLog(localStringBuilder.toString());
        i += 1;
      }
      return;
    }
    finally
    {
      paramArrayList = finally;
      throw paramArrayList;
    }
  }
  
  String filterString(TencentWebViewDatabaseAdapter.AdInfo paramAdInfo)
  {
    try
    {
      if ((paramAdInfo.filterRulesMap != null) && (!paramAdInfo.filterRulesMap.isEmpty()))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        paramAdInfo = paramAdInfo.filterRulesMap.entrySet().iterator();
        while (paramAdInfo.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)paramAdInfo.next();
          String str = (String)localEntry.getKey();
          if (((TencentWebViewDatabaseAdapter.Values)localEntry.getValue()).dbmode != 2) {
            if (localStringBuilder.length() < 1)
            {
              localStringBuilder.append(str);
            }
            else
            {
              localStringBuilder.append(" ");
              localStringBuilder.append(str);
            }
          }
        }
        paramAdInfo = new StringBuilder();
        paramAdInfo.append("filterStringArray=");
        paramAdInfo.append(localStringBuilder.toString());
        adblockLog(paramAdInfo.toString());
        paramAdInfo = localStringBuilder.toString();
        return paramAdInfo;
      }
      return null;
    }
    finally {}
  }
  
  String[] filterStringArray(TencentWebViewDatabaseAdapter.AdInfo paramAdInfo)
  {
    try
    {
      if ((paramAdInfo.filterRulesMap != null) && (!paramAdInfo.filterRulesMap.isEmpty()))
      {
        ArrayList localArrayList = new ArrayList();
        paramAdInfo = paramAdInfo.filterRulesMap.entrySet().iterator();
        while (paramAdInfo.hasNext())
        {
          Map.Entry localEntry = (Map.Entry)paramAdInfo.next();
          String str = (String)localEntry.getKey();
          if (((TencentWebViewDatabaseAdapter.Values)localEntry.getValue()).dbmode != 2) {
            localArrayList.add(str);
          }
        }
        paramAdInfo = new StringBuilder();
        paramAdInfo.append("filterStringArray=");
        paramAdInfo.append(localArrayList.toString());
        adblockLog(paramAdInfo.toString());
        paramAdInfo = (String[])localArrayList.toArray(new String[0]);
        return paramAdInfo;
      }
      return null;
    }
    finally {}
  }
  
  public void flushLiveLog()
  {
    LiveLog.d("AdFilter", SmttServiceProxy.getInstance().getSmartAdLiveLog());
  }
  
  @JavascriptInterface
  public String getAdFeatureRules(String paramString)
  {
    if (initializeException) {
      return "";
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("@JavascriptInterface getAdFeatureRules host=");
    ((StringBuilder)localObject).append(paramString);
    adblockLog(((StringBuilder)localObject).toString());
    if (TextUtils.isEmpty(paramString)) {
      return "";
    }
    try
    {
      if (!AdInfoQuickCheckTable.containsKey(paramString)) {
        return "";
      }
      localObject = (TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(paramString);
      if (isEmpty((TencentWebViewDatabaseAdapter.AdInfo)localObject)) {
        return "";
      }
      if ((((TencentWebViewDatabaseAdapter.AdInfo)localObject).featureRulesStr != null) && (((TencentWebViewDatabaseAdapter.AdInfo)localObject).featureRulesStr.length() != 0))
      {
        if (((TencentWebViewDatabaseAdapter.AdInfo)localObject).featureRulesStatus.dbmode == 2) {
          return "";
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("getAdFeatureRules host=");
        localStringBuilder.append(paramString);
        localStringBuilder.append(" adinfo.featureRulesStr=");
        localStringBuilder.append(((TencentWebViewDatabaseAdapter.AdInfo)localObject).featureRulesStr);
        adblockLog(localStringBuilder.toString());
        return ((TencentWebViewDatabaseAdapter.AdInfo)localObject).featureRulesStr;
      }
      return "";
    }
    finally {}
  }
  
  @CalledByNative
  public String getAdFilterScriptArguments(String paramString, boolean paramBoolean)
  {
    if (initializeException) {
      return null;
    }
    paramString = TencentURLUtil.getHost(paramString);
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getAdFilterScriptArguments host=");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(" isPrefetchPage=");
    ((StringBuilder)localObject).append(paramBoolean);
    adblockLog(((StringBuilder)localObject).toString());
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    try
    {
      if (!AdInfoQuickCheckTable.containsKey(paramString)) {
        return null;
      }
      localObject = (TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(paramString);
      if (isEmpty((TencentWebViewDatabaseAdapter.AdInfo)localObject)) {
        return null;
      }
      if (((TencentWebViewDatabaseAdapter.AdInfo)localObject).scriptArguments != null)
      {
        if (((TencentWebViewDatabaseAdapter.AdInfo)localObject).scriptArguments.length() == 0) {
          return null;
        }
        if (((TencentWebViewDatabaseAdapter.AdInfo)localObject).ScriptArgumentsStatus.dbmode == 2) {
          return null;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("getAdFilterScriptArguments host=");
        localStringBuilder.append(paramString);
        localStringBuilder.append(" adinfo.scriptArguments=");
        localStringBuilder.append(((TencentWebViewDatabaseAdapter.AdInfo)localObject).scriptArguments);
        adblockLog(localStringBuilder.toString());
        return ((TencentWebViewDatabaseAdapter.AdInfo)localObject).scriptArguments;
      }
      return null;
    }
    finally {}
  }
  
  @CalledByNative
  public String getAdFilterScriptTemplate()
  {
    if (initializeException) {
      return null;
    }
    if (filterScriptTemplate == null)
    {
      filterScriptTemplate = SmttServiceProxy.getInstance().getJsTemplate();
      String str = filterScriptTemplate;
      if ((str != null) && (str.length() > 0)) {
        return filterScriptTemplate;
      }
    }
    return "";
  }
  
  @CalledByNative
  public String getAdFilterString()
  {
    return adFilterString.toString();
  }
  
  @CalledByNative
  public String getAdInfoForFilterRules(String paramString, boolean paramBoolean)
  {
    if (initializeException) {
      return null;
    }
    paramString = TencentURLUtil.getHost(paramString);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getAdInfoForFilterRules host=");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" isPrefetchPage=");
    localStringBuilder.append(paramBoolean);
    adblockLog(localStringBuilder.toString());
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    preReadAdInfoFromDB(paramString);
    try
    {
      if (!AdInfoQuickCheckTable.containsKey(paramString)) {
        return null;
      }
      paramString = (TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(paramString);
      if (isEmpty(paramString)) {
        return null;
      }
      paramString = filterString(paramString);
      if ((paramString != null) && (paramString.length() > 0)) {
        return paramString;
      }
      return null;
    }
    finally {}
  }
  
  @CalledByNative
  public String getAdInfoForHiddenRules(String paramString, boolean paramBoolean)
  {
    if (initializeException) {
      return null;
    }
    paramString = TencentURLUtil.getHost(paramString);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getAdInfoForHiddenRules host=");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" isPrefetchPage=");
    localStringBuilder.append(paramBoolean);
    adblockLog(localStringBuilder.toString());
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    try
    {
      if (!AdInfoQuickCheckTable.containsKey(paramString)) {
        return null;
      }
      paramString = (TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(paramString);
      if (isEmpty(paramString)) {
        return null;
      }
      paramString = hiddenString(paramString);
      if ((paramString != null) && (paramString.length() > 0)) {
        return paramString;
      }
      return null;
    }
    finally {}
  }
  
  @CalledByNative
  public boolean getAdInfoForThirdParty(String paramString)
  {
    if (initializeException) {
      return false;
    }
    paramString = TencentURLUtil.getHost(paramString);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getAdInfoForThirdParty host=");
    localStringBuilder.append(paramString);
    adblockLog(localStringBuilder.toString());
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    preReadAdInfoFromDB(paramString);
    try
    {
      if (!AdInfoQuickCheckTable.containsKey(paramString)) {
        return false;
      }
      paramString = (TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(paramString);
      if (isEmpty(paramString)) {
        return false;
      }
      return paramString.useThirdPartyAdRules;
    }
    finally {}
  }
  
  TencentWebViewDatabaseAdapter.AdInfo getAdInfoFromDomain(String paramString)
  {
    if (initializeException) {
      return null;
    }
    TencentWebViewDatabaseAdapter localTencentWebViewDatabaseAdapter = this.mDataBase;
    if (localTencentWebViewDatabaseAdapter == null) {
      return null;
    }
    return localTencentWebViewDatabaseAdapter.getAdInfoByDomain(paramString);
  }
  
  @CalledByNative
  public String getAdInfoRuleUrl()
  {
    if (initializeException) {
      return "";
    }
    return SmttServiceProxy.getInstance().getAdBlockUrl();
  }
  
  @CalledByNative
  public boolean getIsUseThirdPartyAdRules()
  {
    if (initializeException) {
      return false;
    }
    return SmttServiceProxy.getInstance().getIsUseThirdPartyAdRules();
  }
  
  @CalledByNative
  public int getProxyStrategyInDirectLink(String paramString)
  {
    paramString = TencentURLUtil.getHost(paramString);
    if (TextUtils.isEmpty(paramString)) {
      return -1;
    }
    paramString = (TencentWebViewDatabaseAdapter.AdStatus)AdInfoQuickCheckTable.get(paramString);
    if ((paramString != null) && (paramString.isTouch)) {
      return paramString.directStrategy;
    }
    return -1;
  }
  
  @CalledByNative
  public int getRandomIdSwitch(String paramString)
  {
    paramString = TencentURLUtil.getHost(paramString);
    if (TextUtils.isEmpty(paramString)) {
      return 0;
    }
    paramString = (TencentWebViewDatabaseAdapter.AdStatus)AdInfoQuickCheckTable.get(paramString);
    if ((paramString != null) && (paramString.isTouch)) {
      return paramString.randomIdSwitch;
    }
    return 0;
  }
  
  ArrayList<TencentWebViewDatabaseAdapter.AdInfo> getUpdatedAdInfoSince()
  {
    try
    {
      ArrayList localArrayList = new ArrayList();
      Enumeration localEnumeration = this.mAdInfoMap.keys();
      while (localEnumeration.hasMoreElements()) {
        localArrayList.add((TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(localEnumeration.nextElement()));
      }
      return localArrayList;
    }
    finally {}
  }
  
  public List<String> getUserSelectAdInfoDomain()
  {
    for (;;)
    {
      ArrayList localArrayList;
      Iterator localIterator;
      try
      {
        adblockLog("getUserSelectAdInfoDomain ###");
        localArrayList = new ArrayList();
      }
      finally {}
      try
      {
        localIterator = this.mAdInfoMap.keySet().iterator();
        if (localIterator.hasNext())
        {
          String str = (String)localIterator.next();
          if (((TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(str)).userSelectHiddenRulesMap.size() == 0) {
            continue;
          }
          localArrayList.add(str);
        }
      }
      catch (Exception localException) {}
    }
    return localArrayList;
  }
  
  @CalledByNative
  public String getUserSelectAdInfoForHiddenRulesString(String paramString, boolean paramBoolean)
  {
    if (!WebSettingsExtension.getRemoveAds()) {
      return null;
    }
    if (initializeException) {
      return null;
    }
    paramString = TencentURLUtil.getHost(paramString);
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getUserSelectAdInfoForHiddenRulesString host=");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(" isPrefetchPage=");
    ((StringBuilder)localObject).append(paramBoolean);
    adblockLog(((StringBuilder)localObject).toString());
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    try
    {
      if (!AdInfoQuickCheckTable.containsKey(paramString))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("getUserSelectAdInfoForHiddenRulesString ## host=");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(" isPrefetchPage=");
        ((StringBuilder)localObject).append(paramBoolean);
        adblockLog(((StringBuilder)localObject).toString());
        return null;
      }
      localObject = (TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(paramString);
      if (isUserSelectedHiddenRuleEmpty((TencentWebViewDatabaseAdapter.AdInfo)localObject))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("getUserSelectAdInfoForHiddenRulesString #### host=");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append(" isPrefetchPage=");
        ((StringBuilder)localObject).append(paramBoolean);
        adblockLog(((StringBuilder)localObject).toString());
        return null;
      }
      paramString = getUserSelectHiddenRules((TencentWebViewDatabaseAdapter.AdInfo)localObject);
      if ((paramString != null) && (paramString.length() > 0)) {
        return paramString;
      }
      return null;
    }
    finally {}
  }
  
  public String getUserSelectHiddenRules(TencentWebViewDatabaseAdapter.AdInfo paramAdInfo)
  {
    if ((paramAdInfo.userSelectHiddenRulesMap != null) && (!paramAdInfo.userSelectHiddenRulesMap.isEmpty()))
    {
      String str = "";
      Iterator localIterator = paramAdInfo.userSelectHiddenRulesMap.entrySet().iterator();
      paramAdInfo = str;
      while (localIterator.hasNext())
      {
        Object localObject = (Map.Entry)localIterator.next();
        str = (String)((Map.Entry)localObject).getKey();
        if (((TencentWebViewDatabaseAdapter.Values)((Map.Entry)localObject).getValue()).dbmode != 2)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(paramAdInfo);
          ((StringBuilder)localObject).append(str);
          ((StringBuilder)localObject).append(",");
          paramAdInfo = ((StringBuilder)localObject).toString();
        }
      }
      if (paramAdInfo.length() >= 1) {
        return paramAdInfo.substring(0, paramAdInfo.length() - 1);
      }
      return paramAdInfo;
    }
    return null;
  }
  
  @CalledByNative
  public void handleAdBlockBackendCmd(String paramString1, String paramString2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("handleAdBlockBackendCmd url=");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(" resp=");
    ((StringBuilder)localObject).append(paramString2);
    adblockLog(((StringBuilder)localObject).toString());
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    paramString1 = TencentURLUtil.getHost(paramString1);
    if ((paramString2 != null) && (paramString2.length() > 0))
    {
      paramString2 = paramString2.split(";");
      int m = 0;
      int n = 0;
      int i1 = -1;
      int j = 0;
      int k = 0;
      while (m < paramString2.length)
      {
        localObject = paramString2[m];
        String str;
        if (((String)localObject).startsWith("adc="))
        {
          str = ((String)localObject).substring(4);
          if (str.length() < 1) {
            return;
          }
        }
        try
        {
          i = Integer.parseInt(str);
        }
        catch (Exception localException2)
        {
          int i;
          for (;;) {}
        }
        i = 0;
        n = i;
        if ((i & 0x2) == 2)
        {
          removeAdInfo(paramString1);
          n = i;
        }
        i = i1;
        if (((String)localObject).startsWith("ds="))
        {
          str = ((String)localObject).substring(3);
          if (str.length() < 1) {
            return;
          }
        }
        try
        {
          i = Integer.parseInt(str);
        }
        catch (Exception localException3)
        {
          for (;;) {}
        }
        i = -1;
        if (((String)localObject).startsWith("rd="))
        {
          str = ((String)localObject).substring(3);
          if (str.length() < 1) {
            return;
          }
        }
        try
        {
          j = Integer.parseInt(str);
        }
        catch (Exception localException4)
        {
          for (;;) {}
        }
        j = 0;
        if (((String)localObject).startsWith("fix="))
        {
          localObject = ((String)localObject).substring(4);
          if (((String)localObject).length() < 1) {
            return;
          }
        }
        try
        {
          k = Integer.parseInt((String)localObject);
        }
        catch (Exception localException1)
        {
          for (;;) {}
        }
        k = 0;
        m += 1;
        i1 = i;
      }
      if ((n & 0x1) == 1) {
        setAdInfoBackendType(paramString1, true, i1, j, k);
      }
    }
  }
  
  String hiddenString(TencentWebViewDatabaseAdapter.AdInfo paramAdInfo)
  {
    try
    {
      if ((paramAdInfo.hiddenRulesMap != null) && (!paramAdInfo.hiddenRulesMap.isEmpty()))
      {
        Object localObject1 = "";
        Iterator localIterator = paramAdInfo.hiddenRulesMap.entrySet().iterator();
        int i = 0;
        paramAdInfo = (TencentWebViewDatabaseAdapter.AdInfo)localObject1;
        while (localIterator.hasNext())
        {
          Object localObject2 = (Map.Entry)localIterator.next();
          localObject1 = (String)((Map.Entry)localObject2).getKey();
          if (((TencentWebViewDatabaseAdapter.Values)((Map.Entry)localObject2).getValue()).dbmode != 2)
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append(paramAdInfo);
            ((StringBuilder)localObject2).append((String)localObject1);
            ((StringBuilder)localObject2).append(",");
            paramAdInfo = ((StringBuilder)localObject2).toString();
            i += 1;
          }
        }
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("hiddenString=");
        ((StringBuilder)localObject1).append(paramAdInfo.toString());
        ((StringBuilder)localObject1).append(" num=");
        ((StringBuilder)localObject1).append(i);
        adblockLog(((StringBuilder)localObject1).toString());
        if (paramAdInfo.length() >= 1)
        {
          paramAdInfo = paramAdInfo.substring(0, paramAdInfo.length() - 1);
          return paramAdInfo;
        }
        return paramAdInfo;
      }
      return null;
    }
    finally {}
  }
  
  @CalledByNative
  public void init()
  {
    if ((!initialized) && (this.mAppContext != null)) {
      if (initializeException) {
        return;
      }
    }
    try
    {
      this.mSyncWorker = new SyncWorkerThread();
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      SyncWorkerThread localSyncWorkerThread;
      for (;;) {}
    }
    this.mSyncWorker = null;
    localSyncWorkerThread = this.mSyncWorker;
    if (localSyncWorkerThread != null) {
      localSyncWorkerThread.post(new Runnable()
      {
        public void run()
        {
          if ((AdInfoManager.this.mDataBase == null) && (AdInfoManager.this.mAppContext != null)) {}
          try
          {
            AdInfoManager.this.mDataBase = TencentWebViewDatabaseAdapter.getInstance(AdInfoManager.this.mAppContext);
          }
          catch (Exception localException)
          {
            List localList;
            Object localObject;
            int j;
            int i;
            for (;;) {}
          }
          AdInfoManager.this.mDataBase = null;
          AdInfoManager.access$102(true);
          return;
          if (AdInfoManager.this.mDataBase == null)
          {
            AdInfoManager.access$102(true);
            return;
          }
          localList = AdInfoManager.this.mDataBase.getAllAdInfoDomain();
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("init AdInfoManager rets=");
          j = 0;
          if (localList != null) {
            i = localList.size();
          } else {
            i = 0;
          }
          ((StringBuilder)localObject).append(i);
          AdInfoManager.adblockLog(((StringBuilder)localObject).toString());
          if (localList != null)
          {
            i = j;
            while (i < localList.size())
            {
              localObject = ((TencentWebViewDatabaseAdapter.AdStatus)localList.get(i)).domain;
              AdInfoManager.AdInfoQuickCheckTable.put(localObject, localList.get(i));
              TencentWebViewDatabaseAdapter.AdInfo localAdInfo = AdInfoManager.this.getAdInfoFromDomain((String)localObject);
              if (localAdInfo != null) {
                AdInfoManager.this.mAdInfoMap.put(localObject, localAdInfo);
              }
              i += 1;
            }
          }
          if (e.a().n())
          {
            AdInfoManager.this.initSmartAdFilterFiles();
            AdInfoManager.this.initSubsetAdRuleFiles();
            if (AdInfoManager.this.getIsUseThirdPartyAdRules()) {
              AdInfoManager.this.initAdBlockSourceFiles();
            }
          }
          AdInfoManager.access$402(true);
        }
      });
    }
    return;
  }
  
  public void initAdBlockSourceFiles()
  {
    if ((this.mAppContext != null) && (!initializeException))
    {
      if (initializedAdBlockSourceFiles) {
        return;
      }
      BrowserExecutorSupplier.postForDbTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          if (AdInfoManager.adBlockSourceInitating) {
            return;
          }
          AdInfoManager.access$1102(true);
          try
          {
            long l = System.currentTimeMillis();
            int i = AdInfoManager.nativeInitAdBlockSourceFiles(SmttServiceProxy.getInstance().getAdBlockSourceFilePath(), SmttServiceProxy.getInstance().getAdBlockSourceMD5Value());
            if (i == -2) {
              SmttServiceProxy.getInstance().resetAdBlockSourceMD5();
            }
            if (i == 1) {
              AdInfoManager.access$1302(true);
            }
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("initAdBlockSourceFiles cost=");
            localStringBuilder.append(System.currentTimeMillis() - l);
            localStringBuilder.append(" Thread=");
            localStringBuilder.append(Thread.currentThread().getName());
            localStringBuilder.append(" initializedAdBlockSourceFiles=");
            localStringBuilder.append(AdInfoManager.initializedAdBlockSourceFiles);
            X5Log.i("AdFilter", localStringBuilder.toString());
          }
          catch (Exception localException)
          {
            for (;;) {}
          }
          AdInfoManager.access$1102(false);
        }
      });
      return;
    }
  }
  
  int initAdJSFile()
  {
    LiveLog.d("AdFilter", "start initAdJSFile");
    Object localObject1;
    if (adFilterString.length() != 0)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("initAdJSFile adFilterString.length=");
      ((StringBuilder)localObject1).append(adFilterString.length());
      ((StringBuilder)localObject1).append(" return");
      LiveLog.d("AdFilter", ((StringBuilder)localObject1).toString());
      return 1;
    }
    try
    {
      localObject1 = new File(FileUtils.getCacheDir(this.mAppContext), "safj.inf");
      if (!((File)localObject1).exists())
      {
        LiveLog.d("AdFilter", "initAdJSFile file doesn't exist ");
        return 0;
      }
      if (((File)localObject1).length() > 1048576L)
      {
        LiveLog.d("AdFilter", "initAdJSFile file too long! ");
        return 0;
      }
      Object localObject2 = FileUtils.read((File)localObject1);
      if ((localObject2 != null) && (((ByteBuffer)localObject2).position() > 0))
      {
        adFilterString = new String(((ByteBuffer)localObject2).array(), 0, ((ByteBuffer)localObject2).position());
        currentFileMD5 = Md5Utils.getMD5(adFilterString);
        localObject2 = SmttServiceProxy.getInstance().getAdJsMD5Value();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("initAdJSFile currentFileMD5=");
        localStringBuilder.append(currentFileMD5);
        localStringBuilder.append(" getAdJsMD5Value=");
        localStringBuilder.append((String)localObject2);
        localStringBuilder.append(" adFilterString.length=");
        localStringBuilder.append(adFilterString.length());
        LiveLog.d("AdFilter", localStringBuilder.toString());
        if (!currentFileMD5.equalsIgnoreCase((String)localObject2))
        {
          ((File)localObject1).delete();
          LiveLog.d("AdFilter", "DELETE FILE safj.inf");
          currentFileMD5 = "";
          adFilterString = "";
          return -1;
        }
        return 1;
      }
      return 0;
    }
    catch (Exception localException) {}
    return 0;
  }
  
  @UsedByReflection("X5CoreInit.java")
  public void initAdManager(Context paramContext)
  {
    setContext(paramContext);
    paramContext = this.mAppContext;
    if ((paramContext != null) && ("com.tencent.mtt".equals(paramContext.getPackageName()))) {
      BrowserExecutorSupplier.postForDbTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          AdInfoManager.this.init();
        }
      });
    }
  }
  
  public void initSmartAdFilterFiles()
  {
    if ((this.mAppContext != null) && (!initializeException))
    {
      if (initializedSmartFiles) {
        return;
      }
      BrowserExecutorSupplier.postForDbTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          if (AdInfoManager.initating) {
            return;
          }
          AdInfoManager.access$502(true);
          try
          {
            l = System.currentTimeMillis();
            i = AdInfoManager.this.initAdJSFile();
            j = AdInfoManager.nativeInitFiles(SmttServiceProxy.getInstance().getModuleMD5Value());
            if ((i != -1) && (j != -1)) {
              break label69;
            }
            localObject = SmttServiceProxy.getInstance();
            if (i != -1) {
              break label170;
            }
            bool1 = true;
          }
          catch (Exception localException)
          {
            for (;;)
            {
              long l;
              int i;
              int j;
              Object localObject;
              boolean bool2;
              continue;
              boolean bool1 = false;
              if (j == -1) {
                bool2 = true;
              } else {
                bool2 = false;
              }
            }
          }
          ((SmttServiceProxy)localObject).resetJSAndModelMD5(bool1, bool2);
          label69:
          if ((i == 1) && (j == 1)) {
            AdInfoManager.access$702(true);
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("initSmartAdFilterFiles cost=");
          ((StringBuilder)localObject).append(System.currentTimeMillis() - l);
          ((StringBuilder)localObject).append(" Thread=");
          ((StringBuilder)localObject).append(Thread.currentThread().getName());
          ((StringBuilder)localObject).append(" initializedSmartFiles=");
          ((StringBuilder)localObject).append(AdInfoManager.initializedSmartFiles);
          LiveLog.d("AdFilter", ((StringBuilder)localObject).toString());
          AdInfoManager.access$502(false);
        }
      });
      return;
    }
  }
  
  public void initSubsetAdRuleFiles()
  {
    if ((this.mAppContext != null) && (!initializeException))
    {
      if (initializedSubsetAdRuleFiles) {
        return;
      }
      BrowserExecutorSupplier.postForDbTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          if (AdInfoManager.subsetAdRuleInitating) {
            return;
          }
          AdInfoManager.access$802(true);
          try
          {
            long l = System.currentTimeMillis();
            int i = AdInfoManager.nativeInitSubsetAdRuleFiles(SmttServiceProxy.getInstance().getSubsetAdRuleFilePath(), SmttServiceProxy.getInstance().getSubsetAdRuleMD5Value());
            if (i == -1) {
              SmttServiceProxy.getInstance().resetSubsetAdRuleMD5();
            }
            if (i == 1) {
              AdInfoManager.access$1002(true);
            }
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("initSmartAdFilterFiles cost=");
            localStringBuilder.append(System.currentTimeMillis() - l);
            localStringBuilder.append(" Thread=");
            localStringBuilder.append(Thread.currentThread().getName());
            localStringBuilder.append(" initializedSubsetAdRuleFiles=");
            localStringBuilder.append(AdInfoManager.initializedSubsetAdRuleFiles);
            X5Log.i("AdFilter", localStringBuilder.toString());
          }
          catch (Exception localException)
          {
            for (;;) {}
          }
          AdInfoManager.access$802(false);
        }
      });
      return;
    }
  }
  
  public boolean isDomainFeatureRulesEmpty(String paramString)
  {
    if (initializeException) {
      return true;
    }
    if (TextUtils.isEmpty(paramString)) {
      return true;
    }
    if (!AdInfoQuickCheckTable.containsKey(paramString)) {
      return true;
    }
    paramString = (TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(paramString);
    if (paramString != null) {
      return TextUtils.isEmpty(paramString.featureRulesStr);
    }
    return true;
  }
  
  boolean isEmpty(TencentWebViewDatabaseAdapter.AdInfo paramAdInfo)
  {
    boolean bool = true;
    if (paramAdInfo == null) {
      return true;
    }
    if (((paramAdInfo.hiddenRulesMap == null) || (paramAdInfo.hiddenRulesMap.isEmpty())) && ((paramAdInfo.filterRulesMap == null) || (paramAdInfo.filterRulesMap.isEmpty())) && ((paramAdInfo.scriptArguments == null) || (paramAdInfo.scriptArguments.length() == 0)))
    {
      if (paramAdInfo.featureRulesStr == null) {
        return bool;
      }
      if (paramAdInfo.featureRulesStr.length() == 0) {
        return true;
      }
    }
    bool = false;
    return bool;
  }
  
  public boolean isHostNameInAdblockBlackList(String paramString)
  {
    return SmttServiceProxy.getInstance().isHostNameInAdblockBlackList(paramString);
  }
  
  public boolean isHostNameInAdblockWhiteList(String paramString)
  {
    return SmttServiceProxy.getInstance().isHostNameInAdblockWhiteList(paramString);
  }
  
  @CalledByNative
  public boolean isQbMode()
  {
    return e.a().n();
  }
  
  boolean isUserSelectedHiddenRuleEmpty(TencentWebViewDatabaseAdapter.AdInfo paramAdInfo)
  {
    boolean bool = true;
    if (paramAdInfo == null) {
      return true;
    }
    if (paramAdInfo.userSelectHiddenRulesMap != null)
    {
      if (paramAdInfo.userSelectHiddenRulesMap.isEmpty()) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  void parseAdInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("parseAdInfo hiddenRulesStr=");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(" filterRulesStr=");
    ((StringBuilder)localObject).append(paramString3);
    ((StringBuilder)localObject).append(" featureRulesStr=");
    ((StringBuilder)localObject).append(paramString5);
    ((StringBuilder)localObject).append(" scriptArguments=");
    ((StringBuilder)localObject).append(paramString4);
    ((StringBuilder)localObject).append(" thread=");
    ((StringBuilder)localObject).append(Thread.currentThread().getName());
    adblockLog(((StringBuilder)localObject).toString());
    if (!AdInfoQuickCheckTable.containsKey(paramString1))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("AdInfoQuickCheckTable do not contains host=");
      ((StringBuilder)localObject).append(paramString1);
      adblockLog(((StringBuilder)localObject).toString());
      localObject = new TencentWebViewDatabaseAdapter.AdInfo();
      this.mAdInfoMap.put(paramString1, localObject);
      AdInfoQuickCheckTable.put(paramString1, new TencentWebViewDatabaseAdapter.AdStatus(paramString1, false, -1, 0, 0, 0L));
    }
    else if (this.mAdInfoMap.containsKey(paramString1))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("mAdInfoMap contains host=");
      ((StringBuilder)localObject).append(paramString1);
      adblockLog(((StringBuilder)localObject).toString());
      localObject = (TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(paramString1);
    }
    else
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("mAdInfoMap do not contains host=");
      ((StringBuilder)localObject).append(paramString1);
      adblockLog(((StringBuilder)localObject).toString());
      localObject = new TencentWebViewDatabaseAdapter.AdInfo();
      this.mAdInfoMap.put(paramString1, localObject);
    }
    if (localObject == null) {
      return;
    }
    ((TencentWebViewDatabaseAdapter.AdInfo)localObject).host = paramString1;
    int i;
    if ((paramString2 != null) && (paramString2.length() > 0))
    {
      paramString1 = paramString2.split(",");
      i = 0;
      while (i < paramString1.length)
      {
        if (paramString1[i].length() > 2)
        {
          if (paramString1[i].substring(2) != null) {
            ((TencentWebViewDatabaseAdapter.AdInfo)localObject).hiddenRulesMap.put(paramString1[i].substring(2), new TencentWebViewDatabaseAdapter.Values((byte)0));
          }
          paramString2 = new StringBuilder();
          paramString2.append("parseAdInfo  adinfo.hiddenRulesMap.put ");
          paramString2.append(paramString1[i].substring(2));
          adblockLog(paramString2.toString());
        }
        i += 1;
      }
    }
    if ((paramString3 != null) && (paramString3.length() > 0))
    {
      paramString1 = paramString3.split(" ");
      i = 0;
      while (i < paramString1.length)
      {
        if (paramString1[i].length() > 2)
        {
          ((TencentWebViewDatabaseAdapter.AdInfo)localObject).filterRulesMap.put(paramString1[i], new TencentWebViewDatabaseAdapter.Values((byte)0));
          paramString2 = new StringBuilder();
          paramString2.append("parseAdInfo  adinfo.filterRulesMap.put ");
          paramString2.append(paramString1[i]);
          adblockLog(paramString2.toString());
        }
        i += 1;
      }
    }
    if ((paramString5 != null) && (paramString5.length() > 0) && (!((TencentWebViewDatabaseAdapter.AdInfo)localObject).featureRulesStr.equalsIgnoreCase(paramString5)))
    {
      ((TencentWebViewDatabaseAdapter.AdInfo)localObject).featureRulesStr = paramString5;
      ((TencentWebViewDatabaseAdapter.AdInfo)localObject).featureRulesStatus = new TencentWebViewDatabaseAdapter.Values((byte)0);
      paramString1 = new StringBuilder();
      paramString1.append("parseAdInfo featureRulesStr=");
      paramString1.append(paramString5);
      adblockLog(paramString1.toString());
    }
    if ((paramString4 != null) && (paramString4.length() > 0))
    {
      ((TencentWebViewDatabaseAdapter.AdInfo)localObject).scriptArguments = paramString4;
      ((TencentWebViewDatabaseAdapter.AdInfo)localObject).ScriptArgumentsStatus = new TencentWebViewDatabaseAdapter.Values((byte)0);
      paramString1 = new StringBuilder();
      paramString1.append("parseAdInfo scriptArguments=");
      paramString1.append(paramString4);
      adblockLog(paramString1.toString());
    }
    if ((paramString6 != null) && (paramString6.equals("1")))
    {
      ((TencentWebViewDatabaseAdapter.AdInfo)localObject).useThirdPartyAdRules = true;
      return;
    }
    ((TencentWebViewDatabaseAdapter.AdInfo)localObject).useThirdPartyAdRules = false;
  }
  
  public void parseUserSelectAdInfo(final String paramString1, final String paramString2)
  {
    SyncWorkerThread localSyncWorkerThread = this.mSyncWorker;
    if (localSyncWorkerThread == null) {
      return;
    }
    localSyncWorkerThread.post(new Runnable()
    {
      public void run()
      {
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("  parseUserSelectAdInfo hiddenRulesStr=");
        ((StringBuilder)localObject1).append(paramString2);
        ((StringBuilder)localObject1).append(",  thread=");
        ((StringBuilder)localObject1).append(Thread.currentThread().getName());
        AdInfoManager.adblockLog(((StringBuilder)localObject1).toString());
        if (!AdInfoManager.AdInfoQuickCheckTable.containsKey(paramString1))
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("  AdInfoQuickCheckTable do not contains host=");
          ((StringBuilder)localObject1).append(paramString1);
          AdInfoManager.adblockLog(((StringBuilder)localObject1).toString());
          localObject1 = new TencentWebViewDatabaseAdapter.AdInfo();
          AdInfoManager.this.mAdInfoMap.put(paramString1, localObject1);
          localObject2 = AdInfoManager.AdInfoQuickCheckTable;
          String str = paramString1;
          ((Hashtable)localObject2).put(str, new TencentWebViewDatabaseAdapter.AdStatus(str, false, -1, 0, 0, 0L));
        }
        else if (AdInfoManager.this.mAdInfoMap.containsKey(paramString1))
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("  mAdInfoMap contains host=");
          ((StringBuilder)localObject1).append(paramString1);
          AdInfoManager.adblockLog(((StringBuilder)localObject1).toString());
          localObject1 = (TencentWebViewDatabaseAdapter.AdInfo)AdInfoManager.this.mAdInfoMap.get(paramString1);
        }
        else
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("  mAdInfoMap do not contains host=");
          ((StringBuilder)localObject1).append(paramString1);
          AdInfoManager.adblockLog(((StringBuilder)localObject1).toString());
          localObject1 = new TencentWebViewDatabaseAdapter.AdInfo();
          AdInfoManager.this.mAdInfoMap.put(paramString1, localObject1);
        }
        if (localObject1 == null) {
          return;
        }
        ((TencentWebViewDatabaseAdapter.AdInfo)localObject1).host = paramString1;
        ((TencentWebViewDatabaseAdapter.AdInfo)localObject1).userSelectHiddenRulesMap.put(paramString2, new TencentWebViewDatabaseAdapter.Values((byte)0));
        AdInfoManager.this.saveUserSelectAdInfoToDatabase((TencentWebViewDatabaseAdapter.AdInfo)localObject1);
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("  parseUserSelectAdInfo  size = ");
        ((StringBuilder)localObject2).append(((TencentWebViewDatabaseAdapter.AdInfo)localObject1).userSelectHiddenRulesMap.size());
        ((StringBuilder)localObject2).append(", map_size = ");
        ((StringBuilder)localObject2).append(AdInfoManager.this.mAdInfoMap.size());
        ((StringBuilder)localObject2).append(", content = ");
        ((StringBuilder)localObject2).append(paramString2);
        AdInfoManager.adblockLog(((StringBuilder)localObject2).toString());
      }
    });
  }
  
  void preReadAdInfoFromDB(String paramString)
  {
    try
    {
      boolean bool = initializeException;
      if (bool) {
        return;
      }
      if ((!TextUtils.isEmpty(paramString)) && (AdInfoQuickCheckTable.containsKey(paramString)) && (!this.mAdInfoMap.containsKey(paramString)))
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("preReadAdInfoFromDB host=");
        ((StringBuilder)localObject).append(paramString);
        adblockLog(((StringBuilder)localObject).toString());
        localObject = getAdInfoFromDomain(paramString);
        if (localObject == null) {
          return;
        }
        this.mAdInfoMap.put(paramString, localObject);
        return;
      }
      return;
    }
    finally {}
  }
  
  void removeAdInfo(String paramString)
  {
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("removeAdInfo host=");
      ((StringBuilder)localObject).append(paramString);
      adblockLog(((StringBuilder)localObject).toString());
      boolean bool = AdInfoQuickCheckTable.containsKey(paramString);
      if (!bool) {
        return;
      }
      paramString = (TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(paramString);
      if (paramString != null)
      {
        localObject = paramString.filterRulesMap.entrySet().iterator();
        while (((Iterator)localObject).hasNext()) {
          ((TencentWebViewDatabaseAdapter.Values)((Map.Entry)((Iterator)localObject).next()).getValue()).dbmode = 2;
        }
        localObject = paramString.hiddenRulesMap.entrySet().iterator();
        while (((Iterator)localObject).hasNext()) {
          ((TencentWebViewDatabaseAdapter.Values)((Map.Entry)((Iterator)localObject).next()).getValue()).dbmode = 2;
        }
        if (paramString.featureRulesStatus != null) {
          paramString.featureRulesStatus.dbmode = 2;
        }
        if (paramString.ScriptArgumentsStatus != null) {
          paramString.ScriptArgumentsStatus.dbmode = 2;
        }
      }
      return;
    }
    finally {}
  }
  
  public void removeUserSelectAdInfoByDomain(String paramString)
  {
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("removeAdInfo host=");
      ((StringBuilder)localObject).append(paramString);
      adblockLog(((StringBuilder)localObject).toString());
      boolean bool = initializeException;
      if (bool) {
        return;
      }
      bool = AdInfoQuickCheckTable.containsKey(paramString);
      if (!bool) {
        return;
      }
      localObject = (TencentWebViewDatabaseAdapter.AdInfo)this.mAdInfoMap.get(paramString);
      if (localObject != null)
      {
        Iterator localIterator = ((TencentWebViewDatabaseAdapter.AdInfo)localObject).userSelectHiddenRulesMap.entrySet().iterator();
        while (localIterator.hasNext()) {
          ((TencentWebViewDatabaseAdapter.Values)((Map.Entry)localIterator.next()).getValue()).dbmode = 2;
        }
        ((TencentWebViewDatabaseAdapter.AdInfo)localObject).host = paramString;
      }
      saveUserSelectAdInfoToDatabase((TencentWebViewDatabaseAdapter.AdInfo)localObject);
      return;
    }
    finally {}
  }
  
  @CalledByNative
  public void reportAdBlockWithThirdPartyRules(final String paramString1, final String paramString2)
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("adblock_main_host", paramString1);
        localHashMap.put("adblock_subresource_url", paramString2);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(System.currentTimeMillis());
        localStringBuilder.append("");
        localHashMap.put("adblock_time", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("reportAdBlockWithThirdPartyRules adblock_main_host = ");
        localStringBuilder.append(paramString1);
        localStringBuilder.append(", adblock_subresource_url = ");
        localStringBuilder.append(paramString2);
        AdInfoManager.adblockLog(localStringBuilder.toString());
        SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_QB_ADBLOCK_WITH_THIRDPARTY_RULES", localHashMap);
      }
    });
  }
  
  @CalledByNative
  public void reportUserHideAdInfo(final String paramString1, final String paramString2, final int paramInt)
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("hide_ad_url", paramString1);
        localHashMap.put("hide_ad_host", TencentURLUtil.getHost(paramString1));
        localHashMap.put("hide_ad_css_rule", paramString2);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramInt);
        localStringBuilder.append("");
        localHashMap.put("hide_ad_type", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(System.currentTimeMillis());
        localStringBuilder.append("");
        localHashMap.put("hide_ad_time", localStringBuilder.toString());
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("reportUserHideAdInfo isFrame = ");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(", url = ");
        localStringBuilder.append(paramString1);
        localStringBuilder.append(", rules = ");
        localStringBuilder.append(paramString2);
        AdInfoManager.adblockLog(localStringBuilder.toString());
        SmttServiceProxy.getInstance().upLoadToBeacon("MTT_CORE_QB_USER_HIDE_AD_INFO", localHashMap);
      }
    });
  }
  
  public boolean saveAdInfoToDatabase(TencentWebViewDatabaseAdapter.AdInfo paramAdInfo)
  {
    if (initializeException) {
      return false;
    }
    if ((paramAdInfo != null) && (paramAdInfo.host != null) && (this.mDataBase != null))
    {
      TencentWebViewDatabaseAdapter.AdStatus localAdStatus = (TencentWebViewDatabaseAdapter.AdStatus)AdInfoQuickCheckTable.get(paramAdInfo.host);
      Object localObject2;
      Object localObject1;
      Map.Entry localEntry;
      TencentWebViewDatabaseAdapter.Values localValues;
      int i;
      if (!paramAdInfo.filterRulesMap.isEmpty())
      {
        localObject2 = new ArrayList();
        localObject1 = paramAdInfo.filterRulesMap.entrySet().iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localEntry = (Map.Entry)((Iterator)localObject1).next();
          localValues = (TencentWebViewDatabaseAdapter.Values)localEntry.getValue();
          if (localValues.dbmode != 1) {
            if (localValues.dbmode == 0)
            {
              this.mDataBase.addRule(paramAdInfo.host, (String)localEntry.getKey(), 0, localAdStatus.isTouch, localAdStatus.directStrategy, localAdStatus.randomIdSwitch, localAdStatus.fixSwitch, localAdStatus.lastRequestTime);
              syncedARule(localValues);
            }
            else if (localValues.dbmode == 2)
            {
              this.mDataBase.deleteRule(paramAdInfo.host, (String)localEntry.getKey());
              ((ArrayList)localObject2).add((String)localEntry.getKey());
            }
          }
        }
        i = 0;
        try
        {
          while (i < ((ArrayList)localObject2).size())
          {
            paramAdInfo.filterRulesMap.remove(((ArrayList)localObject2).get(i));
            i += 1;
          }
        }
        finally {}
      }
      if (!paramAdInfo.hiddenRulesMap.isEmpty())
      {
        localObject1 = new ArrayList();
        localObject2 = paramAdInfo.hiddenRulesMap.entrySet().iterator();
        while (((Iterator)localObject2).hasNext())
        {
          localEntry = (Map.Entry)((Iterator)localObject2).next();
          localValues = (TencentWebViewDatabaseAdapter.Values)localEntry.getValue();
          if (localValues.dbmode != 1) {
            if (localValues.dbmode == 0)
            {
              this.mDataBase.addRule(paramAdInfo.host, (String)localEntry.getKey(), 1, localAdStatus.isTouch, localAdStatus.directStrategy, localAdStatus.randomIdSwitch, localAdStatus.fixSwitch, localAdStatus.lastRequestTime);
              syncedARule(localValues);
            }
            else if (localValues.dbmode == 2)
            {
              this.mDataBase.deleteRule(paramAdInfo.host, (String)localEntry.getKey());
              ((ArrayList)localObject1).add((String)localEntry.getKey());
            }
          }
        }
        i = 0;
        try
        {
          while (i < ((ArrayList)localObject1).size())
          {
            paramAdInfo.hiddenRulesMap.remove(((ArrayList)localObject1).get(i));
            i += 1;
          }
        }
        finally {}
      }
      if ((paramAdInfo.featureRulesStr != null) && (paramAdInfo.featureRulesStr.length() > 0) && (paramAdInfo.featureRulesStatus != null) && (paramAdInfo.featureRulesStatus.dbmode != 1) && (paramAdInfo.featureRulesStatus.dbmode == 0))
      {
        this.mDataBase.deleteAdFeatureRule(paramAdInfo.host);
        this.mDataBase.addRule(paramAdInfo.host, paramAdInfo.featureRulesStr, 6, localAdStatus.isTouch, localAdStatus.directStrategy, localAdStatus.randomIdSwitch, localAdStatus.fixSwitch, localAdStatus.lastRequestTime);
        syncedARule(paramAdInfo.featureRulesStatus);
      }
      if ((paramAdInfo.scriptArguments != null) && (paramAdInfo.scriptArguments.length() > 0) && (paramAdInfo.ScriptArgumentsStatus != null) && (paramAdInfo.ScriptArgumentsStatus.dbmode != 1)) {
        if (paramAdInfo.ScriptArgumentsStatus.dbmode == 0)
        {
          this.mDataBase.addRule(paramAdInfo.host, paramAdInfo.scriptArguments, 2, localAdStatus.isTouch, localAdStatus.directStrategy, localAdStatus.randomIdSwitch, localAdStatus.fixSwitch, localAdStatus.lastRequestTime);
          syncedARule(paramAdInfo.ScriptArgumentsStatus);
        }
        else if (paramAdInfo.ScriptArgumentsStatus.dbmode == 2)
        {
          paramAdInfo.ScriptArgumentsStatus = null;
          paramAdInfo.scriptArguments = null;
        }
      }
      return (paramAdInfo.userSelectHiddenRulesMap.isEmpty()) && (paramAdInfo.hiddenRulesMap.isEmpty()) && (paramAdInfo.featureRulesStatus == null) && (paramAdInfo.scriptArguments == null);
    }
    return false;
  }
  
  public boolean saveUserSelectAdInfoToDatabase(TencentWebViewDatabaseAdapter.AdInfo paramAdInfo)
  {
    if (initializeException) {
      return false;
    }
    Object localObject1;
    ArrayList localArrayList;
    Iterator localIterator;
    if ((paramAdInfo != null) && (paramAdInfo.host != null))
    {
      if (this.mDataBase == null) {
        return false;
      }
      localObject1 = (TencentWebViewDatabaseAdapter.AdStatus)AdInfoQuickCheckTable.get(paramAdInfo.host);
      if (!paramAdInfo.userSelectHiddenRulesMap.isEmpty())
      {
        localArrayList = new ArrayList();
        localIterator = paramAdInfo.userSelectHiddenRulesMap.entrySet().iterator();
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("saveUserSelectAdInfoToDatabase userSelectHiddenRulesMap count = ");
        ((StringBuilder)localObject2).append(paramAdInfo.userSelectHiddenRulesMap.size());
        adblockLog(((StringBuilder)localObject2).toString());
        while (localIterator.hasNext())
        {
          localObject2 = (Map.Entry)localIterator.next();
          TencentWebViewDatabaseAdapter.Values localValues = (TencentWebViewDatabaseAdapter.Values)((Map.Entry)localObject2).getValue();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("  saveAdInfoToDatabase mode = ");
          localStringBuilder.append(localValues.dbmode);
          localStringBuilder.append(", host =");
          localStringBuilder.append(paramAdInfo.host);
          localStringBuilder.append(", content = ");
          localStringBuilder.append((String)((Map.Entry)localObject2).getKey());
          adblockLog(localStringBuilder.toString());
          if (localValues.dbmode != 1) {
            if (localValues.dbmode == 0)
            {
              this.mDataBase.addRule(paramAdInfo.host, (String)((Map.Entry)localObject2).getKey(), 4, ((TencentWebViewDatabaseAdapter.AdStatus)localObject1).isTouch, ((TencentWebViewDatabaseAdapter.AdStatus)localObject1).directStrategy, ((TencentWebViewDatabaseAdapter.AdStatus)localObject1).randomIdSwitch, ((TencentWebViewDatabaseAdapter.AdStatus)localObject1).fixSwitch, ((TencentWebViewDatabaseAdapter.AdStatus)localObject1).lastRequestTime);
              syncedARule(localValues);
            }
            else if (localValues.dbmode == 2)
            {
              this.mDataBase.deleteRule(paramAdInfo.host, (String)((Map.Entry)localObject2).getKey());
              localArrayList.add((String)((Map.Entry)localObject2).getKey());
            }
          }
        }
      }
    }
    for (;;)
    {
      int i;
      try
      {
        localIterator = paramAdInfo.userSelectHiddenRulesMap.keySet().iterator();
        if (localIterator.hasNext())
        {
          localObject1 = (String)localIterator.next();
          i = 0;
          if (i >= localArrayList.size()) {
            continue;
          }
          if (!((String)localObject1).equals(localArrayList.get(i))) {
            break label457;
          }
          localIterator.remove();
          break label457;
        }
      }
      finally {}
      return paramAdInfo.userSelectHiddenRulesMap.isEmpty();
      return false;
      label457:
      i += 1;
    }
  }
  
  @CalledByNative
  void setAdInfo(String paramString1, String paramString2)
  {
    for (;;)
    {
      Object localObject2;
      String str2;
      String str3;
      try
      {
        boolean bool = initializeException;
        if (bool) {
          return;
        }
        int i;
        try
        {
          String str4 = new m(paramString1).b.toLowerCase();
          bool = TextUtils.isEmpty(str4);
          if (bool) {
            return;
          }
          String[] arrayOfString = paramString2.split("\r\n");
          i = 0;
          localObject1 = null;
          paramString1 = (String)localObject1;
          paramString2 = paramString1;
          String str1 = paramString2;
          if (i < arrayOfString.length)
          {
            if (arrayOfString[i].startsWith("hide="))
            {
              localObject2 = arrayOfString[i].substring(5);
              str2 = paramString1;
              str3 = paramString2;
            }
            else if (arrayOfString[i].startsWith("filter="))
            {
              str2 = arrayOfString[i].substring(7);
              localObject2 = localObject1;
              str3 = paramString2;
            }
            else if (arrayOfString[i].startsWith("feature="))
            {
              str3 = arrayOfString[i].substring(8);
              localObject2 = localObject1;
              str2 = paramString1;
            }
            else
            {
              localObject2 = localObject1;
              str2 = paramString1;
              str3 = paramString2;
              if (arrayOfString[i].startsWith("adblock="))
              {
                str1 = arrayOfString[i].substring(8);
                localObject2 = localObject1;
                str2 = paramString1;
                str3 = paramString2;
              }
            }
          }
          else
          {
            parseAdInfo(str4, (String)localObject1, paramString1, null, paramString2, str1);
            return;
          }
        }
        catch (Throwable paramString1)
        {
          paramString1.printStackTrace();
          return;
        }
        i += 1;
      }
      finally {}
      Object localObject1 = localObject2;
      paramString1 = str2;
      paramString2 = str3;
    }
  }
  
  /* Error */
  @CalledByNative
  void setAdInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: getstatic 98	com/tencent/smtt/webkit/AdInfoManager:initializeException	Z
    //   5: istore 8
    //   7: iload 8
    //   9: ifeq +6 -> 15
    //   12: aload_0
    //   13: monitorexit
    //   14: return
    //   15: new 773	com/tencent/smtt/net/m
    //   18: dup
    //   19: aload_1
    //   20: invokespecial 775	com/tencent/smtt/net/m:<init>	(Ljava/lang/String;)V
    //   23: getfield 778	com/tencent/smtt/net/m:b	Ljava/lang/String;
    //   26: invokevirtual 781	java/lang/String:toLowerCase	()Ljava/lang/String;
    //   29: astore 9
    //   31: aload 9
    //   33: invokestatic 357	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   36: istore 8
    //   38: iload 8
    //   40: ifeq +6 -> 46
    //   43: aload_0
    //   44: monitorexit
    //   45: return
    //   46: aload_0
    //   47: aload_1
    //   48: aload 4
    //   50: invokevirtual 799	com/tencent/smtt/webkit/AdInfoManager:handleAdBlockBackendCmd	(Ljava/lang/String;Ljava/lang/String;)V
    //   53: aload_0
    //   54: aload 9
    //   56: aload_2
    //   57: aload_3
    //   58: aload 5
    //   60: aload 6
    //   62: aload 7
    //   64: invokevirtual 793	com/tencent/smtt/webkit/AdInfoManager:parseAdInfo	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   67: aload_0
    //   68: monitorexit
    //   69: return
    //   70: astore_1
    //   71: aload_1
    //   72: invokevirtual 796	java/lang/Throwable:printStackTrace	()V
    //   75: aload_0
    //   76: monitorexit
    //   77: return
    //   78: astore_1
    //   79: aload_0
    //   80: monitorexit
    //   81: aload_1
    //   82: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	83	0	this	AdInfoManager
    //   0	83	1	paramString1	String
    //   0	83	2	paramString2	String
    //   0	83	3	paramString3	String
    //   0	83	4	paramString4	String
    //   0	83	5	paramString5	String
    //   0	83	6	paramString6	String
    //   0	83	7	paramString7	String
    //   5	34	8	bool	boolean
    //   29	26	9	str	String
    // Exception table:
    //   from	to	target	type
    //   15	38	70	java/lang/Throwable
    //   46	67	70	java/lang/Throwable
    //   2	7	78	finally
    //   15	38	78	finally
    //   46	67	78	finally
    //   71	75	78	finally
  }
  
  void setAdInfoBackendType(String paramString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("setAdInfoBackendType host=");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(" isTouch=");
      ((StringBuilder)localObject).append(paramBoolean);
      adblockLog(((StringBuilder)localObject).toString());
      long l = System.currentTimeMillis();
      if (!AdInfoQuickCheckTable.containsKey(paramString))
      {
        AdInfoQuickCheckTable.put(paramString, new TencentWebViewDatabaseAdapter.AdStatus(paramString, paramBoolean, paramInt1, paramInt2, paramInt3, l));
      }
      else
      {
        localObject = (TencentWebViewDatabaseAdapter.AdStatus)AdInfoQuickCheckTable.get(paramString);
        if (localObject == null)
        {
          AdInfoQuickCheckTable.put(paramString, new TencentWebViewDatabaseAdapter.AdStatus(paramString, paramBoolean, paramInt1, paramInt2, paramInt3, l));
        }
        else
        {
          ((TencentWebViewDatabaseAdapter.AdStatus)localObject).isTouch = paramBoolean;
          ((TencentWebViewDatabaseAdapter.AdStatus)localObject).directStrategy = paramInt1;
          ((TencentWebViewDatabaseAdapter.AdStatus)localObject).randomIdSwitch = paramInt2;
          ((TencentWebViewDatabaseAdapter.AdStatus)localObject).lastRequestTime = l;
        }
      }
      return;
    }
    finally {}
  }
  
  public void setContext(Context paramContext)
  {
    if (paramContext != null)
    {
      if (this.mAppContext != null) {
        return;
      }
      this.mAppContext = paramContext.getApplicationContext();
      return;
    }
  }
  
  @CalledByNative
  public void setUserSelectAdInfo(String paramString1, String paramString2)
  {
    if (initializeException) {
      return;
    }
    try
    {
      paramString1 = new m(paramString1).b.toLowerCase();
      if (TextUtils.isEmpty(paramString1)) {
        return;
      }
      parseUserSelectAdInfo(paramString1, paramString2);
      return;
    }
    catch (Throwable paramString1)
    {
      paramString1.printStackTrace();
    }
  }
  
  @CalledByNative
  public boolean shallRequestAdblockInfo(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    String str = TencentURLUtil.getHost(paramString);
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    if (!paramBoolean1)
    {
      if ((DIRECT_ADBLOCK_SWITCHER_STATUS == -1) || ((paramBoolean2) && (DIRECT_ADBLOCK_SWITCHER_STATUS_FOR_MINIQB == -1))) {
        if (e.a().a(paramBoolean2))
        {
          DIRECT_ADBLOCK_SWITCHER_STATUS = SmttServiceProxy.getInstance().directAdblockSwitcherLevelForQB();
          if (paramBoolean2) {
            DIRECT_ADBLOCK_SWITCHER_STATUS_FOR_MINIQB = DIRECT_ADBLOCK_SWITCHER_STATUS;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("isQBMode DIRECT_ADBLOCK_SWITCHER_STATUS=");
          ((StringBuilder)localObject).append(DIRECT_ADBLOCK_SWITCHER_STATUS_FOR_MINIQB);
          adblockLog(((StringBuilder)localObject).toString());
        }
        else
        {
          DIRECT_ADBLOCK_SWITCHER_STATUS = SmttServiceProxy.getInstance().directAdblockSwitcherLevel();
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("isTBSMode DIRECT_ADBLOCK_SWITCHER_STATUS=");
          ((StringBuilder)localObject).append(DIRECT_ADBLOCK_SWITCHER_STATUS);
          adblockLog(((StringBuilder)localObject).toString());
        }
      }
      int i;
      if (paramBoolean2) {
        i = DIRECT_ADBLOCK_SWITCHER_STATUS_FOR_MINIQB;
      } else {
        i = DIRECT_ADBLOCK_SWITCHER_STATUS;
      }
      switch (i)
      {
      default: 
        break;
      case 3: 
        if (isHostNameInAdblockBlackList(str)) {
          return false;
        }
        break;
      case 2: 
        if (paramString.startsWith("https")) {
          return false;
        }
        if (isHostNameInAdblockBlackList(str)) {
          return false;
        }
        break;
      case 1: 
        if (!isHostNameInAdblockWhiteList(str)) {
          return false;
        }
        break;
      case 0: 
        return false;
      }
    }
    Object localObject = (TencentWebViewDatabaseAdapter.AdStatus)AdInfoQuickCheckTable.get(str);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("shallRequestAdblockInfo url=");
    localStringBuilder.append(paramString);
    localStringBuilder.append(" isProxy=");
    localStringBuilder.append(paramBoolean1);
    localStringBuilder.append(" RulesCached?=");
    if (localObject != null) {
      paramString = Boolean.valueOf(((TencentWebViewDatabaseAdapter.AdStatus)localObject).isTouch);
    } else {
      paramString = "null";
    }
    localStringBuilder.append(paramString);
    adblockLog(localStringBuilder.toString());
    if ((localObject != null) && (((TencentWebViewDatabaseAdapter.AdStatus)localObject).isTouch))
    {
      long l = System.currentTimeMillis();
      if ((((TencentWebViewDatabaseAdapter.AdStatus)localObject).isTouch) && (((TencentWebViewDatabaseAdapter.AdStatus)localObject).lastRequestTime + TouchTypeCheckInterval > l))
      {
        paramString = new StringBuilder();
        paramString.append("adinfo is valid ");
        paramString.append(str);
        paramString.append(" isTouch=");
        paramString.append(((TencentWebViewDatabaseAdapter.AdStatus)localObject).isTouch);
        paramString.append(" ExipredTime=");
        paramString.append(((TencentWebViewDatabaseAdapter.AdStatus)localObject).lastRequestTime + TouchTypeCheckInterval);
        paramString.append("  now=");
        paramString.append(l);
        adblockLog(paramString.toString());
        return false;
      }
    }
    return true;
  }
  
  @CalledByNative
  public boolean shouldFilterFixElement(String paramString)
  {
    paramString = TencentURLUtil.getHost(paramString);
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    paramString = (TencentWebViewDatabaseAdapter.AdStatus)AdInfoQuickCheckTable.get(paramString);
    if ((paramString != null) && (paramString.isTouch)) {
      return paramString.fixSwitch == 1;
    }
    return false;
  }
  
  public void syncAdInfoFromRamToFlash()
  {
    if (!WebSettingsExtension.getRemoveAds()) {
      return;
    }
    if (initializeException) {
      return;
    }
    SyncWorkerThread localSyncWorkerThread = this.mSyncWorker;
    if (localSyncWorkerThread == null) {
      return;
    }
    localSyncWorkerThread.post(new Runnable()
    {
      public void run()
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("syncAdInfoFromRamToFlash thread=");
        ((StringBuilder)localObject).append(Thread.currentThread().getName());
        AdInfoManager.adblockLog(((StringBuilder)localObject).toString());
        AdInfoManager.this.flushLiveLog();
        long l1 = System.currentTimeMillis();
        localObject = AdInfoManager.this.getUpdatedAdInfoSince().iterator();
        ArrayList localArrayList = new ArrayList();
        while (((Iterator)localObject).hasNext())
        {
          TencentWebViewDatabaseAdapter.AdInfo localAdInfo = (TencentWebViewDatabaseAdapter.AdInfo)((Iterator)localObject).next();
          if (AdInfoManager.this.saveAdInfoToDatabase(localAdInfo))
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("syncAdInfoFromRamToFlash todelete =");
            localStringBuilder.append(localAdInfo.host);
            AdInfoManager.adblockLog(localStringBuilder.toString());
            localArrayList.add(localAdInfo.host);
          }
        }
        AdInfoManager.this.deleteAdInfo(localArrayList);
        long l2 = System.currentTimeMillis();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("syncAdInfoFromRamToFlash cost=");
        ((StringBuilder)localObject).append(l2 - l1);
        AdInfoManager.adblockLog(((StringBuilder)localObject).toString());
      }
    });
  }
  
  void syncedARule(TencentWebViewDatabaseAdapter.Values paramValues)
  {
    try
    {
      paramValues.dbmode = 1;
      return;
    }
    finally
    {
      paramValues = finally;
      throw paramValues;
    }
  }
  
  private class SyncWorkerThread
  {
    private Handler jdField_a_of_type_AndroidOsHandler = null;
    private Looper jdField_a_of_type_AndroidOsLooper = null;
    
    public SyncWorkerThread() {}
    
    public final boolean post(Runnable paramRunnable)
    {
      return this.jdField_a_of_type_AndroidOsHandler.post(paramRunnable);
    }
  }
  
  private static class a
  {
    public static final AdInfoManager a = new AdInfoManager();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\AdInfoManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */