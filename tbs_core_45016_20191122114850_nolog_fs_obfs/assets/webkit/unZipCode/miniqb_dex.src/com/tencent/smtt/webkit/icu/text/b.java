package com.tencent.smtt.webkit.icu.text;

import java.io.InputStream;

public class b
  implements Comparable<b>
{
  private int jdField_a_of_type_Int;
  private InputStream jdField_a_of_type_JavaIoInputStream = null;
  private String jdField_a_of_type_JavaLangString;
  private byte[] jdField_a_of_type_ArrayOfByte = null;
  private int jdField_b_of_type_Int;
  private String jdField_b_of_type_JavaLangString;
  
  b(a parama, CharsetRecognizer paramCharsetRecognizer, int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
    if (parama.jdField_a_of_type_JavaIoInputStream == null)
    {
      this.jdField_a_of_type_ArrayOfByte = parama.jdField_b_of_type_ArrayOfByte;
      this.jdField_b_of_type_Int = parama.jdField_b_of_type_Int;
    }
    this.jdField_a_of_type_JavaIoInputStream = parama.jdField_a_of_type_JavaIoInputStream;
    this.jdField_a_of_type_JavaLangString = paramCharsetRecognizer.getName();
    this.jdField_b_of_type_JavaLangString = paramCharsetRecognizer.a();
  }
  
  b(a parama, CharsetRecognizer paramCharsetRecognizer, int paramInt, String paramString1, String paramString2)
  {
    this.jdField_a_of_type_Int = paramInt;
    if (parama.jdField_a_of_type_JavaIoInputStream == null)
    {
      this.jdField_a_of_type_ArrayOfByte = parama.jdField_b_of_type_ArrayOfByte;
      this.jdField_b_of_type_Int = parama.jdField_b_of_type_Int;
    }
    this.jdField_a_of_type_JavaIoInputStream = parama.jdField_a_of_type_JavaIoInputStream;
    this.jdField_a_of_type_JavaLangString = paramString1;
    this.jdField_b_of_type_JavaLangString = paramString2;
  }
  
  public int a(b paramb)
  {
    int i = this.jdField_a_of_type_Int;
    int j = paramb.jdField_a_of_type_Int;
    if (i > j) {
      return 1;
    }
    if (i < j) {
      return -1;
    }
    return 0;
  }
  
  public String a()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\icu\text\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */