package org.chromium.base;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.metrics.CachedMetrics.BooleanHistogramSample;
import org.chromium.tencent.utils.TencentDeviceUtils;

@JNINamespace("base::android")
public class SysUtils
{
  private static Boolean jdField_a_of_type_JavaLangBoolean;
  private static Integer jdField_a_of_type_JavaLangInteger;
  private static CachedMetrics.BooleanHistogramSample jdField_a_of_type_OrgChromiumBaseMetricsCachedMetrics$BooleanHistogramSample = new CachedMetrics.BooleanHistogramSample("Android.SysUtilsLowEndMatches");
  
  @CalledByNative
  public static String OsName()
  {
    return TencentDeviceUtils.OsName();
  }
  
  /* Error */
  private static int a()
  {
    // Byte code:
    //   0: ldc 48
    //   2: invokestatic 54	java/util/regex/Pattern:compile	(Ljava/lang/String;)Ljava/util/regex/Pattern;
    //   5: astore 4
    //   7: invokestatic 60	android/os/StrictMode:allowThreadDiskReads	()Landroid/os/StrictMode$ThreadPolicy;
    //   10: astore_1
    //   11: new 62	java/io/FileReader
    //   14: dup
    //   15: ldc 64
    //   17: invokespecial 65	java/io/FileReader:<init>	(Ljava/lang/String;)V
    //   20: astore_2
    //   21: new 67	java/io/BufferedReader
    //   24: dup
    //   25: aload_2
    //   26: invokespecial 70	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
    //   29: astore_3
    //   30: aload_3
    //   31: invokevirtual 73	java/io/BufferedReader:readLine	()Ljava/lang/String;
    //   34: astore 5
    //   36: aload 5
    //   38: ifnonnull +6 -> 44
    //   41: goto +40 -> 81
    //   44: aload 4
    //   46: aload 5
    //   48: invokevirtual 77	java/util/regex/Pattern:matcher	(Ljava/lang/CharSequence;)Ljava/util/regex/Matcher;
    //   51: astore 5
    //   53: aload 5
    //   55: invokevirtual 82	java/util/regex/Matcher:find	()Z
    //   58: ifne +6 -> 64
    //   61: goto -31 -> 30
    //   64: aload 5
    //   66: iconst_1
    //   67: invokevirtual 86	java/util/regex/Matcher:group	(I)Ljava/lang/String;
    //   70: invokestatic 92	java/lang/Integer:parseInt	(Ljava/lang/String;)I
    //   73: istore_0
    //   74: iload_0
    //   75: sipush 1024
    //   78: if_icmpgt +14 -> 92
    //   81: aload_3
    //   82: invokevirtual 95	java/io/BufferedReader:close	()V
    //   85: aload_2
    //   86: invokevirtual 96	java/io/FileReader:close	()V
    //   89: goto +40 -> 129
    //   92: aload_3
    //   93: invokevirtual 95	java/io/BufferedReader:close	()V
    //   96: aload_2
    //   97: invokevirtual 96	java/io/FileReader:close	()V
    //   100: aload_1
    //   101: invokestatic 100	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   104: iload_0
    //   105: ireturn
    //   106: astore 4
    //   108: aload_3
    //   109: invokevirtual 95	java/io/BufferedReader:close	()V
    //   112: aload 4
    //   114: athrow
    //   115: astore_3
    //   116: aload_2
    //   117: invokevirtual 96	java/io/FileReader:close	()V
    //   120: aload_3
    //   121: athrow
    //   122: astore_2
    //   123: aload_1
    //   124: invokestatic 100	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   127: aload_2
    //   128: athrow
    //   129: aload_1
    //   130: invokestatic 100	android/os/StrictMode:setThreadPolicy	(Landroid/os/StrictMode$ThreadPolicy;)V
    //   133: iconst_0
    //   134: ireturn
    //   135: astore_2
    //   136: goto -7 -> 129
    // Local variable table:
    //   start	length	slot	name	signature
    //   73	32	0	i	int
    //   10	120	1	localThreadPolicy	android.os.StrictMode.ThreadPolicy
    //   20	97	2	localFileReader	java.io.FileReader
    //   122	6	2	localObject1	Object
    //   135	1	2	localException	Exception
    //   29	80	3	localBufferedReader	java.io.BufferedReader
    //   115	6	3	localObject2	Object
    //   5	40	4	localPattern	java.util.regex.Pattern
    //   106	7	4	localObject3	Object
    //   34	31	5	localObject4	Object
    // Exception table:
    //   from	to	target	type
    //   30	36	106	finally
    //   44	61	106	finally
    //   64	74	106	finally
    //   21	30	115	finally
    //   81	85	115	finally
    //   92	96	115	finally
    //   108	115	115	finally
    //   11	21	122	finally
    //   85	89	122	finally
    //   96	100	122	finally
    //   116	122	122	finally
    //   11	21	135	java/lang/Exception
    //   85	89	135	java/lang/Exception
    //   96	100	135	java/lang/Exception
    //   116	122	135	java/lang/Exception
  }
  
  @TargetApi(19)
  private static boolean a()
  {
    if ((!jdField_a_of_type_Boolean) && (!CommandLine.isInitialized())) {
      throw new AssertionError();
    }
    boolean bool1 = CommandLine.getInstance().hasSwitch("enable-low-end-device-mode");
    boolean bool3 = true;
    if (bool1) {
      return true;
    }
    if (CommandLine.getInstance().hasSwitch("disable-low-end-device-mode")) {
      return false;
    }
    jdField_a_of_type_JavaLangInteger = Integer.valueOf(a());
    if (jdField_a_of_type_JavaLangInteger.intValue() <= 0) {
      bool1 = false;
    } else if (Build.VERSION.SDK_INT >= 26)
    {
      if (jdField_a_of_type_JavaLangInteger.intValue() / 1024 <= 1024) {
        bool1 = true;
      } else {
        bool1 = false;
      }
    }
    else if (jdField_a_of_type_JavaLangInteger.intValue() / 1024 <= 512) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    boolean bool2;
    if ((ContextUtils.getApplicationContext() != null) && (Build.VERSION.SDK_INT >= 19)) {
      bool2 = ((ActivityManager)ContextUtils.getApplicationContext().getSystemService("activity")).isLowRamDevice();
    } else {
      bool2 = false;
    }
    CachedMetrics.BooleanHistogramSample localBooleanHistogramSample = jdField_a_of_type_OrgChromiumBaseMetricsCachedMetrics$BooleanHistogramSample;
    if (bool1 == bool2) {
      bool2 = bool3;
    } else {
      bool2 = false;
    }
    localBooleanHistogramSample.record(bool2);
    return bool1;
  }
  
  public static int amountOfPhysicalMemoryKB()
  {
    if (jdField_a_of_type_JavaLangInteger == null) {
      jdField_a_of_type_JavaLangInteger = Integer.valueOf(a());
    }
    return jdField_a_of_type_JavaLangInteger.intValue();
  }
  
  public static boolean hasCamera(Context paramContext)
  {
    paramContext = paramContext.getPackageManager();
    boolean bool2 = paramContext.hasSystemFeature("android.hardware.camera");
    boolean bool1 = bool2;
    if (Build.VERSION.SDK_INT >= 17) {
      bool1 = bool2 | paramContext.hasSystemFeature("android.hardware.camera.any");
    }
    return bool1;
  }
  
  @CalledByNative
  public static boolean isCurrentlyLowMemory()
  {
    ActivityManager localActivityManager = (ActivityManager)ContextUtils.getApplicationContext().getSystemService("activity");
    ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
    localActivityManager.getMemoryInfo(localMemoryInfo);
    return localMemoryInfo.lowMemory;
  }
  
  @CalledByNative
  public static boolean isLowEndDevice()
  {
    if (jdField_a_of_type_JavaLangBoolean == null) {
      jdField_a_of_type_JavaLangBoolean = Boolean.valueOf(a());
    }
    return jdField_a_of_type_JavaLangBoolean.booleanValue();
  }
  
  public static void logPageFaultCountToTracing() {}
  
  private static native void nativeLogPageFaultCountToTracing();
  
  @VisibleForTesting
  public static void resetForTesting()
  {
    jdField_a_of_type_JavaLangBoolean = null;
    jdField_a_of_type_JavaLangInteger = null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\SysUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */