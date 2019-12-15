package com.tencent.smtt.aladdin;

import android.os.Handler;
import com.tencent.smtt.export.external.easel.interfaces.ILoader;
import com.tencent.smtt.export.external.easel.interfaces.ILoader.Delegate;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("aladdin")
class ResourceLoader
  implements ILoader.Delegate
{
  private long jdField_a_of_type_Long;
  private final Handler jdField_a_of_type_AndroidOsHandler = new Handler();
  private ILoader jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader;
  
  public ResourceLoader(ILoader paramILoader)
  {
    this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader = paramILoader;
  }
  
  private void a()
  {
    this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader = null;
    this.jdField_a_of_type_Long = 0L;
  }
  
  @CalledByNative
  private void cancel()
  {
    ILoader localILoader = this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader;
    if (localILoader != null)
    {
      localILoader.cancel();
      this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader.setDelegate(null);
    }
    a();
  }
  
  @CalledByNative
  private void loadAsynchronously(String paramString1, String paramString2)
  {
    ILoader localILoader = this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader;
    if (localILoader != null)
    {
      localILoader.setDelegate(this);
      this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader.load(paramString1, paramString2);
    }
  }
  
  @CalledByNative
  private byte[] loadSynchronously(String paramString1, String paramString2)
  {
    final ByteArrayOutputStream localByteArrayOutputStream;
    final AtomicBoolean localAtomicBoolean;
    final Object localObject;
    if (this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader != null)
    {
      localByteArrayOutputStream = new ByteArrayOutputStream();
      localAtomicBoolean = new AtomicBoolean();
      localObject = new Object();
      this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader.setDelegate(new ILoader.Delegate() {});
      this.jdField_a_of_type_ComTencentSmttExportExternalEaselInterfacesILoader.load(paramString1, paramString2);
    }
    for (;;)
    {
      try
      {
        boolean bool = localAtomicBoolean.get();
        if (bool) {}
      }
      finally {}
      try
      {
        localObject.wait();
      }
      catch (InterruptedException paramString1) {}
      return localByteArrayOutputStream.toByteArray();
      return null;
    }
  }
  
  private native void nativeDidFail(long paramLong);
  
  private native void nativeDidFinishLoading(long paramLong);
  
  private native void nativeDidReceiveData(long paramLong, byte[] paramArrayOfByte, int paramInt);
  
  private native void nativeDidReceiveResponse(long paramLong, int paramInt);
  
  @CalledByNative
  private void setNativePtr(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\aladdin\ResourceLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */