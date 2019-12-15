package org.chromium.components.webrestrictions.browser;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.text.TextUtils;
import org.chromium.base.ContextUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("web_restrictions")
public class WebRestrictionsClient
{
  private static WebRestrictionsClient jdField_a_of_type_OrgChromiumComponentsWebrestrictionsBrowserWebRestrictionsClient;
  private ContentResolver jdField_a_of_type_AndroidContentContentResolver;
  private ContentObserver jdField_a_of_type_AndroidDatabaseContentObserver;
  private Uri jdField_a_of_type_AndroidNetUri;
  private Uri b;
  
  @CalledByNative
  private static WebRestrictionsClient create(String paramString, long paramLong)
  {
    WebRestrictionsClient localWebRestrictionsClient2 = jdField_a_of_type_OrgChromiumComponentsWebrestrictionsBrowserWebRestrictionsClient;
    WebRestrictionsClient localWebRestrictionsClient1 = localWebRestrictionsClient2;
    if (localWebRestrictionsClient2 == null) {
      localWebRestrictionsClient1 = new WebRestrictionsClient();
    }
    localWebRestrictionsClient1.a(paramString, paramLong);
    return localWebRestrictionsClient1;
  }
  
  void a(String paramString, final long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (TextUtils.isEmpty(paramString))) {
      throw new AssertionError();
    }
    paramString = new Uri.Builder().scheme("content").authority(paramString).build();
    this.jdField_a_of_type_AndroidNetUri = Uri.withAppendedPath(paramString, "authorized");
    this.b = Uri.withAppendedPath(paramString, "requested");
    this.jdField_a_of_type_AndroidContentContentResolver = ContextUtils.getApplicationContext().getContentResolver();
    this.jdField_a_of_type_AndroidDatabaseContentObserver = new ContentObserver(null)
    {
      public void onChange(boolean paramAnonymousBoolean)
      {
        onChange(paramAnonymousBoolean, null);
      }
      
      public void onChange(boolean paramAnonymousBoolean, Uri paramAnonymousUri)
      {
        WebRestrictionsClient.this.nativeOnWebRestrictionsChanged(paramLong);
      }
    };
    this.jdField_a_of_type_AndroidContentContentResolver.registerContentObserver(paramString, true, this.jdField_a_of_type_AndroidDatabaseContentObserver);
  }
  
  native void nativeOnWebRestrictionsChanged(long paramLong);
  
  @CalledByNative
  void onDestroy()
  {
    this.jdField_a_of_type_AndroidContentContentResolver.unregisterContentObserver(this.jdField_a_of_type_AndroidDatabaseContentObserver);
  }
  
  @CalledByNative
  boolean requestPermission(String paramString)
  {
    ContentValues localContentValues = new ContentValues(1);
    localContentValues.put("url", paramString);
    return this.jdField_a_of_type_AndroidContentContentResolver.insert(this.b, localContentValues) != null;
  }
  
  @CalledByNative
  WebRestrictionsClientResult shouldProceed(String paramString)
  {
    paramString = String.format("url = '%s'", new Object[] { paramString });
    return new WebRestrictionsClientResult(this.jdField_a_of_type_AndroidContentContentResolver.query(this.jdField_a_of_type_AndroidNetUri, null, paramString, null, null));
  }
  
  @CalledByNative
  boolean supportsRequest()
  {
    ContentResolver localContentResolver = this.jdField_a_of_type_AndroidContentContentResolver;
    return (localContentResolver != null) && (localContentResolver.getType(this.b) != null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\webrestrictions\browser\WebRestrictionsClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */