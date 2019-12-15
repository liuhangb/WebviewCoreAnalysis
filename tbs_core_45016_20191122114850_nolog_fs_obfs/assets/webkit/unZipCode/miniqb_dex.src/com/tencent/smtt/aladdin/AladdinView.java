package com.tencent.smtt.aladdin;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import com.tencent.common.utils.FileUtils;
import com.tencent.smtt.export.external.easel.interfaces.IEaselViewClient;
import com.tencent.smtt.export.external.easel.interfaces.ILoader;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.UsedByReflection;

@JNINamespace("aladdin")
@UsedByReflection("WebCoreProxy.java")
public class AladdinView
  extends SurfaceView
  implements Choreographer.FrameCallback, SurfaceHolder.Callback, BundleLoader.Listener
{
  private float jdField_a_of_type_Float;
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private final Choreographer jdField_a_of_type_AndroidViewChoreographer;
  private IEaselViewClient jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesIEaselViewClient;
  private Object jdField_a_of_type_JavaLangObject = new Object();
  private String jdField_a_of_type_JavaLangString = null;
  private final HashSet<Object> jdField_a_of_type_JavaUtilHashSet = new HashSet();
  private Set<ValueCallback<Bitmap>> jdField_a_of_type_JavaUtilSet = new HashSet();
  
  @UsedByReflection("WebCoreProxy.java")
  public AladdinView(final Context paramContext)
  {
    super(paramContext);
    setZOrderMediaOverlay(true);
    getHolder().setFormat(1);
    getHolder().addCallback(this);
    this.jdField_a_of_type_AndroidViewChoreographer = Choreographer.getInstance();
    this.jdField_a_of_type_AndroidOsHandler = new Handler(AladdinThread.a().a())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what != 1) {
          return;
        }
        if (AladdinView.a(AladdinView.this) != 0L)
        {
          AladdinView localAladdinView = AladdinView.this;
          AladdinView.a(localAladdinView, AladdinView.a(localAladdinView), ((Long)paramAnonymousMessage.obj).longValue());
        }
      }
    };
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    Display localDisplay = ((WindowManager)paramContext.getApplicationContext().getSystemService("window")).getDefaultDisplay();
    localDisplay.getRealMetrics(localDisplayMetrics);
    Point localPoint = new Point();
    localDisplay.getSize(localPoint);
    final int i = localPoint.x;
    final int j = localPoint.y;
    this.jdField_a_of_type_Float = localDisplayMetrics.density;
    this.jdField_a_of_type_Int = localDisplay.getRotation();
    AladdinFactory.a(paramContext);
    a(new Runnable()
    {
      public void run()
      {
        AladdinView localAladdinView1 = AladdinView.this;
        int i = i;
        int j = j;
        float f = AladdinView.a(localAladdinView1);
        AladdinView localAladdinView2 = AladdinView.this;
        AladdinView.a(localAladdinView1, AladdinView.a(localAladdinView1, i, j, f, AladdinView.a(localAladdinView2, AladdinView.a(localAladdinView2))));
        AladdinView.a(AladdinView.this, paramContext);
      }
    });
  }
  
  private int a(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return 0;
    case 3: 
      return -90;
    case 2: 
      return 180;
    }
    return 90;
  }
  
  private ILoader a()
  {
    Object localObject1 = this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesIEaselViewClient;
    if (localObject1 != null) {
      localObject1 = ((IEaselViewClient)localObject1).onCreateLoader();
    } else {
      localObject1 = null;
    }
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = new a();
    }
    return (ILoader)localObject2;
  }
  
  private void a(Context paramContext)
  {
    if (!SmttServiceProxy.getInstance().isCodeCacheEnable()) {
      return;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("/tencent/QQBrowser");
    ((StringBuilder)localObject).append("/aladdin/code_cache/");
    localObject = ((StringBuilder)localObject).toString();
    if (Environment.getExternalStorageState().equals("mounted"))
    {
      File localFile = FileUtils.getSDcardDir(paramContext);
      if (localFile != null)
      {
        l = this.jdField_a_of_type_Long;
        paramContext = new StringBuilder();
        paramContext.append(localFile);
        paramContext.append((String)localObject);
        nativeSetCodeCacheDir(l, paramContext.toString());
        return;
      }
    }
    long l = this.jdField_a_of_type_Long;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramContext.getCacheDir());
    ((StringBuilder)localObject).append("/aladdin/code_cache/");
    nativeSetCodeCacheDir(l, ((StringBuilder)localObject).toString());
  }
  
  @CalledByNative
  private void addRetainedJavaScriptObject(Object paramObject)
  {
    this.jdField_a_of_type_JavaUtilHashSet.add(paramObject);
  }
  
  private static boolean b(int paramInt)
  {
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (paramInt != 0)
    {
      bool1 = bool2;
      if (paramInt != 1)
      {
        bool1 = bool2;
        if (paramInt != 3)
        {
          bool1 = bool2;
          if (paramInt != 2)
          {
            bool1 = bool2;
            if (paramInt != 5)
            {
              if (paramInt == 6) {
                return true;
              }
              bool1 = false;
            }
          }
        }
      }
    }
    return bool1;
  }
  
  @CalledByNative
  private ResourceLoader createResourceLoader()
  {
    return new ResourceLoader(a());
  }
  
  private native void nativeAddJavascriptInterface(long paramLong, Object paramObject, String paramString, Class paramClass);
  
  private native void nativeCompileScriptInBg(long paramLong, String paramString1, String paramString2);
  
  private native void nativeDestroy(long paramLong);
  
  private native void nativeDraw(long paramLong1, long paramLong2);
  
  private native Object nativeEvaluateScript(long paramLong, String paramString1, String paramString2, int paramInt);
  
  private native long nativeInit(int paramInt1, int paramInt2, float paramFloat, int paramInt3);
  
  private native void nativeOnTouchEvent(long paramLong1, MotionEvent paramMotionEvent, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt5, int paramInt6, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, int paramInt7, int paramInt8, int paramInt9, int paramInt10, boolean paramBoolean);
  
  private native void nativePause(long paramLong);
  
  private native void nativeRemoveJavascriptInterface(long paramLong, String paramString);
  
  private native void nativeResume(long paramLong);
  
  private native Object nativeRunBgCompiledScript(long paramLong, String paramString1, String paramString2, int paramInt);
  
  private native void nativeSetCodeCacheDir(long paramLong, String paramString);
  
  private native void nativeSetDeviceScaleFactor(long paramLong, float paramFloat);
  
  private native void nativeSetOrientation(long paramLong, int paramInt);
  
  private native void nativeSetURL(long paramLong, String paramString);
  
  private native void nativeSurfaceChanged(long paramLong, int paramInt1, int paramInt2, int paramInt3, Surface paramSurface);
  
  private native void nativeSurfaceCreated(long paramLong);
  
  private native void nativeSurfaceDestroyed(long paramLong);
  
  private native void nativeTakeScreenshot(long paramLong);
  
  @CalledByNative
  private void onFirstPaint()
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        if (AladdinView.a(AladdinView.this) != null) {
          AladdinView.a(AladdinView.this).onContentsShown();
        }
      }
    });
  }
  
  @CalledByNative
  private void onScreenshotTaken(final Bitmap paramBitmap)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        synchronized (AladdinView.a(AladdinView.this))
        {
          Iterator localIterator = AladdinView.a(AladdinView.this).iterator();
          while (localIterator.hasNext()) {
            ((ValueCallback)localIterator.next()).onReceiveValue(paramBitmap);
          }
          AladdinView.a(AladdinView.this).clear();
          return;
        }
      }
    });
  }
  
  @CalledByNative
  private void removeRetainedJavaScriptObject(Object paramObject)
  {
    this.jdField_a_of_type_JavaUtilHashSet.remove(paramObject);
  }
  
  public void a(float paramFloat)
  {
    if (paramFloat == this.jdField_a_of_type_Float) {
      return;
    }
    this.jdField_a_of_type_Float = paramFloat;
    if (a()) {
      return;
    }
    a(new Runnable()
    {
      public void run()
      {
        if (AladdinView.a(AladdinView.this) == 0L) {
          return;
        }
        AladdinView localAladdinView = AladdinView.this;
        AladdinView.a(localAladdinView, AladdinView.a(localAladdinView), AladdinView.a(AladdinView.this));
      }
    });
  }
  
  public void a(int paramInt)
  {
    if (paramInt == this.jdField_a_of_type_Int) {
      return;
    }
    this.jdField_a_of_type_Int = paramInt;
    if (a()) {
      return;
    }
    a(new Runnable()
    {
      public void run()
      {
        if (AladdinView.a(AladdinView.this) == 0L) {
          return;
        }
        AladdinView localAladdinView1 = AladdinView.this;
        long l = AladdinView.a(localAladdinView1);
        AladdinView localAladdinView2 = AladdinView.this;
        AladdinView.a(localAladdinView1, l, AladdinView.a(localAladdinView2, AladdinView.a(localAladdinView2)));
      }
    });
  }
  
  protected void a(Runnable paramRunnable)
  {
    if (b())
    {
      paramRunnable.run();
      return;
    }
    this.jdField_a_of_type_AndroidOsHandler.post(paramRunnable);
  }
  
  public void a(final String paramString1, final String paramString2)
  {
    this.jdField_a_of_type_JavaLangString = paramString1;
    if (paramString2 == null) {
      return;
    }
    if (a()) {
      return;
    }
    a(new Runnable()
    {
      public void run()
      {
        if (AladdinView.a(AladdinView.this) == 0L) {
          return;
        }
        AladdinReporter.a(AladdinView.a(AladdinView.this));
        AladdinView localAladdinView = AladdinView.this;
        AladdinView.a(localAladdinView, AladdinView.a(localAladdinView), paramString1);
        localAladdinView = AladdinView.this;
        AladdinView.a(localAladdinView, AladdinView.a(localAladdinView), paramString2, paramString1, AladdinView.a.a.ordinal());
      }
    });
    this.jdField_a_of_type_AndroidViewChoreographer.postFrameCallback(this);
  }
  
  protected boolean a()
  {
    return this.jdField_a_of_type_AndroidOsHandler == null;
  }
  
  protected boolean b()
  {
    return this.jdField_a_of_type_AndroidOsHandler.getLooper() == Looper.myLooper();
  }
  
  public void doFrame(long paramLong)
  {
    if (a()) {
      return;
    }
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(1);
    this.jdField_a_of_type_AndroidOsHandler.obtainMessage(1, Long.valueOf(paramLong)).sendToTarget();
    this.jdField_a_of_type_AndroidViewChoreographer.postFrameCallback(this);
  }
  
  protected void onConfigurationChanged(Configuration paramConfiguration)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((WindowManager)getContext().getApplicationContext().getSystemService("window")).getDefaultDisplay().getRealMetrics(localDisplayMetrics);
    a(localDisplayMetrics.density);
    a(paramConfiguration.orientation);
  }
  
  public void onLoadComplete(BundleLoader paramBundleLoader, String paramString1, String paramString2)
  {
    a(paramString2, paramString1);
  }
  
  public void onLoadFailed(BundleLoader paramBundleLoader)
  {
    Log.e("AladdinView", "onLoadFailed");
  }
  
  public boolean onTouchEvent(final MotionEvent paramMotionEvent)
  {
    if (a()) {
      return false;
    }
    a(new Runnable()
    {
      public void run()
      {
        if (AladdinView.a(AladdinView.this) == 0L) {
          return;
        }
        int k = paramMotionEvent.getActionMasked();
        if (!AladdinView.a(k)) {
          return;
        }
        int m = paramMotionEvent.getPointerCount();
        float f1;
        float[] arrayOfFloat2;
        try
        {
          arrayOfFloat1 = new float[2];
          arrayOfFloat1[0] = paramMotionEvent.getTouchMajor();
          if (m <= 1) {
            break label450;
          }
          f1 = paramMotionEvent.getTouchMajor(1);
          arrayOfFloat1[1] = f1;
          arrayOfFloat2 = new float[2];
          arrayOfFloat2[0] = paramMotionEvent.getTouchMinor();
          if (m <= 1) {
            break label455;
          }
          f1 = paramMotionEvent.getTouchMinor(1);
        }
        catch (Exception localException)
        {
          float[] arrayOfFloat1;
          label113:
          AladdinView localAladdinView;
          long l1;
          MotionEvent localMotionEvent;
          long l2;
          int n;
          int i1;
          float f5;
          float f6;
          int i2;
          float f7;
          float f8;
          float f9;
          float f10;
          float f11;
          float f12;
          float f13;
          float f14;
          int i3;
          localException.printStackTrace();
          return;
        }
        localAladdinView = AladdinView.this;
        l1 = AladdinView.a(AladdinView.this);
        localMotionEvent = paramMotionEvent;
        l2 = paramMotionEvent.getEventTime();
        n = paramMotionEvent.getHistorySize();
        i1 = paramMotionEvent.getActionIndex();
        f5 = paramMotionEvent.getX();
        f6 = paramMotionEvent.getY();
        label197:
        float f2;
        label215:
        int i;
        label244:
        float f3;
        label295:
        float f4;
        label327:
        int j;
        if (m > 1)
        {
          f1 = paramMotionEvent.getX(1);
          if (m <= 1) {
            break label521;
          }
          f2 = paramMotionEvent.getY(1);
          i2 = paramMotionEvent.getPointerId(0);
          if (m <= 1) {
            break label526;
          }
          i = paramMotionEvent.getPointerId(1);
          f7 = arrayOfFloat1[0];
          f8 = arrayOfFloat1[1];
          f9 = arrayOfFloat2[0];
          f10 = arrayOfFloat2[1];
          f11 = paramMotionEvent.getOrientation();
          if (m <= 1) {
            break label532;
          }
          f3 = paramMotionEvent.getOrientation(1);
          f12 = paramMotionEvent.getAxisValue(25);
          if (m <= 1) {
            break label537;
          }
          f4 = paramMotionEvent.getAxisValue(25, 1);
          f13 = paramMotionEvent.getX();
          f14 = paramMotionEvent.getY();
          i3 = paramMotionEvent.getToolType(0);
          if (m <= 1) {
            break label543;
          }
          j = paramMotionEvent.getToolType(1);
        }
        for (;;)
        {
          AladdinView.a(localAladdinView, l1, localMotionEvent, l2, k, m, n, i1, f5, f6, f1, f2, i2, i, f7, f8, f9, f10, f11, f3, f12, f4, f13, f14, i3, j, paramMotionEvent.getButtonState(), paramMotionEvent.getMetaState(), false);
          return;
          label450:
          f1 = 0.0F;
          break;
          label455:
          f1 = 0.0F;
          arrayOfFloat2[1] = f1;
          i = 0;
          while (i < 2)
          {
            if (localException[i] < arrayOfFloat2[i])
            {
              f1 = localException[i];
              localException[i] = arrayOfFloat2[i];
              arrayOfFloat2[i] = f1;
            }
            i += 1;
          }
          break label113;
          f1 = 0.0F;
          break label197;
          label521:
          f2 = 0.0F;
          break label215;
          label526:
          i = -1;
          break label244;
          label532:
          f3 = 0.0F;
          break label295;
          label537:
          f4 = 0.0F;
          break label327;
          label543:
          j = 0;
        }
      }
    });
    return true;
  }
  
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, final int paramInt1, final int paramInt2, final int paramInt3)
  {
    if (a()) {
      return;
    }
    a(new Runnable()
    {
      public void run()
      {
        if (AladdinView.a(AladdinView.this) == 0L) {
          return;
        }
        AladdinView localAladdinView = AladdinView.this;
        AladdinView.a(localAladdinView, AladdinView.a(localAladdinView), paramInt1, paramInt2, paramInt3, this.jdField_a_of_type_AndroidViewSurface);
      }
    });
  }
  
  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    if (a()) {
      return;
    }
    a(new Runnable()
    {
      public void run()
      {
        if (AladdinView.a(AladdinView.this) == 0L) {
          return;
        }
        AladdinView localAladdinView = AladdinView.this;
        AladdinView.a(localAladdinView, AladdinView.a(localAladdinView));
      }
    });
  }
  
  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    if (a()) {
      return;
    }
    a(new Runnable()
    {
      public void run()
      {
        if (AladdinView.a(AladdinView.this) == 0L) {
          return;
        }
        AladdinView localAladdinView = AladdinView.this;
        AladdinView.b(localAladdinView, AladdinView.a(localAladdinView));
      }
    });
  }
  
  private static enum a
  {
    static
    {
      jdField_a_of_type_ComTencentSmttAladdinAladdinView$a = new a("NONE", 0);
      b = new a("STRING", 1);
      c = new a("JSVALUE", 2);
      jdField_a_of_type_ArrayOfComTencentSmttAladdinAladdinView$a = new a[] { jdField_a_of_type_ComTencentSmttAladdinAladdinView$a, b, c };
    }
    
    private a() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\aladdin\AladdinView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */