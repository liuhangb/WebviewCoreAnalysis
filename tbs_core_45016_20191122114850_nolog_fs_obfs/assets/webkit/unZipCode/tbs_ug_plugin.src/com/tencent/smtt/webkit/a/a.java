package com.tencent.smtt.webkit.a;

import android.content.Context;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Scroller;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;

public class a
{
  private Context jdField_a_of_type_AndroidContentContext = null;
  private Scroller jdField_a_of_type_AndroidWidgetScroller;
  private c jdField_a_of_type_ComTencentSmttWebkitAC = null;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = null;
  private final String jdField_a_of_type_JavaLangString = "tbs_bar";
  private boolean jdField_a_of_type_Boolean = true;
  
  public a(Context paramContext, TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    c();
  }
  
  private void c()
  {
    this.jdField_a_of_type_ComTencentSmttWebkitAC = new c(this.jdField_a_of_type_AndroidContentContext, this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy, this);
    int i = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWidth() * 2 / 15;
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(-1, i);
    localLayoutParams.gravity = 80;
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContentHeight() <= this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getHeight())
    {
      localLayoutParams.bottomMargin = (0 - i);
      this.jdField_a_of_type_ComTencentSmttWebkitAC.a(false);
      this.jdField_a_of_type_ComTencentSmttWebkitAC.b(false);
      this.jdField_a_of_type_Boolean = false;
    }
    else
    {
      localLayoutParams.bottomMargin = 0;
      this.jdField_a_of_type_ComTencentSmttWebkitAC.a(false);
      this.jdField_a_of_type_ComTencentSmttWebkitAC.b(true);
      this.jdField_a_of_type_Boolean = true;
    }
    if ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getParent() instanceof FrameLayout)) {
      ((FrameLayout)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getParent()).addView(this.jdField_a_of_type_ComTencentSmttWebkitAC, localLayoutParams);
    }
    this.jdField_a_of_type_AndroidWidgetScroller = new Scroller(this.jdField_a_of_type_AndroidContentContext);
  }
  
  public c a()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitAC;
  }
  
  public void a()
  {
    if (!this.jdField_a_of_type_Boolean)
    {
      this.jdField_a_of_type_ComTencentSmttWebkitAC.d();
      this.jdField_a_of_type_Boolean = true;
    }
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public void b()
  {
    if (this.jdField_a_of_type_Boolean)
    {
      this.jdField_a_of_type_ComTencentSmttWebkitAC.f();
      this.jdField_a_of_type_Boolean = false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */