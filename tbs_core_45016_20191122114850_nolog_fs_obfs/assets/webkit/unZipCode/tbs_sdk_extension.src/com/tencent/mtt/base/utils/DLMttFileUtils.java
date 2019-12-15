package com.tencent.mtt.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.QSize;
import com.tencent.common.utils.StringUtils;
import java.io.File;

public class DLMttFileUtils
{
  public static final int DOWNLOAD_SETTING_EXTERNAL_DATA = 2;
  public static final int DOWNLOAD_SETTING_EXTERNAL_QQBROWSER = 1;
  public static final int DOWNLOAD_SETTING_INTERNAL_SDCARD = 0;
  public static int sDownloadSdcardValue;
  
  public static boolean deleteDownloadFile(String paramString1, String paramString2)
  {
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(".");
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append(".dltmp");
      localObject = new File(paramString1, ((StringBuilder)localObject).toString());
      if (((File)localObject).exists()) {
        FileUtilsF.deleteFileOnThread((File)localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(".");
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append(".qbdltmp");
      localObject = new File(paramString1, ((StringBuilder)localObject).toString());
      if (((File)localObject).exists()) {
        FileUtilsF.deleteFileOnThread((File)localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append(".qbdltmp");
      localObject = new File(paramString1, ((StringBuilder)localObject).toString());
      if (((File)localObject).exists()) {
        FileUtilsF.deleteFileOnThread((File)localObject);
      }
      localObject = new File(paramString1, paramString2);
      if (((File)localObject).exists()) {
        FileUtilsF.deleteFileOnThread((File)localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(".");
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append(".png.icon");
      paramString1 = new File(paramString1, ((StringBuilder)localObject).toString());
      if (paramString1.exists()) {
        FileUtilsF.deleteFileOnThread(paramString1);
      }
      return true;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return false;
  }
  
  public static void deleteDownloadFileInThread(String paramString1, final String paramString2)
  {
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        DLMttFileUtils.deleteDownloadFile(this.a, paramString2);
      }
    });
  }
  
  public static boolean deleteDownloadTempFile(String paramString1, String paramString2)
  {
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(".");
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append(".dltmp");
      localObject = new File(paramString1, ((StringBuilder)localObject).toString());
      if (((File)localObject).exists()) {
        FileUtilsF.deleteFileOnThread((File)localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(".");
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append(".qbdltmp");
      localObject = new File(paramString1, ((StringBuilder)localObject).toString());
      if (((File)localObject).exists()) {
        FileUtilsF.deleteFileOnThread((File)localObject);
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).append(".qbdltmp");
      paramString1 = new File(paramString1, ((StringBuilder)localObject).toString());
      if (paramString1.exists()) {
        FileUtilsF.deleteFileOnThread(paramString1);
      }
      return true;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return false;
  }
  
  public static void deleteDownloadTypeIconFile(String paramString1, String paramString2)
  {
    if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)))
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append(".");
      ((StringBuilder)localObject).append(paramString1);
      ((StringBuilder)localObject).append(".png");
      localObject = new File(paramString2, ((StringBuilder)localObject).toString());
      if (((File)localObject).exists()) {
        ((File)localObject).delete();
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(".");
      ((StringBuilder)localObject).append(paramString1);
      ((StringBuilder)localObject).append(".png.tmp");
      paramString1 = new File(paramString2, ((StringBuilder)localObject).toString());
      if (paramString1.exists()) {
        paramString1.delete();
      }
    }
  }
  
  public static String getDownloadFilePath(Context paramContext, String paramString)
  {
    paramString = DLMediaFileType.getDownloadDir(getFileType(paramString));
    if (TextUtils.isEmpty(paramString)) {
      return FileUtilsF.createDir(getQQBrowserDownloadDir(paramContext), "其他").getAbsolutePath();
    }
    return FileUtilsF.createDir(getQQBrowserDownloadDir(paramContext), paramString).getAbsolutePath();
  }
  
  public static QSize getDownloadFileTypeIconSize(Context paramContext)
  {
    if (paramContext != null) {
      return new QSize((int)(paramContext.getResources().getDisplayMetrics().density * 40.0F + 0.5F), (int)(paramContext.getResources().getDisplayMetrics().density * 40.0F + 0.5F));
    }
    return new QSize(0, 0);
  }
  
  public static long getDownloadSdcardFreeSpace(Context paramContext, String paramString)
  {
    String str = DLSdcardUtils.getSDcardDir(paramContext, paramString);
    paramString = str;
    if (str == null)
    {
      File localFile = getQQBrowserDownloadDir(paramContext);
      paramString = str;
      if (localFile != null) {
        paramString = DLSdcardUtils.getSDcardDir(paramContext, localFile.getAbsolutePath());
      }
    }
    paramContext = paramString;
    if (paramString == null) {
      paramContext = FileUtilsF.getSDcardDir().getAbsolutePath();
    }
    return FileUtilsF.getSdcardFreeSpace(paramContext);
  }
  
  public static Bitmap getDownloadTypeIcon(String paramString1, String paramString2)
    throws OutOfMemoryError
  {
    Object localObject2 = null;
    Object localObject1 = localObject2;
    try
    {
      if (!TextUtils.isEmpty(paramString1))
      {
        localObject1 = localObject2;
        if (!TextUtils.isEmpty(paramString2))
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(".");
          ((StringBuilder)localObject1).append(paramString1);
          ((StringBuilder)localObject1).append(".png.icon");
          localObject1 = FileUtilsF.getImage(new File(paramString2, ((StringBuilder)localObject1).toString()));
          if (localObject1 == null)
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append(".");
            ((StringBuilder)localObject1).append(paramString1);
            ((StringBuilder)localObject1).append(".png");
            paramString1 = FileUtilsF.getImage(new File(paramString2, ((StringBuilder)localObject1).toString()));
            return paramString1;
          }
        }
      }
      return (Bitmap)localObject1;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
      return null;
    }
    catch (OutOfMemoryError paramString1) {}
    return null;
  }
  
  public static String getFileExt(String paramString)
  {
    if (paramString != null)
    {
      if (paramString.length() == 0) {
        return null;
      }
      int i = paramString.lastIndexOf('.');
      if ((i > -1) && (i < paramString.length() - 1))
      {
        String str = paramString.substring(i + 1);
        paramString = str;
        if (str.indexOf(File.separatorChar) > -1) {
          return null;
        }
      }
      else
      {
        paramString = null;
      }
      return paramString;
    }
    return null;
  }
  
  public static DLMediaFileType.FileExtType getFileExtType(String paramString)
  {
    paramString = getFileExt(paramString);
    if (paramString != null) {
      return DLMediaFileType.getFileTypeFromExt(paramString.toLowerCase());
    }
    return DLMediaFileType.FileExtType.FILE_EXT_UNKNOWN;
  }
  
  public static byte getFileType(String paramString)
  {
    return getFileExtType(paramString).fileType;
  }
  
  public static File getQQBrowserDownloadDir(Context paramContext)
  {
    if (DLSdcardUtils.hasTwoOrMoreSdcards(paramContext))
    {
      if (sDownloadSdcardValue == 0) {
        paramContext = DLSdcardUtils.getInternalAvailableQQBrowserDir(paramContext);
      } else {
        paramContext = DLSdcardUtils.getExternalAvailableQQBrowserDir(paramContext);
      }
    }
    else {
      paramContext = DLSdcardUtils.getAvailableQQBrowserDir(paramContext);
    }
    if (paramContext != null) {
      return paramContext;
    }
    return FileUtilsF.getSDcardDir();
  }
  
  public static boolean isSameFileName(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1)) {
      if (TextUtils.isEmpty(paramString2)) {
        return false;
      }
    }
    for (;;)
    {
      try
      {
        int i = paramString1.lastIndexOf('.');
        if (i <= -1) {
          break label177;
        }
        localObject1 = paramString1.substring(0, i);
        localObject2 = paramString1.substring(i);
        paramString1 = (String)localObject1;
        localObject1 = localObject2;
        i = paramString2.lastIndexOf('.');
        if (i <= -1) {
          break label184;
        }
        localObject2 = paramString2.substring(0, i);
        String str = paramString2.substring(i);
        paramString2 = (String)localObject2;
        localObject2 = str;
        if ((((String)localObject1).equalsIgnoreCase((String)localObject2)) && (paramString1.startsWith(paramString2)))
        {
          paramString1 = paramString1.substring(paramString2.length());
          if ((paramString1 != null) && (paramString1.length() > 2) && (paramString1.startsWith("(")) && (paramString1.endsWith(")")))
          {
            boolean bool = StringUtils.isNumeric(paramString1.substring(1, paramString1.length() - 1));
            if (bool) {
              return true;
            }
          }
        }
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
      }
      return false;
      return false;
      label177:
      Object localObject1 = "";
      continue;
      label184:
      Object localObject2 = "";
    }
  }
  
  public static boolean isValidExtensionFileName(String paramString)
  {
    int i = getFileType(paramString);
    return (i != 0) && (i != 9);
  }
  
  public static void saveDownloadFileTypeIcon(String paramString1, String paramString2, Bitmap paramBitmap)
  {
    if ((!TextUtils.isEmpty(paramString1)) && (paramBitmap != null))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(".");
      localStringBuilder.append(paramString1);
      localStringBuilder.append(".png.icon");
      FileUtilsF.saveImage(new File(paramString2, localStringBuilder.toString()), paramBitmap);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLMttFileUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */