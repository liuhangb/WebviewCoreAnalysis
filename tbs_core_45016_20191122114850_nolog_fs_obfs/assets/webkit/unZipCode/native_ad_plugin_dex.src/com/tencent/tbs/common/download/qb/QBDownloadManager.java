package com.tencent.tbs.common.download.qb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.IntentUtils;
import com.tencent.common.utils.MttLoader;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.ICoreInfoFetcher;
import com.tencent.tbs.common.baseinfo.QBInstallerRecorder;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.ui.dialog.ProgressAlertDialog;
import com.tencent.tbs.common.ui.dialog.ProgressAlertDialog.ExtButtonClickListenerSerial;
import com.tencent.tbs.common.ui.dialog.ProgressAlertDialog.IProgressViewListener;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.utils.TBSIntentUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class QBDownloadManager
  implements QBDownloadListener
{
  private static final String ACTION_QB_INSTALLING = "com.tencent.mtt.QBDownloadManager.INSTALLING";
  private static final String BZDM001 = "BZDM001";
  private static final String BZDM002 = "BZDM002";
  private static final String BZDM003 = "BZDM003";
  private static final String BZDM004 = "BZDM004";
  private static final String BZDM005 = "BZDM005";
  private static final String BZDM006 = "BZDM006";
  private static final String BZDM007 = "BZDM007";
  private static final String BZDM008 = "BZDM008";
  private static final String BZDM009 = "BZDM009";
  private static final String BZDM010 = "BZDM010";
  private static final String BZKT998 = "BZKT998";
  private static final String BZKT999 = "BZKT999";
  private static final String CHANNEL_ID = "ChannelID";
  public static final String CHANNEL_ID_DEFAULT = "23150";
  public static final String CHANNEL_ID_DOWNLOAD_INTERCEPT_MM_APK = "11265";
  public static final String CHANNEL_ID_DOWNLOAD_INTERCEPT_MM_NOT_APK = "11147";
  public static final String CHANNEL_ID_DOWNLOAD_INTERCEPT_QQ_APK = "11053";
  public static final String CHANNEL_ID_HW_DIALOG_LONG_CLICK_POPUP_OPEN_IN_BROWSER_QQ = "11366";
  public static final String CHANNEL_ID_HW_DIALOG_YYB_WX = "11367";
  public static final String CHANNEL_ID_LONG_CLICK_POPUP_OPEN_IN_BROWSER_MM = "10947";
  public static final String CHANNEL_ID_LONG_CLICK_POPUP_OPEN_IN_BROWSER_QQ = "11314";
  public static final String CHANNEL_ID_LONG_CLICK_POPUP_OPEN_IN_BROWSER_THIRD = "11315";
  public static final String CHANNEL_ID_RIGHT_UP_QQ = "11368";
  public static final String CHANNEL_ID_SEARCH_DIRECT = "11423";
  private static final String CONFIG_KEY_CURRENT_TBSVERSION = "current_tbsversion";
  public static final String DEFALUT_QB_OPENURL = "qb://home";
  public static final String DJM007 = "DJM007";
  public static final String DJM008 = "DJM008";
  public static final String DJM009 = "DJM009";
  public static final String DJM010 = "DJM010";
  public static final String DJM011 = "DJM011";
  public static final String DJM012 = "DJM012";
  public static final String DJM013 = "DJM013";
  public static final String DJM014 = "DJM014";
  public static final String DJM015 = "DJM015";
  public static final String DJM016 = "DJM016";
  public static final String DJMD001 = "DJMD001";
  private static final String DJM_INSTALLEDUNIQ = "DJM_INSTALLEDUNIQ";
  private static final String DJM_NULLINSTALLID = "DJM_NULLINSTALLEDID";
  private static final String DJM_TMPFILENOTEXIST = "DJM_TMPFILENOTEXIST";
  private static final String DJM_VIRTURALDOWNLOAD = "DJM_VIRTURALDOWNLOAD";
  public static final String DM_ALL_VERSION0 = "DWALLVERSION0";
  public static final String DM_INSTALL_EXCEPTION = "DM_INSTALL_EXCEPTION";
  public static final String DM_INSTALL_FILENOTEXIST = "DM_INSTALL_FILENOTEXIST";
  public static final String DM_INSTALL_FILENULL = "DM_INSTALL_FILENULL";
  public static final String DM_INSTALL_START = "DM_INSTALL_START";
  public static final String DM_INSTALL_VERIFYFAILED = "DM_INSTALL_VERIFYFAILED";
  private static final String DM_NOSDCARD = "DWNOSDCARD";
  public static final String DM_NO_EQUAL = "DM_CHECK_OUTTIME_NEW";
  private static final String DM_QBINSTALLED = "DWQBINSTALLED";
  public static final String DM_SERVER_VERSION_ERROR = "DWSERVERSIONVERSIONERROR";
  private static final String DOWNLOAD_DIR = ".log";
  private static final String DOWNLOAD_FILENAME = "qsa?.log";
  private static final String DOWNLOAD_LOCK_FILENAME = "tql.data";
  private static final String DOWNLOAD_TEMP_FILENAME = "qsat?.log";
  public static final String DOWNLOAD_URL = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=23150";
  public static final String DOWNLOAD_URL_PREFIX = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=";
  public static final int FROM_WIFI_AUTO_SILENT = 1;
  public static final String HOST_BEFORE_INTERCEPT_DOWNLOAD = "hostBeforeInterceptDownload";
  public static final String INSTALL_FILENAME = "tmp.apk";
  public static final String KEY_CUSTOM_PROGRESS_DOWNLOADING_TEXT = "key_custom_progress_downloading_text";
  public static final String KEY_CUSTOM_PROGRESS_DOWNLOAD_FAIL_TEXT = "key_custom_progress_download_fail_text";
  public static final String KEY_CUSTOM_PROGRESS_DOWNLOAD_SUCC_TEXT = "key_custom_progress_download_succ_text";
  public static final String KEY_CUSTOM_PROGRESS_INIT_TEXT = "key_custom_progress_init_text";
  public static final String KEY_DOWNLOAD_AUTO = "key_download_auto";
  public static final String KEY_EXT_BTN_CLICK_LISTENER = "key_ext_btn_click_listener";
  public static final String KEY_EXT_BTN_TXT = "key_ext_btn_txt";
  public static final String KEY_IS_PROGRESS_TEXT_CUSTOM = "key_is_progress_text_custom";
  private static final String KEY_QBDOWNLOAD_LASTURL = "qbdownload_lasturl";
  private static final String KEY_QBDOWNLOAD_OPENURL = "qbdownload_openurl";
  public static final String KEY_QB_CURRENT_INSTALLLISTENER = "key_qb_current_installlistener";
  public static final String KEY_QB_CURRENT_URL_PROVIDER = "key_qb_current_url_provider";
  public static final String KEY_QB_CURRENT_URL_PROVIDER_REAL = "key_qb_current_url_provider_real";
  public static final String KEY_QB_DOWNLOADLISTENER = "key_qb_downloadlistener";
  public static final String KEY_QB_EXTRA_PARAMS = "key_qb_extra_params";
  public static final String KEY_QB_INTENT = "key_qb_intent";
  public static final String KEY_QB_ISUPGRATE = "key_qb_isupgrate";
  public static final String KEY_QB_NEED_OPENQB = "key_qb_need_openqb";
  public static final String KEY_QB_OPENURL = "key_qb_openurl";
  private static final String KEY_QB_PACKAGENAME_INSTALLING = "key_qb_packagename_installing";
  public static final String KEY_QB_STATKEY_INSTALLED = "key_qb_statkey_installed";
  public static final String KEY_QB_STATKEY_INSTALLING = "key_qb_statkey_installing";
  public static final String KEY_QB_TOAST = "key_qb_toast";
  public static final String KEY_SHOW_BITMAP = "key_show_bitmap";
  public static final String KEY_SHOW_MESSAGE = "key_show_message";
  public static final String KEY_SHOW_PROGRESS = "key_show_progress";
  public static final String KEY_SHOW_TYPE = "key_show_type";
  public static final String KEY_SKIP_NETWORK_CHECK = "key_skip_network_check";
  private static final int MSG_SHOW_TOAST = 1000;
  private static final int MSG_START_CHECK_REUSE = 104;
  private static final int MSG_START_DOWNLOAD = 100;
  private static final int MSG_START_MOCK_DOWNLOAD = 102;
  public static final String NEW_DOWNLOAD_URL_PREFIX_HTTP = "http://appchannel.html5.qq.com/directdown?app=qqbrowser&channel=";
  public static final String NEW_DOWNLOAD_URL_PREFIX_HTTPS = "https://appchannel.html5.qq.com/directdown?app=qqbrowser&channel=";
  private static final String OLD_DOWNLOAD_DIR = ".tbs";
  private static final String OLD_DOWNLOAD_FILENAME = "qb_silent.apk";
  private static final String OLD_DOWNLOAD_LOCK_FILENAME = "tbs_qbdownload.lock";
  private static final String OLD_DOWNLOAD_TEMP_FILENAME = "qb_silent.temp";
  public static final String PARAM_KEY_COOKIE = "param_key_cookie";
  public static final String PARAM_KEY_DOWNLOAD_CHANNEL = "param_key_download_channel";
  public static final String PARAM_KEY_FEATUREID = "param_key_featureid";
  public static final String PARAM_KEY_FILENAME = "param_key_filename";
  public static final String PARAM_KEY_FUNCTIONID = "param_key_functionid";
  public static final String PARAM_KEY_MIMETYPE = "param_key_mimetype";
  public static final String PARAM_KEY_POSITIONID = "param_key_positionid";
  private static final String POSID_OPENFILE = "14002";
  private static final String POSID_OPENURL = "14001";
  private static final String POS_ID = "PosID";
  public static final int PROGRESS_LAYOUT_TYPE_DEFAULT = 0;
  public static final int PROGRESS_LAYOUT_TYPE_EXT_BTN = 2;
  public static final int PROGRESS_LAYOUT_TYPE_WITHBITMAP = 1;
  private static final String QBDOWNLOADER_THREAD_NAME = "qbdownloader_thread";
  private static final String QB_AUTO_DOWNLOAD_WIFI = "qb_auto_download_wifi";
  private static final String QB_DOWNLOAD_CONFIG_FILENAME = "tbs_qbd_config";
  static final byte RANDOM_BYTE_LENGTH = 127;
  private static final long REPORT_PERIOD = 86400000L;
  private static final String RESUME_FLAG = "RESUME_FLAG";
  private static final String REUSE_FLAG = "REUSE_FLAG";
  private static final String STAT_KEY_DOWNLOAD_INSTALL = "ARTB5";
  private static final String STAT_KEY_DOWNLOAD_LOCALEXIST = "ARTB3";
  private static final String STAT_KEY_DOWNLOAD_REAL = "ARTB4";
  private static final String STAT_KEY_QB_INSTALLED = "AWTQB41";
  private static final String STAT_KEY_QB_INSTALLING = "AWTQB40";
  public static final String TAG = "QBDownloadManager";
  private static QBDownloadManager mInstance;
  private String BK_INSTALLED_BEGIN = "BKINSTALLED3";
  private String BK_INSTALLED_OVER = "BKINSTALLED4";
  private String BK_INSTALLED_PID = "BKINSTALLED5";
  private String hostBeforeInterceptDownload;
  private String installingChannelId = "";
  volatile boolean isApkValid = false;
  private Context mAppContext = null;
  private int mCurrentQBInstallListenerKey;
  private int mCurrentUrlProviderKey;
  private QBDownloadListener mDefaultQbDownloadListener = null;
  private String mDownloadFileName = "qsa?.log";
  private int mDownloadFrom = -1;
  private Handler mDownloadHandler = null;
  private int mDownloadStatus = 0;
  private String mDownloadTempFileName = "qsat?.log";
  private Bundle mExtraParams = null;
  private File mFilePath = null;
  private Intent mIntent = null;
  private boolean mIsNeedOpenQB = true;
  Bundle mLastBundle = null;
  private String mLastOpenUrl = null;
  private String mOpenUrl = null;
  private String mPackageNameInstalling = null;
  private int mPackageType = 0;
  private ProgressAlertDialog mProgressDialog;
  private Set<QBDownloadListener> mQBDownloadListeners = new HashSet();
  private QBDownloader mQBDownloader;
  private QBInstallBroadcastReceiver mQBInstallBroadcastReceiver = null;
  private Map<Integer, QBInstallInfo> mQBInstallInfos = new HashMap();
  private HashMap<Integer, QBInstallListener> mQBInstallListeners = new HashMap();
  private String mStatKeyInstalled = null;
  private Handler mUIHandler = null;
  boolean showProgress = false;
  private long timeStampOfStartInstall = 0L;
  
  private QBDownloadManager()
  {
    init();
    syncConfigFile(false);
  }
  
  private void checkQbReplaced()
  {
    Object localObject1 = ChannelUtil.getInstalledQbChannelId();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("downloadedChannel:");
    ((StringBuilder)localObject2).append(this.installingChannelId);
    ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("installedChannel:");
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).toString();
    if (!TextUtils.isEmpty(this.installingChannelId))
    {
      if (TextUtils.isEmpty((CharSequence)localObject1)) {
        return;
      }
      localObject2 = ChannelUtil.getDeviceInfo();
      ((Map)localObject2).put("before_channel", this.installingChannelId);
      ((Map)localObject2).put("after_channel", localObject1);
      if (!((String)localObject1).equals(this.installingChannelId))
      {
        ((Map)localObject2).put("isReplace", "true");
        localObject1 = new HashMap();
        ((Map)localObject1).put("guid", GUIDFactory.getInstance().getStrGuid());
        X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("QB_CHANNEL_REPLACE_GUID", (Map)localObject1);
        X5CoreBeaconUploader.getInstance(this.mAppContext).userBehaviorStatistics("BZKT999");
      }
      else
      {
        ((Map)localObject2).put("isReplace", "false");
        X5CoreBeaconUploader.getInstance(this.mAppContext).userBehaviorStatistics("BZKT998");
      }
      X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("QB_CHANNEL_REPLACE_INFO", (Map)localObject2);
      return;
    }
  }
  
  private String createDownloadUrlByChannel(String paramString)
  {
    if (Configuration.getInstance(this.mAppContext).isNewQbDownloadUrlStyleEnabled())
    {
      if (Configuration.getInstance(this.mAppContext).isNewQbDownloadUrlStyleHttpsEnabled())
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("https://appchannel.html5.qq.com/directdown?app=qqbrowser&channel=");
        localStringBuilder.append(paramString);
        return localStringBuilder.toString();
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("http://appchannel.html5.qq.com/directdown?app=qqbrowser&channel=");
      localStringBuilder.append(paramString);
      return localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("http://mdc.html5.qq.com/d/directdown.jsp?channel_id=");
    localStringBuilder.append(paramString);
    return localStringBuilder.toString();
  }
  
  private QBInstallInfo currentUrlProviderToInstallInfo(CurrentUrlProvider paramCurrentUrlProvider)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("currentUrlProviderToInstallInfo currentUrlProvider=");
    localStringBuilder.append(paramCurrentUrlProvider);
    localStringBuilder.toString();
    if (paramCurrentUrlProvider != null)
    {
      int i = paramCurrentUrlProvider.hashCode();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("installListenerToInstallInfo installListenerKey=");
      localStringBuilder.append(i);
      localStringBuilder.toString();
      paramCurrentUrlProvider = new QBInstallInfo(i, paramCurrentUrlProvider);
      this.mQBInstallInfos.put(Integer.valueOf(i), paramCurrentUrlProvider);
      return paramCurrentUrlProvider;
    }
    return null;
  }
  
  private void doInitifNeeded(final Context paramContext, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("registerQBInstallBroadcastReceiverIfNeeded mQBInstallBroadcastReceiver=");
    localStringBuilder.append(this.mQBInstallBroadcastReceiver);
    localStringBuilder.append(" context=");
    localStringBuilder.append(paramContext);
    localStringBuilder.toString();
    if (this.mAppContext == null) {
      this.mAppContext = paramContext.getApplicationContext();
    }
    this.mQBDownloader = new QBDownloader(createDownloadUrlByChannel(paramString), this.mFilePath, this.mDownloadTempFileName, this.mDownloadFileName);
    if (this.mDownloadHandler == null)
    {
      paramString = new HandlerThread("qbdownloader_thread");
      paramString.start();
      this.mDownloadHandler = new Handler(paramString.getLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (QBDownloadManager.this.mFilePath == null)
          {
            Log.w("QBDownloadManager", "handleMessage() mFilePath null return");
            return;
          }
          Object localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("handleMessage() msg.what: ");
          ((StringBuilder)localObject1).append(paramAnonymousMessage.what);
          ((StringBuilder)localObject1).append(",msg.obj:");
          ((StringBuilder)localObject1).append(paramAnonymousMessage.obj);
          ((StringBuilder)localObject1).toString();
          int i = paramAnonymousMessage.what;
          FileOutputStream localFileOutputStream = null;
          if (i != 102)
          {
            localObject1 = QBDownloadManager.this;
            localFileOutputStream = ((QBDownloadManager)localObject1).getLockFileOutputStream(((QBDownloadManager)localObject1).mFilePath);
            if (localFileOutputStream == null)
            {
              Log.w("QBDownloadManager", "startDownloadIfNeeded lockFos is null");
              if ((paramAnonymousMessage.obj instanceof Bundle))
              {
                paramAnonymousMessage = (Bundle)paramAnonymousMessage.obj;
                QBDownloadManager.this.onDownloadFailed(false, paramAnonymousMessage);
              }
              TBSStatManager.getInstance().userBehaviorStatistics("DJMD001");
              return;
            }
            localObject1 = QBDownloadManager.this.tryFileLock(localFileOutputStream);
          }
          else
          {
            localObject1 = null;
          }
          i = paramAnonymousMessage.what;
          Object localObject2;
          Object localObject3;
          if (i != 100)
          {
            if (i != 102)
            {
              if (i == 104)
              {
                paramAnonymousMessage = new File(QBDownloadManager.this.mFilePath, QBDownloadManager.this.mDownloadFileName);
                localObject2 = new File(QBDownloadManager.this.mFilePath.getParentFile(), "tmp.apk");
                if (paramAnonymousMessage.exists()) {
                  FileUtils.copyFile(paramAnonymousMessage.getAbsolutePath(), ((File)localObject2).getAbsolutePath());
                }
                localObject3 = new QbReuseProcesser().process(paramContext, (File)localObject2, QBDownloadManager.this.mQBDownloader.getDownloadUrl());
                if ((localObject3 != null) && (((File)localObject3).exists()))
                {
                  QBDownloadManager.this.uploadDownloadFunnel("BZDM004");
                  QBDownloadManager.this.mLastBundle.putBoolean("REUSE_FLAG", true);
                  QBDownloadManager.this.onDownloadSucess(false, ((File)localObject3).getAbsolutePath(), QBDownloadManager.this.mLastBundle);
                }
                else
                {
                  QBDownloadManager.this.uploadDownloadFunnel("BZDM005");
                  paramAnonymousMessage.delete();
                  ((File)localObject2).delete();
                  QBDownloadManager.this.clearDownloadFile();
                  new Handler(Looper.getMainLooper()).post(new Runnable()
                  {
                    public void run()
                    {
                      QBDownloadManager.this.resumeDownloadWithBundle(QBDownloadManager.this.mLastBundle);
                    }
                  });
                }
              }
            }
            else
            {
              localObject2 = (Bundle)paramAnonymousMessage.obj;
              QBDownloadManager.this.mQBDownloader.setExtraParams(QBDownloadManager.this, (Bundle)localObject2);
              QBDownloadManager.this.startMockDownload((Bundle)paramAnonymousMessage.obj);
            }
          }
          else
          {
            QBDownloadManager.this.uploadDownloadFunnel("BZDM006");
            if (paramAnonymousMessage.obj != null)
            {
              localObject2 = (Bundle)paramAnonymousMessage.obj;
              paramAnonymousMessage = "23150";
              if (QBDownloadManager.this.mExtraParams != null) {
                paramAnonymousMessage = QBDownloadManager.this.mExtraParams.getString("param_key_download_channel", "23150");
              }
              localObject3 = TBSStatManager.getInstance();
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("BZXZBCH_");
              localStringBuilder.append(paramAnonymousMessage);
              ((TBSStatManager)localObject3).userBehaviorStatistics(localStringBuilder.toString());
              QBDownloadManager.this.mQBDownloader.setExtraParams(QBDownloadManager.this, (Bundle)localObject2);
            }
            if (localObject1 == null)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("DJM_VIRTURALDOWNLOAD");
              QBDownloadManager.this.mQBDownloader.startVirtualDownload();
            }
            else
            {
              QBDownloadManager.this.mQBDownloader.startDownload();
              QBDownloadManager.this.freeFileLock((FileLock)localObject1, localFileOutputStream);
            }
          }
          if (localObject1 != null) {
            QBDownloadManager.this.freeFileLock((FileLock)localObject1, localFileOutputStream);
          }
        }
      };
    }
    if (this.mQBInstallBroadcastReceiver == null)
    {
      paramString = new IntentFilter();
      paramString.addAction("android.intent.action.PACKAGE_ADDED");
      paramString.addAction("android.intent.action.PACKAGE_INSTALL");
      paramString.addAction("android.intent.action.PACKAGE_REMOVED");
      paramString.addAction("com.tencent.mtt.QBDownloadManager.INSTALLING");
      paramString.addDataScheme("package");
      this.mQBInstallBroadcastReceiver = new QBInstallBroadcastReceiver(null);
      try
      {
        paramContext.getApplicationContext().registerReceiver(this.mQBInstallBroadcastReceiver, paramString);
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
        this.mQBInstallBroadcastReceiver = null;
        paramContext = new StringBuilder();
        paramContext.append("registerQBInstallBroadcastReceiverIfNeeded mQBInstallBroadcastReceiver=");
        paramContext.append(this.mQBInstallBroadcastReceiver);
        paramContext.toString();
      }
    }
    if (this.mUIHandler == null) {
      this.mUIHandler = new Handler(Looper.getMainLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramAnonymousMessage.what == 1000)
          {
            paramAnonymousMessage = (String)paramAnonymousMessage.obj;
            Toast.makeText(QBDownloadManager.this.mAppContext, paramAnonymousMessage, 0).show();
          }
        }
      };
    }
  }
  
  private void fileCompatible(File paramFile1, File paramFile2)
  {
    if (paramFile1.exists())
    {
      boolean bool2 = this.mFilePath.exists();
      boolean bool1 = true;
      if (!bool2) {
        bool1 = true ^ paramFile1.renameTo(this.mFilePath);
      }
      if (!bool1) {}
    }
    try
    {
      FileUtils.delete(paramFile1);
    }
    catch (Exception paramFile1)
    {
      try
      {
        for (;;)
        {
          paramFile1 = getRandomString();
          this.mDownloadFileName = "qsa?.log".replaceAll("\\?", paramFile1);
          this.mDownloadTempFileName = "qsat?.log".replaceAll("\\?", paramFile1);
          paramFile1 = new File(this.mFilePath, "qb_silent.apk");
          paramFile2 = new File(this.mFilePath, "qb_silent.temp");
          File localFile = new File(this.mFilePath, "tbs_qbdownload.lock");
          if (paramFile1.exists()) {
            paramFile1.renameTo(new File(this.mFilePath, this.mDownloadFileName));
          }
          if (paramFile2.exists()) {
            paramFile2.renameTo(new File(this.mFilePath, this.mDownloadTempFileName));
          }
          if (localFile.exists()) {
            localFile.renameTo(new File(this.mFilePath, "tql.data"));
          }
          return;
          paramFile1 = paramFile1;
        }
      }
      catch (Exception paramFile1)
      {
        for (;;) {}
      }
    }
  }
  
  private void freeFileLock(FileLock paramFileLock, FileOutputStream paramFileOutputStream)
  {
    if (paramFileLock != null) {
      try
      {
        paramFileLock.release();
      }
      catch (Exception paramFileLock)
      {
        paramFileLock.printStackTrace();
      }
    }
    if (paramFileOutputStream != null) {
      try
      {
        paramFileOutputStream.close();
        return;
      }
      catch (Exception paramFileLock)
      {
        paramFileLock.printStackTrace();
      }
    }
  }
  
  public static File getApkFile()
  {
    File localFile = getPublicDownloadDir();
    if (localFile == null) {
      return null;
    }
    return new File(localFile, "tmp.apk");
  }
  
  private String getBundleMsg(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return "null";
    }
    return paramBundle.toString();
  }
  
  public static QBDownloadManager getInstance()
  {
    try
    {
      if (mInstance == null) {
        mInstance = new QBDownloadManager();
      }
      QBDownloadManager localQBDownloadManager = mInstance;
      return localQBDownloadManager;
    }
    finally {}
  }
  
  private File getLockFile(File paramFile)
  {
    if (paramFile == null) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getLockFile() lockFileDir: ");
    localStringBuilder.append(paramFile.getAbsolutePath());
    localStringBuilder.toString();
    if (!paramFile.exists()) {
      try
      {
        paramFile.mkdir();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
    if ((paramFile.exists()) && (paramFile.isDirectory()))
    {
      paramFile = new File(paramFile, "tql.data");
      if (paramFile.exists()) {
        return paramFile;
      }
      try
      {
        paramFile.createNewFile();
        return paramFile;
      }
      catch (IOException paramFile)
      {
        paramFile.printStackTrace();
      }
    }
    return null;
  }
  
  private FileOutputStream getLockFileOutputStream(File paramFile)
  {
    paramFile = getLockFile(paramFile);
    if (paramFile != null) {
      try
      {
        paramFile = new FileOutputStream(paramFile);
        return paramFile;
      }
      catch (FileNotFoundException paramFile)
      {
        paramFile.printStackTrace();
      }
    }
    return null;
  }
  
  public static File getOldPublicDownloadDir()
  {
    Context localContext = ContextHolder.getAppContext();
    File localFile1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(".");
      ((StringBuilder)localObject).append(localContext.getPackageName());
      File localFile2 = new File(localFile1, ((StringBuilder)localObject).toString());
      localObject = localFile2;
      if (!localFile2.exists())
      {
        localObject = localFile2;
        if (!localFile2.mkdir()) {
          localObject = localFile1;
        }
      }
      if ((((File)localObject).exists()) && (!((File)localObject).canWrite()))
      {
        localObject = localContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (localObject == null) {
          return localFile1;
        }
        return (File)localObject;
      }
      return (File)localObject;
    }
    catch (Throwable localThrowable) {}
    return localFile1;
  }
  
  public static File getPublicDownloadDir()
  {
    Context localContext = ContextHolder.getAppContext();
    File localFile2 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    try
    {
      File localFile3 = new File(localFile2, ".cu");
      File localFile1 = localFile3;
      if (!localFile3.exists())
      {
        localFile1 = localFile3;
        if (!localFile3.mkdir()) {
          localFile1 = localFile2;
        }
      }
      if ((localFile1.exists()) && (!localFile1.canWrite()))
      {
        localFile1 = localContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (localFile1 == null) {
          return localFile2;
        }
        return localFile1;
      }
      return localFile1;
    }
    catch (Throwable localThrowable) {}
    return localFile2;
  }
  
  private String getRandomString()
  {
    Object localObject2 = "00000";
    Object localObject1 = localObject2;
    try
    {
      int i = DeviceUtils.getIMEI(TbsBaseModuleShell.getContext()).hashCode();
      localObject1 = localObject2;
      StringBuilder localStringBuilder = new StringBuilder();
      localObject1 = localObject2;
      localStringBuilder.append("00000");
      localObject1 = localObject2;
      localStringBuilder.append(i);
      localObject1 = localObject2;
      localObject2 = localStringBuilder.toString();
      localObject1 = localObject2;
      localObject2 = ((String)localObject2).substring(((String)localObject2).length() - 5, ((String)localObject2).length());
      localObject1 = localObject2;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("getRandomString random=");
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).toString();
    return (String)localObject1;
  }
  
  /* Error */
  private void init()
  {
    // Byte code:
    //   0: invokestatic 829	com/tencent/tbs/common/download/qb/QBDownloadManager:getPublicDownloadDir	()Ljava/io/File;
    //   3: astore 4
    //   5: invokestatic 919	com/tencent/tbs/common/download/qb/QBDownloadManager:getOldPublicDownloadDir	()Ljava/io/File;
    //   8: astore_1
    //   9: aload_1
    //   10: ifnull +15 -> 25
    //   13: aload_1
    //   14: invokevirtual 791	java/io/File:exists	()Z
    //   17: ifeq +8 -> 25
    //   20: aload_1
    //   21: invokevirtual 921	java/io/File:delete	()Z
    //   24: pop
    //   25: aload_0
    //   26: aload 4
    //   28: putfield 417	com/tencent/tbs/common/download/qb/QBDownloadManager:mFilePath	Ljava/io/File;
    //   31: aload_0
    //   32: getfield 417	com/tencent/tbs/common/download/qb/QBDownloadManager:mFilePath	Ljava/io/File;
    //   35: ifnull +148 -> 183
    //   38: aconst_null
    //   39: astore_3
    //   40: aconst_null
    //   41: astore_2
    //   42: aload_2
    //   43: astore_1
    //   44: aload_0
    //   45: new 788	java/io/File
    //   48: dup
    //   49: aload 4
    //   51: ldc -103
    //   53: invokespecial 813	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   56: putfield 417	com/tencent/tbs/common/download/qb/QBDownloadManager:mFilePath	Ljava/io/File;
    //   59: aload_2
    //   60: astore_1
    //   61: aload_0
    //   62: getfield 417	com/tencent/tbs/common/download/qb/QBDownloadManager:mFilePath	Ljava/io/File;
    //   65: invokevirtual 791	java/io/File:exists	()Z
    //   68: ifne +23 -> 91
    //   71: aload_2
    //   72: astore_1
    //   73: aload_0
    //   74: getfield 417	com/tencent/tbs/common/download/qb/QBDownloadManager:mFilePath	Ljava/io/File;
    //   77: invokevirtual 852	java/io/File:mkdir	()Z
    //   80: ifne +11 -> 91
    //   83: aload_2
    //   84: astore_1
    //   85: aload_0
    //   86: aload 4
    //   88: putfield 417	com/tencent/tbs/common/download/qb/QBDownloadManager:mFilePath	Ljava/io/File;
    //   91: aload_2
    //   92: astore_1
    //   93: new 788	java/io/File
    //   96: dup
    //   97: aload 4
    //   99: ldc_w 278
    //   102: invokespecial 813	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   105: astore_2
    //   106: aload_0
    //   107: aload_2
    //   108: aload_0
    //   109: getfield 417	com/tencent/tbs/common/download/qb/QBDownloadManager:mFilePath	Ljava/io/File;
    //   112: invokespecial 923	com/tencent/tbs/common/download/qb/QBDownloadManager:fileCompatible	(Ljava/io/File;Ljava/io/File;)V
    //   115: aload_2
    //   116: invokevirtual 791	java/io/File:exists	()Z
    //   119: ifeq +64 -> 183
    //   122: aload_2
    //   123: invokestatic 801	com/tencent/common/utils/FileUtils:delete	(Ljava/io/File;)V
    //   126: return
    //   127: astore_3
    //   128: aload_2
    //   129: astore_1
    //   130: aload_3
    //   131: astore_2
    //   132: goto +34 -> 166
    //   135: goto +7 -> 142
    //   138: astore_2
    //   139: goto +27 -> 166
    //   142: aload_2
    //   143: astore_1
    //   144: aload_0
    //   145: aload 4
    //   147: putfield 417	com/tencent/tbs/common/download/qb/QBDownloadManager:mFilePath	Ljava/io/File;
    //   150: aload_2
    //   151: ifnull +32 -> 183
    //   154: aload_2
    //   155: invokevirtual 791	java/io/File:exists	()Z
    //   158: ifeq +25 -> 183
    //   161: aload_2
    //   162: invokestatic 801	com/tencent/common/utils/FileUtils:delete	(Ljava/io/File;)V
    //   165: return
    //   166: aload_1
    //   167: ifnull +14 -> 181
    //   170: aload_1
    //   171: invokevirtual 791	java/io/File:exists	()Z
    //   174: ifeq +7 -> 181
    //   177: aload_1
    //   178: invokestatic 801	com/tencent/common/utils/FileUtils:delete	(Ljava/io/File;)V
    //   181: aload_2
    //   182: athrow
    //   183: return
    //   184: astore_1
    //   185: aload_3
    //   186: astore_2
    //   187: goto -45 -> 142
    //   190: astore_1
    //   191: goto -56 -> 135
    //   194: astore_1
    //   195: return
    //   196: astore_1
    //   197: goto -16 -> 181
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	200	0	this	QBDownloadManager
    //   8	170	1	localObject1	Object
    //   184	1	1	localThrowable1	Throwable
    //   190	1	1	localThrowable2	Throwable
    //   194	1	1	localException1	Exception
    //   196	1	1	localException2	Exception
    //   41	91	2	localObject2	Object
    //   138	44	2	localFile1	File
    //   186	1	2	localObject3	Object
    //   39	1	3	localObject4	Object
    //   127	59	3	localObject5	Object
    //   3	143	4	localFile2	File
    // Exception table:
    //   from	to	target	type
    //   106	115	127	finally
    //   44	59	138	finally
    //   61	71	138	finally
    //   73	83	138	finally
    //   85	91	138	finally
    //   93	106	138	finally
    //   144	150	138	finally
    //   44	59	184	java/lang/Throwable
    //   61	71	184	java/lang/Throwable
    //   73	83	184	java/lang/Throwable
    //   85	91	184	java/lang/Throwable
    //   93	106	184	java/lang/Throwable
    //   106	115	190	java/lang/Throwable
    //   122	126	194	java/lang/Exception
    //   161	165	194	java/lang/Exception
    //   177	181	196	java/lang/Exception
  }
  
  private void realInstallDownloadFile(Context paramContext, File paramFile, Bundle paramBundle)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("installDownloadFile2,bundle:");
    ((StringBuilder)localObject).append(getBundleMsg(paramBundle));
    ((StringBuilder)localObject).toString();
    if (paramBundle == null) {
      return;
    }
    String str1 = paramBundle.getString("key_qb_statkey_installing");
    String str2 = paramBundle.getString("key_qb_statkey_installed");
    String str3 = paramBundle.getString("key_qb_openurl");
    Intent localIntent = (Intent)paramBundle.getParcelable("key_qb_intent");
    boolean bool = paramBundle.getBoolean("key_qb_need_openqb", true);
    localObject = paramBundle.getBundle("key_qb_extra_params");
    if (localObject == null) {
      localObject = new Bundle();
    }
    ((Bundle)localObject).putInt("download_type", paramBundle.getInt("download_type", 0));
    int i = paramBundle.getInt("key_qb_current_installlistener");
    QBInstallListener localQBInstallListener = (QBInstallListener)this.mQBInstallListeners.get(Integer.valueOf(i));
    i = paramBundle.getInt("key_qb_current_url_provider");
    paramBundle = (QBInstallInfo)this.mQBInstallInfos.get(Integer.valueOf(i));
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("realInstallDownloadFile Bundle installInfo=");
    localStringBuilder.append(paramBundle);
    localStringBuilder.toString();
    realInstallDownloadFile(paramContext, paramFile, str1, str2, localQBInstallListener, paramBundle, localIntent, str3, bool, (Bundle)localObject);
  }
  
  private boolean realInstallDownloadFile(Context paramContext, File paramFile, String paramString1, String paramString2, QBInstallListener paramQBInstallListener, QBInstallInfo paramQBInstallInfo, Intent paramIntent, String paramString3, boolean paramBoolean, Bundle paramBundle)
  {
    label875:
    label881:
    for (;;)
    {
      Object localObject;
      try
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("realInstallDownloadFile statKeyInstalling=");
        ((StringBuilder)localObject).append(paramString1);
        ((StringBuilder)localObject).append(" statKeyInstalled=");
        ((StringBuilder)localObject).append(paramString2);
        ((StringBuilder)localObject).append(" needOpenQB=");
        ((StringBuilder)localObject).append(paramBoolean);
        ((StringBuilder)localObject).append(", extraParams:");
        ((StringBuilder)localObject).append(getBundleMsg(paramBundle));
        ((StringBuilder)localObject).toString();
        localObject = "23150";
        if (paramBundle != null) {
          localObject = paramBundle.getString("param_key_download_channel", "23150");
        }
        doInitifNeeded(paramContext, (String)localObject);
        if (paramBundle == null) {
          break label875;
        }
        i = paramBundle.getInt("download_type", 0);
        if (i == 2) {
          TBSStatManager.getInstance().userBehaviorStatistics("DJM008");
        }
        localObject = null;
        if (i == 2)
        {
          paramFile = TBSDownloaderHelper.getInstance(this.mAppContext).getApkFile(paramContext);
        }
        else
        {
          if (this.mFilePath == null)
          {
            TBSStatManager.getInstance().userBehaviorStatistics("DM_INSTALL_FILENULL");
            return false;
          }
          TBSStatManager.getInstance().userBehaviorStatistics("AWTQB40");
          if (paramFile != null) {
            if (paramFile.exists()) {
              break label881;
            }
          }
          localObject = new File(this.mFilePath, this.mDownloadFileName);
          paramFile = new File(this.mFilePath.getParentFile(), "tmp.apk");
          if (((File)localObject).exists()) {
            FileUtils.copyFile(((File)localObject).getAbsolutePath(), paramFile.getAbsolutePath());
          } else {
            TBSStatManager.getInstance().userBehaviorStatistics("DJM_TMPFILENOTEXIST");
          }
        }
        if (i == 2) {
          TBSStatManager.getInstance().userBehaviorStatistics("DJM009");
        }
        if ((paramFile != null) && (paramFile.exists()))
        {
          if (i == 2) {
            TBSStatManager.getInstance().userBehaviorStatistics("DJM010");
          }
          if (MttLoader.verifySignature(paramFile))
          {
            if (i == 2) {
              TBSStatManager.getInstance().userBehaviorStatistics("DJM011");
            }
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("realInstallDownloadFile statKeyInstalling=");
            ((StringBuilder)localObject).append(paramString1);
            ((StringBuilder)localObject).append(" statKeyInstalled=");
            ((StringBuilder)localObject).append(paramString2);
            ((StringBuilder)localObject).append(" needOpenQB=");
            ((StringBuilder)localObject).append(paramBoolean);
            ((StringBuilder)localObject).toString();
            if (paramString1 != null) {
              TBSStatManager.getInstance().userBehaviorStatistics(paramString1);
            }
            paramString1 = new HashMap();
            paramString1.put("key", paramString2);
            X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon(this.BK_INSTALLED_BEGIN, paramString1);
            paramString1 = new Intent("com.tencent.mtt.QBDownloadManager.INSTALLING");
            paramString1.putExtra("key_qb_packagename_installing", paramContext.getApplicationContext().getPackageName());
            paramString1.putExtra("key_qb_statkey_installed", paramString2);
            paramString1.putExtra("key_qb_openurl", paramString3);
            paramString1.putExtra("key_qb_intent", paramIntent);
            paramString1.putExtra("key_qb_need_openqb", paramBoolean);
            paramString1.setData(Uri.parse("package:com.tencent.mtt.QBDownloadManager.INSTALLING"));
            if (paramQBInstallListener != null)
            {
              int j = paramQBInstallListener.hashCode();
              paramString1.putExtra("key_qb_current_installlistener", j);
              this.mQBInstallListeners.put(Integer.valueOf(j), paramQBInstallListener);
            }
            if (paramQBInstallInfo != null) {
              paramString1.putExtra("key_qb_current_url_provider", paramQBInstallInfo.currentUrlProviderKey);
            }
            removeUiKeysFromBundle(paramBundle);
            paramString1.putExtra("key_qb_extra_params", paramBundle);
            paramString1.putExtra("download_type", i);
            paramContext.sendBroadcast(paramString1);
            if (i == 2) {
              TBSStatManager.getInstance().userBehaviorStatistics("DJM012");
            }
            if (paramBundle == null) {}
          }
        }
      }
      finally {}
      try
      {
        if ("webview".equals(paramBundle.getString("param_key_featureid"))) {
          TBSStatManager.getInstance().userBehaviorStatistics("BZCA009");
        }
        uploadDownloadFunnel("BZDM009");
        this.installingChannelId = ChannelUtil.getQbChannelIdByApkFile(paramFile.getAbsolutePath());
        InstallUtil.startInstall(paramContext, paramFile);
        TBSStatManager.getInstance().userBehaviorStatistics("DM_INSTALL_START");
        return true;
      }
      catch (Exception paramContext)
      {
        continue;
      }
      TBSStatManager.getInstance().userBehaviorStatistics("DM_INSTALL_EXCEPTION");
      continue;
      TBSStatManager.getInstance().userBehaviorStatistics("DM_INSTALL_VERIFYFAILED");
      paramString1 = new StringBuilder();
      paramString1.append("MttLoader.verifySignature error:");
      paramString1.append(paramFile.getAbsolutePath());
      paramString1.toString();
      if (Looper.myLooper() == Looper.getMainLooper()) {
        Toast.makeText(paramContext, TBSResources.getString("x5_qb_download_file_verifyfailed"), 0).show();
      }
      if (i == 2)
      {
        TBSDownloaderHelper.getInstance(this.mAppContext).deleteCachedFiles(true);
      }
      else
      {
        if ((localObject != null) && (((File)localObject).exists())) {
          ((File)localObject).delete();
        }
        if ((paramFile != null) && (paramFile.exists()))
        {
          paramFile.delete();
          continue;
          TBSStatManager.getInstance().userBehaviorStatistics("DM_INSTALL_FILENOTEXIST");
          if ((localObject != null) && (((File)localObject).exists())) {
            ((File)localObject).delete();
          }
          if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(paramContext, TBSResources.getString("x5_qb_download_file_notexists"), 0).show();
          }
        }
      }
      return false;
      int i = 0;
    }
  }
  
  private static void removeUiKeysFromBundle(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return;
    }
    paramBundle.remove("key_show_progress");
    paramBundle.remove("key_show_message");
    paramBundle.remove("key_show_type");
    paramBundle.remove("key_show_bitmap");
    paramBundle.remove("key_ext_btn_txt");
    paramBundle.remove("key_ext_btn_click_listener");
    paramBundle.remove("key_is_progress_text_custom");
    paramBundle.remove("key_custom_progress_init_text");
    paramBundle.remove("key_custom_progress_downloading_text");
    paramBundle.remove("key_custom_progress_download_succ_text");
    paramBundle.remove("key_custom_progress_download_fail_text");
  }
  
  private void resumeDownloadWithBundle(Bundle paramBundle)
  {
    for (;;)
    {
      try
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("resumeDownloadWithBundle:");
        ((StringBuilder)localObject).append(getBundleMsg(paramBundle));
        ((StringBuilder)localObject).toString();
        if ((paramBundle != null) && (this.mAppContext != null))
        {
          int i = paramBundle.getInt("key_qb_current_installlistener");
          QBInstallListener localQBInstallListener = (QBInstallListener)this.mQBInstallListeners.get(Integer.valueOf(i));
          i = paramBundle.getInt("key_qb_current_url_provider");
          localObject = (QBInstallInfo)this.mQBInstallInfos.get(Integer.valueOf(i));
          if (localObject == null) {
            localObject = null;
          } else {
            localObject = ((QBInstallInfo)localObject).currentUrlProvider;
          }
          Bundle localBundle = (Bundle)paramBundle.getParcelable("key_qb_extra_params");
          if (localBundle == null)
          {
            localBundle = new Bundle();
            localBundle.putBoolean("RESUME_FLAG", true);
            startDownload(this.mAppContext, false, paramBundle.getBoolean("key_qb_isupgrate"), paramBundle.getString("key_qb_statkey_installing"), paramBundle.getString("key_qb_statkey_installed"), paramBundle.getString("key_qb_toast"), (QBDownloadListener)paramBundle.getSerializable("key_qb_downloadlistener"), localQBInstallListener, (CurrentUrlProvider)localObject, (Intent)paramBundle.getParcelable("key_qb_intent"), paramBundle.getString("key_qb_openurl"), paramBundle.getBoolean("key_qb_need_openqb"), localBundle);
          }
        }
        else
        {
          return;
        }
      }
      finally {}
    }
  }
  
  private void startMockDownload(final Bundle paramBundle)
  {
    onDownloadStart(false);
    new Thread(new Runnable()
    {
      public void run()
      {
        int i = 0;
        while (i <= 1000)
        {
          try
          {
            Thread.sleep(300L);
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
          }
          i += 100;
          QBDownloadManager.this.onDownloadProgress(false, i / 10);
        }
        File localFile = new File(QBDownloadManager.this.mFilePath, QBDownloadManager.this.mDownloadFileName);
        if (localFile.exists())
        {
          QBDownloadManager.this.onDownloadSucess(false, localFile.getAbsolutePath(), paramBundle);
          return;
        }
        QBDownloadManager.this.onDownloadFailed(false, paramBundle);
      }
    }).start();
  }
  
  private Bundle storeParamsToBundle(boolean paramBoolean1, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, QBInstallListener paramQBInstallListener, CurrentUrlProvider paramCurrentUrlProvider, Intent paramIntent, String paramString4, boolean paramBoolean2, Bundle paramBundle)
  {
    Bundle localBundle2 = new Bundle();
    Bundle localBundle1 = paramBundle;
    if (paramBundle == null) {
      localBundle1 = new Bundle();
    }
    if (paramQBInstallListener != null)
    {
      registerQBInstallListener(paramQBInstallListener);
      localBundle2.putInt("key_qb_current_installlistener", paramQBInstallListener.hashCode());
    }
    if (paramCurrentUrlProvider != null) {
      localBundle2.putInt("key_qb_current_url_provider", currentUrlProviderToInstallInfo(paramCurrentUrlProvider).currentUrlProviderKey);
    }
    localBundle2.putBoolean("key_qb_isupgrate", paramBoolean1);
    localBundle2.putString("key_qb_statkey_installing", paramString1);
    localBundle2.putString("key_qb_statkey_installed", paramString2);
    localBundle2.putString("key_qb_toast", paramString3);
    localBundle2.putSerializable("key_qb_downloadlistener", paramQBDownloadListener);
    localBundle2.putParcelable("key_qb_intent", paramIntent);
    localBundle2.putString("key_qb_openurl", paramString4);
    localBundle2.putBoolean("key_qb_need_openqb", paramBoolean2);
    localBundle2.putParcelable("key_qb_extra_params", localBundle1);
    return localBundle2;
  }
  
  private void syncConfigFile(boolean paramBoolean)
  {
    try
    {
      Object localObject1 = TbsBaseModuleShell.getCallerContext();
      Object localObject3 = TbsBaseModuleShell.getCoreInfoFetcher();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("syncConfigFile forceSync=");
      localStringBuilder.append(paramBoolean);
      localStringBuilder.append(" context=");
      localStringBuilder.append(localObject1);
      localStringBuilder.toString();
      if ((localObject1 != null) && (localObject3 != null))
      {
        localObject3 = ((ICoreInfoFetcher)localObject3).getCoreVersion();
        localObject1 = ((Context)localObject1).getSharedPreferences("tbs_qbd_config", 0);
        if ((!((SharedPreferences)localObject1).getString("current_tbsversion", "").equals(localObject3)) || (paramBoolean))
        {
          localObject1 = ((SharedPreferences)localObject1).edit();
          ((SharedPreferences.Editor)localObject1).putString("current_tbsversion", (String)localObject3);
          ((SharedPreferences.Editor)localObject1).commit();
        }
      }
      return;
    }
    finally {}
  }
  
  private FileLock tryFileLock(FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream == null) {
      return null;
    }
    try
    {
      paramFileOutputStream = paramFileOutputStream.getChannel().tryLock();
      boolean bool = paramFileOutputStream.isValid();
      if (bool) {
        return paramFileOutputStream;
      }
    }
    catch (Exception paramFileOutputStream)
    {
      paramFileOutputStream.printStackTrace();
    }
    return null;
  }
  
  private void unregisterQBInstallBroadcastReceiverIfNeeded(Context paramContext)
  {
    QBInstallBroadcastReceiver localQBInstallBroadcastReceiver = this.mQBInstallBroadcastReceiver;
    if (localQBInstallBroadcastReceiver != null) {}
    try
    {
      paramContext.unregisterReceiver(localQBInstallBroadcastReceiver);
      this.mQBInstallBroadcastReceiver = null;
      return;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
  }
  
  private void uploadDownloadFunnel(String paramString)
  {
    Object localObject1 = "23150";
    Object localObject2 = this.mExtraParams;
    if (localObject2 != null) {
      localObject1 = ((Bundle)localObject2).getString("param_key_download_channel", "23150");
    }
    localObject2 = TBSStatManager.getInstance();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("_");
    localStringBuilder.append((String)localObject1);
    ((TBSStatManager)localObject2).userBehaviorStatistics(localStringBuilder.toString());
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("");
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).toString();
  }
  
  private void uploadDownloadPageUrlToBeacon(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("qbdownload_lasturl", this.mLastOpenUrl);
    localHashMap.put("param_key_positionid", paramString1);
    localHashMap.put("param_key_featureid", paramString2);
    localHashMap.put("param_key_functionid", paramString3);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mLastOpenUrl);
    localStringBuilder.append("|");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("|");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("|");
    localStringBuilder.append(paramString3);
    paramString1 = localStringBuilder.toString();
    paramString2 = new StringBuilder();
    paramString2.append("uploadDownloadPageUrlToBeacon: ");
    paramString2.append(paramString1);
    paramString2.toString();
    X5CoreBeaconUploader.getInstance(paramContext).upLoadToBeacon("qbdownload_openurl", localHashMap);
  }
  
  public void cancelDownload()
  {
    try
    {
      if (this.mQBDownloader != null) {
        this.mQBDownloader.stopDownload();
      }
      if (this.mAppContext != null) {
        TBSDownloaderHelper.getInstance(this.mAppContext).stopDownload(false);
      }
      return;
    }
    finally {}
  }
  
  public void clearDownloadFile()
  {
    try
    {
      try
      {
        if (this.mFilePath != null)
        {
          File localFile1 = new File(this.mFilePath, this.mDownloadFileName);
          File localFile2 = new File(this.mFilePath, this.mDownloadTempFileName);
          File localFile3 = new File(this.mFilePath, "tqc.data");
          if (localFile1.exists()) {
            localFile1.delete();
          }
          if (localFile2.exists()) {
            localFile2.delete();
          }
          if (localFile3.exists()) {
            localFile3.delete();
          }
        }
      }
      finally {}
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
  }
  
  public QBDownloadListener getDefaultQbDownloadListener(Context paramContext)
  {
    try
    {
      if (this.mDefaultQbDownloadListener == null) {
        if (Build.VERSION.SDK_INT >= 21) {
          this.mDefaultQbDownloadListener = new QBDownloadNotificationAndroidM(paramContext);
        } else {
          this.mDefaultQbDownloadListener = new QBDownloadNotification(paramContext);
        }
      }
      paramContext = this.mDefaultQbDownloadListener;
      return paramContext;
    }
    finally {}
  }
  
  public int getDownloadStatus()
  {
    try
    {
      int i = this.mDownloadStatus;
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getQBDownloadProgress()
  {
    try
    {
      int i;
      if ((this.mQBDownloader != null) && (this.mQBDownloader.isDownloading()))
      {
        i = this.mQBDownloader.currentDownloadProgress();
        return i;
      }
      if ((this.mAppContext != null) && (TBSDownloaderHelper.getInstance(this.mAppContext).isDownloading(false)))
      {
        i = TBSDownloaderHelper.getInstance(this.mAppContext).getDownloadProgress(false);
        return i;
      }
      return 0;
    }
    finally {}
  }
  
  @Deprecated
  public void installDownloadFile(Context paramContext, String paramString1, String paramString2, Intent paramIntent, Bundle paramBundle)
  {
    installDownloadFile(paramContext, paramString1, paramString2, null, null, paramIntent, null, true, paramBundle);
  }
  
  @Deprecated
  public void installDownloadFile(Context paramContext, String paramString1, String paramString2, CurrentUrlProvider paramCurrentUrlProvider, Bundle paramBundle)
  {
    installDownloadFile(paramContext, paramString1, paramString2, null, currentUrlProviderToInstallInfo(paramCurrentUrlProvider), null, null, true, paramBundle);
  }
  
  @Deprecated
  public void installDownloadFile(Context paramContext, String paramString1, String paramString2, QBInstallListener paramQBInstallListener, Bundle paramBundle)
  {
    installDownloadFile(paramContext, paramString1, paramString2, paramQBInstallListener, null, null, null, true, paramBundle);
  }
  
  @Deprecated
  public void installDownloadFile(Context paramContext, String paramString1, String paramString2, String paramString3, Bundle paramBundle)
  {
    installDownloadFile(paramContext, paramString1, paramString2, null, null, null, paramString3, true, paramBundle);
  }
  
  @Deprecated
  public boolean installDownloadFile(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    return installDownloadFile(paramContext, paramString1, paramString2, null, null, null, null, false, paramBundle);
  }
  
  public boolean installDownloadFile(Context paramContext, String paramString1, String paramString2, QBInstallListener paramQBInstallListener, QBInstallInfo paramQBInstallInfo, Intent paramIntent, String paramString3, boolean paramBoolean, Bundle paramBundle)
  {
    try
    {
      startDownload(paramContext, false, false, paramString1, paramString2, null, null, paramQBInstallListener, null, paramIntent, paramString3, paramBoolean, paramBundle);
      return true;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
  
  public boolean isEverDownloaded()
  {
    for (;;)
    {
      try
      {
        if ((this.mFilePath != null) && (new File(this.mFilePath, this.mDownloadTempFileName).exists()))
        {
          bool = true;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("isEverDownloaded=");
          localStringBuilder.append(bool);
          localStringBuilder.toString();
          return bool;
        }
      }
      finally {}
      boolean bool = false;
    }
  }
  
  @Deprecated
  public boolean isQBDownloaded()
  {
    try
    {
      if (this.mFilePath != null)
      {
        boolean bool = new File(this.mFilePath, this.mDownloadFileName).exists();
        if (bool) {
          return true;
        }
      }
      return false;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean isQBDownloading()
  {
    try
    {
      boolean bool;
      if (this.mQBDownloader != null)
      {
        bool = this.mQBDownloader.isDownloading();
        if (bool) {
          return true;
        }
      }
      if (this.mAppContext != null)
      {
        bool = TBSDownloaderHelper.getInstance(this.mAppContext).isDownloading(false);
        if (bool) {
          return true;
        }
      }
      return false;
    }
    finally {}
  }
  
  public void onCurrentUrlProviderDismissed(Context paramContext, CurrentUrlProvider paramCurrentUrlProvider)
  {
    if (paramCurrentUrlProvider != null) {
      try
      {
        if (this.mQBInstallInfos != null)
        {
          paramContext = paramCurrentUrlProvider.getCurrentUrl();
          int i = paramCurrentUrlProvider.hashCode();
          paramCurrentUrlProvider = new StringBuilder();
          paramCurrentUrlProvider.append("unreigsterQBInstallListener mLastUrl=");
          paramCurrentUrlProvider.append(paramContext);
          paramCurrentUrlProvider.append(" installListenerKey=");
          paramCurrentUrlProvider.append(i);
          paramCurrentUrlProvider.toString();
          paramCurrentUrlProvider = (QBInstallInfo)this.mQBInstallInfos.get(Integer.valueOf(i));
          if (paramCurrentUrlProvider != null)
          {
            paramCurrentUrlProvider.lastUrl = paramContext;
            paramCurrentUrlProvider.currentUrlProvider = null;
            this.mQBInstallInfos.put(Integer.valueOf(i), paramCurrentUrlProvider);
          }
        }
      }
      finally {}
    }
  }
  
  public void onDownloadFailed(boolean paramBoolean, Bundle paramBundle)
  {
    ??? = new StringBuilder();
    ((StringBuilder)???).append("onDownloadFailed,params:");
    ((StringBuilder)???).append(getBundleMsg(paramBundle));
    ((StringBuilder)???).toString();
    Log.d("QBDownloadManager", "onDownloadFailed,stackTrace:", new Throwable());
    if (this.mDownloadStatus == 5) {
      return;
    }
    this.mDownloadStatus = 3;
    for (;;)
    {
      synchronized (this.mQBDownloadListeners)
      {
        Iterator localIterator = this.mQBDownloadListeners.iterator();
        QBDownloadListener localQBDownloadListener;
        if (localIterator.hasNext())
        {
          localQBDownloadListener = (QBDownloadListener)localIterator.next();
          if (localQBDownloadListener == null) {}
        }
        else
        {
          try
          {
            localQBDownloadListener.onDownloadFailed(paramBoolean, paramBundle);
          }
          catch (Throwable localThrowable) {}
          paramBundle = this.mProgressDialog;
          if (paramBundle != null) {
            paramBundle.setStatus(3, 0);
          }
          uploadDownloadFunnel("BZDM007");
          resetDownloadFrom();
          return;
        }
      }
    }
  }
  
  public void onDownloadPause(boolean paramBoolean, int paramInt)
  {
    for (;;)
    {
      QBDownloadListener localQBDownloadListener;
      synchronized (this.mQBDownloadListeners)
      {
        Iterator localIterator = this.mQBDownloadListeners.iterator();
        if (localIterator.hasNext())
        {
          localQBDownloadListener = (QBDownloadListener)localIterator.next();
          if (localQBDownloadListener == null) {
            continue;
          }
        }
      }
      try
      {
        localQBDownloadListener.onDownloadPause(paramBoolean, paramInt);
      }
      catch (Throwable localThrowable) {}
      return;
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onDownloadProgress(boolean paramBoolean, int paramInt)
  {
    this.mDownloadStatus = 2;
    ??? = new StringBuilder();
    ((StringBuilder)???).append("onDownloadProgress isSilent=");
    ((StringBuilder)???).append(paramBoolean);
    ((StringBuilder)???).append(" progress=");
    ((StringBuilder)???).append(paramInt);
    ((StringBuilder)???).toString();
    for (;;)
    {
      synchronized (this.mQBDownloadListeners)
      {
        Iterator localIterator = this.mQBDownloadListeners.iterator();
        QBDownloadListener localQBDownloadListener;
        if (localIterator.hasNext())
        {
          localQBDownloadListener = (QBDownloadListener)localIterator.next();
          if (localQBDownloadListener == null) {}
        }
        else
        {
          try
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("onDownloadProgress isSilent=");
            localStringBuilder.append(paramBoolean);
            localStringBuilder.append(" progress=");
            localStringBuilder.append(paramInt);
            localStringBuilder.append(" qbDownloadListener=");
            localStringBuilder.append(localQBDownloadListener);
            localStringBuilder.toString();
            localQBDownloadListener.onDownloadProgress(paramBoolean, paramInt);
          }
          catch (Throwable localThrowable) {}
          ??? = this.mProgressDialog;
          if (??? != null) {
            ((ProgressAlertDialog)???).setStatus(2, paramInt);
          }
          return;
        }
      }
    }
  }
  
  public void onDownloadResume(boolean paramBoolean, int paramInt)
  {
    for (;;)
    {
      QBDownloadListener localQBDownloadListener;
      synchronized (this.mQBDownloadListeners)
      {
        Iterator localIterator = this.mQBDownloadListeners.iterator();
        if (localIterator.hasNext())
        {
          localQBDownloadListener = (QBDownloadListener)localIterator.next();
          if (localQBDownloadListener == null) {
            continue;
          }
        }
      }
      try
      {
        localQBDownloadListener.onDownloadResume(paramBoolean, paramInt);
      }
      catch (Throwable localThrowable) {}
      return;
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void onDownloadStart(boolean paramBoolean)
  {
    this.mDownloadStatus = 4;
    ??? = new StringBuilder();
    ((StringBuilder)???).append("onDownloadStart:");
    ((StringBuilder)???).append(paramBoolean);
    ((StringBuilder)???).toString();
    for (;;)
    {
      synchronized (this.mQBDownloadListeners)
      {
        Iterator localIterator = this.mQBDownloadListeners.iterator();
        QBDownloadListener localQBDownloadListener;
        if (localIterator.hasNext())
        {
          localQBDownloadListener = (QBDownloadListener)localIterator.next();
          if (localQBDownloadListener == null) {}
        }
        else
        {
          try
          {
            localQBDownloadListener.onDownloadStart(paramBoolean);
          }
          catch (Throwable localThrowable) {}
          return;
        }
      }
    }
  }
  
  public void onDownloadSucess(boolean paramBoolean, String paramString, Bundle paramBundle)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("onDownloadSucess,isSilent:");
    ((StringBuilder)localObject1).append(paramBoolean);
    ((StringBuilder)localObject1).append("params:");
    ((StringBuilder)localObject1).append(getBundleMsg(paramBundle));
    ((StringBuilder)localObject1).toString();
    this.mDownloadStatus = 1;
    ??? = new File(paramString);
    localObject1 = new File(this.mFilePath.getParentFile(), "tmp.apk");
    if (!TextUtils.equals(((File)???).getAbsolutePath(), ((File)localObject1).getAbsolutePath())) {
      FileUtils.copyFile(((File)???).getAbsolutePath(), ((File)localObject1).getAbsolutePath());
    }
    for (;;)
    {
      synchronized (this.mQBDownloadListeners)
      {
        Object localObject3 = this.mQBDownloadListeners.iterator();
        QBDownloadListener localQBDownloadListener;
        if (((Iterator)localObject3).hasNext())
        {
          localQBDownloadListener = (QBDownloadListener)((Iterator)localObject3).next();
          if (localQBDownloadListener == null) {}
        }
        else
        {
          try
          {
            localQBDownloadListener.onDownloadSucess(paramBoolean, paramString, paramBundle);
          }
          catch (Throwable localThrowable) {}
          paramString = this.mProgressDialog;
          if (paramString != null)
          {
            paramString.setStatus(1, 0);
            this.mProgressDialog.onDownloadFinish();
          }
          paramString = "0";
          if (paramBundle != null) {
            paramString = paramBundle.getString("param_key_functionid", "0");
          }
          ??? = TBSStatManager.getInstance();
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("ARTB5");
          ((StringBuilder)localObject3).append(paramString);
          ((TBSStatManager)???).userBehaviorStatistics(((StringBuilder)localObject3).toString());
          ??? = new StringBuilder();
          ((StringBuilder)???).append("userBehaviorStatistics:");
          ((StringBuilder)???).append("ARTB5");
          ((StringBuilder)???).append(paramString);
          ((StringBuilder)???).toString();
          if (paramBundle != null)
          {
            if (!paramBundle.getBoolean("REUSE_FLAG")) {
              uploadDownloadFunnel("BZDM008");
            }
            paramBundle.remove("REUSE_FLAG");
          }
          realInstallDownloadFile(this.mAppContext, (File)localObject1, paramBundle);
          syncConfigFile(true);
          if (this.mDownloadFrom == 1) {
            TBSStatManager.getInstance().userBehaviorStatistics("AWTWF026");
          }
          resetDownloadFrom();
          return;
        }
      }
    }
  }
  
  public void pauseDownload()
  {
    try
    {
      if (this.mDownloadStatus == 2) {
        this.mDownloadStatus = 5;
      }
      cancelDownload();
      return;
    }
    finally {}
  }
  
  public void registerQBDownloadListener(QBDownloadListener paramQBDownloadListener)
  {
    Set localSet = this.mQBDownloadListeners;
    if (paramQBDownloadListener != null) {}
    try
    {
      if (this.mQBDownloadListeners != null) {
        this.mQBDownloadListeners.add(paramQBDownloadListener);
      }
      return;
    }
    finally {}
  }
  
  public void registerQBInstallListener(QBInstallListener paramQBInstallListener)
  {
    HashMap localHashMap = this.mQBInstallListeners;
    if (paramQBInstallListener != null) {}
    try
    {
      this.mQBInstallListeners.put(Integer.valueOf(paramQBInstallListener.hashCode()), paramQBInstallListener);
      return;
    }
    finally {}
  }
  
  public void resetDownloadFrom()
  {
    this.mDownloadFrom = -1;
  }
  
  public void resumeDownload()
  {
    try
    {
      if ((this.mDownloadStatus == 5) && (this.mDownloadHandler != null)) {
        resumeDownloadWithBundle(this.mLastBundle);
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public boolean setAutoSilentDownloadSwitch(Context paramContext, boolean paramBoolean)
  {
    try
    {
      paramContext = paramContext.getSharedPreferences("tbs_qbd_config", 0).edit();
      paramContext.putBoolean("qb_auto_download_wifi", paramBoolean);
      paramContext.commit();
      return true;
    }
    catch (Exception paramContext)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("openSilentDownload error:");
      localStringBuilder.append(paramContext.toString());
      Log.w("QBDownloadManager", localStringBuilder.toString());
    }
    return false;
  }
  
  public void setDownloadFrom(int paramInt)
  {
    this.mDownloadFrom = paramInt;
  }
  
  public void setHostBeforeInterceptDownload(String paramString)
  {
    this.hostBeforeInterceptDownload = paramString;
  }
  
  public void setLastOpenUrl(String paramString)
  {
    this.mLastOpenUrl = paramString;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setLastOpenUrl:");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
  }
  
  public void setTimeStampOfStartInstall(long paramLong)
  {
    this.timeStampOfStartInstall = paramLong;
  }
  
  protected int startDownload(Context paramContext, Bundle paramBundle)
  {
    String str1 = paramBundle.getString("key_qb_statkey_installing");
    String str2 = paramBundle.getString("key_qb_statkey_installed");
    String str3 = paramBundle.getString("key_qb_openurl");
    Intent localIntent = (Intent)paramBundle.getParcelable("key_qb_intent");
    boolean bool = paramBundle.getBoolean("key_qb_need_openqb", true);
    String str4 = paramBundle.getString("key_qb_toast");
    Bundle localBundle = paramBundle.getBundle("key_qb_extra_params");
    int i = paramBundle.getInt("key_qb_current_installlistener");
    QBInstallListener localQBInstallListener = (QBInstallListener)this.mQBInstallListeners.get(Integer.valueOf(i));
    i = paramBundle.getInt("key_qb_current_url_provider");
    paramBundle = (QBInstallInfo)this.mQBInstallInfos.get(Integer.valueOf(i));
    if (paramBundle == null) {
      paramBundle = null;
    } else {
      paramBundle = paramBundle.currentUrlProvider;
    }
    return startDownload(paramContext, false, false, str1, str2, str4, null, localQBInstallListener, paramBundle, localIntent, str3, bool, localBundle);
  }
  
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, Bundle paramBundle)
  {
    if ((paramContext != null) && (!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)) && (!TextUtils.isEmpty(paramString3)))
    {
      String str1 = paramBundle.getString("key_qb_statkey_installing");
      String str2 = paramBundle.getString("key_qb_statkey_installed");
      String str3 = paramBundle.getString("key_qb_openurl");
      Intent localIntent2;
      try
      {
        Intent localIntent1 = (Intent)paramBundle.getSerializable("key_qb_intent");
      }
      catch (Exception localException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Exception: params.getSerializable(QBDownloadManager.KEY_QB_INTENT) - ");
        localStringBuilder.append(localException.toString());
        localStringBuilder.toString();
        localIntent2 = null;
      }
      paramBundle.putString("param_key_positionid", paramString1);
      paramBundle.putString("param_key_featureid", paramString2);
      paramBundle.putString("param_key_functionid", paramString3);
      boolean bool = paramBundle.getBoolean("key_qb_need_openqb", true);
      paramString2 = paramBundle.getString("key_qb_toast");
      try
      {
        paramString1 = (CurrentUrlProvider)paramBundle.getSerializable("key_qb_current_url_provider_real");
      }
      catch (Exception paramString1)
      {
        paramString3 = new StringBuilder();
        paramString3.append("Exception: params.getSerializable(KEY_QB_CURRENT_URL_PROVIDER_REAL - ");
        paramString3.append(paramString1.toString());
        paramString3.toString();
        paramString1 = null;
      }
      return startDownload(paramContext, false, false, str1, str2, paramString2, null, null, paramString1, localIntent2, str3, bool, paramBundle);
    }
    return -6;
  }
  
  @Deprecated
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, Intent paramIntent, Bundle paramBundle)
  {
    return startDownload(paramContext, false, false, paramString1, paramString2, paramString3, paramQBDownloadListener, null, null, paramIntent, null, true, paramBundle);
  }
  
  @Deprecated
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, Bundle paramBundle)
  {
    return startDownload(paramContext, false, false, paramString1, paramString2, paramString3, paramQBDownloadListener, null, null, null, null, false, paramBundle);
  }
  
  @Deprecated
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, CurrentUrlProvider paramCurrentUrlProvider, Bundle paramBundle)
  {
    return startDownload(paramContext, false, false, paramString1, paramString2, paramString3, paramQBDownloadListener, null, paramCurrentUrlProvider, null, null, true, paramBundle);
  }
  
  @Deprecated
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, QBInstallListener paramQBInstallListener, Bundle paramBundle)
  {
    return startDownload(paramContext, false, false, paramString1, paramString2, paramString3, paramQBDownloadListener, paramQBInstallListener, null, null, null, true, paramBundle);
  }
  
  @Deprecated
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, String paramString4, Bundle paramBundle)
  {
    return startDownload(paramContext, false, false, paramString1, paramString2, paramString3, paramQBDownloadListener, null, null, null, paramString4, true, paramBundle);
  }
  
  @Deprecated
  public int startDownload(Context paramContext, boolean paramBoolean, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, QBInstallListener paramQBInstallListener, Bundle paramBundle)
  {
    return startDownload(paramContext, false, paramBoolean, paramString1, paramString2, paramString3, paramQBDownloadListener, paramQBInstallListener, null, null, null, true, paramBundle);
  }
  
  public int startDownload(Context paramContext, boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, QBInstallListener paramQBInstallListener, CurrentUrlProvider paramCurrentUrlProvider, Intent paramIntent, String paramString4, boolean paramBoolean3, Bundle paramBundle)
  {
    for (;;)
    {
      try
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("startDownload isSilent=");
        ((StringBuilder)localObject1).append(paramBoolean1);
        ((StringBuilder)localObject1).append(" needOpenQB=");
        ((StringBuilder)localObject1).append(paramBoolean3);
        ((StringBuilder)localObject1).append(",isUpgrade:");
        ((StringBuilder)localObject1).append(paramBoolean2);
        ((StringBuilder)localObject1).append(",extraParams:");
        ((StringBuilder)localObject1).append(getBundleMsg(paramBundle));
        ((StringBuilder)localObject1).toString();
        if ((paramBundle == null) || (!paramBundle.getBoolean("RESUME_FLAG"))) {
          uploadDownloadFunnel("BZDM001");
        }
        if (!FileUtils.hasSDcard())
        {
          Log.w("QBDownloadManager", "startDownloadIfNeeded sdcard not exist");
          return -2;
        }
        localObject1 = "23150";
        if (paramBundle != null)
        {
          localObject1 = paramBundle.getString("param_key_download_channel", "23150");
          doInitifNeeded(paramContext, (String)localObject1);
          if (paramQBDownloadListener != null) {
            registerQBDownloadListener(paramQBDownloadListener);
          }
          if (paramQBInstallListener != null) {
            registerQBInstallListener(paramQBInstallListener);
          }
          if (paramBundle == null) {
            break label1253;
          }
          localObject1 = paramBundle.getString("param_key_functionid", "0");
          paramBundle.putInt("download_type", 0);
          this.mLastBundle = storeParamsToBundle(paramBoolean2, paramString1, paramString2, paramString3, paramQBDownloadListener, paramQBInstallListener, paramCurrentUrlProvider, paramIntent, paramString4, paramBoolean3, paramBundle);
          this.mLastBundle.putString("param_key_functionid", (String)localObject1);
          if (this.mFilePath == null)
          {
            TBSStatManager.getInstance().userBehaviorStatistics("DWNOSDCARD");
            return -2;
          }
          paramString1 = TbsBaseModuleShell.getCoreInfoFetcher();
          if (paramString1 == null) {
            break label1261;
          }
          paramBoolean1 = paramString1.getMiniqbDebugFlag();
          paramString1 = new StringBuilder();
          paramString1.append("MttLoader.isBrowserInstalled:");
          paramString1.append(MttLoader.isBrowserInstalled(paramContext));
          paramString1.append(", isDebugForminiqb:");
          paramString1.append(paramBoolean1);
          paramString1.toString();
          if ((MttLoader.isBrowserInstalled(paramContext)) && (!paramBoolean2) && (!paramBoolean1))
          {
            TBSStatManager.getInstance().userBehaviorStatistics("DWQBINSTALLED");
            return -1;
          }
          if (paramBundle == null) {
            break label1266;
          }
          this.mQBDownloader.setSkipNetworkTypeCheck(paramBundle.getBoolean("key_skip_network_check", false));
          this.showProgress = false;
          paramCurrentUrlProvider = null;
          if (paramBundle == null) {
            break label1287;
          }
          this.showProgress = paramBundle.getBoolean("key_show_progress", false);
          if (!this.showProgress) {
            break label1287;
          }
          paramBoolean3 = paramBundle.getBoolean("key_download_auto", false);
          String str2 = paramBundle.getString("key_show_message");
          int j = paramBundle.getInt("key_show_type", 0);
          Bitmap localBitmap = (Bitmap)paramBundle.getParcelable("key_show_bitmap");
          boolean bool = paramBundle.getBoolean("key_is_progress_text_custom", false);
          if (!bool) {
            break label1269;
          }
          paramString1 = paramBundle.getString("key_custom_progress_init_text");
          paramString2 = paramBundle.getString("key_custom_progress_downloading_text");
          paramQBDownloadListener = paramBundle.getString("key_custom_progress_download_succ_text");
          paramQBInstallListener = paramBundle.getString("key_custom_progress_download_fail_text");
          String str3 = paramBundle.getString("key_ext_btn_txt");
          Serializable localSerializable = paramBundle.getSerializable("key_ext_btn_click_listener");
          paramBoolean1 = paramBoolean3;
          paramIntent = paramString1;
          paramString4 = str2;
          i = j;
          paramBundle = localBitmap;
          localObject2 = paramString2;
          localObject3 = paramQBDownloadListener;
          localObject4 = paramQBInstallListener;
          str1 = str3;
          paramBoolean2 = bool;
          if ((localSerializable instanceof ProgressAlertDialog.ExtButtonClickListenerSerial))
          {
            paramCurrentUrlProvider = (ProgressAlertDialog.ExtButtonClickListenerSerial)localSerializable;
            paramBoolean1 = paramBoolean3;
            paramIntent = paramString1;
            paramString4 = str2;
            i = j;
            paramBundle = localBitmap;
            localObject2 = paramString2;
            localObject3 = paramQBDownloadListener;
            localObject4 = paramQBInstallListener;
            str1 = str3;
            paramBoolean2 = bool;
          }
          if (this.mQBDownloader.isDownloading())
          {
            if ((this.showProgress) && (ProgressAlertDialog.canShowAlertDialog(paramContext)))
            {
              this.mProgressDialog = new ProgressAlertDialog(paramContext, paramString4, i, paramBundle, paramBoolean1, null);
              if (paramBoolean2)
              {
                this.mProgressDialog.setIsButtonTextCustomized(true);
                this.mProgressDialog.setCustomInitButtonText(paramIntent);
                this.mProgressDialog.setCustomDownloadingButtonText((String)localObject2);
                this.mProgressDialog.setCustomDownloadSuccButtonText((String)localObject3);
                this.mProgressDialog.setCustomDownloadFailButtonText((String)localObject4);
              }
              this.mProgressDialog.setExtButtonText(str1);
              this.mProgressDialog.setExtButtonClickListener(paramCurrentUrlProvider);
              this.mProgressDialog.show();
            }
            return -3;
          }
          paramString1 = new File(this.mFilePath, this.mDownloadFileName);
          if (paramString1.exists())
          {
            if ((this.showProgress) && (ProgressAlertDialog.canShowAlertDialog(paramContext)))
            {
              if (!TextUtils.isEmpty(paramString3)) {
                Message.obtain(this.mUIHandler, 1000, paramString3).sendToTarget();
              }
              this.mProgressDialog = new ProgressAlertDialog(paramContext, paramString4, i, paramBundle, paramBoolean1, new ProgressAlertDialog.IProgressViewListener()
              {
                public void onInstallButtonClick()
                {
                  QBDownloadManager.this.mDownloadHandler.removeMessages(102);
                  QBDownloadManager.this.mDownloadHandler.removeMessages(100);
                  Message.obtain(QBDownloadManager.this.mDownloadHandler, 102, QBDownloadManager.this.mLastBundle).sendToTarget();
                }
              });
              if (paramBoolean2)
              {
                this.mProgressDialog.setIsButtonTextCustomized(true);
                this.mProgressDialog.setCustomInitButtonText(paramIntent);
                this.mProgressDialog.setCustomDownloadingButtonText((String)localObject2);
                this.mProgressDialog.setCustomDownloadSuccButtonText((String)localObject3);
                this.mProgressDialog.setCustomDownloadFailButtonText((String)localObject4);
              }
              this.mProgressDialog.setExtButtonText(str1);
              this.mProgressDialog.setExtButtonClickListener(paramCurrentUrlProvider);
              this.mProgressDialog.show();
            }
            else
            {
              this.mDownloadHandler.obtainMessage(104).sendToTarget();
            }
            uploadDownloadFunnel("BZDM002");
            paramContext = ChannelUtil.getQbChannelIdByApkFile(paramString1.getAbsolutePath());
            paramString1 = TBSStatManager.getInstance();
            paramString2 = new StringBuilder();
            paramString2.append("BZBFBCH_");
            paramString2.append(paramContext);
            paramString1.userBehaviorStatistics(paramString2.toString());
            return -4;
          }
          uploadDownloadFunnel("BZDM003");
          this.mDownloadHandler.removeMessages(100);
          if (!TextUtils.isEmpty(paramString3)) {
            Message.obtain(this.mUIHandler, 1000, paramString3).sendToTarget();
          }
          if ((this.showProgress) && (ProgressAlertDialog.canShowAlertDialog(paramContext)))
          {
            this.mProgressDialog = new ProgressAlertDialog(paramContext, paramString4, i, paramBundle, paramBoolean1, new ProgressAlertDialog.IProgressViewListener()
            {
              public void onInstallButtonClick()
              {
                Message.obtain(QBDownloadManager.this.mDownloadHandler, 100, QBDownloadManager.this.mLastBundle).sendToTarget();
              }
            });
            if (paramBoolean2)
            {
              this.mProgressDialog.setIsButtonTextCustomized(true);
              this.mProgressDialog.setCustomInitButtonText(paramIntent);
              this.mProgressDialog.setCustomDownloadingButtonText((String)localObject2);
              this.mProgressDialog.setCustomDownloadSuccButtonText((String)localObject3);
              this.mProgressDialog.setCustomDownloadFailButtonText((String)localObject4);
            }
            this.mProgressDialog.setExtButtonText(str1);
            this.mProgressDialog.setExtButtonClickListener(paramCurrentUrlProvider);
            this.mProgressDialog.show();
          }
          else
          {
            Message.obtain(this.mDownloadHandler, 100, this.mLastBundle).sendToTarget();
          }
          paramContext = TBSStatManager.getInstance();
          paramString1 = new StringBuilder();
          paramString1.append("ARTB4");
          paramString1.append((String)localObject1);
          paramContext.userBehaviorStatistics(paramString1.toString());
          paramContext = new StringBuilder();
          paramContext.append("start to download real .userBehaviorStatistics:");
          paramContext.append("ARTB4");
          paramContext.append((String)localObject1);
          paramContext.toString();
          return 1;
        }
      }
      finally {}
      continue;
      label1253:
      Object localObject1 = "0";
      continue;
      label1261:
      paramBoolean1 = false;
      continue;
      label1266:
      continue;
      label1269:
      paramString1 = null;
      paramString2 = paramString1;
      paramQBDownloadListener = paramString2;
      paramQBInstallListener = paramQBDownloadListener;
      continue;
      label1287:
      paramString4 = "";
      paramIntent = null;
      paramQBInstallListener = paramIntent;
      paramQBDownloadListener = paramQBInstallListener;
      paramString2 = paramQBDownloadListener;
      paramString1 = paramString2;
      String str1 = paramString1;
      paramBoolean1 = false;
      int i = 0;
      paramBoolean2 = false;
      paramBundle = paramQBInstallListener;
      Object localObject2 = paramQBDownloadListener;
      Object localObject3 = paramString2;
      Object localObject4 = paramString1;
    }
  }
  
  @Deprecated
  public int startSilentDownload(Context paramContext)
  {
    return 0;
  }
  
  @Deprecated
  public int startSilentDownload(Context paramContext, QBDownloadListener paramQBDownloadListener)
  {
    return 0;
  }
  
  public void unregisterQBDownloadListener(QBDownloadListener paramQBDownloadListener)
  {
    Set localSet = this.mQBDownloadListeners;
    if (paramQBDownloadListener != null) {}
    try
    {
      if ((this.mQBDownloadListeners != null) && (this.mQBDownloadListeners.contains(paramQBDownloadListener))) {
        this.mQBDownloadListeners.remove(paramQBDownloadListener);
      }
      return;
    }
    finally {}
  }
  
  public void unregisterQBInstallListener(QBInstallListener paramQBInstallListener)
  {
    HashMap localHashMap = this.mQBInstallListeners;
    if (paramQBInstallListener != null) {}
    try
    {
      if (this.mQBInstallListeners.containsKey(Integer.valueOf(paramQBInstallListener.hashCode()))) {
        this.mQBInstallListeners.remove(Integer.valueOf(paramQBInstallListener.hashCode()));
      }
      return;
    }
    finally {}
  }
  
  private class QBInstallBroadcastReceiver
    extends BroadcastReceiver
  {
    private QBInstallBroadcastReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("QBInstallBroadcastReceiver.onReceive intent=");
      ((StringBuilder)localObject1).append(paramIntent);
      ((StringBuilder)localObject1).toString();
      boolean bool1;
      int k;
      Object localObject2;
      Object localObject3;
      if (paramIntent != null)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("QBInstallBroadcastReceiver.onReceive action=");
        ((StringBuilder)localObject1).append(paramIntent.getAction());
        ((StringBuilder)localObject1).toString();
        boolean bool2 = "android.intent.action.PACKAGE_ADDED".equals(paramIntent.getAction());
        bool1 = false;
        k = 0;
        if (bool2)
        {
          if ((!"package:com.tencent.mtt".equals(paramIntent.getDataString())) || (!MttLoader.isBrowserInstalled(paramContext))) {
            break label2161;
          }
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("QBInstallBroadcastReceiver.onReceive mPackageNameInstalling=");
          ((StringBuilder)localObject1).append(QBDownloadManager.this.mPackageNameInstalling);
          ((StringBuilder)localObject1).append(" mStatKeyInstalled=");
          ((StringBuilder)localObject1).append(QBDownloadManager.this.mStatKeyInstalled);
          ((StringBuilder)localObject1).toString();
          QBDownloadManager.this.uploadDownloadFunnel("BZDM010");
          localObject2 = new HashMap();
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(QBDownloadManager.this.mPackageNameInstalling);
          ((StringBuilder)localObject1).append(",");
          ((StringBuilder)localObject1).append(paramContext.getApplicationContext().getPackageName());
          ((StringBuilder)localObject1).append(",");
          ((StringBuilder)localObject1).append(QBDownloadManager.this.mStatKeyInstalled);
          localObject1 = ((StringBuilder)localObject1).toString();
          ((HashMap)localObject2).put("key", localObject1);
          X5CoreBeaconUploader.getInstance(QBDownloadManager.this.mAppContext).upLoadToBeacon(QBDownloadManager.this.BK_INSTALLED_PID, (Map)localObject2);
          if (TextUtils.isEmpty(QBDownloadManager.this.mStatKeyInstalled)) {
            TBSStatManager.getInstance().userBehaviorStatistics("DJM_NULLINSTALLEDID");
          }
          if (QBDownloadManager.this.mPackageType == 2) {
            TBSStatManager.getInstance().userBehaviorStatistics("DJM015");
          }
          QBDownloadManager.this.checkQbReplaced();
          localObject2 = new File(QBDownloadManager.this.mFilePath.getParentFile(), "tmp.apk");
          localObject3 = ChannelUtil.getQbChannelIdByApkFile(((File)localObject2).getAbsolutePath());
          if ("11423".equals(localObject3)) {
            TBSStatManager.getInstance().userBehaviorStatistics("BZCA405");
          }
          Object localObject4 = new HashMap();
          ((HashMap)localObject4).put("mainchannelapk", localObject3);
          ((HashMap)localObject4).put("subchannelapk", ChannelUtil.getQbSubChannelByApk(((File)localObject2).getAbsolutePath()));
          ((HashMap)localObject4).put("mainchannelqb", ChannelUtil.getInstalledQbChannelId());
          ((HashMap)localObject4).put("subchannelqb", ChannelUtil.getInstalledQbSubChannelId());
          X5CoreBeaconUploader.getInstance(QBDownloadManager.this.mAppContext).upLoadToBeacon("TBSMQBINSTALLEDCHANNEL", (Map)localObject4);
          if ((QBDownloadManager.this.mPackageNameInstalling == null) || (!QBDownloadManager.this.mPackageNameInstalling.equals(paramContext.getApplicationContext().getPackageName())) || (QBDownloadManager.this.mStatKeyInstalled == null)) {
            break label2161;
          }
          if ((System.currentTimeMillis() - QBDownloadManager.this.timeStampOfStartInstall < 120000L) && (QBDownloadManager.this.hostBeforeInterceptDownload != null))
          {
            localObject2 = new HashMap();
            ((HashMap)localObject2).put("host", QBDownloadManager.this.hostBeforeInterceptDownload);
            X5CoreBeaconUploader.getInstance(QBDownloadManager.this.mAppContext).upLoadToBeacon("MTT_DOWNLOAD_INTERCEPT_INSTALL_HOST", (Map)localObject2);
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("host=");
            ((StringBuilder)localObject2).append(QBDownloadManager.this.hostBeforeInterceptDownload);
            ((StringBuilder)localObject2).toString();
          }
          localObject2 = new HashMap();
          ((HashMap)localObject2).put("key", localObject1);
          X5CoreBeaconUploader.getInstance(QBDownloadManager.this.mAppContext).upLoadToBeacon(QBDownloadManager.this.BK_INSTALLED_OVER, (Map)localObject2);
          TBSStatManager.getInstance().userBehaviorStatistics("DJM_INSTALLEDUNIQ");
          TBSStatManager.getInstance().userBehaviorStatistics("AWTQB41");
          TBSStatManager.getInstance().userBehaviorStatistics(QBDownloadManager.this.mStatKeyInstalled);
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("QBInstallBroadcastReceiver.onReceive mStatKeyInstalled=");
          ((StringBuilder)localObject1).append(QBDownloadManager.this.mStatKeyInstalled);
          ((StringBuilder)localObject1).append(" mIsNeedOpenQB=");
          ((StringBuilder)localObject1).append(QBDownloadManager.this.mIsNeedOpenQB);
          ((StringBuilder)localObject1).append(" mQBInstallListeners.size=");
          ((StringBuilder)localObject1).append(QBDownloadManager.this.mQBInstallListeners.size());
          ((StringBuilder)localObject1).toString();
          if (QBDownloadManager.this.mPackageType == 2) {
            TBSStatManager.getInstance().userBehaviorStatistics("DJM016");
          }
          if (QBDownloadManager.this.mExtraParams != null)
          {
            localObject1 = "0";
            localObject2 = QBDownloadManager.this.mExtraParams.getString("param_key_positionid");
            if (localObject2 != null) {
              localObject1 = localObject2;
            }
            localObject2 = "0";
            localObject3 = QBDownloadManager.this.mExtraParams.getString("param_key_functionid");
            if (localObject3 != null) {
              localObject2 = localObject3;
            }
            localObject3 = "0";
            localObject4 = QBDownloadManager.this.mExtraParams.getString("param_key_featureid");
            if (localObject4 != null) {
              localObject3 = localObject4;
            }
            localObject4 = GUIDFactory.getInstance().getStrGuid();
            Object localObject5 = QBDownloadManager.this.mPackageNameInstalling;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("QBInstallBroadcastReceiver.onReceive positionID=");
            localStringBuilder.append((String)localObject1);
            localStringBuilder.append(" featureID=");
            localStringBuilder.append((String)localObject3);
            localStringBuilder.append(" functionID=");
            localStringBuilder.append((String)localObject2);
            localStringBuilder.append(" guid=");
            localStringBuilder.append((String)localObject4);
            localStringBuilder.append(" appName=");
            localStringBuilder.append((String)localObject5);
            localStringBuilder.toString();
            localStringBuilder = new StringBuilder();
            localStringBuilder.append((String)localObject4);
            localStringBuilder.append("|");
            localStringBuilder.append((String)localObject5);
            localStringBuilder.append("|");
            localStringBuilder.append((String)localObject1);
            localStringBuilder.append("|");
            localStringBuilder.append((String)localObject3);
            localStringBuilder.append("|");
            localStringBuilder.append((String)localObject2);
            localObject4 = localStringBuilder.toString();
            localObject5 = new StringBuilder();
            ((StringBuilder)localObject5).append("QBInstallBroadcastReceiver.onReceive recordString=");
            ((StringBuilder)localObject5).append((String)localObject4);
            ((StringBuilder)localObject5).toString();
            QBInstallerRecorder.getInstance().recordQBInstaller((String)localObject4);
            QBDownloadManager.this.uploadDownloadPageUrlToBeacon(paramContext, (String)localObject1, (String)localObject3, (String)localObject2);
          }
          localObject1 = (HashMap)QBDownloadManager.this.mQBInstallListeners.clone();
          localObject2 = ((HashMap)localObject1).keySet().iterator();
        }
      }
      for (;;)
      {
        int i;
        if (((Iterator)localObject2).hasNext())
        {
          i = ((Integer)((Iterator)localObject2).next()).intValue();
          localObject3 = (QBInstallListener)((HashMap)localObject1).get(Integer.valueOf(i));
        }
        int j;
        try
        {
          bool1 = ((QBInstallListener)localObject3).onInstallFinished();
          j = QBDownloadManager.this.mCurrentQBInstallListenerKey;
          if (i != j) {
            continue;
          }
          k = bool1;
        }
        catch (Throwable localThrowable3) {}
        if (k != 0) {
          return;
        }
        if (!QBDownloadManager.this.mIsNeedOpenQB) {
          return;
        }
        localObject1 = paramContext.getApplicationContext().getPackageName();
        if (QBDownloadManager.this.mIntent != null)
        {
          if (!TextUtils.isEmpty((CharSequence)localObject1)) {
            paramIntent.putExtra("ChannelID", (String)localObject1);
          }
          paramIntent.putExtra("PosID", "14002");
        }
        try
        {
          paramContext.startActivity(QBDownloadManager.this.mIntent);
          return;
        }
        catch (Throwable paramContext)
        {
          for (;;)
          {
            continue;
          }
        }
        paramContext = new StringBuilder();
        paramContext.append("QBInstallBroadcastReceiver.onReceive startActivity intent=");
        paramContext.append(QBDownloadManager.this.mIntent);
        paramContext.append(" Failed!");
        Log.w("QBDownloadManager", paramContext.toString());
        return;
        paramIntent = new StringBuilder();
        paramIntent.append("QBInstallBroadcastReceiver.onReceive mOpenUrl=");
        paramIntent.append(QBDownloadManager.this.mOpenUrl);
        paramIntent.toString();
        paramIntent = QBDownloadManager.this.mOpenUrl;
        localObject2 = (QBDownloadManager.QBInstallInfo)QBDownloadManager.this.mQBInstallInfos.get(Integer.valueOf(QBDownloadManager.this.mCurrentUrlProviderKey));
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("QBInstallBroadcastReceiver.onReceive mCurrentInstallInfo=");
        ((StringBuilder)localObject3).append(localObject2);
        ((StringBuilder)localObject3).toString();
        if ((TextUtils.isEmpty(paramIntent)) && (localObject2 != null))
        {
          paramIntent = ((QBDownloadManager.QBInstallInfo)localObject2).lastUrl;
          if (TextUtils.isEmpty(paramIntent))
          {
            if (((QBDownloadManager.QBInstallInfo)localObject2).currentUrlProvider != null) {
              paramIntent = ((QBDownloadManager.QBInstallInfo)localObject2).currentUrlProvider.getCurrentUrl();
            } else {
              paramIntent = null;
            }
          }
          else {}
        }
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("QBInstallBroadcastReceiver.onReceive url=");
        ((StringBuilder)localObject2).append(paramIntent);
        ((StringBuilder)localObject2).toString();
        if ((QBDownloadManager.this.mExtraParams != null) && ("download".equals(QBDownloadManager.this.mExtraParams.getString("param_key_featureid")))) {
          try
          {
            paramIntent = TBSIntentUtils.getOpenUrlAndDownloadInQQBrowserWithReport(paramContext, true, "qb://home", paramIntent, QBDownloadManager.this.mExtraParams.getString("param_key_cookie"), QBDownloadManager.this.mExtraParams.getString("param_key_filename", ""), QBDownloadManager.this.mExtraParams.getString("param_key_mimetype", ""), "APTX1", paramContext.getPackageName(), "", "downloadintercept");
            paramIntent.addFlags(268435456);
            paramContext.startActivity(paramIntent);
            return;
          }
          catch (Exception paramContext)
          {
            paramIntent = new StringBuilder();
            paramIntent.append("QBInstallBroadcastReceiver.onReceive oninstall:");
            paramIntent.append(paramContext.toString());
            paramIntent.toString();
            return;
          }
        }
        if (!TextUtils.isEmpty(paramIntent))
        {
          IntentUtils.openUrlInQQBrowserWithReport(paramContext, paramIntent, "QBInstallBroadcastReceiver", (String)localObject1, "14001");
          return;
          if ("android.intent.action.PACKAGE_REMOVED".equals(paramIntent.getAction()))
          {
            if (!"package:com.tencent.mtt".equals(paramIntent.getDataString())) {
              break label2161;
            }
            paramContext = (HashMap)QBDownloadManager.this.mQBInstallListeners.clone();
            paramIntent = paramContext.keySet().iterator();
            k = bool1;
          }
        }
        for (;;)
        {
          if (paramIntent.hasNext())
          {
            i = ((Integer)paramIntent.next()).intValue();
            localObject1 = (QBInstallListener)paramContext.get(Integer.valueOf(i));
          }
          try
          {
            bool1 = ((QBInstallListener)localObject1).onUninstallFinished();
            j = QBDownloadManager.this.mCurrentQBInstallListenerKey;
            if (i == j) {
              k = bool1;
            }
          }
          catch (Throwable localThrowable1) {}
        }
        if (k != 0)
        {
          return;
          if ("com.tencent.mtt.QBDownloadManager.INSTALLING".equals(paramIntent.getAction()))
          {
            QBDownloadManager.access$1802(QBDownloadManager.this, paramIntent.getIntExtra("key_qb_current_url_provider", 0));
            QBDownloadManager.access$1502(QBDownloadManager.this, paramIntent.getIntExtra("key_qb_current_installlistener", 0));
            paramContext = new StringBuilder();
            paramContext.append("ACTION_QB_INSTALLING mCurrentUrlProviderKey=");
            paramContext.append(QBDownloadManager.this.mCurrentUrlProviderKey);
            paramContext.append(" mCurrentQBInstallListenerKey=");
            paramContext.append(QBDownloadManager.this.mCurrentQBInstallListenerKey);
            paramContext.toString();
            paramContext = (HashMap)QBDownloadManager.this.mQBInstallListeners.clone();
            localObject1 = paramContext.keySet().iterator();
            k = 0;
          }
        }
        for (;;)
        {
          if (((Iterator)localObject1).hasNext())
          {
            i = ((Integer)((Iterator)localObject1).next()).intValue();
            localObject2 = (QBInstallListener)paramContext.get(Integer.valueOf(i));
          }
          try
          {
            bool1 = ((QBInstallListener)localObject2).onInstalling();
            j = QBDownloadManager.this.mCurrentQBInstallListenerKey;
            if (i == j) {
              k = bool1;
            }
          }
          catch (Throwable localThrowable2) {}
        }
        if (k != 0) {
          return;
        }
        QBDownloadManager.access$002(QBDownloadManager.this, paramIntent.getStringExtra("key_qb_packagename_installing"));
        QBDownloadManager.access$102(QBDownloadManager.this, paramIntent.getStringExtra("key_qb_statkey_installed"));
        QBDownloadManager.access$1702(QBDownloadManager.this, paramIntent.getStringExtra("key_qb_openurl"));
        QBDownloadManager.access$1602(QBDownloadManager.this, (Intent)paramIntent.getParcelableExtra("key_qb_intent"));
        QBDownloadManager.access$502(QBDownloadManager.this, paramIntent.getIntExtra("download_type", 0));
        if (QBDownloadManager.this.mPackageType == 2) {
          TBSStatManager.getInstance().userBehaviorStatistics("DJM014");
        }
        QBDownloadManager.access$1102(QBDownloadManager.this, paramIntent.getBooleanExtra("key_qb_need_openqb", true));
        QBDownloadManager.access$1302(QBDownloadManager.this, paramIntent.getBundleExtra("key_qb_extra_params"));
        label2161:
        return;
      }
    }
  }
  
  class QBInstallInfo
  {
    CurrentUrlProvider currentUrlProvider;
    int currentUrlProviderKey;
    String lastUrl;
    
    public QBInstallInfo(int paramInt, CurrentUrlProvider paramCurrentUrlProvider)
    {
      this.currentUrlProviderKey = paramInt;
      this.currentUrlProvider = paramCurrentUrlProvider;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(" currentUrlProvider:");
      localStringBuilder.append(this.currentUrlProvider);
      localStringBuilder.append(" lastUrl:");
      localStringBuilder.append(this.lastUrl);
      localStringBuilder.append(" currentUrlProviderKey:");
      localStringBuilder.append(this.currentUrlProviderKey);
      return localStringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\QBDownloadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */