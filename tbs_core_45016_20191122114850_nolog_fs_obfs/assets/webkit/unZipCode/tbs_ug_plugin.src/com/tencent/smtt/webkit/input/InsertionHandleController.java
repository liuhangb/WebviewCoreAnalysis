package com.tencent.smtt.webkit.input;

import android.content.Context;
import android.view.View;
import com.tencent.smtt.webkit.WebSettingsExtension;
import org.chromium.content.browser.PositionObserver;

public abstract class InsertionHandleController
  implements CursorController
{
  private final Context jdField_a_of_type_AndroidContentContext;
  private final View jdField_a_of_type_AndroidViewView;
  private WebSettingsExtension jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension;
  private b jdField_a_of_type_ComTencentSmttWebkitInputB;
  private final PositionObserver jdField_a_of_type_OrgChromiumContentBrowserPositionObserver;
  private boolean jdField_a_of_type_Boolean;
  private boolean b;
  
  public InsertionHandleController(View paramView, PositionObserver paramPositionObserver, WebSettingsExtension paramWebSettingsExtension)
  {
    this.jdField_a_of_type_AndroidViewView = paramView;
    this.jdField_a_of_type_AndroidContentContext = paramView.getContext();
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver = paramPositionObserver;
    this.jdField_a_of_type_ComTencentSmttWebkitWebSettingsExtension = paramWebSettingsExtension;
  }
  
  private void e()
  {
    if (this.jdField_a_of_type_ComTencentSmttWebkitInputB == null)
    {
      this.jdField_a_of_type_ComTencentSmttWebkitInputB = new b(this, 1, this.jdField_a_of_type_AndroidViewView, this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver);
      this.jdField_a_of_type_ComTencentSmttWebkitInputB.a("insertHandle");
    }
  }
  
  private void f()
  {
    if (!this.jdField_a_of_type_Boolean)
    {
      this.jdField_a_of_type_Boolean = true;
      this.jdField_a_of_type_ComTencentSmttWebkitInputB.b();
      a(0);
    }
  }
  
  public void a()
  {
    this.b = true;
  }
  
  public void a(int paramInt)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitInputB.setVisibility(paramInt);
  }
  
  public void a(b.a.a parama1, b.a.a parama2)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitInputB.a(parama1, parama2);
  }
  
  public void a(boolean paramBoolean)
  {
    b localb = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
    if (localb != null) {
      localb.b(paramBoolean);
    }
  }
  
  public boolean a(View paramView)
  {
    return paramView == this.jdField_a_of_type_ComTencentSmttWebkitInputB;
  }
  
  public void afterEndUpdatingPosition(b paramb) {}
  
  public void b()
  {
    hide();
    this.b = false;
  }
  
  public void beforeStartUpdatingPosition(b paramb) {}
  
  public void c()
  {
    e();
    f();
  }
  
  public void d()
  {
    if (this.b) {
      c();
    }
  }
  
  protected abstract int getLineHeight();
  
  public void hide()
  {
    if (this.jdField_a_of_type_Boolean)
    {
      b localb = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
      if (localb != null) {
        localb.c();
      }
      this.jdField_a_of_type_Boolean = false;
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
    return this.jdField_a_of_type_Boolean;
  }
  
  public void onDetached() {}
  
  public void onTouchModeChanged(boolean paramBoolean)
  {
    if (!paramBoolean) {
      hide();
    }
  }
  
  protected abstract void paste();
  
  protected abstract void setCursorPosition(int paramInt1, int paramInt2);
  
  public void show()
  {
    if (!this.jdField_a_of_type_Boolean)
    {
      b localb = this.jdField_a_of_type_ComTencentSmttWebkitInputB;
      if (localb != null)
      {
        localb.b();
        this.jdField_a_of_type_Boolean = true;
      }
    }
  }
  
  public void updateHandleViewSliderPosition(float paramFloat1, float paramFloat2) {}
  
  public void updatePosition(b paramb, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    setCursorPosition(paramInt1, paramInt2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\input\InsertionHandleController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */