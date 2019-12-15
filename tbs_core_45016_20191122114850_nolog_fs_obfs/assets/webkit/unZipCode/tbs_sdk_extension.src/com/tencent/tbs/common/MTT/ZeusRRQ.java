package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ZeusRRQ
  extends JceStruct
{
  static int cache_t;
  static byte[] cache_vData = (byte[])new byte[1];
  public int t = 0;
  public byte[] vData = null;
  
  static
  {
    ((byte[])cache_vData)[0] = 0;
  }
  
  public ZeusRRQ() {}
  
  public ZeusRRQ(int paramInt, byte[] paramArrayOfByte)
  {
    this.t = paramInt;
    this.vData = paramArrayOfByte;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.t = paramJceInputStream.read(this.t, 0, true);
    this.vData = ((byte[])paramJceInputStream.read(cache_vData, 1, true));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.t, 0);
    paramJceOutputStream.write(this.vData, 1);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ZeusRRQ.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */