package com.tencent.smtt.webkit.icu;

import android.icu.text.NumberingSystem;
import android.os.Build.VERSION;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyNumberingSystem
{
  private static final String TAG = "ICU_PROXY_LOG(Java_ICUProxyNumberingSystem)";
  private static boolean isSupportRef;
  private NumberingSystem mInternalObject;
  
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
  
  ICUProxyNumberingSystem(NumberingSystem paramNumberingSystem)
  {
    this.mInternalObject = paramNumberingSystem;
  }
  
  @CalledByNative
  public static ICUProxyNumberingSystem getInstance(String paramString)
  {
    try
    {
      paramString = new ICUProxyNumberingSystem(NumberingSystem.getInstance(ICUProxy.GetLocale(paramString)));
      return paramString;
    }
    catch (Exception paramString)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getInstance:");
      localStringBuilder.append(paramString.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyNumberingSystem)", localStringBuilder.toString());
    }
    return null;
  }
  
  public Object getInternalObject()
  {
    return this.mInternalObject;
  }
  
  @CalledByNative
  public String getName()
  {
    Object localObject = this.mInternalObject;
    if (localObject == null) {
      return null;
    }
    try
    {
      localObject = ((NumberingSystem)localObject).getName();
      return (String)localObject;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getName:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyNumberingSystem)", localStringBuilder.toString());
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyNumberingSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */