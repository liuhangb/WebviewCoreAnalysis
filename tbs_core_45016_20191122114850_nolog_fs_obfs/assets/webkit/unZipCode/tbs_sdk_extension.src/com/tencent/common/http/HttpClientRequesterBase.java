package com.tencent.common.http;

import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.UrlUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.Header;
import org.apache.http.HeaderElement;
import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRoute;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.scheme.SocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.HttpEntityWrapper;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;

public class HttpClientRequesterBase
  extends Requester
{
  private static QBHttpClient jdField_a_of_type_ComTencentCommonHttpQBHttpClient;
  private static ThreadSafeClientConnManager jdField_a_of_type_OrgApacheHttpImplConnTsccmThreadSafeClientConnManager;
  private InputStream jdField_a_of_type_JavaIoInputStream;
  protected HttpRequestBase mHttpRequest;
  protected HttpResponse mHttpResponse;
  protected URL mUrl;
  
  static
  {
    BasicHttpParams localBasicHttpParams = new BasicHttpParams();
    HttpClientParams.setRedirecting(localBasicHttpParams, false);
    HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 20000);
    HttpConnectionParams.setSoTimeout(localBasicHttpParams, 30000);
    HttpConnectionParams.setSocketBufferSize(localBasicHttpParams, 8192);
    HttpConnectionParams.setTcpNoDelay(localBasicHttpParams, true);
    ConnManagerParams.setMaxTotalConnections(localBasicHttpParams, 200);
    Object localObject = new ConnPerRouteBean(20);
    ((ConnPerRouteBean)localObject).setMaxForRoute(new HttpRoute(new HttpHost("localhost", 80)), 5);
    ConnManagerParams.setMaxConnectionsPerRoute(localBasicHttpParams, (ConnPerRoute)localObject);
    SchemeRegistry localSchemeRegistry = new SchemeRegistry();
    localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
    try
    {
      localObject = KeyStore.getInstance(KeyStore.getDefaultType());
      ((KeyStore)localObject).load(null, null);
      localObject = new b((KeyStore)localObject);
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    localObject = org.apache.http.conn.ssl.SSLSocketFactory.getSocketFactory();
    ((org.apache.http.conn.ssl.SSLSocketFactory)localObject).setHostnameVerifier(org.apache.http.conn.ssl.SSLSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
    localSchemeRegistry.register(new Scheme("https", (SocketFactory)localObject, 443));
    jdField_a_of_type_OrgApacheHttpImplConnTsccmThreadSafeClientConnManager = new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry);
    jdField_a_of_type_ComTencentCommonHttpQBHttpClient = new QBHttpClient(jdField_a_of_type_OrgApacheHttpImplConnTsccmThreadSafeClientConnManager, localBasicHttpParams);
    jdField_a_of_type_ComTencentCommonHttpQBHttpClient.setCookieStore(null);
    jdField_a_of_type_ComTencentCommonHttpQBHttpClient.addResponseInterceptor(new HttpResponseInterceptor()
    {
      public void process(HttpResponse paramAnonymousHttpResponse, HttpContext paramAnonymousHttpContext)
        throws HttpException, IOException
      {
        try
        {
          paramAnonymousHttpContext = paramAnonymousHttpResponse.getEntity().getContentEncoding();
          if (paramAnonymousHttpContext != null)
          {
            paramAnonymousHttpContext = paramAnonymousHttpContext.getElements();
            int i = 0;
            while (i < paramAnonymousHttpContext.length)
            {
              if (paramAnonymousHttpContext[i].getName().equalsIgnoreCase("gzip"))
              {
                paramAnonymousHttpResponse.setEntity(new HttpClientRequesterBase.a(paramAnonymousHttpResponse.getEntity()));
                return;
              }
              i += 1;
            }
          }
          return;
        }
        catch (Throwable paramAnonymousHttpResponse) {}
      }
    });
  }
  
  private int a(MttRequestBase paramMttRequestBase)
    throws Exception
  {
    paramMttRequestBase.preparePerform();
    Object localObject1 = Apn.getApnInfo();
    QueenConfig.a(paramMttRequestBase, this.mQueenRequestTimes, true);
    if ((QueenConfig.isQueenEnable()) && (this.mQueenRequestTimes <= 2)) {
      paramMttRequestBase.setIsQueenFlow(true);
    }
    if (this.mRetryTimes > 0) {
      close();
    }
    setApn(((Apn.ApnInfo)localObject1).getApnTypeS());
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("M_APN_TYPE == ");
    ((StringBuilder)localObject2).append(((Apn.ApnInfo)localObject1).getApnTypeS());
    ((StringBuilder)localObject2).toString();
    localObject1 = paramMttRequestBase.getExecuteUrl();
    this.mUrl = UrlUtils.toURL((String)localObject1);
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("Url : ");
    ((StringBuilder)localObject2).append(this.mUrl);
    ((StringBuilder)localObject2).toString();
    localObject1 = a((String)localObject1);
    jdField_a_of_type_ComTencentCommonHttpQBHttpClient.getParams().setParameter("http.route.default-proxy", localObject1);
    if (paramMttRequestBase.getMethod() == 0) {
      this.mHttpRequest = new HttpGet(this.mUrl.toString());
    } else {
      this.mHttpRequest = new HttpPost(this.mUrl.toString());
    }
    fillUserAgent();
    a();
    b();
    c();
    Object localObject3 = this.mUrl;
    localObject1 = ((URL)localObject3).getProtocol();
    int j = ((URL)localObject3).getPort();
    int i = j;
    if (j < 0)
    {
      i = j;
      if ("https".equalsIgnoreCase((String)localObject1)) {
        i = 443;
      }
    }
    localObject2 = new HttpHost(((URL)localObject3).getHost(), i, (String)localObject1);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("host : ");
    localStringBuilder.append(((URL)localObject3).getHost());
    localStringBuilder.toString();
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("port : ");
    ((StringBuilder)localObject3).append(i);
    ((StringBuilder)localObject3).toString();
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("schme : ");
    ((StringBuilder)localObject3).append((String)localObject1);
    ((StringBuilder)localObject3).toString();
    this.mHttpResponse = jdField_a_of_type_ComTencentCommonHttpQBHttpClient.executeQuest((HttpHost)localObject2, this.mHttpRequest);
    i = this.mHttpResponse.getStatusLine().getStatusCode();
    if ((i != 300) && (i != 301) && (i != 302) && (i != 303))
    {
      localObject1 = null;
    }
    else
    {
      localObject1 = this.mHttpResponse.getFirstHeader("Location");
      if (localObject1 == null) {
        localObject1 = null;
      } else {
        localObject1 = ((Header)localObject1).getValue();
      }
    }
    paramMttRequestBase.setRedirectUrl(null);
    paramMttRequestBase = this.mHttpResponse.getFirstHeader("Content-Length");
    long l;
    if (paramMttRequestBase != null) {
      l = a(paramMttRequestBase.getValue());
    } else {
      l = 0L;
    }
    i = checkStatusCode(this.mMttRequest, i, (String)localObject1, l);
    if (i == 1)
    {
      paramMttRequestBase = this.mHttpResponse.getEntity();
      this.mMttResponse = a(this.mHttpResponse);
      if (paramMttRequestBase != null)
      {
        this.jdField_a_of_type_JavaIoInputStream = paramMttRequestBase.getContent();
        this.mInputStream = new MttInputStream(this.jdField_a_of_type_JavaIoInputStream, true);
        this.mInputStream.setExceptionHandler(this.mExceptionHandler);
        this.mMttResponse.setInputStream(this.mInputStream);
      }
    }
    return i;
  }
  
  private long a(String paramString)
  {
    try
    {
      long l = Long.parseLong(paramString);
      return l;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return 0L;
  }
  
  private MttResponse a(HttpResponse paramHttpResponse)
    throws Exception
  {
    MttResponse localMttResponse = new MttResponse();
    localMttResponse.setStatusCode(Integer.valueOf(paramHttpResponse.getStatusLine().getStatusCode()));
    Object localObject1 = paramHttpResponse.getFirstHeader("Location");
    Object localObject2 = null;
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setLocation((String)localObject1);
    if ((this.mCookieEnable) && ((paramHttpResponse.containsHeader("Set-Cookie")) || (paramHttpResponse.containsHeader("Set-Cookie2"))))
    {
      localObject1 = new HashMap();
      Header[] arrayOfHeader = paramHttpResponse.getHeaders("Set-Cookie");
      localObject3 = new ArrayList();
      int k = arrayOfHeader.length;
      int j = 0;
      int i = 0;
      while (i < k)
      {
        ((ArrayList)localObject3).add(arrayOfHeader[i].getValue());
        i += 1;
      }
      ((Map)localObject1).put("Set-Cookie", localObject3);
      arrayOfHeader = paramHttpResponse.getHeaders("Set-Cookie2");
      k = arrayOfHeader.length;
      i = j;
      while (i < k)
      {
        ((ArrayList)localObject3).add(arrayOfHeader[i].getValue());
        i += 1;
      }
      ((Map)localObject1).put("Set-Cookie2", localObject3);
      if (!((Map)localObject1).isEmpty())
      {
        localMttResponse.setCookies((Map)localObject1);
        setCookie((Map)localObject1);
      }
    }
    localObject1 = paramHttpResponse.getFirstHeader("Server");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setServer((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("Content-Length");
    long l;
    if (localObject1 == null) {
      l = 0L;
    } else {
      l = a(((Header)localObject1).getValue());
    }
    localMttResponse.setContentLength(l);
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("header : ");
    ((StringBuilder)localObject3).append(localObject1);
    ((StringBuilder)localObject3).toString();
    if (localObject1 != null)
    {
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("header.getValue : ");
      ((StringBuilder)localObject3).append(((Header)localObject1).getValue());
      ((StringBuilder)localObject3).toString();
    }
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("Content-Length : ");
    ((StringBuilder)localObject1).append(localMttResponse.getContentLength());
    ((StringBuilder)localObject1).toString();
    localObject1 = paramHttpResponse.getFirstHeader("Content-Encoding");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setContentEncoding((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("Charset");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setCharset((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("Transfer-Encoding");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setTransferEncoding((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("Last-Modified");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setLastModify((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("Byte-Ranges");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setByteRanges((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("Cache-Control");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setCacheControl((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("Connection");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setConnection((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("Content-Range");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setContentRange((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("Content-Disposition");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setContentDisposition((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("QQ-S-ZIP");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setQSZip((String)localObject1);
    localObject1 = paramHttpResponse.getFirstHeader("QQ-S-Encrypt");
    if (localObject1 == null) {
      localObject1 = null;
    } else {
      localObject1 = ((Header)localObject1).getValue();
    }
    localMttResponse.setQEncrypt((String)localObject1);
    paramHttpResponse = paramHttpResponse.getFirstHeader("Content-Type");
    if (paramHttpResponse == null) {
      paramHttpResponse = (HttpResponse)localObject2;
    } else {
      paramHttpResponse = paramHttpResponse.getValue();
    }
    localMttResponse.setContentType(parseContentType(paramHttpResponse, this.mUrl.toString()));
    return localMttResponse;
  }
  
  private void a()
  {
    Iterator localIterator = this.mMttRequest.getHeaders().entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      this.mHttpRequest.setHeader((String)localEntry.getKey(), (String)localEntry.getValue());
    }
    if (!TextUtils.isEmpty(this.mMttRequest.getReferer())) {
      this.mHttpRequest.setHeader("Referer", this.mMttRequest.getReferer());
    }
    if (this.mCookieEnable) {
      fillCookies();
    }
    if (this.Q_DEBUG) {
      fillQHeaders();
    }
  }
  
  private void b()
  {
    boolean bool = this.mMttRequest.isQueenProxyEnable();
    Map.Entry localEntry = null;
    if (bool) {
      localObject = this.mMttRequest.getQueenConfig();
    } else {
      localObject = null;
    }
    if (localObject == null) {
      return;
    }
    Object localObject = localEntry;
    if (this.mMttRequest.isQueenProxyEnable())
    {
      localObject = localEntry;
      if (this.mMttRequest.getQueenConfig() != null) {
        localObject = this.mMttRequest.getQueenConfig().headers;
      }
    }
    if (localObject != null)
    {
      localObject = ((Map)localObject).entrySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        localEntry = (Map.Entry)((Iterator)localObject).next();
        this.mHttpRequest.setHeader((String)localEntry.getKey(), (String)localEntry.getValue());
      }
    }
  }
  
  private void c()
    throws IOException
  {
    if (this.mMttRequest.isPostEnable())
    {
      IPostDataBuf localIPostDataBuf = this.mMttRequest.getPostData();
      Object localObject;
      if (this.mHttpRequest.getFirstHeader("Content-Type") == null) {
        if (localIPostDataBuf.isUploadFile())
        {
          localObject = this.mHttpRequest;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("multipart/form-data; boundary=");
          localStringBuilder.append(localIPostDataBuf.getBoundary());
          ((HttpRequestBase)localObject).setHeader("Content-Type", localStringBuilder.toString());
        }
        else
        {
          this.mHttpRequest.setHeader("Content-Type", "application/x-www-form-urlencoded");
        }
      }
      if (localIPostDataBuf.hasValidData())
      {
        localObject = (HttpPost)this.mHttpRequest;
        try
        {
          ((HttpPost)localObject).setEntity(new ByteArrayEntity(localIPostDataBuf.toByteArray()));
          return;
        }
        catch (OutOfMemoryError localOutOfMemoryError)
        {
          localOutOfMemoryError.printStackTrace();
        }
      }
    }
  }
  
  public static void shutdown()
  {
    jdField_a_of_type_ComTencentCommonHttpQBHttpClient.getConnectionManager().shutdown();
  }
  
  HttpHost a(String paramString)
  {
    boolean bool = this.mMttRequest.getIsWupRequest();
    StringBuilder localStringBuilder = null;
    if (bool) {
      return null;
    }
    if (this.mMttRequest.isQueenProxyEnable()) {
      localObject = this.mMttRequest.getQueenConfig();
    } else {
      localObject = null;
    }
    if (localObject != null) {
      localObject = ((QueenConfig.QueenConfigInfo)localObject).proxyInfo;
    } else {
      localObject = null;
    }
    if (localObject != null) {
      return new HttpHost(((QueenConfig.QueenProxy)localObject).url, ((QueenConfig.QueenProxy)localObject).port);
    }
    if (this.mMttRequest.isProxyDisable()) {
      return null;
    }
    Apn.ApnProxyInfo localApnProxyInfo = Apn.getApnProxyInfo();
    Object localObject = localStringBuilder;
    if (localApnProxyInfo.apnUseProxy)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("PROXY : ");
      ((StringBuilder)localObject).append(localApnProxyInfo.apnProxy);
      ((StringBuilder)localObject).toString();
      int i = paramString.indexOf("://") + 3;
      int j = paramString.indexOf('/', i);
      if (j < 0)
      {
        localObject = paramString.substring(i);
        paramString = "";
      }
      else
      {
        localObject = paramString.substring(i, j);
        paramString = paramString.substring(j);
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Host : ");
      localStringBuilder.append((String)localObject);
      localStringBuilder.toString();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Path : ");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).toString();
      localObject = new HttpHost(localApnProxyInfo.apnProxy, localApnProxyInfo.apnPort);
    }
    return (HttpHost)localObject;
  }
  
  public void abort()
  {
    this.mIsCanceled = true;
    close();
  }
  
  public void close()
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("CLOSE : ");
    ((StringBuilder)localObject1).append(this);
    ((StringBuilder)localObject1).toString();
    localObject1 = this.mHttpRequest;
    if (localObject1 != null) {
      ((HttpRequestBase)localObject1).abort();
    }
    if (this.mInputStream != null)
    {
      try
      {
        this.mInputStream.close();
      }
      catch (Exception localException1)
      {
        localException1.printStackTrace();
      }
      this.mInputStream = null;
    }
    InputStream localInputStream = this.jdField_a_of_type_JavaIoInputStream;
    if (localInputStream != null)
    {
      try
      {
        localInputStream.close();
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
      }
      this.jdField_a_of_type_JavaIoInputStream = null;
    }
    if ((this.mMttRequest != null) && (this.mMttRequest.isPostEnable())) {
      this.mMttRequest.getPostData().cancel();
    }
    if (this.mMttRequest != null)
    {
      Object localObject2 = this.mMttRequest;
      int i;
      if (this.mInputStream != null) {
        i = this.mInputStream.getFlow();
      } else {
        i = 0;
      }
      ((MttRequestBase)localObject2).setDownFlow(i);
      localObject2 = RequesterFactory.getRequestObsever();
      if (localObject2 != null) {
        ((RequesterFactory.IRequestObsever)localObject2).onRequestComplete(this.mMttRequest);
      }
    }
    jdField_a_of_type_OrgApacheHttpImplConnTsccmThreadSafeClientConnManager.closeExpiredConnections();
  }
  
  public MttResponse execute(MttRequestBase paramMttRequestBase)
    throws Exception
  {
    if (paramMttRequestBase == null) {
      return null;
    }
    if (paramMttRequestBase.getRequestType() == 104) {
      paramMttRequestBase.addHeader("Accept-Encoding", "identity");
    }
    paramMttRequestBase.doHeaderIntercept();
    this.mMttRequest = paramMttRequestBase;
    paramMttRequestBase.setProxyDisable(this.mDisableProxy);
    while ((!this.mIsCanceled) && (this.mRetryTimes <= 3))
    {
      int i;
      try
      {
        i = a(paramMttRequestBase);
      }
      catch (Throwable localThrowable)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Exception: ");
        localStringBuilder.append(localThrowable.toString());
        FLogger.d("HttpClient", localStringBuilder.toString());
        i = handleException(paramMttRequestBase, localThrowable);
        if (i == 1) {
          break label155;
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
          label155:
          throw localThrowable;
        }
      }
    }
    return this.mMttResponse;
  }
  
  protected void fillCookies()
  {
    String str = this.mMttRequest.getCookie();
    if (!TextUtils.isEmpty(str)) {
      this.mHttpRequest.setHeader("Cookie", str);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("CookieStr : ");
    localStringBuilder.append(str);
    localStringBuilder.toString();
  }
  
  protected void fillQHeaders() {}
  
  protected void fillUserAgent()
  {
    if (!this.mMttRequest.hasHeader("User-Agent")) {
      this.mMttRequest.addHeader("User-Agent", this.mMttRequest.getUserAgent());
    }
  }
  
  public MttResponse getResponse()
  {
    return this.mMttResponse;
  }
  
  protected String getTag()
  {
    return "HttpClient";
  }
  
  public void setCookie(Map<String, List<String>> paramMap) {}
  
  static class a
    extends HttpEntityWrapper
  {
    public a(HttpEntity paramHttpEntity)
    {
      super();
    }
    
    public InputStream getContent()
      throws IOException, IllegalStateException
    {
      return new GZIPInputStream(this.wrappedEntity.getContent());
    }
    
    public long getContentLength()
    {
      return -1L;
    }
  }
  
  static class b
    extends org.apache.http.conn.ssl.SSLSocketFactory
  {
    SSLContext a = SSLContext.getInstance("TLS");
    
    public b(KeyStore paramKeyStore)
      throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException, UnrecoverableKeyException
    {
      super();
      paramKeyStore = new X509TrustManager()
      {
        public void checkClientTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
          throws CertificateException
        {}
        
        public void checkServerTrusted(X509Certificate[] paramAnonymousArrayOfX509Certificate, String paramAnonymousString)
          throws CertificateException
        {}
        
        public X509Certificate[] getAcceptedIssuers()
        {
          return null;
        }
      };
      this.a.init(null, new TrustManager[] { paramKeyStore }, null);
    }
    
    public Socket createSocket()
      throws IOException
    {
      return this.a.getSocketFactory().createSocket();
    }
    
    public Socket createSocket(Socket paramSocket, String paramString, int paramInt, boolean paramBoolean)
      throws IOException, UnknownHostException
    {
      return this.a.getSocketFactory().createSocket(paramSocket, paramString, paramInt, paramBoolean);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\HttpClientRequesterBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */