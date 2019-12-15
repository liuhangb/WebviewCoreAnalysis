package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.HashMap;
import java.util.Map;

public final class GetOperateReqItem
  extends JceStruct
{
  static Map<String, String> cache_extraInfo;
  static Map<Integer, String> cache_md5Info = new HashMap();
  public Map<String, String> extraInfo = null;
  public boolean forcePull = false;
  public Map<Integer, String> md5Info = null;
  public int sourceType = 0;
  
  static
  {
    cache_md5Info.put(Integer.valueOf(0), "");
    cache_extraInfo = new HashMap();
    cache_extraInfo.put("", "");
  }
  
  public GetOperateReqItem() {}
  
  public GetOperateReqItem(int paramInt, Map<Integer, String> paramMap, boolean paramBoolean, Map<String, String> paramMap1)
  {
    this.sourceType = paramInt;
    this.md5Info = paramMap;
    this.forcePull = paramBoolean;
    this.extraInfo = paramMap1;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sourceType = paramJceInputStream.read(this.sourceType, 0, false);
    this.md5Info = ((Map)paramJceInputStream.read(cache_md5Info, 1, false));
    this.forcePull = paramJceInputStream.read(this.forcePull, 2, false);
    this.extraInfo = ((Map)paramJceInputStream.read(cache_extraInfo, 3, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sourceType, 0);
    Map localMap = this.md5Info;
    if (localMap != null) {
      paramJceOutputStream.write(localMap, 1);
    }
    paramJceOutputStream.write(this.forcePull, 2);
    localMap = this.extraInfo;
    if (localMap != null) {
      paramJceOutputStream.write(localMap, 3);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\GetOperateReqItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */