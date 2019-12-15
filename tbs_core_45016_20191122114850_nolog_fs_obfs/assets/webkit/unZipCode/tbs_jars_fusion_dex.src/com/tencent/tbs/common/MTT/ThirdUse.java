package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ThirdUse
  extends JceStruct
{
  public int eEUSESTAT = 0;
  public int iFail = 0;
  public int iSucc = 0;
  public String sPID = "";
  public String sUrl = "";
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sPID = paramJceInputStream.readString(0, true);
    this.eEUSESTAT = paramJceInputStream.read(this.eEUSESTAT, 1, true);
    this.sUrl = paramJceInputStream.readString(2, true);
    this.iSucc = paramJceInputStream.read(this.iSucc, 3, true);
    this.iFail = paramJceInputStream.read(this.iFail, 4, true);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sPID, 0);
    paramJceOutputStream.write(this.eEUSESTAT, 1);
    paramJceOutputStream.write(this.sUrl, 2);
    paramJceOutputStream.write(this.iSucc, 3);
    paramJceOutputStream.write(this.iFail, 4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ThirdUse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */