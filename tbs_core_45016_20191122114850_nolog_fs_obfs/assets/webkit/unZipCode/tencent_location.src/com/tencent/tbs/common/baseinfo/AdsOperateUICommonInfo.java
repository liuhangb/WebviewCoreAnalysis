package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class AdsOperateUICommonInfo
  extends JceStruct
{
  public int iContentType = 0;
  public int iResouceSize = 0;
  public String sAdId = "";
  public String sAdName = "";
  public String sDesc = "";
  public String sImageUrl = "";
  public String sLinkUrl = "";
  public String sWording = "";
  
  public AdsOperateUICommonInfo() {}
  
  public AdsOperateUICommonInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, String paramString6)
  {
    this.sAdId = paramString1;
    this.sAdName = paramString2;
    this.sImageUrl = paramString3;
    this.sLinkUrl = paramString4;
    this.sWording = paramString5;
    this.iResouceSize = paramInt1;
    this.iContentType = paramInt2;
    this.sDesc = paramString6;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sAdId = paramJceInputStream.readString(0, false);
    this.sAdName = paramJceInputStream.readString(1, false);
    this.sImageUrl = paramJceInputStream.readString(2, false);
    this.sLinkUrl = paramJceInputStream.readString(3, false);
    this.sWording = paramJceInputStream.readString(4, false);
    this.iResouceSize = paramJceInputStream.read(this.iResouceSize, 5, false);
    this.iContentType = paramJceInputStream.read(this.iContentType, 6, false);
    this.sDesc = paramJceInputStream.readString(7, false);
  }
  
  public String toString()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("sAdId is ");
    ((StringBuilder)localObject).append(this.sAdId);
    ((StringBuilder)localObject).append(" sAdName is ");
    ((StringBuilder)localObject).append(this.sAdName);
    ((StringBuilder)localObject).append(" sImageUrl is ");
    ((StringBuilder)localObject).append(this.sImageUrl);
    ((StringBuilder)localObject).append(" sLinkUrl is ");
    ((StringBuilder)localObject).append(this.sLinkUrl);
    localObject = ((StringBuilder)localObject).toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(" sWording is ");
    localStringBuilder.append(this.sWording);
    localStringBuilder.append(" sDesc is ");
    localStringBuilder.append(this.sDesc);
    return localStringBuilder.toString();
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sAdId;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    str = this.sAdName;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
    str = this.sImageUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    str = this.sLinkUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 3);
    }
    str = this.sWording;
    if (str != null) {
      paramJceOutputStream.write(str, 4);
    }
    paramJceOutputStream.write(this.iResouceSize, 5);
    paramJceOutputStream.write(this.iContentType, 6);
    str = this.sDesc;
    if (str != null) {
      paramJceOutputStream.write(str, 7);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\AdsOperateUICommonInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */