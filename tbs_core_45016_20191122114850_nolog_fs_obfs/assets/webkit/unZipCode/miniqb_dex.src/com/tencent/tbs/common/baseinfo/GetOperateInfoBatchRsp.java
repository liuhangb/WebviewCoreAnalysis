package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.HashMap;
import java.util.Map;

public final class GetOperateInfoBatchRsp
  extends JceStruct
{
  static Map<Integer, Integer> cache_ret = new HashMap();
  static Map<Integer, UserOperateItemBatch> cache_sourceBatch;
  public Map<Integer, Integer> ret = null;
  public Map<Integer, UserOperateItemBatch> sourceBatch = null;
  
  static
  {
    cache_ret.put(Integer.valueOf(0), Integer.valueOf(0));
    cache_sourceBatch = new HashMap();
    UserOperateItemBatch localUserOperateItemBatch = new UserOperateItemBatch();
    cache_sourceBatch.put(Integer.valueOf(0), localUserOperateItemBatch);
  }
  
  public GetOperateInfoBatchRsp() {}
  
  public GetOperateInfoBatchRsp(Map<Integer, Integer> paramMap, Map<Integer, UserOperateItemBatch> paramMap1)
  {
    this.ret = paramMap;
    this.sourceBatch = paramMap1;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.ret = ((Map)paramJceInputStream.read(cache_ret, 0, true));
    this.sourceBatch = ((Map)paramJceInputStream.read(cache_sourceBatch, 1, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.ret, 0);
    Map localMap = this.sourceBatch;
    if (localMap != null) {
      paramJceOutputStream.write(localMap, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\GetOperateInfoBatchRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */