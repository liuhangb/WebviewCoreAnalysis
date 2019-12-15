package org.chromium.tencent.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Region.Op;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.content_public.browser.WebContents;
import org.chromium.tencent.TencentAwContent;
import org.chromium.ui.base.EventForwarder;

public class OverScrollBrand
{
  private static final String TAG = "OverScrollBrand";
  private static final boolean TRACE = false;
  private AwContents mAwContents;
  private MotionEvent mBackupReleaseEventInScrollMode;
  private boolean mEnableOverScrollBrand = false;
  private View mHostView;
  private boolean mInOverScrollMode;
  private float mLastTouchY = 0.0F;
  private Drawable mOverScrollBackgroundDrawable;
  private Paint mOverScrollBackgroundPaint;
  private Drawable mOverScrollBorderTopDrawable;
  private boolean mPreAddressBarVisibleState = false;
  private AwScrollOffsetManager mScrollOffsetManager;
  private int mScrollYWhenTapDown = 0;
  private OverScroller mScroller;
  
  public OverScrollBrand(AwContents paramAwContents, View paramView, Context paramContext)
  {
    this.mAwContents = paramAwContents;
    this.mHostView = paramView;
    this.mScroller = new OverScroller(paramContext, new DefaultInterpolator(null), true);
  }
  
  private void LOG(String paramString) {}
  
  private void drawOverScrollBackground(Canvas paramCanvas, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    if (this.mHostView != null)
    {
      if (this.mAwContents == null) {
        return;
      }
      if (this.mOverScrollBackgroundDrawable != null)
      {
        int i = paramCanvas.save();
        paramCanvas.translate(paramInt1, paramInt2);
        paramCanvas.clipRect(-paramInt1, paramInt3 - paramInt2, paramInt4 - paramInt1, paramInt5 - paramInt2, Region.Op.DIFFERENCE);
        Object localObject = this.mOverScrollBackgroundDrawable;
        if ((localObject instanceof BitmapDrawable))
        {
          localObject = ((BitmapDrawable)localObject).getBitmap();
          if (this.mOverScrollBackgroundPaint == null)
          {
            this.mOverScrollBackgroundPaint = new Paint();
            this.mOverScrollBackgroundPaint.setShader(new BitmapShader((Bitmap)localObject, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
          }
          paramCanvas.drawPaint(this.mOverScrollBackgroundPaint);
        }
        else
        {
          ((Drawable)localObject).setBounds(paramCanvas.getClipBounds());
          this.mOverScrollBackgroundDrawable.draw(paramCanvas);
        }
        paramCanvas.restoreToCount(i);
      }
      return;
    }
  }
  
  private boolean drawTopBrand(Canvas paramCanvas)
  {
    int i = this.mHostView.getScrollY();
    if ((i < 0) && (this.mHostView != null))
    {
      if (this.mAwContents == null) {
        return false;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("drawTopBrand offsetY:");
      ((StringBuilder)localObject).append(i);
      LOG(((StringBuilder)localObject).toString());
      int j = (int)this.mAwContents.getContentViewCore().getRenderCoordinates().getContentOffsetYPix();
      int k = this.mAwContents.computeHorizontalScrollRange();
      int m = j + Math.max(j, this.mAwContents.computeVerticalScrollRange());
      int n = this.mHostView.getScrollX();
      int i1 = paramCanvas.save();
      drawOverScrollBackground(paramCanvas, n, i, j, k, m);
      localObject = this.mAwContents;
      boolean bool;
      if ((localObject instanceof TencentAwContent)) {
        bool = ((TencentAwContent)localObject).isRenderToNativeSurface();
      } else {
        bool = false;
      }
      if (bool) {
        paramCanvas.translate(0.0F, i);
      }
      localObject = this.mAwContents;
      if ((localObject != null) && (((AwContents)localObject).getContentViewCore() != null)) {
        paramCanvas.clipRect(-n, j - i, k - n, m - i, Region.Op.DIFFERENCE);
      }
      if (bool) {
        paramCanvas.translate(0.0F, -i);
      }
      if (this.mOverScrollBorderTopDrawable != null)
      {
        paramCanvas.translate(n, j + i);
        this.mOverScrollBorderTopDrawable.setBounds(0, 0, this.mHostView.getWidth(), -i);
        this.mOverScrollBorderTopDrawable.draw(paramCanvas);
      }
      paramCanvas.restoreToCount(i1);
      if (i < 0) {
        paramCanvas.translate(0.0F, -i);
      }
      return true;
    }
    return false;
  }
  
  public boolean OverScrollTouchHandler(MotionEvent paramMotionEvent)
  {
    if (!this.mEnableOverScrollBrand) {
      return false;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("OverScrollTouchHandler isOverScrollMode():");
    ((StringBuilder)localObject).append(isOverScrollMode());
    LOG(((StringBuilder)localObject).toString());
    int j = paramMotionEvent.getActionIndex();
    int k = paramMotionEvent.getActionMasked();
    int i;
    if (k == 0)
    {
      this.mLastTouchY = paramMotionEvent.getY(j);
      localObject = this.mHostView;
      if (localObject != null) {
        i = ((View)localObject).getScrollY();
      } else {
        i = 0;
      }
      this.mScrollYWhenTapDown = i;
      if (isOverScrollMode())
      {
        localObject = this.mBackupReleaseEventInScrollMode;
        if (localObject != null)
        {
          ((MotionEvent)localObject).recycle();
          this.mBackupReleaseEventInScrollMode = null;
        }
      }
    }
    if ((k == 1) || (k == 3))
    {
      this.mLastTouchY = 0.0F;
      this.mScrollYWhenTapDown = 0;
      this.mPreAddressBarVisibleState = false;
      if (isOverScrollMode())
      {
        localObject = this.mBackupReleaseEventInScrollMode;
        if (localObject != null) {
          ((MotionEvent)localObject).setAction(k);
        }
      }
    }
    if (isOverScrollMode())
    {
      if ((paramMotionEvent.getActionMasked() == 2) && (this.mHostView.getScrollY() >= 0)) {
        return false;
      }
      if (paramMotionEvent.getActionMasked() == 2)
      {
        float f = paramMotionEvent.getY(j);
        i = (int)(this.mLastTouchY - f);
        if (this.mBackupReleaseEventInScrollMode == null) {
          this.mBackupReleaseEventInScrollMode = MotionEvent.obtain(paramMotionEvent);
        }
        paramMotionEvent = this.mScrollOffsetManager;
        if (paramMotionEvent != null) {
          paramMotionEvent.overScrollBy(0, i);
        } else if (!this.mPreAddressBarVisibleState) {
          this.mPreAddressBarVisibleState = true;
        } else if (paramMotionEvent != null) {
          paramMotionEvent.overScrollBy(0, i);
        }
        this.mLastTouchY = f;
      }
      return true;
    }
    return false;
  }
  
  public void bindScrollOffsetManager(AwScrollOffsetManager paramAwScrollOffsetManager)
  {
    this.mScrollOffsetManager = paramAwScrollOffsetManager;
  }
  
  public boolean canShouldOverScroll(int paramInt1, int paramInt2)
  {
    return (!this.mInOverScrollMode) && (this.mLastTouchY > 0.0F) && (this.mScrollYWhenTapDown == 0) && (paramInt2 < 0);
  }
  
  public int clampVerticalScroll(int paramInt)
  {
    int k = this.mHostView.getScrollY();
    if (k > 0)
    {
      localAwScrollOffsetManager = this.mScrollOffsetManager;
      if (localAwScrollOffsetManager != null)
      {
        i = k - localAwScrollOffsetManager.computeMaximumVerticalScrollOffset();
        break label41;
      }
    }
    int i = -k;
    label41:
    int j = this.mHostView.getHeight();
    AwScrollOffsetManager localAwScrollOffsetManager = this.mScrollOffsetManager;
    if (localAwScrollOffsetManager != null) {
      j = localAwScrollOffsetManager.computeVerticalScrollExtent();
    }
    if (j > 0) {
      f = 1.0F - i / j;
    } else {
      f = 0.6F;
    }
    float f = Math.max(0.05F, Math.min(0.6F, f));
    j = (int)((paramInt - k) * f);
    i = j;
    if (j >= 0)
    {
      i = j;
      if (j <= 3) {
        i = 3;
      }
    }
    i = Math.max(k + i, paramInt);
    localAwScrollOffsetManager = this.mScrollOffsetManager;
    paramInt = i;
    if (localAwScrollOffsetManager != null) {
      paramInt = Math.min(localAwScrollOffsetManager.computeMaximumVerticalScrollOffset(), i);
    }
    return paramInt;
  }
  
  public void computeScroll()
  {
    Object localObject = this.mScroller;
    if ((localObject != null) && (((OverScroller)localObject).computeScrollOffset()))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("OverScrollBrand.computeScroll x:");
      ((StringBuilder)localObject).append(this.mScroller.getCurrX());
      ((StringBuilder)localObject).append(" y:");
      ((StringBuilder)localObject).append(this.mScroller.getCurrY());
      LOG(((StringBuilder)localObject).toString());
      this.mScrollOffsetManager.scrollContainerViewTo(this.mScroller.getCurrX(), this.mScroller.getCurrY());
      if (this.mScroller.isFinished()) {
        overScrollFinished();
      }
    }
  }
  
  public boolean drawBrand(Canvas paramCanvas)
  {
    if (!this.mEnableOverScrollBrand) {
      return false;
    }
    return drawTopBrand(paramCanvas);
  }
  
  public boolean isEnableOverscrollBrand()
  {
    return this.mEnableOverScrollBrand;
  }
  
  public boolean isOverScrollMode()
  {
    if (!this.mInOverScrollMode)
    {
      Object localObject = this.mHostView;
      if ((localObject == null) || (((View)localObject).getScrollY() >= 0))
      {
        localObject = this.mScroller;
        if ((localObject == null) || (!((OverScroller)localObject).isOverScrolled())) {
          return false;
        }
      }
    }
    return true;
  }
  
  public void overScrollFinished()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("overScrollFinished mInOverScrollMode:");
    ((StringBuilder)localObject).append(this.mInOverScrollMode);
    LOG(((StringBuilder)localObject).toString());
    this.mInOverScrollMode = false;
    if (this.mAwContents != null)
    {
      localObject = this.mBackupReleaseEventInScrollMode;
      if ((localObject != null) && ((((MotionEvent)localObject).getAction() == 1) || (this.mBackupReleaseEventInScrollMode.getAction() == 3)))
      {
        localObject = this.mAwContents;
        if ((localObject != null) && (((AwContents)localObject).getContentViewCore() != null)) {
          this.mAwContents.getContentViewCore().getWebContents().getEventForwarder().a(this.mBackupReleaseEventInScrollMode);
        }
        this.mBackupReleaseEventInScrollMode.recycle();
        this.mBackupReleaseEventInScrollMode = null;
      }
    }
  }
  
  public void releaseAll()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("ReleaseAll isOverScrollMode():");
    localStringBuilder.append(isOverScrollMode());
    LOG(localStringBuilder.toString());
    if (this.mHostView != null)
    {
      if (!isOverScrollMode()) {
        return;
      }
      if ((this.mScrollOffsetManager != null) && (this.mHostView.getScrollY() < 0))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("ReleaseAll mHostView.getScrollY():");
        localStringBuilder.append(this.mHostView.getScrollY());
        LOG(localStringBuilder.toString());
        if (this.mScrollOffsetManager != null)
        {
          int i = this.mHostView.getScrollX();
          int j = this.mHostView.getScrollY();
          if (this.mScroller.springBack(i, j, 0, this.mScrollOffsetManager.computeHorizontalScrollOffset(), 0, this.mScrollOffsetManager.computeVerticalScrollRange())) {
            this.mHostView.invalidate();
          }
        }
      }
      return;
    }
  }
  
  public void setEnableOverScrollBrand(boolean paramBoolean)
  {
    if (paramBoolean != this.mEnableOverScrollBrand) {
      this.mEnableOverScrollBrand = paramBoolean;
    }
  }
  
  public void setOverScrollMode(boolean paramBoolean)
  {
    this.mInOverScrollMode = paramBoolean;
  }
  
  public void setOverScrollParams(Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3)
  {
    LOG("OverScrollBrand.setOverScrollParams");
    this.mOverScrollBackgroundDrawable = paramDrawable1;
    this.mOverScrollBorderTopDrawable = paramDrawable2;
    this.mOverScrollBackgroundPaint = null;
    setEnableOverScrollBrand(true);
  }
  
  private static class DefaultInterpolator
    implements Interpolator
  {
    public float getInterpolation(float paramFloat)
    {
      paramFloat -= 1.0F;
      return paramFloat * (paramFloat * paramFloat * paramFloat * paramFloat) + 1.0F;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\widget\OverScrollBrand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */