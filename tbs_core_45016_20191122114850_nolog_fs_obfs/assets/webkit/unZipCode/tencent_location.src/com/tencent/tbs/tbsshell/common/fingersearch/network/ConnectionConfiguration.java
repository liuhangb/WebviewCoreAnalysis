package com.tencent.tbs.tbsshell.common.fingersearch.network;

import java.net.MalformedURLException;

public class ConnectionConfiguration
{
  private static final int DEFAULT_CONNECT_TIME_OUT = 120000;
  private static final int DEFAULT_READ_TIME_OUT = 120000;
  private static final int DEFAULT_RECONNECT_TIMES = 1;
  public static final int PACKET_HEAD_LEN_INT = 4;
  public static final int PACKET_HEAD_LEN_SHORT = 2;
  private int mConnectTimeOut = 120000;
  private int mHeadLen = 2;
  private String mHost;
  private int mPort;
  private int mReadTimeOut = 120000;
  private int mReconnectTimes = 1;
  
  public ConnectionConfiguration(String paramString)
    throws MalformedURLException
  {
    try
    {
      int i = paramString.indexOf(':');
      if (i != -1)
      {
        this.mHost = paramString.substring(0, i);
        this.mPort = Integer.parseInt(paramString.substring(i + 1));
        return;
      }
      this.mHost = paramString;
      this.mPort = 80;
      return;
    }
    catch (Exception localException)
    {
      StringBuilder localStringBuilder;
      for (;;) {}
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("Invalid address: ");
    localStringBuilder.append(paramString);
    throw new MalformedURLException(localStringBuilder.toString());
  }
  
  public ConnectionConfiguration(String paramString, int paramInt)
  {
    this.mHost = paramString;
    this.mPort = paramInt;
  }
  
  public ConnectionConfiguration(String paramString, int paramInt1, int paramInt2)
  {
    this.mHost = paramString;
    this.mPort = paramInt1;
    this.mHeadLen = paramInt2;
  }
  
  public int getConnectTimeout()
  {
    return this.mConnectTimeOut;
  }
  
  public int getHeadLen()
  {
    return this.mHeadLen;
  }
  
  public String getHost()
  {
    return this.mHost;
  }
  
  public int getPort()
  {
    return this.mPort;
  }
  
  public int getReadTimeout()
  {
    return this.mReadTimeOut;
  }
  
  public int getReconnectTimes()
  {
    return this.mReconnectTimes;
  }
  
  public void setConnectTimeout(int paramInt)
  {
    this.mConnectTimeOut = paramInt;
  }
  
  public void setHost(String paramString)
  {
    this.mHost = paramString;
  }
  
  public void setPort(int paramInt)
  {
    this.mPort = paramInt;
  }
  
  public void setReadTimeout(int paramInt)
  {
    this.mReadTimeOut = paramInt;
  }
  
  public void setReconnectTimes(int paramInt)
  {
    this.mReconnectTimes = paramInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\network\ConnectionConfiguration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */