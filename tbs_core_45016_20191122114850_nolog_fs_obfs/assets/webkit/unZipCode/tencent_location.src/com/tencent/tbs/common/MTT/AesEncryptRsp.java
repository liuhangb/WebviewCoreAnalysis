package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public class AesEncryptRsp
  extends JceStruct
{
  public int iErrCode = 0;
  public String result = "";
  public String sMessage = "";
  
  public AesEncryptRsp() {}
  
  public AesEncryptRsp(int paramInt, String paramString1, String paramString2)
  {
    this.iErrCode = paramInt;
    this.sMessage = paramString1;
    this.result = paramString2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iErrCode = paramJceInputStream.read(this.iErrCode, 0, true);
    this.sMessage = paramJceInputStream.readString(1, true);
    this.result = paramJceInputStream.readString(2, true);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iErrCode, 0);
    paramJceOutputStream.write(this.sMessage, 1);
    paramJceOutputStream.write(this.result, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\AesEncryptRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */