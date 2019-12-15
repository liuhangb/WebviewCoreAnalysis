package com.tencent.mtt.browser.download.engine;

import android.content.Context;
import android.drm.DrmManagerClient;
import java.io.File;

public class DownloadDrmHelper
{
  public static final String EXTENSION_DRM_MESSAGE = ".dm";
  public static final String EXTENSION_INTERNAL_FWDL = ".fl";
  public static final String MIMETYPE_DRM_MESSAGE = "application/vnd.oma.drm.message";
  
  public static String getOriginalMimeType(Context paramContext, File paramFile, String paramString)
  {
    paramContext = new DrmManagerClient(paramContext);
    try
    {
      paramFile = paramFile.toString();
      if (paramContext.canHandle(paramFile, null))
      {
        paramFile = paramContext.getOriginalMimeType(paramFile);
        return paramFile;
      }
      return paramString;
    }
    finally
    {
      paramContext.release();
    }
  }
  
  public static boolean isDrmConvertNeeded(String paramString)
  {
    return "application/vnd.oma.drm.message".equals(paramString);
  }
  
  public static String modifyDrmFwLockFileExtension(String paramString)
  {
    String str = paramString;
    if (paramString != null)
    {
      int i = paramString.lastIndexOf(".");
      str = paramString;
      if (i != -1) {
        str = paramString.substring(0, i);
      }
      str = str.concat(".fl");
    }
    return str;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadDrmHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */