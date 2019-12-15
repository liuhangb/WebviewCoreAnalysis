package org.chromium.device.gamepad;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.InputDevice;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("content")
abstract class GamepadMappings
{
  @VisibleForTesting
  static float a(float paramFloat)
  {
    if (paramFloat < -0.5F) {
      return 1.0F;
    }
    return 0.0F;
  }
  
  @TargetApi(19)
  @VisibleForTesting
  static GamepadMappings a(int paramInt1, int paramInt2)
  {
    if ((paramInt2 == 1356) && ((paramInt1 == 1476) || (paramInt1 == 2508) || (paramInt1 == 2976))) {
      return new PS4GamepadMappings();
    }
    if ((paramInt2 == 1118) && (paramInt1 == 736)) {
      return new XboxOneS2016FirmwareMappings(null);
    }
    return null;
  }
  
  public static GamepadMappings a(InputDevice paramInputDevice, int[] paramArrayOfInt)
  {
    GamepadMappings localGamepadMappings2;
    if (Build.VERSION.SDK_INT >= 19) {
      localGamepadMappings2 = a(paramInputDevice.getProductId(), paramInputDevice.getVendorId());
    } else {
      localGamepadMappings2 = null;
    }
    GamepadMappings localGamepadMappings1 = localGamepadMappings2;
    if (localGamepadMappings2 == null) {
      localGamepadMappings1 = a(paramInputDevice.getName());
    }
    paramInputDevice = localGamepadMappings1;
    if (localGamepadMappings1 == null) {
      paramInputDevice = new UnknownGamepadMappings(paramArrayOfInt);
    }
    return paramInputDevice;
  }
  
  @VisibleForTesting
  static GamepadMappings a(String paramString)
  {
    if ((!paramString.startsWith("NVIDIA Corporation NVIDIA Controller")) && (!paramString.equals("Microsoft X-Box 360 pad")))
    {
      if (paramString.equals("Sony PLAYSTATION(R)3 Controller")) {
        return new PS3SixAxisGamepadMappings(null);
      }
      if (paramString.equals("Samsung Game Pad EI-GP20")) {
        return new SamsungEIGP20GamepadMappings(null);
      }
      if (paramString.equals("Amazon Fire Game Controller")) {
        return new AmazonFireGamepadMappings(null);
      }
      return null;
    }
    return new XboxCompatibleGamepadMappings(null);
  }
  
  @VisibleForTesting
  static float b(float paramFloat)
  {
    if (paramFloat > 0.5F) {
      return 1.0F;
    }
    return 0.0F;
  }
  
  private static void n(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f1 = paramArrayOfFloat2[96];
    float f2 = paramArrayOfFloat2[97];
    float f3 = paramArrayOfFloat2[99];
    float f4 = paramArrayOfFloat2[100];
    paramArrayOfFloat1[0] = f1;
    paramArrayOfFloat1[1] = f2;
    paramArrayOfFloat1[2] = f3;
    paramArrayOfFloat1[3] = f4;
  }
  
  private static void o(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f1 = paramArrayOfFloat2[108];
    float f2 = paramArrayOfFloat2[109];
    float f3 = paramArrayOfFloat2[110];
    paramArrayOfFloat1[9] = f1;
    paramArrayOfFloat1[8] = f2;
    paramArrayOfFloat1[16] = f3;
  }
  
  private static void p(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f1 = paramArrayOfFloat2[106];
    float f2 = paramArrayOfFloat2[107];
    paramArrayOfFloat1[10] = f1;
    paramArrayOfFloat1[11] = f2;
  }
  
  private static void q(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f1 = paramArrayOfFloat2[102];
    float f2 = paramArrayOfFloat2[103];
    paramArrayOfFloat1[6] = f1;
    paramArrayOfFloat1[7] = f2;
  }
  
  private static void r(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f1 = paramArrayOfFloat2[102];
    float f2 = paramArrayOfFloat2[103];
    paramArrayOfFloat1[4] = f1;
    paramArrayOfFloat1[5] = f2;
  }
  
  private static void s(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f1 = paramArrayOfFloat2[20];
    float f2 = paramArrayOfFloat2[19];
    float f3 = paramArrayOfFloat2[21];
    float f4 = paramArrayOfFloat2[22];
    paramArrayOfFloat1[13] = f1;
    paramArrayOfFloat1[12] = f2;
    paramArrayOfFloat1[14] = f3;
    paramArrayOfFloat1[15] = f4;
  }
  
  private static void t(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    paramArrayOfFloat1[0] = paramArrayOfFloat2[0];
    paramArrayOfFloat1[1] = paramArrayOfFloat2[1];
  }
  
  private static void u(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    paramArrayOfFloat1[2] = paramArrayOfFloat2[12];
    paramArrayOfFloat1[3] = paramArrayOfFloat2[13];
  }
  
  private static void v(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    paramArrayOfFloat1[2] = paramArrayOfFloat2[11];
    paramArrayOfFloat1[3] = paramArrayOfFloat2[14];
  }
  
  private static void w(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f1 = paramArrayOfFloat2[23];
    float f2 = paramArrayOfFloat2[22];
    paramArrayOfFloat1[6] = f1;
    paramArrayOfFloat1[7] = f2;
  }
  
  private static void x(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f1 = paramArrayOfFloat2[17];
    float f2 = paramArrayOfFloat2[18];
    paramArrayOfFloat1[6] = f1;
    paramArrayOfFloat1[7] = f2;
  }
  
  private static void y(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f1 = paramArrayOfFloat2[104];
    float f2 = paramArrayOfFloat2[105];
    paramArrayOfFloat1[6] = f1;
    paramArrayOfFloat1[7] = f2;
  }
  
  private static void z(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2)
  {
    float f1 = paramArrayOfFloat2[15];
    float f2 = paramArrayOfFloat2[16];
    paramArrayOfFloat1[14] = a(f1);
    paramArrayOfFloat1[15] = b(f1);
    paramArrayOfFloat1[12] = a(f2);
    paramArrayOfFloat1[13] = b(f2);
  }
  
  public boolean a()
  {
    return true;
  }
  
  public abstract void mapToStandardGamepad(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3, float[] paramArrayOfFloat4);
  
  private static class AmazonFireGamepadMappings
    extends GamepadMappings
  {
    public void mapToStandardGamepad(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3, float[] paramArrayOfFloat4)
    {
      GamepadMappings.a(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.b(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.c(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.d(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.e(paramArrayOfFloat2, paramArrayOfFloat3);
      GamepadMappings.f(paramArrayOfFloat2, paramArrayOfFloat3);
      GamepadMappings.g(paramArrayOfFloat1, paramArrayOfFloat3);
      GamepadMappings.h(paramArrayOfFloat1, paramArrayOfFloat3);
    }
  }
  
  private static class PS3SixAxisGamepadMappings
    extends GamepadMappings
  {
    public void mapToStandardGamepad(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3, float[] paramArrayOfFloat4)
    {
      float f1 = paramArrayOfFloat4[96];
      float f2 = paramArrayOfFloat4[97];
      float f3 = paramArrayOfFloat4[99];
      float f4 = paramArrayOfFloat4[100];
      paramArrayOfFloat2[0] = f3;
      paramArrayOfFloat2[1] = f4;
      paramArrayOfFloat2[2] = f1;
      paramArrayOfFloat2[3] = f2;
      GamepadMappings.b(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.c(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.k(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.d(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.i(paramArrayOfFloat2, paramArrayOfFloat3);
      GamepadMappings.g(paramArrayOfFloat1, paramArrayOfFloat3);
      GamepadMappings.h(paramArrayOfFloat1, paramArrayOfFloat3);
    }
  }
  
  static class PS4GamepadMappings
    extends GamepadMappings
  {
    private static float c(float paramFloat)
    {
      return 1.0F - (1.0F - paramFloat) / 2.0F;
    }
    
    public void mapToStandardGamepad(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3, float[] paramArrayOfFloat4)
    {
      float f1 = paramArrayOfFloat4[96];
      float f2 = paramArrayOfFloat4[97];
      float f3 = paramArrayOfFloat4[98];
      float f4 = paramArrayOfFloat4[99];
      paramArrayOfFloat2[0] = f2;
      paramArrayOfFloat2[1] = f3;
      paramArrayOfFloat2[2] = f1;
      paramArrayOfFloat2[3] = f4;
      f1 = paramArrayOfFloat4[100];
      f2 = paramArrayOfFloat4[101];
      paramArrayOfFloat2[4] = f1;
      paramArrayOfFloat2[5] = f2;
      f1 = paramArrayOfFloat3[12];
      f2 = paramArrayOfFloat3[13];
      paramArrayOfFloat2[6] = c(f1);
      paramArrayOfFloat2[7] = c(f2);
      f1 = paramArrayOfFloat4[104];
      f2 = paramArrayOfFloat4[105];
      paramArrayOfFloat2[8] = f1;
      paramArrayOfFloat2[9] = f2;
      f1 = paramArrayOfFloat4[109];
      f2 = paramArrayOfFloat4[108];
      paramArrayOfFloat2[10] = f1;
      paramArrayOfFloat2[11] = f2;
      paramArrayOfFloat2[16] = paramArrayOfFloat4[110];
      GamepadMappings.f(paramArrayOfFloat2, paramArrayOfFloat3);
      GamepadMappings.g(paramArrayOfFloat1, paramArrayOfFloat3);
      GamepadMappings.h(paramArrayOfFloat1, paramArrayOfFloat3);
    }
  }
  
  private static class SamsungEIGP20GamepadMappings
    extends GamepadMappings
  {
    public void mapToStandardGamepad(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3, float[] paramArrayOfFloat4)
    {
      GamepadMappings.a(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.l(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.c(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.d(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.f(paramArrayOfFloat2, paramArrayOfFloat3);
      GamepadMappings.g(paramArrayOfFloat1, paramArrayOfFloat3);
      GamepadMappings.j(paramArrayOfFloat1, paramArrayOfFloat3);
    }
  }
  
  private static class UnknownGamepadMappings
    extends GamepadMappings
  {
    private int jdField_a_of_type_Int = -1;
    private boolean jdField_a_of_type_Boolean;
    private int b = -1;
    private int c = -1;
    private int d = -1;
    
    UnknownGamepadMappings(int[] paramArrayOfInt)
    {
      int k = paramArrayOfInt.length;
      int j = 0;
      int i = 0;
      while (j < k)
      {
        int m = paramArrayOfInt[j];
        switch (m)
        {
        case 20: 
        case 21: 
        default: 
          break;
        case 18: 
        case 19: 
        case 22: 
          this.b = m;
          break;
        case 17: 
        case 23: 
          this.jdField_a_of_type_Int = m;
          break;
        case 16: 
          i += 1;
          break;
        case 15: 
          i += 1;
          break;
        case 13: 
        case 14: 
          this.d = m;
          break;
        case 11: 
        case 12: 
          this.c = m;
        }
        j += 1;
      }
      if (i == 2) {
        this.jdField_a_of_type_Boolean = true;
      }
    }
    
    public boolean a()
    {
      return false;
    }
    
    public void mapToStandardGamepad(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3, float[] paramArrayOfFloat4)
    {
      GamepadMappings.a(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.b(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.c(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.d(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.g(paramArrayOfFloat1, paramArrayOfFloat3);
      int i = this.jdField_a_of_type_Int;
      int j;
      float f1;
      float f2;
      if (i != -1)
      {
        j = this.b;
        if (j != -1)
        {
          f1 = paramArrayOfFloat3[i];
          f2 = paramArrayOfFloat3[j];
          paramArrayOfFloat2[6] = f1;
          paramArrayOfFloat2[7] = f2;
          break label86;
        }
      }
      GamepadMappings.m(paramArrayOfFloat2, paramArrayOfFloat4);
      label86:
      i = this.c;
      if (i != -1)
      {
        j = this.d;
        if (j != -1)
        {
          f1 = paramArrayOfFloat3[i];
          f2 = paramArrayOfFloat3[j];
          paramArrayOfFloat1[2] = f1;
          paramArrayOfFloat1[3] = f2;
        }
      }
      if (this.jdField_a_of_type_Boolean)
      {
        GamepadMappings.f(paramArrayOfFloat2, paramArrayOfFloat3);
        return;
      }
      GamepadMappings.k(paramArrayOfFloat2, paramArrayOfFloat4);
    }
  }
  
  private static class XboxCompatibleGamepadMappings
    extends GamepadMappings
  {
    public void mapToStandardGamepad(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3, float[] paramArrayOfFloat4)
    {
      GamepadMappings.a(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.b(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.c(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.d(paramArrayOfFloat2, paramArrayOfFloat4);
      GamepadMappings.i(paramArrayOfFloat2, paramArrayOfFloat3);
      GamepadMappings.f(paramArrayOfFloat2, paramArrayOfFloat3);
      GamepadMappings.g(paramArrayOfFloat1, paramArrayOfFloat3);
      GamepadMappings.h(paramArrayOfFloat1, paramArrayOfFloat3);
    }
  }
  
  private static class XboxOneS2016FirmwareMappings
    extends GamepadMappings
  {
    private boolean a = false;
    private boolean b = false;
    
    public void mapToStandardGamepad(float[] paramArrayOfFloat1, float[] paramArrayOfFloat2, float[] paramArrayOfFloat3, float[] paramArrayOfFloat4)
    {
      paramArrayOfFloat2[0] = paramArrayOfFloat4[96];
      paramArrayOfFloat2[1] = paramArrayOfFloat4[97];
      paramArrayOfFloat2[2] = paramArrayOfFloat4[98];
      paramArrayOfFloat2[3] = paramArrayOfFloat4[99];
      paramArrayOfFloat2[4] = paramArrayOfFloat4[100];
      paramArrayOfFloat2[5] = paramArrayOfFloat4[101];
      paramArrayOfFloat2[8] = paramArrayOfFloat4[102];
      paramArrayOfFloat2[9] = paramArrayOfFloat4[103];
      paramArrayOfFloat2[10] = paramArrayOfFloat4[104];
      paramArrayOfFloat2[11] = paramArrayOfFloat4[105];
      if (paramArrayOfFloat3[11] != 0.0F) {
        this.a = true;
      }
      if (paramArrayOfFloat3[14] != 0.0F) {
        this.b = true;
      }
      if (this.a) {
        paramArrayOfFloat2[6] = ((paramArrayOfFloat3[11] + 1.0F) / 2.0F);
      } else {
        paramArrayOfFloat2[6] = 0.0F;
      }
      if (this.b) {
        paramArrayOfFloat2[7] = ((paramArrayOfFloat3[14] + 1.0F) / 2.0F);
      } else {
        paramArrayOfFloat2[7] = 0.0F;
      }
      GamepadMappings.f(paramArrayOfFloat2, paramArrayOfFloat3);
      GamepadMappings.g(paramArrayOfFloat1, paramArrayOfFloat3);
      GamepadMappings.j(paramArrayOfFloat1, paramArrayOfFloat3);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\device\gamepad\GamepadMappings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */