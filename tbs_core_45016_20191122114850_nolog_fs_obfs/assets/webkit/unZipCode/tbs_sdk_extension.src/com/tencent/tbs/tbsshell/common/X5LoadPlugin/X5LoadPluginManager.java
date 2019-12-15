package com.tencent.tbs.tbsshell.common.X5LoadPlugin;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.plugin.IQBPluginSystemCallback;
import com.tencent.common.plugin.QBPluginItemInfo;
import com.tencent.common.plugin.QBPluginSystem;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.TbsMode;
import java.io.File;

public class X5LoadPluginManager
{
  private static final int ERROR_AR_TYPE = 1008;
  private static final int ERROR_LIB_NOT_EXIST = 1006;
  private static final int ERROR_NO_PLUGIN_INFO = 1004;
  private static final int ERROR_NO_SPACE = 1002;
  private static final int ERROR_PERMISSION_DENY = 1007;
  private static final int ERROR_UNKNOWN = 1001;
  private static final int ERROR_UNZIP_DIR_EMPTY = 1005;
  private static final int ERROR_USER_CANCELED = 1003;
  private static final String PLUGIN_SHARPP_PACKAGE_NAME = "com.tencent.qb.plugin.sharpP";
  private static final String PLUGIN_TPG_PACKAGE_NAME = "com.tencent.qb.plugin.TPG";
  private static final int SO_TYPE_SHARPP = 1;
  private static final int SO_TYPE_TPG = 2;
  private static final String TAG = "X5LoadPluginManager";
  private Context mContext;
  private QBPluginSystem mPluginSystem;
  private String mSharpPluginPath = null;
  private String mTPGPluginPath = null;
  
  X5LoadPluginManager(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private void downloadSO(final int paramInt, final IX5LoadPluginCallback paramIX5LoadPluginCallback)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("downloadSO soType: ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).toString();
    localObject = new IQBPluginSystemCallback()
    {
      public void onDownloadCreateed(String paramAnonymousString1, String paramAnonymousString2)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadCreateed:");
        localStringBuilder.append(paramAnonymousString1);
        localStringBuilder.append(" url:");
        localStringBuilder.append(paramAnonymousString2);
        localStringBuilder.toString();
      }
      
      public void onDownloadProgress(String paramAnonymousString, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadProgress:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(" progress:");
        localStringBuilder.append(paramAnonymousInt2);
        localStringBuilder.toString();
        paramIX5LoadPluginCallback.onDownloadProgress(paramAnonymousInt2);
      }
      
      public void onDownloadStart(String paramAnonymousString, int paramAnonymousInt)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadStart:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(" size:");
        localStringBuilder.append(paramAnonymousInt);
        localStringBuilder.toString();
        paramIX5LoadPluginCallback.onDownloadStart(paramAnonymousInt);
      }
      
      public void onDownloadSuccessed(String paramAnonymousString1, String paramAnonymousString2)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadSuccessed:");
        localStringBuilder.append(paramAnonymousString1);
        localStringBuilder.append(" filePath:");
        localStringBuilder.append(paramAnonymousString2);
        localStringBuilder.toString();
      }
      
      public void onNeedDownloadNotify(String paramAnonymousString, boolean paramAnonymousBoolean)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onNeedDownloadNotify:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(" canLoadLocal:");
        localStringBuilder.append(paramAnonymousBoolean);
        localStringBuilder.toString();
      }
      
      public void onPrepareFinished(String paramAnonymousString, QBPluginItemInfo paramAnonymousQBPluginItemInfo, int paramAnonymousInt1, int paramAnonymousInt2)
      {
        if ((paramAnonymousInt2 == 0) && (paramAnonymousQBPluginItemInfo != null))
        {
          paramAnonymousString = new StringBuilder();
          paramAnonymousString.append("url:");
          paramAnonymousString.append(paramAnonymousQBPluginItemInfo.mUrl);
          paramAnonymousString.toString();
          X5LoadPluginManager.this.doLoadSO(paramIX5LoadPluginCallback, paramInt, paramAnonymousQBPluginItemInfo.mUnzipDir);
          return;
        }
        if ((paramAnonymousInt2 != 3014) && (paramAnonymousInt2 != 6008))
        {
          if (paramAnonymousInt2 == 3010)
          {
            X5LoadPluginManager.this.onFailed(paramIX5LoadPluginCallback, paramInt, 1003, "plugin install, user canceled");
            return;
          }
          if (paramAnonymousInt2 == 3029)
          {
            X5LoadPluginManager.this.onFailed(paramIX5LoadPluginCallback, paramInt, 1004, "plugin install, no plugin info");
            return;
          }
          paramAnonymousString = new StringBuilder();
          paramAnonymousString.append("onPrepareFinished errorCode: ");
          paramAnonymousString.append(paramAnonymousInt2);
          Log.e("X5LoadPluginManager", paramAnonymousString.toString());
          X5LoadPluginManager.this.onFailed(paramIX5LoadPluginCallback, paramInt, paramAnonymousInt2, "plugin install, unkonw error");
          return;
        }
        X5LoadPluginManager.this.onFailed(paramIX5LoadPluginCallback, paramInt, 1002, "plugin install,  no space");
      }
      
      public void onPrepareStart(String paramAnonymousString)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onPrepareStart:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.toString();
      }
    };
    int i;
    if (TbsMode.TBSISQB()) {
      i = 1;
    } else {
      i = 2;
    }
    if (paramInt == 1)
    {
      this.mPluginSystem.usePluginAsync("com.tencent.qb.plugin.sharpP", 1, (IQBPluginSystemCallback)localObject, null, null, i);
      return;
    }
    if (paramInt == 2)
    {
      this.mPluginSystem.usePluginAsync("com.tencent.qb.plugin.TPG", 1, (IQBPluginSystemCallback)localObject, null, null, i);
      return;
    }
    Log.e("X5LoadPluginManager", "soType is not supported");
    onFailed(paramIX5LoadPluginCallback, paramInt, 1008, "soType is not supported");
  }
  
  protected void doLoadSO(IX5LoadPluginCallback paramIX5LoadPluginCallback, int paramInt, String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doLoadSO soType: ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(" pluginPath = ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    if (TextUtils.isEmpty(paramString))
    {
      onFailed(paramIX5LoadPluginCallback, paramInt, 1005, "plugin install, unzip dir is empty");
      return;
    }
    localObject = "";
    if (paramInt == 1) {
      localObject = "libSharpPDecoder.so";
    } else if (paramInt == 2) {
      localObject = "libTPGDecoder.so";
    }
    localObject = new File(paramString, (String)localObject);
    if (!((File)localObject).isFile())
    {
      onFailed(paramIX5LoadPluginCallback, paramInt, 1006, "plugin install, so not exist");
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("doLoadSO  FileLength = ");
    localStringBuilder.append(((File)localObject).length());
    localStringBuilder.toString();
    if (paramInt == 1) {
      this.mSharpPluginPath = paramString;
    } else if (paramInt == 2) {
      this.mTPGPluginPath = paramString;
    }
    onSuccess(paramIX5LoadPluginCallback, paramInt, paramString);
  }
  
  public void loadX5Plugin(final int paramInt, final IX5LoadPluginCallback paramIX5LoadPluginCallback)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("loadX5Plugin()  soType: ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).toString();
    if (this.mPluginSystem == null) {
      this.mPluginSystem = QBPluginSystem.getInstance(this.mContext);
    }
    if (paramInt == 1)
    {
      localObject = this.mSharpPluginPath;
      if (localObject != null) {
        onSuccess(paramIX5LoadPluginCallback, paramInt, (String)localObject);
      }
    }
    else if (paramInt == 2)
    {
      localObject = this.mTPGPluginPath;
      if (localObject != null)
      {
        onSuccess(paramIX5LoadPluginCallback, paramInt, (String)localObject);
        return;
      }
    }
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        X5LoadPluginManager.this.downloadSO(paramInt, paramIX5LoadPluginCallback);
      }
    });
  }
  
  public void onFailed(IX5LoadPluginCallback paramIX5LoadPluginCallback, int paramInt1, int paramInt2, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onFailed errorCode: ");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append(" soType: ");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append(" message: ");
    localStringBuilder.append(paramString);
    Log.e("X5LoadPluginManager", localStringBuilder.toString());
    paramIX5LoadPluginCallback.onLoadFailed(paramInt1, paramInt2, paramString);
  }
  
  public void onSuccess(IX5LoadPluginCallback paramIX5LoadPluginCallback, int paramInt, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onSuccess soType: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(" pluginPath = ");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    paramIX5LoadPluginCallback.onLoadSuccess(paramInt, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\X5LoadPlugin\X5LoadPluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */