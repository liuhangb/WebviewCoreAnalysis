package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class STPV
  extends JceStruct
{
  static int cache_eEntryType;
  static int cache_eLoginType;
  static int cache_eStatFrom;
  public int eEntryType = 0;
  public int eLoginType = 0;
  public int eStatFrom = 1;
  public int iFlow = 0;
  public int iResPv = 0;
  public int iWapPV = 0;
  public int iWebPV = 0;
  public String sDomain = "";
  
  public STPV() {}
  
  public STPV(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    this.sDomain = paramString;
    this.iWapPV = paramInt1;
    this.iWebPV = paramInt2;
    this.iResPv = paramInt3;
    this.iFlow = paramInt4;
    this.eStatFrom = paramInt5;
    this.eLoginType = paramInt6;
    this.eEntryType = paramInt7;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sDomain = paramJceInputStream.readString(0, true);
    this.iWapPV = paramJceInputStream.read(this.iWapPV, 1, true);
    this.iWebPV = paramJceInputStream.read(this.iWebPV, 2, true);
    this.iResPv = paramJceInputStream.read(this.iResPv, 3, true);
    this.iFlow = paramJceInputStream.read(this.iFlow, 4, false);
    this.eStatFrom = paramJceInputStream.read(this.eStatFrom, 5, false);
    this.eLoginType = paramJceInputStream.read(this.eLoginType, 6, false);
    this.eEntryType = paramJceInputStream.read(this.eEntryType, 7, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sDomain, 0);
    paramJceOutputStream.write(this.iWapPV, 1);
    paramJceOutputStream.write(this.iWebPV, 2);
    paramJceOutputStream.write(this.iResPv, 3);
    paramJceOutputStream.write(this.iFlow, 4);
    paramJceOutputStream.write(this.eStatFrom, 5);
    paramJceOutputStream.write(this.eLoginType, 6);
    paramJceOutputStream.write(this.eEntryType, 7);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\STPV.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */