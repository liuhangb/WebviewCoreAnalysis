package com.tencent.smtt.downloader;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.os.Process;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import java.io.File;
import java.util.Iterator;
import java.util.List;

public class a
{
  public static TbsListener a = new TbsListener()
  {
    public void onDownloadFinish(int paramAnonymousInt) {}
    
    public void onDownloadProgress(int paramAnonymousInt) {}
    
    public void onInstallFinish(int paramAnonymousInt)
    {
      if (paramAnonymousInt == 200) {
        X5CoreBeaconUploader.getInstance(TbsBaseModuleShell.getContext()).userBehaviorStatistics("CD00001");
      }
    }
  };
  
  public static File a(Context paramContext)
  {
    return paramContext.getDir("tbs_64", 0);
  }
  
  public static String a(Context paramContext)
  {
    try
    {
      int i = Process.myPid();
      Object localObject = "";
      Iterator localIterator = ((ActivityManager)paramContext.getApplicationContext().getSystemService("activity")).getRunningAppProcesses().iterator();
      paramContext = (Context)localObject;
      while (localIterator.hasNext())
      {
        localObject = (ActivityManager.RunningAppProcessInfo)localIterator.next();
        if (((ActivityManager.RunningAppProcessInfo)localObject).pid == i) {
          paramContext = ((ActivityManager.RunningAppProcessInfo)localObject).processName;
        }
      }
      return paramContext;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    return "";
  }
  
  public static void a(boolean paramBoolean) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */