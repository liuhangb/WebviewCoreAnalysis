package com.tencent.tbs.tbsshell.common.fingersearch.network;

import com.taf.UniPacket;

public abstract interface WUPConnectionListener
{
  public abstract void onAllServersFailure(Throwable paramThrowable);
  
  public abstract void onConnectException(Throwable paramThrowable, int paramInt);
  
  public abstract void onReceiveData(UniPacket paramUniPacket);
  
  public abstract void onReceiveDataError();
  
  public abstract void onSendFailure(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void onSendSuccess(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void onSingleServerTimeout(Throwable paramThrowable, int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\network\WUPConnectionListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */