package org.chromium.content.browser.input;

import android.text.TextUtils;
import java.util.Locale;

public class TextInputState
{
  private final CharSequence jdField_a_of_type_JavaLangCharSequence;
  private final Range jdField_a_of_type_OrgChromiumContentBrowserInputRange;
  private final boolean jdField_a_of_type_Boolean;
  private final Range jdField_b_of_type_OrgChromiumContentBrowserInputRange;
  private final boolean jdField_b_of_type_Boolean;
  
  public TextInputState(CharSequence paramCharSequence, Range paramRange1, Range paramRange2, boolean paramBoolean1, boolean paramBoolean2)
  {
    paramRange1.clamp(0, paramCharSequence.length());
    if ((paramRange2.start() != -1) || (paramRange2.end() != -1)) {
      paramRange2.clamp(0, paramCharSequence.length());
    }
    this.jdField_a_of_type_JavaLangCharSequence = paramCharSequence;
    this.jdField_a_of_type_OrgChromiumContentBrowserInputRange = paramRange1;
    this.jdField_b_of_type_OrgChromiumContentBrowserInputRange = paramRange2;
    this.jdField_a_of_type_Boolean = paramBoolean1;
    this.jdField_b_of_type_Boolean = paramBoolean2;
  }
  
  public Range composition()
  {
    return this.jdField_b_of_type_OrgChromiumContentBrowserInputRange;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof TextInputState)) {
      return false;
    }
    paramObject = (TextInputState)paramObject;
    if (paramObject == this) {
      return true;
    }
    return (TextUtils.equals(this.jdField_a_of_type_JavaLangCharSequence, ((TextInputState)paramObject).jdField_a_of_type_JavaLangCharSequence)) && (this.jdField_a_of_type_OrgChromiumContentBrowserInputRange.equals(((TextInputState)paramObject).jdField_a_of_type_OrgChromiumContentBrowserInputRange)) && (this.jdField_b_of_type_OrgChromiumContentBrowserInputRange.equals(((TextInputState)paramObject).jdField_b_of_type_OrgChromiumContentBrowserInputRange)) && (this.jdField_a_of_type_Boolean == ((TextInputState)paramObject).jdField_a_of_type_Boolean) && (this.jdField_b_of_type_Boolean == ((TextInputState)paramObject).jdField_b_of_type_Boolean);
  }
  
  public CharSequence getSelectedText()
  {
    if (this.jdField_a_of_type_OrgChromiumContentBrowserInputRange.start() == this.jdField_a_of_type_OrgChromiumContentBrowserInputRange.end()) {
      return null;
    }
    return TextUtils.substring(this.jdField_a_of_type_JavaLangCharSequence, this.jdField_a_of_type_OrgChromiumContentBrowserInputRange.start(), this.jdField_a_of_type_OrgChromiumContentBrowserInputRange.end());
  }
  
  public CharSequence getTextAfterSelection(int paramInt)
  {
    return TextUtils.substring(this.jdField_a_of_type_JavaLangCharSequence, this.jdField_a_of_type_OrgChromiumContentBrowserInputRange.end(), Math.min(this.jdField_a_of_type_JavaLangCharSequence.length(), this.jdField_a_of_type_OrgChromiumContentBrowserInputRange.end() + paramInt));
  }
  
  public CharSequence getTextBeforeSelection(int paramInt)
  {
    return TextUtils.substring(this.jdField_a_of_type_JavaLangCharSequence, Math.max(0, this.jdField_a_of_type_OrgChromiumContentBrowserInputRange.start() - paramInt), this.jdField_a_of_type_OrgChromiumContentBrowserInputRange.start());
  }
  
  public int hashCode()
  {
    int k = this.jdField_a_of_type_JavaLangCharSequence.hashCode();
    int m = this.jdField_a_of_type_OrgChromiumContentBrowserInputRange.hashCode();
    int n = this.jdField_b_of_type_OrgChromiumContentBrowserInputRange.hashCode();
    boolean bool = this.jdField_a_of_type_Boolean;
    int j = 0;
    int i;
    if (bool) {
      i = 19;
    } else {
      i = 0;
    }
    if (this.jdField_b_of_type_Boolean) {
      j = 23;
    }
    return k * 7 + m * 11 + n * 13 + i + j;
  }
  
  public boolean replyToRequest()
  {
    return this.jdField_b_of_type_Boolean;
  }
  
  public Range selection()
  {
    return this.jdField_a_of_type_OrgChromiumContentBrowserInputRange;
  }
  
  public boolean shouldUnblock()
  {
    return false;
  }
  
  public boolean singleLine()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public CharSequence text()
  {
    return this.jdField_a_of_type_JavaLangCharSequence;
  }
  
  public String toString()
  {
    Locale localLocale = Locale.US;
    CharSequence localCharSequence = this.jdField_a_of_type_JavaLangCharSequence;
    Range localRange1 = this.jdField_a_of_type_OrgChromiumContentBrowserInputRange;
    Range localRange2 = this.jdField_b_of_type_OrgChromiumContentBrowserInputRange;
    String str1;
    if (this.jdField_a_of_type_Boolean) {
      str1 = "SIN";
    } else {
      str1 = "MUL";
    }
    String str2;
    if (this.jdField_b_of_type_Boolean) {
      str2 = " ReplyToRequest";
    } else {
      str2 = "";
    }
    return String.format(localLocale, "TextInputState {[%s] SEL%s COM%s %s%s}", new Object[] { localCharSequence, localRange1, localRange2, str1, str2 });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\TextInputState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */