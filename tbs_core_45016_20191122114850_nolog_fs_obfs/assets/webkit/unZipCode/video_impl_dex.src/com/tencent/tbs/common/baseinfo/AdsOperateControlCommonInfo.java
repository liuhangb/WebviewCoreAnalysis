package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class AdsOperateControlCommonInfo
  extends JceStruct
{
  static Map<Integer, ArrayList<String>> cache_mStatUrl = new HashMap();
  public int iShowInterval = 0;
  public int iShowNum = 1;
  public int iShowSecond = 6;
  public Map<Integer, ArrayList<String>> mStatUrl = null;
  public String sStatCommonInfo = "";
  
  static
  {
    ArrayList localArrayList = new ArrayList();
    localArrayList.add("");
    cache_mStatUrl.put(Integer.valueOf(0), localArrayList);
  }
  
  public AdsOperateControlCommonInfo() {}
  
  public AdsOperateControlCommonInfo(int paramInt1, int paramInt2, int paramInt3, String paramString, Map<Integer, ArrayList<String>> paramMap)
  {
    this.iShowNum = paramInt1;
    this.iShowSecond = paramInt2;
    this.iShowInterval = paramInt3;
    this.sStatCommonInfo = paramString;
    this.mStatUrl = paramMap;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iShowNum = paramJceInputStream.read(this.iShowNum, 0, false);
    this.iShowSecond = paramJceInputStream.read(this.iShowSecond, 1, false);
    this.iShowInterval = paramJceInputStream.read(this.iShowInterval, 2, false);
    this.sStatCommonInfo = paramJceInputStream.readString(3, false);
    this.mStatUrl = ((Map)paramJceInputStream.read(cache_mStatUrl, 4, false));
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("iShowNum is ");
    localStringBuilder.append(this.iShowNum);
    localStringBuilder.append(" iShowSecond is ");
    localStringBuilder.append(this.iShowSecond);
    localStringBuilder.append(" iShowInterval is ");
    localStringBuilder.append(this.iShowInterval);
    return localStringBuilder.toString();
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iShowNum, 0);
    paramJceOutputStream.write(this.iShowSecond, 1);
    paramJceOutputStream.write(this.iShowInterval, 2);
    Object localObject = this.sStatCommonInfo;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 3);
    }
    localObject = this.mStatUrl;
    if (localObject != null) {
      paramJceOutputStream.write((Map)localObject, 4);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\AdsOperateControlCommonInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */