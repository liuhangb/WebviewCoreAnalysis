package com.tencent.smtt.webkit;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.CustomViewCallback;
import com.tencent.tbs.core.webkit.WebChromeClient.CustomViewCallback;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class l
{
  private static a jdField_a_of_type_ComTencentSmttWebkitL$a;
  private final int jdField_a_of_type_Int;
  private View jdField_a_of_type_AndroidViewView;
  private final WebChromeClient.CustomViewCallback jdField_a_of_type_ComTencentTbsCoreWebkitWebChromeClient$CustomViewCallback = new WebChromeClient.CustomViewCallback()
  {
    public void onCustomViewHidden()
    {
      if (l.a(l.this) != null)
      {
        if (l.a() == null) {
          return;
        }
        l.a(l.this).fullScreenPluginHidden();
        l.a().removeView(l.a(l.this));
        l.a(null);
        l.a(l.this).getViewManager().c();
        return;
      }
    }
  };
  private final TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  
  l(TencentWebViewProxy paramTencentWebViewProxy, int paramInt)
  {
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public void a()
  {
    Object localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if (localObject == null) {
      return;
    }
    if (((TencentWebViewProxy)localObject).getViewManager() != null) {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getViewManager().b();
    }
    localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getWebChromeClient();
    if (localObject != null) {
      ((IX5WebChromeClient)localObject).onShowCustomView(jdField_a_of_type_ComTencentSmttWebkitL$a, this.jdField_a_of_type_Int, new IX5WebChromeClient.CustomViewCallback() {});
    }
  }
  
  public void a(View paramView)
  {
    jdField_a_of_type_ComTencentSmttWebkitL$a = new a(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext());
    Object localObject = new FrameLayout.LayoutParams(-1, -1, 17);
    jdField_a_of_type_ComTencentSmttWebkitL$a.addView(paramView, (ViewGroup.LayoutParams)localObject);
    jdField_a_of_type_ComTencentSmttWebkitL$a.setVisibility(0);
    if ((paramView instanceof SurfaceView))
    {
      localObject = (SurfaceView)paramView;
      if (a((SurfaceView)localObject)) {
        ((SurfaceView)localObject).getHolder().setSizeFromLayout();
      }
    }
    this.jdField_a_of_type_AndroidViewView = paramView;
  }
  
  boolean a(SurfaceView paramSurfaceView)
  {
    try
    {
      paramSurfaceView = (Boolean)Class.forName("android.view.SurfaceView").getDeclaredMethod("isFixedSize", (Class[])null).invoke(paramSurfaceView, (Object[])null);
    }
    catch (InvocationTargetException paramSurfaceView)
    {
      paramSurfaceView.printStackTrace();
    }
    catch (IllegalAccessException paramSurfaceView)
    {
      paramSurfaceView.printStackTrace();
    }
    catch (IllegalArgumentException paramSurfaceView)
    {
      paramSurfaceView.printStackTrace();
    }
    catch (NoSuchMethodException paramSurfaceView)
    {
      paramSurfaceView.printStackTrace();
    }
    catch (ClassNotFoundException paramSurfaceView)
    {
      paramSurfaceView.printStackTrace();
    }
    paramSurfaceView = Boolean.valueOf(false);
    return paramSurfaceView.booleanValue();
  }
  
  public void b()
  {
    Object localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if (localObject == null) {
      return;
    }
    localObject = ((TencentWebViewProxy)localObject).getWebChromeClient();
    if (localObject != null) {
      ((IX5WebChromeClient)localObject).onHideCustomView();
    }
  }
  
  private class a
    extends FrameLayout
  {
    a(Context paramContext)
    {
      super();
    }
    
    public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
    {
      if (paramKeyEvent.isSystem()) {
        return super.onKeyDown(paramInt, paramKeyEvent);
      }
      l.a(l.this).getRealWebView().onKeyDown(paramInt, paramKeyEvent);
      return true;
    }
    
    public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
    {
      if (paramKeyEvent.isSystem()) {
        return super.onKeyUp(paramInt, paramKeyEvent);
      }
      l.a(l.this).getRealWebView().onKeyUp(paramInt, paramKeyEvent);
      return true;
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      return true;
    }
    
    public boolean onTrackballEvent(MotionEvent paramMotionEvent)
    {
      l.a(l.this).getRealWebView().onTrackballEvent(paramMotionEvent);
      return true;
    }
    
    protected void onVisibilityChanged(View paramView, int paramInt)
    {
      super.onVisibilityChanged(paramView, paramInt);
      if (paramInt == 4) {
        l.a(l.this).getRealWebView().post(new Runnable()
        {
          public void run()
          {
            l.a(l.this).exitPluginFullScreen();
          }
        });
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\l.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */