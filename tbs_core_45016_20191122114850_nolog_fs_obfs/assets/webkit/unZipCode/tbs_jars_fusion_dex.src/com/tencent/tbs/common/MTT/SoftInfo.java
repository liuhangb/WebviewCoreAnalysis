package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class SoftInfo
  extends JceStruct
  implements Cloneable
{
  public int iLanguage = 200;
  public int iServerVer = 0;
  public String sAdId = "";
  public String sCurChannel = "";
  public String sLc = "";
  public String sOriChannel = "";
  public String sVenderId = "";
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sLc = paramJceInputStream.readString(0, false);
    this.sOriChannel = paramJceInputStream.readString(1, false);
    this.sCurChannel = paramJceInputStream.readString(2, false);
    this.iLanguage = paramJceInputStream.read(this.iLanguage, 3, false);
    this.iServerVer = paramJceInputStream.read(this.iServerVer, 4, false);
    this.sVenderId = paramJceInputStream.readString(5, false);
    this.sAdId = paramJceInputStream.readString(6, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sLc;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    str = this.sOriChannel;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
    str = this.sCurChannel;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    paramJceOutputStream.write(this.iLanguage, 3);
    paramJceOutputStream.write(this.iServerVer, 4);
    str = this.sVenderId;
    if (str != null) {
      paramJceOutputStream.write(str, 5);
    }
    str = this.sAdId;
    if (str != null) {
      paramJceOutputStream.write(str, 6);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\SoftInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */