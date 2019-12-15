package com.tencent.beacontbs.b.a;

import com.tencent.beacontbs.d.a;
import com.tencent.beacontbs.d.b;
import com.tencent.beacontbs.d.c;
import java.util.HashMap;
import java.util.Map;

public final class e
  extends c
{
  private static Map<String, String> b = new HashMap();
  public int a;
  public String a;
  public Map<String, String> a;
  public byte[] a;
  
  static
  {
    b.put("", "");
    ((byte[])new byte[1])[0] = 0;
  }
  
  public e()
  {
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_JavaUtilMap = null;
    this.jdField_a_of_type_ArrayOfByte = null;
    this.jdField_a_of_type_JavaLangString = "";
  }
  
  public final void a(a parama)
  {
    this.jdField_a_of_type_Int = parama.a(this.jdField_a_of_type_Int, 0, true);
    this.jdField_a_of_type_JavaUtilMap = ((Map)parama.a(b, 1, true));
    this.jdField_a_of_type_ArrayOfByte = ((byte[])parama.a(2, true));
    this.jdField_a_of_type_JavaLangString = parama.a(3, false);
  }
  
  public final void a(b paramb)
  {
    paramb.a(this.jdField_a_of_type_Int, 0);
    paramb.a(this.jdField_a_of_type_JavaUtilMap, 1);
    paramb.a(this.jdField_a_of_type_ArrayOfByte, 2);
    String str = this.jdField_a_of_type_JavaLangString;
    if (str != null) {
      paramb.a(str, 3);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\b\a\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */