package org.chromium.components.autofill;

import org.chromium.ui.DropdownItemBase;

public class AutofillSuggestion
  extends DropdownItemBase
{
  private final int jdField_a_of_type_Int;
  private final String jdField_a_of_type_JavaLangString;
  private final boolean jdField_a_of_type_Boolean;
  private final int jdField_b_of_type_Int;
  private final String jdField_b_of_type_JavaLangString;
  private final boolean jdField_b_of_type_Boolean;
  private final boolean c;
  private final boolean d;
  
  public AutofillSuggestion(String paramString1, String paramString2, int paramInt1, boolean paramBoolean1, int paramInt2, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_b_of_type_JavaLangString = paramString2;
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_a_of_type_Boolean = paramBoolean1;
    this.jdField_b_of_type_Int = paramInt2;
    this.jdField_b_of_type_Boolean = paramBoolean2;
    this.c = paramBoolean3;
    this.d = paramBoolean4;
  }
  
  public int a()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public boolean a()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  public int getIconId()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public int getIconMarginResId()
  {
    if (this.jdField_b_of_type_Int == -10) {
      return R.dimen.dropdown_large_icon_margin;
    }
    return super.getIconMarginResId();
  }
  
  public int getIconSizeResId()
  {
    if (this.jdField_b_of_type_Int == -10) {
      return R.dimen.dropdown_large_icon_size;
    }
    return super.getIconSizeResId();
  }
  
  public String getLabel()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public int getLabelFontColorResId()
  {
    int i = this.jdField_b_of_type_Int;
    if (i == -10) {
      return R.color.http_bad_warning_message_text;
    }
    if (i == -1) {
      return R.color.insecure_context_payment_disabled_message_text;
    }
    return super.getLabelFontColorResId();
  }
  
  public String getSublabel()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  public int getSublabelFontSizeResId()
  {
    if (this.jdField_b_of_type_Int == -10) {
      return R.dimen.dropdown_item_larger_sublabel_font_size;
    }
    return super.getSublabelFontSizeResId();
  }
  
  public boolean isBoldLabel()
  {
    return this.d;
  }
  
  public boolean isIconAtStart()
  {
    if (this.jdField_a_of_type_Boolean) {
      return true;
    }
    return super.isIconAtStart();
  }
  
  public boolean isLabelAndSublabelOnSameLine()
  {
    if (this.jdField_b_of_type_Int == -10) {
      return true;
    }
    return super.isLabelAndSublabelOnSameLine();
  }
  
  public boolean isMultilineLabel()
  {
    return this.c;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\autofill\AutofillSuggestion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */