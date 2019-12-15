package org.chromium.mojo.system;

public class ResultAnd<A>
{
  private final int jdField_a_of_type_Int;
  private final A jdField_a_of_type_JavaLangObject;
  
  public ResultAnd(int paramInt, A paramA)
  {
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_a_of_type_JavaLangObject = paramA;
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public A a()
  {
    return (A)this.jdField_a_of_type_JavaLangObject;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\ResultAnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */