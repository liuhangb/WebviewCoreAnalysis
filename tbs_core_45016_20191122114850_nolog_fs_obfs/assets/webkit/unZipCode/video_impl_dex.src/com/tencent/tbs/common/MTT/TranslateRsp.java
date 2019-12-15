package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class TranslateRsp
  extends JceStruct
{
  public int iErrorPercent = 0;
  public String sBrandWording = "";
  public String sErrorMsg = "";
  public String sResult = "";
  
  public TranslateRsp() {}
  
  public TranslateRsp(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    this.sResult = paramString1;
    this.sErrorMsg = paramString2;
    this.sBrandWording = paramString3;
    this.iErrorPercent = paramInt;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sResult = paramJceInputStream.readString(0, true);
    this.sErrorMsg = paramJceInputStream.readString(1, false);
    this.sBrandWording = paramJceInputStream.readString(2, false);
    this.iErrorPercent = paramJceInputStream.read(this.iErrorPercent, 3, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sResult, 0);
    String str = this.sErrorMsg;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
    str = this.sBrandWording;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    paramJceOutputStream.write(this.iErrorPercent, 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\TranslateRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */