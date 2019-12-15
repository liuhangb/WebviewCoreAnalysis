package org.chromium.components.web_contents_delegate_android;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.view.View.OnClickListener;

public class ColorPickerDialog
  extends AlertDialog
  implements OnColorChangedListener
{
  private final int jdField_a_of_type_Int;
  private final View jdField_a_of_type_AndroidViewView;
  private final ColorPickerAdvanced jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorPickerAdvanced;
  private final OnColorChangedListener jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidOnColorChangedListener;
  private int b;
  
  private void a()
  {
    findViewById(R.id.more_colors_button_border).setVisibility(8);
    findViewById(R.id.color_picker_simple).setVisibility(8);
    this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorPickerAdvanced.setVisibility(0);
    this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorPickerAdvanced.a(this);
    this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorPickerAdvanced.a(this.b);
  }
  
  private void a(int paramInt)
  {
    OnColorChangedListener localOnColorChangedListener = this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidOnColorChangedListener;
    if (localOnColorChangedListener != null) {
      localOnColorChangedListener.onColorChanged(paramInt);
    }
  }
  
  private void b(int paramInt)
  {
    this.b = paramInt;
    View localView = this.jdField_a_of_type_AndroidViewView;
    if (localView != null) {
      localView.setBackgroundColor(paramInt);
    }
  }
  
  public void onColorChanged(int paramInt)
  {
    b(paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\web_contents_delegate_android\ColorPickerDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */