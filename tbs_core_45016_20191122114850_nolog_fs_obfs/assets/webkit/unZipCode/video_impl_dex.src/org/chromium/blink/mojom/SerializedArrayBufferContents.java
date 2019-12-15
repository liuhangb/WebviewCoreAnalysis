package org.chromium.blink.mojom;

import java.util.Arrays;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class SerializedArrayBufferContents
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(16, 0) };
  public byte[] a;
  
  public SerializedArrayBufferContents()
  {
    this(0);
  }
  
  private SerializedArrayBufferContents(int paramInt)
  {
    super(16, paramInt);
  }
  
  public static SerializedArrayBufferContents a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      SerializedArrayBufferContents localSerializedArrayBufferContents = new SerializedArrayBufferContents(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localSerializedArrayBufferContents.jdField_a_of_type_ArrayOfByte = paramDecoder.a(8, 0, -1);
      }
      return localSerializedArrayBufferContents;
    }
    finally
    {
      paramDecoder.b();
    }
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
    paramObject = (SerializedArrayBufferContents)paramObject;
    return Arrays.equals(this.jdField_a_of_type_ArrayOfByte, ((SerializedArrayBufferContents)paramObject).jdField_a_of_type_ArrayOfByte);
  }
  
  public int hashCode()
  {
    return (getClass().hashCode() + 31) * 31 + Arrays.hashCode(this.jdField_a_of_type_ArrayOfByte);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\blink\mojom\SerializedArrayBufferContents.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */