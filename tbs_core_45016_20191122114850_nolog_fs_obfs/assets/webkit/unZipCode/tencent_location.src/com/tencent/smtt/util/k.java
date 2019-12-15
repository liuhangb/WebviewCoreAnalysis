package com.tencent.smtt.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.webkit.ValueCallback;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.WebViewChromiumExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.tencent.TencentCacheManager;
import com.tencent.tbs.core.webkit.tencent.TencentCacheManager.CacheResult;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Map;

public class k
  implements ValueCallback<Map<String, String>>
{
  private static Handler jdField_a_of_type_AndroidOsHandler;
  private static Looper jdField_a_of_type_AndroidOsLooper;
  private static k jdField_a_of_type_ComTencentSmttUtilK;
  private int jdField_a_of_type_Int = 0;
  private SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences = null;
  private String jdField_a_of_type_JavaLangString = "";
  private WeakReference<WebViewChromiumExtension> jdField_a_of_type_JavaLangRefWeakReference = null;
  
  private k()
  {
    d();
  }
  
  public static k a()
  {
    if (jdField_a_of_type_ComTencentSmttUtilK == null) {
      try
      {
        if (jdField_a_of_type_ComTencentSmttUtilK == null) {
          jdField_a_of_type_ComTencentSmttUtilK = new k();
        }
      }
      finally {}
    }
    return jdField_a_of_type_ComTencentSmttUtilK;
  }
  
  private void d()
  {
    jdField_a_of_type_AndroidOsLooper = BrowserExecutorSupplier.getLooperForRunShortTime();
    jdField_a_of_type_AndroidOsHandler = new Handler(jdField_a_of_type_AndroidOsLooper)
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        switch (paramAnonymousMessage.what)
        {
        default: 
          return;
        case 3: 
          k.this.c();
          return;
        case 2: 
          paramAnonymousMessage = (Map)paramAnonymousMessage.obj;
          k.this.c(paramAnonymousMessage);
          return;
        }
        k.this.a();
      }
    };
    Message localMessage = jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 1;
    jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public int a()
  {
    try
    {
      if (this.jdField_a_of_type_AndroidContentSharedPreferences == null) {
        this.jdField_a_of_type_AndroidContentSharedPreferences = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
      }
      int i = this.jdField_a_of_type_AndroidContentSharedPreferences.getInt("smart_box_search_video_normal_display_count", 0);
      return i;
    }
    catch (Exception localException) {}
    return 0;
  }
  
  public String a(byte[] paramArrayOfByte)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
    int j = paramArrayOfByte.length;
    int i = 0;
    while (i < j)
    {
      int k = paramArrayOfByte[i] & 0xFF;
      if (k < 16) {
        localStringBuffer.append("0");
      }
      localStringBuffer.append(Long.toString(k, 16));
      i += 1;
    }
    return localStringBuffer.toString();
  }
  
  public void a()
  {
    SmttServiceProxy.getInstance().setQBPageVideoSearchWupCallback(this);
    this.jdField_a_of_type_Int = a();
  }
  
  public void a(WebViewChromiumExtension paramWebViewChromiumExtension)
  {
    this.jdField_a_of_type_JavaLangRefWeakReference = new WeakReference(paramWebViewChromiumExtension);
  }
  
  public void a(Map<String, String> paramMap)
  {
    Object localObject = this.jdField_a_of_type_JavaLangRefWeakReference;
    if ((localObject != null) && (((WeakReference)localObject).get() != null))
    {
      localObject = (String)paramMap.get("mainUrl");
      if (localObject != null)
      {
        if (((String)localObject).equals("")) {
          return;
        }
        if ((!a()) && ((this.jdField_a_of_type_JavaLangString.equals("")) || (!this.jdField_a_of_type_JavaLangString.equals(localObject))))
        {
          this.jdField_a_of_type_JavaLangString = ((String)localObject);
          this.jdField_a_of_type_Int += 1;
          b();
        }
        if (a()) {
          paramMap.put("displayNormal", "true");
        } else {
          paramMap.put("displayNormal", "false");
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("Options=SearchResult; map=");
        ((StringBuilder)localObject).append(paramMap.toString());
        X5Log.i("SMARTBOX", ((StringBuilder)localObject).toString());
        ((WebViewChromiumExtension)this.jdField_a_of_type_JavaLangRefWeakReference.get()).OnQBVideoSearchResult(paramMap);
        return;
      }
      return;
    }
  }
  
  public boolean a()
  {
    return this.jdField_a_of_type_Int > 2;
  }
  
  public void b()
  {
    Message localMessage = jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 3;
    jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public void b(Map<String, String> paramMap)
  {
    Message localMessage = jdField_a_of_type_AndroidOsHandler.obtainMessage();
    localMessage.what = 2;
    localMessage.obj = paramMap;
    jdField_a_of_type_AndroidOsHandler.sendMessage(localMessage);
  }
  
  public void c()
  {
    try
    {
      if (this.jdField_a_of_type_AndroidContentSharedPreferences == null) {
        this.jdField_a_of_type_AndroidContentSharedPreferences = ContextHolder.getInstance().getApplicationContext().getSharedPreferences("x5_proxy_setting", 0);
      }
      int i = this.jdField_a_of_type_AndroidContentSharedPreferences.getInt("smart_box_search_video_normal_display_count", 0);
      SharedPreferences.Editor localEditor = this.jdField_a_of_type_AndroidContentSharedPreferences.edit();
      localEditor.putInt("smart_box_search_video_normal_display_count", i + 1);
      localEditor.commit();
      return;
    }
    catch (Exception localException) {}
  }
  
  public void c(Map<String, String> paramMap)
  {
    try
    {
      String str = (String)paramMap.get("mainUrl");
      if (TextUtils.isEmpty(str)) {
        return;
      }
      str = new URL(str).getHost();
      if (!SmttServiceProxy.getInstance().shouldReportSearchVideoForDomain(str)) {
        return;
      }
      str = (String)paramMap.get("imageUrl");
      Object localObject1 = TencentCacheManager.getCacheResult(str);
      ((TencentCacheManager.CacheResult)localObject1).getLocalPath();
      localObject1 = ((TencentCacheManager.CacheResult)localObject1).inStream;
      ((InputStream)localObject1).available();
      Object localObject2 = MessageDigest.getInstance("MD5");
      byte[] arrayOfByte = new byte['က'];
      int i = 0;
      while (i != -1)
      {
        i = ((InputStream)localObject1).read(arrayOfByte);
        ((MessageDigest)localObject2).update(arrayOfByte);
      }
      localObject1 = a(((MessageDigest)localObject2).digest());
      if (((String)localObject1).isEmpty())
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("Error=md5NULL; url=");
        ((StringBuilder)localObject2).append(str);
        X5Log.i("SmartBox", ((StringBuilder)localObject2).toString());
      }
      paramMap.put("md5", localObject1);
      SmttServiceProxy.getInstance().reportPageImageInfo(paramMap);
      return;
    }
    catch (Exception paramMap)
    {
      for (;;) {}
    }
    X5Log.i("SmartBox", "Error=md5Exception");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\k.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */