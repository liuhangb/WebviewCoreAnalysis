package com.tencent.tbs.common.download.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class DistReqHeader
  extends JceStruct
{
  public String sIMEI = "";
  
  public DistReqHeader() {}
  
  public DistReqHeader(String paramString)
  {
    this.sIMEI = paramString;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sIMEI = paramJceInputStream.readString(0, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sIMEI;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\MTT\DistReqHeader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */