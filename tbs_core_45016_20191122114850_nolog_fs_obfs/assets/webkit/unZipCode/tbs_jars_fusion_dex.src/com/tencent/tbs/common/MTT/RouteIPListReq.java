package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class RouteIPListReq
  extends JceStruct
  implements Cloneable
{
  static UserBase cache_stUB;
  static ArrayList<Integer> cache_vIPType;
  public int iSubType = 0;
  public String sExtraInfo = "";
  public String sMCCMNC = "";
  public String sTypeName = "";
  public UserBase stUB = null;
  public ArrayList<Integer> vIPType = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    if (cache_stUB == null) {
      cache_stUB = new UserBase();
    }
    this.stUB = ((UserBase)paramJceInputStream.read(cache_stUB, 0, true));
    if (cache_vIPType == null)
    {
      cache_vIPType = new ArrayList();
      cache_vIPType.add(Integer.valueOf(0));
    }
    this.vIPType = ((ArrayList)paramJceInputStream.read(cache_vIPType, 1, true));
    this.sTypeName = paramJceInputStream.readString(2, false);
    this.iSubType = paramJceInputStream.read(this.iSubType, 3, false);
    this.sExtraInfo = paramJceInputStream.readString(4, false);
    this.sMCCMNC = paramJceInputStream.readString(5, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.stUB, 0);
    paramJceOutputStream.write(this.vIPType, 1);
    String str = this.sTypeName;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    paramJceOutputStream.write(this.iSubType, 3);
    str = this.sExtraInfo;
    if (str != null) {
      paramJceOutputStream.write(str, 4);
    }
    str = this.sMCCMNC;
    if (str != null) {
      paramJceOutputStream.write(str, 5);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\RouteIPListReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */