package com.tencent.beacontbs.event;

import java.util.HashMap;
import java.util.Map;

public final class e
{
  public long a;
  public String a;
  public Map<String, String> a;
  public boolean a;
  public long b;
  boolean b;
  
  public e(String paramString, boolean paramBoolean1, long paramLong1, long paramLong2, Map<String, String> paramMap, boolean paramBoolean2)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    this.jdField_a_of_type_Boolean = paramBoolean1;
    this.jdField_a_of_type_Long = paramLong1;
    this.jdField_b_of_type_Long = paramLong2;
    if (paramMap != null)
    {
      this.jdField_a_of_type_JavaUtilMap = new HashMap();
      this.jdField_a_of_type_JavaUtilMap.putAll(paramMap);
    }
    this.jdField_b_of_type_Boolean = paramBoolean2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\event\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */