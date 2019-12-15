package org.chromium.ui.base;

import android.content.Context;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import org.chromium.base.ContextUtils;

public final class SPenSupport
{
  private static Boolean a;
  
  public static int a(int paramInt)
  {
    if (a == null) {
      a();
    }
    if (!a.booleanValue()) {
      return paramInt;
    }
    switch (paramInt)
    {
    default: 
      return paramInt;
    case 214: 
      return 3;
    case 213: 
      return 2;
    case 212: 
      return 1;
    }
    return 0;
  }
  
  private static void a()
  {
    if (a != null) {
      return;
    }
    if (!"SAMSUNG".equalsIgnoreCase(Build.MANUFACTURER))
    {
      a = Boolean.valueOf(false);
      return;
    }
    FeatureInfo[] arrayOfFeatureInfo = ContextUtils.getApplicationContext().getPackageManager().getSystemAvailableFeatures();
    int j = arrayOfFeatureInfo.length;
    int i = 0;
    while (i < j)
    {
      if ("com.sec.feature.spen_usp".equalsIgnoreCase(arrayOfFeatureInfo[i].name))
      {
        a = Boolean.valueOf(true);
        return;
      }
      i += 1;
    }
    a = Boolean.valueOf(false);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\SPenSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */