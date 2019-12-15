package com.tencent.mtt.base.utils;

import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class WindowManagerCompat
{
  private static Class jdField_a_of_type_JavaLangClass;
  private static Field jdField_a_of_type_JavaLangReflectField;
  private static Method jdField_a_of_type_JavaLangReflectMethod;
  private static Field b;
  private static Field c;
  private static Field d;
  private static Field e;
  private static Field f;
  private static Field g;
  private static Field h;
  
  private static Field a(String paramString)
  {
    Class localClass = jdField_a_of_type_JavaLangClass;
    String str = null;
    if (localClass == null) {
      return null;
    }
    try
    {
      paramString = localClass.getDeclaredField(paramString);
      str = paramString;
      paramString.setAccessible(true);
      return paramString;
    }
    catch (NoSuchFieldException paramString) {}
    return str;
  }
  
  private static boolean a()
  {
    Class localClass = jdField_a_of_type_JavaLangClass;
    boolean bool2 = false;
    if (localClass == null) {}
    try
    {
      jdField_a_of_type_JavaLangClass = Class.forName("android.widget.Toast$TN");
      if (jdField_a_of_type_JavaLangReflectMethod == null) {}
      boolean bool1;
      return false;
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      try
      {
        jdField_a_of_type_JavaLangReflectMethod = jdField_a_of_type_JavaLangClass.getDeclaredMethod("handleShow", new Class[0]);
        jdField_a_of_type_JavaLangReflectMethod.setAccessible(true);
        if (h == null) {
          h = a("mView");
        }
        if (jdField_a_of_type_JavaLangReflectField == null) {
          jdField_a_of_type_JavaLangReflectField = a("mParams");
        }
        if (b == null) {
          b = a("mNextView");
        }
        if (c == null) {
          c = a("mGravity");
        }
        if (d == null) {
          d = a("mX");
        }
        if (e == null) {
          e = a("mY");
        }
        if (f == null) {
          f = a("mHorizontalMargin");
        }
        if (g == null) {
          g = a("mVerticalMargin");
        }
        bool1 = bool2;
        if (jdField_a_of_type_JavaLangClass != null)
        {
          bool1 = bool2;
          if (jdField_a_of_type_JavaLangReflectField != null)
          {
            bool1 = bool2;
            if (b != null)
            {
              bool1 = bool2;
              if (jdField_a_of_type_JavaLangReflectMethod != null)
              {
                bool1 = bool2;
                if (h != null) {
                  bool1 = true;
                }
              }
            }
          }
        }
        return bool1;
      }
      catch (NoSuchMethodException localNoSuchMethodException) {}
      localClassNotFoundException = localClassNotFoundException;
      return false;
    }
  }
  
  public static void addViewCompat(WindowManager paramWindowManager, View paramView, WindowManager.LayoutParams paramLayoutParams)
  {
    if (paramWindowManager != null)
    {
      if (paramView == null) {
        return;
      }
      if ((!DeviceUtilsF.isMIUIV8()) || (!a())) {}
    }
    try
    {
      Object localObject = jdField_a_of_type_JavaLangClass.getDeclaredConstructors();
      localObject[0].setAccessible(true);
      localObject = localObject[0].newInstance(new Object[0]);
      ((WindowManager.LayoutParams)jdField_a_of_type_JavaLangReflectField.get(localObject)).copyFrom(paramLayoutParams);
      if (h != null) {
        h.set(localObject, null);
      }
      if (d != null) {
        d.set(localObject, Integer.valueOf(paramLayoutParams.x));
      }
      if (e != null) {
        e.set(localObject, Integer.valueOf(paramLayoutParams.y));
      }
      if (b != null) {
        b.set(localObject, paramView);
      }
      if (c != null) {
        c.set(localObject, Integer.valueOf(paramLayoutParams.gravity));
      }
      if (f != null) {
        f.set(localObject, Float.valueOf(paramLayoutParams.horizontalMargin));
      }
      if (g != null) {
        g.set(localObject, Float.valueOf(paramLayoutParams.verticalMargin));
      }
      if (jdField_a_of_type_JavaLangReflectMethod != null) {
        jdField_a_of_type_JavaLangReflectMethod.invoke(localObject, new Object[0]);
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      for (;;) {}
    }
    if (DeviceUtilsF.getSdkVersion() > 18) {
      paramLayoutParams.type = 2005;
    } else {
      paramLayoutParams.type = 3001;
    }
    paramWindowManager.addView(paramView, paramLayoutParams);
    return;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\WindowManagerCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */