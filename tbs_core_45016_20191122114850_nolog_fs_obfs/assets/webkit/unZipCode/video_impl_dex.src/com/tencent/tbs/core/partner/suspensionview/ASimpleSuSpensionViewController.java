package com.tencent.tbs.core.partner.suspensionview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.tbs.core.ProxyWebViewObserver;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwWebResourceResponse;

public abstract class ASimpleSuSpensionViewController
  extends ProxyWebViewObserver
{
  public static final boolean ENABLEVIEW = true;
  public static final String LOGTAG = "SimpleSpensionViewController";
  private Context appContext;
  private int[] boundys = null;
  String coreVersion = "0";
  boolean isExpanded = false;
  private ViewGroup layout;
  DraggableLinearLayout mLayout;
  private FrameLayout.LayoutParams mOuterLayoutParams;
  private int mVisiablity = 0;
  private TencentWebViewProxy mWebview;
  int offsetX = -1;
  int offsetY = -1;
  int tbsSize = 40;
  
  public ASimpleSuSpensionViewController(TencentWebViewProxy paramTencentWebViewProxy)
  {
    super(paramTencentWebViewProxy.getRealWebView());
    this.mWebview = paramTencentWebViewProxy;
    this.appContext = this.mWebview.getContext().getApplicationContext();
    this.mWebview.addObserver(this);
  }
  
  private boolean isViewEnabled(Context paramContext)
  {
    return true;
  }
  
  public abstract View generateView(Context paramContext);
  
  public ViewGroup getContentView()
  {
    if (this.layout == null)
    {
      DraggableLinearLayout localDraggableLinearLayout = new DraggableLinearLayout(this.appContext, this.boundys);
      localDraggableLinearLayout.setBackgroundColor(0);
      localDraggableLinearLayout.addView(generateView(this.appContext));
      this.layout = localDraggableLinearLayout;
    }
    return this.layout;
  }
  
  public void onContentsSizeChanged(int paramInt1, int paramInt2) {}
  
  public void onFirstScreenTime(long paramLong1, long paramLong2) {}
  
  public void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2) {}
  
  public void onPageFinished(String paramString)
  {
    if ((this.mWebview != null) && (isViewEnabled(this.appContext)) && (this.layout == null))
    {
      this.boundys = new int[] { this.mWebview.getRealWebView().getWidth(), this.mWebview.getRealWebView().getHeight() };
      if ((this.mWebview.getRealWebView().getParent() instanceof FrameLayout))
      {
        int i = SUtiles.dip2px(this.appContext, this.tbsSize);
        if (this.mOuterLayoutParams == null)
        {
          this.mOuterLayoutParams = new FrameLayout.LayoutParams(-2, -2);
          paramString = this.mOuterLayoutParams;
          paramString.gravity = 0;
          int[] arrayOfInt = this.boundys;
          paramString.setMargins(arrayOfInt[0] - i, arrayOfInt[1] * 9 / 10 - i - 40, 0, 0);
        }
        paramString = (DraggableLinearLayout)getContentView();
        ((FrameLayout)this.mWebview.getRealWebView().getParent()).addView(paramString, this.mOuterLayoutParams);
      }
    }
    paramString = this.layout;
    if (paramString != null) {
      paramString.setVisibility(this.mVisiablity);
    }
  }
  
  public void onQProxyResponseReceived(String paramString) {}
  
  public void onReceivedError(int paramInt, String paramString1, String paramString2) {}
  
  public void onReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse) {}
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void onUpdateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2) {}
  
  public void onWebViewDestroyed()
  {
    this.mWebview = null;
    this.layout = null;
  }
  
  public void onWindowVisibilityChanged(int paramInt) {}
  
  public void reset()
  {
    if (this.mOuterLayoutParams != null)
    {
      ViewGroup localViewGroup = this.layout;
      if (localViewGroup != null)
      {
        ((DraggableLinearLayout)localViewGroup).reset();
        this.layout.setLayoutParams(this.mOuterLayoutParams);
      }
    }
  }
  
  public void setBoundys(int[] paramArrayOfInt)
  {
    this.boundys = paramArrayOfInt;
    ViewGroup localViewGroup = this.layout;
    if (localViewGroup != null) {
      ((DraggableLinearLayout)localViewGroup).setBoundys(paramArrayOfInt);
    }
  }
  
  public void setOuterLayoutParams(FrameLayout.LayoutParams paramLayoutParams)
  {
    if (paramLayoutParams != null) {
      this.mOuterLayoutParams = paramLayoutParams;
    }
  }
  
  public void setVisibility(int paramInt)
  {
    this.mVisiablity = paramInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\suspensionview\ASimpleSuSpensionViewController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */