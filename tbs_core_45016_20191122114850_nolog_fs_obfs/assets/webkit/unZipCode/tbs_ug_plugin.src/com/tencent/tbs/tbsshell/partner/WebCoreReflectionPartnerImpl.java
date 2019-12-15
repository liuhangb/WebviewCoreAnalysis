package com.tencent.tbs.tbsshell.partner;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.download.qb.QBDownloadManager;
import com.tencent.tbs.common.log.LogManager;
import com.tencent.tbs.tbsshell.IWebCoreReflectionPartner;
import com.tencent.tbs.tbsshell.TBSShell;
import com.tencent.tbs.tbsshell.TBSShellDelegate;
import com.tencent.tbs.tbsshell.TBSShellFactory;
import com.tencent.tbs.tbsshell.WebCoreProxy;
import com.tencent.tbs.tbsshell.common.TbsLog;
import com.tencent.tbs.tbsshell.partner.miniqb.TbsMiniQBProxy;
import com.tencent.tbs.tbsshell.partner.webaccelerator.WebViewPool;
import java.util.Map;

public class WebCoreReflectionPartnerImpl
  implements IWebCoreReflectionPartner
{
  private static final String TAG = "WebCoreReflectionPartnerImpl";
  
  private boolean isWeixinCalled()
  {
    try
    {
      boolean bool = "com.tencent.mm".equals(TbsBaseModuleShell.getCallerContext().getApplicationContext().getApplicationInfo().packageName);
      if (bool) {
        return true;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return false;
  }
  
  public boolean canOpenFile(Context paramContext, String paramString)
  {
    if ((!isWeixinCalled()) && (!WebCoreProxy.canUseX5())) {
      return false;
    }
    return TbsMiniQBProxy.canOpenFile(paramContext, paramString);
  }
  
  public void closeFileReader()
  {
    if ((!isWeixinCalled()) && (!WebCoreProxy.canUseX5())) {
      return;
    }
    TbsMiniQBProxy.closeFileReader();
  }
  
  public void closeMiniQB()
  {
    if ((!isWeixinCalled()) && (!WebCoreProxy.canUseX5())) {
      return;
    }
    TbsMiniQBProxy.closeMiniQB();
  }
  
  public void createCacheWebView(Context paramContext)
  {
    WebViewPool.createCacheWebView(paramContext);
  }
  
  public boolean createMiniQBShortCut(Context paramContext, String paramString1, String paramString2, Bitmap paramBitmap)
  {
    TbsLog.d("WebCoreReflectionPartnerImpl", "createMiniQBShortCut");
    TbsMiniQBProxy.getInstance().createMiniQBShortCut(paramContext, paramString1, paramString2, paramBitmap);
    return true;
  }
  
  public IX5WebViewBase createSDKWebview(Context paramContext)
  {
    long l = System.currentTimeMillis();
    if (WebCoreProxy.canUseX5())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("preinit - createSDKWebview before create: ");
      localStringBuilder.append(System.currentTimeMillis() - l);
      localStringBuilder.toString();
      TBSShellFactory.getTbsShellDelegate().getDataRecord().put("x5webview_clinit_begin", String.valueOf(System.currentTimeMillis()));
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("x5webview_clinit_begin: ");
      localStringBuilder.append(System.currentTimeMillis());
      localStringBuilder.toString();
      paramContext = WebViewPool.acquireWebView(paramContext);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("preinit - createSDKWebview finished - cost: ");
      localStringBuilder.append(System.currentTimeMillis() - l);
      localStringBuilder.toString();
      return paramContext;
    }
    TBSShell.setLoadFailureDetails("createSDKWebview canUseX5 is false!");
    return null;
  }
  
  public String getMiniQBVersion()
  {
    return TbsMiniQBProxy.getMiniQbVersionName();
  }
  
  public boolean installLocalQbApk(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    return QBDownloadManager.getInstance().installDownloadFile(paramContext, paramString1, paramString2, paramBundle);
  }
  
  public boolean isMiniQBShortCutExist(Context paramContext, String paramString)
  {
    TbsLog.d("WebCoreReflectionPartnerImpl", "createMiniQBShortCut");
    return TbsMiniQBProxy.getInstance().isMiniQBShortCutExist(paramContext, paramString, null);
  }
  
  public void onCannotUseX5()
  {
    LogManager.logL1(1003, "canUseX5()=false", new Object[0]);
  }
  
  public void prefetchResource(Context paramContext, String paramString, Map<String, String> paramMap, boolean paramBoolean)
  {
    if (Configuration.getInstance(paramContext).isWebAcceleratorRefetchResourceEnable())
    {
      if (!WebCoreProxy.canUseX5()) {
        return;
      }
      WebCoreProxy.invokeStaticMethod(paramBoolean, "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "prefetchResource", new Class[] { String.class, Map.class }, new Object[] { paramString, paramMap });
    }
  }
  
  public void setWebViewPoolSize(Context paramContext, int paramInt)
  {
    WebViewPool.setWebViewPoolSize(paramInt);
  }
  
  public int startMiniQB(Context paramContext, String paramString, Map<String, String> paramMap, ValueCallback<String> paramValueCallback)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("startQBWeb - url:");
    localStringBuilder.append(paramString);
    localStringBuilder.append("; args:");
    localStringBuilder.append(paramMap);
    localStringBuilder.append("callback: ");
    localStringBuilder.append(paramValueCallback);
    TbsLog.d("WebCoreReflectionPartnerImpl", localStringBuilder.toString());
    if ((!isWeixinCalled()) && (!WebCoreProxy.canUseX5())) {
      return 55534;
    }
    TbsMiniQBProxy.getInstance();
    return TbsMiniQBProxy.startMiniQB(paramContext, paramString, null, paramMap, paramValueCallback);
  }
  
  public int startQBWeb(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("startQBWeb - url:");
    localStringBuilder.append(paramString1);
    localStringBuilder.append("; from:");
    localStringBuilder.append(paramString2);
    localStringBuilder.append("...");
    TbsLog.d("WebCoreReflectionPartnerImpl", localStringBuilder.toString());
    if ((!isWeixinCalled()) && (!WebCoreProxy.canUseX5())) {
      return 55535;
    }
    TbsMiniQBProxy.getInstance();
    return TbsMiniQBProxy.startMiniQB(paramContext, paramString1, paramString2, paramMap);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\WebCoreReflectionPartnerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */