package com.tencent.mtt.browser.download.engine;

import android.text.TextUtils;
import android.util.Log;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.Apn;
import com.tencent.common.http.MttInputStream.recvFullyBack;
import com.tencent.common.http.MttRequestBase;
import com.tencent.common.http.MttResponse;
import com.tencent.common.http.RequesterFactory;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.mtt.base.task.Task;
import com.tencent.mtt.base.utils.DLDeviceUtils;
import com.tencent.mtt.base.utils.DLQBUrlUtils;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NoRouteToHostException;
import java.net.PortUnreachableException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.conn.ConnectTimeoutException;

public class HttpDownloader
  extends Task
  implements MttInputStream.recvFullyBack
{
  public static final int FIRE_THREHOLD = 1000;
  String A = "-3a";
  String B = "-3b";
  String C = "-3c";
  String D = "-3d";
  String E = "-3f";
  String F = "-60";
  String G = "-61";
  String H = "-62";
  String I = "-63";
  String J = "-64";
  String K = "-65";
  String L = "-66";
  String M = "-67";
  String N = "-68";
  String O = "-69";
  String P = "-6a";
  String Q = "-6b";
  String R = "-8a";
  String S = "-7";
  String T = "-80";
  String U = "-REQ";
  String V = "-RSP";
  String W;
  String X = "";
  int jdField_a_of_type_Int = 5;
  long jdField_a_of_type_Long = 0L;
  DownloadSection jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection;
  final DownloadTask jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask;
  String jdField_a_of_type_JavaLangString;
  boolean jdField_a_of_type_Boolean = false;
  int jdField_b_of_type_Int = -1;
  long jdField_b_of_type_Long = 0L;
  String jdField_b_of_type_JavaLangString = "";
  boolean jdField_b_of_type_Boolean = true;
  int jdField_c_of_type_Int = 0;
  long jdField_c_of_type_Long = 0L;
  String jdField_c_of_type_JavaLangString = "";
  boolean jdField_c_of_type_Boolean = false;
  int jdField_d_of_type_Int = 0;
  long jdField_d_of_type_Long = 0L;
  String jdField_d_of_type_JavaLangString = "-1";
  boolean jdField_d_of_type_Boolean = false;
  final int jdField_e_of_type_Int = 30000;
  String jdField_e_of_type_JavaLangString = "-21";
  boolean jdField_e_of_type_Boolean = false;
  int jdField_f_of_type_Int = -1;
  String jdField_f_of_type_JavaLangString = "-41";
  boolean jdField_f_of_type_Boolean = false;
  int jdField_g_of_type_Int = 0;
  String jdField_g_of_type_JavaLangString = "-42";
  boolean jdField_g_of_type_Boolean = false;
  int jdField_h_of_type_Int = 0;
  String jdField_h_of_type_JavaLangString = "-21";
  private boolean jdField_h_of_type_Boolean = false;
  private int jdField_i_of_type_Int = 0;
  String jdField_i_of_type_JavaLangString = "-22";
  private int jdField_j_of_type_Int = 0;
  String jdField_j_of_type_JavaLangString = "-52";
  String k = "-53";
  String l = "-54";
  String m = "-54";
  public Object mDetectDownloaderLock = new Object();
  public long mDownloadExceptionSleepTime = 100L;
  public long mDownloadNoNetworkTryTime = 2000L;
  public boolean mIsDetectDownloader = false;
  public boolean mNeedGoon = true;
  public long mStartDownloadTime = 0L;
  String n = "-55";
  String o = "-56";
  String p = "-30";
  String q = "-31";
  String r = "-32";
  String s = "-3";
  String t = "-33";
  String u = "-34";
  String v = "-35";
  String w = "-36";
  String x = "-37";
  String y = "-38";
  String z = "-39";
  
  protected HttpDownloader(DownloadTask paramDownloadTask, DownloadSection paramDownloadSection, int paramInt)
  {
    this.mNeedNotifyCanceled = true;
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask = paramDownloadTask;
    this.jdField_b_of_type_Int = paramInt;
    this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection = paramDownloadSection;
    this.jdField_f_of_type_Int = -1;
    this.mMttRequest = RequesterFactory.getMttRequestBase();
    this.mMttRequest.setRequestType((byte)104);
    if ((!paramDownloadTask.isHidden()) && (!paramDownloadTask.isQQMarketTask())) {
      this.mMttRequest.setRequestInterceptor(DownloaderInterceptor.getInstance());
    }
    paramDownloadSection = paramDownloadTask.getDownloaderDownloadDataUrl();
    if (!TextUtils.isEmpty(paramDownloadSection)) {
      this.mMttRequest.setUrl(paramDownloadSection);
    } else {
      this.mMttRequest.setUrl(paramDownloadTask.getTaskUrl());
    }
    addObserver(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask);
  }
  
  private void b(long paramLong)
  {
    if (paramLong > 3000L)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("spendTime : ");
      localStringBuilder.append(paramLong);
      localStringBuilder.append(", downloaderId : ");
      localStringBuilder.append(this.jdField_b_of_type_Int);
      localStringBuilder.append(", mAboveTimes : ");
      localStringBuilder.append(this.jdField_i_of_type_Int);
      FLogger.d("ZHTAG", localStringBuilder.toString());
      this.jdField_i_of_type_Int += 1;
      return;
    }
    this.jdField_i_of_type_Int = 0;
  }
  
  private void f()
  {
    if (this.mMttResponse != null)
    {
      Object localObject = this.mMttResponse.getRspHeaderMaps();
      localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("<");
      if (localObject != null)
      {
        localStringBuilder1.append("HEADER[");
        localStringBuilder1.append(localObject.toString());
        localStringBuilder1.append("]");
      }
      else
      {
        localStringBuilder1.append("HEADER[NULL]");
      }
      localObject = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask;
      if ((localObject != null) && (((DownloadTask)localObject).getExtFlagPlugin()))
      {
        localObject = this.mMttRequest.getUrl();
        try
        {
          localObject = InetAddress.getByName(new URL((String)localObject).getHost()).getHostAddress();
          localStringBuilder1.append(",IP[");
          localStringBuilder1.append((String)localObject);
          localStringBuilder1.append("]");
        }
        catch (UnknownHostException localUnknownHostException)
        {
          localUnknownHostException.printStackTrace();
          localStringBuilder1.append(",IP[UnknownHostException]");
        }
        catch (MalformedURLException localMalformedURLException)
        {
          localMalformedURLException.printStackTrace();
          localStringBuilder1.append(",IP[MalformedURLException]");
        }
      }
      localStringBuilder1.append(">");
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(this.jdField_b_of_type_JavaLangString);
      localStringBuilder2.append(localStringBuilder1.toString());
      this.jdField_b_of_type_JavaLangString = localStringBuilder2.toString();
    }
    StringBuilder localStringBuilder1 = new StringBuilder();
    localStringBuilder1.append("setHttpFailedErrorDesc() mErrorDes:");
    localStringBuilder1.append(this.jdField_b_of_type_JavaLangString);
    Log.d("FUCK_RSP", localStringBuilder1.toString());
  }
  
  public static Object invokeStatic(String paramString1, String paramString2)
  {
    try
    {
      paramString1 = Class.forName(paramString1).getMethod(paramString2, new Class[0]).invoke(null, new Object[0]);
      return paramString1;
    }
    catch (Throwable paramString1) {}
    return null;
  }
  
  String a(Map<String, List<String>> paramMap, String paramString)
  {
    if (paramMap.containsKey(paramString)) {
      paramMap = (List)paramMap.get(paramString);
    } else {
      paramMap = null;
    }
    if (paramMap != null)
    {
      paramString = "";
      Iterator localIterator = paramMap.iterator();
      StringBuilder localStringBuilder;
      for (paramMap = paramString; localIterator.hasNext(); paramMap = localStringBuilder.toString())
      {
        paramString = (String)localIterator.next();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(paramMap);
        localStringBuilder.append(paramString);
        localStringBuilder.append(",");
      }
      return paramMap;
    }
    return null;
  }
  
  void a()
  {
    setStatus((byte)3);
    this.jdField_c_of_type_Int = 0;
    fireObserverEvent();
  }
  
  void a(int paramInt)
  {
    this.jdField_c_of_type_Int = paramInt;
    setStatus((byte)5);
    fireObserverEvent();
  }
  
  void a(long paramLong)
  {
    try
    {
      Thread.sleep(paramLong);
    }
    catch (InterruptedException localInterruptedException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Interrupted while sleeping to retry - ");
      localStringBuilder.append(localInterruptedException);
      FLogger.d("HttpDownloader", localStringBuilder.toString());
    }
    this.jdField_d_of_type_Int += 1;
  }
  
  void a(MttRequestBase paramMttRequestBase, MttResponse paramMttResponse)
  {
    if (paramMttRequestBase != null)
    {
      Object localObject = paramMttRequestBase.getHeaders();
      paramMttRequestBase = new StringBuilder();
      if (localObject != null)
      {
        paramMttRequestBase.append("ae:");
        paramMttRequestBase.append((String)((Map)localObject).get("Accept-Encoding"));
        paramMttRequestBase.append(";");
        paramMttRequestBase.append("ra:");
        paramMttRequestBase.append((String)((Map)localObject).get("Range"));
        paramMttRequestBase.append(";");
      }
      else
      {
        paramMttRequestBase.append("NO_HE");
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(this.jdField_c_of_type_JavaLangString);
      ((StringBuilder)localObject).append("-REQ_H(");
      ((StringBuilder)localObject).append(paramMttRequestBase.toString());
      ((StringBuilder)localObject).append(")");
      this.jdField_c_of_type_JavaLangString = ((StringBuilder)localObject).toString();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(" >>> Req Header [");
      ((StringBuilder)localObject).append(paramMttRequestBase.toString());
      ((StringBuilder)localObject).append("]");
      Log.d("DOWNLOADER_F", ((StringBuilder)localObject).toString());
    }
    if (paramMttResponse != null)
    {
      paramMttResponse = paramMttResponse.getRspHeaderMaps();
      paramMttRequestBase = new StringBuilder();
      if (paramMttResponse != null)
      {
        paramMttRequestBase.append("ce:");
        paramMttRequestBase.append(a(paramMttResponse, "Content-Encoding"));
        paramMttRequestBase.append(";");
        paramMttRequestBase.append("ct:");
        paramMttRequestBase.append(a(paramMttResponse, "Content-Type"));
        paramMttRequestBase.append(";");
        paramMttRequestBase.append("cl:");
        paramMttRequestBase.append(a(paramMttResponse, "Content-Length"));
        paramMttRequestBase.append(";");
        paramMttRequestBase.append("qce:");
        paramMttRequestBase.append(a(paramMttResponse, "Q-Content-Encoding"));
        paramMttRequestBase.append(";");
        paramMttRequestBase.append("qcl:");
        paramMttRequestBase.append(a(paramMttResponse, "Q-Content-Length"));
        paramMttRequestBase.append(";");
        paramMttRequestBase.append("et:");
        paramMttRequestBase.append(a(paramMttResponse, "ETag"));
        paramMttRequestBase.append(";");
        paramMttRequestBase.append("ar:");
        paramMttRequestBase.append(a(paramMttResponse, "Accept-Ranges"));
        paramMttRequestBase.append(";");
      }
      else
      {
        paramMttRequestBase.append("NO_HE");
      }
      paramMttResponse = new StringBuilder();
      paramMttResponse.append(this.jdField_c_of_type_JavaLangString);
      paramMttResponse.append("-RSP_H(");
      paramMttResponse.append(paramMttRequestBase.toString());
      paramMttResponse.append(")");
      this.jdField_c_of_type_JavaLangString = paramMttResponse.toString();
      paramMttResponse = new StringBuilder();
      paramMttResponse.append(" <<< Rsp Header [");
      paramMttResponse.append(paramMttRequestBase.toString());
      paramMttResponse.append("]");
      Log.d("DOWNLOADER_F", paramMttResponse.toString());
    }
  }
  
  void a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.jdField_c_of_type_JavaLangString);
    localStringBuilder.append(paramString);
    localStringBuilder.append("(");
    localStringBuilder.append(System.currentTimeMillis() - this.mStartDownloadTime);
    localStringBuilder.append(")");
    this.jdField_c_of_type_JavaLangString = localStringBuilder.toString();
  }
  
  /* Error */
  boolean a(MttResponse paramMttResponse)
  {
    // Byte code:
    //   0: new 397	java/lang/StringBuilder
    //   3: dup
    //   4: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   7: astore 13
    //   9: aload 13
    //   11: aload_0
    //   12: invokevirtual 561	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   15: pop
    //   16: aload 13
    //   18: ldc_w 636
    //   21: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   24: pop
    //   25: ldc_w 563
    //   28: aload 13
    //   30: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   33: invokestatic 424	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   36: ldc_w 563
    //   39: ldc_w 638
    //   42: invokestatic 424	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   45: new 397	java/lang/StringBuilder
    //   48: dup
    //   49: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   52: astore 13
    //   54: aload 13
    //   56: ldc_w 640
    //   59: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: pop
    //   63: aload 13
    //   65: aload_1
    //   66: invokevirtual 644	com/tencent/common/http/MttResponse:getContentType	()Lcom/tencent/common/http/ContentType;
    //   69: getfield 649	com/tencent/common/http/ContentType:mType	Ljava/lang/String;
    //   72: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   75: pop
    //   76: aload 13
    //   78: ldc_w 651
    //   81: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   84: pop
    //   85: aload 13
    //   87: aload_1
    //   88: invokevirtual 644	com/tencent/common/http/MttResponse:getContentType	()Lcom/tencent/common/http/ContentType;
    //   91: getfield 654	com/tencent/common/http/ContentType:mTypeValue	Ljava/lang/String;
    //   94: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: pop
    //   98: ldc_w 563
    //   101: aload 13
    //   103: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   106: invokestatic 424	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   109: new 397	java/lang/StringBuilder
    //   112: dup
    //   113: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   116: astore 13
    //   118: aload 13
    //   120: ldc_w 656
    //   123: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   126: pop
    //   127: aload 13
    //   129: aload_1
    //   130: invokevirtual 659	com/tencent/common/http/MttResponse:getContentDisposition	()Ljava/lang/String;
    //   133: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   136: pop
    //   137: ldc_w 563
    //   140: aload 13
    //   142: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   145: invokestatic 424	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   148: aload_0
    //   149: getfield 311	com/tencent/mtt/browser/download/engine/HttpDownloader:mIsDetectDownloader	Z
    //   152: ifeq +148 -> 300
    //   155: aload_0
    //   156: getfield 109	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_e_of_type_Boolean	Z
    //   159: ifeq +19 -> 178
    //   162: aload_0
    //   163: aload_0
    //   164: getfield 211	com/tencent/mtt/browser/download/engine/HttpDownloader:z	Ljava/lang/String;
    //   167: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   170: aload_0
    //   171: getfield 333	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask	Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   174: iconst_1
    //   175: invokevirtual 665	com/tencent/mtt/browser/download/engine/DownloadTask:setRangeNotSupported	(Z)V
    //   178: aload_0
    //   179: getfield 115	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_f_of_type_Boolean	Z
    //   182: ifeq +17 -> 199
    //   185: aload_0
    //   186: aload_0
    //   187: getfield 287	com/tencent/mtt/browser/download/engine/HttpDownloader:S	Ljava/lang/String;
    //   190: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   193: aload_0
    //   194: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   197: iconst_1
    //   198: ireturn
    //   199: aload_0
    //   200: getfield 345	com/tencent/mtt/browser/download/engine/HttpDownloader:mMttRequest	Lcom/tencent/common/http/MttRequestBase;
    //   203: invokevirtual 453	com/tencent/common/http/MttRequestBase:getUrl	()Ljava/lang/String;
    //   206: astore 13
    //   208: aload_0
    //   209: iconst_1
    //   210: putfield 318	com/tencent/mtt/browser/download/engine/HttpDownloader:mNeedGoon	Z
    //   213: aload_0
    //   214: getfield 333	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask	Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   217: astore 14
    //   219: aload 14
    //   221: aload 14
    //   223: invokevirtual 671	com/tencent/mtt/browser/download/engine/DownloadTask:getDownloadTaskId	()I
    //   226: aload 13
    //   228: aload_1
    //   229: invokevirtual 675	com/tencent/mtt/browser/download/engine/DownloadTask:firstSectionComeBack	(ILjava/lang/String;Lcom/tencent/common/http/MttResponse;)Z
    //   232: pop
    //   233: aload_0
    //   234: getfield 316	com/tencent/mtt/browser/download/engine/HttpDownloader:mDetectDownloaderLock	Ljava/lang/Object;
    //   237: astore 13
    //   239: aload 13
    //   241: monitorenter
    //   242: aload_0
    //   243: getfield 316	com/tencent/mtt/browser/download/engine/HttpDownloader:mDetectDownloaderLock	Ljava/lang/Object;
    //   246: invokevirtual 678	java/lang/Object:wait	()V
    //   249: goto +14 -> 263
    //   252: astore_1
    //   253: goto +42 -> 295
    //   256: astore 14
    //   258: aload 14
    //   260: invokevirtual 679	java/lang/InterruptedException:printStackTrace	()V
    //   263: aload 13
    //   265: monitorexit
    //   266: aload_0
    //   267: getfield 318	com/tencent/mtt/browser/download/engine/HttpDownloader:mNeedGoon	Z
    //   270: ifne +13 -> 283
    //   273: aload_0
    //   274: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   277: aload_0
    //   278: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   281: iconst_1
    //   282: ireturn
    //   283: ldc_w 416
    //   286: ldc_w 684
    //   289: invokestatic 424	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   292: goto +8 -> 300
    //   295: aload 13
    //   297: monitorexit
    //   298: aload_1
    //   299: athrow
    //   300: invokestatic 629	java/lang/System:currentTimeMillis	()J
    //   303: lstore 6
    //   305: aload_1
    //   306: invokevirtual 688	com/tencent/common/http/MttResponse:getInputStream	()Lcom/tencent/common/http/MttInputStream;
    //   309: astore 13
    //   311: aload 13
    //   313: ifnonnull +9 -> 322
    //   316: aload_0
    //   317: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   320: iconst_0
    //   321: ireturn
    //   322: aload_0
    //   323: getfield 333	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask	Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   326: iconst_2
    //   327: invokevirtual 689	com/tencent/mtt/browser/download/engine/DownloadTask:setStatus	(B)V
    //   330: getstatic 694	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:BUFFERLEN	I
    //   333: istore 4
    //   335: aload_0
    //   336: aload_0
    //   337: getfield 141	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_h_of_type_JavaLangString	Ljava/lang/String;
    //   340: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   343: iconst_0
    //   344: istore_2
    //   345: aload_0
    //   346: getfield 115	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_f_of_type_Boolean	Z
    //   349: ifeq +21 -> 370
    //   352: aload_0
    //   353: aload_0
    //   354: getfield 287	com/tencent/mtt/browser/download/engine/HttpDownloader:S	Ljava/lang/String;
    //   357: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   360: aload_0
    //   361: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   364: aload_0
    //   365: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   368: iconst_1
    //   369: ireturn
    //   370: aload_0
    //   371: getfield 697	com/tencent/mtt/browser/download/engine/HttpDownloader:mCanceled	Z
    //   374: ifeq +10 -> 384
    //   377: aload_0
    //   378: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   381: goto +508 -> 889
    //   384: invokestatic 703	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:obtainBuffer	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;
    //   387: astore_1
    //   388: aload_1
    //   389: ifnonnull +24 -> 413
    //   392: aload_0
    //   393: getfield 697	com/tencent/mtt/browser/download/engine/HttpDownloader:mCanceled	Z
    //   396: ifeq +10 -> 406
    //   399: aload_0
    //   400: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   403: goto +10 -> 413
    //   406: invokestatic 703	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:obtainBuffer	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;
    //   409: astore_1
    //   410: goto -22 -> 388
    //   413: aload_1
    //   414: aload_0
    //   415: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   418: getfield 708	com/tencent/mtt/browser/download/engine/DownloadSection:mCurrentIndex	I
    //   421: putfield 711	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:mBufferSectionIndex	I
    //   424: aload_0
    //   425: getfield 697	com/tencent/mtt/browser/download/engine/HttpDownloader:mCanceled	Z
    //   428: ifeq +14 -> 442
    //   431: aload_0
    //   432: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   435: aload_1
    //   436: invokestatic 715	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:recyleBuffer	(Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;)V
    //   439: goto +450 -> 889
    //   442: invokestatic 629	java/lang/System:currentTimeMillis	()J
    //   445: lstore 8
    //   447: aload 13
    //   449: aload_1
    //   450: getfield 719	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:data	[B
    //   453: aload_1
    //   454: getfield 722	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:len	I
    //   457: iload 4
    //   459: aload_0
    //   460: invokevirtual 728	com/tencent/common/http/MttInputStream:readFully	([BIILcom/tencent/common/http/MttInputStream$recvFullyBack;)I
    //   463: istore_3
    //   464: invokestatic 629	java/lang/System:currentTimeMillis	()J
    //   467: lstore 10
    //   469: aload_0
    //   470: lload 10
    //   472: lload 8
    //   474: lsub
    //   475: invokespecial 730	com/tencent/mtt/browser/download/engine/HttpDownloader:b	(J)V
    //   478: iload_3
    //   479: ifge +136 -> 615
    //   482: aload_0
    //   483: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   486: invokevirtual 733	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionNextDownloadPos	()J
    //   489: aload_0
    //   490: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   493: invokevirtual 736	com/tencent/mtt/browser/download/engine/DownloadSection:getCurrentSectionEndPos	()J
    //   496: lcmp
    //   497: ifge +107 -> 604
    //   500: aload_0
    //   501: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   504: invokevirtual 736	com/tencent/mtt/browser/download/engine/DownloadSection:getCurrentSectionEndPos	()J
    //   507: ldc2_w 737
    //   510: lcmp
    //   511: ifeq +93 -> 604
    //   514: new 397	java/lang/StringBuilder
    //   517: dup
    //   518: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   521: astore 13
    //   523: aload 13
    //   525: aload_0
    //   526: getfield 320	com/tencent/mtt/browser/download/engine/HttpDownloader:X	Ljava/lang/String;
    //   529: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   532: pop
    //   533: aload 13
    //   535: ldc_w 740
    //   538: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   541: pop
    //   542: aload 13
    //   544: ldc_w 623
    //   547: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   550: pop
    //   551: aload 13
    //   553: aload_0
    //   554: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   557: invokevirtual 733	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionNextDownloadPos	()J
    //   560: invokevirtual 407	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   563: pop
    //   564: aload 13
    //   566: ldc_w 742
    //   569: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   572: pop
    //   573: aload 13
    //   575: aload_0
    //   576: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   579: invokevirtual 745	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionEndPos	()Ljava/lang/String;
    //   582: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   585: pop
    //   586: aload 13
    //   588: ldc_w 583
    //   591: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   594: pop
    //   595: aload_0
    //   596: aload 13
    //   598: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   601: putfield 320	com/tencent/mtt/browser/download/engine/HttpDownloader:X	Ljava/lang/String;
    //   604: aload_0
    //   605: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   608: aload_1
    //   609: invokestatic 715	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:recyleBuffer	(Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;)V
    //   612: goto +277 -> 889
    //   615: aload_1
    //   616: iload_3
    //   617: putfield 722	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:len	I
    //   620: aload_1
    //   621: aload_0
    //   622: getfield 333	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask	Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   625: invokevirtual 671	com/tencent/mtt/browser/download/engine/DownloadTask:getDownloadTaskId	()I
    //   628: aload_0
    //   629: getfield 91	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_b_of_type_Int	I
    //   632: aload_0
    //   633: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   636: invokevirtual 733	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionNextDownloadPos	()J
    //   639: invokevirtual 749	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:initBuffer	(IIJ)V
    //   642: aload_0
    //   643: getfield 697	com/tencent/mtt/browser/download/engine/HttpDownloader:mCanceled	Z
    //   646: ifeq +14 -> 660
    //   649: aload_0
    //   650: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   653: aload_1
    //   654: invokestatic 715	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:recyleBuffer	(Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;)V
    //   657: goto +232 -> 889
    //   660: aload_0
    //   661: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   664: invokevirtual 736	com/tencent/mtt/browser/download/engine/DownloadSection:getCurrentSectionEndPos	()J
    //   667: ldc2_w 737
    //   670: lcmp
    //   671: ifeq +139 -> 810
    //   674: aload_0
    //   675: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   678: invokevirtual 733	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionNextDownloadPos	()J
    //   681: lstore 8
    //   683: iload_3
    //   684: i2l
    //   685: lstore 10
    //   687: lload 8
    //   689: lload 10
    //   691: ladd
    //   692: aload_0
    //   693: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   696: invokevirtual 736	com/tencent/mtt/browser/download/engine/DownloadSection:getCurrentSectionEndPos	()J
    //   699: lconst_1
    //   700: ladd
    //   701: lcmp
    //   702: ifle +30 -> 732
    //   705: lload 10
    //   707: aload_0
    //   708: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   711: invokevirtual 733	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionNextDownloadPos	()J
    //   714: lload 10
    //   716: ladd
    //   717: aload_0
    //   718: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   721: invokevirtual 736	com/tencent/mtt/browser/download/engine/DownloadSection:getCurrentSectionEndPos	()J
    //   724: lsub
    //   725: lconst_1
    //   726: lsub
    //   727: lsub
    //   728: l2i
    //   729: istore_3
    //   730: iconst_1
    //   731: istore_2
    //   732: aload_0
    //   733: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   736: invokevirtual 753	com/tencent/mtt/browser/download/engine/DownloadSection:dataBuffer	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer;
    //   739: aload_1
    //   740: invokevirtual 756	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:appendItem	(Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;)V
    //   743: aload_0
    //   744: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   747: iload_3
    //   748: i2l
    //   749: invokevirtual 759	com/tencent/mtt/browser/download/engine/DownloadSection:addSectionDownloadLen	(J)V
    //   752: aload_0
    //   753: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   756: invokevirtual 736	com/tencent/mtt/browser/download/engine/DownloadSection:getCurrentSectionEndPos	()J
    //   759: aload_0
    //   760: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   763: invokevirtual 733	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionNextDownloadPos	()J
    //   766: lsub
    //   767: lconst_1
    //   768: ladd
    //   769: lstore 8
    //   771: iload 4
    //   773: istore_3
    //   774: iload_2
    //   775: istore 5
    //   777: lload 8
    //   779: lconst_0
    //   780: lcmp
    //   781: ifle +60 -> 841
    //   784: iload 4
    //   786: istore_3
    //   787: iload_2
    //   788: istore 5
    //   790: lload 8
    //   792: getstatic 694	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:BUFFERLEN	I
    //   795: i2l
    //   796: lcmp
    //   797: ifge +44 -> 841
    //   800: lload 8
    //   802: l2i
    //   803: istore_3
    //   804: iload_2
    //   805: istore 5
    //   807: goto +34 -> 841
    //   810: getstatic 694	com/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer:BUFFERLEN	I
    //   813: istore 4
    //   815: aload_0
    //   816: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   819: invokevirtual 753	com/tencent/mtt/browser/download/engine/DownloadSection:dataBuffer	()Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer;
    //   822: aload_1
    //   823: invokevirtual 756	com/tencent/mtt/browser/download/engine/DownloadDataBuffer:appendItem	(Lcom/tencent/mtt/browser/download/engine/DownloadDataBuffer$Buffer;)V
    //   826: aload_0
    //   827: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   830: iload_3
    //   831: i2l
    //   832: invokevirtual 759	com/tencent/mtt/browser/download/engine/DownloadSection:addSectionDownloadLen	(J)V
    //   835: iload 4
    //   837: istore_3
    //   838: iload_2
    //   839: istore 5
    //   841: aload_0
    //   842: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   845: invokevirtual 733	com/tencent/mtt/browser/download/engine/DownloadSection:getSectionNextDownloadPos	()J
    //   848: aload_0
    //   849: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   852: invokevirtual 736	com/tencent/mtt/browser/download/engine/DownloadSection:getCurrentSectionEndPos	()J
    //   855: lcmp
    //   856: ifle +24 -> 880
    //   859: aload_0
    //   860: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   863: invokevirtual 736	com/tencent/mtt/browser/download/engine/DownloadSection:getCurrentSectionEndPos	()J
    //   866: ldc2_w 737
    //   869: lcmp
    //   870: ifeq +10 -> 880
    //   873: aload_0
    //   874: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   877: goto +12 -> 889
    //   880: iload 5
    //   882: ifeq +141 -> 1023
    //   885: aload_0
    //   886: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   889: new 397	java/lang/StringBuilder
    //   892: dup
    //   893: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   896: astore_1
    //   897: aload_1
    //   898: ldc_w 761
    //   901: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   904: pop
    //   905: aload_1
    //   906: aload_0
    //   907: getfield 91	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_b_of_type_Int	I
    //   910: invokevirtual 412	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   913: pop
    //   914: ldc_w 416
    //   917: aload_1
    //   918: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   921: invokestatic 424	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   924: aload_0
    //   925: getfield 333	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask	Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   928: invokestatic 629	java/lang/System:currentTimeMillis	()J
    //   931: lload 6
    //   933: lsub
    //   934: putfield 764	com/tencent/mtt/browser/download/engine/DownloadTask:mDownloadCostTime	J
    //   937: aload_0
    //   938: getfield 697	com/tencent/mtt/browser/download/engine/HttpDownloader:mCanceled	Z
    //   941: ifeq +17 -> 958
    //   944: aload_0
    //   945: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   948: aload_0
    //   949: invokevirtual 766	com/tencent/mtt/browser/download/engine/HttpDownloader:e	()V
    //   952: aload_0
    //   953: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   956: iconst_1
    //   957: ireturn
    //   958: aload_0
    //   959: getfield 333	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask	Lcom/tencent/mtt/browser/download/engine/DownloadTask;
    //   962: invokevirtual 769	com/tencent/mtt/browser/download/engine/DownloadTask:isFileExist	()Z
    //   965: ifne +35 -> 1000
    //   968: aload_0
    //   969: aload_0
    //   970: getfield 179	com/tencent/mtt/browser/download/engine/HttpDownloader:r	Ljava/lang/String;
    //   973: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   976: aload_0
    //   977: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   980: aload_0
    //   981: getfield 335	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection	Lcom/tencent/mtt/browser/download/engine/DownloadSection;
    //   984: lconst_0
    //   985: invokevirtual 772	com/tencent/mtt/browser/download/engine/DownloadSection:setSectionDownloadLen	(J)V
    //   988: aload_0
    //   989: bipush 27
    //   991: invokevirtual 774	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(I)V
    //   994: aload_0
    //   995: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   998: iconst_1
    //   999: ireturn
    //   1000: aload_0
    //   1001: aload_0
    //   1002: getfield 183	com/tencent/mtt/browser/download/engine/HttpDownloader:s	Ljava/lang/String;
    //   1005: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   1008: aload_0
    //   1009: iconst_1
    //   1010: putfield 93	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_Boolean	Z
    //   1013: aload_0
    //   1014: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   1017: aload_0
    //   1018: invokevirtual 776	com/tencent/mtt/browser/download/engine/HttpDownloader:a	()V
    //   1021: iconst_1
    //   1022: ireturn
    //   1023: aload_0
    //   1024: getfield 115	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_f_of_type_Boolean	Z
    //   1027: ifeq +11 -> 1038
    //   1030: aload_0
    //   1031: aload_0
    //   1032: getfield 287	com/tencent/mtt/browser/download/engine/HttpDownloader:S	Ljava/lang/String;
    //   1035: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   1038: iload_3
    //   1039: istore 4
    //   1041: iload 5
    //   1043: istore_2
    //   1044: goto -699 -> 345
    //   1047: astore_1
    //   1048: new 397	java/lang/StringBuilder
    //   1051: dup
    //   1052: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   1055: astore 13
    //   1057: aload 13
    //   1059: ldc_w 778
    //   1062: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1065: pop
    //   1066: aload 13
    //   1068: aload_1
    //   1069: invokevirtual 781	java/io/IOException:getMessage	()Ljava/lang/String;
    //   1072: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1075: pop
    //   1076: aload 13
    //   1078: aload_1
    //   1079: invokevirtual 561	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1082: pop
    //   1083: ldc_w 416
    //   1086: aload 13
    //   1088: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1091: invokestatic 424	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1094: aload_0
    //   1095: getfield 115	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_f_of_type_Boolean	Z
    //   1098: ifeq +17 -> 1115
    //   1101: aload_0
    //   1102: aload_0
    //   1103: getfield 287	com/tencent/mtt/browser/download/engine/HttpDownloader:S	Ljava/lang/String;
    //   1106: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   1109: aload_0
    //   1110: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   1113: iconst_1
    //   1114: ireturn
    //   1115: new 397	java/lang/StringBuilder
    //   1118: dup
    //   1119: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   1122: astore 13
    //   1124: aload 13
    //   1126: ldc_w 783
    //   1129: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1132: pop
    //   1133: aload 13
    //   1135: aload_1
    //   1136: invokevirtual 784	java/io/IOException:toString	()Ljava/lang/String;
    //   1139: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1142: pop
    //   1143: aload_0
    //   1144: aload 13
    //   1146: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1149: putfield 113	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_b_of_type_JavaLangString	Ljava/lang/String;
    //   1152: aload_1
    //   1153: instanceof 786
    //   1156: istore 12
    //   1158: iload 12
    //   1160: ifne +10 -> 1170
    //   1163: aload_1
    //   1164: instanceof 788
    //   1167: ifeq +34 -> 1201
    //   1170: invokestatic 793	com/tencent/common/http/Apn:isNetworkConnected	()Z
    //   1173: ifeq +28 -> 1201
    //   1176: aload_0
    //   1177: getfield 97	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_d_of_type_Int	I
    //   1180: aload_0
    //   1181: getfield 89	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_a_of_type_Int	I
    //   1184: if_icmpge +17 -> 1201
    //   1187: aload_0
    //   1188: aload_0
    //   1189: getfield 235	com/tencent/mtt/browser/download/engine/HttpDownloader:F	Ljava/lang/String;
    //   1192: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   1195: aload_0
    //   1196: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   1199: iconst_0
    //   1200: ireturn
    //   1201: aload_0
    //   1202: iconst_3
    //   1203: putfield 95	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_c_of_type_Int	I
    //   1206: aload_1
    //   1207: instanceof 632
    //   1210: ifeq +140 -> 1350
    //   1213: aload_0
    //   1214: getfield 95	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_c_of_type_Int	I
    //   1217: iconst_3
    //   1218: if_icmpne +132 -> 1350
    //   1221: invokestatic 793	com/tencent/common/http/Apn:isNetworkConnected	()Z
    //   1224: ifeq +126 -> 1350
    //   1227: aload_0
    //   1228: getfield 113	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_b_of_type_JavaLangString	Ljava/lang/String;
    //   1231: ldc_w 795
    //   1234: invokevirtual 798	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
    //   1237: ifeq +113 -> 1350
    //   1240: aload_0
    //   1241: aload_0
    //   1242: getfield 149	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_j_of_type_JavaLangString	Ljava/lang/String;
    //   1245: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   1248: aload_0
    //   1249: getfield 303	com/tencent/mtt/browser/download/engine/HttpDownloader:mDownloadExceptionSleepTime	J
    //   1252: ldc2_w 799
    //   1255: lmul
    //   1256: lstore 6
    //   1258: aload_0
    //   1259: lload 6
    //   1261: putfield 303	com/tencent/mtt/browser/download/engine/HttpDownloader:mDownloadExceptionSleepTime	J
    //   1264: lload 6
    //   1266: invokestatic 556	java/lang/Thread:sleep	(J)V
    //   1269: goto +40 -> 1309
    //   1272: astore_1
    //   1273: new 397	java/lang/StringBuilder
    //   1276: dup
    //   1277: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   1280: astore 13
    //   1282: aload 13
    //   1284: ldc_w 558
    //   1287: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1290: pop
    //   1291: aload 13
    //   1293: aload_1
    //   1294: invokevirtual 561	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1297: pop
    //   1298: ldc_w 563
    //   1301: aload 13
    //   1303: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1306: invokestatic 424	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1309: aload_0
    //   1310: ldc 111
    //   1312: putfield 113	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_b_of_type_JavaLangString	Ljava/lang/String;
    //   1315: aload_0
    //   1316: getfield 697	com/tencent/mtt/browser/download/engine/HttpDownloader:mCanceled	Z
    //   1319: ifeq +17 -> 1336
    //   1322: aload_0
    //   1323: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   1326: aload_0
    //   1327: invokevirtual 766	com/tencent/mtt/browser/download/engine/HttpDownloader:e	()V
    //   1330: aload_0
    //   1331: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   1334: iconst_1
    //   1335: ireturn
    //   1336: aload_0
    //   1337: aload_0
    //   1338: getfield 239	com/tencent/mtt/browser/download/engine/HttpDownloader:G	Ljava/lang/String;
    //   1341: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   1344: aload_0
    //   1345: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   1348: iconst_0
    //   1349: ireturn
    //   1350: invokestatic 793	com/tencent/common/http/Apn:isNetworkConnected	()Z
    //   1353: ifne +103 -> 1456
    //   1356: aload_0
    //   1357: getfield 117	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_g_of_type_Int	I
    //   1360: iconst_2
    //   1361: if_icmpge +95 -> 1456
    //   1364: aload_0
    //   1365: aload_0
    //   1366: getfield 153	com/tencent/mtt/browser/download/engine/HttpDownloader:k	Ljava/lang/String;
    //   1369: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   1372: aload_0
    //   1373: aload_0
    //   1374: getfield 117	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_g_of_type_Int	I
    //   1377: iconst_1
    //   1378: iadd
    //   1379: putfield 117	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_g_of_type_Int	I
    //   1382: aload_0
    //   1383: getfield 307	com/tencent/mtt/browser/download/engine/HttpDownloader:mDownloadNoNetworkTryTime	J
    //   1386: invokestatic 556	java/lang/Thread:sleep	(J)V
    //   1389: goto +40 -> 1429
    //   1392: astore_1
    //   1393: new 397	java/lang/StringBuilder
    //   1396: dup
    //   1397: invokespecial 398	java/lang/StringBuilder:<init>	()V
    //   1400: astore 13
    //   1402: aload 13
    //   1404: ldc_w 558
    //   1407: invokevirtual 404	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   1410: pop
    //   1411: aload 13
    //   1413: aload_1
    //   1414: invokevirtual 561	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   1417: pop
    //   1418: ldc_w 563
    //   1421: aload 13
    //   1423: invokevirtual 419	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   1426: invokestatic 424	com/tencent/basesupport/FLogger:d	(Ljava/lang/String;Ljava/lang/String;)V
    //   1429: aload_0
    //   1430: getfield 697	com/tencent/mtt/browser/download/engine/HttpDownloader:mCanceled	Z
    //   1433: ifeq +17 -> 1450
    //   1436: aload_0
    //   1437: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   1440: aload_0
    //   1441: invokevirtual 766	com/tencent/mtt/browser/download/engine/HttpDownloader:e	()V
    //   1444: aload_0
    //   1445: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   1448: iconst_1
    //   1449: ireturn
    //   1450: aload_0
    //   1451: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   1454: iconst_0
    //   1455: ireturn
    //   1456: aload_0
    //   1457: invokevirtual 682	com/tencent/mtt/browser/download/engine/HttpDownloader:closeQuietly	()V
    //   1460: iload 12
    //   1462: ifeq +12 -> 1474
    //   1465: aload_0
    //   1466: bipush 39
    //   1468: putfield 95	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_c_of_type_Int	I
    //   1471: goto +90 -> 1561
    //   1474: aload_1
    //   1475: instanceof 802
    //   1478: ifeq +12 -> 1490
    //   1481: aload_0
    //   1482: bipush 36
    //   1484: putfield 95	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_c_of_type_Int	I
    //   1487: goto +74 -> 1561
    //   1490: aload_1
    //   1491: instanceof 804
    //   1494: ifeq +12 -> 1506
    //   1497: aload_0
    //   1498: bipush 37
    //   1500: putfield 95	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_c_of_type_Int	I
    //   1503: goto +58 -> 1561
    //   1506: aload_1
    //   1507: instanceof 806
    //   1510: ifne +45 -> 1555
    //   1513: aload_1
    //   1514: instanceof 808
    //   1517: ifeq +6 -> 1523
    //   1520: goto +35 -> 1555
    //   1523: aload_1
    //   1524: instanceof 428
    //   1527: ifeq +12 -> 1539
    //   1530: aload_0
    //   1531: bipush 34
    //   1533: putfield 95	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_c_of_type_Int	I
    //   1536: goto +25 -> 1561
    //   1539: aload_1
    //   1540: instanceof 426
    //   1543: ifeq +18 -> 1561
    //   1546: aload_0
    //   1547: bipush 35
    //   1549: putfield 95	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_c_of_type_Int	I
    //   1552: goto +9 -> 1561
    //   1555: aload_0
    //   1556: bipush 40
    //   1558: putfield 95	com/tencent/mtt/browser/download/engine/HttpDownloader:jdField_c_of_type_Int	I
    //   1561: aload_0
    //   1562: iconst_5
    //   1563: invokevirtual 545	com/tencent/mtt/browser/download/engine/HttpDownloader:setStatus	(B)V
    //   1566: aload_0
    //   1567: aload_0
    //   1568: getfield 175	com/tencent/mtt/browser/download/engine/HttpDownloader:q	Ljava/lang/String;
    //   1571: invokevirtual 661	com/tencent/mtt/browser/download/engine/HttpDownloader:a	(Ljava/lang/String;)V
    //   1574: aload_0
    //   1575: invokevirtual 548	com/tencent/mtt/browser/download/engine/HttpDownloader:fireObserverEvent	()V
    //   1578: aload_0
    //   1579: invokevirtual 667	com/tencent/mtt/browser/download/engine/HttpDownloader:c	()V
    //   1582: iconst_1
    //   1583: ireturn
    //   1584: astore 14
    //   1586: goto -1108 -> 478
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	1589	0	this	HttpDownloader
    //   0	1589	1	paramMttResponse	MttResponse
    //   344	700	2	i1	int
    //   463	576	3	i2	int
    //   333	707	4	i3	int
    //   775	267	5	i4	int
    //   303	962	6	l1	long
    //   445	356	8	l2	long
    //   467	248	10	l3	long
    //   1156	305	12	bool	boolean
    //   7	1415	13	localObject	Object
    //   217	5	14	localDownloadTask	DownloadTask
    //   256	3	14	localInterruptedException	InterruptedException
    //   1584	1	14	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   242	249	252	finally
    //   258	263	252	finally
    //   263	266	252	finally
    //   295	298	252	finally
    //   242	249	256	java/lang/InterruptedException
    //   300	311	1047	java/io/IOException
    //   316	320	1047	java/io/IOException
    //   322	343	1047	java/io/IOException
    //   345	368	1047	java/io/IOException
    //   370	381	1047	java/io/IOException
    //   384	388	1047	java/io/IOException
    //   392	403	1047	java/io/IOException
    //   406	410	1047	java/io/IOException
    //   413	439	1047	java/io/IOException
    //   442	469	1047	java/io/IOException
    //   469	478	1047	java/io/IOException
    //   482	604	1047	java/io/IOException
    //   604	612	1047	java/io/IOException
    //   615	657	1047	java/io/IOException
    //   660	683	1047	java/io/IOException
    //   687	730	1047	java/io/IOException
    //   732	771	1047	java/io/IOException
    //   790	800	1047	java/io/IOException
    //   810	835	1047	java/io/IOException
    //   841	877	1047	java/io/IOException
    //   885	889	1047	java/io/IOException
    //   889	937	1047	java/io/IOException
    //   1023	1038	1047	java/io/IOException
    //   1248	1269	1272	java/lang/InterruptedException
    //   1382	1389	1392	java/lang/InterruptedException
    //   469	478	1584	java/lang/Exception
  }
  
  boolean a(Exception paramException)
  {
    if (this.jdField_f_of_type_Boolean) {
      return true;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("-E2:");
    localStringBuilder.append(paramException.toString());
    this.jdField_b_of_type_JavaLangString = localStringBuilder.toString();
    if (this.mCanceled)
    {
      closeQuietly();
      e();
      return true;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Download task ");
    localStringBuilder.append(this.jdField_d_of_type_Int);
    localStringBuilder.append(" failed - ");
    localStringBuilder.append(paramException);
    FLogger.d("ZHTAG", localStringBuilder.toString());
    long l1;
    if ((this.jdField_d_of_type_Int < this.jdField_a_of_type_Int) && ((!this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.canRetry()) || (!(paramException instanceof SocketTimeoutException))))
    {
      a(this.n);
      l1 = this.mDownloadExceptionSleepTime * 2L;
      this.mDownloadExceptionSleepTime = l1;
      a(l1);
      if (this.mCanceled)
      {
        closeQuietly();
        e();
        return true;
      }
      a(this.O);
      return false;
    }
    if ((this.mIsDetectDownloader) && (Apn.isNetworkConnected()) && (!this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.isRangeNotSupported()) && (!this.jdField_c_of_type_Boolean))
    {
      this.jdField_c_of_type_Int = 44;
      this.jdField_c_of_type_Boolean = true;
      a(this.x);
    }
    else
    {
      if (((paramException instanceof IOException)) && (Apn.isNetworkConnected()) && (this.jdField_b_of_type_JavaLangString.contains("unexpected")))
      {
        a(this.o);
        try
        {
          l1 = this.mDownloadExceptionSleepTime * 2L;
          this.mDownloadExceptionSleepTime = l1;
          Thread.sleep(l1);
        }
        catch (InterruptedException paramException)
        {
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("Interrupted while sleeping to retry - ");
          localStringBuilder.append(paramException);
          FLogger.d("HttpDownloader", localStringBuilder.toString());
        }
        this.jdField_b_of_type_JavaLangString = "";
        if (this.mCanceled)
        {
          closeQuietly();
          e();
          return true;
        }
        a(this.P);
        return false;
      }
      if (!Apn.isNetworkConnected())
      {
        int i1 = this.jdField_g_of_type_Int;
        if (i1 < 2)
        {
          this.jdField_g_of_type_Int = (i1 + 1);
          try
          {
            Thread.sleep(2000L);
          }
          catch (InterruptedException paramException)
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("Interrupted while sleeping to retry - ");
            localStringBuilder.append(paramException);
            FLogger.d("HttpDownloader", localStringBuilder.toString());
          }
          if (this.mCanceled)
          {
            closeQuietly();
            e();
            return true;
          }
          a(this.Q);
          return false;
        }
      }
      a(this.y);
      this.jdField_c_of_type_Int = 42;
      if ((paramException instanceof SocketTimeoutException)) {
        this.jdField_c_of_type_Int = 39;
      } else if ((paramException instanceof ConnectException)) {
        this.jdField_c_of_type_Int = 36;
      } else if ((paramException instanceof ConnectTimeoutException)) {
        this.jdField_c_of_type_Int = 37;
      } else if ((!(paramException instanceof PortUnreachableException)) && (!(paramException instanceof NoRouteToHostException)))
      {
        if ((paramException instanceof UnknownHostException)) {
          this.jdField_c_of_type_Int = 34;
        } else if ((paramException instanceof MalformedURLException)) {
          this.jdField_c_of_type_Int = 35;
        }
      }
      else {
        this.jdField_c_of_type_Int = 40;
      }
    }
    closeQuietly();
    if ((!Apn.isNetworkConnected()) && (!this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.isHidden()))
    {
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.setPausedByNoWifi(true, true);
      DownloadTask.a.pauseTaskFromDownloader(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask);
    }
    else
    {
      setStatus((byte)5);
    }
    fireObserverEvent();
    return true;
  }
  
  void b()
  {
    setStatus((byte)1);
    this.jdField_c_of_type_Int = 0;
    fireObserverEvent();
  }
  
  boolean b(MttResponse paramMttResponse)
  {
    a(this.jdField_i_of_type_JavaLangString);
    paramMttResponse = paramMttResponse.getLocation();
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("location:");
    ((StringBuilder)localObject1).append(paramMttResponse);
    FLogger.d("ZHTAG", ((StringBuilder)localObject1).toString());
    if (!TextUtils.isEmpty(paramMttResponse))
    {
      localObject1 = this.mMttRequest.getUrl();
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Download Task request old url:");
      ((StringBuilder)localObject2).append((String)localObject1);
      FLogger.d("HttpDownloader", ((StringBuilder)localObject2).toString());
      localObject2 = UrlUtils.prepareUrl(DLQBUrlUtils.resolveBase((String)localObject1, paramMttResponse));
      localObject1 = UrlUtils.prepareUrl((String)localObject1);
      if ((!TextUtils.isEmpty((CharSequence)localObject2)) && (!((String)localObject2).equalsIgnoreCase((String)localObject1))) {
        this.mMttRequest.setUrl((String)localObject2);
      } else {
        this.mMttRequest.setUrl(paramMttResponse);
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("Download Task 302,location:");
      ((StringBuilder)localObject1).append(paramMttResponse);
      FLogger.d("HttpDownloader", ((StringBuilder)localObject1).toString());
      paramMttResponse = new StringBuilder();
      paramMttResponse.append("Download Task request new url:");
      paramMttResponse.append(this.mMttRequest.getUrl());
      FLogger.d("HttpDownloader", paramMttResponse.toString());
      this.jdField_a_of_type_JavaLangString = this.mMttRequest.getUrl();
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.setDownloaderDownloadDataUrl(this.jdField_a_of_type_JavaLangString);
    }
    this.jdField_h_of_type_Int += 1;
    return false;
  }
  
  void c()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.X);
    localStringBuilder.append(",");
    localStringBuilder.append(this.jdField_d_of_type_Long);
    this.X = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.jdField_b_of_type_Int);
    localStringBuilder.append(" ");
    localStringBuilder.append(this.X);
    FLogger.d("HttpDownloader", localStringBuilder.toString());
  }
  
  boolean c(MttResponse paramMttResponse)
  {
    int i1 = paramMttResponse.getStatusCode().intValue();
    long l1;
    if ((i1 == 416) && (!this.mIsDetectDownloader))
    {
      if (!TextUtils.isEmpty(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getDownloaderDownloadDataUrl()))
      {
        closeQuietly();
        this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.setDownloaderDownloadDataUrl(null);
        this.jdField_c_of_type_Int = 18;
        e();
        return true;
      }
      l1 = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getDownloadedSize();
      if ((l1 > 0L) && (this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getCurrentSectionEndPos() >= l1))
      {
        this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.changeCurrentSectionEndPos(l1 - 1L);
        a(this.I);
        return false;
      }
      if (this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.isDownloadFinish())
      {
        setStatus((byte)3);
        this.jdField_c_of_type_Int = 0;
      }
      else
      {
        paramMttResponse = new StringBuilder();
        paramMttResponse.append("status code:");
        paramMttResponse.append(i1);
        FLogger.d("HttpDownloader", paramMttResponse.toString());
        this.jdField_c_of_type_Int = 6;
        setStatus((byte)5);
      }
      a(this.D);
      paramMttResponse = new StringBuilder();
      paramMttResponse.append("-");
      paramMttResponse.append(this.W);
      a(paramMttResponse.toString());
    }
    else if (((i1 == 403) || (i1 == 416) || (i1 == 406)) && (this.mIsDetectDownloader))
    {
      this.jdField_c_of_type_Int = 10;
      setStatus((byte)5);
      a(this.u);
    }
    else
    {
      if (i1 == 202)
      {
        a(this.J);
        return false;
      }
      if ((this.jdField_d_of_type_Int < this.jdField_a_of_type_Int) && (i1 == 503))
      {
        a(this.l);
        long l2 = paramMttResponse.getRetryAfter();
        l1 = l2;
        if (l2 <= 0L)
        {
          l1 = this.mDownloadExceptionSleepTime * 2L;
          this.mDownloadExceptionSleepTime = l1;
        }
        a(l1);
        if (this.mCanceled)
        {
          closeQuietly();
          e();
          return true;
        }
        a(this.K);
        return false;
      }
      if ((this.jdField_d_of_type_Int < this.jdField_a_of_type_Int) && ((i1 == 408) || (i1 == 504) || (i1 == 502) || (i1 == 408)))
      {
        a(this.m);
        l1 = this.mDownloadExceptionSleepTime * 2L;
        this.mDownloadExceptionSleepTime = l1;
        a(l1);
        if (this.mCanceled)
        {
          closeQuietly();
          e();
          return true;
        }
        FLogger.d("HttpDownloader", "reset read timeout to 300000");
        a(this.L);
        return false;
      }
      if ((this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getDownloadedSize() <= 0L) && (!this.jdField_e_of_type_Boolean) && (i1 != 410))
      {
        this.jdField_e_of_type_Boolean = true;
        a(this.M);
        return false;
      }
      paramMttResponse = new StringBuilder();
      paramMttResponse.append("status code:");
      paramMttResponse.append(i1);
      FLogger.d("HttpDownloader", paramMttResponse.toString());
      this.jdField_c_of_type_Int = 43;
      setStatus((byte)5);
      a(this.v);
      paramMttResponse = new StringBuilder();
      paramMttResponse.append("(");
      paramMttResponse.append(i1);
      paramMttResponse.append(")");
      a(paramMttResponse.toString());
    }
    closeQuietly();
    fireObserverEvent();
    return true;
  }
  
  public void cancel()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Cancel task.");
    localStringBuilder.append(Log.getStackTraceString(new Throwable()));
    FLogger.d("HttpDownloader", localStringBuilder.toString());
    if (!this.mCanceled)
    {
      FLogger.d("HttpDownloader", "Cancel task implemented.");
      this.mCanceled = true;
      super.cancel();
      if (!TextUtils.isEmpty(this.X))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.X);
        localStringBuilder.append(",");
        localStringBuilder.append(this.jdField_d_of_type_Long);
        localStringBuilder.append("C");
        this.X = localStringBuilder.toString();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.jdField_b_of_type_Int);
        localStringBuilder.append(" ");
        localStringBuilder.append(this.X);
        FLogger.d("HttpDownloader", localStringBuilder.toString());
        this.X = "";
      }
      if ((getStatus() != 1) && (getStatus() != 2))
      {
        d();
        return;
      }
      this.jdField_f_of_type_Boolean = true;
      d();
      if ((DLDeviceUtils.getSdkVersion() < 19) && (!this.jdField_g_of_type_Boolean))
      {
        closeQuietly();
        return;
      }
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          HttpDownloader.this.closeQuietly();
        }
      });
    }
  }
  
  void d()
  {
    a(this.t);
    this.jdField_b_of_type_JavaLangString = "CancleByUser";
    this.jdField_c_of_type_Int = 0;
    setStatus((byte)6);
    fireObserverEvent();
  }
  
  public void doRun()
  {
    try
    {
      runDownload();
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  void e()
  {
    a(this.E);
    setStatus((byte)6);
    fireObserverEvent();
  }
  
  public void fireObserverEvent()
  {
    if (!this.jdField_f_of_type_Boolean)
    {
      if (getStatus() == 5) {
        f();
      }
      this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.downloaderFireEvent(this);
    }
  }
  
  public long getRemainingLen()
  {
    return this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getCurrentSectionEndPos() - this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getSectionNextDownloadPos() + 1L;
  }
  
  protected void init()
  {
    this.jdField_a_of_type_Boolean = false;
    this.jdField_c_of_type_Int = 0;
    this.mCanceled = false;
  }
  
  public boolean isNetWorkError()
  {
    int i1 = this.jdField_c_of_type_Int;
    return (i1 == 3) || (i1 == 42) || (i1 == 39) || (i1 == 36) || (i1 == 37) || (i1 == 40) || (i1 == 34) || (i1 == 35);
  }
  
  protected boolean makeSureSectionLength(MttResponse paramMttResponse)
  {
    this.X = "";
    this.jdField_c_of_type_Long = 0L;
    this.jdField_b_of_type_Long = 0L;
    this.jdField_a_of_type_Long = 0L;
    this.jdField_d_of_type_Long = 0L;
    paramMttResponse = paramMttResponse.getContentRange();
    if (paramMttResponse != null)
    {
      paramMttResponse = DownloadTask.CONTENT_RANGE_PATTERN.matcher(paramMttResponse);
      long l1;
      if (paramMttResponse.find())
      {
        this.jdField_a_of_type_Long = StringUtils.parseLong(paramMttResponse.group(1), 0L);
        this.jdField_b_of_type_Long = StringUtils.parseLong(paramMttResponse.group(2), 0L);
        this.jdField_c_of_type_Long = StringUtils.parseLong(paramMttResponse.group(3), 0L);
        l1 = this.jdField_c_of_type_Long;
        if ((l1 != 0L) && (l1 != this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getTotalSize())) {
          this.X = "FUCK";
        }
        paramMttResponse = new StringBuilder();
        paramMttResponse.append(this.X);
        paramMttResponse.append(this.jdField_a_of_type_Long);
        paramMttResponse.append(",");
        paramMttResponse.append(this.jdField_b_of_type_Long);
        paramMttResponse.append(",");
        paramMttResponse.append(this.jdField_c_of_type_Long);
        paramMttResponse.append(",");
        paramMttResponse.append(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.getTotalSize());
        this.X = paramMttResponse.toString();
      }
      else
      {
        this.X = "NC";
      }
      if ((this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getCurrentSectionEndPos() != -1L) && (!this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask.isRangeNotSupported()) && (!this.jdField_c_of_type_Boolean))
      {
        l1 = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getCurrentSectionEndPos() - this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadSection.getSectionNextDownloadPos();
        long l2 = this.jdField_b_of_type_Long - this.jdField_a_of_type_Long;
        if ((l1 > 0L) && (l2 > 0L) && (l1 != l2)) {
          return false;
        }
      }
    }
    else
    {
      this.X = "ND";
    }
    return true;
  }
  
  public void recvDataLen(int paramInt)
  {
    long l1 = this.jdField_d_of_type_Long;
    long l2 = paramInt;
    this.jdField_d_of_type_Long = (l1 + l2);
    DownloadTask localDownloadTask = this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask;
    localDownloadTask.addDownloadedSize(localDownloadTask.getDownloadTaskId(), l2);
  }
  
  protected void runDownload()
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.provideAs(TypeTransformer.java:780)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.e1expr(TypeTransformer.java:496)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:713)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:698)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s2stmt(TypeTransformer.java:820)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:843)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void start()
  {
    DownloadThreadPolicy.a(this.jdField_a_of_type_ComTencentMttBrowserDownloadEngineDownloadTask, new Runnable()
    {
      public void run()
      {
        HttpDownloader.this.doRun();
      }
    });
  }
  
  public void superFireObserverEvent()
  {
    super.fireObserverEvent(this.mStatus);
  }
  
  public static abstract interface GetMttRequesterInterface
  {
    public abstract MttRequestBase getMttRequestBase();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\HttpDownloader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */