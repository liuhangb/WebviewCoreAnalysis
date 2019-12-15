package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;
import java.util.ArrayList;
import java.util.Collection;

public final class SmartBox_GetVideoInfoRsp
  extends JceStruct
  implements Cloneable
{
  static ArrayList<SmartBox_VideoInfo> cache_vecVideoInfo = new ArrayList();
  public int iRetCode = 0;
  public String sCue = "";
  public String sErrMsg = "";
  public String sShorterCue = "";
  public String sUrl = "";
  public ArrayList<SmartBox_VideoInfo> vecVideoInfo = null;
  
  static
  {
    SmartBox_VideoInfo localSmartBox_VideoInfo = new SmartBox_VideoInfo();
    cache_vecVideoInfo.add(localSmartBox_VideoInfo);
  }
  
  public SmartBox_GetVideoInfoRsp() {}
  
  public SmartBox_GetVideoInfoRsp(int paramInt, String paramString1, String paramString2, ArrayList<SmartBox_VideoInfo> paramArrayList, String paramString3)
  {
    this.iRetCode = paramInt;
    this.sErrMsg = paramString1;
    this.sUrl = paramString2;
    this.vecVideoInfo = paramArrayList;
    this.sCue = paramString3;
    this.sShorterCue = this.sShorterCue;
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
  
  public int getIRetCode()
  {
    return this.iRetCode;
  }
  
  public String getSCue()
  {
    return this.sCue;
  }
  
  public String getSErrMsg()
  {
    return this.sErrMsg;
  }
  
  public String getSShorterCue()
  {
    return this.sShorterCue;
  }
  
  public String getSUrl()
  {
    return this.sUrl;
  }
  
  public ArrayList<SmartBox_VideoInfo> getVecVideoInfo()
  {
    return this.vecVideoInfo;
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
    this.iRetCode = paramJceInputStream.read(this.iRetCode, 0, false);
    this.sErrMsg = paramJceInputStream.readString(1, false);
    this.sUrl = paramJceInputStream.readString(2, false);
    this.vecVideoInfo = ((ArrayList)paramJceInputStream.read(cache_vecVideoInfo, 3, false));
    this.sCue = paramJceInputStream.readString(4, false);
    this.sShorterCue = paramJceInputStream.readString(5, false);
  }
  
  public void setIRetCode(int paramInt)
  {
    this.iRetCode = paramInt;
  }
  
  public void setSCue(String paramString)
  {
    this.sCue = paramString;
  }
  
  public void setSErrMsg(String paramString)
  {
    this.sErrMsg = paramString;
  }
  
  public void setSShorterCue(String paramString)
  {
    this.sShorterCue = paramString;
  }
  
  public void setSUrl(String paramString)
  {
    this.sUrl = paramString;
  }
  
  public void setVecVideoInfo(ArrayList<SmartBox_VideoInfo> paramArrayList)
  {
    this.vecVideoInfo = paramArrayList;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iRetCode, 0);
    Object localObject = this.sErrMsg;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 1);
    }
    localObject = this.sUrl;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    localObject = this.vecVideoInfo;
    if (localObject != null) {
      paramJceOutputStream.write((Collection)localObject, 3);
    }
    localObject = this.sCue;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 4);
    }
    localObject = this.sShorterCue;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 5);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\SmartBox_GetVideoInfoRsp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */