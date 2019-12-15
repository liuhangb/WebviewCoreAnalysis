package com.tencent.mtt.network;

import com.tencent.basesupport.ModuleProxy;
import com.tencent.mtt.proguard.KeepNameAndPublic;

@KeepNameAndPublic
public class QBNetworkInfo
{
  public static boolean isQueenEnable()
  {
    return ((QBNetworkInfoProvider)QBNetworkInfoProvider.PROXY.get()).isQueenEnable();
  }
  
  public static boolean isQueenUser()
  {
    return ((QBNetworkInfoProvider)QBNetworkInfoProvider.PROXY.get()).isQueenUser();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\network\QBNetworkInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */