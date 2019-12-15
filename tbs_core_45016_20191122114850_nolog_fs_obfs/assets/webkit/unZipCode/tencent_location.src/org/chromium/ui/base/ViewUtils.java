package org.chromium.ui.base;

import android.view.View;

public final class ViewUtils
{
  public static void a(View paramView)
  {
    if ((b(paramView)) && (!paramView.isFocused())) {
      paramView.requestFocus();
    }
  }
  
  public static boolean a(View paramView)
  {
    if (!b(paramView)) {
      return true;
    }
    return paramView.hasFocus();
  }
  
  private static boolean b(View paramView)
  {
    if (paramView.isInTouchMode()) {
      return paramView.isFocusableInTouchMode();
    }
    return paramView.isFocusable();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\ViewUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */