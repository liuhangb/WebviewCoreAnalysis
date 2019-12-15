package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.tbs.common.log.LogManager;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

class MiniQBDownloader
{
  public static final boolean DEBUG_DISABLE_DOWNLOAD = false;
  private static final String FORMAL_POST_URL = "https://cfg.imtt.qq.com/miniqb?v=2&mk=";
  private static final int FUNCTION_DOWNLOAD = 0;
  private static final int FUNCTION_UPDATE = 1;
  public static final String LOGTAG = "MiniQBUpgrade";
  static final int MAX_SDK_RESPONSECODE = 10000;
  private static final int MSG_PREPARE_DOWNLOAD = 102;
  private static final int MSG_START_DOWNLOAD = 101;
  private static final int PROTOCOL_VERSION = 1;
  static final int RESPONSECODE_APK = 1;
  static final int RESPONSECODE_UPDATE_APK = 2;
  static final int RESPONSECODE_UPDATE_PATCH = 3;
  private static final String TBS_CORE_LOCK_FILE = "tbslock.txt";
  static final String TBS_METADATA = "com.tencent.mm.BuildInfo.CLIENT_VERSION";
  private static final String TEST_POST_URL = "http://cfg.sparta.html5.qq.com/miniqb?v=2&mk=";
  private static MiniQBApkDownloader sApkDownloader;
  private static Context sAppContext;
  private static String sDefalutUserAgent;
  private static Handler sDownloaderHandler;
  private static HandlerThread sHandlerThread;
  
  private static void freeFileLock(FileLock paramFileLock, FileOutputStream paramFileOutputStream)
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
  
  static String getDefaultUserAgent(Context paramContext)
  {
    if (!TextUtils.isEmpty(sDefalutUserAgent)) {
      return sDefalutUserAgent;
    }
    Locale localLocale = Locale.getDefault();
    StringBuffer localStringBuffer = new StringBuffer();
    paramContext = Build.VERSION.RELEASE;
    try
    {
      str = new String(paramContext.getBytes("UTF-8"), "ISO8859-1");
      paramContext = str;
    }
    catch (Exception localException1)
    {
      String str;
      for (;;) {}
    }
    if (paramContext.length() > 0) {
      localStringBuffer.append(paramContext);
    } else {
      localStringBuffer.append("1.0");
    }
    localStringBuffer.append("; ");
    paramContext = localLocale.getLanguage();
    if (paramContext != null)
    {
      localStringBuffer.append(paramContext.toLowerCase());
      paramContext = localLocale.getCountry();
      if (paramContext != null)
      {
        localStringBuffer.append("-");
        localStringBuffer.append(paramContext.toLowerCase());
      }
    }
    else
    {
      localStringBuffer.append("en");
    }
    if ("REL".equals(Build.VERSION.CODENAME)) {
      paramContext = Build.MODEL;
    }
    try
    {
      str = new String(paramContext.getBytes("UTF-8"), "ISO8859-1");
      paramContext = str;
    }
    catch (Exception localException2)
    {
      for (;;) {}
    }
    if (paramContext.length() > 0)
    {
      localStringBuffer.append("; ");
      localStringBuffer.append(paramContext);
    }
    paramContext = "";
    if (!TextUtils.isEmpty(Build.ID)) {
      paramContext = Build.ID.replaceAll("[一-龥]", "");
    }
    if (!TextUtils.isEmpty(paramContext))
    {
      localStringBuffer.append(" Build/");
      localStringBuffer.append(paramContext);
    }
    paramContext = String.format("Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 Mobile Safari/533.1", new Object[] { localStringBuffer });
    sDefalutUserAgent = paramContext;
    return paramContext;
  }
  
  private static File getLockFile(Context paramContext, String paramString)
  {
    paramContext = MiniQBUpgradeManager.getInstance().getTbsCorePrivateDir(paramContext);
    if (paramContext == null) {
      return null;
    }
    paramContext = new File(paramContext, paramString);
    if (paramContext.exists()) {
      return paramContext;
    }
    try
    {
      paramContext.createNewFile();
      return paramContext;
    }
    catch (IOException paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  private static FileOutputStream getLockFos(Context paramContext)
  {
    paramContext = getLockFile(paramContext, "tbslock.txt");
    if (paramContext != null) {
      try
      {
        paramContext = new FileOutputStream(paramContext);
        return paramContext;
      }
      catch (FileNotFoundException paramContext)
      {
        paramContext.printStackTrace();
      }
    }
    return null;
  }
  
  private static boolean initDownloaderThreadIfNeeded()
  {
    try
    {
      Object localObject1 = sHandlerThread;
      if (localObject1 == null) {
        try
        {
          sHandlerThread = MiniQBHandlerThread.getInstance();
          sApkDownloader = new MiniQBApkDownloader(sAppContext);
          localObject1 = sHandlerThread.getLooper();
          if (localObject1 == null) {
            return false;
          }
          sDownloaderHandler = new Handler(sHandlerThread.getLooper())
          {
            public void handleMessage(Message paramAnonymousMessage)
            {
              FileOutputStream localFileOutputStream = MiniQBDownloader.getLockFos(MiniQBDownloader.sAppContext);
              if (localFileOutputStream == null) {
                return;
              }
              FileLock localFileLock = MiniQBDownloader.tryFileLock(MiniQBDownloader.sAppContext, localFileOutputStream);
              if (localFileLock == null) {
                return;
              }
              switch (paramAnonymousMessage.what)
              {
              default: 
                break;
              case 102: 
                MiniQBDownloader.sApkDownloader.removeApkIfNeeded(((Integer)paramAnonymousMessage.obj).intValue());
                break;
              case 101: 
                paramAnonymousMessage = MiniQBDownloadConfig.getInstance(MiniQBDownloader.sAppContext);
                if ((MiniQBDownloader.access$300()) && (paramAnonymousMessage.isNeedDownload()))
                {
                  LogManager.logL1(9005, null, new Object[0]);
                  MiniQBDownloader.sApkDownloader.startDownload();
                }
                MiniQBDownloader.access$500();
              }
              MiniQBDownloader.freeFileLock(localFileLock, localFileOutputStream);
            }
          };
        }
        catch (Throwable localThrowable)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("initDownloaderThreadIfNeeded init has Exception:");
          localStringBuilder.append(localThrowable.toString());
          Log.e("MiniQBUpgrade", localStringBuilder.toString());
          return false;
        }
      }
      return true;
    }
    finally {}
  }
  
  private static boolean needStartDownload()
  {
    MiniQBDownloadConfig localMiniQBDownloadConfig = MiniQBDownloadConfig.getInstance(sAppContext);
    if (localMiniQBDownloadConfig.getDownloadSuccessRetryTimes() >= localMiniQBDownloadConfig.getDownloadSuccessMaxRetryTimes()) {
      return false;
    }
    if (localMiniQBDownloadConfig.getDownloadFailedRetryTimes() >= localMiniQBDownloadConfig.getDownloadFailedMaxRetryTimes()) {
      return false;
    }
    if (!sApkDownloader.hasEnoughFreeSpace()) {
      return false;
    }
    if (System.currentTimeMillis() - localMiniQBDownloadConfig.getDownloadStartTime() <= 86400000L)
    {
      long l = localMiniQBDownloadConfig.getDownloadFlow();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[MiniQBDownloader.needStartDownload] downloadFlow=");
      localStringBuilder.append(l);
      localStringBuilder.toString();
      if (l >= localMiniQBDownloadConfig.getDownloadMaxFlow()) {
        return false;
      }
    }
    return true;
  }
  
  private static String notNullString(String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    return str;
  }
  
  private static JSONObject postJsonData()
  {
    Object localObject = MiniQBDownloadConfig.getInstance(sAppContext);
    String str = getDefaultUserAgent(sAppContext);
    JSONObject localJSONObject = new JSONObject();
    int j = 1;
    try
    {
      localJSONObject.put("PROTOCOLVERSION", 1);
      k = MiniQBUpgradeManager.getInstance().getMiniQBVersion();
      if (k != 0) {
        break label418;
      }
      i = 0;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        int k;
        JSONArray localJSONArray;
        int[] arrayOfInt;
        int m;
        int n;
        continue;
        int i = 1;
        continue;
        i += 1;
      }
    }
    localJSONObject.put("FUNCTION", i);
    localJSONObject.put("APPN", sAppContext.getPackageName());
    localJSONObject.put("APPVN", notNullString(((MiniQBDownloadConfig)localObject).getAppVersionName()));
    localJSONObject.put("APPVC", ((MiniQBDownloadConfig)localObject).getAppVersionCode());
    localJSONObject.put("APPMETA", notNullString(((MiniQBDownloadConfig)localObject).getAppMetaData()));
    localJSONObject.put("TBSSDKV", MiniQBUpgradeManager.getInstance().getTbsSdkVersion());
    localJSONObject.put("TBSV", MiniQBUpgradeManager.getInstance().getTbsCoreVersion());
    localJSONObject.put("MINIQBV", k);
    if (k != 0) {
      localJSONObject.put("SRCBACKUPV", 0);
    }
    if (MiniQBShareManager.mAllowThirdAppUpgradeMiniqb) {
      localJSONObject.put("THIRDREQ", 1);
    }
    if (MiniQBUpgradeManager.getInstance().isThirdPartyApp())
    {
      localJSONArray = new JSONArray();
      arrayOfInt = MiniQBUpgradeManager.getInstance().getMiniQBVersionArray();
      m = arrayOfInt.length;
      i = 0;
      if (i < m)
      {
        n = arrayOfInt[i];
        if (n != 0) {
          localJSONArray.put(n);
        }
      }
      else
      {
        localJSONObject.put("MINIQBVARR", localJSONArray);
      }
    }
    else
    {
      localJSONObject.put("UA", str);
      localJSONObject.put("CPU", notNullString(((MiniQBDownloadConfig)localObject).getDeviceCpu()));
      localJSONObject.put("IMSI", notNullString(MiniQBUpgradeManager.getInstance().getImsi()));
      localJSONObject.put("IMEI", notNullString(MiniQBUpgradeManager.getInstance().getImei()));
      localJSONObject.put("ANDROID_ID", notNullString(MiniQBUpgradeManager.getInstance().getAndroidID()));
      i = j;
      if (MiniQBDownloadConfig.getInstance(sAppContext).isMiniQBDisabled(k)) {
        i = 0;
      }
      localJSONObject.put("STATUS", i);
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("[MiniQBDownloader.postJsonData] jsonData=");
      ((StringBuilder)localObject).append(localJSONObject.toString());
      ((StringBuilder)localObject).toString();
      return localJSONObject;
    }
  }
  
  private static void quitLooperSafely()
  {
    try
    {
      Object localObject1 = sHandlerThread;
      if (localObject1 != null) {
        try
        {
          localObject1 = sHandlerThread.getLooper();
          if ((localObject1 != null) && (Build.VERSION.SDK_INT != 18))
          {
            ((Looper)localObject1).quit();
            sHandlerThread = null;
          }
        }
        catch (Exception localException)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("quitLooperSafely exception:");
          localStringBuilder.append(localException.toString());
          localStringBuilder.toString();
        }
      }
      return;
    }
    finally {}
  }
  
  @TargetApi(11)
  private static boolean readResponse(String paramString, int paramInt)
    throws Exception
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[MiniQBDownloader.readResponse] response=");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    paramString = new JSONObject(paramString);
    if (paramString.getInt("RET") != 0) {
      return false;
    }
    localObject = MiniQBDownloadConfig.getInstance(sAppContext);
    boolean bool;
    if (paramString.getInt("USE") == 0) {
      bool = true;
    } else {
      bool = false;
    }
    if (bool) {
      Log.w("MiniQBUpgrade", "[MiniQBDownloader.readResponse] miniqb has been disabled by server !!");
    }
    ((MiniQBDownloadConfig)localObject).setMiniQBDisabled(paramInt, bool);
    if (MiniQBUpgradeManager.getInstance().isThirdPartyApp())
    {
      i = paramString.getInt("MINIQBSV");
      if (!MiniQBShareManager.mAllowThirdAppUpgradeMiniqb)
      {
        ((MiniQBDownloadConfig)localObject).setNeedDownload(false);
        MiniQBUpgradeManager.getInstance().updateShareCoreVersion(i, 0);
        if (i >= paramInt) {
          ((MiniQBDownloadConfig)localObject).setMiniQBDisabled(i, false);
        }
        ((MiniQBDownloadConfig)localObject).commit();
        return false;
      }
      MiniQBUpgradeManager.getInstance().updateShareCoreVersion(i, MiniQBUpgradeManager.getInstance().getTbsCoreVersion());
      if (i >= paramInt) {
        ((MiniQBDownloadConfig)localObject).setMiniQBDisabled(i, false);
      }
      ((MiniQBDownloadConfig)localObject).commit();
    }
    int i = paramString.getInt("RESPONSECODE");
    if (i != 0)
    {
      String str1 = paramString.getString("DOWNLOADURL");
      String str2 = paramString.getString("MD5");
      int j = paramString.getInt("MINIQBSV");
      long l = paramString.getLong("MINIQBFILESIZE");
      if (paramString.getInt("FLOWCTR") == 1) {
        bool = true;
      } else {
        bool = false;
      }
      int k = paramString.getInt("DOWNLOADMAXFLOW");
      int m = paramString.getInt("DOWNLOAD_MIN_FREE_SPACE");
      int n = paramString.getInt("DOWNLOAD_SUCCESS_MAX_RETRYTIMES");
      int i1 = paramString.getInt("DOWNLOAD_FAILED_MAX_RETRYTIMES");
      if ((paramInt < j) && (!TextUtils.isEmpty(str1)))
      {
        if (!str1.equals(((MiniQBDownloadConfig)localObject).getMiniQBDownloadUrl()))
        {
          sApkDownloader.clearCache();
          ((MiniQBDownloadConfig)localObject).setDownloadFailedRetryTimes(0);
          ((MiniQBDownloadConfig)localObject).setDownloadSuccessRetryTimes(0);
        }
        ((MiniQBDownloadConfig)localObject).setmMiniQBResponseCode(i);
        ((MiniQBDownloadConfig)localObject).setMiniQBDownloadUrl(str1);
        ((MiniQBDownloadConfig)localObject).setMd5(str2);
        ((MiniQBDownloadConfig)localObject).setMiniQBDownloadVersion(j);
        ((MiniQBDownloadConfig)localObject).setMiniQBFileSize(l);
        ((MiniQBDownloadConfig)localObject).setFlowControl(bool);
        ((MiniQBDownloadConfig)localObject).setDownloadMaxFlow(k);
        ((MiniQBDownloadConfig)localObject).setMinFreeSpace(m);
        ((MiniQBDownloadConfig)localObject).setDownloadSuccessMaxRetryTimes(n);
        ((MiniQBDownloadConfig)localObject).setDownloadFailedMaxRetryTimes(i1);
        if (MiniQBInstaller.getInstance().installLocalCore(sAppContext, j)) {
          ((MiniQBDownloadConfig)localObject).setNeedDownload(false);
        } else {
          ((MiniQBDownloadConfig)localObject).setNeedDownload(true);
        }
        ((MiniQBDownloadConfig)localObject).commit();
        return true;
      }
      ((MiniQBDownloadConfig)localObject).setNeedDownload(false);
      ((MiniQBDownloadConfig)localObject).commit();
      return false;
    }
    ((MiniQBDownloadConfig)localObject).setNeedDownload(false);
    ((MiniQBDownloadConfig)localObject).commit();
    return false;
  }
  
  private static boolean sendRequest()
  {
    boolean bool1 = MiniQBInstaller.getInstance().isMiniQBLocalInstalled(sAppContext);
    boolean bool3 = false;
    if (bool1) {
      return false;
    }
    Object localObject1 = new File(FileUtil.getTBSSdcardFilePath(sAppContext, 3), "miniqb.tbs.org");
    Object localObject2 = new File(FileUtil.getTBSSdcardFilePath(sAppContext, 4), "miniqb.tbs.org");
    if ((!((File)localObject2).exists()) && (((File)localObject1).exists())) {
      ((File)localObject1).renameTo((File)localObject2);
    }
    long l = System.currentTimeMillis();
    MiniQBDownloadConfig.getInstance(sAppContext).setLastCheck(l);
    MiniQBDownloadConfig.getInstance(sAppContext).setAppVersionName(AppUtil.getAppVersionName(sAppContext));
    MiniQBDownloadConfig.getInstance(sAppContext).setAppVersionCode(AppUtil.getAppVersionCode(sAppContext));
    MiniQBDownloadConfig.getInstance(sAppContext).setAppMetaData(AppUtil.getAppMetadata(sAppContext, "com.tencent.mm.BuildInfo.CLIENT_VERSION"));
    MiniQBDownloadConfig.getInstance(sAppContext).commit();
    if (MiniQBDownloadConfig.getInstance(sAppContext).getDeviceCpu() == null) {
      MiniQBDownloadConfig.getInstance(sAppContext).setDeviceCpu(MiniQBUpgradeManager.getInstance().getCpuInfo());
    }
    localObject2 = postJsonData();
    try
    {
      i = ((JSONObject)localObject2).getInt("MINIQBV");
    }
    catch (Exception localException)
    {
      for (;;)
      {
        int i;
        boolean bool2;
        continue;
        String str = "https://cfg.imtt.qq.com/miniqb?v=2&mk=";
      }
    }
    i = -1;
    if (i != -1) {
      try
      {
        if (!MiniQBUpgradeManager.getInstance().isTestMiniQBEnv()) {
          break label369;
        }
        localObject1 = "http://cfg.sparta.html5.qq.com/miniqb?v=2&mk=";
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("[MiniQBDownloader.sendRequest] postUrl=");
        localStringBuilder.append((String)localObject1);
        localStringBuilder.toString();
        localObject2 = ((JSONObject)localObject2).toString();
        LogManager.logL1(9002, (String)localObject1, new Object[0]);
        LogManager.logL1(9003, (String)localObject2, new Object[0]);
        localObject1 = HttpUtil.postData((String)localObject1, ((String)localObject2).getBytes("utf-8"), new HttpUtil.HttpResponseListener()
        {
          public void onHttpResponseCode(int paramAnonymousInt)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("[MiniQBDownloader.sendRequest] httpResponseCode=");
            localStringBuilder.append(paramAnonymousInt);
            localStringBuilder.toString();
          }
        }, false);
        bool1 = readResponse((String)localObject1, i);
        try
        {
          LogManager.logL1(9004, (String)localObject1, new Object[0]);
        }
        catch (Throwable localThrowable1) {}
        localThrowable2.printStackTrace();
      }
      catch (Throwable localThrowable2)
      {
        bool1 = false;
      }
    } else {
      bool1 = false;
    }
    bool2 = bool3;
    if (bool1)
    {
      bool2 = bool3;
      if (needStartDownload()) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public static void startDownload(Context paramContext)
  {
    for (;;)
    {
      try
      {
        sAppContext = paramContext.getApplicationContext();
        MiniQBDownloadConfig localMiniQBDownloadConfig = MiniQBDownloadConfig.getInstance(sAppContext);
        boolean bool1 = initDownloaderThreadIfNeeded();
        if (!bool1) {
          return;
        }
        Object localObject = localMiniQBDownloadConfig.getAppVersionName();
        i = localMiniQBDownloadConfig.getAppVersionCode();
        String str1 = localMiniQBDownloadConfig.getAppMetaData();
        String str2 = AppUtil.getAppVersionName(sAppContext);
        int j = AppUtil.getAppVersionCode(sAppContext);
        String str3 = AppUtil.getAppMetadata(sAppContext, "com.tencent.mm.BuildInfo.CLIENT_VERSION");
        long l1 = System.currentTimeMillis();
        long l2 = localMiniQBDownloadConfig.getLastCheck();
        boolean bool2 = false;
        if (l1 - l2 > 86400000L)
        {
          i = 1;
        }
        else
        {
          if ((str2 == null) || (j == 0) || (str3 == null)) {
            break label339;
          }
          if ((!str2.equals(localObject)) || (j != i)) {
            break label334;
          }
          if (str3.equals(str1)) {
            break label339;
          }
          break label334;
        }
        j = MiniQBUpgradeManager.getInstance().getMiniQBVersion();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("[MiniQBDownloader.startDownload] localVersion=");
        ((StringBuilder)localObject).append(j);
        ((StringBuilder)localObject).toString();
        sDownloaderHandler.removeMessages(102);
        Message.obtain(sDownloaderHandler, 102, Integer.valueOf(j)).sendToTarget();
        bool1 = bool2;
        if (localMiniQBDownloadConfig.isNeedDownload())
        {
          bool1 = bool2;
          if (Apn.getApnType(paramContext) == 3) {
            bool1 = true;
          }
        }
        paramContext = new StringBuilder();
        paramContext.append("[MiniQBDownloader.startDownload] needDownload=");
        paramContext.append(bool1);
        paramContext.toString();
        if ((i == 0) && (!bool1))
        {
          quitLooperSafely();
        }
        else
        {
          sDownloaderHandler.removeMessages(101);
          Message.obtain(sDownloaderHandler, 101).sendToTarget();
        }
        paramContext = new StringBuilder();
        paramContext.append("[MiniQBDownloader.startDownload] needDownload=");
        paramContext.append(bool1);
        paramContext.toString();
        return;
      }
      finally {}
      label334:
      int i = 1;
      continue;
      label339:
      i = 0;
    }
  }
  
  public static void stopDownload()
  {
    Object localObject = sApkDownloader;
    if (localObject != null) {
      ((MiniQBApkDownloader)localObject).stopDownload();
    }
    localObject = sDownloaderHandler;
    if (localObject != null)
    {
      ((Handler)localObject).removeMessages(101);
      quitLooperSafely();
    }
  }
  
  private static FileLock tryFileLock(Context paramContext, FileOutputStream paramFileOutputStream)
  {
    if (paramFileOutputStream == null) {
      return null;
    }
    try
    {
      paramContext = paramFileOutputStream.getChannel().tryLock();
      boolean bool = paramContext.isValid();
      if (bool) {
        return paramContext;
      }
    }
    catch (Throwable paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQBDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */