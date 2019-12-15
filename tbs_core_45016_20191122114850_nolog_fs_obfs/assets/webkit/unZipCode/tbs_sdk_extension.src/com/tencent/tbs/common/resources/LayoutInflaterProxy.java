package com.tencent.tbs.common.resources;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.View;

final class LayoutInflaterProxy
  extends LayoutInflater
{
  private InflaterImpl mInflaterImpl;
  
  protected LayoutInflaterProxy(Context paramContext)
  {
    this(paramContext, null);
  }
  
  protected LayoutInflaterProxy(Context paramContext, InflaterImpl paramInflaterImpl)
  {
    super(paramContext);
    setInflaterImpl(paramInflaterImpl);
    init();
  }
  
  private View createViewSafely(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
  {
    InflaterImpl localInflaterImpl = this.mInflaterImpl;
    if (localInflaterImpl == null) {
      return null;
    }
    try
    {
      paramView = localInflaterImpl.createViewImpl(paramView, paramString, paramContext, paramAttributeSet);
      return paramView;
    }
    catch (Throwable paramView) {}
    return null;
  }
  
  @SuppressLint({"InlinedApi", "NewApi"})
  private void init()
  {
    if (supportFactory2()) {}
    try
    {
      setFactory2(new LayoutInflater.Factory2()
      {
        public View onCreateView(View paramAnonymousView, String paramAnonymousString, Context paramAnonymousContext, AttributeSet paramAnonymousAttributeSet)
        {
          return LayoutInflaterProxy.this.createViewSafely(paramAnonymousView, paramAnonymousString, paramAnonymousContext, paramAnonymousAttributeSet);
        }
        
        public View onCreateView(String paramAnonymousString, Context paramAnonymousContext, AttributeSet paramAnonymousAttributeSet)
        {
          return null;
        }
      });
      i = 1;
    }
    catch (Throwable localThrowable)
    {
      int i;
      for (;;) {}
    }
    i = 0;
    if (i == 0) {
      setFactory(new LayoutInflater.Factory()
      {
        public View onCreateView(String paramAnonymousString, Context paramAnonymousContext, AttributeSet paramAnonymousAttributeSet)
        {
          return LayoutInflaterProxy.this.createViewSafely(null, paramAnonymousString, paramAnonymousContext, paramAnonymousAttributeSet);
        }
      });
    }
  }
  
  private static boolean supportFactory2()
  {
    return Build.VERSION.SDK_INT >= 11;
  }
  
  public LayoutInflater cloneInContext(Context paramContext)
  {
    return null;
  }
  
  public void setInflaterImpl(InflaterImpl paramInflaterImpl)
  {
    this.mInflaterImpl = paramInflaterImpl;
  }
  
  public static abstract interface InflaterImpl
  {
    public abstract View createViewImpl(View paramView, String paramString, Context paramContext, AttributeSet paramAttributeSet)
      throws ClassNotFoundException;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\resources\LayoutInflaterProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */