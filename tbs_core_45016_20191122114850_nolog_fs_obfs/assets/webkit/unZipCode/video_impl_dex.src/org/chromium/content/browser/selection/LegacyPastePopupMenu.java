package org.chromium.content.browser.selection;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

public class LegacyPastePopupMenu
  implements View.OnClickListener, PastePopupMenu
{
  private int jdField_a_of_type_Int;
  private final Context jdField_a_of_type_AndroidContentContext;
  private final View jdField_a_of_type_AndroidViewView;
  private final PopupWindow jdField_a_of_type_AndroidWidgetPopupWindow;
  private final PastePopupMenu.PastePopupMenuDelegate jdField_a_of_type_OrgChromiumContentBrowserSelectionPastePopupMenu$PastePopupMenuDelegate;
  private int jdField_b_of_type_Int;
  private View jdField_b_of_type_AndroidViewView;
  private int c;
  private final int d;
  private final int e;
  private final int f;
  
  public LegacyPastePopupMenu(Context paramContext, View paramView, PastePopupMenu.PastePopupMenuDelegate paramPastePopupMenuDelegate)
  {
    this.jdField_a_of_type_AndroidViewView = paramView;
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionPastePopupMenu$PastePopupMenuDelegate = paramPastePopupMenuDelegate;
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_a_of_type_AndroidWidgetPopupWindow = new PopupWindow(this.jdField_a_of_type_AndroidContentContext, null, 16843464);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setSplitTouchEnabled(true);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setClippingEnabled(false);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setAnimationStyle(0);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setWidth(-2);
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setHeight(-2);
    paramContext = this.jdField_a_of_type_AndroidContentContext.getTheme().obtainStyledAttributes(new int[] { 16843540 });
    this.d = paramContext.getResourceId(paramContext.getIndex(0), 0);
    paramContext.recycle();
    this.e = ((int)TypedValue.applyDimension(1, 5.0F, this.jdField_a_of_type_AndroidContentContext.getResources().getDisplayMetrics()));
    this.f = ((int)TypedValue.applyDimension(1, 30.0F, this.jdField_a_of_type_AndroidContentContext.getResources().getDisplayMetrics()));
    int i = this.jdField_a_of_type_AndroidContentContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (i > 0) {
      this.c = this.jdField_a_of_type_AndroidContentContext.getResources().getDimensionPixelSize(i);
    }
  }
  
  private void a()
  {
    if (this.jdField_b_of_type_AndroidViewView == null)
    {
      LayoutInflater localLayoutInflater = (LayoutInflater)this.jdField_a_of_type_AndroidContentContext.getSystemService("layout_inflater");
      if (localLayoutInflater != null) {
        this.jdField_b_of_type_AndroidViewView = localLayoutInflater.inflate(this.d, null);
      }
      if (this.jdField_b_of_type_AndroidViewView != null)
      {
        int i = View.MeasureSpec.makeMeasureSpec(0, 0);
        this.jdField_b_of_type_AndroidViewView.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
        this.jdField_b_of_type_AndroidViewView.measure(i, i);
        this.jdField_b_of_type_AndroidViewView.setOnClickListener(this);
      }
      else
      {
        throw new IllegalArgumentException("Unable to inflate TextEdit paste window");
      }
    }
    this.jdField_a_of_type_AndroidWidgetPopupWindow.setContentView(this.jdField_b_of_type_AndroidViewView);
  }
  
  private void a(int paramInt1, int paramInt2)
  {
    if ((this.jdField_a_of_type_Int == paramInt1) && (this.jdField_b_of_type_Int == paramInt2)) {
      return;
    }
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    Object localObject = this.jdField_a_of_type_AndroidWidgetPopupWindow.getContentView();
    int j = ((View)localObject).getMeasuredWidth();
    int m = ((View)localObject).getMeasuredHeight();
    int k = (int)(paramInt1 - j / 2.0F);
    int i = paramInt2 - m - this.e;
    if (this.jdField_a_of_type_AndroidViewView.getSystemUiVisibility() == 0) {
      paramInt2 = this.c;
    } else {
      paramInt2 = 0;
    }
    int n = this.jdField_a_of_type_AndroidContentContext.getResources().getDisplayMetrics().widthPixels;
    if (i < paramInt2)
    {
      paramInt2 = i + m + this.e;
      i = this.f / 2;
      if (paramInt1 + j < n) {
        paramInt1 = k + (i + j / 2);
      } else {
        paramInt1 = k - (i + j / 2);
      }
    }
    else
    {
      paramInt1 = Math.min(n - j, Math.max(0, k));
      paramInt2 = i;
    }
    localObject = new int[2];
    this.jdField_a_of_type_AndroidViewView.getLocationInWindow((int[])localObject);
    i = localObject[0];
    j = localObject[1];
    this.jdField_a_of_type_AndroidWidgetPopupWindow.showAtLocation(this.jdField_a_of_type_AndroidViewView, 0, paramInt1 + i, paramInt2 + j);
  }
  
  private void b()
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserSelectionPastePopupMenu$PastePopupMenuDelegate.paste();
  }
  
  public void hide()
  {
    this.jdField_a_of_type_AndroidWidgetPopupWindow.dismiss();
  }
  
  public void onClick(View paramView)
  {
    b();
    hide();
  }
  
  public void show(Rect paramRect)
  {
    hide();
    a();
    a(paramRect.left, paramRect.bottom);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\LegacyPastePopupMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */