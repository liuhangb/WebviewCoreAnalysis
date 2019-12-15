package com.tencent.common.http;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.UrlUtils;
import com.tencent.common.utils.gzip.GZipInputStream;
import com.tencent.mtt.network.QBUrl;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ssl.BrowserCompatHostnameVerifier;
import org.apache.http.util.ByteArrayBuffer;

public class HttpRequesterBase
  extends Requester
{
  static String SET_COOKIE = "Set-Cookie";
  static String SET_COOKIE2 = "Set-Cookie2";
  private static final String TAG = "HttpURLConnection";
  protected MttInputStream mErrorStream;
  protected HttpURLConnection mHttpConnection;
  private MttOutputStream mOutputStream;
  protected URL mUrl = null;
  
  private MttResponse createResponse(HttpURLConnection paramHttpURLConnection)
    throws Exception
  {
    MttResponse localMttResponse = new MttResponse();
    parseResponseHeaders(paramHttpURLConnection, localMttResponse);
    Object localObject4 = null;
    Object localObject3;
    int i;
    try
    {
      localObject3 = paramHttpURLConnection.getInputStream();
      i = 0;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      localObject3 = null;
      i = 1;
    }
    Object localObject1;
    Object localObject2;
    if (i == 0)
    {
      localObject1 = localObject4;
      if (paramHttpURLConnection.getResponseCode() < 400) {}
    }
    else
    {
      try
      {
        localObject1 = paramHttpURLConnection.getErrorStream();
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        localObject2 = localObject4;
      }
    }
    if (i != 0) {
      localObject3 = localObject2;
    }
    if (localObject3 != null)
    {
      if (this.mMttRequest.getRequestType() != 104) {
        paramHttpURLConnection = decodeInputStream(paramHttpURLConnection, (InputStream)localObject3);
      } else {
        paramHttpURLConnection = (HttpURLConnection)localObject3;
      }
      this.mInputStream = new MttInputStream(paramHttpURLConnection, true);
      this.mInputStream.setExceptionHandler(this.mExceptionHandler);
      localMttResponse.setInputStream(this.mInputStream);
      if (i != 0) {}
    }
    else
    {
      paramHttpURLConnection = (HttpURLConnection)localObject2;
    }
    if (paramHttpURLConnection != null)
    {
      this.mErrorStream = new MttInputStream(paramHttpURLConnection, true);
      localMttResponse.setErrorStream(this.mErrorStream);
    }
    setCookie();
    return localMttResponse;
  }
  
  private void fillHeader(URLConnection paramURLConnection, MttRequestBase paramMttRequestBase)
  {
    fillUserAgent();
    if (paramMttRequestBase.getRequestType() == 104) {
      paramMttRequestBase.addHeader("Accept-Encoding", "identity");
    }
    paramMttRequestBase.addHeaders(this.mIsRemoveHeader, this.mCookieEnable, this.Q_DEBUG);
    paramMttRequestBase.doHeaderIntercept();
    paramMttRequestBase = paramMttRequestBase.getHeaders().entrySet().iterator();
    while (paramMttRequestBase.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMttRequestBase.next();
      paramURLConnection.setRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue());
    }
  }
  
  private void fillRequestProperty(HttpURLConnection paramHttpURLConnection, MttRequestBase paramMttRequestBase)
  {
    paramMttRequestBase = paramMttRequestBase.getRequestProperty();
    if (paramMttRequestBase != null)
    {
      if (paramMttRequestBase.size() < 1) {
        return;
      }
      paramMttRequestBase = paramMttRequestBase.entrySet().iterator();
      while (paramMttRequestBase.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMttRequestBase.next();
        paramHttpURLConnection.setRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue());
      }
      return;
    }
  }
  
  private int performRequest(MttRequestBase paramMttRequestBase)
    throws Exception
  {
    paramMttRequestBase.preparePerform();
    setApn(Apn.getApnInfo().getApnTypeS());
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("performRequest... retryTimes:");
    localStringBuilder.append(this.mRetryTimes);
    localStringBuilder.append(", queen:");
    boolean bool;
    if (paramMttRequestBase.getQueenConfig() != null) {
      bool = true;
    } else {
      bool = false;
    }
    localStringBuilder.append(bool);
    localStringBuilder.append(" [");
    localStringBuilder.append(paramMttRequestBase.getExecuteUrl());
    localStringBuilder.append("]");
    FLogger.d("HttpURLConnection", localStringBuilder.toString());
    if (this.mRetryTimes > 0) {
      close();
    }
    this.mHttpConnection = createConnection(paramMttRequestBase);
    paramMttRequestBase.mNetworkStatus = 2;
    fillHeader(this.mHttpConnection, paramMttRequestBase);
    fillRequestProperty(this.mHttpConnection, paramMttRequestBase);
    this.mOutputStream = fillPostBody(this.mHttpConnection, paramMttRequestBase);
    paramMttRequestBase.mNetworkStatus = 3;
    this.mMttResponse = createResponse(this.mHttpConnection);
    return 1;
  }
  
  protected static void trustAllHttpsCertificates(MttRequestBase paramMttRequestBase)
    throws Exception
  {
    if (paramMttRequestBase.getRequestType() == 104)
    {
      paramMttRequestBase = new TrustManager[1];
      paramMttRequestBase[0 = new miTM() {};
    }
    else
    {
      paramMttRequestBase = null;
    }
    SSLContext localSSLContext = SSLContext.getInstance("TLS");
    localSSLContext.init(null, paramMttRequestBase, null);
    HttpsURLConnection.setDefaultSSLSocketFactory(localSSLContext.getSocketFactory());
  }
  
  public void abort()
  {
    this.mIsCanceled = true;
    close();
  }
  
  public void close()
  {
    if (this.mInputStream != null)
    {
      try
      {
        this.mInputStream.close();
      }
      catch (Throwable localThrowable1)
      {
        localThrowable1.printStackTrace();
      }
      this.mInputStream = null;
    }
    MttInputStream localMttInputStream = this.mErrorStream;
    if (localMttInputStream != null)
    {
      try
      {
        localMttInputStream.close();
      }
      catch (Throwable localThrowable2)
      {
        localThrowable2.printStackTrace();
      }
      this.mErrorStream = null;
    }
    MttOutputStream localMttOutputStream = this.mOutputStream;
    if (localMttOutputStream != null)
    {
      try
      {
        localMttOutputStream.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
      this.mOutputStream = null;
    }
    if ((this.mMttRequest != null) && (this.mMttRequest.isPostEnable())) {
      this.mMttRequest.getPostData().cancel();
    }
    HttpURLConnection localHttpURLConnection = this.mHttpConnection;
    if (localHttpURLConnection != null)
    {
      try
      {
        localHttpURLConnection.disconnect();
      }
      catch (Throwable localThrowable4)
      {
        localThrowable4.printStackTrace();
      }
      this.mHttpConnection = null;
    }
    if (this.mMttRequest != null)
    {
      Object localObject = this.mInputStream;
      int j = 0;
      int i;
      if (localObject != null) {
        i = this.mInputStream.getFlow();
      } else {
        i = 0;
      }
      localObject = this.mOutputStream;
      if (localObject != null) {
        j = ((MttOutputStream)localObject).getFlow();
      }
      this.mMttRequest.setDownFlow(i + j);
      localObject = RequesterFactory.getRequestObsever();
      if (localObject != null) {
        ((RequesterFactory.IRequestObsever)localObject).onRequestComplete(this.mMttRequest);
      }
    }
  }
  
  protected void configHttpsDefaultSetting(MttRequestBase paramMttRequestBase)
    throws Exception
  {
    SSLContext localSSLContext;
    if (paramMttRequestBase.getHttpsVerStrategy() == 0) {
      localSSLContext = SSLContext.getInstance("TLS");
    } else if (paramMttRequestBase.getHttpsVerStrategy() == 1)
    {
      if (Integer.parseInt(Build.VERSION.SDK) >= 16) {
        localSSLContext = SSLContext.getInstance("TLSv1.2", "AndroidOpenSSL");
      } else {
        localSSLContext = SSLContext.getInstance("TLSv1", "AndroidOpenSSL");
      }
    }
    else {
      localSSLContext = null;
    }
    if (localSSLContext != null)
    {
      int i = paramMttRequestBase.getTrustCertificatesType();
      TrustManager[] arrayOfTrustManager;
      if (((i == 0) && (paramMttRequestBase.getRequestType() == 104)) || (i == 1))
      {
        arrayOfTrustManager = new TrustManager[1];
        arrayOfTrustManager[0 = new miTM() {};
      }
      else
      {
        arrayOfTrustManager = null;
      }
      localSSLContext.init(null, arrayOfTrustManager, null);
      if (paramMttRequestBase.getIsDisableSSLV3()) {
        paramMttRequestBase = new NoSSLv3Factory(localSSLContext.getSocketFactory());
      } else {
        paramMttRequestBase = localSSLContext.getSocketFactory();
      }
      HttpsURLConnection.setDefaultSSLSocketFactory(paramMttRequestBase);
    }
  }
  
  protected HttpURLConnection createConnection(MttRequestBase paramMttRequestBase)
    throws Exception
  {
    Object localObject1 = paramMttRequestBase.getExecuteUrl();
    boolean bool = UrlUtils.isHttpsUrl((String)localObject1);
    if (bool) {
      configHttpsDefaultSetting(paramMttRequestBase);
    }
    this.mUrl = UrlUtils.toURL((String)localObject1);
    localObject1 = getProxy();
    Object localObject2 = new QBUrl(this.mUrl).setQueenProxyEnable(paramMttRequestBase.isQueenProxyEnable()).setTag(MttRequestBase.getRequestTypeName(paramMttRequestBase.getRequestType()));
    if (localObject1 != null) {
      localObject1 = (HttpURLConnection)((QBUrl)localObject2).openConnection((Proxy)localObject1);
    } else {
      localObject1 = (HttpURLConnection)((QBUrl)localObject2).openConnection();
    }
    if (this.mInterceptor != null) {
      this.mInterceptor.onIntercept((HttpURLConnection)localObject1);
    }
    ((HttpURLConnection)localObject1).setRequestMethod(paramMttRequestBase.getMethodName());
    ((HttpURLConnection)localObject1).setUseCaches(paramMttRequestBase.isUseCaches());
    ((HttpURLConnection)localObject1).setInstanceFollowRedirects(paramMttRequestBase.isInstanceFollowRedirects());
    int i;
    if (paramMttRequestBase.mConnectTimeout > 0) {
      i = paramMttRequestBase.mConnectTimeout;
    } else if (this.mConnectTimeout > 0) {
      i = this.mConnectTimeout;
    } else {
      i = getConnectTimeoutByApnType();
    }
    ((HttpURLConnection)localObject1).setConnectTimeout(i);
    if (paramMttRequestBase.mReadTimeout > 0) {
      i = paramMttRequestBase.mReadTimeout;
    } else if (this.mReadTimeout > 0) {
      i = this.mReadTimeout;
    } else {
      i = getReadTimeoutByApnType();
    }
    ((HttpURLConnection)localObject1).setReadTimeout(i);
    ((HttpURLConnection)localObject1).setDoInput(true);
    if (paramMttRequestBase.getMethodName().equalsIgnoreCase("POST")) {
      ((HttpURLConnection)localObject1).setDoOutput(true);
    }
    if ((bool) && ((localObject1 instanceof HttpsURLConnection)))
    {
      localObject2 = (HttpsURLConnection)localObject1;
      if (paramMttRequestBase.getHostVerifier() != null)
      {
        ((HttpsURLConnection)localObject2).setHostnameVerifier(paramMttRequestBase.getHostVerifier());
        return (HttpURLConnection)localObject1;
      }
      ((HttpsURLConnection)localObject2).setHostnameVerifier(new BrowserCompatHostnameVerifier());
    }
    return (HttpURLConnection)localObject1;
  }
  
  InputStream decodeInputStream(HttpURLConnection paramHttpURLConnection, InputStream paramInputStream)
    throws IOException
  {
    paramHttpURLConnection = paramHttpURLConnection.getContentEncoding();
    if ((paramHttpURLConnection != null) && (paramHttpURLConnection.toLowerCase().indexOf("gzip") != -1)) {
      try
      {
        paramHttpURLConnection = new GZIPInputStream(paramInputStream);
        return paramHttpURLConnection;
      }
      catch (Exception paramHttpURLConnection)
      {
        paramHttpURLConnection.printStackTrace();
      }
    } else if ((paramHttpURLConnection != null) && (paramHttpURLConnection.toLowerCase().indexOf("deflate") != -1)) {
      return new GZipInputStream(paramInputStream, 0, false);
    }
    return paramInputStream;
  }
  
  public MttResponse execute(MttRequestBase paramMttRequestBase)
    throws Exception
  {
    if (paramMttRequestBase == null) {
      return null;
    }
    long l = System.currentTimeMillis();
    this.mMttRequest = paramMttRequestBase;
    paramMttRequestBase.setProxyDisable(this.mDisableProxy);
    paramMttRequestBase.mNetworkStatus = 1;
    while ((!this.mIsCanceled) && (this.mRetryTimes <= 3))
    {
      int i;
      try
      {
        i = performRequest(paramMttRequestBase);
      }
      catch (Throwable localThrowable)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Exception: ");
        localStringBuilder.append(localThrowable.toString());
        FLogger.d("HttpURLConnection", localStringBuilder.toString());
        i = handleException(paramMttRequestBase, localThrowable);
        if (i == 1) {
          break label143;
        }
      }
      if (i != 1)
      {
        if (i == 3) {
          Thread.sleep(1000L);
        }
        if (i != 4)
        {
          this.mRetryTimes += 1;
          continue;
          label143:
          throw localThrowable;
        }
      }
    }
    paramMttRequestBase.mNetworkStatus = 4;
    paramMttRequestBase = new StringBuilder();
    paramMttRequestBase.append("execute cost: ");
    paramMttRequestBase.append(System.currentTimeMillis() - l);
    paramMttRequestBase.append(" [");
    paramMttRequestBase.append(this.mMttRequest.getUrl());
    paramMttRequestBase.append("]");
    FLogger.d("HttpURLConnection", paramMttRequestBase.toString());
    return this.mMttResponse;
  }
  
  protected MttOutputStream fillPostBody(HttpURLConnection paramHttpURLConnection, MttRequestBase paramMttRequestBase)
    throws IOException, InterruptedException
  {
    boolean bool = paramMttRequestBase.isPostEnable();
    MttOutputStream localMttOutputStream = null;
    if (!bool) {
      return null;
    }
    IPostDataBuf localIPostDataBuf = paramMttRequestBase.getPostData();
    String str = localIPostDataBuf.getBoundary();
    Object localObject1;
    if (TextUtils.isEmpty(paramHttpURLConnection.getRequestProperty("Content-Type"))) {
      if ((!localIPostDataBuf.isUploadFile()) && (TextUtils.isEmpty(str)))
      {
        paramHttpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
      }
      else
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("multipart/form-data; boundary=");
        ((StringBuilder)localObject1).append(str);
        paramHttpURLConnection.setRequestProperty("Content-Type", ((StringBuilder)localObject1).toString());
      }
    }
    paramHttpURLConnection.setRequestProperty("Content-Length", String.valueOf(localIPostDataBuf.getLen()));
    if (paramMttRequestBase.getIsWupRequest()) {
      bool = paramMttRequestBase.getUseWapProxy();
    } else if (Apn.getApnType() == 2) {
      bool = true;
    } else {
      bool = false;
    }
    int i;
    if (bool)
    {
      FLogger.d("HttpURLConnection", "this is wap request");
      i = localIPostDataBuf.getLen();
      Object localObject2 = paramHttpURLConnection.getRequestProperties();
      localObject1 = new ByteArrayBuffer(0);
      Object localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append(paramHttpURLConnection.getRequestMethod());
      ((StringBuilder)localObject3).append(" / HTTP/1.1\r\n");
      localObject3 = ((StringBuilder)localObject3).toString();
      ((ByteArrayBuffer)localObject1).append(((String)localObject3).getBytes(), 0, ((String)localObject3).length());
      localObject2 = ((Map)localObject2).entrySet().iterator();
      while (((Iterator)localObject2).hasNext())
      {
        Object localObject4 = (Map.Entry)((Iterator)localObject2).next();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append((String)((Map.Entry)localObject4).getKey());
        ((StringBuilder)localObject3).append(": ");
        localObject3 = ((StringBuilder)localObject3).toString();
        localObject4 = ((List)((Map.Entry)localObject4).getValue()).toString();
        int j = ((String)localObject4).length();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append((String)localObject3);
        localStringBuilder.append(((String)localObject4).subSequence(1, j - 1));
        localStringBuilder.append("\r\n");
        localObject3 = localStringBuilder.toString().getBytes();
        ((ByteArrayBuffer)localObject1).append((byte[])localObject3, 0, localObject3.length);
      }
      ((ByteArrayBuffer)localObject1).append("\r\n".getBytes(), 0, 2);
      localObject2 = localIPostDataBuf.toByteArray();
      if ((localObject2 != null) && (localObject2.length > 0)) {
        ((ByteArrayBuffer)localObject1).append((byte[])localObject2, 0, localObject2.length);
      }
      localIPostDataBuf.setPostData(((ByteArrayBuffer)localObject1).toByteArray());
    }
    else
    {
      i = 0;
    }
    paramHttpURLConnection.setRequestProperty("Content-Length", String.valueOf(localIPostDataBuf.getLen() - i));
    if ((localIPostDataBuf.isUploadFile()) || (!TextUtils.isEmpty(str))) {
      paramHttpURLConnection.setFixedLengthStreamingMode(localIPostDataBuf.getLen());
    }
    paramHttpURLConnection.connect();
    if (localIPostDataBuf.hasValidData())
    {
      localMttOutputStream = new MttOutputStream(paramHttpURLConnection.getOutputStream());
      if (paramMttRequestBase.getIsBackGroudRequest())
      {
        localIPostDataBuf.sendTo(localMttOutputStream, true);
        return localMttOutputStream;
      }
      localIPostDataBuf.sendTo(localMttOutputStream);
    }
    return localMttOutputStream;
  }
  
  protected void fillUserAgent()
  {
    this.mMttRequest.addHeader("User-Agent", this.mMttRequest.getUserAgent());
  }
  
  protected Proxy getProxy()
  {
    if (this.mMttRequest.getIsWupRequest()) {
      return Proxy.NO_PROXY;
    }
    if (this.mMttRequest.isProxyDisable()) {
      return Proxy.NO_PROXY;
    }
    return null;
  }
  
  public MttResponse getResponse()
  {
    return this.mMttResponse;
  }
  
  protected String getTag()
  {
    return "HttpURLConnection";
  }
  
  protected int handleException(MttRequestBase paramMttRequestBase, Throwable paramThrowable)
  {
    return 1;
  }
  
  protected void parseResponseHeaders(HttpURLConnection paramHttpURLConnection, MttResponse paramMttResponse)
    throws Exception
  {
    if (paramHttpURLConnection == null) {
      return;
    }
    paramMttResponse.setFlow(paramHttpURLConnection.getHeaderFields());
    paramMttResponse.setStatusCode(Integer.valueOf(paramHttpURLConnection.getResponseCode()));
    Long localLong = Long.getLong(paramHttpURLConnection.getHeaderField("Retry-After"));
    long l2 = 0L;
    long l1;
    if (localLong != null) {
      l1 = localLong.longValue();
    } else {
      l1 = 0L;
    }
    paramMttResponse.setRetryAfter(l1);
    paramMttResponse.setLocation(paramHttpURLConnection.getHeaderField("Location"));
    paramMttResponse.setServer(paramHttpURLConnection.getHeaderField("Server"));
    if ((paramHttpURLConnection.getHeaderField("Set-Cookie") != null) || (paramHttpURLConnection.getHeaderField("Set-Cookie2") != null)) {
      paramMttResponse.setCookies(paramHttpURLConnection.getHeaderFields());
    }
    try
    {
      l1 = Long.parseLong(paramHttpURLConnection.getHeaderField("Content-Length"));
      paramMttResponse.setContentLength(l1);
      paramMttResponse.setContentEncoding(paramHttpURLConnection.getHeaderField("Content-Encoding"));
      paramMttResponse.setCharset(paramHttpURLConnection.getHeaderField("Charset"));
      paramMttResponse.setTransferEncoding(paramHttpURLConnection.getHeaderField("Transfer-Encoding"));
      paramMttResponse.setLastModify(paramHttpURLConnection.getHeaderField("Last-Modified"));
      paramMttResponse.setByteRanges(paramHttpURLConnection.getHeaderField("Byte-Ranges"));
      paramMttResponse.setCacheControl(paramHttpURLConnection.getHeaderField("Cache-Control"));
      paramMttResponse.setConnection(paramHttpURLConnection.getHeaderField("Connection"));
      paramMttResponse.setContentRange(paramHttpURLConnection.getHeaderField("Content-Range"));
      paramMttResponse.setContentDisposition(paramHttpURLConnection.getHeaderField("Content-Disposition"));
      paramMttResponse.setETag(paramHttpURLConnection.getHeaderField("ETag"));
      paramMttResponse.setQSZip(paramHttpURLConnection.getHeaderField("QQ-S-ZIP"));
      paramMttResponse.setQEncrypt(paramHttpURLConnection.getHeaderField("QQ-S-Encrypt"));
      paramMttResponse.setQToken(paramHttpURLConnection.getHeaderField("tk"));
      paramMttResponse.setTokenExpireSpan(paramHttpURLConnection.getHeaderField("maxage"));
      paramMttResponse.setWupEnvironment(paramHttpURLConnection.getHeaderField("env"));
      paramMttResponse.setContentType(parseContentType(paramHttpURLConnection.getHeaderField("Content-Type"), paramHttpURLConnection.getURL().toString()));
      paramMttResponse.setRspHeaderMap(paramHttpURLConnection.getHeaderFields());
      paramMttResponse.setResponseMessage(paramHttpURLConnection.getResponseMessage());
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        l1 = l2;
      }
    }
  }
  
  protected void setCookie()
  {
    if (!this.mCookieEnable) {
      return;
    }
    Map localMap = this.mHttpConnection.getHeaderFields();
    if (localMap == null) {
      return;
    }
    Iterator localIterator = localMap.keySet().iterator();
    while (localIterator.hasNext())
    {
      Object localObject = (String)localIterator.next();
      if ((localObject != null) && ((((String)localObject).equalsIgnoreCase(SET_COOKIE)) || (((String)localObject).equalsIgnoreCase(SET_COOKIE2))))
      {
        localObject = ((List)localMap.get(localObject)).iterator();
        while (((Iterator)localObject).hasNext())
        {
          String str = (String)((Iterator)localObject).next();
          this.mMttRequest.setCookie(str);
        }
      }
    }
  }
  
  public static class miTM
    implements TrustManager, X509TrustManager
  {
    public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
      throws CertificateException
    {}
    
    public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
      throws CertificateException
    {}
    
    public X509Certificate[] getAcceptedIssuers()
    {
      return null;
    }
    
    public boolean isClientTrusted(X509Certificate[] paramArrayOfX509Certificate)
    {
      return true;
    }
    
    public boolean isServerTrusted(X509Certificate[] paramArrayOfX509Certificate)
    {
      return true;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\HttpRequesterBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */