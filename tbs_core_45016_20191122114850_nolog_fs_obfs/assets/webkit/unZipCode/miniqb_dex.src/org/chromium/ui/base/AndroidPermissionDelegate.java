package org.chromium.ui.base;

public abstract interface AndroidPermissionDelegate
{
  public abstract boolean canRequestPermission(String paramString);
  
  public abstract boolean hasPermission(String paramString);
  
  public abstract boolean isPermissionRevokedByPolicy(String paramString);
  
  public abstract void requestPermissions(String[] paramArrayOfString, WindowAndroid.PermissionCallback paramPermissionCallback);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\AndroidPermissionDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */