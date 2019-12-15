package com.tencent.smtt.jsApi.export;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.JavascriptInterface;
import dalvik.system.DexClassLoader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class OpenJsApiBridge
  implements IJsHelperDestroyCallback
{
  private Context jdField_a_of_type_AndroidContentContext;
  private DexClassLoader jdField_a_of_type_DalvikSystemDexClassLoader;
  private Object jdField_a_of_type_JavaLangObject;
  private ArrayList<IWebviewNotifyListener> jdField_a_of_type_JavaUtilArrayList;
  private HashMap<String, IOpenJsApis> jdField_a_of_type_JavaUtilHashMap;
  private Context jdField_b_of_type_AndroidContentContext;
  private Object jdField_b_of_type_JavaLangObject;
  
  public OpenJsApiBridge(Object paramObject1, Context paramContext1, Context paramContext2, Object paramObject2)
  {
    this.jdField_a_of_type_JavaLangObject = paramObject1;
    this.jdField_a_of_type_AndroidContentContext = paramContext1;
    this.jdField_b_of_type_AndroidContentContext = paramContext2;
    this.jdField_a_of_type_JavaUtilHashMap = new HashMap();
    if (paramObject2 != null) {
      this.jdField_a_of_type_DalvikSystemDexClassLoader = ((DexClassLoader)paramObject2);
    }
    this.jdField_b_of_type_JavaLangObject = a("com.tencent.smtt.jsApi.impl.OpenJsApisService", "createInstance", new Class[0], new Object[0]);
  }
  
  private Object a(Object paramObject, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    if (paramObject == null) {
      return null;
    }
    try
    {
      paramArrayOfClass = this.jdField_a_of_type_DalvikSystemDexClassLoader.loadClass(paramString1).getMethod(paramString2, paramArrayOfClass);
      paramArrayOfClass.setAccessible(true);
      paramObject = paramArrayOfClass.invoke(paramObject, paramVarArgs);
      return paramObject;
    }
    catch (Throwable paramObject)
    {
      paramArrayOfClass = new StringBuilder();
      paramArrayOfClass.append("'");
      paramArrayOfClass.append(paramString1);
      paramArrayOfClass.append("' invoke static method '");
      paramArrayOfClass.append(paramString2);
      paramArrayOfClass.append("' failed");
      Log.e("invokeStaticMethod", paramArrayOfClass.toString(), (Throwable)paramObject);
    }
    return null;
  }
  
  private Object a(String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramArrayOfClass = this.jdField_a_of_type_DalvikSystemDexClassLoader.loadClass(paramString1).getMethod(paramString2, paramArrayOfClass);
      paramArrayOfClass.setAccessible(true);
      paramArrayOfClass = paramArrayOfClass.invoke(null, paramVarArgs);
      return paramArrayOfClass;
    }
    catch (Throwable paramArrayOfClass)
    {
      paramVarArgs = new StringBuilder();
      paramVarArgs.append("'");
      paramVarArgs.append(paramString1);
      paramVarArgs.append("' invoke static method '");
      paramVarArgs.append(paramString2);
      paramVarArgs.append("' failed");
      Log.e("invokeStaticMethod", paramVarArgs.toString(), paramArrayOfClass);
    }
    return null;
  }
  
  public void destory()
  {
    if (this.jdField_a_of_type_DalvikSystemDexClassLoader != null) {
      a(this.jdField_b_of_type_JavaLangObject, "com.tencent.smtt.jsApi.impl.OpenJsApisService", "destory", new Class[0], new Object[0]);
    }
  }
  
  public IOpenJsApis getService(String paramString)
  {
    IOpenJsApis localIOpenJsApis = (IOpenJsApis)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
    if (localIOpenJsApis != null) {
      return localIOpenJsApis;
    }
    Object localObject2 = this.jdField_a_of_type_DalvikSystemDexClassLoader;
    Object localObject1 = localIOpenJsApis;
    if (localObject2 != null)
    {
      localObject1 = this.jdField_b_of_type_JavaLangObject;
      Object localObject3 = this.jdField_a_of_type_JavaLangObject;
      Context localContext1 = this.jdField_a_of_type_AndroidContentContext;
      Context localContext2 = this.jdField_b_of_type_AndroidContentContext;
      ArrayList localArrayList = this.jdField_a_of_type_JavaUtilArrayList;
      localObject2 = a(localObject1, "com.tencent.smtt.jsApi.impl.OpenJsApisService", "initService", new Class[] { String.class, Object.class, Object.class, Context.class, Context.class, ArrayList.class }, new Object[] { paramString, localObject3, localObject2, localContext1, localContext2, localArrayList });
      localObject1 = localIOpenJsApis;
      if (localObject2 != null)
      {
        localObject1 = localIOpenJsApis;
        if ((localObject2 instanceof IOpenJsApis)) {
          localObject1 = (IOpenJsApis)localObject2;
        }
      }
    }
    if (localObject1 != null) {
      this.jdField_a_of_type_JavaUtilHashMap.put(paramString, localObject1);
    }
    return (IOpenJsApis)localObject1;
  }
  
  @JavascriptInterface
  public String nativeExec(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("OpenJsApiBridge--serviceName = ");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", action = ");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(", callbackid = ");
    ((StringBuilder)localObject).append(paramString3);
    ((StringBuilder)localObject).append(", args = ");
    ((StringBuilder)localObject).append(paramString4);
    ((StringBuilder)localObject).append(", originUrl = ");
    ((StringBuilder)localObject).append(paramString5);
    Log.d("OpenJsApiBridge", ((StringBuilder)localObject).toString());
    localObject = getService(paramString1);
    paramString1 = null;
    if (localObject == null) {
      return null;
    }
    if (!TextUtils.isEmpty(paramString4)) {
      try
      {
        paramString1 = new JSONObject(paramString4);
      }
      catch (JSONException paramString1)
      {
        paramString1.printStackTrace();
        return null;
      }
    }
    return ((IOpenJsApis)localObject).exec(paramString2, paramString3, paramString1, paramString5);
  }
  
  public void onWebViewDestroyed()
  {
    Iterator localIterator = this.jdField_a_of_type_JavaUtilHashMap.values().iterator();
    while (localIterator.hasNext()) {
      ((IOpenJsApis)localIterator.next()).destroy();
    }
  }
  
  public void setWebviewListener(ArrayList<IWebviewNotifyListener> paramArrayList)
  {
    this.jdField_a_of_type_JavaUtilArrayList = paramArrayList;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\export\OpenJsApiBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */