package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ZeusDFI
  extends JceStruct
{
  static int cache_t;
  public String b = "";
  public long f = 0L;
  public String s = "";
  public int t = 0;
  
  public ZeusDFI() {}
  
  public ZeusDFI(String paramString1, String paramString2, int paramInt, long paramLong)
  {
    this.s = paramString1;
    this.b = paramString2;
    this.t = paramInt;
    this.f = paramLong;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.s = paramJceInputStream.readString(0, true);
    this.b = paramJceInputStream.readString(1, true);
    this.t = paramJceInputStream.read(this.t, 2, true);
    this.f = paramJceInputStream.read(this.f, 3, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.s, 0);
    paramJceOutputStream.write(this.b, 1);
    paramJceOutputStream.write(this.t, 2);
    paramJceOutputStream.write(this.f, 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ZeusDFI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */