package com.tencent.mtt.base.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import java.lang.reflect.Method;

public class UIUtil
{
  private static Paint jdField_a_of_type_AndroidGraphicsPaint = new Paint();
  private static final PorterDuffXfermode jdField_a_of_type_AndroidGraphicsPorterDuffXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
  private static Rect jdField_a_of_type_AndroidGraphicsRect = new Rect();
  private static Method jdField_a_of_type_JavaLangReflectMethod;
  
  public static int alphaBind(int paramInt1, int paramInt2)
  {
    int i = paramInt2 >> 24 & 0xFF;
    int j = 255 - i;
    return ((paramInt2 >> 16 & 0xFF) * i + (paramInt1 >> 16 & 0xFF) * j) / 255 << 16 | 0xFF000000 | ((paramInt2 >> 8 & 0xFF) * i + (paramInt1 >> 8 & 0xFF) * j) / 255 << 8 | (i * (paramInt2 & 0xFF) + j * (paramInt1 & 0xFF)) / 255;
  }
  
  public static void drawColor(Canvas paramCanvas, Paint paramPaint, int paramInt, Rect paramRect)
  {
    int i = paramPaint.getAlpha();
    paramPaint.setColor(paramInt);
    paramPaint.setAlpha(paramPaint.getAlpha() * i / 255);
    paramPaint.setStyle(Paint.Style.FILL);
    paramCanvas.drawRect(paramRect, paramPaint);
    paramPaint.setAlpha(i);
  }
  
  public static void drawImage(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, Bitmap paramBitmap)
  {
    drawImage(paramCanvas, paramPaint, (int)paramFloat1, (int)paramFloat2, paramBitmap);
  }
  
  public static void drawImage(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, Bitmap paramBitmap, boolean paramBoolean)
  {
    drawImage(paramCanvas, paramPaint, (int)paramFloat1, (int)paramFloat2, paramBitmap, paramBoolean);
  }
  
  public static void drawImage(Canvas paramCanvas, Paint paramPaint, int paramInt1, int paramInt2, Bitmap paramBitmap)
  {
    drawImage(paramCanvas, paramPaint, paramInt1, paramInt2, paramBitmap, true);
  }
  
  public static void drawImage(Canvas paramCanvas, Paint paramPaint, int paramInt1, int paramInt2, Bitmap paramBitmap, boolean paramBoolean)
  {
    if (paramBitmap != null)
    {
      if (paramBitmap.isRecycled()) {
        return;
      }
      jdField_a_of_type_AndroidGraphicsRect.set(paramInt1, paramInt2, paramBitmap.getWidth() + paramInt1, paramBitmap.getHeight() + paramInt2);
      if (paramBoolean)
      {
        paramPaint.setFilterBitmap(true);
        paramCanvas.drawBitmap(paramBitmap, null, jdField_a_of_type_AndroidGraphicsRect, paramPaint);
        paramPaint.setFilterBitmap(false);
        return;
      }
      paramCanvas.drawBitmap(paramBitmap, null, jdField_a_of_type_AndroidGraphicsRect, paramPaint);
      return;
    }
  }
  
  public static void drawImage(Canvas paramCanvas, Paint paramPaint, Rect paramRect1, Rect paramRect2, Bitmap paramBitmap)
  {
    drawImage(paramCanvas, paramPaint, paramRect1, paramRect2, paramBitmap, true);
  }
  
  public static void drawImage(Canvas paramCanvas, Paint paramPaint, Rect paramRect1, Rect paramRect2, Bitmap paramBitmap, boolean paramBoolean)
  {
    if (paramBitmap != null)
    {
      if (paramBitmap.isRecycled()) {
        return;
      }
      if (paramBoolean)
      {
        paramPaint.setFilterBitmap(true);
        paramCanvas.drawBitmap(paramBitmap, paramRect1, paramRect2, paramPaint);
        paramPaint.setFilterBitmap(false);
        return;
      }
      paramCanvas.drawBitmap(paramBitmap, paramRect1, paramRect2, paramPaint);
      return;
    }
  }
  
  public static void drawImageColor(Canvas paramCanvas, Paint paramPaint, int paramInt1, int paramInt2, Bitmap paramBitmap, int paramInt3)
  {
    drawImageColor(paramCanvas, paramPaint, paramInt1, paramInt2, paramBitmap, paramInt3, 255);
  }
  
  public static void drawImageColor(Canvas paramCanvas, Paint paramPaint, int paramInt1, int paramInt2, Bitmap paramBitmap, int paramInt3, int paramInt4)
  {
    if (paramBitmap != null)
    {
      if (paramBitmap.isRecycled()) {
        return;
      }
      jdField_a_of_type_AndroidGraphicsRect.set(paramInt1, paramInt2, paramBitmap.getWidth() + paramInt1, paramBitmap.getHeight() + paramInt2);
      paramCanvas.saveLayer(paramInt1, paramInt2, paramBitmap.getWidth() + paramInt1, paramBitmap.getHeight() + paramInt2, null, 31);
      drawImage(paramCanvas, paramPaint, paramInt1, paramInt2, paramBitmap);
      paramInt1 = paramPaint.getColor();
      paramBitmap = paramPaint.getXfermode();
      paramPaint.setXfermode(jdField_a_of_type_AndroidGraphicsPorterDuffXfermode);
      if (paramInt4 == 255) {
        paramPaint.setColor(paramInt3);
      } else {
        paramPaint.setColor(Color.argb(paramInt4 * Color.alpha(paramInt3) / 255, Color.red(paramInt3), Color.green(paramInt3), Color.blue(paramInt3)));
      }
      paramCanvas.drawRect(jdField_a_of_type_AndroidGraphicsRect, paramPaint);
      paramCanvas.restore();
      paramPaint.setXfermode(paramBitmap);
      paramPaint.setColor(paramInt1);
      return;
    }
  }
  
  public static void drawImageRepeat(Canvas paramCanvas, Rect paramRect, Drawable paramDrawable)
  {
    if (paramDrawable == null) {
      return;
    }
    paramCanvas.save();
    paramCanvas.clipRect(paramRect, Region.Op.INTERSECT);
    int i = paramRect.width() / paramDrawable.getIntrinsicWidth();
    int j = i;
    if (paramRect.width() % paramDrawable.getIntrinsicWidth() != 0) {
      j = i + 1;
    }
    i = paramRect.height() / paramDrawable.getIntrinsicHeight();
    int k = i;
    if (paramRect.height() % paramDrawable.getIntrinsicHeight() != 0) {
      k = i + 1;
    }
    int n = paramRect.left;
    i = paramRect.top;
    int m = 0;
    while (m < j)
    {
      int i2 = 0;
      int i1 = i;
      i = i2;
      while (i < k)
      {
        paramDrawable.setBounds(n, i1, paramDrawable.getIntrinsicWidth() + n, paramDrawable.getIntrinsicHeight() + i1);
        paramDrawable.draw(paramCanvas);
        i1 += paramDrawable.getIntrinsicHeight();
        i += 1;
      }
      n += paramDrawable.getIntrinsicWidth();
      i = paramRect.top;
      m += 1;
    }
    paramCanvas.restore();
  }
  
  public static void drawPath(Canvas paramCanvas, Paint paramPaint, Path paramPath, boolean paramBoolean)
  {
    paramPaint.setAntiAlias(false);
    if (paramBoolean) {
      paramPaint.setStyle(Paint.Style.FILL);
    } else {
      paramPaint.setStyle(Paint.Style.STROKE);
    }
    paramCanvas.drawPath(paramPath, paramPaint);
  }
  
  public static void drawRect(Canvas paramCanvas, Paint paramPaint, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean)
  {
    paramPaint.setAntiAlias(false);
    if (paramBoolean) {
      paramPaint.setStyle(Paint.Style.FILL);
    } else {
      paramPaint.setStyle(Paint.Style.STROKE);
    }
    paramCanvas.drawRect(paramInt1, paramInt2, paramInt3, paramInt4, paramPaint);
  }
  
  public static void drawRect(Canvas paramCanvas, Paint paramPaint, Rect paramRect, boolean paramBoolean)
  {
    paramPaint.setAntiAlias(false);
    if (paramBoolean) {
      paramPaint.setStyle(Paint.Style.FILL);
    } else {
      paramPaint.setStyle(Paint.Style.STROKE);
    }
    paramCanvas.drawRect(paramRect, paramPaint);
  }
  
  public static void drawText(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, float paramFloat3, CharSequence paramCharSequence, int paramInt1, int paramInt2)
  {
    paramPaint.setAntiAlias(true);
    paramPaint.setTextAlign(Paint.Align.LEFT);
    paramPaint.setStyle(Paint.Style.FILL);
    paramCanvas.drawText(paramCharSequence, paramInt1, paramInt2, paramFloat1, paramFloat2 - paramPaint.ascent() - paramFloat3, paramPaint);
    paramPaint.setAntiAlias(false);
  }
  
  public static void drawText(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, float paramFloat3, String paramString)
  {
    paramPaint.setAntiAlias(true);
    paramPaint.setTextAlign(Paint.Align.LEFT);
    paramPaint.setStyle(Paint.Style.FILL);
    paramCanvas.drawText(paramString, paramFloat1, paramFloat2 - paramPaint.ascent() - paramFloat3, paramPaint);
    paramPaint.setAntiAlias(false);
  }
  
  public static Bitmap getBitmapColor(Bitmap paramBitmap, int paramInt)
  {
    if (paramInt == 0) {
      return paramBitmap;
    }
    if ((paramBitmap != null) && (!paramBitmap.isRecycled()))
    {
      Bitmap localBitmap = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), Bitmap.Config.ARGB_8888);
      drawImageColor(new Canvas(localBitmap), jdField_a_of_type_AndroidGraphicsPaint, 0, 0, paramBitmap, paramInt);
      return localBitmap;
    }
    return null;
  }
  
  public static Bitmap getColorBitmap(int paramInt, Bitmap.Config paramConfig)
  {
    paramConfig = Bitmap.createBitmap(1, 1, paramConfig);
    paramConfig.setPixel(0, 0, paramInt);
    return paramConfig;
  }
  
  public static Bitmap getScaleBitmap(Bitmap paramBitmap, int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, Bitmap.Config paramConfig)
  {
    if ((paramBitmap != null) && (paramInt1 > 0))
    {
      if (paramInt2 <= 0) {
        return null;
      }
      if (paramBoolean1)
      {
        float f1 = paramInt1;
        float f2 = paramInt2;
        if (f1 / f2 > paramBitmap.getHeight() / paramBitmap.getWidth()) {
          f1 = f2 / paramBitmap.getHeight();
        } else {
          f1 /= paramBitmap.getWidth();
        }
        paramConfig = new Matrix();
        paramConfig.postScale(f1, f1);
        try
        {
          paramBitmap = Bitmap.createBitmap(paramBitmap, 0, 0, Math.min(paramInt1, paramBitmap.getWidth()), Math.min(paramInt2, paramBitmap.getHeight()), paramConfig, true);
          return paramBitmap;
        }
        catch (Exception paramBitmap)
        {
          paramBitmap.printStackTrace();
        }
      }
      return null;
    }
    return null;
  }
  
  public static boolean isLightColor(int paramInt)
  {
    int i = paramInt;
    if (Color.alpha(paramInt) != 255) {
      i = alphaBind(-16777216, paramInt);
    }
    paramInt = Color.red(i);
    int j = Color.green(i);
    i = Color.blue(i);
    return paramInt * 0.299F + j * 0.587F + i * 0.114F >= 192.0F;
  }
  
  public static int setAlpha(int paramInt1, int paramInt2)
  {
    return paramInt1 & 0xFFFFFF | (paramInt2 & 0xFF) << 24;
  }
  
  public static void setBitmapPremultiplied(Bitmap paramBitmap)
  {
    try
    {
      if (jdField_a_of_type_JavaLangReflectMethod == null) {
        jdField_a_of_type_JavaLangReflectMethod = Class.forName("android.graphics.Bitmap").getDeclaredMethod("setPremultiplied", new Class[] { Boolean.TYPE });
      }
      jdField_a_of_type_JavaLangReflectMethod.invoke(paramBitmap, new Object[] { Boolean.valueOf(true) });
      return;
    }
    catch (Exception paramBitmap)
    {
      paramBitmap.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\UIUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */