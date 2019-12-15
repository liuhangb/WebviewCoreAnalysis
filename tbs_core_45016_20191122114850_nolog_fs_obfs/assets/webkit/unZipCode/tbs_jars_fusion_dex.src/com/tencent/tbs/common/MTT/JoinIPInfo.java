package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class JoinIPInfo
  extends JceStruct
{
  static int cache_eIPType;
  static ArrayList<String> cache_vIPList = new ArrayList();
  static ArrayList<Integer> cache_vProxyListType;
  public int eIPType = 0;
  public int iLifePeriod = 8;
  public int iTotalPollNum = 8;
  public ArrayList<String> vIPList = null;
  public ArrayList<Integer> vProxyListType = null;
  
  static
  {
    cache_vIPList.add("");
    cache_vProxyListType = new ArrayList();
    cache_vProxyListType.add(Integer.valueOf(0));
  }
  
  public JoinIPInfo() {}
  
  public JoinIPInfo(int paramInt1, ArrayList<String> paramArrayList, int paramInt2, int paramInt3, ArrayList<Integer> paramArrayList1)
  {
    this.eIPType = paramInt1;
    this.vIPList = paramArrayList;
    this.iTotalPollNum = paramInt2;
    this.iLifePeriod = paramInt3;
    this.vProxyListType = paramArrayList1;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.eIPType = paramJceInputStream.read(this.eIPType, 0, false);
    this.vIPList = ((ArrayList)paramJceInputStream.read(cache_vIPList, 1, false));
    this.iTotalPollNum = paramJceInputStream.read(this.iTotalPollNum, 2, false);
    this.iLifePeriod = paramJceInputStream.read(this.iLifePeriod, 3, false);
    this.vProxyListType = ((ArrayList)paramJceInputStream.read(cache_vProxyListType, 4, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.eIPType, 0);
    ArrayList localArrayList = this.vIPList;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 1);
    }
    paramJceOutputStream.write(this.iTotalPollNum, 2);
    paramJceOutputStream.write(this.iLifePeriod, 3);
    localArrayList = this.vProxyListType;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 4);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\JoinIPInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */