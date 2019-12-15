package com.tencent.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build.VERSION;
import android.os.Environment;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.basesupport.PackageInfo;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.internal.service.StatServerHolder;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ExternalDataDir
{
  public static final byte DATADIR_POSITION_DATA = 2;
  public static final byte DATADIR_POSITION_NONE = 0;
  public static final byte DATADIR_POSITION_ROOT = 1;
  public static final String DEFAULT_DIR_EXT_MAIN = ;
  public static final int TYPE_DATA = 2;
  public static final int TYPE_DEFAUT = 0;
  public static final int TYPE_SDCARD = 1;
  private static ExternalDataDir jdField_a_of_type_ComTencentCommonUtilsExternalDataDir = null;
  private static final HashMap<String, ExternalDataDir> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  byte jdField_a_of_type_Byte = 0;
  private BroadcastReceiver jdField_a_of_type_AndroidContentBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent == null) {
        return;
      }
      paramAnonymousContext = paramAnonymousIntent.getAction();
      if (("android.intent.action.MEDIA_UNMOUNTED".equalsIgnoreCase(paramAnonymousContext)) || ("android.intent.action.MEDIA_MOUNTED".equalsIgnoreCase(paramAnonymousContext)) || ("android.intent.action.MEDIA_EJECT".equalsIgnoreCase(paramAnonymousContext)) || ("android.intent.action.MEDIA_REMOVED".equalsIgnoreCase(paramAnonymousContext)) || ("android.intent.action.MEDIA_BAD_REMOVAL".equalsIgnoreCase(paramAnonymousContext)))
      {
        FLogger.d("ExternalDataDir", "sdcardChanged");
        paramAnonymousContext = ExternalDataDir.this;
        paramAnonymousContext.jdField_a_of_type_Boolean = false;
        paramAnonymousContext.jdField_b_of_type_Boolean = false;
        paramAnonymousContext.jdField_a_of_type_JavaIoFile = null;
        paramAnonymousContext.jdField_b_of_type_JavaIoFile = null;
        paramAnonymousContext.c = null;
        paramAnonymousContext.jdField_a_of_type_Byte = 0;
      }
    }
  };
  File jdField_a_of_type_JavaIoFile = null;
  private Object jdField_a_of_type_JavaLangObject = new Object();
  final String jdField_a_of_type_JavaLangString;
  boolean jdField_a_of_type_Boolean = false;
  File jdField_b_of_type_JavaIoFile = null;
  boolean jdField_b_of_type_Boolean = false;
  File c = null;
  
  public ExternalDataDir(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    a();
  }
  
  private void a()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (!this.jdField_b_of_type_Boolean)
      {
        FLogger.d("ExternalDataDir", "initDataDir called here", new Throwable("initDataDir called here"));
        Object localObject1 = SdCardInfo.Utils.getSDcardInfo(ContextHolder.getAppContext());
        int i;
        Object localObject6;
        if ((this.jdField_b_of_type_JavaIoFile == null) && (((SdCardInfo)localObject1).hasInternalSD()))
        {
          Object localObject5 = new File(((SdCardInfo)localObject1).internalStorage.path, this.jdField_a_of_type_JavaLangString);
          i = Integer.parseInt(Build.VERSION.SDK);
          if (FileUtilsF.isFolderWritable((File)localObject5)) {
            this.jdField_b_of_type_JavaIoFile = ((File)localObject5);
          } else if (i >= 19) {
            try
            {
              localObject5 = ContextHolder.getAppContext().getExternalFilesDir(null);
              if ((localObject5 != null) && (((File)localObject5).exists()))
              {
                localObject5 = ((File)localObject5).getAbsolutePath();
                if (((String)localObject5).startsWith(((SdCardInfo)localObject1).internalStorage.path))
                {
                  localObject6 = new StringBuilder();
                  ((StringBuilder)localObject6).append((String)localObject5);
                  ((StringBuilder)localObject6).append(File.separator);
                  ((StringBuilder)localObject6).append(this.jdField_a_of_type_JavaLangString);
                  localObject5 = new File(((StringBuilder)localObject6).toString());
                  if (FileUtilsF.isFolderWritable((File)localObject5)) {
                    this.jdField_b_of_type_JavaIoFile = ((File)localObject5);
                  }
                }
              }
            }
            catch (Throwable localThrowable2)
            {
              localThrowable2.printStackTrace();
            }
          }
        }
        Iterator localIterator = ((SdCardInfo)localObject1).extStorage.iterator();
        while (localIterator.hasNext())
        {
          localObject6 = (StorageInfo)localIterator.next();
          localObject1 = new File(((StorageInfo)localObject6).path, this.jdField_a_of_type_JavaLangString);
          i = Integer.parseInt(Build.VERSION.SDK);
          if (((StorageInfo)localObject6).isWritable)
          {
            this.c = ((File)localObject1);
            this.jdField_a_of_type_Byte = 1;
            break;
          }
          if (i >= 19)
          {
            try
            {
              localObject1 = ContextHolder.getAppContext().getExternalFilesDir(null);
              if (localObject1 != null) {
                localObject1 = ((File)localObject1).getAbsolutePath();
              } else {
                localObject1 = null;
              }
            }
            catch (Throwable localThrowable1)
            {
              localThrowable1.printStackTrace();
              localObject2 = null;
            }
            Object localObject7 = new StringBuilder();
            ((StringBuilder)localObject7).append(((StorageInfo)localObject6).path);
            ((StringBuilder)localObject7).append(File.separator);
            ((StringBuilder)localObject7).append("Android");
            ((StringBuilder)localObject7).append(File.separator);
            ((StringBuilder)localObject7).append("data");
            ((StringBuilder)localObject7).append(File.separator);
            ((StringBuilder)localObject7).append(ContextHolder.getAppContext().getPackageName());
            ((StringBuilder)localObject7).append(File.separator);
            ((StringBuilder)localObject7).append("files");
            ((StringBuilder)localObject7).append(File.separator);
            ((StringBuilder)localObject7).append(this.jdField_a_of_type_JavaLangString);
            localObject6 = ((StringBuilder)localObject7).toString();
            localObject7 = new File((String)localObject6);
            if (FileUtilsF.isFolderWritable((File)localObject7))
            {
              this.c = ((File)localObject7);
              this.jdField_a_of_type_Byte = 2;
              break;
            }
            if (!TextUtils.isEmpty((CharSequence)localObject2))
            {
              localObject7 = new StringBuilder();
              ((StringBuilder)localObject7).append("AHNG708_");
              ((StringBuilder)localObject7).append((String)localObject2);
              ((StringBuilder)localObject7).append("_");
              ((StringBuilder)localObject7).append((String)localObject6);
              StatServerHolder.userBehaviorStatistics(((StringBuilder)localObject7).toString());
              localObject7 = new StringBuilder();
              ((StringBuilder)localObject7).append((String)localObject2);
              ((StringBuilder)localObject7).append(";");
              ((StringBuilder)localObject7).append((String)localObject6);
              FLogger.d("SDCardInfo", ((StringBuilder)localObject7).toString());
            }
          }
        }
        if (FileUtilsF.hasSDcard())
        {
          localObject2 = FileUtilsF.getSDcardDir();
          if ((localObject2 != null) && (((File)localObject2).exists()))
          {
            localObject2 = FileUtilsF.createDir((File)localObject2, this.jdField_a_of_type_JavaLangString);
            if (localObject2 != null) {
              if (((File)localObject2).equals(this.jdField_b_of_type_JavaIoFile)) {
                this.jdField_a_of_type_JavaIoFile = this.jdField_b_of_type_JavaIoFile;
              } else if (((File)localObject2).equals(this.c)) {
                this.jdField_a_of_type_JavaIoFile = this.c;
              } else if (FileUtilsF.isFolderWritable((File)localObject2)) {
                this.jdField_a_of_type_JavaIoFile = ((File)localObject2);
              }
            }
          }
        }
        if (this.jdField_a_of_type_JavaIoFile == null)
        {
          if (this.jdField_b_of_type_JavaIoFile != null) {
            localObject2 = this.jdField_b_of_type_JavaIoFile;
          } else {
            localObject2 = this.c;
          }
          this.jdField_a_of_type_JavaIoFile = ((File)localObject2);
        }
        Object localObject2 = new IntentFilter();
        ((IntentFilter)localObject2).addAction("android.intent.action.MEDIA_UNMOUNTED");
        ((IntentFilter)localObject2).addAction("android.intent.action.MEDIA_EJECT");
        ((IntentFilter)localObject2).addAction("android.intent.action.MEDIA_REMOVED");
        ((IntentFilter)localObject2).addAction("android.intent.action.MEDIA_BAD_REMOVAL");
        ((IntentFilter)localObject2).addAction("android.intent.action.MEDIA_MOUNTED");
        ((IntentFilter)localObject2).addDataScheme("file");
        ContextHolder.getAppContext().registerReceiver(this.jdField_a_of_type_AndroidContentBroadcastReceiver, (IntentFilter)localObject2);
        this.jdField_b_of_type_Boolean = true;
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("mAvailableQQBrowser=");
        ((StringBuilder)localObject2).append(this.jdField_a_of_type_JavaIoFile);
        FLogger.i("ExternalDataDir", ((StringBuilder)localObject2).toString());
        if (this.jdField_a_of_type_JavaIoFile != null)
        {
          localObject2 = new File(this.jdField_a_of_type_JavaIoFile, ".nomedia");
          if (((File)localObject2).exists()) {
            FileUtilsF.deleteQuietly((File)localObject2);
          }
        }
      }
      return;
    }
  }
  
  public static ExternalDataDir get(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    synchronized (jdField_a_of_type_JavaUtilHashMap)
    {
      ExternalDataDir localExternalDataDir2 = (ExternalDataDir)jdField_a_of_type_JavaUtilHashMap.get(paramString);
      ExternalDataDir localExternalDataDir1 = localExternalDataDir2;
      if (localExternalDataDir2 == null)
      {
        localExternalDataDir1 = new ExternalDataDir(paramString);
        jdField_a_of_type_JavaUtilHashMap.put(paramString, localExternalDataDir1);
      }
      return localExternalDataDir1;
    }
  }
  
  public static ExternalDataDir getDefault()
  {
    if (jdField_a_of_type_ComTencentCommonUtilsExternalDataDir == null) {
      jdField_a_of_type_ComTencentCommonUtilsExternalDataDir = get(DEFAULT_DIR_EXT_MAIN);
    }
    return jdField_a_of_type_ComTencentCommonUtilsExternalDataDir;
  }
  
  public File[] getAllPossibleStorageDirs(Context paramContext)
  {
    HashSet localHashSet = new HashSet();
    a();
    Object localObject1 = this.jdField_a_of_type_JavaIoFile;
    File localFile = this.jdField_b_of_type_JavaIoFile;
    int i = 1;
    localObject1 = Arrays.asList(new File[] { localObject1, localFile, this.c }).iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localFile = (File)((Iterator)localObject1).next();
      if (localFile != null) {
        try
        {
          localHashSet.add(localFile.getParentFile().getCanonicalFile());
        }
        catch (IOException localIOException)
        {
          FLogger.w("ExternalDataDir", localIOException.toString());
        }
      }
    }
    localObject1 = null;
    if (paramContext != null) {}
    for (;;)
    {
      try
      {
        paramContext = paramContext.getExternalFilesDir(null);
        if (paramContext == null) {
          continue;
        }
        localHashSet.add(paramContext.getCanonicalFile());
      }
      catch (Exception paramContext)
      {
        Object localObject2;
        continue;
      }
      FLogger.w("ExternalDataDir", paramContext.toString());
      try
      {
        paramContext = Environment.getExternalStorageDirectory();
        if (paramContext != null) {
          localHashSet.add(paramContext.getCanonicalFile());
        }
      }
      catch (Exception paramContext)
      {
        FLogger.w("ExternalDataDir", paramContext.toString());
      }
      paramContext = new File("/mnt/sdcard");
      if (paramContext.exists()) {
        try
        {
          localHashSet.add(paramContext.getCanonicalFile());
        }
        catch (IOException paramContext)
        {
          FLogger.w("ExternalDataDir", paramContext.toString());
        }
      }
      paramContext = new File("/storage/sdcard0");
      if (paramContext.exists()) {
        try
        {
          localHashSet.add(paramContext.getCanonicalFile());
        }
        catch (IOException paramContext)
        {
          FLogger.w("ExternalDataDir", paramContext.toString());
        }
      }
      if (Build.VERSION.SDK_INT < 21)
      {
        localObject2 = localHashSet.iterator();
        paramContext = (Context)localObject1;
        if (((Iterator)localObject2).hasNext())
        {
          paramContext = (File)((Iterator)localObject2).next();
          if (!"legacy".equals(paramContext.getName())) {
            continue;
          }
        }
        if (paramContext != null)
        {
          localObject1 = localHashSet.iterator();
          if (((Iterator)localObject1).hasNext())
          {
            localObject2 = (File)((Iterator)localObject1).next();
            if (("legacy".equals(((File)localObject2).getName())) || (!StringUtils.isStringEqual(((File)localObject2).getParent(), paramContext.getParent()))) {
              continue;
            }
          }
          else
          {
            i = 0;
          }
          if (i != 0) {
            localHashSet.remove(paramContext);
          }
        }
      }
      return (File[])localHashSet.toArray(new File[0]);
      paramContext = null;
    }
  }
  
  public File getAppCacheDir(Context paramContext)
  {
    if (FileUtilsF.hasSDcard())
    {
      paramContext.getExternalCacheDir();
      return FileUtilsF.createDir(getDataDir(), ".cache");
    }
    return FileUtilsF.getCacheDir(paramContext);
  }
  
  public File getAvailableDataDir(long paramLong)
  {
    a();
    File localFile = this.jdField_a_of_type_JavaIoFile;
    if ((localFile != null) && (SdCardInfo.Utils.getSdcardSpace(localFile.getAbsolutePath(), ContextHolder.getAppContext()).rest >= paramLong)) {
      return this.jdField_a_of_type_JavaIoFile;
    }
    localFile = this.jdField_b_of_type_JavaIoFile;
    if ((localFile != null) && (localFile != this.jdField_a_of_type_JavaIoFile) && (SdCardInfo.Utils.getSdcardSpace(localFile.getAbsolutePath(), ContextHolder.getAppContext()).rest >= paramLong)) {
      return this.jdField_b_of_type_JavaIoFile;
    }
    localFile = this.c;
    if ((localFile != null) && (localFile != this.jdField_a_of_type_JavaIoFile) && (SdCardInfo.Utils.getSdcardSpace(localFile.getAbsolutePath(), ContextHolder.getAppContext()).rest >= paramLong)) {
      return this.c;
    }
    return null;
  }
  
  public File getCacheDir()
  {
    if (SdCardInfo.Utils.hasSdcard(ContextHolder.getAppContext())) {
      return FileUtilsF.createDir(FileUtilsF.getDataDir(), ".cache");
    }
    return FileUtilsF.createDir(FileUtilsF.getCacheDir(ContextHolder.getAppContext()), ".cache");
  }
  
  public File getDataDir()
  {
    File localFile2 = getAvailableDataDir(0L);
    File localFile1;
    if (localFile2 != null)
    {
      localFile1 = localFile2;
      if (localFile2.exists()) {}
    }
    else
    {
      localFile1 = FileUtilsF.createDir(FileUtilsF.getSDcardDir(), this.jdField_a_of_type_JavaLangString);
    }
    return localFile1;
  }
  
  public File getDirFromRelativeName(String paramString, int paramInt)
  {
    File localFile = null;
    Object localObject1 = null;
    Object localObject2 = null;
    switch (paramInt)
    {
    default: 
      break;
    case 2: 
      localObject1 = FileUtilsF.getFilesDir(ContextHolder.getAppContext());
      if (localObject1 == null) {
        return null;
      }
      localObject1 = ((File)localObject1).getAbsolutePath();
      if (paramString == null)
      {
        localObject1 = new File((String)localObject1);
      }
      else if (paramString.startsWith("/"))
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append(paramString);
        localObject1 = new File(((StringBuilder)localObject2).toString());
      }
      else
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("/");
        ((StringBuilder)localObject2).append(paramString);
        localObject1 = new File(((StringBuilder)localObject2).toString());
      }
      break;
    case 1: 
      if (SdCardInfo.Utils.hasSdcard(ContextHolder.getAppContext()))
      {
        localFile = getDataDir();
        localObject1 = localObject2;
        if (localFile != null)
        {
          localObject1 = localObject2;
          if (localFile.exists()) {
            localObject1 = localFile.getAbsolutePath();
          }
        }
        if (paramString == null)
        {
          localObject1 = new File((String)localObject1);
        }
        else if (paramString.startsWith("/"))
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append((String)localObject1);
          ((StringBuilder)localObject2).append(paramString);
          localObject1 = new File(((StringBuilder)localObject2).toString());
        }
        else
        {
          localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append((String)localObject1);
          ((StringBuilder)localObject2).append("/");
          ((StringBuilder)localObject2).append(paramString);
          localObject1 = new File(((StringBuilder)localObject2).toString());
        }
      }
      break;
    case 0: 
      if (SdCardInfo.Utils.hasSdcard(ContextHolder.getAppContext()))
      {
        localObject2 = getDataDir();
        localObject1 = localFile;
        if (localObject2 != null)
        {
          localObject1 = localFile;
          if (((File)localObject2).exists()) {
            localObject1 = ((File)localObject2).getAbsolutePath();
          }
        }
      }
      else
      {
        localObject1 = FileUtilsF.getFilesDir(ContextHolder.getAppContext());
        if (localObject1 == null) {
          return null;
        }
        localObject1 = ((File)localObject1).getAbsolutePath();
      }
      if (paramString == null)
      {
        localObject1 = new File((String)localObject1);
      }
      else if (paramString.startsWith("/"))
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append(paramString);
        localObject1 = new File(((StringBuilder)localObject2).toString());
      }
      else
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("/");
        ((StringBuilder)localObject2).append(paramString);
        localObject1 = new File(((StringBuilder)localObject2).toString());
      }
      break;
    }
    if ((localObject1 != null) && (!((File)localObject1).exists())) {
      ((File)localObject1).mkdirs();
    }
    return (File)localObject1;
  }
  
  public File getExternalAvailableDataDir()
  {
    a();
    return this.c;
  }
  
  public File getExternalAvailableDataDirForDownload()
  {
    if (!this.jdField_b_of_type_Boolean) {
      a();
    }
    Object localObject1 = this.c;
    if (localObject1 != null) {
      return (File)localObject1;
    }
    Iterator localIterator = SdCardInfo.Utils.getSDcardInfo(ContextHolder.getAppContext()).extStorage.iterator();
    for (;;)
    {
      boolean bool = localIterator.hasNext();
      ??? = null;
      if (bool)
      {
        Object localObject5 = (StorageInfo)localIterator.next();
        localObject1 = new File(((StorageInfo)localObject5).path, this.jdField_a_of_type_JavaLangString);
        int i = Integer.parseInt(Build.VERSION.SDK);
        if (((StorageInfo)localObject5).isWritable) {
          return (File)localObject1;
        }
        if (i < 19) {
          continue;
        }
        Object localObject2;
        try
        {
          localObject6 = ContextHolder.getAppContext().getExternalFilesDir(null);
          localObject1 = ???;
          if (localObject6 != null) {
            localObject1 = ((File)localObject6).getAbsolutePath();
          }
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
          localObject2 = ???;
        }
        ??? = new StringBuilder();
        ((StringBuilder)???).append(((StorageInfo)localObject5).path);
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append("Android");
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append("data");
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append(ContextHolder.getAppContext().getPackageName());
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append("files");
        ((StringBuilder)???).append(File.separator);
        ((StringBuilder)???).append(this.jdField_a_of_type_JavaLangString);
        localObject5 = ((StringBuilder)???).toString();
        Object localObject6 = new File((String)localObject5);
        synchronized (this.jdField_a_of_type_JavaLangObject)
        {
          if ((!((File)localObject6).exists()) && ((((File)localObject6).exists()) || (((File)localObject6).mkdirs() != true)))
          {
            if (!TextUtils.isEmpty((CharSequence)localObject2))
            {
              localObject6 = new StringBuilder();
              ((StringBuilder)localObject6).append("AHNG708_");
              ((StringBuilder)localObject6).append((String)localObject2);
              ((StringBuilder)localObject6).append("_");
              ((StringBuilder)localObject6).append((String)localObject5);
              StatServerHolder.userBehaviorStatistics(((StringBuilder)localObject6).toString());
              localObject6 = new StringBuilder();
              ((StringBuilder)localObject6).append((String)localObject2);
              ((StringBuilder)localObject6).append(";");
              ((StringBuilder)localObject6).append((String)localObject5);
              FLogger.d("SDCardInfo", ((StringBuilder)localObject6).toString());
            }
          }
          else {
            return (File)localObject6;
          }
        }
      }
    }
    return null;
  }
  
  public int getExternalDataDirPos()
  {
    if (!this.jdField_b_of_type_Boolean) {
      a();
    }
    return this.jdField_a_of_type_Byte;
  }
  
  public File getInternalAvailableDataDir()
  {
    a();
    return this.jdField_b_of_type_JavaIoFile;
  }
  
  public boolean isUsingExternalDataDir()
  {
    return this.jdField_a_of_type_Byte == 2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\ExternalDataDir.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */