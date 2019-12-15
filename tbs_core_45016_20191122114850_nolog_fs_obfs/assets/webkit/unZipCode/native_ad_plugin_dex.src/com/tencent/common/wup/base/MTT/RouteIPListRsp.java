package com.tencent.common.wup.base.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class RouteIPListRsp
  extends JceStruct
{
  static ArrayList<JoinIPInfo> a = new ArrayList();
  public byte bProxy = 0;
  public int iSubType = 0;
  public String sApn = "";
  public String sExtraInfo = "";
  public String sMCCMNC = "";
  public String sTypeName = "";
  public ArrayList<JoinIPInfo> vIPInfos = null;
  
  static
  {
    JoinIPInfo localJoinIPInfo = new JoinIPInfo();
    a.add(localJoinIPInfo);
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.vIPInfos = ((ArrayList)paramJceInputStream.read(a, 0, true));
    this.sApn = paramJceInputStream.readString(1, false);
    this.bProxy = paramJceInputStream.read(this.bProxy, 2, false);
    this.sTypeName = paramJceInputStream.readString(3, false);
    this.iSubType = paramJceInputStream.read(this.iSubType, 4, false);
    this.sExtraInfo = paramJceInputStream.readString(5, false);
    this.sMCCMNC = paramJceInputStream.readString(6, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.vIPInfos, 0);
    String str = this.sApn;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
    paramJceOutputStream.write(this.bProxy, 2);
    str = this.sTypeName;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
    paramJceOutputStream.write(this.iSubType, 4);
    str = this.sExtraInfo;
    if (str != null) {
      paramJceOutputStream.write(str, 5);
    }
    str = this.sMCCMNC;
    if (str != null) {
      paramJceOutputStream.write(str, 6);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\base\MTT\RouteIPListRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */