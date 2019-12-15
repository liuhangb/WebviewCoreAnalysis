package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class ZeusQRS
  extends JceStruct
{
  static ArrayList<ZeusQRI> cache_sdkBlackList;
  static ArrayList<ZeusDFI> cache_vD;
  static ArrayList<ZeusQRI> cache_vF;
  static ArrayList<ZeusQRI> cache_vO;
  static ArrayList<ZeusQRI> cache_vQ = new ArrayList();
  public int code = 0;
  public ArrayList<ZeusQRI> sdkBlackList = null;
  public long t = 0L;
  public ArrayList<ZeusDFI> vD = null;
  public ArrayList<ZeusQRI> vF = null;
  public ArrayList<ZeusQRI> vO = null;
  public ArrayList<ZeusQRI> vQ = null;
  
  static
  {
    Object localObject = new ZeusQRI();
    cache_vQ.add(localObject);
    cache_vF = new ArrayList();
    localObject = new ZeusQRI();
    cache_vF.add(localObject);
    cache_vO = new ArrayList();
    localObject = new ZeusQRI();
    cache_vO.add(localObject);
    cache_vD = new ArrayList();
    localObject = new ZeusDFI();
    cache_vD.add(localObject);
    cache_sdkBlackList = new ArrayList();
    localObject = new ZeusQRI();
    cache_sdkBlackList.add(localObject);
  }
  
  public ZeusQRS() {}
  
  public ZeusQRS(int paramInt, ArrayList<ZeusQRI> paramArrayList1, long paramLong, ArrayList<ZeusQRI> paramArrayList2, ArrayList<ZeusQRI> paramArrayList3, ArrayList<ZeusDFI> paramArrayList, ArrayList<ZeusQRI> paramArrayList4)
  {
    this.code = paramInt;
    this.vQ = paramArrayList1;
    this.t = paramLong;
    this.vF = paramArrayList2;
    this.vO = paramArrayList3;
    this.vD = paramArrayList;
    this.sdkBlackList = paramArrayList4;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.code = paramJceInputStream.read(this.code, 0, true);
    this.vQ = ((ArrayList)paramJceInputStream.read(cache_vQ, 1, true));
    this.t = paramJceInputStream.read(this.t, 2, false);
    this.vF = ((ArrayList)paramJceInputStream.read(cache_vF, 3, false));
    this.vO = ((ArrayList)paramJceInputStream.read(cache_vO, 4, false));
    this.vD = ((ArrayList)paramJceInputStream.read(cache_vD, 5, false));
    this.sdkBlackList = ((ArrayList)paramJceInputStream.read(cache_sdkBlackList, 6, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.code, 0);
    paramJceOutputStream.write(this.vQ, 1);
    paramJceOutputStream.write(this.t, 2);
    ArrayList localArrayList = this.vF;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 3);
    }
    localArrayList = this.vO;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 4);
    }
    localArrayList = this.vD;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 5);
    }
    localArrayList = this.sdkBlackList;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 6);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ZeusQRS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */