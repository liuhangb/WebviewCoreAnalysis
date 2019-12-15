package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class GuidRsp
  extends JceStruct
{
  static byte[] cache_vGuid;
  static byte[] cache_vValidation;
  public long lGenerateTime = 0L;
  public byte[] vGuid = null;
  public byte[] vValidation = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    if (cache_vGuid == null)
    {
      cache_vGuid = (byte[])new byte[1];
      ((byte[])cache_vGuid)[0] = 0;
    }
    this.vGuid = ((byte[])paramJceInputStream.read(cache_vGuid, 0, false));
    if (cache_vValidation == null)
    {
      cache_vValidation = (byte[])new byte[1];
      ((byte[])cache_vValidation)[0] = 0;
    }
    this.vValidation = ((byte[])paramJceInputStream.read(cache_vValidation, 1, false));
    this.lGenerateTime = paramJceInputStream.read(this.lGenerateTime, 2, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    byte[] arrayOfByte = this.vGuid;
    if (arrayOfByte != null) {
      paramJceOutputStream.write(arrayOfByte, 0);
    }
    arrayOfByte = this.vValidation;
    if (arrayOfByte != null) {
      paramJceOutputStream.write(arrayOfByte, 1);
    }
    paramJceOutputStream.write(this.lGenerateTime, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\GuidRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */