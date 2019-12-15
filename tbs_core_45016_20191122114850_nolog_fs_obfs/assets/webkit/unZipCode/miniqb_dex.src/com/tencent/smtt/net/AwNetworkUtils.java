package com.tencent.smtt.net;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.UsedByReflection;

@JNINamespace("tencent")
public class AwNetworkUtils
{
  public static void StartRenderMonitor(int paramInt1, int paramInt2, int paramInt3)
  {
    nativeStartRenderMonitor(paramInt1, paramInt2, paramInt3);
  }
  
  public static String StopRenderMonitor(int paramInt1, int paramInt2, int paramInt3)
  {
    return nativeStopRenderMonitor(paramInt1, paramInt2, paramInt3);
  }
  
  public static void clearCache()
  {
    nativeClearDNSCache();
    nativeClearCache();
    nativeClearSWCache();
    SWOfflineFramework.getInstance().clearSWCache();
  }
  
  public static void clearCacheExpceptSW()
  {
    nativeClearDNSCache();
    nativeClearCache();
  }
  
  public static void clearCacheOnlySW()
  {
    nativeClearSWCache();
    SWOfflineFramework.getInstance().clearSWCache();
  }
  
  public static void clearDNSCache() {}
  
  public static void clearMemoryCache() {}
  
  @UsedByReflection("WebCoreProxy.java")
  public static void closeAllSpdySessions() {}
  
  public static void enableADFilterAddr(String paramString)
  {
    nativeEnableADFilterAddr(paramString);
  }
  
  public static void enableTraceUploading(boolean paramBoolean) {}
  
  public static String[] getCacheEntry(String paramString1, String paramString2)
  {
    return nativeGetCacheEntry(paramString1, paramString2);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static String getCanonicalUrl(String paramString)
  {
    return "";
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static String getCoreExtraMessage()
  {
    return nativeGetCoreExtraMessage();
  }
  
  private static native void nativeClearCache();
  
  private static native void nativeClearDNSCache();
  
  private static native void nativeClearMemCache();
  
  private static native void nativeClearSWCache();
  
  private static native void nativeCloseAllSpdySessions();
  
  public static native void nativeCustomDiskCachePathEnabled(boolean paramBoolean, String paramString);
  
  private static native void nativeEnableADFilterAddr(String paramString);
  
  private static native void nativeEnableTraceUploading(boolean paramBoolean);
  
  private static native String[] nativeGetCacheEntry(String paramString1, String paramString2);
  
  private static native String nativeGetCoreExtraMessage();
  
  private static native void nativePrefetchResource(String paramString1, String paramString2);
  
  private static native void nativeSetConnectionTimeOut(int paramInt1, int paramInt2);
  
  private static native void nativeSetCustomHosts(String paramString);
  
  public static native void nativeSetIsViewSourceMode(boolean paramBoolean);
  
  public static native void nativeSetNetworkTypeChangedState(int paramInt);
  
  private static native void nativeSetPreconnect(String paramString, int paramInt);
  
  private static native void nativeSetQProxyType(int paramInt);
  
  public static native void nativeSetQProxyUserChanged(boolean paramBoolean);
  
  private static native void nativeSetSPDYPreconnect();
  
  private static native void nativeStartCheckSpdyProxy(String paramString);
  
  private static native void nativeStartRenderMonitor(int paramInt1, int paramInt2, int paramInt3);
  
  private static native String nativeStopRenderMonitor(int paramInt1, int paramInt2, int paramInt3);
  
  public static native void nativeUpdateCrashMonitorMessage(String paramString1, String paramString2);
  
  public static void prefetchResource(String paramString, Map<String, String> paramMap)
  {
    StringBuilder localStringBuilder1 = new StringBuilder();
    if ((paramMap != null) && (!paramMap.isEmpty()))
    {
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        StringBuilder localStringBuilder2 = new StringBuilder();
        localStringBuilder2.append((String)localEntry.getKey());
        localStringBuilder2.append(":");
        localStringBuilder2.append((String)localEntry.getValue());
        localStringBuilder2.append("\r\n");
        localStringBuilder1.append(localStringBuilder2.toString());
      }
    }
    nativePrefetchResource(paramString, localStringBuilder1.toString());
  }
  
  public static void setConnectionTimeOut(int paramInt1, int paramInt2)
  {
    if ((paramInt1 != 0) && (paramInt2 != 0)) {
      nativeSetConnectionTimeOut(paramInt1, paramInt2);
    }
  }
  
  public static void setCustomDiskCachePath(boolean paramBoolean, String paramString)
  {
    nativeCustomDiskCachePathEnabled(paramBoolean, paramString);
  }
  
  public static void setCustomHosts(String paramString)
  {
    nativeSetCustomHosts(paramString);
  }
  
  @UsedByReflection("TBSShellMtt.java")
  public static void setNetworkTypeChangedState(int paramInt)
  {
    nativeSetNetworkTypeChangedState(paramInt);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void setPreConnect(String paramString, int paramInt)
  {
    nativeSetPreconnect(paramString, paramInt);
  }
  
  public static void setQProxyType(int paramInt)
  {
    nativeSetQProxyType(paramInt);
  }
  
  public static void setSPDYPreconnect() {}
  
  public static void startCheckSpdyProxy(String paramString)
  {
    nativeStartCheckSpdyProxy(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\AwNetworkUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */