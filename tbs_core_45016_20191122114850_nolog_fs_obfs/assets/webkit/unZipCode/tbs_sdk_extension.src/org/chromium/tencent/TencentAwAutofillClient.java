package org.chromium.tencent;

import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import org.chromium.android_webview.AwAutofillClient;
import org.chromium.components.autofill.AutofillDelegate;
import org.chromium.components.autofill.AutofillSuggestion;

public class TencentAwAutofillClient
  extends AwAutofillClient
{
  public TencentAwAutofillClient(long paramLong)
  {
    super(paramLong);
  }
  
  public void adjustAutofillPositionOnScrollChanged()
  {
    if (this.mContentViewCore != null)
    {
      if (this.mAutofillPopup == null) {
        return;
      }
      if (this.mAutofillPopup.isShowing()) {
        hideAutofillPopup();
      }
      return;
    }
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    hideAutofillPopup();
  }
  
  protected void showAutofillPopup(View paramView, boolean paramBoolean, AutofillSuggestion[] paramArrayOfAutofillSuggestion)
  {
    if (this.mContentViewCore == null) {
      return;
    }
    if (Build.VERSION.SDK_INT < 14) {
      return;
    }
    try
    {
      SharedResource.getLayoutInflater().inflate(SharedResource.loadIdentifierResource("x5_dropdown_item", "layout"), null);
      boolean bool2 = false;
      boolean bool1 = bool2;
      if ((this.mContentViewCore instanceof TencentContentViewCore))
      {
        ContentViewClientExtension localContentViewClientExtension = ((TencentContentViewCore)this.mContentViewCore).getContentViewClientExtension();
        bool1 = bool2;
        if (localContentViewClientExtension != null) {
          bool1 = localContentViewClientExtension.isNightMode();
        }
      }
      if ((this.mAutofillPopup == null) || (bool1 != this.mAutofillPopup.isNightMode())) {
        this.mAutofillPopup = new X5AutofillPopup(SharedResource.getResourceContext(), paramView, new AutofillDelegate()
        {
          public void accessibilityFocusCleared() {}
          
          public void deleteSuggestion(int paramAnonymousInt) {}
          
          public void dismissed()
          {
            TencentAwAutofillClient localTencentAwAutofillClient = TencentAwAutofillClient.this;
            localTencentAwAutofillClient.nativeDismissed(localTencentAwAutofillClient.mNativeAwAutofillClient);
            TencentAwAutofillClient.access$202(TencentAwAutofillClient.this, null);
          }
          
          public void suggestionSelected(int paramAnonymousInt)
          {
            TencentAwAutofillClient localTencentAwAutofillClient = TencentAwAutofillClient.this;
            localTencentAwAutofillClient.nativeSuggestionSelected(localTencentAwAutofillClient.mNativeAwAutofillClient, paramAnonymousInt);
          }
        }, bool1);
      }
      this.mAutofillPopup.filterAndShow(paramArrayOfAutofillSuggestion, paramBoolean, 0, 0, 0, 0);
      return;
    }
    catch (Throwable paramView)
    {
      paramView.printStackTrace();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentAwAutofillClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */