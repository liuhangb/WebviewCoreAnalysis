package com.tencent.common.utils;

import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;

public class GpuInfoUtils
{
  private static String jdField_a_of_type_JavaLangString = "";
  private static boolean jdField_a_of_type_Boolean = false;
  private static String b = "";
  private static String c = "";
  
  public static String getGLRenderer()
  {
    if ((TextUtils.isEmpty(jdField_a_of_type_JavaLangString)) && (inited())) {
      try
      {
        jdField_a_of_type_JavaLangString = nativeGetGLRenderer();
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    return jdField_a_of_type_JavaLangString;
  }
  
  public static String getGLVendor()
  {
    if ((TextUtils.isEmpty(b)) && (inited())) {
      try
      {
        b = nativeGetGLVendor();
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
      }
    }
    return b;
  }
  
  public static String getGLVersion()
  {
    if ((TextUtils.isEmpty(c)) && (inited())) {}
    for (;;)
    {
      int i;
      int j;
      int k;
      try
      {
        String[] arrayOfString = nativeGetGLVersion().split(" ");
        if (arrayOfString.length > 2)
        {
          c = "";
          if ((arrayOfString[0].equals("OpenGL")) && (arrayOfString[1].equals("ES")))
          {
            i = 0;
            j = 0;
            if (i < arrayOfString[2].length())
            {
              k = arrayOfString[2].charAt(i);
              if (k < 48) {
                break label126;
              }
              if (k <= 57) {
                break label132;
              }
              break label126;
            }
            if (j > 0) {
              c = arrayOfString[2].substring(0, j);
            }
          }
        }
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        c = "";
      }
      return c;
      label126:
      if (k == 46)
      {
        label132:
        j += 1;
        i += 1;
      }
    }
  }
  
  public static boolean init(String paramString)
  {
    if (Build.VERSION.SDK_INT < 14) {
      return false;
    }
    synchronized (LinuxToolsJni.gBaseModuleSoLock)
    {
      if (LinuxToolsJni.gJniloaded)
      {
        bool = jdField_a_of_type_Boolean;
        if (!bool) {
          try
          {
            jdField_a_of_type_Boolean = nativeInitGLString();
          }
          catch (Throwable paramString)
          {
            paramString.printStackTrace();
            jdField_a_of_type_Boolean = false;
          }
        }
        bool = LinuxToolsJni.gJniloaded;
        return bool;
      }
      try
      {
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(paramString);
        ((StringBuilder)localObject2).append("/libcommon_basemodule_jni.so");
        localObject2 = QBSoLoader.tinkerSoLoadPath(((StringBuilder)localObject2).toString());
        if (TextUtils.isEmpty((CharSequence)localObject2))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append(paramString);
          ((StringBuilder)localObject2).append("/libcommon_basemodule_jni.so");
          System.load(((StringBuilder)localObject2).toString());
        }
        else
        {
          System.load((String)localObject2);
        }
        LinuxToolsJni.gJniloaded = nativeInitGLString();
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
        LinuxToolsJni.gJniloaded = false;
      }
      boolean bool = LinuxToolsJni.gJniloaded;
      return bool;
    }
  }
  
  public static boolean inited()
  {
    if (Build.VERSION.SDK_INT < 14) {
      return false;
    }
    synchronized (LinuxToolsJni.gBaseModuleSoLock)
    {
      boolean bool = LinuxToolsJni.gJniloaded;
      if (!bool) {
        try
        {
          String str = QBSoLoader.tinkerSoLoadLibraryPath("common_basemodule_jni");
          if (TextUtils.isEmpty(str)) {
            System.loadLibrary("common_basemodule_jni");
          } else {
            System.load(str);
          }
          LinuxToolsJni.gJniloaded = nativeInitGLString();
        }
        catch (Throwable localThrowable1)
        {
          localThrowable1.printStackTrace();
          FLogger.e("GpuInfoUtils", localThrowable1.getMessage(), localThrowable1);
        }
      }
      bool = jdField_a_of_type_Boolean;
      if (!bool) {
        try
        {
          jdField_a_of_type_Boolean = nativeInitGLString();
        }
        catch (Throwable localThrowable2)
        {
          localThrowable2.printStackTrace();
          jdField_a_of_type_Boolean = false;
        }
      }
      bool = LinuxToolsJni.gJniloaded;
      return bool;
    }
  }
  
  private static native String nativeGetGLRenderer();
  
  private static native String nativeGetGLVendor();
  
  private static native String nativeGetGLVersion();
  
  private static native boolean nativeInitGLString();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\GpuInfoUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */