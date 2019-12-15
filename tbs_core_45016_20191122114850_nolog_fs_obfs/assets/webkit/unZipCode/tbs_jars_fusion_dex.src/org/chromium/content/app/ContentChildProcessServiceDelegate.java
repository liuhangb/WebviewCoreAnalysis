package org.chromium.content.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.SparseArray;
import android.view.Surface;
import java.util.List;
import org.chromium.base.CommandLine;
import org.chromium.base.JNIUtils;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;
import org.chromium.base.library_loader.LibraryLoader;
import org.chromium.base.library_loader.Linker;
import org.chromium.base.library_loader.ProcessInitException;
import org.chromium.base.process_launcher.ChildProcessServiceDelegate;
import org.chromium.content.browser.ChildProcessCreationParams;
import org.chromium.content.common.IGpuProcessCallback;
import org.chromium.content.common.IGpuProcessCallback.Stub;
import org.chromium.content.common.SurfaceWrapper;
import org.chromium.content_public.common.ContentProcessInfo;

@JNINamespace("content")
@MainDex
public class ContentChildProcessServiceDelegate
  implements ChildProcessServiceDelegate
{
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private SparseArray<String> jdField_a_of_type_AndroidUtilSparseArray;
  private ChromiumLinkerParams jdField_a_of_type_OrgChromiumContentAppChromiumLinkerParams;
  private IGpuProcessCallback jdField_a_of_type_OrgChromiumContentCommonIGpuProcessCallback;
  private int b;
  
  public ContentChildProcessServiceDelegate()
  {
    KillChildUncaughtExceptionHandler.a();
  }
  
  private Linker a()
  {
    if (Linker.areTestsEnabled())
    {
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumContentAppChromiumLinkerParams == null)) {
        throw new AssertionError();
      }
      Linker.setupForTesting(this.jdField_a_of_type_OrgChromiumContentAppChromiumLinkerParams.jdField_a_of_type_JavaLangString);
    }
    return Linker.getInstance();
  }
  
  /* Error */
  @CalledByNative
  private void forwardSurfaceTextureForSurfaceRequest(org.chromium.base.UnguessableToken paramUnguessableToken, android.graphics.SurfaceTexture paramSurfaceTexture)
  {
    // Byte code:
    //   0: aload_0
    //   1: getfield 67	org/chromium/content/app/ContentChildProcessServiceDelegate:jdField_a_of_type_OrgChromiumContentCommonIGpuProcessCallback	Lorg/chromium/content/common/IGpuProcessCallback;
    //   4: ifnonnull +15 -> 19
    //   7: ldc 69
    //   9: ldc 71
    //   11: iconst_0
    //   12: anewarray 4	java/lang/Object
    //   15: invokestatic 77	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   18: return
    //   19: new 79	android/view/Surface
    //   22: dup
    //   23: aload_2
    //   24: invokespecial 82	android/view/Surface:<init>	(Landroid/graphics/SurfaceTexture;)V
    //   27: astore_2
    //   28: aload_0
    //   29: getfield 67	org/chromium/content/app/ContentChildProcessServiceDelegate:jdField_a_of_type_OrgChromiumContentCommonIGpuProcessCallback	Lorg/chromium/content/common/IGpuProcessCallback;
    //   32: aload_1
    //   33: aload_2
    //   34: invokeinterface 88 3 0
    //   39: aload_2
    //   40: invokevirtual 91	android/view/Surface:release	()V
    //   43: return
    //   44: astore_1
    //   45: goto +24 -> 69
    //   48: astore_1
    //   49: ldc 69
    //   51: ldc 93
    //   53: iconst_1
    //   54: anewarray 4	java/lang/Object
    //   57: dup
    //   58: iconst_0
    //   59: aload_1
    //   60: aastore
    //   61: invokestatic 77	org/chromium/base/Log:e	(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/Object;)V
    //   64: aload_2
    //   65: invokevirtual 91	android/view/Surface:release	()V
    //   68: return
    //   69: aload_2
    //   70: invokevirtual 91	android/view/Surface:release	()V
    //   73: aload_1
    //   74: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	75	0	this	ContentChildProcessServiceDelegate
    //   0	75	1	paramUnguessableToken	org.chromium.base.UnguessableToken
    //   0	75	2	paramSurfaceTexture	android.graphics.SurfaceTexture
    // Exception table:
    //   from	to	target	type
    //   28	39	44	finally
    //   49	64	44	finally
    //   28	39	48	android/os/RemoteException
  }
  
  @CalledByNative
  private Surface getViewSurface(int paramInt)
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumContentCommonIGpuProcessCallback;
    Surface localSurface = null;
    if (localObject == null)
    {
      Log.e("ContentCPSDelegate", "No callback interface has been provided.", new Object[0]);
      return null;
    }
    try
    {
      localObject = ((IGpuProcessCallback)localObject).getViewSurface(paramInt);
      if (localObject != null) {
        localSurface = ((SurfaceWrapper)localObject).a();
      }
      return localSurface;
    }
    catch (RemoteException localRemoteException)
    {
      Log.e("ContentCPSDelegate", "Unable to call getViewSurface: %s", new Object[] { localRemoteException });
    }
    return null;
  }
  
  private native void nativeInitChildProcess(int paramInt, long paramLong);
  
  private native void nativeRetrieveFileDescriptorsIdsToKeys();
  
  private native void nativeShutdownMainThread();
  
  @CalledByNative
  private void setFileDescriptorsIdsToKeys(int[] paramArrayOfInt, String[] paramArrayOfString)
  {
    if ((!jdField_a_of_type_Boolean) && (paramArrayOfInt.length != paramArrayOfString.length)) {
      throw new AssertionError();
    }
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidUtilSparseArray != null)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
    int i = 0;
    while (i < paramArrayOfInt.length)
    {
      this.jdField_a_of_type_AndroidUtilSparseArray.put(paramArrayOfInt[i], paramArrayOfString[i]);
      i += 1;
    }
  }
  
  public SparseArray<String> getFileDescriptorsIdsToKeys()
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_AndroidUtilSparseArray == null)) {
      throw new AssertionError();
    }
    return this.jdField_a_of_type_AndroidUtilSparseArray;
  }
  
  public boolean loadNativeLibrary(Context paramContext)
  {
    if (CommandLine.getInstance().getSwitchValue("type") != null) {
      JNIUtils.enableSelectiveJniRegistration();
    }
    boolean bool1 = Linker.isUsed();
    LibraryLoader localLibraryLoader = null;
    Object localObject2;
    if (bool1)
    {
      if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_OrgChromiumContentAppChromiumLinkerParams == null)) {
        throw new AssertionError();
      }
      localObject1 = a();
      if (this.jdField_a_of_type_OrgChromiumContentAppChromiumLinkerParams.jdField_a_of_type_Boolean)
      {
        ((Linker)localObject1).initServiceProcess(this.jdField_a_of_type_OrgChromiumContentAppChromiumLinkerParams.jdField_a_of_type_Long);
        bool1 = true;
        localObject2 = localObject1;
      }
      else
      {
        ((Linker)localObject1).disableSharedRelros();
        bool1 = false;
        localObject2 = localObject1;
      }
    }
    else
    {
      localObject2 = null;
      bool1 = false;
    }
    Object localObject1 = localLibraryLoader;
    int i;
    boolean bool2;
    try
    {
      localLibraryLoader = LibraryLoader.get(this.jdField_a_of_type_Int);
      localObject1 = localLibraryLoader;
      localLibraryLoader.loadNowOverrideApplicationContext(paramContext);
      localObject1 = localLibraryLoader;
      i = 1;
      bool2 = false;
    }
    catch (ProcessInitException localProcessInitException)
    {
      if (bool1)
      {
        i = 0;
        bool2 = true;
      }
      else
      {
        Log.e("ContentCPSDelegate", "Failed to load native library", new Object[] { localProcessInitException });
        i = 0;
        bool2 = false;
      }
    }
    int j = i;
    if (i == 0)
    {
      j = i;
      if (localObject1 != null)
      {
        j = i;
        if (bool1)
        {
          ((Linker)localObject2).disableSharedRelros();
          try
          {
            ((LibraryLoader)localObject1).loadNowOverrideApplicationContext(paramContext);
            j = 1;
          }
          catch (ProcessInitException paramContext)
          {
            Log.e("ContentCPSDelegate", "Failed to load native library on retry", new Object[] { paramContext });
            j = i;
          }
        }
      }
    }
    if (j == 0) {
      return false;
    }
    ((LibraryLoader)localObject1).registerRendererProcessHistogram(bool1, bool2);
    try
    {
      ((LibraryLoader)localObject1).initialize();
      nativeRetrieveFileDescriptorsIdsToKeys();
      return true;
    }
    catch (ProcessInitException paramContext) {}
    return false;
  }
  
  public void onBeforeMain()
  {
    nativeInitChildProcess(this.b, this.jdField_a_of_type_Long);
  }
  
  public void onConnectionSetup(Bundle paramBundle, List<IBinder> paramList)
  {
    if ((paramList != null) && (!paramList.isEmpty())) {
      paramList = IGpuProcessCallback.Stub.asInterface((IBinder)paramList.get(0));
    } else {
      paramList = null;
    }
    this.jdField_a_of_type_OrgChromiumContentCommonIGpuProcessCallback = paramList;
    this.b = paramBundle.getInt("com.google.android.apps.chrome.extra.cpu_count");
    this.jdField_a_of_type_Long = paramBundle.getLong("com.google.android.apps.chrome.extra.cpu_features");
    if ((!jdField_a_of_type_Boolean) && (this.b <= 0)) {
      throw new AssertionError();
    }
    paramBundle = paramBundle.getBundle("org.chromium.base.android.linker.shared_relros");
    if (paramBundle != null) {
      a().useSharedRelros(paramBundle);
    }
  }
  
  public void onDestroy()
  {
    nativeShutdownMainThread();
  }
  
  public void onServiceBound(Intent paramIntent)
  {
    this.jdField_a_of_type_OrgChromiumContentAppChromiumLinkerParams = ChromiumLinkerParams.a(paramIntent.getExtras());
    this.jdField_a_of_type_Int = ChildProcessCreationParams.getLibraryProcessType(paramIntent.getExtras());
  }
  
  public void onServiceCreated()
  {
    ContentProcessInfo.a(true);
  }
  
  public void preloadNativeLibrary(Context paramContext)
  {
    try
    {
      LibraryLoader.get(this.jdField_a_of_type_Int).preloadNowOverrideApplicationContext(paramContext);
      return;
    }
    catch (ProcessInitException paramContext) {}
  }
  
  public void runMain()
  {
    ContentMain.start();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\app\ContentChildProcessServiceDelegate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */