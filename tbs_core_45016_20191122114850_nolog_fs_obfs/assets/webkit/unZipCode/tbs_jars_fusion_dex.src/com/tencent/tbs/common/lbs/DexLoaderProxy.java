package com.tencent.tbs.common.lbs;

import android.content.Context;
import android.util.Log;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.QBSoLoader;
import dalvik.system.DexClassLoader;
import java.lang.reflect.Constructor;

class DexLoaderProxy
{
  private static final String TAG = "DexLoaderProxy";
  private static final String classPath = "com.tencent.tbs.common.lbs.TxLocationManagerProxy";
  private static final String dexPath = "TencentLocationSDK.jar";
  
  public static ITxLocationManagerProxy getTxLocationManagerProxy(Context paramContext, ClassLoader paramClassLoader)
  {
    String str = LBS.getLibSearchPath();
    paramClassLoader = (LbsDexLoader)QBSoLoader.tbsTinkerForTbsDex(str, "TencentLocationSDK.jar", new LbsDexLoader(str, LBS.getOptDir(), paramClassLoader));
    try
    {
      paramContext = paramClassLoader.loadClass("com.tencent.tbs.common.lbs.TxLocationManagerProxy").getConstructor(new Class[] { Context.class }).newInstance(new Object[] { paramContext });
      if ((paramContext != null) && ((paramContext instanceof ITxLocationManagerProxy)))
      {
        paramContext = (ITxLocationManagerProxy)paramContext;
        return paramContext;
      }
    }
    catch (Throwable paramContext)
    {
      Log.e("DexLoaderProxy", "create 'com.tencent.tbs.common.lbs.TxLocationManagerProxy' instance failed", paramContext);
    }
    return null;
  }
  
  private static class LbsDexLoader
    extends DexClassLoader
  {
    public LbsDexLoader(String paramString1, String paramString2, ClassLoader paramClassLoader)
    {
      super(paramString2, FileUtilsF.getNativeLibraryDir(LBS.getContext()), paramClassLoader);
    }
    
    protected Class<?> loadClass(String paramString, boolean paramBoolean)
      throws ClassNotFoundException
    {
      Object localObject1 = findLoadedClass(paramString);
      Object localObject2 = localObject1;
      if (localObject1 == null) {}
      try
      {
        localObject2 = findClass(paramString);
        localObject1 = localObject2;
      }
      catch (ClassNotFoundException localClassNotFoundException)
      {
        for (;;) {}
      }
      if (localObject1 == null)
      {
        localObject2 = localObject1;
        if (getParent() != null) {
          return getParent().loadClass(paramString);
        }
      }
      else
      {
        resolveClass((Class)localObject1);
        localObject2 = localObject1;
      }
      return (Class<?>)localObject2;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\lbs\DexLoaderProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */