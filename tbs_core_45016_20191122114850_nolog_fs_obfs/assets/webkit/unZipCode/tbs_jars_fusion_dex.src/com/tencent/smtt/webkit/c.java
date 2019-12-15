package com.tencent.smtt.webkit;

import com.tencent.smtt.util.MttLog;
import java.io.File;
import java.io.IOException;
import org.chromium.android_webview.AwDebug;
import org.chromium.base.PathUtils;

public class c
{
  public static boolean a(String paramString)
  {
    if (paramString.startsWith("http://debug.core/dump-thread"))
    {
      paramString = new File(PathUtils.getExternalStorageDirectory(), "dump.dmp");
      if (!paramString.exists()) {
        try
        {
          paramString.createNewFile();
        }
        catch (IOException localIOException)
        {
          localIOException.printStackTrace();
        }
      }
      AwDebug.dumpWithoutCrashing(paramString);
      return true;
    }
    return false;
  }
  
  public static boolean b(String paramString)
  {
    String str = MttLog.getLocalDumpDirTbs();
    if (str == null) {
      return false;
    }
    paramString = new File(str, paramString);
    if (!paramString.exists()) {
      try
      {
        paramString.createNewFile();
      }
      catch (IOException localIOException)
      {
        localIOException.printStackTrace();
      }
    }
    AwDebug.dumpWithoutCrashing(paramString);
    return true;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */