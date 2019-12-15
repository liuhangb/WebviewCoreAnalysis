package org.chromium.components.web_contents_delegate_android;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class ColorPickerAdvanced
  extends LinearLayout
  implements SeekBar.OnSeekBarChangeListener
{
  private int jdField_a_of_type_Int;
  ColorPickerAdvancedComponent jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorPickerAdvancedComponent;
  private OnColorChangedListener jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidOnColorChangedListener;
  private final float[] jdField_a_of_type_ArrayOfFloat = new float[3];
  ColorPickerAdvancedComponent b;
  ColorPickerAdvancedComponent c;
  
  public ColorPickerAdvanced(Context paramContext)
  {
    super(paramContext);
    a();
  }
  
  public ColorPickerAdvanced(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    a();
  }
  
  public ColorPickerAdvanced(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    a();
  }
  
  private void a()
  {
    setOrientation(1);
    this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorPickerAdvancedComponent = a(R.string.color_picker_hue, 360, this);
    this.b = a(R.string.color_picker_saturation, 100, this);
    this.c = a(R.string.color_picker_value, 100, this);
    f();
  }
  
  private void b()
  {
    OnColorChangedListener localOnColorChangedListener = this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidOnColorChangedListener;
    if (localOnColorChangedListener != null) {
      localOnColorChangedListener.onColorChanged(a());
    }
  }
  
  private void c()
  {
    float[] arrayOfFloat = new float[3];
    Object localObject = this.jdField_a_of_type_ArrayOfFloat;
    arrayOfFloat[1] = localObject[1];
    arrayOfFloat[2] = localObject[2];
    localObject = new int[7];
    int i = 0;
    while (i < 7)
    {
      arrayOfFloat[0] = (i * 60.0F);
      localObject[i] = Color.HSVToColor(arrayOfFloat);
      i += 1;
    }
    this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorPickerAdvancedComponent.a((int[])localObject);
  }
  
  private void d()
  {
    float[] arrayOfFloat1 = new float[3];
    float[] arrayOfFloat2 = this.jdField_a_of_type_ArrayOfFloat;
    arrayOfFloat1[0] = arrayOfFloat2[0];
    arrayOfFloat1[1] = 0.0F;
    arrayOfFloat1[2] = arrayOfFloat2[2];
    int i = Color.HSVToColor(arrayOfFloat1);
    arrayOfFloat1[1] = 1.0F;
    int j = Color.HSVToColor(arrayOfFloat1);
    this.b.a(new int[] { i, j });
  }
  
  private void e()
  {
    float[] arrayOfFloat1 = new float[3];
    float[] arrayOfFloat2 = this.jdField_a_of_type_ArrayOfFloat;
    arrayOfFloat1[0] = arrayOfFloat2[0];
    arrayOfFloat1[1] = arrayOfFloat2[1];
    arrayOfFloat1[2] = 0.0F;
    int i = Color.HSVToColor(arrayOfFloat1);
    arrayOfFloat1[2] = 1.0F;
    int j = Color.HSVToColor(arrayOfFloat1);
    this.c.a(new int[] { i, j });
  }
  
  private void f()
  {
    int i = Math.max(Math.min(Math.round(this.jdField_a_of_type_ArrayOfFloat[1] * 100.0F), 100), 0);
    int j = Math.max(Math.min(Math.round(this.jdField_a_of_type_ArrayOfFloat[2] * 100.0F), 100), 0);
    this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorPickerAdvancedComponent.a(this.jdField_a_of_type_ArrayOfFloat[0]);
    this.b.a(i);
    this.c.a(j);
    c();
    d();
    e();
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public ColorPickerAdvancedComponent a(int paramInt1, int paramInt2, SeekBar.OnSeekBarChangeListener paramOnSeekBarChangeListener)
  {
    View localView = ((LayoutInflater)getContext().getSystemService("layout_inflater")).inflate(R.layout.color_picker_advanced_component, null);
    addView(localView);
    return new ColorPickerAdvancedComponent(localView, paramInt1, paramInt2, paramOnSeekBarChangeListener);
  }
  
  public void a(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
    Color.colorToHSV(this.jdField_a_of_type_Int, this.jdField_a_of_type_ArrayOfFloat);
    f();
  }
  
  public void a(OnColorChangedListener paramOnColorChangedListener)
  {
    this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidOnColorChangedListener = paramOnColorChangedListener;
  }
  
  public void onProgressChanged(SeekBar paramSeekBar, int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.jdField_a_of_type_ArrayOfFloat[0] = this.jdField_a_of_type_OrgChromiumComponentsWeb_contents_delegate_androidColorPickerAdvancedComponent.a();
      this.jdField_a_of_type_ArrayOfFloat[1] = (this.b.a() / 100.0F);
      this.jdField_a_of_type_ArrayOfFloat[2] = (this.c.a() / 100.0F);
      this.jdField_a_of_type_Int = Color.HSVToColor(this.jdField_a_of_type_ArrayOfFloat);
      c();
      d();
      e();
      b();
    }
  }
  
  public void onStartTrackingTouch(SeekBar paramSeekBar) {}
  
  public void onStopTrackingTouch(SeekBar paramSeekBar) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\web_contents_delegate_android\ColorPickerAdvanced.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */