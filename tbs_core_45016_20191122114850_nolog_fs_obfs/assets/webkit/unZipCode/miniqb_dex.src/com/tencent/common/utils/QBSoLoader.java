package com.tencent.common.utils;

import com.tencent.mtt.proguard.KeepNameAndPublic;
import dalvik.system.DexClassLoader;
import java.io.File;

@KeepNameAndPublic
public class QBSoLoader
{
  private static IQBSoLoadProxy a;
  
  static <T extends ClassLoader> T a(String paramString1, String paramString2, T paramT)
  {
    IQBSoLoadProxy localIQBSoLoadProxy = a;
    if (localIQBSoLoadProxy != null) {
      return localIQBSoLoadProxy.tbsTinkerForTbsDex(paramString1, paramString2, paramT);
    }
    return paramT;
  }
  
  public static void init(IQBSoLoadProxy paramIQBSoLoadProxy)
  {
    a = paramIQBSoLoadProxy;
  }
  
  public static String tbsTinkerForRes(String paramString1, String paramString2)
  {
    IQBSoLoadProxy localIQBSoLoadProxy = a;
    if (localIQBSoLoadProxy != null) {
      return localIQBSoLoadProxy.tbsTinkerForRes(paramString1, paramString2);
    }
    return new File(paramString1, paramString2).getAbsolutePath();
  }
  
  public static String tbsTinkerForTbsConf(String paramString1, String paramString2)
  {
    IQBSoLoadProxy localIQBSoLoadProxy = a;
    if (localIQBSoLoadProxy != null) {
      return localIQBSoLoadProxy.tbsTinkerForTbsConf(paramString1, paramString2);
    }
    return new File(paramString1, paramString2).getAbsolutePath();
  }
  
  public static DexClassLoader tbsTinkerForTbsDex(String paramString1, String paramString2, DexClassLoader paramDexClassLoader)
  {
    return (DexClassLoader)a(paramString1, paramString2, paramDexClassLoader);
  }
  
  public static <T extends ClassLoader> T tbsTinkerForTbsDex(String paramString1, String paramString2, T paramT)
  {
    return a(paramString1, paramString2, paramT);
  }
  
  public static String tbsTinkerLibPath()
  {
    IQBSoLoadProxy localIQBSoLoadProxy = a;
    if (localIQBSoLoadProxy != null) {
      return localIQBSoLoadProxy.tbsTinkerLibPath();
    }
    return null;
  }
  
  public static String tinkerSoLoadLibraryPath(String paramString)
  {
    IQBSoLoadProxy localIQBSoLoadProxy = a;
    if (localIQBSoLoadProxy != null) {
      return localIQBSoLoadProxy.loadSoByLibraryName(paramString);
    }
    return null;
  }
  
  public static String tinkerSoLoadPath(String paramString)
  {
    IQBSoLoadProxy localIQBSoLoadProxy = a;
    if (localIQBSoLoadProxy != null) {
      return localIQBSoLoadProxy.loadSoByPath(paramString);
    }
    return null;
  }
  
  public static abstract interface IQBSoLoadProxy
  {
    public abstract String loadSoByLibraryName(String paramString);
    
    public abstract String loadSoByPath(String paramString);
    
    public abstract String tbsTinkerForRes(String paramString1, String paramString2);
    
    public abstract String tbsTinkerForTbsConf(String paramString1, String paramString2);
    
    public abstract ClassLoader tbsTinkerForTbsDex(String paramString1, String paramString2, ClassLoader paramClassLoader);
    
    public abstract String tbsTinkerLibPath();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\QBSoLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */