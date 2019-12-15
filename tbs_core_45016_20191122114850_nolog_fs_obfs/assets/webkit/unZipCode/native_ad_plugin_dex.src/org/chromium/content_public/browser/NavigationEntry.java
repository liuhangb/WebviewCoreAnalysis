package org.chromium.content_public.browser;

import android.graphics.Bitmap;

public class NavigationEntry
{
  private final int jdField_a_of_type_Int;
  private Bitmap jdField_a_of_type_AndroidGraphicsBitmap;
  private final String jdField_a_of_type_JavaLangString;
  private final int jdField_b_of_type_Int;
  private final String jdField_b_of_type_JavaLangString;
  private int jdField_c_of_type_Int;
  private final String jdField_c_of_type_JavaLangString;
  private final String d;
  
  public NavigationEntry(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, String paramString4, Bitmap paramBitmap, int paramInt3)
  {
    this.jdField_a_of_type_Int = paramInt1;
    this.jdField_b_of_type_Int = paramInt2;
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_c_of_type_JavaLangString = paramString2;
    this.jdField_b_of_type_JavaLangString = paramString3;
    this.d = paramString4;
    this.jdField_a_of_type_AndroidGraphicsBitmap = paramBitmap;
    this.jdField_c_of_type_Int = paramInt3;
  }
  
  public Bitmap getFavicon()
  {
    return this.jdField_a_of_type_AndroidGraphicsBitmap;
  }
  
  public int getId()
  {
    return this.jdField_b_of_type_Int;
  }
  
  public int getIndex()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public String getOriginalUrl()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  public String getTitle()
  {
    return this.d;
  }
  
  public int getTransition()
  {
    return this.jdField_c_of_type_Int;
  }
  
  public String getUrl()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public String getVirtualUrl()
  {
    return this.jdField_c_of_type_JavaLangString;
  }
  
  public void updateFavicon(Bitmap paramBitmap)
  {
    this.jdField_a_of_type_AndroidGraphicsBitmap = paramBitmap;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\NavigationEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */