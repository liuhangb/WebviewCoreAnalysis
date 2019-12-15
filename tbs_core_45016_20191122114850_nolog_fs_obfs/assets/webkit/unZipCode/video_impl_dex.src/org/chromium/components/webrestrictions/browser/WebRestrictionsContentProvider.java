package org.chromium.components.webrestrictions.browser;

import android.content.ContentProvider;
import android.database.AbstractCursor;
import java.util.regex.Pattern;

public abstract class WebRestrictionsContentProvider
  extends ContentProvider
{
  private final Pattern a = Pattern.compile("\\s*url\\s*=\\s*'([^']*)'");
  
  protected abstract boolean canInsert();
  
  protected abstract boolean contentProviderEnabled();
  
  protected abstract String[] getErrorColumnNames();
  
  protected abstract boolean requestInsert(String paramString);
  
  protected abstract WebRestrictionsResult shouldProceed(String paramString1, String paramString2);
  
  public static class WebRestrictionsResult
  {
    private final int[] jdField_a_of_type_ArrayOfInt;
    private final String[] jdField_a_of_type_ArrayOfJavaLangString;
    private final boolean b;
    
    public int a()
    {
      int[] arrayOfInt = this.jdField_a_of_type_ArrayOfInt;
      if (arrayOfInt == null) {
        return 0;
      }
      return arrayOfInt.length;
    }
    
    public int a(int paramInt)
    {
      int[] arrayOfInt = this.jdField_a_of_type_ArrayOfInt;
      if ((arrayOfInt != null) && (paramInt < arrayOfInt.length)) {
        return arrayOfInt[paramInt];
      }
      return 0;
    }
    
    public String a(int paramInt)
    {
      String[] arrayOfString = this.jdField_a_of_type_ArrayOfJavaLangString;
      if ((arrayOfString != null) && (paramInt < arrayOfString.length)) {
        return arrayOfString[paramInt];
      }
      return null;
    }
    
    public boolean a()
    {
      return this.b;
    }
    
    public int b()
    {
      String[] arrayOfString = this.jdField_a_of_type_ArrayOfJavaLangString;
      if (arrayOfString == null) {
        return 0;
      }
      return arrayOfString.length;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\webrestrictions\browser\WebRestrictionsContentProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */