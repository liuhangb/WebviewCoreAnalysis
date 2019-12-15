package com.tencent.tbs.common.baseinfo;

import java.util.ArrayList;

public abstract interface ITbsSmttServiceCallback
{
  public abstract String getCrashExtraInfo();
  
  public abstract ArrayList<String> getQProxyAddressList();
  
  public abstract String getX5CoreVersion();
  
  public abstract void setNeedCheckQProxy(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  public abstract void setNeedWIFILogin(boolean paramBoolean);
  
  public abstract void setSpdyListToSDK();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\ITbsSmttServiceCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */