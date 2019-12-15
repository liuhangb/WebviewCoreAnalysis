package com.tencent.smtt.webkit;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import com.tencent.smtt.webkit.service.SmttServiceProxy;

public class m
{
  private static int a(Context paramContext)
  {
    try
    {
      paramContext = paramContext.getPackageManager().getPackageInfo("com.tencent.mobileqq", 0).versionName.split("\\.");
      if (paramContext.length < 3) {
        return 0;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramContext[0]);
      localStringBuilder.append(paramContext[1]);
      localStringBuilder.append(paramContext[2]);
      int i = Integer.parseInt(localStringBuilder.toString());
      return i;
    }
    catch (PackageManager.NameNotFoundException paramContext)
    {
      paramContext.printStackTrace();
    }
    return 0;
  }
  
  public static String a(Context paramContext, int paramInt)
  {
    if (paramInt != 17018)
    {
      if (paramInt != 25300)
      {
        if (paramInt != 25416)
        {
          if (paramInt != 160000)
          {
            switch (paramInt)
            {
            default: 
              return null;
            case 10003: 
              return "errorpage/x5_redirects_tip.html";
            case 10002: 
              return "errorpage/x5_searchchildren.html";
            }
            if ((paramContext != null) && ("com.tencent.mobileqq".equals(paramContext.getApplicationInfo().packageName)) && (SmttServiceProxy.getInstance().isQQErrorPageLittleGameEnabled(paramContext)) && (a(paramContext) >= 765)) {
              return "errorpage/x5_error_qq.html";
            }
            return "errorpage/x5_error.html";
          }
          return "adfilter/adFilterHidenRules.js";
        }
        return "webkit/inspector/InspectorOverlayPage.html";
      }
      return "webkit/missingImage.png";
    }
    return "distiller/domdistiller.js";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\m.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */