package com.tencent.mtt.network;

import com.tencent.basesupport.ModuleProxy;
import com.tencent.common.manifest.annotation.Service;

@Service
public abstract interface QBNetworkInfoProvider
{
  public static final ModuleProxy<QBNetworkInfoProvider> PROXY = ModuleProxy.newProxy(QBNetworkInfoProvider.class);
  
  public abstract boolean isQueenEnable();
  
  public abstract boolean isQueenUser();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\network\QBNetworkInfoProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */