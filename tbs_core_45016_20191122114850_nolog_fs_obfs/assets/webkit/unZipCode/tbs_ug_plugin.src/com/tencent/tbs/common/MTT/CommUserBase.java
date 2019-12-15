package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;

public final class CommUserBase
  extends JceStruct
  implements Cloneable
{
  static byte[] cache_sGUID;
  private static CommUserBase mInstance;
  public byte[] sGUID = null;
  public String sIMEI = "";
  public String sLC = "";
  public String sQUA = "";
  public String sUin = "";
  
  public CommUserBase()
  {
    DeviceUtils.getIMEI(TbsBaseModuleShell.getContext());
    byte[] arrayOfByte = GUIDFactory.getInstance().getByteGuid();
    if (arrayOfByte.length > 0) {
      setSGUID(arrayOfByte);
    } else {
      setSGUID(new byte[16]);
    }
    setSLC(TbsInfoUtils.getLC().trim());
    setSQUA(TbsInfoUtils.getQUA());
  }
  
  public static CommUserBase getIntance()
  {
    if (mInstance == null) {
      mInstance = new CommUserBase();
    }
    return mInstance;
  }
  
  public byte[] getSGUID()
  {
    return this.sGUID;
  }
  
  public String getSIMEI()
  {
    return this.sIMEI;
  }
  
  public String getSLC()
  {
    return this.sLC;
  }
  
  public String getSQUA()
  {
    return this.sQUA;
  }
  
  public String getSUin()
  {
    return this.sUin;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    if (cache_sGUID == null)
    {
      cache_sGUID = (byte[])new byte[1];
      ((byte[])cache_sGUID)[0] = 0;
    }
    this.sGUID = ((byte[])paramJceInputStream.read(cache_sGUID, 0, true));
    this.sQUA = paramJceInputStream.readString(1, true);
    this.sIMEI = paramJceInputStream.readString(2, false);
    this.sUin = paramJceInputStream.readString(3, false);
    this.sLC = paramJceInputStream.readString(4, false);
  }
  
  public void setSGUID(byte[] paramArrayOfByte)
  {
    this.sGUID = paramArrayOfByte;
  }
  
  public void setSIMEI(String paramString)
  {
    this.sIMEI = paramString;
  }
  
  public void setSLC(String paramString)
  {
    this.sLC = paramString;
  }
  
  public void setSQUA(String paramString)
  {
    this.sQUA = paramString;
  }
  
  public void setSUin(String paramString)
  {
    this.sUin = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sGUID, 0);
    paramJceOutputStream.write(this.sQUA, 1);
    String str = this.sIMEI;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    str = this.sUin;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
    str = this.sLC;
    if (str != null) {
      paramJceOutputStream.write(str, 4);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\CommUserBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */