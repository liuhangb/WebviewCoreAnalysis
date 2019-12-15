package org.chromium.android_webview;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import java.lang.annotation.Annotation;
import org.chromium.base.Callback;
import org.chromium.base.CommandLine;
import org.chromium.base.Log;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.metrics.RecordHistogram;

@JNINamespace("android_webview")
public class AwSafeBrowsingConfigHelper
{
  private static Boolean a;
  
  @Nullable
  private static Boolean a(Context paramContext)
  {
    try
    {
      ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 128);
      Bundle localBundle = localApplicationInfo.metaData;
      paramContext = null;
      if (localBundle == null) {
        return null;
      }
      if (localApplicationInfo.metaData.containsKey("android.webkit.WebView.EnableSafeBrowsing")) {
        paramContext = Boolean.valueOf(localApplicationInfo.metaData.getBoolean("android.webkit.WebView.EnableSafeBrowsing"));
      }
      return paramContext;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      for (;;) {}
    }
    Log.e("AwSafeBrowsingConfi-", "App could not find itself by package name!", new Object[0]);
    return Boolean.valueOf(false);
  }
  
  private static void a(@AppOptIn int paramInt)
  {
    RecordHistogram.recordEnumeratedHistogram("SafeBrowsing.WebView.AppOptIn", paramInt, 3);
  }
  
  private static boolean a()
  {
    return CommandLine.getInstance().hasSwitch("webview-disable-safebrowsing-support");
  }
  
  public static Boolean getSafeBrowsingUserOptIn()
  {
    return a;
  }
  
  public static void maybeEnableSafeBrowsingFromManifest(Context paramContext)
  {
    Object localObject = a(paramContext);
    boolean bool = true;
    if (localObject == null) {
      a(0);
    } else if (((Boolean)localObject).booleanValue()) {
      a(1);
    } else {
      a(2);
    }
    if (localObject == null)
    {
      if (a()) {
        bool = false;
      }
    }
    else {
      bool = ((Boolean)localObject).booleanValue();
    }
    AwContentsStatics.setSafeBrowsingEnabledByManifest(bool);
    localObject = new Callback()
    {
      public void onResult(Boolean paramAnonymousBoolean)
      {
        boolean bool;
        if (paramAnonymousBoolean == null) {
          bool = false;
        } else {
          bool = paramAnonymousBoolean.booleanValue();
        }
        AwSafeBrowsingConfigHelper.setSafeBrowsingUserOptIn(bool);
      }
    };
    PlatformServiceBridge.getInstance().querySafeBrowsingUserConsent(paramContext, (Callback)localObject);
  }
  
  public static void setSafeBrowsingUserOptIn(boolean paramBoolean)
  {
    a = Boolean.valueOf(paramBoolean);
    RecordHistogram.recordBooleanHistogram("SafeBrowsing.WebView.UserOptIn", paramBoolean);
  }
  
  @IntDef({0L, 1L, 2L})
  static @interface AppOptIn
  {
    public static final int COUNT = 3;
    public static final int NO_PREFERENCE = 0;
    public static final int OPT_IN = 1;
    public static final int OPT_OUT = 2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwSafeBrowsingConfigHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */