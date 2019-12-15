package org.chromium.tencent.base;

import android.content.Context;
import java.util.ArrayList;
import org.chromium.base.MemoryPressureListener;

public class TencentMemoryPressureListener
  extends MemoryPressureListener
{
  public static final int TRIM_MEMORY_ACCURATE = 100;
  public static final int TRIM_MEMORY_SPECIAL = 110;
  private static Helper sHelper = null;
  private static ArrayList<MemoryTrimCallback> sMemoryTrimCallbacks = new ArrayList();
  
  public static void ayncTrim(int[] paramArrayOfInt) {}
  
  private static Object[] collectMemoryTrimCallback()
  {
    for (;;)
    {
      synchronized (sMemoryTrimCallbacks)
      {
        if (sMemoryTrimCallbacks.size() > 0)
        {
          Object[] arrayOfObject = sMemoryTrimCallbacks.toArray();
          return arrayOfObject;
        }
      }
      Object localObject2 = null;
    }
  }
  
  public static void maybeNotifyMemoryPresure(int paramInt)
  {
    paramInt = memoryPressureLevel(paramInt);
    if (paramInt == 0) {
      return;
    }
    nativeOnMemoryPressure(paramInt);
  }
  
  public static int maybeNotifyMemoryPresureInDefaultOrder(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    return maybeNotifyMemoryPresureInOrder(paramInt1, null, paramInt2, paramBoolean);
  }
  
  public static int maybeNotifyMemoryPresureInOrder(int paramInt1, int[] paramArrayOfInt, int paramInt2, boolean paramBoolean)
  {
    paramInt1 = memoryPressureLevel(paramInt1);
    if (paramInt1 == 0) {
      return -1;
    }
    return nativeOnMemoryPressureInOrder(paramInt1, paramArrayOfInt, paramInt2, paramBoolean);
  }
  
  public static int memoryPressureLevel(int paramInt)
  {
    if (paramInt != 5)
    {
      if (paramInt != 10)
      {
        if (paramInt != 15)
        {
          if (paramInt != 20)
          {
            if (paramInt != 40)
            {
              if ((paramInt != 60) && (paramInt != 80))
              {
                if (paramInt != 100)
                {
                  if (paramInt != 110) {
                    return 0;
                  }
                  return 4;
                }
                return 3;
              }
              return 2;
            }
            return 1;
          }
          return 0;
        }
        return 2;
      }
      return 1;
    }
    return 0;
  }
  
  public static void onLowMemory(Context paramContext)
  {
    Object[] arrayOfObject = collectMemoryTrimCallback();
    if (arrayOfObject != null)
    {
      int i = 0;
      while (i < arrayOfObject.length)
      {
        Object localObject = arrayOfObject[i];
        if ((localObject instanceof MemoryTrimCallback)) {
          ((MemoryTrimCallback)localObject).onLowMemory(paramContext);
        }
        i += 1;
      }
    }
  }
  
  public static void onTrimMemory(Context paramContext, int paramInt)
  {
    Object[] arrayOfObject = collectMemoryTrimCallback();
    if (arrayOfObject != null)
    {
      int i = 0;
      while (i < arrayOfObject.length)
      {
        Object localObject = arrayOfObject[i];
        if ((localObject instanceof MemoryTrimCallback)) {
          ((MemoryTrimCallback)localObject).onTrimMemory(paramContext, paramInt);
        }
        i += 1;
      }
    }
  }
  
  public static void registerMemoryTrimCallback(MemoryTrimCallback paramMemoryTrimCallback)
  {
    synchronized (sMemoryTrimCallbacks)
    {
      sMemoryTrimCallbacks.add(paramMemoryTrimCallback);
      return;
    }
  }
  
  public static void setMemoryPressureHelper(Helper paramHelper)
  {
    sHelper = paramHelper;
  }
  
  public static void trimInOrder(int paramInt, int[] paramArrayOfInt)
  {
    Helper localHelper = sHelper;
    if (localHelper != null) {
      localHelper.trimInOrder(paramInt, paramArrayOfInt);
    }
  }
  
  public static void unregisterMemoryTrimCallback(MemoryTrimCallback paramMemoryTrimCallback)
  {
    synchronized (sMemoryTrimCallbacks)
    {
      sMemoryTrimCallbacks.remove(paramMemoryTrimCallback);
      return;
    }
  }
  
  public static abstract class Helper
  {
    public abstract void trimInOrder(int paramInt, int[] paramArrayOfInt);
  }
  
  public static abstract class MemoryTrimCallback
  {
    public abstract void onLowMemory(Context paramContext);
    
    public abstract void onTrimMemory(Context paramContext, int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\base\TencentMemoryPressureListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */