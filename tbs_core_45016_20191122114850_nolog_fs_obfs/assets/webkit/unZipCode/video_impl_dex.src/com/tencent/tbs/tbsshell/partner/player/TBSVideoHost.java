package com.tencent.tbs.tbsshell.partner.player;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import com.tencent.mtt.video.export.VideoHost;
import com.tencent.tbs.common.sniffer.SniffObserver;
import com.tencent.tbs.common.sniffer.VideoSniffer;
import com.tencent.tbs.tbsshell.WebCoreProxy;
import com.tencent.tbs.tbsshell.partner.miniqb.TbsMiniQBProxy;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class TBSVideoHost
  extends VideoHost
{
  public TBSVideoHost(Context paramContext)
  {
    super(paramContext);
  }
  
  public Bundle callMiscMethod(String paramString, Bundle paramBundle, Context paramContext)
  {
    if ((TextUtils.equals(paramString, "openUrlByMiniQb")) && (paramBundle != null))
    {
      paramString = paramBundle.getString("url");
      if ((!TextUtils.isEmpty(paramString)) && (paramContext != null))
      {
        boolean bool = false;
        int i = TbsMiniQBProxy.startQBWeb(paramContext, paramString, null, 16, new Point(0, 0), new Point(0, 0));
        paramString = new Bundle();
        if (i == 0) {
          bool = true;
        }
        paramString.putBoolean("ret", bool);
        return paramString;
      }
    }
    return null;
  }
  
  public String getCookie(String paramString, boolean paramBoolean)
  {
    return WebCoreProxy.getCookie(paramString, paramBoolean);
  }
  
  public int getVideoHostType()
  {
    return 3;
  }
  
  public void onSniffPlayFailed(String paramString)
  {
    VideoSniffer.onSniffPlayFailed(paramString);
    openUrl(paramString, false);
  }
  
  public void setCookie(URL paramURL, Map<String, List<String>> paramMap, boolean paramBoolean)
  {
    WebCoreProxy.setCookie(paramURL, paramMap);
  }
  
  public void sniffVideo(String paramString, int paramInt, SniffObserver paramSniffObserver)
  {
    new VideoSniffer().sniffVideo(paramString, paramInt, paramSniffObserver);
  }
  
  public boolean supportSniff(String paramString)
  {
    return VideoSniffer.isSniffSupported(paramString);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\player\TBSVideoHost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */