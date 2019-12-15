package org.chromium.net;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.telephony.PhoneStateListener;
import android.telephony.SignalStrength;
import android.telephony.TelephonyManager;
import org.chromium.base.ApplicationStatus;
import org.chromium.base.ApplicationStatus.ApplicationStateListener;
import org.chromium.base.ContextUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("net::android")
public class AndroidCellularSignalStrength
{
  private static final AndroidCellularSignalStrength jdField_a_of_type_OrgChromiumNetAndroidCellularSignalStrength = new AndroidCellularSignalStrength();
  private volatile int jdField_a_of_type_Int = Integer.MIN_VALUE;
  
  private AndroidCellularSignalStrength()
  {
    if (Build.VERSION.SDK_INT < 23) {
      return;
    }
    HandlerThread localHandlerThread = new HandlerThread("AndroidCellularSignalStrength");
    localHandlerThread.start();
    new Handler(localHandlerThread.getLooper()).post(new Runnable()
    {
      public void run()
      {
        new AndroidCellularSignalStrength.CellStateListener(AndroidCellularSignalStrength.this);
      }
    });
  }
  
  @TargetApi(23)
  @CalledByNative
  private static int getSignalStrengthLevel()
  {
    return jdField_a_of_type_OrgChromiumNetAndroidCellularSignalStrength.jdField_a_of_type_Int;
  }
  
  private class CellStateListener
    extends PhoneStateListener
    implements ApplicationStatus.ApplicationStateListener
  {
    private final TelephonyManager jdField_a_of_type_AndroidTelephonyTelephonyManager;
    
    CellStateListener()
    {
      ThreadUtils.assertOnBackgroundThread();
      this.jdField_a_of_type_AndroidTelephonyTelephonyManager = ((TelephonyManager)ContextUtils.getApplicationContext().getSystemService("phone"));
      if (this.jdField_a_of_type_AndroidTelephonyTelephonyManager.getSimState() != 5) {
        return;
      }
      ApplicationStatus.registerApplicationStateListener(this);
      onApplicationStateChange(ApplicationStatus.getStateForApplication());
    }
    
    private void a()
    {
      this.jdField_a_of_type_AndroidTelephonyTelephonyManager.listen(this, 256);
    }
    
    private void b()
    {
      AndroidCellularSignalStrength.a(AndroidCellularSignalStrength.this, Integer.MIN_VALUE);
      this.jdField_a_of_type_AndroidTelephonyTelephonyManager.listen(this, 0);
    }
    
    public void onApplicationStateChange(int paramInt)
    {
      if (paramInt == 1)
      {
        a();
        return;
      }
      if (paramInt == 2) {
        b();
      }
    }
    
    @TargetApi(23)
    public void onSignalStrengthsChanged(SignalStrength paramSignalStrength)
    {
      if (ApplicationStatus.getStateForApplication() != 1) {
        return;
      }
      try
      {
        AndroidCellularSignalStrength.a(AndroidCellularSignalStrength.this, paramSignalStrength.getLevel());
        return;
      }
      catch (SecurityException paramSignalStrength)
      {
        for (;;) {}
      }
      AndroidCellularSignalStrength.a(AndroidCellularSignalStrength.this, Integer.MIN_VALUE);
      if (jdField_a_of_type_Boolean) {
        return;
      }
      throw new AssertionError();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\AndroidCellularSignalStrength.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */