package com.taf;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class UniAttribute
  extends a
{
  JceInputStream b = new JceInputStream();
  protected HashMap<String, byte[]> c = null;
  private HashMap<String, Object> d = new HashMap();
  
  private Object a(byte[] paramArrayOfByte, Object paramObject)
  {
    this.b.wrap(paramArrayOfByte);
    this.b.setServerEncoding(this.jdField_a_of_type_JavaLangString);
    return this.b.read(paramObject, 0, true);
  }
  
  private void a(String paramString, Object paramObject)
  {
    this.d.put(paramString, paramObject);
  }
  
  public void decode(byte[] paramArrayOfByte)
  {
    try
    {
      super.decode(paramArrayOfByte);
      return;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    this.b.wrap(paramArrayOfByte);
    this.b.setServerEncoding(this.jdField_a_of_type_JavaLangString);
    paramArrayOfByte = new HashMap(1);
    paramArrayOfByte.put("", new byte[0]);
    this.c = this.b.readMap(paramArrayOfByte, 0, false);
  }
  
  public void decodeVersion2(byte[] paramArrayOfByte)
  {
    super.decode(paramArrayOfByte);
  }
  
  public void decodeVersion3(byte[] paramArrayOfByte)
  {
    this.b.wrap(paramArrayOfByte);
    this.b.setServerEncoding(this.jdField_a_of_type_JavaLangString);
    paramArrayOfByte = new HashMap(1);
    paramArrayOfByte.put("", new byte[0]);
    this.c = this.b.readMap(paramArrayOfByte, 0, false);
  }
  
  public byte[] encode()
  {
    if (this.c != null)
    {
      JceOutputStream localJceOutputStream = JceSynchronizedPool.getInstance().acquireout();
      localJceOutputStream.setServerEncoding(this.jdField_a_of_type_JavaLangString);
      localJceOutputStream.write(this.c, 0);
      byte[] arrayOfByte = JceUtil.getJceBufArray(localJceOutputStream.getByteBuffer());
      JceSynchronizedPool.getInstance().releaseOut(localJceOutputStream);
      return arrayOfByte;
    }
    return super.encode();
  }
  
  public <T> T get(String paramString)
    throws ObjectCreateException
  {
    return (T)get(paramString, true, null);
  }
  
  public <T> T get(String paramString, Object paramObject)
  {
    return (T)get(paramString, paramObject, true, null);
  }
  
  public <T> T get(String paramString, T paramT, Object paramObject)
  {
    if (!this.c.containsKey(paramString)) {
      return (T)paramObject;
    }
    return (T)getByClass(paramString, paramT);
  }
  
  public <T> T get(String paramString, Object paramObject, boolean paramBoolean, ClassLoader paramClassLoader)
  {
    if (this.c == null) {
      return (T)super.get(paramString, paramObject, paramBoolean, paramClassLoader);
    }
    throw new RuntimeException("data is encoded by new version, please use getByClass(String name, T proxy, Object defaultValue)");
  }
  
  public <T> T get(String paramString, boolean paramBoolean, ClassLoader paramClassLoader)
    throws ObjectCreateException
  {
    if (this.c == null) {
      return (T)super.get(paramString, paramBoolean, paramClassLoader);
    }
    throw new RuntimeException("data is encoded by new version, please use getByClass(String name, T proxy)");
  }
  
  public <T> T getByClass(String paramString, T paramT)
    throws ObjectCreateException
  {
    Object localObject1 = this.c;
    if (localObject1 != null)
    {
      if (!((HashMap)localObject1).containsKey(paramString)) {
        return null;
      }
      if (this.d.containsKey(paramString)) {
        return (T)this.d.get(paramString);
      }
      localObject1 = (byte[])this.c.get(paramString);
      try
      {
        paramT = a((byte[])localObject1, paramT);
        if (paramT != null) {
          a(paramString, paramT);
        }
        return paramT;
      }
      catch (Exception paramString)
      {
        throw new ObjectCreateException(paramString);
      }
    }
    if (!this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString)) {
      return null;
    }
    if (this.d.containsKey(paramString)) {
      return (T)this.d.get(paramString);
    }
    Object localObject2 = (HashMap)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
    localObject1 = new byte[0];
    localObject2 = ((HashMap)localObject2).entrySet().iterator();
    if (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Map.Entry)((Iterator)localObject2).next();
      localObject2 = (String)((Map.Entry)localObject1).getKey();
      localObject1 = (byte[])((Map.Entry)localObject1).getValue();
    }
    try
    {
      this.b.wrap((byte[])localObject1);
      this.b.setServerEncoding(this.jdField_a_of_type_JavaLangString);
      paramT = this.b.read(paramT, 0, true);
      a(paramString, paramT);
      return paramT;
    }
    catch (Exception paramString)
    {
      throw new ObjectCreateException(paramString);
    }
  }
  
  public <T> void put(String paramString, T paramT)
  {
    if (this.c != null)
    {
      if (paramString != null)
      {
        if (paramT != null)
        {
          if (!(paramT instanceof Set))
          {
            JceOutputStream localJceOutputStream = JceSynchronizedPool.getInstance().acquireout();
            localJceOutputStream.setServerEncoding(this.jdField_a_of_type_JavaLangString);
            localJceOutputStream.write(paramT, 0);
            paramT = JceUtil.getJceBufArray(localJceOutputStream.getByteBuffer());
            JceSynchronizedPool.getInstance().releaseOut(localJceOutputStream);
            this.c.put(paramString, paramT);
            return;
          }
          throw new IllegalArgumentException("can not support Set");
        }
        throw new IllegalArgumentException("put value can not is null");
      }
      throw new IllegalArgumentException("put key can not is null");
    }
    super.put(paramString, paramT);
  }
  
  public void useVersion3()
  {
    this.c = new HashMap();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\taf\UniAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */