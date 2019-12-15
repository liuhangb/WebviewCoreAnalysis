package org.chromium.base;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("base::android")
public class EventLog
{
  @CalledByNative
  public static void writeEvent(int paramInt1, int paramInt2)
  {
    android.util.EventLog.writeEvent(paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\EventLog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */