package org.chromium.content_public.browser;

import org.chromium.content.browser.GestureListenerManagerImpl;

public abstract class GestureListenerManager
{
  public static GestureListenerManager fromWebContents(WebContents paramWebContents)
  {
    return GestureListenerManagerImpl.fromWebContents(paramWebContents);
  }
  
  public abstract void addListener(GestureStateListener paramGestureStateListener);
  
  public abstract void removeListener(GestureStateListener paramGestureStateListener);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\GestureListenerManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */