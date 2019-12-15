package org.chromium.mojo.common.mojom;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class UnguessableToken
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(24, 0) };
  public long a;
  public long b;
  
  public UnguessableToken()
  {
    this(0);
  }
  
  private UnguessableToken(int paramInt)
  {
    super(24, paramInt);
  }
  
  public static UnguessableToken a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      UnguessableToken localUnguessableToken = new UnguessableToken(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localUnguessableToken.jdField_a_of_type_Long = paramDecoder.a(8);
      }
      if (localDataHeader.b >= 0) {
        localUnguessableToken.b = paramDecoder.a(16);
      }
      return localUnguessableToken;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_Long, 8);
    paramEncoder.a(this.b, 16);
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
    paramObject = (UnguessableToken)paramObject;
    if (this.jdField_a_of_type_Long != ((UnguessableToken)paramObject).jdField_a_of_type_Long) {
      return false;
    }
    return this.b == ((UnguessableToken)paramObject).b;
  }
  
  public int hashCode()
  {
    return ((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_Long)) * 31 + BindingsHelper.a(this.b);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\common\mojom\UnguessableToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */