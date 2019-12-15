package com.tencent.smtt.util;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.webview.chromium.tencent.TencentContentSettingsAdapter;
import com.tencent.common.http.Apn;
import com.tencent.smtt.livelog.LiveLog;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.chromium.base.annotations.UsedByReflection;

public class X5LogManager
{
  private static final int MSG_PACKAGE_LOG_FILES = 1;
  private static final String TAG = "X5LogManager";
  private static final String X5_LOG_ZIP = "x5log.zip";
  private static Handler mEventHandler;
  private static HandlerThread mLogAffairThread;
  private static String openFlag;
  private static boolean openLogFlag = false;
  
  private static void deleteFilesList(List<String> paramList)
  {
    if (paramList != null)
    {
      if (paramList.size() == 0) {
        return;
      }
      int i = 0;
      while (i < paramList.size())
      {
        File localFile = new File((String)paramList.get(i));
        try
        {
          if (localFile.isFile()) {
            localFile.delete();
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        i += 1;
      }
      return;
    }
  }
  
  private static void handlerInit()
  {
    mLogAffairThread = new HandlerThread("x5log-handler");
    mLogAffairThread.start();
    mEventHandler = new Handler(mLogAffairThread.getLooper())
    {
      public void handleMessage(Message paramAnonymousMessage)
      {
        if (paramAnonymousMessage.what != 1) {
          return;
        }
        X5LogManager.access$000();
      }
    };
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static boolean isOpenX5log()
  {
    return openLogFlag;
  }
  
  private static void packageX5LogFiles()
  {
    Object localObject1 = LiveLog.getInstance().getLogDir();
    Object localObject2 = new File((String)localObject1);
    if (!((File)localObject2).exists())
    {
      SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_log_no_file_exist", "string"), 0);
      return;
    }
    Object localObject3 = ((File)localObject2).list();
    if ((localObject3 != null) && (localObject3.length != 0))
    {
      localObject2 = new ArrayList();
      int j = localObject3.length;
      int i = 0;
      Object localObject4;
      StringBuilder localStringBuilder1;
      while (i < j)
      {
        localObject4 = localObject3[i];
        if (((String)localObject4).endsWith(".x5log"))
        {
          localStringBuilder1 = new StringBuilder();
          localStringBuilder1.append(LiveLog.getInstance().getNamePrefix());
          localStringBuilder1.append("_");
          if (((String)localObject4).startsWith(localStringBuilder1.toString()))
          {
            localStringBuilder1 = new StringBuilder();
            localStringBuilder1.append((String)localObject1);
            localStringBuilder1.append("/");
            localStringBuilder1.append((String)localObject4);
            ((ArrayList)localObject2).add(localStringBuilder1.toString());
          }
        }
        i += 1;
      }
      localObject3 = MttLog.getLocalLogDir();
      if (localObject3 != null)
      {
        localObject3 = new File(((File)localObject3).getAbsolutePath());
        if (((File)localObject3).exists())
        {
          localObject4 = ((File)localObject3).list();
          if ((localObject4 != null) && (localObject4.length != 0))
          {
            j = localObject4.length;
            i = 0;
            while (i < j)
            {
              localStringBuilder1 = localObject4[i];
              StringBuilder localStringBuilder2 = new StringBuilder();
              localStringBuilder2.append(LiveLog.getInstance().getNamePrefix());
              localStringBuilder2.append("_");
              if (localStringBuilder1.startsWith(localStringBuilder2.toString()))
              {
                localStringBuilder2 = new StringBuilder();
                localStringBuilder2.append(localObject3);
                localStringBuilder2.append("/");
                localStringBuilder2.append(localStringBuilder1);
                ((ArrayList)localObject2).add(localStringBuilder2.toString());
              }
              i += 1;
            }
          }
        }
      }
      if (((ArrayList)localObject2).size() == 0)
      {
        SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_log_no_file_exist", "string"), 0);
        return;
      }
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append((String)localObject1);
      ((StringBuilder)localObject3).append("/");
      ((StringBuilder)localObject3).append("x5log.zip");
      localObject3 = ((StringBuilder)localObject3).toString();
      new MttTimingLog.c((String[])((ArrayList)localObject2).toArray(new String[((ArrayList)localObject2).size()]), (String)localObject3).a();
      localObject1 = openFlag;
      if (localObject1 == null) {
        localObject1 = "X5Log";
      }
      localObject1 = new X5LogUploadManager.b((String)localObject3, "X5Log", (String)localObject1, null);
      X5LogUploadManager.a().a((X5LogUploadManager.b)localObject1, new X5LogUploadManager.OnUploadListener()
      {
        public void onUploadFinished(boolean paramAnonymousBoolean)
        {
          String str;
          if (paramAnonymousBoolean) {
            str = SmttResource.getString("x5_log_upload_success", "string");
          } else {
            str = SmttResource.getString("x5_log_upload_failed", "string");
          }
          SmttServiceProxy.getInstance().showToast(str, 0);
        }
      });
      deleteFilesList((List)localObject2);
      return;
    }
    SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_log_no_file_exist", "string"), 0);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void setLogWrite2FileFlag(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      openLogFlag = true;
      o.a().a(true);
      if (!LiveLog.getInstance().getLiveLogStatus())
      {
        LiveLog.getInstance().resetInitFlag();
        LiveLog.getInstance().setForceInit();
        LiveLog.getInstance().init(ContextHolder.getInstance().getApplicationContext());
      }
      LiveLog.getInstance().openWriteAllLog();
      X5Log.setX5LogStatus(true);
      X5Log.d("X5Log", "******************* start x5log *******************");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("UserAgent is :[ ");
      localStringBuilder.append(TencentContentSettingsAdapter.getStaticUserAgent());
      localStringBuilder.append(" ] QUA is :[ ");
      localStringBuilder.append(SmttServiceProxy.getInstance().getQUA2());
      localStringBuilder.append("] GUID is :[");
      localStringBuilder.append(SmttServiceProxy.getInstance().getGUID());
      localStringBuilder.append("]");
      X5Log.d("X5Log", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("userNetworkSummary:");
      localStringBuilder.append(Apn.userNetworkSummary());
      X5Log.d("X5Log", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Proxy and Direct white list is :\r\n");
      localStringBuilder.append(SmttServiceProxy.getInstance().getWhiteListInfo());
      X5Log.d("X5Log", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("Proxy and Wup IP list is :\r\n");
      localStringBuilder.append(SmttServiceProxy.getInstance().getIPListForWupAndQProxy());
      X5Log.d("X5Log", localStringBuilder.toString());
      MttLog.openNetLogger();
      return;
    }
    X5Log.d("X5Log", "******************* end x5log *******************");
    openLogFlag = false;
    X5Log.setX5LogStatus(false);
    LiveLog.getInstance().closeWriteAllLog();
    MttLog.closeNetLogger();
    o.a().a(false);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void setLogWrite2FileFlag(boolean paramBoolean, String paramString)
  {
    openFlag = paramString;
    setLogWrite2FileFlag(paramBoolean);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void uploadX5LogFilesToServer()
  {
    if ((mLogAffairThread == null) || (mEventHandler == null)) {
      handlerInit();
    }
    Message localMessage = mEventHandler.obtainMessage(1);
    mEventHandler.sendMessage(localMessage);
  }
  
  @UsedByReflection("WebCoreProxy.java")
  public static void uploadX5LogFilesToServer(long paramLong)
  {
    if ((mLogAffairThread == null) || (mEventHandler == null)) {
      handlerInit();
    }
    Message localMessage = mEventHandler.obtainMessage(1);
    mEventHandler.sendMessageDelayed(localMessage, paramLong);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\X5LogManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */