package com.tencent.smtt.processutil;

import android.util.Log;
import com.tencent.smtt.processutil.models.AndroidAppProcess;
import com.tencent.smtt.processutil.models.AndroidAppProcess.NotAndroidAppProcessException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class a
{
  private static boolean a = false;
  
  public static List<AndroidAppProcess> a()
  {
    ArrayList localArrayList = new ArrayList();
    File[] arrayOfFile = new File("/proc").listFiles();
    int j = arrayOfFile.length;
    int i = 0;
    while (i < j)
    {
      File localFile = arrayOfFile[i];
      if (localFile.isDirectory()) {}
      try
      {
        int k = Integer.parseInt(localFile.getName());
        try
        {
          localArrayList.add(new AndroidAppProcess(k));
        }
        catch (IOException localIOException)
        {
          a(localIOException, "Error reading from /proc/%d.", new Object[] { Integer.valueOf(k) });
        }
      }
      catch (NumberFormatException|AndroidAppProcess.NotAndroidAppProcessException localNumberFormatException)
      {
        for (;;) {}
      }
      i += 1;
    }
    return localArrayList;
  }
  
  public static void a(String paramString, Object... paramVarArgs)
  {
    if (a)
    {
      if (paramVarArgs.length != 0) {
        paramString = String.format(paramString, paramVarArgs);
      }
      Log.d("AndroidProcessHelper", paramString);
    }
  }
  
  public static void a(Throwable paramThrowable, String paramString, Object... paramVarArgs)
  {
    if (a)
    {
      if (paramVarArgs.length != 0) {
        paramString = String.format(paramString, paramVarArgs);
      }
      Log.d("AndroidProcessHelper", paramString, paramThrowable);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\processutil\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */