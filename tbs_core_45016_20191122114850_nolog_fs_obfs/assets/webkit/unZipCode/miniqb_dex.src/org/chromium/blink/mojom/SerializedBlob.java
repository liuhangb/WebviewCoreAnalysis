package org.chromium.blink.mojom;

import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class SerializedBlob
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(40, 0) };
  public long a;
  public String a;
  public Blob a;
  public String b;
  
  public SerializedBlob()
  {
    this(0);
  }
  
  private SerializedBlob(int paramInt)
  {
    super(40, paramInt);
  }
  
  public static SerializedBlob a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      SerializedBlob localSerializedBlob = new SerializedBlob(localDataHeader.b);
      if (localDataHeader.b >= 0) {
        localSerializedBlob.jdField_a_of_type_JavaLangString = paramDecoder.a(8, false);
      }
      if (localDataHeader.b >= 0) {
        localSerializedBlob.b = paramDecoder.a(16, false);
      }
      if (localDataHeader.b >= 0) {
        localSerializedBlob.jdField_a_of_type_Long = paramDecoder.a(24);
      }
      if (localDataHeader.b >= 0) {
        localSerializedBlob.jdField_a_of_type_OrgChromiumBlinkMojomBlob = ((Blob)paramDecoder.a(32, false, Blob.a));
      }
      return localSerializedBlob;
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
    paramEncoder.a(this.jdField_a_of_type_Long, 24);
    paramEncoder.a(this.jdField_a_of_type_OrgChromiumBlinkMojomBlob, 32, false, Blob.a);
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
    paramObject = (SerializedBlob)paramObject;
    if (!BindingsHelper.a(this.jdField_a_of_type_JavaLangString, ((SerializedBlob)paramObject).jdField_a_of_type_JavaLangString)) {
      return false;
    }
    if (!BindingsHelper.a(this.b, ((SerializedBlob)paramObject).b)) {
      return false;
    }
    if (this.jdField_a_of_type_Long != ((SerializedBlob)paramObject).jdField_a_of_type_Long) {
      return false;
    }
    return BindingsHelper.a(this.jdField_a_of_type_OrgChromiumBlinkMojomBlob, ((SerializedBlob)paramObject).jdField_a_of_type_OrgChromiumBlinkMojomBlob);
  }
  
  public int hashCode()
  {
    return ((((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_JavaLangString)) * 31 + BindingsHelper.a(this.b)) * 31 + BindingsHelper.a(this.jdField_a_of_type_Long)) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumBlinkMojomBlob);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\blink\mojom\SerializedBlob.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */