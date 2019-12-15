package com.tencent.tbs.core.partner.downloadserver;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.ProxyWebViewObserver;
import com.tencent.tbs.core.webkit.CookieManager;
import com.tencent.tbs.core.webkit.DownloadListener;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;

public class TBSDownloadServer
  extends ProxyWebViewObserver
{
  public static final String PARAM_KEY_FORCE_DIALOG_STYLE = "PARAM_KEY_FORCE_DIALOG_STYLE";
  public static final String TAG = "TBSDownloadServer";
  
  public TBSDownloadServer(WebView paramWebView)
  {
    super(paramWebView);
  }
  
  public boolean onDownloadStartCustom(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte, TencentWebViewProxy paramTencentWebViewProxy, DownloadListener paramDownloadListener, String paramString9)
  {
    Context localContext = paramTencentWebViewProxy.getContext();
    if ((localContext != null) && (localContext.getApplicationInfo() != null) && ("com.tencent.mtt".equals(localContext.getApplicationInfo().packageName))) {
      return false;
    }
    int i = SmttServiceProxy.getInstance().useDownloadInterceptPlugin(localContext, paramString1, CookieManager.getInstance().getCookie(paramString1), paramString2, paramString3, paramString4, paramLong, paramString5, paramString6, paramString7, paramString8, paramArrayOfByte, paramTencentWebViewProxy, paramDownloadListener, paramString9);
    if (i == 0)
    {
      SmttServiceProxy.getInstance().userBehaviorStatistics("BZUGPL_0");
      return false;
    }
    if (i == 1)
    {
      SmttServiceProxy.getInstance().userBehaviorStatistics("BZUGPL_0");
      return true;
    }
    SmttServiceProxy.getInstance().userBehaviorStatistics("BZUGPL_1");
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\downloadserver\TBSDownloadServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */