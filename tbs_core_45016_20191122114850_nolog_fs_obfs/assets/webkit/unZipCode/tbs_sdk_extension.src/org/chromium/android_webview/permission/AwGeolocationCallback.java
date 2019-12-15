package org.chromium.android_webview.permission;

import java.lang.ref.WeakReference;
import org.chromium.android_webview.AwContents;
import org.chromium.android_webview.AwGeolocationPermissions;
import org.chromium.android_webview.AwGeolocationPermissions.Callback;
import org.chromium.android_webview.CleanupReference;
import org.chromium.base.ThreadUtils;

public class AwGeolocationCallback
  implements AwGeolocationPermissions.Callback
{
  private CleanupReference jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference;
  private CleanupRunable jdField_a_of_type_OrgChromiumAndroid_webviewPermissionAwGeolocationCallback$CleanupRunable;
  
  public AwGeolocationCallback(String paramString, AwContents paramAwContents)
  {
    this.jdField_a_of_type_OrgChromiumAndroid_webviewPermissionAwGeolocationCallback$CleanupRunable = new CleanupRunable(paramAwContents, paramString);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference = new CleanupReference(this, this.jdField_a_of_type_OrgChromiumAndroid_webviewPermissionAwGeolocationCallback$CleanupRunable);
  }
  
  public void invoke(String paramString, boolean paramBoolean1, boolean paramBoolean2)
  {
    CleanupRunable localCleanupRunable = this.jdField_a_of_type_OrgChromiumAndroid_webviewPermissionAwGeolocationCallback$CleanupRunable;
    if (localCleanupRunable != null)
    {
      if (this.jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference == null) {
        return;
      }
      localCleanupRunable.setResponse(paramString, paramBoolean1, paramBoolean2);
      this.jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference.cleanupNow();
      this.jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference = null;
      this.jdField_a_of_type_OrgChromiumAndroid_webviewPermissionAwGeolocationCallback$CleanupRunable = null;
      return;
    }
  }
  
  private static class CleanupRunable
    implements Runnable
  {
    private String jdField_a_of_type_JavaLangString;
    private WeakReference<AwContents> jdField_a_of_type_JavaLangRefWeakReference;
    private boolean b;
    private boolean c;
    
    public CleanupRunable(AwContents paramAwContents, String paramString)
    {
      this.jdField_a_of_type_JavaLangRefWeakReference = new WeakReference(paramAwContents);
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    public void run()
    {
      if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
        throw new AssertionError();
      }
      AwContents localAwContents = (AwContents)this.jdField_a_of_type_JavaLangRefWeakReference.get();
      if (localAwContents == null) {
        return;
      }
      if (this.c) {
        if (this.b) {
          localAwContents.getGeolocationPermissions().allow(this.jdField_a_of_type_JavaLangString);
        } else {
          localAwContents.getGeolocationPermissions().deny(this.jdField_a_of_type_JavaLangString);
        }
      }
      localAwContents.invokeGeolocationCallback(this.b, this.jdField_a_of_type_JavaLangString);
    }
    
    public void setResponse(String paramString, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      this.b = paramBoolean1;
      this.c = paramBoolean2;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\permission\AwGeolocationCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */