package org.chromium.tencent;

import org.chromium.base.TraceEvent;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("base::android::tencent")
public class TencentTraceEvent
  extends TraceEvent
{
  private static boolean sRecordingTrace = false;
  
  private TencentTraceEvent(String paramString)
  {
    super(paramString);
  }
  
  public static void begin()
  {
    if (sEnabled) {
      begin(getCallerName(), null);
    }
  }
  
  public static void end()
  {
    if (sEnabled) {
      end(getCallerName(), null);
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
  
  public static boolean isRecordingTrace()
  {
    return sRecordingTrace;
  }
  
  private static native void nativeStartRecoderTrace(String paramString);
  
  private static native void nativeStopRecoderTrace(String paramString);
  
  public static void recoderTrace(String paramString)
  {
    nativeStartRecoderTrace(paramString);
    sRecordingTrace = true;
  }
  
  public static void stopTrace(String paramString)
  {
    nativeStopRecoderTrace(paramString);
    sRecordingTrace = false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentTraceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */