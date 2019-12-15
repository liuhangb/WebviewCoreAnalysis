package com.tencent.smtt.jscore;

import android.content.Context;
import android.os.Looper;
import com.tencent.common.utils.FileUtils;
import com.tencent.smtt.jscore.devtools.Inspector;
import com.tencent.smtt.jscore.devtools.InspectorServer;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.File;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("jscore")
public class X5JsCoreFactory
{
  private static final String CODE_CACHE_DIR = "/tencent/tbs/code_cache/";
  private static final String PACKAGE_NAME_FORMAT = "X5JsCore in %s";
  private static boolean sIsStarted;
  
  public static X5JsContextImpl createJsContext(Context paramContext)
  {
    return createJsVirtualMachine(paramContext, null).createJsContext();
  }
  
  public static X5JsVirtualMachineImpl createJsVirtualMachine(Context paramContext, Looper paramLooper)
  {
    startYourEngine(paramContext);
    return new X5JsVirtualMachineImpl(paramContext, paramLooper);
  }
  
  private static native void nativeSetCodeCacheDirectory(String paramString);
  
  private static native void nativeSetCodeCacheEnabled(boolean paramBoolean);
  
  private static void startYourEngine(Context paramContext)
  {
    try
    {
      boolean bool = sIsStarted;
      if (bool) {
        return;
      }
      X5JsCoreReporter.setPackageName(paramContext.getPackageName());
      Object localObject1 = FileUtils.getSDcardDir(paramContext);
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("/tencent/tbs/code_cache/");
      ((StringBuilder)localObject2).append(paramContext.getPackageName());
      ((StringBuilder)localObject2).append(File.separator);
      nativeSetCodeCacheDirectory(new File((File)localObject1, ((StringBuilder)localObject2).toString()).getPath());
      nativeSetCodeCacheEnabled(SmttServiceProxy.getInstance().isCodeCacheEnable());
      if (SmttServiceProxy.getInstance().getIsX5jscoreInspectorEnabled())
      {
        localObject2 = e.a().b();
        if (localObject2 != null)
        {
          localObject1 = localObject2;
          if (!((String)localObject2).isEmpty()) {}
        }
        else
        {
          localObject1 = paramContext.getPackageName();
        }
        Inspector.getInstance().setPackageName(String.format("X5JsCore in %s", new Object[] { localObject1 }));
        InspectorServer.startRemoteDebugging();
      }
      sIsStarted = true;
      return;
    }
    finally {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jscore\X5JsCoreFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */