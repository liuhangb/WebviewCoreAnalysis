package com.tencent.tbs.tbsshell.common;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.webkit.ValueCallback;
import com.tencent.mtt.video.export.FeatureSupport;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IH5VideoPlayer;
import com.tencent.mtt.video.export.PlayerEnv;
import com.tencent.mtt.video.export.VideoProxyDefault;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.tbs.common.ar.WEBAR.IWebARCloudRecognitionCallback;
import com.tencent.tbs.common.ar.WEBAR.IWebAREngineCallback;
import com.tencent.tbs.common.ar.WEBAR.IWebARModelCallback;
import com.tencent.tbs.tbsshell.common.X5LoadPlugin.IX5LoadPluginCallback;
import com.tencent.tbs.tbsshell.common.errorpage.IWebErrorPagePluginLoadCallback;
import com.tencent.tbs.tbsshell.common.readmode.IWebpageReadModePluginLoadCallback;
import com.tencent.tbs.tbsshell.common.webrtc.IWebRtcPluginLoadCallback;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public abstract interface ISmttServiceCommon
{
  public abstract void adResearch(boolean paramBoolean, String paramString1, String paramString2);
  
  public abstract void addNovelSiteAdFilterBlackListDomaim(String paramString);
  
  public abstract void addNovelSiteAdFilterWhiteListDomaim(String paramString);
  
  public abstract boolean canClearDNSCacheGodCmd();
  
  public abstract boolean canInsertAdInSepcialSite();
  
  public abstract boolean canInsertTbsAdInPage(String paramString);
  
  public abstract boolean canShowEncouorageWindow();
  
  public abstract boolean canShowScreenAd(Context paramContext, String paramString, IX5WebView paramIX5WebView);
  
  public abstract boolean canShowScreenAdWhenBackEvent(Context paramContext, String paramString, IX5WebView paramIX5WebView);
  
  public abstract boolean canUseAdBlockWhenNotUsingQProxy();
  
  public abstract boolean canUseDynamicCanvasGpu();
  
  public abstract boolean canUseQProxyWhenHasSystemProxy();
  
  public abstract void cancelFingerSearch(Object paramObject, String paramString, boolean paramBoolean);
  
  public abstract void checkNeedToUpload(String paramString);
  
  public abstract boolean checkUseX5CookiePathPolicyEnabled();
  
  public abstract void chooseFavorites(Object paramObject, String paramString);
  
  public abstract void chooseTranslation(Object paramObject, String paramString);
  
  public abstract void cleanPlugins();
  
  public abstract void clearNovelSiteAdFilterWhiteAndBlackListDomaim();
  
  public abstract int convertGetToPostLevel();
  
  @TargetApi(18)
  public abstract void copyWupSwitchesToClipBoard();
  
  public abstract IH5VideoPlayer createTbsVideoPlayer(Context paramContext, VideoProxyDefault paramVideoProxyDefault, H5VideoInfo paramH5VideoInfo, FeatureSupport paramFeatureSupport, PlayerEnv paramPlayerEnv);
  
  public abstract void detectQProxyReachable();
  
  public abstract int directAdblockSwitcherLevel();
  
  public abstract int directAdblockSwitcherLevelForQB();
  
  public abstract void doRecogitionOnCloud(byte[] paramArrayOfByte);
  
  public abstract void enterFingerSearchMode(Object paramObject);
  
  public abstract void enterOtherMode(Object paramObject);
  
  public abstract void executeCopyItem(Object paramObject, String paramString);
  
  public abstract void executeDiectConsumptionItems(Object paramObject, String paramString);
  
  public abstract void executeMoreItem(Object paramObject, String paramString);
  
  public abstract void executeSearchItem(Object paramObject, String paramString);
  
  public abstract void exitFlushBeacon();
  
  public abstract void exitX5LongClickPopMenu(Object paramObject);
  
  public abstract void fingerSearchRequest(Object paramObject, String paramString1, int paramInt1, String paramString2, int paramInt2, int[] paramArrayOfInt, String paramString3, String paramString4);
  
  public abstract void generateTbsAdUserInfo(IX5WebView paramIX5WebView, Point paramPoint1, Point paramPoint2);
  
  public abstract List<String> getAccessibilityBlackList();
  
  public abstract String getAdBlockSourceFilePath();
  
  public abstract int getAndroidAccelerated2dCanvas();
  
  public abstract int getAndroidGpuRasterization();
  
  public abstract int getAndroidUploadTextureMode();
  
  public abstract int getAndroidWebgl();
  
  public abstract boolean getAutoPageDiscardingDefaultEnabled();
  
  public abstract boolean getAutoPageDiscardingEnabled();
  
  public abstract boolean getAutoPageRestorationDefaultEnabled();
  
  public abstract boolean getAutoPageRestorationEnabled();
  
  public abstract boolean getBFOptEnabled();
  
  public abstract String getBrowserBuildId();
  
  public abstract String getBrowserVersion();
  
  public abstract Object getCachedImage(String paramString);
  
  public abstract boolean getCanAIAExtension();
  
  public abstract String getCloudStringSwitch(String paramString1, String paramString2);
  
  public abstract int getCloudSwitch(String paramString, int paramInt);
  
  public abstract int getCustomImageQuality();
  
  public abstract String getDeviceName();
  
  public abstract String getDevicePlatform();
  
  public abstract String getDeviceVersion();
  
  public abstract int getDisplayCutoutEnableSwitch();
  
  public abstract int getDnsResolveTypeForThisDomain(String paramString);
  
  public abstract InputStream getDomDistillerJS();
  
  public abstract List<String> getECommerceFetchjsList();
  
  public abstract boolean getEnableQua1();
  
  public abstract String getExtendJsRule(String paramString);
  
  public abstract String[] getExternalSDcardPathArray();
  
  public abstract Object getFingerSearchInstance(IX5WebView paramIX5WebView);
  
  public abstract boolean getFirstScreenDetectEnable();
  
  public abstract boolean getFirstScreenDrawFullScreenEnable();
  
  public abstract boolean getFirstScreenDrawNotFullScreenEnable();
  
  public abstract boolean getFirstStartDownloadYYBTips();
  
  public abstract int getFrameLimitCount(int paramInt);
  
  public abstract String getGUID();
  
  public abstract String getGpuBugWorkAroundSwitch(String paramString);
  
  public abstract Bundle getGuidBundle();
  
  public abstract String getHttpDNSEncryptKey();
  
  public abstract int getHttpDNSRequestID(String paramString);
  
  public abstract String getHttpDNSServerIP();
  
  public abstract String getHttpsTunnelProxyAddress(String paramString1, boolean paramBoolean1, int paramInt, boolean paramBoolean2, String paramString2);
  
  public abstract String getIPListForWupAndQProxy();
  
  public abstract boolean getIsSchemeCallAppByUser();
  
  public abstract boolean getIsUseThirdPartyAdRules();
  
  public abstract boolean getJniRegisterEnabled();
  
  public abstract String getKingCardToken();
  
  public abstract String getLC();
  
  public abstract Map getLbsHeaders(String paramString);
  
  public abstract String getLiveLogGodCmd();
  
  public abstract ArrayList<String> getLocalConfigFilePath();
  
  public abstract byte[] getLocalPluginInfo();
  
  public abstract String getLocalPluginPath();
  
  public abstract String[] getLoginUserInfo();
  
  public abstract boolean getMSEEnabled();
  
  public abstract int getMaxReportNumber();
  
  public abstract String getMd5GUID();
  
  public abstract int getNativeOptSwitches();
  
  public abstract boolean getNeedWIFILogin();
  
  public abstract String getOAID();
  
  public abstract boolean getOpenWifiProxyEnable();
  
  public abstract String getPasswordKey();
  
  public abstract void getPluginForce(Context paramContext);
  
  public abstract String getPrerenderPkgName();
  
  public abstract int getPrerenderSwitch();
  
  public abstract int getProxyAddressType();
  
  public abstract String getQAID();
  
  public abstract String getQAuth();
  
  public abstract String getQIMEI();
  
  public abstract String getQProxyAddressInStringFormat(int paramInt, boolean paramBoolean);
  
  public abstract ArrayList<String> getQProxyAddressList();
  
  public abstract int getQProxyUsingFlag(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, byte paramByte, String paramString2, boolean paramBoolean4);
  
  public abstract String getQUA(boolean paramBoolean, String paramString1, String paramString2);
  
  public abstract String getQUA2();
  
  public abstract boolean getQuicProxySwitch();
  
  public abstract int getRedirectCountLimit();
  
  public abstract long getRemoteServerTimeStamp();
  
  public abstract boolean getSPAAdPageReportSwitch();
  
  public abstract List<String> getSWPresetWhiteList();
  
  public abstract List<String> getSWUpdateWhiteList();
  
  public abstract Bundle getSecurityLevel(String paramString);
  
  public abstract int getServerAllowQProxyStatus();
  
  public abstract String[] getSessionPersistentSupportedDomains();
  
  public abstract boolean getSharpDefaultEnable();
  
  public abstract boolean getSharpEnabled();
  
  public abstract long getSntpTime();
  
  public abstract String getSpecialSiteReportRatioInterval();
  
  public abstract String getSpyFinanceAdRatioInterval();
  
  public abstract boolean getSubResourcePerformanceEnabled();
  
  public abstract String getSubsetAdRuleFilePath();
  
  public abstract String getSwitchUA(String paramString, int[] paramArrayOfInt);
  
  public abstract String getTAID();
  
  public abstract String getTbsAdAppInstallCheckRatioInterval();
  
  public abstract String getTbsAdCompetitorReportRatioInterval();
  
  public abstract ArrayList<String> getTbsAdCompetitors();
  
  public abstract void getTbsAdDebugWhiteList();
  
  public abstract String getTbsAdStatsRatioInterval();
  
  public abstract ArrayList<String> getTbsAdUrl();
  
  public abstract String getTbsAdUserInfo(IX5WebView paramIX5WebView, String paramString, Point paramPoint1, Point paramPoint2);
  
  public abstract InputStream getTbsAutoInsertAdJS();
  
  public abstract boolean getTbsImpatientNetworkEnabled();
  
  public abstract String getTbsUserinfoLiveLog();
  
  public abstract String getTbsWebviewSearchEngineUrl();
  
  public abstract int getTpgEnabledType();
  
  public abstract String[] getUserBehaviourDomains();
  
  public abstract List<String> getVideoSniffList();
  
  public abstract HashMap<String, String> getWeChatPayServiceProviderSpiedInfoMap();
  
  public abstract String getWebARSlamHardwareConfig();
  
  public abstract String getWhiteListInfo();
  
  public abstract String getWupAddressByForce();
  
  public abstract String getX5CoreVersion();
  
  public abstract String getXTbsKeyPrivateKey();
  
  public abstract void hideScreenAd(IX5WebView paramIX5WebView);
  
  public abstract void initDomainList();
  
  public abstract boolean isAllowDnsStoreEnable();
  
  public abstract boolean isAllowLocalAddrAccess(String paramString);
  
  public abstract boolean isAllowQHead();
  
  public abstract boolean isAllowReportBadJs(String paramString);
  
  public abstract boolean isAutoSwitchARCoreBlackList(String paramString);
  
  public abstract boolean isAutoSwitchARCoreDeviceWhiteList(String paramString);
  
  public abstract boolean isAutoSwitchARCoreEnabled();
  
  public abstract boolean isAutoSwitchARCoreWhiteList(String paramString);
  
  public abstract boolean isAutoSwitchSlamVioBlackList(String paramString);
  
  public abstract boolean isAutoSwitchSlamVioEnabled();
  
  public abstract boolean isAutoSwitchSlamVioWhiteList(String paramString);
  
  public abstract boolean isBubbleAdEnabled();
  
  public abstract boolean isBubbleMiniQbAdEnabled();
  
  public abstract boolean isCallMode();
  
  public abstract boolean isCodeCacheEnable();
  
  public abstract boolean isDebugWupEnvironment();
  
  public abstract boolean isDefaultVideoFullScreenPlay(boolean paramBoolean);
  
  public abstract boolean isEnableAutoRemoveAds();
  
  public abstract boolean isEnableInterceptDidfailPageFinished();
  
  public abstract boolean isEnableLogs();
  
  public abstract boolean isEnablePreConn();
  
  public abstract boolean isEnableTbsARPage(String paramString);
  
  public abstract boolean isEnableVideoSameLayer(String paramString, boolean paramBoolean);
  
  public abstract int isEnableX5TBSHeaderType(String paramString);
  
  public abstract boolean isForceVideoPagePlay(String paramString, boolean paramBoolean);
  
  public abstract boolean isGdtAdvertisementEnabled();
  
  public abstract boolean isHostInTbsAdAppInstallCheckWhitelist(String paramString);
  
  public abstract boolean isHostInTbsAdWhitelist(String paramString);
  
  public abstract boolean isHostInTbsJsAdReportWhitelist(String paramString);
  
  public abstract boolean isHostNameInAdblockBlackList(String paramString);
  
  public abstract boolean isHostNameInAdblockWhiteList(String paramString);
  
  public abstract boolean isHostNameInSPAAdPageReportWhiteList(String paramString);
  
  public abstract boolean isInDebugTbsAdWhiteList();
  
  public abstract boolean isInNovelSiteAdFilterBlackList(String paramString);
  
  public abstract boolean isInNovelSiteAdFilterWhiteList(String paramString);
  
  public abstract boolean isInQuicDirectBlacklist(String paramString);
  
  public abstract boolean isInRenderFixedADFilterDomainWhiteList(String paramString);
  
  public abstract boolean isInRenderHijackDomainWhiteList(String paramString);
  
  public abstract boolean isInRenderReportDomainWhiteList(String paramString);
  
  public abstract boolean isInSelectedAdFilterWhiteList(String paramString);
  
  public abstract boolean isInTBSFileChooserBlackDomainList(String paramString);
  
  public abstract boolean isInWebpageReadModeBlackList(String paramString);
  
  public abstract boolean isInWebpageReadModeWhiteList(String paramString);
  
  public abstract boolean isKingCardUser();
  
  public abstract boolean isLbsDontAlertWhiteList(String paramString);
  
  public abstract boolean isMSEBlackList(String paramString);
  
  public abstract boolean isMediaCodecBlackList(String paramString);
  
  public abstract boolean isNativeBufferEnable();
  
  public abstract boolean isNeedQHead(String paramString);
  
  public abstract boolean isOutFrequencyControl(Context paramContext, String paramString, IX5WebView paramIX5WebView);
  
  public abstract boolean isPluginSupported(String paramString1, String paramString2, String paramString3);
  
  public abstract boolean isQBiconInQQShineEnabled();
  
  public abstract boolean isScreenAdDomainWhiteListMatched(String paramString);
  
  public abstract boolean isSessionPersistentEnabled();
  
  public abstract boolean isSpliceAdEnabled();
  
  public abstract boolean isSpliceMiniQbAdEnabled();
  
  public abstract boolean isStatReportPage(String paramString);
  
  public abstract boolean isTbsARDenyPermisionRequest(String paramString);
  
  public abstract boolean isTbsAREnable(Context paramContext);
  
  public abstract boolean isTbsARGrantPermisionRequest(String paramString);
  
  public abstract boolean isTbsAdHttpProxySwitchOpened();
  
  public abstract boolean isTbsWebRTCAudioWhiteList(String paramString);
  
  public abstract boolean isUseDefaultDNSTTL(String paramString);
  
  public abstract boolean isWasmDisabled();
  
  public abstract boolean isWebARCameraWhiteList(String paramString);
  
  public abstract boolean isWebARGestureModeWhiteList(String paramString);
  
  public abstract boolean isWebARMarkerDisabled();
  
  public abstract boolean isWebARMarkerEnabledOnAllDevices();
  
  public abstract boolean isWebARMarkerWhiteList(String paramString);
  
  public abstract boolean isWebARSlamDisabled();
  
  public abstract boolean isWebARSlamEnabledOnAllDevices();
  
  public abstract boolean isWebARSlamVIODisabled();
  
  public abstract boolean isWebARSlamWhiteList(String paramString);
  
  public abstract boolean isWebARTFLiteWhiteList(String paramString);
  
  public abstract boolean isWebRTCOptimizationWhiteList(String paramString);
  
  public abstract boolean isWebRTCPluginAutoDownloadNotAllowed();
  
  public abstract boolean isWebRTCPluginLoadBlackList(String paramString);
  
  public abstract boolean isWebRTCinCamera1WhiteList(String paramString);
  
  public abstract boolean isWebUrlInCloudList(String paramString, int paramInt);
  
  public abstract boolean isX5ProxyRequestEncrypted();
  
  public abstract boolean isX5ProxySupportReadMode();
  
  public abstract boolean isX5ProxySupportWebP();
  
  public abstract int kingCardUserProxyJudgeCondition();
  
  public abstract void loadWebAREngine(int paramInt, IWebAREngineCallback paramIWebAREngineCallback);
  
  public abstract void loadWebARModel(String paramString, IWebARModelCallback paramIWebARModelCallback);
  
  public abstract void loadWebErrorPagePlugin(IWebErrorPagePluginLoadCallback paramIWebErrorPagePluginLoadCallback);
  
  public abstract void loadWebRtcPlugin(IWebRtcPluginLoadCallback paramIWebRtcPluginLoadCallback);
  
  public abstract void loadWebpageReadModePlugin(IWebpageReadModePluginLoadCallback paramIWebpageReadModePluginLoadCallback);
  
  public abstract void loadX5Plugin(int paramInt, IX5LoadPluginCallback paramIX5LoadPluginCallback);
  
  public abstract boolean logFirstTextAndFirstScreenEnable();
  
  public abstract boolean needIgnoreGdtAd();
  
  public abstract boolean needRequestTbsAd(String paramString);
  
  public abstract void notifyHttpsTunnelExpired();
  
  public abstract boolean notifyHttpsTunnelProxyFail(int paramInt1, String paramString, int paramInt2);
  
  public abstract void notifyProxyStatusReport(boolean paramBoolean, HashMap<String, String> paramHashMap);
  
  public abstract void notifyQProxyDetectResult(Boolean paramBoolean, String paramString);
  
  public abstract boolean notifyQProxyFailHandler(int paramInt1, String paramString, int paramInt2);
  
  public abstract void onGeolocationStartUpdating(ValueCallback<Location> paramValueCallback, ValueCallback<Bundle> paramValueCallback1, boolean paramBoolean);
  
  public abstract void onGeolocationStopUpdating();
  
  public abstract void onPageLoadFinished(String paramString);
  
  public abstract void onPageLoadStart(String paramString);
  
  public abstract void onProxyDetectFinish(int paramInt1, int paramInt2);
  
  public abstract boolean onReceivedHeaders(String paramString1, String paramString2, String paramString3, ISmttServiceCallBack paramISmttServiceCallBack);
  
  public abstract void onReportMetrics(String paramString1, String paramString2, long paramLong1, long paramLong2, int paramInt1, String paramString3, boolean paramBoolean1, int paramInt2, String paramString4, String paramString5, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, boolean paramBoolean5, int paramInt4);
  
  public abstract void onReportPerformanceV4(HashMap<String, String> paramHashMap);
  
  public abstract void onReportResouceLoadError(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, long paramLong);
  
  public abstract void onReportTbsDAE(String paramString1, long paramLong, String paramString2, int paramInt1, String[] paramArrayOfString, int paramInt2);
  
  public abstract void onWebViewAppExit();
  
  public abstract void onWebViewDestroy();
  
  public abstract void onWebViewDetachedFromWindow();
  
  public abstract void onWebViewPause();
  
  public abstract void onWebviewStatusChange(IX5WebView paramIX5WebView, int paramInt, JSONObject paramJSONObject);
  
  public abstract boolean openUrlInQQBrowser(Context paramContext, String paramString1, String paramString2);
  
  public abstract boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract void playVideo(H5VideoInfo paramH5VideoInfo);
  
  public abstract int preLoadScreenAd(Context paramContext, String paramString);
  
  public abstract void pullWupDomainInfoByForce();
  
  public abstract void pullWupInfoByForce();
  
  public abstract void pullWupIpListByForce();
  
  public abstract void pullWupPreferenceByForce();
  
  public abstract void reportAdHiddenNum(String paramString, int paramInt);
  
  public abstract void reportBigKingStatus(HashMap<String, String> paramHashMap);
  
  public abstract void reportDisplayCutoutInfo(String paramString1, String paramString2, boolean paramBoolean, int paramInt1, int paramInt2);
  
  public abstract boolean reportDnsResolveResults(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString1, String paramString2, String paramString3, int paramInt7, String paramString4, int paramInt8);
  
  public abstract void reportDoFingerSearch(Object paramObject);
  
  public abstract void reportDoLongClick(Object paramObject);
  
  public abstract void reportJsFetchResults(HashMap paramHashMap);
  
  public abstract void reportRedirectIntercept(String paramString, int paramInt);
  
  public abstract void reportShowLongClickPopupMenu(Object paramObject);
  
  public abstract void reportUserFinallySelectText(Object paramObject, String paramString);
  
  public abstract void reportX5CoreDataDirInfo(boolean paramBoolean);
  
  public abstract void requestCloudRecognition(String paramString, IWebARCloudRecognitionCallback paramIWebARCloudRecognitionCallback);
  
  public abstract void resetIPList();
  
  public abstract boolean satisfyRatioControl(String paramString);
  
  public abstract void sendUserBeaviourDataToBeacon(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString6, String paramString7, String paramString8);
  
  public abstract void setCallback(ISmttServiceCallBack paramISmttServiceCallBack);
  
  public abstract void setEntryDataForSearchTeam(String paramString);
  
  public abstract void setNeedWIFILogin(boolean paramBoolean);
  
  public abstract boolean setQProxyBlackDomain(String paramString);
  
  public abstract boolean setQProxyBlackUrl(String paramString);
  
  public abstract boolean setQProxyWhiteUrl(String paramString);
  
  public abstract void setSkvDataForSearchTeam(String paramString);
  
  public abstract void setSpdyListToSDK(ArrayList<String> paramArrayList, ISmttServiceCallBack paramISmttServiceCallBack);
  
  public abstract void setWupAddressByForce(String paramString);
  
  public abstract boolean shouldConvertToHttpsForThisDomain(String paramString);
  
  public abstract boolean shouldInterceptJsapiRequest();
  
  public abstract boolean shouldNotExecuteSmartAdFilter(String paramString);
  
  public abstract boolean shouldSupportQuicDirect(String paramString);
  
  public abstract boolean shouldSupportTpgDec(String paramString);
  
  public abstract int showScreenAd(Context paramContext, IX5WebView paramIX5WebView, String paramString, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void showToast(String paramString, int paramInt);
  
  public abstract void snapshotScreenAd(Bitmap paramBitmap, float paramFloat1, float paramFloat2, IX5WebView paramIX5WebView);
  
  public abstract void stopCloudRecognition();
  
  public abstract void upLoadToBeacon(String paramString, Map<String, String> paramMap);
  
  public abstract void uploadTbsAdStatsRealTime(String paramString, Map<String, String> paramMap);
  
  public abstract void uploadTbsDirectInfoToBeacon(Map<String, String> paramMap);
  
  public abstract void userBehaviorStatistics(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\ISmttServiceCommon.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */