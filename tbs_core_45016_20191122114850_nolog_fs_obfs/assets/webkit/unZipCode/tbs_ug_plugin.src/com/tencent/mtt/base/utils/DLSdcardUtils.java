package com.tencent.mtt.base.utils;

import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.utils.ExternalDataDir;
import com.tencent.common.utils.FileUtilsF;
import com.tencent.common.utils.StringUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class DLSdcardUtils
{
  public static final byte QQBROWSER_POSITION_DATA = 2;
  public static final byte QQBROWSER_POSITION_NONE = 0;
  public static final byte QQBROWSER_POSITION_ROOT = 1;
  private static byte jdField_a_of_type_Byte = 0;
  private static SdCardInfo jdField_a_of_type_ComTencentMttBaseUtilsDLSdcardUtils$SdCardInfo;
  private static File jdField_a_of_type_JavaIoFile;
  private static HashMap<String, String> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  static boolean jdField_a_of_type_Boolean = false;
  private static File jdField_b_of_type_JavaIoFile;
  static boolean jdField_b_of_type_Boolean = false;
  private static File jdField_c_of_type_JavaIoFile;
  private static boolean jdField_c_of_type_Boolean = false;
  
  static ArrayList<StorageInfo> a(Context paramContext)
  {
    return a.a(paramContext);
  }
  
  private static ArrayList<String> a(ArrayList<String> paramArrayList)
  {
    if (paramArrayList.isEmpty()) {
      return paramArrayList;
    }
    int k = paramArrayList.size();
    ArrayList localArrayList = new ArrayList(k);
    long[] arrayOfLong1 = new long[k];
    long[] arrayOfLong2 = new long[k];
    int i = 0;
    String str;
    label126:
    int j;
    if (i < k)
    {
      str = (String)paramArrayList.get(i);
      try
      {
        if (!new File(str).exists()) {
          break label246;
        }
        StatFs localStatFs = new StatFs(str);
        arrayOfLong1[i] = (localStatFs.getBlockCount() * localStatFs.getBlockSize());
        arrayOfLong2[i] = (localStatFs.getAvailableBlocks() * localStatFs.getBlockSize());
        if (arrayOfLong1[i] > 0L) {
          break label256;
        }
      }
      catch (Exception localException)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("path invalid:");
        localStringBuilder.append(str);
        FLogger.d("SdcardUtils", localStringBuilder.toString());
        localException.printStackTrace();
      }
      if (j >= i) {
        break label273;
      }
      if ((arrayOfLong1[j] != arrayOfLong1[i]) || (arrayOfLong2[j] != arrayOfLong2[i])) {
        break label266;
      }
      if (!((String)paramArrayList.get(j)).startsWith(str)) {
        break label261;
      }
      localArrayList.remove(j);
      localArrayList.add(j, str);
    }
    for (;;)
    {
      if (j != 0) {
        localArrayList.add(str);
      }
      StringBuilder localStringBuilder;
      label246:
      i += 1;
      break;
      return localArrayList;
      label256:
      j = 0;
      break label126;
      label261:
      j = 0;
      continue;
      label266:
      j += 1;
      break label126;
      label273:
      j = 1;
    }
  }
  
  private static void a(Context paramContext)
  {
    boolean bool = jdField_b_of_type_Boolean;
    int j = 0;
    Object localObject1;
    if (bool)
    {
      localObject1 = jdField_a_of_type_JavaIoFile;
      if ((localObject1 != null) && (!((File)localObject1).exists())) {
        jdField_b_of_type_Boolean = false;
      }
      localObject1 = jdField_b_of_type_JavaIoFile;
      if ((localObject1 != null) && (!((File)localObject1).exists())) {
        jdField_b_of_type_Boolean = false;
      }
      localObject1 = jdField_c_of_type_JavaIoFile;
      if ((localObject1 != null) && (!((File)localObject1).exists())) {
        jdField_b_of_type_Boolean = false;
      }
      if (jdField_b_of_type_Boolean) {
        return;
      }
    }
    FLogger.d("SdcardUtils", "initQQBrowser");
    jdField_a_of_type_JavaIoFile = null;
    jdField_b_of_type_JavaIoFile = null;
    jdField_c_of_type_JavaIoFile = null;
    jdField_a_of_type_Byte = 0;
    SdCardInfo localSdCardInfo = getSDcardInfo(paramContext);
    File localFile;
    int i;
    if ((jdField_b_of_type_JavaIoFile == null) && (localSdCardInfo.hasInternalSD))
    {
      localFile = new File(localSdCardInfo.internalSDPath, ExternalDataDir.DEFAULT_DIR_EXT_MAIN);
      i = j;
      localObject1 = localFile;
      if (isFolderWritable(localFile))
      {
        jdField_b_of_type_JavaIoFile = localFile;
        i = j;
        localObject1 = localFile;
      }
    }
    else
    {
      localObject1 = null;
      i = j;
    }
    Object localObject2;
    while (i < localSdCardInfo.extStorageCount)
    {
      localObject1 = new File((String)localSdCardInfo.extStoragePaths.get(i), ExternalDataDir.DEFAULT_DIR_EXT_MAIN);
      if (((Boolean)localSdCardInfo.extWritable.get(i)).booleanValue())
      {
        jdField_c_of_type_JavaIoFile = (File)localObject1;
        jdField_a_of_type_Byte = 1;
        break;
      }
      if (DLDeviceUtils.getSdkVersion() >= 19)
      {
        try
        {
          paramContext.getExternalFilesDir(null);
        }
        catch (Throwable localThrowable)
        {
          localThrowable.printStackTrace();
        }
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localSdCardInfo.extStoragePaths.get(i));
        ((StringBuilder)localObject2).append(File.separator);
        ((StringBuilder)localObject2).append("Android");
        ((StringBuilder)localObject2).append(File.separator);
        ((StringBuilder)localObject2).append("data");
        ((StringBuilder)localObject2).append(File.separator);
        ((StringBuilder)localObject2).append(paramContext.getApplicationContext().getPackageName());
        ((StringBuilder)localObject2).append(File.separator);
        ((StringBuilder)localObject2).append("files");
        ((StringBuilder)localObject2).append(File.separator);
        ((StringBuilder)localObject2).append(ExternalDataDir.DEFAULT_DIR_EXT_MAIN);
        localObject2 = new File(((StringBuilder)localObject2).toString());
        if (isFolderWritable((File)localObject2))
        {
          jdField_c_of_type_JavaIoFile = (File)localObject2;
          jdField_a_of_type_Byte = 2;
          break;
        }
      }
      i += 1;
    }
    if (FileUtilsF.hasSDcard())
    {
      localFile = FileUtilsF.getSDcardDir();
      paramContext = (Context)localObject2;
      if (localFile != null)
      {
        paramContext = (Context)localObject2;
        if (localFile.exists()) {
          paramContext = FileUtilsF.createDir(localFile, ExternalDataDir.DEFAULT_DIR_EXT_MAIN);
        }
      }
      if (paramContext != null) {
        if (paramContext.equals(jdField_b_of_type_JavaIoFile)) {
          jdField_a_of_type_JavaIoFile = jdField_b_of_type_JavaIoFile;
        } else if (paramContext.equals(jdField_c_of_type_JavaIoFile)) {
          jdField_a_of_type_JavaIoFile = jdField_c_of_type_JavaIoFile;
        } else if (isFolderWritable(paramContext)) {
          jdField_a_of_type_JavaIoFile = paramContext;
        }
      }
    }
    if (jdField_a_of_type_JavaIoFile == null)
    {
      paramContext = jdField_b_of_type_JavaIoFile;
      if (paramContext == null) {
        paramContext = jdField_c_of_type_JavaIoFile;
      }
      jdField_a_of_type_JavaIoFile = paramContext;
    }
    jdField_b_of_type_Boolean = true;
    FLogger.d("SdcardUtils", "initQQBrowser done");
  }
  
  public static File getAvailableQQBrowserDir(Context paramContext)
  {
    return getAvailableQQBrowserDir(paramContext, 0L);
  }
  
  public static File getAvailableQQBrowserDir(Context paramContext, long paramLong)
  {
    a(paramContext);
    File localFile = jdField_a_of_type_JavaIoFile;
    if ((localFile != null) && (getSdcardSpace(paramContext, localFile.getAbsolutePath()).rest >= paramLong)) {
      return jdField_a_of_type_JavaIoFile;
    }
    localFile = jdField_b_of_type_JavaIoFile;
    if ((localFile != null) && (localFile != jdField_a_of_type_JavaIoFile) && (getSdcardSpace(paramContext, localFile.getAbsolutePath()).rest >= paramLong)) {
      return jdField_b_of_type_JavaIoFile;
    }
    localFile = jdField_c_of_type_JavaIoFile;
    if ((localFile != null) && (localFile != jdField_a_of_type_JavaIoFile) && (getSdcardSpace(paramContext, localFile.getAbsolutePath()).rest >= paramLong)) {
      return jdField_c_of_type_JavaIoFile;
    }
    return null;
  }
  
  public static ArrayList<File> getAvailableSDcardDirs(Context paramContext)
  {
    ArrayList localArrayList = new ArrayList();
    paramContext = getSDcardInfo(paramContext);
    if (paramContext.hasInternalSD) {
      localArrayList.add(new File(paramContext.internalSDPath));
    }
    paramContext = paramContext.extStoragePaths.iterator();
    while (paramContext.hasNext()) {
      localArrayList.add(new File((String)paramContext.next()));
    }
    return localArrayList;
  }
  
  public static File getExternalAvailableQQBrowserDir(Context paramContext)
  {
    a(paramContext);
    return jdField_c_of_type_JavaIoFile;
  }
  
  public static File getInternalAvailableQQBrowserDir(Context paramContext)
  {
    a(paramContext);
    return jdField_b_of_type_JavaIoFile;
  }
  
  public static String getSDcardDir(Context paramContext, String paramString)
  {
    boolean bool = TextUtils.isEmpty(paramString);
    String str = null;
    if (bool) {
      return null;
    }
    Object localObject = getSDcardInfo(paramContext);
    paramContext = str;
    if (((SdCardInfo)localObject).hasInternalSD)
    {
      paramContext = str;
      if (paramString.startsWith(((SdCardInfo)localObject).internalSDPath)) {
        paramContext = ((SdCardInfo)localObject).internalSDPath;
      }
    }
    localObject = ((SdCardInfo)localObject).extStoragePaths.iterator();
    while (((Iterator)localObject).hasNext())
    {
      str = (String)((Iterator)localObject).next();
      if (((paramContext == null) || (paramContext.length() < str.length())) && (paramString.startsWith(str))) {
        paramContext = str;
      }
    }
    return paramContext;
  }
  
  public static SdCardInfo getSDcardInfo(Context paramContext)
  {
    if (jdField_a_of_type_Boolean) {
      return jdField_a_of_type_ComTencentMttBaseUtilsDLSdcardUtils$SdCardInfo;
    }
    FLogger.d("SdcardUtils", "initSDcardInfo");
    int j = 0;
    jdField_c_of_type_Boolean = false;
    SdCardInfo localSdCardInfo = new SdCardInfo();
    paramContext = a(paramContext);
    Object localObject1;
    boolean bool;
    Object localObject2;
    if ((paramContext != null) && (!paramContext.isEmpty()))
    {
      paramContext = paramContext.iterator();
      while (paramContext.hasNext())
      {
        localObject1 = (StorageInfo)paramContext.next();
        if ((((StorageInfo)localObject1).path != null) && (((StorageInfo)localObject1).isExists)) {
          if (((StorageInfo)localObject1).isInternal)
          {
            if (localSdCardInfo.internalSDPath == null) {
              localSdCardInfo.internalSDPath = ((StorageInfo)localObject1).path;
            } else {
              FLogger.d("SdcardUtils", "出现两个内置SD卡!!存到外置SD卡里面去");
            }
          }
          else if (!StringUtils.isStringEqual(localSdCardInfo.internalSDPath, ((StorageInfo)localObject1).path))
          {
            localSdCardInfo.extStoragePaths.add(((StorageInfo)localObject1).path);
            bool = isFolderWritable(new File(((StorageInfo)localObject1).path));
            localSdCardInfo.extWritable.add(Boolean.valueOf(bool));
            if (!bool)
            {
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append(((StorageInfo)localObject1).path);
              ((StringBuilder)localObject2).append("不可写！");
              FLogger.d("SdcardUtils", ((StringBuilder)localObject2).toString());
              jdField_c_of_type_Boolean = true;
            }
          }
        }
      }
    }
    int i;
    if ((localSdCardInfo.internalSDPath == null) && (localSdCardInfo.extStoragePaths.size() >= 1))
    {
      paramContext = FileUtilsF.getSDcardDir().getAbsolutePath();
      i = localSdCardInfo.extStoragePaths.indexOf(paramContext);
      if (i >= 0)
      {
        localSdCardInfo.extStoragePaths.remove(i);
        localSdCardInfo.extWritable.remove(i);
        localSdCardInfo.internalSDPath = paramContext;
      }
    }
    if (localSdCardInfo.internalSDPath != null) {
      bool = true;
    } else {
      bool = false;
    }
    localSdCardInfo.hasInternalSD = bool;
    localSdCardInfo.extStorageCount = localSdCardInfo.extStoragePaths.size();
    if ((localSdCardInfo.extStorageCount == 0) && (DLDeviceUtils.getSdkVersion() < 11))
    {
      localSdCardInfo.extStoragePaths = a(b.a());
      localSdCardInfo.extStoragePaths.remove(localSdCardInfo.internalSDPath);
      localSdCardInfo.extStorageCount = localSdCardInfo.extStoragePaths.size();
      i = 0;
      while (i < localSdCardInfo.extStorageCount)
      {
        localSdCardInfo.extWritable.add(Boolean.valueOf(isFolderWritable(new File((String)localSdCardInfo.extStoragePaths.get(i)))));
        i += 1;
      }
    }
    if (DLDeviceUtils.getSdkVersion() < 19) {
      jdField_c_of_type_Boolean = false;
    }
    if (localSdCardInfo.hasInternalSD) {
      if (localSdCardInfo.extStorageCount == 0) {
        jdField_a_of_type_JavaUtilHashMap.put(localSdCardInfo.internalSDPath, "手机SD卡");
      } else {
        jdField_a_of_type_JavaUtilHashMap.put(localSdCardInfo.internalSDPath, "内置SD卡");
      }
    }
    if (localSdCardInfo.extStorageCount > 1) {
      i = 1;
    } else {
      i = 0;
    }
    while (j < localSdCardInfo.extStorageCount)
    {
      localObject1 = jdField_a_of_type_JavaUtilHashMap;
      localObject2 = localSdCardInfo.extStoragePaths.get(j);
      if (i != 0)
      {
        paramContext = new StringBuilder();
        paramContext.append("外置SD卡");
        paramContext.append(j + 1);
        paramContext = paramContext.toString();
      }
      else
      {
        paramContext = "外置SD卡";
      }
      ((HashMap)localObject1).put(localObject2, paramContext);
      j += 1;
    }
    jdField_a_of_type_ComTencentMttBaseUtilsDLSdcardUtils$SdCardInfo = localSdCardInfo;
    jdField_a_of_type_Boolean = true;
    return jdField_a_of_type_ComTencentMttBaseUtilsDLSdcardUtils$SdCardInfo;
  }
  
  public static String getSdcardName(Context paramContext, String paramString)
  {
    getSDcardInfo(paramContext);
    return (String)jdField_a_of_type_JavaUtilHashMap.get(paramString);
  }
  
  public static SdcardSizeInfo getSdcardSpace(Context paramContext, String paramString)
  {
    paramContext = getSDcardDir(paramContext, paramString);
    if (paramContext == null) {
      return new SdcardSizeInfo();
    }
    paramString = new ArrayList();
    paramString.add(paramContext);
    return getSdcardSpace(paramString);
  }
  
  public static SdcardSizeInfo getSdcardSpace(ArrayList<String> paramArrayList)
  {
    SdcardSizeInfo localSdcardSizeInfo = new SdcardSizeInfo();
    if (paramArrayList == null) {
      return localSdcardSizeInfo;
    }
    int m = paramArrayList.size();
    long[] arrayOfLong1 = new long[m];
    long[] arrayOfLong2 = new long[m];
    int k = 0;
    int i = 0;
    int j;
    for (;;)
    {
      j = k;
      if (i < m)
      {
        String str = (String)paramArrayList.get(i);
        try
        {
          StatFs localStatFs = new StatFs(str);
          arrayOfLong1[i] = (localStatFs.getBlockCount() * localStatFs.getBlockSize());
          arrayOfLong2[i] = (localStatFs.getAvailableBlocks() * localStatFs.getBlockSize());
          j = 0;
          while (j < i)
          {
            if ((arrayOfLong1[j] == arrayOfLong1[i]) && (arrayOfLong2[j] == arrayOfLong2[i]))
            {
              arrayOfLong1[i] = 0L;
              arrayOfLong2[i] = 0L;
              break;
            }
            j += 1;
          }
        }
        catch (Exception localException)
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("path invalid:");
          localStringBuilder.append(str);
          FLogger.d("SdcardUtils", localStringBuilder.toString());
          localException.printStackTrace();
          i += 1;
        }
      }
    }
    while (j < m)
    {
      localSdcardSizeInfo.total += arrayOfLong1[j];
      localSdcardSizeInfo.rest += arrayOfLong2[j];
      j += 1;
    }
    return localSdcardSizeInfo;
  }
  
  public static boolean has44ReadOnlySdcard(Context paramContext)
  {
    getSDcardInfo(paramContext);
    return jdField_c_of_type_Boolean;
  }
  
  public static boolean hasTwoOrMoreSdcards(Context paramContext)
  {
    paramContext = getSDcardInfo(paramContext);
    boolean bool2 = paramContext.hasInternalSD;
    boolean bool1 = true;
    if ((!bool2) || (paramContext.extStorageCount <= 0))
    {
      if (paramContext.extStorageCount > 1) {
        return true;
      }
      bool1 = false;
    }
    return bool1;
  }
  
  public static boolean is44ReadOnlyFile(Context paramContext, String paramString)
  {
    boolean bool1 = has44ReadOnlySdcard(paramContext);
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    File localFile = jdField_c_of_type_JavaIoFile;
    if ((localFile != null) && (paramString.startsWith(localFile.getAbsolutePath()))) {
      return false;
    }
    paramString = getSDcardDir(paramContext, paramString);
    paramContext = getSDcardInfo(paramContext);
    int i = paramContext.extStoragePaths.indexOf(paramString);
    bool1 = bool2;
    if (i >= 0)
    {
      bool1 = bool2;
      if (!((Boolean)paramContext.extWritable.get(i)).booleanValue()) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static boolean isFolderWritable(File paramFile)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("isFolderWritable ");
    localStringBuilder.append(paramFile.getAbsolutePath());
    FLogger.d("SdcardUtils", localStringBuilder.toString());
    try
    {
      if (!paramFile.exists()) {
        paramFile.mkdirs();
      }
      paramFile = File.createTempFile("tmppp", null, paramFile);
      if (!paramFile.exists()) {
        return false;
      }
      paramFile.delete();
      return true;
    }
    catch (Exception paramFile)
    {
      paramFile.printStackTrace();
    }
    return false;
  }
  
  public static boolean isOn44ExternalSDcard(Context paramContext, String paramString)
  {
    boolean bool1 = has44ReadOnlySdcard(paramContext);
    boolean bool2 = false;
    if (!bool1) {
      return false;
    }
    paramString = getSDcardDir(paramContext, paramString);
    paramContext = getSDcardInfo(paramContext);
    int i = paramContext.extStoragePaths.indexOf(paramString);
    bool1 = bool2;
    if (i >= 0)
    {
      bool1 = bool2;
      if (!((Boolean)paramContext.extWritable.get(i)).booleanValue()) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public static boolean isUsingExternalDataDir()
  {
    return jdField_a_of_type_Byte == 2;
  }
  
  public static boolean noSdcard(Context paramContext)
  {
    return getAvailableSDcardDirs(paramContext).isEmpty();
  }
  
  public static String replaceSdcardName(Context paramContext, String paramString)
  {
    String str = getSDcardDir(paramContext, paramString);
    if (str != null)
    {
      paramContext = getSdcardName(paramContext, str);
      if (paramContext != null) {
        return paramString.replaceFirst(str, paramContext);
      }
    }
    return paramString;
  }
  
  public static class SdCardInfo
  {
    public int extStorageCount = 0;
    public ArrayList<String> extStoragePaths = new ArrayList();
    public ArrayList<Boolean> extWritable = new ArrayList();
    public boolean hasInternalSD = false;
    public String internalSDPath = null;
  }
  
  public static class SdcardSizeInfo
  {
    public long rest = 0L;
    public long total = 0L;
  }
  
  public static class StorageInfo
  {
    public boolean isExists;
    public boolean isInternal;
    public String path;
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("path:");
      localStringBuilder.append(this.path);
      localStringBuilder.append("   isInternal:");
      localStringBuilder.append(this.isInternal);
      localStringBuilder.append("   isExists:");
      localStringBuilder.append(this.isExists);
      return localStringBuilder.toString();
    }
  }
  
  static class a
  {
    public static ArrayList<DLSdcardUtils.StorageInfo> a(Context paramContext)
    {
      ArrayList localArrayList = new ArrayList();
      if (DLDeviceUtils.getSdkVersion() >= 9) {}
      for (;;)
      {
        try
        {
          Object localObject = (StorageManager)paramContext.getSystemService("storage");
          if (localObject != null)
          {
            localObject = (Object[])DLReflectionUtils.invokeInstance(localObject, "getVolumeList");
            if ((localObject != null) && (localObject.length > 0))
            {
              int k = 0;
              int j = 0;
              int i = k;
              if (j < localObject.length)
              {
                DLSdcardUtils.StorageInfo localStorageInfo = new DLSdcardUtils.StorageInfo();
                localStorageInfo.path = ((String)DLReflectionUtils.invokeInstance(localObject[j], "getPath"));
                if (localStorageInfo.path == null)
                {
                  i = 1;
                }
                else
                {
                  Boolean localBoolean = (Boolean)DLReflectionUtils.invokeInstance(localObject[j], "isRemovable");
                  if (localBoolean == null)
                  {
                    i = 1;
                  }
                  else
                  {
                    if (localBoolean.booleanValue()) {
                      break label237;
                    }
                    bool = true;
                    localStorageInfo.isInternal = bool;
                    localStorageInfo.isExists = a(paramContext, localStorageInfo.path);
                    localArrayList.add(localStorageInfo);
                    j += 1;
                    continue;
                  }
                }
              }
              if (i == 0) {
                return localArrayList;
              }
            }
          }
        }
        catch (Exception paramContext)
        {
          paramContext.printStackTrace();
        }
        paramContext = new DLSdcardUtils.StorageInfo();
        paramContext.path = FileUtilsF.getSDcardDir().getAbsolutePath();
        paramContext.isInternal = true;
        paramContext.isExists = FileUtilsF.hasSDcard();
        localArrayList.add(paramContext);
        return localArrayList;
        label237:
        boolean bool = false;
      }
    }
    
    public static boolean a(Context paramContext, String paramString)
    {
      if (DLDeviceUtils.getSdkVersion() < 9) {
        return false;
      }
      try
      {
        paramContext = (StorageManager)paramContext.getSystemService("storage");
        if (paramContext != null)
        {
          paramContext = (String)DLReflectionUtils.invokeInstance(paramContext, "getVolumeState", new Class[] { String.class }, new Object[] { paramString });
          if (paramContext != null)
          {
            boolean bool = "mounted".equals(paramContext);
            return bool;
          }
        }
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
      return true;
    }
  }
  
  static class b
  {
    private static final File jdField_a_of_type_JavaIoFile;
    private static ArrayList<String> jdField_a_of_type_JavaUtilArrayList;
    private static ArrayList<String> b;
    
    static
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(Environment.getRootDirectory().getAbsoluteFile());
      localStringBuilder.append(File.separator);
      localStringBuilder.append("etc");
      localStringBuilder.append(File.separator);
      localStringBuilder.append("vold.fstab");
      jdField_a_of_type_JavaIoFile = new File(localStringBuilder.toString());
    }
    
    public static ArrayList<String> a()
    {
      if (b == null)
      {
        jdField_a_of_type_JavaUtilArrayList = new ArrayList();
        b = new ArrayList();
        a();
      }
      return b;
    }
    
    /* Error */
    private static void a()
    {
      // Byte code:
      //   0: getstatic 53	com/tencent/mtt/base/utils/DLSdcardUtils$b:jdField_a_of_type_JavaIoFile	Ljava/io/File;
      //   3: invokevirtual 72	java/io/File:exists	()Z
      //   6: ifne +4 -> 10
      //   9: return
      //   10: getstatic 62	com/tencent/mtt/base/utils/DLSdcardUtils$b:jdField_a_of_type_JavaUtilArrayList	Ljava/util/ArrayList;
      //   13: invokevirtual 75	java/util/ArrayList:clear	()V
      //   16: getstatic 57	com/tencent/mtt/base/utils/DLSdcardUtils$b:b	Ljava/util/ArrayList;
      //   19: invokevirtual 75	java/util/ArrayList:clear	()V
      //   22: new 77	java/io/BufferedReader
      //   25: dup
      //   26: new 79	java/io/FileReader
      //   29: dup
      //   30: getstatic 53	com/tencent/mtt/base/utils/DLSdcardUtils$b:jdField_a_of_type_JavaIoFile	Ljava/io/File;
      //   33: invokespecial 82	java/io/FileReader:<init>	(Ljava/io/File;)V
      //   36: invokespecial 85	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
      //   39: astore_1
      //   40: aload_1
      //   41: astore_0
      //   42: aload_1
      //   43: invokevirtual 88	java/io/BufferedReader:readLine	()Ljava/lang/String;
      //   46: astore_2
      //   47: aload_2
      //   48: ifnull +81 -> 129
      //   51: aload_1
      //   52: astore_0
      //   53: aload_2
      //   54: invokevirtual 93	java/lang/String:toLowerCase	()Ljava/lang/String;
      //   57: invokevirtual 96	java/lang/String:trim	()Ljava/lang/String;
      //   60: astore_2
      //   61: aload_1
      //   62: astore_0
      //   63: aload_2
      //   64: ldc 98
      //   66: invokevirtual 102	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   69: ifeq -29 -> 40
      //   72: aload_1
      //   73: astore_0
      //   74: aload_2
      //   75: ldc 104
      //   77: invokevirtual 108	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
      //   80: astore_2
      //   81: aload_1
      //   82: astore_0
      //   83: aload_2
      //   84: arraylength
      //   85: iconst_2
      //   86: if_icmple -46 -> 40
      //   89: aload_1
      //   90: astore_0
      //   91: aload_2
      //   92: iconst_2
      //   93: aaload
      //   94: ldc 110
      //   96: invokevirtual 114	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
      //   99: ifne -59 -> 40
      //   102: aload_1
      //   103: astore_0
      //   104: getstatic 62	com/tencent/mtt/base/utils/DLSdcardUtils$b:jdField_a_of_type_JavaUtilArrayList	Ljava/util/ArrayList;
      //   107: aload_2
      //   108: iconst_1
      //   109: aaload
      //   110: invokevirtual 118	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   113: pop
      //   114: aload_1
      //   115: astore_0
      //   116: getstatic 57	com/tencent/mtt/base/utils/DLSdcardUtils$b:b	Ljava/util/ArrayList;
      //   119: aload_2
      //   120: iconst_2
      //   121: aaload
      //   122: invokevirtual 118	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   125: pop
      //   126: goto -86 -> 40
      //   129: aload_1
      //   130: invokevirtual 121	java/io/BufferedReader:close	()V
      //   133: return
      //   134: astore_2
      //   135: goto +12 -> 147
      //   138: astore_0
      //   139: aconst_null
      //   140: astore_1
      //   141: goto +32 -> 173
      //   144: astore_2
      //   145: aconst_null
      //   146: astore_1
      //   147: aload_1
      //   148: astore_0
      //   149: aload_2
      //   150: invokevirtual 124	java/io/IOException:printStackTrace	()V
      //   153: aload_1
      //   154: ifnull +13 -> 167
      //   157: aload_1
      //   158: invokevirtual 121	java/io/BufferedReader:close	()V
      //   161: return
      //   162: astore_0
      //   163: aload_0
      //   164: invokevirtual 124	java/io/IOException:printStackTrace	()V
      //   167: return
      //   168: astore_2
      //   169: aload_0
      //   170: astore_1
      //   171: aload_2
      //   172: astore_0
      //   173: aload_1
      //   174: ifnull +15 -> 189
      //   177: aload_1
      //   178: invokevirtual 121	java/io/BufferedReader:close	()V
      //   181: goto +8 -> 189
      //   184: astore_1
      //   185: aload_1
      //   186: invokevirtual 124	java/io/IOException:printStackTrace	()V
      //   189: aload_0
      //   190: athrow
      // Local variable table:
      //   start	length	slot	name	signature
      //   41	75	0	localObject1	Object
      //   138	1	0	localObject2	Object
      //   148	1	0	localObject3	Object
      //   162	8	0	localIOException1	java.io.IOException
      //   172	18	0	localObject4	Object
      //   39	139	1	localObject5	Object
      //   184	2	1	localIOException2	java.io.IOException
      //   46	74	2	localObject6	Object
      //   134	1	2	localIOException3	java.io.IOException
      //   144	6	2	localIOException4	java.io.IOException
      //   168	4	2	localObject7	Object
      // Exception table:
      //   from	to	target	type
      //   42	47	134	java/io/IOException
      //   53	61	134	java/io/IOException
      //   63	72	134	java/io/IOException
      //   74	81	134	java/io/IOException
      //   83	89	134	java/io/IOException
      //   91	102	134	java/io/IOException
      //   104	114	134	java/io/IOException
      //   116	126	134	java/io/IOException
      //   22	40	138	finally
      //   22	40	144	java/io/IOException
      //   129	133	162	java/io/IOException
      //   157	161	162	java/io/IOException
      //   42	47	168	finally
      //   53	61	168	finally
      //   63	72	168	finally
      //   74	81	168	finally
      //   83	89	168	finally
      //   91	102	168	finally
      //   104	114	168	finally
      //   116	126	168	finally
      //   149	153	168	finally
      //   177	181	184	java/io/IOException
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\DLSdcardUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */