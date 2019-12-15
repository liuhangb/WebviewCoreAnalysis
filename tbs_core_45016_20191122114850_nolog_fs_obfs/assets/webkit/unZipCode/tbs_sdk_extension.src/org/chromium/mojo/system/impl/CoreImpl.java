package org.chromium.mojo.system.impl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.annotations.MainDex;
import org.chromium.mojo.system.Core;
import org.chromium.mojo.system.Core.HandleSignals;
import org.chromium.mojo.system.Core.HandleSignalsState;
import org.chromium.mojo.system.DataPipe.ConsumerHandle;
import org.chromium.mojo.system.DataPipe.CreateFlags;
import org.chromium.mojo.system.DataPipe.CreateOptions;
import org.chromium.mojo.system.DataPipe.ProducerHandle;
import org.chromium.mojo.system.DataPipe.ReadFlags;
import org.chromium.mojo.system.DataPipe.WriteFlags;
import org.chromium.mojo.system.Handle;
import org.chromium.mojo.system.MessagePipeHandle;
import org.chromium.mojo.system.MessagePipeHandle.CreateFlags;
import org.chromium.mojo.system.MessagePipeHandle.CreateOptions;
import org.chromium.mojo.system.MessagePipeHandle.ReadFlags;
import org.chromium.mojo.system.MessagePipeHandle.ReadMessageResult;
import org.chromium.mojo.system.MessagePipeHandle.WriteFlags;
import org.chromium.mojo.system.MojoException;
import org.chromium.mojo.system.Pair;
import org.chromium.mojo.system.ResultAnd;
import org.chromium.mojo.system.RunLoop;
import org.chromium.mojo.system.SharedBufferHandle;
import org.chromium.mojo.system.SharedBufferHandle.CreateFlags;
import org.chromium.mojo.system.SharedBufferHandle.CreateOptions;
import org.chromium.mojo.system.SharedBufferHandle.DuplicateFlags;
import org.chromium.mojo.system.SharedBufferHandle.DuplicateOptions;
import org.chromium.mojo.system.SharedBufferHandle.MapFlags;
import org.chromium.mojo.system.UntypedHandle;
import org.chromium.mojo.system.Watcher;

@JNINamespace("mojo::android")
@MainDex
public class CoreImpl
  implements Core
{
  private final int jdField_a_of_type_Int = nativeGetNativeBufferOffset(ByteBuffer.allocateDirect(8), 8);
  private final ThreadLocal<BaseRunLoop> jdField_a_of_type_JavaLangThreadLocal = new ThreadLocal();
  
  private int a(Handle paramHandle)
  {
    if (paramHandle.isValid()) {
      return ((HandleBase)paramHandle).a();
    }
    return 0;
  }
  
  private ByteBuffer a(int paramInt)
  {
    ByteBuffer localByteBuffer2 = ByteBuffer.allocateDirect(paramInt + this.jdField_a_of_type_Int);
    paramInt = this.jdField_a_of_type_Int;
    ByteBuffer localByteBuffer1 = localByteBuffer2;
    if (paramInt != 0)
    {
      localByteBuffer2.position(paramInt);
      localByteBuffer1 = localByteBuffer2.slice();
    }
    return localByteBuffer1.order(ByteOrder.nativeOrder());
  }
  
  public static Core a()
  {
    return LazyHolder.a();
  }
  
  private native ResultAnd<ByteBuffer> nativeBeginReadData(int paramInt1, int paramInt2, int paramInt3);
  
  private native ResultAnd<ByteBuffer> nativeBeginWriteData(int paramInt1, int paramInt2, int paramInt3);
  
  private native int nativeClose(int paramInt);
  
  private native ResultAnd<IntegerPair> nativeCreateDataPipe(ByteBuffer paramByteBuffer);
  
  private native ResultAnd<IntegerPair> nativeCreateMessagePipe(ByteBuffer paramByteBuffer);
  
  private native ResultAnd<Integer> nativeCreateSharedBuffer(ByteBuffer paramByteBuffer, long paramLong);
  
  private native ResultAnd<Integer> nativeDuplicate(int paramInt, ByteBuffer paramByteBuffer);
  
  private native int nativeEndReadData(int paramInt1, int paramInt2);
  
  private native int nativeEndWriteData(int paramInt1, int paramInt2);
  
  private native int nativeGetNativeBufferOffset(ByteBuffer paramByteBuffer, int paramInt);
  
  private native long nativeGetTimeTicksNow();
  
  private native ResultAnd<ByteBuffer> nativeMap(int paramInt1, long paramLong1, long paramLong2, int paramInt2);
  
  private native int nativeQueryHandleSignalsState(int paramInt, ByteBuffer paramByteBuffer);
  
  private native ResultAnd<Integer> nativeReadData(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3);
  
  private native ResultAnd<MessagePipeHandle.ReadMessageResult> nativeReadMessage(int paramInt1, int paramInt2);
  
  private native int nativeUnmap(ByteBuffer paramByteBuffer);
  
  private native ResultAnd<Integer> nativeWriteData(int paramInt1, ByteBuffer paramByteBuffer, int paramInt2, int paramInt3);
  
  private native int nativeWriteMessage(int paramInt1, ByteBuffer paramByteBuffer1, int paramInt2, ByteBuffer paramByteBuffer2, int paramInt3);
  
  @CalledByNative
  private static ResultAnd<IntegerPair> newNativeCreationResult(int paramInt1, int paramInt2, int paramInt3)
  {
    return new ResultAnd(paramInt1, new IntegerPair(Integer.valueOf(paramInt2), Integer.valueOf(paramInt3)));
  }
  
  @CalledByNative
  private static ResultAnd<MessagePipeHandle.ReadMessageResult> newReadMessageResult(int paramInt, byte[] paramArrayOfByte, int[] paramArrayOfInt)
  {
    MessagePipeHandle.ReadMessageResult localReadMessageResult = new MessagePipeHandle.ReadMessageResult();
    if (paramInt == 0)
    {
      localReadMessageResult.jdField_a_of_type_ArrayOfByte = paramArrayOfByte;
      localReadMessageResult.jdField_a_of_type_ArrayOfInt = paramArrayOfInt;
    }
    return new ResultAnd(paramInt, localReadMessageResult);
  }
  
  @CalledByNative
  private static ResultAnd<ByteBuffer> newResultAndBuffer(int paramInt, ByteBuffer paramByteBuffer)
  {
    return new ResultAnd(paramInt, paramByteBuffer);
  }
  
  @CalledByNative
  private static ResultAnd<Integer> newResultAndInteger(int paramInt1, int paramInt2)
  {
    return new ResultAnd(paramInt1, Integer.valueOf(paramInt2));
  }
  
  int a(int paramInt)
  {
    return nativeClose(paramInt);
  }
  
  int a(DataPipeConsumerHandleImpl paramDataPipeConsumerHandleImpl, int paramInt, DataPipe.ReadFlags paramReadFlags)
  {
    paramDataPipeConsumerHandleImpl = nativeReadData(paramDataPipeConsumerHandleImpl.a(), null, paramInt, paramReadFlags.a() | 0x2);
    if (paramDataPipeConsumerHandleImpl.a() == 0) {
      return ((Integer)paramDataPipeConsumerHandleImpl.a()).intValue();
    }
    throw new MojoException(paramDataPipeConsumerHandleImpl.a());
  }
  
  ByteBuffer a(DataPipeConsumerHandleImpl paramDataPipeConsumerHandleImpl, int paramInt, DataPipe.ReadFlags paramReadFlags)
  {
    paramDataPipeConsumerHandleImpl = nativeBeginReadData(paramDataPipeConsumerHandleImpl.a(), paramInt, paramReadFlags.a());
    if (paramDataPipeConsumerHandleImpl.a() == 0) {
      return ((ByteBuffer)paramDataPipeConsumerHandleImpl.a()).asReadOnlyBuffer();
    }
    throw new MojoException(paramDataPipeConsumerHandleImpl.a());
  }
  
  ByteBuffer a(DataPipeProducerHandleImpl paramDataPipeProducerHandleImpl, int paramInt, DataPipe.WriteFlags paramWriteFlags)
  {
    paramDataPipeProducerHandleImpl = nativeBeginWriteData(paramDataPipeProducerHandleImpl.a(), paramInt, paramWriteFlags.a());
    if (paramDataPipeProducerHandleImpl.a() == 0) {
      return (ByteBuffer)paramDataPipeProducerHandleImpl.a();
    }
    throw new MojoException(paramDataPipeProducerHandleImpl.a());
  }
  
  ByteBuffer a(SharedBufferHandleImpl paramSharedBufferHandleImpl, long paramLong1, long paramLong2, SharedBufferHandle.MapFlags paramMapFlags)
  {
    paramSharedBufferHandleImpl = nativeMap(paramSharedBufferHandleImpl.a(), paramLong1, paramLong2, paramMapFlags.a());
    if (paramSharedBufferHandleImpl.a() == 0) {
      return (ByteBuffer)paramSharedBufferHandleImpl.a();
    }
    throw new MojoException(paramSharedBufferHandleImpl.a());
  }
  
  Core.HandleSignalsState a(int paramInt)
  {
    ByteBuffer localByteBuffer = a(8);
    paramInt = nativeQueryHandleSignalsState(paramInt, localByteBuffer);
    if (paramInt == 0) {
      return new Core.HandleSignalsState(new Core.HandleSignals(localByteBuffer.getInt(0)), new Core.HandleSignals(localByteBuffer.getInt(4)));
    }
    throw new MojoException(paramInt);
  }
  
  ResultAnd<Integer> a(DataPipeConsumerHandleImpl paramDataPipeConsumerHandleImpl, ByteBuffer paramByteBuffer, DataPipe.ReadFlags paramReadFlags)
  {
    int j = paramDataPipeConsumerHandleImpl.a();
    int i;
    if (paramByteBuffer == null) {
      i = 0;
    } else {
      i = paramByteBuffer.capacity();
    }
    paramDataPipeConsumerHandleImpl = nativeReadData(j, paramByteBuffer, i, paramReadFlags.a());
    if ((paramDataPipeConsumerHandleImpl.a() != 0) && (paramDataPipeConsumerHandleImpl.a() != 17)) {
      throw new MojoException(paramDataPipeConsumerHandleImpl.a());
    }
    if ((paramDataPipeConsumerHandleImpl.a() == 0) && (paramByteBuffer != null)) {
      paramByteBuffer.limit(((Integer)paramDataPipeConsumerHandleImpl.a()).intValue());
    }
    return paramDataPipeConsumerHandleImpl;
  }
  
  ResultAnd<Integer> a(DataPipeProducerHandleImpl paramDataPipeProducerHandleImpl, ByteBuffer paramByteBuffer, DataPipe.WriteFlags paramWriteFlags)
  {
    return nativeWriteData(paramDataPipeProducerHandleImpl.a(), paramByteBuffer, paramByteBuffer.limit(), paramWriteFlags.a());
  }
  
  ResultAnd<MessagePipeHandle.ReadMessageResult> a(MessagePipeHandleImpl paramMessagePipeHandleImpl, MessagePipeHandle.ReadFlags paramReadFlags)
  {
    paramMessagePipeHandleImpl = nativeReadMessage(paramMessagePipeHandleImpl.a(), paramReadFlags.a());
    if ((paramMessagePipeHandleImpl.a() != 0) && (paramMessagePipeHandleImpl.a() != 17)) {
      throw new MojoException(paramMessagePipeHandleImpl.a());
    }
    paramReadFlags = (MessagePipeHandle.ReadMessageResult)paramMessagePipeHandleImpl.a();
    int[] arrayOfInt = paramReadFlags.jdField_a_of_type_ArrayOfInt;
    int i = 0;
    int j;
    if ((arrayOfInt != null) && (arrayOfInt.length != 0))
    {
      paramReadFlags.jdField_a_of_type_JavaUtilList = new ArrayList(arrayOfInt.length);
      j = arrayOfInt.length;
    }
    while (i < j)
    {
      int k = arrayOfInt[i];
      paramReadFlags.jdField_a_of_type_JavaUtilList.add(new UntypedHandleImpl(this, k));
      i += 1;
      continue;
      paramReadFlags.jdField_a_of_type_JavaUtilList = new ArrayList(0);
    }
    return paramMessagePipeHandleImpl;
  }
  
  SharedBufferHandle a(SharedBufferHandleImpl paramSharedBufferHandleImpl, SharedBufferHandle.DuplicateOptions paramDuplicateOptions)
  {
    if (paramDuplicateOptions != null)
    {
      ByteBuffer localByteBuffer = a(8);
      localByteBuffer.putInt(0, 8);
      localByteBuffer.putInt(4, paramDuplicateOptions.a().a());
      paramDuplicateOptions = localByteBuffer;
    }
    else
    {
      paramDuplicateOptions = null;
    }
    paramSharedBufferHandleImpl = nativeDuplicate(paramSharedBufferHandleImpl.a(), paramDuplicateOptions);
    if (paramSharedBufferHandleImpl.a() == 0) {
      return new SharedBufferHandleImpl(this, ((Integer)paramSharedBufferHandleImpl.a()).intValue());
    }
    throw new MojoException(paramSharedBufferHandleImpl.a());
  }
  
  void a()
  {
    this.jdField_a_of_type_JavaLangThreadLocal.remove();
  }
  
  void a(int paramInt)
  {
    paramInt = nativeClose(paramInt);
    if (paramInt == 0) {
      return;
    }
    throw new MojoException(paramInt);
  }
  
  void a(ByteBuffer paramByteBuffer)
  {
    int i = nativeUnmap(paramByteBuffer);
    if (i == 0) {
      return;
    }
    throw new MojoException(i);
  }
  
  void a(DataPipeConsumerHandleImpl paramDataPipeConsumerHandleImpl, int paramInt)
  {
    paramInt = nativeEndReadData(paramDataPipeConsumerHandleImpl.a(), paramInt);
    if (paramInt == 0) {
      return;
    }
    throw new MojoException(paramInt);
  }
  
  void a(DataPipeProducerHandleImpl paramDataPipeProducerHandleImpl, int paramInt)
  {
    paramInt = nativeEndWriteData(paramDataPipeProducerHandleImpl.a(), paramInt);
    if (paramInt == 0) {
      return;
    }
    throw new MojoException(paramInt);
  }
  
  void a(MessagePipeHandleImpl paramMessagePipeHandleImpl, ByteBuffer paramByteBuffer, List<? extends Handle> paramList, MessagePipeHandle.WriteFlags paramWriteFlags)
  {
    ByteBuffer localByteBuffer;
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      localByteBuffer = a(paramList.size() * 4);
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext()) {
        localByteBuffer.putInt(a((Handle)localIterator.next()));
      }
      localByteBuffer.position(0);
    }
    else
    {
      localByteBuffer = null;
    }
    int j = paramMessagePipeHandleImpl.a();
    if (paramByteBuffer == null) {
      i = 0;
    } else {
      i = paramByteBuffer.limit();
    }
    int i = nativeWriteMessage(j, paramByteBuffer, i, localByteBuffer, paramWriteFlags.a());
    if (i == 0)
    {
      if (paramList != null)
      {
        paramMessagePipeHandleImpl = paramList.iterator();
        while (paramMessagePipeHandleImpl.hasNext())
        {
          paramByteBuffer = (Handle)paramMessagePipeHandleImpl.next();
          if (paramByteBuffer.isValid()) {
            ((HandleBase)paramByteBuffer).a();
          }
        }
      }
      return;
    }
    throw new MojoException(i);
  }
  
  public UntypedHandle acquireNativeHandle(int paramInt)
  {
    return new UntypedHandleImpl(this, paramInt);
  }
  
  public Pair<DataPipe.ProducerHandle, DataPipe.ConsumerHandle> createDataPipe(DataPipe.CreateOptions paramCreateOptions)
  {
    if (paramCreateOptions != null)
    {
      ByteBuffer localByteBuffer = a(16);
      localByteBuffer.putInt(0, 16);
      localByteBuffer.putInt(4, paramCreateOptions.a().a());
      localByteBuffer.putInt(8, paramCreateOptions.a());
      localByteBuffer.putInt(12, paramCreateOptions.b());
      paramCreateOptions = localByteBuffer;
    }
    else
    {
      paramCreateOptions = null;
    }
    paramCreateOptions = nativeCreateDataPipe(paramCreateOptions);
    if (paramCreateOptions.a() == 0) {
      return Pair.a(new DataPipeProducerHandleImpl(this, ((Integer)((IntegerPair)paramCreateOptions.a()).a).intValue()), new DataPipeConsumerHandleImpl(this, ((Integer)((IntegerPair)paramCreateOptions.a()).b).intValue()));
    }
    throw new MojoException(paramCreateOptions.a());
  }
  
  public RunLoop createDefaultRunLoop()
  {
    if (this.jdField_a_of_type_JavaLangThreadLocal.get() == null)
    {
      BaseRunLoop localBaseRunLoop = new BaseRunLoop(this);
      this.jdField_a_of_type_JavaLangThreadLocal.set(localBaseRunLoop);
      return localBaseRunLoop;
    }
    throw new MojoException(9);
  }
  
  public Pair<MessagePipeHandle, MessagePipeHandle> createMessagePipe(MessagePipeHandle.CreateOptions paramCreateOptions)
  {
    if (paramCreateOptions != null)
    {
      ByteBuffer localByteBuffer = a(8);
      localByteBuffer.putInt(0, 8);
      localByteBuffer.putInt(4, paramCreateOptions.a().a());
      paramCreateOptions = localByteBuffer;
    }
    else
    {
      paramCreateOptions = null;
    }
    paramCreateOptions = nativeCreateMessagePipe(paramCreateOptions);
    if (paramCreateOptions.a() == 0) {
      return Pair.a(new MessagePipeHandleImpl(this, ((Integer)((IntegerPair)paramCreateOptions.a()).a).intValue()), new MessagePipeHandleImpl(this, ((Integer)((IntegerPair)paramCreateOptions.a()).b).intValue()));
    }
    throw new MojoException(paramCreateOptions.a());
  }
  
  public SharedBufferHandle createSharedBuffer(SharedBufferHandle.CreateOptions paramCreateOptions, long paramLong)
  {
    if (paramCreateOptions != null)
    {
      ByteBuffer localByteBuffer = a(8);
      localByteBuffer.putInt(0, 8);
      localByteBuffer.putInt(4, paramCreateOptions.a().a());
      paramCreateOptions = localByteBuffer;
    }
    else
    {
      paramCreateOptions = null;
    }
    paramCreateOptions = nativeCreateSharedBuffer(paramCreateOptions, paramLong);
    if (paramCreateOptions.a() == 0) {
      return new SharedBufferHandleImpl(this, ((Integer)paramCreateOptions.a()).intValue());
    }
    throw new MojoException(paramCreateOptions.a());
  }
  
  public RunLoop getCurrentRunLoop()
  {
    return (RunLoop)this.jdField_a_of_type_JavaLangThreadLocal.get();
  }
  
  public long getTimeTicksNow()
  {
    return nativeGetTimeTicksNow();
  }
  
  public Watcher getWatcher()
  {
    return new WatcherImpl();
  }
  
  private static final class IntegerPair
    extends Pair<Integer, Integer>
  {
    public IntegerPair(Integer paramInteger1, Integer paramInteger2)
    {
      super(paramInteger2);
    }
  }
  
  private static class LazyHolder
  {
    private static final Core a = new CoreImpl(null);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\mojo\system\impl\CoreImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */