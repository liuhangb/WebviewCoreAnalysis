package com.tencent.mtt.video.export.basemoduleforexternal;

import java.security.MessageDigest;

public class Md5Utils
{
  private static String a(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
    {
      StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
      int i = 0;
      while (i < paramArrayOfByte.length)
      {
        if ((paramArrayOfByte[i] & 0xFF) < 16) {
          localStringBuffer.append("0");
        }
        localStringBuffer.append(Long.toString(paramArrayOfByte[i] & 0xFF, 16));
        i += 1;
      }
      return localStringBuffer.toString();
    }
    return null;
  }
  
  public static String getMD5(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    try
    {
      paramString = paramString.getBytes();
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString);
      paramString = a(localMessageDigest.digest());
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\export\basemoduleforexternal\Md5Utils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */