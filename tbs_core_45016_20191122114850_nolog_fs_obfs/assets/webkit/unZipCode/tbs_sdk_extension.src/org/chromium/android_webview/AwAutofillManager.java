package org.chromium.android_webview;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillManager.AutofillCallback;
import android.view.autofill.AutofillValue;
import java.lang.ref.WeakReference;
import org.chromium.base.BuildInfo;

@TargetApi(26)
public class AwAutofillManager
{
  private AutofillManager jdField_a_of_type_AndroidViewAutofillAutofillManager;
  private AutofillInputUIMonitor jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager$AutofillInputUIMonitor;
  private boolean jdField_a_of_type_Boolean;
  private boolean b;
  private boolean c;
  
  public AwAutofillManager(Context paramContext)
  {
    boolean bool;
    if ((!BuildInfo.isAtLeastP()) && (AwContents.activityFromContext(paramContext) == null)) {
      bool = true;
    } else {
      bool = false;
    }
    this.c = bool;
    if (this.c) {
      return;
    }
    this.jdField_a_of_type_AndroidViewAutofillAutofillManager = ((AutofillManager)paramContext.getSystemService(AutofillManager.class));
    this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager$AutofillInputUIMonitor = new AutofillInputUIMonitor(this);
    this.jdField_a_of_type_AndroidViewAutofillAutofillManager.registerCallback(this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager$AutofillInputUIMonitor);
  }
  
  private boolean a()
  {
    return this.b;
  }
  
  public void cancel()
  {
    if (!this.c)
    {
      if (a()) {
        return;
      }
      this.jdField_a_of_type_AndroidViewAutofillAutofillManager.cancel();
      return;
    }
  }
  
  public void commit(int paramInt)
  {
    if (!this.c)
    {
      if (a()) {
        return;
      }
      this.jdField_a_of_type_AndroidViewAutofillAutofillManager.commit();
      return;
    }
  }
  
  public void destroy()
  {
    if (!this.c)
    {
      if (a()) {
        return;
      }
      this.jdField_a_of_type_AndroidViewAutofillAutofillManager.unregisterCallback(this.jdField_a_of_type_OrgChromiumAndroid_webviewAwAutofillManager$AutofillInputUIMonitor);
      this.jdField_a_of_type_AndroidViewAutofillAutofillManager = null;
      this.b = true;
      return;
    }
  }
  
  public boolean isAutofillInputUIShowing()
  {
    if ((!this.c) && (!a())) {
      return this.jdField_a_of_type_Boolean;
    }
    return false;
  }
  
  public void notifyVirtualValueChanged(View paramView, int paramInt, AutofillValue paramAutofillValue)
  {
    if (!this.c)
    {
      if (a()) {
        return;
      }
      this.jdField_a_of_type_AndroidViewAutofillAutofillManager.notifyValueChanged(paramView, paramInt, paramAutofillValue);
      return;
    }
  }
  
  public void notifyVirtualViewEntered(View paramView, int paramInt, Rect paramRect)
  {
    if (this.c) {
      return;
    }
    if (a()) {
      return;
    }
    this.jdField_a_of_type_AndroidViewAutofillAutofillManager.notifyViewEntered(paramView, paramInt, paramRect);
  }
  
  public void notifyVirtualViewExited(View paramView, int paramInt)
  {
    if (!this.c)
    {
      if (a()) {
        return;
      }
      this.jdField_a_of_type_AndroidViewAutofillAutofillManager.notifyViewExited(paramView, paramInt);
      return;
    }
  }
  
  public void requestAutofill(View paramView, int paramInt, Rect paramRect)
  {
    if (!this.c)
    {
      if (a()) {
        return;
      }
      this.jdField_a_of_type_AndroidViewAutofillAutofillManager.requestAutofill(paramView, paramInt, paramRect);
      return;
    }
  }
  
  private static class AutofillInputUIMonitor
    extends AutofillManager.AutofillCallback
  {
    private WeakReference<AwAutofillManager> a;
    
    public AutofillInputUIMonitor(AwAutofillManager paramAwAutofillManager)
    {
      this.a = new WeakReference(paramAwAutofillManager);
    }
    
    public void onAutofillEvent(View paramView, int paramInt1, int paramInt2)
    {
      paramView = (AwAutofillManager)this.a.get();
      if (paramView == null) {
        return;
      }
      boolean bool = true;
      if (paramInt2 != 1) {
        bool = false;
      }
      AwAutofillManager.a(paramView, bool);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwAutofillManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */