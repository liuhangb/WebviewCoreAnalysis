package com.tencent.tbs.common.push;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.tencent.common.utils.LogUtils;
import java.nio.ByteBuffer;

public class PushDataParserUtils
{
  private static final String TAG = "PushDataParserUtils";
  
  public static byte[] jce2Bytes(JceStruct paramJceStruct)
  {
    if (paramJceStruct == null) {
      return null;
    }
    try
    {
      JceOutputStream localJceOutputStream = new JceOutputStream();
      localJceOutputStream.setServerEncoding("UTF-8");
      paramJceStruct.writeTo(localJceOutputStream);
      paramJceStruct = localJceOutputStream.toByteArray();
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
        LogUtils.e("PushDataParserUtils", paramClass);
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
        LogUtils.e("PushDataParserUtils", paramClass);
        return null;
      }
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\push\PushDataParserUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */