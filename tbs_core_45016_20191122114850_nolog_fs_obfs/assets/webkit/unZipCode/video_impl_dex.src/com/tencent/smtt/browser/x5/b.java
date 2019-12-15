package com.tencent.smtt.browser.x5;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class b
  extends Drawable
{
  private final int jdField_a_of_type_Int;
  private final Rect jdField_a_of_type_AndroidGraphicsRect = new Rect();
  private final Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  private final boolean jdField_a_of_type_Boolean;
  private final int b;
  private final int c;
  
  public b(boolean paramBoolean, Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = paramDrawable;
    this.jdField_a_of_type_Int = paramInt1;
    this.b = paramInt2;
    this.c = paramInt3;
    setAlpha(255);
  }
  
  public void draw(Canvas paramCanvas)
  {
    Rect localRect1 = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getBounds();
    Rect localRect2 = this.jdField_a_of_type_AndroidGraphicsRect;
    localRect2.set(getBounds());
    if (this.jdField_a_of_type_Boolean) {
      localRect2.right -= this.jdField_a_of_type_Int;
    } else {
      localRect2.bottom -= this.jdField_a_of_type_Int;
    }
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setBounds(localRect2);
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.draw(paramCanvas);
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setBounds(localRect1);
  }
  
  public int getIntrinsicHeight()
  {
    if (this.jdField_a_of_type_Boolean) {
      return this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight();
    }
    return this.b + this.jdField_a_of_type_Int;
  }
  
  public int getIntrinsicWidth()
  {
    if (this.jdField_a_of_type_Boolean) {
      return this.b + this.jdField_a_of_type_Int;
    }
    return this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth();
  }
  
  public int getOpacity()
  {
    return this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getOpacity();
  }
  
  public void setAlpha(int paramInt)
  {
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setAlpha(paramInt * this.c / 255);
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setColorFilter(paramColorFilter);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\browser\x5\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */