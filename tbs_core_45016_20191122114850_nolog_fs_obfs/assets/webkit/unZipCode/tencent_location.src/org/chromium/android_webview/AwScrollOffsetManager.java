package org.chromium.android_webview;

import android.graphics.Rect;
import org.chromium.base.VisibleForTesting;

@VisibleForTesting
public class AwScrollOffsetManager
{
  private static final int MAX_SCROLL_ANIMATION_DURATION_MILLISEC = 750;
  private static final int PAGE_SCROLL_OVERLAP = 24;
  private static final int STD_SCROLL_ANIMATION_SPEED_PIX_PER_SEC = 480;
  private boolean mApplyDeferredNativeScroll;
  private int mContainerViewHeight;
  private int mContainerViewWidth;
  private int mDeferredNativeScrollX;
  private int mDeferredNativeScrollY;
  private final Delegate mDelegate;
  private int mMaxHorizontalScrollOffset;
  private int mMaxVerticalScrollOffset;
  private int mNativeScrollX;
  private int mNativeScrollY;
  private boolean mProcessingTouchEvent;
  
  public AwScrollOffsetManager(Delegate paramDelegate)
  {
    this.mDelegate = paramDelegate;
  }
  
  private boolean animateScrollTo(int paramInt1, int paramInt2)
  {
    int i = this.mDelegate.getContainerViewScrollX();
    int j = this.mDelegate.getContainerViewScrollY();
    paramInt1 = clampHorizontalScroll(paramInt1);
    paramInt2 = clampVerticalScroll(paramInt2);
    paramInt1 -= i;
    paramInt2 -= j;
    if ((paramInt1 == 0) && (paramInt2 == 0)) {
      return false;
    }
    this.mDelegate.smoothScroll(i + paramInt1, j + paramInt2, computeDurationInMilliSec(paramInt1, paramInt2));
    this.mDelegate.invalidate();
    return true;
  }
  
  private int clampHorizontalScroll(int paramInt)
  {
    paramInt = Math.max(0, paramInt);
    return Math.min(computeMaximumHorizontalScrollOffset(), paramInt);
  }
  
  public static int computeDurationInMilliSec(int paramInt1, int paramInt2)
  {
    return Math.min(Math.max(Math.abs(paramInt1), Math.abs(paramInt2)) * 1000 / 480, 750);
  }
  
  private void scrollBy(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 0) && (paramInt2 == 0)) {
      return;
    }
    int i = this.mDelegate.getContainerViewScrollX();
    int j = this.mDelegate.getContainerViewScrollY();
    int k = computeMaximumHorizontalScrollOffset();
    int m = computeMaximumVerticalScrollOffset();
    this.mDelegate.overScrollContainerViewBy(paramInt1, paramInt2, i, j, k, m, this.mProcessingTouchEvent);
  }
  
  protected int clampVerticalScroll(int paramInt)
  {
    paramInt = Math.max(0, paramInt);
    return Math.min(computeMaximumVerticalScrollOffset(), paramInt);
  }
  
  public int computeHorizontalScrollOffset()
  {
    return this.mDelegate.getContainerViewScrollX();
  }
  
  public int computeHorizontalScrollRange()
  {
    return this.mContainerViewWidth + this.mMaxHorizontalScrollOffset;
  }
  
  public int computeMaximumHorizontalScrollOffset()
  {
    return this.mMaxHorizontalScrollOffset;
  }
  
  public int computeMaximumVerticalScrollOffset()
  {
    return this.mMaxVerticalScrollOffset;
  }
  
  public int computeVerticalScrollExtent()
  {
    return this.mContainerViewHeight;
  }
  
  public int computeVerticalScrollOffset()
  {
    return this.mDelegate.getContainerViewScrollY();
  }
  
  public int computeVerticalScrollRange()
  {
    return this.mContainerViewHeight + this.mMaxVerticalScrollOffset;
  }
  
  int getScrollX()
  {
    return this.mNativeScrollX;
  }
  
  int getScrollY()
  {
    return this.mNativeScrollY;
  }
  
  public void onContainerViewOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramInt1 = clampHorizontalScroll(paramInt1);
    paramInt2 = clampVerticalScroll(paramInt2);
    this.mDelegate.scrollContainerViewTo(paramInt1, paramInt2);
    scrollNativeTo(this.mDelegate.getContainerViewScrollX(), this.mDelegate.getContainerViewScrollY());
  }
  
  public void onContainerViewScrollChanged(int paramInt1, int paramInt2)
  {
    scrollNativeTo(paramInt1, paramInt2);
  }
  
  public void overScrollBy(int paramInt1, int paramInt2)
  {
    scrollBy(paramInt1, paramInt2);
  }
  
  public boolean pageDown(boolean paramBoolean)
  {
    int j = this.mDelegate.getContainerViewScrollX();
    int k = this.mDelegate.getContainerViewScrollY();
    if (paramBoolean) {
      return animateScrollTo(j, computeVerticalScrollRange());
    }
    int m = this.mContainerViewHeight;
    int i = m / 2;
    if (m > 48) {
      i = m - 24;
    }
    return animateScrollTo(j, k + i);
  }
  
  public boolean pageUp(boolean paramBoolean)
  {
    int j = this.mDelegate.getContainerViewScrollX();
    int k = this.mDelegate.getContainerViewScrollY();
    if (paramBoolean) {
      return animateScrollTo(j, 0);
    }
    int m = this.mContainerViewHeight;
    int i = -m / 2;
    if (m > 48) {
      i = -m + 24;
    }
    return animateScrollTo(j, k + i);
  }
  
  public boolean requestChildRectangleOnScreen(int paramInt1, int paramInt2, Rect paramRect, boolean paramBoolean)
  {
    int i = this.mDelegate.getContainerViewScrollX();
    int j = this.mDelegate.getContainerViewScrollY();
    paramRect.offset(paramInt1, paramInt2);
    paramInt1 = this.mContainerViewHeight;
    if (paramRect.bottom > paramInt1 + j)
    {
      paramInt1 = this.mContainerViewHeight / 3;
      if (paramRect.width() > paramInt1 * 2) {
        paramInt1 = paramRect.top - j;
      } else {
        paramInt1 = paramRect.top - (paramInt1 + j);
      }
    }
    else if (paramRect.top < j)
    {
      paramInt1 = paramRect.top - j;
    }
    else
    {
      paramInt1 = 0;
    }
    paramInt2 = this.mContainerViewWidth + i;
    if ((paramRect.right > paramInt2) && (paramRect.left > i))
    {
      if (paramRect.width() > this.mContainerViewWidth) {
        paramInt2 = paramRect.left - i + 0;
      } else {
        paramInt2 = paramRect.right - paramInt2 + 0;
      }
    }
    else if (paramRect.left < i) {
      paramInt2 = 0 - (i - paramRect.left);
    } else {
      paramInt2 = 0;
    }
    if ((paramInt1 == 0) && (paramInt2 == 0)) {
      return false;
    }
    if (paramBoolean)
    {
      scrollBy(paramInt2, paramInt1);
      return true;
    }
    return animateScrollTo(i + paramInt2, j + paramInt1);
  }
  
  public void scrollContainerViewTo(int paramInt1, int paramInt2)
  {
    this.mNativeScrollX = paramInt1;
    this.mNativeScrollY = paramInt2;
    int i = this.mDelegate.getContainerViewScrollX();
    int j = this.mDelegate.getContainerViewScrollY();
    int k = computeMaximumHorizontalScrollOffset();
    int m = computeMaximumVerticalScrollOffset();
    this.mDelegate.overScrollContainerViewBy(paramInt1 - i, paramInt2 - j, i, j, k, m, this.mProcessingTouchEvent);
  }
  
  protected void scrollNativeTo(int paramInt1, int paramInt2)
  {
    paramInt1 = clampHorizontalScroll(paramInt1);
    paramInt2 = clampVerticalScroll(paramInt2);
    if (this.mProcessingTouchEvent)
    {
      this.mDeferredNativeScrollX = paramInt1;
      this.mDeferredNativeScrollY = paramInt2;
      this.mApplyDeferredNativeScroll = true;
      return;
    }
    if ((paramInt1 == this.mNativeScrollX) && (paramInt2 == this.mNativeScrollY)) {
      return;
    }
    this.mNativeScrollX = paramInt1;
    this.mNativeScrollY = paramInt2;
    this.mDelegate.scrollNativeTo(paramInt1, paramInt2);
  }
  
  public void setContainerViewSize(int paramInt1, int paramInt2)
  {
    this.mContainerViewWidth = paramInt1;
    this.mContainerViewHeight = paramInt2;
  }
  
  public void setMaxScrollOffset(int paramInt1, int paramInt2)
  {
    this.mMaxHorizontalScrollOffset = paramInt1;
    this.mMaxVerticalScrollOffset = paramInt2;
  }
  
  public void setProcessingTouchEvent(boolean paramBoolean)
  {
    this.mProcessingTouchEvent = paramBoolean;
    if ((!this.mProcessingTouchEvent) && (this.mApplyDeferredNativeScroll))
    {
      this.mApplyDeferredNativeScroll = false;
      scrollNativeTo(this.mDeferredNativeScrollX, this.mDeferredNativeScrollY);
    }
  }
  
  public void syncScrollOffsetFromOnDraw()
  {
    onContainerViewScrollChanged(this.mDelegate.getContainerViewScrollX(), this.mDelegate.getContainerViewScrollY());
  }
  
  public static abstract interface Delegate
  {
    public abstract void cancelFling();
    
    public abstract int getContainerViewScrollX();
    
    public abstract int getContainerViewScrollY();
    
    public abstract void invalidate();
    
    public abstract void overScrollContainerViewBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean);
    
    public abstract void scrollContainerViewTo(int paramInt1, int paramInt2);
    
    public abstract void scrollNativeTo(int paramInt1, int paramInt2);
    
    public abstract void smoothScroll(int paramInt1, int paramInt2, long paramLong);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwScrollOffsetManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */