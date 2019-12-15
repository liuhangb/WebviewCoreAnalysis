package org.chromium.content_public.browser;

public abstract class GestureStateListener
{
  public void onDestroyed() {}
  
  public void onDoubleTapEventAck() {}
  
  public void onFlingEndGesture(int paramInt1, int paramInt2) {}
  
  public void onFlingStartGesture(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void onLongPress() {}
  
  public void onPinchEnded() {}
  
  public void onPinchStarted() {}
  
  public void onScaleLimitsChanged(float paramFloat1, float paramFloat2) {}
  
  public void onScrollEnded(int paramInt1, int paramInt2) {}
  
  public void onScrollOffsetOrExtentChanged(int paramInt1, int paramInt2) {}
  
  public void onScrollStarted(int paramInt1, int paramInt2) {}
  
  public void onScrollUpdateGestureConsumed() {}
  
  public void onShowUnhandledTapUIIfNeeded(int paramInt1, int paramInt2) {}
  
  public void onSingleTap(boolean paramBoolean) {}
  
  public void onTouchDown() {}
  
  public void onWindowFocusChanged(boolean paramBoolean) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\GestureStateListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */