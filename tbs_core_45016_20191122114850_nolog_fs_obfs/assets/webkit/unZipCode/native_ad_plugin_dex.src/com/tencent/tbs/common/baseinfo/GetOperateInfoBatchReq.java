package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;

public final class GetOperateInfoBatchReq
  extends JceStruct
{
  static ArrayList<GetOperateReqItem> cache_reqItems;
  static OperateUserInfo cache_userInfo = new OperateUserInfo();
  public ArrayList<GetOperateReqItem> reqItems = null;
  public OperateUserInfo userInfo = null;
  
  static
  {
    cache_reqItems = new ArrayList();
    GetOperateReqItem localGetOperateReqItem = new GetOperateReqItem();
    cache_reqItems.add(localGetOperateReqItem);
  }
  
  public GetOperateInfoBatchReq() {}
  
  public GetOperateInfoBatchReq(OperateUserInfo paramOperateUserInfo, ArrayList<GetOperateReqItem> paramArrayList)
  {
    this.userInfo = paramOperateUserInfo;
    this.reqItems = paramArrayList;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.userInfo = ((OperateUserInfo)paramJceInputStream.read(cache_userInfo, 0, true));
    this.reqItems = ((ArrayList)paramJceInputStream.read(cache_reqItems, 1, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.userInfo, 0);
    ArrayList localArrayList = this.reqItems;
    if (localArrayList != null) {
      paramJceOutputStream.write(localArrayList, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\GetOperateInfoBatchReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */