package org.chromium.base;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageUtils
{
  public static int getPackageVersion(Context paramContext, String paramString)
  {
    paramContext = paramContext.getPackageManager();
    int i = -1;
    try
    {
      paramContext = paramContext.getPackageInfo(paramString, 0);
      if (paramContext != null) {
        i = paramContext.versionCode;
      }
      return i;
    }
    catch (PackageManager.NameNotFoundException paramContext) {}
    return -1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\PackageUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */