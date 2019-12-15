package org.chromium.tencent;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.http.SslError;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import java.util.Map;
import org.chromium.android_webview.AwBrowserContext;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwContents.AwScrollOffsetManagerDelegate;
import org.chromium.android_webview.AwContents.AwViewMethodsImpl;
import org.chromium.android_webview.AwContents.DependencyFactory;
import org.chromium.android_webview.AwContents.ForceAuxiliaryBitmapRendering;
import org.chromium.android_webview.AwContents.HitTestData;
import org.chromium.android_webview.AwContents.InternalAccessDelegate;
import org.chromium.android_webview.AwContents.NativeDrawGLFunctorFactory;
import org.chromium.android_webview.AwContentsClient;
import org.chromium.android_webview.AwGLFunctor;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.android_webview.AwSettings;
import org.chromium.android_webview.OverScrollGlow;
import org.chromium.android_webview.permission.AwPermissionRequest;
import org.chromium.base.Callback;
import org.chromium.base.ThreadUtils;
import org.chromium.base.TraceEvent;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.components.navigation_interception.NavigationParams;
import org.chromium.content.browser.ContentViewStatics;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.content_public.browser.GestureListenerManager;
import org.chromium.content_public.browser.GestureStateListener;
import org.chromium.content_public.browser.LoadUrlParams;
import org.chromium.content_public.browser.NavigationController;
import org.chromium.content_public.browser.WebContents;
import org.chromium.content_public.common.UseZoomForDSFPolicy;
import org.chromium.tencent.android_webview.TencentAwScrollOffsetManager;
import org.chromium.tencent.base.TencentMemoryPressureListener;
import org.chromium.tencent.base.TencentMemoryPressureListener.MemoryTrimCallback;
import org.chromium.tencent.ui.gfx.DeviceDisplayInfo;
import org.chromium.tencent.utils.X5ApiCompatibilityUtils;
import org.chromium.tencent.widget.OverScrollBrand;
import org.chromium.tencent.x5pro.SurfaceRenderer;
import org.chromium.ui.base.EventForwarder;
import org.json.JSONObject;

@JNINamespace("android_webview")
public class TencentAwContent
  extends AwContents
{
  public static boolean ENABLE_SW_DRAW = false;
  private static int[] sTrimAwContentsOrder = { 16 };
  public static final String shouldReplaceCurrentEntryString = "should_replace_current_entry";
  private boolean isTouching = false;
  private float mBrowserControlsOffsetY = 0.0F;
  private AwContentsClientExtension mContentsClientExtension;
  float mCurPosY;
  private float mCurrVelocityX = 0.0F;
  private float mCurrVelocityY = 0.0F;
  private final double mDIPScale;
  private boolean mForceBitmapRenderTemp = false;
  protected OverScrollBrand mOverScrollBrand;
  float mPosY;
  private Bitmap mPossiblyStaleHitTestBitmap = null;
  private int mScreenHeight = 0;
  private int mScreenWidth = 0;
  final boolean mShouldReportNightMode = false;
  private SurfaceRenderer mSurfaceRenderer = null;
  private TencentAwBrowserContext mTencentBrowserContext;
  private ITencentAwContentsClient mTencentContentsClient;
  private TencentAwContentsIoThreadClient mTencentIoThreadClient;
  private int mTouchEventCount = 0;
  protected int mYOverScrollDistance;
  
  public TencentAwContent(AwBrowserContext paramAwBrowserContext, ViewGroup paramViewGroup, Context paramContext, AwContents.InternalAccessDelegate paramInternalAccessDelegate, AwContents.NativeDrawGLFunctorFactory paramNativeDrawGLFunctorFactory, AwContentsClient paramAwContentsClient, AwSettings paramAwSettings, AwContents.DependencyFactory paramDependencyFactory, TencentAwBrowserContext paramTencentAwBrowserContext, ITencentAwContentsClient paramITencentAwContentsClient)
  {
    super(paramAwBrowserContext, paramViewGroup, paramContext, paramInternalAccessDelegate, paramNativeDrawGLFunctorFactory, paramAwContentsClient, paramAwSettings, paramDependencyFactory);
    this.mTencentBrowserContext = paramTencentAwBrowserContext;
    this.mTencentContentsClient = paramITencentAwContentsClient;
    this.mScreenWidth = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getWidth();
    this.mScreenHeight = ((WindowManager)this.mContext.getSystemService("window")).getDefaultDisplay().getHeight();
    this.mDIPScale = DeviceDisplayInfo.create(this.mContext).getDIPScale();
    GestureListenerManager.fromWebContents(this.mContentViewCore.getWebContents()).addListener(new TencentAwGestureStateListener(null));
    paramAwBrowserContext = new ImeAdapterListener(this);
    getTencentContentViewCore().setImeAdapterListener(paramAwBrowserContext);
    createSurfaceRendererIfNeeded();
  }
  
  public TencentAwContent(AwBrowserContext paramAwBrowserContext, ViewGroup paramViewGroup, Context paramContext, AwContents.InternalAccessDelegate paramInternalAccessDelegate, AwContents.NativeDrawGLFunctorFactory paramNativeDrawGLFunctorFactory, AwContentsClient paramAwContentsClient, AwSettings paramAwSettings, TencentAwBrowserContext paramTencentAwBrowserContext, ITencentAwContentsClient paramITencentAwContentsClient)
  {
    this(paramAwBrowserContext, paramViewGroup, paramContext, paramInternalAccessDelegate, paramNativeDrawGLFunctorFactory, paramAwContentsClient, paramAwSettings, new AwContents.DependencyFactory(), paramTencentAwBrowserContext, paramITencentAwContentsClient);
  }
  
  private boolean createSurfaceRendererIfNeeded()
  {
    this.mSurfaceRenderer = SurfaceRenderer.create(this.mContainerView, this.mContext, this);
    if (this.mSurfaceRenderer == null) {
      return false;
    }
    nativeSetSurfaceRenderer(this.mNativeAwContents, this.mSurfaceRenderer.getNativeSurfaceRenderer());
    setRenderToNativeSurface(true);
    return true;
  }
  
  private void drawOverScrollBrand(Canvas paramCanvas)
  {
    int i = paramCanvas.save();
    OverScrollBrand localOverScrollBrand = this.mOverScrollBrand;
    if (localOverScrollBrand != null) {
      localOverScrollBrand.drawBrand(paramCanvas);
    }
    paramCanvas.restoreToCount(i);
  }
  
  private TencentAwAutofillClient getTencentAwAutofillClient()
  {
    if ((this.mAwAutofillClient != null) && ((this.mAwAutofillClient instanceof TencentAwAutofillClient))) {
      return (TencentAwAutofillClient)this.mAwAutofillClient;
    }
    return null;
  }
  
  private native void nativeSetDrawParam(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  private native void nativeSetSurfaceRenderer(long paramLong1, long paramLong2);
  
  @CalledByNative
  private void notifyFirstScreenParameter(long paramLong1, long paramLong2, long paramLong3, long paramLong4)
  {
    this.mContentsClientExtension.notifyFirstScreenParameter(paramLong1, paramLong2, paramLong3, paramLong4);
  }
  
  @CalledByNative
  private void notifyFirstScreenTime(long paramLong1, long paramLong2)
  {
    this.mContentsClientExtension.notifyFirstScreenTime(paramLong1, paramLong2);
  }
  
  @CalledByNative
  private void notifyScreenStatus(int paramInt)
  {
    this.mContentsClientExtension.notifyScreenStatus(paramInt);
  }
  
  @CalledByNative
  private void onTopControlsShownHeight(float paramFloat)
  {
    if ((this.mContentsClientExtension != null) && (this.mContentViewCore.isScrollInProgress()) && (this.isTouching)) {
      this.mContentsClientExtension.onTopControlsChanged(paramFloat);
    }
    this.mBrowserControlsOffsetY = paramFloat;
  }
  
  private void recordUrlAndScale()
  {
    ThreadUtils.postOnUiThreadDelayed(new Runnable()
    {
      public void run()
      {
        if (TencentAwContent.this.mContentViewCore == null) {
          return;
        }
        if (((TencentContentViewCore)TencentAwContent.this.mContentViewCore).getMinPageScaleFactor() < ((TencentContentViewCore)TencentAwContent.this.mContentViewCore).getMaxPageScaleFactor()) {
          TencentAwContent.this.mTencentContentsClient.recordUrlAndScale(TencentAwContent.this.getUrl(), ((TencentContentViewCore)TencentAwContent.this.mContentViewCore).getPageScaleFactor());
        }
      }
    }, 500L);
  }
  
  @CalledByNative
  private void setBackgroundAtNight(final boolean paramBoolean)
  {
    ThreadUtils.runOnUiThread(new Runnable()
    {
      public void run()
      {
        if (paramBoolean)
        {
          TencentAwContent.this.setBackgroundColor(-14671065);
          return;
        }
        TencentAwContent.this.setBackgroundColor(-1);
      }
    });
  }
  
  @CalledByNative
  private void updateDrawParam()
  {
    this.mScrollOffsetManager.syncScrollOffsetFromOnDraw();
    int i = this.mContainerView.getScrollX();
    int j = this.mContainerView.getScrollY();
    Rect localRect = getGlobalVisibleRect();
    nativeSetDrawParam(this.mNativeAwContents, i, j, localRect.left, localRect.top, localRect.right, localRect.bottom);
  }
  
  @CalledByNative
  private void updateHitTestDataExtra(boolean paramBoolean1, int paramInt1, int paramInt2, boolean paramBoolean2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    this.mPossiblyStaleHitTestData.isPassword = paramBoolean1;
    this.mPossiblyStaleHitTestData.imgWidth = paramInt1;
    this.mPossiblyStaleHitTestData.imgHeight = paramInt2;
    this.mPossiblyStaleHitTestData.canBeHidden = paramBoolean2;
    this.mPossiblyStaleHitTestData.hitNodeX = paramInt3;
    this.mPossiblyStaleHitTestData.hitNodeY = paramInt4;
    this.mPossiblyStaleHitTestData.hitNodeWidth = paramInt5;
    this.mPossiblyStaleHitTestData.hitNodeHeight = paramInt6;
  }
  
  public void adjustAutofillPositionOnScrollChanged()
  {
    TencentAwAutofillClient localTencentAwAutofillClient = getTencentAwAutofillClient();
    if (localTencentAwAutofillClient != null) {
      localTencentAwAutofillClient.adjustAutofillPositionOnScrollChanged();
    }
  }
  
  public void destroy()
  {
    super.destroy();
    if (isRenderToNativeSurface())
    {
      this.mSurfaceRenderer.destroy();
      this.mSurfaceRenderer = null;
    }
  }
  
  public double dipScale()
  {
    return this.mDIPScale;
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (isRenderToNativeSurface()) {
      drawOverScrollBrand(paramCanvas);
    }
  }
  
  public void drawToSurfaceView(Canvas paramCanvas)
  {
    if (isDestroyedOrNoOperation(0))
    {
      TraceEvent.instant("EarlyOut_destroyed");
      paramCanvas.drawColor(getEffectiveBackgroundColor());
      return;
    }
    Rect localRect = getGlobalVisibleRect();
    if (!nativeOnDraw(this.mNativeAwContents, paramCanvas, false, 0, 0, localRect.left, localRect.top, localRect.right, localRect.bottom, false))
    {
      TraceEvent.instant("NativeDrawFailed");
      paramCanvas.drawColor(getEffectiveBackgroundColor());
    }
    if ((this.mOverScrollGlow != null) && (this.mOverScrollGlow.drawEdgeGlows(paramCanvas, this.mScrollOffsetManager.computeMaximumHorizontalScrollOffset(), this.mScrollOffsetManager.computeMaximumVerticalScrollOffset()))) {
      postInvalidateOnAnimation();
    }
    if (this.mInvalidateRootViewOnNextDraw)
    {
      this.mContainerView.getRootView().invalidate();
      this.mInvalidateRootViewOnNextDraw = false;
    }
  }
  
  public void focusTtsNode(int paramInt, boolean paramBoolean)
  {
    if (getTencentContentViewCore() == null) {
      return;
    }
    getTencentContentViewCore().focusTtsNode(paramInt, paramBoolean);
  }
  
  public float getCurrScrollVelocity()
  {
    float f1 = this.mCurrVelocityX;
    float f2 = this.mCurrVelocityY;
    return (float)Math.sqrt(f1 * f1 + f2 * f2);
  }
  
  public int getCurrentEntryIndex()
  {
    if (isDestroyedOrNoOperation(1)) {
      return -1;
    }
    return this.mNavigationController.getCurrentEntryIndex();
  }
  
  public float getCurrentPageMaxVerticalScrollRatio()
  {
    if (isDestroyedOrNoOperation(1)) {
      return 0.0F;
    }
    return nativeGetCurrentPageMaxVerticalScrollRatio(this.mNativeAwContents);
  }
  
  public float getCurrentPageNumber()
  {
    if (isDestroyedOrNoOperation(1)) {
      return 0.0F;
    }
    return nativeGetCurrentPageNumber(this.mNativeAwContents);
  }
  
  public Bitmap getLastHitTestResultBitmap()
  {
    if (this.mNativeAwContents != 0L) {
      nativeUpdateLastHitTestBitmap(this.mNativeAwContents);
    }
    return this.mPossiblyStaleHitTestBitmap;
  }
  
  public TencentHitTestData getLastTencentHitTestResult()
  {
    AwContents.HitTestData localHitTestData = super.getLastHitTestResult();
    if ((localHitTestData != null) && ((localHitTestData instanceof TencentHitTestData))) {
      return (TencentHitTestData)localHitTestData;
    }
    return null;
  }
  
  public AwMediaAccessPermissions getMediaAccessPermissions()
  {
    return this.mTencentBrowserContext.getMediaAccessPermissions();
  }
  
  protected TencentAwContentsIoThreadClient getTencentAwContentsIoThreadClient()
  {
    return this.mTencentIoThreadClient;
  }
  
  @VisibleForTesting
  public TencentContentViewCore getTencentContentViewCore()
  {
    return (TencentContentViewCore)this.mContentViewCore;
  }
  
  public TencentAwSettings getTencentSettings()
  {
    return (TencentAwSettings)this.mSettings;
  }
  
  public int getTouchEventCount()
  {
    return this.mTouchEventCount;
  }
  
  public void getTtsTextAsync(int paramInt)
  {
    if (getTencentContentViewCore() == null) {
      return;
    }
    getTencentContentViewCore().getTtsTextAsync(paramInt);
  }
  
  public void goBack()
  {
    hideAutofillPopup();
    super.goBack();
  }
  
  public void goBackOrForward(int paramInt)
  {
    hideAutofillPopup();
    super.goBackOrForward(paramInt);
  }
  
  public void goForward()
  {
    hideAutofillPopup();
    super.goForward();
  }
  
  public boolean grantFileSchemeAccesstoChildProcess()
  {
    if (isDestroyedOrNoOperation(1)) {
      return false;
    }
    nativeGrantFileSchemeAccesstoChildProcess(this.mNativeAwContents);
    return true;
  }
  
  public void hideSelectedAdElement(String paramString)
  {
    if (this.mNativeAwContents == 0L) {
      return;
    }
    double d1 = this.mPossiblyStaleHitTestData.hitTestPoint.x;
    double d2 = this.mDIPScale;
    Double.isNaN(d1);
    int i = (int)Math.round(d1 / d2);
    d1 = this.mPossiblyStaleHitTestData.hitTestPoint.y;
    d2 = this.mDIPScale;
    Double.isNaN(d1);
    int j = (int)Math.round(d1 / d2);
    nativeHideSelectedAdElement(this.mNativeAwContents, i, j, paramString);
  }
  
  protected AwContents.AwScrollOffsetManagerDelegate initAwScrollOffsetManagerDelegate()
  {
    return new TencentAwScrollOffsetManagerDelegate();
  }
  
  protected AwContents.AwViewMethodsImpl initAwViewMethodsImpl()
  {
    return new TencentAwViewMethodsImpl();
  }
  
  protected void initTencentAwContentsIoThreadClient(Context paramContext, AwContentsClient paramAwContentsClient)
  {
    this.mTencentIoThreadClient = new TencentAwContentsIoThreadClientImpl(this.mContext, paramAwContentsClient);
  }
  
  public boolean isOverScrollMode()
  {
    OverScrollBrand localOverScrollBrand = this.mOverScrollBrand;
    return (localOverScrollBrand != null) && (localOverScrollBrand.isOverScrollMode());
  }
  
  public boolean isRenderToNativeSurface()
  {
    return this.mSurfaceRenderer != null;
  }
  
  public boolean isShowOnScreen()
  {
    if ((this.mIsAttachedToWindow) && (this.mIsViewVisible) && ((!this.mIsAttachedToWindow) || (this.mIsWindowVisible)) && (this.mContentViewCore.getViewportWidthPix() > 0) && (this.mContentViewCore.getViewportHeightPix() > 0))
    {
      Object localObject = new int[2];
      this.mContainerView.getLocationOnScreen((int[])localObject);
      localObject = new Rect(localObject[0], localObject[1], localObject[0] + this.mContentViewCore.getViewportWidthPix() - 1, localObject[1] + this.mContentViewCore.getViewportHeightPix() - 1);
      if (Rect.intersects(new Rect(0, 0, this.mScreenWidth, this.mScreenHeight), (Rect)localObject)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isTouching()
  {
    return this.isTouching;
  }
  
  public boolean isWebviewDestroyed()
  {
    return super.isDestroyed(0);
  }
  
  public void loadUrl(LoadUrlParams paramLoadUrlParams)
  {
    Map localMap = paramLoadUrlParams.a();
    if ((localMap != null) && (localMap.containsKey("should_replace_current_entry")))
    {
      paramLoadUrlParams.b(true);
      localMap.remove("should_replace_current_entry");
    }
    super.loadUrl(paramLoadUrlParams);
  }
  
  public void logShowOnScreen(String paramString)
  {
    paramString = new int[2];
    Rect localRect = new Rect();
    this.mContainerView.getHitRect(localRect);
    localRect = new Rect();
    this.mContainerView.getLocalVisibleRect(localRect);
    localRect = new Rect();
    this.mContainerView.getLocalVisibleRect(localRect);
    this.mContainerView.getLocationOnScreen(paramString);
  }
  
  protected void notifyRenderType(boolean paramBoolean)
  {
    if (!AwContents.ForceAuxiliaryBitmapRendering.sResult)
    {
      if (ENABLE_SW_DRAW) {
        return;
      }
      this.mForceBitmapRenderTemp = paramBoolean;
      return;
    }
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    super.onConfigurationChanged(paramConfiguration);
    TencentAwAutofillClient localTencentAwAutofillClient = getTencentAwAutofillClient();
    if (localTencentAwAutofillClient != null) {
      localTencentAwAutofillClient.onConfigurationChanged(paramConfiguration);
    }
  }
  
  public void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    hideAutofillPopup();
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
  }
  
  public void onPause()
  {
    hideAutofillPopup();
    this.mContentsClientExtension.onPause();
    super.onPause();
  }
  
  protected void onPermissionRequest(AwPermissionRequest paramAwPermissionRequest)
  {
    this.mContentsClientExtension.onPermissionRequest(paramAwPermissionRequest);
  }
  
  public void onReceivedSslError(Callback<Boolean> paramCallback, SslError paramSslError, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mTencentContentsClient.onReceivedSslError(paramCallback, paramSslError, paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2);
  }
  
  public void onReportFramePerformanceInfo(JSONObject paramJSONObject)
  {
    this.mTencentContentsClient.onReportFramePerformanceInfo(paramJSONObject);
  }
  
  public void onReportJSPerformanceInfo(JSONObject paramJSONObject)
  {
    this.mTencentContentsClient.onReportJSPerformanceInfo(paramJSONObject);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default: 
      break;
    case 2: 
      this.mCurPosY = paramMotionEvent.getY();
      break;
    case 1: 
    case 3: 
      if (Math.abs(this.mCurPosY - this.mPosY) > 50.0F) {
        this.mTouchEventCount += 1;
      }
      this.isTouching = false;
      break;
    case 0: 
      this.isTouching = true;
      float f = paramMotionEvent.getY();
      this.mPosY = f;
      this.mCurPosY = f;
    }
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void onUpdateHTMLElementAssoicateNativePanel(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    this.mTencentContentsClient.onUpdateHTMLElementAssoicateNativePanel(paramInt1, paramString, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  public void onVisibilityChanged(View paramView, int paramInt)
  {
    this.mContentsClientExtension.onVisibilityChanged(paramView, paramInt);
    super.onVisibilityChanged(paramView, paramInt);
  }
  
  public void onWindowVisibilityChanged(int paramInt)
  {
    this.mContentsClientExtension.onWindowVisibilityChanged(paramInt);
    super.onWindowVisibilityChanged(paramInt);
  }
  
  public boolean pageDown(boolean paramBoolean)
  {
    hideAutofillPopup();
    return super.pageDown(paramBoolean);
  }
  
  public boolean pageUp(boolean paramBoolean)
  {
    hideAutofillPopup();
    return super.pageUp(paramBoolean);
  }
  
  public void pauseTimers()
  {
    super.pauseTimers();
    TencentContentViewCore.setWebKitSharedTimersSuspended(true);
  }
  
  protected void receivePopupContents(long paramLong)
  {
    super.receivePopupContents(paramLong);
    this.mContentsClientExtension.restoreAddressBarDisplayMode();
    this.mContentsClient.onReceivedTitle(getTitle());
  }
  
  public void resetTouchEventCount()
  {
    this.mTouchEventCount = 0;
  }
  
  public void resumeTimers()
  {
    super.resumeTimers();
    if ((isDestroyedOrNoOperation(1)) && (TencentContentViewCore.isWebKitSharedTimersSuspended())) {
      ContentViewStatics.setWebKitSharedTimersSuspended(false);
    }
    TencentContentViewCore.setWebKitSharedTimersSuspended(false);
  }
  
  public void scrollBy(int paramInt1, int paramInt2)
  {
    didOverscroll(paramInt1, paramInt2, 0.0F, 0.0F);
  }
  
  public void setAwContentsClientExtension(AwContentsClientExtension paramAwContentsClientExtension)
  {
    this.mContentsClientExtension = paramAwContentsClientExtension;
    if ((this.mContentsClientBridge instanceof TencentAwContentsClientBridge)) {
      ((TencentAwContentsClientBridge)this.mContentsClientBridge).setAwContentsClientExtension(paramAwContentsClientExtension);
    }
    this.mTencentIoThreadClient.setAwContentsClientExtension(paramAwContentsClientExtension);
    this.mTencentIoThreadClient.setTencentAwSettings((TencentAwSettings)this.mSettings);
    if ((this.mWebContentsDelegate instanceof TencentAwWebContentsDelegateAdapter)) {
      ((TencentAwWebContentsDelegateAdapter)this.mWebContentsDelegate).setAwContentsClientExtension(paramAwContentsClientExtension);
    }
  }
  
  public void setBackgroundColor(int paramInt)
  {
    super.setBackgroundColor(paramInt);
    if (isRenderToNativeSurface())
    {
      int i;
      if ((paramInt & 0xFF000000) == -16777216) {
        i = 1;
      } else {
        i = 0;
      }
      if (i == 0)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("setBackgroundColor:");
        localStringBuilder.append(Integer.toHexString(paramInt));
        SurfaceRenderer.reportX5ProInfo(1002, localStringBuilder.toString());
        setRenderToNativeSurface(false);
        return;
      }
      this.mSurfaceRenderer.setBackgroundColor(paramInt);
    }
  }
  
  protected void setContainerView(ViewGroup paramViewGroup, AwGLFunctor paramAwGLFunctor)
  {
    super.setContainerView(paramViewGroup, paramAwGLFunctor);
    paramAwGLFunctor = this.mSurfaceRenderer;
    if (paramAwGLFunctor != null) {
      paramAwGLFunctor.setContainerView(paramViewGroup);
    }
  }
  
  public void setOverScrollMode(int paramInt)
  {
    this.mOverScrollBrand = new OverScrollBrand(this, this.mContainerView, this.mContext);
    if ((this.mOverScrollBrand != null) && (this.mScrollOffsetManager != null) && ((this.mScrollOffsetManager instanceof TencentAwScrollOffsetManager)))
    {
      ((TencentAwScrollOffsetManager)this.mScrollOffsetManager).bindOverScrollBrand(this.mOverScrollBrand);
      this.mOverScrollBrand.bindScrollOffsetManager(this.mScrollOffsetManager);
    }
  }
  
  public void setOverScrollParams(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, Drawable paramDrawable1, Drawable paramDrawable2, Drawable paramDrawable3)
  {
    this.mYOverScrollDistance = paramInt5;
    OverScrollBrand localOverScrollBrand = this.mOverScrollBrand;
    if (localOverScrollBrand != null) {
      localOverScrollBrand.setOverScrollParams(paramDrawable1, paramDrawable2, paramDrawable3);
    }
  }
  
  public void setRenderToNativeSurface(boolean paramBoolean)
  {
    getTencentSettings().setUseSurfaceView(paramBoolean);
    this.mSurfaceRenderer.setRenderToNativeSurface(paramBoolean);
    if (!paramBoolean)
    {
      this.mSurfaceRenderer.destroy();
      this.mSurfaceRenderer = null;
    }
  }
  
  public void setSiteSecurityInfo(String paramString1, String paramString2)
  {
    if (getTencentContentViewCore() == null) {
      return;
    }
    getTencentContentViewCore().setSiteSecurityInfo(paramString1, paramString2);
  }
  
  public void shouldIgnoreNavigationImpl(NavigationParams paramNavigationParams, boolean paramBoolean)
  {
    paramNavigationParams = paramNavigationParams.a;
    if (!paramBoolean)
    {
      AwContentsClientExtension localAwContentsClientExtension = this.mContentsClientExtension;
      if (localAwContentsClientExtension != null) {
        localAwContentsClientExtension.onPageStarted(paramNavigationParams, false);
      }
    }
  }
  
  public void suspendPageScroll(boolean paramBoolean) {}
  
  protected void updateFlingVelocity(float paramFloat1, float paramFloat2)
  {
    this.mCurrVelocityX = paramFloat1;
    this.mCurrVelocityY = paramFloat2;
  }
  
  protected void updateHitTestBitmap(Bitmap paramBitmap)
  {
    this.mPossiblyStaleHitTestBitmap = paramBitmap;
  }
  
  public void updateHitTestEditable(String paramString)
  {
    getLastHitTestResult();
    if ((this.mPossiblyStaleHitTestData != null) && (this.mPossiblyStaleHitTestData.hitTestResultType == 9) && ((!TextUtils.isEmpty(this.mPossiblyStaleHitTestData.hitTestResultExtraData)) || (!TextUtils.isEmpty(paramString)))) {
      this.mPossiblyStaleHitTestData.hitTestResultExtraData = paramString;
    }
  }
  
  protected void updateScrollState(int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3)
  {
    int i;
    if ((this.mContentWidthDip == paramInt3) && (this.mContentHeightDip == paramInt4)) {
      i = 0;
    } else {
      i = 1;
    }
    super.updateScrollState(paramInt1, paramInt2, paramInt3, paramInt4, paramFloat1, paramFloat2, paramFloat3);
    if (i != 0) {
      this.mTencentContentsClient.onContentsSizeChanged(paramInt3, paramInt4);
    }
  }
  
  private static class AwContentsTrimCallback
    extends TencentMemoryPressureListener.MemoryTrimCallback
  {
    public void onLowMemory(Context paramContext) {}
    
    public void onTrimMemory(Context paramContext, int paramInt)
    {
      TencentMemoryPressureListener.trimInOrder(paramInt, TencentAwContent.sTrimAwContentsOrder);
    }
  }
  
  private class TencentAwGestureStateListener
    extends GestureStateListener
  {
    private TencentAwGestureStateListener() {}
    
    public void onDoubleTapEventAck()
    {
      TencentAwContent.this.recordUrlAndScale();
    }
    
    public void onFlingEndGesture(int paramInt1, int paramInt2)
    {
      if (TencentAwContent.this.mContentsClientExtension != null)
      {
        TencentAwContent.this.mContentsClientExtension.onTopControlsChanged(TencentAwContent.this.mBrowserControlsOffsetY);
        TencentAwContent.this.mContentsClientExtension.onScrollEnded();
      }
    }
    
    public void onFlingStartGesture(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      TencentAwContent.this.mContentsClientExtension.onFlingScrollBegin(AwScrollOffsetManager.computeDurationInMilliSec(paramInt1, paramInt2), paramInt1, paramInt2);
    }
    
    public void onPinchEnded()
    {
      float f1 = TencentAwContent.this.getTencentContentViewCore().getMinPageScaleFactor();
      float f2 = TencentAwContent.this.getTencentContentViewCore().getMaxPageScaleFactor();
      TencentAwContent.this.mContentsClientExtension.onPinchEndEvent(f1, f2);
      if (f1 < f2) {
        TencentAwContent.this.mTencentContentsClient.recordUrlAndScale(TencentAwContent.this.getUrl(), ((TencentContentViewCore)TencentAwContent.this.mContentViewCore).getPageScaleFactor());
      }
    }
    
    public void onPinchStarted()
    {
      TencentAwContent.this.mContentsClientExtension.onPinchBeginEvent();
    }
    
    public void onScrollEnded(int paramInt1, int paramInt2)
    {
      if (TencentAwContent.this.mContentsClientExtension != null)
      {
        TencentAwContent.this.mContentsClientExtension.onTopControlsChanged(TencentAwContent.this.mBrowserControlsOffsetY);
        TencentAwContent.this.mContentsClientExtension.onScrollEnded();
      }
    }
  }
  
  public class TencentAwScrollOffsetManagerDelegate
    extends AwContents.AwScrollOffsetManagerDelegate
  {
    public TencentAwScrollOffsetManagerDelegate()
    {
      super();
    }
    
    public void overScrollContainerViewBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean)
    {
      TencentAwContent.this.mInternalAccessAdapter.overScrollBy(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, 0, TencentAwContent.this.mYOverScrollDistance, paramBoolean);
    }
  }
  
  public class TencentAwViewMethodsImpl
    extends AwContents.AwViewMethodsImpl
  {
    public TencentAwViewMethodsImpl()
    {
      super();
    }
    
    public void computeScroll()
    {
      if (TencentAwContent.this.isDestroyedOrNoOperation(0)) {
        return;
      }
      if ((TencentAwContent.this.mOverScrollBrand != null) && (TencentAwContent.this.mOverScrollBrand.isOverScrollMode()))
      {
        TencentAwContent.this.mOverScrollBrand.computeScroll();
        return;
      }
      TencentAwContent localTencentAwContent = TencentAwContent.this;
      localTencentAwContent.nativeOnComputeScroll(localTencentAwContent.mNativeAwContents, AnimationUtils.currentAnimationTimeMillis());
    }
    
    public void onContainerViewOverScrolled(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2)
    {
      super.onContainerViewOverScrolled(paramInt1, paramInt2, paramBoolean1, paramBoolean2);
      if ((TencentAwContent.this.mOverScrollBrand != null) && (TencentAwContent.this.mOverScrollBrand.canShouldOverScroll(paramInt1, paramInt2))) {
        TencentAwContent.this.mOverScrollBrand.setOverScrollMode(true);
      }
    }
    
    public void onDraw(Canvas paramCanvas)
    {
      if (TencentAwContent.this.isRenderToNativeSurface())
      {
        TencentAwContent.this.mSurfaceRenderer.setTranslationX(TencentAwContent.this.mContainerView.getScrollX());
        TencentAwContent.this.mSurfaceRenderer.setTranslationY(Math.max(0, TencentAwContent.this.mContainerView.getScrollY()));
        return;
      }
      if (TencentAwContent.this.isDestroyedOrNoOperation(0))
      {
        TraceEvent.instant("EarlyOut_destroyed");
        paramCanvas.drawColor(TencentAwContent.this.getEffectiveBackgroundColor());
        return;
      }
      if ((!X5ApiCompatibilityUtils.invokeCanvasIsHardwareAccelerated(paramCanvas)) && (!paramCanvas.getClipBounds(this.mClipBoundsTemporary)))
      {
        TraceEvent.instant("EarlyOut_software_empty_clip");
        return;
      }
      int k = paramCanvas.save();
      if ((TencentAwContent.this.mOverScrollBrand != null) && (!TencentAwContent.this.isRenderToNativeSurface())) {
        TencentAwContent.this.mOverScrollBrand.drawBrand(paramCanvas);
      }
      if ((Build.VERSION.SDK_INT < 21) && (paramCanvas.isHardwareAccelerated()) && (!AwContents.ForceAuxiliaryBitmapRendering.sResult) && (!TencentAwContent.ENABLE_SW_DRAW) && (!TencentAwContent.this.mForceBitmapRenderTemp))
      {
        TencentAwContent.this.mCurrentFunctor.requestDrawGL(paramCanvas);
        paramCanvas.restoreToCount(k);
      }
      else
      {
        TencentAwContent.this.mScrollOffsetManager.syncScrollOffsetFromOnDraw();
        int i = TencentAwContent.this.mContainerView.getScrollX();
        int j = TencentAwContent.this.mContainerView.getScrollY();
        Rect localRect = TencentAwContent.this.getGlobalVisibleRect();
        if ((Build.VERSION.SDK_INT == 24) || (Build.VERSION.SDK_INT == 25))
        {
          if (TencentAwContent.this.mPaintForNWorkaround == null)
          {
            TencentAwContent.access$902(TencentAwContent.this, new Paint());
            TencentAwContent.this.mPaintForNWorkaround.setColor(Color.argb(1, 0, 0, 0));
            localObject = new ColorMatrix();
            ((ColorMatrix)localObject).setScale(0.0F, 0.0F, 0.0F, 0.1F);
            TencentAwContent.this.mPaintForNWorkaround.setColorFilter(new ColorMatrixColorFilter((ColorMatrix)localObject));
          }
          paramCanvas.drawRect(0.0F, 0.0F, 1.0F, 1.0F, TencentAwContent.this.mPaintForNWorkaround);
        }
        Object localObject = TencentAwContent.this;
        long l = ((TencentAwContent)localObject).mNativeAwContents;
        boolean bool2 = paramCanvas.isHardwareAccelerated();
        int m = localRect.left;
        int n = localRect.top;
        int i1 = localRect.right;
        int i2 = localRect.bottom;
        if ((!AwContents.ForceAuxiliaryBitmapRendering.sResult) && (!TencentAwContent.ENABLE_SW_DRAW) && (!TencentAwContent.this.mForceBitmapRenderTemp)) {
          bool1 = false;
        } else {
          bool1 = true;
        }
        bool2 = ((TencentAwContent)localObject).nativeOnDraw(l, paramCanvas, bool2, i, j, m, n, i1, i2, bool1);
        boolean bool1 = bool2;
        if (bool2)
        {
          bool1 = bool2;
          if (paramCanvas.isHardwareAccelerated())
          {
            bool1 = bool2;
            if (!AwContents.ForceAuxiliaryBitmapRendering.sResult)
            {
              bool1 = bool2;
              if (!TencentAwContent.ENABLE_SW_DRAW)
              {
                bool1 = bool2;
                if (!TencentAwContent.this.mForceBitmapRenderTemp) {
                  bool1 = TencentAwContent.this.mCurrentFunctor.requestDrawGL(paramCanvas);
                }
              }
            }
          }
        }
        paramCanvas.restoreToCount(k);
        if (bool1)
        {
          k = TencentAwContent.this.mContainerView.getScrollX();
          m = TencentAwContent.this.mContainerView.getScrollY();
          paramCanvas.translate(-(k - i), -(m - j));
        }
        else if (!TencentAwContent.this.isRenderToNativeSurface())
        {
          TraceEvent.instant("NativeDrawFailed");
          paramCanvas.drawColor(TencentAwContent.this.getEffectiveBackgroundColor());
        }
      }
      if ((TencentAwContent.this.mOverScrollGlow != null) && (TencentAwContent.this.mOverScrollGlow.drawEdgeGlows(paramCanvas, TencentAwContent.this.mScrollOffsetManager.computeMaximumHorizontalScrollOffset(), TencentAwContent.this.mScrollOffsetManager.computeMaximumVerticalScrollOffset()))) {
        TencentAwContent.this.postInvalidateOnAnimation();
      }
      if (TencentAwContent.this.mInvalidateRootViewOnNextDraw)
      {
        TencentAwContent.this.mContainerView.getRootView().invalidate();
        TencentAwContent.access$2202(TencentAwContent.this, false);
      }
    }
    
    public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      if (TencentAwContent.this.isRenderToNativeSurface()) {
        TencentAwContent.this.mSurfaceRenderer.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
      }
      if ((TencentAwContent.this.mContentsClientExtension != null) && (TencentAwContent.this.mContentsClientExtension.needReSizeForDisplayCut(paramInt1, paramInt2, paramInt3, paramInt4))) {
        return;
      }
      super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      if (TencentAwContent.this.isDestroyedOrNoOperation(0)) {
        return false;
      }
      if (paramMotionEvent.getActionMasked() == 0) {
        TencentAwContent.this.mSettings.setSpatialNavigationEnabled(false);
      }
      boolean bool;
      if ((TencentAwContent.this.mOverScrollBrand != null) && (((TencentAwSettings)TencentAwContent.this.mSettings).getOverScrollState()) && (TencentAwContent.this.mOverScrollBrand.OverScrollTouchHandler(paramMotionEvent)))
      {
        bool = true;
      }
      else
      {
        TencentAwContent.this.mScrollOffsetManager.setProcessingTouchEvent(true);
        bool = TencentAwContent.this.mWebContents.getEventForwarder().a(paramMotionEvent);
        TencentAwContent.this.mScrollOffsetManager.setProcessingTouchEvent(false);
      }
      if (paramMotionEvent.getActionMasked() == 0)
      {
        int i = paramMotionEvent.getActionIndex();
        Object localObject = TencentAwContent.this.mContentViewCore.getWebContents().getEventForwarder().a(paramMotionEvent);
        TencentAwContent.this.mPossiblyStaleHitTestData.hitTestPoint = new Point(Math.round(((MotionEvent)localObject).getX(i)), Math.round(((MotionEvent)localObject).getY(i)));
        float f1 = ((MotionEvent)localObject).getX();
        float f2 = ((MotionEvent)localObject).getY();
        float f3 = Math.max(paramMotionEvent.getTouchMajor(), paramMotionEvent.getTouchMinor());
        if (!UseZoomForDSFPolicy.a())
        {
          float f4 = TencentAwContent.this.getDeviceScaleFactor();
          f1 /= f4;
          f2 /= f4;
          f3 /= f4;
        }
        localObject = TencentAwContent.this;
        ((TencentAwContent)localObject).nativeRequestNewHitTestDataAt(((TencentAwContent)localObject).mNativeAwContents, f1, f2, f3);
      }
      if (TencentAwContent.this.mOverScrollGlow != null) {
        if (paramMotionEvent.getActionMasked() == 0)
        {
          TencentAwContent.this.mOverScrollGlow.setShouldPull(true);
        }
        else if ((paramMotionEvent.getActionMasked() == 1) || (paramMotionEvent.getActionMasked() == 3))
        {
          TencentAwContent.this.mOverScrollGlow.setShouldPull(false);
          TencentAwContent.this.mOverScrollGlow.releaseAll();
        }
      }
      if ((TencentAwContent.this.mOverScrollBrand != null) && (TencentAwContent.this.mOverScrollBrand.isOverScrollMode()) && ((paramMotionEvent.getActionMasked() == 1) || (paramMotionEvent.getActionMasked() == 3)))
      {
        TencentAwContent.this.mOverScrollBrand.setOverScrollMode(false);
        TencentAwContent.this.mOverScrollBrand.releaseAll();
      }
      return bool;
    }
  }
  
  public static class TencentHitTestData
    extends AwContents.HitTestData
  {
    public boolean canBeHidden;
    public int hitNodeHeight;
    public int hitNodeWidth;
    public int hitNodeX;
    public int hitNodeY;
    public Point hitTestPoint;
    public int imgHeight;
    public int imgWidth;
    public boolean isPassword;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentAwContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */