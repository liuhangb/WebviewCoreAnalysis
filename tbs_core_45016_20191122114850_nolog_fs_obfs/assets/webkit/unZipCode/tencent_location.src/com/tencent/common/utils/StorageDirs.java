package com.tencent.common.utils;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.mtt.ContextHolder;
import java.io.File;

public class StorageDirs
{
  public static final int EXTERNAL_STORAGE = 2;
  public static final int INTERNAL_STORAGE = 1;
  public static final int READ_ONLY = 1;
  public static final int READ_WRITE = 2;
  
  @NonNull
  public static File getAppFileDir(String paramString)
  {
    return ContextHolder.getAppContext().getDir(paramString, 0);
  }
  
  @NonNull
  public static File getCacheDir()
  {
    Object localObject2 = getCacheDir(2);
    Object localObject1;
    if (localObject2 != null)
    {
      localObject1 = localObject2;
      if (((File)localObject2).exists()) {}
    }
    else
    {
      localObject1 = getCacheDir(1);
    }
    if (localObject1 != null)
    {
      localObject2 = localObject1;
      if (((File)localObject1).exists()) {}
    }
    else
    {
      localObject2 = getInternalFilesDir("cache");
    }
    return (File)localObject2;
  }
  
  @Nullable
  public static File getCacheDir(int paramInt)
  {
    if (paramInt == 1) {
      return FileUtilsF.getCacheDir(ContextHolder.getAppContext());
    }
    try
    {
      File localFile = ContextHolder.getAppContext().getExternalCacheDir();
      return localFile;
    }
    catch (Exception localException)
    {
      FLogger.e("StorageDirs", localException);
    }
    return null;
  }
  
  public static File[] getCacheDirs()
  {
    return new File[] { getCacheDir(2), getCacheDir(1), getInternalFilesDir("cache") };
  }
  
  @Nullable
  public static File getExternalFilesDir(String paramString)
  {
    try
    {
      paramString = ContextHolder.getAppContext().getExternalFilesDir(paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      FLogger.e("StorageDirs", paramString);
    }
    return null;
  }
  
  @NonNull
  public static File getInternalFilesDir(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return ContextHolder.getAppContext().getFilesDir();
    }
    return FileUtilsF.createDir(ContextHolder.getAppContext().getFilesDir(), paramString);
  }
  
  @Nullable
  public static File getMainDownloadDir(int paramInt)
  {
    if (paramInt == 2) {
      return ExternalDataDir.getDefault().getDataDir();
    }
    return new File(getSdcardDir(paramInt), ExternalDataDir.DEFAULT_DIR_EXT_MAIN);
  }
  
  @Nullable
  public static File getSdcardDir(int paramInt)
  {
    if (paramInt == 2) {
      return FileUtilsF.getSDcardDir(ContextHolder.getAppContext());
    }
    try
    {
      File localFile1 = Environment.getExternalStorageDirectory();
      if (localFile1 != null)
      {
        boolean bool = localFile1.exists();
        if (bool) {
          return localFile1;
        }
      }
    }
    catch (Exception localException)
    {
      FLogger.e("StorageDirs", localException);
      String[] arrayOfString = FileUtilsF.DEFAULT_SDCARD_CONVENTIONS;
      int i = arrayOfString.length;
      paramInt = 0;
      while (paramInt < i)
      {
        File localFile2 = new File(arrayOfString[paramInt]);
        if (localFile2.exists()) {
          return localFile2;
        }
        paramInt += 1;
      }
    }
    return FileUtilsF.getSDcardDir();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\StorageDirs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */