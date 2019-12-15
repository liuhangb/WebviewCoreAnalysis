package org.chromium.components.web_contents_delegate_android;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.view.View;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import org.chromium.base.ApiCompatibilityUtils;

public class ColorPickerAdvancedComponent
{
  private GradientDrawable jdField_a_of_type_AndroidGraphicsDrawableGradientDrawable;
  private final View jdField_a_of_type_AndroidViewView;
  private final SeekBar jdField_a_of_type_AndroidWidgetSeekBar;
  private final TextView jdField_a_of_type_AndroidWidgetTextView;
  private int[] jdField_a_of_type_ArrayOfInt;
  
  ColorPickerAdvancedComponent(View paramView, int paramInt1, int paramInt2, SeekBar.OnSeekBarChangeListener paramOnSeekBarChangeListener)
  {
    this.jdField_a_of_type_AndroidViewView = paramView.findViewById(R.id.gradient);
    this.jdField_a_of_type_AndroidWidgetTextView = ((TextView)paramView.findViewById(R.id.text));
    this.jdField_a_of_type_AndroidWidgetTextView.setText(paramInt1);
    this.jdField_a_of_type_AndroidGraphicsDrawableGradientDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT, null);
    this.jdField_a_of_type_AndroidWidgetSeekBar = ((SeekBar)paramView.findViewById(R.id.seek_bar));
    this.jdField_a_of_type_AndroidWidgetSeekBar.setOnSeekBarChangeListener(paramOnSeekBarChangeListener);
    this.jdField_a_of_type_AndroidWidgetSeekBar.setMax(paramInt2);
    paramInt1 = ApiCompatibilityUtils.getDrawable(paramView.getContext().getResources(), R.drawable.color_picker_advanced_select_handle).getIntrinsicWidth();
    this.jdField_a_of_type_AndroidWidgetSeekBar.setThumbOffset(paramInt1 / 2);
  }
  
  public float a()
  {
    return this.jdField_a_of_type_AndroidWidgetSeekBar.getProgress();
  }
  
  public void a(float paramFloat)
  {
    this.jdField_a_of_type_AndroidWidgetSeekBar.setProgress((int)paramFloat);
  }
  
  public void a(int[] paramArrayOfInt)
  {
    this.jdField_a_of_type_ArrayOfInt = ((int[])paramArrayOfInt.clone());
    this.jdField_a_of_type_AndroidGraphicsDrawableGradientDrawable.setColors(this.jdField_a_of_type_ArrayOfInt);
    this.jdField_a_of_type_AndroidViewView.setBackground(this.jdField_a_of_type_AndroidGraphicsDrawableGradientDrawable);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\web_contents_delegate_android\ColorPickerAdvancedComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */