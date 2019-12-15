package org.chromium.content_public.browser;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.View;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityNodeProvider;
import org.chromium.base.VisibleForTesting;

public abstract interface WebContentsAccessibility
{
  public abstract void disableAccessibilityIfNecessary();
  
  public abstract AccessibilityNodeProvider getAccessibilityNodeProvider();
  
  public abstract boolean isAccessibilityEnabled();
  
  public abstract boolean isTouchExplorationEnabled();
  
  public abstract void onAutofillPopupAccessibilityFocusCleared();
  
  public abstract void onAutofillPopupDismissed();
  
  public abstract void onAutofillPopupDisplayed(View paramView);
  
  @TargetApi(23)
  public abstract void onProvideVirtualStructure(ViewStructure paramViewStructure, boolean paramBoolean);
  
  public abstract boolean performAction(int paramInt, Bundle paramBundle);
  
  @VisibleForTesting
  public abstract void setAccessibilityEnabledForTesting();
  
  public abstract void setObscuredByAnotherView(boolean paramBoolean);
  
  public abstract void setShouldFocusOnPageLoad(boolean paramBoolean);
  
  public abstract void setState(boolean paramBoolean);
  
  public abstract void startAccessibilityIfNecessary();
  
  public abstract boolean supportsAction(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\WebContentsAccessibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */