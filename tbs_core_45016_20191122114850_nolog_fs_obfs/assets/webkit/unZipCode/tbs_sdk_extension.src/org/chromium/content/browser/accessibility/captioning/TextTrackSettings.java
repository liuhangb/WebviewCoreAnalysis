package org.chromium.content.browser.accessibility.captioning;

import android.annotation.TargetApi;
import java.util.Objects;

@TargetApi(19)
public final class TextTrackSettings
{
  private String jdField_a_of_type_JavaLangString;
  private boolean jdField_a_of_type_Boolean;
  private String b;
  private String c;
  private String d;
  private String e;
  private String f;
  private String g;
  
  public TextTrackSettings() {}
  
  public TextTrackSettings(boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.b = paramString2;
    this.c = paramString3;
    this.d = paramString4;
    this.e = paramString5;
    this.f = paramString6;
    this.g = paramString7;
  }
  
  public String getTextTrackBackgroundColor()
  {
    return Objects.toString(this.jdField_a_of_type_JavaLangString, "");
  }
  
  public String getTextTrackFontFamily()
  {
    return Objects.toString(this.b, "");
  }
  
  public String getTextTrackFontStyle()
  {
    return Objects.toString(this.c, "");
  }
  
  public String getTextTrackFontVariant()
  {
    return Objects.toString(this.d, "");
  }
  
  public String getTextTrackTextColor()
  {
    return Objects.toString(this.e, "");
  }
  
  public String getTextTrackTextShadow()
  {
    return Objects.toString(this.f, "");
  }
  
  public String getTextTrackTextSize()
  {
    return Objects.toString(this.g, "");
  }
  
  public boolean getTextTracksEnabled()
  {
    return this.jdField_a_of_type_Boolean;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\accessibility\captioning\TextTrackSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */