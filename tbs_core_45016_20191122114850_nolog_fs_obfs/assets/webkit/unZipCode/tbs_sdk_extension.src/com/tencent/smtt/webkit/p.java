package com.tencent.smtt.webkit;

import android.content.Context;
import com.tencent.common.http.MttLocalProxy;
import com.tencent.smtt.livelog.LiveLog;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.List;
import java.util.Map;
import org.chromium.tencent.ISmttServiceClient;

public class p
  implements ISmttServiceClient
{
  public List<String> getAccessibilityBlackList()
  {
    return SmttServiceProxy.getInstance().getAccessibilityBlackList();
  }
  
  public Context getCurrentContext()
  {
    return ContextHolder.getInstance().getCurrentContext();
  }
  
  public boolean getIsX5ProEnabled()
  {
    return SmttServiceProxy.getInstance().getIsX5ProEnabled();
  }
  
  public String getProxyURL(String paramString)
  {
    return MttLocalProxy.getInstance().getProxyURL(paramString);
  }
  
  public boolean isMSEBlackList(String paramString)
  {
    return SmttServiceProxy.getInstance().isMSEBlackList(paramString);
  }
  
  public boolean isMediaCodecBlackList(String paramString)
  {
    return SmttServiceProxy.getInstance().isMediaCodecBlackList(paramString);
  }
  
  public boolean isTbsWebRTCAudioWhiteList(String paramString)
  {
    return SmttServiceProxy.getInstance().isTbsWebRTCAudioWhiteList(paramString);
  }
  
  public boolean isWebRTCinCamera1WhiteList(String paramString)
  {
    return SmttServiceProxy.getInstance().isWebRTCinCamera1WhiteList(paramString);
  }
  
  public void notifyVPNChanged(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().notifyVPNChanged(paramBoolean);
  }
  
  public void printLiveLogError(String paramString1, String paramString2)
  {
    LiveLog.e(paramString1, paramString2);
  }
  
  public void reportAccessibilityEnableCause(String paramString)
  {
    SmttServiceProxy.getInstance().reportAccessibilityEnableCause(paramString);
  }
  
  public void reportTbsError(int paramInt, String paramString)
  {
    SmttServiceProxy.getInstance().reportTbsError(paramInt, paramString);
  }
  
  public boolean shouldUseTbsAudioPlayerWithNoClick(String paramString)
  {
    return SmttServiceProxy.getInstance().shouldUseTbsAudioPlayerWithNoClick(paramString);
  }
  
  public boolean shouldUseTbsMediaPlayer(String paramString)
  {
    return SmttServiceProxy.getInstance().shouldUseTbsAudioPlayerWithNoClick(paramString);
  }
  
  public void uploadToBeacon(String paramString, Map<String, String> paramMap)
  {
    SmttServiceProxy.getInstance().upLoadToBeacon(paramString, paramMap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\p.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */