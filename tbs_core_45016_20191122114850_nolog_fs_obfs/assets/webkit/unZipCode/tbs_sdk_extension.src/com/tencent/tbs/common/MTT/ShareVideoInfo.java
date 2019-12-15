package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ShareVideoInfo
  extends JceStruct
{
  public String sSummary = "";
  public String sTitle = "";
  public String sUrl = "";
  
  public ShareVideoInfo() {}
  
  public ShareVideoInfo(String paramString1, String paramString2, String paramString3)
  {
    this.sUrl = paramString1;
    this.sTitle = paramString2;
    this.sSummary = paramString3;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sUrl = paramJceInputStream.readString(0, false);
    this.sTitle = paramJceInputStream.readString(1, false);
    this.sSummary = paramJceInputStream.readString(2, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    str = this.sTitle;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
    str = this.sSummary;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ShareVideoInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */