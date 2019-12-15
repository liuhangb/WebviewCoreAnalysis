package com.tencent.tbs.core.webkit;

import java.util.Set;
import org.chromium.base.annotations.UsedByReflection;

public class GeolocationPermissions
{
  @UsedByReflection("WebCoreProxy.java")
  public static GeolocationPermissions getInstance()
  {
    return WebViewFactory.getProvider().getGeolocationPermissions();
  }
  
  public void allow(String paramString) {}
  
  public void clear(String paramString) {}
  
  public void clearAll() {}
  
  public void getAllowed(String paramString, ValueCallback<Boolean> paramValueCallback) {}
  
  public void getOrigins(ValueCallback<Set<String>> paramValueCallback) {}
  
  public static abstract interface Callback
  {
    public abstract void invoke(String paramString, boolean paramBoolean1, boolean paramBoolean2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\GeolocationPermissions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */