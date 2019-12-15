package com.taf;

import java.io.Serializable;

public abstract class JceStruct
  implements Serializable
{
  public static final byte BYTE = 0;
  public static final byte DOUBLE = 5;
  public static final byte FLOAT = 4;
  public static final byte INT = 2;
  public static final int JCE_MAX_STRING_LENGTH = 104857600;
  public static final byte LIST = 9;
  public static final byte LONG = 3;
  public static final byte MAP = 8;
  public static final byte SHORT = 1;
  public static final byte SIMPLE_LIST = 13;
  public static final byte STRING1 = 6;
  public static final byte STRING4 = 7;
  public static final byte STRUCT_BEGIN = 10;
  public static final byte STRUCT_END = 11;
  public static final byte ZERO_TAG = 12;
  
  public void display(StringBuilder paramStringBuilder, int paramInt) {}
  
  public void displaySimple(StringBuilder paramStringBuilder, int paramInt) {}
  
  public abstract void readFrom(JceInputStream paramJceInputStream);
  
  public byte[] toByteArray()
  {
    JceOutputStream localJceOutputStream = JceSynchronizedPool.getInstance().acquireout();
    writeTo(localJceOutputStream);
    byte[] arrayOfByte = localJceOutputStream.copyByteArray();
    JceSynchronizedPool.getInstance().releaseOut(localJceOutputStream);
    return arrayOfByte;
  }
  
  public byte[] toByteArray(String paramString)
  {
    JceOutputStream localJceOutputStream = JceSynchronizedPool.getInstance().acquireout();
    localJceOutputStream.setServerEncoding(paramString);
    writeTo(localJceOutputStream);
    paramString = localJceOutputStream.copyByteArray();
    JceSynchronizedPool.getInstance().releaseOut(localJceOutputStream);
    return paramString;
  }
  
  public abstract void writeTo(JceOutputStream paramJceOutputStream);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\taf\JceStruct.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */