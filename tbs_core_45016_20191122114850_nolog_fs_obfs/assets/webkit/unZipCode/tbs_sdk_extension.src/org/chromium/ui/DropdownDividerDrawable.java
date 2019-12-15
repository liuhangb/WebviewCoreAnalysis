package org.chromium.ui;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class DropdownDividerDrawable
  extends Drawable
{
  private final Paint jdField_a_of_type_AndroidGraphicsPaint = new Paint();
  private final Rect jdField_a_of_type_AndroidGraphicsRect = new Rect();
  private final Integer jdField_a_of_type_JavaLangInteger;
  
  public DropdownDividerDrawable(Integer paramInteger)
  {
    this.jdField_a_of_type_JavaLangInteger = paramInteger;
  }
  
  public void a(int paramInt)
  {
    Rect localRect = this.jdField_a_of_type_AndroidGraphicsRect;
    localRect.set(0, 0, localRect.right, paramInt);
  }
  
  public void b(int paramInt)
  {
    this.jdField_a_of_type_AndroidGraphicsPaint.setColor(paramInt);
  }
  
  public void draw(Canvas paramCanvas)
  {
    Integer localInteger = this.jdField_a_of_type_JavaLangInteger;
    if (localInteger != null) {
      paramCanvas.drawColor(localInteger.intValue());
    }
    paramCanvas.drawRect(this.jdField_a_of_type_AndroidGraphicsRect, this.jdField_a_of_type_AndroidGraphicsPaint);
  }
  
  public int getOpacity()
  {
    return -2;
  }
  
  public void onBoundsChange(Rect paramRect)
  {
    this.jdField_a_of_type_AndroidGraphicsRect.set(0, 0, paramRect.width(), this.jdField_a_of_type_AndroidGraphicsRect.height());
  }
  
  public void setAlpha(int paramInt) {}
  
  public void setColorFilter(ColorFilter paramColorFilter) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\DropdownDividerDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */