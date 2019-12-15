package com.tencent.common.utils;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.storage.StorageManager;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import java.io.File;
import java.util.ArrayList;

public class SDCardDetector_StorageManager
{
  public static final String TAG = "SDCardDetector_StorageManager";
  
  public static ArrayList<StorageInfo> getStorageInfo(Context paramContext)
  {
    ArrayList localArrayList = new ArrayList();
    if (Integer.parseInt(Build.VERSION.SDK) >= 9) {}
    for (;;)
    {
      try
      {
        Object localObject1 = getStorageManager(paramContext);
        if (localObject1 != null)
        {
          localObject1 = (Object[])ReflectionUtils.invokeInstance(localObject1, "getVolumeList");
          if ((localObject1 != null) && (localObject1.length > 0))
          {
            int i = 0;
            if (i < localObject1.length)
            {
              StorageInfo localStorageInfo = new StorageInfo();
              Object localObject2 = ReflectionUtils.invokeInstance(localObject1[i], "getPath");
              StringBuilder localStringBuilder = new StringBuilder();
              localStringBuilder.append("[getStorageInfo] pathResult:");
              localStringBuilder.append(localObject2);
              FLogger.d("SDCardDetector_StorageManager", localStringBuilder.toString());
              if ((localObject2 != null) && ((localObject2 instanceof String)))
              {
                localStorageInfo.path = ((String)localObject2);
                localObject2 = new StringBuilder();
                ((StringBuilder)localObject2).append("[getStorageInfo] isMounted(info.path):");
                ((StringBuilder)localObject2).append(isMounted(localStorageInfo.path, paramContext));
                FLogger.d("SDCardDetector_StorageManager", ((StringBuilder)localObject2).toString());
                if ((!TextUtils.isEmpty(localStorageInfo.path)) && (isMounted(localStorageInfo.path, paramContext)))
                {
                  localObject2 = ReflectionUtils.invokeInstance(localObject1[i], "isRemovable");
                  localStringBuilder = new StringBuilder();
                  localStringBuilder.append("[getStorageInfo] removeResult:");
                  localStringBuilder.append(localObject2);
                  FLogger.d("SDCardDetector_StorageManager", localStringBuilder.toString());
                  if ((localObject2 != null) && ((localObject2 instanceof Boolean)))
                  {
                    if (((Boolean)localObject2).booleanValue()) {
                      break label376;
                    }
                    bool = true;
                    localStorageInfo.isInternal = bool;
                    localObject2 = new StringBuilder();
                    ((StringBuilder)localObject2).append("[getStorageInfo] info:");
                    ((StringBuilder)localObject2).append(localStorageInfo);
                    FLogger.d("SDCardDetector_StorageManager", ((StringBuilder)localObject2).toString());
                    localArrayList.add(localStorageInfo);
                  }
                }
              }
              i += 1;
              continue;
            }
          }
          return localArrayList;
        }
      }
      catch (Exception paramContext)
      {
        paramContext.printStackTrace();
      }
      paramContext = new StorageInfo();
      paramContext.path = FileUtilsF.getSDcardDir().getAbsolutePath();
      paramContext.isInternal = true;
      localArrayList.add(paramContext);
      return localArrayList;
      label376:
      boolean bool = false;
    }
  }
  
  public static StorageManager getStorageManager(Context paramContext)
  {
    if (paramContext == null) {
      return null;
    }
    try
    {
      paramContext = (StorageManager)paramContext.getSystemService("storage");
      return paramContext;
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
    }
    return null;
  }
  
  public static boolean isMounted(String paramString, Context paramContext)
  {
    if (Integer.parseInt(Build.VERSION.SDK) >= 9)
    {
      if (paramContext == null) {
        return false;
      }
      try
      {
        paramContext = getStorageManager(paramContext);
        if (paramContext != null)
        {
          paramString = (String)ReflectionUtils.invokeInstance(paramContext, "getVolumeState", new Class[] { String.class }, new Object[] { paramString });
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
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\SDCardDetector_StorageManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */