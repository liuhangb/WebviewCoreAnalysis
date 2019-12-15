package com.tencent.tbs.common.download.qb;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import java.lang.reflect.Method;

public class QBDownloadProxy
{
  private static QBDownloadProxy mInstance;
  
  public static QBDownloadProxy getInstance()
  {
    try
    {
      if (mInstance == null) {
        mInstance = new QBDownloadProxy();
      }
      QBDownloadProxy localQBDownloadProxy = mInstance;
      return localQBDownloadProxy;
    }
    finally {}
  }
  
  static Object invokeMethod(Object paramObject, String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    try
    {
      paramArrayOfClass = paramObject.getClass().getMethod(paramString, paramArrayOfClass);
      paramArrayOfClass.setAccessible(true);
      paramArrayOfClass = paramArrayOfClass.invoke(paramObject, paramVarArgs);
      return paramArrayOfClass;
    }
    catch (Throwable paramArrayOfClass)
    {
      paramVarArgs = new StringBuilder();
      paramVarArgs.append("'");
      paramVarArgs.append(paramObject);
      paramVarArgs.append("' invoke method '");
      paramVarArgs.append(paramString);
      paramVarArgs.append("' failed");
      Log.e("QBDownloadManager", paramVarArgs.toString(), paramArrayOfClass);
    }
    return null;
  }
  
  public void clearDownloadFile()
  {
    QBDownloadManager.getInstance().clearDownloadFile();
  }
  
  public QBDownloadListener getDefaultQbDownloadListener(Context paramContext)
  {
    return QBDownloadManager.getInstance().getDefaultQbDownloadListener(paramContext);
  }
  
  public int getQBDownloadProgress()
  {
    return QBDownloadManager.getInstance().getQBDownloadProgress();
  }
  
  public CurrentUrlProvider initCurrentUrlProvider(Object paramObject)
  {
    return new CurrentUrlProviderProxy(paramObject);
  }
  
  public QBDownloadListener initQbDownloadListener(Object paramObject)
  {
    return new QBDownloadListenerProxy(paramObject);
  }
  
  public QBInstallListener initQbInstallListener(Object paramObject)
  {
    return new QBInstallListenerProxy(paramObject);
  }
  
  public void installDownloadFile(Context paramContext, String paramString1, String paramString2, Intent paramIntent, Bundle paramBundle)
  {
    QBDownloadManager.getInstance().installDownloadFile(paramContext, paramString1, paramString2, paramIntent, paramBundle);
  }
  
  public void installDownloadFile(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    QBDownloadManager.getInstance().installDownloadFile(paramContext, paramString1, paramString2, paramBundle);
  }
  
  public void installDownloadFile(Context paramContext, String paramString1, String paramString2, CurrentUrlProviderProxy paramCurrentUrlProviderProxy, Bundle paramBundle)
  {
    QBDownloadManager.getInstance().installDownloadFile(paramContext, paramString1, paramString2, paramCurrentUrlProviderProxy, paramBundle);
  }
  
  public void installDownloadFile(Context paramContext, String paramString1, String paramString2, QBInstallListenerProxy paramQBInstallListenerProxy, Bundle paramBundle)
  {
    QBDownloadManager.getInstance().installDownloadFile(paramContext, paramString1, paramString2, paramQBInstallListenerProxy, paramBundle);
  }
  
  public void installDownloadFile(Context paramContext, String paramString1, String paramString2, String paramString3, Bundle paramBundle)
  {
    QBDownloadManager.getInstance().installDownloadFile(paramContext, paramString1, paramString2, paramString3, paramBundle);
  }
  
  public boolean isEverDownloaded()
  {
    return QBDownloadManager.getInstance().isEverDownloaded();
  }
  
  public boolean isQBDownloaded()
  {
    return QBDownloadManager.getInstance().isQBDownloaded();
  }
  
  public boolean isQBDownloading()
  {
    return QBDownloadManager.getInstance().isQBDownloading();
  }
  
  public boolean isSilentDownloading()
  {
    return false;
  }
  
  public void onCurrentUrlProviderDismissed(Context paramContext, CurrentUrlProviderProxy paramCurrentUrlProviderProxy)
  {
    QBDownloadManager.getInstance().onCurrentUrlProviderDismissed(paramContext, paramCurrentUrlProviderProxy);
  }
  
  public void registerQBDownloadListener(QBDownloadListenerProxy paramQBDownloadListenerProxy)
  {
    QBDownloadManager.getInstance().registerQBDownloadListener(paramQBDownloadListenerProxy);
  }
  
  public void registerQBInstallListener(QBInstallListenerProxy paramQBInstallListenerProxy)
  {
    QBDownloadManager.getInstance().registerQBInstallListener(paramQBInstallListenerProxy);
  }
  
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListenerProxy paramQBDownloadListenerProxy, Intent paramIntent, Bundle paramBundle)
  {
    return QBDownloadManager.getInstance().startDownload(paramContext, paramString1, paramString2, paramString3, paramQBDownloadListenerProxy, paramIntent, paramBundle);
  }
  
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListenerProxy paramQBDownloadListenerProxy, Bundle paramBundle)
  {
    return QBDownloadManager.getInstance().startDownload(paramContext, paramString1, paramString2, paramString3, paramQBDownloadListenerProxy, paramBundle);
  }
  
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListenerProxy paramQBDownloadListenerProxy, CurrentUrlProviderProxy paramCurrentUrlProviderProxy, Bundle paramBundle)
  {
    return QBDownloadManager.getInstance().startDownload(paramContext, paramString1, paramString2, paramString3, paramQBDownloadListenerProxy, paramCurrentUrlProviderProxy, paramBundle);
  }
  
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListenerProxy paramQBDownloadListenerProxy, QBInstallListenerProxy paramQBInstallListenerProxy, Bundle paramBundle)
  {
    return QBDownloadManager.getInstance().startDownload(paramContext, paramString1, paramString2, paramString3, paramQBDownloadListenerProxy, paramQBInstallListenerProxy, paramBundle);
  }
  
  public int startDownload(Context paramContext, String paramString1, String paramString2, String paramString3, QBDownloadListenerProxy paramQBDownloadListenerProxy, String paramString4, Bundle paramBundle)
  {
    return QBDownloadManager.getInstance().startDownload(paramContext, paramString1, paramString2, paramString3, paramQBDownloadListenerProxy, paramString4, paramBundle);
  }
  
  public int startDownloadWithDefaultDownloadListener(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, Bundle paramBundle)
  {
    return QBDownloadManager.getInstance().startDownload(paramContext, paramString1, paramString2, paramString3, QBDownloadManager.getInstance().getDefaultQbDownloadListener(paramContext), paramString4, paramBundle);
  }
  
  public int startSilentDownload(Context paramContext)
  {
    return QBDownloadManager.getInstance().startSilentDownload(paramContext);
  }
  
  public int startSilentDownload(Context paramContext, QBDownloadListenerProxy paramQBDownloadListenerProxy)
  {
    return QBDownloadManager.getInstance().startSilentDownload(paramContext, paramQBDownloadListenerProxy);
  }
  
  public void unregisterQBDownloadListener(QBDownloadListenerProxy paramQBDownloadListenerProxy)
  {
    QBDownloadManager.getInstance().unregisterQBDownloadListener(paramQBDownloadListenerProxy);
  }
  
  public void unregisterQBInstallListener(QBInstallListenerProxy paramQBInstallListenerProxy)
  {
    QBDownloadManager.getInstance().unregisterQBInstallListener(paramQBInstallListenerProxy);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\QBDownloadProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */