package com.tencent.tbs.core.webkit;

import android.app.ActivityThread;
import android.app.Application;
import android.app.ResourcesManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.SystemProperties;
import android.os.Trace;
import android.util.SparseArray;
import android.view.DisplayListCanvas;
import android.view.View;
import android.view.ViewRootImpl;
import com.android.internal.util.ArrayUtils;

public final class WebViewDelegate
{
  public void addWebViewAssetPath(Context paramContext)
  {
    String str = WebViewFactory.getLoadedPackageInfo().applicationInfo.sourceDir;
    paramContext = paramContext.getApplicationInfo();
    String[] arrayOfString1 = paramContext.sharedLibraryFiles;
    if (!ArrayUtils.contains(arrayOfString1, str))
    {
      int i;
      if (arrayOfString1 != null) {
        i = arrayOfString1.length;
      } else {
        i = 0;
      }
      i += 1;
      String[] arrayOfString2 = new String[i];
      if (arrayOfString1 != null) {
        System.arraycopy(arrayOfString1, 0, arrayOfString2, 0, arrayOfString1.length);
      }
      arrayOfString2[(i - 1)] = str;
      paramContext.sharedLibraryFiles = arrayOfString2;
      ResourcesManager.getInstance().appendLibAssetForMainAssetPath(paramContext.getBaseResourcePath(), str);
    }
  }
  
  public void callDrawGlFunction(Canvas paramCanvas, long paramLong)
  {
    if ((paramCanvas instanceof DisplayListCanvas))
    {
      ((DisplayListCanvas)paramCanvas).drawGLFunctor2(paramLong, null);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramCanvas.getClass().getName());
    localStringBuilder.append(" is not a DisplayList canvas");
    throw new IllegalArgumentException(localStringBuilder.toString());
  }
  
  public void callDrawGlFunction(Canvas paramCanvas, long paramLong, Runnable paramRunnable)
  {
    if ((paramCanvas instanceof DisplayListCanvas))
    {
      ((DisplayListCanvas)paramCanvas).drawGLFunctor2(paramLong, paramRunnable);
      return;
    }
    paramRunnable = new StringBuilder();
    paramRunnable.append(paramCanvas.getClass().getName());
    paramRunnable.append(" is not a DisplayList canvas");
    throw new IllegalArgumentException(paramRunnable.toString());
  }
  
  public boolean canInvokeDrawGlFunctor(View paramView)
  {
    return true;
  }
  
  public void detachDrawGlFunctor(View paramView, long paramLong)
  {
    paramView = paramView.getViewRootImpl();
    if ((paramLong != 0L) && (paramView != null)) {
      paramView.detachFunctor(paramLong);
    }
  }
  
  public Application getApplication()
  {
    return ActivityThread.currentApplication();
  }
  
  public String getErrorString(Context paramContext, int paramInt)
  {
    return LegacyErrorStrings.getString(paramInt, paramContext);
  }
  
  public int getPackageId(Resources paramResources, String paramString)
  {
    paramResources = paramResources.getAssets().getAssignedPackageIdentifiers();
    int i = 0;
    while (i < paramResources.size())
    {
      if (paramString.equals((String)paramResources.valueAt(i))) {
        return paramResources.keyAt(i);
      }
      i += 1;
    }
    paramResources = new StringBuilder();
    paramResources.append("Package not found: ");
    paramResources.append(paramString);
    throw new RuntimeException(paramResources.toString());
  }
  
  public void invokeDrawGlFunctor(View paramView, long paramLong, boolean paramBoolean)
  {
    ViewRootImpl.invokeFunctor(paramLong, paramBoolean);
  }
  
  public boolean isTraceTagEnabled()
  {
    return Trace.isTagEnabled(16L);
  }
  
  public void setOnTraceEnabledChangeListener(final OnTraceEnabledChangeListener paramOnTraceEnabledChangeListener)
  {
    SystemProperties.addChangeCallback(new Runnable()
    {
      public void run()
      {
        paramOnTraceEnabledChangeListener.onTraceEnabledChange(WebViewDelegate.this.isTraceTagEnabled());
      }
    });
  }
  
  public static abstract interface OnTraceEnabledChangeListener
  {
    public abstract void onTraceEnabledChange(boolean paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebViewDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */