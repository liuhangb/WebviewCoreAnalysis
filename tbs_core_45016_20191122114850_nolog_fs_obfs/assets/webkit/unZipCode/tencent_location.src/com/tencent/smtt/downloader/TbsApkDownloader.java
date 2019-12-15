package com.tencent.smtt.downloader;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.tencent.smtt.downloader.utils.ApkUtil;
import com.tencent.smtt.downloader.utils.FileUtil;
import com.tencent.smtt.downloader.utils.b;
import com.tencent.smtt.downloader.utils.h;
import com.tencent.smtt.downloader.utils.i;
import com.tencent.smtt.downloader.utils.i.a;
import com.tencent.smtt.downloader.utils.i.b;
import com.tencent.smtt.downloader.utils.j;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class TbsApkDownloader
{
  private static int jdField_b_of_type_Int = 5;
  private static final String[] jdField_b_of_type_ArrayOfJavaLangString = { "tbs_downloading_com.tencent.mtt", "tbs_downloading_com.tencent.mm", "tbs_downloading_com.tencent.mobileqq", "tbs_downloading_com.tencent.tbs", "tbs_downloading_com.qzone" };
  private static int jdField_c_of_type_Int = 1;
  int jdField_a_of_type_Int = 0;
  private long jdField_a_of_type_Long;
  private Context jdField_a_of_type_AndroidContentContext;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private i.b jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b;
  private File jdField_a_of_type_JavaIoFile;
  private String jdField_a_of_type_JavaLangString;
  private HttpURLConnection jdField_a_of_type_JavaNetHttpURLConnection;
  private Set<String> jdField_a_of_type_JavaUtilSet;
  private boolean jdField_a_of_type_Boolean;
  String[] jdField_a_of_type_ArrayOfJavaLangString = null;
  private String jdField_b_of_type_JavaLangString;
  private boolean jdField_b_of_type_Boolean;
  private String jdField_c_of_type_JavaLangString;
  private boolean jdField_c_of_type_Boolean;
  private int jdField_d_of_type_Int = 30000;
  private String jdField_d_of_type_JavaLangString;
  private boolean jdField_d_of_type_Boolean;
  private int jdField_e_of_type_Int = 20000;
  private String jdField_e_of_type_JavaLangString;
  private boolean jdField_e_of_type_Boolean;
  private int f;
  private int g;
  private int h;
  private int i = jdField_b_of_type_Int;
  
  public TbsApkDownloader(Context paramContext)
    throws NullPointerException
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
    this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b = i.a(this.jdField_a_of_type_AndroidContentContext).a();
    this.jdField_a_of_type_JavaUtilSet = new HashSet();
    paramContext = new StringBuilder();
    paramContext.append("tbs_downloading_");
    paramContext.append(this.jdField_a_of_type_AndroidContentContext.getPackageName());
    this.jdField_d_of_type_JavaLangString = paramContext.toString();
    e.a();
    this.jdField_a_of_type_JavaIoFile = e.d(this.jdField_a_of_type_AndroidContentContext);
    if (this.jdField_a_of_type_JavaIoFile != null)
    {
      c();
      this.jdField_e_of_type_JavaLangString = null;
      this.h = -1;
      return;
    }
    throw new NullPointerException("TbsCorePrivateDir is null!");
  }
  
  private long a()
  {
    File localFile = new File(this.jdField_a_of_type_JavaIoFile, "x5.tbs.temp");
    if (localFile.exists()) {
      return localFile.length();
    }
    return 0L;
  }
  
  private long a(long paramLong1, long paramLong2)
  {
    long l = System.currentTimeMillis();
    this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.c(l - paramLong1);
    this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.d(paramLong2);
    return l;
  }
  
  @TargetApi(8)
  static File a(Context paramContext)
  {
    for (;;)
    {
      try
      {
        if (Build.VERSION.SDK_INT >= 8)
        {
          paramContext = new File(FileUtil.a(paramContext, 4));
          if ((paramContext != null) && (!paramContext.exists()) && (!paramContext.isDirectory())) {
            paramContext.mkdirs();
          }
          return paramContext;
        }
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("[TbsApkDownloader.backupApkPath] Exception is ");
        localStringBuilder.append(paramContext.getMessage());
        h.b("TbsDownload", localStringBuilder.toString());
        return null;
      }
      paramContext = null;
    }
  }
  
  private String a(Throwable paramThrowable)
  {
    String str = Log.getStackTraceString(paramThrowable);
    paramThrowable = str;
    if (str.length() > 1024) {
      paramThrowable = str.substring(0, 1024);
    }
    return paramThrowable;
  }
  
  private String a(URL paramURL)
  {
    try
    {
      paramURL = InetAddress.getByName(paramURL.getHost()).getHostAddress();
      return paramURL;
    }
    catch (Error paramURL)
    {
      paramURL.printStackTrace();
      return "";
    }
    catch (Exception paramURL)
    {
      paramURL.printStackTrace();
    }
    return "";
  }
  
  private void a(int paramInt, String paramString, boolean paramBoolean)
  {
    if ((paramBoolean) || (this.f > this.i))
    {
      this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.h(paramInt);
      this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.e(paramString);
    }
  }
  
  private void a(long paramLong)
  {
    this.f += 1;
    long l = paramLong;
    if (paramLong <= 0L) {}
    try
    {
      l = b();
      Thread.sleep(l);
      return;
    }
    catch (Exception localException) {}
  }
  
  public static void a(Context paramContext)
  {
    for (;;)
    {
      int j;
      try
      {
        e.a();
        Object localObject1 = e.d(paramContext);
        new File((File)localObject1, "x5.tbs").delete();
        new File((File)localObject1, "x5.tbs.temp").delete();
        paramContext = a(paramContext);
        if (paramContext != null)
        {
          int k = 0;
          new File(paramContext, TbsDownloader.a(false)).delete();
          new File(paramContext, "x5.oversea.tbs.org").delete();
          paramContext = paramContext.listFiles();
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(ApkUtil.getCoreVersionCfgFile(true));
          ((StringBuilder)localObject1).append("(.*)");
          localObject1 = Pattern.compile(((StringBuilder)localObject1).toString());
          int m = paramContext.length;
          j = 0;
          Object localObject2;
          if (j < m)
          {
            localObject2 = paramContext[j];
            if ((!((Pattern)localObject1).matcher(((File)localObject2).getName()).find()) || (!((File)localObject2).isFile()) || (!((File)localObject2).exists())) {
              break label281;
            }
            ((File)localObject2).delete();
            break label281;
          }
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(ApkUtil.getCoreVersionCfgFile(false));
          ((StringBuilder)localObject1).append("(.*)");
          localObject1 = Pattern.compile(((StringBuilder)localObject1).toString());
          m = paramContext.length;
          j = k;
          if (j < m)
          {
            localObject2 = paramContext[j];
            if ((((Pattern)localObject1).matcher(((File)localObject2).getName()).find()) && (((File)localObject2).isFile()) && (((File)localObject2).exists())) {
              ((File)localObject2).delete();
            }
            j += 1;
            continue;
          }
        }
        return;
      }
      catch (Exception paramContext)
      {
        return;
      }
      label281:
      j += 1;
    }
  }
  
  private void a(Closeable paramCloseable)
  {
    if (paramCloseable != null) {}
    try
    {
      paramCloseable.close();
      return;
    }
    catch (IOException paramCloseable) {}
  }
  
  public static void a(File paramFile, Context paramContext)
  {
    if (paramFile != null) {}
    for (;;)
    {
      boolean bool1;
      File localFile;
      Object localObject1;
      boolean bool2;
      Object localObject2;
      int k;
      int j;
      Object localObject3;
      try
      {
        bool1 = paramFile.exists();
        if (bool1) {}
      }
      finally {}
      try
      {
        localFile = a(paramContext);
        if (localFile == null) {
          continue;
        }
        if (c.a(paramContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_version_type", 0) == 1)
        {
          localObject1 = new File(localFile, TbsDownloader.a(true));
        }
        else
        {
          if (TbsDownloader.b(paramContext)) {
            localObject1 = "x5.oversea.tbs.org";
          } else {
            localObject1 = TbsDownloader.a(false);
          }
          localObject1 = new File(localFile, (String)localObject1);
        }
        ((File)localObject1).delete();
        FileUtil.b(paramFile, (File)localObject1);
        bool1 = ((File)localObject1).getName().contains("tbs.org");
        bool2 = ((File)localObject1).getName().contains("x5.tbs.decouple");
        if ((!bool2) && (!bool1)) {
          continue;
        }
        localObject1 = localFile.listFiles();
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(ApkUtil.getCoreVersionCfgFile(bool2));
        ((StringBuilder)localObject2).append("(.*)");
        localObject2 = Pattern.compile(((StringBuilder)localObject2).toString());
        k = localObject1.length;
        j = 0;
      }
      catch (Exception paramFile)
      {
        continue;
        j += 1;
      }
    }
    if (j < k)
    {
      localObject3 = localObject1[j];
      if ((((Pattern)localObject2).matcher(((File)localObject3).getName()).find()) && (((File)localObject3).isFile()) && (((File)localObject3).exists())) {
        ((File)localObject3).delete();
      }
    }
    else
    {
      j = c.a(paramContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_version", 0);
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append(ApkUtil.getCoreVersionCfgFile(bool2));
      ((StringBuilder)localObject1).append(".");
      ((StringBuilder)localObject1).append(j);
      localObject1 = new File(localFile, ((StringBuilder)localObject1).toString());
      if (((File)localObject1).exists())
      {
        h.b("TbsDownload", "[TbsApkDownloader.backupTbsApk]delete bacup config file error ");
        return;
      }
      ((File)localObject1).createNewFile();
      if ((c.a(paramContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_version_type", 0) != 1) && (c.a(paramContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_decouplecoreversion", 0) == ApkUtil.a(paramContext, paramFile)))
      {
        j = c.a(paramContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_responsecode", 0);
        if ((j == 5) || (j == 3))
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("response code=");
          ((StringBuilder)localObject1).append(j);
          ((StringBuilder)localObject1).append("return backup decouple apk");
          h.a("TbsApkDownloader", ((StringBuilder)localObject1).toString());
        }
        localObject1 = new File(localFile, TbsDownloader.a(true));
        if (ApkUtil.a(paramContext, paramFile) != ApkUtil.a(paramContext, (File)localObject1))
        {
          ((File)localObject1).delete();
          FileUtil.b(paramFile, (File)localObject1);
        }
      }
      return;
      return;
    }
  }
  
  private void a(String paramString)
    throws Exception
  {
    paramString = new URL(paramString);
    HttpURLConnection localHttpURLConnection = this.jdField_a_of_type_JavaNetHttpURLConnection;
    if (localHttpURLConnection != null) {
      try
      {
        localHttpURLConnection.disconnect();
      }
      catch (Throwable localThrowable)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("[initHttpRequest] mHttpRequest.disconnect() Throwable:");
        localStringBuilder.append(localThrowable.toString());
        h.b("TbsDownload", localStringBuilder.toString());
      }
    }
    this.jdField_a_of_type_JavaNetHttpURLConnection = ((HttpURLConnection)paramString.openConnection());
    this.jdField_a_of_type_JavaNetHttpURLConnection.setRequestProperty("User-Agent", TbsDownloader.a(this.jdField_a_of_type_AndroidContentContext));
    this.jdField_a_of_type_JavaNetHttpURLConnection.setRequestProperty("Accept-Encoding", "identity");
    this.jdField_a_of_type_JavaNetHttpURLConnection.setRequestMethod("GET");
    this.jdField_a_of_type_JavaNetHttpURLConnection.setInstanceFollowRedirects(false);
    this.jdField_a_of_type_JavaNetHttpURLConnection.setConnectTimeout(this.jdField_e_of_type_Int);
    this.jdField_a_of_type_JavaNetHttpURLConnection.setReadTimeout(this.jdField_d_of_type_Int);
  }
  
  private void a(boolean paramBoolean)
  {
    j.a(this.jdField_a_of_type_AndroidContentContext);
    c localc = c.a(this.jdField_a_of_type_AndroidContentContext);
    localc.jdField_a_of_type_JavaUtilMap.put("request_full_package", Boolean.valueOf(false));
    localc.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload", Boolean.valueOf(false));
    localc.jdField_a_of_type_JavaUtilMap.put("tbs_download_interrupt_code_reason", Integer.valueOf(-123));
    localc.a();
    TbsListener localTbsListener = a.a;
    if (paramBoolean) {
      j = 100;
    } else {
      j = 120;
    }
    localTbsListener.onDownloadFinish(j);
    int j = localc.jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_responsecode", 0);
    paramBoolean = TbsDownloader.a(this.jdField_a_of_type_AndroidContentContext);
    if (j == 5)
    {
      if (a(j, paramBoolean) != null) {}
    }
    else
    {
      if ((j != 3) && (j <= 10000))
      {
        j = localc.jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_version", 0);
        e.a().a(this.jdField_a_of_type_AndroidContentContext, new File(this.jdField_a_of_type_JavaIoFile, "x5.tbs").getAbsolutePath(), j);
        a(new File(this.jdField_a_of_type_JavaIoFile, "x5.tbs"), this.jdField_a_of_type_AndroidContentContext);
        return;
      }
      if (a(this.jdField_a_of_type_AndroidContentContext) != null) {
        return;
      }
      b();
      localc.jdField_a_of_type_JavaUtilMap.put("tbs_needdownload", Boolean.valueOf(true));
      localc.a();
    }
  }
  
  private boolean a()
  {
    return new File(this.jdField_a_of_type_JavaIoFile, "x5.tbs.temp").exists();
  }
  
  private boolean a(boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[TbsApkDownloader.verifyTbsApk] isTempFile=");
    ((StringBuilder)localObject1).append(paramBoolean1);
    h.a("TbsDownload", ((StringBuilder)localObject1).toString());
    Object localObject2 = this.jdField_a_of_type_JavaIoFile;
    if (!paramBoolean1) {
      localObject1 = "x5.tbs";
    } else {
      localObject1 = "x5.tbs.temp";
    }
    localObject2 = new File((File)localObject2, (String)localObject1);
    if (!((File)localObject2).exists()) {
      return false;
    }
    Object localObject3 = c.a(this.jdField_a_of_type_AndroidContentContext).jdField_a_of_type_AndroidContentSharedPreferences;
    localObject1 = null;
    Object localObject4 = ((SharedPreferences)localObject3).getString("tbs_apk_md5", null);
    localObject3 = ApkUtil.a((File)localObject2);
    if ((localObject4 != null) && (((String)localObject4).equals(localObject3)))
    {
      localObject4 = new StringBuilder();
      ((StringBuilder)localObject4).append("[TbsApkDownloader.verifyTbsApk] md5(");
      ((StringBuilder)localObject4).append((String)localObject3);
      ((StringBuilder)localObject4).append(") successful!");
      h.a("TbsDownload", ((StringBuilder)localObject4).toString());
      long l1 = 0L;
      long l2 = l1;
      if (paramBoolean1)
      {
        long l3 = c.a(this.jdField_a_of_type_AndroidContentContext).jdField_a_of_type_AndroidContentSharedPreferences.getLong("tbs_apkfilesize", 0L);
        l2 = l1;
        if (((File)localObject2).exists())
        {
          l2 = l1;
          if (l3 > 0L)
          {
            l1 = ((File)localObject2).length();
            l2 = l1;
            if (l3 != l1) {
              l2 = l1;
            }
          }
        }
        else
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("[TbsApkDownloader.verifyTbsApk] isTempFile=");
          ((StringBuilder)localObject1).append(paramBoolean1);
          ((StringBuilder)localObject1).append(" filelength failed");
          h.a("TbsDownload", ((StringBuilder)localObject1).toString());
          localObject1 = this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b;
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("fileLength:");
          ((StringBuilder)localObject2).append(l2);
          ((StringBuilder)localObject2).append(",contentLength:");
          ((StringBuilder)localObject2).append(l3);
          ((i.b)localObject1).d(((StringBuilder)localObject2).toString());
          return false;
        }
      }
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("[TbsApkDownloader.verifyTbsApk] length(");
      ((StringBuilder)localObject3).append(l2);
      ((StringBuilder)localObject3).append(") successful!");
      h.a("TbsDownload", ((StringBuilder)localObject3).toString());
      int k = -1;
      int j = k;
      if (paramBoolean2)
      {
        j = k;
        if (!paramBoolean1)
        {
          k = ApkUtil.a(this.jdField_a_of_type_AndroidContentContext, (File)localObject2);
          int m = c.a(this.jdField_a_of_type_AndroidContentContext).jdField_a_of_type_AndroidContentSharedPreferences.getInt("tbs_download_version", 0);
          j = k;
          if (m != k)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("[TbsApkDownloader.verifyTbsApk] isTempFile=");
            ((StringBuilder)localObject1).append(paramBoolean1);
            ((StringBuilder)localObject1).append(" versionCode failed");
            h.a("TbsDownload", ((StringBuilder)localObject1).toString());
            if (paramBoolean1)
            {
              localObject1 = this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b;
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("fileVersion:");
              ((StringBuilder)localObject2).append(k);
              ((StringBuilder)localObject2).append(",configVersion:");
              ((StringBuilder)localObject2).append(m);
              ((i.b)localObject1).d(((StringBuilder)localObject2).toString());
            }
            return false;
          }
        }
      }
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("[TbsApkDownloader.verifyTbsApk] tbsApkVersionCode(");
      ((StringBuilder)localObject3).append(j);
      ((StringBuilder)localObject3).append(") successful!");
      h.a("TbsDownload", ((StringBuilder)localObject3).toString());
      if ((paramBoolean2) && (!paramBoolean1))
      {
        localObject3 = b.a(this.jdField_a_of_type_AndroidContentContext, false, (File)localObject2);
        if (!"3082023f308201a8a00302010202044c46914a300d06092a864886f70d01010505003064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f301e170d3130303732313036313835305a170d3430303731333036313835305a3064310b30090603550406130238363110300e060355040813074265696a696e673110300e060355040713074265696a696e673110300e060355040a130754656e63656e74310c300a060355040b13035753443111300f0603550403130873616d75656c6d6f30819f300d06092a864886f70d010101050003818d0030818902818100c209077044bd0d63ea00ede5b839914cabcc912a87f0f8b390877e0f7a2583f0d5933443c40431c35a4433bc4c965800141961adc44c9625b1d321385221fd097e5bdc2f44a1840d643ab59dc070cf6c4b4b4d98bed5cbb8046e0a7078ae134da107cdf2bfc9b440fe5cb2f7549b44b73202cc6f7c2c55b8cfb0d333a021f01f0203010001300d06092a864886f70d010105050003818100b007db9922774ef4ccfee81ba514a8d57c410257e7a2eba64bfa17c9e690da08106d32f637ac41fbc9f205176c71bde238c872c3ee2f8313502bee44c80288ea4ef377a6f2cdfe4d3653c145c4acfedbfbadea23b559d41980cc3cdd35d79a68240693739aabf5c5ed26148756cf88264226de394c8a24ac35b712b120d4d23a".equals(localObject3))
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("[TbsApkDownloader.verifyTbsApk] isTempFile=");
          ((StringBuilder)localObject1).append(paramBoolean1);
          ((StringBuilder)localObject1).append(" signature failed");
          h.a("TbsDownload", ((StringBuilder)localObject1).toString());
          if (paramBoolean1)
          {
            localObject2 = this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b;
            localObject4 = new StringBuilder();
            ((StringBuilder)localObject4).append("signature:");
            if (localObject3 == null) {
              localObject1 = "null";
            } else {
              localObject1 = Integer.valueOf(((String)localObject3).length());
            }
            ((StringBuilder)localObject4).append(localObject1);
            ((i.b)localObject2).d(((StringBuilder)localObject4).toString());
          }
          return false;
        }
      }
      h.a("TbsDownload", "[TbsApkDownloader.verifyTbsApk] signature successful!");
      if (paramBoolean1)
      {
        try
        {
          paramBoolean1 = ((File)localObject2).renameTo(new File(this.jdField_a_of_type_JavaIoFile, "x5.tbs"));
        }
        catch (Exception localException)
        {
          paramBoolean1 = false;
        }
        paramBoolean2 = paramBoolean1;
        if (!paramBoolean1)
        {
          a(109, a(localException), true);
          return false;
        }
      }
      else
      {
        paramBoolean2 = false;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("[TbsApkDownloader.verifyTbsApk] rename(");
      localStringBuilder.append(paramBoolean2);
      localStringBuilder.append(") successful!");
      h.a("TbsDownload", localStringBuilder.toString());
      return true;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[TbsApkDownloader.verifyTbsApk] isTempFile=");
    localStringBuilder.append(paramBoolean1);
    localStringBuilder.append(" md5 failed");
    h.a("TbsDownload", localStringBuilder.toString());
    if (paramBoolean1) {
      this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.d("fileMd5 not match");
    }
    return false;
  }
  
  private long b()
  {
    int j = this.f;
    switch (j)
    {
    default: 
      return 200000L;
    case 3: 
    case 4: 
      return 100000L;
    }
    return j * 20000L;
  }
  
  /* Error */
  private boolean b()
  {
    // Byte code:
    //   0: invokestatic 550	java/lang/Runtime:getRuntime	()Ljava/lang/Runtime;
    //   3: astore 6
    //   5: iconst_0
    //   6: istore 5
    //   8: iconst_0
    //   9: istore 4
    //   11: aconst_null
    //   12: astore 10
    //   14: aconst_null
    //   15: astore 8
    //   17: new 89	java/lang/StringBuilder
    //   20: dup
    //   21: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   24: astore 7
    //   26: aload 7
    //   28: ldc_w 552
    //   31: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   34: pop
    //   35: aload 7
    //   37: ldc_w 554
    //   40: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   43: pop
    //   44: aload 6
    //   46: aload 7
    //   48: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   51: invokevirtual 558	java/lang/Runtime:exec	(Ljava/lang/String;)Ljava/lang/Process;
    //   54: invokevirtual 564	java/lang/Process:getInputStream	()Ljava/io/InputStream;
    //   57: astore 6
    //   59: new 566	java/io/InputStreamReader
    //   62: dup
    //   63: aload 6
    //   65: invokespecial 569	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;)V
    //   68: astore 7
    //   70: new 571	java/io/BufferedReader
    //   73: dup
    //   74: aload 7
    //   76: invokespecial 574	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   79: astore 10
    //   81: iconst_0
    //   82: istore_1
    //   83: aload 10
    //   85: invokevirtual 577	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   88: astore 8
    //   90: iload 4
    //   92: istore_3
    //   93: aload 8
    //   95: ifnull +49 -> 144
    //   98: aload 8
    //   100: ldc_w 579
    //   103: invokevirtual 324	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   106: ifne +36 -> 142
    //   109: aload 8
    //   111: ldc_w 581
    //   114: invokevirtual 324	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   117: istore_3
    //   118: iload_3
    //   119: ifeq +6 -> 125
    //   122: goto +20 -> 142
    //   125: iload_1
    //   126: iconst_1
    //   127: iadd
    //   128: istore_2
    //   129: iload_2
    //   130: istore_1
    //   131: iload_2
    //   132: iconst_5
    //   133: if_icmplt -50 -> 83
    //   136: iload 4
    //   138: istore_3
    //   139: goto +5 -> 144
    //   142: iconst_1
    //   143: istore_3
    //   144: aload_0
    //   145: aload 6
    //   147: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   150: aload 10
    //   152: astore 6
    //   154: goto +128 -> 282
    //   157: astore 8
    //   159: aload 6
    //   161: astore 9
    //   163: aload 8
    //   165: astore 6
    //   167: aload 10
    //   169: astore 8
    //   171: goto +139 -> 310
    //   174: astore 9
    //   176: aload 6
    //   178: astore 8
    //   180: aload 10
    //   182: astore 6
    //   184: goto +84 -> 268
    //   187: astore 10
    //   189: aconst_null
    //   190: astore 8
    //   192: aload 6
    //   194: astore 9
    //   196: aload 10
    //   198: astore 6
    //   200: goto +110 -> 310
    //   203: astore 9
    //   205: aconst_null
    //   206: astore 10
    //   208: aload 6
    //   210: astore 8
    //   212: aload 10
    //   214: astore 6
    //   216: goto +52 -> 268
    //   219: astore 7
    //   221: aconst_null
    //   222: astore 8
    //   224: goto +98 -> 322
    //   227: astore 9
    //   229: aconst_null
    //   230: astore 10
    //   232: aload 10
    //   234: astore 7
    //   236: aload 6
    //   238: astore 8
    //   240: aload 10
    //   242: astore 6
    //   244: goto +24 -> 268
    //   247: astore 7
    //   249: aconst_null
    //   250: astore 6
    //   252: aload 6
    //   254: astore 8
    //   256: goto +66 -> 322
    //   259: astore 9
    //   261: aconst_null
    //   262: astore 7
    //   264: aload 7
    //   266: astore 6
    //   268: aload 9
    //   270: invokevirtual 584	java/lang/Throwable:printStackTrace	()V
    //   273: aload_0
    //   274: aload 8
    //   276: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   279: iload 5
    //   281: istore_3
    //   282: aload_0
    //   283: aload 7
    //   285: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   288: aload_0
    //   289: aload 6
    //   291: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   294: iload_3
    //   295: ireturn
    //   296: astore 10
    //   298: aload 8
    //   300: astore 9
    //   302: aload 6
    //   304: astore 8
    //   306: aload 10
    //   308: astore 6
    //   310: aload 7
    //   312: astore 10
    //   314: aload 6
    //   316: astore 7
    //   318: aload 9
    //   320: astore 6
    //   322: aload_0
    //   323: aload 6
    //   325: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   328: aload_0
    //   329: aload 10
    //   331: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   334: aload_0
    //   335: aload 8
    //   337: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   340: aload 7
    //   342: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	343	0	this	TbsApkDownloader
    //   82	49	1	j	int
    //   128	6	2	k	int
    //   92	203	3	bool1	boolean
    //   9	128	4	bool2	boolean
    //   6	274	5	bool3	boolean
    //   3	321	6	localObject1	Object
    //   24	51	7	localObject2	Object
    //   219	1	7	localObject3	Object
    //   234	1	7	localObject4	Object
    //   247	1	7	localObject5	Object
    //   262	79	7	localObject6	Object
    //   15	95	8	str	String
    //   157	7	8	localObject7	Object
    //   169	167	8	localObject8	Object
    //   161	1	9	localObject9	Object
    //   174	1	9	localThrowable1	Throwable
    //   194	1	9	localObject10	Object
    //   203	1	9	localThrowable2	Throwable
    //   227	1	9	localThrowable3	Throwable
    //   259	10	9	localThrowable4	Throwable
    //   300	19	9	localObject11	Object
    //   12	169	10	localBufferedReader	java.io.BufferedReader
    //   187	10	10	localObject12	Object
    //   206	35	10	localObject13	Object
    //   296	11	10	localObject14	Object
    //   312	18	10	localObject15	Object
    // Exception table:
    //   from	to	target	type
    //   83	90	157	finally
    //   98	118	157	finally
    //   83	90	174	java/lang/Throwable
    //   98	118	174	java/lang/Throwable
    //   70	81	187	finally
    //   70	81	203	java/lang/Throwable
    //   59	70	219	finally
    //   59	70	227	java/lang/Throwable
    //   17	59	247	finally
    //   17	59	259	java/lang/Throwable
    //   268	273	296	finally
  }
  
  private boolean b(boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[TbsApkDownloader.deleteFile] isApk=");
    ((StringBuilder)localObject).append(paramBoolean);
    h.a("TbsDownload", ((StringBuilder)localObject).toString());
    if (paramBoolean) {
      localObject = new File(this.jdField_a_of_type_JavaIoFile, "x5.tbs");
    } else {
      localObject = new File(this.jdField_a_of_type_JavaIoFile, "x5.tbs.temp");
    }
    if (((File)localObject).exists()) {
      FileUtil.a((File)localObject);
    }
    return true;
  }
  
  private void c()
  {
    this.f = 0;
    this.g = 0;
    this.jdField_a_of_type_Long = -1L;
    this.jdField_c_of_type_JavaLangString = null;
    this.jdField_a_of_type_Boolean = false;
    this.jdField_b_of_type_Boolean = false;
    this.jdField_c_of_type_Boolean = false;
    this.jdField_d_of_type_Boolean = false;
  }
  
  /* Error */
  private boolean c()
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4: invokestatic 611	com/tencent/smtt/downloader/utils/a:a	(Landroid/content/Context;)I
    //   7: istore_1
    //   8: iconst_0
    //   9: istore 4
    //   11: iconst_0
    //   12: istore 5
    //   14: iconst_0
    //   15: istore_3
    //   16: iload_1
    //   17: iconst_3
    //   18: if_icmpne +8 -> 26
    //   21: iconst_1
    //   22: istore_2
    //   23: goto +5 -> 28
    //   26: iconst_0
    //   27: istore_2
    //   28: new 89	java/lang/StringBuilder
    //   31: dup
    //   32: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   35: astore 6
    //   37: aload 6
    //   39: ldc_w 613
    //   42: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   45: pop
    //   46: aload 6
    //   48: iload_2
    //   49: invokevirtual 462	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   52: pop
    //   53: ldc -69
    //   55: aload 6
    //   57: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   60: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   63: aconst_null
    //   64: astore 8
    //   66: aconst_null
    //   67: astore 6
    //   69: iload_2
    //   70: ifeq +271 -> 341
    //   73: aload_0
    //   74: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   77: invokestatic 615	com/tencent/smtt/downloader/utils/a:b	(Landroid/content/Context;)Ljava/lang/String;
    //   80: astore 9
    //   82: new 89	java/lang/StringBuilder
    //   85: dup
    //   86: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   89: astore 7
    //   91: aload 7
    //   93: ldc_w 617
    //   96: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   99: pop
    //   100: aload 7
    //   102: aload 9
    //   104: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   107: pop
    //   108: ldc -69
    //   110: aload 7
    //   112: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   115: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   118: new 211	java/net/URL
    //   121: dup
    //   122: ldc_w 619
    //   125: invokespecial 356	java/net/URL:<init>	(Ljava/lang/String;)V
    //   128: invokevirtual 370	java/net/URL:openConnection	()Ljava/net/URLConnection;
    //   131: checkcast 360	java/net/HttpURLConnection
    //   134: astore 7
    //   136: aload 7
    //   138: iconst_0
    //   139: invokevirtual 391	java/net/HttpURLConnection:setInstanceFollowRedirects	(Z)V
    //   142: aload 7
    //   144: sipush 10000
    //   147: invokevirtual 394	java/net/HttpURLConnection:setConnectTimeout	(I)V
    //   150: aload 7
    //   152: sipush 10000
    //   155: invokevirtual 397	java/net/HttpURLConnection:setReadTimeout	(I)V
    //   158: aload 7
    //   160: iconst_0
    //   161: invokevirtual 622	java/net/HttpURLConnection:setUseCaches	(Z)V
    //   164: aload 7
    //   166: invokevirtual 623	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   169: pop
    //   170: aload 7
    //   172: invokevirtual 626	java/net/HttpURLConnection:getResponseCode	()I
    //   175: istore_1
    //   176: new 89	java/lang/StringBuilder
    //   179: dup
    //   180: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   183: astore 6
    //   185: aload 6
    //   187: ldc_w 628
    //   190: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   193: pop
    //   194: aload 6
    //   196: iload_1
    //   197: invokevirtual 333	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   200: pop
    //   201: ldc -69
    //   203: aload 6
    //   205: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   208: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   211: iload_3
    //   212: istore_2
    //   213: iload_1
    //   214: sipush 204
    //   217: if_icmpne +5 -> 222
    //   220: iconst_1
    //   221: istore_2
    //   222: aload 9
    //   224: astore 6
    //   226: iload_2
    //   227: istore_3
    //   228: aload 7
    //   230: ifnull +117 -> 347
    //   233: iload_2
    //   234: istore_3
    //   235: aload 7
    //   237: invokevirtual 363	java/net/HttpURLConnection:disconnect	()V
    //   240: aload 9
    //   242: astore 6
    //   244: iload_2
    //   245: istore_3
    //   246: goto +101 -> 347
    //   249: aload 9
    //   251: astore 6
    //   253: goto +94 -> 347
    //   256: astore 6
    //   258: goto +70 -> 328
    //   261: astore 8
    //   263: goto +26 -> 289
    //   266: astore 8
    //   268: aload 6
    //   270: astore 7
    //   272: aload 8
    //   274: astore 6
    //   276: goto +52 -> 328
    //   279: astore 6
    //   281: aload 8
    //   283: astore 7
    //   285: aload 6
    //   287: astore 8
    //   289: aload 7
    //   291: astore 6
    //   293: aload 8
    //   295: invokevirtual 584	java/lang/Throwable:printStackTrace	()V
    //   298: aload 9
    //   300: astore 6
    //   302: iload 4
    //   304: istore_3
    //   305: aload 7
    //   307: ifnull +40 -> 347
    //   310: iload 5
    //   312: istore_3
    //   313: aload 7
    //   315: invokevirtual 363	java/net/HttpURLConnection:disconnect	()V
    //   318: aload 9
    //   320: astore 6
    //   322: iload 4
    //   324: istore_3
    //   325: goto +22 -> 347
    //   328: aload 7
    //   330: ifnull +8 -> 338
    //   333: aload 7
    //   335: invokevirtual 363	java/net/HttpURLConnection:disconnect	()V
    //   338: aload 6
    //   340: athrow
    //   341: aconst_null
    //   342: astore 6
    //   344: iload 4
    //   346: istore_3
    //   347: iload_3
    //   348: ifne +68 -> 416
    //   351: aload 6
    //   353: invokestatic 633	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   356: ifne +60 -> 416
    //   359: aload_0
    //   360: getfield 87	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaUtilSet	Ljava/util/Set;
    //   363: aload 6
    //   365: invokeinterface 637 2 0
    //   370: ifne +46 -> 416
    //   373: aload_0
    //   374: getfield 87	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaUtilSet	Ljava/util/Set;
    //   377: aload 6
    //   379: invokeinterface 640 2 0
    //   384: pop
    //   385: aload_0
    //   386: invokespecial 642	com/tencent/smtt/downloader/TbsApkDownloader:e	()V
    //   389: aload_0
    //   390: getfield 644	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidOsHandler	Landroid/os/Handler;
    //   393: sipush 150
    //   396: aload 6
    //   398: invokevirtual 650	android/os/Handler:obtainMessage	(ILjava/lang/Object;)Landroid/os/Message;
    //   401: astore 7
    //   403: aload_0
    //   404: getfield 644	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidOsHandler	Landroid/os/Handler;
    //   407: aload 7
    //   409: ldc2_w 651
    //   412: invokevirtual 656	android/os/Handler:sendMessageDelayed	(Landroid/os/Message;J)Z
    //   415: pop
    //   416: iload_3
    //   417: ifeq +29 -> 446
    //   420: aload_0
    //   421: getfield 87	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaUtilSet	Ljava/util/Set;
    //   424: aload 6
    //   426: invokeinterface 637 2 0
    //   431: ifeq +15 -> 446
    //   434: aload_0
    //   435: getfield 87	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaUtilSet	Ljava/util/Set;
    //   438: aload 6
    //   440: invokeinterface 659 2 0
    //   445: pop
    //   446: iload_3
    //   447: ireturn
    //   448: astore 6
    //   450: goto -201 -> 249
    //   453: astore 7
    //   455: goto -117 -> 338
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	458	0	this	TbsApkDownloader
    //   7	211	1	j	int
    //   22	223	2	bool1	boolean
    //   15	432	3	bool2	boolean
    //   9	336	4	bool3	boolean
    //   12	299	5	bool4	boolean
    //   35	217	6	localObject1	Object
    //   256	13	6	localObject2	Object
    //   274	1	6	localObject3	Object
    //   279	7	6	localThrowable1	Throwable
    //   291	148	6	localObject4	Object
    //   448	1	6	localException1	Exception
    //   89	319	7	localObject5	Object
    //   453	1	7	localException2	Exception
    //   64	1	8	localObject6	Object
    //   261	1	8	localThrowable2	Throwable
    //   266	16	8	localObject7	Object
    //   287	7	8	localThrowable3	Throwable
    //   80	239	9	str	String
    // Exception table:
    //   from	to	target	type
    //   136	211	256	finally
    //   136	211	261	java/lang/Throwable
    //   118	136	266	finally
    //   293	298	266	finally
    //   118	136	279	java/lang/Throwable
    //   235	240	448	java/lang/Exception
    //   313	318	448	java/lang/Exception
    //   333	338	453	java/lang/Exception
  }
  
  private void d()
  {
    h.a("TbsDownload", "[TbsApkDownloader.closeHttpRequest]");
    HttpURLConnection localHttpURLConnection = this.jdField_a_of_type_JavaNetHttpURLConnection;
    Object localObject2;
    if (localHttpURLConnection != null)
    {
      if (!this.jdField_b_of_type_Boolean) {
        this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.b(a(localHttpURLConnection.getURL()));
      }
      try
      {
        this.jdField_a_of_type_JavaNetHttpURLConnection.disconnect();
      }
      catch (Throwable localThrowable)
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("[closeHttpRequest] mHttpRequest.disconnect() Throwable:");
        ((StringBuilder)localObject2).append(localThrowable.toString());
        h.b("TbsDownload", ((StringBuilder)localObject2).toString());
      }
      this.jdField_a_of_type_JavaNetHttpURLConnection = null;
    }
    int j = this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.jdField_a_of_type_Int;
    if ((!this.jdField_b_of_type_Boolean) && (this.jdField_d_of_type_Boolean))
    {
      this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.a(System.currentTimeMillis());
      localObject2 = com.tencent.smtt.downloader.utils.a.a(this.jdField_a_of_type_AndroidContentContext);
      Object localObject1 = localObject2;
      if (localObject2 == null) {
        localObject1 = "";
      }
      int k = com.tencent.smtt.downloader.utils.a.a(this.jdField_a_of_type_AndroidContentContext);
      this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.c((String)localObject1);
      this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.e(k);
      if ((k != this.h) || (!((String)localObject1).equals(this.jdField_e_of_type_JavaLangString))) {
        this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.g(0);
      }
      if (((this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.jdField_a_of_type_Int == 0) || (this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.jdField_a_of_type_Int == 107)) && (this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.a() == 0)) {
        if (!com.tencent.smtt.downloader.utils.a.a(this.jdField_a_of_type_AndroidContentContext)) {
          a(101, null, true);
        } else if (!b()) {
          a(101, null, true);
        }
      }
      if (TbsDownloader.a(this.jdField_a_of_type_AndroidContentContext)) {
        i.a(this.jdField_a_of_type_AndroidContentContext).a(i.a.d, this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b);
      } else {
        i.a(this.jdField_a_of_type_AndroidContentContext).a(i.a.a, this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b);
      }
      this.jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b.a();
      if (j != 100) {
        a.a.onDownloadFinish(j);
      }
    }
    else
    {
      TbsDownloader.jdField_b_of_type_Boolean = false;
    }
  }
  
  private void e()
  {
    if (this.jdField_a_of_type_AndroidOsHandler == null) {
      this.jdField_a_of_type_AndroidOsHandler = new Handler(d.a().getLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramAnonymousMessage.what == 150) {
            TbsApkDownloader.a(TbsApkDownloader.this);
          }
        }
      };
    }
  }
  
  public int a(boolean paramBoolean)
  {
    return 0;
  }
  
  public Bundle a(int paramInt, boolean paramBoolean)
  {
    return null;
  }
  
  public void a()
  {
    this.jdField_b_of_type_Boolean = true;
  }
  
  public void a(int paramInt)
  {
    if (e.a().c(this.jdField_a_of_type_AndroidContentContext)) {
      e.a().a();
    }
    try
    {
      File localFile = new File(this.jdField_a_of_type_JavaIoFile, "x5.tbs");
      int j = ApkUtil.a(this.jdField_a_of_type_AndroidContentContext, localFile);
      if ((-1 == j) || ((paramInt > 0) && (paramInt == j))) {
        FileUtil.a(localFile);
      }
      return;
    }
    catch (Exception localException) {}
  }
  
  /* Error */
  public void a(boolean paramBoolean1, boolean paramBoolean2)
  {
    // Byte code:
    //   0: invokestatic 110	com/tencent/smtt/downloader/e:a	()Lcom/tencent/smtt/downloader/e;
    //   3: aload_0
    //   4: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   7: invokevirtual 716	com/tencent/smtt/downloader/e:a	(Landroid/content/Context;)Z
    //   10: ifeq +25 -> 35
    //   13: iload_1
    //   14: ifne +21 -> 35
    //   17: iconst_0
    //   18: putstatic 698	com/tencent/smtt/downloader/TbsDownloader:jdField_b_of_type_Boolean	Z
    //   21: aload_0
    //   22: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   25: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   28: sipush 65214
    //   31: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   34: return
    //   35: aload_0
    //   36: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   39: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   42: getfield 304	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   45: ldc_w 345
    //   48: iconst_0
    //   49: invokeinterface 312 3 0
    //   54: istore 7
    //   56: iload 7
    //   58: iconst_1
    //   59: if_icmpeq +24 -> 83
    //   62: iload 7
    //   64: iconst_2
    //   65: if_icmpeq +18 -> 83
    //   68: iload 7
    //   70: iconst_4
    //   71: if_icmpne +6 -> 77
    //   74: goto +9 -> 83
    //   77: iconst_0
    //   78: istore 11
    //   80: goto +6 -> 86
    //   83: iconst_1
    //   84: istore 11
    //   86: aload_0
    //   87: iload_1
    //   88: putfield 720	com/tencent/smtt/downloader/TbsApkDownloader:jdField_e_of_type_Boolean	Z
    //   91: aload_0
    //   92: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   95: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   98: getfield 304	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   101: astore 36
    //   103: aconst_null
    //   104: astore 35
    //   106: aload_0
    //   107: aload 36
    //   109: ldc_w 722
    //   112: aconst_null
    //   113: invokeinterface 468 3 0
    //   118: putfield 724	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   121: aload_0
    //   122: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   125: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   128: getfield 304	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   131: ldc_w 726
    //   134: aconst_null
    //   135: invokeinterface 468 3 0
    //   140: astore 36
    //   142: new 89	java/lang/StringBuilder
    //   145: dup
    //   146: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   149: astore 37
    //   151: aload 37
    //   153: ldc_w 728
    //   156: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   159: pop
    //   160: aload 37
    //   162: aload 36
    //   164: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   167: pop
    //   168: ldc -69
    //   170: aload 37
    //   172: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   175: iconst_1
    //   176: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   179: aload_0
    //   180: aconst_null
    //   181: putfield 62	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ArrayOfJavaLangString	[Ljava/lang/String;
    //   184: aload_0
    //   185: iconst_0
    //   186: putfield 64	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Int	I
    //   189: iload_1
    //   190: ifne +36 -> 226
    //   193: aload 36
    //   195: ifnull +31 -> 226
    //   198: ldc -30
    //   200: aload 36
    //   202: invokevirtual 734	java/lang/String:trim	()Ljava/lang/String;
    //   205: invokevirtual 475	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   208: ifne +18 -> 226
    //   211: aload_0
    //   212: aload 36
    //   214: invokevirtual 734	java/lang/String:trim	()Ljava/lang/String;
    //   217: ldc_w 736
    //   220: invokevirtual 740	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
    //   223: putfield 62	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ArrayOfJavaLangString	[Ljava/lang/String;
    //   226: new 89	java/lang/StringBuilder
    //   229: dup
    //   230: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   233: astore 37
    //   235: aload 37
    //   237: ldc_w 742
    //   240: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   243: pop
    //   244: aload 37
    //   246: aload_0
    //   247: getfield 724	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   250: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   253: pop
    //   254: aload 37
    //   256: ldc_w 744
    //   259: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   262: pop
    //   263: aload 37
    //   265: aload 36
    //   267: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   270: pop
    //   271: aload 37
    //   273: ldc_w 746
    //   276: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   279: pop
    //   280: aload 37
    //   282: aload_0
    //   283: getfield 598	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_JavaLangString	Ljava/lang/String;
    //   286: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   289: pop
    //   290: aload 37
    //   292: ldc_w 748
    //   295: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   298: pop
    //   299: aload 37
    //   301: aload_0
    //   302: getfield 602	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_Boolean	Z
    //   305: invokevirtual 462	java/lang/StringBuilder:append	(Z)Ljava/lang/StringBuilder;
    //   308: pop
    //   309: aload 37
    //   311: ldc_w 750
    //   314: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   317: pop
    //   318: aload 37
    //   320: aload_0
    //   321: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   324: invokevirtual 522	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   327: pop
    //   328: ldc -69
    //   330: aload 37
    //   332: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   335: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   338: aload_0
    //   339: getfield 724	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   342: ifnonnull +34 -> 376
    //   345: aload_0
    //   346: getfield 598	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_JavaLangString	Ljava/lang/String;
    //   349: ifnonnull +27 -> 376
    //   352: getstatic 434	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   355: bipush 110
    //   357: invokeinterface 439 2 0
    //   362: aload_0
    //   363: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   366: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   369: sipush 65234
    //   372: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   375: return
    //   376: aload_0
    //   377: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   380: ifnull +34 -> 414
    //   383: aload_0
    //   384: getfield 602	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_Boolean	Z
    //   387: ifne +27 -> 414
    //   390: getstatic 434	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   393: bipush 110
    //   395: invokeinterface 439 2 0
    //   400: aload_0
    //   401: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   404: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   407: sipush 65233
    //   410: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   413: return
    //   414: iload_1
    //   415: ifne +54 -> 469
    //   418: aload_0
    //   419: getfield 87	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaUtilSet	Ljava/util/Set;
    //   422: aload_0
    //   423: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   426: invokestatic 615	com/tencent/smtt/downloader/utils/a:b	(Landroid/content/Context;)Ljava/lang/String;
    //   429: invokeinterface 637 2 0
    //   434: ifeq +35 -> 469
    //   437: ldc -69
    //   439: ldc_w 752
    //   442: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   445: getstatic 434	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   448: bipush 110
    //   450: invokeinterface 439 2 0
    //   455: aload_0
    //   456: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   459: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   462: sipush 65232
    //   465: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   468: return
    //   469: aload_0
    //   470: invokespecial 117	com/tencent/smtt/downloader/TbsApkDownloader:c	()V
    //   473: ldc -69
    //   475: ldc_w 754
    //   478: iconst_1
    //   479: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   482: aload_0
    //   483: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   486: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   489: invokevirtual 756	com/tencent/smtt/downloader/c:a	()J
    //   492: lstore 19
    //   494: aload_0
    //   495: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   498: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   501: getfield 304	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   504: ldc_w 758
    //   507: lconst_0
    //   508: invokeinterface 485 4 0
    //   513: lstore 15
    //   515: iload_1
    //   516: ifeq +13 -> 529
    //   519: aload_0
    //   520: getstatic 760	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_Int	I
    //   523: putfield 60	com/tencent/smtt/downloader/TbsApkDownloader:i	I
    //   526: goto +10 -> 536
    //   529: aload_0
    //   530: getstatic 58	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_Int	I
    //   533: putfield 60	com/tencent/smtt/downloader/TbsApkDownloader:i	I
    //   536: iconst_0
    //   537: istore_2
    //   538: aload_0
    //   539: getfield 229	com/tencent/smtt/downloader/TbsApkDownloader:f	I
    //   542: aload_0
    //   543: getfield 60	com/tencent/smtt/downloader/TbsApkDownloader:i	I
    //   546: if_icmple +9 -> 555
    //   549: iload_2
    //   550: istore 9
    //   552: goto +2547 -> 3099
    //   555: aload_0
    //   556: getfield 592	com/tencent/smtt/downloader/TbsApkDownloader:g	I
    //   559: bipush 8
    //   561: if_icmple +31 -> 592
    //   564: aload_0
    //   565: bipush 123
    //   567: aload 35
    //   569: iconst_1
    //   570: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   573: aload_0
    //   574: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   577: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   580: sipush 65230
    //   583: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   586: iload_2
    //   587: istore 9
    //   589: goto +2510 -> 3099
    //   592: invokestatic 148	java/lang/System:currentTimeMillis	()J
    //   595: lstore 31
    //   597: lload 15
    //   599: lstore 13
    //   601: iload_1
    //   602: ifne +431 -> 1033
    //   605: lload 15
    //   607: lstore 23
    //   609: lload 15
    //   611: lstore 17
    //   613: lload 31
    //   615: aload_0
    //   616: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   619: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   622: getfield 304	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   625: ldc_w 762
    //   628: lconst_0
    //   629: invokeinterface 485 4 0
    //   634: lsub
    //   635: ldc2_w 763
    //   638: lcmp
    //   639: ifle +106 -> 745
    //   642: lload 15
    //   644: lstore 23
    //   646: lload 15
    //   648: lstore 17
    //   650: ldc -69
    //   652: ldc_w 766
    //   655: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   658: lload 15
    //   660: lstore 23
    //   662: lload 15
    //   664: lstore 17
    //   666: aload_0
    //   667: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   670: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   673: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   676: ldc_w 762
    //   679: lload 31
    //   681: invokestatic 771	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   684: invokeinterface 418 3 0
    //   689: pop
    //   690: lload 15
    //   692: lstore 23
    //   694: lload 15
    //   696: lstore 17
    //   698: aload_0
    //   699: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   702: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   705: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   708: ldc_w 758
    //   711: lconst_0
    //   712: invokestatic 771	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   715: invokeinterface 418 3 0
    //   720: pop
    //   721: lload 15
    //   723: lstore 23
    //   725: lload 15
    //   727: lstore 17
    //   729: aload_0
    //   730: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   733: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   736: invokevirtual 429	com/tencent/smtt/downloader/c:a	()V
    //   739: lconst_0
    //   740: lstore 21
    //   742: goto +188 -> 930
    //   745: lload 15
    //   747: lstore 23
    //   749: lload 15
    //   751: lstore 17
    //   753: new 89	java/lang/StringBuilder
    //   756: dup
    //   757: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   760: astore 35
    //   762: lload 15
    //   764: lstore 23
    //   766: lload 15
    //   768: lstore 17
    //   770: aload 35
    //   772: ldc_w 773
    //   775: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   778: pop
    //   779: lload 15
    //   781: lstore 23
    //   783: lload 15
    //   785: lstore 17
    //   787: aload 35
    //   789: lload 15
    //   791: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   794: pop
    //   795: lload 15
    //   797: lstore 23
    //   799: lload 15
    //   801: lstore 17
    //   803: ldc -69
    //   805: aload 35
    //   807: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   810: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   813: lload 15
    //   815: lstore 21
    //   817: lload 15
    //   819: lload 19
    //   821: lcmp
    //   822: iflt +108 -> 930
    //   825: lload 15
    //   827: lstore 23
    //   829: lload 15
    //   831: lstore 17
    //   833: ldc -69
    //   835: ldc_w 775
    //   838: iconst_1
    //   839: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   842: lload 15
    //   844: lstore 23
    //   846: lload 15
    //   848: lstore 17
    //   850: aload_0
    //   851: bipush 112
    //   853: aconst_null
    //   854: iconst_1
    //   855: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   858: lload 15
    //   860: lstore 23
    //   862: lload 15
    //   864: lstore 17
    //   866: aload_0
    //   867: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   870: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   873: sipush 65229
    //   876: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   879: iload_2
    //   880: istore 9
    //   882: iload_1
    //   883: ifne +2216 -> 3099
    //   886: lload 15
    //   888: lstore 13
    //   890: aload_0
    //   891: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   894: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   897: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   900: ldc_w 758
    //   903: lload 13
    //   905: invokestatic 771	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   908: invokeinterface 418 3 0
    //   913: pop
    //   914: aload_0
    //   915: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   918: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   921: invokevirtual 429	com/tencent/smtt/downloader/c:a	()V
    //   924: iload_2
    //   925: istore 9
    //   927: goto +2172 -> 3099
    //   930: lload 21
    //   932: lstore 23
    //   934: lload 21
    //   936: lstore 13
    //   938: lload 21
    //   940: lstore 17
    //   942: aload_0
    //   943: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   946: invokestatic 776	com/tencent/smtt/downloader/utils/FileUtil:a	(Landroid/content/Context;)Z
    //   949: ifne +84 -> 1033
    //   952: lload 21
    //   954: lstore 23
    //   956: lload 21
    //   958: lstore 17
    //   960: ldc -69
    //   962: ldc_w 778
    //   965: iconst_1
    //   966: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   969: lload 21
    //   971: lstore 23
    //   973: lload 21
    //   975: lstore 17
    //   977: aload_0
    //   978: bipush 105
    //   980: aconst_null
    //   981: iconst_1
    //   982: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   985: lload 21
    //   987: lstore 23
    //   989: lload 21
    //   991: lstore 17
    //   993: aload_0
    //   994: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   997: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   1000: sipush 65228
    //   1003: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   1006: iload_2
    //   1007: istore 9
    //   1009: iload_1
    //   1010: ifne +2089 -> 3099
    //   1013: lload 21
    //   1015: lstore 13
    //   1017: goto -127 -> 890
    //   1020: astore 35
    //   1022: lload 19
    //   1024: lstore 15
    //   1026: lload 23
    //   1028: lstore 13
    //   1030: goto +4370 -> 5400
    //   1033: lload 13
    //   1035: lstore 17
    //   1037: aload_0
    //   1038: iconst_1
    //   1039: putfield 606	com/tencent/smtt/downloader/TbsApkDownloader:jdField_d_of_type_Boolean	Z
    //   1042: lload 13
    //   1044: lstore 17
    //   1046: aload_0
    //   1047: getfield 598	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_JavaLangString	Ljava/lang/String;
    //   1050: astore 35
    //   1052: aload 35
    //   1054: ifnull +20 -> 1074
    //   1057: lload 13
    //   1059: lstore 23
    //   1061: lload 13
    //   1063: lstore 17
    //   1065: aload_0
    //   1066: getfield 598	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_JavaLangString	Ljava/lang/String;
    //   1069: astore 35
    //   1071: goto +13 -> 1084
    //   1074: lload 13
    //   1076: lstore 17
    //   1078: aload_0
    //   1079: getfield 724	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1082: astore 35
    //   1084: lload 13
    //   1086: lstore 17
    //   1088: new 89	java/lang/StringBuilder
    //   1091: dup
    //   1092: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   1095: astore 36
    //   1097: lload 13
    //   1099: lstore 17
    //   1101: aload 36
    //   1103: ldc_w 780
    //   1106: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1109: pop
    //   1110: lload 13
    //   1112: lstore 17
    //   1114: aload 36
    //   1116: aload 35
    //   1118: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1121: pop
    //   1122: lload 13
    //   1124: lstore 17
    //   1126: aload 36
    //   1128: ldc_w 782
    //   1131: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1134: pop
    //   1135: lload 13
    //   1137: lstore 17
    //   1139: aload 36
    //   1141: aload_0
    //   1142: getfield 229	com/tencent/smtt/downloader/TbsApkDownloader:f	I
    //   1145: invokevirtual 333	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1148: pop
    //   1149: lload 13
    //   1151: lstore 17
    //   1153: ldc -69
    //   1155: aload 36
    //   1157: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1160: iconst_1
    //   1161: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   1164: lload 13
    //   1166: lstore 17
    //   1168: aload 35
    //   1170: aload_0
    //   1171: getfield 784	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_JavaLangString	Ljava/lang/String;
    //   1174: invokevirtual 475	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1177: istore 9
    //   1179: iload 9
    //   1181: ifne +20 -> 1201
    //   1184: lload 13
    //   1186: lstore 23
    //   1188: lload 13
    //   1190: lstore 17
    //   1192: aload_0
    //   1193: getfield 82	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b	Lcom/tencent/smtt/downloader/utils/i$b;
    //   1196: aload 35
    //   1198: invokevirtual 786	com/tencent/smtt/downloader/utils/i$b:a	(Ljava/lang/String;)V
    //   1201: lload 13
    //   1203: lstore 17
    //   1205: aload_0
    //   1206: aload 35
    //   1208: putfield 784	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_JavaLangString	Ljava/lang/String;
    //   1211: lload 13
    //   1213: lstore 17
    //   1215: aload_0
    //   1216: aload 35
    //   1218: invokespecial 787	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/lang/String;)V
    //   1221: lload 13
    //   1223: lstore 17
    //   1225: aload_0
    //   1226: getfield 600	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Boolean	Z
    //   1229: istore 9
    //   1231: iload 9
    //   1233: ifne +416 -> 1649
    //   1236: lload 13
    //   1238: lstore 23
    //   1240: lload 13
    //   1242: lstore 17
    //   1244: aload_0
    //   1245: invokespecial 788	com/tencent/smtt/downloader/TbsApkDownloader:a	()J
    //   1248: lstore 21
    //   1250: lload 13
    //   1252: lstore 23
    //   1254: lload 13
    //   1256: lstore 17
    //   1258: new 89	java/lang/StringBuilder
    //   1261: dup
    //   1262: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   1265: astore 35
    //   1267: lload 13
    //   1269: lstore 23
    //   1271: lload 13
    //   1273: lstore 17
    //   1275: aload 35
    //   1277: ldc_w 790
    //   1280: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1283: pop
    //   1284: lload 13
    //   1286: lstore 23
    //   1288: lload 13
    //   1290: lstore 17
    //   1292: aload 35
    //   1294: lload 21
    //   1296: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1299: pop
    //   1300: lload 13
    //   1302: lstore 23
    //   1304: lload 13
    //   1306: lstore 17
    //   1308: ldc -69
    //   1310: aload 35
    //   1312: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1315: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   1318: lload 13
    //   1320: lstore 17
    //   1322: aload_0
    //   1323: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   1326: lconst_0
    //   1327: lcmp
    //   1328: ifgt +137 -> 1465
    //   1331: lload 13
    //   1333: lstore 17
    //   1335: new 89	java/lang/StringBuilder
    //   1338: dup
    //   1339: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   1342: astore 35
    //   1344: lload 13
    //   1346: lstore 17
    //   1348: aload 35
    //   1350: ldc_w 792
    //   1353: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1356: pop
    //   1357: lload 13
    //   1359: lstore 17
    //   1361: aload 35
    //   1363: lload 21
    //   1365: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1368: pop
    //   1369: lload 13
    //   1371: lstore 17
    //   1373: ldc -69
    //   1375: aload 35
    //   1377: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1380: iconst_1
    //   1381: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   1384: lload 13
    //   1386: lstore 17
    //   1388: aload_0
    //   1389: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   1392: astore 35
    //   1394: lload 13
    //   1396: lstore 17
    //   1398: new 89	java/lang/StringBuilder
    //   1401: dup
    //   1402: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   1405: astore 36
    //   1407: lload 13
    //   1409: lstore 17
    //   1411: aload 36
    //   1413: ldc_w 794
    //   1416: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1419: pop
    //   1420: lload 13
    //   1422: lstore 17
    //   1424: aload 36
    //   1426: lload 21
    //   1428: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1431: pop
    //   1432: lload 13
    //   1434: lstore 17
    //   1436: aload 36
    //   1438: ldc_w 796
    //   1441: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1444: pop
    //   1445: lload 13
    //   1447: lstore 17
    //   1449: aload 35
    //   1451: ldc_w 798
    //   1454: aload 36
    //   1456: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1459: invokevirtual 378	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   1462: goto +190 -> 1652
    //   1465: lload 13
    //   1467: lstore 17
    //   1469: new 89	java/lang/StringBuilder
    //   1472: dup
    //   1473: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   1476: astore 35
    //   1478: lload 13
    //   1480: lstore 17
    //   1482: aload 35
    //   1484: ldc_w 800
    //   1487: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1490: pop
    //   1491: lload 13
    //   1493: lstore 17
    //   1495: aload 35
    //   1497: lload 21
    //   1499: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1502: pop
    //   1503: lload 13
    //   1505: lstore 17
    //   1507: aload 35
    //   1509: ldc_w 802
    //   1512: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1515: pop
    //   1516: lload 13
    //   1518: lstore 17
    //   1520: aload 35
    //   1522: aload_0
    //   1523: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   1526: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1529: pop
    //   1530: lload 13
    //   1532: lstore 17
    //   1534: ldc -69
    //   1536: aload 35
    //   1538: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1541: iconst_1
    //   1542: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   1545: lload 13
    //   1547: lstore 17
    //   1549: aload_0
    //   1550: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   1553: astore 35
    //   1555: lload 13
    //   1557: lstore 17
    //   1559: new 89	java/lang/StringBuilder
    //   1562: dup
    //   1563: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   1566: astore 36
    //   1568: lload 13
    //   1570: lstore 17
    //   1572: aload 36
    //   1574: ldc_w 794
    //   1577: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1580: pop
    //   1581: lload 13
    //   1583: lstore 17
    //   1585: aload 36
    //   1587: lload 21
    //   1589: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1592: pop
    //   1593: lload 13
    //   1595: lstore 17
    //   1597: aload 36
    //   1599: ldc_w 796
    //   1602: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1605: pop
    //   1606: lload 13
    //   1608: lstore 17
    //   1610: aload 36
    //   1612: aload_0
    //   1613: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   1616: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   1619: pop
    //   1620: lload 13
    //   1622: lstore 17
    //   1624: aload 35
    //   1626: ldc_w 798
    //   1629: aload 36
    //   1631: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1634: invokevirtual 378	java/net/HttpURLConnection:setRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   1637: goto +15 -> 1652
    //   1640: astore 35
    //   1642: lload 19
    //   1644: lstore 15
    //   1646: goto -616 -> 1030
    //   1649: lconst_0
    //   1650: lstore 21
    //   1652: lload 19
    //   1654: lstore 15
    //   1656: lload 13
    //   1658: lstore 17
    //   1660: aload_0
    //   1661: getfield 82	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b	Lcom/tencent/smtt/downloader/utils/i$b;
    //   1664: astore 35
    //   1666: lload 21
    //   1668: lconst_0
    //   1669: lcmp
    //   1670: ifne +4369 -> 6039
    //   1673: iconst_0
    //   1674: istore 7
    //   1676: goto +3 -> 1679
    //   1679: lload 13
    //   1681: lstore 17
    //   1683: aload 35
    //   1685: iload 7
    //   1687: invokevirtual 804	com/tencent/smtt/downloader/utils/i$b:c	(I)V
    //   1690: lload 13
    //   1692: lstore 17
    //   1694: aload_0
    //   1695: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   1698: invokestatic 611	com/tencent/smtt/downloader/utils/a:a	(Landroid/content/Context;)I
    //   1701: istore 7
    //   1703: lload 13
    //   1705: lstore 17
    //   1707: aload_0
    //   1708: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   1711: invokestatic 675	com/tencent/smtt/downloader/utils/a:a	(Landroid/content/Context;)Ljava/lang/String;
    //   1714: astore 35
    //   1716: lload 13
    //   1718: lstore 17
    //   1720: aload_0
    //   1721: getfield 119	com/tencent/smtt/downloader/TbsApkDownloader:jdField_e_of_type_JavaLangString	Ljava/lang/String;
    //   1724: astore 36
    //   1726: aload 36
    //   1728: ifnonnull +38 -> 1766
    //   1731: lload 13
    //   1733: lstore 17
    //   1735: aload_0
    //   1736: getfield 121	com/tencent/smtt/downloader/TbsApkDownloader:h	I
    //   1739: iconst_m1
    //   1740: if_icmpne +26 -> 1766
    //   1743: lload 13
    //   1745: lstore 17
    //   1747: aload_0
    //   1748: aload 35
    //   1750: putfield 119	com/tencent/smtt/downloader/TbsApkDownloader:jdField_e_of_type_JavaLangString	Ljava/lang/String;
    //   1753: lload 13
    //   1755: lstore 17
    //   1757: aload_0
    //   1758: iload 7
    //   1760: putfield 121	com/tencent/smtt/downloader/TbsApkDownloader:h	I
    //   1763: goto +72 -> 1835
    //   1766: lload 13
    //   1768: lstore 17
    //   1770: aload_0
    //   1771: getfield 121	com/tencent/smtt/downloader/TbsApkDownloader:h	I
    //   1774: istore 8
    //   1776: iload 7
    //   1778: iload 8
    //   1780: if_icmpne +23 -> 1803
    //   1783: lload 13
    //   1785: lstore 17
    //   1787: aload 35
    //   1789: aload_0
    //   1790: getfield 119	com/tencent/smtt/downloader/TbsApkDownloader:jdField_e_of_type_JavaLangString	Ljava/lang/String;
    //   1793: invokevirtual 475	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   1796: istore 9
    //   1798: iload 9
    //   1800: ifne +35 -> 1835
    //   1803: lload 13
    //   1805: lstore 17
    //   1807: aload_0
    //   1808: getfield 82	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b	Lcom/tencent/smtt/downloader/utils/i$b;
    //   1811: iconst_0
    //   1812: invokevirtual 681	com/tencent/smtt/downloader/utils/i$b:g	(I)V
    //   1815: lload 13
    //   1817: lstore 17
    //   1819: aload_0
    //   1820: aload 35
    //   1822: putfield 119	com/tencent/smtt/downloader/TbsApkDownloader:jdField_e_of_type_JavaLangString	Ljava/lang/String;
    //   1825: lload 13
    //   1827: lstore 17
    //   1829: aload_0
    //   1830: iload 7
    //   1832: putfield 121	com/tencent/smtt/downloader/TbsApkDownloader:h	I
    //   1835: lload 13
    //   1837: lstore 17
    //   1839: aload_0
    //   1840: getfield 229	com/tencent/smtt/downloader/TbsApkDownloader:f	I
    //   1843: istore 7
    //   1845: iload 7
    //   1847: iconst_1
    //   1848: if_icmplt +21 -> 1869
    //   1851: lload 13
    //   1853: lstore 17
    //   1855: aload_0
    //   1856: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   1859: ldc_w 806
    //   1862: aload_0
    //   1863: getfield 724	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaLangString	Ljava/lang/String;
    //   1866: invokevirtual 809	java/net/HttpURLConnection:addRequestProperty	(Ljava/lang/String;Ljava/lang/String;)V
    //   1869: lload 13
    //   1871: lstore 17
    //   1873: aload_0
    //   1874: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   1877: invokevirtual 626	java/net/HttpURLConnection:getResponseCode	()I
    //   1880: istore 7
    //   1882: lload 13
    //   1884: lstore 17
    //   1886: new 89	java/lang/StringBuilder
    //   1889: dup
    //   1890: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   1893: astore 35
    //   1895: lload 13
    //   1897: lstore 17
    //   1899: aload 35
    //   1901: ldc_w 811
    //   1904: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1907: pop
    //   1908: lload 13
    //   1910: lstore 17
    //   1912: aload 35
    //   1914: iload 7
    //   1916: invokevirtual 333	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   1919: pop
    //   1920: lload 13
    //   1922: lstore 17
    //   1924: ldc -69
    //   1926: aload 35
    //   1928: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1931: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   1934: lload 13
    //   1936: lstore 17
    //   1938: aload_0
    //   1939: getfield 82	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b	Lcom/tencent/smtt/downloader/utils/i$b;
    //   1942: iload 7
    //   1944: invokevirtual 812	com/tencent/smtt/downloader/utils/i$b:a	(I)V
    //   1947: iload_1
    //   1948: ifne +91 -> 2039
    //   1951: lload 13
    //   1953: lstore 17
    //   1955: aload_0
    //   1956: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   1959: invokestatic 315	com/tencent/smtt/downloader/TbsDownloader:b	(Landroid/content/Context;)Z
    //   1962: ifne +77 -> 2039
    //   1965: lload 13
    //   1967: lstore 17
    //   1969: aload_0
    //   1970: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   1973: invokestatic 611	com/tencent/smtt/downloader/utils/a:a	(Landroid/content/Context;)I
    //   1976: iconst_3
    //   1977: if_icmpne +17 -> 1994
    //   1980: lload 13
    //   1982: lstore 17
    //   1984: aload_0
    //   1985: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   1988: invokestatic 611	com/tencent/smtt/downloader/utils/a:a	(Landroid/content/Context;)I
    //   1991: ifne +48 -> 2039
    //   1994: lload 13
    //   1996: lstore 17
    //   1998: aload_0
    //   1999: invokevirtual 813	com/tencent/smtt/downloader/TbsApkDownloader:a	()V
    //   2002: lload 13
    //   2004: lstore 17
    //   2006: getstatic 434	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   2009: ifnull +17 -> 2026
    //   2012: lload 13
    //   2014: lstore 17
    //   2016: getstatic 434	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   2019: bipush 111
    //   2021: invokeinterface 439 2 0
    //   2026: lload 13
    //   2028: lstore 17
    //   2030: ldc -69
    //   2032: ldc_w 815
    //   2035: iconst_0
    //   2036: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   2039: lload 13
    //   2041: lstore 17
    //   2043: aload_0
    //   2044: getfield 602	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_Boolean	Z
    //   2047: istore 9
    //   2049: iload 9
    //   2051: ifeq +3994 -> 6045
    //   2054: lload 13
    //   2056: lstore 17
    //   2058: aload_0
    //   2059: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2062: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2065: sipush 65227
    //   2068: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   2071: iload_2
    //   2072: istore 9
    //   2074: iload_1
    //   2075: ifne +1024 -> 3099
    //   2078: goto -1188 -> 890
    //   2081: iload 7
    //   2083: sipush 300
    //   2086: if_icmplt +109 -> 2195
    //   2089: iload 7
    //   2091: sipush 307
    //   2094: if_icmpgt +101 -> 2195
    //   2097: lload 13
    //   2099: lstore 17
    //   2101: aload_0
    //   2102: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   2105: ldc_w 817
    //   2108: invokevirtual 821	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   2111: astore 35
    //   2113: lload 13
    //   2115: lstore 17
    //   2117: aload 35
    //   2119: invokestatic 633	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   2122: ifne +34 -> 2156
    //   2125: lload 13
    //   2127: lstore 17
    //   2129: aload_0
    //   2130: aload 35
    //   2132: putfield 598	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_JavaLangString	Ljava/lang/String;
    //   2135: lload 13
    //   2137: lstore 17
    //   2139: aload_0
    //   2140: aload_0
    //   2141: getfield 592	com/tencent/smtt/downloader/TbsApkDownloader:g	I
    //   2144: iconst_1
    //   2145: iadd
    //   2146: putfield 592	com/tencent/smtt/downloader/TbsApkDownloader:g	I
    //   2149: iload_1
    //   2150: ifne +3947 -> 6097
    //   2153: goto +243 -> 2396
    //   2156: lload 13
    //   2158: lstore 17
    //   2160: aload_0
    //   2161: bipush 124
    //   2163: aconst_null
    //   2164: iconst_1
    //   2165: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   2168: lload 13
    //   2170: lstore 17
    //   2172: aload_0
    //   2173: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2176: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2179: sipush 65224
    //   2182: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   2185: iload_2
    //   2186: istore 9
    //   2188: iload_1
    //   2189: ifne +910 -> 3099
    //   2192: goto -1302 -> 890
    //   2195: lload 13
    //   2197: lstore 17
    //   2199: aload_0
    //   2200: bipush 102
    //   2202: iload 7
    //   2204: invokestatic 824	java/lang/String:valueOf	(I)Ljava/lang/String;
    //   2207: iconst_0
    //   2208: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   2211: iload 7
    //   2213: sipush 416
    //   2216: if_icmpne +3848 -> 6064
    //   2219: lload 13
    //   2221: lstore 17
    //   2223: aload_0
    //   2224: iconst_1
    //   2225: iload 11
    //   2227: invokespecial 826	com/tencent/smtt/downloader/TbsApkDownloader:a	(ZZ)Z
    //   2230: istore 9
    //   2232: iload 9
    //   2234: ifeq +71 -> 2305
    //   2237: lload 13
    //   2239: lstore 17
    //   2241: aload_0
    //   2242: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2245: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2248: sipush 65322
    //   2251: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   2254: iload_1
    //   2255: ifne +37 -> 2292
    //   2258: aload_0
    //   2259: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2262: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2265: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   2268: ldc_w 758
    //   2271: lload 13
    //   2273: invokestatic 771	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   2276: invokeinterface 418 3 0
    //   2281: pop
    //   2282: aload_0
    //   2283: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2286: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2289: invokevirtual 429	com/tencent/smtt/downloader/c:a	()V
    //   2292: iconst_1
    //   2293: istore 9
    //   2295: goto +3314 -> 5609
    //   2298: astore 35
    //   2300: iconst_1
    //   2301: istore_2
    //   2302: goto +3098 -> 5400
    //   2305: lload 13
    //   2307: lstore 17
    //   2309: aload_0
    //   2310: iconst_0
    //   2311: invokespecial 828	com/tencent/smtt/downloader/TbsApkDownloader:b	(Z)Z
    //   2314: pop
    //   2315: lload 13
    //   2317: lstore 17
    //   2319: aload_0
    //   2320: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2323: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2326: sipush 65223
    //   2329: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   2332: iload_2
    //   2333: istore 9
    //   2335: iload_1
    //   2336: ifne +763 -> 3099
    //   2339: goto -1449 -> 890
    //   2342: lload 13
    //   2344: lstore 17
    //   2346: aload_0
    //   2347: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   2350: ldc2_w 593
    //   2353: lcmp
    //   2354: ifne +30 -> 2384
    //   2357: lload 13
    //   2359: lstore 17
    //   2361: aload_0
    //   2362: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2365: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2368: sipush 65222
    //   2371: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   2374: iload_2
    //   2375: istore 9
    //   2377: iload_1
    //   2378: ifne +721 -> 3099
    //   2381: goto -1491 -> 890
    //   2384: iload 7
    //   2386: sipush 202
    //   2389: if_icmpne +44 -> 2433
    //   2392: iload_1
    //   2393: ifne +3704 -> 6097
    //   2396: aload_0
    //   2397: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2400: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2403: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   2406: ldc_w 758
    //   2409: lload 13
    //   2411: invokestatic 771	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   2414: invokeinterface 418 3 0
    //   2419: pop
    //   2420: aload_0
    //   2421: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2424: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2427: invokevirtual 429	com/tencent/smtt/downloader/c:a	()V
    //   2430: goto +3667 -> 6097
    //   2433: lload 13
    //   2435: lstore 17
    //   2437: aload_0
    //   2438: getfield 229	com/tencent/smtt/downloader/TbsApkDownloader:f	I
    //   2441: aload_0
    //   2442: getfield 60	com/tencent/smtt/downloader/TbsApkDownloader:i	I
    //   2445: if_icmpge +70 -> 2515
    //   2448: iload 7
    //   2450: sipush 503
    //   2453: if_icmpne +62 -> 2515
    //   2456: lload 13
    //   2458: lstore 17
    //   2460: aload_0
    //   2461: aload_0
    //   2462: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   2465: ldc_w 830
    //   2468: invokevirtual 821	java/net/HttpURLConnection:getHeaderField	(Ljava/lang/String;)Ljava/lang/String;
    //   2471: invokestatic 834	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   2474: invokespecial 835	com/tencent/smtt/downloader/TbsApkDownloader:a	(J)V
    //   2477: lload 13
    //   2479: lstore 17
    //   2481: aload_0
    //   2482: getfield 602	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_Boolean	Z
    //   2485: ifeq +3598 -> 6083
    //   2488: lload 13
    //   2490: lstore 17
    //   2492: aload_0
    //   2493: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2496: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2499: sipush 65227
    //   2502: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   2505: iload_2
    //   2506: istore 9
    //   2508: iload_1
    //   2509: ifne +590 -> 3099
    //   2512: goto -1622 -> 890
    //   2515: lload 13
    //   2517: lstore 17
    //   2519: aload_0
    //   2520: getfield 229	com/tencent/smtt/downloader/TbsApkDownloader:f	I
    //   2523: aload_0
    //   2524: getfield 60	com/tencent/smtt/downloader/TbsApkDownloader:i	I
    //   2527: if_icmpge +82 -> 2609
    //   2530: iload 7
    //   2532: sipush 408
    //   2535: if_icmpeq +27 -> 2562
    //   2538: iload 7
    //   2540: sipush 504
    //   2543: if_icmpeq +19 -> 2562
    //   2546: iload 7
    //   2548: sipush 502
    //   2551: if_icmpeq +11 -> 2562
    //   2554: iload 7
    //   2556: sipush 408
    //   2559: if_icmpne +50 -> 2609
    //   2562: lload 13
    //   2564: lstore 17
    //   2566: aload_0
    //   2567: lconst_0
    //   2568: invokespecial 835	com/tencent/smtt/downloader/TbsApkDownloader:a	(J)V
    //   2571: lload 13
    //   2573: lstore 17
    //   2575: aload_0
    //   2576: getfield 602	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_Boolean	Z
    //   2579: ifeq +3511 -> 6090
    //   2582: lload 13
    //   2584: lstore 17
    //   2586: aload_0
    //   2587: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2590: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2593: sipush 65227
    //   2596: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   2599: iload_2
    //   2600: istore 9
    //   2602: iload_1
    //   2603: ifne +496 -> 3099
    //   2606: goto -1716 -> 890
    //   2609: lload 13
    //   2611: lstore 17
    //   2613: aload_0
    //   2614: invokespecial 788	com/tencent/smtt/downloader/TbsApkDownloader:a	()J
    //   2617: lconst_0
    //   2618: lcmp
    //   2619: ifgt +38 -> 2657
    //   2622: lload 13
    //   2624: lstore 17
    //   2626: aload_0
    //   2627: getfield 600	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Boolean	Z
    //   2630: ifne +27 -> 2657
    //   2633: iload 7
    //   2635: sipush 410
    //   2638: if_icmpeq +19 -> 2657
    //   2641: lload 13
    //   2643: lstore 17
    //   2645: aload_0
    //   2646: iconst_1
    //   2647: putfield 600	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Boolean	Z
    //   2650: iload_1
    //   2651: ifne +3446 -> 6097
    //   2654: goto -258 -> 2396
    //   2657: lload 13
    //   2659: lstore 17
    //   2661: aload_0
    //   2662: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2665: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2668: sipush 65221
    //   2671: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   2674: iload_2
    //   2675: istore 9
    //   2677: iload_1
    //   2678: ifne +421 -> 3099
    //   2681: goto -1791 -> 890
    //   2684: lload 13
    //   2686: lstore 17
    //   2688: aload_0
    //   2689: aload_0
    //   2690: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   2693: invokevirtual 838	java/net/HttpURLConnection:getContentLength	()I
    //   2696: i2l
    //   2697: lload 21
    //   2699: ladd
    //   2700: putfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   2703: lload 13
    //   2705: lstore 17
    //   2707: new 89	java/lang/StringBuilder
    //   2710: dup
    //   2711: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   2714: astore 35
    //   2716: lload 13
    //   2718: lstore 17
    //   2720: aload 35
    //   2722: ldc_w 840
    //   2725: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2728: pop
    //   2729: lload 13
    //   2731: lstore 17
    //   2733: aload 35
    //   2735: aload_0
    //   2736: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   2739: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   2742: pop
    //   2743: lload 13
    //   2745: lstore 17
    //   2747: ldc -69
    //   2749: aload 35
    //   2751: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2754: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   2757: lload 13
    //   2759: lstore 17
    //   2761: aload_0
    //   2762: getfield 82	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b	Lcom/tencent/smtt/downloader/utils/i$b;
    //   2765: aload_0
    //   2766: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   2769: invokevirtual 842	com/tencent/smtt/downloader/utils/i$b:b	(J)V
    //   2772: lload 13
    //   2774: lstore 17
    //   2776: aload_0
    //   2777: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2780: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   2783: getfield 304	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   2786: ldc_w 481
    //   2789: lconst_0
    //   2790: invokeinterface 485 4 0
    //   2795: lstore 23
    //   2797: lload 23
    //   2799: lconst_0
    //   2800: lcmp
    //   2801: ifeq +301 -> 3102
    //   2804: lload 13
    //   2806: lstore 17
    //   2808: aload_0
    //   2809: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   2812: lload 23
    //   2814: lcmp
    //   2815: ifeq +287 -> 3102
    //   2818: lload 13
    //   2820: lstore 17
    //   2822: new 89	java/lang/StringBuilder
    //   2825: dup
    //   2826: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   2829: astore 35
    //   2831: lload 13
    //   2833: lstore 17
    //   2835: aload 35
    //   2837: ldc_w 844
    //   2840: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2843: pop
    //   2844: lload 13
    //   2846: lstore 17
    //   2848: aload 35
    //   2850: lload 23
    //   2852: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   2855: pop
    //   2856: lload 13
    //   2858: lstore 17
    //   2860: aload 35
    //   2862: ldc_w 846
    //   2865: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2868: pop
    //   2869: lload 13
    //   2871: lstore 17
    //   2873: aload 35
    //   2875: aload_0
    //   2876: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   2879: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   2882: pop
    //   2883: lload 13
    //   2885: lstore 17
    //   2887: ldc -69
    //   2889: aload 35
    //   2891: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   2894: iconst_1
    //   2895: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   2898: iload_1
    //   2899: ifne +159 -> 3058
    //   2902: lload 13
    //   2904: lstore 17
    //   2906: aload_0
    //   2907: invokespecial 456	com/tencent/smtt/downloader/TbsApkDownloader:c	()Z
    //   2910: ifne +17 -> 2927
    //   2913: lload 13
    //   2915: lstore 17
    //   2917: aload_0
    //   2918: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   2921: invokestatic 684	com/tencent/smtt/downloader/utils/a:a	(Landroid/content/Context;)Z
    //   2924: ifeq +134 -> 3058
    //   2927: lload 13
    //   2929: lstore 17
    //   2931: aload_0
    //   2932: getfield 62	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ArrayOfJavaLangString	[Ljava/lang/String;
    //   2935: ifnull +22 -> 2957
    //   2938: lload 13
    //   2940: lstore 17
    //   2942: aload_0
    //   2943: iconst_0
    //   2944: invokevirtual 848	com/tencent/smtt/downloader/TbsApkDownloader:a	(Z)Z
    //   2947: ifeq +10 -> 2957
    //   2950: iload_1
    //   2951: ifne +3146 -> 6097
    //   2954: goto -558 -> 2396
    //   2957: lload 13
    //   2959: lstore 17
    //   2961: new 89	java/lang/StringBuilder
    //   2964: dup
    //   2965: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   2968: astore 35
    //   2970: lload 13
    //   2972: lstore 17
    //   2974: aload 35
    //   2976: ldc_w 850
    //   2979: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   2982: pop
    //   2983: lload 13
    //   2985: lstore 17
    //   2987: aload 35
    //   2989: lload 23
    //   2991: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   2994: pop
    //   2995: lload 13
    //   2997: lstore 17
    //   2999: aload 35
    //   3001: ldc_w 846
    //   3004: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3007: pop
    //   3008: lload 13
    //   3010: lstore 17
    //   3012: aload 35
    //   3014: aload_0
    //   3015: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   3018: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   3021: pop
    //   3022: lload 13
    //   3024: lstore 17
    //   3026: aload_0
    //   3027: bipush 113
    //   3029: aload 35
    //   3031: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3034: iconst_1
    //   3035: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   3038: lload 13
    //   3040: lstore 17
    //   3042: aload_0
    //   3043: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   3046: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   3049: sipush 65226
    //   3052: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   3055: goto +34 -> 3089
    //   3058: lload 13
    //   3060: lstore 17
    //   3062: aload_0
    //   3063: bipush 101
    //   3065: ldc_w 852
    //   3068: iconst_1
    //   3069: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   3072: lload 13
    //   3074: lstore 17
    //   3076: aload_0
    //   3077: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   3080: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   3083: sipush 65232
    //   3086: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   3089: iload_2
    //   3090: istore 9
    //   3092: iload_1
    //   3093: ifne +6 -> 3099
    //   3096: goto -2206 -> 890
    //   3099: goto +2510 -> 5609
    //   3102: lload 13
    //   3104: lstore 17
    //   3106: ldc -69
    //   3108: ldc_w 854
    //   3111: invokestatic 353	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;)V
    //   3114: aload_0
    //   3115: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   3118: invokevirtual 623	java/net/HttpURLConnection:getInputStream	()Ljava/io/InputStream;
    //   3121: astore 38
    //   3123: aload 38
    //   3125: astore 35
    //   3127: aload 35
    //   3129: ifnull +1474 -> 4603
    //   3132: aload_0
    //   3133: getfield 358	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaNetHttpURLConnection	Ljava/net/HttpURLConnection;
    //   3136: invokevirtual 857	java/net/HttpURLConnection:getContentEncoding	()Ljava/lang/String;
    //   3139: astore 36
    //   3141: aload 36
    //   3143: ifnull +28 -> 3171
    //   3146: aload 36
    //   3148: ldc_w 859
    //   3151: invokevirtual 324	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   3154: ifeq +17 -> 3171
    //   3157: new 861	java/util/zip/GZIPInputStream
    //   3160: dup
    //   3161: aload 35
    //   3163: invokespecial 862	java/util/zip/GZIPInputStream:<init>	(Ljava/io/InputStream;)V
    //   3166: astore 36
    //   3168: goto +45 -> 3213
    //   3171: aload 36
    //   3173: ifnull +36 -> 3209
    //   3176: aload 36
    //   3178: ldc_w 864
    //   3181: invokevirtual 324	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   3184: ifeq +25 -> 3209
    //   3187: new 866	java/util/zip/InflaterInputStream
    //   3190: dup
    //   3191: aload 35
    //   3193: new 868	java/util/zip/Inflater
    //   3196: dup
    //   3197: iconst_1
    //   3198: invokespecial 870	java/util/zip/Inflater:<init>	(Z)V
    //   3201: invokespecial 873	java/util/zip/InflaterInputStream:<init>	(Ljava/io/InputStream;Ljava/util/zip/Inflater;)V
    //   3204: astore 36
    //   3206: goto +7 -> 3213
    //   3209: aload 35
    //   3211: astore 36
    //   3213: sipush 8192
    //   3216: newarray <illegal type>
    //   3218: astore 40
    //   3220: new 875	java/io/FileOutputStream
    //   3223: dup
    //   3224: new 130	java/io/File
    //   3227: dup
    //   3228: aload_0
    //   3229: getfield 115	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_JavaIoFile	Ljava/io/File;
    //   3232: ldc -124
    //   3234: invokespecial 135	java/io/File:<init>	(Ljava/io/File;Ljava/lang/String;)V
    //   3237: iconst_1
    //   3238: invokespecial 878	java/io/FileOutputStream:<init>	(Ljava/io/File;Z)V
    //   3241: astore 37
    //   3243: lload 13
    //   3245: lstore 19
    //   3247: aload 35
    //   3249: astore 38
    //   3251: iload_2
    //   3252: istore 9
    //   3254: lload 15
    //   3256: lstore 23
    //   3258: lload 13
    //   3260: lstore 17
    //   3262: aload 35
    //   3264: astore 39
    //   3266: iload_2
    //   3267: istore 10
    //   3269: lload 15
    //   3271: lstore 25
    //   3273: invokestatic 148	java/lang/System:currentTimeMillis	()J
    //   3276: lstore 27
    //   3278: lload 21
    //   3280: lstore 29
    //   3282: lload 13
    //   3284: lstore 19
    //   3286: aload 35
    //   3288: astore 38
    //   3290: iload_2
    //   3291: istore 9
    //   3293: lload 15
    //   3295: lstore 23
    //   3297: lload 13
    //   3299: lstore 17
    //   3301: aload 35
    //   3303: astore 39
    //   3305: iload_2
    //   3306: istore 10
    //   3308: lload 15
    //   3310: lstore 25
    //   3312: aload_0
    //   3313: getfield 602	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_Boolean	Z
    //   3316: istore 12
    //   3318: iload 12
    //   3320: ifeq +59 -> 3379
    //   3323: iload_2
    //   3324: istore 9
    //   3326: iload_2
    //   3327: istore 10
    //   3329: ldc -69
    //   3331: ldc_w 880
    //   3334: iconst_1
    //   3335: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   3338: iload_2
    //   3339: istore 9
    //   3341: iload_2
    //   3342: istore 10
    //   3344: aload_0
    //   3345: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   3348: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   3351: sipush 65227
    //   3354: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   3357: iconst_0
    //   3358: istore 7
    //   3360: goto +957 -> 4317
    //   3363: astore 38
    //   3365: iload 9
    //   3367: istore_2
    //   3368: goto +2751 -> 6119
    //   3371: astore 38
    //   3373: iload 10
    //   3375: istore_2
    //   3376: goto +2770 -> 6146
    //   3379: aload 36
    //   3381: aload 40
    //   3383: iconst_0
    //   3384: sipush 8192
    //   3387: invokevirtual 886	java/io/InputStream:read	([BII)I
    //   3390: istore 7
    //   3392: iload 7
    //   3394: ifgt +123 -> 3517
    //   3397: aload_0
    //   3398: getfield 62	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ArrayOfJavaLangString	[Ljava/lang/String;
    //   3401: ifnull +2751 -> 6152
    //   3404: aload_0
    //   3405: iconst_1
    //   3406: iload 11
    //   3408: invokespecial 826	com/tencent/smtt/downloader/TbsApkDownloader:a	(ZZ)Z
    //   3411: ifne +2738 -> 6149
    //   3414: iload_1
    //   3415: ifne +17 -> 3432
    //   3418: aload_0
    //   3419: iconst_0
    //   3420: invokevirtual 848	com/tencent/smtt/downloader/TbsApkDownloader:a	(Z)Z
    //   3423: ifeq +9 -> 3432
    //   3426: iconst_1
    //   3427: istore 7
    //   3429: goto +888 -> 4317
    //   3432: aload_0
    //   3433: iconst_1
    //   3434: putfield 604	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_Boolean	Z
    //   3437: iconst_0
    //   3438: istore 7
    //   3440: iconst_0
    //   3441: istore_2
    //   3442: goto +875 -> 4317
    //   3445: aload_0
    //   3446: iconst_1
    //   3447: putfield 604	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_Boolean	Z
    //   3450: aload_0
    //   3451: getfield 62	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ArrayOfJavaLangString	[Ljava/lang/String;
    //   3454: astore 38
    //   3456: aload 38
    //   3458: ifnull +8 -> 3466
    //   3461: iconst_1
    //   3462: istore_2
    //   3463: goto +3 -> 3466
    //   3466: iload_2
    //   3467: istore 9
    //   3469: iload_2
    //   3470: istore 10
    //   3472: aload_0
    //   3473: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   3476: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   3479: sipush 65225
    //   3482: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   3485: iconst_0
    //   3486: istore 7
    //   3488: goto +829 -> 4317
    //   3491: astore 38
    //   3493: aload 35
    //   3495: astore 39
    //   3497: aload 38
    //   3499: astore 35
    //   3501: aload 36
    //   3503: astore 38
    //   3505: aload 39
    //   3507: astore 36
    //   3509: goto +1778 -> 5287
    //   3512: astore 38
    //   3514: goto +1280 -> 4794
    //   3517: aload 37
    //   3519: aload 40
    //   3521: iconst_0
    //   3522: iload 7
    //   3524: invokevirtual 890	java/io/FileOutputStream:write	([BII)V
    //   3527: aload 37
    //   3529: invokevirtual 893	java/io/FileOutputStream:flush	()V
    //   3532: iload_1
    //   3533: ifne +331 -> 3864
    //   3536: lload 13
    //   3538: iload 7
    //   3540: i2l
    //   3541: ladd
    //   3542: lstore 17
    //   3544: lload 17
    //   3546: lload 15
    //   3548: lcmp
    //   3549: iflt +111 -> 3660
    //   3552: ldc -69
    //   3554: ldc_w 775
    //   3557: iconst_1
    //   3558: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   3561: new 89	java/lang/StringBuilder
    //   3564: dup
    //   3565: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   3568: astore 38
    //   3570: aload 38
    //   3572: ldc_w 895
    //   3575: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3578: pop
    //   3579: aload 38
    //   3581: lload 17
    //   3583: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   3586: pop
    //   3587: aload 38
    //   3589: ldc_w 897
    //   3592: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3595: pop
    //   3596: aload 38
    //   3598: lload 15
    //   3600: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   3603: pop
    //   3604: aload_0
    //   3605: bipush 112
    //   3607: aload 38
    //   3609: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3612: iconst_1
    //   3613: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   3616: aload_0
    //   3617: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   3620: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   3623: sipush 65229
    //   3626: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   3629: goto +207 -> 3836
    //   3632: astore 38
    //   3634: goto +10 -> 3644
    //   3637: astore 38
    //   3639: goto +14 -> 3653
    //   3642: astore 38
    //   3644: lload 17
    //   3646: lstore 13
    //   3648: goto -155 -> 3493
    //   3651: astore 38
    //   3653: lload 17
    //   3655: lstore 13
    //   3657: goto -143 -> 3514
    //   3660: lload 17
    //   3662: lstore 19
    //   3664: lload 19
    //   3666: lstore 23
    //   3668: lload 19
    //   3670: lstore 25
    //   3672: lload 19
    //   3674: lstore 13
    //   3676: aload_0
    //   3677: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   3680: invokestatic 776	com/tencent/smtt/downloader/utils/FileUtil:a	(Landroid/content/Context;)Z
    //   3683: ifne +181 -> 3864
    //   3686: lload 19
    //   3688: lstore 23
    //   3690: lload 19
    //   3692: lstore 25
    //   3694: ldc -69
    //   3696: ldc_w 899
    //   3699: iconst_1
    //   3700: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   3703: lload 19
    //   3705: lstore 23
    //   3707: lload 19
    //   3709: lstore 25
    //   3711: new 89	java/lang/StringBuilder
    //   3714: dup
    //   3715: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   3718: astore 38
    //   3720: lload 19
    //   3722: lstore 23
    //   3724: lload 19
    //   3726: lstore 25
    //   3728: aload 38
    //   3730: ldc_w 901
    //   3733: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3736: pop
    //   3737: lload 19
    //   3739: lstore 23
    //   3741: lload 19
    //   3743: lstore 25
    //   3745: aload 38
    //   3747: invokestatic 902	com/tencent/smtt/downloader/utils/j:a	()J
    //   3750: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   3753: pop
    //   3754: lload 19
    //   3756: lstore 23
    //   3758: lload 19
    //   3760: lstore 25
    //   3762: aload 38
    //   3764: ldc_w 904
    //   3767: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3770: pop
    //   3771: lload 19
    //   3773: lstore 23
    //   3775: lload 19
    //   3777: lstore 25
    //   3779: aload 38
    //   3781: aload_0
    //   3782: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   3785: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   3788: invokevirtual 906	com/tencent/smtt/downloader/c:c	()J
    //   3791: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   3794: pop
    //   3795: lload 19
    //   3797: lstore 23
    //   3799: lload 19
    //   3801: lstore 25
    //   3803: aload_0
    //   3804: bipush 105
    //   3806: aload 38
    //   3808: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   3811: iconst_1
    //   3812: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   3815: lload 19
    //   3817: lstore 23
    //   3819: lload 19
    //   3821: lstore 25
    //   3823: aload_0
    //   3824: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   3827: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   3830: sipush 65228
    //   3833: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   3836: lload 17
    //   3838: lstore 13
    //   3840: iconst_0
    //   3841: istore 7
    //   3843: goto +474 -> 4317
    //   3846: astore 38
    //   3848: lload 23
    //   3850: lstore 13
    //   3852: goto -359 -> 3493
    //   3855: astore 38
    //   3857: lload 25
    //   3859: lstore 13
    //   3861: goto -347 -> 3514
    //   3864: aload 35
    //   3866: astore 39
    //   3868: lload 15
    //   3870: lstore 17
    //   3872: iload 7
    //   3874: i2l
    //   3875: lstore 19
    //   3877: aload_0
    //   3878: lload 31
    //   3880: lload 19
    //   3882: invokespecial 908	com/tencent/smtt/downloader/TbsApkDownloader:a	(JJ)J
    //   3885: lstore 31
    //   3887: invokestatic 148	java/lang/System:currentTimeMillis	()J
    //   3890: lstore 33
    //   3892: lload 29
    //   3894: lload 19
    //   3896: ladd
    //   3897: lstore 19
    //   3899: lload 33
    //   3901: lload 27
    //   3903: lsub
    //   3904: ldc2_w 909
    //   3907: lcmp
    //   3908: ifle +532 -> 4440
    //   3911: lload 13
    //   3913: lstore 23
    //   3915: lload 13
    //   3917: lstore 25
    //   3919: new 89	java/lang/StringBuilder
    //   3922: dup
    //   3923: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   3926: astore 38
    //   3928: lload 13
    //   3930: lstore 23
    //   3932: lload 13
    //   3934: lstore 25
    //   3936: aload 38
    //   3938: ldc_w 912
    //   3941: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3944: pop
    //   3945: lload 13
    //   3947: lstore 23
    //   3949: lload 13
    //   3951: lstore 25
    //   3953: aload 38
    //   3955: lload 19
    //   3957: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   3960: pop
    //   3961: lload 13
    //   3963: lstore 23
    //   3965: lload 13
    //   3967: lstore 25
    //   3969: aload 38
    //   3971: ldc_w 802
    //   3974: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   3977: pop
    //   3978: lload 13
    //   3980: lstore 23
    //   3982: lload 13
    //   3984: lstore 25
    //   3986: aload 38
    //   3988: aload_0
    //   3989: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   3992: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   3995: pop
    //   3996: lload 13
    //   3998: lstore 23
    //   4000: lload 13
    //   4002: lstore 25
    //   4004: ldc -69
    //   4006: aload 38
    //   4008: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   4011: iconst_1
    //   4012: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   4015: lload 13
    //   4017: lstore 23
    //   4019: lload 13
    //   4021: lstore 25
    //   4023: getstatic 434	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   4026: ifnull +135 -> 4161
    //   4029: lload 19
    //   4031: l2d
    //   4032: dstore_3
    //   4033: lload 13
    //   4035: lstore 23
    //   4037: lload 13
    //   4039: lstore 25
    //   4041: aload_0
    //   4042: getfield 596	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_Long	J
    //   4045: lstore 27
    //   4047: lload 27
    //   4049: l2d
    //   4050: dstore 5
    //   4052: dload_3
    //   4053: invokestatic 918	java/lang/Double:isNaN	(D)Z
    //   4056: pop
    //   4057: dload 5
    //   4059: invokestatic 918	java/lang/Double:isNaN	(D)Z
    //   4062: pop
    //   4063: dload_3
    //   4064: dload 5
    //   4066: ddiv
    //   4067: ldc2_w 919
    //   4070: dmul
    //   4071: d2i
    //   4072: istore 7
    //   4074: lload 13
    //   4076: lstore 23
    //   4078: lload 13
    //   4080: lstore 25
    //   4082: new 89	java/lang/StringBuilder
    //   4085: dup
    //   4086: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   4089: astore 38
    //   4091: lload 13
    //   4093: lstore 23
    //   4095: lload 13
    //   4097: lstore 25
    //   4099: aload 38
    //   4101: ldc_w 922
    //   4104: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4107: pop
    //   4108: lload 13
    //   4110: lstore 23
    //   4112: lload 13
    //   4114: lstore 25
    //   4116: aload 38
    //   4118: iload 7
    //   4120: invokevirtual 333	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   4123: pop
    //   4124: lload 13
    //   4126: lstore 23
    //   4128: lload 13
    //   4130: lstore 25
    //   4132: ldc_w 351
    //   4135: aload 38
    //   4137: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   4140: invokestatic 924	com/tencent/smtt/downloader/utils/h:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   4143: lload 13
    //   4145: lstore 23
    //   4147: lload 13
    //   4149: lstore 25
    //   4151: getstatic 434	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   4154: iload 7
    //   4156: invokeinterface 927 2 0
    //   4161: iload_1
    //   4162: ifne +271 -> 4433
    //   4165: lload 19
    //   4167: lload 21
    //   4169: lsub
    //   4170: ldc2_w 928
    //   4173: lcmp
    //   4174: ifle +256 -> 4430
    //   4177: lload 13
    //   4179: lstore 23
    //   4181: lload 13
    //   4183: lstore 25
    //   4185: aload_0
    //   4186: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4189: invokestatic 315	com/tencent/smtt/downloader/TbsDownloader:b	(Landroid/content/Context;)Z
    //   4192: ifne +231 -> 4423
    //   4195: lload 13
    //   4197: lstore 23
    //   4199: lload 13
    //   4201: lstore 25
    //   4203: aload_0
    //   4204: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4207: invokestatic 611	com/tencent/smtt/downloader/utils/a:a	(Landroid/content/Context;)I
    //   4210: iconst_3
    //   4211: if_icmpne +21 -> 4232
    //   4214: lload 13
    //   4216: lstore 23
    //   4218: lload 13
    //   4220: lstore 25
    //   4222: aload_0
    //   4223: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4226: invokestatic 611	com/tencent/smtt/downloader/utils/a:a	(Landroid/content/Context;)I
    //   4229: ifne +194 -> 4423
    //   4232: lload 13
    //   4234: lstore 23
    //   4236: lload 13
    //   4238: lstore 25
    //   4240: aload_0
    //   4241: invokevirtual 813	com/tencent/smtt/downloader/TbsApkDownloader:a	()V
    //   4244: lload 13
    //   4246: lstore 23
    //   4248: lload 13
    //   4250: lstore 25
    //   4252: getstatic 434	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   4255: ifnull +21 -> 4276
    //   4258: lload 13
    //   4260: lstore 23
    //   4262: lload 13
    //   4264: lstore 25
    //   4266: getstatic 434	com/tencent/smtt/downloader/a:a	Lcom/tencent/smtt/downloader/TbsListener;
    //   4269: bipush 111
    //   4271: invokeinterface 439 2 0
    //   4276: lload 13
    //   4278: lstore 23
    //   4280: lload 13
    //   4282: lstore 25
    //   4284: ldc -69
    //   4286: ldc_w 931
    //   4289: iconst_0
    //   4290: invokestatic 731	com/tencent/smtt/downloader/utils/h:a	(Ljava/lang/String;Ljava/lang/String;Z)V
    //   4293: lload 13
    //   4295: lstore 23
    //   4297: lload 13
    //   4299: lstore 25
    //   4301: aload_0
    //   4302: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4305: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   4308: sipush 65232
    //   4311: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   4314: iconst_0
    //   4315: istore 7
    //   4317: iload 7
    //   4319: ifeq +101 -> 4420
    //   4322: lload 13
    //   4324: lstore 17
    //   4326: aload_0
    //   4327: aload 37
    //   4329: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   4332: lload 13
    //   4334: lstore 17
    //   4336: aload_0
    //   4337: aload 36
    //   4339: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   4342: lload 13
    //   4344: lstore 17
    //   4346: aload_0
    //   4347: aload 35
    //   4349: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   4352: lload 13
    //   4354: lstore 17
    //   4356: lload 15
    //   4358: lstore 19
    //   4360: iload_2
    //   4361: istore 9
    //   4363: iload_1
    //   4364: ifne +891 -> 5255
    //   4367: aload_0
    //   4368: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4371: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   4374: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   4377: ldc_w 758
    //   4380: lload 13
    //   4382: invokestatic 771	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   4385: invokeinterface 418 3 0
    //   4390: pop
    //   4391: aload_0
    //   4392: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4395: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   4398: invokevirtual 429	com/tencent/smtt/downloader/c:a	()V
    //   4401: lload 13
    //   4403: lstore 17
    //   4405: lload 15
    //   4407: lstore 19
    //   4409: iload_2
    //   4410: istore 9
    //   4412: goto +843 -> 5255
    //   4415: astore 35
    //   4417: goto -3387 -> 1030
    //   4420: goto +189 -> 4609
    //   4423: lload 19
    //   4425: lstore 21
    //   4427: goto +6 -> 4433
    //   4430: goto +3 -> 4433
    //   4433: lload 33
    //   4435: lstore 27
    //   4437: goto +3 -> 4440
    //   4440: lload 17
    //   4442: lstore 15
    //   4444: aload 39
    //   4446: astore 35
    //   4448: lload 19
    //   4450: lstore 29
    //   4452: goto -1170 -> 3282
    //   4455: astore 35
    //   4457: aload 36
    //   4459: astore 38
    //   4461: lload 17
    //   4463: lstore 15
    //   4465: aload 39
    //   4467: astore 36
    //   4469: goto +818 -> 5287
    //   4472: astore 38
    //   4474: lload 17
    //   4476: lstore 15
    //   4478: aload 39
    //   4480: astore 35
    //   4482: goto +312 -> 4794
    //   4485: astore 39
    //   4487: aload 35
    //   4489: astore 38
    //   4491: aload 39
    //   4493: astore 35
    //   4495: aload 36
    //   4497: astore 39
    //   4499: goto +780 -> 5279
    //   4502: astore 38
    //   4504: goto +40 -> 4544
    //   4507: astore 35
    //   4509: iload 9
    //   4511: istore_2
    //   4512: lload 23
    //   4514: lstore 15
    //   4516: aload 36
    //   4518: astore 39
    //   4520: lload 19
    //   4522: lstore 13
    //   4524: goto +755 -> 5279
    //   4527: astore 38
    //   4529: iload 10
    //   4531: istore_2
    //   4532: aload 39
    //   4534: astore 35
    //   4536: lload 25
    //   4538: lstore 15
    //   4540: lload 17
    //   4542: lstore 13
    //   4544: goto +250 -> 4794
    //   4547: astore 35
    //   4549: goto +13 -> 4562
    //   4552: astore 35
    //   4554: goto +31 -> 4585
    //   4557: astore 35
    //   4559: aconst_null
    //   4560: astore 36
    //   4562: aload 38
    //   4564: astore 39
    //   4566: aconst_null
    //   4567: astore 37
    //   4569: aload 36
    //   4571: astore 38
    //   4573: aload 39
    //   4575: astore 36
    //   4577: goto +710 -> 5287
    //   4580: astore 35
    //   4582: aconst_null
    //   4583: astore 36
    //   4585: aload 38
    //   4587: astore 39
    //   4589: aconst_null
    //   4590: astore 37
    //   4592: aload 35
    //   4594: astore 38
    //   4596: aload 39
    //   4598: astore 35
    //   4600: goto +194 -> 4794
    //   4603: aconst_null
    //   4604: astore 36
    //   4606: aconst_null
    //   4607: astore 37
    //   4609: lload 13
    //   4611: lstore 21
    //   4613: lload 15
    //   4615: lstore 19
    //   4617: iload_2
    //   4618: istore 9
    //   4620: lload 13
    //   4622: lstore 17
    //   4624: aload_0
    //   4625: aload 37
    //   4627: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   4630: lload 13
    //   4632: lstore 21
    //   4634: lload 15
    //   4636: lstore 19
    //   4638: iload_2
    //   4639: istore 9
    //   4641: lload 13
    //   4643: lstore 17
    //   4645: aload_0
    //   4646: aload 36
    //   4648: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   4651: lload 13
    //   4653: lstore 21
    //   4655: lload 15
    //   4657: lstore 19
    //   4659: iload_2
    //   4660: istore 9
    //   4662: lload 13
    //   4664: lstore 17
    //   4666: aload_0
    //   4667: aload 35
    //   4669: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   4672: lload 13
    //   4674: lstore 21
    //   4676: lload 15
    //   4678: lstore 19
    //   4680: iload_2
    //   4681: istore 9
    //   4683: lload 13
    //   4685: lstore 17
    //   4687: aload_0
    //   4688: getfield 604	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_Boolean	Z
    //   4691: ifne +31 -> 4722
    //   4694: lload 13
    //   4696: lstore 21
    //   4698: lload 15
    //   4700: lstore 19
    //   4702: iload_2
    //   4703: istore 9
    //   4705: lload 13
    //   4707: lstore 17
    //   4709: aload_0
    //   4710: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4713: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   4716: sipush 65217
    //   4719: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   4722: iload_2
    //   4723: istore 9
    //   4725: iload_1
    //   4726: ifne +883 -> 5609
    //   4729: aload_0
    //   4730: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4733: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   4736: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   4739: ldc_w 758
    //   4742: lload 13
    //   4744: invokestatic 771	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   4747: invokeinterface 418 3 0
    //   4752: pop
    //   4753: aload_0
    //   4754: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4757: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   4760: invokevirtual 429	com/tencent/smtt/downloader/c:a	()V
    //   4763: iload_2
    //   4764: istore 9
    //   4766: goto +843 -> 5609
    //   4769: astore 35
    //   4771: aconst_null
    //   4772: astore 38
    //   4774: aconst_null
    //   4775: astore 37
    //   4777: aconst_null
    //   4778: astore 36
    //   4780: goto +507 -> 5287
    //   4783: astore 38
    //   4785: aconst_null
    //   4786: astore 36
    //   4788: aconst_null
    //   4789: astore 37
    //   4791: aconst_null
    //   4792: astore 35
    //   4794: aload 38
    //   4796: invokevirtual 932	java/io/IOException:printStackTrace	()V
    //   4799: aload 38
    //   4801: instanceof 934
    //   4804: ifne +303 -> 5107
    //   4807: aload 38
    //   4809: instanceof 936
    //   4812: ifeq +6 -> 4818
    //   4815: goto +292 -> 5107
    //   4818: iload_1
    //   4819: ifne +166 -> 4985
    //   4822: aload_0
    //   4823: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4826: invokestatic 776	com/tencent/smtt/downloader/utils/FileUtil:a	(Landroid/content/Context;)Z
    //   4829: ifne +153 -> 4982
    //   4832: new 89	java/lang/StringBuilder
    //   4835: dup
    //   4836: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   4839: astore 38
    //   4841: aload 38
    //   4843: ldc_w 901
    //   4846: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4849: pop
    //   4850: aload 38
    //   4852: invokestatic 902	com/tencent/smtt/downloader/utils/j:a	()J
    //   4855: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   4858: pop
    //   4859: aload 38
    //   4861: ldc_w 904
    //   4864: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   4867: pop
    //   4868: aload 38
    //   4870: aload_0
    //   4871: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4874: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   4877: invokevirtual 906	com/tencent/smtt/downloader/c:c	()J
    //   4880: invokevirtual 492	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   4883: pop
    //   4884: aload_0
    //   4885: bipush 105
    //   4887: aload 38
    //   4889: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   4892: iconst_1
    //   4893: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   4896: aload_0
    //   4897: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   4900: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   4903: sipush 65228
    //   4906: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   4909: lload 13
    //   4911: lstore 21
    //   4913: lload 15
    //   4915: lstore 19
    //   4917: iload_2
    //   4918: istore 9
    //   4920: lload 13
    //   4922: lstore 17
    //   4924: aload_0
    //   4925: aload 37
    //   4927: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   4930: lload 13
    //   4932: lstore 21
    //   4934: lload 15
    //   4936: lstore 19
    //   4938: iload_2
    //   4939: istore 9
    //   4941: lload 13
    //   4943: lstore 17
    //   4945: aload_0
    //   4946: aload 36
    //   4948: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   4951: lload 13
    //   4953: lstore 21
    //   4955: lload 15
    //   4957: lstore 19
    //   4959: iload_2
    //   4960: istore 9
    //   4962: lload 13
    //   4964: lstore 17
    //   4966: aload_0
    //   4967: aload 35
    //   4969: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   4972: iload_2
    //   4973: istore 9
    //   4975: iload_1
    //   4976: ifne +633 -> 5609
    //   4979: goto -250 -> 4729
    //   4982: goto +3 -> 4985
    //   4985: aload_0
    //   4986: lconst_0
    //   4987: invokespecial 835	com/tencent/smtt/downloader/TbsApkDownloader:a	(J)V
    //   4990: aload_0
    //   4991: invokespecial 938	com/tencent/smtt/downloader/TbsApkDownloader:a	()Z
    //   4994: ifne +19 -> 5013
    //   4997: aload_0
    //   4998: bipush 106
    //   5000: aload_0
    //   5001: aload 38
    //   5003: invokespecial 530	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   5006: iconst_0
    //   5007: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   5010: goto +16 -> 5026
    //   5013: aload_0
    //   5014: bipush 104
    //   5016: aload_0
    //   5017: aload 38
    //   5019: invokespecial 530	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   5022: iconst_0
    //   5023: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   5026: lload 13
    //   5028: lstore 21
    //   5030: lload 15
    //   5032: lstore 19
    //   5034: iload_2
    //   5035: istore 9
    //   5037: lload 13
    //   5039: lstore 17
    //   5041: aload_0
    //   5042: aload 37
    //   5044: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   5047: lload 13
    //   5049: lstore 21
    //   5051: lload 15
    //   5053: lstore 19
    //   5055: iload_2
    //   5056: istore 9
    //   5058: lload 13
    //   5060: lstore 17
    //   5062: aload_0
    //   5063: aload 36
    //   5065: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   5068: lload 13
    //   5070: lstore 21
    //   5072: lload 15
    //   5074: lstore 19
    //   5076: iload_2
    //   5077: istore 9
    //   5079: lload 13
    //   5081: lstore 17
    //   5083: aload_0
    //   5084: aload 35
    //   5086: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   5089: lload 13
    //   5091: lstore 17
    //   5093: lload 15
    //   5095: lstore 19
    //   5097: iload_2
    //   5098: istore 9
    //   5100: iload_1
    //   5101: ifne +154 -> 5255
    //   5104: goto +106 -> 5210
    //   5107: aload_0
    //   5108: ldc_w 939
    //   5111: putfield 54	com/tencent/smtt/downloader/TbsApkDownloader:jdField_d_of_type_Int	I
    //   5114: aload_0
    //   5115: lconst_0
    //   5116: invokespecial 835	com/tencent/smtt/downloader/TbsApkDownloader:a	(J)V
    //   5119: aload_0
    //   5120: bipush 103
    //   5122: aload_0
    //   5123: aload 38
    //   5125: invokespecial 530	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   5128: iconst_0
    //   5129: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   5132: lload 13
    //   5134: lstore 21
    //   5136: lload 15
    //   5138: lstore 19
    //   5140: iload_2
    //   5141: istore 9
    //   5143: lload 13
    //   5145: lstore 17
    //   5147: aload_0
    //   5148: aload 37
    //   5150: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   5153: lload 13
    //   5155: lstore 21
    //   5157: lload 15
    //   5159: lstore 19
    //   5161: iload_2
    //   5162: istore 9
    //   5164: lload 13
    //   5166: lstore 17
    //   5168: aload_0
    //   5169: aload 36
    //   5171: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   5174: lload 13
    //   5176: lstore 21
    //   5178: lload 15
    //   5180: lstore 19
    //   5182: iload_2
    //   5183: istore 9
    //   5185: lload 13
    //   5187: lstore 17
    //   5189: aload_0
    //   5190: aload 35
    //   5192: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   5195: lload 13
    //   5197: lstore 17
    //   5199: lload 15
    //   5201: lstore 19
    //   5203: iload_2
    //   5204: istore 9
    //   5206: iload_1
    //   5207: ifne +48 -> 5255
    //   5210: aload_0
    //   5211: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   5214: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   5217: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   5220: ldc_w 758
    //   5223: lload 13
    //   5225: invokestatic 771	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   5228: invokeinterface 418 3 0
    //   5233: pop
    //   5234: aload_0
    //   5235: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   5238: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   5241: invokevirtual 429	com/tencent/smtt/downloader/c:a	()V
    //   5244: iload_2
    //   5245: istore 9
    //   5247: lload 15
    //   5249: lstore 19
    //   5251: lload 13
    //   5253: lstore 17
    //   5255: lload 17
    //   5257: lstore 13
    //   5259: iload 9
    //   5261: istore_2
    //   5262: goto +839 -> 6101
    //   5265: astore 40
    //   5267: aload 35
    //   5269: astore 38
    //   5271: aload 36
    //   5273: astore 39
    //   5275: aload 40
    //   5277: astore 35
    //   5279: aload 38
    //   5281: astore 36
    //   5283: aload 39
    //   5285: astore 38
    //   5287: lload 13
    //   5289: lstore 21
    //   5291: lload 15
    //   5293: lstore 19
    //   5295: iload_2
    //   5296: istore 9
    //   5298: lload 13
    //   5300: lstore 17
    //   5302: aload_0
    //   5303: aload 37
    //   5305: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   5308: lload 13
    //   5310: lstore 21
    //   5312: lload 15
    //   5314: lstore 19
    //   5316: iload_2
    //   5317: istore 9
    //   5319: lload 13
    //   5321: lstore 17
    //   5323: aload_0
    //   5324: aload 38
    //   5326: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   5329: lload 13
    //   5331: lstore 21
    //   5333: lload 15
    //   5335: lstore 19
    //   5337: iload_2
    //   5338: istore 9
    //   5340: lload 13
    //   5342: lstore 17
    //   5344: aload_0
    //   5345: aload 36
    //   5347: invokespecial 583	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/io/Closeable;)V
    //   5350: lload 13
    //   5352: lstore 21
    //   5354: lload 15
    //   5356: lstore 19
    //   5358: iload_2
    //   5359: istore 9
    //   5361: lload 13
    //   5363: lstore 17
    //   5365: aload 35
    //   5367: athrow
    //   5368: astore 35
    //   5370: iload 9
    //   5372: istore_2
    //   5373: lload 21
    //   5375: lstore 13
    //   5377: lload 19
    //   5379: lstore 15
    //   5381: goto +19 -> 5400
    //   5384: astore 35
    //   5386: goto +14 -> 5400
    //   5389: astore 35
    //   5391: goto +607 -> 5998
    //   5394: astore 35
    //   5396: lload 19
    //   5398: lstore 15
    //   5400: lload 13
    //   5402: lstore 17
    //   5404: aload 35
    //   5406: instanceof 941
    //   5409: ifeq +127 -> 5536
    //   5412: iload_1
    //   5413: ifne +123 -> 5536
    //   5416: lload 13
    //   5418: lstore 17
    //   5420: aload_0
    //   5421: getfield 62	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ArrayOfJavaLangString	[Ljava/lang/String;
    //   5424: ifnull +112 -> 5536
    //   5427: lload 13
    //   5429: lstore 17
    //   5431: aload_0
    //   5432: iconst_0
    //   5433: invokevirtual 848	com/tencent/smtt/downloader/TbsApkDownloader:a	(Z)Z
    //   5436: ifeq +100 -> 5536
    //   5439: lload 13
    //   5441: lstore 17
    //   5443: new 89	java/lang/StringBuilder
    //   5446: dup
    //   5447: invokespecial 90	java/lang/StringBuilder:<init>	()V
    //   5450: astore 36
    //   5452: lload 13
    //   5454: lstore 17
    //   5456: aload 36
    //   5458: ldc_w 943
    //   5461: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5464: pop
    //   5465: lload 13
    //   5467: lstore 17
    //   5469: aload 36
    //   5471: aload_0
    //   5472: getfield 598	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_JavaLangString	Ljava/lang/String;
    //   5475: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5478: pop
    //   5479: lload 13
    //   5481: lstore 17
    //   5483: aload 36
    //   5485: ldc_w 945
    //   5488: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5491: pop
    //   5492: lload 13
    //   5494: lstore 17
    //   5496: aload 36
    //   5498: aload 35
    //   5500: invokevirtual 366	java/lang/Throwable:toString	()Ljava/lang/String;
    //   5503: invokevirtual 96	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   5506: pop
    //   5507: lload 13
    //   5509: lstore 17
    //   5511: ldc -69
    //   5513: aload 36
    //   5515: invokevirtual 103	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   5518: invokestatic 192	com/tencent/smtt/downloader/utils/h:b	(Ljava/lang/String;Ljava/lang/String;)V
    //   5521: lload 13
    //   5523: lstore 17
    //   5525: aload_0
    //   5526: bipush 125
    //   5528: aconst_null
    //   5529: iconst_1
    //   5530: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   5533: goto +396 -> 5929
    //   5536: lload 13
    //   5538: lstore 17
    //   5540: aload 35
    //   5542: invokevirtual 584	java/lang/Throwable:printStackTrace	()V
    //   5545: lload 13
    //   5547: lstore 17
    //   5549: aload_0
    //   5550: lconst_0
    //   5551: invokespecial 835	com/tencent/smtt/downloader/TbsApkDownloader:a	(J)V
    //   5554: lload 13
    //   5556: lstore 17
    //   5558: aload_0
    //   5559: bipush 107
    //   5561: aload_0
    //   5562: aload 35
    //   5564: invokespecial 530	com/tencent/smtt/downloader/TbsApkDownloader:a	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   5567: iconst_0
    //   5568: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   5571: lload 13
    //   5573: lstore 17
    //   5575: aload_0
    //   5576: getfield 602	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_Boolean	Z
    //   5579: ifeq +350 -> 5929
    //   5582: lload 13
    //   5584: lstore 17
    //   5586: aload_0
    //   5587: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   5590: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   5593: sipush 65227
    //   5596: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   5599: iload_2
    //   5600: istore 9
    //   5602: iload_1
    //   5603: ifne -2504 -> 3099
    //   5606: goto -4716 -> 890
    //   5609: aload_0
    //   5610: getfield 602	com/tencent/smtt/downloader/TbsApkDownloader:jdField_b_of_type_Boolean	Z
    //   5613: ifne +311 -> 5924
    //   5616: aload_0
    //   5617: getfield 604	com/tencent/smtt/downloader/TbsApkDownloader:jdField_c_of_type_Boolean	Z
    //   5620: ifeq +154 -> 5774
    //   5623: iload 9
    //   5625: istore_1
    //   5626: aload_0
    //   5627: getfield 62	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ArrayOfJavaLangString	[Ljava/lang/String;
    //   5630: ifnonnull +19 -> 5649
    //   5633: iload 9
    //   5635: istore_1
    //   5636: iload 9
    //   5638: ifne +11 -> 5649
    //   5641: aload_0
    //   5642: iconst_1
    //   5643: iload 11
    //   5645: invokespecial 826	com/tencent/smtt/downloader/TbsApkDownloader:a	(ZZ)Z
    //   5648: istore_1
    //   5649: aload_0
    //   5650: getfield 82	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b	Lcom/tencent/smtt/downloader/utils/i$b;
    //   5653: astore 35
    //   5655: iload_1
    //   5656: ifeq +9 -> 5665
    //   5659: iconst_1
    //   5660: istore 7
    //   5662: goto +6 -> 5668
    //   5665: iconst_0
    //   5666: istore 7
    //   5668: aload 35
    //   5670: iload 7
    //   5672: invokevirtual 947	com/tencent/smtt/downloader/utils/i$b:d	(I)V
    //   5675: iload 11
    //   5677: ifne +32 -> 5709
    //   5680: aload_0
    //   5681: getfield 82	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b	Lcom/tencent/smtt/downloader/utils/i$b;
    //   5684: astore 35
    //   5686: iload_1
    //   5687: ifeq +9 -> 5696
    //   5690: iconst_1
    //   5691: istore 7
    //   5693: goto +6 -> 5699
    //   5696: iconst_2
    //   5697: istore 7
    //   5699: aload 35
    //   5701: iload 7
    //   5703: invokevirtual 949	com/tencent/smtt/downloader/utils/i$b:b	(I)V
    //   5706: goto +11 -> 5717
    //   5709: aload_0
    //   5710: getfield 82	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b	Lcom/tencent/smtt/downloader/utils/i$b;
    //   5713: iconst_0
    //   5714: invokevirtual 949	com/tencent/smtt/downloader/utils/i$b:b	(I)V
    //   5717: iload_1
    //   5718: ifeq +34 -> 5752
    //   5721: aload_0
    //   5722: iconst_1
    //   5723: invokespecial 951	com/tencent/smtt/downloader/TbsApkDownloader:a	(Z)V
    //   5726: aload_0
    //   5727: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   5730: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   5733: sipush 65219
    //   5736: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   5739: aload_0
    //   5740: bipush 100
    //   5742: ldc_w 953
    //   5745: iconst_1
    //   5746: invokespecial 532	com/tencent/smtt/downloader/TbsApkDownloader:a	(ILjava/lang/String;Z)V
    //   5749: goto +28 -> 5777
    //   5752: aload_0
    //   5753: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   5756: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   5759: sipush 65218
    //   5762: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   5765: aload_0
    //   5766: iconst_0
    //   5767: invokespecial 828	com/tencent/smtt/downloader/TbsApkDownloader:b	(Z)Z
    //   5770: pop
    //   5771: goto +6 -> 5777
    //   5774: iload 9
    //   5776: istore_1
    //   5777: aload_0
    //   5778: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   5781: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   5784: astore 35
    //   5786: iload_1
    //   5787: ifeq +43 -> 5830
    //   5790: aload 35
    //   5792: getfield 304	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   5795: ldc_w 955
    //   5798: iconst_0
    //   5799: invokeinterface 312 3 0
    //   5804: istore 7
    //   5806: aload 35
    //   5808: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   5811: ldc_w 955
    //   5814: iload 7
    //   5816: iconst_1
    //   5817: iadd
    //   5818: invokestatic 427	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   5821: invokeinterface 418 3 0
    //   5826: pop
    //   5827: goto +66 -> 5893
    //   5830: aload 35
    //   5832: getfield 304	com/tencent/smtt/downloader/c:jdField_a_of_type_AndroidContentSharedPreferences	Landroid/content/SharedPreferences;
    //   5835: ldc_w 957
    //   5838: iconst_0
    //   5839: invokeinterface 312 3 0
    //   5844: istore 7
    //   5846: aload 35
    //   5848: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   5851: astore 36
    //   5853: iload 7
    //   5855: iconst_1
    //   5856: iadd
    //   5857: istore 7
    //   5859: aload 36
    //   5861: ldc_w 957
    //   5864: iload 7
    //   5866: invokestatic 427	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   5869: invokeinterface 418 3 0
    //   5874: pop
    //   5875: iload 7
    //   5877: aload 35
    //   5879: invokevirtual 959	com/tencent/smtt/downloader/c:b	()I
    //   5882: if_icmpne +11 -> 5893
    //   5885: aload_0
    //   5886: getfield 82	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b	Lcom/tencent/smtt/downloader/utils/i$b;
    //   5889: iconst_2
    //   5890: invokevirtual 804	com/tencent/smtt/downloader/utils/i$b:c	(I)V
    //   5893: iconst_1
    //   5894: istore 7
    //   5896: aload 35
    //   5898: invokevirtual 429	com/tencent/smtt/downloader/c:a	()V
    //   5901: aload_0
    //   5902: getfield 82	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_ComTencentSmttDownloaderUtilsI$b	Lcom/tencent/smtt/downloader/utils/i$b;
    //   5905: astore 35
    //   5907: iload_1
    //   5908: ifeq +6 -> 5914
    //   5911: goto +6 -> 5917
    //   5914: iconst_0
    //   5915: istore 7
    //   5917: aload 35
    //   5919: iload 7
    //   5921: invokevirtual 961	com/tencent/smtt/downloader/utils/i$b:f	(I)V
    //   5924: aload_0
    //   5925: invokespecial 963	com/tencent/smtt/downloader/TbsApkDownloader:d	()V
    //   5928: return
    //   5929: lload 13
    //   5931: lstore 17
    //   5933: aload_0
    //   5934: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   5937: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   5940: sipush 65220
    //   5943: invokevirtual 718	com/tencent/smtt/downloader/c:a	(I)V
    //   5946: iload_1
    //   5947: ifne +37 -> 5984
    //   5950: aload_0
    //   5951: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   5954: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   5957: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   5960: ldc_w 758
    //   5963: lload 13
    //   5965: invokestatic 771	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   5968: invokeinterface 418 3 0
    //   5973: pop
    //   5974: aload_0
    //   5975: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   5978: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   5981: invokevirtual 429	com/tencent/smtt/downloader/c:a	()V
    //   5984: aconst_null
    //   5985: astore 35
    //   5987: lload 15
    //   5989: lstore 19
    //   5991: lload 13
    //   5993: lstore 15
    //   5995: goto -5457 -> 538
    //   5998: iload_1
    //   5999: ifne +37 -> 6036
    //   6002: aload_0
    //   6003: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   6006: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   6009: getfield 404	com/tencent/smtt/downloader/c:jdField_a_of_type_JavaUtilMap	Ljava/util/Map;
    //   6012: ldc_w 758
    //   6015: lload 17
    //   6017: invokestatic 771	java/lang/Long:valueOf	(J)Ljava/lang/Long;
    //   6020: invokeinterface 418 3 0
    //   6025: pop
    //   6026: aload_0
    //   6027: getfield 72	com/tencent/smtt/downloader/TbsApkDownloader:jdField_a_of_type_AndroidContentContext	Landroid/content/Context;
    //   6030: invokestatic 301	com/tencent/smtt/downloader/c:a	(Landroid/content/Context;)Lcom/tencent/smtt/downloader/c;
    //   6033: invokevirtual 429	com/tencent/smtt/downloader/c:a	()V
    //   6036: aload 35
    //   6038: athrow
    //   6039: iconst_1
    //   6040: istore 7
    //   6042: goto -4363 -> 1679
    //   6045: iload 7
    //   6047: sipush 200
    //   6050: if_icmpeq -3366 -> 2684
    //   6053: iload 7
    //   6055: sipush 206
    //   6058: if_icmpne -3977 -> 2081
    //   6061: goto -3377 -> 2684
    //   6064: iload 7
    //   6066: sipush 403
    //   6069: if_icmpeq -3727 -> 2342
    //   6072: iload 7
    //   6074: sipush 406
    //   6077: if_icmpne -3693 -> 2384
    //   6080: goto -3738 -> 2342
    //   6083: iload_1
    //   6084: ifne +13 -> 6097
    //   6087: goto -3691 -> 2396
    //   6090: iload_1
    //   6091: ifne +6 -> 6097
    //   6094: goto -3698 -> 2396
    //   6097: lload 15
    //   6099: lstore 19
    //   6101: aconst_null
    //   6102: astore 35
    //   6104: lload 13
    //   6106: lstore 15
    //   6108: goto -5570 -> 538
    //   6111: astore 38
    //   6113: aconst_null
    //   6114: astore 36
    //   6116: aconst_null
    //   6117: astore 37
    //   6119: aload 35
    //   6121: astore 39
    //   6123: aload 38
    //   6125: astore 35
    //   6127: aload 36
    //   6129: astore 38
    //   6131: aload 39
    //   6133: astore 36
    //   6135: goto -848 -> 5287
    //   6138: astore 38
    //   6140: aconst_null
    //   6141: astore 36
    //   6143: aconst_null
    //   6144: astore 37
    //   6146: goto -1352 -> 4794
    //   6149: goto -2704 -> 3445
    //   6152: goto -2707 -> 3445
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	6155	0	this	TbsApkDownloader
    //   0	6155	1	paramBoolean1	boolean
    //   0	6155	2	paramBoolean2	boolean
    //   4032	32	3	d1	double
    //   4050	15	5	d2	double
    //   54	6024	7	j	int
    //   1774	7	8	k	int
    //   550	5225	9	bool1	boolean
    //   3267	1263	10	bool2	boolean
    //   78	5598	11	bool3	boolean
    //   3316	3	12	bool4	boolean
    //   599	5506	13	l1	long
    //   513	5594	15	l2	long
    //   611	5405	17	l3	long
    //   492	5608	19	l4	long
    //   740	4634	21	l5	long
    //   607	3906	23	l6	long
    //   3271	1266	25	l7	long
    //   3276	1160	27	l8	long
    //   3280	1171	29	l9	long
    //   595	3291	31	l10	long
    //   3890	544	33	l11	long
    //   104	702	35	localObject1	Object
    //   1020	1	35	localThrowable1	Throwable
    //   1050	575	35	localObject2	Object
    //   1640	1	35	localThrowable2	Throwable
    //   1664	467	35	localObject3	Object
    //   2298	1	35	localThrowable3	Throwable
    //   2714	1634	35	localObject4	Object
    //   4415	1	35	localThrowable4	Throwable
    //   4446	1	35	localObject5	Object
    //   4455	1	35	localObject6	Object
    //   4480	14	35	localObject7	Object
    //   4507	1	35	localObject8	Object
    //   4534	1	35	localObject9	Object
    //   4547	1	35	localObject10	Object
    //   4552	1	35	localIOException1	IOException
    //   4557	1	35	localObject11	Object
    //   4580	13	35	localIOException2	IOException
    //   4598	70	35	localObject12	Object
    //   4769	1	35	localObject13	Object
    //   4792	574	35	localObject14	Object
    //   5368	1	35	localThrowable5	Throwable
    //   5384	1	35	localThrowable6	Throwable
    //   5389	1	35	localObject15	Object
    //   5394	169	35	localThrowable7	Throwable
    //   5653	473	35	localObject16	Object
    //   101	6041	36	localObject17	Object
    //   149	5996	37	localObject18	Object
    //   3121	168	38	localObject19	Object
    //   3363	1	38	localObject20	Object
    //   3371	1	38	localIOException3	IOException
    //   3454	3	38	arrayOfString	String[]
    //   3491	7	38	localObject21	Object
    //   3503	1	38	localObject22	Object
    //   3512	1	38	localIOException4	IOException
    //   3568	40	38	localStringBuilder1	StringBuilder
    //   3632	1	38	localObject23	Object
    //   3637	1	38	localIOException5	IOException
    //   3642	1	38	localObject24	Object
    //   3651	1	38	localIOException6	IOException
    //   3718	89	38	localStringBuilder2	StringBuilder
    //   3846	1	38	localObject25	Object
    //   3855	1	38	localIOException7	IOException
    //   3926	534	38	localObject26	Object
    //   4472	1	38	localIOException8	IOException
    //   4489	1	38	localObject27	Object
    //   4502	1	38	localIOException9	IOException
    //   4527	36	38	localIOException10	IOException
    //   4571	202	38	localObject28	Object
    //   4783	25	38	localIOException11	IOException
    //   4839	486	38	localObject29	Object
    //   6111	13	38	localObject30	Object
    //   6129	1	38	localObject31	Object
    //   6138	1	38	localIOException12	IOException
    //   3264	1215	39	localObject32	Object
    //   4485	7	39	localObject33	Object
    //   4497	1635	39	localObject34	Object
    //   3218	302	40	arrayOfByte	byte[]
    //   5265	11	40	localObject35	Object
    // Exception table:
    //   from	to	target	type
    //   613	642	1020	java/lang/Throwable
    //   650	658	1020	java/lang/Throwable
    //   666	690	1020	java/lang/Throwable
    //   698	721	1020	java/lang/Throwable
    //   729	739	1020	java/lang/Throwable
    //   753	762	1020	java/lang/Throwable
    //   770	779	1020	java/lang/Throwable
    //   787	795	1020	java/lang/Throwable
    //   803	813	1020	java/lang/Throwable
    //   833	842	1020	java/lang/Throwable
    //   850	858	1020	java/lang/Throwable
    //   866	879	1020	java/lang/Throwable
    //   942	952	1020	java/lang/Throwable
    //   960	969	1020	java/lang/Throwable
    //   977	985	1020	java/lang/Throwable
    //   993	1006	1020	java/lang/Throwable
    //   1065	1071	1020	java/lang/Throwable
    //   1192	1201	1020	java/lang/Throwable
    //   1244	1250	1020	java/lang/Throwable
    //   1258	1267	1020	java/lang/Throwable
    //   1275	1284	1020	java/lang/Throwable
    //   1292	1300	1020	java/lang/Throwable
    //   1308	1318	1020	java/lang/Throwable
    //   1322	1331	1640	java/lang/Throwable
    //   1335	1344	1640	java/lang/Throwable
    //   1348	1357	1640	java/lang/Throwable
    //   1361	1369	1640	java/lang/Throwable
    //   1373	1384	1640	java/lang/Throwable
    //   1388	1394	1640	java/lang/Throwable
    //   1398	1407	1640	java/lang/Throwable
    //   1411	1420	1640	java/lang/Throwable
    //   1424	1432	1640	java/lang/Throwable
    //   1436	1445	1640	java/lang/Throwable
    //   1449	1462	1640	java/lang/Throwable
    //   1469	1478	1640	java/lang/Throwable
    //   1482	1491	1640	java/lang/Throwable
    //   1495	1503	1640	java/lang/Throwable
    //   1507	1516	1640	java/lang/Throwable
    //   1520	1530	1640	java/lang/Throwable
    //   1534	1545	1640	java/lang/Throwable
    //   1549	1555	1640	java/lang/Throwable
    //   1559	1568	1640	java/lang/Throwable
    //   1572	1581	1640	java/lang/Throwable
    //   1585	1593	1640	java/lang/Throwable
    //   1597	1606	1640	java/lang/Throwable
    //   1610	1620	1640	java/lang/Throwable
    //   1624	1637	1640	java/lang/Throwable
    //   1735	1743	1640	java/lang/Throwable
    //   1747	1753	1640	java/lang/Throwable
    //   1757	1763	1640	java/lang/Throwable
    //   1787	1798	1640	java/lang/Throwable
    //   1855	1869	1640	java/lang/Throwable
    //   1955	1965	1640	java/lang/Throwable
    //   1969	1980	1640	java/lang/Throwable
    //   1984	1994	1640	java/lang/Throwable
    //   1998	2002	1640	java/lang/Throwable
    //   2006	2012	1640	java/lang/Throwable
    //   2016	2026	1640	java/lang/Throwable
    //   2030	2039	1640	java/lang/Throwable
    //   2058	2071	1640	java/lang/Throwable
    //   2101	2113	1640	java/lang/Throwable
    //   2117	2125	1640	java/lang/Throwable
    //   2129	2135	1640	java/lang/Throwable
    //   2139	2149	1640	java/lang/Throwable
    //   2160	2168	1640	java/lang/Throwable
    //   2172	2185	1640	java/lang/Throwable
    //   2199	2211	1640	java/lang/Throwable
    //   2223	2232	1640	java/lang/Throwable
    //   2309	2315	1640	java/lang/Throwable
    //   2319	2332	1640	java/lang/Throwable
    //   2346	2357	1640	java/lang/Throwable
    //   2361	2374	1640	java/lang/Throwable
    //   2437	2448	1640	java/lang/Throwable
    //   2460	2477	1640	java/lang/Throwable
    //   2481	2488	1640	java/lang/Throwable
    //   2492	2505	1640	java/lang/Throwable
    //   2519	2530	1640	java/lang/Throwable
    //   2566	2571	1640	java/lang/Throwable
    //   2575	2582	1640	java/lang/Throwable
    //   2586	2599	1640	java/lang/Throwable
    //   2613	2622	1640	java/lang/Throwable
    //   2626	2633	1640	java/lang/Throwable
    //   2645	2650	1640	java/lang/Throwable
    //   2661	2674	1640	java/lang/Throwable
    //   2808	2818	1640	java/lang/Throwable
    //   2822	2831	1640	java/lang/Throwable
    //   2835	2844	1640	java/lang/Throwable
    //   2848	2856	1640	java/lang/Throwable
    //   2860	2869	1640	java/lang/Throwable
    //   2873	2883	1640	java/lang/Throwable
    //   2887	2898	1640	java/lang/Throwable
    //   2906	2913	1640	java/lang/Throwable
    //   2917	2927	1640	java/lang/Throwable
    //   2931	2938	1640	java/lang/Throwable
    //   2942	2950	1640	java/lang/Throwable
    //   2961	2970	1640	java/lang/Throwable
    //   2974	2983	1640	java/lang/Throwable
    //   2987	2995	1640	java/lang/Throwable
    //   2999	3008	1640	java/lang/Throwable
    //   3012	3022	1640	java/lang/Throwable
    //   3026	3038	1640	java/lang/Throwable
    //   3042	3055	1640	java/lang/Throwable
    //   3062	3072	1640	java/lang/Throwable
    //   3076	3089	1640	java/lang/Throwable
    //   2241	2254	2298	java/lang/Throwable
    //   3329	3338	3363	finally
    //   3344	3357	3363	finally
    //   3472	3485	3363	finally
    //   3329	3338	3371	java/io/IOException
    //   3344	3357	3371	java/io/IOException
    //   3472	3485	3371	java/io/IOException
    //   3397	3414	3491	finally
    //   3418	3426	3491	finally
    //   3432	3437	3491	finally
    //   3445	3456	3491	finally
    //   3397	3414	3512	java/io/IOException
    //   3418	3426	3512	java/io/IOException
    //   3432	3437	3512	java/io/IOException
    //   3445	3456	3512	java/io/IOException
    //   3596	3629	3632	finally
    //   3596	3629	3637	java/io/IOException
    //   3552	3596	3642	finally
    //   3552	3596	3651	java/io/IOException
    //   3676	3686	3846	finally
    //   3694	3703	3846	finally
    //   3711	3720	3846	finally
    //   3728	3737	3846	finally
    //   3745	3754	3846	finally
    //   3762	3771	3846	finally
    //   3779	3795	3846	finally
    //   3803	3815	3846	finally
    //   3823	3836	3846	finally
    //   3919	3928	3846	finally
    //   3936	3945	3846	finally
    //   3953	3961	3846	finally
    //   3969	3978	3846	finally
    //   3986	3996	3846	finally
    //   4004	4015	3846	finally
    //   4023	4029	3846	finally
    //   4041	4047	3846	finally
    //   4082	4091	3846	finally
    //   4099	4108	3846	finally
    //   4116	4124	3846	finally
    //   4132	4143	3846	finally
    //   4151	4161	3846	finally
    //   4185	4195	3846	finally
    //   4203	4214	3846	finally
    //   4222	4232	3846	finally
    //   4240	4244	3846	finally
    //   4252	4258	3846	finally
    //   4266	4276	3846	finally
    //   4284	4293	3846	finally
    //   4301	4314	3846	finally
    //   3676	3686	3855	java/io/IOException
    //   3694	3703	3855	java/io/IOException
    //   3711	3720	3855	java/io/IOException
    //   3728	3737	3855	java/io/IOException
    //   3745	3754	3855	java/io/IOException
    //   3762	3771	3855	java/io/IOException
    //   3779	3795	3855	java/io/IOException
    //   3803	3815	3855	java/io/IOException
    //   3823	3836	3855	java/io/IOException
    //   3919	3928	3855	java/io/IOException
    //   3936	3945	3855	java/io/IOException
    //   3953	3961	3855	java/io/IOException
    //   3969	3978	3855	java/io/IOException
    //   3986	3996	3855	java/io/IOException
    //   4004	4015	3855	java/io/IOException
    //   4023	4029	3855	java/io/IOException
    //   4041	4047	3855	java/io/IOException
    //   4082	4091	3855	java/io/IOException
    //   4099	4108	3855	java/io/IOException
    //   4116	4124	3855	java/io/IOException
    //   4132	4143	3855	java/io/IOException
    //   4151	4161	3855	java/io/IOException
    //   4185	4195	3855	java/io/IOException
    //   4203	4214	3855	java/io/IOException
    //   4222	4232	3855	java/io/IOException
    //   4240	4244	3855	java/io/IOException
    //   4252	4258	3855	java/io/IOException
    //   4266	4276	3855	java/io/IOException
    //   4284	4293	3855	java/io/IOException
    //   4301	4314	3855	java/io/IOException
    //   4326	4332	4415	java/lang/Throwable
    //   4336	4342	4415	java/lang/Throwable
    //   4346	4352	4415	java/lang/Throwable
    //   3877	3892	4455	finally
    //   3877	3892	4472	java/io/IOException
    //   3379	3392	4485	finally
    //   3517	3532	4485	finally
    //   3379	3392	4502	java/io/IOException
    //   3517	3532	4502	java/io/IOException
    //   3273	3278	4507	finally
    //   3312	3318	4507	finally
    //   3273	3278	4527	java/io/IOException
    //   3312	3318	4527	java/io/IOException
    //   3213	3243	4547	finally
    //   3213	3243	4552	java/io/IOException
    //   3132	3141	4557	finally
    //   3132	3141	4580	java/io/IOException
    //   3114	3123	4769	finally
    //   3114	3123	4783	java/io/IOException
    //   4794	4815	5265	finally
    //   4822	4909	5265	finally
    //   4985	5010	5265	finally
    //   5013	5026	5265	finally
    //   5107	5132	5265	finally
    //   4624	4630	5368	java/lang/Throwable
    //   4645	4651	5368	java/lang/Throwable
    //   4666	4672	5368	java/lang/Throwable
    //   4687	4694	5368	java/lang/Throwable
    //   4709	4722	5368	java/lang/Throwable
    //   4924	4930	5368	java/lang/Throwable
    //   4945	4951	5368	java/lang/Throwable
    //   4966	4972	5368	java/lang/Throwable
    //   5041	5047	5368	java/lang/Throwable
    //   5062	5068	5368	java/lang/Throwable
    //   5083	5089	5368	java/lang/Throwable
    //   5147	5153	5368	java/lang/Throwable
    //   5168	5174	5368	java/lang/Throwable
    //   5189	5195	5368	java/lang/Throwable
    //   5302	5308	5368	java/lang/Throwable
    //   5323	5329	5368	java/lang/Throwable
    //   5344	5350	5368	java/lang/Throwable
    //   5365	5368	5368	java/lang/Throwable
    //   1660	1666	5384	java/lang/Throwable
    //   1683	1690	5384	java/lang/Throwable
    //   1694	1703	5384	java/lang/Throwable
    //   1707	1716	5384	java/lang/Throwable
    //   1720	1726	5384	java/lang/Throwable
    //   1770	1776	5384	java/lang/Throwable
    //   1807	1815	5384	java/lang/Throwable
    //   1819	1825	5384	java/lang/Throwable
    //   1829	1835	5384	java/lang/Throwable
    //   1839	1845	5384	java/lang/Throwable
    //   1873	1882	5384	java/lang/Throwable
    //   1886	1895	5384	java/lang/Throwable
    //   1899	1908	5384	java/lang/Throwable
    //   1912	1920	5384	java/lang/Throwable
    //   1924	1934	5384	java/lang/Throwable
    //   1938	1947	5384	java/lang/Throwable
    //   2043	2049	5384	java/lang/Throwable
    //   2688	2703	5384	java/lang/Throwable
    //   2707	2716	5384	java/lang/Throwable
    //   2720	2729	5384	java/lang/Throwable
    //   2733	2743	5384	java/lang/Throwable
    //   2747	2757	5384	java/lang/Throwable
    //   2761	2772	5384	java/lang/Throwable
    //   2776	2797	5384	java/lang/Throwable
    //   3106	3114	5384	java/lang/Throwable
    //   613	642	5389	finally
    //   650	658	5389	finally
    //   666	690	5389	finally
    //   698	721	5389	finally
    //   729	739	5389	finally
    //   753	762	5389	finally
    //   770	779	5389	finally
    //   787	795	5389	finally
    //   803	813	5389	finally
    //   833	842	5389	finally
    //   850	858	5389	finally
    //   866	879	5389	finally
    //   942	952	5389	finally
    //   960	969	5389	finally
    //   977	985	5389	finally
    //   993	1006	5389	finally
    //   1037	1042	5389	finally
    //   1046	1052	5389	finally
    //   1065	1071	5389	finally
    //   1078	1084	5389	finally
    //   1088	1097	5389	finally
    //   1101	1110	5389	finally
    //   1114	1122	5389	finally
    //   1126	1135	5389	finally
    //   1139	1149	5389	finally
    //   1153	1164	5389	finally
    //   1168	1179	5389	finally
    //   1192	1201	5389	finally
    //   1205	1211	5389	finally
    //   1215	1221	5389	finally
    //   1225	1231	5389	finally
    //   1244	1250	5389	finally
    //   1258	1267	5389	finally
    //   1275	1284	5389	finally
    //   1292	1300	5389	finally
    //   1308	1318	5389	finally
    //   1322	1331	5389	finally
    //   1335	1344	5389	finally
    //   1348	1357	5389	finally
    //   1361	1369	5389	finally
    //   1373	1384	5389	finally
    //   1388	1394	5389	finally
    //   1398	1407	5389	finally
    //   1411	1420	5389	finally
    //   1424	1432	5389	finally
    //   1436	1445	5389	finally
    //   1449	1462	5389	finally
    //   1469	1478	5389	finally
    //   1482	1491	5389	finally
    //   1495	1503	5389	finally
    //   1507	1516	5389	finally
    //   1520	1530	5389	finally
    //   1534	1545	5389	finally
    //   1549	1555	5389	finally
    //   1559	1568	5389	finally
    //   1572	1581	5389	finally
    //   1585	1593	5389	finally
    //   1597	1606	5389	finally
    //   1610	1620	5389	finally
    //   1624	1637	5389	finally
    //   1660	1666	5389	finally
    //   1683	1690	5389	finally
    //   1694	1703	5389	finally
    //   1707	1716	5389	finally
    //   1720	1726	5389	finally
    //   1735	1743	5389	finally
    //   1747	1753	5389	finally
    //   1757	1763	5389	finally
    //   1770	1776	5389	finally
    //   1787	1798	5389	finally
    //   1807	1815	5389	finally
    //   1819	1825	5389	finally
    //   1829	1835	5389	finally
    //   1839	1845	5389	finally
    //   1855	1869	5389	finally
    //   1873	1882	5389	finally
    //   1886	1895	5389	finally
    //   1899	1908	5389	finally
    //   1912	1920	5389	finally
    //   1924	1934	5389	finally
    //   1938	1947	5389	finally
    //   1955	1965	5389	finally
    //   1969	1980	5389	finally
    //   1984	1994	5389	finally
    //   1998	2002	5389	finally
    //   2006	2012	5389	finally
    //   2016	2026	5389	finally
    //   2030	2039	5389	finally
    //   2043	2049	5389	finally
    //   2058	2071	5389	finally
    //   2101	2113	5389	finally
    //   2117	2125	5389	finally
    //   2129	2135	5389	finally
    //   2139	2149	5389	finally
    //   2160	2168	5389	finally
    //   2172	2185	5389	finally
    //   2199	2211	5389	finally
    //   2223	2232	5389	finally
    //   2241	2254	5389	finally
    //   2309	2315	5389	finally
    //   2319	2332	5389	finally
    //   2346	2357	5389	finally
    //   2361	2374	5389	finally
    //   2437	2448	5389	finally
    //   2460	2477	5389	finally
    //   2481	2488	5389	finally
    //   2492	2505	5389	finally
    //   2519	2530	5389	finally
    //   2566	2571	5389	finally
    //   2575	2582	5389	finally
    //   2586	2599	5389	finally
    //   2613	2622	5389	finally
    //   2626	2633	5389	finally
    //   2645	2650	5389	finally
    //   2661	2674	5389	finally
    //   2688	2703	5389	finally
    //   2707	2716	5389	finally
    //   2720	2729	5389	finally
    //   2733	2743	5389	finally
    //   2747	2757	5389	finally
    //   2761	2772	5389	finally
    //   2776	2797	5389	finally
    //   2808	2818	5389	finally
    //   2822	2831	5389	finally
    //   2835	2844	5389	finally
    //   2848	2856	5389	finally
    //   2860	2869	5389	finally
    //   2873	2883	5389	finally
    //   2887	2898	5389	finally
    //   2906	2913	5389	finally
    //   2917	2927	5389	finally
    //   2931	2938	5389	finally
    //   2942	2950	5389	finally
    //   2961	2970	5389	finally
    //   2974	2983	5389	finally
    //   2987	2995	5389	finally
    //   2999	3008	5389	finally
    //   3012	3022	5389	finally
    //   3026	3038	5389	finally
    //   3042	3055	5389	finally
    //   3062	3072	5389	finally
    //   3076	3089	5389	finally
    //   3106	3114	5389	finally
    //   4326	4332	5389	finally
    //   4336	4342	5389	finally
    //   4346	4352	5389	finally
    //   4624	4630	5389	finally
    //   4645	4651	5389	finally
    //   4666	4672	5389	finally
    //   4687	4694	5389	finally
    //   4709	4722	5389	finally
    //   4924	4930	5389	finally
    //   4945	4951	5389	finally
    //   4966	4972	5389	finally
    //   5041	5047	5389	finally
    //   5062	5068	5389	finally
    //   5083	5089	5389	finally
    //   5147	5153	5389	finally
    //   5168	5174	5389	finally
    //   5189	5195	5389	finally
    //   5302	5308	5389	finally
    //   5323	5329	5389	finally
    //   5344	5350	5389	finally
    //   5365	5368	5389	finally
    //   5404	5412	5389	finally
    //   5420	5427	5389	finally
    //   5431	5439	5389	finally
    //   5443	5452	5389	finally
    //   5456	5465	5389	finally
    //   5469	5479	5389	finally
    //   5483	5492	5389	finally
    //   5496	5507	5389	finally
    //   5511	5521	5389	finally
    //   5525	5533	5389	finally
    //   5540	5545	5389	finally
    //   5549	5554	5389	finally
    //   5558	5571	5389	finally
    //   5575	5582	5389	finally
    //   5586	5599	5389	finally
    //   5933	5946	5389	finally
    //   1037	1042	5394	java/lang/Throwable
    //   1046	1052	5394	java/lang/Throwable
    //   1078	1084	5394	java/lang/Throwable
    //   1088	1097	5394	java/lang/Throwable
    //   1101	1110	5394	java/lang/Throwable
    //   1114	1122	5394	java/lang/Throwable
    //   1126	1135	5394	java/lang/Throwable
    //   1139	1149	5394	java/lang/Throwable
    //   1153	1164	5394	java/lang/Throwable
    //   1168	1179	5394	java/lang/Throwable
    //   1205	1211	5394	java/lang/Throwable
    //   1215	1221	5394	java/lang/Throwable
    //   1225	1231	5394	java/lang/Throwable
    //   3146	3168	6111	finally
    //   3176	3206	6111	finally
    //   3146	3168	6138	java/io/IOException
    //   3176	3206	6138	java/io/IOException
  }
  
  public boolean a(boolean paramBoolean)
  {
    if ((paramBoolean) && (!c()) && (!com.tencent.smtt.downloader.utils.a.a(this.jdField_a_of_type_AndroidContentContext))) {
      return false;
    }
    String[] arrayOfString = this.jdField_a_of_type_ArrayOfJavaLangString;
    if (arrayOfString != null)
    {
      int j = this.jdField_a_of_type_Int;
      if ((j >= 0) && (j < arrayOfString.length))
      {
        this.jdField_a_of_type_Int = (j + 1);
        this.jdField_c_of_type_JavaLangString = arrayOfString[j];
        this.f = 0;
        this.g = 0;
        this.jdField_a_of_type_Long = -1L;
        this.jdField_a_of_type_Boolean = false;
        this.jdField_b_of_type_Boolean = false;
        this.jdField_c_of_type_Boolean = false;
        this.jdField_d_of_type_Boolean = false;
        return true;
      }
    }
    return false;
  }
  
  public void b()
  {
    a();
    b(false);
    b(true);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\TbsApkDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */