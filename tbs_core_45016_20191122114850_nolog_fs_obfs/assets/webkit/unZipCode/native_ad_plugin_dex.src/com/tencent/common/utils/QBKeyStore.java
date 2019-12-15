package com.tencent.common.utils;

import android.text.TextUtils;
import android.util.SparseArray;
import com.tencent.basesupport.FLogger;

public class QBKeyStore
{
  public static final int COMMON_KEY_INDEX = 1;
  public static final int FASTLINK_REPORT_TEA_KEY = 12;
  public static final int LOG_UPLOAD_KEY_INDEX = 5;
  public static final int MAC_KEY_INDEX = 2;
  public static final int MTT_KEY_INDEX = 3;
  public static final int NOVEL_CONTENT_TEA_KEY_INDEX = 9;
  public static final int PUSH_DATA_TEA_KEY_INDEX = 10;
  public static final int PUSH_REPORT_KEY_INDEX = 13;
  public static final int QB_GAME_ACCOUNT_KEY_INDEX = 8;
  public static final int QB_URL_UTILS_KEY_INDEX = 6;
  public static final int QQMARKET_KEY_INDEX = 4;
  public static final int REPORT_KEY_TEA_INDEX = 0;
  public static final int RSA_PRIV_KEY_PART_A_INDEX = 14;
  public static final int RSA_PRIV_KEY_PART_B_INDEX = 15;
  public static final int RSA_PRIV_KEY_PART_C_INDEX = 16;
  public static final int RSA_PRIV_KEY_PART_D_INDEX = 17;
  public static final int RSA_PRIV_KEY_PART_E_INDEX = 18;
  public static final int RSA_PRIV_KEY_PART_F_INDEX = 19;
  public static final int RSA_PRIV_KEY_PART_G_INDEX = 20;
  public static final int WUP_TEA_ENCRYPT_KEY_INDEX = 7;
  public static final int YIYA_DATA_TEA_KEY_INDEX = 11;
  private static SparseArray<byte[]> jdField_a_of_type_AndroidUtilSparseArray = new SparseArray();
  private static final QBKeyStore jdField_a_of_type_ComTencentCommonUtilsQBKeyStore;
  private static volatile boolean jdField_a_of_type_Boolean = false;
  
  static
  {
    if (!jdField_a_of_type_Boolean) {
      try
      {
        String str = QBSoLoader.tinkerSoLoadLibraryPath("qb_keystore");
        if (TextUtils.isEmpty(str)) {
          System.loadLibrary("qb_keystore");
        } else {
          System.load(str);
        }
        jdField_a_of_type_Boolean = true;
        new QBKeyStore().nativeCheckJNILoad();
        FLogger.d("QBKeyStore", "JNI load succ");
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        jdField_a_of_type_Boolean = false;
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("JNI load fail, error = ");
        localStringBuilder.append(localThrowable.getMessage());
        FLogger.d("QBKeyStore", localStringBuilder.toString());
      }
    }
    jdField_a_of_type_ComTencentCommonUtilsQBKeyStore = new QBKeyStore();
  }
  
  private static byte a(char paramChar)
  {
    if ((paramChar >= '0') && (paramChar <= '9')) {
      return (byte)(paramChar - '0');
    }
    if ((paramChar >= 'a') && (paramChar <= 'f')) {
      return (byte)(paramChar - 'a' + 10);
    }
    if ((paramChar >= 'A') && (paramChar <= 'F')) {
      return (byte)(paramChar - 'A' + 10);
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("Illigal string containing non-hex chars that are neither number nor alphabite: ");
    localStringBuilder.append(paramChar);
    throw new RuntimeException(localStringBuilder.toString());
  }
  
  private static byte[] a(String paramString)
  {
    int i = 0;
    if ((paramString != null) && (!paramString.equals("")))
    {
      byte[] arrayOfByte = new byte[paramString.length() / 2];
      while (i < arrayOfByte.length)
      {
        int j = i * 2;
        char c1 = paramString.charAt(j);
        char c2 = paramString.charAt(j + 1);
        arrayOfByte[i] = ((byte)(a(c1) * 16 + a(c2)));
        i += 1;
      }
      return arrayOfByte;
    }
    return new byte[0];
  }
  
  public static byte[] getKeyBytesById(int paramInt)
    throws Exception
  {
    Object localObject1 = (byte[])jdField_a_of_type_AndroidUtilSparseArray.get(paramInt);
    Object localObject3;
    if (localObject1 != null)
    {
      localObject3 = new StringBuilder();
      ((StringBuilder)localObject3).append("get from cache: keyId = ");
      ((StringBuilder)localObject3).append(paramInt);
      ((StringBuilder)localObject3).append(", cached = ");
      ((StringBuilder)localObject3).append(HexUtil.bytes2HexStr((byte[])localObject1));
      FLogger.d("QBKeyStore", ((StringBuilder)localObject3).toString());
      return (byte[])localObject1;
    }
    if (jdField_a_of_type_Boolean)
    {
      try
      {
        localObject1 = jdField_a_of_type_ComTencentCommonUtilsQBKeyStore.nativeGetKeyStrById(paramInt);
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append("get key from so failed, error = ");
        ((StringBuilder)localObject3).append(localThrowable.getMessage());
        FLogger.d("QBKeyStore", ((StringBuilder)localObject3).toString());
        localObject2 = null;
      }
      if (!TextUtils.isEmpty((CharSequence)localObject2))
      {
        localObject3 = a((String)localObject2);
        if (localObject3 != null) {
          jdField_a_of_type_AndroidUtilSparseArray.put(paramInt, localObject3);
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("getKeyBytesById: keyId = ");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(", value = ");
        if (localObject2 == null) {
          localObject2 = "null";
        } else {
          localObject2 = HexUtil.bytes2HexStr((byte[])localObject3);
        }
        localStringBuilder.append((String)localObject2);
        FLogger.d("QBKeyStore", localStringBuilder.toString());
        return (byte[])localObject3;
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("get from cache: keyId = ");
      ((StringBuilder)localObject2).append(paramInt);
      ((StringBuilder)localObject2).append(", but NO matched key found");
      FLogger.d("QBKeyStore", ((StringBuilder)localObject2).toString());
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("No valid key found for keyID: ");
      ((StringBuilder)localObject2).append(paramInt);
      throw new RuntimeException(((StringBuilder)localObject2).toString());
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("get from cache: keyId = ");
    ((StringBuilder)localObject2).append(paramInt);
    ((StringBuilder)localObject2).append(", but so not loaded");
    FLogger.d("QBKeyStore", ((StringBuilder)localObject2).toString());
    throw new IllegalStateException("fail to load so libarary");
  }
  
  public static String getKeyStrById(int paramInt)
    throws Exception
  {
    if (jdField_a_of_type_Boolean)
    {
      StringBuilder localStringBuilder;
      try
      {
        String str = jdField_a_of_type_ComTencentCommonUtilsQBKeyStore.nativeGetKeyStrById(paramInt);
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("get key String from so failed, error = ");
        localStringBuilder.append(localThrowable.getMessage());
        FLogger.d("QBKeyStore", localStringBuilder.toString());
        localObject = null;
      }
      if (!TextUtils.isEmpty((CharSequence)localObject))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("getKeyStrById: keyId = ");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(", value = ");
        localStringBuilder.append((String)localObject);
        FLogger.d("QBKeyStore", localStringBuilder.toString());
        return (String)localObject;
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("get from cache: keyId = ");
      ((StringBuilder)localObject).append(paramInt);
      ((StringBuilder)localObject).append(", but NO matched key found");
      FLogger.d("QBKeyStore", ((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("No valid key found for keyID: ");
      ((StringBuilder)localObject).append(paramInt);
      throw new RuntimeException(((StringBuilder)localObject).toString());
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("getKeyStrById: keyId = ");
    ((StringBuilder)localObject).append(paramInt);
    ((StringBuilder)localObject).append(", but so not loaded");
    FLogger.d("QBKeyStore", ((StringBuilder)localObject).toString());
    throw new IllegalStateException("fail to load so libarary");
  }
  
  public static void init(String paramString)
  {
    if (!jdField_a_of_type_Boolean) {
      try
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(paramString);
        ((StringBuilder)localObject).append("/libqb_keystore.so");
        localObject = QBSoLoader.tinkerSoLoadPath(((StringBuilder)localObject).toString());
        if (TextUtils.isEmpty((CharSequence)localObject))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append("/libqb_keystore.so");
          System.load(((StringBuilder)localObject).toString());
        }
        else
        {
          System.load((String)localObject);
        }
        jdField_a_of_type_Boolean = true;
        new QBKeyStore().nativeCheckJNILoad();
        FLogger.d("QBKeyStore", "JNI load succ");
        return;
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
        jdField_a_of_type_Boolean = false;
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("JNI load fail, error = ");
        ((StringBuilder)localObject).append(paramString.getMessage());
        FLogger.d("QBKeyStore", ((StringBuilder)localObject).toString());
      }
    }
  }
  
  public native String nativeCheckJNILoad();
  
  public native String nativeGetKeyStrById(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\QBKeyStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */