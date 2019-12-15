package com.tencent.smtt.webkit;

import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.HashMap;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class V8Reporter
{
  @CalledByNative
  public static void reportInfo(String paramString1, String paramString2)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("info", paramString1);
    localHashMap.put("type", paramString2);
    SmttServiceProxy.getInstance().upLoadToBeacon("MTT_V8_WASM", localHashMap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\V8Reporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */