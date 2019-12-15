package org.chromium.content.browser.input;

import android.content.Context;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.UsedByReflection;

@UsedByReflection("ThreadedInputConnectionFactory.java")
public class ThreadedInputConnectionProxyView
  extends View
{
  private final Handler jdField_a_of_type_AndroidOsHandler;
  private final View jdField_a_of_type_AndroidViewView;
  private final AtomicBoolean jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean = new AtomicBoolean();
  private final AtomicReference<IBinder> jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference = new AtomicReference();
  private final AtomicBoolean jdField_b_of_type_JavaUtilConcurrentAtomicAtomicBoolean = new AtomicBoolean();
  private final AtomicReference<View> jdField_b_of_type_JavaUtilConcurrentAtomicAtomicReference = new AtomicReference();
  
  ThreadedInputConnectionProxyView(Context paramContext, Handler paramHandler, View paramView)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidOsHandler = paramHandler;
    this.jdField_a_of_type_AndroidViewView = paramView;
    setFocusable(true);
    setFocusableInTouchMode(true);
    setVisibility(0);
    this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean.set(this.jdField_a_of_type_AndroidViewView.hasFocus());
    this.jdField_b_of_type_JavaUtilConcurrentAtomicAtomicBoolean.set(this.jdField_a_of_type_AndroidViewView.hasWindowFocus());
    this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.set(this.jdField_a_of_type_AndroidViewView.getWindowToken());
    this.jdField_b_of_type_JavaUtilConcurrentAtomicAtomicReference.set(this.jdField_a_of_type_AndroidViewView.getRootView());
  }
  
  public boolean checkInputConnectionProxy(View paramView)
  {
    return this.jdField_a_of_type_AndroidViewView == paramView;
  }
  
  public Handler getHandler()
  {
    return this.jdField_a_of_type_AndroidOsHandler;
  }
  
  public View getRootView()
  {
    if (this.jdField_b_of_type_JavaUtilConcurrentAtomicAtomicBoolean.get()) {
      return (View)this.jdField_b_of_type_JavaUtilConcurrentAtomicAtomicReference.get();
    }
    return null;
  }
  
  public IBinder getWindowToken()
  {
    return (IBinder)this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.get();
  }
  
  public boolean hasWindowFocus()
  {
    return this.jdField_b_of_type_JavaUtilConcurrentAtomicAtomicBoolean.get();
  }
  
  public boolean isFocused()
  {
    return this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean.get();
  }
  
  public boolean onCheckIsTextEditor()
  {
    return true;
  }
  
  public InputConnection onCreateInputConnection(final EditorInfo paramEditorInfo)
  {
    (InputConnection)ThreadUtils.runOnUiThreadBlockingNoException(new Callable()
    {
      public InputConnection call()
        throws Exception
      {
        return ThreadedInputConnectionProxyView.a(ThreadedInputConnectionProxyView.this).onCreateInputConnection(paramEditorInfo);
      }
    });
  }
  
  public void onOriginalViewAttachedToWindow()
  {
    this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.set(this.jdField_a_of_type_AndroidViewView.getWindowToken());
    this.jdField_b_of_type_JavaUtilConcurrentAtomicAtomicReference.set(this.jdField_a_of_type_AndroidViewView.getRootView());
  }
  
  public void onOriginalViewDetachedFromWindow()
  {
    this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicReference.set(null);
    this.jdField_b_of_type_JavaUtilConcurrentAtomicAtomicReference.set(null);
  }
  
  public void onOriginalViewFocusChanged(boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaUtilConcurrentAtomicAtomicBoolean.set(paramBoolean);
  }
  
  public void onOriginalViewWindowFocusChanged(boolean paramBoolean)
  {
    this.jdField_b_of_type_JavaUtilConcurrentAtomicAtomicBoolean.set(paramBoolean);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\ThreadedInputConnectionProxyView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */