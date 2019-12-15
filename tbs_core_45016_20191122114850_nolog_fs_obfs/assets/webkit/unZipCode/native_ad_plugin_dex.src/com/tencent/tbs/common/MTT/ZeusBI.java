package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ZeusBI
  extends JceStruct
{
  static int cache_e;
  static int cache_ps;
  public String b = "";
  public int e = 0;
  public long f = 0L;
  public String p = "";
  public int ps = 0;
  public String s = "";
  public long t = 0L;
  
  public ZeusBI() {}
  
  public ZeusBI(String paramString1, String paramString2, int paramInt1, String paramString3, long paramLong1, int paramInt2, long paramLong2)
  {
    this.s = paramString1;
    this.b = paramString2;
    this.e = paramInt1;
    this.p = paramString3;
    this.f = paramLong1;
    this.ps = paramInt2;
    this.t = paramLong2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.s = paramJceInputStream.readString(0, true);
    this.b = paramJceInputStream.readString(1, true);
    this.e = paramJceInputStream.read(this.e, 2, true);
    this.p = paramJceInputStream.readString(3, true);
    this.f = paramJceInputStream.read(this.f, 4, true);
    this.ps = paramJceInputStream.read(this.ps, 5, false);
    this.t = paramJceInputStream.read(this.t, 6, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.s, 0);
    paramJceOutputStream.write(this.b, 1);
    paramJceOutputStream.write(this.e, 2);
    paramJceOutputStream.write(this.p, 3);
    paramJceOutputStream.write(this.f, 4);
    paramJceOutputStream.write(this.ps, 5);
    paramJceOutputStream.write(this.t, 6);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ZeusBI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */