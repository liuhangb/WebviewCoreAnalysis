package com.tencent.tbs.core.partner.ad;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;

public class AdWebViewAdapterBubbleForEncourageWindow
  extends AdWebViewAdapterBubble
{
  public AdWebViewAdapterBubbleForEncourageWindow(Context paramContext, boolean paramBoolean, TencentWebViewProxy paramTencentWebViewProxy, AdWebViewController paramAdWebViewController)
  {
    super(paramContext, paramBoolean, paramTencentWebViewProxy, paramAdWebViewController);
    initBubbleAdWebView();
    initWebViewClientOpenedInMainWebView();
  }
  
  protected void initBubbleAdWebView()
  {
    this.mAdWebView.getRealWebView().setBackgroundColor(0);
    Object localObject = new FrameLayout.LayoutParams(-1, -1);
    ((FrameLayout.LayoutParams)localObject).gravity = 51;
    ((FrameLayout.LayoutParams)localObject).leftMargin = 0;
    ((FrameLayout.LayoutParams)localObject).topMargin = 0;
    this.mAdWebView.getRealWebView().setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mAdWebView.getRealWebView().setFocusableInTouchMode(true);
    localObject = new FrameLayout.LayoutParams(-1, -1);
    ((FrameLayout.LayoutParams)localObject).gravity = 51;
    if ((this.mMainWebView.getRealWebView().getParent() instanceof FrameLayout)) {
      ((FrameLayout)this.mMainWebView.getRealWebView().getParent()).addView(this.mAdWebView.getRealWebView(), (ViewGroup.LayoutParams)localObject);
    }
    this.mAdWebView.getRealWebView().setVisibility(0);
    localObject = this.mContext.getResources().getConfiguration();
    if ((localObject != null) && (((Configuration)localObject).orientation == 1)) {
      setOrientationPortrait(true);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdWebViewAdapterBubbleForEncourageWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */