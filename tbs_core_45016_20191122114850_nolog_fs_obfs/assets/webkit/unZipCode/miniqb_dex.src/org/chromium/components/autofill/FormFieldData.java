package org.chromium.components.autofill;

import android.support.annotation.IntDef;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("autofill")
public class FormFieldData
{
  public final int a;
  public final String a;
  public final boolean a;
  public final String[] a;
  public final String b;
  private boolean b;
  public final String[] b;
  public final String c;
  public final String d;
  public final String e;
  public final String f;
  private String g;
  
  private FormFieldData(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean1, String paramString5, String paramString6, String paramString7, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean2, boolean paramBoolean3)
  {
    this.jdField_b_of_type_JavaLangString = paramString1;
    this.jdField_a_of_type_JavaLangString = paramString2;
    this.g = paramString3;
    this.c = paramString4;
    this.jdField_a_of_type_Boolean = paramBoolean1;
    this.d = paramString5;
    this.e = paramString6;
    this.f = paramString7;
    this.jdField_a_of_type_ArrayOfJavaLangString = paramArrayOfString1;
    this.jdField_b_of_type_ArrayOfJavaLangString = paramArrayOfString2;
    this.jdField_b_of_type_Boolean = paramBoolean3;
    paramString1 = this.jdField_a_of_type_ArrayOfJavaLangString;
    if ((paramString1 != null) && (paramString1.length != 0))
    {
      this.jdField_a_of_type_Int = 2;
      return;
    }
    if (paramBoolean2)
    {
      this.jdField_a_of_type_Int = 1;
      return;
    }
    this.jdField_a_of_type_Int = 0;
  }
  
  @CalledByNative
  private static FormFieldData createFormFieldData(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean1, String paramString5, String paramString6, String paramString7, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean2, boolean paramBoolean3)
  {
    return new FormFieldData(paramString1, paramString2, paramString3, paramString4, paramBoolean1, paramString5, paramString6, paramString7, paramArrayOfString1, paramArrayOfString2, paramBoolean2, paramBoolean3);
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public void a(boolean paramBoolean)
  {
    this.jdField_b_of_type_Boolean = paramBoolean;
  }
  
  @CalledByNative
  public String getValue()
  {
    return this.g;
  }
  
  @CalledByNative
  public boolean isChecked()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  @CalledByNative
  public void updateValue(String paramString)
  {
    this.g = paramString;
  }
  
  @Retention(RetentionPolicy.SOURCE)
  @IntDef({0L, 1L, 2L})
  public static @interface ControlType {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\autofill\FormFieldData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */