package com.tencent.tbs.core.partner.tencentfiledownload;

import java.io.Serializable;

public abstract interface TFMInstallListener
  extends Serializable
{
  public abstract boolean onInstallFinished();
  
  public abstract boolean onInstalling();
  
  public abstract boolean onUninstallFinished();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\tencentfiledownload\TFMInstallListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */