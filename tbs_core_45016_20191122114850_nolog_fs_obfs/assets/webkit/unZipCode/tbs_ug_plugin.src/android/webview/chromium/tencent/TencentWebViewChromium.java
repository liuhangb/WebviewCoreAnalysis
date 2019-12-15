package android.webview.chromium.tencent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webview.chromium.ContentSettingsAdapter;
import android.webview.chromium.SharedWebViewChromium;
import android.webview.chromium.WebViewChromium;
import android.webview.chromium.WebViewChromium.InternalAccessAdapter;
import android.webview.chromium.WebViewChromium.WebViewNativeDrawGLFunctorFactory;
import android.webview.chromium.WebViewChromiumFactoryProvider;
import android.widget.TextView;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebBackForwardList;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult.AnchorData;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult.DeepImageData;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult.EditableData;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult.ImageAnchorData;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult.ImageData;
import com.tencent.smtt.jsApi.export.IWebviewNotifyListener;
import com.tencent.smtt.jsApi.export.OpenJsApiBridge;
import com.tencent.smtt.net.X5BadJsReporter;
import com.tencent.smtt.net.X5LogReportJsApi;
import com.tencent.smtt.net.f;
import com.tencent.smtt.os.SMTTAdaptation;
import com.tencent.smtt.util.MttLog;
import com.tencent.smtt.util.X5WebRTCJsHelper;
import com.tencent.smtt.webkit.AdInfoManager;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.WebViewChromiumExtension;
import com.tencent.smtt.webkit.WebViewProviderExtension;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.partner.ad.AdWebView;
import com.tencent.tbs.core.partner.jsextension.JsExtensions;
import com.tencent.tbs.core.webkit.SDKMttTraceEvent;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.WebView.HitTestResult;
import com.tencent.tbs.core.webkit.tencent.TencentWebSettingsProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProvider;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.HitTestResult;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView.PrivateAccess;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Callable;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwContents.DependencyFactory;
import org.chromium.android_webview.AwContents.HitTestData;
import org.chromium.android_webview.AwContentsStatics;
import org.chromium.android_webview.AwSettings;
import org.chromium.components.autofill.AutofillProvider;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.content_public.browser.NavigationHistory;
import org.chromium.tencent.ITencentAwContentsClient;
import org.chromium.tencent.SharedResource;
import org.chromium.tencent.TencentAwContent;
import org.chromium.tencent.TencentAwContent.TencentHitTestData;
import org.chromium.tencent.TencentAwSettings;
import org.chromium.tencent.TencentAwSettings.DayOrNightModeChangeListener;
import org.chromium.tencent.TencentAwSettings.PicModelChangeListener;
import org.json.JSONException;
import org.json.JSONObject;

public class TencentWebViewChromium
  extends WebViewChromium
  implements TencentWebViewProvider, TencentAwSettings.DayOrNightModeChangeListener, TencentAwSettings.PicModelChangeListener
{
  private static final int WEBVIEW_STATE_DESTROY = 3;
  private static final int WEBVIEW_STATE_DETACHED = 4;
  private static final int WEBVIEW_STATE_PAUSE = 1;
  private static final int WEBVIEW_STATE_RESUME = 2;
  private static boolean sRecordWholeDocumentEnabledByApi;
  private JsExtensions jsEx = null;
  private WebViewChromiumExtension mExtension;
  private TencentWebViewChromiumFactoryProvider mFactory;
  private ArrayList<IWebviewNotifyListener> mListeners;
  private OpenJsApiBridge mOpenJsApiBridge = null;
  private TencentWebViewProxy mWebViewProxy;
  private X5LogReportJsApi mX5LogReportJsApi = null;
  private X5WebRTCJsHelper mX5WebRTCJsApi = null;
  private X5BadJsReporter x5BadJsReporter = null;
  private IX5WebViewBase.HitTestResult x5HitTestResult;
  
  public TencentWebViewChromium(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider, TencentWebViewProxy.InnerWebView paramInnerWebView, TencentWebViewProxy.InnerWebView.PrivateAccess paramPrivateAccess, boolean paramBoolean)
  {
    super(paramWebViewChromiumFactoryProvider, paramInnerWebView, paramPrivateAccess, paramBoolean);
    this.mFactory = ((TencentWebViewChromiumFactoryProvider)paramWebViewChromiumFactoryProvider);
    this.mWebViewProxy = paramInnerWebView.getOuterProxy();
    this.mWebViewProxy.setRealWebView(paramInnerWebView);
    this.x5HitTestResult = new WebViewHitTestResult(this.mHitTestResult);
  }
  
  static void completeWindowCreation(TencentWebViewProxy paramTencentWebViewProxy1, TencentWebViewProxy paramTencentWebViewProxy2)
  {
    AwContents localAwContents = ((TencentWebViewChromium)paramTencentWebViewProxy1.getWebViewProvider()).mAwContents;
    if (paramTencentWebViewProxy2 == null) {
      paramTencentWebViewProxy1 = null;
    } else {
      paramTencentWebViewProxy1 = ((TencentWebViewChromium)paramTencentWebViewProxy2.getWebViewProvider()).mAwContents;
    }
    localAwContents.supplyContentsForPopup(paramTencentWebViewProxy1);
    if ((paramTencentWebViewProxy1 != null) && (paramTencentWebViewProxy1.getWebContents() != null)) {
      ((TencentWebViewChromium)paramTencentWebViewProxy2.getWebViewProvider()).mExtension.onReceivedPopupContents();
    }
  }
  
  private void disableThreadChecking() {}
  
  private void initForReal()
  {
    boolean bool;
    if ((!sRecordWholeDocumentEnabledByApi) && (this.mAppTargetSdkVersion >= 21)) {
      bool = false;
    } else {
      bool = true;
    }
    AwContentsStatics.setRecordFullDocument(bool);
    SharedResource.getPerformanceData().put("new_aw_contents_begin", String.valueOf(System.currentTimeMillis()));
    this.mAwContents = new TencentAwContent(this.mFactory.getBrowserContextOnUiThread(), this.mWebViewProxy.getRealWebView(), this.mContext, new WebViewChromium.InternalAccessAdapter(this), new WebViewChromium.WebViewNativeDrawGLFunctorFactory(this), this.mContentsClientAdapter, this.mWebSettings.getAwSettings(), new AwContents.DependencyFactory()
    {
      public AutofillProvider createAutofillProvider(Context paramAnonymousContext, ViewGroup paramAnonymousViewGroup)
      {
        return TencentWebViewChromium.this.mFactory.createAutofillProvider(paramAnonymousContext, TencentWebViewChromium.this.mWebView);
      }
    }, this.mFactory.getTencentBrowserContext(), (ITencentAwContentsClient)this.mContentsClientAdapter);
    SharedResource.getPerformanceData().put("new_aw_contents_end", String.valueOf(System.currentTimeMillis()));
    this.mSharedWebViewChromium.setAwContentsOnUiThread(this.mAwContents);
    if ((this.mAppTargetSdkVersion >= 19) && (WebSettingsExtension.getShouldRequestFavicon())) {
      AwContents.setShouldDownloadFavicons();
    }
    if (this.mAppTargetSdkVersion < 21) {
      this.mAwContents.disableJavascriptInterfacesInspection();
    }
    this.mAwContents.setLayerType(this.mWebView.getLayerType(), null);
    SharedResource.getPerformanceData().put("webviewchromium_extension_begin", String.valueOf(System.currentTimeMillis()));
    this.mExtension = new WebViewChromiumExtension(this.mFactory, this.mWebViewProxy, this, (TencentWebViewContentsClientAdapter)this.mContentsClientAdapter, (TencentAwContent)this.mAwContents);
    SharedResource.getPerformanceData().put("webviewchromium_extension_end", String.valueOf(System.currentTimeMillis()));
  }
  
  public void checkDeepImgClass(AwContents.HitTestData paramHitTestData)
  {
    for (;;)
    {
      try
      {
        Object localObject = this.x5HitTestResult.getClass().getMethod("getDeepImageData", new Class[0]);
        int i;
        if ((localObject != null) && (((Method)localObject).getName().equals("getDeepImageData")))
        {
          localObject = this.x5HitTestResult;
          localObject.getClass();
          localObject = new IX5WebViewBase.HitTestResult.DeepImageData((IX5WebViewBase.HitTestResult)localObject);
          i = this.x5HitTestResult.getType();
          if (i == 5) {}
        }
        switch (i)
        {
        case 7: 
          ((IX5WebViewBase.HitTestResult.DeepImageData)localObject).mPicUrl = paramHitTestData.imgSrc;
          continue;
          ((IX5WebViewBase.HitTestResult.DeepImageData)localObject).mPicUrl = paramHitTestData.imgSrc;
        case 8: 
        case 9: 
          this.x5HitTestResult.setDeepImageData((IX5WebViewBase.HitTestResult.DeepImageData)localObject);
          return;
        }
      }
      catch (Exception paramHitTestData)
      {
        return;
      }
    }
  }
  
  public IX5WebBackForwardList copyBackForwardListTencent()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (IX5WebBackForwardList)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public IX5WebBackForwardList call()
        {
          return TencentWebViewChromium.this.copyBackForwardListTencent();
        }
      });
    }
    NavigationHistory localNavigationHistory2 = this.mAwContents.getNavigationHistory();
    NavigationHistory localNavigationHistory1 = localNavigationHistory2;
    if (localNavigationHistory2 == null) {
      localNavigationHistory1 = new NavigationHistory();
    }
    return new TencentWebBackForwardListChromium(localNavigationHistory1);
  }
  
  public void destroy()
  {
    super.destroy();
    Object localObject = this.mExtension;
    if (localObject != null)
    {
      ((WebViewChromiumExtension)localObject).savePasswordResponse("");
      this.mExtension.cancelFingerSearch(true);
    }
    localObject = this.mOpenJsApiBridge;
    if (localObject != null) {
      ((OpenJsApiBridge)localObject).onWebViewDestroyed();
    }
    SmttServiceProxy.getInstance().onWebViewDestroy();
  }
  
  public void draw(Canvas paramCanvas)
  {
    if ((this.mAwContents instanceof TencentAwContent)) {
      ((TencentAwContent)this.mAwContents).draw(paramCanvas);
    }
  }
  
  public WebViewProviderExtension getExtension()
  {
    return this.mExtension;
  }
  
  public int[] getHitTestResultImgSize()
  {
    TencentAwContent.TencentHitTestData localTencentHitTestData = ((TencentAwContent)this.mAwContents).getLastTencentHitTestResult();
    return new int[] { localTencentHitTestData.hitNodeWidth, localTencentHitTestData.hitNodeHeight };
  }
  
  public int[] getHitTestResultPosition()
  {
    TencentAwContent.TencentHitTestData localTencentHitTestData = ((TencentAwContent)this.mAwContents).getLastTencentHitTestResult();
    return new int[] { localTencentHitTestData.hitNodeX, localTencentHitTestData.hitNodeY };
  }
  
  public IX5WebViewBase.HitTestResult getHitTestResultTencent()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (IX5WebViewBase.HitTestResult)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public IX5WebViewBase.HitTestResult call()
        {
          return TencentWebViewChromium.this.getHitTestResultTencent();
        }
      });
    }
    if (!(this.mAwContents instanceof TencentAwContent)) {
      return null;
    }
    TencentAwContent.TencentHitTestData localTencentHitTestData = ((TencentAwContent)this.mAwContents).getLastTencentHitTestResult();
    if (localTencentHitTestData == null) {
      return null;
    }
    this.x5HitTestResult.setType(localTencentHitTestData.hitTestResultType);
    this.x5HitTestResult.setExtra(localTencentHitTestData.hitTestResultExtraData);
    int i = this.x5HitTestResult.getType();
    Object localObject;
    if (i != 5)
    {
      switch (i)
      {
      default: 
        break;
      case 9: 
        localObject = this.x5HitTestResult;
        localObject.getClass();
        localObject = new IX5WebViewBase.HitTestResult.EditableData((IX5WebViewBase.HitTestResult)localObject);
        ((IX5WebViewBase.HitTestResult.EditableData)localObject).mIsPassword = localTencentHitTestData.isPassword;
        if (!localTencentHitTestData.isPassword) {
          ((IX5WebViewBase.HitTestResult.EditableData)localObject).mEditableText = localTencentHitTestData.hitTestResultExtraData;
        }
        this.x5HitTestResult.setData(localObject);
        break;
      case 8: 
        localObject = this.x5HitTestResult;
        localObject.getClass();
        localObject = new IX5WebViewBase.HitTestResult.ImageAnchorData((IX5WebViewBase.HitTestResult)localObject);
        ((IX5WebViewBase.HitTestResult.ImageAnchorData)localObject).mAHref = localTencentHitTestData.href;
        ((IX5WebViewBase.HitTestResult.ImageAnchorData)localObject).mPicUrl = localTencentHitTestData.imgSrc;
        this.x5HitTestResult.setData(localObject);
        break;
      case 7: 
        localObject = this.x5HitTestResult;
        localObject.getClass();
        localObject = new IX5WebViewBase.HitTestResult.AnchorData((IX5WebViewBase.HitTestResult)localObject);
        ((IX5WebViewBase.HitTestResult.AnchorData)localObject).mAnchorUrl = localTencentHitTestData.href;
        ((IX5WebViewBase.HitTestResult.AnchorData)localObject).mAnchorTitle = localTencentHitTestData.anchorText;
        this.x5HitTestResult.setData(localObject);
        break;
      }
    }
    else
    {
      localObject = this.x5HitTestResult;
      localObject.getClass();
      localObject = new IX5WebViewBase.HitTestResult.ImageData((IX5WebViewBase.HitTestResult)localObject);
      ((IX5WebViewBase.HitTestResult.ImageData)localObject).mPicUrl = localTencentHitTestData.imgSrc;
      try
      {
        ((IX5WebViewBase.HitTestResult.ImageData)localObject).mImgWidth = localTencentHitTestData.imgWidth;
        ((IX5WebViewBase.HitTestResult.ImageData)localObject).mImgHeight = localTencentHitTestData.imgHeight;
      }
      catch (NoSuchFieldError localNoSuchFieldError)
      {
        localNoSuchFieldError.printStackTrace();
      }
      this.x5HitTestResult.setData(localObject);
    }
    checkDeepImgClass(localTencentHitTestData);
    return this.x5HitTestResult;
  }
  
  public OpenJsApiBridge getOpenJsApiBridge()
  {
    return this.mOpenJsApiBridge;
  }
  
  public IX5WebSettings getSettingsTencent()
  {
    return new TencentWebSettingsProxy(this.mWebSettings);
  }
  
  public int getVisibleTitleHeight()
  {
    if ((this.mAwContents != null) && (this.mAwContents.getContentViewCore() != null)) {
      return (int)this.mAwContents.getContentViewCore().getRenderCoordinates().getContentOffsetYPix();
    }
    return 0;
  }
  
  public void init(Map<String, Object> paramMap, final boolean paramBoolean)
  {
    SDKMttTraceEvent.begin("TencentWebViewChromium<init>");
    if (paramBoolean)
    {
      SDKMttTraceEvent.begin("startYourEngines");
      this.mFactory.startYourEngines(true);
      SDKMttTraceEvent.end("startYourEngines");
      if (this.mAppTargetSdkVersion < 19)
      {
        paramMap = new TextView(this.mContext);
        paramMap.setText("Warning shown when the app tries to create a private browsing WebView, which is not supported.");
        this.mWebView.addView(paramMap);
      }
      else
      {
        throw new IllegalArgumentException("Private browsing is not supported in WebView.");
      }
    }
    SharedResource.getPerformanceData().put("startyourengines_begin", String.valueOf(System.currentTimeMillis()));
    SDKMttTraceEvent.begin("startYourEngines1<init>");
    if (this.mAppTargetSdkVersion >= 18)
    {
      this.mFactory.startYourEngines(false);
      checkThread();
    }
    else if ((!this.mFactory.hasStarted()) && (Looper.myLooper() == Looper.getMainLooper()))
    {
      this.mFactory.startYourEngines(true);
    }
    SDKMttTraceEvent.end("startYourEngines1<init>");
    SharedResource.getPerformanceData().put("startyourengines_end", String.valueOf(System.currentTimeMillis()));
    boolean bool1;
    if (this.mAppTargetSdkVersion < 16) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    boolean bool2;
    if (this.mAppTargetSdkVersion <= 23) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    boolean bool3;
    if (this.mAppTargetSdkVersion <= 23) {
      bool3 = true;
    } else {
      bool3 = false;
    }
    boolean bool4;
    if (this.mAppTargetSdkVersion <= 23) {
      bool4 = true;
    } else {
      bool4 = false;
    }
    this.mContentsClientAdapter = this.mFactory.createWebViewContentsClientAdapter(this.mWebViewProxy, this.mContext);
    SDKMttTraceEvent.begin("TencentContentSettingsAdapter");
    paramMap = new TencentAwSettings(this.mContext, bool1, false, bool2, bool3, bool4);
    this.mWebSettings = new TencentContentSettingsAdapter(paramMap);
    SDKMttTraceEvent.end("TencentContentSettingsAdapter");
    paramMap.setDayOrNightModeChangeListener(this);
    paramMap.setPicModelChangeListener(this);
    if (this.mAppTargetSdkVersion < 21)
    {
      this.mWebSettings.setMixedContentMode(0);
      this.mWebSettings.setAcceptThirdPartyCookies(true);
      this.mWebSettings.getAwSettings().setZeroLayoutHeightDisablesViewportQuirk(true);
    }
    if (this.mShouldDisableThreadChecking) {
      disableThreadChecking();
    }
    SharedResource.getPerformanceData().put("chromium_init_for_real_begin", String.valueOf(System.currentTimeMillis()));
    ((TencentAwSettings)this.mWebSettings.getAwSettings()).setFirstScreenDrawFullScreen(SmttServiceProxy.getInstance().getFirstScreenDrawFullScreenEnable());
    ((TencentAwSettings)this.mWebSettings.getAwSettings()).setFirstScreenDrawNotFullScreen(SmttServiceProxy.getInstance().getFirstScreenDrawNotFullScreenEnable());
    ((TencentAwSettings)this.mWebSettings.getAwSettings()).setFirstScreenDetect(SmttServiceProxy.getInstance().getFirstScreenDetectEnable());
    SDKMttTraceEvent.begin("addTask");
    this.mFactory.addTask(new Runnable()
    {
      public void run()
      {
        SDKMttTraceEvent.begin("addTask runnable");
        TencentWebViewChromium.this.initForReal();
        if (paramBoolean) {
          TencentWebViewChromium.this.destroy();
        }
        SDKMttTraceEvent.end("addTask runnable");
      }
    });
    SDKMttTraceEvent.end("addTask");
    SharedResource.getPerformanceData().put("chromium_init_for_real_end", String.valueOf(System.currentTimeMillis()));
    SDKMttTraceEvent.begin("addJavascriptInterface");
    ContextHolder.getInstance().getApplicationContext();
    if (SmttServiceProxy.getInstance().isTbsJsapiEnabled(this.mWebViewProxy.getContext()))
    {
      if (this.mOpenJsApiBridge == null)
      {
        paramMap = this.mWebViewProxy;
        this.mOpenJsApiBridge = new OpenJsApiBridge(paramMap, paramMap.getContext(), this.mWebViewProxy.getContext(), ContextHolder.getInstance().getDexClassLoader());
      }
      addJavascriptInterface(this.mOpenJsApiBridge, "tbs_bridge");
    }
    if (this.mOpenJsApiBridge != null)
    {
      if (this.mListeners == null) {
        this.mListeners = new ArrayList();
      }
      this.mOpenJsApiBridge.setWebviewListener(this.mListeners);
    }
    if (this.jsEx == null) {
      this.jsEx = new JsExtensions(this.mWebViewProxy);
    }
    addJavascriptInterface(this.jsEx, "tbsJs");
    SDKMttTraceEvent.end("addJavascriptInterface");
    SDKMttTraceEvent.begin("setSPDYPreconnect");
    SDKMttTraceEvent.end("setSPDYPreconnect");
    SDKMttTraceEvent.end("TencentWebViewChromium<init>");
    if (e.a().n()) {
      addJavascriptInterface(AdInfoManager.getInstance(), "adfilter");
    }
    if (e.a().n())
    {
      if (this.mX5WebRTCJsApi == null) {
        this.mX5WebRTCJsApi = new X5WebRTCJsHelper(this.mWebViewProxy);
      }
      addJavascriptInterface(this.mX5WebRTCJsApi, "X5WebRTC");
    }
    if (e.a().n())
    {
      if (this.mX5LogReportJsApi == null) {
        this.mX5LogReportJsApi = new X5LogReportJsApi(this.mWebViewProxy);
      }
      addJavascriptInterface(this.mX5LogReportJsApi, "X5LogReportJsApi");
    }
    if (this.x5BadJsReporter == null) {
      this.x5BadJsReporter = new X5BadJsReporter(this.mWebViewProxy);
    }
    addJavascriptInterface(this.x5BadJsReporter, "X5BadJsReporter");
  }
  
  public boolean isFullScreen()
  {
    return this.mAwContents.isFullScreen();
  }
  
  public Bitmap lastHitTestBmp()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (Bitmap)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Bitmap call()
        {
          return TencentWebViewChromium.this.lastHitTestBmp();
        }
      });
    }
    return ((TencentAwContent)this.mAwContents).getLastHitTestResultBitmap();
  }
  
  public void notifyADWebviewVisiableHeight(int paramInt)
  {
    if (this.mListeners == null) {
      return;
    }
    Object localObject2 = "0";
    Object localObject3 = this.mWebViewProxy;
    Object localObject1 = localObject2;
    if ((localObject3 instanceof AdWebView)) {
      if (((AdWebView)localObject3).getIsADWebviewSplice())
      {
        localObject1 = "1";
      }
      else
      {
        localObject1 = localObject2;
        if (((AdWebView)this.mWebViewProxy).getIsADWebviewBubble()) {
          localObject1 = "2";
        }
      }
    }
    localObject1 = "{\"listenName\":\"heightADWebviewVisiable\",\"value\":\"num\",\"webview_type\":\"webview_type_value\"}".replace("num", Integer.toString(paramInt)).replace("webview_type_value", (CharSequence)localObject1);
    if (MttLog.isEnableLog())
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("notifyADWebviewVisiableHeight()--params =");
      ((StringBuilder)localObject2).append((String)localObject1);
      Log.e("NativeAdDebug", ((StringBuilder)localObject2).toString());
    }
    localObject2 = this.mListeners.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      localObject3 = (IWebviewNotifyListener)((Iterator)localObject2).next();
      if (localObject3 != null) {
        ((IWebviewNotifyListener)localObject3).OnWebviewNotify((String)localObject1);
      }
    }
  }
  
  public void notifyFirstScreenFinished(long paramLong1, long paramLong2)
  {
    if (this.mListeners == null) {
      return;
    }
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("{\"listenName\":\"firstScreenTime\",\"value\":\"");
    ((StringBuilder)localObject1).append(paramLong1);
    localObject1 = ((StringBuilder)localObject1).toString();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\",\"timestamp\":\"");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(paramLong2);
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("\"}");
    localObject1 = ((StringBuilder)localObject2).toString();
    localObject2 = this.mListeners.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      IWebviewNotifyListener localIWebviewNotifyListener = (IWebviewNotifyListener)((Iterator)localObject2).next();
      if (localIWebviewNotifyListener != null) {
        localIWebviewNotifyListener.OnWebviewNotify((String)localObject1);
      }
    }
  }
  
  public void notifyMiniQbStatus(String paramString1, String paramString2)
  {
    if (this.mListeners == null) {
      return;
    }
    localObject = "";
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("listenName", "miniQB");
      localJSONObject.put("value", paramString1);
      if (paramString2 != null) {
        localJSONObject.put("extra", paramString2);
      } else {
        localJSONObject.put("extra", "");
      }
      paramString1 = localJSONObject.toString();
    }
    catch (JSONException paramString1)
    {
      for (;;)
      {
        paramString1 = (String)localObject;
      }
    }
    if (TextUtils.isEmpty(paramString1)) {
      return;
    }
    paramString2 = this.mListeners.iterator();
    while (paramString2.hasNext())
    {
      localObject = (IWebviewNotifyListener)paramString2.next();
      if (localObject != null) {
        ((IWebviewNotifyListener)localObject).OnWebviewNotify(paramString1);
      }
    }
  }
  
  public void notifyWebviewState(int paramInt)
  {
    if (this.mListeners == null) {
      return;
    }
    Object localObject1 = "";
    try
    {
      localObject2 = new JSONObject();
      ((JSONObject)localObject2).put("listenName", "stateChange");
      ((JSONObject)localObject2).put("value", paramInt);
      localObject2 = ((JSONObject)localObject2).toString();
      localObject1 = localObject2;
    }
    catch (JSONException localJSONException)
    {
      Object localObject2;
      for (;;) {}
    }
    if (TextUtils.isEmpty((CharSequence)localObject1)) {
      return;
    }
    localObject2 = this.mListeners.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      IWebviewNotifyListener localIWebviewNotifyListener = (IWebviewNotifyListener)((Iterator)localObject2).next();
      if (localIWebviewNotifyListener != null) {
        localIWebviewNotifyListener.OnWebviewNotify((String)localObject1);
      }
    }
  }
  
  public void notifyWindowFocusChanged(boolean paramBoolean)
  {
    if (this.mListeners == null) {
      return;
    }
    Object localObject1 = "";
    try
    {
      localObject2 = new JSONObject();
      ((JSONObject)localObject2).put("listenName", "focusChange");
      ((JSONObject)localObject2).put("value", paramBoolean);
      localObject2 = ((JSONObject)localObject2).toString();
      localObject1 = localObject2;
    }
    catch (JSONException localJSONException)
    {
      Object localObject2;
      for (;;) {}
    }
    if (TextUtils.isEmpty((CharSequence)localObject1)) {
      return;
    }
    localObject2 = this.mListeners.iterator();
    while (((Iterator)localObject2).hasNext())
    {
      IWebviewNotifyListener localIWebviewNotifyListener = (IWebviewNotifyListener)((Iterator)localObject2).next();
      if (localIWebviewNotifyListener != null) {
        localIWebviewNotifyListener.OnWebviewNotify((String)localObject1);
      }
    }
  }
  
  public void onDayOrNightModeChangeListener(boolean paramBoolean)
  {
    this.mWebViewProxy.onDayOrNightChanged(paramBoolean);
  }
  
  public void onDetachedFromWindow()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          TencentWebViewChromium.this.onDetachedFromWindow();
        }
      });
      return;
    }
    Runnable local9 = new Runnable()
    {
      public void run()
      {
        TencentWebViewChromium.this.mAwContents.onDetachedFromWindow();
      }
    };
    if (!SMTTAdaptation.viewExecuteHardwareAction(this.mWebView, local9)) {
      local9.run();
    }
  }
  
  public void onPicModelChangeListener(int paramInt)
  {
    this.mWebViewProxy.onPicModleChanged(paramInt);
  }
  
  public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    IX5WebViewClientExtension localIX5WebViewClientExtension = getExtension().getWebViewClientExtension();
    if (localIX5WebViewClientExtension != null) {
      localIX5WebViewClientExtension.onScrollChanged(paramInt3, paramInt4, paramInt1, paramInt2);
    }
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mExtension.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = super.onTouchEvent(paramMotionEvent);
    if (paramMotionEvent.getActionMasked() == 0)
    {
      int i = paramMotionEvent.getActionIndex();
      this.x5HitTestResult.setHitTestPoint(new Point(Math.round(paramMotionEvent.getX(i)), Math.round(paramMotionEvent.getY(i))));
    }
    if (paramMotionEvent.getActionMasked() != 2) {
      this.mExtension.onNotifyTouchEvent(paramMotionEvent.getActionMasked());
    }
    return bool;
  }
  
  public void onVisibilityChanged(View paramView, int paramInt)
  {
    super.onVisibilityChanged(paramView, paramInt);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
    if ((this.mContentsClientAdapter != null) && (paramBoolean)) {
      f.a().b(this.mContentsClientAdapter.hashCode());
    }
  }
  
  public IX5WebBackForwardList restoreStateTencent(final Bundle paramBundle)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (IX5WebBackForwardList)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public IX5WebBackForwardList call()
        {
          return TencentWebViewChromium.this.restoreStateTencent(paramBundle);
        }
      });
    }
    if (paramBundle == null) {
      return null;
    }
    if (!this.mAwContents.restoreState(paramBundle)) {
      return null;
    }
    return copyBackForwardListTencent();
  }
  
  public IX5WebBackForwardList saveStateTencent(final Bundle paramBundle)
  {
    do
    {
      try
      {
        this.mFactory.startYourEngines(true);
        if (!checkNeedsPost()) {
          continue;
        }
        (IX5WebBackForwardList)this.mFactory.runOnUiThreadBlocking(new Callable()
        {
          public IX5WebBackForwardList call()
          {
            return TencentWebViewChromium.this.saveStateTencent(paramBundle);
          }
        });
      }
      catch (Throwable paramBundle)
      {
        return null;
      }
      if (!this.mAwContents.saveState(paramBundle)) {
        return null;
      }
      paramBundle = copyBackForwardListTencent();
      return paramBundle;
    } while (paramBundle != null);
    return null;
  }
  
  public boolean showFindDialog(String paramString, boolean paramBoolean)
  {
    return false;
  }
  
  private class WebViewHitTestResult
    extends TencentWebViewProxy.HitTestResult
  {
    public WebViewHitTestResult(WebView.HitTestResult paramHitTestResult)
    {
      super();
    }
    
    protected Bitmap getBitmapData()
    {
      if ((TencentWebViewChromium.this.mAwContents instanceof TencentAwContent)) {
        return ((TencentAwContent)TencentWebViewChromium.this.mAwContents).getLastHitTestResultBitmap();
      }
      return null;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\tencent\TencentWebViewChromium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */