package org.chromium.tencent.x5pro;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.os.Build;
import android.os.Build.VERSION;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback2;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedList;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.SmttServiceClientProxy;
import org.chromium.tencent.TencentAwContent;

@JNINamespace("tencent")
public class SurfaceRenderer
{
  private static final String EVENT_X5PRO_INFO = "MTT_CORE_X5PRO_INFO";
  public static final int REPORT_TYPE_CONTEXT_IS_INVALID = -1001;
  public static final int REPORT_TYPE_SURFACE_VIEW_IS_EXIST = -1003;
  public static final int REPORT_TYPE_WINDOW_IS_INVALID = -1002;
  public static final int REPORT_TYPE_X5PRO_ERROR = 1001;
  public static final int REPORT_TYPE_X5PRO_IS_CREATED = 0;
  public static final int REPORT_TYPE_X5PRO_IS_ROLLBACK = 1002;
  private static final int SDK_80 = 26;
  private static final int SDK_90 = 28;
  private static final String TAG = "X5Pro";
  public static boolean sDebugMode = false;
  public static boolean sDisableX5ProForced = false;
  private static boolean sHiddenApiWarningShown = false;
  private static Method sViewGroupAttachViewToParentMethod;
  private static Method sViewGroupDetachViewFromParentMethod;
  private TencentAwContent mAwContents;
  private ViewGroup mContainerView;
  private boolean mHasReceivedFrame = false;
  private int mLastSwappedRoutingId = 0;
  private int mLastSwappedSurfaceHandle = 0;
  private int mLastSwappedSurfaceHeight = 0;
  private int mLastSwappedSurfaceWidth = 0;
  private long mNativeSurfaceRenderer;
  private SurfaceViewRenderView mRenderView;
  private boolean mSurfaceCreated = false;
  private int mSurfaceHandle = 0;
  private int mSurfaceHeight = 0;
  private int mSurfaceWidth = 0;
  
  static
  {
    try
    {
      sViewGroupAttachViewToParentMethod = Class.forName("android.view.ViewGroup").getDeclaredMethod("attachViewToParent", new Class[] { View.class, Integer.TYPE, ViewGroup.LayoutParams.class });
      sViewGroupAttachViewToParentMethod.setAccessible(true);
    }
    catch (Throwable localThrowable1)
    {
      localThrowable1.printStackTrace();
    }
    try
    {
      sViewGroupDetachViewFromParentMethod = Class.forName("android.view.ViewGroup").getDeclaredMethod("detachViewFromParent", new Class[] { View.class });
      sViewGroupDetachViewFromParentMethod.setAccessible(true);
    }
    catch (Throwable localThrowable2)
    {
      localThrowable2.printStackTrace();
    }
    if (("SAMSUNG".equalsIgnoreCase(Build.MANUFACTURER)) && (Build.VERSION.SDK_INT == 26)) {
      sDisableX5ProForced = true;
    }
  }
  
  private SurfaceRenderer(ViewGroup paramViewGroup, Context paramContext, TencentAwContent paramTencentAwContent)
  {
    this.mAwContents = paramTencentAwContent;
    this.mRenderView = new SurfaceViewRenderView(paramContext);
    setContainerView(paramViewGroup);
    this.mNativeSurfaceRenderer = nativeInit();
    reportX5ProInfo(0, "X5ProIsCreated");
  }
  
  public static Activity activityFromContext(Context paramContext)
  {
    if ((paramContext instanceof Activity)) {
      return (Activity)paramContext;
    }
    if ((paramContext instanceof ContextWrapper))
    {
      Context localContext = ((ContextWrapper)paramContext).getBaseContext();
      if (paramContext == localContext) {
        return null;
      }
      return activityFromContext(localContext);
    }
    return null;
  }
  
  public static SurfaceRenderer create(ViewGroup paramViewGroup, Context paramContext, TencentAwContent paramTencentAwContent)
  {
    if (sDisableX5ProForced) {
      return null;
    }
    if (Build.VERSION.SDK_INT >= 26)
    {
      if (Build.VERSION.SDK_INT > 28) {
        return null;
      }
      if (!SmttServiceClientProxy.getInstance().getIsX5ProEnabled()) {
        return null;
      }
      Object localObject = activityFromContext(paramContext);
      if (localObject == null)
      {
        paramViewGroup = new StringBuilder();
        paramViewGroup.append("context:");
        paramViewGroup.append(paramContext);
        reportX5ProInfo(64535, paramViewGroup.toString());
        return null;
      }
      localObject = ((Activity)localObject).getWindow();
      if (localObject == null)
      {
        paramViewGroup = new StringBuilder();
        paramViewGroup.append("context:");
        paramViewGroup.append(paramContext);
        reportX5ProInfo(64534, paramViewGroup.toString());
        return null;
      }
      if (hasSurfaceViewAlreadyExist((Window)localObject, null))
      {
        reportX5ProInfo(64533, "SurfaceViewHasAlreadyExist");
        return null;
      }
      return new SurfaceRenderer(paramViewGroup, paramContext, paramTencentAwContent);
    }
    return null;
  }
  
  @CalledByNative
  private void didDestroyCompositor(int paramInt) {}
  
  private static boolean hasSurfaceViewAlreadyExist(Window paramWindow, SurfaceView paramSurfaceView)
  {
    paramWindow = paramWindow.getDecorView();
    if (paramWindow == null) {
      return false;
    }
    if ((paramWindow instanceof ViewGroup))
    {
      ViewGroup localViewGroup = (ViewGroup)paramWindow;
      paramWindow = new LinkedList();
      paramWindow.add(localViewGroup);
      while (!paramWindow.isEmpty())
      {
        localViewGroup = (ViewGroup)paramWindow.removeFirst();
        int i = 0;
        while (i < localViewGroup.getChildCount())
        {
          View localView = localViewGroup.getChildAt(i);
          if ((localView instanceof ViewGroup)) {
            paramWindow.addLast((ViewGroup)localView);
          } else if (((localView instanceof SurfaceView)) && (localView != paramSurfaceView)) {
            return true;
          }
          i += 1;
        }
      }
    }
    return false;
  }
  
  private native void nativeDestroy(long paramLong);
  
  private native long nativeInit();
  
  private native void nativeRedrawSurfaceSync(long paramLong);
  
  private native void nativeSetRenderToNativeSurface(long paramLong, boolean paramBoolean);
  
  private native int nativeSurfaceChanged(long paramLong, int paramInt1, int paramInt2, int paramInt3, Surface paramSurface);
  
  private native void nativeSurfaceCreated(long paramLong);
  
  private native void nativeSurfaceDestroyed(long paramLong);
  
  private native void nativeSurfaceSizeChanged(long paramLong, int paramInt1, int paramInt2);
  
  @CalledByNative
  private void onSwapBuffersCompleted(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (!this.mSurfaceCreated) {
      return;
    }
    int i;
    if ((this.mLastSwappedSurfaceWidth == paramInt3) && (this.mLastSwappedSurfaceHeight == paramInt4)) {
      i = 0;
    } else {
      i = 1;
    }
    if ((!this.mHasReceivedFrame) || (i != 0))
    {
      this.mRenderView.setWillNotDraw(false);
      this.mRenderView.invalidate();
    }
    this.mHasReceivedFrame = true;
    this.mLastSwappedSurfaceHandle = paramInt1;
    this.mLastSwappedSurfaceWidth = paramInt3;
    this.mLastSwappedSurfaceHeight = paramInt4;
    this.mLastSwappedRoutingId = paramInt2;
  }
  
  @CalledByNative
  private static void reportError(String paramString)
  {
    reportX5ProInfo(1001, paramString);
  }
  
  public static void reportX5ProInfo(int paramInt, String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("type", String.valueOf(paramInt));
    localHashMap.put("msg", paramString);
    SmttServiceClientProxy.getInstance().uploadToBeacon("MTT_CORE_X5PRO_INFO", localHashMap);
  }
  
  public void destroy()
  {
    if (this.mRenderView != null)
    {
      if (this.mSurfaceCreated)
      {
        nativeSurfaceDestroyed(this.mNativeSurfaceRenderer);
        this.mRenderView.destroySurfaceControlDelayed();
      }
      this.mRenderView.destroy();
      this.mContainerView.removeView(this.mRenderView);
    }
    this.mContainerView = null;
    this.mRenderView = null;
    this.mAwContents = null;
    nativeDestroy(this.mNativeSurfaceRenderer);
    this.mNativeSurfaceRenderer = 0L;
  }
  
  public long getNativeSurfaceRenderer()
  {
    return this.mNativeSurfaceRenderer;
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mRenderView.post(new Runnable()
    {
      public void run()
      {
        Object localObject;
        if ((SurfaceRenderer.this.mContainerView instanceof AbsoluteLayout)) {
          localObject = new AbsoluteLayout.LayoutParams(-1, -1, 0, 0);
        } else if ((SurfaceRenderer.this.mContainerView instanceof FrameLayout)) {
          localObject = new FrameLayout.LayoutParams(-1, -1);
        } else {
          localObject = null;
        }
        if (SurfaceRenderer.this.mRenderView != null)
        {
          SurfaceRenderer.this.mRenderView.setLayoutParams((ViewGroup.LayoutParams)localObject);
          SurfaceRenderer.this.mRenderView.requestLayout();
        }
      }
    });
  }
  
  public void setBackgroundColor(int paramInt)
  {
    SurfaceViewRenderView localSurfaceViewRenderView = this.mRenderView;
    if (localSurfaceViewRenderView != null)
    {
      localSurfaceViewRenderView.setBackgroundColor(paramInt);
      this.mRenderView.setWillNotDraw(false);
      this.mRenderView.invalidate();
    }
  }
  
  public void setContainerView(ViewGroup paramViewGroup)
  {
    if (paramViewGroup == null) {
      return;
    }
    Object localObject = null;
    if ((paramViewGroup instanceof AbsoluteLayout)) {
      localObject = new AbsoluteLayout.LayoutParams(-1, -1, 0, 0);
    } else if ((paramViewGroup instanceof FrameLayout)) {
      localObject = new FrameLayout.LayoutParams(-1, -1);
    }
    ViewGroup localViewGroup = this.mContainerView;
    if (localViewGroup == null)
    {
      paramViewGroup.addView(this.mRenderView);
      if (localObject != null) {
        this.mRenderView.setLayoutParams((ViewGroup.LayoutParams)localObject);
      }
    }
    else
    {
      try
      {
        if (sViewGroupDetachViewFromParentMethod != null) {
          sViewGroupDetachViewFromParentMethod.invoke(localViewGroup, new Object[] { this.mRenderView });
        }
        if (sViewGroupAttachViewToParentMethod != null) {
          sViewGroupAttachViewToParentMethod.invoke(paramViewGroup, new Object[] { this.mRenderView, Integer.valueOf(-1), localObject });
        }
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    this.mContainerView = paramViewGroup;
    this.mRenderView.setWillNotDraw(false);
    this.mRenderView.invalidate();
  }
  
  public void setRenderToNativeSurface(boolean paramBoolean)
  {
    nativeSetRenderToNativeSurface(this.mNativeSurfaceRenderer, paramBoolean);
  }
  
  public void setTranslationX(float paramFloat)
  {
    SurfaceViewRenderView localSurfaceViewRenderView = this.mRenderView;
    if (localSurfaceViewRenderView != null) {
      localSurfaceViewRenderView.setTranslationX(paramFloat);
    }
  }
  
  public void setTranslationY(float paramFloat)
  {
    SurfaceViewRenderView localSurfaceViewRenderView = this.mRenderView;
    if (localSurfaceViewRenderView != null) {
      localSurfaceViewRenderView.setTranslationY(paramFloat);
    }
  }
  
  public class SurfaceViewRenderView
    extends SurfaceView
  {
    private Context mContext;
    private SurfaceHolder.Callback2 mSurfaceCallback;
    
    public SurfaceViewRenderView(Context paramContext)
    {
      super();
      this.mContext = paramContext;
      setBackgroundColor(SurfaceRenderer.this.mAwContents.getEffectiveBackgroundColor());
      this.mSurfaceCallback = new SurfaceHolder.Callback2()
      {
        public void surfaceChanged(SurfaceHolder paramAnonymousSurfaceHolder, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          SurfaceRenderer.access$302(SurfaceRenderer.this, SurfaceRenderer.this.nativeSurfaceChanged(SurfaceRenderer.this.mNativeSurfaceRenderer, paramAnonymousInt1, paramAnonymousInt2, paramAnonymousInt3, paramAnonymousSurfaceHolder.getSurface()));
          SurfaceRenderer.SurfaceViewRenderView.this.setWillNotDraw(false);
          SurfaceRenderer.SurfaceViewRenderView.this.invalidate();
        }
        
        public void surfaceCreated(SurfaceHolder paramAnonymousSurfaceHolder)
        {
          SurfaceRenderer.access$602(SurfaceRenderer.this, true);
          SurfaceRenderer.this.nativeSurfaceCreated(SurfaceRenderer.this.mNativeSurfaceRenderer);
        }
        
        public void surfaceDestroyed(SurfaceHolder paramAnonymousSurfaceHolder)
        {
          SurfaceRenderer.access$802(SurfaceRenderer.this, false);
          SurfaceRenderer.access$602(SurfaceRenderer.this, false);
          SurfaceRenderer.access$302(SurfaceRenderer.this, 0);
          SurfaceRenderer.this.nativeSurfaceDestroyed(SurfaceRenderer.this.mNativeSurfaceRenderer);
          SurfaceRenderer.SurfaceViewRenderView.this.destroySurfaceControlDelayed();
        }
        
        public void surfaceRedrawNeeded(SurfaceHolder paramAnonymousSurfaceHolder)
        {
          SurfaceRenderer.this.nativeRedrawSurfaceSync(SurfaceRenderer.this.mNativeSurfaceRenderer);
        }
      };
      getHolder().addCallback(this.mSurfaceCallback);
    }
    
    public void destroy()
    {
      getHolder().removeCallback(this.mSurfaceCallback);
    }
    
    public void destroySurfaceControlDelayed()
    {
      Object localObject3;
      if ((Build.VERSION.SDK_INT == 28) && (!SurfaceRenderer.sHiddenApiWarningShown))
      {
        try
        {
          Class.forName("android.content.pm.PackageParser$Package").getDeclaredConstructor(new Class[] { String.class }).setAccessible(true);
        }
        catch (Exception localException1)
        {
          localException1.printStackTrace();
        }
        try
        {
          Object localObject1 = Class.forName("android.app.ActivityThread");
          localObject3 = ((Class)localObject1).getDeclaredMethod("currentActivityThread", new Class[0]);
          ((Method)localObject3).setAccessible(true);
          localObject3 = ((Method)localObject3).invoke(null, new Object[0]);
          localObject1 = ((Class)localObject1).getDeclaredField("mHiddenApiWarningShown");
          ((Field)localObject1).setAccessible(true);
          ((Field)localObject1).setBoolean(localObject3, true);
          SurfaceRenderer.access$1102(true);
        }
        catch (Exception localException2)
        {
          localException2.printStackTrace();
        }
      }
      if ((Build.VERSION.SDK_INT >= 26) && (Build.VERSION.SDK_INT <= 28)) {
        try
        {
          Object localObject2 = SurfaceView.class.getDeclaredField("mSurface");
          ((Field)localObject2).setAccessible(true);
          localObject2 = (Surface)((Field)localObject2).get(this);
          localObject3 = SurfaceView.class.getDeclaredField("mSurfaceControl");
          ((Field)localObject3).setAccessible(true);
          Object localObject4 = ((Field)localObject3).get(this);
          ((Field)localObject3).set(this, null);
          Choreographer.getInstance().postFrameCallback(new DelayedCallback(2, (Surface)localObject2, localObject4));
          return;
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
      }
    }
    
    protected void onAttachedToWindow()
    {
      Object localObject = SurfaceRenderer.activityFromContext(this.mContext);
      if ((localObject != null) && (((Activity)localObject).getWindow() != null) && (SurfaceRenderer.hasSurfaceViewAlreadyExist(((Activity)localObject).getWindow(), this)))
      {
        SurfaceRenderer.reportX5ProInfo(1002, "SurfaceViewExistWhenAttachView");
        super.post(new Runnable()
        {
          public void run()
          {
            if (SurfaceRenderer.this.mAwContents != null) {
              SurfaceRenderer.this.mAwContents.setRenderToNativeSurface(false);
            }
          }
        });
        return;
      }
      SurfaceRenderer.access$1302(SurfaceRenderer.this, getWidth());
      SurfaceRenderer.access$1502(SurfaceRenderer.this, getHeight());
      super.onAttachedToWindow();
      setWillNotDraw(false);
      invalidate();
      localObject = SurfaceRenderer.this;
      ((SurfaceRenderer)localObject).nativeSurfaceSizeChanged(((SurfaceRenderer)localObject).mNativeSurfaceRenderer, SurfaceRenderer.this.mSurfaceWidth, SurfaceRenderer.this.mSurfaceHeight);
    }
    
    protected void onDetachedFromWindow()
    {
      super.onDetachedFromWindow();
    }
    
    public void onDraw(Canvas paramCanvas)
    {
      if (!ThreadUtils.runningOnUiThread()) {
        return;
      }
      paramCanvas.clipRect(new Rect(0, 0, Math.min(SurfaceRenderer.this.mLastSwappedSurfaceWidth, SurfaceRenderer.this.mSurfaceWidth), Math.min(SurfaceRenderer.this.mLastSwappedSurfaceHeight, SurfaceRenderer.this.mSurfaceHeight)));
      paramCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
      if ((!paramCanvas.isHardwareAccelerated()) || (!SurfaceRenderer.this.mHasReceivedFrame)) {
        SurfaceRenderer.this.mAwContents.drawToSurfaceView(paramCanvas);
      }
      if (SurfaceRenderer.sDebugMode)
      {
        Paint localPaint = new Paint();
        localPaint.setAntiAlias(true);
        localPaint.setTextSize(40.0F);
        localPaint.setColor(-65536);
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("X5Pro id:");
        localStringBuilder.append(SurfaceRenderer.this.mNativeSurfaceRenderer);
        paramCanvas.drawText(localStringBuilder.toString(), 20.0F, 300.0F, localPaint);
      }
      setWillNotDraw(true);
    }
    
    public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
      SurfaceRenderer.access$1302(SurfaceRenderer.this, paramInt1);
      SurfaceRenderer.access$1502(SurfaceRenderer.this, paramInt2);
      setWillNotDraw(false);
      invalidate();
      SurfaceRenderer localSurfaceRenderer = SurfaceRenderer.this;
      localSurfaceRenderer.nativeSurfaceSizeChanged(localSurfaceRenderer.mNativeSurfaceRenderer, SurfaceRenderer.this.mSurfaceWidth, SurfaceRenderer.this.mSurfaceHeight);
    }
    
    protected void onWindowVisibilityChanged(int paramInt)
    {
      super.onWindowVisibilityChanged(paramInt);
    }
    
    public class DelayedCallback
      implements Choreographer.FrameCallback
    {
      private Surface mSurface;
      private Object mSurfaceControl;
      private int mframeCount = 1;
      
      DelayedCallback(int paramInt, Surface paramSurface, Object paramObject)
      {
        this.mframeCount = paramInt;
        this.mSurface = paramSurface;
        this.mSurfaceControl = paramObject;
      }
      
      public void doFrame(long paramLong)
      {
        this.mframeCount -= 1;
        if (this.mframeCount > 0)
        {
          Choreographer.getInstance().postFrameCallback(this);
          return;
        }
        try
        {
          if (this.mSurface != null) {
            this.mSurface.release();
          }
          Method localMethod = Class.forName("android.view.SurfaceControl").getDeclaredMethod("destroy", new Class[0]);
          if ((localMethod != null) && (this.mSurfaceControl != null))
          {
            localMethod.invoke(this.mSurfaceControl, new Object[0]);
            return;
          }
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\x5pro\SurfaceRenderer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */