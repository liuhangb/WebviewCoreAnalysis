package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ZeusQI
  extends JceStruct
{
  static int cache_t;
  public String b = "";
  public String s = "";
  public int t = 0;
  
  public ZeusQI() {}
  
  public ZeusQI(String paramString1, String paramString2, int paramInt)
  {
    this.s = paramString1;
    this.b = paramString2;
    this.t = paramInt;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.s = paramJceInputStream.readString(0, true);
    this.b = paramJceInputStream.readString(1, true);
    this.t = paramJceInputStream.read(this.t, 2, true);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    if (this.s == null) {
      this.s = "";
    }
    if (this.b == null) {
      this.b = "";
    }
    paramJceOutputStream.write(this.s, 0);
    paramJceOutputStream.write(this.b, 1);
    paramJceOutputStream.write(this.t, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ZeusQI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */