package com.tencent.smtt.downloader.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Environment;
import android.os.StatFs;
import com.tencent.smtt.downloader.a;
import java.io.File;

public class j
{
  private static File a;
  
  public static long a()
  {
    StatFs localStatFs = new StatFs(Environment.getDataDirectory().getPath());
    return localStatFs.getBlockSize() * localStatFs.getAvailableBlocks();
  }
  
  @TargetApi(9)
  public static boolean a(Context paramContext)
  {
    if (paramContext == null) {
      return false;
    }
    if (a == null) {
      try
      {
        if (!paramContext.getApplicationInfo().processName.contains("com.tencent.mm")) {
          return false;
        }
        paramContext = a.a(paramContext);
        if (paramContext != null)
        {
          if (!paramContext.isDirectory()) {
            return false;
          }
          paramContext = new File(paramContext, "share");
          if ((!paramContext.isDirectory()) && (!paramContext.mkdir())) {
            return false;
          }
          a = paramContext;
          paramContext.setExecutable(true, false);
          return true;
        }
        return false;
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
        return false;
      }
    }
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\downloader\utils\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */