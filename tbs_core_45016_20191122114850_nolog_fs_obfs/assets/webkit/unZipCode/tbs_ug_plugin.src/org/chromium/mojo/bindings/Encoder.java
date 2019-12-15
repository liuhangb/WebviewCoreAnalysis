package org.chromium.mojo.bindings;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.Handle;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.Pair;

public class Encoder
{
  private int jdField_a_of_type_Int;
  private final EncoderState jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState;
  
  private Encoder(EncoderState paramEncoderState)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState = paramEncoderState;
    this.jdField_a_of_type_Int = paramEncoderState.jdField_a_of_type_Int;
  }
  
  public Encoder(Core paramCore, int paramInt)
  {
    this(new EncoderState(paramCore, paramInt, null));
  }
  
  private Encoder a(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((paramInt4 != -1) && (paramInt4 != paramInt2)) {
      throw new SerializationException("Trying to encode a fixed array of incorrect length.");
    }
    return b(paramInt1 * paramInt2, paramInt2, paramInt3);
  }
  
  private void a(int paramInt)
  {
    a(this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_Int - (this.jdField_a_of_type_Int + paramInt), paramInt);
  }
  
  private void a(byte[] paramArrayOfByte)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.position(this.jdField_a_of_type_Int + 8);
    this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.put(paramArrayOfByte);
  }
  
  private void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    b(paramArrayOfByte.length, paramInt1, paramInt2).a(paramArrayOfByte);
  }
  
  private Encoder b(int paramInt1, int paramInt2, int paramInt3)
  {
    a(paramInt3);
    return a(new DataHeader(paramInt1 + 8, paramInt2));
  }
  
  public Encoder a(int paramInt)
  {
    a(paramInt);
    return a(BindingsHelper.a);
  }
  
  public Encoder a(int paramInt1, int paramInt2, int paramInt3)
  {
    return a(8, paramInt1, paramInt2, paramInt3);
  }
  
  public Encoder a(DataHeader paramDataHeader)
  {
    Encoder localEncoder = new Encoder(this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState);
    localEncoder.a(paramDataHeader);
    return localEncoder;
  }
  
  public Message a()
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.position(0);
    this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.limit(this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_Int);
    return new Message(this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer, this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaUtilList);
  }
  
  public void a(double paramDouble, int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.putDouble(this.jdField_a_of_type_Int + paramInt, paramDouble);
  }
  
  public void a(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.putInt(this.jdField_a_of_type_Int + paramInt2, paramInt1);
  }
  
  public void a(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.putLong(this.jdField_a_of_type_Int + paramInt, 0L);
      return;
    }
    throw new SerializationException("Trying to encode a null pointer for a non-nullable type.");
  }
  
  public void a(long paramLong, int paramInt)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.putLong(this.jdField_a_of_type_Int + paramInt, paramLong);
  }
  
  public void a(String paramString, int paramInt, boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge Z and I\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.useAs(TypeTransformer.java:868)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.enexpr(TypeTransformer.java:668)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:719)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.exExpr(TypeTransformer.java:703)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.s1stmt(TypeTransformer.java:810)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.sxStmt(TypeTransformer.java:840)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:206)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  public void a(AssociatedInterfaceRequestNotSupported paramAssociatedInterfaceRequestNotSupported, int paramInt, boolean paramBoolean) {}
  
  public void a(DataHeader paramDataHeader)
  {
    this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.a(BindingsHelper.a(paramDataHeader.jdField_a_of_type_Int));
    a(paramDataHeader.jdField_a_of_type_Int, 0);
    a(paramDataHeader.b, 4);
  }
  
  public <T extends Interface> void a(T paramT, int paramInt, boolean paramBoolean, Interface.Manager<T, ?> paramManager)
  {
    if (paramT == null)
    {
      b(paramInt, paramBoolean);
      a(0, paramInt + 4);
      return;
    }
    if (this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_OrgChromiumMojoSystemCore != null)
    {
      if ((paramT instanceof Interface.Proxy))
      {
        paramT = ((Interface.Proxy)paramT).getProxyHandler();
        a(paramT.passHandle(), paramInt, paramBoolean);
        a(paramT.getVersion(), paramInt + 4);
        return;
      }
      Pair localPair = this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_OrgChromiumMojoSystemCore.createMessagePipe(null);
      paramManager.a(paramT, (MessagePipeHandle)localPair.a);
      a((Handle)localPair.b, paramInt, paramBoolean);
      a(paramManager.getVersion(), paramInt + 4);
      return;
    }
    throw new UnsupportedOperationException("The encoder has been created without a Core. It can't encode an interface.");
  }
  
  public <I extends Interface> void a(InterfaceRequest<I> paramInterfaceRequest, int paramInt, boolean paramBoolean)
  {
    if (paramInterfaceRequest == null)
    {
      b(paramInt, paramBoolean);
      return;
    }
    if (this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_OrgChromiumMojoSystemCore != null)
    {
      a(paramInterfaceRequest.a(), paramInt, paramBoolean);
      return;
    }
    throw new UnsupportedOperationException("The encoder has been created without a Core. It can't encode an interface.");
  }
  
  public void a(Struct paramStruct, int paramInt, boolean paramBoolean)
  {
    if (paramStruct == null)
    {
      a(paramInt, paramBoolean);
      return;
    }
    a(paramInt);
    paramStruct.encode(this);
  }
  
  public void a(Union paramUnion, int paramInt, boolean paramBoolean)
  {
    if ((paramUnion == null) && (!paramBoolean)) {
      throw new SerializationException("Trying to encode a null pointer for a non-nullable type.");
    }
    if (paramUnion == null)
    {
      a(0L, paramInt);
      a(0L, paramInt + 8);
      return;
    }
    paramUnion.encode(this, paramInt);
  }
  
  public void a(Handle paramHandle, int paramInt, boolean paramBoolean)
  {
    if ((paramHandle != null) && (paramHandle.isValid()))
    {
      a(this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaUtilList.size(), paramInt);
      this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaUtilList.add(paramHandle);
      return;
    }
    b(paramInt, paramBoolean);
  }
  
  public void a(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    if (paramBoolean)
    {
      byte b = (byte)(this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.get(this.jdField_a_of_type_Int + paramInt1) | (byte)(1 << paramInt2));
      this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.put(this.jdField_a_of_type_Int + paramInt1, b);
    }
  }
  
  public void a(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramArrayOfByte == null)
    {
      a(paramInt1, BindingsHelper.a(paramInt2));
      return;
    }
    if ((paramInt3 != -1) && (paramInt3 != paramArrayOfByte.length)) {
      throw new SerializationException("Trying to encode a fixed array of incorrect length.");
    }
    a(paramArrayOfByte, paramArrayOfByte.length, paramInt1);
  }
  
  public void a(Handle[] paramArrayOfHandle, int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramArrayOfHandle == null)
    {
      a(paramInt1, BindingsHelper.a(paramInt2));
      return;
    }
    Encoder localEncoder = a(4, paramArrayOfHandle.length, paramInt1, paramInt3);
    paramInt1 = 0;
    while (paramInt1 < paramArrayOfHandle.length)
    {
      localEncoder.a(paramArrayOfHandle[paramInt1], paramInt1 * 4 + 8, BindingsHelper.b(paramInt2));
      paramInt1 += 1;
    }
  }
  
  public void b(int paramInt, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.jdField_a_of_type_OrgChromiumMojoBindingsEncoder$EncoderState.jdField_a_of_type_JavaNioByteBuffer.putInt(this.jdField_a_of_type_Int + paramInt, -1);
      return;
    }
    throw new SerializationException("Trying to encode an invalid handle for a non-nullable type.");
  }
  
  private static class EncoderState
  {
    public int a;
    public ByteBuffer a;
    public final List<Handle> a;
    public final Core a;
    
    static
    {
      jdField_a_of_type_Boolean = Encoder.class.desiredAssertionStatus() ^ true;
    }
    
    private EncoderState(Core paramCore, int paramInt)
    {
      this.jdField_a_of_type_JavaUtilList = new ArrayList();
      if ((!jdField_a_of_type_Boolean) && (paramInt % 8 != 0)) {
        throw new AssertionError();
      }
      this.jdField_a_of_type_OrgChromiumMojoSystemCore = paramCore;
      if (paramInt <= 0) {
        paramInt = 1024;
      }
      this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.allocateDirect(paramInt);
      this.jdField_a_of_type_JavaNioByteBuffer.order(ByteOrder.LITTLE_ENDIAN);
      this.jdField_a_of_type_Int = 0;
    }
    
    private void a()
    {
      if (this.jdField_a_of_type_JavaNioByteBuffer.capacity() >= this.jdField_a_of_type_Int) {
        return;
      }
      int i = this.jdField_a_of_type_JavaNioByteBuffer.capacity() * 2;
      while (i < this.jdField_a_of_type_Int) {
        i *= 2;
      }
      ByteBuffer localByteBuffer1 = ByteBuffer.allocateDirect(i);
      localByteBuffer1.order(ByteOrder.nativeOrder());
      this.jdField_a_of_type_JavaNioByteBuffer.position(0);
      ByteBuffer localByteBuffer2 = this.jdField_a_of_type_JavaNioByteBuffer;
      localByteBuffer2.limit(localByteBuffer2.capacity());
      localByteBuffer1.put(this.jdField_a_of_type_JavaNioByteBuffer);
      this.jdField_a_of_type_JavaNioByteBuffer = localByteBuffer1;
    }
    
    public void a(int paramInt)
    {
      this.jdField_a_of_type_Int += paramInt;
      a();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\bindings\Encoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */