package com.tencent.common.wup;

public abstract interface IWUPRequestCallBack
{
  public abstract void onWUPTaskFail(WUPRequestBase paramWUPRequestBase);
  
  public abstract void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\IWUPRequestCallBack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */