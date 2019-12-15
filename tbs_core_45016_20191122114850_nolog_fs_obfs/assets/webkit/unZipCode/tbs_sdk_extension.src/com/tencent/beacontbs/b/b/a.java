package com.tencent.beacontbs.b.b;

import com.tencent.beacontbs.d.b;
import com.tencent.beacontbs.d.c;

public final class a
  extends c
  implements Cloneable
{
  private int a;
  public long a;
  public String a;
  public boolean a;
  public long b;
  public String b;
  public long c;
  public String c;
  public String d = "";
  
  public a()
  {
    this.jdField_a_of_type_JavaLangString = "";
    this.jdField_b_of_type_JavaLangString = "";
    this.jdField_c_of_type_JavaLangString = "";
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_Long = 0L;
    this.jdField_b_of_type_Long = 0L;
    this.jdField_c_of_type_Long = 0L;
    this.jdField_a_of_type_Int = 0;
  }
  
  public final void a(com.tencent.beacontbs.d.a parama)
  {
    this.jdField_a_of_type_JavaLangString = parama.a(0, true);
    this.jdField_b_of_type_JavaLangString = parama.a(1, true);
    this.jdField_c_of_type_JavaLangString = parama.a(2, true);
    this.jdField_a_of_type_Boolean = parama.a(3);
    this.jdField_a_of_type_Long = parama.a(this.jdField_a_of_type_Long, 4, true);
    this.jdField_b_of_type_Long = parama.a(this.jdField_b_of_type_Long, 5, true);
    this.d = parama.a(6, true);
    this.jdField_c_of_type_Long = parama.a(this.jdField_c_of_type_Long, 7, true);
    this.jdField_a_of_type_Int = parama.a(this.jdField_a_of_type_Int, 8, false);
  }
  
  public final void a(b paramb)
  {
    paramb.a(this.jdField_a_of_type_JavaLangString, 0);
    paramb.a(this.jdField_b_of_type_JavaLangString, 1);
    paramb.a(this.jdField_c_of_type_JavaLangString, 2);
    paramb.a(this.jdField_a_of_type_Boolean, 3);
    paramb.a(this.jdField_a_of_type_Long, 4);
    paramb.a(this.jdField_b_of_type_Long, 5);
    paramb.a(this.d, 6);
    paramb.a(this.jdField_c_of_type_Long, 7);
    paramb.a(this.jdField_a_of_type_Int, 8);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\b\b\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */