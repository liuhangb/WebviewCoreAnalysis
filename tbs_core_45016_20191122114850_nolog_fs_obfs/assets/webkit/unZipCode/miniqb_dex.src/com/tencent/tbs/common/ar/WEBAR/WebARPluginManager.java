package com.tencent.tbs.common.ar.WEBAR;

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

public class WebARPluginManager
{
  private static final int AR_TYPE_WEBAR_ARCORE = 5;
  private static final int AR_TYPE_WEBAR_MARKER = 2;
  private static final int AR_TYPE_WEBAR_SLAM = 1;
  private static final int AR_TYPE_WEBAR_SLAM_VIO = 3;
  private static final int AR_TYPE_WEBAR_TFLITE = 4;
  private static final int ERROR_AR_TYPE = 1008;
  private static final int ERROR_LIB_NOT_EXIST = 1006;
  private static final int ERROR_NO_PLUGIN_INFO = 1004;
  private static final int ERROR_NO_SPACE = 1002;
  private static final int ERROR_PERMISSION_DENY = 1007;
  private static final int ERROR_UNKNOWN = 1001;
  private static final int ERROR_UNZIP_DIR_EMPTY = 1005;
  private static final int ERROR_USER_CANCELED = 1003;
  private static final String PLUGIN_ARCORE_PACKAGE_NAME = "com.tencent.qb.plugin.webar.arcore";
  private static final String PLUGIN_MARKER_PACKAGE_NAME = "com.tencent.qb.plugin.webar.marker";
  private static final String PLUGIN_SLAM_PACKAGE_NAME = "com.tencent.qb.plugin.webar.markerless";
  private static final String PLUGIN_TFLITE_PACKAGE_NAME = "com.tencent.qb.plugin.webar.tflite";
  private static final String TAG = "WebARPluginManager";
  private String mARCorePluginPath = null;
  private Context mContext;
  private String mMarkerPluginPath = null;
  private QBPluginSystem mPluginSystem;
  private String mSlamPluginPath = null;
  private String mTFLitePluginPath = null;
  
  WebARPluginManager(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private void downloadPlugin(final int paramInt, final IWebAREngineCallback paramIWebAREngineCallback)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("downloadPlugin arType: ");
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
        paramIWebAREngineCallback.onDownloadProgress(paramAnonymousInt2);
      }
      
      public void onDownloadStart(String paramAnonymousString, int paramAnonymousInt)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadStart:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(" size:");
        localStringBuilder.append(paramAnonymousInt);
        localStringBuilder.toString();
        paramIWebAREngineCallback.onDownloadStart(paramAnonymousInt);
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
          WebARPluginManager.this.doLoadPlugin(paramIWebAREngineCallback, paramInt, paramAnonymousQBPluginItemInfo.mUnzipDir);
          return;
        }
        if ((paramAnonymousInt2 != 3014) && (paramAnonymousInt2 != 6008))
        {
          if (paramAnonymousInt2 == 3010)
          {
            WebARPluginManager.this.onFailed(paramIWebAREngineCallback, paramInt, 1003, "plugin install, user canceled");
            return;
          }
          if (paramAnonymousInt2 == 3029)
          {
            WebARPluginManager.this.onFailed(paramIWebAREngineCallback, paramInt, 1004, "plugin install, no plugin info");
            return;
          }
          paramAnonymousString = new StringBuilder();
          paramAnonymousString.append("onPrepareFinished errorCode: ");
          paramAnonymousString.append(paramAnonymousInt2);
          Log.e("WebARPluginManager", paramAnonymousString.toString());
          WebARPluginManager.this.onFailed(paramIWebAREngineCallback, paramInt, paramAnonymousInt2, "plugin install, unkonw error");
          return;
        }
        WebARPluginManager.this.onFailed(paramIWebAREngineCallback, paramInt, 1002, "plugin install,  no space");
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
    if ((paramInt != 1) && (paramInt != 3))
    {
      if (paramInt == 2)
      {
        this.mPluginSystem.usePluginAsync("com.tencent.qb.plugin.webar.marker", 1, (IQBPluginSystemCallback)localObject, null, null, i);
        return;
      }
      if (paramInt == 4)
      {
        this.mPluginSystem.usePluginAsync("com.tencent.qb.plugin.webar.tflite", 1, (IQBPluginSystemCallback)localObject, null, null, i);
        return;
      }
      if (paramInt == 5)
      {
        this.mPluginSystem.usePluginAsync("com.tencent.qb.plugin.webar.arcore", 1, (IQBPluginSystemCallback)localObject, null, null, i);
        return;
      }
      Log.e("WebARPluginManager", "arType is not supported");
      onFailed(paramIWebAREngineCallback, paramInt, 1008, "arType is not supported");
      return;
    }
    this.mPluginSystem.usePluginAsync("com.tencent.qb.plugin.webar.markerless", 1, (IQBPluginSystemCallback)localObject, null, null, i);
  }
  
  private void loadWebARPlugin(final int paramInt, final IWebAREngineCallback paramIWebAREngineCallback)
  {
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        WebARPluginManager.this.downloadPlugin(paramInt, paramIWebAREngineCallback);
      }
    });
  }
  
  protected void doLoadPlugin(IWebAREngineCallback paramIWebAREngineCallback, int paramInt, String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doLoadPlugin arType: ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(" pluginPath = ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    if (TextUtils.isEmpty(paramString))
    {
      onFailed(paramIWebAREngineCallback, paramInt, 1005, "plugin install, unzip dir is empty");
      return;
    }
    localObject = "";
    if ((paramInt != 1) && (paramInt != 3))
    {
      if (paramInt == 2) {
        localObject = "libmtt_marker.so";
      } else if (paramInt == 4) {
        localObject = "libmtt_tflite.so";
      } else if (paramInt == 5) {
        localObject = "libmtt_arcore.so";
      }
    }
    else {
      localObject = "libmtt_markerless.so";
    }
    if (!new File(paramString, (String)localObject).isFile())
    {
      onFailed(paramIWebAREngineCallback, paramInt, 1006, "plugin install, so not exist");
      return;
    }
    if ((paramInt != 1) && (paramInt != 3))
    {
      if (paramInt == 2) {
        this.mMarkerPluginPath = paramString;
      } else if (paramInt == 4) {
        this.mTFLitePluginPath = paramString;
      } else if (paramInt == 5) {
        this.mARCorePluginPath = paramString;
      }
    }
    else {
      this.mSlamPluginPath = paramString;
    }
    onSuccess(paramIWebAREngineCallback, paramInt, paramString);
  }
  
  public void loadWebAREngine(int paramInt, IWebAREngineCallback paramIWebAREngineCallback)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("loadWebARPlugin arType: ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).toString();
    if (this.mPluginSystem == null) {
      this.mPluginSystem = QBPluginSystem.getInstance(this.mContext);
    }
    if ((paramInt != 1) && (paramInt != 3))
    {
      if (paramInt == 2)
      {
        localObject = this.mMarkerPluginPath;
        if (localObject != null) {
          onSuccess(paramIWebAREngineCallback, paramInt, (String)localObject);
        }
      }
      else if (paramInt == 4)
      {
        localObject = this.mTFLitePluginPath;
        if (localObject != null) {
          onSuccess(paramIWebAREngineCallback, paramInt, (String)localObject);
        }
      }
      else if (paramInt == 5)
      {
        localObject = this.mARCorePluginPath;
        if (localObject != null) {
          onSuccess(paramIWebAREngineCallback, paramInt, (String)localObject);
        }
      }
    }
    else
    {
      localObject = this.mSlamPluginPath;
      if (localObject != null)
      {
        onSuccess(paramIWebAREngineCallback, paramInt, (String)localObject);
        return;
      }
    }
    loadWebARPlugin(paramInt, paramIWebAREngineCallback);
  }
  
  public void onFailed(IWebAREngineCallback paramIWebAREngineCallback, int paramInt1, int paramInt2, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onFailed errorCode: ");
    localStringBuilder.append(paramInt2);
    localStringBuilder.append(" arType: ");
    localStringBuilder.append(paramInt1);
    localStringBuilder.append(" message: ");
    localStringBuilder.append(paramString);
    Log.e("WebARPluginManager", localStringBuilder.toString());
    paramIWebAREngineCallback.onLoadFailed(paramInt1, paramInt2, paramString);
  }
  
  public void onSuccess(IWebAREngineCallback paramIWebAREngineCallback, int paramInt, String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onSuccess arType: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(" pluginPath = ");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    paramIWebAREngineCallback.onLoadSuccess(paramInt, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\WEBAR\WebARPluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */