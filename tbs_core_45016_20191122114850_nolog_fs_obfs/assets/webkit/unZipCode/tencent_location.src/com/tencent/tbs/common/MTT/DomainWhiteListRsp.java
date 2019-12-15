package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class DomainWhiteListRsp
  extends JceStruct
  implements Cloneable
{
  static Map<Integer, ArrayList<String>> cache_mTypeDomain;
  public int iDomainTime = 0;
  public Map<Integer, ArrayList<String>> mTypeDomain = null;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iDomainTime = paramJceInputStream.read(this.iDomainTime, 0, true);
    if (cache_mTypeDomain == null)
    {
      cache_mTypeDomain = new HashMap();
      ArrayList localArrayList = new ArrayList();
      localArrayList.add("");
      cache_mTypeDomain.put(Integer.valueOf(0), localArrayList);
    }
    this.mTypeDomain = ((Map)paramJceInputStream.read(cache_mTypeDomain, 1, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iDomainTime, 0);
    Map localMap = this.mTypeDomain;
    if (localMap != null) {
      paramJceOutputStream.write(localMap, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\DomainWhiteListRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */