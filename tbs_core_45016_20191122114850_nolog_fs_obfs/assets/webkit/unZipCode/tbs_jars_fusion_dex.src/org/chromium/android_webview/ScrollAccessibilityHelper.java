package org.chromium.android_webview;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;

class ScrollAccessibilityHelper
{
  private Handler jdField_a_of_type_AndroidOsHandler;
  private boolean jdField_a_of_type_Boolean;
  
  public ScrollAccessibilityHelper(View paramView)
  {
    this.jdField_a_of_type_AndroidOsHandler = new Handler(new HandlerCallback(paramView));
  }
  
  public void postViewScrolledAccessibilityEventCallback()
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_Boolean = true;
    Message localMessage = this.jdField_a_of_type_AndroidOsHandler.obtainMessage(1);
    this.jdField_a_of_type_AndroidOsHandler.sendMessageDelayed(localMessage, 100L);
  }
  
  public void removePostedCallbacks()
  {
    removePostedViewScrolledAccessibilityEventCallback();
  }
  
  public void removePostedViewScrolledAccessibilityEventCallback()
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(1);
  }
  
  private class HandlerCallback
    implements Handler.Callback
  {
    public static final int MSG_VIEW_SCROLLED = 1;
    private View jdField_a_of_type_AndroidViewView;
    
    public HandlerCallback(View paramView)
    {
      this.jdField_a_of_type_AndroidViewView = paramView;
    }
    
    public boolean handleMessage(Message paramMessage)
    {
      if (paramMessage.what == 1)
      {
        ScrollAccessibilityHelper.a(ScrollAccessibilityHelper.this, false);
        this.jdField_a_of_type_AndroidViewView.sendAccessibilityEvent(4096);
        return true;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("AccessibilityInjector: unhandled message: ");
      localStringBuilder.append(paramMessage.what);
      throw new IllegalStateException(localStringBuilder.toString());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\ScrollAccessibilityHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */