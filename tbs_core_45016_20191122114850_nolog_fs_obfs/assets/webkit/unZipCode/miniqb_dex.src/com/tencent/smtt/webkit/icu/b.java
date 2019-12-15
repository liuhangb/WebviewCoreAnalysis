package com.tencent.smtt.webkit.icu;

import android.text.TextUtils;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import org.chromium.tencent.utils.PersistLog;

public class b
  extends BrkIter
{
  String jdField_a_of_type_JavaLangString;
  BreakIterator jdField_a_of_type_JavaTextBreakIterator;
  
  b(BreakIterator paramBreakIterator)
  {
    this.jdField_a_of_type_JavaTextBreakIterator = paramBreakIterator;
  }
  
  public static b a(String paramString)
  {
    return new b(BreakIterator.getLineInstance(ICUProxy.GetLocale(paramString)));
  }
  
  public static String[] a()
  {
    ArrayList localArrayList = new ArrayList();
    Locale[] arrayOfLocale = BreakIterator.getAvailableLocales();
    int j = arrayOfLocale.length;
    int i = 0;
    while (i < j)
    {
      String str = ICUProxy.getLocaleName(arrayOfLocale[i]);
      if (!TextUtils.isEmpty(str)) {
        localArrayList.add(str);
      }
      i += 1;
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public static b b(String paramString)
  {
    return new b(BreakIterator.getWordInstance(ICUProxy.GetLocale(paramString)));
  }
  
  public static b c(String paramString)
  {
    return new b(BreakIterator.getCharacterInstance(ICUProxy.GetLocale(paramString)));
  }
  
  public static b d(String paramString)
  {
    return new b(BreakIterator.getSentenceInstance(ICUProxy.GetLocale(paramString)));
  }
  
  public static b e(String paramString)
  {
    return new b(BreakIterator.getSentenceInstance(ICUProxy.GetLocale(paramString)));
  }
  
  public int current()
  {
    return this.jdField_a_of_type_JavaTextBreakIterator.current();
  }
  
  public int first()
  {
    return this.jdField_a_of_type_JavaTextBreakIterator.first();
  }
  
  public int following(int paramInt)
  {
    try
    {
      if (paramInt >= this.jdField_a_of_type_JavaTextBreakIterator.first())
      {
        if (paramInt > this.jdField_a_of_type_JavaTextBreakIterator.last()) {
          return -1;
        }
        paramInt = this.jdField_a_of_type_JavaTextBreakIterator.following(paramInt);
        return paramInt;
      }
      return -1;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("following:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorOld)", localStringBuilder.toString());
    }
    return -1;
  }
  
  public int getRuleStatus()
  {
    return 0;
  }
  
  public boolean isBoundary(int paramInt)
  {
    return this.jdField_a_of_type_JavaTextBreakIterator.isBoundary(paramInt);
  }
  
  public boolean isWord()
  {
    int i = this.jdField_a_of_type_JavaTextBreakIterator.current();
    int j = this.jdField_a_of_type_JavaTextBreakIterator.previous();
    if ((!jdField_a_of_type_Boolean) && (i != this.jdField_a_of_type_JavaTextBreakIterator.next())) {
      throw new AssertionError();
    }
    if (i != -1)
    {
      if (j == -1) {
        return false;
      }
      Object localObject = this.jdField_a_of_type_JavaLangString.substring(j, i);
      if (((String)localObject).isEmpty()) {
        return false;
      }
      if (Character.isLetterOrDigit(((String)localObject).charAt(0))) {
        return true;
      }
      localObject = Character.UnicodeBlock.of(((String)localObject).charAt(0));
      if ((localObject != Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) && (localObject != Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT) && (localObject != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) && (localObject != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) && (localObject != Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) && (localObject != Character.UnicodeBlock.KATAKANA)) {
        return localObject == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS;
      }
      return true;
    }
    return false;
  }
  
  public int last()
  {
    return this.jdField_a_of_type_JavaTextBreakIterator.last();
  }
  
  public int next()
  {
    return this.jdField_a_of_type_JavaTextBreakIterator.next();
  }
  
  public int preceding(int paramInt)
  {
    return this.jdField_a_of_type_JavaTextBreakIterator.preceding(paramInt);
  }
  
  public int previous()
  {
    return this.jdField_a_of_type_JavaTextBreakIterator.previous();
  }
  
  public void setText(String paramString)
  {
    this.jdField_a_of_type_JavaTextBreakIterator.setText(paramString);
    this.jdField_a_of_type_JavaLangString = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */