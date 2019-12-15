package org.chromium.blink.mojom;

import java.util.Arrays;
import org.chromium.mojo.bindings.BindingsHelper;
import org.chromium.mojo.bindings.DataHeader;
import org.chromium.mojo.bindings.Decoder;
import org.chromium.mojo.bindings.Encoder;
import org.chromium.mojo.bindings.Message;
import org.chromium.mojo.bindings.Struct;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.skia.mojom.Bitmap;

public final class TransferableMessage
  extends Struct
{
  private static final DataHeader jdField_a_of_type_OrgChromiumMojoBindingsDataHeader = jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader[0];
  private static final DataHeader[] jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader = { new DataHeader(48, 0) };
  public CloneableMessage a;
  public boolean a;
  public SerializedArrayBufferContents[] a;
  public MessagePipeHandle[] a;
  public Bitmap[] a;
  
  public TransferableMessage()
  {
    this(0);
  }
  
  private TransferableMessage(int paramInt)
  {
    super(48, paramInt);
  }
  
  public static TransferableMessage a(Decoder paramDecoder)
  {
    if (paramDecoder == null) {
      return null;
    }
    paramDecoder.a();
    try
    {
      DataHeader localDataHeader1 = paramDecoder.a(jdField_a_of_type_ArrayOfOrgChromiumMojoBindingsDataHeader);
      TransferableMessage localTransferableMessage = new TransferableMessage(localDataHeader1.b);
      if (localDataHeader1.b >= 0) {
        localTransferableMessage.jdField_a_of_type_OrgChromiumBlinkMojomCloneableMessage = CloneableMessage.a(paramDecoder.a(8, false));
      }
      if (localDataHeader1.b >= 0) {
        localTransferableMessage.jdField_a_of_type_ArrayOfOrgChromiumMojoSystemMessagePipeHandle = paramDecoder.a(16, 0, -1);
      }
      Decoder localDecoder1;
      DataHeader localDataHeader2;
      int i;
      Decoder localDecoder2;
      if (localDataHeader1.b >= 0)
      {
        localDecoder1 = paramDecoder.a(24, false);
        localDataHeader2 = localDecoder1.b(-1);
        localTransferableMessage.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedArrayBufferContents = new SerializedArrayBufferContents[localDataHeader2.b];
        i = 0;
        while (i < localDataHeader2.b)
        {
          localDecoder2 = localDecoder1.a(i * 8 + 8, false);
          localTransferableMessage.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedArrayBufferContents[i] = SerializedArrayBufferContents.a(localDecoder2);
          i += 1;
        }
      }
      if (localDataHeader1.b >= 0)
      {
        localDecoder1 = paramDecoder.a(32, false);
        localDataHeader2 = localDecoder1.b(-1);
        localTransferableMessage.jdField_a_of_type_ArrayOfOrgChromiumSkiaMojomBitmap = new Bitmap[localDataHeader2.b];
        i = 0;
        while (i < localDataHeader2.b)
        {
          localDecoder2 = localDecoder1.a(i * 8 + 8, false);
          localTransferableMessage.jdField_a_of_type_ArrayOfOrgChromiumSkiaMojomBitmap[i] = Bitmap.a(localDecoder2);
          i += 1;
        }
      }
      if (localDataHeader1.b >= 0) {
        localTransferableMessage.jdField_a_of_type_Boolean = paramDecoder.a(40, 0);
      }
      return localTransferableMessage;
    }
    finally
    {
      paramDecoder.b();
    }
  }
  
  public static TransferableMessage a(Message paramMessage)
  {
    return a(new Decoder(paramMessage));
  }
  
  protected final void encode(Encoder paramEncoder)
  {
    paramEncoder = paramEncoder.a(jdField_a_of_type_OrgChromiumMojoBindingsDataHeader);
    paramEncoder.a(this.jdField_a_of_type_OrgChromiumBlinkMojomCloneableMessage, 8, false);
    paramEncoder.a(this.jdField_a_of_type_ArrayOfOrgChromiumMojoSystemMessagePipeHandle, 16, 0, -1);
    Object localObject1 = this.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedArrayBufferContents;
    int i;
    Object localObject2;
    if (localObject1 == null)
    {
      paramEncoder.a(24, false);
    }
    else
    {
      localObject1 = paramEncoder.a(localObject1.length, 24, -1);
      i = 0;
      for (;;)
      {
        localObject2 = this.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedArrayBufferContents;
        if (i >= localObject2.length) {
          break;
        }
        ((Encoder)localObject1).a(localObject2[i], i * 8 + 8, false);
        i += 1;
      }
    }
    localObject1 = this.jdField_a_of_type_ArrayOfOrgChromiumSkiaMojomBitmap;
    if (localObject1 == null)
    {
      paramEncoder.a(32, false);
    }
    else
    {
      localObject1 = paramEncoder.a(localObject1.length, 32, -1);
      i = 0;
      for (;;)
      {
        localObject2 = this.jdField_a_of_type_ArrayOfOrgChromiumSkiaMojomBitmap;
        if (i >= localObject2.length) {
          break;
        }
        ((Encoder)localObject1).a(localObject2[i], i * 8 + 8, false);
        i += 1;
      }
    }
    paramEncoder.a(this.jdField_a_of_type_Boolean, 40, 0);
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
    paramObject = (TransferableMessage)paramObject;
    if (!BindingsHelper.a(this.jdField_a_of_type_OrgChromiumBlinkMojomCloneableMessage, ((TransferableMessage)paramObject).jdField_a_of_type_OrgChromiumBlinkMojomCloneableMessage)) {
      return false;
    }
    if (!Arrays.deepEquals(this.jdField_a_of_type_ArrayOfOrgChromiumMojoSystemMessagePipeHandle, ((TransferableMessage)paramObject).jdField_a_of_type_ArrayOfOrgChromiumMojoSystemMessagePipeHandle)) {
      return false;
    }
    if (!Arrays.deepEquals(this.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedArrayBufferContents, ((TransferableMessage)paramObject).jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedArrayBufferContents)) {
      return false;
    }
    if (!Arrays.deepEquals(this.jdField_a_of_type_ArrayOfOrgChromiumSkiaMojomBitmap, ((TransferableMessage)paramObject).jdField_a_of_type_ArrayOfOrgChromiumSkiaMojomBitmap)) {
      return false;
    }
    return this.jdField_a_of_type_Boolean == ((TransferableMessage)paramObject).jdField_a_of_type_Boolean;
  }
  
  public int hashCode()
  {
    return (((((getClass().hashCode() + 31) * 31 + BindingsHelper.a(this.jdField_a_of_type_OrgChromiumBlinkMojomCloneableMessage)) * 31 + Arrays.deepHashCode(this.jdField_a_of_type_ArrayOfOrgChromiumMojoSystemMessagePipeHandle)) * 31 + Arrays.deepHashCode(this.jdField_a_of_type_ArrayOfOrgChromiumBlinkMojomSerializedArrayBufferContents)) * 31 + Arrays.deepHashCode(this.jdField_a_of_type_ArrayOfOrgChromiumSkiaMojomBitmap)) * 31 + BindingsHelper.a(this.jdField_a_of_type_Boolean);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\blink\mojom\TransferableMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */