package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class DeviceInfo
  extends JceStruct
  implements Cloneable
{
  static byte[] cache_vMac;
  public int iScreenHeight = 0;
  public int iScreenWidth = 0;
  public String sImei = "";
  public String sImsi = "";
  public String sPhoneNumber = "";
  public byte[] vMac = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sImei = paramJceInputStream.readString(0, false);
    this.sImsi = paramJceInputStream.readString(1, false);
    this.sPhoneNumber = paramJceInputStream.readString(2, false);
    if (cache_vMac == null)
    {
      cache_vMac = (byte[])new byte[1];
      ((byte[])cache_vMac)[0] = 0;
    }
    this.vMac = ((byte[])paramJceInputStream.read(cache_vMac, 3, false));
    this.iScreenWidth = paramJceInputStream.read(this.iScreenWidth, 4, false);
    this.iScreenHeight = paramJceInputStream.read(this.iScreenHeight, 5, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.sImei;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 0);
    }
    localObject = this.sImsi;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.sPhoneNumber;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    localObject = this.vMac;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 3);
    }
    paramJceOutputStream.write(this.iScreenWidth, 4);
    paramJceOutputStream.write(this.iScreenHeight, 5);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\DeviceInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */