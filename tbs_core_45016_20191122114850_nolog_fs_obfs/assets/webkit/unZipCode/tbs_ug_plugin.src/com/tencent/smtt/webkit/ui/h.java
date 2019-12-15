package com.tencent.smtt.webkit.ui;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build.VERSION;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.mtt.browser.toast.ToastCompat;
import com.tencent.smtt.webkit.SmttResource;

public class h
{
  public static final int a = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_toast_margin_left_right", "dimen"));
  public static final int b = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_toast_margin_top_bottom", "dimen"));
  
  public static Toast a(Context paramContext, CharSequence paramCharSequence, int paramInt, boolean paramBoolean)
  {
    try
    {
      Toast localToast = new Toast(paramContext);
      LinearLayout localLinearLayout = new LinearLayout(paramContext);
      localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
      e.setViewBackground(localLinearLayout, e.getImageDrawable("x5_tbs_menu_toast_background", paramBoolean, 2));
      paramContext = new TextView(paramContext);
      paramContext.setTextSize(16.0F);
      if (paramBoolean) {
        paramContext.setTextColor(SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_menu_toast_text_night_color", "color")));
      } else {
        paramContext.setTextColor(SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_menu_toast_text_color", "color")));
      }
      paramContext.setText(paramCharSequence);
      paramContext.setFocusable(false);
      paramCharSequence = new LinearLayout.LayoutParams(-2, -2);
      paramCharSequence.setMargins(a, b, a, b);
      paramContext.setLayoutParams(paramCharSequence);
      paramContext.setGravity(17);
      localLinearLayout.addView(paramContext);
      localToast.setDuration(paramInt);
      localToast.setView(localLinearLayout);
      return localToast;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static void a(Context paramContext, CharSequence paramCharSequence, int paramInt, boolean paramBoolean)
  {
    try
    {
      if ((Build.VERSION.SDK_INT >= 23) && (Build.VERSION.SDK_INT <= 25))
      {
        ToastCompat.makeText(paramContext, paramCharSequence, paramInt).show();
        return;
      }
      a(paramContext, paramCharSequence, paramInt, paramBoolean).show();
      return;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
  }
  
  public static void a(Context paramContext, CharSequence paramCharSequence, boolean paramBoolean)
  {
    a(paramContext, paramCharSequence, 0, paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\h.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */