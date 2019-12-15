package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class DetectRsp
  extends JceStruct
{
  public double dScore = 0.0D;
  public int iPercent = 0;
  public String sLang = "";
  public String sPossibleLang = "";
  
  public DetectRsp() {}
  
  public DetectRsp(String paramString1, double paramDouble, int paramInt, String paramString2)
  {
    this.sLang = paramString1;
    this.dScore = paramDouble;
    this.iPercent = paramInt;
    this.sPossibleLang = paramString2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sLang = paramJceInputStream.readString(0, true);
    this.dScore = paramJceInputStream.read(this.dScore, 1, true);
    this.iPercent = paramJceInputStream.read(this.iPercent, 2, true);
    this.sPossibleLang = paramJceInputStream.readString(3, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sLang, 0);
    paramJceOutputStream.write(this.dScore, 1);
    paramJceOutputStream.write(this.iPercent, 2);
    String str = this.sPossibleLang;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\DetectRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */