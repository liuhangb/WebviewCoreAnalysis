package com.tencent.smtt.webkit.icu;

import android.icu.text.RuleBasedCollator;
import android.icu.text.StringSearch;
import android.os.Build.VERSION;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyStringSearcher
{
  private static final String TAG = "ICU_PROXY_LOG(Java_StringSearcher)";
  private static boolean isSupportRef;
  private StringSearch mStringSearch = null;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 24) {
      bool = true;
    } else {
      bool = false;
    }
    isSupportRef = bool;
  }
  
  ICUProxyStringSearcher(String paramString1, String paramString2, String paramString3)
  {
    try
    {
      this.mStringSearch = new StringSearch(paramString1, new StringCharacterIterator(paramString2), ICUProxy.GetLocale(paramString3));
      this.mStringSearch.getCollator().setStrength(0);
      return;
    }
    catch (Exception paramString1)
    {
      paramString2 = new StringBuilder();
      paramString2.append("ICUProxyStringSearcher:");
      paramString2.append(paramString1.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", paramString2.toString());
    }
  }
  
  @CalledByNative
  public static ICUProxyStringSearcher createSearcher(String paramString1, String paramString2, String paramString3)
  {
    return new ICUProxyStringSearcher(paramString1, paramString2, paramString3);
  }
  
  @CalledByNative
  public int first()
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return -1;
    }
    try
    {
      int i = localStringSearch.first();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("first:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return -1;
  }
  
  @CalledByNative
  public int following(int paramInt)
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return -1;
    }
    try
    {
      paramInt = localStringSearch.following(paramInt);
      return paramInt;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("following:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return -1;
  }
  
  @CalledByNative
  public int getIndex()
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return -1;
    }
    try
    {
      int i = localStringSearch.getIndex();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getIndex:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return -1;
  }
  
  @CalledByNative
  public int getMatchStart()
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return -1;
    }
    try
    {
      int i = localStringSearch.getMatchStart();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getMatchStart:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return -1;
  }
  
  @CalledByNative
  public int getMatchedLength()
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return 0;
    }
    try
    {
      int i = localStringSearch.getMatchLength();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getMatchedLength:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return 0;
  }
  
  @CalledByNative
  public String getMatchedText()
  {
    Object localObject = this.mStringSearch;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = ((StringSearch)localObject).getMatchedText();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getMatchedText:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public String getPattern()
  {
    Object localObject = this.mStringSearch;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = ((StringSearch)localObject).getPattern();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getPattern:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public String getTarget()
  {
    Object localObject1 = this.mStringSearch;
    if (localObject1 == null) {
      return null;
    }
    try
    {
      localObject1 = ((StringSearch)localObject1).getTarget();
      localObject2 = new StringBuffer();
      int j;
      for (int i = ((CharacterIterator)localObject1).first(); i != 65535; j = ((CharacterIterator)localObject1).next()) {
        ((StringBuffer)localObject2).append(i);
      }
      localObject1 = ((StringBuffer)localObject2).toString();
      return (String)localObject1;
    }
    catch (Exception localException)
    {
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("getTarget:");
      ((StringBuilder)localObject2).append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", ((StringBuilder)localObject2).toString());
    }
    return null;
  }
  
  @CalledByNative
  public int last()
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return -1;
    }
    try
    {
      int i = localStringSearch.last();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("last:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return -1;
  }
  
  @CalledByNative
  public int next()
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return -1;
    }
    try
    {
      int i = localStringSearch.next();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("next:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return -1;
  }
  
  @CalledByNative
  public int preceding(int paramInt)
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return -1;
    }
    try
    {
      paramInt = localStringSearch.preceding(paramInt);
      return paramInt;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("preceding:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return -1;
  }
  
  @CalledByNative
  public int previous()
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return -1;
    }
    try
    {
      int i = localStringSearch.previous();
      return i;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("last:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
    return -1;
  }
  
  @CalledByNative
  public void reset()
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return;
    }
    try
    {
      localStringSearch.reset();
      return;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("reset:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
  }
  
  @CalledByNative
  public void setIndex(int paramInt)
  {
    StringSearch localStringSearch = this.mStringSearch;
    if (localStringSearch == null) {
      return;
    }
    try
    {
      localStringSearch.setIndex(paramInt);
      return;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setIndex:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
  }
  
  @CalledByNative
  public void setPattern(String paramString)
  {
    Object localObject = this.mStringSearch;
    if (localObject == null) {
      return;
    }
    try
    {
      ((StringSearch)localObject).setPattern(paramString);
      return;
    }
    catch (Exception paramString)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("setPattern:");
      ((StringBuilder)localObject).append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", ((StringBuilder)localObject).toString());
    }
  }
  
  @CalledByNative
  public void setTarget(String paramString)
  {
    if (this.mStringSearch == null) {
      return;
    }
    paramString = new StringCharacterIterator(paramString);
    try
    {
      this.mStringSearch.setTarget(paramString);
      return;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("setTarget:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_StringSearcher)", localStringBuilder.toString());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyStringSearcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */