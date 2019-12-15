package com.tencent.tbs.core.partner;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebSettingsExtension;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewExtension;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.ProxyWebViewObserver;
import com.tencent.tbs.core.partner.clipboard.PartnerClipboard;
import com.tencent.tbs.core.partner.clipboard.SystemClipboardMonitor;
import com.tencent.tbs.core.partner.crashmonitor.CrashMonitor;
import com.tencent.tbs.core.partner.crashmonitor.IWebViewStateChangeListener;
import com.tencent.tbs.core.partner.downloadserver.TBSDownloadServer;
import com.tencent.tbs.core.partner.headsup.HeadsupEngine;
import com.tencent.tbs.core.webkit.DownloadListener;
import com.tencent.tbs.core.webkit.WebView;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;

public class PartnerWebViewObserver
  extends ProxyWebViewObserver
{
  private boolean isClipboardInited = false;
  private HeadsupEngine mHeadsupEngine = new HeadsupEngine();
  private TBSDownloadServer mTBSDownloadServer;
  
  public PartnerWebViewObserver(WebView paramWebView)
  {
    super(paramWebView);
    this.mTBSDownloadServer = new TBSDownloadServer(paramWebView);
  }
  
  private void initClipboadrIfNeeded()
  {
    if (this.isClipboardInited) {
      return;
    }
    try
    {
      boolean bool1 = SmttServiceProxy.getInstance().isTbsClipBoardEnabled(ContextHolder.getInstance().getApplicationContext());
      boolean bool2 = ContextHolder.getInstance().isThirdPartyApp(ContextHolder.getInstance().getApplicationContext());
      PartnerClipboard.setIsTbsClipBoardEnabled(bool1);
      PartnerClipboard.setIsThirdPartyApp(bool2);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    this.isClipboardInited = true;
  }
  
  public void onAttachedToWindow()
  {
    if (CrashMonitor.getInstance().isEnable()) {
      CrashMonitor.getInstance().getWebViewStateChangeListener().onWebViewAttachedToWindow(webView());
    }
  }
  
  public void onDetachedFromWindow()
  {
    if (CrashMonitor.getInstance().isEnable()) {
      CrashMonitor.getInstance().getWebViewStateChangeListener().onWebViewDetachedFromWindow(webView());
    }
  }
  
  public boolean onDownloadStartCustom(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, String paramString5, String paramString6, String paramString7, String paramString8, byte[] paramArrayOfByte, TencentWebViewProxy paramTencentWebViewProxy, DownloadListener paramDownloadListener, String paramString9)
  {
    return this.mTBSDownloadServer.onDownloadStartCustom(paramString1, paramString2, paramString3, paramString4, paramLong, paramString5, paramString6, paramString7, paramString8, paramArrayOfByte, paramTencentWebViewProxy, paramDownloadListener, paramString9);
  }
  
  public void onPageFinished(String paramString)
  {
    try
    {
      SystemClipboardMonitor.getInstance(ContextHolder.getInstance().getApplicationContext());
      if (CrashMonitor.getInstance().isEnable()) {
        CrashMonitor.getInstance().getWebViewStateChangeListener().onWebViewPageFinished(webView());
      }
      return;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
  }
  
  public void onPageStarted(String paramString)
  {
    this.mHeadsupEngine.doHeadsup(paramString);
    initClipboadrIfNeeded();
    if (CrashMonitor.getInstance().isEnable()) {
      CrashMonitor.getInstance().getWebViewStateChangeListener().onWebViewPageStarted(webView());
    }
  }
  
  public void onWebViewCreated(final TencentWebViewProxy paramTencentWebViewProxy)
  {
    MeiZuNightModeAdapter.getInstance().init(paramTencentWebViewProxy.getContext(), new MeiZuNightModeAdapter.MeizuNightModeCallback()
    {
      public void onChangeNightMode(boolean paramAnonymousBoolean)
      {
        paramTencentWebViewProxy.getX5WebViewExtension().getSettingsExtension().setDayOrNight(paramAnonymousBoolean ^ true);
      }
    });
    PartnerClipboard.initClipboardListener();
    if (CrashMonitor.getInstance().isEnable()) {
      CrashMonitor.getInstance().getWebViewStateChangeListener().onWebViewInited(webView());
    }
  }
  
  public void onWebViewDestroyed(Context paramContext)
  {
    PartnerClipboard.setTextToSysClipBoard(paramContext);
    MeiZuNightModeAdapter.getInstance().unRegister(paramContext);
    if (CrashMonitor.getInstance().isEnable()) {
      CrashMonitor.getInstance().getWebViewStateChangeListener().onWebViewDestroyed(webView());
    }
  }
  
  public void onWebViewPaused(Context paramContext)
  {
    PartnerClipboard.setTextToSysClipBoard(paramContext);
  }
  
  public void onWebViewVisibilityChanged(final Context paramContext, View paramView, int paramInt)
  {
    if (paramInt == 0)
    {
      new Handler(Looper.getMainLooper()).post(new Runnable()
      {
        public void run()
        {
          PartnerClipboard.getTextFromSysClipBoard(paramContext);
        }
      });
      if (CrashMonitor.getInstance().isEnable()) {
        CrashMonitor.getInstance().getWebViewStateChangeListener().onWebViewForeground(webView());
      }
    }
    else if ((paramInt == 4) || (paramInt == 8))
    {
      new Handler(Looper.getMainLooper()).post(new Runnable()
      {
        public void run()
        {
          PartnerClipboard.setTextToSysClipBoard(paramContext);
        }
      });
      if (CrashMonitor.getInstance().isEnable()) {
        CrashMonitor.getInstance().getWebViewStateChangeListener().onWebViewBackground(webView());
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\PartnerWebViewObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */