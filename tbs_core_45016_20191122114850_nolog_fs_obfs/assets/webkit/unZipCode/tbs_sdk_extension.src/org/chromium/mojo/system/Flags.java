package org.chromium.mojo.system;

public abstract class Flags<F extends Flags<F>>
{
  private int jdField_a_of_type_Int;
  private boolean jdField_a_of_type_Boolean = false;
  
  protected Flags(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
  }
  
  public int a()
  {
    return this.jdField_a_of_type_Int;
  }
  
  protected F a()
  {
    this.jdField_a_of_type_Boolean = true;
    return this;
  }
  
  protected F a(int paramInt, boolean paramBoolean)
  {
    if (!this.jdField_a_of_type_Boolean)
    {
      if (paramBoolean)
      {
        this.jdField_a_of_type_Int = (paramInt | this.jdField_a_of_type_Int);
        return this;
      }
      this.jdField_a_of_type_Int = ((paramInt ^ 0xFFFFFFFF) & this.jdField_a_of_type_Int);
      return this;
    }
    throw new UnsupportedOperationException("Flags is immutable.");
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (getClass() != paramObject.getClass()) {
      return false;
    }
    paramObject = (Flags)paramObject;
    return this.jdField_a_of_type_Int == ((Flags)paramObject).jdField_a_of_type_Int;
  }
  
  public int hashCode()
  {
    return this.jdField_a_of_type_Int;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\Flags.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */