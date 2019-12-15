package com.tencent.tbs.common.lbs;

import android.location.Location;

public abstract interface ILbsListener
{
  public abstract void onLocationFailed(int paramInt, String paramString);
  
  public abstract void onLocationSuccess(Location paramLocation);
  
  public abstract void onStatusUpdate(String paramString1, int paramInt, String paramString2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\lbs\ILbsListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */