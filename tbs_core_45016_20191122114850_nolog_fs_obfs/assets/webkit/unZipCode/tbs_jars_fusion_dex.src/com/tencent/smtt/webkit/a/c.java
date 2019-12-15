package com.tencent.smtt.webkit.a;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Scroller;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.WebBackForwardList;
import com.tencent.tbs.core.webkit.WebHistoryItem;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.util.HashMap;
import java.util.Map;

public class c
  extends LinearLayout
{
  int jdField_a_of_type_Int = 500;
  private ImageView jdField_a_of_type_AndroidWidgetImageView;
  private Scroller jdField_a_of_type_AndroidWidgetScroller;
  private a jdField_a_of_type_ComTencentSmttWebkitAA;
  private a jdField_a_of_type_ComTencentSmttWebkitAC$a = a.jdField_a_of_type_ComTencentSmttWebkitAC$a;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private final String jdField_a_of_type_JavaLangString = "tbs_bar";
  private boolean jdField_a_of_type_Boolean = false;
  private int jdField_b_of_type_Int;
  private ImageView jdField_b_of_type_AndroidWidgetImageView;
  private String jdField_b_of_type_JavaLangString = null;
  private boolean jdField_b_of_type_Boolean = false;
  private ImageView jdField_c_of_type_AndroidWidgetImageView;
  private String jdField_c_of_type_JavaLangString = "BZDB001";
  private boolean jdField_c_of_type_Boolean = false;
  private String d = "BZDB002";
  private String e = "BZDB003";
  private String f = "BZDB004";
  private String g = "BZDB005";
  private String h = "BZDB006";
  private String i = "BZDB007";
  private String j = "BZDB008";
  
  public c(Context paramContext, TencentWebViewProxy paramTencentWebViewProxy, a parama)
  {
    super(paramContext);
    setOrientation(0);
    setBackgroundColor(Color.argb(245, 255, 255, 255));
    this.jdField_a_of_type_ComTencentSmttWebkitAA = parama;
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    int k = paramTencentWebViewProxy.getWidth() / 15;
    this.jdField_a_of_type_AndroidWidgetImageView = new ImageView(paramContext);
    this.jdField_a_of_type_AndroidWidgetImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    paramTencentWebViewProxy = a("x5_bar_back");
    this.jdField_a_of_type_AndroidWidgetImageView.setImageDrawable(paramTencentWebViewProxy);
    this.jdField_a_of_type_AndroidWidgetImageView.setBackgroundColor(0);
    paramTencentWebViewProxy = new LinearLayout.LayoutParams(k, k);
    paramTencentWebViewProxy.weight = 1.0F;
    paramTencentWebViewProxy.gravity = 17;
    this.jdField_a_of_type_AndroidWidgetImageView.setLayoutParams(paramTencentWebViewProxy);
    this.jdField_a_of_type_AndroidWidgetImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics(c.a(c.this));
        paramAnonymousView = c.this;
        c.a(paramAnonymousView, c.a(paramAnonymousView).getUrl());
        paramAnonymousView = new HashMap();
        paramAnonymousView.put("domain", TencentURLUtil.getHost(c.b(c.this)));
        SmttServiceProxy.getInstance().upLoadToBeacon(c.c(c.this), paramAnonymousView);
        if ((c.a(c.this) != null) && (c.a(c.this).canGoBack()) && (c.a(c.this))) {
          c.a(c.this).goBack();
        }
      }
    });
    addView(this.jdField_a_of_type_AndroidWidgetImageView);
    this.jdField_b_of_type_AndroidWidgetImageView = new ImageView(paramContext);
    this.jdField_b_of_type_AndroidWidgetImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    paramTencentWebViewProxy = a("x5_bar_forward");
    this.jdField_b_of_type_AndroidWidgetImageView.setImageDrawable(paramTencentWebViewProxy);
    this.jdField_b_of_type_AndroidWidgetImageView.setBackgroundColor(0);
    paramTencentWebViewProxy = new LinearLayout.LayoutParams(k, k);
    paramTencentWebViewProxy.weight = 1.0F;
    paramTencentWebViewProxy.gravity = 17;
    this.jdField_b_of_type_AndroidWidgetImageView.setLayoutParams(paramTencentWebViewProxy);
    this.jdField_b_of_type_AndroidWidgetImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics(c.d(c.this));
        paramAnonymousView = c.this;
        c.a(paramAnonymousView, c.a(paramAnonymousView).getUrl());
        paramAnonymousView = new HashMap();
        paramAnonymousView.put("domain", TencentURLUtil.getHost(c.b(c.this)));
        SmttServiceProxy.getInstance().upLoadToBeacon(c.e(c.this), paramAnonymousView);
        if ((c.a(c.this) != null) && (c.a(c.this).canGoForward())) {
          c.a(c.this).goForward();
        }
      }
    });
    addView(this.jdField_b_of_type_AndroidWidgetImageView);
    this.jdField_c_of_type_AndroidWidgetImageView = new ImageView(paramContext);
    this.jdField_c_of_type_AndroidWidgetImageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
    paramTencentWebViewProxy = a("x5_bar_reload");
    this.jdField_c_of_type_AndroidWidgetImageView.setImageDrawable(paramTencentWebViewProxy);
    this.jdField_c_of_type_AndroidWidgetImageView.setBackgroundColor(0);
    paramTencentWebViewProxy = new LinearLayout.LayoutParams(k, k);
    paramTencentWebViewProxy.weight = 1.0F;
    paramTencentWebViewProxy.gravity = 17;
    this.jdField_c_of_type_AndroidWidgetImageView.setLayoutParams(paramTencentWebViewProxy);
    this.jdField_c_of_type_AndroidWidgetImageView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics(c.f(c.this));
        paramAnonymousView = c.this;
        c.a(paramAnonymousView, c.a(paramAnonymousView).getUrl());
        paramAnonymousView = new HashMap();
        paramAnonymousView.put("domain", TencentURLUtil.getHost(c.b(c.this)));
        SmttServiceProxy.getInstance().upLoadToBeacon(c.g(c.this), paramAnonymousView);
        if (c.a(c.this) != null) {
          c.a(c.this).reload();
        }
      }
    });
    addView(this.jdField_c_of_type_AndroidWidgetImageView);
    this.jdField_a_of_type_AndroidWidgetScroller = new Scroller(paramContext);
  }
  
  private Drawable a(Bitmap paramBitmap)
  {
    Resources localResources = SmttResource.getResources();
    int k = localResources.getDisplayMetrics().densityDpi;
    if (paramBitmap != null)
    {
      paramBitmap.setDensity(k);
      return new BitmapDrawable(localResources, paramBitmap);
    }
    return null;
  }
  
  private Drawable a(String paramString)
  {
    Resources localResources = SmttResource.getResources();
    try
    {
      k = SmttResource.loadIdentifierResource(paramString, "drawable");
    }
    catch (RuntimeException paramString)
    {
      int k;
      Drawable localDrawable;
      for (;;) {}
    }
    k = 0;
    paramString = new BitmapFactory.Options();
    paramString.inScaled = true;
    localDrawable = a(BitmapFactory.decodeResource(localResources, k, paramString));
    paramString = localDrawable;
    if (localDrawable == null) {
      paramString = localResources.getDrawable(k);
    }
    return paramString;
  }
  
  private boolean c()
  {
    try
    {
      WebBackForwardList localWebBackForwardList = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().copyBackForwardList();
      boolean bool = localWebBackForwardList.getItemAtIndex(localWebBackForwardList.getCurrentIndex() - 1).getUrl().equals("about:blank");
      return !bool;
    }
    catch (Exception localException) {}
    return false;
  }
  
  public void a()
  {
    try
    {
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy != null)
      {
        if ((this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.canGoBack()) && (c())) {
          this.jdField_a_of_type_AndroidWidgetImageView.setImageAlpha(255);
        } else {
          this.jdField_a_of_type_AndroidWidgetImageView.setImageAlpha(120);
        }
        if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.canGoForward())
        {
          this.jdField_b_of_type_AndroidWidgetImageView.setImageAlpha(255);
          return;
        }
        this.jdField_b_of_type_AndroidWidgetImageView.setImageAlpha(120);
      }
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_b_of_type_Boolean = paramBoolean;
  }
  
  public boolean a()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public void b()
  {
    if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContentHeight() <= this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getHeight())
    {
      if (this.jdField_a_of_type_ComTencentSmttWebkitAC$a == a.jdField_a_of_type_ComTencentSmttWebkitAC$a) {
        g();
      }
    }
    else if ((this.jdField_a_of_type_ComTencentSmttWebkitAC$a == a.b) && (!this.jdField_b_of_type_Boolean) && (!this.jdField_c_of_type_Boolean)) {
      e();
    }
  }
  
  public void b(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.jdField_a_of_type_ComTencentSmttWebkitAC$a = a.jdField_a_of_type_ComTencentSmttWebkitAC$a;
      return;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitAC$a = a.b;
  }
  
  public boolean b()
  {
    return this.jdField_c_of_type_Boolean;
  }
  
  public void c()
  {
    if (this.jdField_a_of_type_ComTencentSmttWebkitAC$a == a.jdField_a_of_type_ComTencentSmttWebkitAC$a)
    {
      Object localObject = this.jdField_b_of_type_JavaLangString;
      if ((localObject == null) || (!((String)localObject).equals(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl())))
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics(this.jdField_c_of_type_JavaLangString);
        this.jdField_b_of_type_JavaLangString = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getUrl();
        localObject = new HashMap();
        ((Map)localObject).put("domain", TencentURLUtil.getHost(this.jdField_b_of_type_JavaLangString));
        try
        {
          ((Map)localObject).put("coreversion", ContextHolder.getInstance().getTbsCoreVersion());
          ((Map)localObject).put("packageName", this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext().getApplicationContext().getPackageName());
        }
        catch (Exception localException)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(localException.getMessage());
          localStringBuilder.append("&&");
          localStringBuilder.append(localException.getCause());
          ((Map)localObject).put("packageName", localStringBuilder.toString());
        }
        SmttServiceProxy.getInstance().upLoadToBeacon(this.g, (Map)localObject);
      }
    }
  }
  
  public void computeScroll()
  {
    super.computeScroll();
    if (this.jdField_a_of_type_AndroidWidgetScroller.computeScrollOffset())
    {
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)getLayoutParams();
      if (this.jdField_a_of_type_ComTencentSmttWebkitAC$a == a.jdField_a_of_type_ComTencentSmttWebkitAC$a) {
        localLayoutParams.bottomMargin = (this.jdField_a_of_type_AndroidWidgetScroller.getCurrY() - this.jdField_b_of_type_Int);
      } else if (this.jdField_a_of_type_ComTencentSmttWebkitAC$a == a.b) {
        localLayoutParams.bottomMargin = (0 - this.jdField_a_of_type_AndroidWidgetScroller.getCurrY());
      }
      requestLayout();
    }
  }
  
  public void d()
  {
    this.jdField_b_of_type_Boolean = true;
    this.jdField_a_of_type_ComTencentSmttWebkitAC$a = a.jdField_a_of_type_ComTencentSmttWebkitAC$a;
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)getLayoutParams();
    this.jdField_a_of_type_AndroidWidgetScroller.startScroll(0, getScrollY(), 0, this.jdField_b_of_type_Int, this.jdField_a_of_type_Int);
    localLayoutParams.bottomMargin = (0 - this.jdField_b_of_type_Int + 1);
    requestLayout();
    c();
  }
  
  public void e()
  {
    if (this.jdField_c_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitAA.a(true);
    this.jdField_a_of_type_Boolean = true;
    this.jdField_b_of_type_Boolean = false;
    this.jdField_a_of_type_ComTencentSmttWebkitAC$a = a.jdField_a_of_type_ComTencentSmttWebkitAC$a;
    ((FrameLayout.LayoutParams)getLayoutParams()).bottomMargin = 0;
    requestLayout();
    c();
  }
  
  public void f()
  {
    this.jdField_b_of_type_Boolean = true;
    this.jdField_a_of_type_ComTencentSmttWebkitAC$a = a.b;
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)getLayoutParams();
    this.jdField_b_of_type_Int = localLayoutParams.height;
    this.jdField_a_of_type_AndroidWidgetScroller.startScroll(0, getScrollY(), 0, this.jdField_b_of_type_Int, this.jdField_a_of_type_Int);
    localLayoutParams.bottomMargin = -1;
    requestLayout();
  }
  
  public void g()
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_ComTencentSmttWebkitAA.a(false);
    this.jdField_b_of_type_Boolean = false;
    this.jdField_a_of_type_ComTencentSmttWebkitAC$a = a.b;
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)getLayoutParams();
    localLayoutParams.bottomMargin = (0 - localLayoutParams.height);
    requestLayout();
  }
  
  public void h()
  {
    this.jdField_c_of_type_Boolean = true;
    this.jdField_b_of_type_Boolean = false;
    this.jdField_a_of_type_ComTencentSmttWebkitAC$a = a.b;
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)getLayoutParams();
    localLayoutParams.bottomMargin = (0 - localLayoutParams.height);
    this.jdField_a_of_type_ComTencentSmttWebkitAA.a(false);
    requestLayout();
  }
  
  public static enum a
  {
    static
    {
      jdField_a_of_type_ComTencentSmttWebkitAC$a = new a("SHOW", 0);
      b = new a("HIDE", 1);
      jdField_a_of_type_ArrayOfComTencentSmttWebkitAC$a = new a[] { jdField_a_of_type_ComTencentSmttWebkitAC$a, b };
    }
    
    private a() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */