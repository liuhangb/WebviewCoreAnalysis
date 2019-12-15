package org.chromium.content.browser.input;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import org.chromium.base.VisibleForTesting;
import org.chromium.content_public.browser.InputMethodManagerWrapper;

public class ThreadedInputConnectionFactory
  implements ChromiumBaseInputConnection.Factory
{
  private final InputMethodUma jdField_a_of_type_OrgChromiumContentBrowserInputInputMethodUma;
  private ThreadedInputConnection jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnection;
  private CheckInvalidator jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionFactory$CheckInvalidator;
  private ThreadedInputConnectionProxyView jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionProxyView;
  private final InputMethodManagerWrapper jdField_a_of_type_OrgChromiumContent_publicBrowserInputMethodManagerWrapper;
  private boolean jdField_a_of_type_Boolean;
  
  ThreadedInputConnectionFactory(InputMethodManagerWrapper paramInputMethodManagerWrapper)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserInputMethodManagerWrapper = paramInputMethodManagerWrapper;
    this.jdField_a_of_type_OrgChromiumContentBrowserInputInputMethodUma = a();
  }
  
  private void a(final View paramView)
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    if (!paramView.hasFocus()) {
      return;
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionFactory$CheckInvalidator = new CheckInvalidator(null);
    if (!paramView.hasWindowFocus()) {
      this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionFactory$CheckInvalidator.invalidate();
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionProxyView = a(getHandler(), paramView);
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionProxyView.requestFocus();
    this.jdField_a_of_type_Boolean = false;
    paramView.getHandler().post(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnectionFactory.a(ThreadedInputConnectionFactory.this).onWindowFocusChanged(true);
        ThreadedInputConnectionFactory.a(ThreadedInputConnectionFactory.this).isActive(paramView);
        ThreadedInputConnectionFactory.this.getHandler().post(new Runnable()
        {
          public void run()
          {
            ThreadedInputConnectionFactory.a(ThreadedInputConnectionFactory.1.this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionFactory, ThreadedInputConnectionFactory.1.this.jdField_a_of_type_AndroidViewView, ThreadedInputConnectionFactory.a(ThreadedInputConnectionFactory.1.this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionFactory), 1);
          }
        });
      }
    });
  }
  
  private void a(final View paramView, final CheckInvalidator paramCheckInvalidator, final int paramInt)
  {
    Handler localHandler = paramView.getHandler();
    if (localHandler == null) {
      return;
    }
    localHandler.post(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnectionFactory.b(ThreadedInputConnectionFactory.this, paramView, paramCheckInvalidator, paramInt);
      }
    });
  }
  
  private boolean a()
  {
    StackTraceElement[] arrayOfStackTraceElement = Thread.currentThread().getStackTrace();
    int j = arrayOfStackTraceElement.length;
    int i = 0;
    while (i < j)
    {
      String str = arrayOfStackTraceElement[i].getClassName();
      if ((str != null) && ((str.contains(ThreadedInputConnectionProxyView.class.getName())) || (str.contains("TestInputMethodManagerWrapper")))) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  private void b(View paramView, CheckInvalidator paramCheckInvalidator, int paramInt)
  {
    if (this.jdField_a_of_type_OrgChromiumContent_publicBrowserInputMethodManagerWrapper.isActive(this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionProxyView))
    {
      a();
      return;
    }
    if (paramInt > 0)
    {
      a(paramView, paramCheckInvalidator, paramInt - 1);
      return;
    }
    if (paramCheckInvalidator.isInvalid()) {
      return;
    }
    b();
  }
  
  @VisibleForTesting
  protected InputMethodUma a()
  {
    return new InputMethodUma();
  }
  
  @VisibleForTesting
  protected ThreadedInputConnectionProxyView a(Handler paramHandler, View paramView)
  {
    return new ThreadedInputConnectionProxyView(paramView.getContext(), paramHandler, paramView);
  }
  
  @VisibleForTesting
  protected void a()
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserInputInputMethodUma.a();
  }
  
  @VisibleForTesting
  protected void b()
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserInputInputMethodUma.b();
  }
  
  public Handler getHandler()
  {
    return LazyHandlerHolder.a();
  }
  
  public ThreadedInputConnection initializeAndGet(View paramView, ImeAdapterImpl paramImeAdapterImpl, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, EditorInfo paramEditorInfo)
  {
    ImeUtils.a();
    ImeUtils.computeEditorInfo(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramEditorInfo);
    if ((Build.VERSION.SDK_INT < 24) || ("com.htc.android.mail".equals(paramView.getContext().getPackageName())))
    {
      paramEditorInfo = this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionFactory$CheckInvalidator;
      if (paramEditorInfo != null) {
        paramEditorInfo.invalidate();
      }
      if (a())
      {
        try
        {
          a(paramView);
        }
        catch (Exception paramView)
        {
          paramView.printStackTrace();
        }
        return null;
      }
    }
    paramEditorInfo = this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnection;
    if (paramEditorInfo == null) {
      this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnection = new ThreadedInputConnection(paramView, paramImeAdapterImpl, getHandler());
    } else {
      paramEditorInfo.a();
    }
    return this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnection;
  }
  
  public void onViewAttachedToWindow()
  {
    ThreadedInputConnectionProxyView localThreadedInputConnectionProxyView = this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionProxyView;
    if (localThreadedInputConnectionProxyView != null) {
      localThreadedInputConnectionProxyView.onOriginalViewAttachedToWindow();
    }
  }
  
  public void onViewDetachedFromWindow()
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionFactory$CheckInvalidator;
    if (localObject != null) {
      ((CheckInvalidator)localObject).invalidate();
    }
    localObject = this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionProxyView;
    if (localObject != null) {
      ((ThreadedInputConnectionProxyView)localObject).onOriginalViewDetachedFromWindow();
    }
  }
  
  public void onViewFocusChanged(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      localObject = this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionFactory$CheckInvalidator;
      if (localObject != null) {
        ((CheckInvalidator)localObject).invalidate();
      }
    }
    Object localObject = this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionProxyView;
    if (localObject != null) {
      ((ThreadedInputConnectionProxyView)localObject).onOriginalViewFocusChanged(paramBoolean);
    }
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      localObject = this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionFactory$CheckInvalidator;
      if (localObject != null) {
        ((CheckInvalidator)localObject).invalidate();
      }
    }
    Object localObject = this.jdField_a_of_type_OrgChromiumContentBrowserInputThreadedInputConnectionProxyView;
    if (localObject != null) {
      ((ThreadedInputConnectionProxyView)localObject).onOriginalViewWindowFocusChanged(paramBoolean);
    }
  }
  
  private static class CheckInvalidator
  {
    private boolean a;
    
    public void invalidate()
    {
      ImeUtils.a();
      this.a = true;
    }
    
    public boolean isInvalid()
    {
      ImeUtils.a();
      return this.a;
    }
  }
  
  private static class LazyHandlerHolder
  {
    private static final Handler a;
    
    static
    {
      HandlerThread localHandlerThread = new HandlerThread("InputConnectionHandlerThread", 5);
      localHandlerThread.start();
      a = new Handler(localHandlerThread.getLooper());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\ThreadedInputConnectionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */