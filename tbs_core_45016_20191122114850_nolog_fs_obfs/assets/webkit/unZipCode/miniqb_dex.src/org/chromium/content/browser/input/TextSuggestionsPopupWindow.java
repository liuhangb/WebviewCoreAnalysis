package org.chromium.content.browser.input;

import android.content.Context;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import org.chromium.ui.base.WindowAndroid;

public class TextSuggestionsPopupWindow
  extends SuggestionsPopupWindow
{
  private TextAppearanceSpan jdField_a_of_type_AndroidTextStyleTextAppearanceSpan;
  private SuggestionInfo[] jdField_a_of_type_ArrayOfOrgChromiumContentBrowserInputSuggestionInfo;
  private TextAppearanceSpan b;
  
  public TextSuggestionsPopupWindow(Context paramContext, TextSuggestionHost paramTextSuggestionHost, WindowAndroid paramWindowAndroid, View paramView)
  {
    super(paramContext, paramTextSuggestionHost, paramWindowAndroid, paramView);
    this.jdField_a_of_type_AndroidTextStyleTextAppearanceSpan = new TextAppearanceSpan(paramContext, 0);
    this.b = new TextAppearanceSpan(paramContext, 0);
  }
  
  protected void applySuggestion(int paramInt)
  {
    SuggestionInfo localSuggestionInfo = this.jdField_a_of_type_ArrayOfOrgChromiumContentBrowserInputSuggestionInfo[paramInt];
    this.jdField_a_of_type_OrgChromiumContentBrowserInputTextSuggestionHost.applyTextSuggestion(localSuggestionInfo.getMarkerTag(), localSuggestionInfo.getSuggestionIndex());
  }
  
  protected Object getSuggestionItem(int paramInt)
  {
    return this.jdField_a_of_type_ArrayOfOrgChromiumContentBrowserInputSuggestionInfo[paramInt];
  }
  
  protected SpannableString getSuggestionText(int paramInt)
  {
    SuggestionInfo localSuggestionInfo = this.jdField_a_of_type_ArrayOfOrgChromiumContentBrowserInputSuggestionInfo[paramInt];
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(localSuggestionInfo.getPrefix());
    ((StringBuilder)localObject).append(localSuggestionInfo.getSuggestion());
    ((StringBuilder)localObject).append(localSuggestionInfo.getSuffix());
    localObject = new SpannableString(((StringBuilder)localObject).toString());
    ((SpannableString)localObject).setSpan(this.jdField_a_of_type_AndroidTextStyleTextAppearanceSpan, 0, localSuggestionInfo.getPrefix().length(), 33);
    ((SpannableString)localObject).setSpan(this.b, localSuggestionInfo.getPrefix().length() + localSuggestionInfo.getSuggestion().length(), localSuggestionInfo.getPrefix().length() + localSuggestionInfo.getSuggestion().length() + localSuggestionInfo.getSuffix().length(), 33);
    return (SpannableString)localObject;
  }
  
  protected int getSuggestionsCount()
  {
    return this.jdField_a_of_type_ArrayOfOrgChromiumContentBrowserInputSuggestionInfo.length;
  }
  
  public void show(double paramDouble1, double paramDouble2, String paramString, SuggestionInfo[] paramArrayOfSuggestionInfo)
  {
    this.jdField_a_of_type_ArrayOfOrgChromiumContentBrowserInputSuggestionInfo = ((SuggestionInfo[])paramArrayOfSuggestionInfo.clone());
    a(false);
    super.a(paramDouble1, paramDouble2, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\TextSuggestionsPopupWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */