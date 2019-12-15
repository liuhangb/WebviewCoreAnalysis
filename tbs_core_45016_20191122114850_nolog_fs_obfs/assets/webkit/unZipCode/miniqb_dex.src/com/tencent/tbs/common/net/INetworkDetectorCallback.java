package com.tencent.tbs.common.net;

public abstract interface INetworkDetectorCallback
{
  public abstract void onResult(boolean paramBoolean);
  
  public abstract void setOpenWifiProxyEnable(boolean paramBoolean);
  
  public abstract boolean showLoginDialog();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\net\INetworkDetectorCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */