package com.tencent.turingfd.sdk.tbs;

public class x
  implements k
{
  public static final byte[] a;
  public final int a;
  public final long a;
  public final String a;
  public final String b;
  public final byte[] b;
  public final String c;
  public final String d;
  public final String e;
  
  static
  {
    jdField_a_of_type_ArrayOfByte = new byte[0];
  }
  
  public x(int paramInt, byte[] paramArrayOfByte)
  {
    this.jdField_a_of_type_JavaLangString = "";
    this.jdField_a_of_type_Long = 0L;
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_b_of_type_ArrayOfByte = paramArrayOfByte;
    this.jdField_b_of_type_JavaLangString = "";
    this.c = "";
    this.d = "";
    this.e = "";
  }
  
  public x(b paramb)
  {
    this.jdField_a_of_type_JavaLangString = paramb.jdField_a_of_type_JavaLangString;
    this.jdField_a_of_type_Long = paramb.jdField_a_of_type_Long;
    this.jdField_a_of_type_Int = paramb.jdField_a_of_type_Int;
    this.jdField_b_of_type_ArrayOfByte = paramb.jdField_a_of_type_ArrayOfByte;
    this.jdField_b_of_type_JavaLangString = paramb.jdField_b_of_type_JavaLangString;
    this.c = paramb.c;
    this.d = paramb.d;
    this.e = paramb.e;
  }
  
  public static b a(int paramInt)
  {
    return new b(paramInt, null);
  }
  
  public static x a(int paramInt)
  {
    return new x(paramInt, jdField_a_of_type_ArrayOfByte);
  }
  
  public static final class b
  {
    public int a;
    public long a;
    public String a;
    public byte[] a;
    public String b;
    public String c;
    public String d;
    public String e;
    
    public b a(long paramLong)
    {
      this.jdField_a_of_type_Long = paramLong;
      return this;
    }
    
    public b a(String paramString)
    {
      this.jdField_a_of_type_JavaLangString = paramString;
      return this;
    }
    
    public x a()
    {
      return new x(this);
    }
    
    public b b(String paramString)
    {
      this.b = paramString;
      return this;
    }
    
    public b c(String paramString)
    {
      this.c = paramString;
      return this;
    }
    
    public b d(String paramString)
    {
      this.d = paramString;
      return this;
    }
    
    public b e(String paramString)
    {
      this.e = paramString;
      return this;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\turingfd\sdk\tbs\x.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */