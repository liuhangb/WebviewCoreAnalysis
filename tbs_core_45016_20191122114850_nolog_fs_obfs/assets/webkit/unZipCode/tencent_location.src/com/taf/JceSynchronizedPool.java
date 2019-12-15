package com.taf;

import java.nio.ByteBuffer;

public class JceSynchronizedPool
{
  public static JceSynchronizedPool sInstance;
  private int jdField_a_of_type_Int = 0;
  private final JceOutputStream[] jdField_a_of_type_ArrayOfComTafJceOutputStream;
  private int b = 0;
  private int c = 0;
  private int d = 0;
  public int mOutputPoolAvailableSize;
  
  private JceSynchronizedPool(int paramInt)
  {
    if (paramInt > 0)
    {
      this.b = paramInt;
      this.jdField_a_of_type_ArrayOfComTafJceOutputStream = new JceOutputStream[paramInt];
      return;
    }
    throw new IllegalArgumentException("The max pool size must be > 0");
  }
  
  private JceOutputStream a()
  {
    int i = this.mOutputPoolAvailableSize;
    int j = i - 1;
    JceOutputStream[] arrayOfJceOutputStream = this.jdField_a_of_type_ArrayOfComTafJceOutputStream;
    JceOutputStream localJceOutputStream = arrayOfJceOutputStream[j];
    arrayOfJceOutputStream[j] = null;
    this.mOutputPoolAvailableSize = (i - 1);
    return localJceOutputStream;
  }
  
  private boolean a(JceOutputStream paramJceOutputStream)
  {
    int i = 0;
    while (i < this.mOutputPoolAvailableSize)
    {
      if (this.jdField_a_of_type_ArrayOfComTafJceOutputStream[i] == paramJceOutputStream) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  public static JceSynchronizedPool getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new JceSynchronizedPool(4);
      }
      JceSynchronizedPool localJceSynchronizedPool = sInstance;
      return localJceSynchronizedPool;
    }
    finally {}
  }
  
  public JceOutputStream acquireout()
  {
    synchronized (this.jdField_a_of_type_ArrayOfComTafJceOutputStream)
    {
      if (this.c != 0) {
        int i = this.c;
      }
      this.c += 1;
      if (this.mOutputPoolAvailableSize > 0)
      {
        this.d += 1;
        localJceOutputStream = a();
        return localJceOutputStream;
      }
      if (this.jdField_a_of_type_Int < this.b)
      {
        this.d += 1;
        localJceOutputStream = new JceOutputStream();
        this.jdField_a_of_type_ArrayOfComTafJceOutputStream[this.mOutputPoolAvailableSize] = localJceOutputStream;
        this.mOutputPoolAvailableSize += 1;
        this.jdField_a_of_type_Int += 1;
        localJceOutputStream = a();
        return localJceOutputStream;
      }
      JceOutputStream localJceOutputStream = new JceOutputStream();
      return localJceOutputStream;
    }
  }
  
  public boolean releaseOut(JceOutputStream paramJceOutputStream)
  {
    synchronized (this.jdField_a_of_type_ArrayOfComTafJceOutputStream)
    {
      if (!a(paramJceOutputStream))
      {
        if (paramJceOutputStream.getByteBuffer().capacity() > 65536) {
          return true;
        }
        if (this.mOutputPoolAvailableSize < this.jdField_a_of_type_ArrayOfComTafJceOutputStream.length)
        {
          paramJceOutputStream.reInit();
          this.jdField_a_of_type_ArrayOfComTafJceOutputStream[this.mOutputPoolAvailableSize] = paramJceOutputStream;
          this.mOutputPoolAvailableSize += 1;
          return true;
        }
        return true;
      }
      throw new IllegalStateException("Already in the pool!");
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\taf\JceSynchronizedPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */