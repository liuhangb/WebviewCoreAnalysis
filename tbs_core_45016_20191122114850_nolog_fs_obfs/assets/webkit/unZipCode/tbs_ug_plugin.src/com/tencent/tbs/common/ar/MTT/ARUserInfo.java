package com.tencent.tbs.common.ar.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ARUserInfo
  extends JceStruct
{
  public String sUid = "";
  public String sUname = "";
  
  public ARUserInfo() {}
  
  public ARUserInfo(String paramString1, String paramString2)
  {
    this.sUid = paramString1;
    this.sUname = paramString2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sUid = paramJceInputStream.readString(0, false);
    this.sUname = paramJceInputStream.readString(1, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sUid;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    str = this.sUname;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\MTT\ARUserInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */