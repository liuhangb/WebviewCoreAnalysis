package com.tencent.beacontbs.b.c;

import com.tencent.beacontbs.d.a;
import com.tencent.beacontbs.d.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class b
  extends c
{
  private static Map<String, String> b;
  private static ArrayList<String> c;
  private static ArrayList<String> d;
  public byte a;
  public String a;
  public ArrayList<String> a;
  public Map<String, String> a;
  public byte b;
  public ArrayList<String> b;
  
  public b()
  {
    this.jdField_a_of_type_Byte = 0;
    this.jdField_b_of_type_Byte = 0;
    this.jdField_a_of_type_JavaLangString = "";
    this.jdField_a_of_type_JavaUtilMap = null;
    this.jdField_a_of_type_JavaUtilArrayList = null;
    this.jdField_b_of_type_JavaUtilArrayList = null;
  }
  
  public final void a(a parama)
  {
    this.jdField_a_of_type_Byte = parama.a(this.jdField_a_of_type_Byte, 0, true);
    this.jdField_b_of_type_Byte = parama.a(this.jdField_b_of_type_Byte, 1, true);
    this.jdField_a_of_type_JavaLangString = parama.a(2, true);
    if (jdField_b_of_type_JavaUtilMap == null)
    {
      jdField_b_of_type_JavaUtilMap = new HashMap();
      jdField_b_of_type_JavaUtilMap.put("", "");
    }
    this.jdField_a_of_type_JavaUtilMap = ((Map)parama.a(jdField_b_of_type_JavaUtilMap, 3, true));
    if (c == null)
    {
      c = new ArrayList();
      c.add("");
    }
    this.jdField_a_of_type_JavaUtilArrayList = ((ArrayList)parama.a(c, 4, false));
    if (d == null)
    {
      d = new ArrayList();
      d.add("");
    }
    this.jdField_b_of_type_JavaUtilArrayList = ((ArrayList)parama.a(d, 6, false));
  }
  
  public final void a(com.tencent.beacontbs.d.b paramb)
  {
    paramb.a(this.jdField_a_of_type_Byte, 0);
    paramb.a(this.jdField_b_of_type_Byte, 1);
    paramb.a(this.jdField_a_of_type_JavaLangString, 2);
    paramb.a(this.jdField_a_of_type_JavaUtilMap, 3);
    ArrayList localArrayList = this.jdField_a_of_type_JavaUtilArrayList;
    if (localArrayList != null) {
      paramb.a(localArrayList, 4);
    }
    localArrayList = this.jdField_b_of_type_JavaUtilArrayList;
    if (localArrayList != null) {
      paramb.a(localArrayList, 6);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\b\c\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */