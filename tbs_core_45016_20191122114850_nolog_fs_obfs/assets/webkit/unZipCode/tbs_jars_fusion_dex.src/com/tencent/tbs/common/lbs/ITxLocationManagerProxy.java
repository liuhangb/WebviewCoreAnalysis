package com.tencent.tbs.common.lbs;

public abstract interface ITxLocationManagerProxy
{
  public abstract void setDebug(boolean paramBoolean);
  
  public abstract void startRequestLocation(ILbsListener paramILbsListener, boolean paramBoolean);
  
  public abstract void stopRequestLocation();
  
  public abstract boolean wgs84ToGcj02(double[] paramArrayOfDouble1, double[] paramArrayOfDouble2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\lbs\ITxLocationManagerProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */