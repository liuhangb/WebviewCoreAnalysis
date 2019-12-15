package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class SmartBox_VideoInfo
  extends JceStruct
  implements Cloneable
{
  public int iIndex = 0;
  public String sDuration = "";
  public String sIcon = "";
  public String sJumpUrl = "";
  public String sMd5 = "";
  public String sTitle = "";
  public String sUrl = "";
  public String sVid = "";
  
  public SmartBox_VideoInfo() {}
  
  public SmartBox_VideoInfo(String paramString1, int paramInt, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.sMd5 = paramString1;
    this.iIndex = paramInt;
    this.sUrl = paramString2;
    this.sTitle = paramString3;
    this.sVid = paramString4;
    this.sDuration = paramString5;
    this.sIcon = paramString6;
    this.sJumpUrl = paramString7;
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
  
  public int getIIndex()
  {
    return this.iIndex;
  }
  
  public String getSDuration()
  {
    return this.sDuration;
  }
  
  public String getSIcon()
  {
    return this.sIcon;
  }
  
  public String getSJumpUrl()
  {
    return this.sJumpUrl;
  }
  
  public String getSMd5()
  {
    return this.sMd5;
  }
  
  public String getSTitle()
  {
    return this.sTitle;
  }
  
  public String getSUrl()
  {
    return this.sUrl;
  }
  
  public String getSVid()
  {
    return this.sVid;
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
    this.sMd5 = paramJceInputStream.readString(0, false);
    this.iIndex = paramJceInputStream.read(this.iIndex, 1, false);
    this.sUrl = paramJceInputStream.readString(2, false);
    this.sTitle = paramJceInputStream.readString(3, false);
    this.sVid = paramJceInputStream.readString(4, false);
    this.sDuration = paramJceInputStream.readString(5, false);
    this.sIcon = paramJceInputStream.readString(6, false);
    this.sJumpUrl = paramJceInputStream.readString(7, false);
  }
  
  public void setIIndex(int paramInt)
  {
    this.iIndex = paramInt;
  }
  
  public void setSDuration(String paramString)
  {
    this.sDuration = paramString;
  }
  
  public void setSIcon(String paramString)
  {
    this.sIcon = paramString;
  }
  
  public void setSJumpUrl(String paramString)
  {
    this.sJumpUrl = paramString;
  }
  
  public void setSMd5(String paramString)
  {
    this.sMd5 = paramString;
  }
  
  public void setSTitle(String paramString)
  {
    this.sTitle = paramString;
  }
  
  public void setSUrl(String paramString)
  {
    this.sUrl = paramString;
  }
  
  public void setSVid(String paramString)
  {
    this.sVid = paramString;
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sMd5;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    paramJceOutputStream.write(this.iIndex, 1);
    str = this.sUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    str = this.sTitle;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
    str = this.sVid;
    if (str != null) {
      paramJceOutputStream.write(str, 4);
    }
    str = this.sDuration;
    if (str != null) {
      paramJceOutputStream.write(str, 5);
    }
    str = this.sIcon;
    if (str != null) {
      paramJceOutputStream.write(str, 6);
    }
    str = this.sJumpUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 7);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\SmartBox_VideoInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */