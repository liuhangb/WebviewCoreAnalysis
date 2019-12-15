package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.os.RemoteException;
import com.tencent.common.plugin.IQBPluginSystemCallback;
import com.tencent.common.plugin.QBPluginItemInfo;
import com.tencent.common.plugin.QBPluginSystem;
import com.tencent.common.plugin.QBPluginSystem.ILoadPluginCallback;
import java.io.File;

public class ReaderPluginSoSession
{
  public static final String PACKAGE_NAME_CHM = "com.tencent.qb.plugin.chm";
  public static final String PACKAGE_NAME_DEFAULT = "com.tencent.qb.plugin.default";
  public static final String PACKAGE_NAME_DOC = "com.tencent.qb.plugin.doc";
  public static final String PACKAGE_NAME_DOCX = "com.tencent.qb.plugin.docx";
  public static final String PACKAGE_NAME_EPUB = "com.tencent.qb.plugin.epub";
  public static final String PACKAGE_NAME_MENU = "com.tencent.qb.plugin.menu";
  public static final String PACKAGE_NAME_PDF = "com.tencent.qb.plugin.pdf";
  public static final String PACKAGE_NAME_PPT = "com.tencent.qb.plugin.ppt";
  public static final String PACKAGE_NAME_PPTX = "com.tencent.qb.plugin.pptx";
  public static final String PACKAGE_NAME_TXT = "com.tencent.qb.plugin.txt";
  public static final String PACKAGE_NAME_XLS = "com.tencent.qb.plugin.xls";
  public static final String PACKAGE_NAME_XLSDOC = "com.tencent.qb.plugin.xlsdoc";
  public static final String PACKAGE_NAME_XLSX = "com.tencent.qb.plugin.xlsx";
  public static QBPluginSystem mPluginSystem;
  public static ReaderPluginSoSession sInstance;
  private Context jdField_a_of_type_AndroidContentContext = null;
  private boolean jdField_a_of_type_Boolean = true;
  
  public ReaderPluginSoSession()
  {
    mPluginSystem = QBPluginSystem.getInstance(this.jdField_a_of_type_AndroidContentContext);
  }
  
  public static ReaderPluginSoSession getInstance()
  {
    if (sInstance == null) {
      sInstance = new ReaderPluginSoSession();
    }
    return sInstance;
  }
  
  public void downloadPluginOrLoadLocal(int paramInt, IQBPluginSystemCallback paramIQBPluginSystemCallback, QBPluginSystem.ILoadPluginCallback paramILoadPluginCallback, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      mPluginSystem.LoadLocalPlugin(getPluginName(paramInt), paramIQBPluginSystemCallback, paramILoadPluginCallback, 2);
      return;
    }
    mPluginSystem.usePluginAsync(getPluginName(paramInt), 1, paramIQBPluginSystemCallback, paramILoadPluginCallback, null, 2);
  }
  
  public boolean getBackgroudDownload(int paramInt)
  {
    return true;
  }
  
  public String getCachePath(Context paramContext)
  {
    return paramContext.getDir("plugins_cache", 0).getAbsolutePath();
  }
  
  public String getPluginExt(int paramInt)
  {
    Object localObject;
    try
    {
      QBPluginItemInfo localQBPluginItemInfo = mPluginSystem.getPluginInfo(getPluginName(paramInt), 2);
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
      localObject = null;
    }
    if (localObject != null) {
      return ((QBPluginItemInfo)localObject).mExt;
    }
    return "";
  }
  
  public String getPluginName(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "com.tencent.qb.plugin.default";
    case 14: 
      return "com.tencent.qb.plugin.menu";
    case 13: 
      return "com.tencent.qb.plugin.txt";
    case 12: 
      return "com.tencent.qb.plugin.chm";
    case 11: 
      return "com.tencent.qb.plugin.epub";
    case 10: 
      return "com.tencent.qb.plugin.xlsdoc";
    case 9: 
      return "com.tencent.qb.plugin.doc";
    case 8: 
      return "com.tencent.qb.plugin.xls";
    case 7: 
      return "com.tencent.qb.plugin.xlsx";
    case 6: 
      return "com.tencent.qb.plugin.pptx";
    case 5: 
      return "com.tencent.qb.plugin.docx";
    case 4: 
      return "com.tencent.qb.plugin.ppt";
    }
    return "com.tencent.qb.plugin.pdf";
  }
  
  public String getPluginPath(int paramInt)
  {
    if (this.jdField_a_of_type_Boolean) {}
    try
    {
      Object localObject = mPluginSystem.getPluginInfo(getPluginName(paramInt), 2);
      if (localObject != null)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(((QBPluginItemInfo)localObject).mUnzipDir);
        localStringBuilder.append(File.separator);
        localObject = localStringBuilder.toString();
        return (String)localObject;
      }
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    return "";
  }
  
  public int getPluginSize(int paramInt)
  {
    try
    {
      QBPluginItemInfo localQBPluginItemInfo = mPluginSystem.getPluginInfo(getPluginName(paramInt), 2);
      if (localQBPluginItemInfo != null)
      {
        paramInt = Integer.parseInt(localQBPluginItemInfo.mPackageSize);
        return paramInt;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public String getPluginVersion(int paramInt)
  {
    try
    {
      Object localObject = mPluginSystem.getPluginInfo(getPluginName(paramInt), 2);
      if (localObject != null)
      {
        localObject = ((QBPluginItemInfo)localObject).mVersion;
        return (String)localObject;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return "";
  }
  
  public boolean isPluginInstalled(int paramInt)
  {
    try
    {
      QBPluginItemInfo localQBPluginItemInfo = mPluginSystem.getPluginInfo(getPluginName(paramInt), 2);
      if (localQBPluginItemInfo != null)
      {
        paramInt = localQBPluginItemInfo.mIsInstall;
        if (paramInt == 1) {
          return true;
        }
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public int isPluginNeedDownload(int paramInt)
  {
    return mPluginSystem.isPluginNeedDownload(getPluginName(paramInt), 1, 2);
  }
  
  public boolean prepareSoSessionIfNeed(int paramInt, IQBPluginSystemCallback paramIQBPluginSystemCallback, QBPluginSystem.ILoadPluginCallback paramILoadPluginCallback, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramILoadPluginCallback == null) {
      return false;
    }
    String str = getPluginName(paramInt);
    if ("com.tencent.qb.plugin.default".equals(str))
    {
      if (paramIQBPluginSystemCallback != null) {
        paramIQBPluginSystemCallback.onPrepareFinished(str, null, -1, 0);
      }
      return false;
    }
    if (paramBoolean2)
    {
      mPluginSystem.downloadPluginBackGround(str, 1, paramIQBPluginSystemCallback, 2);
      return true;
    }
    mPluginSystem.usePluginAsync(str, 1, paramIQBPluginSystemCallback, paramILoadPluginCallback, null, 2);
    return true;
  }
  
  public void stopDownloadPlugin(int paramInt, IQBPluginSystemCallback paramIQBPluginSystemCallback)
  {
    mPluginSystem.stopPluginTask(getPluginName(paramInt), 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\ReaderPluginSoSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */