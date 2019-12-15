package org.chromium.tencent.utils;

import org.chromium.content.browser.accessibility.WebContentsAccessibilityImpl;
import org.chromium.content.browser.selection.SelectionPopupControllerImpl;
import org.chromium.content_public.browser.SelectionPopupController;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.browser.WebContentsAccessibility;

public class ClassAdapter
{
  public static SelectionPopupController fromWebContentsSelectionPopupController(WebContents paramWebContents)
  {
    return SelectionPopupControllerImpl.fromWebContents(paramWebContents);
  }
  
  public static WebContentsAccessibility fromWebContentsWebContentsAccessibility(WebContents paramWebContents)
  {
    return WebContentsAccessibilityImpl.fromWebContents(paramWebContents);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\utils\ClassAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */