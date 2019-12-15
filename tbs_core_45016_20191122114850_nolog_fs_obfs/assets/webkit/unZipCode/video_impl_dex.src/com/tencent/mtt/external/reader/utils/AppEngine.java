package com.tencent.mtt.external.reader.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuResources;
import java.lang.reflect.Method;

public class AppEngine
{
  private static AppEngine jdField_a_of_type_ComTencentMttExternalReaderUtilsAppEngine = new AppEngine();
  private Context jdField_a_of_type_AndroidContentContext = null;
  private String jdField_a_of_type_JavaLangString = "";
  
  public static AppEngine getInstance()
  {
    return jdField_a_of_type_ComTencentMttExternalReaderUtilsAppEngine;
  }
  
  public Context getApplicationContext()
  {
    return this.jdField_a_of_type_AndroidContentContext;
  }
  
  public String getTempPath()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public Object invokeMethod(ClassLoader paramClassLoader, Object paramObject, String paramString1, String paramString2, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramClassLoader = paramClassLoader.loadClass(paramString1).getMethod(paramString2, paramArrayOfClass);
      paramClassLoader.setAccessible(true);
      paramClassLoader = paramClassLoader.invoke(paramObject, paramVarArgs);
      return paramClassLoader;
    }
    catch (Throwable paramClassLoader)
    {
      paramObject = new StringBuilder();
      ((StringBuilder)paramObject).append("'");
      ((StringBuilder)paramObject).append(paramString1);
      ((StringBuilder)paramObject).append("' invoke method '");
      ((StringBuilder)paramObject).append(paramString2);
      ((StringBuilder)paramObject).append("' failed");
      Log.e("AppEngine", ((StringBuilder)paramObject).toString(), paramClassLoader);
    }
    return null;
  }
  
  public boolean isSetPkg()
  {
    if (getInstance().getApplicationContext() == null) {
      return false;
    }
    String str = getInstance().getApplicationContext().getPackageName();
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    String[] arrayOfString = MenuResources.getStringArray("name_cannot_set_menu_show_package_name");
    if (arrayOfString == null) {
      return false;
    }
    int i = 0;
    while (i < arrayOfString.length)
    {
      if (str.equalsIgnoreCase(arrayOfString[i])) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public boolean isSetServicePkg()
  {
    if (getInstance().getApplicationContext() == null) {
      return false;
    }
    String str = getInstance().getApplicationContext().getPackageName();
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    Object localObject = null;
    try
    {
      String[] arrayOfString = MenuResources.getStringArray("name_set_menu_service_package_name");
      localObject = arrayOfString;
    }
    catch (Exception localException)
    {
      int i;
      for (;;) {}
    }
    if (localObject == null) {
      return false;
    }
    i = 0;
    while (i < localObject.length)
    {
      if (str.equalsIgnoreCase(localObject[i])) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public void setApplicationContext(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  public void setTempPath(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\utils\AppEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */