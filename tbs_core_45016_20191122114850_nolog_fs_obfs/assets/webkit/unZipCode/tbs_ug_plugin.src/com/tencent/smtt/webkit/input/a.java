package com.tencent.smtt.webkit.input;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;

public class a
  extends View
{
  private static int e = 5;
  private float jdField_a_of_type_Float;
  private int jdField_a_of_type_Int;
  private Paint jdField_a_of_type_AndroidGraphicsPaint = new Paint();
  private Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  private int b;
  private int c;
  private int d;
  
  a(Context paramContext)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidGraphicsPaint.setAntiAlias(true);
    this.jdField_a_of_type_Float = paramContext.getResources().getDisplayMetrics().density;
  }
  
  int a()
  {
    return this.d;
  }
  
  void a(int paramInt)
  {
    this.b = paramInt;
  }
  
  void a(int paramInt1, int paramInt2, int paramInt3)
  {
    this.jdField_a_of_type_Int = paramInt2;
    this.jdField_a_of_type_AndroidGraphicsPaint.setStrokeWidth(paramInt1);
    this.jdField_a_of_type_AndroidGraphicsPaint.setColor(paramInt3);
  }
  
  void a(Drawable paramDrawable, int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = paramDrawable;
    this.d = paramInt1;
    this.c = paramInt2;
  }
  
  void b(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    int i = this.jdField_a_of_type_Int;
    if (i != 0)
    {
      int j = (int)(this.jdField_a_of_type_Float * 1.0F);
      if (this.b == 0)
      {
        float f = j;
        paramCanvas.drawLine(f, 0.0F, f, i, this.jdField_a_of_type_AndroidGraphicsPaint);
      }
      else
      {
        int k = this.d;
        paramCanvas.drawLine(k - j, 0.0F, k - j, i, this.jdField_a_of_type_AndroidGraphicsPaint);
      }
      Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
      i = this.jdField_a_of_type_Int;
      localDrawable.setBounds(0, i, this.d, this.c + i);
    }
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.draw(paramCanvas);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension((int)(this.d + e * this.jdField_a_of_type_Float), this.jdField_a_of_type_Int + this.c);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\input\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */