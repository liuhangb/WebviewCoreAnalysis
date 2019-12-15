package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class GuidReq
  extends JceStruct
{
  static int cache_eRequestTriggeredType;
  static byte[] cache_vValidation;
  public int eRequestTriggeredType = 0;
  public String sAdId = "";
  public String sAdrID = "";
  public String sImei = "";
  public String sImsi = "";
  public String sMac = "";
  public String sQIMEI = "";
  public String sQua = "";
  public String sVenderId = "";
  public String ss = "";
  public byte[] vValidation = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sQua = paramJceInputStream.readString(0, false);
    this.sImei = paramJceInputStream.readString(1, false);
    this.sImsi = paramJceInputStream.readString(2, false);
    this.sVenderId = paramJceInputStream.readString(3, false);
    this.sAdId = paramJceInputStream.readString(4, false);
    if (cache_vValidation == null)
    {
      cache_vValidation = (byte[])new byte[1];
      ((byte[])cache_vValidation)[0] = 0;
    }
    this.vValidation = ((byte[])paramJceInputStream.read(cache_vValidation, 5, false));
    this.eRequestTriggeredType = paramJceInputStream.read(this.eRequestTriggeredType, 6, false);
    this.sMac = paramJceInputStream.readString(7, false);
    this.sQIMEI = paramJceInputStream.readString(8, false);
    this.sAdrID = paramJceInputStream.readString(9, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.sQua;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 0);
    }
    localObject = this.sImei;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.sImsi;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    localObject = this.sVenderId;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 3);
    }
    localObject = this.sAdId;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 4);
    }
    localObject = this.vValidation;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 5);
    }
    paramJceOutputStream.write(this.eRequestTriggeredType, 6);
    localObject = this.sMac;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 7);
    }
    localObject = this.sQIMEI;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 8);
    }
    localObject = this.sAdrID;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 9);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\GuidReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */