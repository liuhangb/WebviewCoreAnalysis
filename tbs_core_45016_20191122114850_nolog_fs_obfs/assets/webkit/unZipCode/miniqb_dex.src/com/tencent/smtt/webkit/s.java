package com.tencent.smtt.webkit;

import android.content.res.Resources;
import android.os.Build.VERSION;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.smtt.os.SMTTAdaptation;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;

public class s
{
  private int jdField_a_of_type_Int = 0;
  private final TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private final ArrayList<a> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
  private boolean jdField_a_of_type_Boolean;
  private int jdField_b_of_type_Int = 0;
  private boolean jdField_b_of_type_Boolean = true;
  private final int jdField_c_of_type_Int;
  private boolean jdField_c_of_type_Boolean = false;
  
  public s(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    paramTencentWebViewProxy = paramTencentWebViewProxy.getRealWebView().getResources().getDisplayMetrics();
    double d = paramTencentWebViewProxy.widthPixels * paramTencentWebViewProxy.heightPixels;
    Double.isNaN(d);
    this.jdField_c_of_type_Int = ((int)(d * 2.75D));
  }
  
  private void a(a parama)
  {
    if (!this.jdField_a_of_type_JavaUtilArrayList.contains(parama)) {
      return;
    }
    int j = parama.e;
    int i = 0;
    int i1;
    int k;
    int n;
    int i2;
    Object localObject1;
    int m;
    if (j == 2)
    {
      i1 = parama.jdField_a_of_type_Int;
      k = parama.jdField_b_of_type_Int;
      j = parama.jdField_c_of_type_Int;
      n = parama.d;
      if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getScrollY() < 0) {
        i = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getScrollY();
      } else {
        i = 0;
      }
      i2 = k - i + this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getVisibleTitleHeight();
      localObject1 = parama.jdField_a_of_type_AndroidViewView.getLayoutParams();
      if ((localObject1 instanceof FrameLayout.LayoutParams))
      {
        localObject1 = (FrameLayout.LayoutParams)localObject1;
        Object localObject2 = parama.jdField_a_of_type_AndroidViewView.getTag();
        if (((localObject2 instanceof Boolean)) && ((!((Boolean)localObject2).booleanValue()) || (((FrameLayout.LayoutParams)localObject1).leftMargin != 0) || (((FrameLayout.LayoutParams)localObject1).topMargin > i2))) {
          i = 0;
        } else {
          i = 1;
        }
        k = j;
        m = n;
        if (i != 0)
        {
          parama.jdField_a_of_type_AndroidViewView.setTag(null);
          ((FrameLayout.LayoutParams)localObject1).width = j;
          ((FrameLayout.LayoutParams)localObject1).height = n;
          ((FrameLayout.LayoutParams)localObject1).gravity = 51;
          ((FrameLayout.LayoutParams)localObject1).setMargins(i1, i2, 0, 0);
          parama.jdField_a_of_type_AndroidViewView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
          k = j;
          m = n;
        }
      }
      else
      {
        localObject1 = new FrameLayout.LayoutParams(j, n, 51);
        ((FrameLayout.LayoutParams)localObject1).setMargins(i1, i2, 0, 0);
        parama.jdField_a_of_type_AndroidViewView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        k = j;
        m = n;
      }
    }
    else
    {
      j = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(parama.jdField_c_of_type_Int);
      n = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewDimension(parama.d);
      k = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewX(parama.jdField_a_of_type_Int);
      m = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.contentToViewY(parama.jdField_b_of_type_Int);
      if (parama.e == 0)
      {
        localObject1 = parama.jdField_a_of_type_AndroidViewView.getLayoutParams();
        if ((localObject1 instanceof AbsoluteLayout.LayoutParams))
        {
          localObject1 = (AbsoluteLayout.LayoutParams)localObject1;
          ((AbsoluteLayout.LayoutParams)localObject1).width = j;
          ((AbsoluteLayout.LayoutParams)localObject1).height = n;
          ((AbsoluteLayout.LayoutParams)localObject1).x = k;
          ((AbsoluteLayout.LayoutParams)localObject1).y = m;
        }
        else
        {
          localObject1 = new AbsoluteLayout.LayoutParams(j, n, k, m);
        }
        parama.jdField_a_of_type_AndroidViewView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        k = j;
        m = n;
        if (Build.VERSION.SDK_INT < 11)
        {
          parama.jdField_a_of_type_AndroidViewView.setTag(Integer.valueOf(((AbsoluteLayout.LayoutParams)localObject1).y));
          k = j;
          m = n;
        }
      }
      else
      {
        if (this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getScrollY() < 0) {
          i = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getScrollY();
        }
        i = m - i + this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().getVisibleTitleHeight();
        localObject1 = new FrameLayout.LayoutParams(j, n, 51);
        ((FrameLayout.LayoutParams)localObject1).setMargins(k, i, k + j, i + n);
        parama.jdField_a_of_type_AndroidViewView.setLayoutParams((ViewGroup.LayoutParams)localObject1);
        m = n;
        k = j;
      }
    }
    if ((parama.jdField_a_of_type_AndroidViewView instanceof SurfaceView))
    {
      localObject1 = (SurfaceView)parama.jdField_a_of_type_AndroidViewView;
      if ((a((SurfaceView)localObject1)) && (this.jdField_c_of_type_Boolean)) {
        return;
      }
      i = 2048;
      if ((k <= 2048) && (m <= 2048))
      {
        i = k;
        j = m;
      }
      else if (parama.jdField_c_of_type_Int > parama.d)
      {
        j = parama.d * 2048 / parama.jdField_c_of_type_Int;
      }
      else
      {
        i = parama.jdField_c_of_type_Int * 2048 / parama.d;
        j = 2048;
      }
      i2 = this.jdField_c_of_type_Int;
      n = i;
      i1 = j;
      if (i * j > i2)
      {
        float f = i2;
        if (parama.jdField_c_of_type_Int > parama.d)
        {
          n = (int)Math.sqrt(f * parama.jdField_c_of_type_Int / parama.d);
          i1 = parama.d * n / parama.jdField_c_of_type_Int;
        }
        else
        {
          i1 = (int)Math.sqrt(f * parama.d / parama.jdField_c_of_type_Int);
          n = parama.jdField_c_of_type_Int * i1 / parama.d;
        }
      }
      if ((n == k) && (i1 == m))
      {
        if ((!a((SurfaceView)localObject1)) && (this.jdField_c_of_type_Boolean))
        {
          ((SurfaceView)localObject1).getHolder().setFixedSize(((SurfaceView)localObject1).getWidth(), ((SurfaceView)localObject1).getHeight());
          return;
        }
        if ((a((SurfaceView)localObject1)) && (!this.jdField_c_of_type_Boolean))
        {
          if (((SurfaceView)localObject1).getVisibility() == 0)
          {
            ((SurfaceView)localObject1).getHolder().setSizeFromLayout();
            this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().post(new Runnable()
            {
              public void run() {}
            });
            return;
          }
          ((SurfaceView)localObject1).getHolder().setSizeFromLayout();
        }
      }
      else
      {
        ((SurfaceView)localObject1).getHolder().setFixedSize(n, i1);
      }
    }
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public a a()
  {
    return new a();
  }
  
  public void a()
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext())
    {
      a locala = (a)localIterator.next();
      if ((!locala.a()) && (locala.e != 2)) {
        a(locala);
      }
    }
  }
  
  public void a(int paramInt)
  {
    if (this.jdField_b_of_type_Int != paramInt)
    {
      this.jdField_b_of_type_Int = paramInt;
      d();
    }
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
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext()) {
      ((a)localIterator.next()).jdField_a_of_type_AndroidViewView.setVisibility(8);
    }
    this.jdField_a_of_type_Boolean = true;
  }
  
  public void b(int paramInt)
  {
    if (this.jdField_a_of_type_Int != paramInt)
    {
      this.jdField_a_of_type_Int = paramInt;
      d();
    }
  }
  
  public void c()
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext()) {
      ((a)localIterator.next()).jdField_a_of_type_AndroidViewView.setVisibility(0);
    }
    this.jdField_a_of_type_Boolean = false;
  }
  
  public void d()
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext())
    {
      a locala = (a)localIterator.next();
      if ((locala.e == 1) || (locala.e == 2)) {
        a(locala);
      }
    }
  }
  
  public class a
  {
    int jdField_a_of_type_Int;
    public View a;
    boolean jdField_a_of_type_Boolean = false;
    int b;
    int c;
    int d;
    public int e = 0;
    
    a() {}
    
    private void c()
    {
      if (Looper.myLooper() != Looper.getMainLooper())
      {
        s.a(s.this).getRealWebView().post(new Runnable()
        {
          public void run()
          {
            s.a.a(s.a.this);
          }
        });
        return;
      }
      try
      {
        if (((this.e == 1) || (this.e == 2)) && ((s.a(s.this).getRealWebView().getParent() instanceof ViewGroup)))
        {
          SMTTAdaptation.setTranslationY(this.jdField_a_of_type_AndroidViewView, 0.0F);
          ViewGroup localViewGroup = (ViewGroup)s.a(s.this).getRealWebView().getParent();
          FrameLayout.LayoutParams localLayoutParams;
          if (this.e == 1) {
            localLayoutParams = new FrameLayout.LayoutParams(s.a(s.this).contentToViewDimension(this.c), s.a(s.this).contentToViewDimension(this.d), 51);
          } else {
            localLayoutParams = new FrameLayout.LayoutParams(this.c, this.d, 51);
          }
          this.jdField_a_of_type_AndroidViewView.setLayoutParams(localLayoutParams);
          localViewGroup.addView(this.jdField_a_of_type_AndroidViewView, localLayoutParams);
        }
        else
        {
          s.a(s.this).getRealWebView().addView(this.jdField_a_of_type_AndroidViewView);
        }
        s.a(s.this).add(this);
        if (!s.b(s.this)) {
          this.jdField_a_of_type_AndroidViewView.setVisibility(8);
        }
        s.a(s.this, this);
        if ((e.a().p()) && (s.a(s.this).getRealWebView().getParent() != null) && (s.a(s.this).getRealWebView().getParent().getParent() != null) && (s.a(s.this).getRealWebView().getParent().isLayoutRequested()))
        {
          s.a(s.this).getRealWebView().getParent().getParent().requestLayout();
          return;
        }
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    
    private void d()
    {
      if (Looper.myLooper() != Looper.getMainLooper())
      {
        s.a(s.this).getRealWebView().post(new Runnable()
        {
          public void run()
          {
            s.a.b(s.a.this);
          }
        });
        return;
      }
      for (;;)
      {
        try
        {
          if ((this.jdField_a_of_type_AndroidViewView != null) && (s.a(s.this) != null))
          {
            ViewGroup localViewGroup = (ViewGroup)this.jdField_a_of_type_AndroidViewView.getParent();
            if (localViewGroup == s.a(s.this).getRealWebView()) {
              break label151;
            }
            if (localViewGroup == s.a(s.this).getRealWebView().getParent())
            {
              break label151;
              if (i < localViewGroup.getChildCount())
              {
                if (localViewGroup.getChildAt(i) != this.jdField_a_of_type_AndroidViewView) {
                  break label156;
                }
                localViewGroup.removeViewAt(i);
              }
            }
          }
          if (s.a(s.this) != null)
          {
            s.a(s.this).remove(this);
            return;
          }
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
        return;
        label151:
        int i = 0;
        continue;
        label156:
        i += 1;
      }
    }
    
    public View a()
    {
      return this.jdField_a_of_type_AndroidViewView;
    }
    
    public void a()
    {
      if (this.jdField_a_of_type_AndroidViewView == null) {
        return;
      }
      s.a(s.this).getRealWebView().post(new Runnable()
      {
        public void run()
        {
          s.a.b(s.a.this);
        }
      });
    }
    
    public void a(int paramInt)
    {
      if (this.jdField_a_of_type_AndroidViewView == null) {
        return;
      }
      if (!s.a(s.this)) {
        this.jdField_a_of_type_AndroidViewView.setVisibility(paramInt);
      }
    }
    
    public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      this.jdField_a_of_type_Int = paramInt1;
      this.b = paramInt2;
      this.c = paramInt3;
      this.d = paramInt4;
    }
    
    public void a(boolean paramBoolean)
    {
      this.jdField_a_of_type_Boolean = paramBoolean;
    }
    
    public boolean a()
    {
      return this.jdField_a_of_type_Boolean;
    }
    
    public void b()
    {
      d();
    }
    
    public void b(int paramInt)
    {
      int i;
      if (this.e != paramInt) {
        i = 1;
      } else {
        i = 0;
      }
      if (i != 0)
      {
        this.e = paramInt;
        b();
        c();
        s.a(s.this, this);
      }
    }
    
    public void b(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (this.jdField_a_of_type_AndroidViewView == null) {
        return;
      }
      a(paramInt1, paramInt2, paramInt3, paramInt4);
      s.a(s.this, this);
      if (this.jdField_a_of_type_AndroidViewView.getParent() == null) {
        c();
      }
    }
    
    public void c(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (this.jdField_a_of_type_AndroidViewView == null) {
        return;
      }
      a(paramInt1, paramInt2, paramInt3, paramInt4);
      if (Looper.getMainLooper() != Looper.myLooper())
      {
        s.a(s.this).getRealWebView().post(new Runnable()
        {
          public void run()
          {
            s.a(s.this, s.a.this);
          }
        });
        return;
      }
      s.a(s.this, this);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\s.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */