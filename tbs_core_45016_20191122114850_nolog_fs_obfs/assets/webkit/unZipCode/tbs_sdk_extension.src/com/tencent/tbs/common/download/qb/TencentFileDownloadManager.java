package com.tencent.tbs.common.download.qb;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.tencent.common.utils.FileUtils;
import com.tencent.tbs.common.ui.dialog.ProgressAlertDialog;
import com.tencent.tbs.common.ui.dialog.ProgressAlertDialog.ExtButtonClickListenerSerial;
import com.tencent.tbs.common.ui.dialog.ProgressAlertDialog.IProgressViewListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class TencentFileDownloadManager
  implements QBDownloadListener
{
  private static final String ACTION_QB_INSTALLING = "com.tencent.FileManager.INSTALLING";
  public static final String CHANNEL_ID = "ChannelID";
  public static final String DEFALUT_QB_OPENURL = "qb://home";
  static final String DOWNLOAD_CONFIG_PREFIX = "download_config_";
  private static final String DOWNLOAD_FILENAME_SUFFIX = "_file.apk";
  private static final String DOWNLOAD_LOCK_FILENAME = "tql.data";
  private static final String DOWNLOAD_TEMP_FILENAME_SUFFIX = "_tmpfile.tbs";
  public static final String KEY_CUSTOM_PROGRESS_DOWNLOADING_TEXT = "key_custom_progress_downloading_text";
  public static final String KEY_CUSTOM_PROGRESS_DOWNLOAD_FAIL_TEXT = "key_custom_progress_download_fail_text";
  public static final String KEY_CUSTOM_PROGRESS_DOWNLOAD_SUCC_TEXT = "key_custom_progress_download_succ_text";
  public static final String KEY_CUSTOM_PROGRESS_INIT_TEXT = "key_custom_progress_init_text";
  public static final String KEY_DOWNLOAD_AUTO = "key_download_auto";
  public static final String KEY_EXT_BTN_CLICK_LISTENER = "key_ext_btn_click_listener";
  public static final String KEY_EXT_BTN_TXT = "key_ext_btn_txt";
  public static final String KEY_IS_PROGRESS_TEXT_CUSTOM = "key_is_progress_text_custom";
  public static final String KEY_PROCESS_ID = "key_processid";
  private static final String KEY_QBDOWNLOAD_LASTURL = "qbdownload_lasturl";
  public static final String KEY_QB_CURRENT_INSTALLLISTENER = "key_qb_current_installlistener";
  public static final String KEY_QB_CURRENT_URL_PROVIDER = "key_qb_current_url_provider";
  public static final String KEY_QB_DOWNLOADLISTENER = "key_qb_downloadlistener";
  public static final String KEY_QB_DOWNLOAD_ISSLIENT = "key_qb_download_issilent";
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
  private static final int MSG_SHOW_TOAST = 1000;
  private static final int MSG_START_DOWNLOAD = 100;
  public static final String PARAM_KEY_FEATUREID = "param_key_featureid";
  public static final String PARAM_KEY_FUNCTIONID = "param_key_functionid";
  public static final String PARAM_KEY_POSITIONID = "param_key_positionid";
  private static final String POSID_OPENFILE = "14002";
  public static final String POS_ID = "PosID";
  public static final int PROGRESS_LAYOUT_TYPE_WITHBITMAP = 1;
  private static final String QBDOWNLOADER_THREAD_NAME = "qbdownloader_thread";
  public static final String TAG = "TBSDownloadManager";
  private static TencentFileDownloadManager mInstance;
  private String TARGETFILEPKG = "com.tencent.FileManager";
  private Intent intentForTf = null;
  private Context mAppContext = null;
  private int mCurrentTBSInstallListenerKey;
  private int mCurrentUrlProviderKey;
  private String mDownloadFileTag = "tencentfiledoanload";
  private Handler mDownloadHandler = null;
  private Set<QBDownloadListener> mDownloadListeners = new HashSet();
  private int mDownloadStatus = 0;
  private String mDownloadUrl = "http://appchannel.html5.qq.com/directdown?app=file&channel=11144";
  private Bundle mExtraParams = null;
  FileDownloader mFileDownloader;
  private File mFilePath = null;
  private String mFileSignature = "3082019b30820104a00302010202044e3a4bd0300d06092a864886f70d010105050030123110300e060355040a130754656e63656e74301e170d3131303830343037333534345a170d3431303732373037333534345a30123110300e060355040a130754656e63656e7430819f300d06092a864886f70d010101050003818d00308189028181008beb584b8807431eca10a75996fcd95344198c145149c2c66004d208a03a2f733424fb5e6585b3a3d4edb7ab93866a2c712ffae1b07f4a7e3646ded857dbfaf27be359ae460a9dc35d6e99396a6270b2d4d94559331f5ed919917e67a1f1cd19023770db06b214ffc8702024c854751c8158a3e83aa4030365f9a88f6c907adf0203010001300d06092a864886f70d0101050500038181000148b50768d9c7bdbbb320f2e66cd0eb74efc984b9af413d3508898662fd5f00751bd23224f63ba1d70156139a391010e3c07fbcb80c4a6bd1c62f2319356fae3b25a5dec5e8823cccdc123dfcc6967b01225db046171938554f45f769a01afc8c8c5922f45cdae23fd0e9d8a4551ec3d9de3462563b72c35a392d6cacbe1033";
  public int mInstalledProcessId = -1;
  private Intent mIntent = null;
  private boolean mIsNeedOpenQB = true;
  Bundle mLastBundle = null;
  private String mLastOpenUrl = null;
  private String mOpenUrl = null;
  private String mPackageNameInstalling = null;
  public int mProcessid = -1;
  private ProgressAlertDialog mProgressDialog;
  private String mStatKeyInstalled = null;
  private TBSInstallBroadcastReceiver mTBSInstallBroadcastReceiver = null;
  private Map<Integer, TBSInstallInfo> mTBSInstallInfos = new HashMap();
  private HashMap<Integer, TBSInstallListener> mTBSInstallListeners = new HashMap();
  private Handler mUIHandler = null;
  boolean showProgress = false;
  private long timeStampOfStartInstall = 0L;
  private String ｍPackageName = "com.tencent.FileManager";
  
  private TBSInstallInfo currentUrlProviderToInstallInfo(CurrentUrlProvider paramCurrentUrlProvider)
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
      paramCurrentUrlProvider = new TBSInstallInfo(i, paramCurrentUrlProvider);
      this.mTBSInstallInfos.put(Integer.valueOf(i), paramCurrentUrlProvider);
      return paramCurrentUrlProvider;
    }
    return null;
  }
  
  private void doInitifNeeded(Context paramContext)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("registerQBInstallBroadcastReceiverIfNeeded mTBSInstallBroadcastReceiver=");
    ((StringBuilder)localObject).append(this.mTBSInstallBroadcastReceiver);
    ((StringBuilder)localObject).append(" context=");
    ((StringBuilder)localObject).append(paramContext);
    ((StringBuilder)localObject).toString();
    if (this.mAppContext == null) {
      this.mAppContext = paramContext.getApplicationContext();
    }
    if (this.mFileDownloader == null)
    {
      localObject = this.mAppContext;
      File localFile = getDownloadFileDir();
      String str1 = getTmpDownloadedFileName(this.mDownloadFileTag);
      String str2 = getDownloadedFileName(this.mDownloadFileTag);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("download_config_");
      localStringBuilder.append(this.mDownloadFileTag);
      this.mFileDownloader = new FileDownloader((Context)localObject, localFile, str1, str2, new TbsDownloadHelperConfig(paramContext, localStringBuilder.toString()), false);
      this.mFileDownloader.setmAllowFlowcheck(false);
      this.mFilePath = this.mFileDownloader.getDownloadFile();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("mFilePath:");
      ((StringBuilder)localObject).append(this.mFilePath);
      ((StringBuilder)localObject).toString();
    }
    if (this.mDownloadHandler == null)
    {
      localObject = new HandlerThread("qbdownloader_thread");
      ((HandlerThread)localObject).start();
      this.mDownloadHandler = new Handler(((HandlerThread)localObject).getLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (TencentFileDownloadManager.this.mFilePath == null)
          {
            Log.w("TBSDownloadManager", "handleMessage() mFilePath null return");
            return;
          }
          Object localObject = new StringBuilder();
          ((StringBuilder)localObject).append("handleMessage() msg.what: ");
          ((StringBuilder)localObject).append(paramAnonymousMessage.what);
          ((StringBuilder)localObject).append(",msg.obj:");
          ((StringBuilder)localObject).append(paramAnonymousMessage.obj);
          ((StringBuilder)localObject).toString();
          if (paramAnonymousMessage.what != 100) {
            return;
          }
          localObject = TencentFileDownloadManager.this.mLastBundle;
          if (paramAnonymousMessage.obj != null)
          {
            localObject = (Bundle)paramAnonymousMessage.obj;
            ((Bundle)localObject).getBoolean("key_qb_download_issilent");
          }
          TencentFileDownloadManager.this.mFileDownloader.setExtraParams(false, TencentFileDownloadManager.this, (Bundle)localObject, null);
          paramAnonymousMessage = new Bundle();
          paramAnonymousMessage.putString(FileDownloader.KEY_VF_SIGN, TencentFileDownloadManager.this.mFileSignature);
          TencentFileDownloadManager.this.mFileDownloader.setVerifyInfo(paramAnonymousMessage);
          int i = TencentFileDownloadManager.this.mFileDownloader.startDownload(TencentFileDownloadManager.this.mDownloadUrl, false);
          paramAnonymousMessage = new StringBuilder();
          paramAnonymousMessage.append("mFileDownloader download result:");
          paramAnonymousMessage.append(i);
          paramAnonymousMessage.toString();
        }
      };
    }
    if (this.mTBSInstallBroadcastReceiver == null)
    {
      localObject = new IntentFilter();
      ((IntentFilter)localObject).addAction("android.intent.action.PACKAGE_ADDED");
      ((IntentFilter)localObject).addAction("android.intent.action.PACKAGE_INSTALL");
      ((IntentFilter)localObject).addAction("android.intent.action.PACKAGE_REMOVED");
      ((IntentFilter)localObject).addAction("com.tencent.FileManager.INSTALLING");
      ((IntentFilter)localObject).addDataScheme("package");
      this.mTBSInstallBroadcastReceiver = new TBSInstallBroadcastReceiver(null);
      try
      {
        paramContext.getApplicationContext().registerReceiver(this.mTBSInstallBroadcastReceiver, (IntentFilter)localObject);
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
        this.mTBSInstallBroadcastReceiver = null;
        paramContext = new StringBuilder();
        paramContext.append("registerQBInstallBroadcastReceiverIfNeeded mTBSInstallBroadcastReceiver=");
        paramContext.append(this.mTBSInstallBroadcastReceiver);
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
            Toast.makeText(TencentFileDownloadManager.this.mAppContext, paramAnonymousMessage, 0).show();
          }
        }
      };
    }
  }
  
  private boolean downloadWithCustomProgressUi(Context paramContext, final Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      if (paramBundle.getBundle("key_qb_extra_params") == null) {
        return false;
      }
      Object localObject7 = paramBundle.getBundle("key_qb_extra_params");
      this.showProgress = false;
      this.showProgress = ((Bundle)localObject7).getBoolean("key_show_progress", false);
      if ((this.showProgress) && (ProgressAlertDialog.canShowAlertDialog(paramContext)))
      {
        boolean bool1 = ((Bundle)localObject7).getBoolean("key_download_auto", false);
        String str1 = ((Bundle)localObject7).getString("key_show_message");
        int i = ((Bundle)localObject7).getInt("key_show_type", 0);
        Bitmap localBitmap = (Bitmap)((Bundle)localObject7).getParcelable("key_show_bitmap");
        boolean bool2 = ((Bundle)localObject7).getBoolean("key_is_progress_text_custom", false);
        Object localObject6 = null;
        Object localObject1;
        Object localObject2;
        Object localObject3;
        Object localObject4;
        if (bool2)
        {
          localObject1 = ((Bundle)localObject7).getString("key_custom_progress_init_text");
          localObject2 = ((Bundle)localObject7).getString("key_custom_progress_downloading_text");
          localObject3 = ((Bundle)localObject7).getString("key_custom_progress_download_succ_text");
          localObject4 = ((Bundle)localObject7).getString("key_custom_progress_download_fail_text");
        }
        else
        {
          localObject5 = null;
          localObject1 = localObject5;
          localObject2 = localObject1;
          localObject4 = localObject2;
          localObject3 = localObject2;
          localObject2 = localObject1;
          localObject1 = localObject5;
        }
        String str2 = ((Bundle)localObject7).getString("key_ext_btn_txt");
        localObject7 = ((Bundle)localObject7).getSerializable("key_ext_btn_click_listener");
        Object localObject5 = localObject6;
        if ((localObject7 instanceof ProgressAlertDialog.ExtButtonClickListenerSerial)) {
          localObject5 = (ProgressAlertDialog.ExtButtonClickListenerSerial)localObject7;
        }
        this.mProgressDialog = new ProgressAlertDialog(paramContext, str1, i, localBitmap, bool1, new ProgressAlertDialog.IProgressViewListener()
        {
          public void onInstallButtonClick()
          {
            TencentFileDownloadManager.this.mDownloadHandler.removeMessages(100);
            Message.obtain(TencentFileDownloadManager.this.mDownloadHandler, 100, paramBundle).sendToTarget();
          }
        });
        if (bool2)
        {
          this.mProgressDialog.setIsButtonTextCustomized(true);
          this.mProgressDialog.setCustomInitButtonText((String)localObject1);
          this.mProgressDialog.setCustomDownloadingButtonText((String)localObject2);
          this.mProgressDialog.setCustomDownloadSuccButtonText((String)localObject3);
          this.mProgressDialog.setCustomDownloadFailButtonText((String)localObject4);
        }
        this.mProgressDialog.setExtButtonText(str2);
        this.mProgressDialog.setExtButtonClickListener((ProgressAlertDialog.ExtButtonClickListenerSerial)localObject5);
        this.mProgressDialog.show();
        return true;
      }
      return false;
    }
    return false;
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
  
  private String getBundleMsg(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return "null";
    }
    return paramBundle.toString();
  }
  
  private static File getDownloadFileDir()
  {
    File localFile = Environment.getExternalStorageDirectory();
    if (localFile == null) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(localFile.getAbsolutePath());
    localStringBuilder.append(File.separator);
    localStringBuilder.append("tencent");
    localStringBuilder.append(File.separator);
    localStringBuilder.append("tencentfile");
    localStringBuilder.append(File.separator);
    localStringBuilder.append(".download");
    localStringBuilder.append(File.separator);
    localFile = new File(localStringBuilder.toString());
    if (!localFile.exists()) {
      try
      {
        localFile.mkdirs();
        return localFile;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    return localFile;
  }
  
  private static String getDownloadedFileName(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("_file.apk");
    return localStringBuilder.toString();
  }
  
  public static TencentFileDownloadManager getInstance()
  {
    try
    {
      if (mInstance == null) {
        mInstance = new TencentFileDownloadManager();
      }
      TencentFileDownloadManager localTencentFileDownloadManager = mInstance;
      return localTencentFileDownloadManager;
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
  
  private static String getTmpDownloadedFileName(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("_tmpfile.tbs");
    return localStringBuilder.toString();
  }
  
  static Object invokeMethod(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramArrayOfClass = paramObject.getClass().getMethod(paramString, paramArrayOfClass);
      paramArrayOfClass.setAccessible(true);
      paramArrayOfClass = paramArrayOfClass.invoke(paramObject, paramVarArgs);
      return paramArrayOfClass;
    }
    catch (Throwable paramArrayOfClass)
    {
      paramVarArgs = new StringBuilder();
      paramVarArgs.append("'");
      paramVarArgs.append(paramObject);
      paramVarArgs.append("' invoke method '");
      paramVarArgs.append(paramString);
      paramVarArgs.append("' failed");
      Log.e("QBDownloadManager", paramVarArgs.toString(), paramArrayOfClass);
    }
    return null;
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
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("resumeDownloadWithBundle:");
      ((StringBuilder)localObject).append(getBundleMsg(paramBundle));
      ((StringBuilder)localObject).toString();
      if ((paramBundle != null) && (this.mAppContext != null))
      {
        int i = paramBundle.getInt("key_qb_current_installlistener");
        TBSInstallListener localTBSInstallListener = (TBSInstallListener)this.mTBSInstallListeners.get(Integer.valueOf(i));
        i = paramBundle.getInt("key_qb_current_url_provider");
        localObject = (TBSInstallInfo)this.mTBSInstallInfos.get(Integer.valueOf(i));
        if (localObject == null) {
          localObject = null;
        } else {
          localObject = ((TBSInstallInfo)localObject).currentUrlProvider;
        }
        startDownload(this.mAppContext, paramBundle.getBoolean("key_qb_download_issilent"), paramBundle.getBoolean("key_qb_isupgrate"), paramBundle.getString("key_qb_statkey_installing"), paramBundle.getString("key_qb_statkey_installed"), paramBundle.getString("key_qb_toast"), (QBDownloadListener)paramBundle.getSerializable("key_qb_downloadlistener"), localTBSInstallListener, (CurrentUrlProvider)localObject, (Intent)paramBundle.getParcelable("key_qb_intent"), paramBundle.getString("key_qb_openurl"), paramBundle.getBoolean("key_qb_need_openqb"), (Bundle)paramBundle.getParcelable("key_qb_extra_params"));
      }
      return;
    }
    finally {}
  }
  
  private Bundle storeParamsToBundle(boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, TBSInstallListener paramTBSInstallListener, CurrentUrlProvider paramCurrentUrlProvider, Intent paramIntent, String paramString4, boolean paramBoolean3, Bundle paramBundle)
  {
    Bundle localBundle2 = new Bundle();
    Bundle localBundle1 = paramBundle;
    if (paramBundle == null) {
      localBundle1 = new Bundle();
    }
    if (paramTBSInstallListener != null)
    {
      registerTBSInstallListener(paramTBSInstallListener);
      localBundle2.putInt("key_qb_current_installlistener", paramTBSInstallListener.hashCode());
    }
    if (paramCurrentUrlProvider != null) {
      localBundle2.putInt("key_qb_current_url_provider", currentUrlProviderToInstallInfo(paramCurrentUrlProvider).currentUrlProviderKey);
    }
    localBundle2.putBoolean("key_qb_download_issilent", paramBoolean1);
    localBundle2.putBoolean("key_qb_isupgrate", paramBoolean2);
    localBundle2.putString("key_qb_statkey_installing", paramString1);
    localBundle2.putString("key_qb_statkey_installed", paramString2);
    localBundle2.putString("key_qb_toast", paramString3);
    localBundle2.putSerializable("key_qb_downloadlistener", paramQBDownloadListener);
    localBundle2.putParcelable("key_qb_intent", paramIntent);
    localBundle2.putString("key_qb_openurl", paramString4);
    localBundle2.putBoolean("key_qb_need_openqb", paramBoolean3);
    localBundle2.putParcelable("key_qb_extra_params", localBundle1);
    return localBundle2;
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
    TBSInstallBroadcastReceiver localTBSInstallBroadcastReceiver = this.mTBSInstallBroadcastReceiver;
    if (localTBSInstallBroadcastReceiver != null) {}
    try
    {
      paramContext.unregisterReceiver(localTBSInstallBroadcastReceiver);
      this.mTBSInstallBroadcastReceiver = null;
      return;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
  }
  
  public void cancelDownload()
  {
    try
    {
      if (this.mFileDownloader != null) {
        this.mFileDownloader.stopDownload();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  void deleteCachedFile()
  {
    FileDownloader localFileDownloader = this.mFileDownloader;
    if (localFileDownloader != null) {
      localFileDownloader.deleteDownloadedFile();
    }
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
  
  public int getTBSDownloadProgress()
  {
    int i = 0;
    try
    {
      if (this.mFileDownloader != null) {
        i = this.mFileDownloader.currentDownloadProgress();
      }
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  protected void installDownloadFile(Context paramContext, Bundle paramBundle)
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
    int i = paramBundle.getInt("key_qb_current_installlistener");
    TBSInstallListener localTBSInstallListener = (TBSInstallListener)this.mTBSInstallListeners.get(Integer.valueOf(i));
    i = paramBundle.getInt("key_qb_current_url_provider");
    paramBundle = (TBSInstallInfo)this.mTBSInstallInfos.get(Integer.valueOf(i));
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("installDownloadFile Bundle installInfo=");
    localStringBuilder.append(paramBundle);
    localStringBuilder.toString();
    installDownloadFile(paramContext, str1, str2, localTBSInstallListener, paramBundle, localIntent, str3, bool, (Bundle)localObject);
  }
  
  @Deprecated
  public boolean installDownloadFile(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    return installDownloadFile(paramContext, paramString1, paramString2, null, null, null, paramBundle.getString("key_qb_openurl"), paramBundle.getBoolean("key_qb_need_openqb", false), paramBundle);
  }
  
  protected boolean installDownloadFile(Context paramContext, String paramString1, String paramString2, TBSInstallListener paramTBSInstallListener, TBSInstallInfo paramTBSInstallInfo, Intent paramIntent, String paramString3, boolean paramBoolean, Bundle paramBundle)
  {
    for (;;)
    {
      try
      {
        if (Thread.currentThread() == Looper.getMainLooper().getThread())
        {
          bool = true;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("installDownloadFile statKeyInstalling=");
          localStringBuilder.append(paramString1);
          localStringBuilder.append(" statKeyInstalled=");
          localStringBuilder.append(paramString2);
          localStringBuilder.append(" needOpenQB=");
          localStringBuilder.append(paramBoolean);
          localStringBuilder.append(", extraParams:");
          localStringBuilder.append(getBundleMsg(paramBundle));
          localStringBuilder.append(",isUiThread:");
          localStringBuilder.append(bool);
          localStringBuilder.toString();
          doInitifNeeded(paramContext);
          if ((this.mFilePath != null) && (this.mFilePath.exists()))
          {
            if (verifySignature(this.mFilePath))
            {
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("installDownloadFile statKeyInstalling=");
              localStringBuilder.append(paramString1);
              localStringBuilder.append(" statKeyInstalled=");
              localStringBuilder.append(paramString2);
              localStringBuilder.append(" needOpenQB=");
              localStringBuilder.append(paramBoolean);
              localStringBuilder.toString();
              new HashMap().put("key", paramString2);
              this.mProcessid = Process.myPid();
              paramString1 = new StringBuilder();
              paramString1.append("start install processid:");
              paramString1.append(this.mProcessid);
              paramString1.toString();
              paramString1 = new Intent("com.tencent.FileManager.INSTALLING");
              paramString1.putExtra("key_qb_packagename_installing", paramContext.getApplicationContext().getPackageName());
              paramString1.putExtra("key_qb_statkey_installed", paramString2);
              paramString1.putExtra("key_qb_openurl", paramString3);
              paramString1.putExtra("key_qb_intent", paramIntent);
              paramString1.putExtra("key_qb_need_openqb", paramBoolean);
              paramString1.putExtra("key_processid", this.mProcessid);
              paramString1.setData(Uri.parse("package:com.tencent.FileManager.INSTALLING"));
              if (paramTBSInstallListener != null)
              {
                int i = paramTBSInstallListener.hashCode();
                paramString1.putExtra("key_qb_current_installlistener", i);
                this.mTBSInstallListeners.put(Integer.valueOf(i), paramTBSInstallListener);
              }
              if (paramTBSInstallInfo != null) {
                paramString1.putExtra("key_qb_current_url_provider", paramTBSInstallInfo.currentUrlProviderKey);
              }
              removeUiKeysFromBundle(paramBundle);
              paramString1.putExtra("key_qb_extra_params", paramBundle);
              paramContext.sendBroadcast(paramString1);
              try
              {
                InstallUtil.startInstall(paramContext, this.mFilePath);
                return true;
              }
              catch (Exception paramContext)
              {
                paramString1 = new StringBuilder();
                paramString1.append("MttLoader.verifySignature error:");
                paramString1.append(paramContext.toString());
                paramString1.toString();
              }
            }
            else
            {
              paramString1 = new StringBuilder();
              paramString1.append("verifySignature error:");
              paramString1.append(this.mFilePath.getAbsolutePath());
              paramString1.toString();
              if (Looper.myLooper() == Looper.getMainLooper()) {
                Toast.makeText(paramContext, "x5_qb_download_file_verifyfailed", 0).show();
              }
              deleteCachedFile();
            }
          }
          else if (Looper.myLooper() == Looper.getMainLooper()) {
            Toast.makeText(paramContext, "x5_qb_download_file_notexists", 0).show();
          }
          return false;
        }
      }
      finally {}
      boolean bool = false;
    }
  }
  
  public int installDownloadFileCore(Context paramContext, String paramString1, String paramString2, String paramString3, Bundle paramBundle)
  {
    if (paramContext != null) {}
    for (;;)
    {
      try
      {
        if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)) && (!TextUtils.isEmpty(paramString3)))
        {
          if (paramBundle == null) {
            break label142;
          }
          String str2 = paramBundle.getString("key_qb_statkey_installing");
          String str1 = paramBundle.getString("key_qb_statkey_installed");
          String str3 = paramBundle.getString("key_qb_openurl");
          bool = paramBundle.getBoolean("key_qb_need_openqb");
          paramBundle.putString("param_key_positionid", paramString1);
          paramBundle.putString("param_key_featureid", paramString2);
          paramBundle.putString("param_key_functionid", paramString3);
          paramString1 = str3;
          paramString2 = str2;
          paramString3 = str1;
          installDownloadFile(paramContext, paramString2, paramString3, null, null, null, paramString1, bool, paramBundle);
          return -7;
        }
      }
      finally {}
      return -6;
      label142:
      paramString3 = "";
      paramString1 = "qb://home";
      boolean bool = false;
      paramString2 = "";
    }
  }
  
  public boolean isDownloading()
  {
    try
    {
      if (this.mFileDownloader != null)
      {
        boolean bool = this.mFileDownloader.isDownloading();
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
  
  public boolean isFileDownloaded()
  {
    try
    {
      if (this.mFilePath != null)
      {
        bool = this.mFilePath.exists();
        return bool;
      }
      boolean bool = new File(getDownloadFileDir(), getDownloadedFileName(this.mDownloadFileTag)).exists();
      return bool;
    }
    finally {}
  }
  
  public void onDownloadFailed(boolean paramBoolean, Bundle paramBundle)
  {
    ??? = new StringBuilder();
    ((StringBuilder)???).append("onDownloadFailed,params:");
    ((StringBuilder)???).append(getBundleMsg(paramBundle));
    ((StringBuilder)???).toString();
    if (this.mDownloadStatus == 5) {
      return;
    }
    this.mDownloadStatus = 3;
    for (;;)
    {
      synchronized (this.mDownloadListeners)
      {
        Iterator localIterator = this.mDownloadListeners.iterator();
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
          return;
        }
      }
    }
  }
  
  public void onDownloadPause(boolean paramBoolean, int paramInt)
  {
    ??? = new StringBuilder();
    ((StringBuilder)???).append("onDownloadPause isSilent=");
    ((StringBuilder)???).append(paramBoolean);
    ((StringBuilder)???).append(" progress=");
    ((StringBuilder)???).append(paramInt);
    ((StringBuilder)???).toString();
    for (;;)
    {
      synchronized (this.mDownloadListeners)
      {
        Iterator localIterator = this.mDownloadListeners.iterator();
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
            localQBDownloadListener.onDownloadPause(paramBoolean, paramInt);
          }
          catch (Throwable localThrowable) {}
          return;
        }
      }
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
      synchronized (this.mDownloadListeners)
      {
        Iterator localIterator = this.mDownloadListeners.iterator();
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
            localStringBuilder.append(" DownloadListener=");
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
    ??? = new StringBuilder();
    ((StringBuilder)???).append("onDownloadResume isSilent=");
    ((StringBuilder)???).append(paramBoolean);
    ((StringBuilder)???).append(" progress=");
    ((StringBuilder)???).append(paramInt);
    ((StringBuilder)???).toString();
    for (;;)
    {
      synchronized (this.mDownloadListeners)
      {
        Iterator localIterator = this.mDownloadListeners.iterator();
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
            localQBDownloadListener.onDownloadResume(paramBoolean, paramInt);
          }
          catch (Throwable localThrowable) {}
          return;
        }
      }
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
      synchronized (this.mDownloadListeners)
      {
        Iterator localIterator = this.mDownloadListeners.iterator();
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
    ??? = new StringBuilder();
    ((StringBuilder)???).append("onDownloadSucess,isSilent:");
    ((StringBuilder)???).append(paramBoolean);
    ((StringBuilder)???).append("params:");
    ((StringBuilder)???).append(getBundleMsg(paramBundle));
    ((StringBuilder)???).toString();
    this.mDownloadStatus = 1;
    for (;;)
    {
      QBDownloadListener localQBDownloadListener;
      synchronized (this.mDownloadListeners)
      {
        Iterator localIterator = this.mDownloadListeners.iterator();
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
        localQBDownloadListener.onDownloadSucess(paramBoolean, paramString, paramBundle);
      }
      catch (Throwable localThrowable) {}
      paramString = this.mProgressDialog;
      if (paramString != null)
      {
        paramString.setStatus(1, 0);
        this.mProgressDialog.onDownloadFinish();
      }
      if (!paramBoolean)
      {
        if (paramBundle != null) {
          paramBundle.getString("param_key_functionid", "0");
        }
        installDownloadFile(this.mAppContext, paramBundle);
      }
      return;
      paramString = finally;
      throw paramString;
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
  
  public void registerDownloadListener(QBDownloadListener paramQBDownloadListener)
  {
    Set localSet = this.mDownloadListeners;
    if (paramQBDownloadListener != null) {}
    try
    {
      if (this.mDownloadListeners != null) {
        this.mDownloadListeners.add(paramQBDownloadListener);
      }
      return;
    }
    finally {}
  }
  
  public void registerTBSInstallListener(TBSInstallListener paramTBSInstallListener)
  {
    HashMap localHashMap = this.mTBSInstallListeners;
    if (paramTBSInstallListener != null) {}
    try
    {
      this.mTBSInstallListeners.put(Integer.valueOf(paramTBSInstallListener.hashCode()), paramTBSInstallListener);
      return;
    }
    finally {}
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
  
  public TencentFileDownloadManager setDownloadAppPackagename(String paramString)
  {
    this.ｍPackageName = paramString;
    return this;
  }
  
  public TencentFileDownloadManager setDownloadFileTag(String paramString)
  {
    this.mDownloadFileTag = paramString;
    return this;
  }
  
  public TencentFileDownloadManager setDownloadUrl(String paramString)
  {
    this.mDownloadUrl = paramString;
    return this;
  }
  
  public TencentFileDownloadManager setSignature(String paramString)
  {
    this.mFileSignature = paramString;
    return this;
  }
  
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, Bundle paramBundle)
  {
    return startDownload(paramContext, false, true, paramString1, paramString2, paramString3, paramQBDownloadListener, null, null, null, paramBundle.getString("key_qb_openurl"), paramBundle.getBoolean("key_qb_need_openqb", true), paramBundle);
  }
  
  public int startDownload(Context paramContext, boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, QBDownloadListener paramQBDownloadListener, TBSInstallListener paramTBSInstallListener, CurrentUrlProvider paramCurrentUrlProvider, Intent paramIntent, String paramString4, boolean paramBoolean3, Bundle paramBundle)
  {
    for (;;)
    {
      try
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("startDownload isSilent=");
        ((StringBuilder)localObject).append(paramBoolean1);
        ((StringBuilder)localObject).append(" needOpenQB=");
        ((StringBuilder)localObject).append(paramBoolean3);
        ((StringBuilder)localObject).append(",openUrl:");
        ((StringBuilder)localObject).append(paramString4);
        ((StringBuilder)localObject).append(",isUpgrade:");
        ((StringBuilder)localObject).append(paramBoolean2);
        ((StringBuilder)localObject).append(",extraParams:");
        ((StringBuilder)localObject).append(getBundleMsg(paramBundle));
        ((StringBuilder)localObject).toString();
        boolean bool1 = FileUtils.hasSDcard();
        this.intentForTf = ((Intent)paramBundle.getParcelable("intentForTf"));
        new HashMap().put("key", paramString2);
        if (!bool1)
        {
          Log.w("TBSDownloadManager", "startDownloadIfNeeded sdcard not exist");
          return -2;
        }
        doInitifNeeded(paramContext);
        if ((!paramBoolean2) && (AppUtil.isAppInstalled(this.mAppContext, this.TARGETFILEPKG)))
        {
          paramContext = new StringBuilder();
          paramContext.append(this.TARGETFILEPKG);
          paramContext.append(" already installed!!!");
          Log.w("TBSDownloadManager", paramContext.toString());
          return -1;
        }
        if (paramQBDownloadListener != null) {
          registerDownloadListener(paramQBDownloadListener);
        }
        if (paramTBSInstallListener != null) {
          registerTBSInstallListener(paramTBSInstallListener);
        }
        if (paramBundle != null)
        {
          localObject = paramBundle.getString("param_key_functionid", "0");
          this.mLastBundle = storeParamsToBundle(paramBoolean1, paramBoolean2, paramString1, paramString2, paramString3, paramQBDownloadListener, paramTBSInstallListener, paramCurrentUrlProvider, paramIntent, paramString4, paramBoolean3, paramBundle);
          this.mLastBundle.putString("param_key_functionid", (String)localObject);
          paramQBDownloadListener = this.mFilePath;
          if (paramQBDownloadListener == null) {
            return -2;
          }
          if ((this.mFilePath != null) && (this.mFilePath.exists()))
          {
            if (verifySignature(this.mFilePath))
            {
              installDownloadFile(paramContext, paramString1, paramString2, paramTBSInstallListener, currentUrlProviderToInstallInfo(paramCurrentUrlProvider), paramIntent, paramString4, paramBoolean3, paramBundle);
              return -4;
            }
            deleteCachedFile();
          }
          bool1 = false;
          this.showProgress = false;
          if (paramBundle == null) {
            break label934;
          }
          this.showProgress = paramBundle.getBoolean("key_show_progress", false);
          if (!this.showProgress) {
            break label934;
          }
          paramBoolean2 = paramBundle.getBoolean("key_download_auto", false);
          str4 = paramBundle.getString("key_show_message");
          i = paramBundle.getInt("key_show_type", 0);
          localBitmap = (Bitmap)paramBundle.getParcelable("key_show_bitmap");
          bool1 = paramBundle.getBoolean("key_is_progress_text_custom", false);
          if (!bool1) {
            break label913;
          }
          localObject = paramBundle.getString("key_custom_progress_init_text");
          str1 = paramBundle.getString("key_custom_progress_downloading_text");
          str2 = paramBundle.getString("key_custom_progress_download_succ_text");
          str3 = paramBundle.getString("key_custom_progress_download_fail_text");
          str5 = paramBundle.getString("key_ext_btn_txt");
          paramQBDownloadListener = paramBundle.getSerializable("key_ext_btn_click_listener");
          if (!(paramQBDownloadListener instanceof ProgressAlertDialog.ExtButtonClickListenerSerial)) {
            break label928;
          }
          paramQBDownloadListener = (ProgressAlertDialog.ExtButtonClickListenerSerial)paramQBDownloadListener;
          boolean bool2 = this.mFileDownloader.isDownloading();
          if (bool2)
          {
            if (paramBoolean1) {
              return -3;
            }
            if ((this.showProgress) && (ProgressAlertDialog.canShowAlertDialog(paramContext)))
            {
              this.mProgressDialog = new ProgressAlertDialog(paramContext, str4, i, localBitmap, paramBoolean2, null);
              if (bool1)
              {
                this.mProgressDialog.setIsButtonTextCustomized(true);
                this.mProgressDialog.setCustomInitButtonText((String)localObject);
                this.mProgressDialog.setCustomDownloadingButtonText(str1);
                this.mProgressDialog.setCustomDownloadSuccButtonText(str2);
                this.mProgressDialog.setCustomDownloadFailButtonText(str3);
              }
              this.mProgressDialog.setExtButtonText(str5);
              this.mProgressDialog.setExtButtonClickListener(paramQBDownloadListener);
              this.mProgressDialog.show();
            }
            return -3;
          }
          if (this.mFilePath.exists())
          {
            if (!paramBoolean1) {
              installDownloadFile(paramContext, paramString1, paramString2, paramTBSInstallListener, currentUrlProviderToInstallInfo(paramCurrentUrlProvider), paramIntent, paramString4, paramBoolean3, paramBundle);
            }
            return -4;
          }
          this.mDownloadHandler.removeMessages(100);
          if ((!paramBoolean1) && (!TextUtils.isEmpty(paramString3))) {
            Message.obtain(this.mUIHandler, 1000, paramString3).sendToTarget();
          }
          if ((this.showProgress) && (ProgressAlertDialog.canShowAlertDialog(paramContext)))
          {
            this.mProgressDialog = new ProgressAlertDialog(paramContext, str4, i, localBitmap, paramBoolean2, new ProgressAlertDialog.IProgressViewListener()
            {
              public void onInstallButtonClick()
              {
                Message.obtain(TencentFileDownloadManager.this.mDownloadHandler, 100, TencentFileDownloadManager.this.mLastBundle).sendToTarget();
              }
            });
            if (bool1)
            {
              this.mProgressDialog.setIsButtonTextCustomized(true);
              this.mProgressDialog.setCustomInitButtonText((String)localObject);
              this.mProgressDialog.setCustomDownloadingButtonText(str1);
              this.mProgressDialog.setCustomDownloadSuccButtonText(str2);
              this.mProgressDialog.setCustomDownloadFailButtonText(str3);
            }
            this.mProgressDialog.setExtButtonText(str5);
            this.mProgressDialog.setExtButtonClickListener(paramQBDownloadListener);
            this.mProgressDialog.show();
          }
          else
          {
            Message.obtain(this.mDownloadHandler, 100, this.mLastBundle).sendToTarget();
          }
          return 1;
        }
      }
      finally {}
      Object localObject = "0";
      continue;
      label913:
      localObject = null;
      String str1 = null;
      String str2 = null;
      String str3 = null;
      continue;
      label928:
      paramQBDownloadListener = null;
      continue;
      label934:
      String str4 = "";
      paramBoolean2 = false;
      paramQBDownloadListener = null;
      int i = 0;
      Bitmap localBitmap = null;
      localObject = null;
      str1 = null;
      str2 = null;
      str3 = null;
      String str5 = null;
    }
  }
  
  public void unregisterDownloadListener(QBDownloadListener paramQBDownloadListener)
  {
    Set localSet = this.mDownloadListeners;
    if (paramQBDownloadListener != null) {}
    try
    {
      if ((this.mDownloadListeners != null) && (this.mDownloadListeners.contains(paramQBDownloadListener))) {
        this.mDownloadListeners.remove(paramQBDownloadListener);
      }
      return;
    }
    finally {}
  }
  
  public void unregisterTBSInstallListener(TBSInstallListener paramTBSInstallListener)
  {
    HashMap localHashMap = this.mTBSInstallListeners;
    if (paramTBSInstallListener != null) {}
    try
    {
      if (this.mTBSInstallListeners.containsKey(Integer.valueOf(paramTBSInstallListener.hashCode()))) {
        this.mTBSInstallListeners.remove(Integer.valueOf(paramTBSInstallListener.hashCode()));
      }
      return;
    }
    finally {}
  }
  
  boolean verifySignature(File paramFile)
  {
    paramFile = AppUtil.getSignatureFromApk(this.mAppContext, paramFile);
    if (paramFile == null) {
      return true;
    }
    return this.mFileSignature.equals(paramFile);
  }
  
  private class TBSInstallBroadcastReceiver
    extends BroadcastReceiver
  {
    private TBSInstallBroadcastReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("TBSInstallBroadcastReceiver.onReceive intent=");
      ((StringBuilder)localObject).append(paramIntent);
      ((StringBuilder)localObject).toString();
      boolean bool1;
      int m;
      int k;
      int i;
      if (paramIntent != null)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("TBSInstallBroadcastReceiver.onReceive action=");
        ((StringBuilder)localObject).append(paramIntent.getAction());
        ((StringBuilder)localObject).toString();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("package:");
        ((StringBuilder)localObject).append(TencentFileDownloadManager.this.ｍPackageName);
        localObject = ((StringBuilder)localObject).toString();
        boolean bool2 = "android.intent.action.PACKAGE_ADDED".equals(paramIntent.getAction());
        bool1 = false;
        m = 0;
        k = 0;
        if (bool2)
        {
          if ((!((String)localObject).equals(paramIntent.getDataString())) || (!AppUtil.isAppInstalled(paramContext, TencentFileDownloadManager.this.ｍPackageName))) {
            break label1007;
          }
          paramIntent = new StringBuilder();
          paramIntent.append("TBSInstallBroadcastReceiver.onReceive mPackageNameInstalling=");
          paramIntent.append(TencentFileDownloadManager.this.mPackageNameInstalling);
          paramIntent.append(" mStatKeyInstalled=");
          paramIntent.append(TencentFileDownloadManager.this.mStatKeyInstalled);
          paramIntent.append(",mIsNeedOpenQB:");
          paramIntent.append(TencentFileDownloadManager.this.mIsNeedOpenQB);
          paramIntent.toString();
          i = TencentFileDownloadManager.this.mProcessid;
          i = TencentFileDownloadManager.this.mInstalledProcessId;
          paramContext.getApplicationContext().getPackageName();
          if ((TencentFileDownloadManager.this.mPackageNameInstalling == null) || (!TencentFileDownloadManager.this.mPackageNameInstalling.equals(paramContext.getApplicationContext().getPackageName())) || (TencentFileDownloadManager.this.mStatKeyInstalled == null)) {
            break label1007;
          }
          paramIntent = new StringBuilder();
          paramIntent.append("TBSInstallBroadcastReceiver.onReceive mStatKeyInstalled=");
          paramIntent.append(TencentFileDownloadManager.this.mStatKeyInstalled);
          paramIntent.append(" mIsNeedOpenQB=");
          paramIntent.append(TencentFileDownloadManager.this.mIsNeedOpenQB);
          paramIntent.append(" mTBSInstallListeners.size=");
          paramIntent.append(TencentFileDownloadManager.this.mTBSInstallListeners.size());
          paramIntent.toString();
          TencentFileDownloadManager.this.deleteCachedFile();
          paramIntent = (HashMap)TencentFileDownloadManager.this.mTBSInstallListeners.clone();
          localObject = paramIntent.keySet().iterator();
        }
      }
      for (;;)
      {
        TBSInstallListener localTBSInstallListener;
        if (((Iterator)localObject).hasNext())
        {
          i = ((Integer)((Iterator)localObject).next()).intValue();
          localTBSInstallListener = (TBSInstallListener)paramIntent.get(Integer.valueOf(i));
        }
        int j;
        try
        {
          bool1 = localTBSInstallListener.onInstallFinished();
          j = TencentFileDownloadManager.this.mCurrentTBSInstallListenerKey;
          if (i != j) {
            continue;
          }
          k = bool1;
        }
        catch (Throwable localThrowable2) {}
        if (k != 0) {
          return;
        }
        if (!TencentFileDownloadManager.this.mIsNeedOpenQB) {
          return;
        }
        if (TencentFileDownloadManager.this.intentForTf != null) {}
        try
        {
          paramContext.startActivity(TencentFileDownloadManager.this.intentForTf);
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
        paramContext.append("TBSInstallBroadcastReceiver.onReceive startActivity intent=");
        paramContext.append(TencentFileDownloadManager.this.intentForTf);
        paramContext.append(" Failed!");
        Log.w("TBSDownloadManager", paramContext.toString());
        return;
        if ("android.intent.action.PACKAGE_REMOVED".equals(paramIntent.getAction()))
        {
          if (!((String)localObject).equals(paramIntent.getDataString())) {
            break label1007;
          }
          paramContext = (HashMap)TencentFileDownloadManager.this.mTBSInstallListeners.clone();
          paramIntent = paramContext.keySet().iterator();
          k = bool1;
        }
        for (;;)
        {
          if (paramIntent.hasNext())
          {
            i = ((Integer)paramIntent.next()).intValue();
            localObject = (TBSInstallListener)paramContext.get(Integer.valueOf(i));
          }
          label1007:
          try
          {
            bool1 = ((TBSInstallListener)localObject).onUninstallFinished();
            j = TencentFileDownloadManager.this.mCurrentTBSInstallListenerKey;
            if (i == j) {
              k = bool1;
            }
          }
          catch (Throwable localThrowable1) {}
        }
        if (k != 0)
        {
          return;
          if ("com.tencent.FileManager.INSTALLING".equals(paramIntent.getAction()))
          {
            TencentFileDownloadManager.access$702(TencentFileDownloadManager.this, paramIntent.getIntExtra("key_qb_current_url_provider", 0));
            TencentFileDownloadManager.access$502(TencentFileDownloadManager.this, paramIntent.getIntExtra("key_qb_current_installlistener", 0));
            paramContext = new StringBuilder();
            paramContext.append("ACTION_QB_INSTALLING mCurrentUrlProviderKey=");
            paramContext.append(TencentFileDownloadManager.this.mCurrentUrlProviderKey);
            paramContext.append(" mCurrentTBSInstallListenerKey=");
            paramContext.append(TencentFileDownloadManager.this.mCurrentTBSInstallListenerKey);
            paramContext.toString();
            paramContext = (HashMap)TencentFileDownloadManager.this.mTBSInstallListeners.clone();
            localObject = paramContext.keySet().iterator();
            k = m;
          }
        }
        for (;;)
        {
          if (((Iterator)localObject).hasNext())
          {
            i = ((Integer)((Iterator)localObject).next()).intValue();
            localTBSInstallListener = (TBSInstallListener)paramContext.get(Integer.valueOf(i));
          }
          try
          {
            bool1 = localTBSInstallListener.onInstalling();
            j = TencentFileDownloadManager.this.mCurrentTBSInstallListenerKey;
            if (i == j) {
              k = bool1;
            }
          }
          catch (Throwable localThrowable3) {}
        }
        if (k != 0) {
          return;
        }
        TencentFileDownloadManager.access$102(TencentFileDownloadManager.this, paramIntent.getStringExtra("key_qb_packagename_installing"));
        TencentFileDownloadManager.access$202(TencentFileDownloadManager.this, paramIntent.getStringExtra("key_qb_statkey_installed"));
        TencentFileDownloadManager.access$802(TencentFileDownloadManager.this, paramIntent.getStringExtra("key_qb_openurl"));
        TencentFileDownloadManager.access$902(TencentFileDownloadManager.this, (Intent)paramIntent.getParcelableExtra("key_qb_intent"));
        TencentFileDownloadManager.access$302(TencentFileDownloadManager.this, paramIntent.getBooleanExtra("key_qb_need_openqb", true));
        TencentFileDownloadManager.access$1002(TencentFileDownloadManager.this, paramIntent.getBundleExtra("key_qb_extra_params"));
        TencentFileDownloadManager.this.mInstalledProcessId = paramIntent.getIntExtra("key_processid", -1);
        paramContext = new StringBuilder();
        paramContext.append("ACTION_QB_INSTALLING mInstalledProcessId:");
        paramContext.append(TencentFileDownloadManager.this.mInstalledProcessId);
        paramContext.toString();
        return;
      }
    }
  }
  
  class TBSInstallInfo
  {
    CurrentUrlProvider currentUrlProvider;
    int currentUrlProviderKey;
    String lastUrl;
    
    public TBSInstallInfo(int paramInt, CurrentUrlProvider paramCurrentUrlProvider)
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\TencentFileDownloadManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */