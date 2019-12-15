package com.tencent.mtt.base.utils;

import android.os.Build.VERSION;

public class DLDeviceUtils
{
  private static int a = -1;
  
  public static int getSdkVersion()
  {
    if (-1 == a) {
      a = Integer.parseInt(Build.VERSION.SDK);
    }
    return a;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLDeviceUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */