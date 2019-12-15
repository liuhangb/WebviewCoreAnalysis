package com.tencent.smtt.util;

import android.os.Handler;
import android.os.Looper;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.smtt.webkit.ReadModeManager;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.tbsshell.common.readmode.IWebpageReadModePluginLoadCallback;

public class r
{
  private static r jdField_a_of_type_ComTencentSmttUtilR;
  private static String jdField_a_of_type_JavaLangString = "X5WebpageReadModePluginManager";
  private Handler jdField_a_of_type_AndroidOsHandler = null;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private boolean jdField_a_of_type_Boolean = false;
  private String b = null;
  
  private Handler a()
  {
    Handler localHandler = this.jdField_a_of_type_AndroidOsHandler;
    if (localHandler != null) {
      return localHandler;
    }
    if (localHandler == null) {
      this.jdField_a_of_type_AndroidOsHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime());
    }
    return this.jdField_a_of_type_AndroidOsHandler;
  }
  
  public static r a()
  {
    if (jdField_a_of_type_ComTencentSmttUtilR == null) {
      jdField_a_of_type_ComTencentSmttUtilR = new r();
    }
    return jdField_a_of_type_ComTencentSmttUtilR;
  }
  
  private void a(int paramInt) {}
  
  private void a(final int paramInt, final String paramString)
  {
    if (a().getLooper() != Looper.myLooper())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          r.a(r.this, paramInt, paramString);
        }
      });
      return;
    }
    this.jdField_a_of_type_Boolean = false;
  }
  
  private void a(final String paramString)
  {
    if (a().getLooper() != Looper.myLooper())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          r.a(r.this, paramString);
        }
      });
      return;
    }
    this.jdField_a_of_type_Boolean = false;
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.b = paramString;
      ReadModeManager.a(paramString);
      return;
    }
  }
  
  private void b(int paramInt) {}
  
  public void a()
  {
    if (a().getLooper() != Looper.myLooper())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          r.this.a();
        }
      });
      return;
    }
    if (this.jdField_a_of_type_Boolean == true) {
      return;
    }
    this.jdField_a_of_type_Boolean = true;
    SmttServiceProxy.getInstance().loadWebpageReadModePlugin(new IWebpageReadModePluginLoadCallback()
    {
      public void onPluginLoadFailed(int paramAnonymousInt, String paramAnonymousString)
      {
        r.a(r.this, paramAnonymousInt, paramAnonymousString);
      }
      
      public void onPluginLoadProgress(int paramAnonymousInt)
      {
        r.b(r.this, paramAnonymousInt);
      }
      
      public void onPluginLoadStarted(int paramAnonymousInt)
      {
        r.a(r.this, paramAnonymousInt);
      }
      
      public void onPluginLoadSuccess(String paramAnonymousString)
      {
        r.a(r.this, paramAnonymousString);
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\r.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */