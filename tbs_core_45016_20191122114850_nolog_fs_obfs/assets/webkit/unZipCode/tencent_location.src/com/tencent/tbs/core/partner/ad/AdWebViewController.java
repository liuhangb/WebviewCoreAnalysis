package com.tencent.tbs.core.partner.ad;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.MainThreadExecutor;
import com.tencent.common.utils.SignatureUtil;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.internal.interfaces.IAdSettings;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.util.d;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.ProxyWebViewObserver;
import com.tencent.tbs.core.partner.ad.stats.AdStatsInfoRecorder;
import com.tencent.tbs.core.partner.ad.stats.TbsSpecialSiteRecorder;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.chromium.android_webview.AwContentsClient.AwWebResourceError;
import org.chromium.android_webview.AwContentsClient.AwWebResourceRequest;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwWebResourceResponse;

public class AdWebViewController
  extends ProxyWebViewObserver
  implements AdRequest.IAdRequestInterface
{
  private static final String SEPCIALSITE_HOST = "specialSite.com";
  private boolean isAdInfoAvailable = false;
  private boolean isBlockNetworkImage = false;
  private boolean isFirstModeSet = true;
  private boolean isImageMode = false;
  private boolean isNightMode = false;
  private JsExtensionForAd jsExForAd = null;
  private AdInfoUnit mAdInfo = null;
  private AdInfoGenerater mAdInfoGenerater = null;
  private AdRequest mAdRequest = null;
  private AdWebViewAdapter mAdWebviewAdatperBubble = null;
  private AdWebViewAdapter mAdWebviewAdatperBubbleForEncourageWindow = null;
  private AdWebViewAdapter mAdWebviewAdatperSplice = null;
  private ContentInfo mContentInfo;
  private boolean mEncourageWindowShowedFlag = false;
  private int mHasInsertAdInPageStatus = 0;
  private boolean mHasNotifyInstallApk = false;
  private boolean mHasPreloadScreenAd = false;
  private boolean mHasRequestAd = false;
  private boolean mHasRequestSpecialAd = false;
  private boolean mHasShowScreenAd = false;
  private String mInstallPackageName = null;
  private long mInstalltimestamp = -1L;
  private boolean mIsDestroy = false;
  private boolean mIsEncourageWindowShowed = false;
  private boolean mIsErrorFailload = false;
  private boolean mIsHttpErrorFailload = false;
  private float mOldScale = -1.0F;
  private boolean mPageHasShowScreenAd = false;
  private boolean mShouldRefreshAdWebView = false;
  private List<String> mSpecialUrlList = null;
  private TencentWebViewProxy mWebView = null;
  
  public AdWebViewController(TencentWebViewProxy paramTencentWebViewProxy)
  {
    super(paramTencentWebViewProxy.getRealWebView());
    this.mWebView = paramTencentWebViewProxy;
    paramTencentWebViewProxy.addObserver(this);
    if (!(paramTencentWebViewProxy instanceof AdWebView)) {
      paramTencentWebViewProxy.addJavascriptInterface(new JsExtensionForAd(this, paramTencentWebViewProxy), "bubbleAdJs");
    }
  }
  
  private void autoInsertADInPage(String paramString)
  {
    if (!isAdWebView())
    {
      if (this.mHasInsertAdInPageStatus > 0) {
        return;
      }
      if (!SmttServiceProxy.getInstance().canInsertTbsAdInPage(paramString)) {
        return;
      }
      this.mHasInsertAdInPageStatus = 1;
      new Thread(new Runnable()
      {
        public void run()
        {
          if (AdWebViewController.this.mIsDestroy) {
            return;
          }
          Object localObject2 = SmttServiceProxy.getInstance().getTbsAutoInsertAdJS();
          Object localObject1 = localObject2;
          if (localObject2 == null) {
            localObject1 = SmttResource.getTbsAdStream();
          }
          if (localObject1 != null)
          {
            localObject2 = d.a((InputStream)localObject1, "UTF-8").replace("$$ACTIONFORAD", "InsertAdInPage");
            AdWebViewController.this.evaluateJavascript(1, (String)localObject2);
            try
            {
              ((InputStream)localObject1).close();
              return;
            }
            catch (Exception localException)
            {
              localException.printStackTrace();
            }
          }
        }
      }).start();
      return;
    }
  }
  
  private boolean createAdWebview()
  {
    Object localObject = this.mAdInfo;
    if (localObject != null)
    {
      if (TextUtils.isEmpty(AdUtils.getAdUrl(((AdInfoUnit)localObject).getUrl()))) {
        return false;
      }
      if (this.jsExForAd == null) {
        this.jsExForAd = new JsExtensionForAd(this, this.mWebView);
      }
      this.jsExForAd.setWebviewSize(this.mWebView.getContentWidth(), getContentHeight());
      if (((this.mAdInfo.getType() & 0x8) == 8) || ((this.mAdInfo.getType() & 0x2) == 2))
      {
        localObject = this.mAdWebviewAdatperSplice;
        if (localObject == null)
        {
          this.mAdWebviewAdatperSplice = new AdWebViewAdapterSplice(this.mWebView.getContext(), false, this.mAdInfo, this.mWebView, this);
          this.mAdWebviewAdatperSplice.getAdWebView().getRealWebView().addJavascriptInterface(this.jsExForAd, "bubbleAdJs");
        }
        else
        {
          ((AdWebViewAdapter)localObject).updateInfo(this.mAdInfo);
        }
        localObject = this.jsExForAd;
        int i;
        if (this.mAdInfo.getAdPosition() == 2) {
          i = 0;
        } else {
          i = getContentHeight();
        }
        ((JsExtensionForAd)localObject).setAdLocation(0, i);
      }
      if (((this.mAdInfo.getType() & 0x4) == 4) || ((this.mAdInfo.getType() & 0x20) == 32))
      {
        localObject = this.mAdWebviewAdatperBubble;
        if (localObject == null)
        {
          this.mAdWebviewAdatperBubble = new AdWebViewAdapterBubble(this.mWebView.getContext(), false, this.mAdInfo, this.mWebView, this);
          this.mAdWebviewAdatperBubble.getAdWebView().getRealWebView().addJavascriptInterface(this.jsExForAd, "bubbleAdJs");
        }
        else if (localObject != null)
        {
          ((AdWebViewAdapter)localObject).updateInfo(this.mAdInfo);
        }
        localObject = this.jsExForAd;
        float f;
        if (this.mAdInfo.getAdPosition() == 2) {
          f = 0.0F;
        } else {
          f = this.mWebView.getView().getMeasuredHeight() / getScale();
        }
        ((JsExtensionForAd)localObject).setAdLocation(0, (int)f);
      }
      updateAdWebviewModeIfNeeded();
      setAdInfoValide(true);
      return true;
    }
    return false;
  }
  
  private void evaluateJavascript(final int paramInt, final String paramString)
  {
    if (this.mIsDestroy) {
      return;
    }
    BrowserExecutorSupplier.getInstance().getMainThreadExecutor().execute(new Runnable()
    {
      public void run()
      {
        if (AdWebViewController.this.mIsDestroy) {
          return;
        }
        int i = paramInt;
        if (i == 1)
        {
          if (AdWebViewController.this.mHasInsertAdInPageStatus != 1) {
            return;
          }
          AdWebViewController.access$302(AdWebViewController.this, 2);
        }
        else if (i == 2)
        {
          AdStatsInfoRecorder.getInstance().statReinstallTipsEvent(AdStatsInfoRecorder.REINSTALL_EXEC_JS, AdWebViewController.this.mWebView.getUrl(), null);
        }
        AdWebViewController.this.mWebView.evaluateJavascript(paramString, null);
      }
    });
  }
  
  private void hideAd()
  {
    this.mShouldRefreshAdWebView = false;
    this.isAdInfoAvailable = false;
    this.mAdInfo = null;
    this.mContentInfo = null;
    this.mHasRequestAd = false;
    this.mHasInsertAdInPageStatus = 0;
    Object localObject = this.mAdRequest;
    if (localObject != null)
    {
      ((AdRequest)localObject).AbortRequest();
      this.mAdRequest = null;
    }
    setAdInfoValide(false);
    localObject = this.mAdWebviewAdatperBubble;
    if ((localObject != null) && (((AdWebViewAdapter)localObject).getAdWebView().getVisibility() == 0)) {
      this.mAdWebviewAdatperBubble.hideAd();
    }
    localObject = this.mAdWebviewAdatperSplice;
    if ((localObject != null) && (((AdWebViewAdapter)localObject).getAdWebView().getVisibility() == 0)) {
      this.mAdWebviewAdatperSplice.hideAd();
    }
    localObject = this.jsExForAd;
    if (localObject != null) {
      ((JsExtensionForAd)localObject).setWvHeight(0.0F);
    }
  }
  
  private boolean isAdWebViewExist()
  {
    return (this.mAdWebviewAdatperBubble != null) || (this.mAdWebviewAdatperSplice != null);
  }
  
  private void setShouldReloadAdURL(boolean paramBoolean)
  {
    AdInfoUnit localAdInfoUnit;
    if (this.mAdWebviewAdatperBubble != null)
    {
      localAdInfoUnit = this.mAdInfo;
      if ((localAdInfoUnit == null) || ((localAdInfoUnit.getType() & 0x4) == 4)) {
        this.mAdWebviewAdatperBubble.setShouldReloadURL(paramBoolean);
      }
    }
    if (this.mAdWebviewAdatperSplice != null)
    {
      localAdInfoUnit = this.mAdInfo;
      if ((localAdInfoUnit == null) || ((localAdInfoUnit.getType() & 0x8) == 8)) {
        this.mAdWebviewAdatperSplice.setShouldReloadURL(paramBoolean);
      }
    }
  }
  
  private boolean shouldLoadSpecialSiteAd(String paramString)
  {
    if (this.mHasRequestSpecialAd) {
      return false;
    }
    if (paramString == null) {
      return false;
    }
    if (!SmttServiceProxy.getInstance().canInsertAdInSepcialSite()) {
      return false;
    }
    List localList = this.mSpecialUrlList;
    if (localList != null)
    {
      if (!localList.contains(paramString)) {
        return false;
      }
      if (this.mIsErrorFailload)
      {
        TbsSpecialSiteRecorder.upLoadInterceptToBeacon("TBS_SPECIAL_SITE_INTERCEPT_REPORT", paramString, "0", "1", "0");
        return false;
      }
      if (this.mIsHttpErrorFailload)
      {
        TbsSpecialSiteRecorder.upLoadInterceptToBeacon("TBS_SPECIAL_SITE_INTERCEPT_REPORT", paramString, "1", "1", "0");
        return false;
      }
      return true;
    }
    return false;
  }
  
  private void startRequestAd()
  {
    if (this.mHasRequestAd) {
      return;
    }
    if (isAdWebView()) {
      return;
    }
    this.mHasRequestAd = true;
    String str = this.mWebView.getUrl();
    Object localObject = AdInfoHolder.getInstance().getmAdInfoUnit();
    if (AdInfoHolder.getInstance().isAdShouldHide())
    {
      hideAd();
      return;
    }
    if ((localObject != null) && (AdInfoHolder.getInstance().isAdShouldShow()))
    {
      this.isAdInfoAvailable = true;
      setAdInfo(((AdInfoUnit)localObject).getType(), ((AdInfoUnit)localObject).getStatKey(), ((AdInfoUnit)localObject).getUrl(), ((AdInfoUnit)localObject).getUrlMd5(), ((AdInfoUnit)localObject).getShowTime(), ((AdInfoUnit)localObject).getAdId(), ((AdInfoUnit)localObject).getAdPosition(), ((AdInfoUnit)localObject).getOpenStyle());
    }
    if (shouldLoadSpecialSiteAd(TencentURLUtil.getHost(str)))
    {
      if (this.mAdRequest == null) {
        this.mAdRequest = new AdRequest(this);
      }
      this.mAdRequest.RequestAd("specialSite.com");
      this.mHasRequestSpecialAd = true;
      return;
    }
    localObject = this.mAdInfo;
    if ((localObject != null) && (!TextUtils.isEmpty(((AdInfoUnit)localObject).getUrl())))
    {
      this.mShouldRefreshAdWebView = true;
      AdInfoHolder.getInstance().addAdToAdInfoMap(str, this.mAdInfo);
      if (X5Log.isEnableLog())
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("AdWebViewController--createAdWebview. url = ");
        ((StringBuilder)localObject).append(str);
        Log.e("NativeAdDebug", ((StringBuilder)localObject).toString());
      }
      if (!createAdWebview()) {
        return;
      }
      setShouldReloadAdURL(true);
      refreshAdWebview();
      if (X5Log.isEnableLog())
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("AdWebViewController--refreshAdWebview ");
        ((StringBuilder)localObject).append(str);
        Log.e("NativeAdDebug", ((StringBuilder)localObject).toString());
      }
    }
    else if (SmttServiceProxy.getInstance().needRequestTbsAd(str))
    {
      SmttServiceProxy.getInstance().generateTbsAdUserInfo((X5WebViewAdapter)this.mWebView, null, null);
      if (this.mAdRequest == null) {
        this.mAdRequest = new AdRequest(this);
      }
      this.mAdRequest.RequestAd(str);
    }
  }
  
  private void startShowReinstallTips(final String paramString)
  {
    if (e.a().n()) {
      return;
    }
    if (!SmttServiceProxy.getInstance().canShowReinstallTip(paramString)) {
      return;
    }
    new Thread(new Runnable()
    {
      public void run()
      {
        if (AdWebViewController.this.mIsDestroy) {
          return;
        }
        AdStatsInfoRecorder.getInstance().statReinstallTipsEvent(AdStatsInfoRecorder.REINSTALL_LOAD_JS, paramString, "start");
        Object localObject2 = SmttServiceProxy.getInstance().getTbsAdReinstallTipJS();
        Object localObject1 = localObject2;
        if (localObject2 == null) {
          localObject1 = SmttResource.getTbsAdReinstallTipsStream();
        }
        if (localObject1 != null)
        {
          AdStatsInfoRecorder.getInstance().statReinstallTipsEvent(AdStatsInfoRecorder.REINSTALL_LOAD_JS, paramString, "success");
          localObject2 = d.a((InputStream)localObject1, "UTF-8");
          AdWebViewController.this.evaluateJavascript(2, (String)localObject2);
          try
          {
            ((InputStream)localObject1).close();
            return;
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
          }
        }
      }
    }).start();
  }
  
  private void updateAdWebviewModeIfNeeded()
  {
    if (!isAdWebView())
    {
      AdWebViewAdapter localAdWebViewAdapter;
      if ((this.isNightMode != this.mWebView.getSettingsExtension().getDayOrNight()) || (this.isFirstModeSet))
      {
        this.isNightMode = this.mWebView.getSettingsExtension().getDayOrNight();
        localAdWebViewAdapter = this.mAdWebviewAdatperSplice;
        if (localAdWebViewAdapter != null) {
          localAdWebViewAdapter.getAdWebView().getSettingsExtension().setDayOrNight(this.mWebView.getSettingsExtension().getDayOrNight());
        }
        localAdWebViewAdapter = this.mAdWebviewAdatperBubble;
        if (localAdWebViewAdapter != null) {
          localAdWebViewAdapter.getAdWebView().getSettingsExtension().setDayOrNight(this.mWebView.getSettingsExtension().getDayOrNight());
        }
      }
      if ((this.isImageMode != this.mWebView.getSettings().getLoadsImagesAutomatically()) || (this.isFirstModeSet))
      {
        this.isImageMode = this.mWebView.getSettings().getLoadsImagesAutomatically();
        localAdWebViewAdapter = this.mAdWebviewAdatperSplice;
        if (localAdWebViewAdapter != null) {
          localAdWebViewAdapter.getAdWebView().getSettings().setLoadsImagesAutomatically(this.mWebView.getSettings().getLoadsImagesAutomatically());
        }
        localAdWebViewAdapter = this.mAdWebviewAdatperBubble;
        if (localAdWebViewAdapter != null) {
          localAdWebViewAdapter.getAdWebView().getSettings().setLoadsImagesAutomatically(this.mWebView.getSettings().getLoadsImagesAutomatically());
        }
      }
      if ((this.isBlockNetworkImage != this.mWebView.getSettings().getBlockNetworkImage()) || (this.isFirstModeSet))
      {
        localAdWebViewAdapter = this.mAdWebviewAdatperSplice;
        if (localAdWebViewAdapter != null) {
          localAdWebViewAdapter.getAdWebView().getSettings().setBlockNetworkImage(this.mWebView.getSettings().getBlockNetworkImage());
        }
        localAdWebViewAdapter = this.mAdWebviewAdatperBubble;
        if (localAdWebViewAdapter != null) {
          localAdWebViewAdapter.getAdWebView().getSettings().setBlockNetworkImage(this.mWebView.getSettings().getBlockNetworkImage());
        }
      }
      if (isAdWebViewExist()) {
        this.isFirstModeSet = false;
      }
    }
  }
  
  public void callAdWebviewUpdate()
  {
    this.mWebView.getRealWebView().post(new Runnable()
    {
      public void run()
      {
        if (AdWebViewController.this.mIsDestroy) {
          return;
        }
        AdWebViewController.this.refreshAdWebview();
      }
    });
  }
  
  public boolean canShowScreenAdWhenBackEvent()
  {
    if ((this.mHasPreloadScreenAd) && (!this.mPageHasShowScreenAd)) {
      return SmttServiceProxy.getInstance().canShowScreenAdWhenBackEvent(this.mWebView.getContext(), this.mWebView.getUrl(), (X5WebViewAdapter)this.mWebView);
    }
    return false;
  }
  
  public void closeAdWebviewAdatperBubbleForEncourageWindow()
  {
    try
    {
      if (this.mAdWebviewAdatperBubbleForEncourageWindow != null)
      {
        if (this.mAdWebviewAdatperBubbleForEncourageWindow.getAdWebView().getVisibility() == 0) {
          this.mAdWebviewAdatperBubbleForEncourageWindow.getAdWebView().getRealWebView().setVisibility(4);
        }
        this.mAdWebviewAdatperBubbleForEncourageWindow.getAdWebView().getRealWebView().removeAllViews();
        this.mAdWebviewAdatperBubbleForEncourageWindow.getAdWebView().getRealWebView().destroy();
        this.mAdWebviewAdatperBubbleForEncourageWindow = null;
        this.jsExForAd = null;
      }
      this.mIsEncourageWindowShowed = false;
      this.mHasNotifyInstallApk = false;
      return;
    }
    finally {}
  }
  
  public void destroy()
  {
    this.mIsDestroy = true;
    AdWebViewAdapter localAdWebViewAdapter = this.mAdWebviewAdatperBubble;
    if (localAdWebViewAdapter != null) {
      localAdWebViewAdapter.destroy();
    }
    localAdWebViewAdapter = this.mAdWebviewAdatperSplice;
    if (localAdWebViewAdapter != null) {
      localAdWebViewAdapter.destroy();
    }
    this.mWebView.removeObserver(this);
  }
  
  public void generateAdInfo(String paramString)
  {
    if (this.mAdInfoGenerater == null) {
      this.mAdInfoGenerater = new AdInfoGenerater(this);
    }
    this.isAdInfoAvailable = this.mAdInfoGenerater.generateAdInfo(paramString);
    if ((X5Log.isEnableLog()) && (!isAdWebView()))
    {
      paramString = new StringBuilder();
      paramString.append("AdWebviewController--isAdInfoAvailable = ");
      paramString.append(this.isAdInfoAvailable);
      Log.e("NativeAdDebug", paramString.toString());
    }
  }
  
  public IAdSettings getAdSettings()
  {
    return AdInfoHolder.getInstance();
  }
  
  public AdWebViewAdapter getAdWebViewAdapterBubble()
  {
    return this.mAdWebviewAdatperBubble;
  }
  
  public AdWebViewAdapter getAdWebViewAdapterSplice()
  {
    return this.mAdWebviewAdatperSplice;
  }
  
  public ContentInfo getAdWebviewContent()
  {
    ContentInfo localContentInfo = this.mContentInfo;
    this.mContentInfo = null;
    return localContentInfo;
  }
  
  public int getBubbleWvHeightFromJs()
  {
    JsExtensionForAd localJsExtensionForAd = this.jsExForAd;
    if (localJsExtensionForAd != null) {
      return localJsExtensionForAd.getBubbleAdWebViewHeight();
    }
    return 0;
  }
  
  public int getContentHeight()
  {
    return this.mWebView.getRealWebView().getContentHeight();
  }
  
  public boolean getEncourageWindowShowedFlag()
  {
    return this.mEncourageWindowShowedFlag;
  }
  
  public String getInstallPackageName()
  {
    return this.mInstallPackageName;
  }
  
  public String getLocationInWindow()
  {
    int[] arrayOfInt = new int[2];
    this.mWebView.getRealWebView().getLocationInWindow(arrayOfInt);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("x:");
    localStringBuilder.append(arrayOfInt[0]);
    localStringBuilder.append(";y:");
    localStringBuilder.append(arrayOfInt[1]);
    return localStringBuilder.toString();
  }
  
  public String getLocationOnScreen()
  {
    int[] arrayOfInt = new int[2];
    this.mWebView.getRealWebView().getLocationOnScreen(arrayOfInt);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("x:");
    localStringBuilder.append(arrayOfInt[0]);
    localStringBuilder.append(";y:");
    localStringBuilder.append(arrayOfInt[1]);
    return localStringBuilder.toString();
  }
  
  public int getMainX5WebviewMaxOverScrollY()
  {
    return ((X5WebViewAdapter)this.mWebView).getMainX5WebviewMaxOverScrollY();
  }
  
  public String getMiniProgramPkgName()
  {
    return ((X5WebViewAdapter)this.mWebView).getMiniProgramPkgName();
  }
  
  public float getScale()
  {
    return this.mWebView.getRealWebView().getScale();
  }
  
  public int getWebviewHeight()
  {
    return this.mWebView.getRealWebView().getHeight();
  }
  
  public boolean hasShownScreenAd()
  {
    return this.mHasShowScreenAd;
  }
  
  public void initAdWebviewAdatperBubbleForEncourageWindow()
  {
    if (!SmttServiceProxy.getInstance().canShowEncouorageWindow()) {
      return;
    }
    if (this.mWebView.getRealWebView().getHeight() < 420) {
      return;
    }
    this.mIsEncourageWindowShowed = true;
    this.mEncourageWindowShowedFlag = true;
    if (this.mAdWebviewAdatperBubbleForEncourageWindow == null) {
      this.mAdWebviewAdatperBubbleForEncourageWindow = new AdWebViewAdapterBubbleForEncourageWindow(this.mWebView.getContext(), false, this.mWebView, this);
    } else {
      showAdWebviewAdatperBubbleForEncourageWindow();
    }
    if (this.jsExForAd == null) {
      this.jsExForAd = new JsExtensionForAd(this, this.mAdWebviewAdatperBubbleForEncourageWindow.getAdWebView());
    }
    this.mAdWebviewAdatperBubbleForEncourageWindow.getAdWebView().getRealWebView().addJavascriptInterface(this.jsExForAd, "bubbleAdJs");
    this.mWebView.getRealWebView().post(new Runnable()
    {
      public void run()
      {
        AdWebViewController.this.mAdWebviewAdatperBubbleForEncourageWindow.getAdWebView().loadUrl("https://res.imtt.qq.com/res_mtt/m_download_qb/dialog_page/qbload_dialog.html");
      }
    });
    AdStatsInfoRecorder.uploadEncourageWindowEvent("TBS_ENCOURAGE_WINDOW_REPORT", this.mWebView.getContext().getPackageName(), this.mInstallPackageName, "1");
  }
  
  public boolean isAdWebView()
  {
    return (this.mWebView instanceof AdWebView);
  }
  
  public boolean isMiniQB()
  {
    return ((X5WebViewAdapter)this.mWebView).isMiniQB();
  }
  
  public boolean isSpliceAdWebViewVisible()
  {
    AdWebViewAdapter localAdWebViewAdapter = this.mAdWebviewAdatperSplice;
    return (localAdWebViewAdapter != null) && (localAdWebViewAdapter.getAdWebView().getRealWebView().getVisibility() == 0);
  }
  
  public void loadAdWithData(String paramString1, ContentInfo paramContentInfo, String paramString2)
  {
    this.mContentInfo = paramContentInfo;
    if ("0".equals(paramString1))
    {
      hideAd();
      return;
    }
    paramContentInfo = new StringBuilder();
    paramContentInfo.append("pmad=");
    paramContentInfo.append(paramString1);
    generateAdInfo(paramContentInfo.toString());
    if (this.mAdInfo != null)
    {
      createAdWebview();
      this.mShouldRefreshAdWebView = true;
      setShouldReloadAdURL(true);
      refreshAdWebview();
    }
  }
  
  public void notifyADWebviewVisiableHeight(int paramInt)
  {
    AdWebViewAdapter localAdWebViewAdapter = this.mAdWebviewAdatperSplice;
    if ((localAdWebViewAdapter != null) && (localAdWebViewAdapter.getAdWebView().getRealWebView().getVisibility() == 0)) {
      this.mAdWebviewAdatperSplice.getAdWebView().notifyADWebviewVisiableHeight(paramInt);
    }
  }
  
  public void notifyInstallApk(String paramString)
  {
    if (paramString.equals(this.mInstallPackageName)) {
      return;
    }
    this.mInstallPackageName = paramString;
    this.mInstalltimestamp = System.currentTimeMillis();
    this.mHasNotifyInstallApk = true;
  }
  
  public void onBackEvent()
  {
    if (this.mAdWebviewAdatperBubbleForEncourageWindow != null)
    {
      AdStatsInfoRecorder.uploadEncourageWindowEvent("TBS_ENCOURAGE_WINDOW_REPORT", this.mWebView.getContext().getPackageName(), this.mInstallPackageName, "5");
      closeAdWebviewAdatperBubbleForEncourageWindow();
    }
  }
  
  public void onBrowserControlsChanged(int paramInt)
  {
    AdWebViewAdapter localAdWebViewAdapter = this.mAdWebviewAdatperSplice;
    if (localAdWebViewAdapter != null) {
      localAdWebViewAdapter.onBrowserControlsChanged(paramInt);
    }
  }
  
  public void onContentPageSwapIn(String paramString)
  {
    hideAd();
  }
  
  public void onContentsSizeChanged(int paramInt1, int paramInt2)
  {
    if (X5Log.isEnableLog())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("AdWebViewController-onContentsSizeChanged- isAdWebView = ");
      localStringBuilder.append(isAdWebView());
      localStringBuilder.append(", width = ");
      localStringBuilder.append(paramInt1);
      localStringBuilder.append(", height = ");
      localStringBuilder.append(paramInt2);
      localStringBuilder.append(", mShouldRefreshAdWebView=");
      localStringBuilder.append(this.mShouldRefreshAdWebView);
      Log.e("NativeAdDebug", localStringBuilder.toString());
    }
    if (isAdWebView()) {
      return;
    }
    if ((AdInfoHolder.getInstance().getmAdInfoUnit() != null) && (AdInfoHolder.getInstance().isAdShouldHide()))
    {
      setAdInfoValide(false);
      return;
    }
    refreshAdWebview();
  }
  
  public void onDayOrNightChanged(boolean paramBoolean)
  {
    updateAdWebviewModeIfNeeded();
  }
  
  public void onDetectSpecialSite(String paramString, int paramInt)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    if (TbsSpecialSiteRecorder.inSpecialSiteReportRatioControl)
    {
      String str = Integer.toString(paramInt);
      TbsSpecialSiteRecorder.upLoadDetectToBeacon("TBS_SPECIAL_SITE_REPORT", TencentURLUtil.getHost(paramString), str);
    }
    if (isAdWebView()) {
      return;
    }
    if (this.mSpecialUrlList == null) {
      this.mSpecialUrlList = new ArrayList();
    }
    this.mSpecialUrlList.add(TencentURLUtil.getHost(paramString));
    if (!this.mHasRequestSpecialAd) {
      startRequestAd();
    }
  }
  
  public void onFirstScreenTime(long paramLong1, long paramLong2)
  {
    startRequestAd();
  }
  
  public void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!isSpliceAdWebViewVisible()) {
      return;
    }
    float f1 = getScale();
    float f2 = this.mOldScale;
    if (f1 != f2)
    {
      if (f2 != -1.0F) {
        hideAd();
      }
      this.mOldScale = f1;
    }
    paramInt1 = getContentHeight();
    paramInt1 = (int)((int)(paramInt2 + getWebviewHeight() - paramInt1 * getScale()) / getScale());
    if (paramInt1 > 0) {
      notifyADWebviewVisiableHeight(paramInt1);
    }
  }
  
  public void onPageFinished(String paramString)
  {
    AdStatsInfoRecorder.getInstance().sendStatsInfoToTbsAdServer("PAGE_FINISHED", paramString);
    autoInsertADInPage(paramString);
    startShowReinstallTips(paramString);
    SmttServiceProxy.getInstance().onPageLoadFinished(paramString);
    preLoadOrShowScreenAd(this.mWebView.getUrl());
  }
  
  public void onPageStarted(String paramString)
  {
    this.mPageHasShowScreenAd = false;
    this.mHasPreloadScreenAd = false;
    hideAd();
    AdStatsInfoRecorder.getInstance().sendStatsInfoToTbsAdServer("PAGE_STARTED", paramString);
    if (X5Log.isEnableLog())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("AdWebViewController-onPageStarted() isAdWebview = ");
      localStringBuilder.append(isAdWebView());
      localStringBuilder.append(", url = ");
      localStringBuilder.append(paramString);
      Log.e("NativeAdDebug", localStringBuilder.toString());
    }
    SmttServiceProxy.getInstance().onPageLoadStart(paramString);
  }
  
  public void onPicModleChanged(int paramInt)
  {
    updateAdWebviewModeIfNeeded();
  }
  
  public void onProgressChanged(int paramInt)
  {
    if (paramInt < 100) {
      return;
    }
    startRequestAd();
  }
  
  public void onQProxyResponseReceived(String paramString)
  {
    if (isAdWebView()) {
      return;
    }
    generateAdInfo(paramString);
  }
  
  public void onReceivedError(int paramInt, String paramString1, String paramString2)
  {
    if (isAdWebView())
    {
      if (X5Log.isEnableLog())
      {
        paramString1 = new StringBuilder();
        paramString1.append("AdWebViewController--onReceivedError()--errorCode =");
        paramString1.append(paramInt);
        paramString1.append(", failingUrl = ");
        paramString1.append(paramString2);
        Log.e("NativeAdDebug", paramString1.toString());
      }
      if (paramInt < 800) {
        ((AdWebView)this.mWebView).setAdWebLoadError(true);
      }
    }
  }
  
  public void onReceivedError2(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwContentsClient.AwWebResourceError paramAwWebResourceError)
  {
    if ((paramAwWebResourceRequest.isMainFrame) && (paramAwWebResourceRequest.resourceType == 0)) {
      this.mIsErrorFailload = true;
    }
  }
  
  public void onReceivedHttpError(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest, AwWebResourceResponse paramAwWebResourceResponse)
  {
    if ((paramAwWebResourceRequest.isMainFrame) && (paramAwWebResourceRequest.resourceType == 0)) {
      this.mIsHttpErrorFailload = true;
    }
    if (isAdWebView())
    {
      if (X5Log.isEnableLog())
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("AdWebViewController--onReceivedHttpError()--responseCode =");
        localStringBuilder.append(paramAwWebResourceResponse.getStatusCode());
        localStringBuilder.append(", url = ");
        localStringBuilder.append(paramAwWebResourceRequest.url);
        Log.e("NativeAdDebug", localStringBuilder.toString());
      }
      if (paramAwWebResourceResponse.getStatusCode() < 800) {
        ((AdWebView)this.mWebView).setAdWebLoadError(true);
      }
    }
  }
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
  
  public void onUpdateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2)
  {
    if (!isAdWebView()) {
      refreshAdWebview();
    }
  }
  
  public void onWebViewDestroyed() {}
  
  @TargetApi(7)
  public void onWebViewVisibilityChanged(Context paramContext, View paramView, int paramInt)
  {
    if ((paramInt == 0) && (this.mHasNotifyInstallApk) && (System.currentTimeMillis() - this.mInstalltimestamp < 20000L) && (SignatureUtil.getInstalledPKGInfo(this.mInstallPackageName, paramContext, 0) == null) && (!this.mIsEncourageWindowShowed)) {
      initAdWebviewAdatperBubbleForEncourageWindow();
    }
  }
  
  public void onWindowVisibilityChanged(int paramInt)
  {
    if (paramInt != 0)
    {
      resetAdWebviewCurrentFlag(false);
      return;
    }
    resetAdWebviewCurrentFlag(true);
  }
  
  public void preLoadOrShowScreenAd(String paramString)
  {
    if (SmttServiceProxy.getInstance().canShowScreenAdWhenBackEvent(this.mWebView.getContext(), paramString, (X5WebViewAdapter)this.mWebView))
    {
      if (this.mHasPreloadScreenAd) {
        return;
      }
      if (SmttServiceProxy.getInstance().preLoadScreenAd(this.mWebView.getContext(), paramString) >= 0) {
        this.mHasPreloadScreenAd = true;
      }
      return;
    }
    if (!SmttServiceProxy.getInstance().canShowScreenAd(this.mWebView.getContext(), paramString, (X5WebViewAdapter)this.mWebView)) {
      return;
    }
    showScreenAd(paramString, false);
  }
  
  public void refreshAdWebview()
  {
    if ((this.mShouldRefreshAdWebView) && (this.isAdInfoAvailable))
    {
      AdInfoUnit localAdInfoUnit;
      if (this.mAdWebviewAdatperSplice != null)
      {
        localAdInfoUnit = this.mAdInfo;
        if ((localAdInfoUnit == null) || ((localAdInfoUnit.getType() & 0x8) == 8)) {
          this.mAdWebviewAdatperSplice.refreshAdWebview();
        }
      }
      if (this.mAdWebviewAdatperBubble != null)
      {
        localAdInfoUnit = this.mAdInfo;
        if ((localAdInfoUnit == null) || ((localAdInfoUnit.getType() & 0x4) == 4)) {
          this.mAdWebviewAdatperBubble.refreshAdWebview();
        }
      }
    }
  }
  
  public void resetAdWebviewCurrentFlag(boolean paramBoolean)
  {
    Object localObject;
    if (X5Log.isEnableLog())
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("AdWebViewController--resetAdWebviewCurrentFlag() isCurWebview = ");
      ((StringBuilder)localObject).append(paramBoolean);
      Log.e("NativeAdDebug", ((StringBuilder)localObject).toString());
    }
    if (this.mAdWebviewAdatperSplice != null)
    {
      localObject = this.mAdInfo;
      if ((localObject == null) || ((((AdInfoUnit)localObject).getType() & 0x8) == 8)) {
        this.mAdWebviewAdatperSplice.getAdWebView().setCurrentAd(paramBoolean);
      }
    }
    if (this.mAdWebviewAdatperBubble != null)
    {
      localObject = this.mAdInfo;
      if ((localObject == null) || ((((AdInfoUnit)localObject).getType() & 0x4) == 4)) {
        this.mAdWebviewAdatperBubble.getAdWebView().setCurrentAd(paramBoolean);
      }
    }
  }
  
  public void resetBubbleWvHeightFromJs()
  {
    JsExtensionForAd localJsExtensionForAd = this.jsExForAd;
    if (localJsExtensionForAd != null) {
      localJsExtensionForAd.SetBubbleAdWebViewHeight(0);
    }
  }
  
  public void sendCloseEncourageWindowMsg()
  {
    BrowserExecutorSupplier.getInstance().getMainThreadExecutor().execute(new Runnable()
    {
      public void run()
      {
        AdWebViewController.this.closeAdWebviewAdatperBubbleForEncourageWindow();
      }
    });
  }
  
  public void setAdInfo(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4, int paramInt3, int paramInt4)
  {
    int i = paramInt1;
    if (i == 16)
    {
      if (this.mWebView.getWebViewClientExtension() != null)
      {
        if (X5Log.isEnableLog()) {
          Log.e("NativeAdDebug", "AdWebViewController--setAdInfo Type = 16");
        }
        this.mWebView.getWebViewClientExtension().onMiscCallBack("notifyQBiconShine", null);
        SmttServiceProxy.getInstance().userBehaviorStatistics("AGND1");
      }
      return;
    }
    paramInt1 = i;
    if (i == 128) {
      paramInt1 = 136;
    }
    Object localObject = this.mAdInfo;
    if (localObject == null)
    {
      this.mAdInfo = new AdInfoUnit(paramInt1, paramString1, paramString2, paramString3, paramInt2, paramString4, paramInt3, paramInt4);
      i = paramInt1;
      paramInt1 = paramInt2;
    }
    else
    {
      i = paramInt1;
      if (paramInt1 == 0) {
        i = ((AdInfoUnit)localObject).getType();
      }
      if (TextUtils.isEmpty(paramString2)) {
        paramString2 = this.mAdInfo.getUrl();
      }
      if (TextUtils.isEmpty(paramString3)) {
        paramString3 = this.mAdInfo.getUrlMd5();
      }
      if (paramInt2 == 0) {
        paramInt1 = this.mAdInfo.getShowTime();
      } else {
        paramInt1 = paramInt2;
      }
      if (TextUtils.isEmpty(paramString4)) {
        paramString4 = this.mAdInfo.getAdId();
      }
      if (paramInt3 == 0) {
        paramInt2 = this.mAdInfo.getAdPosition();
      } else {
        paramInt2 = paramInt3;
      }
      if (paramInt4 == 0) {
        paramInt3 = this.mAdInfo.getOpenStyle();
      } else {
        paramInt3 = paramInt4;
      }
      this.mAdInfo.updateAdInfo(i, paramString1, paramString2, paramString3, paramInt1, paramString4, paramInt2, paramInt3);
      if (((i & 0x4) == 4) || (i == 32))
      {
        localObject = this.mAdWebviewAdatperBubble;
        if (localObject != null) {
          ((AdWebViewAdapter)localObject).updateInfo(this.mAdInfo);
        }
      }
      if (((i & 0x8) == 8) || (i == 2))
      {
        localObject = this.mAdWebviewAdatperSplice;
        if (localObject != null) {
          ((AdWebViewAdapter)localObject).updateInfo(this.mAdInfo);
        }
      }
      paramInt4 = paramInt3;
      paramInt3 = paramInt2;
    }
    AdInfoHolder.getInstance().setCurrentAdInfo(i, paramString1, paramString2, paramString3, paramInt1, paramString4, paramInt3, paramInt4);
    if ("0".equals(paramString2)) {
      setAdInfoValide(false);
    }
    if (X5Log.isEnableLog())
    {
      paramString1 = new StringBuilder();
      paramString1.append("AdWebViewController--setAdInfo = ");
      paramString1.append(this.mAdInfo.toString());
      Log.e("NativeAdDebug", paramString1.toString());
    }
  }
  
  public void setAdInfoValide(boolean paramBoolean)
  {
    Object localObject;
    if (X5Log.isEnableLog())
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("AdWebViewController--setAdInfoInvalide() -- invalide = ");
      ((StringBuilder)localObject).append(paramBoolean);
      Log.e("NativeAdDebug", ((StringBuilder)localObject).toString());
    }
    if (this.mAdWebviewAdatperSplice != null)
    {
      localObject = this.mAdInfo;
      if ((localObject == null) || ((((AdInfoUnit)localObject).getType() & 0x8) == 8)) {
        this.mAdWebviewAdatperSplice.setAdInfoValid(paramBoolean);
      }
    }
    if (this.mAdWebviewAdatperBubble != null)
    {
      localObject = this.mAdInfo;
      if ((localObject == null) || ((((AdInfoUnit)localObject).getType() & 0x4) == 4)) {
        this.mAdWebviewAdatperBubble.setAdInfoValid(paramBoolean);
      }
    }
  }
  
  public void setEncourageWindowShowedFlag(boolean paramBoolean)
  {
    this.mEncourageWindowShowedFlag = paramBoolean;
  }
  
  public void setHasShownScreenAd(boolean paramBoolean)
  {
    this.mPageHasShowScreenAd = paramBoolean;
    this.mHasShowScreenAd = paramBoolean;
  }
  
  public void showAdWebviewAdatperBubbleForEncourageWindow()
  {
    AdWebViewAdapter localAdWebViewAdapter = this.mAdWebviewAdatperBubbleForEncourageWindow;
    if ((localAdWebViewAdapter != null) && (localAdWebViewAdapter.getAdWebView().getVisibility() == 4)) {
      this.mAdWebviewAdatperBubbleForEncourageWindow.getAdWebView().getRealWebView().setVisibility(0);
    }
  }
  
  public boolean showScreenAd(String paramString, boolean paramBoolean)
  {
    if (SmttServiceProxy.getInstance().showScreenAd(this.mWebView.getContext(), (X5WebViewAdapter)this.mWebView, null, paramBoolean, true) >= 0)
    {
      this.mHasPreloadScreenAd = false;
      return true;
    }
    return false;
  }
  
  public void snapshotVisibleWithBitmap(Bitmap paramBitmap, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, float paramFloat1, float paramFloat2)
  {
    AdWebViewAdapter localAdWebViewAdapter = this.mAdWebviewAdatperSplice;
    if (localAdWebViewAdapter != null) {
      try
      {
        localAdWebViewAdapter.snapshotVisibleWithBitmap(paramBitmap, paramBoolean1, paramBoolean2, paramBoolean3, paramFloat1, paramFloat2);
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    SmttServiceProxy.getInstance().snapshotScreenAd(paramBitmap, paramFloat1, paramFloat2, (X5WebViewAdapter)this.mWebView);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdWebViewController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */