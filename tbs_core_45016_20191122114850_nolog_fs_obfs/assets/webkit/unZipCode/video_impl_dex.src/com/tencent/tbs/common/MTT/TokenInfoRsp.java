package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class TokenInfoRsp
  extends JceStruct
  implements Cloneable
{
  public int iExpireTime = 0;
  public int rspCode = -1;
  public String sToken = "";
  
  public TokenInfoRsp() {}
  
  public TokenInfoRsp(int paramInt1, String paramString, int paramInt2)
  {
    this.rspCode = paramInt1;
    this.sToken = paramString;
    this.iExpireTime = paramInt2;
  }
  
  public int getIExpireTime()
  {
    return this.iExpireTime;
  }
  
  public int getRspCode()
  {
    return this.rspCode;
  }
  
  public String getSToken()
  {
    return this.sToken;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.rspCode = paramJceInputStream.read(this.rspCode, 0, false);
    this.sToken = paramJceInputStream.readString(1, false);
    this.iExpireTime = paramJceInputStream.read(this.iExpireTime, 2, false);
  }
  
  public void setIExpireTime(int paramInt)
  {
    this.iExpireTime = paramInt;
  }
  
  public void setRspCode(int paramInt)
  {
    this.rspCode = paramInt;
  }
  
  public void setSToken(String paramString)
  {
    this.sToken = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.rspCode, 0);
    String str = this.sToken;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
    paramJceOutputStream.write(this.iExpireTime, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\TokenInfoRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */