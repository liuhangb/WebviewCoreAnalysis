package com.tencent.downloadprovider;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.HashMap;
import java.util.Map;

public final class d
{
  private Handler.Callback jdField_a_of_type_AndroidOsHandler$Callback;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private final Map<Integer, a> jdField_a_of_type_JavaUtilMap = new HashMap();
  
  public d(Looper paramLooper, Handler.Callback paramCallback)
  {
    this.jdField_a_of_type_AndroidOsHandler$Callback = paramCallback;
    this.jdField_a_of_type_AndroidOsHandler = new Handler(paramLooper, new Handler.Callback()
    {
      public boolean handleMessage(Message paramAnonymousMessage)
      {
        return d.a(d.this, paramAnonymousMessage);
      }
    });
  }
  
  private long a()
  {
    return System.currentTimeMillis();
  }
  
  private boolean b(Message paramMessage)
  {
    boolean bool1 = false;
    if (paramMessage == null) {
      return false;
    }
    int i = paramMessage.what;
    synchronized (this.jdField_a_of_type_JavaUtilMap)
    {
      a locala = (a)this.jdField_a_of_type_JavaUtilMap.remove(Integer.valueOf(i));
      if (locala != null) {
        locala.d = a();
      }
      ??? = this.jdField_a_of_type_AndroidOsHandler$Callback;
      if (??? != null)
      {
        boolean bool2 = ((Handler.Callback)???).handleMessage(paramMessage);
        bool1 = bool2;
        if (locala != null)
        {
          locala.b = a();
          bool1 = bool2;
        }
      }
      if (locala != null) {
        locala.b();
      }
      return bool1;
    }
  }
  
  public final Handler a()
  {
    return this.jdField_a_of_type_AndroidOsHandler;
  }
  
  public void a(int paramInt)
  {
    synchronized (this.jdField_a_of_type_JavaUtilMap)
    {
      this.jdField_a_of_type_JavaUtilMap.remove(Integer.valueOf(paramInt));
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(paramInt);
      return;
    }
  }
  
  public boolean a(Message paramMessage)
  {
    return a(paramMessage, 0L);
  }
  
  public boolean a(Message paramMessage, long paramLong)
  {
    synchronized (this.jdField_a_of_type_JavaUtilMap)
    {
      a locala = new a(paramMessage.what);
      locala.a = a();
      locala.c = (locala.a + paramLong);
      this.jdField_a_of_type_JavaUtilMap.put(Integer.valueOf(paramMessage.what), locala);
      return this.jdField_a_of_type_AndroidOsHandler.sendMessageDelayed(paramMessage, paramLong);
    }
  }
  
  public boolean a(Runnable paramRunnable)
  {
    return this.jdField_a_of_type_AndroidOsHandler.post(new b(paramRunnable, 0L));
  }
  
  private class a
  {
    int jdField_a_of_type_Int = 64512;
    long jdField_a_of_type_Long;
    Runnable jdField_a_of_type_JavaLangRunnable;
    boolean jdField_a_of_type_Boolean = true;
    long b;
    long c;
    long d;
    
    a(int paramInt)
    {
      this.jdField_a_of_type_Int = paramInt;
    }
    
    a(Runnable paramRunnable)
    {
      this.jdField_a_of_type_JavaLangRunnable = paramRunnable;
      this.jdField_a_of_type_Boolean = false;
    }
    
    void a()
    {
      this.jdField_a_of_type_JavaLangRunnable = null;
    }
    
    void b()
    {
      StringBuilder localStringBuilder1 = new StringBuilder();
      localStringBuilder1.append("WATCH_HANDLER:");
      localStringBuilder1.append(Thread.currentThread().getName());
      String str = localStringBuilder1.toString();
      StringBuilder localStringBuilder2 = new StringBuilder();
      localStringBuilder2.append("Watch ");
      if (this.jdField_a_of_type_Boolean)
      {
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("w=[");
        localStringBuilder1.append(this.jdField_a_of_type_Int);
      }
      else
      {
        localStringBuilder1 = new StringBuilder();
        localStringBuilder1.append("r=[");
        localStringBuilder1.append(this.jdField_a_of_type_JavaLangRunnable);
      }
      localStringBuilder1.append("], ");
      localStringBuilder2.append(localStringBuilder1.toString());
      localStringBuilder2.append("cre_t=[");
      localStringBuilder2.append(this.jdField_a_of_type_Long);
      localStringBuilder2.append("], d_ss_t=[");
      localStringBuilder2.append(this.c - this.jdField_a_of_type_Long);
      localStringBuilder2.append("], d_rs_t=[");
      localStringBuilder2.append(this.d - this.jdField_a_of_type_Long);
      localStringBuilder2.append("], d_rf_t=[");
      localStringBuilder2.append(this.b - this.d);
      localStringBuilder2.append("]");
      Log.d(str, localStringBuilder2.toString());
    }
  }
  
  private class b
    implements Runnable
  {
    d.a jdField_a_of_type_ComTencentDownloadproviderD$a;
    Runnable jdField_a_of_type_JavaLangRunnable;
    
    b(Runnable paramRunnable, long paramLong)
    {
      this.jdField_a_of_type_JavaLangRunnable = paramRunnable;
      this.jdField_a_of_type_ComTencentDownloadproviderD$a = new d.a(d.this, paramRunnable);
      this.jdField_a_of_type_ComTencentDownloadproviderD$a.a = d.a(d.this);
      this$1 = this.jdField_a_of_type_ComTencentDownloadproviderD$a;
      d.this.c = (d.this.a + paramLong);
    }
    
    public void run()
    {
      this.jdField_a_of_type_ComTencentDownloadproviderD$a.d = d.a(d.this);
      Runnable localRunnable = this.jdField_a_of_type_JavaLangRunnable;
      if (localRunnable != null) {
        localRunnable.run();
      }
      this.jdField_a_of_type_ComTencentDownloadproviderD$a.b = d.a(d.this);
      this.jdField_a_of_type_ComTencentDownloadproviderD$a.b();
      this.jdField_a_of_type_ComTencentDownloadproviderD$a.a();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\downloadprovider\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */