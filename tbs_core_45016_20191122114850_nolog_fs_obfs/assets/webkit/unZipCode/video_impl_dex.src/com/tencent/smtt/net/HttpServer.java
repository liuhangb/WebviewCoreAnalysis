package com.tencent.smtt.net;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class HttpServer
{
  private long jdField_a_of_type_Long;
  private Delegate jdField_a_of_type_ComTencentSmttNetHttpServer$Delegate;
  
  public HttpServer(Delegate paramDelegate, String paramString)
  {
    this.jdField_a_of_type_ComTencentSmttNetHttpServer$Delegate = paramDelegate;
    this.jdField_a_of_type_Long = nativeInit(paramString);
  }
  
  private native void nativeClose(long paramLong, int paramInt);
  
  private native long nativeInit(String paramString);
  
  private native void nativeSend(long paramLong, int paramInt1, int paramInt2, String paramString1, String paramString2);
  
  private native void nativeSend200(long paramLong, int paramInt, String paramString1, String paramString2);
  
  private native void nativeSend404(long paramLong, int paramInt);
  
  private native void nativeSend500(long paramLong, int paramInt, String paramString);
  
  private native void nativeSendOverWebSocket(long paramLong, int paramInt, String paramString);
  
  private native void nativeSendRaw(long paramLong, int paramInt, String paramString);
  
  private native void nativeSendResponse(long paramLong, int paramInt, String paramString);
  
  private native void nativeSetReceiveBufferSize(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativeSetSendBufferSize(long paramLong, int paramInt1, int paramInt2);
  
  public void a(int paramInt)
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeSend404(l, paramInt);
    }
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeSetReceiveBufferSize(l, paramInt1, paramInt2);
    }
  }
  
  public void a(int paramInt1, int paramInt2, String paramString1, String paramString2)
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeSend(l, paramInt1, paramInt2, paramString1, paramString2);
    }
  }
  
  public void a(int paramInt, String paramString)
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeSendOverWebSocket(l, paramInt, paramString);
    }
  }
  
  public void b(int paramInt1, int paramInt2)
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeSetSendBufferSize(l, paramInt1, paramInt2);
    }
  }
  
  public void b(int paramInt, String paramString)
  {
    long l = this.jdField_a_of_type_Long;
    if (l != 0L) {
      nativeSend500(l, paramInt, paramString);
    }
  }
  
  @CalledByNative
  void onClose(int paramInt)
  {
    Delegate localDelegate = this.jdField_a_of_type_ComTencentSmttNetHttpServer$Delegate;
    if (localDelegate != null) {
      localDelegate.onClose(paramInt);
    }
  }
  
  @CalledByNative
  void onConnect(int paramInt)
  {
    Delegate localDelegate = this.jdField_a_of_type_ComTencentSmttNetHttpServer$Delegate;
    if (localDelegate != null) {
      localDelegate.onConnect(paramInt);
    }
  }
  
  @CalledByNative
  void onHttpRequest(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    Delegate localDelegate = this.jdField_a_of_type_ComTencentSmttNetHttpServer$Delegate;
    if (localDelegate != null) {
      localDelegate.onHttpRequest(paramInt, paramString1, paramString2, paramString3);
    }
  }
  
  @CalledByNative
  void onWebSocketMessage(int paramInt, String paramString)
  {
    Delegate localDelegate = this.jdField_a_of_type_ComTencentSmttNetHttpServer$Delegate;
    if (localDelegate != null) {
      localDelegate.onWebSocketMessage(paramInt, paramString);
    }
  }
  
  @CalledByNative
  boolean onWebSocketRequest(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    Delegate localDelegate = this.jdField_a_of_type_ComTencentSmttNetHttpServer$Delegate;
    if (localDelegate != null) {
      return localDelegate.onWebSocketRequest(paramInt, paramString1, paramString2, paramString3);
    }
    return false;
  }
  
  public static abstract interface Delegate
  {
    public abstract void onClose(int paramInt);
    
    public abstract void onConnect(int paramInt);
    
    public abstract void onHttpRequest(int paramInt, String paramString1, String paramString2, String paramString3);
    
    public abstract void onWebSocketMessage(int paramInt, String paramString);
    
    public abstract boolean onWebSocketRequest(int paramInt, String paramString1, String paramString2, String paramString3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\HttpServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */