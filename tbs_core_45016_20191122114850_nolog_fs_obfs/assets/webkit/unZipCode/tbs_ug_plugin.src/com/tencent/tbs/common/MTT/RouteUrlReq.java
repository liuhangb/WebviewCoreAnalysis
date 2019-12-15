package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class RouteUrlReq
  extends JceStruct
{
  static UserBase cache_stUB = new UserBase();
  public int iMD5Value = 0;
  public int iType = 0;
  public UserBase stUB = null;
  
  public RouteUrlReq() {}
  
  public RouteUrlReq(UserBase paramUserBase, int paramInt1, int paramInt2)
  {
    this.stUB = paramUserBase;
    this.iMD5Value = paramInt1;
    this.iType = paramInt2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.stUB = ((UserBase)paramJceInputStream.read(cache_stUB, 0, true));
    this.iMD5Value = paramJceInputStream.read(this.iMD5Value, 1, true);
    this.iType = paramJceInputStream.read(this.iType, 2, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.stUB, 0);
    paramJceOutputStream.write(this.iMD5Value, 1);
    paramJceOutputStream.write(this.iType, 2);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\RouteUrlReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */