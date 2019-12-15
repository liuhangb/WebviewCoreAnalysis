package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class DetectReq
  extends JceStruct
{
  public String sText = "";
  public String sUrl = "";
  
  public DetectReq() {}
  
  public DetectReq(String paramString1, String paramString2)
  {
    this.sText = paramString1;
    this.sUrl = paramString2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sText = paramJceInputStream.readString(0, true);
    this.sUrl = paramJceInputStream.readString(1, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sText, 0);
    String str = this.sUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\DetectReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */