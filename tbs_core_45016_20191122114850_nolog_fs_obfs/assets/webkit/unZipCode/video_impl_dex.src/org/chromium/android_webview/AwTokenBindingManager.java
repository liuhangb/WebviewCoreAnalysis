package org.chromium.android_webview;

import android.net.Uri;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import org.chromium.base.Callback;
import org.chromium.base.Log;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("android_webview")
public final class AwTokenBindingManager
{
  private native void nativeDeleteAllTokenBindingKeys(Callback<Boolean> paramCallback);
  
  private native void nativeDeleteTokenBindingKey(String paramString, Callback<Boolean> paramCallback);
  
  private native void nativeEnableTokenBinding();
  
  private native void nativeGetTokenBindingKey(String paramString, Callback<KeyPair> paramCallback);
  
  @CalledByNative
  private static void onDeletionComplete(Callback<Boolean> paramCallback)
  {
    paramCallback.onResult(Boolean.valueOf(true));
  }
  
  @CalledByNative
  private static void onKeyReady(Callback<KeyPair> paramCallback, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    Object localObject = null;
    if ((paramArrayOfByte1 != null) && (paramArrayOfByte2 != null))
    {
      try
      {
        KeyFactory localKeyFactory = KeyFactory.getInstance("EC");
        paramArrayOfByte1 = localKeyFactory.generatePrivate(new PKCS8EncodedKeySpec(paramArrayOfByte1));
        paramArrayOfByte1 = new KeyPair(localKeyFactory.generatePublic(new X509EncodedKeySpec(paramArrayOfByte2)), paramArrayOfByte1);
      }
      catch (InvalidKeySpecException paramArrayOfByte1) {}catch (NoSuchAlgorithmException paramArrayOfByte1) {}
      Log.e("TokenBindingManager", "Failed converting key ", new Object[] { paramArrayOfByte1 });
      paramArrayOfByte1 = (byte[])localObject;
      paramCallback.onResult(paramArrayOfByte1);
      return;
    }
    paramCallback.onResult(null);
  }
  
  public void deleteAllKeys(Callback<Boolean> paramCallback)
  {
    nativeDeleteAllTokenBindingKeys(paramCallback);
  }
  
  public void deleteKey(Uri paramUri, Callback<Boolean> paramCallback)
  {
    if (paramUri != null)
    {
      nativeDeleteTokenBindingKey(paramUri.getHost(), paramCallback);
      return;
    }
    throw new IllegalArgumentException("origin can't be null");
  }
  
  public void enableTokenBinding()
  {
    nativeEnableTokenBinding();
  }
  
  public void getKey(Uri paramUri, String[] paramArrayOfString, Callback<KeyPair> paramCallback)
  {
    if (paramCallback != null)
    {
      nativeGetTokenBindingKey(paramUri.getHost(), paramCallback);
      return;
    }
    throw new IllegalArgumentException("callback can't be null");
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\AwTokenBindingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */