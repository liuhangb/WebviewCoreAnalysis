package org.chromium.content.browser;

import android.view.KeyEvent;
import android.view.MotionEvent;
import org.chromium.content.browser.webcontents.WebContentsUserData;
import org.chromium.content.browser.webcontents.WebContentsUserData.UserDataFactory;
import org.chromium.content_public.browser.ImeEventObserver;
import org.chromium.content_public.browser.WebContents;
import org.chromium.ui.base.EventForwarder;

public class JoystickHandler
  implements ImeEventObserver
{
  private final EventForwarder jdField_a_of_type_OrgChromiumUiBaseEventForwarder;
  private boolean jdField_a_of_type_Boolean = true;
  
  private JoystickHandler(WebContents paramWebContents)
  {
    this.jdField_a_of_type_OrgChromiumUiBaseEventForwarder = paramWebContents.getEventForwarder();
  }
  
  private static float a(MotionEvent paramMotionEvent, int paramInt)
  {
    float f = paramMotionEvent.getAxisValue(paramInt);
    if (Math.abs(f) <= 0.2F) {
      return 0.0F;
    }
    return -f;
  }
  
  public static JoystickHandler fromWebContents(WebContents paramWebContents)
  {
    return (JoystickHandler)WebContentsUserData.fromWebContents(paramWebContents, JoystickHandler.class, UserDataFactoryLazyHolder.a());
  }
  
  public void onBeforeSendKeyEvent(KeyEvent paramKeyEvent) {}
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if (this.jdField_a_of_type_Boolean)
    {
      if ((paramMotionEvent.getSource() & 0x10) == 0) {
        return false;
      }
      float f1 = a(paramMotionEvent, 0);
      float f2 = a(paramMotionEvent, 1);
      if ((f1 == 0.0F) && (f2 == 0.0F)) {
        return false;
      }
      this.jdField_a_of_type_OrgChromiumUiBaseEventForwarder.a(paramMotionEvent.getEventTime(), f1, f2, true);
      return true;
    }
    return false;
  }
  
  public void onImeEvent() {}
  
  public void onNodeAttributeUpdated(boolean paramBoolean1, boolean paramBoolean2)
  {
    setScrollEnabled(paramBoolean1 ^ true);
  }
  
  public void setScrollEnabled(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  private static final class UserDataFactoryLazyHolder
  {
    private static final WebContentsUserData.UserDataFactory<JoystickHandler> a = new WebContentsUserData.UserDataFactory()
    {
      public JoystickHandler create(WebContents paramAnonymousWebContents)
      {
        return new JoystickHandler(paramAnonymousWebContents, null);
      }
    };
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\JoystickHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */