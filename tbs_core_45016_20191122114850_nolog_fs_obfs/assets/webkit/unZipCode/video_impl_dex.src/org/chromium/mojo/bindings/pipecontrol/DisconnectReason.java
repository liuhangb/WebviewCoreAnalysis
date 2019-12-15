package org.chromium.mojo.bindings.pipecontrol;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class DisconnectReason
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
  public int a;
  public String a;
  
  public DisconnectReason()
  {
    this(0);
  }
  
  private DisconnectReason(int paramInt)
  {
    super(24, paramInt);
  }
  
  public static DisconnectReason a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      DisconnectReason localDisconnectReason = new DisconnectReason(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localDisconnectReason.jdField_a_of_type_Int = paramDecoder.a(8);
      }
      if (localDataHeader.b >= 0) {
        localDisconnectReason.jdField_a_of_type_JavaLangString = paramDecoder.a(16, false);
      }
      return localDisconnectReason;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_Int, 8);
    paramEncoder.a(this.jdField_a_of_type_JavaLangString, 16, false);
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
    paramObject = (DisconnectReason)paramObject;
    if (this.jdField_a_of_type_Int != ((DisconnectReason)paramObject).jdField_a_of_type_Int) {
      return false;
    }
    return BindingsHelper.a(this.jdField_a_of_type_JavaLangString, ((DisconnectReason)paramObject).jdField_a_of_type_JavaLangString);
  }
  
  public int hashCode()
  {
    return ((getClass().hashCode() + 31) * 31 + BindingsHelper.b(this.jdField_a_of_type_Int)) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaLangString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\pipecontrol\DisconnectReason.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */