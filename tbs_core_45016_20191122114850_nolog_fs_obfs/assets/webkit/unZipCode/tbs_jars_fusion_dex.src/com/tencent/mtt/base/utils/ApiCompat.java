package com.tencent.mtt.base.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.view.View;
import android.view.Window;

public class ApiCompat
{
  @TargetApi(14)
  public static boolean hideNavigation(Activity paramActivity)
  {
    try
    {
      paramActivity.getWindow().getDecorView().setSystemUiVisibility(2);
      return true;
    }
    catch (Exception paramActivity)
    {
      paramActivity.printStackTrace();
    }
    return false;
  }
  
  @TargetApi(14)
  public static boolean hideNavigation(View paramView)
  {
    if (paramView != null) {
      try
      {
        paramView.setSystemUiVisibility(2);
      }
      catch (Exception paramView)
      {
        paramView.printStackTrace();
        return false;
      }
    }
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\ApiCompat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */