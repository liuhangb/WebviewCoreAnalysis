package com.tencent.smtt.net;

import com.tencent.smtt.export.external.interfaces.UrlRequest;
import com.tencent.smtt.export.external.interfaces.UrlRequest.Callback;
import com.tencent.smtt.export.external.interfaces.UrlResponseInfo;
import com.tencent.smtt.export.external.interfaces.X5netException;
import java.nio.ByteBuffer;

public class l
{
  public static final class a
    extends UrlRequest.Callback
  {
    private final UrlRequest.Callback a;
    
    public a(UrlRequest.Callback paramCallback)
    {
      this.a = paramCallback;
    }
    
    public void a(UrlRequest paramUrlRequest, UrlResponseInfo paramUrlResponseInfo)
      throws Exception
    {
      this.a.onResponseStarted(paramUrlRequest, paramUrlResponseInfo);
    }
    
    public void a(UrlRequest paramUrlRequest, UrlResponseInfo paramUrlResponseInfo, X5netException paramX5netException)
    {
      this.a.onFailed(paramUrlRequest, paramUrlResponseInfo, paramX5netException);
    }
    
    public void a(UrlRequest paramUrlRequest, UrlResponseInfo paramUrlResponseInfo, String paramString)
      throws Exception
    {
      this.a.onRedirectReceived(paramUrlRequest, paramUrlResponseInfo, paramString);
    }
    
    public void a(UrlRequest paramUrlRequest, UrlResponseInfo paramUrlResponseInfo, ByteBuffer paramByteBuffer)
      throws Exception
    {
      this.a.onReadCompleted(paramUrlRequest, paramUrlResponseInfo, paramByteBuffer);
    }
    
    public void b(UrlRequest paramUrlRequest, UrlResponseInfo paramUrlResponseInfo)
    {
      this.a.onSucceeded(paramUrlRequest, paramUrlResponseInfo);
    }
    
    public void c(UrlRequest paramUrlRequest, UrlResponseInfo paramUrlResponseInfo)
    {
      this.a.onCanceled(paramUrlRequest, paramUrlResponseInfo);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */