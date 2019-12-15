package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class SmartBox_PicInfo
  extends JceStruct
  implements Cloneable
{
  public int iHeight = 0;
  public int iIndex = 0;
  public int iWidth = 0;
  public String sMd5 = "";
  public String sUrl = "";
  
  public SmartBox_PicInfo() {}
  
  public SmartBox_PicInfo(String paramString1, int paramInt1, String paramString2, int paramInt2, int paramInt3)
  {
    this.sMd5 = paramString1;
    this.iIndex = paramInt1;
    this.sUrl = paramString2;
    this.iHeight = paramInt2;
    this.iWidth = paramInt3;
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
  
  public int getIHeight()
  {
    return this.iHeight;
  }
  
  public int getIIndex()
  {
    return this.iIndex;
  }
  
  public int getIWidth()
  {
    return this.iWidth;
  }
  
  public String getSMd5()
  {
    return this.sMd5;
  }
  
  public String getSUrl()
  {
    return this.sUrl;
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
    this.iHeight = paramJceInputStream.read(this.iHeight, 3, false);
    this.iWidth = paramJceInputStream.read(this.iWidth, 4, false);
  }
  
  public void setIHeight(int paramInt)
  {
    this.iHeight = paramInt;
  }
  
  public void setIIndex(int paramInt)
  {
    this.iIndex = paramInt;
  }
  
  public void setIWidth(int paramInt)
  {
    this.iWidth = paramInt;
  }
  
  public void setSMd5(String paramString)
  {
    this.sMd5 = paramString;
  }
  
  public void setSUrl(String paramString)
  {
    this.sUrl = paramString;
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
    paramJceOutputStream.write(this.iHeight, 3);
    paramJceOutputStream.write(this.iWidth, 4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\SmartBox_PicInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */