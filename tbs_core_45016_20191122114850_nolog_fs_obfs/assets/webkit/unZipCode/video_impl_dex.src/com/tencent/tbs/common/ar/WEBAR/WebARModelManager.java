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
import java.util.HashMap;
import java.util.Map;

public class WebARModelManager
{
  private static final int ERROR_MODEL_CONFIG_NOT_EXIST = 1007;
  private static final int ERROR_MODEL_NOT_EXIST = 1006;
  private static final int ERROR_NO_MODEL_INFO = 1004;
  private static final int ERROR_NO_SPACE = 1002;
  private static final int ERROR_UNZIP_DIR_EMPTY = 1005;
  private static final int ERROR_USER_CANCELED = 1003;
  private static final String MODEL_CONFIG_PATH_SUFFIX = ".txt";
  private static final String MODEL_PATH_SUFFIX = ".tflite";
  private static final String PREFIX_MODEL_NAME = "com.tencent.qb.webar.model.";
  private static final String TAG = "WebARModelManager";
  private Context mContext;
  private Map<String, String> mModelMaps = new HashMap();
  private QBPluginSystem mPluginSystem;
  
  WebARModelManager(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private void doLoadModel(IWebARModelCallback paramIWebARModelCallback, String paramString1, String paramString2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("doLoadModel modelName: ");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(" modelDir ");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).toString();
    if (TextUtils.isEmpty(paramString2))
    {
      onFailed(paramIWebARModelCallback, paramString1, 1005, "model install, unzip dir is empty");
      return;
    }
    localObject = getFilePath(paramString2, ".tflite");
    String str = getFilePath(paramString2, ".txt");
    if (localObject == null)
    {
      onFailed(paramIWebARModelCallback, paramString1, 1006, "model install, no model file");
      return;
    }
    if (str == null)
    {
      onFailed(paramIWebARModelCallback, paramString1, 1007, "model install, no model config file");
      return;
    }
    this.mModelMaps.put(paramString1, paramString2);
    onSuccess(paramIWebARModelCallback, paramString1, (String)localObject, str);
  }
  
  private void downloadModel(final String paramString, final IWebARModelCallback paramIWebARModelCallback)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("com.tencent.qb.webar.model.");
    ((StringBuilder)localObject).append(paramString);
    localObject = ((StringBuilder)localObject).toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("downloadModel modelPkgName: ");
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(" modelName: ");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    paramString = new IQBPluginSystemCallback()
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
      }
      
      public void onDownloadStart(String paramAnonymousString, int paramAnonymousInt)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("onDownloadStart:");
        localStringBuilder.append(paramAnonymousString);
        localStringBuilder.append(" size:");
        localStringBuilder.append(paramAnonymousInt);
        localStringBuilder.toString();
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
          WebARModelManager.this.doLoadModel(paramIWebARModelCallback, paramString, paramAnonymousQBPluginItemInfo.mUnzipDir);
          return;
        }
        if ((paramAnonymousInt2 != 3014) && (paramAnonymousInt2 != 6008))
        {
          if (paramAnonymousInt2 == 3010)
          {
            WebARModelManager.this.onFailed(paramIWebARModelCallback, paramString, 1003, "model install, user canceled");
            return;
          }
          if (paramAnonymousInt2 == 3029)
          {
            WebARModelManager.this.onFailed(paramIWebARModelCallback, paramString, 1004, "model install, no plugin info");
            return;
          }
          paramAnonymousString = new StringBuilder();
          paramAnonymousString.append("onPrepareFinished errorCode: ");
          paramAnonymousString.append(paramAnonymousInt2);
          Log.e("WebARModelManager", paramAnonymousString.toString());
          WebARModelManager.this.onFailed(paramIWebARModelCallback, paramString, paramAnonymousInt2, "model install, unkonw error");
          return;
        }
        WebARModelManager.this.onFailed(paramIWebARModelCallback, paramString, 1002, "model install,  no space");
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
    this.mPluginSystem.usePluginAsync((String)localObject, 1, paramString, null, null, i);
  }
  
  private String getFilePath(String paramString1, String paramString2)
  {
    paramString1 = new File(paramString1).listFiles();
    int i = 0;
    while (i < paramString1.length)
    {
      Object localObject = paramString1[i];
      if ((((File)localObject).isFile()) && (((File)localObject).getPath().substring(((File)localObject).getPath().length() - paramString2.length()).equals(paramString2))) {
        return ((File)localObject).getPath();
      }
      i += 1;
    }
    return null;
  }
  
  private void onFailed(IWebARModelCallback paramIWebARModelCallback, String paramString1, int paramInt, String paramString2)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onFailed errorCode: ");
    localStringBuilder.append(paramInt);
    localStringBuilder.append(" modelName: ");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(" message: ");
    localStringBuilder.append(paramString2);
    Log.e("WebARModelManager", localStringBuilder.toString());
    paramIWebARModelCallback.onModelLoadFailed(paramString1, paramInt, paramString2);
  }
  
  private void onSuccess(IWebARModelCallback paramIWebARModelCallback, String paramString1, String paramString2, String paramString3)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onSuccess modelName: ");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(" modelPath = ");
    localStringBuilder.append(paramString2);
    localStringBuilder.append(" modelConfigPath: ");
    localStringBuilder.append(paramString3);
    localStringBuilder.toString();
    paramIWebARModelCallback.onModelLoadSuccess(paramString1, paramString2, paramString3);
  }
  
  public void loadWebARModel(final String paramString, final IWebARModelCallback paramIWebARModelCallback)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("loadWebARModel modelName: ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    if (this.mPluginSystem == null) {
      this.mPluginSystem = QBPluginSystem.getInstance(this.mContext);
    }
    if (this.mModelMaps.containsKey(paramString))
    {
      localObject = (String)this.mModelMaps.get(paramString);
      onSuccess(paramIWebARModelCallback, paramString, getFilePath((String)localObject, ".tflite"), getFilePath((String)localObject, ".txt"));
      return;
    }
    BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        WebARModelManager.this.downloadModel(paramString, paramIWebARModelCallback);
      }
    });
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\WEBAR\WebARModelManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */