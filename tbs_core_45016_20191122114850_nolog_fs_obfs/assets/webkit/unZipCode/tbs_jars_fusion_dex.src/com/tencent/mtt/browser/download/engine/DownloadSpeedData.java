package com.tencent.mtt.browser.download.engine;

public class DownloadSpeedData
{
  public static final String DATA_SIZE = "datasize";
  public static final String RECEIVCE_TIME = "receivetime";
  public long mDataSize;
  public long mReceiveTime;
  
  public DownloadSpeedData(long paramLong1, long paramLong2)
  {
    this.mDataSize = paramLong1;
    this.mReceiveTime = paramLong2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\browser\download\engine\DownloadSpeedData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */