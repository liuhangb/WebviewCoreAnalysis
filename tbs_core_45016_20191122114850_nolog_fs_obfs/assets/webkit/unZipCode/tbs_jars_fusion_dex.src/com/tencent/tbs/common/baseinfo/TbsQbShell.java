package com.tencent.tbs.common.baseinfo;

import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.MTT.GuidRsp;

public class TbsQbShell
{
  public static void setGuidToTbs(GuidRsp paramGuidRsp)
  {
    LogUtils.d("GUIDFactory", "handle qb set guid");
    GUIDFactory.getInstance().onQbSetGuid(paramGuidRsp);
  }
  
  public static void setGuidToTbs(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong)
  {
    GuidRsp localGuidRsp = new GuidRsp();
    localGuidRsp.vGuid = paramArrayOfByte1;
    localGuidRsp.vValidation = paramArrayOfByte2;
    localGuidRsp.lGenerateTime = paramLong;
    setGuidToTbs(localGuidRsp);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TbsQbShell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */