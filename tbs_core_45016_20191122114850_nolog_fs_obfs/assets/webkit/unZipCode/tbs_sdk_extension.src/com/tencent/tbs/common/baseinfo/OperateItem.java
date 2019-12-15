package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class OperateItem
  extends JceStruct
{
  static byte[] cache_businessPrivateInfo;
  static OperateCommonInfo cache_commonInfo = new OperateCommonInfo();
  public byte[] businessPrivateInfo = null;
  public OperateCommonInfo commonInfo = null;
  
  static
  {
    cache_businessPrivateInfo = (byte[])new byte[1];
    ((byte[])cache_businessPrivateInfo)[0] = 0;
  }
  
  public OperateItem() {}
  
  public OperateItem(OperateCommonInfo paramOperateCommonInfo, byte[] paramArrayOfByte)
  {
    this.commonInfo = paramOperateCommonInfo;
    this.businessPrivateInfo = paramArrayOfByte;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.commonInfo = ((OperateCommonInfo)paramJceInputStream.read(cache_commonInfo, 0, false));
    this.businessPrivateInfo = ((byte[])paramJceInputStream.read(cache_businessPrivateInfo, 1, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.commonInfo;
    if (localObject != null) {
      paramJceOutputStream.write((JceStruct)localObject, 0);
    }
    localObject = this.businessPrivateInfo;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 1);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\OperateItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */