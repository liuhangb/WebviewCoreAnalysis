package com.tencent.tbs.common.download.qb;

import android.content.Context;
import java.io.File;

public class QbDownloaderAdvanced
{
  private File apkFile = null;
  private String backupQbApkPath = "";
  
  private boolean fileExist(File paramFile)
  {
    return (paramFile != null) && (paramFile.exists());
  }
  
  public void startDownload() {}
  
  static class QbCacheFileProcesser
    implements IFileProcesser
  {
    public IFileProcesser next()
    {
      return null;
    }
    
    public File process(Context paramContext, File paramFile, String paramString)
    {
      return null;
    }
  }
  
  static class QbInstallerProcesser
    implements IFileProcesser
  {
    public IFileProcesser next()
    {
      return null;
    }
    
    public File process(Context paramContext, File paramFile, String paramString)
    {
      return null;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\qb\QbDownloaderAdvanced.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */