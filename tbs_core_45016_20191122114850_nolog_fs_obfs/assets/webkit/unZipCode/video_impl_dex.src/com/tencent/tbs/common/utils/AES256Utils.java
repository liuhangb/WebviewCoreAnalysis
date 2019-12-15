package com.tencent.tbs.common.utils;

import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES256Utils
{
  public static final String IMEI_DEFAULT = "865282033997410";
  public static final String KEY = "mvLBiZsiTbGwrfJB";
  private static final String TAG = "AES256Utils";
  
  public static byte[] decrypt(byte[] paramArrayOfByte, String paramString)
  {
    try
    {
      paramString = new SecretKeySpec(paramString.getBytes(), "AES");
      Cipher localCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      localCipher.init(2, paramString);
      paramArrayOfByte = localCipher.doFinal(paramArrayOfByte);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      LogUtils.d("AES256Utils", Log.getStackTraceString(paramArrayOfByte));
    }
    return null;
  }
  
  public static byte[] encrypt(byte[] paramArrayOfByte, String paramString)
  {
    try
    {
      paramString = new SecretKeySpec(paramString.getBytes(), "AES");
      Cipher localCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      localCipher.init(1, paramString);
      paramArrayOfByte = localCipher.doFinal(paramArrayOfByte);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      LogUtils.d("AES256Utils", Log.getStackTraceString(paramArrayOfByte));
    }
    return null;
  }
  
  public static String generateKey()
  {
    Object localObject2 = DeviceUtils.getIMEI(TbsBaseModuleShell.getContext());
    String str = GUIDFactory.getInstance().getStrGuid();
    Object localObject1 = localObject2;
    if (TextUtils.isEmpty((CharSequence)localObject2)) {
      localObject1 = "865282033997410";
    }
    if (TextUtils.isEmpty(str)) {
      return "mvLBiZsiTbGwrfJB";
    }
    localObject2 = new StringBuilder();
    if ((((String)localObject1).length() > 0) && (((String)localObject1).length() / 2 < 16)) {
      ((StringBuilder)localObject2).append(((String)localObject1).substring(0, ((String)localObject1).length() / 2));
    }
    ((StringBuilder)localObject2).append(str.substring(0, 16 - ((StringBuilder)localObject2).length()));
    return ((StringBuilder)localObject2).toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\AES256Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */