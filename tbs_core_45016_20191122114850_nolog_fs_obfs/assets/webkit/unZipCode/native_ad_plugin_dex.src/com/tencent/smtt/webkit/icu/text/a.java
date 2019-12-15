package com.tencent.smtt.webkit.icu.text;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class a
{
  private static final List<a> jdField_a_of_type_JavaUtilList;
  int jdField_a_of_type_Int;
  InputStream jdField_a_of_type_JavaIoInputStream;
  boolean jdField_a_of_type_Boolean = false;
  byte[] jdField_a_of_type_ArrayOfByte = new byte['ὀ'];
  short[] jdField_a_of_type_ArrayOfShort = new short['Ā'];
  private boolean[] jdField_a_of_type_ArrayOfBoolean;
  int jdField_b_of_type_Int;
  private boolean jdField_b_of_type_Boolean = false;
  byte[] jdField_b_of_type_ArrayOfByte;
  
  static
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(new a(new d(), true));
    localArrayList.add(new a(new CharsetRecog_Unicode.a(), true));
    localArrayList.add(new a(new CharsetRecog_Unicode.b(), true));
    localArrayList.add(new a(new CharsetRecog_Unicode.c(), true));
    localArrayList.add(new a(new CharsetRecog_Unicode.d(), true));
    localArrayList.add(new a(new CharsetRecog_mbcs.d(), true));
    localArrayList.add(new a(new c.b(), true));
    localArrayList.add(new a(new c.a(), true));
    localArrayList.add(new a(new c.c(), true));
    localArrayList.add(new a(new CharsetRecog_mbcs.c(), true));
    localArrayList.add(new a(new CharsetRecog_mbcs.b.a(), true));
    localArrayList.add(new a(new CharsetRecog_mbcs.b.b(), true));
    localArrayList.add(new a(new CharsetRecog_mbcs.a(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.a(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.b(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.d(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.f(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.h(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.j(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.k(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.u(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.v(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.t(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.m(), true));
    localArrayList.add(new a(new CharsetRecog_sbcs.s(), false));
    localArrayList.add(new a(new CharsetRecog_sbcs.r(), false));
    localArrayList.add(new a(new CharsetRecog_sbcs.p(), false));
    localArrayList.add(new a(new CharsetRecog_sbcs.o(), false));
    jdField_a_of_type_JavaUtilList = Collections.unmodifiableList(localArrayList);
  }
  
  private void a()
  {
    int k;
    if (this.jdField_b_of_type_Boolean)
    {
      int i2 = 0;
      int i3 = 0;
      k = 0;
      j = 0;
      int m = 0;
      while ((i2 < this.jdField_b_of_type_Int) && (i3 < this.jdField_a_of_type_ArrayOfByte.length))
      {
        int i = this.jdField_b_of_type_ArrayOfByte[i2];
        int i4 = k;
        int i1 = j;
        int n = m;
        if (i == 60)
        {
          i1 = j;
          if (m != 0) {
            i1 = j + 1;
          }
          i4 = k + 1;
          n = 1;
        }
        j = i3;
        if (n == 0)
        {
          this.jdField_a_of_type_ArrayOfByte[i3] = i;
          j = i3 + 1;
        }
        m = n;
        if (i == 62) {
          m = 0;
        }
        i2 += 1;
        i3 = j;
        k = i4;
        j = i1;
      }
      this.jdField_a_of_type_Int = i3;
    }
    else
    {
      k = 0;
      j = 0;
    }
    if ((k < 5) || (k / 5 < j) || ((this.jdField_a_of_type_Int < 100) && (this.jdField_b_of_type_Int > 600)))
    {
      k = this.jdField_b_of_type_Int;
      j = k;
      if (k > 8000) {
        j = 8000;
      }
      k = 0;
      while (k < j)
      {
        this.jdField_a_of_type_ArrayOfByte[k] = this.jdField_b_of_type_ArrayOfByte[k];
        k += 1;
      }
      this.jdField_a_of_type_Int = k;
    }
    Arrays.fill(this.jdField_a_of_type_ArrayOfShort, (short)0);
    int j = 0;
    while (j < this.jdField_a_of_type_Int)
    {
      k = this.jdField_a_of_type_ArrayOfByte[j] & 0xFF;
      short[] arrayOfShort = this.jdField_a_of_type_ArrayOfShort;
      arrayOfShort[k] = ((short)(arrayOfShort[k] + 1));
      j += 1;
    }
    this.jdField_a_of_type_Boolean = false;
    j = 128;
    while (j <= 159)
    {
      if (this.jdField_a_of_type_ArrayOfShort[j] != 0)
      {
        this.jdField_a_of_type_Boolean = true;
        return;
      }
      j += 1;
    }
  }
  
  public a a(byte[] paramArrayOfByte)
  {
    this.jdField_b_of_type_ArrayOfByte = paramArrayOfByte;
    this.jdField_b_of_type_Int = paramArrayOfByte.length;
    return this;
  }
  
  public b[] a()
  {
    ArrayList localArrayList = new ArrayList();
    a();
    int i = 0;
    while (i < jdField_a_of_type_JavaUtilList.size())
    {
      Object localObject = (a)jdField_a_of_type_JavaUtilList.get(i);
      boolean[] arrayOfBoolean = this.jdField_a_of_type_ArrayOfBoolean;
      boolean bool;
      if (arrayOfBoolean != null) {
        int j = arrayOfBoolean[i];
      } else {
        bool = ((a)localObject).jdField_a_of_type_Boolean;
      }
      if (bool)
      {
        localObject = ((a)localObject).jdField_a_of_type_ComTencentSmttWebkitIcuTextCharsetRecognizer.match(this);
        if (localObject != null) {
          localArrayList.add(localObject);
        }
      }
      i += 1;
    }
    Collections.sort(localArrayList);
    Collections.reverse(localArrayList);
    return (b[])localArrayList.toArray(new b[localArrayList.size()]);
  }
  
  private static class a
  {
    CharsetRecognizer jdField_a_of_type_ComTencentSmttWebkitIcuTextCharsetRecognizer;
    boolean jdField_a_of_type_Boolean;
    
    a(CharsetRecognizer paramCharsetRecognizer, boolean paramBoolean)
    {
      this.jdField_a_of_type_ComTencentSmttWebkitIcuTextCharsetRecognizer = paramCharsetRecognizer;
      this.jdField_a_of_type_Boolean = paramBoolean;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\text\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */