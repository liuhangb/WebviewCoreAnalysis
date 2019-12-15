package org.chromium.content_public.browser;

import android.view.KeyEvent;

public abstract interface ImeEventObserver
{
  public abstract void onBeforeSendKeyEvent(KeyEvent paramKeyEvent);
  
  public abstract void onImeEvent();
  
  public abstract void onNodeAttributeUpdated(boolean paramBoolean1, boolean paramBoolean2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\ImeEventObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */