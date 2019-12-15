package org.chromium.base.library_loader;

import android.os.Bundle;
import android.os.Parcel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import org.chromium.base.Log;
import org.chromium.base.SysUtils;
import org.chromium.base.ThreadUtils;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.MainDex;

@MainDex
class LegacyLinker
  extends Linker
{
  private long jdField_a_of_type_Long = -1L;
  private Bundle jdField_a_of_type_AndroidOsBundle;
  private HashMap<String, Linker.LibInfo> jdField_a_of_type_JavaUtilHashMap;
  private long jdField_b_of_type_Long = -1L;
  private boolean jdField_b_of_type_Boolean;
  private boolean c = true;
  private boolean d;
  private boolean e;
  private boolean f;
  
  static Linker a()
  {
    return new LegacyLinker();
  }
  
  private void a(Bundle paramBundle)
  {
    if ((!jdField_a_of_type_Boolean) && (!Thread.holdsLock(this.jdField_a_of_type_JavaLangObject))) {
      throw new AssertionError();
    }
    if (paramBundle == null) {
      return;
    }
    if (this.jdField_a_of_type_JavaUtilHashMap == null) {
      return;
    }
    paramBundle = a(paramBundle);
    Iterator localIterator = paramBundle.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      nativeUseSharedRelro((String)localEntry.getKey(), (Linker.LibInfo)localEntry.getValue());
    }
    if (!this.c) {
      a(paramBundle);
    }
  }
  
  private void b()
  {
    if ((!jdField_a_of_type_Boolean) && (!Thread.holdsLock(this.jdField_a_of_type_JavaLangObject))) {
      throw new AssertionError();
    }
    if (!this.jdField_b_of_type_Boolean)
    {
      if (!NativeLibraries.sUseLinker) {
        return;
      }
      a();
      if (this.jdField_a_of_type_Int == 0) {
        if (SysUtils.isLowEndDevice()) {
          this.jdField_a_of_type_Int = 1;
        } else {
          this.jdField_a_of_type_Int = 2;
        }
      }
      switch (1)
      {
      default: 
        Log.wtf("LibraryLoader", "FATAL: illegal shared RELRO config", new Object[0]);
        throw new AssertionError();
      case 2: 
        this.e = true;
        break;
      case 1: 
        if (this.jdField_a_of_type_Int == 1) {
          this.e = true;
        } else {
          this.e = false;
        }
        break;
      case 0: 
        this.e = false;
      }
      this.jdField_b_of_type_Boolean = true;
      return;
    }
  }
  
  private void c()
  {
    if ((!jdField_a_of_type_Boolean) && (!Thread.holdsLock(this.jdField_a_of_type_JavaLangObject))) {
      throw new AssertionError();
    }
    if (this.jdField_a_of_type_Long == -1L)
    {
      this.jdField_a_of_type_Long = a();
      long l = this.jdField_a_of_type_Long;
      this.jdField_b_of_type_Long = l;
      if (l == 0L)
      {
        this.e = false;
        this.d = false;
      }
    }
  }
  
  private static native boolean nativeCreateSharedRelro(String paramString, long paramLong, Linker.LibInfo paramLibInfo);
  
  private static native boolean nativeLoadLibrary(String paramString, long paramLong, Linker.LibInfo paramLibInfo);
  
  private static native boolean nativeLoadLibraryInZipFile(String paramString1, String paramString2, long paramLong, Linker.LibInfo paramLibInfo);
  
  private static native void nativeRunCallbackOnUiThread(long paramLong);
  
  private static native boolean nativeUseSharedRelro(String paramString, Linker.LibInfo paramLibInfo);
  
  @CalledByNative
  public static void postCallbackOnMainThread(long paramLong)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        LegacyLinker.a(this.a);
      }
    });
  }
  
  public void disableSharedRelros()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      b();
      this.c = false;
      this.d = false;
      this.e = false;
      return;
    }
  }
  
  public void finishLibraryLoad()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      b();
      if (this.jdField_a_of_type_JavaUtilHashMap != null)
      {
        if (this.c)
        {
          this.jdField_a_of_type_AndroidOsBundle = a(this.jdField_a_of_type_JavaUtilHashMap);
          if (this.e) {
            a(this.jdField_a_of_type_AndroidOsBundle);
          }
        }
        if (this.d)
        {
          if ((!jdField_a_of_type_Boolean) && (this.c)) {
            throw new AssertionError();
          }
          for (;;)
          {
            Bundle localBundle = this.jdField_a_of_type_AndroidOsBundle;
            if (localBundle != null) {
              break;
            }
            try
            {
              this.jdField_a_of_type_JavaLangObject.wait();
            }
            catch (InterruptedException localInterruptedException)
            {
              for (;;) {}
            }
            Thread.currentThread().interrupt();
          }
          a(this.jdField_a_of_type_AndroidOsBundle);
          this.jdField_a_of_type_AndroidOsBundle.clear();
          this.jdField_a_of_type_AndroidOsBundle = null;
        }
      }
      if (NativeLibraries.sEnableLinkerTests) {
        runTestRunnerClassForTesting(this.jdField_a_of_type_Int, this.c);
      }
      return;
    }
  }
  
  public long getBaseLoadAddress()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      b();
      if (!this.c) {
        return 0L;
      }
      c();
      long l = this.jdField_a_of_type_Long;
      return l;
    }
  }
  
  public Bundle getSharedRelros()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      if (!this.c) {
        return null;
      }
      Bundle localBundle = this.jdField_a_of_type_AndroidOsBundle;
      return localBundle;
    }
  }
  
  public void initServiceProcess(long paramLong)
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      b();
      this.c = false;
      this.e = false;
      this.d = true;
      this.jdField_a_of_type_Long = paramLong;
      this.jdField_b_of_type_Long = paramLong;
      return;
    }
  }
  
  public boolean isUsingBrowserSharedRelros()
  {
    for (;;)
    {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        b();
        if ((this.c) && (this.e))
        {
          bool = true;
          return bool;
        }
      }
      boolean bool = false;
    }
  }
  
  void loadLibraryImpl(String paramString1, String paramString2, boolean paramBoolean)
  {
    for (;;)
    {
      synchronized (this.jdField_a_of_type_JavaLangObject)
      {
        b();
        if ((!jdField_a_of_type_Boolean) && (!this.f)) {
          throw new AssertionError();
        }
        if (this.jdField_a_of_type_JavaUtilHashMap == null) {
          this.jdField_a_of_type_JavaUtilHashMap = new HashMap();
        }
        if (this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString2)) {
          return;
        }
        Object localObject2 = new Linker.LibInfo();
        if ((paramBoolean) && (((this.c) && (this.e)) || (this.d)))
        {
          l = this.jdField_b_of_type_Long;
          if (l > this.jdField_a_of_type_Long + 201326592L)
          {
            paramString1 = new StringBuilder();
            paramString1.append("Load address outside reservation, for: ");
            paramString1.append(paramString2);
            paramString1 = paramString1.toString();
            Log.e("LibraryLoader", paramString1, new Object[0]);
            throw new UnsatisfiedLinkError(paramString1);
          }
          if (paramString1 != null)
          {
            if (!nativeLoadLibraryInZipFile(paramString1, paramString2, l, (Linker.LibInfo)localObject2))
            {
              localObject2 = new StringBuilder();
              ((StringBuilder)localObject2).append("Unable to load library: ");
              ((StringBuilder)localObject2).append(paramString2);
              ((StringBuilder)localObject2).append(", in: ");
              ((StringBuilder)localObject2).append(paramString1);
              paramString1 = ((StringBuilder)localObject2).toString();
              Log.e("LibraryLoader", paramString1, new Object[0]);
              throw new UnsatisfiedLinkError(paramString1);
            }
          }
          else
          {
            if (!nativeLoadLibrary(paramString2, l, (Linker.LibInfo)localObject2)) {
              continue;
            }
            paramString1 = paramString2;
          }
          if (NativeLibraries.sEnableLinkerTests) {
            paramBoolean = this.c;
          }
          if (this.c) {
            nativeCreateSharedRelro(paramString1, this.jdField_b_of_type_Long, (Linker.LibInfo)localObject2);
          }
          if ((l != 0L) && (this.jdField_b_of_type_Long != 0L)) {
            this.jdField_b_of_type_Long = (((Linker.LibInfo)localObject2).mLoadAddress + ((Linker.LibInfo)localObject2).mLoadSize + 16777216L);
          }
          this.jdField_a_of_type_JavaUtilHashMap.put(paramString1, localObject2);
          return;
          paramString1 = new StringBuilder();
          paramString1.append("Unable to load library: ");
          paramString1.append(paramString2);
          paramString1 = paramString1.toString();
          Log.e("LibraryLoader", paramString1, new Object[0]);
          throw new UnsatisfiedLinkError(paramString1);
        }
      }
      long l = 0L;
    }
  }
  
  public void prepareLibraryLoad()
  {
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      b();
      this.f = true;
      if (this.c) {
        c();
      }
      return;
    }
  }
  
  public void useSharedRelros(Bundle paramBundle)
  {
    if (paramBundle != null)
    {
      paramBundle.setClassLoader(Linker.LibInfo.class.getClassLoader());
      ??? = new Bundle(Linker.LibInfo.class.getClassLoader());
      Parcel localParcel = Parcel.obtain();
      paramBundle.writeToParcel(localParcel, 0);
      localParcel.setDataPosition(0);
      ((Bundle)???).readFromParcel(localParcel);
      localParcel.recycle();
      paramBundle = (Bundle)???;
    }
    else
    {
      paramBundle = null;
    }
    synchronized (this.jdField_a_of_type_JavaLangObject)
    {
      this.jdField_a_of_type_AndroidOsBundle = paramBundle;
      this.jdField_a_of_type_JavaLangObject.notifyAll();
      return;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\library_loader\LegacyLinker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */