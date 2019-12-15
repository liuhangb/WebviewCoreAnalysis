package com.tencent.beacontbs.b.a;

import com.tencent.beacontbs.d.a;
import com.tencent.beacontbs.d.c;

public final class b
  extends c
{
  private static byte[] b;
  public byte a;
  public int a;
  public String a;
  public byte[] a;
  public byte b;
  public String b;
  public byte c;
  public String c;
  public String d = "";
  public String e = "";
  public String f = "";
  public String g = "";
  
  public b()
  {
    this.jdField_a_of_type_Byte = 0;
    this.jdField_a_of_type_JavaLangString = "";
    this.jdField_b_of_type_JavaLangString = "";
    this.jdField_c_of_type_JavaLangString = "";
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_ArrayOfByte = null;
    this.jdField_b_of_type_Byte = 0;
    this.jdField_c_of_type_Byte = 0;
  }
  
  public final void a(a parama)
  {
    this.jdField_a_of_type_Byte = parama.a(this.jdField_a_of_type_Byte, 0, true);
    this.jdField_a_of_type_JavaLangString = parama.a(1, true);
    this.jdField_b_of_type_JavaLangString = parama.a(2, true);
    this.jdField_c_of_type_JavaLangString = parama.a(3, true);
    this.d = parama.a(4, true);
    this.jdField_a_of_type_Int = parama.a(this.jdField_a_of_type_Int, 5, true);
    if (jdField_b_of_type_ArrayOfByte == null)
    {
      byte[] arrayOfByte = (byte[])new byte[1];
      jdField_b_of_type_ArrayOfByte = arrayOfByte;
      ((byte[])arrayOfByte)[0] = 0;
    }
    this.jdField_a_of_type_ArrayOfByte = ((byte[])parama.a(6, true));
    this.jdField_b_of_type_Byte = parama.a(this.jdField_b_of_type_Byte, 7, true);
    this.jdField_c_of_type_Byte = parama.a(this.jdField_c_of_type_Byte, 8, true);
    this.e = parama.a(9, false);
    this.f = parama.a(10, false);
    this.g = parama.a(11, false);
  }
  
  public final void a(com.tencent.beacontbs.d.b paramb)
  {
    paramb.a(this.jdField_a_of_type_Byte, 0);
    paramb.a(this.jdField_a_of_type_JavaLangString, 1);
    paramb.a(this.jdField_b_of_type_JavaLangString, 2);
    paramb.a(this.jdField_c_of_type_JavaLangString, 3);
    paramb.a(this.d, 4);
    paramb.a(this.jdField_a_of_type_Int, 5);
    paramb.a(this.jdField_a_of_type_ArrayOfByte, 6);
    paramb.a(this.jdField_b_of_type_Byte, 7);
    paramb.a(this.jdField_c_of_type_Byte, 8);
    String str = this.e;
    if (str != null) {
      paramb.a(str, 9);
    }
    str = this.f;
    if (str != null) {
      paramb.a(str, 10);
    }
    str = this.g;
    if (str != null) {
      paramb.a(str, 11);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\b\a\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */