package org.chromium.blink.mojom;

import java.util.Arrays;
import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Struct;

public final class CloneableMessage
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(48, 0) };
  public long a;
  public byte[] a;
  public SerializedBlob[] a;
  public long b;
  public long c;
  
  public CloneableMessage()
  {
    this(0);
  }
  
  private CloneableMessage(int paramInt)
  {
    super(48, paramInt);
  }
  
  public static CloneableMessage a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader1 = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      CloneableMessage localCloneableMessage = new CloneableMessage(localDataHeader1.b);
      if (localDataHeader1.b >= 0) {
        localCloneableMessage.jdField_a_of_type_ArrayOfByte = paramDecoder.a(8, 0, -1);
      }
      if (localDataHeader1.b >= 0)
      {
        Decoder localDecoder1 = paramDecoder.a(16, false);
        DataHeader localDataHeader2 = localDecoder1.b(-1);
        localCloneableMessage.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedBlob = new SerializedBlob[localDataHeader2.b];
        int i = 0;
        while (i < localDataHeader2.b)
        {
          Decoder localDecoder2 = localDecoder1.a(i * 8 + 8, false);
          localCloneableMessage.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedBlob[i] = SerializedBlob.a(localDecoder2);
          i += 1;
        }
      }
      if (localDataHeader1.b >= 0) {
        localCloneableMessage.jdField_a_of_type_Long = paramDecoder.a(24);
      }
      if (localDataHeader1.b >= 0) {
        localCloneableMessage.b = paramDecoder.a(32);
      }
      if (localDataHeader1.b >= 0) {
        localCloneableMessage.c = paramDecoder.a(40);
      }
      return localCloneableMessage;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_ArrayOfByte, 8, 0, -1);
    Object localObject = this.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedBlob;
    if (localObject == null)
    {
      paramEncoder.a(16, false);
    }
    else
    {
      localObject = paramEncoder.a(localObject.length, 16, -1);
      int i = 0;
      for (;;)
      {
        SerializedBlob[] arrayOfSerializedBlob = this.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedBlob;
        if (i >= arrayOfSerializedBlob.length) {
          break;
        }
        ((Encoder)localObject).a(arrayOfSerializedBlob[i], i * 8 + 8, false);
        i += 1;
      }
    }
    paramEncoder.a(this.jdField_a_of_type_Long, 24);
    paramEncoder.a(this.b, 32);
    paramEncoder.a(this.c, 40);
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
    paramObject = (CloneableMessage)paramObject;
    if (!Arrays.equals(this.jdField_a_of_type_ArrayOfByte, ((CloneableMessage)paramObject).jdField_a_of_type_ArrayOfByte)) {
      return false;
    }
    if (!Arrays.deepEquals(this.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedBlob, ((CloneableMessage)paramObject).jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedBlob)) {
      return false;
    }
    if (this.jdField_a_of_type_Long != ((CloneableMessage)paramObject).jdField_a_of_type_Long) {
      return false;
    }
    if (this.b != ((CloneableMessage)paramObject).b) {
      return false;
    }
    return this.c == ((CloneableMessage)paramObject).c;
  }
  
  public int hashCode()
  {
    return (((((getClass().hashCode() + 31) * 31 + Arrays.hashCode(this.jdField_a_of_type_ArrayOfByte)) * 31 + Arrays.deepHashCode(this.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedBlob)) * 31 + BindingsHelper.a(this.jdField_a_of_type_Long)) * 31 + BindingsHelper.a(this.b)) * 31 + BindingsHelper.a(this.c);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\blink\mojom\CloneableMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */