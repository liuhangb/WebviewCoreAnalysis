package org.chromium.components.autofill;

import android.util.SparseArray;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.autofill.AutofillValue;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content_public.browser.WebContents;

@JNINamespace("autofill")
public abstract class AutofillProvider
{
  private native void nativeOnAutofillAvailable(long paramLong, FormData paramFormData);
  
  protected void a(long paramLong, FormData paramFormData)
  {
    nativeOnAutofillAvailable(paramLong, paramFormData);
  }
  
  public abstract void autofill(SparseArray<AutofillValue> paramSparseArray);
  
  public abstract void onContainerViewChanged(ViewGroup paramViewGroup);
  
  @CalledByNative
  protected abstract void onDidFillAutofillFormData();
  
  @CalledByNative
  protected abstract void onFocusChanged(boolean paramBoolean, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  @CalledByNative
  protected abstract void onFormSubmitted(int paramInt);
  
  public abstract void onProvideAutoFillVirtualStructure(ViewStructure paramViewStructure, int paramInt);
  
  @CalledByNative
  protected abstract void onTextFieldDidChange(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  @CalledByNative
  protected abstract void onTextFieldDidScroll(int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
  
  public abstract void queryAutofillSuggestion();
  
  @CalledByNative
  protected abstract void reset();
  
  @CalledByNative
  protected abstract void setNativeAutofillProvider(long paramLong);
  
  public abstract void setWebContents(WebContents paramWebContents);
  
  public abstract boolean shouldQueryAutofillSuggestion();
  
  @CalledByNative
  protected abstract void startAutofillSession(FormData paramFormData, int paramInt, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\autofill\AutofillProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */