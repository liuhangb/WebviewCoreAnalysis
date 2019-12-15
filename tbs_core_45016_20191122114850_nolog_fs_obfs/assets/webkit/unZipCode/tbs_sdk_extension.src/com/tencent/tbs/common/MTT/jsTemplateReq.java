package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class jsTemplateReq
  extends JceStruct
  implements Cloneable
{
  public String sGuid = "";
  public String sUserLocalMd5 = "";
  
  public jsTemplateReq() {}
  
  public jsTemplateReq(String paramString1, String paramString2)
  {
    this.sGuid = paramString1;
    this.sUserLocalMd5 = paramString2;
  }
  
  public String getSGuid()
  {
    return this.sGuid;
  }
  
  public String getSUserLocalMd5()
  {
    return this.sUserLocalMd5;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sGuid = paramJceInputStream.readString(0, true);
    this.sUserLocalMd5 = paramJceInputStream.readString(1, true);
  }
  
  public void setSGuid(String paramString)
  {
    this.sGuid = paramString;
  }
  
  public void setSUserLocalMd5(String paramString)
  {
    this.sUserLocalMd5 = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sGuid, 0);
    paramJceOutputStream.write(this.sUserLocalMd5, 1);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\jsTemplateReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */