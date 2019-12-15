package org.chromium.android_webview;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.components.autofill.AutofillDelegate;
import org.chromium.components.autofill.AutofillSuggestion;
import org.chromium.content_public.browser.ContentViewCore;
import org.chromium.tencent.SharedResource;
import org.chromium.tencent.TencentAwAutofillClient;
import org.chromium.tencent.X5AutofillPopup;
import org.chromium.ui.base.WindowAndroid;

@JNINamespace("android_webview")
public class AwAutofillClient
{
  protected X5AutofillPopup mAutofillPopup;
  private ViewGroup mContainerView;
  protected ContentViewCore mContentViewCore;
  protected final long mNativeAwAutofillClient;
  
  protected AwAutofillClient(long paramLong)
  {
    this.mNativeAwAutofillClient = paramLong;
  }
  
  @CalledByNative
  private static void addToAutofillSuggestionArray(AutofillSuggestion[] paramArrayOfAutofillSuggestion, int paramInt1, String paramString1, String paramString2, int paramInt2)
  {
    paramArrayOfAutofillSuggestion[paramInt1] = new AutofillSuggestion(paramString1, paramString2, 0, false, paramInt2, false, false, false);
  }
  
  @CalledByNative
  public static AwAutofillClient create(long paramLong)
  {
    return new TencentAwAutofillClient(paramLong);
  }
  
  @CalledByNative
  private static AutofillSuggestion[] createAutofillSuggestionArray(int paramInt)
  {
    return new AutofillSuggestion[paramInt];
  }
  
  @CalledByNative
  public void hideAutofillPopup()
  {
    X5AutofillPopup localX5AutofillPopup = this.mAutofillPopup;
    if (localX5AutofillPopup == null) {
      return;
    }
    localX5AutofillPopup.dismiss();
    this.mAutofillPopup = null;
  }
  
  public void init(ContentViewCore paramContentViewCore)
  {
    this.mContentViewCore = paramContentViewCore;
    this.mContainerView = paramContentViewCore.getContainerView();
  }
  
  protected native void nativeDismissed(long paramLong);
  
  protected native void nativeSuggestionSelected(long paramLong, int paramInt);
  
  @CalledByNative
  protected void showAutofillPopup(View paramView, boolean paramBoolean, AutofillSuggestion[] paramArrayOfAutofillSuggestion)
  {
    if (this.mContentViewCore == null) {
      return;
    }
    Context localContext;
    if (this.mAutofillPopup == null)
    {
      localContext = SharedResource.getResourceContext();
      if (WindowAndroid.activityFromContext(localContext) == null)
      {
        nativeDismissed(this.mNativeAwAutofillClient);
        return;
      }
    }
    try
    {
      this.mAutofillPopup = new X5AutofillPopup(localContext, paramView, new AutofillDelegate()
      {
        public void accessibilityFocusCleared() {}
        
        public void deleteSuggestion(int paramAnonymousInt) {}
        
        public void dismissed()
        {
          AwAutofillClient localAwAutofillClient = AwAutofillClient.this;
          localAwAutofillClient.nativeDismissed(localAwAutofillClient.mNativeAwAutofillClient);
        }
        
        public void suggestionSelected(int paramAnonymousInt)
        {
          AwAutofillClient localAwAutofillClient = AwAutofillClient.this;
          localAwAutofillClient.nativeSuggestionSelected(localAwAutofillClient.mNativeAwAutofillClient, paramAnonymousInt);
        }
      });
    }
    catch (RuntimeException paramView)
    {
      for (;;) {}
    }
    nativeDismissed(this.mNativeAwAutofillClient);
    return;
    this.mAutofillPopup.filterAndShow(paramArrayOfAutofillSuggestion, paramBoolean, 0, 0, 0, 0);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwAutofillClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */