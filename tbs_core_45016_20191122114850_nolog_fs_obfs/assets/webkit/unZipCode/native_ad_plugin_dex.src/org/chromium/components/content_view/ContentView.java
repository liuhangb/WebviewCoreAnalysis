package org.chromium.components.content_view;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.FrameLayout;
import org.chromium.base.TraceEvent;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.content_public.browser.ContentViewCore.InternalAccessDelegate;
import org.chromium.content_public.browser.ImeAdapter;
import org.chromium.content_public.browser.SmartClipProvider;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.browser.WebContentsAccessibility;
import org.chromium.tencent.utils.ClassAdapter;
import org.chromium.ui.base.EventForwarder;

public class ContentView
  extends FrameLayout
  implements ContentViewCore.InternalAccessDelegate, SmartClipProvider
{
  public static final int a;
  protected final ContentViewCore a;
  private EventForwarder a;
  private int b;
  private int c;
  
  static
  {
    jdField_a_of_type_Int = View.MeasureSpec.makeMeasureSpec(0, 0);
  }
  
  private WebContents a()
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getWebContents();
  }
  
  private EventForwarder a()
  {
    if (this.jdField_a_of_type_OrgChromiumUiBaseEventForwarder == null) {
      this.jdField_a_of_type_OrgChromiumUiBaseEventForwarder = this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getWebContents().getEventForwarder();
    }
    return this.jdField_a_of_type_OrgChromiumUiBaseEventForwarder;
  }
  
  protected WebContentsAccessibility a()
  {
    WebContents localWebContents = a();
    if (localWebContents != null) {
      return ClassAdapter.fromWebContentsWebContentsAccessibility(localWebContents);
    }
    return null;
  }
  
  public boolean awakenScrollBars(int paramInt, boolean paramBoolean)
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.awakenScrollBars(paramInt, paramBoolean);
  }
  
  protected int computeHorizontalScrollExtent()
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.computeHorizontalScrollExtent();
  }
  
  protected int computeHorizontalScrollOffset()
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.computeHorizontalScrollOffset();
  }
  
  protected int computeHorizontalScrollRange()
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.computeHorizontalScrollRange();
  }
  
  protected int computeVerticalScrollExtent()
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.computeVerticalScrollExtent();
  }
  
  protected int computeVerticalScrollOffset()
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.computeVerticalScrollOffset();
  }
  
  protected int computeVerticalScrollRange()
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.computeVerticalScrollRange();
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (isFocused()) {
      return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.dispatchKeyEvent(paramKeyEvent);
    }
    return super.dispatchKeyEvent(paramKeyEvent);
  }
  
  public void extractSmartClipData(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getWebContents().requestSmartClipExtract(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public AccessibilityNodeProvider getAccessibilityNodeProvider()
  {
    Object localObject = a();
    if (localObject != null) {
      localObject = ((WebContentsAccessibility)localObject).getAccessibilityNodeProvider();
    } else {
      localObject = null;
    }
    if (localObject != null) {
      return (AccessibilityNodeProvider)localObject;
    }
    return super.getAccessibilityNodeProvider();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.onAttachedToWindow();
  }
  
  public boolean onCheckIsTextEditor()
  {
    if (a() == null) {
      return false;
    }
    return ImeAdapter.fromWebContents(a()).onCheckIsTextEditor();
  }
  
  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.onConfigurationChanged(paramConfiguration);
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    if (a() == null) {
      return null;
    }
    return ImeAdapter.fromWebContents(a()).onCreateInputConnection(paramEditorInfo);
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.onDetachedFromWindow();
  }
  
  public boolean onDragEvent(DragEvent paramDragEvent)
  {
    return a().a(paramDragEvent, this);
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    try
    {
      TraceEvent.begin("ContentView.onFocusChanged");
      super.onFocusChanged(paramBoolean, paramInt, paramRect);
      this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.onFocusChanged(paramBoolean, true);
      return;
    }
    finally
    {
      TraceEvent.end("ContentView.onFocusChanged");
    }
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.onGenericMotionEvent(paramMotionEvent);
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = a().c(paramMotionEvent);
    WebContentsAccessibility localWebContentsAccessibility = a();
    if ((localWebContentsAccessibility != null) && (!localWebContentsAccessibility.isTouchExplorationEnabled())) {
      super.onHoverEvent(paramMotionEvent);
    }
    return bool;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.onKeyUp(paramInt, paramKeyEvent);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = this.b;
    if (i != jdField_a_of_type_Int) {
      paramInt1 = i;
    }
    i = this.c;
    if (i != jdField_a_of_type_Int) {
      paramInt2 = i;
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return a().a(paramMotionEvent);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.onWindowFocusChanged(paramBoolean);
  }
  
  public boolean performAccessibilityAction(int paramInt, Bundle paramBundle)
  {
    WebContentsAccessibility localWebContentsAccessibility = a();
    if ((localWebContentsAccessibility != null) && (localWebContentsAccessibility.supportsAction(paramInt))) {
      return localWebContentsAccessibility.performAction(paramInt, paramBundle);
    }
    return super.performAccessibilityAction(paramInt, paramBundle);
  }
  
  public boolean performLongClick()
  {
    return false;
  }
  
  public void scrollBy(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.scrollBy(paramInt1, paramInt2);
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.scrollTo(paramInt1, paramInt2);
  }
  
  public void setSmartClipResultHandler(Handler paramHandler)
  {
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserContentViewCore.getWebContents().setSmartClipResultHandler(paramHandler);
  }
  
  public boolean super_awakenScrollBars(int paramInt, boolean paramBoolean)
  {
    return super.awakenScrollBars(paramInt, paramBoolean);
  }
  
  public boolean super_dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return super.dispatchKeyEvent(paramKeyEvent);
  }
  
  public void super_onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
  }
  
  public boolean super_onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    return super.onGenericMotionEvent(paramMotionEvent);
  }
  
  public boolean super_onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  private static class ContentViewApi23
    extends ContentView
  {
    public void onProvideVirtualStructure(ViewStructure paramViewStructure)
    {
      WebContentsAccessibility localWebContentsAccessibility = a();
      if (localWebContentsAccessibility != null) {
        localWebContentsAccessibility.onProvideVirtualStructure(paramViewStructure, false);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\content_view\ContentView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */