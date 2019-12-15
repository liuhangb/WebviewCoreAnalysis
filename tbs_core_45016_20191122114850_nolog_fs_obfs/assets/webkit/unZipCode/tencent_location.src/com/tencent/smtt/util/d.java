package com.tencent.smtt.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class d
{
  public static int a(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    long l = a(paramInputStream, paramOutputStream);
    if (l > 2147483647L) {
      return -1;
    }
    return (int)l;
  }
  
  public static long a(InputStream paramInputStream, OutputStream paramOutputStream)
    throws IOException
  {
    if (paramInputStream == null) {
      return -1L;
    }
    byte[] arrayOfByte = new byte['က'];
    int i;
    for (long l = 0L;; l += i)
    {
      i = paramInputStream.read(arrayOfByte);
      if (-1 == i) {
        break;
      }
      paramOutputStream.write(arrayOfByte, 0, i);
    }
    return l;
  }
  
  public static String a(InputStream paramInputStream, String paramString)
  {
    try
    {
      paramInputStream = new String(a(paramInputStream), paramString);
      return paramInputStream;
    }
    catch (IOException paramInputStream)
    {
      for (;;) {}
    }
    return null;
  }
  
  public static byte[] a(InputStream paramInputStream)
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    a(paramInputStream, localByteArrayOutputStream);
    return localByteArrayOutputStream.toByteArray();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\d.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */