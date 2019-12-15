package com.tencent.mtt.base.utils;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;

public class Base64Utils
{
  public static final int CRLF = 4;
  public static final int DEFAULT = 0;
  public static final int NO_CLOSE = 16;
  public static final int NO_PADDING = 1;
  public static final int NO_WRAP = 2;
  public static final int URL_SAFE = 8;
  
  static
  {
    jdField_a_of_type_Boolean = Base64Utils.class.desiredAssertionStatus() ^ true;
  }
  
  public static byte[] decode(String paramString, int paramInt)
  {
    return decode(paramString.getBytes(), paramInt);
  }
  
  public static byte[] decode(byte[] paramArrayOfByte, int paramInt)
  {
    return decode(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }
  
  public static byte[] decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    b localb = new b(paramInt3, new byte[paramInt2 * 3 / 4]);
    if (localb.a(paramArrayOfByte, paramInt1, paramInt2, true))
    {
      if (localb.jdField_a_of_type_Int == localb.jdField_a_of_type_ArrayOfByte.length) {
        return localb.jdField_a_of_type_ArrayOfByte;
      }
      paramArrayOfByte = new byte[localb.jdField_a_of_type_Int];
      System.arraycopy(localb.jdField_a_of_type_ArrayOfByte, 0, paramArrayOfByte, 0, localb.jdField_a_of_type_Int);
      return paramArrayOfByte;
    }
    throw new IllegalArgumentException("bad base-64");
  }
  
  public static byte[] encode(byte[] paramArrayOfByte, int paramInt)
  {
    return encode(paramArrayOfByte, 0, paramArrayOfByte.length, paramInt);
  }
  
  public static byte[] encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    c localc = new c(paramInt3, null);
    int i = paramInt2 / 3 * 4;
    if (localc.jdField_a_of_type_Boolean)
    {
      paramInt3 = i;
      if (paramInt2 % 3 > 0) {
        paramInt3 = i + 4;
      }
    }
    else
    {
      paramInt3 = i;
      switch (paramInt2 % 3)
      {
      default: 
        paramInt3 = i;
        break;
      case 2: 
        paramInt3 = i + 3;
        break;
      case 1: 
        paramInt3 = i + 2;
      }
    }
    i = paramInt3;
    if (localc.b)
    {
      i = paramInt3;
      if (paramInt2 > 0)
      {
        int j = (paramInt2 - 1) / 57;
        if (localc.c) {
          i = 2;
        } else {
          i = 1;
        }
        i = paramInt3 + (j + 1) * i;
      }
    }
    localc.jdField_a_of_type_ArrayOfByte = new byte[i];
    localc.a(paramArrayOfByte, paramInt1, paramInt2, true);
    if ((!jdField_a_of_type_Boolean) && (localc.jdField_a_of_type_Int != i)) {
      throw new AssertionError();
    }
    return localc.jdField_a_of_type_ArrayOfByte;
  }
  
  public static String encodeToString(byte[] paramArrayOfByte, int paramInt)
  {
    try
    {
      paramArrayOfByte = new String(encode(paramArrayOfByte, paramInt), "US-ASCII");
      return paramArrayOfByte;
    }
    catch (UnsupportedEncodingException paramArrayOfByte)
    {
      throw new AssertionError(paramArrayOfByte);
    }
  }
  
  public static String encodeToString(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    try
    {
      paramArrayOfByte = new String(encode(paramArrayOfByte, paramInt1, paramInt2, paramInt3), "US-ASCII");
      return paramArrayOfByte;
    }
    catch (UnsupportedEncodingException paramArrayOfByte)
    {
      throw new AssertionError(paramArrayOfByte);
    }
  }
  
  public static String getBase64UrlHeader(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return null;
    }
    int i = paramString.indexOf(";base64,");
    if (i == -1) {
      return null;
    }
    return paramString.substring(0, i + 8);
  }
  
  static abstract class a
  {
    public int a;
    public byte[] a;
  }
  
  static class b
    extends Base64Utils.a
  {
    private static final int[] a;
    private static final int[] jdField_b_of_type_ArrayOfInt = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private int jdField_b_of_type_Int;
    private int jdField_c_of_type_Int;
    private final int[] jdField_c_of_type_ArrayOfInt;
    
    static
    {
      jdField_a_of_type_ArrayOfInt = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    }
    
    public b(int paramInt, byte[] paramArrayOfByte)
    {
      this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
      if ((paramInt & 0x8) == 0) {
        paramArrayOfByte = jdField_a_of_type_ArrayOfInt;
      } else {
        paramArrayOfByte = jdField_b_of_type_ArrayOfInt;
      }
      this.jdField_c_of_type_ArrayOfInt = paramArrayOfByte;
      this.jdField_b_of_type_Int = 0;
      this.jdField_c_of_type_Int = 0;
    }
    
    public boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      int n = this.jdField_b_of_type_Int;
      if (n == 6) {
        return false;
      }
      int i2 = paramInt2 + paramInt1;
      paramInt2 = this.jdField_c_of_type_Int;
      byte[] arrayOfByte = this.jdField_a_of_type_ArrayOfByte;
      int[] arrayOfInt = this.jdField_c_of_type_ArrayOfInt;
      int j = 0;
      int i = paramInt1;
      paramInt1 = j;
      int k;
      for (;;)
      {
        k = paramInt2;
        j = paramInt1;
        if (i >= i2) {
          break;
        }
        k = paramInt2;
        j = paramInt1;
        int m = i;
        if (n == 0)
        {
          for (;;)
          {
            k = i + 4;
            if (k > i2) {
              break;
            }
            j = arrayOfInt[(paramArrayOfByte[i] & 0xFF)] << 18 | arrayOfInt[(paramArrayOfByte[(i + 1)] & 0xFF)] << 12 | arrayOfInt[(paramArrayOfByte[(i + 2)] & 0xFF)] << 6 | arrayOfInt[(paramArrayOfByte[(i + 3)] & 0xFF)];
            paramInt2 = j;
            if (j < 0) {
              break;
            }
            arrayOfByte[(paramInt1 + 2)] = ((byte)j);
            arrayOfByte[(paramInt1 + 1)] = ((byte)(j >> 8));
            arrayOfByte[paramInt1] = ((byte)(j >> 16));
            paramInt1 += 3;
            i = k;
            paramInt2 = j;
          }
          k = paramInt2;
          j = paramInt1;
          m = i;
          if (i >= i2)
          {
            k = paramInt2;
            j = paramInt1;
            break;
          }
        }
        int i1 = arrayOfInt[(paramArrayOfByte[m] & 0xFF)];
        switch (n)
        {
        default: 
          paramInt1 = n;
          paramInt2 = k;
          i = j;
          break;
        case 5: 
          paramInt1 = n;
          paramInt2 = k;
          i = j;
          if (i1 != -1)
          {
            this.jdField_b_of_type_Int = 6;
            return false;
          }
          break;
        case 4: 
          if (i1 == -2)
          {
            paramInt1 = n + 1;
            paramInt2 = k;
            i = j;
          }
          else
          {
            paramInt1 = n;
            paramInt2 = k;
            i = j;
            if (i1 != -1)
            {
              this.jdField_b_of_type_Int = 6;
              return false;
            }
          }
          break;
        case 3: 
          if (i1 >= 0)
          {
            paramInt2 = i1 | k << 6;
            arrayOfByte[(j + 2)] = ((byte)paramInt2);
            arrayOfByte[(j + 1)] = ((byte)(paramInt2 >> 8));
            arrayOfByte[j] = ((byte)(paramInt2 >> 16));
            i = j + 3;
            paramInt1 = 0;
          }
          else if (i1 == -2)
          {
            arrayOfByte[(j + 1)] = ((byte)(k >> 2));
            arrayOfByte[j] = ((byte)(k >> 10));
            i = j + 2;
            paramInt1 = 5;
            paramInt2 = k;
          }
          else
          {
            paramInt1 = n;
            paramInt2 = k;
            i = j;
            if (i1 != -1)
            {
              this.jdField_b_of_type_Int = 6;
              return false;
            }
          }
          break;
        case 2: 
          if (i1 >= 0)
          {
            paramInt1 = n + 1;
            paramInt2 = i1 | k << 6;
            i = j;
          }
          else if (i1 == -2)
          {
            arrayOfByte[j] = ((byte)(k >> 4));
            i = j + 1;
            paramInt1 = 4;
            paramInt2 = k;
          }
          else
          {
            paramInt1 = n;
            paramInt2 = k;
            i = j;
            if (i1 != -1)
            {
              this.jdField_b_of_type_Int = 6;
              return false;
            }
          }
          break;
        case 1: 
          if (i1 >= 0)
          {
            paramInt1 = n + 1;
            paramInt2 = i1 | k << 6;
            i = j;
          }
          else
          {
            paramInt1 = n;
            paramInt2 = k;
            i = j;
            if (i1 != -1)
            {
              this.jdField_b_of_type_Int = 6;
              return false;
            }
          }
          break;
        case 0: 
          if (i1 >= 0)
          {
            paramInt1 = n + 1;
            paramInt2 = i1;
            i = j;
          }
          else
          {
            paramInt1 = n;
            paramInt2 = k;
            i = j;
            if (i1 != -1)
            {
              this.jdField_b_of_type_Int = 6;
              return false;
            }
          }
          break;
        }
        j = m + 1;
        n = paramInt1;
        paramInt1 = i;
        i = j;
      }
      if (!paramBoolean)
      {
        this.jdField_b_of_type_Int = n;
        this.jdField_c_of_type_Int = k;
        this.jdField_a_of_type_Int = j;
        return true;
      }
      paramInt1 = j;
      switch (n)
      {
      default: 
        paramInt1 = j;
        break;
      case 4: 
        this.jdField_b_of_type_Int = 6;
        return false;
      case 3: 
        paramInt2 = j + 1;
        arrayOfByte[j] = ((byte)(k >> 10));
        paramInt1 = paramInt2 + 1;
        arrayOfByte[paramInt2] = ((byte)(k >> 2));
        break;
      case 2: 
        arrayOfByte[j] = ((byte)(k >> 4));
        paramInt1 = j + 1;
        break;
      case 1: 
        this.jdField_b_of_type_Int = 6;
        return false;
      }
      this.jdField_b_of_type_Int = n;
      this.jdField_a_of_type_Int = paramInt1;
      return true;
    }
  }
  
  static class c
    extends Base64Utils.a
  {
    private static final byte[] jdField_b_of_type_ArrayOfByte = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
    private static final byte[] jdField_c_of_type_ArrayOfByte = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
    public final boolean a;
    int jdField_b_of_type_Int;
    public final boolean b;
    private int jdField_c_of_type_Int;
    public final boolean c;
    private final byte[] jdField_d_of_type_ArrayOfByte;
    private final byte[] e;
    
    public c(int paramInt, byte[] paramArrayOfByte)
    {
      this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
      boolean bool2 = true;
      boolean bool1;
      if ((paramInt & 0x1) == 0) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.jdField_a_of_type_Boolean = bool1;
      if ((paramInt & 0x2) == 0) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.jdField_b_of_type_Boolean = bool1;
      if ((paramInt & 0x4) != 0) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      this.jdField_c_of_type_Boolean = bool1;
      if ((paramInt & 0x8) == 0) {
        paramArrayOfByte = jdField_b_of_type_ArrayOfByte;
      } else {
        paramArrayOfByte = jdField_c_of_type_ArrayOfByte;
      }
      this.e = paramArrayOfByte;
      this.jdField_d_of_type_ArrayOfByte = new byte[2];
      this.jdField_b_of_type_Int = 0;
      if (this.jdField_b_of_type_Boolean) {
        paramInt = 19;
      } else {
        paramInt = -1;
      }
      this.jdField_c_of_type_Int = paramInt;
    }
    
    public boolean a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      byte[] arrayOfByte1 = this.e;
      byte[] arrayOfByte2 = this.jdField_a_of_type_ArrayOfByte;
      int j = this.jdField_c_of_type_Int;
      int i1 = paramInt2 + paramInt1;
      paramInt2 = this.jdField_b_of_type_Int;
      int n = 0;
      int m = 0;
      int k;
      switch (paramInt2)
      {
      default: 
        break;
      case 2: 
        paramInt2 = paramInt1 + 1;
        if (paramInt2 <= i1)
        {
          byte[] arrayOfByte3 = this.jdField_d_of_type_ArrayOfByte;
          i = arrayOfByte3[0];
          k = arrayOfByte3[1];
          paramInt1 = paramArrayOfByte[paramInt1];
          this.jdField_b_of_type_Int = 0;
          paramInt1 = (k & 0xFF) << 8 | (i & 0xFF) << 16 | paramInt1 & 0xFF;
        }
        break;
      case 1: 
        if (paramInt1 + 2 <= i1)
        {
          i = this.jdField_d_of_type_ArrayOfByte[0];
          k = paramInt1 + 1;
          paramInt1 = paramArrayOfByte[paramInt1];
          paramInt2 = k + 1;
          paramInt1 = (i & 0xFF) << 16 | (paramInt1 & 0xFF) << 8 | paramArrayOfByte[k] & 0xFF;
          this.jdField_b_of_type_Int = 0;
        }
        break;
      }
      int i = -1;
      paramInt2 = paramInt1;
      paramInt1 = i;
      if (paramInt1 != -1)
      {
        arrayOfByte2[0] = arrayOfByte1[(paramInt1 >> 18 & 0x3F)];
        arrayOfByte2[1] = arrayOfByte1[(paramInt1 >> 12 & 0x3F)];
        arrayOfByte2[2] = arrayOfByte1[(paramInt1 >> 6 & 0x3F)];
        arrayOfByte2[3] = arrayOfByte1[(paramInt1 & 0x3F)];
        j -= 1;
        if (j == 0)
        {
          if (this.jdField_c_of_type_Boolean)
          {
            paramInt1 = 5;
            arrayOfByte2[4] = 13;
          }
          else
          {
            paramInt1 = 4;
          }
          i = paramInt1 + 1;
          arrayOfByte2[paramInt1] = 10;
          j = 19;
          paramInt1 = i;
        }
        else
        {
          paramInt1 = 4;
        }
      }
      else
      {
        paramInt1 = 0;
      }
      for (;;)
      {
        i = paramInt2 + 3;
        if (i > i1) {
          break;
        }
        k = paramArrayOfByte[paramInt2];
        paramInt2 = (paramArrayOfByte[(paramInt2 + 1)] & 0xFF) << 8 | (k & 0xFF) << 16 | paramArrayOfByte[(paramInt2 + 2)] & 0xFF;
        arrayOfByte2[paramInt1] = arrayOfByte1[(paramInt2 >> 18 & 0x3F)];
        arrayOfByte2[(paramInt1 + 1)] = arrayOfByte1[(paramInt2 >> 12 & 0x3F)];
        arrayOfByte2[(paramInt1 + 2)] = arrayOfByte1[(paramInt2 >> 6 & 0x3F)];
        arrayOfByte2[(paramInt1 + 3)] = arrayOfByte1[(paramInt2 & 0x3F)];
        paramInt1 += 4;
        j -= 1;
        if (j == 0)
        {
          if (this.jdField_c_of_type_Boolean)
          {
            paramInt2 = paramInt1 + 1;
            arrayOfByte2[paramInt1] = 13;
            paramInt1 = paramInt2;
          }
          k = paramInt1 + 1;
          arrayOfByte2[paramInt1] = 10;
          paramInt2 = i;
          j = 19;
          paramInt1 = k;
        }
        else
        {
          paramInt2 = i;
        }
      }
      if (paramBoolean)
      {
        i = this.jdField_b_of_type_Int;
        if (paramInt2 - i == i1 - 1)
        {
          if (i > 0)
          {
            k = this.jdField_d_of_type_ArrayOfByte[0];
            i = 1;
          }
          else
          {
            k = paramArrayOfByte[paramInt2];
            paramInt2 += 1;
            i = m;
          }
          k = (k & 0xFF) << 4;
          this.jdField_b_of_type_Int -= i;
          m = paramInt1 + 1;
          arrayOfByte2[paramInt1] = arrayOfByte1[(k >> 6 & 0x3F)];
          i = m + 1;
          arrayOfByte2[m] = arrayOfByte1[(k & 0x3F)];
          paramInt1 = i;
          if (this.jdField_a_of_type_Boolean)
          {
            k = i + 1;
            arrayOfByte2[i] = 61;
            paramInt1 = k + 1;
            arrayOfByte2[k] = 61;
          }
          i = paramInt1;
          k = paramInt2;
          if (this.jdField_b_of_type_Boolean)
          {
            if (this.jdField_c_of_type_Boolean)
            {
              i = paramInt1 + 1;
              arrayOfByte2[paramInt1] = 13;
              paramInt1 = i;
            }
            i = paramInt1 + 1;
            arrayOfByte2[paramInt1] = 10;
            k = paramInt2;
          }
        }
        else if (paramInt2 - i == i1 - 2)
        {
          if (i > 1)
          {
            k = this.jdField_d_of_type_ArrayOfByte[0];
            i = 1;
          }
          else
          {
            k = paramArrayOfByte[paramInt2];
            paramInt2 += 1;
            i = n;
          }
          if (this.jdField_b_of_type_Int > 0)
          {
            paramArrayOfByte = this.jdField_d_of_type_ArrayOfByte;
            m = i + 1;
            i = paramArrayOfByte[i];
          }
          else
          {
            n = paramArrayOfByte[paramInt2];
            m = i;
            i = n;
            paramInt2 += 1;
          }
          i = (k & 0xFF) << 10 | (i & 0xFF) << 2;
          this.jdField_b_of_type_Int -= m;
          k = paramInt1 + 1;
          arrayOfByte2[paramInt1] = arrayOfByte1[(i >> 12 & 0x3F)];
          m = k + 1;
          arrayOfByte2[k] = arrayOfByte1[(i >> 6 & 0x3F)];
          paramInt1 = m + 1;
          arrayOfByte2[m] = arrayOfByte1[(i & 0x3F)];
          if (this.jdField_a_of_type_Boolean)
          {
            i = paramInt1 + 1;
            arrayOfByte2[paramInt1] = 61;
            paramInt1 = i;
          }
          i = paramInt1;
          if (this.jdField_b_of_type_Boolean)
          {
            i = paramInt1;
            if (this.jdField_c_of_type_Boolean)
            {
              arrayOfByte2[paramInt1] = 13;
              i = paramInt1 + 1;
            }
            arrayOfByte2[i] = 10;
            i += 1;
          }
          k = paramInt2;
        }
        else
        {
          i = paramInt1;
          k = paramInt2;
          if (this.jdField_b_of_type_Boolean)
          {
            i = paramInt1;
            k = paramInt2;
            if (paramInt1 > 0)
            {
              i = paramInt1;
              k = paramInt2;
              if (j != 19)
              {
                if (this.jdField_c_of_type_Boolean)
                {
                  i = paramInt1 + 1;
                  arrayOfByte2[paramInt1] = 13;
                  paramInt1 = i;
                }
                i = paramInt1 + 1;
                arrayOfByte2[paramInt1] = 10;
                k = paramInt2;
              }
            }
          }
        }
        if ((!jdField_d_of_type_Boolean) && (this.jdField_b_of_type_Int != 0)) {
          throw new AssertionError();
        }
        m = i;
        if (!jdField_d_of_type_Boolean) {
          if (k == i1) {
            m = i;
          } else {
            throw new AssertionError();
          }
        }
      }
      else if (paramInt2 == i1 - 1)
      {
        arrayOfByte1 = this.jdField_d_of_type_ArrayOfByte;
        i = this.jdField_b_of_type_Int;
        this.jdField_b_of_type_Int = (i + 1);
        arrayOfByte1[i] = paramArrayOfByte[paramInt2];
        m = paramInt1;
      }
      else
      {
        m = paramInt1;
        if (paramInt2 == i1 - 2)
        {
          arrayOfByte1 = this.jdField_d_of_type_ArrayOfByte;
          i = this.jdField_b_of_type_Int;
          this.jdField_b_of_type_Int = (i + 1);
          arrayOfByte1[i] = paramArrayOfByte[paramInt2];
          i = this.jdField_b_of_type_Int;
          this.jdField_b_of_type_Int = (i + 1);
          arrayOfByte1[i] = paramArrayOfByte[(paramInt2 + 1)];
          m = paramInt1;
        }
      }
      this.jdField_a_of_type_Int = m;
      this.jdField_c_of_type_Int = j;
      return true;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\Base64Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */