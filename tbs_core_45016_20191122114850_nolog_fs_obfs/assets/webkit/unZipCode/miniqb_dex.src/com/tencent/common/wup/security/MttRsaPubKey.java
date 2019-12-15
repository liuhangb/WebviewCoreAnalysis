package com.tencent.common.wup.security;

import android.util.Base64;
import com.taf.HexUtil;
import com.tencent.basesupport.FLogger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

public class MttRsaPubKey
{
  private static final byte[] jdField_a_of_type_ArrayOfByte = { -90, -112, -43, -11, 75, 67, -54, 83, 90, -14, 102, -61, 24, 7, 105, -57 };
  private Key jdField_a_of_type_JavaSecurityKey = null;
  private boolean jdField_a_of_type_Boolean = false;
  public byte[] mKeyData = null;
  public int mKeyLength = -1;
  public byte[] mVerificationInfo = null;
  
  private static String a()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCzazICPQD9Tuky2L9Nl88S6WI2");
    localStringBuilder.append("QQAUirJznzLq923Q5mV6DHJTLYgG4hx44+0ViPgwMHzHiMn4sfK5+ZdsukjPXEG0");
    localStringBuilder.append("Wm+YHqCK0IECHE7dN3TXhyBspm4YlZBJUCKbMBlO0jYPywUSPmQQ9nazll0/o1JO");
    localStringBuilder.append("ephdDs1ixszWS92WnQIDAQAB");
    return localStringBuilder.toString();
  }
  
  public static MttRsaPubKey getDefault()
    throws Exception
  {
    Object localObject = a();
    MttRsaPubKey localMttRsaPubKey = new MttRsaPubKey();
    localMttRsaPubKey.jdField_a_of_type_Boolean = true;
    localMttRsaPubKey.mKeyData = Base64.decode(((String)localObject).getBytes(), 0);
    localMttRsaPubKey.mKeyLength = 1024;
    localMttRsaPubKey.mVerificationInfo = jdField_a_of_type_ArrayOfByte;
    if (localMttRsaPubKey.mVerificationInfo == null) {
      return null;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getDefault: data=");
    ((StringBuilder)localObject).append(HexUtil.bytes2HexStr(localMttRsaPubKey.mKeyData));
    ((StringBuilder)localObject).append(", verify=");
    ((StringBuilder)localObject).append(HexUtil.bytes2HexStr(localMttRsaPubKey.mVerificationInfo));
    FLogger.d("MttRsaPubKey", ((StringBuilder)localObject).toString());
    return localMttRsaPubKey;
  }
  
  public Key getKeyInstance()
  {
    Object localObject = this.jdField_a_of_type_JavaSecurityKey;
    if (localObject != null) {
      return (Key)localObject;
    }
    localObject = this.mKeyData;
    if (localObject != null)
    {
      if (localObject.length <= 0) {
        return null;
      }
      try
      {
        localObject = new X509EncodedKeySpec((byte[])localObject);
        this.jdField_a_of_type_JavaSecurityKey = KeyFactory.getInstance("RSA").generatePublic((KeySpec)localObject);
        FLogger.d("MttRsaPubKey", "getKeyInstance: success!!!");
        return this.jdField_a_of_type_JavaSecurityKey;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("getKeyInstance: error!!!");
        localStringBuilder.append(localThrowable.getMessage());
        FLogger.d("MttRsaPubKey", localStringBuilder.toString());
        return null;
      }
    }
    return null;
  }
  
  public int getKeySizeByte()
  {
    int i = this.mKeyLength;
    if ((i > 0) && (i % 8 == 0)) {
      return i / 8;
    }
    return -1;
  }
  
  public boolean isDefaultKey()
  {
    return this.jdField_a_of_type_Boolean;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\security\MttRsaPubKey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */