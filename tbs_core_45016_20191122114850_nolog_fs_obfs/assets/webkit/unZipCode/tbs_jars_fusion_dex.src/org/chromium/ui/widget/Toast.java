package org.chromium.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import org.chromium.base.SysUtils;

public class Toast
{
  private ViewGroup jdField_a_of_type_AndroidViewViewGroup;
  private android.widget.Toast jdField_a_of_type_AndroidWidgetToast;
  
  public Toast(Context paramContext)
  {
    this(paramContext, new android.widget.Toast(paramContext));
  }
  
  private Toast(Context paramContext, android.widget.Toast paramToast)
  {
    this.jdField_a_of_type_AndroidWidgetToast = paramToast;
    if ((SysUtils.isLowEndDevice()) && (Build.VERSION.SDK_INT >= 21))
    {
      this.jdField_a_of_type_AndroidViewViewGroup = new FrameLayout(new ContextWrapper(paramContext)
      {
        public ApplicationInfo getApplicationInfo()
        {
          ApplicationInfo localApplicationInfo = new ApplicationInfo(super.getApplicationInfo());
          localApplicationInfo.targetSdkVersion = 19;
          localApplicationInfo.flags &= 0xDFFFFFFF;
          return localApplicationInfo;
        }
      });
      a(paramToast.getView());
    }
  }
  
  @SuppressLint({"ShowToast"})
  public static Toast a(Context paramContext, CharSequence paramCharSequence, int paramInt)
  {
    return new Toast(paramContext, android.widget.Toast.makeText(paramContext, paramCharSequence, paramInt));
  }
  
  public void a()
  {
    this.jdField_a_of_type_AndroidWidgetToast.show();
  }
  
  public void a(View paramView)
  {
    ViewGroup localViewGroup = this.jdField_a_of_type_AndroidViewViewGroup;
    if (localViewGroup != null)
    {
      localViewGroup.removeAllViews();
      if (paramView != null)
      {
        this.jdField_a_of_type_AndroidViewViewGroup.addView(paramView, -2, -2);
        this.jdField_a_of_type_AndroidWidgetToast.setView(this.jdField_a_of_type_AndroidViewViewGroup);
        return;
      }
      this.jdField_a_of_type_AndroidWidgetToast.setView(null);
      return;
    }
    this.jdField_a_of_type_AndroidWidgetToast.setView(paramView);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\widget\Toast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */