package com.tencent.smtt.webkit.ui.a.a;

import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.View;

public class a
{
  public static int a = Build.VERSION.SDK_INT;
  
  private static int a()
  {
    return 16;
  }
  
  @TargetApi(16)
  public static void a(View paramView)
  {
    if (a >= 16)
    {
      paramView.postInvalidateOnAnimation();
      return;
    }
    paramView.postInvalidateDelayed(a());
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\a\a\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */