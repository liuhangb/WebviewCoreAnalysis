package com.tencent.smtt.webkit.icu;

import android.text.TextUtils;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Currency;
import java.util.Locale;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.CalledByNativeUnchecked;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyNumberFormat
{
  private static final String TAG = "ICU_PROXY_LOG(Java_ICUProxyNumberFormat)";
  private NumberFormat m_obj = null;
  
  ICUProxyNumberFormat(NumberFormat paramNumberFormat)
  {
    this.m_obj = paramNumberFormat;
  }
  
  @CalledByNative
  public static String[] GetAvailableLocales()
  {
    ArrayList localArrayList = new ArrayList();
    Locale[] arrayOfLocale = Locale.getAvailableLocales();
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
  public static ICUProxyNumberFormat createCurrencyInstance(String paramString)
  {
    return new ICUProxyNumberFormat(NumberFormat.getCurrencyInstance(ICUProxy.GetLocale(paramString)));
  }
  
  @CalledByNative
  public static ICUProxyNumberFormat createInstance(String paramString)
  {
    return new ICUProxyNumberFormat(NumberFormat.getInstance(ICUProxy.GetLocale(paramString)));
  }
  
  @CalledByNative
  public static ICUProxyNumberFormat createPercentInstance(String paramString)
  {
    return new ICUProxyNumberFormat(NumberFormat.getPercentInstance(ICUProxy.GetLocale(paramString)));
  }
  
  @CalledByNative
  public String format(double paramDouble)
  {
    NumberFormat localNumberFormat = this.m_obj;
    if (localNumberFormat != null) {
      return localNumberFormat.format(paramDouble);
    }
    return null;
  }
  
  @CalledByNative
  public String getCurrency()
  {
    Object localObject = this.m_obj;
    if (localObject == null) {
      return null;
    }
    localObject = ((NumberFormat)localObject).getCurrency();
    if (localObject == null) {
      return null;
    }
    return ((Currency)localObject).getSymbol();
  }
  
  @CalledByNative
  public int getDefaultFractionDigits()
  {
    Object localObject = this.m_obj;
    if (localObject == null) {
      return 0;
    }
    localObject = ((NumberFormat)localObject).getCurrency();
    if (localObject == null) {
      return 0;
    }
    return ((Currency)localObject).getDefaultFractionDigits();
  }
  
  @CalledByNative
  public int getMaximumFractionDigits()
  {
    NumberFormat localNumberFormat = this.m_obj;
    if (localNumberFormat == null) {
      return 0;
    }
    return localNumberFormat.getMaximumFractionDigits();
  }
  
  @CalledByNative
  public int getMaximumSignificantDigits()
  {
    return 0;
  }
  
  @CalledByNative
  public int getMinimumFractionDigits()
  {
    NumberFormat localNumberFormat = this.m_obj;
    if (localNumberFormat == null) {
      return 0;
    }
    return localNumberFormat.getMinimumFractionDigits();
  }
  
  @CalledByNative
  public int getMinimumIntegerDigits()
  {
    NumberFormat localNumberFormat = this.m_obj;
    if (localNumberFormat == null) {
      return 0;
    }
    return localNumberFormat.getMinimumIntegerDigits();
  }
  
  @CalledByNative
  public int getMinimumSignificantDigits()
  {
    return 0;
  }
  
  @CalledByNative
  public boolean isGroupingUsed()
  {
    NumberFormat localNumberFormat = this.m_obj;
    if (localNumberFormat == null) {
      return false;
    }
    return localNumberFormat.isGroupingUsed();
  }
  
  @CalledByNativeUnchecked
  public double parse(String paramString)
    throws ParseException
  {
    return this.m_obj.parse(paramString).doubleValue();
  }
  
  @CalledByNative
  public void setCurrency(String paramString)
  {
    if (this.m_obj == null) {
      return;
    }
    try
    {
      paramString = Currency.getInstance(paramString);
    }
    catch (Exception paramString)
    {
      PersistLog.printStackTrace(paramString);
      paramString = Currency.getInstance(Locale.getDefault());
    }
    this.m_obj.setCurrency(paramString);
  }
  
  @CalledByNative
  public void setGroupingUsed(int paramInt)
  {
    NumberFormat localNumberFormat = this.m_obj;
    if (localNumberFormat == null) {
      return;
    }
    boolean bool;
    if (paramInt > 0) {
      bool = true;
    } else {
      bool = false;
    }
    localNumberFormat.setGroupingUsed(bool);
  }
  
  @CalledByNative
  public void setMaximumFractionDigits(int paramInt)
  {
    NumberFormat localNumberFormat = this.m_obj;
    if (localNumberFormat == null) {
      return;
    }
    localNumberFormat.setMaximumFractionDigits(paramInt);
  }
  
  @CalledByNative
  public void setMaximumSignificantDigits(int paramInt) {}
  
  @CalledByNative
  public void setMinimumFractionDigits(int paramInt)
  {
    NumberFormat localNumberFormat = this.m_obj;
    if (localNumberFormat == null) {
      return;
    }
    localNumberFormat.setMinimumFractionDigits(paramInt);
  }
  
  @CalledByNative
  public void setMinimumIntegerDigits(int paramInt)
  {
    NumberFormat localNumberFormat = this.m_obj;
    if (localNumberFormat == null) {
      return;
    }
    localNumberFormat.setMinimumIntegerDigits(paramInt);
  }
  
  @CalledByNative
  public void setMinimumSignificantDigits(int paramInt) {}
  
  @CalledByNative
  public void setRoundingMode(int paramInt)
  {
    if (this.m_obj == null) {
      return;
    }
    RoundingMode localRoundingMode;
    switch (paramInt)
    {
    default: 
      localRoundingMode = RoundingMode.CEILING;
      break;
    case 6: 
      localRoundingMode = RoundingMode.HALF_UP;
      break;
    case 5: 
      localRoundingMode = RoundingMode.CEILING;
      break;
    case 4: 
      localRoundingMode = RoundingMode.HALF_EVEN;
      break;
    case 3: 
      localRoundingMode = RoundingMode.UP;
      break;
    case 2: 
      localRoundingMode = RoundingMode.DOWN;
      break;
    case 1: 
      localRoundingMode = RoundingMode.FLOOR;
      break;
    case 0: 
      localRoundingMode = RoundingMode.CEILING;
    }
    this.m_obj.setRoundingMode(localRoundingMode);
  }
  
  @CalledByNative
  public void setSignificantDigitsUsed(int paramInt) {}
  
  @CalledByNative
  public String toPattern()
  {
    NumberFormat localNumberFormat = this.m_obj;
    if ((localNumberFormat instanceof DecimalFormat)) {
      return ((DecimalFormat)localNumberFormat).toPattern();
    }
    return "#,##0.#";
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyNumberFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */