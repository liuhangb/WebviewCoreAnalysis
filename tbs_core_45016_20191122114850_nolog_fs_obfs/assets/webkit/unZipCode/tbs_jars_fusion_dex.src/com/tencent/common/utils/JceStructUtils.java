package com.tencent.common.utils;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.taf.JceSynchronizedPool;
import java.nio.ByteBuffer;

public class JceStructUtils
{
  public static final String DEFAULT_ENCODE_NAME = "UTF-8";
  
  public static byte[] jce2Bytes(JceStruct paramJceStruct)
  {
    if (paramJceStruct == null) {
      return null;
    }
    try
    {
      JceOutputStream localJceOutputStream = JceSynchronizedPool.getInstance().acquireout();
      localJceOutputStream.setServerEncoding("UTF-8");
      paramJceStruct.writeTo(localJceOutputStream);
      paramJceStruct = localJceOutputStream.copyByteArray();
      JceSynchronizedPool.getInstance().releaseOut(localJceOutputStream);
      return paramJceStruct;
    }
    catch (Exception paramJceStruct)
    {
      paramJceStruct.printStackTrace();
    }
    return null;
  }
  
  public static <T extends JceStruct> T parseRawData(Class<T> paramClass, ByteBuffer paramByteBuffer)
  {
    if (paramByteBuffer != null)
    {
      if (paramByteBuffer.position() == 0) {
        return null;
      }
      try
      {
        paramClass = (JceStruct)paramClass.newInstance();
        paramByteBuffer = new JceInputStream(paramByteBuffer);
        paramByteBuffer.setServerEncoding("UTF-8");
        paramClass.readFrom(paramByteBuffer);
        return paramClass;
      }
      catch (Throwable paramClass)
      {
        paramClass.printStackTrace();
        return null;
      }
    }
    return null;
  }
  
  public static <T extends JceStruct> T parseRawData(Class<T> paramClass, byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null)
    {
      if (paramArrayOfByte.length == 0) {
        return null;
      }
      try
      {
        paramClass = (JceStruct)paramClass.newInstance();
        paramArrayOfByte = new JceInputStream(paramArrayOfByte);
        paramArrayOfByte.setServerEncoding("UTF-8");
        paramClass.readFrom(paramArrayOfByte);
        return paramClass;
      }
      catch (Throwable paramClass)
      {
        paramClass.printStackTrace();
        return null;
      }
    }
    return null;
  }
  
  public static <T extends JceStruct> T parseRawDataThrows(Class<T> paramClass, byte[] paramArrayOfByte)
    throws InstantiationException, IllegalAccessException
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length != 0))
    {
      paramClass = (JceStruct)paramClass.newInstance();
      paramArrayOfByte = new JceInputStream(paramArrayOfByte);
      paramArrayOfByte.setServerEncoding("UTF-8");
      paramClass.readFrom(paramArrayOfByte);
      return paramClass;
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\JceStructUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */