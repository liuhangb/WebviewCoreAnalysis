package com.tencent.common.wup.base.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class JoinIPInfo
  extends JceStruct
  implements Cloneable
{
  static ArrayList<String> a;
  public int eIPType = 0;
  public int iLifePeriod = 8;
  public int iTotalPollNum = 8;
  public ArrayList<String> vIPList = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.eIPType = paramJceInputStream.read(this.eIPType, 0, false);
    if (a == null)
    {
      a = new ArrayList();
      a.add("");
    }
    this.vIPList = ((ArrayList)paramJceInputStream.read(a, 1, false));
    this.iTotalPollNum = paramJceInputStream.read(this.iTotalPollNum, 2, false);
    this.iLifePeriod = paramJceInputStream.read(this.iLifePeriod, 3, false);
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
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\base\MTT\JoinIPInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */