package com.tencent.smtt.util;

import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.UsedByReflection;

@JNINamespace("android_webview")
public class MttTraceEvent
{
  private static final boolean ENABLE_MTT_TRACE_EVENT = false;
  public static final int HITTEST = 32;
  public static final int KPI = 2;
  public static final int LOAD = 16;
  public static final int NETWORK = 4;
  public static final int UI = 64;
  public static final int WEBCORE = 8;
  private static volatile boolean bIsTraceEnabled = false;
  private static volatile int sMask = 4;
  
  @UsedByReflection("WebCoreProxy.java")
  public static void begin(int paramInt)
  {
    if (enabled(paramInt)) {
      nativeBegin(getCallerName(), null);
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void begin(int paramInt, String paramString)
  {
    if (enabled(paramInt)) {
      nativeBegin(paramString, null);
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void begin(int paramInt, String paramString1, String paramString2)
  {
    if (enabled(paramInt)) {
      nativeBegin(paramString1, paramString2);
    }
  }
  
  public static boolean enabled(int paramInt)
  {
    return false;
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void end(int paramInt)
  {
    if (enabled(paramInt)) {
      nativeEnd(getCallerName(), null);
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void end(int paramInt, String paramString)
  {
    if (enabled(paramInt)) {
      nativeEnd(paramString, null);
    }
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void end(int paramInt, String paramString1, String paramString2)
  {
    if (enabled(paramInt)) {
      nativeEnd(paramString1, paramString2);
    }
  }
  
  public static void finishAsync(int paramInt, long paramLong)
  {
    if (enabled(paramInt)) {
      nativeFinishAsync(getCallerName(), paramLong, null);
    }
  }
  
  public static void finishAsync(int paramInt, String paramString, long paramLong)
  {
    if (enabled(paramInt)) {
      nativeFinishAsync(paramString, paramLong, null);
    }
  }
  
  public static void finishAsync(int paramInt, String paramString1, long paramLong, String paramString2)
  {
    if (enabled(paramInt)) {
      nativeFinishAsync(paramString1, paramLong, paramString2);
    }
  }
  
  private static String getCallerName()
  {
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(arrayOfStackTraceElement[4].getClassName());
    localStringBuilder.append(".");
    localStringBuilder.append(arrayOfStackTraceElement[4].getMethodName());
    return localStringBuilder.toString();
  }
  
  public static void instant(int paramInt, String paramString)
  {
    if (enabled(paramInt)) {
      nativeInstant(paramString, null);
    }
  }
  
  public static void instant(int paramInt, String paramString1, String paramString2)
  {
    if (enabled(paramInt)) {
      nativeInstant(paramString1, paramString2);
    }
  }
  
  private static native void nativeBegin(String paramString1, String paramString2);
  
  private static native void nativeEnd(String paramString1, String paramString2);
  
  private static native void nativeFinishAsync(String paramString1, long paramLong, String paramString2);
  
  private static native void nativeInstant(String paramString1, String paramString2);
  
  private static native void nativeStartAsync(String paramString1, long paramLong, String paramString2);
  
  private static native boolean nativeTraceEnabled();
  
  public static void setTraceEnableFlag(boolean paramBoolean)
  {
    bIsTraceEnabled = paramBoolean;
  }
  
  public static void startAsync(int paramInt, long paramLong)
  {
    if (enabled(paramInt)) {
      nativeStartAsync(getCallerName(), paramLong, null);
    }
  }
  
  public static void startAsync(int paramInt, String paramString, long paramLong)
  {
    if (enabled(paramInt)) {
      nativeStartAsync(paramString, paramLong, null);
    }
  }
  
  public static void startAsync(int paramInt, String paramString1, long paramLong, String paramString2)
  {
    if (enabled(paramInt)) {
      nativeStartAsync(paramString1, paramLong, paramString2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\MttTraceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */