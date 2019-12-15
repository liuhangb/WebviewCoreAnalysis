package org.chromium.android_webview;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwDevToolsServer
{
  private long a = nativeInitRemoteDebugging();
  
  private native void nativeDestroyRemoteDebugging(long paramLong);
  
  private native long nativeInitRemoteDebugging();
  
  private native void nativeSetRemoteDebuggingEnabled(long paramLong, boolean paramBoolean);
  
  public void destroy()
  {
    nativeDestroyRemoteDebugging(this.a);
    this.a = 0L;
  }
  
  public void setRemoteDebuggingEnabled(boolean paramBoolean)
  {
    nativeSetRemoteDebuggingEnabled(this.a, paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwDevToolsServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */