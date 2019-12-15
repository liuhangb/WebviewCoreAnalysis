package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class STTime
  extends JceStruct
{
  public int iAvgDirectTime = 0;
  public int iAvgProxyTime = 0;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iAvgProxyTime = paramJceInputStream.read(this.iAvgProxyTime, 0, true);
    this.iAvgDirectTime = paramJceInputStream.read(this.iAvgDirectTime, 1, true);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iAvgProxyTime, 0);
    paramJceOutputStream.write(this.iAvgDirectTime, 1);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\STTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */