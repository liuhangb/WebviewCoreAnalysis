package org.chromium.android_webview;

import java.security.PrivateKey;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ClientCertLookupTable
{
  private final Map<String, Cert> jdField_a_of_type_JavaUtilMap = new HashMap();
  private final Set<String> jdField_a_of_type_JavaUtilSet = new HashSet();
  
  private static String a(String paramString, int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(":");
    localStringBuilder.append(paramInt);
    return localStringBuilder.toString();
  }
  
  public void allow(String paramString, int paramInt, PrivateKey paramPrivateKey, byte[][] paramArrayOfByte)
  {
    paramString = a(paramString, paramInt);
    this.jdField_a_of_type_JavaUtilMap.put(paramString, new Cert(paramPrivateKey, paramArrayOfByte));
    this.jdField_a_of_type_JavaUtilSet.remove(paramString);
  }
  
  public void clear()
  {
    this.jdField_a_of_type_JavaUtilMap.clear();
    this.jdField_a_of_type_JavaUtilSet.clear();
  }
  
  public void deny(String paramString, int paramInt)
  {
    paramString = a(paramString, paramInt);
    this.jdField_a_of_type_JavaUtilMap.remove(paramString);
    this.jdField_a_of_type_JavaUtilSet.add(paramString);
  }
  
  public Cert getCertData(String paramString, int paramInt)
  {
    return (Cert)this.jdField_a_of_type_JavaUtilMap.get(a(paramString, paramInt));
  }
  
  public boolean isDenied(String paramString, int paramInt)
  {
    return this.jdField_a_of_type_JavaUtilSet.contains(a(paramString, paramInt));
  }
  
  public static class Cert
  {
    PrivateKey jdField_a_of_type_JavaSecurityPrivateKey;
    byte[][] jdField_a_of_type_Array2dOfByte;
    
    public Cert(PrivateKey paramPrivateKey, byte[][] paramArrayOfByte)
    {
      this.jdField_a_of_type_JavaSecurityPrivateKey = paramPrivateKey;
      paramPrivateKey = new byte[paramArrayOfByte.length][];
      int i = 0;
      while (i < paramArrayOfByte.length)
      {
        paramPrivateKey[i] = Arrays.copyOf(paramArrayOfByte[i], paramArrayOfByte[i].length);
        i += 1;
      }
      this.jdField_a_of_type_Array2dOfByte = paramPrivateKey;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\ClientCertLookupTable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */