package org.chromium.content.browser.input;

import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import org.chromium.ui.base.WindowAndroid;

public class SpellCheckPopupWindow
  extends SuggestionsPopupWindow
{
  private String[] a;
  
  public SpellCheckPopupWindow(Context paramContext, TextSuggestionHost paramTextSuggestionHost, WindowAndroid paramWindowAndroid, View paramView)
  {
    super(paramContext, paramTextSuggestionHost, paramWindowAndroid, paramView);
    this.jdField_a_of_type_ArrayOfJavaLangString = new String[0];
  }
  
  protected void applySuggestion(int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionHost.applySpellCheckSuggestion(this.jdField_a_of_type_ArrayOfJavaLangString[paramInt]);
  }
  
  protected Object getSuggestionItem(int paramInt)
  {
    return this.jdField_a_of_type_ArrayOfJavaLangString[paramInt];
  }
  
  protected SpannableString getSuggestionText(int paramInt)
  {
    return new SpannableString(this.jdField_a_of_type_ArrayOfJavaLangString[paramInt]);
  }
  
  protected int getSuggestionsCount()
  {
    return this.jdField_a_of_type_ArrayOfJavaLangString.length;
  }
  
  public void show(double paramDouble1, double paramDouble2, String paramString, String[] paramArrayOfString)
  {
    this.jdField_a_of_type_ArrayOfJavaLangString = ((String[])paramArrayOfString.clone());
    a(true);
    super.a(paramDouble1, paramDouble2, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\SpellCheckPopupWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */