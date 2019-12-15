package org.chromium.android_webview;

import android.content.Context;
import android.content.SharedPreferences;
import org.chromium.content.browser.ContentViewStatics;

public class AwBrowserContext
{
  private Context jdField_a_of_type_AndroidContentContext;
  private final SharedPreferences jdField_a_of_type_AndroidContentSharedPreferences;
  private AwFormDatabase jdField_a_of_type_OrgChromiumAndroid_webviewAwFormDatabase;
  private AwGeolocationPermissions jdField_a_of_type_OrgChromiumAndroid_webviewAwGeolocationPermissions;
  private AwServiceWorkerController jdField_a_of_type_OrgChromiumAndroid_webviewAwServiceWorkerController;
  private AwTracingController jdField_a_of_type_OrgChromiumAndroid_webviewAwTracingController;
  
  public AwBrowserContext(SharedPreferences paramSharedPreferences, Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentSharedPreferences = paramSharedPreferences;
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    PlatformServiceBridge.getInstance().setSafeBrowsingHandler();
  }
  
  public AwFormDatabase getFormDatabase()
  {
    if (this.jdField_a_of_type_OrgChromiumAndroid_webviewAwFormDatabase == null) {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwFormDatabase = new AwFormDatabase();
    }
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwFormDatabase;
  }
  
  public AwGeolocationPermissions getGeolocationPermissions()
  {
    if (this.jdField_a_of_type_OrgChromiumAndroid_webviewAwGeolocationPermissions == null) {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwGeolocationPermissions = new AwGeolocationPermissions(this.jdField_a_of_type_AndroidContentSharedPreferences);
    }
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwGeolocationPermissions;
  }
  
  public AwServiceWorkerController getServiceWorkerController()
  {
    if (this.jdField_a_of_type_OrgChromiumAndroid_webviewAwServiceWorkerController == null) {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwServiceWorkerController = new AwServiceWorkerController(this.jdField_a_of_type_AndroidContentContext, this);
    }
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwServiceWorkerController;
  }
  
  public AwTracingController getTracingController()
  {
    if (this.jdField_a_of_type_OrgChromiumAndroid_webviewAwTracingController == null) {
      this.jdField_a_of_type_OrgChromiumAndroid_webviewAwTracingController = new AwTracingController();
    }
    return this.jdField_a_of_type_OrgChromiumAndroid_webviewAwTracingController;
  }
  
  public void pauseTimers()
  {
    ContentViewStatics.setWebKitSharedTimersSuspended(true);
  }
  
  public void resumeTimers()
  {
    ContentViewStatics.setWebKitSharedTimersSuspended(false);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwBrowserContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */