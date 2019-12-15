package com.tencent.smtt.aladdin;

import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.HashMap;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("aladdin")
public class AladdinReporter
{
  private static String a = "";
  private static String b = "";
  
  public static void a(String paramString)
  {
    a = paramString;
  }
  
  @CalledByNative
  public static void initGpu(String paramString)
  {
    b = paramString;
  }
  
  @CalledByNative
  public static void report(String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("url", a);
    localHashMap.put("gpu", b);
    localHashMap.put("type", paramString1);
    localHashMap.put("msg", paramString2);
    SmttServiceProxy.getInstance().upLoadToBeacon("JR_INFO", localHashMap);
  }
  
  @CalledByNative
  public static void reportPerf(int paramInt1, int paramInt2, int paramInt3, long paramLong)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("url", a);
    localHashMap.put("gpu", b);
    localHashMap.put("minfps", String.valueOf(paramInt1));
    localHashMap.put("maxfps", String.valueOf(paramInt2));
    localHashMap.put("avgfps", String.valueOf(paramInt3));
    localHashMap.put("usetime", String.valueOf(paramLong));
    SmttServiceProxy.getInstance().upLoadToBeacon("JR_PERF", localHashMap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\aladdin\AladdinReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */