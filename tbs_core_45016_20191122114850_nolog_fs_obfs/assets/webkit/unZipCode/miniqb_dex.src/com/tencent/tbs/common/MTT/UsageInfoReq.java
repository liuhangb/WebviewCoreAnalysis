package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class UsageInfoReq
  extends JceStruct
  implements Cloneable
{
  static UsageInfo cache_stUsageInfo;
  static byte[] cache_vGuid;
  public UsageInfo stUsageInfo = null;
  public byte[] vGuid = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    if (cache_vGuid == null)
    {
      cache_vGuid = (byte[])new byte[1];
      ((byte[])cache_vGuid)[0] = 0;
    }
    this.vGuid = ((byte[])paramJceInputStream.read(cache_vGuid, 0, false));
    if (cache_stUsageInfo == null) {
      cache_stUsageInfo = new UsageInfo();
    }
    this.stUsageInfo = ((UsageInfo)paramJceInputStream.read(cache_stUsageInfo, 1, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.vGuid;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 0);
    }
    localObject = this.stUsageInfo;
    if (localObject != null) {
      paramJceOutputStream.write((JceStruct)localObject, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\UsageInfoReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */