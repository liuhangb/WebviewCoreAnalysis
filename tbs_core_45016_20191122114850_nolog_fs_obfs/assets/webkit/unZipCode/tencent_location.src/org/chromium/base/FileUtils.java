package org.chromium.base;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

public class FileUtils
{
  public static void batchDeleteFiles(List<File> paramList)
  {
    ThreadUtils.assertOnBackgroundThread();
    paramList = paramList.iterator();
    while (paramList.hasNext())
    {
      File localFile = (File)paramList.next();
      if (localFile.exists()) {
        recursivelyDeleteFile(localFile);
      }
    }
  }
  
  public static boolean extractAsset(Context paramContext, String paramString, File paramFile)
  {
    Object localObject = null;
    for (;;)
    {
      try
      {
        paramString = paramContext.getAssets().open(paramString);
      }
      catch (IOException paramContext)
      {
        try
        {
          int i;
          paramContext.close();
          return false;
        }
        catch (IOException paramContext) {}
        paramContext = paramContext;
        continue;
      }
      try
      {
        paramContext = new BufferedOutputStream(new FileOutputStream(paramFile));
      }
      catch (IOException paramContext)
      {
        continue;
      }
      try
      {
        paramFile = new byte[' '];
        i = paramString.read(paramFile);
        if (i != -1)
        {
          paramContext.write(paramFile, 0, i);
        }
        else
        {
          paramString.close();
          paramContext.close();
          return true;
        }
      }
      catch (IOException paramFile) {}
    }
    break label79;
    paramContext = (Context)localObject;
    break label79;
    paramString = null;
    paramContext = (Context)localObject;
    label79:
    if (paramString != null) {}
    try
    {
      paramString.close();
    }
    catch (IOException paramString)
    {
      for (;;) {}
    }
    if (paramContext != null) {}
    return false;
  }
  
  public static Uri getUriForFile(File paramFile)
  {
    Object localObject1;
    try
    {
      Uri localUri = ContentUriUtils.getContentUriFromFile(paramFile);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("Could not create content uri: ");
      ((StringBuilder)localObject2).append(localIllegalArgumentException);
      Log.e("FileUtils", ((StringBuilder)localObject2).toString(), new Object[0]);
      localObject1 = null;
    }
    Object localObject2 = localObject1;
    if (localObject1 == null) {
      localObject2 = Uri.fromFile(paramFile);
    }
    return (Uri)localObject2;
  }
  
  public static void recursivelyDeleteFile(File paramFile)
  {
    
    Object localObject;
    if (paramFile.isDirectory())
    {
      localObject = paramFile.listFiles();
      if (localObject != null)
      {
        int j = localObject.length;
        int i = 0;
        while (i < j)
        {
          recursivelyDeleteFile(localObject[i]);
          i += 1;
        }
      }
    }
    if (!paramFile.delete())
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("Failed to delete: ");
      ((StringBuilder)localObject).append(paramFile);
      Log.e("FileUtils", ((StringBuilder)localObject).toString(), new Object[0]);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\FileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */