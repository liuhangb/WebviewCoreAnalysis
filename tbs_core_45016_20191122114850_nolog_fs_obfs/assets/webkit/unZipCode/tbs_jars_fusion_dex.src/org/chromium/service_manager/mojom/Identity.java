package org.chromium.service_manager.mojom;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class Identity
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(32, 0) };
  public String a;
  public String b;
  public String c;
  
  public Identity()
  {
    this(0);
  }
  
  private Identity(int paramInt)
  {
    super(32, paramInt);
  }
  
  public static Identity a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      Identity localIdentity = new Identity(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localIdentity.jdField_a_of_type_JavaLangString = paramDecoder.a(8, false);
      }
      if (localDataHeader.b >= 0) {
        localIdentity.b = paramDecoder.a(16, false);
      }
      if (localDataHeader.b >= 0) {
        localIdentity.c = paramDecoder.a(24, false);
      }
      return localIdentity;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_JavaLangString, 8, false);
    paramEncoder.a(this.b, 16, false);
    paramEncoder.a(this.c, 24, false);
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
    paramObject = (Identity)paramObject;
    if (!BindingsHelper.a(this.jdField_a_of_type_JavaLangString, ((Identity)paramObject).jdField_a_of_type_JavaLangString)) {
      return false;
    }
    if (!BindingsHelper.a(this.b, ((Identity)paramObject).b)) {
      return false;
    }
    return BindingsHelper.a(this.c, ((Identity)paramObject).c);
  }
  
  public int hashCode()
  {
    return (((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaLangString)) * 31 + BindingsHelper.a(this.b)) * 31 + BindingsHelper.a(this.c);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\service_manager\mojom\Identity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */