package org.chromium.components.web_contents_delegate_android;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class ColorPickerSimple
  extends ListView
  implements ColorSuggestionListAdapter.OnColorSuggestionClickListener
{
  private static final int[] jdField_a_of_type_ArrayOfInt = { -65536, -16711681, -16776961, -16711936, -65281, 65280, -16777216, -1 };
  private static final int[] b = { R.string.color_picker_button_red, R.string.color_picker_button_cyan, R.string.color_picker_button_blue, R.string.color_picker_button_green, R.string.color_picker_button_magenta, R.string.color_picker_button_yellow, R.string.color_picker_button_black, R.string.color_picker_button_white };
  private OnColorChangedListener jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidOnColorChangedListener;
  
  public ColorPickerSimple(Context paramContext)
  {
    super(paramContext);
  }
  
  public ColorPickerSimple(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public ColorPickerSimple(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void onColorSuggestionClick(ColorSuggestion paramColorSuggestion)
  {
    this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidOnColorChangedListener.onColorChanged(paramColorSuggestion.a);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\web_contents_delegate_android\ColorPickerSimple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */