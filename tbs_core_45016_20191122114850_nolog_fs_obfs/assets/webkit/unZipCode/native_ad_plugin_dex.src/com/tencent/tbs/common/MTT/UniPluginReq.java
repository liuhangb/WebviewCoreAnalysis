package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class UniPluginReq
  extends JceStruct
{
  static byte[] cache_vGuid;
  public int iPluginType = 0;
  public int iSDKVersion = 0;
  public String sMd5 = "";
  public String sQua = "";
  public String sQua2ExInfo = "";
  public String strCpu = "";
  public byte[] vGuid = null;
  
  public UniPluginReq() {}
  
  public UniPluginReq(String paramString1, byte[] paramArrayOfByte, int paramInt1, String paramString2, String paramString3, int paramInt2, String paramString4)
  {
    this.sQua = paramString1;
    this.vGuid = paramArrayOfByte;
    this.iSDKVersion = paramInt1;
    this.strCpu = paramString2;
    this.sMd5 = paramString3;
    this.iPluginType = paramInt2;
    this.sQua2ExInfo = paramString4;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sQua = paramJceInputStream.readString(0, true);
    if (cache_vGuid == null)
    {
      cache_vGuid = (byte[])new byte[1];
      ((byte[])cache_vGuid)[0] = 0;
    }
    this.vGuid = ((byte[])paramJceInputStream.read(cache_vGuid, 1, true));
    this.iSDKVersion = paramJceInputStream.read(this.iSDKVersion, 2, true);
    this.strCpu = paramJceInputStream.readString(3, true);
    this.sMd5 = paramJceInputStream.readString(4, true);
    this.iPluginType = paramJceInputStream.read(this.iPluginType, 5, false);
    this.sQua2ExInfo = paramJceInputStream.readString(6, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sQua, 0);
    paramJceOutputStream.write(this.vGuid, 1);
    paramJceOutputStream.write(this.iSDKVersion, 2);
    paramJceOutputStream.write(this.strCpu, 3);
    paramJceOutputStream.write(this.sMd5, 4);
    paramJceOutputStream.write(this.iPluginType, 5);
    String str = this.sQua2ExInfo;
    if (str != null) {
      paramJceOutputStream.write(str, 6);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\UniPluginReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */