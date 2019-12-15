package com.tencent.tbs.common.utils;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class TbsFileUtils
{
  public static final String DIR_JSAPI_CONF = "tbs_jsApi";
  private static final String FILE_ADBLOCK_SOURCE = "adblocksource.dat";
  private static final String FILE_DOMAIN_DATA;
  public static final String FILE_JSAPI_WHITELIST_CONF;
  private static final String FILE_JSON_DOMAIN_DATA = "domain_list_file_new.inf";
  private static final String FILE_JSON_DOWNLOAD_APP_DATA = "download_app_date_file.inf";
  private static final String FILE_JSON_TBS_AD_DATA = "tbs_ad_data_file.inf";
  private static final String FILE_JSON_USER_DATA = "user_data_file_new.inf";
  private static final String FILE_LBS_DOMAIN = "lbsdomain";
  private static final String FILE_PREFIX = "com.tencent.tbs";
  private static final String FILE_SAFJ = "safj.inf";
  private static final String FILE_SAFM = "safm.inf";
  private static final String FILE_SIMPLE_QB_STAT_INFO_PREFIX;
  private static final String FILE_STAT_INFO_POSTFIX = ".inf";
  private static final String FILE_STAT_INFO_PREFIX = "tbs_use";
  private static final String FILE_SUBSET_ADRULE = "subsetadrule.inf";
  private static final String FILE_TBS_PAGE_LOAD_INFO_POSTFIX = ".inf";
  private static final String FILE_TBS_PAGE_LOAD_INFO_PREFIX;
  private static final String FILE_USER_BASE_INFO;
  private static final String FILE_USER_INFO;
  private static final String FILE_WUP_STAT_REQ = "wup_stat_req_file";
  
  static
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(ThreadUtils.getCurrentProcessNameIngoreColon(TbsBaseModuleShell.getCallerContext()));
    localStringBuilder.append("_tbs_user.inf");
    FILE_USER_INFO = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(ThreadUtils.getCurrentProcessNameIngoreColon(TbsBaseModuleShell.getCallerContext()));
    localStringBuilder.append("_tbs_userBase.inf");
    FILE_USER_BASE_INFO = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(ThreadUtils.getCurrentProcessNameIngoreColon(TbsBaseModuleShell.getCallerContext()));
    localStringBuilder.append("_tbs_jsApiDomain.ini");
    FILE_JSAPI_WHITELIST_CONF = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(ThreadUtils.getCurrentProcessNameIngoreColon(TbsBaseModuleShell.getCallerContext()));
    localStringBuilder.append("_domain.inf");
    FILE_DOMAIN_DATA = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(ThreadUtils.getCurrentProcessNameIngoreColon(TbsBaseModuleShell.getCallerContext()));
    localStringBuilder.append("_tbs_pli");
    FILE_TBS_PAGE_LOAD_INFO_PREFIX = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(ThreadUtils.getCurrentProcessNameIngoreColon(TbsBaseModuleShell.getCallerContext()));
    localStringBuilder.append("_use_for_sqb");
    FILE_SIMPLE_QB_STAT_INFO_PREFIX = localStringBuilder.toString();
  }
  
  public static boolean deleteLbsDomain(Context paramContext)
  {
    try
    {
      paramContext = getLbsDomainFile(paramContext);
      if (paramContext.exists()) {
        paramContext.delete();
      }
      return true;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return false;
  }
  
  public static File getAdBlockSourceFile(Context paramContext)
  {
    return new File(FileUtils.getCacheDir(paramContext), "adblocksource.dat");
  }
  
  public static File getDomainFile(Context paramContext, byte paramByte)
  {
    paramContext = FileUtils.getDataDir(paramContext);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramByte);
    localStringBuilder.append(FILE_DOMAIN_DATA);
    return new File(paramContext, localStringBuilder.toString());
  }
  
  public static File getDomainJsonFile(Context paramContext, byte paramByte)
  {
    if (paramByte == TbsBaseModuleShell.REQ_SRC_MINI_QB) {
      return new File(FileUtils.getDataDir(paramContext), "com.tencent.tbs_miniqb_domain_list_file_new.inf");
    }
    if (paramByte == TbsBaseModuleShell.REQ_SRC_TBS_VIDEO) {
      return new File(FileUtils.getDataDir(paramContext), "com.tencent.tbs_video_domain_list_file_new.inf");
    }
    return new File(FileUtils.getDataDir(paramContext), "com.tencent.tbs_domain_list_file_new.inf");
  }
  
  public static File getDownloadAppJsonFile(Context paramContext)
  {
    return new File(FileUtils.getDataDir(paramContext), "com.tencent.tbs_download_app_date_file.inf");
  }
  
  public static Vector<String> getExternalSDcardPath()
  {
    Vector localVector = new Vector();
    ArrayList localArrayList = SdcardUtils.getStorageInfo();
    if ((localArrayList != null) && (!localArrayList.isEmpty()))
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        SdcardUtils.StorageInfo localStorageInfo = (SdcardUtils.StorageInfo)localIterator.next();
        if ((!TextUtils.isEmpty(localStorageInfo.path)) && (localStorageInfo.isExists)) {
          localVector.add(localStorageInfo.path);
        }
      }
    }
    if (((localArrayList == null) || (localArrayList.size() <= 0)) && (DeviceUtils.sLessHoneycomb)) {
      localVector.addAll(SdcardUtils.SDCardDetector_VoldFstab.getMountedPath());
    }
    return localVector;
  }
  
  public static String getJsApiWhiteListDataFileName()
  {
    return FILE_JSAPI_WHITELIST_CONF;
  }
  
  public static File getLbsDomainFile(Context paramContext)
  {
    return new File(FileUtils.getDataDir(paramContext), "lbsdomain");
  }
  
  /* Error */
  public static String getLbsDomains(Context paramContext)
  {
    // Byte code:
    //   0: aload_0
    //   1: invokestatic 115	com/tencent/tbs/common/utils/TbsFileUtils:getLbsDomainFile	(Landroid/content/Context;)Ljava/io/File;
    //   4: astore_0
    //   5: aload_0
    //   6: invokevirtual 121	java/io/File:exists	()Z
    //   9: ifeq +75 -> 84
    //   12: new 236	java/io/FileInputStream
    //   15: dup
    //   16: aload_0
    //   17: invokespecial 239	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   20: astore_1
    //   21: aload_1
    //   22: astore_0
    //   23: aload_1
    //   24: invokestatic 243	com/tencent/common/utils/FileUtils:toByteArray	(Ljava/io/InputStream;)Ljava/nio/ByteBuffer;
    //   27: astore_2
    //   28: aload_1
    //   29: astore_0
    //   30: aload_2
    //   31: ifnull +55 -> 86
    //   34: aload_1
    //   35: astore_0
    //   36: new 245	java/lang/String
    //   39: dup
    //   40: aload_2
    //   41: invokevirtual 251	java/nio/ByteBuffer:array	()[B
    //   44: iconst_0
    //   45: aload_2
    //   46: invokevirtual 254	java/nio/ByteBuffer:position	()I
    //   49: invokespecial 257	java/lang/String:<init>	([BII)V
    //   52: astore_3
    //   53: aload_1
    //   54: astore_0
    //   55: invokestatic 261	com/tencent/common/utils/FileUtils:getInstance	()Lcom/tencent/common/utils/FileUtils;
    //   58: aload_2
    //   59: invokevirtual 265	com/tencent/common/utils/FileUtils:releaseByteBuffer	(Ljava/nio/ByteBuffer;)Z
    //   62: pop
    //   63: aload_1
    //   64: invokevirtual 268	java/io/FileInputStream:close	()V
    //   67: aload_3
    //   68: areturn
    //   69: astore_0
    //   70: aload_0
    //   71: invokevirtual 269	java/io/IOException:printStackTrace	()V
    //   74: aload_3
    //   75: areturn
    //   76: astore_2
    //   77: goto +28 -> 105
    //   80: astore_2
    //   81: goto +43 -> 124
    //   84: aconst_null
    //   85: astore_0
    //   86: aload_0
    //   87: ifnull +58 -> 145
    //   90: aload_0
    //   91: invokevirtual 268	java/io/FileInputStream:close	()V
    //   94: aconst_null
    //   95: areturn
    //   96: astore_1
    //   97: aconst_null
    //   98: astore_0
    //   99: goto +49 -> 148
    //   102: astore_2
    //   103: aconst_null
    //   104: astore_1
    //   105: aload_1
    //   106: astore_0
    //   107: aload_2
    //   108: invokevirtual 270	java/lang/OutOfMemoryError:printStackTrace	()V
    //   111: aload_1
    //   112: ifnull +33 -> 145
    //   115: aload_1
    //   116: invokevirtual 268	java/io/FileInputStream:close	()V
    //   119: aconst_null
    //   120: areturn
    //   121: astore_2
    //   122: aconst_null
    //   123: astore_1
    //   124: aload_1
    //   125: astore_0
    //   126: aload_2
    //   127: invokevirtual 127	java/lang/Exception:printStackTrace	()V
    //   130: aload_1
    //   131: ifnull +14 -> 145
    //   134: aload_1
    //   135: invokevirtual 268	java/io/FileInputStream:close	()V
    //   138: aconst_null
    //   139: areturn
    //   140: astore_0
    //   141: aload_0
    //   142: invokevirtual 269	java/io/IOException:printStackTrace	()V
    //   145: aconst_null
    //   146: areturn
    //   147: astore_1
    //   148: aload_0
    //   149: ifnull +15 -> 164
    //   152: aload_0
    //   153: invokevirtual 268	java/io/FileInputStream:close	()V
    //   156: goto +8 -> 164
    //   159: astore_0
    //   160: aload_0
    //   161: invokevirtual 269	java/io/IOException:printStackTrace	()V
    //   164: aload_1
    //   165: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	166	0	paramContext	Context
    //   20	44	1	localFileInputStream	java.io.FileInputStream
    //   96	1	1	localObject1	Object
    //   104	31	1	localObject2	Object
    //   147	18	1	localObject3	Object
    //   27	32	2	localByteBuffer	java.nio.ByteBuffer
    //   76	1	2	localOutOfMemoryError1	OutOfMemoryError
    //   80	1	2	localException1	Exception
    //   102	6	2	localOutOfMemoryError2	OutOfMemoryError
    //   121	6	2	localException2	Exception
    //   52	23	3	str	String
    // Exception table:
    //   from	to	target	type
    //   63	67	69	java/io/IOException
    //   23	28	76	java/lang/OutOfMemoryError
    //   36	53	76	java/lang/OutOfMemoryError
    //   55	63	76	java/lang/OutOfMemoryError
    //   23	28	80	java/lang/Exception
    //   36	53	80	java/lang/Exception
    //   55	63	80	java/lang/Exception
    //   0	21	96	finally
    //   0	21	102	java/lang/OutOfMemoryError
    //   0	21	121	java/lang/Exception
    //   90	94	140	java/io/IOException
    //   115	119	140	java/io/IOException
    //   134	138	140	java/io/IOException
    //   23	28	147	finally
    //   36	53	147	finally
    //   55	63	147	finally
    //   107	111	147	finally
    //   126	130	147	finally
    //   152	156	159	java/io/IOException
  }
  
  public static File getSAFJFile(Context paramContext)
  {
    return new File(FileUtils.getCacheDir(paramContext), "safj.inf");
  }
  
  public static File getSAFMFile(Context paramContext)
  {
    return new File(FileUtils.getCacheDir(paramContext), "safm.inf");
  }
  
  public static File getSharedDownloadAppJsonFile()
  {
    File localFile = FileUtils.getSDcardDir();
    if (localFile == null) {
      return null;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(localFile.getAbsolutePath());
    localStringBuilder.append("/Android/data/");
    localFile = FileUtils.createDir(new File(localStringBuilder.toString()), "com.tencent.tbsad");
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(localFile.getAbsolutePath());
    localStringBuilder.append(File.separator);
    localStringBuilder.append(".");
    localStringBuilder.append("download_app_date_file.inf");
    return new File(localStringBuilder.toString());
  }
  
  public static File getStatFile(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("tbs_use");
    localStringBuilder.append(Integer.toString(paramInt));
    localStringBuilder.append(".inf");
    return new File(FileUtils.getDataDir(TbsBaseModuleShell.getContext()), localStringBuilder.toString());
  }
  
  public static File getStatFile(int paramInt, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    String str;
    if (paramBoolean) {
      str = FILE_SIMPLE_QB_STAT_INFO_PREFIX;
    } else {
      str = "tbs_use";
    }
    localStringBuilder.append(str);
    localStringBuilder.append(Integer.toString(paramInt));
    localStringBuilder.append(".inf");
    return new File(FileUtils.getDataDir(TbsBaseModuleShell.getContext()), localStringBuilder.toString());
  }
  
  public static File getStatWUPRequestFile(boolean paramBoolean)
  {
    String str = ThreadUtils.getCurrentProcessNameIngoreColon(TbsBaseModuleShell.getContext());
    File localFile = FileUtils.getDataDir(TbsBaseModuleShell.getContext());
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(str);
    localStringBuilder.append("_");
    localStringBuilder.append("wup_stat_req_file");
    localStringBuilder.append(".dat");
    if (paramBoolean) {
      str = ".partial";
    } else {
      str = "";
    }
    localStringBuilder.append(str);
    return new File(localFile, localStringBuilder.toString());
  }
  
  public static File getSubsetAdRuleFile(Context paramContext)
  {
    return new File(FileUtils.getCacheDir(paramContext), "subsetadrule.inf");
  }
  
  public static File getTBSinfoFile(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(FILE_TBS_PAGE_LOAD_INFO_PREFIX);
    localStringBuilder.append(Integer.toString(paramInt));
    localStringBuilder.append(".inf");
    return new File(FileUtils.getDataDir(TbsBaseModuleShell.getContext()), localStringBuilder.toString());
  }
  
  public static File getTbsAdDataJsonFile(Context paramContext)
  {
    return new File(FileUtils.getDataDir(paramContext), "com.tencent.tbs_tbs_ad_data_file.inf");
  }
  
  public static File getUserBaseFile(Context paramContext)
  {
    return new File(FileUtils.getDataDir(paramContext), FILE_USER_BASE_INFO);
  }
  
  public static File getUserFile(Context paramContext)
  {
    return new File(FileUtils.getDataDir(paramContext), FILE_USER_INFO);
  }
  
  public static File getUserInfoFile(Context paramContext)
  {
    return new File(FileUtils.getDataDir(paramContext), "com.tencent.tbs_user_data_file_new.inf");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\TbsFileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */