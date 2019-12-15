package org.chromium.mojo.bindings.interfacecontrol;

import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class FlushForTesting
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(8, 0) };
  
  public FlushForTesting()
  {
    this(0);
  }
  
  private FlushForTesting(int paramInt)
  {
    super(8, paramInt);
  }
  
  public static FlushForTesting a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      FlushForTesting localFlushForTesting = new FlushForTesting(paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader).b);
      return localFlushForTesting;
    }
    finally
    {
      paramDecoder.b();
    }
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\interfacecontrol\FlushForTesting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */