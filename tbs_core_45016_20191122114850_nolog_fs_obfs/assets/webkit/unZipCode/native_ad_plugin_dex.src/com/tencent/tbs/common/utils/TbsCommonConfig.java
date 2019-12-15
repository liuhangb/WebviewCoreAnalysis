package com.tencent.tbs.common.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import com.tencent.common.utils.LogUtils;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class TbsCommonConfig
{
  private static final String COMMON_CONFIG_FILE = "tbsnetui.conf";
  private static final String KEY_WUP_PROXY_URL = "wup_proxy_url";
  private static final String LOGTAG = "TbsCommonConfig";
  public static final String SAVOR_WUP_PROXY_ADDRESS = "http://111.30.132.147:8080";
  private static final String TBS_FOLDER_NAME = "tbs";
  public static final String TEST_WUP_PROXY_ADDRESS = "http://61.172.204.175:18000";
  private static TbsCommonConfig mInstance;
  private Context mContext = null;
  private File mTbsConfigDir = null;
  private String mWupProxyAddress = null;
  
  @TargetApi(11)
  private TbsCommonConfig(Context paramContext)
  {
    LogUtils.d("TbsCommonConfig", "TbsCommonConfig constructing...");
    this.mContext = paramContext.getApplicationContext();
    loadProperties();
  }
  
  private File getConfigFile()
  {
    Object localObject1 = null;
    Object localObject2;
    try
    {
      StringBuilder localStringBuilder1;
      if (this.mTbsConfigDir == null)
      {
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append(Environment.getExternalStorageDirectory());
        localStringBuilder1.append(File.separator);
        localStringBuilder1.append("tbs");
        localStringBuilder1.append(File.separator);
        localStringBuilder1.append(this.mContext.getApplicationInfo().packageName);
        this.mTbsConfigDir = new File(localStringBuilder1.toString());
        if (this.mTbsConfigDir == null) {
          break label257;
        }
        if (!this.mTbsConfigDir.isDirectory()) {
          return null;
        }
      }
      localObject2 = new File(this.mTbsConfigDir, "tbsnetui.conf");
      if (!((File)localObject2).exists())
      {
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("Get file(");
        localStringBuilder1.append(((File)localObject2).getCanonicalPath());
        localStringBuilder1.append(") failed!");
        LogUtils.d("TbsCommonConfig", localStringBuilder1.toString());
        return null;
      }
      try
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("pathc:");
        ((StringBuilder)localObject1).append(((File)localObject2).getCanonicalPath());
        LogUtils.d("TbsCommonConfig", ((StringBuilder)localObject1).toString());
        return (File)localObject2;
      }
      catch (Throwable localThrowable1)
      {
        localObject1 = localObject2;
      }
      localObject2 = new StringWriter();
    }
    catch (Throwable localThrowable2) {}
    localThrowable2.printStackTrace(new PrintWriter((Writer)localObject2));
    StringBuilder localStringBuilder2 = new StringBuilder();
    localStringBuilder2.append("exceptions occurred2:");
    localStringBuilder2.append(((StringWriter)localObject2).toString());
    LogUtils.d("TbsCommonConfig", localStringBuilder2.toString());
    return (File)localObject1;
    label257:
    return null;
  }
  
  public static TbsCommonConfig getInstance()
  {
    try
    {
      TbsCommonConfig localTbsCommonConfig = mInstance;
      return localTbsCommonConfig;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static TbsCommonConfig getInstance(Context paramContext)
  {
    try
    {
      if (mInstance == null) {
        mInstance = new TbsCommonConfig(paramContext);
      }
      paramContext = mInstance;
      return paramContext;
    }
    finally {}
  }
  
  /* Error */
  private void loadProperties()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: invokespecial 147	com/tencent/tbs/common/utils/TbsCommonConfig:getConfigFile	()Ljava/io/File;
    //   6: astore_1
    //   7: aload_1
    //   8: ifnonnull +13 -> 21
    //   11: ldc 14
    //   13: ldc -107
    //   15: invokestatic 54	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   18: aload_0
    //   19: monitorexit
    //   20: return
    //   21: new 151	java/io/FileInputStream
    //   24: dup
    //   25: aload_1
    //   26: invokespecial 154	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   29: astore_1
    //   30: new 156	java/util/Properties
    //   33: dup
    //   34: invokespecial 157	java/util/Properties:<init>	()V
    //   37: astore_2
    //   38: aload_2
    //   39: aload_1
    //   40: invokevirtual 161	java/util/Properties:load	(Ljava/io/InputStream;)V
    //   43: aload_2
    //   44: ldc 11
    //   46: ldc -93
    //   48: invokevirtual 167	java/util/Properties:getProperty	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   51: astore_2
    //   52: ldc -93
    //   54: aload_2
    //   55: invokevirtual 173	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   58: ifne +8 -> 66
    //   61: aload_0
    //   62: aload_2
    //   63: putfield 46	com/tencent/tbs/common/utils/TbsCommonConfig:mWupProxyAddress	Ljava/lang/String;
    //   66: aload_1
    //   67: invokevirtual 176	java/io/FileInputStream:close	()V
    //   70: goto +61 -> 131
    //   73: astore_1
    //   74: goto +60 -> 134
    //   77: astore_2
    //   78: new 125	java/io/StringWriter
    //   81: dup
    //   82: invokespecial 126	java/io/StringWriter:<init>	()V
    //   85: astore_1
    //   86: aload_2
    //   87: new 128	java/io/PrintWriter
    //   90: dup
    //   91: aload_1
    //   92: invokespecial 131	java/io/PrintWriter:<init>	(Ljava/io/Writer;)V
    //   95: invokevirtual 135	java/lang/Throwable:printStackTrace	(Ljava/io/PrintWriter;)V
    //   98: new 70	java/lang/StringBuilder
    //   101: dup
    //   102: invokespecial 71	java/lang/StringBuilder:<init>	()V
    //   105: astore_2
    //   106: aload_2
    //   107: ldc -78
    //   109: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   112: pop
    //   113: aload_2
    //   114: aload_1
    //   115: invokevirtual 138	java/io/StringWriter:toString	()Ljava/lang/String;
    //   118: invokevirtual 88	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   121: pop
    //   122: ldc 14
    //   124: aload_2
    //   125: invokevirtual 101	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   128: invokestatic 54	com/tencent/common/utils/LogUtils:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   131: aload_0
    //   132: monitorexit
    //   133: return
    //   134: aload_0
    //   135: monitorexit
    //   136: aload_1
    //   137: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	138	0	this	TbsCommonConfig
    //   6	61	1	localObject1	Object
    //   73	1	1	localObject2	Object
    //   85	52	1	localStringWriter	StringWriter
    //   37	26	2	localObject3	Object
    //   77	10	2	localThrowable	Throwable
    //   105	20	2	localStringBuilder	StringBuilder
    // Exception table:
    //   from	to	target	type
    //   2	7	73	finally
    //   11	18	73	finally
    //   21	66	73	finally
    //   66	70	73	finally
    //   78	131	73	finally
    //   2	7	77	java/lang/Throwable
    //   11	18	77	java/lang/Throwable
    //   21	66	77	java/lang/Throwable
    //   66	70	77	java/lang/Throwable
  }
  
  public String getWupProxyAddress()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getWupProxyAddress:");
    localStringBuilder.append(this.mWupProxyAddress);
    LogUtils.d("TbsCommonConfig", localStringBuilder.toString());
    return this.mWupProxyAddress;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\TbsCommonConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */