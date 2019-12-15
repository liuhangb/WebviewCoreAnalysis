package org.chromium.content.browser;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.util.TypedValue;
import org.chromium.base.VisibleForTesting;

public class RenderCoordinates
{
  protected float mContentHeightCss;
  protected float mContentWidthCss;
  protected float mDeviceScaleFactor = 1.0F;
  protected boolean mHasFrameInfo;
  protected float mLastFrameViewportHeightCss;
  protected float mLastFrameViewportWidthCss;
  protected float mMaxPageScaleFactor = 1.0F;
  protected float mMinPageScaleFactor = 1.0F;
  protected float mPageScaleFactor = 1.0F;
  protected float mScrollXCss;
  protected float mScrollYCss;
  protected float mTopContentOffsetYPix;
  protected float mWheelScrollFactor;
  
  void a(float paramFloat1, float paramFloat2)
  {
    this.mContentWidthCss = paramFloat1;
    this.mContentHeightCss = paramFloat2;
  }
  
  public NormalizedPoint createNormalizedPoint()
  {
    return new NormalizedPoint();
  }
  
  public float fromDipToPix(float paramFloat)
  {
    return paramFloat * this.mDeviceScaleFactor;
  }
  
  public float fromLocalCssToPix(float paramFloat)
  {
    return paramFloat * this.mPageScaleFactor * this.mDeviceScaleFactor;
  }
  
  public float fromPixToDip(float paramFloat)
  {
    return paramFloat / this.mDeviceScaleFactor;
  }
  
  public float fromPixToLocalCss(float paramFloat)
  {
    return paramFloat / (this.mDeviceScaleFactor * this.mPageScaleFactor);
  }
  
  public float getContentHeightCss()
  {
    return this.mContentHeightCss;
  }
  
  public float getContentHeightPix()
  {
    return fromLocalCssToPix(this.mContentHeightCss);
  }
  
  public int getContentHeightPixInt()
  {
    return (int)Math.ceil(getContentHeightPix());
  }
  
  public float getContentOffsetYPix()
  {
    return this.mTopContentOffsetYPix;
  }
  
  public float getContentWidthCss()
  {
    return this.mContentWidthCss;
  }
  
  public float getContentWidthPix()
  {
    return fromLocalCssToPix(this.mContentWidthCss);
  }
  
  public int getContentWidthPixInt()
  {
    return (int)Math.ceil(getContentWidthPix());
  }
  
  public float getDeviceScaleFactor()
  {
    return this.mDeviceScaleFactor;
  }
  
  public float getLastFrameViewportHeightCss()
  {
    return this.mLastFrameViewportHeightCss;
  }
  
  public float getLastFrameViewportHeightPix()
  {
    return fromLocalCssToPix(this.mLastFrameViewportHeightCss);
  }
  
  public int getLastFrameViewportHeightPixInt()
  {
    return (int)Math.ceil(getLastFrameViewportHeightPix());
  }
  
  public float getLastFrameViewportWidthCss()
  {
    return this.mLastFrameViewportWidthCss;
  }
  
  public float getLastFrameViewportWidthPix()
  {
    return fromLocalCssToPix(this.mLastFrameViewportWidthCss);
  }
  
  public int getLastFrameViewportWidthPixInt()
  {
    return (int)Math.ceil(getLastFrameViewportWidthPix());
  }
  
  public float getMaxHorizontalScrollPix()
  {
    return getContentWidthPix() - getLastFrameViewportWidthPix();
  }
  
  public int getMaxHorizontalScrollPixInt()
  {
    return (int)Math.floor(getMaxHorizontalScrollPix());
  }
  
  public float getMaxPageScaleFactor()
  {
    return this.mMaxPageScaleFactor;
  }
  
  public float getMaxVerticalScrollPix()
  {
    return getContentHeightPix() - getLastFrameViewportHeightPix();
  }
  
  public int getMaxVerticalScrollPixInt()
  {
    return (int)Math.floor(getMaxVerticalScrollPix());
  }
  
  public float getMinPageScaleFactor()
  {
    return this.mMinPageScaleFactor;
  }
  
  public float getPageScaleFactor()
  {
    return this.mPageScaleFactor;
  }
  
  public float getScrollX()
  {
    return this.mScrollXCss;
  }
  
  public float getScrollXPix()
  {
    return fromLocalCssToPix(this.mScrollXCss);
  }
  
  public int getScrollXPixInt()
  {
    return (int)Math.floor(getScrollXPix());
  }
  
  public float getScrollY()
  {
    return this.mScrollYCss;
  }
  
  public float getScrollYPix()
  {
    return fromLocalCssToPix(this.mScrollYCss);
  }
  
  public int getScrollYPixInt()
  {
    return (int)Math.floor(getScrollYPix());
  }
  
  public float getWheelScrollFactor()
  {
    return this.mWheelScrollFactor;
  }
  
  public boolean hasFrameInfo()
  {
    return this.mHasFrameInfo;
  }
  
  public void reset()
  {
    this.mScrollYCss = 0.0F;
    this.mScrollXCss = 0.0F;
    this.mPageScaleFactor = 1.0F;
    this.mHasFrameInfo = false;
  }
  
  public void setDeviceScaleFactor(float paramFloat, Context paramContext)
  {
    this.mDeviceScaleFactor = paramFloat;
    TypedValue localTypedValue = new TypedValue();
    if ((paramContext != null) && (paramContext.getTheme().resolveAttribute(16842829, localTypedValue, true)))
    {
      this.mWheelScrollFactor = localTypedValue.getDimension(paramContext.getResources().getDisplayMetrics());
      return;
    }
    this.mWheelScrollFactor = (this.mDeviceScaleFactor * 64.0F);
  }
  
  @VisibleForTesting
  public void setFrameInfoForTest(float paramFloat1, float paramFloat2)
  {
    reset();
    this.mDeviceScaleFactor = paramFloat1;
    this.mTopContentOffsetYPix = paramFloat2;
  }
  
  public void updateFrameInfo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10)
  {
    this.mScrollXCss = paramFloat1;
    this.mScrollYCss = paramFloat2;
    this.mPageScaleFactor = paramFloat7;
    this.mMinPageScaleFactor = paramFloat8;
    this.mMaxPageScaleFactor = paramFloat9;
    this.mTopContentOffsetYPix = paramFloat10;
    a(paramFloat3, paramFloat4);
    this.mLastFrameViewportWidthCss = paramFloat5;
    this.mLastFrameViewportHeightCss = paramFloat6;
    this.mHasFrameInfo = true;
  }
  
  public class NormalizedPoint
  {
    private float jdField_a_of_type_Float;
    private float b;
    
    public NormalizedPoint() {}
    
    public float getXAbsoluteCss()
    {
      return this.jdField_a_of_type_Float;
    }
    
    public float getXLocalDip()
    {
      return (this.jdField_a_of_type_Float - RenderCoordinates.this.mScrollXCss) * RenderCoordinates.this.mPageScaleFactor;
    }
    
    public float getXPix()
    {
      return getXLocalDip() * RenderCoordinates.this.mDeviceScaleFactor;
    }
    
    public float getYAbsoluteCss()
    {
      return this.b;
    }
    
    public float getYLocalDip()
    {
      return (this.b - RenderCoordinates.this.mScrollYCss) * RenderCoordinates.this.mPageScaleFactor;
    }
    
    public float getYPix()
    {
      return getYLocalDip() * RenderCoordinates.this.mDeviceScaleFactor + RenderCoordinates.this.mTopContentOffsetYPix;
    }
    
    public void setAbsoluteCss(float paramFloat1, float paramFloat2)
    {
      this.jdField_a_of_type_Float = paramFloat1;
      this.b = paramFloat2;
    }
    
    public void setLocalDip(float paramFloat1, float paramFloat2)
    {
      setAbsoluteCss(paramFloat1 / RenderCoordinates.this.mPageScaleFactor + RenderCoordinates.this.mScrollXCss, paramFloat2 / RenderCoordinates.this.mPageScaleFactor + RenderCoordinates.this.mScrollYCss);
    }
    
    public void setScreen(float paramFloat1, float paramFloat2)
    {
      setLocalDip(paramFloat1 / RenderCoordinates.this.mDeviceScaleFactor, paramFloat2 / RenderCoordinates.this.mDeviceScaleFactor);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\RenderCoordinates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */