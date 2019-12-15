package org.chromium.android_webview;

import android.view.View.MeasureSpec;

public class AwLayoutSizer
{
  private double jdField_a_of_type_Double;
  private float jdField_a_of_type_Float = 1.0F;
  private int jdField_a_of_type_Int;
  private Delegate jdField_a_of_type_OrgChromiumAndroid_webviewAwLayoutSizer$Delegate;
  private boolean jdField_a_of_type_Boolean;
  private int jdField_b_of_type_Int;
  private boolean jdField_b_of_type_Boolean;
  private int jdField_c_of_type_Int;
  private boolean jdField_c_of_type_Boolean;
  private boolean d;
  private boolean e;
  
  private void a()
  {
    Delegate localDelegate = this.jdField_a_of_type_OrgChromiumAndroid_webviewAwLayoutSizer$Delegate;
    localDelegate.setForceZeroLayoutHeight(localDelegate.isLayoutParamsHeightWrapContent());
  }
  
  private void a(int paramInt1, int paramInt2, float paramFloat)
  {
    float f1 = paramInt2;
    float f2 = this.jdField_a_of_type_Float;
    double d1 = f1 * f2;
    double d2 = this.jdField_a_of_type_Double;
    Double.isNaN(d1);
    int j = (int)(d1 * d2);
    int m = 0;
    int i;
    if (f2 != paramFloat) {
      i = 1;
    } else {
      i = 0;
    }
    if ((!this.jdField_b_of_type_Boolean) && ((!this.e) || (j < this.jdField_c_of_type_Int))) {
      j = 1;
    } else {
      j = 0;
    }
    int k;
    if ((this.jdField_a_of_type_Boolean) && (j == 0)) {
      k = 0;
    } else {
      k = 1;
    }
    if (((this.jdField_b_of_type_Int == paramInt1) || (this.jdField_a_of_type_Boolean)) && ((this.jdField_a_of_type_Int == paramInt2) || (j == 0)))
    {
      j = m;
      if (i != 0)
      {
        j = m;
        if (k == 0) {}
      }
    }
    else
    {
      j = 1;
    }
    this.jdField_b_of_type_Int = paramInt1;
    this.jdField_a_of_type_Int = paramInt2;
    this.jdField_a_of_type_Float = paramFloat;
    if (j != 0)
    {
      if (this.jdField_c_of_type_Boolean)
      {
        this.d = true;
        return;
      }
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwLayoutSizer$Delegate.requestLayout();
    }
  }
  
  public void freezeLayoutRequests()
  {
    this.jdField_c_of_type_Boolean = true;
    this.d = false;
  }
  
  public void onContentSizeChanged(int paramInt1, int paramInt2)
  {
    a(paramInt1, paramInt2, this.jdField_a_of_type_Float);
  }
  
  public void onLayoutParamsChange()
  {
    a();
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    int m = View.MeasureSpec.getMode(paramInt2);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    int n = View.MeasureSpec.getMode(paramInt1);
    int i = View.MeasureSpec.getSize(paramInt1);
    float f1 = this.jdField_a_of_type_Int;
    float f2 = this.jdField_a_of_type_Float;
    double d2 = f1 * f2;
    double d1 = this.jdField_a_of_type_Double;
    Double.isNaN(d2);
    int k = (int)(d2 * d1);
    d2 = this.jdField_b_of_type_Int * f2;
    Double.isNaN(d2);
    int j = (int)(d2 * d1);
    boolean bool2 = true;
    boolean bool1;
    if (n != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.jdField_a_of_type_Boolean = bool1;
    if (m == 1073741824) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.jdField_b_of_type_Boolean = bool1;
    if ((m == Integer.MIN_VALUE) && (k > paramInt2)) {
      bool1 = bool2;
    } else {
      bool1 = false;
    }
    this.e = bool1;
    this.jdField_c_of_type_Int = paramInt2;
    paramInt1 = paramInt2;
    if (!this.jdField_b_of_type_Boolean) {
      if (this.e) {
        paramInt1 = paramInt2;
      } else {
        paramInt1 = k;
      }
    }
    if (this.jdField_a_of_type_Boolean) {
      paramInt2 = i;
    } else {
      paramInt2 = j;
    }
    i = paramInt1;
    if (paramInt1 < k) {
      i = paramInt1 | 0x1000000;
    }
    paramInt1 = paramInt2;
    if (paramInt2 < j) {
      paramInt1 = paramInt2 | 0x1000000;
    }
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwLayoutSizer$Delegate.setMeasuredDimension(paramInt1, i);
  }
  
  public void onPageScaleChanged(float paramFloat)
  {
    a(this.jdField_b_of_type_Int, this.jdField_a_of_type_Int, paramFloat);
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    a();
  }
  
  public void setDIPScale(double paramDouble)
  {
    this.jdField_a_of_type_Double = paramDouble;
  }
  
  public void setDelegate(Delegate paramDelegate)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwLayoutSizer$Delegate = paramDelegate;
  }
  
  public void unfreezeLayoutRequests()
  {
    this.jdField_c_of_type_Boolean = false;
    if (this.d)
    {
      this.d = false;
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwLayoutSizer$Delegate.requestLayout();
    }
  }
  
  public static abstract interface Delegate
  {
    public abstract boolean isLayoutParamsHeightWrapContent();
    
    public abstract void requestLayout();
    
    public abstract void setForceZeroLayoutHeight(boolean paramBoolean);
    
    public abstract void setMeasuredDimension(int paramInt1, int paramInt2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwLayoutSizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */