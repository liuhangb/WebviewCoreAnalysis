package org.chromium.components.navigation_interception;

import android.text.TextUtils;
import org.chromium.base.annotations.CalledByNative;

public class NavigationParams
{
  public final int a;
  public final String a;
  public final boolean a;
  public final String b;
  public final boolean b;
  public final String c;
  public final boolean c;
  public final boolean d;
  public final boolean e;
  public final boolean f;
  
  public NavigationParams(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, int paramInt, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, String paramString3, boolean paramBoolean6)
  {
    this.jdField_a_of_type_JavaLangString = paramString1;
    paramString1 = paramString2;
    if (TextUtils.isEmpty(paramString2)) {
      paramString1 = null;
    }
    this.jdField_b_of_type_JavaLangString = paramString1;
    this.jdField_a_of_type_Boolean = paramBoolean1;
    this.jdField_b_of_type_Boolean = paramBoolean2;
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_c_of_type_Boolean = paramBoolean3;
    this.d = paramBoolean4;
    this.f = paramBoolean5;
    this.jdField_c_of_type_JavaLangString = paramString3;
    this.e = paramBoolean6;
  }
  
  @CalledByNative
  public static NavigationParams create(String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, int paramInt, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, String paramString3, boolean paramBoolean6)
  {
    return new NavigationParams(paramString1, paramString2, paramBoolean1, paramBoolean2, paramInt, paramBoolean3, paramBoolean4, paramBoolean5, paramString3, paramBoolean6);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\navigation_interception\NavigationParams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */