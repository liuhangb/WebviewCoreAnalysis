package org.chromium.ui.gfx;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("gfx")
public class BitmapHelper
{
  private static Bitmap.Config a(int paramInt)
  {
    if (paramInt != 4)
    {
      switch (paramInt)
      {
      default: 
        return Bitmap.Config.ARGB_8888;
      case 2: 
        return Bitmap.Config.ARGB_4444;
      }
      return Bitmap.Config.ALPHA_8;
    }
    return Bitmap.Config.RGB_565;
  }
  
  @CalledByNative
  private static Bitmap createBitmap(int paramInt1, int paramInt2, int paramInt3)
  {
    Object localObject = a(paramInt3);
    try
    {
      Bitmap localBitmap = Bitmap.createBitmap(paramInt1, paramInt2, (Bitmap.Config)localObject);
      return localBitmap;
    }
    catch (Throwable localThrowable)
    {
      localObject = Bitmap.createBitmap(1, 1, (Bitmap.Config)localObject);
      localThrowable.printStackTrace();
    }
    return (Bitmap)localObject;
  }
  
  @CalledByNative
  private static int getBitmapFormatForConfig(Bitmap.Config paramConfig)
  {
    switch (1.a[paramConfig.ordinal()])
    {
    default: 
      return 0;
    case 4: 
      return 4;
    case 3: 
      return 3;
    case 2: 
      return 2;
    }
    return 1;
  }
  
  @CalledByNative
  private static int getByteCount(Bitmap paramBitmap)
  {
    return paramBitmap.getByteCount();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\gfx\BitmapHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */