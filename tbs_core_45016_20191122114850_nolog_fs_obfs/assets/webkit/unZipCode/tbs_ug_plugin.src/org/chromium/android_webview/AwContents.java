package org.chromium.android_webview;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Picture;
import android.graphics.Rect;
import android.net.Uri;
import android.net.http.SslCertificate;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Pair;
import android.util.SparseArray;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.AnimationUtils;
import android.view.autofill.AutofillValue;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.textclassifier.TextClassifier;
import android.webkit.JavascriptInterface;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.Callable;
import org.chromium.android_webview.permission.AwGeolocationCallback;
import org.chromium.android_webview.permission.AwPermissionRequest;
import org.chromium.base.Callback;
import org.chromium.base.LocaleUtils;
import org.chromium.base.Log;
import org.chromium.base.ObserverList;
import org.chromium.base.ThreadUtils;
import org.chromium.base.TraceEvent;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.metrics.RecordHistogram;
import org.chromium.components.autofill.AutofillProvider;
import org.chromium.components.navigation_interception.InterceptNavigationDelegate;
import org.chromium.components.navigation_interception.NavigationParams;
import org.chromium.content.browser.AppWebMessagePort;
import org.chromium.content.browser.ContentViewStatics;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.content_public.browser.ContentViewCore.InternalAccessDelegate;
import org.chromium.content_public.browser.GestureListenerManager;
import org.chromium.content_public.browser.GestureStateListener;
import org.chromium.content_public.browser.ImeAdapter;
import org.chromium.content_public.browser.ImeEventObserver;
import org.chromium.content_public.browser.JavaScriptCallback;
import org.chromium.content_public.browser.JavascriptInjector;
import org.chromium.content_public.browser.LoadUrlParams;
import org.chromium.content_public.browser.MessagePort;
import org.chromium.content_public.browser.NavigationController;
import org.chromium.content_public.browser.NavigationEntry;
import org.chromium.content_public.browser.NavigationHistory;
import org.chromium.content_public.browser.SelectionClient;
import org.chromium.content_public.browser.SelectionPopupController;
import org.chromium.content_public.browser.SmartClipProvider;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.browser.WebContents.InternalsHolder;
import org.chromium.content_public.browser.WebContentsAccessibility;
import org.chromium.content_public.browser.WebContentsInternals;
import org.chromium.content_public.common.BrowserSideNavigationPolicy;
import org.chromium.content_public.common.Referrer;
import org.chromium.content_public.common.UseZoomForDSFPolicy;
import org.chromium.device.gamepad.GamepadList;
import org.chromium.net.NetworkChangeNotifier;
import org.chromium.tencent.TencentAwContent.TencentHitTestData;
import org.chromium.tencent.TencentAwContentsClientBridge;
import org.chromium.tencent.TencentAwContentsIoThreadClient;
import org.chromium.tencent.TencentAwWebContentsDelegateAdapter;
import org.chromium.tencent.android_webview.TencentAwScrollOffsetManager;
import org.chromium.tencent.utils.ClassAdapter;
import org.chromium.tencent.utils.TencentDeviceUtils;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;
import org.chromium.ui.base.ActivityWindowAndroid;
import org.chromium.ui.base.EventForwarder;
import org.chromium.ui.base.ViewAndroidDelegate;
import org.chromium.ui.base.WindowAndroid;
import org.chromium.ui.display.DisplayAndroid;
import org.chromium.ui.display.DisplayAndroid.DisplayAndroidObserver;

@JNINamespace("android_webview")
public class AwContents
  implements SmartClipProvider
{
  private static final double MIN_SCREEN_HEIGHT_PERCENTAGE_FOR_INTERSTITIAL = 0.7D;
  protected static final int NO_WARN = 0;
  private static final int PROCESS_TEXT_REQUEST_CODE = 100;
  private static final String PRODUCT_VERSION = ;
  private static final int RAM_MEMORY_HARDWARE_ENABLED = 650;
  private static final String SAMSUNG_WORKAROUND_BASE_URL = "email://";
  private static final int SAMSUNG_WORKAROUND_DELAY = 200;
  public static final String SAVE_RESTORE_STATE_KEY = "WEBVIEW_CHROMIUM_STATE";
  protected static final String TAG = "AwContents";
  protected static final boolean TRACE = false;
  protected static final int WARN = 1;
  private static final String WEB_ARCHIVE_EXTENSION = ".mht";
  private static final float ZOOM_CONTROLS_EPSILON = 0.007F;
  private static WeakHashMap<Context, WindowAndroidWrapper> sContextWindowMap;
  private static String sCurrentLocales = "";
  private static final Rect sLocalGlobalVisibleRect = new Rect();
  private final int mAppTargetSdkVersion;
  private AutofillProvider mAutofillProvider;
  protected AwAutofillClient mAwAutofillClient;
  private AwPdfExporter mAwPdfExporter;
  private AwViewMethods mAwViewMethods;
  private final AwContentsBackgroundThreadClient mBackgroundThreadClient;
  private int mBaseBackgroundColor = -1;
  protected final AwBrowserContext mBrowserContext;
  private CleanupReference mCleanupReference;
  public ViewGroup mContainerView;
  public boolean mContainerViewFocused;
  protected float mContentHeightDip;
  protected ContentViewCore mContentViewCore;
  protected float mContentWidthDip;
  protected final AwContentsClient mContentsClient;
  protected final AwContentsClientBridge mContentsClientBridge;
  protected final Context mContext;
  protected AwGLFunctor mCurrentFunctor;
  private final DefaultVideoPosterRequestHandler mDefaultVideoPosterRequestHandler;
  private boolean mDeferredShouldOverrideUrlLoadingIsPendingForPopup;
  private final DisplayAndroid.DisplayAndroidObserver mDisplayObserver;
  private Bitmap mFavicon;
  private AwGLFunctor mFullScreenFunctor;
  private final FullScreenTransitionsState mFullScreenTransitionsState;
  private Handler mHandler;
  private boolean mHasRequestedVisitedHistoryFromClient;
  private AwGLFunctor mInitialFunctor;
  private final InterceptNavigationDelegateImpl mInterceptNavigationDelegate;
  protected InternalAccessDelegate mInternalAccessAdapter;
  protected boolean mInvalidateRootViewOnNextDraw;
  private final AwContentsIoThreadClient mIoThreadClient;
  public boolean mIsAttachedToWindow;
  public boolean mIsContentViewCoreVisible;
  private boolean mIsDestroyed;
  private boolean mIsNoOperation;
  private boolean mIsPaused;
  private boolean mIsPopupWindow;
  private boolean mIsUpdateVisibilityTaskPending;
  public boolean mIsViewVisible;
  public boolean mIsWindowVisible;
  private JavascriptInjector mJavascriptInjector;
  private final AwLayoutSizer mLayoutSizer;
  private float mMaxPageScaleFactor = 1.0F;
  private float mMinPageScaleFactor = 1.0F;
  protected long mNativeAwContents;
  private final NativeDrawGLFunctorFactory mNativeDrawGLFunctorFactory;
  protected NavigationController mNavigationController;
  protected OverScrollGlow mOverScrollGlow;
  private boolean mOverlayHorizontalScrollbar = true;
  private boolean mOverlayVerticalScrollbar;
  private float mPageScaleFactor = 1.0F;
  protected Paint mPaintForNWorkaround;
  private Callable<Picture> mPictureListenerContentProvider;
  protected final TencentAwContent.TencentHitTestData mPossiblyStaleHitTestData = new TencentAwContent.TencentHitTestData();
  private int mRendererPriority = 2;
  private boolean mRendererPriorityWaivedWhenNotVisible;
  private final ScrollAccessibilityHelper mScrollAccessibilityHelper;
  protected final AwScrollOffsetManager mScrollOffsetManager;
  protected final AwSettings mSettings;
  private boolean mTemporarilyDetached;
  private final ObserverList<PopupTouchHandleDrawable> mTouchHandleDrawables = new ObserverList();
  private Runnable mUpdateVisibilityRunnable;
  private AwViewAndroidDelegate mViewAndroidDelegate;
  protected WebContents mWebContents;
  protected final AwWebContentsDelegateAdapter mWebContentsDelegate;
  private WebContentsInternals mWebContentsInternals;
  private WebContentsInternalsHolder mWebContentsInternalsHolder;
  private AwWebContentsObserver mWebContentsObserver;
  private WindowAndroidWrapper mWindowAndroid;
  public boolean mWindowFocused;
  private final AwZoomControls mZoomControls;
  
  public AwContents(AwBrowserContext paramAwBrowserContext, ViewGroup paramViewGroup, Context paramContext, InternalAccessDelegate paramInternalAccessDelegate, NativeDrawGLFunctorFactory paramNativeDrawGLFunctorFactory, AwContentsClient paramAwContentsClient, AwSettings paramAwSettings)
  {
    this(paramAwBrowserContext, paramViewGroup, paramContext, paramInternalAccessDelegate, paramNativeDrawGLFunctorFactory, paramAwContentsClient, paramAwSettings, new DependencyFactory());
  }
  
  public AwContents(AwBrowserContext paramAwBrowserContext, ViewGroup paramViewGroup, Context paramContext, InternalAccessDelegate paramInternalAccessDelegate, NativeDrawGLFunctorFactory paramNativeDrawGLFunctorFactory, AwContentsClient paramAwContentsClient, AwSettings paramAwSettings, DependencyFactory paramDependencyFactory)
  {
    updateDefaultLocale();
    paramAwSettings.updateAcceptLanguages();
    this.mBrowserContext = paramAwBrowserContext;
    this.mContainerView = paramViewGroup;
    this.mContainerView.setWillNotDraw(false);
    this.mHandler = new Handler();
    this.mContext = paramContext;
    this.mAutofillProvider = paramDependencyFactory.createAutofillProvider(paramContext, this.mContainerView);
    this.mAppTargetSdkVersion = this.mContext.getApplicationInfo().targetSdkVersion;
    this.mInternalAccessAdapter = paramInternalAccessDelegate;
    this.mNativeDrawGLFunctorFactory = paramNativeDrawGLFunctorFactory;
    this.mInitialFunctor = new AwGLFunctor(this.mNativeDrawGLFunctorFactory, this.mContainerView);
    this.mCurrentFunctor = this.mInitialFunctor;
    this.mContentsClient = paramAwContentsClient;
    initTencentAwContentsIoThreadClient(this.mContext, this.mContentsClient);
    this.mContentsClient.getCallbackHelper().setCancelCallbackPoller(new AwContentsClientCallbackHelper.CancelCallbackPoller()
    {
      public boolean shouldCancelAllCallbacks()
      {
        return AwContents.this.isDestroyedOrNoOperation(0);
      }
    });
    this.mAwViewMethods = initAwViewMethodsImpl();
    this.mFullScreenTransitionsState = new FullScreenTransitionsState(this.mContainerView, this.mInternalAccessAdapter, this.mAwViewMethods, null);
    this.mLayoutSizer = paramDependencyFactory.createLayoutSizer();
    this.mSettings = paramAwSettings;
    this.mLayoutSizer.setDelegate(new AwLayoutSizerDelegate(null));
    this.mWebContentsDelegate = new TencentAwWebContentsDelegateAdapter(this, paramAwContentsClient, paramAwSettings, this.mContext, this.mContainerView);
    this.mContentsClientBridge = new TencentAwContentsClientBridge(this.mContext, paramAwContentsClient, AwContentsStatics.getClientCertLookupTable());
    this.mZoomControls = new AwZoomControls(this);
    this.mBackgroundThreadClient = new BackgroundThreadClientImpl(null);
    this.mIoThreadClient = new IoThreadClientImpl(null);
    this.mInterceptNavigationDelegate = new InterceptNavigationDelegateImpl(null);
    this.mDisplayObserver = new AwDisplayAndroidObserver(null);
    this.mUpdateVisibilityRunnable = new Runnable()
    {
      public void run()
      {
        AwContents.this.updateContentViewCoreVisibility();
      }
    };
    paramAwBrowserContext = new AwSettings.ZoomSupportChangeListener()
    {
      public void onGestureZoomSupportChanged(boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2)
      {
        if (AwContents.this.isDestroyedOrNoOperation(0)) {
          return;
        }
        AwContents.this.mContentViewCore.updateDoubleTapSupport(paramAnonymousBoolean1);
        AwContents.this.mContentViewCore.updateMultiTouchZoomSupport(paramAnonymousBoolean2);
      }
    };
    this.mSettings.setZoomListener(paramAwBrowserContext);
    this.mDefaultVideoPosterRequestHandler = new DefaultVideoPosterRequestHandler(this.mContentsClient);
    this.mSettings.setDefaultVideoPosterURL(this.mDefaultVideoPosterRequestHandler.getDefaultVideoPosterURL());
    this.mScrollOffsetManager = paramDependencyFactory.createScrollOffsetManager(initAwScrollOffsetManagerDelegate());
    this.mScrollAccessibilityHelper = new ScrollAccessibilityHelper(this.mContainerView);
    setOverScrollMode(this.mContainerView.getOverScrollMode());
    setScrollBarStyle(this.mInternalAccessAdapter.super_getScrollBarStyle());
    setNewAwContents(nativeInit(this.mBrowserContext));
    onContainerViewChanged();
  }
  
  public static Activity activityFromContext(Context paramContext)
  {
    return WindowAndroid.activityFromContext(paramContext);
  }
  
  private void destroyNatives()
  {
    if (this.mCleanupReference != null)
    {
      this.mWebContentsObserver.destroy();
      this.mWebContentsObserver = null;
      this.mContentViewCore.destroy();
      this.mContentViewCore = null;
      this.mNativeAwContents = 0L;
      this.mWebContents = null;
      this.mWebContentsInternals = null;
      this.mNavigationController = null;
      this.mCleanupReference.cleanupNow();
      this.mCleanupReference = null;
    }
    onDestroyed();
  }
  
  private boolean extendsOutOfWindow()
  {
    int[] arrayOfInt = new int[2];
    this.mContainerView.getLocationOnScreen(arrayOfInt);
    int k = arrayOfInt[0];
    int i = arrayOfInt[1];
    this.mContainerView.getRootView().getLocationOnScreen(arrayOfInt);
    int m = arrayOfInt[0];
    int j = arrayOfInt[1];
    k -= m;
    i -= j;
    if ((k >= 0) && (i >= 0) && (k + this.mContainerView.getWidth() <= this.mContainerView.getRootView().getWidth())) {
      return i + this.mContainerView.getHeight() > this.mContainerView.getRootView().getHeight();
    }
    return true;
  }
  
  private static String fixupBase(String paramString)
  {
    String str = paramString;
    if (TextUtils.isEmpty(paramString)) {
      str = "about:blank";
    }
    return str;
  }
  
  private static String fixupData(String paramString)
  {
    String str = paramString;
    if (TextUtils.isEmpty(paramString)) {
      str = "";
    }
    return str;
  }
  
  private static String fixupHistory(String paramString)
  {
    String str = paramString;
    if (TextUtils.isEmpty(paramString)) {
      str = "about:blank";
    }
    return str;
  }
  
  private static String fixupMimeType(String paramString)
  {
    String str = paramString;
    if (TextUtils.isEmpty(paramString)) {
      str = "text/html";
    }
    return str;
  }
  
  public static String generateArchiveAutoNamePath(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (!paramString1.isEmpty())) {}
    try
    {
      str = new URL(paramString1).getPath();
      i = str.lastIndexOf('/');
      paramString1 = str;
      if (i <= 0) {
        break label49;
      }
      paramString1 = str.substring(i + 1);
    }
    catch (MalformedURLException paramString1)
    {
      String str;
      int i;
      for (;;) {}
    }
    paramString1 = null;
    label49:
    str = paramString1;
    if (TextUtils.isEmpty(paramString1)) {
      str = "index";
    }
    paramString1 = new StringBuilder();
    paramString1.append(paramString2);
    paramString1.append(str);
    paramString1.append(".mht");
    paramString1 = paramString1.toString();
    if (!new File(paramString1).exists()) {
      return paramString1;
    }
    i = 1;
    while (i < 100)
    {
      paramString1 = new StringBuilder();
      paramString1.append(paramString2);
      paramString1.append(str);
      paramString1.append("-");
      paramString1.append(i);
      paramString1.append(".mht");
      paramString1 = paramString1.toString();
      if (!new File(paramString1).exists()) {
        return paramString1;
      }
      i += 1;
    }
    Log.e("AwContents", "Unable to auto generate archive name for path: %s", new Object[] { paramString2 });
    return null;
  }
  
  @CalledByNative
  private static void generateMHTMLCallback(String paramString, long paramLong, Callback<String> paramCallback)
  {
    if (paramCallback == null) {
      return;
    }
    if (paramLong < 0L) {
      paramString = null;
    }
    paramCallback.onResult(paramString);
  }
  
  public static long getAwDrawGLFunction()
  {
    return AwGLFunctor.getAwDrawGLFunction();
  }
  
  @CalledByNative
  private int getErrorUiType()
  {
    if (extendsOutOfWindow()) {
      return 2;
    }
    if (canShowBigInterstitial()) {
      return 0;
    }
    return 1;
  }
  
  private JavascriptInjector getJavascriptInjector()
  {
    if (this.mJavascriptInjector == null) {
      this.mJavascriptInjector = JavascriptInjector.fromWebContents(this.mWebContents);
    }
    return this.mJavascriptInjector;
  }
  
  @CalledByNative
  private int[] getLocationOnScreen()
  {
    int[] arrayOfInt = new int[2];
    this.mContainerView.getLocationOnScreen(arrayOfInt);
    return arrayOfInt;
  }
  
  @VisibleForTesting
  public static int getNativeInstanceCount()
  {
    return nativeGetNativeInstanceCount();
  }
  
  private static WindowAndroidWrapper getWindowAndroid(Context paramContext)
  {
    if (sContextWindowMap == null) {
      sContextWindowMap = new WeakHashMap();
    }
    WindowAndroidWrapper localWindowAndroidWrapper = (WindowAndroidWrapper)sContextWindowMap.get(paramContext);
    if (localWindowAndroidWrapper != null) {
      return localWindowAndroidWrapper;
    }
    int i;
    if (activityFromContext(paramContext) != null) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0) {
      localWindowAndroidWrapper = new WindowAndroidWrapper(new ActivityWindowAndroid(paramContext, false));
    } else {
      localWindowAndroidWrapper = new WindowAndroidWrapper(new WindowAndroid(paramContext));
    }
    sContextWindowMap.put(paramContext, localWindowAndroidWrapper);
    return localWindowAndroidWrapper;
  }
  
  private void initializeContentViewCore(ContentViewCore paramContentViewCore, Context paramContext, ViewAndroidDelegate paramViewAndroidDelegate, InternalAccessDelegate paramInternalAccessDelegate, WebContents paramWebContents, WindowAndroid paramWindowAndroid)
  {
    paramContentViewCore.initialize(paramViewAndroidDelegate, paramInternalAccessDelegate, paramWebContents, paramWindowAndroid);
    paramContentViewCore = ClassAdapter.fromWebContentsSelectionPopupController(paramWebContents);
    paramContentViewCore.setActionModeCallback(new AwActionModeCallback(this.mContext, this, paramContentViewCore.getActionModeCallbackHelper()));
    paramViewAndroidDelegate = this.mAutofillProvider;
    if (paramViewAndroidDelegate != null) {
      paramContentViewCore.setNonSelectionActionModeCallback(new AutofillActionModeCallback(paramContext, paramViewAndroidDelegate));
    }
    paramContentViewCore.setSelectionClient(SelectionClient.createSmartSelectionClient(paramWebContents));
    ImeAdapter.fromWebContents(paramWebContents).addEventObserver(new ImeEventObserver()
    {
      public void onBeforeSendKeyEvent(KeyEvent paramAnonymousKeyEvent)
      {
        if (AwContents.isDpadEvent(paramAnonymousKeyEvent)) {
          AwContents.this.mSettings.setSpatialNavigationEnabled(true);
        }
      }
      
      public void onImeEvent() {}
      
      public void onNodeAttributeUpdated(boolean paramAnonymousBoolean1, boolean paramAnonymousBoolean2) {}
    });
  }
  
  private void installWebContentsObserver()
  {
    AwWebContentsObserver localAwWebContentsObserver = this.mWebContentsObserver;
    if (localAwWebContentsObserver != null) {
      localAwWebContentsObserver.destroy();
    }
    this.mWebContentsObserver = new AwWebContentsObserver(this.mWebContents, this, this.mContentsClient);
  }
  
  private static boolean isBase64Encoded(String paramString)
  {
    return "base64".equals(paramString);
  }
  
  public static boolean isDpadEvent(KeyEvent paramKeyEvent)
  {
    if (paramKeyEvent.getAction() == 0) {
      switch (paramKeyEvent.getKeyCode())
      {
      default: 
        break;
      case 19: 
      case 20: 
      case 21: 
      case 22: 
      case 23: 
        return true;
      }
    }
    return false;
  }
  
  private boolean isNoOperation()
  {
    return this.mIsNoOperation;
  }
  
  private boolean isSamsungMailApp()
  {
    String str = this.mContext.getPackageName();
    return ("com.android.email".equals(str)) || ("com.samsung.android.email.composer".equals(str));
  }
  
  private native void nativeAddVisitedLinks(long paramLong, String[] paramArrayOfString);
  
  private native long nativeCapturePicture(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativeClearCache(long paramLong, boolean paramBoolean);
  
  private native void nativeClearMatches(long paramLong);
  
  private native void nativeClearView(long paramLong);
  
  private native void nativeCreatePdfExporter(long paramLong, AwPdfExporter paramAwPdfExporter);
  
  private static native void nativeDestroy(long paramLong);
  
  private native void nativeDocumentHasImages(long paramLong, Message paramMessage);
  
  private native void nativeEnableOnNewPicture(long paramLong, boolean paramBoolean);
  
  private native void nativeEvaluateJavaScriptOnInterstitialForTesting(long paramLong, String paramString, JavaScriptCallback paramJavaScriptCallback);
  
  private native void nativeFindAllAsync(long paramLong, String paramString);
  
  private native void nativeFindNext(long paramLong, boolean paramBoolean);
  
  private native void nativeFocusFirstNode(long paramLong);
  
  private native void nativeGenerateMHTML(long paramLong, String paramString, Callback<String> paramCallback);
  
  private native byte[] nativeGetCertificate(long paramLong);
  
  private native int nativeGetEffectivePriority(long paramLong);
  
  private static native int nativeGetNativeInstanceCount();
  
  private native byte[] nativeGetOpaqueState(long paramLong);
  
  private native WebContents nativeGetWebContents(long paramLong);
  
  private static native boolean nativeHasRequiredHardwareExtensions();
  
  private static native long nativeInit(AwBrowserContext paramAwBrowserContext);
  
  private native void nativeInsertVisualStateCallback(long paramLong1, long paramLong2, VisualStateCallback paramVisualStateCallback);
  
  private native void nativeInvokeGeolocationCallback(long paramLong, boolean paramBoolean, String paramString);
  
  private native boolean nativeIsVisible(long paramLong);
  
  private native void nativeKillRenderProcess(long paramLong);
  
  private native void nativeOnAttachedToWindow(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativeOnDetachedFromWindow(long paramLong);
  
  private native void nativeOnSizeChanged(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  private native void nativePreauthorizePermission(long paramLong1, String paramString, long paramLong2);
  
  private native long nativeReleasePopupAwContents(long paramLong);
  
  private native boolean nativeRestoreFromOpaqueState(long paramLong, byte[] paramArrayOfByte);
  
  private native void nativeResumeLoadingCreatedPopupWebContents(long paramLong);
  
  private native void nativeScrollTo(long paramLong, int paramInt1, int paramInt2);
  
  private static native void nativeSetAwDrawGLFunctionTable(long paramLong);
  
  private static native void nativeSetAwDrawSWFunctionTable(long paramLong);
  
  private native void nativeSetAwGLFunctor(long paramLong1, long paramLong2);
  
  private native void nativeSetBackgroundColor(long paramLong, int paramInt);
  
  private native void nativeSetDipScale(long paramLong, float paramFloat);
  
  private native void nativeSetExtraHeadersForUrl(long paramLong, String paramString1, String paramString2);
  
  private native void nativeSetIsPaused(long paramLong, boolean paramBoolean);
  
  private native void nativeSetJavaPeers(long paramLong, AwContents paramAwContents, AwWebContentsDelegate paramAwWebContentsDelegate, AwContentsClientBridge paramAwContentsClientBridge, AwContentsIoThreadClient paramAwContentsIoThreadClient, TencentAwContentsIoThreadClient paramTencentAwContentsIoThreadClient, InterceptNavigationDelegate paramInterceptNavigationDelegate, AutofillProvider paramAutofillProvider);
  
  private native void nativeSetJsOnlineProperty(long paramLong, boolean paramBoolean);
  
  private static native void nativeSetShouldDownloadFavicons();
  
  private native void nativeSetViewVisibility(long paramLong, boolean paramBoolean);
  
  private native void nativeSetWindowVisibility(long paramLong, boolean paramBoolean);
  
  private native void nativeSmoothScroll(long paramLong1, int paramInt1, int paramInt2, long paramLong2);
  
  private native void nativeTrimMemory(long paramLong, int paramInt, boolean paramBoolean);
  
  private static native void nativeUpdateDefaultLocale(String paramString1, String paramString2);
  
  private native void nativeUpdateLastHitTestData(long paramLong);
  
  private native void nativeZoomBy(long paramLong, float paramFloat);
  
  @SuppressLint({"NewApi"})
  private void onContainerViewChanged()
  {
    Object localObject = FullScreenTransitionsState.a(this.mFullScreenTransitionsState);
    ViewGroup localViewGroup = this.mContainerView;
    ((AwViewMethods)localObject).onVisibilityChanged(localViewGroup, localViewGroup.getVisibility());
    ((AwViewMethods)localObject).onWindowVisibilityChanged(this.mContainerView.getWindowVisibility());
    boolean bool = X5ApiCompatibilityUtils.isAttachedToWindow(this.mContainerView);
    if ((bool) && (!this.mIsAttachedToWindow)) {
      ((AwViewMethods)localObject).onAttachedToWindow();
    } else if ((!bool) && (this.mIsAttachedToWindow)) {
      ((AwViewMethods)localObject).onDetachedFromWindow();
    }
    ((AwViewMethods)localObject).onSizeChanged(this.mContainerView.getWidth(), this.mContainerView.getHeight(), 0, 0);
    ((AwViewMethods)localObject).onWindowFocusChanged(this.mContainerView.hasWindowFocus());
    ((AwViewMethods)localObject).onFocusChanged(this.mContainerView.hasFocus(), 0, null);
    this.mContainerView.requestLayout();
    localObject = this.mAutofillProvider;
    if (localObject != null) {
      ((AutofillProvider)localObject).onContainerViewChanged(this.mContainerView);
    }
  }
  
  @CalledByNative
  private long onCreateTouchHandle()
  {
    return PopupTouchHandleDrawable.create(this.mTouchHandleDrawables, this.mContentViewCore).getNativeDrawable();
  }
  
  @CalledByNative
  private static void onDocumentHasImagesResponse(boolean paramBoolean, Message paramMessage)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  @CalledByNative
  private static void onEvaluateJavaScriptResultForTesting(String paramString, JavaScriptCallback paramJavaScriptCallback)
  {
    paramJavaScriptCallback.handleJavaScriptResult(paramString);
  }
  
  @CalledByNative
  private void onGeolocationPermissionsHidePrompt()
  {
    this.mContentsClient.onGeolocationPermissionsHidePrompt();
  }
  
  @CalledByNative
  private void onGeolocationPermissionsShowPrompt(String paramString)
  {
    if (isDestroyedOrNoOperation(0)) {
      return;
    }
    AwGeolocationPermissions localAwGeolocationPermissions = this.mBrowserContext.getGeolocationPermissions();
    if (!this.mSettings.getGeolocationEnabled())
    {
      nativeInvokeGeolocationCallback(this.mNativeAwContents, false, paramString);
      return;
    }
    if (localAwGeolocationPermissions.hasOrigin(paramString))
    {
      nativeInvokeGeolocationCallback(this.mNativeAwContents, localAwGeolocationPermissions.isOriginAllowed(paramString), paramString);
      return;
    }
    this.mContentsClient.onGeolocationPermissionsShowPrompt(paramString, new AwGeolocationCallback(paramString, this));
  }
  
  @CalledByNative
  private void onPermissionRequestCanceled(AwPermissionRequest paramAwPermissionRequest)
  {
    this.mContentsClient.onPermissionRequestCanceled(paramAwPermissionRequest);
  }
  
  @CalledByNative
  private void onReceivedHttpAuthRequest(AwHttpAuthHandler paramAwHttpAuthHandler, String paramString1, String paramString2)
  {
    this.mContentsClient.onReceivedHttpAuthRequest(paramAwHttpAuthHandler, paramString1, paramString2);
  }
  
  @CalledByNative
  private void onReceivedIcon(Bitmap paramBitmap)
  {
    this.mContentsClient.onReceivedIcon(paramBitmap);
    this.mFavicon = paramBitmap;
  }
  
  @CalledByNative
  private void onReceivedTouchIconUrl(String paramString, boolean paramBoolean)
  {
    this.mContentsClient.onReceivedTouchIconUrl(paramString, paramBoolean);
  }
  
  @CalledByNative
  private void onWebLayoutContentsSizeChanged(int paramInt1, int paramInt2)
  {
    this.mLayoutSizer.onContentSizeChanged(paramInt1, paramInt2);
  }
  
  @CalledByNative
  private void onWebLayoutPageScaleFactorChanged(float paramFloat)
  {
    this.mLayoutSizer.onPageScaleChanged(paramFloat);
  }
  
  private void postUpdateContentViewCoreVisibility()
  {
    if (this.mIsUpdateVisibilityTaskPending) {
      return;
    }
    this.mIsUpdateVisibilityTaskPending = true;
    this.mHandler.post(this.mUpdateVisibilityRunnable);
  }
  
  private static void recordHistoryUrl(@HistoryUrl int paramInt)
  {
    RecordHistogram.recordEnumeratedHistogram("WebView.LoadDataWithBaseUrl.HistoryUrl", paramInt, 3);
  }
  
  private void requestVisitedHistoryFromClient()
  {
    Callback local7 = new Callback()
    {
      public void onResult(final String[] paramAnonymousArrayOfString)
      {
        if (paramAnonymousArrayOfString != null)
        {
          int i = 0;
          while (i < paramAnonymousArrayOfString.length)
          {
            if (paramAnonymousArrayOfString[i] == null) {
              paramAnonymousArrayOfString[i] = "";
            }
            i += 1;
          }
        }
        ThreadUtils.runOnUiThread(new Runnable()
        {
          public void run()
          {
            if (!AwContents.this.isDestroyedOrNoOperation(0)) {
              AwContents.this.nativeAddVisitedLinks(AwContents.this.mNativeAwContents, paramAnonymousArrayOfString);
            }
          }
        });
      }
    };
    this.mContentsClient.getVisitedHistory(local7);
  }
  
  private void saveWebArchiveInternal(String paramString, final Callback<String> paramCallback)
  {
    if ((paramString != null) && (!isDestroyedOrNoOperation(1)))
    {
      nativeGenerateMHTML(this.mNativeAwContents, paramString, paramCallback);
      return;
    }
    if (paramCallback == null) {
      return;
    }
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        paramCallback.onResult(null);
      }
    });
  }
  
  @CalledByNative
  private void scrollContainerViewTo(int paramInt1, int paramInt2)
  {
    this.mScrollOffsetManager.scrollContainerViewTo(paramInt1, paramInt2);
  }
  
  @CalledByNative
  private void setAwAutofillClient(AwAutofillClient paramAwAutofillClient)
  {
    this.mAwAutofillClient = paramAwAutofillClient;
    paramAwAutofillClient.init(this.mContentViewCore);
  }
  
  public static void setAwDrawGLFunctionTable(long paramLong)
  {
    nativeSetAwDrawGLFunctionTable(paramLong);
  }
  
  public static void setAwDrawSWFunctionTable(long paramLong)
  {
    nativeSetAwDrawSWFunctionTable(paramLong);
  }
  
  private void setInternalAccessAdapter(InternalAccessDelegate paramInternalAccessDelegate)
  {
    this.mInternalAccessAdapter = paramInternalAccessDelegate;
    this.mContentViewCore.setContainerViewInternals(this.mInternalAccessAdapter);
  }
  
  private void setNewAwContents(long paramLong)
  {
    if (Build.VERSION.SDK_INT < 26)
    {
      setNewAwContentsPreO(paramLong);
      return;
    }
    TextClassifier localTextClassifier;
    if (this.mWebContents != null) {
      localTextClassifier = getTextClassifier();
    } else {
      localTextClassifier = null;
    }
    setNewAwContentsPreO(paramLong);
    if (localTextClassifier != null) {
      setTextClassifier(localTextClassifier);
    }
  }
  
  private void setNewAwContentsPreO(long paramLong)
  {
    if (this.mNativeAwContents != 0L)
    {
      destroyNatives();
      this.mContentViewCore = null;
      this.mWebContents = null;
      this.mWebContentsInternalsHolder = null;
      this.mWebContentsInternals = null;
      this.mNavigationController = null;
      this.mJavascriptInjector = null;
    }
    this.mNativeAwContents = paramLong;
    updateNativeAwGLFunctor();
    WebContents localWebContents = nativeGetWebContents(this.mNativeAwContents);
    this.mWindowAndroid = getWindowAndroid(this.mContext);
    this.mContentViewCore = ContentViewCore.create(this.mContext, PRODUCT_VERSION);
    this.mViewAndroidDelegate = new AwViewAndroidDelegate(this.mContainerView, this.mContentsClient, this.mScrollOffsetManager);
    initializeContentViewCore(this.mContentViewCore, this.mContext, this.mViewAndroidDelegate, this.mInternalAccessAdapter, localWebContents, this.mWindowAndroid.getWindowAndroid());
    nativeSetJavaPeers(this.mNativeAwContents, this, this.mWebContentsDelegate, this.mContentsClientBridge, this.mIoThreadClient, getTencentAwContentsIoThreadClient(), this.mInterceptNavigationDelegate, this.mAutofillProvider);
    this.mWebContents = this.mContentViewCore.getWebContents();
    this.mWebContentsInternalsHolder = new WebContentsInternalsHolder(this, null);
    this.mWebContents.setInternalsHolder(this.mWebContentsInternalsHolder);
    GestureListenerManager.fromWebContents(this.mWebContents).addListener(new AwGestureStateListener(null));
    this.mNavigationController = this.mWebContents.getNavigationController();
    installWebContentsObserver();
    this.mSettings.setWebContents(localWebContents);
    AutofillProvider localAutofillProvider = this.mAutofillProvider;
    if (localAutofillProvider != null) {
      localAutofillProvider.setWebContents(localWebContents);
    }
    this.mDisplayObserver.onDIPScaleChanged(getDeviceScaleFactor());
    updateContentViewCoreVisibility();
    this.mCleanupReference = new CleanupReference(this, new AwContentsDestroyRunnable(this.mNativeAwContents, this.mWindowAndroid, null));
  }
  
  private void setPageScaleFactorAndLimits(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if ((this.mPageScaleFactor == paramFloat1) && (this.mMinPageScaleFactor == paramFloat2) && (this.mMaxPageScaleFactor == paramFloat3)) {
      return;
    }
    this.mMinPageScaleFactor = paramFloat2;
    this.mMaxPageScaleFactor = paramFloat3;
    paramFloat2 = this.mPageScaleFactor;
    if (paramFloat2 != paramFloat1)
    {
      this.mPageScaleFactor = paramFloat1;
      paramFloat1 = getDeviceScaleFactor();
      this.mContentsClient.getCallbackHelper().postOnScaleChangedScaled(paramFloat2 * paramFloat1, this.mPageScaleFactor * paramFloat1);
    }
  }
  
  public static void setShouldDownloadFavicons() {}
  
  private void setViewVisibilityInternal(boolean paramBoolean)
  {
    this.mIsViewVisible = paramBoolean;
    if (!isDestroyedOrNoOperation(0)) {
      nativeSetViewVisibility(this.mNativeAwContents, this.mIsViewVisible);
    }
    postUpdateContentViewCoreVisibility();
  }
  
  private void setWindowVisibilityInternal(boolean paramBoolean)
  {
    boolean bool2 = this.mInvalidateRootViewOnNextDraw;
    boolean bool1;
    if ((Build.VERSION.SDK_INT <= 21) && (paramBoolean) && (!this.mIsWindowVisible)) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    this.mInvalidateRootViewOnNextDraw = (bool2 | bool1);
    this.mIsWindowVisible = paramBoolean;
    if (!isDestroyedOrNoOperation(0)) {
      nativeSetWindowVisibility(this.mNativeAwContents, this.mIsWindowVisible);
    }
    postUpdateContentViewCoreVisibility();
  }
  
  private void updateChildProcessImportance()
  {
    boolean bool = this.mRendererPriorityWaivedWhenNotVisible;
    int i = 0;
    if ((!bool) || (this.mIsContentViewCoreVisible)) {
      switch (this.mRendererPriority)
      {
      default: 
        break;
      case 1: 
        i = 1;
        break;
      case -1: 
      case 2: 
        i = 2;
        break label71;
        i = 2;
      }
    }
    label71:
    this.mWebContents.setImportance(i);
  }
  
  private void updateContentViewCoreVisibility()
  {
    this.mIsUpdateVisibilityTaskPending = false;
    if (isDestroyedOrNoOperation(0)) {
      return;
    }
    boolean bool = nativeIsVisible(this.mNativeAwContents);
    if ((bool) && (!this.mIsContentViewCoreVisible)) {
      this.mContentViewCore.onShow();
    } else if ((!bool) && (this.mIsContentViewCoreVisible)) {
      this.mContentViewCore.onHide();
    }
    this.mIsContentViewCoreVisible = bool;
    updateChildProcessImportance();
  }
  
  @VisibleForTesting
  public static void updateDefaultLocale()
  {
    String str = LocaleUtils.getDefaultLocaleListString();
    if (!sCurrentLocales.equals(str))
    {
      sCurrentLocales = str;
      nativeUpdateDefaultLocale(LocaleUtils.getDefaultLocaleString(), sCurrentLocales);
    }
  }
  
  private void updateNativeAwGLFunctor()
  {
    long l2 = this.mNativeAwContents;
    AwGLFunctor localAwGLFunctor = this.mCurrentFunctor;
    long l1;
    if (localAwGLFunctor != null) {
      l1 = localAwGLFunctor.getNativeAwGLFunctor();
    } else {
      l1 = 0L;
    }
    nativeSetAwGLFunctor(l2, l1);
  }
  
  @CalledByNative
  private boolean useLegacyGeolocationPermissionAPI()
  {
    return true;
  }
  
  @SuppressLint({"NewApi"})
  public void addJavascriptInterface(Object paramObject, String paramString)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    Object localObject2 = null;
    Object localObject1 = localObject2;
    if (this.mAppTargetSdkVersion >= 17)
    {
      localObject1 = localObject2;
      if (Build.VERSION.SDK_INT >= 17) {
        localObject1 = JavascriptInterface.class;
      }
    }
    getJavascriptInjector().addPossiblyUnsafeInterface(paramObject, paramString, (Class)localObject1);
  }
  
  public void autofill(SparseArray<AutofillValue> paramSparseArray)
  {
    AutofillProvider localAutofillProvider = this.mAutofillProvider;
    if (localAutofillProvider != null) {
      localAutofillProvider.autofill(paramSparseArray);
    }
  }
  
  public boolean canGoBack()
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    return this.mNavigationController.canGoBack();
  }
  
  public boolean canGoBackOrForward(int paramInt)
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    return this.mNavigationController.canGoToOffset(paramInt);
  }
  
  public boolean canGoForward()
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    return this.mNavigationController.canGoForward();
  }
  
  @VisibleForTesting
  protected boolean canShowBigInterstitial()
  {
    double d1 = this.mContainerView.getHeight();
    double d2 = this.mContainerView.getRootView().getHeight();
    Double.isNaN(d1);
    Double.isNaN(d2);
    d1 /= d2;
    return (this.mContainerView.getWidth() == this.mContainerView.getRootView().getWidth()) && (d1 >= 0.7D);
  }
  
  @VisibleForTesting
  @CalledByNative
  protected boolean canShowInterstitial()
  {
    return (this.mIsAttachedToWindow) && (this.mIsViewVisible);
  }
  
  public boolean canZoomIn()
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    return this.mMaxPageScaleFactor - this.mPageScaleFactor > 0.007F;
  }
  
  public boolean canZoomOut()
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    return this.mPageScaleFactor - this.mMinPageScaleFactor > 0.007F;
  }
  
  public Picture capturePicture()
  {
    if (isDestroyedOrNoOperation(1)) {
      return null;
    }
    return new AwPicture(nativeCapturePicture(this.mNativeAwContents, this.mScrollOffsetManager.computeHorizontalScrollRange(), this.mScrollOffsetManager.computeVerticalScrollRange()));
  }
  
  public void clearCache(boolean paramBoolean)
  {
    if (!isDestroyedOrNoOperation(1)) {
      nativeClearCache(this.mNativeAwContents, paramBoolean);
    }
  }
  
  public void clearHistory()
  {
    if (!isDestroyedOrNoOperation(1)) {
      this.mNavigationController.clearHistory();
    }
  }
  
  public void clearMatches()
  {
    if (!isDestroyedOrNoOperation(1)) {
      nativeClearMatches(this.mNativeAwContents);
    }
  }
  
  public void clearSslPreferences()
  {
    if (!isDestroyedOrNoOperation(1)) {
      this.mNavigationController.clearSslPreferences();
    }
  }
  
  public void clearView()
  {
    if (!isDestroyedOrNoOperation(1)) {
      nativeClearView(this.mNativeAwContents);
    }
  }
  
  public int computeHorizontalScrollOffset()
  {
    return this.mAwViewMethods.computeHorizontalScrollOffset();
  }
  
  public int computeHorizontalScrollRange()
  {
    return this.mAwViewMethods.computeHorizontalScrollRange();
  }
  
  public void computeScroll()
  {
    this.mAwViewMethods.computeScroll();
  }
  
  public int computeVerticalScrollExtent()
  {
    return this.mAwViewMethods.computeVerticalScrollExtent();
  }
  
  public int computeVerticalScrollOffset()
  {
    return this.mAwViewMethods.computeVerticalScrollOffset();
  }
  
  public int computeVerticalScrollRange()
  {
    return this.mAwViewMethods.computeVerticalScrollRange();
  }
  
  public AppWebMessagePort[] createMessageChannel()
  {
    if (isDestroyedOrNoOperation(1)) {
      return null;
    }
    return AppWebMessagePort.createPair();
  }
  
  public void destroy()
  {
    if (isDestroyed(0)) {
      return;
    }
    this.mContentsClient.getCallbackHelper().a();
    if (this.mIsAttachedToWindow) {
      onDetachedFromWindow();
    }
    this.mIsNoOperation = true;
    this.mIsDestroyed = true;
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        AwContents.this.destroyNatives();
      }
    });
  }
  
  @CalledByNative
  protected void didOverscroll(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2)
  {
    this.mScrollOffsetManager.overScrollBy(paramInt1, paramInt2);
    OverScrollGlow localOverScrollGlow = this.mOverScrollGlow;
    if (localOverScrollGlow == null) {
      return;
    }
    localOverScrollGlow.setOverScrollDeltas(paramInt1, paramInt2);
    int i = this.mContainerView.getScrollX();
    int j = this.mContainerView.getScrollY();
    int k = this.mScrollOffsetManager.computeMaximumHorizontalScrollOffset();
    int m = this.mScrollOffsetManager.computeMaximumVerticalScrollOffset();
    this.mOverScrollGlow.absorbGlow(i + paramInt1, j + paramInt2, i, j, k, m, (float)Math.hypot(paramFloat1, paramFloat2));
    if (this.mOverScrollGlow.isAnimating()) {
      postInvalidateOnAnimation();
    }
  }
  
  public void disableJavascriptInterfacesInspection()
  {
    if (!isDestroyedOrNoOperation(1)) {
      getJavascriptInjector().setAllowInspection(false);
    }
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    return this.mAwViewMethods.dispatchKeyEvent(paramKeyEvent);
  }
  
  public void documentHasImages(Message paramMessage)
  {
    if (!isDestroyedOrNoOperation(1)) {
      nativeDocumentHasImages(this.mNativeAwContents, paramMessage);
    }
  }
  
  public void enableOnNewPicture(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    if (paramBoolean2) {
      this.mPictureListenerContentProvider = null;
    } else if ((paramBoolean1) && (this.mPictureListenerContentProvider == null)) {
      this.mPictureListenerContentProvider = new Callable()
      {
        public Picture call()
          throws Exception
        {
          return AwContents.this.capturePicture();
        }
      };
    }
    nativeEnableOnNewPicture(this.mNativeAwContents, paramBoolean1);
  }
  
  protected View enterFullScreen()
  {
    if (isDestroyedOrNoOperation(0)) {
      return null;
    }
    onDetachedFromWindow();
    FullScreenView localFullScreenView = new FullScreenView(this.mContext, this.mAwViewMethods, this);
    localFullScreenView.setFocusable(true);
    localFullScreenView.setFocusableInTouchMode(true);
    boolean bool = this.mContainerView.isFocused();
    if (bool) {
      localFullScreenView.requestFocus();
    }
    this.mFullScreenFunctor = new AwGLFunctor(this.mNativeDrawGLFunctorFactory, localFullScreenView);
    FullScreenTransitionsState.a(this.mFullScreenTransitionsState, localFullScreenView, bool);
    this.mAwViewMethods = new NullAwViewMethods(this, this.mInternalAccessAdapter, this.mContainerView);
    setInternalAccessAdapter(localFullScreenView.getInternalAccessAdapter());
    setContainerView(localFullScreenView, this.mFullScreenFunctor);
    return localFullScreenView;
  }
  
  public void evaluateJavaScript(String paramString, final Callback<String> paramCallback)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    JavaScriptCallback local10 = null;
    if (paramCallback != null) {
      local10 = new JavaScriptCallback()
      {
        public void handleJavaScriptResult(final String paramAnonymousString)
        {
          AwContents.this.mHandler.post(new Runnable()
          {
            public void run()
            {
              AwContents.10.this.a.onResult(paramAnonymousString);
            }
          });
        }
      };
    }
    this.mWebContents.evaluateJavaScript(paramString, local10);
  }
  
  public void evaluateJavaScriptForTests(String paramString, final Callback<String> paramCallback)
  {
    if (isDestroyedOrNoOperation(0)) {
      return;
    }
    JavaScriptCallback local11 = null;
    if (paramCallback != null) {
      local11 = new JavaScriptCallback()
      {
        public void handleJavaScriptResult(String paramAnonymousString)
        {
          paramCallback.onResult(paramAnonymousString);
        }
      };
    }
    this.mWebContents.evaluateJavaScriptForTests(paramString, local11);
  }
  
  @VisibleForTesting
  public void evaluateJavaScriptOnInterstitialForTesting(String paramString, final Callback<String> paramCallback)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    JavaScriptCallback local13 = null;
    if (paramCallback != null) {
      local13 = new JavaScriptCallback()
      {
        public void handleJavaScriptResult(String paramAnonymousString)
        {
          paramCallback.onResult(paramAnonymousString);
        }
      };
    }
    nativeEvaluateJavaScriptOnInterstitialForTesting(this.mNativeAwContents, paramString, local13);
  }
  
  void exitFullScreen()
  {
    if (isFullScreen())
    {
      if (isDestroyedOrNoOperation(0)) {
        return;
      }
      Object localObject = FullScreenTransitionsState.a(this.mFullScreenTransitionsState);
      ((AwViewMethods)localObject).onDetachedFromWindow();
      FullScreenView localFullScreenView = FullScreenTransitionsState.a(this.mFullScreenTransitionsState);
      localFullScreenView.setAwViewMethods(new NullAwViewMethods(this, localFullScreenView.getInternalAccessAdapter(), localFullScreenView));
      this.mAwViewMethods = ((AwViewMethods)localObject);
      localObject = FullScreenTransitionsState.a(this.mFullScreenTransitionsState);
      setInternalAccessAdapter(FullScreenTransitionsState.a(this.mFullScreenTransitionsState));
      setContainerView((ViewGroup)localObject, this.mInitialFunctor);
      if (FullScreenTransitionsState.a(this.mFullScreenTransitionsState)) {
        this.mContainerView.requestFocus();
      }
      FullScreenTransitionsState.a(this.mFullScreenTransitionsState);
      this.mFullScreenFunctor = null;
      return;
    }
  }
  
  public void extractSmartClipData(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!isDestroyedOrNoOperation(1)) {
      this.mWebContents.requestSmartClipExtract(paramInt1, paramInt2, paramInt3, paramInt4);
    }
  }
  
  public void findAllAsync(String paramString)
  {
    if (!isDestroyedOrNoOperation(1)) {
      nativeFindAllAsync(this.mNativeAwContents, paramString);
    }
  }
  
  public void findNext(boolean paramBoolean)
  {
    if (!isDestroyedOrNoOperation(1)) {
      nativeFindNext(this.mNativeAwContents, paramBoolean);
    }
  }
  
  public void flingScroll(int paramInt1, int paramInt2)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    this.mWebContents.getEventForwarder().a(SystemClock.uptimeMillis(), -paramInt1, -paramInt2, false);
  }
  
  public AccessibilityNodeProvider getAccessibilityNodeProvider()
  {
    return this.mAwViewMethods.getAccessibilityNodeProvider();
  }
  
  public SslCertificate getCertificate()
  {
    if (isDestroyedOrNoOperation(1)) {
      return null;
    }
    return SslUtil.getCertificateFromDerBytes(nativeGetCertificate(this.mNativeAwContents));
  }
  
  public int getContentHeightCss()
  {
    if (isDestroyedOrNoOperation(1)) {
      return 0;
    }
    return (int)Math.ceil(this.mContentHeightDip);
  }
  
  @VisibleForTesting
  public ContentViewCore getContentViewCore()
  {
    return this.mContentViewCore;
  }
  
  public int getContentWidthCss()
  {
    if (isDestroyedOrNoOperation(1)) {
      return 0;
    }
    return (int)Math.ceil(this.mContentWidthDip);
  }
  
  public float getDeviceScaleFactor()
  {
    return this.mWindowAndroid.getWindowAndroid().getDisplay().getDipScale();
  }
  
  public int getEffectiveBackgroundColor()
  {
    if ((!isDestroyedOrNoOperation(0)) && (this.mContentsClient.isCachedRendererBackgroundColorValid())) {
      return this.mContentsClient.getCachedRendererBackgroundColor();
    }
    return this.mBaseBackgroundColor;
  }
  
  @VisibleForTesting
  public int getEffectivePriorityForTesting()
  {
    return nativeGetEffectivePriority(this.mNativeAwContents);
  }
  
  public Bitmap getFavicon()
  {
    if (isDestroyedOrNoOperation(1)) {
      return null;
    }
    return this.mFavicon;
  }
  
  public AwGeolocationPermissions getGeolocationPermissions()
  {
    return this.mBrowserContext.getGeolocationPermissions();
  }
  
  protected Rect getGlobalVisibleRect()
  {
    if (!this.mContainerView.getGlobalVisibleRect(sLocalGlobalVisibleRect)) {
      sLocalGlobalVisibleRect.setEmpty();
    }
    return sLocalGlobalVisibleRect;
  }
  
  public String getLastCommittedUrl()
  {
    if (isDestroyedOrNoOperation(0)) {
      return null;
    }
    String str = this.mWebContents.getLastCommittedUrl();
    if (str != null)
    {
      if (str.trim().isEmpty()) {
        return null;
      }
      return str;
    }
    return null;
  }
  
  public HitTestData getLastHitTestResult()
  {
    if (isDestroyedOrNoOperation(1)) {
      return null;
    }
    nativeUpdateLastHitTestData(this.mNativeAwContents);
    return this.mPossiblyStaleHitTestData;
  }
  
  public int getMostRecentProgress()
  {
    if (isDestroyedOrNoOperation(1)) {
      return 0;
    }
    return this.mWebContentsDelegate.getMostRecentProgress();
  }
  
  @VisibleForTesting
  public NavigationController getNavigationController()
  {
    return this.mNavigationController;
  }
  
  public NavigationHistory getNavigationHistory()
  {
    if (isDestroyedOrNoOperation(1)) {
      return null;
    }
    return this.mNavigationController.getNavigationHistory();
  }
  
  public String getOriginalUrl()
  {
    if (isDestroyedOrNoOperation(1)) {
      return null;
    }
    NavigationHistory localNavigationHistory = this.mNavigationController.getNavigationHistory();
    int i = localNavigationHistory.getCurrentEntryIndex();
    if ((i >= 0) && (i < localNavigationHistory.getEntryCount())) {
      return localNavigationHistory.getEntryAtIndex(i).getOriginalUrl();
    }
    return null;
  }
  
  @VisibleForTesting
  public float getPageScaleFactor()
  {
    return this.mPageScaleFactor;
  }
  
  public AwPdfExporter getPdfExporter()
  {
    if (this.mNativeAwContents == 0L) {
      return null;
    }
    if (isDestroyedOrNoOperation(1)) {
      return null;
    }
    if (this.mAwPdfExporter == null)
    {
      this.mAwPdfExporter = new AwPdfExporter(this.mContainerView);
      nativeCreatePdfExporter(this.mNativeAwContents, this.mAwPdfExporter);
    }
    return this.mAwPdfExporter;
  }
  
  public boolean getRendererPriorityWaivedWhenNotVisible()
  {
    return this.mRendererPriorityWaivedWhenNotVisible;
  }
  
  public int getRendererRequestedPriority()
  {
    return this.mRendererPriority;
  }
  
  public float getScale()
  {
    if (isDestroyedOrNoOperation(1)) {
      return 1.0F;
    }
    return this.mPageScaleFactor * getDeviceScaleFactor();
  }
  
  public AwSettings getSettings()
  {
    return this.mSettings;
  }
  
  protected TencentAwContentsIoThreadClient getTencentAwContentsIoThreadClient()
  {
    X5ApiCompatibilityUtils.mustBeOverride();
    return null;
  }
  
  public TextClassifier getTextClassifier()
  {
    return ClassAdapter.fromWebContentsSelectionPopupController(this.mWebContents).getTextClassifier();
  }
  
  public String getTitle()
  {
    if (isDestroyedOrNoOperation(1)) {
      return null;
    }
    return this.mWebContents.getTitle();
  }
  
  public String getUrl()
  {
    if (isDestroyedOrNoOperation(1)) {
      return null;
    }
    String str = this.mWebContents.getVisibleUrl();
    if (str != null)
    {
      if (str.trim().isEmpty()) {
        return null;
      }
      return str;
    }
    return null;
  }
  
  @VisibleForTesting
  public WebContents getWebContents()
  {
    return this.mWebContents;
  }
  
  public WebContentsAccessibility getWebContentsAccessibility()
  {
    return ClassAdapter.fromWebContentsWebContentsAccessibility(this.mWebContents);
  }
  
  public View getZoomControlsForTest()
  {
    return this.mZoomControls.a();
  }
  
  public void goBack()
  {
    if (!isDestroyedOrNoOperation(1)) {
      this.mNavigationController.goBack();
    }
  }
  
  public void goBackOrForward(int paramInt)
  {
    if (!isDestroyedOrNoOperation(1)) {
      this.mNavigationController.goToOffset(paramInt);
    }
  }
  
  public void goForward()
  {
    if (!isDestroyedOrNoOperation(1)) {
      this.mNavigationController.goForward();
    }
  }
  
  public boolean hasAccessedInitialDocument()
  {
    if (isDestroyedOrNoOperation(0)) {
      return false;
    }
    return this.mWebContents.hasAccessedInitialDocument();
  }
  
  public void hideAutofillPopup()
  {
    AwAutofillClient localAwAutofillClient = this.mAwAutofillClient;
    if (localAwAutofillClient != null) {
      localAwAutofillClient.hideAutofillPopup();
    }
  }
  
  protected AwScrollOffsetManagerDelegate initAwScrollOffsetManagerDelegate()
  {
    X5ApiCompatibilityUtils.mustBeOverride();
    return new AwScrollOffsetManagerDelegate();
  }
  
  protected AwViewMethodsImpl initAwViewMethodsImpl()
  {
    X5ApiCompatibilityUtils.mustBeOverride();
    return new AwViewMethodsImpl();
  }
  
  protected void initTencentAwContentsIoThreadClient(Context paramContext, AwContentsClient paramAwContentsClient) {}
  
  public void injectJavascript(String paramString)
  {
    getJavascriptInjector().injectJavascript(paramString);
  }
  
  public void insertVisualStateCallback(long paramLong, VisualStateCallback paramVisualStateCallback)
  {
    if (!isDestroyed(0))
    {
      nativeInsertVisualStateCallback(this.mNativeAwContents, paramLong, paramVisualStateCallback);
      return;
    }
    throw new IllegalStateException("insertVisualStateCallback cannot be called after the WebView has been destroyed");
  }
  
  protected void insertVisualStateCallbackIfNotDestroyed(long paramLong, VisualStateCallback paramVisualStateCallback)
  {
    if (isDestroyedOrNoOperation(0)) {
      return;
    }
    nativeInsertVisualStateCallback(this.mNativeAwContents, paramLong, paramVisualStateCallback);
  }
  
  public void invokeGeolocationCallback(boolean paramBoolean, String paramString)
  {
    if (isDestroyedOrNoOperation(0)) {
      return;
    }
    nativeInvokeGeolocationCallback(this.mNativeAwContents, paramBoolean, paramString);
  }
  
  @CalledByNative
  public void invokeVisualStateCallback(final VisualStateCallback paramVisualStateCallback, final long paramLong)
  {
    if (isDestroyedOrNoOperation(0)) {
      return;
    }
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        paramVisualStateCallback.onComplete(paramLong);
      }
    });
  }
  
  public void invokeZoomPicker()
  {
    if (!isDestroyedOrNoOperation(1)) {
      this.mZoomControls.invokeZoomPicker();
    }
  }
  
  protected boolean isDestroyed(int paramInt)
  {
    boolean bool1 = this.mIsDestroyed;
    Object localObject = this.mCleanupReference;
    boolean bool2 = true;
    if ((localObject != null) && (((CleanupReference)localObject).hasCleanedUp())) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    localObject = this.mWebContentsInternalsHolder;
    int i;
    if ((localObject != null) && (((WebContentsInternalsHolder)localObject).weakRefCleared())) {
      i = 1;
    } else {
      i = 0;
    }
    bool1 = bool2;
    if (!this.mIsDestroyed)
    {
      bool1 = bool2;
      if (paramInt == 0)
      {
        if (i != 0) {
          return true;
        }
        bool1 = false;
      }
    }
    return bool1;
  }
  
  protected boolean isDestroyedOrNoOperation(int paramInt)
  {
    return (isDestroyed(paramInt)) || (isNoOperation());
  }
  
  public boolean isFullScreen()
  {
    return this.mFullScreenTransitionsState.isFullScreen();
  }
  
  public boolean isMultiTouchZoomSupported()
  {
    return this.mSettings.supportsMultiTouchZoom();
  }
  
  @VisibleForTesting
  public boolean isPageVisible()
  {
    if (isDestroyedOrNoOperation(0)) {
      return this.mIsContentViewCoreVisible;
    }
    return nativeIsVisible(this.mNativeAwContents);
  }
  
  public boolean isPaused()
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    return this.mIsPaused;
  }
  
  public boolean isPopupWindow()
  {
    return this.mIsPopupWindow;
  }
  
  public boolean isSelectActionModeAllowed(int paramInt)
  {
    return (this.mSettings.getDisabledActionModeMenuItems() & paramInt) != paramInt;
  }
  
  @VisibleForTesting
  public void killRenderProcess()
  {
    if (!isDestroyedOrNoOperation(1))
    {
      nativeKillRenderProcess(this.mNativeAwContents);
      return;
    }
    throw new IllegalStateException("killRenderProcess() shouldn't be invoked after render process is gone or webview is destroyed");
  }
  
  public void loadData(String paramString1, String paramString2, String paramString3)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    loadUrl(LoadUrlParams.a(fixupData(paramString1), fixupMimeType(paramString2), isBase64Encoded(paramString3)));
  }
  
  public void loadDataWithBaseURL(final String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    paramString2 = fixupData(paramString2);
    paramString3 = fixupMimeType(paramString3);
    paramString1 = fixupBase(paramString1);
    paramString5 = fixupHistory(paramString5);
    if (paramString5.equals("about:blank")) {
      recordHistoryUrl(0);
    } else if (paramString5.equals(paramString1)) {
      recordHistoryUrl(1);
    } else {
      recordHistoryUrl(2);
    }
    if (paramString1.startsWith("data:"))
    {
      boolean bool = isBase64Encoded(paramString4);
      if (bool) {
        paramString4 = null;
      }
      paramString1 = LoadUrlParams.a(paramString2, paramString3, bool, paramString1, paramString5, paramString4);
    }
    try
    {
      paramString1 = LoadUrlParams.a(Base64.encodeToString(paramString2.getBytes("utf-8"), 0), paramString3, true, paramString1, paramString5, "utf-8");
      if ((isSamsungMailApp()) && ("email://".equals(paramString1.b())))
      {
        ThreadUtils.postOnUiThreadDelayed(new Runnable()
        {
          public void run()
          {
            AwContents.this.loadUrl(paramString1);
          }
        }, 200L);
        return;
      }
      loadUrl(paramString1);
      return;
    }
    catch (UnsupportedEncodingException paramString1)
    {
      Log.wtf("AwContents", "Unable to load data string %s", new Object[] { paramString2, paramString1 });
    }
  }
  
  public void loadUrl(String paramString)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    if (paramString == null) {
      return;
    }
    loadUrl(paramString, null);
  }
  
  public void loadUrl(String paramString, Map<String, String> paramMap)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    if ((this.mAppTargetSdkVersion < 19) && (paramString != null) && (paramString.startsWith("javascript:")))
    {
      evaluateJavaScript(paramString.substring(11), null);
      return;
    }
    paramString = new LoadUrlParams(paramString);
    if (paramMap != null) {
      paramString.a(new HashMap(paramMap));
    }
    loadUrl(paramString);
  }
  
  @VisibleForTesting
  public void loadUrl(LoadUrlParams paramLoadUrlParams)
  {
    if ((paramLoadUrlParams.c() == 2) && (!paramLoadUrlParams.d()))
    {
      paramLoadUrlParams.a(true);
      nativeGrantFileSchemeAccesstoChildProcess(this.mNativeAwContents);
    }
    if ((paramLoadUrlParams.a() != null) && (paramLoadUrlParams.a().equals(this.mWebContents.getLastCommittedUrl())) && (paramLoadUrlParams.a() == 0)) {
      paramLoadUrlParams.b(8);
    }
    paramLoadUrlParams.b(paramLoadUrlParams.a() | 0x8000000);
    paramLoadUrlParams.c(2);
    Map localMap = paramLoadUrlParams.a();
    if (localMap != null)
    {
      Iterator localIterator = localMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if ("referer".equals(str.toLowerCase(Locale.US)))
        {
          paramLoadUrlParams.a(new Referrer((String)localMap.remove(str), 1));
          paramLoadUrlParams.a(localMap);
        }
      }
    }
    nativeSetExtraHeadersForUrl(this.mNativeAwContents, paramLoadUrlParams.a(), paramLoadUrlParams.d());
    paramLoadUrlParams.a(new HashMap());
    this.mNavigationController.loadUrl(paramLoadUrlParams);
    if (!this.mHasRequestedVisitedHistoryFromClient)
    {
      this.mHasRequestedVisitedHistoryFromClient = true;
      requestVisitedHistoryFromClient();
    }
    if ((!BrowserSideNavigationPolicy.a()) && (paramLoadUrlParams.c() == 2) && (paramLoadUrlParams.b() != null)) {
      this.mContentsClient.getCallbackHelper().postOnPageStarted(paramLoadUrlParams.b());
    }
  }
  
  protected native float nativeGetCurrentPageMaxVerticalScrollRatio(long paramLong);
  
  protected native float nativeGetCurrentPageNumber(long paramLong);
  
  protected native void nativeGrantFileSchemeAccesstoChildProcess(long paramLong);
  
  protected native void nativeHideSelectedAdElement(long paramLong, int paramInt1, int paramInt2, String paramString);
  
  protected native void nativeOnComputeScroll(long paramLong1, long paramLong2);
  
  protected native boolean nativeOnDraw(long paramLong, Canvas paramCanvas, boolean paramBoolean1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean2);
  
  protected native void nativeRequestNewHitTestDataAt(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3);
  
  protected native void nativeUpdateLastHitTestBitmap(long paramLong);
  
  @CalledByNative
  protected void notifyRenderType(boolean paramBoolean) {}
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (isDestroyedOrNoOperation(0)) {
      return;
    }
    if (paramInt1 == 100)
    {
      ClassAdapter.fromWebContentsSelectionPopupController(this.mWebContents).onReceivedProcessTextResult(paramInt2, paramIntent);
      return;
    }
    Log.e("AwContents", "Received activity result for an unknown request code %d", new Object[] { Integer.valueOf(paramInt1) });
  }
  
  public void onAttachedToWindow()
  {
    this.mTemporarilyDetached = false;
    this.mAwViewMethods.onAttachedToWindow();
    this.mWindowAndroid.getWindowAndroid().getDisplay().addObserver(this.mDisplayObserver);
  }
  
  public boolean onCheckIsTextEditor()
  {
    return this.mAwViewMethods.onCheckIsTextEditor();
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    this.mAwViewMethods.onConfigurationChanged(paramConfiguration);
  }
  
  public void onContainerViewOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mAwViewMethods.onContainerViewOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
  }
  
  public void onContainerViewScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mAwViewMethods.onContainerViewScrollChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    return this.mAwViewMethods.onCreateInputConnection(paramEditorInfo);
  }
  
  @VisibleForTesting
  protected void onDestroyed() {}
  
  @SuppressLint({"MissingSuperCall"})
  public void onDetachedFromWindow()
  {
    this.mWindowAndroid.getWindowAndroid().getDisplay().removeObserver(this.mDisplayObserver);
    this.mAwViewMethods.onDetachedFromWindow();
  }
  
  public boolean onDragEvent(DragEvent paramDragEvent)
  {
    return this.mAwViewMethods.onDragEvent(paramDragEvent);
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    try
    {
      TraceEvent.begin("AwContents.onDraw");
      this.mAwViewMethods.onDraw(paramCanvas);
      return;
    }
    finally
    {
      TraceEvent.end("AwContents.onDraw");
    }
  }
  
  @CalledByNative
  public void onFindResultReceived(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    this.mContentsClient.onFindResultReceived(paramInt1, paramInt2, paramBoolean);
  }
  
  public void onFinishTemporaryDetach()
  {
    this.mTemporarilyDetached = false;
  }
  
  public void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    if (!this.mTemporarilyDetached) {
      this.mAwViewMethods.onFocusChanged(paramBoolean, paramInt, paramRect);
    }
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if (isDestroyedOrNoOperation(0)) {
      return false;
    }
    return this.mContentViewCore.onGenericMotionEvent(paramMotionEvent);
  }
  
  public boolean onHoverEvent(MotionEvent paramMotionEvent)
  {
    return this.mAwViewMethods.onHoverEvent(paramMotionEvent);
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return this.mAwViewMethods.onKeyUp(paramInt, paramKeyEvent);
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    this.mAwViewMethods.onMeasure(paramInt1, paramInt2);
  }
  
  @CalledByNative
  public void onNewPicture()
  {
    this.mContentsClient.getCallbackHelper().postOnNewPicture(this.mPictureListenerContentProvider);
  }
  
  public void onPause()
  {
    if (!this.mIsPaused)
    {
      if (isDestroyedOrNoOperation(0)) {
        return;
      }
      this.mIsPaused = true;
      nativeSetIsPaused(this.mNativeAwContents, this.mIsPaused);
      updateContentViewCoreVisibility();
      return;
    }
  }
  
  @CalledByNative
  protected void onPermissionRequest(AwPermissionRequest paramAwPermissionRequest)
  {
    this.mContentsClient.onPermissionRequest(paramAwPermissionRequest);
  }
  
  public void onProvideAutoFillVirtualStructure(ViewStructure paramViewStructure, int paramInt)
  {
    AutofillProvider localAutofillProvider = this.mAutofillProvider;
    if (localAutofillProvider != null) {
      localAutofillProvider.onProvideAutoFillVirtualStructure(paramViewStructure, paramInt);
    }
  }
  
  @TargetApi(23)
  public void onProvideVirtualStructure(ViewStructure paramViewStructure)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    if (!this.mWebContentsObserver.didEverCommitNavigation())
    {
      paramViewStructure.setChildCount(0);
      return;
    }
    getWebContentsAccessibility().onProvideVirtualStructure(paramViewStructure, true);
  }
  
  @VisibleForTesting
  @CalledByNative
  protected void onRenderProcessGone(int paramInt)
  {
    this.mIsNoOperation = true;
  }
  
  @VisibleForTesting
  @CalledByNative
  protected boolean onRenderProcessGoneDetail(int paramInt, boolean paramBoolean)
  {
    if (isDestroyed(0)) {
      return true;
    }
    return this.mContentsClient.onRenderProcessGone(new AwRenderProcessGoneDetail(paramBoolean, nativeGetEffectivePriority(this.mNativeAwContents)));
  }
  
  public void onResume()
  {
    if (this.mIsPaused)
    {
      if (isDestroyedOrNoOperation(0)) {
        return;
      }
      this.mIsPaused = false;
      nativeSetIsPaused(this.mNativeAwContents, this.mIsPaused);
      updateContentViewCoreVisibility();
      return;
    }
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mAwViewMethods.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public void onStartTemporaryDetach()
  {
    this.mTemporarilyDetached = true;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return this.mAwViewMethods.onTouchEvent(paramMotionEvent);
  }
  
  public void onVisibilityChanged(View paramView, int paramInt)
  {
    this.mAwViewMethods.onVisibilityChanged(paramView, paramInt);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    this.mAwViewMethods.onWindowFocusChanged(paramBoolean);
  }
  
  public void onWindowVisibilityChanged(int paramInt)
  {
    this.mAwViewMethods.onWindowVisibilityChanged(paramInt);
  }
  
  public boolean overlayHorizontalScrollbar()
  {
    return this.mOverlayHorizontalScrollbar;
  }
  
  public boolean overlayVerticalScrollbar()
  {
    return this.mOverlayVerticalScrollbar;
  }
  
  public boolean pageDown(boolean paramBoolean)
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    return this.mScrollOffsetManager.pageDown(paramBoolean);
  }
  
  public boolean pageUp(boolean paramBoolean)
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    return this.mScrollOffsetManager.pageUp(paramBoolean);
  }
  
  public void pauseTimers()
  {
    if (!isDestroyedOrNoOperation(1)) {
      ContentViewStatics.setWebKitSharedTimersSuspended(true);
    }
  }
  
  public boolean performAccessibilityAction(int paramInt, Bundle paramBundle)
  {
    return this.mAwViewMethods.performAccessibilityAction(paramInt, paramBundle);
  }
  
  @CalledByNative
  protected void postInvalidateOnAnimation()
  {
    if ((!this.mWindowAndroid.getWindowAndroid().isInsideVSync()) && (Build.VERSION.SDK_INT >= 17))
    {
      this.mContainerView.postInvalidateOnAnimation();
      return;
    }
    this.mContainerView.invalidate();
  }
  
  public void postMessageToFrame(String paramString1, String paramString2, String paramString3, MessagePort[] paramArrayOfMessagePort)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    this.mWebContents.postMessageToFrame(paramString1, paramString2, null, paramString3, paramArrayOfMessagePort);
  }
  
  public void postUrl(String paramString, byte[] paramArrayOfByte)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    paramString = LoadUrlParams.a(paramString, paramArrayOfByte);
    paramArrayOfByte = new HashMap();
    paramArrayOfByte.put("Content-Type", "application/x-www-form-urlencoded");
    paramString.a(paramArrayOfByte);
    loadUrl(paramString);
  }
  
  public void preauthorizePermission(Uri paramUri, long paramLong)
  {
    if (isDestroyedOrNoOperation(0)) {
      return;
    }
    nativePreauthorizePermission(this.mNativeAwContents, paramUri.toString(), paramLong);
  }
  
  protected void receivePopupContents(long paramLong)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    this.mDeferredShouldOverrideUrlLoadingIsPendingForPopup = true;
    boolean bool1 = this.mIsAttachedToWindow;
    boolean bool2 = this.mIsViewVisible;
    boolean bool3 = this.mIsWindowVisible;
    boolean bool4 = this.mIsPaused;
    boolean bool5 = this.mContainerViewFocused;
    boolean bool6 = this.mWindowFocused;
    if (bool5) {
      onFocusChanged(false, 0, null);
    }
    if (bool6) {
      onWindowFocusChanged(false);
    }
    if (bool2) {
      setViewVisibilityInternal(false);
    }
    if (bool3) {
      setWindowVisibilityInternal(false);
    }
    if (bool1) {
      onDetachedFromWindow();
    }
    if (!bool4) {
      onPause();
    }
    Object localObject = new HashMap();
    if (this.mContentViewCore != null) {
      ((Map)localObject).putAll(getJavascriptInjector().getInterfaces());
    }
    setNewAwContents(paramLong);
    nativeResumeLoadingCreatedPopupWebContents(this.mNativeAwContents);
    if (!bool4) {
      onResume();
    }
    if (bool1)
    {
      onAttachedToWindow();
      postInvalidateOnAnimation();
    }
    onSizeChanged(this.mContainerView.getWidth(), this.mContainerView.getHeight(), 0, 0);
    if (bool3) {
      setWindowVisibilityInternal(true);
    }
    if (bool2) {
      setViewVisibilityInternal(true);
    }
    if (bool6) {
      onWindowFocusChanged(bool6);
    }
    if (bool5) {
      onFocusChanged(true, 0, null);
    }
    this.mIsPopupWindow = true;
    localObject = ((Map)localObject).entrySet().iterator();
    while (((Iterator)localObject).hasNext())
    {
      Map.Entry localEntry = (Map.Entry)((Iterator)localObject).next();
      Class localClass = (Class)((Pair)localEntry.getValue()).second;
      getJavascriptInjector().addPossiblyUnsafeInterface(((Pair)localEntry.getValue()).first, (String)localEntry.getKey(), localClass);
    }
  }
  
  public void reload()
  {
    if (!isDestroyedOrNoOperation(1)) {
      this.mNavigationController.reload(true);
    }
  }
  
  public void removeJavascriptInterface(String paramString)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    getJavascriptInjector().removeInterface(paramString);
  }
  
  public boolean requestChildRectangleOnScreen(View paramView, Rect paramRect, boolean paramBoolean)
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    return this.mScrollOffsetManager.requestChildRectangleOnScreen(paramView.getLeft() - paramView.getScrollX(), paramView.getTop() - paramView.getScrollY(), paramRect, paramBoolean);
  }
  
  void requestExitFullscreen()
  {
    if (!isDestroyedOrNoOperation(0)) {
      this.mContentViewCore.getWebContents().exitFullscreen();
    }
  }
  
  public void requestFocus()
  {
    this.mAwViewMethods.requestFocus();
  }
  
  public void requestFocusNodeHref(Message paramMessage)
  {
    if (paramMessage != null)
    {
      if (isDestroyedOrNoOperation(1)) {
        return;
      }
      nativeUpdateLastHitTestData(this.mNativeAwContents);
      Bundle localBundle = paramMessage.getData();
      localBundle.putString("url", this.mPossiblyStaleHitTestData.href);
      localBundle.putString("title", this.mPossiblyStaleHitTestData.anchorText);
      localBundle.putString("src", this.mPossiblyStaleHitTestData.imgSrc);
      paramMessage.setData(localBundle);
      paramMessage.sendToTarget();
      return;
    }
  }
  
  public void requestImageRef(Message paramMessage)
  {
    if (paramMessage != null)
    {
      if (isDestroyedOrNoOperation(1)) {
        return;
      }
      nativeUpdateLastHitTestData(this.mNativeAwContents);
      Bundle localBundle = paramMessage.getData();
      localBundle.putString("url", this.mPossiblyStaleHitTestData.imgSrc);
      paramMessage.setData(localBundle);
      paramMessage.sendToTarget();
      return;
    }
  }
  
  public boolean restoreState(Bundle paramBundle)
  {
    if (!isDestroyedOrNoOperation(1))
    {
      if (paramBundle == null) {
        return false;
      }
      paramBundle = paramBundle.getByteArray("WEBVIEW_CHROMIUM_STATE");
      if (paramBundle == null) {
        return false;
      }
      boolean bool = nativeRestoreFromOpaqueState(this.mNativeAwContents, paramBundle);
      if (bool) {
        this.mContentsClient.onReceivedTitle(this.mWebContents.getTitle());
      }
      return bool;
    }
    return false;
  }
  
  public void resumeTimers()
  {
    if (!isDestroyedOrNoOperation(1)) {
      ContentViewStatics.setWebKitSharedTimersSuspended(false);
    }
  }
  
  public boolean saveState(Bundle paramBundle)
  {
    if (!isDestroyedOrNoOperation(1))
    {
      if (paramBundle == null) {
        return false;
      }
      try
      {
        byte[] arrayOfByte = nativeGetOpaqueState(this.mNativeAwContents);
        if (arrayOfByte == null) {
          return false;
        }
        paramBundle.putByteArray("WEBVIEW_CHROMIUM_STATE", arrayOfByte);
        return true;
      }
      catch (Throwable paramBundle)
      {
        paramBundle.printStackTrace();
        return false;
      }
    }
    return false;
  }
  
  public void saveWebArchive(final String paramString, boolean paramBoolean, final Callback<String> paramCallback)
  {
    if (!paramBoolean)
    {
      saveWebArchiveInternal(paramString, paramCallback);
      return;
    }
    new AsyncTask()
    {
      protected String a(Void... paramAnonymousVarArgs)
      {
        return AwContents.generateArchiveAutoNamePath(AwContents.this.getOriginalUrl(), paramString);
      }
      
      protected void a(String paramAnonymousString)
      {
        AwContents.this.saveWebArchiveInternal(paramAnonymousString, paramCallback);
      }
    }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
  }
  
  public void setBackgroundColor(int paramInt)
  {
    this.mBaseBackgroundColor = paramInt;
    if (!isDestroyedOrNoOperation(1)) {
      nativeSetBackgroundColor(this.mNativeAwContents, paramInt);
    }
  }
  
  protected void setContainerView(ViewGroup paramViewGroup, AwGLFunctor paramAwGLFunctor)
  {
    this.mContainerView = paramViewGroup;
    this.mCurrentFunctor = paramAwGLFunctor;
    updateNativeAwGLFunctor();
    this.mContainerView.setWillNotDraw(false);
    this.mViewAndroidDelegate.updateCurrentContainerView(this.mContainerView, this.mWindowAndroid.getWindowAndroid().getDisplay());
    this.mContentViewCore.setContainerView(this.mContainerView);
    paramAwGLFunctor = this.mAwPdfExporter;
    if (paramAwGLFunctor != null) {
      paramAwGLFunctor.setContainerView(this.mContainerView);
    }
    this.mWebContentsDelegate.setContainerView(this.mContainerView);
    paramAwGLFunctor = this.mTouchHandleDrawables.iterator();
    while (paramAwGLFunctor.hasNext()) {
      ((PopupTouchHandleDrawable)paramAwGLFunctor.next()).onContainerViewChanged(paramViewGroup);
    }
    onContainerViewChanged();
  }
  
  public void setHorizontalScrollbarOverlay(boolean paramBoolean)
  {
    this.mOverlayHorizontalScrollbar = paramBoolean;
  }
  
  public void setLayerType(int paramInt, Paint paramPaint)
  {
    this.mAwViewMethods.setLayerType(paramInt, paramPaint);
  }
  
  public void setLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    this.mLayoutSizer.onLayoutParamsChange();
  }
  
  public void setNetworkAvailable(boolean paramBoolean)
  {
    if (!isDestroyedOrNoOperation(1))
    {
      NetworkChangeNotifier.setAutoDetectConnectivityState(false);
      nativeSetJsOnlineProperty(this.mNativeAwContents, paramBoolean);
    }
  }
  
  public void setOverScrollMode(int paramInt)
  {
    if (paramInt != 2)
    {
      this.mOverScrollGlow = new OverScrollGlow(this.mContext, this.mContainerView);
      return;
    }
    this.mOverScrollGlow = null;
  }
  
  public void setRendererPriorityPolicy(int paramInt, boolean paramBoolean)
  {
    this.mRendererPriority = paramInt;
    this.mRendererPriorityWaivedWhenNotVisible = paramBoolean;
    updateChildProcessImportance();
  }
  
  public void setScrollBarStyle(int paramInt)
  {
    if ((paramInt != 0) && (paramInt != 33554432))
    {
      this.mOverlayVerticalScrollbar = false;
      this.mOverlayHorizontalScrollbar = false;
      return;
    }
    this.mOverlayVerticalScrollbar = true;
    this.mOverlayHorizontalScrollbar = true;
  }
  
  public void setSmartClipResultHandler(Handler paramHandler)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    this.mWebContents.setSmartClipResultHandler(paramHandler);
  }
  
  public void setTextClassifier(TextClassifier paramTextClassifier)
  {
    ClassAdapter.fromWebContentsSelectionPopupController(this.mWebContents).setTextClassifier(paramTextClassifier);
  }
  
  public void setVerticalScrollbarOverlay(boolean paramBoolean)
  {
    this.mOverlayVerticalScrollbar = paramBoolean;
  }
  
  public void shouldIgnoreNavigationImpl(NavigationParams paramNavigationParams, boolean paramBoolean) {}
  
  void startActivityForResult(Intent paramIntent, int paramInt)
  {
    FullScreenTransitionsState.a(this.mFullScreenTransitionsState).super_startActivityForResult(paramIntent, paramInt);
  }
  
  void startProcessTextIntent(Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT == 23) {
      paramIntent.putExtra("android.intent.extra.PROCESS_TEXT_READONLY", true);
    }
    if (WindowAndroid.activityFromContext(this.mContext) == null)
    {
      this.mContext.startActivity(paramIntent);
      return;
    }
    startActivityForResult(paramIntent, 100);
  }
  
  public void stopLoading()
  {
    if (!isDestroyedOrNoOperation(1)) {
      this.mWebContents.stop();
    }
  }
  
  public void supplyContentsForPopup(AwContents paramAwContents)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    long l = nativeReleasePopupAwContents(this.mNativeAwContents);
    if (l == 0L)
    {
      if (paramAwContents != null) {
        paramAwContents.destroy();
      }
      return;
    }
    if (paramAwContents == null)
    {
      nativeDestroy(l);
      return;
    }
    paramAwContents.receivePopupContents(l);
  }
  
  public boolean supportsAccessibilityAction(int paramInt)
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    return getWebContentsAccessibility().supportsAction(paramInt);
  }
  
  @CalledByNative
  protected void updateHitTestBitmap(Bitmap paramBitmap) {}
  
  @CalledByNative
  protected void updateHitTestData(int paramInt, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    TencentAwContent.TencentHitTestData localTencentHitTestData = this.mPossiblyStaleHitTestData;
    localTencentHitTestData.hitTestResultType = paramInt;
    localTencentHitTestData.hitTestResultExtraData = paramString1;
    localTencentHitTestData.href = paramString2;
    localTencentHitTestData.anchorText = paramString3;
    localTencentHitTestData.imgSrc = paramString4;
  }
  
  @CalledByNative
  protected void updateScrollState(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    this.mContentWidthDip = paramInt3;
    this.mContentHeightDip = paramInt4;
    this.mScrollOffsetManager.setMaxScrollOffset(paramInt1, paramInt2);
    setPageScaleFactorAndLimits(paramFloat1, paramFloat2, paramFloat3);
  }
  
  public void zoomBy(float paramFloat)
  {
    if (isDestroyedOrNoOperation(1)) {
      return;
    }
    if ((paramFloat >= 0.01F) && (paramFloat <= 100.0F))
    {
      nativeZoomBy(this.mNativeAwContents, paramFloat);
      return;
    }
    throw new IllegalStateException("zoom delta value outside [0.01, 100] range.");
  }
  
  public boolean zoomIn()
  {
    if (!canZoomIn()) {
      return false;
    }
    zoomBy(1.25F);
    return true;
  }
  
  public boolean zoomOut()
  {
    if (!canZoomOut()) {
      return false;
    }
    zoomBy(0.8F);
    return true;
  }
  
  private class AwComponentCallbacks
    implements ComponentCallbacks2
  {
    private AwComponentCallbacks() {}
    
    public void onConfigurationChanged(Configuration paramConfiguration)
    {
      AwContents.updateDefaultLocale();
      AwContents.this.mSettings.updateAcceptLanguages();
    }
    
    public void onLowMemory() {}
    
    public void onTrimMemory(final int paramInt)
    {
      final boolean bool = AwContents.this.getGlobalVisibleRect().isEmpty();
      if ((AwContents.this.mIsViewVisible) && (AwContents.this.mIsWindowVisible) && (!bool)) {
        bool = true;
      } else {
        bool = false;
      }
      ThreadUtils.runOnUiThreadBlocking(new Runnable()
      {
        public void run()
        {
          if (AwContents.this.isDestroyedOrNoOperation(0)) {
            return;
          }
          if (paramInt >= 60)
          {
            AwContents.this.mInitialFunctor.deleteHardwareRenderer();
            if (AwContents.this.mFullScreenFunctor != null) {
              AwContents.this.mFullScreenFunctor.deleteHardwareRenderer();
            }
          }
          AwContents.this.nativeTrimMemory(AwContents.this.mNativeAwContents, paramInt, bool);
        }
      });
    }
  }
  
  private static final class AwContentsDestroyRunnable
    implements Runnable
  {
    private final long jdField_a_of_type_Long;
    private final AwContents.WindowAndroidWrapper jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$WindowAndroidWrapper;
    
    private AwContentsDestroyRunnable(long paramLong, AwContents.WindowAndroidWrapper paramWindowAndroidWrapper)
    {
      this.jdField_a_of_type_Long = paramLong;
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$WindowAndroidWrapper = paramWindowAndroidWrapper;
    }
    
    public void run()
    {
      AwContents.nativeDestroy(this.jdField_a_of_type_Long);
    }
  }
  
  private class AwDisplayAndroidObserver
    implements DisplayAndroid.DisplayAndroidObserver
  {
    private AwDisplayAndroidObserver() {}
    
    public void onDIPScaleChanged(float paramFloat)
    {
      Object localObject = AwContents.this;
      ((AwContents)localObject).nativeSetDipScale(((AwContents)localObject).mNativeAwContents, paramFloat);
      localObject = AwContents.this.mLayoutSizer;
      double d = paramFloat;
      ((AwLayoutSizer)localObject).setDIPScale(d);
      AwContents.this.mSettings.setDIPScale(d);
    }
    
    public void onRotationChanged(int paramInt) {}
  }
  
  private class AwGestureStateListener
    extends GestureStateListener
  {
    private AwGestureStateListener() {}
    
    public void onDestroyed() {}
    
    public void onPinchEnded()
    {
      AwContents.this.mLayoutSizer.unfreezeLayoutRequests();
    }
    
    public void onPinchStarted()
    {
      AwContents.this.mLayoutSizer.freezeLayoutRequests();
    }
    
    public void onScaleLimitsChanged(float paramFloat1, float paramFloat2)
    {
      AwContents.this.mZoomControls.updateZoomControls();
    }
    
    public void onScrollOffsetOrExtentChanged(int paramInt1, int paramInt2)
    {
      AwContents.this.mZoomControls.updateZoomControls();
    }
    
    public void onScrollStarted(int paramInt1, int paramInt2)
    {
      AwContents.this.mZoomControls.invokeZoomPicker();
    }
    
    public void onScrollUpdateGestureConsumed()
    {
      AwContents.this.mScrollAccessibilityHelper.postViewScrolledAccessibilityEventCallback();
      AwContents.this.mZoomControls.invokeZoomPicker();
    }
  }
  
  private class AwLayoutSizerDelegate
    implements AwLayoutSizer.Delegate
  {
    private AwLayoutSizerDelegate() {}
    
    public boolean isLayoutParamsHeightWrapContent()
    {
      return (AwContents.this.mContainerView.getLayoutParams() != null) && (AwContents.this.mContainerView.getLayoutParams().height == -2);
    }
    
    public void requestLayout()
    {
      AwContents.this.mContainerView.requestLayout();
    }
    
    public void setForceZeroLayoutHeight(boolean paramBoolean)
    {
      AwContents.this.getSettings().setForceZeroLayoutHeight(paramBoolean);
    }
    
    public void setMeasuredDimension(int paramInt1, int paramInt2)
    {
      AwContents.this.mInternalAccessAdapter.setMeasuredDimension(paramInt1, paramInt2);
    }
  }
  
  protected class AwScrollOffsetManagerDelegate
    implements AwScrollOffsetManager.Delegate
  {
    protected AwScrollOffsetManagerDelegate() {}
    
    public void cancelFling()
    {
      AwContents.this.mWebContents.getEventForwarder().onCancelFling(SystemClock.uptimeMillis());
    }
    
    public int getContainerViewScrollX()
    {
      return AwContents.this.mContainerView.getScrollX();
    }
    
    public int getContainerViewScrollY()
    {
      return AwContents.this.mContainerView.getScrollY();
    }
    
    public void invalidate()
    {
      AwContents.this.postInvalidateOnAnimation();
    }
    
    public void overScrollContainerViewBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean)
    {
      AwContents.this.mInternalAccessAdapter.overScrollBy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 0, 0, paramBoolean);
    }
    
    public void scrollContainerViewTo(int paramInt1, int paramInt2)
    {
      AwContents.this.mInternalAccessAdapter.super_scrollTo(paramInt1, paramInt2);
    }
    
    public void scrollNativeTo(int paramInt1, int paramInt2)
    {
      if (!AwContents.this.isDestroyedOrNoOperation(0))
      {
        AwContents localAwContents = AwContents.this;
        localAwContents.nativeScrollTo(localAwContents.mNativeAwContents, paramInt1, paramInt2);
      }
    }
    
    public void smoothScroll(int paramInt1, int paramInt2, long paramLong)
    {
      if (!AwContents.this.isDestroyedOrNoOperation(0))
      {
        AwContents localAwContents = AwContents.this;
        localAwContents.nativeSmoothScroll(localAwContents.mNativeAwContents, paramInt1, paramInt2, paramLong);
      }
    }
  }
  
  protected class AwViewMethodsImpl
    implements AwViewMethods
  {
    protected final Rect mClipBoundsTemporary = new Rect();
    private ComponentCallbacks2 mComponentCallbacks;
    private int mLayerType = 0;
    
    protected AwViewMethodsImpl() {}
    
    private void updateHardwareAcceleratedFeaturesToggle()
    {
      AwSettings localAwSettings = AwContents.this.mSettings;
      if ((AwContents.this.mIsAttachedToWindow) && (AwContents.this.mContainerView.isHardwareAccelerated()))
      {
        int i = this.mLayerType;
        if ((i == 0) || (i == 2))
        {
          bool = true;
          break label52;
        }
      }
      boolean bool = false;
      label52:
      localAwSettings.setEnableSupportedHardwareAcceleratedFeatures(bool);
    }
    
    public int computeHorizontalScrollOffset()
    {
      return AwContents.this.mScrollOffsetManager.computeHorizontalScrollOffset();
    }
    
    public int computeHorizontalScrollRange()
    {
      return AwContents.this.mScrollOffsetManager.computeHorizontalScrollRange();
    }
    
    public void computeScroll()
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return;
      }
      AwContents localAwContents = AwContents.this;
      localAwContents.nativeOnComputeScroll(localAwContents.mNativeAwContents, AnimationUtils.currentAnimationTimeMillis());
    }
    
    public int computeVerticalScrollExtent()
    {
      return AwContents.this.mScrollOffsetManager.computeVerticalScrollExtent();
    }
    
    public int computeVerticalScrollOffset()
    {
      return AwContents.this.mScrollOffsetManager.computeVerticalScrollOffset();
    }
    
    public int computeVerticalScrollRange()
    {
      return AwContents.this.mScrollOffsetManager.computeVerticalScrollRange();
    }
    
    public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return false;
      }
      if (AwContents.isDpadEvent(paramKeyEvent)) {
        AwContents.this.mSettings.setSpatialNavigationEnabled(true);
      }
      if (GamepadList.a(paramKeyEvent)) {
        return true;
      }
      if ((AwContents.this.mContentsClient.hasWebViewClient()) && (AwContents.this.mContentsClient.shouldOverrideKeyEvent(paramKeyEvent))) {
        return AwContents.this.mInternalAccessAdapter.super_dispatchKeyEvent(paramKeyEvent);
      }
      return AwContents.this.mContentViewCore.dispatchKeyEvent(paramKeyEvent);
    }
    
    public AccessibilityNodeProvider getAccessibilityNodeProvider()
    {
      boolean bool = AwContents.this.isDestroyedOrNoOperation(0);
      AccessibilityNodeProvider localAccessibilityNodeProvider = null;
      if (bool) {
        return null;
      }
      WebContentsAccessibility localWebContentsAccessibility = AwContents.this.getWebContentsAccessibility();
      if (localWebContentsAccessibility != null) {
        localAccessibilityNodeProvider = localWebContentsAccessibility.getAccessibilityNodeProvider();
      }
      return localAccessibilityNodeProvider;
    }
    
    public void onAttachedToWindow()
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return;
      }
      if (AwContents.this.mIsAttachedToWindow) {
        return;
      }
      AwContents localAwContents = AwContents.this;
      localAwContents.mIsAttachedToWindow = true;
      localAwContents.mContentViewCore.onAttachedToWindow();
      localAwContents = AwContents.this;
      localAwContents.nativeOnAttachedToWindow(localAwContents.mNativeAwContents, AwContents.this.mContainerView.getWidth(), AwContents.this.mContainerView.getHeight());
      updateHardwareAcceleratedFeaturesToggle();
      AwContents.this.postUpdateContentViewCoreVisibility();
      AwContents.this.mCurrentFunctor.onAttachedToWindow();
      AwContents.updateDefaultLocale();
      AwContents.this.mSettings.updateAcceptLanguages();
      if (this.mComponentCallbacks != null) {
        return;
      }
      this.mComponentCallbacks = new AwContents.AwComponentCallbacks(AwContents.this, null);
      AwContents.this.mContext.registerComponentCallbacks(this.mComponentCallbacks);
    }
    
    public boolean onCheckIsTextEditor()
    {
      Object localObject = AwContents.this;
      boolean bool = false;
      if (((AwContents)localObject).isDestroyedOrNoOperation(0)) {
        return false;
      }
      localObject = ImeAdapter.fromWebContents(AwContents.this.mWebContents);
      if (localObject != null) {
        bool = ((ImeAdapter)localObject).onCheckIsTextEditor();
      }
      return bool;
    }
    
    public void onConfigurationChanged(Configuration paramConfiguration)
    {
      if (!AwContents.this.isDestroyedOrNoOperation(0)) {
        AwContents.this.mContentViewCore.onConfigurationChanged(paramConfiguration);
      }
    }
    
    public void onContainerViewOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
      int i = AwContents.this.mContainerView.getScrollX();
      int j = AwContents.this.mContainerView.getScrollY();
      AwContents.this.mScrollOffsetManager.onContainerViewOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
      if (AwContents.this.mOverScrollGlow != null) {
        AwContents.this.mOverScrollGlow.pullGlow(AwContents.this.mContainerView.getScrollX(), AwContents.this.mContainerView.getScrollY(), i, j, AwContents.this.mScrollOffsetManager.computeMaximumHorizontalScrollOffset(), AwContents.this.mScrollOffsetManager.computeMaximumVerticalScrollOffset());
      }
    }
    
    public void onContainerViewScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      AwContents.this.mScrollAccessibilityHelper.removePostedViewScrolledAccessibilityEventCallback();
      AwContents.this.mScrollOffsetManager.onContainerViewScrollChanged(paramInt1, paramInt2);
    }
    
    public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return null;
      }
      return ImeAdapter.fromWebContents(AwContents.this.mWebContents).onCreateInputConnection(paramEditorInfo);
    }
    
    public void onDetachedFromWindow()
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return;
      }
      if (!AwContents.this.mIsAttachedToWindow) {
        return;
      }
      AwContents localAwContents = AwContents.this;
      localAwContents.mIsAttachedToWindow = false;
      localAwContents.hideAutofillPopup();
      localAwContents = AwContents.this;
      localAwContents.nativeOnDetachedFromWindow(localAwContents.mNativeAwContents);
      AwContents.this.mContentViewCore.onDetachedFromWindow();
      updateHardwareAcceleratedFeaturesToggle();
      AwContents.this.postUpdateContentViewCoreVisibility();
      AwContents.this.mCurrentFunctor.onDetachedFromWindow();
      if (this.mComponentCallbacks != null)
      {
        AwContents.this.mContext.unregisterComponentCallbacks(this.mComponentCallbacks);
        this.mComponentCallbacks = null;
      }
      AwContents.this.mScrollAccessibilityHelper.removePostedCallbacks();
      AwContents.this.mZoomControls.dismissZoomPicker();
    }
    
    public boolean onDragEvent(DragEvent paramDragEvent)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return false;
      }
      return AwContents.this.mWebContents.getEventForwarder().a(paramDragEvent, AwContents.this.mContainerView);
    }
    
    public void onDraw(Canvas paramCanvas)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0))
      {
        TraceEvent.instant("EarlyOut_destroyed");
        paramCanvas.drawColor(AwContents.this.getEffectiveBackgroundColor());
        return;
      }
      if ((!paramCanvas.isHardwareAccelerated()) && (!paramCanvas.getClipBounds(this.mClipBoundsTemporary)))
      {
        TraceEvent.instant("EarlyOut_software_empty_clip");
        return;
      }
      AwContents.this.mScrollOffsetManager.syncScrollOffsetFromOnDraw();
      int i = AwContents.this.mContainerView.getScrollX();
      int j = AwContents.this.mContainerView.getScrollY();
      Rect localRect = AwContents.this.getGlobalVisibleRect();
      if ((Build.VERSION.SDK_INT == 24) || (Build.VERSION.SDK_INT == 25))
      {
        if (AwContents.this.mPaintForNWorkaround == null)
        {
          AwContents.this.mPaintForNWorkaround = new Paint();
          AwContents.this.mPaintForNWorkaround.setColor(Color.argb(1, 0, 0, 0));
          localObject = new ColorMatrix();
          ((ColorMatrix)localObject).setScale(0.0F, 0.0F, 0.0F, 0.1F);
          AwContents.this.mPaintForNWorkaround.setColorFilter(new ColorMatrixColorFilter((ColorMatrix)localObject));
        }
        paramCanvas.drawRect(0.0F, 0.0F, 1.0F, 1.0F, AwContents.this.mPaintForNWorkaround);
      }
      Object localObject = AwContents.this;
      boolean bool2 = ((AwContents)localObject).nativeOnDraw(((AwContents)localObject).mNativeAwContents, paramCanvas, paramCanvas.isHardwareAccelerated(), i, j, localRect.left, localRect.top, localRect.right, localRect.bottom, AwContents.ForceAuxiliaryBitmapRendering.sResult);
      boolean bool1 = bool2;
      if (bool2)
      {
        bool1 = bool2;
        if (paramCanvas.isHardwareAccelerated())
        {
          bool1 = bool2;
          if (!AwContents.ForceAuxiliaryBitmapRendering.sResult) {
            bool1 = AwContents.this.mCurrentFunctor.requestDrawGL(paramCanvas);
          }
        }
      }
      if (bool1)
      {
        int k = AwContents.this.mContainerView.getScrollX();
        int m = AwContents.this.mContainerView.getScrollY();
        paramCanvas.translate(-(k - i), -(m - j));
      }
      else
      {
        TraceEvent.instant("NativeDrawFailed");
        paramCanvas.drawColor(AwContents.this.getEffectiveBackgroundColor());
      }
      if ((AwContents.this.mOverScrollGlow != null) && (AwContents.this.mOverScrollGlow.drawEdgeGlows(paramCanvas, AwContents.this.mScrollOffsetManager.computeMaximumHorizontalScrollOffset(), AwContents.this.mScrollOffsetManager.computeMaximumVerticalScrollOffset()))) {
        AwContents.this.postInvalidateOnAnimation();
      }
      if (AwContents.this.mInvalidateRootViewOnNextDraw)
      {
        AwContents.this.mContainerView.getRootView().invalidate();
        AwContents.this.mInvalidateRootViewOnNextDraw = false;
      }
    }
    
    public void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return;
      }
      paramRect = AwContents.this;
      paramRect.mContainerViewFocused = paramBoolean;
      paramRect.mContentViewCore.onFocusChanged(paramBoolean, false);
    }
    
    public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return false;
      }
      return AwContents.this.mContentViewCore.onGenericMotionEvent(paramMotionEvent);
    }
    
    public boolean onHoverEvent(MotionEvent paramMotionEvent)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return false;
      }
      return AwContents.this.mWebContents.getEventForwarder().c(paramMotionEvent);
    }
    
    public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return false;
      }
      return AwContents.this.mContentViewCore.onKeyUp(paramInt, paramKeyEvent);
    }
    
    public void onMeasure(int paramInt1, int paramInt2)
    {
      AwContents.this.mLayoutSizer.onMeasure(paramInt1, paramInt2);
    }
    
    public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return;
      }
      AwContents.this.mScrollOffsetManager.setContainerViewSize(paramInt1, paramInt2);
      AwContents.this.mLayoutSizer.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
      AwContents localAwContents = AwContents.this;
      localAwContents.nativeOnSizeChanged(localAwContents.mNativeAwContents, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return false;
      }
      if (paramMotionEvent.getActionMasked() == 0) {
        AwContents.this.mSettings.setSpatialNavigationEnabled(false);
      }
      AwContents.this.mScrollOffsetManager.setProcessingTouchEvent(true);
      boolean bool = AwContents.this.mWebContents.getEventForwarder().a(paramMotionEvent);
      AwContents.this.mScrollOffsetManager.setProcessingTouchEvent(false);
      if (paramMotionEvent.getActionMasked() == 0)
      {
        float f1 = paramMotionEvent.getX();
        float f2 = paramMotionEvent.getY();
        float f3 = Math.max(paramMotionEvent.getTouchMajor(), paramMotionEvent.getTouchMinor());
        if (!UseZoomForDSFPolicy.a())
        {
          float f4 = AwContents.this.getDeviceScaleFactor();
          f1 /= f4;
          f2 /= f4;
          f3 /= f4;
        }
        AwContents localAwContents = AwContents.this;
        localAwContents.nativeRequestNewHitTestDataAt(localAwContents.mNativeAwContents, f1, f2, f3);
      }
      if (AwContents.this.mOverScrollGlow != null)
      {
        if (paramMotionEvent.getActionMasked() == 0)
        {
          AwContents.this.mOverScrollGlow.setShouldPull(true);
          return bool;
        }
        if ((paramMotionEvent.getActionMasked() == 1) || (paramMotionEvent.getActionMasked() == 3))
        {
          AwContents.this.mOverScrollGlow.setShouldPull(false);
          AwContents.this.mOverScrollGlow.releaseAll();
        }
      }
      return bool;
    }
    
    public void onVisibilityChanged(View paramView, int paramInt)
    {
      boolean bool;
      if (AwContents.this.mContainerView.getVisibility() == 0) {
        bool = true;
      } else {
        bool = false;
      }
      if (AwContents.this.mIsViewVisible == bool) {
        return;
      }
      AwContents.this.setViewVisibilityInternal(bool);
    }
    
    public void onWindowFocusChanged(boolean paramBoolean)
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return;
      }
      AwContents localAwContents = AwContents.this;
      localAwContents.mWindowFocused = paramBoolean;
      localAwContents.mContentViewCore.onWindowFocusChanged(paramBoolean);
    }
    
    public void onWindowVisibilityChanged(int paramInt)
    {
      boolean bool;
      if (paramInt == 0) {
        bool = true;
      } else {
        bool = false;
      }
      if (AwContents.this.mIsWindowVisible == bool) {
        return;
      }
      AwContents.this.setWindowVisibilityInternal(bool);
    }
    
    public boolean performAccessibilityAction(int paramInt, Bundle paramBundle)
    {
      Object localObject = AwContents.this;
      boolean bool = false;
      if (((AwContents)localObject).isDestroyedOrNoOperation(0)) {
        return false;
      }
      localObject = AwContents.this.getWebContentsAccessibility();
      if (localObject != null) {
        bool = ((WebContentsAccessibility)localObject).performAction(paramInt, paramBundle);
      }
      return bool;
    }
    
    public void requestFocus()
    {
      if (AwContents.this.isDestroyedOrNoOperation(0)) {
        return;
      }
      if ((!AwContents.this.mContainerView.isInTouchMode()) && (AwContents.this.mSettings.shouldFocusFirstNode()))
      {
        AwContents localAwContents = AwContents.this;
        localAwContents.nativeFocusFirstNode(localAwContents.mNativeAwContents);
      }
    }
    
    public void setLayerType(int paramInt, Paint paramPaint)
    {
      this.mLayerType = paramInt;
      updateHardwareAcceleratedFeaturesToggle();
    }
  }
  
  private class BackgroundThreadClientImpl
    extends AwContentsBackgroundThreadClient
  {
    private BackgroundThreadClientImpl() {}
    
    public AwWebResourceResponse shouldInterceptRequest(AwContentsClient.AwWebResourceRequest paramAwWebResourceRequest)
    {
      String str = paramAwWebResourceRequest.url;
      AwWebResourceResponse localAwWebResourceResponse = AwContents.this.mDefaultVideoPosterRequestHandler.shouldInterceptRequest(str);
      if (localAwWebResourceResponse != null) {
        return localAwWebResourceResponse;
      }
      localAwWebResourceResponse = AwContents.this.mContentsClient.shouldInterceptRequest(paramAwWebResourceRequest);
      if (localAwWebResourceResponse == null) {
        AwContents.this.mContentsClient.getCallbackHelper().postOnLoadResource(str);
      }
      if ((localAwWebResourceResponse != null) && (localAwWebResourceResponse.getData() == null)) {
        AwContents.this.mContentsClient.getCallbackHelper().postOnReceivedError(paramAwWebResourceRequest, new AwContentsClient.AwWebResourceError());
      }
      return localAwWebResourceResponse;
    }
  }
  
  public static class DependencyFactory
  {
    public AutofillProvider createAutofillProvider(Context paramContext, ViewGroup paramViewGroup)
    {
      return null;
    }
    
    public AwLayoutSizer createLayoutSizer()
    {
      return new AwLayoutSizer();
    }
    
    public AwScrollOffsetManager createScrollOffsetManager(AwScrollOffsetManager.Delegate paramDelegate)
    {
      return new TencentAwScrollOffsetManager(paramDelegate);
    }
  }
  
  protected static class ForceAuxiliaryBitmapRendering
  {
    public static boolean sResult = ;
    
    private static boolean a()
    {
      int i = Build.VERSION.SDK_INT;
      boolean bool2 = true;
      if ((i < 21) && (TencentDeviceUtils.getTotalRAMMemory() < 650)) {
        return true;
      }
      boolean bool1 = bool2;
      if (!"goldfish".equals(Build.HARDWARE))
      {
        bool1 = bool2;
        if (!"ranchu".equals(Build.HARDWARE))
        {
          if (!AwContents.access$000()) {
            return true;
          }
          bool1 = false;
        }
      }
      return bool1;
    }
  }
  
  private static class FullScreenTransitionsState
  {
    private final ViewGroup jdField_a_of_type_AndroidViewViewGroup;
    private final AwContents.InternalAccessDelegate jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$InternalAccessDelegate;
    private final AwViewMethods jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods;
    private FullScreenView jdField_a_of_type_OrgChromiumAndroid_webviewFullScreenView;
    private boolean jdField_a_of_type_Boolean;
    
    private FullScreenTransitionsState(ViewGroup paramViewGroup, AwContents.InternalAccessDelegate paramInternalAccessDelegate, AwViewMethods paramAwViewMethods)
    {
      this.jdField_a_of_type_AndroidViewViewGroup = paramViewGroup;
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$InternalAccessDelegate = paramInternalAccessDelegate;
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods = paramAwViewMethods;
    }
    
    private ViewGroup a()
    {
      return this.jdField_a_of_type_AndroidViewViewGroup;
    }
    
    private AwContents.InternalAccessDelegate a()
    {
      return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwContents$InternalAccessDelegate;
    }
    
    private AwViewMethods a()
    {
      return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwViewMethods;
    }
    
    private FullScreenView a()
    {
      return this.jdField_a_of_type_OrgChromiumAndroid_webviewFullScreenView;
    }
    
    private void a()
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewFullScreenView = null;
    }
    
    private void a(FullScreenView paramFullScreenView, boolean paramBoolean)
    {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewFullScreenView = paramFullScreenView;
      this.jdField_a_of_type_Boolean = paramBoolean;
    }
    
    private boolean a()
    {
      return this.jdField_a_of_type_Boolean;
    }
    
    public boolean isFullScreen()
    {
      return this.jdField_a_of_type_OrgChromiumAndroid_webviewFullScreenView != null;
    }
  }
  
  @IntDef({0L, 1L, 2L, 3L})
  static @interface HistoryUrl
  {
    public static final int BASEURL = 1;
    public static final int COUNT = 3;
    public static final int DIFFERENT = 2;
    public static final int EMPTY = 0;
  }
  
  public static class HitTestData
  {
    public String anchorText;
    public String hitTestResultExtraData;
    public int hitTestResultType;
    public String href;
    public String imgSrc;
  }
  
  private class InterceptNavigationDelegateImpl
    implements InterceptNavigationDelegate
  {
    private InterceptNavigationDelegateImpl() {}
    
    public boolean shouldIgnoreNavigation(NavigationParams paramNavigationParams)
    {
      String str = paramNavigationParams.jdField_a_of_type_JavaLangString;
      boolean bool3 = BrowserSideNavigationPolicy.a();
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (!bool3)
      {
        bool1 = bool2;
        if (AwContents.this.mDeferredShouldOverrideUrlLoadingIsPendingForPopup)
        {
          AwContents.access$502(AwContents.this, false);
          bool1 = bool2;
          if (!paramNavigationParams.jdField_a_of_type_Boolean)
          {
            AwContentsClient localAwContentsClient = AwContents.this.mContentsClient;
            Context localContext = AwContents.this.mContext;
            bool2 = paramNavigationParams.f;
            if ((!paramNavigationParams.b) && (!paramNavigationParams.e)) {
              bool1 = false;
            } else {
              bool1 = true;
            }
            bool1 = localAwContentsClient.shouldIgnoreNavigation(localContext, str, bool2, bool1, paramNavigationParams.c);
          }
        }
      }
      if (!bool1) {
        AwContents.this.mContentsClient.getCallbackHelper().postOnPageStarted(str);
      }
      AwContents.this.shouldIgnoreNavigationImpl(paramNavigationParams, bool1);
      return bool1;
    }
  }
  
  public static abstract interface InternalAccessDelegate
    extends ContentViewCore.InternalAccessDelegate
  {
    public abstract void overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean);
    
    public abstract void setMeasuredDimension(int paramInt1, int paramInt2);
    
    public abstract int super_getScrollBarStyle();
    
    public abstract void super_scrollTo(int paramInt1, int paramInt2);
    
    public abstract void super_startActivityForResult(Intent paramIntent, int paramInt);
  }
  
  private class IoThreadClientImpl
    extends AwContentsIoThreadClient
  {
    private IoThreadClientImpl() {}
    
    public AwContentsBackgroundThreadClient getBackgroundThreadClient()
    {
      return AwContents.this.mBackgroundThreadClient;
    }
    
    public int getCacheMode()
    {
      return AwContents.this.mSettings.getCacheMode();
    }
    
    public boolean getSafeBrowsingEnabled()
    {
      return AwContents.this.mSettings.getSafeBrowsingEnabled();
    }
    
    public boolean shouldAcceptThirdPartyCookies()
    {
      return AwContents.this.mSettings.getAcceptThirdPartyCookies();
    }
    
    public boolean shouldBlockContentUrls()
    {
      return AwContents.this.mSettings.getAllowContentAccess() ^ true;
    }
    
    public boolean shouldBlockFileUrls()
    {
      return AwContents.this.mSettings.getAllowFileAccess() ^ true;
    }
    
    public boolean shouldBlockNetworkLoads()
    {
      return AwContents.this.mSettings.getBlockNetworkLoads();
    }
  }
  
  public static abstract interface NativeDrawGLFunctor
  {
    public abstract void detach(View paramView);
    
    public abstract Runnable getDestroyRunnable();
    
    public abstract boolean requestDrawGL(Canvas paramCanvas, Runnable paramRunnable);
    
    public abstract boolean requestInvokeGL(View paramView, boolean paramBoolean);
    
    public abstract boolean supportsDrawGLFunctorReleasedCallback();
  }
  
  public static abstract interface NativeDrawGLFunctorFactory
  {
    public abstract AwContents.NativeDrawGLFunctor createFunctor(long paramLong);
  }
  
  @VisibleForTesting
  public static abstract class VisualStateCallback
  {
    public abstract void onComplete(long paramLong);
  }
  
  private static class WebContentsInternalsHolder
    implements WebContents.InternalsHolder
  {
    private final WeakReference<AwContents> a;
    
    private WebContentsInternalsHolder(AwContents paramAwContents)
    {
      this.a = new WeakReference(paramAwContents);
    }
    
    public WebContentsInternals get()
    {
      AwContents localAwContents = (AwContents)this.a.get();
      if (localAwContents == null) {
        return null;
      }
      return localAwContents.mWebContentsInternals;
    }
    
    public void set(WebContentsInternals paramWebContentsInternals)
    {
      AwContents localAwContents = (AwContents)this.a.get();
      if (localAwContents != null)
      {
        AwContents.access$102(localAwContents, paramWebContentsInternals);
        return;
      }
      throw new IllegalStateException("AwContents should be available at this time");
    }
    
    public boolean weakRefCleared()
    {
      return this.a.get() == null;
    }
  }
  
  private static class WindowAndroidWrapper
  {
    private final CleanupReference jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference;
    private final WindowAndroid jdField_a_of_type_OrgChromiumUiBaseWindowAndroid;
    
    public WindowAndroidWrapper(WindowAndroid paramWindowAndroid)
    {
      this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid = paramWindowAndroid;
      this.jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference = new CleanupReference(this, new DestroyRunnable(paramWindowAndroid, null));
    }
    
    public WindowAndroid getWindowAndroid()
    {
      return this.jdField_a_of_type_OrgChromiumUiBaseWindowAndroid;
    }
    
    private static final class DestroyRunnable
      implements Runnable
    {
      private final WindowAndroid a;
      
      private DestroyRunnable(WindowAndroid paramWindowAndroid)
      {
        this.a = paramWindowAndroid;
      }
      
      public void run()
      {
        this.a.destroy();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwContents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */