package com.tencent.tbs.common.ar.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ARCloudMarkerInfo
  extends JceStruct
{
  static byte[] cache_vMetaData = (byte[])new byte[1];
  public String sExtendJsonString = "";
  public String sModifyTime = "";
  public String sTargetName = "";
  public String sTid = "";
  public String sUrl = "";
  public byte[] vMetaData = null;
  
  static
  {
    ((byte[])cache_vMetaData)[0] = 0;
  }
  
  public ARCloudMarkerInfo() {}
  
  public ARCloudMarkerInfo(String paramString1, String paramString2, String paramString3, byte[] paramArrayOfByte, String paramString4, String paramString5)
  {
    this.sTid = paramString1;
    this.sUrl = paramString2;
    this.sTargetName = paramString3;
    this.vMetaData = paramArrayOfByte;
    this.sModifyTime = paramString4;
    this.sExtendJsonString = paramString5;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sTid = paramJceInputStream.readString(0, false);
    this.sUrl = paramJceInputStream.readString(1, false);
    this.sTargetName = paramJceInputStream.readString(2, false);
    this.vMetaData = ((byte[])paramJceInputStream.read(cache_vMetaData, 3, false));
    this.sModifyTime = paramJceInputStream.readString(4, false);
    this.sExtendJsonString = paramJceInputStream.readString(5, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.sTid;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 0);
    }
    localObject = this.sUrl;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.sTargetName;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    localObject = this.vMetaData;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 3);
    }
    localObject = this.sModifyTime;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 4);
    }
    localObject = this.sExtendJsonString;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 5);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\MTT\ARCloudMarkerInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */