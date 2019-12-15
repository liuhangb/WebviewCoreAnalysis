package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class PWItemData
  extends JceStruct
{
  public long iTypeId = 0L;
  public long iUiId = 0L;
  public String sData = "";
  public String sUiAlias = "";
  
  public PWItemData() {}
  
  public PWItemData(long paramLong1, long paramLong2, String paramString1, String paramString2)
  {
    this.iTypeId = paramLong1;
    this.iUiId = paramLong2;
    this.sUiAlias = paramString1;
    this.sData = paramString2;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.iTypeId = paramJceInputStream.read(this.iTypeId, 0, true);
    this.iUiId = paramJceInputStream.read(this.iUiId, 1, true);
    this.sUiAlias = paramJceInputStream.readString(2, false);
    this.sData = paramJceInputStream.readString(3, true);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.iTypeId, 0);
    paramJceOutputStream.write(this.iUiId, 1);
    String str = this.sUiAlias;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    paramJceOutputStream.write(this.sData, 3);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PWItemData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */