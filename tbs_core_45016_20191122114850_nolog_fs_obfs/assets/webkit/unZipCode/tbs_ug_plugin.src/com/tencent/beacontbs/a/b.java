package com.tencent.beacontbs.a;

import android.util.SparseArray;
import com.tencent.beacontbs.c.a;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public abstract class b
{
  private static b a;
  public static boolean a = true;
  
  public static b a()
  {
    try
    {
      if (a == null) {
        a = new a();
      }
      b localb = a;
      return localb;
    }
    finally {}
  }
  
  public abstract void a(int paramInt);
  
  public abstract void a(int paramInt, Runnable paramRunnable, long paramLong1, long paramLong2);
  
  public abstract void a(Runnable paramRunnable);
  
  public abstract void a(Runnable paramRunnable, long paramLong);
  
  static final class a
    extends b
  {
    private SparseArray<ScheduledFuture<?>> jdField_a_of_type_AndroidUtilSparseArray = null;
    private ScheduledExecutorService jdField_a_of_type_JavaUtilConcurrentScheduledExecutorService = null;
    
    public final void a(int paramInt)
    {
      try
      {
        ScheduledFuture localScheduledFuture = (ScheduledFuture)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramInt);
        if ((localScheduledFuture != null) && (!localScheduledFuture.isCancelled()))
        {
          a.b("cancel a old future!", new Object[0]);
          localScheduledFuture.cancel(true);
        }
        this.jdField_a_of_type_AndroidUtilSparseArray.remove(paramInt);
        return;
      }
      finally {}
    }
    
    public final void a(int paramInt, Runnable paramRunnable, long paramLong1, long paramLong2)
    {
      if (paramRunnable == null) {
        try
        {
          a.d("task runner should not be null", new Object[0]);
          return;
        }
        finally
        {
          break label123;
        }
      }
      long l;
      if (paramLong1 > 0L) {
        l = paramLong1;
      } else {
        l = 0L;
      }
      if (jdField_a_of_type_Boolean)
      {
        paramLong1 = 10000L;
        if (paramLong2 > 10000L) {
          paramLong1 = paramLong2;
        }
      }
      for (;;)
      {
        a(paramInt);
        paramRunnable = this.jdField_a_of_type_JavaUtilConcurrentScheduledExecutorService.scheduleAtFixedRate(paramRunnable, l, paramLong1, TimeUnit.MILLISECONDS);
        if (paramRunnable != null)
        {
          a.b("add a new future! taskId: %d , periodTime: %d", new Object[] { Integer.valueOf(paramInt), Long.valueOf(paramLong1) });
          this.jdField_a_of_type_AndroidUtilSparseArray.put(paramInt, paramRunnable);
        }
        return;
        label123:
        throw paramRunnable;
        continue;
        paramLong1 = paramLong2;
      }
    }
    
    public final void a(Runnable paramRunnable)
    {
      if (paramRunnable == null) {}
      try
      {
        a.d("task runner should not be null", new Object[0]);
        return;
      }
      finally {}
      this.jdField_a_of_type_JavaUtilConcurrentScheduledExecutorService.execute(paramRunnable);
    }
    
    public final void a(Runnable paramRunnable, long paramLong)
    {
      if (paramRunnable == null) {
        try
        {
          a.d("task runner should not be null", new Object[0]);
          return;
        }
        finally
        {
          break label51;
        }
      }
      if (paramLong <= 0L) {
        paramLong = 0L;
      }
      this.jdField_a_of_type_JavaUtilConcurrentScheduledExecutorService.schedule(paramRunnable, paramLong, TimeUnit.MILLISECONDS);
      return;
      label51:
      throw paramRunnable;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */