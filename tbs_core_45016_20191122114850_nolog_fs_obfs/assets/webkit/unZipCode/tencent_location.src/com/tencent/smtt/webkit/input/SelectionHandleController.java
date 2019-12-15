package com.tencent.smtt.webkit.input;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import com.tencent.smtt.webkit.WebSettingsExtension;
import org.chromium.content.browser.PositionObserver;

public abstract class SelectionHandleController
  implements CursorController
{
  private float jdField_a_of_type_Float;
  private int jdField_a_of_type_Int;
  private View jdField_a_of_type_AndroidViewView;
  private WebSettingsExtension jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension;
  private a jdField_a_of_type_ComTencentSmttWebkitInputA;
  protected b a;
  private PositionObserver jdField_a_of_type_OrgChromiumContentBrowserPositionObserver;
  private boolean jdField_a_of_type_Boolean = true;
  private float jdField_b_of_type_Float;
  private int jdField_b_of_type_Int;
  protected b b;
  private boolean jdField_b_of_type_Boolean = true;
  private float jdField_c_of_type_Float;
  private boolean jdField_c_of_type_Boolean = true;
  private float jdField_d_of_type_Float;
  private boolean jdField_d_of_type_Boolean;
  private boolean e;
  
  public SelectionHandleController(View paramView, PositionObserver paramPositionObserver, WebSettingsExtension paramWebSettingsExtension)
  {
    this.jdField_a_of_type_AndroidViewView = paramView;
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver = paramPositionObserver;
    this.jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension = paramWebSettingsExtension;
  }
  
  private void a(float paramFloat1, float paramFloat2)
  {
    if (this.jdField_a_of_type_ComTencentSmttWebkitInputA != null)
    {
      if (this.e) {
        localObject = this.jdField_b_of_type_ComTencentSmttWebkitInputB;
      } else {
        localObject = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
      }
      int j = ((b)localObject).f();
      if (this.e) {
        localObject = this.jdField_b_of_type_ComTencentSmttWebkitInputB;
      } else {
        localObject = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
      }
      int k = ((b)localObject).c();
      int i = 0;
      if (k != 0) {
        if (k == 1) {
          i = this.jdField_a_of_type_ComTencentSmttWebkitInputA.a() / 2;
        } else if (k == 2) {
          i = this.jdField_a_of_type_ComTencentSmttWebkitInputA.a();
        }
      }
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.b(j);
      Object localObject = new AbsoluteLayout.LayoutParams(-2, -2, (int)paramFloat1 - i, (int)paramFloat2);
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.setLayoutParams((ViewGroup.LayoutParams)localObject);
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.invalidate();
    }
  }
  
  private void c(int paramInt1, int paramInt2)
  {
    b localb = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
    int i = 0;
    if (localb == null)
    {
      if (paramInt1 == 2) {
        paramInt1 = 2;
      } else {
        paramInt1 = 0;
      }
      this.jdField_a_of_type_ComTencentSmttWebkitInputB = new b(this, paramInt1, this.jdField_a_of_type_AndroidViewView, this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver);
      this.jdField_a_of_type_ComTencentSmttWebkitInputB.a("startHandle");
    }
    if (this.jdField_b_of_type_ComTencentSmttWebkitInputB == null)
    {
      if (paramInt2 == 2) {
        paramInt1 = i;
      } else {
        paramInt1 = 2;
      }
      this.jdField_b_of_type_ComTencentSmttWebkitInputB = new b(this, paramInt1, this.jdField_a_of_type_AndroidViewView, this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver);
      this.jdField_b_of_type_ComTencentSmttWebkitInputB.a("endHandle");
    }
  }
  
  private void d()
  {
    if (!this.jdField_d_of_type_Boolean)
    {
      this.jdField_d_of_type_Boolean = true;
      this.jdField_a_of_type_ComTencentSmttWebkitInputB.b();
      this.jdField_b_of_type_ComTencentSmttWebkitInputB.b();
      a(0);
    }
    a(true, true);
  }
  
  public void a()
  {
    this.jdField_c_of_type_Boolean = true;
  }
  
  public void a(int paramInt)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitInputB.setVisibility(paramInt);
    this.jdField_b_of_type_ComTencentSmttWebkitInputB.setVisibility(paramInt);
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    if (this.jdField_c_of_type_Boolean) {
      b(paramInt1, paramInt2);
    }
  }
  
  public void a(b.a.a parama1, b.a.a parama2)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitInputB.a(parama1, parama2);
  }
  
  public void a(boolean paramBoolean)
  {
    a locala = this.jdField_a_of_type_ComTencentSmttWebkitInputA;
    if (locala != null)
    {
      if (locala.getVisibility() == 4) {
        return;
      }
      if (this.e == paramBoolean) {
        return;
      }
      this.e = paramBoolean;
      if (!paramBoolean)
      {
        this.jdField_a_of_type_ComTencentSmttWebkitInputA.a(this.jdField_a_of_type_ComTencentSmttWebkitInputB.c());
        this.jdField_a_of_type_ComTencentSmttWebkitInputA.a(this.jdField_a_of_type_ComTencentSmttWebkitInputB.a(), this.jdField_a_of_type_ComTencentSmttWebkitInputB.a(), this.jdField_a_of_type_ComTencentSmttWebkitInputB.a());
        this.jdField_a_of_type_ComTencentSmttWebkitInputA.a(this.jdField_a_of_type_ComTencentSmttWebkitInputB.e(), this.jdField_a_of_type_ComTencentSmttWebkitInputB.f(), this.jdField_a_of_type_ComTencentSmttWebkitInputB.d());
        this.jdField_a_of_type_ComTencentSmttWebkitInputB.a(true);
        this.jdField_b_of_type_ComTencentSmttWebkitInputB.a(false);
        return;
      }
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.a(this.jdField_b_of_type_ComTencentSmttWebkitInputB.c());
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.a(this.jdField_b_of_type_ComTencentSmttWebkitInputB.a(), this.jdField_b_of_type_ComTencentSmttWebkitInputB.a(), this.jdField_b_of_type_ComTencentSmttWebkitInputB.a());
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.a(this.jdField_b_of_type_ComTencentSmttWebkitInputB.e(), this.jdField_b_of_type_ComTencentSmttWebkitInputB.f(), this.jdField_b_of_type_ComTencentSmttWebkitInputB.d());
      this.jdField_a_of_type_ComTencentSmttWebkitInputB.a(false);
      this.jdField_b_of_type_ComTencentSmttWebkitInputB.a(true);
      return;
    }
  }
  
  public void a(boolean paramBoolean1, boolean paramBoolean2)
  {
    b localb = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
    if (localb != null) {
      localb.b(paramBoolean1);
    }
    localb = this.jdField_b_of_type_ComTencentSmttWebkitInputB;
    if (localb != null) {
      localb.b(paramBoolean2);
    }
  }
  
  public boolean a()
  {
    b localb = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
    if ((localb == null) || (!localb.b())) {
      localb = this.jdField_b_of_type_ComTencentSmttWebkitInputB;
    }
    return (localb != null) && (localb.b());
  }
  
  public boolean a(View paramView)
  {
    return (paramView == this.jdField_a_of_type_ComTencentSmttWebkitInputB) || (paramView == this.jdField_b_of_type_ComTencentSmttWebkitInputB);
  }
  
  public void afterEndUpdatingPosition(b paramb)
  {
    paramb = this.jdField_a_of_type_ComTencentSmttWebkitInputA;
    if (paramb != null)
    {
      ((ViewGroup)this.jdField_a_of_type_AndroidViewView).removeView(paramb);
      this.jdField_a_of_type_ComTencentSmttWebkitInputA = null;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitInputB.a(false);
    this.jdField_b_of_type_ComTencentSmttWebkitInputB.a(false);
    this.jdField_c_of_type_Float = 0.0F;
    this.jdField_d_of_type_Float = 0.0F;
    this.jdField_a_of_type_Float = 0.0F;
    this.jdField_b_of_type_Float = 0.0F;
    this.jdField_a_of_type_ComTencentSmttWebkitInputB.invalidate();
    this.jdField_b_of_type_ComTencentSmttWebkitInputB.invalidate();
  }
  
  public void b()
  {
    hide();
    this.jdField_c_of_type_Boolean = false;
  }
  
  public void b(int paramInt1, int paramInt2)
  {
    c(paramInt1, paramInt2);
    d();
  }
  
  public void b(b.a.a parama1, b.a.a parama2)
  {
    this.jdField_b_of_type_ComTencentSmttWebkitInputB.a(parama1, parama2);
  }
  
  public boolean b(View paramView)
  {
    a locala = this.jdField_a_of_type_ComTencentSmttWebkitInputA;
    return (locala != null) && (locala == paramView);
  }
  
  public void beforeStartUpdatingPosition(b paramb)
  {
    b localb2 = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
    b localb1 = localb2;
    if (paramb == localb2) {
      localb1 = this.jdField_b_of_type_ComTencentSmttWebkitInputB;
    }
    this.jdField_a_of_type_Int = localb1.g();
    this.jdField_b_of_type_Int = localb1.h();
    if (this.jdField_a_of_type_ComTencentSmttWebkitInputA == null)
    {
      this.jdField_a_of_type_ComTencentSmttWebkitInputA = new a(this.jdField_a_of_type_AndroidViewView.getContext());
      boolean bool;
      if (paramb == this.jdField_a_of_type_ComTencentSmttWebkitInputB) {
        bool = false;
      } else {
        bool = true;
      }
      this.e = bool;
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.a(paramb.c());
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.a(paramb.a(), paramb.a(), paramb.b());
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.a(paramb.e(), paramb.f(), paramb.d());
      paramb = new RelativeLayout.LayoutParams(paramb.a(), paramb.b());
      ((ViewGroup)this.jdField_a_of_type_AndroidViewView).addView(this.jdField_a_of_type_ComTencentSmttWebkitInputA, paramb);
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.setVisibility(4);
    }
  }
  
  public void c()
  {
    a(this.jdField_a_of_type_AndroidViewView.getScrollX() - this.jdField_c_of_type_Float + this.jdField_a_of_type_Float, this.jdField_a_of_type_AndroidViewView.getScrollY() - this.jdField_d_of_type_Float + this.jdField_b_of_type_Float);
  }
  
  public void hide()
  {
    if (this.jdField_d_of_type_Boolean)
    {
      b localb = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
      if (localb != null) {
        localb.c();
      }
      localb = this.jdField_b_of_type_ComTencentSmttWebkitInputB;
      if (localb != null) {
        localb.c();
      }
      this.jdField_d_of_type_Boolean = false;
    }
  }
  
  public boolean isNightMode()
  {
    WebSettingsExtension localWebSettingsExtension = this.jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension;
    boolean bool2 = false;
    boolean bool1 = bool2;
    if (localWebSettingsExtension != null)
    {
      bool1 = bool2;
      if (!localWebSettingsExtension.getDayOrNight()) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public boolean isShowing()
  {
    return this.jdField_d_of_type_Boolean;
  }
  
  public void onDetached() {}
  
  public void onTouchModeChanged(boolean paramBoolean)
  {
    if (!paramBoolean) {
      hide();
    }
  }
  
  protected abstract void selectBetweenCoordinates(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  public void show()
  {
    if (!this.jdField_d_of_type_Boolean)
    {
      b localb = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
      if (localb != null) {
        localb.b();
      }
      localb = this.jdField_b_of_type_ComTencentSmttWebkitInputB;
      if (localb != null) {
        localb.b();
      }
      if ((this.jdField_a_of_type_ComTencentSmttWebkitInputB != null) && (this.jdField_b_of_type_ComTencentSmttWebkitInputB != null)) {
        this.jdField_d_of_type_Boolean = true;
      }
    }
  }
  
  public void updateHandleViewSliderPosition(float paramFloat1, float paramFloat2)
  {
    a locala = this.jdField_a_of_type_ComTencentSmttWebkitInputA;
    if ((locala != null) && (locala.getVisibility() == 4))
    {
      this.jdField_a_of_type_ComTencentSmttWebkitInputB.invalidate();
      this.jdField_b_of_type_ComTencentSmttWebkitInputB.invalidate();
      this.jdField_a_of_type_ComTencentSmttWebkitInputA.setVisibility(0);
    }
    if (this.e) {
      this.jdField_b_of_type_ComTencentSmttWebkitInputB.a(true);
    } else {
      this.jdField_a_of_type_ComTencentSmttWebkitInputB.a(true);
    }
    this.jdField_a_of_type_Float = paramFloat1;
    this.jdField_b_of_type_Float = paramFloat2;
    this.jdField_c_of_type_Float = this.jdField_a_of_type_AndroidViewView.getScrollX();
    this.jdField_d_of_type_Float = this.jdField_a_of_type_AndroidViewView.getScrollY();
    a(paramFloat1, paramFloat2);
  }
  
  public void updatePosition(b paramb, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    selectBetweenCoordinates(this.jdField_a_of_type_Int, this.jdField_b_of_type_Int, paramInt1, paramInt2, paramInt3, paramInt4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\input\SelectionHandleController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */