package org.chromium.components.web_contents_delegate_android;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import org.chromium.base.ApiCompatibilityUtils;

public class ColorSuggestionListAdapter
  extends BaseAdapter
  implements View.OnClickListener
{
  private Context jdField_a_of_type_AndroidContentContext;
  private OnColorSuggestionClickListener jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorSuggestionListAdapter$OnColorSuggestionClickListener;
  private ColorSuggestion[] jdField_a_of_type_ArrayOfOrgChromiumComponentsWeb_contents_delegate_androidColorSuggestion;
  
  private void a(View paramView, int paramInt)
  {
    Object localObject = this.jdField_a_of_type_ArrayOfOrgChromiumComponentsWeb_contents_delegate_androidColorSuggestion;
    if (paramInt >= localObject.length)
    {
      paramView.setTag(null);
      paramView.setContentDescription(null);
      paramView.setVisibility(4);
      return;
    }
    paramView.setTag(localObject[paramInt]);
    paramView.setVisibility(0);
    ColorSuggestion localColorSuggestion = this.jdField_a_of_type_ArrayOfOrgChromiumComponentsWeb_contents_delegate_androidColorSuggestion[paramInt];
    ((GradientDrawable)((LayerDrawable)paramView.getBackground()).findDrawableByLayerId(R.id.color_button_swatch)).setColor(localColorSuggestion.jdField_a_of_type_Int);
    String str = localColorSuggestion.jdField_a_of_type_JavaLangString;
    localObject = str;
    if (TextUtils.isEmpty(str)) {
      localObject = String.format("#%06X", new Object[] { Integer.valueOf(localColorSuggestion.jdField_a_of_type_Int & 0xFFFFFF) });
    }
    paramView.setContentDescription((CharSequence)localObject);
    paramView.setOnClickListener(this);
  }
  
  public int getCount()
  {
    return (this.jdField_a_of_type_ArrayOfOrgChromiumComponentsWeb_contents_delegate_androidColorSuggestion.length + 4 - 1) / 4;
  }
  
  public Object getItem(int paramInt)
  {
    return null;
  }
  
  public long getItemId(int paramInt)
  {
    return paramInt;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    boolean bool = paramView instanceof LinearLayout;
    int k = 0;
    int j;
    if (bool)
    {
      paramView = (LinearLayout)paramView;
      j = k;
    }
    else
    {
      paramViewGroup = new LinearLayout(this.jdField_a_of_type_AndroidContentContext);
      paramViewGroup.setLayoutParams(new AbsListView.LayoutParams(-1, -2));
      paramViewGroup.setOrientation(0);
      paramViewGroup.setBackgroundColor(-1);
      int m = this.jdField_a_of_type_AndroidContentContext.getResources().getDimensionPixelOffset(R.dimen.color_button_height);
      int i = 0;
      for (;;)
      {
        j = k;
        paramView = paramViewGroup;
        if (i >= 4) {
          break;
        }
        paramView = new View(this.jdField_a_of_type_AndroidContentContext);
        LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(0, m, 1.0F);
        ApiCompatibilityUtils.setMarginStart(localLayoutParams, -1);
        if (i == 3) {
          ApiCompatibilityUtils.setMarginEnd(localLayoutParams, -1);
        }
        paramView.setLayoutParams(localLayoutParams);
        paramView.setBackgroundResource(R.drawable.color_button_background);
        paramViewGroup.addView(paramView);
        i += 1;
      }
    }
    while (j < 4)
    {
      a(paramView.getChildAt(j), paramInt * 4 + j);
      j += 1;
    }
    return paramView;
  }
  
  public void onClick(View paramView)
  {
    if (this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorSuggestionListAdapter$OnColorSuggestionClickListener == null) {
      return;
    }
    paramView = (ColorSuggestion)paramView.getTag();
    if (paramView == null) {
      return;
    }
    this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorSuggestionListAdapter$OnColorSuggestionClickListener.onColorSuggestionClick(paramView);
  }
  
  public static abstract interface OnColorSuggestionClickListener
  {
    public abstract void onColorSuggestionClick(ColorSuggestion paramColorSuggestion);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\web_contents_delegate_android\ColorSuggestionListAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */