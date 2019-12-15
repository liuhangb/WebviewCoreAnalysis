package org.chromium.mojo.common.mojom;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class TimeDelta
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
  public long a;
  
  public TimeDelta()
  {
    this(0);
  }
  
  private TimeDelta(int paramInt)
  {
    super(16, paramInt);
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_Long, 8);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    if (getClass() != paramObject.getClass()) {
      return false;
    }
    paramObject = (TimeDelta)paramObject;
    return this.jdField_a_of_type_Long == ((TimeDelta)paramObject).jdField_a_of_type_Long;
  }
  
  public int hashCode()
  {
    return (getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_Long);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\common\mojom\TimeDelta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */