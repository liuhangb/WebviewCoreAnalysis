package com.tencent.smtt.webkit.icu;

import android.icu.lang.UScript;
import android.os.Build.VERSION;
import java.util.BitSet;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.tencent.utils.PersistLog;

@JNINamespace("tencent")
public class ICUProxyUScript
{
  private static final String TAG = "ICU_PROXY_LOG(Java_ICUProxyUScript)";
  private static boolean isSupportRef;
  
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
  
  @CalledByNative
  public static String GetName(int paramInt)
  {
    try
    {
      String str = UScript.getName(paramInt);
      return str;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("GetName:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyUScript)", localStringBuilder.toString());
    }
    return null;
  }
  
  @CalledByNative
  public static int GetScript(int paramInt)
  {
    try
    {
      paramInt = Integer.valueOf(UScript.getScript(paramInt)).intValue();
      return paramInt;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("GetScript:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyUScript)", localStringBuilder.toString());
    }
    return -1;
  }
  
  @CalledByNative
  public static int[] GetScriptExtensions(int paramInt1, int paramInt2)
  {
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = 103;
    if (isSupportRef)
    {
      if (paramInt2 <= 0) {
        return arrayOfInt;
      }
      Object localObject = new BitSet();
      try
      {
        paramInt1 = UScript.getScriptExtensions(paramInt1, (BitSet)localObject);
        if (paramInt1 == 0) {
          return arrayOfInt;
        }
        if (paramInt1 > 0) {
          return GetScriptFromBitSet((BitSet)localObject, 1);
        }
        if (paramInt1 < 0)
        {
          localObject = GetScriptFromBitSet((BitSet)localObject, -paramInt1);
          return (int[])localObject;
        }
      }
      catch (Exception localException)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("GetScriptExtensions:");
        localStringBuilder.append(localException.getMessage());
        PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyUScript)", localStringBuilder.toString());
      }
      return arrayOfInt;
    }
    return arrayOfInt;
  }
  
  private static int[] GetScriptFromBitSet(BitSet paramBitSet, int paramInt)
  {
    int j = paramBitSet.cardinality();
    int i = paramInt;
    if (paramInt > j) {
      i = j;
    }
    int[] arrayOfInt = new int[i];
    j = 0;
    for (int k = 0; j < paramBitSet.size(); k = paramInt)
    {
      paramInt = k;
      if (paramBitSet.get(j))
      {
        arrayOfInt[k] = j;
        k += 1;
        paramInt = k;
        if (k >= i) {
          return arrayOfInt;
        }
      }
      j += 1;
    }
    return arrayOfInt;
  }
  
  @CalledByNative
  public static String GetShortName(int paramInt)
  {
    try
    {
      String str = UScript.getShortName(paramInt);
      return str;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("GetShortName:");
      localStringBuilder.append(localException.getMessage());
      PersistLog.e("ICU_PROXY_LOG(Java_ICUProxyUScript)", localStringBuilder.toString());
    }
    return new String();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\ICUProxyUScript.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */