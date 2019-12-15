package com.tencent.smtt.webkit.icu.text;

class d
  extends CharsetRecognizer
{
  String getName()
  {
    return "UTF-8";
  }
  
  b match(a parama)
  {
    byte[] arrayOfByte = parama.jdField_b_of_type_ArrayOfByte;
    int i = parama.jdField_b_of_type_Int;
    int i2 = 0;
    int k;
    if ((i >= 3) && ((arrayOfByte[0] & 0xFF) == 239) && ((arrayOfByte[1] & 0xFF) == 187) && ((arrayOfByte[2] & 0xFF) == 191)) {
      k = 1;
    } else {
      k = 0;
    }
    i = 0;
    int n = 0;
    int m = 0;
    while (i < parama.jdField_b_of_type_Int)
    {
      int j = arrayOfByte[i];
      if ((j & 0x80) == 0)
      {
        j = n;
      }
      else
      {
        if ((j & 0xE0) == 192)
        {
          j = 1;
        }
        else if ((j & 0xF0) == 224)
        {
          j = 2;
        }
        else
        {
          if ((j & 0xF8) != 240) {
            break label230;
          }
          j = 3;
        }
        int i1;
        int i3;
        do
        {
          i1 = i + 1;
          if (i1 >= parama.jdField_b_of_type_Int)
          {
            i = i1;
            j = n;
            break;
          }
          if ((arrayOfByte[i1] & 0xC0) != 128)
          {
            j = n + 1;
            i = i1;
            break;
          }
          i3 = j - 1;
          i = i1;
          j = i3;
        } while (i3 != 0);
        m += 1;
        i = i1;
        j = n;
        break label235;
        label230:
        j = n + 1;
      }
      label235:
      i += 1;
      n = j;
    }
    if ((k != 0) && (n == 0))
    {
      i = 100;
    }
    else if ((k != 0) && (m > n * 10))
    {
      i = 80;
    }
    else if ((m > 3) && (n == 0))
    {
      i = 100;
    }
    else if ((m > 0) && (n == 0))
    {
      i = 80;
    }
    else if ((m == 0) && (n == 0))
    {
      i = 15;
    }
    else
    {
      i = i2;
      if (m > n * 10) {
        i = 25;
      }
    }
    if (i == 0) {
      return null;
    }
    return new b(parama, this, i);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\text\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */