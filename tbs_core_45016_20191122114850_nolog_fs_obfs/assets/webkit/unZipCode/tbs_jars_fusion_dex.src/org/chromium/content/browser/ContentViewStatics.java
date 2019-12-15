package org.chromium.content.browser;

import org.chromium.net.ProxyChangeListener;

public class ContentViewStatics
{
  public static void disablePlatformNotifications()
  {
    ProxyChangeListener.setEnabled(false);
  }
  
  public static void enablePlatformNotifications()
  {
    ProxyChangeListener.setEnabled(true);
  }
  
  private static native void nativeSetWebKitSharedTimersSuspended(boolean paramBoolean);
  
  public static void setWebKitSharedTimersSuspended(boolean paramBoolean)
  {
    nativeSetWebKitSharedTimersSuspended(paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ContentViewStatics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */