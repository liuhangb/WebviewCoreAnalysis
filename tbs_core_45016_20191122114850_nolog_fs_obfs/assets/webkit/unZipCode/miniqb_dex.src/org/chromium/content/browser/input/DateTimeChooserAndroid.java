package org.chromium.content.browser.input;

import android.app.Activity;
import android.content.Context;
import java.lang.ref.WeakReference;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.picker.DateTimeSuggestion;
import org.chromium.tencent.X5InputDialogContainer;
import org.chromium.tencent.X5InputDialogContainer.InputActionDelegate;
import org.chromium.ui.base.WindowAndroid;

@JNINamespace("content")
class DateTimeChooserAndroid
{
  private final long jdField_a_of_type_Long;
  private final X5InputDialogContainer jdField_a_of_type_OrgChromiumTencentX5InputDialogContainer;
  private boolean jdField_a_of_type_Boolean;
  
  private DateTimeChooserAndroid(Context paramContext, long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_OrgChromiumTencentX5InputDialogContainer = new X5InputDialogContainer(paramContext, new X5InputDialogContainer.InputActionDelegate()
    {
      public void cancelDateTimeDialog()
      {
        if (!DateTimeChooserAndroid.a(DateTimeChooserAndroid.this))
        {
          DateTimeChooserAndroid localDateTimeChooserAndroid = DateTimeChooserAndroid.this;
          DateTimeChooserAndroid.a(localDateTimeChooserAndroid, DateTimeChooserAndroid.a(localDateTimeChooserAndroid));
        }
      }
      
      public void replaceDateTime(double paramAnonymousDouble)
      {
        if (!DateTimeChooserAndroid.a(DateTimeChooserAndroid.this))
        {
          DateTimeChooserAndroid localDateTimeChooserAndroid = DateTimeChooserAndroid.this;
          DateTimeChooserAndroid.a(localDateTimeChooserAndroid, DateTimeChooserAndroid.a(localDateTimeChooserAndroid), paramAnonymousDouble);
        }
      }
    });
  }
  
  private void a(int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, DateTimeSuggestion[] paramArrayOfDateTimeSuggestion)
  {
    this.jdField_a_of_type_OrgChromiumTencentX5InputDialogContainer.showDialog(paramInt, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramArrayOfDateTimeSuggestion);
  }
  
  @CalledByNative
  private static DateTimeChooserAndroid createDateTimeChooser(WindowAndroid paramWindowAndroid, long paramLong, int paramInt, double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4, DateTimeSuggestion[] paramArrayOfDateTimeSuggestion)
  {
    paramWindowAndroid = (Activity)paramWindowAndroid.getActivity().get();
    if (paramWindowAndroid == null) {
      return null;
    }
    paramWindowAndroid = new DateTimeChooserAndroid(paramWindowAndroid, paramLong);
    paramWindowAndroid.a(paramInt, paramDouble1, paramDouble2, paramDouble3, paramDouble4, paramArrayOfDateTimeSuggestion);
    return paramWindowAndroid;
  }
  
  @CalledByNative
  private static DateTimeSuggestion[] createSuggestionsArray(int paramInt)
  {
    return new DateTimeSuggestion[paramInt];
  }
  
  @CalledByNative
  private void destory()
  {
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_OrgChromiumTencentX5InputDialogContainer.dismissDialog();
  }
  
  private native void nativeCancelDialog(long paramLong);
  
  private native void nativeReplaceDateTime(long paramLong, double paramDouble);
  
  @CalledByNative
  private static void setDateTimeSuggestionAt(DateTimeSuggestion[] paramArrayOfDateTimeSuggestion, int paramInt, double paramDouble, String paramString1, String paramString2)
  {
    paramArrayOfDateTimeSuggestion[paramInt] = new DateTimeSuggestion(paramDouble, paramString1, paramString2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\DateTimeChooserAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */