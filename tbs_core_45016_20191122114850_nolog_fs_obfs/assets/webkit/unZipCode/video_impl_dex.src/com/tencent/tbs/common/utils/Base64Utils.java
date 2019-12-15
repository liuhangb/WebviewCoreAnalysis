package com.tencent.tbs.common.utils;

import java.io.UnsupportedEncodingException;

public class Base64Utils
{
  public static final int CRLF = 4;
  public static final int DEFAULT = 0;
  public static final int NO_CLOSE = 16;
  public static final int NO_PADDING = 1;
  public static final int NO_WRAP = 2;
  public static final int URL_SAFE = 8;
  
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
    Decoder localDecoder = new Decoder(paramInt3, new byte[paramInt2 * 3 / 4]);
    if (localDecoder.process(paramArrayOfByte, paramInt1, paramInt2, true))
    {
      if (localDecoder.op == localDecoder.output.length) {
        return localDecoder.output;
      }
      paramArrayOfByte = new byte[localDecoder.op];
      System.arraycopy(localDecoder.output, 0, paramArrayOfByte, 0, localDecoder.op);
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
    Encoder localEncoder = new Encoder(paramInt3, null);
    int i = paramInt2 / 3 * 4;
    if (localEncoder.do_padding)
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
    if (localEncoder.do_newline)
    {
      i = paramInt3;
      if (paramInt2 > 0)
      {
        int j = (paramInt2 - 1) / 57;
        if (localEncoder.do_cr) {
          i = 2;
        } else {
          i = 1;
        }
        i = paramInt3 + (j + 1) * i;
      }
    }
    localEncoder.output = new byte[i];
    localEncoder.process(paramArrayOfByte, paramInt1, paramInt2, true);
    return localEncoder.output;
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
  
  static abstract class Coder
  {
    public int op;
    public byte[] output;
    
    public abstract int maxOutputSize(int paramInt);
    
    public abstract boolean process(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean);
  }
  
  static class Decoder
    extends Base64Utils.Coder
  {
    private static final int[] DECODE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private static final int[] DECODE_WEBSAFE = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -2, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 };
    private static final int EQUALS = -2;
    private static final int SKIP = -1;
    private final int[] alphabet;
    private int state;
    private int value;
    
    public Decoder(int paramInt, byte[] paramArrayOfByte)
    {
      this.output = paramArrayOfByte;
      if ((paramInt & 0x8) == 0) {
        paramArrayOfByte = DECODE;
      } else {
        paramArrayOfByte = DECODE_WEBSAFE;
      }
      this.alphabet = paramArrayOfByte;
      this.state = 0;
      this.value = 0;
    }
    
    public int maxOutputSize(int paramInt)
    {
      return paramInt * 3 / 4 + 10;
    }
    
    public boolean process(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      int n = this.state;
      if (n == 6) {
        return false;
      }
      int i2 = paramInt2 + paramInt1;
      paramInt2 = this.value;
      byte[] arrayOfByte = this.output;
      int[] arrayOfInt = this.alphabet;
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
            this.state = 6;
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
              this.state = 6;
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
              this.state = 6;
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
              this.state = 6;
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
              this.state = 6;
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
              this.state = 6;
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
        this.state = n;
        this.value = k;
        this.op = j;
        return true;
      }
      paramInt1 = j;
      switch (n)
      {
      default: 
        paramInt1 = j;
        break;
      case 4: 
        this.state = 6;
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
        this.state = 6;
        return false;
      }
      this.state = n;
      this.op = paramInt1;
      return true;
    }
  }
  
  static class Encoder
    extends Base64Utils.Coder
  {
    private static final byte[] ENCODE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
    private static final byte[] ENCODE_WEBSAFE = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
    public static final int LINE_GROUPS = 19;
    private final byte[] alphabet;
    private int count;
    public final boolean do_cr;
    public final boolean do_newline;
    public final boolean do_padding;
    private final byte[] tail;
    int tailLen;
    
    public Encoder(int paramInt, byte[] paramArrayOfByte)
    {
      this.output = paramArrayOfByte;
      boolean bool2 = true;
      boolean bool1;
      if ((paramInt & 0x1) == 0) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.do_padding = bool1;
      if ((paramInt & 0x2) == 0) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      this.do_newline = bool1;
      if ((paramInt & 0x4) != 0) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      this.do_cr = bool1;
      if ((paramInt & 0x8) == 0) {
        paramArrayOfByte = ENCODE;
      } else {
        paramArrayOfByte = ENCODE_WEBSAFE;
      }
      this.alphabet = paramArrayOfByte;
      this.tail = new byte[2];
      this.tailLen = 0;
      if (this.do_newline) {
        paramInt = 19;
      } else {
        paramInt = -1;
      }
      this.count = paramInt;
    }
    
    public int maxOutputSize(int paramInt)
    {
      return paramInt * 8 / 5 + 10;
    }
    
    public boolean process(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
    {
      byte[] arrayOfByte1 = this.alphabet;
      byte[] arrayOfByte2 = this.output;
      int i = this.count;
      int n = paramInt2 + paramInt1;
      paramInt2 = this.tailLen;
      int m = 0;
      int k = 0;
      int i1;
      switch (paramInt2)
      {
      default: 
        break;
      case 2: 
        j = paramInt1 + 1;
        if (j <= n)
        {
          byte[] arrayOfByte3 = this.tail;
          paramInt2 = arrayOfByte3[0];
          i1 = arrayOfByte3[1];
          paramInt1 = paramArrayOfByte[paramInt1];
          this.tailLen = 0;
          paramInt1 = (i1 & 0xFF) << 8 | (paramInt2 & 0xFF) << 16 | paramInt1 & 0xFF;
        }
        break;
      case 1: 
        if (paramInt1 + 2 <= n)
        {
          paramInt2 = this.tail[0];
          i1 = paramInt1 + 1;
          paramInt1 = paramArrayOfByte[paramInt1];
          j = i1 + 1;
          paramInt1 = (paramInt2 & 0xFF) << 16 | (paramInt1 & 0xFF) << 8 | paramArrayOfByte[i1] & 0xFF;
          this.tailLen = 0;
        }
        break;
      }
      paramInt2 = -1;
      int j = paramInt1;
      paramInt1 = paramInt2;
      if (paramInt1 != -1)
      {
        arrayOfByte2[0] = arrayOfByte1[(paramInt1 >> 18 & 0x3F)];
        arrayOfByte2[1] = arrayOfByte1[(paramInt1 >> 12 & 0x3F)];
        arrayOfByte2[2] = arrayOfByte1[(paramInt1 >> 6 & 0x3F)];
        arrayOfByte2[3] = arrayOfByte1[(paramInt1 & 0x3F)];
        paramInt2 = i - 1;
        if (paramInt2 == 0)
        {
          if (this.do_cr)
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
          paramInt2 = 19;
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
        paramInt2 = i;
      }
      for (;;)
      {
        i = j + 3;
        if (i > n) {
          break;
        }
        i1 = paramArrayOfByte[j];
        j = (paramArrayOfByte[(j + 1)] & 0xFF) << 8 | (i1 & 0xFF) << 16 | paramArrayOfByte[(j + 2)] & 0xFF;
        arrayOfByte2[paramInt1] = arrayOfByte1[(j >> 18 & 0x3F)];
        arrayOfByte2[(paramInt1 + 1)] = arrayOfByte1[(j >> 12 & 0x3F)];
        arrayOfByte2[(paramInt1 + 2)] = arrayOfByte1[(j >> 6 & 0x3F)];
        arrayOfByte2[(paramInt1 + 3)] = arrayOfByte1[(j & 0x3F)];
        paramInt1 += 4;
        paramInt2 -= 1;
        if (paramInt2 == 0)
        {
          if (this.do_cr)
          {
            paramInt2 = paramInt1 + 1;
            arrayOfByte2[paramInt1] = 13;
            paramInt1 = paramInt2;
          }
          j = paramInt1 + 1;
          arrayOfByte2[paramInt1] = 10;
          paramInt2 = 19;
          paramInt1 = j;
          j = i;
        }
        else
        {
          j = i;
        }
      }
      if (paramBoolean)
      {
        i = this.tailLen;
        if (j - i == n - 1)
        {
          if (i > 0)
          {
            j = this.tail[0];
            i = 1;
          }
          else
          {
            j = paramArrayOfByte[j];
            i = k;
          }
          j = (j & 0xFF) << 4;
          this.tailLen -= i;
          k = paramInt1 + 1;
          arrayOfByte2[paramInt1] = arrayOfByte1[(j >> 6 & 0x3F)];
          i = k + 1;
          arrayOfByte2[k] = arrayOfByte1[(j & 0x3F)];
          paramInt1 = i;
          if (this.do_padding)
          {
            j = i + 1;
            arrayOfByte2[i] = 61;
            paramInt1 = j + 1;
            arrayOfByte2[j] = 61;
          }
          i = paramInt1;
          if (this.do_newline)
          {
            if (this.do_cr)
            {
              i = paramInt1 + 1;
              arrayOfByte2[paramInt1] = 13;
              paramInt1 = i;
            }
            i = paramInt1 + 1;
            arrayOfByte2[paramInt1] = 10;
          }
        }
        else if (j - i == n - 2)
        {
          if (i > 1)
          {
            k = this.tail[0];
            i = 1;
          }
          else
          {
            k = paramArrayOfByte[j];
            j += 1;
            i = m;
          }
          if (this.tailLen > 0)
          {
            j = this.tail[i];
            i += 1;
          }
          else
          {
            j = paramArrayOfByte[j];
          }
          j = (k & 0xFF) << 10 | (j & 0xFF) << 2;
          this.tailLen -= i;
          i = paramInt1 + 1;
          arrayOfByte2[paramInt1] = arrayOfByte1[(j >> 12 & 0x3F)];
          k = i + 1;
          arrayOfByte2[i] = arrayOfByte1[(j >> 6 & 0x3F)];
          paramInt1 = k + 1;
          arrayOfByte2[k] = arrayOfByte1[(j & 0x3F)];
          if (this.do_padding)
          {
            i = paramInt1 + 1;
            arrayOfByte2[paramInt1] = 61;
            paramInt1 = i;
          }
          i = paramInt1;
          if (this.do_newline)
          {
            i = paramInt1;
            if (this.do_cr)
            {
              arrayOfByte2[paramInt1] = 13;
              i = paramInt1 + 1;
            }
            arrayOfByte2[i] = 10;
            i += 1;
          }
        }
        else
        {
          i = paramInt1;
          if (this.do_newline)
          {
            i = paramInt1;
            if (paramInt1 > 0)
            {
              i = paramInt1;
              if (paramInt2 != 19)
              {
                if (this.do_cr)
                {
                  i = paramInt1 + 1;
                  arrayOfByte2[paramInt1] = 13;
                  paramInt1 = i;
                }
                arrayOfByte2[paramInt1] = 10;
                i = paramInt1 + 1;
              }
            }
          }
        }
      }
      else if (j == n - 1)
      {
        arrayOfByte1 = this.tail;
        i = this.tailLen;
        this.tailLen = (i + 1);
        arrayOfByte1[i] = paramArrayOfByte[j];
        i = paramInt1;
      }
      else
      {
        i = paramInt1;
        if (j == n - 2)
        {
          arrayOfByte1 = this.tail;
          i = this.tailLen;
          this.tailLen = (i + 1);
          arrayOfByte1[i] = paramArrayOfByte[j];
          i = this.tailLen;
          this.tailLen = (i + 1);
          arrayOfByte1[i] = paramArrayOfByte[(j + 1)];
          i = paramInt1;
        }
      }
      this.op = i;
      this.count = paramInt2;
      return true;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\Base64Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */