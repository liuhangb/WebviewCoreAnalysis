package com.tencent.smtt.webkit.icu;

import android.icu.text.BreakIterator;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Locale;
import org.chromium.tencent.utils.PersistLog;

public class a
  extends BrkIter
{
  private BreakIterator jdField_a_of_type_AndroidIcuTextBreakIterator;
  private String jdField_a_of_type_JavaLangString;
  
  a(BreakIterator paramBreakIterator)
  {
    this.jdField_a_of_type_AndroidIcuTextBreakIterator = paramBreakIterator;
  }
  
  public static a a(String paramString)
  {
    paramString = ICUProxy.GetLocale(paramString);
    try
    {
      paramString = new a(BreakIterator.getLineInstance(paramString));
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("createLineInstance:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return null;
  }
  
  public static String[] a()
  {
    localArrayList = new ArrayList();
    try
    {
      Locale[] arrayOfLocale = BreakIterator.getAvailableLocales();
      int j = arrayOfLocale.length;
      int i = 0;
      Object localObject;
      while (i < j)
      {
        localObject = ICUProxy.getLocaleName(arrayOfLocale[i]);
        if (!TextUtils.isEmpty((CharSequence)localObject)) {
          localArrayList.add(localObject);
        }
        i += 1;
      }
      return (String[])localArrayList.toArray(new String[localArrayList.size()]);
    }
    catch (Exception localException)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("GetAvailableLocales:");
      ((StringBuilder)localObject).append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", ((StringBuilder)localObject).toString());
    }
  }
  
  public static a b(String paramString)
  {
    paramString = ICUProxy.GetLocale(paramString);
    try
    {
      paramString = new a(BreakIterator.getWordInstance(paramString));
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("createWordInstance:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return null;
  }
  
  public static a c(String paramString)
  {
    paramString = ICUProxy.GetLocale(paramString);
    try
    {
      paramString = new a(BreakIterator.getCharacterInstance(paramString));
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("createCharacterInstance:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return null;
  }
  
  public static a d(String paramString)
  {
    paramString = ICUProxy.GetLocale(paramString);
    try
    {
      paramString = new a(BreakIterator.getSentenceInstance(paramString));
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("createSentenceInstance:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return null;
  }
  
  public static a e(String paramString)
  {
    paramString = ICUProxy.GetLocale(paramString);
    try
    {
      paramString = new a(BreakIterator.getSentenceInstance(paramString));
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("createTitleInstance:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return null;
  }
  
  public int current()
  {
    BreakIterator localBreakIterator = this.jdField_a_of_type_AndroidIcuTextBreakIterator;
    if (localBreakIterator == null) {
      return 0;
    }
    try
    {
      int i = localBreakIterator.current();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("current:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return 0;
  }
  
  public int first()
  {
    BreakIterator localBreakIterator = this.jdField_a_of_type_AndroidIcuTextBreakIterator;
    if (localBreakIterator == null) {
      return 0;
    }
    try
    {
      int i = localBreakIterator.first();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("first:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return 0;
  }
  
  public int following(int paramInt)
  {
    BreakIterator localBreakIterator = this.jdField_a_of_type_AndroidIcuTextBreakIterator;
    if (localBreakIterator == null) {
      return -1;
    }
    try
    {
      paramInt = localBreakIterator.following(paramInt);
      return paramInt;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("following:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return -1;
  }
  
  public int getRuleStatus()
  {
    BreakIterator localBreakIterator = this.jdField_a_of_type_AndroidIcuTextBreakIterator;
    if (localBreakIterator == null) {
      return 0;
    }
    try
    {
      int i = localBreakIterator.getRuleStatus();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getRuleStatus:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return 0;
  }
  
  public boolean isBoundary(int paramInt)
  {
    BreakIterator localBreakIterator = this.jdField_a_of_type_AndroidIcuTextBreakIterator;
    if (localBreakIterator == null) {
      return false;
    }
    try
    {
      boolean bool = localBreakIterator.isBoundary(paramInt);
      return bool;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("isBoundary:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return false;
  }
  
  public boolean isWord()
  {
    int i = current();
    int j = previous();
    if ((!jdField_a_of_type_Boolean) && (i != next())) {
      throw new AssertionError();
    }
    if (i != -1)
    {
      if (j == -1) {
        return false;
      }
      Object localObject = this.jdField_a_of_type_JavaLangString.substring(j, i);
      if (TextUtils.isEmpty((CharSequence)localObject)) {
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
    BreakIterator localBreakIterator = this.jdField_a_of_type_AndroidIcuTextBreakIterator;
    if (localBreakIterator == null) {
      return 0;
    }
    try
    {
      int i = localBreakIterator.last();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("last:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return 0;
  }
  
  public int next()
  {
    BreakIterator localBreakIterator = this.jdField_a_of_type_AndroidIcuTextBreakIterator;
    if (localBreakIterator == null) {
      return -1;
    }
    try
    {
      int i = localBreakIterator.next();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("next:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return -1;
  }
  
  public int preceding(int paramInt)
  {
    BreakIterator localBreakIterator = this.jdField_a_of_type_AndroidIcuTextBreakIterator;
    if (localBreakIterator == null) {
      return -1;
    }
    try
    {
      paramInt = localBreakIterator.preceding(paramInt);
      return paramInt;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("preceding:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return -1;
  }
  
  public int previous()
  {
    BreakIterator localBreakIterator = this.jdField_a_of_type_AndroidIcuTextBreakIterator;
    if (localBreakIterator == null) {
      return -1;
    }
    try
    {
      int i = localBreakIterator.previous();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("previous:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", localStringBuilder.toString());
    }
    return -1;
  }
  
  public void setText(String paramString)
  {
    Object localObject = this.jdField_a_of_type_AndroidIcuTextBreakIterator;
    if (localObject == null) {
      return;
    }
    try
    {
      ((BreakIterator)localObject).setText(paramString);
      this.jdField_a_of_type_JavaLangString = paramString;
      return;
    }
    catch (Exception paramString)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("setText:");
      ((StringBuilder)localObject).append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_InternalBreakIteratorNew)", ((StringBuilder)localObject).toString());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */