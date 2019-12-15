package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class TokenInfoReq
  extends JceStruct
  implements Cloneable
{
  public String sGuid = "";
  public String sPhoneNum = "";
  public String sQua2 = "";
  
  public TokenInfoReq() {}
  
  public TokenInfoReq(String paramString1, String paramString2, String paramString3)
  {
    this.sGuid = paramString1;
    this.sQua2 = paramString2;
    this.sPhoneNum = paramString3;
  }
  
  public String getSGuid()
  {
    return this.sGuid;
  }
  
  public String getSPhoneNum()
  {
    return this.sPhoneNum;
  }
  
  public String getSQua2()
  {
    return this.sQua2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sGuid = paramJceInputStream.readString(0, false);
    this.sQua2 = paramJceInputStream.readString(1, false);
    this.sPhoneNum = paramJceInputStream.readString(2, false);
  }
  
  public void setSGuid(String paramString)
  {
    this.sGuid = paramString;
  }
  
  public void setSPhoneNum(String paramString)
  {
    this.sPhoneNum = paramString;
  }
  
  public void setSQua2(String paramString)
  {
    this.sQua2 = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sGuid;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    str = this.sQua2;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
    str = this.sPhoneNum;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\TokenInfoReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */