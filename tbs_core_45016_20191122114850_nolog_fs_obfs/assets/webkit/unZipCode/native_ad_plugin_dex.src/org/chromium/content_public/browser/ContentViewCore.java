package org.chromium.content_public.browser;

import android.content.Context;
import android.content.res.Configuration;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ViewGroup;
import org.chromium.base.VisibleForTesting;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.tencent.TencentContentViewCore;
import org.chromium.ui.base.ViewAndroidDelegate;
import org.chromium.ui.base.WindowAndroid;

public abstract class ContentViewCore
{
  public static ContentViewCore create(Context paramContext, String paramString)
  {
    return new TencentContentViewCore(paramContext, paramString);
  }
  
  public abstract boolean awakenScrollBars(int paramInt, boolean paramBoolean);
  
  public abstract int computeHorizontalScrollExtent();
  
  public abstract int computeHorizontalScrollOffset();
  
  public abstract int computeHorizontalScrollRange();
  
  public abstract int computeVerticalScrollExtent();
  
  public abstract int computeVerticalScrollOffset();
  
  public abstract int computeVerticalScrollRange();
  
  public abstract void destroy();
  
  public abstract boolean dispatchKeyEvent(KeyEvent paramKeyEvent);
  
  public abstract ViewGroup getContainerView();
  
  public abstract Context getContext();
  
  public abstract boolean getIsMobileOptimizedHint();
  
  public abstract RenderCoordinates getRenderCoordinates();
  
  @VisibleForTesting
  public abstract int getTopControlsShrinkBlinkHeightForTesting();
  
  public abstract int getViewportHeightPix();
  
  public abstract int getViewportWidthPix();
  
  public abstract WebContents getWebContents();
  
  public abstract WindowAndroid getWindowAndroid();
  
  public abstract void initialize(ViewAndroidDelegate paramViewAndroidDelegate, InternalAccessDelegate paramInternalAccessDelegate, WebContents paramWebContents, WindowAndroid paramWindowAndroid);
  
  public abstract boolean isAlive();
  
  public abstract boolean isAttachedToWindow();
  
  public abstract boolean isScrollInProgress();
  
  @VisibleForTesting
  public abstract boolean isSelectPopupVisibleForTest();
  
  public abstract void onAttachedToWindow();
  
  public abstract void onConfigurationChanged(Configuration paramConfiguration);
  
  public abstract void onDetachedFromWindow();
  
  public abstract void onFocusChanged(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract boolean onGenericMotionEvent(MotionEvent paramMotionEvent);
  
  public abstract void onHide();
  
  public abstract boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent);
  
  public abstract void onPause();
  
  public abstract void onResume();
  
  public abstract void onShow();
  
  public abstract void onWindowFocusChanged(boolean paramBoolean);
  
  public abstract void preserveSelectionOnNextLossOfFocus();
  
  public abstract void scrollBy(float paramFloat1, float paramFloat2);
  
  public abstract void scrollTo(float paramFloat1, float paramFloat2);
  
  public abstract void selectPopupMenuItems(int[] paramArrayOfInt);
  
  @VisibleForTesting
  public abstract void sendDoubleTapForTest(long paramLong, int paramInt1, int paramInt2);
  
  public abstract void setContainerView(ViewGroup paramViewGroup);
  
  public abstract void setContainerViewInternals(InternalAccessDelegate paramInternalAccessDelegate);
  
  public abstract void updateDoubleTapSupport(boolean paramBoolean);
  
  public abstract void updateMultiTouchZoomSupport(boolean paramBoolean);
  
  public abstract void updateTextSelectionUI(boolean paramBoolean);
  
  public abstract void updateWindowAndroid(WindowAndroid paramWindowAndroid);
  
  public static abstract interface InternalAccessDelegate
  {
    public abstract void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
    
    public abstract boolean super_awakenScrollBars(int paramInt, boolean paramBoolean);
    
    public abstract boolean super_dispatchKeyEvent(KeyEvent paramKeyEvent);
    
    public abstract void super_onConfigurationChanged(Configuration paramConfiguration);
    
    public abstract boolean super_onGenericMotionEvent(MotionEvent paramMotionEvent);
    
    public abstract boolean super_onKeyUp(int paramInt, KeyEvent paramKeyEvent);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\ContentViewCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */