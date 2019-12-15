package com.tencent.tbs.tbsshell.partner.debug;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.tbsshell.common.TbsLog;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONArray;

public class TbsLoadFailuresReport
{
  private static final String FORMAL_TBS_DOWNLOAD_STAT_POST_URL = "https://log.tbs.qq.com/ajax?c=dl&k=";
  private static final String KEY_TBSDOWNLOAD_UPLOAD = "tbs_download_upload";
  protected static final String LOGTAG = "tbsshell_report";
  static final int MAX_CALLSTACK_LENGTH = 1024;
  private static final String TBSAPK_DOWNLOAD_STAT_FILENAME = "tbs_download_stat";
  private static final String TEST_TBS_DOWNLOAD_STAT_POST_URL = "http://tr.cs0309.imtt.qq.com/ajax?c=dl&k=";
  private static TbsLoadFailuresReport mInstance;
  private String mApn;
  private String mCheckErrorDetail;
  private Context mContext;
  private int mCurrentTbsVersion;
  private long mDownConsumeTime;
  private int mDownFinalFlag;
  private String mDownUrl;
  private int mDownloadCancel;
  private long mDownloadSize;
  int mErrorCode;
  private long mEventTime;
  private String mFailDetail;
  private int mHttpCode;
  private int mNetworkChange;
  private int mNetworkType;
  private int mPatchUpdateFlag;
  private long mPkgSize;
  private String mResolveIp;
  private int mUnpkgFlag;
  boolean report_success = false;
  
  private TbsLoadFailuresReport(Context paramContext)
  {
    this.mContext = paramContext.getApplicationContext();
    resetArgs();
  }
  
  private String addData(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt);
    localStringBuilder.append("|");
    return localStringBuilder.toString();
  }
  
  private String addData(long paramLong)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramLong);
    localStringBuilder.append("|");
    return localStringBuilder.toString();
  }
  
  private String addData(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    localStringBuilder.append(str);
    localStringBuilder.append("|");
    return localStringBuilder.toString();
  }
  
  private String formatTime(long paramLong)
  {
    try
    {
      String str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date(paramLong));
      return str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static int getAppVersionCode(Context paramContext)
  {
    try
    {
      String str = paramContext.getPackageName();
      int i = paramContext.getPackageManager().getPackageInfo(str, 0).versionCode;
      return i;
    }
    catch (Exception paramContext) {}
    return 0;
  }
  
  public static TbsLoadFailuresReport getInstance(Context paramContext)
  {
    try
    {
      if (mInstance == null) {
        mInstance = new TbsLoadFailuresReport(paramContext);
      }
      paramContext = mInstance;
      return paramContext;
    }
    finally {}
  }
  
  private String getTbsStatPostUrl()
  {
    return "https://log.tbs.qq.com/ajax?c=dl&k=";
  }
  
  private void resetArgs()
  {
    this.mEventTime = 0L;
    this.mDownUrl = null;
    this.mResolveIp = null;
    this.mHttpCode = 0;
    this.mPatchUpdateFlag = 0;
    this.mDownloadCancel = 0;
    this.mUnpkgFlag = 2;
    this.mApn = "unknown";
    this.mNetworkType = 0;
    this.mDownFinalFlag = 2;
    this.mPkgSize = 0L;
    this.mDownConsumeTime = 0L;
    this.mNetworkChange = 1;
    this.mErrorCode = 0;
    this.mCheckErrorDetail = null;
    this.mFailDetail = null;
    this.mDownloadSize = 0L;
    this.mCurrentTbsVersion = 0;
  }
  
  public int getCurrentTbsVersion()
  {
    return this.mCurrentTbsVersion;
  }
  
  public int getDownFinalFlag()
  {
    return this.mDownFinalFlag;
  }
  
  public boolean reportStatData(JSONArray paramJSONArray)
  {
    this.report_success = false;
    if (Looper.getMainLooper() == Looper.myLooper()) {
      return this.report_success;
    }
    if ((paramJSONArray != null) && (paramJSONArray.length() != 0))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("[reportStatData] jsonArray:");
      localStringBuilder.append(paramJSONArray);
      localStringBuilder.toString();
      try
      {
        paramJSONArray = HttpUtil.postData(getTbsStatPostUrl(), paramJSONArray.toString().getBytes("utf-8"), new HttpUtil.HttpResponseListener()
        {
          public void onHttpResponseCode(int paramAnonymousInt)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("[reportStatData] onHttpResponseCode:");
            localStringBuilder.append(paramAnonymousInt);
            localStringBuilder.toString();
            if (paramAnonymousInt < 300) {
              TbsLoadFailuresReport.this.report_success = true;
            }
          }
        });
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("[reportStatData] response:");
        localStringBuilder.append(paramJSONArray);
        localStringBuilder.toString();
      }
      catch (Throwable paramJSONArray)
      {
        paramJSONArray.printStackTrace();
      }
      return this.report_success;
    }
    return this.report_success;
  }
  
  public void saveUploadData(final EventType paramEventType)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(addData(paramEventType.value));
    localStringBuilder.append(addData(DeviceUtils.getIMEI(this.mContext)));
    localStringBuilder.append(addData("qua2"));
    localStringBuilder.append(addData(getCurrentTbsVersion()));
    paramEventType = Build.MODEL;
    try
    {
      String str = new String(paramEventType.getBytes("UTF-8"), "ISO8859-1");
      paramEventType = str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    localStringBuilder.append(addData(paramEventType));
    localStringBuilder.append(addData(this.mContext.getPackageName()));
    localStringBuilder.append(addData(getAppVersionCode(this.mContext)));
    localStringBuilder.append(addData(formatTime(this.mEventTime)));
    localStringBuilder.append(addData(this.mDownUrl));
    localStringBuilder.append(addData(this.mResolveIp));
    localStringBuilder.append(addData(this.mHttpCode));
    localStringBuilder.append(addData(this.mPatchUpdateFlag));
    localStringBuilder.append(addData(this.mDownloadCancel));
    localStringBuilder.append(addData(this.mUnpkgFlag));
    localStringBuilder.append(addData(this.mApn));
    localStringBuilder.append(addData(this.mNetworkType));
    localStringBuilder.append(addData(this.mDownFinalFlag));
    localStringBuilder.append(addData(this.mDownloadSize));
    localStringBuilder.append(addData(this.mPkgSize));
    localStringBuilder.append(addData(this.mDownConsumeTime));
    localStringBuilder.append(addData(this.mNetworkChange));
    localStringBuilder.append(addData(this.mErrorCode));
    localStringBuilder.append(addData(this.mCheckErrorDetail));
    localStringBuilder.append(addData(this.mFailDetail));
    localStringBuilder.append(addData(getCurrentTbsVersion()));
    localStringBuilder.append(DeviceUtils.getAndroidId());
    paramEventType = new JSONArray();
    paramEventType.put(localStringBuilder.toString());
    resetArgs();
    new Thread(new Runnable()
    {
      public void run()
      {
        boolean bool = TbsLoadFailuresReport.this.reportStatData(paramEventType);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("reportStatData: ");
        localStringBuilder.append(paramEventType);
        localStringBuilder.append("; ret: ");
        localStringBuilder.append(bool);
        TbsLog.i("tbsshell_report", localStringBuilder.toString());
      }
    }).start();
  }
  
  public void setApn(String paramString)
  {
    this.mApn = paramString;
  }
  
  public void setCurrentTbsVersion(int paramInt)
  {
    this.mCurrentTbsVersion = paramInt;
  }
  
  public void setDownConsumeTime(long paramLong)
  {
    this.mDownConsumeTime += paramLong;
  }
  
  public void setDownFinalFlag(int paramInt)
  {
    this.mDownFinalFlag = paramInt;
  }
  
  public void setDownloadCancel(int paramInt)
  {
    this.mDownloadCancel = paramInt;
  }
  
  public void setDownloadSize(long paramLong)
  {
    this.mDownloadSize += paramLong;
  }
  
  public void setDownloadUrl(String paramString)
  {
    if (this.mDownUrl == null)
    {
      this.mDownUrl = paramString;
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mDownUrl);
    localStringBuilder.append(";");
    localStringBuilder.append(paramString);
    this.mDownUrl = localStringBuilder.toString();
  }
  
  public void setErrorCode(int paramInt)
  {
    if ((paramInt != 100) && (paramInt != 110) && (paramInt != 120) && (paramInt != 111) && (paramInt < 400))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setErrorCode -- errorCode:");
      localStringBuilder.append(paramInt);
      localStringBuilder.toString();
    }
    this.mErrorCode = paramInt;
  }
  
  public void setEventTime(long paramLong)
  {
    this.mEventTime = paramLong;
  }
  
  public void setFailDetail(String paramString)
  {
    String str = paramString;
    if (paramString.length() > 1024) {
      str = paramString.substring(0, 1024);
    }
    this.mFailDetail = str;
  }
  
  public void setFailDetail(Throwable paramThrowable)
  {
    if (paramThrowable == null)
    {
      this.mFailDetail = "";
      return;
    }
    String str = Log.getStackTraceString(paramThrowable);
    paramThrowable = str;
    if (str.length() > 1024) {
      paramThrowable = str.substring(0, 1024);
    }
    this.mFailDetail = paramThrowable;
  }
  
  public void setHttpCode(int paramInt)
  {
    this.mHttpCode = paramInt;
  }
  
  public void setLoadErrorCode(int paramInt1, String paramString, int paramInt2)
  {
    setErrorCode(paramInt1);
    setEventTime(System.currentTimeMillis());
    setFailDetail(paramString);
    setCurrentTbsVersion(paramInt2);
    saveUploadData(EventType.TYPE_LOAD);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("setLoadErrorCode(");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append(", ");
    localStringBuilder.append(paramString);
    localStringBuilder.append(", ");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append(")");
    localStringBuilder.toString();
  }
  
  public void setNetworkChange(int paramInt)
  {
    this.mNetworkChange = paramInt;
  }
  
  public void setNetworkType(int paramInt)
  {
    this.mNetworkType = paramInt;
  }
  
  public void setPatchUpdateFlag(int paramInt)
  {
    this.mPatchUpdateFlag = paramInt;
  }
  
  public void setPkgSize(long paramLong)
  {
    this.mPkgSize = paramLong;
  }
  
  public void setResolveIp(String paramString)
  {
    this.mResolveIp = paramString;
  }
  
  public void setUnpkgFlag(int paramInt)
  {
    this.mUnpkgFlag = paramInt;
  }
  
  public static abstract interface ErrorCode
  {
    public static final int APK_INVALID = 204;
    public static final int APK_PATH_ERROR = 202;
    public static final int APK_VERSION_ERROR = 203;
    public static final int COPY_EXCEPTION = 215;
    public static final int COPY_FAIL = 212;
    public static final int COPY_INSTALL_SUCCESS = 220;
    public static final int COPY_SRCDIR_ERROR = 213;
    public static final int COPY_TMPDIR_ERROR = 214;
    public static final int DEXOPT_EXCEPTION = 209;
    public static final String DISABLE_X5 = "forbiden by server add 469936079 report it";
    public static final int DISK_FULL = 105;
    public static final int DOWNLOAD_FILE_CONTENTLENGTH_NOT_MATCH = 113;
    public static final int DOWNLOAD_HAS_LOCAL_TBS_ERROR = 120;
    public static final int DOWNLOAD_INSTALL_SUCCESS = 200;
    public static final int DOWNLOAD_OVER_FLOW = 112;
    public static final int DOWNLOAD_SUCCESS = 100;
    public static final int ERROR_AUTHENTICATION = 317;
    public static final int ERROR_CANLOADVIDEO_RETURN_FALSE = 313;
    public static final int ERROR_CANLOADVIDEO_RETURN_NULL = 314;
    public static final int ERROR_CANLOADX5_RETURN_FALSE = 307;
    public static final int ERROR_CANLOADX5_RETURN_NULL = 308;
    public static final int ERROR_CODE_COMMON_BASE = 0;
    public static final int ERROR_CODE_CORE_LOAD_BASE = 900;
    public static final int ERROR_CODE_DOWNLOAD_BASE = 100;
    public static final int ERROR_CODE_INSTALL_BASE = 200;
    public static final int ERROR_CODE_LOAD_BASE = 300;
    public static final int ERROR_FORCE_SYSTEM_WEBVIEW_INNER_FAILED_TO_CREATE = 301;
    public static final int ERROR_HOST_UNAVAILABLE = 304;
    public static final int ERROR_LESS_THAN_MIN_SUPPORT_VER = 309;
    public static final int ERROR_QBSDK_INIT = 315;
    public static final int ERROR_QBSDK_INIT_CANLOADX5 = 319;
    public static final int ERROR_QBSDK_INIT_END = 316;
    public static final int ERROR_QBSDK_INIT_ISSUPPORT = 318;
    public static final int ERROR_SDKENGINE_CANLOADTBS = 324;
    public static final int ERROR_SDKENGINE_ISCOMPATIBLE = 320;
    public static final int ERROR_TBSCORE_DEXOPT_DIR = 311;
    public static final int ERROR_TBSCORE_SHARE_DIR = 312;
    public static final int ERROR_TBSINSTALLER_ISTBSCORELEGAL_01 = 321;
    public static final int ERROR_TBSINSTALLER_ISTBSCORELEGAL_02 = 322;
    public static final int ERROR_TBSINSTALLER_ISTBSCORELEGAL_03 = 323;
    public static final int ERROR_UNMATCH_TBSCORE_VER = 303;
    public static final int ERROR_UNMATCH_TBSCORE_VER_THIRDPARTY = 302;
    public static final int EXCEED_COPY_RETRY_NUM = 211;
    public static final int EXCEED_DEXOPT_RETRY_NUM = 208;
    public static final int EXCEED_UNZIP_RETRY_NUM = 201;
    public static final int EXCEPTION_QBSDK_INIT = 305;
    public static final int FILE_DELETED = 106;
    public static final int FILE_RENAME_ERROR = 109;
    public static final String FORCE_SYSTEM_WEBVIEW_INNER = "some error happened anlysis the other errcodes ^_^";
    public static final String FORCE_SYSTEM_WEBVIEW_OUTER = "APP lead to force system webview";
    public static final int INCRUPDATE_INSTALL_SUCCESS = 221;
    public static final int INCR_UPDATE_ERROR = 216;
    public static final int INCR_UPDATE_EXCEPTION = 218;
    public static final int INCR_UPDATE_FAIL = 217;
    public static final int INFO_CAN_LOAD_TBS = 406;
    public static final int INFO_CAN_NOT_LOAD_TBS = 405;
    public static final int INFO_CODE_BASE = 400;
    public static final int INFO_DISABLE_X5 = 404;
    public static final String INFO_ERROR_AUTHENTICATION = "Authentication fail, use sys webview, please refer to the document of anthentication strictly";
    public static final String INFO_ERROR_CANLOADX5_RETURN_FALSE = "sdkextension return false maybe your phone TotalRAM is less 170M ";
    public static final String INFO_ERROR_CANLOADX5_RETURN_NULL = "sdkextension return null try to update X5 in wechat/qq/qzone ";
    public static final String INFO_ERROR_FORCE_SYSTEM_WEBVIEW_INNER_FAILED_TO_CREATE = "Fail to create createSDKWebview please restart you app try again";
    public static final String INFO_ERROR_HOST_UNAVAILABLE = "if the errocode is the only first time so ignore it. but if always occur then host app unavalilable try to update the x5 in wechat/qq/qzone (isShareTbsCoreAvailable=false in init())";
    public static final String INFO_ERROR_QBSDK_INIT = "SDK init failed,you need analysis the other errcodes";
    public static final String INFO_ERROR_QBSDK_INIT_CANLOADX5 = "sdkextension return false maybe your phone TotalRAM is less 170M or X5 is closed by server ";
    public static final String INFO_ERROR_SDKENGINE_ISCOMPATIBLE = "min support api 7";
    public static final String INFO_ERROR_TBSCORE_DEXOPT_DIR = "DEXOPT path is null  make sure x5 in wechat/qq/qzone is normal";
    public static final String INFO_EXCEPTION_QBSDK_INIT = "exception in init analysis other errcode or try to restart app again ";
    public static final int INFO_FORCE_SYSTEM_WEBVIEW_INNER = 401;
    public static final int INFO_FORCE_SYSTEM_WEBVIEW_OUTER = 402;
    public static final int INFO_MISS_SDKEXTENSION_JAR = 403;
    public static final String INFO_SDKENGINE_CANLOADTBS = "if the errocode is the only first time so ignore it. take care the other codes beside it. The first time of open appwebview,it is nomal because it takes some time to share X5.if always happen then make sure app current web process restart or look up other errcodes ^_^";
    public static final String INFO_THROWABLE_LOAD_TBS = "throw a exception when load x5,try to restart your app or anlysis other errcodes";
    public static final String METHOD_MISS_SDKEXTENSION_JAR = "miss tbs_sdk_extension_dex.jar make sure X5 is normal in wechat/qq/qzone ";
    public static final int NETWORK_NOT_WIFI_ERROR = 111;
    public static final int NETWORK_UNAVAILABLE = 101;
    public static final int NONEEDTODOWN_ERROR = 110;
    public static final int READ_RESPONSE_ERROR = 103;
    public static final int RENAME_EXCEPTION = 219;
    public static final int ROM_NOT_ENOUGH = 210;
    public static final int SERVER_ERROR = 102;
    public static final int THROWABLE_LOAD_TBS = 310;
    public static final int THROWABLE_QBSDK_INIT = 306;
    public static final int UNKNOWN_ERROR = 107;
    public static final int UNZIP_DIR_ERROR = 205;
    public static final int UNZIP_IO_ERROR = 206;
    public static final int UNZIP_OTHER_ERROR = 207;
    public static final int VERIFY_ERROR = 108;
    public static final int WRITE_DISK_ERROR = 104;
  }
  
  public static enum EventType
  {
    TYPE_DOWNLOAD(0),  TYPE_INSTALL(1),  TYPE_LOAD(2);
    
    int value;
    
    private EventType(int paramInt)
    {
      this.value = paramInt;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\debug\TbsLoadFailuresReport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */