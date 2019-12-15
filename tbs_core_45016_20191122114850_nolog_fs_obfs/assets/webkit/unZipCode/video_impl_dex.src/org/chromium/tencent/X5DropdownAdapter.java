package org.chromium.tencent;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import java.util.List;
import java.util.Set;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.ui.DropdownDividerDrawable;
import org.chromium.ui.DropdownItem;

public class X5DropdownAdapter
  extends ArrayAdapter<DropdownItem>
{
  private static final int COLOR_TYPE_DARK_DIVIDER = 2;
  private static final int COLOR_TYPE_DIVIDER = 3;
  private static final int COLOR_TYPE_ITEM = 1;
  private static final int COLOR_TYPE_LABEL = 4;
  private static final int COLOR_TYPE_SUBLABEL = 5;
  private static final int dropdown_dark_divider_color = -4144960;
  private static final int dropdown_dark_divider_color_night = -12368569;
  private static final int dropdown_divider_color = -1710609;
  private static final int dropdown_divider_color_night = -12368569;
  private static final int dropdown_item_color = -1;
  private static final int dropdown_item_color_night = -14012362;
  private static final int dropdown_label_color = -16777216;
  private static final int dropdown_label_color_night = -12368569;
  private static final int dropdown_sublabel_color = -7631989;
  private static final int dropdown_sublabel_color_night = -12368569;
  private final boolean mAreAllItemsEnabled;
  private final Integer mBackgroundColor;
  private final Context mContext;
  private final Integer mDividerColor;
  private final Integer mDropdownItemHeight;
  private final boolean mHasUniformHorizontalMargin;
  private boolean mInNightMode;
  private final int mLabelHorizontalMargin;
  private final int mLabelVerticalMargin;
  private final Set<Integer> mSeparators;
  
  public X5DropdownAdapter(Context paramContext, List<? extends DropdownItem> paramList, Set<Integer> paramSet, @Nullable Integer paramInteger1, @Nullable Integer paramInteger2, @Nullable Integer paramInteger3, @Nullable Integer paramInteger4, boolean paramBoolean)
  {
    super(paramContext, SharedResource.loadIdentifierResource("x5_dropdown_item", "layout"));
    this.mContext = paramContext;
    this.mInNightMode = paramBoolean;
    addAll(paramList);
    this.mSeparators = paramSet;
    this.mAreAllItemsEnabled = checkAreAllItemsEnabled();
    paramList = paramInteger1;
    if (paramInteger1 == null) {
      paramList = Integer.valueOf(getColor(1));
    }
    this.mBackgroundColor = paramList;
    this.mDividerColor = paramInteger2;
    this.mDropdownItemHeight = paramInteger3;
    this.mLabelVerticalMargin = paramContext.getResources().getDimensionPixelSize(SharedResource.loadIdentifierResource("x5_dropdown_item_label_margin", "dimen"));
    if (paramInteger4 == null)
    {
      this.mLabelHorizontalMargin = this.mLabelVerticalMargin;
      this.mHasUniformHorizontalMargin = false;
      return;
    }
    this.mLabelHorizontalMargin = ((int)TypedValue.applyDimension(1, paramInteger4.intValue(), paramContext.getResources().getDisplayMetrics()));
    this.mHasUniformHorizontalMargin = true;
  }
  
  private boolean checkAreAllItemsEnabled()
  {
    int i = 0;
    while (i < getCount())
    {
      DropdownItem localDropdownItem = (DropdownItem)getItem(i);
      if ((localDropdownItem.isEnabled()) && (!localDropdownItem.isGroupHeader())) {
        return false;
      }
      i += 1;
    }
    return true;
  }
  
  private int getColor(int paramInt)
  {
    int i = -16777216;
    switch (paramInt)
    {
    default: 
      return -16777216;
    case 5: 
      if (this.mInNightMode) {
        return -12368569;
      }
      return -7631989;
    case 4: 
      paramInt = i;
      if (this.mInNightMode) {
        return -12368569;
      }
      break;
    case 3: 
      if (this.mInNightMode) {
        return -12368569;
      }
      return -1710609;
    case 2: 
      if (this.mInNightMode) {
        return -12368569;
      }
      return -4144960;
    case 1: 
      if (this.mInNightMode) {
        return -14012362;
      }
      paramInt = -1;
    }
    return paramInt;
  }
  
  private static void setBackgroundForView(View paramView, Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramView.setBackground(paramDrawable);
      return;
    }
    paramView.setBackgroundDrawable(paramDrawable);
  }
  
  public boolean areAllItemsEnabled()
  {
    return this.mAreAllItemsEnabled;
  }
  
  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    paramViewGroup = paramView;
    if (paramView == null)
    {
      paramViewGroup = SharedResource.getLayoutInflater().inflate(SharedResource.loadIdentifierResource("x5_dropdown_item", "layout"), null);
      setBackgroundForView(paramViewGroup, new DropdownDividerDrawable(this.mBackgroundColor));
    }
    paramView = (DropdownDividerDrawable)paramViewGroup.getBackground();
    Object localObject1 = this.mDropdownItemHeight;
    int i;
    if (localObject1 == null) {
      i = this.mContext.getResources().getDimensionPixelSize(SharedResource.loadIdentifierResource("x5_dropdown_item_height", "dimen"));
    } else {
      i = (int)TypedValue.applyDimension(1, ((Integer)localObject1).intValue(), this.mContext.getResources().getDisplayMetrics());
    }
    int j;
    if (paramInt == 0)
    {
      paramView.b(0);
    }
    else
    {
      int k = this.mContext.getResources().getDimensionPixelSize(SharedResource.loadIdentifierResource("x5_dropdown_item_divider_height", "dimen"));
      j = i + k;
      paramView.a(k);
      localObject1 = this.mSeparators;
      if ((localObject1 != null) && (((Set)localObject1).contains(Integer.valueOf(paramInt))))
      {
        i = getColor(2);
      }
      else
      {
        localObject1 = this.mDividerColor;
        if (localObject1 == null) {
          i = getColor(3);
        } else {
          i = ((Integer)localObject1).intValue();
        }
      }
      paramView.b(i);
      i = j;
    }
    DropdownItem localDropdownItem = (DropdownItem)getItem(paramInt);
    paramView = (LinearLayout)paramViewGroup.findViewById(SharedResource.loadIdentifierResource("x5_dropdown_label_wrapper", "id"));
    if (localDropdownItem.isMultilineLabel()) {
      i = -2;
    }
    if (localDropdownItem.isLabelAndSublabelOnSameLine()) {
      paramView.setOrientation(0);
    } else {
      paramView.setOrientation(1);
    }
    paramView.setLayoutParams(new LinearLayout.LayoutParams(0, i, 1.0F));
    Object localObject2 = (TextView)paramViewGroup.findViewById(SharedResource.loadIdentifierResource("x5_dropdown_label", "id"));
    ((TextView)localObject2).setText(localDropdownItem.getLabel());
    ((TextView)localObject2).setSingleLine(localDropdownItem.isMultilineLabel() ^ true);
    if (localDropdownItem.isLabelAndSublabelOnSameLine())
    {
      paramView = new LinearLayout.LayoutParams(0, -2, 1.0F);
    }
    else
    {
      localObject1 = new LinearLayout.LayoutParams(-2, -2);
      if ((localDropdownItem.getIconId() == 0) || (!this.mHasUniformHorizontalMargin)) {
        ApiCompatibilityUtils.setMarginStart((ViewGroup.MarginLayoutParams)localObject1, this.mLabelHorizontalMargin);
      }
      ApiCompatibilityUtils.setMarginEnd((ViewGroup.MarginLayoutParams)localObject1, this.mLabelHorizontalMargin);
      paramView = (View)localObject1;
      if (localDropdownItem.isMultilineLabel())
      {
        paramInt = ApiCompatibilityUtils.getPaddingStart((View)localObject2);
        i = ApiCompatibilityUtils.getPaddingEnd((View)localObject2);
        j = this.mLabelVerticalMargin;
        ApiCompatibilityUtils.setPaddingRelative((View)localObject2, paramInt, j, i, j);
        paramView = (View)localObject1;
      }
    }
    ((TextView)localObject2).setLayoutParams(paramView);
    ((TextView)localObject2).setEnabled(localDropdownItem.isEnabled());
    if ((!localDropdownItem.isGroupHeader()) && (!localDropdownItem.isBoldLabel())) {
      ((TextView)localObject2).setTypeface(null, 0);
    } else {
      ((TextView)localObject2).setTypeface(null, 1);
    }
    ((TextView)localObject2).setTextColor(getColor(4));
    ((TextView)localObject2).setTextSize(0, this.mContext.getResources().getDimension(SharedResource.loadIdentifierResource("x5_dropdown_item_label_font_size", "dimen")));
    localObject1 = (TextView)paramViewGroup.findViewById(SharedResource.loadIdentifierResource("x5_dropdown_sublabel", "id"));
    ((TextView)localObject1).setTextColor(getColor(5));
    localObject2 = localDropdownItem.getSublabel();
    if (TextUtils.isEmpty((CharSequence)localObject2))
    {
      ((TextView)localObject1).setVisibility(8);
      return paramViewGroup;
    }
    if (localDropdownItem.isLabelAndSublabelOnSameLine())
    {
      paramView = new LinearLayout.LayoutParams(-2, -2);
      ApiCompatibilityUtils.setMarginStart(paramView, this.mLabelHorizontalMargin);
      ApiCompatibilityUtils.setMarginEnd(paramView, this.mLabelHorizontalMargin);
      ((TextView)localObject1).setLayoutParams(paramView);
    }
    else
    {
      ((TextView)localObject1).setLayoutParams(paramView);
    }
    ((TextView)localObject1).setText((CharSequence)localObject2);
    ((TextView)localObject1).setTextSize(0, this.mContext.getResources().getDimension(SharedResource.loadIdentifierResource("x5_dropdown_item_larger_sublabel_font_size", "dimen")));
    ((TextView)localObject1).setVisibility(0);
    return paramViewGroup;
  }
  
  public boolean isEnabled(int paramInt)
  {
    boolean bool2 = false;
    if (paramInt >= 0)
    {
      if (paramInt >= getCount()) {
        return false;
      }
      DropdownItem localDropdownItem = (DropdownItem)getItem(paramInt);
      boolean bool1 = bool2;
      if (localDropdownItem.isEnabled())
      {
        bool1 = bool2;
        if (!localDropdownItem.isGroupHeader()) {
          bool1 = true;
        }
      }
      return bool1;
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\X5DropdownAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */