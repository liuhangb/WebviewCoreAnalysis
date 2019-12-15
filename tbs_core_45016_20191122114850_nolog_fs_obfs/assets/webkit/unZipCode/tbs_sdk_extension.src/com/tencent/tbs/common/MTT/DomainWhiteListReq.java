package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class DomainWhiteListReq
  extends JceStruct
{
  public int iDomainTime = 0;
  public int iGrayAreaType = 0;
  public String sGUID = "";
  public String sQUA = "";
  public String sQua2ExInfo = "";
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iDomainTime = paramJceInputStream.read(this.iDomainTime, 0, true);
    this.sQUA = paramJceInputStream.readString(1, true);
    this.sGUID = paramJceInputStream.readString(2, true);
    this.sQua2ExInfo = paramJceInputStream.readString(3, false);
    this.iGrayAreaType = paramJceInputStream.read(this.iGrayAreaType, 5, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iDomainTime, 0);
    paramJceOutputStream.write(this.sQUA, 1);
    paramJceOutputStream.write(this.sGUID, 2);
    String str = this.sQua2ExInfo;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
    paramJceOutputStream.write(this.iGrayAreaType, 5);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\DomainWhiteListReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */