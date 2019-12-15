package com.tencent.common.utils.gzip;

public class ZipIntMultShortHashMap
{
  public static final int DEFAULT_LOAD_FACTOR = 75;
  public static final int SUB_ELEMENT_SIZE = 3;
  private final int jdField_a_of_type_Int;
  private final boolean jdField_a_of_type_Boolean;
  private Element[] jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element;
  private int b;
  
  public ZipIntMultShortHashMap(int paramInt)
  {
    this(paramInt, 75);
  }
  
  public ZipIntMultShortHashMap(int paramInt1, int paramInt2)
  {
    int i = paramInt1 * 100 / paramInt2;
    boolean bool = true;
    paramInt1 = 1;
    while (i > paramInt1) {
      paramInt1 <<= 1;
    }
    if (paramInt1 != i) {
      bool = false;
    }
    this.jdField_a_of_type_Boolean = bool;
    this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element = new Element[i];
    this.jdField_a_of_type_Int = paramInt2;
  }
  
  private void a()
  {
    int i;
    if (this.jdField_a_of_type_Boolean) {
      i = this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element.length << 1;
    } else {
      i = (this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element.length << 1) - 1;
    }
    Element[] arrayOfElement = new Element[i];
    int j = 0;
    for (;;)
    {
      Object localObject = this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element;
      if (j >= localObject.length) {
        break;
      }
      Element localElement1;
      for (localObject = localObject[j]; localObject != null; localObject = localElement1)
      {
        int k;
        if (this.jdField_a_of_type_Boolean) {
          k = ((Element)localObject).key & 0x7FFFFFFF & i - 1;
        } else {
          k = (((Element)localObject).key & 0x7FFFFFFF) % i;
        }
        Element localElement2 = arrayOfElement[k];
        localElement1 = localElement2;
        if (localElement2 == null)
        {
          arrayOfElement[k] = localObject;
        }
        else
        {
          while (localElement1.next != null) {
            localElement1 = localElement1.next;
          }
          localElement1.next = ((Element)localObject);
        }
        localElement1 = ((Element)localObject).next;
        ((Element)localObject).next = null;
      }
      j += 1;
    }
    this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element = arrayOfElement;
  }
  
  public void clear()
  {
    int i = 0;
    for (;;)
    {
      Element[] arrayOfElement = this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element;
      if (i >= arrayOfElement.length) {
        break;
      }
      arrayOfElement[i] = null;
      i += 1;
    }
    this.b = 0;
  }
  
  public boolean containsKey(int paramInt)
  {
    return get(paramInt) != null;
  }
  
  public Element get(int paramInt)
  {
    int i;
    if (this.jdField_a_of_type_Boolean) {
      i = paramInt & 0x7FFFFFFF & this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element.length - 1;
    } else {
      i = (paramInt & 0x7FFFFFFF) % this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element.length;
    }
    Element localElement2 = this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element[i];
    Element localElement1 = localElement2;
    if (localElement2 == null) {
      return null;
    }
    do
    {
      if (localElement1.key == paramInt) {
        return localElement1;
      }
      localElement2 = localElement1.next;
      localElement1 = localElement2;
    } while (localElement2 != null);
    return null;
  }
  
  public boolean isEmpty()
  {
    return this.b == 0;
  }
  
  public int[] keys()
  {
    int[] arrayOfInt = new int[this.b];
    int i = 0;
    int j = 0;
    for (;;)
    {
      Object localObject = this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element;
      if (i >= localObject.length) {
        break;
      }
      for (localObject = localObject[i]; localObject != null; localObject = ((Element)localObject).next)
      {
        arrayOfInt[j] = ((Element)localObject).key;
        j += 1;
      }
      i += 1;
    }
    return arrayOfInt;
  }
  
  public boolean put(int paramInt, short paramShort)
  {
    if (this.b * 100 / this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element.length > this.jdField_a_of_type_Int) {
      a();
    }
    int i;
    if (this.jdField_a_of_type_Boolean) {
      i = paramInt & 0x7FFFFFFF & this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element.length - 1;
    } else {
      i = (paramInt & 0x7FFFFFFF) % this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element.length;
    }
    Object localObject2 = this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element[i];
    Object localObject1 = localObject2;
    if (localObject2 == null)
    {
      localObject1 = new Element(paramInt, new short[3]);
      ((Element)localObject1).values[0] = paramShort;
      ((Element)localObject1).size = ((short)(((Element)localObject1).size + 1));
      this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element[i] = localObject1;
      this.b += 1;
      return true;
    }
    for (;;)
    {
      if (((Element)localObject1).key == paramInt)
      {
        ((Element)localObject1).size = ((short)(((Element)localObject1).size + 1));
        if (((Element)localObject1).size == ((Element)localObject1).values.length)
        {
          localObject2 = new short[((Element)localObject1).values.length * 2];
          System.arraycopy(((Element)localObject1).values, 0, localObject2, 0, ((Element)localObject1).values.length);
          ((Element)localObject1).values = ((short[])localObject2);
        }
        ((Element)localObject1).values[(localObject1.size - 1)] = paramShort;
        return true;
      }
      localObject2 = ((Element)localObject1).next;
      if (localObject2 == null)
      {
        localObject2 = new Element(paramInt, new short[3]);
        ((Element)localObject2).values[0] = paramShort;
        ((Element)localObject2).size = ((short)(((Element)localObject2).size + 1));
        this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element[i] = localObject2;
        this.b += 1;
        ((Element)localObject1).next = ((Element)localObject2);
        return true;
      }
      localObject1 = localObject2;
    }
  }
  
  public short remove(int paramInt)
  {
    int i;
    if (this.jdField_a_of_type_Boolean) {
      i = paramInt & 0x7FFFFFFF & this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element.length - 1;
    } else {
      i = (paramInt & 0x7FFFFFFF) % this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element.length;
    }
    Object localObject1 = this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element[i];
    if (localObject1 == null) {
      return -1;
    }
    Object localObject2 = null;
    for (;;)
    {
      if (((Element)localObject1).key == paramInt)
      {
        if (localObject2 == null) {
          this.jdField_a_of_type_ArrayOfComTencentCommonUtilsGzipZipIntMultShortHashMap$Element[i] = ((Element)localObject1).next;
        } else {
          ((Element)localObject2).next = ((Element)localObject1).next;
        }
        this.b -= 1;
        return 1;
      }
      Element localElement = ((Element)localObject1).next;
      if (localElement == null) {
        return -1;
      }
      localObject2 = localObject1;
      localObject1 = localElement;
    }
  }
  
  public int size()
  {
    return this.b;
  }
  
  public static final class Element
  {
    public final int key;
    public Element next;
    public short size;
    public short[] values;
    
    public Element(int paramInt, short[] paramArrayOfShort)
    {
      this.key = paramInt;
      this.values = paramArrayOfShort;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\gzip\ZipIntMultShortHashMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */