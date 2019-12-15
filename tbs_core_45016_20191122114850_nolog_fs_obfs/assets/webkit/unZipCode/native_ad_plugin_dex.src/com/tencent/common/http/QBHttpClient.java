package com.tencent.common.http;

import java.io.IOException;
import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.RequestDirector;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;

public class QBHttpClient
  extends DefaultHttpClient
{
  HttpRequestBase a = null;
  
  public QBHttpClient(ThreadSafeClientConnManager paramThreadSafeClientConnManager, BasicHttpParams paramBasicHttpParams)
  {
    super(paramThreadSafeClientConnManager, paramBasicHttpParams);
  }
  
  protected RequestDirector createClientRequestDirector(HttpRequestExecutor paramHttpRequestExecutor, ClientConnectionManager paramClientConnectionManager, ConnectionReuseStrategy paramConnectionReuseStrategy, ConnectionKeepAliveStrategy paramConnectionKeepAliveStrategy, HttpRoutePlanner paramHttpRoutePlanner, HttpProcessor paramHttpProcessor, HttpRequestRetryHandler paramHttpRequestRetryHandler, RedirectHandler paramRedirectHandler, AuthenticationHandler paramAuthenticationHandler1, AuthenticationHandler paramAuthenticationHandler2, UserTokenHandler paramUserTokenHandler, HttpParams paramHttpParams)
  {
    return new QBDefaultRequestDirector(paramHttpRequestExecutor, paramClientConnectionManager, paramConnectionReuseStrategy, paramConnectionKeepAliveStrategy, paramHttpRoutePlanner, paramHttpProcessor, paramHttpRequestRetryHandler, paramRedirectHandler, paramAuthenticationHandler1, paramAuthenticationHandler2, paramUserTokenHandler, paramHttpParams, this.a);
  }
  
  public HttpResponse executeQuest(HttpHost paramHttpHost, HttpRequestBase paramHttpRequestBase)
    throws IOException
  {
    this.a = paramHttpRequestBase;
    return execute(paramHttpHost, paramHttpRequestBase);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\QBHttpClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */