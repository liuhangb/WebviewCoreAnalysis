package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ReportResetGuidReq
  extends JceStruct
{
  static int cache_eTbsResetGuidCode;
  static byte[] cache_sOldGUID;
  public int eTbsResetGuidCode = 0;
  public int iScreenHeight = 0;
  public int iScreenWidth = 0;
  public String sImei = "";
  public String sImsi = "";
  public String sMac = "";
  public String sMachineName = "";
  public byte[] sOldGUID = null;
  public String sUin = "";
  
  public ReportResetGuidReq() {}
  
  public ReportResetGuidReq(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3)
  {
    this.sImei = paramString1;
    this.sImsi = paramString2;
    this.sMac = paramString3;
    this.sMachineName = paramString4;
    this.sUin = paramString5;
    this.iScreenWidth = paramInt1;
    this.iScreenHeight = paramInt2;
    this.sOldGUID = paramArrayOfByte;
    this.eTbsResetGuidCode = paramInt3;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sImei = paramJceInputStream.readString(0, false);
    this.sImsi = paramJceInputStream.readString(1, false);
    this.sMac = paramJceInputStream.readString(2, false);
    this.sMachineName = paramJceInputStream.readString(3, false);
    this.sUin = paramJceInputStream.readString(4, false);
    this.iScreenWidth = paramJceInputStream.read(this.iScreenWidth, 5, false);
    this.iScreenHeight = paramJceInputStream.read(this.iScreenHeight, 6, false);
    if (cache_sOldGUID == null)
    {
      cache_sOldGUID = (byte[])new byte[1];
      ((byte[])cache_sOldGUID)[0] = 0;
    }
    this.sOldGUID = ((byte[])paramJceInputStream.read(cache_sOldGUID, 7, false));
    this.eTbsResetGuidCode = paramJceInputStream.read(this.eTbsResetGuidCode, 8, false);
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
    localObject = this.sMac;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    localObject = this.sMachineName;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 3);
    }
    localObject = this.sUin;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 4);
    }
    paramJceOutputStream.write(this.iScreenWidth, 5);
    paramJceOutputStream.write(this.iScreenHeight, 6);
    localObject = this.sOldGUID;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 7);
    }
    paramJceOutputStream.write(this.eTbsResetGuidCode, 8);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ReportResetGuidReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */