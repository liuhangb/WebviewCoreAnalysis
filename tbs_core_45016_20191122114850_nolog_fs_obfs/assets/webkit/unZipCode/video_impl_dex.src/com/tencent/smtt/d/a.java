package com.tencent.smtt.d;

import android.graphics.Canvas;
import android.graphics.Canvas.EdgeType;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

public class a
  extends Drawable
{
  private int jdField_a_of_type_Int;
  private final Rect jdField_a_of_type_AndroidGraphicsRect = new Rect();
  private Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  private boolean jdField_a_of_type_Boolean;
  private int jdField_b_of_type_Int;
  private Rect jdField_b_of_type_AndroidGraphicsRect;
  private Drawable jdField_b_of_type_AndroidGraphicsDrawableDrawable;
  private boolean jdField_b_of_type_Boolean;
  private int jdField_c_of_type_Int;
  private Drawable jdField_c_of_type_AndroidGraphicsDrawableDrawable;
  private boolean jdField_c_of_type_Boolean;
  private int jdField_d_of_type_Int;
  private Drawable jdField_d_of_type_AndroidGraphicsDrawableDrawable;
  private boolean jdField_d_of_type_Boolean;
  private boolean e;
  
  public int a(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
      if ((localDrawable != null) && (this.e)) {
        return localDrawable.getIntrinsicWidth();
      }
      localDrawable = this.jdField_c_of_type_AndroidGraphicsDrawableDrawable;
      if (localDrawable != null) {
        return localDrawable.getIntrinsicWidth();
      }
      return 0;
    }
    Drawable localDrawable = this.jdField_b_of_type_AndroidGraphicsDrawableDrawable;
    if ((localDrawable != null) && (this.jdField_d_of_type_Boolean)) {
      return localDrawable.getIntrinsicWidth();
    }
    localDrawable = this.jdField_d_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      return localDrawable.getIntrinsicWidth();
    }
    return 0;
  }
  
  public Rect a(boolean paramBoolean)
  {
    if (this.jdField_b_of_type_AndroidGraphicsRect == null) {
      this.jdField_b_of_type_AndroidGraphicsRect = new Rect();
    }
    Drawable localDrawable;
    if (paramBoolean)
    {
      if (this.e)
      {
        localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
        if (localDrawable != null) {}
      }
      else
      {
        localDrawable = this.jdField_c_of_type_AndroidGraphicsDrawableDrawable;
      }
    }
    else if (this.jdField_d_of_type_Boolean)
    {
      localDrawable = this.jdField_b_of_type_AndroidGraphicsDrawableDrawable;
      if (localDrawable != null) {}
    }
    else
    {
      localDrawable = this.jdField_d_of_type_AndroidGraphicsDrawableDrawable;
    }
    if (localDrawable != null) {
      this.jdField_b_of_type_AndroidGraphicsRect.set(localDrawable.getBounds());
    } else {
      this.jdField_b_of_type_AndroidGraphicsRect.setEmpty();
    }
    return this.jdField_b_of_type_AndroidGraphicsRect;
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    if (this.jdField_a_of_type_Boolean != paramBoolean) {
      this.jdField_b_of_type_Boolean = true;
    }
    if ((this.jdField_a_of_type_Int != paramInt1) || (this.jdField_b_of_type_Int != paramInt2) || (this.jdField_c_of_type_Int != paramInt3)) {
      this.jdField_c_of_type_Boolean = true;
    }
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    this.jdField_c_of_type_Int = paramInt3;
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  protected void a(Canvas paramCanvas, Rect paramRect, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Rect localRect = this.jdField_a_of_type_AndroidGraphicsRect;
    int i;
    if ((!this.jdField_c_of_type_Boolean) && (!this.jdField_b_of_type_Boolean)) {
      i = 0;
    } else {
      i = 1;
    }
    if (i != 0) {
      if (paramBoolean) {
        localRect.set(paramRect.left, paramRect.top + paramInt1, paramRect.right, paramRect.top + paramInt1 + paramInt2);
      } else {
        localRect.set(paramRect.left + paramInt1, paramRect.top, paramRect.left + paramInt1 + paramInt2, paramRect.bottom);
      }
    }
    if (paramBoolean)
    {
      paramRect = this.jdField_c_of_type_AndroidGraphicsDrawableDrawable;
      if (paramRect != null)
      {
        if (i != 0) {
          paramRect.setBounds(localRect);
        }
        paramRect.draw(paramCanvas);
      }
    }
    else
    {
      paramRect = this.jdField_d_of_type_AndroidGraphicsDrawableDrawable;
      if (paramRect != null)
      {
        if (i != 0) {
          paramRect.setBounds(localRect);
        }
        paramRect.draw(paramCanvas);
      }
    }
  }
  
  protected void a(Canvas paramCanvas, Rect paramRect, boolean paramBoolean, int paramInt)
  {
    Drawable localDrawable;
    if (paramBoolean) {
      localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    } else {
      localDrawable = this.jdField_b_of_type_AndroidGraphicsDrawableDrawable;
    }
    if (localDrawable != null)
    {
      Rect localRect = this.jdField_a_of_type_AndroidGraphicsRect;
      if (paramBoolean)
      {
        localRect.left = (paramRect.right - localDrawable.getIntrinsicWidth());
        localRect.top = (paramInt + paramRect.top);
        localRect.right = paramRect.right;
        localRect.bottom = (localRect.top + localDrawable.getIntrinsicHeight());
      }
      else
      {
        localRect.left = (paramInt + paramRect.left);
        localRect.top = (paramRect.bottom - localDrawable.getIntrinsicHeight());
        localRect.right = (localRect.left + localDrawable.getIntrinsicWidth());
        localRect.bottom = paramRect.bottom;
      }
      localDrawable.setBounds(localRect);
      localDrawable.draw(paramCanvas);
    }
  }
  
  public void a(Drawable paramDrawable)
  {
    if (paramDrawable != null) {
      this.jdField_c_of_type_AndroidGraphicsDrawableDrawable = paramDrawable;
    }
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_d_of_type_Boolean = paramBoolean;
  }
  
  public int b(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
      if ((localDrawable != null) && (this.e)) {
        return localDrawable.getIntrinsicHeight();
      }
      localDrawable = this.jdField_c_of_type_AndroidGraphicsDrawableDrawable;
      if (localDrawable != null) {
        return localDrawable.getIntrinsicHeight();
      }
      return 0;
    }
    Drawable localDrawable = this.jdField_b_of_type_AndroidGraphicsDrawableDrawable;
    if ((localDrawable != null) && (this.jdField_d_of_type_Boolean)) {
      return localDrawable.getIntrinsicHeight();
    }
    localDrawable = this.jdField_d_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      return localDrawable.getIntrinsicHeight();
    }
    return 0;
  }
  
  public void b(Drawable paramDrawable)
  {
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = paramDrawable;
  }
  
  public void b(boolean paramBoolean)
  {
    this.e = paramBoolean;
  }
  
  public void c(Drawable paramDrawable)
  {
    if (paramDrawable != null) {
      this.jdField_d_of_type_AndroidGraphicsDrawableDrawable = paramDrawable;
    }
  }
  
  public void d(Drawable paramDrawable)
  {
    this.jdField_b_of_type_AndroidGraphicsDrawableDrawable = paramDrawable;
  }
  
  public void draw(Canvas paramCanvas)
  {
    boolean bool = this.jdField_a_of_type_Boolean;
    int m = this.jdField_c_of_type_Int;
    int n = this.jdField_a_of_type_Int;
    int j = 1;
    int i;
    if (bool ? (this.e) && (this.jdField_a_of_type_AndroidGraphicsDrawableDrawable != null) : (this.jdField_d_of_type_Boolean) && (this.jdField_b_of_type_AndroidGraphicsDrawableDrawable != null)) {
      i = 1;
    } else {
      i = 0;
    }
    if ((i != 0) || (n <= m)) {
      j = 0;
    }
    Rect localRect = getBounds();
    if (paramCanvas.quickReject(localRect.left, localRect.top, localRect.right, localRect.bottom, Canvas.EdgeType.AA)) {
      return;
    }
    if (i != 0)
    {
      if (bool) {
        i = localRect.height();
      } else {
        i = localRect.width();
      }
      if (bool) {
        j = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight();
      } else {
        j = this.jdField_b_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth();
      }
      a(paramCanvas, localRect, bool, Math.round((i - j) * this.jdField_b_of_type_Int / (n - m)));
      return;
    }
    if (j != 0)
    {
      if (bool) {
        i = localRect.height();
      } else {
        i = localRect.width();
      }
      if (bool) {
        j = localRect.width();
      } else {
        j = localRect.height();
      }
      int k = Math.round(i * m / n);
      m = Math.round((i - k) * this.jdField_b_of_type_Int / (n - m));
      j *= 2;
      if (k >= j) {
        j = k;
      }
      if (m + j > i) {
        i -= j;
      } else {
        i = m;
      }
      a(paramCanvas, localRect, i, j, bool);
    }
  }
  
  public int getAlpha()
  {
    return this.jdField_d_of_type_Int;
  }
  
  public int getOpacity()
  {
    return -3;
  }
  
  protected void onBoundsChange(Rect paramRect)
  {
    super.onBoundsChange(paramRect);
    this.jdField_b_of_type_Boolean = true;
  }
  
  public void setAlpha(int paramInt)
  {
    this.jdField_d_of_type_Int = paramInt;
    Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setAlpha(paramInt);
    }
    localDrawable = this.jdField_c_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setAlpha(paramInt);
    }
    localDrawable = this.jdField_b_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setAlpha(paramInt);
    }
    localDrawable = this.jdField_d_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setAlpha(paramInt);
    }
  }
  
  public void setColorFilter(ColorFilter paramColorFilter)
  {
    Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setColorFilter(paramColorFilter);
    }
    localDrawable = this.jdField_c_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setColorFilter(paramColorFilter);
    }
    localDrawable = this.jdField_b_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setColorFilter(paramColorFilter);
    }
    localDrawable = this.jdField_d_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setColorFilter(paramColorFilter);
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ScrollBarDrawable: range=");
    localStringBuilder.append(this.jdField_a_of_type_Int);
    localStringBuilder.append(" offset=");
    localStringBuilder.append(this.jdField_b_of_type_Int);
    localStringBuilder.append(" extent=");
    localStringBuilder.append(this.jdField_c_of_type_Int);
    String str;
    if (this.jdField_a_of_type_Boolean) {
      str = " V";
    } else {
      str = " H";
    }
    localStringBuilder.append(str);
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */