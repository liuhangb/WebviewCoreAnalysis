package com.tencent.tbs.common.stat;

public class StatEntry
{
  public static final int STAT_TYPE_NORMAL = 1;
  public static final int STAT_TYPE_PERFORMANCE_HTTP = 2;
  public static final int STAT_TYPE_PERFORMANCE_PAGE = 3;
  public int apn;
  public byte apnType;
  public long connectModuleFeedbackTime;
  public long connectTime;
  public int connectionUseCount;
  public long dnsTime;
  public int eEUSESTAT;
  public int enter;
  public long firstScreenTime;
  public long firstWordTime;
  public int flow;
  public int iFail;
  public long iRecordTime;
  public long iRecvRspTime;
  public long iSendingTime;
  public int iSucc;
  public boolean isError;
  public boolean isProxy;
  public boolean isRes;
  public boolean isWap;
  public boolean isWup;
  public boolean isiMainResource;
  public long pageTime;
  public long progressFinishTime;
  public String proxyIP;
  public byte proxyType;
  public String referUrl;
  public String remoteIP;
  public String sAPN;
  public String sPID;
  public String sProxyData;
  public String sUrl;
  public String sWebSiteIP;
  public int statType = 1;
  public String statUrl;
  public int stateCode;
  public long statusCode;
  public int time;
  public long totalBytes;
  public long totalTime;
  public String url;
  public long waitRspTime;
  
  public StatEntry()
  {
    this.isWup = false;
    this.iSucc = 0;
    this.iFail = 0;
  }
  
  public StatEntry(String paramString1, String paramString2, String paramString3, byte paramByte, long paramLong1, long paramLong2, long paramLong3, long paramLong4, String paramString4)
  {
    this.isWup = false;
    this.iSucc = 0;
    this.iFail = 0;
    this.url = paramString1;
    this.statUrl = paramString1;
    this.remoteIP = paramString2;
    this.proxyIP = paramString3;
    this.proxyType = paramByte;
    this.pageTime = paramLong4;
    this.firstWordTime = paramLong1;
    this.firstScreenTime = paramLong2;
    this.progressFinishTime = paramLong3;
    this.sAPN = paramString4;
    this.statType = 3;
  }
  
  public StatEntry(String paramString1, String paramString2, String paramString3, byte paramByte, boolean paramBoolean, long paramLong1, long paramLong2, long paramLong3, long paramLong4, int paramInt, long paramLong5, long paramLong6, long paramLong7, long paramLong8, long paramLong9, String paramString4, String paramString5, String paramString6)
  {
    boolean bool = false;
    this.isWup = false;
    this.iSucc = 0;
    this.iFail = 0;
    this.statUrl = paramString1;
    this.url = paramString1;
    this.referUrl = paramString2;
    this.proxyIP = paramString3;
    this.proxyType = paramByte;
    this.isiMainResource = paramBoolean;
    this.iRecordTime = paramLong1;
    this.dnsTime = paramLong2;
    this.connectTime = paramLong3;
    this.connectModuleFeedbackTime = paramLong4;
    this.connectionUseCount = paramInt;
    this.iSendingTime = paramLong5;
    this.waitRspTime = paramLong6;
    this.iRecvRspTime = paramLong7;
    this.totalBytes = paramLong8;
    this.statusCode = paramLong9;
    this.sWebSiteIP = paramString4;
    this.sProxyData = paramString5;
    paramBoolean = bool;
    if ((paramByte & 0x1) == 1) {
      paramBoolean = true;
    }
    this.isProxy = paramBoolean;
    this.sAPN = paramString6;
    this.statType = 2;
  }
  
  public StatEntry(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, int paramInt1, int paramInt2)
  {
    this.isWup = false;
    this.iSucc = 0;
    this.iFail = 0;
    this.url = paramString;
    this.isWap = paramBoolean1;
    this.isRes = paramBoolean2;
    this.isError = paramBoolean3;
    this.flow = paramInt1;
    this.apn = paramInt2;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(" URL = ");
    localStringBuilder.append(this.url);
    String str;
    if (this.isError) {
      str = " Error";
    } else {
      str = " ";
    }
    localStringBuilder.append(str);
    if (this.isRes) {
      str = " Res";
    } else {
      str = " Page";
    }
    localStringBuilder.append(str);
    if (this.isWap) {
      str = " wap";
    } else {
      str = " web";
    }
    localStringBuilder.append(str);
    localStringBuilder.append(" flow = ");
    localStringBuilder.append(this.flow);
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\stat\StatEntry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */