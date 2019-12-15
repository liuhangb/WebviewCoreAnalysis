package com.tencent.tbs.core.partner.ad;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.webkit.e;
import com.tencent.tbs.core.partner.ad.stats.AdStatsInfoRecorder;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;

public class AdWebViewAdapterSplice
  extends AdWebViewAdapter
{
  private boolean mHasLoad = false;
  
  public AdWebViewAdapterSplice(Context paramContext, boolean paramBoolean, AdInfoUnit paramAdInfoUnit, TencentWebViewProxy paramTencentWebViewProxy, AdWebViewController paramAdWebViewController)
  {
    super(paramContext, paramBoolean, paramAdInfoUnit, paramTencentWebViewProxy, paramAdWebViewController);
    this.mAdapterType = AdWebViewAdapter.AdapterType.Splice;
    this.mNeedShowAd = true;
  }
  
  private boolean loadAdIfNeed()
  {
    if (!this.mNeedShowAd) {
      return false;
    }
    if (this.mHasLoad) {
      return false;
    }
    if (!isShouldReloadURL()) {
      return false;
    }
    this.mHasLoad = true;
    ContentInfo localContentInfo = this.mAdWebViewController.getAdWebviewContent();
    if ((localContentInfo != null) && (!TextUtils.isEmpty(localContentInfo.getContent())))
    {
      String str1 = localContentInfo.getType();
      String str2 = localContentInfo.getEncoding();
      if (str1 == null) {
        str1 = "text/html";
      }
      this.mAdWebView.loadDataWithBaseURL(this.mADUrl, localContentInfo.getContent(), str1, str2, this.mADUrl);
      this.mAdWebView.setHostUrl(this.mMainWebView.getUrl());
      AdStatsInfoRecorder.getInstance().sendStatsInfoToTbsAdServer("AD_PAGE_LOADED", this.mADUrl, this.mMainWebView.getUrl());
    }
    else
    {
      this.mAdWebView.loadUrl(this.mADUrl);
      this.mAdWebView.setHostUrl(this.mMainWebView.getUrl());
      AdStatsInfoRecorder.getInstance().sendStatsInfoToTbsAdServer("AD_PAGE_LOADED", this.mADUrl, this.mMainWebView.getUrl());
    }
    setShouldReloadURL(false);
    return true;
  }
  
  private void showAdIfNeed()
  {
    if (!this.mNeedShowAd) {
      return;
    }
    if (this.mAdWebViewController.getBubbleWvHeightFromJs() <= 10) {
      return;
    }
    if (this.mAdWebView.isAdWebLoadError())
    {
      this.mNeedShowAd = false;
      return;
    }
    if (!this.mAdWebView.isAdWebViewLoadFinished()) {
      return;
    }
    this.mNeedShowAd = false;
    showAd();
  }
  
  public boolean hideAd()
  {
    this.mNeedShowAd = false;
    if (!super.hideAd()) {
      return true;
    }
    Object localObject;
    if (this.mADPosition == 1)
    {
      if (this.mMainWebView == null) {
        return true;
      }
      if (X5Log.isEnableLog()) {
        Log.e("NativeAdDebug", "AdWebViewAdapterSplice--bottom()");
      }
      final int i = this.mMainWebView.getRealWebView().getScrollY();
      int j = this.mMainWebView.getRealWebView().getHeight();
      float f = this.mMainWebView.getScale();
      i = (int)((i + j - this.mMainWebView.getRealWebView().getContentHeight() * f) / f);
      if (i <= 0)
      {
        setMainX5WebviewMaxOverScrollY(0);
        return true;
      }
      localObject = new Runnable()
      {
        public void run()
        {
          try
          {
            if (!AdWebViewAdapterSplice.this.mAdClosed) {
              return;
            }
            int i = i / 10;
            int j = AdWebViewAdapterSplice.this.mMainWebView.getScrollY();
            if (j - i >= this.val$startScrollY - i)
            {
              AdWebViewAdapterSplice.this.setMainX5WebviewMaxOverScrollY(j - (this.val$startScrollY - i));
              AdWebViewAdapterSplice.this.mMainWebView.scrollBy(0, -i);
              AdWebViewAdapterSplice.this.mHandler.postDelayed(this, 10L);
              return;
            }
            AdWebViewAdapterSplice.this.setMainX5WebviewMaxOverScrollY(0);
            AdWebViewAdapterSplice.this.mHandler.removeCallbacks(this);
            return;
          }
          catch (Exception localException)
          {
            for (;;) {}
          }
          AdWebViewAdapterSplice.this.setMainX5WebviewMaxOverScrollY(0);
        }
      };
      if (this.mHandler == null) {
        this.mHandler = new Handler();
      }
      this.mHandler.post((Runnable)localObject);
      return true;
    }
    if (this.mADPosition == 2)
    {
      if (this.mMainWebView == null) {
        return true;
      }
      if (X5Log.isEnableLog()) {
        Log.e("NativeAdDebug", "AdWebViewAdapterSplice--top()");
      }
      localObject = new Runnable()
      {
        public void run()
        {
          for (;;)
          {
            try
            {
              FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)AdWebViewAdapterSplice.this.mMainWebView.getRealWebView().getLayoutParams();
              int j = localLayoutParams.topMargin - this.val$topMargin / 10;
              if (!AdWebViewAdapterSplice.this.mAdClosed) {
                return;
              }
              localLayoutParams.gravity = -1;
              if (j >= 0)
              {
                i = j;
                localLayoutParams.topMargin = i;
                AdWebViewAdapterSplice.this.mMainWebView.getRealWebView().setLayoutParams(localLayoutParams);
                if (j > 0)
                {
                  AdWebViewAdapterSplice.this.mHandler.postDelayed(this, 10L);
                  return;
                }
                AdWebViewAdapterSplice.this.mHandler.removeCallbacks(this);
                return;
              }
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
              return;
            }
            int i = 0;
          }
        }
      };
      if (this.mHandler == null) {
        this.mHandler = new Handler();
      }
      this.mHandler.post((Runnable)localObject);
    }
    return true;
  }
  
  public void initAdWebview()
  {
    if (this.mADPosition == 1)
    {
      initSpliceAdWebView();
      return;
    }
    if (this.mADPosition == 2) {
      initBubbleOrTopAdWebView();
    }
  }
  
  protected void initStatInfo()
  {
    AdWebView localAdWebView;
    StringBuilder localStringBuilder;
    if (this.mADPosition == 1)
    {
      this.mPosId = "70";
      this.mStatKeyMidPage = "AZTQB17";
      this.mPositionID = 9;
      localAdWebView = this.mAdWebView;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("AGNP2_");
      localStringBuilder.append(this.mKey);
      localAdWebView.setAdStatClickKey(localStringBuilder.toString());
      this.mAdWebView.setAdStatClickPosIdForQB("52");
      this.mAdWebView.setAdStatClickEntryIdForMiniQB("33");
      return;
    }
    if (this.mADPosition == 2)
    {
      this.mPosId = "71";
      this.mStatKeyMidPage = "AZTQB17";
      this.mPositionID = 36;
      localAdWebView = this.mAdWebView;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("AGNP4_");
      localStringBuilder.append(this.mKey);
      localAdWebView.setAdStatClickKey(localStringBuilder.toString());
      this.mAdWebView.setAdStatClickPosIdForQB("54");
      this.mAdWebView.setAdStatClickEntryIdForMiniQB("37");
    }
  }
  
  protected boolean isTopSpliceAd()
  {
    return this.mADPosition == 2;
  }
  
  public void onBrowserControlsChanged(int paramInt)
  {
    if ((this.mADPosition == 2) && (e.a().n())) {}
    try
    {
      FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.mAdWebView.getRealWebView().getLayoutParams();
      localLayoutParams.topMargin = Math.max(paramInt, 0);
      this.mAdWebView.getRealWebView().setLayoutParams(localLayoutParams);
      return;
    }
    catch (Exception localException) {}
  }
  
  public void refreshAdWebview()
  {
    if ((this.mADUrl != null) && (!this.mADUrl.equals("0")) && (this.mAdWebView != null))
    {
      if (this.isAdWebviewRefreshing) {
        return;
      }
      if (loadAdIfNeed()) {
        return;
      }
      this.isAdWebviewRefreshing = true;
      showAdIfNeed();
      if (this.mADPosition == 1) {
        refreshBottomSpliceAdWebview();
      }
      if (this.mADPosition == 2) {
        refreshTopSpliceAdWebview();
      }
      this.isAdWebviewRefreshing = false;
      return;
    }
  }
  
  public void setAdInfoValid(boolean paramBoolean)
  {
    if (X5Log.isEnableLog())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setAdInfoValid:");
      localStringBuilder.append(paramBoolean);
      Log.e("NativeAdDebug", localStringBuilder.toString());
    }
    if (paramBoolean)
    {
      this.mHasLoad = false;
      this.mNeedShowAd = true;
      return;
    }
    hideAd();
  }
  
  protected boolean showAd()
  {
    if (!super.showAd()) {
      return true;
    }
    this.mNeedShowAd = false;
    if (X5Log.isEnableLog()) {
      Log.e("NativeAdDebug", "showAd");
    }
    if (this.mADPosition == 1)
    {
      setMainX5WebviewMaxOverScrollY((int)(getAdWebViewHeight() * this.mAdWebView.getScale()));
      return true;
    }
    this.mAdWebView.notifyADWebviewVisiableHeight(0);
    return true;
  }
  
  public void snapshotVisibleWithBitmap(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, float paramFloat1, float paramFloat2)
  {
    if (this.mAdClosed) {
      return;
    }
    int j = this.mAdWebView.getRealWebView().getHeight();
    int i;
    if (this.mADPosition == 1)
    {
      i = this.mMainWebView.getRealWebView().getScrollY();
      int k = this.mMainWebView.getRealWebView().getHeight();
      float f = this.mMainWebView.getScale();
      k = (int)((k + i - this.mMainWebView.getRealWebView().getContentHeight() * f) / this.mAdWebView.getScale());
      i = (int)(this.mMainWebView.getRealWebView().getContentHeight() * f - i);
      if (k < 0) {
        return;
      }
    }
    else
    {
      i = 0;
    }
    Bitmap localBitmap1 = Bitmap.createBitmap(paramBitmap.getWidth(), j, paramBitmap.getConfig());
    this.mAdWebView.snapshotVisibleWithBitmap(localBitmap1, paramBoolean1, paramBoolean2, paramBoolean3, paramFloat1, paramFloat2);
    if (this.mADPosition == 2)
    {
      Bitmap localBitmap2 = Bitmap.createBitmap(paramBitmap.getWidth(), paramBitmap.getHeight(), paramBitmap.getConfig());
      Canvas localCanvas = new Canvas(localBitmap2);
      localCanvas.drawBitmap(localBitmap1, 0.0F, 0.0F, null);
      localCanvas.drawBitmap(paramBitmap, 0.0F, localBitmap1.getHeight() * paramFloat2, null);
      new Canvas(paramBitmap).drawBitmap(localBitmap2, 0.0F, 0.0F, null);
      localBitmap2.recycle();
    }
    else if (this.mADPosition == 1)
    {
      new Canvas(paramBitmap).drawBitmap(localBitmap1, 0.0F, (int)(i * paramFloat2), null);
    }
    localBitmap1.recycle();
  }
  
  public void updateInfo(AdInfoUnit paramAdInfoUnit)
  {
    if (this.mADPosition != paramAdInfoUnit.getAdPosition())
    {
      if (this.mADPosition == 1) {
        setMainX5WebviewMaxOverScrollY(0);
      }
      if (this.mAdWebView.getRealWebView().getParent() != null)
      {
        int i = ((ViewGroup)this.mAdWebView.getRealWebView().getParent()).indexOfChild(this.mAdWebView.getRealWebView());
        ((ViewGroup)this.mAdWebView.getRealWebView().getParent()).removeViewAt(i);
      }
      super.updateInfo(paramAdInfoUnit);
      initAdWebview();
      return;
    }
    super.updateInfo(paramAdInfoUnit);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdWebViewAdapterSplice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */