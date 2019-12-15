package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class ConstantsUnusedStructInternal
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(8, 0) };
  
  public ConstantsUnusedStructInternal()
  {
    this(0);
  }
  
  private ConstantsUnusedStructInternal(int paramInt)
  {
    super(8, paramInt);
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (paramObject == null) {
      return false;
    }
    return getClass() == paramObject.getClass();
  }
  
  public int hashCode()
  {
    return getClass().hashCode() + 31;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\ConstantsUnusedStructInternal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */