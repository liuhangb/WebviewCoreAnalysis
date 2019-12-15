package org.chromium.gfx.mojom;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class BufferUsageAndFormat
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
  public int a;
  public int b;
  
  public BufferUsageAndFormat()
  {
    this(0);
  }
  
  private BufferUsageAndFormat(int paramInt)
  {
    super(16, paramInt);
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_Int, 8);
    paramEncoder.a(this.b, 12);
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
    paramObject = (BufferUsageAndFormat)paramObject;
    if (this.jdField_a_of_type_Int != ((BufferUsageAndFormat)paramObject).jdField_a_of_type_Int) {
      return false;
    }
    return this.b == ((BufferUsageAndFormat)paramObject).b;
  }
  
  public int hashCode()
  {
    return ((getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int)) * 31 + BindingsHelper.b(this.b);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\gfx\mojom\BufferUsageAndFormat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */