package com.taf;

import android.text.TextUtils;
import android.util.Pair;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class a
{
  private static final List<String> jdField_a_of_type_JavaUtilList = new ArrayList();
  JceInputStream jdField_a_of_type_ComTafJceInputStream = new JceInputStream();
  protected String a;
  protected HashMap<String, HashMap<String, byte[]>> a;
  private String b;
  protected HashMap<String, Object> b;
  private HashMap<String, Object> c = new HashMap();
  
  static
  {
    jdField_a_of_type_JavaUtilList.add("MTTQBGC");
    jdField_a_of_type_JavaUtilList.add("MTTGP");
    jdField_a_of_type_JavaUtilList.add("MTTUI");
    jdField_a_of_type_JavaUtilList.add("TIRI");
    jdField_a_of_type_JavaUtilList.add("circle");
    jdField_a_of_type_JavaUtilList.add("QB");
    jdField_a_of_type_JavaUtilList.add("LBSAddrProtocol");
    jdField_a_of_type_JavaUtilList.add("Live");
    jdField_a_of_type_JavaUtilList.add("AppMarket");
    jdField_a_of_type_JavaUtilList.add("SmartService4TrainTicket");
    jdField_a_of_type_JavaUtilList.add("SmartService4Taxi");
    jdField_a_of_type_JavaUtilList.add("SmartService4Express");
    jdField_a_of_type_JavaUtilList.add("SmartService4Flight");
    jdField_a_of_type_JavaUtilList.add("SmartService4POI");
    jdField_a_of_type_JavaUtilList.add("SmartService");
    jdField_a_of_type_JavaUtilList.add("SmartAssistant");
    jdField_a_of_type_JavaUtilList.add("ComicCircle");
    jdField_a_of_type_JavaUtilList.add("Explore");
    jdField_a_of_type_JavaUtilList.add("MTT");
    jdField_a_of_type_JavaUtilList.add("AI");
    jdField_a_of_type_JavaUtilList.add("UGCVideo");
    jdField_a_of_type_JavaUtilList.add("FCG");
  }
  
  a()
  {
    this.jdField_a_of_type_JavaUtilHashMap = new HashMap();
    this.jdField_b_of_type_JavaUtilHashMap = new HashMap();
    this.jdField_a_of_type_JavaLangString = "GBK";
    this.jdField_b_of_type_JavaLangString = "";
  }
  
  private Object a(String paramString, boolean paramBoolean, ClassLoader paramClassLoader)
  {
    if (this.jdField_b_of_type_JavaUtilHashMap.containsKey(paramString)) {
      return this.jdField_b_of_type_JavaUtilHashMap.get(paramString);
    }
    paramClassLoader = BasicClassTypeUtil.createClassByUni(paramString, paramBoolean, paramClassLoader);
    this.jdField_b_of_type_JavaUtilHashMap.put(paramString, paramClassLoader);
    return paramClassLoader;
  }
  
  private void a(String paramString, Object paramObject)
  {
    this.c.put(paramString, paramObject);
  }
  
  private void a(ArrayList<String> paramArrayList, Object paramObject)
  {
    if (paramObject.getClass().isArray())
    {
      if (paramObject.getClass().getComponentType().toString().equals("byte"))
      {
        if (Array.getLength(paramObject) > 0)
        {
          paramArrayList.add("java.util.List");
          a(paramArrayList, Array.get(paramObject, 0));
          return;
        }
        paramArrayList.add("Array");
        paramArrayList.add("?");
        return;
      }
      throw new IllegalArgumentException("only byte[] is supported");
    }
    if (!(paramObject instanceof Array))
    {
      if ((paramObject instanceof List))
      {
        paramArrayList.add("java.util.List");
        paramObject = (List)paramObject;
        if (((List)paramObject).size() > 0)
        {
          a(paramArrayList, ((List)paramObject).get(0));
          return;
        }
        paramArrayList.add("?");
        return;
      }
      if ((paramObject instanceof Map))
      {
        paramArrayList.add("java.util.Map");
        Object localObject = (Map)paramObject;
        if (((Map)localObject).size() > 0)
        {
          paramObject = ((Map)localObject).keySet().iterator().next();
          localObject = ((Map)localObject).get(paramObject);
          paramArrayList.add(paramObject.getClass().getName());
          a(paramArrayList, localObject);
          return;
        }
        paramArrayList.add("?");
        paramArrayList.add("?");
        return;
      }
      paramArrayList.add(paramObject.getClass().getName());
      return;
    }
    throw new IllegalArgumentException("can not support Array, please use List");
  }
  
  public void decode(byte[] paramArrayOfByte)
  {
    this.jdField_a_of_type_ComTafJceInputStream.wrap(paramArrayOfByte);
    this.jdField_a_of_type_ComTafJceInputStream.setServerEncoding(this.jdField_a_of_type_JavaLangString);
    paramArrayOfByte = new HashMap(1);
    HashMap localHashMap = new HashMap(1);
    localHashMap.put("", new byte[0]);
    paramArrayOfByte.put("", localHashMap);
    this.jdField_a_of_type_JavaUtilHashMap = this.jdField_a_of_type_ComTafJceInputStream.readMap(paramArrayOfByte, 0, false);
  }
  
  public byte[] encode()
  {
    JceOutputStream localJceOutputStream = JceSynchronizedPool.getInstance().acquireout();
    localJceOutputStream.setServerEncoding(this.jdField_a_of_type_JavaLangString);
    localJceOutputStream.write(this.jdField_a_of_type_JavaUtilHashMap, 0);
    byte[] arrayOfByte = JceUtil.getJceBufArray(localJceOutputStream.getByteBuffer());
    JceSynchronizedPool.getInstance().releaseOut(localJceOutputStream);
    return arrayOfByte;
  }
  
  public <T> T get(String paramString, Object paramObject, boolean paramBoolean, ClassLoader paramClassLoader)
  {
    if (!this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString)) {
      return (T)paramObject;
    }
    if (this.c.containsKey(paramString)) {
      return (T)this.c.get(paramString);
    }
    Object localObject2 = (HashMap)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
    String str = "";
    Object localObject1 = new byte[0];
    localObject2 = ((HashMap)localObject2).entrySet().iterator();
    if (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Map.Entry)((Iterator)localObject2).next();
      str = (String)((Map.Entry)localObject1).getKey();
      localObject1 = (byte[])((Map.Entry)localObject1).getValue();
    }
    localObject2 = str;
    try
    {
      if (!TextUtils.isEmpty(this.jdField_b_of_type_JavaLangString))
      {
        Iterator localIterator = jdField_a_of_type_JavaUtilList.iterator();
        for (;;)
        {
          localObject2 = str;
          if (!localIterator.hasNext()) {
            break;
          }
          localObject2 = (String)localIterator.next();
          if (str.startsWith((String)localObject2))
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(this.jdField_b_of_type_JavaLangString);
            localStringBuilder.append(str.substring(((String)localObject2).length()));
            str = localStringBuilder.toString();
          }
        }
      }
      paramClassLoader = a((String)localObject2, paramBoolean, paramClassLoader);
      this.jdField_a_of_type_ComTafJceInputStream.wrap((byte[])localObject1);
      this.jdField_a_of_type_ComTafJceInputStream.setServerEncoding(this.jdField_a_of_type_JavaLangString);
      paramClassLoader = this.jdField_a_of_type_ComTafJceInputStream.read(paramClassLoader, 0, true);
      a(paramString, paramClassLoader);
      return paramClassLoader;
    }
    catch (Exception paramClassLoader)
    {
      paramClassLoader.printStackTrace();
      a(paramString, paramObject);
    }
    return (T)paramObject;
  }
  
  public <T> T get(String paramString, boolean paramBoolean, ClassLoader paramClassLoader)
    throws ObjectCreateException
  {
    boolean bool = this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString);
    String str = null;
    if (!bool) {
      return null;
    }
    if (this.c.containsKey(paramString)) {
      return (T)this.c.get(paramString);
    }
    Object localObject2 = (HashMap)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
    Object localObject1 = new byte[0];
    localObject2 = ((HashMap)localObject2).entrySet().iterator();
    if (((Iterator)localObject2).hasNext())
    {
      localObject1 = (Map.Entry)((Iterator)localObject2).next();
      str = (String)((Map.Entry)localObject1).getKey();
      localObject1 = (byte[])((Map.Entry)localObject1).getValue();
    }
    localObject2 = str;
    try
    {
      if (!TextUtils.isEmpty(this.jdField_b_of_type_JavaLangString))
      {
        Object localObject3 = jdField_a_of_type_JavaUtilList.iterator();
        do
        {
          localObject2 = str;
          if (!((Iterator)localObject3).hasNext()) {
            break;
          }
          localObject2 = (String)((Iterator)localObject3).next();
        } while (!str.startsWith((String)localObject2));
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append(this.jdField_b_of_type_JavaLangString);
        ((StringBuilder)localObject3).append(str.substring(((String)localObject2).length()));
        localObject2 = ((StringBuilder)localObject3).toString();
      }
      paramClassLoader = a((String)localObject2, paramBoolean, paramClassLoader);
      this.jdField_a_of_type_ComTafJceInputStream.wrap((byte[])localObject1);
      this.jdField_a_of_type_ComTafJceInputStream.setServerEncoding(this.jdField_a_of_type_JavaLangString);
      paramClassLoader = this.jdField_a_of_type_ComTafJceInputStream.read(paramClassLoader, 0, true);
      a(paramString, paramClassLoader);
      return paramClassLoader;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
      throw new ObjectCreateException(paramString);
    }
  }
  
  public String getEncodeName()
  {
    return this.jdField_a_of_type_JavaLangString;
  }
  
  public Pair<String, byte[]> getRawResponseData(String paramString)
  {
    boolean bool = this.jdField_a_of_type_JavaUtilHashMap.containsKey(paramString);
    String str = null;
    if (!bool) {
      return null;
    }
    Object localObject = (HashMap)this.jdField_a_of_type_JavaUtilHashMap.get(paramString);
    paramString = new byte[0];
    localObject = ((HashMap)localObject).entrySet().iterator();
    if (((Iterator)localObject).hasNext())
    {
      paramString = (Map.Entry)((Iterator)localObject).next();
      str = (String)paramString.getKey();
      paramString = (byte[])paramString.getValue();
    }
    return new Pair(str, paramString);
  }
  
  public <T> void put(String paramString, T paramT)
  {
    if (paramString != null)
    {
      if (paramT != null)
      {
        if (!(paramT instanceof Set))
        {
          Object localObject = JceSynchronizedPool.getInstance().acquireout();
          ((JceOutputStream)localObject).setServerEncoding(this.jdField_a_of_type_JavaLangString);
          int j = 0;
          ((JceOutputStream)localObject).write(paramT, 0);
          byte[] arrayOfByte = JceUtil.getJceBufArray(((JceOutputStream)localObject).getByteBuffer());
          JceSynchronizedPool.getInstance().releaseOut((JceOutputStream)localObject);
          HashMap localHashMap = new HashMap(1);
          localObject = new ArrayList(1);
          a((ArrayList)localObject, paramT);
          String str = BasicClassTypeUtil.transTypeList((ArrayList)localObject);
          localObject = str;
          if (!TextUtils.isEmpty(this.jdField_b_of_type_JavaLangString))
          {
            localObject = str;
            if (str.startsWith(this.jdField_b_of_type_JavaLangString))
            {
              localObject = jdField_a_of_type_JavaUtilList.iterator();
              do
              {
                i = j;
                paramT = str;
                if (!((Iterator)localObject).hasNext()) {
                  break;
                }
                paramT = (String)((Iterator)localObject).next();
              } while (!this.jdField_b_of_type_JavaLangString.endsWith(paramT));
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append(paramT);
              ((StringBuilder)localObject).append(str.substring(this.jdField_b_of_type_JavaLangString.length()));
              paramT = ((StringBuilder)localObject).toString();
              int i = 1;
              localObject = paramT;
              if (i == 0)
              {
                localObject = new StringBuilder();
                ((StringBuilder)localObject).append("MTT");
                ((StringBuilder)localObject).append(paramT.substring(this.jdField_b_of_type_JavaLangString.length()));
                localObject = ((StringBuilder)localObject).toString();
              }
            }
          }
          localHashMap.put(localObject, arrayOfByte);
          this.c.remove(paramString);
          this.jdField_a_of_type_JavaUtilHashMap.put(paramString, localHashMap);
          return;
        }
        throw new IllegalArgumentException("can not support Set");
      }
      throw new IllegalArgumentException("put value can not is null");
    }
    throw new IllegalArgumentException("put key can not is null");
  }
  
  public void putRawRequestData(String paramString1, String paramString2, byte[] paramArrayOfByte)
  {
    HashMap localHashMap = new HashMap(1);
    localHashMap.put(paramString2, paramArrayOfByte);
    this.c.remove(paramString1);
    this.jdField_a_of_type_JavaUtilHashMap.put(paramString1, localHashMap);
  }
  
  public void setEncodeName(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  public void setProtocolClassNamePrefs(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    this.jdField_b_of_type_JavaLangString = paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\taf\a.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */