package org.chromium.base.metrics;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base::android")
@MainDex
public class RecordHistogram
{
  private static Throwable jdField_a_of_type_JavaLangThrowable;
  private static Map<String, Long> jdField_a_of_type_JavaUtilMap = Collections.synchronizedMap(new HashMap());
  
  private static void a(String paramString, long paramLong1, long paramLong2, long paramLong3, int paramInt) {}
  
  @VisibleForTesting
  public static int getHistogramTotalCountForTesting(String paramString)
  {
    return 0;
  }
  
  @VisibleForTesting
  public static int getHistogramValueCountForTesting(String paramString, int paramInt)
  {
    return 0;
  }
  
  private static native int nativeGetHistogramTotalCountForTesting(String paramString);
  
  private static native int nativeGetHistogramValueCountForTesting(String paramString, int paramInt);
  
  private static native long nativeRecordBooleanHistogram(String paramString, long paramLong, boolean paramBoolean);
  
  private static native long nativeRecordCustomCountHistogram(String paramString, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  private static native long nativeRecordCustomTimesHistogramMilliseconds(String paramString, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  private static native long nativeRecordEnumeratedHistogram(String paramString, long paramLong, int paramInt1, int paramInt2);
  
  private static native long nativeRecordLinearCountHistogram(String paramString, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  private static native long nativeRecordSparseHistogram(String paramString, long paramLong, int paramInt);
  
  public static void recordBooleanHistogram(String paramString, boolean paramBoolean) {}
  
  public static void recordCount1000Histogram(String paramString, int paramInt)
  {
    recordCustomCountHistogram(paramString, paramInt, 1, 1000, 50);
  }
  
  public static void recordCount100Histogram(String paramString, int paramInt)
  {
    recordCustomCountHistogram(paramString, paramInt, 1, 100, 50);
  }
  
  public static void recordCountHistogram(String paramString, int paramInt)
  {
    recordCustomCountHistogram(paramString, paramInt, 1, 1000000, 50);
  }
  
  public static void recordCustomCountHistogram(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public static void recordCustomTimesHistogram(String paramString, long paramLong1, long paramLong2, long paramLong3, TimeUnit paramTimeUnit, int paramInt)
  {
    a(paramString, paramTimeUnit.toMillis(paramLong1), paramTimeUnit.toMillis(paramLong2), paramTimeUnit.toMillis(paramLong3), paramInt);
  }
  
  public static void recordEnumeratedHistogram(String paramString, int paramInt1, int paramInt2) {}
  
  public static void recordLinearCountHistogram(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public static void recordLongTimesHistogram(String paramString, long paramLong, TimeUnit paramTimeUnit)
  {
    a(paramString, paramTimeUnit.toMillis(paramLong), 1L, TimeUnit.HOURS.toMillis(1L), 50);
  }
  
  public static void recordLongTimesHistogram100(String paramString, long paramLong, TimeUnit paramTimeUnit)
  {
    a(paramString, paramTimeUnit.toMillis(paramLong), 1L, TimeUnit.HOURS.toMillis(1L), 100);
  }
  
  public static void recordMediumTimesHistogram(String paramString, long paramLong, TimeUnit paramTimeUnit)
  {
    a(paramString, paramTimeUnit.toMillis(paramLong), 10L, TimeUnit.MINUTES.toMillis(3L), 50);
  }
  
  public static void recordMemoryKBHistogram(String paramString, int paramInt)
  {
    recordCustomCountHistogram(paramString, paramInt, 1000, 500000, 50);
  }
  
  public static void recordPercentageHistogram(String paramString, int paramInt) {}
  
  public static void recordSparseSlowlyHistogram(String paramString, int paramInt) {}
  
  public static void recordTimesHistogram(String paramString, long paramLong, TimeUnit paramTimeUnit)
  {
    a(paramString, paramTimeUnit.toMillis(paramLong), 1L, TimeUnit.SECONDS.toMillis(10L), 50);
  }
  
  @VisibleForTesting
  public static void setDisabledForTests(boolean paramBoolean)
  {
    Throwable localThrowable;
    if (paramBoolean)
    {
      localThrowable = jdField_a_of_type_JavaLangThrowable;
      if (localThrowable != null) {
        throw new IllegalStateException("Histograms are already disabled.", localThrowable);
      }
    }
    if (paramBoolean) {
      localThrowable = new Throwable();
    } else {
      localThrowable = null;
    }
    jdField_a_of_type_JavaLangThrowable = localThrowable;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\metrics\RecordHistogram.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */