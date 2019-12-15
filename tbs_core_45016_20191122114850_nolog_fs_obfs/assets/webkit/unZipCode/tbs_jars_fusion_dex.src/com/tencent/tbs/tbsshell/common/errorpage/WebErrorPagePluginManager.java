package com.tencent.tbs.tbsshell.common.errorpage;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.plugin.IQBPluginSystemCallback;
import com.tencent.common.plugin.QBPluginItemInfo;
import com.tencent.common.plugin.QBPluginSystem;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import java.io.File;

public class WebErrorPagePluginManager
{
  private static final int ERROR_LIB_NOT_EXIST = 1006;
  private static final int ERROR_NO_PLUGIN_INFO = 1004;
  private static final int ERROR_NO_SPACE = 1002;
  private static final int ERROR_UNZIP_DIR_EMPTY = 1005;
  private static final int ERROR_USER_CANCELED = 1003;
  private static String TAG = "WebErrorPagePluginManager";
  private static final String WEBPAGE_ERRORPAGE_PLUGIN_PACKAGE_NAME = "com.tencent.qb.plugin.webpage.errorpage";
  private static WebErrorPagePluginManager sInstance;
  private Context mContext = null;
  private QBPluginSystem mPluginSystem = null;
  private String mWebErrorPagePluginPath = null;
  
  private WebErrorPagePluginManager(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private boolean checkPluginResourceExists(String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(File.separator);
    ((StringBuilder)localObject).append("errorpage");
    ((StringBuilder)localObject).append(File.separator);
    ((StringBuilder)localObject).append("x5_error.html");
    paramString = new File(((StringBuilder)localObject).toString());
    if (!paramString.exists())
    {
      localObject = TAG;
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("checkPluginResourceExists FALSE");
      ((StringBuilder)localObject).append(paramString.exists());
      ((StringBuilder)localObject).toString();
      return false;
    }
    return true;
  }
  
  private void doLoadPlugin(IWebErrorPagePluginLoadCallback paramIWebErrorPagePluginLoadCallback, String paramString1, String paramString2)
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
      onFailed(paramIWebErrorPagePluginLoadCallback, 1005, "plugin install, unzip dir is empty");
      return;
    }
    if (!checkPluginResourceExists(paramString1))
    {
      onFailed(paramIWebErrorPagePluginLoadCallback, 1006, "plugin install, so not exist");
      return;
    }
    this.mWebErrorPagePluginPath = paramString1;
    onSuccess(paramIWebErrorPagePluginLoadCallback, this.mWebErrorPagePluginPath);
  }
  
  private void downloadPlugin(final IWebErrorPagePluginLoadCallback paramIWebErrorPagePluginLoadCallback)
  {
    paramIWebErrorPagePluginLoadCallback = new IQBPluginSystemCallback()
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
        paramIWebErrorPagePluginLoadCallback.onPluginLoadProgress(paramAnonymousInt2);
      }
      
      public void onDownloadStart(String paramAnonymousString, int paramAnonymousInt)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadStart:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(" size:");
        localStringBuilder.append(paramAnonymousInt);
        localStringBuilder.toString();
        paramIWebErrorPagePluginLoadCallback.onPluginLoadStarted(paramAnonymousInt);
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
          WebErrorPagePluginManager.this.doLoadPlugin(paramIWebErrorPagePluginLoadCallback, paramAnonymousQBPluginItemInfo.mUnzipDir, paramAnonymousQBPluginItemInfo.mVersion);
          return;
        }
        if ((paramAnonymousInt2 != 3014) && (paramAnonymousInt2 != 6008))
        {
          if (paramAnonymousInt2 == 3010)
          {
            WebErrorPagePluginManager.this.onFailed(paramIWebErrorPagePluginLoadCallback, 1003, "plugin install, user canceled");
            return;
          }
          if (paramAnonymousInt2 == 3029)
          {
            WebErrorPagePluginManager.this.onFailed(paramIWebErrorPagePluginLoadCallback, 1004, "plugin install, no plugin info");
            return;
          }
          paramAnonymousString = WebErrorPagePluginManager.TAG;
          paramAnonymousQBPluginItemInfo = new StringBuilder();
          paramAnonymousQBPluginItemInfo.append("onPrepareFinished errorCode: ");
          paramAnonymousQBPluginItemInfo.append(paramAnonymousInt2);
          Log.e(paramAnonymousString, paramAnonymousQBPluginItemInfo.toString());
          WebErrorPagePluginManager.this.onFailed(paramIWebErrorPagePluginLoadCallback, paramAnonymousInt2, "plugin install, unknow error");
          return;
        }
        WebErrorPagePluginManager.this.onFailed(paramIWebErrorPagePluginLoadCallback, 1002, "plugin install,  no space");
      }
      
      public void onPrepareStart(String paramAnonymousString)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onPrepareStart:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.toString();
      }
    };
    this.mPluginSystem.usePluginAsync("com.tencent.qb.plugin.webpage.errorpage", 1, paramIWebErrorPagePluginLoadCallback, null, null, 1);
  }
  
  public static WebErrorPagePluginManager getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null) {
        sInstance = new WebErrorPagePluginManager(paramContext);
      }
      paramContext = sInstance;
      return paramContext;
    }
    finally {}
  }
  
  public void loadWebErrorPagePlugin(final IWebErrorPagePluginLoadCallback paramIWebErrorPagePluginLoadCallback)
  {
    String str = TAG;
    if (this.mPluginSystem == null) {
      this.mPluginSystem = QBPluginSystem.getInstance(this.mContext);
    }
    str = this.mWebErrorPagePluginPath;
    if ((str != null) && (checkPluginResourceExists(str)))
    {
      onSuccess(paramIWebErrorPagePluginLoadCallback, this.mWebErrorPagePluginPath);
      return;
    }
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        WebErrorPagePluginManager.this.downloadPlugin(paramIWebErrorPagePluginLoadCallback);
      }
    });
  }
  
  public void onFailed(IWebErrorPagePluginLoadCallback paramIWebErrorPagePluginLoadCallback, int paramInt, String paramString)
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onFailed errorCode: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(", message: ");
    localStringBuilder.append(paramString);
    Log.e(str, localStringBuilder.toString());
    paramIWebErrorPagePluginLoadCallback.onPluginLoadFailed(paramInt, paramString);
  }
  
  public void onSuccess(IWebErrorPagePluginLoadCallback paramIWebErrorPagePluginLoadCallback, String paramString)
  {
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onSuccess pluginPath: ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    paramIWebErrorPagePluginLoadCallback.onPluginLoadSuccess(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\errorpage\WebErrorPagePluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */