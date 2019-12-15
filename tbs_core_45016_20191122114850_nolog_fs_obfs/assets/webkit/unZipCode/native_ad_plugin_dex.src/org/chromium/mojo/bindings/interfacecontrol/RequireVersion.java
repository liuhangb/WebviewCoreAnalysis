package org.chromium.mojo.bindings.interfacecontrol;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class RequireVersion
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
  public int a;
  
  public RequireVersion()
  {
    this(0);
  }
  
  private RequireVersion(int paramInt)
  {
    super(16, paramInt);
  }
  
  public static RequireVersion a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      RequireVersion localRequireVersion = new RequireVersion(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localRequireVersion.jdField_a_of_type_Int = paramDecoder.a(8);
      }
      return localRequireVersion;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader).a(this.jdField_a_of_type_Int, 8);
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
    paramObject = (RequireVersion)paramObject;
    return this.jdField_a_of_type_Int == ((RequireVersion)paramObject).jdField_a_of_type_Int;
  }
  
  public int hashCode()
  {
    return (getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\interfacecontrol\RequireVersion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */