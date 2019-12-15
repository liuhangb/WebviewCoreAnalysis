package org.chromium.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build.VERSION;
import android.provider.Settings.Global;
import android.provider.Settings.System;
import android.support.annotation.Nullable;
import java.io.File;

public final class CommandLineInitUtil
{
  @SuppressLint({"NewApi"})
  private static String a(Context paramContext)
  {
    int j = Settings.Global.getInt(paramContext.getContentResolver(), "adb_enabled", 0);
    int i = 1;
    if (j != 1) {
      i = 0;
    }
    if (i != 0) {
      return Settings.Global.getString(paramContext.getContentResolver(), "debug_app");
    }
    return null;
  }
  
  private static boolean a(@Nullable Supplier<Boolean> paramSupplier)
  {
    boolean bool = true;
    if ((paramSupplier != null) && (((Boolean)paramSupplier.get()).booleanValue())) {
      return true;
    }
    Context localContext = ContextUtils.getApplicationContext();
    if (Build.VERSION.SDK_INT < 17) {
      paramSupplier = b(localContext);
    } else {
      paramSupplier = a(localContext);
    }
    if (!localContext.getPackageName().equals(paramSupplier))
    {
      if (BuildInfo.isDebugAndroid()) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  private static String b(Context paramContext)
  {
    int j = Settings.System.getInt(paramContext.getContentResolver(), "adb_enabled", 0);
    int i = 1;
    if (j != 1) {
      i = 0;
    }
    if (i != 0) {
      return Settings.System.getString(paramContext.getContentResolver(), "debug_app");
    }
    return null;
  }
  
  public static void initCommandLine(String paramString)
  {
    initCommandLine(paramString, null);
  }
  
  public static void initCommandLine(String paramString, @Nullable Supplier<Boolean> paramSupplier)
  {
    if ((!a) && (CommandLine.isInitialized())) {
      throw new AssertionError();
    }
    File localFile = new File("/data/local/tmp", paramString);
    if ((!localFile.exists()) || (!a(paramSupplier))) {
      localFile = new File("/data/local", paramString);
    }
    CommandLine.initFromFile(localFile.getPath());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\CommandLineInitUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */