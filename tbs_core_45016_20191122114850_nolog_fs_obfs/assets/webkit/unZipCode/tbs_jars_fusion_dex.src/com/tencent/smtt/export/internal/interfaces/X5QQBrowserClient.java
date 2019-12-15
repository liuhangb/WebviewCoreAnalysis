package com.tencent.smtt.export.internal.interfaces;

import com.tencent.mtt.video.export.IVideoPlayerHelper;
import com.tencent.mtt.video.export.IX5VideoPlayer;

public class X5QQBrowserClient
  implements IX5QQBrowserClient
{
  public void checkSecurityLevel(String paramString1, String paramString2, int paramInt) {}
  
  public int getTitleHeight()
  {
    return 0;
  }
  
  public IVideoPlayerHelper getVideoPlayerHelper()
  {
    return null;
  }
  
  public int getVisbleTitleHeight()
  {
    return 0;
  }
  
  public void onReportMainresourceInDirectMode(String paramString) {}
  
  public void onVisbleTitleHeightChanged(int paramInt) {}
  
  public void setSecurityLevel(SecurityLevelBase paramSecurityLevelBase) {}
  
  public boolean shouldOverrideStandardPlay(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, IX5VideoPlayer paramIX5VideoPlayer)
  {
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\export\internal\interfaces\X5QQBrowserClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */