package com.tencent.tbs.core;

import android.webkit.ValueCallback;
import android.webview.chromium.tencent.TencentGeolocationPermissionsAdapter;
import com.tencent.smtt.export.external.interfaces.IX5CoreGeolocationPermissions;
import com.tencent.tbs.core.webkit.GeolocationPermissions;
import java.util.Set;

public class X5CoreGeolocationPermissions
  implements IX5CoreGeolocationPermissions
{
  private static X5CoreGeolocationPermissions sInstance;
  
  public static X5CoreGeolocationPermissions getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreGeolocationPermissions();
      }
      X5CoreGeolocationPermissions localX5CoreGeolocationPermissions = sInstance;
      return localX5CoreGeolocationPermissions;
    }
    finally {}
  }
  
  public void allow(String paramString)
  {
    TencentGeolocationPermissionsAdapter.getInstance().clear(paramString);
  }
  
  public void clear(String paramString)
  {
    TencentGeolocationPermissionsAdapter.getInstance().clear(paramString);
  }
  
  public void clearAll()
  {
    TencentGeolocationPermissionsAdapter.getInstance().clearAll();
  }
  
  public void getAllowed(String paramString, ValueCallback<Boolean> paramValueCallback)
  {
    GeolocationPermissions localGeolocationPermissions = TencentGeolocationPermissionsAdapter.getInstance();
    if ((localGeolocationPermissions instanceof TencentGeolocationPermissionsAdapter)) {
      ((TencentGeolocationPermissionsAdapter)localGeolocationPermissions).getAllowed(paramString, paramValueCallback);
    }
  }
  
  public void getOrigins(ValueCallback<Set<String>> paramValueCallback)
  {
    GeolocationPermissions localGeolocationPermissions = TencentGeolocationPermissionsAdapter.getInstance();
    if ((localGeolocationPermissions instanceof TencentGeolocationPermissionsAdapter)) {
      ((TencentGeolocationPermissionsAdapter)localGeolocationPermissions).getOrigins(paramValueCallback);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreGeolocationPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */