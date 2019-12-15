package org.chromium.android_webview;

import android.net.http.SslCertificate;
import android.net.http.SslError;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import org.chromium.net.X509Util;

public class SslUtil
{
  public static SslCertificate getCertificateFromDerBytes(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    try
    {
      paramArrayOfByte = new SslCertificate(X509Util.a(paramArrayOfByte));
      return paramArrayOfByte;
    }
    catch (CertificateException|KeyStoreException|NoSuchAlgorithmException paramArrayOfByte) {}
    return null;
  }
  
  public static SslError sslErrorFromNetErrorCode(int paramInt, SslCertificate paramSslCertificate, String paramString)
  {
    if ((!a) && ((paramInt < 65320) || (paramInt > 65336))) {
      throw new AssertionError();
    }
    switch (paramInt)
    {
    default: 
      return new SslError(5, paramSslCertificate, paramString);
    case -200: 
      return new SslError(2, paramSslCertificate, paramString);
    case -201: 
      return new SslError(4, paramSslCertificate, paramString);
    }
    return new SslError(3, paramSslCertificate, paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\SslUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */