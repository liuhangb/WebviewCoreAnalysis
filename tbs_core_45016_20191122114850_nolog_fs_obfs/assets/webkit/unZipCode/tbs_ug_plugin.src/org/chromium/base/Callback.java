package org.chromium.base;

import org.chromium.base.annotations.CalledByNative;

public abstract interface Callback<T>
{
  public abstract void onResult(T paramT);
  
  public static abstract class Helper
  {
    @CalledByNative("Helper")
    static void onBooleanResultFromNative(Callback paramCallback, boolean paramBoolean)
    {
      paramCallback.onResult(Boolean.valueOf(paramBoolean));
    }
    
    @CalledByNative("Helper")
    static void onIntResultFromNative(Callback paramCallback, int paramInt)
    {
      paramCallback.onResult(Integer.valueOf(paramInt));
    }
    
    @CalledByNative("Helper")
    static void onObjectResultFromNative(Callback paramCallback, Object paramObject)
    {
      paramCallback.onResult(paramObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\Callback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */