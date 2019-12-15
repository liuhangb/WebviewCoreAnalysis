package com.tencent.smtt.webkit.icu;

import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class ICUProxyBreakIterator
{
  private static final String TAG = "ICU_PROXY_LOG(Java_BreakIterator)";
  private static boolean isSupportRef = false;
  BrkIter m_obj;
  
  ICUProxyBreakIterator(BrkIter paramBrkIter)
  {
    this.m_obj = paramBrkIter;
  }
  
  @CalledByNative
  public static String[] GetAvailableLocales()
  {
    if (isSupportRef) {
      return a.a();
    }
    return b.a();
  }
  
  @CalledByNative
  private static ICUProxyBreakIterator createCharacterInstance(String paramString)
  {
    if (isSupportRef) {
      return new ICUProxyBreakIterator(a.c(paramString));
    }
    return new ICUProxyBreakIterator(b.c(paramString));
  }
  
  @CalledByNative
  public static ICUProxyBreakIterator createLineInstance(String paramString)
  {
    if (isSupportRef) {
      return new ICUProxyBreakIterator(a.a(paramString));
    }
    return new ICUProxyBreakIterator(b.a(paramString));
  }
  
  @CalledByNative
  public static ICUProxyBreakIterator createSentenceInstance(String paramString)
  {
    if (isSupportRef) {
      return new ICUProxyBreakIterator(a.d(paramString));
    }
    return new ICUProxyBreakIterator(b.d(paramString));
  }
  
  @CalledByNative
  public static ICUProxyBreakIterator createTitleInstance(String paramString)
  {
    if (isSupportRef) {
      return new ICUProxyBreakIterator(a.e(paramString));
    }
    return new ICUProxyBreakIterator(b.e(paramString));
  }
  
  @CalledByNative
  public static ICUProxyBreakIterator createWordInstance(String paramString)
  {
    if (isSupportRef) {
      return new ICUProxyBreakIterator(a.b(paramString));
    }
    return new ICUProxyBreakIterator(b.b(paramString));
  }
  
  @CalledByNative
  public int current()
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return 0;
    }
    return localBrkIter.current();
  }
  
  @CalledByNative
  public int first()
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return 0;
    }
    return localBrkIter.first();
  }
  
  @CalledByNative
  public int following(int paramInt)
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return 0;
    }
    return localBrkIter.following(paramInt);
  }
  
  @CalledByNative
  public int getRuleStatus()
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return 0;
    }
    return localBrkIter.getRuleStatus();
  }
  
  @CalledByNative
  public boolean isBoundary(int paramInt)
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return false;
    }
    return localBrkIter.isBoundary(paramInt);
  }
  
  @CalledByNative
  public boolean isWord()
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return false;
    }
    return localBrkIter.isWord();
  }
  
  @CalledByNative
  public int last()
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return 0;
    }
    return localBrkIter.last();
  }
  
  @CalledByNative
  public int next()
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return 0;
    }
    return localBrkIter.next();
  }
  
  @CalledByNative
  public int preceding(int paramInt)
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return 0;
    }
    return localBrkIter.preceding(paramInt);
  }
  
  @CalledByNative
  public int previous()
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return 0;
    }
    return localBrkIter.previous();
  }
  
  @CalledByNative
  public void setText(String paramString)
  {
    BrkIter localBrkIter = this.m_obj;
    if (localBrkIter == null) {
      return;
    }
    localBrkIter.setText(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyBreakIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */