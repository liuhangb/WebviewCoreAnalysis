package com.tencent.common.http;

public abstract interface IFlowListener
{
  public static final int FLOW_TYPE_DOWNLOAD = 1;
  public static final int FLOW_TYPE_UPLOAD = 2;
  
  public abstract void onFlow(int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\http\IFlowListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */