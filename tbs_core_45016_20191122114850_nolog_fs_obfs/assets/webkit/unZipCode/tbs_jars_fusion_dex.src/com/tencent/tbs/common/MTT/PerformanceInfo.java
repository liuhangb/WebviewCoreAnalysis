package com.tencent.tbs.common.MTT;

import com.taf.JceInputStream;
import com.taf.JceOutputStream;
import com.taf.JceStruct;

public class PerformanceInfo
  extends JceStruct
{
  public int iAvgConnectTime;
  public int iAvgDNSTime;
  public int iAvgSpeed;
  public int iAvgWaitRspTime;
  public int iHTTPRequestCount;
  public int iMainResDownloadTime;
  public int iMainResouceCostTime;
  public int iMainResourceSize;
  public int iPageVisitCount;
  public byte iProxyType;
  public int iSubResouceCostTime;
  public int iSubResourceSize;
  public int iTotalTime;
  public String sAPN = "";
  public String sDevice = "";
  public String sProxyIP;
  public String sRemoteIP;
  public String sUrl = "";
  private long tConnectTime;
  private long tConnectTimes;
  private long tDNSTime;
  private long tDNSTimes;
  private long tMainResDownloadTime;
  private long tMainResDownloadTimes;
  private long tTotalHttpBytes;
  private long tTotalHttptime;
  private long tTotalTime;
  private long tTotalTimes;
  private long tWaitRspTime;
  private long tWaitRspTimes;
  
  public void addValues(PerformanceInfo paramPerformanceInfo)
  {
    if (paramPerformanceInfo == null) {
      return;
    }
    this.tTotalTime += paramPerformanceInfo.tTotalTimes;
    this.tMainResDownloadTime += paramPerformanceInfo.tMainResDownloadTime;
    this.tMainResDownloadTimes += paramPerformanceInfo.tMainResDownloadTimes;
    this.tDNSTime += paramPerformanceInfo.tDNSTime;
    this.tDNSTimes += paramPerformanceInfo.tDNSTimes;
    this.tConnectTime += paramPerformanceInfo.tConnectTime;
    this.tConnectTimes += paramPerformanceInfo.tConnectTimes;
    this.tWaitRspTime += paramPerformanceInfo.tWaitRspTime;
    this.tWaitRspTimes += paramPerformanceInfo.tWaitRspTimes;
    this.tTotalHttpBytes += paramPerformanceInfo.tTotalHttpBytes;
    this.tTotalHttptime += paramPerformanceInfo.tTotalHttptime;
  }
  
  public int getiAvgConnectTime()
  {
    long l = this.tConnectTimes;
    if (l > 0L) {
      this.iAvgConnectTime = ((int)(this.tConnectTime / l));
    }
    return this.iAvgConnectTime;
  }
  
  public int getiAvgDNSTime()
  {
    long l = this.tDNSTimes;
    if (l > 0L) {
      this.iAvgDNSTime = ((int)(this.tDNSTime / l));
    }
    return this.iAvgDNSTime;
  }
  
  public int getiAvgSpeed()
  {
    long l = this.tTotalHttptime;
    if (l > 0L) {
      this.iAvgSpeed = ((int)(this.tTotalHttpBytes * 1000L / l));
    }
    return this.iAvgSpeed;
  }
  
  public int getiAvgWaitRspTime()
  {
    long l = this.tWaitRspTimes;
    if (l > 0L) {
      this.iAvgWaitRspTime = ((int)(this.tWaitRspTime / l));
    }
    return this.iAvgWaitRspTime;
  }
  
  public int getiMainResDownloadTime()
  {
    long l = this.tMainResDownloadTimes;
    if (l > 0L) {
      this.iMainResDownloadTime = ((int)(this.tMainResDownloadTime / l));
    }
    return this.iMainResDownloadTime;
  }
  
  public int getiTotalTime()
  {
    long l = this.tTotalTimes;
    if (l > 0L) {
      this.iTotalTime = ((int)(this.tTotalTime / l));
    }
    return this.iTotalTime;
  }
  
  public void readFrom(JceInputStream paramJceInputStream)
  {
    this.sDevice = paramJceInputStream.read(this.sDevice, 0, false);
    this.sUrl = paramJceInputStream.read(this.sUrl, 1, false);
    this.sAPN = paramJceInputStream.read(this.sAPN, 2, false);
    this.iProxyType = paramJceInputStream.read(this.iProxyType, 3, false);
    this.iTotalTime = paramJceInputStream.read(this.iTotalTime, 4, false);
    this.iMainResDownloadTime = paramJceInputStream.read(this.iMainResDownloadTime, 5, false);
    this.iAvgDNSTime = paramJceInputStream.read(this.iAvgDNSTime, 6, false);
    this.iAvgConnectTime = paramJceInputStream.read(this.iAvgConnectTime, 7, false);
    this.iAvgWaitRspTime = paramJceInputStream.read(this.iAvgWaitRspTime, 8, false);
    this.iAvgSpeed = paramJceInputStream.read(this.iAvgSpeed, 9, false);
    this.sRemoteIP = paramJceInputStream.read(this.sRemoteIP, 10, false);
    this.sProxyIP = paramJceInputStream.read(this.sProxyIP, 11, false);
    this.iMainResourceSize = paramJceInputStream.read(this.iMainResourceSize, 12, false);
    this.iMainResouceCostTime = paramJceInputStream.read(this.iMainResouceCostTime, 13, false);
    this.iSubResourceSize = paramJceInputStream.read(this.iSubResourceSize, 14, false);
    this.iSubResouceCostTime = paramJceInputStream.read(this.iSubResouceCostTime, 15, false);
    this.iPageVisitCount = paramJceInputStream.read(this.iPageVisitCount, 16, false);
    this.iHTTPRequestCount = paramJceInputStream.read(this.iHTTPRequestCount, 17, false);
  }
  
  public void setConnectTime(long paramLong)
  {
    this.tConnectTime += paramLong;
    this.tConnectTimes += 1L;
  }
  
  public void setDNSTime(long paramLong)
  {
    this.tDNSTime += paramLong;
    this.tDNSTimes += 1L;
  }
  
  public void setMainResDownloadTime(long paramLong)
  {
    this.tMainResDownloadTime += paramLong;
    this.tMainResDownloadTimes += 1L;
  }
  
  public void setTotalHttpBytes(long paramLong)
  {
    this.tTotalHttpBytes += paramLong;
  }
  
  public void setTotalHttptime(long paramLong)
  {
    this.tTotalHttptime += paramLong;
  }
  
  public void setTotalTime(long paramLong)
  {
    this.tTotalTime += paramLong;
    this.tTotalTimes += 1L;
  }
  
  public void setWaitRspTime(long paramLong)
  {
    this.tWaitRspTime += paramLong;
    this.tWaitRspTimes += 1L;
  }
  
  public void stat()
  {
    getiTotalTime();
    getiMainResDownloadTime();
    getiAvgDNSTime();
    getiAvgConnectTime();
    getiAvgWaitRspTime();
    getiAvgSpeed();
  }
  
  public void writeTo(JceOutputStream paramJceOutputStream)
  {
    String str = this.sDevice;
    if (str != null) {
      paramJceOutputStream.write(str, 0);
    }
    str = this.sUrl;
    if (str != null) {
      paramJceOutputStream.write(str, 1);
    }
    str = this.sAPN;
    if (str != null) {
      paramJceOutputStream.write(str, 2);
    }
    paramJceOutputStream.write(this.iProxyType, 3);
    paramJceOutputStream.write(this.iTotalTime, 4);
    paramJceOutputStream.write(this.iMainResDownloadTime, 5);
    paramJceOutputStream.write(this.iAvgDNSTime, 6);
    paramJceOutputStream.write(this.iAvgConnectTime, 7);
    paramJceOutputStream.write(this.iAvgWaitRspTime, 8);
    paramJceOutputStream.write(this.iAvgSpeed, 9);
    str = this.sRemoteIP;
    if (str != null) {
      paramJceOutputStream.write(str, 10);
    }
    str = this.sProxyIP;
    if (str != null) {
      paramJceOutputStream.write(str, 11);
    }
    paramJceOutputStream.write(this.iMainResourceSize, 12);
    paramJceOutputStream.write(this.iMainResouceCostTime, 13);
    paramJceOutputStream.write(this.iSubResourceSize, 14);
    paramJceOutputStream.write(this.iSubResouceCostTime, 15);
    paramJceOutputStream.write(this.iPageVisitCount, 16);
    paramJceOutputStream.write(this.iHTTPRequestCount, 17);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PerformanceInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */