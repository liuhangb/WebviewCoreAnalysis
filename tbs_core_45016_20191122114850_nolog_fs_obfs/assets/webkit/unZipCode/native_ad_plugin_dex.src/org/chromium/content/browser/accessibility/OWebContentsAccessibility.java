package org.chromium.content.browser.accessibility;

import android.annotation.TargetApi;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.Arrays;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.WebContents;

@JNINamespace("content")
@TargetApi(26)
public class OWebContentsAccessibility
  extends LollipopWebContentsAccessibility
{
  static
  {
    jdField_a_of_type_Boolean = OWebContentsAccessibility.class.desiredAssertionStatus() ^ true;
  }
  
  OWebContentsAccessibility(WebContents paramWebContents)
  {
    super(paramWebContents);
  }
  
  public void addExtraDataToAccessibilityNodeInfo(int paramInt, AccessibilityNodeInfo paramAccessibilityNodeInfo, String paramString, Bundle paramBundle)
  {
    if (!paramString.equals("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY")) {
      return;
    }
    if (!nativeAreInlineTextBoxesLoaded(this.jdField_a_of_type_Long, paramInt)) {
      nativeLoadInlineTextBoxes(this.jdField_a_of_type_Long, paramInt);
    }
    int j = paramBundle.getInt("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX", -1);
    int i = paramBundle.getInt("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH", -1);
    if (i > 0)
    {
      if (j < 0) {
        return;
      }
      paramBundle = nativeGetCharacterBoundingBoxes(this.jdField_a_of_type_Long, paramInt, j, i);
      if (paramBundle == null) {
        return;
      }
      if ((!jdField_a_of_type_Boolean) && (paramBundle.length != i * 4)) {
        throw new AssertionError();
      }
      RectF[] arrayOfRectF = new RectF[i];
      paramInt = 0;
      while (paramInt < i)
      {
        j = paramInt * 4;
        Rect localRect = new Rect(paramBundle[(j + 0)], paramBundle[(j + 1)], paramBundle[(j + 2)], paramBundle[(j + 3)]);
        a(localRect);
        arrayOfRectF[paramInt] = new RectF(localRect);
        paramInt += 1;
      }
      paramAccessibilityNodeInfo.getExtras().putParcelableArray(paramString, arrayOfRectF);
      return;
    }
  }
  
  protected void setAccessibilityNodeInfoKitKatAttributes(AccessibilityNodeInfo paramAccessibilityNodeInfo, boolean paramBoolean1, boolean paramBoolean2, String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, boolean paramBoolean3)
  {
    super.setAccessibilityNodeInfoKitKatAttributes(paramAccessibilityNodeInfo, paramBoolean1, paramBoolean2, paramString1, paramString2, paramString3, paramInt1, paramInt2, paramBoolean3);
    paramAccessibilityNodeInfo.setHintText(paramString3);
  }
  
  protected void setAccessibilityNodeInfoOAttributes(AccessibilityNodeInfo paramAccessibilityNodeInfo, boolean paramBoolean)
  {
    if (!paramBoolean) {
      return;
    }
    paramAccessibilityNodeInfo.setAvailableExtraData(Arrays.asList(new String[] { "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY" }));
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\OWebContentsAccessibility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */