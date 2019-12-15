package org.chromium.base;

import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Build.VERSION;
import android.os.Process;
import android.preference.PreferenceManager;
import java.lang.reflect.Method;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base::android")
@MainDex
public class ContextUtils
{
  private static Context a;
  
  private static void a(Context paramContext)
  {
    if (paramContext != null)
    {
      a = paramContext;
      return;
    }
    throw new RuntimeException("Global application context cannot be set to null.");
  }
  
  private static SharedPreferences b()
  {
    return PreferenceManager.getDefaultSharedPreferences(a);
  }
  
  public static SharedPreferences getAppSharedPreferences()
  {
    return Holder.a();
  }
  
  public static AssetManager getApplicationAssets()
  {
    for (Context localContext = getApplicationContext(); (localContext instanceof ContextWrapper); localContext = ((ContextWrapper)localContext).getBaseContext()) {}
    return localContext.getAssets();
  }
  
  public static Context getApplicationContext()
  {
    return a;
  }
  
  public static String getProcessName()
  {
    try
    {
      Object localObject1 = Class.forName("android.app.ActivityThread");
      Object localObject2 = ((Class)localObject1).getMethod("currentActivityThread", new Class[0]).invoke(null, new Object[0]);
      localObject1 = (String)((Class)localObject1).getMethod("getProcessName", new Class[0]).invoke(localObject2, new Object[0]);
      return (String)localObject1;
    }
    catch (Exception localException)
    {
      throw new RuntimeException(localException);
    }
  }
  
  public static void initApplicationContext(Context paramContext)
  {
    Context localContext = a;
    if ((localContext != null) && (localContext != paramContext)) {
      throw new RuntimeException("Attempting to set multiple global application contexts.");
    }
    a(paramContext);
  }
  
  @VisibleForTesting
  public static void initApplicationContextForTests(Context paramContext)
  {
    if ((paramContext instanceof Application)) {
      ApplicationStatus.initialize((Application)paramContext);
    }
    a(paramContext);
    Holder.a(b());
  }
  
  public static boolean isIsolatedProcess()
  {
    try
    {
      boolean bool = ((Boolean)Process.class.getMethod("isIsolated", new Class[0]).invoke(null, new Object[0])).booleanValue();
      return bool;
    }
    catch (Exception localException)
    {
      throw new RuntimeException(localException);
    }
  }
  
  public static boolean isMainProcess()
  {
    return getProcessName().contains(":") ^ true;
  }
  
  public static void startForegroundService(Context paramContext, Intent paramIntent)
  {
    if (Build.VERSION.SDK_INT >= 26)
    {
      paramContext.startForegroundService(paramIntent);
      return;
    }
    paramContext.startService(paramIntent);
  }
  
  private static class Holder
  {
    private static SharedPreferences a = ;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\ContextUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */