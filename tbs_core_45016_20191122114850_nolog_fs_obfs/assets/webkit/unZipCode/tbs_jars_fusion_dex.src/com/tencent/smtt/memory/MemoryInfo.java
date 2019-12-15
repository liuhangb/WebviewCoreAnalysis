package com.tencent.smtt.memory;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.os.Looper;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.smtt.webkit.ContextHolder;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.chromium.base.SysUtils;
import org.chromium.base.annotations.CalledByNative;

public class MemoryInfo
{
  public static final int TRIM_STATUS_DOING = 2;
  public static final int TRIM_STATUS_ERROR = -1;
  public static final int TRIM_STATUS_SUCCESS = 1;
  public static final int TRIM_STATUS_WAIT = 0;
  static int sAmountOfPhysicalMemoryKB = 0;
  private static int sThresholdMem = -1;
  
  @CalledByNative
  private static NativeMemInfo createNativeMemInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11)
  {
    return new NativeMemInfo(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramInt7, paramInt8, paramInt9, paramInt10, paramInt11);
  }
  
  public static int getAvailMem()
  {
    Object localObject = ContextHolder.getInstance().getApplicationContext();
    if (localObject == null) {
      return 0;
    }
    try
    {
      localObject = (ActivityManager)((Context)localObject).getSystemService("activity");
      ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
      ((ActivityManager)localObject).getMemoryInfo(localMemoryInfo);
      long l = localMemoryInfo.availMem / 1024L;
      return (int)l;
    }
    catch (Throwable localThrowable)
    {
      c.a(localThrowable);
    }
    return 0;
  }
  
  @CalledByNative
  private static int getCurAvailMem()
  {
    return getAvailMem();
  }
  
  @CalledByNative
  public static int getCurPss()
  {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      return getCurPssWithTimeout(100);
    }
    return getDebugMemInfo().getTotalPss();
  }
  
  @CalledByNative
  public static int getCurPssWithTimeout(int paramInt)
  {
    int i = d.a.a().b();
    if (i > 0) {
      return i;
    }
    return getDebugMemInfoWithTimeout(paramInt).getTotalPss();
  }
  
  @CalledByNative
  public static int getCurVss()
  {
    if (Looper.myLooper() == Looper.getMainLooper()) {
      return getCurVssWithTimeout(100);
    }
    return d.a.a().a();
  }
  
  @CalledByNative
  public static int getCurVssWithTimeout(int paramInt)
  {
    try
    {
      paramInt = ((Integer)BrowserExecutorSupplier.getInstance().postForTimeoutTasks(new Callable()
      {
        public Integer a()
          throws Exception
        {
          return Integer.valueOf(d.a.a().a());
        }
      }).get(paramInt, TimeUnit.MILLISECONDS)).intValue();
      return paramInt;
    }
    catch (Throwable localThrowable)
    {
      c.a(localThrowable);
    }
    return 0;
  }
  
  public static Debug.MemoryInfo getDebugMemInfo()
  {
    Debug.MemoryInfo localMemoryInfo = new Debug.MemoryInfo();
    Debug.getMemoryInfo(localMemoryInfo);
    return localMemoryInfo;
  }
  
  public static Debug.MemoryInfo getDebugMemInfoWithTimeout(int paramInt)
  {
    try
    {
      Debug.MemoryInfo localMemoryInfo = (Debug.MemoryInfo)BrowserExecutorSupplier.getInstance().postForTimeoutTasks(new Callable()
      {
        public Debug.MemoryInfo a()
          throws Exception
        {
          Debug.MemoryInfo localMemoryInfo = new Debug.MemoryInfo();
          Debug.getMemoryInfo(localMemoryInfo);
          return localMemoryInfo;
        }
      }).get(paramInt, TimeUnit.MILLISECONDS);
      return localMemoryInfo;
    }
    catch (Throwable localThrowable)
    {
      c.a(localThrowable);
    }
    return new Debug.MemoryInfo();
  }
  
  public static NativeMemInfo getNativeMemInfo()
  {
    return nativeGetMemInfo();
  }
  
  public static int getThresholdMem()
  {
    int i = sThresholdMem;
    if (i != -1) {
      return i;
    }
    Object localObject = ContextHolder.getInstance().getApplicationContext();
    if (localObject == null) {
      return 0;
    }
    try
    {
      localObject = (ActivityManager)((Context)localObject).getSystemService("activity");
      ActivityManager.MemoryInfo localMemoryInfo = new ActivityManager.MemoryInfo();
      ((ActivityManager)localObject).getMemoryInfo(localMemoryInfo);
      sThresholdMem = (int)(localMemoryInfo.threshold / 1024L);
      i = sThresholdMem;
      return i;
    }
    catch (Throwable localThrowable)
    {
      c.a(localThrowable);
    }
    return 0;
  }
  
  public static int getTotalRAMMemory()
  {
    int i = sAmountOfPhysicalMemoryKB;
    if (i > 0) {
      return i;
    }
    sAmountOfPhysicalMemoryKB = SysUtils.amountOfPhysicalMemoryKB();
    return sAmountOfPhysicalMemoryKB;
  }
  
  public static MemoryInfo info()
  {
    return new MemoryInfo();
  }
  
  private static native NativeMemInfo nativeGetMemInfo();
  
  private static native int nativeNeedTrimMemory();
  
  private static native void nativeUpdateBootupMemory(int paramInt1, int paramInt2);
  
  private static native void nativeUpdateMemInfoForReport();
  
  private static native void nativeUpdateMemLimit(int paramInt1, int paramInt2);
  
  private static native void nativeUpdateTrimLevel(int paramInt, boolean paramBoolean1, boolean paramBoolean2);
  
  private static native void nativeUpdateTrimMemory(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  private static native void nativeUpdateTrimStatus(int paramInt);
  
  private static native void nativeUpdateTrimThreshold(int paramInt1, int paramInt2, int paramInt3);
  
  public static int needTrimMemoryFromNative()
  {
    return nativeNeedTrimMemory();
  }
  
  @CalledByNative
  private static void onLargeMemAlloc(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onLargeMemAlloc: size = ");
    localStringBuilder.append(paramInt);
    c.a("meminfo", localStringBuilder.toString());
    MemoryChecker.check(31);
  }
  
  @CalledByNative
  private static void onOOMHandler(int paramInt, boolean paramBoolean)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onOOMHandler: dstSize = ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(", success = ");
    ((StringBuilder)localObject).append(paramBoolean);
    c.a("meminfo", ((StringBuilder)localObject).toString());
    MemoryChecker.trimVM(80);
    localObject = new HashMap();
    ((HashMap)localObject).put("dstSize", Integer.toString(paramInt));
    ((HashMap)localObject).put("success", Boolean.toString(paramBoolean));
    a.a("MTT_CORE_MEMORY_OOM_HANDLE", (HashMap)localObject);
  }
  
  public static void updateBootupMemory(int paramInt1, int paramInt2)
  {
    nativeUpdateBootupMemory(paramInt1, paramInt2);
  }
  
  public static void updateMemInfoForReport() {}
  
  public static void updateMemLimit(int paramInt1, int paramInt2)
  {
    nativeUpdateMemLimit(paramInt1, paramInt2);
  }
  
  public static void updateTrimLevel(int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    nativeUpdateTrimLevel(paramInt, paramBoolean1, paramBoolean2);
  }
  
  public static void updateTrimMemory(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    nativeUpdateTrimMemory(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5);
  }
  
  public static void updateTrimStatus(int paramInt)
  {
    nativeUpdateTrimStatus(paramInt);
  }
  
  public static void updateTrimThreshold(int paramInt1, int paramInt2, int paramInt3)
  {
    nativeUpdateTrimThreshold(paramInt1, paramInt2, paramInt3);
  }
  
  public static class NativeMemInfo
  {
    private int mCachedPages;
    private int mDeactiveNaviViews;
    private int mDiscardableMemLocked;
    private int mDiscardableMemPinned;
    private int mMallocAlloc;
    private int mNaviViews;
    private int mPartitionAlloc;
    private int mPrunedNaviViews;
    private int mSharedMemAlloc;
    private int mTextureAlloc;
    private int mTotalAlloc;
    private int mV8Alloc;
    
    protected NativeMemInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, int paramInt11)
    {
      this.mPartitionAlloc = (paramInt1 / 1024);
      this.mV8Alloc = (paramInt2 / 1024);
      this.mDiscardableMemLocked = (paramInt3 / 1024);
      this.mDiscardableMemPinned = (paramInt4 / 1024);
      this.mSharedMemAlloc = (paramInt5 / 1024);
      this.mTextureAlloc = (paramInt6 / 1024);
      this.mMallocAlloc = (paramInt7 / 1024);
      this.mTotalAlloc = ((int)((paramInt1 + paramInt2 + paramInt5 + paramInt6 + paramInt7) / 1024L));
      this.mCachedPages = paramInt8;
      this.mNaviViews = paramInt9;
      this.mDeactiveNaviViews = paramInt10;
      this.mPrunedNaviViews = paramInt11;
    }
    
    public int getCachedPages()
    {
      return this.mCachedPages;
    }
    
    public int getDeactiveNaviViews()
    {
      return this.mDeactiveNaviViews;
    }
    
    public int getMallocAlloc()
    {
      return this.mMallocAlloc;
    }
    
    public int getNaviViews()
    {
      return this.mNaviViews;
    }
    
    public int getPartitionAlloc()
    {
      return this.mPartitionAlloc;
    }
    
    public int getPrunedNaviViews()
    {
      return this.mPrunedNaviViews;
    }
    
    public int getSharedMemAlloc()
    {
      return this.mSharedMemAlloc;
    }
    
    public int getTextureAlloc()
    {
      return this.mTextureAlloc;
    }
    
    public int getTotalAlloc()
    {
      return this.mTotalAlloc;
    }
    
    public int getV8Alloc()
    {
      return this.mV8Alloc;
    }
    
    public int getdiscardableMemLocked()
    {
      return this.mDiscardableMemLocked;
    }
    
    public int getdiscardableMemPinned()
    {
      return this.mDiscardableMemPinned;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\memory\MemoryInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */