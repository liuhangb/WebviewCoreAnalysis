package org.chromium.net;

import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("net::android")
public class AndroidCertVerifyResult
{
  private final int jdField_a_of_type_Int;
  private final List<X509Certificate> jdField_a_of_type_JavaUtilList;
  private final boolean jdField_a_of_type_Boolean;
  
  public AndroidCertVerifyResult(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_JavaUtilList = Collections.emptyList();
  }
  
  public AndroidCertVerifyResult(int paramInt, boolean paramBoolean, List<X509Certificate> paramList)
  {
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.jdField_a_of_type_JavaUtilList = new ArrayList(paramList);
  }
  
  @CalledByNative
  public byte[][] getCertificateChainEncoded()
  {
    byte[][] arrayOfByte = new byte[this.jdField_a_of_type_JavaUtilList.size()][];
    int i = 0;
    try
    {
      while (i < this.jdField_a_of_type_JavaUtilList.size())
      {
        arrayOfByte[i] = ((X509Certificate)this.jdField_a_of_type_JavaUtilList.get(i)).getEncoded();
        i += 1;
      }
      return arrayOfByte;
    }
    catch (CertificateEncodingException localCertificateEncodingException)
    {
      for (;;) {}
    }
    return new byte[0][];
  }
  
  @CalledByNative
  public int getStatus()
  {
    return this.jdField_a_of_type_Int;
  }
  
  @CalledByNative
  public boolean isIssuedByKnownRoot()
  {
    return this.jdField_a_of_type_Boolean;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\net\AndroidCertVerifyResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */