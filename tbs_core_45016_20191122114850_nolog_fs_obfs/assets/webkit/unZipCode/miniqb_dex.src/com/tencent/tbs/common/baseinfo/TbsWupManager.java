package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.common.serverconfig.WupServerConfigsWrapper;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.MTT.PreferencesKeyValue;
import com.tencent.tbs.common.MTT.PreferencesReq;
import com.tencent.tbs.common.MTT.PreferencesRsp;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSPageLoadInfoManager;
import com.tencent.tbs.common.utils.TbsFileUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.wup.MultiWUPRequest;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executor;

public class TbsWupManager
  implements IWUPRequestCallBack
{
  public static final String REQ_NAME_DOMAIN_LIST = "domainList";
  public static final String REQ_NAME_PREFERENCE = "preference";
  private static final String TAG = "TbsWupManager";
  private static final long TIME_24_HOUR = 86400000L;
  private static boolean mFirstStart = true;
  private static TbsWupManager mInstance;
  private byte mForcePullDomainAndPred = 0;
  private boolean mIsDebugMode = false;
  private long mLastBasicConfMiniQB = -1L;
  private long mLastBasicConfTBS = -1L;
  private long mLastCheckDate = -1L;
  
  public TbsWupManager()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("last check wup date:");
    localStringBuilder.append(this.mLastCheckDate);
    localStringBuilder.append(", LastBasicConfMiniQB=");
    localStringBuilder.append(this.mLastBasicConfMiniQB);
    localStringBuilder.append(" ,LastBasicConfTBS=");
    localStringBuilder.append(this.mLastBasicConfTBS);
    LogUtils.d("TbsWupManager", localStringBuilder.toString());
  }
  
  private boolean forcePullDomainAndPrefs(byte paramByte)
  {
    byte b = TbsBaseModuleShell.REQ_SRC_TBS;
    boolean bool2 = false;
    boolean bool1;
    if (paramByte == b)
    {
      bool1 = bool2;
      if ((byte)(this.mForcePullDomainAndPred & 0x1) == 1) {
        bool1 = true;
      }
    }
    else
    {
      bool1 = bool2;
      if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB)
      {
        bool1 = bool2;
        if ((byte)(this.mForcePullDomainAndPred & 0x2) == 2) {
          bool1 = true;
        }
      }
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("forcePullDomainAndPrefs, mForcePullDomainAndPred=");
    localStringBuilder.append(this.mForcePullDomainAndPred);
    localStringBuilder.append(", result=");
    localStringBuilder.append(bool1);
    LogUtils.d("TbsWupManager", localStringBuilder.toString());
    return bool1;
  }
  
  public static int getAreaTypeFromDisk()
  {
    SharedPreferences localSharedPreferences = TbsBaseModuleShell.getContext().getSharedPreferences("x5_proxy_setting", 0);
    if (localSharedPreferences != null) {
      return localSharedPreferences.getInt("setting_area_type", 0);
    }
    LogUtils.e("TbsWupManager", "requestServiceByAreaType+++++++failed");
    return 0;
  }
  
  public static TbsWupManager getInstance()
  {
    if (mInstance == null) {
      mInstance = new TbsWupManager();
    }
    return mInstance;
  }
  
  private boolean isLastWupConfigFromTestWupEnv()
  {
    return (isLastReqFromWupTestEvn("domainList")) || (isLastReqFromWupTestEvn("preference"));
  }
  
  private boolean isNewTbsCore(Context paramContext)
  {
    Object localObject = TbsBaseModuleShell.getCoreInfoFetcher();
    paramContext = PublicSettingManager.getInstance().getFormerTbsVersion();
    if (localObject != null)
    {
      localObject = ((ICoreInfoFetcher)localObject).getCoreVersion();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("isNewTbsCore()--currentTbsVersion = ");
      localStringBuilder.append((String)localObject);
      localStringBuilder.append(", formerTbsVersion = ");
      localStringBuilder.append(paramContext);
      LogUtils.d("TbsWupManager", localStringBuilder.toString());
      if ((localObject != null) && (!((String)localObject).equals(paramContext)))
      {
        PublicSettingManager.getInstance().setFormerTbsVersion((String)localObject);
        LogUtils.d("TbsWupManager", "isNewTbsCore()--return true ");
        return true;
      }
    }
    return false;
  }
  
  private void onPreferences(WUPResponseBase paramWUPResponseBase)
  {
    if (paramWUPResponseBase == null) {
      return;
    }
    Object localObject1 = paramWUPResponseBase.getReturnCode();
    Object localObject2;
    if (localObject1 != null)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("result  ");
      ((StringBuilder)localObject2).append(localObject1);
      LogUtils.d("TbsWupManager", ((StringBuilder)localObject2).toString());
    }
    else
    {
      LogUtils.d("TbsWupManager", "packet.get() = null  ");
    }
    localObject1 = (PreferencesRsp)paramWUPResponseBase.get("rsp");
    if (localObject1 != null)
    {
      localObject2 = ((PreferencesRsp)localObject1).vPreferencesKeyValue;
      if ((localObject2 != null) && (!((ArrayList)localObject2).isEmpty()))
      {
        paramWUPResponseBase = PublicSettingManager.getInstance();
        paramWUPResponseBase.setBreakCommit(true);
        localObject2 = ((ArrayList)localObject2).iterator();
        while (((Iterator)localObject2).hasNext())
        {
          PreferencesKeyValue localPreferencesKeyValue = (PreferencesKeyValue)((Iterator)localObject2).next();
          setPsm(localPreferencesKeyValue.sKey, localPreferencesKeyValue.sValue, paramWUPResponseBase);
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("preference info =");
          localStringBuilder.append(localPreferencesKeyValue.sKey);
          localStringBuilder.append("   ");
          localStringBuilder.append(localPreferencesKeyValue.sValue);
          LogUtils.d("TbsWupManager", localStringBuilder.toString());
        }
        int i = ((PreferencesRsp)localObject1).iRspTime;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("update prefernce time=");
        ((StringBuilder)localObject2).append(i);
        LogUtils.d("TbsWupManager", ((StringBuilder)localObject2).toString());
        paramWUPResponseBase.setPreferenceUpdateTime(i);
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("onPreferences: rsp.vPreferencesKeyValue size = ");
        ((StringBuilder)localObject2).append(((PreferencesRsp)localObject1).vPreferencesKeyValue.size());
        LogUtils.d("debugPreference", ((StringBuilder)localObject2).toString());
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("onPreferences: rsp.iRspTime =");
        ((StringBuilder)localObject1).append(i);
        LogUtils.d("debugPreference", ((StringBuilder)localObject1).toString());
        paramWUPResponseBase.setBreakCommit(false);
        paramWUPResponseBase.commitAll();
        return;
      }
      LogUtils.d("TbsWupManager", "vPreferencesKeyValue list is empty, ignore");
      return;
    }
    LogUtils.d("TbsWupManager", "preference result list is empty");
  }
  
  private void onVideoPreferences(WUPResponseBase paramWUPResponseBase)
  {
    if (paramWUPResponseBase == null) {
      return;
    }
    Object localObject1 = paramWUPResponseBase.getReturnCode();
    Object localObject2;
    if (localObject1 != null)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("onVideoPreferences: result  ");
      ((StringBuilder)localObject2).append(localObject1);
      LogUtils.d("TbsWupManager", ((StringBuilder)localObject2).toString());
    }
    else
    {
      LogUtils.d("TbsWupManager", "onVideoPreferences: packet.get() = null  ");
    }
    paramWUPResponseBase = (PreferencesRsp)paramWUPResponseBase.get("rsp");
    if (paramWUPResponseBase == null)
    {
      LogUtils.d("TbsWupManager", "onVideoPreferences: preference result list is empty");
      return;
    }
    paramWUPResponseBase = paramWUPResponseBase.vPreferencesKeyValue;
    if ((paramWUPResponseBase != null) && (!paramWUPResponseBase.isEmpty()))
    {
      paramWUPResponseBase = paramWUPResponseBase.iterator();
      localObject1 = TbsBaseModuleShell.getContext().getSharedPreferences("qb_video_settings", 0);
      if (localObject1 != null)
      {
        localObject1 = ((SharedPreferences)localObject1).edit();
        while (paramWUPResponseBase.hasNext())
        {
          localObject2 = (PreferencesKeyValue)paramWUPResponseBase.next();
          try
          {
            i = Integer.parseInt(((PreferencesKeyValue)localObject2).sValue);
          }
          catch (Exception localException)
          {
            int i;
            StringBuilder localStringBuilder;
            for (;;) {}
          }
          i = 64537;
          if (i != 64537) {
            ((SharedPreferences.Editor)localObject1).putInt(((PreferencesKeyValue)localObject2).sKey, i);
          } else {
            ((SharedPreferences.Editor)localObject1).putString(((PreferencesKeyValue)localObject2).sKey, ((PreferencesKeyValue)localObject2).sValue);
          }
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("onVideoPreferences: preference info =");
          localStringBuilder.append(((PreferencesKeyValue)localObject2).sKey);
          localStringBuilder.append("   ");
          localStringBuilder.append(((PreferencesKeyValue)localObject2).sValue);
          LogUtils.d("TbsWupManager", localStringBuilder.toString());
        }
        ((SharedPreferences.Editor)localObject1).commit();
      }
      return;
    }
    LogUtils.d("TbsWupManager", "onVideoPreferences: vPreferencesKeyValue list is empty, ignore");
  }
  
  private void putVideoSettingValue(String paramString1, String paramString2, int paramInt)
  {
    SharedPreferences localSharedPreferences = TbsBaseModuleShell.getContext().getSharedPreferences("qb_video_settings", 0);
    if (localSharedPreferences != null) {
      localSharedPreferences.edit().putInt(paramString1, StringUtils.parseInt(paramString2, paramInt)).commit();
    }
  }
  
  private void setCheckWupOKTime()
  {
    this.mLastCheckDate = System.currentTimeMillis();
    PublicSettingManager.getInstance().setLastCheckWupConfigDate(this.mLastCheckDate);
  }
  
  private void setLastCheckConfDate(byte paramByte)
  {
    long l = System.currentTimeMillis();
    if (paramByte == 1) {
      this.mLastBasicConfMiniQB = l;
    } else {
      this.mLastBasicConfTBS = l;
    }
    PublicSettingManager.getInstance().setLastCheckBasicConfigDate(l, paramByte);
  }
  
  private void updateWupTestEnvFlag(String paramString, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("updateWupTestEnvFlag: requestName=");
    localStringBuilder.append(paramString);
    localStringBuilder.append(", fromTest=");
    localStringBuilder.append(paramBoolean);
    LogUtils.d("TbsWupManager", localStringBuilder.toString());
    PublicSettingManager.getInstance().setLastWupReqFromTestEnv(paramBoolean, paramString);
  }
  
  public boolean checkMiniQBVersion(byte paramByte)
  {
    if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB)
    {
      localObject = TbsBaseModuleShell.getMiniQBVersion();
      String str = PublicSettingManager.getInstance().getLastMiniqbVersionForPrefs();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("checkMiniQBVersion: currMiniQBVersion = ");
      localStringBuilder.append((String)localObject);
      localStringBuilder.append(", prevMiniQBVersion = ");
      localStringBuilder.append(str);
      LogUtils.d("TbsWupManager", localStringBuilder.toString());
      if ((!TextUtils.isEmpty((CharSequence)localObject)) && (!StringUtils.isStringEqual((String)localObject, str)))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("checkMiniQBVersion: return TRUE, type = ");
        ((StringBuilder)localObject).append(paramByte);
        LogUtils.d("TbsWupManager", ((StringBuilder)localObject).toString());
        return true;
      }
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("checkMiniQBVersion: return FALSE, type = ");
    ((StringBuilder)localObject).append(paramByte);
    LogUtils.d("TbsWupManager", ((StringBuilder)localObject).toString());
    return false;
  }
  
  public void doMultiWupRequest()
  {
    LogUtils.d("TbsWupManager", "doMultiWupRequest");
    BrowserExecutorSupplier.forIoTasks().execute(new Runnable()
    {
      public void run()
      {
        TbsWupManager.this.doMultiWupRequestForThirdParty(TbsBaseModuleShell.REQ_SRC_TBS);
      }
    });
  }
  
  public void doMultiWupRequestForQB()
  {
    MultiWUPRequest localMultiWUPRequest = new MultiWUPRequest();
    if (!GUIDFactory.getInstance().isGuidValidate()) {
      LogUtils.d("TbsWupManager", "Guid currently not valid, wait for UI to set");
    }
    boolean bool1 = isNewTbsCore(TbsBaseModuleShell.getCallerContext());
    int j = 0;
    if (bool1)
    {
      TbsUserInfo.getInstance().setDomainTime(0);
      PublicSettingManager.getInstance().setPreferenceUpdateTime(0);
      bool1 = true;
    }
    else
    {
      bool1 = false;
    }
    if (bool1) {
      PublicSettingManager.getInstance().setQBHavePullPluginListYet(false);
    }
    int i;
    if ((!bool1) && (!mFirstStart))
    {
      i = 0;
    }
    else
    {
      if ((!bool1) && (this.mLastCheckDate > 0L) && (System.currentTimeMillis() - this.mLastCheckDate < 3600000L))
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("ignore prefs and domains: forceToRequest = ");
        localStringBuilder.append(bool1);
        localStringBuilder.append(", mLastCheckDate=");
        localStringBuilder.append(this.mLastCheckDate);
        localStringBuilder.append(", time gap=");
        localStringBuilder.append(System.currentTimeMillis() - this.mLastCheckDate);
        LogUtils.d("TbsWupManager", localStringBuilder.toString());
        i = 0;
      }
      else
      {
        localMultiWUPRequest.addWUPRequest(TbsDomainWhiteListManager.getInstance().getDomainWupRequest(bool1, TbsBaseModuleShell.REQ_SRC_TBS));
        localMultiWUPRequest.addWUPRequest(getPreferneceRequest(bool1, TbsBaseModuleShell.REQ_SRC_TBS));
        i = 1;
      }
      mFirstStart = false;
    }
    if ((bool1) || (isNewDay()) || (this.mIsDebugMode))
    {
      localMultiWUPRequest.addWUPRequest(TbsUserInfo.getInstance().getIPListRequest(false));
      LogUtils.d("TheSimCardOfGreatKing", "in doMultiWupRequestForQB create httpsTunnelTokenRequest");
      localMultiWUPRequest.addWUPRequest(TbsUserInfo.getInstance().getHTTPSTunnelTokenRequest());
      LogUtils.d("AdFilter", "in doMultiWupRequestForQB getSmartAdFilterSource");
      WUPTaskProxy.send(TbsUserInfo.getInstance().getSmartAdFilterSource());
      LogUtils.d("AdFilter", "in doMultiWupRequestForQB getAdblockSourceRequest");
      WUPTaskProxy.send(TbsUserInfo.getInstance().getAdblockSourceRequest());
      j = 1;
    }
    LogUtils.d("AdFilter", "in doMultiWupRequestForQB getSubsetAdRuleRequest");
    localMultiWUPRequest.addWUPRequest(TbsUserInfo.getInstance().getSubsetAdRuleRequest());
    bool1 = TbsFileUtils.getUserInfoFile(TbsBaseModuleShell.getContext()).exists();
    boolean bool2 = TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getContext(), TbsBaseModuleShell.REQ_SRC_TBS).exists();
    if ((!bool1) && (j == 0)) {
      localMultiWUPRequest.addWUPRequest(TbsUserInfo.getInstance().getIPListReqsOnBoot());
    }
    if ((!bool2) && (i == 0)) {
      localMultiWUPRequest.addWUPRequest(TbsDomainWhiteListManager.getInstance().getDomainWupRequest(true, TbsBaseModuleShell.REQ_SRC_TBS));
    }
    WUPTaskProxy.send(localMultiWUPRequest);
  }
  
  public void doMultiWupRequestForThirdParty(byte paramByte)
  {
    MultiWUPRequest localMultiWUPRequest = new MultiWUPRequest();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doMultiWupRequest, type=");
    ((StringBuilder)localObject).append(paramByte);
    LogUtils.d("TbsWupManager", ((StringBuilder)localObject).toString());
    if (!GUIDFactory.getInstance().isGuidValidate())
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("doMultiWupRequest, set GUID Request, type=");
      ((StringBuilder)localObject).append(paramByte);
      LogUtils.d("TbsWupManager", ((StringBuilder)localObject).toString());
      localMultiWUPRequest.addWUPRequest(GUIDFactory.getInstance().getGUIDWupRequest(0));
    }
    TBSPageLoadInfoManager.getInstance(TbsBaseModuleShell.getCallerContext()).load();
    boolean bool1 = isNewTbsCore(TbsBaseModuleShell.getCallerContext());
    boolean bool2 = TbsBaseModuleShell.isQBMode();
    boolean bool3 = isNewDay();
    boolean bool4 = shouldCheckBasicConf(paramByte);
    boolean bool5 = checkMiniQBVersion(paramByte);
    boolean bool6 = isLastWupConfigFromTestWupEnv();
    if ((!bool1) && (!bool6)) {
      bool1 = false;
    } else {
      bool1 = true;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doMultiWupRequest, type=");
    ((StringBuilder)localObject).append(paramByte);
    ((StringBuilder)localObject).append(", isNewTbsCore=");
    ((StringBuilder)localObject).append(bool1);
    ((StringBuilder)localObject).append(" ,isQBMode=");
    ((StringBuilder)localObject).append(bool2);
    ((StringBuilder)localObject).append(", isNewDay=");
    ((StringBuilder)localObject).append(bool3);
    ((StringBuilder)localObject).append(" ,shouldCheckBasicConf=");
    ((StringBuilder)localObject).append(bool4);
    ((StringBuilder)localObject).append(", mFirstStart=");
    ((StringBuilder)localObject).append(mFirstStart);
    ((StringBuilder)localObject).append(", isNewMiniQBVersion = ");
    ((StringBuilder)localObject).append(bool5);
    ((StringBuilder)localObject).append(", isLastConfigFromTestServer = ");
    ((StringBuilder)localObject).append(bool6);
    LogUtils.d("TbsWupManager", ((StringBuilder)localObject).toString());
    if ((bool1) || (bool5) || (this.mIsDebugMode))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("doMultiWupRequest, set setDomainTime to 0, APN type=");
      ((StringBuilder)localObject).append(paramByte);
      LogUtils.d("TbsWupManager", ((StringBuilder)localObject).toString());
      setRespondSpacing(0);
      if (bool5)
      {
        PublicSettingManager.getInstance().setMiniQBPreferenceUpdateTime(0);
        PublicSettingManager.getInstance().setDomainTime(TbsBaseModuleShell.REQ_SRC_MINI_QB, 0);
      }
    }
    int i;
    if ((!bool1) && (!bool4) && (!bool5) && (!forcePullDomainAndPrefs(paramByte)) && (!this.mIsDebugMode)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("doMultiWupRequest, set Domain and Preference reqs, type=");
      ((StringBuilder)localObject).append(paramByte);
      LogUtils.d("TbsWupManager", ((StringBuilder)localObject).toString());
      localMultiWUPRequest.addWUPRequest(getPreferneceRequest(false, paramByte));
    }
    bool4 = TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getContext(), paramByte).exists();
    if ((i != 0) || (!bool4)) {
      localMultiWUPRequest.addWUPRequest(TbsDomainWhiteListManager.getInstance().getDomainWupRequest(false, paramByte));
    }
    if (paramByte == TbsBaseModuleShell.REQ_SRC_TBS)
    {
      bool4 = TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getContext(), TbsBaseModuleShell.REQ_SRC_TBS_VIDEO).exists();
      if ((i != 0) || (!bool4)) {
        localMultiWUPRequest.addWUPRequest(TbsDomainWhiteListManager.getInstance().getDomainWupRequest(bool1, TbsBaseModuleShell.REQ_SRC_TBS_VIDEO));
      }
    }
    int j;
    if (((!bool3) && (!bool1)) || ((!bool2) && (paramByte != TbsBaseModuleShell.REQ_SRC_TBS) && (!this.mIsDebugMode)))
    {
      i = 0;
      j = 0;
    }
    else
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("doMultiWupRequest, set plugins, ipList and url reqs, type=");
      ((StringBuilder)localObject).append(paramByte);
      LogUtils.d("TbsWupManager", ((StringBuilder)localObject).toString());
      localObject = TbsUserInfo.getInstance().getIPListReqsOnBoot();
      if (localObject != null) {
        i = 1;
      } else {
        i = 0;
      }
      localMultiWUPRequest.addWUPRequest((WUPRequest)localObject);
      int k = 1;
      j = i;
      i = k;
    }
    if ((paramByte == TbsBaseModuleShell.REQ_SRC_TBS) && (j == 0) && (WupServerConfigsWrapper.needPullWupServer()))
    {
      LogUtils.d("x5-ip-list", "TBS boot, but WUP do not has current network IP, try get");
      localObject = new ArrayList();
      ((ArrayList)localObject).add(Integer.valueOf(1));
      localMultiWUPRequest.addWUPRequest(TbsUserInfo.getInstance().getIPListRequest((ArrayList)localObject));
    }
    if ((!TbsFileUtils.getUserInfoFile(TbsBaseModuleShell.getContext()).exists()) && (i == 0)) {
      localMultiWUPRequest.addWUPRequest(TbsUserInfo.getInstance().getIPListReqsOnBoot());
    }
    WUPTaskProxy.send(localMultiWUPRequest);
    this.mIsDebugMode = false;
  }
  
  public WUPRequest getPreferneceRequest(boolean paramBoolean, byte paramByte)
  {
    WUPRequest localWUPRequest = new WUPRequest("CMD_DOWN_PREFERENCES", "getPreferences");
    localWUPRequest.setType((byte)10);
    localWUPRequest.setBindObject(Byte.valueOf(paramByte));
    localWUPRequest.setRequestCallBack(this);
    PreferencesReq localPreferencesReq = new PreferencesReq();
    if (paramBoolean) {
      localPreferencesReq.iReqTime = 0;
    } else {
      localPreferencesReq.iReqTime = PublicSettingManager.getInstance().getPreferenceUpdateTime(paramByte);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    Object localObject = TbsInfoUtils.getGpuInfoWithSR();
    if (!TextUtils.isEmpty((CharSequence)localObject)) {
      localStringBuilder.append((String)localObject);
    }
    if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("&SR=MINIQB&SRVER=");
      ((StringBuilder)localObject).append(TbsBaseModuleShell.getMiniQBVersion());
      localStringBuilder.append(((StringBuilder)localObject).toString());
    }
    else if (paramByte == TbsBaseModuleShell.REQ_SRC_TBS_VIDEO)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("&SR=TBSVIDEO&SRVER=");
      ((StringBuilder)localObject).append(PublicSettingManager.getInstance().getTbsVideoVersion());
      localStringBuilder.append(((StringBuilder)localObject).toString());
    }
    else
    {
      localStringBuilder.append("&SR=TBS");
    }
    localPreferencesReq.sQua2ExInfo = localStringBuilder.toString();
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("get prefernce req, with exinfo~~ =");
    ((StringBuilder)localObject).append(localStringBuilder.toString());
    LogUtils.d("TbsWupManager", ((StringBuilder)localObject).toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("get prefernce req, time=");
    localStringBuilder.append(localPreferencesReq.iReqTime);
    LogUtils.d("TbsWupManager", localStringBuilder.toString());
    localPreferencesReq.iGrayAreaType = getAreaTypeFromDisk();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("getPreferneceRequest: preferenceReq.iReqTime = ");
    localStringBuilder.append(localPreferencesReq.iReqTime);
    localStringBuilder.append(", preferenceReq.sQua2ExInfo = ");
    localStringBuilder.append(localPreferencesReq.sQua2ExInfo);
    localStringBuilder.append(", preferenceReq.iGrayAreaType = ");
    localStringBuilder.append(localPreferencesReq.iGrayAreaType);
    LogUtils.d("debugPreference", localStringBuilder.toString());
    localWUPRequest.put("req", localPreferencesReq);
    return localWUPRequest;
  }
  
  public boolean isLastReqFromWupTestEvn(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    boolean bool = PublicSettingManager.getInstance().isLastWupReqFromTestEvn(paramString);
    paramString = new StringBuilder();
    paramString.append("isLastConfigFromWupTestEvn: ");
    paramString.append(bool);
    LogUtils.d("TbsWupManager", paramString.toString());
    return bool;
  }
  
  public boolean isNewDay()
  {
    long l1 = System.currentTimeMillis();
    long l2 = l1 - this.mLastCheckDate;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("last check wup date:");
    localStringBuilder.append(this.mLastCheckDate);
    localStringBuilder.append(", curr date:");
    localStringBuilder.append(l1);
    localStringBuilder.append(", time gap=");
    localStringBuilder.append(l2);
    LogUtils.d("TbsWupManager", localStringBuilder.toString());
    if ((l2 < 86400000L) && (l1 >= this.mLastCheckDate))
    {
      LogUtils.d("TbsWupManager", "time gap less than one day");
      return false;
    }
    LogUtils.d("TbsWupManager", "time gap bigger than one day");
    return true;
  }
  
  public void onNetworkChanged()
  {
    if (!Apn.isNetworkConnected()) {
      return;
    }
    if ((TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getContext(), TbsBaseModuleShell.REQ_SRC_TBS).exists()) && (TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).hasDomainListData())) {
      return;
    }
    doMultiWupRequest();
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    if (paramWUPRequestBase.getType() != 10) {
      return;
    }
    this.mLastCheckDate = System.currentTimeMillis();
    this.mLastBasicConfMiniQB = System.currentTimeMillis();
    LogUtils.d("TbsWupManager", "WUP_REQUEST_TYPE_PREFERENCE failed");
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    if (paramWUPRequestBase != null)
    {
      if (paramWUPResponseBase == null) {
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("===onWUPTaskSuccess===");
      localStringBuilder.append(paramWUPRequestBase.getType());
      LogUtils.d("TbsWupManager", localStringBuilder.toString());
      if (paramWUPRequestBase.getType() != 10) {
        return;
      }
      LogUtils.d("TbsWupManager", "WUP_REQUEST_TYPE_PREFERENCE success");
      Object localObject = paramWUPRequestBase.getBindObject();
      localStringBuilder = null;
      paramWUPRequestBase = localStringBuilder;
      if (localObject != null)
      {
        paramWUPRequestBase = localStringBuilder;
        if ((localObject instanceof Byte)) {
          paramWUPRequestBase = (Byte)localObject;
        }
      }
      setPreferenceFromTest(paramWUPResponseBase.isRespFromTestEnvServer());
      if ((paramWUPRequestBase != null) && (paramWUPRequestBase.byteValue() == TbsBaseModuleShell.REQ_SRC_MINI_QB))
      {
        LogUtils.d("TbsWupManager", "WUP_REQUEST_TYPE_PREFERENCE success, type=miniqb");
        TargetAdapter.onPreference(paramWUPResponseBase, TbsBaseModuleShell.REQ_SRC_MINI_QB);
        setLastCheckConfDate(TbsBaseModuleShell.REQ_SRC_MINI_QB);
        return;
      }
      if ((paramWUPRequestBase != null) && (paramWUPRequestBase.byteValue() == TbsBaseModuleShell.REQ_SRC_TBS_VIDEO))
      {
        LogUtils.d("TbsWupManager", "WUP_REQUEST_TYPE_PREFERENCE success, type=tbsvideo");
        onVideoPreferences(paramWUPResponseBase);
        return;
      }
      LogUtils.d("TbsWupManager", "WUP_REQUEST_TYPE_PREFERENCE success, type=tbs");
      onPreferences(paramWUPResponseBase);
      setLastCheckConfDate(TbsBaseModuleShell.REQ_SRC_TBS);
      setCheckWupOKTime();
      return;
    }
  }
  
  public void pullWupDomainInfoByForce()
  {
    WUPTaskProxy.send(TbsDomainWhiteListManager.getInstance().getDomainWupRequest(true, TbsBaseModuleShell.REQ_SRC_TBS));
  }
  
  public void pullWupIpListByForce()
  {
    WUPTaskProxy.send(TbsUserInfo.getInstance().getIPListReqsOnBoot());
  }
  
  public void pullWupMiniQbByForce()
  {
    WUPTaskProxy.send(TbsDomainWhiteListManager.getInstance().getDomainWupRequest(true, TbsBaseModuleShell.REQ_SRC_MINI_QB));
  }
  
  public void pullWupPreferenceByForce()
  {
    WUPTaskProxy.send(getPreferneceRequest(true, TbsBaseModuleShell.REQ_SRC_TBS));
  }
  
  public boolean requestHeadsUp(Context paramContext)
  {
    WUPTaskProxy.send(TbsHeadsUpRequestManager.getInstance(paramContext).getWupRequest());
    return true;
  }
  
  public void requestServiceByAreaType(int paramInt)
  {
    PublicSettingManager.getInstance().clear();
    MultiWUPRequest localMultiWUPRequest = new MultiWUPRequest();
    localMultiWUPRequest.addWUPRequest(TbsDomainWhiteListManager.getInstance().getDomainWupRequest(true, TbsBaseModuleShell.REQ_SRC_TBS));
    localMultiWUPRequest.addWUPRequest(getPreferneceRequest(true, TbsBaseModuleShell.REQ_SRC_TBS));
    if (WUPTaskProxy.send(localMultiWUPRequest)) {
      return;
    }
    LogUtils.e("TbsWupManager", "requestServiceByAreaType+++++++failed");
  }
  
  public void sendWUPRequest(IWUPRequestCallBack paramIWUPRequestCallBack, String paramString1, String paramString2, byte[] paramArrayOfByte, Bundle paramBundle)
  {
    paramIWUPRequestCallBack = new WUPRequest(paramString1, paramString2, paramIWUPRequestCallBack);
    paramIWUPRequestCallBack.setPostData(paramArrayOfByte);
    WUPTaskProxy.send(paramIWUPRequestCallBack);
  }
  
  public void setDebugMode(boolean paramBoolean)
  {
    this.mIsDebugMode = paramBoolean;
  }
  
  public void setDomainListFromTest(boolean paramBoolean)
  {
    updateWupTestEnvFlag("domainList", paramBoolean);
  }
  
  public void setForcePullDomainAndPrefs(boolean paramBoolean, byte paramByte)
  {
    int i;
    if (paramByte == TbsBaseModuleShell.REQ_SRC_TBS)
    {
      if (paramBoolean) {
        i = this.mForcePullDomainAndPred | 0x1;
      } else {
        i = this.mForcePullDomainAndPred & 0xFE;
      }
      this.mForcePullDomainAndPred = ((byte)i);
    }
    else if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB)
    {
      if (paramBoolean) {
        i = this.mForcePullDomainAndPred | 0x2;
      } else {
        i = this.mForcePullDomainAndPred & 0xFD;
      }
      this.mForcePullDomainAndPred = ((byte)i);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setForcePullDomainAndPrefs, force=");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.append(", type=");
    localStringBuilder.append(paramByte);
    localStringBuilder.append(", mForcePullDomainAndPred=");
    localStringBuilder.append(this.mForcePullDomainAndPred);
    LogUtils.d("TbsWupManager", localStringBuilder.toString());
  }
  
  public void setPreferenceFromTest(boolean paramBoolean)
  {
    updateWupTestEnvFlag("preference", paramBoolean);
  }
  
  public void setPsm(String paramString1, String paramString2, PublicSettingManager paramPublicSettingManager)
  {
    if (StringUtils.isStringEqual(paramString1, "GPU"))
    {
      paramPublicSettingManager.setGPUEnable(StringUtils.isStringEqual(paramString2, "1"));
      return;
    }
    if (StringUtils.isStringEqual(paramString1, "WAP_USE_QPROXY"))
    {
      paramPublicSettingManager.setCanUseQProxyUnderProxy(StringUtils.isStringEqual(paramString2, "1"));
      return;
    }
    if (StringUtils.isStringEqual(paramString1, "DIRECT_USE_ADBLOCK"))
    {
      paramPublicSettingManager.setCanUseAdBlockUnderDirect(StringUtils.isStringEqual(paramString2, "1"));
      return;
    }
    if (StringUtils.isStringEqual(paramString1, "GUESS_YOUR_FAV"))
    {
      paramPublicSettingManager.setIsShowGuessYourFav(StringUtils.isStringEqual(paramString2, "1"));
      return;
    }
    if (StringUtils.isStringEqual(paramString1, "OPEN_WIFI_ENCRYPT"))
    {
      paramPublicSettingManager.setOpenWifiProxyEnableByWUP(StringUtils.isStringEqual(paramString2, "1"));
      return;
    }
    boolean bool2 = StringUtils.isStringEqual(paramString1, "ANDROID_VIDEO_BARRAGE");
    boolean bool1 = true;
    if (bool2)
    {
      putVideoSettingValue("video_danmu_config", paramString2, 1);
      return;
    }
    if (StringUtils.isStringEqual(paramString1, "VIDEO_QQ_SAME_LAYER_BTN_TYPE"))
    {
      putVideoSettingValue("video_qq_domain_same_layer_btn_type", paramString2, 0);
      return;
    }
    if (StringUtils.isStringEqual(paramString1, "VIDEO_SAME_LAYER_BTN_TYPE"))
    {
      putVideoSettingValue("video_same_layer_btn_type", paramString2, 0);
      return;
    }
    if (StringUtils.isStringEqual(paramString1, "VIDEO_HWCODEC_CRASH_TIMES"))
    {
      paramString1 = TbsBaseModuleShell.getContext().getSharedPreferences("qb_video_settings", 0);
      if (paramString1 != null) {
        paramString1.edit().putInt("video_hw_codec_crash_times", StringUtils.parseInt(paramString2, 1)).commit();
      }
    }
    else
    {
      if (StringUtils.isStringEqual(paramString1, "ANDROID_CONVERT_GET_TO_POST"))
      {
        paramPublicSettingManager.setConvertGetToPostLevel(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "KEY_NIGHT_MODE_IN_LONG_PRESS_SWITCH"))
      {
        paramPublicSettingManager.setNightModeInLongPressswitch(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "KEY_SAVE_WEBPAGE_IN_LONG_PRESS_SWITCH"))
      {
        paramPublicSettingManager.setsaveWebPageInLongPressswitch(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "KEY_EXPLORER_CHARACTER"))
      {
        paramPublicSettingManager.setExplorerCharacter(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "TBS_WEBVIEW_SEARCH_ENGINE_URL"))
      {
        paramPublicSettingManager.setTbsWebviewSearchEngineUrl(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "ANDROID_DYNAMIC_CANVAS_GPU"))
      {
        paramPublicSettingManager.setCanUseDynamicCanvasGpu(StringUtils.isStringEqual(paramString2, "1"));
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "DOWLOAD_SIZE_THRESHOLD"))
      {
        paramPublicSettingManager.setDowloadSizeThreshold(StringUtils.parseFloat(paramString2, -1.0F));
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_SAME_LAYER"))
      {
        paramPublicSettingManager.setVideoSameLayer(StringUtils.isStringEqual(paramString2, "1"));
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_PRELOAD"))
      {
        putVideoSettingValue("video_preload_enabled", paramString2, 1);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_TITLE_NO_SHOW_HOST"))
      {
        putVideoSettingValue("video_titile_not_show_host", paramString2, 1);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_MAXBUFFSIZE"))
      {
        putVideoSettingValue("video_maxbuffer_size", paramString2, -1);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_LITE_WND_DONWLOAD_QB"))
      {
        putVideoSettingValue("video_litewnd_download_qb", paramString2, 1);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_FULLSCERRN_SPEEDPLAY_DOWNLOAD_QB"))
      {
        putVideoSettingValue("video_fullscreen_speedplay_download_qb", paramString2, 0);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_MINBUFFSIZE"))
      {
        putVideoSettingValue("video_minbuffer_size", paramString2, -1);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_LITE_WND"))
      {
        putVideoSettingValue("video_litewnd_enabled", paramString2, 1);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_PAGE_RECOMMAND"))
      {
        putVideoSettingValue("video_page_recommand", paramString2, 1);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_PRODUCT_JSAPI"))
      {
        paramPublicSettingManager.setVideoProductJsApi(StringUtils.isStringEqual(paramString2, "1"));
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "ANDROID_INTERCEPT_JSAPI_REQUEST"))
      {
        paramPublicSettingManager.setShouldInterceptJsapiRequest(StringUtils.isStringEqual(paramString2, "1"));
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_OPEN_URL_BY_MINI_QB"))
      {
        putVideoSettingValue("video_open_url_by_miniqb", paramString2, -1);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_OPEN_ADV_URL_BY_MINI_QB"))
      {
        putVideoSettingValue("video_open_adv_url_by_miniqb", paramString2, -1);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_PRODUCT_OPENQB"))
      {
        paramPublicSettingManager.setVideoProductOpenQb(StringUtils.isStringEqual(paramString2, "1"));
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "ANDROID_GPU_RASTERIZATION"))
      {
        paramPublicSettingManager.setAndroidGpuRasterization(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "ANDROID_ACCELERATED_2D_CANVAS"))
      {
        paramPublicSettingManager.setAndroidAccelerated2dCanvas(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "ANDROID_WEBGL"))
      {
        paramPublicSettingManager.setAndroidWebgl(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "ANDROID_UPLOAD_TEXTURE_MODE"))
      {
        paramPublicSettingManager.setAndroidUploadTextureMode(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "ANDROID_ENABLE_QUA2_V3"))
      {
        paramPublicSettingManager.setAndroidEnableQua2_v3(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "ENABLE_QUA"))
      {
        paramPublicSettingManager.setAndroidEnableQua1(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "VIDEO_DEFAULT_SCREEN"))
      {
        paramPublicSettingManager.setVideoIsDefaultFullscreen(StringUtils.isStringEqual(paramString2, "1"));
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "ANDROID_TBS_PAGE_INFO_UPLOAD_MAXCOUNT"))
      {
        paramPublicSettingManager.setTbsPageInfoUploadMaxCount(paramString2);
        return;
      }
      if ((StringUtils.isStringEqual(paramString1, "ANDROID_ENABLE_RSA_AES_ENCRYPT")) && (TextUtils.isEmpty(paramString2))) {}
    }
    for (;;)
    {
      try
      {
        if (Integer.parseInt(paramString2) != 1) {
          break label3483;
        }
        paramPublicSettingManager.setEnableRsaAesEncrypt(bool1);
        return;
      }
      catch (Exception paramString1)
      {
        StringBuilder localStringBuilder;
        return;
      }
      if ((paramString1 != null) && (paramString1.startsWith("TBS_GENERAL_FEATURE_SWITCH_")))
      {
        if (paramString1.toLowerCase().contains("activeqbback_frequency"))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("sKey is ");
          localStringBuilder.append(paramString1);
          localStringBuilder.append(" sValue is ");
          localStringBuilder.append(paramString2);
          localStringBuilder.toString();
          paramPublicSettingManager.setTBSGeneralFeatureSwitch(paramString1.substring(27), paramString2);
          return;
        }
        paramPublicSettingManager.setTBSGeneralFeatureSwitch(paramString1.substring(27), StringUtils.isStringEqual(paramString2, "1"));
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "TBS_DIRECT_ADBLOCK_SWITCHER_LEVEL"))
      {
        paramPublicSettingManager.setDirectAdblockSwitcherLevel(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "TBS_RESET_GUID"))
      {
        paramPublicSettingManager.setMergeQBGuid(StringUtils.isStringEqual(paramString2, "1"));
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "KEY_DOWNLOAD_INTERCEPT_SWITCH"))
      {
        paramPublicSettingManager.setConvertGetDownloadfileinterceptswitch(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "X5_LONG_CLICK_POPUP_SUBTEXT"))
      {
        paramPublicSettingManager.setX5LongClickPopupMenuSubText(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "QB_ICON_TYPE_IN_LONG_CLICK_POPUP"))
      {
        paramPublicSettingManager.setQbIconTypeInLongClick(paramString2);
        return;
      }
      if (StringUtils.isStringEqual(paramString1, "MTT_CORE_PAGE_DURATION"))
      {
        if (!TextUtils.isEmpty(paramString1)) {
          paramPublicSettingManager.setCorePageDurationPrefs(paramString2);
        }
      }
      else
      {
        if (StringUtils.isStringEqual(paramString1, "KEY_DOWNLOAD_INTERCEPT_FILETYPE"))
        {
          paramPublicSettingManager.setDownloadfileinterceptFileType(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_CONF_BROWSER_LIST_DIALOG_STYLE"))
        {
          paramPublicSettingManager.setBrowserListDialogStyle(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "IS_USE_THIRDPARTY_ADRULES"))
        {
          paramPublicSettingManager.setIsUseThirdPartyAdRules(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_NOTIFY_DOWNLOAD_QB_LIMIT"))
        {
          paramPublicSettingManager.setNotifyDownloadQBTimesLimit(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_ENVIRONMENT_TYPE"))
        {
          paramPublicSettingManager.setEnvironmentType(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "TBS_ALLOW_LOADDATA_WITH_CSP"))
        {
          paramPublicSettingManager.setTbsAllowLoaddataWithCSP(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_TBS_HTTPDNS_SERVER_IP"))
        {
          paramPublicSettingManager.setHttpDNSServerIP(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_TBS_HTTPDNS_ENCRYPTKEY"))
        {
          paramPublicSettingManager.setHttpDNSEncryptKey(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_ALLOW_DNS_STORE"))
        {
          paramPublicSettingManager.setAllowDnsStoreEnable(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "ENABLE_LOGS"))
        {
          paramPublicSettingManager.setEnableLogs(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "TBS_RESTRICT_ERRORPAGE_RELOAD_SECOND"))
        {
          paramPublicSettingManager.setRestrictErrorPageReloadSecond(Integer.parseInt(paramString2));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_X5COOKIE_ISOLATION"))
        {
          paramPublicSettingManager.setX5CookieIsolationEnabled(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_CHECK_X5COOKIE_PATH_POLICY_ENABLE"))
        {
          paramPublicSettingManager.setCheckUseX5CookiePathPolicyEnabled(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "INTERCEPT_DIDFAIL_ONPAGEFINISHED"))
        {
          paramPublicSettingManager.setEnableInterceptDidfailPageFinished(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_GIN_JAVA_MAP_ERASE_DISABLE"))
        {
          paramPublicSettingManager.setGinJavaMapEraseDisabled(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_WASM_DISABLE"))
        {
          paramPublicSettingManager.setWasmDisabled(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_QQ_INTERRUPT_NOT_APK"))
        {
          paramPublicSettingManager.setQQInterruptNotApkType(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_QQ_INTERRUPT_APK"))
        {
          paramPublicSettingManager.setQQInterruptApkType(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_SYSTEM_POP_MENU_STYLE"))
        {
          paramPublicSettingManager.setSystemPopMenuStyle(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_LONG_PRESS_TO_QB_POP_MENU_STYLE"))
        {
          paramPublicSettingManager.setLongPressToQBPopMenuStyle(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "KEY_DOWNLOAD_INTERCEPT_TO_QB_POP_MENU_STYLE"))
        {
          paramPublicSettingManager.setDownloadInterceptToQBPopMenuStyle(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "TBS_INFO_UPLOAD_ARGUMENTS"))
        {
          paramPublicSettingManager.setTbsInfoUploadArguments(paramString2);
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "CORE_JSONSIZE_FORPV"))
        {
          paramPublicSettingManager.setJsonSizeForPV(Integer.parseInt(paramString2));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "CORE_UPLOADPV_WUPENABLE"))
        {
          paramPublicSettingManager.setIsUploadPvinWup(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "TBS_BUGLY_ENABLE"))
        {
          paramPublicSettingManager.setBuglyEnable(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "TBS_DOWNLOADINQB_ENABLE"))
        {
          paramPublicSettingManager.setDownloadInQB(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "TBS_DOWNLOAD_IN_TENCENTFILE_ENABLE"))
        {
          paramPublicSettingManager.setDownloadInTencentFile(Integer.parseInt(paramString2));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "TBS_ACCUMULATION_VALUE_PV"))
        {
          paramPublicSettingManager.setAccumulationValuePV(Integer.parseInt(paramString2));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "TBS_ACCUMULATION_VALUE_UB"))
        {
          paramPublicSettingManager.setAccumulationValueUB(Integer.parseInt(paramString2));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "CORE_UPLOADPV_BEACONENABLE"))
        {
          paramPublicSettingManager.setBeaconUploadEnable(StringUtils.isStringEqual(paramString2, "1"));
          return;
        }
        if (StringUtils.isStringEqual(paramString1, "TBS_GAME_FW_LOGIN_URL"))
        {
          if (!TextUtils.isEmpty(paramString2)) {
            paramPublicSettingManager.setTbsGameFwLoginUrl(paramString2);
          }
        }
        else if (StringUtils.isStringEqual(paramString1, "TBS_GAME_FW_PAY_URL"))
        {
          if (!TextUtils.isEmpty(paramString2)) {
            paramPublicSettingManager.setTbsGameFwPayUrl(paramString2);
          }
        }
        else if (StringUtils.isStringEqual(paramString1, "TBS_GAME_FW_SHARE_URL"))
        {
          if (!TextUtils.isEmpty(paramString2)) {
            paramPublicSettingManager.setTbsGameFwShareUrl(paramString2);
          }
        }
        else
        {
          if (StringUtils.isStringEqual(paramString1, "ENABLE_SUBRESOURCE_PERFORMANCE"))
          {
            paramPublicSettingManager.setSubResourcePerformanceEnabled(StringUtils.isStringEqual(paramString2, "1"));
            return;
          }
          if (StringUtils.isStringEqual(paramString1, "SPA_AD_PAGE_REPORT"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setSPAAdPageReportEnable(StringUtils.isStringEqual(paramString2, "1"));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_CLEAR_LOCAL_CACHE"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.handleClearCacheCommand(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "ENABLE_PERSISTENT_SESSION_NEW_TBS3"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setSessionPersistentEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "CRASH_INSPECTION_DISABLE_MAX_TIMES"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setCrashInspectionDisableMaxTimes(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "CRASH_INSPECTION_DISABLE_INTERVAL_HOURS"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setCrashInspectionDisableIntervalHours(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_SHARPP_ENABLED"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setSharpPEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_TPG_ENABLED"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setTpgEnabledType(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_WXPC_ENABLED"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setWXPCEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_AUTO_PAGE_DISCARDING_ENABLED"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setAutoPageDiscardingEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_AUTO_PAGE_RESTORATION_ENABLED"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setAutoPageRestorationEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_SDCARD_DISK_CACHE_ENABLED"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setSdcardDiskCacheEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "DISK_CACHE_SIZE_SWITCH"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setDiskCacheSizeEnable(StringUtils.isStringEqual(paramString2, "1"));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_SHARPP_DEFAULT"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setSharppDefaultEnable("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_WXPC_DEFAULT"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setWXPCDefaultEnable("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_AUTO_PAGE_DISCARDING_DEFAULT"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setAutoPageDiscardingDefaultEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_AUTO_PAGE_RESTORATION_DEFAULT"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setAutoPageRestorationDefaultEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "CRASH_INSPECTION_ENABLED"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setCrashInspectionEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "NEED_PREINIT_X5_CORE"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setNeedPreInitX5Core("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_CONTENT_CACHE_ENABLED"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setContentCacheEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "ANDROID_TBSLOG_ENABLE_KEY"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setTbsLogEnablePrefs(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "ANDROID_TBSLOG_FLUSH_STRATEGY_KEY"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setTbsLogFlushStrategyPrefs(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "ANDROID_TBSLOG_CACHE_STRATEGY_KEY"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setTbsLogCacheStrategyPrefs(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "ANDROID_TBSLOG_REPORT_KEY"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setTbsLogReportPrefs(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_CONNECTION_TIMEOUT"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setConnectionTimeOut(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_IMPATIENT_NETWORK_SWITCH"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setTbsImpatientNetworkSwitch(StringUtils.isStringEqual(paramString2, "1"));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "KEY_FOR_CROWN_THE_GREAT_KING_OF_SIMCARD"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.crownTheGreatKingOfSimCardOrNot(StringUtils.isStringEqual(paramString2, "1"));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "KEY_KC_PROXY_STRAGETY"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setKingCardUserProxyJudgeCondition(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_WIFI_DOWNLOAD_OR_INST_DLG_TYPE"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              try
              {
                int i = Integer.parseInt(paramString2);
                paramPublicSettingManager.setWifiDownloadOrInstDlgType(i);
                if ((i != 0) && (i != 2)) {
                  continue;
                }
                TargetAdapter.setAutoSilentDownloadSwitch(ContextHolder.getAppContext(), false);
                return;
              }
              catch (Exception paramString1)
              {
                paramString1.printStackTrace();
                return;
              }
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_CAN_USE_X5_JSCORE"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setCanUseX5Jscore("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_CAN_USE_X5_JSCORE_NEW_API"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setCanUseX5JscoreNewAPI("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "X5_JSCORE_CAN_USE_BUFFER"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setX5JsCoreBufferSwitch("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "X5JSCORE_USE_DEPRECATED"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setX5JsCoreUseDeprecated("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "ANDROID_PERFORMANCE_FRAME_RECORD"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setFramePerformanceRecordEnable(StringUtils.isStringEqual(paramString2, "1"));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "ANDROID_ADD_SPECIAL_ARGUMENT"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setAddArgumentForImageRequestEnable(StringUtils.isStringEqual(paramString2, "1"));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "WIFI_NOQB_SWITCH_JUMPURL"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setWifiNOQBSwitchJumpUrl(paramString2);
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "TBS_JNI_REGISTER_ENABLED"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setJniRegisterEnabled("1".equalsIgnoreCase(paramString2));
            }
          }
          else if (StringUtils.isStringEqual(paramString1, "QUIC_PROXY_SWITCH"))
          {
            if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.setQuicProxySwitch("1".equalsIgnoreCase(paramString2));
            }
          }
          else if ((!StringUtils.isStringEqual(paramString1, "BUG_WORKAROUND_FORCE_BITMAP_RENDERING")) && (!StringUtils.isStringEqual(paramString1, "BUG_WORKAROUND_EXT_GPU_JSON")) && (!StringUtils.isStringEqual(paramString1, "BUG_WORKAROUND_EXT_SOFT_JSON")) && (!StringUtils.isStringEqual(paramString1, "BUG_WORKAROUND_GPU_BUG_MANAGER")))
          {
            if (StringUtils.isStringEqual(paramString1, "WEBAR_SLAM_HARDWARE_CONFIG"))
            {
              if (!TextUtils.isEmpty(paramString2)) {
                paramPublicSettingManager.putWebARSlamHardwareConfig(paramString2);
              }
            }
            else if ((!StringUtils.isStringEqual(paramString1, "FIRST_SCREEN_DRAW_FULLSCREEN")) && (!StringUtils.isStringEqual(paramString1, "FIRST_SCREEN_DRAW_NOTFULLSCREEN")) && (!StringUtils.isStringEqual(paramString1, "FIRST_SCREEN_X5CORE_DETECT")) && (!StringUtils.isStringEqual(paramString1, "X5_PRO_ENABLED")) && (!StringUtils.isStringEqual(paramString1, "BDSEARCH_PREDICT_ENABLED")) && (!StringUtils.isStringEqual(paramString1, "WEBAR_SLAM_DISABLED")) && (!StringUtils.isStringEqual(paramString1, "WEBAR_SLAM_ENABLED_ON_ALL_DEVICES")) && (!StringUtils.isStringEqual(paramString1, "WEBAR_MARKER_DISABLED")) && (!StringUtils.isStringEqual(paramString1, "WEBAR_MARKER_ENABLED_ON_ALL_DEVICES")) && (!StringUtils.isStringEqual(paramString1, "WEBAR_SLAM_VIO_DISABLED")) && (!StringUtils.isStringEqual(paramString1, "WEBAR_SWITCH_ARCORE_ENABLED")) && (!StringUtils.isStringEqual(paramString1, "WEBAR_SWITCH_SLAM_VIO_ENABLED")) && (!StringUtils.isStringEqual(paramString1, "WEBRTC_PLUGIN_AUTO_DOWNLOAD_NOT_ALLOWED")))
            {
              if (StringUtils.isStringEqual(paramString1, "TBS_MSE_ENABLED"))
              {
                if (!TextUtils.isEmpty(paramString2)) {
                  paramPublicSettingManager.setMSEEnabled("1".equalsIgnoreCase(paramString2));
                }
              }
              else if (StringUtils.isStringEqual(paramString1, "KEY_REPORT_CALL_WEBVIEW_API_ON_WRONG_THREAD"))
              {
                if (!TextUtils.isEmpty(paramString2)) {
                  paramPublicSettingManager.setReportCallWebviewApiOnWrongThreadEnabled("1".equalsIgnoreCase(paramString2));
                }
              }
              else if (StringUtils.isStringEqual(paramString1, "PRERENDER_ENABLE"))
              {
                if (!TextUtils.isEmpty(paramString2)) {
                  paramPublicSettingManager.setPrerenderSwitch(paramString2);
                }
              }
              else
              {
                if (StringUtils.isStringEqual(paramString1, "KEY_CAN_AIA_EXTENSION"))
                {
                  paramPublicSettingManager.setCanAIAExtension(StringUtils.isStringEqual(paramString2, "1"));
                  return;
                }
                if (StringUtils.isStringEqual(paramString1, "ANDROID_USE_SMART_ADFILTER"))
                {
                  paramPublicSettingManager.setUseSmartAdFilter(StringUtils.isStringEqual(paramString2, "1"));
                  return;
                }
                if ((!StringUtils.isStringEqual(paramString1, "gdt_advertisement")) && (!StringUtils.isStringEqual(paramString1, "splice_miniqb_ad")) && (!StringUtils.isStringEqual(paramString1, "bubble_ad")) && (!StringUtils.isStringEqual(paramString1, "splice_ad")) && (!StringUtils.isStringEqual(paramString1, "qbicon_qq_shine")) && (!StringUtils.isStringEqual(paramString1, "bubble_miniqb_ad")) && (!StringUtils.isStringEqual(paramString1, "TBS_AD_SPECIAL_PERMISSION")))
                {
                  if (StringUtils.isStringEqual(paramString1, "PRERENDER_PACKAGE_NAME"))
                  {
                    if (!TextUtils.isEmpty(paramString2)) {
                      paramPublicSettingManager.setPrerenderPkgName(paramString2);
                    }
                  }
                  else if (StringUtils.isStringEqual(paramString1, "TBS_AD_HTTP_PROXY_SWITCH"))
                  {
                    if (!TextUtils.isEmpty(paramString2)) {
                      paramPublicSettingManager.setTbsAdHttpProxySwitch("0".equals(paramString2) ^ true);
                    }
                  }
                  else if (StringUtils.isStringEqual(paramString1, "SPECIALSITE_INSERT_AD_SWITCH"))
                  {
                    if (!TextUtils.isEmpty(paramString2)) {
                      paramPublicSettingManager.setCanInsertAdInSepcialSite("0".equals(paramString2) ^ true);
                    }
                  }
                  else if (((!TextUtils.isEmpty(paramString1)) && (paramString1.endsWith("RATIO_CONTROL"))) || (StringUtils.isStringEqual(paramString1, "TBS_UNION_LAND_PAGE_PARAM")))
                  {
                    if (!TextUtils.isEmpty(paramString2)) {
                      paramPublicSettingManager.putCloudStringSwitch(paramString1, paramString2);
                    }
                  }
                  else
                  {
                    if (StringUtils.isStringEqual(paramString1, "CAN_IPV6_PROXY"))
                    {
                      paramPublicSettingManager.setCanIpv6Proxy(StringUtils.isStringEqual(paramString2, "1"));
                      return;
                    }
                    if ((!StringUtils.isStringEqual(paramString1, "MTT_CORE_CONFIG_RENDER_REPORT_COMMON")) && (!StringUtils.isStringEqual(paramString1, "MTT_CORE_CONFIG_COMPOSITOR_TIMING_INFO")) && (!StringUtils.isStringEqual(paramString1, "MTT_CORE_CONFIG_THREAD_JS_TIMEOUT")) && (!StringUtils.isStringEqual(paramString1, "MTT_CORE_CONFIG_BUSINESS_RESPONSE_TIMING")) && (!StringUtils.isStringEqual(paramString1, "MTT_CORE_CONFIG_EXPERIENCE_FIXED_AD")) && (!StringUtils.isStringEqual(paramString1, "MTT_CORE_CONFIG_EXPERIENCE_CLICK_HIJACK")) && (!StringUtils.isStringEqual(paramString1, "MTT_CORE_CONFIG_RENDER_MEM_INFO")) && (!StringUtils.isStringEqual(paramString1, "WEBPAGE_READ_MODE_CONFIG_ANDROID")) && (!StringUtils.isStringEqual(paramString1, "WEBPAGE_READ_MODE_PRECHECK_ANDROID")) && (!StringUtils.isStringEqual(paramString1, "WEBPAGE_READ_MODE_BOTTOM_OPERATION_ANDROID")))
                    {
                      if (StringUtils.isStringEqual(paramString1, "TBS_FAKE_LOGIN_ENABLED"))
                      {
                        if (!TextUtils.isEmpty(paramString2)) {
                          paramPublicSettingManager.setFakeLoginEnabled("1".equalsIgnoreCase(paramString2));
                        }
                      }
                      else if (!TextUtils.isEmpty(paramString2)) {
                        paramPublicSettingManager.putCloudSwitch(paramString1, paramString2, 0);
                      }
                    }
                    else if (!TextUtils.isEmpty(paramString2)) {
                      paramPublicSettingManager.putCloudStringSwitch(paramString1, paramString2);
                    }
                  }
                }
                else if (!TextUtils.isEmpty(paramString2)) {
                  paramPublicSettingManager.putCloudBoolSwitch(paramString1, StringUtils.isStringEqual(paramString2, "1"));
                }
              }
            }
            else if (!TextUtils.isEmpty(paramString2)) {
              paramPublicSettingManager.putCloudBoolSwitch(paramString1, StringUtils.isStringEqual(paramString2, "1"));
            }
          }
          else if (!TextUtils.isEmpty(paramString2)) {
            paramPublicSettingManager.putGpuBugSwitch(paramString1, paramString2);
          }
        }
      }
      return;
      label3483:
      bool1 = false;
    }
  }
  
  public void setRespondSpacing(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("doMultiWupRequest, set setDomainTime to ");
    localStringBuilder.append(paramInt);
    LogUtils.d("TbsWupManager", localStringBuilder.toString());
    TbsUserInfo.getInstance().setDomainTime(paramInt);
    PublicSettingManager.getInstance().setPreferenceUpdateTime(paramInt);
  }
  
  public boolean shouldCheckBasicConf(byte paramByte)
  {
    long l2 = System.currentTimeMillis();
    long l1;
    if (paramByte == 1) {
      l1 = this.mLastBasicConfMiniQB;
    } else {
      l1 = this.mLastBasicConfTBS;
    }
    long l3 = l2 - l1;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("isBasicConfChecked: last check date:");
    localStringBuilder.append(l1);
    localStringBuilder.append(", curr date:");
    localStringBuilder.append(l2);
    localStringBuilder.append(", time gap=");
    localStringBuilder.append(l3);
    LogUtils.d("TbsWupManager", localStringBuilder.toString());
    if ((l3 < 86400000L) && (l2 >= this.mLastBasicConfTBS))
    {
      LogUtils.d("TbsWupManager", "isBasicConfChecked: time gap less than one day");
      return false;
    }
    LogUtils.d("TbsWupManager", "isBasicConfChecked: time gap bigger than one day");
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TbsWupManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */