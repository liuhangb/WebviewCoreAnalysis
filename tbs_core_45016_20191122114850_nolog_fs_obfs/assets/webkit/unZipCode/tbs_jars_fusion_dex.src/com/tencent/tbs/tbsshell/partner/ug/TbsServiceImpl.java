package com.tencent.tbs.tbsshell.partner.ug;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.MttLoader;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.common.wup.WUPTaskProxy;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebBackForwardList;
import com.tencent.smtt.export.external.interfaces.IX5WebHistoryItem;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.tbs.common.MTT.AesEncryptReq;
import com.tencent.tbs.common.MTT.AesEncryptRsp;
import com.tencent.tbs.common.MTT.ChannelUrlDetectReq;
import com.tencent.tbs.common.MTT.ChannelUrlDetectResp;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.QUAUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.resources.PluginContextWrapper;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.tbsshell.common.DomainMatcher;
import com.tencent.tbs.tbsshell.common.ISmttServiceCommon;
import com.tencent.tbs.tbsshell.common.ISmttServicePartner;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TbsServiceImpl
  implements ITbsService
{
  private static final String PREFERENCES_ITEM_NOTIFY_DOWNLOAD_QB_LIMIT = "notify_download_qb_limit";
  private static final String SHARE_PREFERENCES_NAME = "x5_proxy_setting";
  private static final String TAG = "TbsServiceImpl";
  private Context context;
  private SharedPreferences mPreference;
  private ISmttServicePartner smttServicePartner;
  
  public TbsServiceImpl(Context paramContext, ISmttServicePartner paramISmttServicePartner)
  {
    this.context = paramContext;
    this.smttServicePartner = paramISmttServicePartner;
  }
  
  private <T> T optValue(Map<String, Object> paramMap, String paramString, T paramT)
  {
    if (paramMap != null)
    {
      paramMap = paramMap.get(paramString);
      if (paramMap != null) {
        return paramMap;
      }
    }
    return paramT;
  }
  
  public void addJavascriptInterface(Object paramObject1, Object paramObject2, String paramString)
  {
    ((IX5WebViewBase)paramObject1).addJavascriptInterface(paramObject2, paramString);
  }
  
  public int convertDownloadInterceptthreeswitchLevel()
  {
    return this.smttServicePartner.convertDownloadInterceptthreeswitchLevel();
  }
  
  public void fileInfoDetect(Context paramContext, String paramString, final ValueCallback<String> paramValueCallback)
  {
    paramContext = new ChannelUrlDetectReq();
    paramContext.url = paramString;
    WUPRequest localWUPRequest = new WUPRequest("appdistribution", "dectectChannelUrl");
    localWUPRequest.setEncodeName("UTF-8");
    localWUPRequest.put("req", paramContext);
    localWUPRequest.setNeedEncrypt(true);
    localWUPRequest.setBindObject(paramString);
    localWUPRequest.setRequestCallBack(new IWUPRequestCallBack()
    {
      public void onWUPTaskFail(WUPRequestBase paramAnonymousWUPRequestBase)
      {
        paramValueCallback.onReceiveValue("-2");
      }
      
      public void onWUPTaskSuccess(WUPRequestBase paramAnonymousWUPRequestBase, WUPResponseBase paramAnonymousWUPResponseBase)
      {
        paramAnonymousWUPRequestBase = paramAnonymousWUPResponseBase.get("resp");
        if ((paramAnonymousWUPRequestBase instanceof ChannelUrlDetectResp))
        {
          paramAnonymousWUPRequestBase = (ChannelUrlDetectResp)paramAnonymousWUPRequestBase;
          paramValueCallback.onReceiveValue(paramAnonymousWUPRequestBase.appName);
        }
        paramValueCallback.onReceiveValue("-1");
      }
    });
    if (!WUPTaskProxy.send(localWUPRequest)) {
      paramValueCallback.onReceiveValue("-3");
    }
  }
  
  public String fixIllegalPath(String paramString)
  {
    return FileUtils.fixIllegalPath(paramString);
  }
  
  public Context getAppContext()
  {
    return this.context;
  }
  
  public HashMap<String, Drawable> getBrowserListIcons(Context paramContext, Intent paramIntent, Object paramObject)
  {
    HashMap localHashMap;
    if ((paramContext != null) && ("com.tencent.mm".equals(paramContext.getApplicationInfo().packageName)))
    {
      localHashMap = new HashMap();
      try
      {
        paramContext = paramContext.getPackageManager();
        Object localObject1 = paramContext.queryIntentActivities(paramIntent, 65536);
        paramIntent = ((IX5WebView)paramObject).getWebViewClientExtension();
        paramObject = ((List)localObject1).iterator();
        while (((Iterator)paramObject).hasNext())
        {
          localObject1 = ((ResolveInfo)((Iterator)paramObject).next()).activityInfo.packageName;
          int i = paramContext.getApplicationInfo((String)localObject1, 128).icon;
          Object localObject2 = new Bundle();
          ((Bundle)localObject2).putInt("resourceId", i);
          ((Bundle)localObject2).putString("packageName", (String)localObject1);
          localObject2 = paramIntent.onMiscCallBack("getDrawable", (Bundle)localObject2);
          if ((localObject2 instanceof Drawable)) {
            localHashMap.put(localObject1, (Drawable)localObject2);
          }
        }
        return localHashMap;
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return null;
  }
  
  public List<String> getDownloadInterceptApkBlackList()
  {
    return this.smttServicePartner.getDownloadInterceptApkBlackList();
  }
  
  public List<String> getDownloadInterceptApkWhiteList()
  {
    return this.smttServicePartner.getDownloadInterceptApkWhiteList();
  }
  
  public List<String> getDownloadInterceptFileTypeWhiteList()
  {
    return this.smttServicePartner.getDownloadInterceptFileTypeWhiteList();
  }
  
  public String getDownloadInterceptKeyMessage()
  {
    return this.smttServicePartner.getDownloadInterceptKeyMessage();
  }
  
  public List<String> getDownloadInterceptNoApkBlackList()
  {
    return this.smttServicePartner.getDownloadInterceptNoApkBlackList();
  }
  
  public int getDownloadInterceptToQBPopMenuStyle()
  {
    return this.smttServicePartner.getDownloadInterceptToQBPopMenuStyle();
  }
  
  public String getGUID()
  {
    return GUIDFactory.getInstance().getStrGuid();
  }
  
  public String getHistoryUrl(Object paramObject, int paramInt)
  {
    try
    {
      paramObject = ((IX5WebViewBase)paramObject).copyBackForwardList().getItemAtIndex(paramInt).getUrl();
      return (String)paramObject;
    }
    catch (Exception paramObject)
    {
      for (;;) {}
    }
    return null;
  }
  
  public boolean getInterceptDownloadInQB()
  {
    return this.smttServicePartner.getInterceptDownloadInQB(getAppContext());
  }
  
  public boolean getInterceptDownloadInTencentFile()
  {
    return this.smttServicePartner.getInterceptDownloadInTencentFile(getAppContext());
  }
  
  public String getMiniQbVersionName()
  {
    return this.smttServicePartner.getMiniQbVersionName();
  }
  
  public int getNotifyDownloadQBCount()
  {
    if (this.mPreference == null) {
      this.mPreference = getAppContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    return this.mPreference.getInt("notify_download_qb_limit", 0);
  }
  
  public int getNotifyDownloadQBTimesLimit()
  {
    return this.smttServicePartner.getNotifyDownloadQBTimesLimit();
  }
  
  public String getPluginPath()
  {
    return TbsUgEngine.getInstance().getPluginPath();
  }
  
  public int getQQInterruptApkType()
  {
    return this.smttServicePartner.getQQInterruptApkType();
  }
  
  public int getQQInterruptNotApkType()
  {
    return this.smttServicePartner.getQQInterruptNotApkType();
  }
  
  public String getQUA2_V3()
  {
    return QUAUtils.getQUA2_V3();
  }
  
  public int getTBSGeneralFeatureSwitch(String paramString)
  {
    return TbsBaseModuleShell.getTBSGeneralFeatureSwitch(paramString);
  }
  
  public String getTbsSdkVersion()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(TbsBaseModuleShell.getTesVersionCode());
    localStringBuilder.append("");
    return localStringBuilder.toString();
  }
  
  public String getUrl(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    return ((IX5WebViewBase)paramObject).getUrl();
  }
  
  public String getX5CoreVersion()
  {
    return TbsBaseModuleShell.getX5CoreVersion();
  }
  
  public String guessFileName(String paramString1, String paramString2, String paramString3)
  {
    return this.smttServicePartner.guessFileName(paramString1, paramString2, paramString3);
  }
  
  public boolean isBrowserInstalled()
  {
    return MttLoader.isBrowserInstalled(getAppContext());
  }
  
  public boolean isDebugMiniQBEnv()
  {
    return this.smttServicePartner.isDebugMiniQBEnv();
  }
  
  public boolean isDownloadFileInterceptFileTypeWhiteList(String paramString)
  {
    return this.smttServicePartner.isDownloadFileInterceptFileTypeWhiteList(paramString);
  }
  
  public boolean isDownloadFileInterceptNotAPKDomainBlackList(String paramString)
  {
    return this.smttServicePartner.isDownloadFileInterceptNotAPKDomainBlackList(paramString);
  }
  
  public boolean isDownloadFileInterceptWhiteList(String paramString)
  {
    return this.smttServicePartner.isDownloadFileInterceptWhiteList(paramString);
  }
  
  public boolean isDownloadInterceptSwitchMatch()
  {
    return this.smttServicePartner.isDownloadInterceptSwitchMatch(getAppContext());
  }
  
  public boolean isInMiniProgram(Object paramObject)
  {
    return this.smttServicePartner.isInMiniProgram((IX5WebViewBase)paramObject);
  }
  
  public boolean isInterceptDownloadRequestEnabled()
  {
    return this.smttServicePartner.isInterceptDownloadRequestEnabled(getAppContext());
  }
  
  public boolean isMiniQB(Object paramObject)
  {
    paramObject = ReflectionUtils.invokeInstance(paramObject, "isMiniQB", new Class[0], new Object[0]);
    if ((paramObject instanceof Boolean)) {
      return ((Boolean)paramObject).booleanValue();
    }
    return false;
  }
  
  public boolean isMiniQBDisabled()
  {
    return this.smttServicePartner.isMiniQBDisabled();
  }
  
  public boolean isSafeDownloadInterceptEnabled()
  {
    return this.smttServicePartner.isSafeDownloadInterceptEnabled(getAppContext());
  }
  
  public boolean isSupportingDownloadRefer()
  {
    return MttLoader.isSupportingDownloadRefer(getAppContext());
  }
  
  public void loadUrl(Object paramObject, String paramString)
  {
    ((IX5WebViewBase)paramObject).loadUrl(paramString);
  }
  
  public Context newPluginContextWrapper(Context paramContext, String paramString)
  {
    return new PluginContextWrapper(paramContext, paramString);
  }
  
  public Object onMiscCallback(String paramString, final Map<String, Object> paramMap)
  {
    try
    {
      int i;
      Object localObject;
      if (TextUtils.equals(paramString, "aesEncrypt"))
      {
        paramString = (String)optValue(paramMap, "text", "");
        i = ((Integer)optValue(paramMap, "type", Integer.valueOf(0))).intValue();
        paramMap = (ValueCallback)optValue(paramMap, "callback", new ValueCallback()
        {
          public void onReceiveValue(String[] paramAnonymousArrayOfString) {}
        });
        localObject = new AesEncryptReq();
        ((AesEncryptReq)localObject).type = i;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramString);
        localStringBuilder.append("");
        ((AesEncryptReq)localObject).text = localStringBuilder.toString();
        paramString = new WUPRequest("ugfusionfortbs", "aesEncrypt");
        paramString.setEncodeName("UTF-8");
        paramString.put("req", localObject);
        paramString.setNeedEncrypt(true);
        paramString.setRequestCallBack(new IWUPRequestCallBack()
        {
          public void onWUPTaskFail(WUPRequestBase paramAnonymousWUPRequestBase)
          {
            paramMap.onReceiveValue(new String[] { "-3", "wup fail", "" });
          }
          
          public void onWUPTaskSuccess(WUPRequestBase paramAnonymousWUPRequestBase, WUPResponseBase paramAnonymousWUPResponseBase)
          {
            paramAnonymousWUPRequestBase = paramAnonymousWUPResponseBase.get("rsp");
            if ((paramAnonymousWUPRequestBase instanceof AesEncryptRsp))
            {
              paramAnonymousWUPRequestBase = (AesEncryptRsp)paramAnonymousWUPRequestBase;
              paramMap.onReceiveValue(new String[] { "0", "", paramAnonymousWUPRequestBase.result });
              return;
            }
            paramMap.onReceiveValue(new String[] { "-2", "rsp error", "" });
          }
        });
        if (!WUPTaskProxy.send(paramString))
        {
          paramMap.onReceiveValue(new String[] { "-4", "wup send error", "" });
          return null;
        }
      }
      else
      {
        if (TextUtils.equals(paramString, "getTbsDomainList"))
        {
          i = ((Integer)optValue(paramMap, "type", Integer.valueOf(0))).intValue();
          return TbsDomainListDataProvider.getInstance((byte)0).getDomainList(i);
        }
        if (TextUtils.equals(paramString, "evaluateJavascript"))
        {
          paramString = (IX5WebView)optValue(paramMap, "webView", null);
          localObject = (String)optValue(paramMap, "script", null);
          paramMap = (ValueCallback)optValue(paramMap, "callback", null);
          if ((paramString != null) && (localObject != null) && (paramMap != null))
          {
            paramString.evaluateJavascript((String)localObject, paramMap);
            return Boolean.valueOf(true);
          }
          return Boolean.valueOf(false);
        }
        if (TextUtils.equals(paramString, "isContainsDomain2"))
        {
          paramString = (ArrayList)optValue(paramMap, "hostList", null);
          paramMap = (String)optValue(paramMap, "host", null);
          if ((paramString != null) && (paramMap != null))
          {
            localObject = new DomainMatcher();
            ((DomainMatcher)localObject).clear();
            ((DomainMatcher)localObject).addDomainList(paramString);
            return Boolean.valueOf(((DomainMatcher)localObject).isContainsDomain2(paramMap));
          }
          return Boolean.valueOf(false);
        }
        if (TextUtils.equals(paramString, "getSettingSp"))
        {
          paramString = PublicSettingManager.getInstance().getPreference();
          return paramString;
        }
      }
      return null;
    }
    catch (Exception paramString) {}
    return null;
  }
  
  public void openBrowserListWithGeneralDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap)
  {
    this.smttServicePartner.openBrowserListWithGeneralDownloader(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramMap);
  }
  
  public void openBrowserListWithQBDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap, Bundle paramBundle)
  {
    this.smttServicePartner.openBrowserListWithQBDownloader(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramMap, paramBundle);
  }
  
  public boolean openUrlAndDownloadInQQBrowserWithReport(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    return this.smttServicePartner.openUrlAndDownloadInQQBrowserWithReport(paramContext, paramBoolean, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9);
  }
  
  public boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (!MttLoader.isBrowserInstalled(paramContext)) {
      return false;
    }
    return this.smttServicePartner.openUrlInQQBrowserWithReportAndRecordAnchor(paramContext, paramString1, paramString2, paramString3, paramString4, new Point(0, 0), new Point(0, 0));
  }
  
  public void postForBackgroundTasks(final Runnable paramRunnable)
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        try
        {
          if (paramRunnable != null)
          {
            paramRunnable.run();
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    });
  }
  
  public void pullWupPreferenceByForce()
  {
    ((ISmttServiceCommon)this.smttServicePartner).pullWupPreferenceByForce();
  }
  
  public void removeJavascriptInterface(Object paramObject, String paramString)
  {
    ((IX5WebViewBase)paramObject).removeJavascriptInterface(paramString);
  }
  
  public void setNotifyDownloadQBCount(int paramInt)
  {
    if (this.mPreference == null) {
      this.mPreference = getAppContext().getSharedPreferences("x5_proxy_setting", 0);
    }
    SharedPreferences.Editor localEditor = this.mPreference.edit();
    localEditor.putInt("notify_download_qb_limit", paramInt);
    localEditor.commit();
  }
  
  public void startDownloadInMiniQB(Context paramContext, String paramString1, String paramString2, byte[] paramArrayOfByte, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7)
  {
    this.smttServicePartner.startDownloadInMiniQB(paramContext, paramString1, paramString2, paramArrayOfByte, paramString3, paramString4, paramString5, paramLong, paramString6, paramString7);
  }
  
  public void upLoadToBeacon(String paramString, Map<String, String> paramMap)
  {
    ((ISmttServiceCommon)this.smttServicePartner).upLoadToBeacon(paramString, paramMap);
  }
  
  public void userBehaviorStatistics(String paramString)
  {
    ((ISmttServiceCommon)this.smttServicePartner).userBehaviorStatistics(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\ug\TbsServiceImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */