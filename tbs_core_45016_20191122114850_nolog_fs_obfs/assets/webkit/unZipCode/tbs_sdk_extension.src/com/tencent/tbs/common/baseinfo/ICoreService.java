package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import java.util.ArrayList;
import java.util.Map;

public abstract interface ICoreService
{
  public abstract void onConnectivityChanged(int paramInt);
  
  public abstract void refreshSpdyProxy();
  
  public abstract void setForceDirect(boolean paramBoolean);
  
  public abstract void setSafeDomainList(Map<Integer, ArrayList<String>> paramMap);
  
  public abstract void sweepAll(Context paramContext);
  
  public abstract void sweepCookieCache(Context paramContext);
  
  public abstract void sweepDNSCache(Context paramContext);
  
  public abstract void sweepFileCache(Context paramContext);
  
  public abstract void sweepWebStorageCache(Context paramContext);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\ICoreService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */