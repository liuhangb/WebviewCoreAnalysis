package org.chromium.components.navigation_interception;

import org.chromium.base.annotations.CalledByNative;

public abstract interface InterceptNavigationDelegate
{
  @CalledByNative
  public abstract boolean shouldIgnoreNavigation(NavigationParams paramNavigationParams);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\navigation_interception\InterceptNavigationDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */