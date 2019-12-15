package com.tencent.tbs.common.download.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public final class ChannelUrlDetectReq
  extends JceStruct
{
  static DistReqHeader cache_stHeader;
  public int iFromTBS = 0;
  public String referer = "";
  public String segmentMd5 = "";
  public int segmentSize = 0;
  public DistReqHeader stHeader = null;
  public String url = "";
  public int versionCode = 0;
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    if (cache_stHeader == null) {
      cache_stHeader = new DistReqHeader();
    }
    this.stHeader = ((DistReqHeader)paramJceInputStream.read(cache_stHeader, 0, false));
    this.segmentSize = paramJceInputStream.read(this.segmentSize, 1, false);
    this.segmentMd5 = paramJceInputStream.readString(2, false);
    this.referer = paramJceInputStream.readString(3, false);
    this.url = paramJceInputStream.readString(4, false);
    this.versionCode = paramJceInputStream.read(this.versionCode, 5, false);
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    Object localObject = this.stHeader;
    if (localObject != null) {
      paramJceOutputStream.write((JceStruct)localObject, 0);
    }
    paramJceOutputStream.write(this.segmentSize, 1);
    localObject = this.segmentMd5;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 2);
    }
    localObject = this.referer;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 3);
    }
    localObject = this.url;
    if (localObject != null) {
      paramJceOutputStream.write((String)localObject, 4);
    }
    paramJceOutputStream.write(this.versionCode, 5);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\download\MTT\ChannelUrlDetectReq.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */