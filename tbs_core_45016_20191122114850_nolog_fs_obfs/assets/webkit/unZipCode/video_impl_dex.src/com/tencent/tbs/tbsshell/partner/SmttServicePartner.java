package com.tencent.tbs.tbsshell.partner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.widget.Toast;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.IntentUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.base.utils.PackageUtils;
import com.tencent.mtt.video.export.FeatureSupport;
import com.tencent.mtt.video.export.H5VideoInfo;
import com.tencent.mtt.video.export.IH5VideoPlayer;
import com.tencent.mtt.video.export.PlayerEnv;
import com.tencent.mtt.video.export.VideoProxyDefault;
import com.tencent.smtt.download.ad.b;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.ITbsSmttServiceCallback;
import com.tencent.tbs.common.baseinfo.QUAUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.baseinfo.TbsSmttServiceProxy;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.config.DefaultConfigurationManager;
import com.tencent.tbs.common.download.qb.QBDownloadListener;
import com.tencent.tbs.common.download.qb.QBDownloadManager;
import com.tencent.tbs.common.download.qb.TencentFileDownloadManager;
import com.tencent.tbs.common.log.LogExporter;
import com.tencent.tbs.common.log.LogManager;
import com.tencent.tbs.common.resources.PluginContextWrapper;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSPageLoadInfoManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.translate.TranslateWupRequester;
import com.tencent.tbs.common.ui.MttToaster;
import com.tencent.tbs.common.ui.dialog.TBSBrowserList;
import com.tencent.tbs.common.ui.dialog.TBSFileChooser;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.DownloadListener;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.IntentFilterCallback;
import com.tencent.tbs.common.utils.ApkPkgNameDetector;
import com.tencent.tbs.common.utils.CommonUtils;
import com.tencent.tbs.common.utils.MiniProgramUtil;
import com.tencent.tbs.common.utils.TBSIntentUtils;
import com.tencent.tbs.common.utils.TbsWUPTestManager;
import com.tencent.tbs.common.utils.VideoDomainMatcher;
import com.tencent.tbs.common.wifi.FreeWifiGuideView;
import com.tencent.tbs.common.wifi.FreeWifiGuideView.IWifiGuideActionListener;
import com.tencent.tbs.common.zxing.BarcodeFormat;
import com.tencent.tbs.common.zxing.WriterException;
import com.tencent.tbs.common.zxing.common.BitMatrix;
import com.tencent.tbs.common.zxing.qrcode.QRCodeWriter;
import com.tencent.tbs.tbsshell.TBSShell;
import com.tencent.tbs.tbsshell.TBSShellDelegate;
import com.tencent.tbs.tbsshell.TBSShellFactory;
import com.tencent.tbs.tbsshell.WebCoreProxy;
import com.tencent.tbs.tbsshell.common.DomainMatcher;
import com.tencent.tbs.tbsshell.common.ISmttServiceCallBack;
import com.tencent.tbs.tbsshell.common.ISmttServicePartner;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon.CommonDomainTypeMatcher;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon.HttpDnsDomainRequestIdMatcher;
import com.tencent.tbs.tbsshell.common.SmttServiceCommon.X5HeaderPrivateKeyDomainMatcher;
import com.tencent.tbs.tbsshell.common.TbsLog;
import com.tencent.tbs.tbsshell.common.ad.TbsAdProxy;
import com.tencent.tbs.tbsshell.common.ad.TbsAdUserInfoCollector;
import com.tencent.tbs.tbsshell.partner.debug.TbsLoadFailuresReport;
import com.tencent.tbs.tbsshell.partner.miniqb.TbsMiniQBProxy;
import com.tencent.tbs.tbsshell.partner.miniqb.upgrade.MiniQBUpgradeManager;
import com.tencent.tbs.tbsshell.partner.player.TbsVideoManager;
import com.tencent.tbs.tbsshell.partner.ug.TbsUgEngine;
import com.tencent.tbs.tbsshell.partner.webaccelerator.WebViewPool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import org.json.JSONObject;

public class SmttServicePartner
  extends SmttServiceCommon
  implements ISmttServicePartner
{
  private static final String BROWSER_LIST_SCENE_DOWNLOAD = "download";
  private static final String BROWSER_LIST_SCENE_WEBVIEW = "webview";
  private static final String PARAM_KEY_COOKIE = "PARAM_KEY_COOKIE";
  public static final String PARAM_KEY_FILENAME = "PARAM_KEY_FILENAME";
  public static final String PARAM_KEY_FORCE_DIALOG_STYLE = "PARAM_KEY_FORCE_DIALOG_STYLE";
  public static final String PARAM_KEY_MIMETYPE = "PARAM_KEY_MIMETYPE";
  public static final int STATE_SURE_DIRECT = 0;
  private static String mDownloadQbStatKey = "";
  private final int STATE_HOST_DIRECT = 1;
  private final int STATE_HOST_QPROXY = 2;
  private final int STATE_NOT_INITIAL = -1;
  private final int STATE_SURE_QPROXY = 1;
  private final int STATE_UNDETERMINED_DIRECT = 2;
  private final int STATE_UNDETERMINED_QPROXY = 3;
  private final int STATE_UNDETERMINED_STATE = 0;
  ArrayList<String> mContentCacheBusinessBlackList = new ArrayList();
  ContentCacheBusinessDomainAndPathMatcher mContentCacheBusinessBlackListMatcher = new ContentCacheBusinessDomainAndPathMatcher(null);
  private TBSDialogBuilder.DownloadListener mDownloadListener = null;
  DynamicOfflinePageSaveBusinessURLMatcher mDynamicOfflinePageSaveBusinessURLMatcher = new DynamicOfflinePageSaveBusinessURLMatcher(null);
  private boolean mHasDoneWupTask = false;
  private boolean mHasRemovedExpiredCookie = false;
  private QBDownloadListener mQbDownloadManagerListener = null;
  private QBDownloadListener mTfDownloadManagerListener = null;
  
  private void doWupTask() {}
  
  private int finalQProxyState(int paramInt, byte paramByte)
  {
    if (paramInt > 1)
    {
      if (paramByte == 1) {
        return 0;
      }
      if (paramByte == 2) {
        return 1;
      }
      if (paramInt == 2) {
        return 0;
      }
      return 1;
    }
    return paramInt;
  }
  
  private String getBrowserListDialogStyle(String paramString)
  {
    Object localObject2 = "";
    try
    {
      String str = this.mAppContext.getApplicationInfo().packageName;
      localObject2 = str;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    Object localObject3 = "normal";
    Object localObject1 = localObject3;
    if ("webview".equals(paramString))
    {
      localObject1 = localObject3;
      if (((String)localObject2).equals("com.tencent.mobileqq")) {
        localObject1 = "hw";
      }
    }
    localObject2 = PublicSettingManager.getInstance().getBrowserListDialogStyle();
    if (!TextUtils.isEmpty((CharSequence)localObject2))
    {
      localObject3 = ((String)localObject2).trim().split(",");
      int i = 0;
      while (i < localObject3.length)
      {
        if (localObject3[i] == null) {
          localObject2 = "";
        } else {
          localObject2 = localObject3[i];
        }
        localObject2 = ((String)localObject2).trim().split(":");
        if ((localObject2.length == 2) && (!TextUtils.isEmpty(paramString)) && (paramString.equals(localObject2[0].trim())))
        {
          if (TextUtils.isEmpty(localObject2[1].trim())) {
            return (String)localObject1;
          }
          return localObject2[1].trim();
        }
        i += 1;
      }
    }
    return (String)localObject1;
  }
  
  private boolean isFollowReferDirectList(String paramString)
  {
    try
    {
      boolean bool = this.mFollowReferDirectListMatcher.isContainsDomain(paramString);
      return bool;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  private boolean isSubResourceHostnameInDirectList(String paramString)
  {
    try
    {
      boolean bool = this.subResourceDirectDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  private boolean isSubResourceHostnameInQProxyWhiteList(String paramString)
  {
    try
    {
      boolean bool = this.subResourceProxyDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  private void openInTencentFile(Context paramContext, Intent paramIntent, String paramString1, String paramString2, String paramString3)
  {
    if ("com.tencent.FileManager".equals(paramString1)) {}
    try
    {
      Uri localUri = paramIntent.getData();
      String str = paramContext.getApplicationInfo().packageName;
      if (openUrlInTencentFileWithReport(paramContext, localUri.toString(), paramString2, str, paramString3))
      {
        userBehaviorStatistics(paramString2);
        return;
      }
    }
    catch (Throwable paramString2)
    {
      for (;;) {}
    }
    paramIntent.setPackage(paramString1);
    paramContext.startActivity(paramIntent);
  }
  
  private void removeExpiredCookie()
  {
    if (this.mHasRemovedExpiredCookie)
    {
      LogUtils.d(TAG, "has done removeExpiredCookie already, ignore");
      return;
    }
    LogUtils.d(TAG, "never doneremoveExpiredCookie, do it");
    this.mHasRemovedExpiredCookie = true;
    WebCoreProxy.cookieManager_removeExpiredCookie();
  }
  
  public void ActiveQBHeartBeat(Context paramContext)
  {
    try
    {
      if (Configuration.getInstance(this.mAppContext).activeQBBackHeartBeatEnable())
      {
        userBehaviorStatistics("TBSAQBHBPASS001");
        if (Configuration.getInstance(this.mAppContext).isQBPureIncrease())
        {
          userBehaviorStatistics("TBSAQBHBPASS002");
          int i = Configuration.getInstance(this.mAppContext).activeQBBackHeartBeatFrequency();
          if (i < 1)
          {
            userBehaviorStatistics("TBSAQBHBNOPASS003");
            return;
          }
          userBehaviorStatistics("TBSAQBHBPASS003");
          Bundle localBundle = GUIDFactory.getInstance().getGuidBundle();
          TbsUgEngine.getInstance().ActiveQBHeartBeat(paramContext, QUAUtils.getQUA2_V3(), i, localBundle);
          return;
        }
        userBehaviorStatistics("TBSAQBHBNOPASS002");
        return;
      }
      userBehaviorStatistics("TBSAQBHBNOPASS001");
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public boolean AppLongPressMenuEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).AppLongPressMenuEnabled(paramIX5WebViewBase);
  }
  
  public int DownloadInterceptFileType()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().DownloadInterceptFileType();
    }
    return 0;
  }
  
  public boolean LongPressMenuImageQueryEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).LongPressMenuImageQueryEnabled(paramIX5WebViewBase);
  }
  
  public boolean activeQBBackHeartBeatEnable(Context paramContext)
  {
    return Configuration.getInstance(paramContext).activeQBBackHeartBeatEnable();
  }
  
  public int activeQBBackHeartBeatFrequency(Context paramContext)
  {
    return Configuration.getInstance(paramContext).activeQBBackHeartBeatFrequency();
  }
  
  public void addDetectorTask(String paramString, ValueCallback<String> paramValueCallback)
  {
    ApkPkgNameDetector.getInstance(this.mAppContext).addDetectorTask(paramString, paramValueCallback);
  }
  
  public boolean allowUseContentCacheBusiness(String paramString1, String paramString2)
  {
    try
    {
      initDomainList();
      boolean bool1;
      if (TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(192).size() == 0) {
        bool1 = true;
      } else {
        bool1 = this.mContentCacheBusinessWhiteListMatcher.isContainsDomain(paramString1);
      }
      boolean bool2 = bool1;
      if (bool1)
      {
        bool1 = this.mContentCacheBusinessBlackListMatcher.isContainsDomainAndPath(paramString1, paramString2);
        bool2 = bool1 ^ true;
      }
      return bool2;
    }
    catch (Throwable paramString1)
    {
      paramString1.printStackTrace();
    }
    return false;
  }
  
  public boolean canShowReinstallTip(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    if (!Configuration.getInstance(this.mAppContext).isEnableReinstallTipsforAd())
    {
      paramString = TAG;
      return false;
    }
    long l1 = System.currentTimeMillis();
    long l2 = PublicSettingManager.getInstance().getTbsAdReinstallTipsShowTime();
    if ((l2 > 0L) && (l1 - l2 < 86400000L))
    {
      paramString = TAG;
      paramString = new StringBuilder();
      paramString.append("canShowReinstallTip 2 ");
      paramString.append(l1);
      paramString.append(" ");
      paramString.append(l2);
      paramString.toString();
      return false;
    }
    String str = UrlUtils.getHost(paramString);
    Object localObject1 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(331);
    Object localObject2 = new DomainMatcher();
    boolean bool;
    if (!((ArrayList)localObject1).isEmpty())
    {
      ((DomainMatcher)localObject2).addDomainList((ArrayList)localObject1);
      bool = ((DomainMatcher)localObject2).isContainsDomain(str);
    }
    else
    {
      bool = false;
    }
    localObject1 = new DomainMatcher();
    localObject2 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(330);
    if (!((ArrayList)localObject2).isEmpty()) {
      ((DomainMatcher)localObject1).addDomainList((ArrayList)localObject2);
    }
    if ((!bool) && (!((DomainMatcher)localObject1).isContainsDomain(str)))
    {
      paramString = TAG;
      return false;
    }
    return b.a().a(this.mAppContext, paramString, bool);
  }
  
  public boolean canUseNewJsDialog()
  {
    return PublicSettingManager.getInstance().canUseNewJsDialog();
  }
  
  public void clearLog()
  {
    LogManager.getInstance().clearLog();
  }
  
  public void clearMiniQBUpgradeFile()
  {
    MiniQBUpgradeManager.getInstance().clearMiniQBUpgradeFile(this.mAppContext);
  }
  
  public void clearQbSilentDownloadFile()
  {
    QBDownloadManager.getInstance().clearDownloadFile();
  }
  
  public int convertDownloadInterceptthreeswitchLevel()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().convertDownloadInterceptthreeswitchLevel();
    }
    return 0;
  }
  
  public Bitmap createQrCode(String paramString, int paramInt1, int paramInt2)
  {
    Bitmap localBitmap2 = null;
    Bitmap localBitmap1 = localBitmap2;
    for (;;)
    {
      int j;
      int i;
      try
      {
        paramString = new QRCodeWriter().encode(paramString, BarcodeFormat.QR_CODE, paramInt1, paramInt2);
      }
      catch (WriterException paramString)
      {
        localBitmap1 = localBitmap2;
        paramString.printStackTrace();
        paramString = null;
        break label143;
        localBitmap1 = localBitmap2;
        j = paramString.getWidth();
        localBitmap1 = localBitmap2;
        int k = paramString.getHeight();
        localBitmap1 = localBitmap2;
        localBitmap2 = Bitmap.createBitmap(j, k, Bitmap.Config.ARGB_8888);
        paramInt1 = 0;
        break label149;
        if (paramInt2 < k)
        {
          localBitmap1 = localBitmap2;
          if (!paramString.get(paramInt1, paramInt2)) {
            break label160;
          }
          i = -16777216;
          localBitmap1 = localBitmap2;
          localBitmap2.setPixel(paramInt1, paramInt2, i);
          paramInt2 += 1;
          continue;
        }
        paramInt1 += 1;
        break label149;
        return localBitmap2;
      }
      catch (Throwable paramString)
      {
        return localBitmap1;
      }
      label143:
      if (paramString == null)
      {
        return null;
        label149:
        if (paramInt1 < j)
        {
          paramInt2 = 0;
          continue;
          label160:
          i = -1;
        }
      }
    }
  }
  
  public IH5VideoPlayer createTbsVideoPlayer(Context paramContext, VideoProxyDefault paramVideoProxyDefault, H5VideoInfo paramH5VideoInfo, FeatureSupport paramFeatureSupport, PlayerEnv paramPlayerEnv)
  {
    return TbsVideoManager.getInstance().createVideoPlayer(paramContext, paramVideoProxyDefault, paramH5VideoInfo, paramFeatureSupport, paramPlayerEnv);
  }
  
  public String detectLanguage(String paramString1, String paramString2)
  {
    return TranslateWupRequester.getInstance().detectLanguage(paramString1, paramString2);
  }
  
  public void enableMiniQBAllEntryKeys()
  {
    Configuration.getInstance(this.mAppContext).enableMiniQBAllEntryKeys();
  }
  
  public int explorerCharacter(Context paramContext)
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().explorerCharacter();
    }
    return 0;
  }
  
  public String exportAllLogToFile(String paramString1, String paramString2)
  {
    float f;
    try
    {
      f = Float.parseFloat(paramString1);
      try
      {
        i = Integer.parseInt(paramString2);
      }
      catch (Exception paramString1) {}
      paramString1.printStackTrace();
    }
    catch (Exception paramString1)
    {
      f = 1.0F;
    }
    int i = 1;
    paramString1 = new StringBuilder();
    paramString1.append(Environment.getExternalStorageDirectory());
    paramString1.append("/tencent/tbs/tbslog/export");
    paramString1 = new File(paramString1.toString());
    if (!paramString1.exists()) {
      paramString1.mkdirs();
    }
    paramString2 = new SimpleDateFormat("yyyyMMddHHmmss").format(Long.valueOf(System.currentTimeMillis()));
    paramString1 = String.format("%s/tbslog_%s.txt", new Object[] { paramString1.getAbsolutePath(), paramString2 });
    if (LogExporter.exportAllLogToFile(paramString1, f, i) != null)
    {
      MttToaster.show(paramString1, 1);
      return paramString1;
    }
    MttToaster.show("save file fail!!", 1);
    return null;
  }
  
  public String exportAllLogToImage(String paramString1, String paramString2)
  {
    float f;
    try
    {
      f = Float.parseFloat(paramString1);
      try
      {
        i = Integer.parseInt(paramString2);
      }
      catch (Exception paramString1) {}
      paramString1.printStackTrace();
    }
    catch (Exception paramString1)
    {
      f = 1.0F;
    }
    int i = 1;
    paramString1 = new StringBuilder();
    paramString1.append(Environment.getExternalStorageDirectory());
    paramString1.append("/tencent/tbs/tbslog/export");
    paramString1 = new File(paramString1.toString());
    if (!paramString1.exists()) {
      paramString1.mkdirs();
    }
    paramString2 = new SimpleDateFormat("yyyyMMddHHmmss").format(Long.valueOf(System.currentTimeMillis()));
    paramString2 = String.format("%s/tbslog_%s.png", new Object[] { paramString1.getAbsolutePath(), paramString2 });
    Bitmap localBitmap = LogExporter.exportAllLogToImage(f, i);
    if (localBitmap != null)
    {
      try
      {
        paramString1 = new FileOutputStream(paramString2);
        try
        {
          localBitmap.compress(Bitmap.CompressFormat.PNG, 50, paramString1);
          paramString1.flush();
          paramString1.close();
          MttToaster.show(paramString2, 0);
          return paramString2;
        }
        catch (Exception paramString2) {}
        paramString2.printStackTrace();
      }
      catch (Exception paramString2)
      {
        paramString1 = null;
      }
      if (paramString1 == null) {}
    }
    try
    {
      paramString1.close();
      MttToaster.show("save image fail!!", 0);
      return null;
      MttToaster.show("save image fail!!", 0);
      return null;
    }
    catch (Exception paramString1)
    {
      for (;;) {}
    }
  }
  
  public String exportAllLogToReport(String paramString1, String paramString2)
  {
    float f;
    try
    {
      f = Float.parseFloat(paramString1);
      try
      {
        i = Integer.parseInt(paramString2);
      }
      catch (Exception paramString1) {}
      paramString1.printStackTrace();
    }
    catch (Exception paramString1)
    {
      f = 1.0F;
    }
    int i = 1;
    if (LogExporter.exportAllLogToReport(f, i)) {
      return "report done!!";
    }
    return null;
  }
  
  public String exportAllLogToShow(String paramString1, String paramString2, String paramString3)
  {
    float f1 = 1.0F;
    int i;
    try
    {
      i = Integer.parseInt(paramString1);
      try
      {
        float f2 = Float.parseFloat(paramString2);
        f1 = f2;
        j = Integer.parseInt(paramString3);
        f1 = f2;
      }
      catch (Exception paramString1) {}
      paramString1.printStackTrace();
    }
    catch (Exception paramString1)
    {
      i = 1;
    }
    int j = 1;
    if (i == 1) {
      return LogExporter.exportAllHeaders();
    }
    if (i == 2) {
      return LogExporter.exportAllPreferences();
    }
    if (i == 3) {
      return LogExporter.exportAllRecords(f1, j);
    }
    return "nothing!!";
  }
  
  public boolean getAddArgumentForImageRequestEnable()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getAddArgumentForImageRequestEnable();
    }
    return false;
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
  
  public int getConnectionTimeOutGPRS()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getConnectionTimeOutGPRS();
    }
    return 0;
  }
  
  public int getConnectionTimeOutWIFI()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getConnectionTimeOutWIFI();
    }
    return 0;
  }
  
  public boolean getContentCacheEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getContentCacheEnabled();
    }
    return false;
  }
  
  public String getCrashExtraInfo()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("");
    ((StringBuilder)localObject).append("H5Video:");
    ((StringBuilder)localObject).append(TbsVideoManager.getInstance().getCrashExtraInfo());
    ((StringBuilder)localObject).append("&");
    localObject = ((StringBuilder)localObject).toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)localObject);
    localStringBuilder.append("MiniQB:Ver=");
    localStringBuilder.append(TbsMiniQBProxy.getInstance().getMiniQBVcWithoutLoad());
    localStringBuilder.append("&");
    localObject = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)localObject);
    localStringBuilder.append("TbsUg:");
    localStringBuilder.append(TbsUgEngine.getInstance().getCrashExtraInfo());
    localStringBuilder.append("&");
    return localStringBuilder.toString();
  }
  
  public boolean getCustomDiskCacheEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getSdcardDiskCacheEnabled();
    }
    return false;
  }
  
  public boolean getDiskCacheSizeSwitch()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getDiskCacheSizeEnable();
    }
    return false;
  }
  
  public List<String> getDownloadInterceptApkBlackList()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(254);
  }
  
  public List<String> getDownloadInterceptApkWhiteList()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(107);
  }
  
  public List<String> getDownloadInterceptFileTypeWhiteList()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(116);
  }
  
  public String getDownloadInterceptKeyMessage()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getDownloadInterceptKeyMessage();
    }
    return "SmttServiceError";
  }
  
  public List<String> getDownloadInterceptNoApkBlackList()
  {
    return TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(255);
  }
  
  public int getDownloadInterceptToQBPopMenuStyle()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getDownloadInterceptToQBPopMenuStyle();
    }
    return -1;
  }
  
  public boolean getFakeLoginEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getFakeLoginEnabled();
    }
    return false;
  }
  
  public boolean getFramePerformanceRecordEnable()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getFramePerformanceRecordEnable();
    }
    return false;
  }
  
  public Object getFreeWifiGuideView(Context paramContext, IX5WebView paramIX5WebView)
  {
    paramContext = new FreeWifiGuideView(paramContext);
    paramContext.setWifiGuideActionListener(new FreeWifiGuideView.IWifiGuideActionListener()
    {
      public void jumpTo(String paramAnonymousString)
      {
        if (this.val$weakWebView.get() != null) {
          ((IX5WebView)this.val$weakWebView.get()).loadUrl(paramAnonymousString);
        }
      }
    });
    return paramContext;
  }
  
  public int getGameServiceEnv()
  {
    return PublicSettingManager.getInstance().getGameServiceEnv();
  }
  
  public ArrayList<String> getHeadsupByTBSServer()
  {
    try
    {
      initDomainList();
      ArrayList localArrayList = this.mHeadsupByTBSServer;
      return localArrayList;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    return null;
  }
  
  public String getHttpsTunnelProxyAddress(String paramString1, boolean paramBoolean1, int paramInt, boolean paramBoolean2, String paramString2)
  {
    paramString2 = new StringBuilder();
    paramString2.append("SmttServiceParter getHttpsTunnelProxyAddress host=");
    paramString2.append(paramString1);
    paramString2.append(" isMainResource=");
    paramString2.append(paramBoolean1);
    paramString2.append(" apnType=");
    paramString2.append(paramInt);
    LogUtils.d("TheSimCardOfGreatKing", paramString2.toString());
    initDomainList();
    if ((!this.mForceHTTPSProxyWhitelist.isContainsDomain(paramString1)) && (this.mForceHTTPSDirectWhitelist.isContainsDomain(paramString1))) {
      return "";
    }
    paramString1 = getQProxyAddressInStringFormat(paramInt, true);
    if ((paramString1 != null) && (!paramString1.isEmpty()) && (paramString1.startsWith("http")))
    {
      paramBoolean1 = TbsUserInfo.getInstance().isHttpsTunnelTokenValid();
      paramString2 = new StringBuilder();
      paramString2.append("isHttpsTunnelTokenValid isTokenValid=");
      paramString2.append(paramBoolean1);
      LogUtils.d("TheSimCardOfGreatKing", paramString2.toString());
      paramString2 = new StringBuilder();
      paramString2.append(paramBoolean1);
      paramString2.append("|");
      paramString2.append(paramString1);
      return paramString2.toString();
    }
    return "";
  }
  
  public boolean getInterceptDownloadInQB(Context paramContext)
  {
    return PublicSettingManager.getInstance().getDownloadInQB();
  }
  
  public boolean getInterceptDownloadInTencentFile(Context paramContext)
  {
    return PublicSettingManager.getInstance().getDownloadInTencentFile() == 1;
  }
  
  public String getInterceptDownloadMessage()
  {
    return TbsUgEngine.getInstance().getInterceptDownloadMessage();
  }
  
  public int getLongPressToQBPopMenuStyle()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getLongPressToQBPopMenuStyle();
    }
    return -1;
  }
  
  public String getMiniQBUA()
  {
    return TbsMiniQBProxy.getInstance().getMiniQBUA();
  }
  
  public String getMiniQBVC()
  {
    return TbsMiniQBProxy.getInstance().getMiniQBVC();
  }
  
  public String getMiniQbVersionName()
  {
    return TbsMiniQBProxy.getMiniQbVersionName();
  }
  
  public int getNotifyDownloadQBTimesLimit()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getNotifyDownloadQBTimesLimit();
    }
    return 0;
  }
  
  public int getPrerenderSwitch()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getPrerenderSwitch();
    }
    return -1;
  }
  
  public int getQProxyUsingFlag(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, byte paramByte, String paramString2, boolean paramBoolean4)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public int getQQInterruptApkType()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getQQInterruptApkType();
    }
    return -1;
  }
  
  public int getQQInterruptNotApkType()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getQQInterruptNotApkType();
    }
    return -1;
  }
  
  public int getQbIconTypeInLongClick()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getQbIconTypeInLongClick();
    }
    return 0;
  }
  
  public int getRestrictErrorPageReloadSecond()
  {
    return PublicSettingManager.getInstance().getRestrictErrorPageReloadSecond();
  }
  
  public int getServerAllowQProxyStatus()
  {
    if (TbsUserInfo.getInstance() != null) {
      return TbsUserInfo.getInstance().serverAllowQProxyStatus();
    }
    return -1;
  }
  
  public int getSystemPopMenuStyle()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getSystemPopMenuStyle();
    }
    return -1;
  }
  
  public InputStream getTbsAdReinstallTipJS()
  {
    if (this.mAppContext == null) {
      return null;
    }
    return TbsAdProxy.getInstance(this.mAppContext).getTbsAdReinstallTipJS();
  }
  
  public boolean getWXPCDefaultEnable()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getWXPCDefaultEnable();
    }
    return false;
  }
  
  public boolean getWXPCEnabled()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getWXPCEnabled();
    }
    return false;
  }
  
  public String getWupAddressByForce()
  {
    return TbsWUPTestManager.getInstance(this.mAppContext).getCustomedWupAddress();
  }
  
  public String getX5LongClickPopupMenuSubText()
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().getX5LongClickPopupMenuSubText();
    }
    return "";
  }
  
  public String guessFileName(String paramString1, String paramString2, String paramString3)
  {
    return UrlUtils.guessFileName(paramString1, paramString2, paramString3);
  }
  
  public void headsupActiveQB(Context paramContext)
  {
    try
    {
      TbsUgEngine.getInstance().headsupActiveQB(paramContext, QUAUtils.getQUA2_V3());
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public void initDomainList()
  {
    try
    {
      Object localObject1 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(1001);
      if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
      {
        if (this.mWhiteListRefreshTime == Long.valueOf((String)((ArrayList)localObject1).get(0)).longValue())
        {
          LogUtils.d(TAG, "---initDomainList no update..for time refresh. ");
          return;
        }
        this.mWhiteListRefreshTime = Long.valueOf((String)((ArrayList)localObject1).get(0)).longValue();
      }
      this.httpToHttpsDomainMatcher.clear();
      localObject1 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(123);
      if ((((ArrayList)localObject1).isEmpty()) && (this.mAppContext.getApplicationInfo().packageName.equals("com.tencent.mm")))
      {
        ((ArrayList)localObject1).add("mp.weixin.qq.com");
        ((ArrayList)localObject1).add("res.wx.qq.com");
      }
      this.httpToHttpsDomainMatcher.addDomainList((ArrayList)localObject1);
      this.udpDnsResolveDomainMatcher.clear();
      new ArrayList();
      new ArrayList();
      localObject1 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(156);
      this.httpDnsResolveDomainMatcher.addDomainList((ArrayList)localObject1);
      this.httpDnsDomainRequestIdMatcher.clear();
      Object localObject3 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(257);
      this.httpDnsDomainRequestIdMatcher.addDomainList((ArrayList)localObject3);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("json httpdnsList->");
      localStringBuilder.append(((ArrayList)localObject1).toString());
      localStringBuilder.append(" httprequestList->");
      localStringBuilder.append(((ArrayList)localObject3).toString());
      LogUtils.d("DNSTEST", localStringBuilder.toString());
      this.directDomainMatcher.clear();
      this.directDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(21));
      if (this.directDomainTable != null)
      {
        localObject1 = this.directDomainTable.keys();
        while (((Enumeration)localObject1).hasMoreElements())
        {
          localObject3 = (String)((Enumeration)localObject1).nextElement();
          this.directDomainMatcher.addDomain((String)localObject3);
        }
      }
      this.subResourceProxyDomainMatcher.clear();
      this.subResourceProxyDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(179));
      this.subResourceDirectDomainMatcher.clear();
      this.subResourceDirectDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(180));
      this.directUploadDomainMatcher.clear();
      this.directUploadDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(87));
      this.directUploadReferDomainMatcher.clear();
      this.directUploadReferDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(143));
      this.proxyDomainMatcher.clear();
      this.proxyDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(42));
      this.mVideoSameLayerDomainMatcher.clear();
      localObject1 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(71);
      if ((localObject1 != null) && (!((ArrayList)localObject1).isEmpty()))
      {
        this.mVideoSameLayerDomainMatcher.addDomainList((ArrayList)localObject1);
      }
      else
      {
        this.mVideoSameLayerDomainMatcher.addDomain("*.qq.com");
        this.mVideoSameLayerDomainMatcher.addDomain("*servicewechat.com");
      }
      this.mLocalIpAccessDenyDomainMatcher.clear();
      this.mLocalIpAccessDenyDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(77));
      this.mMiniQBVideoSameLayerDomainMatcher.clear();
      this.mMiniQBVideoSameLayerDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(119));
      this.mMiniQBTBSIsForceVideoPagePlay.clear();
      this.mMiniQBTBSIsForceVideoPagePlay.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(120));
      this.mLocalIpAccessAllowDomainMatcher.clear();
      this.mLocalIpAccessAllowDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(77));
      this.mTBSFileChooserBlackDomainMatcher.clear();
      this.mTBSFileChooserBlackDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(276));
      this.mVideoCanPagePlayDomainMatcher.clear();
      this.mVideoCanPagePlayDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(90));
      this.mLbsDontAlertWhiteListMatcher.clear();
      this.mLbsDontAlertWhiteListMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(97));
      this.mDirectAdblockWhiteDomainMatcher.clear();
      this.mDirectAdblockWhiteDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(85));
      this.mDirectAdblockBlackDomainMatcher.clear();
      this.mDirectAdblockBlackDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(86));
      this.mWebRTCinCamera1WhiteList.clear();
      this.mWebRTCinCamera1WhiteList.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(240));
      this.mDownloadFileInterceptNotAPKBlackList.clear();
      this.mDownloadFileInterceptNotAPKBlackList.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(255));
      this.mDeeplinkBlackDomainMatcher.clear();
      this.mDeeplinkBlackDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(95));
      this.mDeeplinkWhiteDomainMatcher.clear();
      this.mDeeplinkWhiteDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(96));
      this.mExplorerDomainMatcher.clear();
      this.mExplorerDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(195));
      this.mDownloadFileInterceptWhiteListMatcher.clear();
      this.mDownloadFileInterceptWhiteListMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(107));
      this.mDownloadFileIntercepFileTypetWhiteListMatcher.clear();
      this.mDownloadFileIntercepFileTypetWhiteListMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(116));
      this.mTBSDirectAdblockEffectWhiteListMatcher.clear();
      this.mTBSDirectAdblockEffectWhiteListMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(98));
      this.mTbsSwitchToMiniQBWhiteListMatcher.clear();
      this.mTbsSwitchToMiniQBWhiteListMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(131));
      this.mAppInSwitchToMiniQBWhiteListMatcher.clear();
      this.mAppInSwitchToMiniQBWhiteListMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(132));
      this.mContentCacheBusinessBlackList.clear();
      this.mContentCacheBusinessBlackList.addAll(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(194));
      this.mContentCacheBusinessWhiteListMatcher.clear();
      this.mContentCacheBusinessWhiteListMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(192));
      this.mContentCacheBusinessBlackListMatcher.clear();
      this.mContentCacheBusinessBlackListMatcher.addDomainAndPathList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(193));
      this.mFollowReferDirectListMatcher.clear();
      this.mFollowReferDirectListMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(197));
      this.mDynamicOfflinePageSaveBusinessURLMatcher.clear();
      this.mDynamicOfflinePageSaveBusinessURLMatcher.addURLWhiteMatcherList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(220));
      this.mDynamicOfflinePageSaveBusinessURLMatcher.addURLBlackMatcherList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(221));
      this.mSPAAdPageReporterWhiteList.clear();
      this.mSPAAdPageReporterWhiteList.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(252));
      this.mQuicDirectWhiteList.clear();
      this.mQuicDirectWhiteList.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(264));
      this.mQuicDirectBlackList.clear();
      this.mQuicDirectBlackList.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(307));
      this.mPerformanceUploadWhiteList.clear();
      this.mPerformanceUploadWhiteList.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(266));
      this.mDomDistillWhiteDomainMatcher.clear();
      this.mDomDistillWhiteDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(271));
      this.mTpgDecBlackList.clear();
      localObject1 = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(308);
      this.mTpgDecBlackList.addDomainList((ArrayList)localObject1);
      this.mForceHTTPDirectWhitelist.clear();
      this.mForceHTTPDirectWhitelist.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(269));
      this.mForceHTTPSDirectWhitelist.clear();
      this.mForceHTTPSDirectWhitelist.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(270));
      this.mForceHTTPSProxyWhitelist.clear();
      this.mForceHTTPSProxyWhitelist.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(273));
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(" - list Matcher: mForceHTTPDirectWhitelist=");
      ((StringBuilder)localObject1).append(this.mForceHTTPDirectWhitelist.toString());
      LogUtils.d("TheSimCardOfGreatKing", ((StringBuilder)localObject1).toString());
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(" - list Matcher: mForceHTTPSDirectWhitelist=");
      ((StringBuilder)localObject1).append(this.mForceHTTPSDirectWhitelist.toString());
      LogUtils.d("TheSimCardOfGreatKing", ((StringBuilder)localObject1).toString());
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(" - list Matcher: mForceHTTPSProxyWhitelist=");
      ((StringBuilder)localObject1).append(this.mForceHTTPSProxyWhitelist.toString());
      LogUtils.d("TheSimCardOfGreatKing", ((StringBuilder)localObject1).toString());
      this.mX5TBSHeaderDomainTypeMatcher.clear();
      this.mX5TBSHeaderDomainTypeMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(280));
      this.mX5HeaderPrivateKeyDomainMatcher.clear();
      this.mX5HeaderPrivateKeyDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(285));
      this.mReportBadJsDomainMatcher.clear();
      this.mReportBadJsDomainMatcher.addDomainList(TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(298));
      this.mSchemeNotInterceptList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(336);
      this.mHeadsupByTBSServer = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(345);
      return;
    }
    finally {}
  }
  
  public void interceptVideoPlay(Object paramObject, Context paramContext, Handler paramHandler)
  {
    TbsUgEngine.getInstance().interceptVideoPlay(paramObject, paramContext, paramHandler);
  }
  
  public boolean isAppInSwitchToMiniQBWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mAppInSwitchToMiniQBWhiteListMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return true;
  }
  
  public boolean isBottomBarEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isBottomBarEnabled();
  }
  
  public boolean isClickImageScanEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isClickImageScanEnabled(paramIX5WebViewBase);
  }
  
  public boolean isDebugMiniQBEnv()
  {
    return Configuration.getInstance(this.mAppContext).isDebugMiniQB();
  }
  
  public boolean isDefaultVideoFullScreenPlay(boolean paramBoolean)
  {
    return PublicSettingManager.getInstance().getVideoIsDefaultFullscreen();
  }
  
  public boolean isDetectDownloadPkgName()
  {
    return Configuration.getInstance(this.mAppContext).shouldDetectDownloadPkgName();
  }
  
  public boolean isDownloadFileInterceptFileTypeWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mDownloadFileIntercepFileTypetWhiteListMatcher.isContainsDomain2(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isDownloadFileInterceptNotAPKDomainBlackList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mDownloadFileInterceptNotAPKBlackList.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isDownloadFileInterceptWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mDownloadFileInterceptWhiteListMatcher.isContainsDomain2(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isDownloadInterceptSwitchMatch(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isDownloadInterceptSwitchMatch();
  }
  
  public boolean isEnableTbsARPage(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return true;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(181);
      if (localArrayList.isEmpty()) {
        return true;
      }
      paramString = UrlUtils.getHost(paramString);
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString) ^ true;
    }
    return true;
  }
  
  public boolean isEnableVideoSameLayer(String paramString, boolean paramBoolean)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    paramBoolean = false;
    if (bool) {
      return false;
    }
    String str = CommonUtils.findFullHost(paramString);
    try
    {
      initDomainList();
      if (!this.mVideoSameLayerDomainMatcher.isContainsDomain(str))
      {
        bool = this.mVideoSameLayerDomainMatcher.isContainsUrl(paramString);
        if (!bool) {}
      }
      else
      {
        paramBoolean = true;
      }
      return paramBoolean;
    }
    catch (Throwable paramString) {}
    return false;
  }
  
  public boolean isFileChooserTBSEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isFileChooserTBSEnabled();
  }
  
  public boolean isForceVideoPagePlay(String paramString, boolean paramBoolean)
  {
    if (TextUtils.isEmpty(paramString)) {
      return isDefaultVideoFullScreenPlay(paramBoolean) ^ true;
    }
    try
    {
      paramString = UrlUtils.getHost(paramString);
      initDomainList();
      boolean bool = this.mVideoCanPagePlayDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
    return isDefaultVideoFullScreenPlay(paramBoolean) ^ true;
  }
  
  public boolean isGameEngineUseSandbox()
  {
    return PublicSettingManager.getInstance().isGameEngineUseSandbox();
  }
  
  public boolean isGameFrameworkEnabled()
  {
    return (Configuration.getInstance(this.mAppContext).isGameFrameworkEnabled()) || (Configuration.getInstance(this.mAppContext).isGameEmbeddedFrameworkEnabled());
  }
  
  public boolean isGinJavaMapEraseDisabled()
  {
    return PublicSettingManager.getInstance().getGinJavaMapEraseDisabled();
  }
  
  public boolean isHeadsUpRiskWebsite(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isHeadsUpRiskWebsite();
  }
  
  public boolean isHeadsUpTaobaoLinkEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isHeadsUpTaobaoLinkEnabled();
  }
  
  public boolean isHeadsUpTranscodingPageEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isHeadsUpTranscodingPageEnabled();
  }
  
  public boolean isHostNameInSwitchToMiniQBWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mTbsSwitchToMiniQBWhiteListMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return true;
  }
  
  public boolean isImageBrowserDisableInPage(String paramString)
  {
    if ((paramString != null) && (!paramString.equals("")))
    {
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(260);
      paramString = UrlUtils.getHost(paramString);
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      if (DefaultConfigurationManager.getInstance().limitImageBrowserBlackDomain())
      {
        localDomainMatcher.addDomain("*.qq.com");
        localDomainMatcher.addDomain("servicewechat.com");
        localDomainMatcher.addDomain("*.tenpay.com");
        localDomainMatcher.addDomain("*.gtimg.com");
      }
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isInMiniProgram(IX5WebViewBase paramIX5WebViewBase)
  {
    return MiniProgramUtil.isInMiniProgram(paramIX5WebViewBase);
  }
  
  public boolean isInterceptDownloadRequestEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isInterceptDownloadRequestEnabled();
  }
  
  public boolean isLastWupReqFromTestEvn(String paramString)
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().isLastWupReqFromTestEvn(paramString);
    }
    return false;
  }
  
  public boolean isLongClickOptMM(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isLongClickOptMM(paramIX5WebViewBase);
  }
  
  public boolean isLongPressImageScanEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isLongPressImageScanEnabled(paramIX5WebViewBase);
  }
  
  public boolean isLongPressMenuEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isLongPressMenuEnabled(paramIX5WebViewBase);
  }
  
  public boolean isLongPressMenuExplorerEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isLongPressMenuExplorerEnabled(paramIX5WebViewBase);
  }
  
  public boolean isLongPressMenuOpenInBrowserEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isLongPressMenuOpenInBrowserEnabled(paramIX5WebViewBase);
  }
  
  public boolean isLongPressMenuRefreshEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isLongPressMenuRefreshEnabled(paramIX5WebViewBase);
  }
  
  public boolean isLongPressMenuScreenShotEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isLongPressMenuScreenShotEnabled();
  }
  
  public boolean isLongPressMenuSelectCopyEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isLongPressMenuSelectCopyEnabled(paramIX5WebViewBase);
  }
  
  public boolean isLongPressQuickSecondMenu_QQ_ThirdApp(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isLongPressQuickSecondMenu_QQ_ThirdApp(paramIX5WebViewBase);
  }
  
  public boolean isLongPressQuickSelectCopyEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isLongPressQuickSelectCopyEnabled(paramIX5WebViewBase);
  }
  
  public boolean isMeizuNightModeEnabled()
  {
    return Configuration.getInstance(this.mAppContext).isEnableMeizuNightMode();
  }
  
  public boolean isMidPageQbNightModeOnLongPressEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isMidPageQbNightFullScreenReadOnLongPressEnabled();
  }
  
  public boolean isMidPageQbOpenBrowserOnLongPressEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isMidPageQbOpenBrowserOnLongPressEnabled();
  }
  
  public boolean isMidPageQbSearchOnLongPressEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isMidPageQbSearchOnLongPressEnabled();
  }
  
  public boolean isMidPageQbTranslateOnLongPressEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isMidPageQbTranslateOnLongPressEnabled();
  }
  
  public boolean isMidPageQbWebviewBubbleEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isMidPageQbWebviewBubbleEnabled();
  }
  
  public boolean isMiniQBDisabled()
  {
    return MiniQBUpgradeManager.getInstance().isMiniQBDisabled();
  }
  
  public boolean isNewLongPressDownloadInterceptOpenQbMethodEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isNewLongPressDownloadInterceptOpenQbMethodEnabled();
  }
  
  public boolean isOpenDebugTbsEnabled()
  {
    return PublicSettingManager.getInstance().getIsOpenDebugTbsEnabled();
  }
  
  public boolean isOpenUrlOnLongPressEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isOpenUrlOnLongPressEnabled(paramIX5WebViewBase);
  }
  
  public boolean isPlatformTypeReportEnabled()
  {
    return Configuration.getInstance(this.mAppContext).isPlatformTypeReportEnabled();
  }
  
  public boolean isQBDownloaded()
  {
    return QBDownloadManager.getInstance().isQBDownloaded();
  }
  
  public boolean isQBPureIncrease(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isQBPureIncrease();
  }
  
  public boolean isQQDomain(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    try
    {
      boolean bool = PublicSettingManager.getInstance().isQQDomain(paramString);
      return bool;
    }
    catch (Throwable paramString) {}
    return false;
  }
  
  public boolean isQQErrorPageLittleGameEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isQQErrorPageLittleGameEnabled();
  }
  
  public boolean isRestrictErrorPageReload(String paramString)
  {
    Object localObject = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(232);
    if (localObject != null)
    {
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        if (paramString.startsWith((String)((Iterator)localObject).next())) {
          return true;
        }
      }
    }
    return false;
  }
  
  public boolean isSafeDownloadInterceptEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isSafeDownloadInterceptEnabled();
  }
  
  public boolean isSchemeInDeeplinkBlackList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mDeeplinkBlackDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isSchemeInDeeplinkWhiteList(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mDeeplinkWhiteDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public boolean isSchemeIntercept(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    try
    {
      initDomainList();
      int j = this.mSchemeNotInterceptList.size();
      int i = 0;
      while (i < j)
      {
        boolean bool = paramString.equals(this.mSchemeNotInterceptList.get(i));
        if (bool) {
          return false;
        }
        i += 1;
      }
      return true;
    }
    catch (Throwable paramString) {}
    return false;
  }
  
  public boolean isSearchOnLongPressEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isSearchOnLongPressEnabled(paramIX5WebViewBase);
  }
  
  public boolean isShouldContentCacheCurrentFrameWhenJsLocation(String paramString)
  {
    try
    {
      initDomainList();
      int i = 0;
      while (i < this.mContentCacheBusinessBlackList.size())
      {
        if (paramString.startsWith((String)this.mContentCacheBusinessBlackList.get(i)))
        {
          LogUtils.d(TAG, "isShouldContentCacheCurrentFrameWhenJsLocation result false");
          return false;
        }
        i += 1;
      }
      return true;
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public boolean isShouldInterceptMttbrowser()
  {
    return Configuration.getInstance(this.mAppContext).isShouldInterceptMttbrowser();
  }
  
  public boolean isShouldInterceptMttbrowserUseMiniQb()
  {
    return Configuration.getInstance(this.mAppContext).isShouldInterceptMttbrowserUseMiniQb();
  }
  
  public boolean isTbsARDenyPermisionRequest(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(184);
      if (localArrayList.isEmpty()) {
        return false;
      }
      paramString = UrlUtils.getHost(paramString);
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isTbsAREnable(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isTbsAREnable();
  }
  
  public boolean isTbsARGrantPermisionRequest(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.equals("")) {
        return false;
      }
      ArrayList localArrayList = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(183);
      if (localArrayList.isEmpty()) {
        return false;
      }
      paramString = UrlUtils.getHost(paramString);
      DomainMatcher localDomainMatcher = new DomainMatcher();
      localDomainMatcher.addDomainList(localArrayList);
      return localDomainMatcher.isContainsDomain(paramString);
    }
    return false;
  }
  
  public boolean isTbsClipBoardEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isTbsClipBoardEnabled();
  }
  
  public boolean isTbsJsapiEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isTbsJsapiEnabled();
  }
  
  public boolean isTbsQrCodeShareEnabled()
  {
    return Configuration.getInstance(this.mAppContext).isTbsQrCodeShareEnabled();
  }
  
  public boolean isTestMiniQBEnv()
  {
    return MiniQBUpgradeManager.getInstance().isTestMiniQBEnv();
  }
  
  public boolean isTranslateOnLongPressEnabled(Context paramContext, IX5WebViewBase paramIX5WebViewBase)
  {
    return Configuration.getInstance(paramContext).isTranslateOnLongPressEnabled(paramIX5WebViewBase);
  }
  
  public boolean isWXWholePageTranslateEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isWXWholePageTranslateEnabled();
  }
  
  public boolean isWeChatCrashMonitorEnable()
  {
    return Configuration.getInstance(this.mAppContext).isWeChatCrashMonitorEnable();
  }
  
  public boolean isX5CameraEnabled(Context paramContext)
  {
    return Configuration.getInstance(paramContext).isX5CameraEnabled();
  }
  
  public boolean isX5CookieIsolationEnabled()
  {
    return PublicSettingManager.getInstance().getX5CookieIsolationEnabled();
  }
  
  public boolean isX5CookieIsolationEnabledForTBS()
  {
    return PublicSettingManager.getInstance().isX5CookieIsolationEnabledForTBS();
  }
  
  public int kingCardUserProxyJudgeCondition()
  {
    return 0;
  }
  
  public String logDebugExec(String paramString)
  {
    try
    {
      paramString = new JSONObject(paramString);
      String str1 = String.valueOf(paramString.get("action"));
      String str2 = String.valueOf(paramString.get("hours"));
      String str3 = String.valueOf(paramString.get("level"));
      if ("show".equalsIgnoreCase(str1)) {
        return exportAllLogToShow(String.valueOf(paramString.get("part")), str2, str3);
      }
      if ("file".equalsIgnoreCase(str1)) {
        return exportAllLogToFile(str2, str3);
      }
      if ("image".equalsIgnoreCase(str1)) {
        return exportAllLogToImage(str2, str3);
      }
      if ("report".equalsIgnoreCase(str1))
      {
        paramString = exportAllLogToReport(str2, str3);
        return paramString;
      }
      return null;
    }
    catch (Exception paramString) {}
    return null;
  }
  
  public void monitorAppRemoved(Context paramContext)
  {
    try
    {
      TbsUgEngine.getInstance().monitorAppRemoved(paramContext, QUAUtils.getQUA2_V3());
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public Context newPluginContextWrapper(Context paramContext, String paramString)
  {
    TBSResources.setContext(paramContext, paramString, true);
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("newPluginContextWrapper called -- (");
    localStringBuilder.append(paramContext);
    localStringBuilder.append(",");
    localStringBuilder.append(paramString);
    localStringBuilder.append(")");
    LogUtils.d(str, localStringBuilder.toString());
    return new PluginContextWrapper(paramContext, paramString);
  }
  
  public int nightModeInLongPressthreeswitchLevel(Context paramContext)
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().nightModeInLongPressthreeswitchLevel();
    }
    return 0;
  }
  
  public void notifyHttpsTunnelExpired() {}
  
  public boolean onLongClickSearch(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("mttbrowser://url=");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(",product=");
    localStringBuilder.append("TBS");
    localStringBuilder.append(",packagename=");
    localStringBuilder.append(paramContext.getApplicationContext().getPackageName());
    localStringBuilder.append(",version=");
    localStringBuilder.append(TbsBaseModuleShell.getTesVersionName());
    localStringBuilder.append(",from");
    localStringBuilder.append("fromWebviewSearch");
    localStringBuilder.append(",ChannelID=");
    localStringBuilder.append(paramContext.getApplicationContext().getPackageName());
    localStringBuilder.append(",PosID=");
    localStringBuilder.append(paramString2);
    paramBundle.putBoolean("notShowWhenInstalled", true);
    paramBundle.putString("scheme", localStringBuilder.toString());
    return TbsUgEngine.getInstance().showUgDialog(paramContext, paramString2, paramBundle, null) == 0;
  }
  
  public boolean onOpenInBrowserClick(Context paramContext, String paramString, Bundle paramBundle)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("mttbrowser://url=");
    localStringBuilder.append(paramString);
    localStringBuilder.append(",product=");
    localStringBuilder.append("TBS");
    localStringBuilder.append(",packagename=");
    localStringBuilder.append(paramContext.getApplicationContext().getPackageName());
    localStringBuilder.append(",version=");
    localStringBuilder.append(TbsBaseModuleShell.getTesVersionName());
    localStringBuilder.append(",from");
    localStringBuilder.append(null);
    localStringBuilder.append(",ChannelID=");
    localStringBuilder.append(paramContext.getApplicationContext().getPackageName());
    localStringBuilder.append(",PosID=");
    localStringBuilder.append("8");
    paramBundle.putBoolean("notShowWhenInstalled", true);
    paramBundle.putString("scheme", localStringBuilder.toString());
    return TbsUgEngine.getInstance().showUgDialog(paramContext, "8", paramBundle, null) == 0;
  }
  
  public void onPageLoadFinished(String paramString)
  {
    removeExpiredCookie();
    doWupTask();
    QBDownloadManager.getInstance().setLastOpenUrl(paramString);
    BrowserExecutorSupplier.forIoTasks().execute(new Runnable()
    {
      public void run()
      {
        MiniQBUpgradeManager localMiniQBUpgradeManager = MiniQBUpgradeManager.getInstance();
        if (localMiniQBUpgradeManager != null) {
          localMiniQBUpgradeManager.doMiniQBUpgradeTask();
        }
      }
    });
    X5CoreBeaconUploader.getInstance(this.mAppContext).doOverNightUpload();
    WebViewPool.createCacheWebView(this.mAppContext);
    try
    {
      TbsPrecheck.startTbsCheckTask(this.mAppContext, TBSShellFactory.getTbsShellDelegate().getTbsInstallLocation(), true, "TBS_PRECHECK_REPORT_ON_PAGE_FINISHED");
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    TbsAdUserInfoCollector.getInstance().onPageFinish(paramString);
  }
  
  public void onWebViewAppExit()
  {
    TBSStatManager.getInstance().save();
    TBSPageLoadInfoManager.getInstance(this.mAppContext).saveRunable();
    X5CoreBeaconUploader.getInstance(this.mAppContext).savetobeacon();
  }
  
  public void onWebViewDestroy()
  {
    TBSStatManager.getInstance().save();
    TBSPageLoadInfoManager.getInstance(this.mAppContext).saveRunable();
    X5CoreBeaconUploader.getInstance(this.mAppContext).savetobeacon();
  }
  
  public void onWebViewDetachedFromWindow()
  {
    TBSStatManager.getInstance().save();
    TBSPageLoadInfoManager.getInstance(this.mAppContext).saveRunable();
    X5CoreBeaconUploader.getInstance(this.mAppContext).savetobeacon();
  }
  
  public void onWebViewPause()
  {
    TBSStatManager.getInstance().save();
    TBSPageLoadInfoManager.getInstance(this.mAppContext).saveRunable();
    X5CoreBeaconUploader.getInstance(this.mAppContext).savetobeacon();
  }
  
  public void onWindowFocusChanged(Context paramContext, boolean paramBoolean)
  {
    try
    {
      if (Configuration.getInstance(this.mAppContext).requestActiveQBEnable())
      {
        TbsUgEngine.getInstance().onWindowFocusChanged(paramContext, paramBoolean);
        return;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public void openBrowserListWithGeneralDownloader(final String paramString1, Context paramContext, final Intent paramIntent, final File paramFile, String paramString2, String paramString3, String paramString4, final String paramString5, final String paramString6, final String paramString7, Map<String, Drawable> paramMap)
  {
    paramFile = new WeakReference(paramContext);
    paramString3 = PublicSettingManager.getInstance().getTBSPickedDefaultFileOpener();
    if ((paramString3 != null) && (paramString3.length() > 0)) {}
    try
    {
      openInTencentFile(paramContext, paramIntent, paramString3, paramString5, paramString7);
      return;
    }
    catch (Throwable paramString3)
    {
      for (;;) {}
    }
    paramIntent.setPackage(null);
    userBehaviorStatistics("BZTW004");
    TBSBrowserList.OpenBrowserList(paramContext, paramIntent, paramString2, paramMap, new TBSDialogBuilder.IntentFilterCallback()
    {
      public Drawable getAppIcon()
      {
        return TBSResources.getDrawable("tencent_file_application_icon");
      }
      
      public String getAppLabel()
      {
        return "腾讯文件";
      }
      
      public String getAppPackageName()
      {
        return "com.tencent.FileManager";
      }
      
      public String getDialogStyle()
      {
        return null;
      }
      
      public String getFileTips()
      {
        return TBSResources.getString("x5_tbs_download_file_tips_for_tencent_file");
      }
      
      public int getFileType()
      {
        String str = paramString1;
        if (TextUtils.isEmpty(str)) {
          return 4;
        }
        if ((!StringUtils.isStringEqual(str, "video/3gpp")) && (!StringUtils.isStringEqual(str, "video/x-ms-asf")) && (!StringUtils.isStringEqual(str, "video/x-msvideo")) && (!StringUtils.isStringEqual(str, "video/vnd.mpegurl")) && (!StringUtils.isStringEqual(str, "video/x-m4v")) && (!StringUtils.isStringEqual(str, "video/quicktime")) && (!StringUtils.isStringEqual(str, "video/mp4")) && (!StringUtils.isStringEqual(str, "video/mpeg")) && (!StringUtils.isStringEqual(str, "audio/x-pn-realaudio")) && (!StringUtils.isStringEqual(str, "application/vnd.mpohun.certificate")) && (!StringUtils.isStringEqual(str, "audio/x-ms-wmv")) && (!StringUtils.isStringEqual(str, "audio/x-mpeg")) && (!StringUtils.isStringEqual(str, "audio/mp4a-latm")) && (!StringUtils.isStringEqual(str, "audio/mpeg")) && (!StringUtils.isStringEqual(str, "audio/ogg")) && (!StringUtils.isStringEqual(str, "audio/x-wav")) && (!StringUtils.isStringEqual(str, "audio/x-mpegurl")))
        {
          if ((!StringUtils.isStringEqual(str, "application/x-gtar")) && (!StringUtils.isStringEqual(str, "application/x-gzip")) && (!StringUtils.isStringEqual(str, "application/x-tar")) && (!StringUtils.isStringEqual(str, "application/x-compressed")) && (!StringUtils.isStringEqual(str, "application/x-zip-compressed")) && (!StringUtils.isStringEqual(str, "application/x-rar-compressed")) && (!StringUtils.isStringEqual(str, "application/x-compress")) && (!StringUtils.isStringEqual(str, "application/zip")))
          {
            if ((!StringUtils.isStringEqual(str, "text/plain")) && (!StringUtils.isStringEqual(str, "application/msword")) && (!StringUtils.isStringEqual(str, "text/html")) && (!StringUtils.isStringEqual(str, "application/pdf")) && (!StringUtils.isStringEqual(str, "application/vnd.ms-powerpoint")) && (!StringUtils.isStringEqual(str, "application/rtf")) && (!StringUtils.isStringEqual(str, "application/vnd.ms-works")) && (!StringUtils.isStringEqual(str, "text/xml")) && (!StringUtils.isStringEqual(str, "text/plain"))) {
              return 4;
            }
            return 2;
          }
          return 3;
        }
        return 1;
      }
      
      public boolean installDownloadFile(Context paramAnonymousContext, String paramAnonymousString1, String paramAnonymousString2, Bundle paramAnonymousBundle)
      {
        return TencentFileDownloadManager.getInstance().installDownloadFile(paramAnonymousContext, paramAnonymousString1, paramAnonymousString2, paramAnonymousBundle);
      }
      
      public boolean isDownloadIntercept()
      {
        return "AWTDL7".equals(paramString6);
      }
      
      public boolean isDownloadStarted()
      {
        return TencentFileDownloadManager.getInstance().isDownloading();
      }
      
      public boolean isDownloaded()
      {
        return TencentFileDownloadManager.getInstance().isFileDownloaded();
      }
      
      public boolean isInstalled()
      {
        Object localObject2 = (Context)paramFile.get();
        Object localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = SmttServicePartner.this.mAppContext;
        }
        if (localObject1 == null) {
          return false;
        }
        localObject1 = SmttServicePartner.this.mAppContext.getPackageManager();
        if (localObject1 != null) {
          try
          {
            localObject1 = ((PackageManager)localObject1).getPackageInfo(getAppPackageName(), 0);
            if ((localObject1 != null) && (((PackageInfo)localObject1).versionCode >= 4120000))
            {
              localObject2 = SmttServicePartner.TAG;
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("tencentfile versionCode:");
              localStringBuilder.append(((PackageInfo)localObject1).versionCode);
              LogUtils.d((String)localObject2, localStringBuilder.toString());
              return true;
            }
          }
          catch (Throwable localThrowable)
          {
            localThrowable.printStackTrace();
          }
        }
        return false;
      }
      
      public boolean isOpenBrowserInLongPress()
      {
        return "BDTT6".equals(paramString5);
      }
      
      public boolean isSearchInLongPress()
      {
        return "ZZNS3".equals(paramString5);
      }
      
      public void onDialogDismissed() {}
      
      public void onSendingIntent(Intent paramAnonymousIntent, ResolveInfo paramAnonymousResolveInfo, boolean paramAnonymousBoolean)
      {
        Context localContext = (Context)paramFile.get();
        if (localContext == null) {
          return;
        }
        try
        {
          SmttServicePartner.this.openInTencentFile(localContext, paramAnonymousIntent, paramAnonymousResolveInfo.activityInfo.packageName, paramString5, paramString7);
          if (!paramAnonymousBoolean) {
            PublicSettingManager.getInstance().setTBSPickedDefaultFileOpener(paramAnonymousResolveInfo.activityInfo.packageName);
          }
          return;
        }
        catch (Throwable paramAnonymousIntent) {}
      }
      
      public void onSetListener(TBSDialogBuilder.DownloadListener paramAnonymousDownloadListener)
      {
        SmttServicePartner.access$202(SmttServicePartner.this, paramAnonymousDownloadListener);
      }
      
      public int onStartDownload(String paramAnonymousString, TBSDialogBuilder.DownloadListener paramAnonymousDownloadListener)
      {
        paramAnonymousString = (Context)paramFile.get();
        if (paramAnonymousString == null) {
          return -1;
        }
        if (paramAnonymousDownloadListener != null) {
          SmttServicePartner.access$202(SmttServicePartner.this, paramAnonymousDownloadListener);
        }
        if (SmttServicePartner.this.mTfDownloadManagerListener == null) {
          SmttServicePartner.access$602(SmttServicePartner.this, new QBDownloadListener()
          {
            public void onDownloadFailed(boolean paramAnonymous2Boolean, Bundle paramAnonymous2Bundle) {}
            
            public void onDownloadPause(boolean paramAnonymous2Boolean, int paramAnonymous2Int) {}
            
            public void onDownloadProgress(boolean paramAnonymous2Boolean, int paramAnonymous2Int)
            {
              if (SmttServicePartner.this.mDownloadListener != null) {
                SmttServicePartner.this.mDownloadListener.onProgress(paramAnonymous2Int);
              }
            }
            
            public void onDownloadResume(boolean paramAnonymous2Boolean, int paramAnonymous2Int) {}
            
            public void onDownloadStart(boolean paramAnonymous2Boolean)
            {
              if (SmttServicePartner.this.mDownloadListener != null) {
                SmttServicePartner.this.mDownloadListener.onStarted();
              }
            }
            
            public void onDownloadSucess(boolean paramAnonymous2Boolean, String paramAnonymous2String, Bundle paramAnonymous2Bundle)
            {
              if (SmttServicePartner.this.mDownloadListener != null) {
                SmttServicePartner.this.mDownloadListener.onFinished(null);
              }
            }
          });
        }
        TencentFileDownloadManager.getInstance().registerDownloadListener(SmttServicePartner.this.mTfDownloadManagerListener);
        paramAnonymousDownloadListener = new Bundle();
        paramAnonymousDownloadListener.putString("param_key_positionid", "0");
        if (isDownloadIntercept())
        {
          paramAnonymousDownloadListener.putString("param_key_functionid", "1101");
          paramAnonymousDownloadListener.putString("param_key_featureid", "download");
        }
        else
        {
          paramAnonymousDownloadListener.putString("param_key_functionid", "3003");
          paramAnonymousDownloadListener.putString("param_key_featureid", "webview");
        }
        String str = paramIntent.getData().toString();
        paramAnonymousDownloadListener.putParcelable("intentForTf", paramIntent.getParcelableExtra("intentForTf"));
        TencentFileDownloadManager.getInstance().startDownload(paramAnonymousString, false, false, "AATQB76", "AATQB77", null, null, null, null, null, str, true, paramAnonymousDownloadListener);
        return 0;
      }
      
      public void openApp()
      {
        Intent localIntent = paramIntent;
        if (localIntent != null)
        {
          localIntent = (Intent)localIntent.getParcelableExtra("intentForTf");
          if ((localIntent != null) && (SmttServicePartner.this.mAppContext != null)) {
            SmttServicePartner.this.mAppContext.startActivity(localIntent);
          }
        }
      }
    });
  }
  
  public void openBrowserListWithQBDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    openBrowserListWithQBDownloader(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, null);
  }
  
  public void openBrowserListWithQBDownloader(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap)
  {
    openBrowserListWithQBDownloader(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, null, null);
  }
  
  public void openBrowserListWithQBDownloader(final String paramString1, Context paramContext, final Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, final String paramString5, final String paramString6, final String paramString7, Map<String, Drawable> paramMap, final Bundle paramBundle)
  {
    mDownloadQbStatKey = paramString6;
    paramString3 = new TBSDialogBuilder.IntentFilterCallback()
    {
      public Drawable getAppIcon()
      {
        if ((isOpenBrowserInLongPress()) && (SmttServicePartner.this.getQbIconTypeInLongClick() == 1)) {
          return TBSResources.getDrawable("x5_long_click_qb_logo");
        }
        return TBSResources.getDrawable("application_icon");
      }
      
      public String getAppLabel()
      {
        return "QQ浏览器";
      }
      
      public String getAppPackageName()
      {
        return "com.tencent.mtt";
      }
      
      public String getDialogStyle()
      {
        Bundle localBundle = paramBundle;
        if ((localBundle != null) && (localBundle.getString("PARAM_KEY_FORCE_DIALOG_STYLE") != null)) {
          return paramBundle.getString("PARAM_KEY_FORCE_DIALOG_STYLE", "normal");
        }
        if (isDownloadIntercept()) {
          return SmttServicePartner.this.getBrowserListDialogStyle("download");
        }
        if (isOpenBrowserInLongPress()) {
          return SmttServicePartner.this.getBrowserListDialogStyle("webview");
        }
        return "normal";
      }
      
      public String getFileTips()
      {
        if (isDownloadIntercept()) {
          return "高速专业的下载工具";
        }
        if (isOpenBrowserInLongPress())
        {
          String str = SmttServicePartner.this.getX5LongClickPopupMenuSubText();
          if (!TextUtils.isEmpty(str)) {
            return str;
          }
        }
        int i = getFileType();
        if (i == 1) {
          return TBSResources.getString("x5_tbs_download_file_tips_video");
        }
        if (i == 2) {
          return TBSResources.getString("x5_tbs_download_file_tips_document");
        }
        if (i == 3) {
          return TBSResources.getString("x5_tbs_download_file_tips_archive");
        }
        return TBSResources.getString("x5_tbs_wechat_activity_picker_label_recommend");
      }
      
      public int getFileType()
      {
        String str = paramString1;
        if (TextUtils.isEmpty(str)) {
          return 4;
        }
        if ((!StringUtils.isStringEqual(str, "video/3gpp")) && (!StringUtils.isStringEqual(str, "video/x-ms-asf")) && (!StringUtils.isStringEqual(str, "video/x-msvideo")) && (!StringUtils.isStringEqual(str, "video/vnd.mpegurl")) && (!StringUtils.isStringEqual(str, "video/x-m4v")) && (!StringUtils.isStringEqual(str, "video/quicktime")) && (!StringUtils.isStringEqual(str, "video/mp4")) && (!StringUtils.isStringEqual(str, "video/mpeg")) && (!StringUtils.isStringEqual(str, "audio/x-pn-realaudio")) && (!StringUtils.isStringEqual(str, "application/vnd.mpohun.certificate")) && (!StringUtils.isStringEqual(str, "audio/x-ms-wmv")) && (!StringUtils.isStringEqual(str, "audio/x-mpeg")) && (!StringUtils.isStringEqual(str, "audio/mp4a-latm")) && (!StringUtils.isStringEqual(str, "audio/mpeg")) && (!StringUtils.isStringEqual(str, "audio/ogg")) && (!StringUtils.isStringEqual(str, "audio/x-wav")) && (!StringUtils.isStringEqual(str, "audio/x-mpegurl")))
        {
          if ((!StringUtils.isStringEqual(str, "application/x-gtar")) && (!StringUtils.isStringEqual(str, "application/x-gzip")) && (!StringUtils.isStringEqual(str, "application/x-tar")) && (!StringUtils.isStringEqual(str, "application/x-compressed")) && (!StringUtils.isStringEqual(str, "application/x-zip-compressed")) && (!StringUtils.isStringEqual(str, "application/x-rar-compressed")) && (!StringUtils.isStringEqual(str, "application/x-compress")) && (!StringUtils.isStringEqual(str, "application/zip")))
          {
            if ((!StringUtils.isStringEqual(str, "text/plain")) && (!StringUtils.isStringEqual(str, "application/msword")) && (!StringUtils.isStringEqual(str, "text/html")) && (!StringUtils.isStringEqual(str, "application/pdf")) && (!StringUtils.isStringEqual(str, "application/vnd.ms-powerpoint")) && (!StringUtils.isStringEqual(str, "application/rtf")) && (!StringUtils.isStringEqual(str, "application/vnd.ms-works")) && (!StringUtils.isStringEqual(str, "text/xml")) && (!StringUtils.isStringEqual(str, "text/plain"))) {
              return 4;
            }
            return 2;
          }
          return 3;
        }
        return 1;
      }
      
      public boolean installDownloadFile(Context paramAnonymousContext, String paramAnonymousString1, String paramAnonymousString2, Bundle paramAnonymousBundle)
      {
        return QBDownloadManager.getInstance().installDownloadFile(paramAnonymousContext, paramAnonymousString1, paramAnonymousString2, paramAnonymousBundle);
      }
      
      public boolean isDownloadIntercept()
      {
        return "AWTDL7".equals(paramString6);
      }
      
      public boolean isDownloadStarted()
      {
        return QBDownloadManager.getInstance().isQBDownloading();
      }
      
      public boolean isDownloaded()
      {
        return QBDownloadManager.getInstance().isQBDownloaded();
      }
      
      public boolean isInstalled()
      {
        return false;
      }
      
      public boolean isOpenBrowserInLongPress()
      {
        return "BDTT6".equals(paramString5);
      }
      
      public boolean isSearchInLongPress()
      {
        return "ZZNS3".equals(paramString5);
      }
      
      public void onDialogDismissed() {}
      
      public void onSendingIntent(Intent paramAnonymousIntent, ResolveInfo paramAnonymousResolveInfo, boolean paramAnonymousBoolean)
      {
        Context localContext = (Context)this.val$rContext.get();
        if (localContext == null) {
          return;
        }
        for (;;)
        {
          try
          {
            SmttServicePartner localSmttServicePartner = SmttServicePartner.this;
            String str2 = paramAnonymousResolveInfo.activityInfo.packageName;
            String str3 = paramString5;
            String str4 = paramString7;
            Bundle localBundle = paramBundle;
            if (isDownloadIntercept())
            {
              str1 = "downloadintercept";
              localSmttServicePartner.openInBrowser(localContext, paramAnonymousIntent, str2, str3, str4, localBundle, str1);
              if (!paramAnonymousBoolean) {
                PublicSettingManager.getInstance().setTBSPickedDefaultBrowser(paramAnonymousResolveInfo.activityInfo.packageName);
              }
              return;
            }
          }
          catch (Throwable paramAnonymousIntent)
          {
            return;
          }
          String str1 = null;
        }
      }
      
      public void onSetListener(TBSDialogBuilder.DownloadListener paramAnonymousDownloadListener)
      {
        SmttServicePartner.access$202(SmttServicePartner.this, paramAnonymousDownloadListener);
      }
      
      public int onStartDownload(String paramAnonymousString, TBSDialogBuilder.DownloadListener paramAnonymousDownloadListener)
      {
        Context localContext = (Context)this.val$rContext.get();
        if (localContext == null) {
          return -1;
        }
        if (paramAnonymousDownloadListener != null) {
          SmttServicePartner.access$202(SmttServicePartner.this, paramAnonymousDownloadListener);
        }
        if (SmttServicePartner.this.mQbDownloadManagerListener == null) {
          SmttServicePartner.access$302(SmttServicePartner.this, new QBDownloadListener()
          {
            public void onDownloadFailed(boolean paramAnonymous2Boolean, Bundle paramAnonymous2Bundle) {}
            
            public void onDownloadPause(boolean paramAnonymous2Boolean, int paramAnonymous2Int) {}
            
            public void onDownloadProgress(boolean paramAnonymous2Boolean, int paramAnonymous2Int)
            {
              if (SmttServicePartner.this.mDownloadListener != null) {
                SmttServicePartner.this.mDownloadListener.onProgress(paramAnonymous2Int);
              }
            }
            
            public void onDownloadResume(boolean paramAnonymous2Boolean, int paramAnonymous2Int) {}
            
            public void onDownloadStart(boolean paramAnonymous2Boolean)
            {
              if (SmttServicePartner.this.mDownloadListener != null) {
                SmttServicePartner.this.mDownloadListener.onStarted();
              }
            }
            
            public void onDownloadSucess(boolean paramAnonymous2Boolean, String paramAnonymous2String, Bundle paramAnonymous2Bundle)
            {
              if (SmttServicePartner.this.mDownloadListener != null) {
                SmttServicePartner.this.mDownloadListener.onFinished(null);
              }
            }
          });
        }
        QBDownloadManager.getInstance().registerQBDownloadListener(SmttServicePartner.this.mQbDownloadManagerListener);
        paramAnonymousDownloadListener = paramIntent.getData().toString();
        if (localContext.getApplicationInfo() != null) {
          paramAnonymousString = localContext.getApplicationInfo().packageName;
        } else {
          paramAnonymousString = null;
        }
        if ((paramAnonymousDownloadListener == null) || (!paramAnonymousDownloadListener.endsWith(".apk")))
        {
          localObject = paramString1;
          if ((localObject == null) || (!((String)localObject).equalsIgnoreCase("application/vnd.android.package-archive"))) {}
        }
        else
        {
          i = 1;
          break label150;
        }
        int i = 0;
        label150:
        Object localObject = new Bundle();
        ((Bundle)localObject).putString("param_key_positionid", "0");
        if (isDownloadIntercept())
        {
          ((Bundle)localObject).putString("param_key_functionid", "1101");
          ((Bundle)localObject).putString("param_key_featureid", "download");
          if (("com.tencent.mm".equals(paramAnonymousString)) && (i != 0)) {
            ((Bundle)localObject).putString("param_key_download_channel", "11265");
          } else if (("com.tencent.mm".equals(paramAnonymousString)) && (i == 0)) {
            ((Bundle)localObject).putString("param_key_download_channel", "11147");
          } else if ("com.tencent.mobileqq".equals(paramAnonymousString)) {
            ((Bundle)localObject).putString("param_key_download_channel", "11053");
          }
          Bundle localBundle = paramBundle;
          if (localBundle != null)
          {
            ((Bundle)localObject).putString("param_key_cookie", localBundle.getString("PARAM_KEY_COOKIE"));
            ((Bundle)localObject).putString("param_key_mimetype", paramBundle.getString("PARAM_KEY_MIMETYPE"));
            ((Bundle)localObject).putString("param_key_filename", paramBundle.getString("PARAM_KEY_FILENAME"));
          }
        }
        else if (isOpenBrowserInLongPress())
        {
          ((Bundle)localObject).putString("param_key_functionid", "3003");
          ((Bundle)localObject).putString("param_key_featureid", "webview");
          if ("com.tencent.mm".equals(paramAnonymousString)) {
            ((Bundle)localObject).putString("param_key_download_channel", "10947");
          } else if ("com.tencent.mobileqq".equals(paramAnonymousString)) {
            ((Bundle)localObject).putString("param_key_download_channel", "11314");
          } else {
            ((Bundle)localObject).putString("param_key_download_channel", "11315");
          }
        }
        if ("hw".equals(getDialogStyle())) {
          if ("com.tencent.mm".equals(paramAnonymousString)) {
            ((Bundle)localObject).putString("param_key_download_channel", "11367");
          } else if ("com.tencent.mobileqq".equals(paramAnonymousString)) {
            ((Bundle)localObject).putString("param_key_download_channel", "11366");
          }
        }
        paramAnonymousString = paramString6;
        if ((paramAnonymousString != null) && (paramAnonymousString.equals("ZZNS2"))) {
          ((Bundle)localObject).putString("param_key_download_channel", "11423");
        }
        QBDownloadManager.getInstance().startDownload(localContext, false, false, "AATQB76", "AATQB77", null, null, null, null, null, paramAnonymousDownloadListener, true, (Bundle)localObject);
        paramAnonymousString = paramString6;
        if (paramAnonymousString != null) {
          SmttServicePartner.this.userBehaviorStatistics(paramAnonymousString);
        }
        return 0;
      }
      
      public void openApp() {}
    };
    paramString1 = paramIntent.getStringExtra("search_direct");
    boolean bool3 = false;
    boolean bool2 = false;
    boolean bool1;
    if ((paramString6 != null) && (paramString6.equals("ZZNS2")) && ("true".equals(paramString1)))
    {
      bool1 = bool2;
      try
      {
        if (Configuration.getInstance(this.mAppContext).isDownloadInterceptPluginEnabled()) {
          bool1 = TbsUgEngine.getInstance().openBrowserListDialog("hwsearch", paramContext, paramIntent, paramMap, paramString3);
        }
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
        bool1 = bool2;
      }
      if (!bool1) {
        TBSBrowserList.OpenHWSearchBrowserList(paramContext, paramIntent, paramMap, paramString3);
      }
    }
    else if ("hw".equals(paramString3.getDialogStyle()))
    {
      bool1 = bool3;
      try
      {
        if (Configuration.getInstance(this.mAppContext).isDownloadInterceptPluginEnabled()) {
          bool1 = TbsUgEngine.getInstance().openBrowserListDialog("hw", paramContext, paramIntent, paramMap, paramString3);
        }
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
        bool1 = bool3;
      }
      if (!bool1) {
        TBSBrowserList.OpenHWBrowserList(paramContext, paramIntent, paramMap, paramString3);
      }
    }
    else
    {
      paramString1 = PublicSettingManager.getInstance().getTBSPickedDefaultBrowser();
      if (!PackageUtils.isInstalledPKGExist(paramString1, paramContext))
      {
        PublicSettingManager.getInstance().setTBSPickedDefaultBrowser("");
        paramString1 = "";
      }
      if (TextUtils.isEmpty(paramString1)) {}
    }
    try
    {
      if (!paramString3.isDownloadIntercept()) {
        break label343;
      }
      paramFile = "downloadintercept";
    }
    catch (Throwable paramString1)
    {
      for (;;)
      {
        continue;
        paramFile = null;
      }
    }
    openInBrowser(paramContext, paramIntent, paramString1, paramString5, paramString7, paramBundle, paramFile);
    return;
    paramIntent.setPackage(null);
    TBSBrowserList.OpenBrowserList(paramContext, paramIntent, paramString2, paramMap, paramString3);
    if (paramString3.isDownloadIntercept())
    {
      userBehaviorStatistics("BZWQ011");
      return;
    }
    if (paramString3.isOpenBrowserInLongPress()) {
      userBehaviorStatistics("BZCA006");
    }
  }
  
  public void openInBrowser(Context paramContext, Intent paramIntent, String paramString1, String paramString2, String paramString3, Bundle paramBundle, String paramString4)
  {
    if ("com.tencent.mtt".equals(paramString1)) {}
    try
    {
      Uri localUri = paramIntent.getData();
      String str1 = paramContext.getApplicationInfo().packageName;
      String str2 = paramBundle.getString("PARAM_KEY_COOKIE");
      String str3 = paramBundle.getString("PARAM_KEY_FILENAME");
      paramBundle = paramBundle.getString("PARAM_KEY_MIMETYPE");
      if (TextUtils.isEmpty(paramString4))
      {
        if (openUrlInQQBrowserWithReport(paramContext, localUri.toString(), paramString2, str1, paramString3)) {
          userBehaviorStatistics(paramString2);
        }
      }
      else
      {
        boolean bool = openUrlAndDownloadInQQBrowserWithReport(paramContext, true, "qb://home", localUri.toString(), str2, str3, paramBundle, paramString2, str1, paramString3, paramString4);
        if (bool) {
          return;
        }
      }
    }
    catch (Throwable paramString2)
    {
      for (;;) {}
    }
    paramIntent.setPackage(paramString1);
    paramContext.startActivity(paramIntent);
    if ("AWTDL7".equals(mDownloadQbStatKey)) {
      userBehaviorStatistics("AWTDL8");
    }
  }
  
  public View openLandPage(Context paramContext, String paramString, ViewGroup paramViewGroup)
  {
    return TbsAdProxy.getInstance(this.mAppContext).openLandPage(paramContext, paramString, paramViewGroup);
  }
  
  public boolean openTBSFileChooser(IX5WebViewBase paramIX5WebViewBase, ValueCallback<Uri[]> paramValueCallback, int paramInt, File paramFile, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Map<String, Drawable> paramMap)
  {
    TBSFileChooser.OpenTBSFileChooser(paramIX5WebViewBase.getView().getContext(), paramIX5WebViewBase, paramValueCallback, paramInt);
    return false;
  }
  
  public boolean openUrlAndDownloadInQQBrowserWithReport(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9)
  {
    return TBSIntentUtils.openUrlAndDownloadInQQBrowserWithReport(paramContext, paramBoolean, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9);
  }
  
  public boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    return openUrlInQQBrowserWithReportAndRecordAnchor(paramContext, paramString1, paramString2, paramString3, paramString4, new Point(0, 0), new Point(0, 0));
  }
  
  public boolean openUrlInQQBrowserWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    return IntentUtils.openUrlInQQBrowserWithReport(paramContext, paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public boolean openUrlInQQBrowserWithReportAndRecordAnchor(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, Point paramPoint1, Point paramPoint2)
  {
    return TBSIntentUtils.openUrlInQQBrowserWithReport(paramContext, paramString1, paramString2, paramString3, paramString4, paramPoint1, paramPoint2);
  }
  
  public boolean openUrlInTencentFileWithReport(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    return TBSIntentUtils.openUrlInTencentFileWithReport(paramContext, paramString1, paramString2, paramString3, paramString4);
  }
  
  public void reportTbsError(int paramInt, String paramString)
  {
    try
    {
      localObject = this.mAppContext;
      if (localObject == null)
      {
        TbsLog.e(TAG, "reportTbsError error: context is null!");
        return;
      }
      int i = Integer.parseInt(TBSShell.getTbsCoreVersionCode());
      TbsLoadFailuresReport.getInstance((Context)localObject).setLoadErrorCode(paramInt, paramString, i);
      return;
    }
    catch (Throwable paramString)
    {
      Object localObject = TAG;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("reportTbsError exception: ");
      localStringBuilder.append(paramString);
      TbsLog.e((String)localObject, localStringBuilder.toString());
    }
  }
  
  public boolean requestHeadsUp(Context paramContext)
  {
    return TbsWupManager.getInstance().requestHeadsUp(paramContext);
  }
  
  public void requestServiceByAreaType(int paramInt)
  {
    TbsWupManager.getInstance().requestServiceByAreaType(paramInt);
  }
  
  public int saveWebPageInLongPressthreeswitchLevel(Context paramContext)
  {
    if (this.mAppContext != null) {
      return PublicSettingManager.getInstance().saveWebPageInLongPressthreeswitchLevel();
    }
    return 0;
  }
  
  public boolean schemeInterceptActiveQBEnable(Context paramContext)
  {
    return Configuration.getInstance(paramContext).schemeInterceptActiveQBEnable();
  }
  
  @SuppressLint({"ShowToast"})
  public void sendMiniQBWupRequestForSwitch(final Context paramContext)
  {
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public void run()
      {
        TbsWupManager.getInstance().setForcePullDomainAndPrefs(true, (byte)1);
        TbsWupManager.getInstance().doMultiWupRequestForThirdParty((byte)1);
        TbsWupManager.getInstance().pullWupMiniQbByForce();
        Toast.makeText(paramContext, "miniQB 开关请求发送成功！", 0).show();
      }
    });
  }
  
  public void setGameSandbox(boolean paramBoolean)
  {
    PublicSettingManager.getInstance().setGameSandbox(paramBoolean);
  }
  
  public void setGameServiceEnv(int paramInt)
  {
    PublicSettingManager.getInstance().setGameServiceEnv(paramInt);
  }
  
  public void setLogBeaconUpload(boolean paramBoolean)
  {
    X5CoreBeaconUploader.getInstance(this.mAppContext).setLogAble(paramBoolean);
  }
  
  public void setTbsSmttServiceCallBack()
  {
    TbsSmttServiceProxy.getInstance().setTesSmttServiceCallback(new TbsSmttServiceCallBack(null));
  }
  
  public void setTestMiniQBEnv(boolean paramBoolean)
  {
    MiniQBUpgradeManager.getInstance().setTestMiniQBEnv(paramBoolean);
  }
  
  public void setWupAddressByForce(String paramString)
  {
    TbsWUPTestManager.getInstance(this.mAppContext).setCustomedWupAddress(paramString);
  }
  
  public boolean shouldLongClickExplorerByDomains(String paramString)
  {
    try
    {
      initDomainList();
      boolean bool = this.mExplorerDomainMatcher.isContainsDomain(paramString);
      return bool;
    }
    catch (Throwable paramString)
    {
      for (;;) {}
    }
    return false;
  }
  
  public void startDownloadInMiniQB(Context paramContext, String paramString1, String paramString2, byte[] paramArrayOfByte, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7)
  {
    TbsMiniQBProxy.getInstance().onDownloadStart(paramContext, paramString1, paramString2, paramArrayOfByte, paramString3, paramString4, paramString5, paramLong, paramString6, paramString7);
  }
  
  public int startDownloadQb(Context paramContext, String paramString1, String paramString2, String paramString3, Bundle paramBundle)
  {
    return QBDownloadManager.getInstance().startDownload(paramContext, paramString1, paramString2, paramString3, paramBundle);
  }
  
  public int startMiniQB(Context paramContext, String paramString1, String paramString2)
  {
    return TbsMiniQBProxy.startMiniQB(paramContext, paramString1, paramString2);
  }
  
  public int startMiniQB(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap)
  {
    return TbsMiniQBProxy.startMiniQB(paramContext, paramString1, paramString2, paramMap);
  }
  
  public int startMiniQB(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap, ValueCallback<String> paramValueCallback)
  {
    return TbsMiniQBProxy.startMiniQB(paramContext, paramString1, paramString2, paramMap, paramValueCallback);
  }
  
  public int startMiniQBWithImages(Context paramContext, Map<String, Bitmap> paramMap, Map<String, Boolean> paramMap1, int paramInt, String paramString)
  {
    return TbsMiniQBProxy.startMiniQBWithImages(paramContext, paramMap, paramMap1, paramInt, paramString);
  }
  
  public int startQBWeb(Context paramContext, String paramString1, String paramString2, int paramInt, Point paramPoint1, Point paramPoint2)
  {
    TbsMiniQBProxy.getInstance();
    return TbsMiniQBProxy.startQBWeb(paramContext, paramString1, paramString2, paramInt, paramPoint1, paramPoint2);
  }
  
  public int startQBWebWithNightFullscreenMode(Context paramContext, String paramString, int paramInt)
  {
    TbsMiniQBProxy.getInstance();
    return TbsMiniQBProxy.startQBWebWithNightFullscreenMode(paramContext, paramString, paramInt);
  }
  
  public boolean switchPluginDebugEnv()
  {
    return false;
  }
  
  public Bundle translate(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    return TranslateWupRequester.getInstance().translate(paramString1, paramString2, paramString3, paramString4);
  }
  
  public boolean uploadPageErrorMetaInfo(String paramString1, int paramInt, String paramString2, String paramString3, boolean paramBoolean, String paramString4, String paramString5)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("guid", paramString1);
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramInt);
    localHashMap.put("error_code", paramString1.toString());
    localHashMap.put("error_desc", paramString2);
    localHashMap.put("error_url", paramString3);
    paramString1 = new StringBuilder();
    paramString1.append("");
    paramString1.append(paramBoolean);
    localHashMap.put("qproxy", paramString1.toString());
    localHashMap.put("net_type_name", paramString4);
    localHashMap.put("net_extra_info", paramString5);
    paramString1 = new StringBuilder();
    paramString1.append("uploadToBeacon, event= QBPageLoadErrorInfoKey, Error Meta info=");
    paramString1.append(localHashMap.toString());
    LogUtils.d("X5WebView", paramString1.toString());
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("QBPageLoadErrorInfoKey", localHashMap);
    return true;
  }
  
  public void uploadUgLog()
  {
    TbsUgEngine.getInstance().uploadUgLog();
  }
  
  public int useDownloadInterceptPlugin(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong, String paramString6, String paramString7, String paramString8, String paramString9, byte[] paramArrayOfByte, IX5WebViewBase paramIX5WebViewBase, Object paramObject, String paramString10)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void useQBWebLoadUrl(String paramString)
  {
    TbsMiniQBProxy.getInstance();
    TbsMiniQBProxy.useQBWebLoadUrl(paramString);
  }
  
  private class ContentCacheBusinessDomainAndPathMatcher
  {
    private static final int sMatchSize = 20;
    private Hashtable<String, Boolean> mCacheMatchTable = new Hashtable();
    private Hashtable<String, ArrayList<String>> mFullDomainAndPathTable = new Hashtable();
    private Hashtable<String, ArrayList<String>> mPartDomainAndPathTable = new Hashtable();
    
    private ContentCacheBusinessDomainAndPathMatcher() {}
    
    private void addDomainAndPathInternal(String paramString1, String paramString2)
    {
      Object localObject2 = this.mFullDomainAndPathTable;
      Object localObject1 = localObject2;
      String str = paramString1;
      if (paramString1.length() > 3)
      {
        localObject1 = localObject2;
        str = paramString1;
        if (paramString1.startsWith("*"))
        {
          localObject1 = localObject2;
          str = paramString1;
          if (!paramString1.substring(1).contains("*"))
          {
            str = paramString1.substring(1);
            localObject1 = this.mPartDomainAndPathTable;
          }
        }
      }
      localObject2 = (ArrayList)((Hashtable)localObject1).get(str);
      paramString1 = (String)localObject2;
      if (localObject2 == null)
      {
        paramString1 = new ArrayList();
        ((Hashtable)localObject1).put(str, paramString1);
      }
      paramString1.add(paramString2);
    }
    
    private boolean containsPathInternal(ArrayList<String> paramArrayList, String paramString)
    {
      if (paramArrayList != null)
      {
        int i = 0;
        try
        {
          while (i < paramArrayList.size())
          {
            boolean bool = paramString.startsWith((String)paramArrayList.get(i));
            if (bool) {
              return true;
            }
            i += 1;
          }
          return false;
        }
        catch (Exception paramArrayList)
        {
          paramArrayList.printStackTrace();
        }
      }
    }
    
    public void addDomainAndPathList(ArrayList<String> paramArrayList)
    {
      if (paramArrayList != null)
      {
        if (paramArrayList.size() == 0) {
          return;
        }
        int i = 0;
        while (i < paramArrayList.size())
        {
          Object localObject = (String)paramArrayList.get(i);
          if (!TextUtils.isEmpty((CharSequence)localObject))
          {
            localObject = ((String)localObject).toLowerCase().split(";");
            if ((1 == localObject.length) || (2 == localObject.length))
            {
              CharSequence localCharSequence = localObject[0];
              if (2 == localObject.length) {
                localObject = localObject[1];
              } else {
                localObject = "";
              }
              if (!TextUtils.isEmpty(localCharSequence)) {
                addDomainAndPathInternal(localCharSequence, (String)localObject);
              }
            }
          }
          i += 1;
        }
        return;
      }
    }
    
    public void clear()
    {
      this.mFullDomainAndPathTable.clear();
      this.mPartDomainAndPathTable.clear();
      this.mCacheMatchTable.clear();
    }
    
    public boolean isContainsDomainAndPath(String paramString1, String paramString2)
    {
      if (!TextUtils.isEmpty(paramString1))
      {
        if (paramString2 == null) {
          return false;
        }
        try
        {
          if (this.mCacheMatchTable.size() >= 20)
          {
            Iterator localIterator = this.mCacheMatchTable.keySet().iterator();
            int i = 0;
            while ((localIterator.hasNext()) && (i < 4) && (localIterator.next() != null))
            {
              localIterator.remove();
              i += 1;
            }
          }
          Object localObject1;
          Object localObject2;
          return false;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          try
          {
            localObject1 = this.mCacheMatchTable;
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append(paramString1);
            ((StringBuilder)localObject2).append(paramString2);
            if (((Hashtable)localObject1).containsKey(((StringBuilder)localObject2).toString()))
            {
              localObject1 = this.mCacheMatchTable;
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append(paramString1);
              ((StringBuilder)localObject2).append(paramString2);
              return ((Boolean)((Hashtable)localObject1).get(((StringBuilder)localObject2).toString())).booleanValue();
            }
            if ((this.mFullDomainAndPathTable.size() == 0) && (this.mPartDomainAndPathTable.size() == 0)) {
              return false;
            }
            if ((this.mFullDomainAndPathTable.size() > 0) && (containsPathInternal((ArrayList)this.mFullDomainAndPathTable.get(paramString1), paramString2)))
            {
              localObject1 = this.mCacheMatchTable;
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append(paramString1);
              ((StringBuilder)localObject2).append(paramString2);
              ((Hashtable)localObject1).put(((StringBuilder)localObject2).toString(), Boolean.valueOf(true));
              return true;
            }
            if (this.mPartDomainAndPathTable.size() > 0)
            {
              localObject1 = this.mPartDomainAndPathTable.keys();
              while (((Enumeration)localObject1).hasMoreElements())
              {
                localObject2 = (String)((Enumeration)localObject1).nextElement();
                if ((paramString1.endsWith((String)localObject2)) && (containsPathInternal((ArrayList)this.mPartDomainAndPathTable.get(localObject2), paramString2)))
                {
                  localObject1 = this.mCacheMatchTable;
                  localObject2 = new StringBuilder();
                  ((StringBuilder)localObject2).append(paramString1);
                  ((StringBuilder)localObject2).append(paramString2);
                  ((Hashtable)localObject1).put(((StringBuilder)localObject2).toString(), Boolean.valueOf(true));
                  return true;
                }
              }
            }
            localObject1 = this.mCacheMatchTable;
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append(paramString1);
            ((StringBuilder)localObject2).append(paramString2);
            ((Hashtable)localObject1).put(((StringBuilder)localObject2).toString(), Boolean.valueOf(false));
            return false;
          }
          catch (Exception paramString1)
          {
            paramString1.printStackTrace();
            return false;
          }
        }
      }
    }
  }
  
  private class DynamicOfflinePageSaveBusinessURLMatcher
  {
    private Hashtable<String, ArrayList<String>> mURLBlackMatcherTable = new Hashtable();
    private Hashtable<String, ArrayList<String>> mURLWhiteMatcherTable = new Hashtable();
    
    private DynamicOfflinePageSaveBusinessURLMatcher() {}
    
    private void addURLMatcherListInternal(Hashtable<String, ArrayList<String>> paramHashtable, ArrayList<String> paramArrayList)
    {
      if (paramArrayList != null)
      {
        if (paramArrayList.size() == 0) {
          return;
        }
        int i = 0;
        while (i < paramArrayList.size())
        {
          Object localObject1 = (String)paramArrayList.get(i);
          if (!TextUtils.isEmpty((CharSequence)localObject1))
          {
            localObject1 = ((String)localObject1).toLowerCase().split(";");
            ArrayList localArrayList = new ArrayList();
            int j = 1;
            while (j < localObject1.length)
            {
              localArrayList.add(localObject1[j]);
              j += 1;
            }
            Object localObject2 = localObject1[0];
            localObject1 = localObject2;
            if (((String)localObject2).length() > 3)
            {
              localObject1 = localObject2;
              if (((String)localObject2).startsWith("*"))
              {
                localObject1 = localObject2;
                if (!((String)localObject2).substring(1).contains("*")) {
                  localObject1 = ((String)localObject2).substring(1);
                }
              }
            }
            paramHashtable.put(localObject1, localArrayList);
          }
          i += 1;
        }
        return;
      }
    }
    
    public void addURLBlackMatcherList(ArrayList<String> paramArrayList)
    {
      addURLMatcherListInternal(this.mURLBlackMatcherTable, paramArrayList);
    }
    
    public void addURLWhiteMatcherList(ArrayList<String> paramArrayList)
    {
      addURLMatcherListInternal(this.mURLWhiteMatcherTable, paramArrayList);
    }
    
    public void clear()
    {
      this.mURLWhiteMatcherTable.clear();
      this.mURLBlackMatcherTable.clear();
    }
    
    public boolean isURLAllowed(String paramString)
    {
      if (paramString != null)
      {
        if (TextUtils.isEmpty(paramString)) {
          return false;
        }
        if (this.mURLWhiteMatcherTable.isEmpty()) {
          return false;
        }
        String str1 = UrlUtils.getHost(paramString);
        Iterator localIterator = this.mURLWhiteMatcherTable.keySet().iterator();
        int i = 0;
        String str2;
        ArrayList localArrayList;
        while (localIterator.hasNext())
        {
          str2 = (String)localIterator.next();
          localArrayList = (ArrayList)this.mURLWhiteMatcherTable.get(str2);
          if (str1.endsWith(str2))
          {
            str2 = UrlUtils.getPathAndQuery(paramString);
            i = 0;
            while (i < localArrayList.size()) {
              if (str2.contains((CharSequence)localArrayList.get(i))) {
                i += 1;
              } else {
                return false;
              }
            }
            i = 1;
          }
        }
        if (i == 0) {
          return false;
        }
        if (this.mURLBlackMatcherTable.isEmpty()) {
          return true;
        }
        localIterator = this.mURLBlackMatcherTable.keySet().iterator();
        while (localIterator.hasNext())
        {
          str2 = (String)localIterator.next();
          localArrayList = (ArrayList)this.mURLBlackMatcherTable.get(str2);
          if (str1.endsWith(str2))
          {
            str2 = UrlUtils.getPathAndQuery(paramString);
            i = 0;
            while (i < localArrayList.size())
            {
              if (str2.contains((CharSequence)localArrayList.get(i))) {
                break label257;
              }
              i += 1;
            }
            continue;
            label257:
            return false;
          }
        }
        return true;
      }
      return false;
    }
  }
  
  class TbsSmttServiceCallBack
    implements ITbsSmttServiceCallback
  {
    ISmttServiceCallBack mSmttServiceCallBack = null;
    
    public TbsSmttServiceCallBack(ISmttServiceCallBack paramISmttServiceCallBack)
    {
      this.mSmttServiceCallBack = paramISmttServiceCallBack;
    }
    
    public String getCrashExtraInfo()
    {
      return SmttServicePartner.this.getCrashExtraInfo();
    }
    
    public ArrayList<String> getQProxyAddressList()
    {
      return null;
    }
    
    public String getX5CoreVersion()
    {
      return SmttServicePartner.this.getX5CoreVersion();
    }
    
    public void setNeedCheckQProxy(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      SmttServicePartner.this.setNeedCheckQProxy(paramBoolean1, paramBoolean2, paramBoolean3);
    }
    
    public void setNeedWIFILogin(boolean paramBoolean) {}
    
    public void setSpdyListToSDK()
    {
      if (this.mSmttServiceCallBack == null) {
        return;
      }
      ArrayList localArrayList = getQProxyAddressList();
      if ((localArrayList != null) && (localArrayList.size() > 0)) {
        try
        {
          this.mSmttServiceCallBack.refreshSpdyProxy((String[])localArrayList.toArray(new String[localArrayList.size()]));
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\SmttServicePartner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */