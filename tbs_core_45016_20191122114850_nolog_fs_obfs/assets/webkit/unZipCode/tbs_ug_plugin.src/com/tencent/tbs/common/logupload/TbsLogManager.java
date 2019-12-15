package com.tencent.tbs.common.logupload;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import java.io.File;
import org.json.JSONObject;

public class TbsLogManager
{
  public static final String LOG_TYPE_TBSLOG = "tbslog_auto_submit";
  public static final String OPENMODE = "openmode";
  public static final String OPENRANDOM = "openrandom";
  public static final String PERFORMANCE_LOG_SERVER_HOST = "qprostat.imtt.qq.com";
  public static final String PERFORMANCE_LOG_SERVER_URL = "https://qprostat.imtt.qq.com";
  public static final String TAG = "TbsLogManager";
  
  private static String getModeStr()
  {
    Object localObject = Build.MODEL;
    try
    {
      String str = new String(((String)localObject).getBytes("UTF-8"), "ISO8859-1");
      localObject = str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    if (((String)localObject).length() > 0) {
      return (String)localObject;
    }
    return (String)localObject;
  }
  
  /* Error */
  private static boolean postZipFile(Context paramContext, UploadZip paramUploadZip, String paramString)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore 4
    //   3: new 66	java/io/File
    //   6: dup
    //   7: aload_1
    //   8: invokevirtual 69	com/tencent/tbs/common/logupload/TbsLogManager$UploadZip:getPath	()Ljava/lang/String;
    //   11: invokespecial 72	java/io/File:<init>	(Ljava/lang/String;)V
    //   14: astore 7
    //   16: aload 7
    //   18: invokevirtual 76	java/io/File:exists	()Z
    //   21: ifeq +440 -> 461
    //   24: aload 7
    //   26: invokevirtual 79	java/io/File:length	()J
    //   29: lconst_0
    //   30: lcmp
    //   31: ifeq +430 -> 461
    //   34: aload 7
    //   36: invokevirtual 82	java/io/File:isFile	()Z
    //   39: ifne +5 -> 44
    //   42: iconst_0
    //   43: ireturn
    //   44: new 84	java/net/URL
    //   47: dup
    //   48: ldc 26
    //   50: invokespecial 85	java/net/URL:<init>	(Ljava/lang/String;)V
    //   53: invokevirtual 89	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   56: checkcast 91	java/net/HttpURLConnection
    //   59: astore 6
    //   61: aload 6
    //   63: iconst_1
    //   64: invokevirtual 95	java/net/HttpURLConnection:setDoOutput	(Z)V
    //   67: aload 6
    //   69: iconst_1
    //   70: invokevirtual 98	java/net/HttpURLConnection:setDoInput	(Z)V
    //   73: aload 6
    //   75: iconst_0
    //   76: invokevirtual 101	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   79: aload 6
    //   81: ldc 103
    //   83: invokevirtual 106	java/net/HttpURLConnection:setRequestMethod	(Ljava/lang/String;)V
    //   86: new 108	java/lang/StringBuilder
    //   89: dup
    //   90: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   93: astore 5
    //   95: aload 5
    //   97: ldc 111
    //   99: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   102: pop
    //   103: aload 5
    //   105: aload 7
    //   107: invokevirtual 79	java/io/File:length	()J
    //   110: invokevirtual 118	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   113: pop
    //   114: aload 6
    //   116: ldc 120
    //   118: aload 5
    //   120: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   123: invokevirtual 127	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   126: aload 6
    //   128: ldc -127
    //   130: ldc -125
    //   132: invokevirtual 127	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   135: aload 6
    //   137: ldc -123
    //   139: ldc 47
    //   141: invokevirtual 127	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   144: aload 6
    //   146: ldc -121
    //   148: aload_2
    //   149: invokevirtual 127	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   152: aload 6
    //   154: ldc -119
    //   156: aload_0
    //   157: invokestatic 143	com/tencent/tbs/common/utils/DeviceUtils:getIMEI	(Landroid/content/Context;)Ljava/lang/String;
    //   160: invokevirtual 127	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   163: aload 6
    //   165: ldc -111
    //   167: aload_1
    //   168: invokevirtual 149	com/tencent/tbs/common/logupload/TbsLogManager$UploadZip:getParams	()Lorg/json/JSONObject;
    //   171: invokevirtual 152	org/json/JSONObject:toString	()Ljava/lang/String;
    //   174: invokevirtual 127	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   177: aload 6
    //   179: ldc 20
    //   181: aload_2
    //   182: invokevirtual 127	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   185: aload 6
    //   187: ldc 17
    //   189: invokestatic 154	com/tencent/tbs/common/logupload/TbsLogManager:getModeStr	()Ljava/lang/String;
    //   192: invokevirtual 127	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   195: aload 6
    //   197: invokevirtual 158	java/net/HttpURLConnection:getOutputStream	()Ljava/io/OutputStream;
    //   200: astore_2
    //   201: sipush 10240
    //   204: newarray <illegal type>
    //   206: astore 8
    //   208: aconst_null
    //   209: astore 5
    //   211: aconst_null
    //   212: astore_0
    //   213: new 160	java/io/FileInputStream
    //   216: dup
    //   217: aload 7
    //   219: invokespecial 163	java/io/FileInputStream:<init>	(Ljava/io/File;)V
    //   222: astore_1
    //   223: aload_1
    //   224: aload 8
    //   226: iconst_0
    //   227: sipush 10240
    //   230: invokevirtual 167	java/io/FileInputStream:read	([BII)I
    //   233: istore_3
    //   234: iload_3
    //   235: iconst_m1
    //   236: if_icmpeq +14 -> 250
    //   239: aload_2
    //   240: aload 8
    //   242: iconst_0
    //   243: iload_3
    //   244: invokevirtual 173	java/io/OutputStream:write	([BII)V
    //   247: goto -24 -> 223
    //   250: aload_1
    //   251: invokevirtual 176	java/io/FileInputStream:close	()V
    //   254: aload_2
    //   255: invokevirtual 177	java/io/OutputStream:close	()V
    //   258: aload 6
    //   260: invokevirtual 180	java/net/HttpURLConnection:getResponseCode	()I
    //   263: istore_3
    //   264: new 108	java/lang/StringBuilder
    //   267: dup
    //   268: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   271: astore_0
    //   272: aload_0
    //   273: ldc -74
    //   275: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   278: pop
    //   279: aload_0
    //   280: iload_3
    //   281: invokevirtual 185	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   284: pop
    //   285: aload_0
    //   286: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   289: pop
    //   290: iload_3
    //   291: sipush 200
    //   294: if_icmpne +6 -> 300
    //   297: iconst_1
    //   298: istore 4
    //   300: iload 4
    //   302: ireturn
    //   303: astore_2
    //   304: aload_1
    //   305: astore_0
    //   306: aload_2
    //   307: astore_1
    //   308: goto +104 -> 412
    //   311: astore_2
    //   312: goto +11 -> 323
    //   315: astore_1
    //   316: goto +96 -> 412
    //   319: astore_2
    //   320: aload 5
    //   322: astore_1
    //   323: aload_1
    //   324: astore_0
    //   325: new 108	java/lang/StringBuilder
    //   328: dup
    //   329: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   332: astore 5
    //   334: aload_1
    //   335: astore_0
    //   336: aload 5
    //   338: ldc -69
    //   340: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   343: pop
    //   344: aload_1
    //   345: astore_0
    //   346: aload 5
    //   348: aload_2
    //   349: invokevirtual 188	java/lang/Exception:toString	()Ljava/lang/String;
    //   352: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   355: pop
    //   356: aload_1
    //   357: astore_0
    //   358: aload 5
    //   360: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   363: pop
    //   364: aload_1
    //   365: ifnull +45 -> 410
    //   368: aload_1
    //   369: invokevirtual 176	java/io/FileInputStream:close	()V
    //   372: iconst_0
    //   373: ireturn
    //   374: astore_0
    //   375: aload_0
    //   376: invokevirtual 191	java/io/IOException:printStackTrace	()V
    //   379: new 108	java/lang/StringBuilder
    //   382: dup
    //   383: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   386: astore_1
    //   387: aload_1
    //   388: ldc -63
    //   390: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   393: pop
    //   394: aload_1
    //   395: aload_0
    //   396: invokevirtual 194	java/io/IOException:toString	()Ljava/lang/String;
    //   399: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   402: pop
    //   403: aload_1
    //   404: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   407: pop
    //   408: iconst_0
    //   409: ireturn
    //   410: iconst_0
    //   411: ireturn
    //   412: aload_0
    //   413: ifnull +46 -> 459
    //   416: aload_0
    //   417: invokevirtual 176	java/io/FileInputStream:close	()V
    //   420: goto +39 -> 459
    //   423: astore_0
    //   424: aload_0
    //   425: invokevirtual 191	java/io/IOException:printStackTrace	()V
    //   428: new 108	java/lang/StringBuilder
    //   431: dup
    //   432: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   435: astore_1
    //   436: aload_1
    //   437: ldc -63
    //   439: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   442: pop
    //   443: aload_1
    //   444: aload_0
    //   445: invokevirtual 194	java/io/IOException:toString	()Ljava/lang/String;
    //   448: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   451: pop
    //   452: aload_1
    //   453: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   456: pop
    //   457: iconst_0
    //   458: ireturn
    //   459: aload_1
    //   460: athrow
    //   461: iconst_0
    //   462: ireturn
    //   463: astore_0
    //   464: new 108	java/lang/StringBuilder
    //   467: dup
    //   468: invokespecial 109	java/lang/StringBuilder:<init>	()V
    //   471: astore_1
    //   472: aload_1
    //   473: ldc -60
    //   475: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   478: pop
    //   479: aload_1
    //   480: aload_0
    //   481: invokevirtual 188	java/lang/Exception:toString	()Ljava/lang/String;
    //   484: invokevirtual 115	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   487: pop
    //   488: aload_1
    //   489: invokevirtual 123	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   492: pop
    //   493: iconst_0
    //   494: ireturn
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	495	0	paramContext	Context
    //   0	495	1	paramUploadZip	UploadZip
    //   0	495	2	paramString	String
    //   233	62	3	i	int
    //   1	300	4	bool	boolean
    //   93	266	5	localStringBuilder	StringBuilder
    //   59	200	6	localHttpURLConnection	java.net.HttpURLConnection
    //   14	204	7	localFile	File
    //   206	35	8	arrayOfByte	byte[]
    // Exception table:
    //   from	to	target	type
    //   223	234	303	finally
    //   239	247	303	finally
    //   250	254	303	finally
    //   223	234	311	java/lang/Exception
    //   239	247	311	java/lang/Exception
    //   250	254	311	java/lang/Exception
    //   213	223	315	finally
    //   325	334	315	finally
    //   336	344	315	finally
    //   346	356	315	finally
    //   358	364	315	finally
    //   213	223	319	java/lang/Exception
    //   368	372	374	java/io/IOException
    //   416	420	423	java/io/IOException
    //   3	42	463	java/lang/Exception
    //   44	208	463	java/lang/Exception
    //   254	290	463	java/lang/Exception
    //   368	372	463	java/lang/Exception
    //   375	408	463	java/lang/Exception
    //   416	420	463	java/lang/Exception
    //   424	457	463	java/lang/Exception
    //   459	461	463	java/lang/Exception
  }
  
  public static boolean uploadTbsLog(Context paramContext, String[] paramArrayOfString, String paramString)
  {
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("uploadTbsLog -> ");
      ((StringBuilder)localObject).append(paramArrayOfString.length);
      ((StringBuilder)localObject).append(",");
      ((StringBuilder)localObject).append(paramArrayOfString[0]);
      ((StringBuilder)localObject).append(",");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).toString();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramContext.getFilesDir());
      ((StringBuilder)localObject).append("/logzipfile.zip");
      localObject = ((StringBuilder)localObject).toString();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("zipPath:");
      localStringBuilder.append((String)localObject);
      localStringBuilder.append(",logfile: ");
      localStringBuilder.append(paramArrayOfString[0]);
      localStringBuilder.toString();
      new ZipHelper(paramArrayOfString, (String)localObject).Zip();
      boolean bool = postZipFile(paramContext, new UploadZip((String)localObject, paramString, paramString, null), paramString);
      return bool;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
  
  public static void uploadTbsLogIfNeeded(Context paramContext)
  {
    try
    {
      String str = paramContext.getApplicationContext().getPackageName();
      File localFile = Environment.getExternalStorageDirectory();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Android/data/");
      localStringBuilder.append(str);
      localStringBuilder.append("/files/tbslog/tbslog.txt");
      uploadTbsLog(paramContext, new String[] { new File(localFile, localStringBuilder.toString()).getAbsolutePath() }, "tbslog_auto_submit");
      return;
    }
    finally
    {
      paramContext = finally;
      throw paramContext;
    }
  }
  
  public final class Headers
  {
    public static final String Q_AB = "q-ab";
    public static final String Q_AUTH = "q-auth";
    public static final String Q_GUID = "q-guid";
    public static final String Q_HEADER = "q-header";
    public static final String Q_KEY = "psw";
    public static final String Q_NEED = "q-need";
    public static final String Q_PARAMS = "q-params";
    public static final String Q_PROXY_LOG = "q-proxy-log";
    public static final String Q_PROXY_RESP = "q-proxy-resp";
    public static final String Q_PROXY_RESP_AD = "AD";
    public static final String Q_PROXY_RESP_ADC = "adc";
    public static final String Q_PROXY_RESP_APKSIZE = "apkSize";
    public static final String Q_PROXY_RESP_APKTYPE = "apkType";
    public static final String Q_PROXY_RESP_APP = "app";
    public static final String Q_PROXY_RESP_BACKUPAPKURL = "backupApkUrl";
    public static final String Q_PROXY_RESP_CE = "ce";
    public static final String Q_PROXY_RESP_DS = "ds";
    public static final String Q_PROXY_RESP_ITG = "itg";
    public static final String Q_PROXY_RESP_MARKETPKGNAME = "marketPkgName";
    public static final String Q_PROXY_RESP_MIF = "mif";
    public static final String Q_PROXY_RESP_NEWVERSION = "newVersion";
    public static final String Q_PROXY_RESP_ORGVERSION = "orgVersion";
    public static final String Q_PROXY_RESP_PKGNAME = "pkgName";
    public static final String Q_PROXY_RESP_PST = "pst";
    public static final String Q_PROXY_RESP_QZV = "qzv";
    public static final String Q_PROXY_RESP_STFlag = "STFlag";
    public static final String Q_PROXY_RESP_STInfoUrl = "STInfoUrl";
    public static final String Q_PROXY_RESP_STLEVEL = "STLevel";
    public static final String Q_PROXY_RESP_STSUBLEVEL = "STSubLevel";
    public static final String Q_PROXY_RESP_STTYPE = "STType";
    public static final String Q_PROXY_RESP_VN = "vn";
    public static final String Q_PROXY_RESP_YYBAPKNAME = "yybApkName";
    public static final String Q_PROXY_RESP_YYBAPKSIZE = "yybApkSize";
    public static final String Q_PROXY_RESP_YYBAPKURL = "yybApkUrl";
    public static final String Q_PROXY_TYPE = "q-proxy-type";
    public static final String Q_REDIRECT = "q-redirect";
    public static final String Q_SID = "Q-SID";
    public static final String Q_TIP = "q-tip";
    public static final String Q_UA = "q-ua";
    public static final String Q_UA2 = "q-ua2";
    
    public Headers() {}
  }
  
  public static class UploadZip
  {
    String openRandom = "random";
    JSONObject params = new JSONObject();
    String path;
    String qProxyLog = "qProxyLog";
    
    public UploadZip(String paramString1, String paramString2, String paramString3, JSONObject paramJSONObject)
    {
      this.path = paramString1;
      this.qProxyLog = paramString2;
      if (paramString3 != null) {
        this.openRandom = paramString3;
      }
      if (paramJSONObject != null) {
        this.params = paramJSONObject;
      }
    }
    
    public String getOpenRandom()
    {
      return this.openRandom;
    }
    
    public JSONObject getParams()
    {
      return this.params;
    }
    
    public String getPath()
    {
      return this.path;
    }
    
    public String getProxyLog()
    {
      return this.qProxyLog;
    }
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("UploadZip path = ");
      localStringBuilder.append(this.path);
      localStringBuilder.append(", qProxyLog = ");
      localStringBuilder.append(this.qProxyLog);
      localStringBuilder.append(", openRandom = ");
      localStringBuilder.append(this.openRandom);
      localStringBuilder.append(", params = ");
      localStringBuilder.append(this.params.toString());
      return localStringBuilder.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\logupload\TbsLogManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */