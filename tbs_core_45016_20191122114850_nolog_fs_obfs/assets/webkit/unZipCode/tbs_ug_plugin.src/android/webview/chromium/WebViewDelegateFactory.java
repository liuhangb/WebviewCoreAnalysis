package android.webview.chromium;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Trace;
import android.util.SparseArray;
import android.view.View;
import android.webkit.WebViewDelegate;
import android.webkit.WebViewDelegate.OnTraceEnabledChangeListener;
import java.lang.reflect.Method;

public class WebViewDelegateFactory
{
  public static WebViewDelegate createApi21CompatibilityDelegate()
  {
    return new Api21CompatibilityDelegate();
  }
  
  static WebViewDelegate createProxyDelegate(WebViewDelegate paramWebViewDelegate)
  {
    return new ProxyDelegate(paramWebViewDelegate);
  }
  
  private static class Api21CompatibilityDelegate
    implements WebViewDelegateFactory.WebViewDelegate
  {
    private static final long TRACE_TAG_WEBVIEW = 16L;
    private final Method mAddAssetPathMethod;
    private final Method mAddChangeCallbackMethod;
    private final Method mCallDrawGLFunctionMethod;
    private final Method mCurrentApplicationMethod;
    private final Method mDetachFunctorMethod;
    private final Method mGetAssignedPackageIdentifiersMethod;
    private final Method mGetLoadedPackageInfoMethod;
    private final Method mGetStringMethod;
    private final Method mGetViewRootImplMethod;
    private final Method mInvokeFunctorMethod;
    private final Method mIsTagEnabledMethod;
    
    public Api21CompatibilityDelegate()
    {
      try
      {
        this.mIsTagEnabledMethod = Trace.class.getMethod("isTagEnabled", new Class[] { Long.TYPE });
        this.mAddChangeCallbackMethod = Class.forName("android.os.SystemProperties").getMethod("addChangeCallback", new Class[] { Runnable.class });
        this.mGetViewRootImplMethod = View.class.getMethod("getViewRootImpl", new Class[0]);
        this.mInvokeFunctorMethod = Class.forName("android.view.ViewRootImpl").getMethod("invokeFunctor", new Class[] { Long.TYPE, Boolean.TYPE });
        this.mDetachFunctorMethod = Class.forName("android.view.ViewRootImpl").getMethod("detachFunctor", new Class[] { Long.TYPE });
        this.mCallDrawGLFunctionMethod = Class.forName("android.view.HardwareCanvas").getMethod("callDrawGLFunction", new Class[] { Long.TYPE });
        this.mGetAssignedPackageIdentifiersMethod = AssetManager.class.getMethod("getAssignedPackageIdentifiers", new Class[0]);
        this.mAddAssetPathMethod = AssetManager.class.getMethod("addAssetPath", new Class[] { String.class });
        this.mCurrentApplicationMethod = Class.forName("android.app.ActivityThread").getMethod("currentApplication", new Class[0]);
        this.mGetStringMethod = Class.forName("android.net.http.ErrorStrings").getMethod("getString", new Class[] { Integer.TYPE, Context.class });
        this.mGetLoadedPackageInfoMethod = Class.forName("android.webkit.WebViewFactory").getMethod("getLoadedPackageInfo", new Class[0]);
        return;
      }
      catch (Exception localException)
      {
        throw new RuntimeException("Invalid reflection", localException);
      }
    }
    
    public void addWebViewAssetPath(Context paramContext)
    {
      try
      {
        PackageInfo localPackageInfo = (PackageInfo)this.mGetLoadedPackageInfoMethod.invoke(null, new Object[0]);
        this.mAddAssetPathMethod.invoke(paramContext.getResources().getAssets(), new Object[] { localPackageInfo.applicationInfo.sourceDir });
        return;
      }
      catch (Exception paramContext)
      {
        throw new RuntimeException("Invalid reflection", paramContext);
      }
    }
    
    public void callDrawGlFunction(Canvas paramCanvas, long paramLong)
    {
      try
      {
        this.mCallDrawGLFunctionMethod.invoke(paramCanvas, new Object[] { Long.valueOf(paramLong) });
        return;
      }
      catch (Exception paramCanvas)
      {
        throw new RuntimeException("Invalid reflection", paramCanvas);
      }
    }
    
    public void callDrawGlFunction(Canvas paramCanvas, long paramLong, Runnable paramRunnable)
    {
      throw new RuntimeException("Call not supported");
    }
    
    public boolean canInvokeDrawGlFunctor(View paramView)
    {
      try
      {
        Method localMethod = this.mGetViewRootImplMethod;
        boolean bool = false;
        paramView = localMethod.invoke(paramView, new Object[0]);
        if (paramView != null) {
          bool = true;
        }
        return bool;
      }
      catch (Exception paramView)
      {
        throw new RuntimeException("Invalid reflection", paramView);
      }
    }
    
    public void detachDrawGlFunctor(View paramView, long paramLong)
    {
      try
      {
        paramView = this.mGetViewRootImplMethod.invoke(paramView, new Object[0]);
        if (paramView != null) {
          this.mDetachFunctorMethod.invoke(paramView, new Object[] { Long.valueOf(paramLong) });
        }
        return;
      }
      catch (Exception paramView)
      {
        throw new RuntimeException("Invalid reflection", paramView);
      }
    }
    
    public Application getApplication()
    {
      try
      {
        Application localApplication = (Application)this.mCurrentApplicationMethod.invoke(null, new Object[0]);
        return localApplication;
      }
      catch (Exception localException)
      {
        throw new RuntimeException("Invalid reflection", localException);
      }
    }
    
    public String getDataDirectorySuffix()
    {
      return null;
    }
    
    public String getErrorString(Context paramContext, int paramInt)
    {
      try
      {
        paramContext = (String)this.mGetStringMethod.invoke(null, new Object[] { Integer.valueOf(paramInt), paramContext });
        return paramContext;
      }
      catch (Exception paramContext)
      {
        throw new RuntimeException("Invalid reflection", paramContext);
      }
    }
    
    public int getPackageId(Resources paramResources, String paramString)
    {
      try
      {
        Method localMethod = this.mGetAssignedPackageIdentifiersMethod;
        paramResources = paramResources.getAssets();
        int i = 0;
        paramResources = (SparseArray)localMethod.invoke(paramResources, new Object[0]);
        while (i < paramResources.size())
        {
          if (paramString.equals((String)paramResources.valueAt(i)))
          {
            i = paramResources.keyAt(i);
            return i;
          }
          i += 1;
        }
        paramResources = new StringBuilder();
        paramResources.append("Package not found: ");
        paramResources.append(paramString);
        throw new RuntimeException(paramResources.toString());
      }
      catch (Exception paramResources)
      {
        throw new RuntimeException("Invalid reflection", paramResources);
      }
    }
    
    public void invokeDrawGlFunctor(View paramView, long paramLong, boolean paramBoolean)
    {
      try
      {
        paramView = this.mGetViewRootImplMethod.invoke(paramView, new Object[0]);
        if (paramView != null) {
          this.mInvokeFunctorMethod.invoke(paramView, new Object[] { Long.valueOf(paramLong), Boolean.valueOf(paramBoolean) });
        }
        return;
      }
      catch (Exception paramView)
      {
        throw new RuntimeException("Invalid reflection", paramView);
      }
    }
    
    public boolean isMultiProcessEnabled()
    {
      throw new UnsupportedOperationException();
    }
    
    public boolean isTraceTagEnabled()
    {
      try
      {
        boolean bool = ((Boolean)this.mIsTagEnabledMethod.invoke(null, new Object[] { Long.valueOf(16L) })).booleanValue();
        return bool;
      }
      catch (Exception localException)
      {
        throw new RuntimeException("Invalid reflection", localException);
      }
    }
    
    public void setOnTraceEnabledChangeListener(final WebViewDelegateFactory.WebViewDelegate.OnTraceEnabledChangeListener paramOnTraceEnabledChangeListener)
    {
      try
      {
        this.mAddChangeCallbackMethod.invoke(null, new Object[] { new Runnable()
        {
          public void run()
          {
            paramOnTraceEnabledChangeListener.onTraceEnabledChange(WebViewDelegateFactory.Api21CompatibilityDelegate.this.isTraceTagEnabled());
          }
        } });
        return;
      }
      catch (Exception paramOnTraceEnabledChangeListener)
      {
        throw new RuntimeException("Invalid reflection", paramOnTraceEnabledChangeListener);
      }
    }
  }
  
  static class ProxyDelegate
    implements WebViewDelegateFactory.WebViewDelegate
  {
    WebViewDelegate mDelegate;
    
    ProxyDelegate(WebViewDelegate paramWebViewDelegate)
    {
      this.mDelegate = paramWebViewDelegate;
    }
    
    public void addWebViewAssetPath(Context paramContext)
    {
      this.mDelegate.addWebViewAssetPath(new ContextWrapper(paramContext)
      {
        public AssetManager getAssets()
        {
          return getResources().getAssets();
        }
      });
    }
    
    public void callDrawGlFunction(Canvas paramCanvas, long paramLong)
    {
      this.mDelegate.callDrawGlFunction(paramCanvas, paramLong);
    }
    
    public void callDrawGlFunction(Canvas paramCanvas, long paramLong, Runnable paramRunnable)
    {
      this.mDelegate.callDrawGlFunction(paramCanvas, paramLong, paramRunnable);
    }
    
    public boolean canInvokeDrawGlFunctor(View paramView)
    {
      return this.mDelegate.canInvokeDrawGlFunctor(paramView);
    }
    
    public void detachDrawGlFunctor(View paramView, long paramLong)
    {
      this.mDelegate.detachDrawGlFunctor(paramView, paramLong);
    }
    
    public Application getApplication()
    {
      return this.mDelegate.getApplication();
    }
    
    public String getDataDirectorySuffix()
    {
      return null;
    }
    
    public String getErrorString(Context paramContext, int paramInt)
    {
      return this.mDelegate.getErrorString(paramContext, paramInt);
    }
    
    public int getPackageId(Resources paramResources, String paramString)
    {
      return this.mDelegate.getPackageId(paramResources, paramString);
    }
    
    public void invokeDrawGlFunctor(View paramView, long paramLong, boolean paramBoolean)
    {
      this.mDelegate.invokeDrawGlFunctor(paramView, paramLong, paramBoolean);
    }
    
    public boolean isMultiProcessEnabled()
    {
      return this.mDelegate.isMultiProcessEnabled();
    }
    
    public boolean isTraceTagEnabled()
    {
      return this.mDelegate.isTraceTagEnabled();
    }
    
    public void setOnTraceEnabledChangeListener(final WebViewDelegateFactory.WebViewDelegate.OnTraceEnabledChangeListener paramOnTraceEnabledChangeListener)
    {
      this.mDelegate.setOnTraceEnabledChangeListener(new WebViewDelegate.OnTraceEnabledChangeListener()
      {
        public void onTraceEnabledChange(boolean paramAnonymousBoolean)
        {
          paramOnTraceEnabledChangeListener.onTraceEnabledChange(paramAnonymousBoolean);
        }
      });
    }
  }
  
  public static abstract interface WebViewDelegate
  {
    public abstract void addWebViewAssetPath(Context paramContext);
    
    public abstract void callDrawGlFunction(Canvas paramCanvas, long paramLong);
    
    public abstract void callDrawGlFunction(Canvas paramCanvas, long paramLong, Runnable paramRunnable);
    
    public abstract boolean canInvokeDrawGlFunctor(View paramView);
    
    public abstract void detachDrawGlFunctor(View paramView, long paramLong);
    
    public abstract Application getApplication();
    
    public abstract String getDataDirectorySuffix();
    
    public abstract String getErrorString(Context paramContext, int paramInt);
    
    public abstract int getPackageId(Resources paramResources, String paramString);
    
    public abstract void invokeDrawGlFunctor(View paramView, long paramLong, boolean paramBoolean);
    
    public abstract boolean isMultiProcessEnabled();
    
    public abstract boolean isTraceTagEnabled();
    
    public abstract void setOnTraceEnabledChangeListener(OnTraceEnabledChangeListener paramOnTraceEnabledChangeListener);
    
    public static abstract interface OnTraceEnabledChangeListener
    {
      public abstract void onTraceEnabledChange(boolean paramBoolean);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebViewDelegateFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */