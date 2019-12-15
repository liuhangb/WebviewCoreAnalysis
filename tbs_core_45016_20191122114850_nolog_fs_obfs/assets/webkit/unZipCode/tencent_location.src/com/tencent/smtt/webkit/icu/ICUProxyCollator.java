package com.tencent.smtt.webkit.icu;

import android.text.TextUtils;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Locale;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class ICUProxyCollator
{
  private static final String TAG = "ICU_PROXY_LOG(Java_ICUProxyCollator)";
  private Collator m_obj;
  
  ICUProxyCollator(Collator paramCollator)
  {
    this.m_obj = paramCollator;
  }
  
  @CalledByNative
  public static String[] GetAvailableLocales()
  {
    ArrayList localArrayList = new ArrayList();
    Locale[] arrayOfLocale = Collator.getAvailableLocales();
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
  
  @CalledByNative
  public static ICUProxyCollator createInstance(String paramString)
  {
    return new ICUProxyCollator(Collator.getInstance(ICUProxy.GetLocale(paramString)));
  }
  
  @CalledByNative
  public int compare(String paramString1, String paramString2)
  {
    Collator localCollator = this.m_obj;
    if (localCollator == null) {
      return -1;
    }
    return localCollator.compare(paramString1, paramString2);
  }
  
  @CalledByNative
  public int getStrength()
  {
    Collator localCollator = this.m_obj;
    if (localCollator == null) {
      return 0;
    }
    return localCollator.getStrength();
  }
  
  @CalledByNative
  public void setStrength(int paramInt)
  {
    if (this.m_obj == null) {
      return;
    }
    int j = 0;
    int i;
    if (paramInt != 15) {
      i = j;
    }
    switch (paramInt)
    {
    default: 
      i = j;
      break;
    case 2: 
      i = 2;
      break;
    case 1: 
      i = 1;
      break;
      i = 3;
    }
    this.m_obj.setStrength(i);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyCollator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */