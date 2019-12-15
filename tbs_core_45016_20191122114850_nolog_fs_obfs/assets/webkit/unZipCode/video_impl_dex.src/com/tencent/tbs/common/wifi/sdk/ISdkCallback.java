package com.tencent.tbs.common.wifi.sdk;

import com.tencent.tbs.common.wifi.WifiApInfo;
import java.util.ArrayList;

public abstract interface ISdkCallback
{
  public abstract void onConnectCompleted(String paramString, int paramInt1, int paramInt2);
  
  public abstract void onPullPwdCompleted(Object paramObject);
  
  public abstract void onRequestCompleted(ArrayList<WifiApInfo> paramArrayList, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\sdk\ISdkCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */