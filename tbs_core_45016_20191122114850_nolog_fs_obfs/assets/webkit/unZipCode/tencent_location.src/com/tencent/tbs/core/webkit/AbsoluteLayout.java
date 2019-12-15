package com.tencent.tbs.core.webkit;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

@TargetApi(14)
public class AbsoluteLayout
  extends android.widget.AbsoluteLayout
{
  public AbsoluteLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public AbsoluteLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public AbsoluteLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public AbsoluteLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    super(paramContext, paramAttributeSet, paramInt1);
  }
  
  public void onInitializeAccessibilityEventInternal(AccessibilityEvent paramAccessibilityEvent)
  {
    try
    {
      int i = View.class.getDeclaredMethod("onInitializeAccessibilityEventInternal", new Class[] { AccessibilityEvent.class }).getModifiers();
      if ((Modifier.isPublic(i)) || (Modifier.isProtected(i)))
      {
        super.onInitializeAccessibilityEventInternal(paramAccessibilityEvent);
        return;
      }
    }
    catch (Throwable paramAccessibilityEvent)
    {
      paramAccessibilityEvent.printStackTrace();
    }
  }
  
  public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    try
    {
      int i = View.class.getDeclaredMethod("onInitializeAccessibilityNodeInfoInternal", new Class[] { AccessibilityNodeInfo.class }).getModifiers();
      if ((Modifier.isPublic(i)) || (Modifier.isProtected(i)))
      {
        super.onInitializeAccessibilityNodeInfoInternal(paramAccessibilityNodeInfo);
        return;
      }
    }
    catch (Throwable paramAccessibilityNodeInfo)
    {
      paramAccessibilityNodeInfo.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\AbsoluteLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */