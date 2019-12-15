package com.tencent.tbs.core.webkit;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.net.MalformedURLException;
import java.net.URL;

public class JsDialogHelper
{
  public static final int ALERT = 1;
  public static final int CONFIRM = 2;
  public static final int PROMPT = 3;
  protected static final String TAG = "JsDialogHelper";
  public static final int UNLOAD = 4;
  protected final String mDefaultValue;
  protected final String mMessage;
  protected final JsPromptResult mResult;
  protected final int mType;
  protected final String mUrl;
  
  public JsDialogHelper(JsPromptResult paramJsPromptResult, int paramInt, String paramString1, String paramString2, String paramString3)
  {
    this.mResult = paramJsPromptResult;
    this.mDefaultValue = paramString1;
    this.mMessage = paramString2;
    this.mType = paramInt;
    this.mUrl = paramString3;
  }
  
  public JsDialogHelper(JsPromptResult paramJsPromptResult, Message paramMessage)
  {
    this.mResult = paramJsPromptResult;
    this.mDefaultValue = paramMessage.getData().getString("default");
    this.mMessage = paramMessage.getData().getString("message");
    this.mType = paramMessage.getData().getInt("type");
    this.mUrl = paramMessage.getData().getString("url");
  }
  
  protected static boolean canShowAlertDialog(Context paramContext)
  {
    return paramContext instanceof Activity;
  }
  
  private String getJsDialogTitle(Context paramContext)
  {
    String str = this.mUrl;
    if (URLUtil.isDataUrl(str)) {
      return paramContext.getString(17039998);
    }
    try
    {
      URL localURL = new URL(this.mUrl);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(localURL.getProtocol());
      localStringBuilder.append("://");
      localStringBuilder.append(localURL.getHost());
      paramContext = paramContext.getString(17039997, new Object[] { localStringBuilder.toString() });
      return paramContext;
    }
    catch (MalformedURLException paramContext) {}
    return str;
  }
  
  public boolean invokeCallback(WebChromeClient paramWebChromeClient, WebView paramWebView)
  {
    switch (this.mType)
    {
    default: 
      paramWebChromeClient = new StringBuilder();
      paramWebChromeClient.append("Unexpected type: ");
      paramWebChromeClient.append(this.mType);
      throw new IllegalArgumentException(paramWebChromeClient.toString());
    case 4: 
      return paramWebChromeClient.onJsBeforeUnload(paramWebView, this.mUrl, this.mMessage, this.mResult);
    case 3: 
      return paramWebChromeClient.onJsPrompt(paramWebView, this.mUrl, this.mMessage, this.mDefaultValue, this.mResult);
    case 2: 
      return paramWebChromeClient.onJsConfirm(paramWebView, this.mUrl, this.mMessage, this.mResult);
    }
    return paramWebChromeClient.onJsAlert(paramWebView, this.mUrl, this.mMessage, this.mResult);
  }
  
  public void showDialog(Context paramContext)
  {
    if (!canShowAlertDialog(paramContext))
    {
      this.mResult.cancel();
      return;
    }
    Object localObject;
    String str;
    int i;
    int j;
    if (this.mType == 4)
    {
      localObject = paramContext.getString(17039996);
      str = paramContext.getString(17039993, new Object[] { this.mMessage });
      i = 17039995;
      j = 17039994;
    }
    else
    {
      localObject = getJsDialogTitle(paramContext);
      str = this.mMessage;
      i = 17039370;
      j = 17039360;
    }
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext);
    localBuilder.setTitle((CharSequence)localObject);
    localBuilder.setOnCancelListener(new CancelListener());
    if (this.mType != 3)
    {
      localBuilder.setMessage(str);
      localBuilder.setPositiveButton(i, new PositiveListener(null));
    }
    else
    {
      paramContext = LayoutInflater.from(paramContext).inflate(17367153, null);
      localObject = (EditText)paramContext.findViewById(16909440);
      ((EditText)localObject).setText(this.mDefaultValue);
      localBuilder.setPositiveButton(i, new PositiveListener((EditText)localObject));
      ((TextView)paramContext.findViewById(16908299)).setText(this.mMessage);
      localBuilder.setView(paramContext);
    }
    if (this.mType != 1) {
      localBuilder.setNegativeButton(j, new CancelListener());
    }
    localBuilder.show();
  }
  
  public class CancelListener
    implements DialogInterface.OnCancelListener, DialogInterface.OnClickListener
  {
    public CancelListener() {}
    
    public void onCancel(DialogInterface paramDialogInterface)
    {
      JsDialogHelper.this.mResult.cancel();
    }
    
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      JsDialogHelper.this.mResult.cancel();
    }
  }
  
  private class PositiveListener
    implements DialogInterface.OnClickListener
  {
    private final EditText mEdit;
    
    public PositiveListener(EditText paramEditText)
    {
      this.mEdit = paramEditText;
    }
    
    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      if (this.mEdit == null)
      {
        JsDialogHelper.this.mResult.confirm();
        return;
      }
      JsDialogHelper.this.mResult.confirm(this.mEdit.getText().toString());
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\JsDialogHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */