package org.chromium.tencent;

import android.content.Context;
import java.util.List;
import java.util.Map;

public class SmttServiceClientProxy
{
  private static SmttServiceClientProxy sInstance;
  private ISmttServiceClient mClient = null;
  
  public static SmttServiceClientProxy getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new SmttServiceClientProxy();
      }
      SmttServiceClientProxy localSmttServiceClientProxy = sInstance;
      return localSmttServiceClientProxy;
    }
    finally {}
  }
  
  public List<String> getAccessibilityBlackList()
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      return localISmttServiceClient.getAccessibilityBlackList();
    }
    return null;
  }
  
  public Context getCurrentContext()
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      return localISmttServiceClient.getCurrentContext();
    }
    return null;
  }
  
  public boolean getIsX5ProEnabled()
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      return localISmttServiceClient.getIsX5ProEnabled();
    }
    return false;
  }
  
  public String getProxyURL(String paramString)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      return localISmttServiceClient.getProxyURL(paramString);
    }
    return paramString;
  }
  
  public boolean isMSEBlackList(String paramString)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      return localISmttServiceClient.isMSEBlackList(paramString);
    }
    return false;
  }
  
  public boolean isMediaCodecBlackList(String paramString)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      return localISmttServiceClient.isMediaCodecBlackList(paramString);
    }
    return false;
  }
  
  public boolean isTbsWebRTCAudioWhiteList(String paramString)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      return localISmttServiceClient.isTbsWebRTCAudioWhiteList(paramString);
    }
    return false;
  }
  
  public boolean isWebRTCinCamera1WhiteList(String paramString)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      return localISmttServiceClient.isWebRTCinCamera1WhiteList(paramString);
    }
    return false;
  }
  
  public void notifyVPNChanged(boolean paramBoolean)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      localISmttServiceClient.notifyVPNChanged(paramBoolean);
    }
  }
  
  public void printLiveLogError(String paramString1, String paramString2)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      localISmttServiceClient.printLiveLogError(paramString1, paramString2);
    }
  }
  
  public void reportAccessibilityEnableCause(String paramString)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      localISmttServiceClient.reportAccessibilityEnableCause(paramString);
    }
  }
  
  public void reportTbsError(int paramInt, String paramString)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      localISmttServiceClient.reportTbsError(paramInt, paramString);
    }
  }
  
  public void setSmttServiceClient(ISmttServiceClient paramISmttServiceClient)
  {
    this.mClient = paramISmttServiceClient;
  }
  
  public boolean shouldUseTbsAudioPlayerWithNoClick(String paramString)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      return localISmttServiceClient.shouldUseTbsAudioPlayerWithNoClick(paramString);
    }
    return false;
  }
  
  public boolean shouldUseTbsMediaPlayer(String paramString)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      return localISmttServiceClient.shouldUseTbsAudioPlayerWithNoClick(paramString);
    }
    return false;
  }
  
  public void uploadToBeacon(String paramString, Map<String, String> paramMap)
  {
    ISmttServiceClient localISmttServiceClient = this.mClient;
    if (localISmttServiceClient != null) {
      localISmttServiceClient.uploadToBeacon(paramString, paramMap);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\SmttServiceClientProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */