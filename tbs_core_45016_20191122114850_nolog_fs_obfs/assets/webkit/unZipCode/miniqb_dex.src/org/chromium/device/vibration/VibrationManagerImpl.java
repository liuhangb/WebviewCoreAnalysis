package org.chromium.device.vibration;

import android.content.Context;
import android.media.AudioManager;
import android.os.Vibrator;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.device.mojom.VibrationManager;
import org.chromium.device.mojom.VibrationManager.CancelResponse;
import org.chromium.device.mojom.VibrationManager.VibrateResponse;
import org.chromium.mojo.system.MojoException;
import org.chromium.services.service_manager.InterfaceFactory;

@JNINamespace("device")
public class VibrationManagerImpl
  implements VibrationManager
{
  private static long jdField_a_of_type_Long = -1L;
  private static boolean b = false;
  private final AudioManager jdField_a_of_type_AndroidMediaAudioManager;
  private final Vibrator jdField_a_of_type_AndroidOsVibrator;
  private final boolean jdField_a_of_type_Boolean;
  
  public VibrationManagerImpl()
  {
    Context localContext = ContextUtils.getApplicationContext();
    this.jdField_a_of_type_AndroidMediaAudioManager = ((AudioManager)localContext.getSystemService("audio"));
    this.jdField_a_of_type_AndroidOsVibrator = ((Vibrator)localContext.getSystemService("vibrator"));
    if (localContext.checkCallingOrSelfPermission("android.permission.VIBRATE") == 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.jdField_a_of_type_Boolean = bool;
    boolean bool = this.jdField_a_of_type_Boolean;
  }
  
  static void a(long paramLong)
  {
    jdField_a_of_type_Long = paramLong;
  }
  
  static void a(boolean paramBoolean)
  {
    b = paramBoolean;
  }
  
  @CalledByNative
  static boolean getVibrateCancelledForTesting()
  {
    return b;
  }
  
  @CalledByNative
  static long getVibrateMilliSecondsForTesting()
  {
    return jdField_a_of_type_Long;
  }
  
  public void cancel(VibrationManager.CancelResponse paramCancelResponse)
  {
    if (this.jdField_a_of_type_Boolean) {
      this.jdField_a_of_type_AndroidOsVibrator.cancel();
    }
    a(true);
    paramCancelResponse.call();
  }
  
  public void close() {}
  
  public void onConnectionError(MojoException paramMojoException) {}
  
  public void vibrate(long paramLong, VibrationManager.VibrateResponse paramVibrateResponse)
  {
    paramLong = Math.max(1L, Math.min(paramLong, 10000L));
    if ((this.jdField_a_of_type_AndroidMediaAudioManager.getRingerMode() != 0) && (this.jdField_a_of_type_Boolean)) {
      this.jdField_a_of_type_AndroidOsVibrator.vibrate(paramLong);
    }
    a(paramLong);
    paramVibrateResponse.call();
  }
  
  public static class Factory
    implements InterfaceFactory<VibrationManager>
  {
    public VibrationManager a()
    {
      return new VibrationManagerImpl();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\vibration\VibrationManagerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */