package com.tencent.smtt.webkit;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.smtt.util.g;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class RenderMonitor
{
  private static SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences;
  public static g a;
  private static String jdField_a_of_type_JavaLangString = "RenderMonitor";
  private static boolean jdField_a_of_type_Boolean = false;
  private static boolean b = false;
  private static boolean c = false;
  private static boolean d = false;
  private static boolean e = false;
  private static boolean f = false;
  private static boolean g = false;
  
  public static void a()
  {
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        try
        {
          RenderMonitor.b();
          RenderMonitor.a(RenderMonitor.a().getBoolean("render_debug_debug_borders", false));
          RenderMonitor.i(RenderMonitor.h());
          RenderMonitor.b(RenderMonitor.a().getBoolean("render_debug_info_panel", false));
          org.chromium.tencent.x5pro.SurfaceRenderer.sDebugMode = RenderMonitor.i();
          RenderMonitor.c(RenderMonitor.a().getBoolean("render_debug_disable_x5pro", false));
          org.chromium.tencent.x5pro.SurfaceRenderer.sDisableX5ProForced = RenderMonitor.j();
          RenderMonitor.d(RenderMonitor.a().getBoolean("render_debug_fps_meter", false));
          RenderMonitor.j(RenderMonitor.k());
          RenderMonitor.e(RenderMonitor.a().getBoolean("render_debug_cpu_raster", false));
          RenderMonitor.k(RenderMonitor.l());
          RenderMonitor.f(RenderMonitor.a().getBoolean("render_debug_sw_draw", false));
          org.chromium.tencent.TencentAwContent.ENABLE_SW_DRAW = RenderMonitor.m();
          RenderMonitor.g(RenderMonitor.a().getBoolean("render_debug_ad_filter", false));
          RenderMonitor.l(RenderMonitor.n());
          return;
        }
        catch (UnsatisfiedLinkError localUnsatisfiedLinkError) {}
      }
    });
  }
  
  public static void a(Canvas paramCanvas, String paramString)
  {
    if (!b()) {
      return;
    }
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    localPaint.setTextSize(40.0F);
    localPaint.setColor(-65536);
    paramCanvas.drawText(paramString, 20.0F, 300.0F, localPaint);
  }
  
  public static void a(TencentWebViewProxy paramTencentWebViewProxy, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString)
  {
    jdField_a_of_type_ComTencentSmttUtilG = new g(paramTencentWebViewProxy, paramString);
    jdField_a_of_type_ComTencentSmttUtilG.a(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramString);
  }
  
  public static void a(boolean paramBoolean)
  {
    jdField_a_of_type_Boolean = paramBoolean;
    try
    {
      nativeSetEnableDebugBorders(jdField_a_of_type_Boolean);
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          RenderMonitor.b();
          SharedPreferences.Editor localEditor = RenderMonitor.a().edit();
          localEditor.putBoolean("render_debug_debug_borders", RenderMonitor.h());
          localEditor.commit();
        }
      });
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      for (;;) {}
    }
  }
  
  public static void a(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    try
    {
      nativeSetEnableClearFrameBufferForStart(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError) {}
  }
  
  public static boolean a()
  {
    return jdField_a_of_type_Boolean;
  }
  
  public static void b(boolean paramBoolean)
  {
    b = paramBoolean;
    org.chromium.tencent.x5pro.SurfaceRenderer.sDebugMode = b;
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        RenderMonitor.b();
        SharedPreferences.Editor localEditor = RenderMonitor.a().edit();
        localEditor.putBoolean("render_debug_info_panel", RenderMonitor.i());
        localEditor.commit();
      }
    });
  }
  
  public static void b(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    try
    {
      nativeSetEnableClearFrameBufferForEnd(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError) {}
  }
  
  public static boolean b()
  {
    return b;
  }
  
  private static void c()
  {
    if (jdField_a_of_type_AndroidContentSharedPreferences == null) {
      jdField_a_of_type_AndroidContentSharedPreferences = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("render_switch_settings", 0);
    }
  }
  
  public static void c(boolean paramBoolean)
  {
    c = paramBoolean;
    org.chromium.tencent.x5pro.SurfaceRenderer.sDisableX5ProForced = c;
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        RenderMonitor.b();
        SharedPreferences.Editor localEditor = RenderMonitor.a().edit();
        localEditor.putBoolean("render_debug_disable_x5pro", RenderMonitor.j());
        localEditor.commit();
      }
    });
  }
  
  public static boolean c()
  {
    return c;
  }
  
  public static void d(boolean paramBoolean)
  {
    d = paramBoolean;
    try
    {
      nativeSetEnableFpsMeter(d);
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          RenderMonitor.b();
          SharedPreferences.Editor localEditor = RenderMonitor.a().edit();
          localEditor.putBoolean("render_debug_fps_meter", RenderMonitor.k());
          localEditor.commit();
        }
      });
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      for (;;) {}
    }
  }
  
  public static boolean d()
  {
    return d;
  }
  
  public static void e(boolean paramBoolean)
  {
    e = paramBoolean;
    try
    {
      nativeSetEnableCpuRaster(e);
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          RenderMonitor.b();
          SharedPreferences.Editor localEditor = RenderMonitor.a().edit();
          localEditor.putBoolean("render_debug_cpu_raster", RenderMonitor.l());
          localEditor.commit();
        }
      });
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      for (;;) {}
    }
  }
  
  public static boolean e()
  {
    return e;
  }
  
  public static void f(boolean paramBoolean)
  {
    f = paramBoolean;
    org.chromium.tencent.TencentAwContent.ENABLE_SW_DRAW = f;
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        RenderMonitor.b();
        SharedPreferences.Editor localEditor = RenderMonitor.a().edit();
        localEditor.putBoolean("render_debug_sw_draw", RenderMonitor.m());
        localEditor.commit();
      }
    });
  }
  
  public static boolean f()
  {
    return f;
  }
  
  public static void g(boolean paramBoolean)
  {
    g = paramBoolean;
    try
    {
      nativeSetDisableRenderAdFilter(g);
      BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
      {
        public void doRun()
        {
          RenderMonitor.b();
          SharedPreferences.Editor localEditor = RenderMonitor.a().edit();
          localEditor.putBoolean("render_debug_ad_filter", RenderMonitor.n());
          localEditor.commit();
        }
      });
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      for (;;) {}
    }
  }
  
  public static boolean g()
  {
    return g;
  }
  
  public static void h(boolean paramBoolean)
  {
    try
    {
      nativeSetEnableKeyLog(paramBoolean);
      return;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError) {}
  }
  
  private static native void nativeSetDisableRenderAdFilter(boolean paramBoolean);
  
  private static native void nativeSetEnableClearFrameBufferForEnd(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  private static native void nativeSetEnableClearFrameBufferForStart(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  private static native void nativeSetEnableCpuRaster(boolean paramBoolean);
  
  private static native void nativeSetEnableDebugBorders(boolean paramBoolean);
  
  private static native void nativeSetEnableFpsMeter(boolean paramBoolean);
  
  private static native void nativeSetEnableKeyLog(boolean paramBoolean);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\RenderMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */