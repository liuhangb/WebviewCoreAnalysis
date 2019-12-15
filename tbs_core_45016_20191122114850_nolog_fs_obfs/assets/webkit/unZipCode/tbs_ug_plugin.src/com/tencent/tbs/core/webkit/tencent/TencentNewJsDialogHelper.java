package com.tencent.tbs.core.webkit.tencent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.e;
import com.tencent.tbs.core.webkit.JsDialogHelper;
import com.tencent.tbs.core.webkit.JsDialogHelper.CancelListener;
import com.tencent.tbs.core.webkit.JsPromptResult;

public class TencentNewJsDialogHelper
  extends JsDialogHelper
{
  private final boolean mIsNightMode;
  
  public TencentNewJsDialogHelper(JsPromptResult paramJsPromptResult, int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    super(paramJsPromptResult, paramInt, paramString1, paramString2, paramString3);
    this.mIsNightMode = paramBoolean;
  }
  
  private String getJsDialogTitle(Context paramContext)
  {
    paramContext = this.mUrl;
    if (TencentURLUtil.isDataUrl(this.mUrl))
    {
      paramContext = new StringBuilder();
      paramContext.append("JavaScript");
      paramContext.append(SmttResource.getString("x5_js_new_dialog_second_title_suffix", "string"));
      return paramContext.toString();
    }
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(TencentURLUtil.getHost(this.mUrl));
      ((StringBuilder)localObject).append(SmttResource.getString("x5_js_new_dialog_second_title_suffix", "string"));
      localObject = ((StringBuilder)localObject).toString();
      return (String)localObject;
    }
    catch (Exception localException) {}
    return paramContext;
  }
  
  public int getDialogType()
  {
    if (this.mType == 2) {
      return 2;
    }
    if (this.mType == 3) {
      return 1;
    }
    return 0;
  }
  
  public void showDialog(Context paramContext)
  {
    if (!Boolean.valueOf(canShowAlertDialog(paramContext)).booleanValue())
    {
      this.mResult.cancel();
      return;
    }
    TencentNewJsDialog localTencentNewJsDialog = new TencentNewJsDialog(paramContext, getDialogType(), this.mIsNightMode);
    if (e.a().g()) {
      localTencentNewJsDialog.setTitleText(SmttResource.getString("x5_js_new_dialog_main_title", "string"));
    }
    localTencentNewJsDialog.setSecondTitleText(getJsDialogTitle(paramContext));
    localTencentNewJsDialog.setMessage(this.mMessage);
    if (this.mType == 1)
    {
      localTencentNewJsDialog.setPositiveButton(SmttResource.getString("x5_js_new_dialog_alert_button_text", "string"), new PositiveListener(null));
    }
    else if (this.mType == 2)
    {
      localTencentNewJsDialog.setPositiveButton(SmttResource.getString("x5_ok", "string"), new PositiveListener(null));
      localTencentNewJsDialog.setNegativeButton(SmttResource.getString("x5_cancel", "string"), new JsDialogHelper.CancelListener(this));
    }
    else if (this.mType == 3)
    {
      localTencentNewJsDialog.setEditText(this.mDefaultValue);
      localTencentNewJsDialog.setPositiveButton(SmttResource.getString("x5_ok", "string"), new PositiveListener(localTencentNewJsDialog));
      localTencentNewJsDialog.setNegativeButton(SmttResource.getString("x5_cancel", "string"), new JsDialogHelper.CancelListener(this));
    }
    localTencentNewJsDialog.setOnCancelListener(new JsDialogHelper.CancelListener(this));
    localTencentNewJsDialog.show();
  }
  
  private class PositiveListener
    implements DialogInterface.OnClickListener
  {
    private final TencentNewJsDialog mDialog;
    
    public PositiveListener(TencentNewJsDialog paramTencentNewJsDialog)
    {
      this.mDialog = paramTencentNewJsDialog;
    }
    
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      if (this.mDialog == null)
      {
        TencentNewJsDialogHelper.this.mResult.confirm();
        return;
      }
      TencentNewJsDialogHelper.this.mResult.confirm(this.mDialog.getEditTextViewText());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentNewJsDialogHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */