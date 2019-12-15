package com.tencent.tbs.tbsshell.common;

import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.net.INetworkDetectorCallback;
import com.tencent.tbs.tbsshell.TBSShellFactory;

public class NetworkDetectorCallbackImpl
  implements INetworkDetectorCallback
{
  public void onResult(boolean paramBoolean)
  {
    TBSShellFactory.getSmttServiceCommon().setNeedWIFILogin(paramBoolean ^ true);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onResult ");
    localStringBuilder.append(paramBoolean ^ true);
    LogUtils.d("NetworkDetector", localStringBuilder.toString());
  }
  
  public void setOpenWifiProxyEnable(boolean paramBoolean) {}
  
  public boolean showLoginDialog()
  {
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\NetworkDetectorCallbackImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */