package com.tencent.mtt.external.reader.utils;

import android.content.Context;
import android.text.TextUtils;
import com.tencent.common.utils.FileUtils;
import java.io.File;
import java.io.IOException;

public class FileOpenUtils
{
  private static final String[] a = { "txt", "epub", "pdf", "doc", "docx", "ppt", "pptx", "xlsx", "xls", "chm" };
  private static final String[] b = { "ini", "log", "bat", "php", "js", "lrc" };
  
  public static void copyAssetsFileToEx(Context paramContext, String paramString, File paramFile, boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean) {
      FileUtils.deleteQuietly(paramFile);
    }
    FileUtils.copyAssetsFileTo(paramContext, paramString, paramFile);
  }
  
  public static File getPluginCache(Context paramContext)
  {
    return paramContext.getDir("plugins_cache", 0);
  }
  
  public static boolean isBasicReaderType(String paramString)
  {
    return (!TextUtils.isEmpty(paramString)) && ((paramString.toLowerCase().equals("txt")) || (isTxtOpenableFile(paramString)));
  }
  
  public static boolean isReaderFileExt(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      paramString = paramString.toLowerCase();
      String[] arrayOfString = a;
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        if (paramString.equals(arrayOfString[i])) {
          return true;
        }
        i += 1;
      }
    }
    return false;
  }
  
  public static boolean isTxtOpenableFile(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      paramString = paramString.toLowerCase();
      String[] arrayOfString = b;
      int j = arrayOfString.length;
      int i = 0;
      while (i < j)
      {
        if (paramString.equals(arrayOfString[i])) {
          return true;
        }
        i += 1;
      }
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\utils\FileOpenUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */