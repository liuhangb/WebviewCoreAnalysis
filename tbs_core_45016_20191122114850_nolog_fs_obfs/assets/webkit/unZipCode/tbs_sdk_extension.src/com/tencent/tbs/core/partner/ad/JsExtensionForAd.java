package com.tencent.tbs.core.partner.ad;

import android.content.Context;
import android.graphics.Point;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.partner.ad.stats.AdStatsInfoRecorder;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import org.chromium.base.ThreadUtils;
import org.json.JSONException;
import org.json.JSONObject;

public class JsExtensionForAd
{
  private Point mAdLocation = new Point(-1, -1);
  private AdWebViewController mAdWebViewController = null;
  private int mBubbleAdWebViewHeight = 0;
  private TencentWebViewProxy mWebView = null;
  private Point mWebViewSize = new Point(-1, -1);
  
  public JsExtensionForAd(AdWebViewController paramAdWebViewController, TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.mAdWebViewController = paramAdWebViewController;
    this.mWebView = paramTencentWebViewProxy;
  }
  
  @JavascriptInterface
  public void OpenVideoLandPage(final String paramString)
  {
    TencentWebViewProxy localTencentWebViewProxy = this.mWebView;
    if (localTencentWebViewProxy == null) {
      return;
    }
    localTencentWebViewProxy.getRealWebView().post(new Runnable()
    {
      public void run()
      {
        SmttServiceProxy.getInstance().openLandPage(JsExtensionForAd.this.mWebView.getContext(), paramString, (ViewGroup)JsExtensionForAd.this.mWebView.getRealWebView().getParent());
      }
    });
  }
  
  public void SetBubbleAdWebViewHeight(int paramInt)
  {
    this.mBubbleAdWebViewHeight = paramInt;
  }
  
  @JavascriptInterface
  public void closeAdWebviewAdatperBubbleForEncourageWindow(String paramString)
  {
    if (!hasPermission()) {
      return;
    }
    this.mAdWebViewController.sendCloseEncourageWindowMsg();
  }
  
  public int getBubbleAdWebViewHeight()
  {
    return this.mBubbleAdWebViewHeight;
  }
  
  @JavascriptInterface
  public String getLocationInWindow()
  {
    if (!hasPermission()) {
      return null;
    }
    AdWebViewController localAdWebViewController = this.mAdWebViewController;
    if (localAdWebViewController != null) {
      return localAdWebViewController.getLocationInWindow();
    }
    return "x:-1;y:-1";
  }
  
  @JavascriptInterface
  public String getLocationOnScreen()
  {
    if (!hasPermission()) {
      return null;
    }
    AdWebViewController localAdWebViewController = this.mAdWebViewController;
    if (localAdWebViewController != null) {
      return localAdWebViewController.getLocationOnScreen();
    }
    return "x:-1;y:-1";
  }
  
  @JavascriptInterface
  public String getStatSession(String paramString)
  {
    SmttServiceProxy localSmttServiceProxy = SmttServiceProxy.getInstance();
    X5WebViewAdapter localX5WebViewAdapter = (X5WebViewAdapter)this.mWebView;
    int i = this.mAdLocation.x;
    Point localPoint2 = null;
    Point localPoint1;
    if (i == -1) {
      localPoint1 = null;
    } else {
      localPoint1 = this.mAdLocation;
    }
    if (this.mWebViewSize.y != -1) {
      localPoint2 = this.mWebViewSize;
    }
    return localSmttServiceProxy.getTbsAdUserInfo(localX5WebViewAdapter, paramString, localPoint1, localPoint2);
  }
  
  @JavascriptInterface
  public String getmInstallPackageName()
  {
    if (!hasPermission()) {
      return null;
    }
    return this.mAdWebViewController.getInstallPackageName();
  }
  
  public boolean hasPermission()
  {
    final boolean[] arrayOfBoolean = new boolean[1];
    arrayOfBoolean[0] = false;
    ThreadUtils.runOnUiThreadBlocking(new Runnable()
    {
      public void run()
      {
        String str = JsExtensionForAd.this.mWebView.getRealWebView().getUrl();
        if ((!TextUtils.isEmpty(str)) && (TencentURLUtil.getHost(str).endsWith(".qq.com"))) {
          arrayOfBoolean[0] = true;
        }
      }
    });
    return arrayOfBoolean[0];
  }
  
  public void setAdLocation(int paramInt1, int paramInt2)
  {
    this.mAdLocation.set(paramInt1, paramInt2);
  }
  
  public void setWebview(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.mWebView = paramTencentWebViewProxy;
  }
  
  public void setWebviewSize(int paramInt1, int paramInt2)
  {
    this.mWebViewSize.set(paramInt1, paramInt2);
  }
  
  @JavascriptInterface
  public void setWvHeight(float paramFloat)
  {
    this.mBubbleAdWebViewHeight = ((int)paramFloat);
    if (X5Log.isEnableLog())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("JsExtensionForAdsetWvHeight height = ");
      localStringBuilder.append(paramFloat);
      localStringBuilder.append(", mBubbleAdWebViewHeight=");
      localStringBuilder.append(this.mBubbleAdWebViewHeight);
      Log.e("NativeAdDebug", localStringBuilder.toString());
    }
    if (this.mBubbleAdWebViewHeight != 0)
    {
      if (X5Log.isEnableLog()) {
        Log.e("NativeAdDebug", "JsExtensionForAdsetWvHeight call onAdWebviewUpdate");
      }
      this.mAdWebViewController.callAdWebviewUpdate();
    }
  }
  
  @JavascriptInterface
  public void uploadEncourageWindowEvent(String paramString)
  {
    if (!hasPermission()) {
      return;
    }
    if (!TextUtils.isEmpty(paramString)) {
      try
      {
        Object localObject = new JSONObject(paramString);
        paramString = ((JSONObject)localObject).getString("packageName");
        localObject = ((JSONObject)localObject).getString("type");
        AdStatsInfoRecorder.uploadEncourageWindowEvent("TBS_ENCOURAGE_WINDOW_REPORT", this.mWebView.getContext().getPackageName(), paramString, (String)localObject);
        return;
      }
      catch (JSONException paramString)
      {
        paramString.printStackTrace();
        return;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\JsExtensionForAd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */