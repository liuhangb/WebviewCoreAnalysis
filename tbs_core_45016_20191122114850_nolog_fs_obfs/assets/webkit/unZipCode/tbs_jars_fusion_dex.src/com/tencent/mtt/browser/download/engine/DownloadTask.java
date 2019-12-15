package com.tencent.mtt.browser.download.engine;

import android.content.ContentValues;
import android.content.Context;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.HandlerThread;
import android.os.Message;
import android.os.StatFs;
import android.text.TextUtils;
import com.tencent.basesupport.ModuleProxy;
import com.tencent.common.dns.DnsProvider;
import com.tencent.common.http.Apn;
import com.tencent.common.http.MttRequestBase;
import com.tencent.common.http.MttResponse;
import com.tencent.common.http.RequesterFactory;
import com.tencent.common.manifest.EventEmiter;
import com.tencent.common.manifest.EventMessage;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.ICostTimeLite;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.downloadprovider.DownloadproviderHelper;
import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.base.task.TaskObserver;
import com.tencent.mtt.base.utils.DLMttFileUtils;
import com.tencent.mtt.base.utils.DLReporter;
import com.tencent.mtt.base.utils.DLReporterManager;
import com.tencent.mtt.browser.download.business.DownloadHijackExcutor;
import com.tencent.tbs.common.download.BaseDownloadManager;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DownloadTask
  extends Task
  implements TaskObserver
{
  public static final Pattern CONTENT_RANGE_PATTERN;
  public static final String DL_BT_FILE_SUFFIX = ".btdltmp";
  public static final String DL_FILE_HIDE = ".";
  public static final String DL_FILE_ICON = ".png.icon";
  public static final String DL_FILE_SUFFIX = ".qbdltmp";
  public static final long EXT_FLAG_ANNOTATIONASREFER = 8192L;
  public static final long EXT_FLAG_FROM_WEB = 512L;
  public static final long EXT_FLAG_HAS_DELAY_NOTIFY_INSTALL = 32768L;
  public static final long EXT_FLAG_HAS_REPOERT = 256L;
  public static final long EXT_FLAG_INSTALL_BY_YYB = 65536L;
  public static final long EXT_FLAG_ISTBS = 16384L;
  public static final long EXT_FLAG_NOTIFIED_INSTALL = 2048L;
  public static final long EXT_FLAG_NO_3G_DIALOG = 4096L;
  public static final long EXT_FLAG_PAUSED_BY_USER = 128L;
  public static final long EXT_FLAG_PRE_DOWNLOAD = 33554432L;
  public static final long EXT_FLAG_PRIVATE_TASK = 2097152L;
  public static final long EXT_FLAG_PRIVATE_TASK_REMOVED = 4194304L;
  public static final long EXT_FLAG_QB_UPDATE = 64L;
  public static final long EXT_FLAG_TASK_AUTO_INSTALL = 4L;
  public static final long EXT_FLAG_TASK_CONTINUE_TASK = 1L;
  public static final long EXT_FLAG_TASK_CREATE_FIRST = 16L;
  public static final long EXT_FLAG_TASK_ISFILESIZE_REAL = 8L;
  public static final long EXT_FLAG_TASK_PLUGIN = 32L;
  public static final long EXT_FLAG_TASK_SHOW_TOAST = 2L;
  public static final long EXT_FLAG_TBS_DOWNLOADINTERCEPT = 131072L;
  public static final long EXT_FLAG_TBS_DOWNLOADTBSAD = 1048576L;
  public static final long EXT_FLAG_TBS_DOWNLOADVIDEO = 262144L;
  public static final long EXT_FLAG_TBS_DOWNLOADYYBCHANNEL = 524288L;
  public static final long EXT_FLAG_TENCENT_DNS = 1024L;
  public static final long EXT_FLAG_THIRD_DOWNLOAD_QQ = 16777216L;
  public static final long EXT_FLAG_THIRD_DOWNLOAD_SOGOU = 8388608L;
  public static final long FILE_SIZE_UNSPECIFIED = -1L;
  public static final int FLAG_APPOINTMENT_DOWNLOAD_TASK = 1073741824;
  public static final int FLAG_BACKGROUDAUTO_TASK = 268435456;
  public static final int FLAG_CHECK_SIGN_AFTER_DOWNLOAD_TASK = 536870912;
  public static final int FLAG_CUMSTOM_LOGO_TASK = 524288;
  public static final int FLAG_DELTA_UPDATE_FILED_TASK = 1024;
  public static final int FLAG_HAS_INSTALLED = 64;
  public static final int FLAG_HAS_NO_ALLOCATION = 8388608;
  public static final int FLAG_HIDE_FILE = 32;
  public static final int FLAG_IMAGE_ENTER_DB_TASK = 4194304;
  public static final int FLAG_IMMDIEATE_RUN_TASK = 4;
  public static final int FLAG_INCRCORE_UPDATE_TASK = 1048576;
  public static final int FLAG_IS_BT_TASK = 33554432;
  public static final int FLAG_IS_FS_TASK = 67108864;
  public static final int FLAG_IS_M3U8 = 512;
  public static final int FLAG_IS_MP4 = 16384;
  public static final int FLAG_IS_POST_TASK = 131072;
  public static final int FLAG_IS_XL_TASK = 16777216;
  public static final int FLAG_NORMAL_FILE = 0;
  public static final int FLAG_OPEN_DIRECTYLY_TASK = 2097152;
  public static final int FLAG_PRE_DOWNLOAD_TASK = 4096;
  public static final int FLAG_PUASED_BY_NO_WIFI = 8192;
  public static final int FLAG_QQBROWSER_DELTA_UPDATE = 2048;
  public static final int FLAG_QQMARKET_DELTA_UPDATE = 256;
  public static final int FLAG_QQMARKET_DELTA_UPDATE_NEW_PRCOTCOL = 32768;
  public static final int FLAG_QQMARKET_TASK_FILE = 16;
  public static final int FLAG_RANGE_NOT_SUPPORT = 128;
  public static final int FLAG_RESUME_TASK_FILE = 2;
  public static final int FLAG_SAFE_APK_TASK = 134217728;
  public static final int FLAG_START_ONWIFI_DOWNLOAD_TASK = Integer.MIN_VALUE;
  public static final int FLAG_UPDATE_FILE = 1;
  public static final int FLAG_USED_SNIFF = 65536;
  public static final int FLAG_WORDERPLAYER_TASK = 262144;
  public static final int INVAILD_TASKID = -1;
  public static final long MAX_SECTION_FILE_SIZE = 52428800L;
  public static int NEW_DOWNLOAD_FILE_MODE_IN_DETERMINE_SEC = 2;
  public static int NEW_DOWNLOAD_FILE_MODE_IN_PROBE_FILELEN = 0;
  public static int NEW_DOWNLOAD_FILE_MODE_IN_WRITE_FILE = 3;
  public static final String TAG = "DownloadTask";
  public static final int TASK_ID_UNSPECIFIED = -1;
  static long jdField_a_of_type_Long;
  static DownloadTaskManager jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
  static long jdField_b_of_type_Long;
  public static String sCookieKey;
  public static ConcurrentHashMap<Integer, Object> sStatusLocks = new ConcurrentHashMap();
  int jdField_a_of_type_Int;
  DownloadSections jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections = new DownloadSections();
  IRefreshFileNameAndSizeForPreDownloadTask jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$IRefreshFileNameAndSizeForPreDownloadTask = null;
  Worker jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker = null;
  RandomAccessFile jdField_a_of_type_JavaIoRandomAccessFile;
  Object jdField_a_of_type_JavaLangObject = new Object();
  String jdField_a_of_type_JavaLangString;
  ArrayList<String> jdField_a_of_type_JavaUtilArrayList = null;
  boolean jdField_a_of_type_Boolean = false;
  int jdField_b_of_type_Int = 0;
  final Object jdField_b_of_type_JavaLangObject = new Object();
  String jdField_b_of_type_JavaLangString;
  boolean jdField_b_of_type_Boolean = false;
  int jdField_c_of_type_Int;
  long jdField_c_of_type_Long;
  String jdField_c_of_type_JavaLangString;
  boolean jdField_c_of_type_Boolean = false;
  int jdField_d_of_type_Int = -1;
  long jdField_d_of_type_Long;
  String jdField_d_of_type_JavaLangString = "";
  boolean jdField_d_of_type_Boolean = true;
  int jdField_e_of_type_Int = -1;
  long jdField_e_of_type_Long;
  String jdField_e_of_type_JavaLangString = "";
  boolean jdField_e_of_type_Boolean = false;
  int jdField_f_of_type_Int = -1;
  long jdField_f_of_type_Long;
  String jdField_f_of_type_JavaLangString;
  boolean jdField_f_of_type_Boolean = false;
  int jdField_g_of_type_Int = -1;
  long jdField_g_of_type_Long = 0L;
  String jdField_g_of_type_JavaLangString;
  boolean jdField_g_of_type_Boolean = false;
  int jdField_h_of_type_Int = 0;
  String jdField_h_of_type_JavaLangString;
  boolean jdField_h_of_type_Boolean = false;
  int jdField_i_of_type_Int = 4;
  String jdField_i_of_type_JavaLangString;
  private boolean jdField_i_of_type_Boolean = false;
  String j;
  String k;
  String l;
  String m;
  public int mClarity = 0;
  public long mConnectCostTime = -1L;
  public long mConnectResourceCostTime = -1L;
  public Context mContext;
  public long mCostTime;
  public long mCreateTime;
  public String mDivideSectionPos = "";
  public long mDownloadCostTime = -1L;
  public int mDownloadErrorCode = 0;
  public DownloadOperation mDownloadOps;
  public String mDownloadUrl;
  public long mDownloadedSize;
  public List<HttpDownloader> mDownloaders;
  public String mErrorDes;
  public String mFileFolderPath;
  public String mFileName = "";
  public long mFileSize;
  public long mFileSizeForHijack;
  public int mFlag;
  public boolean mFlagChanged = false;
  public int mFrom = 0;
  public String mHijackInfo = "";
  public boolean mIsDeleted = false;
  public boolean mIsForground = false;
  public boolean mIsMergeFile = false;
  public boolean mIsSupportResume;
  public int mMergeFailCode = 0;
  public int mPercent = 0;
  public boolean mRangeNotSupported = false;
  public final Object mReadWriteProgressLock = new Object();
  public String mRunPos;
  public long mSaveFlowSize = 0L;
  public String mVersionName;
  public long mWrittenSize;
  String n;
  private String o;
  private String p;
  private String q = "";
  private String r = "";
  
  static
  {
    CONTENT_RANGE_PATTERN = Pattern.compile("[^\\d]*(\\d+)\\-(\\d+)\\/(\\d+|\\*)");
    jdField_a_of_type_Long = 1048576L;
    jdField_b_of_type_Long = 6291456L;
    sCookieKey = "1234567890";
    NEW_DOWNLOAD_FILE_MODE_IN_PROBE_FILELEN = 1;
  }
  
  public DownloadTask(Context paramContext, int paramInt1, byte paramByte, String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2, boolean paramBoolean1, String paramString4, int paramInt2, boolean paramBoolean2, long paramLong3, String paramString5)
  {
    super.setTaskType((byte)3);
    this.mDownloaders = Collections.synchronizedList(new ArrayList());
    setDownloadTaskId(paramInt1);
    super.setStatus(paramByte);
    this.jdField_f_of_type_JavaLangString = paramString5;
    boolean bool;
    if ((0x20000000 & paramInt2) > 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.jdField_e_of_type_Boolean = bool;
    this.mMttRequest = RequesterFactory.getMttRequestBase();
    this.mMttRequest.setRequestType((byte)104);
    if ((!isHidden()) && (!isQQMarketTask())) {
      this.mMttRequest.setRequestInterceptor(DownloaderInterceptor.getInstance());
    }
    this.mDownloadUrl = paramString1;
    this.mMttRequest.setUrl(this.mDownloadUrl);
    this.mContext = paramContext;
    if (TextUtils.isEmpty(paramString3))
    {
      this.mFileFolderPath = DLMttFileUtils.getDownloadFilePath(this.mContext, paramString2);
    }
    else
    {
      this.mFileFolderPath = paramString3;
      this.jdField_a_of_type_Boolean = true;
    }
    this.mDownloadOps = new DownloadOperation();
    this.mFileName = b(paramString2);
    if ((isM3U8()) && (paramString2 != null) && (!paramString2.endsWith(".m3u8")))
    {
      paramContext = new StringBuilder();
      paramContext.append(paramString2);
      paramContext.append(".m3u8");
      this.mFileName = b(paramContext.toString());
    }
    this.mFileSizeForHijack = paramLong2;
    this.mFileSize = paramLong2;
    this.mDownloadedSize = paramLong1;
    this.mWrittenSize = paramLong1;
    this.mCostTime = paramLong3;
    this.mIsSupportResume = paramBoolean1;
    if (paramBoolean2) {
      this.mTaskAttr |= 0x2;
    }
    this.jdField_b_of_type_JavaLangString = paramString4;
    setFlag(paramInt2, false);
    this.mFlagChanged = false;
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.setDownloadTask(this);
  }
  
  public DownloadTask(Context paramContext, int paramInt1, String paramString1, String paramString2, String paramString3, long paramLong1, long paramLong2, boolean paramBoolean1, String paramString4, int paramInt2, boolean paramBoolean2)
  {
    this(paramContext, paramInt1, (byte)0, paramString1, paramString2, paramString3, paramLong1, paramLong2, paramBoolean1, paramString4, paramInt2, paramBoolean2, 0L, null);
  }
  
  public DownloadTask(Context paramContext, String paramString)
  {
    this(paramContext, paramString, null, -1L, null);
  }
  
  public DownloadTask(Context paramContext, String paramString1, String paramString2)
  {
    this(paramContext, -1, (byte)0, paramString1, null, paramString2, 0L, -1L, true, null, 0, false, 0L, null);
  }
  
  public DownloadTask(Context paramContext, String paramString1, String paramString2, long paramLong, String paramString3)
  {
    this(paramContext, -1, paramString1, paramString2, null, 0L, paramLong, true, paramString3, 0, false);
  }
  
  public DownloadTask(Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    this(paramContext, -1, paramString1, paramString2, paramString3, 0L, 0L, true, null, 0, false);
  }
  
  public DownloadTask(Context paramContext, String paramString1, String paramString2, String paramString3, long paramLong, String paramString4)
  {
    this(paramContext, -1, paramString1, paramString2, paramString3, 0L, paramLong, true, paramString4, 0, false);
  }
  
  public DownloadTask(DownloadTask paramDownloadTask)
  {
    super.setTaskType((byte)3);
    setDownloadTaskId(paramDownloadTask.getDownloadTaskId());
    super.setStatus(paramDownloadTask.getStatus());
    this.jdField_b_of_type_Int = paramDownloadTask.getMaxThreadNum();
    this.mDownloadUrl = paramDownloadTask.getTaskUrl();
    this.mFileFolderPath = paramDownloadTask.getFileFolderPath();
    this.mDownloadErrorCode = paramDownloadTask.getErrorCode();
    this.mMergeFailCode = paramDownloadTask.getMergeFailCode();
    this.jdField_d_of_type_Int = paramDownloadTask.getHttpResponseCode();
    this.mFileName = b(paramDownloadTask.getFileName());
    this.mFileSize = paramDownloadTask.getTotalSize();
    this.mCostTime = paramDownloadTask.getCostTime();
    this.mCreateTime = paramDownloadTask.getCreateTime();
    this.mDivideSectionPos = paramDownloadTask.mDivideSectionPos;
    this.jdField_b_of_type_JavaLangString = paramDownloadTask.getReferer();
    this.jdField_h_of_type_JavaLangString = paramDownloadTask.getReportDownloadDataUrl();
    this.mErrorDes = paramDownloadTask.getErrorDesForUpload();
    this.mRunPos = paramDownloadTask.getDownloaderRunPath(true);
    this.jdField_g_of_type_Boolean = paramDownloadTask.hasTmpTryRange();
    setHidden(paramDownloadTask.isHidden(), false);
    setIsBackAutoTask(paramDownloadTask.isBackAutoTask(), false);
    this.mFlag = paramDownloadTask.mFlag;
    this.jdField_d_of_type_JavaLangString = paramDownloadTask.jdField_d_of_type_JavaLangString;
    this.jdField_e_of_type_JavaLangString = paramDownloadTask.jdField_e_of_type_JavaLangString;
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.setDownloadTask(this);
    this.jdField_f_of_type_JavaLangString = paramDownloadTask.getPackageName();
    setDownloadApkType(paramDownloadTask.getDownloadApkType());
    this.l = paramDownloadTask.getChannel();
    this.n = paramDownloadTask.getChannelPkgName();
    this.mDownloadCostTime = paramDownloadTask.mDownloadCostTime;
    this.mConnectCostTime = paramDownloadTask.mConnectCostTime;
    this.mConnectResourceCostTime = paramDownloadTask.mConnectResourceCostTime;
    this.jdField_d_of_type_Long = paramDownloadTask.jdField_d_of_type_Long;
    this.mDownloadedSize = paramDownloadTask.mDownloadedSize;
  }
  
  private String a(String paramString)
  {
    String str = paramString;
    if (!TextUtils.isEmpty(paramString))
    {
      str = paramString;
      if (paramString.length() > 80)
      {
        int i1 = paramString.lastIndexOf('.');
        if (i1 > 0)
        {
          str = paramString.substring(i1 + 1);
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(paramString.substring(0, Math.min(i1, 79)));
          localStringBuilder.append(".");
          localStringBuilder.append(str);
          return localStringBuilder.toString();
        }
        str = paramString.substring(0, 79);
      }
    }
    return str;
  }
  
  private String b(String paramString)
  {
    String str = a(paramString);
    paramString = str;
    if (!TextUtils.isEmpty(str))
    {
      paramString = str;
      if (str.contains("../")) {
        paramString = str.replaceAll("\\.\\./", "");
      }
    }
    return paramString;
  }
  
  public static boolean isBTFlag(int paramInt)
  {
    return (paramInt & 0x2000000) != 0;
  }
  
  public static boolean isM3U8Flag(int paramInt)
  {
    return (paramInt & 0x200) != 0;
  }
  
  public static boolean isMp4Flag(int paramInt)
  {
    return (paramInt & 0x4000) != 0;
  }
  
  public static boolean isXunleiFlag(int paramInt)
  {
    return (paramInt & 0x1000000) != 0;
  }
  
  public boolean StatusIsBackAutoTask()
  {
    return (isHidden()) && (isBackAutoTask());
  }
  
  long a()
  {
    int i1 = this.jdField_b_of_type_Int;
    long l1 = 52428800L / i1;
    if (this.mFileSize > 524288000L) {
      l1 = 104857600L / i1;
    }
    return l1;
  }
  
  StringBuilder a(File paramFile)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("p:");
    localStringBuilder.append(paramFile.getAbsoluteFile());
    localStringBuilder.append(",");
    try
    {
      localStringBuilder.append("ex:");
      localStringBuilder.append(paramFile.exists());
    }
    catch (Exception localException1)
    {
      localStringBuilder.append(localException1.getMessage());
    }
    localStringBuilder.append(",");
    try
    {
      localStringBuilder.append("rw:");
      localStringBuilder.append(paramFile.canWrite());
    }
    catch (Exception localException2)
    {
      localStringBuilder.append(localException2.getMessage());
    }
    localStringBuilder.append(",");
    try
    {
      localStringBuilder.append("r:");
      localStringBuilder.append(paramFile.canRead());
      return localStringBuilder;
    }
    catch (Exception paramFile)
    {
      localStringBuilder.append(paramFile.getMessage());
    }
    return localStringBuilder;
  }
  
  void a()
  {
    f();
    if (isDownloaderFinish())
    {
      if (!isBufferEmpty()) {
        return;
      }
      updateCostTime();
      long l1 = this.mWrittenSize;
      long l2 = this.mFileSize;
      if (l1 != l2) {
        this.mWrittenSize = l2;
      }
      if (getStatus() != 3)
      {
        if (this.mIsMergeFile) {
          return;
        }
        closeSavedFile();
        if (!renameAfterFinish())
        {
          this.mDownloadErrorCode = 9;
          setStatus((byte)5);
          fireObserverEvent(this.mStatus);
          return;
        }
        if (this.mFileSize <= 0L) {
          this.mFileSize = this.mDownloadedSize;
        }
        if ((!isQQMarketDeltaUpdateTask()) && ((getFlag() & 0x800) != 2048))
        {
          setStatus((byte)3);
          fireObserverEvent(this.mStatus);
          return;
        }
        synchronized (this.mObservers)
        {
          Iterator localIterator = this.mObservers.iterator();
          while (localIterator.hasNext()) {
            ((TaskObserver)localIterator.next()).onTaskCompleted(this);
          }
          return;
        }
      }
      return;
    }
  }
  
  void a(byte paramByte)
  {
    setStatus(paramByte);
    fireObserverEvent(this.mStatus);
  }
  
  void a(byte paramByte, boolean paramBoolean)
  {
    Object localObject1 = sStatusLocks.get(Integer.valueOf(this.jdField_a_of_type_Int));
    if (localObject1 != null) {}
    for (;;)
    {
      try
      {
        byte b1;
        if (this.jdField_a_of_type_Int != -1)
        {
          b1 = DownloadproviderHelper.a(this.jdField_a_of_type_Int);
          super.setStatus(b1);
          if (b1 == 6) {
            break label207;
          }
        }
        switch (b1)
        {
        case 11: 
          if (paramByte != 2)
          {
            if (isAppointmentTask()) {
              break label180;
            }
            if (isStartOnWifiTask()) {
              break label180;
            }
          }
          else
          {
            return;
            return;
          }
        case 9: 
          return;
        case 8: 
          return;
          return;
          super.setStatus(paramByte);
          if (paramBoolean) {
            if ((paramByte != 2) && (paramByte != 1))
            {
              DownloadproviderHelper.a(this, true);
              if (paramByte == 0) {
                DownloadEventManager.getInstance().notifyTaskPrepareStart(this);
              }
            }
            else
            {
              DownloadproviderHelper.a(this);
            }
          }
          return;
        }
      }
      finally {}
      return;
      continue;
      label180:
      if (paramByte == 1)
      {
        continue;
        if ((paramByte != 2) && (paramByte != 1)) {
          if (paramByte == 6)
          {
            continue;
            label207:
            if (paramByte != 2) {
              if (paramByte != 1) {}
            }
          }
        }
      }
    }
  }
  
  void a(MttResponse paramMttResponse)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("url:[");
    localStringBuilder.append(this.mDownloadUrl);
    localStringBuilder.append("]\n");
    localStringBuilder.append("refer:[");
    localStringBuilder.append(getReferer());
    localStringBuilder.append("]\n");
    localStringBuilder.append("contentRange:[");
    localStringBuilder.append(paramMttResponse.getContentRange());
    localStringBuilder.append("]\n");
    localStringBuilder.append("isRangeNotSupported:[");
    localStringBuilder.append(isRangeNotSupported());
    localStringBuilder.append("]\n");
    paramMttResponse = paramMttResponse.getRspHeaderMaps();
    if (paramMttResponse != null)
    {
      paramMttResponse = paramMttResponse.entrySet().iterator();
      while (paramMttResponse.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMttResponse.next();
        localStringBuilder.append("header:[");
        localStringBuilder.append((String)localEntry.getKey());
        localStringBuilder.append("], value:[");
        localStringBuilder.append(localEntry.getValue());
        localStringBuilder.append("]\n");
      }
    }
    paramMttResponse = localStringBuilder.toString();
    paramMttResponse = new EventMessage("com.tencent.mtt.external.rqd.RQDManager.handleCatchException", new Object[] { Thread.currentThread(), new MarketTaskNotSupportResumeException("MarketTaskNotSupportResumeException"), paramMttResponse });
    EventEmiter.getDefault().emit(paramMttResponse);
  }
  
  void a(HttpDownloader paramHttpDownloader1, HttpDownloader paramHttpDownloader2)
  {
    f();
    if (paramHttpDownloader1 != null)
    {
      if (paramHttpDownloader2 == null) {
        return;
      }
      if (paramHttpDownloader1.getStatus() == 5) {
        paramHttpDownloader2.getStatus();
      }
      DownloadSection localDownloadSection1 = paramHttpDownloader2.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection;
      DownloadSection localDownloadSection2 = paramHttpDownloader1.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection;
      if (localDownloadSection2 == null) {
        return;
      }
      long l2 = localDownloadSection2.getSectionNextDownloadPos();
      long l1 = localDownloadSection2.getCurrentSectionEndPos();
      paramHttpDownloader1.getRemainingLen();
      System.currentTimeMillis();
      long l3 = paramHttpDownloader1.mStartDownloadTime;
      int i1 = this.jdField_i_of_type_Int;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramHttpDownloader2.jdField_b_of_type_Int);
      localStringBuilder.append("D");
      localStringBuilder.append(paramHttpDownloader1.jdField_b_of_type_Int);
      addDivideRunPos(i1, localStringBuilder.toString());
      l2 = (l2 + l1) / 2L;
      localDownloadSection2.changeCurrentSectionEndPos(l2);
      paramHttpDownloader1 = new StringBuilder();
      paramHttpDownloader1.append(localDownloadSection1.getSectionStartPos());
      paramHttpDownloader1.append(DownloadSection.sDivider);
      paramHttpDownloader1.append(l2 + 1L);
      localDownloadSection1.setSectionStartPos(paramHttpDownloader1.toString());
      paramHttpDownloader1 = new StringBuilder();
      paramHttpDownloader1.append(localDownloadSection1.getSectionEndPos());
      paramHttpDownloader1.append(DownloadSection.sDivider);
      paramHttpDownloader1.append(l1);
      localDownloadSection1.setSectionEndPos(paramHttpDownloader1.toString());
      paramHttpDownloader1 = new StringBuilder();
      paramHttpDownloader1.append(localDownloadSection1.getSectionWriteLen());
      paramHttpDownloader1.append(DownloadSection.sDivider);
      paramHttpDownloader1.append(0);
      localDownloadSection1.setSectionWriteLen(paramHttpDownloader1.toString());
      localDownloadSection1.setSectionDownloadLen(0L);
      localDownloadSection1.mCurrentIndex += 1;
      paramHttpDownloader2.jdField_a_of_type_Boolean = false;
      paramHttpDownloader2.jdField_b_of_type_Boolean = false;
      paramHttpDownloader2.setStatus((byte)1);
      paramHttpDownloader2.start();
      return;
    }
  }
  
  void a(Exception paramException, long paramLong)
  {
    if (getStatus() == 5) {
      return;
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("-D3:");
    ((StringBuilder)localObject1).append(paramException.toString());
    this.mErrorDes = ((StringBuilder)localObject1).toString();
    int i1;
    if ((!TextUtils.isEmpty(paramException.getMessage())) && (paramException.getMessage().toLowerCase().contains("enospc"))) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    if (i1 != 0)
    {
      this.mDownloadErrorCode = 41;
      Object localObject2 = getFileFolderPath();
      paramException = "NULL";
      if (!TextUtils.isEmpty((CharSequence)localObject2))
      {
        localObject1 = localObject2;
        if (new File((String)localObject2).exists()) {
          try
          {
            paramException = new StatFs(getFileFolderPath());
            paramException.restat((String)localObject2);
            paramException = Long.toString(paramException.getBlockSize() * paramException.getAvailableBlocks());
            localObject1 = localObject2;
          }
          catch (IllegalArgumentException paramException)
          {
            paramException = paramException.getMessage();
            localObject1 = localObject2;
          }
        }
      }
      else
      {
        localObject1 = "NULL";
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(this.mErrorDes);
      ((StringBuilder)localObject2).append("(f:");
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(",r:");
      ((StringBuilder)localObject2).append(paramException);
      ((StringBuilder)localObject2).append(",d:");
      ((StringBuilder)localObject2).append(getDownloadedSize());
      ((StringBuilder)localObject2).append(",t:");
      ((StringBuilder)localObject2).append(getTotalSize());
      ((StringBuilder)localObject2).append(",w:");
      ((StringBuilder)localObject2).append(this.mWrittenSize);
      ((StringBuilder)localObject2).append(",s:");
      ((StringBuilder)localObject2).append(this.mFileSize);
      ((StringBuilder)localObject2).append(",l:");
      ((StringBuilder)localObject2).append(paramLong);
      ((StringBuilder)localObject2).append(")");
      this.mErrorDes = ((StringBuilder)localObject2).toString();
      setStatus((byte)11);
      if (!this.jdField_i_of_type_Boolean)
      {
        this.jdField_i_of_type_Boolean = true;
        if (!isHidden()) {
          DownloadEventManager.getInstance().notifyUser("空间不足，暂停下载。", 0);
        }
      }
      return;
    }
    this.mDownloadErrorCode = 20;
    paramException = paramException.getMessage();
    if (!TextUtils.isEmpty(paramException)) {
      if (paramException.toLowerCase().contains("erofs")) {
        this.mDownloadErrorCode = 32;
      } else if (paramException.toLowerCase().contains("ebus")) {
        this.mDownloadErrorCode = 33;
      }
    }
    setStatus((byte)5);
    paramException = this.mDownloaders.iterator();
    while (paramException.hasNext())
    {
      localObject1 = (HttpDownloader)paramException.next();
      if ((localObject1 != null) && (!((HttpDownloader)localObject1).jdField_a_of_type_Boolean)) {
        ((HttpDownloader)localObject1).cancel();
      }
    }
    closeSavedFile();
    fireObserverEvent(this.mStatus);
  }
  
  void a(boolean paramBoolean)
  {
    this.mFlagChanged = true;
    int i1;
    if (paramBoolean) {
      i1 = this.mFlag | 0x800000;
    } else {
      i1 = this.mFlag & 0xFF7FFFFF;
    }
    this.mFlag = i1;
  }
  
  boolean a()
  {
    return (this.mFlag & 0x800000) == 8388608;
  }
  
  boolean a(String arg1, MttResponse paramMttResponse)
  {
    f();
    this.mIsSupportResume = false;
    this.jdField_b_of_type_Boolean = true;
    Object localObject = paramMttResponse.getContentRange();
    boolean bool;
    if (localObject != null)
    {
      localObject = CONTENT_RANGE_PATTERN.matcher((CharSequence)localObject);
      if (((Matcher)localObject).find())
      {
        if (StringUtils.parseLong(((Matcher)localObject).group(1), 0L) == 0L) {
          bool = true;
        } else {
          bool = false;
        }
        this.mIsSupportResume = bool;
      }
    }
    makeSureSupportResume(isRangeNotSupported());
    setETag(paramMttResponse.getETag(), true);
    if ((!this.mIsSupportResume) && (isQQMarketTask())) {
      a(paramMttResponse);
    }
    if (makeSureFileSize(paramMttResponse.getContentLength()))
    {
      b(false);
      return false;
    }
    adjustMaxDownloaderNum(true);
    if ((isRangeNotSupported()) || (TextUtils.isEmpty(this.mFileName)) || (!FileUtilsF.checkFileName(this.mFileName)))
    {
      ??? = UrlUtils.guessFileName(???, paramMttResponse.getContentDisposition(), null);
      if ((isRangeNotSupported()) && (!TextUtils.isEmpty(???)) && (isQQMarketDeltaUpdateTask()))
      {
        if ((TextUtils.isEmpty(this.mFileName)) && (???.endsWith(".patch"))) {
          this.mFileName = b(???.substring(0, ???.length() - 6));
        }
      }
      else {
        this.mFileName = b(???);
      }
      if (!this.jdField_a_of_type_Boolean) {
        this.mFileFolderPath = DLMttFileUtils.getDownloadFilePath(this.mContext, this.mFileName);
      }
      ??? = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$IRefreshFileNameAndSizeForPreDownloadTask;
      if (??? != null)
      {
        ???.refreshFileNameAndSizeForPreDownloadTask(this);
        this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$IRefreshFileNameAndSizeForPreDownloadTask = null;
      }
    }
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      if (this.jdField_d_of_type_Boolean) {
        this.mFileName = b(FileUtilsF.renameFileIfExist(this.mFileFolderPath, this.mFileName));
      }
      bool = newSavedFile(NEW_DOWNLOAD_FILE_MODE_IN_PROBE_FILELEN);
      if (!bool)
      {
        b(false);
        return false;
      }
      if (this.mCanceled)
      {
        taskCanceled();
        b(false);
        return false;
      }
      if ((!this.jdField_d_of_type_Boolean) && (c()) && (isFileExist()))
      {
        b(false);
        restoreDownloaders();
        return false;
      }
      divideDownloadSections(true);
      return true;
    }
  }
  
  public void accumulateSpeedData()
  {
    DownloadproviderHelper.a(this.jdField_a_of_type_Int, this.mDownloadedSize);
  }
  
  public void addDivideRunPos(int paramInt, String paramString)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.mDivideSectionPos);
      localStringBuilder.append(paramInt);
      localStringBuilder.append(":");
      localStringBuilder.append(paramString);
      localStringBuilder.append(";");
      this.mDivideSectionPos = localStringBuilder.toString();
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public boolean addDownloadedSize(int paramInt, long paramLong)
  {
    try
    {
      Worker localWorker = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker;
      if (localWorker == null) {
        return false;
      }
      if (getDownloadTaskId() == paramInt)
      {
        long l1 = this.mDownloadedSize + paramLong;
        paramLong = l1;
        if (l1 > 0L)
        {
          paramLong = l1;
          if (l1 > getTotalSize())
          {
            paramLong = l1;
            if (getTotalSize() > 0L) {
              paramLong = getTotalSize();
            }
          }
        }
        this.mDownloadedSize = paramLong;
      }
      return true;
    }
    finally {}
  }
  
  protected void adjustMaxDownloaderNum(boolean paramBoolean)
  {
    if (this.jdField_b_of_type_Int == 0) {
      if ((this.mIsSupportResume) && (!isRangeNotSupported()))
      {
        long l1 = this.mFileSize;
        long l2 = jdField_a_of_type_Long;
        if (l1 < l2) {
          this.jdField_b_of_type_Int = 1;
        } else if (l1 < l2 * 2L) {
          this.jdField_b_of_type_Int = 2;
        } else {
          this.jdField_b_of_type_Int = 3;
        }
      }
      else
      {
        this.jdField_b_of_type_Int = 1;
      }
    }
    if (!TextUtils.isEmpty(getTaskUrl()))
    {
      if (getTaskUrl().contains("ws.cdn.baidupcs.com")) {
        this.jdField_b_of_type_Int = 1;
      }
      if (getTaskUrl().contains("m.gdown.baidu.com")) {
        this.jdField_b_of_type_Int = 1;
      }
    }
    if ((isRangeNotSupported()) || (!this.mIsSupportResume) || (isBackAutoTask())) {
      this.jdField_b_of_type_Int = 1;
    }
  }
  
  void b()
    throws IOException
  {
    if (!this.mIsSupportResume)
    {
      this.mDownloaders.clear();
      this.mDownloadedSize = 0L;
      this.mWrittenSize = 0L;
      divideDownloadSections(false);
      return;
    }
    restoreConfigData();
    if (isValidSectionData())
    {
      restoreDownloaders();
    }
    else
    {
      setDownloadedSize(0L, false);
      setWrittenSize(0L, false);
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.clear(false);
      DownloadproviderHelper.a(this);
      divideDownloadSections(false);
    }
    long l1 = this.mWrittenSize;
    this.mDownloadedSize = l1;
    this.jdField_e_of_type_Long = l1;
  }
  
  void b(boolean paramBoolean)
  {
    ??? = this.mDownloaders;
    if ((??? != null) && (((List)???).size() > 0))
    {
      HttpDownloader localHttpDownloader = (HttpDownloader)this.mDownloaders.get(0);
      if (localHttpDownloader != null)
      {
        localHttpDownloader.mIsDetectDownloader = false;
        synchronized (localHttpDownloader.mDetectDownloaderLock)
        {
          localHttpDownloader.mNeedGoon = paramBoolean;
          localHttpDownloader.mDetectDownloaderLock.notify();
          return;
        }
      }
    }
  }
  
  boolean b()
  {
    f();
    Iterator localIterator = this.mDownloaders.iterator();
    while (localIterator.hasNext())
    {
      HttpDownloader localHttpDownloader = (HttpDownloader)localIterator.next();
      if ((localHttpDownloader != null) && (localHttpDownloader.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getSectionNextDownloadPos() > localHttpDownloader.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getCurrentSectionEndPos() + 1L)) {
        return true;
      }
    }
    return false;
  }
  
  void c()
  {
    this.mDownloadedSize = 0L;
    this.mWrittenSize = 0L;
    this.mDownloaders.clear();
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.clear(true);
  }
  
  boolean c()
  {
    return this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.hasValidConfigData(this.jdField_b_of_type_Int);
  }
  
  public boolean canRetry()
  {
    ArrayList localArrayList = this.jdField_a_of_type_JavaUtilArrayList;
    if (localArrayList != null)
    {
      if (localArrayList.size() == 0) {
        return false;
      }
      return this.jdField_e_of_type_Int < this.jdField_a_of_type_JavaUtilArrayList.size() - 1;
    }
    return false;
  }
  
  public void cancel()
  {
    cancel(true);
  }
  
  public void cancel(boolean paramBoolean)
  {
    Iterator localIterator;
    if ((this.mDownloadedSize > 0L) && (!isFileExist()))
    {
      localIterator = this.mDownloaders.iterator();
      while (localIterator.hasNext()) {
        try
        {
          localHttpDownloader = (HttpDownloader)localIterator.next();
          if (localHttpDownloader != null) {
            localHttpDownloader.cancel();
          }
        }
        catch (ConcurrentModificationException localConcurrentModificationException1)
        {
          HttpDownloader localHttpDownloader;
          for (;;) {}
        }
      }
      this.mDownloadedSize = 0L;
      this.mWrittenSize = 0L;
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.clear(false);
      taskCanceled(paramBoolean);
      if (getExtFlagShowToast()) {
        DownloadEventManager.getInstance().notifyUser("您暂停的任务，下载的文件被删除。", 0);
      }
      return;
    }
    if (!this.mCanceled)
    {
      if ((getStatus() != 5) && (getStatus() != 1) && (getStatus() != 2) && (getStatus() != 11) && (getStatus() != 8) && (getStatus() != 9) && (getStatus() != 10))
      {
        taskCanceled(paramBoolean);
        return;
      }
      this.mCanceled = true;
      localIterator = this.mDownloaders.iterator();
      while (localIterator.hasNext()) {
        try
        {
          localHttpDownloader = (HttpDownloader)localIterator.next();
          if ((localHttpDownloader != null) && (!localHttpDownloader.jdField_a_of_type_Boolean)) {
            localHttpDownloader.cancel();
          }
        }
        catch (ConcurrentModificationException localConcurrentModificationException2)
        {
          for (;;) {}
        }
      }
      taskCanceled(paramBoolean);
    }
  }
  
  public void closeSavedFile()
  {
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      Object localObject2 = this.jdField_a_of_type_JavaIoRandomAccessFile;
      if (localObject2 != null)
      {
        try
        {
          localObject2 = this.jdField_a_of_type_JavaIoRandomAccessFile.getFD();
          if (localObject2 != null) {
            ((FileDescriptor)localObject2).sync();
          }
          if ((this.mFileSize > 0L) && (this.jdField_a_of_type_JavaIoRandomAccessFile.length() > this.mFileSize)) {
            this.jdField_a_of_type_JavaIoRandomAccessFile.setLength(this.mFileSize);
          }
          this.jdField_a_of_type_JavaIoRandomAccessFile.close();
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
        this.jdField_a_of_type_JavaIoRandomAccessFile = null;
      }
      return;
    }
  }
  
  void d()
  {
    setTencentDNS(true, true);
    try
    {
      String str2 = new URL(this.mDownloadUrl).getHost();
      String str3 = DnsProvider.resolveIPAddress(str2);
      String str1 = this.mDownloadUrl;
      if (!TextUtils.isEmpty(str3)) {
        str1 = this.mDownloadUrl.replace(str2, str3);
      }
      if (this.jdField_a_of_type_JavaUtilArrayList == null)
      {
        this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
        this.jdField_e_of_type_Int += 1;
      }
      this.jdField_a_of_type_JavaUtilArrayList.add(str1);
      this.mWrittenSize = 0L;
      this.mDownloadedSize = 0L;
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.clear(true);
      this.m = str2;
      setStatus((byte)10);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public void deleteCfgFile()
  {
    setCostTime(0L, true);
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.resetSections();
    this.mDownloadedSize = 0L;
    this.mWrittenSize = 0L;
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
    localContentValues.put("downloadedsize", Integer.valueOf(0));
    localContentValues.put("downloadsize", Integer.valueOf(0));
    DownloadSection localDownloadSection;
    if (this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.size() > 0)
    {
      localDownloadSection = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.getSection(0);
      localContentValues.put("startpos1", localDownloadSection.getSectionStartPos());
      localContentValues.put("endpos1", localDownloadSection.getSectionEndPos());
      localContentValues.put("writepos1", localDownloadSection.getSectionWriteLen());
    }
    else
    {
      localContentValues.put("startpos1", Integer.valueOf(0));
      localContentValues.put("endpos1", Integer.valueOf(-1));
      localContentValues.put("writepos1", Integer.valueOf(0));
    }
    if (this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.size() > 1)
    {
      localDownloadSection = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.getSection(1);
      localContentValues.put("startpos2", localDownloadSection.getSectionStartPos());
      localContentValues.put("endpos2", localDownloadSection.getSectionEndPos());
      localContentValues.put("writepos2", localDownloadSection.getSectionWriteLen());
    }
    else
    {
      localContentValues.put("startpos2", Integer.valueOf(0));
      localContentValues.put("endpos2", Integer.valueOf(-1));
      localContentValues.put("writepos2", Integer.valueOf(0));
    }
    if (this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.size() > 2)
    {
      localDownloadSection = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.getSection(2);
      localContentValues.put("startpos3", localDownloadSection.getSectionStartPos());
      localContentValues.put("endpos3", localDownloadSection.getSectionEndPos());
      localContentValues.put("writepos3", localDownloadSection.getSectionWriteLen());
    }
    else
    {
      localContentValues.put("startpos3", Integer.valueOf(0));
      localContentValues.put("endpos3", Integer.valueOf(-1));
      localContentValues.put("writepos3", Integer.valueOf(0));
    }
    DownloadproviderHelper.a(localContentValues);
  }
  
  protected void divideDownloadSections(boolean paramBoolean)
  {
    f();
    long l1 = this.mFileSize;
    int i1 = this.jdField_b_of_type_Int;
    l1 /= i1;
    if (i1 > 1)
    {
      a();
      if ((paramBoolean) && (this.mFileSize > 52428800L))
      {
        l1 = 52428800L / this.jdField_b_of_type_Int;
        a(true);
        i1 = 1;
        break label124;
      }
      if (((!paramBoolean) || (this.mFileSize >= 52428800L)) && (!paramBoolean) && (this.mFileSize > 52428800L))
      {
        l1 = 52428800L / this.jdField_b_of_type_Int;
        a(true);
        i1 = 1;
        break label124;
      }
    }
    i1 = 0;
    label124:
    if (!paramBoolean) {
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.clear(false);
    }
    int i3;
    for (int i2 = 0;; i2 = i3)
    {
      int i4 = this.jdField_b_of_type_Int;
      if (i2 >= i4) {
        break;
      }
      long l4 = i2 * l1;
      i3 = i2 + 1;
      long l3 = i3 * l1 - 1L;
      long l2 = l3;
      if (i1 == 0)
      {
        l2 = l3;
        if (i2 == i4 - 1) {
          l2 = this.mFileSize - 1L;
        }
      }
      DownloadDataBuffer localDownloadDataBuffer = null;
      Object localObject;
      if ((i2 == 0) && (paramBoolean))
      {
        if (this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.size() <= 0) {
          this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.createSection(0, null);
        }
        localObject = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.getSection(0);
        ((DownloadSection)localObject).setSectionStartPos(String.valueOf(l4));
        ((DownloadSection)localObject).setSectionEndPos(String.valueOf(l2));
        b(true);
        addDivideRunPos(this.jdField_i_of_type_Int, "0DF");
      }
      else
      {
        if ((i2 >= 0) && (i2 < this.mDownloaders.size())) {
          localObject = (HttpDownloader)this.mDownloaders.remove(i2);
        } else {
          localObject = null;
        }
        if (localObject != null) {
          localDownloadDataBuffer = ((HttpDownloader)localObject).jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.dataBuffer();
        }
        localObject = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.createSection(i2, localDownloadDataBuffer);
        ((DownloadSection)localObject).setSectionStartPos(String.valueOf(l4));
        ((DownloadSection)localObject).setSectionEndPos(String.valueOf(l2));
        this.mDownloaders.add(i2, new HttpDownloader(this, (DownloadSection)localObject, i2));
        if (this.mCanceled)
        {
          taskCanceled();
          return;
        }
        localObject = (HttpDownloader)this.mDownloaders.get(i2);
        ((HttpDownloader)localObject).jdField_a_of_type_Boolean = false;
        ((HttpDownloader)localObject).mIsDetectDownloader = false;
        ((HttpDownloader)localObject).start();
        i4 = this.jdField_i_of_type_Int;
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(i2);
        ((StringBuilder)localObject).append("D");
        addDivideRunPos(i4, ((StringBuilder)localObject).toString());
      }
    }
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.size();
  }
  
  public void doRun()
  {
    try
    {
      ICostTimeLite localICostTimeLite = (ICostTimeLite)ICostTimeLite.PROXY.get();
      if (localICostTimeLite != null) {
        localICostTimeLite.start("DownloadTask.doRun");
      }
      DLReporter localDLReporter = DLReporterManager.getReporter(getDownloadTaskId());
      if (localDLReporter != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("(C2:");
        localStringBuilder.append(localDLReporter.calDeltTime());
        localStringBuilder.append(")");
        localDLReporter.addStep(localStringBuilder.toString());
      }
      setPausedByUser(false, true);
      setReproted(false, true);
      prepareRun();
      if (isTheDownloadSectionDetermined())
      {
        addDivideRunPos(5, "runR");
        downloadTaskInSectionDeterminedMode();
      }
      else
      {
        addDivideRunPos(5, "runD");
        a(false);
        downloadTaskInProbeFileLenMode();
      }
      if (localICostTimeLite != null) {
        localICostTimeLite.end("DownloadTask.doRun");
      }
      return;
    }
    finally {}
  }
  
  /* Error */
  protected void doScheduleDownloaders(HttpDownloader paramHttpDownloader, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokevirtual 723	com/tencent/mtt/browser/download/engine/DownloadTask:f	()V
    //   6: aload_0
    //   7: getfield 400	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_i_of_type_Int	I
    //   10: istore_3
    //   11: new 499	java/lang/StringBuilder
    //   14: dup
    //   15: invokespecial 500	java/lang/StringBuilder:<init>	()V
    //   18: astore 6
    //   20: aload 6
    //   22: ldc_w 914
    //   25: invokevirtual 504	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   28: pop
    //   29: aload 6
    //   31: aload_1
    //   32: getfield 909	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_b_of_type_Int	I
    //   35: invokevirtual 912	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   38: pop
    //   39: aload 6
    //   41: ldc_w 1365
    //   44: invokevirtual 504	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   47: pop
    //   48: aload_0
    //   49: iload_3
    //   50: aload 6
    //   52: invokevirtual 508	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   55: invokevirtual 918	com/tencent/mtt/browser/download/engine/DownloadTask:addDivideRunPos	(ILjava/lang/String;)V
    //   58: aload_0
    //   59: invokevirtual 553	com/tencent/mtt/browser/download/engine/DownloadTask:getStatus	()B
    //   62: istore_3
    //   63: iconst_1
    //   64: istore_2
    //   65: iload_3
    //   66: iconst_2
    //   67: if_icmpeq +29 -> 96
    //   70: aload_0
    //   71: invokevirtual 553	com/tencent/mtt/browser/download/engine/DownloadTask:getStatus	()B
    //   74: iconst_1
    //   75: if_icmpeq +21 -> 96
    //   78: aload_0
    //   79: invokevirtual 553	com/tencent/mtt/browser/download/engine/DownloadTask:getStatus	()B
    //   82: bipush 11
    //   84: if_icmpne +9 -> 93
    //   87: aload_0
    //   88: bipush 6
    //   90: invokevirtual 739	com/tencent/mtt/browser/download/engine/DownloadTask:setStatus	(B)V
    //   93: aload_0
    //   94: monitorexit
    //   95: return
    //   96: aload_0
    //   97: aload_1
    //   98: invokevirtual 1369	com/tencent/mtt/browser/download/engine/DownloadTask:scheduelTasks	(Lcom/tencent/mtt/browser/download/engine/HttpDownloader;)V
    //   101: aload_0
    //   102: invokevirtual 1371	com/tencent/mtt/browser/download/engine/DownloadTask:a	()Z
    //   105: ifne +388 -> 493
    //   108: aload_0
    //   109: getfield 415	com/tencent/mtt/browser/download/engine/DownloadTask:mDownloaders	Ljava/util/List;
    //   112: invokeinterface 761 1 0
    //   117: astore_1
    //   118: iconst_1
    //   119: istore_3
    //   120: iload_3
    //   121: istore 4
    //   123: iload_2
    //   124: istore 5
    //   126: aload_1
    //   127: invokeinterface 766 1 0
    //   132: ifeq +44 -> 176
    //   135: aload_1
    //   136: invokeinterface 770 1 0
    //   141: checkcast 885	com/tencent/mtt/browser/download/engine/HttpDownloader
    //   144: astore 6
    //   146: aload 6
    //   148: ifnull +340 -> 488
    //   151: aload 6
    //   153: getfield 953	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_Boolean	Z
    //   156: ifne -36 -> 120
    //   159: aload 6
    //   161: invokevirtual 886	com/tencent/mtt/browser/download/engine/HttpDownloader:getStatus	()B
    //   164: bipush 11
    //   166: if_icmpne +317 -> 483
    //   169: iconst_0
    //   170: istore_3
    //   171: iconst_0
    //   172: istore_2
    //   173: goto -53 -> 120
    //   176: aload_0
    //   177: getfield 415	com/tencent/mtt/browser/download/engine/DownloadTask:mDownloaders	Ljava/util/List;
    //   180: invokeinterface 761 1 0
    //   185: astore_1
    //   186: aload_1
    //   187: invokeinterface 766 1 0
    //   192: ifeq +17 -> 209
    //   195: aload_1
    //   196: invokeinterface 770 1 0
    //   201: checkcast 885	com/tencent/mtt/browser/download/engine/HttpDownloader
    //   204: astore 6
    //   206: goto -20 -> 186
    //   209: iload 5
    //   211: ifne +205 -> 416
    //   214: aconst_null
    //   215: astore_1
    //   216: aload_0
    //   217: getfield 415	com/tencent/mtt/browser/download/engine/DownloadTask:mDownloaders	Ljava/util/List;
    //   220: invokeinterface 761 1 0
    //   225: astore 7
    //   227: aload 7
    //   229: invokeinterface 766 1 0
    //   234: ifeq +175 -> 409
    //   237: aload 7
    //   239: invokeinterface 770 1 0
    //   244: checkcast 885	com/tencent/mtt/browser/download/engine/HttpDownloader
    //   247: astore 6
    //   249: aload 6
    //   251: ifnonnull +6 -> 257
    //   254: goto -27 -> 227
    //   257: aload 6
    //   259: invokevirtual 886	com/tencent/mtt/browser/download/engine/HttpDownloader:getStatus	()B
    //   262: istore_3
    //   263: iload_3
    //   264: iconst_5
    //   265: if_icmpeq +3 -> 268
    //   268: iload_3
    //   269: iconst_5
    //   270: if_icmpne -43 -> 227
    //   273: aload 6
    //   275: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   278: aload 6
    //   280: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   283: invokevirtual 1374	com/tencent/mtt/browser/download/engine/DownloadSection:getCurrentSectionWriteLen	()J
    //   286: invokevirtual 949	com/tencent/mtt/browser/download/engine/DownloadSection:setSectionDownloadLen	(J)V
    //   289: aload 6
    //   291: invokevirtual 958	com/tencent/mtt/browser/download/engine/HttpDownloader:start	()V
    //   294: goto +109 -> 403
    //   297: aload_0
    //   298: getfield 400	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_i_of_type_Int	I
    //   301: istore_3
    //   302: new 499	java/lang/StringBuilder
    //   305: dup
    //   306: invokespecial 500	java/lang/StringBuilder:<init>	()V
    //   309: astore_1
    //   310: aload_1
    //   311: ldc_w 1376
    //   314: invokevirtual 504	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   317: pop
    //   318: aload_1
    //   319: aload 6
    //   321: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   324: getfield 952	com/tencent/mtt/browser/download/engine/DownloadSection:mCurrentIndex	I
    //   327: invokevirtual 912	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   330: pop
    //   331: aload_1
    //   332: ldc_w 1378
    //   335: invokevirtual 504	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   338: pop
    //   339: aload_1
    //   340: aload 6
    //   342: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   345: invokevirtual 925	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionStartPos	()Ljava/lang/String;
    //   348: invokevirtual 504	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   351: pop
    //   352: aload_1
    //   353: ldc_w 1378
    //   356: invokevirtual 504	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   359: pop
    //   360: aload_1
    //   361: aload 6
    //   363: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   366: invokevirtual 937	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionEndPos	()Ljava/lang/String;
    //   369: invokevirtual 504	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   372: pop
    //   373: aload_1
    //   374: ldc_w 1378
    //   377: invokevirtual 504	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   380: pop
    //   381: aload_1
    //   382: aload 6
    //   384: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   387: invokevirtual 943	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionWriteLen	()Ljava/lang/String;
    //   390: invokevirtual 504	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   393: pop
    //   394: aload_0
    //   395: iload_3
    //   396: aload_1
    //   397: invokevirtual 508	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   400: invokevirtual 918	com/tencent/mtt/browser/download/engine/DownloadTask:addDivideRunPos	(ILjava/lang/String;)V
    //   403: aload 6
    //   405: astore_1
    //   406: goto -179 -> 227
    //   409: aload_1
    //   410: ifnull +6 -> 416
    //   413: aload_0
    //   414: monitorexit
    //   415: return
    //   416: iload 4
    //   418: ifeq +22 -> 440
    //   421: aload_0
    //   422: getfield 1096	com/tencent/mtt/browser/download/engine/DownloadTask:mCanceled	Z
    //   425: ifeq +15 -> 440
    //   428: aload_0
    //   429: iconst_0
    //   430: putfield 1096	com/tencent/mtt/browser/download/engine/DownloadTask:mCanceled	Z
    //   433: aload_0
    //   434: invokevirtual 1099	com/tencent/mtt/browser/download/engine/DownloadTask:taskCanceled	()V
    //   437: aload_0
    //   438: monitorexit
    //   439: return
    //   440: aload_0
    //   441: iload 5
    //   443: putfield 380	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_h_of_type_Boolean	Z
    //   446: iload 5
    //   448: ifeq +19 -> 467
    //   451: aload_0
    //   452: aload_0
    //   453: getfield 400	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_i_of_type_Int	I
    //   456: ldc_w 1380
    //   459: invokevirtual 918	com/tencent/mtt/browser/download/engine/DownloadTask:addDivideRunPos	(ILjava/lang/String;)V
    //   462: aload_0
    //   463: invokevirtual 1383	com/tencent/mtt/browser/download/engine/DownloadTask:tryToFinisDownloadTask	()Z
    //   466: pop
    //   467: aload_0
    //   468: monitorexit
    //   469: return
    //   470: astore_1
    //   471: aload_0
    //   472: monitorexit
    //   473: aload_1
    //   474: athrow
    //   475: astore_1
    //   476: goto -179 -> 297
    //   479: astore_1
    //   480: goto -77 -> 403
    //   483: iconst_0
    //   484: istore_2
    //   485: goto -365 -> 120
    //   488: iconst_0
    //   489: istore_2
    //   490: goto -370 -> 120
    //   493: iconst_1
    //   494: istore 4
    //   496: iconst_0
    //   497: istore 5
    //   499: goto -323 -> 176
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	502	0	this	DownloadTask
    //   0	502	1	paramHttpDownloader	HttpDownloader
    //   0	502	2	paramBoolean	boolean
    //   10	386	3	i1	int
    //   121	374	4	i2	int
    //   124	374	5	bool	boolean
    //   18	386	6	localObject	Object
    //   225	13	7	localIterator	Iterator
    // Exception table:
    //   from	to	target	type
    //   2	63	470	finally
    //   70	93	470	finally
    //   96	118	470	finally
    //   126	146	470	finally
    //   151	169	470	finally
    //   176	186	470	finally
    //   186	206	470	finally
    //   216	227	470	finally
    //   227	249	470	finally
    //   257	263	470	finally
    //   273	294	470	finally
    //   297	403	470	finally
    //   421	437	470	finally
    //   440	446	470	finally
    //   451	467	470	finally
    //   273	294	475	java/lang/Exception
    //   297	403	479	java/lang/Exception
  }
  
  protected void downloadTaskInProbeFileLenMode()
  {
    f();
    this.mDownloaders.clear();
    Object localObject = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.createSection(0, null);
    DLReporter localDLReporter = DLReporterManager.getReporter(getDownloadTaskId());
    if (localDLReporter != null) {
      localDLReporter.addStep(":RUN_D[");
    }
    localObject = new HttpDownloader(this, (DownloadSection)localObject, 0);
    ((HttpDownloader)localObject).mIsDetectDownloader = true;
    this.mDownloaders.add(0, localObject);
    if (this.mCanceled)
    {
      if (localDLReporter != null) {
        localDLReporter.addStep("-1]");
      }
      taskCanceled();
      return;
    }
    ((HttpDownloader)localObject).jdField_a_of_type_Boolean = false;
    if (localDLReporter != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("-2(");
      localStringBuilder.append(System.currentTimeMillis());
      localStringBuilder.append(")]");
      localDLReporter.addStep(localStringBuilder.toString());
    }
    try
    {
      ((HttpDownloader)localObject).start();
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError) {}
  }
  
  protected void downloadTaskInSectionDeterminedMode()
  {
    f();
    int i1 = 0;
    adjustMaxDownloaderNum(false);
    try
    {
      if (!newSavedFile(NEW_DOWNLOAD_FILE_MODE_IN_DETERMINE_SEC)) {
        return;
      }
      if (this.mCanceled)
      {
        taskCanceled();
        return;
      }
      b();
      return;
    }
    catch (IOException localIOException)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("-D1:");
      ((StringBuilder)localObject).append(localIOException.toString());
      this.mErrorDes = ((StringBuilder)localObject).toString();
      StackTraceElement[] arrayOfStackTraceElement = localIOException.getStackTrace();
      int i2 = arrayOfStackTraceElement.length;
      while (i1 < i2)
      {
        localObject = arrayOfStackTraceElement[i1];
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.mErrorDes);
        localStringBuilder.append(((StackTraceElement)localObject).toString());
        this.mErrorDes = localStringBuilder.toString();
        i1 += 1;
      }
      this.mDownloadErrorCode = 21;
      a((byte)5);
    }
  }
  
  public boolean downloaderFireEvent(HttpDownloader paramHttpDownloader)
  {
    Object localObject = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker;
    if (localObject == null) {
      return false;
    }
    try
    {
      ((Worker)localObject).workHandler().removeMessages(5);
      localObject = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker.workHandler().obtainMessage();
      ((Message)localObject).what = 5;
      ((Message)localObject).obj = paramHttpDownloader;
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker.workHandler().sendMessage((Message)localObject);
      return true;
    }
    catch (NullPointerException paramHttpDownloader)
    {
      for (;;) {}
    }
  }
  
  void e()
  {
    f();
    setRangeNotSupported(true, true);
    setMaxThreadNum(1, true);
    setStatus((byte)10);
  }
  
  void f()
  {
    if (Thread.currentThread().getName().equals("worker_thread")) {
      return;
    }
    throw new RuntimeException("this method can only be called in worker_thread.");
  }
  
  public void fireTaskCreatedEvent()
  {
    this.mCanceled = false;
    DownloadproviderHelper.b(this.jdField_a_of_type_Int);
    this.mDownloadErrorCode = 0;
    fireObserverEvent(this.mStatus);
  }
  
  public boolean firstSectionComeBack(int paramInt, String paramString, MttResponse paramMttResponse)
  {
    Object localObject = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker;
    if (localObject == null) {
      return false;
    }
    try
    {
      ((Worker)localObject).workHandler().removeMessages(4);
      localObject = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker.workHandler().obtainMessage();
      ((Message)localObject).what = 4;
      b localb = new b();
      localb.jdField_a_of_type_Int = paramInt;
      localb.jdField_a_of_type_JavaLangString = paramString;
      localb.jdField_a_of_type_ComTencentCommonHttpMttResponse = paramMttResponse;
      ((Message)localObject).obj = localb;
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker.workHandler().sendMessage((Message)localObject);
      return true;
    }
    catch (NullPointerException paramString)
    {
      for (;;) {}
    }
  }
  
  public void fixDownloadStatus()
  {
    Iterator localIterator = this.mDownloaders.iterator();
    while (localIterator.hasNext())
    {
      HttpDownloader localHttpDownloader = (HttpDownloader)localIterator.next();
      if ((localHttpDownloader != null) && (localHttpDownloader.getStatus() == 11))
      {
        i1 = 0;
        break label49;
      }
    }
    int i1 = 1;
    label49:
    if ((i1 != 0) && (getStatus() == 11)) {
      setStatus((byte)6);
    }
  }
  
  public String getAnnotation()
  {
    return this.jdField_d_of_type_JavaLangString;
  }
  
  public String getAnnotationExt()
  {
    return this.jdField_e_of_type_JavaLangString;
  }
  
  public int getBufferSize()
  {
    Iterator localIterator = this.mDownloaders.iterator();
    int i1 = 0;
    while (localIterator.hasNext())
    {
      Object localObject = (HttpDownloader)localIterator.next();
      if (localObject != null)
      {
        localObject = ((HttpDownloader)localObject).jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.dataBuffer();
        if (localObject != null) {
          i1 += ((DownloadDataBuffer)localObject).size();
        }
      }
    }
    return i1;
  }
  
  public String getChannel()
  {
    return this.l;
  }
  
  public String getChannelPkgName()
  {
    return this.n;
  }
  
  public String getCookie()
  {
    return this.k;
  }
  
  public long getCostTime()
  {
    return this.mCostTime;
  }
  
  public long getCreateTime()
  {
    return this.mCreateTime;
  }
  
  public boolean getDetectReceived()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  public long getDoneTime()
  {
    return this.jdField_c_of_type_Long;
  }
  
  public int getDownloadApkRunPos()
  {
    return this.jdField_g_of_type_Int;
  }
  
  public int getDownloadApkType()
  {
    return this.jdField_f_of_type_Int;
  }
  
  public int getDownloadTaskId()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public long getDownloadedSize()
  {
    return this.mDownloadedSize;
  }
  
  public String getDownloaderDownloadDataUrl()
  {
    return this.jdField_i_of_type_JavaLangString;
  }
  
  public String getDownloaderRunPath(boolean paramBoolean)
  {
    if ((!paramBoolean) && (!TextUtils.isEmpty(this.mRunPos))) {
      return this.mRunPos;
    }
    this.mRunPos = "";
    try
    {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        if ((this.mDownloaders != null) && (this.mDownloaders.size() >= 1))
        {
          Iterator localIterator = this.mDownloaders.iterator();
          while (localIterator.hasNext())
          {
            Object localObject3 = (HttpDownloader)localIterator.next();
            if (localObject3 != null)
            {
              localObject3 = ((HttpDownloader)localObject3).jdField_c_of_type_JavaLangString;
              if (!TextUtils.isEmpty((CharSequence)localObject3))
              {
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append(this.mRunPos);
                localStringBuilder.append((String)localObject3);
                localStringBuilder.append(";");
                this.mRunPos = localStringBuilder.toString();
              }
            }
          }
        }
      }
    }
    catch (ConcurrentModificationException localConcurrentModificationException)
    {
      localConcurrentModificationException.printStackTrace();
      return this.mRunPos;
    }
  }
  
  public String getETag()
  {
    return this.jdField_c_of_type_JavaLangString;
  }
  
  public int getErrorCode()
  {
    return this.mDownloadErrorCode;
  }
  
  public String getErrorDesForUpload()
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(getStatus());
    ((StringBuilder)localObject1).append("-");
    ((StringBuilder)localObject1).append(this.mDownloadErrorCode);
    localObject3 = ((StringBuilder)localObject1).toString();
    localObject1 = localObject3;
    Object localObject2 = localObject3;
    try
    {
      if (!TextUtils.isEmpty(this.mErrorDes))
      {
        localObject2 = localObject3;
        localObject1 = new StringBuilder();
        localObject2 = localObject3;
        ((StringBuilder)localObject1).append((String)localObject3);
        localObject2 = localObject3;
        ((StringBuilder)localObject1).append("[");
        localObject2 = localObject3;
        ((StringBuilder)localObject1).append(this.mErrorDes);
        localObject2 = localObject3;
        ((StringBuilder)localObject1).append("];");
        localObject2 = localObject3;
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      localObject2 = localObject1;
      localObject3 = localObject1;
      if (this.mDownloaders != null)
      {
        localObject2 = localObject1;
        localObject3 = localObject1;
        if (this.mDownloaders.size() >= 1)
        {
          localObject2 = localObject1;
          Iterator localIterator = this.mDownloaders.iterator();
          for (;;)
          {
            localObject2 = localObject1;
            localObject3 = localObject1;
            if (!localIterator.hasNext()) {
              break;
            }
            localObject2 = localObject1;
            localObject3 = (HttpDownloader)localIterator.next();
            if (localObject3 != null)
            {
              localObject2 = localObject1;
              StringBuilder localStringBuilder = new StringBuilder();
              localObject2 = localObject1;
              localStringBuilder.append("<");
              localObject2 = localObject1;
              localStringBuilder.append(((HttpDownloader)localObject3).jdField_b_of_type_Int);
              localObject2 = localObject1;
              localStringBuilder.append(":");
              localObject2 = localObject1;
              localStringBuilder.append(((HttpDownloader)localObject3).jdField_b_of_type_JavaLangString);
              localObject2 = localObject1;
              localStringBuilder.append(">");
              localObject2 = localObject1;
              localObject3 = localStringBuilder.toString();
              localObject2 = localObject1;
              if (!TextUtils.isEmpty((CharSequence)localObject3))
              {
                localObject2 = localObject1;
                localStringBuilder = new StringBuilder();
                localObject2 = localObject1;
                localStringBuilder.append((String)localObject1);
                localObject2 = localObject1;
                localStringBuilder.append((String)localObject3);
                localObject2 = localObject1;
                localStringBuilder.append(";");
                localObject2 = localObject1;
                localObject1 = localStringBuilder.toString();
              }
            }
          }
        }
      }
      return (String)localObject3;
    }
    catch (ConcurrentModificationException localConcurrentModificationException)
    {
      localConcurrentModificationException.printStackTrace();
      localObject3 = localObject2;
    }
  }
  
  public String getErrorDesc(ArrayList<String> paramArrayList)
  {
    if (paramArrayList != null) {}
    try
    {
      if (paramArrayList.size() != 0)
      {
        if (this.mDownloadErrorCode == 101) {
          return "下载失败";
        }
        if ((this.mDownloadErrorCode >= 0) && (this.mDownloadErrorCode < paramArrayList.size())) {
          return (String)paramArrayList.get(this.mDownloadErrorCode);
        }
      }
      else
      {
        return "";
      }
    }
    catch (Throwable paramArrayList)
    {
      paramArrayList.printStackTrace();
      return "";
    }
    return "";
  }
  
  public long getExtFlag()
  {
    return this.jdField_d_of_type_Long;
  }
  
  public boolean getExtFlagAutoInstall()
  {
    return (this.jdField_d_of_type_Long & 0x4) != 0L;
  }
  
  public boolean getExtFlagContinueTask()
  {
    return (this.jdField_d_of_type_Long & 1L) != 0L;
  }
  
  public boolean getExtFlagCreateFirst()
  {
    return (this.jdField_d_of_type_Long & 0x10) != 0L;
  }
  
  public boolean getExtFlagFileSizeReal()
  {
    return (this.jdField_d_of_type_Long & 0x8) != 0L;
  }
  
  public boolean getExtFlagPlugin()
  {
    return (this.jdField_d_of_type_Long & 0x20) != 0L;
  }
  
  public boolean getExtFlagShowToast()
  {
    return (this.jdField_d_of_type_Long & 0x2) != 0L;
  }
  
  public String getFileFolderPath()
  {
    return this.mFileFolderPath;
  }
  
  public String getFileName()
  {
    return this.mFileName;
  }
  
  public long getFileSizeForHijack()
  {
    return this.mFileSizeForHijack;
  }
  
  public int getFlag()
  {
    return this.mFlag;
  }
  
  public String getFromWhere()
  {
    return this.q;
  }
  
  public String getFullFilePath()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    if ((!TextUtils.isEmpty(this.mFileFolderPath)) && (!TextUtils.isEmpty(this.mFileName)))
    {
      localStringBuilder.append(this.mFileFolderPath);
      localStringBuilder.append(File.separator);
      localStringBuilder.append(this.mFileName);
      return localStringBuilder.toString();
    }
    return null;
  }
  
  public boolean getHasReported()
  {
    return (this.jdField_d_of_type_Long & 0x100) == 256L;
  }
  
  public String getHost()
  {
    return this.m;
  }
  
  public int getHttpResponseCode()
  {
    try
    {
      Iterator localIterator;
      if ((this.mDownloaders != null) && (!this.mDownloaders.isEmpty())) {
        localIterator = this.mDownloaders.iterator();
      }
      while (localIterator.hasNext())
      {
        HttpDownloader localHttpDownloader = (HttpDownloader)localIterator.next();
        if ((localHttpDownloader != null) && (localHttpDownloader.jdField_f_of_type_Int != -1))
        {
          return localHttpDownloader.jdField_f_of_type_Int;
          if (this.jdField_d_of_type_Int != -1)
          {
            int i1 = this.jdField_d_of_type_Int;
            return i1;
          }
        }
      }
      return -1;
    }
    catch (ConcurrentModificationException localConcurrentModificationException)
    {
      for (;;) {}
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      for (;;) {}
    }
    return this.jdField_d_of_type_Int;
    return this.jdField_d_of_type_Int;
  }
  
  public String getIconUrl()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public boolean getInstallByYYB()
  {
    return (this.jdField_d_of_type_Long & 0x10000) == 65536L;
  }
  
  public boolean getIsFromTBS()
  {
    return (this.jdField_d_of_type_Long & 0x4000) == 16384L;
  }
  
  public boolean getIsFromWeb()
  {
    return (this.jdField_d_of_type_Long & 0x200) == 512L;
  }
  
  public boolean getIsOpen()
  {
    return (this.mFlag & 0x200000) == 2097152;
  }
  
  public boolean getIsPausdedByNoWifi()
  {
    return (this.mFlag & 0x2000) == 8192;
  }
  
  public boolean getIsPausdedByUser()
  {
    return (this.jdField_d_of_type_Long & 0x80) == 128L;
  }
  
  public boolean getIsSupportResume()
  {
    return this.mIsSupportResume;
  }
  
  public boolean getIsTencentDNS()
  {
    return (this.jdField_d_of_type_Long & 0x400) == 1024L;
  }
  
  public int getMaxThreadNum()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public int getMergeFailCode()
  {
    return this.mMergeFailCode;
  }
  
  public boolean getNeedNotification()
  {
    return this.jdField_d_of_type_Boolean;
  }
  
  public boolean getNotifiedInstall()
  {
    return (this.jdField_d_of_type_Long & 0x800) == 2048L;
  }
  
  public String getNotifyInstallCount()
  {
    return this.r;
  }
  
  public String getOriginalUrl()
  {
    return this.j;
  }
  
  public String getPackageName()
  {
    return this.jdField_f_of_type_JavaLangString;
  }
  
  public String getPluginMd5()
  {
    return this.o;
  }
  
  public String getPostData()
  {
    return this.jdField_g_of_type_JavaLangString;
  }
  
  public int getProgress()
  {
    long l1 = this.mFileSize;
    if (l1 != 0L) {
      return (int)(this.mDownloadedSize * 100L / l1);
    }
    return 0;
  }
  
  public String getReferer()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  public String getReportDownloadDataUrl()
  {
    if (!TextUtils.isEmpty(this.jdField_h_of_type_JavaLangString)) {
      return this.jdField_h_of_type_JavaLangString;
    }
    Object localObject2 = "";
    int i1 = 0;
    try
    {
      Object localObject1;
      if ((this.mDownloaders != null) && (!this.mDownloaders.isEmpty()))
      {
        Iterator localIterator = this.mDownloaders.iterator();
        if (!localIterator.hasNext()) {
          return localObject2;
        }
        Object localObject3 = (HttpDownloader)localIterator.next();
        localObject1 = localObject2;
        if (localObject3 != null)
        {
          localObject1 = ((HttpDownloader)localObject3).jdField_a_of_type_JavaLangString;
          if (!TextUtils.isEmpty((CharSequence)localObject1))
          {
            localObject3 = new StringBuilder();
            ((StringBuilder)localObject3).append((String)localObject2);
            ((StringBuilder)localObject3).append(i1);
            ((StringBuilder)localObject3).append("-");
            ((StringBuilder)localObject3).append((String)localObject1);
            ((StringBuilder)localObject3).append(";");
            localObject1 = ((StringBuilder)localObject3).toString();
          }
          else if (!TextUtils.isEmpty(getDownloaderDownloadDataUrl()))
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append((String)localObject2);
            ((StringBuilder)localObject1).append(i1);
            ((StringBuilder)localObject1).append("+");
            ((StringBuilder)localObject1).append(getDownloaderDownloadDataUrl());
            ((StringBuilder)localObject1).append(";");
            localObject1 = ((StringBuilder)localObject1).toString();
          }
          else
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append((String)localObject2);
            ((StringBuilder)localObject1).append(i1);
            ((StringBuilder)localObject1).append("*");
            ((StringBuilder)localObject1).append(getTaskUrl());
            ((StringBuilder)localObject1).append(";");
            localObject1 = ((StringBuilder)localObject1).toString();
          }
        }
      }
      else
      {
        localObject1 = this.mDownloadUrl;
        return (String)localObject1;
        return this.mDownloadUrl;
      }
    }
    catch (ConcurrentModificationException localConcurrentModificationException)
    {
      for (;;)
      {
        continue;
        i1 += 1;
        localObject2 = localConcurrentModificationException;
      }
    }
    return (String)localObject2;
  }
  
  public String getReportString()
  {
    return this.p;
  }
  
  public String getRetryUrl()
  {
    try
    {
      if ((this.jdField_e_of_type_Int > -1) && (this.jdField_e_of_type_Int < this.jdField_a_of_type_JavaUtilArrayList.size())) {
        return (String)this.jdField_a_of_type_JavaUtilArrayList.get(this.jdField_e_of_type_Int);
      }
      String str = getTaskUrl();
      return str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return null;
  }
  
  public ArrayList<String> getRetryUrlList()
  {
    return this.jdField_a_of_type_JavaUtilArrayList;
  }
  
  public String getRetryUrlsStrFomat()
  {
    Object localObject = this.jdField_a_of_type_JavaUtilArrayList;
    if ((localObject != null) && (((ArrayList)localObject).size() != 0))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("");
      ((StringBuilder)localObject).append(this.jdField_e_of_type_Int);
      ((StringBuilder)localObject).append(";");
      localObject = ((StringBuilder)localObject).toString();
      Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append((String)localObject);
        localStringBuilder.append(str);
        localStringBuilder.append(";");
        localObject = localStringBuilder.toString();
      }
      return (String)localObject;
    }
    return null;
  }
  
  public long getSaveFlowSize()
  {
    return this.mSaveFlowSize;
  }
  
  public DownloadSections getSectionData()
  {
    return this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections;
  }
  
  public String getShowFileName()
  {
    return this.mFileName;
  }
  
  public float getSpeed()
  {
    Object localObject = DownloadproviderHelper.a(this.jdField_a_of_type_Int);
    if (localObject == null) {
      return 0.0F;
    }
    localObject = (DownloadSpeedData)((LinkedList)localObject).peek();
    long l1 = System.currentTimeMillis();
    long l2 = this.mDownloadedSize;
    float f1;
    if (localObject != null)
    {
      if (((DownloadSpeedData)localObject).mReceiveTime == l1) {
        f1 = 0.0F;
      } else {
        f1 = (float)(l2 - ((DownloadSpeedData)localObject).mDataSize) * 1000.0F / (float)(l1 - ((DownloadSpeedData)localObject).mReceiveTime);
      }
    }
    else {
      f1 = 0.0F;
    }
    float f2 = f1;
    if (f1 < 0.0F) {
      f2 = 0.0F;
    }
    localObject = DownloadproviderHelper.a(getDownloadTaskId());
    if ((localObject != null) && (((Float)localObject).floatValue() > f2) && (f2 < 1024.0F)) {
      return DownloadproviderHelper.a(getDownloadTaskId()).floatValue();
    }
    DownloadproviderHelper.a(getDownloadTaskId(), Float.valueOf(f2));
    return f2;
  }
  
  public boolean getTaskRunRightNow()
  {
    return (this.mFlag & 0x4) == 4;
  }
  
  public String getTaskUrl()
  {
    return this.mDownloadUrl;
  }
  
  public long getTotalSize()
  {
    return this.mFileSize;
  }
  
  public String getUploadErrorDesc()
  {
    return String.valueOf(this.mDownloadErrorCode);
  }
  
  public String getVersionName()
  {
    return this.mVersionName;
  }
  
  public Worker getWorker()
  {
    Worker localWorker = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker;
    if (localWorker != null) {
      return localWorker;
    }
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker = new Worker();
    return this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker;
  }
  
  public long getWrittenSize()
  {
    return this.mWrittenSize;
  }
  
  protected void handleTaskFailed(HttpDownloader paramHttpDownloader)
  {
    f();
    setStatus((byte)5);
    this.mDownloadErrorCode = paramHttpDownloader.jdField_c_of_type_Int;
    if (this.mDownloadErrorCode == 27) {
      c();
    }
    Iterator localIterator = this.mDownloaders.iterator();
    while (localIterator.hasNext())
    {
      HttpDownloader localHttpDownloader = (HttpDownloader)localIterator.next();
      if ((localHttpDownloader != null) && (!localHttpDownloader.jdField_a_of_type_Boolean) && (localHttpDownloader != paramHttpDownloader)) {
        localHttpDownloader.cancel();
      }
    }
    fireObserverEvent(this.mStatus);
  }
  
  protected void handleTaskRestart()
  {
    f();
    setStatus((byte)10);
  }
  
  public boolean hasDelayNotifyInstall()
  {
    return (this.jdField_d_of_type_Long & 0x8000) == 32768L;
  }
  
  public boolean hasDeltaUpdateFailed()
  {
    return (this.mFlag & 0x400) > 0;
  }
  
  public boolean hasFlag(int paramInt)
  {
    return (paramInt & getFlag()) != 0;
  }
  
  public boolean hasInstalled()
  {
    return (this.mFlag & 0x40) == 64;
  }
  
  public boolean hasLogo()
  {
    return (this.mFlag & 0x80000) == 524288;
  }
  
  public boolean hasTmpTryRange()
  {
    try
    {
      Iterator localIterator;
      if ((this.mDownloaders != null) && (!this.mDownloaders.isEmpty())) {
        localIterator = this.mDownloaders.iterator();
      }
      while (localIterator.hasNext())
      {
        HttpDownloader localHttpDownloader = (HttpDownloader)localIterator.next();
        if ((localHttpDownloader != null) && (localHttpDownloader.jdField_e_of_type_Boolean))
        {
          return true;
          boolean bool = this.jdField_g_of_type_Boolean;
          if (bool) {
            return true;
          }
        }
      }
    }
    catch (ConcurrentModificationException localConcurrentModificationException)
    {
      for (;;) {}
    }
    return false;
  }
  
  public void initDownloadStatus(TaskObserver paramTaskObserver)
  {
    addObserver(paramTaskObserver);
  }
  
  public boolean isApkFile()
  {
    return (!TextUtils.isEmpty(this.mFileName)) && (this.mFileName.toLowerCase().endsWith(".apk"));
  }
  
  public boolean isAppUpdateTask()
  {
    return this.jdField_c_of_type_Boolean;
  }
  
  public boolean isAppointmentTask()
  {
    return (this.mFlag & 0x40000000) != 0;
  }
  
  public boolean isBTTask()
  {
    return (this.mFlag & 0x2000000) != 0;
  }
  
  public boolean isBackAutoTask()
  {
    return (this.mFlag & 0x10000000) == 268435456;
  }
  
  public boolean isBufferEmpty()
  {
    return getBufferSize() == 0;
  }
  
  public boolean isDeleted()
  {
    return this.mIsDeleted;
  }
  
  public boolean isDownloadFileExist()
  {
    if ((!TextUtils.isEmpty(this.mFileName)) && (!TextUtils.isEmpty(this.mFileFolderPath))) {
      return new File(this.mFileFolderPath, this.mFileName).exists();
    }
    return false;
  }
  
  public boolean isDownloadedFileSizeOk()
  {
    if (!TextUtils.isEmpty(this.mFileName))
    {
      if (TextUtils.isEmpty(this.mFileFolderPath)) {
        return false;
      }
      File localFile = new File(this.mFileFolderPath, this.mFileName);
      return (localFile.exists()) && (localFile.length() == this.mFileSize);
    }
    return false;
  }
  
  public boolean isDownloaderFinish()
  {
    return this.jdField_h_of_type_Boolean;
  }
  
  public boolean isFile()
  {
    boolean bool1 = TextUtils.isEmpty(this.mFileName);
    boolean bool2 = false;
    if (!bool1)
    {
      if (TextUtils.isEmpty(this.mFileFolderPath)) {
        return false;
      }
      File localFile = new File(this.mFileFolderPath, this.mFileName);
      bool1 = bool2;
      if (localFile.exists())
      {
        bool1 = bool2;
        if (localFile.isFile()) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
  
  public boolean isFileExist()
  {
    if ((!TextUtils.isEmpty(this.mFileName)) && (!TextUtils.isEmpty(this.mFileFolderPath)))
    {
      boolean bool2 = new File(this.mFileFolderPath, this.mFileName).exists();
      boolean bool1 = bool2;
      if (!bool2)
      {
        Object localObject1 = this.mFileFolderPath;
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(".");
        ((StringBuilder)localObject2).append(this.mFileName);
        ((StringBuilder)localObject2).append(".qbdltmp");
        localObject1 = new File((String)localObject1, ((StringBuilder)localObject2).toString());
        bool1 = ((File)localObject1).exists();
        if (!bool1)
        {
          localObject2 = this.mFileFolderPath;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(this.mFileName);
          localStringBuilder.append(".qbdltmp");
          localObject2 = new File((String)localObject2, localStringBuilder.toString());
          if (((File)localObject2).exists())
          {
            FileUtilsF.renameTo((File)localObject2, (File)localObject1);
            return true;
          }
        }
      }
      return bool1;
    }
    return false;
  }
  
  public boolean isForground()
  {
    return this.mIsForground;
  }
  
  public boolean isFsTast()
  {
    return (this.mFlag & 0x4000000) != 0;
  }
  
  public boolean isHidden()
  {
    return (this.mFlag & 0x20) == 32;
  }
  
  public boolean isInDataDir()
  {
    String str = Environment.getDataDirectory().getAbsolutePath();
    return (!TextUtils.isEmpty(this.mFileFolderPath)) && (this.mFileFolderPath.startsWith(str));
  }
  
  public boolean isM3U8()
  {
    return (this.mFlag & 0x200) != 0;
  }
  
  public boolean isMergingFile()
  {
    return this.mIsMergeFile;
  }
  
  public boolean isMp4()
  {
    return (this.mFlag & 0x4000) != 0;
  }
  
  public boolean isNeedCheckAfterDownload()
  {
    return this.jdField_e_of_type_Boolean;
  }
  
  public boolean isNeedRestartTask()
  {
    return this.jdField_f_of_type_Boolean;
  }
  
  public boolean isPlayingVideo()
  {
    return false;
  }
  
  public boolean isPostTask()
  {
    return (this.mFlag & 0x20000) == 131072;
  }
  
  public boolean isPreDownload()
  {
    return (this.mFlag & 0x1000) == 4096;
  }
  
  public boolean isPreDownloadTask()
  {
    return (this.jdField_d_of_type_Long & 0x2000000) == 33554432L;
  }
  
  public boolean isPrivateTask()
  {
    return (this.jdField_d_of_type_Long & 0x200000) == 2097152L;
  }
  
  public boolean isPrivateTaskRemoved()
  {
    return (this.jdField_d_of_type_Long & 0x400000) == 4194304L;
  }
  
  public boolean isQBUpdateTask()
  {
    return (getExtFlag() & 0x40) == 64L;
  }
  
  public boolean isQQMarketCommonDeltaUpdateTask()
  {
    return (getFlag() & 0x100) == 256;
  }
  
  public boolean isQQMarketDeltaUpdateTask()
  {
    boolean bool1 = isQQMarketCommonDeltaUpdateTask();
    boolean bool2 = isQQMarketNewDeltaUpdateTask();
    return (bool1) || (bool2);
  }
  
  public boolean isQQMarketNewDeltaUpdateTask()
  {
    return (getFlag() & 0x8000) == 32768;
  }
  
  public boolean isQQMarketTask()
  {
    return (getFlag() & 0x10) == 16;
  }
  
  public boolean isQQMarketUpdateTask()
  {
    return (this.mFlag & 0x1) != 0;
  }
  
  public boolean isRangeNotSupported()
  {
    return (this.mFlag & 0x80) == 128;
  }
  
  public boolean isSafeApk()
  {
    return (this.mFlag & 0x8000000) != 0;
  }
  
  public boolean isStartOnWifiTask()
  {
    return (this.mFlag & 0x80000000) != 0;
  }
  
  protected boolean isTheDownloadSectionDetermined()
  {
    return (isM3U8()) || ((this.mFileSize > 0L) && (this.mDownloadedSize > 0L) && (!TextUtils.isEmpty(this.mFileName)) && (FileUtilsF.checkFileName(this.mFileName))) || ((this.mFileSize > 0L) && (getExtFlagFileSizeReal()));
  }
  
  protected boolean isValidSectionData()
  {
    return this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.isValidSectionData(this.mFileSize, this.jdField_b_of_type_Int);
  }
  
  public boolean isWriteFinish()
  {
    return this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.isWriteFinish(this.mFileSize);
  }
  
  public boolean isXunleiTask()
  {
    return (this.mFlag & 0x1000000) != 0;
  }
  
  protected boolean makeSureFileSize(long paramLong)
  {
    DownloadEventManager.getInstance().downloadEventOnDownloadedTaskListener().notifyTaskLength(this, this.mDownloadUrl, this.mFileSizeForHijack, paramLong);
    this.mFileSize = paramLong;
    updateFileSize();
    return new DownloadHijackExcutor(BaseDownloadManager.getInstance()).doHijack(this, this.mFileSizeForHijack, paramLong);
  }
  
  protected void makeSureSupportResume(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.mIsSupportResume = false;
    }
  }
  
  protected boolean newSavedFile(int paramInt)
  {
    int i1 = 1;
    try
    {
      new File(this.mFileFolderPath).mkdirs();
      Object localObject1 = this.mFileFolderPath;
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(".");
      ((StringBuilder)localObject2).append(this.mFileName);
      ((StringBuilder)localObject2).append(".qbdltmp");
      localObject2 = new File((String)localObject1, ((StringBuilder)localObject2).toString());
      localObject1 = this.mFileFolderPath;
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append(this.mFileName);
      ((StringBuilder)localObject3).append(".qbdltmp");
      localObject1 = new File((String)localObject1, ((StringBuilder)localObject3).toString());
      if (((File)localObject1).exists()) {
        FileUtilsF.renameTo((File)localObject1, (File)localObject2);
      }
      localObject1 = localObject2;
      if (this.mDownloadedSize > 0L)
      {
        localObject1 = localObject2;
        if (!((File)localObject2).exists()) {
          localObject1 = new File(this.mFileFolderPath, this.mFileName);
        }
      }
      if ((this.mDownloadedSize > 0L) && (!isFileExist()))
      {
        localObject1 = this.mFileFolderPath;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(".");
        ((StringBuilder)localObject2).append(this.mFileName);
        ((StringBuilder)localObject2).append(".dltmp");
        localObject1 = new File((String)localObject1, ((StringBuilder)localObject2).toString());
        if (((File)localObject1).exists()) {
          ((File)localObject1).delete();
        }
        if (paramInt == 2)
        {
          if (!isAppointmentTask()) {
            isHidden();
          }
          this.mDownloadedSize = 0L;
          this.mWrittenSize = 0L;
          this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.clear(false);
          setStatus((byte)10);
          return false;
        }
        if (paramInt == 3)
        {
          if (this.mCanceled) {
            return false;
          }
          notifyTaskFailed(26);
          return false;
        }
        notifyTaskFailed(24);
        return false;
      }
      if (!((File)localObject1).exists())
      {
        ((File)localObject1).createNewFile();
        this.jdField_a_of_type_JavaIoRandomAccessFile = new RandomAccessFile((File)localObject1, "rw");
      }
      if (this.jdField_a_of_type_JavaIoRandomAccessFile == null) {
        this.jdField_a_of_type_JavaIoRandomAccessFile = new RandomAccessFile((File)localObject1, "rw");
      }
      return true;
    }
    catch (Exception localException)
    {
      if ((!TextUtils.isEmpty(localException.getMessage())) && (localException.getMessage().toLowerCase().contains("enopc"))) {
        paramInt = i1;
      } else {
        paramInt = 0;
      }
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("-D2:");
      ((StringBuilder)localObject2).append(localException.getMessage());
      this.mErrorDes = ((StringBuilder)localObject2).toString();
      if (paramInt != 0)
      {
        this.mDownloadErrorCode = 2;
        setStatus((byte)11);
        return false;
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("-D2:[");
      ((StringBuilder)localObject2).append(localException.getMessage());
      ((StringBuilder)localObject2).append("]");
      Object localObject4 = getFileFolderPath();
      Object localObject3 = getFullFilePath();
      if (TextUtils.isEmpty((CharSequence)localObject4))
      {
        ((StringBuilder)localObject2).append("-NO_FOLDER");
      }
      else
      {
        localObject4 = new File((String)localObject4);
        ((StringBuilder)localObject2).append("-FOLDER:[");
        ((StringBuilder)localObject2).append(a((File)localObject4));
        ((StringBuilder)localObject2).append("]");
      }
      if (TextUtils.isEmpty((CharSequence)localObject3))
      {
        ((StringBuilder)localObject2).append("-NO_FILE");
      }
      else
      {
        localObject3 = new File((String)localObject3);
        ((StringBuilder)localObject2).append("-FILE:[p:");
        ((StringBuilder)localObject2).append(a((File)localObject3));
        ((StringBuilder)localObject2).append("]");
      }
      this.mErrorDes = ((StringBuilder)localObject2).toString();
      this.mDownloadErrorCode = 31;
      String str = localException.getMessage();
      if (!TextUtils.isEmpty(str)) {
        if (str.toLowerCase().contains("erofs")) {
          this.mDownloadErrorCode = 29;
        } else if (str.toLowerCase().contains("ebus")) {
          this.mDownloadErrorCode = 30;
        }
      }
      setStatus((byte)5);
      fireObserverEvent(this.mStatus);
    }
    return false;
  }
  
  protected void notifyTaskFailed(int paramInt)
  {
    c();
    this.mDownloadErrorCode = paramInt;
    a((byte)5);
  }
  
  public void notifyTaskStarted()
  {
    setStatus((byte)1);
    fireObserverEvent(this.mStatus);
  }
  
  public void onTaskCompleted(Task paramTask)
  {
    try
    {
      f();
      doScheduleDownloaders((HttpDownloader)paramTask, true);
      return;
    }
    finally
    {
      paramTask = finally;
      throw paramTask;
    }
  }
  
  public void onTaskCreated(Task paramTask) {}
  
  public void onTaskExtEvent(Task paramTask) {}
  
  public void onTaskFailed(Task paramTask)
  {
    for (;;)
    {
      try
      {
        f();
        paramTask = (HttpDownloader)paramTask;
        i1 = this.jdField_i_of_type_Int;
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("D");
        ((StringBuilder)localObject).append(paramTask.jdField_b_of_type_Int);
        ((StringBuilder)localObject).append("Fail");
        addDivideRunPos(i1, ((StringBuilder)localObject).toString());
        if ((paramTask.getStatus() == 5) && (getStatus() != 5))
        {
          this.jdField_e_of_type_Long = this.mDownloadedSize;
          if ((Apn.isNetworkConnected()) && (paramTask.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getSectionDownloadLen() == 0L))
          {
            i1 = 1;
            localObject = this.mDownloaders.iterator();
            int i2 = 0;
            int i3 = 1;
            if (((Iterator)localObject).hasNext())
            {
              HttpDownloader localHttpDownloader = (HttpDownloader)((Iterator)localObject).next();
              if ((localHttpDownloader == null) || (localHttpDownloader == paramTask) || (localHttpDownloader.jdField_a_of_type_Boolean) || (localHttpDownloader.getStatus() == 5) || (localHttpDownloader.getStatus() == 3)) {
                continue;
              }
              i2 += 1;
              i3 = 0;
              continue;
            }
            if ((i1 != 0) && (i2 <= 1) && (this.jdField_b_of_type_Int > 1) && (paramTask.isNetWorkError()))
            {
              addDivideRunPos(this.jdField_i_of_type_Int, "FReNR1");
              e();
              return;
            }
            if ((paramTask.jdField_c_of_type_Int == 44) && (!isRangeNotSupported()) && (paramTask.jdField_d_of_type_Boolean))
            {
              addDivideRunPos(this.jdField_i_of_type_Int, "FNMRReNR");
              e();
              return;
            }
            if (paramTask.jdField_c_of_type_Int == 8)
            {
              addDivideRunPos(this.jdField_i_of_type_Int, "FReST");
              handleTaskRestart();
              return;
            }
            if (i3 != 0)
            {
              if (retryWithNewUrl())
              {
                addDivideRunPos(this.jdField_i_of_type_Int, "FRetry");
                return;
              }
              if ((paramTask.jdField_c_of_type_Int == 34) && (!getIsTencentDNS()))
              {
                d();
                addDivideRunPos(this.jdField_i_of_type_Int, "FTDns");
              }
              else if ((paramTask.jdField_c_of_type_Int != 44) && ((paramTask.jdField_c_of_type_Int != 10) || (isRangeNotSupported())))
              {
                addDivideRunPos(this.jdField_i_of_type_Int, "TFail");
                handleTaskFailed(paramTask);
              }
              else
              {
                addDivideRunPos(this.jdField_i_of_type_Int, "FReNR");
                e();
              }
            }
          }
        }
        else
        {
          return;
        }
      }
      finally {}
      int i1 = 0;
    }
  }
  
  public void onTaskProgress(Task paramTask)
  {
    try
    {
      f();
      if (getStatus() != 5) {
        if (getStatus() == 11)
        {
          ((HttpDownloader)paramTask).cancel();
        }
        else if (!this.mCanceled)
        {
          setStatusWithoutDB((byte)2);
          paramTask = new ContentValues();
          paramTask.put("id", Integer.valueOf(getDownloadTaskId()));
          paramTask.put("status", Byte.valueOf(getStatus()));
          DownloadproviderHelper.a(paramTask);
          fireObserverEvent(this.mStatus);
        }
      }
      return;
    }
    finally {}
  }
  
  public void onTaskStarted(Task paramTask) {}
  
  protected void prepareRun()
  {
    if (this.mIsForground) {
      Thread.currentThread().setPriority(5);
    } else {
      Thread.currentThread().setPriority(1);
    }
    this.mIsMergeFile = false;
    a((byte)1);
    long l1 = this.mWrittenSize;
    this.mDownloadedSize = l1;
    this.jdField_e_of_type_Long = l1;
    this.jdField_f_of_type_Long = System.currentTimeMillis();
    this.jdField_c_of_type_Int = Apn.getApnTypeS();
  }
  
  public void quitWorkerThread()
  {
    Worker localWorker = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker;
    if (localWorker != null)
    {
      localWorker.a.quit();
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker = null;
    }
  }
  
  public void rename(String paramString)
  {
    synchronized (this.jdField_b_of_type_JavaLangObject)
    {
      Object localObject2 = this.mFileFolderPath;
      Object localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append(".");
      ((StringBuilder)localObject4).append(this.mFileName);
      ((StringBuilder)localObject4).append(".qbdltmp");
      localObject2 = new File((String)localObject2, ((StringBuilder)localObject4).toString());
      localObject4 = this.mFileFolderPath;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.mFileName);
      localStringBuilder.append(".qbdltmp");
      localObject4 = new File((String)localObject4, localStringBuilder.toString());
      if (((File)localObject4).exists()) {
        FileUtilsF.renameTo((File)localObject4, (File)localObject2);
      }
      localObject4 = this.mFileFolderPath;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(".");
      localStringBuilder.append(paramString);
      localStringBuilder.append(".qbdltmp");
      localObject4 = new File((String)localObject4, localStringBuilder.toString());
      ((File)localObject2).renameTo((File)localObject4);
      boolean bool = ((File)localObject4).exists();
      if (!bool) {
        try
        {
          ((File)localObject4).createNewFile();
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
      }
      try
      {
        this.jdField_a_of_type_JavaIoRandomAccessFile = new RandomAccessFile((File)localObject4, "rw");
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        localFileNotFoundException.printStackTrace();
      }
      Object localObject3 = new File(this.mFileFolderPath, this.mFileName);
      localObject4 = new File(this.mFileFolderPath, paramString);
      if (((File)localObject3).exists()) {
        ((File)localObject3).renameTo((File)localObject4);
      }
      synchronized (this.mReadWriteProgressLock)
      {
        localObject3 = this.mFileFolderPath;
        localObject4 = new StringBuilder();
        ((StringBuilder)localObject4).append(".");
        ((StringBuilder)localObject4).append(this.mFileName);
        ((StringBuilder)localObject4).append(".dltmp");
        localObject3 = new File((String)localObject3, ((StringBuilder)localObject4).toString());
        localObject4 = this.mFileFolderPath;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(".");
        localStringBuilder.append(paramString);
        localStringBuilder.append(".dltmp");
        ((File)localObject3).renameTo(new File((String)localObject4, localStringBuilder.toString()));
        this.mFileName = b(paramString);
        return;
      }
    }
  }
  
  protected boolean renameAfterFinish()
  {
    Object localObject1 = this.mFileFolderPath;
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(".");
    ((StringBuilder)localObject2).append(this.mFileName);
    ((StringBuilder)localObject2).append(".qbdltmp");
    localObject1 = new File((String)localObject1, ((StringBuilder)localObject2).toString());
    localObject2 = this.mFileFolderPath;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mFileName);
    localStringBuilder.append(".qbdltmp");
    localObject2 = new File((String)localObject2, localStringBuilder.toString());
    if (((File)localObject2).exists()) {
      FileUtilsF.renameTo((File)localObject2, (File)localObject1);
    }
    if (((File)localObject1).exists()) {
      return ((File)localObject1).renameTo(new File(this.mFileFolderPath, this.mFileName));
    }
    return true;
  }
  
  public void restoreConfigData()
  {
    synchronized (this.mReadWriteProgressLock)
    {
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.restoreConfigData(this.mFileFolderPath, this.mFileName, this.jdField_b_of_type_Int);
      return;
    }
  }
  
  protected void restoreDownloaders()
  {
    f();
    ArrayList localArrayList = new ArrayList();
    Object localObject = this.mDownloaders.iterator();
    while (((Iterator)localObject).hasNext()) {
      localArrayList.add((HttpDownloader)((Iterator)localObject).next());
    }
    this.mDownloaders.clear();
    boolean bool2 = false;
    int i1 = 0;
    boolean bool1 = true;
    while (i1 < this.jdField_b_of_type_Int)
    {
      DownloadSection localDownloadSection = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.getSection(i1);
      localObject = null;
      if (localArrayList.size() == this.jdField_b_of_type_Int) {
        localObject = (HttpDownloader)localArrayList.get(i1);
      }
      if (localObject != null) {
        localDownloadSection.setDownloadDataBuffer(((HttpDownloader)localObject).jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.dataBuffer());
      }
      this.mDownloaders.add(i1, new HttpDownloader(this, localDownloadSection, i1));
      localObject = (HttpDownloader)this.mDownloaders.get(i1);
      ((HttpDownloader)localObject).mIsDetectDownloader = false;
      if ((!localDownloadSection.isWriteFinish()) && (!localDownloadSection.isPending()))
      {
        if (this.mCanceled)
        {
          taskCanceled();
          return;
        }
        ((HttpDownloader)localObject).jdField_a_of_type_Boolean = false;
        ((HttpDownloader)localObject).start();
        bool1 = false;
      }
      else
      {
        ((HttpDownloader)localObject).jdField_a_of_type_Boolean = true;
      }
      i1 += 1;
    }
    if ((bool1) && (b()))
    {
      handleTaskRestart();
      bool1 = bool2;
    }
    if (bool1)
    {
      this.jdField_h_of_type_Boolean = bool1;
      tryToFinisDownloadTask();
    }
  }
  
  public boolean restoreRetryUrls(String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    int i1 = 0;
    if (bool) {
      return false;
    }
    int i2 = paramString.indexOf(';', 0);
    if (i2 != -1)
    {
      this.jdField_e_of_type_Int = Integer.parseInt((String)paramString.subSequence(0, i2));
      i1 = i2 + 1;
    }
    this.jdField_a_of_type_JavaUtilArrayList = new ArrayList();
    int i3;
    do
    {
      if (i2 == -1) {
        break;
      }
      i3 = paramString.indexOf(';', i1);
      i2 = i1;
      if (i3 != -1)
      {
        this.jdField_a_of_type_JavaUtilArrayList.add((String)paramString.subSequence(i1, i3));
        i2 = i3 + 1;
      }
      i1 = i2;
      i2 = i3;
    } while (i3 != -1);
    i1 = this.jdField_e_of_type_Int;
    if ((i1 != -1) && (i1 < this.jdField_a_of_type_JavaUtilArrayList.size())) {
      setDownloaderDownloadDataUrl((String)this.jdField_a_of_type_JavaUtilArrayList.get(this.jdField_e_of_type_Int));
    }
    return true;
  }
  
  public boolean retryWithNewUrl()
  {
    ArrayList localArrayList = this.jdField_a_of_type_JavaUtilArrayList;
    if (localArrayList != null)
    {
      if (localArrayList.size() == 0) {
        return false;
      }
      do
      {
        int i1 = this.jdField_e_of_type_Int + 1;
        this.jdField_e_of_type_Int = i1;
        if (i1 >= this.jdField_a_of_type_JavaUtilArrayList.size()) {
          break;
        }
      } while (TextUtils.isEmpty((CharSequence)this.jdField_a_of_type_JavaUtilArrayList.get(this.jdField_e_of_type_Int)));
      setDownloaderDownloadDataUrl((String)this.jdField_a_of_type_JavaUtilArrayList.get(this.jdField_e_of_type_Int));
      if (this.jdField_e_of_type_Int >= this.jdField_a_of_type_JavaUtilArrayList.size()) {
        return false;
      }
      setDownloaderDownloadDataUrl((String)this.jdField_a_of_type_JavaUtilArrayList.get(this.jdField_e_of_type_Int));
      this.mWrittenSize = 0L;
      this.mDownloadedSize = 0L;
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.clear(true);
      setStatus((byte)10);
      return true;
    }
    return false;
  }
  
  public void saveToDatabase()
  {
    DownloadproviderHelper.a(this);
  }
  
  protected void scheduelTasks(HttpDownloader paramHttpDownloader)
  {
    f();
    int i3;
    int i4;
    int i1;
    if (paramHttpDownloader != null)
    {
      if (!paramHttpDownloader.jdField_a_of_type_Boolean) {
        return;
      }
      i3 = paramHttpDownloader.jdField_b_of_type_Int;
      i4 = this.mDownloaders.size();
      boolean bool = paramHttpDownloader.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.hasRemain();
      int i2 = 0;
      Object localObject1;
      if (bool)
      {
        localObject1 = paramHttpDownloader.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection;
        ((DownloadSection)localObject1).mCurrentIndex += 1;
        ((DownloadSection)localObject1).changeCurrentSectionWritenPos(0L);
        ((DownloadSection)localObject1).setSectionDownloadLen(0L);
        paramHttpDownloader.jdField_a_of_type_Boolean = false;
        paramHttpDownloader.jdField_b_of_type_Boolean = false;
        paramHttpDownloader.setStatus((byte)1);
        paramHttpDownloader.start();
        i1 = this.jdField_i_of_type_Int;
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append(paramHttpDownloader.jdField_b_of_type_Int);
        ((StringBuilder)localObject1).append("DN");
        addDivideRunPos(i1, ((StringBuilder)localObject1).toString());
        return;
      }
      i1 = i2;
      if (a())
      {
        i1 = i2;
        if (this.jdField_b_of_type_Int > 1)
        {
          localObject1 = this.mDownloaders.iterator();
          long l3 = -1L;
          long l1 = -1L;
          while (((Iterator)localObject1).hasNext())
          {
            localObject2 = (HttpDownloader)((Iterator)localObject1).next();
            if (((HttpDownloader)localObject2).jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getCurrentSectionEndPos() > l1) {
              l1 = ((HttpDownloader)localObject2).jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getCurrentSectionEndPos();
            }
          }
          long l2;
          if (l1 < this.mFileSize - 1L)
          {
            l2 = l1 + 1L;
            l1 = a() + l2;
          }
          else
          {
            l2 = -1L;
            l1 = l3;
          }
          long l4 = this.mFileSize;
          l3 = l1;
          if (l1 > l4 - 1L) {
            l3 = l4 - 1L;
          }
          if (l3 == this.mFileSize - 1L) {
            a(false);
          }
          localObject1 = paramHttpDownloader.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection;
          Object localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(((DownloadSection)localObject1).getSectionStartPos());
          ((StringBuilder)localObject2).append(DownloadSection.sDivider);
          ((StringBuilder)localObject2).append(l2);
          ((DownloadSection)localObject1).setSectionStartPos(((StringBuilder)localObject2).toString());
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(((DownloadSection)localObject1).getSectionEndPos());
          ((StringBuilder)localObject2).append(DownloadSection.sDivider);
          ((StringBuilder)localObject2).append(l3);
          ((DownloadSection)localObject1).setSectionEndPos(((StringBuilder)localObject2).toString());
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(((DownloadSection)localObject1).getSectionWriteLen());
          ((StringBuilder)localObject2).append(DownloadSection.sDivider);
          ((StringBuilder)localObject2).append(0);
          ((DownloadSection)localObject1).setSectionWriteLen(((StringBuilder)localObject2).toString());
          ((DownloadSection)localObject1).setSectionDownloadLen(0L);
          ((DownloadSection)localObject1).mCurrentIndex += 1;
          paramHttpDownloader.jdField_a_of_type_Boolean = false;
          paramHttpDownloader.jdField_b_of_type_Boolean = false;
          paramHttpDownloader.setStatus((byte)1);
          paramHttpDownloader.start();
          i1 = this.jdField_i_of_type_Int;
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(paramHttpDownloader.jdField_b_of_type_Int);
          ((StringBuilder)localObject1).append("DN");
          addDivideRunPos(i1, ((StringBuilder)localObject1).toString());
          return;
        }
      }
    }
    try
    {
      while (i1 < this.jdField_b_of_type_Int)
      {
        paramHttpDownloader = (HttpDownloader)this.mDownloaders.get(i1);
        if ((i1 != i3) && (i1 < i4) && (i3 < i4) && (paramHttpDownloader.getRemainingLen() > jdField_b_of_type_Long))
        {
          a(paramHttpDownloader, (HttpDownloader)this.mDownloaders.get(i3));
          return;
        }
        i1 += 1;
      }
      return;
    }
    catch (Exception paramHttpDownloader) {}
    return;
  }
  
  public void setAnnotation(String paramString, boolean paramBoolean)
  {
    this.jdField_d_of_type_JavaLangString = paramString;
    if (paramBoolean)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setAnnotation(paramString, false);
        }
      }
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("annotation", this.jdField_d_of_type_JavaLangString);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setAnnotationExt(String paramString, boolean paramBoolean)
  {
    this.jdField_e_of_type_JavaLangString = paramString;
    if (paramBoolean)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setAnnotationExt(paramString, false);
        }
      }
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("annotationext", this.jdField_e_of_type_JavaLangString);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setCancelled(boolean paramBoolean)
  {
    this.mCanceled = paramBoolean;
  }
  
  public void setChannel(String paramString, boolean paramBoolean)
  {
    this.l = paramString;
    if (paramBoolean)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setChannel(paramString, false);
        }
      }
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("second_extend_7", this.l);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setChannelPkgName(String paramString, boolean paramBoolean)
  {
    this.n = paramString;
    if (paramBoolean)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setChannelPkgName(paramString, false);
        }
      }
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("second_extend_8", this.n);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setCookie(String paramString, boolean paramBoolean)
  {
    this.k = paramString;
    if (paramBoolean)
    {
      paramString = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (paramString != null)
      {
        paramString = paramString.a(getDownloadTaskId());
        if (paramString != null) {
          paramString.setCookie(this.k, false);
        }
      }
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("second_extend_5", this.k);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setCostTime(long paramLong, boolean paramBoolean)
  {
    this.mCostTime = paramLong;
    if (paramBoolean)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
      localContentValues.put("costtime", Long.valueOf(this.mCostTime));
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public void setCreateTime(long paramLong, boolean paramBoolean)
  {
    this.mCreateTime = paramLong;
    if (paramBoolean)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
      localContentValues.put("createdate", Long.valueOf(this.mCreateTime));
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public void setDeleted(boolean paramBoolean)
  {
    this.mIsDeleted = paramBoolean;
  }
  
  public void setDeltaUpdateFailed(boolean paramBoolean)
  {
    this.mFlagChanged = true;
    if (paramBoolean)
    {
      this.mFlag |= 0x400;
      return;
    }
    this.mFlag &= 0xFBFF;
  }
  
  public void setDoneTime(long paramLong, boolean paramBoolean)
  {
    this.jdField_c_of_type_Long = paramLong;
    if (paramBoolean)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
      localContentValues.put("donedate", Long.valueOf(this.jdField_c_of_type_Long));
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public void setDownloadApkRunPos(int paramInt)
  {
    this.jdField_g_of_type_Int = paramInt;
  }
  
  public void setDownloadApkType(int paramInt)
  {
    this.jdField_f_of_type_Int = paramInt;
  }
  
  public void setDownloadTaskId(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
    if (!sStatusLocks.containsKey(Integer.valueOf(paramInt))) {
      sStatusLocks.put(Integer.valueOf(paramInt), new Object());
    }
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSections.setDownloadTask(this);
  }
  
  public void setDownloadUrl(String paramString, boolean paramBoolean)
  {
    this.mDownloadUrl = paramString;
    if (paramBoolean)
    {
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("url", this.mDownloadUrl);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setDownloadedSize(long paramLong, boolean paramBoolean)
  {
    this.mDownloadedSize = paramLong;
    if (paramBoolean)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
      localContentValues.put("downloadedsize", Long.valueOf(this.mDownloadedSize));
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public void setDownloaderDownloadDataUrl(String paramString)
  {
    try
    {
      this.jdField_i_of_type_JavaLangString = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public void setETag(String paramString, boolean paramBoolean)
  {
    this.jdField_c_of_type_JavaLangString = paramString;
    if (paramBoolean)
    {
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("etag", this.jdField_c_of_type_JavaLangString);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setExtFlag(long paramLong, boolean paramBoolean)
  {
    this.jdField_d_of_type_Long = paramLong;
    if (paramBoolean)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setExtFlag(paramLong, false);
        }
      }
      if (getDownloadTaskId() != -1)
      {
        localObject = new ContentValues();
        ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
        ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
        DownloadproviderHelper.a((ContentValues)localObject);
      }
    }
  }
  
  public void setExtFlagAutoInstall(boolean paramBoolean)
  {
    setExtFlagAutoInstall(paramBoolean, true);
  }
  
  public void setExtFlagAutoInstall(boolean paramBoolean1, boolean paramBoolean2)
  {
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x4;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFFFB;
    }
    this.jdField_d_of_type_Long = l1;
    setExtFlag(this.jdField_d_of_type_Long, paramBoolean2);
  }
  
  public void setExtFlagContinueTask(boolean paramBoolean)
  {
    setExtFlagContinueTask(paramBoolean, true);
  }
  
  public void setExtFlagContinueTask(boolean paramBoolean1, boolean paramBoolean2)
  {
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 1L;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFFFE;
    }
    this.jdField_d_of_type_Long = l1;
    setExtFlag(this.jdField_d_of_type_Long, paramBoolean2);
  }
  
  public void setExtFlagCreateFirst(boolean paramBoolean1, boolean paramBoolean2)
  {
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x10;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFFEF;
    }
    this.jdField_d_of_type_Long = l1;
    setExtFlag(this.jdField_d_of_type_Long, paramBoolean2);
  }
  
  public void setExtFlagFileSizeReal(boolean paramBoolean)
  {
    setExtFlagFileSizeReal(paramBoolean, true);
  }
  
  public void setExtFlagFileSizeReal(boolean paramBoolean1, boolean paramBoolean2)
  {
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x8;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFFF7;
    }
    this.jdField_d_of_type_Long = l1;
    setExtFlag(this.jdField_d_of_type_Long, paramBoolean2);
  }
  
  public void setExtFlagNotifiedInstall(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x800;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFF7FF;
    }
    this.jdField_d_of_type_Long = l1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setExtFlag(this.jdField_d_of_type_Long, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setExtFlagPlugin(boolean paramBoolean1, boolean paramBoolean2)
  {
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x20;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFFDF;
    }
    this.jdField_d_of_type_Long = l1;
    setExtFlag(this.jdField_d_of_type_Long, paramBoolean2);
  }
  
  public void setExtFlagQBUpdateTask(boolean paramBoolean1, boolean paramBoolean2)
  {
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x40;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFFBF;
    }
    this.jdField_d_of_type_Long = l1;
    setExtFlag(this.jdField_d_of_type_Long, paramBoolean2);
  }
  
  public void setExtFlagShowToast(boolean paramBoolean)
  {
    setExtFlagShowToast(paramBoolean, true);
  }
  
  public void setExtFlagShowToast(boolean paramBoolean1, boolean paramBoolean2)
  {
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x2;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFFFD;
    }
    this.jdField_d_of_type_Long = l1;
    setExtFlag(this.jdField_d_of_type_Long, paramBoolean2);
  }
  
  public void setFileFolderPath(String paramString, boolean paramBoolean)
  {
    this.mFileFolderPath = paramString;
    if (paramBoolean)
    {
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("filefolderpath", this.mFileFolderPath);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setFileName(String paramString, boolean paramBoolean)
  {
    this.mFileName = b(paramString);
    if (paramBoolean)
    {
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("filename", this.mFileName);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setFileSize(long paramLong, boolean paramBoolean)
  {
    this.mFileSize = paramLong;
    if (paramBoolean)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
      localContentValues.put("totalsize", Long.valueOf(this.mFileSize));
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public void setFileSizeForHijack(long paramLong, boolean paramBoolean)
  {
    this.mFileSizeForHijack = paramLong;
    if (paramBoolean)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
      localContentValues.put("second_extend_2", Long.valueOf(this.mFileSizeForHijack));
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public void setFlag(int paramInt, boolean paramBoolean)
  {
    this.mFlagChanged = true;
    this.mFlag = paramInt;
    if (paramBoolean)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setFlag(paramInt, false);
        }
      }
      if (getDownloadTaskId() != -1)
      {
        localObject = new ContentValues();
        ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
        ((ContentValues)localObject).put("flag", Integer.valueOf(this.mFlag));
        DownloadproviderHelper.a((ContentValues)localObject);
      }
    }
  }
  
  public void setForground(boolean paramBoolean)
  {
    this.mIsForground = paramBoolean;
  }
  
  public void setFromTBS(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x4000;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFBFFF;
    }
    this.jdField_d_of_type_Long = l1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setExtFlag(this.jdField_d_of_type_Long, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setFromWeb(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x200;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFDFF;
    }
    this.jdField_d_of_type_Long = l1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setExtFlag(this.jdField_d_of_type_Long, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setFromWhere(String paramString, boolean paramBoolean)
  {
    this.q = paramString;
    if (paramBoolean)
    {
      paramString = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (paramString != null)
      {
        paramString = paramString.a(getDownloadTaskId());
        if (paramString != null) {
          paramString.setFromWhere(this.q, false);
        }
      }
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("second_extend_3", this.q);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setHasDelayNotifyInstall(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x8000;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFF7FFF;
    }
    this.jdField_d_of_type_Long = l1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setExtFlag(this.jdField_d_of_type_Long, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setHasLogo(boolean paramBoolean)
  {
    this.mFlagChanged = true;
    if (paramBoolean)
    {
      this.mFlag |= 0x80000;
      return;
    }
    this.mFlag &= 0xFFF7FFFF;
  }
  
  public void setHidden(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    if (paramBoolean1) {
      this.mFlag |= 0x20;
    } else {
      this.mFlag &= 0xFFFFFFDF;
    }
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setFlag(this.mFlag, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("flag", Integer.valueOf(this.mFlag));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setHost(String paramString, boolean paramBoolean)
  {
    this.m = paramString;
    if (paramBoolean)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setHost(paramString, false);
        }
      }
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("second_extend_6", this.m);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setIconUrl(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  public void setIconUrl(String paramString, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    if (paramBoolean)
    {
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("extend_9", this.jdField_a_of_type_JavaLangString);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setInstallByYYB(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x10000;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFEFFFF;
    }
    this.jdField_d_of_type_Long = l1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setExtFlag(this.jdField_d_of_type_Long, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setInstalled(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    if (paramBoolean1) {
      this.mFlag |= 0x40;
    } else {
      this.mFlag &= 0xFFFFFFBF;
    }
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setFlag(this.mFlag, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("flag", Integer.valueOf(this.mFlag));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setIsAppUpdateTask(boolean paramBoolean)
  {
    this.jdField_c_of_type_Boolean = paramBoolean;
  }
  
  public void setIsBackAutoTask(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    if (paramBoolean1) {
      this.mFlag |= 0x10000000;
    } else {
      this.mFlag &= 0xEFFFFFFF;
    }
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setFlag(this.mFlag, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("flag", Integer.valueOf(this.mFlag));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setIsForgroundwithOutRefreshThreadPriority(boolean paramBoolean)
  {
    this.mIsForground = paramBoolean;
  }
  
  public void setIsNeedRestartTask(boolean paramBoolean)
  {
    this.jdField_f_of_type_Boolean = paramBoolean;
  }
  
  public void setIsPostTask(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1) {
      this.mFlag |= 0x20000;
    } else {
      this.mFlag &= 0xFFFDFFFF;
    }
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setFlag(this.mFlag, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("flag", Integer.valueOf(this.mFlag));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setIsSupportResume(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mIsSupportResume = paramBoolean1;
    if (paramBoolean2)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
      localContentValues.put("supportresume", Boolean.valueOf(this.mIsSupportResume));
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public void setMaxThreadNum(int paramInt, boolean paramBoolean)
  {
    this.jdField_b_of_type_Int = paramInt;
    if (paramBoolean)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
      localContentValues.put("threadnum", Integer.valueOf(this.jdField_b_of_type_Int));
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public void setNeedNotification(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.jdField_d_of_type_Boolean = paramBoolean1;
  }
  
  public void setNotifyInstallCount(String paramString, boolean paramBoolean)
  {
    this.r = paramString;
    if (paramBoolean)
    {
      paramString = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (paramString != null)
      {
        paramString = paramString.a(getDownloadTaskId());
        if (paramString != null) {
          paramString.setNotifyInstallCount(this.r, false);
        }
      }
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("second_extend_4", this.r);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setOpenDirectyly(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    int i1;
    if (paramBoolean1) {
      i1 = this.mFlag | 0x200000;
    } else {
      i1 = this.mFlag & 0xFFDFFFFF;
    }
    this.mFlag = i1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setFlag(this.mFlag, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("flag", Integer.valueOf(this.mFlag));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setOriginalUrl(String paramString, boolean paramBoolean)
  {
    this.j = paramString;
    if (paramBoolean)
    {
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("extend_3", this.j);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setPackageName(String paramString, boolean paramBoolean)
  {
    this.jdField_f_of_type_JavaLangString = paramString;
    if (paramBoolean)
    {
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("extend_1", this.jdField_f_of_type_JavaLangString);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setPausedByNoWifi(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    int i1;
    if (paramBoolean1) {
      i1 = this.mFlag | 0x2000;
    } else {
      i1 = this.mFlag & 0xDFFF;
    }
    this.mFlag = i1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setFlag(this.mFlag, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("flag", Integer.valueOf(this.mFlag));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setPausedByUser(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x80;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFF7F;
    }
    this.jdField_d_of_type_Long = l1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setExtFlag(this.jdField_d_of_type_Long, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setPluginMd5(String paramString, boolean paramBoolean)
  {
    this.o = paramString;
    if (paramBoolean)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setPluginMd5(paramString, false);
        }
      }
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("second_extend_9", this.o);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setPostData(String paramString, boolean paramBoolean)
  {
    this.jdField_g_of_type_JavaLangString = paramString;
    if (paramBoolean)
    {
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("extend_4", this.jdField_g_of_type_JavaLangString);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setPreDownload(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    if (paramBoolean1) {
      this.mFlag |= 0x1000;
    } else {
      this.mFlag &= 0xEFFF;
    }
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setFlag(this.mFlag, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("flag", Integer.valueOf(this.mFlag));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setPrivateTask(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x200000;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFDFFFFF;
    }
    this.jdField_d_of_type_Long = l1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setExtFlag(this.jdField_d_of_type_Long, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setPrivateTaskRemoved()
  {
    this.mFlagChanged = true;
    this.jdField_d_of_type_Long |= 0x400000;
    Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
    if (localObject != null)
    {
      localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
      if (localObject != null) {
        ((DownloadTask)localObject).setExtFlag(this.jdField_d_of_type_Long, false);
      }
    }
    localObject = new ContentValues();
    ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
    ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
    DownloadproviderHelper.a((ContentValues)localObject);
  }
  
  public void setRangeNotSupported(boolean paramBoolean)
  {
    this.mFlagChanged = true;
    int i1;
    if (paramBoolean) {
      i1 = this.mFlag | 0x80;
    } else {
      i1 = this.mFlag & 0xFF7F;
    }
    this.mFlag = i1;
  }
  
  public void setRangeNotSupported(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    int i1;
    if (paramBoolean1) {
      i1 = this.mFlag | 0x80;
    } else {
      i1 = this.mFlag & 0xFF7F;
    }
    this.mFlag = i1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setFlag(this.mFlag, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("flag", Integer.valueOf(this.mFlag));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setReferer(String paramString, boolean paramBoolean)
  {
    this.jdField_b_of_type_JavaLangString = paramString;
    if (paramBoolean)
    {
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("referer", this.jdField_b_of_type_JavaLangString);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setRefreshFileNameAndSizeForPreDownloadTask(IRefreshFileNameAndSizeForPreDownloadTask paramIRefreshFileNameAndSizeForPreDownloadTask)
  {
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$IRefreshFileNameAndSizeForPreDownloadTask = paramIRefreshFileNameAndSizeForPreDownloadTask;
  }
  
  public void setReportString(String paramString, boolean paramBoolean)
  {
    this.p = paramString;
    if (paramBoolean)
    {
      paramString = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (paramString != null)
      {
        paramString = paramString.a(getDownloadTaskId());
        if (paramString != null) {
          paramString.setReportString(this.p, false);
        }
      }
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("second_extend_10", this.p);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setReproted(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x100;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFEFF;
    }
    this.jdField_d_of_type_Long = l1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setExtFlag(this.jdField_d_of_type_Long, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setRetryUrls(ArrayList<String> paramArrayList)
  {
    this.jdField_a_of_type_JavaUtilArrayList = paramArrayList;
    paramArrayList = this.jdField_a_of_type_JavaUtilArrayList;
    if (paramArrayList != null)
    {
      if (paramArrayList.size() == 0) {
        return;
      }
      this.jdField_e_of_type_Int = -1;
      return;
    }
  }
  
  public void setRetryUrls(ArrayList<String> paramArrayList, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaUtilArrayList = paramArrayList;
    paramArrayList = this.jdField_a_of_type_JavaUtilArrayList;
    if (paramArrayList != null)
    {
      if (paramArrayList.size() == 0) {
        return;
      }
      this.jdField_e_of_type_Int = -1;
      paramArrayList = new ContentValues();
      paramArrayList.put("id", Integer.valueOf(getDownloadTaskId()));
      paramArrayList.put("extend_8", getRetryUrlsStrFomat());
      DownloadproviderHelper.a(paramArrayList);
      return;
    }
  }
  
  public void setSaveFlowSize(long paramLong, boolean paramBoolean)
  {
    this.mSaveFlowSize = paramLong;
    if (paramBoolean)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
      localContentValues.put("extend_2", Long.valueOf(this.mSaveFlowSize));
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public void setStatus(byte paramByte)
  {
    a(paramByte, true);
  }
  
  public void setStatusInner(byte paramByte)
  {
    super.setStatus(paramByte);
  }
  
  public void setStatusWithoutDB(byte paramByte)
  {
    a(paramByte, false);
  }
  
  public void setTaskRunRightNow(boolean paramBoolean)
  {
    this.mFlagChanged = true;
    int i1;
    if (paramBoolean) {
      i1 = this.mFlag | 0x4;
    } else {
      i1 = this.mFlag & 0xFFFFFFFB;
    }
    this.mFlag = i1;
  }
  
  public void setTencentDNS(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mFlagChanged = true;
    long l1;
    if (paramBoolean1) {
      l1 = this.jdField_d_of_type_Long | 0x400;
    } else {
      l1 = this.jdField_d_of_type_Long & 0xFFFFFFFFFFFFFBFF;
    }
    this.jdField_d_of_type_Long = l1;
    if (paramBoolean2)
    {
      Object localObject = jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTaskManager;
      if (localObject != null)
      {
        localObject = ((DownloadTaskManager)localObject).a(getDownloadTaskId());
        if (localObject != null) {
          ((DownloadTask)localObject).setExtFlag(this.jdField_d_of_type_Long, false);
        }
      }
      localObject = new ContentValues();
      ((ContentValues)localObject).put("id", Integer.valueOf(getDownloadTaskId()));
      ((ContentValues)localObject).put("ext_flag", Long.valueOf(this.jdField_d_of_type_Long));
      DownloadproviderHelper.a((ContentValues)localObject);
    }
  }
  
  public void setVersionName(String paramString, boolean paramBoolean)
  {
    this.mVersionName = paramString;
    if (paramBoolean)
    {
      paramString = new ContentValues();
      paramString.put("id", Integer.valueOf(getDownloadTaskId()));
      paramString.put("versionname", this.mVersionName);
      DownloadproviderHelper.a(paramString);
    }
  }
  
  public void setWrittenSize(long paramLong, boolean paramBoolean)
  {
    this.mWrittenSize = paramLong;
    if (paramBoolean)
    {
      ContentValues localContentValues = new ContentValues();
      localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
      localContentValues.put("downloadsize", Long.valueOf(this.mWrittenSize));
      DownloadproviderHelper.a(localContentValues);
    }
  }
  
  public boolean statusCanResume()
  {
    return (getStatus() == 6) || (getStatus() == 5) || (getStatus() == 4);
  }
  
  public boolean statusIsComplete()
  {
    return getStatus() == 3;
  }
  
  protected void taskCanceled()
  {
    taskCanceled(true);
  }
  
  protected void taskCanceled(boolean paramBoolean)
  {
    if (!isTheDownloadSectionDetermined())
    {
      closeSavedFile();
      this.mWrittenSize = 0L;
      this.mDownloadedSize = 0L;
      if (getStatus() != 3) {
        DLMttFileUtils.deleteDownloadFileInThread(this.mFileFolderPath, this.mFileName);
      }
    }
    if (paramBoolean) {
      a((byte)6);
    }
    if ((getStatus() == 6) || (getStatus() == 11))
    {
      updateCostTime();
      DownloadEventManager.getInstance().notifyTaskCanceled(getTaskUrl());
    }
  }
  
  public boolean tryToFinisDownloadTask()
  {
    Worker localWorker = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker;
    if (localWorker == null) {
      return false;
    }
    try
    {
      localWorker.workHandler().removeMessages(6);
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker.workHandler().sendEmptyMessage(6);
      return true;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;) {}
    }
  }
  
  public void updateCostTime()
  {
    this.mCostTime += System.currentTimeMillis() - this.jdField_f_of_type_Long;
    setCostTime(this.mCostTime, true);
  }
  
  public void updateFileSize()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("id", Integer.valueOf(getDownloadTaskId()));
    localContentValues.put("totalsize", Long.valueOf(getTotalSize()));
    DownloadproviderHelper.a(localContentValues);
  }
  
  public void waitForAllBufferBeWritten()
  {
    while (!isBufferEmpty()) {
      try
      {
        Thread.sleep(100L);
      }
      catch (InterruptedException localInterruptedException)
      {
        localInterruptedException.printStackTrace();
      }
    }
  }
  
  /* Error */
  public boolean writeBuffer()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 394	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_h_of_type_Int	I
    //   4: aload_0
    //   5: getfield 415	com/tencent/mtt/browser/download/engine/DownloadTask:mDownloaders	Ljava/util/List;
    //   8: invokeinterface 1150 1 0
    //   13: if_icmplt +8 -> 21
    //   16: aload_0
    //   17: iconst_0
    //   18: putfield 394	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_h_of_type_Int	I
    //   21: aconst_null
    //   22: astore_2
    //   23: aload_2
    //   24: astore 5
    //   26: iconst_0
    //   27: istore_1
    //   28: aload_2
    //   29: astore_3
    //   30: aload 5
    //   32: astore 4
    //   34: iload_1
    //   35: aload_0
    //   36: getfield 415	com/tencent/mtt/browser/download/engine/DownloadTask:mDownloaders	Ljava/util/List;
    //   39: invokeinterface 1150 1 0
    //   44: if_icmpge +110 -> 154
    //   47: aload_0
    //   48: getfield 415	com/tencent/mtt/browser/download/engine/DownloadTask:mDownloaders	Ljava/util/List;
    //   51: aload_0
    //   52: getfield 394	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_h_of_type_Int	I
    //   55: invokeinterface 1153 2 0
    //   60: checkcast 885	com/tencent/mtt/browser/download/engine/HttpDownloader
    //   63: astore 4
    //   65: aload_2
    //   66: astore_3
    //   67: aload 4
    //   69: ifnull +15 -> 84
    //   72: aload 4
    //   74: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   77: invokevirtual 1298	com/tencent/mtt/browser/download/engine/DownloadSection:dataBuffer	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer;
    //   80: invokevirtual 2147	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:peekItem	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;
    //   83: astore_3
    //   84: aload_3
    //   85: astore_2
    //   86: aload_0
    //   87: aload_0
    //   88: getfield 394	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_h_of_type_Int	I
    //   91: iconst_1
    //   92: iadd
    //   93: putfield 394	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_h_of_type_Int	I
    //   96: aload_3
    //   97: astore_2
    //   98: aload_0
    //   99: getfield 394	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_h_of_type_Int	I
    //   102: aload_0
    //   103: getfield 415	com/tencent/mtt/browser/download/engine/DownloadTask:mDownloaders	Ljava/util/List;
    //   106: invokeinterface 1150 1 0
    //   111: if_icmpne +10 -> 121
    //   114: aload_3
    //   115: astore_2
    //   116: aload_0
    //   117: iconst_0
    //   118: putfield 394	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_h_of_type_Int	I
    //   121: aload_3
    //   122: ifnull +17 -> 139
    //   125: aload_3
    //   126: astore_2
    //   127: aload_3
    //   128: aload 4
    //   130: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   133: putfield 2152	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:mDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   136: goto +18 -> 154
    //   139: iload_1
    //   140: iconst_1
    //   141: iadd
    //   142: istore_1
    //   143: aload_3
    //   144: astore_2
    //   145: aload 4
    //   147: astore 5
    //   149: goto -121 -> 28
    //   152: aload_2
    //   153: astore_3
    //   154: aload_3
    //   155: ifnonnull +5 -> 160
    //   158: iconst_0
    //   159: ireturn
    //   160: aload_0
    //   161: getfield 356	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_b_of_type_JavaLangObject	Ljava/lang/Object;
    //   164: astore_2
    //   165: aload_2
    //   166: monitorenter
    //   167: aload_0
    //   168: invokevirtual 553	com/tencent/mtt/browser/download/engine/DownloadTask:getStatus	()B
    //   171: iconst_5
    //   172: if_icmpne +23 -> 195
    //   175: aload 4
    //   177: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   180: invokevirtual 1298	com/tencent/mtt/browser/download/engine/DownloadSection:dataBuffer	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer;
    //   183: invokevirtual 2155	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:removeItem	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;
    //   186: pop
    //   187: aload_3
    //   188: invokestatic 2159	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:recyleBuffer	(Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;)V
    //   191: aload_2
    //   192: monitorexit
    //   193: iconst_1
    //   194: ireturn
    //   195: aload_0
    //   196: getfield 1183	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_a_of_type_JavaIoRandomAccessFile	Ljava/io/RandomAccessFile;
    //   199: ifnonnull +11 -> 210
    //   202: aload_0
    //   203: getstatic 299	com/tencent/mtt/browser/download/engine/DownloadTask:NEW_DOWNLOAD_FILE_MODE_IN_WRITE_FILE	I
    //   206: invokevirtual 1093	com/tencent/mtt/browser/download/engine/DownloadTask:newSavedFile	(I)Z
    //   209: pop
    //   210: aload_0
    //   211: getfield 1183	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_a_of_type_JavaIoRandomAccessFile	Ljava/io/RandomAccessFile;
    //   214: ifnull +119 -> 333
    //   217: aload_0
    //   218: getfield 1183	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_a_of_type_JavaIoRandomAccessFile	Ljava/io/RandomAccessFile;
    //   221: aload_3
    //   222: getfield 2162	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:offset	J
    //   225: invokevirtual 2165	java/io/RandomAccessFile:seek	(J)V
    //   228: aload_0
    //   229: getfield 1183	com/tencent/mtt/browser/download/engine/DownloadTask:jdField_a_of_type_JavaIoRandomAccessFile	Ljava/io/RandomAccessFile;
    //   232: aload_3
    //   233: getfield 2169	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:data	[B
    //   236: iconst_0
    //   237: aload_3
    //   238: getfield 2172	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:len	I
    //   241: invokevirtual 2176	java/io/RandomAccessFile:write	([BII)V
    //   244: aload_0
    //   245: getfield 340	com/tencent/mtt/browser/download/engine/DownloadTask:mReadWriteProgressLock	Ljava/lang/Object;
    //   248: astore 5
    //   250: aload 5
    //   252: monitorenter
    //   253: aload_3
    //   254: getfield 2152	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:mDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   257: aload_3
    //   258: getfield 2179	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:mBufferSectionIndex	I
    //   261: aload_3
    //   262: getfield 2172	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:len	I
    //   265: i2l
    //   266: invokevirtual 2182	com/tencent/mtt/browser/download/engine/DownloadSection:addSectionWriteLen	(IJ)V
    //   269: aload_0
    //   270: getfield 340	com/tencent/mtt/browser/download/engine/DownloadTask:mReadWriteProgressLock	Ljava/lang/Object;
    //   273: invokevirtual 2185	java/lang/Object:notifyAll	()V
    //   276: aload 5
    //   278: monitorexit
    //   279: aload_0
    //   280: aload_0
    //   281: getfield 516	com/tencent/mtt/browser/download/engine/DownloadTask:mWrittenSize	J
    //   284: aload_3
    //   285: getfield 2172	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:len	I
    //   288: i2l
    //   289: ladd
    //   290: putfield 516	com/tencent/mtt/browser/download/engine/DownloadTask:mWrittenSize	J
    //   293: aload_0
    //   294: getfield 512	com/tencent/mtt/browser/download/engine/DownloadTask:mFileSize	J
    //   297: lconst_0
    //   298: lcmp
    //   299: ifle +34 -> 333
    //   302: aload_0
    //   303: getfield 516	com/tencent/mtt/browser/download/engine/DownloadTask:mWrittenSize	J
    //   306: aload_0
    //   307: getfield 512	com/tencent/mtt/browser/download/engine/DownloadTask:mFileSize	J
    //   310: lcmp
    //   311: ifle +22 -> 333
    //   314: aload_0
    //   315: aload_0
    //   316: getfield 512	com/tencent/mtt/browser/download/engine/DownloadTask:mFileSize	J
    //   319: putfield 516	com/tencent/mtt/browser/download/engine/DownloadTask:mWrittenSize	J
    //   322: goto +11 -> 333
    //   325: astore 6
    //   327: aload 5
    //   329: monitorexit
    //   330: aload 6
    //   332: athrow
    //   333: aload 4
    //   335: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   338: invokevirtual 1298	com/tencent/mtt/browser/download/engine/DownloadSection:dataBuffer	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer;
    //   341: invokevirtual 2155	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:removeItem	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;
    //   344: pop
    //   345: aload_3
    //   346: invokestatic 2159	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:recyleBuffer	(Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;)V
    //   349: aload_2
    //   350: monitorexit
    //   351: iconst_1
    //   352: ireturn
    //   353: astore 5
    //   355: aload_2
    //   356: monitorexit
    //   357: aload 5
    //   359: athrow
    //   360: astore_2
    //   361: aload_0
    //   362: aload_2
    //   363: aload_3
    //   364: getfield 2172	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:len	I
    //   367: i2l
    //   368: invokevirtual 2187	com/tencent/mtt/browser/download/engine/DownloadTask:a	(Ljava/lang/Exception;J)V
    //   371: aload 4
    //   373: getfield 889	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   376: invokevirtual 1298	com/tencent/mtt/browser/download/engine/DownloadSection:dataBuffer	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer;
    //   379: invokevirtual 2155	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:removeItem	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;
    //   382: pop
    //   383: aload_3
    //   384: invokestatic 2159	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:recyleBuffer	(Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;)V
    //   387: aload_2
    //   388: invokevirtual 1230	java/lang/Exception:printStackTrace	()V
    //   391: iconst_1
    //   392: ireturn
    //   393: astore_3
    //   394: aload_2
    //   395: astore_3
    //   396: aload 5
    //   398: astore 4
    //   400: goto -246 -> 154
    //   403: astore_3
    //   404: goto -252 -> 152
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	407	0	this	DownloadTask
    //   27	116	1	i1	int
    //   360	35	2	localException1	Exception
    //   29	355	3	localObject2	Object
    //   393	1	3	localIndexOutOfBoundsException1	IndexOutOfBoundsException
    //   395	1	3	localException2	Exception
    //   403	1	3	localIndexOutOfBoundsException2	IndexOutOfBoundsException
    //   32	367	4	localObject3	Object
    //   353	44	5	localObject5	Object
    //   325	6	6	localObject6	Object
    // Exception table:
    //   from	to	target	type
    //   253	279	325	finally
    //   327	330	325	finally
    //   167	193	353	finally
    //   195	210	353	finally
    //   210	253	353	finally
    //   279	322	353	finally
    //   330	333	353	finally
    //   333	351	353	finally
    //   355	357	353	finally
    //   160	167	360	java/lang/Exception
    //   357	360	360	java/lang/Exception
    //   34	65	393	java/lang/IndexOutOfBoundsException
    //   72	84	403	java/lang/IndexOutOfBoundsException
    //   86	96	403	java/lang/IndexOutOfBoundsException
    //   98	114	403	java/lang/IndexOutOfBoundsException
    //   116	121	403	java/lang/IndexOutOfBoundsException
    //   127	136	403	java/lang/IndexOutOfBoundsException
  }
  
  public static abstract interface IRefreshFileNameAndSizeForPreDownloadTask
  {
    public abstract void refreshFileNameAndSizeForPreDownloadTask(DownloadTask paramDownloadTask);
  }
  
  class MarketTaskNotSupportResumeException
    extends Exception
  {
    MarketTaskNotSupportResumeException(String paramString)
    {
      super();
    }
  }
  
  public class Worker
  {
    public static final String ADD_DOWNLOADEDSIZE_THREAD = "add_downloadedsize_thread";
    public static final int DOWNLOAD_WORKER_CALL_BACK_FROM_DOWNLOADER = 5;
    public static final int DOWNLOAD_WORKER_DOWNLOADER_DOWNLOAD_SIZE_FROM_DOWNLOADER = 3;
    public static final int DOWNLOAD_WORKER_FINISH_DOWNLOAD_TASK_FROM_DOWNORWRITE = 6;
    public static final int DOWNLOAD_WORKER_FIRST_SECTION_BACK_FROM_DOWNLOADER = 4;
    public static final int DOWNLOAD_WORKER_START_TASK_RUN_FROM_SCHEDULE = 1;
    public static final String WORKER_THREAD = "worker_thread";
    Handler jdField_a_of_type_AndroidOsHandler;
    HandlerThread jdField_a_of_type_AndroidOsHandlerThread;
    a jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker$a;
    
    public Worker()
    {
      DownloadTask.this.g = System.currentTimeMillis();
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker$a = new a();
      this.jdField_a_of_type_AndroidOsHandlerThread = new HandlerThread("worker_thread", 10);
      this.jdField_a_of_type_AndroidOsHandlerThread.start();
      this.jdField_a_of_type_AndroidOsHandler = new Handler(this.jdField_a_of_type_AndroidOsHandlerThread.getLooper(), this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask$Worker$a);
    }
    
    public DownloadTask getTask()
    {
      return DownloadTask.this;
    }
    
    public boolean isWorkerForTask(int paramInt)
    {
      DownloadTask localDownloadTask = DownloadTask.this;
      return (localDownloadTask != null) && (localDownloadTask.getDownloadTaskId() == paramInt);
    }
    
    public void startTaskRun()
    {
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessage(1);
    }
    
    public Handler workHandler()
    {
      return this.jdField_a_of_type_AndroidOsHandler;
    }
    
    class a
      implements Handler.Callback
    {
      a() {}
      
      public boolean handleMessage(Message paramMessage)
      {
        int i = paramMessage.what;
        if (i != 1) {
          switch (i)
          {
          default: 
            break;
          case 6: 
            DownloadTask.this.a();
            break;
          case 5: 
            if (!(paramMessage.obj instanceof HttpDownloader)) {
              break;
            }
            ((HttpDownloader)paramMessage.obj).superFireObserverEvent();
            break;
          case 4: 
            if (!(paramMessage.obj instanceof DownloadTask.b)) {
              break;
            }
            i = ((DownloadTask.b)paramMessage.obj).jdField_a_of_type_Int;
            if (DownloadTask.this.getDownloadTaskId() != i) {
              break;
            }
            DownloadTask.this.a(((DownloadTask.b)paramMessage.obj).jdField_a_of_type_JavaLangString, ((DownloadTask.b)paramMessage.obj).jdField_a_of_type_ComTencentCommonHttpMttResponse);
            break;
          case 3: 
            if (!(paramMessage.obj instanceof DownloadTask.a)) {
              break;
            }
            i = ((DownloadTask.a)paramMessage.obj).jdField_a_of_type_Int;
            long l1 = ((DownloadTask.a)paramMessage.obj).jdField_a_of_type_Long;
            if (DownloadTask.this.getDownloadTaskId() != i) {
              break;
            }
            long l2 = DownloadTask.this.mDownloadedSize + l1;
            l1 = l2;
            if (l2 > 0L)
            {
              l1 = l2;
              if (l2 > DownloadTask.this.getTotalSize())
              {
                l1 = l2;
                if (DownloadTask.this.getTotalSize() > 0L) {
                  l1 = DownloadTask.this.getTotalSize();
                }
              }
            }
            DownloadTask.this.mDownloadedSize = l1;
            break;
          }
        } else {
          DownloadTask.this.doRun();
        }
        return false;
      }
    }
  }
  
  static class a
  {
    public int a;
    public long a;
  }
  
  static class b
  {
    public int a;
    public MttResponse a;
    public String a;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */