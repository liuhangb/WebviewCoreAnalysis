package org.chromium.base;

import android.os.Looper;
import android.os.MessageQueue;
import android.os.MessageQueue.IdleHandler;
import android.os.SystemClock;
import android.util.Log;
import android.util.Printer;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base::android")
@MainDex
public class TraceEvent
  implements AutoCloseable
{
  private static volatile boolean sATraceEnabled;
  protected static volatile boolean sEnabled;
  private final String mName;
  
  protected TraceEvent(String paramString)
  {
    this.mName = paramString;
    begin(paramString);
  }
  
  public static void begin(String paramString)
  {
    begin(paramString, null);
  }
  
  public static void begin(String paramString1, String paramString2)
  {
    EarlyTraceEvent.begin(paramString1);
    if (sEnabled) {
      nativeBegin(paramString1, paramString2);
    }
  }
  
  public static boolean enabled()
  {
    return sEnabled;
  }
  
  public static void end(String paramString)
  {
    end(paramString, null);
  }
  
  public static void end(String paramString1, String paramString2)
  {
    EarlyTraceEvent.end(paramString1);
    if (sEnabled) {
      nativeEnd(paramString1, paramString2);
    }
  }
  
  public static void finishAsync(String paramString, long paramLong)
  {
    if (sEnabled) {
      nativeFinishAsync(paramString, paramLong);
    }
  }
  
  public static void instant(String paramString)
  {
    if (sEnabled) {
      nativeInstant(paramString, null);
    }
  }
  
  public static void instant(String paramString1, String paramString2)
  {
    if (sEnabled) {
      nativeInstant(paramString1, paramString2);
    }
  }
  
  public static void maybeEnableEarlyTracing()
  {
    
    if (EarlyTraceEvent.a()) {
      ThreadUtils.getUiThreadLooper().setMessageLogging(LooperMonitorHolder.a());
    }
  }
  
  private static native void nativeBegin(String paramString1, String paramString2);
  
  private static native void nativeBeginToplevel(String paramString);
  
  private static native void nativeEnd(String paramString1, String paramString2);
  
  private static native void nativeEndToplevel();
  
  private static native void nativeFinishAsync(String paramString, long paramLong);
  
  private static native void nativeInstant(String paramString1, String paramString2);
  
  private static native void nativeRegisterEnabledObserver();
  
  private static native void nativeStartATrace();
  
  private static native void nativeStartAsync(String paramString, long paramLong);
  
  private static native void nativeStopATrace();
  
  public static void registerNativeEnabledObserver() {}
  
  public static TraceEvent scoped(String paramString)
  {
    if ((!EarlyTraceEvent.b()) && (!enabled())) {
      return null;
    }
    return new TraceEvent(paramString);
  }
  
  public static void setATraceEnabled(boolean paramBoolean)
  {
    if (sATraceEnabled == paramBoolean) {
      return;
    }
    sATraceEnabled = paramBoolean;
    if (paramBoolean)
    {
      nativeStartATrace();
      return;
    }
    nativeStopATrace();
  }
  
  @CalledByNative
  public static void setEnabled(boolean paramBoolean)
  {
    if (paramBoolean) {
      EarlyTraceEvent.c();
    }
    if (sEnabled != paramBoolean)
    {
      sEnabled = paramBoolean;
      if (sATraceEnabled) {
        return;
      }
      Looper localLooper = ThreadUtils.getUiThreadLooper();
      BasicLooperMonitor localBasicLooperMonitor;
      if (paramBoolean) {
        localBasicLooperMonitor = LooperMonitorHolder.a();
      } else {
        localBasicLooperMonitor = null;
      }
      localLooper.setMessageLogging(localBasicLooperMonitor);
    }
  }
  
  public static void startAsync(String paramString, long paramLong)
  {
    if (sEnabled) {
      nativeStartAsync(paramString, paramLong);
    }
  }
  
  public void close()
  {
    end(this.mName);
  }
  
  private static class BasicLooperMonitor
    implements Printer
  {
    private static String a(String paramString)
    {
      int j = paramString.indexOf('(', 21);
      int i;
      if (j == -1) {
        i = -1;
      } else {
        i = paramString.indexOf(')', j);
      }
      if (i != -1) {
        return paramString.substring(j + 1, i);
      }
      return "";
    }
    
    void beginHandling(String paramString)
    {
      boolean bool = EarlyTraceEvent.a();
      if ((TraceEvent.sEnabled) || (bool))
      {
        paramString = a(paramString);
        if (TraceEvent.sEnabled)
        {
          TraceEvent.nativeBeginToplevel(paramString);
          return;
        }
        if (bool)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("Looper.dispatchMessage: ");
          localStringBuilder.append(paramString);
          EarlyTraceEvent.begin(localStringBuilder.toString());
        }
      }
    }
    
    void endHandling(String paramString)
    {
      if (EarlyTraceEvent.a())
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Looper.dispatchMessage: ");
        localStringBuilder.append(a(paramString));
        EarlyTraceEvent.end(localStringBuilder.toString());
      }
      if (TraceEvent.sEnabled) {
        TraceEvent.access$100();
      }
    }
    
    public void println(String paramString)
    {
      if (paramString.startsWith(">"))
      {
        beginHandling(paramString);
        return;
      }
      if ((!a) && (!paramString.startsWith("<"))) {
        throw new AssertionError();
      }
      endHandling(paramString);
    }
  }
  
  private static final class IdleTracingLooperMonitor
    extends TraceEvent.BasicLooperMonitor
    implements MessageQueue.IdleHandler
  {
    private int jdField_a_of_type_Int;
    private long jdField_a_of_type_Long;
    private int jdField_b_of_type_Int;
    private long jdField_b_of_type_Long;
    private boolean jdField_b_of_type_Boolean;
    private int c;
    
    private IdleTracingLooperMonitor()
    {
      super();
    }
    
    private static void a(int paramInt, String paramString)
    {
      TraceEvent.instant("TraceEvent.LooperMonitor:IdleStats", paramString);
      Log.println(paramInt, "TraceEvent.LooperMonitor", paramString);
    }
    
    private final void syncIdleMonitoring()
    {
      if ((TraceEvent.sEnabled) && (!this.jdField_b_of_type_Boolean))
      {
        this.jdField_a_of_type_Long = SystemClock.elapsedRealtime();
        Looper.myQueue().addIdleHandler(this);
        this.jdField_b_of_type_Boolean = true;
        return;
      }
      if ((this.jdField_b_of_type_Boolean) && (!TraceEvent.sEnabled))
      {
        Looper.myQueue().removeIdleHandler(this);
        this.jdField_b_of_type_Boolean = false;
      }
    }
    
    final void beginHandling(String paramString)
    {
      if (this.c == 0) {
        TraceEvent.end("Looper.queueIdle");
      }
      this.jdField_b_of_type_Long = SystemClock.elapsedRealtime();
      syncIdleMonitoring();
      super.beginHandling(paramString);
    }
    
    final void endHandling(String paramString)
    {
      long l = SystemClock.elapsedRealtime() - this.jdField_b_of_type_Long;
      if (l > 16L)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("observed a task that took ");
        localStringBuilder.append(l);
        localStringBuilder.append("ms: ");
        localStringBuilder.append(paramString);
        a(5, localStringBuilder.toString());
      }
      super.endHandling(paramString);
      syncIdleMonitoring();
      this.jdField_a_of_type_Int += 1;
      this.c += 1;
    }
    
    public final boolean queueIdle()
    {
      long l1 = SystemClock.elapsedRealtime();
      if (this.jdField_a_of_type_Long == 0L) {
        this.jdField_a_of_type_Long = l1;
      }
      long l2 = l1 - this.jdField_a_of_type_Long;
      this.jdField_b_of_type_Int += 1;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.c);
      localStringBuilder.append(" tasks since last idle.");
      TraceEvent.begin("Looper.queueIdle", localStringBuilder.toString());
      if (l2 > 48L)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append(this.jdField_a_of_type_Int);
        localStringBuilder.append(" tasks and ");
        localStringBuilder.append(this.jdField_b_of_type_Int);
        localStringBuilder.append(" idles processed so far, ");
        localStringBuilder.append(this.c);
        localStringBuilder.append(" tasks bursted and ");
        localStringBuilder.append(l2);
        localStringBuilder.append("ms elapsed since last idle");
        a(3, localStringBuilder.toString());
      }
      this.jdField_a_of_type_Long = l1;
      this.c = 0;
      return true;
    }
  }
  
  private static final class LooperMonitorHolder
  {
    private static final TraceEvent.BasicLooperMonitor a;
    
    static
    {
      Object localObject;
      if (CommandLine.getInstance().hasSwitch("enable-idle-tracing")) {
        localObject = new TraceEvent.IdleTracingLooperMonitor(null);
      } else {
        localObject = new TraceEvent.BasicLooperMonitor(null);
      }
      a = (TraceEvent.BasicLooperMonitor)localObject;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\TraceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */