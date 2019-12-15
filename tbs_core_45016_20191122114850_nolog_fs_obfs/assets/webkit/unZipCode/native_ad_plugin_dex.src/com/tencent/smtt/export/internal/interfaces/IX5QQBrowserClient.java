package com.tencent.smtt.export.internal.interfaces;

import com.tencent.mtt.video.export.IVideoPlayerHelper;
import com.tencent.mtt.video.export.IX5VideoPlayer;

public abstract interface IX5QQBrowserClient
{
  public static final int ResourceType_CSS = 5;
  public static final int ResourceType_FROMJS = 7;
  public static final int ResourceType_IMAGE = 4;
  public static final int ResourceType_JSMAIN = 3;
  public static final int ResourceType_MAIN = 1;
  public static final int ResourceType_PAR = 9;
  public static final int ResourceType_SCRIPT = 6;
  public static final int ResourceType_SUBMAIN = 2;
  public static final int ResourceType_UNKNOWN = 0;
  public static final int ResourceType_XHR = 8;
  
  public abstract void checkSecurityLevel(String paramString1, String paramString2, int paramInt);
  
  public abstract int getTitleHeight();
  
  public abstract IVideoPlayerHelper getVideoPlayerHelper();
  
  public abstract int getVisbleTitleHeight();
  
  public abstract void onReportMainresourceInDirectMode(String paramString);
  
  public abstract void onVisbleTitleHeightChanged(int paramInt);
  
  public abstract void setSecurityLevel(SecurityLevelBase paramSecurityLevelBase);
  
  public abstract boolean shouldOverrideStandardPlay(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, IX5VideoPlayer paramIX5VideoPlayer);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\export\internal\interfaces\IX5QQBrowserClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */