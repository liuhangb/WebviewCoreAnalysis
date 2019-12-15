package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class STTotal
  extends JceStruct
  implements Cloneable
{
  static int cache_eStatFrom;
  public int eStatFrom = 1;
  public int iErrorPV = 0;
  public int iLoginSuccessCount = 0;
  public int iLoginTotalCount = 0;
  public int iProxyPV = 0;
  public int iResFlow = 0;
  public int iResPv = 0;
  public int iWapFlow = 0;
  public int iWapPV = 0;
  public int iWebFlow = 0;
  public int iWebPV = 0;
  public String sAPN = "";
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sAPN = paramJceInputStream.readString(0, true);
    this.iWapPV = paramJceInputStream.read(this.iWapPV, 1, true);
    this.iWebPV = paramJceInputStream.read(this.iWebPV, 2, true);
    this.iResPv = paramJceInputStream.read(this.iResPv, 3, true);
    this.iWapFlow = paramJceInputStream.read(this.iWapFlow, 4, true);
    this.iWebFlow = paramJceInputStream.read(this.iWebFlow, 5, true);
    this.iResFlow = paramJceInputStream.read(this.iResFlow, 6, true);
    this.iErrorPV = paramJceInputStream.read(this.iErrorPV, 7, false);
    this.iProxyPV = paramJceInputStream.read(this.iProxyPV, 8, false);
    this.iLoginTotalCount = paramJceInputStream.read(this.iLoginTotalCount, 9, false);
    this.iLoginSuccessCount = paramJceInputStream.read(this.iLoginSuccessCount, 10, false);
    this.eStatFrom = paramJceInputStream.read(this.eStatFrom, 11, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sAPN, 0);
    paramJceOutputStream.write(this.iWapPV, 1);
    paramJceOutputStream.write(this.iWebPV, 2);
    paramJceOutputStream.write(this.iResPv, 3);
    paramJceOutputStream.write(this.iWapFlow, 4);
    paramJceOutputStream.write(this.iWebFlow, 5);
    paramJceOutputStream.write(this.iResFlow, 6);
    paramJceOutputStream.write(this.iErrorPV, 7);
    paramJceOutputStream.write(this.iProxyPV, 8);
    paramJceOutputStream.write(this.iLoginTotalCount, 9);
    paramJceOutputStream.write(this.iLoginSuccessCount, 10);
    paramJceOutputStream.write(this.eStatFrom, 11);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\STTotal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */