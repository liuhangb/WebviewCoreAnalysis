package com.tencent.beacontbs.d;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class a
{
  private String jdField_a_of_type_JavaLangString = "GBK";
  private ByteBuffer jdField_a_of_type_JavaNioByteBuffer;
  
  public a() {}
  
  public a(byte[] paramArrayOfByte)
  {
    this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
  }
  
  public a(byte[] paramArrayOfByte, byte paramByte)
  {
    this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
    this.jdField_a_of_type_JavaNioByteBuffer.position(4);
  }
  
  private double a(double paramDouble, int paramInt, boolean paramBoolean)
  {
    if (b(paramInt))
    {
      a locala = new a();
      a(locala, this.jdField_a_of_type_JavaNioByteBuffer);
      paramInt = locala.jdField_a_of_type_Byte;
      if (paramInt != 12)
      {
        switch (paramInt)
        {
        default: 
          throw new RuntimeException("type mismatch.");
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
    throw new RuntimeException("require field not exist.");
  }
  
  private float a(float paramFloat, int paramInt, boolean paramBoolean)
  {
    if (b(paramInt))
    {
      a locala = new a();
      a(locala, this.jdField_a_of_type_JavaNioByteBuffer);
      paramInt = locala.jdField_a_of_type_Byte;
      if (paramInt != 4)
      {
        if (paramInt == 12) {
          return 0.0F;
        }
        throw new RuntimeException("type mismatch.");
      }
      return this.jdField_a_of_type_JavaNioByteBuffer.getFloat();
    }
    if (!paramBoolean) {
      return paramFloat;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  private static int a(a parama, ByteBuffer paramByteBuffer)
  {
    int i = paramByteBuffer.get();
    parama.jdField_a_of_type_Byte = ((byte)(i & 0xF));
    parama.jdField_a_of_type_Int = ((i & 0xF0) >> 4);
    if (parama.jdField_a_of_type_Int == 15)
    {
      parama.jdField_a_of_type_Int = (paramByteBuffer.get() & 0xFF);
      return 2;
    }
    return 1;
  }
  
  private c a(c paramc, int paramInt, boolean paramBoolean)
  {
    if (b(paramInt)) {
      try
      {
        paramc = (c)paramc.getClass().newInstance();
        a locala = new a();
        a(locala, this.jdField_a_of_type_JavaNioByteBuffer);
        if (locala.jdField_a_of_type_Byte == 10)
        {
          paramc.a(this);
          a();
          return paramc;
        }
        throw new RuntimeException("type mismatch.");
      }
      catch (Exception paramc)
      {
        throw new RuntimeException(paramc.getMessage());
      }
    }
    if (!paramBoolean) {
      return null;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  private <K, V> Map<K, V> a(Map<K, V> paramMap1, Map<K, V> paramMap2, int paramInt, boolean paramBoolean)
  {
    if ((paramMap2 != null) && (!paramMap2.isEmpty()))
    {
      Object localObject = (Map.Entry)paramMap2.entrySet().iterator().next();
      paramMap2 = ((Map.Entry)localObject).getKey();
      localObject = ((Map.Entry)localObject).getValue();
      if (b(paramInt))
      {
        a locala = new a();
        a(locala, this.jdField_a_of_type_JavaNioByteBuffer);
        if (locala.jdField_a_of_type_Byte == 8)
        {
          int i = a(0, 0, true);
          if (i >= 0)
          {
            paramInt = 0;
            while (paramInt < i)
            {
              paramMap1.put(a(paramMap2, 0, true), a(localObject, 1, true));
              paramInt += 1;
            }
          }
          paramMap1 = new StringBuilder("size invalid: ");
          paramMap1.append(i);
          throw new RuntimeException(paramMap1.toString());
        }
        throw new RuntimeException("type mismatch.");
      }
      if (!paramBoolean) {
        return paramMap1;
      }
      throw new RuntimeException("require field not exist.");
    }
    return new HashMap();
  }
  
  private void a()
  {
    a locala = new a();
    do
    {
      a(locala, this.jdField_a_of_type_JavaNioByteBuffer);
      a(locala.jdField_a_of_type_Byte);
    } while (locala.jdField_a_of_type_Byte != 11);
  }
  
  private void a(byte paramByte)
  {
    byte b2 = 0;
    byte b1 = 0;
    switch (paramByte)
    {
    default: 
      throw new RuntimeException("invalid type.");
    case 13: 
      localObject = new a();
      a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
      if (((a)localObject).jdField_a_of_type_Byte == 0)
      {
        paramByte = a(0, 0, true);
        localObject = this.jdField_a_of_type_JavaNioByteBuffer;
        ((ByteBuffer)localObject).position(((ByteBuffer)localObject).position() + paramByte);
        return;
      }
      StringBuilder localStringBuilder = new StringBuilder("skipField with invalid type, type value: ");
      localStringBuilder.append(paramByte);
      localStringBuilder.append(", ");
      localStringBuilder.append(((a)localObject).jdField_a_of_type_Byte);
      throw new RuntimeException(localStringBuilder.toString());
    case 11: 
    case 12: 
      return;
    case 10: 
      a();
      return;
    case 9: 
      b2 = a(0, 0, true);
      paramByte = b1;
      while (paramByte < b2)
      {
        localObject = new a();
        a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
        a(((a)localObject).jdField_a_of_type_Byte);
        paramByte += 1;
      }
      return;
    case 8: 
      b1 = a(0, 0, true);
      paramByte = b2;
      while (paramByte < b1 << 1)
      {
        localObject = new a();
        a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
        a(((a)localObject).jdField_a_of_type_Byte);
        paramByte += 1;
      }
      return;
    case 7: 
      paramByte = this.jdField_a_of_type_JavaNioByteBuffer.getInt();
      localObject = this.jdField_a_of_type_JavaNioByteBuffer;
      ((ByteBuffer)localObject).position(((ByteBuffer)localObject).position() + paramByte);
      return;
    case 6: 
      b1 = this.jdField_a_of_type_JavaNioByteBuffer.get();
      paramByte = b1;
      if (b1 < 0) {
        paramByte = b1 + 256;
      }
      localObject = this.jdField_a_of_type_JavaNioByteBuffer;
      ((ByteBuffer)localObject).position(((ByteBuffer)localObject).position() + paramByte);
      return;
    case 5: 
      localObject = this.jdField_a_of_type_JavaNioByteBuffer;
      ((ByteBuffer)localObject).position(((ByteBuffer)localObject).position() + 8);
      return;
    case 4: 
      localObject = this.jdField_a_of_type_JavaNioByteBuffer;
      ((ByteBuffer)localObject).position(((ByteBuffer)localObject).position() + 4);
      return;
    case 3: 
      localObject = this.jdField_a_of_type_JavaNioByteBuffer;
      ((ByteBuffer)localObject).position(((ByteBuffer)localObject).position() + 8);
      return;
    case 2: 
      localObject = this.jdField_a_of_type_JavaNioByteBuffer;
      ((ByteBuffer)localObject).position(((ByteBuffer)localObject).position() + 4);
      return;
    case 1: 
      localObject = this.jdField_a_of_type_JavaNioByteBuffer;
      ((ByteBuffer)localObject).position(((ByteBuffer)localObject).position() + 2);
      return;
    }
    Object localObject = this.jdField_a_of_type_JavaNioByteBuffer;
    ((ByteBuffer)localObject).position(((ByteBuffer)localObject).position() + 1);
  }
  
  private double[] a(int paramInt, boolean paramBoolean)
  {
    Object localObject;
    if (b(paramInt))
    {
      localObject = new a();
      a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
      if (((a)localObject).jdField_a_of_type_Byte == 9)
      {
        int i = a(0, 0, true);
        if (i >= 0)
        {
          double[] arrayOfDouble = new double[i];
          paramInt = 0;
          for (;;)
          {
            localObject = arrayOfDouble;
            if (paramInt >= i) {
              break;
            }
            arrayOfDouble[paramInt] = a(arrayOfDouble[0], 0, true);
            paramInt += 1;
          }
        }
        localObject = new StringBuilder("size invalid: ");
        ((StringBuilder)localObject).append(i);
        throw new RuntimeException(((StringBuilder)localObject).toString());
      }
      throw new RuntimeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      localObject = null;
      return (double[])localObject;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  private float[] a(int paramInt, boolean paramBoolean)
  {
    Object localObject;
    if (b(paramInt))
    {
      localObject = new a();
      a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
      if (((a)localObject).jdField_a_of_type_Byte == 9)
      {
        int i = a(0, 0, true);
        if (i >= 0)
        {
          float[] arrayOfFloat = new float[i];
          paramInt = 0;
          for (;;)
          {
            localObject = arrayOfFloat;
            if (paramInt >= i) {
              break;
            }
            arrayOfFloat[paramInt] = a(arrayOfFloat[0], 0, true);
            paramInt += 1;
          }
        }
        localObject = new StringBuilder("size invalid: ");
        ((StringBuilder)localObject).append(i);
        throw new RuntimeException(((StringBuilder)localObject).toString());
      }
      throw new RuntimeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      localObject = null;
      return (float[])localObject;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  private int[] a(int paramInt, boolean paramBoolean)
  {
    Object localObject;
    if (b(paramInt))
    {
      localObject = new a();
      a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
      if (((a)localObject).jdField_a_of_type_Byte == 9)
      {
        int i = a(0, 0, true);
        if (i >= 0)
        {
          int[] arrayOfInt = new int[i];
          paramInt = 0;
          for (;;)
          {
            localObject = arrayOfInt;
            if (paramInt >= i) {
              break;
            }
            arrayOfInt[paramInt] = a(arrayOfInt[0], 0, true);
            paramInt += 1;
          }
        }
        localObject = new StringBuilder("size invalid: ");
        ((StringBuilder)localObject).append(i);
        throw new RuntimeException(((StringBuilder)localObject).toString());
      }
      throw new RuntimeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      localObject = null;
      return (int[])localObject;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  private long[] a(int paramInt, boolean paramBoolean)
  {
    Object localObject;
    if (b(paramInt))
    {
      localObject = new a();
      a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
      if (((a)localObject).jdField_a_of_type_Byte == 9)
      {
        int i = a(0, 0, true);
        if (i >= 0)
        {
          long[] arrayOfLong = new long[i];
          paramInt = 0;
          for (;;)
          {
            localObject = arrayOfLong;
            if (paramInt >= i) {
              break;
            }
            arrayOfLong[paramInt] = a(arrayOfLong[0], 0, true);
            paramInt += 1;
          }
        }
        localObject = new StringBuilder("size invalid: ");
        ((StringBuilder)localObject).append(i);
        throw new RuntimeException(((StringBuilder)localObject).toString());
      }
      throw new RuntimeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      localObject = null;
      return (long[])localObject;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  private <T> T[] a(T paramT, int paramInt, boolean paramBoolean)
  {
    if (b(paramInt))
    {
      Object localObject = new a();
      a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
      if (((a)localObject).jdField_a_of_type_Byte == 9)
      {
        int i = a(0, 0, true);
        if (i >= 0)
        {
          localObject = (Object[])Array.newInstance(paramT.getClass(), i);
          paramInt = 0;
          while (paramInt < i)
          {
            localObject[paramInt] = a(paramT, 0, true);
            paramInt += 1;
          }
          return (T[])localObject;
        }
        paramT = new StringBuilder("size invalid: ");
        paramT.append(i);
        throw new RuntimeException(paramT.toString());
      }
      throw new RuntimeException("type mismatch.");
    }
    if (!paramBoolean) {
      return null;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  private short[] a(int paramInt, boolean paramBoolean)
  {
    Object localObject;
    if (b(paramInt))
    {
      localObject = new a();
      a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
      if (((a)localObject).jdField_a_of_type_Byte == 9)
      {
        int i = a(0, 0, true);
        if (i >= 0)
        {
          short[] arrayOfShort = new short[i];
          paramInt = 0;
          for (;;)
          {
            localObject = arrayOfShort;
            if (paramInt >= i) {
              break;
            }
            arrayOfShort[paramInt] = a(arrayOfShort[0], 0, true);
            paramInt += 1;
          }
        }
        localObject = new StringBuilder("size invalid: ");
        ((StringBuilder)localObject).append(i);
        throw new RuntimeException(((StringBuilder)localObject).toString());
      }
      throw new RuntimeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      localObject = null;
      return (short[])localObject;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  private boolean[] a(int paramInt, boolean paramBoolean)
  {
    Object localObject;
    if (b(paramInt))
    {
      localObject = new a();
      a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
      if (((a)localObject).jdField_a_of_type_Byte == 9)
      {
        int i = a(0, 0, true);
        if (i >= 0)
        {
          boolean[] arrayOfBoolean = new boolean[i];
          paramInt = 0;
          for (;;)
          {
            localObject = arrayOfBoolean;
            if (paramInt >= i) {
              break;
            }
            if (a((byte)0, 0, true) != 0) {
              paramBoolean = true;
            } else {
              paramBoolean = false;
            }
            arrayOfBoolean[paramInt] = paramBoolean;
            paramInt += 1;
          }
        }
        localObject = new StringBuilder("size invalid: ");
        ((StringBuilder)localObject).append(i);
        throw new RuntimeException(((StringBuilder)localObject).toString());
      }
      throw new RuntimeException("type mismatch.");
    }
    if (!paramBoolean)
    {
      localObject = null;
      return (boolean[])localObject;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  private boolean b(int paramInt)
  {
    try
    {
      a locala = new a();
      for (;;)
      {
        int i = a(locala, this.jdField_a_of_type_JavaNioByteBuffer.duplicate());
        if (locala.jdField_a_of_type_Byte == 11) {
          return false;
        }
        if (paramInt <= locala.jdField_a_of_type_Int)
        {
          if (paramInt != locala.jdField_a_of_type_Int) {
            break;
          }
          return true;
        }
        this.jdField_a_of_type_JavaNioByteBuffer.position(this.jdField_a_of_type_JavaNioByteBuffer.position() + i);
        a(locala.jdField_a_of_type_Byte);
      }
      return false;
    }
    catch (BufferUnderflowException|RuntimeException localBufferUnderflowException)
    {
      return false;
    }
  }
  
  public final byte a(byte paramByte, int paramInt, boolean paramBoolean)
  {
    if (b(paramInt))
    {
      a locala = new a();
      a(locala, this.jdField_a_of_type_JavaNioByteBuffer);
      paramInt = locala.jdField_a_of_type_Byte;
      if (paramInt != 0)
      {
        if (paramInt == 12) {
          return 0;
        }
        throw new RuntimeException("type mismatch.");
      }
      return this.jdField_a_of_type_JavaNioByteBuffer.get();
    }
    if (!paramBoolean) {
      return paramByte;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  public final int a(int paramInt1, int paramInt2, boolean paramBoolean)
  {
    if (b(paramInt2))
    {
      a locala = new a();
      a(locala, this.jdField_a_of_type_JavaNioByteBuffer);
      paramInt1 = locala.jdField_a_of_type_Byte;
      if (paramInt1 != 12)
      {
        switch (paramInt1)
        {
        default: 
          throw new RuntimeException("type mismatch.");
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
    throw new RuntimeException("require field not exist.");
  }
  
  public final int a(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
    return 0;
  }
  
  public final long a(long paramLong, int paramInt, boolean paramBoolean)
  {
    if (b(paramInt))
    {
      a locala = new a();
      a(locala, this.jdField_a_of_type_JavaNioByteBuffer);
      paramInt = locala.jdField_a_of_type_Byte;
      if (paramInt != 12)
      {
        switch (paramInt)
        {
        default: 
          throw new RuntimeException("type mismatch.");
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
    throw new RuntimeException("require field not exist.");
  }
  
  public final <T> Object a(T paramT, int paramInt, boolean paramBoolean)
  {
    boolean bool2 = paramT instanceof Byte;
    int i = 0;
    boolean bool1 = false;
    if (bool2) {
      return Byte.valueOf(a((byte)0, paramInt, paramBoolean));
    }
    if ((paramT instanceof Boolean))
    {
      if (a((byte)0, paramInt, paramBoolean) != 0) {
        bool1 = true;
      }
      return Boolean.valueOf(bool1);
    }
    if ((paramT instanceof Short)) {
      return Short.valueOf(a((short)0, paramInt, paramBoolean));
    }
    if ((paramT instanceof Integer)) {
      return Integer.valueOf(a(0, paramInt, paramBoolean));
    }
    if ((paramT instanceof Long)) {
      return Long.valueOf(a(0L, paramInt, paramBoolean));
    }
    if ((paramT instanceof Float)) {
      return Float.valueOf(a(0.0F, paramInt, paramBoolean));
    }
    if ((paramT instanceof Double)) {
      return Double.valueOf(a(0.0D, paramInt, paramBoolean));
    }
    if ((paramT instanceof String)) {
      return a(paramInt, paramBoolean);
    }
    if ((paramT instanceof Map))
    {
      paramT = (Map)paramT;
      return (HashMap)a(new HashMap(), paramT, paramInt, paramBoolean);
    }
    if ((paramT instanceof List))
    {
      paramT = (List)paramT;
      if ((paramT != null) && (!paramT.isEmpty()))
      {
        paramT = a(paramT.get(0), paramInt, paramBoolean);
        if (paramT == null) {
          return null;
        }
        ArrayList localArrayList = new ArrayList();
        paramInt = i;
        while (paramInt < paramT.length)
        {
          localArrayList.add(paramT[paramInt]);
          paramInt += 1;
        }
        return localArrayList;
      }
      return new ArrayList();
    }
    if ((paramT instanceof c)) {
      return a((c)paramT, paramInt, paramBoolean);
    }
    if (paramT.getClass().isArray())
    {
      if ((!(paramT instanceof byte[])) && (!(paramT instanceof Byte[])))
      {
        if ((paramT instanceof boolean[])) {
          return a(paramInt, paramBoolean);
        }
        if ((paramT instanceof short[])) {
          return a(paramInt, paramBoolean);
        }
        if ((paramT instanceof int[])) {
          return a(paramInt, paramBoolean);
        }
        if ((paramT instanceof long[])) {
          return a(paramInt, paramBoolean);
        }
        if ((paramT instanceof float[])) {
          return a(paramInt, paramBoolean);
        }
        if ((paramT instanceof double[])) {
          return a(paramInt, paramBoolean);
        }
        paramT = (Object[])paramT;
        if ((paramT != null) && (paramT.length != 0)) {
          return a(paramT[0], paramInt, paramBoolean);
        }
        throw new RuntimeException("unable to get type of key and value.");
      }
      return a(paramInt, paramBoolean);
    }
    throw new RuntimeException("read object error: unsupport type.");
  }
  
  public final String a(int paramInt, boolean paramBoolean)
  {
    if (b(paramInt))
    {
      localObject = new a();
      a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
      switch (((a)localObject).jdField_a_of_type_Byte)
      {
      default: 
        throw new RuntimeException("type mismatch.");
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
    Object localObject = new StringBuilder("String too long: ");
    ((StringBuilder)localObject).append(paramInt);
    throw new RuntimeException(((StringBuilder)localObject).toString());
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
    throw new RuntimeException("require field not exist.");
  }
  
  public final <K, V> HashMap<K, V> a(Map<K, V> paramMap)
  {
    return (HashMap)a(new HashMap(), paramMap, 0, false);
  }
  
  public final short a(short paramShort, int paramInt, boolean paramBoolean)
  {
    if (b(paramInt))
    {
      a locala = new a();
      a(locala, this.jdField_a_of_type_JavaNioByteBuffer);
      paramInt = locala.jdField_a_of_type_Byte;
      if (paramInt != 12)
      {
        switch (paramInt)
        {
        default: 
          throw new RuntimeException("type mismatch.");
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
    throw new RuntimeException("require field not exist.");
  }
  
  public final void a(byte[] paramArrayOfByte)
  {
    this.jdField_a_of_type_JavaNioByteBuffer = ByteBuffer.wrap(paramArrayOfByte);
  }
  
  public final boolean a(int paramInt)
  {
    return a((byte)0, paramInt, true) != 0;
  }
  
  public final byte[] a(int paramInt, boolean paramBoolean)
  {
    if (b(paramInt))
    {
      Object localObject = new a();
      a((a)localObject, this.jdField_a_of_type_JavaNioByteBuffer);
      int i = ((a)localObject).jdField_a_of_type_Byte;
      if (i != 9)
      {
        if (i == 13)
        {
          a locala = new a();
          a(locala, this.jdField_a_of_type_JavaNioByteBuffer);
          if (locala.jdField_a_of_type_Byte == 0)
          {
            i = a(0, 0, true);
            if ((i >= 0) && (i <= this.jdField_a_of_type_JavaNioByteBuffer.capacity()))
            {
              localObject = new byte[i];
              this.jdField_a_of_type_JavaNioByteBuffer.get((byte[])localObject);
              return (byte[])localObject;
            }
            localStringBuilder = new StringBuilder("invalid size, tag: ");
            localStringBuilder.append(paramInt);
            localStringBuilder.append(", type: ");
            localStringBuilder.append(((a)localObject).jdField_a_of_type_Byte);
            localStringBuilder.append(", ");
            localStringBuilder.append(locala.jdField_a_of_type_Byte);
            localStringBuilder.append(", size: ");
            localStringBuilder.append(i);
            throw new RuntimeException(localStringBuilder.toString());
          }
          StringBuilder localStringBuilder = new StringBuilder("type mismatch, tag: ");
          localStringBuilder.append(paramInt);
          localStringBuilder.append(", type: ");
          localStringBuilder.append(((a)localObject).jdField_a_of_type_Byte);
          localStringBuilder.append(", ");
          localStringBuilder.append(locala.jdField_a_of_type_Byte);
          throw new RuntimeException(localStringBuilder.toString());
        }
        throw new RuntimeException("type mismatch.");
      }
      i = a(0, 0, true);
      if ((i >= 0) && (i <= this.jdField_a_of_type_JavaNioByteBuffer.capacity()))
      {
        localObject = new byte[i];
        paramInt = 0;
        while (paramInt < i)
        {
          localObject[paramInt] = a(localObject[0], 0, true);
          paramInt += 1;
        }
        return (byte[])localObject;
      }
      localObject = new StringBuilder("size invalid: ");
      ((StringBuilder)localObject).append(i);
      throw new RuntimeException(((StringBuilder)localObject).toString());
    }
    if (!paramBoolean) {
      return null;
    }
    throw new RuntimeException("require field not exist.");
  }
  
  public static final class a
  {
    public byte a;
    public int a;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\d\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */