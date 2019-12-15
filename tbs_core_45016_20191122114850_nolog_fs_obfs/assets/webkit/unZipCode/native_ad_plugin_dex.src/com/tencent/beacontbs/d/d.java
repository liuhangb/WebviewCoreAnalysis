package com.tencent.beacontbs.d;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;

public final class d
{
  private static HashMap<String, byte[]> b;
  private a jdField_a_of_type_ComTencentBeacontbsDA = new a();
  public final e a;
  private String jdField_a_of_type_JavaLangString = "GBK";
  private HashMap<String, byte[]> jdField_a_of_type_JavaUtilHashMap = new HashMap();
  
  public d()
  {
    this.jdField_a_of_type_ComTencentBeacontbsDE = new e();
  }
  
  public final <T> T a(String paramString, T paramT)
    throws Exception
  {
    if (!this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString)) {
      return null;
    }
    paramString = (byte[])this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
    try
    {
      this.jdField_a_of_type_ComTencentBeacontbsDA.a(paramString);
      this.jdField_a_of_type_ComTencentBeacontbsDA.a(this.jdField_a_of_type_JavaLangString);
      paramString = this.jdField_a_of_type_ComTencentBeacontbsDA.a(paramT, 0, true);
      return paramString;
    }
    catch (Exception paramString)
    {
      throw new Exception(paramString);
    }
  }
  
  public final <T> void a(String paramString, T paramT)
  {
    if (paramT != null)
    {
      if (!(paramT instanceof Set))
      {
        Object localObject = new b();
        ((b)localObject).a(this.jdField_a_of_type_JavaLangString);
        ((b)localObject).a(paramT, 0);
        paramT = ((b)localObject).a();
        localObject = new byte[paramT.position()];
        System.arraycopy(paramT.array(), 0, localObject, 0, localObject.length);
        this.jdField_a_of_type_JavaUtilHashMap.put(paramString, localObject);
        return;
      }
      throw new IllegalArgumentException("can not support Set");
    }
    throw new IllegalArgumentException("put value can not is null");
  }
  
  public final void a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length >= 4) {
      try
      {
        paramArrayOfByte = new a(paramArrayOfByte, (byte)0);
        paramArrayOfByte.a(this.jdField_a_of_type_JavaLangString);
        this.jdField_a_of_type_ComTencentBeacontbsDE.a(paramArrayOfByte);
        paramArrayOfByte = new a(this.jdField_a_of_type_ComTencentBeacontbsDE.jdField_a_of_type_ArrayOfByte);
        paramArrayOfByte.a(this.jdField_a_of_type_JavaLangString);
        if (b == null)
        {
          HashMap localHashMap = new HashMap();
          b = localHashMap;
          localHashMap.put("", new byte[0]);
        }
        this.jdField_a_of_type_JavaUtilHashMap = paramArrayOfByte.a(b);
        return;
      }
      catch (Exception paramArrayOfByte)
      {
        throw new RuntimeException(paramArrayOfByte);
      }
    }
    throw new IllegalArgumentException("decode package must include size head");
  }
  
  public final byte[] a()
  {
    Object localObject2 = new b(0);
    ((b)localObject2).a(this.jdField_a_of_type_JavaLangString);
    ((b)localObject2).a(this.jdField_a_of_type_JavaUtilHashMap, 0);
    Object localObject1 = this.jdField_a_of_type_ComTencentBeacontbsDE;
    ((e)localObject1).jdField_a_of_type_Short = 3;
    localObject2 = ((b)localObject2).a();
    byte[] arrayOfByte = new byte[((ByteBuffer)localObject2).position()];
    System.arraycopy(((ByteBuffer)localObject2).array(), 0, arrayOfByte, 0, arrayOfByte.length);
    ((e)localObject1).jdField_a_of_type_ArrayOfByte = arrayOfByte;
    localObject1 = new b(0);
    ((b)localObject1).a(this.jdField_a_of_type_JavaLangString);
    this.jdField_a_of_type_ComTencentBeacontbsDE.a((b)localObject1);
    localObject2 = ((b)localObject1).a();
    localObject1 = new byte[((ByteBuffer)localObject2).position()];
    System.arraycopy(((ByteBuffer)localObject2).array(), 0, localObject1, 0, localObject1.length);
    int i = localObject1.length + 4;
    localObject2 = ByteBuffer.allocate(i);
    ((ByteBuffer)localObject2).putInt(i).put((byte[])localObject1).flip();
    return ((ByteBuffer)localObject2).array();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\beacontbs\d\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */