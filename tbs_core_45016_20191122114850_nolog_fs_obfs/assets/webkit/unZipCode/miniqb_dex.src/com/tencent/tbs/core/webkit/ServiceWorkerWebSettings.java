package com.tencent.tbs.core.webkit;

public abstract class ServiceWorkerWebSettings
{
  public abstract boolean getAllowContentAccess();
  
  public abstract boolean getAllowFileAccess();
  
  public abstract boolean getBlockNetworkLoads();
  
  public abstract int getCacheMode();
  
  public abstract void setAllowContentAccess(boolean paramBoolean);
  
  public abstract void setAllowFileAccess(boolean paramBoolean);
  
  public abstract void setBlockNetworkLoads(boolean paramBoolean);
  
  public abstract void setCacheMode(int paramInt);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\ServiceWorkerWebSettings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */