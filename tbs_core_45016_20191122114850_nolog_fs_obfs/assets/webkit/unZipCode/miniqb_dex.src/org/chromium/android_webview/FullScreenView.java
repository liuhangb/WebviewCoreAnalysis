package org.chromium.android_webview;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.FrameLayout;

public class FullScreenView
  extends FrameLayout
{
  private final AwContents jdField_a_of_type_OrgChromiumAndroid_webviewAwContents;
  private AwViewMethods jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods;
  private InternalAccessAdapter jdField_a_of_type_OrgChromiumAndroid_webviewFullScreenView$InternalAccessAdapter;
  
  public FullScreenView(Context paramContext, AwViewMethods paramAwViewMethods, AwContents paramAwContents)
  {
    super(paramContext);
    setAwViewMethods(paramAwViewMethods);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents = paramAwContents;
    this.jdField_a_of_type_OrgChromiumAndroid_webviewFullScreenView$InternalAccessAdapter = new InternalAccessAdapter(null);
  }
  
  public int computeHorizontalScrollOffset()
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.computeHorizontalScrollOffset();
  }
  
  public int computeHorizontalScrollRange()
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.computeHorizontalScrollRange();
  }
  
  public void computeScroll()
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.computeScroll();
  }
  
  public int computeVerticalScrollExtent()
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.computeVerticalScrollExtent();
  }
  
  public int computeVerticalScrollOffset()
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.computeVerticalScrollOffset();
  }
  
  public int computeVerticalScrollRange()
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.computeVerticalScrollRange();
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if ((paramKeyEvent.getKeyCode() == 4) && (paramKeyEvent.getAction() == 1) && (this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents.isFullScreen()))
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents.requestExitFullscreen();
      return true;
    }
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.dispatchKeyEvent(paramKeyEvent);
  }
  
  public AccessibilityNodeProvider getAccessibilityNodeProvider()
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.getAccessibilityNodeProvider();
  }
  
  public InternalAccessAdapter getInternalAccessAdapter()
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewFullScreenView$InternalAccessAdapter;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onAttachedToWindow();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onConfigurationChanged(paramConfiguration);
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onCreateInputConnection(paramEditorInfo);
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onDetachedFromWindow();
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onDraw(paramCanvas);
  }
  
  public void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onFocusChanged(paramBoolean, paramInt, paramRect);
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onGenericMotionEvent(paramMotionEvent);
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onHoverEvent(paramMotionEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onKeyUp(paramInt, paramKeyEvent);
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onMeasure(paramInt1, paramInt2);
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onContainerViewOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
  }
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onContainerViewScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onTouchEvent(paramMotionEvent);
  }
  
  protected void onVisibilityChanged(View paramView, int paramInt)
  {
    super.onVisibilityChanged(paramView, paramInt);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onVisibilityChanged(paramView, paramInt);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onWindowFocusChanged(paramBoolean);
  }
  
  public void onWindowVisibilityChanged(int paramInt)
  {
    super.onWindowVisibilityChanged(paramInt);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.onWindowVisibilityChanged(paramInt);
  }
  
  public boolean performAccessibilityAction(int paramInt, Bundle paramBundle)
  {
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.performAccessibilityAction(paramInt, paramBundle);
  }
  
  public boolean requestFocus(int paramInt, Rect paramRect)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.requestFocus();
    return super.requestFocus(paramInt, paramRect);
  }
  
  public void setAwViewMethods(AwViewMethods paramAwViewMethods)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods = paramAwViewMethods;
  }
  
  public void setLayerType(int paramInt, Paint paramPaint)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods.setLayerType(paramInt, paramPaint);
  }
  
  private class InternalAccessAdapter
    implements AwContents.InternalAccessDelegate
  {
    private InternalAccessAdapter() {}
    
    public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      FullScreenView.this.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
    {
      FullScreenView.a(FullScreenView.this, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
    }
    
    public void setMeasuredDimension(int paramInt1, int paramInt2)
    {
      FullScreenView.b(FullScreenView.this, paramInt1, paramInt2);
    }
    
    public boolean super_awakenScrollBars(int paramInt, boolean paramBoolean)
    {
      return FullScreenView.a(FullScreenView.this, paramInt, paramBoolean);
    }
    
    public boolean super_dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      return FullScreenView.a(FullScreenView.this, paramKeyEvent);
    }
    
    public int super_getScrollBarStyle()
    {
      return FullScreenView.a(FullScreenView.this);
    }
    
    public void super_onConfigurationChanged(Configuration paramConfiguration) {}
    
    public boolean super_onGenericMotionEvent(MotionEvent paramMotionEvent)
    {
      return FullScreenView.a(FullScreenView.this, paramMotionEvent);
    }
    
    public boolean super_onKeyUp(int paramInt, KeyEvent paramKeyEvent)
    {
      return FullScreenView.a(FullScreenView.this, paramInt, paramKeyEvent);
    }
    
    public void super_scrollTo(int paramInt1, int paramInt2)
    {
      FullScreenView.a(FullScreenView.this, paramInt1, paramInt2);
    }
    
    public void super_startActivityForResult(Intent paramIntent, int paramInt)
    {
      throw new RuntimeException("FullScreenView InternalAccessAdapter shouldn't call startActivityForResult. See AwContents#startActivityForResult");
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\FullScreenView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */