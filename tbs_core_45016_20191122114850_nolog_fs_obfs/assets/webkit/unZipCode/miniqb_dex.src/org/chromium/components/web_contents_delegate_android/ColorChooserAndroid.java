package org.chromium.components.web_contents_delegate_android;

import android.content.Context;
import java.lang.ref.WeakReference;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.X5ColorPickerDialog;
import org.chromium.ui.base.WindowAndroid;

@JNINamespace("web_contents_delegate_android")
public class ColorChooserAndroid
{
  private final long jdField_a_of_type_Long;
  private final X5ColorPickerDialog jdField_a_of_type_OrgChromiumTencentX5ColorPickerDialog;
  
  private ColorChooserAndroid(long paramLong, Context paramContext, int paramInt, ColorSuggestion[] paramArrayOfColorSuggestion)
  {
    OnColorChangedListener local1 = new OnColorChangedListener()
    {
      public void onColorChanged(int paramAnonymousInt)
      {
        ColorChooserAndroid.a(ColorChooserAndroid.this).dismiss();
        ColorChooserAndroid localColorChooserAndroid = ColorChooserAndroid.this;
        ColorChooserAndroid.a(localColorChooserAndroid, ColorChooserAndroid.a(localColorChooserAndroid), paramAnonymousInt);
      }
    };
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_OrgChromiumTencentX5ColorPickerDialog = new X5ColorPickerDialog(paramContext, local1, paramInt, paramArrayOfColorSuggestion);
  }
  
  private void a()
  {
    this.jdField_a_of_type_OrgChromiumTencentX5ColorPickerDialog.show();
  }
  
  @CalledByNative
  private static void addToColorSuggestionArray(ColorSuggestion[] paramArrayOfColorSuggestion, int paramInt1, int paramInt2, String paramString)
  {
    paramArrayOfColorSuggestion[paramInt1] = new ColorSuggestion(paramInt2, paramString);
  }
  
  @CalledByNative
  public static ColorChooserAndroid createColorChooserAndroid(long paramLong, WindowAndroid paramWindowAndroid, int paramInt, ColorSuggestion[] paramArrayOfColorSuggestion)
  {
    if (paramWindowAndroid == null) {
      return null;
    }
    paramWindowAndroid = (Context)paramWindowAndroid.getContext().get();
    if (WindowAndroid.activityFromContext(paramWindowAndroid) == null) {
      return null;
    }
    paramWindowAndroid = new ColorChooserAndroid(paramLong, paramWindowAndroid, paramInt, paramArrayOfColorSuggestion);
    paramWindowAndroid.a();
    return paramWindowAndroid;
  }
  
  @CalledByNative
  private static ColorSuggestion[] createColorSuggestionArray(int paramInt)
  {
    return new ColorSuggestion[paramInt];
  }
  
  private native void nativeOnColorChosen(long paramLong, int paramInt);
  
  @CalledByNative
  public void closeColorChooser()
  {
    this.jdField_a_of_type_OrgChromiumTencentX5ColorPickerDialog.dismiss();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\web_contents_delegate_android\ColorChooserAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */