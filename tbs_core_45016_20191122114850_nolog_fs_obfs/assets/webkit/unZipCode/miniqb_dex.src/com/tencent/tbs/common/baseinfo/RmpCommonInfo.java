package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class RmpCommonInfo
  extends JceStruct
{
  public boolean check = false;
  public int checkInterval = 600;
  public int downloadTime = 0;
  public int effectiveTime = 0;
  public boolean freqControl = false;
  public int invalidTime = 0;
  public String md5 = "";
  public int priority = 0;
  public int sourceId = 0;
  public int sourceSize = 0;
  public int sourceType = 0;
  
  public RmpCommonInfo() {}
  
  public RmpCommonInfo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString, boolean paramBoolean1, int paramInt5, int paramInt6, boolean paramBoolean2, int paramInt7, int paramInt8)
  {
    this.sourceId = paramInt1;
    this.sourceType = paramInt2;
    this.effectiveTime = paramInt3;
    this.invalidTime = paramInt4;
    this.md5 = paramString;
    this.check = paramBoolean1;
    this.checkInterval = paramInt5;
    this.downloadTime = paramInt6;
    this.freqControl = paramBoolean2;
    this.sourceSize = paramInt7;
    this.priority = paramInt8;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sourceId = paramJceInputStream.read(this.sourceId, 0, false);
    this.sourceType = paramJceInputStream.read(this.sourceType, 1, false);
    this.effectiveTime = paramJceInputStream.read(this.effectiveTime, 2, false);
    this.invalidTime = paramJceInputStream.read(this.invalidTime, 3, false);
    this.md5 = paramJceInputStream.readString(4, false);
    this.check = paramJceInputStream.read(this.check, 5, false);
    this.checkInterval = paramJceInputStream.read(this.checkInterval, 6, false);
    this.downloadTime = paramJceInputStream.read(this.downloadTime, 7, false);
    this.freqControl = paramJceInputStream.read(this.freqControl, 8, false);
    this.sourceSize = paramJceInputStream.read(this.sourceSize, 9, false);
    this.priority = paramJceInputStream.read(this.priority, 10, false);
  }
  
  public String toString()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("sourceId is ");
    ((StringBuilder)localObject).append(this.sourceId);
    ((StringBuilder)localObject).append(" sourceType is ");
    ((StringBuilder)localObject).append(this.sourceType);
    ((StringBuilder)localObject).append(" effectiveTime is ");
    ((StringBuilder)localObject).append(this.effectiveTime);
    ((StringBuilder)localObject).append(" invalidTime is ");
    ((StringBuilder)localObject).append(this.invalidTime);
    localObject = ((StringBuilder)localObject).toString();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(" md5 is ");
    localStringBuilder.append(this.md5);
    localStringBuilder.append(" check is ");
    localStringBuilder.append(this.check);
    localStringBuilder.append(" checkInterval is ");
    localStringBuilder.append(this.checkInterval);
    localStringBuilder.append(" downloadTime is ");
    localStringBuilder.append(this.downloadTime);
    localObject = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(" freqControl is ");
    localStringBuilder.append(this.freqControl);
    localStringBuilder.append(" sourceSize is ");
    localStringBuilder.append(this.sourceSize);
    localStringBuilder.append(" priority is ");
    localStringBuilder.append(this.priority);
    return localStringBuilder.toString();
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.sourceId, 0);
    paramJceOutputStream.write(this.sourceType, 1);
    paramJceOutputStream.write(this.effectiveTime, 2);
    paramJceOutputStream.write(this.invalidTime, 3);
    String str = this.md5;
    if (str != null) {
      paramJceOutputStream.write(str, 4);
    }
    paramJceOutputStream.write(this.check, 5);
    paramJceOutputStream.write(this.checkInterval, 6);
    paramJceOutputStream.write(this.downloadTime, 7);
    paramJceOutputStream.write(this.freqControl, 8);
    paramJceOutputStream.write(this.sourceSize, 9);
    paramJceOutputStream.write(this.priority, 10);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\RmpCommonInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */