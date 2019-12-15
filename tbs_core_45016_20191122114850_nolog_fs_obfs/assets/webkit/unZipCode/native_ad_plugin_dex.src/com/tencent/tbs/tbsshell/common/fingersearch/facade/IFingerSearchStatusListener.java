package com.tencent.tbs.tbsshell.common.fingersearch.facade;

import com.taf.UniPacket;

public abstract interface IFingerSearchStatusListener
{
  public abstract void onFingerSearchFailed(UniPacket paramUniPacket);
  
  public abstract void onFingerSearchSuccess(UniPacket paramUniPacket);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\facade\IFingerSearchStatusListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */