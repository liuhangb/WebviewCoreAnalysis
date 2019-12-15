package org.chromium.base;

import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import java.util.TimeZone;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base::android")
@MainDex
class TimezoneUtils
{
  @CalledByNative
  private static String getDefaultTimeZoneId()
  {
    StrictMode.ThreadPolicy localThreadPolicy = StrictMode.allowThreadDiskReads();
    String str = TimeZone.getDefault().getID();
    StrictMode.setThreadPolicy(localThreadPolicy);
    return str;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\TimezoneUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */