package org.chromium.content.browser.picker;

import android.text.TextUtils;

public class DateTimeSuggestion
{
  private final double jdField_a_of_type_Double;
  private final String jdField_a_of_type_JavaLangString;
  private final String b;
  
  public DateTimeSuggestion(double paramDouble, String paramString1, String paramString2)
  {
    this.jdField_a_of_type_Double = paramDouble;
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.b = paramString2;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof DateTimeSuggestion;
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    paramObject = (DateTimeSuggestion)paramObject;
    bool1 = bool2;
    if (this.jdField_a_of_type_Double == ((DateTimeSuggestion)paramObject).jdField_a_of_type_Double)
    {
      bool1 = bool2;
      if (TextUtils.equals(this.jdField_a_of_type_JavaLangString, ((DateTimeSuggestion)paramObject).jdField_a_of_type_JavaLangString))
      {
        bool1 = bool2;
        if (TextUtils.equals(this.b, ((DateTimeSuggestion)paramObject).b)) {
          bool1 = true;
        }
      }
    }
    return bool1;
  }
  
  public int hashCode()
  {
    return ((1147 + (int)this.jdField_a_of_type_Double) * 37 + this.jdField_a_of_type_JavaLangString.hashCode()) * 37 + this.b.hashCode();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\picker\DateTimeSuggestion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */