package com.tencent.tbs.common.baseinfo;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class RmpPosData
  extends JceStruct
{
  static int cache_eRMPPosType;
  static RmpCommonInfo cache_stCommonInfo = new RmpCommonInfo();
  static AdsOperateControlCommonInfo cache_stControlInfo;
  static AdsOperateUICommonInfo cache_stUIInfo = new AdsOperateUICommonInfo();
  static byte[] cache_vPosData;
  public int eRMPPosType = 0;
  public RmpCommonInfo stCommonInfo = null;
  public AdsOperateControlCommonInfo stControlInfo = null;
  public AdsOperateUICommonInfo stUIInfo = null;
  public byte[] vPosData = null;
  
  static
  {
    cache_stControlInfo = new AdsOperateControlCommonInfo();
    cache_vPosData = (byte[])new byte[1];
    ((byte[])cache_vPosData)[0] = 0;
  }
  
  public RmpPosData() {}
  
  public RmpPosData(int paramInt, RmpCommonInfo paramRmpCommonInfo, AdsOperateUICommonInfo paramAdsOperateUICommonInfo, AdsOperateControlCommonInfo paramAdsOperateControlCommonInfo, byte[] paramArrayOfByte)
  {
    this.eRMPPosType = paramInt;
    this.stCommonInfo = paramRmpCommonInfo;
    this.stUIInfo = paramAdsOperateUICommonInfo;
    this.stControlInfo = paramAdsOperateControlCommonInfo;
    this.vPosData = paramArrayOfByte;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.eRMPPosType = paramJceInputStream.read(this.eRMPPosType, 0, false);
    this.stCommonInfo = ((RmpCommonInfo)paramJceInputStream.read(cache_stCommonInfo, 1, false));
    this.stUIInfo = ((AdsOperateUICommonInfo)paramJceInputStream.read(cache_stUIInfo, 2, false));
    this.stControlInfo = ((AdsOperateControlCommonInfo)paramJceInputStream.read(cache_stControlInfo, 3, false));
    this.vPosData = ((byte[])paramJceInputStream.read(cache_vPosData, 4, false));
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    paramJceOutputStream.write(this.eRMPPosType, 0);
    Object localObject = this.stCommonInfo;
    if (localObject != null) {
      paramJceOutputStream.write((JceStruct)localObject, 1);
    }
    localObject = this.stUIInfo;
    if (localObject != null) {
      paramJceOutputStream.write((JceStruct)localObject, 2);
    }
    localObject = this.stControlInfo;
    if (localObject != null) {
      paramJceOutputStream.write((JceStruct)localObject, 3);
    }
    localObject = this.vPosData;
    if (localObject != null) {
      paramJceOutputStream.write((byte[])localObject, 4);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\RmpPosData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */