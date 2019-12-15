package com.tencent.smtt.webkit.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import org.chromium.ui.base.WindowAndroid;

public class b
{
  private a jdField_a_of_type_ComTencentSmttWebkitUiA = null;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private boolean jdField_a_of_type_Boolean;
  private String[] jdField_a_of_type_ArrayOfJavaLangString;
  private boolean[] jdField_a_of_type_ArrayOfBoolean;
  private boolean[] b;
  private boolean[] c;
  
  public b(String[] paramArrayOfString, int[] paramArrayOfInt1, int[] paramArrayOfInt2, boolean[] paramArrayOfBoolean, boolean paramBoolean, TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.jdField_a_of_type_ArrayOfJavaLangString = paramArrayOfString;
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.b = new boolean[paramArrayOfString.length];
    int i = 0;
    for (;;)
    {
      paramArrayOfString = this.b;
      if (i >= paramArrayOfString.length) {
        break;
      }
      paramArrayOfString[i] = 0;
      i += 1;
    }
    i = 0;
    while (i < paramArrayOfInt2.length)
    {
      if (paramArrayOfInt2[i] >= 0)
      {
        int j = paramArrayOfInt2[i];
        paramArrayOfString = this.b;
        if (j < paramArrayOfString.length) {
          paramArrayOfString[paramArrayOfInt2[i]] = 1;
        }
      }
      i += 1;
    }
    this.jdField_a_of_type_ArrayOfBoolean = new boolean[paramArrayOfInt1.length];
    i = 0;
    while (i < paramArrayOfInt1.length)
    {
      paramArrayOfString = this.jdField_a_of_type_ArrayOfBoolean;
      if (paramArrayOfInt1[i] == 1) {
        paramBoolean = true;
      } else {
        paramBoolean = false;
      }
      paramArrayOfString[i] = paramBoolean;
      i += 1;
    }
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    this.c = paramArrayOfBoolean;
  }
  
  private boolean a()
  {
    Activity localActivity = WindowAndroid.activityFromContext(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext());
    return (localActivity != null) && (!localActivity.isFinishing());
  }
  
  private void b()
  {
    if (this.jdField_a_of_type_ComTencentSmttWebkitUiA != null) {
      this.jdField_a_of_type_ComTencentSmttWebkitUiA = null;
    }
  }
  
  public void a()
  {
    Object localObject = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    boolean bool;
    if ((localObject != null) && (((TencentWebViewProxy)localObject).getSettingsExtension() != null) && (!this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getSettingsExtension().getDayOrNight())) {
      bool = true;
    } else {
      bool = false;
    }
    if (a()) {
      try
      {
        localObject = new a(WindowAndroid.activityFromContext(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getContext()), bool, this.jdField_a_of_type_Boolean, this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy, this.jdField_a_of_type_ArrayOfJavaLangString, this.b, this.jdField_a_of_type_ArrayOfBoolean, this.c)
        {
          public void onDismiss(DialogInterface paramAnonymousDialogInterface)
          {
            super.onDismiss(paramAnonymousDialogInterface);
            b.a(b.this);
          }
        };
        b();
        this.jdField_a_of_type_ComTencentSmttWebkitUiA = ((a)localObject);
        ((a)localObject).show();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */