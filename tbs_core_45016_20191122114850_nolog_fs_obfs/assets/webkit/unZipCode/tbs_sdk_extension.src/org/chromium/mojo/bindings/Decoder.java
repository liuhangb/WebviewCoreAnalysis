package org.chromium.mojo.bindings;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.List;
import org.chromium.mojo.system.DataPipe.ProducerHandle;
import org.chromium.mojo.system.Handle;
import org.chromium.mojo.system.InvalidHandle;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.UntypedHandle;

public class Decoder
{
  private final int jdField_a_of_type_Int;
  private final Validator jdField_a_of_type_OrgChromiumMojoBindingsDecoder$Validator;
  private final Message jdField_a_of_type_OrgChromiumMojoBindingsMessage;
  
  public Decoder(Message paramMessage)
  {
    this(paramMessage, new Validator(paramMessage.a().limit(), paramMessage.a().size()), 0);
  }
  
  private Decoder(Message paramMessage, Validator paramValidator, int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsMessage = paramMessage;
    this.jdField_a_of_type_OrgChromiumMojoBindingsMessage.a().order(ByteOrder.LITTLE_ENDIAN);
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_a_of_type_OrgChromiumMojoBindingsDecoder$Validator = paramValidator;
  }
  
  private DataHeader a(int paramInt, boolean paramBoolean)
  {
    int i = a(paramInt + 0);
    paramInt = a(paramInt + 4);
    if (i >= 0)
    {
      if ((paramInt < 0) && ((!paramBoolean) || (paramInt != -1))) {
        throw new DeserializationException("Negative elements or version. Unsigned integers are not valid for java.");
      }
      return new DataHeader(i, paramInt);
    }
    throw new DeserializationException("Negative size. Unsigned integers are not valid for java.");
  }
  
  private DataHeader a(long paramLong, int paramInt)
  {
    DataHeader localDataHeader = a();
    if (localDataHeader.jdField_a_of_type_Int >= paramLong * localDataHeader.b + 8L)
    {
      if (paramInt != -1)
      {
        if (localDataHeader.b == paramInt) {
          return localDataHeader;
        }
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Incorrect array length. Expected: ");
        localStringBuilder.append(paramInt);
        localStringBuilder.append(", but got: ");
        localStringBuilder.append(localDataHeader.b);
        localStringBuilder.append(".");
        throw new DeserializationException(localStringBuilder.toString());
      }
      return localDataHeader;
    }
    throw new DeserializationException("Array header is incorrect.");
  }
  
  private Decoder a(int paramInt)
  {
    return new Decoder(this.jdField_a_of_type_OrgChromiumMojoBindingsMessage, this.jdField_a_of_type_OrgChromiumMojoBindingsDecoder$Validator, paramInt);
  }
  
  private void a(int paramInt1, int paramInt2)
  {
    if (this.jdField_a_of_type_OrgChromiumMojoBindingsMessage.a().limit() >= paramInt1 + paramInt2) {
      return;
    }
    throw new DeserializationException("Buffer is smaller than expected.");
  }
  
  public byte a(int paramInt)
  {
    a(paramInt, 1);
    return this.jdField_a_of_type_OrgChromiumMojoBindingsMessage.a().get(this.jdField_a_of_type_Int + paramInt);
  }
  
  public double a(int paramInt)
  {
    a(paramInt, 8);
    return this.jdField_a_of_type_OrgChromiumMojoBindingsMessage.a().getDouble(this.jdField_a_of_type_Int + paramInt);
  }
  
  public int a(int paramInt)
  {
    a(paramInt, 4);
    return this.jdField_a_of_type_OrgChromiumMojoBindingsMessage.a().getInt(this.jdField_a_of_type_Int + paramInt);
  }
  
  public long a(int paramInt)
  {
    a(paramInt, 8);
    return this.jdField_a_of_type_OrgChromiumMojoBindingsMessage.a().getLong(this.jdField_a_of_type_Int + paramInt);
  }
  
  public String a(int paramInt, boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public AssociatedInterfaceRequestNotSupported a(int paramInt, boolean paramBoolean)
  {
    return null;
  }
  
  public DataHeader a()
  {
    Object localObject = this.jdField_a_of_type_OrgChromiumMojoBindingsDecoder$Validator;
    int i = this.jdField_a_of_type_Int;
    ((Validator)localObject).a(i, i + 8);
    localObject = a(0, false);
    Validator localValidator = this.jdField_a_of_type_OrgChromiumMojoBindingsDecoder$Validator;
    i = this.jdField_a_of_type_Int;
    localValidator.a(i + 8, i + ((DataHeader)localObject).jdField_a_of_type_Int);
    return (DataHeader)localObject;
  }
  
  public DataHeader a(int paramInt)
  {
    DataHeader localDataHeader = a(paramInt, true);
    if (localDataHeader.jdField_a_of_type_Int == 0)
    {
      if (localDataHeader.b == 0) {
        return localDataHeader;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Unexpected version tag for a null union. Expecting 0, found: ");
      localStringBuilder.append(localDataHeader.b);
      throw new DeserializationException(localStringBuilder.toString());
    }
    if (localDataHeader.jdField_a_of_type_Int == 16) {
      return localDataHeader;
    }
    throw new DeserializationException("Unexpected size of an union. The size must be 0 for a null union, or 16 for a non-null union.");
  }
  
  public DataHeader a(DataHeader[] paramArrayOfDataHeader)
  {
    DataHeader localDataHeader = a();
    int i = paramArrayOfDataHeader.length - 1;
    if (localDataHeader.b <= paramArrayOfDataHeader[i].b)
    {
      Object localObject2 = null;
      Object localObject1;
      for (;;)
      {
        localObject1 = localObject2;
        if (i < 0) {
          break;
        }
        localObject1 = paramArrayOfDataHeader[i];
        if (localDataHeader.b >= ((DataHeader)localObject1).b) {
          break;
        }
        i -= 1;
      }
      if ((localObject1 != null) && (((DataHeader)localObject1).jdField_a_of_type_Int == localDataHeader.jdField_a_of_type_Int)) {
        return localDataHeader;
      }
      throw new DeserializationException("Header doesn't correspond to any known version.");
    }
    if (localDataHeader.jdField_a_of_type_Int >= paramArrayOfDataHeader[i].jdField_a_of_type_Int) {
      return localDataHeader;
    }
    throw new DeserializationException("Message newer than the last known version cannot be shorter than required by the last known version.");
  }
  
  public Decoder a(int paramInt, boolean paramBoolean)
  {
    int i = this.jdField_a_of_type_Int;
    long l = a(paramInt);
    if (l == 0L)
    {
      if (paramBoolean) {
        return null;
      }
      throw new DeserializationException("Trying to decode null pointer for a non-nullable type.");
    }
    return a((int)(i + paramInt + l));
  }
  
  public <P extends Interface.Proxy> P a(int paramInt, boolean paramBoolean, Interface.Manager<?, P> paramManager)
  {
    MessagePipeHandle localMessagePipeHandle = a(paramInt, paramBoolean);
    if (!localMessagePipeHandle.isValid()) {
      return null;
    }
    return paramManager.attachProxy(localMessagePipeHandle, a(paramInt + 4));
  }
  
  public <I extends Interface> InterfaceRequest<I> a(int paramInt, boolean paramBoolean)
  {
    MessagePipeHandle localMessagePipeHandle = a(paramInt, paramBoolean);
    if (localMessagePipeHandle == null) {
      return null;
    }
    return new InterfaceRequest(localMessagePipeHandle);
  }
  
  public DataPipe.ProducerHandle a(int paramInt, boolean paramBoolean)
  {
    return a(paramInt, paramBoolean).toDataPipeProducerHandle();
  }
  
  public Handle a(int paramInt, boolean paramBoolean)
  {
    paramInt = a(paramInt);
    if (paramInt == -1)
    {
      if (paramBoolean) {
        return InvalidHandle.a;
      }
      throw new DeserializationException("Trying to decode an invalid handle for a non-nullable type.");
    }
    this.jdField_a_of_type_OrgChromiumMojoBindingsDecoder$Validator.a(paramInt);
    return (Handle)this.jdField_a_of_type_OrgChromiumMojoBindingsMessage.a().get(paramInt);
  }
  
  public MessagePipeHandle a(int paramInt, boolean paramBoolean)
  {
    return a(paramInt, paramBoolean).toMessagePipeHandle();
  }
  
  public UntypedHandle a(int paramInt, boolean paramBoolean)
  {
    return a(paramInt, paramBoolean).toUntypedHandle();
  }
  
  public void a()
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsDecoder$Validator.a();
  }
  
  public boolean a(int paramInt1, int paramInt2)
  {
    a(paramInt1, 1);
    return (a(paramInt1) & 1 << paramInt2) != 0;
  }
  
  public byte[] a(int paramInt1, int paramInt2, int paramInt3)
  {
    Decoder localDecoder = a(paramInt1, BindingsHelper.a(paramInt2));
    if (localDecoder == null) {
      return null;
    }
    byte[] arrayOfByte = new byte[localDecoder.a(1L, paramInt3).b];
    localDecoder.jdField_a_of_type_OrgChromiumMojoBindingsMessage.a().position(localDecoder.jdField_a_of_type_Int + 8);
    localDecoder.jdField_a_of_type_OrgChromiumMojoBindingsMessage.a().get(arrayOfByte);
    return arrayOfByte;
  }
  
  public MessagePipeHandle[] a(int paramInt1, int paramInt2, int paramInt3)
  {
    Decoder localDecoder = a(paramInt1, BindingsHelper.a(paramInt2));
    if (localDecoder == null) {
      return null;
    }
    MessagePipeHandle[] arrayOfMessagePipeHandle = new MessagePipeHandle[localDecoder.a(4L, paramInt3).b];
    paramInt1 = 0;
    while (paramInt1 < arrayOfMessagePipeHandle.length)
    {
      arrayOfMessagePipeHandle[paramInt1] = localDecoder.a(paramInt1 * 4 + 8, BindingsHelper.b(paramInt2));
      paramInt1 += 1;
    }
    return arrayOfMessagePipeHandle;
  }
  
  public DataHeader b(int paramInt)
  {
    return a(8L, paramInt);
  }
  
  public void b()
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsDecoder$Validator.b();
  }
  
  static final class Validator
  {
    private int jdField_a_of_type_Int;
    private long jdField_a_of_type_Long;
    private long b;
    private final long c;
    private final long d;
    
    Validator(long paramLong, int paramInt)
    {
      this.c = paramLong;
      this.d = paramInt;
      this.b = 0L;
    }
    
    public void a()
    {
      this.b += 1L;
      if (this.b < 100L) {
        return;
      }
      throw new DeserializationException("Recursion depth limit exceeded.");
    }
    
    public void a(int paramInt)
    {
      if (paramInt >= this.jdField_a_of_type_Int)
      {
        if (paramInt < this.d)
        {
          this.jdField_a_of_type_Int = (paramInt + 1);
          return;
        }
        throw new DeserializationException("Trying to access non present handle.");
      }
      throw new DeserializationException("Trying to access handle out of order.");
    }
    
    public void a(long paramLong1, long paramLong2)
    {
      if (paramLong1 % 8L == 0L)
      {
        if (paramLong1 >= this.jdField_a_of_type_Long)
        {
          if (paramLong2 >= paramLong1)
          {
            if (paramLong2 <= this.c)
            {
              this.jdField_a_of_type_Long = BindingsHelper.a(paramLong2);
              return;
            }
            throw new DeserializationException("Trying to access out of range memory.");
          }
          throw new DeserializationException("Incorrect memory range.");
        }
        throw new DeserializationException("Trying to access memory out of order.");
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("Incorrect starting alignment: ");
      localStringBuilder.append(paramLong1);
      localStringBuilder.append(".");
      throw new DeserializationException(localStringBuilder.toString());
    }
    
    public void b()
    {
      this.b -= 1L;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\Decoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */