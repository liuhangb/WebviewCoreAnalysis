package com.tencent.tbs.common.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Environment;
import android.os.storage.StorageManager;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import java.io.File;
import java.util.ArrayList;

public class SdcardUtils
{
  static ArrayList<StorageInfo> getStorageInfo()
  {
    return SDCardDetector_StorageManager.getStorageInfo();
  }
  
  @TargetApi(9)
  public static StorageManager getStorageManager()
  {
    return (StorageManager)TbsBaseModuleShell.getCallerContext().getSystemService("storage");
  }
  
  static class SDCardDetector_StorageManager
  {
    public static ArrayList<SdcardUtils.StorageInfo> getStorageInfo()
    {
      ArrayList localArrayList = new ArrayList();
      if (DeviceUtils.getSdkVersion() >= 9) {}
      for (;;)
      {
        try
        {
          Object localObject = SdcardUtils.getStorageManager();
          if (localObject != null)
          {
            localObject = (Object[])ReflectionUtils.invokeInstance(localObject, "getVolumeList");
            if ((localObject != null) && (localObject.length > 0))
            {
              int k = 0;
              int j = 0;
              int i = k;
              if (j < localObject.length)
              {
                SdcardUtils.StorageInfo localStorageInfo2 = new SdcardUtils.StorageInfo();
                localStorageInfo2.path = ((String)ReflectionUtils.invokeInstance(localObject[j], "getPath"));
                if (localStorageInfo2.path == null)
                {
                  i = 1;
                }
                else
                {
                  Boolean localBoolean = (Boolean)ReflectionUtils.invokeInstance(localObject[j], "isRemovable");
                  if (localBoolean == null)
                  {
                    i = 1;
                  }
                  else
                  {
                    if (localBoolean.booleanValue()) {
                      break label235;
                    }
                    bool = true;
                    localStorageInfo2.isInternal = bool;
                    localStorageInfo2.isExists = isMounted(localStorageInfo2.path);
                    localArrayList.add(localStorageInfo2);
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
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
        SdcardUtils.StorageInfo localStorageInfo1 = new SdcardUtils.StorageInfo();
        localStorageInfo1.path = FileUtils.getSDcardDir().getAbsolutePath();
        localStorageInfo1.isInternal = true;
        localStorageInfo1.isExists = FileUtils.hasSDcard();
        localArrayList.add(localStorageInfo1);
        return localArrayList;
        label235:
        boolean bool = false;
      }
    }
    
    public static boolean isMounted(String paramString)
    {
      if (DeviceUtils.getSdkVersion() < 9) {
        return false;
      }
      try
      {
        StorageManager localStorageManager = SdcardUtils.getStorageManager();
        if (localStorageManager != null)
        {
          paramString = (String)ReflectionUtils.invokeInstance(localStorageManager, "getVolumeState", new Class[] { String.class }, new Object[] { paramString });
          if (paramString != null)
          {
            boolean bool = "mounted".equals(paramString);
            return bool;
          }
        }
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
      return true;
    }
  }
  
  static class SDCardDetector_VoldFstab
  {
    private static final File VOLD_FSTAB;
    private static ArrayList<String> mLabels;
    private static ArrayList<String> mPaths;
    
    static
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(Environment.getRootDirectory().getAbsoluteFile());
      localStringBuilder.append(File.separator);
      localStringBuilder.append("etc");
      localStringBuilder.append(File.separator);
      localStringBuilder.append("vold.fstab");
      VOLD_FSTAB = new File(localStringBuilder.toString());
    }
    
    public static ArrayList<String> getMountedPath()
    {
      if (mPaths == null)
      {
        mLabels = new ArrayList();
        mPaths = new ArrayList();
        initVoldFstabToCache();
      }
      return mPaths;
    }
    
    /* Error */
    private static void initVoldFstabToCache()
    {
      // Byte code:
      //   0: getstatic 55	com/tencent/tbs/common/utils/SdcardUtils$SDCardDetector_VoldFstab:VOLD_FSTAB	Ljava/io/File;
      //   3: invokevirtual 77	java/io/File:exists	()Z
      //   6: ifne +4 -> 10
      //   9: return
      //   10: getstatic 66	com/tencent/tbs/common/utils/SdcardUtils$SDCardDetector_VoldFstab:mLabels	Ljava/util/ArrayList;
      //   13: invokevirtual 80	java/util/ArrayList:clear	()V
      //   16: getstatic 61	com/tencent/tbs/common/utils/SdcardUtils$SDCardDetector_VoldFstab:mPaths	Ljava/util/ArrayList;
      //   19: invokevirtual 80	java/util/ArrayList:clear	()V
      //   22: new 82	java/io/BufferedReader
      //   25: dup
      //   26: new 84	java/io/FileReader
      //   29: dup
      //   30: getstatic 55	com/tencent/tbs/common/utils/SdcardUtils$SDCardDetector_VoldFstab:VOLD_FSTAB	Ljava/io/File;
      //   33: invokespecial 87	java/io/FileReader:<init>	(Ljava/io/File;)V
      //   36: invokespecial 90	java/io/BufferedReader:<init>	(Ljava/io/Reader;)V
      //   39: astore_1
      //   40: aload_1
      //   41: astore_0
      //   42: aload_1
      //   43: invokevirtual 93	java/io/BufferedReader:readLine	()Ljava/lang/String;
      //   46: astore_2
      //   47: aload_2
      //   48: ifnull +81 -> 129
      //   51: aload_1
      //   52: astore_0
      //   53: aload_2
      //   54: invokevirtual 98	java/lang/String:toLowerCase	()Ljava/lang/String;
      //   57: invokevirtual 101	java/lang/String:trim	()Ljava/lang/String;
      //   60: astore_2
      //   61: aload_1
      //   62: astore_0
      //   63: aload_2
      //   64: ldc 103
      //   66: invokevirtual 107	java/lang/String:startsWith	(Ljava/lang/String;)Z
      //   69: ifeq -29 -> 40
      //   72: aload_1
      //   73: astore_0
      //   74: aload_2
      //   75: ldc 109
      //   77: invokevirtual 113	java/lang/String:split	(Ljava/lang/String;)[Ljava/lang/String;
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
      //   94: ldc 115
      //   96: invokevirtual 119	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
      //   99: ifne -59 -> 40
      //   102: aload_1
      //   103: astore_0
      //   104: getstatic 66	com/tencent/tbs/common/utils/SdcardUtils$SDCardDetector_VoldFstab:mLabels	Ljava/util/ArrayList;
      //   107: aload_2
      //   108: iconst_1
      //   109: aaload
      //   110: invokevirtual 123	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   113: pop
      //   114: aload_1
      //   115: astore_0
      //   116: getstatic 61	com/tencent/tbs/common/utils/SdcardUtils$SDCardDetector_VoldFstab:mPaths	Ljava/util/ArrayList;
      //   119: aload_2
      //   120: iconst_2
      //   121: aaload
      //   122: invokevirtual 123	java/util/ArrayList:add	(Ljava/lang/Object;)Z
      //   125: pop
      //   126: goto -86 -> 40
      //   129: aload_1
      //   130: invokevirtual 126	java/io/BufferedReader:close	()V
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
      //   150: invokevirtual 129	java/io/IOException:printStackTrace	()V
      //   153: aload_1
      //   154: ifnull +13 -> 167
      //   157: aload_1
      //   158: invokevirtual 126	java/io/BufferedReader:close	()V
      //   161: return
      //   162: astore_0
      //   163: aload_0
      //   164: invokevirtual 129	java/io/IOException:printStackTrace	()V
      //   167: return
      //   168: astore_2
      //   169: aload_0
      //   170: astore_1
      //   171: aload_2
      //   172: astore_0
      //   173: aload_1
      //   174: ifnull +15 -> 189
      //   177: aload_1
      //   178: invokevirtual 126	java/io/BufferedReader:close	()V
      //   181: goto +8 -> 189
      //   184: astore_1
      //   185: aload_1
      //   186: invokevirtual 129	java/io/IOException:printStackTrace	()V
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
  
  public static class StorageInfo
  {
    public boolean isExists;
    public boolean isInternal;
    public String path;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\SdcardUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */