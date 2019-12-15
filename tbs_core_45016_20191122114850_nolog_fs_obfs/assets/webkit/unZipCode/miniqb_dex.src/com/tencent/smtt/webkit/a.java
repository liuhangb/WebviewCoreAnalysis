package com.tencent.smtt.webkit;

import com.tencent.smtt.export.external.interfaces.MediaAccessPermissionsCallback;
import java.lang.ref.WeakReference;
import org.chromium.android_webview.CleanupReference;
import org.chromium.android_webview.permission.AwPermissionRequest;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.tencent.AwMediaAccessPermissions;
import org.chromium.tencent.TencentAwContent;
import org.chromium.tencent.android_webview.permission.TencentAwPermissionRequest;

public class a
  implements MediaAccessPermissionsCallback
{
  private a jdField_a_of_type_ComTencentSmttWebkitA$a;
  private CleanupReference jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference;
  
  public a(String paramString, TencentAwContent paramTencentAwContent, AwPermissionRequest paramAwPermissionRequest)
  {
    this.jdField_a_of_type_ComTencentSmttWebkitA$a = new a(paramTencentAwContent, paramAwPermissionRequest, paramString);
    this.jdField_a_of_type_OrgChromiumAndroid_webviewCleanupReference = new CleanupReference(this, this.jdField_a_of_type_ComTencentSmttWebkitA$a);
  }
  
  private static class a
    implements Runnable
  {
    private long jdField_a_of_type_Long;
    private String jdField_a_of_type_JavaLangString;
    private WeakReference<TencentAwContent> jdField_a_of_type_JavaLangRefWeakReference;
    private WeakReference<AwPermissionRequest> jdField_b_of_type_JavaLangRefWeakReference;
    private boolean jdField_b_of_type_Boolean;
    
    public a(TencentAwContent paramTencentAwContent, AwPermissionRequest paramAwPermissionRequest, String paramString)
    {
      this.jdField_a_of_type_JavaLangRefWeakReference = new WeakReference(paramTencentAwContent);
      this.jdField_b_of_type_JavaLangRefWeakReference = new WeakReference(paramAwPermissionRequest);
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    public void run()
    {
      if ((!jdField_a_of_type_Boolean) && (!ThreadUtils.runningOnUiThread())) {
        throw new AssertionError();
      }
      TencentAwContent localTencentAwContent = (TencentAwContent)this.jdField_a_of_type_JavaLangRefWeakReference.get();
      AwPermissionRequest localAwPermissionRequest = (AwPermissionRequest)this.jdField_b_of_type_JavaLangRefWeakReference.get();
      if (localTencentAwContent != null)
      {
        if (localAwPermissionRequest == null) {
          return;
        }
        if (this.jdField_b_of_type_Boolean)
        {
          long l = localAwPermissionRequest.getResources();
          int j = 1;
          int i;
          if ((l & 0x2) != 0L)
          {
            if ((0x2 & this.jdField_a_of_type_Long) != 0L) {
              i = 1;
            } else {
              i = 0;
            }
            if (i != 0) {
              localTencentAwContent.getMediaAccessPermissions().allow(this.jdField_a_of_type_JavaLangString, 1);
            } else {
              localTencentAwContent.getMediaAccessPermissions().deny(this.jdField_a_of_type_JavaLangString, 1);
            }
          }
          if ((l & 0x4) != 0L)
          {
            if ((this.jdField_a_of_type_Long & 0x4) != 0L) {
              i = j;
            } else {
              i = 0;
            }
            if (i != 0) {
              localTencentAwContent.getMediaAccessPermissions().allow(this.jdField_a_of_type_JavaLangString, 2);
            } else {
              localTencentAwContent.getMediaAccessPermissions().deny(this.jdField_a_of_type_JavaLangString, 2);
            }
          }
          l = this.jdField_a_of_type_Long;
          if (l != 0L)
          {
            if ((localAwPermissionRequest instanceof TencentAwPermissionRequest))
            {
              ((TencentAwPermissionRequest)localAwPermissionRequest).grant(l);
              return;
            }
            Log.e("cr.MediaAccess", "permissionRequest not instanceof TencentAwPermissionRequest", new Object[0]);
            return;
          }
          localAwPermissionRequest.deny();
        }
        return;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */