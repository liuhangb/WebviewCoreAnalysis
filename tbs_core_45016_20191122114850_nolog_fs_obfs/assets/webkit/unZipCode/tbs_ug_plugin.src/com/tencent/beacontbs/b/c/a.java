package com.tencent.beacontbs.b.c;

import com.tencent.beacontbs.d.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class a
  extends c
{
  private static ArrayList<b> jdField_b_of_type_JavaUtilArrayList;
  private static Map<String, String> jdField_b_of_type_JavaUtilMap;
  public int a;
  public String a;
  public ArrayList<b> a;
  public Map<String, String> a;
  
  public a()
  {
    this.jdField_a_of_type_JavaUtilArrayList = null;
    this.jdField_a_of_type_Int = 0;
    this.jdField_a_of_type_JavaLangString = "";
    this.jdField_a_of_type_JavaUtilMap = null;
  }
  
  public final void a(com.tencent.beacontbs.d.a parama)
  {
    if (jdField_b_of_type_JavaUtilArrayList == null)
    {
      jdField_b_of_type_JavaUtilArrayList = new ArrayList();
      b localb = new b();
      jdField_b_of_type_JavaUtilArrayList.add(localb);
    }
    this.jdField_a_of_type_JavaUtilArrayList = ((ArrayList)parama.a(jdField_b_of_type_JavaUtilArrayList, 0, true));
    this.jdField_a_of_type_Int = parama.a(this.jdField_a_of_type_Int, 1, true);
    this.jdField_a_of_type_JavaLangString = parama.a(2, true);
    if (jdField_b_of_type_JavaUtilMap == null)
    {
      jdField_b_of_type_JavaUtilMap = new HashMap();
      jdField_b_of_type_JavaUtilMap.put("", "");
    }
    this.jdField_a_of_type_JavaUtilMap = ((Map)parama.a(jdField_b_of_type_JavaUtilMap, 3, false));
  }
  
  public final void a(com.tencent.beacontbs.d.b paramb)
  {
    paramb.a(this.jdField_a_of_type_JavaUtilArrayList, 0);
    paramb.a(this.jdField_a_of_type_Int, 1);
    paramb.a(this.jdField_a_of_type_JavaLangString, 2);
    Map localMap = this.jdField_a_of_type_JavaUtilMap;
    if (localMap != null) {
      paramb.a(localMap, 3);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\b\c\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */