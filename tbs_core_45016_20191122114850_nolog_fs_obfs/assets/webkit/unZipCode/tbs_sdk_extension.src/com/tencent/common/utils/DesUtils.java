package com.tencent.common.utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class DesUtils
{
  public static final int FLAG_DECRYPT = 0;
  public static final int FLAG_ENCRYPT = 1;
  public static final byte[] KEY;
  public static final String LOG_UPLOAD_KEY = "I!Love!#$Cirodeng@";
  public static final byte[] MAC_KEY;
  public static final byte[] MTT_KEY;
  public static final byte[] REPORT_KEY_TEA = a(0);
  static byte[] jdField_a_of_type_ArrayOfByte;
  static int[] jdField_a_of_type_ArrayOfInt;
  static short[] jdField_a_of_type_ArrayOfShort;
  static byte[] jdField_b_of_type_ArrayOfByte;
  static int[] jdField_b_of_type_ArrayOfInt = { 16843776, 0, 65536, 16843780, 16842756, 66564, 4, 65536, 1024, 16843776, 16843780, 1024, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, 65536, 16843780, 4, 16842752, 16843776, 16777216, 16777216, 1024, 16842756, 65536, 66560, 16777220, 1024, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756 };
  static short[] jdField_b_of_type_ArrayOfShort;
  static byte[] jdField_c_of_type_ArrayOfByte;
  static int[] jdField_c_of_type_ArrayOfInt = { -2146402272, -2147450880, 32768, 1081376, 1048576, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, Integer.MIN_VALUE, -2147450880, 1048576, 32, -2146435040, 1081344, 1048608, -2147450848, 0, Integer.MIN_VALUE, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, 1048576, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, Integer.MIN_VALUE, 32800, -2146402304, 1048576, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, Integer.MIN_VALUE, -2146435040, -2146402272, 1081344 };
  static int[] d = { 520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, 131072, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, 131072, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, 131072, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, 131072, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584 };
  static int[] e = { 8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, 129, 0, 8388736, 8388609, 1, 8192, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, 8192, 8396928, 8396929, 129, 8388736, 8388609, 8396800, 8396929, 129, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, 129, 1, 8192, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, 8192, 8396928 };
  static int[] f = { 256, 34078976, 34078720, 1107296512, 524288, 256, 1073741824, 34078720, 1074266368, 524288, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, 524288, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, 524288, 0, 1074266112, 34078976, 1073742080 };
  static int[] g = { 536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, 536870912, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, 536870912, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, 536870912, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, 536870912, 4194320, 536887312 };
  static int[] h = { 2097152, 69206018, 67110914, 0, 2048, 67110914, 2099202, 69208064, 69208066, 2097152, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, 2048, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, 2097152, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, 2097152, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, 2048, 67108866, 67110912, 2048, 2097154 };
  static int[] i = { 268439616, 4096, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, 4096, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, 4096, 64, 268697664, 4096, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696 };
  
  static
  {
    KEY = new byte[] { -25, -101, -115, 1, 47, 7, -27, -59, 18, -128, 123, 79, -44, 37, 46, 115 };
    MAC_KEY = new byte[] { 37, -110, 60, 127, 42, -27, -17, -110 };
    MTT_KEY = new byte[] { -122, -8, -23, -84, -125, 113, 84, 99 };
    jdField_a_of_type_ArrayOfShort = new short[] { 1, 35, 69, 103, 137, 171, 205, 239, 254, 220, 186, 152, 118, 84, 50, 16, 137, 171, 205, 239, 1, 35, 69, 103 };
    jdField_b_of_type_ArrayOfShort = new short[] { 128, 64, 32, 16, 8, 4, 2, 1 };
    jdField_a_of_type_ArrayOfInt = new int[] { 8388608, 4194304, 2097152, 1048576, 524288, 262144, 131072, 65536, 32768, 16384, 8192, 4096, 2048, 1024, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1 };
    jdField_a_of_type_ArrayOfByte = new byte[] { 56, 48, 40, 32, 24, 16, 8, 0, 57, 49, 41, 33, 25, 17, 9, 1, 58, 50, 42, 34, 26, 18, 10, 2, 59, 51, 43, 35, 62, 54, 46, 38, 30, 22, 14, 6, 61, 53, 45, 37, 29, 21, 13, 5, 60, 52, 44, 36, 28, 20, 12, 4, 27, 19, 11, 3 };
    jdField_b_of_type_ArrayOfByte = new byte[] { 1, 2, 4, 6, 8, 10, 12, 14, 15, 17, 19, 21, 23, 25, 27, 28 };
    jdField_c_of_type_ArrayOfByte = new byte[] { 13, 16, 10, 23, 0, 4, 2, 27, 14, 5, 20, 9, 22, 18, 11, 3, 25, 7, 15, 6, 26, 19, 12, 1, 40, 51, 30, 36, 46, 54, 29, 39, 50, 44, 32, 47, 43, 48, 38, 55, 33, 52, 45, 41, 49, 35, 28, 31 };
  }
  
  public static byte[] Des3Encrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
    throws Exception
  {
    paramArrayOfByte1 = new SecretKeySpec(paramArrayOfByte1, "DESede");
    Cipher localCipher = Cipher.getInstance("DESede");
    try
    {
      localCipher.init(paramInt, paramArrayOfByte1);
      paramArrayOfByte1 = localCipher.doFinal(paramArrayOfByte2);
      return paramArrayOfByte1;
    }
    catch (AbstractMethodError paramArrayOfByte1)
    {
      paramArrayOfByte1.printStackTrace();
    }
    catch (NoSuchFieldError paramArrayOfByte1)
    {
      paramArrayOfByte1.printStackTrace();
    }
    return null;
  }
  
  public static byte[] DesEncrypt(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt)
  {
    int m = 1;
    boolean bool;
    if (paramInt == 1) {
      bool = true;
    } else {
      bool = false;
    }
    paramArrayOfByte1 = generateWorkingKey(bool, paramArrayOfByte1, 0);
    int i1 = paramArrayOfByte2.length;
    int i2 = 8 - i1 % 8;
    int j = i1 + i2;
    byte[] arrayOfByte1 = new byte[j];
    int k = j / 8;
    j = 0;
    while (j < k - 1)
    {
      n = j * 8;
      a(paramArrayOfByte1, paramArrayOfByte2, n, arrayOfByte1, n);
      j += 1;
    }
    byte[] arrayOfByte2 = new byte[8];
    int n = j * 8;
    k = n;
    j = 0;
    while (j < 8)
    {
      if (k < i1)
      {
        arrayOfByte2[j] = paramArrayOfByte2[k];
        k += 1;
      }
      else
      {
        arrayOfByte2[j] = ((byte)i2);
      }
      j += 1;
    }
    a(paramArrayOfByte1, arrayOfByte2, 0, arrayOfByte1, n);
    paramArrayOfByte1 = arrayOfByte1;
    if (paramInt == 0)
    {
      paramArrayOfByte2 = new byte[paramArrayOfByte2.length];
      System.arraycopy(arrayOfByte1, 0, paramArrayOfByte2, 0, paramArrayOfByte2.length);
      k = paramArrayOfByte2[(paramArrayOfByte2.length - 1)];
      paramArrayOfByte1 = arrayOfByte1;
      if (k > 0)
      {
        paramArrayOfByte1 = arrayOfByte1;
        if (k <= 8)
        {
          paramInt = 0;
          for (;;)
          {
            j = m;
            if (paramInt >= k) {
              break;
            }
            if (k != paramArrayOfByte2[(paramArrayOfByte2.length - 1 - paramInt)])
            {
              j = 0;
              break;
            }
            paramInt += 1;
          }
          paramArrayOfByte1 = arrayOfByte1;
          if (j != 0)
          {
            paramArrayOfByte1 = new byte[paramArrayOfByte2.length - k];
            System.arraycopy(paramArrayOfByte2, 0, paramArrayOfByte1, 0, paramArrayOfByte1.length);
          }
        }
      }
    }
    return paramArrayOfByte1;
  }
  
  private static void a(int[] paramArrayOfInt, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    if (paramArrayOfInt != null)
    {
      desFunc(paramArrayOfInt, paramArrayOfByte1, paramInt1, paramArrayOfByte2, paramInt2);
      return;
    }
    throw new IllegalStateException("DES engine not initialised!");
  }
  
  private static byte[] a(int paramInt)
  {
    byte[] arrayOfByte1 = new byte[8];
    try
    {
      byte[] arrayOfByte2 = QBKeyStore.getKeyBytesById(paramInt);
      return arrayOfByte2;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return arrayOfByte1;
  }
  
  public static String deCrypt(String paramString1, String paramString2)
    throws InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
  {
    DESKeySpec localDESKeySpec = new DESKeySpec(paramString2.getBytes());
    Object localObject2 = null;
    try
    {
      paramString2 = SecretKeyFactory.getInstance("DES");
      try
      {
        Cipher localCipher = Cipher.getInstance("DES");
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException1) {}
      localNoSuchAlgorithmException2.printStackTrace();
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException2)
    {
      paramString2 = null;
    }
    Object localObject1 = localObject2;
    ((Cipher)localObject1).init(2, paramString2.generateSecret(localDESKeySpec));
    paramString2 = new byte[paramString1.length() / 2];
    int j = 0;
    int m = paramString1.length();
    while (j < m)
    {
      int n = j / 2;
      int k = j + 2;
      paramString2[n] = ((byte)Integer.parseInt(paramString1.substring(j, k), 16));
      j = k;
    }
    return new String(((Cipher)localObject1).doFinal(paramString2));
  }
  
  protected static void desFunc(int[] paramArrayOfInt, byte[] paramArrayOfByte1, int paramInt1, byte[] paramArrayOfByte2, int paramInt2)
  {
    int j = (paramArrayOfByte1[(paramInt1 + 0)] & 0xFF) << 24 | (paramArrayOfByte1[(paramInt1 + 1)] & 0xFF) << 16 | (paramArrayOfByte1[(paramInt1 + 2)] & 0xFF) << 8 | paramArrayOfByte1[(paramInt1 + 3)] & 0xFF;
    paramInt1 = (paramArrayOfByte1[(paramInt1 + 4)] & 0xFF) << 24 | (paramArrayOfByte1[(paramInt1 + 5)] & 0xFF) << 16 | (paramArrayOfByte1[(paramInt1 + 6)] & 0xFF) << 8 | paramArrayOfByte1[(paramInt1 + 7)] & 0xFF;
    int k = (j >>> 4 ^ paramInt1) & 0xF0F0F0F;
    paramInt1 ^= k;
    j ^= k << 4;
    k = (j >>> 16 ^ paramInt1) & 0xFFFF;
    paramInt1 ^= k;
    j ^= k << 16;
    k = (paramInt1 >>> 2 ^ j) & 0x33333333;
    j ^= k;
    k = paramInt1 ^ k << 2;
    int m = (k >>> 8 ^ j) & 0xFF00FF;
    paramInt1 = j ^ m;
    j = k ^ m << 8;
    j = (j >>> 31 & 0x1 | j << 1) & 0xFFFFFFFF;
    k = (paramInt1 ^ j) & 0xAAAAAAAA;
    paramInt1 ^= k;
    j ^= k;
    k = (paramInt1 >>> 31 & 0x1 | paramInt1 << 1) & 0xFFFFFFFF;
    paramInt1 = 0;
    while (paramInt1 < 8)
    {
      m = paramInt1 * 4;
      int i3 = (j << 28 | j >>> 4) ^ paramArrayOfInt[(m + 0)];
      paramArrayOfByte1 = h;
      int n = paramArrayOfByte1[(i3 & 0x3F)];
      int[] arrayOfInt1 = f;
      int i1 = arrayOfInt1[(i3 >>> 8 & 0x3F)];
      int[] arrayOfInt2 = d;
      int i2 = arrayOfInt2[(i3 >>> 16 & 0x3F)];
      int[] arrayOfInt3 = jdField_b_of_type_ArrayOfInt;
      i3 = arrayOfInt3[(i3 >>> 24 & 0x3F)];
      int i4 = paramArrayOfInt[(m + 1)] ^ j;
      int[] arrayOfInt4 = i;
      int i5 = arrayOfInt4[(i4 & 0x3F)];
      int[] arrayOfInt5 = g;
      int i6 = arrayOfInt5[(i4 >>> 8 & 0x3F)];
      int[] arrayOfInt6 = e;
      int i7 = arrayOfInt6[(i4 >>> 16 & 0x3F)];
      int[] arrayOfInt7 = jdField_c_of_type_ArrayOfInt;
      k ^= (i3 | n | i1 | i2 | i5 | i6 | i7 | arrayOfInt7[(i4 >>> 24 & 0x3F)]);
      i3 = (k << 28 | k >>> 4) ^ paramArrayOfInt[(m + 2)];
      n = paramArrayOfByte1[(i3 & 0x3F)];
      i1 = arrayOfInt1[(i3 >>> 8 & 0x3F)];
      i2 = arrayOfInt2[(i3 >>> 16 & 0x3F)];
      i3 = arrayOfInt3[(i3 >>> 24 & 0x3F)];
      m = paramArrayOfInt[(m + 3)] ^ k;
      j ^= (i3 | n | i1 | i2 | arrayOfInt4[(m & 0x3F)] | arrayOfInt5[(m >>> 8 & 0x3F)] | arrayOfInt6[(m >>> 16 & 0x3F)] | arrayOfInt7[(m >>> 24 & 0x3F)]);
      paramInt1 += 1;
    }
    paramInt1 = j >>> 1 | j << 31;
    j = (k ^ paramInt1) & 0xAAAAAAAA;
    k ^= j;
    paramInt1 ^= j;
    j = k >>> 1 | k << 31;
    k = (j >>> 8 ^ paramInt1) & 0xFF00FF;
    paramInt1 ^= k;
    j ^= k << 8;
    k = (j >>> 2 ^ paramInt1) & 0x33333333;
    paramInt1 ^= k;
    j ^= k << 2;
    k = (paramInt1 >>> 16 ^ j) & 0xFFFF;
    j ^= k;
    paramInt1 ^= k << 16;
    k = (paramInt1 >>> 4 ^ j) & 0xF0F0F0F;
    j ^= k;
    paramInt1 ^= k << 4;
    paramArrayOfByte2[(paramInt2 + 0)] = ((byte)(paramInt1 >>> 24 & 0xFF));
    paramArrayOfByte2[(paramInt2 + 1)] = ((byte)(paramInt1 >>> 16 & 0xFF));
    paramArrayOfByte2[(paramInt2 + 2)] = ((byte)(paramInt1 >>> 8 & 0xFF));
    paramArrayOfByte2[(paramInt2 + 3)] = ((byte)(paramInt1 & 0xFF));
    paramArrayOfByte2[(paramInt2 + 4)] = ((byte)(j >>> 24 & 0xFF));
    paramArrayOfByte2[(paramInt2 + 5)] = ((byte)(j >>> 16 & 0xFF));
    paramArrayOfByte2[(paramInt2 + 6)] = ((byte)(j >>> 8 & 0xFF));
    paramArrayOfByte2[(paramInt2 + 7)] = ((byte)(j & 0xFF));
  }
  
  public static byte[] enCrypt(byte[] paramArrayOfByte, String paramString)
    throws InvalidKeySpecException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    DESKeySpec localDESKeySpec = new DESKeySpec(paramString.getBytes());
    Object localObject2 = null;
    try
    {
      paramString = SecretKeyFactory.getInstance("DES");
      try
      {
        Cipher localCipher = Cipher.getInstance("DES");
      }
      catch (NoSuchAlgorithmException localNoSuchAlgorithmException1) {}
      localNoSuchAlgorithmException2.printStackTrace();
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException2)
    {
      paramString = null;
    }
    Object localObject1 = localObject2;
    ((Cipher)localObject1).init(1, paramString.generateSecret(localDESKeySpec));
    paramArrayOfByte = ((Cipher)localObject1).doFinal(paramArrayOfByte);
    int j = 0;
    while (j < paramArrayOfByte.length)
    {
      paramString = Integer.toHexString(paramArrayOfByte[j] & 0xFF);
      if (paramString.length() == 1)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("0");
        ((StringBuilder)localObject1).append(paramString);
        localStringBuffer.append(((StringBuilder)localObject1).toString());
      }
      else
      {
        localStringBuffer.append(paramString);
      }
      j += 1;
    }
    return localStringBuffer.toString().toUpperCase().getBytes();
  }
  
  protected static int[] generateWorkingKey(boolean paramBoolean, byte[] paramArrayOfByte, int paramInt)
  {
    int[] arrayOfInt = new int[32];
    boolean[] arrayOfBoolean1 = new boolean[56];
    boolean[] arrayOfBoolean2 = new boolean[56];
    int n = 0;
    int j = 0;
    int k;
    int m;
    for (;;)
    {
      int i2 = 1;
      if (j >= 56) {
        break;
      }
      k = jdField_a_of_type_ArrayOfByte[j];
      m = paramArrayOfByte[((k >>> 3) + paramInt)];
      if ((jdField_b_of_type_ArrayOfShort[(k & 0x7)] & m) == 0) {
        i2 = 0;
      }
      arrayOfBoolean1[j] = i2;
      j += 1;
    }
    paramInt = 0;
    for (;;)
    {
      j = n;
      if (paramInt >= 16) {
        break;
      }
      if (paramBoolean) {
        j = paramInt << 1;
      } else {
        j = 15 - paramInt << 1;
      }
      int i1 = j + 1;
      arrayOfInt[i1] = 0;
      arrayOfInt[j] = 0;
      k = 0;
      for (;;)
      {
        m = 28;
        if (k >= 28) {
          break;
        }
        m = jdField_b_of_type_ArrayOfByte[paramInt] + k;
        if (m < 28) {
          arrayOfBoolean2[k] = arrayOfBoolean1[m];
        } else {
          arrayOfBoolean2[k] = arrayOfBoolean1[(m - 28)];
        }
        k += 1;
      }
      while (m < 56)
      {
        k = jdField_b_of_type_ArrayOfByte[paramInt] + m;
        if (k < 56) {
          arrayOfBoolean2[m] = arrayOfBoolean1[k];
        } else {
          arrayOfBoolean2[m] = arrayOfBoolean1[(k - 28)];
        }
        m += 1;
      }
      k = 0;
      while (k < 24)
      {
        if (arrayOfBoolean2[jdField_c_of_type_ArrayOfByte[k]] != 0) {
          arrayOfInt[j] |= jdField_a_of_type_ArrayOfInt[k];
        }
        if (arrayOfBoolean2[jdField_c_of_type_ArrayOfByte[(k + 24)]] != 0) {
          arrayOfInt[i1] |= jdField_a_of_type_ArrayOfInt[k];
        }
        k += 1;
      }
      paramInt += 1;
    }
    while (j != 32)
    {
      paramInt = arrayOfInt[j];
      k = j + 1;
      m = arrayOfInt[k];
      arrayOfInt[j] = ((0xFC0000 & m) >>> 10 | (paramInt & 0xFC0000) << 6 | (paramInt & 0xFC0) << 10 | (m & 0xFC0) >>> 6);
      arrayOfInt[k] = ((paramInt & 0x3F) << 16 | (paramInt & 0x3F000) << 12 | (0x3F000 & m) >>> 4 | m & 0x3F);
      j += 2;
    }
    return arrayOfInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\DesUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */