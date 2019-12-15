package com.tencent.common.utils.gzip;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GzipUtils
{
  public static byte[] gZip(byte[] paramArrayOfByte)
  {
    try
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      Object localObject = new GZIPOutputStream(localByteArrayOutputStream);
      ((GZIPOutputStream)localObject).write(paramArrayOfByte);
      ((GZIPOutputStream)localObject).finish();
      ((GZIPOutputStream)localObject).close();
      localObject = localByteArrayOutputStream.toByteArray();
      localByteArrayOutputStream.close();
      return (byte[])localObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramArrayOfByte;
  }
  
  public static byte[] gZipMayThrows(byte[] paramArrayOfByte)
    throws Throwable
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    GZIPOutputStream localGZIPOutputStream = new GZIPOutputStream(localByteArrayOutputStream);
    localGZIPOutputStream.write(paramArrayOfByte);
    localGZIPOutputStream.finish();
    localGZIPOutputStream.close();
    paramArrayOfByte = localByteArrayOutputStream.toByteArray();
    localByteArrayOutputStream.close();
    return paramArrayOfByte;
  }
  
  public static byte[] unGzip(byte[] paramArrayOfByte)
  {
    try
    {
      ByteArrayInputStream localByteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
      GZIPInputStream localGZIPInputStream = new GZIPInputStream(localByteArrayInputStream);
      byte[] arrayOfByte = new byte['Ѐ'];
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      for (;;)
      {
        int i = localGZIPInputStream.read(arrayOfByte, 0, arrayOfByte.length);
        if (i == -1) {
          break;
        }
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      arrayOfByte = localByteArrayOutputStream.toByteArray();
      localByteArrayOutputStream.flush();
      localByteArrayOutputStream.close();
      localGZIPInputStream.close();
      localByteArrayInputStream.close();
      return arrayOfByte;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return paramArrayOfByte;
  }
  
  public static byte[] unGzipMayThrows(byte[] paramArrayOfByte)
    throws Throwable
  {
    paramArrayOfByte = new ByteArrayInputStream(paramArrayOfByte);
    GZIPInputStream localGZIPInputStream = new GZIPInputStream(paramArrayOfByte);
    byte[] arrayOfByte = new byte['Ѐ'];
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    for (;;)
    {
      int i = localGZIPInputStream.read(arrayOfByte, 0, arrayOfByte.length);
      if (i == -1) {
        break;
      }
      localByteArrayOutputStream.write(arrayOfByte, 0, i);
    }
    arrayOfByte = localByteArrayOutputStream.toByteArray();
    localByteArrayOutputStream.flush();
    localByteArrayOutputStream.close();
    localGZIPInputStream.close();
    paramArrayOfByte.close();
    return arrayOfByte;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\gzip\GzipUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */