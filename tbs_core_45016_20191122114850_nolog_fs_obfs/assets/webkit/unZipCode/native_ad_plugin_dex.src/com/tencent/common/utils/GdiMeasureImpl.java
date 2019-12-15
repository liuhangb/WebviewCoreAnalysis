package com.tencent.common.utils;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;

public class GdiMeasureImpl
  extends GdiMeasure
{
  private static int jdField_a_of_type_Int = 0;
  private static int b = 0;
  private static int c = -1;
  private Paint jdField_a_of_type_AndroidGraphicsPaint;
  
  public GdiMeasureImpl()
  {
    this.jdField_a_of_type_AndroidGraphicsPaint = new Paint();
    this.jdField_a_of_type_AndroidGraphicsPaint.setAntiAlias(true);
  }
  
  public GdiMeasureImpl(Paint paramPaint)
  {
    this.jdField_a_of_type_AndroidGraphicsPaint = paramPaint;
    this.jdField_a_of_type_AndroidGraphicsPaint.setAntiAlias(true);
  }
  
  public static int getScreenHeight(Context paramContext)
  {
    if (b <= 0) {
      getScreenSize(paramContext);
    }
    return b;
  }
  
  public static void getScreenSize(Context paramContext)
  {
    paramContext = (WindowManager)paramContext.getApplicationContext().getSystemService("window");
    jdField_a_of_type_Int = paramContext.getDefaultDisplay().getWidth();
    b = paramContext.getDefaultDisplay().getHeight();
  }
  
  public static int getScreenWidth(Context paramContext)
  {
    if (jdField_a_of_type_Int <= 0) {
      getScreenSize(paramContext);
    }
    return jdField_a_of_type_Int;
  }
  
  public static int getSdkVersion()
  {
    if (-1 == c) {
      c = Integer.parseInt(Build.VERSION.SDK);
    }
    return c;
  }
  
  public int breakText(String paramString, int paramInt, float[] paramArrayOfFloat)
  {
    this.jdField_a_of_type_AndroidGraphicsPaint.setAntiAlias(true);
    float f1 = getStringWidth("a");
    int m = Math.min((int)(paramInt * 2 / f1), paramString.length());
    if (m <= 0) {
      return 0;
    }
    float[] arrayOfFloat = new float[m];
    try
    {
      this.jdField_a_of_type_AndroidGraphicsPaint.getTextWidths(paramString.subSequence(0, m).toString(), arrayOfFloat);
      int j = 0;
      int i = 0;
      int k;
      for (;;)
      {
        k = i;
        if (j >= m) {
          break;
        }
        i = (int)(i + arrayOfFloat[j]);
        if (i > paramInt)
        {
          k = i;
          break;
        }
        j += 1;
      }
      if (paramArrayOfFloat != null)
      {
        float f2 = k;
        if (j < m) {
          f1 = arrayOfFloat[j];
        } else {
          f1 = 0.0F;
        }
        paramArrayOfFloat[0] = (f2 - f1);
      }
      return j;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
  }
  
  public short getCanvasWidth()
  {
    return 400;
  }
  
  public short getCharHeight()
  {
    this.jdField_a_of_type_AndroidGraphicsPaint.setAntiAlias(true);
    return (short)(int)this.jdField_a_of_type_AndroidGraphicsPaint.getTextSize();
  }
  
  public short getCharWidth()
  {
    this.jdField_a_of_type_AndroidGraphicsPaint.setAntiAlias(true);
    return (short)(int)this.jdField_a_of_type_AndroidGraphicsPaint.getTextSize();
  }
  
  public short getStringWidth(String paramString)
  {
    if (StringUtils.isEmpty(paramString)) {
      return 0;
    }
    this.jdField_a_of_type_AndroidGraphicsPaint.setAntiAlias(true);
    return (short)(int)this.jdField_a_of_type_AndroidGraphicsPaint.measureText(paramString);
  }
  
  public void getStringWidthHeight(String paramString, QSize paramQSize)
  {
    paramQSize.mWidth = getStringWidth(paramString);
    paramQSize.mHeight = getCharHeight();
  }
  
  public int getStringWidthInt(String paramString)
  {
    if (StringUtils.isEmpty(paramString)) {
      return 0;
    }
    this.jdField_a_of_type_AndroidGraphicsPaint.setAntiAlias(true);
    return (int)Math.ceil(this.jdField_a_of_type_AndroidGraphicsPaint.measureText(paramString));
  }
  
  public void setFontSize(int paramInt)
  {
    this.jdField_a_of_type_AndroidGraphicsPaint.setTextSize(paramInt);
  }
  
  public void setFontTypeface(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.jdField_a_of_type_AndroidGraphicsPaint.setFakeBoldText(paramBoolean1);
    if (paramBoolean2)
    {
      this.jdField_a_of_type_AndroidGraphicsPaint.setTextSkewX(-0.25F);
      return;
    }
    this.jdField_a_of_type_AndroidGraphicsPaint.setTextSkewX(0.0F);
  }
  
  public void setUnderline(boolean paramBoolean)
  {
    this.jdField_a_of_type_AndroidGraphicsPaint.setUnderlineText(paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\GdiMeasureImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */