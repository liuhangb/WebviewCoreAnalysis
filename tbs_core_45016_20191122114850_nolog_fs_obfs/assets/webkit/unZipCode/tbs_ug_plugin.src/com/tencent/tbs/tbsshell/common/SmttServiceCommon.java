package com.tencent.tbs.tbsshell.common;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import android.widget.Toast;
import com.tencent.common.http.Apn;
import com.tencent.common.plugin.IQBPluginCallback;
import com.tencent.common.plugin.PluginSetting;
import com.tencent.common.plugin.QBPluginItemInfo;
import com.tencent.common.plugin.QBPluginServiceImpl;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.Md5Utils;
import com.tencent.common.utils.TbsMode;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.video.export.FeatureSupport;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IH5VideoPlayer;
import com.tencent.mtt.video.export.PlayerEnv;
import com.tencent.mtt.video.export.VideoProxyDefault;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.tbs.common.ar.WEBAR.IWebARCloudRecognitionCallback;
import com.tencent.tbs.common.ar.WEBAR.IWebAREngineCallback;
import com.tencent.tbs.common.ar.WEBAR.IWebARModelCallback;
import com.tencent.tbs.common.ar.WEBAR.WebAREngineManagerService;
import com.tencent.tbs.common.baseinfo.CommonConfigListMangager;
import com.tencent.tbs.common.baseinfo.DomainListDataProvider;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.GodCmdWupManager;
import com.tencent.tbs.common.baseinfo.QAID;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.baseinfo.TuringDID;
import com.tencent.tbs.common.baseinfo.UserInfo;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.lbs.LbsManager;
import com.tencent.tbs.common.net.NetworkDetector;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.task.IQProxyCheckCallBack;
import com.tencent.tbs.common.ui.MttToaster;
import com.tencent.tbs.common.utils.AES256Utils;
import com.tencent.tbs.common.utils.CommonUtils;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.utils.ExtendJSRule;
import com.tencent.tbs.common.utils.NetworkUtils;
import com.tencent.tbs.common.utils.QBUrlUtils;
import com.tencent.tbs.common.utils.TBSIntentUtils;
import com.tencent.tbs.common.utils.TbsFileUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.utils.URLUtil;
import com.tencent.tbs.common.utils.VideoDomainMatcher;
import com.tencent.tbs.tbsshell.TBSShell;
import com.tencent.tbs.tbsshell.WebCoreProxy;
import com.tencent.tbs.tbsshell.common.X5LoadPlugin.IX5LoadPluginCallback;
import com.tencent.tbs.tbsshell.common.X5LoadPlugin.X5LoadPluginManagerService;
import com.tencent.tbs.tbsshell.common.ad.TbsAdProxy;
import com.tencent.tbs.tbsshell.common.ad.TbsAdUserInfoCollector;
import com.tencent.tbs.tbsshell.common.errorpage.IWebErrorPagePluginLoadCallback;
import com.tencent.tbs.tbsshell.common.errorpage.WebErrorPagePluginManager;
import com.tencent.tbs.tbsshell.common.fingersearch.FingerSearch;
import com.tencent.tbs.tbsshell.common.readmode.IWebpageReadModePluginLoadCallback;
import com.tencent.tbs.tbsshell.common.readmode.WebpageReadModePluginManager;
import com.tencent.tbs.tbsshell.common.webrtc.IWebRtcPluginLoadCallback;
import com.tencent.tbs.tbsshell.common.webrtc.WebRtcPluginManager;
import com.tencent.tbs.tbsshell.common.x5core.QProxyDetect;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Vector;
import org.json.JSONObject;

public abstract class SmttServiceCommon
  implements IQProxyCheckCallBack, ISmttServiceCommon
{
  private static final String COMMON_CONFIG_FILE = "debug.conf";
  public static int DNS_RESOLVE_TYPE_HTTP_AES = 2;
  public static int DNS_RESOLVE_TYPE_LOCAL_DNS = 0;
  public static int DNS_RESOLVE_TYPE_UDP = 1;
  protected static int GET_IP_LIST_TRIGGER_MAXCOUNT = 15;
  protected static boolean PROXY_CONTROL_BY_SERVER = true;
  protected static String TAG = "SmttService";
  private static String result_config_forceQProxy;
  protected DomainMatcher directDomainMatcher = new DomainMatcher();
  protected Hashtable<String, String> directDomainTable = null;
  protected DomainMatcher directUploadDomainMatcher = new DomainMatcher();
  protected DomainMatcher directUploadReferDomainMatcher = new DomainMatcher();
  private String entryDataForSearchTeam = "";
  Hashtable<String, String> forceQProxyTable = null;
  protected HttpDnsDomainRequestIdMatcher httpDnsDomainRequestIdMatcher = new HttpDnsDomainRequestIdMatcher();
  protected DomainMatcher httpDnsResolveDomainMatcher = new DomainMatcher();
  protected DomainMatcher httpToHttpsDomainMatcher = new DomainMatcher();
  private boolean isFirstGet = true;
  private boolean isIPV6Checking = false;
  protected boolean isNeedCheckHttpsTunnelProxy = false;
  protected boolean isNeedCheckQProxy = false;
  protected boolean isNeedWIFILogin = false;
  private String lastIPV6ChecktApnType = "";
  protected Context mAppContext = null;
  protected DomainMatcher mAppInSwitchToMiniQBWhiteListMatcher = new DomainMatcher();
  protected DomainMatcher mCanUseQBVideoFloatModeAttrDomainMatcher = new DomainMatcher();
  protected DomainMatcher mContentCacheBusinessWhiteListMatcher = new DomainMatcher();
  protected String mCurrentApnType = "";
  String mCustomQProxy = "";
  protected DomainMatcher mDeeplinkBlackDomainMatcher = new DomainMatcher();
  protected DomainMatcher mDeeplinkWhiteDomainMatcher = new DomainMatcher();
  protected DomainMatcher mDirectAdblockBlackDomainMatcher = new DomainMatcher();
  protected DomainMatcher mDirectAdblockWhiteDomainMatcher = new DomainMatcher();
  protected DomainMatcher mDomDistillWhiteDomainMatcher = new DomainMatcher();
  protected DomainMatcher mDownloadFileIntercepFileTypetWhiteListMatcher = new DomainMatcher();
  protected DomainMatcher mDownloadFileInterceptNotAPKBlackList = new DomainMatcher();
  protected DomainMatcher mDownloadFileInterceptWhiteListMatcher = new DomainMatcher();
  private boolean mEnableLogs = false;
  protected DomainMatcher mExplorerDomainMatcher = new DomainMatcher();
  private ArrayList<String> mFlash2VideoDefaultList = null;
  protected DomainMatcher mFollowReferDirectListMatcher = new DomainMatcher();
  protected DomainMatcher mForceHTTPDirectWhitelist = new DomainMatcher();
  protected DomainMatcher mForceHTTPSDirectWhitelist = new DomainMatcher();
  protected DomainMatcher mForceHTTPSProxyWhitelist = new DomainMatcher();
  protected int mGetIPListCounts = 0;
  protected ArrayList<String> mHeadsupByTBSServer;
  protected DomainMatcher mKingCardDirectUrlDomainList = new DomainMatcher();
  protected ArrayList<String> mKingCardDirectUrlList = new ArrayList();
  String mLastMd5 = "";
  protected DomainMatcher mLbsDontAlertWhiteListMatcher = new DomainMatcher();
  protected DomainMatcher mLocalIpAccessAllowDomainMatcher = new DomainMatcher();
  protected DomainMatcher mLocalIpAccessDenyDomainMatcher = new DomainMatcher();
  protected DomainMatcher mMiniQBTBSIsForceVideoPagePlay = new DomainMatcher();
  protected DomainMatcher mMiniQBVideoSameLayerDomainMatcher = new DomainMatcher();
  protected DomainMatcher mNovelSiteAdFilterBlackListDomainMatcher = new DomainMatcher();
  protected DomainMatcher mNovelSiteAdFilterWhiteListDomainMatcher = new DomainMatcher();
  protected DomainMatcher mPerformanceUploadWhiteList = new DomainMatcher();
  protected DomainMatcher mQuicDirectBlackList = new DomainMatcher();
  protected DomainMatcher mQuicDirectWhiteList = new DomainMatcher();
  protected DomainMatcher mRenderFixedADFilterWhiteListDomainMatcher = new DomainMatcher();
  protected DomainMatcher mRenderReportWhiteListDomainMatcher = new DomainMatcher();
  protected DomainMatcher mReportBadJsDomainMatcher = new DomainMatcher();
  protected DomainMatcher mSPAAdPageReporterWhiteList = new DomainMatcher();
  protected ArrayList<String> mSchemeNotInterceptList;
  protected DomainMatcher mSearchVideoDomainMatcher = new DomainMatcher();
  protected DomainMatcher mSelectedAdFilterWhiteListDomainMatcher = new DomainMatcher();
  protected DomainMatcher mSmartAdFilterDomainMatcher = new DomainMatcher();
  protected DomainMatcher mTBSDirectAdblockEffectWhiteListMatcher = new DomainMatcher();
  protected DomainMatcher mTBSFileChooserBlackDomainMatcher = new DomainMatcher();
  protected DomainMatcher mTbsSwitchToMiniQBWhiteListMatcher = new DomainMatcher();
  protected DomainMatcher mTpgDecBlackList = new DomainMatcher();
  protected DomainMatcher mUseAudioPlayerByClickDomainMatcher = new DomainMatcher();
  protected DomainMatcher mUseAudioPlayerDomainMatcher = new DomainMatcher();
  protected DomainMatcher mVideoCanPagePlayDomainMatcher = new DomainMatcher();
  protected VideoDomainMatcher mVideoSameLayerDomainMatcher = new VideoDomainMatcher();
  protected DomainMatcher mVideoZOrderBlackListDomainMather = new DomainMatcher();
  protected DomainMatcher mWebRTCinCamera1WhiteList = new DomainMatcher();
  protected DomainMatcher mWebpageReadModeBlackListDomainMatcher = new DomainMatcher();
  protected DomainMatcher mWebpageReadModeWhiteListDomainMatcher = new DomainMatcher();
  protected long mWhiteListRefreshTime = -1L;
  protected X5HeaderPrivateKeyDomainMatcher mX5HeaderPrivateKeyDomainMatcher = new X5HeaderPrivateKeyDomainMatcher();
  protected CommonDomainTypeMatcher mX5TBSHeaderDomainTypeMatcher = new CommonDomainTypeMatcher();
  protected DomainMatcher proxyDomainMatcher = new DomainMatcher();
  protected Hashtable<String, String> refuseQProxyTable = null;
  private Boolean searchResultNeedToUpload = Boolean.valueOf(false);
  private String skvDataForSearchTeam = "";
  protected DomainMatcher subResourceDirectDomainMatcher = new DomainMatcher();
  protected DomainMatcher subResourceProxyDomainMatcher = new DomainMatcher();
  protected DomainMatcher udpDnsResolveDomainMatcher = new DomainMatcher();
  
  private void CheckQProxy(boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("tbs-shell.SmttService CheckQProxy isHttpsTunnel=");
    ((StringBuilder)localObject).append(paramBoolean1);
    ((StringBuilder)localObject).append("ipv6Qproxy=");
    ((StringBuilder)localObject).append(paramBoolean2);
    LogUtils.d("x5-ip-list", ((StringBuilder)localObject).toString());
    localObject = getQProxyAddress(Apn.sApnTypeS, paramBoolean1, paramBoolean2);
    if (localObject == null)
    {
      if (paramBoolean2)
      {
        this.isIPV6Checking = false;
        return;
      }
      if (paramBoolean1)
      {
        this.isNeedCheckHttpsTunnelProxy = false;
        return;
      }
      this.isNeedCheckQProxy = false;
      return;
    }
    if (paramBoolean2)
    {
      String str = Apn.getApnNameWithBSSID(Apn.sApnTypeS);
      if ("N/A".equals(str)) {
        Apn.refreshApntype();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("  IPV6   oldApn:");
      localStringBuilder.append(this.lastIPV6ChecktApnType);
      localStringBuilder.append(" newApn:");
      localStringBuilder.append(str);
      LogUtils.d("x5-ip-list", localStringBuilder.toString());
      if (!this.lastIPV6ChecktApnType.equals(str))
      {
        this.lastIPV6ChecktApnType = str;
      }
      else
      {
        this.isIPV6Checking = false;
        this.isNeedCheckQProxy = false;
        return;
      }
    }
    QProxyDetect.getInstance().startQProxyDetectWithSpdy((URL)localObject, this);
  }
  
  private String getCustomQProxy()
  {
    return this.mCustomQProxy;
  }
  
  private ArrayList<String> getDefaultFlash2VideoList()
  {
    try
    {
      if (this.mFlash2VideoDefaultList == null)
      {
        this.mFlash2VideoDefaultList = new ArrayList();
        localObject1 = new String[11];
        int i = 0;
        localObject1[0] = "youku.com";
        localObject1[1] = "letv.com";
        localObject1[2] = "tudou.com";
        localObject1[3] = "sohu.com";
        localObject1[4] = "ku6.com";
        localObject1[5] = "funshion.com";
        localObject1[6] = "91.bestchic.com";
        localObject1[7] = "sexdou.com";
        localObject1[8] = "gantube.com";
        localObject1[9] = "gan91.com";
        localObject1[10] = "gan55.com";
        while (i < localObject1.length)
        {
          this.mFlash2VideoDefaultList.add(localObject1[i]);
          i += 1;
        }
      }
      Object localObject1 = this.mFlash2VideoDefaultList;
      return (ArrayList<String>)localObject1;
    }
    finally {}
  }
  
  private URL getQProxyAddress(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (this.isFirstGet)
    {
      this.isFirstGet = false;
      setNeedCheckQProxy(true, false, true);
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("tbs-shell.SmttService getQProxyAddress  apnType=");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append("isHttpsTunnel= ");
    ((StringBuilder)localObject).append(paramBoolean1);
    ((StringBuilder)localObject).append("ipv6Qproxy= ");
    ((StringBuilder)localObject).append(paramBoolean2);
    LogUtils.d("x5-ip-list", ((StringBuilder)localObject).toString());
    if (!paramBoolean1) {}
    try
    {
      if (isSetedCustomProxy())
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("http://");
        ((StringBuilder)localObject).append(getCustomQProxy());
        return new URL(((StringBuilder)localObject).toString());
      }
      localObject = Apn.getApnNameWithBSSID(paramInt);
      if ("N/A".equals(localObject)) {
        Apn.refreshApntype();
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("    oldApn:");
      localStringBuilder.append(this.mCurrentApnType);
      localStringBuilder.append(" newApn:");
      localStringBuilder.append((String)localObject);
      LogUtils.d("x5-ip-list", localStringBuilder.toString());
      if (!this.mCurrentApnType.equals(localObject))
      {
        this.mCurrentApnType = ((String)localObject);
        NetworkUtils.onIPListChanged(paramInt);
      }
      localObject = TbsUserInfo.getInstance().getQProxyAddress(paramInt, paramBoolean1, paramBoolean2);
      if (localObject == null)
      {
        if (!paramBoolean2)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("getQProxyAddress function is called, but the list is vacant, try to get ip-list asynchronously, apnType=");
          ((StringBuilder)localObject).append(paramInt);
          LogUtils.d("x5-ip-list", ((StringBuilder)localObject).toString());
          TbsUserInfo.getInstance().tryGetIPList(paramInt, false, true, false);
          return null;
        }
      }
      else
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("http://");
        localStringBuilder.append((String)localObject);
        localObject = localStringBuilder.toString();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("SmttService.getQProxyAddress, apnType=");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(", proxy=");
        localStringBuilder.append((String)localObject);
        LogUtils.d("x5-ip-list", localStringBuilder.toString());
        if ((localObject != null) && (((String)localObject).length() > 0))
        {
          localObject = new URL((String)localObject);
          return (URL)localObject;
        }
        return null;
      }
    }
    catch (Exception localException)
    {
      return null;
    }
    return null;
  }
  
  private boolean isHostnameInDirectUploadList(String paramString)
  {
    try
    {
      boolean bool = this.directUploadDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  private boolean isHostnameInDirectUploadReferList(String paramString)
  {
    try
    {
      boolean bool = this.directUploadReferDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  private boolean isHostnameInTBSDirectAdblockEffectWhiteList(String paramString)
  {
    try
    {
      boolean bool = this.mTBSDirectAdblockEffectWhiteListMatcher.isContainsDomain(paramString);
      return bool;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  private boolean isSetedCustomProxy()
  {
    return !getCustomQProxy().equals("");
  }
  
  private boolean isWebRTCinCamera1BlackList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(297);
      if (localArrayList.isEmpty()) {
        return false;
      }
      if (localArrayList.contains("*")) {
        return true;
      }
      return localArrayList.contains(paramString);
    }
    return false;
  }
  
  public void adResearch(boolean paramBoolean, String paramString1, String paramString2)
  {
    Context localContext = this.mAppContext;
    if (localContext != null) {
      TbsAdProxy.getInstance(localContext).adResearch(this.mAppContext, paramBoolean, paramString1, paramString2);
    }
  }
  
  public void addNovelSiteAdFilterBlackListDomaim(String paramString)
  {
    try
    {
      this.mNovelSiteAdFilterBlackListDomainMatcher.addDomain(paramString);
      return;
    }
    catch (Throwable paramString) {}
  }
  
  public void addNovelSiteAdFilterWhiteListDomaim(String paramString)
  {
    try
    {
      this.mNovelSiteAdFilterWhiteListDomainMatcher.addDomain(paramString);
      return;
    }
    catch (Throwable paramString) {}
  }
  
  public boolean canClearDNSCacheGodCmd()
  {
    return GodCmdWupManager.getInstance().canClearDNSCacheGodCmd();
  }
  
  public boolean canInsertAdInSepcialSite()
  {
    return PublicSettingManager.getInstance().canInsertAdInSepcialSite();
  }
  
  public boolean canInsertTbsAdInPage(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    return URLUtil.isUrlMatchDomainList(paramString, 318, false);
  }
  
  public boolean canShowEncouorageWindow()
  {
    String str = PublicSettingManager.getInstance().getCommonConfigListContent(1006);
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("canShowEncouorageWindow ---------------");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).toString();
    return (!TextUtils.isEmpty(str)) && (str.contains(this.mAppContext.getPackageName()));
  }
  
  public boolean canShowScreenAd(Context paramContext, String paramString, IX5WebView paramIX5WebView)
  {
    return TbsAdProxy.getInstance(this.mAppContext).canShowScreenAd(paramContext, paramString, paramIX5WebView);
  }
  
  public boolean canShowScreenAdWhenBackEvent(Context paramContext, String paramString, IX5WebView paramIX5WebView)
  {
    return TbsAdProxy.getInstance(this.mAppContext).canShowScreenAdWhenBackEvent(paramContext, paramString, paramIX5WebView);
  }
  
  public boolean canUseAdBlockWhenNotUsingQProxy()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().canUseAdBlockUnderDirect();
    }
    return false;
  }
  
  public boolean canUseDynamicCanvasGpu()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().canUseDynamicCanvasGpu();
    }
    return false;
  }
  
  public boolean canUseQProxyWhenHasSystemProxy()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().canUseQProxyUnderProxy();
    }
    return false;
  }
  
  public void cancelFingerSearch(Object paramObject, String paramString, boolean paramBoolean)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).cancelFingerSearch(paramString, paramBoolean);
  }
  
  public void checkNeedToUpload(String paramString)
  {
    HashMap localHashMap = new HashMap();
    if ((TbsMode.TBSISQB()) && (this.searchResultNeedToUpload.booleanValue()))
    {
      this.searchResultNeedToUpload = Boolean.valueOf(false);
      localHashMap.put("url", paramString);
      localHashMap.put("skv", this.skvDataForSearchTeam);
      paramString = new StringBuilder();
      paramString.append("");
      paramString.append(System.currentTimeMillis());
      localHashMap.put("uploadTime", paramString.toString());
      X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_SEARCH_RESULT_DETAIL", localHashMap);
    }
  }
  
  public boolean checkUseX5CookiePathPolicyEnabled()
  {
    return PublicSettingManager.getInstance().checkUseX5CookiePathPolicyEnabled();
  }
  
  public void chooseFavorites(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).chooseFavorites(paramString);
  }
  
  public void chooseTranslation(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).chooseTranslation(paramString);
  }
  
  public void cleanPlugins()
  {
    QBPluginServiceImpl.getInstance().cleanPluginData();
    Context localContext = this.mAppContext;
    if (localContext != null) {
      Toast.makeText(localContext, "本地插件列表已删除,正在关闭进程...", 1).show();
    }
    QBPluginServiceImpl.getInstance().forcekillProcess();
  }
  
  public void clearNovelSiteAdFilterWhiteAndBlackListDomaim()
  {
    try
    {
      this.mNovelSiteAdFilterWhiteListDomainMatcher.clear();
      this.mNovelSiteAdFilterBlackListDomainMatcher.clear();
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  public int convertGetToPostLevel()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().convertGetToPostLevel();
    }
    return 0;
  }
  
  @TargetApi(18)
  public void copyWupSwitchesToClipBoard()
  {
    Object localObject1 = PublicSettingManager.getInstance().getPreference();
    if (localObject1 == null)
    {
      Toast.makeText(this.mAppContext, "开关为空,请重新尝试.", 0).show();
      return;
    }
    localObject1 = ((SharedPreferences)localObject1).getAll();
    Object localObject2 = ((Map)localObject1).keySet();
    StringBuilder localStringBuilder = new StringBuilder("geek提供的开关信息:");
    localObject2 = ((Set)localObject2).iterator();
    while (((Iterator)localObject2).hasNext())
    {
      String str = (String)((Iterator)localObject2).next();
      Object localObject3 = ((Map)localObject1).get(str);
      localStringBuilder.append(str);
      localStringBuilder.append("=");
      localStringBuilder.append(String.valueOf(localObject3));
      localStringBuilder.append(", ");
    }
    localStringBuilder.append("开关信息结束");
    if (Build.VERSION.SDK_INT >= 11) {
      ((ClipboardManager)this.mAppContext.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText("WUP_INFO", localStringBuilder));
    } else if (Build.VERSION.SDK_INT < 11) {
      ((ClipboardManager)this.mAppContext.getSystemService("clipboard")).setText(localStringBuilder.toString());
    }
    Toast.makeText(this.mAppContext, "开关信息已经复制到剪切板~", 0).show();
  }
  
  public IH5VideoPlayer createTbsVideoPlayer(Context paramContext, VideoProxyDefault paramVideoProxyDefault, H5VideoInfo paramH5VideoInfo, FeatureSupport paramFeatureSupport, PlayerEnv paramPlayerEnv)
  {
    return null;
  }
  
  public void detectQProxyReachable()
  {
    if (this.mAppContext != null)
    {
      LogUtils.d("NetworkDetector", "smttservice--detectQProxyReachable");
      NetworkDetector.getInstance(this.mAppContext).detectWifiConn(new NetworkDetectorCallbackImpl());
    }
  }
  
  public int directAdblockSwitcherLevel()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getDirectAdblockSwitcherLevel();
    }
    return 0;
  }
  
  public int directAdblockSwitcherLevelForQB()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getDirectAdblockSwitcherLevelForQB();
    }
    return 0;
  }
  
  public void doRecogitionOnCloud(byte[] paramArrayOfByte)
  {
    WebAREngineManagerService.getInstance(this.mAppContext).doRecogitionOnCloud(paramArrayOfByte);
  }
  
  public void enterFingerSearchMode(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).enterFingerSearchMode();
  }
  
  public void enterOtherMode(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).enterOtherMode();
  }
  
  public void executeCopyItem(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).executeCopyItem(paramString);
  }
  
  public void executeDiectConsumptionItems(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).executeDiectConsumptionItems(paramString);
  }
  
  public void executeMoreItem(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).executeMoreItem(paramString);
  }
  
  public void executeSearchItem(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).executeSearchItem(paramString);
  }
  
  public void exitFlushBeacon()
  {
    Context localContext = this.mAppContext;
    if (localContext != null) {
      X5CoreBeaconUploader.getInstance(localContext).savetobeacon();
    }
  }
  
  public void exitX5LongClickPopMenu(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).exitX5LongClickPopMenu();
  }
  
  public void fingerSearchRequest(Object paramObject, String paramString1, int paramInt1, String paramString2, int paramInt2, int[] paramArrayOfInt, String paramString3, String paramString4)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).fingerSearchRequest(paramString1, paramInt1, paramString2, paramInt2, paramArrayOfInt, paramString3, paramString4);
  }
  
  public void generateTbsAdUserInfo(IX5WebView paramIX5WebView, Point paramPoint1, Point paramPoint2)
  {
    TbsAdUserInfoCollector.getInstance().generateTbsAdUserInfo(paramIX5WebView, paramPoint1, paramPoint2);
  }
  
  public List<String> getAccessibilityBlackList()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(168);
  }
  
  public String getAdBlockSourceFilePath()
  {
    if (TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getContext()).exists()) {
      return TbsFileUtils.getAdBlockSourceFile(TbsBaseModuleShell.getContext()).getPath();
    }
    return "";
  }
  
  public int getAndroidAccelerated2dCanvas()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getAndroidAccelerated2dCanvas();
    }
    return 0;
  }
  
  public int getAndroidGpuRasterization()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getAndroidGpuRasterization();
    }
    return 0;
  }
  
  public int getAndroidUploadTextureMode()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getAndroidUploadTextureMode();
    }
    return 0;
  }
  
  public int getAndroidWebgl()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getAndroidWebgl();
    }
    return 0;
  }
  
  public boolean getAutoPageDiscardingDefaultEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getAutoPageDiscardingDefaultEnabled();
    }
    return false;
  }
  
  public boolean getAutoPageDiscardingEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getAutoPageDiscardingEnabled();
    }
    return false;
  }
  
  public boolean getAutoPageRestorationDefaultEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getAutoPageRestorationDefaultEnabled();
    }
    return false;
  }
  
  public boolean getAutoPageRestorationEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getAutoPageRestorationEnabled();
    }
    return false;
  }
  
  public boolean getBFOptEnabled()
  {
    return PublicSettingManager.getInstance().getBFOptEnabled();
  }
  
  public String getBrowserBuildId()
  {
    return "652170";
  }
  
  public String getBrowserVersion()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("6.5 ");
    localStringBuilder.append(TbsInfoUtils.getMode());
    return localStringBuilder.toString();
  }
  
  public Object getCachedImage(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    return WebCoreProxy.getCacheFile(paramString);
  }
  
  public boolean getCanAIAExtension()
  {
    return PublicSettingManager.getInstance().getCanAIAExtension();
  }
  
  public String getCloudStringSwitch(String paramString1, String paramString2)
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getCloudStringSwitch(paramString1, paramString2);
    }
    return paramString2;
  }
  
  public int getCloudSwitch(String paramString, int paramInt)
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getCloudSwitch(paramString, paramInt);
    }
    return paramInt;
  }
  
  public Context getContext()
  {
    return this.mAppContext;
  }
  
  public int getCustomImageQuality()
  {
    return -1;
  }
  
  public String getDeviceName()
  {
    return DeviceUtils.getDeviceName();
  }
  
  public String getDevicePlatform()
  {
    return "Android";
  }
  
  public String getDeviceVersion()
  {
    return Build.VERSION.RELEASE;
  }
  
  public int getDisplayCutoutEnableSwitch()
  {
    return PublicSettingManager.getInstance().getDisplayCutoutEnableSwitch();
  }
  
  public int getDnsResolveTypeForThisDomain(String paramString)
  {
    initDomainList();
    if (this.httpDnsResolveDomainMatcher.isContainsDomain(paramString)) {
      return DNS_RESOLVE_TYPE_HTTP_AES;
    }
    if (this.udpDnsResolveDomainMatcher.isContainsDomain(paramString)) {
      return DNS_RESOLVE_TYPE_UDP;
    }
    return DNS_RESOLVE_TYPE_LOCAL_DNS;
  }
  
  public InputStream getDomDistillerJS()
  {
    Context localContext = this.mAppContext;
    if (localContext == null) {
      return null;
    }
    return TbsAdProxy.getInstance(localContext).getDomDistillerJS();
  }
  
  public List<String> getECommerceFetchjsList()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(278);
  }
  
  public boolean getEnableQua1()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getAndroidEnableQua1() != 0;
    }
    return true;
  }
  
  public String getExtendJsRule(String paramString)
  {
    return ExtendJSRule.getExtendRule(this.mAppContext, paramString);
  }
  
  public String[] getExternalSDcardPathArray()
  {
    Vector localVector = TbsFileUtils.getExternalSDcardPath();
    return (String[])localVector.toArray(new String[localVector.size()]);
  }
  
  public Object getFingerSearchInstance(IX5WebView paramIX5WebView)
  {
    if (paramIX5WebView == null) {
      return null;
    }
    FingerSearch localFingerSearch = new FingerSearch();
    localFingerSearch.build(paramIX5WebView);
    return localFingerSearch;
  }
  
  public boolean getFirstScreenDetectEnable()
  {
    return PublicSettingManager.getInstance().getFirstScreenDetectEnable();
  }
  
  public boolean getFirstScreenDrawFullScreenEnable()
  {
    return PublicSettingManager.getInstance().getFirstScreenDrawFullScreenEnable();
  }
  
  public boolean getFirstScreenDrawNotFullScreenEnable()
  {
    return PublicSettingManager.getInstance().getFirstScreenDrawNotFullScreenEnable();
  }
  
  public boolean getFirstStartDownloadYYBTips()
  {
    return false;
  }
  
  public int getFrameLimitCount(int paramInt)
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getFrameLimitCount(paramInt);
    }
    return paramInt;
  }
  
  public String getGUID()
  {
    return GUIDFactory.getInstance().getStrGuid();
  }
  
  public String getGpuBugWorkAroundSwitch(String paramString)
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getGpuBugSwitch(paramString, null);
    }
    return null;
  }
  
  public Bundle getGuidBundle()
  {
    return GUIDFactory.getInstance().getGuidBundle();
  }
  
  public String getHttpDNSEncryptKey()
  {
    return PublicSettingManager.getInstance().getHttpDNSEncryptKey();
  }
  
  public int getHttpDNSRequestID(String paramString)
  {
    initDomainList();
    return this.httpDnsDomainRequestIdMatcher.getHttpDNSRequestID(paramString);
  }
  
  public String getHttpDNSServerIP()
  {
    return PublicSettingManager.getInstance().getHttpDNSServerIP();
  }
  
  public String getIPListForWupAndQProxy()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append("\r\nWup IP list INFO:\r\n");
    Iterator localIterator = TbsUserInfo.getInstance().getWupProxyList().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(str);
      localStringBuilder2.append("\r\n");
      localStringBuilder1.append(localStringBuilder2.toString());
    }
    localStringBuilder1.append("\r\nQProxy IP list INFO:\r\n");
    localStringBuilder1.append(TbsUserInfo.getInstance().getQProxyListString());
    localStringBuilder1.append("\r\nPrototol Flag list INFO:\r\n");
    localStringBuilder1.append(TbsUserInfo.getInstance().getProtocolListString());
    return localStringBuilder1.toString();
  }
  
  public boolean getIsSchemeCallAppByUser()
  {
    return PublicSettingManager.getInstance().getIsSchemeCallAppByUser();
  }
  
  public boolean getIsUseThirdPartyAdRules()
  {
    return PublicSettingManager.getInstance().getIsUseThirdPartyAdRules();
  }
  
  protected boolean getIsX5ProxyEnabled(boolean paramBoolean)
  {
    Object localObject2 = new BufferedInputStream(null);
    try
    {
      if (result_config_forceQProxy == null)
      {
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(Environment.getExternalStorageDirectory().getAbsolutePath());
        ((StringBuilder)localObject1).append(File.separator);
        ((StringBuilder)localObject1).append("debug.conf");
        localObject1 = new File(((StringBuilder)localObject1).toString());
        if (((File)localObject1).exists())
        {
          BufferedInputStream localBufferedInputStream = new BufferedInputStream(new FileInputStream((File)localObject1));
          try
          {
            localObject1 = new Properties();
            ((Properties)localObject1).load(localBufferedInputStream);
            result_config_forceQProxy = ((Properties)localObject1).getProperty("setting_froceUseQProxy", "");
            localBufferedInputStream.close();
          }
          catch (Exception localException1)
          {
            localObject2 = localBufferedInputStream;
            break label144;
          }
        }
        else
        {
          result_config_forceQProxy = new String("");
        }
      }
    }
    catch (Exception localException2)
    {
      try
      {
        label144:
        ((BufferedInputStream)localObject2).close();
      }
      catch (Exception localException3)
      {
        localException3.printStackTrace();
      }
      localException2.printStackTrace();
    }
    String str = result_config_forceQProxy;
    if ((str != null) && (!"".equals(str))) {
      return Boolean.parseBoolean(result_config_forceQProxy);
    }
    return paramBoolean;
  }
  
  public boolean getJniRegisterEnabled()
  {
    return PublicSettingManager.getInstance().getJniRegisterEnabled();
  }
  
  public String getKingCardToken()
  {
    return TbsUserInfo.getInstance().getHttpsTunnelToken();
  }
  
  public String getLC()
  {
    return TbsInfoUtils.getLC();
  }
  
  public Map<String, byte[]> getLbsHeaders(String paramString)
  {
    return LbsManager.getInstance().getLbsParams(paramString);
  }
  
  public String getLiveLogGodCmd()
  {
    return GodCmdWupManager.getInstance().getLiveLogGodCmd();
  }
  
  public ArrayList<String> getLocalConfigFilePath()
  {
    ArrayList localArrayList = new ArrayList();
    if (TbsFileUtils.getUserInfoFile(TbsBaseModuleShell.getContext()).exists()) {
      try
      {
        localArrayList.add(TbsFileUtils.getUserInfoFile(TbsBaseModuleShell.getContext()).getCanonicalPath());
      }
      catch (IOException localIOException1)
      {
        LogUtils.d(TAG, localIOException1.getMessage());
      }
    }
    if (TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getContext(), TbsBaseModuleShell.REQ_SRC_TBS).exists()) {
      try
      {
        localArrayList.add(TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getContext(), TbsBaseModuleShell.REQ_SRC_TBS).getCanonicalPath());
      }
      catch (IOException localIOException2)
      {
        LogUtils.d(TAG, localIOException2.getMessage());
      }
    }
    if (TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getContext(), TbsBaseModuleShell.REQ_SRC_TBS_VIDEO).exists()) {
      try
      {
        localArrayList.add(TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getContext(), TbsBaseModuleShell.REQ_SRC_TBS_VIDEO).getCanonicalPath());
      }
      catch (IOException localIOException3)
      {
        LogUtils.d(TAG, localIOException3.getMessage());
      }
    }
    if (TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getContext(), TbsBaseModuleShell.REQ_SRC_MINI_QB).exists()) {
      try
      {
        localArrayList.add(TbsFileUtils.getDomainJsonFile(TbsBaseModuleShell.getContext(), TbsBaseModuleShell.REQ_SRC_MINI_QB).getCanonicalPath());
        return localArrayList;
      }
      catch (IOException localIOException4)
      {
        LogUtils.d(TAG, localIOException4.getMessage());
      }
    }
    return localArrayList;
  }
  
  public byte[] getLocalPluginInfo()
  {
    for (;;)
    {
      try
      {
        if (TbsMode.TBSISQB())
        {
          i = 1;
          Object localObject = QBPluginServiceImpl.getInstance().pluginsToJson(i);
          ((JSONObject)localObject).put("serLastMd5", PluginSetting.getInstance(this.mAppContext).pluginListRspMD5(i));
          localObject = AES256Utils.encrypt(((JSONObject)localObject).toString().getBytes("utf-8"), AES256Utils.generateKey());
          return (byte[])localObject;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
      int i = 2;
    }
  }
  
  public String getLocalPluginPath()
  {
    return QBPluginServiceImpl.getInstance().getLocalPluinName();
  }
  
  public String[] getLoginUserInfo()
  {
    return null;
  }
  
  public boolean getMSEEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getMSEEnabled();
    }
    return true;
  }
  
  public int getMaxReportNumber()
  {
    return TbsUserInfo.getInstance().mMaxReportNumber;
  }
  
  public String getMd5GUID()
  {
    try
    {
      if (!TextUtils.isEmpty(GUIDFactory.getInstance().getStrGuid()))
      {
        Object localObject = ByteUtils.hexStringToByte(Md5Utils.getMD5(GUIDFactory.getInstance().getStrGuid()));
        byte[] arrayOfByte = new byte[localObject.length / 2];
        int i = 0;
        while (i < arrayOfByte.length)
        {
          arrayOfByte[i] = ((byte)(localObject[i] + localObject[(arrayOfByte.length + i)]));
          i += 1;
        }
        localObject = ByteUtils.byteToHexString(arrayOfByte);
        return (String)localObject;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }
  
  public int getNativeOptSwitches()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getCloudSwitch("NATIVE_OPT_SWITCHES", 1);
    }
    return 1;
  }
  
  public boolean getNeedWIFILogin()
  {
    return this.isNeedWIFILogin;
  }
  
  public String getOAID()
  {
    return TuringDID.getInstance().getOAID();
  }
  
  public boolean getOpenWifiProxyEnable()
  {
    return false;
  }
  
  public String getPasswordKey()
  {
    return AES256Utils.generateKey();
  }
  
  @SuppressLint({"ShowToast"})
  public void getPluginForce(final Context paramContext)
  {
    try
    {
      paramContext = new IQBPluginCallback()
      {
        public IBinder asBinder()
        {
          return null;
        }
        
        public void onGetPluginListFailed(String paramAnonymousString, int paramAnonymousInt)
          throws RemoteException
        {
          Toast.makeText(paramContext, "拉取插件列表:失败！", 1).show();
        }
        
        public void onGetPluginListSucc(String paramAnonymousString, int paramAnonymousInt)
          throws RemoteException
        {
          if (!SmttServiceCommon.this.mLastMd5.equalsIgnoreCase(PluginSetting.getInstance(SmttServiceCommon.this.mAppContext).pluginListRspMD5(paramAnonymousInt)))
          {
            Toast.makeText(paramContext, "拉取插件列表已成功,正在关闭进程...", 1).show();
            QBPluginServiceImpl.getInstance().forcekillProcess();
            return;
          }
          Toast.makeText(paramContext, "请求拉取插件列表无更新！", 1).show();
        }
        
        public void onPluginDownloadCreated(String paramAnonymousString1, String paramAnonymousString2, int paramAnonymousInt1, int paramAnonymousInt2)
          throws RemoteException
        {}
        
        public void onPluginDownloadFailed(String paramAnonymousString1, String paramAnonymousString2, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
          throws RemoteException
        {}
        
        public void onPluginDownloadProgress(String paramAnonymousString1, String paramAnonymousString2, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
          throws RemoteException
        {}
        
        public void onPluginDownloadStarted(String paramAnonymousString1, String paramAnonymousString2, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4)
          throws RemoteException
        {}
        
        public void onPluginDownloadSuccessed(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, boolean paramAnonymousBoolean)
          throws RemoteException
        {}
        
        public void onPluginInstallFailed(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2)
          throws RemoteException
        {}
        
        public void onPluginInstallSuccessed(String paramAnonymousString, QBPluginItemInfo paramAnonymousQBPluginItemInfo, int paramAnonymousInt1, int paramAnonymousInt2)
          throws RemoteException
        {}
        
        public void onPluginSystemInit(boolean paramAnonymousBoolean, int paramAnonymousInt)
          throws RemoteException
        {}
      };
      if (TbsMode.TBSISQB())
      {
        this.mLastMd5 = PluginSetting.getInstance(this.mAppContext).pluginListRspMD5(1);
        QBPluginServiceImpl.getInstance().setPluginCallback(paramContext, 1);
        QBPluginServiceImpl.getInstance().forcePullPluginList(1);
        return;
      }
      this.mLastMd5 = PluginSetting.getInstance(this.mAppContext).pluginListRspMD5(2);
      QBPluginServiceImpl.getInstance().setPluginCallback(paramContext, 2);
      QBPluginServiceImpl.getInstance().forcePullPluginList(2);
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public String getPrerenderPkgName()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getPrerenderPkgName();
    }
    return "";
  }
  
  public int getPrerenderSwitch()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getPrerenderSwitch();
    }
    return -1;
  }
  
  public int getProxyAddressType()
  {
    return TbsUserInfo.getInstance().getProxyAddressType();
  }
  
  public String getQAID()
  {
    return QAID.getInstance().getQAID();
  }
  
  public String getQAuth()
  {
    return UserInfo.getInstance().getQAuth();
  }
  
  public String getQIMEI()
  {
    return X5CoreBeaconUploader.getQIMEI();
  }
  
  public String getQProxyAddressInStringFormat(int paramInt, boolean paramBoolean)
  {
    LogUtils.d("x5-ip-list", "tbs-shell.SmttService getQProxyAddressInStringFormat");
    WebCoreProxy.traceBegin(2, "SmttService.getQProxyAddressInStringFormat");
    URL localURL = getQProxyAddress(paramInt, paramBoolean, false);
    if (localURL != null)
    {
      WebCoreProxy.traceEnd(2, "SmttService.getQProxyAddressInStringFormat");
      return localURL.toString();
    }
    WebCoreProxy.traceEnd(2, "SmttService.getQProxyAddressInStringFormat");
    return null;
  }
  
  public ArrayList<String> getQProxyAddressList()
  {
    for (;;)
    {
      try
      {
        i = Apn.sApnTypeS;
        localObject = Apn.getApnNameWithBSSID(i);
        if (!this.mCurrentApnType.equals(localObject))
        {
          this.mCurrentApnType = ((String)localObject);
          NetworkUtils.onIPListChanged(i);
        }
        localObject = TbsUserInfo.getInstance().getQProxyAddressList(i);
        if (localObject == null) {}
      }
      catch (Exception localException1)
      {
        int i;
        Object localObject;
        return null;
      }
      try
      {
        if (((ArrayList)localObject).size() >= 1) {
          return localObject;
        }
        TbsUserInfo.getInstance().tryGetIPList(i, false, false, false);
        return null;
      }
      catch (Exception localException2) {}
    }
    return (ArrayList<String>)localObject;
    return localException1;
  }
  
  public int getQProxyUsingFlag(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, byte paramByte, String paramString2, boolean paramBoolean4)
  {
    return 0;
  }
  
  public String getQUA(boolean paramBoolean, String paramString1, String paramString2)
  {
    return TbsInfoUtils.getQUA();
  }
  
  public String getQUA2()
  {
    return TbsInfoUtils.getQUA2();
  }
  
  public boolean getQuicProxySwitch()
  {
    return PublicSettingManager.getInstance().getQuicProxySwitch();
  }
  
  public int getRedirectCountLimit()
  {
    return PublicSettingManager.getInstance().getRedirectCountLimit();
  }
  
  public long getRemoteServerTimeStamp()
  {
    initDomainList();
    return this.mWhiteListRefreshTime;
  }
  
  public boolean getSPAAdPageReportSwitch()
  {
    return PublicSettingManager.getInstance().getSPAAdPageReportEnable();
  }
  
  public List<String> getSWPresetWhiteList()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(137);
  }
  
  public List<String> getSWUpdateWhiteList()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(138);
  }
  
  public Bundle getSecurityLevel(String paramString)
  {
    return null;
  }
  
  public int getServerAllowQProxyStatus()
  {
    return -1;
  }
  
  public String[] getSessionPersistentSupportedDomains()
  {
    ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(147);
    if (localArrayList.size() == 0) {
      return null;
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public boolean getSharpDefaultEnable()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getSharpDefaultEnable();
    }
    return false;
  }
  
  public boolean getSharpEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getSharpEnabled();
    }
    return false;
  }
  
  public long getSntpTime()
  {
    return PublicSettingManager.getInstance().getSntpTime();
  }
  
  public String getSpecialSiteReportRatioInterval()
  {
    return PublicSettingManager.getInstance().getCloudStringSwitch("SPECIAL_SITE_REPORT_RATIO_CONTROL", "[1,0]");
  }
  
  public String getSpyFinanceAdRatioInterval()
  {
    return PublicSettingManager.getInstance().getCloudStringSwitch("SPY_AD_AFTER_WECHAT_PAY_RATIO_CONTROL", "[1,0]");
  }
  
  public boolean getSubResourcePerformanceEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getSubResourcePerformanceEnabled();
    }
    return false;
  }
  
  public String getSubsetAdRuleFilePath()
  {
    if (TbsFileUtils.getSubsetAdRuleFile(TbsBaseModuleShell.getContext()).exists()) {
      return TbsFileUtils.getSubsetAdRuleFile(TbsBaseModuleShell.getContext()).getPath();
    }
    return "";
  }
  
  public String getSwitchUA(String paramString, int[] paramArrayOfInt)
  {
    return null;
  }
  
  public String getTAID()
  {
    return TuringDID.getInstance().getTAID();
  }
  
  public String getTbsAdAppInstallCheckRatioInterval()
  {
    return PublicSettingManager.getInstance().getCloudStringSwitch("TBS_AD_APP_INSTALL_CHECK_RATIO_CONTROL", "[1,0]");
  }
  
  public String getTbsAdCompetitorReportRatioInterval()
  {
    return PublicSettingManager.getInstance().getCloudStringSwitch("TBS_AD_COMPETITOR_REPORT_RATIO_CONTROL", "[1,0]");
  }
  
  public ArrayList<String> getTbsAdCompetitors()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(314);
  }
  
  public void getTbsAdDebugWhiteList()
  {
    CommonConfigListMangager.getInstance().requestCommonConfigList(false, 1003, "");
  }
  
  public String getTbsAdStatsRatioInterval()
  {
    return PublicSettingManager.getInstance().getCloudStringSwitch("TBS_AD_STATS_RATIO_CONTROL", "[1,0]");
  }
  
  public ArrayList<String> getTbsAdUrl()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(288);
  }
  
  public String getTbsAdUserInfo(IX5WebView paramIX5WebView, String paramString, Point paramPoint1, Point paramPoint2)
  {
    return TbsAdUserInfoCollector.getInstance().getTbsAdUserInfo(paramIX5WebView, paramString, paramPoint1, paramPoint2);
  }
  
  public InputStream getTbsAutoInsertAdJS()
  {
    Context localContext = this.mAppContext;
    if (localContext == null) {
      return null;
    }
    return TbsAdProxy.getInstance(localContext).getTbsAutoInsertAdJS();
  }
  
  public boolean getTbsImpatientNetworkEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getTbsImpatientNetworkSwitch();
    }
    return false;
  }
  
  public String getTbsUserinfoLiveLog()
  {
    return TbsUserInfo.getInstance().getUserinfoLivelogStr();
  }
  
  public String getTbsWebviewSearchEngineUrl()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getTbsWebviewSearchEngineUrl();
    }
    return "";
  }
  
  public int getTpgEnabledType()
  {
    return PublicSettingManager.getInstance().getTpgEnabledType();
  }
  
  public String[] getUserBehaviourDomains()
  {
    ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(150);
    if (localArrayList.size() == 0) {
      return null;
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public List<String> getVideoSniffList()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(64);
  }
  
  public HashMap<String, String> getWeChatPayServiceProviderSpiedInfoMap()
  {
    Object localObject = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(343);
    if (((ArrayList)localObject).isEmpty()) {
      return null;
    }
    HashMap localHashMap = new HashMap();
    localObject = ((ArrayList)localObject).iterator();
    while (((Iterator)localObject).hasNext())
    {
      String str = (String)((Iterator)localObject).next();
      if ((!TextUtils.isEmpty(str)) && (str.contains("|")))
      {
        int i = str.indexOf("|");
        localHashMap.put(str.substring(0, i), str.substring(i + 1));
      }
    }
    return localHashMap;
  }
  
  public String getWebARSlamHardwareConfig()
  {
    return PublicSettingManager.getInstance().getWebARSlamHardwareConfig();
  }
  
  public String getWhiteListInfo()
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("ClientWhiteListRefreshTime : ");
    ((StringBuilder)localObject).append(new Date(this.mWhiteListRefreshTime * 1000L).toString());
    ((StringBuilder)localObject).append("(");
    ((StringBuilder)localObject).append(this.mWhiteListRefreshTime);
    ((StringBuilder)localObject).append(")\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<DirectDomain WhiteList:>>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.directDomainMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<HttpToHttps WhiteList:>>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.httpToHttpsDomainMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<HttpDns WhiteList:>>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.httpDnsResolveDomainMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<UdpDns WhiteList:>>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.udpDnsResolveDomainMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<ProxyDomain WhiteList:>>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.proxyDomainMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<DirectUploadDomain WhiteList:>>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.directUploadDomainMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<DirectAdblockReport WhiteList:>>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.mTBSDirectAdblockEffectWhiteListMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<QProxyBlackUrl BlackList:>>>>>>>>>>\r\n");
    localStringBuilder1.append("\r\n<<<<<<<<<<<subResourceProxyDomain WhiteList:>>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.subResourceProxyDomainMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<subResourceDirectDomain WhiteList:>>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.subResourceDirectDomainMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<FollowReferDirectListMatcher WhiteList:>>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.mFollowReferDirectListMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<PerformanceUpload ListMatcher WhiteList:  >>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.mPerformanceUploadWhiteList.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<ForceHTTPDirectWhitelist WhiteList:  >>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.mForceHTTPDirectWhitelist.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<ForceHTTPSDirectWhitelist WhiteList:  >>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.mForceHTTPSDirectWhitelist.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<ForceHTTPSProxyWhitelist WhiteList:  >>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.mForceHTTPSProxyWhitelist.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<SmartAdFilterDomainMatcher WhiteList:  >>>>>>>>>>");
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.mSmartAdFilterDomainMatcher.toString());
    ((StringBuilder)localObject).append("\r\n");
    localStringBuilder1.append(((StringBuilder)localObject).toString());
    localStringBuilder1.append("\r\n<<<<<<<<<<<mKingCardDirectUrlList WhiteList:  >>>>>>>>>>");
    int i;
    if (this.mKingCardDirectUrlList != null) {
      i = 0;
    }
    try
    {
      while (i < this.mKingCardDirectUrlList.size())
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append((String)this.mKingCardDirectUrlList.get(i));
        ((StringBuilder)localObject).append("\t,");
        localStringBuilder1.append(((StringBuilder)localObject).toString());
        i += 1;
      }
      localStringBuilder1.append("\r\n");
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    localStringBuilder1.append("\r\n<<<<<<<<<<<refuseQProxyTable WhiteList:  >>>>>>>>>>");
    localObject = this.refuseQProxyTable;
    if (localObject != null)
    {
      localObject = ((Hashtable)localObject).keySet();
      if (localObject != null)
      {
        localObject = ((Set)localObject).iterator();
        while (((Iterator)localObject).hasNext())
        {
          String str = (String)((Iterator)localObject).next();
          StringBuilder localStringBuilder2 = new StringBuilder();
          localStringBuilder2.append(str);
          localStringBuilder2.append("\t,");
          localStringBuilder1.append(localStringBuilder2.toString());
        }
        localStringBuilder1.append("\r\n");
      }
    }
    return localStringBuilder1.toString();
  }
  
  public String getWupAddressByForce()
  {
    return null;
  }
  
  public String getX5CoreVersion()
  {
    return TBSShell.getTbsCoreVersionCode();
  }
  
  public String getXTbsKeyPrivateKey()
  {
    try
    {
      initDomainList();
      String str = this.mX5HeaderPrivateKeyDomainMatcher.getXTbsKeyPrivateKey();
      return str;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    return "";
  }
  
  public void hideScreenAd(IX5WebView paramIX5WebView)
  {
    Context localContext = this.mAppContext;
    if (localContext != null) {
      TbsAdProxy.getInstance(localContext).hideScreenAd(this.mAppContext, paramIX5WebView);
    }
  }
  
  public void initDomainList() {}
  
  public boolean isAllowDnsStoreEnable()
  {
    return PublicSettingManager.getInstance().isAllowDnsStoreEnable();
  }
  
  public boolean isAllowLocalAddrAccess(String paramString)
  {
    paramString = CommonUtils.findFullHost(paramString);
    try
    {
      if ((!"localhost".equalsIgnoreCase(paramString)) && (!"127.0.0.1".equalsIgnoreCase(paramString)) && (!"[::1]".equals(paramString)))
      {
        if ("0:0:0:0:0:0:0:1".equals(paramString)) {
          return true;
        }
        initDomainList();
        try
        {
          boolean bool = this.mLocalIpAccessAllowDomainMatcher.isContainsDomain(paramString);
          return bool;
        }
        finally {}
      }
      return true;
    }
    catch (Throwable paramString) {}
    return true;
  }
  
  public boolean isAllowQHead()
  {
    return false;
  }
  
  public boolean isAllowReportBadJs(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    try
    {
      initDomainList();
      boolean bool = this.mReportBadJsDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString) {}
    return false;
  }
  
  public boolean isAutoSwitchARCoreBlackList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      Iterator localIterator = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(320).iterator();
      while (localIterator.hasNext()) {
        if (((String)localIterator.next()).startsWith(paramString)) {
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  public boolean isAutoSwitchARCoreDeviceWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(328);
      if (localArrayList.isEmpty()) {
        return false;
      }
      return localArrayList.contains(paramString);
    }
    return false;
  }
  
  public boolean isAutoSwitchARCoreEnabled()
  {
    return PublicSettingManager.getInstance().getAutoSwitchARCoreEnabled();
  }
  
  public boolean isAutoSwitchARCoreWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      Iterator localIterator = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(319).iterator();
      while (localIterator.hasNext()) {
        if (((String)localIterator.next()).startsWith(paramString)) {
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  public boolean isAutoSwitchSlamVioBlackList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      Iterator localIterator = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(322).iterator();
      while (localIterator.hasNext()) {
        if (((String)localIterator.next()).startsWith(paramString)) {
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  public boolean isAutoSwitchSlamVioEnabled()
  {
    return PublicSettingManager.getInstance().getAutoSwitchSlamVioEnabled();
  }
  
  public boolean isAutoSwitchSlamVioWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      Iterator localIterator = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(321).iterator();
      while (localIterator.hasNext()) {
        if (((String)localIterator.next()).startsWith(paramString)) {
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  public boolean isBubbleAdEnabled()
  {
    return PublicSettingManager.getInstance().isBubbleAdEnabled();
  }
  
  public boolean isBubbleMiniQbAdEnabled()
  {
    return PublicSettingManager.getInstance().isBubbleMiniQbAdEnabled();
  }
  
  public boolean isCallMode()
  {
    return false;
  }
  
  public boolean isCodeCacheEnable()
  {
    return PublicSettingManager.getInstance().getCloudSwitch("CODE_CACHE_ENABLE_FOR_JSCORE", 0) == 1;
  }
  
  public boolean isDebugWupEnvironment()
  {
    return TextUtils.isEmpty(PublicSettingManager.getInstance().getCustomedWupAddress()) ^ true;
  }
  
  public boolean isDefaultVideoFullScreenPlay(boolean paramBoolean)
  {
    return false;
  }
  
  public boolean isEnableAutoRemoveAds()
  {
    return true;
  }
  
  public boolean isEnableInterceptDidfailPageFinished()
  {
    return PublicSettingManager.getInstance().isEnableInterceptDidfailPageFinished();
  }
  
  public boolean isEnableLogs()
  {
    if (this.mAppContext != null) {
      this.mEnableLogs = PublicSettingManager.getInstance().getEnableLogs();
    }
    return this.mEnableLogs;
  }
  
  public boolean isEnablePreConn()
  {
    return true;
  }
  
  public boolean isEnableTbsARPage(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return true;
      }
      return DomainListDataProvider.getInstance().isUrlInDomainList(TbsBaseModuleShell.REQ_SRC_TBS, 181, paramString) ^ true;
    }
    return true;
  }
  
  public boolean isEnableVideoSameLayer(String paramString, boolean paramBoolean)
  {
    return false;
  }
  
  public int isEnableX5TBSHeaderType(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return -1;
    }
    try
    {
      initDomainList();
      int i = this.mX5TBSHeaderDomainTypeMatcher.getTypeFromDomain(paramString);
      return i;
    }
    catch (Throwable paramString) {}
    return -1;
  }
  
  public boolean isForceVideoPagePlay(String paramString, boolean paramBoolean)
  {
    return false;
  }
  
  public boolean isGdtAdvertisementEnabled()
  {
    return PublicSettingManager.getInstance().isGdtAdvertisementEnabled();
  }
  
  public boolean isHostInTbsAdAppInstallCheckWhitelist(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(329);
    if (localArrayList.isEmpty()) {
      return false;
    }
    DomainMatcher localDomainMatcher = new DomainMatcher();
    localDomainMatcher.addDomainList(localArrayList);
    return localDomainMatcher.isContainsDomain(paramString);
  }
  
  public boolean isHostInTbsAdWhitelist(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(287);
    if (localArrayList.isEmpty()) {
      return false;
    }
    DomainMatcher localDomainMatcher = new DomainMatcher();
    localDomainMatcher.addDomainList(localArrayList);
    return localDomainMatcher.isContainsDomain(paramString);
  }
  
  public boolean isHostInTbsJsAdReportWhitelist(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(315);
    if (localArrayList.isEmpty()) {
      return false;
    }
    DomainMatcher localDomainMatcher = new DomainMatcher();
    localDomainMatcher.addDomainList(localArrayList);
    return localDomainMatcher.isContainsDomain(paramString);
  }
  
  public boolean isHostNameInAdblockBlackList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mDirectAdblockBlackDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return true;
  }
  
  public boolean isHostNameInAdblockWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mDirectAdblockWhiteDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isHostNameInSPAAdPageReportWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mSPAAdPageReporterWhiteList.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  protected boolean isHostnameInDirectList(String paramString)
  {
    try
    {
      boolean bool = this.directDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  protected boolean isHostnameInQProxyWhiteList(String paramString)
  {
    try
    {
      boolean bool = this.proxyDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public boolean isInDebugTbsAdWhiteList()
  {
    String str = PublicSettingManager.getInstance().getCommonConfigListContent(1003);
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("TbsAdWhiteList isInDebugTbsAdWhiteList guids:");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).toString();
    return (!TextUtils.isEmpty(str)) && (str.contains(getGUID()));
  }
  
  public boolean isInNovelSiteAdFilterBlackList(String paramString)
  {
    try
    {
      boolean bool = this.mNovelSiteAdFilterBlackListDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isInNovelSiteAdFilterWhiteList(String paramString)
  {
    try
    {
      boolean bool = this.mNovelSiteAdFilterWhiteListDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isInQuicDirectBlacklist(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    paramString = UrlUtils.getHost(paramString);
    try
    {
      initDomainList();
      boolean bool = this.mQuicDirectBlackList.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString) {}
    return false;
  }
  
  public boolean isInRenderFixedADFilterDomainWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mRenderFixedADFilterWhiteListDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isInRenderHijackDomainWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(362);
      if (localArrayList.isEmpty()) {
        return false;
      }
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isInRenderReportDomainWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mRenderReportWhiteListDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isInSelectedAdFilterWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mSelectedAdFilterWhiteListDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isInTBSFileChooserBlackDomainList(String paramString)
  {
    paramString = CommonUtils.findFullHost(paramString);
    try
    {
      initDomainList();
      try
      {
        boolean bool = this.mTBSFileChooserBlackDomainMatcher.isContainsDomain(paramString);
        return bool;
      }
      finally {}
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isInWebpageReadModeBlackList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mWebpageReadModeBlackListDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isInWebpageReadModeWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mWebpageReadModeWhiteListDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isKingCardUser()
  {
    return TbsUserInfo.getInstance().isGreatKingCard();
  }
  
  public boolean isLbsDontAlertWhiteList(String paramString)
  {
    try
    {
      paramString = CommonUtils.findFullHost(paramString);
      initDomainList();
      boolean bool = this.mLbsDontAlertWhiteListMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isMSEBlackList(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(317);
    if (localArrayList.isEmpty()) {
      return false;
    }
    DomainMatcher localDomainMatcher = new DomainMatcher();
    localDomainMatcher.addDomainList(localArrayList);
    return localDomainMatcher.isContainsDomain(paramString);
  }
  
  public boolean isMediaCodecBlackList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(299);
      if (localArrayList.isEmpty()) {
        return false;
      }
      if (localArrayList.contains("*")) {
        return false;
      }
      return localArrayList.contains(paramString);
    }
    return false;
  }
  
  public boolean isNativeBufferEnable()
  {
    return PublicSettingManager.getInstance().getX5JsCoreBufferSwitch();
  }
  
  public boolean isNeedQHead(String paramString)
  {
    return QBUrlUtils.isQQDomain(paramString);
  }
  
  public boolean isOutFrequencyControl(Context paramContext, String paramString, IX5WebView paramIX5WebView)
  {
    return TbsAdProxy.getInstance(this.mAppContext).isOutFrequencyControl(paramContext, paramString, paramIX5WebView);
  }
  
  public boolean isPluginSupported(String paramString1, String paramString2, String paramString3)
  {
    String str = paramString1;
    if (paramString2 != null) {
      if (!paramString2.contains("://")) {
        str = paramString1;
      } else {
        str = paramString2;
      }
    }
    boolean bool = false;
    if (str != null)
    {
      paramString1 = str.toLowerCase();
      if ((!"application/x-shockwave-flash".equalsIgnoreCase(paramString3)) && (!paramString1.contains(".swf")))
      {
        if (("application/player-activex".equalsIgnoreCase(paramString3)) || ("application/qvod-plugin".equalsIgnoreCase(paramString3)) || (paramString1.startsWith("qvod://")) || (paramString1.startsWith("bdhd://"))) {
          bool = true;
        }
        return bool;
      }
      paramString2 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(901);
      paramString3 = UrlUtils.getHost(paramString1);
      if (paramString3 == null) {
        return false;
      }
      if (paramString2 != null)
      {
        paramString1 = paramString2;
        if (paramString2.size() > 0) {}
      }
      else
      {
        paramString1 = getDefaultFlash2VideoList();
      }
      paramString1 = paramString1.iterator();
      while (paramString1.hasNext()) {
        if (paramString3.contains((String)paramString1.next())) {
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  public boolean isQBiconInQQShineEnabled()
  {
    return PublicSettingManager.getInstance().isQBiconInQQShineEnabled();
  }
  
  public boolean isScreenAdDomainWhiteListMatched(String paramString)
  {
    return TbsAdProxy.getInstance(this.mAppContext).isScreenAdDomainWhiteListMatched(paramString);
  }
  
  public boolean isSessionPersistentEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getSessionPersistentEnabled();
    }
    return false;
  }
  
  public boolean isSpliceAdEnabled()
  {
    return PublicSettingManager.getInstance().isSpliceAdEnabled();
  }
  
  public boolean isSpliceMiniQbAdEnabled()
  {
    return PublicSettingManager.getInstance().isSpliceMiniQbAdEnabled();
  }
  
  public boolean isStatReportPage(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      if ((TbsUserInfo.getInstance().getStatState() & 0x100) == 256) {
        return this.mPerformanceUploadWhiteList.isContainsDomain(paramString);
      }
      return false;
    }
    return false;
  }
  
  public boolean isTbsARDenyPermisionRequest(String paramString)
  {
    if ((paramString != null) && (!paramString.equals(""))) {
      return DomainListDataProvider.getInstance().isUrlInDomainList(TbsBaseModuleShell.REQ_SRC_TBS, 184, paramString);
    }
    return false;
  }
  
  public boolean isTbsAREnable(Context paramContext)
  {
    return true;
  }
  
  public boolean isTbsARGrantPermisionRequest(String paramString)
  {
    if ((paramString != null) && (!paramString.equals(""))) {
      return DomainListDataProvider.getInstance().isUrlInDomainList(TbsBaseModuleShell.REQ_SRC_TBS, 183, paramString);
    }
    return false;
  }
  
  public boolean isTbsAdHttpProxySwitchOpened()
  {
    return PublicSettingManager.getInstance().getTbsAdHttpProxySwitch(false);
  }
  
  public boolean isTbsWebRTCAudioWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(262);
      if (localArrayList.isEmpty()) {
        return false;
      }
      if (localArrayList.contains("*")) {
        return true;
      }
      return localArrayList.contains(paramString);
    }
    return false;
  }
  
  protected boolean isURLInQProxyBlackList(String paramString)
  {
    if ((this.refuseQProxyTable != null) && (paramString != null))
    {
      if (paramString.length() == 0) {
        return false;
      }
      return this.refuseQProxyTable.containsKey(paramString.toLowerCase());
    }
    return false;
  }
  
  protected boolean isURLInQProxyWhiteList(String paramString)
  {
    if ((this.forceQProxyTable != null) && (paramString != null))
    {
      if (paramString.length() == 0) {
        return false;
      }
      return this.forceQProxyTable.containsKey(paramString.toLowerCase());
    }
    return false;
  }
  
  public boolean isUseDefaultDNSTTL(String paramString)
  {
    initDomainList();
    return this.httpDnsDomainRequestIdMatcher.isUseDefaultDNSTTL(paramString);
  }
  
  public boolean isWasmDisabled()
  {
    return PublicSettingManager.getInstance().getWasmDisabled();
  }
  
  public boolean isWebARCameraWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(316);
      if (localArrayList.isEmpty()) {
        return false;
      }
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isWebARGestureModeWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(304);
      if (localArrayList.isEmpty()) {
        return false;
      }
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isWebARMarkerDisabled()
  {
    return PublicSettingManager.getInstance().getWebARMarkerDisabled();
  }
  
  public boolean isWebARMarkerEnabledOnAllDevices()
  {
    return PublicSettingManager.getInstance().getWebARMarkerEnabledOnAllDevices();
  }
  
  public boolean isWebARMarkerWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(284);
      if (localArrayList.isEmpty()) {
        return false;
      }
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isWebARSlamDisabled()
  {
    return PublicSettingManager.getInstance().getWebARSlamDisabled();
  }
  
  public boolean isWebARSlamEnabledOnAllDevices()
  {
    return PublicSettingManager.getInstance().getWebARSlamEnabledOnAllDevices();
  }
  
  public boolean isWebARSlamVIODisabled()
  {
    return PublicSettingManager.getInstance().getWebARSlamVIODisabled();
  }
  
  public boolean isWebARSlamWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(283);
      if (localArrayList.isEmpty()) {
        return false;
      }
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isWebARTFLiteWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(303);
      if (localArrayList.isEmpty()) {
        return false;
      }
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isWebRTCOptimizationWhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(259);
      if (localArrayList.isEmpty()) {
        return false;
      }
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isWebRTCPluginAutoDownloadNotAllowed()
  {
    return PublicSettingManager.getInstance().isWebRTCPluginAutoDownloadNotAllowed();
  }
  
  public boolean isWebRTCPluginLoadBlackList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(340);
      if (localArrayList.isEmpty()) {
        return false;
      }
      if (localArrayList.contains("*")) {
        return true;
      }
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isWebRTCinCamera1WhiteList(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      if (isWebRTCinCamera1BlackList(paramString)) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(240);
      if (localArrayList.isEmpty()) {
        return false;
      }
      if (localArrayList.contains("*")) {
        return true;
      }
      return localArrayList.contains(paramString);
    }
    return false;
  }
  
  public boolean isWebUrlInCloudList(String paramString, int paramInt)
  {
    boolean bool1 = false;
    boolean bool2 = false;
    int i;
    if (paramInt != 169)
    {
      if (paramInt != 171) {
        return DomainListDataProvider.getInstance().isUrlInDomainList(TbsBaseModuleShell.REQ_SRC_TBS, paramInt, paramString);
      }
      if (PublicSettingManager.getInstance().getCloudSwitch("WIFI_VIDEO_MASTER_SWITCH", 1) == 1) {
        i = 1;
      } else {
        i = 0;
      }
      bool1 = bool2;
      if (i != 0)
      {
        bool1 = bool2;
        if (DomainListDataProvider.getInstance().isUrlInDomainList(TbsBaseModuleShell.REQ_SRC_TBS, paramInt, paramString)) {
          bool1 = true;
        }
      }
      return bool1;
    }
    if (PublicSettingManager.getInstance().getCloudSwitch("WIFI_VIDEO_MASTER_SWITCH", 1) == 0) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i != 0) || (DomainListDataProvider.getInstance().isUrlInDomainList(TbsBaseModuleShell.REQ_SRC_TBS, paramInt, paramString))) {
      bool1 = true;
    }
    return bool1;
  }
  
  public boolean isX5ProxyRequestEncrypted()
  {
    return true;
  }
  
  public boolean isX5ProxySupportReadMode()
  {
    return false;
  }
  
  public boolean isX5ProxySupportWebP()
  {
    return true;
  }
  
  public void loadWebAREngine(int paramInt, IWebAREngineCallback paramIWebAREngineCallback)
  {
    WebAREngineManagerService.getInstance(this.mAppContext).loadWebAREngine(paramInt, paramIWebAREngineCallback);
  }
  
  public void loadWebARModel(String paramString, IWebARModelCallback paramIWebARModelCallback)
  {
    WebAREngineManagerService.getInstance(this.mAppContext).loadWebARModel(paramString, paramIWebARModelCallback);
  }
  
  public void loadWebErrorPagePlugin(IWebErrorPagePluginLoadCallback paramIWebErrorPagePluginLoadCallback)
  {
    WebErrorPagePluginManager.getInstance(this.mAppContext).loadWebErrorPagePlugin(paramIWebErrorPagePluginLoadCallback);
  }
  
  public void loadWebRtcPlugin(IWebRtcPluginLoadCallback paramIWebRtcPluginLoadCallback)
  {
    WebRtcPluginManager.getInstance(this.mAppContext).loadWebRtcPlugin(paramIWebRtcPluginLoadCallback);
  }
  
  public void loadWebpageReadModePlugin(IWebpageReadModePluginLoadCallback paramIWebpageReadModePluginLoadCallback)
  {
    WebpageReadModePluginManager.getInstance(this.mAppContext).loadWebpageReadModePlugin(paramIWebpageReadModePluginLoadCallback);
  }
  
  public void loadX5Plugin(int paramInt, IX5LoadPluginCallback paramIX5LoadPluginCallback)
  {
    X5LoadPluginManagerService.getInstance(this.mAppContext).loadX5Plugin(paramInt, paramIX5LoadPluginCallback);
  }
  
  public boolean logFirstTextAndFirstScreenEnable()
  {
    return false;
  }
  
  public boolean needIgnoreGdtAd()
  {
    return 1 == PublicSettingManager.getInstance().getCloudSwitch("SPY_AD_AFTER_WECHAT_PAY_IGNORE_GDT_SWITCH", 1);
  }
  
  public boolean needRequestTbsAd(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    return isHostInTbsAdWhitelist(UrlUtils.getHost(paramString));
  }
  
  public boolean notifyHttpsTunnelProxyFail(int paramInt1, String paramString, int paramInt2)
  {
    TbsUserInfo.getInstance().handleHttpsTunnelProxyFail(paramInt1, paramString);
    return true;
  }
  
  public void notifyProxyStatusReport(boolean paramBoolean, HashMap<String, String> paramHashMap)
  {
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_PROXY_REPORT_V2", paramHashMap);
  }
  
  public void notifyQProxyDetectResult(Boolean paramBoolean, String paramString)
  {
    QProxyDetect.getInstance().notifyQProxyDetectResult(paramBoolean, paramString);
    paramString = new StringBuilder();
    paramString.append(TAG);
    paramString.append("QProxyDetect");
    paramString = paramString.toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("notifyQProxyDetectResult:");
    localStringBuilder.append(paramBoolean);
    LogUtils.d(paramString, localStringBuilder.toString());
  }
  
  public boolean notifyQProxyFailHandler(int paramInt1, String paramString, int paramInt2)
  {
    TbsUserInfo.getInstance().handleQProxyFailure(paramInt1, paramString);
    return true;
  }
  
  public void onCheckTaskComplete(String paramString)
  {
    if ((this.isIPV6Checking) && (TbsUserInfo.getInstance().isIPV6Adress(paramString)))
    {
      this.isIPV6Checking = false;
      if (paramString != null) {
        TbsUserInfo.getInstance().resetIPV6Status(true);
      }
    }
    else
    {
      int i = Apn.sApnTypeS;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("CheckQProxy onCheckTaskComplete()-qProxyAddress = ");
      localStringBuilder.append(paramString);
      LogUtils.d("QProxyDetect", localStringBuilder.toString());
      if (paramString != null) {
        TbsUserInfo.getInstance().handleQProxySuccess(i, paramString);
      }
    }
  }
  
  public void onCheckTaskFailed(String paramString)
  {
    int i = Apn.sApnTypeS;
    StringBuilder localStringBuilder;
    if ((this.isIPV6Checking) && (TbsUserInfo.getInstance().isIPV6Adress(paramString)))
    {
      this.isIPV6Checking = false;
      TbsUserInfo.getInstance().resetIPV6Status(false);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Check IPV6  onCheckTaskFailed()-qProxyAddress = ");
      localStringBuilder.append(paramString);
      LogUtils.d("QProxyDetect", localStringBuilder.toString());
      if (paramString != null) {
        notifyQProxyFailHandler(i, paramString, 0);
      }
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Check IPV4 onCheckTaskFailed()-qProxyAddress = ");
      localStringBuilder.append(paramString);
      LogUtils.d("QProxyDetect", localStringBuilder.toString());
      if (paramString != null) {
        notifyQProxyFailHandler(i, paramString, 0);
      }
    }
  }
  
  public void onGeolocationStartUpdating(ValueCallback<Location> paramValueCallback, ValueCallback<Bundle> paramValueCallback1, boolean paramBoolean)
  {
    LbsManager.getInstance().startGeolocationTask(this, paramValueCallback, paramValueCallback1, paramBoolean);
  }
  
  public void onGeolocationStopUpdating()
  {
    LbsManager.getInstance().stopRequestLocation(this);
  }
  
  public void onPageLoadFinished(String paramString) {}
  
  public void onPageLoadStart(String paramString)
  {
    TbsAdUserInfoCollector.getInstance().onPageStart(paramString);
  }
  
  public void onProxyDetectFinish(int paramInt1, int paramInt2) {}
  
  public boolean onReceivedHeaders(String paramString1, String paramString2, String paramString3, ISmttServiceCallBack paramISmttServiceCallBack)
  {
    return paramString2 == null;
  }
  
  public void onReportMetrics(String paramString1, String paramString2, long paramLong1, long paramLong2, int paramInt1, String paramString3, boolean paramBoolean1, int paramInt2, String paramString4, String paramString5, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, boolean paramBoolean5, int paramInt4)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void onReportPerformanceV4(HashMap<String, String> paramHashMap)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" data=");
    localStringBuilder.append(paramHashMap.toString());
    LogUtils.d("onReportPerformanceV4", localStringBuilder.toString());
    if ((paramHashMap.containsKey("sub_resource_1")) && (((String)paramHashMap.get("sub_resource_1")).length() > 0)) {
      X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_PAGE_PERFORMANCE_57", paramHashMap);
    }
  }
  
  public void onReportResouceLoadError(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, long paramLong)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("url", paramString);
    paramString = new StringBuilder();
    paramString.append("");
    paramString.append(paramBoolean1);
    localHashMap.put("isWap", paramString.toString());
    paramString = new StringBuilder();
    paramString.append("");
    paramString.append(paramBoolean2);
    localHashMap.put("isRes", paramString.toString());
    paramString = new StringBuilder();
    paramString.append("");
    paramString.append(paramBoolean3);
    localHashMap.put("isError", paramString.toString());
    paramString = new StringBuilder();
    paramString.append("");
    paramString.append(paramLong);
    localHashMap.put("flow", paramString.toString());
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_PAGE_ERROR_LOAD_SIMPLE", localHashMap);
  }
  
  public void onReportTbsDAE(String paramString1, long paramLong, String paramString2, int paramInt1, String[] paramArrayOfString, int paramInt2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if (paramArrayOfString != null)
    {
      int j = paramArrayOfString.length;
      int i = 0;
      while (i < j)
      {
        localStringBuilder.append(paramArrayOfString[i]);
        localStringBuilder.append("|");
        i += 1;
      }
    }
    localStringBuilder.append("|");
    paramArrayOfString = new HashMap();
    paramArrayOfString.put("pageurl", paramString1);
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramLong);
    paramArrayOfString.put("timestamp", paramString1.toString());
    paramArrayOfString.put("cellid", paramString2);
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramInt1);
    paramArrayOfString.put("apn", paramString1.toString());
    paramArrayOfString.put("filteredurl", localStringBuilder.substring(0, localStringBuilder.length() - 1));
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramInt2);
    paramArrayOfString.put("hiddennum", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append("onReportTbsDAE data is ");
    paramString1.append(paramArrayOfString.toString());
    LogUtils.d("SmttService", paramString1.toString());
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_PAGE_DIRECT_ADBLOCK_INFO", paramArrayOfString);
  }
  
  public void onWebViewAppExit() {}
  
  public void onWebViewDestroy() {}
  
  public void onWebViewDetachedFromWindow() {}
  
  public void onWebViewPause() {}
  
  public void onWebviewStatusChange(IX5WebView paramIX5WebView, int paramInt, JSONObject paramJSONObject)
  {
    Context localContext = this.mAppContext;
    if (localContext == null) {
      return;
    }
    TbsAdProxy.getInstance(localContext).onWebviewStatusChange(paramIX5WebView, paramInt, paramJSONObject);
  }
  
  public boolean openUrlInQQBrowser(Context paramContext, String paramString1, String paramString2)
  {
    return TBSIntentUtils.openUrlInQQBrowser(paramContext, paramString1, paramString2);
  }
  
  public boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    return false;
  }
  
  public void playVideo(H5VideoInfo paramH5VideoInfo) {}
  
  public int preLoadScreenAd(Context paramContext, String paramString)
  {
    return TbsAdProxy.getInstance(this.mAppContext).preLoadScreenAd(paramContext, paramString);
  }
  
  public void pullWupDomainInfoByForce()
  {
    TbsWupManager.getInstance().pullWupDomainInfoByForce();
  }
  
  public void pullWupInfoByForce()
  {
    TbsWupManager.getInstance().setDebugMode(true);
    TbsWupManager.getInstance().doMultiWupRequest();
  }
  
  public void pullWupIpListByForce()
  {
    TbsWupManager.getInstance().pullWupIpListByForce();
  }
  
  public void pullWupPreferenceByForce()
  {
    TbsWupManager.getInstance().pullWupPreferenceByForce();
  }
  
  public void reportAdHiddenNum(String paramString, int paramInt)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("pageurl", paramString);
    paramString = new StringBuilder();
    paramString.append("");
    paramString.append(paramInt);
    localHashMap.put("hiddenNumber", paramString.toString());
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_ADBLOCK_HIDDEN", localHashMap);
  }
  
  public void reportBigKingStatus(HashMap<String, String> paramHashMap)
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("reportBigKingStatus data=");
    localStringBuilder.append(paramHashMap.toString());
    LogUtils.d(str, localStringBuilder.toString());
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_BKING", paramHashMap);
  }
  
  public void reportDisplayCutoutInfo(String paramString1, String paramString2, boolean paramBoolean, int paramInt1, int paramInt2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("guid", GUIDFactory.getInstance().getStrGuid());
    localHashMap.put("packagename", this.mAppContext.getApplicationInfo().packageName);
    localHashMap.put("url", paramString1);
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramBoolean);
    localHashMap.put("fullscreen", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramInt1);
    localHashMap.put("oritation", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramInt2);
    localHashMap.put("viewportfit", paramString1.toString());
    localHashMap.put("device", paramString2);
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_DISPLAY_CUTOUT_INFO", localHashMap);
  }
  
  public boolean reportDnsResolveResults(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString1, String paramString2, String paramString3, int paramInt7, String paramString4, int paramInt8)
  {
    HashMap localHashMap = new HashMap();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramInt1);
    localHashMap.put("dnstype", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramInt2);
    localHashMap.put("errorcode", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramInt3);
    localHashMap.put("oserror", localStringBuilder.toString());
    localHashMap.put("hostname", paramString1);
    localHashMap.put("dnsresult", paramString2);
    localHashMap.put("dnsserver", paramString3);
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramInt7);
    localHashMap.put("apntype", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramString4);
    localHashMap.put("clientip", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramInt4);
    localHashMap.put("httpdnserror", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramInt5);
    localHashMap.put("dnsrequesttime", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramInt6);
    localHashMap.put("requestid", paramString1.toString());
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramInt8);
    localHashMap.put("ipcount", paramString1.toString());
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_DNS_RESOLVE_INFO", localHashMap);
    return true;
  }
  
  public void reportDoFingerSearch(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).reportDoFingerSearch();
  }
  
  public void reportDoLongClick(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).reportDoLongClick();
  }
  
  public void reportJsFetchResults(HashMap paramHashMap)
  {
    if (TbsMode.TBSISQB())
    {
      paramHashMap.put("skv", this.skvDataForSearchTeam);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("");
      localStringBuilder.append(System.currentTimeMillis());
      paramHashMap.put("uploadTime", localStringBuilder.toString());
      this.searchResultNeedToUpload = Boolean.valueOf(true);
    }
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_SEARCH_RESULT", paramHashMap, true);
  }
  
  public void reportRedirectIntercept(String paramString, int paramInt)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("guid", GUIDFactory.getInstance().getStrGuid());
    localHashMap.put("packagename", this.mAppContext.getApplicationInfo().packageName);
    localHashMap.put("url", paramString);
    paramString = new StringBuilder();
    paramString.append("");
    paramString.append(paramInt);
    localHashMap.put("limit", paramString.toString());
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_REDIRECT_INTERCEPT_INFO", localHashMap);
  }
  
  public void reportShowLongClickPopupMenu(Object paramObject)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).reportShowLongClickPopupMenu();
  }
  
  public void reportUserFinallySelectText(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      return;
    }
    ((FingerSearch)paramObject).reportUserFinallySelectText(paramString);
  }
  
  public void reportX5CoreDataDirInfo(boolean paramBoolean)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("guid", GUIDFactory.getInstance().getStrGuid());
    localHashMap.put("packagename", this.mAppContext.getApplicationInfo().packageName);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramBoolean);
    localHashMap.put("usex5dir", localStringBuilder.toString());
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_X5_CORE_DATA_DIR_INFO", localHashMap);
  }
  
  public void requestCloudRecognition(String paramString, IWebARCloudRecognitionCallback paramIWebARCloudRecognitionCallback)
  {
    WebAREngineManagerService.getInstance(this.mAppContext).requestCloudRecognition(paramString, paramIWebARCloudRecognitionCallback);
  }
  
  public void resetIPList()
  {
    TbsUserInfo.getInstance().tryGetIPList(Apn.getApnTypeS(), true, false, false);
  }
  
  public boolean satisfyRatioControl(String paramString)
  {
    return PublicSettingManager.getInstance().satisfyRatioControl(paramString);
  }
  
  public void sendUserBeaviourDataToBeacon(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString6, String paramString7, String paramString8)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("src_url", paramString1);
    localHashMap.put("dst_url", paramString2);
    localHashMap.put("elements_id", paramString3);
    localHashMap.put("elements_class", paramString4);
    localHashMap.put("elements_tagname", paramString5);
    localHashMap.put("screen_x", Integer.toString(paramInt1));
    localHashMap.put("screen_y", Integer.toString(paramInt2));
    localHashMap.put("page_x", Integer.toString(paramInt3));
    localHashMap.put("page_y", Integer.toString(paramInt4));
    localHashMap.put("middle_url_1", paramString6);
    localHashMap.put("middle_url_2", paramString7);
    localHashMap.put("middle_url_3", paramString8);
    paramString1 = TAG;
    paramString2 = new StringBuilder();
    paramString2.append("sendUserBeaviourDataToBeacon data=");
    paramString2.append(localHashMap.toString());
    LogUtils.d(paramString1, paramString2.toString());
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_USER_BEHAVIOUR_INFO", localHashMap);
  }
  
  public void setCallback(ISmttServiceCallBack paramISmttServiceCallBack) {}
  
  public void setContext(Context paramContext)
  {
    this.mAppContext = paramContext.getApplicationContext();
  }
  
  public void setCurrentApnType(int paramInt)
  {
    this.mCurrentApnType = Apn.getApnNameWithBSSID(paramInt);
  }
  
  public void setCurrentApnType(String paramString)
  {
    this.mCurrentApnType = paramString;
  }
  
  public void setCustomQProxy(String paramString)
  {
    this.mCustomQProxy = paramString;
  }
  
  public void setEntryDataForSearchTeam(String paramString)
  {
    this.entryDataForSearchTeam = paramString;
  }
  
  public void setNeedCheckQProxy(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SmttService setNeedCheckQProxy()-needCheckQProxy = ");
    localStringBuilder.append(paramBoolean1);
    localStringBuilder.append(" isHttpsTunnel=");
    localStringBuilder.append(paramBoolean2);
    localStringBuilder.append("ipv6Qproxy=");
    localStringBuilder.append(paramBoolean3);
    LogUtils.d("QProxyDetect", localStringBuilder.toString());
    if (paramBoolean3)
    {
      if ((!this.isIPV6Checking) && (!this.isFirstGet)) {
        this.isIPV6Checking = paramBoolean1;
      } else {
        paramBoolean1 = false;
      }
    }
    else if (paramBoolean2) {
      this.isNeedCheckHttpsTunnelProxy = paramBoolean1;
    } else {
      this.isNeedCheckQProxy = paramBoolean1;
    }
    if (paramBoolean1) {
      CheckQProxy(paramBoolean2, paramBoolean3);
    }
  }
  
  public void setNeedWIFILogin(boolean paramBoolean)
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("SmttService--setNeedWIFILogin()-isNeedWIFILogin=");
    localStringBuilder.append(paramBoolean);
    LogUtils.d(str, localStringBuilder.toString());
    this.isNeedWIFILogin = paramBoolean;
  }
  
  public boolean setQProxyBlackDomain(String paramString)
  {
    if (paramString != null) {
      try
      {
        if (paramString.length() != 0)
        {
          if (this.directDomainTable == null) {
            this.directDomainTable = new Hashtable();
          }
          paramString = paramString.toLowerCase();
          if (!this.directDomainTable.containsKey(paramString))
          {
            this.directDomainTable.put(paramString, "");
            this.directDomainMatcher.addDomain(paramString);
          }
          return true;
        }
      }
      finally {}
    }
    return false;
  }
  
  public boolean setQProxyBlackUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      if (this.refuseQProxyTable == null) {
        this.refuseQProxyTable = new Hashtable();
      }
      this.refuseQProxyTable.put(paramString.toLowerCase(), "");
      return true;
    }
    return false;
  }
  
  public boolean setQProxyWhiteUrl(String paramString)
  {
    if ((paramString != null) && (paramString.length() != 0))
    {
      if (this.forceQProxyTable == null) {
        this.forceQProxyTable = new Hashtable();
      }
      this.forceQProxyTable.put(paramString.toLowerCase(), "");
      return true;
    }
    return false;
  }
  
  public void setSkvDataForSearchTeam(String paramString)
  {
    this.skvDataForSearchTeam = paramString;
  }
  
  public void setSpdyListToSDK(ArrayList<String> paramArrayList, ISmttServiceCallBack paramISmttServiceCallBack) {}
  
  public abstract void setTbsSmttServiceCallBack();
  
  public void setWupAddressByForce(String paramString)
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setCustomedWupAddress:");
    localStringBuilder.append(paramString);
    LogUtils.d(str, localStringBuilder.toString());
    PublicSettingManager.getInstance().setCustomedWupAddress(paramString);
  }
  
  public boolean shouldConvertToHttpsForThisDomain(String paramString)
  {
    initDomainList();
    return this.httpToHttpsDomainMatcher.isContainsDomain(paramString);
  }
  
  public boolean shouldInterceptJsapiRequest()
  {
    return PublicSettingManager.getInstance().shouldInterceptJsapiRequest();
  }
  
  public boolean shouldNotExecuteSmartAdFilter(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return true;
    }
    if (!PublicSettingManager.getInstance().shouldUseSmartAdFilter()) {
      return true;
    }
    try
    {
      initDomainList();
      boolean bool = this.mSmartAdFilterDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString) {}
    return true;
  }
  
  public boolean shouldSupportQuicDirect(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    paramString = UrlUtils.getHost(paramString);
    try
    {
      initDomainList();
      boolean bool = this.mQuicDirectWhiteList.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString) {}
    return false;
  }
  
  public boolean shouldSupportTpgDec(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return true;
    }
    try
    {
      initDomainList();
      boolean bool = this.mTpgDecBlackList.isContainsDomain(paramString);
      return !bool;
    }
    catch (Throwable paramString) {}
    return true;
  }
  
  public int showScreenAd(Context paramContext, IX5WebView paramIX5WebView, String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    Context localContext = this.mAppContext;
    if (localContext != null) {
      return TbsAdProxy.getInstance(localContext).showScreenAd(paramContext, paramIX5WebView, paramString, paramBoolean1, paramBoolean2);
    }
    return -1;
  }
  
  public void showToast(String paramString, int paramInt)
  {
    if (this.mAppContext != null) {
      MttToaster.show(paramString, paramInt);
    }
  }
  
  public void snapshotScreenAd(Bitmap paramBitmap, float paramFloat1, float paramFloat2, IX5WebView paramIX5WebView)
  {
    Context localContext = this.mAppContext;
    if (localContext != null) {
      TbsAdProxy.getInstance(localContext).snapshotScreenAd(paramBitmap, paramFloat1, paramFloat2, paramIX5WebView);
    }
  }
  
  public void stopCloudRecognition()
  {
    WebAREngineManagerService.getInstance(this.mAppContext).stopCloudRecognition();
  }
  
  public void upLoadToBeacon(String paramString, Map<String, String> paramMap)
  {
    Context localContext = this.mAppContext;
    if (localContext != null) {
      X5CoreBeaconUploader.getInstance(localContext).upLoadToBeacon(paramString, paramMap);
    }
  }
  
  public void uploadTbsAdStatsRealTime(String paramString, Map<String, String> paramMap)
  {
    if (this.mAppContext != null)
    {
      Object localObject = PublicSettingManager.getInstance();
      boolean bool = true;
      int i = ((PublicSettingManager)localObject).getCloudSwitch("UPLOAD_REAL_TIME_SWITCH", 1);
      localObject = X5CoreBeaconUploader.getInstance(this.mAppContext);
      if (i != 1) {
        bool = false;
      }
      ((X5CoreBeaconUploader)localObject).upLoadToBeacon(paramString, paramMap, bool);
    }
  }
  
  public void uploadTbsDirectInfoToBeacon(Map<String, String> paramMap)
  {
    if ((this.mAppContext != null) && (paramMap != null))
    {
      if (paramMap.isEmpty()) {
        return;
      }
      PublicSettingManager localPublicSettingManager = PublicSettingManager.getInstance();
      String str1 = (String)paramMap.get("proxy");
      String str2 = UrlUtils.getHost((String)paramMap.get("url"));
      String str3 = UrlUtils.getHost((String)paramMap.get("refer"));
      if (TbsMode.TBSISQB())
      {
        paramMap.put("skv", this.skvDataForSearchTeam);
        paramMap.put("entry", this.entryDataForSearchTeam);
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(System.currentTimeMillis());
        paramMap.put("uploadTime", localStringBuilder.toString());
      }
      LogUtils.d("DirectUploadInfo", "--- Upload Direct To Beacon ---");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(" - data = ");
      localStringBuilder.append(paramMap.toString());
      LogUtils.d("DirectUploadInfo", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(" - Msg  : # WUPswitcher=");
      localStringBuilder.append(localPublicSettingManager.getTbsInfoUploadSwitcer());
      localStringBuilder.append(" WUPapn=");
      localStringBuilder.append(localPublicSettingManager.getTbsInfoUploadApn());
      localStringBuilder.append(" isInWhiteList=");
      localStringBuilder.append(isHostnameInDirectUploadList(str2));
      localStringBuilder.append(" isReferInWhiteList:");
      localStringBuilder.append(isHostnameInDirectUploadReferList(str3));
      localStringBuilder.append(" isProxy=");
      localStringBuilder.append(str1);
      localStringBuilder.append(" WUPNeedProxyData=");
      localStringBuilder.append(localPublicSettingManager.getTbsInfoUploadNeedProxyData());
      LogUtils.d("DirectUploadInfo", localStringBuilder.toString());
      if (((localPublicSettingManager.getTbsInfoUploadSwitcer()) && ((isHostnameInDirectUploadList(str2)) || (isHostnameInDirectUploadReferList(str3))) && ((str1.equals("0")) || (localPublicSettingManager.getTbsInfoUploadNeedProxyData())) && ((localPublicSettingManager.getTbsInfoUploadApn() & Apn.sApnType) != 0)) || (TbsMode.TBSISQB()))
      {
        LogUtils.d("DirectUploadInfo", " - Finally, client upload to beacon !!!");
        X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("MTT_CORE_DIRECT_INFO", paramMap);
      }
      else
      {
        LogUtils.d("DirectUploadInfo", " - Finally, give up... ");
      }
      LogUtils.d("DirectUploadInfo", "-------------------------------");
      return;
    }
  }
  
  public void userBehaviorStatistics(String paramString)
  {
    if (PublicSettingManager.getInstance().getIsUploadPvinWup()) {
      TBSStatManager.getInstance().userBehaviorStatistics(paramString, 1, true, false);
    }
    X5CoreBeaconUploader.getInstance(this.mAppContext).userBehaviorStatistics(paramString);
  }
  
  protected class CommonDomainTypeMatcher
  {
    public HashMap<String, Integer> domainTypeMaps = new HashMap();
    
    protected CommonDomainTypeMatcher() {}
    
    public void addDomainList(ArrayList<String> paramArrayList)
    {
      this.domainTypeMaps.clear();
      int i = 0;
      while (i < paramArrayList.size())
      {
        String[] arrayOfString = ((String)paramArrayList.get(i)).split("\\|");
        if (arrayOfString.length == 2) {
          try
          {
            this.domainTypeMaps.put(arrayOfString[0].trim(), Integer.valueOf(Integer.parseInt(arrayOfString[1].trim())));
          }
          catch (NumberFormatException localNumberFormatException)
          {
            localNumberFormatException.printStackTrace();
          }
        }
        i += 1;
      }
    }
    
    public void clear()
    {
      this.domainTypeMaps.clear();
    }
    
    public int getTypeFromDomain(String paramString)
    {
      if (TextUtils.isEmpty(paramString)) {
        return -1;
      }
      HashMap localHashMap = this.domainTypeMaps;
      if (localHashMap != null)
      {
        if (localHashMap.size() != 0) {
          return ((Integer)this.domainTypeMaps.get(paramString)).intValue();
        }
        return -2;
      }
      return -1;
    }
  }
  
  protected class HttpDnsDomainRequestIdMatcher
  {
    private HashMap<String, Integer> domainRequestIDmaps = new HashMap();
    private HashMap<String, Integer> domainUseDefaultTTLmaps = new HashMap();
    
    protected HttpDnsDomainRequestIdMatcher() {}
    
    public void addDomainList(ArrayList<String> paramArrayList)
    {
      this.domainRequestIDmaps.clear();
      this.domainUseDefaultTTLmaps.clear();
      if (paramArrayList != null)
      {
        if (paramArrayList.size() == 0) {
          return;
        }
        int i = 0;
        while (i < paramArrayList.size())
        {
          String[] arrayOfString = ((String)paramArrayList.get(i)).split("\\|");
          if ((arrayOfString.length == 3) && (isDomain(arrayOfString[0]))) {
            try
            {
              this.domainRequestIDmaps.put(arrayOfString[0].trim(), Integer.valueOf(Integer.parseInt(arrayOfString[1].trim())));
              this.domainUseDefaultTTLmaps.put(arrayOfString[0].trim(), Integer.valueOf(Integer.parseInt(arrayOfString[2].trim())));
            }
            catch (NumberFormatException localNumberFormatException)
            {
              localNumberFormatException.printStackTrace();
            }
          }
          i += 1;
        }
        return;
      }
    }
    
    public void clear()
    {
      this.domainRequestIDmaps.clear();
      this.domainUseDefaultTTLmaps.clear();
    }
    
    public int getHttpDNSRequestID(String paramString)
    {
      int i;
      if (this.domainRequestIDmaps.get(paramString) != null) {
        i = ((Integer)this.domainRequestIDmaps.get(paramString)).intValue();
      } else {
        i = 0;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getHttpDNSRequestID:");
      localStringBuilder.append(paramString);
      localStringBuilder.append(" - ");
      localStringBuilder.append(i);
      LogUtils.d("DNSTEST", localStringBuilder.toString());
      return i;
    }
    
    public boolean isDomain(String paramString)
    {
      return true;
    }
    
    public boolean isUseDefaultDNSTTL(String paramString)
    {
      if (SmttServiceCommon.this.getDnsResolveTypeForThisDomain(paramString) == SmttServiceCommon.DNS_RESOLVE_TYPE_HTTP_AES)
      {
        if ((this.domainUseDefaultTTLmaps.get(paramString) != null) && (((Integer)this.domainUseDefaultTTLmaps.get(paramString)).intValue() == 0))
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("isUseDefaultDNSTTL ");
          localStringBuilder.append(paramString);
          localStringBuilder.append(" FALSE");
          LogUtils.d("DNSTEST", localStringBuilder.toString());
          return false;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("isUseDefaultDNSTTL ");
        localStringBuilder.append(paramString);
        localStringBuilder.append(" TRUE");
        LogUtils.d("DNSTEST", localStringBuilder.toString());
        return true;
      }
      return true;
    }
  }
  
  protected class X5HeaderPrivateKeyDomainMatcher
  {
    public HashMap<Integer, String> idKeys = new HashMap();
    private StringBuffer privateKey = null;
    
    protected X5HeaderPrivateKeyDomainMatcher() {}
    
    public void addDomainList(ArrayList<String> paramArrayList)
    {
      this.idKeys.clear();
      int j = 0;
      int i = 0;
      while (i < paramArrayList.size())
      {
        String[] arrayOfString = ((String)paramArrayList.get(i)).split("\\|");
        if (arrayOfString.length == 2) {
          try
          {
            this.idKeys.put(Integer.valueOf(Integer.parseInt(arrayOfString[0].trim())), arrayOfString[1].trim());
          }
          catch (NumberFormatException localNumberFormatException)
          {
            localNumberFormatException.printStackTrace();
          }
        }
        i += 1;
      }
      this.privateKey = new StringBuffer("");
      i = j;
      while (i < this.idKeys.size())
      {
        paramArrayList = (String)this.idKeys.get(Integer.valueOf(i));
        this.privateKey.append(paramArrayList);
        i += 1;
      }
    }
    
    public void clear()
    {
      this.idKeys.clear();
    }
    
    public String getXTbsKeyPrivateKey()
    {
      StringBuffer localStringBuffer = this.privateKey;
      if (localStringBuffer != null) {
        return localStringBuffer.toString();
      }
      return "";
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\SmttServiceCommon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */