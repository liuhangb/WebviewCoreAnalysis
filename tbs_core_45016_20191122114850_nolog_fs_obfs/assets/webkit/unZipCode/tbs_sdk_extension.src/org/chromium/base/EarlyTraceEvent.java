package org.chromium.base;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Process;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.os.SystemClock;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base::android")
@MainDex
public class EarlyTraceEvent
{
  @VisibleForTesting
  static volatile int jdField_a_of_type_Int = 0;
  private static final Object jdField_a_of_type_JavaLangObject = new Object();
  @VisibleForTesting
  static List<Event> jdField_a_of_type_JavaUtilList;
  @VisibleForTesting
  static Map<String, Event> jdField_a_of_type_JavaUtilMap;
  
  static void a()
  {
    ThreadUtils.assertOnUiThread();
    localThreadPolicy = StrictMode.allowThreadDiskReads();
    for (;;)
    {
      try
      {
        bool = CommandLine.getInstance().hasSwitch("trace-startup");
        if (bool) {
          bool = true;
        }
      }
      finally
      {
        boolean bool;
        StrictMode.setThreadPolicy(localThreadPolicy);
      }
      try
      {
        bool = new File("/data/local/chrome-trace-config.json").exists();
      }
      catch (SecurityException localSecurityException) {}
    }
    bool = false;
    StrictMode.setThreadPolicy(localThreadPolicy);
    if (bool) {
      b();
    }
  }
  
  private static void a(List<Event> paramList)
  {
    long l = TimeUtils.nativeGetTimeTicksNowUs() * 1000L - Event.a();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      Event localEvent = (Event)paramList.next();
      nativeRecordEarlyEvent(localEvent.jdField_a_of_type_JavaLangString, localEvent.jdField_a_of_type_Long + l, localEvent.c + l, localEvent.jdField_a_of_type_Int, localEvent.d - localEvent.b);
    }
  }
  
  static boolean a()
  {
    int i = jdField_a_of_type_Int;
    boolean bool = true;
    if (i != 1)
    {
      if (i == 2) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  @VisibleForTesting
  static void b()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (jdField_a_of_type_Int != 0) {
        return;
      }
      jdField_a_of_type_JavaUtilList = new ArrayList();
      jdField_a_of_type_JavaUtilMap = new HashMap();
      jdField_a_of_type_Int = 1;
      return;
    }
  }
  
  static boolean b()
  {
    return jdField_a_of_type_Int == 1;
  }
  
  public static void begin(String paramString)
  {
    if (!b()) {
      return;
    }
    Event localEvent = new Event(paramString);
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (!b()) {
        return;
      }
      paramString = (Event)jdField_a_of_type_JavaUtilMap.put(paramString, localEvent);
      if (paramString == null) {
        return;
      }
      throw new IllegalArgumentException("Multiple pending trace events can't have the same name");
    }
  }
  
  static void c()
  {
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (!b()) {
        return;
      }
      jdField_a_of_type_Int = 2;
      d();
      return;
    }
  }
  
  private static void d()
  {
    if (!jdField_a_of_type_JavaUtilList.isEmpty())
    {
      a(jdField_a_of_type_JavaUtilList);
      jdField_a_of_type_JavaUtilList.clear();
    }
    if (jdField_a_of_type_JavaUtilMap.isEmpty())
    {
      jdField_a_of_type_Int = 3;
      jdField_a_of_type_JavaUtilMap = null;
      jdField_a_of_type_JavaUtilList = null;
    }
  }
  
  public static void end(String paramString)
  {
    if (!a()) {
      return;
    }
    synchronized (jdField_a_of_type_JavaLangObject)
    {
      if (!a()) {
        return;
      }
      paramString = (Event)jdField_a_of_type_JavaUtilMap.remove(paramString);
      if (paramString == null) {
        return;
      }
      paramString.a();
      jdField_a_of_type_JavaUtilList.add(paramString);
      if (jdField_a_of_type_Int == 2) {
        d();
      }
      return;
    }
  }
  
  private static native void nativeRecordEarlyEvent(String paramString, long paramLong1, long paramLong2, int paramInt, long paramLong3);
  
  @VisibleForTesting
  static final class Event
  {
    final int jdField_a_of_type_Int;
    final long jdField_a_of_type_Long;
    final String jdField_a_of_type_JavaLangString;
    final long b;
    long c;
    long d;
    
    Event(String paramString)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_Int = Process.myTid();
      this.jdField_a_of_type_Long = a();
      this.b = SystemClock.currentThreadTimeMillis();
    }
    
    @SuppressLint({"NewApi"})
    @VisibleForTesting
    static long a()
    {
      if (Build.VERSION.SDK_INT >= 17) {
        return SystemClock.elapsedRealtimeNanos();
      }
      return SystemClock.elapsedRealtime() * 1000000L;
    }
    
    void a()
    {
      if ((!jdField_a_of_type_Boolean) && (this.c != 0L)) {
        throw new AssertionError();
      }
      if ((!jdField_a_of_type_Boolean) && (this.d != 0L)) {
        throw new AssertionError();
      }
      this.c = a();
      this.d = SystemClock.currentThreadTimeMillis();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\EarlyTraceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */