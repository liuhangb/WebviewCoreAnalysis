package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class TranslateReq
  extends JceStruct
{
  public String sDestLang = "";
  public String sSrcLang = "";
  public String sText = "";
  public String sUrl = "";
  
  public TranslateReq() {}
  
  public TranslateReq(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    this.sText = paramString1;
    this.sDestLang = paramString2;
    this.sSrcLang = paramString3;
    this.sUrl = paramString4;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sText = paramJceInputStream.readString(0, true);
    this.sDestLang = paramJceInputStream.readString(1, true);
    this.sSrcLang = paramJceInputStream.readString(2, false);
    this.sUrl = paramJceInputStream.readString(3, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sText, 0);
    paramJceOutputStream.write(this.sDestLang, 1);
    String str = this.sSrcLang;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    str = this.sUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\TranslateReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */