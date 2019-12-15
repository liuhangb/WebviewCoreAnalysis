package com.tencent.tbs.tbsshell.common;

public abstract interface ISmttServiceCallBack
{
  public abstract void SetSPDYInfos(boolean paramBoolean, long paramLong, String[] paramArrayOfString);
  
  public abstract void SetX5ProxyEnable(boolean paramBoolean);
  
  public abstract void redirectTo(String paramString);
  
  public abstract void refreshSpdyProxy(String[] paramArrayOfString);
  
  public abstract void setAPNType(int paramInt);
  
  public abstract void setAdBlockEnabled(boolean paramBoolean);
  
  public abstract void setChromiumNet(boolean paramBoolean);
  
  public abstract void setDirectWhiteList(String[] paramArrayOfString);
  
  public abstract void setHasEverLogin(boolean paramBoolean);
  
  public abstract void setIOThreadPriority(int paramInt);
  
  public abstract void setNetworkType(int paramInt);
  
  public abstract void setQAuth(String paramString);
  
  public abstract void setQGUID(String paramString);
  
  public abstract void setQProxyParam(String paramString);
  
  public abstract void setQUA(String paramString);
  
  public abstract void setWupProxyList(String[] paramArrayOfString);
  
  public abstract void setWupWhiteList(String[] paramArrayOfString);
  
  public abstract void setX5ProxyReadModeEnabled(boolean paramBoolean);
  
  public abstract void startProxyChecking(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\ISmttServiceCallBack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */