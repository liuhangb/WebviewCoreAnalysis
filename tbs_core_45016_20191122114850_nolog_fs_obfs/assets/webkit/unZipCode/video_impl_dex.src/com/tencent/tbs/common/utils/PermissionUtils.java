package com.tencent.tbs.common.utils;

import android.content.Context;
import android.os.Build.VERSION;

public class PermissionUtils
{
  public static boolean hasPermission(Context paramContext, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramContext.checkSelfPermission(paramString) == 0;
    }
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\PermissionUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */