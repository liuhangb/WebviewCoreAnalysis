package com.tencent.common.utils;

import android.text.TextUtils;
import java.nio.ByteBuffer;

public class LinuxToolsJni
{
  public static Object gBaseModuleSoLock = new Object();
  public static boolean gJniloaded = false;
  
  static
  {
    synchronized (gBaseModuleSoLock)
    {
      boolean bool = gJniloaded;
      if (!bool) {
        try
        {
          String str = QBSoLoader.tinkerSoLoadLibraryPath("common_basemodule_jni");
          if (TextUtils.isEmpty(str)) {
            System.loadLibrary("common_basemodule_jni");
          } else {
            System.load(str);
          }
          gJniloaded = true;
          new LinuxToolsJni().Chmod("/checkChmodeExists", "700");
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
          gJniloaded = false;
        }
      }
      return;
    }
  }
  
  public static native ByteBuffer AllocateNativeByte(int paramInt);
  
  public static native void FreeNativeByte(ByteBuffer paramByteBuffer);
  
  public static boolean init(String paramString)
  {
    synchronized (gBaseModuleSoLock)
    {
      if (gJniloaded)
      {
        boolean bool = gJniloaded;
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
        gJniloaded = true;
        new LinuxToolsJni().Chmod("/checkChmodeExists", "700");
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
        gJniloaded = false;
      }
      return gJniloaded;
    }
  }
  
  public native int Chmod(String paramString1, String paramString2);
  
  public native int Link(String paramString1, String paramString2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\LinuxToolsJni.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */