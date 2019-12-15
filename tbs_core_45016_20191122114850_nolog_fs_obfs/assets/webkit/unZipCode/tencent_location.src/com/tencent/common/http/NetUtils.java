package com.tencent.common.http;

import android.os.Build.VERSION;
import android.text.TextUtils;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;

public class NetUtils
{
  public static final String CONTENT_LENGTH = "Content-Length";
  public static final String CONTENT_RANGE = "Content-Range";
  public static final String CONTENT_RANGE_PARAMS = "bytes ";
  public static final String HOST = "Host";
  public static final String HTTP_END = "\r\n\r\n";
  public static final String LINE_BREAK = "\r\n";
  public static final String RANGE = "Range";
  public static final String RANGE_PARAMS = "bytes=";
  public static final String RANGE_PARAMS_0 = "bytes=0-";
  public static final String SCHEME_HTTP = "http://";
  public static final String SCHEME_HTTPS = "https://";
  public static final String USER_AGENT = "User-Agent";
  
  private static Requester a(MttRequestBase paramMttRequestBase)
  {
    int j;
    if ((Build.VERSION.SDK_INT != 19) && (Build.VERSION.SDK_INT != 20)) {
      j = 0;
    } else {
      j = 1;
    }
    Object localObject = paramMttRequestBase.getUrl();
    int i = j;
    if (!TextUtils.isEmpty((CharSequence)localObject))
    {
      i = j;
      if (((String)localObject).toLowerCase().startsWith("https")) {
        i = j ^ 0x1;
      }
    }
    try
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("requestBase.getUrl()=");
      ((StringBuilder)localObject).append(paramMttRequestBase.getUrl());
      ((StringBuilder)localObject).toString();
      paramMttRequestBase = new URL(paramMttRequestBase.getUrl()).getHost();
      j = i;
      if (!TextUtils.isEmpty(paramMttRequestBase))
      {
        boolean bool = paramMttRequestBase.contains("_");
        j = i;
        if (bool) {
          j = 1;
        }
      }
    }
    catch (Exception paramMttRequestBase)
    {
      paramMttRequestBase.printStackTrace();
      j = i;
    }
    if ((!Apn.getApnProxyInfo().apnUseProxy) && (j != 0)) {
      paramMttRequestBase = new HttpClientRequesterBase();
    } else {
      paramMttRequestBase = new HttpRequesterBase();
    }
    paramMttRequestBase.setCookieEnable(true);
    if (Apn.is4GMode(false)) {
      paramMttRequestBase.setDisableProxy(true);
    }
    return paramMttRequestBase;
  }
  
  public static String genResponseHeader(int paramInt1, int paramInt2, int paramInt3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("HTTP/1.1 206 Partial Content");
    localStringBuffer.append("\n");
    localStringBuffer.append("Content-Type: audio/mpeg");
    localStringBuffer.append("\n");
    localStringBuffer.append("Content-Length: ");
    localStringBuffer.append(paramInt2 - paramInt1 + 1);
    localStringBuffer.append("\n");
    localStringBuffer.append("Connection: keep-alive");
    localStringBuffer.append("\n");
    localStringBuffer.append("Accept-Ranges: bytes");
    localStringBuffer.append("\n");
    String str = String.format("bytes %d-%d/%d", new Object[] { Integer.valueOf(paramInt1), Integer.valueOf(paramInt2), Integer.valueOf(paramInt3) });
    localStringBuffer.append("Content-Range: ");
    localStringBuffer.append(str);
    localStringBuffer.append("\n");
    localStringBuffer.append("\n");
    return localStringBuffer.toString();
  }
  
  public static String[] getRequestPartsFromSocket(Socket paramSocket)
  {
    byte[] arrayOfByte = new byte['Ѐ'];
    Object localObject1 = "";
    Object localObject2;
    for (;;)
    {
      localObject2 = localObject1;
      try
      {
        int i = paramSocket.getInputStream().read(arrayOfByte);
        localObject2 = localObject1;
        if (i != -1)
        {
          localObject2 = localObject1;
          Object localObject3 = new byte[i];
          localObject2 = localObject1;
          System.arraycopy(arrayOfByte, 0, localObject3, 0, i);
          localObject2 = localObject1;
          localObject3 = new String((byte[])localObject3);
          localObject2 = localObject1;
          StringBuilder localStringBuilder = new StringBuilder();
          localObject2 = localObject1;
          localStringBuilder.append(" Header-> ");
          localObject2 = localObject1;
          localStringBuilder.append((String)localObject3);
          localObject2 = localObject1;
          localStringBuilder.toString();
          localObject2 = localObject1;
          localStringBuilder = new StringBuilder();
          localObject2 = localObject1;
          localStringBuilder.append((String)localObject1);
          localObject2 = localObject1;
          localStringBuilder.append((String)localObject3);
          localObject2 = localObject1;
          localObject3 = localStringBuilder.toString();
          localObject1 = localObject3;
          localObject2 = localObject3;
          if (!((String)localObject3).contains("GET")) {
            continue;
          }
          localObject2 = localObject3;
          boolean bool = ((String)localObject3).contains("\r\n\r\n");
          localObject1 = localObject3;
          if (!bool) {
            continue;
          }
          localObject2 = localObject3;
        }
      }
      catch (IOException paramSocket)
      {
        paramSocket.printStackTrace();
      }
    }
    return ((String)localObject2).split("\r\n");
  }
  
  public static MttResponse send(MttRequestBase paramMttRequestBase)
  {
    a(paramMttRequestBase);
    throw new NullPointerException();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\NetUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */