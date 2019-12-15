package com.tencent.tbs.tbsshell.common.webrtc;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.plugin.IQBPluginSystemCallback;
import com.tencent.common.plugin.QBPluginItemInfo;
import com.tencent.common.plugin.QBPluginSystem;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class WebRtcPluginManager
{
  private static final int ERROR_LIB_NOT_EXIST = 1006;
  private static final int ERROR_NO_PLUGIN_INFO = 1004;
  private static final int ERROR_NO_SPACE = 1002;
  private static final int ERROR_UNZIP_DIR_EMPTY = 1005;
  private static final int ERROR_USER_CANCELED = 1003;
  private static final String PLUGIN_WEBRTC_PACKAGE_NAME = "com.tencent.qb.m66.plugin.webrtc";
  private static String TAG = "WebRtcPluginManager";
  private static WebRtcPluginManager sInstance;
  private Context mContext;
  private boolean mPluginIsDownloaded = false;
  private QBPluginSystem mPluginSystem;
  private String mPluginVersion = null;
  private String mWebRtcPluginPath = null;
  
  WebRtcPluginManager(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private boolean checkPluginExists(String paramString)
  {
    return new File(paramString).exists();
  }
  
  private void doLoadPlugin(IWebRtcPluginLoadCallback paramIWebRtcPluginLoadCallback, String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1))
    {
      onFailed(paramIWebRtcPluginLoadCallback, 1005, "plugin install, unzip dir is empty");
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString1);
    localStringBuilder.append(File.separator);
    localStringBuilder.append("libmttwebrtc.so");
    paramString1 = localStringBuilder.toString();
    if (!checkPluginExists(paramString1))
    {
      onFailed(paramIWebRtcPluginLoadCallback, 1006, "plugin install, so not exist");
      return;
    }
    this.mWebRtcPluginPath = paramString1;
    onSuccess(paramIWebRtcPluginLoadCallback, this.mWebRtcPluginPath, paramString2);
  }
  
  private void downloadPlugin(final IWebRtcPluginLoadCallback paramIWebRtcPluginLoadCallback)
  {
    paramIWebRtcPluginLoadCallback = new IQBPluginSystemCallback()
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
        paramIWebRtcPluginLoadCallback.onPluginLoadProgress(paramAnonymousInt2);
      }
      
      public void onDownloadStart(String paramAnonymousString, int paramAnonymousInt)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadStart:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(" size:");
        localStringBuilder.append(paramAnonymousInt);
        localStringBuilder.toString();
        paramIWebRtcPluginLoadCallback.onPluginLoadStarted(paramAnonymousInt);
      }
      
      public void onDownloadSuccessed(String paramAnonymousString1, String paramAnonymousString2)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadSuccessed:");
        localStringBuilder.append(paramAnonymousString1);
        localStringBuilder.append(" filePath:");
        localStringBuilder.append(paramAnonymousString2);
        localStringBuilder.toString();
        WebRtcPluginManager.access$302(WebRtcPluginManager.this, true);
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
          WebRtcPluginManager.this.doLoadPlugin(paramIWebRtcPluginLoadCallback, paramAnonymousQBPluginItemInfo.mUnzipDir, paramAnonymousQBPluginItemInfo.mVersion);
          return;
        }
        if ((paramAnonymousInt2 != 3014) && (paramAnonymousInt2 != 6008))
        {
          if (paramAnonymousInt2 == 3010)
          {
            WebRtcPluginManager.this.onFailed(paramIWebRtcPluginLoadCallback, 1003, "plugin install, user canceled");
            return;
          }
          if (paramAnonymousInt2 == 3029)
          {
            WebRtcPluginManager.this.onFailed(paramIWebRtcPluginLoadCallback, 1004, "plugin install, no plugin info");
            return;
          }
          paramAnonymousString = WebRtcPluginManager.TAG;
          paramAnonymousQBPluginItemInfo = new StringBuilder();
          paramAnonymousQBPluginItemInfo.append("onPrepareFinished errorCode: ");
          paramAnonymousQBPluginItemInfo.append(paramAnonymousInt2);
          Log.e(paramAnonymousString, paramAnonymousQBPluginItemInfo.toString());
          WebRtcPluginManager.this.onFailed(paramIWebRtcPluginLoadCallback, paramAnonymousInt2, "plugin install, unkonw error");
          return;
        }
        WebRtcPluginManager.this.onFailed(paramIWebRtcPluginLoadCallback, 1002, "plugin install,  no space");
      }
      
      public void onPrepareStart(String paramAnonymousString)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onPrepareStart:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.toString();
      }
    };
    this.mPluginSystem.usePluginAsync("com.tencent.qb.m66.plugin.webrtc", 1, paramIWebRtcPluginLoadCallback, null, null, 1);
  }
  
  public static WebRtcPluginManager getInstance(Context paramContext)
  {
    try
    {
      if (sInstance == null) {
        sInstance = new WebRtcPluginManager(paramContext);
      }
      paramContext = sInstance;
      return paramContext;
    }
    finally {}
  }
  
  public void loadWebRtcPlugin(final IWebRtcPluginLoadCallback paramIWebRtcPluginLoadCallback)
  {
    String str = TAG;
    if (this.mPluginSystem == null) {
      this.mPluginSystem = QBPluginSystem.getInstance(this.mContext);
    }
    str = this.mWebRtcPluginPath;
    if ((str != null) && (checkPluginExists(str)))
    {
      onSuccess(paramIWebRtcPluginLoadCallback, this.mWebRtcPluginPath, this.mPluginVersion);
      return;
    }
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        WebRtcPluginManager.this.downloadPlugin(paramIWebRtcPluginLoadCallback);
      }
    });
  }
  
  public void onFailed(IWebRtcPluginLoadCallback paramIWebRtcPluginLoadCallback, int paramInt, String paramString)
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onFailed errorCode: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(" message: ");
    localStringBuilder.append(paramString);
    Log.e(str, localStringBuilder.toString());
    paramIWebRtcPluginLoadCallback.onPluginLoadFailed(paramInt, paramString);
    paramIWebRtcPluginLoadCallback = new HashMap();
    paramIWebRtcPluginLoadCallback.put("device", Build.MODEL);
    paramIWebRtcPluginLoadCallback.put("errorCode", String.valueOf(paramInt));
    X5CoreBeaconUploader.getInstance(this.mContext).upLoadToBeacon("MTT_QB_WEBRTC_PLUGIN_LOAD_FAILED", paramIWebRtcPluginLoadCallback);
  }
  
  public void onSuccess(IWebRtcPluginLoadCallback paramIWebRtcPluginLoadCallback, String paramString1, String paramString2)
  {
    Object localObject = TAG;
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onSuccess  pluginPath = ");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).toString();
    paramIWebRtcPluginLoadCallback.onPluginLoadSuccess(paramString1);
    if (this.mPluginIsDownloaded)
    {
      this.mPluginIsDownloaded = false;
      this.mPluginVersion = paramString2;
      paramIWebRtcPluginLoadCallback = new HashMap();
      paramIWebRtcPluginLoadCallback.put("device", Build.MODEL);
      paramIWebRtcPluginLoadCallback.put("pluginVersion", paramString2);
      paramIWebRtcPluginLoadCallback.put("coreVersion", TbsBaseModuleShell.getX5CoreVersion());
      X5CoreBeaconUploader.getInstance(this.mContext).upLoadToBeacon("MTT_QB_WEBRTC_PLUGIN_LOAD_SUCCESS", paramIWebRtcPluginLoadCallback);
      paramString1 = TAG;
      paramString1 = new StringBuilder();
      paramString1.append("onSuccess  params = ");
      paramString1.append(paramIWebRtcPluginLoadCallback.toString());
      paramString1.toString();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\webrtc\WebRtcPluginManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */