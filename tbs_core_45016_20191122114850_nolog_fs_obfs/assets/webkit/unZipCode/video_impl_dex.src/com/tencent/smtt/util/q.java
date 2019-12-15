package com.tencent.smtt.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.tbsshell.common.webrtc.IWebRtcPluginLoadCallback;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import org.chromium.base.annotations.JNINamespace;
import org.json.JSONException;
import org.json.JSONObject;

@JNINamespace("tencent")
public class q
{
  private static q jdField_a_of_type_ComTencentSmttUtilQ;
  private static String jdField_a_of_type_JavaLangString = "X5WebRtcPluginManager";
  private Handler jdField_a_of_type_AndroidOsHandler = null;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private ArrayList<WeakReference<X5WebRTCJsHelper.IX5WebRTCPluginLoadCallback>> jdField_a_of_type_JavaUtilArrayList = new ArrayList();
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
  
  public static q a()
  {
    if (jdField_a_of_type_ComTencentSmttUtilQ == null) {
      jdField_a_of_type_ComTencentSmttUtilQ = new q();
    }
    return jdField_a_of_type_ComTencentSmttUtilQ;
  }
  
  private void a(final int paramInt)
  {
    if (a().getLooper() != Looper.myLooper())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          q.a(q.this, paramInt);
        }
      });
      return;
    }
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("size", paramInt);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    a("LOAD_START", localJSONObject, false);
  }
  
  private void a(final int paramInt, final String paramString)
  {
    if (a().getLooper() != Looper.myLooper())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          q.a(q.this, paramInt, paramString);
        }
      });
      return;
    }
    this.jdField_a_of_type_Boolean = false;
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("retCode", paramInt);
      localJSONObject.put("retMsg", paramString);
    }
    catch (JSONException paramString)
    {
      paramString.printStackTrace();
    }
    a("LOAD_FAILED", localJSONObject, true);
  }
  
  private void a(final String paramString)
  {
    if (a().getLooper() != Looper.myLooper())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          q.a(q.this, paramString);
        }
      });
      return;
    }
    this.jdField_a_of_type_Boolean = false;
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.b = paramString;
      paramString = new JSONObject();
      try
      {
        paramString.put("retCode", 0);
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      a("LOAD_SUCCESS", paramString, true);
      return;
    }
  }
  
  private void a(String paramString, JSONObject paramJSONObject, boolean paramBoolean)
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilArrayList.iterator();
    while (localIterator.hasNext())
    {
      JSONObject localJSONObject = new JSONObject();
      try
      {
        localJSONObject.put("status", paramString);
        localJSONObject.put("result", paramJSONObject);
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
      }
      WeakReference localWeakReference = (WeakReference)localIterator.next();
      if (localWeakReference.get() != null) {
        ((X5WebRTCJsHelper.IX5WebRTCPluginLoadCallback)localWeakReference.get()).onLodingCallback(localJSONObject.toString());
      }
      if (paramBoolean) {
        localIterator.remove();
      }
    }
  }
  
  private void b(final int paramInt)
  {
    if (a().getLooper() != Looper.myLooper())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          q.b(q.this, paramInt);
        }
      });
      return;
    }
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("progress", paramInt);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    a("LOAD_PROGRESS", localJSONObject, false);
  }
  
  public String a()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      String str = this.b;
      ??? = str;
      if (TextUtils.isEmpty(str))
      {
        ??? = new StringBuilder();
        ((StringBuilder)???).append(ContextHolder.getInstance().getApplicationContext().getFilesDir().getAbsolutePath());
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append("plugins");
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append("com.tencent.qb.m66.plugin.webrtc");
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append("libmttwebrtc.so");
        ??? = ((StringBuilder)???).toString();
      }
      return (String)???;
    }
  }
  
  public void a()
  {
    if (a().getLooper() != Looper.myLooper())
    {
      a().postDelayed(new Runnable()
      {
        public void run()
        {
          if (!SmttServiceProxy.getInstance().isWebRTCPluginAutoDownloadNotAllowed()) {
            q.this.a(null);
          }
        }
      }, 60000L);
      return;
    }
  }
  
  public void a(final WeakReference<X5WebRTCJsHelper.IX5WebRTCPluginLoadCallback> paramWeakReference)
  {
    if (a().getLooper() != Looper.myLooper())
    {
      a().post(new Runnable()
      {
        public void run()
        {
          q.this.a(paramWeakReference);
        }
      });
      return;
    }
    if ((paramWeakReference != null) && (paramWeakReference.get() != null)) {
      this.jdField_a_of_type_JavaUtilArrayList.add(paramWeakReference);
    }
    if (this.jdField_a_of_type_Boolean == true) {
      return;
    }
    this.jdField_a_of_type_Boolean = true;
    SmttServiceProxy.getInstance().loadWebRtcPlugin(new IWebRtcPluginLoadCallback()
    {
      public void onPluginLoadFailed(int paramAnonymousInt, String paramAnonymousString)
      {
        q.a(q.this, paramAnonymousInt, paramAnonymousString);
      }
      
      public void onPluginLoadProgress(int paramAnonymousInt)
      {
        q.b(q.this, paramAnonymousInt);
      }
      
      public void onPluginLoadStarted(int paramAnonymousInt)
      {
        q.a(q.this, paramAnonymousInt);
      }
      
      public void onPluginLoadSuccess(String paramAnonymousString)
      {
        q.a(q.this, paramAnonymousString);
      }
    });
  }
  
  public boolean a()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      String str = this.b;
      ??? = str;
      if (TextUtils.isEmpty(str))
      {
        ??? = new StringBuilder();
        ((StringBuilder)???).append(ContextHolder.getInstance().getApplicationContext().getFilesDir().getAbsolutePath());
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append("plugins");
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append("com.tencent.qb.m66.plugin.webrtc");
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append("libmttwebrtc.so");
        ??? = ((StringBuilder)???).toString();
      }
      return new File((String)???).exists();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\q.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */