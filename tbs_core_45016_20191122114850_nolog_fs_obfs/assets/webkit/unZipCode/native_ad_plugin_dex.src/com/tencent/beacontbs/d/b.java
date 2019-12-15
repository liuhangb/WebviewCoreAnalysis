package com.tencent.beacontbs.d;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class b
{
  private String jdField_a_of_type_JavaLangString = "GBK";
  private ByteBuffer jdField_a_of_type_JavaNioByteBuffer;
  
  public b()
  {
    this(128);
  }
  
  public b(int paramInt)
  {
    this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.allocate(paramInt);
  }
  
  private void a(int paramInt)
  {
    if (this.jdField_a_of_type_JavaNioByteBuffer.remaining() < paramInt)
    {
      int i = this.jdField_a_of_type_JavaNioByteBuffer.capacity();
      try
      {
        ByteBuffer localByteBuffer = ByteBuffer.allocate(i + paramInt << 1);
        localByteBuffer.put(this.jdField_a_of_type_JavaNioByteBuffer.array(), 0, this.jdField_a_of_type_JavaNioByteBuffer.position());
        this.jdField_a_of_type_JavaNioByteBuffer = localByteBuffer;
        return;
      }
      catch (IllegalArgumentException localIllegalArgumentException)
      {
        throw localIllegalArgumentException;
      }
    }
  }
  
  private void b(byte paramByte, int paramInt)
  {
    byte b;
    if (paramInt < 15)
    {
      b = (byte)(paramByte | paramInt << 4);
      this.jdField_a_of_type_JavaNioByteBuffer.put(b);
      return;
    }
    if (paramInt < 256)
    {
      b = (byte)(paramByte | 0xF0);
      this.jdField_a_of_type_JavaNioByteBuffer.put(b);
      this.jdField_a_of_type_JavaNioByteBuffer.put((byte)paramInt);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder("tag is too large: ");
    localStringBuilder.append(paramInt);
    throw new RuntimeException(localStringBuilder.toString());
  }
  
  public final int a(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    return 0;
  }
  
  public final ByteBuffer a()
  {
    return this.jdField_a_of_type_JavaNioByteBuffer;
  }
  
  public final void a(byte paramByte, int paramInt)
  {
    a(3);
    if (paramByte == 0)
    {
      b((byte)12, paramInt);
      return;
    }
    b((byte)0, paramInt);
    this.jdField_a_of_type_JavaNioByteBuffer.put(paramByte);
  }
  
  public final void a(int paramInt1, int paramInt2)
  {
    a(6);
    if ((paramInt1 >= 32768) && (paramInt1 <= 32767))
    {
      a((short)paramInt1, paramInt2);
      return;
    }
    b((byte)2, paramInt2);
    this.jdField_a_of_type_JavaNioByteBuffer.putInt(paramInt1);
  }
  
  public final void a(long paramLong, int paramInt)
  {
    a(10);
    if ((paramLong >= -2147483648L) && (paramLong <= 2147483647L))
    {
      a((int)paramLong, paramInt);
      return;
    }
    b((byte)3, paramInt);
    this.jdField_a_of_type_JavaNioByteBuffer.putLong(paramLong);
  }
  
  public final void a(Object paramObject, int paramInt)
  {
    if ((paramObject instanceof Byte))
    {
      a(((Byte)paramObject).byteValue(), paramInt);
      return;
    }
    if ((paramObject instanceof Boolean))
    {
      a((byte)((Boolean)paramObject).booleanValue(), paramInt);
      return;
    }
    if ((paramObject instanceof Short))
    {
      a(((Short)paramObject).shortValue(), paramInt);
      return;
    }
    if ((paramObject instanceof Integer))
    {
      a(((Integer)paramObject).intValue(), paramInt);
      return;
    }
    if ((paramObject instanceof Long))
    {
      a(((Long)paramObject).longValue(), paramInt);
      return;
    }
    float f;
    if ((paramObject instanceof Float))
    {
      f = ((Float)paramObject).floatValue();
      a(6);
      b((byte)4, paramInt);
      this.jdField_a_of_type_JavaNioByteBuffer.putFloat(f);
      return;
    }
    double d;
    if ((paramObject instanceof Double))
    {
      d = ((Double)paramObject).doubleValue();
      a(10);
      b((byte)5, paramInt);
      this.jdField_a_of_type_JavaNioByteBuffer.putDouble(d);
      return;
    }
    if ((paramObject instanceof String))
    {
      a((String)paramObject, paramInt);
      return;
    }
    if ((paramObject instanceof Map))
    {
      a((Map)paramObject, paramInt);
      return;
    }
    if ((paramObject instanceof List))
    {
      a((List)paramObject, paramInt);
      return;
    }
    if ((paramObject instanceof c))
    {
      paramObject = (c)paramObject;
      a(2);
      b((byte)10, paramInt);
      ((c)paramObject).a(this);
      a(2);
      b((byte)11, 0);
      return;
    }
    if ((paramObject instanceof byte[]))
    {
      a((byte[])paramObject, paramInt);
      return;
    }
    int i;
    if ((paramObject instanceof boolean[]))
    {
      paramObject = (boolean[])paramObject;
      a(8);
      b((byte)9, paramInt);
      a(paramObject.length, 0);
      i = paramObject.length;
      paramInt = 0;
      while (paramInt < i)
      {
        a((byte)paramObject[paramInt], 0);
        paramInt += 1;
      }
      return;
    }
    if ((paramObject instanceof short[]))
    {
      paramObject = (short[])paramObject;
      a(8);
      b((byte)9, paramInt);
      a(paramObject.length, 0);
      i = paramObject.length;
      paramInt = 0;
      while (paramInt < i)
      {
        a(paramObject[paramInt], 0);
        paramInt += 1;
      }
      return;
    }
    if ((paramObject instanceof int[]))
    {
      paramObject = (int[])paramObject;
      a(8);
      b((byte)9, paramInt);
      a(paramObject.length, 0);
      i = paramObject.length;
      paramInt = 0;
      while (paramInt < i)
      {
        a(paramObject[paramInt], 0);
        paramInt += 1;
      }
      return;
    }
    if ((paramObject instanceof long[]))
    {
      paramObject = (long[])paramObject;
      a(8);
      b((byte)9, paramInt);
      a(paramObject.length, 0);
      i = paramObject.length;
      paramInt = 0;
      while (paramInt < i)
      {
        a(paramObject[paramInt], 0);
        paramInt += 1;
      }
      return;
    }
    if ((paramObject instanceof float[]))
    {
      paramObject = (float[])paramObject;
      a(8);
      b((byte)9, paramInt);
      a(paramObject.length, 0);
      i = paramObject.length;
      paramInt = 0;
      while (paramInt < i)
      {
        f = paramObject[paramInt];
        a(6);
        b((byte)4, 0);
        this.jdField_a_of_type_JavaNioByteBuffer.putFloat(f);
        paramInt += 1;
      }
      return;
    }
    if ((paramObject instanceof double[]))
    {
      paramObject = (double[])paramObject;
      a(8);
      b((byte)9, paramInt);
      a(paramObject.length, 0);
      i = paramObject.length;
      paramInt = 0;
      while (paramInt < i)
      {
        d = paramObject[paramInt];
        a(10);
        b((byte)5, 0);
        this.jdField_a_of_type_JavaNioByteBuffer.putDouble(d);
        paramInt += 1;
      }
      return;
    }
    if (paramObject.getClass().isArray())
    {
      paramObject = (Object[])paramObject;
      a(8);
      b((byte)9, paramInt);
      a(paramObject.length, 0);
      i = paramObject.length;
      paramInt = 0;
      while (paramInt < i)
      {
        a(paramObject[paramInt], 0);
        paramInt += 1;
      }
      return;
    }
    if ((paramObject instanceof Collection))
    {
      a((Collection)paramObject, paramInt);
      return;
    }
    StringBuilder localStringBuilder = new StringBuilder("write object error: unsupport type. ");
    localStringBuilder.append(paramObject.getClass());
    throw new RuntimeException(localStringBuilder.toString());
  }
  
  public final void a(String paramString, int paramInt)
  {
    try
    {
      byte[] arrayOfByte = paramString.getBytes(this.jdField_a_of_type_JavaLangString);
      paramString = arrayOfByte;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      for (;;) {}
    }
    paramString = paramString.getBytes();
    a(paramString.length + 10);
    if (paramString.length > 255)
    {
      b((byte)7, paramInt);
      this.jdField_a_of_type_JavaNioByteBuffer.putInt(paramString.length);
      this.jdField_a_of_type_JavaNioByteBuffer.put(paramString);
      return;
    }
    b((byte)6, paramInt);
    this.jdField_a_of_type_JavaNioByteBuffer.put((byte)paramString.length);
    this.jdField_a_of_type_JavaNioByteBuffer.put(paramString);
  }
  
  public final <T> void a(Collection<T> paramCollection, int paramInt)
  {
    a(8);
    b((byte)9, paramInt);
    if (paramCollection == null) {
      paramInt = 0;
    } else {
      paramInt = paramCollection.size();
    }
    a(paramInt, 0);
    if (paramCollection != null)
    {
      paramCollection = paramCollection.iterator();
      while (paramCollection.hasNext()) {
        a(paramCollection.next(), 0);
      }
    }
  }
  
  public final <K, V> void a(Map<K, V> paramMap, int paramInt)
  {
    a(8);
    b((byte)8, paramInt);
    if (paramMap == null) {
      paramInt = 0;
    } else {
      paramInt = paramMap.size();
    }
    a(paramInt, 0);
    if (paramMap != null)
    {
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        a(localEntry.getKey(), 0);
        a(localEntry.getValue(), 1);
      }
    }
  }
  
  public final void a(short paramShort, int paramInt)
  {
    a(4);
    if ((paramShort >= -128) && (paramShort <= 127))
    {
      a((byte)paramShort, paramInt);
      return;
    }
    b((byte)1, paramInt);
    this.jdField_a_of_type_JavaNioByteBuffer.putShort(paramShort);
  }
  
  public final void a(boolean paramBoolean, int paramInt)
  {
    a((byte)paramBoolean, paramInt);
  }
  
  public final void a(byte[] paramArrayOfByte, int paramInt)
  {
    a(paramArrayOfByte.length + 8);
    b((byte)13, paramInt);
    b((byte)0, 0);
    a(paramArrayOfByte.length, 0);
    this.jdField_a_of_type_JavaNioByteBuffer.put(paramArrayOfByte);
  }
  
  public final byte[] a()
  {
    byte[] arrayOfByte = new byte[this.jdField_a_of_type_JavaNioByteBuffer.position()];
    System.arraycopy(this.jdField_a_of_type_JavaNioByteBuffer.array(), 0, arrayOfByte, 0, this.jdField_a_of_type_JavaNioByteBuffer.position());
    return arrayOfByte;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\d\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */