package org.chromium.components.variations;

import org.chromium.base.annotations.JNINamespace;

@JNINamespace("variations::android")
public final class VariationsAssociatedData
{
  private static native String nativeGetFeedbackVariations();
  
  private static native String nativeGetVariationParamValue(String paramString1, String paramString2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\variations\VariationsAssociatedData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */