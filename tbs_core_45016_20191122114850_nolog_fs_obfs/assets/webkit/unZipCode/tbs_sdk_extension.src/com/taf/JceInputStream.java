package com.taf;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class JceInputStream
{
  protected String a;
  private ByteBuffer a;
  
  public JceInputStream()
  {
    this.jdField_a_of_type_JavaLangString = "GBK";
  }
  
  public JceInputStream(ByteBuffer paramByteBuffer)
  {
    this.jdField_a_of_type_JavaLangString = "GBK";
    this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.wrap(paramByteBuffer.array(), 0, paramByteBuffer.position());
  }
  
  public JceInputStream(byte[] paramArrayOfByte)
  {
    this.jdField_a_of_type_JavaLangString = "GBK";
    this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
  }
  
  public JceInputStream(byte[] paramArrayOfByte, int paramInt)
  {
    this.jdField_a_of_type_JavaLangString = "GBK";
    this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
    this.jdField_a_of_type_JavaNioByteBuffer.position(paramInt);
  }
  
  private int a(HeadData paramHeadData)
  {
    return readHead(paramHeadData, this.jdField_a_of_type_JavaNioByteBuffer.duplicate());
  }
  
  private <K, V> Map<K, V> a(Map<K, V> paramMap1, Map<K, V> paramMap2, int paramInt, boolean paramBoolean)
  {
    if ((paramMap2 != null) && (!paramMap2.isEmpty()))
    {
      Object localObject = (Map.Entry)paramMap2.entrySet().iterator().next();
      paramMap2 = ((Map.Entry)localObject).getKey();
      localObject = ((Map.Entry)localObject).getValue();
      if (skipToTag(paramInt))
      {
        HeadData localHeadData = new HeadData();
        readHead(localHeadData);
        if (localHeadData.type == 8)
        {
          int i = read(0, 0, true);
          if (i >= 0)
          {
            paramInt = 0;
            while (paramInt < i)
            {
              paramMap1.put(read(paramMap2, 0, true), read(localObject, 1, true));
              paramInt += 1;
            }
          }
          paramMap1 = new StringBuilder();
          paramMap1.append("size invalid: ");
          paramMap1.append(i);
          throw new JceDecodeException(paramMap1.toString());
        }
        throw new JceDecodeException("type mismatch.");
      }
      if (!paramBoolean) {
        return paramMap1;
      }
      throw new JceDecodeException("require field not exist.");
    }
    return new HashMap();
  }
  
  private void a()
  {
    HeadData localHeadData = new HeadData();
    readHead(localHeadData);
    a(localHeadData.type);
  }
  
  private void a(byte paramByte)
  {
    byte b2 = 0;
    byte b1 = 0;
    switch (paramByte)
    {
    default: 
      throw new JceDecodeException("invalid type.");
    case 13: 
      HeadData localHeadData = new HeadData();
      readHead(localHeadData);
      if (localHeadData.type == 0)
      {
        a(read(0, 0, true));
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("skipField with invalid type, type value: ");
      localStringBuilder.append(paramByte);
      localStringBuilder.append(", ");
      localStringBuilder.append(localHeadData.type);
      throw new JceDecodeException(localStringBuilder.toString());
    case 10: 
      skipToStructEnd();
      return;
    case 9: 
      b2 = read(0, 0, true);
      paramByte = b1;
    case 8: 
    case 7: 
    case 6: 
    case 5: 
    case 4: 
    case 3: 
    case 2: 
    case 1: 
    case 0: 
      while (paramByte < b2)
      {
        a();
        paramByte += 1;
        continue;
        b1 = read(0, 0, true);
        paramByte = b2;
        while (paramByte < b1 * 2)
        {
          a();
          paramByte += 1;
          continue;
          a(this.jdField_a_of_type_JavaNioByteBuffer.getInt());
          return;
          b1 = this.jdField_a_of_type_JavaNioByteBuffer.get();
          paramByte = b1;
          if (b1 < 0) {
            paramByte = b1 + 256;
          }
          a(paramByte);
          return;
          a(8);
          return;
          a(4);
          return;
          a(8);
          return;
          a(4);
          return;
          a(2);
          return;
          a(1);
        }
      }
    }
  }
  
  private void a(int paramInt)
  {
    ByteBuffer localByteBuffer = this.jdField_a_of_type_JavaNioByteBuffer;
    localByteBuffer.position(localByteBuffer.position() + paramInt);
  }
  
  private <T> T[] a(T paramT, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      Object localObject = new HeadData();
      readHead((HeadData)localObject);
      if (((HeadData)localObject).type == 9)
      {
        int i = read(0, 0, true);
        if (i >= 0)
        {
          localObject = (Object[])Array.newInstance(paramT.getClass(), i);
          paramInt = 0;
          while (paramInt < i)
          {
            localObject[paramInt] = read(paramT, 0, true);
            paramInt += 1;
          }
          return (T[])localObject;
        }
        paramT = new StringBuilder();
        paramT.append("size invalid: ");
        paramT.append(i);
        throw new JceDecodeException(paramT.toString());
      }
      throw new JceDecodeException("type mismatch.");
    }
    if (!paramBoolean) {
      return null;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public static int readHead(HeadData paramHeadData, ByteBuffer paramByteBuffer)
  {
    int i = paramByteBuffer.get();
    paramHeadData.type = ((byte)(i & 0xF));
    paramHeadData.tag = ((i & 0xF0) >> 4);
    if (paramHeadData.tag == 15)
    {
      paramHeadData.tag = (paramByteBuffer.get() & 0xFF);
      return 2;
    }
    return 1;
  }
  
  public void reInit()
  {
    this.jdField_a_of_type_JavaLangString = "GBK";
    this.jdField_a_of_type_JavaNioByteBuffer.clear();
  }
  
  public byte read(byte paramByte, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      HeadData localHeadData = new HeadData();
      readHead(localHeadData);
      paramInt = localHeadData.type;
      if (paramInt != 0)
      {
        if (paramInt == 12) {
          return 0;
        }
        throw new JceDecodeException("type mismatch.");
      }
      return this.jdField_a_of_type_JavaNioByteBuffer.get();
    }
    if (!paramBoolean) {
      return paramByte;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public double read(double paramDouble, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      HeadData localHeadData = new HeadData();
      readHead(localHeadData);
      paramInt = localHeadData.type;
      if (paramInt != 12)
      {
        switch (paramInt)
        {
        default: 
          throw new JceDecodeException("type mismatch.");
        case 5: 
          return this.jdField_a_of_type_JavaNioByteBuffer.getDouble();
        }
        return this.jdField_a_of_type_JavaNioByteBuffer.getFloat();
      }
      return 0.0D;
    }
    if (!paramBoolean) {
      return paramDouble;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public float read(float paramFloat, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      HeadData localHeadData = new HeadData();
      readHead(localHeadData);
      paramInt = localHeadData.type;
      if (paramInt != 4)
      {
        if (paramInt == 12) {
          return 0.0F;
        }
        throw new JceDecodeException("type mismatch.");
      }
      return this.jdField_a_of_type_JavaNioByteBuffer.getFloat();
    }
    if (!paramBoolean) {
      return paramFloat;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public int read(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (skipToTag(paramInt2))
    {
      HeadData localHeadData = new HeadData();
      readHead(localHeadData);
      paramInt1 = localHeadData.type;
      if (paramInt1 != 12)
      {
        switch (paramInt1)
        {
        default: 
          throw new JceDecodeException("type mismatch.");
        case 2: 
          return this.jdField_a_of_type_JavaNioByteBuffer.getInt();
        case 1: 
          return this.jdField_a_of_type_JavaNioByteBuffer.getShort();
        }
        return this.jdField_a_of_type_JavaNioByteBuffer.get();
      }
      return 0;
    }
    if (!paramBoolean) {
      return paramInt1;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public long read(long paramLong, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      HeadData localHeadData = new HeadData();
      readHead(localHeadData);
      paramInt = localHeadData.type;
      if (paramInt != 12)
      {
        switch (paramInt)
        {
        default: 
          throw new JceDecodeException("type mismatch.");
        case 3: 
          return this.jdField_a_of_type_JavaNioByteBuffer.getLong();
        case 2: 
          return this.jdField_a_of_type_JavaNioByteBuffer.getInt();
        case 1: 
          return this.jdField_a_of_type_JavaNioByteBuffer.getShort();
        }
        return this.jdField_a_of_type_JavaNioByteBuffer.get();
      }
      return 0L;
    }
    if (!paramBoolean) {
      return paramLong;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public JceStruct read(JceStruct paramJceStruct, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt)) {
      try
      {
        paramJceStruct = (JceStruct)paramJceStruct.getClass().newInstance();
        if (paramJceStruct == null) {
          return null;
        }
        HeadData localHeadData = new HeadData();
        readHead(localHeadData);
        if (localHeadData.type == 10)
        {
          paramJceStruct.readFrom(this);
          skipToStructEnd();
          return paramJceStruct;
        }
        throw new JceDecodeException("type mismatch.");
      }
      catch (Exception paramJceStruct)
      {
        throw new JceDecodeException(paramJceStruct.getMessage());
      }
    }
    if (!paramBoolean) {
      return null;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public <T> Object read(T paramT, int paramInt, boolean paramBoolean)
  {
    if ((paramT instanceof Byte)) {
      return Byte.valueOf(read((byte)0, paramInt, paramBoolean));
    }
    if ((paramT instanceof Boolean)) {
      return Boolean.valueOf(read(false, paramInt, paramBoolean));
    }
    if ((paramT instanceof Short)) {
      return Short.valueOf(read((short)0, paramInt, paramBoolean));
    }
    if ((paramT instanceof Integer)) {
      return Integer.valueOf(read(0, paramInt, paramBoolean));
    }
    if ((paramT instanceof Long)) {
      return Long.valueOf(read(0L, paramInt, paramBoolean));
    }
    if ((paramT instanceof Float)) {
      return Float.valueOf(read(0.0F, paramInt, paramBoolean));
    }
    if ((paramT instanceof Double)) {
      return Double.valueOf(read(0.0D, paramInt, paramBoolean));
    }
    if ((paramT instanceof String)) {
      return readString(paramInt, paramBoolean);
    }
    if ((paramT instanceof Map)) {
      return readMap((Map)paramT, paramInt, paramBoolean);
    }
    if ((paramT instanceof List)) {
      return readArray((List)paramT, paramInt, paramBoolean);
    }
    if ((paramT instanceof JceStruct)) {
      return read((JceStruct)paramT, paramInt, paramBoolean);
    }
    if (paramT.getClass().isArray())
    {
      if ((!(paramT instanceof byte[])) && (!(paramT instanceof Byte[])))
      {
        if ((paramT instanceof boolean[])) {
          return read((boolean[])null, paramInt, paramBoolean);
        }
        if ((paramT instanceof short[])) {
          return read((short[])null, paramInt, paramBoolean);
        }
        if ((paramT instanceof int[])) {
          return read((int[])null, paramInt, paramBoolean);
        }
        if ((paramT instanceof long[])) {
          return read((long[])null, paramInt, paramBoolean);
        }
        if ((paramT instanceof float[])) {
          return read((float[])null, paramInt, paramBoolean);
        }
        if ((paramT instanceof double[])) {
          return read((double[])null, paramInt, paramBoolean);
        }
        return readArray((Object[])paramT, paramInt, paramBoolean);
      }
      return read((byte[])null, paramInt, paramBoolean);
    }
    throw new JceDecodeException("read object error: unsupport type.");
  }
  
  public String read(String paramString, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      paramString = new HeadData();
      readHead(paramString);
      switch (paramString.type)
      {
      default: 
        throw new JceDecodeException("type mismatch.");
      case 7: 
        paramInt = this.jdField_a_of_type_JavaNioByteBuffer.getInt();
        if ((paramInt <= 104857600) && (paramInt >= 0) && (paramInt <= this.jdField_a_of_type_JavaNioByteBuffer.capacity()))
        {
          arrayOfByte = new byte[paramInt];
          this.jdField_a_of_type_JavaNioByteBuffer.get(arrayOfByte);
        }
        break;
      }
    }
    try
    {
      paramString = new String(arrayOfByte, this.jdField_a_of_type_JavaLangString);
    }
    catch (UnsupportedEncodingException paramString)
    {
      int i;
      for (;;) {}
    }
    paramString = new String(arrayOfByte);
    return paramString;
    paramString = new StringBuilder();
    paramString.append("String too long: ");
    paramString.append(paramInt);
    throw new JceDecodeException(paramString.toString());
    i = this.jdField_a_of_type_JavaNioByteBuffer.get();
    paramInt = i;
    if (i < 0) {
      paramInt = i + 256;
    }
    byte[] arrayOfByte = new byte[paramInt];
    this.jdField_a_of_type_JavaNioByteBuffer.get(arrayOfByte);
    try
    {
      paramString = new String(arrayOfByte, this.jdField_a_of_type_JavaLangString);
    }
    catch (UnsupportedEncodingException paramString)
    {
      for (;;) {}
    }
    paramString = new String(arrayOfByte);
    return paramString;
    if (!paramBoolean) {
      return paramString;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public short read(short paramShort, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      HeadData localHeadData = new HeadData();
      readHead(localHeadData);
      paramInt = localHeadData.type;
      if (paramInt != 12)
      {
        switch (paramInt)
        {
        default: 
          throw new JceDecodeException("type mismatch.");
        case 1: 
          return this.jdField_a_of_type_JavaNioByteBuffer.getShort();
        }
        return (short)this.jdField_a_of_type_JavaNioByteBuffer.get();
      }
      return 0;
    }
    if (!paramBoolean) {
      return paramShort;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public boolean read(boolean paramBoolean1, int paramInt, boolean paramBoolean2)
  {
    paramBoolean1 = false;
    if (read((byte)0, paramInt, paramBoolean2) != 0) {
      paramBoolean1 = true;
    }
    return paramBoolean1;
  }
  
  public byte[] read(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      paramArrayOfByte = new HeadData();
      readHead(paramArrayOfByte);
      int i = paramArrayOfByte.type;
      if (i != 9)
      {
        if (i == 13)
        {
          HeadData localHeadData = new HeadData();
          readHead(localHeadData);
          if (localHeadData.type == 0)
          {
            i = read(0, 0, true);
            if ((i >= 0) && (i <= this.jdField_a_of_type_JavaNioByteBuffer.capacity()))
            {
              paramArrayOfByte = new byte[i];
              this.jdField_a_of_type_JavaNioByteBuffer.get(paramArrayOfByte);
              return paramArrayOfByte;
            }
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("invalid size, tag: ");
            localStringBuilder.append(paramInt);
            localStringBuilder.append(", type: ");
            localStringBuilder.append(paramArrayOfByte.type);
            localStringBuilder.append(", ");
            localStringBuilder.append(localHeadData.type);
            localStringBuilder.append(", size: ");
            localStringBuilder.append(i);
            throw new JceDecodeException(localStringBuilder.toString());
          }
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("type mismatch, tag: ");
          localStringBuilder.append(paramInt);
          localStringBuilder.append(", type: ");
          localStringBuilder.append(paramArrayOfByte.type);
          localStringBuilder.append(", ");
          localStringBuilder.append(localHeadData.type);
          throw new JceDecodeException(localStringBuilder.toString());
        }
        throw new JceDecodeException("type mismatch.");
      }
      i = read(0, 0, true);
      if ((i >= 0) && (i <= this.jdField_a_of_type_JavaNioByteBuffer.capacity()))
      {
        paramArrayOfByte = new byte[i];
        paramInt = 0;
        while (paramInt < i)
        {
          paramArrayOfByte[paramInt] = read(paramArrayOfByte[0], 0, true);
          paramInt += 1;
        }
        return paramArrayOfByte;
      }
      paramArrayOfByte = new StringBuilder();
      paramArrayOfByte.append("size invalid: ");
      paramArrayOfByte.append(i);
      throw new JceDecodeException(paramArrayOfByte.toString());
    }
    if (!paramBoolean) {
      return null;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public double[] read(double[] paramArrayOfDouble, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      paramArrayOfDouble = new HeadData();
      readHead(paramArrayOfDouble);
      if (paramArrayOfDouble.type == 9)
      {
        int i = read(0, 0, true);
        if (i >= 0)
        {
          double[] arrayOfDouble = new double[i];
          paramInt = 0;
          for (;;)
          {
            paramArrayOfDouble = arrayOfDouble;
            if (paramInt >= i) {
              break;
            }
            arrayOfDouble[paramInt] = read(arrayOfDouble[0], 0, true);
            paramInt += 1;
          }
        }
        paramArrayOfDouble = new StringBuilder();
        paramArrayOfDouble.append("size invalid: ");
        paramArrayOfDouble.append(i);
        throw new JceDecodeException(paramArrayOfDouble.toString());
      }
      throw new JceDecodeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      paramArrayOfDouble = null;
      return paramArrayOfDouble;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public float[] read(float[] paramArrayOfFloat, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      paramArrayOfFloat = new HeadData();
      readHead(paramArrayOfFloat);
      if (paramArrayOfFloat.type == 9)
      {
        int i = read(0, 0, true);
        if (i >= 0)
        {
          float[] arrayOfFloat = new float[i];
          paramInt = 0;
          for (;;)
          {
            paramArrayOfFloat = arrayOfFloat;
            if (paramInt >= i) {
              break;
            }
            arrayOfFloat[paramInt] = read(arrayOfFloat[0], 0, true);
            paramInt += 1;
          }
        }
        paramArrayOfFloat = new StringBuilder();
        paramArrayOfFloat.append("size invalid: ");
        paramArrayOfFloat.append(i);
        throw new JceDecodeException(paramArrayOfFloat.toString());
      }
      throw new JceDecodeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      paramArrayOfFloat = null;
      return paramArrayOfFloat;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public int[] read(int[] paramArrayOfInt, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      paramArrayOfInt = new HeadData();
      readHead(paramArrayOfInt);
      if (paramArrayOfInt.type == 9)
      {
        int i = read(0, 0, true);
        if (i >= 0)
        {
          int[] arrayOfInt = new int[i];
          paramInt = 0;
          for (;;)
          {
            paramArrayOfInt = arrayOfInt;
            if (paramInt >= i) {
              break;
            }
            arrayOfInt[paramInt] = read(arrayOfInt[0], 0, true);
            paramInt += 1;
          }
        }
        paramArrayOfInt = new StringBuilder();
        paramArrayOfInt.append("size invalid: ");
        paramArrayOfInt.append(i);
        throw new JceDecodeException(paramArrayOfInt.toString());
      }
      throw new JceDecodeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      paramArrayOfInt = null;
      return paramArrayOfInt;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public long[] read(long[] paramArrayOfLong, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      paramArrayOfLong = new HeadData();
      readHead(paramArrayOfLong);
      if (paramArrayOfLong.type == 9)
      {
        int i = read(0, 0, true);
        if (i >= 0)
        {
          long[] arrayOfLong = new long[i];
          paramInt = 0;
          for (;;)
          {
            paramArrayOfLong = arrayOfLong;
            if (paramInt >= i) {
              break;
            }
            arrayOfLong[paramInt] = read(arrayOfLong[0], 0, true);
            paramInt += 1;
          }
        }
        paramArrayOfLong = new StringBuilder();
        paramArrayOfLong.append("size invalid: ");
        paramArrayOfLong.append(i);
        throw new JceDecodeException(paramArrayOfLong.toString());
      }
      throw new JceDecodeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      paramArrayOfLong = null;
      return paramArrayOfLong;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public JceStruct[] read(JceStruct[] paramArrayOfJceStruct, int paramInt, boolean paramBoolean)
  {
    return (JceStruct[])readArray(paramArrayOfJceStruct, paramInt, paramBoolean);
  }
  
  public String[] read(String[] paramArrayOfString, int paramInt, boolean paramBoolean)
  {
    return (String[])readArray(paramArrayOfString, paramInt, paramBoolean);
  }
  
  public short[] read(short[] paramArrayOfShort, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      paramArrayOfShort = new HeadData();
      readHead(paramArrayOfShort);
      if (paramArrayOfShort.type == 9)
      {
        int i = read(0, 0, true);
        if (i >= 0)
        {
          short[] arrayOfShort = new short[i];
          paramInt = 0;
          for (;;)
          {
            paramArrayOfShort = arrayOfShort;
            if (paramInt >= i) {
              break;
            }
            arrayOfShort[paramInt] = read(arrayOfShort[0], 0, true);
            paramInt += 1;
          }
        }
        paramArrayOfShort = new StringBuilder();
        paramArrayOfShort.append("size invalid: ");
        paramArrayOfShort.append(i);
        throw new JceDecodeException(paramArrayOfShort.toString());
      }
      throw new JceDecodeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      paramArrayOfShort = null;
      return paramArrayOfShort;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public boolean[] read(boolean[] paramArrayOfBoolean, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      paramArrayOfBoolean = new HeadData();
      readHead(paramArrayOfBoolean);
      if (paramArrayOfBoolean.type == 9)
      {
        int i = read(0, 0, true);
        if (i >= 0)
        {
          boolean[] arrayOfBoolean = new boolean[i];
          paramInt = 0;
          for (;;)
          {
            paramArrayOfBoolean = arrayOfBoolean;
            if (paramInt >= i) {
              break;
            }
            arrayOfBoolean[paramInt] = read(arrayOfBoolean[0], 0, true);
            paramInt += 1;
          }
        }
        paramArrayOfBoolean = new StringBuilder();
        paramArrayOfBoolean.append("size invalid: ");
        paramArrayOfBoolean.append(i);
        throw new JceDecodeException(paramArrayOfBoolean.toString());
      }
      throw new JceDecodeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      paramArrayOfBoolean = null;
      return paramArrayOfBoolean;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public <T> List<T> readArray(List<T> paramList, int paramInt, boolean paramBoolean)
  {
    if ((paramList != null) && (!paramList.isEmpty()))
    {
      int i = 0;
      paramList = a(paramList.get(0), paramInt, paramBoolean);
      if (paramList == null) {
        return null;
      }
      ArrayList localArrayList = new ArrayList();
      paramInt = i;
      while (paramInt < paramList.length)
      {
        localArrayList.add(paramList[paramInt]);
        paramInt += 1;
      }
      return localArrayList;
    }
    return new ArrayList();
  }
  
  public <T> T[] readArray(T[] paramArrayOfT, int paramInt, boolean paramBoolean)
  {
    if ((paramArrayOfT != null) && (paramArrayOfT.length != 0)) {
      return a(paramArrayOfT[0], paramInt, paramBoolean);
    }
    throw new JceDecodeException("unable to get type of key and value.");
  }
  
  public String readByteString(String paramString, int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      paramString = new HeadData();
      readHead(paramString);
      switch (paramString.type)
      {
      default: 
        throw new JceDecodeException("type mismatch.");
      case 7: 
        paramInt = this.jdField_a_of_type_JavaNioByteBuffer.getInt();
        if ((paramInt <= 104857600) && (paramInt >= 0) && (paramInt <= this.jdField_a_of_type_JavaNioByteBuffer.capacity()))
        {
          paramString = new byte[paramInt];
          this.jdField_a_of_type_JavaNioByteBuffer.get(paramString);
          return HexUtil.bytes2HexStr(paramString);
        }
        paramString = new StringBuilder();
        paramString.append("String too long: ");
        paramString.append(paramInt);
        throw new JceDecodeException(paramString.toString());
      }
      int i = this.jdField_a_of_type_JavaNioByteBuffer.get();
      paramInt = i;
      if (i < 0) {
        paramInt = i + 256;
      }
      paramString = new byte[paramInt];
      this.jdField_a_of_type_JavaNioByteBuffer.get(paramString);
      return HexUtil.bytes2HexStr(paramString);
    }
    if (!paramBoolean) {
      return paramString;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public void readHead(HeadData paramHeadData)
  {
    readHead(paramHeadData, this.jdField_a_of_type_JavaNioByteBuffer);
  }
  
  public List readList(int paramInt, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    StringBuilder localStringBuilder;
    if (skipToTag(paramInt))
    {
      Object localObject = new HeadData();
      readHead((HeadData)localObject);
      if (((HeadData)localObject).type == 9)
      {
        int k = read(0, 0, true);
        if (k >= 0)
        {
          paramInt = 0;
          while (paramInt < k)
          {
            localObject = new HeadData();
            readHead((HeadData)localObject);
            switch (((HeadData)localObject).type)
            {
            case 11: 
            default: 
              throw new JceDecodeException("type mismatch.");
            case 12: 
              localArrayList.add(new Integer(0));
              break;
            case 10: 
              try
              {
                localObject = (JceStruct)Class.forName(JceStruct.class.getName()).getConstructor(new Class[0]).newInstance(new Object[0]);
                ((JceStruct)localObject).readFrom(this);
                skipToStructEnd();
                localArrayList.add(localObject);
              }
              catch (Exception localException)
              {
                localException.printStackTrace();
                localObject = new StringBuilder();
                ((StringBuilder)localObject).append("type mismatch.");
                ((StringBuilder)localObject).append(localException);
                throw new JceDecodeException(((StringBuilder)localObject).toString());
              }
            case 7: 
              a(this.jdField_a_of_type_JavaNioByteBuffer.getInt());
              break;
            case 6: 
              int j = this.jdField_a_of_type_JavaNioByteBuffer.get();
              int i = j;
              if (j < 0) {
                i = j + 256;
              }
              a(i);
              break;
            case 5: 
              a(8);
              break;
            case 4: 
              a(4);
              break;
            case 3: 
              a(8);
              break;
            case 2: 
              a(4);
              break;
            case 1: 
              a(2);
              break;
            case 0: 
              a(1);
            }
            paramInt += 1;
          }
        }
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("size invalid: ");
        localStringBuilder.append(k);
        throw new JceDecodeException(localStringBuilder.toString());
      }
      throw new JceDecodeException("type mismatch.");
    }
    if (!paramBoolean) {
      return localStringBuilder;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public <K, V> HashMap<K, V> readMap(Map<K, V> paramMap, int paramInt, boolean paramBoolean)
  {
    return (HashMap)a(new HashMap(), paramMap, paramInt, paramBoolean);
  }
  
  public String readString(int paramInt, boolean paramBoolean)
  {
    if (skipToTag(paramInt))
    {
      localObject = new HeadData();
      readHead((HeadData)localObject);
      switch (((HeadData)localObject).type)
      {
      default: 
        throw new JceDecodeException("type mismatch.");
      case 7: 
        paramInt = this.jdField_a_of_type_JavaNioByteBuffer.getInt();
        if ((paramInt <= 104857600) && (paramInt >= 0) && (paramInt <= this.jdField_a_of_type_JavaNioByteBuffer.capacity()))
        {
          localObject = new byte[paramInt];
          this.jdField_a_of_type_JavaNioByteBuffer.get((byte[])localObject);
        }
        break;
      }
    }
    try
    {
      str = new String((byte[])localObject, this.jdField_a_of_type_JavaLangString);
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException1)
    {
      String str;
      int i;
      for (;;) {}
    }
    return new String((byte[])localObject);
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("String too long: ");
    ((StringBuilder)localObject).append(paramInt);
    throw new JceDecodeException(((StringBuilder)localObject).toString());
    i = this.jdField_a_of_type_JavaNioByteBuffer.get();
    paramInt = i;
    if (i < 0) {
      paramInt = i + 256;
    }
    localObject = new byte[paramInt];
    this.jdField_a_of_type_JavaNioByteBuffer.get((byte[])localObject);
    try
    {
      str = new String((byte[])localObject, this.jdField_a_of_type_JavaLangString);
      return str;
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException2)
    {
      for (;;) {}
    }
    return new String((byte[])localObject);
    if (!paramBoolean) {
      return null;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public Map<String, String> readStringMap(int paramInt, boolean paramBoolean)
  {
    Object localObject = new HashMap();
    if (skipToTag(paramInt))
    {
      HeadData localHeadData = new HeadData();
      readHead(localHeadData);
      if (localHeadData.type == 8)
      {
        int i = read(0, 0, true);
        if (i >= 0)
        {
          paramInt = 0;
          while (paramInt < i)
          {
            ((HashMap)localObject).put(readString(0, true), readString(1, true));
            paramInt += 1;
          }
        }
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("size invalid: ");
        ((StringBuilder)localObject).append(i);
        throw new JceDecodeException(((StringBuilder)localObject).toString());
      }
      throw new JceDecodeException("type mismatch.");
    }
    if (!paramBoolean) {
      return (Map<String, String>)localObject;
    }
    throw new JceDecodeException("require field not exist.");
  }
  
  public int setServerEncoding(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    return 0;
  }
  
  public void skipToStructEnd()
  {
    HeadData localHeadData = new HeadData();
    do
    {
      readHead(localHeadData);
      a(localHeadData.type);
    } while (localHeadData.type != 11);
  }
  
  public boolean skipToTag(int paramInt)
  {
    boolean bool = false;
    try
    {
      HeadData localHeadData = new HeadData();
      for (;;)
      {
        i = a(localHeadData);
        if ((paramInt <= localHeadData.tag) || (localHeadData.type == 11)) {
          break;
        }
        a(i);
        a(localHeadData.type);
      }
      if (localHeadData.type == 11) {
        return false;
      }
      int i = localHeadData.tag;
      if (paramInt == i) {
        bool = true;
      }
      return bool;
    }
    catch (JceDecodeException|BufferUnderflowException localJceDecodeException) {}
    return false;
  }
  
  public void warp(byte[] paramArrayOfByte)
  {
    wrap(paramArrayOfByte);
  }
  
  public void wrap(byte[] paramArrayOfByte)
  {
    this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
  }
  
  public static class HeadData
  {
    public int tag;
    public byte type;
    
    public void clear()
    {
      this.type = 0;
      this.tag = 0;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\taf\JceInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */