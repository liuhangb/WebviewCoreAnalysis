package com.tencent.tbs.tbsshell.partner.utils;

import android.content.Context;
import android.os.Bundle;
import com.tencent.mtt.video.browser.export.engine.IQbVideoManager;
import com.tencent.mtt.video.browser.export.wc.IWonderCacheTaskMgr;
import com.tencent.tbs.tbsshell.partner.player.TbsVideoManager;

public class TbsVideoUtilsProxy
{
  public void deleteVideoCache(Context paramContext, String paramString)
  {
    paramContext = TbsVideoManager.getInstance().getWonderCacheTaskManager(paramContext);
    if (paramContext != null) {
      paramContext.deleteCache(paramString);
    }
  }
  
  public String getCurWDPDecodeType(Context paramContext)
  {
    paramContext = ((IQbVideoManager)TbsVideoManager.getInstance().getVideoManager(paramContext)).getSettingValues("key_wdp_decode_type");
    int i;
    if (paramContext != null) {
      i = paramContext.getInt("value");
    } else {
      i = 99;
    }
    switch (i)
    {
    case 2: 
    case 3: 
    default: 
      return "";
    case 4: 
    case 5: 
      return "-M";
    case 1: 
      return "-H";
    }
    return "-S";
  }
  
  public Bundle getVideoGetAttribute(Context paramContext, String paramString, Bundle paramBundle)
  {
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\utils\TbsVideoUtilsProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */