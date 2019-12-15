package com.tencent.basesupport;

import android.content.Context;
import com.tencent.common.manifest.annotation.Service;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.proguard.KeepNameAndPublic;

@KeepNameAndPublic
public class PackageInfo
{
  public static ModuleProxy<PackageInfoProvider> PROXY = ModuleProxy.newProxy(PackageInfoProvider.class, new a());
  
  public static String BROADCAST_PERMISSION()
  {
    return ((PackageInfoProvider)PROXY.get()).broadcastPermission();
  }
  
  public static String KEYNAME()
  {
    return ((PackageInfoProvider)PROXY.get()).keyName();
  }
  
  public static String PKGNAME()
  {
    return ((PackageInfoProvider)PROXY.get()).packageName();
  }
  
  @Service
  public static abstract interface PackageInfoProvider
  {
    public abstract String broadcastPermission();
    
    public abstract String keyName();
    
    public abstract String packageName();
  }
  
  static class a
    implements PackageInfo.PackageInfoProvider
  {
    private String a = null;
    
    public String broadcastPermission()
    {
      return null;
    }
    
    public String keyName()
    {
      return packageName();
    }
    
    public String packageName()
    {
      if (this.a == null)
      {
        Context localContext = ContextHolder.getAppContext();
        if (localContext != null) {
          this.a = localContext.getPackageName();
        }
        if (this.a == null) {
          this.a = "";
        }
      }
      return this.a;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\basesupport\PackageInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */