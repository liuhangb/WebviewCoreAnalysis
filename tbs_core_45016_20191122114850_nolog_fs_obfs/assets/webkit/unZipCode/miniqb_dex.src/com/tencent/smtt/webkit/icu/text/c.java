package com.tencent.smtt.webkit.icu.text;

abstract class c
  extends CharsetRecognizer
{
  int a(byte[] paramArrayOfByte, int paramInt, byte[][] paramArrayOfByte1)
  {
    int i = 0;
    int n = 0;
    int j = 0;
    label104:
    int i3;
    for (int m = 0; i < paramInt; m = i3)
    {
      int k = j;
      int i1;
      int i2;
      if (paramArrayOfByte[i] == 27)
      {
        k = 0;
        if (k < paramArrayOfByte1.length)
        {
          byte[] arrayOfByte = paramArrayOfByte1[k];
          if (paramInt - i >= arrayOfByte.length) {
            i1 = 1;
          }
          for (;;)
          {
            if (i1 >= arrayOfByte.length) {
              break label104;
            }
            if (arrayOfByte[i1] != paramArrayOfByte[(i + i1)])
            {
              k += 1;
              break;
            }
            i1 += 1;
          }
          i2 = n + 1;
          i1 = i + (arrayOfByte.length - 1);
          i3 = m;
        }
        else
        {
          k = j + 1;
        }
      }
      else if (paramArrayOfByte[i] != 14)
      {
        i1 = i;
        i2 = n;
        j = k;
        i3 = m;
        if (paramArrayOfByte[i] != 15) {}
      }
      else
      {
        i3 = m + 1;
        j = k;
        i2 = n;
        i1 = i;
      }
      i = i1 + 1;
      n = i2;
    }
    if (n == 0) {
      return 0;
    }
    i = (n * 100 - j * 100) / (j + n);
    j = n + m;
    paramInt = i;
    if (j < 5) {
      paramInt = i - (5 - j) * 10;
    }
    i = paramInt;
    if (paramInt < 0) {
      i = 0;
    }
    return i;
  }
  
  static class a
    extends c
  {
    private byte[][] a;
    
    a()
    {
      byte[] arrayOfByte1 = { 27, 36, 41, 65 };
      byte[] arrayOfByte2 = { 27, 36, 41, 71 };
      byte[] arrayOfByte3 = { 27, 36, 42, 72 };
      byte[] arrayOfByte4 = { 27, 36, 41, 69 };
      byte[] arrayOfByte5 = { 27, 36, 43, 73 };
      byte[] arrayOfByte6 = { 27, 36, 43, 74 };
      byte[] arrayOfByte7 = { 27, 36, 43, 76 };
      byte[] arrayOfByte8 = { 27, 78 };
      byte[] arrayOfByte9 = { 27, 79 };
      this.a = new byte[][] { arrayOfByte1, arrayOfByte2, arrayOfByte3, arrayOfByte4, arrayOfByte5, arrayOfByte6, { 27, 36, 43, 75 }, arrayOfByte7, { 27, 36, 43, 77 }, arrayOfByte8, arrayOfByte9 };
    }
    
    String getName()
    {
      return "ISO-2022-CN";
    }
    
    b match(a parama)
    {
      int i = a(parama.jdField_a_of_type_ArrayOfByte, parama.jdField_a_of_type_Int, this.a);
      if (i == 0) {
        return null;
      }
      return new b(parama, this, i);
    }
  }
  
  static class b
    extends c
  {
    private byte[][] a;
    
    b()
    {
      byte[] arrayOfByte1 = { 27, 36, 40, 68 };
      byte[] arrayOfByte2 = { 27, 36, 65 };
      byte[] arrayOfByte3 = { 27, 36, 66 };
      byte[] arrayOfByte4 = { 27, 40, 72 };
      byte[] arrayOfByte5 = { 27, 40, 73 };
      byte[] arrayOfByte6 = { 27, 46, 70 };
      this.a = new byte[][] { { 27, 36, 40, 67 }, arrayOfByte1, { 27, 36, 64 }, arrayOfByte2, arrayOfByte3, { 27, 38, 64 }, { 27, 40, 66 }, arrayOfByte4, arrayOfByte5, { 27, 40, 74 }, { 27, 46, 65 }, arrayOfByte6 };
    }
    
    String getName()
    {
      return "ISO-2022-JP";
    }
    
    b match(a parama)
    {
      int i = a(parama.jdField_a_of_type_ArrayOfByte, parama.jdField_a_of_type_Int, this.a);
      if (i == 0) {
        return null;
      }
      return new b(parama, this, i);
    }
  }
  
  static class c
    extends c
  {
    private byte[][] a = { { 27, 36, 41, 67 } };
    
    String getName()
    {
      return "ISO-2022-KR";
    }
    
    b match(a parama)
    {
      int i = a(parama.jdField_a_of_type_ArrayOfByte, parama.jdField_a_of_type_Int, this.a);
      if (i == 0) {
        return null;
      }
      return new b(parama, this, i);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\text\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */