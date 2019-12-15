package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class TBSJSApiWhitelistReq
  extends JceStruct
{
  public String sAuth = "";
  public String sGuid = "";
  public String sHost = "";
  public String sQua = "";
  
  public TBSJSApiWhitelistReq() {}
  
  public TBSJSApiWhitelistReq(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.sGuid = paramString1;
    this.sQua = paramString2;
    this.sHost = paramString3;
    this.sAuth = paramString4;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sGuid = paramJceInputStream.readString(0, true);
    this.sQua = paramJceInputStream.readString(1, true);
    this.sHost = paramJceInputStream.readString(2, true);
    this.sAuth = paramJceInputStream.readString(3, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sGuid, 0);
    paramJceOutputStream.write(this.sQua, 1);
    paramJceOutputStream.write(this.sHost, 2);
    String str = this.sAuth;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\TBSJSApiWhitelistReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */