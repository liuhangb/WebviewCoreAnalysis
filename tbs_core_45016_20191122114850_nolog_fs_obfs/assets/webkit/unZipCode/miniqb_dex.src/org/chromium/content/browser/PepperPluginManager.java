package org.chromium.content.browser;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.Log;

public class PepperPluginManager
{
  public static final String PEPPER_PLUGIN_ACTION = "org.chromium.intent.PEPPERPLUGIN";
  public static final String PEPPER_PLUGIN_ROOT = "/system/lib/pepperplugin/";
  
  private static String a(Bundle paramBundle)
  {
    String str2 = paramBundle.getString("filename");
    if (str2 != null)
    {
      if (str2.isEmpty()) {
        return null;
      }
      String str1 = paramBundle.getString("mimetype");
      if (str1 != null)
      {
        if (str1.isEmpty()) {
          return null;
        }
        StringBuilder localStringBuilder = new StringBuilder("/system/lib/pepperplugin/");
        localStringBuilder.append(str2);
        str2 = paramBundle.getString("name");
        String str3 = paramBundle.getString("description");
        paramBundle = paramBundle.getString("version");
        if ((str2 != null) && (!str2.isEmpty()))
        {
          localStringBuilder.append("#");
          localStringBuilder.append(str2);
          if ((str3 != null) && (!str3.isEmpty()))
          {
            localStringBuilder.append("#");
            localStringBuilder.append(str3);
            if ((paramBundle != null) && (!paramBundle.isEmpty()))
            {
              localStringBuilder.append("#");
              localStringBuilder.append(paramBundle);
            }
          }
        }
        localStringBuilder.append(';');
        localStringBuilder.append(str1);
        return localStringBuilder.toString();
      }
      return null;
    }
    return null;
  }
  
  @SuppressLint({"WrongConstant"})
  public static String getPlugins(Context paramContext)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    paramContext = paramContext.getPackageManager();
    Iterator localIterator = paramContext.queryIntentServices(new Intent("org.chromium.intent.PEPPERPLUGIN"), 132).iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = (ResolveInfo)localIterator.next();
      Object localObject1 = ((ResolveInfo)localObject2).serviceInfo;
      if ((localObject1 != null) && (((ServiceInfo)localObject1).metaData != null) && (((ServiceInfo)localObject1).packageName != null)) {}
      try
      {
        localObject2 = paramContext.getPackageInfo(((ServiceInfo)localObject1).packageName, 0);
        if ((localObject2 == null) || ((((PackageInfo)localObject2).applicationInfo.flags & 0x1) == 0)) {
          continue;
        }
        localObject1 = a(((ServiceInfo)localObject1).metaData);
        if (localObject1 == null) {
          continue;
        }
        if (localStringBuilder.length() > 0) {
          localStringBuilder.append(',');
        }
        localStringBuilder.append((String)localObject1);
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        for (;;) {}
      }
      Log.e("cr.PepperPluginManager", "Can't find plugin: %s", new Object[] { ((ServiceInfo)localObject1).packageName });
      continue;
      Log.e("cr.PepperPluginManager", "Can't get service information from %s", new Object[] { localObject2 });
    }
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\PepperPluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */