package com.tencent.tbs.tbsshell.common.readmode;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.plugin.IQBPluginSystemCallback;
import com.tencent.common.plugin.QBPluginItemInfo;
import com.tencent.common.plugin.QBPluginSystem;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import java.io.File;

public class WebpageReadModePluginManager
{
  private static final int ERROR_LIB_NOT_EXIST = 1006;
  private static final int ERROR_NO_PLUGIN_INFO = 1004;
  private static final int ERROR_NO_SPACE = 1002;
  private static final int ERROR_UNZIP_DIR_EMPTY = 1005;
  private static final int ERROR_USER_CANCELED = 1003;
  private static String TAG = "WebpageReadModePluginManager";
  private static final String WEBPAGE_READMODE_PLUGIN_PACKAGE_NAME = "com.tencent.qb.plugin.webpage.readmode";
  private static WebpageReadModePluginManager sInstance;
  private Context mContext = null;
  private QBPluginSystem mPluginSystem = null;
  private String mWebpageReadModePluginPath = null;
  
  WebpageReadModePluginManager(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private boolean checkPluginResourceExists(String paramString)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append(paramString);
    ((StringBuilder)localObject1).append(File.separator);
    ((StringBuilder)localObject1).append("javascript");
    ((StringBuilder)localObject1).append(File.separator);
    ((StringBuilder)localObject1).append("mtt_domdistiller.js");
    localObject1 = new File(((StringBuilder)localObject1).toString());
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(paramString);
    ((StringBuilder)localObject2).append(File.separator);
    ((StringBuilder)localObject2).append("html");
    ((StringBuilder)localObject2).append(File.separator);
    ((StringBuilder)localObject2).append("dom_distiller_viewer.html");
    localObject2 = new File(((StringBuilder)localObject2).toString());
    Object localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append(paramString);
    ((StringBuilder)localObject3).append(File.separator);
    ((StringBuilder)localObject3).append("css");
    ((StringBuilder)localObject3).append(File.separator);
    ((StringBuilder)localObject3).append("distilledpage.css");
    paramString = new File(((StringBuilder)localObject3).toString());
    if ((((File)localObject1).exists()) && (((File)localObject2).exists()) && (paramString.exists())) {
      return true;
    }
    localObject3 = TAG;
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("checkPluginResourceExists FALSE ");
    ((StringBuilder)localObject3).append(((File)localObject1).exists());
    ((StringBuilder)localObject3).append("|");
    ((StringBuilder)localObject3).append(((File)localObject2).exists());
    ((StringBuilder)localObject3).append("|");
    ((StringBuilder)localObject3).append(paramString.exists());
    ((StringBuilder)localObject3).toString();
    return false;
  }
  
  private void doLoadPlugin(IWebpageReadModePluginLoadCallback paramIWebpageReadModePluginLoadCallback, String paramString1, String paramString2)
  {
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doLoadPlugin pluginPath= ");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", pluginVersion=");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).toString();
    if (TextUtils.isEmpty(paramString1))
    {
      onFailed(paramIWebpageReadModePluginLoadCallback, 1005, "plugin install, unzip dir is empty");
      return;
    }
    if (!checkPluginResourceExists(paramString1))
    {
      onFailed(paramIWebpageReadModePluginLoadCallback, 1006, "plugin install, so not exist");
      return;
    }
    this.mWebpageReadModePluginPath = paramString1;
    onSuccess(paramIWebpageReadModePluginLoadCallback, this.mWebpageReadModePluginPath);
  }
  
  private void downloadPlugin(final IWebpageReadModePluginLoadCallback paramIWebpageReadModePluginLoadCallback)
  {
    paramIWebpageReadModePluginLoadCallback = new IQBPluginSystemCallback()
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
        paramIWebpageReadModePluginLoadCallback.onPluginLoadProgress(paramAnonymousInt2);
      }
      
      public void onDownloadStart(String paramAnonymousString, int paramAnonymousInt)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadStart:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(" size:");
        localStringBuilder.append(paramAnonymousInt);
        localStringBuilder.toString();
        paramIWebpageReadModePluginLoadCallback.onPluginLoadStarted(paramAnonymousInt);
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
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("onPrepareFinished:");
          localStringBuilder.append(paramAnonymousString);
          localStringBuilder.toString();
          WebpageReadModePluginManager.this.doLoadPlugin(paramIWebpageReadModePluginLoadCallback, paramAnonymousQBPluginItemInfo.mUnzipDir, paramAnonymousQBPluginItemInfo.mVersion);
          return;
        }
        if ((paramAnonymousInt2 != 3014) && (paramAnonymousInt2 != 6008))
        {
          if (paramAnonymousInt2 == 3010)
          {
            WebpageReadModePluginManager.this.onFailed(paramIWebpageReadModePluginLoadCallback, 1003, "onPrepareFinished plugin install, user canceled");
            return;
          }
          if (paramAnonymousInt2 == 3029)
          {
            WebpageReadModePluginManager.this.onFailed(paramIWebpageReadModePluginLoadCallback, 1004, "onPrepareFinished plugin install, no plugin info");
            return;
          }
          paramAnonymousString = new StringBuilder();
          paramAnonymousString.append("onPrepareFinished errorCode: ");
          paramAnonymousString.append(paramAnonymousInt2);
          paramAnonymousString.toString();
          WebpageReadModePluginManager.this.onFailed(paramIWebpageReadModePluginLoadCallback, paramAnonymousInt2, "plugin install, unkonw error");
          return;
        }
        WebpageReadModePluginManager.this.onFailed(paramIWebpageReadModePluginLoadCallback, 1002, "onPrepareFinished plugin install, no space");
      }
      
      public void onPrepareStart(String paramAnonymousString)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onPrepareStart:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.toString();
      }
    };
    this.mPluginSystem.usePluginAsync("com.tencent.qb.plugin.webpage.readmode", 1, paramIWebpageReadModePluginLoadCallback, null, null, 1);
  }
  
  public static WebpageReadModePluginManager getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null) {
        sInstance = new WebpageReadModePluginManager(paramContext);
      }
      paramContext = sInstance;
      return paramContext;
    }
    finally {}
  }
  
  public void loadWebpageReadModePlugin(final IWebpageReadModePluginLoadCallback paramIWebpageReadModePluginLoadCallback)
  {
    String str = TAG;
    if (this.mPluginSystem == null) {
      this.mPluginSystem = QBPluginSystem.getInstance(this.mContext);
    }
    str = this.mWebpageReadModePluginPath;
    if ((str != null) && (checkPluginResourceExists(str)))
    {
      onSuccess(paramIWebpageReadModePluginLoadCallback, this.mWebpageReadModePluginPath);
      return;
    }
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        WebpageReadModePluginManager.this.downloadPlugin(paramIWebpageReadModePluginLoadCallback);
      }
    });
  }
  
  public void onFailed(IWebpageReadModePluginLoadCallback paramIWebpageReadModePluginLoadCallback, int paramInt, String paramString)
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onFailed errorCode: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(", message: ");
    localStringBuilder.append(paramString);
    Log.e(str, localStringBuilder.toString());
    paramIWebpageReadModePluginLoadCallback.onPluginLoadFailed(paramInt, paramString);
  }
  
  public void onSuccess(IWebpageReadModePluginLoadCallback paramIWebpageReadModePluginLoadCallback, String paramString)
  {
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onSuccess pluginPath: ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    paramIWebpageReadModePluginLoadCallback.onPluginLoadSuccess(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\readmode\WebpageReadModePluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */