package com.tencent.common.plugin;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.LinuxToolsJni;
import com.tencent.common.utils.StorageDirs;
import com.tencent.common.utils.TbsMode;
import java.io.File;

public class PluginFileUtils
{
  public static final String DIR_PLUGINS = "plugins";
  
  public static File createDir(File paramFile, String paramString1, String paramString2)
  {
    if ((paramFile != null) && (!TextUtils.isEmpty(paramString1)))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return null;
      }
      paramFile = new File(paramFile, paramString1);
      if (!paramFile.exists())
      {
        paramFile.mkdirs();
        if (!paramFile.isDirectory()) {
          return null;
        }
        if (!TbsMode.TBSISQB())
        {
          paramString1 = new LinuxToolsJni();
          if (LinuxToolsJni.gJniloaded)
          {
            paramString1.Chmod(paramFile.getAbsolutePath(), paramString2);
            return paramFile;
          }
          return null;
        }
      }
      return paramFile;
    }
    return null;
  }
  
  public static File getPluginDir(Context paramContext)
  {
    paramContext = FileUtilsF.createDir(FileUtilsF.getFilesDir(paramContext), "plugins");
    if ((paramContext != null) && (LinuxToolsJni.gJniloaded) && (new LinuxToolsJni().Chmod(paramContext.getAbsolutePath(), QBPluginServiceImpl.mFileMode) != 0))
    {
      paramContext.delete();
      return null;
    }
    return paramContext;
  }
  
  @NonNull
  public static File getSDCardPluginDir()
  {
    File localFile = StorageDirs.getExternalFilesDir("plugins");
    if (localFile != null) {
      return localFile;
    }
    localFile = FileUtilsF.createDir(StorageDirs.getMainDownloadDir(2), "plugins");
    if ((localFile != null) && (localFile.exists())) {
      return localFile;
    }
    return StorageDirs.getInternalFilesDir("plugins");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\plugin\PluginFileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */