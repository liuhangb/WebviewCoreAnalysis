package com.tencent.beacontbs.d;

import java.util.HashMap;
import java.util.Map;

public final class e
  extends c
{
  private static byte[] jdField_b_of_type_ArrayOfByte;
  private static Map<String, String> jdField_c_of_type_JavaUtilMap;
  private byte jdField_a_of_type_Byte = 0;
  public int a;
  public String a;
  private Map<String, String> jdField_a_of_type_JavaUtilMap;
  public short a;
  public byte[] a;
  private int jdField_b_of_type_Int = 0;
  public String b;
  private Map<String, String> jdField_b_of_type_JavaUtilMap;
  private int jdField_c_of_type_Int = 0;
  
  public e()
  {
    this.jdField_a_of_type_Short = 3;
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_JavaLangString = null;
    this.jdField_b_of_type_JavaLangString = null;
  }
  
  public final void a(a parama)
  {
    try
    {
      this.jdField_a_of_type_Short = parama.a(this.jdField_a_of_type_Short, 1, true);
      this.jdField_a_of_type_Byte = parama.a(this.jdField_a_of_type_Byte, 2, true);
      this.jdField_b_of_type_Int = parama.a(this.jdField_b_of_type_Int, 3, true);
      this.jdField_a_of_type_Int = parama.a(this.jdField_a_of_type_Int, 4, true);
      this.jdField_a_of_type_JavaLangString = parama.a(5, true);
      this.jdField_b_of_type_JavaLangString = parama.a(6, true);
      if (jdField_b_of_type_ArrayOfByte == null) {
        jdField_b_of_type_ArrayOfByte = new byte[] { 0 };
      }
      this.jdField_a_of_type_ArrayOfByte = ((byte[])parama.a(7, true));
      this.jdField_c_of_type_Int = parama.a(this.jdField_c_of_type_Int, 8, true);
      HashMap localHashMap;
      if (jdField_c_of_type_JavaUtilMap == null)
      {
        localHashMap = new HashMap();
        jdField_c_of_type_JavaUtilMap = localHashMap;
        localHashMap.put("", "");
      }
      this.jdField_a_of_type_JavaUtilMap = ((Map)parama.a(jdField_c_of_type_JavaUtilMap, 9, true));
      if (jdField_c_of_type_JavaUtilMap == null)
      {
        localHashMap = new HashMap();
        jdField_c_of_type_JavaUtilMap = localHashMap;
        localHashMap.put("", "");
      }
      this.jdField_b_of_type_JavaUtilMap = ((Map)parama.a(jdField_c_of_type_JavaUtilMap, 10, true));
      return;
    }
    catch (Exception parama)
    {
      parama.printStackTrace();
      throw new RuntimeException(parama);
    }
  }
  
  public final void a(b paramb)
  {
    paramb.a(this.jdField_a_of_type_Short, 1);
    paramb.a(this.jdField_a_of_type_Byte, 2);
    paramb.a(this.jdField_b_of_type_Int, 3);
    paramb.a(this.jdField_a_of_type_Int, 4);
    paramb.a(this.jdField_a_of_type_JavaLangString, 5);
    paramb.a(this.jdField_b_of_type_JavaLangString, 6);
    paramb.a(this.jdField_a_of_type_ArrayOfByte, 7);
    paramb.a(this.jdField_c_of_type_Int, 8);
    paramb.a(this.jdField_a_of_type_JavaUtilMap, 9);
    paramb.a(this.jdField_b_of_type_JavaUtilMap, 10);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\d\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */