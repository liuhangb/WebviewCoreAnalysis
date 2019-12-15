package org.chromium.media;

import android.annotation.TargetApi;
import org.chromium.base.Callback;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;

@JNINamespace("media")
@TargetApi(23)
@MainDex
class MediaDrmStorageBridge
{
  private long jdField_a_of_type_Long;
  
  MediaDrmStorageBridge(long paramLong)
  {
    this.jdField_a_of_type_Long = paramLong;
    if (!jdField_a_of_type_Boolean)
    {
      if (a()) {
        return;
      }
      throw new AssertionError();
    }
  }
  
  private boolean a()
  {
    return this.jdField_a_of_type_Long != -1L;
  }
  
  private native void nativeOnClearInfo(long paramLong, byte[] paramArrayOfByte, Callback<Boolean> paramCallback);
  
  private native void nativeOnLoadInfo(long paramLong, byte[] paramArrayOfByte, Callback<PersistentInfo> paramCallback);
  
  private native void nativeOnProvisioned(long paramLong, Callback<Boolean> paramCallback);
  
  private native void nativeOnSaveInfo(long paramLong, PersistentInfo paramPersistentInfo, Callback<Boolean> paramCallback);
  
  void a(Callback<Boolean> paramCallback)
  {
    if (a())
    {
      nativeOnProvisioned(this.jdField_a_of_type_Long, paramCallback);
      return;
    }
    paramCallback.onResult(Boolean.valueOf(true));
  }
  
  void a(PersistentInfo paramPersistentInfo, Callback<Boolean> paramCallback)
  {
    if (a())
    {
      nativeOnSaveInfo(this.jdField_a_of_type_Long, paramPersistentInfo, paramCallback);
      return;
    }
    paramCallback.onResult(Boolean.valueOf(false));
  }
  
  void a(byte[] paramArrayOfByte, Callback<PersistentInfo> paramCallback)
  {
    if (a())
    {
      nativeOnLoadInfo(this.jdField_a_of_type_Long, paramArrayOfByte, paramCallback);
      return;
    }
    paramCallback.onResult(null);
  }
  
  void b(byte[] paramArrayOfByte, Callback<Boolean> paramCallback)
  {
    if (a())
    {
      nativeOnClearInfo(this.jdField_a_of_type_Long, paramArrayOfByte, paramCallback);
      return;
    }
    paramCallback.onResult(Boolean.valueOf(true));
  }
  
  @MainDex
  static class PersistentInfo
  {
    private final String jdField_a_of_type_JavaLangString;
    private final byte[] jdField_a_of_type_ArrayOfByte;
    private final byte[] b;
    
    PersistentInfo(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString)
    {
      this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte1;
      this.b = paramArrayOfByte2;
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    @CalledByNative("PersistentInfo")
    private static PersistentInfo create(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, String paramString)
    {
      return new PersistentInfo(paramArrayOfByte1, paramArrayOfByte2, paramString);
    }
    
    @CalledByNative("PersistentInfo")
    byte[] emeId()
    {
      return this.jdField_a_of_type_ArrayOfByte;
    }
    
    @CalledByNative("PersistentInfo")
    byte[] keySetId()
    {
      return this.b;
    }
    
    @CalledByNative("PersistentInfo")
    String mimeType()
    {
      return this.jdField_a_of_type_JavaLangString;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\media\MediaDrmStorageBridge.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */