package com.tencent.common.wup.security;

import android.text.TextUtils;
import com.taf.HexUtil;
import com.tencent.basesupport.FLogger;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;

public class MttWupToken
{
  public static final int STATUS_CODE_RSA_DEC_FAIL = 702;
  public static final int STATUS_CODE_TOKEN_ERROR = 700;
  public static final int STATUS_CODE_TOKEN_EXPIRED = 701;
  private long jdField_a_of_type_Long = -1L;
  private MttTokenProvider jdField_a_of_type_ComTencentCommonWupSecurityMttTokenProvider = null;
  private Key jdField_a_of_type_JavaSecurityKey = null;
  private boolean jdField_a_of_type_Boolean = false;
  private byte[] jdField_a_of_type_ArrayOfByte = null;
  public String mToken = "";
  
  public MttWupToken(byte[] paramArrayOfByte, MttTokenProvider paramMttTokenProvider)
  {
    if (paramArrayOfByte != null) {
      this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
    }
    this.jdField_a_of_type_ComTencentCommonWupSecurityMttTokenProvider = paramMttTokenProvider;
  }
  
  public MttWupToken(byte[] paramArrayOfByte, String paramString, long paramLong)
  {
    this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
    this.mToken = new String(paramString);
    this.jdField_a_of_type_Long = paramLong;
  }
  
  private Key a()
  {
    Key localKey = this.jdField_a_of_type_JavaSecurityKey;
    if (localKey != null) {
      return localKey;
    }
    try
    {
      if (this.jdField_a_of_type_ArrayOfByte != null) {
        this.jdField_a_of_type_JavaSecurityKey = new SecretKeySpec(this.jdField_a_of_type_ArrayOfByte, "AES");
      }
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("fail to generate key instance, error=");
      localStringBuilder.append(localException.getMessage());
      FLogger.d("MttWupToken", localStringBuilder.toString());
    }
    return this.jdField_a_of_type_JavaSecurityKey;
  }
  
  public String buildUrlParam(byte paramByte, String paramString)
    throws Throwable
  {
    Object localObject;
    if (!TextUtils.isEmpty(this.mToken))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("tk");
      ((StringBuilder)localObject).append("=");
      ((StringBuilder)localObject).append(this.mToken);
      if (!TextUtils.isEmpty(paramString))
      {
        ((StringBuilder)localObject).append("&");
        ((StringBuilder)localObject).append("iv");
        ((StringBuilder)localObject).append("=");
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append("&");
        ((StringBuilder)localObject).append("encrypt");
        ((StringBuilder)localObject).append("=");
        ((StringBuilder)localObject).append("17");
      }
      return ((StringBuilder)localObject).toString();
    }
    if (this.jdField_a_of_type_ComTencentCommonWupSecurityMttTokenProvider != null)
    {
      if (paramByte != 2) {
        paramByte = 1;
      }
      localObject = this.jdField_a_of_type_ComTencentCommonWupSecurityMttTokenProvider.encryptAESKey(this.jdField_a_of_type_ArrayOfByte, paramByte);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("before encrypted, key=");
      localStringBuilder.append(HexUtil.bytes2HexStr(this.jdField_a_of_type_ArrayOfByte));
      localStringBuilder.append(", after encrypted=");
      localStringBuilder.append(HexUtil.bytes2HexStr((byte[])localObject));
      FLogger.d("MttWupToken", localStringBuilder.toString());
      return this.jdField_a_of_type_ComTencentCommonWupSecurityMttTokenProvider.buildUrlParams((byte[])localObject, paramByte, paramString);
    }
    return "";
  }
  
  public byte[] decryptWithToken(byte[] paramArrayOfByte, String paramString)
    throws Throwable
  {
    Key localKey = a();
    if (!TextUtils.isEmpty(paramString)) {
      return WupEncryptHelper.decryptWithCBC(paramArrayOfByte, localKey, paramString.getBytes());
    }
    return WupEncryptHelper.decryptWithECB(paramArrayOfByte, localKey);
  }
  
  public byte[] encryptWithToken(byte[] paramArrayOfByte, String paramString)
    throws Throwable
  {
    Key localKey = a();
    if (!TextUtils.isEmpty(paramString)) {
      return WupEncryptHelper.encryptWithCBC(paramArrayOfByte, localKey, paramString.getBytes());
    }
    return WupEncryptHelper.encryptWithECB(paramArrayOfByte, localKey);
  }
  
  public String getAESKeyString()
  {
    byte[] arrayOfByte = this.jdField_a_of_type_ArrayOfByte;
    if ((arrayOfByte != null) && (arrayOfByte.length > 0)) {
      return HexUtil.bytes2HexStr(arrayOfByte).toLowerCase();
    }
    return "";
  }
  
  public String getSerializedString()
  {
    Object localObject = this.jdField_a_of_type_ArrayOfByte;
    if ((localObject != null) && (localObject.length > 0) && (!isExpired()) && (this.jdField_a_of_type_Long > 0L) && (!TextUtils.isEmpty(this.mToken)))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(HexUtil.bytes2HexStr(this.jdField_a_of_type_ArrayOfByte));
      ((StringBuilder)localObject).append(";");
      ((StringBuilder)localObject).append(this.mToken);
      ((StringBuilder)localObject).append(";");
      ((StringBuilder)localObject).append(this.jdField_a_of_type_Long);
      return ((StringBuilder)localObject).toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getSerializedString: aesKey=");
    localObject = this.jdField_a_of_type_ArrayOfByte;
    if (localObject == null) {
      localObject = "null";
    } else {
      localObject = HexUtil.bytes2HexStr((byte[])localObject);
    }
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(", isExpire = ");
    localStringBuilder.append(isExpired());
    localStringBuilder.append(", token = ");
    localStringBuilder.append(this.mToken);
    FLogger.d("MttWupToken", localStringBuilder.toString());
    return null;
  }
  
  public boolean isExpired()
  {
    if ((this.jdField_a_of_type_Long > 0L) && (System.currentTimeMillis() > this.jdField_a_of_type_Long)) {
      this.jdField_a_of_type_Boolean = true;
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("isExpired: return ");
    localStringBuilder.append(this.jdField_a_of_type_Boolean);
    FLogger.d("MttWupToken", localStringBuilder.toString());
    return this.jdField_a_of_type_Boolean;
  }
  
  public void setExpire(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public void setExpireDate(long paramLong)
  {
    if (paramLong > 0L) {
      this.jdField_a_of_type_Long = paramLong;
    }
  }
  
  public void setTokenExpired(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public boolean setTokenParam(String paramString1, String paramString2)
  {
    if (!TextUtils.isEmpty(paramString1))
    {
      if (TextUtils.isEmpty(paramString2)) {
        return false;
      }
      this.mToken = new String(paramString1);
      try
      {
        long l = Long.parseLong(paramString2);
        if (l > 0L)
        {
          this.jdField_a_of_type_Long = (System.currentTimeMillis() + l);
          return true;
        }
      }
      catch (Exception paramString1)
      {
        paramString2 = new StringBuilder();
        paramString2.append("setTokenExpireSpan error, msg=");
        paramString2.append(paramString1.getMessage());
        FLogger.d("MttWupToken", paramString2.toString());
      }
      return false;
    }
    return false;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("aesKey=");
    localStringBuilder.append(HexUtil.bytes2HexStr(this.jdField_a_of_type_ArrayOfByte));
    localStringBuilder.append(", token=");
    localStringBuilder.append(this.mToken);
    localStringBuilder.append(", expire date=");
    localStringBuilder.append(new Date(this.jdField_a_of_type_Long));
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\security\MttWupToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */