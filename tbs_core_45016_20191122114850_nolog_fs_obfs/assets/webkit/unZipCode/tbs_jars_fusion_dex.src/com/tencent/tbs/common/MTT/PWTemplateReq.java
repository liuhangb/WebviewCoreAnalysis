package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class PWTemplateReq
  extends JceStruct
{
  public long iTemplateId = 0L;
  public String sChannelId = "";
  public String sGuid = "";
  public String sOriginalUrl = "";
  public String sQua = "";
  public String sTid = "";
  
  public PWTemplateReq() {}
  
  public PWTemplateReq(long paramLong, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    this.iTemplateId = paramLong;
    this.sGuid = paramString1;
    this.sQua = paramString2;
    this.sChannelId = paramString3;
    this.sOriginalUrl = paramString4;
    this.sTid = paramString5;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iTemplateId = paramJceInputStream.read(this.iTemplateId, 0, true);
    this.sGuid = paramJceInputStream.readString(1, false);
    this.sQua = paramJceInputStream.readString(2, false);
    this.sChannelId = paramJceInputStream.readString(3, false);
    this.sOriginalUrl = paramJceInputStream.readString(4, false);
    this.sTid = paramJceInputStream.readString(5, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iTemplateId, 0);
    String str = this.sGuid;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
    str = this.sQua;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    str = this.sChannelId;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
    str = this.sOriginalUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 4);
    }
    str = this.sTid;
    if (str != null) {
      paramJceOutputStream.write(str, 5);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PWTemplateReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */