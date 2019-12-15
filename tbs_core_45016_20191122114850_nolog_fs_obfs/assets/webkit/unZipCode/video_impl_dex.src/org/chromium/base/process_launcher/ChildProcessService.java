package org.chromium.base.process_launcher;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.util.SparseArray;
import java.util.List;
import java.util.concurrent.Semaphore;
import javax.annotation.concurrent.GuardedBy;
import org.chromium.base.CommandLine;
import org.chromium.base.ContextUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("base::android")
@MainDex
public abstract class ChildProcessService
  extends Service
{
  private static boolean jdField_b_of_type_Boolean;
  @GuardedBy("mBinderLock")
  private int jdField_a_of_type_Int;
  private final Object jdField_a_of_type_JavaLangObject = new Object();
  private Thread jdField_a_of_type_JavaLangThread;
  private final Semaphore jdField_a_of_type_JavaUtilConcurrentSemaphore = new Semaphore(1);
  private final ChildProcessServiceDelegate jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessServiceDelegate;
  private final IChildProcessService.Stub jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService$Stub = new IChildProcessService.Stub()
  {
    public boolean bindToCaller()
    {
      if ((!jdField_a_of_type_Boolean) && (!ChildProcessService.a(ChildProcessService.this))) {
        throw new AssertionError();
      }
      if ((!jdField_a_of_type_Boolean) && (!ChildProcessService.b(ChildProcessService.this))) {
        throw new AssertionError();
      }
      synchronized (ChildProcessService.a(ChildProcessService.this))
      {
        int i = Binder.getCallingPid();
        if (ChildProcessService.a(ChildProcessService.this) == 0)
        {
          ChildProcessService.a(ChildProcessService.this, i);
        }
        else if (ChildProcessService.a(ChildProcessService.this) != i)
        {
          Log.e("ChildProcessService", "Service is already bound by pid %d, cannot bind for pid %d", new Object[] { Integer.valueOf(ChildProcessService.a(ChildProcessService.this)), Integer.valueOf(i) });
          return false;
        }
        return true;
      }
    }
    
    public void crashIntentionallyForTesting()
    {
      if ((!jdField_a_of_type_Boolean) && (!ChildProcessService.b(ChildProcessService.this))) {
        throw new AssertionError();
      }
      Process.killProcess(Process.myPid());
    }
    
    public void setupConnection(Bundle paramAnonymousBundle, ICallbackInt paramAnonymousICallbackInt, List<IBinder> paramAnonymousList)
      throws RemoteException
    {
      if ((!jdField_a_of_type_Boolean) && (!ChildProcessService.b(ChildProcessService.this))) {
        throw new AssertionError();
      }
      synchronized (ChildProcessService.a(ChildProcessService.this))
      {
        if ((ChildProcessService.a(ChildProcessService.this)) && (ChildProcessService.a(ChildProcessService.this) == 0))
        {
          Log.e("ChildProcessService", "Service has not been bound with bindToCaller()", new Object[0]);
          paramAnonymousICallbackInt.call(-1);
          return;
        }
        paramAnonymousICallbackInt.call(Process.myPid());
        ChildProcessService.a(ChildProcessService.this, paramAnonymousBundle, paramAnonymousList);
        return;
      }
    }
  };
  private String[] jdField_a_of_type_ArrayOfJavaLangString;
  private FileDescriptorInfo[] jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherFileDescriptorInfo;
  private final Object jdField_b_of_type_JavaLangObject = new Object();
  private boolean c;
  @GuardedBy("mLibraryInitializedLock")
  private boolean d;
  private boolean e;
  
  public ChildProcessService(ChildProcessServiceDelegate paramChildProcessServiceDelegate)
  {
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessServiceDelegate = paramChildProcessServiceDelegate;
  }
  
  private void a(Bundle paramBundle, List<IBinder> paramList)
  {
    paramBundle.setClassLoader(getApplicationContext().getClassLoader());
    synchronized (this.jdField_a_of_type_JavaLangThread)
    {
      if (this.jdField_a_of_type_ArrayOfJavaLangString == null)
      {
        this.jdField_a_of_type_ArrayOfJavaLangString = paramBundle.getStringArray("org.chromium.base.process_launcher.extra.command_line");
        this.jdField_a_of_type_JavaLangThread.notifyAll();
      }
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_ArrayOfJavaLangString == null)) {
        throw new AssertionError();
      }
      Parcelable[] arrayOfParcelable = paramBundle.getParcelableArray("org.chromium.base.process_launcher.extra.extraFiles");
      if (arrayOfParcelable != null)
      {
        this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherFileDescriptorInfo = new FileDescriptorInfo[arrayOfParcelable.length];
        System.arraycopy(arrayOfParcelable, 0, this.jdField_a_of_type_ArrayOfOrgChromiumBaseProcess_launcherFileDescriptorInfo, 0, arrayOfParcelable.length);
      }
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessServiceDelegate.onConnectionSetup(paramBundle, paramList);
      this.jdField_a_of_type_JavaLangThread.notifyAll();
      return;
    }
  }
  
  private static native void nativeExitChildProcess();
  
  private static native void nativeRegisterFileDescriptors(String[] paramArrayOfString, int[] paramArrayOfInt1, int[] paramArrayOfInt2, long[] paramArrayOfLong1, long[] paramArrayOfLong2);
  
  public IBinder onBind(Intent paramIntent)
  {
    if ((!jdField_a_of_type_Boolean) && (this.e)) {
      throw new AssertionError();
    }
    stopSelf();
    this.c = paramIntent.getBooleanExtra("org.chromium.base.process_launcher.extra.bind_to_caller", false);
    this.e = true;
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessServiceDelegate.onServiceBound(paramIntent);
    new Handler(Looper.getMainLooper()).post(new Runnable()
    {
      public void run()
      {
        ChildProcessService.a(ChildProcessService.this).preloadNativeLibrary(ChildProcessService.this.getApplicationContext());
      }
    });
    return this.jdField_a_of_type_OrgChromiumBaseProcess_launcherIChildProcessService$Stub;
  }
  
  public void onCreate()
  {
    super.onCreate();
    if (!jdField_b_of_type_Boolean)
    {
      jdField_b_of_type_Boolean = true;
      ContextUtils.initApplicationContext(getApplicationContext());
      this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessServiceDelegate.onServiceCreated();
      this.jdField_a_of_type_JavaLangThread = new Thread(new Runnable()
      {
        public void run()
        {
          for (;;)
          {
            int i;
            String[] arrayOfString;
            try
            {
              synchronized (ChildProcessService.a(ChildProcessService.this))
              {
                if (ChildProcessService.a(ChildProcessService.this) == null)
                {
                  ChildProcessService.a(ChildProcessService.this).wait();
                  continue;
                }
                if ((!jdField_a_of_type_Boolean) && (!ChildProcessService.b(ChildProcessService.this))) {
                  throw new AssertionError();
                }
                CommandLine.init(ChildProcessService.a(ChildProcessService.this));
                if (CommandLine.getInstance().hasSwitch("renderer-wait-for-java-debugger")) {
                  Debug.waitForDebugger();
                }
                i = 0;
                boolean bool;
                try
                {
                  bool = ChildProcessService.a(ChildProcessService.this).loadNativeLibrary(ChildProcessService.this.getApplicationContext());
                }
                catch (Exception localException)
                {
                  Log.e("ChildProcessService", "Failed to load native library.", new Object[] { localException });
                  bool = false;
                }
                if (!bool) {
                  System.exit(-1);
                }
                synchronized (ChildProcessService.b(ChildProcessService.this))
                {
                  ChildProcessService.a(ChildProcessService.this, true);
                  ChildProcessService.b(ChildProcessService.this).notifyAll();
                  synchronized (ChildProcessService.a(ChildProcessService.this))
                  {
                    ChildProcessService.a(ChildProcessService.this).notifyAll();
                    if (ChildProcessService.a(ChildProcessService.this) == null)
                    {
                      ChildProcessService.a(ChildProcessService.this).wait();
                      continue;
                    }
                    SparseArray localSparseArray = ChildProcessService.a(ChildProcessService.this).getFileDescriptorsIdsToKeys();
                    int[] arrayOfInt1 = new int[ChildProcessService.a(ChildProcessService.this).length];
                    arrayOfString = new String[ChildProcessService.a(ChildProcessService.this).length];
                    int[] arrayOfInt2 = new int[ChildProcessService.a(ChildProcessService.this).length];
                    long[] arrayOfLong1 = new long[ChildProcessService.a(ChildProcessService.this).length];
                    long[] arrayOfLong2 = new long[ChildProcessService.a(ChildProcessService.this).length];
                    if (i < ChildProcessService.a(ChildProcessService.this).length)
                    {
                      FileDescriptorInfo localFileDescriptorInfo = ChildProcessService.a(ChildProcessService.this)[i];
                      if (localSparseArray == null) {
                        break label457;
                      }
                      ??? = (String)localSparseArray.get(localFileDescriptorInfo.id);
                      break label459;
                      arrayOfInt1[i] = localFileDescriptorInfo.id;
                      arrayOfInt2[i] = localFileDescriptorInfo.fd.detachFd();
                      arrayOfLong1[i] = localFileDescriptorInfo.offset;
                      arrayOfLong2[i] = localFileDescriptorInfo.size;
                      i += 1;
                      continue;
                    }
                    ChildProcessService.a(arrayOfString, arrayOfInt1, arrayOfInt2, arrayOfLong1, arrayOfLong2);
                    ChildProcessService.a(ChildProcessService.this).onBeforeMain();
                    if (ChildProcessService.a(ChildProcessService.this).tryAcquire())
                    {
                      ChildProcessService.a(ChildProcessService.this).runMain();
                      ChildProcessService.a();
                      return;
                    }
                  }
                }
              }
              return;
            }
            catch (InterruptedException localInterruptedException)
            {
              return;
            }
            label457:
            Object localObject2 = null;
            label459:
            if (localObject2 != null) {
              arrayOfString[i] = localObject2;
            }
          }
        }
      }, "ChildProcessMain");
      this.jdField_a_of_type_JavaLangThread.start();
      return;
    }
    throw new RuntimeException("Illegal child process reuse.");
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    if (this.jdField_a_of_type_JavaUtilConcurrentSemaphore.tryAcquire())
    {
      System.exit(0);
      return;
    }
    try
    {
      synchronized (this.jdField_b_of_type_JavaLangObject)
      {
        if (!this.d) {
          this.jdField_b_of_type_JavaLangObject.wait();
        }
      }
    }
    catch (InterruptedException localInterruptedException)
    {
      for (;;) {}
    }
    this.jdField_a_of_type_OrgChromiumBaseProcess_launcherChildProcessServiceDelegate.onDestroy();
    return;
    throw ((Throwable)localObject2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\base\process_launcher\ChildProcessService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */