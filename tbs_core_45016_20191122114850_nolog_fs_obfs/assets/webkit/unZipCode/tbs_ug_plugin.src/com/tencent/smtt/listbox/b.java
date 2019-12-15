package com.tencent.smtt.listbox;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;
import java.lang.ref.WeakReference;
import org.chromium.tencent.X5NativeBitmap;
import org.chromium.tencent.X5NativeBitmap.Factory;

public class b
{
  private static float jdField_a_of_type_Float;
  private static Paint.FontMetricsInt jdField_a_of_type_AndroidGraphicsPaint$FontMetricsInt = new Paint.FontMetricsInt();
  private static Paint jdField_a_of_type_AndroidGraphicsPaint;
  private static final PorterDuffXfermode jdField_a_of_type_AndroidGraphicsPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
  private static Rect jdField_a_of_type_AndroidGraphicsRect;
  private static SparseArray<WeakReference<BitmapDrawable>> jdField_a_of_type_AndroidUtilSparseArray;
  private static Rect b;
  
  static
  {
    jdField_a_of_type_Float = 2.0F;
    jdField_a_of_type_AndroidGraphicsRect = new Rect();
    b = new Rect();
    jdField_a_of_type_AndroidGraphicsPaint = new Paint();
    jdField_a_of_type_AndroidUtilSparseArray = null;
  }
  
  public static Bitmap a(Bitmap paramBitmap, float paramFloat1, float paramFloat2)
  {
    try
    {
      Bitmap localBitmap = X5NativeBitmap.nativeBitmapFactory().createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas localCanvas = new Canvas(localBitmap);
      Paint localPaint = new Paint();
      Rect localRect = new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight());
      RectF localRectF = new RectF(new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight()));
      localPaint.setAntiAlias(true);
      localCanvas.drawARGB(0, 0, 0, 0);
      localPaint.setColor(-16777216);
      localCanvas.drawRoundRect(localRectF, paramFloat1, paramFloat2, localPaint);
      localPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
      localCanvas.drawBitmap(paramBitmap, new Rect(0, 0, paramBitmap.getWidth(), paramBitmap.getHeight()), localRect, localPaint);
      return localBitmap;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      localOutOfMemoryError.printStackTrace();
      return paramBitmap;
    }
    catch (Exception localException) {}
    return paramBitmap;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\listbox\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */