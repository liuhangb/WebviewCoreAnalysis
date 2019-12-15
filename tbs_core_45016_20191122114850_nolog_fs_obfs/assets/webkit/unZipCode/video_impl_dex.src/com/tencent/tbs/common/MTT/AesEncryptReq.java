package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public class AesEncryptReq
  extends JceStruct
{
  public String text = "";
  public int type = 0;
  
  public AesEncryptReq() {}
  
  public AesEncryptReq(int paramInt, String paramString)
  {
    this.type = paramInt;
    this.text = paramString;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.type = paramJceInputStream.read(this.type, 0, true);
    this.text = paramJceInputStream.readString(1, true);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.type, 0);
    paramJceOutputStream.write(this.text, 1);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\AesEncryptReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */