package com.tencent.tbs.common.download.qb;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.HashMap;
import java.util.Map;

public class TbsDownloadHelperConfig
{
  static final int DECODE_FAILED = 205;
  static final int DECODE_KEYFILE_NOTEXIST = 203;
  static final int DECODE_PACKAGEFILE_NOTEXIST = 202;
  static final int DECODE_READ_KEY_FAILED = 204;
  static final int DECODE_SUCCESS = 200;
  static final int DECODE_TARGETFILE_NOTEXIST = 201;
  private static final int DEFAULT_DOWNLOAD_FAILED_MAX_RETRYTIMES = 100;
  private static final int DEFAULT_DOWNLOAD_MAX_FLOW = 60;
  private static final int DEFAULT_DOWNLOAD_MIN_FREE_SPACE = 0;
  private static final long DEFAULT_DOWNLOAD_SINGLE_TIMEOUT = 1200000L;
  private static final int DEFAULT_DOWNLOAD_SUCCESS_MAX_RETRYTIMES = 3;
  public static final long DEFAULT_RETRY_INTERVAL_SEC = 86400L;
  static final int DOWNLOADING = 103;
  static final int DOWNLOAD_SUCCESS = 100;
  static final int EMPTY_DOWNLOADURL = 102;
  static final int FILE_EXIST = 104;
  static final int NEWWORK_UNABLE = 105;
  static final int NONEED_UPGRADE = 101;
  static final int RENAME_FAILED = 106;
  private static String TBS_CFG_FILE = "tbs_download_config_helper";
  static final int VERIFY_FAILED = 107;
  private Context mAppContext;
  public SharedPreferences mPreferences;
  Map<String, Object> mSyncMap = new HashMap();
  
  public TbsDownloadHelperConfig(Context paramContext, String paramString)
  {
    TBS_CFG_FILE = paramString;
    this.mPreferences = paramContext.getSharedPreferences(TBS_CFG_FILE, 4);
    this.mAppContext = paramContext.getApplicationContext();
  }
  
  public void clear()
  {
    try
    {
      this.mSyncMap.clear();
      SharedPreferences.Editor localEditor = this.mPreferences.edit();
      localEditor.clear();
      localEditor.commit();
      return;
    }
    catch (Exception localException) {}
  }
  
  /* Error */
  public void commit()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 83	com/tencent/tbs/common/download/qb/TbsDownloadHelperConfig:mPreferences	Landroid/content/SharedPreferences;
    //   6: invokeinterface 102 1 0
    //   11: astore_1
    //   12: aload_0
    //   13: getfield 73	com/tencent/tbs/common/download/qb/TbsDownloadHelperConfig:mSyncMap	Ljava/util/Map;
    //   16: invokeinterface 114 1 0
    //   21: invokeinterface 120 1 0
    //   26: astore_2
    //   27: aload_2
    //   28: invokeinterface 125 1 0
    //   33: ifeq +157 -> 190
    //   36: aload_2
    //   37: invokeinterface 129 1 0
    //   42: checkcast 131	java/lang/String
    //   45: astore_3
    //   46: aload_0
    //   47: getfield 73	com/tencent/tbs/common/download/qb/TbsDownloadHelperConfig:mSyncMap	Ljava/util/Map;
    //   50: aload_3
    //   51: invokeinterface 135 2 0
    //   56: astore 4
    //   58: aload 4
    //   60: instanceof 131
    //   63: ifeq +19 -> 82
    //   66: aload_1
    //   67: aload_3
    //   68: aload 4
    //   70: checkcast 131	java/lang/String
    //   73: invokeinterface 139 3 0
    //   78: pop
    //   79: goto -52 -> 27
    //   82: aload 4
    //   84: instanceof 141
    //   87: ifeq +22 -> 109
    //   90: aload_1
    //   91: aload_3
    //   92: aload 4
    //   94: checkcast 141	java/lang/Boolean
    //   97: invokevirtual 144	java/lang/Boolean:booleanValue	()Z
    //   100: invokeinterface 148 3 0
    //   105: pop
    //   106: goto -79 -> 27
    //   109: aload 4
    //   111: instanceof 150
    //   114: ifeq +22 -> 136
    //   117: aload_1
    //   118: aload_3
    //   119: aload 4
    //   121: checkcast 150	java/lang/Long
    //   124: invokevirtual 154	java/lang/Long:longValue	()J
    //   127: invokeinterface 158 4 0
    //   132: pop
    //   133: goto -106 -> 27
    //   136: aload 4
    //   138: instanceof 160
    //   141: ifeq +22 -> 163
    //   144: aload_1
    //   145: aload_3
    //   146: aload 4
    //   148: checkcast 160	java/lang/Integer
    //   151: invokevirtual 164	java/lang/Integer:intValue	()I
    //   154: invokeinterface 168 3 0
    //   159: pop
    //   160: goto -133 -> 27
    //   163: aload 4
    //   165: instanceof 170
    //   168: ifeq -141 -> 27
    //   171: aload_1
    //   172: aload_3
    //   173: aload 4
    //   175: checkcast 170	java/lang/Float
    //   178: invokevirtual 174	java/lang/Float:floatValue	()F
    //   181: invokeinterface 178 3 0
    //   186: pop
    //   187: goto -160 -> 27
    //   190: aload_1
    //   191: invokeinterface 110 1 0
    //   196: pop
    //   197: aload_0
    //   198: getfield 73	com/tencent/tbs/common/download/qb/TbsDownloadHelperConfig:mSyncMap	Ljava/util/Map;
    //   201: invokeinterface 96 1 0
    //   206: goto +12 -> 218
    //   209: astore_1
    //   210: goto +11 -> 221
    //   213: astore_1
    //   214: aload_1
    //   215: invokevirtual 181	java/lang/Exception:printStackTrace	()V
    //   218: aload_0
    //   219: monitorexit
    //   220: return
    //   221: aload_0
    //   222: monitorexit
    //   223: aload_1
    //   224: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	225	0	this	TbsDownloadHelperConfig
    //   11	180	1	localEditor	SharedPreferences.Editor
    //   209	1	1	localObject1	Object
    //   213	11	1	localException	Exception
    //   26	11	2	localIterator	java.util.Iterator
    //   45	128	3	str	String
    //   56	118	4	localObject2	Object
    // Exception table:
    //   from	to	target	type
    //   2	27	209	finally
    //   27	79	209	finally
    //   82	106	209	finally
    //   109	133	209	finally
    //   136	160	209	finally
    //   163	187	209	finally
    //   190	206	209	finally
    //   214	218	209	finally
    //   2	27	213	java/lang/Exception
    //   27	79	213	java/lang/Exception
    //   82	106	213	java/lang/Exception
    //   109	133	213	java/lang/Exception
    //   136	160	213	java/lang/Exception
    //   163	187	213	java/lang/Exception
    //   190	206	213	java/lang/Exception
  }
  
  /* Error */
  public long getBackupCoreMinFreeSpace()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 83	com/tencent/tbs/common/download/qb/TbsDownloadHelperConfig:mPreferences	Landroid/content/SharedPreferences;
    //   6: ldc -72
    //   8: iconst_0
    //   9: invokeinterface 188 3 0
    //   14: istore_2
    //   15: iload_2
    //   16: istore_1
    //   17: iload_2
    //   18: ifne +5 -> 23
    //   21: iconst_0
    //   22: istore_1
    //   23: iload_1
    //   24: sipush 1024
    //   27: imul
    //   28: i2l
    //   29: lstore_3
    //   30: aload_0
    //   31: monitorexit
    //   32: lload_3
    //   33: ldc2_w 189
    //   36: lmul
    //   37: ldc2_w 191
    //   40: ladd
    //   41: lreturn
    //   42: astore 5
    //   44: aload_0
    //   45: monitorexit
    //   46: aload 5
    //   48: athrow
    //   49: aload_0
    //   50: monitorexit
    //   51: lconst_0
    //   52: lreturn
    //   53: astore 5
    //   55: goto -6 -> 49
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	58	0	this	TbsDownloadHelperConfig
    //   16	12	1	i	int
    //   14	4	2	j	int
    //   29	4	3	l	long
    //   42	5	5	localObject	Object
    //   53	1	5	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   2	15	42	finally
    //   2	15	53	java/lang/Exception
  }
  
  public long getDownloadMaxflow()
  {
    try
    {
      int j = this.mPreferences.getInt("tbs_download_maxflow", 0);
      int i = j;
      if (j == 0) {
        i = 60;
      }
      long l = i * 1024;
      return l * 1024L;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getDownloadVersion()
  {
    try
    {
      int i = this.mPreferences.getInt("tbs_download_version", 0);
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getLocalVersion()
  {
    try
    {
      int i = this.mPreferences.getInt("tbs_local_version", 0);
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public int getPatchFileSize()
  {
    try
    {
      int i = this.mPreferences.getInt("patch_filesize", 0);
      return i;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public long getRetryInterval()
  {
    try
    {
      long l = this.mPreferences.getLong("retry_interval", 86400L);
      return l;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static abstract interface TbsConfigKey
  {
    public static final String KEY_APP_METADATA = "app_metadata";
    public static final String KEY_APP_VERSIONCODE = "app_versioncode";
    public static final String KEY_APP_VERSIONNAME = "app_versionname";
    public static final String KEY_DOWNLOADURL_LIST = "tbs_downloadurl_list";
    public static final String KEY_DOWNLOAD_FAILED_MAX_RETRYTIMES = "tbs_download_failed_max_retrytimes";
    public static final String KEY_DOWNLOAD_INTERRUPT_CODE = "tbs_download_interrupt_code";
    public static final String KEY_DOWNLOAD_INTERRUPT_TIME = "tbs_download_interrupt_time";
    public static final String KEY_DOWNLOAD_MAXFLOW = "tbs_download_maxflow";
    public static final String KEY_DOWNLOAD_MIN_FREE_SPACE = "tbs_download_min_free_space";
    public static final String KEY_DOWNLOAD_SINGLE_TIMEOUT = "tbs_single_timeout";
    public static final String KEY_DOWNLOAD_SUCCESS_MAX_RETRYTIMES = "tbs_download_success_max_retrytimes";
    public static final String KEY_INSTALL_INTERRUPT_CODE = "tbs_install_interrupt_code";
    public static final String KEY_IS_OVERSEA = "is_oversea";
    public static final String KEY_LAST_CHECK = "last_check";
    public static final String KEY_NEEDDOWNLOAD = "tbs_needdownload";
    public static final String KEY_PATCH_FILE_SIZE = "patch_filesize";
    public static final String KEY_QB_SEEK = "key_seek";
    public static final String KEY_RESPONSECODE = "tbs_responsecode";
    public static final String KEY_RETRY_INTERVAL = "retry_interval";
    public static final String KEY_TBSAPKFILESIZE = "tbs_apkfilesize";
    public static final String KEY_TBSAPK_MD5 = "tbs_apk_md5";
    public static final String KEY_TBSDOWNLOADURL = "tbs_downloadurl";
    public static final String KEY_TBSDOWNLOAD_FLOW = "tbs_downloadflow";
    public static final String KEY_TBSDOWNLOAD_STARTTIME = "tbs_downloadstarttime";
    public static final String KEY_TBS_CORE_LOAD_RENAME_FILE_LOCK_ENABLE = "tbs_core_load_rename_file_lock_enable";
    public static final String KEY_TBS_DOWNLOAD_V = "tbs_download_version";
    public static final String KEY_TBS_LOCAL_V = "tbs_local_version";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\TbsDownloadHelperConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */