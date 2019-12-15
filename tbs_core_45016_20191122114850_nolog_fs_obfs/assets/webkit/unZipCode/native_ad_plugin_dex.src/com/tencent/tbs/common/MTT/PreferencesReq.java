package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class PreferencesReq
  extends JceStruct
{
  public int iGrayAreaType = 0;
  public int iReqTime = 0;
  public String sGUID = "";
  public String sQUA = "";
  public String sQua2ExInfo = "";
  
  public PreferencesReq() {}
  
  public PreferencesReq(int paramInt, String paramString1, String paramString2, String paramString3)
  {
    this.iReqTime = paramInt;
    this.sQUA = paramString1;
    this.sGUID = paramString2;
    this.sQua2ExInfo = paramString3;
  }
  
  public int getIReqTime()
  {
    return this.iReqTime;
  }
  
  public String getSGUID()
  {
    return this.sGUID;
  }
  
  public String getSQUA()
  {
    return this.sQUA;
  }
  
  public String getSQua2ExInfo()
  {
    return this.sQua2ExInfo;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iReqTime = paramJceInputStream.read(this.iReqTime, 0, true);
    this.sQUA = paramJceInputStream.readString(1, false);
    this.sGUID = paramJceInputStream.readString(2, false);
    this.sQua2ExInfo = paramJceInputStream.readString(3, false);
    this.iGrayAreaType = paramJceInputStream.read(this.iGrayAreaType, 5, false);
  }
  
  public void setIReqTime(int paramInt)
  {
    this.iReqTime = paramInt;
  }
  
  public void setSGUID(String paramString)
  {
    this.sGUID = paramString;
  }
  
  public void setSQUA(String paramString)
  {
    this.sQUA = paramString;
  }
  
  public void setSQua2ExInfo(String paramString)
  {
    this.sQua2ExInfo = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iReqTime, 0);
    String str = this.sQUA;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
    str = this.sGUID;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    str = this.sQua2ExInfo;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
    paramJceOutputStream.write(this.iGrayAreaType, 5);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PreferencesReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */