package org.chromium.content.browser;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import org.chromium.base.ObserverList;
import org.chromium.base.TraceEvent;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.accessibility.WebContentsAccessibilityImpl;
import org.chromium.content.browser.accessibility.captioning.CaptioningBridgeFactory;
import org.chromium.content.browser.accessibility.captioning.SystemCaptioningBridge;
import org.chromium.content.browser.accessibility.captioning.SystemCaptioningBridge.SystemCaptioningBridgeListener;
import org.chromium.content.browser.accessibility.captioning.TextTrackSettings;
import org.chromium.content.browser.input.ImeAdapterImpl;
import org.chromium.content.browser.input.SelectPopup;
import org.chromium.content.browser.input.TextSuggestionHost;
import org.chromium.content.browser.selection.SelectionPopupControllerImpl;
import org.chromium.content.browser.webcontents.WebContentsImpl;
import org.chromium.content_public.browser.ActionModeCallbackHelper;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.content_public.browser.ContentViewCore.InternalAccessDelegate;
import org.chromium.content_public.browser.GestureStateListener;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.browser.WebContentsObserver;
import org.chromium.device.gamepad.GamepadList;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;
import org.chromium.ui.base.EventForwarder;
import org.chromium.ui.base.ViewAndroidDelegate;
import org.chromium.ui.base.ViewUtils;
import org.chromium.ui.base.WindowAndroid;
import org.chromium.ui.display.DisplayAndroid;
import org.chromium.ui.display.DisplayAndroid.DisplayAndroidObserver;

@JNINamespace("content")
public class ContentViewCoreImpl
  extends ContentViewCore
  implements SystemCaptioningBridge.SystemCaptioningBridgeListener, DisplayAndroid.DisplayAndroidObserver
{
  public static final int INVALID_RENDER_PROCESS_PID = 0;
  private static final String TAG = "cr_ContentViewCore";
  protected boolean mAttachedToWindow;
  protected ViewGroup mContainerView;
  private ContentViewCore.InternalAccessDelegate mContainerViewInternals;
  protected final Context mContext;
  protected Boolean mHasViewFocus;
  private boolean mIsMobileOptimizedHint;
  public long mNativeContentViewCore;
  protected long mNativeSelectPopupSourceFrame;
  protected boolean mPaused;
  protected boolean mPreserveSelectionOnNextLossOfFocus;
  private final String mProductVersion;
  protected RenderCoordinates mRenderCoordinates;
  protected SelectPopup mSelectPopup;
  private boolean mShouldRequestUnbufferedDispatch;
  protected final SystemCaptioningBridge mSystemCaptioningBridge;
  private boolean mTouchScrollInProgress;
  private ViewAndroidDelegate mViewAndroidDelegate;
  protected WebContentsImpl mWebContents;
  private WebContentsObserver mWebContentsObserver;
  private final ObserverList<WindowAndroidChangedObserver> mWindowAndroidChangedObservers;
  private final ObserverList<WindowEventObserver> mWindowEventObservers = new ObserverList();
  
  public ContentViewCoreImpl(Context paramContext, String paramString)
  {
    this.mContext = paramContext;
    this.mProductVersion = paramString;
    this.mSystemCaptioningBridge = CaptioningBridgeFactory.getSystemCaptioningBridge(this.mContext);
    this.mWindowAndroidChangedObservers = new ObserverList();
  }
  
  private void destroyPastePopup()
  {
    getSelectionPopupController().destroyPastePopup();
  }
  
  public static ContentViewCoreImpl fromWebContents(WebContents paramWebContents)
  {
    return nativeFromWebContentsAndroid(paramWebContents);
  }
  
  private EventForwarder getEventForwarder()
  {
    return getWebContents().getEventForwarder();
  }
  
  private GestureListenerManagerImpl getGestureListenerManager()
  {
    return GestureListenerManagerImpl.fromWebContents(this.mWebContents);
  }
  
  private ImeAdapterImpl getImeAdapter()
  {
    return ImeAdapterImpl.fromWebContents(this.mWebContents);
  }
  
  private JoystickHandler getJoystick()
  {
    return JoystickHandler.fromWebContents(this.mWebContents);
  }
  
  @CalledByNative
  private float getMouseWheelTickMultiplier()
  {
    return this.mRenderCoordinates.getWheelScrollFactor() / this.mRenderCoordinates.getDeviceScaleFactor();
  }
  
  private SelectionPopupControllerImpl getSelectionPopupController()
  {
    return SelectionPopupControllerImpl.fromWebContents(this.mWebContents);
  }
  
  private TapDisambiguator getTapDisambiguator()
  {
    return TapDisambiguator.fromWebContents(this.mWebContents);
  }
  
  private TextSuggestionHost getTextSuggestionHost()
  {
    return TextSuggestionHost.fromWebContents(this.mWebContents);
  }
  
  private WebContentsAccessibilityImpl getWebContentsAccessibility()
  {
    return WebContentsAccessibilityImpl.fromWebContents(this.mWebContents);
  }
  
  private void hidePopups()
  {
    destroyPastePopup();
    getTapDisambiguator().hidePopup(false);
    getTextSuggestionHost().hidePopups();
    hideSelectPopupWithCancelMessage();
  }
  
  @CalledByNative
  private void hideSelectPopup()
  {
    SelectPopup localSelectPopup = this.mSelectPopup;
    if (localSelectPopup == null) {
      return;
    }
    localSelectPopup.hide(false);
    this.mSelectPopup = null;
    this.mNativeSelectPopupSourceFrame = 0L;
  }
  
  private void hideSelectPopupWithCancelMessage()
  {
    SelectPopup localSelectPopup = this.mSelectPopup;
    if (localSelectPopup != null) {
      localSelectPopup.hide(true);
    }
  }
  
  private native void nativeDoubleTap(long paramLong1, long paramLong2, float paramFloat1, float paramFloat2);
  
  private static native ContentViewCoreImpl nativeFromWebContentsAndroid(WebContents paramWebContents);
  
  private native WindowAndroid nativeGetJavaWindowAndroid(long paramLong);
  
  private native int nativeGetTopControlsShrinkBlinkHeightPixForTesting(long paramLong);
  
  private native WebContents nativeGetWebContentsAndroid(long paramLong);
  
  private native long nativeInit(WebContents paramWebContents, ViewAndroidDelegate paramViewAndroidDelegate, WindowAndroid paramWindowAndroid, float paramFloat);
  
  private native void nativeOnJavaContentViewCoreDestroyed(long paramLong);
  
  private native void nativeResetGestureDetection(long paramLong);
  
  private native void nativeScrollBegin(long paramLong1, long paramLong2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, boolean paramBoolean1, boolean paramBoolean2);
  
  private native void nativeScrollBy(long paramLong1, long paramLong2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  private native void nativeScrollEnd(long paramLong1, long paramLong2);
  
  private native void nativeSelectPopupMenuItems(long paramLong1, long paramLong2, int[] paramArrayOfInt);
  
  private native void nativeSendOrientationChangeEvent(long paramLong, int paramInt);
  
  private native void nativeSetDIPScale(long paramLong, float paramFloat);
  
  private native void nativeSetDoubleTapSupportEnabled(long paramLong, boolean paramBoolean);
  
  private native void nativeSetMultiTouchZoomSupportEnabled(long paramLong, boolean paramBoolean);
  
  private native void nativeSetTextHandlesTemporarilyHidden(long paramLong, boolean paramBoolean);
  
  private native void nativeSetTextTrackSettings(long paramLong, boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7);
  
  private native void nativeUpdateWindowAndroid(long paramLong, WindowAndroid paramWindowAndroid);
  
  private native boolean nativeUsingSynchronousCompositing(long paramLong);
  
  @CalledByNative
  private void onNativeContentViewCoreDestroyed(long paramLong)
  {
    this.mNativeContentViewCore = 0L;
  }
  
  @CalledByNative
  private void onRenderProcessChange()
  {
    this.mSystemCaptioningBridge.syncToListener(this);
  }
  
  @CalledByNative
  private void onTouchDown(MotionEvent paramMotionEvent)
  {
    if (this.mShouldRequestUnbufferedDispatch) {
      requestUnbufferedDispatch(paramMotionEvent);
    }
    cancelRequestToScrollFocusedEditableNodeIntoView();
    getGestureListenerManager().updateOnTouchDown();
  }
  
  @CalledByNative
  private void requestDisallowInterceptTouchEvent()
  {
    this.mContainerView.requestDisallowInterceptTouchEvent(true);
  }
  
  @TargetApi(21)
  private void requestUnbufferedDispatch(MotionEvent paramMotionEvent)
  {
    this.mContainerView.requestUnbufferedDispatch(paramMotionEvent);
  }
  
  private void resetGestureDetection()
  {
    long l = this.mNativeContentViewCore;
    if (l == 0L) {
      return;
    }
    nativeResetGestureDetection(l);
  }
  
  private void resetScrollInProgress()
  {
    if (!isScrollInProgress()) {
      return;
    }
    boolean bool = this.mTouchScrollInProgress;
    setTouchScrollInProgress(false);
    if (bool) {
      getGestureListenerManager().updateOnScrollEnd();
    }
    getGestureListenerManager().resetFlingGesture();
  }
  
  @VisibleForTesting
  private void sendOrientationChangeEvent(int paramInt)
  {
    long l = this.mNativeContentViewCore;
    if (l == 0L) {
      return;
    }
    nativeSendOrientationChangeEvent(l, paramInt);
  }
  
  private void setTextHandlesTemporarilyHidden(boolean paramBoolean)
  {
    long l = this.mNativeContentViewCore;
    if (l == 0L) {
      return;
    }
    nativeSetTextHandlesTemporarilyHidden(l, paramBoolean);
  }
  
  private void setTouchScrollInProgress(boolean paramBoolean)
  {
    this.mTouchScrollInProgress = paramBoolean;
    getSelectionPopupController().setScrollInProgress(paramBoolean, isScrollInProgress());
  }
  
  private static boolean shouldPropagateKeyEvent(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    return (i != 82) && (i != 3) && (i != 4) && (i != 5) && (i != 6) && (i != 26) && (i != 79) && (i != 27) && (i != 80) && (i != 25) && (i != 164) && (i != 24);
  }
  
  protected void addDisplayAndroidObserverIfNeeded()
  {
    if (!this.mAttachedToWindow) {
      return;
    }
    Object localObject = getWindowAndroid();
    if (localObject != null)
    {
      localObject = ((WindowAndroid)localObject).getDisplay();
      ((DisplayAndroid)localObject).addObserver(this);
      onRotationChanged(((DisplayAndroid)localObject).getRotation());
      onDIPScaleChanged(((DisplayAndroid)localObject).getDipScale());
    }
  }
  
  public void addWindowAndroidChangedObserver(WindowAndroidChangedObserver paramWindowAndroidChangedObserver)
  {
    this.mWindowAndroidChangedObservers.addObserver(paramWindowAndroidChangedObserver);
  }
  
  public boolean awakenScrollBars(int paramInt, boolean paramBoolean)
  {
    if (this.mContainerView.getScrollBarStyle() == 0) {
      return false;
    }
    return this.mContainerViewInternals.super_awakenScrollBars(paramInt, paramBoolean);
  }
  
  protected void cancelRequestToScrollFocusedEditableNodeIntoView()
  {
    getImeAdapter().getFocusPreOSKViewportRect().setEmpty();
  }
  
  public int computeHorizontalScrollExtent()
  {
    return this.mRenderCoordinates.getLastFrameViewportWidthPixInt();
  }
  
  public int computeHorizontalScrollOffset()
  {
    return this.mRenderCoordinates.getScrollXPixInt();
  }
  
  public int computeHorizontalScrollRange()
  {
    return this.mRenderCoordinates.getContentWidthPixInt();
  }
  
  public int computeVerticalScrollExtent()
  {
    return this.mRenderCoordinates.getLastFrameViewportHeightPixInt();
  }
  
  public int computeVerticalScrollOffset()
  {
    return this.mRenderCoordinates.getScrollYPixInt();
  }
  
  public int computeVerticalScrollRange()
  {
    return this.mRenderCoordinates.getContentHeightPixInt();
  }
  
  public void destroy()
  {
    removeDisplayAndroidObserver();
    long l = this.mNativeContentViewCore;
    if (l != 0L) {
      nativeOnJavaContentViewCoreDestroyed(l);
    }
    this.mWebContentsObserver.destroy();
    this.mWebContentsObserver = null;
    ImeAdapterImpl localImeAdapterImpl = getImeAdapter();
    localImeAdapterImpl.resetAndHideKeyboard();
    localImeAdapterImpl.removeEventObserver(getSelectionPopupController());
    localImeAdapterImpl.removeEventObserver(getJoystick());
    localImeAdapterImpl.removeEventObserver(getTapDisambiguator());
    getGestureListenerManager().reset();
    removeWindowAndroidChangedObserver(getTextSuggestionHost());
    this.mWindowEventObservers.clear();
    hidePopupsAndPreserveSelection();
    this.mWebContents = null;
    this.mNativeContentViewCore = 0L;
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    if (GamepadList.a(paramKeyEvent)) {
      return true;
    }
    if (!shouldPropagateKeyEvent(paramKeyEvent)) {
      return this.mContainerViewInternals.super_dispatchKeyEvent(paramKeyEvent);
    }
    if (getImeAdapter().dispatchKeyEvent(paramKeyEvent)) {
      return true;
    }
    return this.mContainerViewInternals.super_dispatchKeyEvent(paramKeyEvent);
  }
  
  @CalledByNative
  protected boolean filterTapOrPressEvent(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 == 5) && (offerLongPressToEmbedder())) {
      return true;
    }
    TapDisambiguator localTapDisambiguator = getTapDisambiguator();
    if (!localTapDisambiguator.isShowing()) {
      localTapDisambiguator.setLastTouch(paramInt2, paramInt3);
    }
    return false;
  }
  
  public ViewGroup getContainerView()
  {
    return this.mContainerView;
  }
  
  public Context getContext()
  {
    return this.mContext;
  }
  
  public boolean getIsMobileOptimizedHint()
  {
    return this.mIsMobileOptimizedHint;
  }
  
  public RenderCoordinates getRenderCoordinates()
  {
    return this.mRenderCoordinates;
  }
  
  @VisibleForTesting
  public int getTopControlsShrinkBlinkHeightForTesting()
  {
    long l = this.mNativeContentViewCore;
    if (l == 0L) {
      return 0;
    }
    return nativeGetTopControlsShrinkBlinkHeightPixForTesting(l);
  }
  
  @CalledByNative
  public int getViewportHeightPix()
  {
    return this.mContainerView.getHeight();
  }
  
  @CalledByNative
  public int getViewportWidthPix()
  {
    return this.mContainerView.getWidth();
  }
  
  public WebContents getWebContents()
  {
    return this.mWebContents;
  }
  
  public WindowAndroid getWindowAndroid()
  {
    long l = this.mNativeContentViewCore;
    if (l == 0L) {
      return null;
    }
    return nativeGetJavaWindowAndroid(l);
  }
  
  protected void hidePopupsAndClearSelection()
  {
    getSelectionPopupController().destroyActionModeAndUnselect();
    this.mWebContents.dismissTextHandles();
    hidePopups();
  }
  
  @CalledByNative
  protected void hidePopupsAndPreserveSelection()
  {
    getSelectionPopupController().destroyActionModeAndKeepSelection();
    hidePopups();
  }
  
  public void initialize(ViewAndroidDelegate paramViewAndroidDelegate, ContentViewCore.InternalAccessDelegate paramInternalAccessDelegate, WebContents paramWebContents, WindowAndroid paramWindowAndroid)
  {
    this.mViewAndroidDelegate = paramViewAndroidDelegate;
    float f = paramWindowAndroid.getDisplay().getDipScale();
    this.mNativeContentViewCore = nativeInit(paramWebContents, this.mViewAndroidDelegate, paramWindowAndroid, f);
    this.mWebContents = ((WebContentsImpl)nativeGetWebContentsAndroid(this.mNativeContentViewCore));
    ViewGroup localViewGroup = paramViewAndroidDelegate.getContainerView();
    paramViewAndroidDelegate = SelectionPopupControllerImpl.create(this.mContext, paramWindowAndroid, paramWebContents, localViewGroup);
    paramViewAndroidDelegate.setActionModeCallback(ActionModeCallbackHelper.EMPTY_CALLBACK);
    addWindowAndroidChangedObserver(paramViewAndroidDelegate);
    setContainerView(localViewGroup);
    this.mRenderCoordinates = this.mWebContents.getRenderCoordinates();
    this.mRenderCoordinates.setDeviceScaleFactor(f, (Context)paramWindowAndroid.getContext().get());
    paramWebContents = WebContentsAccessibilityImpl.create(this.mContext, localViewGroup, paramWebContents, this.mProductVersion);
    setContainerViewInternals(paramInternalAccessDelegate);
    paramInternalAccessDelegate = ImeAdapterImpl.create(this.mWebContents, this.mContainerView, ImeAdapterImpl.createDefaultInputMethodManagerWrapper(this.mContext));
    paramInternalAccessDelegate.addEventObserver(paramViewAndroidDelegate);
    paramInternalAccessDelegate.addEventObserver(getJoystick());
    paramInternalAccessDelegate.addEventObserver(TapDisambiguator.create(this.mContext, this.mWebContents, localViewGroup));
    paramWindowAndroid = TextSuggestionHost.create(this.mContext, this.mWebContents, paramWindowAndroid, localViewGroup);
    addWindowAndroidChangedObserver(paramWindowAndroid);
    this.mWebContentsObserver = new ContentViewWebContentsObserver(this);
    boolean bool;
    if ((Build.VERSION.SDK_INT >= 21) && (ContentFeatureList.isEnabled("RequestUnbufferedDispatch")) && (!nativeUsingSynchronousCompositing(this.mNativeContentViewCore))) {
      bool = true;
    } else {
      bool = false;
    }
    this.mShouldRequestUnbufferedDispatch = bool;
    getGestureListenerManager().addListener(new ContentGestureStateListener(null));
    this.mWindowEventObservers.addObserver(paramViewAndroidDelegate);
    this.mWindowEventObservers.addObserver(getGestureListenerManager());
    this.mWindowEventObservers.addObserver(paramWindowAndroid);
    this.mWindowEventObservers.addObserver(paramInternalAccessDelegate);
    this.mWindowEventObservers.addObserver(paramWebContents);
  }
  
  public boolean isAlive()
  {
    return this.mNativeContentViewCore != 0L;
  }
  
  public boolean isAttachedToWindow()
  {
    return this.mAttachedToWindow;
  }
  
  public boolean isScrollInProgress()
  {
    return (this.mTouchScrollInProgress) || (getGestureListenerManager().hasPotentiallyActiveFling());
  }
  
  @VisibleForTesting
  public boolean isSelectPopupVisibleForTest()
  {
    return this.mSelectPopup != null;
  }
  
  protected native void nativeFocusTtsNode(long paramLong, int paramInt, boolean paramBoolean);
  
  protected native void nativeGetTtsTextAsync(long paramLong, int paramInt);
  
  protected native void nativeSetFocus(long paramLong, boolean paramBoolean);
  
  protected native void nativesetSiteSecurityInfo(long paramLong, String paramString1, String paramString2);
  
  protected boolean offerLongPressToEmbedder()
  {
    return this.mContainerView.performLongClick();
  }
  
  public void onAttachedToWindow()
  {
    this.mAttachedToWindow = true;
    Iterator localIterator = this.mWindowEventObservers.iterator();
    while (localIterator.hasNext()) {
      ((WindowEventObserver)localIterator.next()).onAttachedToWindow();
    }
    addDisplayAndroidObserverIfNeeded();
    updateTextSelectionUI(true);
    if (Build.VERSION.SDK_INT >= 17) {
      GamepadList.a(this.mContext);
    }
    this.mSystemCaptioningBridge.addListener(this);
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    try
    {
      TraceEvent.begin("ContentViewCore.onConfigurationChanged");
      getImeAdapter().onKeyboardConfigurationChanged(paramConfiguration);
      this.mContainerViewInternals.super_onConfigurationChanged(paramConfiguration);
      this.mContainerView.requestLayout();
      return;
    }
    finally
    {
      TraceEvent.end("ContentViewCore.onConfigurationChanged");
    }
  }
  
  public void onDIPScaleChanged(float paramFloat)
  {
    if (getWindowAndroid() != null)
    {
      if (this.mNativeContentViewCore == 0L) {
        return;
      }
      this.mRenderCoordinates.setDeviceScaleFactor(paramFloat, (Context)getWindowAndroid().getContext().get());
      nativeSetDIPScale(this.mNativeContentViewCore, paramFloat);
      return;
    }
  }
  
  @SuppressLint({"MissingSuperCall"})
  public void onDetachedFromWindow()
  {
    this.mAttachedToWindow = false;
    Iterator localIterator = this.mWindowEventObservers.iterator();
    while (localIterator.hasNext()) {
      ((WindowEventObserver)localIterator.next()).onDetachedFromWindow();
    }
    removeDisplayAndroidObserver();
    if (Build.VERSION.SDK_INT >= 17) {
      GamepadList.a();
    }
    updateTextSelectionUI(false);
    this.mSystemCaptioningBridge.removeListener(this);
  }
  
  public void onFocusChanged(boolean paramBoolean1, boolean paramBoolean2)
  {
    Object localObject = this.mHasViewFocus;
    if ((localObject != null) && (((Boolean)localObject).booleanValue() == paramBoolean1)) {
      return;
    }
    if ((paramBoolean1) && (this.mPaused)) {
      return;
    }
    this.mHasViewFocus = Boolean.valueOf(paramBoolean1);
    if (this.mWebContents == null) {
      return;
    }
    getImeAdapter().onViewFocusChanged(paramBoolean1, paramBoolean2);
    localObject = getJoystick();
    if ((paramBoolean1) && (!getSelectionPopupController().isFocusedNodeEditable())) {
      paramBoolean2 = true;
    } else {
      paramBoolean2 = false;
    }
    ((JoystickHandler)localObject).setScrollEnabled(paramBoolean2);
    if (paramBoolean1)
    {
      getSelectionPopupController().restoreSelectionPopupsIfNecessary();
    }
    else
    {
      cancelRequestToScrollFocusedEditableNodeIntoView();
      if (this.mPreserveSelectionOnNextLossOfFocus)
      {
        this.mPreserveSelectionOnNextLossOfFocus = false;
        hidePopupsAndPreserveSelection();
      }
      else
      {
        hidePopupsAndClearSelection();
        getSelectionPopupController().clearSelection();
      }
    }
    long l = this.mNativeContentViewCore;
    if (l != 0L) {
      nativeSetFocus(l, paramBoolean1);
    }
  }
  
  public boolean onGenericMotionEvent(MotionEvent paramMotionEvent)
  {
    if (GamepadList.a(paramMotionEvent)) {
      return true;
    }
    if (getJoystick().onGenericMotionEvent(paramMotionEvent)) {
      return true;
    }
    if ((paramMotionEvent.getSource() & 0x2) != 0)
    {
      int i = paramMotionEvent.getActionMasked();
      if (i != 8)
      {
        switch (i)
        {
        default: 
          break;
        case 11: 
        case 12: 
          if (paramMotionEvent.getToolType(0) != 3) {
            break;
          }
          return getEventForwarder().d(paramMotionEvent);
        }
      }
      else
      {
        getEventForwarder().a(paramMotionEvent.getEventTime(), paramMotionEvent.getX(), paramMotionEvent.getY(), paramMotionEvent.getAxisValue(10), paramMotionEvent.getAxisValue(9), this.mRenderCoordinates.getWheelScrollFactor());
        return true;
      }
    }
    return this.mContainerViewInternals.super_onGenericMotionEvent(paramMotionEvent);
  }
  
  public void onHide()
  {
    hidePopupsAndPreserveSelection();
    this.mWebContents.onHide();
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    TapDisambiguator localTapDisambiguator = getTapDisambiguator();
    if ((localTapDisambiguator.isShowing()) && (paramInt == 4))
    {
      localTapDisambiguator.backButtonPressed();
      return true;
    }
    return this.mContainerViewInternals.super_onKeyUp(paramInt, paramKeyEvent);
  }
  
  public void onPause()
  {
    this.mPaused = true;
    onFocusChanged(false, true);
  }
  
  public void onResume()
  {
    this.mPaused = false;
    onFocusChanged(ViewUtils.a(getContainerView()), true);
  }
  
  public void onRotationChanged(int paramInt)
  {
    if (this.mWebContents != null)
    {
      Object localObject = getSelectionPopupController();
      if ((Build.VERSION.SDK_INT >= 23) && (localObject != null) && (((SelectionPopupControllerImpl)localObject).isActionModeValid()))
      {
        hidePopupsAndPreserveSelection();
        ((SelectionPopupControllerImpl)localObject).showActionModeOrClearOnFailure();
      }
      localObject = getTextSuggestionHost();
      if (localObject != null) {
        ((TextSuggestionHost)localObject).hidePopups();
      }
    }
    switch (paramInt)
    {
    default: 
      throw new IllegalStateException("Display.getRotation() shouldn't return that value");
    case 3: 
      paramInt = -90;
      break;
    case 2: 
      paramInt = 180;
      break;
    case 1: 
      paramInt = 90;
      break;
    case 0: 
      paramInt = 0;
    }
    sendOrientationChangeEvent(paramInt);
  }
  
  public void onShow()
  {
    this.mWebContents.onShow();
    getWebContentsAccessibility().refreshState();
    getSelectionPopupController().restoreSelectionPopupsIfNecessary();
  }
  
  @TargetApi(19)
  public void onSystemCaptioningChanged(TextTrackSettings paramTextTrackSettings)
  {
    long l = this.mNativeContentViewCore;
    if (l == 0L) {
      return;
    }
    nativeSetTextTrackSettings(l, paramTextTrackSettings.getTextTracksEnabled(), paramTextTrackSettings.getTextTrackBackgroundColor(), paramTextTrackSettings.getTextTrackFontFamily(), paramTextTrackSettings.getTextTrackFontStyle(), paramTextTrackSettings.getTextTrackFontVariant(), paramTextTrackSettings.getTextTrackTextColor(), paramTextTrackSettings.getTextTrackTextShadow(), paramTextTrackSettings.getTextTrackTextSize());
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    if (!paramBoolean) {
      resetGestureDetection();
    }
    Iterator localIterator = this.mWindowEventObservers.iterator();
    while (localIterator.hasNext()) {
      ((WindowEventObserver)localIterator.next()).onWindowFocusChanged(paramBoolean);
    }
  }
  
  public void preserveSelectionOnNextLossOfFocus()
  {
    this.mPreserveSelectionOnNextLossOfFocus = true;
  }
  
  protected void removeDisplayAndroidObserver()
  {
    WindowAndroid localWindowAndroid = getWindowAndroid();
    if (localWindowAndroid != null) {
      localWindowAndroid.getDisplay().removeObserver(this);
    }
  }
  
  public void removeWindowAndroidChangedObserver(WindowAndroidChangedObserver paramWindowAndroidChangedObserver)
  {
    this.mWindowAndroidChangedObservers.removeObserver(paramWindowAndroidChangedObserver);
  }
  
  public void scrollBy(float paramFloat1, float paramFloat2)
  {
    if (this.mNativeContentViewCore == 0L) {
      return;
    }
    if ((paramFloat1 == 0.0F) && (paramFloat2 == 0.0F)) {
      return;
    }
    long l = SystemClock.uptimeMillis();
    if (getGestureListenerManager().hasPotentiallyActiveFling()) {
      getEventForwarder().onCancelFling(l);
    }
    nativeScrollBegin(this.mNativeContentViewCore, l, 0.0F, 0.0F, -paramFloat1, -paramFloat2, true, false);
    nativeScrollBy(this.mNativeContentViewCore, l, 0.0F, 0.0F, paramFloat1, paramFloat2);
    nativeScrollEnd(this.mNativeContentViewCore, l);
  }
  
  public void scrollTo(float paramFloat1, float paramFloat2)
  {
    if (this.mNativeContentViewCore == 0L) {
      return;
    }
    scrollBy(paramFloat1 - this.mRenderCoordinates.getScrollXPix(), paramFloat2 - this.mRenderCoordinates.getScrollYPix());
  }
  
  public void selectPopupMenuItems(int[] paramArrayOfInt)
  {
    long l = this.mNativeContentViewCore;
    if (l != 0L) {
      nativeSelectPopupMenuItems(l, this.mNativeSelectPopupSourceFrame, paramArrayOfInt);
    }
    this.mNativeSelectPopupSourceFrame = 0L;
    this.mSelectPopup = null;
  }
  
  @VisibleForTesting
  public void sendDoubleTapForTest(long paramLong, int paramInt1, int paramInt2)
  {
    long l = this.mNativeContentViewCore;
    if (l == 0L) {
      return;
    }
    nativeDoubleTap(l, paramLong, paramInt1, paramInt2);
  }
  
  public void setContainerView(ViewGroup paramViewGroup)
  {
    try
    {
      TraceEvent.begin("ContentViewCore.setContainerView");
      if (this.mContainerView != null)
      {
        hideSelectPopupWithCancelMessage();
        getImeAdapter().setContainerView(paramViewGroup);
        getTextSuggestionHost().setContainerView(paramViewGroup);
      }
      this.mContainerView = paramViewGroup;
      this.mContainerView.setClickable(true);
      getSelectionPopupController().setContainerView(paramViewGroup);
      return;
    }
    finally
    {
      TraceEvent.end("ContentViewCore.setContainerView");
    }
  }
  
  public void setContainerViewInternals(ContentViewCore.InternalAccessDelegate paramInternalAccessDelegate)
  {
    this.mContainerViewInternals = paramInternalAccessDelegate;
  }
  
  @VisibleForTesting
  void setWebContentsForTesting(WebContentsImpl paramWebContentsImpl)
  {
    this.mWebContents = paramWebContentsImpl;
  }
  
  @CalledByNative
  protected void showSelectPopup(View paramView, long paramLong, String[] paramArrayOfString, int[] paramArrayOfInt1, boolean paramBoolean1, int[] paramArrayOfInt2, boolean paramBoolean2) {}
  
  public void updateDoubleTapSupport(boolean paramBoolean)
  {
    long l = this.mNativeContentViewCore;
    if (l == 0L) {
      return;
    }
    nativeSetDoubleTapSupportEnabled(l, paramBoolean);
  }
  
  @CalledByNative
  protected void updateFrameInfo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, boolean paramBoolean1, boolean paramBoolean2)
  {
    TraceEvent.begin("ContentViewCore:updateFrameInfo");
    this.mIsMobileOptimizedHint = paramBoolean2;
    int i;
    if ((paramFloat6 == this.mRenderCoordinates.getContentWidthCss()) && (paramFloat7 == this.mRenderCoordinates.getContentHeightCss())) {
      i = 0;
    } else {
      i = 1;
    }
    int j;
    if ((paramFloat4 == this.mRenderCoordinates.getMinPageScaleFactor()) && (paramFloat5 == this.mRenderCoordinates.getMaxPageScaleFactor())) {
      j = 0;
    } else {
      j = 1;
    }
    int k;
    if (paramFloat3 != this.mRenderCoordinates.getPageScaleFactor()) {
      k = 1;
    } else {
      k = 0;
    }
    if ((k == 0) && (paramFloat1 == this.mRenderCoordinates.getScrollX()) && (paramFloat2 == this.mRenderCoordinates.getScrollY())) {
      k = 0;
    } else {
      k = 1;
    }
    if ((i != 0) || (k != 0)) {
      getTapDisambiguator().hidePopup(true);
    }
    if (k != 0) {
      this.mContainerViewInternals.onScrollChanged((int)this.mRenderCoordinates.fromLocalCssToPix(paramFloat1), (int)this.mRenderCoordinates.fromLocalCssToPix(paramFloat2), (int)this.mRenderCoordinates.getScrollXPix(), (int)this.mRenderCoordinates.getScrollYPix());
    }
    this.mRenderCoordinates.updateFrameInfo(paramFloat1, paramFloat2, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat3, paramFloat4, paramFloat5, paramFloat10);
    if ((k != 0) || (paramBoolean1)) {
      getGestureListenerManager().updateOnScrollChanged(computeVerticalScrollOffset(), computeVerticalScrollExtent());
    }
    if (j != 0) {
      getGestureListenerManager().updateOnScaleLimitsChanged(paramFloat4, paramFloat5);
    }
    TraceEvent.end("ContentViewCore:updateFrameInfo");
  }
  
  public void updateMultiTouchZoomSupport(boolean paramBoolean)
  {
    long l = this.mNativeContentViewCore;
    if (l == 0L) {
      return;
    }
    nativeSetMultiTouchZoomSupportEnabled(l, paramBoolean);
  }
  
  public void updateTextSelectionUI(boolean paramBoolean)
  {
    if (this.mWebContents == null) {
      return;
    }
    setTextHandlesTemporarilyHidden(paramBoolean ^ true);
    if (paramBoolean)
    {
      getSelectionPopupController().restoreSelectionPopupsIfNecessary();
      return;
    }
    hidePopupsAndPreserveSelection();
  }
  
  public void updateWindowAndroid(WindowAndroid paramWindowAndroid)
  {
    removeDisplayAndroidObserver();
    nativeUpdateWindowAndroid(this.mNativeContentViewCore, paramWindowAndroid);
    this.mSelectPopup = null;
    destroyPastePopup();
    addDisplayAndroidObserverIfNeeded();
    Iterator localIterator = this.mWindowAndroidChangedObservers.iterator();
    while (localIterator.hasNext()) {
      ((WindowAndroidChangedObserver)localIterator.next()).onWindowAndroidChanged(paramWindowAndroid);
    }
  }
  
  private class ContentGestureStateListener
    extends GestureStateListener
  {
    private ContentGestureStateListener() {}
    
    public void onFlingEndGesture(int paramInt1, int paramInt2)
    {
      ContentViewCoreImpl.this.setTouchScrollInProgress(false);
    }
    
    public void onFlingStartGesture(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      ContentViewCoreImpl.this.setTouchScrollInProgress(false);
    }
    
    public void onLongPress()
    {
      ContentViewCoreImpl.this.mContainerView.performHapticFeedback(0);
    }
    
    public void onScrollEnded(int paramInt1, int paramInt2)
    {
      ContentViewCoreImpl.this.setTouchScrollInProgress(false);
    }
    
    public void onScrollStarted(int paramInt1, int paramInt2)
    {
      ContentViewCoreImpl.this.setTouchScrollInProgress(true);
    }
    
    public void onScrollUpdateGestureConsumed()
    {
      ContentViewCoreImpl.this.destroyPastePopup();
    }
    
    public void onSingleTap(boolean paramBoolean)
    {
      ContentViewCoreImpl.this.destroyPastePopup();
    }
  }
  
  private static class ContentViewWebContentsObserver
    extends WebContentsObserver
  {
    private final WeakReference<ContentViewCoreImpl> b;
    
    ContentViewWebContentsObserver(ContentViewCoreImpl paramContentViewCoreImpl)
    {
      super();
      this.b = new WeakReference(paramContentViewCoreImpl);
    }
    
    private void a()
    {
      ContentViewCoreImpl localContentViewCoreImpl = (ContentViewCoreImpl)this.b.get();
      if (localContentViewCoreImpl == null) {
        return;
      }
      ContentViewCoreImpl.access$102(localContentViewCoreImpl, false);
      localContentViewCoreImpl.hidePopupsAndClearSelection();
      localContentViewCoreImpl.resetScrollInProgress();
    }
    
    public void didFinishNavigation(String paramString1, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, Integer paramInteger, int paramInt1, String paramString2, int paramInt2)
    {
      if ((paramBoolean3) && (paramBoolean1) && (!paramBoolean4)) {
        a();
      }
    }
    
    public void renderProcessGone(boolean paramBoolean)
    {
      a();
      ContentViewCoreImpl localContentViewCoreImpl = (ContentViewCoreImpl)this.b.get();
      if (localContentViewCoreImpl == null) {
        return;
      }
      localContentViewCoreImpl.getImeAdapter().resetAndHideKeyboard();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\ContentViewCoreImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */