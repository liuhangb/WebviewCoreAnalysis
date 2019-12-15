package com.tencent.smtt.util;

import android.os.Handler;
import android.os.Looper;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.smtt.webkit.f;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.tbsshell.common.errorpage.IWebErrorPagePluginLoadCallback;

public class p
{
  private static p jdField_a_of_type_ComTencentSmttUtilP;
  private static String jdField_a_of_type_JavaLangString = "X5WebErrorPagePluginManager";
  private Handler jdField_a_of_type_AndroidOsHandler = null;
  private Boolean jdField_a_of_type_JavaLangBoolean = Boolean.valueOf(false);
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private String b = null;
  
  private Handler a()
  {
    if (this.jdField_a_of_type_AndroidOsHandler == null) {
      this.jdField_a_of_type_AndroidOsHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime());
    }
    return this.jdField_a_of_type_AndroidOsHandler;
  }
  
  public static p a()
  {
    if (jdField_a_of_type_ComTencentSmttUtilP == null) {
      jdField_a_of_type_ComTencentSmttUtilP = new p();
    }
    return jdField_a_of_type_ComTencentSmttUtilP;
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
          p.a(p.this, paramInt, paramString);
        }
      });
      return;
    }
    String str = jdField_a_of_type_JavaLangString;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onWebErrorPagePluginLoadFailed error: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(", message: ");
    localStringBuilder.append(paramString);
    X5Log.i(str, localStringBuilder.toString());
    f.a();
  }
  
  private void a(final String paramString)
  {
    if (a().getLooper() != Looper.myLooper())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          p.a(p.this, paramString);
        }
      });
      return;
    }
    ??? = jdField_a_of_type_JavaLangString;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onWebErrorPagePluginLoadSuccess pluginPath: ");
    localStringBuilder.append(paramString);
    X5Log.i((String)???, localStringBuilder.toString());
    this.jdField_a_of_type_JavaLangBoolean = Boolean.valueOf(false);
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.b = paramString;
      f.a(paramString);
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
          p.this.a();
        }
      });
      return;
    }
    if (this.jdField_a_of_type_JavaLangBoolean.booleanValue() == true) {
      return;
    }
    this.jdField_a_of_type_JavaLangBoolean = Boolean.valueOf(true);
    SmttServiceProxy.getInstance().loadWebErrorPagePlugin(new IWebErrorPagePluginLoadCallback()
    {
      public void onPluginLoadFailed(int paramAnonymousInt, String paramAnonymousString)
      {
        p.a(p.this, paramAnonymousInt, paramAnonymousString);
      }
      
      public void onPluginLoadProgress(int paramAnonymousInt)
      {
        p.b(p.this, paramAnonymousInt);
      }
      
      public void onPluginLoadStarted(int paramAnonymousInt)
      {
        p.a(p.this, paramAnonymousInt);
      }
      
      public void onPluginLoadSuccess(String paramAnonymousString)
      {
        p.a(p.this, paramAnonymousString);
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\p.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */