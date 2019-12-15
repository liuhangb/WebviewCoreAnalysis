package com.tencent.beacontbs.b.a;

import com.tencent.beacontbs.d.a;
import com.tencent.beacontbs.d.b;

public final class c
  extends com.tencent.beacontbs.d.c
{
  private static byte[] jdField_b_of_type_ArrayOfByte;
  public byte a;
  public int a;
  public long a;
  public String a;
  public byte[] a;
  private byte jdField_b_of_type_Byte = 0;
  private String jdField_b_of_type_JavaLangString = "";
  private byte jdField_c_of_type_Byte = 0;
  private String jdField_c_of_type_JavaLangString = "";
  
  public c()
  {
    this.jdField_a_of_type_Byte = 0;
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_ArrayOfByte = null;
    this.jdField_a_of_type_JavaLangString = "";
    this.jdField_a_of_type_Long = 0L;
  }
  
  public final void a(a parama)
  {
    this.jdField_a_of_type_Byte = parama.a(this.jdField_a_of_type_Byte, 0, true);
    this.jdField_a_of_type_Int = parama.a(this.jdField_a_of_type_Int, 1, true);
    if (jdField_b_of_type_ArrayOfByte == null)
    {
      byte[] arrayOfByte = (byte[])new byte[1];
      jdField_b_of_type_ArrayOfByte = arrayOfByte;
      ((byte[])arrayOfByte)[0] = 0;
    }
    this.jdField_a_of_type_ArrayOfByte = ((byte[])parama.a(2, true));
    this.jdField_a_of_type_JavaLangString = parama.a(3, true);
    this.jdField_b_of_type_Byte = parama.a(this.jdField_b_of_type_Byte, 4, true);
    this.jdField_c_of_type_Byte = parama.a(this.jdField_c_of_type_Byte, 5, true);
    this.jdField_a_of_type_Long = parama.a(this.jdField_a_of_type_Long, 6, true);
    this.jdField_b_of_type_JavaLangString = parama.a(7, false);
    this.jdField_c_of_type_JavaLangString = parama.a(8, false);
  }
  
  public final void a(b paramb)
  {
    paramb.a(this.jdField_a_of_type_Byte, 0);
    paramb.a(this.jdField_a_of_type_Int, 1);
    paramb.a(this.jdField_a_of_type_ArrayOfByte, 2);
    paramb.a(this.jdField_a_of_type_JavaLangString, 3);
    paramb.a(this.jdField_b_of_type_Byte, 4);
    paramb.a(this.jdField_c_of_type_Byte, 5);
    paramb.a(this.jdField_a_of_type_Long, 6);
    String str = this.jdField_b_of_type_JavaLangString;
    if (str != null) {
      paramb.a(str, 7);
    }
    str = this.jdField_c_of_type_JavaLangString;
    if (str != null) {
      paramb.a(str, 8);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\b\a\c.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */