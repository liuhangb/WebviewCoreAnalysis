package org.chromium.mojo.common.mojom;

import java.util.Arrays;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class ReadOnlyBuffer
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
  public byte[] a;
  
  public ReadOnlyBuffer()
  {
    this(0);
  }
  
  private ReadOnlyBuffer(int paramInt)
  {
    super(16, paramInt);
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_ArrayOfByte, 8, 0, -1);
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
    paramObject = (ReadOnlyBuffer)paramObject;
    return Arrays.equals(this.jdField_a_of_type_ArrayOfByte, ((ReadOnlyBuffer)paramObject).jdField_a_of_type_ArrayOfByte);
  }
  
  public int hashCode()
  {
    return (getClass().hashCode() + 31) * 31 + Arrays.hashCode(this.jdField_a_of_type_ArrayOfByte);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\common\mojom\ReadOnlyBuffer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */