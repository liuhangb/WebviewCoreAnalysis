package org.chromium.android_webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import java.util.List;
import org.chromium.base.Callback;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwContentsStatics
{
  private static String jdField_a_of_type_JavaLangString;
  private static ClientCertLookupTable jdField_a_of_type_OrgChromiumAndroid_webviewClientCertLookupTable;
  private static boolean jdField_a_of_type_Boolean;
  
  static boolean a()
  {
    return jdField_a_of_type_Boolean;
  }
  
  public static void clearClientCertPreferences(Runnable paramRunnable)
  {
    ThreadUtils.assertOnUiThread();
    getClientCertLookupTable().clear();
    nativeClearClientCertPreferences(paramRunnable);
  }
  
  @CalledByNative
  private static void clientCertificatesCleared(Runnable paramRunnable)
  {
    if (paramRunnable == null) {
      return;
    }
    paramRunnable.run();
  }
  
  public static String findAddress(String paramString)
  {
    if (paramString != null) {
      return FindAddress.findAddress(paramString);
    }
    throw new NullPointerException("addr is null");
  }
  
  public static ClientCertLookupTable getClientCertLookupTable()
  {
    
    if (jdField_a_of_type_OrgChromiumAndroid_webviewClientCertLookupTable == null) {
      jdField_a_of_type_OrgChromiumAndroid_webviewClientCertLookupTable = new ClientCertLookupTable();
    }
    return jdField_a_of_type_OrgChromiumAndroid_webviewClientCertLookupTable;
  }
  
  public static String getProductVersion()
  {
    return nativeGetProductVersion();
  }
  
  public static boolean getSafeBrowsingEnabledByManifest()
  {
    return nativeGetSafeBrowsingEnabledByManifest();
  }
  
  public static Uri getSafeBrowsingPrivacyPolicyUrl()
  {
    return Uri.parse(nativeGetSafeBrowsingPrivacyPolicyUrl());
  }
  
  public static String getUnreachableWebDataUrl()
  {
    if (jdField_a_of_type_JavaLangString == null) {
      jdField_a_of_type_JavaLangString = nativeGetUnreachableWebDataUrl();
    }
    return jdField_a_of_type_JavaLangString;
  }
  
  @TargetApi(19)
  public static void initSafeBrowsing(Context paramContext, Callback<Boolean> paramCallback)
  {
    paramContext = paramContext.getApplicationContext();
    paramCallback = new Callback()
    {
      public void onResult(final Boolean paramAnonymousBoolean)
      {
        if (this.a != null) {
          ThreadUtils.runOnUiThread(new Runnable()
          {
            public void run()
            {
              AwContentsStatics.2.this.a.onResult(paramAnonymousBoolean);
            }
          });
        }
      }
    };
    PlatformServiceBridge.getInstance().warmUpSafeBrowsing(paramContext, paramCallback);
  }
  
  private static native void nativeClearClientCertPreferences(Runnable paramRunnable);
  
  private static native String nativeGetProductVersion();
  
  private static native boolean nativeGetSafeBrowsingEnabledByManifest();
  
  private static native String nativeGetSafeBrowsingPrivacyPolicyUrl();
  
  private static native String nativeGetUnreachableWebDataUrl();
  
  private static native void nativeSetCheckClearTextPermitted(boolean paramBoolean);
  
  private static native void nativeSetSafeBrowsingEnabledByManifest(boolean paramBoolean);
  
  private static native void nativeSetSafeBrowsingWhitelist(String[] paramArrayOfString, Callback<Boolean> paramCallback);
  
  private static native void nativeSetServiceWorkerIoThreadClient(AwContentsIoThreadClient paramAwContentsIoThreadClient, AwBrowserContext paramAwBrowserContext);
  
  @CalledByNative
  private static void safeBrowsingWhitelistAssigned(Callback<Boolean> paramCallback, boolean paramBoolean)
  {
    if (paramCallback == null) {
      return;
    }
    paramCallback.onResult(Boolean.valueOf(paramBoolean));
  }
  
  public static void setCheckClearTextPermitted(boolean paramBoolean)
  {
    nativeSetCheckClearTextPermitted(paramBoolean);
  }
  
  public static void setRecordFullDocument(boolean paramBoolean)
  {
    jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public static void setSafeBrowsingEnabledByManifest(boolean paramBoolean)
  {
    nativeSetSafeBrowsingEnabledByManifest(paramBoolean);
  }
  
  public static void setSafeBrowsingWhitelist(List<String> paramList, Callback<Boolean> paramCallback)
  {
    String[] arrayOfString = (String[])paramList.toArray(new String[paramList.size()]);
    paramList = paramCallback;
    if (paramCallback == null) {
      paramList = new Callback()
      {
        public void onResult(Boolean paramAnonymousBoolean) {}
      };
    }
    nativeSetSafeBrowsingWhitelist(arrayOfString, paramList);
  }
  
  public static void setServiceWorkerIoThreadClient(AwContentsIoThreadClient paramAwContentsIoThreadClient, AwBrowserContext paramAwBrowserContext)
  {
    nativeSetServiceWorkerIoThreadClient(paramAwContentsIoThreadClient, paramAwBrowserContext);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwContentsStatics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */