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

abstract interface AwViewMethods
{
  public abstract int computeHorizontalScrollOffset();
  
  public abstract int computeHorizontalScrollRange();
  
  public abstract void computeScroll();
  
  public abstract int computeVerticalScrollExtent();
  
  public abstract int computeVerticalScrollOffset();
  
  public abstract int computeVerticalScrollRange();
  
  public abstract boolean dispatchKeyEvent(KeyEvent paramKeyEvent);
  
  public abstract AccessibilityNodeProvider getAccessibilityNodeProvider();
  
  public abstract void onAttachedToWindow();
  
  public abstract boolean onCheckIsTextEditor();
  
  public abstract void onConfigurationChanged(Configuration paramConfiguration);
  
  public abstract void onContainerViewOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void onContainerViewScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract InputConnection onCreateInputConnection(EditorInfo paramEditorInfo);
  
  public abstract void onDetachedFromWindow();
  
  public abstract boolean onDragEvent(DragEvent paramDragEvent);
  
  public abstract void onDraw(Canvas paramCanvas);
  
  public abstract void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect);
  
  public abstract boolean onGenericMotionEvent(MotionEvent paramMotionEvent);
  
  public abstract boolean onHoverEvent(MotionEvent paramMotionEvent);
  
  public abstract boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent);
  
  public abstract void onMeasure(int paramInt1, int paramInt2);
  
  public abstract void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  public abstract boolean onTouchEvent(MotionEvent paramMotionEvent);
  
  public abstract void onVisibilityChanged(View paramView, int paramInt);
  
  public abstract void onWindowFocusChanged(boolean paramBoolean);
  
  public abstract void onWindowVisibilityChanged(int paramInt);
  
  public abstract boolean performAccessibilityAction(int paramInt, Bundle paramBundle);
  
  public abstract void requestFocus();
  
  public abstract void setLayerType(int paramInt, Paint paramPaint);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwViewMethods.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */