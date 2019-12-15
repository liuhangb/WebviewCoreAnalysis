package com.tencent.tbs.common.ui.dialog;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.webkit.ValueCallback;
import android.widget.ListAdapter;
import com.tencent.common.utils.LogUtils;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialog;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBase;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBase.DialogButton;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.IntentFilterCallback;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilderProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TBSDefaultDialogBuilder
  implements TBSDialogBuilderProvider
{
  private static final String TAG = "TBSDialog";
  private ListAdapter mAdapter;
  private List<TBSDialogBase.DialogButton> mAdditionButtons = new ArrayList();
  private Map<Integer, TBSDialogBase.DialogButton> mButtons = new HashMap();
  private DialogInterface.OnCancelListener mCancelListener;
  private Context mContext;
  private Map<String, Drawable> mDrawables;
  private boolean mFileChooserTag = false;
  private ValueCallback<Uri[]> mFilePathCallback;
  private IX5WebViewBase mIX5WebViewBase = null;
  private Intent mIntent;
  private TBSDialogBuilder.IntentFilterCallback mIntentSendingCallback;
  private String mMessage;
  private int mMessageAlignment;
  private int mMode;
  private DialogInterface.OnKeyListener mOnKeyListener;
  private String mTitle;
  
  public TBSDefaultDialogBuilder(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private Map<Integer, Object> formatProperties()
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put(Integer.valueOf(1), this.mTitle);
    localHashMap.put(Integer.valueOf(2), this.mMessage);
    localHashMap.put(Integer.valueOf(8), Integer.valueOf(this.mMessageAlignment));
    localHashMap.put(Integer.valueOf(3), this.mButtons);
    localHashMap.put(Integer.valueOf(4), this.mCancelListener);
    localHashMap.put(Integer.valueOf(9), this.mOnKeyListener);
    localHashMap.put(Integer.valueOf(5), this.mIntent);
    localHashMap.put(Integer.valueOf(6), this.mAdapter);
    localHashMap.put(Integer.valueOf(7), this.mIntentSendingCallback);
    localHashMap.put(Integer.valueOf(10), this.mDrawables);
    return localHashMap;
  }
  
  public TBSDialog create()
  {
    int i = 0;
    try
    {
      localObject1 = TBSResources.getResourceContext();
      if (localObject1 != null) {
        i = 1;
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1;
      Object localObject2;
      for (;;) {}
    }
    if (this.mFileChooserTag)
    {
      localObject1 = new TBSFileChooserDialog(this.mContext, this.mIX5WebViewBase, this.mFilePathCallback, this.mMode);
      ((TBSDialogBase)localObject1).setProperties(formatProperties());
      return (TBSDialog)localObject1;
    }
    if (i != 0)
    {
      if (this.mIntent != null) {
        localObject1 = new TBSActivityPicker(this.mContext);
      } else {
        localObject1 = new TBSPlainTextDialog(this.mContext);
      }
      ((TBSDialogBase)localObject1).setProperties(formatProperties());
      return (TBSDialog)localObject1;
    }
    LogUtils.d("TBSDialog", "Resource Context not found for TBSDefaultDialog, use AlertDialog instead.");
    localObject1 = this.mIntent;
    if (localObject1 != null)
    {
      this.mContext.startActivity((Intent)localObject1);
      return null;
    }
    localObject1 = new AlertDialog.Builder(this.mContext);
    localObject2 = this.mTitle;
    if (localObject2 != null) {
      ((AlertDialog.Builder)localObject1).setTitle((CharSequence)localObject2);
    }
    localObject2 = this.mMessage;
    if (localObject2 != null) {
      ((AlertDialog.Builder)localObject1).setMessage((CharSequence)localObject2);
    }
    localObject2 = this.mCancelListener;
    if (localObject2 != null) {
      ((AlertDialog.Builder)localObject1).setOnCancelListener((DialogInterface.OnCancelListener)localObject2);
    }
    localObject2 = this.mOnKeyListener;
    if (localObject2 != null) {
      ((AlertDialog.Builder)localObject1).setOnKeyListener((DialogInterface.OnKeyListener)localObject2);
    }
    localObject2 = this.mButtons.values().iterator();
    while (((Iterator)localObject2).hasNext())
    {
      TBSDialogBase.DialogButton localDialogButton = (TBSDialogBase.DialogButton)((Iterator)localObject2).next();
      switch (localDialogButton.which)
      {
      default: 
        ((AlertDialog.Builder)localObject1).setPositiveButton(localDialogButton.label, localDialogButton.diListener);
        break;
      case -2: 
        ((AlertDialog.Builder)localObject1).setNegativeButton(localDialogButton.label, localDialogButton.diListener);
        break;
      case -3: 
        ((AlertDialog.Builder)localObject1).setNeutralButton(localDialogButton.label, localDialogButton.diListener);
      }
    }
    return new TBSSysAlertDialog(((AlertDialog.Builder)localObject1).create());
  }
  
  public void setAdapter(ListAdapter paramListAdapter)
  {
    this.mAdapter = paramListAdapter;
  }
  
  public void setDrawables(Map<String, Drawable> paramMap)
  {
    this.mDrawables = paramMap;
  }
  
  public void setFileChooserCallback(ValueCallback<Uri[]> paramValueCallback)
  {
    this.mFilePathCallback = paramValueCallback;
  }
  
  public void setFileChooserParamsMode(int paramInt)
  {
    this.mMode = paramInt;
  }
  
  public void setFileChooserTag(boolean paramBoolean)
  {
    this.mFileChooserTag = paramBoolean;
  }
  
  public void setIX5WebViewBase(IX5WebViewBase paramIX5WebViewBase)
  {
    this.mIX5WebViewBase = paramIX5WebViewBase;
  }
  
  public void setIntentToSend(Intent paramIntent, TBSDialogBuilder.IntentFilterCallback paramIntentFilterCallback)
  {
    this.mIntent = paramIntent;
    this.mIntentSendingCallback = paramIntentFilterCallback;
  }
  
  public void setMessage(String paramString)
  {
    this.mMessage = paramString;
  }
  
  public void setMessageAlignment(int paramInt)
  {
    this.mMessageAlignment = paramInt;
  }
  
  public void setNegativeButton(String paramString, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mButtons.put(Integer.valueOf(-2), new TBSDialogBase.DialogButton(-2, paramString, paramOnClickListener));
  }
  
  public void setNeutralButton(String paramString, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mButtons.put(Integer.valueOf(-3), new TBSDialogBase.DialogButton(-3, paramString, paramOnClickListener));
  }
  
  public void setOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener)
  {
    this.mCancelListener = paramOnCancelListener;
  }
  
  public void setOnKeyListener(DialogInterface.OnKeyListener paramOnKeyListener)
  {
    this.mOnKeyListener = paramOnKeyListener;
  }
  
  public void setPositiveButton(String paramString, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mButtons.put(Integer.valueOf(-1), new TBSDialogBase.DialogButton(-1, paramString, paramOnClickListener));
  }
  
  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\TBSDefaultDialogBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */