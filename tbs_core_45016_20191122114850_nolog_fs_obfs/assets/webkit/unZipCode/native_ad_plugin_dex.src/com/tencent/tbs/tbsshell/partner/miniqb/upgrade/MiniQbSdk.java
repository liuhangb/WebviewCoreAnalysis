package com.tencent.tbs.tbsshell.partner.miniqb.upgrade;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.tencent.common.utils.ReflectionUtils;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.lang.reflect.Constructor;

public class MiniQbSdk
{
  private static final String EXTENSION_DEX_FILE = "tbs_sdk_extension_dex.jar";
  private static final String EXTENSION_IMPL_CLASS = "com.tencent.tbs.sdk.extension.TbsSDKExtension";
  private static final String TAG = "MiniQbSdk";
  private static Class<?> sExtensionClass;
  private static Object sExtensionObj;
  
  static Bundle incrUpdate(Context paramContext, Bundle paramBundle)
    throws Exception
  {
    if (!initForPatch(paramContext))
    {
      MiniQBInstaller.getInstance().logReport(paramContext, 216, "incrUpdate return false");
      return null;
    }
    paramBundle = ReflectionUtils.invokeInstance(sExtensionObj, "incrUpdate", new Class[] { Context.class, Bundle.class }, new Object[] { paramContext, paramBundle });
    if (paramBundle != null) {
      return (Bundle)paramBundle;
    }
    MiniQBInstaller.getInstance().logReport(paramContext, 216, "incrUpdate return null");
    return null;
  }
  
  static boolean initForPatch(Context paramContext)
  {
    try
    {
      if (sExtensionClass != null) {
        return true;
      }
      localObject = MiniQBUpgradeManager.getInstance().getTbsCoreShareDir();
      if (localObject == null)
      {
        Log.e("MiniQbSdk", "miniQbSdk initForPatch (false) optDir == null");
        return false;
      }
      File localFile = new File((File)localObject, "tbs_sdk_extension_dex.jar");
      if (!localFile.exists())
      {
        Log.e("MiniQbSdk", "miniQbSdk initForPatch (false) dexFile.exists()=false");
        return false;
      }
      sExtensionClass = new DexClassLoader(localFile.getAbsolutePath(), ((File)localObject).getAbsolutePath(), localFile.getAbsolutePath(), MiniQbSdk.class.getClassLoader()).loadClass("com.tencent.tbs.sdk.extension.TbsSDKExtension");
      sExtensionObj = sExtensionClass.getConstructor(new Class[] { Context.class, Context.class }).newInstance(new Object[] { paramContext, paramContext });
      return true;
    }
    catch (Throwable paramContext)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("initForPatch sys WebView: ");
      ((StringBuilder)localObject).append(Log.getStackTraceString(paramContext));
      Log.e("MiniQbSdk", ((StringBuilder)localObject).toString());
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\miniqb\upgrade\MiniQbSdk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */