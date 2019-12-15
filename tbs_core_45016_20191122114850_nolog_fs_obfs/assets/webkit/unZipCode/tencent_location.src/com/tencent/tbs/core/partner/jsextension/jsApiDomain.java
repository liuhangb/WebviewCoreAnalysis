package com.tencent.tbs.core.partner.jsextension;

import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import dalvik.system.DexClassLoader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import org.json.JSONObject;

public class jsApiDomain
{
  private static final String KEY_USE_CACHE = "useCachedApi";
  private static jsApiDomain mInstance;
  private String JSAPI_CLASSNAME = "com.tencent.smtt.jsApi.impl.jsApiDomainWhiteListManager";
  
  public static jsApiDomain getInstance()
  {
    if (mInstance == null) {
      mInstance = new jsApiDomain();
    }
    return mInstance;
  }
  
  private Object getJsApiInstance()
  {
    if (ContextHolder.getInstance().getDexClassLoader() != null) {
      return invokeStaticMethod(this.JSAPI_CLASSNAME, "getInstance", new Class[0], new Object[0]);
    }
    return null;
  }
  
  private static Method getParentDeclaredMethod(Object paramObject, String paramString, Class<?>... paramVarArgs)
  {
    for (paramObject = paramObject.getClass(); paramObject != Object.class; paramObject = ((Class)paramObject).getSuperclass())
    {
      if (paramObject == null) {
        return null;
      }
      try
      {
        Method localMethod = ((Class)paramObject).getDeclaredMethod(paramString, paramVarArgs);
        return localMethod;
      }
      catch (Exception localException)
      {
        for (;;) {}
      }
    }
    return null;
  }
  
  private static Object invokeInstance(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramObject == null) {
      return null;
    }
    try
    {
      Class localClass = paramObject.getClass();
      if (Build.VERSION.SDK_INT > 10) {
        paramString = localClass.getMethod(paramString, paramArrayOfClass);
      } else {
        paramString = localClass.getDeclaredMethod(paramString, paramArrayOfClass);
      }
      paramString.setAccessible(true);
      paramArrayOfClass = paramVarArgs;
      if (paramVarArgs.length == 0) {
        paramArrayOfClass = null;
      }
      paramObject = paramString.invoke(paramObject, paramArrayOfClass);
      return paramObject;
    }
    catch (Throwable paramString)
    {
      paramObject = new StringWriter();
      paramString.printStackTrace(new PrintWriter((Writer)paramObject));
      paramString = new StringBuilder();
      paramString.append("invokeInstance -- exceptions:");
      paramString.append(((StringWriter)paramObject).toString());
      Log.e("ReflectionUtils", paramString.toString());
    }
    return null;
  }
  
  private void invokeJsApiDomainUpdate(String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    Object localObject = getJsApiInstance();
    if (localObject == null) {
      return;
    }
    invokeInstance(localObject, paramString, paramArrayOfClass, paramVarArgs);
  }
  
  private Object invokeStaticMethod(String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (ContextHolder.getInstance().getDexClassLoader() == null) {
      return null;
    }
    try
    {
      paramArrayOfClass = ContextHolder.getInstance().getDexClassLoader().loadClass(paramString1).getMethod(paramString2, paramArrayOfClass);
      paramArrayOfClass.setAccessible(true);
      paramArrayOfClass = paramArrayOfClass.invoke(null, paramVarArgs);
      return paramArrayOfClass;
    }
    catch (Throwable paramArrayOfClass)
    {
      paramVarArgs = getClass().getSimpleName();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("'");
      localStringBuilder.append(paramString1);
      localStringBuilder.append("' invoke static method '");
      localStringBuilder.append(paramString2);
      localStringBuilder.append("' failed");
      Log.e(paramVarArgs, localStringBuilder.toString(), paramArrayOfClass);
    }
    return null;
  }
  
  public String onReady(String paramString1, String paramString2, TencentWebViewProxy paramTencentWebViewProxy)
  {
    Log.e("jsapiDomain", "onReady");
    String str1;
    if (paramTencentWebViewProxy != null) {
      str1 = paramTencentWebViewProxy.getRealWebView().getUrl();
    } else {
      str1 = null;
    }
    if (TextUtils.isEmpty(str1)) {
      return "host error";
    }
    Object localObject = "true";
    try
    {
      String str2 = new JSONObject(paramString1).optString("useCachedApi");
      localObject = str2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    invokeJsApiDomainUpdate("onReady", new Class[] { Object.class, String.class, String.class, String.class, String.class }, new Object[] { paramTencentWebViewProxy, paramString1, str1, paramString2, localObject });
    return "ok";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\jsextension\jsApiDomain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */