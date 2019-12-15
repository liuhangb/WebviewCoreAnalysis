package com.tencent.common;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import com.tencent.basesupport.FLogger;
import java.lang.ref.SoftReference;

public class BitmapCache
{
  static BitmapCache jdField_a_of_type_ComTencentCommonBitmapCache = new BitmapCache();
  static SoftReference<Bitmap> jdField_a_of_type_JavaLangRefSoftReference;
  static SoftReference<Bitmap> jdField_b_of_type_JavaLangRefSoftReference;
  Bitmap jdField_a_of_type_AndroidGraphicsBitmap = null;
  Bitmap jdField_b_of_type_AndroidGraphicsBitmap = null;
  
  public static BitmapCache getInstance()
  {
    return jdField_a_of_type_ComTencentCommonBitmapCache;
  }
  
  boolean a(Bitmap paramBitmap, int paramInt1, int paramInt2)
  {
    return (paramBitmap != null) && (!paramBitmap.isRecycled()) && (paramBitmap.getWidth() == paramInt1) && (paramBitmap.getHeight() == paramInt2);
  }
  
  public void clearCache()
  {
    try
    {
      if (jdField_a_of_type_JavaLangRefSoftReference != null) {
        this.jdField_a_of_type_AndroidGraphicsBitmap = ((Bitmap)jdField_a_of_type_JavaLangRefSoftReference.get());
      }
      if (jdField_b_of_type_JavaLangRefSoftReference != null) {
        this.jdField_b_of_type_AndroidGraphicsBitmap = ((Bitmap)jdField_b_of_type_JavaLangRefSoftReference.get());
      }
      if ((this.jdField_a_of_type_AndroidGraphicsBitmap != null) && (!this.jdField_a_of_type_AndroidGraphicsBitmap.isRecycled()))
      {
        this.jdField_a_of_type_AndroidGraphicsBitmap.recycle();
        this.jdField_a_of_type_AndroidGraphicsBitmap = null;
      }
      if ((this.jdField_b_of_type_AndroidGraphicsBitmap != null) && (!this.jdField_b_of_type_AndroidGraphicsBitmap.isRecycled()))
      {
        this.jdField_b_of_type_AndroidGraphicsBitmap.recycle();
        this.jdField_b_of_type_AndroidGraphicsBitmap = null;
      }
      this.jdField_a_of_type_AndroidGraphicsBitmap = null;
      this.jdField_b_of_type_AndroidGraphicsBitmap = null;
      return;
    }
    finally {}
  }
  
  public Bitmap getCurrentBitmap()
  {
    Bitmap localBitmap = null;
    try
    {
      if (jdField_a_of_type_JavaLangRefSoftReference != null) {
        localBitmap = (Bitmap)jdField_a_of_type_JavaLangRefSoftReference.get();
      }
      return localBitmap;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public Bitmap getNextBitmap()
  {
    Bitmap localBitmap = null;
    try
    {
      if (jdField_b_of_type_JavaLangRefSoftReference != null) {
        localBitmap = (Bitmap)jdField_b_of_type_JavaLangRefSoftReference.get();
      }
      return localBitmap;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public void preCreateTwoBitmaps(int paramInt1, int paramInt2)
  {
    if ((paramInt1 > 0) && (paramInt2 > 0)) {
      try
      {
        if (jdField_a_of_type_JavaLangRefSoftReference != null) {
          this.jdField_a_of_type_AndroidGraphicsBitmap = ((Bitmap)jdField_a_of_type_JavaLangRefSoftReference.get());
        }
        if (a(this.jdField_a_of_type_AndroidGraphicsBitmap, paramInt1, paramInt2))
        {
          if (jdField_b_of_type_JavaLangRefSoftReference != null) {
            this.jdField_b_of_type_AndroidGraphicsBitmap = ((Bitmap)jdField_b_of_type_JavaLangRefSoftReference.get());
          }
          if (a(this.jdField_b_of_type_AndroidGraphicsBitmap, paramInt1, paramInt2))
          {
            this.jdField_a_of_type_AndroidGraphicsBitmap = null;
            this.jdField_b_of_type_AndroidGraphicsBitmap = null;
            return;
          }
        }
        this.jdField_a_of_type_AndroidGraphicsBitmap = null;
        this.jdField_b_of_type_AndroidGraphicsBitmap = null;
        clearCache();
        FLogger.d("BitmapCache", "create current bitmap start");
        this.jdField_a_of_type_AndroidGraphicsBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.RGB_565);
        jdField_a_of_type_JavaLangRefSoftReference = new SoftReference(this.jdField_a_of_type_AndroidGraphicsBitmap);
        this.jdField_a_of_type_AndroidGraphicsBitmap = null;
        FLogger.d("BitmapCache", "create current bitmap end");
        FLogger.d("BitmapCache", "create next bitmap start");
        this.jdField_b_of_type_AndroidGraphicsBitmap = Bitmap.createBitmap(paramInt1, paramInt2, Bitmap.Config.RGB_565);
        jdField_b_of_type_JavaLangRefSoftReference = new SoftReference(this.jdField_b_of_type_AndroidGraphicsBitmap);
        this.jdField_b_of_type_AndroidGraphicsBitmap = null;
        FLogger.d("BitmapCache", "create next bitmap end");
        return;
      }
      finally {}
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\BitmapCache.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */