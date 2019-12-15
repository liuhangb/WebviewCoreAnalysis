package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;

public final class UsageInfo
  extends JceStruct
  implements Cloneable
{
  static byte[] cache_vLbsKeyData;
  static ArrayList<Long> cache_vWifiMacs;
  public short iMcc = 0;
  public short iMnc = 0;
  public String sApn = "";
  public String sCellId = "";
  public String sLac = "";
  public String sUin = "";
  public byte[] vLbsKeyData = null;
  public ArrayList<Long> vWifiMacs = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sUin = paramJceInputStream.readString(0, false);
    this.sCellId = paramJceInputStream.readString(1, false);
    this.sLac = paramJceInputStream.readString(2, false);
    this.iMnc = paramJceInputStream.read(this.iMnc, 3, false);
    this.iMcc = paramJceInputStream.read(this.iMcc, 4, false);
    this.sApn = paramJceInputStream.readString(5, false);
    if (cache_vLbsKeyData == null)
    {
      cache_vLbsKeyData = (byte[])new byte[1];
      ((byte[])cache_vLbsKeyData)[0] = 0;
    }
    this.vLbsKeyData = ((byte[])paramJceInputStream.read(cache_vLbsKeyData, 6, false));
    if (cache_vWifiMacs == null)
    {
      cache_vWifiMacs = new ArrayList();
      cache_vWifiMacs.add(Long.valueOf(0L));
    }
    this.vWifiMacs = ((ArrayList)paramJceInputStream.read(cache_vWifiMacs, 7, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.sUin;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 0);
    }
    localObject = this.sCellId;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.sLac;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    paramJceOutputStream.write(this.iMnc, 3);
    paramJceOutputStream.write(this.iMcc, 4);
    localObject = this.sApn;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 5);
    }
    localObject = this.vLbsKeyData;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 6);
    }
    localObject = this.vWifiMacs;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 7);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\UsageInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */