package android.webview.chromium;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.print.PrintDocumentAdapter;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textclassifier.TextClassifier;
import android.widget.TextView;
import com.tencent.tbs.core.webkit.DownloadListener;
import com.tencent.tbs.core.webkit.FindActionModeCallback;
import com.tencent.tbs.core.webkit.ValueCallback;
import com.tencent.tbs.core.webkit.WebBackForwardList;
import com.tencent.tbs.core.webkit.WebChromeClient;
import com.tencent.tbs.core.webkit.WebChromeClient.CustomViewCallback;
import com.tencent.tbs.core.webkit.WebMessage;
import com.tencent.tbs.core.webkit.WebMessagePort;
import com.tencent.tbs.core.webkit.WebSettings;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.WebView.FindListener;
import com.tencent.tbs.core.webkit.WebView.HitTestResult;
import com.tencent.tbs.core.webkit.WebView.PictureListener;
import com.tencent.tbs.core.webkit.WebView.PrivateAccess;
import com.tencent.tbs.core.webkit.WebView.VisualStateCallback;
import com.tencent.tbs.core.webkit.WebViewClient;
import com.tencent.tbs.core.webkit.WebViewProvider;
import com.tencent.tbs.core.webkit.WebViewProvider.ScrollDelegate;
import com.tencent.tbs.core.webkit.WebViewProvider.ViewDelegate;
import java.io.BufferedWriter;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.Callable;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwContents.DependencyFactory;
import org.chromium.android_webview.AwContents.HitTestData;
import org.chromium.android_webview.AwContents.InternalAccessDelegate;
import org.chromium.android_webview.AwContents.NativeDrawGLFunctor;
import org.chromium.android_webview.AwContents.NativeDrawGLFunctorFactory;
import org.chromium.android_webview.AwContents.VisualStateCallback;
import org.chromium.android_webview.AwContentsStatics;
import org.chromium.android_webview.AwPrintDocumentAdapter;
import org.chromium.android_webview.AwSettings;
import org.chromium.android_webview.ResourcesContextWrapperFactory;
import org.chromium.base.BuildInfo;
import org.chromium.base.ThreadUtils;
import org.chromium.components.autofill.AutofillProvider;
import org.chromium.content_public.browser.NavigationHistory;
import org.chromium.content_public.browser.SmartClipProvider;

public class WebViewChromium
  implements WebViewProvider, WebViewProvider.ScrollDelegate, WebViewProvider.ViewDelegate, SmartClipProvider
{
  protected static final String TAG = "WebViewChromium";
  private static boolean sRecordWholeDocumentEnabledByApi;
  protected final int mAppTargetSdkVersion;
  public AwContents mAwContents;
  protected WebViewContentsClientAdapter mContentsClientAdapter;
  protected Context mContext;
  protected WebViewChromiumFactoryProvider mFactory;
  protected final WebView.HitTestResult mHitTestResult;
  protected final SharedWebViewChromium mSharedWebViewChromium;
  protected final boolean mShouldDisableThreadChecking;
  protected ContentSettingsAdapter mWebSettings;
  protected WebView mWebView;
  WebView.PrivateAccess mWebViewPrivate;
  
  public WebViewChromium(WebViewChromiumFactoryProvider paramWebViewChromiumFactoryProvider, WebView paramWebView, WebView.PrivateAccess paramPrivateAccess, boolean paramBoolean)
  {
    WebViewChromiumFactoryProvider.checkStorageIsNotDeviceProtected(paramWebView.getContext());
    this.mWebView = paramWebView;
    this.mWebViewPrivate = paramPrivateAccess;
    this.mHitTestResult = new WebView.HitTestResult();
    this.mContext = ResourcesContextWrapperFactory.get(this.mWebView.getContext());
    this.mAppTargetSdkVersion = this.mContext.getApplicationInfo().targetSdkVersion;
    this.mFactory = paramWebViewChromiumFactoryProvider;
    this.mShouldDisableThreadChecking = paramBoolean;
    paramWebViewChromiumFactoryProvider.getWebViewDelegate().addWebViewAssetPath(this.mWebView.getContext());
    this.mSharedWebViewChromium = new SharedWebViewChromium(this.mFactory.getRunQueue(), this.mFactory.getAwInit());
  }
  
  static void completeWindowCreation(WebView paramWebView1, WebView paramWebView2)
  {
    AwContents localAwContents = ((WebViewChromium)paramWebView1.getWebViewProvider()).mAwContents;
    if (paramWebView2 == null) {
      paramWebView1 = null;
    } else {
      paramWebView1 = ((WebViewChromium)paramWebView2.getWebViewProvider()).mAwContents;
    }
    localAwContents.supplyContentsForPopup(paramWebView1);
  }
  
  private RuntimeException createThreadException()
  {
    return new IllegalStateException("Calling View methods on another thread than the UI thread.");
  }
  
  private void disableThreadChecking()
  {
    try
    {
      Field localField = Class.forName("android.webkit.WebView").getDeclaredField("sEnforceThreadChecking");
      localField.setAccessible(true);
      localField.setBoolean(null, false);
      localField.setAccessible(false);
      return;
    }
    catch (ClassNotFoundException|NoSuchFieldException|IllegalAccessException|IllegalArgumentException localClassNotFoundException) {}
  }
  
  private boolean doesSupportFullscreen(WebChromeClient paramWebChromeClient)
  {
    boolean bool2 = false;
    if (paramWebChromeClient == null) {
      return false;
    }
    paramWebChromeClient = paramWebChromeClient.getClass();
    k = 0;
    for (i = 0; (paramWebChromeClient != WebChromeClient.class) && ((k == 0) || (i == 0)); i = m)
    {
      j = k;
      if (k == 0) {}
      try
      {
        paramWebChromeClient.getDeclaredMethod("onShowCustomView", new Class[] { View.class, WebChromeClient.CustomViewCallback.class });
        j = 1;
      }
      catch (NoSuchMethodException localNoSuchMethodException1)
      {
        for (;;)
        {
          j = k;
        }
      }
      m = i;
      if (i == 0) {}
      try
      {
        paramWebChromeClient.getDeclaredMethod("onHideCustomView", new Class[0]);
        m = 1;
      }
      catch (NoSuchMethodException localNoSuchMethodException2)
      {
        for (;;)
        {
          boolean bool1;
          m = i;
        }
      }
      paramWebChromeClient = paramWebChromeClient.getSuperclass();
      k = j;
    }
    bool1 = bool2;
    if (k != 0)
    {
      bool1 = bool2;
      if (i != 0) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static void enableSlowWholeDocumentDraw()
  {
    sRecordWholeDocumentEnabledByApi = true;
  }
  
  private void initForReal()
  {
    boolean bool;
    if ((!sRecordWholeDocumentEnabledByApi) && (this.mAppTargetSdkVersion >= 21)) {
      bool = false;
    } else {
      bool = true;
    }
    AwContentsStatics.setRecordFullDocument(bool);
    this.mAwContents = new AwContents(this.mFactory.getBrowserContextOnUiThread(), this.mWebView, this.mContext, new InternalAccessAdapter(), new WebViewNativeDrawGLFunctorFactory(), this.mContentsClientAdapter, this.mWebSettings.getAwSettings(), new AwContents.DependencyFactory()
    {
      public AutofillProvider createAutofillProvider(Context paramAnonymousContext, ViewGroup paramAnonymousViewGroup)
      {
        return WebViewChromium.this.mFactory.createAutofillProvider(paramAnonymousContext, WebViewChromium.this.mWebView);
      }
    });
    this.mSharedWebViewChromium.setAwContentsOnUiThread(this.mAwContents);
    if (this.mAppTargetSdkVersion >= 19) {
      AwContents.setShouldDownloadFavicons();
    }
    if (this.mAppTargetSdkVersion < 21) {
      this.mAwContents.disableJavascriptInterfacesInspection();
    }
    this.mAwContents.setLayerType(this.mWebView.getLayerType(), null);
  }
  
  public void addJavascriptInterface(final Object paramObject, final String paramString)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.addJavascriptInterface(paramObject, paramString);
        }
      });
      return;
    }
    this.mAwContents.addJavascriptInterface(paramObject, paramString);
  }
  
  public void autofill(final SparseArray<AutofillValue> paramSparseArray)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      this.mFactory.runVoidTaskOnUiThreadBlocking(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.autofill(paramSparseArray);
        }
      });
    }
    this.mAwContents.autofill(paramSparseArray);
  }
  
  public boolean canGoBack()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.canGoBack());
        }
      })).booleanValue();
    }
    return this.mAwContents.canGoBack();
  }
  
  public boolean canGoBackOrForward(final int paramInt)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.canGoBackOrForward(paramInt));
        }
      })).booleanValue();
    }
    return this.mAwContents.canGoBackOrForward(paramInt);
  }
  
  public boolean canGoForward()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.canGoForward());
        }
      })).booleanValue();
    }
    return this.mAwContents.canGoForward();
  }
  
  public boolean canZoomIn()
  {
    if (checkNeedsPost()) {
      return false;
    }
    return this.mAwContents.canZoomIn();
  }
  
  public boolean canZoomOut()
  {
    if (checkNeedsPost()) {
      return false;
    }
    return this.mAwContents.canZoomOut();
  }
  
  public Picture capturePicture()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (Picture)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Picture call()
        {
          return WebViewChromium.this.capturePicture();
        }
      });
    }
    return this.mAwContents.capturePicture();
  }
  
  protected boolean checkNeedsPost()
  {
    return this.mSharedWebViewChromium.checkNeedsPost();
  }
  
  protected void checkThread()
  {
    if (ThreadUtils.runningOnUiThread()) {
      return;
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        throw this.val$threadViolation;
      }
    });
    throw createThreadException();
  }
  
  public void clearCache(final boolean paramBoolean)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.clearCache(paramBoolean);
        }
      });
      return;
    }
    this.mAwContents.clearCache(paramBoolean);
  }
  
  public void clearFormData()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.clearFormData();
        }
      });
      return;
    }
    this.mAwContents.hideAutofillPopup();
  }
  
  public void clearHistory()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.clearHistory();
        }
      });
      return;
    }
    this.mAwContents.clearHistory();
  }
  
  public void clearMatches()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.clearMatches();
        }
      });
      return;
    }
    this.mAwContents.clearMatches();
  }
  
  public void clearSslPreferences()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.clearSslPreferences();
        }
      });
      return;
    }
    this.mAwContents.clearSslPreferences();
  }
  
  public void clearView()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.clearView();
        }
      });
      return;
    }
    this.mAwContents.clearView();
  }
  
  public int computeHorizontalScrollOffset()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Integer)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Integer call()
        {
          return Integer.valueOf(WebViewChromium.this.computeHorizontalScrollOffset());
        }
      })).intValue();
    }
    return this.mAwContents.computeHorizontalScrollOffset();
  }
  
  public int computeHorizontalScrollRange()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Integer)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Integer call()
        {
          return Integer.valueOf(WebViewChromium.this.computeHorizontalScrollRange());
        }
      })).intValue();
    }
    return this.mAwContents.computeHorizontalScrollRange();
  }
  
  public void computeScroll()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost())
    {
      this.mFactory.runVoidTaskOnUiThreadBlocking(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.computeScroll();
        }
      });
      return;
    }
    this.mAwContents.computeScroll();
  }
  
  public int computeVerticalScrollExtent()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Integer)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Integer call()
        {
          return Integer.valueOf(WebViewChromium.this.computeVerticalScrollExtent());
        }
      })).intValue();
    }
    return this.mAwContents.computeVerticalScrollExtent();
  }
  
  public int computeVerticalScrollOffset()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Integer)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Integer call()
        {
          return Integer.valueOf(WebViewChromium.this.computeVerticalScrollOffset());
        }
      })).intValue();
    }
    return this.mAwContents.computeVerticalScrollOffset();
  }
  
  public int computeVerticalScrollRange()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Integer)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Integer call()
        {
          return Integer.valueOf(WebViewChromium.this.computeVerticalScrollRange());
        }
      })).intValue();
    }
    return this.mAwContents.computeVerticalScrollRange();
  }
  
  public WebBackForwardList copyBackForwardList()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (WebBackForwardList)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public WebBackForwardList call()
        {
          return WebViewChromium.this.copyBackForwardList();
        }
      });
    }
    NavigationHistory localNavigationHistory2 = this.mAwContents.getNavigationHistory();
    NavigationHistory localNavigationHistory1 = localNavigationHistory2;
    if (localNavigationHistory2 == null) {
      localNavigationHistory1 = new NavigationHistory();
    }
    return new WebBackForwardListChromium(localNavigationHistory1);
  }
  
  public PrintDocumentAdapter createPrintDocumentAdapter(String paramString)
  {
    checkThread();
    return new AwPrintDocumentAdapter(this.mAwContents.getPdfExporter(), paramString);
  }
  
  public WebMessagePort[] createWebMessageChannel()
  {
    return WebMessagePortAdapter.fromMessagePorts(this.mSharedWebViewChromium.createWebMessageChannel());
  }
  
  public void destroy()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.destroy();
        }
      });
      return;
    }
    this.mContentsClientAdapter.setWebChromeClient(null);
    this.mContentsClientAdapter.setWebViewClient(null);
    this.mContentsClientAdapter.setPictureListener(null, true);
    this.mContentsClientAdapter.setFindListener(null);
    this.mContentsClientAdapter.setDownloadListener(null);
    this.mAwContents.destroy();
  }
  
  public boolean dispatchKeyEvent(final KeyEvent paramKeyEvent)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.dispatchKeyEvent(paramKeyEvent));
        }
      })).booleanValue();
    }
    return this.mAwContents.dispatchKeyEvent(paramKeyEvent);
  }
  
  public void documentHasImages(final Message paramMessage)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.documentHasImages(paramMessage);
        }
      });
      return;
    }
    this.mAwContents.documentHasImages(paramMessage);
  }
  
  public void dumpViewHierarchyWithProperties(BufferedWriter paramBufferedWriter, int paramInt) {}
  
  public void evaluateJavaScript(final String paramString, final ValueCallback<String> paramValueCallback)
  {
    if ((this.mShouldDisableThreadChecking) && (checkNeedsPost()))
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.mAwContents.evaluateJavaScript(paramString, CallbackConverter.fromValueCallback(paramValueCallback));
        }
      });
      return;
    }
    checkThread();
    this.mAwContents.evaluateJavaScript(paramString, CallbackConverter.fromValueCallback(paramValueCallback));
  }
  
  public void extractSmartClipData(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    checkThread();
    this.mAwContents.extractSmartClipData(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public int findAll(String paramString)
  {
    findAllAsync(paramString);
    return 0;
  }
  
  public void findAllAsync(final String paramString)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.findAllAsync(paramString);
        }
      });
      return;
    }
    this.mAwContents.findAllAsync(paramString);
  }
  
  public View findFocus(View paramView)
  {
    return paramView;
  }
  
  public View findHierarchyView(String paramString, int paramInt)
  {
    return null;
  }
  
  public void findNext(final boolean paramBoolean)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.findNext(paramBoolean);
        }
      });
      return;
    }
    this.mAwContents.findNext(paramBoolean);
  }
  
  public void flingScroll(final int paramInt1, final int paramInt2)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.flingScroll(paramInt1, paramInt2);
        }
      });
      return;
    }
    this.mAwContents.flingScroll(paramInt1, paramInt2);
  }
  
  public void freeMemory() {}
  
  public AccessibilityNodeProvider getAccessibilityNodeProvider()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      (AccessibilityNodeProvider)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public AccessibilityNodeProvider call()
        {
          return WebViewChromium.this.getAccessibilityNodeProvider();
        }
      });
    }
    return this.mAwContents.getAccessibilityNodeProvider();
  }
  
  public SslCertificate getCertificate()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (SslCertificate)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public SslCertificate call()
        {
          return WebViewChromium.this.getCertificate();
        }
      });
    }
    return this.mAwContents.getCertificate();
  }
  
  public int getContentHeight()
  {
    AwContents localAwContents = this.mAwContents;
    if (localAwContents == null) {
      return 0;
    }
    return localAwContents.getContentHeightCss();
  }
  
  public int getContentWidth()
  {
    AwContents localAwContents = this.mAwContents;
    if (localAwContents == null) {
      return 0;
    }
    return localAwContents.getContentWidthCss();
  }
  
  public Bitmap getFavicon()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (Bitmap)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Bitmap call()
        {
          return WebViewChromium.this.getFavicon();
        }
      });
    }
    return this.mAwContents.getFavicon();
  }
  
  public Handler getHandler(Handler paramHandler)
  {
    return paramHandler;
  }
  
  public WebView.HitTestResult getHitTestResult()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (WebView.HitTestResult)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public WebView.HitTestResult call()
        {
          return WebViewChromium.this.getHitTestResult();
        }
      });
    }
    AwContents.HitTestData localHitTestData = this.mAwContents.getLastHitTestResult();
    this.mHitTestResult.setType(localHitTestData.hitTestResultType);
    this.mHitTestResult.setExtra(localHitTestData.hitTestResultExtraData);
    return this.mHitTestResult;
  }
  
  public String[] getHttpAuthUsernamePassword(final String paramString1, final String paramString2)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (String[])this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public String[] call()
        {
          return WebViewChromium.this.getHttpAuthUsernamePassword(paramString1, paramString2);
        }
      });
    }
    return ((WebViewDatabaseAdapter)this.mFactory.getWebViewDatabase(this.mContext)).getHttpAuthUsernamePassword(paramString1, paramString2);
  }
  
  public String getOriginalUrl()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (String)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public String call()
        {
          return WebViewChromium.this.getOriginalUrl();
        }
      });
    }
    return this.mAwContents.getOriginalUrl();
  }
  
  public int getProgress()
  {
    AwContents localAwContents = this.mAwContents;
    if (localAwContents == null) {
      return 100;
    }
    return localAwContents.getMostRecentProgress();
  }
  
  public boolean getRendererPriorityWaivedWhenNotVisible()
  {
    return this.mAwContents.getRendererPriorityWaivedWhenNotVisible();
  }
  
  public float getScale()
  {
    this.mFactory.startYourEngines(true);
    return this.mAwContents.getScale();
  }
  
  public WebViewProvider.ScrollDelegate getScrollDelegate()
  {
    return this;
  }
  
  public WebSettings getSettings()
  {
    return this.mWebSettings;
  }
  
  SharedWebViewChromium getSharedWebViewChromium()
  {
    return this.mSharedWebViewChromium;
  }
  
  public TextClassifier getTextClassifier()
  {
    return this.mAwContents.getTextClassifier();
  }
  
  public String getTitle()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (String)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public String call()
        {
          return WebViewChromium.this.getTitle();
        }
      });
    }
    return this.mAwContents.getTitle();
  }
  
  public String getTouchIconUrl()
  {
    return null;
  }
  
  public String getUrl()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (String)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public String call()
        {
          return WebViewChromium.this.getUrl();
        }
      });
    }
    return this.mAwContents.getUrl();
  }
  
  public WebViewProvider.ViewDelegate getViewDelegate()
  {
    return this;
  }
  
  public int getVisibleTitleHeight()
  {
    return 0;
  }
  
  public WebChromeClient getWebChromeClient()
  {
    return this.mContentsClientAdapter.getWebChromeClient();
  }
  
  public WebViewClient getWebViewClient()
  {
    return this.mContentsClientAdapter.getWebViewClient();
  }
  
  public View getZoomControls()
  {
    this.mFactory.startYourEngines(false);
    boolean bool = checkNeedsPost();
    View localView = null;
    if (bool) {
      return null;
    }
    if (this.mAwContents.getSettings().supportZoom()) {
      localView = new View(this.mContext);
    }
    return localView;
  }
  
  public void goBack()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.goBack();
        }
      });
      return;
    }
    this.mAwContents.goBack();
  }
  
  public void goBackOrForward(final int paramInt)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.goBackOrForward(paramInt);
        }
      });
      return;
    }
    this.mAwContents.goBackOrForward(paramInt);
  }
  
  public void goForward()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.goForward();
        }
      });
      return;
    }
    this.mAwContents.goForward();
  }
  
  public void init(Map<String, Object> paramMap, final boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mFactory.startYourEngines(true);
      if (this.mAppTargetSdkVersion < 19)
      {
        paramMap = new TextView(this.mContext);
        this.mWebView.addView(paramMap);
      }
      else
      {
        throw new IllegalArgumentException("Private browsing is not supported in WebView.");
      }
    }
    if (this.mAppTargetSdkVersion >= 18)
    {
      this.mFactory.startYourEngines(false);
      checkThread();
    }
    else if ((!this.mFactory.hasStarted()) && (Looper.myLooper() == Looper.getMainLooper()))
    {
      this.mFactory.startYourEngines(true);
    }
    boolean bool1;
    if (this.mAppTargetSdkVersion < 16) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    boolean bool2;
    if (this.mAppTargetSdkVersion < 19) {
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
    boolean bool5;
    if (this.mAppTargetSdkVersion <= 23) {
      bool5 = true;
    } else {
      bool5 = false;
    }
    this.mContentsClientAdapter = this.mFactory.createWebViewContentsClientAdapter(this.mWebView, this.mContext);
    this.mWebSettings = new ContentSettingsAdapter(new AwSettings(this.mContext, bool1, bool2, bool3, bool4, bool5));
    if (this.mAppTargetSdkVersion < 21)
    {
      this.mWebSettings.setMixedContentMode(0);
      this.mWebSettings.setAcceptThirdPartyCookies(true);
      this.mWebSettings.getAwSettings().setZeroLayoutHeightDisablesViewportQuirk(true);
    }
    if (BuildInfo.targetsAtLeastP())
    {
      this.mWebSettings.getAwSettings().setCSSHexAlphaColorEnabled(true);
      this.mWebSettings.getAwSettings().setScrollTopLeftInteropEnabled(true);
    }
    if (this.mShouldDisableThreadChecking) {
      disableThreadChecking();
    }
    this.mFactory.addTask(new Runnable()
    {
      public void run()
      {
        WebViewChromium.this.initForReal();
        if (paramBoolean) {
          WebViewChromium.this.destroy();
        }
      }
    });
  }
  
  @TargetApi(23)
  public void insertVisualStateCallback(long paramLong, final WebView.VisualStateCallback paramVisualStateCallback)
  {
    this.mSharedWebViewChromium.insertVisualStateCallback(paramLong, new AwContents.VisualStateCallback()
    {
      public void onComplete(long paramAnonymousLong)
      {
        paramVisualStateCallback.onComplete(paramAnonymousLong);
      }
    });
  }
  
  public void invokeZoomPicker()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.invokeZoomPicker();
        }
      });
      return;
    }
    this.mAwContents.invokeZoomPicker();
  }
  
  public boolean isPaused()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.isPaused());
        }
      })).booleanValue();
    }
    return this.mAwContents.isPaused();
  }
  
  public boolean isPrivateBrowsingEnabled()
  {
    return false;
  }
  
  public void loadData(final String paramString1, final String paramString2, final String paramString3)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.mAwContents.loadData(paramString1, paramString2, paramString3);
        }
      });
      return;
    }
    this.mAwContents.loadData(paramString1, paramString2, paramString3);
  }
  
  public void loadDataWithBaseURL(final String paramString1, final String paramString2, final String paramString3, final String paramString4, final String paramString5)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.mAwContents.loadDataWithBaseURL(paramString1, paramString2, paramString3, paramString4, paramString5);
        }
      });
      return;
    }
    this.mAwContents.loadDataWithBaseURL(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public void loadUrl(final String paramString)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.mAwContents.loadUrl(paramString);
        }
      });
      return;
    }
    this.mAwContents.loadUrl(paramString);
  }
  
  public void loadUrl(final String paramString, final Map<String, String> paramMap)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.mAwContents.loadUrl(paramString, paramMap);
        }
      });
      return;
    }
    this.mAwContents.loadUrl(paramString, paramMap);
  }
  
  public void notifyFindDialogDismissed()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.notifyFindDialogDismissed();
        }
      });
      return;
    }
    clearMatches();
  }
  
  public void onActivityResult(final int paramInt1, final int paramInt2, final Intent paramIntent)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onActivityResult(paramInt1, paramInt2, paramIntent);
        }
      });
      return;
    }
    this.mAwContents.onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public void onAttachedToWindow()
  {
    this.mFactory.startYourEngines(false);
    checkThread();
    this.mAwContents.onAttachedToWindow();
  }
  
  public boolean onCheckIsTextEditor()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.onCheckIsTextEditor());
        }
      })).booleanValue();
    }
    return this.mAwContents.onCheckIsTextEditor();
  }
  
  public void onConfigurationChanged(final Configuration paramConfiguration)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onConfigurationChanged(paramConfiguration);
        }
      });
      return;
    }
    this.mAwContents.onConfigurationChanged(paramConfiguration);
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      return null;
    }
    return this.mAwContents.onCreateInputConnection(paramEditorInfo);
  }
  
  public void onDetachedFromWindow()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onDetachedFromWindow();
        }
      });
      return;
    }
    this.mAwContents.onDetachedFromWindow();
  }
  
  public boolean onDragEvent(final DragEvent paramDragEvent)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.onDragEvent(paramDragEvent));
        }
      })).booleanValue();
    }
    return this.mAwContents.onDragEvent(paramDragEvent);
  }
  
  @SuppressLint({"DrawAllocation"})
  public void onDraw(final Canvas paramCanvas)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost())
    {
      this.mFactory.runVoidTaskOnUiThreadBlocking(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onDraw(paramCanvas);
        }
      });
      return;
    }
    this.mAwContents.onDraw(paramCanvas);
  }
  
  public void onDrawVerticalScrollBar(Canvas paramCanvas, Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mWebViewPrivate.super_onDrawVerticalScrollBar(paramCanvas, paramDrawable, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void onFinishTemporaryDetach()
  {
    this.mAwContents.onFinishTemporaryDetach();
  }
  
  public void onFocusChanged(final boolean paramBoolean, final int paramInt, final Rect paramRect)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onFocusChanged(paramBoolean, paramInt, paramRect);
        }
      });
      return;
    }
    this.mAwContents.onFocusChanged(paramBoolean, paramInt, paramRect);
  }
  
  public boolean onGenericMotionEvent(final MotionEvent paramMotionEvent)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.onGenericMotionEvent(paramMotionEvent));
        }
      })).booleanValue();
    }
    return this.mAwContents.onGenericMotionEvent(paramMotionEvent);
  }
  
  public boolean onHoverEvent(final MotionEvent paramMotionEvent)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.onHoverEvent(paramMotionEvent));
        }
      })).booleanValue();
    }
    return this.mAwContents.onHoverEvent(paramMotionEvent);
  }
  
  public void onInitializeAccessibilityEvent(AccessibilityEvent paramAccessibilityEvent) {}
  
  public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo paramAccessibilityNodeInfo) {}
  
  public boolean onKeyDown(final int paramInt, final KeyEvent paramKeyEvent)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.onKeyDown(paramInt, paramKeyEvent));
        }
      })).booleanValue();
    }
    return false;
  }
  
  public boolean onKeyMultiple(final int paramInt1, final int paramInt2, final KeyEvent paramKeyEvent)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.onKeyMultiple(paramInt1, paramInt2, paramKeyEvent));
        }
      })).booleanValue();
    }
    return false;
  }
  
  public boolean onKeyUp(final int paramInt, final KeyEvent paramKeyEvent)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.onKeyUp(paramInt, paramKeyEvent));
        }
      })).booleanValue();
    }
    return this.mAwContents.onKeyUp(paramInt, paramKeyEvent);
  }
  
  @SuppressLint({"DrawAllocation"})
  public void onMeasure(final int paramInt1, final int paramInt2)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost())
    {
      this.mFactory.runVoidTaskOnUiThreadBlocking(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onMeasure(paramInt1, paramInt2);
        }
      });
      return;
    }
    this.mAwContents.onMeasure(paramInt1, paramInt2);
  }
  
  public void onOverScrolled(final int paramInt1, final int paramInt2, final boolean paramBoolean1, final boolean paramBoolean2)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
        }
      });
      return;
    }
    this.mAwContents.onContainerViewOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
  }
  
  public void onPause()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onPause();
        }
      });
      return;
    }
    this.mAwContents.onPause();
  }
  
  public void onProvideAutofillVirtualStructure(final ViewStructure paramViewStructure, final int paramInt)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost())
    {
      this.mFactory.runVoidTaskOnUiThreadBlocking(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onProvideAutofillVirtualStructure(paramViewStructure, paramInt);
        }
      });
      return;
    }
    this.mAwContents.onProvideAutoFillVirtualStructure(paramViewStructure, paramInt);
  }
  
  @TargetApi(23)
  public void onProvideVirtualStructure(final ViewStructure paramViewStructure)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost())
    {
      this.mFactory.runVoidTaskOnUiThreadBlocking(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onProvideVirtualStructure(paramViewStructure);
        }
      });
      return;
    }
    this.mAwContents.onProvideVirtualStructure(paramViewStructure);
  }
  
  public void onResume()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onResume();
        }
      });
      return;
    }
    this.mAwContents.onResume();
  }
  
  public void onScrollChanged(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
        }
      });
      return;
    }
    this.mAwContents.onContainerViewScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void onSizeChanged(final int paramInt1, final int paramInt2, final int paramInt3, final int paramInt4)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
        }
      });
      return;
    }
    this.mAwContents.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void onStartTemporaryDetach()
  {
    this.mAwContents.onStartTemporaryDetach();
  }
  
  public boolean onTouchEvent(final MotionEvent paramMotionEvent)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.onTouchEvent(paramMotionEvent));
        }
      })).booleanValue();
    }
    return this.mAwContents.onTouchEvent(paramMotionEvent);
  }
  
  public boolean onTrackballEvent(MotionEvent paramMotionEvent)
  {
    return false;
  }
  
  public void onVisibilityChanged(final View paramView, final int paramInt)
  {
    if (this.mAwContents == null) {
      return;
    }
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onVisibilityChanged(paramView, paramInt);
        }
      });
      return;
    }
    this.mAwContents.onVisibilityChanged(paramView, paramInt);
  }
  
  public void onWindowFocusChanged(final boolean paramBoolean)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onWindowFocusChanged(paramBoolean);
        }
      });
      return;
    }
    this.mAwContents.onWindowFocusChanged(paramBoolean);
  }
  
  public void onWindowVisibilityChanged(final int paramInt)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.onWindowVisibilityChanged(paramInt);
        }
      });
      return;
    }
    this.mAwContents.onWindowVisibilityChanged(paramInt);
  }
  
  public boolean overlayHorizontalScrollbar()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.overlayHorizontalScrollbar());
        }
      })).booleanValue();
    }
    return this.mAwContents.overlayHorizontalScrollbar();
  }
  
  public boolean overlayVerticalScrollbar()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.overlayVerticalScrollbar());
        }
      })).booleanValue();
    }
    return this.mAwContents.overlayVerticalScrollbar();
  }
  
  public boolean pageDown(final boolean paramBoolean)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.pageDown(paramBoolean));
        }
      })).booleanValue();
    }
    return this.mAwContents.pageDown(paramBoolean);
  }
  
  public boolean pageUp(final boolean paramBoolean)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.pageUp(paramBoolean));
        }
      })).booleanValue();
    }
    return this.mAwContents.pageUp(paramBoolean);
  }
  
  public void pauseTimers()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.pauseTimers();
        }
      });
      return;
    }
    this.mAwContents.pauseTimers();
  }
  
  public boolean performAccessibilityAction(final int paramInt, final Bundle paramBundle)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.performAccessibilityAction(paramInt, paramBundle));
        }
      })).booleanValue();
    }
    if (this.mAwContents.supportsAccessibilityAction(paramInt)) {
      return this.mAwContents.performAccessibilityAction(paramInt, paramBundle);
    }
    return this.mWebViewPrivate.super_performAccessibilityAction(paramInt, paramBundle);
  }
  
  public boolean performLongClick()
  {
    if (this.mWebView.getParent() != null) {
      return this.mWebViewPrivate.super_performLongClick();
    }
    return false;
  }
  
  @TargetApi(23)
  public void postMessageToMainFrame(WebMessage paramWebMessage, Uri paramUri)
  {
    this.mSharedWebViewChromium.postMessageToMainFrame(paramWebMessage.getData(), paramUri.toString(), WebMessagePortAdapter.toMessagePorts(paramWebMessage.getPorts()));
  }
  
  public void postUrl(final String paramString, final byte[] paramArrayOfByte)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.mAwContents.postUrl(paramString, paramArrayOfByte);
        }
      });
      return;
    }
    this.mAwContents.postUrl(paramString, paramArrayOfByte);
  }
  
  public void preDispatchDraw(Canvas paramCanvas) {}
  
  public void reload()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.reload();
        }
      });
      return;
    }
    this.mAwContents.reload();
  }
  
  public void removeJavascriptInterface(final String paramString)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.removeJavascriptInterface(paramString);
        }
      });
      return;
    }
    this.mAwContents.removeJavascriptInterface(paramString);
  }
  
  public boolean requestChildRectangleOnScreen(final View paramView, final Rect paramRect, final boolean paramBoolean)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.requestChildRectangleOnScreen(paramView, paramRect, paramBoolean));
        }
      })).booleanValue();
    }
    return this.mAwContents.requestChildRectangleOnScreen(paramView, paramRect, paramBoolean);
  }
  
  public boolean requestFocus(final int paramInt, final Rect paramRect)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.requestFocus(paramInt, paramRect));
        }
      })).booleanValue();
    }
    this.mAwContents.requestFocus();
    return this.mWebViewPrivate.super_requestFocus(paramInt, paramRect);
  }
  
  public void requestFocusNodeHref(final Message paramMessage)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.requestFocusNodeHref(paramMessage);
        }
      });
      return;
    }
    this.mAwContents.requestFocusNodeHref(paramMessage);
  }
  
  public void requestImageRef(final Message paramMessage)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.requestImageRef(paramMessage);
        }
      });
      return;
    }
    this.mAwContents.requestImageRef(paramMessage);
  }
  
  public boolean restorePicture(Bundle paramBundle, File paramFile)
  {
    return false;
  }
  
  public WebBackForwardList restoreState(final Bundle paramBundle)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (WebBackForwardList)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public WebBackForwardList call()
        {
          return WebViewChromium.this.restoreState(paramBundle);
        }
      });
    }
    if (paramBundle == null) {
      return null;
    }
    if (!this.mAwContents.restoreState(paramBundle)) {
      return null;
    }
    return copyBackForwardList();
  }
  
  public void resumeTimers()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.resumeTimers();
        }
      });
      return;
    }
    this.mAwContents.resumeTimers();
  }
  
  public void savePassword(String paramString1, String paramString2, String paramString3) {}
  
  public boolean savePicture(Bundle paramBundle, File paramFile)
  {
    return false;
  }
  
  public WebBackForwardList saveState(final Bundle paramBundle)
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      (WebBackForwardList)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public WebBackForwardList call()
        {
          return WebViewChromium.this.saveState(paramBundle);
        }
      });
    }
    if (paramBundle == null) {
      return null;
    }
    if (!this.mAwContents.saveState(paramBundle)) {
      return null;
    }
    return copyBackForwardList();
  }
  
  public void saveWebArchive(String paramString)
  {
    saveWebArchive(paramString, false, null);
  }
  
  public void saveWebArchive(final String paramString, final boolean paramBoolean, final ValueCallback<String> paramValueCallback)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.saveWebArchive(paramString, paramBoolean, paramValueCallback);
        }
      });
      return;
    }
    this.mAwContents.saveWebArchive(paramString, paramBoolean, CallbackConverter.fromValueCallback(paramValueCallback));
  }
  
  public void setBackgroundColor(final int paramInt)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost())
    {
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.setBackgroundColor(paramInt);
        }
      });
      return;
    }
    this.mAwContents.setBackgroundColor(paramInt);
  }
  
  public void setCertificate(SslCertificate paramSslCertificate) {}
  
  public void setDownloadListener(DownloadListener paramDownloadListener)
  {
    this.mContentsClientAdapter.setDownloadListener(paramDownloadListener);
  }
  
  public void setFindListener(WebView.FindListener paramFindListener)
  {
    this.mContentsClientAdapter.setFindListener(paramFindListener);
  }
  
  public boolean setFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return this.mWebViewPrivate.super_setFrame(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void setHorizontalScrollbarOverlay(final boolean paramBoolean)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.setHorizontalScrollbarOverlay(paramBoolean);
        }
      });
      return;
    }
    this.mAwContents.setHorizontalScrollbarOverlay(paramBoolean);
  }
  
  public void setHttpAuthUsernamePassword(final String paramString1, final String paramString2, final String paramString3, final String paramString4)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.setHttpAuthUsernamePassword(paramString1, paramString2, paramString3, paramString4);
        }
      });
      return;
    }
    ((WebViewDatabaseAdapter)this.mFactory.getWebViewDatabase(this.mContext)).setHttpAuthUsernamePassword(paramString1, paramString2, paramString3, paramString4);
  }
  
  public void setInitialScale(int paramInt)
  {
    this.mWebSettings.getAwSettings().setInitialPageScale(paramInt);
  }
  
  public void setLayerType(final int paramInt, final Paint paramPaint)
  {
    if (this.mAwContents == null) {
      return;
    }
    if (checkNeedsPost())
    {
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.setLayerType(paramInt, paramPaint);
        }
      });
      return;
    }
    this.mAwContents.setLayerType(paramInt, paramPaint);
  }
  
  public void setLayoutParams(final ViewGroup.LayoutParams paramLayoutParams)
  {
    this.mFactory.startYourEngines(false);
    checkThread();
    this.mWebViewPrivate.super_setLayoutParams(paramLayoutParams);
    if (checkNeedsPost())
    {
      this.mFactory.runVoidTaskOnUiThreadBlocking(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.mAwContents.setLayoutParams(paramLayoutParams);
        }
      });
      return;
    }
    this.mAwContents.setLayoutParams(paramLayoutParams);
  }
  
  public void setMapTrackballToArrowKeys(boolean paramBoolean) {}
  
  public void setNetworkAvailable(final boolean paramBoolean)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.setNetworkAvailable(paramBoolean);
        }
      });
      return;
    }
    this.mAwContents.setNetworkAvailable(paramBoolean);
  }
  
  public void setOverScrollMode(final int paramInt)
  {
    if (this.mAwContents == null) {
      return;
    }
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.setOverScrollMode(paramInt);
        }
      });
      return;
    }
    this.mAwContents.setOverScrollMode(paramInt);
  }
  
  public void setPictureListener(final WebView.PictureListener paramPictureListener)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.setPictureListener(paramPictureListener);
        }
      });
      return;
    }
    int i = this.mAppTargetSdkVersion;
    boolean bool2 = true;
    boolean bool1;
    if (i >= 18) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mContentsClientAdapter.setPictureListener(paramPictureListener, bool1);
    AwContents localAwContents = this.mAwContents;
    if (paramPictureListener == null) {
      bool2 = false;
    }
    localAwContents.enableOnNewPicture(bool2, bool1);
  }
  
  public void setScrollBarStyle(final int paramInt)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.setScrollBarStyle(paramInt);
        }
      });
      return;
    }
    this.mAwContents.setScrollBarStyle(paramInt);
  }
  
  public void setSmartClipResultHandler(Handler paramHandler)
  {
    checkThread();
    this.mAwContents.setSmartClipResultHandler(paramHandler);
  }
  
  public void setTextClassifier(TextClassifier paramTextClassifier)
  {
    this.mAwContents.setTextClassifier(paramTextClassifier);
  }
  
  public void setVerticalScrollbarOverlay(final boolean paramBoolean)
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.setVerticalScrollbarOverlay(paramBoolean);
        }
      });
      return;
    }
    this.mAwContents.setVerticalScrollbarOverlay(paramBoolean);
  }
  
  public void setWebChromeClient(WebChromeClient paramWebChromeClient)
  {
    this.mWebSettings.getAwSettings().setFullscreenSupported(doesSupportFullscreen(paramWebChromeClient));
    this.mContentsClientAdapter.setWebChromeClient(paramWebChromeClient);
  }
  
  public void setWebViewClient(WebViewClient paramWebViewClient)
  {
    this.mContentsClientAdapter.setWebViewClient(paramWebViewClient);
  }
  
  public boolean shouldDelayChildPressedState()
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.shouldDelayChildPressedState());
        }
      })).booleanValue();
    }
    return true;
  }
  
  public boolean showFindDialog(String paramString, boolean paramBoolean)
  {
    this.mFactory.startYourEngines(false);
    if (checkNeedsPost()) {
      return false;
    }
    if (this.mWebView.getParent() == null) {
      return false;
    }
    FindActionModeCallback localFindActionModeCallback = new FindActionModeCallback(this.mContext);
    this.mWebView.startActionMode(localFindActionModeCallback);
    localFindActionModeCallback.setWebView(this.mWebView);
    if (paramBoolean) {
      localFindActionModeCallback.showSoftInput();
    }
    if (paramString != null)
    {
      localFindActionModeCallback.setText(paramString);
      localFindActionModeCallback.findAll();
    }
    return true;
  }
  
  public void stopLoading()
  {
    if (checkNeedsPost())
    {
      this.mFactory.addTask(new Runnable()
      {
        public void run()
        {
          WebViewChromium.this.stopLoading();
        }
      });
      return;
    }
    this.mAwContents.stopLoading();
  }
  
  public boolean zoomBy(float paramFloat)
  {
    this.mFactory.startYourEngines(true);
    checkThread();
    this.mAwContents.zoomBy(paramFloat);
    return true;
  }
  
  public boolean zoomIn()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.zoomIn());
        }
      })).booleanValue();
    }
    return this.mAwContents.zoomIn();
  }
  
  public boolean zoomOut()
  {
    this.mFactory.startYourEngines(true);
    if (checkNeedsPost()) {
      ((Boolean)this.mFactory.runOnUiThreadBlocking(new Callable()
      {
        public Boolean call()
        {
          return Boolean.valueOf(WebViewChromium.this.zoomOut());
        }
      })).booleanValue();
    }
    return this.mAwContents.zoomOut();
  }
  
  public class InternalAccessAdapter
    implements AwContents.InternalAccessDelegate
  {
    public InternalAccessAdapter() {}
    
    public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {}
    
    public void overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
    {
      WebViewChromium.this.mWebViewPrivate.overScrollBy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
    }
    
    public void setMeasuredDimension(int paramInt1, int paramInt2)
    {
      WebViewChromium.this.mWebViewPrivate.setMeasuredDimension(paramInt1, paramInt2);
    }
    
    public boolean super_awakenScrollBars(int paramInt, boolean paramBoolean)
    {
      return false;
    }
    
    public boolean super_dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      return WebViewChromium.this.mWebViewPrivate.super_dispatchKeyEvent(paramKeyEvent);
    }
    
    public int super_getScrollBarStyle()
    {
      return WebViewChromium.this.mWebViewPrivate.super_getScrollBarStyle();
    }
    
    public void super_onConfigurationChanged(Configuration paramConfiguration) {}
    
    public boolean super_onGenericMotionEvent(MotionEvent paramMotionEvent)
    {
      return WebViewChromium.this.mWebViewPrivate.super_onGenericMotionEvent(paramMotionEvent);
    }
    
    public boolean super_onHoverEvent(MotionEvent paramMotionEvent)
    {
      return WebViewChromium.this.mWebViewPrivate.super_onHoverEvent(paramMotionEvent);
    }
    
    public boolean super_onKeyUp(int paramInt, KeyEvent paramKeyEvent)
    {
      return false;
    }
    
    public void super_scrollTo(int paramInt1, int paramInt2)
    {
      WebViewChromium.this.mWebViewPrivate.super_scrollTo(paramInt1, paramInt2);
    }
    
    public void super_startActivityForResult(Intent paramIntent, int paramInt)
    {
      if (Build.VERSION.SDK_INT >= 24)
      {
        WebViewChromium.this.mWebViewPrivate.super_startActivityForResult(paramIntent, paramInt);
        return;
      }
      try
      {
        View.class.getMethod("startActivityForResult", new Class[] { Intent.class, Integer.TYPE }).invoke(WebViewChromium.this.mWebView, new Object[] { paramIntent, Integer.valueOf(paramInt) });
        return;
      }
      catch (Exception paramIntent)
      {
        throw new RuntimeException("Invalid reflection", paramIntent);
      }
    }
  }
  
  public class WebViewNativeDrawGLFunctorFactory
    implements AwContents.NativeDrawGLFunctorFactory
  {
    public WebViewNativeDrawGLFunctorFactory() {}
    
    public AwContents.NativeDrawGLFunctor createFunctor(long paramLong)
    {
      return new DrawGLFunctor(paramLong, WebViewChromium.this.mFactory.getWebViewDelegate());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\android\webview\chromium\WebViewChromium.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */