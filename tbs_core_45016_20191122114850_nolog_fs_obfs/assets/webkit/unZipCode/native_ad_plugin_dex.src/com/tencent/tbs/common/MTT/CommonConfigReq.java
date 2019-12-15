package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class CommonConfigReq
  extends JceStruct
{
  public int iAppId = 0;
  public int iDBTime = 0;
  public String sContentMd5 = "";
  public String sGuid = "";
  public String sQua2 = "";
  public String sQua2ExInfo = "";
  
  public CommonConfigReq() {}
  
  public CommonConfigReq(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4)
  {
    this.iAppId = paramInt1;
    this.sGuid = paramString1;
    this.sQua2 = paramString2;
    this.sContentMd5 = paramString3;
    this.iDBTime = paramInt2;
    this.sQua2ExInfo = paramString4;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iAppId = paramJceInputStream.read(this.iAppId, 0, true);
    this.sGuid = paramJceInputStream.readString(1, true);
    this.sQua2 = paramJceInputStream.readString(2, true);
    this.sContentMd5 = paramJceInputStream.readString(3, false);
    this.iDBTime = paramJceInputStream.read(this.iDBTime, 4, false);
    this.sQua2ExInfo = paramJceInputStream.readString(5, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iAppId, 0);
    paramJceOutputStream.write(this.sGuid, 1);
    paramJceOutputStream.write(this.sQua2, 2);
    String str = this.sContentMd5;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
    paramJceOutputStream.write(this.iDBTime, 4);
    str = this.sQua2ExInfo;
    if (str != null) {
      paramJceOutputStream.write(str, 5);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\CommonConfigReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */