package org.chromium.ui;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Looper;
import android.view.Choreographer;
import android.view.Choreographer.FrameCallback;
import android.view.Display;
import android.view.WindowManager;
import org.chromium.base.TraceEvent;

public class VSyncMonitor
{
  private long jdField_a_of_type_Long;
  private final Handler jdField_a_of_type_AndroidOsHandler = new Handler();
  private final Choreographer.FrameCallback jdField_a_of_type_AndroidViewChoreographer$FrameCallback;
  private final Choreographer jdField_a_of_type_AndroidViewChoreographer;
  private final Runnable jdField_a_of_type_JavaLangRunnable;
  private Listener jdField_a_of_type_OrgChromiumUiVSyncMonitor$Listener;
  private long jdField_b_of_type_Long;
  private boolean jdField_b_of_type_Boolean;
  private long jdField_c_of_type_Long;
  private boolean jdField_c_of_type_Boolean;
  private boolean d;
  
  public VSyncMonitor(Context paramContext, Listener paramListener)
  {
    this.jdField_a_of_type_OrgChromiumUiVSyncMonitor$Listener = paramListener;
    float f2 = ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getRefreshRate();
    final boolean bool;
    if (f2 < 30.0F) {
      bool = true;
    } else {
      bool = false;
    }
    float f1 = f2;
    if (f2 <= 0.0F) {
      f1 = 60.0F;
    }
    this.jdField_a_of_type_Long = ((1.0E9F / f1));
    if (Build.VERSION.SDK_INT >= 16)
    {
      this.jdField_a_of_type_AndroidViewChoreographer = Choreographer.getInstance();
      this.jdField_a_of_type_AndroidViewChoreographer$FrameCallback = new Choreographer.FrameCallback()
      {
        public void doFrame(long paramAnonymousLong)
        {
          TraceEvent.begin("VSync");
          if ((bool) && (VSyncMonitor.a(VSyncMonitor.this)))
          {
            long l = VSyncMonitor.a(VSyncMonitor.this);
            localVSyncMonitor = VSyncMonitor.this;
            VSyncMonitor.a(localVSyncMonitor, VSyncMonitor.b(localVSyncMonitor) + ((float)(paramAnonymousLong - l - VSyncMonitor.b(VSyncMonitor.this)) * 0.1F));
          }
          VSyncMonitor.b(VSyncMonitor.this, paramAnonymousLong);
          VSyncMonitor localVSyncMonitor = VSyncMonitor.this;
          VSyncMonitor.a(localVSyncMonitor, paramAnonymousLong, VSyncMonitor.c(localVSyncMonitor));
          TraceEvent.end("VSync");
        }
      };
      this.jdField_a_of_type_JavaLangRunnable = null;
    }
    else
    {
      this.jdField_a_of_type_AndroidViewChoreographer = null;
      this.jdField_a_of_type_AndroidViewChoreographer$FrameCallback = null;
      this.jdField_a_of_type_JavaLangRunnable = new Runnable()
      {
        public void run()
        {
          TraceEvent.begin("VSyncTimer");
          long l = VSyncMonitor.c(VSyncMonitor.this);
          VSyncMonitor.a(VSyncMonitor.this, l, l);
          TraceEvent.end("VSyncTimer");
        }
      };
      this.jdField_c_of_type_Long = 0L;
    }
    this.jdField_b_of_type_Long = b();
  }
  
  private long a(long paramLong)
  {
    long l1 = this.jdField_b_of_type_Long;
    long l2 = this.jdField_a_of_type_Long;
    return l1 + (paramLong - l1) / l2 * l2;
  }
  
  private void a(long paramLong1, long paramLong2)
  {
    if ((!jdField_a_of_type_Boolean) && (!this.d)) {
      throw new AssertionError();
    }
    this.jdField_b_of_type_Boolean = true;
    this.d = false;
    try
    {
      if (this.jdField_a_of_type_OrgChromiumUiVSyncMonitor$Listener != null) {
        this.jdField_a_of_type_OrgChromiumUiVSyncMonitor$Listener.onVSync(this, paramLong1 / 1000L);
      }
      return;
    }
    finally
    {
      this.jdField_b_of_type_Boolean = false;
    }
  }
  
  private long b()
  {
    return System.nanoTime();
  }
  
  private void b()
  {
    if (this.d) {
      return;
    }
    this.d = true;
    this.jdField_c_of_type_Boolean = this.jdField_b_of_type_Boolean;
    if (b())
    {
      this.jdField_a_of_type_AndroidViewChoreographer.postFrameCallback(this.jdField_a_of_type_AndroidViewChoreographer$FrameCallback);
      return;
    }
    c();
  }
  
  private boolean b()
  {
    return this.jdField_a_of_type_AndroidViewChoreographer != null;
  }
  
  private void c()
  {
    if ((!jdField_a_of_type_Boolean) && (b())) {
      throw new AssertionError();
    }
    long l3 = b();
    long l2 = a(l3);
    long l1 = this.jdField_a_of_type_Long;
    l2 = l2 + l1 - l3;
    if ((!jdField_a_of_type_Boolean) && ((l2 <= 0L) || (l2 > l1))) {
      throw new AssertionError();
    }
    long l4 = this.jdField_c_of_type_Long;
    long l5 = this.jdField_a_of_type_Long;
    l1 = l2;
    if (l3 + l2 <= l4 + l5 / 2L) {
      l1 = l2 + l5;
    }
    this.jdField_c_of_type_Long = (l3 + l1);
    if (l1 == 0L)
    {
      this.jdField_a_of_type_AndroidOsHandler.post(this.jdField_a_of_type_JavaLangRunnable);
      return;
    }
    this.jdField_a_of_type_AndroidOsHandler.postDelayed(this.jdField_a_of_type_JavaLangRunnable, l1 / 1000000L);
  }
  
  public long a()
  {
    return this.jdField_a_of_type_Long / 1000L;
  }
  
  public void a()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidOsHandler.getLooper() != Looper.myLooper())) {
      throw new AssertionError();
    }
    b();
  }
  
  public boolean a()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  public static abstract interface Listener
  {
    public abstract void onVSync(VSyncMonitor paramVSyncMonitor, long paramLong);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\VSyncMonitor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */