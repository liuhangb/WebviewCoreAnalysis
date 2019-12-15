package org.chromium.content.browser.webcontents;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.ParcelUuid;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.AppWebMessagePort;
import org.chromium.content.browser.MediaSessionImpl;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content.browser.framehost.RenderFrameHostDelegate;
import org.chromium.content.browser.framehost.RenderFrameHostImpl;
import org.chromium.content_public.browser.AccessibilitySnapshotCallback;
import org.chromium.content_public.browser.AccessibilitySnapshotNode;
import org.chromium.content_public.browser.ContentBitmapCallback;
import org.chromium.content_public.browser.ImageDownloadCallback;
import org.chromium.content_public.browser.JavaScriptCallback;
import org.chromium.content_public.browser.MessagePort;
import org.chromium.content_public.browser.NavigationController;
import org.chromium.content_public.browser.RenderFrameHost;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.browser.WebContents.InternalsHolder;
import org.chromium.content_public.browser.WebContentsInternals;
import org.chromium.content_public.browser.WebContentsObserver;
import org.chromium.ui.OverscrollRefreshHandler;
import org.chromium.ui.base.EventForwarder;
import org.chromium.ui.base.WindowAndroid;

@JNINamespace("content")
public class WebContentsImpl
  implements RenderFrameHostDelegate, WebContents
{
  @SuppressLint({"ParcelClassLoader"})
  public static final Parcelable.Creator<WebContents> CREATOR = new Parcelable.Creator()
  {
    public WebContents createFromParcel(Parcel paramAnonymousParcel)
    {
      paramAnonymousParcel = paramAnonymousParcel.readBundle();
      if (paramAnonymousParcel.getLong("version", -1L) != 0L) {
        return null;
      }
      ParcelUuid localParcelUuid = (ParcelUuid)paramAnonymousParcel.getParcelable("processguard");
      if (WebContentsImpl.a().compareTo(localParcelUuid.getUuid()) != 0) {
        return null;
      }
      return WebContentsImpl.a(paramAnonymousParcel.getLong("webcontents"));
    }
    
    public WebContents[] newArray(int paramAnonymousInt)
    {
      return new WebContents[paramAnonymousInt];
    }
  };
  private static UUID jdField_a_of_type_JavaUtilUUID;
  private long jdField_a_of_type_Long;
  private final List<RenderFrameHostImpl> jdField_a_of_type_JavaUtilList = new ArrayList();
  private MediaSessionImpl jdField_a_of_type_OrgChromiumContentBrowserMediaSessionImpl;
  private RenderCoordinates jdField_a_of_type_OrgChromiumContentBrowserRenderCoordinates;
  private SmartClipCallback jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl$SmartClipCallback;
  private WebContentsObserverProxy jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsObserverProxy;
  private NavigationController jdField_a_of_type_OrgChromiumContent_publicBrowserNavigationController;
  private WebContents.InternalsHolder jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents$InternalsHolder;
  private EventForwarder jdField_a_of_type_OrgChromiumUiBaseEventForwarder;
  
  static
  {
    jdField_a_of_type_JavaUtilUUID = UUID.randomUUID();
  }
  
  private WebContentsImpl(long paramLong, NavigationController paramNavigationController)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserNavigationController = paramNavigationController;
    paramNavigationController = new WebContentsInternalsImpl(null);
    paramNavigationController.userDataMap = new HashMap();
    this.jdField_a_of_type_OrgChromiumContentBrowserRenderCoordinates = new RenderCoordinates();
    this.jdField_a_of_type_OrgChromiumContentBrowserRenderCoordinates.reset();
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents$InternalsHolder = new DefaultInternalsHolder(null);
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents$InternalsHolder.set(paramNavigationController);
  }
  
  @CalledByNative
  private static void addAccessibilityNodeAsChild(AccessibilitySnapshotNode paramAccessibilitySnapshotNode1, AccessibilitySnapshotNode paramAccessibilitySnapshotNode2)
  {
    paramAccessibilitySnapshotNode1.a(paramAccessibilitySnapshotNode2);
  }
  
  @CalledByNative
  private static void addToBitmapList(List<Bitmap> paramList, Bitmap paramBitmap)
  {
    paramList.add(paramBitmap);
  }
  
  @CalledByNative
  private void clearNativePtr()
  {
    this.jdField_a_of_type_Long = 0L;
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserNavigationController = null;
    WebContentsObserverProxy localWebContentsObserverProxy = this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsObserverProxy;
    if (localWebContentsObserverProxy != null)
    {
      localWebContentsObserverProxy.destroy();
      this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsObserverProxy = null;
    }
  }
  
  @CalledByNative
  private static WebContentsImpl create(long paramLong, NavigationController paramNavigationController)
  {
    return new WebContentsImpl(paramLong, paramNavigationController);
  }
  
  @CalledByNative
  private static AccessibilitySnapshotNode createAccessibilitySnapshotNode(int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, String paramString1, int paramInt5, int paramInt6, float paramFloat, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, String paramString2)
  {
    paramString1 = new AccessibilitySnapshotNode(paramString1, paramString2);
    if (paramFloat >= 0.0D) {
      paramString1.a(paramInt5, paramInt6, paramFloat, paramBoolean2, paramBoolean3, paramBoolean4, paramBoolean5);
    }
    paramString1.a(paramInt1, paramInt2, paramInt3, paramInt4, paramBoolean1);
    return paramString1;
  }
  
  @CalledByNative
  private static List<Bitmap> createBitmapList()
  {
    return new ArrayList();
  }
  
  @CalledByNative
  private static Rect createSize(int paramInt1, int paramInt2)
  {
    return new Rect(0, 0, paramInt1, paramInt2);
  }
  
  @CalledByNative
  private static void createSizeAndAddToList(List<Rect> paramList, int paramInt1, int paramInt2)
  {
    paramList.add(new Rect(0, 0, paramInt1, paramInt2));
  }
  
  @CalledByNative
  private static List<Rect> createSizeList()
  {
    return new ArrayList();
  }
  
  @CalledByNative
  private long getNativePointer()
  {
    return this.jdField_a_of_type_Long;
  }
  
  @VisibleForTesting
  public static void invalidateSerializedWebContentsForTesting()
  {
    jdField_a_of_type_JavaUtilUUID = UUID.randomUUID();
  }
  
  private native void nativeAddMessageToDevToolsConsole(long paramLong, int paramInt, String paramString);
  
  private native void nativeAdjustSelectionByCharacterOffset(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean);
  
  private native void nativeCollapseSelection(long paramLong);
  
  private native void nativeCopy(long paramLong);
  
  private native void nativeCut(long paramLong);
  
  private static native void nativeDestroyWebContents(long paramLong);
  
  private native void nativeDismissTextHandles(long paramLong);
  
  private native int nativeDownloadImage(long paramLong, String paramString, boolean paramBoolean1, int paramInt, boolean paramBoolean2, ImageDownloadCallback paramImageDownloadCallback);
  
  private native void nativeEvaluateJavaScript(long paramLong, String paramString, JavaScriptCallback paramJavaScriptCallback);
  
  private native void nativeEvaluateJavaScriptForTests(long paramLong, String paramString, JavaScriptCallback paramJavaScriptCallback);
  
  private native void nativeExitFullscreen(long paramLong);
  
  private native boolean nativeFocusLocationBarByDefault(long paramLong);
  
  private static native WebContents nativeFromNativePtr(long paramLong);
  
  private native int nativeGetBackgroundColor(long paramLong);
  
  private native void nativeGetContentBitmap(long paramLong, int paramInt1, int paramInt2, ContentBitmapCallback paramContentBitmapCallback);
  
  private native String nativeGetEncoding(long paramLong);
  
  private native Rect nativeGetFullscreenVideoSize(long paramLong);
  
  private native int nativeGetHeight(long paramLong);
  
  private native String nativeGetLastCommittedURL(long paramLong);
  
  private native RenderFrameHost nativeGetMainFrame(long paramLong);
  
  private native EventForwarder nativeGetOrCreateEventForwarder(long paramLong);
  
  private native int nativeGetThemeColor(long paramLong);
  
  private native String nativeGetTitle(long paramLong);
  
  private native WindowAndroid nativeGetTopLevelNativeWindow(long paramLong);
  
  private native String nativeGetVisibleURL(long paramLong);
  
  private native int nativeGetWidth(long paramLong);
  
  private native boolean nativeHasAccessedInitialDocument(long paramLong);
  
  private native boolean nativeHasActiveEffectivelyFullscreenVideo(long paramLong);
  
  private native boolean nativeIsIncognito(long paramLong);
  
  private native boolean nativeIsLoading(long paramLong);
  
  private native boolean nativeIsLoadingToDifferentDocument(long paramLong);
  
  private native boolean nativeIsPictureInPictureAllowedForFullscreenVideo(long paramLong);
  
  private native boolean nativeIsRenderWidgetHostViewReady(long paramLong);
  
  private native boolean nativeIsShowingInterstitialPage(long paramLong);
  
  private native void nativeOnHide(long paramLong);
  
  private native void nativeOnShow(long paramLong);
  
  private native void nativePaste(long paramLong);
  
  private native void nativePasteAsPlainText(long paramLong);
  
  private native void nativePostMessageToFrame(long paramLong, String paramString1, String paramString2, String paramString3, String paramString4, MessagePort[] paramArrayOfMessagePort);
  
  private native void nativeReloadLoFiImages(long paramLong);
  
  private native void nativeReplace(long paramLong, String paramString);
  
  private native void nativeRequestAccessibilitySnapshot(long paramLong, AccessibilitySnapshotCallback paramAccessibilitySnapshotCallback);
  
  private native void nativeRequestSmartClipExtract(long paramLong, SmartClipCallback paramSmartClipCallback, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  private native void nativeResumeLoadingCreatedWebContents(long paramLong);
  
  private native void nativeScrollFocusedEditableNodeIntoView(long paramLong);
  
  private native void nativeSelectAll(long paramLong);
  
  private native void nativeSelectWordAroundCaret(long paramLong);
  
  private native void nativeSetAudioMuted(long paramLong, boolean paramBoolean);
  
  private native void nativeSetDisplayCutoutSafeArea(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4);
  
  private native void nativeSetHasPersistentVideo(long paramLong, boolean paramBoolean);
  
  private native void nativeSetImportance(long paramLong, int paramInt);
  
  private native void nativeSetOverscrollRefreshHandler(long paramLong, OverscrollRefreshHandler paramOverscrollRefreshHandler);
  
  private native void nativeSetSize(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativeShowContextMenuAtTouchHandle(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativeShowInterstitialPage(long paramLong1, String paramString, long paramLong2);
  
  private native void nativeStop(long paramLong);
  
  private native void nativeSuspendAllMediaPlayers(long paramLong);
  
  private native void nativeUpdateBrowserControlsState(long paramLong, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4);
  
  @CalledByNative
  private static void onAccessibilitySnapshot(AccessibilitySnapshotNode paramAccessibilitySnapshotNode, AccessibilitySnapshotCallback paramAccessibilitySnapshotCallback)
  {
    paramAccessibilitySnapshotCallback.onAccessibilitySnapshot(paramAccessibilitySnapshotNode);
  }
  
  @CalledByNative
  private void onDownloadImageFinished(ImageDownloadCallback paramImageDownloadCallback, int paramInt1, int paramInt2, String paramString, List<Bitmap> paramList, List<Rect> paramList1)
  {
    paramImageDownloadCallback.onFinishDownloadImage(paramInt1, paramInt2, paramString, paramList, paramList1);
  }
  
  @CalledByNative
  private static void onEvaluateJavaScriptResult(String paramString, JavaScriptCallback paramJavaScriptCallback)
  {
    paramJavaScriptCallback.handleJavaScriptResult(paramString);
  }
  
  @CalledByNative
  private void onGetContentBitmapFinished(ContentBitmapCallback paramContentBitmapCallback, Bitmap paramBitmap)
  {
    paramContentBitmapCallback.onFinishGetBitmap(paramBitmap);
  }
  
  @CalledByNative
  private static void onSmartClipDataExtracted(String paramString1, String paramString2, SmartClipCallback paramSmartClipCallback)
  {
    paramSmartClipCallback.onSmartClipDataExtracted(paramString1, paramString2);
  }
  
  @CalledByNative
  private static void setAccessibilitySnapshotSelection(AccessibilitySnapshotNode paramAccessibilitySnapshotNode, int paramInt1, int paramInt2)
  {
    paramAccessibilitySnapshotNode.a(paramInt1, paramInt2);
  }
  
  @CalledByNative
  private final void setMediaSession(MediaSessionImpl paramMediaSessionImpl)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserMediaSessionImpl = paramMediaSessionImpl;
  }
  
  Map<Class, WebContentsUserData> a()
  {
    WebContentsInternals localWebContentsInternals = this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents$InternalsHolder.get();
    if (localWebContentsInternals == null) {
      return null;
    }
    return ((WebContentsInternalsImpl)localWebContentsInternals).userDataMap;
  }
  
  WebContentsUserData a(Class paramClass)
  {
    Map localMap = a();
    if (localMap != null) {
      return (WebContentsUserData)localMap.get(paramClass);
    }
    return null;
  }
  
  void a(Class paramClass, WebContentsUserData paramWebContentsUserData)
  {
    Map localMap = a();
    if (localMap == null)
    {
      Log.e("cr_WebContentsImpl", "UserDataMap can't be found", new Object[0]);
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (localMap.containsKey(paramClass))) {
      throw new AssertionError();
    }
    localMap.put(paramClass, paramWebContentsUserData);
  }
  
  public void addMessageToDevToolsConsole(int paramInt, String paramString)
  {
    nativeAddMessageToDevToolsConsole(this.jdField_a_of_type_Long, paramInt, paramString);
  }
  
  public void addObserver(WebContentsObserver paramWebContentsObserver)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    if (this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsObserverProxy == null) {
      this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsObserverProxy = new WebContentsObserverProxy(this);
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsObserverProxy.a(paramWebContentsObserver);
  }
  
  public void adjustSelectionByCharacterOffset(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    nativeAdjustSelectionByCharacterOffset(this.jdField_a_of_type_Long, paramInt1, paramInt2, paramBoolean);
  }
  
  public void collapseSelection()
  {
    if (isDestroyed()) {
      return;
    }
    nativeCollapseSelection(this.jdField_a_of_type_Long);
  }
  
  public void copy()
  {
    nativeCopy(this.jdField_a_of_type_Long);
  }
  
  public AppWebMessagePort[] createMessageChannel()
    throws IllegalStateException
  {
    return AppWebMessagePort.createPair();
  }
  
  public void cut()
  {
    nativeCut(this.jdField_a_of_type_Long);
  }
  
  public int describeContents()
  {
    return 0;
  }
  
  public void destroy()
  {
    if (ThreadUtils.runningOnUiThread())
    {
      long l = this.jdField_a_of_type_Long;
      if (l != 0L) {
        nativeDestroyWebContents(l);
      }
      return;
    }
    throw new IllegalStateException("Attempting to destroy WebContents on non-UI thread");
  }
  
  public void dismissTextHandles()
  {
    nativeDismissTextHandles(this.jdField_a_of_type_Long);
  }
  
  public int downloadImage(String paramString, boolean paramBoolean1, int paramInt, boolean paramBoolean2, ImageDownloadCallback paramImageDownloadCallback)
  {
    return nativeDownloadImage(this.jdField_a_of_type_Long, paramString, paramBoolean1, paramInt, paramBoolean2, paramImageDownloadCallback);
  }
  
  public void evaluateJavaScript(String paramString, JavaScriptCallback paramJavaScriptCallback)
  {
    if (!isDestroyed())
    {
      if (paramString == null) {
        return;
      }
      nativeEvaluateJavaScript(this.jdField_a_of_type_Long, paramString, paramJavaScriptCallback);
      return;
    }
  }
  
  @VisibleForTesting
  public void evaluateJavaScriptForTests(String paramString, JavaScriptCallback paramJavaScriptCallback)
  {
    if (paramString == null) {
      return;
    }
    nativeEvaluateJavaScriptForTests(this.jdField_a_of_type_Long, paramString, paramJavaScriptCallback);
  }
  
  public void exitFullscreen()
  {
    nativeExitFullscreen(this.jdField_a_of_type_Long);
  }
  
  public boolean focusLocationBarByDefault()
  {
    return nativeFocusLocationBarByDefault(this.jdField_a_of_type_Long);
  }
  
  public int getBackgroundColor()
  {
    return nativeGetBackgroundColor(this.jdField_a_of_type_Long);
  }
  
  public void getContentBitmapAsync(int paramInt1, int paramInt2, ContentBitmapCallback paramContentBitmapCallback)
  {
    nativeGetContentBitmap(this.jdField_a_of_type_Long, paramInt1, paramInt2, paramContentBitmapCallback);
  }
  
  public String getEncoding()
  {
    return nativeGetEncoding(this.jdField_a_of_type_Long);
  }
  
  public EventForwarder getEventForwarder()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    if (this.jdField_a_of_type_OrgChromiumUiBaseEventForwarder == null) {
      this.jdField_a_of_type_OrgChromiumUiBaseEventForwarder = nativeGetOrCreateEventForwarder(this.jdField_a_of_type_Long);
    }
    return this.jdField_a_of_type_OrgChromiumUiBaseEventForwarder;
  }
  
  @Nullable
  public Rect getFullscreenVideoSize()
  {
    return nativeGetFullscreenVideoSize(this.jdField_a_of_type_Long);
  }
  
  public int getHeight()
  {
    return nativeGetHeight(this.jdField_a_of_type_Long);
  }
  
  public String getLastCommittedUrl()
  {
    return nativeGetLastCommittedURL(this.jdField_a_of_type_Long);
  }
  
  public RenderFrameHost getMainFrame()
  {
    return nativeGetMainFrame(this.jdField_a_of_type_Long);
  }
  
  public NavigationController getNavigationController()
  {
    return this.jdField_a_of_type_OrgChromiumContent_publicBrowserNavigationController;
  }
  
  public RenderCoordinates getRenderCoordinates()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserRenderCoordinates;
  }
  
  public int getThemeColor()
  {
    return nativeGetThemeColor(this.jdField_a_of_type_Long);
  }
  
  public String getTitle()
  {
    return nativeGetTitle(this.jdField_a_of_type_Long);
  }
  
  public WindowAndroid getTopLevelNativeWindow()
  {
    return nativeGetTopLevelNativeWindow(this.jdField_a_of_type_Long);
  }
  
  public String getVisibleUrl()
  {
    return nativeGetVisibleURL(this.jdField_a_of_type_Long);
  }
  
  public int getWidth()
  {
    return nativeGetWidth(this.jdField_a_of_type_Long);
  }
  
  public boolean hasAccessedInitialDocument()
  {
    return nativeHasAccessedInitialDocument(this.jdField_a_of_type_Long);
  }
  
  public boolean hasActiveEffectivelyFullscreenVideo()
  {
    return nativeHasActiveEffectivelyFullscreenVideo(this.jdField_a_of_type_Long);
  }
  
  public boolean isDestroyed()
  {
    return this.jdField_a_of_type_Long == 0L;
  }
  
  public boolean isIncognito()
  {
    return nativeIsIncognito(this.jdField_a_of_type_Long);
  }
  
  public boolean isLoading()
  {
    return nativeIsLoading(this.jdField_a_of_type_Long);
  }
  
  public boolean isLoadingToDifferentDocument()
  {
    return nativeIsLoadingToDifferentDocument(this.jdField_a_of_type_Long);
  }
  
  public boolean isPictureInPictureAllowedForFullscreenVideo()
  {
    return nativeIsPictureInPictureAllowedForFullscreenVideo(this.jdField_a_of_type_Long);
  }
  
  public boolean isReady()
  {
    return nativeIsRenderWidgetHostViewReady(this.jdField_a_of_type_Long);
  }
  
  public boolean isShowingInterstitialPage()
  {
    return nativeIsShowingInterstitialPage(this.jdField_a_of_type_Long);
  }
  
  public void onHide()
  {
    nativeOnHide(this.jdField_a_of_type_Long);
  }
  
  public void onShow()
  {
    nativeOnShow(this.jdField_a_of_type_Long);
  }
  
  public void paste()
  {
    nativePaste(this.jdField_a_of_type_Long);
  }
  
  public void pasteAsPlainText()
  {
    nativePasteAsPlainText(this.jdField_a_of_type_Long);
  }
  
  public void postMessageToFrame(String paramString1, String paramString2, String paramString3, String paramString4, MessagePort[] paramArrayOfMessagePort)
  {
    if (paramArrayOfMessagePort != null)
    {
      int j = paramArrayOfMessagePort.length;
      int i = 0;
      while (i < j)
      {
        MessagePort localMessagePort = paramArrayOfMessagePort[i];
        if ((!localMessagePort.isClosed()) && (!localMessagePort.isTransferred()))
        {
          if (!localMessagePort.isStarted()) {
            i += 1;
          } else {
            throw new IllegalStateException("Port is already started");
          }
        }
        else {
          throw new IllegalStateException("Port is already closed or transferred");
        }
      }
    }
    if (paramString4.equals("*")) {
      paramString4 = "";
    }
    nativePostMessageToFrame(this.jdField_a_of_type_Long, paramString1, paramString2, paramString3, paramString4, paramArrayOfMessagePort);
  }
  
  public void reloadLoFiImages()
  {
    nativeReloadLoFiImages(this.jdField_a_of_type_Long);
  }
  
  public void removeObserver(WebContentsObserver paramWebContentsObserver)
  {
    WebContentsObserverProxy localWebContentsObserverProxy = this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsObserverProxy;
    if (localWebContentsObserverProxy == null) {
      return;
    }
    localWebContentsObserverProxy.b(paramWebContentsObserver);
  }
  
  public void renderFrameCreated(RenderFrameHostImpl paramRenderFrameHostImpl)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_JavaUtilList.contains(paramRenderFrameHostImpl))) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_JavaUtilList.add(paramRenderFrameHostImpl);
  }
  
  public void renderFrameDeleted(RenderFrameHostImpl paramRenderFrameHostImpl)
  {
    if ((!jdField_a_of_type_Boolean) && (!this.jdField_a_of_type_JavaUtilList.contains(paramRenderFrameHostImpl))) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_JavaUtilList.remove(paramRenderFrameHostImpl);
  }
  
  public void replace(String paramString)
  {
    nativeReplace(this.jdField_a_of_type_Long, paramString);
  }
  
  public void requestAccessibilitySnapshot(AccessibilitySnapshotCallback paramAccessibilitySnapshotCallback)
  {
    nativeRequestAccessibilitySnapshot(this.jdField_a_of_type_Long, paramAccessibilitySnapshotCallback);
  }
  
  public void requestSmartClipExtract(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl$SmartClipCallback;
    if (localObject == null) {
      return;
    }
    ((SmartClipCallback)localObject).storeRequestRect(new Rect(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4));
    localObject = getRenderCoordinates();
    float f = ((RenderCoordinates)localObject).getDeviceScaleFactor();
    paramInt2 = (int)(paramInt2 - ((RenderCoordinates)localObject).getContentOffsetYPix());
    nativeRequestSmartClipExtract(this.jdField_a_of_type_Long, this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl$SmartClipCallback, (int)(paramInt1 / f), (int)(paramInt2 / f), (int)(paramInt3 / f), (int)(paramInt4 / f));
  }
  
  public void resumeLoadingCreatedWebContents()
  {
    nativeResumeLoadingCreatedWebContents(this.jdField_a_of_type_Long);
  }
  
  public void scrollFocusedEditableNodeIntoView()
  {
    if (isDestroyed()) {
      return;
    }
    nativeScrollFocusedEditableNodeIntoView(this.jdField_a_of_type_Long);
  }
  
  public void selectAll()
  {
    nativeSelectAll(this.jdField_a_of_type_Long);
  }
  
  public void selectWordAroundCaret()
  {
    nativeSelectWordAroundCaret(this.jdField_a_of_type_Long);
  }
  
  public void setAudioMuted(boolean paramBoolean)
  {
    nativeSetAudioMuted(this.jdField_a_of_type_Long, paramBoolean);
  }
  
  public void setDisplayCutoutSafeArea(Rect paramRect)
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    nativeSetDisplayCutoutSafeArea(l, paramRect.top, paramRect.left, paramRect.bottom, paramRect.right);
  }
  
  public void setHasPersistentVideo(boolean paramBoolean)
  {
    nativeSetHasPersistentVideo(this.jdField_a_of_type_Long, paramBoolean);
  }
  
  public void setImportance(int paramInt)
  {
    nativeSetImportance(this.jdField_a_of_type_Long, paramInt);
  }
  
  public void setInternalsHolder(WebContents.InternalsHolder paramInternalsHolder)
  {
    if ((!jdField_a_of_type_Boolean) && (!(this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents$InternalsHolder instanceof DefaultInternalsHolder))) {
      throw new AssertionError();
    }
    paramInternalsHolder.set(this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents$InternalsHolder.get());
    this.jdField_a_of_type_OrgChromiumContent_publicBrowserWebContents$InternalsHolder = paramInternalsHolder;
  }
  
  public void setOverscrollRefreshHandler(OverscrollRefreshHandler paramOverscrollRefreshHandler)
  {
    nativeSetOverscrollRefreshHandler(this.jdField_a_of_type_Long, paramOverscrollRefreshHandler);
  }
  
  public void setSize(int paramInt1, int paramInt2)
  {
    nativeSetSize(this.jdField_a_of_type_Long, paramInt1, paramInt2);
  }
  
  public void setSmartClipResultHandler(Handler paramHandler)
  {
    if (paramHandler == null)
    {
      this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl$SmartClipCallback = null;
      return;
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsImpl$SmartClipCallback = new SmartClipCallback(paramHandler);
  }
  
  public void showContextMenuAtTouchHandle(int paramInt1, int paramInt2)
  {
    nativeShowContextMenuAtTouchHandle(this.jdField_a_of_type_Long, paramInt1, paramInt2);
  }
  
  public void showInterstitialPage(String paramString, long paramLong)
  {
    nativeShowInterstitialPage(this.jdField_a_of_type_Long, paramString, paramLong);
  }
  
  @VisibleForTesting
  public void simulateRendererKilledForTesting(boolean paramBoolean)
  {
    WebContentsObserverProxy localWebContentsObserverProxy = this.jdField_a_of_type_OrgChromiumContentBrowserWebcontentsWebContentsObserverProxy;
    if (localWebContentsObserverProxy != null) {
      localWebContentsObserverProxy.renderProcessGone(paramBoolean);
    }
  }
  
  public void stop()
  {
    nativeStop(this.jdField_a_of_type_Long);
  }
  
  public void suspendAllMediaPlayers()
  {
    nativeSuspendAllMediaPlayers(this.jdField_a_of_type_Long);
  }
  
  public void updateBrowserControlsState(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    nativeUpdateBrowserControlsState(this.jdField_a_of_type_Long, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4);
  }
  
  public void writeToParcel(Parcel paramParcel, int paramInt)
  {
    Bundle localBundle = new Bundle();
    localBundle.putLong("version", 0L);
    localBundle.putParcelable("processguard", new ParcelUuid(jdField_a_of_type_JavaUtilUUID));
    localBundle.putLong("webcontents", this.jdField_a_of_type_Long);
    paramParcel.writeBundle(localBundle);
  }
  
  private static class DefaultInternalsHolder
    implements WebContents.InternalsHolder
  {
    private WebContentsInternals a;
    
    public WebContentsInternals get()
    {
      return this.a;
    }
    
    public void set(WebContentsInternals paramWebContentsInternals)
    {
      this.a = paramWebContentsInternals;
    }
  }
  
  private class SmartClipCallback
  {
    Rect jdField_a_of_type_AndroidGraphicsRect;
    final Handler jdField_a_of_type_AndroidOsHandler;
    
    public SmartClipCallback(Handler paramHandler)
    {
      this.jdField_a_of_type_AndroidOsHandler = paramHandler;
    }
    
    public void onSmartClipDataExtracted(String paramString1, String paramString2)
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("url", WebContentsImpl.this.getVisibleUrl());
      localBundle.putString("title", WebContentsImpl.this.getTitle());
      localBundle.putString("text", paramString1);
      localBundle.putString("html", paramString2);
      localBundle.putParcelable("rect", this.jdField_a_of_type_AndroidGraphicsRect);
      paramString1 = Message.obtain(this.jdField_a_of_type_AndroidOsHandler, 0);
      paramString1.setData(localBundle);
      paramString1.sendToTarget();
    }
    
    public void storeRequestRect(Rect paramRect)
    {
      this.jdField_a_of_type_AndroidGraphicsRect = paramRect;
    }
  }
  
  private static class WebContentsInternalsImpl
    implements WebContentsInternals
  {
    public HashMap<Class, WebContentsUserData> userDataMap;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\webcontents\WebContentsImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */