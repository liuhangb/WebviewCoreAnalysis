package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ZeusQRI
  extends JceStruct
{
  public String b = "";
  public String br = "";
  public boolean c = true;
  public int f = 0;
  public long from = 0L;
  public boolean hasPsw = true;
  public String lu = "";
  public String new_portalUrl = "";
  public String p = "";
  public String pu = "";
  public String s = "";
  public long t = 0L;
  public String ws = "";
  
  public ZeusQRI() {}
  
  public ZeusQRI(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, int paramInt, long paramLong1, String paramString4, String paramString5, String paramString6, String paramString7, long paramLong2, String paramString8, boolean paramBoolean2)
  {
    this.s = paramString1;
    this.b = paramString2;
    this.p = paramString3;
    this.c = paramBoolean1;
    this.f = paramInt;
    this.t = paramLong1;
    this.lu = paramString4;
    this.br = paramString5;
    this.pu = paramString6;
    this.ws = paramString7;
    this.from = paramLong2;
    this.new_portalUrl = paramString8;
    this.hasPsw = paramBoolean2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.s = paramJceInputStream.readString(0, true);
    this.b = paramJceInputStream.readString(1, true);
    this.p = paramJceInputStream.readString(2, true);
    this.c = paramJceInputStream.read(this.c, 3, false);
    this.f = paramJceInputStream.read(this.f, 4, false);
    this.t = paramJceInputStream.read(this.t, 5, false);
    this.lu = paramJceInputStream.readString(6, false);
    this.br = paramJceInputStream.readString(7, false);
    this.pu = paramJceInputStream.readString(8, false);
    this.ws = paramJceInputStream.readString(9, false);
    this.from = paramJceInputStream.read(this.from, 10, false);
    this.new_portalUrl = paramJceInputStream.readString(11, false);
    this.hasPsw = paramJceInputStream.read(this.hasPsw, 12, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.s, 0);
    paramJceOutputStream.write(this.b, 1);
    paramJceOutputStream.write(this.p, 2);
    paramJceOutputStream.write(this.c, 3);
    paramJceOutputStream.write(this.f, 4);
    paramJceOutputStream.write(this.t, 5);
    String str = this.lu;
    if (str != null) {
      paramJceOutputStream.write(str, 6);
    }
    str = this.br;
    if (str != null) {
      paramJceOutputStream.write(str, 7);
    }
    str = this.pu;
    if (str != null) {
      paramJceOutputStream.write(str, 8);
    }
    str = this.ws;
    if (str != null) {
      paramJceOutputStream.write(str, 9);
    }
    paramJceOutputStream.write(this.from, 10);
    str = this.new_portalUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 11);
    }
    paramJceOutputStream.write(this.hasPsw, 12);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ZeusQRI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */