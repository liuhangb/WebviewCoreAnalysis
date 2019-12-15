package com.tencent.tbs.core.webkit.tencent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.ui.e.a;
import com.tencent.tbs.core.webkit.JsDialogHelper;
import com.tencent.tbs.core.webkit.JsDialogHelper.CancelListener;
import com.tencent.tbs.core.webkit.JsPromptResult;
import java.net.MalformedURLException;
import java.net.URL;

public class TencentJsDialogHelper
  extends JsDialogHelper
{
  private final boolean mIsNightMode;
  
  public TencentJsDialogHelper(JsPromptResult paramJsPromptResult, int paramInt, String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    super(paramJsPromptResult, paramInt, paramString1, paramString2, paramString3);
    this.mIsNightMode = paramBoolean;
  }
  
  private String getJsDialogTitle(Context paramContext)
  {
    paramContext = this.mUrl;
    if (TencentURLUtil.isDataUrl(this.mUrl)) {
      return SmttResource.getString("x5_js_dialog_title_default", "string");
    }
    try
    {
      Object localObject = new URL(this.mUrl);
      int i = SmttResource.loadIdentifierResource("x5_js_dialog_title", "string");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(((URL)localObject).getProtocol());
      localStringBuilder.append("://");
      localStringBuilder.append(((URL)localObject).getHost());
      localObject = SmttResource.getString(i, new Object[] { localStringBuilder.toString() });
      return (String)localObject;
    }
    catch (MalformedURLException localMalformedURLException) {}
    return paramContext;
  }
  
  public int getDialogType()
  {
    if (this.mType != 2)
    {
      if (this.mType == 4) {
        return 2;
      }
      if (this.mType == 3) {
        return 1;
      }
      return 0;
    }
    return 2;
  }
  
  public void showDialog(Context paramContext)
  {
    if (!Boolean.valueOf(canShowAlertDialog(paramContext)).booleanValue())
    {
      this.mResult.cancel();
      return;
    }
    e.a locala = new e.a(paramContext, getDialogType(), this.mIsNightMode);
    if (this.mType != 4)
    {
      locala.a(getJsDialogTitle(paramContext));
      locala.a(this.mMessage);
    }
    else
    {
      locala.a(SmttResource.getString("x5_js_dialog_before_unload", "string"));
    }
    if (this.mType != 3)
    {
      locala.b(SmttResource.getString("x5_ok", "string"), new PositiveListener(null));
    }
    else
    {
      locala.b(this.mDefaultValue);
      locala.b(SmttResource.getString("x5_ok", "string"), new PositiveListener(locala));
    }
    if (this.mType != 1) {
      locala.a(SmttResource.getString("x5_cancel", "string"), new JsDialogHelper.CancelListener(this));
    }
    locala.a(new JsDialogHelper.CancelListener(this));
    locala.a();
  }
  
  private class PositiveListener
    implements DialogInterface.OnClickListener
  {
    private final e.a mBuilder;
    
    public PositiveListener(e.a parama)
    {
      this.mBuilder = parama;
    }
    
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      if (this.mBuilder == null)
      {
        TencentJsDialogHelper.this.mResult.confirm();
        return;
      }
      TencentJsDialogHelper.this.mResult.confirm(this.mBuilder.a());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentJsDialogHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */