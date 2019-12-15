package com.tencent.tbs.core;

import android.os.Build.VERSION;
import android.util.Log;
import org.chromium.base.annotations.UsedByReflection;
import org.chromium.ui.base.TouchDevice;

public class Preprocessor
{
  private static final boolean ENABLE_PREINIT = true;
  
  @UsedByReflection("X5CoreInit.java")
  public static void preinit() {}
  
  @UsedByReflection("X5CoreInit.java")
  public static void preinit0() {}
  
  @UsedByReflection("X5CoreInit.java")
  public static void preinitClassForX5WebView(boolean paramBoolean)
  {
    
    if (paramBoolean)
    {
      preinitClassForX5WebViewPartner();
      return;
    }
    preinitClassForX5WebViewMtt();
  }
  
  public static void preinitClassForX5WebViewCommon()
  {
    try
    {
      Class.forName("android.webview.chromium.tencent.TencentCookieManagerAdapter$1");
      Class.forName("com.tencent.tbs.common.observer.AppBroadcastObserver");
      Class.forName("com.tencent.tbs.core.webkit.WebView$PrivateAccess");
      Class.forName("android.webview.chromium.tencent.TencentWebViewChromium");
      Class.forName("android.webview.chromium.tencent.TencentWebViewChromium$WebViewHitTestResult");
      Class.forName("com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy$HitTestResult");
      Class.forName("com.tencent.tbs.core.webkit.WebView$HitTestResult");
      Class.forName("android.webview.chromium.WebViewChromium$InternalAccessAdapter");
      Class.forName("org.chromium.android_webview.AwContents$InternalAccessDelegate");
      Class.forName("android.webview.chromium.WebViewChromium$WebViewNativeDrawGLFunctorFactory");
      Class.forName("org.chromium.android_webview.AwContents$NativeDrawGLFunctorFactory");
      Class.forName("org.chromium.tencent.TencentAwBrowserContext");
      Class.forName("android.webview.chromium.tencent.TencentWebViewChromium$5");
      Class.forName("android.webview.chromium.tencent.TencentWebBackForwardListChromium");
      Class.forName("android.webview.chromium.tencent.TencentWebViewChromium$4");
      Class.forName("com.tencent.tbs.core.webkit.tencent.TencentWebSettingsProxy");
      Class.forName("android.webview.chromium.tencent.TencentContentSettingsAdapter");
      Class.forName("android.webview.chromium.tencent.TencentWebViewChromium$1");
      Class.forName("com.tencent.tbs.core.partner.ad.AdWebView");
      Class.forName("android.webview.chromium.tencent.TencentWebViewChromium$6");
      Class.forName("android.webview.chromium.tencent.TencentWebViewChromium$7");
      Class.forName("android.webview.chromium.tencent.TencentWebViewChromium$3");
      Class.forName("android.webview.chromium.tencent.TencentWebViewChromium$2");
      Class.forName("android.webview.chromium.tencent.TencentGraphicsUtils");
      Class.forName("android.webview.chromium.GraphicsUtils");
      Class.forName("org.chromium.net.NetworkChangeNotifier$1");
      Class.forName("org.chromium.net.NetworkChangeNotifierAutoDetect$Observer");
      Class.forName("org.chromium.net.NetworkChangeNotifierAutoDetect$ConnectivityManagerDelegate");
      Class.forName("org.chromium.net.NetworkChangeNotifierAutoDetect$WifiManagerDelegate");
      Class.forName("org.chromium.net.NetworkChangeNotifierAutoDetect$NetworkState");
      Class.forName("org.chromium.net.NetworkChangeNotifierAutoDetect$NetworkConnectivityIntentFilter");
      Class.forName("org.chromium.base.ObserverList$ObserverListIterator");
      Class.forName("org.chromium.base.ObserverList$RewindableIterator");
      Class.forName("org.chromium.android_webview.AwBrowserProcess$1");
      Class.forName("org.chromium.policy.AbstractAppRestrictionsProvider$1");
      Class.forName("org.chromium.android_webview.AwSafeBrowsingConfigHelper");
      Class.forName("org.chromium.base.BuildConfig");
      Class.forName("org.chromium.content.browser.BrowserStartupController$4");
      Class.forName("org.chromium.ui.display.DisplayAndroidManager");
      Class.forName("com.tencent.smtt.webkit.icu.ICUProxyUCharacter");
      Class.forName("com.tencent.smtt.webkit.icu.ICUProxyULocale");
      Class.forName("com.tencent.smtt.webkit.icu.ICUProxy");
      Class.forName("org.chromium.base.MemoryPressureListener$1");
      Class.forName("com.tencent.smtt.webkit.m");
      if (Build.VERSION.SDK_INT >= 19) {
        Class.forName("org.chromium.ui.display.DisplayAndroidManager$DisplayListenerBackendImpl");
      }
      Class.forName("org.chromium.ui.display.DisplayAndroidManager$DisplayListenerBackend");
      Class.forName("org.chromium.ui.display.PhysicalDisplayAndroid");
      Class.forName("org.chromium.ui.display.DisplayAndroid");
      Class.forName("org.chromium.base.SystemMessageHandler$MessageCompat");
      Class.forName("org.chromium.net.ProxyChangeListener$ProxyReceiver");
      Class.forName("org.chromium.android_webview.AwBrowserProcess$2");
      Class.forName("com.tencent.tbs.core.webkit.tencent.TencentCacheManager");
      Class.forName("android.webview.chromium.GeolocationPermissionsAdapter");
      Class.forName("android.webview.chromium.WebStorageAdapter");
      Class.forName("com.tencent.tbs.core.webkit.WebStorage");
      Class.forName("org.chromium.android_webview.AwQuotaManagerBridge");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$1");
      Class.forName("com.tencent.smtt.webkit.ScaleManager");
      Class.forName("org.chromium.tencent.TencentTraceEvent");
      Class.forName("org.chromium.android_webview.AwContentsClientBridge$ClientCertificateRequestCallback");
      Class.forName("org.chromium.android_webview.AwContentsClient$AwWebResourceError");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$WebResourceRequestImpl");
      Class.forName("com.tencent.tbs.core.webkit.WebResourceRequest");
      Class.forName("com.tencent.smtt.export.external.interfaces.WebResourceRequest");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$WebResourceErrorImpl");
      Class.forName("com.tencent.tbs.core.webkit.WebResourceError");
      Class.forName("com.tencent.tbs.core.webkit.WebResourceResponse");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$3");
      Class.forName("com.tencent.tbs.core.webkit.SslErrorHandler");
      Class.forName("com.tencent.smtt.export.external.interfaces.WebResourceResponse");
      Class.forName("org.chromium.tencent.AwContentsClient$AwWebResourceError");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$9");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$10");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$6");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$8");
      Class.forName("android.content.DialogInterface$OnClickListener");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$7");
      Class.forName("com.tencent.smtt.export.external.interfaces.IX5WebViewBase");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$2");
      Class.forName("com.tencent.tbs.core.webkit.tencent.TencentJsDialogHelper");
      Class.forName("org.chromium.android_webview.AwContentsClient$FileChooserParamsImpl");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$4");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter$5");
      Class.forName("com.tencent.tbs.core.webkit.ClientCertRequest");
      Class.forName("com.tencent.smtt.export.external.interfaces.ClientCertRequest");
      Class.forName("com.tencent.smtt.export.external.interfaces.ClientCertRequest");
      Class.forName("com.tencent.smtt.export.external.interfaces.WebResourceRequest");
      Class.forName("com.tencent.tbs.core.webkit.HttpAuthHandler");
      Class.forName("com.tencent.smtt.export.external.interfaces.HttpAuthHandler");
      Class.forName("com.tencent.smtt.export.external.interfaces.HttpAuthHandler");
      Class.forName("com.tencent.smtt.export.external.interfaces.WebResourceResponse");
      Class.forName("com.tencent.smtt.export.external.interfaces.SslErrorHandler");
      Class.forName("com.tencent.smtt.export.external.interfaces.SslError");
      Class.forName("org.chromium.android_webview.AwContentsClientCallbackHelper$MyHandler");
      Class.forName("android.webview.chromium.WebViewContentsClientAdapter$1");
      Class.forName("com.tencent.tbs.core.webkit.WebSettings$LayoutAlgorithm");
      Class.forName("com.tencent.tbs.core.webkit.WebSettings$PluginState");
      Class.forName("org.chromium.android_webview.AwSettings$EventHandler");
      Class.forName("android.provider.Settings$System");
      Class.forName("org.chromium.android_webview.AwSettings$EventHandler$2");
      Class.forName("org.chromium.android_webview.AwContents$DependencyFactory");
      Class.forName("android.webview.chromium.DrawGLFunctor$DestroyRunnable");
      Class.forName("org.chromium.android_webview.AwGLFunctor$DestroyRunnable");
      Class.forName("org.chromium.base.EarlyTraceEvent");
      Class.forName("org.chromium.android_webview.AwGLFunctor$1");
      Class.forName("org.chromium.android_webview.AwContents$1");
      Class.forName("org.chromium.android_webview.AwContentsClientCallbackHelper$CancelCallbackPoller");
      Class.forName("org.chromium.android_webview.AwContents$AwViewMethodsImpl");
      Class.forName("org.chromium.android_webview.AwContents$FullScreenTransitionsState");
      Class.forName("org.chromium.android_webview.AwContents$AwLayoutSizerDelegate");
      Class.forName("org.chromium.android_webview.AwLayoutSizer$Delegate");
      Class.forName("org.chromium.tencent.TencentAwContentsClientBridge");
      Class.forName("org.chromium.tencent.TencentAwContentsIoThreadClientImpl");
      Class.forName("org.chromium.tencent.TencentAwContentsIoThreadClient");
      Class.forName("org.chromium.android_webview.AwContents$BackgroundThreadClientImpl");
      Class.forName("org.chromium.android_webview.AwContents$IoThreadClientImpl");
      Class.forName("org.chromium.android_webview.AwContents$InterceptNavigationDelegateImpl");
      Class.forName("org.chromium.android_webview.AwContents$AwDisplayAndroidObserver");
      Class.forName("org.chromium.android_webview.AwContents$2");
      Class.forName("org.chromium.android_webview.AwContents$3");
      Class.forName("org.chromium.android_webview.AwSettings$ZoomSupportChangeListener");
      Class.forName("org.chromium.android_webview.AwContents$AwScrollOffsetManagerDelegate");
      Class.forName("org.chromium.android_webview.AwScrollOffsetManager$Delegate");
      Class.forName("org.chromium.android_webview.ScrollAccessibilityHelper$HandlerCallback");
      Class.forName("org.chromium.tencent.widget.OverScroller");
      Class.forName("org.chromium.tencent.widget.OverScrollBrand$DefaultInterpolator");
      Class.forName("org.chromium.tencent.widget.OverScroller$SplineOverScroller");
      Class.forName("org.chromium.tencent.widget.OverScroller$RenderTestingHelper");
      Class.forName("org.chromium.ui.gfx.ViewConfigurationHelper$1");
      Class.forName("org.chromium.content.browser.InterfaceRegistrarImpl");
      Class.forName("org.chromium.content_public.browser.InterfaceRegistrar");
      Class.forName("org.chromium.content_public.browser.InterfaceRegistrar$Registry");
      Class.forName("org.chromium.mojo.system.impl.UntypedHandleImpl");
      Class.forName("org.chromium.mojo.system.impl.HandleBase");
      Class.forName("org.chromium.mojo.system.Handle");
      Class.forName("org.chromium.mojo.system.UntypedHandle");
      Class.forName("org.chromium.mojo.system.impl.MessagePipeHandleImpl");
      Class.forName("org.chromium.mojo.system.MessagePipeHandle");
      Class.forName("org.chromium.services.service_manager.InterfaceRegistry");
      Class.forName("org.chromium.service_manager.mojom.InterfaceProvider");
      Class.forName("org.chromium.mojo.bindings.Interface");
      Class.forName("org.chromium.mojo.bindings.ConnectionErrorHandler");
      Class.forName("org.chromium.service_manager.mojom.InterfaceProvider_Internal");
      Class.forName("org.chromium.service_manager.mojom.InterfaceProvider_Internal$1");
      Class.forName("org.chromium.mojo.bindings.RouterImpl");
      Class.forName("org.chromium.mojo.bindings.Router");
      Class.forName("org.chromium.mojo.bindings.MessageReceiverWithResponder");
      Class.forName("org.chromium.mojo.bindings.MessageReceiver");
      Class.forName("org.chromium.mojo.bindings.HandleOwner");
      Class.forName("org.chromium.mojo.bindings.BindingsHelper");
      Class.forName("org.chromium.mojo.bindings.DataHeader");
      Class.forName("org.chromium.mojo.system.impl.WatcherImpl");
      Class.forName("org.chromium.mojo.system.Watcher");
      Class.forName("org.chromium.mojo.bindings.Connector");
      Class.forName("org.chromium.mojo.system.MojoException");
      Class.forName("org.chromium.mojo.bindings.Connector$WatcherCallback");
      Class.forName("org.chromium.mojo.system.Watcher$Callback");
      Class.forName("org.chromium.mojo.bindings.RouterImpl$HandleIncomingMessageThunk");
      Class.forName("org.chromium.mojo.bindings.ExecutorFactory");
      Class.forName("org.chromium.mojo.bindings.ExecutorFactory$PipedExecutor");
      Class.forName("org.chromium.mojo.system.MessagePipeHandle$CreateOptions");
      Class.forName("org.chromium.mojo.system.MessagePipeHandle$CreateFlags");
      Class.forName("org.chromium.mojo.system.Flags");
      Class.forName("org.chromium.mojo.system.ResultAnd");
      Class.forName("org.chromium.mojo.system.impl.CoreImpl$IntegerPair");
      Class.forName("org.chromium.mojo.system.Pair");
      Class.forName("org.chromium.mojo.system.Core$HandleSignals");
      Class.forName("org.chromium.service_manager.mojom.InterfaceProvider_Internal$Stub");
      Class.forName("org.chromium.mojo.bindings.Interface$Stub");
      Class.forName("org.chromium.mojo.bindings.DeserializationException");
      Class.forName("org.chromium.device.vibration.VibrationManagerImpl$Factory");
      Class.forName("org.chromium.services.service_manager.InterfaceFactory");
      Class.forName("org.chromium.services.service_manager.InterfaceRegistry$InterfaceBinder");
      Class.forName("org.chromium.device.battery.BatteryMonitorFactory");
      Class.forName("org.chromium.device.battery.BatteryMonitorFactory$1");
      Class.forName("org.chromium.device.battery.BatteryStatusManager$BatteryStatusCallback");
      Class.forName("org.chromium.device.battery.BatteryStatusManager");
      Class.forName("org.chromium.device.battery.BatteryStatusManager$AndroidBatteryManagerWrapper");
      Class.forName("org.chromium.device.battery.BatteryStatusManager$1");
      Class.forName("org.chromium.android_webview.AwContents$WindowAndroidWrapper");
      Class.forName("org.chromium.ui.base.WindowAndroid$1");
      Class.forName("org.chromium.ui.VSyncMonitor$Listener");
      if (Build.VERSION.SDK_INT >= 19) {
        Class.forName("org.chromium.ui.VSyncMonitor$1");
      }
      Class.forName("org.chromium.ui.base.ActivityWindowAndroid$ActivityAndroidPermissionDelegate");
      Class.forName("org.chromium.android_webview.AwContents$WindowAndroidWrapper$DestroyRunnable");
      Class.forName("org.chromium.android_webview.AwViewAndroidDelegate");
      Class.forName("org.chromium.android_webview.AwContents$AwGestureStateListener");
      Class.forName("org.chromium.content.browser.PopupZoomer$ReverseInterpolator");
      Class.forName("org.chromium.content.browser.PopupZoomer$1");
      Class.forName("org.chromium.content.browser.PopupZoomer$OnVisibilityChangedListener");
      Class.forName("org.chromium.content.browser.PopupZoomer$OnTapListener");
      Class.forName("org.chromium.content.browser.input.CursorAnchorInfoController$ComposingTextDelegate");
      Class.forName("org.chromium.content.browser.input.CursorAnchorInfoController$1");
      Class.forName("org.chromium.content.browser.input.CursorAnchorInfoController$ViewDelegate");
      Class.forName("org.chromium.android_webview.AwActionModeCallback");
      Class.forName("org.chromium.android_webview.AwSettings$EventHandler$1");
      Class.forName("org.chromium.tencent.TencentAwAutofillClient");
      Class.forName("org.chromium.android_webview.AwSettings$6");
      Class.forName("org.chromium.android_webview.AwContents$AwContentsDestroyRunnable");
      Class.forName("org.chromium.tencent.ui.gfx.DeviceDisplayInfo");
      Class.forName("org.chromium.tencent.TencentAwContent$TencentAwGestureStateListener");
      Class.forName("org.chromium.base.JavaHandlerThread$1");
      Class.forName("org.chromium.content.browser.ViewPositionObserver$1");
      Class.forName("com.tencent.smtt.webkit.input.c$1");
      Class.forName("com.tencent.smtt.webkit.WebSettingsExtension$1");
      Class.forName("com.tencent.tbs.core.webkit.CookieSyncManager");
      Class.forName("com.tencent.tbs.core.webkit.WebSyncManager");
      Class.forName("com.tencent.smtt.net.PageLoadDetector$1");
      Class.forName("com.tencent.smtt.net.network.connectionclass.ConnectionClassManager$ConnectionClassStateChangeListener");
      Class.forName("com.tencent.tbs.core.webkit.WebSettings$ZoomDensity");
      Class.forName("com.tencent.smtt.export.external.interfaces.IX5WebSettings$ZoomDensity");
      Class.forName("com.tencent.smtt.export.external.interfaces.IX5WebSettings$LayoutAlgorithm");
      Class.forName("com.tencent.smtt.export.external.interfaces.IX5WebSettings$PluginState");
      Class.forName("com.tencent.tbs.core.webkit.WebSettings$TextSize");
      Class.forName("com.tencent.smtt.export.external.interfaces.IX5WebSettings$TextSize");
      Class.forName("com.tencent.smtt.export.external.interfaces.IX5WebSettings$RenderPriority");
      Class.forName("com.tencent.tbs.core.webkit.WebSettings$RenderPriority");
      return;
    }
    catch (Throwable localThrowable)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("***ATTENTION*** preinitClassForX5WebView#1 failed: ");
      localStringBuilder.append(localThrowable);
      Log.e("Preprocessor", localStringBuilder.toString());
    }
  }
  
  private static void preinitClassForX5WebViewMtt() {}
  
  private static void preinitClassForX5WebViewPartner()
  {
    try
    {
      Class.forName("com.tencent.tbs.core.partner.PartnerWebViewObserver");
      Class.forName("com.tencent.tbs.core.partner.downloadserver.TBSDownloadServer");
      Class.forName("com.tencent.tbs.tbsshell.partner.WebCoreReflectionPartnerImpl");
      Class.forName("com.tencent.tbs.tbsshell.IWebCoreReflectionPartner");
      Class.forName("com.tencent.tbs.tbsshell.partner.webaccelerator.WebViewPool");
      Class.forName("com.tencent.tbs.core.partner.headsup.HeadsupEngine");
      Class.forName("com.tencent.tbs.core.partner.clipboard.SystemClipboardMonitor");
      Class.forName("com.tencent.smtt.jsApi.impl.OpenJsApisService");
      return;
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}catch (ClassNotFoundException localClassNotFoundException) {}
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("***ATTENTION*** preinitClassForX5WebViewPartner#1 failed: ");
    localStringBuilder.append(localClassNotFoundException.getMessage());
    Log.e("Preprocessor", localStringBuilder.toString());
  }
  
  @UsedByReflection("X5CoreInit.java")
  public static void preinitClassWithSo()
  {
    try
    {
      Class.forName("org.chromium.android_webview.AwContents");
      Class.forName("org.chromium.tencent.TencentAwContent");
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  private void preinitClassesWithResources()
  {
    try
    {
      Class.forName("com.tencent.tbs.core.partner.menu.X5LongClickPopMenu");
      Class.forName("com.tencent.smtt.webkit.ui.h");
      Class.forName("com.tencent.smtt.webkit.ui.e");
      Class.forName("com.tencent.smtt.webkit.ui.d");
      Class.forName("android.webview.chromium.tencent.TencentWebViewContentsClientAdapter");
      return;
    }
    catch (NoClassDefFoundError localNoClassDefFoundError) {}catch (ClassNotFoundException localClassNotFoundException) {}
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("***ATTENTION*** preloadClass#1 failed: ");
    localStringBuilder.append(localClassNotFoundException.getMessage());
    Log.e("Preprocessor", localStringBuilder.toString());
  }
  
  private static void preloadClasses()
  {
    try
    {
      System.currentTimeMillis();
      Class.forName("com.tencent.tbs.core.partner.jsextension.JsExtensions");
      Class.forName("android.webview.chromium.WebViewDelegateFactory");
      Class.forName("android.webview.chromium.tencent.TencentWebViewDelegateFactory");
      Class.forName("org.chromium.android_webview.AwContentsStatics");
      Class.forName("org.chromium.android_webview.AwBrowserContext");
      Class.forName("org.chromium.android_webview.AwContentsClient");
      Class.forName("android.webview.chromium.WebViewContentsClientAdapter");
      Class.forName("android.webview.chromium.ContentSettingsAdapter");
      Class.forName("org.chromium.android_webview.AwSettings");
      Class.forName("org.chromium.tencent.AwContentsClientExtension");
      Class.forName("org.chromium.tencent.ContentViewClientExtension");
      Class.forName("org.chromium.tencent.SelectionControllerClient");
      Class.forName("com.tencent.smtt.webkit.WebViewChromiumExtension");
      Class.forName("org.chromium.content_public.browser.NavigationHistory");
      Class.forName("android.webview.chromium.WebBackForwardListChromium");
      if (Build.VERSION.SDK_INT >= 19)
      {
        Class.forName("org.chromium.android_webview.AwPrintDocumentAdapter");
        Class.forName("org.chromium.android_webview.AwPdfExporter");
      }
      Class.forName("org.chromium.tencent.TencentContentViewCore");
      Class.forName("com.tencent.smtt.net.f");
      Class.forName("org.chromium.base.TraceEvent");
      Class.forName("org.chromium.base.PathService");
      Class.forName("org.chromium.android_webview.AwResource");
      Class.forName("com.tencent.smtt.webkit.o");
      Class.forName("org.chromium.device.geolocation.LocationProviderFactory");
      Class.forName("org.chromium.base.LocaleUtils");
      Class.forName("org.chromium.android_webview.AwGLFunctor");
      Class.forName("org.chromium.android_webview.AwViewMethods");
      Class.forName("org.chromium.content.browser.ContentVideoViewEmbedder");
      Class.forName("org.chromium.android_webview.AwLayoutSizer");
      Class.forName("org.chromium.components.web_contents_delegate_android.WebContentsDelegateAndroid");
      Class.forName("org.chromium.android_webview.AwWebContentsDelegate");
      Class.forName("org.chromium.android_webview.AwWebContentsDelegateAdapter");
      Class.forName("org.chromium.android_webview.AwContentsClientBridge");
      Class.forName("org.chromium.android_webview.ClientCertLookupTable");
      Class.forName("org.chromium.android_webview.AwZoomControls");
      Class.forName("org.chromium.android_webview.AwContentsBackgroundThreadClient");
      Class.forName("org.chromium.android_webview.AwContentsIoThreadClient");
      Class.forName("org.chromium.components.navigation_interception.InterceptNavigationDelegate");
      Class.forName("org.chromium.android_webview.DefaultVideoPosterRequestHandler");
      Class.forName("org.chromium.android_webview.ScrollAccessibilityHelper");
      Class.forName("org.chromium.tencent.widget.OverScrollBrand");
      Class.forName("org.chromium.android_webview.OverScrollGlow");
      Class.forName("org.chromium.ui.base.WindowAndroid");
      Class.forName("org.chromium.content_public.browser.WebContents");
      Class.forName("org.chromium.content_public.browser.GestureStateListener");
      Class.forName("org.chromium.android_webview.CleanupReference");
      Class.forName("org.chromium.content_public.browser.WebContentsObserver");
      Class.forName("org.chromium.android_webview.AwWebContentsObserver");
      Class.forName("org.chromium.content_public.browser.NavigationController");
      Class.forName("org.chromium.ui.base.ActivityWindowAndroid");
      Class.forName("org.chromium.base.ApiCompatibilityUtils");
      Class.forName("org.chromium.android_webview.AwGeolocationPermissions");
      Class.forName("com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback");
      Class.forName("org.chromium.android_webview.permission.AwGeolocationCallback");
      Class.forName("org.chromium.android_webview.permission.AwPermissionRequest");
      Class.forName("org.chromium.tencent.AwMediaAccessPermissions");
      Class.forName("org.chromium.android_webview.AwHttpAuthHandler");
      Class.forName("org.chromium.android_webview.AwAutofillClient");
      Class.forName("org.chromium.content.browser.DeviceUtils");
      Class.forName("org.chromium.android_webview.AwContentsClientCallbackHelper");
      Class.forName("org.chromium.android_webview.AwPicture");
      Class.forName("org.chromium.android_webview.FullScreenView");
      Class.forName("org.chromium.android_webview.NullAwViewMethods");
      Class.forName("org.chromium.content_public.browser.JavaScriptCallback");
      Class.forName("org.chromium.android_webview.SslUtil");
      Class.forName("org.chromium.android_webview.HttpAuthDatabase");
      Class.forName("org.chromium.content_public.browser.NavigationEntry");
      Class.forName("org.chromium.content_public.browser.LoadUrlParams");
      Class.forName("org.chromium.content_public.common.Referrer");
      Class.forName("org.chromium.content.browser.ContentViewStatics");
      Class.forName("org.chromium.net.NetworkChangeNotifier");
      Class.forName("org.chromium.base.ObserverList");
      Class.forName("org.chromium.android_webview.AwNetworkChangeNotifierRegistrationPolicy");
      Class.forName("org.chromium.net.NetworkChangeNotifierAutoDetect");
      Class.forName("org.chromium.base.metrics.RecordHistogram");
      Class.forName("org.chromium.android_webview.AwContentsLifecycleNotifier");
      Class.forName("org.chromium.content.browser.ChildProcessCreationParams");
      Class.forName("org.chromium.policy.CombinedPolicyProvider");
      Class.forName("org.chromium.policy.PolicyProvider");
      Class.forName("org.chromium.policy.AbstractAppRestrictionsProvider");
      Class.forName("org.chromium.policy.AppRestrictionsProvider");
      Class.forName("org.chromium.android_webview.policy.AwPolicyProvider");
      Class.forName("org.chromium.content.browser.BrowserStartupController");
      Class.forName("org.chromium.base.ResourceExtractor");
      Class.forName("org.chromium.ui.base.DeviceFormFactor");
      Class.forName("org.chromium.base.BuildInfo");
      Class.forName("org.chromium.base.SysUtils");
      Class.forName("org.chromium.content.app.ContentMain");
      Class.forName("org.chromium.ui.gfx.ViewConfigurationHelper");
      Class.forName("org.chromium.base.SystemMessageHandler");
      Class.forName("org.chromium.ui.base.ResourceBundle");
      Class.forName("org.chromium.base.MemoryPressureListener");
      Class.forName("org.chromium.base.ApplicationStatus");
      Class.forName("org.chromium.media.AudioManagerAndroid");
      Class.forName("org.chromium.policy.PolicyConverter");
      Class.forName("org.chromium.android_webview.AwDevToolsServer");
      Class.forName("com.tencent.tbs.core.webkit.CacheManager");
      Class.forName("com.tencent.smtt.export.external.interfaces.JsPromptResult");
      Class.forName("com.tencent.tbs.core.webkit.JsDialogHelper");
      Class.forName("org.chromium.android_webview.JsResultReceiver");
      Class.forName("com.tencent.smtt.export.external.interfaces.JsResult");
      Class.forName("org.chromium.android_webview.JsPromptResultReceiver");
      Class.forName("com.tencent.common.utils.MttLoader");
      Class.forName("com.tencent.smtt.os.FileUtils");
      Class.forName("com.tencent.smtt.util.f");
      Class.forName("com.tencent.smtt.export.external.interfaces.HttpAuthHandler");
      Class.forName("com.tencent.tbs.core.webkit.WebIconDatabase");
      Class.forName("com.tencent.smtt.export.external.interfaces.SslError");
      Class.forName("com.tencent.smtt.export.external.interfaces.SslErrorHandler");
      Class.forName("com.tencent.smtt.webkit.h5video.b");
      Class.forName("com.tencent.smtt.export.external.interfaces.WebResourceRequest");
      Class.forName("com.tencent.smtt.export.external.interfaces.WebResourceResponse");
      Class.forName("com.tencent.tbs.core.webkit.adapter.FileChooserParamsAdapter");
      Class.forName("com.tencent.smtt.export.external.interfaces.ClientCertRequest");
      Class.forName("org.chromium.android_webview.AwMetricsServiceClient");
      Class.forName("org.chromium.android_webview.PlatformServiceBridge");
      Class.forName("org.chromium.mojo.system.Core");
      Class.forName("org.chromium.mojo.system.impl.CoreImpl");
      Class.forName("org.chromium.content.browser.framehost.NavigationControllerImpl");
      Class.forName("org.chromium.content.browser.webcontents.WebContentsImpl");
      Class.forName("org.chromium.net.AndroidNetworkLibrary");
      if (Build.VERSION.SDK_INT >= 19) {
        Class.forName("org.chromium.ui.VSyncMonitor");
      }
      Class.forName("org.chromium.ui.base.AndroidPermissionDelegate");
      Class.forName("org.chromium.content.browser.RenderCoordinates");
      Class.forName("org.chromium.content.browser.accessibility.captioning.CaptioningBridgeFactory");
      Class.forName("org.chromium.content.browser.accessibility.captioning.SystemCaptioningBridge");
      Class.forName("org.chromium.content.browser.accessibility.captioning.KitKatCaptioningBridge");
      Class.forName("org.chromium.content.browser.accessibility.captioning.CaptioningChangeDelegate");
      Class.forName("org.chromium.ui.base.ViewAndroidDelegate");
      Class.forName("org.chromium.content.browser.PopupZoomer");
      Class.forName("org.chromium.content.browser.webcontents.WebContentsObserverProxy");
      Class.forName("org.chromium.ui.base.TouchDevice");
      Class.forName("org.chromium.base.JavaHandlerThread");
      Class.forName("com.tencent.smtt.export.internal.interfaces.SecurityLevelBase");
      Class.forName("com.tencent.smtt.webkit.input.c");
      Class.forName("com.tencent.smtt.util.CrashExtraMessage");
      Class.forName("com.tencent.smtt.webkit.l");
      Class.forName("org.chromium.content.browser.input.ChromiumBaseInputConnection");
      Class.forName("com.tencent.tbs.core.webkit.WebHistoryItem");
      Class.forName("android.webview.chromium.WebHistoryItemChromium");
      Class.forName("com.tencent.tbs.core.partner.impl.wakelock.X5CoreWakeLockManager");
      Class.forName("com.tencent.smtt.net.b");
      Class.forName("org.chromium.media.MediaPlayerBridge");
      Class.forName("org.chromium.tencent.video.SystemMediaPlayer");
      Class.forName("com.tencent.smtt.net.g");
      Class.forName("com.tencent.smtt.net.e");
      Class.forName("com.tencent.smtt.browser.x5.X5OptimizedBitmap");
      Class.forName("org.chromium.tencent.X5NativeBitmap");
      Class.forName("com.tencent.tbs.core.webkit.WebViewDatabase");
      Class.forName("com.tencent.smtt.webkit.ui.b");
      Class.forName("com.tencent.smtt.memory.MemoryChecker");
      Class.forName("org.chromium.content.browser.PositionObserver");
      Class.forName("org.chromium.content.browser.ViewPositionObserver");
      Class.forName("com.tencent.smtt.util.b");
      Class.forName("com.tencent.smtt.net.m");
      Class.forName("com.tencent.tbs.core.partner.ad.AdWebViewController");
      TouchDevice.availablePointerAndHoverTypes();
      TouchDevice.availablePointerAndHoverTypes();
      return;
    }
    catch (Throwable localThrowable)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("***ATTENTION*** preloadClass#1 failed: ");
      localStringBuilder.append(localThrowable.getMessage());
      Log.e("Preprocessor", localStringBuilder.toString());
    }
  }
  
  private static void preloadClasses0()
  {
    try
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      localObject = Class.forName("com.tencent.smtt.webkit.service.SmttServiceProxy");
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 8);
      localObject = Class.forName("com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 8);
      localObject = Class.forName("com.tencent.smtt.util.MttLog");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 2);
      localObject = Class.forName("com.tencent.smtt.webkit.p");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 2);
      localObject = Class.forName("com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 9);
      localObject = Class.forName("com.tencent.tbs.core.webkit.tencent.TencentWebChromeClientProxy");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 16);
      localObject = Class.forName("org.chromium.net.NetworkChangeNotifier");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 2);
      localObject = Class.forName("com.tencent.tbs.core.webkit.WebView");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 2);
      localObject = Class.forName("android.webview.chromium.tencent.TencentWebViewChromium");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 9);
      localObject = Class.forName("org.chromium.net.NetworkChangeNotifierAutoDetect");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 2);
      localObject = Class.forName("com.tencent.tbs.core.webkit.tencent.TencentWebViewClient");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 5);
      localObject = Class.forName("org.chromium.android_webview.AwContentsClient");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 1);
      localObject = Class.forName("com.tencent.smtt.webkit.WebViewChromiumExtension");
      localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append(((Class)localObject).getName());
      localStringBuilder2.append("$");
      preloadInnerClassesExportError(localStringBuilder2.toString(), localStringBuilder1, 73);
      return;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("***ATTENTION*** preloadClass0 failed: ");
      ((StringBuilder)localObject).append(localThrowable.getMessage());
      Log.e("Preprocessor", ((StringBuilder)localObject).toString());
    }
  }
  
  private static void preloadInnerClassesExportError(String paramString, StringBuilder paramStringBuilder, int paramInt)
  {
    String str = "";
    int i = 1;
    Object localObject1;
    for (;;)
    {
      if (i >= paramInt) {
        return;
      }
      localObject2 = str;
      localObject1 = str;
      try
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localObject2 = str;
        localObject1 = str;
        localStringBuilder.append(paramString);
        localObject2 = str;
        localObject1 = str;
        localStringBuilder.append(i);
        localObject2 = str;
        localObject1 = str;
        str = localStringBuilder.toString();
        localObject2 = str;
        localObject1 = str;
        Class.forName(str);
        i += 1;
      }
      catch (Error paramString)
      {
        localObject1 = localObject2;
      }
      catch (Exception paramString) {}
    }
    if (paramString.getCause() != null) {
      str = paramString.getCause().toString();
    } else {
      str = "";
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("[");
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(" , ");
    ((StringBuilder)localObject2).append(paramString.toString());
    ((StringBuilder)localObject2).append(" , causeby:");
    ((StringBuilder)localObject2).append(str);
    ((StringBuilder)localObject2).append("]");
    paramStringBuilder.append(((StringBuilder)localObject2).toString());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\Preprocessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */