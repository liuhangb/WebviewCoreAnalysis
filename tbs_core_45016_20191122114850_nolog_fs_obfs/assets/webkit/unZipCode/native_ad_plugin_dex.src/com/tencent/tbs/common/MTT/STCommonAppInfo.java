package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public final class STCommonAppInfo
  extends JceStruct
{
  static ArrayList<String> cache_vData;
  public String sAppKey = "";
  public ArrayList<String> vData = new ArrayList();
  
  public STCommonAppInfo() {}
  
  public STCommonAppInfo(String paramString, ArrayList<String> paramArrayList)
  {
    this.sAppKey = paramString;
    this.vData = paramArrayList;
  }
  
  public void put(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString1.length() > 0))
    {
      if (paramString2 == null) {
        return;
      }
      ArrayList localArrayList = this.vData;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append("=");
      localStringBuilder.append(paramString2);
      localArrayList.add(localStringBuilder.toString());
      return;
    }
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sAppKey = paramJceInputStream.readString(0, false);
    if (cache_vData == null)
    {
      cache_vData = new ArrayList();
      cache_vData.add("");
    }
    this.vData = ((ArrayList)paramJceInputStream.read(cache_vData, 1, false));
  }
  
  public String toString()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append(this.sAppKey);
    ((StringBuilder)localObject).append("= &&& ");
    localObject = ((StringBuilder)localObject).toString();
    Iterator localIterator = this.vData.iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append((String)localObject);
      localStringBuilder.append("&");
      localStringBuilder.append(str);
      localObject = localStringBuilder.toString();
    }
    return (String)localObject;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.sAppKey;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 0);
    }
    localObject = this.vData;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\STCommonAppInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */