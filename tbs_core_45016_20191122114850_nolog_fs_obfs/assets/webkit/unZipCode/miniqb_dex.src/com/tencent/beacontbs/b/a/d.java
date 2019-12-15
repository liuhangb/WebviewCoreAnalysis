package com.tencent.beacontbs.b.a;

import com.tencent.beacontbs.d.a;
import com.tencent.beacontbs.d.b;
import com.tencent.beacontbs.d.c;
import java.util.HashMap;
import java.util.Map;

public final class d
  extends c
{
  private static Map<String, String> b = new HashMap();
  private Map<String, String> jdField_a_of_type_JavaUtilMap = null;
  private byte[] jdField_a_of_type_ArrayOfByte = null;
  
  static
  {
    b.put("", "");
    ((byte[])new byte[1])[0] = 0;
  }
  
  public d() {}
  
  public d(Map<String, String> paramMap, byte[] paramArrayOfByte)
  {
    this.jdField_a_of_type_JavaUtilMap = paramMap;
    this.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
  }
  
  public final void a(a parama)
  {
    this.jdField_a_of_type_JavaUtilMap = ((Map)parama.a(b, 0, true));
    this.jdField_a_of_type_ArrayOfByte = ((byte[])parama.a(1, true));
  }
  
  public final void a(b paramb)
  {
    paramb.a(this.jdField_a_of_type_JavaUtilMap, 0);
    paramb.a(this.jdField_a_of_type_ArrayOfByte, 1);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\b\a\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */