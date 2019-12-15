package com.tencent.mtt.external.reader.export;

public abstract interface IReaderEventProxy
{
  public abstract void destroy();
  
  public abstract void doCallBackEvent(int paramInt, Object paramObject1, Object paramObject2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\export\IReaderEventProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */