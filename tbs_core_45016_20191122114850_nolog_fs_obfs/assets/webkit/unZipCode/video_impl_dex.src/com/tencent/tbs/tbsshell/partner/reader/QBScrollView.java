package com.tencent.tbs.tbsshell.partner.reader;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout.LayoutParams;
import java.util.ArrayList;
import java.util.Iterator;

public class QBScrollView
  extends ViewGroup
{
  private static final long ANIMATED_SCROLL_GAP = 250L;
  public static final boolean DEBUG = true;
  public static final byte HORIZONTAL = 0;
  private static final int INVALID_POINTER = -1;
  static final int MSG_SCROLLBAR_HIDE = 1;
  public static final int REFRESH_RESULT_FAILED = 3;
  public static final int REFRESH_RESULT_NOTCARE = 1;
  public static final int REFRESH_RESULT_SUCCESSS = 2;
  public static final int REFRESH_STATE_FAILED = 3;
  public static final int REFRESH_STATE_ING = 1;
  public static final int REFRESH_STATE_SUCCESSS = 2;
  public static final int REFRESH_STATE_WAIT = 0;
  static final int SCROLLBAR_ALPHA_HIDE = 0;
  static final int SCROLLBAR_ALPHA_NIGHT = 76;
  static final int SCROLLBAR_ALPHA_SHOW = 100;
  static final int SCROLLBAR_ALPHA_STEP = 20;
  static final int SCROLL_BAR_MIN_HEIGHT = UIResourceDimen.dimen.uifw_control_scrollbar_min_height;
  public static final int SCROLL_STATE_DRAGGING = 1;
  public static final int SCROLL_STATE_IDLE = 0;
  public static final int SCROLL_STATE_SETTLING = 2;
  static final String TAG = "TMYS";
  static final int TOUCH_DRAG_MODE = 1;
  static final int TOUCH_INIT_MODE = 0;
  static final int TOUCH_STATE_SCROLLING = 10;
  public static final byte VERTICAL = 1;
  private boolean mCenterVertival = false;
  protected boolean mCheckDescend = true;
  boolean mDownDragOutSizeEnable = false;
  int mDownOffset = 0;
  private boolean mFlingDisabled;
  private boolean mFooterAppearedReported;
  boolean mHasStartFling = false;
  Handler mHideScrollBarAction;
  private int mInitialTouchX;
  private int mInitialTouchY;
  private boolean mIsUnableToDrag;
  int mLastOffset;
  private long mLastScroll;
  int mLastTouchX;
  int mLastTouchY;
  private int mMaxAlpha = 100;
  private int mMaxLength = -1;
  int mMinimumVelocity;
  boolean mNeedScrollbar = true;
  int mOffset;
  int mOrientation = 1;
  private int mRefreshState = 0;
  boolean mResetPos = false;
  int mScrollBarAlpha;
  int mScrollBarBottomMargin = 0;
  Drawable mScrollBarDrawable;
  private int mScrollBarLeftMargin;
  private int mScrollBarRightMargin;
  int mScrollBarTopMargin = 0;
  private boolean mScrollEnabled = true;
  private int mScrollFinishPosition = Integer.MAX_VALUE;
  private int mScrollPointerId = -1;
  private int mScrollState = 0;
  protected ArrayList<MttScrollViewListener> mScrollViewListeners;
  QBScroller mScroller;
  private boolean mShouldCorrectOffset = true;
  private int mSrolllBarHeight;
  int mSrolllBarLeftOffset;
  private int mSrolllBarMargin;
  int mSrolllBarWidth;
  private Rect mTempRect = new Rect();
  int mTotalLength = 0;
  byte mTouchMode;
  private boolean mTouchOnScrollView;
  private TouchOnScrollViewListener mTouchOnScrollViewListener;
  private int mTouchSlop;
  boolean mUpDragOutSizeEnable = false;
  int mUpOffset = 0;
  protected VelocityTracker mVelocityTracker;
  private onRefreshListener refreshListener = null;
  
  public QBScrollView(Context paramContext)
  {
    super(paramContext);
    ViewConfiguration localViewConfiguration = ViewConfiguration.get(paramContext);
    this.mTouchSlop = localViewConfiguration.getScaledTouchSlop();
    this.mMinimumVelocity = localViewConfiguration.getScaledMinimumFlingVelocity();
    this.mScroller = new QBScroller(paramContext);
    this.mSrolllBarHeight = UIResourceDimen.dimen.uifw_control_scrollbar_height;
    this.mSrolllBarMargin = UIResourceDimen.dimen.uifw_control_scrollbar_left_offset;
    this.mScrollBarAlpha = 0;
    setOrientation((byte)1);
    this.mSrolllBarWidth = UIResourceDimen.dimen.uifw_control_scrollbar_width;
    this.mSrolllBarLeftOffset = UIResourceDimen.dimen.uifw_control_scrollbar_left_offset;
    initHideScrollBarHandler();
    enableSpringback(true);
  }
  
  private boolean canScrollHorizontally()
  {
    return this.mOrientation == 0;
  }
  
  private void cancelTouch()
  {
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    if (localVelocityTracker != null) {
      localVelocityTracker.clear();
    }
    this.mIsUnableToDrag = false;
    setScrollState(0);
  }
  
  private void checkFooterAppeared()
  {
    int i = this.mOffset;
    if (i != 0)
    {
      int j = getViewLength();
      int k = this.mTotalLength;
      if ((i < j - k) && (k > getViewLength()))
      {
        onRefreshListener localonRefreshListener = this.refreshListener;
        if ((localonRefreshListener != null) && (!this.mFooterAppearedReported))
        {
          localonRefreshListener.onFooterAppeared();
          this.mFooterAppearedReported = true;
        }
      }
    }
  }
  
  private void drawScrollBarHorizontal(Canvas paramCanvas)
  {
    int i = getViewLength();
    int j = this.mTotalLength;
    if ((j > i) && (this.mScrollBarDrawable != null))
    {
      int k = i - this.mScrollBarLeftMargin - this.mScrollBarRightMargin;
      j = i * k / j;
      i = j;
      if (j < 10) {
        i = 10;
      }
      j = (k - i) * this.mOffset / (getViewLength() - this.mTotalLength) + getScrollX() + this.mScrollBarLeftMargin;
      int m = getHeight();
      k = this.mSrolllBarHeight;
      m = m - k - this.mSrolllBarMargin;
      this.mScrollBarDrawable.setBounds(j, m, i + j, k + m);
      this.mScrollBarDrawable.setAlpha(this.mScrollBarAlpha);
      this.mScrollBarDrawable.draw(paramCanvas);
      this.mScrollBarDrawable.setAlpha(255);
    }
  }
  
  private void drawScrollBarVertical(Canvas paramCanvas)
  {
    if ((this.mScrollBarDrawable != null) && (this.mTotalLength > getViewLength()))
    {
      int m = getViewLength() - this.mScrollBarTopMargin - this.mScrollBarBottomMargin;
      int j = getViewLength() * m / this.mTotalLength;
      int k = SCROLL_BAR_MIN_HEIGHT;
      int i = j;
      if (j < k) {
        i = k;
      }
      k = (m - i) * this.mOffset / (getViewLength() - this.mTotalLength);
      m = getScrollY();
      int n = getWidth();
      j = this.mSrolllBarWidth;
      n = n - j - this.mSrolllBarLeftOffset;
      k = k + m + this.mScrollBarTopMargin;
      this.mScrollBarDrawable.setBounds(n, k, j + n, i + k);
      this.mScrollBarDrawable.setAlpha(this.mScrollBarAlpha);
      this.mScrollBarDrawable.draw(paramCanvas);
      this.mScrollBarDrawable.setAlpha(255);
    }
  }
  
  private int getViewLength()
  {
    if (canScrollHorizontally()) {
      return getWidth();
    }
    return getHeight();
  }
  
  private void onPointerUp(MotionEvent paramMotionEvent)
  {
    int i = paramMotionEvent.getActionIndex();
    if (paramMotionEvent.getPointerId(i) == this.mScrollPointerId)
    {
      if (i == 0) {
        i = 1;
      } else {
        i = 0;
      }
      this.mScrollPointerId = paramMotionEvent.getPointerId(i);
      int j = (int)(paramMotionEvent.getX(i) + 0.5F);
      this.mLastTouchX = j;
      this.mInitialTouchX = j;
      i = (int)(paramMotionEvent.getY(i) + 0.5F);
      this.mLastTouchY = i;
      this.mInitialTouchY = i;
    }
  }
  
  private void onScrollEnd()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("scroll end-->offsetY=");
    ((StringBuilder)localObject).append(this.mOffset);
    ((StringBuilder)localObject).toString();
    this.mResetPos = false;
    hideScrollBar();
    int i = this.mOffset;
    if (i > 0)
    {
      scrollTo(0, Integer.MAX_VALUE);
      return;
    }
    if ((i != 0) && (i < getViewLength() - this.mTotalLength))
    {
      scrollTo(Math.min(0, getViewLength() - this.mTotalLength), Integer.MAX_VALUE);
      return;
    }
    cancelTouch();
    localObject = this.mScrollViewListeners;
    if ((localObject != null) && (((ArrayList)localObject).size() > 0))
    {
      localObject = this.mScrollViewListeners.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((MttScrollViewListener)((Iterator)localObject).next()).onRealScrollEnd();
      }
    }
  }
  
  private void reportFinishState() {}
  
  private void setScrollState(int paramInt)
  {
    if (paramInt == this.mScrollState) {
      return;
    }
    Object localObject = this.mScrollViewListeners;
    if (localObject != null)
    {
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((MttScrollViewListener)((Iterator)localObject).next()).onScrollStateChanged(this.mScrollState, paramInt);
      }
    }
    this.mScrollState = paramInt;
    if (paramInt != 2)
    {
      this.mHasStartFling = false;
      forceFinishedScroll();
    }
  }
  
  private void showFocusChild(View paramView)
  {
    if (paramView == null) {
      return;
    }
    Rect localRect = new Rect();
    paramView.getFocusedRect(localRect);
    offsetDescendantRectToMyCoords(paramView, localRect);
    int j = 0;
    int i = 0;
    if (!canScrollHorizontally())
    {
      if (-getScrollY() + localRect.top - paramView.getHeight() < 0) {
        i = -(-getScrollY() + localRect.top) + paramView.getHeight();
      } else if (-getScrollY() + localRect.bottom + paramView.getHeight() > getHeight()) {
        i = -(-getScrollY() + localRect.bottom - getHeight() + paramView.getHeight());
      }
      if ((i != 0) && (getTotalLength() > getViewLength())) {
        scrollby(0, 0, -i, false, true);
      }
    }
    else
    {
      if (-getScrollX() + localRect.right + paramView.getWidth() > getWidth())
      {
        i = -(-getScrollX() + localRect.right - getWidth() + paramView.getWidth());
      }
      else
      {
        i = j;
        if (-getScrollX() + localRect.left - paramView.getWidth() < 0) {
          i = -(-getScrollX() + localRect.left) + paramView.getWidth();
        }
      }
      if ((i != 0) && (getTotalLength() > getViewLength())) {
        scrollby(0, 0, -i, false, true);
      }
    }
  }
  
  public void addScrollViewListener(MttScrollViewListener paramMttScrollViewListener)
  {
    if (this.mScrollViewListeners == null) {
      this.mScrollViewListeners = new ArrayList();
    }
    this.mScrollViewListeners.add(paramMttScrollViewListener);
  }
  
  public boolean canScroll(int paramInt)
  {
    if (this.mTotalLength >= getViewLength())
    {
      int i = this.mOffset;
      if ((i - paramInt <= 0) && (i - paramInt >= getViewLength() - this.mTotalLength)) {
        return true;
      }
    }
    return false;
  }
  
  protected boolean canScrollVertically()
  {
    return this.mOrientation == 1;
  }
  
  protected int computeHorizontalScrollRange()
  {
    if (this.mOrientation == 1) {
      return super.computeHorizontalScrollRange();
    }
    int j = getChildCount();
    int i = getWidth() - getPaddingLeft() - getPaddingRight();
    if (j == 0) {
      return i;
    }
    j = getChildAt(0).getRight();
    int k = -this.mOffset;
    int m = Math.max(0, j - i);
    if (k < 0) {
      return j - k;
    }
    i = j;
    if (k > m) {
      i = j + (k - m);
    }
    return i;
  }
  
  public void computeScroll()
  {
    if (this.mScroller.computeScrollOffset())
    {
      int i;
      if (canScrollVertically()) {
        i = this.mScroller.getCurrY();
      } else {
        i = this.mScroller.getCurrX();
      }
      int j = this.mLastOffset;
      this.mLastOffset = i;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("computescroll currOffset=");
      localStringBuilder.append(i);
      localStringBuilder.toString();
      checkFooterAppeared();
      scrollby(0, 0, j - i, false);
      ViewCompat.postInvalidateOnAnimation(this);
      return;
    }
    if (this.mHasStartFling)
    {
      this.mHasStartFling = false;
      onScrollEnd();
    }
  }
  
  protected int computeVerticalScrollRange()
  {
    if (this.mOrientation == 0) {
      return super.computeVerticalScrollRange();
    }
    int j = getChildCount();
    int i = getHeight() - getPaddingBottom() - getPaddingTop();
    if (j == 0) {
      return i;
    }
    j = this.mTotalLength;
    int k = -this.mOffset;
    int m = Math.max(0, j - i);
    if (k < 0) {
      return j - k;
    }
    i = j;
    if (k > m) {
      i = j + (k - m);
    }
    return i;
  }
  
  protected void correctOffsetY()
  {
    if ((this.mOffset <= 0) && (getViewLength() <= this.mTotalLength))
    {
      if (this.mOffset < getViewLength() - this.mTotalLength) {
        this.mOffset = (getViewLength() - this.mTotalLength);
      }
    }
    else {
      this.mOffset = 0;
    }
    if (canScrollHorizontally())
    {
      super.scrollTo(-this.mOffset, getScrollY());
      return;
    }
    super.scrollTo(getScrollX(), -this.mOffset);
  }
  
  public void disableFling()
  {
    this.mFlingDisabled = true;
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    if (this.mNeedScrollbar) {
      drawScrollBar(paramCanvas);
    }
  }
  
  void drawScrollBar(Canvas paramCanvas)
  {
    if (this.mOrientation == 1)
    {
      drawScrollBarVertical(paramCanvas);
      return;
    }
    drawScrollBarHorizontal(paramCanvas);
  }
  
  public void enableSpringback(boolean paramBoolean)
  {
    int j = 0;
    if (paramBoolean) {
      i = UIResourceDimen.dimen.uifw_recycler_springback_max_distance;
    } else {
      i = 0;
    }
    this.mUpOffset = i;
    int i = j;
    if (paramBoolean) {
      i = UIResourceDimen.dimen.uifw_recycler_springback_max_distance;
    }
    this.mDownOffset = i;
  }
  
  public void finishRefreshFooter()
  {
    this.mFooterAppearedReported = false;
  }
  
  public void fling(int paramInt)
  {
    int j = Math.abs(paramInt);
    int k = this.mMinimumVelocity;
    int i = 0;
    if ((j > k) && (!this.mFlingDisabled))
    {
      if (!this.mScroller.isFinished())
      {
        reportFinishState();
        this.mScroller.abortAnimation();
      }
      this.mResetPos = false;
      if (paramInt < 0) {
        i = Integer.MAX_VALUE;
      }
      this.mLastOffset = i;
      this.mHasStartFling = true;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("start fling!!!offsetY=");
      localStringBuilder.append(this.mOffset);
      localStringBuilder.append(",velocity=");
      localStringBuilder.append(paramInt);
      localStringBuilder.append(",minimumvelocity=");
      localStringBuilder.append(this.mMinimumVelocity);
      localStringBuilder.toString();
      setScrollState(2);
      if (canScrollVertically()) {
        this.mScroller.fling(0, i, 0, paramInt, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
      } else {
        this.mScroller.fling(i, 0, paramInt, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
      }
      invalidate();
      return;
    }
    setScrollState(0);
    hideScrollBar();
  }
  
  public void forceFinishedScroll()
  {
    QBScroller localQBScroller = this.mScroller;
    if (localQBScroller != null)
    {
      localQBScroller.forceFinished(true);
      reportFinishState();
    }
  }
  
  public float getCurrVelocity()
  {
    return this.mScroller.getCurrVelocity();
  }
  
  public int getOffset()
  {
    return this.mOffset;
  }
  
  public int getTotalLength()
  {
    return this.mTotalLength;
  }
  
  void hideScrollBar()
  {
    this.mHideScrollBarAction.sendEmptyMessageDelayed(1, 500L);
  }
  
  public void hideScrollBarImmediately()
  {
    this.mHideScrollBarAction.removeMessages(1);
    this.mScrollBarAlpha = 0;
    postInvalidate();
  }
  
  void initHideScrollBarHandler()
  {
    if (this.mHideScrollBarAction == null) {
      this.mHideScrollBarAction = new Handler(Looper.getMainLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramAnonymousMessage.what != 1) {
            return;
          }
          if (QBScrollView.this.mScrollBarAlpha > 0)
          {
            paramAnonymousMessage = QBScrollView.this;
            paramAnonymousMessage.mScrollBarAlpha -= 20;
            if (QBScrollView.this.mScrollBarAlpha < 0) {
              QBScrollView.this.mScrollBarAlpha = 0;
            }
            QBScrollView.this.postInvalidate();
            QBScrollView.this.mHideScrollBarAction.sendEmptyMessage(1);
          }
        }
      };
    }
  }
  
  protected boolean isBrowserWindowGPUEnable()
  {
    return true;
  }
  
  protected boolean isDrag()
  {
    return this.mTouchMode == 1;
  }
  
  public boolean isScrollBarShowing()
  {
    return this.mScrollBarAlpha != 0;
  }
  
  public boolean isScrolling()
  {
    QBScroller localQBScroller = this.mScroller;
    if (localQBScroller != null) {
      return localQBScroller.isFinished() ^ true;
    }
    return false;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
  }
  
  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    if (paramConfiguration.orientation != this.mOrientation) {
      correctOffsetY();
    }
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    forceFinishedScroll();
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onInterceptTouchEvent-->e=");
    localStringBuilder.append(paramMotionEvent.getAction());
    localStringBuilder.toString();
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
    this.mVelocityTracker.addMovement(paramMotionEvent);
    int j = paramMotionEvent.getActionMasked();
    int i = paramMotionEvent.getActionIndex();
    boolean bool2 = false;
    if ((j != 0) && ((this.mIsUnableToDrag) || (!this.mScrollEnabled))) {
      return false;
    }
    switch (j)
    {
    case 4: 
    default: 
      break;
    case 6: 
      onPointerUp(paramMotionEvent);
      break;
    case 5: 
      this.mScrollPointerId = paramMotionEvent.getPointerId(i);
      j = (int)(paramMotionEvent.getX(i) + 0.5F);
      this.mLastTouchX = j;
      this.mInitialTouchX = j;
      i = (int)(paramMotionEvent.getY(i) + 0.5F);
      this.mLastTouchY = i;
      this.mInitialTouchY = i;
      break;
    case 3: 
      cancelTouch();
      break;
    case 2: 
      j = paramMotionEvent.findPointerIndex(this.mScrollPointerId);
      if (j < 0) {
        return false;
      }
      i = (int)(paramMotionEvent.getX(j) + 0.5F);
      j = (int)(paramMotionEvent.getY(j) + 0.5F);
      if (this.mScrollState != 1)
      {
        int n = i - this.mInitialTouchX;
        int m = j - this.mInitialTouchY;
        if (((canScrollHorizontally()) && (n != 0) && (this.mCheckDescend) && (CanScrollChecker.canScroll(this, false, false, n, i, j))) || ((canScrollVertically()) && (m != 0) && (this.mCheckDescend) && (CanScrollChecker.canScroll(this, false, true, m, i, j))))
        {
          this.mLastTouchX = i;
          this.mLastTouchY = j;
          this.mIsUnableToDrag = true;
          return false;
        }
        bool1 = canScrollHorizontally();
        int k = -1;
        if ((bool1) && (Math.abs(n) > this.mTouchSlop) && (Math.abs(n) > Math.abs(m)))
        {
          j = this.mInitialTouchX;
          int i1 = this.mTouchSlop;
          if (n < 0) {
            i = -1;
          } else {
            i = 1;
          }
          this.mLastTouchX = (j + i1 * i);
          i = 1;
        }
        else
        {
          i = 0;
        }
        j = i;
        if (canScrollVertically())
        {
          j = i;
          if (Math.abs(m) > this.mTouchSlop)
          {
            j = i;
            if (Math.abs(m) > Math.abs(n))
            {
              j = this.mInitialTouchY;
              n = this.mTouchSlop;
              if (m < 0) {
                i = k;
              } else {
                i = 1;
              }
              this.mLastTouchY = (j + n * i);
              j = 1;
            }
          }
        }
        if (j != 0)
        {
          getParent().requestDisallowInterceptTouchEvent(true);
          setScrollState(1);
          if (m < 0) {
            bool1 = true;
          } else {
            bool1 = false;
          }
          onStartScroll(bool1);
        }
      }
      break;
    case 1: 
      this.mVelocityTracker.clear();
      this.mIsUnableToDrag = false;
      break;
    case 0: 
      this.mScrollPointerId = paramMotionEvent.getPointerId(0);
      i = (int)(paramMotionEvent.getX() + 0.5F);
      this.mLastTouchX = i;
      this.mInitialTouchX = i;
      i = (int)(paramMotionEvent.getY() + 0.5F);
      this.mLastTouchY = i;
      this.mInitialTouchY = i;
      this.mIsUnableToDrag = false;
      if (this.mScrollState == 2) {
        setScrollState(1);
      }
      break;
    }
    boolean bool1 = bool2;
    if (this.mScrollState == 1) {
      bool1 = true;
    }
    return bool1;
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return super.onKeyUp(paramInt, paramKeyEvent);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int n = getChildCount();
    if (n == 0)
    {
      this.mTotalLength = 0;
      return;
    }
    int i;
    Object localObject2;
    int j;
    int k;
    if (this.mOrientation == 1)
    {
      i = getPaddingTop();
      getPaddingLeft();
      paramInt1 = i;
      if (this.mCenterVertival)
      {
        paramInt1 = 0;
        for (paramInt2 = 0; paramInt1 < n; paramInt2 = paramInt3)
        {
          localObject1 = getChildAt(paramInt1);
          paramInt3 = paramInt2;
          if (localObject1 != null)
          {
            paramInt3 = paramInt2;
            if (((View)localObject1).getVisibility() != 8)
            {
              localObject2 = ((View)localObject1).getLayoutParams();
              j = ((View)localObject1).getMeasuredHeight();
              if ((localObject2 instanceof ViewGroup.MarginLayoutParams))
              {
                localObject1 = (ViewGroup.MarginLayoutParams)localObject2;
                paramInt4 = ((ViewGroup.MarginLayoutParams)localObject1).topMargin;
                paramInt3 = ((ViewGroup.MarginLayoutParams)localObject1).bottomMargin;
              }
              else
              {
                paramInt3 = 0;
                paramInt4 = 0;
              }
              paramInt3 = paramInt2 + (paramInt4 + j + paramInt3);
            }
          }
          paramInt1 += 1;
        }
        paramInt1 = i;
        if (getHeight() - getPaddingTop() - getPaddingBottom() > paramInt2) {
          paramInt1 = (getHeight() - getPaddingTop() - getPaddingBottom() - paramInt2) / 2;
        }
      }
      paramInt3 = 0;
      paramInt2 = paramInt1;
      paramInt1 = paramInt3;
      while (paramInt1 < n)
      {
        localObject1 = getChildAt(paramInt1);
        paramInt3 = paramInt2;
        if (localObject1 != null)
        {
          paramInt3 = paramInt2;
          if (((View)localObject1).getVisibility() != 8)
          {
            localObject2 = ((View)localObject1).getLayoutParams();
            k = ((View)localObject1).getMeasuredHeight();
            if ((localObject2 instanceof ViewGroup.MarginLayoutParams))
            {
              localObject2 = (ViewGroup.MarginLayoutParams)localObject2;
              paramInt4 = ((ViewGroup.MarginLayoutParams)localObject2).leftMargin;
              i = ((ViewGroup.MarginLayoutParams)localObject2).rightMargin;
              j = ((ViewGroup.MarginLayoutParams)localObject2).topMargin;
              paramInt3 = ((ViewGroup.MarginLayoutParams)localObject2).bottomMargin;
            }
            else
            {
              paramInt3 = 0;
              paramInt4 = 0;
              i = 0;
              j = 0;
            }
            paramInt2 += j;
            paramInt4 = (getWidth() - paramInt4 - i - ((View)localObject1).getMeasuredWidth()) / 2 + paramInt4;
            ((View)localObject1).layout(paramInt4, paramInt2, ((View)localObject1).getMeasuredWidth() + paramInt4, paramInt2 + k);
            paramInt3 = paramInt2 + (k + paramInt3);
          }
        }
        paramInt1 += 1;
        paramInt2 = paramInt3;
      }
      paramInt1 = paramInt2 + getPaddingBottom();
    }
    else
    {
      paramInt4 = getPaddingLeft();
      paramInt1 = getPaddingTop();
      paramInt3 = 0;
      while (paramInt3 < n)
      {
        localObject1 = getChildAt(paramInt3);
        paramInt2 = paramInt1;
        i = paramInt4;
        if (localObject1 != null)
        {
          paramInt2 = paramInt1;
          i = paramInt4;
          if (((View)localObject1).getVisibility() != 8)
          {
            localObject2 = ((View)localObject1).getLayoutParams();
            int i1 = ((View)localObject1).getMeasuredWidth();
            int m;
            if ((localObject2 instanceof ViewGroup.MarginLayoutParams))
            {
              ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localObject2;
              k = localMarginLayoutParams.leftMargin;
              j = localMarginLayoutParams.topMargin;
              i = localMarginLayoutParams.rightMargin;
              m = localMarginLayoutParams.bottomMargin;
            }
            else
            {
              m = 0;
              k = 0;
              j = 0;
              i = 0;
            }
            paramInt2 = paramInt1;
            if ((localObject2 instanceof FrameLayout.LayoutParams))
            {
              localObject2 = (FrameLayout.LayoutParams)localObject2;
              if (((FrameLayout.LayoutParams)localObject2).gravity != 17)
              {
                paramInt2 = paramInt1;
                if (((FrameLayout.LayoutParams)localObject2).gravity != 16) {}
              }
              else
              {
                paramInt2 = (getMeasuredHeight() - ((View)localObject1).getMeasuredHeight() - getPaddingTop() - getPaddingBottom() - j - m) / 2;
              }
            }
            paramInt1 = paramInt4 + k;
            paramInt4 = j + paramInt2;
            ((View)localObject1).layout(paramInt1, paramInt4, ((View)localObject1).getMeasuredWidth() + paramInt1, ((View)localObject1).getMeasuredHeight() + paramInt4);
            i = paramInt1 + (i1 + i);
          }
        }
        paramInt3 += 1;
        paramInt1 = paramInt2;
        paramInt4 = i;
      }
      paramInt1 = paramInt4 + getPaddingRight();
    }
    this.mTotalLength = paramInt1;
    if (this.mShouldCorrectOffset) {
      correctOffsetY();
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("mOffset=");
    ((StringBuilder)localObject1).append(this.mOffset);
    ((StringBuilder)localObject1).toString();
    scrollTo(this.mOffset);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int n = getChildCount();
    int i7 = View.MeasureSpec.getSize(paramInt1);
    int i8 = View.MeasureSpec.getSize(paramInt2);
    int i1 = 0;
    int j = 0;
    int i = 0;
    while (i1 < n)
    {
      localObject = getChildAt(i1);
      if (((View)localObject).getVisibility() != 8)
      {
        ViewGroup.LayoutParams localLayoutParams = ((View)localObject).getLayoutParams();
        if (localLayoutParams.width == -2) {
          k = 1;
        } else {
          k = 0;
        }
        int i3;
        if (localLayoutParams.height == -2) {
          i3 = 1;
        } else {
          i3 = 0;
        }
        int i4;
        int i5;
        int i6;
        int i2;
        if ((((View)localObject).getLayoutParams() instanceof ViewGroup.MarginLayoutParams))
        {
          ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localLayoutParams;
          i4 = localMarginLayoutParams.bottomMargin;
          i5 = localMarginLayoutParams.leftMargin;
          i6 = localMarginLayoutParams.topMargin;
          i2 = localMarginLayoutParams.rightMargin;
          if (k != 0) {
            k = View.MeasureSpec.makeMeasureSpec(i7, 0);
          } else {
            k = getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight() + localMarginLayoutParams.leftMargin + localMarginLayoutParams.rightMargin, localLayoutParams.width);
          }
          if (i3 != 0) {
            m = View.MeasureSpec.makeMeasureSpec(i8, 0);
          } else {
            m = getChildMeasureSpec(paramInt2, getPaddingTop() + getPaddingBottom() + localMarginLayoutParams.topMargin + localMarginLayoutParams.bottomMargin, localMarginLayoutParams.height);
          }
          i3 = i5;
          i5 = k;
        }
        else
        {
          if (k != 0) {
            m = View.MeasureSpec.makeMeasureSpec(i7, 0);
          } else {
            m = getChildMeasureSpec(paramInt1, getPaddingLeft() + getPaddingRight(), localLayoutParams.width);
          }
          if (i3 != 0) {
            k = View.MeasureSpec.makeMeasureSpec(i8, 0);
          } else {
            k = getChildMeasureSpec(paramInt2, getPaddingTop() + getPaddingBottom(), localLayoutParams.height);
          }
          i3 = 0;
          i4 = 0;
          i6 = 0;
          i2 = 0;
          i5 = m;
          m = k;
        }
        ((View)localObject).measure(i5, m);
        if (this.mOrientation == 1)
        {
          j += ((View)localObject).getMeasuredHeight() + i6 + i4;
          i = Math.max(i, ((View)localObject).getMeasuredWidth() + i3 + i2);
        }
        else
        {
          j = Math.max(j, ((View)localObject).getMeasuredHeight() + i6 + i4);
          i += ((View)localObject).getMeasuredWidth() + i3 + i2;
        }
      }
      i1 += 1;
    }
    Object localObject = getLayoutParams();
    int k = j;
    int m = i;
    if (this.mMaxLength != -1) {
      if (((ViewGroup.LayoutParams)localObject).width != -2)
      {
        k = j;
        m = i;
        if (((ViewGroup.LayoutParams)localObject).height != -2) {}
      }
      else if ((this.mOrientation == 1) && (((ViewGroup.LayoutParams)localObject).height == -2))
      {
        k = Math.min(j, this.mMaxLength);
        m = i;
      }
      else
      {
        k = j;
        m = i;
        if (this.mOrientation == 0)
        {
          k = j;
          m = i;
          if (((ViewGroup.LayoutParams)localObject).width == -2)
          {
            m = Math.min(i, this.mMaxLength);
            k = j;
          }
        }
      }
    }
    setMeasuredDimension(resolveSize(m, paramInt1), resolveSize(k, paramInt2));
  }
  
  protected void onStartScroll(boolean paramBoolean) {}
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onTouchEvent-->e=");
    localStringBuilder.append(paramMotionEvent.getAction());
    localStringBuilder.toString();
    int j = paramMotionEvent.getActionMasked();
    int i = paramMotionEvent.getActionIndex();
    if ((j != 0) && (!this.mScrollEnabled)) {
      return false;
    }
    if (this.mVelocityTracker == null) {
      this.mVelocityTracker = VelocityTracker.obtain();
    }
    this.mVelocityTracker.addMovement(paramMotionEvent);
    switch (j)
    {
    case 4: 
    default: 
      return true;
    case 6: 
      onPointerUp(paramMotionEvent);
      return true;
    case 5: 
      this.mScrollPointerId = paramMotionEvent.getPointerId(i);
      j = (int)(paramMotionEvent.getX(i) + 0.5F);
      this.mLastTouchX = j;
      this.mInitialTouchX = j;
      i = (int)(paramMotionEvent.getY(i) + 0.5F);
      this.mLastTouchY = i;
      this.mInitialTouchY = i;
      return true;
    case 3: 
      upAction();
      return true;
    case 2: 
      i = paramMotionEvent.findPointerIndex(this.mScrollPointerId);
      if (i < 0) {
        return false;
      }
      int m = (int)(paramMotionEvent.getX(i) + 0.5F);
      int n = (int)(paramMotionEvent.getY(i) + 0.5F);
      if (this.mScrollState != 1)
      {
        i = m - this.mInitialTouchX;
        int i1 = n - this.mInitialTouchY;
        boolean bool = canScrollHorizontally();
        int k = -1;
        int i2;
        if (bool)
        {
          i2 = Math.abs(i);
          j = this.mTouchSlop;
          if (i2 > j)
          {
            i2 = this.mInitialTouchX;
            if (i < 0) {
              i = -1;
            } else {
              i = 1;
            }
            this.mLastTouchX = (i2 + j * i);
            i = 1;
            break label321;
          }
        }
        i = 0;
        j = i;
        if (canScrollVertically())
        {
          int i3 = Math.abs(i1);
          i2 = this.mTouchSlop;
          j = i;
          if (i3 > i2)
          {
            j = this.mInitialTouchY;
            if (i1 < 0) {
              i = k;
            } else {
              i = 1;
            }
            this.mLastTouchY = (j + i2 * i);
            j = 1;
          }
        }
        if (j != 0)
        {
          if (i1 < 0) {
            bool = true;
          } else {
            bool = false;
          }
          onStartScroll(bool);
          this.mTouchOnScrollView = false;
          if (getParent() != null) {
            getParent().requestDisallowInterceptTouchEvent(true);
          }
          setScrollState(1);
        }
      }
      if (this.mScrollState == 1)
      {
        j = this.mLastTouchX;
        i = this.mLastTouchY;
        paramMotionEvent = new StringBuilder();
        paramMotionEvent.append("ontouchevent drag->");
        i = -(n - i);
        paramMotionEvent.append(i);
        paramMotionEvent.append(",offsetY=");
        paramMotionEvent.append(this.mOffset);
        paramMotionEvent.toString();
        if (canScrollVertically())
        {
          showScrollBar();
          scrollby(m, n, i, true);
        }
        else
        {
          showScrollBar();
          i = -(m - j);
          scrollby(m, n, i, true);
        }
        paramMotionEvent = this.mScrollViewListeners;
        if ((paramMotionEvent != null) && (paramMotionEvent.size() > 0))
        {
          paramMotionEvent = this.mScrollViewListeners.iterator();
          while (paramMotionEvent.hasNext()) {
            ((MttScrollViewListener)paramMotionEvent.next()).onRawScroll(i);
          }
        }
      }
      this.mLastTouchX = m;
      this.mLastTouchY = n;
      return true;
    case 1: 
      label321:
      if (this.mTouchOnScrollView)
      {
        paramMotionEvent = this.mTouchOnScrollViewListener;
        if (paramMotionEvent != null) {
          paramMotionEvent.onTouchOnScrollView();
        }
      }
      this.mTouchOnScrollView = false;
      upAction();
      return true;
    }
    this.mScrollPointerId = paramMotionEvent.getPointerId(0);
    i = (int)(paramMotionEvent.getX() + 0.5F);
    this.mLastTouchX = i;
    this.mInitialTouchX = i;
    i = (int)(paramMotionEvent.getY() + 0.5F);
    this.mLastTouchY = i;
    this.mInitialTouchY = i;
    this.mTouchOnScrollView = true;
    return true;
  }
  
  public void requestChildFocus(View paramView1, View paramView2)
  {
    super.requestChildFocus(paramView1, paramView2);
    showFocusChild(paramView2);
  }
  
  public boolean requestFocus(int paramInt, Rect paramRect)
  {
    return super.requestFocus(paramInt, paramRect);
  }
  
  public void requestLayout()
  {
    super.requestLayout();
  }
  
  public void scrollTo(int paramInt)
  {
    scrollTo(paramInt, -1);
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    int i = this.mOffset;
    int j = i - paramInt1;
    if (paramInt2 <= 0)
    {
      updateStartControlIndex(j);
      this.mOffset -= j;
      invalidate();
      Object localObject = this.mScrollViewListeners;
      if ((localObject != null) && (((ArrayList)localObject).size() > 0))
      {
        localObject = this.mScrollViewListeners.iterator();
        while (((Iterator)localObject).hasNext()) {
          ((MttScrollViewListener)((Iterator)localObject).next()).onRealScroll(this.mOffset);
        }
      }
    }
    else if (-paramInt1 != i)
    {
      this.mResetPos = true;
      this.mLastOffset = i;
      this.mHasStartFling = true;
      setScrollState(2);
      if (canScrollVertically()) {
        this.mScroller.startScroll(0, this.mOffset, 0, -j, paramInt2);
      } else {
        this.mScroller.startScroll(this.mOffset, 0, -j, 0, paramInt2);
      }
      ViewCompat.postInvalidateOnAnimation(this);
    }
  }
  
  protected void scrollby(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    scrollby(paramInt1, paramInt2, paramInt3, paramBoolean, false);
  }
  
  protected void scrollby(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!paramBoolean1) && (!this.mResetPos))
    {
      paramInt1 = this.mUpOffset;
      boolean bool = this.mUpDragOutSizeEnable;
      int i = 0;
      if (!bool) {
        paramInt1 = 0;
      }
      paramInt2 = this.mDownOffset;
      if (!this.mDownDragOutSizeEnable) {
        paramInt2 = 0;
      }
      if (this.mTotalLength > getViewLength()) {
        i = getViewLength() - this.mTotalLength;
      }
      int j = this.mOffset;
      if (j - paramInt3 > paramInt1)
      {
        if (!this.mScroller.isFinished()) {
          this.mScroller.forceFinished(true);
        }
        paramInt1 = this.mOffset - paramInt1;
      }
      else
      {
        paramInt2 = i - paramInt2;
        paramInt1 = paramInt3;
        if (j - paramInt3 < paramInt2)
        {
          if (!this.mScroller.isFinished()) {
            this.mScroller.forceFinished(true);
          }
          paramInt1 = this.mOffset - paramInt2;
        }
      }
    }
    else
    {
      paramInt2 = paramInt3;
      if (!this.mUpDragOutSizeEnable)
      {
        paramInt1 = this.mOffset;
        paramInt2 = paramInt3;
        if (paramInt1 - paramInt3 > 0) {
          paramInt2 = paramInt1;
        }
      }
      paramInt1 = paramInt2;
      if (!this.mDownDragOutSizeEnable)
      {
        if (getViewLength() >= this.mTotalLength) {
          return;
        }
        paramInt1 = paramInt2;
        if (this.mOffset - paramInt2 < getViewLength() - this.mTotalLength) {
          paramInt1 = this.mOffset - (getViewLength() - this.mTotalLength);
        }
      }
    }
    paramInt2 = paramInt1;
    if (paramBoolean2)
    {
      paramInt3 = this.mOffset;
      if (paramInt3 - paramInt1 > 0)
      {
        paramInt2 = paramInt3;
      }
      else
      {
        paramInt2 = paramInt1;
        if (paramInt3 - paramInt1 < getViewLength() - this.mTotalLength) {
          paramInt2 = this.mOffset - (getViewLength() - this.mTotalLength);
        }
      }
    }
    if (Math.abs(paramInt2) > 0)
    {
      if (paramBoolean1)
      {
        paramInt1 = this.mOffset;
        if ((paramInt1 > 0) || (paramInt1 < getViewLength() - this.mTotalLength))
        {
          paramInt1 = paramInt2 / 3;
          updateStartControlIndex(paramInt1);
          this.mOffset -= paramInt1;
          break label363;
        }
      }
      updateStartControlIndex(paramInt2);
      this.mOffset -= paramInt2;
    }
    label363:
    invalidate();
    Object localObject = this.mScrollViewListeners;
    if ((localObject != null) && (((ArrayList)localObject).size() > 0))
    {
      localObject = this.mScrollViewListeners.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((MttScrollViewListener)((Iterator)localObject).next()).onRealScroll(this.mOffset);
      }
    }
  }
  
  public void setCenterVertival(boolean paramBoolean)
  {
    this.mCenterVertival = paramBoolean;
  }
  
  public void setDownDragOutSizeEnable(boolean paramBoolean)
  {
    this.mDownDragOutSizeEnable = paramBoolean;
  }
  
  public void setMaxheight(int paramInt)
  {
    this.mMaxLength = paramInt;
  }
  
  public void setNeedScrollbar(boolean paramBoolean)
  {
    this.mNeedScrollbar = paramBoolean;
  }
  
  public void setOffset(int paramInt)
  {
    this.mOffset = paramInt;
  }
  
  public void setOrientation(byte paramByte) {}
  
  public void setRefreshListener(onRefreshListener paramonRefreshListener)
  {
    this.refreshListener = paramonRefreshListener;
  }
  
  public void setRefreshType(int paramInt) {}
  
  public void setScrollEnabled(boolean paramBoolean)
  {
    this.mScrollEnabled = paramBoolean;
  }
  
  public void setScrollbarDrawableColor(int paramInt)
  {
    this.mMaxAlpha = 255;
  }
  
  public void setShouldCorrectOffset(boolean paramBoolean)
  {
    this.mShouldCorrectOffset = paramBoolean;
  }
  
  public void setTotalHeight(int paramInt)
  {
    this.mTotalLength = paramInt;
  }
  
  public void setTouchOnScrollViewListener(TouchOnScrollViewListener paramTouchOnScrollViewListener)
  {
    this.mTouchOnScrollViewListener = paramTouchOnScrollViewListener;
  }
  
  public void setUpDragOutSizeEnable(boolean paramBoolean)
  {
    this.mUpDragOutSizeEnable = paramBoolean;
  }
  
  public boolean shouleCorrectOffset()
  {
    return this.mShouldCorrectOffset;
  }
  
  void showScrollBar()
  {
    this.mHideScrollBarAction.removeMessages(1);
    this.mScrollBarAlpha = this.mMaxAlpha;
  }
  
  public final void smoothScrollBy(int paramInt)
  {
    if (getChildCount() == 0) {
      return;
    }
    long l = AnimationUtils.currentAnimationTimeMillis() - this.mLastScroll;
    int i;
    int j;
    int k;
    if (canScrollHorizontally())
    {
      StringBuilder localStringBuilder;
      if (l > 250L)
      {
        i = getWidth();
        j = getPaddingRight();
        k = getPaddingLeft();
        j = Math.max(0, this.mTotalLength - (i - j - k));
        k = this.mOffset;
        i = -k;
        this.mLastOffset = k;
        paramInt = Math.max(0, Math.min(paramInt + i, j)) - i;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("smoothscrollBy delta->");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(",scrollX->");
        localStringBuilder.append(i);
        localStringBuilder.toString();
        this.mScroller.startScroll(this.mOffset, getScrollY(), -paramInt, 0, Integer.MAX_VALUE);
        ViewCompat.postInvalidateOnAnimation(this);
      }
      else
      {
        if (!this.mScroller.isFinished()) {
          this.mScroller.abortAnimation();
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("scroll too frequently,offsetY=");
        localStringBuilder.append(this.mOffset);
        localStringBuilder.append(",delta=");
        localStringBuilder.append(paramInt);
        localStringBuilder.toString();
        scrollby(0, 0, paramInt, false);
      }
    }
    else if (l > 250L)
    {
      i = getHeight();
      j = getPaddingTop();
      k = getPaddingBottom();
      j = Math.max(0, this.mTotalLength - (i - j - k));
      k = this.mOffset;
      i = -k;
      this.mLastOffset = k;
      paramInt = Math.max(0, Math.min(paramInt + i, j));
      this.mScroller.startScroll(this.mOffset, i, 0, -(paramInt - i), Integer.MAX_VALUE);
      ViewCompat.postInvalidateOnAnimation(this);
    }
    else
    {
      if (!this.mScroller.isFinished()) {
        this.mScroller.abortAnimation();
      }
      scrollby(0, 0, paramInt, false);
    }
    this.mLastScroll = AnimationUtils.currentAnimationTimeMillis();
  }
  
  public final void smoothScrollTo(int paramInt)
  {
    smoothScrollBy(paramInt - this.mOffset);
  }
  
  public void upAction()
  {
    int i = this.mOffset;
    if ((i != 0) && ((i > 0) || (getViewLength() > this.mTotalLength)))
    {
      setScrollState(2);
      scrollTo(0, Integer.MAX_VALUE);
      return;
    }
    i = this.mOffset;
    if ((i != 0) && (i < getViewLength() - this.mTotalLength))
    {
      setScrollState(2);
      scrollTo(getViewLength() - this.mTotalLength, Integer.MAX_VALUE);
      return;
    }
    VelocityTracker localVelocityTracker = this.mVelocityTracker;
    localVelocityTracker.computeCurrentVelocity(1000);
    float f;
    if (canScrollVertically()) {
      f = localVelocityTracker.getYVelocity();
    } else {
      f = localVelocityTracker.getXVelocity();
    }
    fling((int)f);
  }
  
  protected void updateStartControlIndex(int paramInt)
  {
    if (paramInt == 0) {
      return;
    }
    checkFooterAppeared();
    if (canScrollVertically())
    {
      super.scrollTo(0, getScrollY() + paramInt);
      return;
    }
    super.scrollTo(getScrollX() + paramInt, 0);
  }
  
  public static abstract interface MttScrollViewListener
  {
    public abstract void onRawScroll(int paramInt);
    
    public abstract void onRealScroll(int paramInt);
    
    public abstract void onRealScrollEnd();
    
    public abstract void onScrollStateChanged(int paramInt1, int paramInt2);
  }
  
  public static abstract interface TouchOnScrollViewListener
  {
    public abstract void onTouchOnScrollView();
  }
  
  public static abstract interface onRefreshListener
  {
    public abstract void onFooterAppeared();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\reader\QBScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */