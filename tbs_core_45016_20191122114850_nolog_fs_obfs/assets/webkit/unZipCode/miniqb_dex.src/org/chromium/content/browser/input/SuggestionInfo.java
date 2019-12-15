package org.chromium.content.browser.input;

import org.chromium.base.annotations.CalledByNative;

public class SuggestionInfo
{
  private final int jdField_a_of_type_Int;
  private final String jdField_a_of_type_JavaLangString;
  private final int jdField_b_of_type_Int;
  private final String jdField_b_of_type_JavaLangString;
  private final String c;
  
  SuggestionInfo(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3)
  {
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_b_of_type_JavaLangString = paramString2;
    this.c = paramString3;
  }
  
  @CalledByNative
  private static SuggestionInfo[] createArray(int paramInt)
  {
    return new SuggestionInfo[paramInt];
  }
  
  @CalledByNative
  private static void createSuggestionInfoAndPutInArray(SuggestionInfo[] paramArrayOfSuggestionInfo, int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, String paramString3)
  {
    paramArrayOfSuggestionInfo[paramInt1] = new SuggestionInfo(paramInt2, paramInt3, paramString1, paramString2, paramString3);
  }
  
  public int getMarkerTag()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public String getPrefix()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public String getSuffix()
  {
    return this.c;
  }
  
  public String getSuggestion()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  public int getSuggestionIndex()
  {
    return this.jdField_b_of_type_Int;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\SuggestionInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */