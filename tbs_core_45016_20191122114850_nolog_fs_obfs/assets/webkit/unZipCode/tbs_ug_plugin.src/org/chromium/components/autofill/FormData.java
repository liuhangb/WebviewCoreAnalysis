package org.chromium.components.autofill;

import java.util.ArrayList;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("autofill")
public class FormData
{
  private long jdField_a_of_type_Long;
  public final String a;
  public final ArrayList<FormFieldData> a;
  public final String b;
  
  private FormData(long paramLong, String paramString1, String paramString2, int paramInt)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.b = paramString2;
    this.jdField_a_of_type_JavaUtilArrayList = new ArrayList(paramInt);
    a(paramInt);
  }
  
  private void a(int paramInt)
  {
    for (FormFieldData localFormFieldData = nativeGetNextFormFieldData(this.jdField_a_of_type_Long); localFormFieldData != null; localFormFieldData = nativeGetNextFormFieldData(this.jdField_a_of_type_Long)) {
      this.jdField_a_of_type_JavaUtilArrayList.add(localFormFieldData);
    }
    if (!jdField_a_of_type_Boolean)
    {
      if (this.jdField_a_of_type_JavaUtilArrayList.size() == paramInt) {
        return;
      }
      throw new AssertionError();
    }
  }
  
  @CalledByNative
  private static FormData createFormData(long paramLong, String paramString1, String paramString2, int paramInt)
  {
    return new FormData(paramLong, paramString1, paramString2, paramInt);
  }
  
  private native FormFieldData nativeGetNextFormFieldData(long paramLong);
  
  @CalledByNative
  private void onNativeDestroyed()
  {
    this.jdField_a_of_type_Long = 0L;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\autofill\FormData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */