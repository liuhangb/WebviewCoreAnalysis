package org.chromium.content.browser.selection;

import java.text.BreakIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.chromium.base.VisibleForTesting;

public class SelectionIndicesConverter
{
  private static final Pattern jdField_a_of_type_JavaUtilRegexPattern = Pattern.compile("[\\p{javaSpaceChar}\\s]+");
  private int jdField_a_of_type_Int;
  private String jdField_a_of_type_JavaLangString;
  private int jdField_b_of_type_Int;
  private String jdField_b_of_type_JavaLangString;
  private int c;
  
  private void a(String paramString, int paramInt)
  {
    this.jdField_b_of_type_JavaLangString = paramString;
    this.jdField_b_of_type_Int = paramInt;
  }
  
  @VisibleForTesting
  protected static boolean a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 <= paramInt3) {
      return paramInt3 < paramInt2;
    }
    return paramInt4 > paramInt1;
  }
  
  private void b(String paramString, int paramInt)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  private void c(String paramString, int paramInt)
  {
    int i = paramString.length();
    int j = this.jdField_a_of_type_Int + this.jdField_a_of_type_JavaLangString.length();
    StringBuilder localStringBuilder;
    if (paramInt < this.jdField_a_of_type_Int)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString.substring(0, this.jdField_a_of_type_Int - paramInt));
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      b(localStringBuilder.toString(), paramInt);
    }
    if (i + paramInt > j)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(this.jdField_a_of_type_JavaLangString);
      localStringBuilder.append(paramString.substring(j - paramInt, paramString.length()));
      b(localStringBuilder.toString(), this.jdField_a_of_type_Int);
    }
  }
  
  @VisibleForTesting
  protected int a(int paramInt1, int paramInt2, BreakIterator paramBreakIterator)
  {
    if ((!jdField_a_of_type_Boolean) && (paramInt1 < paramInt2)) {
      throw new AssertionError();
    }
    int j = 0;
    int i = paramInt1;
    for (paramInt1 = j; i > paramInt2; paramInt1 = j)
    {
      int k = paramBreakIterator.preceding(i);
      j = paramInt1;
      if (!a(k, i)) {
        j = paramInt1 + 1;
      }
      i = k;
    }
    return paramInt1;
  }
  
  @VisibleForTesting
  protected boolean a(int paramInt1, int paramInt2)
  {
    return jdField_a_of_type_JavaUtilRegexPattern.matcher(this.jdField_a_of_type_JavaLangString.substring(paramInt1, paramInt2)).matches();
  }
  
  @VisibleForTesting
  protected int b(int paramInt1, int paramInt2, BreakIterator paramBreakIterator)
  {
    if ((!jdField_a_of_type_Boolean) && (paramInt1 > paramInt2)) {
      throw new AssertionError();
    }
    int j = 0;
    int i = paramInt1;
    for (paramInt1 = j; i < paramInt2; paramInt1 = j)
    {
      int k = paramBreakIterator.following(i);
      j = paramInt1;
      if (!a(i, k)) {
        j = paramInt1 + 1;
      }
      i = k;
    }
    return paramInt1;
  }
  
  public boolean getWordDelta(int paramInt1, int paramInt2, int[] paramArrayOfInt)
  {
    if ((!jdField_a_of_type_Boolean) && (paramArrayOfInt.length != 2)) {
      throw new AssertionError();
    }
    paramArrayOfInt[1] = 0;
    paramArrayOfInt[0] = 0;
    int i = this.jdField_a_of_type_Int;
    paramInt1 -= i;
    paramInt2 -= i;
    if (paramInt1 >= paramInt2) {
      return false;
    }
    if (paramInt1 >= 0)
    {
      if (paramInt1 >= this.jdField_a_of_type_JavaLangString.length()) {
        return false;
      }
      if (paramInt2 > 0)
      {
        if (paramInt2 > this.jdField_a_of_type_JavaLangString.length()) {
          return false;
        }
        i = this.c - this.jdField_a_of_type_Int;
        BreakIterator localBreakIterator = BreakIterator.getWordInstance();
        localBreakIterator.setText(this.jdField_a_of_type_JavaLangString);
        if (paramInt1 <= i)
        {
          paramArrayOfInt[0] = (-b(paramInt1, i, localBreakIterator));
        }
        else
        {
          paramArrayOfInt[0] = a(paramInt1, i, localBreakIterator);
          if ((!localBreakIterator.isBoundary(paramInt1)) && (!a(localBreakIterator.preceding(paramInt1), localBreakIterator.following(paramInt1)))) {
            paramArrayOfInt[0] -= 1;
          }
        }
        if (paramInt2 <= i)
        {
          paramArrayOfInt[1] = (-b(paramInt2, i, localBreakIterator));
          return true;
        }
        paramArrayOfInt[1] = a(paramInt2, i, localBreakIterator);
        return true;
      }
      return false;
    }
    return false;
  }
  
  public void setInitialStartOffset(int paramInt)
  {
    this.c = paramInt;
  }
  
  public boolean updateSelectionState(String paramString, int paramInt)
  {
    if (this.jdField_a_of_type_JavaLangString == null)
    {
      a(paramString, paramInt);
      b(paramString, paramInt);
      return true;
    }
    int i = paramString.length() + paramInt;
    int j = this.jdField_b_of_type_Int + this.jdField_b_of_type_JavaLangString.length();
    boolean bool;
    if (a(this.jdField_b_of_type_Int, j, paramInt, i))
    {
      int k = Math.max(this.jdField_b_of_type_Int, paramInt);
      int m = Math.min(j, i);
      bool = this.jdField_b_of_type_JavaLangString.regionMatches(k - this.jdField_b_of_type_Int, paramString, k - paramInt, m - k);
    }
    else
    {
      bool = false;
    }
    if ((this.jdField_b_of_type_Int == i) || (j == paramInt)) {
      bool = true;
    }
    if (!bool)
    {
      this.jdField_a_of_type_JavaLangString = null;
      this.jdField_b_of_type_JavaLangString = null;
      return false;
    }
    a(paramString, paramInt);
    c(paramString, paramInt);
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\SelectionIndicesConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */