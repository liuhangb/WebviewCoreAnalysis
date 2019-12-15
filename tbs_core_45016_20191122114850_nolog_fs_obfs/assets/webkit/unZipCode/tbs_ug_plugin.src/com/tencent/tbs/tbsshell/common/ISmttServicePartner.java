package com.tencent.tbs.tbsshell.common;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract interface ISmttServicePartner
{
  public abstract void ActiveQBHeartBeat(Context paramContext);
  
  public abstract boolean AppLongPressMenuEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract int DownloadInterceptFileType();
  
  public abstract boolean LongPressMenuImageQueryEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean activeQBBackHeartBeatEnable(Context paramContext);
  
  public abstract int activeQBBackHeartBeatFrequency(Context paramContext);
  
  public abstract void addDetectorTask(String paramString, ValueCallback<String> paramValueCallback);
  
  public abstract boolean allowUseContentCacheBusiness(String paramString1, String paramString2);
  
  public abstract boolean canShowReinstallTip(String paramString);
  
  public abstract boolean canUseNewJsDialog();
  
  public abstract void clearLog();
  
  public abstract void clearMiniQBUpgradeFile();
  
  public abstract void clearQbSilentDownloadFile();
  
  public abstract int convertDownloadInterceptthreeswitchLevel();
  
  public abstract Bitmap createQrCode(String paramString, int paramInt1, int paramInt2);
  
  public abstract String detectLanguage(String paramString1, String paramString2);
  
  public abstract void enableMiniQBAllEntryKeys();
  
  public abstract int explorerCharacter(Context paramContext);
  
  public abstract boolean getAddArgumentForImageRequestEnable();
  
  public abstract boolean getAutoPageDiscardingDefaultEnabled();
  
  public abstract boolean getAutoPageDiscardingEnabled();
  
  public abstract boolean getAutoPageRestorationDefaultEnabled();
  
  public abstract boolean getAutoPageRestorationEnabled();
  
  public abstract int getConnectionTimeOutGPRS();
  
  public abstract int getConnectionTimeOutWIFI();
  
  public abstract boolean getContentCacheEnabled();
  
  public abstract String getCrashExtraInfo();
  
  public abstract boolean getCustomDiskCacheEnabled();
  
  public abstract boolean getDiskCacheSizeSwitch();
  
  public abstract List<String> getDownloadInterceptApkBlackList();
  
  public abstract List<String> getDownloadInterceptApkWhiteList();
  
  public abstract List<String> getDownloadInterceptFileTypeWhiteList();
  
  public abstract String getDownloadInterceptKeyMessage();
  
  public abstract List<String> getDownloadInterceptNoApkBlackList();
  
  public abstract int getDownloadInterceptToQBPopMenuStyle();
  
  public abstract boolean getFakeLoginEnabled();
  
  public abstract boolean getFramePerformanceRecordEnable();
  
  public abstract Object getFreeWifiGuideView(Context paramContext, IX5WebView paramIX5WebView);
  
  public abstract int getGameServiceEnv();
  
  public abstract ArrayList<String> getHeadsupByTBSServer();
  
  public abstract boolean getInterceptDownloadInQB(Context paramContext);
  
  public abstract boolean getInterceptDownloadInTencentFile(Context paramContext);
  
  public abstract String getInterceptDownloadMessage();
  
  public abstract int getLongPressToQBPopMenuStyle();
  
  public abstract String getMiniQBUA();
  
  public abstract String getMiniQBVC();
  
  public abstract String getMiniQbVersionName();
  
  public abstract int getNotifyDownloadQBTimesLimit();
  
  public abstract int getPrerenderSwitch();
  
  public abstract int getQQInterruptApkType();
  
  public abstract int getQQInterruptNotApkType();
  
  public abstract int getQbIconTypeInLongClick();
  
  public abstract int getRestrictErrorPageReloadSecond();
  
  public abstract int getSystemPopMenuStyle();
  
  public abstract InputStream getTbsAdReinstallTipJS();
  
  public abstract boolean getWXPCDefaultEnable();
  
  public abstract boolean getWXPCEnabled();
  
  public abstract String getX5LongClickPopupMenuSubText();
  
  public abstract String guessFileName(String paramString1, String paramString2, String paramString3);
  
  public abstract void headsupActiveQB(Context paramContext);
  
  public abstract void interceptVideoPlay(Object paramObject, Context paramContext, Handler paramHandler);
  
  public abstract boolean isAppInSwitchToMiniQBWhiteList(String paramString);
  
  public abstract boolean isBottomBarEnabled(Context paramContext);
  
  public abstract boolean isClickImageScanEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isDebugMiniQBEnv();
  
  public abstract boolean isDetectDownloadPkgName();
  
  public abstract boolean isDownloadFileInterceptFileTypeWhiteList(String paramString);
  
  public abstract boolean isDownloadFileInterceptNotAPKDomainBlackList(String paramString);
  
  public abstract boolean isDownloadFileInterceptWhiteList(String paramString);
  
  public abstract boolean isDownloadInterceptSwitchMatch(Context paramContext);
  
  public abstract boolean isFileChooserTBSEnabled(Context paramContext);
  
  public abstract boolean isGameEngineUseSandbox();
  
  public abstract boolean isGameFrameworkEnabled();
  
  public abstract boolean isGinJavaMapEraseDisabled();
  
  public abstract boolean isHeadsUpRiskWebsite(Context paramContext);
  
  public abstract boolean isHeadsUpTaobaoLinkEnabled(Context paramContext);
  
  public abstract boolean isHeadsUpTranscodingPageEnabled(Context paramContext);
  
  public abstract boolean isHostNameInSwitchToMiniQBWhiteList(String paramString);
  
  public abstract boolean isImageBrowserDisableInPage(String paramString);
  
  public abstract boolean isInMiniProgram(IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isInterceptDownloadRequestEnabled(Context paramContext);
  
  public abstract boolean isLastWupReqFromTestEvn(String paramString);
  
  public abstract boolean isLongClickOptMM(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isLongPressImageScanEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isLongPressMenuEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isLongPressMenuExplorerEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isLongPressMenuOpenInBrowserEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isLongPressMenuRefreshEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isLongPressMenuScreenShotEnabled(Context paramContext);
  
  public abstract boolean isLongPressMenuSelectCopyEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isLongPressQuickSecondMenu_QQ_ThirdApp(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isLongPressQuickSelectCopyEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isMeizuNightModeEnabled();
  
  public abstract boolean isMidPageQbNightModeOnLongPressEnabled(Context paramContext);
  
  public abstract boolean isMidPageQbOpenBrowserOnLongPressEnabled(Context paramContext);
  
  public abstract boolean isMidPageQbSearchOnLongPressEnabled(Context paramContext);
  
  public abstract boolean isMidPageQbTranslateOnLongPressEnabled(Context paramContext);
  
  public abstract boolean isMidPageQbWebviewBubbleEnabled(Context paramContext);
  
  public abstract boolean isMiniQBDisabled();
  
  public abstract boolean isNewLongPressDownloadInterceptOpenQbMethodEnabled(Context paramContext);
  
  public abstract boolean isOpenDebugTbsEnabled();
  
  public abstract boolean isOpenUrlOnLongPressEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isPlatformTypeReportEnabled();
  
  public abstract boolean isQBDownloaded();
  
  public abstract boolean isQBPureIncrease(Context paramContext);
  
  public abstract boolean isQQDomain(String paramString);
  
  public abstract boolean isQQErrorPageLittleGameEnabled(Context paramContext);
  
  public abstract boolean isRestrictErrorPageReload(String paramString);
  
  public abstract boolean isSafeDownloadInterceptEnabled(Context paramContext);
  
  public abstract boolean isSchemeInDeeplinkBlackList(String paramString);
  
  public abstract boolean isSchemeInDeeplinkWhiteList(String paramString);
  
  public abstract boolean isSchemeIntercept(String paramString);
  
  public abstract boolean isSearchOnLongPressEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isShouldContentCacheCurrentFrameWhenJsLocation(String paramString);
  
  public abstract boolean isShouldInterceptMttbrowser();
  
  public abstract boolean isShouldInterceptMttbrowserUseMiniQb();
  
  public abstract boolean isTbsClipBoardEnabled(Context paramContext);
  
  public abstract boolean isTbsJsapiEnabled(Context paramContext);
  
  public abstract boolean isTbsQrCodeShareEnabled();
  
  public abstract boolean isTestMiniQBEnv();
  
  public abstract boolean isTranslateOnLongPressEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase);
  
  public abstract boolean isWXWholePageTranslateEnabled(Context paramContext);
  
  public abstract boolean isWeChatCrashMonitorEnable();
  
  public abstract boolean isX5CameraEnabled(Context paramContext);
  
  public abstract boolean isX5CookieIsolationEnabled();
  
  public abstract boolean isX5CookieIsolationEnabledForTBS();
  
  public abstract String logDebugExec(String paramString);
  
  public abstract void monitorAppRemoved(Context paramContext);
  
  public abstract Context newPluginContextWrapper(Context paramContext, String paramString);
  
  public abstract int nightModeInLongPressthreeswitchLevel(Context paramContext);
  
  public abstract boolean onLongClickSearch(Context paramContext, String paramString1, String paramString2, Bundle paramBundle);
  
  public abstract boolean onOpenInBrowserClick(Context paramContext, String paramString, Bundle paramBundle);
  
  public abstract void onWindowFocusChanged(Context paramContext, boolean paramBoolean);
  
  public abstract void openBrowserListWithGeneralDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap);
  
  public abstract void openBrowserListWithQBDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);
  
  public abstract void openBrowserListWithQBDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap);
  
  public abstract void openBrowserListWithQBDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap, Bundle paramBundle);
  
  public abstract View openLandPage(Context paramContext, String paramString, ViewGroup paramViewGroup);
  
  public abstract boolean openTBSFileChooser(IX5WebViewBase paramIX5WebViewBase, ValueCallback<Uri[]> paramValueCallback, int paramInt, File paramFile, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Map<String, Drawable> paramMap);
  
  public abstract boolean openUrlAndDownloadInQQBrowserWithReport(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9);
  
  public abstract boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  public abstract boolean openUrlInQQBrowserWithReportAndRecordAnchor(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, Point paramPoint1, Point paramPoint2);
  
  public abstract boolean openUrlInTencentFileWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract void reportTbsError(int paramInt, String paramString);
  
  public abstract boolean requestHeadsUp(Context paramContext);
  
  public abstract void requestServiceByAreaType(int paramInt);
  
  public abstract int saveWebPageInLongPressthreeswitchLevel(Context paramContext);
  
  public abstract boolean schemeInterceptActiveQBEnable(Context paramContext);
  
  public abstract void sendMiniQBWupRequestForSwitch(Context paramContext);
  
  public abstract void setGameSandbox(boolean paramBoolean);
  
  public abstract void setGameServiceEnv(int paramInt);
  
  public abstract void setLogBeaconUpload(boolean paramBoolean);
  
  public abstract void setTestMiniQBEnv(boolean paramBoolean);
  
  public abstract boolean shouldLongClickExplorerByDomains(String paramString);
  
  public abstract void startDownloadInMiniQB(Context paramContext, String paramString1, String paramString2, byte[] paramArrayOfByte, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7);
  
  public abstract int startDownloadQb(Context paramContext, String paramString1, String paramString2, String paramString3, Bundle paramBundle);
  
  public abstract int startMiniQB(Context paramContext, String paramString1, String paramString2);
  
  public abstract int startMiniQB(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap);
  
  public abstract int startMiniQB(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap, ValueCallback<String> paramValueCallback);
  
  public abstract int startMiniQBWithImages(Context paramContext, Map<String, Bitmap> paramMap, Map<String, Boolean> paramMap1, int paramInt, String paramString);
  
  public abstract int startQBWeb(Context paramContext, String paramString1, String paramString2, int paramInt, Point paramPoint1, Point paramPoint2);
  
  public abstract int startQBWebWithNightFullscreenMode(Context paramContext, String paramString, int paramInt);
  
  public abstract boolean switchPluginDebugEnv();
  
  public abstract Bundle translate(String paramString1, String paramString2, String paramString3, String paramString4);
  
  public abstract boolean uploadPageErrorMetaInfo(String paramString1, int paramInt, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5);
  
  public abstract void uploadUgLog();
  
  public abstract int useDownloadInterceptPlugin(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7, String paramString8, String paramString9, byte[] paramArrayOfByte, IX5WebViewBase paramIX5WebViewBase, Object paramObject, String paramString10);
  
  public abstract void useQBWebLoadUrl(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\ISmttServicePartner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */