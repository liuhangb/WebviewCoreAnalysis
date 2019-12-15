package com.tencent.tbs.tbsshell.partner.fullscreenplayer;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Method;

public class FullscreenPlayerPorxy
{
  private static final String CLASS_NAME = "com.tencent.mtt.fullscreenframework.FullscreenFramework";
  private static final String DEX_NAME = "fullscreen_player_dex.jar";
  private static final String TAG = "FullscreenPlayerPorxy";
  private static DexClassLoader mBaseLoader;
  private static String mDexOptPath;
  private static DexClassLoader mFullScreenLoader;
  private static String mTbsInstallLocation;
  private static FullscreenPlayerPorxy sInstance;
  
  public static FullscreenPlayerPorxy getInstance(Context paramContext)
  {
    if (sInstance == null) {
      try
      {
        if (sInstance == null) {
          sInstance = new FullscreenPlayerPorxy();
        }
      }
      finally {}
    }
    return sInstance;
  }
  
  private void initDex()
  {
    if (mFullScreenLoader != null) {
      return;
    }
    File localFile = new File(mTbsInstallLocation, "fullscreen_player_dex.jar");
    if (!localFile.exists()) {
      return;
    }
    mFullScreenLoader = new DexClassLoader(localFile.getAbsolutePath(), mDexOptPath, null, getClass().getClassLoader());
  }
  
  public void init(DexClassLoader paramDexClassLoader, String paramString1, String paramString2)
  {
    mBaseLoader = paramDexClassLoader;
    mTbsInstallLocation = paramString1;
    mDexOptPath = paramString2;
  }
  
  public int startFullscreenPlayer(Context paramContext, int paramInt, Bundle paramBundle, ValueCallback<Integer> paramValueCallback)
  {
    initDex();
    Object localObject1 = mFullScreenLoader;
    if (localObject1 == null) {
      return -1;
    }
    try
    {
      localObject1 = ((DexClassLoader)localObject1).loadClass("com.tencent.mtt.fullscreenframework.FullscreenFramework");
      Object localObject2 = ((Class)localObject1).getMethod("getInstance", new Class[] { Context.class, DexClassLoader.class });
      ((Method)localObject2).setAccessible(true);
      localObject2 = ((Method)localObject2).invoke(null, new Object[] { paramContext, mBaseLoader });
      localObject1 = ((Class)localObject1).getMethod("startModule", new Class[] { Context.class, Integer.TYPE, Bundle.class, ValueCallback.class });
      ((Method)localObject1).setAccessible(true);
      paramContext = ((Method)localObject1).invoke(localObject2, new Object[] { paramContext, Integer.valueOf(paramInt), paramBundle, paramValueCallback });
      paramBundle = new StringBuilder();
      paramBundle.append("startFullscreenPlayer:");
      paramBundle.append(paramContext);
      paramBundle.toString();
      if ((paramContext instanceof Integer))
      {
        paramInt = ((Integer)paramContext).intValue();
        return paramInt;
      }
    }
    catch (Exception paramContext)
    {
      Log.e("FullscreenPlayerPorxy", paramContext.toString());
    }
    catch (NoSuchMethodException paramContext)
    {
      paramBundle = new StringBuilder();
      paramBundle.append("NoSuchMethodException:");
      paramBundle.append(paramContext.toString());
      Log.e("FullscreenPlayerPorxy", paramBundle.toString());
    }
    catch (ClassNotFoundException paramContext)
    {
      paramBundle = new StringBuilder();
      paramBundle.append("ClassNotFoundException:");
      paramBundle.append(paramContext.toString());
      Log.e("FullscreenPlayerPorxy", paramBundle.toString());
    }
    return -2;
  }
  
  public int stopFullscreenPlayer(int paramInt, ValueCallback<Integer> paramValueCallback)
  {
    Object localObject1 = mFullScreenLoader;
    if (localObject1 == null) {
      return -1;
    }
    try
    {
      localObject1 = ((DexClassLoader)localObject1).loadClass("com.tencent.mtt.fullscreenframework.FullscreenFramework");
      Object localObject2 = ((Class)localObject1).getMethod("getInstance", new Class[] { Context.class, DexClassLoader.class });
      ((Method)localObject2).setAccessible(true);
      localObject2 = ((Method)localObject2).invoke(null, new Object[] { null, mBaseLoader });
      localObject1 = ((Class)localObject1).getMethod("stopModule", new Class[] { Integer.TYPE, ValueCallback.class });
      ((Method)localObject1).setAccessible(true);
      paramValueCallback = ((Method)localObject1).invoke(localObject2, new Object[] { Integer.valueOf(paramInt), paramValueCallback });
      if ((paramValueCallback instanceof Integer))
      {
        paramInt = ((Integer)paramValueCallback).intValue();
        return paramInt;
      }
    }
    catch (Exception paramValueCallback)
    {
      Log.e("FullscreenPlayerPorxy", paramValueCallback.toString());
    }
    catch (NoSuchMethodException paramValueCallback)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("NoSuchMethodException:");
      ((StringBuilder)localObject1).append(paramValueCallback.toString());
      Log.e("FullscreenPlayerPorxy", ((StringBuilder)localObject1).toString());
    }
    catch (ClassNotFoundException paramValueCallback)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("ClassNotFoundException:");
      ((StringBuilder)localObject1).append(paramValueCallback.toString());
      Log.e("FullscreenPlayerPorxy", ((StringBuilder)localObject1).toString());
    }
    return -2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\fullscreenplayer\FullscreenPlayerPorxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */