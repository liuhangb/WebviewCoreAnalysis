package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.text.TextUtils;
import java.lang.reflect.Method;

class PropertyUtils
{
  private static final String CMD_GET_PROP = "getprop";
  public static final String PROPERTY_DNS_PRIMARY = "net.dns1";
  public static final String PROPERTY_DNS_SECNDARY = "net.dns2";
  private static Class sClassSystemProperties;
  private static Method sMethodGetString;
  
  static
  {
    try
    {
      sClassSystemProperties = Class.forName("android.os.SystemProperties");
      sMethodGetString = sClassSystemProperties.getDeclaredMethod("get", new Class[] { String.class, String.class });
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public static String getQuickly(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return paramString2;
    }
    return getWithReflect(paramString1, paramString2);
  }
  
  private static String getWithReflect(String paramString1, String paramString2)
  {
    Class localClass = sClassSystemProperties;
    if (localClass != null)
    {
      Method localMethod = sMethodGetString;
      if (localMethod == null) {
        return paramString2;
      }
      try
      {
        paramString1 = (String)localMethod.invoke(localClass, new Object[] { paramString1, paramString2 });
        return paramString1;
      }
      catch (Throwable paramString1)
      {
        paramString1.printStackTrace();
        return paramString2;
      }
    }
    return paramString2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\PropertyUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */