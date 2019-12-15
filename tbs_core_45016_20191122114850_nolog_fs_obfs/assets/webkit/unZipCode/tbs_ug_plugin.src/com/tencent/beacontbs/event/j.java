package com.tencent.beacontbs.event;

import com.tencent.beacontbs.d.a;
import com.tencent.beacontbs.d.b;
import com.tencent.beacontbs.d.c;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class j
  extends c
  implements Serializable
{
  private static Map<String, String> jdField_b_of_type_JavaUtilMap = new HashMap();
  private long jdField_a_of_type_Long = -1L;
  private String jdField_a_of_type_JavaLangString;
  private Map<String, String> jdField_a_of_type_JavaUtilMap;
  private boolean jdField_a_of_type_Boolean;
  private long jdField_b_of_type_Long;
  private String jdField_b_of_type_JavaLangString;
  
  static
  {
    jdField_b_of_type_JavaUtilMap.put("", "");
  }
  
  public final long a()
  {
    try
    {
      long l = this.jdField_a_of_type_Long;
      return l;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String a()
  {
    try
    {
      String str = this.jdField_a_of_type_JavaLangString;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final Map<String, String> a()
  {
    try
    {
      Map localMap = this.jdField_a_of_type_JavaUtilMap;
      return localMap;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void a(long paramLong)
  {
    try
    {
      this.jdField_a_of_type_Long = paramLong;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void a(a parama)
  {
    this.jdField_a_of_type_Long = parama.a(this.jdField_a_of_type_Long, 0, true);
    this.jdField_a_of_type_JavaLangString = parama.a(1, true);
    this.jdField_b_of_type_Long = parama.a(this.jdField_b_of_type_Long, 2, true);
    this.jdField_b_of_type_JavaLangString = parama.a(3, true);
    this.jdField_a_of_type_JavaUtilMap = ((Map)parama.a(jdField_b_of_type_JavaUtilMap, 4, true));
    this.jdField_a_of_type_Boolean = parama.a(5);
  }
  
  public final void a(b paramb)
  {
    paramb.a(this.jdField_a_of_type_Long, 0);
    paramb.a(this.jdField_a_of_type_JavaLangString, 1);
    paramb.a(this.jdField_b_of_type_Long, 2);
    paramb.a(this.jdField_b_of_type_JavaLangString, 3);
    paramb.a(this.jdField_a_of_type_JavaUtilMap, 4);
    paramb.a(this.jdField_a_of_type_Boolean, 5);
  }
  
  public final void a(String paramString)
  {
    try
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
  
  public final void a(Map<String, String> paramMap)
  {
    try
    {
      this.jdField_a_of_type_JavaUtilMap = paramMap;
      return;
    }
    finally
    {
      paramMap = finally;
      throw paramMap;
    }
  }
  
  public final void a(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
  
  public final boolean a()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public final long b()
  {
    try
    {
      long l = this.jdField_b_of_type_Long;
      return l;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final String b()
  {
    try
    {
      String str = this.jdField_b_of_type_JavaLangString;
      return str;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void b(long paramLong)
  {
    try
    {
      this.jdField_b_of_type_Long = paramLong;
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public final void b(String paramString)
  {
    try
    {
      this.jdField_b_of_type_JavaLangString = paramString;
      return;
    }
    finally
    {
      paramString = finally;
      throw paramString;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\event\j.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */