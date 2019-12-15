package com.tencent.tbs.core;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.tencent.smtt.browser.x5.X5OptimizedBitmap;
import com.tencent.smtt.export.external.interfaces.IX5CoreOptimizedBitmap;

public class X5CoreOptimizedBitmap
  implements IX5CoreOptimizedBitmap
{
  private static X5CoreOptimizedBitmap sInstance;
  
  public static X5CoreOptimizedBitmap getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreOptimizedBitmap();
      }
      X5CoreOptimizedBitmap localX5CoreOptimizedBitmap = sInstance;
      return localX5CoreOptimizedBitmap;
    }
    finally {}
  }
  
  public Bitmap createX5OptimizedBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean)
  {
    return X5OptimizedBitmap.createBitmap(paramInt1, paramInt2, paramConfig, paramBoolean);
  }
  
  public boolean pinBitmap(Bitmap paramBitmap, boolean paramBoolean)
  {
    return X5OptimizedBitmap.pin(paramBitmap, paramBoolean);
  }
  
  public void unpinBitmap(Bitmap paramBitmap)
  {
    X5OptimizedBitmap.unpin(paramBitmap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreOptimizedBitmap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */