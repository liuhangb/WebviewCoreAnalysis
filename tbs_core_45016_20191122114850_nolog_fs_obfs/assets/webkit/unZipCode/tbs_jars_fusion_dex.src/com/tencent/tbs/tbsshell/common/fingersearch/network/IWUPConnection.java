package com.tencent.tbs.tbsshell.common.fingersearch.network;

import com.taf.UniPacket;

public abstract interface IWUPConnection
{
  public static final byte FROMWHERE_ADDRESSBAR = 1;
  public static final byte FROMWHERE_WEBVIEW = 0;
  
  public abstract void connectServer(long paramLong);
  
  public abstract void disConnectServer();
  
  public abstract String getServer();
  
  public abstract boolean isConnected();
  
  public abstract void reset(int paramInt);
  
  public abstract void sendData(UniPacket paramUniPacket, boolean paramBoolean);
  
  public abstract void setFromWhere(byte paramByte);
  
  public abstract void setIsNeedPenddingAll(boolean paramBoolean);
  
  public abstract void setWupConnectionListener(WUPConnectionListener paramWUPConnectionListener);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\network\IWUPConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */