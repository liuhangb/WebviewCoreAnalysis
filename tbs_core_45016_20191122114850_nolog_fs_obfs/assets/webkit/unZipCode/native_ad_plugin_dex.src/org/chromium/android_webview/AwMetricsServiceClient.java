package org.chromium.android_webview;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwMetricsServiceClient
{
  private static boolean a;
  private static boolean b;
  
  private static boolean a(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      if (paramContext.metaData == null) {
        return false;
      }
      boolean bool = paramContext.metaData.getBoolean("android.webkit.WebView.MetricsOptOut");
      return bool;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      for (;;) {}
    }
    Log.e("AwMetricsServiceCli-", "App could not find itself by package name!", new Object[0]);
    return true;
  }
  
  @CalledByNative
  public static void nativeInitialized()
  {
    ThreadUtils.assertOnUiThread();
    a = true;
    if (b) {
      nativeSetHaveMetricsConsent(true);
    }
  }
  
  public static native void nativeSetHaveMetricsConsent(boolean paramBoolean);
  
  public static void setConsentSetting(Context paramContext, boolean paramBoolean)
  {
    
    if (paramBoolean)
    {
      if (a(paramContext)) {
        return;
      }
      b = true;
      if (a) {
        nativeSetHaveMetricsConsent(true);
      }
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwMetricsServiceClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */