package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class SoftInfoReq
  extends JceStruct
  implements Cloneable
{
  static SoftInfo cache_stSoftInfo;
  static byte[] cache_vGuid;
  public SoftInfo stSoftInfo = null;
  public byte[] vGuid = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    if (cache_vGuid == null)
    {
      cache_vGuid = (byte[])new byte[1];
      ((byte[])cache_vGuid)[0] = 0;
    }
    this.vGuid = ((byte[])paramJceInputStream.read(cache_vGuid, 0, false));
    if (cache_stSoftInfo == null) {
      cache_stSoftInfo = new SoftInfo();
    }
    this.stSoftInfo = ((SoftInfo)paramJceInputStream.read(cache_stSoftInfo, 1, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.vGuid;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 0);
    }
    localObject = this.stSoftInfo;
    if (localObject != null) {
      paramJceOutputStream.write((JceStruct)localObject, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\SoftInfoReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */