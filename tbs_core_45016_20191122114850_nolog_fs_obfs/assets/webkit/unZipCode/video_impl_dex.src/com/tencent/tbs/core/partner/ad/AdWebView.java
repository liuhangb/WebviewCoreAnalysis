package com.tencent.tbs.core.partner.ad;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import com.tencent.common.http.Apn;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings.LayoutAlgorithm;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings.PluginState;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings.RenderPriority;
import com.tencent.smtt.util.X5Log;
import com.tencent.tbs.core.partner.ad.stats.AdStatsInfoRecorder;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProvider;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class AdWebView
  extends X5WebViewAdapter
{
  private static final int DOUBLE_TAP_TIMEOUT = 300;
  private static final int TOUCH_SLOP = 8;
  private int adClickPeriod = 300;
  private float adClickScale = 8.0F;
  private float adTouchScale = 16.0F;
  private String hostUrl = null;
  private boolean isMoved = false;
  private long lastTouchDownTime = 0L;
  private float lastTouchPosY = 0.0F;
  private String mAdDetailUrl = null;
  private String mAdStatClickEntryIdForMiniQB = null;
  private String mAdStatClickKey = null;
  private String mAdStatClickPosIdForQB = null;
  private boolean mAdWebLoadError = false;
  private AdWebViewAdapter mAdWebViewAdapter = null;
  private boolean mAdWebViewLoadFinished = false;
  private boolean mCurrentAd = false;
  private WeakReference<TencentWebViewProxy> mMainWebView = null;
  private boolean mOnMeasured = false;
  private boolean misADWebviewBubble = false;
  private boolean misADWebviewSplice = false;
  private int notifyCount = 0;
  private ViewConfiguration viewConfiguration;
  
  public AdWebView(Context paramContext, boolean paramBoolean, AdWebViewAdapter paramAdWebViewAdapter)
  {
    super(paramContext, paramBoolean);
    init(paramContext);
    this.mAdWebViewAdapter = paramAdWebViewAdapter;
  }
  
  private boolean doADTouchEvent(MotionEvent paramMotionEvent)
  {
    Object localObject;
    if (this.viewConfiguration == null)
    {
      this.viewConfiguration = ViewConfiguration.get(this.mContext);
      this.adTouchScale = this.viewConfiguration.getScaledTouchSlop();
      localObject = this.viewConfiguration;
      this.adClickPeriod = ViewConfiguration.getLongPressTimeout();
      localObject = this.viewConfiguration;
      this.adClickScale = ViewConfiguration.getTouchSlop();
    }
    if (paramMotionEvent.getAction() == 0)
    {
      this.lastTouchDownTime = System.currentTimeMillis();
      this.lastTouchPosY = paramMotionEvent.getY();
      return true;
    }
    float f1;
    float f2;
    if (1 == paramMotionEvent.getAction())
    {
      long l = System.currentTimeMillis();
      if (this.isMoved)
      {
        this.isMoved = false;
        return true;
      }
      f1 = paramMotionEvent.getY();
      f2 = this.lastTouchPosY;
      if (l - this.lastTouchDownTime <= this.adClickPeriod)
      {
        if (Math.abs(f1 - f2) > this.adTouchScale) {
          return true;
        }
        localObject = getWebViewClientExtension();
        if ((this.isOunterClientForUIEventSetted) && (localObject != null))
        {
          paramMotionEvent.setAction(0);
          bool2 = ((IX5WebViewClientExtension)localObject).onTouchEvent(paramMotionEvent, getView());
          paramMotionEvent.setAction(1);
          bool1 = ((IX5WebViewClientExtension)localObject).onTouchEvent(paramMotionEvent, getView());
          bool2 = Boolean.valueOf(bool2).booleanValue();
          return Boolean.valueOf(bool1).booleanValue() & bool2;
        }
        paramMotionEvent.setAction(0);
        boolean bool2 = onTouchEventImpl(paramMotionEvent);
        paramMotionEvent.setAction(1);
        boolean bool1 = onTouchEventImpl(paramMotionEvent);
        bool2 = Boolean.valueOf(bool2).booleanValue();
        return Boolean.valueOf(bool1).booleanValue() & bool2;
      }
      return true;
    }
    if (2 == paramMotionEvent.getAction())
    {
      if (this.mMainWebView.get() != null)
      {
        localObject = (TencentWebViewProxy)this.mMainWebView.get();
        f1 = paramMotionEvent.getY() - this.lastTouchPosY;
        if (Math.abs(f1) > this.adClickScale) {
          this.isMoved = true;
        }
        f2 = 0.0F;
        if (f1 <= 0.0F)
        {
          float f3 = ((TencentWebViewProxy)localObject).getRealWebView().getScrollY() + ((TencentWebViewProxy)localObject).getRealWebView().getHeight() - (((TencentWebViewProxy)localObject).getRealWebView().getContentHeight() * ((TencentWebViewProxy)localObject).getRealWebView().getScale() + getRealWebView().getContentHeight() * getRealWebView().getScale());
          if (f3 >= 0.0F) {
            f1 = f2;
          } else {
            f1 = Math.max(f1, f3);
          }
        }
        else if (((TencentWebViewProxy)localObject).getRealWebView().getScrollY() <= 0)
        {
          f1 = f2;
        }
        ((TencentWebViewProxy)localObject).getRealWebView().scrollBy(0, -(int)f1);
      }
      return true;
    }
    return true;
  }
  
  public void closeADWebview(String paramString)
  {
    paramString = this.mAdWebViewAdapter;
    if (paramString != null) {
      paramString.hideAdWebviewCallByJsApi();
    }
  }
  
  public String getAdDetailUrl()
  {
    return this.mAdDetailUrl;
  }
  
  public String getAdStatClickEntryIdForMiniQB()
  {
    return this.mAdStatClickEntryIdForMiniQB;
  }
  
  public String getAdStatClickKey()
  {
    return this.mAdStatClickKey;
  }
  
  public String getAdStatClickPosIdForQB()
  {
    return this.mAdStatClickPosIdForQB;
  }
  
  public boolean getIsADWebviewBubble()
  {
    return this.misADWebviewBubble;
  }
  
  public boolean getIsADWebviewSplice()
  {
    return this.misADWebviewSplice;
  }
  
  void init(Context paramContext)
  {
    IX5WebSettings localIX5WebSettings = getSettings();
    localIX5WebSettings.setJavaScriptEnabled(true);
    localIX5WebSettings.setAllowFileAccess(true);
    localIX5WebSettings.setLayoutAlgorithm(IX5WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
    localIX5WebSettings.setSupportZoom(true);
    localIX5WebSettings.setBuiltInZoomControls(true);
    localIX5WebSettings.setUseWideViewPort(true);
    localIX5WebSettings.setSupportMultipleWindows(false);
    localIX5WebSettings.setLoadWithOverviewMode(true);
    localIX5WebSettings.setAppCacheEnabled(true);
    localIX5WebSettings.setDatabaseEnabled(true);
    localIX5WebSettings.setDomStorageEnabled(true);
    localIX5WebSettings.setGeolocationEnabled(true);
    localIX5WebSettings.setAppCacheMaxSize(Long.MAX_VALUE);
    localIX5WebSettings.setAppCachePath(paramContext.getDir("appcache", 0).getPath());
    localIX5WebSettings.setDatabasePath(paramContext.getDir("databases", 0).getPath());
    localIX5WebSettings.setGeolocationDatabasePath(paramContext.getDir("geolocation", 0).getPath());
    localIX5WebSettings.setPluginState(IX5WebSettings.PluginState.ON_DEMAND);
    localIX5WebSettings.setRenderPriority(IX5WebSettings.RenderPriority.HIGH);
    localIX5WebSettings.setMediaPlaybackRequiresUserGesture(false);
  }
  
  public boolean isAdWebLoadError()
  {
    return this.mAdWebLoadError;
  }
  
  public boolean isAdWebViewLoadFinished()
  {
    return this.mAdWebViewLoadFinished;
  }
  
  public boolean isCurrentAd()
  {
    return this.mCurrentAd;
  }
  
  public boolean isOnMeasured()
  {
    return this.mOnMeasured;
  }
  
  public void loadUrl(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    if (paramString.startsWith("javascript:"))
    {
      super.loadUrl(paramString);
      return;
    }
    HashMap localHashMap = new HashMap();
    Object localObject = "UNKNOWN";
    try
    {
      String str = Apn.getApnName(Apn.getApnTypeS());
      localObject = str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    localHashMap.put("NetType", localObject);
    this.notifyCount = 0;
    super.loadUrl(paramString, localHashMap);
  }
  
  public void notifyADWebviewVisiableHeight(int paramInt)
  {
    getWebViewProvider().notifyADWebviewVisiableHeight(paramInt);
    if ((this.misADWebviewSplice) && (paramInt >= getContentHeight() / 2))
    {
      this.notifyCount += 1;
      if (this.notifyCount == 1) {
        AdStatsInfoRecorder.getInstance().sendStatsInfoToTbsAdServer("AD_MAYBE_SHOWED", getUrl(), this.hostUrl);
      }
    }
    if (this.misADWebviewBubble)
    {
      this.notifyCount += 1;
      if (this.notifyCount == 1) {
        AdStatsInfoRecorder.getInstance().sendStatsInfoToTbsAdServer("AD_MAYBE_SHOWED", getUrl(), this.hostUrl);
      }
    }
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    if ((paramInt1 > 0) && (paramInt2 > 0))
    {
      if (X5Log.isEnableLog())
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("AdWebView--onMeasure = ");
        localStringBuilder.append(this.mOnMeasured);
        localStringBuilder.append(", widthMeasureSpec = ");
        localStringBuilder.append(paramInt1);
        localStringBuilder.append(", heightMeasureSpec = ");
        localStringBuilder.append(paramInt2);
        Log.e("NativeAdDebug", localStringBuilder.toString());
      }
      this.mOnMeasured = true;
      getRealWebView().post(new Runnable()
      {
        public void run()
        {
          AdWebView.this.mAdWebViewAdapter.refreshAdWebview();
        }
      });
      return;
    }
    this.mOnMeasured = false;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.misADWebviewSplice) {
      return doADTouchEvent(paramMotionEvent);
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void setAdDetailUrl(String paramString)
  {
    this.mAdDetailUrl = paramString;
  }
  
  public void setAdStatClickEntryIdForMiniQB(String paramString)
  {
    this.mAdStatClickEntryIdForMiniQB = paramString;
  }
  
  public void setAdStatClickKey(String paramString)
  {
    this.mAdStatClickKey = paramString;
  }
  
  public void setAdStatClickPosIdForQB(String paramString)
  {
    this.mAdStatClickPosIdForQB = paramString;
  }
  
  public void setAdWebLoadError(boolean paramBoolean)
  {
    this.mAdWebLoadError = paramBoolean;
  }
  
  public void setAdWebViewLoadFinished(boolean paramBoolean)
  {
    this.mAdWebViewLoadFinished = paramBoolean;
    if (paramBoolean) {
      this.notifyCount = 0;
    }
    if (X5Log.isEnableLog())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("AdWebView--setAdWebViewLoadFinished() = ");
      localStringBuilder.append(paramBoolean);
      Log.e("NativeAdDebug", localStringBuilder.toString());
    }
  }
  
  public void setCurrentAd(boolean paramBoolean)
  {
    this.mCurrentAd = paramBoolean;
  }
  
  public void setHostUrl(String paramString)
  {
    this.hostUrl = paramString;
  }
  
  public void setIsADWebviewBubble(boolean paramBoolean)
  {
    this.misADWebviewBubble = paramBoolean;
  }
  
  public void setIsADWebviewSplice(boolean paramBoolean)
  {
    this.misADWebviewSplice = paramBoolean;
  }
  
  public void setMainWebview(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.mMainWebView = new WeakReference(paramTencentWebViewProxy);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */