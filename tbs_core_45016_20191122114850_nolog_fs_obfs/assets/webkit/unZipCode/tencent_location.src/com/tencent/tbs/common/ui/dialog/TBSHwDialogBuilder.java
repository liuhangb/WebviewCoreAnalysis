package com.tencent.tbs.common.ui.dialog;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialog;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.DownloadListener;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBuilder.IntentFilterCallback;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TBSHwDialogBuilder
{
  private static final String TAG = "TBSHwDialogBuilder";
  private Context mContext;
  private Map<String, Drawable> mDrawables;
  private boolean mForSearch = false;
  private Intent mIntent;
  private TBSDialogBuilder.IntentFilterCallback mIntentSendingCallback;
  
  public TBSHwDialogBuilder(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public TBSDialog create()
  {
    j = 0;
    int i = 0;
    try
    {
      if (TBSResources.getResourceContext() != null) {
        i = 1;
      }
      k = i;
      j = i;
      if (this.mDrawables == null)
      {
        j = i;
        this.mDrawables = new HashMap();
        j = i;
        localObject = this.mContext.getPackageManager();
        j = i;
        Iterator localIterator = ((PackageManager)localObject).queryIntentActivities(this.mIntent, 65536).iterator();
        for (;;)
        {
          k = i;
          j = i;
          if (!localIterator.hasNext()) {
            break;
          }
          j = i;
          ResolveInfo localResolveInfo = (ResolveInfo)localIterator.next();
          j = i;
          this.mDrawables.put(localResolveInfo.activityInfo.packageName, localResolveInfo.loadIcon((PackageManager)localObject));
        }
      }
    }
    catch (Throwable localThrowable)
    {
      for (;;)
      {
        final Object localObject;
        int k = j;
      }
    }
    if (this.mForSearch)
    {
      TBSStatManager.getInstance().userBehaviorStatistics("BZCA401");
      localObject = new HwActivityPickerSearchDialog(this.mContext, this.mDrawables);
      ((HwActivityPickerSearchDialog)localObject).setPickerDialogCallback(new HwActivityPickerSearchDialog.IPickerDialogCallback()
      {
        public void onAppPicked(String paramAnonymousString, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousString != null) {
            return;
          }
          TBSStatManager.getInstance().userBehaviorStatistics("BZCA403");
          TBSHwDialogBuilder.this.mIntentSendingCallback.onStartDownload(null, new TBSDialogBuilder.DownloadListener()
          {
            public void onFinished(File paramAnonymous2File)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("BZCA404");
              TBSHwDialogBuilder.1.this.val$iPickerDialogHandler.onDownloadFinished();
              TBSHwDialogBuilder.1.this.val$dialog.dismiss();
            }
            
            public void onProgress(int paramAnonymous2Int)
            {
              TBSHwDialogBuilder.1.this.val$iPickerDialogHandler.onProgress(paramAnonymous2Int);
            }
            
            public void onStarted()
            {
              TBSHwDialogBuilder.1.this.val$iPickerDialogHandler.onDownloadStarted();
            }
          });
        }
      });
      return (TBSDialog)localObject;
    }
    if (k != 0)
    {
      localObject = new HwActivityPickerDialog(this.mContext, this.mDrawables, this.mIntentSendingCallback.isDownloadIntercept());
      ((HwActivityPickerDialog)localObject).setPickerDialogCallback(new HwActivityPickerDialog.IPickerDialogCallback()
      {
        public void onAppPicked(String paramAnonymousString, boolean paramAnonymousBoolean)
        {
          if (paramAnonymousString != null)
          {
            TBSStatManager.getInstance().userBehaviorStatistics("BZWQ203");
            ResolveInfo localResolveInfo = new ResolveInfo();
            localResolveInfo.activityInfo = new ActivityInfo();
            localResolveInfo.activityInfo.packageName = paramAnonymousString;
            TBSHwDialogBuilder.this.mIntentSendingCallback.onSendingIntent(TBSHwDialogBuilder.this.mIntent, localResolveInfo, true);
            localObject.dismiss();
            return;
          }
          TBSStatManager.getInstance().userBehaviorStatistics("BZWQ202");
          TBSHwDialogBuilder.this.mIntentSendingCallback.onStartDownload(null, new TBSDialogBuilder.DownloadListener()
          {
            public void onFinished(File paramAnonymous2File)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("BZWQ205");
              TBSStatManager.getInstance().userBehaviorStatistics("BZWQ206");
              TBSHwDialogBuilder.2.this.val$iPickerDialogHandler.onDownloadFinished();
              TBSHwDialogBuilder.2.this.val$dialog.dismiss();
            }
            
            public void onProgress(int paramAnonymous2Int)
            {
              TBSHwDialogBuilder.2.this.val$iPickerDialogHandler.onProgress(paramAnonymous2Int);
            }
            
            public void onStarted()
            {
              TBSStatManager.getInstance().userBehaviorStatistics("BZWQ204");
              TBSHwDialogBuilder.2.this.val$iPickerDialogHandler.onDownloadStarted();
            }
          });
        }
      });
      return (TBSDialog)localObject;
    }
    new TBSDialog()
    {
      public void cancel() {}
      
      public void dismiss() {}
      
      public boolean isShowing()
      {
        return false;
      }
      
      public void setCanceledOnTouchOutside(boolean paramAnonymousBoolean) {}
      
      public void show() {}
    };
  }
  
  public TBSHwDialogBuilder setDialogForSearch(boolean paramBoolean)
  {
    this.mForSearch = paramBoolean;
    return this;
  }
  
  public TBSHwDialogBuilder setDrawables(Map<String, Drawable> paramMap)
  {
    this.mDrawables = paramMap;
    return this;
  }
  
  public TBSHwDialogBuilder setIntentToSend(Intent paramIntent, TBSDialogBuilder.IntentFilterCallback paramIntentFilterCallback)
  {
    this.mIntent = paramIntent;
    this.mIntentSendingCallback = paramIntentFilterCallback;
    return this;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\TBSHwDialogBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */