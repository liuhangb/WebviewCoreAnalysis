package com.tencent.common.utils;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.StatFs;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.mtt.ContextHolder;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class SdCardInfo
{
  public static final int RESULT_TYPE_EXTERNALPATH = 2;
  public static final int RESULT_TYPE_INTERNALPATH = 1;
  public static final int RESULT_TYPE_NONE = -1;
  public static final int SDCARD_TYPE_EXTERNAL = 2;
  public static final int SDCARD_TYPE_INTERNAL = 1;
  public static String TAG = "SdCardInfo";
  public ArrayList<StorageInfo> extStorage = new ArrayList();
  public StorageInfo internalStorage;
  
  static ArrayList<String> a(ArrayList<String> paramArrayList)
  {
    if (paramArrayList.isEmpty()) {
      return paramArrayList;
    }
    int k = paramArrayList.size();
    ArrayList localArrayList = new ArrayList(k);
    long[] arrayOfLong1 = new long[k];
    long[] arrayOfLong2 = new long[k];
    int i = 0;
    String str1;
    label126:
    int j;
    if (i < k)
    {
      str1 = (String)paramArrayList.get(i);
      try
      {
        if (!new File(str1).exists()) {
          break label251;
        }
        StatFs localStatFs = new StatFs(str1);
        arrayOfLong1[i] = (localStatFs.getBlockCount() * localStatFs.getBlockSize());
        arrayOfLong2[i] = (localStatFs.getAvailableBlocks() * localStatFs.getBlockSize());
        if (arrayOfLong1[i] > 0L) {
          break label261;
        }
      }
      catch (Exception localException)
      {
        str2 = TAG;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("path invalid:");
        localStringBuilder.append(str1);
        FLogger.d(str2, localStringBuilder.toString());
        localException.printStackTrace();
      }
      if (j >= i) {
        break label278;
      }
      if ((arrayOfLong1[j] != arrayOfLong1[i]) || (arrayOfLong2[j] != arrayOfLong2[i])) {
        break label271;
      }
      if (!((String)paramArrayList.get(j)).startsWith(str1)) {
        break label266;
      }
      localArrayList.remove(j);
      localArrayList.add(j, str1);
    }
    for (;;)
    {
      if (j != 0) {
        localArrayList.add(str1);
      }
      String str2;
      StringBuilder localStringBuilder;
      label251:
      i += 1;
      break;
      return localArrayList;
      label261:
      j = 0;
      break label126;
      label266:
      j = 0;
      continue;
      label271:
      j += 1;
      break label126;
      label278:
      j = 1;
    }
  }
  
  public static boolean isFolderWritable(File paramFile)
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("isFolderWritable ");
    localStringBuilder.append(paramFile.getAbsolutePath());
    FLogger.d(str, localStringBuilder.toString());
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
  
  public void addExternalStorage(StorageInfo paramStorageInfo)
  {
    if (paramStorageInfo != null) {
      this.extStorage.add(paramStorageInfo);
    }
  }
  
  public List<String> getExternalSdCardPaths()
  {
    ArrayList localArrayList = new ArrayList(1);
    if (hasExternalSD())
    {
      Iterator localIterator = this.extStorage.iterator();
      while (localIterator.hasNext()) {
        localArrayList.add(((StorageInfo)localIterator.next()).path);
      }
    }
    return localArrayList;
  }
  
  public StorageInfo getExternalStorage(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      Iterator localIterator = this.extStorage.iterator();
      while (localIterator.hasNext())
      {
        StorageInfo localStorageInfo = (StorageInfo)localIterator.next();
        if (StringUtils.isStringEqual(paramString, localStorageInfo.path)) {
          return localStorageInfo;
        }
      }
    }
    return null;
  }
  
  public String getInternalSdCardPath()
  {
    if (hasInternalSD()) {
      return this.internalStorage.path;
    }
    return null;
  }
  
  public int getPathType(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if ((hasInternalSD()) && (this.internalStorage.path.equalsIgnoreCase(paramString))) {
        return 1;
      }
      if (hasExternalSD())
      {
        Iterator localIterator = this.extStorage.iterator();
        while (localIterator.hasNext()) {
          if (((StorageInfo)localIterator.next()).path.equalsIgnoreCase(paramString)) {
            return 2;
          }
        }
      }
    }
    return -1;
  }
  
  public int getSdCardCount()
  {
    if (this.internalStorage != null) {
      return this.extStorage.size() + 1;
    }
    return this.extStorage.size();
  }
  
  public boolean hasExternalSD()
  {
    ArrayList localArrayList = this.extStorage;
    return (localArrayList != null) && (localArrayList.size() > 0);
  }
  
  public boolean hasInternalSD()
  {
    return this.internalStorage != null;
  }
  
  public boolean hasMoreThanTwoSD()
  {
    StorageInfo localStorageInfo = this.internalStorage;
    boolean bool = true;
    if ((localStorageInfo == null) || (this.extStorage.size() <= 0))
    {
      if (this.extStorage.size() > 1) {
        return true;
      }
      bool = false;
    }
    return bool;
  }
  
  public void setInternalStorage(StorageInfo paramStorageInfo)
  {
    if (paramStorageInfo != null) {
      this.internalStorage = paramStorageInfo;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("hasInternalSD:");
    localStringBuilder.append(hasInternalSD());
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(", extStorageCount:");
    ((StringBuilder)localObject).append(this.extStorage.size());
    localStringBuilder.append(((StringBuilder)localObject).toString());
    localObject = this.internalStorage;
    if (localObject != null) {
      localStringBuilder.append(((StorageInfo)localObject).toString());
    }
    localObject = this.extStorage.iterator();
    while (((Iterator)localObject).hasNext()) {
      localStringBuilder.append(((StorageInfo)((Iterator)localObject).next()).toString());
    }
    return localStringBuilder.toString();
  }
  
  public static class SdcardSizeInfo
  {
    public long rest = 0L;
    public long total = 0L;
  }
  
  public static class Utils
  {
    static SdCardInfo jdField_a_of_type_ComTencentCommonUtilsSdCardInfo;
    static String jdField_a_of_type_JavaLangString = "SdCardInfo.Utils";
    private static HashMap<String, String> jdField_a_of_type_JavaUtilHashMap = new HashMap();
    static boolean jdField_a_of_type_Boolean = false;
    public static boolean mHas44ReadOnlySdcard = false;
    
    public static ArrayList<File> getAvailableSDcardDirs(Context paramContext)
    {
      ArrayList localArrayList = new ArrayList();
      paramContext = getSDcardInfo(paramContext);
      if (paramContext.hasInternalSD()) {
        localArrayList.add(new File(paramContext.internalStorage.path));
      }
      paramContext = paramContext.extStorage.iterator();
      while (paramContext.hasNext()) {
        localArrayList.add(new File(((StorageInfo)paramContext.next()).path));
      }
      return localArrayList;
    }
    
    public static String getSDcardDir(String paramString, Context paramContext)
    {
      boolean bool = TextUtils.isEmpty(paramString);
      Iterator localIterator = null;
      if (bool) {
        return null;
      }
      Object localObject = getSDcardInfo(paramContext);
      paramContext = localIterator;
      if (((SdCardInfo)localObject).hasInternalSD())
      {
        paramContext = localIterator;
        if (paramString.startsWith(((SdCardInfo)localObject).internalStorage.path)) {
          paramContext = ((SdCardInfo)localObject).internalStorage.path;
        }
      }
      localIterator = ((SdCardInfo)localObject).extStorage.iterator();
      while (localIterator.hasNext())
      {
        localObject = (StorageInfo)localIterator.next();
        if (((paramContext == null) || (paramContext.length() < ((StorageInfo)localObject).path.length())) && (paramString.startsWith(((StorageInfo)localObject).path))) {
          paramContext = ((StorageInfo)localObject).path;
        }
      }
      return paramContext;
    }
    
    public static SdCardInfo getSDcardInfo(Context paramContext)
    {
      if (jdField_a_of_type_Boolean) {
        return jdField_a_of_type_ComTencentCommonUtilsSdCardInfo;
      }
      Object localObject1 = jdField_a_of_type_JavaLangString;
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("[getSDcardInfo] mSdInitialized:");
      ((StringBuilder)localObject2).append(jdField_a_of_type_Boolean);
      FLogger.d((String)localObject1, ((StringBuilder)localObject2).toString());
      mHas44ReadOnlySdcard = false;
      localObject1 = new SdCardInfo();
      paramContext = SDCardDetector_StorageManager.getStorageInfo(paramContext);
      Object localObject3;
      if ((paramContext != null) && (!paramContext.isEmpty()))
      {
        paramContext = paramContext.iterator();
        while (paramContext.hasNext())
        {
          localObject2 = (StorageInfo)paramContext.next();
          if (((StorageInfo)localObject2).path != null) {
            if (((StorageInfo)localObject2).isInternal)
            {
              if (!((SdCardInfo)localObject1).hasInternalSD()) {
                ((SdCardInfo)localObject1).setInternalStorage((StorageInfo)localObject2);
              } else {
                FLogger.d(jdField_a_of_type_JavaLangString, "出现两个内置SD卡!!存到外置SD卡里面去");
              }
            }
            else if ((!((SdCardInfo)localObject1).hasInternalSD()) || (!((SdCardInfo)localObject1).internalStorage.equals(localObject2)))
            {
              ((StorageInfo)localObject2).isWritable = SdCardInfo.isFolderWritable(new File(((StorageInfo)localObject2).path));
              ((SdCardInfo)localObject1).addExternalStorage((StorageInfo)localObject2);
              if (!((StorageInfo)localObject2).isWritable)
              {
                localObject3 = jdField_a_of_type_JavaLangString;
                StringBuilder localStringBuilder = new StringBuilder();
                localStringBuilder.append(((StorageInfo)localObject2).path);
                localStringBuilder.append("不可写！");
                FLogger.d((String)localObject3, localStringBuilder.toString());
                mHas44ReadOnlySdcard = true;
              }
            }
          }
        }
      }
      if ((!((SdCardInfo)localObject1).hasInternalSD()) && (((SdCardInfo)localObject1).hasExternalSD()))
      {
        paramContext = FileUtilsF.getSDcardDir();
        if ((paramContext != null) && (paramContext.exists()))
        {
          paramContext = ((SdCardInfo)localObject1).getExternalStorage(paramContext.getAbsolutePath());
          if (paramContext != null)
          {
            ((SdCardInfo)localObject1).internalStorage = paramContext;
            ((SdCardInfo)localObject1).extStorage.remove(paramContext);
          }
        }
      }
      if ((!((SdCardInfo)localObject1).hasExternalSD()) && (Integer.parseInt(Build.VERSION.SDK) < 11))
      {
        paramContext = SdCardInfo.a(SDCardDetector_VoldFstab.getMountedPath());
        if ((paramContext != null) && (paramContext.size() > 0))
        {
          paramContext = paramContext.iterator();
          while (paramContext.hasNext())
          {
            localObject2 = (String)paramContext.next();
            localObject3 = new StorageInfo();
            ((StorageInfo)localObject3).path = ((String)localObject2);
            ((StorageInfo)localObject3).isInternal = false;
            ((StorageInfo)localObject3).isWritable = SdCardInfo.isFolderWritable(new File((String)localObject2));
            ((SdCardInfo)localObject1).addExternalStorage((StorageInfo)localObject3);
          }
          ((SdCardInfo)localObject1).extStorage.remove(((SdCardInfo)localObject1).internalStorage);
        }
      }
      if (Integer.parseInt(Build.VERSION.SDK) < 19) {
        mHas44ReadOnlySdcard = false;
      }
      jdField_a_of_type_ComTencentCommonUtilsSdCardInfo = (SdCardInfo)localObject1;
      paramContext = jdField_a_of_type_JavaLangString;
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("[getSDcardInfo] mSdCardInfo:");
      ((StringBuilder)localObject1).append(jdField_a_of_type_ComTencentCommonUtilsSdCardInfo);
      FLogger.d(paramContext, ((StringBuilder)localObject1).toString());
      jdField_a_of_type_Boolean = true;
      return jdField_a_of_type_ComTencentCommonUtilsSdCardInfo;
    }
    
    public static String getSdcardName(String paramString, Context paramContext)
    {
      SdCardInfo localSdCardInfo = getSDcardInfo(paramContext);
      if (jdField_a_of_type_JavaUtilHashMap.size() <= 0)
      {
        if (localSdCardInfo.hasInternalSD()) {
          if (!localSdCardInfo.hasExternalSD()) {
            jdField_a_of_type_JavaUtilHashMap.put(localSdCardInfo.internalStorage.path, "手机存储");
          } else {
            jdField_a_of_type_JavaUtilHashMap.put(localSdCardInfo.internalStorage.path, "内置存储");
          }
        }
        int k = localSdCardInfo.extStorage.size();
        int j = 0;
        int i = 1;
        if (k <= 1) {
          i = 0;
        }
        while (j < localSdCardInfo.extStorage.size())
        {
          HashMap localHashMap = jdField_a_of_type_JavaUtilHashMap;
          String str = ((StorageInfo)localSdCardInfo.extStorage.get(j)).path;
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
          localHashMap.put(str, paramContext);
          j += 1;
        }
      }
      return (String)jdField_a_of_type_JavaUtilHashMap.get(paramString);
    }
    
    public static SdCardInfo.SdcardSizeInfo getSdcardSpace(int paramInt)
    {
      SdCardInfo localSdCardInfo = getSDcardInfo(ContextHolder.getAppContext());
      ArrayList localArrayList;
      if ((1 == paramInt) && (localSdCardInfo.hasInternalSD()))
      {
        localArrayList = new ArrayList();
        localArrayList.add(localSdCardInfo.internalStorage.path);
        return getSdcardSpace(localArrayList);
      }
      if ((2 == paramInt) && (localSdCardInfo.hasExternalSD()))
      {
        localArrayList = new ArrayList();
        localArrayList.add(((StorageInfo)localSdCardInfo.extStorage.get(0)).path);
        return getSdcardSpace(localArrayList);
      }
      return null;
    }
    
    public static SdCardInfo.SdcardSizeInfo getSdcardSpace(String paramString, Context paramContext)
    {
      paramString = getSDcardDir(paramString, paramContext);
      if (paramString == null) {
        return new SdCardInfo.SdcardSizeInfo();
      }
      paramContext = new ArrayList();
      paramContext.add(paramString);
      return getSdcardSpace(paramContext);
    }
    
    public static SdCardInfo.SdcardSizeInfo getSdcardSpace(ArrayList<String> paramArrayList)
    {
      SdCardInfo.SdcardSizeInfo localSdcardSizeInfo = new SdCardInfo.SdcardSizeInfo();
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
          String str1 = (String)paramArrayList.get(i);
          try
          {
            StatFs localStatFs = new StatFs(str1);
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
            String str2 = jdField_a_of_type_JavaLangString;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("path invalid:");
            localStringBuilder.append(str1);
            FLogger.d(str2, localStringBuilder.toString());
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
      return mHas44ReadOnlySdcard;
    }
    
    public static boolean hasSdcard(Context paramContext)
    {
      paramContext = getSDcardInfo(paramContext);
      return (paramContext.hasInternalSD()) || (paramContext.hasExternalSD());
    }
    
    public static boolean hasTwoOrMoreSdcards(Context paramContext)
    {
      return getSDcardInfo(paramContext).hasMoreThanTwoSD();
    }
    
    public static boolean is44ReadOnlyFile(String paramString, Context paramContext)
    {
      if (!has44ReadOnlySdcard(paramContext)) {
        return false;
      }
      paramString = getSDcardDir(paramString, paramContext);
      paramString = getSDcardInfo(ContextHolder.getAppContext()).getExternalStorage(paramString);
      if (paramString != null) {
        return paramString.isWritable ^ true;
      }
      return false;
    }
    
    public static boolean isOn44ExternalSDcard(String paramString, Context paramContext)
    {
      if (!has44ReadOnlySdcard(paramContext)) {
        return false;
      }
      paramString = getSDcardDir(paramString, paramContext);
      paramString = getSDcardInfo(ContextHolder.getAppContext()).getExternalStorage(paramString);
      if (paramString != null) {
        return paramString.isWritable ^ true;
      }
      return false;
    }
    
    public static boolean isSdcardRoot(String paramString)
    {
      Object localObject = getSDcardInfo(ContextHolder.getAppContext());
      boolean bool;
      if (((SdCardInfo)localObject).hasInternalSD()) {
        bool = paramString.equalsIgnoreCase(((SdCardInfo)localObject).internalStorage.path);
      } else {
        bool = false;
      }
      if (!bool)
      {
        localObject = ((SdCardInfo)localObject).extStorage.iterator();
        while (((Iterator)localObject).hasNext()) {
          if (paramString.equalsIgnoreCase(((StorageInfo)((Iterator)localObject).next()).path)) {
            return true;
          }
        }
      }
      return bool;
    }
    
    public static String replaceSdcardName(String paramString, Context paramContext)
    {
      String str = getSDcardDir(paramString, paramContext);
      if (str != null)
      {
        paramContext = getSdcardName(str, paramContext);
        if (paramContext != null) {
          return paramString.replaceFirst(str, paramContext);
        }
      }
      return paramString;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\SdCardInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */