package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;

public final class SmartBox_GetVideoInfoReq
  extends JceStruct
  implements Cloneable
{
  static ArrayList<SmartBox_PicInfo> cache_vecPicInfo = new ArrayList();
  public String sDomain = "";
  public String sGuid = "";
  public String sQua2 = "";
  public String sUrl = "";
  public ArrayList<SmartBox_PicInfo> vecPicInfo = null;
  
  static
  {
    SmartBox_PicInfo localSmartBox_PicInfo = new SmartBox_PicInfo();
    cache_vecPicInfo.add(localSmartBox_PicInfo);
  }
  
  public SmartBox_GetVideoInfoReq() {}
  
  public SmartBox_GetVideoInfoReq(String paramString1, String paramString2, String paramString3, ArrayList<SmartBox_PicInfo> paramArrayList, String paramString4)
  {
    this.sGuid = paramString1;
    this.sQua2 = paramString2;
    this.sUrl = paramString3;
    this.vecPicInfo = paramArrayList;
    this.sDomain = paramString4;
  }
  
  public Object clone()
  {
    try
    {
      Object localObject = super.clone();
      return localObject;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      for (;;) {}
    }
    return null;
  }
  
  public String getSDomain()
  {
    return this.sDomain;
  }
  
  public String getSGuid()
  {
    return this.sGuid;
  }
  
  public String getSQua2()
  {
    return this.sQua2;
  }
  
  public String getSUrl()
  {
    return this.sUrl;
  }
  
  public ArrayList<SmartBox_PicInfo> getVecPicInfo()
  {
    return this.vecPicInfo;
  }
  
  public int hashCode()
  {
    try
    {
      throw new Exception("Need define key first!");
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return 0;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sGuid = paramJceInputStream.readString(0, false);
    this.sQua2 = paramJceInputStream.readString(1, false);
    this.sUrl = paramJceInputStream.readString(2, false);
    this.vecPicInfo = ((ArrayList)paramJceInputStream.read(cache_vecPicInfo, 3, false));
    this.sDomain = paramJceInputStream.readString(4, false);
  }
  
  public void setSDomain(String paramString)
  {
    this.sDomain = paramString;
  }
  
  public void setSGuid(String paramString)
  {
    this.sGuid = paramString;
  }
  
  public void setSQua2(String paramString)
  {
    this.sQua2 = paramString;
  }
  
  public void setSUrl(String paramString)
  {
    this.sUrl = paramString;
  }
  
  public void setVecPicInfo(ArrayList<SmartBox_PicInfo> paramArrayList)
  {
    this.vecPicInfo = paramArrayList;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.sGuid;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 0);
    }
    localObject = this.sQua2;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.sUrl;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    localObject = this.vecPicInfo;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 3);
    }
    localObject = this.sDomain;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 4);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\SmartBox_GetVideoInfoReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */