package com.tencent.tbs.common.ar.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class CoordinInfo
  extends JceStruct
{
  public int iBottom = 0;
  public int iLeft = 0;
  public int iRight = 0;
  public int iTop = 0;
  
  public CoordinInfo() {}
  
  public CoordinInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.iTop = paramInt1;
    this.iLeft = paramInt2;
    this.iBottom = paramInt3;
    this.iRight = paramInt4;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iTop = paramJceInputStream.read(this.iTop, 0, false);
    this.iLeft = paramJceInputStream.read(this.iLeft, 1, false);
    this.iBottom = paramJceInputStream.read(this.iBottom, 2, false);
    this.iRight = paramJceInputStream.read(this.iRight, 3, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iTop, 0);
    paramJceOutputStream.write(this.iLeft, 1);
    paramJceOutputStream.write(this.iBottom, 2);
    paramJceOutputStream.write(this.iRight, 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ar\MTT\CoordinInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */