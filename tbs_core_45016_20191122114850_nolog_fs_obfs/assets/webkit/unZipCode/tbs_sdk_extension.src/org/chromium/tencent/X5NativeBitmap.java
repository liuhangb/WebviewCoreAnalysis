package org.chromium.tencent;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.DisplayMetrics;
import org.chromium.base.annotations.CalledByNative;

public class X5NativeBitmap
{
  private static Factory sDefaultBitmapFactory;
  private static Factory sJavaBitmapFactory;
  private static Factory sPurgeableBitmapFactory;
  
  private static void CopyPremultipliedProp(Bitmap paramBitmap1, Bitmap paramBitmap2) {}
  
  private static void checkRecycled(Bitmap paramBitmap, String paramString)
  {
    if (!paramBitmap.isRecycled()) {
      return;
    }
    throw new IllegalStateException(paramString);
  }
  
  private static void checkWidthHeight(int paramInt1, int paramInt2)
  {
    if (paramInt1 > 0)
    {
      if (paramInt2 > 0) {
        return;
      }
      throw new IllegalArgumentException("height must be > 0");
    }
    throw new IllegalArgumentException("width must be > 0");
  }
  
  private static void checkXYSign(int paramInt1, int paramInt2)
  {
    if (paramInt1 >= 0)
    {
      if (paramInt2 >= 0) {
        return;
      }
      throw new IllegalArgumentException("y must be >= 0");
    }
    throw new IllegalArgumentException("x must be >= 0");
  }
  
  public static Bitmap createBitmapFromExternal(int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean)
  {
    Factory.sAllocFromUI.set(Boolean.valueOf(true));
    if (paramBoolean) {
      paramConfig = purgeableBitmapFactory().createBitmap(paramInt1, paramInt2, paramConfig);
    } else {
      paramConfig = nativeBitmapFactory().createBitmap(paramInt1, paramInt2, paramConfig);
    }
    Factory.sAllocFromUI.set(Boolean.valueOf(false));
    return paramConfig;
  }
  
  @CalledByNative
  private static int getBitmapHeight(Bitmap paramBitmap)
  {
    return paramBitmap.getHeight();
  }
  
  @CalledByNative
  private static int getBitmapWidth(Bitmap paramBitmap)
  {
    return paramBitmap.getWidth();
  }
  
  private static boolean isSupportNativeBitmap()
  {
    return nativeIsSupportNativeBitmap();
  }
  
  public static Factory javaBitmapFactory()
  {
    try
    {
      if (sJavaBitmapFactory == null) {
        sJavaBitmapFactory = new Factory(false, false);
      }
      return sJavaBitmapFactory;
    }
    finally {}
  }
  
  public static Factory nativeBitmapFactory()
  {
    try
    {
      if (sDefaultBitmapFactory == null) {
        sDefaultBitmapFactory = new Factory(true, false);
      }
      return sDefaultBitmapFactory;
    }
    finally {}
  }
  
  private static native Bitmap nativeCopyBitmap(Bitmap paramBitmap, Bitmap.Config paramConfig, boolean paramBoolean);
  
  private static native Bitmap nativeCreateBitmap(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Bitmap.Config paramConfig, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  private static native boolean nativeIsSupportNativeBitmap();
  
  private static native boolean nativePinBitmap(Bitmap paramBitmap, boolean paramBoolean);
  
  private static native void nativeUnmapBitmap(Bitmap paramBitmap);
  
  private static native void nativeUnpinBitmap(Bitmap paramBitmap);
  
  public static boolean pin(Bitmap paramBitmap, boolean paramBoolean)
  {
    if ((paramBitmap != null) && (!paramBitmap.isRecycled()))
    {
      if (!isSupportNativeBitmap()) {
        return true;
      }
      return nativePinBitmap(paramBitmap, paramBoolean);
    }
    return false;
  }
  
  public static Factory purgeableBitmapFactory()
  {
    try
    {
      if (sPurgeableBitmapFactory == null) {
        sPurgeableBitmapFactory = new Factory(true, true);
      }
      return sPurgeableBitmapFactory;
    }
    finally {}
  }
  
  public static void unmap(Bitmap paramBitmap)
  {
    nativeUnmapBitmap(paramBitmap);
  }
  
  public static void unpin(Bitmap paramBitmap)
  {
    if (paramBitmap != null)
    {
      if (paramBitmap.isRecycled()) {
        return;
      }
      if (!isSupportNativeBitmap()) {
        return;
      }
      nativeUnpinBitmap(paramBitmap);
      return;
    }
  }
  
  public static class Factory
  {
    private static int MIN_BITMAP_SIZE_ON_NATIVE = 128;
    static ThreadLocal<Boolean> sAllocFromUI = new ThreadLocal()
    {
      public Boolean initialValue()
      {
        return Boolean.valueOf(false);
      }
    };
    private static volatile Matrix sScaleMatrix;
    private boolean mNativeBitmap;
    private boolean mPurgeable;
    
    Factory(boolean paramBoolean1, boolean paramBoolean2)
    {
      this.mNativeBitmap = paramBoolean1;
      this.mPurgeable = paramBoolean2;
    }
    
    private Bitmap CreateOptimizedBitmap(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Bitmap.Config paramConfig, boolean paramBoolean)
      throws IllegalArgumentException, OutOfMemoryError
    {
      if ((paramInt3 > 0) && (paramInt4 > 0))
      {
        boolean bool;
        if (paramBoolean) {
          bool = this.mPurgeable;
        } else {
          bool = false;
        }
        paramArrayOfInt = X5NativeBitmap.nativeCreateBitmap(paramArrayOfInt, paramInt1, paramInt2, paramInt3, paramInt4, paramConfig, paramBoolean, bool, ((Boolean)sAllocFromUI.get()).booleanValue());
        if (paramArrayOfInt != null) {
          return paramArrayOfInt;
        }
        throw new OutOfMemoryError();
      }
      throw new IllegalArgumentException();
    }
    
    private Bitmap createBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean)
    {
      return createBitmap(null, paramInt1, paramInt2, paramConfig, paramBoolean);
    }
    
    private Bitmap createBitmap(DisplayMetrics paramDisplayMetrics, int paramInt1, int paramInt2, Bitmap.Config paramConfig, boolean paramBoolean)
    {
      if ((paramInt1 > 0) && (paramInt2 > 0))
      {
        Bitmap localBitmap = CreateOptimizedBitmap(null, 0, paramInt1, paramInt1, paramInt2, paramConfig, true);
        if (paramDisplayMetrics != null) {
          localBitmap.setDensity(paramDisplayMetrics.densityDpi);
        }
        localBitmap.setHasAlpha(paramBoolean);
        if (paramConfig == Bitmap.Config.ARGB_8888)
        {
          if (!paramBoolean)
          {
            localBitmap.eraseColor(-16777216);
            return localBitmap;
          }
          localBitmap.eraseColor(0);
        }
        return localBitmap;
      }
      throw new IllegalArgumentException("width and height must be > 0");
    }
    
    private boolean createdInNative(int paramInt1, int paramInt2)
    {
      return false;
    }
    
    public Bitmap copy(Bitmap paramBitmap, Bitmap.Config paramConfig, boolean paramBoolean)
    {
      if (!createdInNative(paramBitmap.getWidth(), paramBitmap.getHeight())) {
        return paramBitmap.copy(paramConfig, paramBoolean);
      }
      X5NativeBitmap.checkRecycled(paramBitmap, "Can't copy a recycled bitmap");
      paramConfig = X5NativeBitmap.nativeCopyBitmap(paramBitmap, paramConfig, paramBoolean);
      if (paramConfig != null)
      {
        X5NativeBitmap.CopyPremultipliedProp(paramBitmap, paramConfig);
        paramConfig.setDensity(paramBitmap.getDensity());
      }
      return paramConfig;
    }
    
    public Bitmap createBitmap(int paramInt1, int paramInt2, Bitmap.Config paramConfig)
    {
      if (!createdInNative(paramInt1, paramInt2)) {
        return Bitmap.createBitmap(paramInt1, paramInt2, paramConfig);
      }
      return createBitmap(paramInt1, paramInt2, paramConfig, true);
    }
    
    public Bitmap createBitmap(Bitmap paramBitmap)
    {
      return createBitmap(paramBitmap, 0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
    }
    
    public Bitmap createBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      return createBitmap(paramBitmap, paramInt1, paramInt2, paramInt3, paramInt4, null, false);
    }
    
    public Bitmap createBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Matrix paramMatrix, boolean paramBoolean)
    {
      if (!createdInNative(paramInt3, paramInt4)) {
        return Bitmap.createBitmap(paramBitmap, paramInt1, paramInt2, paramInt3, paramInt4, paramMatrix, paramBoolean);
      }
      X5NativeBitmap.checkXYSign(paramInt1, paramInt2);
      X5NativeBitmap.checkWidthHeight(paramInt3, paramInt4);
      int i = paramInt1 + paramInt3;
      if (i <= paramBitmap.getWidth())
      {
        int j = paramInt2 + paramInt4;
        if (j <= paramBitmap.getHeight())
        {
          if ((!paramBitmap.isMutable()) && (paramInt1 == 0) && (paramInt2 == 0) && (paramInt3 == paramBitmap.getWidth()) && (paramInt4 == paramBitmap.getHeight()) && ((paramMatrix == null) || (paramMatrix.isIdentity()))) {
            return paramBitmap;
          }
          Canvas localCanvas = new Canvas();
          Rect localRect = new Rect(paramInt1, paramInt2, i, j);
          RectF localRectF = new RectF(0.0F, 0.0F, paramInt3, paramInt4);
          Object localObject1 = Bitmap.Config.ARGB_8888;
          Object localObject2 = paramBitmap.getConfig();
          if (localObject2 != null) {
            switch (X5NativeBitmap.1.$SwitchMap$android$graphics$Bitmap$Config[localObject2.ordinal()])
            {
            default: 
              localObject1 = Bitmap.Config.ARGB_8888;
              break;
            case 2: 
              localObject1 = Bitmap.Config.ALPHA_8;
              break;
            case 1: 
              localObject1 = Bitmap.Config.RGB_565;
            }
          }
          if ((paramMatrix != null) && (!paramMatrix.isIdentity()))
          {
            paramInt1 = paramMatrix.rectStaysRect() ^ true;
            Object localObject3 = new RectF();
            paramMatrix.mapRect((RectF)localObject3, localRectF);
            paramInt2 = Math.round(((RectF)localObject3).width());
            paramInt3 = Math.round(((RectF)localObject3).height());
            if (paramInt1 != 0) {
              localObject1 = Bitmap.Config.ARGB_8888;
            }
            boolean bool;
            if ((paramInt1 == 0) && (!paramBitmap.hasAlpha())) {
              bool = false;
            } else {
              bool = true;
            }
            localObject2 = createBitmap(paramInt2, paramInt3, (Bitmap.Config)localObject1, bool);
            ((Bitmap)localObject2).eraseColor(0);
            localCanvas.translate(-((RectF)localObject3).left, -((RectF)localObject3).top);
            localCanvas.concat(paramMatrix);
            localObject3 = new Paint();
            ((Paint)localObject3).setFilterBitmap(paramBoolean);
            localObject1 = localObject2;
            paramMatrix = (Matrix)localObject3;
            if (paramInt1 != 0)
            {
              ((Paint)localObject3).setAntiAlias(true);
              localObject1 = localObject2;
              paramMatrix = (Matrix)localObject3;
            }
          }
          else
          {
            localObject1 = createBitmap(paramInt3, paramInt4, (Bitmap.Config)localObject1, paramBitmap.hasAlpha());
            paramMatrix = null;
          }
          ((Bitmap)localObject1).setDensity(paramBitmap.getDensity());
          ((Bitmap)localObject1).setHasAlpha(paramBitmap.hasAlpha());
          X5NativeBitmap.CopyPremultipliedProp((Bitmap)localObject1, paramBitmap);
          localCanvas.setBitmap((Bitmap)localObject1);
          localCanvas.drawBitmap(paramBitmap, localRect, localRectF, paramMatrix);
          localCanvas.setBitmap(null);
          return (Bitmap)localObject1;
        }
        throw new IllegalArgumentException("y + height must be <= bitmap.height()");
      }
      throw new IllegalArgumentException("x + width must be <= bitmap.width()");
    }
    
    public Bitmap createBitmap(DisplayMetrics paramDisplayMetrics, int paramInt1, int paramInt2, Bitmap.Config paramConfig)
    {
      if (!createdInNative(paramInt1, paramInt2))
      {
        if (paramDisplayMetrics != null) {
          return Bitmap.createBitmap(paramDisplayMetrics, paramInt1, paramInt2, paramConfig);
        }
        return Bitmap.createBitmap(paramInt1, paramInt2, paramConfig);
      }
      return createBitmap(paramDisplayMetrics, paramInt1, paramInt2, paramConfig, true);
    }
    
    public Bitmap createBitmap(DisplayMetrics paramDisplayMetrics, int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Bitmap.Config paramConfig)
    {
      if (!createdInNative(paramInt3, paramInt4))
      {
        if (paramDisplayMetrics != null) {
          return Bitmap.createBitmap(paramDisplayMetrics, paramArrayOfInt, paramInt1, paramInt2, paramInt3, paramInt4, paramConfig);
        }
        return Bitmap.createBitmap(paramArrayOfInt, paramInt1, paramInt2, paramInt3, paramInt4, paramConfig);
      }
      X5NativeBitmap.checkWidthHeight(paramInt3, paramInt4);
      if (Math.abs(paramInt2) >= paramInt3)
      {
        int i = (paramInt4 - 1) * paramInt2 + paramInt1;
        int j = paramArrayOfInt.length;
        if ((paramInt1 >= 0) && (paramInt1 + paramInt3 <= j) && (i >= 0) && (i + paramInt3 <= j))
        {
          if ((paramInt3 > 0) && (paramInt4 > 0))
          {
            paramArrayOfInt = CreateOptimizedBitmap(paramArrayOfInt, paramInt1, paramInt2, paramInt3, paramInt4, paramConfig, false);
            if (paramDisplayMetrics != null) {
              paramArrayOfInt.setDensity(paramDisplayMetrics.densityDpi);
            }
            return paramArrayOfInt;
          }
          throw new IllegalArgumentException("width and height must be > 0");
        }
        throw new ArrayIndexOutOfBoundsException();
      }
      throw new IllegalArgumentException("abs(stride) must be >= width");
    }
    
    public Bitmap createBitmap(DisplayMetrics paramDisplayMetrics, int[] paramArrayOfInt, int paramInt1, int paramInt2, Bitmap.Config paramConfig)
    {
      return createBitmap(paramDisplayMetrics, paramArrayOfInt, 0, paramInt1, paramInt1, paramInt2, paramConfig);
    }
    
    public Bitmap createBitmap(int[] paramArrayOfInt, int paramInt1, int paramInt2, Bitmap.Config paramConfig)
    {
      return createBitmap(null, paramArrayOfInt, 0, paramInt1, paramInt1, paramInt2, paramConfig);
    }
    
    /* Error */
    public Bitmap createScaledBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      // Byte code:
      //   0: aload_0
      //   1: iload_2
      //   2: iload_3
      //   3: invokespecial 110	org/chromium/tencent/X5NativeBitmap$Factory:createdInNative	(II)Z
      //   6: ifne +12 -> 18
      //   9: aload_1
      //   10: iload_2
      //   11: iload_3
      //   12: iload 4
      //   14: invokestatic 274	android/graphics/Bitmap:createScaledBitmap	(Landroid/graphics/Bitmap;IIZ)Landroid/graphics/Bitmap;
      //   17: areturn
      //   18: ldc 6
      //   20: monitorenter
      //   21: getstatic 276	org/chromium/tencent/X5NativeBitmap$Factory:sScaleMatrix	Landroid/graphics/Matrix;
      //   24: astore 8
      //   26: aconst_null
      //   27: putstatic 276	org/chromium/tencent/X5NativeBitmap$Factory:sScaleMatrix	Landroid/graphics/Matrix;
      //   30: ldc 6
      //   32: monitorexit
      //   33: aload 8
      //   35: astore 7
      //   37: aload 8
      //   39: ifnonnull +12 -> 51
      //   42: new 154	android/graphics/Matrix
      //   45: dup
      //   46: invokespecial 277	android/graphics/Matrix:<init>	()V
      //   49: astore 7
      //   51: aload_1
      //   52: invokevirtual 105	android/graphics/Bitmap:getWidth	()I
      //   55: istore 5
      //   57: aload_1
      //   58: invokevirtual 108	android/graphics/Bitmap:getHeight	()I
      //   61: istore 6
      //   63: aload 7
      //   65: iload_2
      //   66: i2f
      //   67: iload 5
      //   69: i2f
      //   70: fdiv
      //   71: iload_3
      //   72: i2f
      //   73: iload 6
      //   75: i2f
      //   76: fdiv
      //   77: invokevirtual 280	android/graphics/Matrix:setScale	(FF)V
      //   80: aload_0
      //   81: aload_1
      //   82: iconst_0
      //   83: iconst_0
      //   84: iload 5
      //   86: iload 6
      //   88: aload 7
      //   90: iload 4
      //   92: invokevirtual 141	org/chromium/tencent/X5NativeBitmap$Factory:createBitmap	(Landroid/graphics/Bitmap;IIIILandroid/graphics/Matrix;Z)Landroid/graphics/Bitmap;
      //   95: astore_1
      //   96: ldc 74
      //   98: monitorenter
      //   99: getstatic 276	org/chromium/tencent/X5NativeBitmap$Factory:sScaleMatrix	Landroid/graphics/Matrix;
      //   102: ifnonnull +8 -> 110
      //   105: aload 7
      //   107: putstatic 276	org/chromium/tencent/X5NativeBitmap$Factory:sScaleMatrix	Landroid/graphics/Matrix;
      //   110: ldc 74
      //   112: monitorexit
      //   113: aload_1
      //   114: areturn
      //   115: astore_1
      //   116: ldc 74
      //   118: monitorexit
      //   119: aload_1
      //   120: athrow
      //   121: astore_1
      //   122: ldc 6
      //   124: monitorexit
      //   125: aload_1
      //   126: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	127	0	this	Factory
      //   0	127	1	paramBitmap	Bitmap
      //   0	127	2	paramInt1	int
      //   0	127	3	paramInt2	int
      //   0	127	4	paramBoolean	boolean
      //   55	30	5	i	int
      //   61	26	6	j	int
      //   35	71	7	localMatrix1	Matrix
      //   24	14	8	localMatrix2	Matrix
      // Exception table:
      //   from	to	target	type
      //   99	110	115	finally
      //   110	113	115	finally
      //   116	119	115	finally
      //   21	33	121	finally
      //   122	125	121	finally
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\X5NativeBitmap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */