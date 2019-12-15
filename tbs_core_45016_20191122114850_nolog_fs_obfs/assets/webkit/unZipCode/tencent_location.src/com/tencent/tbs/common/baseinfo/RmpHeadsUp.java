package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class RmpHeadsUp
  extends JceStruct
{
  public String sBtn = "";
  public String sUrl2 = "";
  
  public RmpHeadsUp() {}
  
  public RmpHeadsUp(String paramString1, String paramString2)
  {
    this.sUrl2 = paramString1;
    this.sBtn = paramString2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sUrl2 = paramJceInputStream.readString(0, false);
    this.sBtn = paramJceInputStream.readString(1, false);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("sUrl2 is ");
    localStringBuilder.append(this.sUrl2);
    localStringBuilder.append(" sBtn is ");
    localStringBuilder.append(this.sBtn);
    return localStringBuilder.toString();
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sUrl2;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    str = this.sBtn;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\RmpHeadsUp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */