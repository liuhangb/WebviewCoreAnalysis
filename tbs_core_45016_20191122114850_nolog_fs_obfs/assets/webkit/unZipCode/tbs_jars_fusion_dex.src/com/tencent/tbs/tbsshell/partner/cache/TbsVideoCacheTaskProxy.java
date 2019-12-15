package com.tencent.tbs.tbsshell.partner.cache;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.tencent.mtt.video.browser.export.wc.IWonderCacheTask;
import com.tencent.mtt.video.browser.export.wc.IWonderCacheTaskMgr;
import com.tencent.mtt.video.browser.export.wc.IWonderCacheTaskOwner;
import com.tencent.tbs.tbsshell.partner.player.TbsVideoManager;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class TbsVideoCacheTaskProxy
  implements IWonderCacheTaskOwner
{
  static final String TAG = "TbsVideoCacheTaskProxy";
  private IWonderCacheTask mDownloader = null;
  private Object mListener = null;
  private HashMap<String, Method> mListenerMethods = new HashMap();
  boolean mTaskBroadcastStart = false;
  private String mTaskFileDownloadFolder;
  private String mTaskFileName;
  private IWonderCacheTaskMgr wcm = null;
  
  public TbsVideoCacheTaskProxy(Context paramContext, Object paramObject, Bundle paramBundle)
  {
    this.mListener = paramObject;
    if (paramBundle == null)
    {
      if (this.mListener != null)
      {
        paramContext = new Bundle();
        paramContext.putInt("errorCode", -1);
        paramContext.putString("msg", "param error!");
        invokeMethod("onCacheInit", new Class[] { Bundle.class }, new Object[] { paramContext });
      }
      return;
    }
    String str = paramBundle.getString("url");
    this.mTaskFileName = paramBundle.getString("filename");
    this.mTaskFileDownloadFolder = paramBundle.getString("folderPath");
    if (paramBundle.containsKey("header")) {
      paramObject = (Map)paramBundle.getSerializable("header");
    } else {
      paramObject = null;
    }
    if ((str != null) && (!str.isEmpty()))
    {
      paramBundle = this.mTaskFileName;
      if ((paramBundle != null) && (!paramBundle.isEmpty()))
      {
        if (this.wcm == null) {
          this.wcm = TbsVideoManager.getInstance().getWonderCacheTaskManager(paramContext);
        }
        paramContext = this.wcm;
        if (paramContext != null)
        {
          this.mDownloader = paramContext.createCacheTask(-1, str, this.mTaskFileName, this.mTaskFileDownloadFolder, (Map)paramObject, this, null);
          paramContext = this.mDownloader;
          if (paramContext != null)
          {
            paramContext.setFinalCacheFile(this.mTaskFileName);
            this.mDownloader.setFinalCacheFolder(this.mTaskFileDownloadFolder);
          }
        }
        else if (this.mListener != null)
        {
          paramContext = new Bundle();
          paramContext.putInt("errorCode", -1);
          paramContext.putString("msg", "can not get cache manager!");
          invokeMethod("onCacheInit", new Class[] { Bundle.class }, new Object[] { paramContext });
        }
        return;
      }
    }
    if (this.mListener != null)
    {
      paramContext = new Bundle();
      paramContext.putInt("errorCode", -1);
      paramContext.putString("msg", "param error!");
      invokeMethod("onCacheInit", new Class[] { Bundle.class }, new Object[] { paramContext });
    }
  }
  
  public boolean canMemoryCache()
  {
    return false;
  }
  
  public long getContentLength()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getContentLength mDownloader = ");
    if (this.mDownloader == null) {
      localObject = "null";
    } else {
      localObject = "not null";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.toString();
    Object localObject = this.mDownloader;
    if (localObject != null) {
      return ((IWonderCacheTask)localObject).getContentLength();
    }
    return 0L;
  }
  
  public long getDownloadedSize()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getDownloadedSize mDownloader = ");
    if (this.mDownloader == null) {
      localObject = "null";
    } else {
      localObject = "not null";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.toString();
    Object localObject = this.mDownloader;
    if (localObject != null) {
      return ((IWonderCacheTask)localObject).getDownloadedSize();
    }
    return 0L;
  }
  
  public String getJumpUrl(String paramString)
  {
    return null;
  }
  
  public int getPriority()
  {
    return 0;
  }
  
  public int getProgress()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getProgress mDownloader = ");
    if (this.mDownloader == null) {
      localObject = "null";
    } else {
      localObject = "not null";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.toString();
    Object localObject = this.mDownloader;
    if (localObject != null) {
      return ((IWonderCacheTask)localObject).getProgress();
    }
    return 0;
  }
  
  public Object invokeMethod(String paramString, Class<?>[] paramArrayOfClass, Object... paramVarArgs)
  {
    Method localMethod2 = (Method)this.mListenerMethods.get(paramString);
    Method localMethod1 = localMethod2;
    if (localMethod2 == null) {
      try
      {
        localMethod1 = this.mListener.getClass().getDeclaredMethod(paramString, paramArrayOfClass);
        this.mListenerMethods.put(paramString, localMethod1);
      }
      catch (NoSuchMethodException paramString)
      {
        paramString.printStackTrace();
        return null;
      }
    }
    try
    {
      localMethod1.setAccessible(true);
      if (paramVarArgs.length == 0) {
        return localMethod1.invoke(this.mListener, new Object[0]);
      }
      paramString = localMethod1.invoke(this.mListener, paramVarArgs);
      return paramString;
    }
    catch (InvocationTargetException paramString)
    {
      paramString.printStackTrace();
      paramString.getCause().printStackTrace();
      return null;
    }
    catch (IllegalAccessException paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public void onCacheCompletion(IWonderCacheTask paramIWonderCacheTask, long paramLong1, long paramLong2, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      if (this.mListener != null)
      {
        Bundle localBundle = new Bundle();
        localBundle.putLong("loadedBytes", paramLong1);
        localBundle.putLong("totalBytes", paramLong2);
        localBundle.putString("taskFileName", paramIWonderCacheTask.getTaskFileName());
        invokeMethod("onCacheComplete", new Class[] { Bundle.class }, new Object[] { localBundle });
      }
      this.mTaskBroadcastStart = true;
      removeTask(false);
    }
  }
  
  public void onCacheError(IWonderCacheTask paramIWonderCacheTask, int paramInt, String paramString)
  {
    if (this.mListener != null)
    {
      paramIWonderCacheTask = new Bundle();
      paramIWonderCacheTask.putLong("errorCode", paramInt);
      paramIWonderCacheTask.putString("msg", paramString);
      invokeMethod("onCacheError", new Class[] { Bundle.class }, new Object[] { paramIWonderCacheTask });
    }
    this.mTaskBroadcastStart = true;
  }
  
  public void onCacheInfo(IWonderCacheTask paramIWonderCacheTask)
  {
    if ((this.mListener != null) && (!this.mTaskBroadcastStart))
    {
      Bundle localBundle = new Bundle();
      localBundle.putLong("contentLength", paramIWonderCacheTask.getContentLength());
      invokeMethod("onCacheStart", new Class[] { Bundle.class }, new Object[] { localBundle });
      this.mTaskBroadcastStart = true;
    }
  }
  
  public void onCacheProgress(IWonderCacheTask paramIWonderCacheTask, int paramInt1, long paramLong, int paramInt2)
  {
    if (this.mListener != null)
    {
      paramIWonderCacheTask = new Bundle();
      paramIWonderCacheTask.putInt("percent", paramInt1);
      paramIWonderCacheTask.putLong("downloadedSize", paramLong);
      paramIWonderCacheTask.putInt("bufferPercent", paramInt2);
      invokeMethod("onCacheProgress", new Class[] { Bundle.class }, new Object[] { paramIWonderCacheTask });
    }
  }
  
  public void onCacheStatusInfo(int paramInt, String paramString, Bundle paramBundle)
  {
    paramString = this.mListener;
  }
  
  public void onDataReceived(int paramInt)
  {
    Object localObject = this.mListener;
  }
  
  public void onWonderCacheTaskCreated(final IWonderCacheTask paramIWonderCacheTask)
  {
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public void run()
      {
        if (TbsVideoCacheTaskProxy.this.mDownloader == null)
        {
          localObject = paramIWonderCacheTask;
          if (localObject != null) {
            TbsVideoCacheTaskProxy.access$002(TbsVideoCacheTaskProxy.this, (IWonderCacheTask)localObject);
          }
        }
        if (TbsVideoCacheTaskProxy.this.mDownloader != null)
        {
          TbsVideoCacheTaskProxy.this.mDownloader.setFinalCacheFile(TbsVideoCacheTaskProxy.this.mTaskFileName);
          TbsVideoCacheTaskProxy.this.mDownloader.setFinalCacheFolder(TbsVideoCacheTaskProxy.this.mTaskFileDownloadFolder);
        }
        Object localObject = new Bundle();
        ((Bundle)localObject).putInt("errorCode", 0);
        ((Bundle)localObject).putString("msg", "cache task created!");
        TbsVideoCacheTaskProxy.this.invokeMethod("onCacheInit", new Class[] { Bundle.class }, new Object[] { localObject });
      }
    });
  }
  
  public void pauseTask()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("pauseTask mDownloader = ");
    if (this.mDownloader == null) {
      localObject = "null";
    } else {
      localObject = "not null";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.toString();
    Object localObject = this.mDownloader;
    if (localObject != null)
    {
      ((IWonderCacheTask)localObject).pause(false);
      this.mTaskBroadcastStart = true;
    }
  }
  
  public void removeTask(boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("removeTask mDownloader = ");
    if (this.mDownloader == null) {
      localObject = "null";
    } else {
      localObject = "not null";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.toString();
    Object localObject = this.mDownloader;
    if (localObject != null)
    {
      this.wcm.stopCacheTask((IWonderCacheTask)localObject, this);
      if (paramBoolean)
      {
        localObject = this.mDownloader.getTaskCacheDir();
        this.wcm.clearDir((File)localObject);
      }
    }
  }
  
  public void resumeTask()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("resumeTask mDownloader = ");
    if (this.mDownloader == null) {
      localObject = "null";
    } else {
      localObject = "not null";
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.toString();
    Object localObject = this.mDownloader;
    if (localObject != null) {
      ((IWonderCacheTask)localObject).resume(false);
    }
  }
  
  public void stopTask()
  {
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("stopTask mDownloader = ");
    if (this.mDownloader == null) {
      localObject1 = "null";
    } else {
      localObject1 = "not null";
    }
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("  wcm = ");
    if (this.wcm == null) {
      localObject1 = "null";
    } else {
      localObject1 = "not null";
    }
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).toString();
    Object localObject1 = this.mDownloader;
    if (localObject1 != null)
    {
      localObject2 = this.wcm;
      if (localObject2 != null)
      {
        ((IWonderCacheTaskMgr)localObject2).stopCacheTask((IWonderCacheTask)localObject1, this);
        this.mTaskBroadcastStart = true;
      }
    }
  }
  
  public boolean supportParallelDownload()
  {
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\cache\TbsVideoCacheTaskProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */