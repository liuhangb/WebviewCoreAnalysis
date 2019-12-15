package com.tencent.smtt.jscore;

import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.HashMap;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("jscore")
public class X5JsCoreReporter
{
  private static String sPackageName = "";
  
  @CalledByNative
  public static void reportError(int paramInt, String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("app", sPackageName);
    localHashMap.put("waid", e.a().a());
    localHashMap.put("wapp", e.a().b());
    localHashMap.put("err", String.valueOf(paramInt));
    localHashMap.put("md5", paramString1);
    localHashMap.put("sum", paramString2);
    SmttServiceProxy.getInstance().upLoadToBeacon("JSCORE_ERR", localHashMap);
  }
  
  @CalledByNative
  public static void reportFatalError(String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("app", sPackageName);
    localHashMap.put("waid", e.a().a());
    localHashMap.put("wapp", e.a().b());
    localHashMap.put("loc", paramString1);
    localHashMap.put("msg", paramString2);
    SmttServiceProxy.getInstance().upLoadToBeacon("JSCORE_FATAL", localHashMap);
  }
  
  @CalledByNative
  public static void reportMessage(String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("app", sPackageName);
    localHashMap.put("waid", e.a().a());
    localHashMap.put("wapp", e.a().b());
    localHashMap.put("msg", paramString1);
    localHashMap.put("stk", paramString2);
    SmttServiceProxy.getInstance().upLoadToBeacon("JSCORE_MSG", localHashMap);
  }
  
  @CalledByNative
  public static void reportPerformance(String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("app", sPackageName);
    localHashMap.put("waid", e.a().a());
    localHashMap.put("wapp", e.a().b());
    localHashMap.put("info", paramString);
    SmttServiceProxy.getInstance().upLoadToBeacon("JSCORE_PERF", localHashMap);
  }
  
  public static void setPackageName(String paramString)
  {
    sPackageName = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\X5JsCoreReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */