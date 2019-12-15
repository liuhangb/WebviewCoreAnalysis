package com.tencent.tbs.common.MTT;

import android.text.TextUtils;
import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class CommStatData
  extends JceStruct
{
  static ArrayList<String> cache_vData;
  public int iPv = 1;
  public byte mDataOp = 2;
  public String sAppKey = "";
  public String sStatKey = "";
  private ArrayList<String> vData = new ArrayList();
  
  public CommStatData() {}
  
  public CommStatData(String paramString, ArrayList<String> paramArrayList)
  {
    this.sAppKey = paramString;
    this.vData = paramArrayList;
  }
  
  public static CommStatData fromHashMap(String paramString, HashMap<String, String> paramHashMap)
  {
    CommStatData localCommStatData = new CommStatData();
    localCommStatData.sAppKey = paramString;
    if (paramHashMap != null)
    {
      paramString = new HashMap(paramHashMap);
      paramHashMap = paramHashMap.keySet().iterator();
      while (paramHashMap.hasNext())
      {
        String str = (String)paramHashMap.next();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append(str);
        localStringBuilder.append("=");
        localStringBuilder.append((String)paramString.get(str));
        str = localStringBuilder.toString();
        localCommStatData.vData.add(str);
      }
    }
    return localCommStatData;
  }
  
  public void put(String paramString1, String paramString2)
  {
    if (paramString1 != null)
    {
      if (paramString1.length() <= 0) {
        return;
      }
      String str = paramString2;
      if (paramString2 == null) {
        str = "NULL";
      }
      paramString2 = this.vData;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString1);
      localStringBuilder.append("=");
      localStringBuilder.append(str);
      paramString2.add(localStringBuilder.toString());
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
    this.mDataOp = paramJceInputStream.read(this.mDataOp, 2, false);
    this.sStatKey = paramJceInputStream.readString(3, false);
    this.iPv = paramJceInputStream.read(this.iPv, 4, false);
  }
  
  public STCommonAppInfo toCommonAppInfo()
  {
    STCommonAppInfo localSTCommonAppInfo = new STCommonAppInfo();
    localSTCommonAppInfo.sAppKey = this.sAppKey;
    localSTCommonAppInfo.vData = this.vData;
    if (!TextUtils.isEmpty(this.sStatKey))
    {
      ArrayList localArrayList = localSTCommonAppInfo.vData;
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("PV=");
      localStringBuilder.append(this.iPv);
      localArrayList.add(localStringBuilder.toString());
    }
    return localSTCommonAppInfo;
  }
  
  public HashMap<String, String> toHashMap()
  {
    HashMap localHashMap = new HashMap();
    Object localObject = this.vData;
    if (localObject != null)
    {
      if (((ArrayList)localObject).size() <= 0) {
        return localHashMap;
      }
      int i = 0;
      while (i < this.vData.size())
      {
        localObject = (String)this.vData.get(i);
        if ((localObject != null) && (((String)localObject).length() > 0))
        {
          String[] arrayOfString = ((String)localObject).split("=");
          if ((arrayOfString != null) && (arrayOfString.length > 1))
          {
            int j = arrayOfString[0].length();
            localHashMap.put(arrayOfString[0], ((String)localObject).substring(j + 1, ((String)localObject).length()));
          }
        }
        i += 1;
      }
      return localHashMap;
    }
    return localHashMap;
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
    paramJceOutputStream.write(this.mDataOp, 2);
    localObject = this.sStatKey;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 3);
    }
    paramJceOutputStream.write(this.iPv, 4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\CommStatData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */