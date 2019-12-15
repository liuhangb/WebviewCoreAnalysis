package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class ZeusQRQ
  extends JceStruct
{
  static ArrayList<String> cache_ci;
  static ArrayList<ZeusDFI> cache_vD;
  static ArrayList<ZeusQI> cache_vQ = new ArrayList();
  public ArrayList<String> ci = null;
  public int iT = 0;
  public long lwkt = 0L;
  public ArrayList<ZeusDFI> vD = null;
  public ArrayList<ZeusQI> vQ = null;
  
  static
  {
    Object localObject = new ZeusQI();
    cache_vQ.add(localObject);
    cache_vD = new ArrayList();
    localObject = new ZeusDFI();
    cache_vD.add(localObject);
    cache_ci = new ArrayList();
    cache_ci.add("");
  }
  
  public ZeusQRQ() {}
  
  public ZeusQRQ(ArrayList<ZeusQI> paramArrayList, ArrayList<ZeusDFI> paramArrayList1, long paramLong, ArrayList<String> paramArrayList2, int paramInt)
  {
    this.vQ = paramArrayList;
    this.vD = paramArrayList1;
    this.lwkt = paramLong;
    this.ci = paramArrayList2;
    this.iT = paramInt;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.vQ = ((ArrayList)paramJceInputStream.read(cache_vQ, 0, true));
    this.vD = ((ArrayList)paramJceInputStream.read(cache_vD, 1, false));
    this.lwkt = paramJceInputStream.read(this.lwkt, 2, false);
    this.ci = ((ArrayList)paramJceInputStream.read(cache_ci, 3, false));
    this.iT = paramJceInputStream.read(this.iT, 4, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.vQ, 0);
    ArrayList localArrayList = this.vD;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 1);
    }
    paramJceOutputStream.write(this.lwkt, 2);
    localArrayList = this.ci;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 3);
    }
    paramJceOutputStream.write(this.iT, 4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ZeusQRQ.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */