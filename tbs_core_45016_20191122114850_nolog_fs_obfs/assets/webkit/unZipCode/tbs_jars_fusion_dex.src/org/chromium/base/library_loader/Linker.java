package org.chromium.base.library_loader;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map.Entry;
import java.util.Set;
import org.chromium.base.Log;
import org.chromium.base.annotations.AccessedByNative;

public abstract class Linker
{
  public static final int BROWSER_SHARED_RELRO_CONFIG = 1;
  public static final int BROWSER_SHARED_RELRO_CONFIG_ALWAYS = 2;
  public static final int BROWSER_SHARED_RELRO_CONFIG_LOW_RAM_ONLY = 1;
  public static final int BROWSER_SHARED_RELRO_CONFIG_NEVER = 0;
  public static final String EXTRA_LINKER_SHARED_RELROS = "org.chromium.base.android.linker.shared_relros";
  public static final int MEMORY_DEVICE_CONFIG_INIT = 0;
  public static final int MEMORY_DEVICE_CONFIG_LOW = 1;
  public static final int MEMORY_DEVICE_CONFIG_NORMAL = 2;
  private static Linker jdField_a_of_type_OrgChromiumBaseLibrary_loaderLinker;
  private static Object b = new Object();
  protected int a;
  protected final Object a;
  private String jdField_a_of_type_JavaLangString;
  
  protected Linker()
  {
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_JavaLangObject = new Object();
  }
  
  protected static void a()
  {
    LibraryLoader.setEnvForNative();
    System.loadLibrary("chromium_android_linker");
  }
  
  private static void a(boolean paramBoolean)
  {
    if (paramBoolean) {
      return;
    }
    throw new AssertionError();
  }
  
  public static boolean areTestsEnabled()
  {
    return NativeLibraries.sEnableLinkerTests;
  }
  
  private static void b()
  {
    if (NativeLibraries.sEnableLinkerTests) {
      return;
    }
    throw new AssertionError("Testing method called in non-testing context");
  }
  
  public static final Linker getInstance()
  {
    synchronized (b)
    {
      if (jdField_a_of_type_OrgChromiumBaseLibrary_loaderLinker == null) {
        jdField_a_of_type_OrgChromiumBaseLibrary_loaderLinker = LegacyLinker.a();
      }
      Linker localLinker = jdField_a_of_type_OrgChromiumBaseLibrary_loaderLinker;
      return localLinker;
    }
  }
  
  public static boolean isInZipFile()
  {
    return NativeLibraries.sUseLibraryInZipFile;
  }
  
  public static boolean isUsed()
  {
    if (Build.VERSION.SDK_INT >= 24) {
      return false;
    }
    return NativeLibraries.sUseLinker;
  }
  
  private static native long nativeGetRandomBaseLoadAddress();
  
  public static final void setupForTesting(String paramString)
  {
    
    for (;;)
    {
      synchronized (b)
      {
        Object localObject2 = jdField_a_of_type_OrgChromiumBaseLibrary_loaderLinker;
        boolean bool2 = true;
        bool1 = true;
        if (localObject2 == null)
        {
          b();
          if (jdField_a_of_type_OrgChromiumBaseLibrary_loaderLinker == null)
          {
            a(bool1);
            jdField_a_of_type_OrgChromiumBaseLibrary_loaderLinker = LegacyLinker.a();
            jdField_a_of_type_OrgChromiumBaseLibrary_loaderLinker.setTestRunnerClassNameForTesting(paramString);
          }
        }
        else
        {
          localObject2 = jdField_a_of_type_OrgChromiumBaseLibrary_loaderLinker.getTestRunnerClassNameForTesting();
          if (paramString == null)
          {
            if (localObject2 != null) {
              break label106;
            }
            bool1 = bool2;
            a(bool1);
          }
          else
          {
            a(((String)localObject2).equals(paramString));
          }
          return;
        }
      }
      boolean bool1 = false;
      continue;
      label106:
      bool1 = false;
    }
  }
  
  protected long a()
  {
    return nativeGetRandomBaseLoadAddress();
  }
  
  protected Bundle a(HashMap<String, LibInfo> paramHashMap)
  {
    Bundle localBundle = new Bundle(paramHashMap.size());
    paramHashMap = paramHashMap.entrySet().iterator();
    while (paramHashMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramHashMap.next();
      localBundle.putParcelable((String)localEntry.getKey(), (Parcelable)localEntry.getValue());
    }
    return localBundle;
  }
  
  protected HashMap<String, LibInfo> a(Bundle paramBundle)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramBundle.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      localHashMap.put(str, (LibInfo)paramBundle.getParcelable(str));
    }
    return localHashMap;
  }
  
  protected void a(HashMap<String, LibInfo> paramHashMap)
  {
    paramHashMap = paramHashMap.entrySet().iterator();
    while (paramHashMap.hasNext()) {
      ((LibInfo)((Map.Entry)paramHashMap.next()).getValue()).close();
    }
  }
  
  public abstract void disableSharedRelros();
  
  public abstract void finishLibraryLoad();
  
  public abstract long getBaseLoadAddress();
  
  public abstract Bundle getSharedRelros();
  
  public final String getTestRunnerClassNameForTesting()
  {
    
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      String str = this.jdField_a_of_type_JavaLangString;
      return str;
    }
  }
  
  public abstract void initServiceProcess(long paramLong);
  
  public boolean isChromiumLinkerLibrary(String paramString)
  {
    return paramString.equals("chromium_android_linker");
  }
  
  public abstract boolean isUsingBrowserSharedRelros();
  
  public void loadLibrary(String paramString1, String paramString2)
  {
    loadLibraryImpl(paramString1, paramString2, true);
  }
  
  abstract void loadLibraryImpl(String paramString1, String paramString2, boolean paramBoolean);
  
  public void loadLibraryNoFixedAddress(String paramString1, String paramString2)
  {
    loadLibraryImpl(paramString1, paramString2, false);
  }
  
  public abstract void prepareLibraryLoad();
  
  protected final void runTestRunnerClassForTesting(int paramInt, boolean paramBoolean)
  {
    
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (this.jdField_a_of_type_JavaLangString == null)
      {
        Log.wtf("LibraryLoader", "Linker runtime tests not set up for this process", new Object[0]);
        a(false);
      }
      Object localObject1 = null;
      try
      {
        TestRunner localTestRunner = (TestRunner)Class.forName(this.jdField_a_of_type_JavaLangString).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        localObject1 = localTestRunner;
      }
      catch (Exception localException)
      {
        Log.wtf("LibraryLoader", "Could not instantiate test runner class by name", new Object[] { localException });
        a(false);
      }
      if (!((TestRunner)localObject1).runChecks(paramInt, paramBoolean))
      {
        Log.wtf("LibraryLoader", "Linker runtime tests failed in this process", new Object[0]);
        a(false);
      }
      return;
    }
  }
  
  public final void setMemoryDeviceConfigForTesting(int paramInt)
  {
    b();
    boolean bool2 = false;
    if ((paramInt != 1) && (paramInt != 2)) {
      bool1 = false;
    } else {
      bool1 = true;
    }
    a(bool1);
    Object localObject1 = this.jdField_a_of_type_JavaLangObject;
    boolean bool1 = bool2;
    try
    {
      if (this.jdField_a_of_type_Int == 0) {
        bool1 = true;
      }
      a(bool1);
      this.jdField_a_of_type_Int = paramInt;
      return;
    }
    finally {}
  }
  
  public final void setTestRunnerClassNameForTesting(String paramString)
  {
    
    for (;;)
    {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        if (this.jdField_a_of_type_JavaLangString == null)
        {
          bool = true;
          a(bool);
          this.jdField_a_of_type_JavaLangString = paramString;
          return;
        }
      }
      boolean bool = false;
    }
  }
  
  public abstract void useSharedRelros(Bundle paramBundle);
  
  public static class LibInfo
    implements Parcelable
  {
    public static final Parcelable.Creator<LibInfo> CREATOR = new Parcelable.Creator()
    {
      public Linker.LibInfo createFromParcel(Parcel paramAnonymousParcel)
      {
        return new Linker.LibInfo(paramAnonymousParcel);
      }
      
      public Linker.LibInfo[] newArray(int paramAnonymousInt)
      {
        return new Linker.LibInfo[paramAnonymousInt];
      }
    };
    @AccessedByNative
    public long mLoadAddress;
    @AccessedByNative
    public long mLoadSize;
    @AccessedByNative
    public int mRelroFd;
    @AccessedByNative
    public long mRelroSize;
    @AccessedByNative
    public long mRelroStart;
    
    public LibInfo()
    {
      this.mLoadAddress = 0L;
      this.mLoadSize = 0L;
      this.mRelroStart = 0L;
      this.mRelroSize = 0L;
      this.mRelroFd = -1;
    }
    
    public LibInfo(Parcel paramParcel)
    {
      this.mLoadAddress = paramParcel.readLong();
      this.mLoadSize = paramParcel.readLong();
      this.mRelroStart = paramParcel.readLong();
      this.mRelroSize = paramParcel.readLong();
      paramParcel = (ParcelFileDescriptor)ParcelFileDescriptor.CREATOR.createFromParcel(paramParcel);
      int i;
      if (paramParcel == null) {
        i = -1;
      } else {
        i = paramParcel.detachFd();
      }
      this.mRelroFd = i;
    }
    
    public void close()
    {
      int i = this.mRelroFd;
      if (i >= 0) {}
      try
      {
        ParcelFileDescriptor.adoptFd(i).close();
        this.mRelroFd = -1;
        return;
      }
      catch (IOException localIOException)
      {
        for (;;) {}
      }
    }
    
    public int describeContents()
    {
      return 1;
    }
    
    public String toString()
    {
      return String.format(Locale.US, "[load=0x%x-0x%x relro=0x%x-0x%x fd=%d]", new Object[] { Long.valueOf(this.mLoadAddress), Long.valueOf(this.mLoadAddress + this.mLoadSize), Long.valueOf(this.mRelroStart), Long.valueOf(this.mRelroStart + this.mRelroSize), Integer.valueOf(this.mRelroFd) });
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      if (this.mRelroFd >= 0)
      {
        paramParcel.writeLong(this.mLoadAddress);
        paramParcel.writeLong(this.mLoadSize);
        paramParcel.writeLong(this.mRelroStart);
        paramParcel.writeLong(this.mRelroSize);
        try
        {
          ParcelFileDescriptor localParcelFileDescriptor = ParcelFileDescriptor.fromFd(this.mRelroFd);
          localParcelFileDescriptor.writeToParcel(paramParcel, 0);
          localParcelFileDescriptor.close();
          return;
        }
        catch (IOException paramParcel)
        {
          Log.e("LibraryLoader", "Can't write LibInfo file descriptor to parcel", new Object[] { paramParcel });
        }
      }
    }
  }
  
  public static abstract interface TestRunner
  {
    public abstract boolean runChecks(int paramInt, boolean paramBoolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\library_loader\Linker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */