package android.webview.chromium.tencent;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewRootImpl;
import android.webview.chromium.WebViewDelegateFactory;
import android.webview.chromium.WebViewDelegateFactory.WebViewDelegate;
import android.webview.chromium.WebViewDelegateFactory.WebViewDelegate.OnTraceEnabledChangeListener;
import com.tencent.smtt.os.SMTTAdaptation;
import com.tencent.smtt.webkit.e;
import java.lang.reflect.Method;

public class TencentWebViewDelegateFactory
  extends WebViewDelegateFactory
{
  public static WebViewDelegateFactory.WebViewDelegate createApi21CompatibilityDelegate()
  {
    return new Api21CompatibilityDelegate();
  }
  
  @TargetApi(21)
  private static class Api21CompatibilityDelegate
    implements WebViewDelegateFactory.WebViewDelegate
  {
    private Method mGetViewRootImplMethod;
    
    Api21CompatibilityDelegate()
    {
      try
      {
        this.mGetViewRootImplMethod = View.class.getMethod("getViewRootImpl", new Class[0]);
        return;
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        for (;;) {}
      }
      this.mGetViewRootImplMethod = null;
    }
    
    public void addWebViewAssetPath(Context paramContext) {}
    
    public void callDrawGlFunction(Canvas paramCanvas, long paramLong)
    {
      try
      {
        SMTTAdaptation.callDrawGLFunction(paramCanvas, paramLong);
        return;
      }
      catch (Exception paramCanvas)
      {
        paramCanvas.printStackTrace();
      }
    }
    
    public void callDrawGlFunction(Canvas paramCanvas, long paramLong, Runnable paramRunnable)
    {
      try
      {
        SMTTAdaptation.drawGLFunctor2(paramCanvas, paramLong, paramRunnable);
        return;
      }
      catch (Exception paramCanvas)
      {
        paramCanvas.printStackTrace();
      }
    }
    
    public boolean canInvokeDrawGlFunctor(View paramView)
    {
      boolean bool = false;
      try
      {
        paramView = this.mGetViewRootImplMethod.invoke(paramView, new Object[0]);
        if (paramView != null) {
          bool = true;
        }
        return bool;
      }
      catch (Exception paramView)
      {
        paramView.printStackTrace();
      }
      return false;
    }
    
    public void detachDrawGlFunctor(View paramView, long paramLong)
    {
      try
      {
        paramView = this.mGetViewRootImplMethod.invoke(paramView, new Object[0]);
        if (paramView != null)
        {
          SMTTAdaptation.detachFunctor(paramView, paramLong);
          return;
        }
      }
      catch (Exception paramView)
      {
        paramView.printStackTrace();
      }
    }
    
    public Application getApplication()
    {
      return null;
    }
    
    public String getDataDirectorySuffix()
    {
      return e.a().i();
    }
    
    public String getErrorString(Context paramContext, int paramInt)
    {
      return "";
    }
    
    public int getPackageId(Resources paramResources, String paramString)
    {
      return 0;
    }
    
    public void invokeDrawGlFunctor(View paramView, long paramLong, boolean paramBoolean)
    {
      try
      {
        if (Build.VERSION.SDK_INT >= 24)
        {
          ViewRootImpl.invokeFunctor(paramLong, paramBoolean);
          return;
        }
        paramView = this.mGetViewRootImplMethod.invoke(paramView, new Object[0]);
        if (paramView != null)
        {
          SMTTAdaptation.invokeFunctor(paramView, paramLong, paramBoolean);
          return;
        }
      }
      catch (Exception paramView)
      {
        paramView.printStackTrace();
      }
    }
    
    public boolean isMultiProcessEnabled()
    {
      throw new UnsupportedOperationException();
    }
    
    public boolean isTraceTagEnabled()
    {
      return false;
    }
    
    public void setOnTraceEnabledChangeListener(WebViewDelegateFactory.WebViewDelegate.OnTraceEnabledChangeListener paramOnTraceEnabledChangeListener) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentWebViewDelegateFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */