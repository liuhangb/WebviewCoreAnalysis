package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class ZeusRID
  extends JceStruct
{
  static ArrayList<ZeusBI> cache_vR = new ArrayList();
  public ArrayList<ZeusBI> vR = null;
  
  static
  {
    ZeusBI localZeusBI = new ZeusBI();
    cache_vR.add(localZeusBI);
  }
  
  public ZeusRID() {}
  
  public ZeusRID(ArrayList<ZeusBI> paramArrayList)
  {
    this.vR = paramArrayList;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.vR = ((ArrayList)paramJceInputStream.read(cache_vR, 0, true));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.vR, 0);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ZeusRID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */