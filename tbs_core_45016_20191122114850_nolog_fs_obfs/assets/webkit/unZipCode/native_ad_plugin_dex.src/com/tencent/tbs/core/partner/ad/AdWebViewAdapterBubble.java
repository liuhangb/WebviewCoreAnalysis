package com.tencent.tbs.core.partner.ad;

import android.content.Context;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.partner.ad.stats.AdStatsInfoRecorder;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;

public class AdWebViewAdapterBubble
  extends AdWebViewAdapter
{
  private boolean mHasLoad = false;
  
  public AdWebViewAdapterBubble(Context paramContext, boolean paramBoolean, AdInfoUnit paramAdInfoUnit, TencentWebViewProxy paramTencentWebViewProxy, AdWebViewController paramAdWebViewController)
  {
    super(paramContext, paramBoolean, paramAdInfoUnit, paramTencentWebViewProxy, paramAdWebViewController);
    this.mAdapterType = AdWebViewAdapter.AdapterType.Bubble;
  }
  
  public AdWebViewAdapterBubble(Context paramContext, boolean paramBoolean, TencentWebViewProxy paramTencentWebViewProxy, AdWebViewController paramAdWebViewController)
  {
    super(paramContext, paramBoolean, paramTencentWebViewProxy, paramAdWebViewController);
    this.mAdapterType = AdWebViewAdapter.AdapterType.Bubble;
  }
  
  public boolean hideAd()
  {
    return super.hideAd();
  }
  
  public void initAdWebview()
  {
    initBubbleOrTopAdWebView();
  }
  
  protected void initStatInfo()
  {
    AdWebView localAdWebView;
    StringBuilder localStringBuilder;
    if (this.mADPosition == 1)
    {
      this.mPosId = "60";
      this.mStatKeyMidPage = "AZTQB18";
      this.mPositionID = 10;
      localAdWebView = this.mAdWebView;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("AGNQ2_");
      localStringBuilder.append(this.mKey);
      localAdWebView.setAdStatClickKey(localStringBuilder.toString());
      this.mAdWebView.setAdStatClickPosIdForQB("51");
      this.mAdWebView.setAdStatClickEntryIdForMiniQB("32");
      return;
    }
    if (this.mADPosition == 2)
    {
      this.mPosId = "61";
      this.mStatKeyMidPage = "AZTQB18";
      this.mPositionID = 34;
      localAdWebView = this.mAdWebView;
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("AGNQ4_");
      localStringBuilder.append(this.mKey);
      localAdWebView.setAdStatClickKey(localStringBuilder.toString());
      this.mAdWebView.setAdStatClickPosIdForQB("53");
      this.mAdWebView.setAdStatClickEntryIdForMiniQB("35");
    }
  }
  
  protected boolean isTopSpliceAd()
  {
    return false;
  }
  
  public void refreshAdWebview()
  {
    if (X5Log.isEnableLog()) {
      Log.e("NativeAdDebug", "refreshAdWebview: 3");
    }
    if ((this.mADUrl != null) && (!this.mADUrl.equals("0")) && (this.mAdWebView != null))
    {
      if (this.isAdWebviewRefreshing) {
        return;
      }
      if (X5Log.isEnableLog()) {
        Log.e("NativeAdDebug", "refreshAdWebview: 4");
      }
      if (!this.mAdClosed)
      {
        if (!this.mOrientationPortrait) {
          return;
        }
        Object localObject1 = (FrameLayout.LayoutParams)this.mAdWebView.getRealWebView().getLayoutParams();
        int k = this.mMainWebView.getRealWebView().getWidth();
        int m = (int)(getAdWebViewHeight() * this.mAdWebView.getScale());
        if (m < 0)
        {
          hideAd();
          return;
        }
        this.isAdWebviewRefreshing = true;
        this.mAdWebView.notifyADWebviewVisiableHeight(getAdWebViewHeight());
        int i;
        if (k != ((FrameLayout.LayoutParams)localObject1).width)
        {
          ((FrameLayout.LayoutParams)localObject1).width = k;
          i = 1;
        }
        else
        {
          i = 0;
        }
        int j = i;
        if (m != 0)
        {
          j = i;
          if (m != ((FrameLayout.LayoutParams)localObject1).height)
          {
            ((FrameLayout.LayoutParams)localObject1).height = m;
            j = 1;
          }
        }
        if (this.mPreAdPosition != this.mADPosition)
        {
          if (this.mADPosition == 1) {
            ((FrameLayout.LayoutParams)localObject1).gravity = 80;
          } else if (this.mADPosition == 2) {
            ((FrameLayout.LayoutParams)localObject1).gravity = 48;
          }
          this.mPreAdPosition = this.mADPosition;
          j = 1;
        }
        Object localObject2;
        if (j != 0)
        {
          if (X5Log.isEnableLog())
          {
            localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("AdWebViewAdapter--refreshAdWebview()--AD_TYPE_BUBBLE. newADWebViewWidth = ");
            ((StringBuilder)localObject2).append(k);
            ((StringBuilder)localObject2).append(", al_b.width = ");
            ((StringBuilder)localObject2).append(((FrameLayout.LayoutParams)localObject1).width);
            ((StringBuilder)localObject2).append(", newADWebViewHeight = ");
            ((StringBuilder)localObject2).append(m);
            ((StringBuilder)localObject2).append(", al_b.height = ");
            ((StringBuilder)localObject2).append(((FrameLayout.LayoutParams)localObject1).height);
            ((StringBuilder)localObject2).append(", mAdWebView.getScale() = ");
            ((StringBuilder)localObject2).append(this.mAdWebView.getScale());
            Log.e("NativeAdDebug", ((StringBuilder)localObject2).toString());
          }
          this.mAdWebView.getRealWebView().setLayoutParams((ViewGroup.LayoutParams)localObject1);
          initCloseBtPos();
        }
        if ((isShouldReloadURL()) && (!this.mHasLoad))
        {
          localObject2 = this.mAdWebViewController.getAdWebviewContent();
          if ((localObject2 != null) && (!TextUtils.isEmpty(((ContentInfo)localObject2).getContent())))
          {
            localObject1 = ((ContentInfo)localObject2).getType();
            String str = ((ContentInfo)localObject2).getEncoding();
            if (localObject1 == null) {
              localObject1 = "text/html";
            }
            this.mAdWebView.loadDataWithBaseURL(this.mADUrl, ((ContentInfo)localObject2).getContent(), (String)localObject1, str, this.mADUrl);
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
          this.mHasLoad = true;
          if (X5Log.isEnableLog())
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("AdWebViewAdapter--refreshAdWebview()--AD_TYPE_BUBBLE--loadUrl--mADUrl = ");
            ((StringBuilder)localObject1).append(this.mADUrl);
            Log.e("NativeAdDebug", ((StringBuilder)localObject1).toString());
          }
        }
        if ((this.mAdWebView.isAdWebViewLoadFinished()) && (!this.mAdWebView.isAdWebLoadError()) && (this.mAdWebView.isOnMeasured()) && (this.mAdWebViewController.getBubbleWvHeightFromJs() > 10)) {
          showAd();
        }
        this.isAdWebviewRefreshing = false;
        return;
      }
      return;
    }
  }
  
  public void setAdInfoValid(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mAdClosed = false;
      this.mHasLoad = false;
      return;
    }
    hideAd();
  }
  
  protected boolean showAd()
  {
    if (!super.showAd()) {
      return true;
    }
    if (this.mHandler == null) {
      this.mHandler = new Handler();
    }
    Object localObject = new Runnable()
    {
      public void run()
      {
        try
        {
          AdWebViewAdapterBubble.this.hideAd();
          return;
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    };
    if (this.mBubbleAdHideTime > 0) {
      this.mHandler.postDelayed((Runnable)localObject, this.mBubbleAdHideTime * 1000);
    }
    if (X5Log.isEnableLog()) {
      Log.e("NativeAdDebug", "ADWebViewAdatper--showBubbleAd()");
    }
    localObject = SmttServiceProxy.getInstance();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(AdUtils.getExposureStatKeyForBubbleAd(this.mType, this.mADPosition));
    localStringBuilder.append(this.mKey);
    ((SmttServiceProxy)localObject).userBehaviorStatistics(localStringBuilder.toString());
    this.mAdWebView.notifyADWebviewVisiableHeight(0);
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdWebViewAdapterBubble.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */