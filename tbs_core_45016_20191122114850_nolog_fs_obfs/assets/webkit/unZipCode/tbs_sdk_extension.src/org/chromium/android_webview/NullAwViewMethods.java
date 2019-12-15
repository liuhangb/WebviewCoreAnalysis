package org.chromium.android_webview;

import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

class NullAwViewMethods
  implements AwViewMethods
{
  private View jdField_a_of_type_AndroidViewView;
  private AwContents.InternalAccessDelegate jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$InternalAccessDelegate;
  private AwContents jdField_a_of_type_OrgChromiumAndroid_webviewAwContents;
  
  public NullAwViewMethods(AwContents paramAwContents, AwContents.InternalAccessDelegate paramInternalAccessDelegate, View paramView)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents = paramAwContents;
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$InternalAccessDelegate = paramInternalAccessDelegate;
    this.jdField_a_of_type_AndroidViewView = paramView;
  }
  
  public int computeHorizontalScrollOffset()
  {
    return 0;
  }
  
  public int computeHorizontalScrollRange()
  {
    return 0;
  }
  
  public void computeScroll() {}
  
  public int computeVerticalScrollExtent()
  {
    return 0;
  }
  
  public int computeVerticalScrollOffset()
  {
    return 0;
  }
  
  public int computeVerticalScrollRange()
  {
    return 0;
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return false;
  }
  
  public AccessibilityNodeProvider getAccessibilityNodeProvider()
  {
    return null;
  }
  
  public void onAttachedToWindow() {}
  
  public boolean onCheckIsTextEditor()
  {
    return false;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration) {}
  
  public void onContainerViewOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {}
  
  public void onContainerViewScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    return null;
  }
  
  public void onDetachedFromWindow() {}
  
  public boolean onDragEvent(DragEvent paramDragEvent)
  {
    return false;
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    paramCanvas.drawColor(this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents.getEffectiveBackgroundColor());
  }
  
  public void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect) {}
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return false;
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$InternalAccessDelegate.setMeasuredDimension(this.jdField_a_of_type_AndroidViewView.getMeasuredWidth(), this.jdField_a_of_type_AndroidViewView.getMeasuredHeight());
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  public void onVisibilityChanged(View paramView, int paramInt) {}
  
  public void onWindowFocusChanged(boolean paramBoolean) {}
  
  public void onWindowVisibilityChanged(int paramInt) {}
  
  public boolean performAccessibilityAction(int paramInt, Bundle paramBundle)
  {
    return false;
  }
  
  public void requestFocus() {}
  
  public void setLayerType(int paramInt, Paint paramPaint) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\NullAwViewMethods.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */