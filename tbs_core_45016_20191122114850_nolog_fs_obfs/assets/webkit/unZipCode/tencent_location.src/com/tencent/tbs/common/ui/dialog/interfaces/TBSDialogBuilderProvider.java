package com.tencent.tbs.common.ui.dialog.interfaces;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.widget.ListAdapter;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import java.util.Map;

public abstract interface TBSDialogBuilderProvider
{
  public abstract TBSDialog create();
  
  public abstract void setAdapter(ListAdapter paramListAdapter);
  
  public abstract void setDrawables(Map<String, Drawable> paramMap);
  
  public abstract void setFileChooserCallback(ValueCallback<Uri[]> paramValueCallback);
  
  public abstract void setFileChooserParamsMode(int paramInt);
  
  public abstract void setFileChooserTag(boolean paramBoolean);
  
  public abstract void setIX5WebViewBase(IX5WebViewBase paramIX5WebViewBase);
  
  public abstract void setIntentToSend(Intent paramIntent, TBSDialogBuilder.IntentFilterCallback paramIntentFilterCallback);
  
  public abstract void setMessage(String paramString);
  
  public abstract void setMessageAlignment(int paramInt);
  
  public abstract void setNegativeButton(String paramString, DialogInterface.OnClickListener paramOnClickListener);
  
  public abstract void setNeutralButton(String paramString, DialogInterface.OnClickListener paramOnClickListener);
  
  public abstract void setOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener);
  
  public abstract void setOnKeyListener(DialogInterface.OnKeyListener paramOnKeyListener);
  
  public abstract void setPositiveButton(String paramString, DialogInterface.OnClickListener paramOnClickListener);
  
  public abstract void setTitle(String paramString);
  
  public static abstract interface Factory
  {
    public abstract TBSDialogBuilderProvider getProvider(Context paramContext);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\interfaces\TBSDialogBuilderProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */