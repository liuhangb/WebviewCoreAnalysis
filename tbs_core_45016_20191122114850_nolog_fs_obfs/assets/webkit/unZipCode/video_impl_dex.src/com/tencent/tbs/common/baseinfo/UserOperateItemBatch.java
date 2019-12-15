package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.HashMap;
import java.util.Map;

public final class UserOperateItemBatch
  extends JceStruct
{
  static Map<Integer, OperateItem> cache_sourceItems = new HashMap();
  static Map<Integer, Integer> cache_sourceState;
  public Map<Integer, OperateItem> sourceItems = null;
  public Map<Integer, Integer> sourceState = null;
  public int sourceType = 0;
  
  static
  {
    OperateItem localOperateItem = new OperateItem();
    cache_sourceItems.put(Integer.valueOf(0), localOperateItem);
    cache_sourceState = new HashMap();
    cache_sourceState.put(Integer.valueOf(0), Integer.valueOf(0));
  }
  
  public UserOperateItemBatch() {}
  
  public UserOperateItemBatch(int paramInt, Map<Integer, OperateItem> paramMap, Map<Integer, Integer> paramMap1)
  {
    this.sourceType = paramInt;
    this.sourceItems = paramMap;
    this.sourceState = paramMap1;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sourceType = paramJceInputStream.read(this.sourceType, 0, true);
    this.sourceItems = ((Map)paramJceInputStream.read(cache_sourceItems, 1, false));
    this.sourceState = ((Map)paramJceInputStream.read(cache_sourceState, 2, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sourceType, 0);
    Map localMap = this.sourceItems;
    if (localMap != null) {
      paramJceOutputStream.write(localMap, 1);
    }
    localMap = this.sourceState;
    if (localMap != null) {
      paramJceOutputStream.write(localMap, 2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\UserOperateItemBatch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */