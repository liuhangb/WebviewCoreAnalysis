package com.taf;

import java.io.PrintStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class JceDisplayer
{
  private int jdField_a_of_type_Int = 0;
  private StringBuilder jdField_a_of_type_JavaLangStringBuilder;
  
  public JceDisplayer(StringBuilder paramStringBuilder)
  {
    this.jdField_a_of_type_JavaLangStringBuilder = paramStringBuilder;
  }
  
  public JceDisplayer(StringBuilder paramStringBuilder, int paramInt)
  {
    this.jdField_a_of_type_JavaLangStringBuilder = paramStringBuilder;
    this.jdField_a_of_type_Int = paramInt;
  }
  
  private void a(String paramString)
  {
    int i = 0;
    while (i < this.jdField_a_of_type_Int)
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append('\t');
      i += 1;
    }
    if (paramString != null)
    {
      StringBuilder localStringBuilder = this.jdField_a_of_type_JavaLangStringBuilder;
      localStringBuilder.append(paramString);
      localStringBuilder.append(": ");
    }
  }
  
  public static void main(String[] paramArrayOfString)
  {
    paramArrayOfString = new StringBuilder();
    paramArrayOfString.append(1.2D);
    System.out.println(paramArrayOfString.toString());
  }
  
  public JceDisplayer display(byte paramByte, String paramString)
  {
    a(paramString);
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramByte);
    paramString.append('\n');
    return this;
  }
  
  public JceDisplayer display(char paramChar, String paramString)
  {
    a(paramString);
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramChar);
    paramString.append('\n');
    return this;
  }
  
  public JceDisplayer display(double paramDouble, String paramString)
  {
    a(paramString);
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramDouble);
    paramString.append('\n');
    return this;
  }
  
  public JceDisplayer display(float paramFloat, String paramString)
  {
    a(paramString);
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramFloat);
    paramString.append('\n');
    return this;
  }
  
  public JceDisplayer display(int paramInt, String paramString)
  {
    a(paramString);
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramInt);
    paramString.append('\n');
    return this;
  }
  
  public JceDisplayer display(long paramLong, String paramString)
  {
    a(paramString);
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramLong);
    paramString.append('\n');
    return this;
  }
  
  public JceDisplayer display(JceStruct paramJceStruct, String paramString)
  {
    display('{', paramString);
    if (paramJceStruct == null)
    {
      paramJceStruct = this.jdField_a_of_type_JavaLangStringBuilder;
      paramJceStruct.append('\t');
      paramJceStruct.append("null");
    }
    else
    {
      paramJceStruct.display(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    }
    display('}', (String)null);
    return this;
  }
  
  public <T> JceDisplayer display(T paramT, String paramString)
  {
    if (paramT == null)
    {
      paramT = this.jdField_a_of_type_JavaLangStringBuilder;
      paramT.append("null");
      paramT.append('\n');
      return this;
    }
    if ((paramT instanceof Byte))
    {
      display(((Byte)paramT).byteValue(), paramString);
      return this;
    }
    if ((paramT instanceof Boolean))
    {
      display(((Boolean)paramT).booleanValue(), paramString);
      return this;
    }
    if ((paramT instanceof Short))
    {
      display(((Short)paramT).shortValue(), paramString);
      return this;
    }
    if ((paramT instanceof Integer))
    {
      display(((Integer)paramT).intValue(), paramString);
      return this;
    }
    if ((paramT instanceof Long))
    {
      display(((Long)paramT).longValue(), paramString);
      return this;
    }
    if ((paramT instanceof Float))
    {
      display(((Float)paramT).floatValue(), paramString);
      return this;
    }
    if ((paramT instanceof Double))
    {
      display(((Double)paramT).doubleValue(), paramString);
      return this;
    }
    if ((paramT instanceof String))
    {
      display((String)paramT, paramString);
      return this;
    }
    if ((paramT instanceof Map))
    {
      display((Map)paramT, paramString);
      return this;
    }
    if ((paramT instanceof List))
    {
      display((List)paramT, paramString);
      return this;
    }
    if ((paramT instanceof JceStruct))
    {
      display((JceStruct)paramT, paramString);
      return this;
    }
    if ((paramT instanceof byte[]))
    {
      display((byte[])paramT, paramString);
      return this;
    }
    if ((paramT instanceof boolean[]))
    {
      display((boolean[])paramT, paramString);
      return this;
    }
    if ((paramT instanceof short[]))
    {
      display((short[])paramT, paramString);
      return this;
    }
    if ((paramT instanceof int[]))
    {
      display((int[])paramT, paramString);
      return this;
    }
    if ((paramT instanceof long[]))
    {
      display((long[])paramT, paramString);
      return this;
    }
    if ((paramT instanceof float[]))
    {
      display((float[])paramT, paramString);
      return this;
    }
    if ((paramT instanceof double[]))
    {
      display((double[])paramT, paramString);
      return this;
    }
    if (paramT.getClass().isArray())
    {
      display((Object[])paramT, paramString);
      return this;
    }
    throw new JceEncodeException("write object error: unsupport type.");
  }
  
  public JceDisplayer display(String paramString1, String paramString2)
  {
    a(paramString2);
    if (paramString1 == null)
    {
      paramString1 = this.jdField_a_of_type_JavaLangStringBuilder;
      paramString1.append("null");
      paramString1.append('\n');
      return this;
    }
    paramString2 = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString2.append(paramString1);
    paramString2.append('\n');
    return this;
  }
  
  public <T> JceDisplayer display(Collection<T> paramCollection, String paramString)
  {
    if (paramCollection == null)
    {
      a(paramString);
      paramCollection = this.jdField_a_of_type_JavaLangStringBuilder;
      paramCollection.append("null");
      paramCollection.append('\t');
      return this;
    }
    return display(paramCollection.toArray(), paramString);
  }
  
  public <K, V> JceDisplayer display(Map<K, V> paramMap, String paramString)
  {
    a(paramString);
    if (paramMap == null)
    {
      paramMap = this.jdField_a_of_type_JavaLangStringBuilder;
      paramMap.append("null");
      paramMap.append('\n');
      return this;
    }
    if (paramMap.isEmpty())
    {
      paramString = this.jdField_a_of_type_JavaLangStringBuilder;
      paramString.append(paramMap.size());
      paramString.append(", {}");
      paramString.append('\n');
      return this;
    }
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramMap.size());
    paramString.append(", {");
    paramString.append('\n');
    paramString = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    JceDisplayer localJceDisplayer = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 2);
    paramMap = paramMap.entrySet().iterator();
    while (paramMap.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)paramMap.next();
      String str = (String)null;
      paramString.display('(', str);
      localJceDisplayer.display(localEntry.getKey(), str);
      localJceDisplayer.display(localEntry.getValue(), str);
      paramString.display(')', str);
    }
    display('}', (String)null);
    return this;
  }
  
  public JceDisplayer display(short paramShort, String paramString)
  {
    a(paramString);
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramShort);
    paramString.append('\n');
    return this;
  }
  
  public JceDisplayer display(boolean paramBoolean, String paramString)
  {
    a(paramString);
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    char c;
    if (paramBoolean) {
      c = 'T';
    } else {
      c = 'F';
    }
    paramString.append(c);
    paramString.append('\n');
    return this;
  }
  
  public JceDisplayer display(byte[] paramArrayOfByte, String paramString)
  {
    a(paramString);
    if (paramArrayOfByte == null)
    {
      paramArrayOfByte = this.jdField_a_of_type_JavaLangStringBuilder;
      paramArrayOfByte.append("null");
      paramArrayOfByte.append('\n');
      return this;
    }
    if (paramArrayOfByte.length == 0)
    {
      paramString = this.jdField_a_of_type_JavaLangStringBuilder;
      paramString.append(paramArrayOfByte.length);
      paramString.append(", []");
      paramString.append('\n');
      return this;
    }
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramArrayOfByte.length);
    paramString.append(", [");
    paramString.append('\n');
    paramString = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    int j = paramArrayOfByte.length;
    int i = 0;
    while (i < j)
    {
      paramString.display(paramArrayOfByte[i], (String)null);
      i += 1;
    }
    display(']', (String)null);
    return this;
  }
  
  public JceDisplayer display(char[] paramArrayOfChar, String paramString)
  {
    a(paramString);
    if (paramArrayOfChar == null)
    {
      paramArrayOfChar = this.jdField_a_of_type_JavaLangStringBuilder;
      paramArrayOfChar.append("null");
      paramArrayOfChar.append('\n');
      return this;
    }
    if (paramArrayOfChar.length == 0)
    {
      paramString = this.jdField_a_of_type_JavaLangStringBuilder;
      paramString.append(paramArrayOfChar.length);
      paramString.append(", []");
      paramString.append('\n');
      return this;
    }
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramArrayOfChar.length);
    paramString.append(", [");
    paramString.append('\n');
    paramString = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    int j = paramArrayOfChar.length;
    int i = 0;
    while (i < j)
    {
      paramString.display(paramArrayOfChar[i], (String)null);
      i += 1;
    }
    display(']', (String)null);
    return this;
  }
  
  public JceDisplayer display(double[] paramArrayOfDouble, String paramString)
  {
    a(paramString);
    if (paramArrayOfDouble == null)
    {
      paramArrayOfDouble = this.jdField_a_of_type_JavaLangStringBuilder;
      paramArrayOfDouble.append("null");
      paramArrayOfDouble.append('\n');
      return this;
    }
    if (paramArrayOfDouble.length == 0)
    {
      paramString = this.jdField_a_of_type_JavaLangStringBuilder;
      paramString.append(paramArrayOfDouble.length);
      paramString.append(", []");
      paramString.append('\n');
      return this;
    }
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramArrayOfDouble.length);
    paramString.append(", [");
    paramString.append('\n');
    paramString = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    int j = paramArrayOfDouble.length;
    int i = 0;
    while (i < j)
    {
      paramString.display(paramArrayOfDouble[i], (String)null);
      i += 1;
    }
    display(']', (String)null);
    return this;
  }
  
  public JceDisplayer display(float[] paramArrayOfFloat, String paramString)
  {
    a(paramString);
    if (paramArrayOfFloat == null)
    {
      paramArrayOfFloat = this.jdField_a_of_type_JavaLangStringBuilder;
      paramArrayOfFloat.append("null");
      paramArrayOfFloat.append('\n');
      return this;
    }
    if (paramArrayOfFloat.length == 0)
    {
      paramString = this.jdField_a_of_type_JavaLangStringBuilder;
      paramString.append(paramArrayOfFloat.length);
      paramString.append(", []");
      paramString.append('\n');
      return this;
    }
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramArrayOfFloat.length);
    paramString.append(", [");
    paramString.append('\n');
    paramString = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    int j = paramArrayOfFloat.length;
    int i = 0;
    while (i < j)
    {
      paramString.display(paramArrayOfFloat[i], (String)null);
      i += 1;
    }
    display(']', (String)null);
    return this;
  }
  
  public JceDisplayer display(int[] paramArrayOfInt, String paramString)
  {
    a(paramString);
    if (paramArrayOfInt == null)
    {
      paramArrayOfInt = this.jdField_a_of_type_JavaLangStringBuilder;
      paramArrayOfInt.append("null");
      paramArrayOfInt.append('\n');
      return this;
    }
    if (paramArrayOfInt.length == 0)
    {
      paramString = this.jdField_a_of_type_JavaLangStringBuilder;
      paramString.append(paramArrayOfInt.length);
      paramString.append(", []");
      paramString.append('\n');
      return this;
    }
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramArrayOfInt.length);
    paramString.append(", [");
    paramString.append('\n');
    paramString = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    int j = paramArrayOfInt.length;
    int i = 0;
    while (i < j)
    {
      paramString.display(paramArrayOfInt[i], (String)null);
      i += 1;
    }
    display(']', (String)null);
    return this;
  }
  
  public JceDisplayer display(long[] paramArrayOfLong, String paramString)
  {
    a(paramString);
    if (paramArrayOfLong == null)
    {
      paramArrayOfLong = this.jdField_a_of_type_JavaLangStringBuilder;
      paramArrayOfLong.append("null");
      paramArrayOfLong.append('\n');
      return this;
    }
    if (paramArrayOfLong.length == 0)
    {
      paramString = this.jdField_a_of_type_JavaLangStringBuilder;
      paramString.append(paramArrayOfLong.length);
      paramString.append(", []");
      paramString.append('\n');
      return this;
    }
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramArrayOfLong.length);
    paramString.append(", [");
    paramString.append('\n');
    paramString = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    int j = paramArrayOfLong.length;
    int i = 0;
    while (i < j)
    {
      paramString.display(paramArrayOfLong[i], (String)null);
      i += 1;
    }
    display(']', (String)null);
    return this;
  }
  
  public <T> JceDisplayer display(T[] paramArrayOfT, String paramString)
  {
    a(paramString);
    if (paramArrayOfT == null)
    {
      paramArrayOfT = this.jdField_a_of_type_JavaLangStringBuilder;
      paramArrayOfT.append("null");
      paramArrayOfT.append('\n');
      return this;
    }
    if (paramArrayOfT.length == 0)
    {
      paramString = this.jdField_a_of_type_JavaLangStringBuilder;
      paramString.append(paramArrayOfT.length);
      paramString.append(", []");
      paramString.append('\n');
      return this;
    }
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramArrayOfT.length);
    paramString.append(", [");
    paramString.append('\n');
    paramString = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    int j = paramArrayOfT.length;
    int i = 0;
    while (i < j)
    {
      paramString.display(paramArrayOfT[i], (String)null);
      i += 1;
    }
    display(']', (String)null);
    return this;
  }
  
  public JceDisplayer display(short[] paramArrayOfShort, String paramString)
  {
    a(paramString);
    if (paramArrayOfShort == null)
    {
      paramArrayOfShort = this.jdField_a_of_type_JavaLangStringBuilder;
      paramArrayOfShort.append("null");
      paramArrayOfShort.append('\n');
      return this;
    }
    if (paramArrayOfShort.length == 0)
    {
      paramString = this.jdField_a_of_type_JavaLangStringBuilder;
      paramString.append(paramArrayOfShort.length);
      paramString.append(", []");
      paramString.append('\n');
      return this;
    }
    paramString = this.jdField_a_of_type_JavaLangStringBuilder;
    paramString.append(paramArrayOfShort.length);
    paramString.append(", [");
    paramString.append('\n');
    paramString = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    int j = paramArrayOfShort.length;
    int i = 0;
    while (i < j)
    {
      paramString.display(paramArrayOfShort[i], (String)null);
      i += 1;
    }
    display(']', (String)null);
    return this;
  }
  
  public JceDisplayer displaySimple(byte paramByte, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangStringBuilder.append(paramByte);
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(char paramChar, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangStringBuilder.append(paramChar);
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(double paramDouble, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangStringBuilder.append(paramDouble);
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(float paramFloat, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangStringBuilder.append(paramFloat);
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(int paramInt, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangStringBuilder.append(paramInt);
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(long paramLong, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangStringBuilder.append(paramLong);
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(JceStruct paramJceStruct, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangStringBuilder.append("{");
    if (paramJceStruct == null)
    {
      paramJceStruct = this.jdField_a_of_type_JavaLangStringBuilder;
      paramJceStruct.append('\t');
      paramJceStruct.append("null");
    }
    else
    {
      paramJceStruct.displaySimple(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
    }
    this.jdField_a_of_type_JavaLangStringBuilder.append("}");
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public <T> JceDisplayer displaySimple(T paramT, boolean paramBoolean)
  {
    if (paramT == null)
    {
      paramT = this.jdField_a_of_type_JavaLangStringBuilder;
      paramT.append("null");
      paramT.append('\n');
      return this;
    }
    if ((paramT instanceof Byte))
    {
      displaySimple(((Byte)paramT).byteValue(), paramBoolean);
      return this;
    }
    if ((paramT instanceof Boolean))
    {
      displaySimple(((Boolean)paramT).booleanValue(), paramBoolean);
      return this;
    }
    if ((paramT instanceof Short))
    {
      displaySimple(((Short)paramT).shortValue(), paramBoolean);
      return this;
    }
    if ((paramT instanceof Integer))
    {
      displaySimple(((Integer)paramT).intValue(), paramBoolean);
      return this;
    }
    if ((paramT instanceof Long))
    {
      displaySimple(((Long)paramT).longValue(), paramBoolean);
      return this;
    }
    if ((paramT instanceof Float))
    {
      displaySimple(((Float)paramT).floatValue(), paramBoolean);
      return this;
    }
    if ((paramT instanceof Double))
    {
      displaySimple(((Double)paramT).doubleValue(), paramBoolean);
      return this;
    }
    if ((paramT instanceof String))
    {
      displaySimple((String)paramT, paramBoolean);
      return this;
    }
    if ((paramT instanceof Map))
    {
      displaySimple((Map)paramT, paramBoolean);
      return this;
    }
    if ((paramT instanceof List))
    {
      displaySimple((List)paramT, paramBoolean);
      return this;
    }
    if ((paramT instanceof JceStruct))
    {
      displaySimple((JceStruct)paramT, paramBoolean);
      return this;
    }
    if ((paramT instanceof byte[]))
    {
      displaySimple((byte[])paramT, paramBoolean);
      return this;
    }
    if ((paramT instanceof boolean[]))
    {
      displaySimple((boolean[])paramT, paramBoolean);
      return this;
    }
    if ((paramT instanceof short[]))
    {
      displaySimple((short[])paramT, paramBoolean);
      return this;
    }
    if ((paramT instanceof int[]))
    {
      displaySimple((int[])paramT, paramBoolean);
      return this;
    }
    if ((paramT instanceof long[]))
    {
      displaySimple((long[])paramT, paramBoolean);
      return this;
    }
    if ((paramT instanceof float[]))
    {
      displaySimple((float[])paramT, paramBoolean);
      return this;
    }
    if ((paramT instanceof double[]))
    {
      displaySimple((double[])paramT, paramBoolean);
      return this;
    }
    if (paramT.getClass().isArray())
    {
      displaySimple((Object[])paramT, paramBoolean);
      return this;
    }
    throw new JceEncodeException("write object error: unsupport type.");
  }
  
  public JceDisplayer displaySimple(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("null");
    } else {
      this.jdField_a_of_type_JavaLangStringBuilder.append(paramString);
    }
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public <T> JceDisplayer displaySimple(Collection<T> paramCollection, boolean paramBoolean)
  {
    if (paramCollection == null)
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append("[]");
      if (paramBoolean) {
        this.jdField_a_of_type_JavaLangStringBuilder.append("|");
      }
      return this;
    }
    return displaySimple(paramCollection.toArray(), paramBoolean);
  }
  
  public <K, V> JceDisplayer displaySimple(Map<K, V> paramMap, boolean paramBoolean)
  {
    if ((paramMap != null) && (!paramMap.isEmpty()))
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append("{");
      JceDisplayer localJceDisplayer = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 2);
      paramMap = paramMap.entrySet().iterator();
      for (int i = 1; paramMap.hasNext(); i = 0)
      {
        Map.Entry localEntry = (Map.Entry)paramMap.next();
        if (i == 0) {
          this.jdField_a_of_type_JavaLangStringBuilder.append(",");
        }
        localJceDisplayer.displaySimple(localEntry.getKey(), true);
        localJceDisplayer.displaySimple(localEntry.getValue(), false);
      }
      this.jdField_a_of_type_JavaLangStringBuilder.append("}");
      if (paramBoolean) {
        this.jdField_a_of_type_JavaLangStringBuilder.append("|");
      }
      return this;
    }
    this.jdField_a_of_type_JavaLangStringBuilder.append("{}");
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(short paramShort, boolean paramBoolean)
  {
    this.jdField_a_of_type_JavaLangStringBuilder.append(paramShort);
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(boolean paramBoolean1, boolean paramBoolean2)
  {
    StringBuilder localStringBuilder = this.jdField_a_of_type_JavaLangStringBuilder;
    char c;
    if (paramBoolean1) {
      c = 'T';
    } else {
      c = 'F';
    }
    localStringBuilder.append(c);
    if (paramBoolean2) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(byte[] paramArrayOfByte, boolean paramBoolean)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length != 0))
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append(HexUtil.bytes2HexStr(paramArrayOfByte));
      if (paramBoolean) {
        this.jdField_a_of_type_JavaLangStringBuilder.append("|");
      }
      return this;
    }
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(char[] paramArrayOfChar, boolean paramBoolean)
  {
    if ((paramArrayOfChar != null) && (paramArrayOfChar.length != 0))
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append(new String(paramArrayOfChar));
      if (paramBoolean) {
        this.jdField_a_of_type_JavaLangStringBuilder.append("|");
      }
      return this;
    }
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(double[] paramArrayOfDouble, boolean paramBoolean)
  {
    if ((paramArrayOfDouble != null) && (paramArrayOfDouble.length != 0))
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append("[");
      JceDisplayer localJceDisplayer = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
      int i = 0;
      while (i < paramArrayOfDouble.length)
      {
        double d = paramArrayOfDouble[i];
        if (i != 0) {
          this.jdField_a_of_type_JavaLangStringBuilder.append("|");
        }
        localJceDisplayer.displaySimple(d, false);
        i += 1;
      }
      this.jdField_a_of_type_JavaLangStringBuilder.append("[");
      if (paramBoolean) {
        this.jdField_a_of_type_JavaLangStringBuilder.append("|");
      }
      return this;
    }
    this.jdField_a_of_type_JavaLangStringBuilder.append("[]");
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(float[] paramArrayOfFloat, boolean paramBoolean)
  {
    if ((paramArrayOfFloat != null) && (paramArrayOfFloat.length != 0))
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append("[");
      JceDisplayer localJceDisplayer = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
      int i = 0;
      while (i < paramArrayOfFloat.length)
      {
        float f = paramArrayOfFloat[i];
        if (i != 0) {
          this.jdField_a_of_type_JavaLangStringBuilder.append("|");
        }
        localJceDisplayer.displaySimple(f, false);
        i += 1;
      }
      this.jdField_a_of_type_JavaLangStringBuilder.append("]");
      if (paramBoolean) {
        this.jdField_a_of_type_JavaLangStringBuilder.append("|");
      }
      return this;
    }
    this.jdField_a_of_type_JavaLangStringBuilder.append("[]");
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(int[] paramArrayOfInt, boolean paramBoolean)
  {
    if ((paramArrayOfInt != null) && (paramArrayOfInt.length != 0))
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append("[");
      JceDisplayer localJceDisplayer = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
      int i = 0;
      while (i < paramArrayOfInt.length)
      {
        int j = paramArrayOfInt[i];
        if (i != 0) {
          this.jdField_a_of_type_JavaLangStringBuilder.append("|");
        }
        localJceDisplayer.displaySimple(j, false);
        i += 1;
      }
      this.jdField_a_of_type_JavaLangStringBuilder.append("]");
      if (paramBoolean) {
        this.jdField_a_of_type_JavaLangStringBuilder.append("|");
      }
      return this;
    }
    this.jdField_a_of_type_JavaLangStringBuilder.append("[]");
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(long[] paramArrayOfLong, boolean paramBoolean)
  {
    if ((paramArrayOfLong != null) && (paramArrayOfLong.length != 0))
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append("[");
      JceDisplayer localJceDisplayer = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
      int i = 0;
      while (i < paramArrayOfLong.length)
      {
        long l = paramArrayOfLong[i];
        if (i != 0) {
          this.jdField_a_of_type_JavaLangStringBuilder.append("|");
        }
        localJceDisplayer.displaySimple(l, false);
        i += 1;
      }
      this.jdField_a_of_type_JavaLangStringBuilder.append("]");
      if (paramBoolean) {
        this.jdField_a_of_type_JavaLangStringBuilder.append("|");
      }
      return this;
    }
    this.jdField_a_of_type_JavaLangStringBuilder.append("[]");
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public <T> JceDisplayer displaySimple(T[] paramArrayOfT, boolean paramBoolean)
  {
    if ((paramArrayOfT != null) && (paramArrayOfT.length != 0))
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append("[");
      JceDisplayer localJceDisplayer = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
      int i = 0;
      while (i < paramArrayOfT.length)
      {
        T ? = paramArrayOfT[i];
        if (i != 0) {
          this.jdField_a_of_type_JavaLangStringBuilder.append("|");
        }
        localJceDisplayer.displaySimple(?, false);
        i += 1;
      }
      this.jdField_a_of_type_JavaLangStringBuilder.append("]");
      if (paramBoolean) {
        this.jdField_a_of_type_JavaLangStringBuilder.append("|");
      }
      return this;
    }
    this.jdField_a_of_type_JavaLangStringBuilder.append("[]");
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
  
  public JceDisplayer displaySimple(short[] paramArrayOfShort, boolean paramBoolean)
  {
    if ((paramArrayOfShort != null) && (paramArrayOfShort.length != 0))
    {
      this.jdField_a_of_type_JavaLangStringBuilder.append("[");
      JceDisplayer localJceDisplayer = new JceDisplayer(this.jdField_a_of_type_JavaLangStringBuilder, this.jdField_a_of_type_Int + 1);
      int i = 0;
      while (i < paramArrayOfShort.length)
      {
        short s = paramArrayOfShort[i];
        if (i != 0) {
          this.jdField_a_of_type_JavaLangStringBuilder.append("|");
        }
        localJceDisplayer.displaySimple(s, false);
        i += 1;
      }
      this.jdField_a_of_type_JavaLangStringBuilder.append("]");
      if (paramBoolean) {
        this.jdField_a_of_type_JavaLangStringBuilder.append("|");
      }
      return this;
    }
    this.jdField_a_of_type_JavaLangStringBuilder.append("[]");
    if (paramBoolean) {
      this.jdField_a_of_type_JavaLangStringBuilder.append("|");
    }
    return this;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\taf\JceDisplayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */