package org.chromium.content.browser.accessibility;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.TargetApi;
import android.os.Bundle;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.WebContents;

@JNINamespace("content")
@TargetApi(19)
public class KitKatWebContentsAccessibility
  extends WebContentsAccessibilityImpl
{
  private String a;
  
  KitKatWebContentsAccessibility(WebContents paramWebContents)
  {
    super(paramWebContents);
  }
  
  protected void a()
  {
    super.a();
    this.jdField_a_of_type_JavaLangString = nativeGetSupportedHtmlElementTypes(this.jdField_a_of_type_Long);
  }
  
  protected int getAccessibilityServiceCapabilitiesMask()
  {
    Iterator localIterator = this.jdField_a_of_type_AndroidViewAccessibilityAccessibilityManager.getEnabledAccessibilityServiceList(-1).iterator();
    int i = 0;
    while (localIterator.hasNext())
    {
      AccessibilityServiceInfo localAccessibilityServiceInfo = (AccessibilityServiceInfo)localIterator.next();
      if (localAccessibilityServiceInfo != null) {
        i |= localAccessibilityServiceInfo.getCapabilities();
      }
    }
    return i;
  }
  
  protected void setAccessibilityNodeInfoKitKatAttributes(AccessibilityNodeInfo paramAccessibilityNodeInfo, boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, boolean paramBoolean3)
  {
    Bundle localBundle = paramAccessibilityNodeInfo.getExtras();
    localBundle.putCharSequence("AccessibilityNodeInfo.chromeRole", paramString1);
    localBundle.putCharSequence("AccessibilityNodeInfo.roleDescription", paramString2);
    localBundle.putCharSequence("AccessibilityNodeInfo.hint", paramString3);
    if (paramBoolean3) {
      localBundle.putCharSequence("AccessibilityNodeInfo.hasImage", "true");
    }
    if (paramBoolean1) {
      localBundle.putCharSequence("ACTION_ARGUMENT_HTML_ELEMENT_STRING_VALUES", this.jdField_a_of_type_JavaLangString);
    }
    if (paramBoolean2)
    {
      paramAccessibilityNodeInfo.setEditable(true);
      paramAccessibilityNodeInfo.setTextSelection(paramInt1, paramInt2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\KitKatWebContentsAccessibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */