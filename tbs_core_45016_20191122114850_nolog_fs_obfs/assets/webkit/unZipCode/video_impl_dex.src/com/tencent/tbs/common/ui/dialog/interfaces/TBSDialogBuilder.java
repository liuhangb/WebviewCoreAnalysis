package com.tencent.tbs.common.ui.dialog.interfaces;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.widget.ListAdapter;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.tbs.common.ui.dialog.TBSDefaultDialogBuilder;
import java.io.File;
import java.util.Map;

public class TBSDialogBuilder
{
  public static final int TEXT_ALIGNMENT_CENTER = 0;
  public static final int TEXT_ALIGNMENT_LEFT = 2;
  public static final int TEXT_ALIGNMENT_RIGHT = 1;
  private static TBSDialogBuilderProvider.Factory sFactory;
  private TBSDialogBuilderProvider mProvider = null;
  
  public TBSDialogBuilder(Context paramContext)
  {
    this.mProvider = getProvider(paramContext);
  }
  
  private static TBSDialogBuilderProvider getProvider(Context paramContext)
  {
    Object localObject = sFactory;
    if (localObject == null) {
      return new TBSDefaultDialogBuilder(paramContext);
    }
    localObject = ((TBSDialogBuilderProvider.Factory)localObject).getProvider(paramContext);
    if (localObject != null) {
      return (TBSDialogBuilderProvider)localObject;
    }
    return new TBSDefaultDialogBuilder(paramContext);
  }
  
  public static void setsFactory(TBSDialogBuilderProvider.Factory paramFactory)
  {
    sFactory = paramFactory;
  }
  
  public TBSDialog create()
  {
    return this.mProvider.create();
  }
  
  public TBSDialogBuilder setAdapter(ListAdapter paramListAdapter)
  {
    this.mProvider.setAdapter(paramListAdapter);
    return this;
  }
  
  public TBSDialogBuilder setDrawables(Map<String, Drawable> paramMap)
  {
    this.mProvider.setDrawables(paramMap);
    return this;
  }
  
  public TBSDialogBuilder setFileChooserCallback(ValueCallback<Uri[]> paramValueCallback)
  {
    this.mProvider.setFileChooserCallback(paramValueCallback);
    return this;
  }
  
  public TBSDialogBuilder setFileChooserParamsMode(int paramInt)
  {
    this.mProvider.setFileChooserParamsMode(paramInt);
    return this;
  }
  
  public TBSDialogBuilder setFileChooserTag(boolean paramBoolean)
  {
    this.mProvider.setFileChooserTag(paramBoolean);
    return this;
  }
  
  public TBSDialogBuilder setIX5WebViewBase(IX5WebViewBase paramIX5WebViewBase)
  {
    this.mProvider.setIX5WebViewBase(paramIX5WebViewBase);
    return this;
  }
  
  public TBSDialogBuilder setIntentToSend(Intent paramIntent, IntentFilterCallback paramIntentFilterCallback)
  {
    this.mProvider.setIntentToSend(paramIntent, paramIntentFilterCallback);
    return this;
  }
  
  public TBSDialogBuilder setMessage(String paramString)
  {
    return setMessage(paramString, 0);
  }
  
  public TBSDialogBuilder setMessage(String paramString, int paramInt)
  {
    this.mProvider.setMessage(paramString);
    this.mProvider.setMessageAlignment(paramInt);
    return this;
  }
  
  public TBSDialogBuilder setNegativeButton(String paramString, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mProvider.setNegativeButton(paramString, paramOnClickListener);
    return this;
  }
  
  public TBSDialogBuilder setNeutralButton(String paramString, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mProvider.setNeutralButton(paramString, paramOnClickListener);
    return this;
  }
  
  public TBSDialogBuilder setOnCancelListener(DialogInterface.OnCancelListener paramOnCancelListener)
  {
    this.mProvider.setOnCancelListener(paramOnCancelListener);
    return this;
  }
  
  public TBSDialogBuilder setOnKeyListener(DialogInterface.OnKeyListener paramOnKeyListener)
  {
    this.mProvider.setOnKeyListener(paramOnKeyListener);
    return this;
  }
  
  public TBSDialogBuilder setPositiveButton(String paramString, DialogInterface.OnClickListener paramOnClickListener)
  {
    this.mProvider.setPositiveButton(paramString, paramOnClickListener);
    return this;
  }
  
  public TBSDialogBuilder setTitle(String paramString)
  {
    this.mProvider.setTitle(paramString);
    return this;
  }
  
  public TBSDialog show()
  {
    TBSDialog localTBSDialog = create();
    if (localTBSDialog != null) {
      localTBSDialog.show();
    }
    return localTBSDialog;
  }
  
  public static abstract interface DownloadListener
  {
    public abstract void onFinished(File paramFile);
    
    public abstract void onProgress(int paramInt);
    
    public abstract void onStarted();
  }
  
  public static abstract interface IntentFilterCallback
  {
    public static final int DOWNLOAD_FILE_TYPE_ARCHIVE = 3;
    public static final int DOWNLOAD_FILE_TYPE_DOCUMENT = 2;
    public static final int DOWNLOAD_FILE_TYPE_OTHER = 4;
    public static final int DOWNLOAD_FILE_TYPE_VIDEO = 1;
    
    public abstract Drawable getAppIcon();
    
    public abstract String getAppLabel();
    
    public abstract String getAppPackageName();
    
    public abstract String getDialogStyle();
    
    public abstract String getFileTips();
    
    public abstract int getFileType();
    
    public abstract boolean installDownloadFile(Context paramContext, String paramString1, String paramString2, Bundle paramBundle);
    
    public abstract boolean isDownloadIntercept();
    
    public abstract boolean isDownloadStarted();
    
    public abstract boolean isDownloaded();
    
    public abstract boolean isInstalled();
    
    public abstract boolean isOpenBrowserInLongPress();
    
    public abstract boolean isSearchInLongPress();
    
    public abstract void onDialogDismissed();
    
    public abstract void onSendingIntent(Intent paramIntent, ResolveInfo paramResolveInfo, boolean paramBoolean);
    
    public abstract void onSetListener(TBSDialogBuilder.DownloadListener paramDownloadListener);
    
    public abstract int onStartDownload(String paramString, TBSDialogBuilder.DownloadListener paramDownloadListener);
    
    public abstract void openApp();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\interfaces\TBSDialogBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */