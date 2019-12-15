package org.chromium.net;

import android.net.TrafficStats;
import android.os.Process;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("net::android::traffic_stats")
public class AndroidTrafficStats
{
  @CalledByNative
  private static long getCurrentUidRxBytes()
  {
    long l = TrafficStats.getUidRxBytes(Process.myUid());
    if (l != -1L) {
      return l;
    }
    return 0L;
  }
  
  @CalledByNative
  private static long getCurrentUidTxBytes()
  {
    long l = TrafficStats.getUidTxBytes(Process.myUid());
    if (l != -1L) {
      return l;
    }
    return 0L;
  }
  
  @CalledByNative
  private static long getTotalRxBytes()
  {
    long l = TrafficStats.getTotalRxBytes();
    if (l != -1L) {
      return l;
    }
    return 0L;
  }
  
  @CalledByNative
  private static long getTotalTxBytes()
  {
    long l = TrafficStats.getTotalTxBytes();
    if (l != -1L) {
      return l;
    }
    return 0L;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\AndroidTrafficStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */