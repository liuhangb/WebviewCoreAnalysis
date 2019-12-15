package com.tencent.tbs.common.download.qb;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.utils.FileUtils;
import com.tencent.tbs.common.baseinfo.ICoreInfoFetcher;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.stat.TBSStatManager;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.json.JSONArray;
import org.json.JSONObject;

public class TBSDownloaderHelper
{
  private static final String CONFIG_KEY_CURRENT_TBSVERSION = "current_tbsversion";
  private static final String CONFIG_KEY_ENABLE = "predownload_enable";
  private static final String CONFIG_KEY_HAS_LOCAL_PACKAGE = "has_local_package";
  static final String CONFIG_PATCH = "tbspatch_config";
  static final String CONFIG_PATCH_KEY = "tbspatch_key_config";
  public static final long DEFAULT_RETRY_INTERVAL_SEC = 86400L;
  public static final long DOWNLOAD_PERIOD = 86400000L;
  private static final int FUNCTION_DOWNLOAD = 0;
  private static final int FUNCTION_QUERY = 2;
  private static final int FUNCTION_UPDATE = 1;
  private static final int KEY_BYTE_LENGTH = 16;
  public static final String KEY_DOWNLOAD_TYPE = "download_type";
  public static final String KEY_STA_DOWNLOAD_KEY_OVER = "BZJM003";
  public static final String KEY_STA_DOWNLOAD_OVER = "BZJM002";
  public static final String KEY_STA_DOWNLOAD_PROCESSBLOCK_SPLICE = "BZJMEX001";
  public static final String KEY_STA_DOWNLOAD_PROCESSBLOCK＿PKG = "BZJMEX002";
  public static final String KEY_STA_DOWNLOAD_START = "BZJM001";
  public static final String KEY_STA_FILE_NOTEXIST = "BZJM009";
  public static final String KEY_STA_INSTALLL_START = "BZJM004";
  public static final String KEY_STA_INSTALLL_SUCCESS = "BZJM005";
  public static final String KEY_STA_NORMAL_DOWNLOAD_START = "BZJM103";
  public static final String KEY_STA_NORMAL_INSTALL_OVER = "BZJM105";
  public static final String KEY_STA_NORMAL_INSTALL_START = "BZJM104";
  public static final String KEY_STA_OVERRIDED = "BZJM008";
  public static final String KEY_STA_PKG_CHECK = "BZJMPKGCHECK";
  public static final String KEY_STA_PKG_CHECK_INSTALL = "BZJMPKGCHECKINSTALL";
  public static final String KEY_STA_SILENT_PACKAGE_DISABLED = "BZJM007";
  public static final String KEY_STA_SILENT_PACKAGE_OUTTIME = "BZJM006";
  public static final String KEY_STA_STATUS_JM_DECODE = "BZJMSTADECODE";
  public static final String KEY_STA_STATUS_JM_KEY = "BZJMSTAKEY";
  public static final String KEY_STA_STATUS_JM_PACKAGE = "BZJMSTA";
  public static final String KEY_STA_VERIFY_ERROR_APK = "BZJM011";
  public static final String KEY_STA_VERIFY_ERROR_INSTALL = "BZJM010";
  public static final String KEY_URL_PARAMS_PATCH_KEY = "patchk";
  public static final String KEY_URL_PARAMS_PATCH_LEN = "patchlen";
  private static final String LOGTAG = "TBSDownloaderHelper";
  private static final int MAX_INTERVAL = 604800;
  private static final String PKG_QB_CODED = "com.tencent.qbtbs.package";
  private static final String PKG_QB_KEY = "com.tencent.qbtbs.key";
  private static final String PRE_DOWNLOAD_CONFIG_FILENAME = "tbs_predownload_config";
  private static final int PROTOCOL_VERSION = 1;
  private static String QB_APK_FILENAME = "tmp.apk";
  private static final String SILENT_DOWNLOAD_FILENAME = "sfile.tbs";
  private static final String SILENT_DOWNLOAD_FILENAME_KEY = "sfilekey.tbs";
  private static final String SILENT_DOWNLOAD_TEMP_FILENAME = "sfile.tbs.temp";
  private static final String SILENT_DOWNLOAD_TEMP_FILENAME_KEY = "sfilekey.tbs.temp";
  public static final String TAG = "TBSDownloaderHelper";
  static final String TBS_METADATA = "com.tencent.mm.BuildInfo.CLIENT_VERSION";
  public static final int TYPE_PATCH_PACKAGE = 1;
  public static final int TYPE_PATCH_PACKAGE_KEY = 2;
  public static final int UPLOAD_TYPE_BEFORE_DOWNLOAD = 1;
  public static final int UPLOAD_YUPE_BEFORE_INSTALL = 2;
  private static TBSDownloaderHelper mInstance;
  private static String sDefalutUserAgent;
  static TbsDownloadHelperConfig silentFileConfig;
  static FileDownloader silentFileDownload;
  static TbsDownloadHelperConfig silentKeyConfig;
  static FileDownloader silentKeyDownload;
  public final int[] TIME_FLOW_CONTROL_ARRAY = { 19, 23 };
  Context mAppContext;
  private File mFileDir = null;
  ArrayList<QBDownloadListener> mListenerList = new ArrayList();
  private String mPatchK = null;
  String mRedirecturls = "";
  
  private TBSDownloaderHelper(Context paramContext)
  {
    init(paramContext);
    syncConfigFile(false);
  }
  
  /* Error */
  public static void closeQuietly(java.io.Closeable paramCloseable)
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: aload_0
    //   4: ifnonnull +7 -> 11
    //   7: ldc 2
    //   9: monitorexit
    //   10: return
    //   11: aload_0
    //   12: invokeinterface 220 1 0
    //   17: goto +12 -> 29
    //   20: astore_0
    //   21: goto +12 -> 33
    //   24: astore_0
    //   25: aload_0
    //   26: invokevirtual 223	java/lang/Exception:printStackTrace	()V
    //   29: ldc 2
    //   31: monitorexit
    //   32: return
    //   33: ldc 2
    //   35: monitorexit
    //   36: aload_0
    //   37: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	38	0	paramCloseable	java.io.Closeable
    // Exception table:
    //   from	to	target	type
    //   11	17	20	finally
    //   25	29	20	finally
    //   11	17	24	java/lang/Exception
  }
  
  private int decodePackage(Context paramContext, File paramFile)
  {
    if (paramFile == null) {
      return 201;
    }
    File localFile = new File(getSilentPackageDir(), "sfile.tbs");
    if (!localFile.exists()) {
      return 202;
    }
    if (!new File(getPrivatePackageDir(paramContext), "sfilekey.tbs").exists()) {
      return 203;
    }
    if (!AESHelper.decryptFile(this.mPatchK, localFile.getAbsolutePath(), paramFile.getAbsolutePath())) {
      return 205;
    }
    return 200;
  }
  
  private void enablePreDownload(Context paramContext, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("enablePreDownload=");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.toString();
    paramContext = paramContext.getSharedPreferences("tbs_predownload_config", 0).edit();
    paramContext.putBoolean("predownload_enable", paramBoolean);
    paramContext.commit();
  }
  
  private boolean generateQbPackage(Context paramContext, File paramFile)
  {
    int i = decodePackage(paramContext, paramFile);
    upLoadToBeacon("BZJMSTADECODE", i);
    paramContext = new StringBuilder();
    paramContext.append("decodePackage:");
    paramContext.append(i);
    paramContext.toString();
    return i == 200;
  }
  
  public static String getBundleMsg(Bundle paramBundle)
  {
    if (paramBundle == null) {
      return "null";
    }
    return paramBundle.toString();
  }
  
  private String getDefaultUserAgent(Context paramContext)
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
    paramContext = Build.ID.replaceAll("[一-龥]", "");
    if (paramContext.length() > 0)
    {
      localStringBuffer.append(" Build/");
      localStringBuffer.append(paramContext);
    }
    paramContext = String.format("Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 Mobile Safari/533.1", new Object[] { localStringBuffer });
    sDefalutUserAgent = paramContext;
    return paramContext;
  }
  
  public static TBSDownloaderHelper getInstance(Context paramContext)
  {
    if (paramContext == null) {
      return null;
    }
    try
    {
      if (mInstance == null) {
        mInstance = new TBSDownloaderHelper(paramContext);
      }
      paramContext = mInstance;
      return paramContext;
    }
    finally {}
  }
  
  private static String getKeyFromUrl(String paramString1, String paramString2)
  {
    return Uri.parse(paramString1).getQueryParameter(paramString2);
  }
  
  private File getPrivatePackageDir(Context paramContext)
  {
    if (paramContext == null) {
      return null;
    }
    return paramContext.getDir(".patch", 0);
  }
  
  private File getSilentPackageDir()
  {
    File localFile = this.mFileDir;
    if (localFile != null) {
      return localFile;
    }
    localFile = Environment.getExternalStorageDirectory();
    if (localFile == null) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(localFile.getAbsolutePath());
    localStringBuilder.append(File.separator);
    localStringBuilder.append("tencent");
    localStringBuilder.append(File.separator);
    localStringBuilder.append("tbs");
    localStringBuilder.append(File.separator);
    localStringBuilder.append(".patch");
    localStringBuilder.append(File.separator);
    this.mFileDir = new File(localStringBuilder.toString());
    localFile = this.mFileDir;
    if ((localFile != null) && (!localFile.exists())) {
      this.mFileDir.mkdirs();
    }
    return this.mFileDir;
  }
  
  public static boolean isTimeBetween(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= 0) && (paramInt1 <= 24) && (paramInt2 >= 0))
    {
      if (paramInt2 > 24) {
        return false;
      }
      int i = Calendar.getInstance().get(11);
      return (i >= paramInt1) && (i <= paramInt2);
    }
    return false;
  }
  
  private boolean needSendQueryRequest(TbsDownloadHelperConfig paramTbsDownloadHelperConfig)
  {
    label637:
    label643:
    for (;;)
    {
      try
      {
        String str1 = paramTbsDownloadHelperConfig.mPreferences.getString("app_versionname", null);
        int i = paramTbsDownloadHelperConfig.mPreferences.getInt("app_versioncode", 0);
        String str2 = paramTbsDownloadHelperConfig.mPreferences.getString("app_metadata", null);
        String str3 = AppUtil.getAppVersionName(this.mAppContext);
        int j = AppUtil.getAppVersionCode(this.mAppContext);
        String str4 = AppUtil.getAppMetadata(this.mAppContext, "com.tencent.mm.BuildInfo.CLIENT_VERSION");
        long l2 = System.currentTimeMillis();
        long l3 = paramTbsDownloadHelperConfig.mPreferences.getLong("last_check", 0L);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("[TbsDownloader.needSendQueryRequest] timeLastCheck=");
        localStringBuilder.append(l3);
        localStringBuilder.append(" timeNow=");
        localStringBuilder.append(l2);
        localStringBuilder.toString();
        bool = paramTbsDownloadHelperConfig.mPreferences.contains("last_check");
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("[TbsDownloader.needSendQueryRequest] hasLaskCheckKey=");
        localStringBuilder.append(bool);
        localStringBuilder.toString();
        long l1 = l3;
        if (bool)
        {
          l1 = l3;
          if (l3 == 0L) {
            l1 = l2;
          }
        }
        l3 = paramTbsDownloadHelperConfig.getRetryInterval();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("retryInterval = ");
        localStringBuilder.append(l3);
        localStringBuilder.append(" s,appVersionName:");
        localStringBuilder.append(str3);
        localStringBuilder.append(",appVersionCode:");
        localStringBuilder.append(j);
        localStringBuilder.append(",appMetadata:");
        localStringBuilder.append(str4);
        localStringBuilder.append(",oldAppVersionName:");
        localStringBuilder.append(str1);
        localStringBuilder.append(",oldAppVersionCode:");
        localStringBuilder.append(i);
        localStringBuilder.append(",oldAppVersionMetadata:");
        localStringBuilder.append(str2);
        localStringBuilder.toString();
        if (paramTbsDownloadHelperConfig.mPreferences.getBoolean("tbs_needdownload", false))
        {
          bool = true;
          if (l2 - paramTbsDownloadHelperConfig.mPreferences.getLong("tbs_downloadstarttime", 0L) <= 86400000L)
          {
            long l4 = paramTbsDownloadHelperConfig.mPreferences.getLong("tbs_downloadflow", 0L);
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("[needSendQueryRequest] downloadFlow=");
            localStringBuilder.append(l4);
            localStringBuilder.toString();
            long l5 = paramTbsDownloadHelperConfig.getDownloadMaxflow();
            if (l4 >= l5) {
              return false;
            }
          }
          if (l2 - l1 > l3 * 1000L)
          {
            bool = true;
          }
          else
          {
            if ((str3 == null) || (j == 0) || (str4 == null)) {
              break label643;
            }
            if ((!str3.equals(str1)) || (j != i)) {
              break label637;
            }
            if (str4.equals(str2)) {
              break label643;
            }
            break label637;
          }
          if (bool)
          {
            l1 = System.currentTimeMillis();
            paramTbsDownloadHelperConfig.mSyncMap.put("last_check", Long.valueOf(l1));
            paramTbsDownloadHelperConfig.mSyncMap.put("app_versionname", AppUtil.getAppVersionName(this.mAppContext));
            paramTbsDownloadHelperConfig.mSyncMap.put("app_versioncode", Integer.valueOf(AppUtil.getAppVersionCode(this.mAppContext)));
            paramTbsDownloadHelperConfig.mSyncMap.put("app_metadata", AppUtil.getAppMetadata(this.mAppContext, "com.tencent.mm.BuildInfo.CLIENT_VERSION"));
            paramTbsDownloadHelperConfig.commit();
          }
          return bool;
        }
      }
      finally {}
      boolean bool = false;
      continue;
      bool = true;
    }
  }
  
  private static String notNullString(String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    return str;
  }
  
  public static FileOutputStream openOutputStream(File paramFile)
    throws IOException
  {
    Object localObject;
    if (paramFile.exists())
    {
      if (!paramFile.isDirectory())
      {
        if (!paramFile.canWrite())
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("File '");
          ((StringBuilder)localObject).append(paramFile);
          ((StringBuilder)localObject).append("' cannot be written to");
          throw new IOException(((StringBuilder)localObject).toString());
        }
      }
      else
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("File '");
        ((StringBuilder)localObject).append(paramFile);
        ((StringBuilder)localObject).append("' exists but is a directory");
        throw new IOException(((StringBuilder)localObject).toString());
      }
    }
    else
    {
      localObject = paramFile.getParentFile();
      if ((localObject != null) && (!((File)localObject).exists()) && (!((File)localObject).mkdirs()))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("File '");
        ((StringBuilder)localObject).append(paramFile);
        ((StringBuilder)localObject).append("' could not be created");
        throw new IOException(((StringBuilder)localObject).toString());
      }
    }
    return new FileOutputStream(paramFile);
  }
  
  private JSONObject postJsonData(boolean paramBoolean, TbsDownloadHelperConfig paramTbsDownloadHelperConfig)
  {
    for (;;)
    {
      String str1;
      Object localObject2;
      int j;
      int i;
      try
      {
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("[QBDownloaderHelper.postJsonData]isQuery: ");
        ((StringBuilder)localObject1).append(paramBoolean);
        ((StringBuilder)localObject1).toString();
        String str2 = getDefaultUserAgent(this.mAppContext);
        String str3 = AppUtil.getImsi(this.mAppContext);
        String str4 = AppUtil.getImei(this.mAppContext);
        String str5 = AppUtil.getAndroidID(this.mAppContext);
        Object localObject3 = "";
        str1 = "";
        localObject4 = TimeZone.getDefault().getID();
        if (localObject4 != null) {
          localObject3 = localObject4;
        }
        try
        {
          TelephonyManager localTelephonyManager = (TelephonyManager)this.mAppContext.getSystemService("phone");
          localObject1 = localObject4;
          if (localTelephonyManager == null) {
            break label509;
          }
          localObject1 = localTelephonyManager.getSimCountryIso();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
          localObject2 = localObject4;
        }
        localObject2 = new JSONObject();
        try
        {
          ((JSONObject)localObject2).put("TIMEZONEID", localObject3);
          ((JSONObject)localObject2).put("COUNTRYISO", localObject4);
          ((JSONObject)localObject2).put("PROTOCOLVERSION", 1);
          boolean bool = isSilentPackageDownloaded(this.mAppContext);
          j = 0;
          if (!bool) {
            break label525;
          }
          i = paramTbsDownloadHelperConfig.getLocalVersion();
          if (!paramBoolean) {
            break label530;
          }
          ((JSONObject)localObject2).put("FUNCTION", 2);
          paramTbsDownloadHelperConfig = "com.tencent.qbtbs.package";
          continue;
          ((JSONObject)localObject2).put("FUNCTION", j);
          paramTbsDownloadHelperConfig = "com.tencent.qbtbs.key";
          ((JSONObject)localObject2).put("TBSV", i);
          ((JSONObject)localObject2).put("APPN", paramTbsDownloadHelperConfig);
          ((JSONObject)localObject2).put("THIRDREQ", 1);
          ((JSONObject)localObject2).put("TBSVLARR", new JSONArray());
          ((JSONObject)localObject2).put("APPVN", "1.0");
          ((JSONObject)localObject2).put("APPVC", 4934);
          ((JSONObject)localObject2).put("TBSSDKV", 43100);
          ((JSONObject)localObject2).put("UA", str2);
          ((JSONObject)localObject2).put("IMSI", notNullString(str3));
          ((JSONObject)localObject2).put("IMEI", notNullString(str4));
          ((JSONObject)localObject2).put("ANDROID_ID", notNullString(str5));
          ((JSONObject)localObject2).put("CPU", "armv8l");
          ((JSONObject)localObject2).put("THIRDREQ", 1);
          ((JSONObject)localObject2).put("APPMETA", "26050a00");
          if (!paramBoolean) {
            ((JSONObject)localObject2).put("DOWNLOAD_FOREGROUND", 1);
          }
        }
        catch (Exception paramTbsDownloadHelperConfig)
        {
          localObject3 = new StringBuilder();
          ((StringBuilder)localObject3).append("QBDownloaderHelper postJsonData exception:");
          ((StringBuilder)localObject3).append(paramTbsDownloadHelperConfig.toString());
          ((StringBuilder)localObject3).toString();
        }
        paramTbsDownloadHelperConfig = new StringBuilder();
        paramTbsDownloadHelperConfig.append("[QBDownloaderHelper.postJsonData] jsonData=");
        paramTbsDownloadHelperConfig.append(((JSONObject)localObject2).toString());
        paramTbsDownloadHelperConfig.toString();
        return (JSONObject)localObject2;
      }
      finally {}
      label509:
      Object localObject4 = str1;
      if (localObject2 != null)
      {
        localObject4 = localObject2;
        continue;
        label525:
        i = 0;
        continue;
        label530:
        if (i != 0) {
          j = 1;
        }
      }
    }
  }
  
  private String processResponse(String paramString, TbsDownloadHelperConfig paramTbsDownloadHelperConfig, FileDownloader paramFileDownloader)
    throws Exception
  {
    for (;;)
    {
      long l1;
      int i1;
      try
      {
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("[processResponse] response=");
        ((StringBuilder)localObject2).append(paramString);
        ((StringBuilder)localObject2).toString();
        boolean bool = TextUtils.isEmpty(paramString);
        if (bool) {
          return null;
        }
        paramString = new JSONObject(paramString);
        int i = paramString.getInt("RET");
        if (i != 0) {
          return null;
        }
        i = paramString.getInt("RESPONSECODE");
        if (i == 0)
        {
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_responsecode", Integer.valueOf(i));
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_needdownload", Boolean.valueOf(false));
          paramTbsDownloadHelperConfig.commit();
          return null;
        }
        String str1 = paramString.getString("PKGMD5");
        localObject2 = paramString.getString("DOWNLOADURL");
        String str2 = paramString.optString("URLLIST", "");
        int i2 = paramString.getInt("TBSAPKSERVERVERSION");
        int j = paramString.getInt("DOWNLOADMAXFLOW");
        int k = paramString.getInt("DOWNLOAD_MIN_FREE_SPACE");
        int m = paramString.getInt("DOWNLOAD_SUCCESS_MAX_RETRYTIMES");
        int n = paramString.getInt("DOWNLOAD_FAILED_MAX_RETRYTIMES");
        long l4 = paramString.getLong("DOWNLOAD_SINGLE_TIMEOUT");
        long l5 = paramString.getLong("TBSAPKFILESIZE");
        l1 = paramString.optLong("RETRY_INTERVAL", 0L);
        i1 = paramString.optInt("FLOWCTR", -1);
        paramString = new Bundle();
        paramString.putLong(FileDownloader.KEY_VF_LEN, l5);
        paramString.putString(FileDownloader.KEY_VF_MD5, str1);
        paramFileDownloader.setVerifyInfo(paramString);
        bool = TextUtils.isEmpty((CharSequence)localObject2);
        if (bool) {
          return null;
        }
        int i3 = paramTbsDownloadHelperConfig.mPreferences.getInt("tbs_download_version", 0);
        if (i3 > i2)
        {
          paramString = new StringBuilder();
          paramString.append("the version can not fallback:");
          paramString.append(i3);
          paramString.append("->");
          paramString.append(i2);
          paramString.toString();
          return null;
        }
        paramTbsDownloadHelperConfig.mSyncMap.put("tbs_download_version", Integer.valueOf(i2));
        paramString = new StringBuilder();
        paramString.append("put KEY_TBS_DOWNLOAD_V is ");
        paramString.append(i2);
        paramString.toString();
        i2 = ((String)localObject2).lastIndexOf("?");
        paramString = (String)localObject2;
        if (i2 >= 0)
        {
          try
          {
            paramString = getKeyFromUrl((String)localObject2, "patchlen");
            if (paramString != null)
            {
              i3 = Integer.parseInt(paramString);
              paramTbsDownloadHelperConfig.mSyncMap.put("patch_filesize", Integer.valueOf(i3));
            }
            this.mPatchK = getKeyFromUrl((String)localObject2, "patchk");
            paramString = new StringBuilder();
            paramString.append("mPatchK is :");
            paramString.append(this.mPatchK);
            paramString.toString();
            paramString = ((String)localObject2).substring(0, i2);
          }
          catch (Throwable paramString)
          {
            paramFileDownloader = new StringBuilder();
            paramFileDownloader.append("parse downloadurl error:");
            paramFileDownloader.append(paramString.toString());
            Log.e("TBSDownloaderHelper", paramFileDownloader.toString());
            paramString = (String)localObject2;
          }
          Object localObject1;
          paramTbsDownloadHelperConfig.mSyncMap.put("retry_interval", Long.valueOf(localObject1));
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_downloadurl", paramString);
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_downloadurl_list", str2);
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_responsecode", Integer.valueOf(i));
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_download_maxflow", Integer.valueOf(j));
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_download_min_free_space", Integer.valueOf(k));
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_download_success_max_retrytimes", Integer.valueOf(m));
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_download_failed_max_retrytimes", Integer.valueOf(n));
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_single_timeout", Long.valueOf(l4));
          paramTbsDownloadHelperConfig.mSyncMap.put("tbs_apkfilesize", Long.valueOf(l5));
          paramTbsDownloadHelperConfig.commit();
          if (str1 != null) {
            paramTbsDownloadHelperConfig.mSyncMap.put("tbs_apk_md5", str1);
          }
          paramTbsDownloadHelperConfig.commit();
          return paramString;
        }
      }
      finally {}
      long l3 = 86400L;
      long l2 = l3;
      if (i1 == 1)
      {
        if (l1 > 604800L) {
          l1 = 604800L;
        }
        l2 = l3;
        if (l1 > 0L) {
          l2 = l1;
        }
      }
    }
  }
  
  private String queryDownloadInfo(boolean paramBoolean, TbsDownloadHelperConfig paramTbsDownloadHelperConfig, FileDownloader paramFileDownloader)
  {
    Object localObject1 = null;
    if (paramBoolean) {}
    try
    {
      boolean bool = needSendQueryRequest(paramTbsDownloadHelperConfig);
      if (!bool) {
        return null;
      }
      Object localObject2 = postJsonData(paramBoolean, paramTbsDownloadHelperConfig);
      if (localObject2 == null) {
        return null;
      }
      localObject2 = ((JSONObject)localObject2).toString();
      String str = TbsCommonConfig.getInstance(this.mAppContext).getTbsDownloaderPostUrl();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("request url:");
      localStringBuilder.append(str);
      localStringBuilder.toString();
      try
      {
        localObject2 = HttpUtil.postData(str, ((String)localObject2).getBytes("utf-8"), new HttpUtil.HttpResponseListener()
        {
          public void onHttpResponseCode(int paramAnonymousInt)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("[TBSDownloaderHelper.queryDownloadUrl] httpResponseCode=");
            localStringBuilder.append(paramAnonymousInt);
            localStringBuilder.toString();
          }
        }, false);
        localObject1 = localObject2;
        paramTbsDownloadHelperConfig = processResponse((String)localObject2, paramTbsDownloadHelperConfig, paramFileDownloader);
      }
      catch (Exception paramTbsDownloadHelperConfig)
      {
        paramFileDownloader = new StringBuilder();
        paramFileDownloader.append("queryDownloadUrl,processResponse exception:");
        paramFileDownloader.append(paramTbsDownloadHelperConfig.toString());
        paramFileDownloader.toString();
        paramTbsDownloadHelperConfig = (TbsDownloadHelperConfig)localObject1;
      }
      paramFileDownloader = new StringBuilder();
      paramFileDownloader.append("queryDownloadInfo responseJsonStr:");
      paramFileDownloader.append(paramTbsDownloadHelperConfig);
      paramFileDownloader.toString();
      return paramTbsDownloadHelperConfig;
    }
    finally {}
  }
  
  private void syncConfigFile(boolean paramBoolean)
  {
    try
    {
      Context localContext = TbsBaseModuleShell.getCallerContext();
      Object localObject2 = TbsBaseModuleShell.getCoreInfoFetcher();
      Object localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("syncConfigFile forceSync=");
      ((StringBuilder)localObject3).append(paramBoolean);
      ((StringBuilder)localObject3).append(" context=");
      ((StringBuilder)localObject3).append(localContext);
      ((StringBuilder)localObject3).toString();
      if ((localContext != null) && (localObject2 != null))
      {
        localObject2 = ((ICoreInfoFetcher)localObject2).getCoreVersion();
        localObject3 = localContext.getSharedPreferences("tbs_predownload_config", 0);
        String str = ((SharedPreferences)localObject3).getString("current_tbsversion", "");
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("syncConfigFile configCurrentTbsVersion=");
        localStringBuilder.append(str);
        localStringBuilder.append(" context=");
        localStringBuilder.append(localContext);
        localStringBuilder.toString();
        if ((!str.equals(localObject2)) || (paramBoolean))
        {
          localObject3 = ((SharedPreferences)localObject3).edit();
          ((SharedPreferences.Editor)localObject3).putString("current_tbsversion", (String)localObject2);
          ((SharedPreferences.Editor)localObject3).putBoolean("has_local_package", isSilentPackageDownloaded(localContext));
          ((SharedPreferences.Editor)localObject3).commit();
        }
      }
      enablePreDownload(localContext, true);
      return;
    }
    finally {}
  }
  
  public boolean addDownloadListener(QBDownloadListener paramQBDownloadListener)
  {
    ArrayList localArrayList = this.mListenerList;
    if ((localArrayList != null) && (paramQBDownloadListener != null)) {
      return localArrayList.add(paramQBDownloadListener);
    }
    return false;
  }
  
  public void checkupgradeOrDownload(QBDownloadListener paramQBDownloadListener, Bundle paramBundle, boolean paramBoolean)
  {
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("checkupgradeOrDownload,checkupgradeOrDownload:isCheckUpgrade:");
      ((StringBuilder)localObject).append(paramBoolean);
      ((StringBuilder)localObject).append(",bundle:");
      ((StringBuilder)localObject).append(getBundleMsg(paramBundle));
      ((StringBuilder)localObject).toString();
      if (silentFileDownload != null)
      {
        bool = silentFileDownload.isDownloading();
        if (bool) {
          return;
        }
      }
      boolean bool = isSilentDownloadPermited(this.mAppContext);
      if (!bool) {
        return;
      }
      localObject = queryDownloadInfo(true, silentFileConfig, silentFileDownload);
      bool = TextUtils.isEmpty((CharSequence)localObject);
      if (bool) {
        return;
      }
      TBSStatManager.getInstance().userBehaviorStatistics("BZJM001");
      silentFileDownload.setExtraParams(true, paramQBDownloadListener, paramBundle, null);
      silentFileConfig.mSyncMap.put("tbs_needdownload", Boolean.valueOf(true));
      silentFileConfig.commit();
      int i = silentFileDownload.startDownload((String)localObject, paramBoolean);
      paramQBDownloadListener = new StringBuilder();
      paramQBDownloadListener.append("checkupgradeOrDownload,downloadsuccess:");
      paramQBDownloadListener.append(i);
      paramQBDownloadListener.toString();
      upLoadToBeacon("BZJMSTA", i);
      if (i == 100)
      {
        syncConfigFile(true);
        silentFileConfig.mSyncMap.put("tbs_needdownload", Boolean.valueOf(false));
        silentFileConfig.mSyncMap.put("tbs_local_version", Integer.valueOf(silentFileConfig.getDownloadVersion()));
        silentFileConfig.commit();
      }
      return;
    }
    finally {}
  }
  
  public void deleteCachedFiles(boolean paramBoolean)
  {
    if (this.mAppContext == null) {
      return;
    }
    if (!paramBoolean)
    {
      File localFile1 = getSilentPackageDir();
      if ((localFile1 != null) && (localFile1.exists())) {
        try
        {
          FileUtils.delete(localFile1);
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("deleteCachedFiles failed : ");
          localStringBuilder.append(localIOException.toString());
          localStringBuilder.toString();
        }
      }
    }
    File localFile2 = getApkFile(this.mAppContext);
    if ((localFile2 != null) && (localFile2.exists())) {
      localFile2.delete();
    }
  }
  
  public File getApkFile(Context paramContext)
  {
    return QBDownloadManager.getApkFile();
  }
  
  public int getDownloadProgress(boolean paramBoolean)
  {
    FileDownloader localFileDownloader;
    if (paramBoolean)
    {
      localFileDownloader = silentFileDownload;
      if (localFileDownloader != null) {
        return localFileDownloader.currentDownloadProgress();
      }
    }
    if (!paramBoolean)
    {
      localFileDownloader = silentKeyDownload;
      if (localFileDownloader != null) {
        return localFileDownloader.currentDownloadProgress();
      }
    }
    return 0;
  }
  
  /* Error */
  public long getLatestPackageSize(String paramString)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: ldc -68
    //   5: putfield 190	com/tencent/tbs/common/download/qb/TBSDownloaderHelper:mRedirecturls	Ljava/lang/String;
    //   8: iconst_0
    //   9: istore 5
    //   11: iconst_0
    //   12: istore_2
    //   13: new 254	java/lang/StringBuilder
    //   16: dup
    //   17: invokespecial 255	java/lang/StringBuilder:<init>	()V
    //   20: astore 8
    //   22: aload 8
    //   24: ldc_w 973
    //   27: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   30: pop
    //   31: aload 8
    //   33: aload_1
    //   34: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   37: pop
    //   38: aload 8
    //   40: ldc_w 975
    //   43: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   46: pop
    //   47: aload 8
    //   49: iload 5
    //   51: invokevirtual 299	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   54: pop
    //   55: aload 8
    //   57: ldc_w 977
    //   60: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   63: pop
    //   64: aload 8
    //   66: iload_2
    //   67: invokevirtual 299	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   70: pop
    //   71: aload 8
    //   73: invokevirtual 267	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   76: pop
    //   77: new 254	java/lang/StringBuilder
    //   80: dup
    //   81: invokespecial 255	java/lang/StringBuilder:<init>	()V
    //   84: astore 8
    //   86: aload 8
    //   88: aload_0
    //   89: getfield 190	com/tencent/tbs/common/download/qb/TBSDownloaderHelper:mRedirecturls	Ljava/lang/String;
    //   92: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: pop
    //   96: aload 8
    //   98: aload_1
    //   99: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: aload 8
    //   105: ldc_w 979
    //   108: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   111: pop
    //   112: aload_0
    //   113: aload 8
    //   115: invokevirtual 267	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   118: putfield 190	com/tencent/tbs/common/download/qb/TBSDownloaderHelper:mRedirecturls	Ljava/lang/String;
    //   121: new 981	java/net/URL
    //   124: dup
    //   125: aload_1
    //   126: invokespecial 982	java/net/URL:<init>	(Ljava/lang/String;)V
    //   129: invokevirtual 986	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   132: checkcast 988	java/net/HttpURLConnection
    //   135: astore 9
    //   137: aload 9
    //   139: astore 10
    //   141: aload 9
    //   143: ldc_w 990
    //   146: ldc_w 992
    //   149: invokevirtual 995	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   152: aload 9
    //   154: astore 10
    //   156: aload 9
    //   158: ldc_w 997
    //   161: invokevirtual 1000	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   164: aload 9
    //   166: astore 10
    //   168: aload 9
    //   170: iconst_0
    //   171: invokevirtual 1003	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   174: aload 9
    //   176: astore 10
    //   178: aload 9
    //   180: sipush 20000
    //   183: invokevirtual 1007	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   186: aload 9
    //   188: astore 10
    //   190: aload 9
    //   192: sipush 30000
    //   195: invokevirtual 1010	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   198: aload 9
    //   200: astore 10
    //   202: aload 9
    //   204: invokevirtual 1013	java/net/HttpURLConnection:getResponseCode	()I
    //   207: istore 4
    //   209: aload 9
    //   211: astore 10
    //   213: aload 9
    //   215: invokevirtual 1016	java/net/HttpURLConnection:getContentLength	()I
    //   218: istore_3
    //   219: aload 9
    //   221: astore 10
    //   223: new 254	java/lang/StringBuilder
    //   226: dup
    //   227: invokespecial 255	java/lang/StringBuilder:<init>	()V
    //   230: astore 8
    //   232: aload 9
    //   234: astore 10
    //   236: aload 8
    //   238: ldc_w 1018
    //   241: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   244: pop
    //   245: aload 9
    //   247: astore 10
    //   249: aload 8
    //   251: iload 5
    //   253: invokevirtual 299	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   256: pop
    //   257: aload 9
    //   259: astore 10
    //   261: aload 8
    //   263: ldc_w 1020
    //   266: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   269: pop
    //   270: aload 9
    //   272: astore 10
    //   274: aload 8
    //   276: iload 4
    //   278: invokevirtual 299	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   281: pop
    //   282: aload 9
    //   284: astore 10
    //   286: aload 8
    //   288: ldc_w 1022
    //   291: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   294: pop
    //   295: aload 9
    //   297: astore 10
    //   299: aload 8
    //   301: iload_3
    //   302: invokevirtual 299	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   305: pop
    //   306: aload 9
    //   308: astore 10
    //   310: aload 8
    //   312: invokevirtual 267	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   315: pop
    //   316: iload 4
    //   318: sipush 200
    //   321: if_icmpne +22 -> 343
    //   324: iload_3
    //   325: i2l
    //   326: lstore 6
    //   328: aload 9
    //   330: ifnull +8 -> 338
    //   333: aload 9
    //   335: invokevirtual 1025	java/net/HttpURLConnection:disconnect	()V
    //   338: aload_0
    //   339: monitorexit
    //   340: lload 6
    //   342: lreturn
    //   343: iload_2
    //   344: istore_3
    //   345: aload_1
    //   346: astore 8
    //   348: iload 4
    //   350: sipush 302
    //   353: if_icmpne +21 -> 374
    //   356: aload 9
    //   358: astore 10
    //   360: aload 9
    //   362: ldc_w 1027
    //   365: invokevirtual 1030	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   368: astore 8
    //   370: iload_2
    //   371: iconst_1
    //   372: iadd
    //   373: istore_3
    //   374: iload_3
    //   375: istore 4
    //   377: aload 8
    //   379: astore 10
    //   381: aload 9
    //   383: ifnull +121 -> 504
    //   386: aload 8
    //   388: astore_1
    //   389: aload 9
    //   391: astore 8
    //   393: iload_3
    //   394: istore_2
    //   395: aload 8
    //   397: invokevirtual 1025	java/net/HttpURLConnection:disconnect	()V
    //   400: iload_2
    //   401: istore 4
    //   403: aload_1
    //   404: astore 10
    //   406: goto +98 -> 504
    //   409: astore 11
    //   411: aload 9
    //   413: astore 8
    //   415: goto +15 -> 430
    //   418: astore_1
    //   419: aconst_null
    //   420: astore 10
    //   422: goto +115 -> 537
    //   425: astore 11
    //   427: aconst_null
    //   428: astore 8
    //   430: aload 8
    //   432: astore 10
    //   434: new 254	java/lang/StringBuilder
    //   437: dup
    //   438: invokespecial 255	java/lang/StringBuilder:<init>	()V
    //   441: astore 9
    //   443: aload 8
    //   445: astore 10
    //   447: aload 9
    //   449: ldc_w 1032
    //   452: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   455: pop
    //   456: aload 8
    //   458: astore 10
    //   460: aload 9
    //   462: aload 11
    //   464: invokevirtual 811	java/lang/Throwable:toString	()Ljava/lang/String;
    //   467: invokevirtual 261	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   470: pop
    //   471: aload 8
    //   473: astore 10
    //   475: aload 9
    //   477: invokevirtual 267	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   480: pop
    //   481: aload 8
    //   483: astore 10
    //   485: aload 11
    //   487: invokevirtual 1033	java/lang/Throwable:printStackTrace	()V
    //   490: iload_2
    //   491: istore 4
    //   493: aload_1
    //   494: astore 10
    //   496: aload 8
    //   498: ifnull +6 -> 504
    //   501: goto -106 -> 395
    //   504: iload 5
    //   506: iconst_1
    //   507: iadd
    //   508: istore 5
    //   510: iload 5
    //   512: iconst_5
    //   513: if_icmplt +7 -> 520
    //   516: aload_0
    //   517: monitorexit
    //   518: lconst_0
    //   519: lreturn
    //   520: iload 4
    //   522: istore_2
    //   523: aload 10
    //   525: astore_1
    //   526: iload 4
    //   528: iconst_5
    //   529: if_icmple -516 -> 13
    //   532: aload_0
    //   533: monitorexit
    //   534: lconst_0
    //   535: lreturn
    //   536: astore_1
    //   537: aload 10
    //   539: ifnull +8 -> 547
    //   542: aload 10
    //   544: invokevirtual 1025	java/net/HttpURLConnection:disconnect	()V
    //   547: aload_1
    //   548: athrow
    //   549: astore_1
    //   550: aload_0
    //   551: monitorexit
    //   552: aload_1
    //   553: athrow
    //   554: astore_1
    //   555: goto -217 -> 338
    //   558: astore 8
    //   560: iload_2
    //   561: istore 4
    //   563: aload_1
    //   564: astore 10
    //   566: goto -62 -> 504
    //   569: astore 8
    //   571: goto -24 -> 547
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	574	0	this	TBSDownloaderHelper
    //   0	574	1	paramString	String
    //   12	549	2	i	int
    //   218	176	3	j	int
    //   207	355	4	k	int
    //   9	505	5	m	int
    //   326	15	6	l	long
    //   20	477	8	localObject1	Object
    //   558	1	8	localException1	Exception
    //   569	1	8	localException2	Exception
    //   135	341	9	localObject2	Object
    //   139	426	10	localObject3	Object
    //   409	1	11	localThrowable1	Throwable
    //   425	61	11	localThrowable2	Throwable
    // Exception table:
    //   from	to	target	type
    //   141	152	409	java/lang/Throwable
    //   156	164	409	java/lang/Throwable
    //   168	174	409	java/lang/Throwable
    //   178	186	409	java/lang/Throwable
    //   190	198	409	java/lang/Throwable
    //   202	209	409	java/lang/Throwable
    //   213	219	409	java/lang/Throwable
    //   223	232	409	java/lang/Throwable
    //   236	245	409	java/lang/Throwable
    //   249	257	409	java/lang/Throwable
    //   261	270	409	java/lang/Throwable
    //   274	282	409	java/lang/Throwable
    //   286	295	409	java/lang/Throwable
    //   299	306	409	java/lang/Throwable
    //   310	316	409	java/lang/Throwable
    //   360	370	409	java/lang/Throwable
    //   77	137	418	finally
    //   77	137	425	java/lang/Throwable
    //   141	152	536	finally
    //   156	164	536	finally
    //   168	174	536	finally
    //   178	186	536	finally
    //   190	198	536	finally
    //   202	209	536	finally
    //   213	219	536	finally
    //   223	232	536	finally
    //   236	245	536	finally
    //   249	257	536	finally
    //   261	270	536	finally
    //   274	282	536	finally
    //   286	295	536	finally
    //   299	306	536	finally
    //   310	316	536	finally
    //   360	370	536	finally
    //   434	443	536	finally
    //   447	456	536	finally
    //   460	471	536	finally
    //   475	481	536	finally
    //   485	490	536	finally
    //   2	8	549	finally
    //   13	77	549	finally
    //   333	338	549	finally
    //   395	400	549	finally
    //   542	547	549	finally
    //   547	549	549	finally
    //   333	338	554	java/lang/Exception
    //   395	400	558	java/lang/Exception
    //   542	547	569	java/lang/Exception
  }
  
  void init(Context paramContext)
  {
    this.mAppContext = paramContext.getApplicationContext();
    if (silentFileConfig == null) {
      silentFileConfig = new TbsDownloadHelperConfig(this.mAppContext, "tbspatch_config");
    }
    if (silentKeyConfig == null) {
      silentKeyConfig = new TbsDownloadHelperConfig(this.mAppContext, "tbspatch_key_config");
    }
    if (silentFileDownload == null) {
      silentFileDownload = new FileDownloader(this.mAppContext, getSilentPackageDir(), "sfile.tbs.temp", "sfile.tbs", silentFileConfig, true);
    }
    if (silentKeyDownload == null) {
      silentKeyDownload = new FileDownloader(this.mAppContext, getPrivatePackageDir(paramContext), "sfilekey.tbs.temp", "sfilekey.tbs", silentKeyConfig, false);
    }
  }
  
  public boolean isDownloading(boolean paramBoolean)
  {
    FileDownloader localFileDownloader;
    if (paramBoolean)
    {
      localFileDownloader = silentFileDownload;
      if (localFileDownloader != null) {
        return localFileDownloader.isDownloading();
      }
    }
    if (!paramBoolean)
    {
      localFileDownloader = silentKeyDownload;
      if (localFileDownloader != null) {
        return localFileDownloader.isDownloading();
      }
    }
    return false;
  }
  
  public boolean isPackageOutTime(String paramString, int paramInt1, int paramInt2)
  {
    try
    {
      long l = getLatestPackageSize(paramString);
      if (paramInt1 == 1) {
        paramInt2 = silentFileConfig.getPatchFileSize();
      }
      paramString = new StringBuilder();
      paramString.append("isPackageOutTime, getLatestPackageSize:");
      paramString.append(paramInt2);
      paramString.append(",");
      paramString.append(l);
      paramString.append(",type:");
      paramString.append(paramInt1);
      paramString.append(",urls:");
      paramString.append(this.mRedirecturls);
      paramString.toString();
      if (paramInt2 == l)
      {
        if (paramInt2 == 0) {
          TBSStatManager.getInstance().userBehaviorStatistics("DWALLVERSION0");
        }
        return false;
      }
      TBSStatManager.getInstance().userBehaviorStatistics("DWSERVERSIONVERSIONERROR");
      paramString = new HashMap();
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramInt2);
      ((StringBuilder)localObject).append(",");
      ((StringBuilder)localObject).append(l);
      localObject = ((StringBuilder)localObject).toString();
      paramString.put("size", localObject);
      paramString.put("type", String.valueOf(paramInt1));
      paramString.put("urls", this.mRedirecturls);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("DM_NO_EQUAL:");
      localStringBuilder.append((String)localObject);
      localStringBuilder.toString();
      X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon("DM_CHECK_OUTTIME_NEW", paramString);
      return true;
    }
    finally {}
  }
  
  public boolean isPreDownloadLocalEnabled(Context paramContext)
  {
    return paramContext.getSharedPreferences("tbs_predownload_config", 0).getBoolean("predownload_enable", true);
  }
  
  boolean isSilentDownloadPermited(Context paramContext)
  {
    int[] arrayOfInt = this.TIME_FLOW_CONTROL_ARRAY;
    if (isTimeBetween(arrayOfInt[0], arrayOfInt[1])) {
      return false;
    }
    boolean bool1 = paramContext.getSharedPreferences("tbs_predownload_config", 0).getBoolean("has_local_package", false);
    boolean bool2 = isSilentPackageDownloaded(paramContext);
    return (!bool1) || (bool2);
  }
  
  public boolean isSilentPackageDownloaded(Context paramContext)
  {
    paramContext = getSilentPackageDir();
    boolean bool = false;
    if (paramContext == null) {
      return false;
    }
    if (new File(paramContext, "sfile.tbs").exists()) {
      bool = true;
    }
    return bool;
  }
  
  public boolean removeDownloadListener(QBDownloadListener paramQBDownloadListener)
  {
    ArrayList localArrayList = this.mListenerList;
    if (localArrayList == null) {
      return false;
    }
    return localArrayList.remove(paramQBDownloadListener);
  }
  
  public void startMockDownload(QBDownloadListener paramQBDownloadListener, Bundle paramBundle, boolean paramBoolean)
  {
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("startMockDownload:isCheckUpgrade:");
      ((StringBuilder)localObject).append(paramBoolean);
      ((StringBuilder)localObject).append(",bundle:");
      ((StringBuilder)localObject).append(getBundleMsg(paramBundle));
      ((StringBuilder)localObject).toString();
      if (silentKeyDownload != null)
      {
        bool = silentKeyDownload.isDownloading();
        if (bool) {
          return;
        }
      }
      silentKeyConfig.clear();
      localObject = queryDownloadInfo(false, silentKeyConfig, silentKeyDownload);
      boolean bool = TextUtils.isEmpty((CharSequence)localObject);
      if (bool) {
        return;
      }
      silentKeyDownload.setExtraParams(paramBoolean, paramQBDownloadListener, paramBundle, new FileDownloader.FileDownloadHook()
      {
        public boolean process()
        {
          long l = System.currentTimeMillis();
          Object localObject = TBSDownloaderHelper.this;
          Context localContext = ((TBSDownloaderHelper)localObject).mAppContext;
          TBSDownloaderHelper localTBSDownloaderHelper = TBSDownloaderHelper.this;
          boolean bool = ((TBSDownloaderHelper)localObject).generateQbPackage(localContext, localTBSDownloaderHelper.getApkFile(localTBSDownloaderHelper.mAppContext));
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("generateQbPackage time used:");
          ((StringBuilder)localObject).append(System.currentTimeMillis() - l);
          ((StringBuilder)localObject).append(",issuccess:");
          ((StringBuilder)localObject).append(bool);
          ((StringBuilder)localObject).toString();
          if (!bool)
          {
            localObject = TBSDownloaderHelper.this;
            ((TBSDownloaderHelper)localObject).enablePreDownload(((TBSDownloaderHelper)localObject).mAppContext, false);
          }
          return bool;
        }
      });
      silentKeyDownload.deleteDownloadedFile();
      int i = silentKeyDownload.startDownload((String)localObject, false);
      upLoadToBeacon("BZJMSTAKEY", i);
      paramQBDownloadListener = new StringBuilder();
      paramQBDownloadListener.append("startMockDownload,downloadsuccess:");
      paramQBDownloadListener.append(i);
      paramQBDownloadListener.toString();
      return;
    }
    finally {}
  }
  
  public void stopDownload(boolean paramBoolean)
  {
    FileDownloader localFileDownloader;
    if (paramBoolean)
    {
      localFileDownloader = silentFileDownload;
      if (localFileDownloader != null) {
        localFileDownloader.stopDownload();
      }
    }
    if (!paramBoolean)
    {
      localFileDownloader = silentKeyDownload;
      if (localFileDownloader != null) {
        localFileDownloader.stopDownload();
      }
    }
  }
  
  public void upLoadToBeacon(String paramString, int paramInt)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("errorcode", String.valueOf(paramInt));
    X5CoreBeaconUploader.getInstance(this.mAppContext).upLoadToBeacon(paramString, localHashMap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\TBSDownloaderHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */