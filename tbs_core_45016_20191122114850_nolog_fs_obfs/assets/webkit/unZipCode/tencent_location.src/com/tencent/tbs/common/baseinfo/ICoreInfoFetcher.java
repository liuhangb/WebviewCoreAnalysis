package com.tencent.tbs.common.baseinfo;

public abstract interface ICoreInfoFetcher
{
  public abstract byte getCoreType();
  
  public abstract String getCoreVersion();
  
  public abstract boolean getIsPad();
  
  public abstract int getMaxReportNumber();
  
  public abstract boolean getMiniqbDebugFlag();
  
  public abstract String getQbCHID();
  
  public abstract String getQbLCID();
  
  public abstract String getQbPB();
  
  public abstract String getQbPPVN();
  
  public abstract String getQbVE();
  
  public abstract String getQua1FromQbUi();
  
  public abstract boolean shouldUseQProxyAccordingToFlag(byte paramByte);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\ICoreInfoFetcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */