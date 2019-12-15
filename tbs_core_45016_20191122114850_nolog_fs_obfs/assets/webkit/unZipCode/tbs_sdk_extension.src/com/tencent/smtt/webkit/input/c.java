package com.tencent.smtt.webkit.input;

import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.WebViewChromiumExtension;
import com.tencent.smtt.webkit.e;
import org.chromium.content.browser.PositionObserver;
import org.chromium.content.browser.PositionObserver.Listener;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content.browser.ViewPositionObserver;
import org.chromium.content_public.browser.GestureStateListener;

public class c
  extends GestureStateListener
{
  private static int jdField_a_of_type_Int = 1;
  private static int jdField_b_of_type_Int = 2;
  private static int jdField_c_of_type_Int = 0;
  private static int jdField_d_of_type_Int = 0;
  private static int jdField_e_of_type_Int = 5;
  private static int jdField_f_of_type_Int = 20;
  private float jdField_a_of_type_Float = 0.0F;
  private Rect jdField_a_of_type_AndroidGraphicsRect;
  private ViewGroup jdField_a_of_type_AndroidViewViewGroup;
  private WebViewChromiumExtension jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension;
  private InsertionHandleController jdField_a_of_type_ComTencentSmttWebkitInputInsertionHandleController;
  private SelectionHandleController jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController;
  private final b.a.a jdField_a_of_type_ComTencentSmttWebkitInputB$a$a;
  private final b.a jdField_a_of_type_ComTencentSmttWebkitInputB$a;
  private a jdField_a_of_type_ComTencentSmttWebkitInputC$a = null;
  private Runnable jdField_a_of_type_JavaLangRunnable;
  private PositionObserver.Listener jdField_a_of_type_OrgChromiumContentBrowserPositionObserver$Listener;
  private PositionObserver jdField_a_of_type_OrgChromiumContentBrowserPositionObserver;
  private boolean jdField_a_of_type_Boolean;
  private float jdField_b_of_type_Float = 0.0F;
  private Rect jdField_b_of_type_AndroidGraphicsRect;
  private final b.a.a jdField_b_of_type_ComTencentSmttWebkitInputB$a$a;
  private Runnable jdField_b_of_type_JavaLangRunnable;
  private boolean jdField_b_of_type_Boolean = true;
  private float jdField_c_of_type_Float = 0.0F;
  private final b.a.a jdField_c_of_type_ComTencentSmttWebkitInputB$a$a;
  private Runnable jdField_c_of_type_JavaLangRunnable = null;
  private boolean jdField_c_of_type_Boolean = false;
  private float jdField_d_of_type_Float = 0.0F;
  private final b.a.a jdField_d_of_type_ComTencentSmttWebkitInputB$a$a;
  private boolean jdField_d_of_type_Boolean = false;
  private final b.a.a jdField_e_of_type_ComTencentSmttWebkitInputB$a$a;
  private final b.a.a jdField_f_of_type_ComTencentSmttWebkitInputB$a$a;
  private int g = 0;
  private int h = 0;
  private int i = 0;
  private int j = 0;
  
  public c(ViewGroup paramViewGroup, WebViewChromiumExtension paramWebViewChromiumExtension)
  {
    this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
    this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension = paramWebViewChromiumExtension;
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver = new ViewPositionObserver(this.jdField_a_of_type_AndroidViewViewGroup);
    this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver$Listener = new PositionObserver.Listener()
    {
      public void onPositionChanged(int paramAnonymousInt1, int paramAnonymousInt2) {}
    };
    this.jdField_a_of_type_ComTencentSmttWebkitInputB$a = new b.a();
    this.jdField_a_of_type_ComTencentSmttWebkitInputC$a = new a(null);
    this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a = this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.a();
    this.jdField_b_of_type_ComTencentSmttWebkitInputB$a$a = this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.a();
    this.jdField_c_of_type_ComTencentSmttWebkitInputB$a$a = this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.a();
    this.jdField_d_of_type_ComTencentSmttWebkitInputB$a$a = this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.a();
    this.jdField_e_of_type_ComTencentSmttWebkitInputB$a$a = this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.a();
    this.jdField_f_of_type_ComTencentSmttWebkitInputB$a$a = this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.a();
  }
  
  private int a()
  {
    return ((WindowManager)this.jdField_a_of_type_AndroidViewViewGroup.getContext().getSystemService("window")).getDefaultDisplay().getWidth();
  }
  
  private int a(int paramInt)
  {
    if (paramInt == jdField_a_of_type_Int)
    {
      if (jdField_c_of_type_Int == 0) {
        jdField_c_of_type_Int = a() / 5;
      }
      return jdField_c_of_type_Int;
    }
    if (paramInt == jdField_b_of_type_Int)
    {
      if (jdField_d_of_type_Int == 0) {
        jdField_d_of_type_Int = b() / 5;
      }
      return jdField_d_of_type_Int;
    }
    return 0;
  }
  
  private InsertionHandleController a()
  {
    if (this.jdField_a_of_type_ComTencentSmttWebkitInputInsertionHandleController == null) {
      this.jdField_a_of_type_ComTencentSmttWebkitInputInsertionHandleController = new InsertionHandleController(this.jdField_a_of_type_AndroidViewViewGroup, this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver, this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.getSettingsExtension())
      {
        public int getLineHeight()
        {
          return (int)Math.ceil(c.a(c.this).fromLocalCssToPix(14.0F));
        }
        
        public void paste() {}
        
        public void setCursorPosition(int paramAnonymousInt1, int paramAnonymousInt2)
        {
          c.a(c.this).moveCaret(paramAnonymousInt1, paramAnonymousInt2);
        }
      };
    }
    return this.jdField_a_of_type_ComTencentSmttWebkitInputInsertionHandleController;
  }
  
  private SelectionHandleController a()
  {
    if (this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController == null) {
      this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController = new SelectionHandleController(this.jdField_a_of_type_AndroidViewViewGroup, this.jdField_a_of_type_OrgChromiumContentBrowserPositionObserver, this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.getSettingsExtension())
      {
        public void afterEndUpdatingPosition(b paramAnonymousb)
        {
          super.afterEndUpdatingPosition(paramAnonymousb);
          c.b(this.jdField_a_of_type_ComTencentSmttWebkitInputC);
          c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC, 0.0F);
          c.b(this.jdField_a_of_type_ComTencentSmttWebkitInputC, 0.0F);
        }
        
        public void beforeStartUpdatingPosition(b paramAnonymousb)
        {
          super.beforeStartUpdatingPosition(paramAnonymousb);
          c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC);
          c localc = this.jdField_a_of_type_ComTencentSmttWebkitInputC;
          c.a(localc, c.a(localc).getScrollX());
          localc = this.jdField_a_of_type_ComTencentSmttWebkitInputC;
          c.b(localc, c.a(localc).getScrollY());
          if (paramAnonymousb == this.jdField_a_of_type_ComTencentSmttWebkitInputB) {
            c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC).markScrollOffsetForMoveRange(true);
          } else {
            c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC).markScrollOffsetForMoveRange(false);
          }
          c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC, true);
        }
        
        public void selectBetweenCoordinates(int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3, int paramAnonymousInt4, int paramAnonymousInt5, int paramAnonymousInt6)
        {
          if ((c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC)) && (!c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC).isFocusedNodeEditable()))
          {
            c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousInt4, paramAnonymousInt5, paramAnonymousInt6);
            return;
          }
          c();
          c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC).selectBetweenCoordinates(paramAnonymousInt1, paramAnonymousInt2 - c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC).getContentOffsetYPix(), paramAnonymousInt3, paramAnonymousInt4 - c.a(this.jdField_a_of_type_ComTencentSmttWebkitInputC).getContentOffsetYPix());
        }
      };
    }
    return this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController;
  }
  
  private void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    int n = a(jdField_a_of_type_Int);
    int k = a(jdField_b_of_type_Int);
    int i1 = a();
    int m = b();
    if ((paramInt5 < n) && (this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.getScrollX() != 0.0F))
    {
      i1 = jdField_e_of_type_Int;
      paramInt5 = 0 - (i1 + (jdField_f_of_type_Int - i1) * (n - paramInt5) / n);
    }
    else
    {
      paramInt5 = i1 - paramInt5;
      if ((paramInt5 < n) && (f()))
      {
        i1 = jdField_e_of_type_Int;
        paramInt5 = i1 + (jdField_f_of_type_Int - i1) * (n - paramInt5) / n + 0;
      }
      else
      {
        paramInt5 = 0;
      }
    }
    if ((paramInt6 < k) && (this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.getScrollY() != 0.0F))
    {
      m = jdField_e_of_type_Int;
      paramInt6 = 0 - (m + (jdField_f_of_type_Int - m) * (k - paramInt6) / k);
    }
    else
    {
      paramInt6 = m - paramInt6;
      if ((paramInt6 < k) && (g()))
      {
        m = jdField_e_of_type_Int;
        paramInt6 = m + (jdField_f_of_type_Int - m) * (k - paramInt6) / k + 0;
      }
      else
      {
        paramInt6 = 0;
      }
    }
    if ((paramInt5 | paramInt6) != 0)
    {
      this.jdField_c_of_type_Float = (paramInt5 * this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.getDeviceScaleFactor());
      this.jdField_d_of_type_Float = (paramInt6 * this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.getDeviceScaleFactor());
      this.i = paramInt1;
      this.j = paramInt2;
      if (paramInt3 < 0)
      {
        paramInt1 = 0;
      }
      else
      {
        paramInt1 = paramInt3;
        if (paramInt3 > this.jdField_a_of_type_AndroidViewViewGroup.getWidth()) {
          paramInt1 = this.jdField_a_of_type_AndroidViewViewGroup.getWidth();
        }
      }
      if (paramInt4 < 0)
      {
        paramInt2 = 0;
      }
      else
      {
        paramInt2 = paramInt4;
        if (paramInt4 > this.jdField_a_of_type_AndroidViewViewGroup.getHeight()) {
          paramInt2 = this.jdField_a_of_type_AndroidViewViewGroup.getHeight();
        }
      }
      this.g = paramInt1;
      this.h = paramInt2;
      j();
      return;
    }
    k();
    this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController.c();
    this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.selectBetweenCoordinates(paramInt1, paramInt2 - this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.getContentOffsetYPix(), paramInt3, paramInt4 - this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.getContentOffsetYPix());
  }
  
  private int b()
  {
    return ((WindowManager)this.jdField_a_of_type_AndroidViewViewGroup.getContext().getSystemService("window")).getDefaultDisplay().getHeight();
  }
  
  private void c()
  {
    float f1 = this.jdField_a_of_type_ComTencentSmttWebkitInputC$a.jdField_a_of_type_AndroidGraphicsRect.left;
    float f2 = this.jdField_a_of_type_ComTencentSmttWebkitInputC$a.jdField_a_of_type_AndroidGraphicsRect.bottom;
    float f3 = this.jdField_a_of_type_ComTencentSmttWebkitInputC$a.jdField_a_of_type_AndroidGraphicsRect.right;
    float f4 = this.jdField_a_of_type_ComTencentSmttWebkitInputC$a.jdField_a_of_type_AndroidGraphicsRect.top;
    float f5 = this.jdField_a_of_type_ComTencentSmttWebkitInputC$a.jdField_b_of_type_AndroidGraphicsRect.right;
    float f6 = this.jdField_a_of_type_ComTencentSmttWebkitInputC$a.jdField_b_of_type_AndroidGraphicsRect.bottom;
    float f7 = this.jdField_a_of_type_ComTencentSmttWebkitInputC$a.jdField_b_of_type_AndroidGraphicsRect.right;
    float f8 = this.jdField_a_of_type_ComTencentSmttWebkitInputC$a.jdField_b_of_type_AndroidGraphicsRect.top;
    this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a.setLocalDip(f1, f2);
    this.jdField_b_of_type_ComTencentSmttWebkitInputB$a$a.setLocalDip(f3, f4);
    this.jdField_c_of_type_ComTencentSmttWebkitInputB$a$a.setLocalDip(f5, f6);
    this.jdField_d_of_type_ComTencentSmttWebkitInputB$a$a.setLocalDip(f7, f8);
  }
  
  private boolean c()
  {
    SelectionHandleController localSelectionHandleController = this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController;
    return (localSelectionHandleController != null) && (localSelectionHandleController.isShowing());
  }
  
  private void d()
  {
    if (c())
    {
      c();
      this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController.a(this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a, this.jdField_b_of_type_ComTencentSmttWebkitInputB$a$a);
      this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController.b(this.jdField_c_of_type_ComTencentSmttWebkitInputB$a$a, this.jdField_d_of_type_ComTencentSmttWebkitInputB$a$a);
    }
    if (d()) {
      this.jdField_a_of_type_ComTencentSmttWebkitInputInsertionHandleController.a(this.jdField_e_of_type_ComTencentSmttWebkitInputB$a$a, this.jdField_f_of_type_ComTencentSmttWebkitInputB$a$a);
    }
  }
  
  private boolean d()
  {
    InsertionHandleController localInsertionHandleController = this.jdField_a_of_type_ComTencentSmttWebkitInputInsertionHandleController;
    return (localInsertionHandleController != null) && (localInsertionHandleController.isShowing());
  }
  
  private void e()
  {
    if (this.jdField_a_of_type_JavaLangRunnable == null) {
      this.jdField_a_of_type_JavaLangRunnable = new Runnable()
      {
        public void run()
        {
          if (c.b(c.this))
          {
            if (c.c(c.this)) {
              return;
            }
            if (c.a(c.this).isScrollInProgress())
            {
              c.c(c.this);
              return;
            }
            c.d(c.this);
            return;
          }
        }
      };
    }
    f();
    this.jdField_a_of_type_AndroidViewViewGroup.removeCallbacks(this.jdField_a_of_type_JavaLangRunnable);
    if (e.a().t())
    {
      this.jdField_a_of_type_AndroidViewViewGroup.postDelayed(this.jdField_a_of_type_JavaLangRunnable, 0L);
      return;
    }
    this.jdField_a_of_type_AndroidViewViewGroup.postDelayed(this.jdField_a_of_type_JavaLangRunnable, 300L);
  }
  
  private boolean e()
  {
    return (a()) && (c());
  }
  
  private void f()
  {
    this.jdField_a_of_type_AndroidGraphicsRect = new Rect((int)this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a.getXPix(), (int)this.jdField_b_of_type_ComTencentSmttWebkitInputB$a$a.getYPix(), (int)this.jdField_b_of_type_ComTencentSmttWebkitInputB$a$a.getXPix(), (int)this.jdField_a_of_type_ComTencentSmttWebkitInputB$a$a.getYPix());
    this.jdField_b_of_type_AndroidGraphicsRect = new Rect((int)this.jdField_c_of_type_ComTencentSmttWebkitInputB$a$a.getXPix(), (int)this.jdField_d_of_type_ComTencentSmttWebkitInputB$a$a.getYPix(), (int)this.jdField_d_of_type_ComTencentSmttWebkitInputB$a$a.getXPix(), (int)this.jdField_c_of_type_ComTencentSmttWebkitInputB$a$a.getYPix());
  }
  
  private boolean f()
  {
    return a() - this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.maxHorizontalScroll() < 0;
  }
  
  private void g()
  {
    this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.onEndUpdatingPosition(this.jdField_a_of_type_AndroidGraphicsRect, this.jdField_b_of_type_AndroidGraphicsRect);
  }
  
  private boolean g()
  {
    return b() - this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.maxVerticalScroll() < 0;
  }
  
  private void h()
  {
    f();
    g();
  }
  
  private void i()
  {
    this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.onStartUpdatingPosition();
  }
  
  private void j()
  {
    if (this.jdField_c_of_type_JavaLangRunnable == null)
    {
      Log.e("stack", Log.getStackTraceString(new Throwable()));
      this.jdField_c_of_type_JavaLangRunnable = new Runnable()
      {
        public void run()
        {
          if (!c.this.b())
          {
            c.e(c.this);
            return;
          }
          c.a(c.this).scrollBy((int)c.a(c.this), (int)c.b(c.this));
          c.a(c.this).c();
          c.a(c.this).selectBetweenCoordinates(c.a(c.this), c.b(c.this) - c.a(c.this).getContentOffsetYPix(), c.c(c.this), c.d(c.this) - c.a(c.this).getContentOffsetYPix());
          c.a(c.this).postDelayed(c.a(c.this), 20L);
        }
      };
      this.jdField_a_of_type_AndroidViewViewGroup.postDelayed(this.jdField_c_of_type_JavaLangRunnable, 20L);
    }
  }
  
  private void k()
  {
    this.jdField_a_of_type_AndroidViewViewGroup.removeCallbacks(this.jdField_c_of_type_JavaLangRunnable);
    this.jdField_c_of_type_JavaLangRunnable = null;
    this.i = 0;
    this.j = 0;
    this.g = 0;
    this.h = 0;
  }
  
  private void l()
  {
    Runnable local6 = new Runnable()
    {
      public void run()
      {
        c.a(c.this, false);
        c.f(c.this);
      }
    };
    this.jdField_a_of_type_AndroidViewViewGroup.postDelayed(local6, 300L);
  }
  
  private void m()
  {
    if ((c()) && (!this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController.a()))
    {
      this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController.a(4);
      this.jdField_a_of_type_ComTencentSmttWebkitWebViewChromiumExtension.hideSelectionView();
      this.jdField_c_of_type_Boolean = true;
    }
  }
  
  private void n()
  {
    if ((!d()) && (!c()))
    {
      this.jdField_c_of_type_Boolean = false;
      return;
    }
    if (this.jdField_b_of_type_JavaLangRunnable == null) {
      this.jdField_b_of_type_JavaLangRunnable = new Runnable()
      {
        public void run()
        {
          if (c.a(c.this).isScrollInProgress())
          {
            c.g(c.this);
            return;
          }
          if (c.d(c.this))
          {
            c.b(c.this).a();
            c.b(c.this).a(0, 0);
            c.h(c.this);
            c.b(c.this).a(c.a(c.this).a, c.a(c.this).b);
            c.h(c.this);
            c.b(c.this).a(c.a(c.this).c);
            c.i(c.this);
            c.d(c.this);
          }
          c.b(c.this, false);
        }
      };
    }
    this.jdField_a_of_type_AndroidViewViewGroup.removeCallbacks(this.jdField_b_of_type_JavaLangRunnable);
    this.jdField_a_of_type_AndroidViewViewGroup.postDelayed(this.jdField_b_of_type_JavaLangRunnable, 300L);
  }
  
  public void a()
  {
    a().b();
    this.jdField_a_of_type_Boolean = false;
  }
  
  public void a(Rect paramRect1, Rect paramRect2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitInputC$a.a(paramRect1, paramRect2, paramBoolean1, paramBoolean2, paramBoolean3);
    c();
    if (!this.jdField_c_of_type_Boolean)
    {
      a().a();
      a().a(0, 0);
      a().a(paramBoolean1, paramBoolean2);
      d();
      a().a(paramBoolean3);
    }
    this.jdField_a_of_type_Boolean = true;
    if ((!this.jdField_d_of_type_Boolean) && (!this.jdField_c_of_type_Boolean)) {
      e();
    }
  }
  
  public void a(Rect paramRect, boolean paramBoolean)
  {
    float f1 = paramRect.left;
    float f2 = paramRect.bottom;
    float f3 = paramRect.right;
    float f4 = paramRect.top;
    this.jdField_e_of_type_ComTencentSmttWebkitInputB$a$a.setLocalDip(f1, f2);
    this.jdField_f_of_type_ComTencentSmttWebkitInputB$a$a.setLocalDip(f3, f4);
    a().a();
    a().d();
    a().a(paramBoolean);
    d();
  }
  
  public void a(RenderCoordinates paramRenderCoordinates)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitInputB$a.a(paramRenderCoordinates);
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_b_of_type_Boolean = paramBoolean;
  }
  
  public boolean a()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public boolean a(View paramView)
  {
    if (c()) {
      return this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController.a(paramView);
    }
    if (d()) {
      return this.jdField_a_of_type_ComTencentSmttWebkitInputInsertionHandleController.a(paramView);
    }
    return false;
  }
  
  public void b()
  {
    a().b();
  }
  
  public boolean b()
  {
    return a().a();
  }
  
  public boolean b(View paramView)
  {
    if (c()) {
      return this.jdField_a_of_type_ComTencentSmttWebkitInputSelectionHandleController.b(paramView);
    }
    return false;
  }
  
  public void onFlingEndGesture(int paramInt1, int paramInt2)
  {
    n();
  }
  
  public void onScrollEnded(int paramInt1, int paramInt2)
  {
    n();
  }
  
  public void onScrollStarted(int paramInt1, int paramInt2)
  {
    m();
  }
  
  private class a
  {
    public Rect a;
    boolean jdField_a_of_type_Boolean = false;
    public Rect b;
    boolean b;
    boolean c = false;
    
    private a()
    {
      this.jdField_b_of_type_Boolean = false;
    }
    
    void a(Rect paramRect1, Rect paramRect2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    {
      this.jdField_a_of_type_AndroidGraphicsRect = paramRect1;
      this.jdField_b_of_type_AndroidGraphicsRect = paramRect2;
      this.jdField_a_of_type_Boolean = paramBoolean1;
      this.jdField_b_of_type_Boolean = paramBoolean2;
      this.c = paramBoolean3;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\input\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */