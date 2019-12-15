package com.tencent.tbs.common.baseinfo;

import java.util.ArrayList;

public class TbsSmttServiceProxy
{
  private static TbsSmttServiceProxy mInstance;
  private ITbsSmttServiceCallback mSmttServiceCallback = null;
  
  public static TbsSmttServiceProxy getInstance()
  {
    if (mInstance == null) {
      mInstance = new TbsSmttServiceProxy();
    }
    return mInstance;
  }
  
  public String getCrashExtraInfo()
  {
    ITbsSmttServiceCallback localITbsSmttServiceCallback = this.mSmttServiceCallback;
    if (localITbsSmttServiceCallback == null) {
      return null;
    }
    return localITbsSmttServiceCallback.getCrashExtraInfo();
  }
  
  public ArrayList<String> getQProxyAddressList()
  {
    ITbsSmttServiceCallback localITbsSmttServiceCallback = this.mSmttServiceCallback;
    if (localITbsSmttServiceCallback == null) {
      return null;
    }
    return localITbsSmttServiceCallback.getQProxyAddressList();
  }
  
  public String getX5CoreVersion()
  {
    ITbsSmttServiceCallback localITbsSmttServiceCallback = this.mSmttServiceCallback;
    if (localITbsSmttServiceCallback == null) {
      return null;
    }
    return localITbsSmttServiceCallback.getX5CoreVersion();
  }
  
  public void setNeedCheckQProxy(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
  {
    ITbsSmttServiceCallback localITbsSmttServiceCallback = this.mSmttServiceCallback;
    if (localITbsSmttServiceCallback == null) {
      return;
    }
    localITbsSmttServiceCallback.setNeedCheckQProxy(paramBoolean1, paramBoolean2, paramBoolean3);
  }
  
  public void setNeedWIFILogin(boolean paramBoolean)
  {
    ITbsSmttServiceCallback localITbsSmttServiceCallback = this.mSmttServiceCallback;
    if (localITbsSmttServiceCallback == null) {
      return;
    }
    localITbsSmttServiceCallback.setNeedWIFILogin(paramBoolean);
  }
  
  public void setSpdyListToSDK(ArrayList<String> paramArrayList)
  {
    paramArrayList = this.mSmttServiceCallback;
    if (paramArrayList == null) {
      return;
    }
    paramArrayList.setSpdyListToSDK();
  }
  
  public void setTesSmttServiceCallback(ITbsSmttServiceCallback paramITbsSmttServiceCallback)
  {
    this.mSmttServiceCallback = paramITbsSmttServiceCallback;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\TbsSmttServiceProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */