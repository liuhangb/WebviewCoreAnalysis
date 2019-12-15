package com.tencent.smtt.net;

import com.tencent.smtt.util.MttLog;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class NetLogLoggerService
{
  private static String a = "java.NetLogLoggerService";
  
  public static void a() {}
  
  public static void b() {}
  
  public static void c() {}
  
  private static native void nativeEnableHttpDump();
  
  private static native void nativeOpenNetLog();
  
  private static native void nativeStopNetLog();
  
  @CalledByNative
  public static void netlog(String paramString)
  {
    MttLog.netlog(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\NetLogLoggerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */