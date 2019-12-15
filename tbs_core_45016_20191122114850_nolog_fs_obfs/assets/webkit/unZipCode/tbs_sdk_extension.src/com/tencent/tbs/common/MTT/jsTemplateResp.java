package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class jsTemplateResp
  extends JceStruct
  implements Cloneable
{
  public String sContentMd5 = "";
  public String sJsTemplateContent = "";
  
  public jsTemplateResp() {}
  
  public jsTemplateResp(String paramString1, String paramString2)
  {
    this.sJsTemplateContent = paramString1;
    this.sContentMd5 = paramString2;
  }
  
  public String getSContentMd5()
  {
    return this.sContentMd5;
  }
  
  public String getSJsTemplateContent()
  {
    return this.sJsTemplateContent;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sJsTemplateContent = paramJceInputStream.readString(0, true);
    this.sContentMd5 = paramJceInputStream.readString(1, true);
  }
  
  public void setSContentMd5(String paramString)
  {
    this.sContentMd5 = paramString;
  }
  
  public void setSJsTemplateContent(String paramString)
  {
    this.sJsTemplateContent = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sJsTemplateContent, 0);
    paramJceOutputStream.write(this.sContentMd5, 1);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\jsTemplateResp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */