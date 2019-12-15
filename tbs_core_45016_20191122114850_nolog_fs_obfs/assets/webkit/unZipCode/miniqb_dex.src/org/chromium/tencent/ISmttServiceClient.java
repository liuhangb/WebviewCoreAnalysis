package org.chromium.tencent;

import android.content.Context;
import java.util.List;
import java.util.Map;

public abstract interface ISmttServiceClient
{
  public abstract List<String> getAccessibilityBlackList();
  
  public abstract Context getCurrentContext();
  
  public abstract boolean getIsX5ProEnabled();
  
  public abstract String getProxyURL(String paramString);
  
  public abstract boolean isMSEBlackList(String paramString);
  
  public abstract boolean isMediaCodecBlackList(String paramString);
  
  public abstract boolean isTbsWebRTCAudioWhiteList(String paramString);
  
  public abstract boolean isWebRTCinCamera1WhiteList(String paramString);
  
  public abstract void notifyVPNChanged(boolean paramBoolean);
  
  public abstract void printLiveLogError(String paramString1, String paramString2);
  
  public abstract void reportAccessibilityEnableCause(String paramString);
  
  public abstract void reportTbsError(int paramInt, String paramString);
  
  public abstract boolean shouldUseTbsAudioPlayerWithNoClick(String paramString);
  
  public abstract boolean shouldUseTbsMediaPlayer(String paramString);
  
  public abstract void uploadToBeacon(String paramString, Map<String, String> paramMap);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\ISmttServiceClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */