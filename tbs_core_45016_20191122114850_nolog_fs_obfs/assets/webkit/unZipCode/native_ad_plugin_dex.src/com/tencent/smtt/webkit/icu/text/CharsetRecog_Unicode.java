package com.tencent.smtt.webkit.icu.text;

abstract class CharsetRecog_Unicode
  extends CharsetRecognizer
{
  static int a(byte paramByte1, byte paramByte2)
  {
    return (paramByte1 & 0xFF) << 8 | paramByte2 & 0xFF;
  }
  
  static int a(int paramInt1, int paramInt2)
  {
    int i;
    if (paramInt1 == 0)
    {
      i = paramInt2 - 10;
    }
    else if ((paramInt1 < 32) || (paramInt1 > 255))
    {
      i = paramInt2;
      if (paramInt1 != 10) {}
    }
    else
    {
      i = paramInt2 + 10;
    }
    if (i < 0) {
      return 0;
    }
    paramInt1 = i;
    if (i > 100) {
      paramInt1 = 100;
    }
    return paramInt1;
  }
  
  abstract String getName();
  
  abstract b match(a parama);
  
  static abstract class CharsetRecog_UTF_32
    extends CharsetRecog_Unicode
  {
    abstract int getChar(byte[] paramArrayOfByte, int paramInt);
    
    abstract String getName();
    
    b match(a parama)
    {
      byte[] arrayOfByte = parama.jdField_b_of_type_ArrayOfByte;
      int n = parama.jdField_b_of_type_Int / 4 * 4;
      if (n == 0) {
        return null;
      }
      int i;
      if (getChar(arrayOfByte, 0) == 65279) {
        i = 1;
      } else {
        i = 0;
      }
      int j = 0;
      int k = 0;
      int m = 0;
      while (j < n)
      {
        int i1 = getChar(arrayOfByte, j);
        if ((i1 >= 0) && (i1 < 1114111) && ((i1 < 55296) || (i1 > 57343))) {
          m += 1;
        } else {
          k += 1;
        }
        j += 4;
      }
      j = 80;
      if ((i != 0) && (k == 0)) {
        i = 100;
      } else if ((i != 0) && (m > k * 10)) {
        i = j;
      } else if ((m > 3) && (k == 0)) {
        i = 100;
      } else if ((m > 0) && (k == 0)) {
        i = j;
      } else if (m > k * 10) {
        i = 25;
      } else {
        i = 0;
      }
      if (i == 0) {
        return null;
      }
      return new b(parama, this, i);
    }
  }
  
  static class a
    extends CharsetRecog_Unicode
  {
    String getName()
    {
      return "UTF-16BE";
    }
    
    b match(a parama)
    {
      byte[] arrayOfByte = parama.b;
      int n = Math.min(arrayOfByte.length, 30);
      int m = 0;
      int k = 0;
      int j = 10;
      int i;
      for (;;)
      {
        i = j;
        if (k >= n - 1) {
          break;
        }
        i = a(arrayOfByte[k], arrayOfByte[(k + 1)]);
        if ((k == 0) && (i == 65279))
        {
          i = 100;
          break;
        }
        j = a(i, j);
        i = j;
        if (j == 0) {
          break;
        }
        if (j == 100)
        {
          i = j;
          break;
        }
        k += 2;
      }
      if ((n < 4) && (i < 100)) {
        i = m;
      }
      if (i > 0) {
        return new b(parama, this, i);
      }
      return null;
    }
  }
  
  static class b
    extends CharsetRecog_Unicode
  {
    String getName()
    {
      return "UTF-16LE";
    }
    
    b match(a parama)
    {
      byte[] arrayOfByte = parama.b;
      int n = Math.min(arrayOfByte.length, 30);
      int m = 0;
      int k = 0;
      int j = 10;
      int i;
      for (;;)
      {
        i = j;
        if (k >= n - 1) {
          break;
        }
        i = a(arrayOfByte[(k + 1)], arrayOfByte[k]);
        if ((k == 0) && (i == 65279))
        {
          i = 100;
          break;
        }
        j = a(i, j);
        i = j;
        if (j == 0) {
          break;
        }
        if (j == 100)
        {
          i = j;
          break;
        }
        k += 2;
      }
      if ((n < 4) && (i < 100)) {
        i = m;
      }
      if (i > 0) {
        return new b(parama, this, i);
      }
      return null;
    }
  }
  
  static class c
    extends CharsetRecog_Unicode.CharsetRecog_UTF_32
  {
    int getChar(byte[] paramArrayOfByte, int paramInt)
    {
      int i = paramArrayOfByte[(paramInt + 0)];
      int j = paramArrayOfByte[(paramInt + 1)];
      int k = paramArrayOfByte[(paramInt + 2)];
      return paramArrayOfByte[(paramInt + 3)] & 0xFF | (i & 0xFF) << 24 | (j & 0xFF) << 16 | (k & 0xFF) << 8;
    }
    
    String getName()
    {
      return "UTF-32BE";
    }
  }
  
  static class d
    extends CharsetRecog_Unicode.CharsetRecog_UTF_32
  {
    int getChar(byte[] paramArrayOfByte, int paramInt)
    {
      int i = paramArrayOfByte[(paramInt + 3)];
      int j = paramArrayOfByte[(paramInt + 2)];
      int k = paramArrayOfByte[(paramInt + 1)];
      return paramArrayOfByte[(paramInt + 0)] & 0xFF | (i & 0xFF) << 24 | (j & 0xFF) << 16 | (k & 0xFF) << 8;
    }
    
    String getName()
    {
      return "UTF-32LE";
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\text\CharsetRecog_Unicode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */