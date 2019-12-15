package org.chromium.content_public.browser;

import android.app.Activity;

public abstract interface ScreenOrientationDelegate
{
  public abstract boolean canLockOrientation();
  
  public abstract boolean canUnlockOrientation(Activity paramActivity, int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\ScreenOrientationDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */