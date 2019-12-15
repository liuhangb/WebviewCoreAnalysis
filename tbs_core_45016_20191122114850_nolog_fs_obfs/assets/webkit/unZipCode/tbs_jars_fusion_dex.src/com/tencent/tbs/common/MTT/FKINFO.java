package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class FKINFO
  extends JceStruct
{
  public int eFastKey = 0;
  public int iStyle = 0;
  public int iUse = 0;
  public String sKey = "";
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iStyle = paramJceInputStream.read(this.iStyle, 0, true);
    this.eFastKey = paramJceInputStream.read(this.eFastKey, 1, true);
    this.sKey = paramJceInputStream.readString(2, true);
    this.iUse = paramJceInputStream.read(this.iUse, 3, true);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iStyle, 0);
    paramJceOutputStream.write(this.eFastKey, 1);
    paramJceOutputStream.write(this.sKey, 2);
    paramJceOutputStream.write(this.iUse, 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\FKINFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */