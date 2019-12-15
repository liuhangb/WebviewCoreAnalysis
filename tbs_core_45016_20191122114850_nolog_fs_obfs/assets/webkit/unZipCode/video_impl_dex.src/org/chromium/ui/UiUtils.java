package org.chromium.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.WindowInsets;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView.LayoutParams;
import android.widget.ListAdapter;
import java.util.List;

public class UiUtils
{
  private static KeyboardShowingDelegate a;
  
  public static int a(ListAdapter paramListAdapter)
  {
    int i = 0;
    int k = View.MeasureSpec.makeMeasureSpec(0, 0);
    int m = View.MeasureSpec.makeMeasureSpec(0, 0);
    AbsListView.LayoutParams localLayoutParams = new AbsListView.LayoutParams(-2, -2);
    View[] arrayOfView = new View[paramListAdapter.getViewTypeCount()];
    int j = 0;
    while (i < paramListAdapter.getCount())
    {
      int n = paramListAdapter.getItemViewType(i);
      View localView;
      if (n < 0)
      {
        localView = paramListAdapter.getView(i, null, null);
      }
      else
      {
        arrayOfView[n] = paramListAdapter.getView(i, arrayOfView[n], null);
        localView = arrayOfView[n];
      }
      localView.setLayoutParams(localLayoutParams);
      localView.measure(k, m);
      j = Math.max(j, localView.getMeasuredWidth());
      i += 1;
    }
    return j;
  }
  
  @SuppressLint({"NewApi"})
  public static boolean a(Context paramContext, View paramView)
  {
    Object localObject = a;
    boolean bool = false;
    if ((localObject != null) && (((KeyboardShowingDelegate)localObject).disableKeyboardCheck(paramContext, paramView))) {
      return false;
    }
    paramView = paramView.getRootView();
    if (paramView == null) {
      return false;
    }
    localObject = new Rect();
    paramView.getWindowVisibleDisplayFrame((Rect)localObject);
    int i = ((Rect)localObject).top;
    int k = paramView.getHeight() - (((Rect)localObject).height() + i);
    if (k <= 0) {
      return false;
    }
    int j;
    if (((Rect)localObject).width() != paramView.getWidth()) {
      j = 1;
    } else {
      j = 0;
    }
    i = k;
    if (j == 0) {
      if (Build.VERSION.SDK_INT >= 23)
      {
        i = k - paramView.getRootWindowInsets().getStableInsetBottom();
      }
      else
      {
        float f = paramContext.getResources().getDisplayMetrics().density;
        i = (int)(k - f * 100.0F);
      }
    }
    if (i > 0) {
      bool = true;
    }
    return bool;
  }
  
  public static boolean a(View paramView)
  {
    return ((InputMethodManager)paramView.getContext().getSystemService("input_method")).hideSoftInputFromWindow(paramView.getWindowToken(), 0);
  }
  
  public static abstract interface KeyboardShowingDelegate
  {
    public abstract boolean disableKeyboardCheck(Context paramContext, View paramView);
  }
  
  public static abstract interface PhotoPickerDelegate
  {
    public abstract void onPhotoPickerDismissed();
    
    public abstract void showPhotoPicker(Context paramContext, PhotoPickerListener paramPhotoPickerListener, boolean paramBoolean, List<String> paramList);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\UiUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */