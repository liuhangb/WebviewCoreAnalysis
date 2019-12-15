package org.chromium.android_webview;

import android.util.SparseArray;
import org.chromium.base.Callback;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public class AwQuotaManagerBridge
{
  private static AwQuotaManagerBridge jdField_a_of_type_OrgChromiumAndroid_webviewAwQuotaManagerBridge;
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private SparseArray<Callback<Origins>> jdField_a_of_type_AndroidUtilSparseArray;
  private SparseArray<Callback<Long>> b;
  private SparseArray<Callback<Long>> c;
  
  private AwQuotaManagerBridge(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
    this.b = new SparseArray();
    this.c = new SparseArray();
    nativeInit(this.jdField_a_of_type_Long);
  }
  
  private int a()
  {
    ThreadUtils.assertOnUiThread();
    int i = this.jdField_a_of_type_Int + 1;
    this.jdField_a_of_type_Int = i;
    return i;
  }
  
  public static AwQuotaManagerBridge getInstance()
  {
    
    if (jdField_a_of_type_OrgChromiumAndroid_webviewAwQuotaManagerBridge == null) {
      jdField_a_of_type_OrgChromiumAndroid_webviewAwQuotaManagerBridge = new AwQuotaManagerBridge(nativeGetDefaultNativeAwQuotaManagerBridge());
    }
    return jdField_a_of_type_OrgChromiumAndroid_webviewAwQuotaManagerBridge;
  }
  
  private native void nativeDeleteAllData(long paramLong);
  
  private native void nativeDeleteOrigin(long paramLong, String paramString);
  
  private static native long nativeGetDefaultNativeAwQuotaManagerBridge();
  
  private native void nativeGetOrigins(long paramLong, int paramInt);
  
  private native void nativeGetUsageAndQuotaForOrigin(long paramLong, String paramString, int paramInt, boolean paramBoolean);
  
  private native void nativeInit(long paramLong);
  
  @CalledByNative
  private void onGetOriginsCallback(int paramInt, String[] paramArrayOfString, long[] paramArrayOfLong1, long[] paramArrayOfLong2)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidUtilSparseArray.get(paramInt) == null)) {
      throw new AssertionError();
    }
    ((Callback)this.jdField_a_of_type_AndroidUtilSparseArray.get(paramInt)).onResult(new Origins(paramArrayOfString, paramArrayOfLong1, paramArrayOfLong2));
    this.jdField_a_of_type_AndroidUtilSparseArray.remove(paramInt);
  }
  
  @CalledByNative
  private void onGetUsageAndQuotaForOriginCallback(int paramInt, boolean paramBoolean, long paramLong1, long paramLong2)
  {
    if (paramBoolean)
    {
      if ((!jdField_a_of_type_Boolean) && (this.b.get(paramInt) == null)) {
        throw new AssertionError();
      }
      ((Callback)this.b.get(paramInt)).onResult(Long.valueOf(paramLong2));
      this.b.remove(paramInt);
      return;
    }
    if ((!jdField_a_of_type_Boolean) && (this.c.get(paramInt) == null)) {
      throw new AssertionError();
    }
    ((Callback)this.c.get(paramInt)).onResult(Long.valueOf(paramLong1));
    this.c.remove(paramInt);
  }
  
  public void deleteAllData()
  {
    nativeDeleteAllData(this.jdField_a_of_type_Long);
  }
  
  public void deleteOrigin(String paramString)
  {
    nativeDeleteOrigin(this.jdField_a_of_type_Long, paramString);
  }
  
  public void getOrigins(Callback<Origins> paramCallback)
  {
    int i = a();
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidUtilSparseArray.get(i) != null)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_AndroidUtilSparseArray.put(i, paramCallback);
    nativeGetOrigins(this.jdField_a_of_type_Long, i);
  }
  
  public void getQuotaForOrigin(String paramString, Callback<Long> paramCallback)
  {
    int i = a();
    if ((!jdField_a_of_type_Boolean) && (this.b.get(i) != null)) {
      throw new AssertionError();
    }
    this.b.put(i, paramCallback);
    nativeGetUsageAndQuotaForOrigin(this.jdField_a_of_type_Long, paramString, i, true);
  }
  
  public void getUsageForOrigin(String paramString, Callback<Long> paramCallback)
  {
    int i = a();
    if ((!jdField_a_of_type_Boolean) && (this.c.get(i) != null)) {
      throw new AssertionError();
    }
    this.c.put(i, paramCallback);
    nativeGetUsageAndQuotaForOrigin(this.jdField_a_of_type_Long, paramString, i, false);
  }
  
  public static class Origins
  {
    public final String[] mOrigins;
    public final long[] mQuotas;
    public final long[] mUsages;
    
    Origins(String[] paramArrayOfString, long[] paramArrayOfLong1, long[] paramArrayOfLong2)
    {
      this.mOrigins = paramArrayOfString;
      this.mUsages = paramArrayOfLong1;
      this.mQuotas = paramArrayOfLong2;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwQuotaManagerBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */