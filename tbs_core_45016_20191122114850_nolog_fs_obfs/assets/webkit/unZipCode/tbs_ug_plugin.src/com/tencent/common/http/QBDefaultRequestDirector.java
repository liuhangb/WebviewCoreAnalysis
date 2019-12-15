package com.tencent.common.http;

import org.apache.http.ConnectionReuseStrategy;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.client.AuthenticationHandler;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.RedirectHandler;
import org.apache.http.client.UserTokenHandler;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.impl.client.DefaultRequestDirector;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpRequestExecutor;

public class QBDefaultRequestDirector
  extends DefaultRequestDirector
{
  HttpRequestBase a;
  
  public QBDefaultRequestDirector(HttpRequestExecutor paramHttpRequestExecutor, ClientConnectionManager paramClientConnectionManager, ConnectionReuseStrategy paramConnectionReuseStrategy, ConnectionKeepAliveStrategy paramConnectionKeepAliveStrategy, HttpRoutePlanner paramHttpRoutePlanner, HttpProcessor paramHttpProcessor, HttpRequestRetryHandler paramHttpRequestRetryHandler, RedirectHandler paramRedirectHandler, AuthenticationHandler paramAuthenticationHandler1, AuthenticationHandler paramAuthenticationHandler2, UserTokenHandler paramUserTokenHandler, HttpParams paramHttpParams, HttpRequestBase paramHttpRequestBase)
  {
    super(paramHttpRequestExecutor, paramClientConnectionManager, paramConnectionReuseStrategy, paramConnectionKeepAliveStrategy, paramHttpRoutePlanner, paramHttpProcessor, paramHttpRequestRetryHandler, paramRedirectHandler, paramAuthenticationHandler1, paramAuthenticationHandler2, paramUserTokenHandler, paramHttpParams);
    this.a = paramHttpRequestBase;
  }
  
  protected HttpRequest createConnectRequest(HttpRoute paramHttpRoute, HttpContext paramHttpContext)
  {
    paramHttpContext = super.createConnectRequest(paramHttpRoute, paramHttpContext);
    paramHttpRoute = this.a;
    if (paramHttpRoute != null) {
      paramHttpRoute = paramHttpRoute.getFirstHeader("Proxy-Authorization");
    } else {
      paramHttpRoute = null;
    }
    if (paramHttpRoute != null) {
      paramHttpContext.setHeader("Proxy-Authorization", paramHttpRoute.getValue());
    }
    return paramHttpContext;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\QBDefaultRequestDirector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */