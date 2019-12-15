package com.tencent.smtt.webkit.icu;

import android.icu.text.IDNA;
import android.icu.text.IDNA.Info;
import android.os.Build.VERSION;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyIDN
{
  private static final String TAG = "ICU_PROXY_LOG(Java_IDN)";
  private static boolean isSupportRef;
  IDNA m_obj;
  
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
  
  ICUProxyIDN(IDNA paramIDNA)
  {
    this.m_obj = paramIDNA;
  }
  
  @CalledByNative
  private static ICUProxyIDN CreateIDNInstance(int paramInt)
  {
    try
    {
      ICUProxyIDN localICUProxyIDN = new ICUProxyIDN(IDNA.getUTS46Instance(paramInt));
      return localICUProxyIDN;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("CreateIDNInstance:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_IDN)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  String LabelToUnicode(String paramString)
  {
    if (this.m_obj == null) {
      return null;
    }
    try
    {
      localObject = new IDNA.Info();
      StringBuilder localStringBuilder = new StringBuilder();
      this.m_obj.labelToUnicode(paramString, localStringBuilder, (IDNA.Info)localObject);
      paramString = localStringBuilder.toString();
      return paramString;
    }
    catch (Exception paramString)
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("LabelToUnicode:");
      ((StringBuilder)localObject).append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_IDN)", ((StringBuilder)localObject).toString());
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyIDN.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */