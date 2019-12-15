package com.tencent.smtt.browser.x5;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.tencent.X5NativeBitmap;

public class X5OptimizedBitmap
{
  @UsedByReflection("WebCoreProxy.java")
  public static Bitmap createBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean)
  {
    return X5NativeBitmap.createBitmapFromExternal(paramInt1, paramInt2, paramConfig, paramBoolean);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean pin(Bitmap paramBitmap, boolean paramBoolean)
  {
    return X5NativeBitmap.pin(paramBitmap, paramBoolean);
  }
  
  public static void unmap(Bitmap paramBitmap)
  {
    X5NativeBitmap.unmap(paramBitmap);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void unpin(Bitmap paramBitmap)
  {
    X5NativeBitmap.unpin(paramBitmap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\browser\x5\X5OptimizedBitmap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */