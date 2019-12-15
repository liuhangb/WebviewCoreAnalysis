package com.tencent.tbs.core.webkit;

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
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.print.PrintDocumentAdapter;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.OnHierarchyChangeListener;
import android.view.ViewHierarchyEncoder;
import android.view.ViewStructure;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import java.io.BufferedWriter;
import java.io.File;
import java.util.Map;

public class WebView
  extends AbsoluteLayout
  implements ViewGroup.OnHierarchyChangeListener, ViewTreeObserver.OnGlobalFocusChangeListener, ViewDebug.HierarchyHandler
{
  public static final String DATA_REDUCTION_PROXY_SETTING_CHANGED = "android.webkit.DATA_REDUCTION_PROXY_SETTING_CHANGED";
  private static final String LOGTAG = "WebView";
  public static final String SCHEME_GEO = "geo:0,0?q=";
  public static final String SCHEME_MAILTO = "mailto:";
  public static final String SCHEME_TEL = "tel:";
  private static volatile boolean sEnforceThreadChecking = false;
  private FindListenerDistributor mFindListener;
  private WebViewProvider mProvider;
  private final Looper mWebViewThread = Looper.myLooper();
  
  public WebView(Context paramContext)
  {
    this(paramContext, null);
  }
  
  public WebView(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 16842885);
  }
  
  public WebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    this(paramContext, paramAttributeSet, paramInt, 0);
  }
  
  public WebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2)
  {
    this(paramContext, paramAttributeSet, paramInt1, paramInt2, null, false);
  }
  
  protected WebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt1, int paramInt2, Map<String, Object> paramMap, boolean paramBoolean)
  {
    super(paramContext, paramAttributeSet, paramInt1, paramInt2);
    if (paramContext != null)
    {
      SDKMttTraceEvent.begin("WebView.<init>");
      boolean bool;
      if (paramContext.getApplicationInfo().targetSdkVersion >= 18) {
        bool = true;
      } else {
        bool = false;
      }
      sEnforceThreadChecking = bool;
      checkThread();
      SDKMttTraceEvent.begin("ensureProviderCreated");
      ensureProviderCreated();
      SDKMttTraceEvent.end("ensureProviderCreated");
      SDKMttTraceEvent.begin("Provider.init");
      this.mProvider.init(paramMap, paramBoolean);
      SDKMttTraceEvent.end("Provider.init");
      CookieSyncManager.setGetInstanceIsAllowed();
      SDKMttTraceEvent.end("WebView.<init>");
      return;
    }
    throw new IllegalArgumentException("Invalid context argument");
  }
  
  protected WebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt, Map<String, Object> paramMap, boolean paramBoolean)
  {
    this(paramContext, paramAttributeSet, paramInt, 0, paramMap, paramBoolean);
  }
  
  @Deprecated
  public WebView(Context paramContext, AttributeSet paramAttributeSet, int paramInt, boolean paramBoolean)
  {
    this(paramContext, paramAttributeSet, paramInt, 0, null, paramBoolean);
  }
  
  public static void clearClientCertPreferences(Runnable paramRunnable)
  {
    getFactory().getStatics().clearClientCertPreferences(paramRunnable);
  }
  
  @Deprecated
  public static void disablePlatformNotifications() {}
  
  @Deprecated
  public static void enablePlatformNotifications() {}
  
  public static void enableSlowWholeDocumentDraw()
  {
    getFactory().getStatics().enableSlowWholeDocumentDraw();
  }
  
  private void ensureProviderCreated()
  {
    checkThread();
    if (this.mProvider == null) {
      this.mProvider = getFactory().createWebView(this, new PrivateAccess());
    }
  }
  
  public static String findAddress(String paramString)
  {
    return getFactory().getStatics().findAddress(paramString);
  }
  
  public static void freeMemoryForTests()
  {
    getFactory().getStatics().freeMemoryForTests();
  }
  
  private static WebViewFactoryProvider getFactory()
  {
    try
    {
      WebViewFactoryProvider localWebViewFactoryProvider = WebViewFactory.getProvider();
      return localWebViewFactoryProvider;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  @Deprecated
  public static PluginList getPluginList()
  {
    try
    {
      PluginList localPluginList = new PluginList();
      return localPluginList;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static void setWebContentsDebuggingEnabled(boolean paramBoolean)
  {
    getFactory().getStatics().setWebContentsDebuggingEnabled(paramBoolean);
  }
  
  private void setupFindListenerIfNeeded()
  {
    if (this.mFindListener == null)
    {
      this.mFindListener = new FindListenerDistributor(null);
      this.mProvider.setFindListener(this.mFindListener);
    }
  }
  
  public void addJavascriptInterface(Object paramObject, String paramString)
  {
    checkThread();
    this.mProvider.addJavascriptInterface(paramObject, paramString);
  }
  
  public boolean canGoBack()
  {
    checkThread();
    return this.mProvider.canGoBack();
  }
  
  public boolean canGoBackOrForward(int paramInt)
  {
    checkThread();
    return this.mProvider.canGoBackOrForward(paramInt);
  }
  
  public boolean canGoForward()
  {
    checkThread();
    return this.mProvider.canGoForward();
  }
  
  @Deprecated
  public boolean canZoomIn()
  {
    checkThread();
    return this.mProvider.canZoomIn();
  }
  
  @Deprecated
  public boolean canZoomOut()
  {
    checkThread();
    return this.mProvider.canZoomOut();
  }
  
  @Deprecated
  public Picture capturePicture()
  {
    checkThread();
    return this.mProvider.capturePicture();
  }
  
  protected void checkThread()
  {
    if ((this.mWebViewThread != null) && (Looper.myLooper() != this.mWebViewThread))
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("A WebView method was called on thread '");
      ((StringBuilder)localObject).append(Thread.currentThread().getName());
      ((StringBuilder)localObject).append("'. All WebView methods must be called on the same thread. (Expected Looper ");
      ((StringBuilder)localObject).append(this.mWebViewThread);
      ((StringBuilder)localObject).append(" called on ");
      ((StringBuilder)localObject).append(Looper.myLooper());
      ((StringBuilder)localObject).append(", FYI main Looper is ");
      ((StringBuilder)localObject).append(Looper.getMainLooper());
      ((StringBuilder)localObject).append(")");
      localObject = new Throwable(((StringBuilder)localObject).toString());
      StrictMode.onWebViewMethodCalledOnWrongThread((Throwable)localObject);
      if (!sEnforceThreadChecking) {
        return;
      }
      throw new RuntimeException((Throwable)localObject);
    }
  }
  
  public void clearCache(boolean paramBoolean)
  {
    checkThread();
    this.mProvider.clearCache(paramBoolean);
  }
  
  public void clearFormData()
  {
    checkThread();
    this.mProvider.clearFormData();
  }
  
  public void clearHistory()
  {
    checkThread();
    this.mProvider.clearHistory();
  }
  
  public void clearMatches()
  {
    checkThread();
    this.mProvider.clearMatches();
  }
  
  public void clearSslPreferences()
  {
    checkThread();
    this.mProvider.clearSslPreferences();
  }
  
  @Deprecated
  public void clearView()
  {
    checkThread();
    this.mProvider.clearView();
  }
  
  protected int computeHorizontalScrollOffset()
  {
    return this.mProvider.getScrollDelegate().computeHorizontalScrollOffset();
  }
  
  protected int computeHorizontalScrollRange()
  {
    return this.mProvider.getScrollDelegate().computeHorizontalScrollRange();
  }
  
  public void computeScroll()
  {
    this.mProvider.getScrollDelegate().computeScroll();
  }
  
  protected int computeVerticalScrollExtent()
  {
    return this.mProvider.getScrollDelegate().computeVerticalScrollExtent();
  }
  
  protected int computeVerticalScrollOffset()
  {
    return this.mProvider.getScrollDelegate().computeVerticalScrollOffset();
  }
  
  protected int computeVerticalScrollRange()
  {
    return this.mProvider.getScrollDelegate().computeVerticalScrollRange();
  }
  
  public WebBackForwardList copyBackForwardList()
  {
    checkThread();
    return this.mProvider.copyBackForwardList();
  }
  
  @Deprecated
  public PrintDocumentAdapter createPrintDocumentAdapter()
  {
    checkThread();
    return this.mProvider.createPrintDocumentAdapter("default");
  }
  
  public PrintDocumentAdapter createPrintDocumentAdapter(String paramString)
  {
    checkThread();
    return this.mProvider.createPrintDocumentAdapter(paramString);
  }
  
  public WebMessagePort[] createWebMessageChannel()
  {
    checkThread();
    return this.mProvider.createWebMessageChannel();
  }
  
  @Deprecated
  public void debugDump()
  {
    checkThread();
  }
  
  public void destroy()
  {
    checkThread();
    this.mProvider.destroy();
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    this.mProvider.getViewDelegate().preDispatchDraw(paramCanvas);
    super.dispatchDraw(paramCanvas);
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return this.mProvider.getViewDelegate().dispatchKeyEvent(paramKeyEvent);
  }
  
  public void documentHasImages(Message paramMessage)
  {
    checkThread();
    this.mProvider.documentHasImages(paramMessage);
  }
  
  public void dumpViewHierarchyWithProperties(BufferedWriter paramBufferedWriter, int paramInt)
  {
    this.mProvider.dumpViewHierarchyWithProperties(paramBufferedWriter, paramInt);
  }
  
  @Deprecated
  public void emulateShiftHeld()
  {
    checkThread();
  }
  
  protected void encodeProperties(ViewHierarchyEncoder paramViewHierarchyEncoder)
  {
    super.encodeProperties(paramViewHierarchyEncoder);
    checkThread();
    paramViewHierarchyEncoder.addProperty("webview:contentHeight", this.mProvider.getContentHeight());
    paramViewHierarchyEncoder.addProperty("webview:contentWidth", this.mProvider.getContentWidth());
    paramViewHierarchyEncoder.addProperty("webview:scale", this.mProvider.getScale());
    paramViewHierarchyEncoder.addProperty("webview:title", this.mProvider.getTitle());
    paramViewHierarchyEncoder.addProperty("webview:url", this.mProvider.getUrl());
    paramViewHierarchyEncoder.addProperty("webview:originalUrl", this.mProvider.getOriginalUrl());
  }
  
  public void evaluateJavascript(String paramString, ValueCallback<String> paramValueCallback)
  {
    checkThread();
    this.mProvider.evaluateJavaScript(paramString, paramValueCallback);
  }
  
  @Deprecated
  public int findAll(String paramString)
  {
    checkThread();
    StrictMode.noteSlowCall("findAll blocks UI: prefer findAllAsync");
    return this.mProvider.findAll(paramString);
  }
  
  public void findAllAsync(String paramString)
  {
    checkThread();
    this.mProvider.findAllAsync(paramString);
  }
  
  public View findFocus()
  {
    return this.mProvider.getViewDelegate().findFocus(super.findFocus());
  }
  
  public View findHierarchyView(String paramString, int paramInt)
  {
    return this.mProvider.findHierarchyView(paramString, paramInt);
  }
  
  public void findNext(boolean paramBoolean)
  {
    checkThread();
    this.mProvider.findNext(paramBoolean);
  }
  
  public void flingScroll(int paramInt1, int paramInt2)
  {
    checkThread();
    this.mProvider.flingScroll(paramInt1, paramInt2);
  }
  
  @Deprecated
  public void freeMemory()
  {
    checkThread();
    this.mProvider.freeMemory();
  }
  
  public CharSequence getAccessibilityClassName()
  {
    return WebView.class.getName();
  }
  
  public AccessibilityNodeProvider getAccessibilityNodeProvider()
  {
    AccessibilityNodeProvider localAccessibilityNodeProvider2 = this.mProvider.getViewDelegate().getAccessibilityNodeProvider();
    AccessibilityNodeProvider localAccessibilityNodeProvider1 = localAccessibilityNodeProvider2;
    if (localAccessibilityNodeProvider2 == null) {
      localAccessibilityNodeProvider1 = super.getAccessibilityNodeProvider();
    }
    return localAccessibilityNodeProvider1;
  }
  
  public SslCertificate getCertificate()
  {
    checkThread();
    return this.mProvider.getCertificate();
  }
  
  @ViewDebug.ExportedProperty(category="webview")
  public int getContentHeight()
  {
    checkThread();
    return this.mProvider.getContentHeight();
  }
  
  @ViewDebug.ExportedProperty(category="webview")
  public int getContentWidth()
  {
    return this.mProvider.getContentWidth();
  }
  
  public Bitmap getFavicon()
  {
    checkThread();
    return this.mProvider.getFavicon();
  }
  
  public Handler getHandler()
  {
    return this.mProvider.getViewDelegate().getHandler(super.getHandler());
  }
  
  public HitTestResult getHitTestResult()
  {
    checkThread();
    return this.mProvider.getHitTestResult();
  }
  
  public String[] getHttpAuthUsernamePassword(String paramString1, String paramString2)
  {
    checkThread();
    return this.mProvider.getHttpAuthUsernamePassword(paramString1, paramString2);
  }
  
  @ViewDebug.ExportedProperty(category="webview")
  public String getOriginalUrl()
  {
    checkThread();
    return this.mProvider.getOriginalUrl();
  }
  
  public int getProgress()
  {
    checkThread();
    return this.mProvider.getProgress();
  }
  
  @ViewDebug.ExportedProperty(category="webview")
  @Deprecated
  public float getScale()
  {
    checkThread();
    return this.mProvider.getScale();
  }
  
  public WebSettings getSettings()
  {
    checkThread();
    return this.mProvider.getSettings();
  }
  
  @ViewDebug.ExportedProperty(category="webview")
  public String getTitle()
  {
    checkThread();
    return this.mProvider.getTitle();
  }
  
  public String getTouchIconUrl()
  {
    return this.mProvider.getTouchIconUrl();
  }
  
  @ViewDebug.ExportedProperty(category="webview")
  public String getUrl()
  {
    checkThread();
    return this.mProvider.getUrl();
  }
  
  public int getVisibleTitleHeight()
  {
    checkThread();
    return this.mProvider.getVisibleTitleHeight();
  }
  
  public WebViewProvider getWebViewProvider()
  {
    return this.mProvider;
  }
  
  @Deprecated
  public View getZoomControls()
  {
    checkThread();
    return this.mProvider.getZoomControls();
  }
  
  public void goBack()
  {
    checkThread();
    this.mProvider.goBack();
  }
  
  public void goBackOrForward(int paramInt)
  {
    checkThread();
    this.mProvider.goBackOrForward(paramInt);
  }
  
  public void goForward()
  {
    checkThread();
    this.mProvider.goForward();
  }
  
  public void invokeZoomPicker()
  {
    checkThread();
    this.mProvider.invokeZoomPicker();
  }
  
  public boolean isPaused()
  {
    return this.mProvider.isPaused();
  }
  
  public boolean isPrivateBrowsingEnabled()
  {
    checkThread();
    return this.mProvider.isPrivateBrowsingEnabled();
  }
  
  public void loadData(String paramString1, String paramString2, String paramString3)
  {
    checkThread();
    this.mProvider.loadData(paramString1, paramString2, paramString3);
  }
  
  public void loadDataWithBaseURL(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    checkThread();
    this.mProvider.loadDataWithBaseURL(paramString1, paramString2, paramString3, paramString4, paramString5);
  }
  
  public void loadUrl(String paramString)
  {
    checkThread();
    this.mProvider.loadUrl(paramString);
  }
  
  public void loadUrl(String paramString, Map<String, String> paramMap)
  {
    checkThread();
    this.mProvider.loadUrl(paramString, paramMap);
  }
  
  void notifyFindDialogDismissed()
  {
    checkThread();
    this.mProvider.notifyFindDialogDismissed();
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    this.mProvider.getViewDelegate().onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mProvider.getViewDelegate().onAttachedToWindow();
  }
  
  @Deprecated
  public void onChildViewAdded(View paramView1, View paramView2) {}
  
  @Deprecated
  public void onChildViewRemoved(View paramView1, View paramView2) {}
  
  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    this.mProvider.getViewDelegate().onConfigurationChanged(paramConfiguration);
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    return this.mProvider.getViewDelegate().onCreateInputConnection(paramEditorInfo);
  }
  
  protected void onDetachedFromWindowInternal()
  {
    this.mProvider.getViewDelegate().onDetachedFromWindow();
    super.onDetachedFromWindowInternal();
  }
  
  public boolean onDragEvent(DragEvent paramDragEvent)
  {
    return this.mProvider.getViewDelegate().onDragEvent(paramDragEvent);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    this.mProvider.getViewDelegate().onDraw(paramCanvas);
  }
  
  protected void onDrawVerticalScrollBar(Canvas paramCanvas, Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mProvider.getViewDelegate().onDrawVerticalScrollBar(paramCanvas, paramDrawable, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void onFinishTemporaryDetach()
  {
    super.onFinishTemporaryDetach();
    this.mProvider.getViewDelegate().onFinishTemporaryDetach();
  }
  
  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    this.mProvider.getViewDelegate().onFocusChanged(paramBoolean, paramInt, paramRect);
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    return this.mProvider.getViewDelegate().onGenericMotionEvent(paramMotionEvent);
  }
  
  @Deprecated
  public void onGlobalFocusChanged(View paramView1, View paramView2) {}
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    return this.mProvider.getViewDelegate().onHoverEvent(paramMotionEvent);
  }
  
  public void onInitializeAccessibilityEventInternal(AccessibilityEvent paramAccessibilityEvent)
  {
    super.onInitializeAccessibilityEventInternal(paramAccessibilityEvent);
    this.mProvider.getViewDelegate().onInitializeAccessibilityEvent(paramAccessibilityEvent);
  }
  
  public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo paramAccessibilityNodeInfo)
  {
    super.onInitializeAccessibilityNodeInfoInternal(paramAccessibilityNodeInfo);
    this.mProvider.getViewDelegate().onInitializeAccessibilityNodeInfo(paramAccessibilityNodeInfo);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return this.mProvider.getViewDelegate().onKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onKeyMultiple(int paramInt1, int paramInt2, KeyEvent paramKeyEvent)
  {
    return this.mProvider.getViewDelegate().onKeyMultiple(paramInt1, paramInt2, paramKeyEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return this.mProvider.getViewDelegate().onKeyUp(paramInt, paramKeyEvent);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    this.mProvider.getViewDelegate().onMeasure(paramInt1, paramInt2);
  }
  
  protected void onOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mProvider.getViewDelegate().onOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
  }
  
  public void onPause()
  {
    checkThread();
    this.mProvider.onPause();
  }
  
  public void onProvideVirtualStructure(ViewStructure paramViewStructure)
  {
    this.mProvider.getViewDelegate().onProvideVirtualStructure(paramViewStructure);
  }
  
  public void onResume()
  {
    checkThread();
    this.mProvider.onResume();
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mProvider.getViewDelegate().onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    this.mProvider.getViewDelegate().onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void onStartTemporaryDetach()
  {
    super.onStartTemporaryDetach();
    this.mProvider.getViewDelegate().onStartTemporaryDetach();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.mProvider.getViewDelegate().onTouchEvent(paramMotionEvent);
  }
  
  public boolean onTrackballEvent(MotionEvent paramMotionEvent)
  {
    return this.mProvider.getViewDelegate().onTrackballEvent(paramMotionEvent);
  }
  
  protected void onVisibilityChanged(View paramView, int paramInt)
  {
    super.onVisibilityChanged(paramView, paramInt);
    ensureProviderCreated();
    this.mProvider.getViewDelegate().onVisibilityChanged(paramView, paramInt);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    this.mProvider.getViewDelegate().onWindowFocusChanged(paramBoolean);
    super.onWindowFocusChanged(paramBoolean);
  }
  
  protected void onWindowVisibilityChanged(int paramInt)
  {
    super.onWindowVisibilityChanged(paramInt);
    this.mProvider.getViewDelegate().onWindowVisibilityChanged(paramInt);
  }
  
  @Deprecated
  public boolean overlayHorizontalScrollbar()
  {
    return true;
  }
  
  @Deprecated
  public boolean overlayVerticalScrollbar()
  {
    return false;
  }
  
  public boolean pageDown(boolean paramBoolean)
  {
    checkThread();
    return this.mProvider.pageDown(paramBoolean);
  }
  
  public boolean pageUp(boolean paramBoolean)
  {
    checkThread();
    return this.mProvider.pageUp(paramBoolean);
  }
  
  public void pauseTimers()
  {
    checkThread();
    this.mProvider.pauseTimers();
  }
  
  public boolean performAccessibilityActionInternal(int paramInt, Bundle paramBundle)
  {
    return this.mProvider.getViewDelegate().performAccessibilityAction(paramInt, paramBundle);
  }
  
  public boolean performLongClick()
  {
    return this.mProvider.getViewDelegate().performLongClick();
  }
  
  public void postUrl(String paramString, byte[] paramArrayOfByte)
  {
    checkThread();
    if (URLUtil.isNetworkUrl(paramString))
    {
      this.mProvider.postUrl(paramString, paramArrayOfByte);
      return;
    }
    this.mProvider.loadUrl(paramString);
  }
  
  public void postVisualStateCallback(long paramLong, VisualStateCallback paramVisualStateCallback)
  {
    checkThread();
    this.mProvider.insertVisualStateCallback(paramLong, paramVisualStateCallback);
  }
  
  public void postWebMessage(WebMessage paramWebMessage, Uri paramUri)
  {
    checkThread();
    this.mProvider.postMessageToMainFrame(paramWebMessage, paramUri);
  }
  
  @Deprecated
  public void refreshPlugins(boolean paramBoolean)
  {
    checkThread();
  }
  
  public void reload()
  {
    checkThread();
    this.mProvider.reload();
  }
  
  public void removeJavascriptInterface(String paramString)
  {
    checkThread();
    this.mProvider.removeJavascriptInterface(paramString);
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    return this.mProvider.getViewDelegate().requestChildRectangleOnScreen(paramView, paramRect, paramBoolean);
  }
  
  public boolean requestFocus(int paramInt, Rect paramRect)
  {
    return this.mProvider.getViewDelegate().requestFocus(paramInt, paramRect);
  }
  
  public void requestFocusNodeHref(Message paramMessage)
  {
    checkThread();
    this.mProvider.requestFocusNodeHref(paramMessage);
  }
  
  public void requestImageRef(Message paramMessage)
  {
    checkThread();
    this.mProvider.requestImageRef(paramMessage);
  }
  
  @Deprecated
  public boolean restorePicture(Bundle paramBundle, File paramFile)
  {
    checkThread();
    return this.mProvider.restorePicture(paramBundle, paramFile);
  }
  
  public WebBackForwardList restoreState(Bundle paramBundle)
  {
    checkThread();
    return this.mProvider.restoreState(paramBundle);
  }
  
  public void resumeTimers()
  {
    checkThread();
    this.mProvider.resumeTimers();
  }
  
  @Deprecated
  public void savePassword(String paramString1, String paramString2, String paramString3)
  {
    checkThread();
    this.mProvider.savePassword(paramString1, paramString2, paramString3);
  }
  
  @Deprecated
  public boolean savePicture(Bundle paramBundle, File paramFile)
  {
    checkThread();
    return this.mProvider.savePicture(paramBundle, paramFile);
  }
  
  public WebBackForwardList saveState(Bundle paramBundle)
  {
    checkThread();
    return this.mProvider.saveState(paramBundle);
  }
  
  public void saveWebArchive(String paramString)
  {
    checkThread();
    this.mProvider.saveWebArchive(paramString);
  }
  
  public void saveWebArchive(String paramString, boolean paramBoolean, ValueCallback<String> paramValueCallback)
  {
    checkThread();
    this.mProvider.saveWebArchive(paramString, paramBoolean, paramValueCallback);
  }
  
  public void setBackgroundColor(int paramInt)
  {
    this.mProvider.getViewDelegate().setBackgroundColor(paramInt);
  }
  
  @Deprecated
  public void setCertificate(SslCertificate paramSslCertificate)
  {
    checkThread();
    this.mProvider.setCertificate(paramSslCertificate);
  }
  
  public void setDownloadListener(DownloadListener paramDownloadListener)
  {
    checkThread();
    this.mProvider.setDownloadListener(paramDownloadListener);
  }
  
  void setFindDialogFindListener(FindListener paramFindListener)
  {
    checkThread();
    setupFindListenerIfNeeded();
    FindListenerDistributor.access$2302(this.mFindListener, paramFindListener);
  }
  
  public void setFindListener(FindListener paramFindListener)
  {
    checkThread();
    setupFindListenerIfNeeded();
    FindListenerDistributor.access$002(this.mFindListener, paramFindListener);
  }
  
  protected boolean setFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return this.mProvider.getViewDelegate().setFrame(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  @Deprecated
  public void setHorizontalScrollbarOverlay(boolean paramBoolean) {}
  
  public void setHttpAuthUsernamePassword(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    checkThread();
    this.mProvider.setHttpAuthUsernamePassword(paramString1, paramString2, paramString3, paramString4);
  }
  
  public void setInitialScale(int paramInt)
  {
    checkThread();
    this.mProvider.setInitialScale(paramInt);
  }
  
  public void setLayerType(int paramInt, Paint paramPaint)
  {
    super.setLayerType(paramInt, paramPaint);
    this.mProvider.getViewDelegate().setLayerType(paramInt, paramPaint);
  }
  
  public void setLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    this.mProvider.getViewDelegate().setLayoutParams(paramLayoutParams);
  }
  
  @Deprecated
  public void setMapTrackballToArrowKeys(boolean paramBoolean)
  {
    checkThread();
    this.mProvider.setMapTrackballToArrowKeys(paramBoolean);
  }
  
  public void setNetworkAvailable(boolean paramBoolean)
  {
    checkThread();
    this.mProvider.setNetworkAvailable(paramBoolean);
  }
  
  public void setOverScrollMode(int paramInt)
  {
    super.setOverScrollMode(paramInt);
    ensureProviderCreated();
    this.mProvider.getViewDelegate().setOverScrollMode(paramInt);
  }
  
  @Deprecated
  public void setPictureListener(PictureListener paramPictureListener)
  {
    checkThread();
    this.mProvider.setPictureListener(paramPictureListener);
  }
  
  public void setScrollBarStyle(int paramInt)
  {
    this.mProvider.getViewDelegate().setScrollBarStyle(paramInt);
    super.setScrollBarStyle(paramInt);
  }
  
  @Deprecated
  public void setVerticalScrollbarOverlay(boolean paramBoolean) {}
  
  public void setWebChromeClient(WebChromeClient paramWebChromeClient)
  {
    checkThread();
    this.mProvider.setWebChromeClient(paramWebChromeClient);
  }
  
  public void setWebViewClient(WebViewClient paramWebViewClient)
  {
    checkThread();
    this.mProvider.setWebViewClient(paramWebViewClient);
  }
  
  @Deprecated
  public boolean shouldDelayChildPressedState()
  {
    return this.mProvider.getViewDelegate().shouldDelayChildPressedState();
  }
  
  @Deprecated
  public boolean showFindDialog(String paramString, boolean paramBoolean)
  {
    checkThread();
    return this.mProvider.showFindDialog(paramString, paramBoolean);
  }
  
  public void stopLoading()
  {
    checkThread();
    this.mProvider.stopLoading();
  }
  
  public void zoomBy(float paramFloat)
  {
    checkThread();
    double d = paramFloat;
    if (d >= 0.01D)
    {
      if (d <= 100.0D)
      {
        this.mProvider.zoomBy(paramFloat);
        return;
      }
      throw new IllegalArgumentException("zoomFactor must be less than 100.");
    }
    throw new IllegalArgumentException("zoomFactor must be greater than 0.01.");
  }
  
  public boolean zoomIn()
  {
    checkThread();
    return this.mProvider.zoomIn();
  }
  
  public boolean zoomOut()
  {
    checkThread();
    return this.mProvider.zoomOut();
  }
  
  public static abstract interface FindListener
  {
    public abstract void onFindResultReceived(int paramInt1, int paramInt2, boolean paramBoolean);
  }
  
  private class FindListenerDistributor
    implements WebView.FindListener
  {
    private WebView.FindListener mFindDialogFindListener;
    private WebView.FindListener mUserFindListener;
    
    private FindListenerDistributor() {}
    
    public void onFindResultReceived(int paramInt1, int paramInt2, boolean paramBoolean)
    {
      WebView.FindListener localFindListener = this.mFindDialogFindListener;
      if (localFindListener != null) {
        localFindListener.onFindResultReceived(paramInt1, paramInt2, paramBoolean);
      }
      localFindListener = this.mUserFindListener;
      if (localFindListener != null) {
        localFindListener.onFindResultReceived(paramInt1, paramInt2, paramBoolean);
      }
    }
  }
  
  public static class HitTestResult
  {
    @Deprecated
    public static final int ANCHOR_TYPE = 1;
    public static final int EDIT_TEXT_TYPE = 9;
    public static final int EMAIL_TYPE = 4;
    public static final int GEO_TYPE = 3;
    @Deprecated
    public static final int IMAGE_ANCHOR_TYPE = 6;
    public static final int IMAGE_TYPE = 5;
    public static final int PHONE_TYPE = 2;
    public static final int SRC_ANCHOR_TYPE = 7;
    public static final int SRC_IMAGE_ANCHOR_TYPE = 8;
    public static final int UNKNOWN_TYPE = 0;
    private String mExtra;
    private int mType = 0;
    
    public String getExtra()
    {
      return this.mExtra;
    }
    
    public int getType()
    {
      return this.mType;
    }
    
    public void setExtra(String paramString)
    {
      this.mExtra = paramString;
    }
    
    public void setType(int paramInt)
    {
      this.mType = paramInt;
    }
  }
  
  @Deprecated
  public static abstract interface PictureListener
  {
    @Deprecated
    public abstract void onNewPicture(WebView paramWebView, Picture paramPicture);
  }
  
  public class PrivateAccess
  {
    public PrivateAccess() {}
    
    public void awakenScrollBars(int paramInt)
    {
      WebView.this.awakenScrollBars(paramInt);
    }
    
    public void awakenScrollBars(int paramInt, boolean paramBoolean)
    {
      WebView.this.awakenScrollBars(paramInt, paramBoolean);
    }
    
    public float getHorizontalScrollFactor()
    {
      return WebView.this.getHorizontalScrollFactor();
    }
    
    public int getHorizontalScrollbarHeight()
    {
      return WebView.this.getHorizontalScrollbarHeight();
    }
    
    public float getVerticalScrollFactor()
    {
      return WebView.this.getVerticalScrollFactor();
    }
    
    public void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      WebView.this.onScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean)
    {
      WebView.this.overScrollBy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramBoolean);
    }
    
    public void setMeasuredDimension(int paramInt1, int paramInt2)
    {
      WebView.this.setMeasuredDimension(paramInt1, paramInt2);
    }
    
    public void setScrollXRaw(int paramInt)
    {
      WebView.access$2102(WebView.this, paramInt);
    }
    
    public void setScrollYRaw(int paramInt)
    {
      WebView.access$2202(WebView.this, paramInt);
    }
    
    public void super_computeScroll()
    {
      WebView.this.computeScroll();
    }
    
    public boolean super_dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      return WebView.this.dispatchKeyEvent(paramKeyEvent);
    }
    
    public int super_getScrollBarStyle()
    {
      return WebView.this.getScrollBarStyle();
    }
    
    public void super_onDrawVerticalScrollBar(Canvas paramCanvas, Drawable paramDrawable, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      WebView.this.onDrawVerticalScrollBar(paramCanvas, paramDrawable, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public boolean super_onGenericMotionEvent(MotionEvent paramMotionEvent)
    {
      return WebView.this.onGenericMotionEvent(paramMotionEvent);
    }
    
    public boolean super_onHoverEvent(MotionEvent paramMotionEvent)
    {
      return WebView.this.onHoverEvent(paramMotionEvent);
    }
    
    public boolean super_performAccessibilityAction(int paramInt, Bundle paramBundle)
    {
      return WebView.this.performAccessibilityActionInternal(paramInt, paramBundle);
    }
    
    public boolean super_performLongClick()
    {
      return WebView.this.performLongClick();
    }
    
    public boolean super_requestFocus(int paramInt, Rect paramRect)
    {
      return WebView.this.requestFocus(paramInt, paramRect);
    }
    
    public void super_scrollTo(int paramInt1, int paramInt2)
    {
      WebView.this.scrollTo(paramInt1, paramInt2);
    }
    
    public boolean super_setFrame(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      return WebView.this.setFrame(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public void super_setLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      WebView.this.setLayoutParams(paramLayoutParams);
    }
    
    public void super_startActivityForResult(Intent paramIntent, int paramInt)
    {
      WebView.this.startActivityForResult(paramIntent, paramInt);
    }
  }
  
  public static abstract class VisualStateCallback
  {
    public abstract void onComplete(long paramLong);
  }
  
  public class WebViewTransport
  {
    private WebView mWebview;
    
    public WebViewTransport() {}
    
    public WebView getWebView()
    {
      try
      {
        WebView localWebView = this.mWebview;
        return localWebView;
      }
      finally
      {
        localObject = finally;
        throw ((Throwable)localObject);
      }
    }
    
    public void setWebView(WebView paramWebView)
    {
      try
      {
        this.mWebview = paramWebView;
        return;
      }
      finally
      {
        paramWebView = finally;
        throw paramWebView;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\WebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */