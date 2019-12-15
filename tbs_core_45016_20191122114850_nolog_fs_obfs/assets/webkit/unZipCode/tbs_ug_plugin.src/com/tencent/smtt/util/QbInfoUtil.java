package com.tencent.smtt.util;

import com.tencent.smtt.webkit.service.SmttServiceProxy;

public class QbInfoUtil
{
  private static QbInfoUtil mInstance = new QbInfoUtil();
  
  public static QbInfoUtil getInstance()
  {
    try
    {
      QbInfoUtil localQbInfoUtil = mInstance;
      return localQbInfoUtil;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public String JNIgetBrowserBuildId()
  {
    return SmttServiceProxy.getInstance().getBrowserBuildId();
  }
  
  public String JNIgetBrowserVersion()
  {
    return SmttServiceProxy.getInstance().getBrowserVersion();
  }
  
  public String JNIgetDeviceName()
  {
    return SmttServiceProxy.getInstance().getDeviceName();
  }
  
  public String JNIgetDevicePlatform()
  {
    return SmttServiceProxy.getInstance().getDevicePlatform();
  }
  
  public String JNIgetDeviceVersion()
  {
    return SmttServiceProxy.getInstance().getDeviceVersion();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\QbInfoUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */