package com.tencent.tbs.core;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.ValueCallback;
import android.webview.chromium.tencent.TencentContentSettingsAdapter;
import com.tencent.common.http.Apn;
import com.tencent.common.http.MimeTypeMap;
import com.tencent.smtt.aladdin.AladdinView;
import com.tencent.smtt.browser.x5.MemoryManager;
import com.tencent.smtt.export.external.easel.interfaces.IEaselView;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5CoreMessy;
import com.tencent.smtt.export.external.interfaces.IX5DateSorter;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.util.CrashTracker;
import com.tencent.smtt.util.MttTraceEvent;
import com.tencent.smtt.util.X5LogManager;
import com.tencent.smtt.webkit.ScaleManager;
import com.tencent.smtt.webkit.SmttPermanentPermissions;
import com.tencent.smtt.webkit.WebViewClientExtension;
import com.tencent.smtt.webkit.WebViewList;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsQbShell;
import com.tencent.tbs.common.baseinfo.TbsWupManager;
import com.tencent.tbs.common.internal.service.IQBSmttService;
import com.tencent.tbs.common.service.QBSmttServiceProxy;
import com.tencent.tbs.common.utils.ExtendJSRule;
import com.tencent.tbs.core.partner.ad.AdInfoHolder;
import com.tencent.tbs.core.partner.menu.SearchEngine;
import com.tencent.tbs.core.webkit.WebSettings;
import com.tencent.tbs.core.webkit.adapter.FileChooserParamsAdapter;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentDateSorter;
import com.tencent.tbs.core.webkit.tencent.TencentWebChromeClientProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewClientProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.tbsshell.IWebCoreReflectionPartner;
import com.tencent.tbs.tbsshell.TBSShellFactory;
import com.tencent.tbs.tbsshell.WebCoreProxy;
import com.tencent.tbs.tbsshell.common.ad.TbsAdUserInfoCollector;
import java.util.Map;
import org.json.JSONObject;

public class X5CoreMessy
  implements IX5CoreMessy
{
  private static boolean mHasDoneWupTask = false;
  private static X5CoreMessy sInstance;
  
  public static X5CoreMessy getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreMessy();
      }
      X5CoreMessy localX5CoreMessy = sInstance;
      return localX5CoreMessy;
    }
    finally {}
  }
  
  public boolean canOpenFile(Context paramContext, String paramString)
  {
    return TBSShellFactory.getWebCoreProxyPartner().canOpenFile(paramContext, paramString);
  }
  
  public void checkTrim(int paramInt)
  {
    MemoryManager.CheckTrim(paramInt);
  }
  
  public void clearAdWebviewInfo()
  {
    AdInfoHolder.getInstance().clearAdInfo();
  }
  
  public void clearAllPermanentPermission()
  {
    SmttPermanentPermissions.getInstance().clearAllPermanentPermission();
  }
  
  public void closeFileReader()
  {
    TBSShellFactory.getWebCoreProxyPartner().closeFileReader();
  }
  
  public void closeMiniQB()
  {
    TBSShellFactory.getWebCoreProxyPartner().closeMiniQB();
  }
  
  public void createCacheWebView(Context paramContext)
  {
    TBSShellFactory.getWebCoreProxyPartner().createCacheWebView(paramContext);
  }
  
  public IX5DateSorter createDateSorter(Context paramContext)
  {
    return new TencentDateSorter(paramContext);
  }
  
  public IX5WebChromeClient createDefaultX5WebChromeClient()
  {
    return new TencentWebChromeClientProxy();
  }
  
  public IX5WebViewClientExtension createDefaultX5WebChromeClientExtension()
  {
    return new WebViewClientExtension();
  }
  
  public IX5WebViewClient createDefaultX5WebViewClient()
  {
    return new TencentWebViewClientProxy();
  }
  
  public IEaselView createEaselView(Context paramContext)
  {
    return new AladdinView(paramContext);
  }
  
  public boolean createMiniQBShortCut(Context paramContext, String paramString1, String paramString2, Bitmap paramBitmap)
  {
    return TBSShellFactory.getWebCoreProxyPartner().createMiniQBShortCut(paramContext, paramString1, paramString2, paramBitmap);
  }
  
  public void destoryScaleManager()
  {
    ScaleManager.getInstance().destroy();
  }
  
  public void doSearch(Context paramContext, String paramString1, IX5WebViewBase paramIX5WebViewBase, String paramString2)
  {
    if ((paramIX5WebViewBase instanceof IX5WebView)) {
      SearchEngine.doSearch(paramContext, paramString1, (IX5WebView)paramIX5WebViewBase, paramString2);
    }
  }
  
  public void doWupTask()
  {
    if (mHasDoneWupTask) {
      return;
    }
    mHasDoneWupTask = true;
    TbsWupManager.getInstance().doMultiWupRequest();
  }
  
  public Bundle getAdWebViewInfoFromX5Core()
  {
    return WebViewList.getAdWebViewInfo();
  }
  
  public String getCrashExtraMessage()
  {
    return CrashTracker.getCrashExtraInfo();
  }
  
  public IX5WebViewBase getCurrentMainWebviewJustForQQandWechat()
  {
    return WebViewList.getCurrentMainWebviewJustForQQandWechat();
  }
  
  public String getDefaultUserAgent(Context paramContext)
  {
    return WebSettings.getDefaultUserAgent(paramContext);
  }
  
  public String getExtendRule(Context paramContext, String paramString)
  {
    return ExtendJSRule.getExtendRule(paramContext, paramString);
  }
  
  public String getGUID()
  {
    return GUIDFactory.getInstance().getStrGuid();
  }
  
  public String getMiniQBVersion()
  {
    return TBSShellFactory.getWebCoreProxyPartner().getMiniQBVersion();
  }
  
  public JSONObject getTbsUserInfo()
  {
    return TbsAdUserInfoCollector.getInstance().getJsonObject();
  }
  
  public boolean getX5RenderPerformDebug()
  {
    return TencentContentSettingsAdapter.X5RenderPerformDebug;
  }
  
  public boolean installLocalQbApk(Context paramContext, String paramString1, String paramString2, Bundle paramBundle)
  {
    return TBSShellFactory.getWebCoreProxyPartner().installLocalQbApk(paramContext, paramString1, paramString2, paramBundle);
  }
  
  public boolean isMiniQBShortCutExist(Context paramContext, String paramString)
  {
    return TBSShellFactory.getWebCoreProxyPartner().isMiniQBShortCutExist(paramContext, paramString);
  }
  
  public boolean isWritingWebCoreLogToFile()
  {
    return X5LogManager.isOpenX5log();
  }
  
  public String mimeTypeMapGetExtensionFromMimeType(String paramString)
  {
    return MimeTypeMap.getSingleton().getExtensionFromMimeType(paramString);
  }
  
  public String mimeTypeMapGetFileExtensionFromUrl(String paramString)
  {
    return MimeTypeMap.getFileExtensionFromUrl(paramString);
  }
  
  public String mimeTypeMapGetMimeTypeFromExtension(String paramString)
  {
    return MimeTypeMap.getSingleton().getMimeTypeFromExtension(paramString);
  }
  
  public boolean mimeTypeMapHasExtension(String paramString)
  {
    return MimeTypeMap.getSingleton().hasExtension(paramString);
  }
  
  public boolean mimeTypeMapHasMimeType(String paramString)
  {
    return MimeTypeMap.getSingleton().hasMimeType(paramString);
  }
  
  public Uri[] parseFileChooserResult(int paramInt, Intent paramIntent)
  {
    return FileChooserParamsAdapter.parseFileChooserResult(paramInt, paramIntent);
  }
  
  public void prefetchResource(Context paramContext, String paramString, Map<String, String> paramMap)
  {
    WebCoreProxy.prefetchResource(paramContext, paramString, paramMap);
  }
  
  public void pvUploadNotifybyUI() {}
  
  public void refreshJavaCoreApnState()
  {
    Apn.getApnType();
  }
  
  public void setAdWebviewInfo(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4, int paramInt3, int paramInt4)
  {
    AdInfoHolder.getInstance().setAdInfoUnit(paramInt1, paramString1, paramString2, paramString3, paramInt2, paramString4, paramInt3, paramInt4);
  }
  
  public void setAdWebviewShouldShow(boolean paramBoolean)
  {
    AdInfoHolder.getInstance().setShouldShow(paramBoolean);
  }
  
  public void setGuidToTbs(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong)
  {
    TbsQbShell.setGuidToTbs(paramArrayOfByte1, paramArrayOfByte2, paramLong);
  }
  
  public void setQBSmttService(Object paramObject)
  {
    if ((paramObject instanceof IQBSmttService)) {
      QBSmttServiceProxy.setLocalSmttService((IQBSmttService)paramObject);
    }
  }
  
  public void setTbsInitPerformanceData(int paramInt, Map<String, String> paramMap)
  {
    WebCoreProxy.setTbsInitPerformanceData(paramInt, paramMap);
  }
  
  public void setWebContentsDebuggingEnabled(boolean paramBoolean)
  {
    TencentWebViewProxy.setWebContentsDebuggingEnabled(paramBoolean);
  }
  
  public void setWebCoreLogWrite2FileFlag(boolean paramBoolean, String paramString)
  {
    X5LogManager.setLogWrite2FileFlag(paramBoolean, paramString);
  }
  
  public void setWebViewPoolSize(Context paramContext, int paramInt)
  {
    TBSShellFactory.getWebCoreProxyPartner().setWebViewPoolSize(paramContext, paramInt);
  }
  
  public void setX5RenderPerformDebug(boolean paramBoolean)
  {
    TencentContentSettingsAdapter.X5RenderPerformDebug = paramBoolean;
  }
  
  public int startMiniQB(Context paramContext, String paramString, Map<String, String> paramMap, ValueCallback<String> paramValueCallback)
  {
    return TBSShellFactory.getWebCoreProxyPartner().startMiniQB(paramContext, paramString, paramMap, paramValueCallback);
  }
  
  public int startQBWeb(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap)
  {
    return TBSShellFactory.getWebCoreProxyPartner().startQBWeb(paramContext, paramString1, paramString2, paramMap);
  }
  
  public void switchContext(IX5WebViewBase paramIX5WebViewBase, Context paramContext)
  {
    if ((paramIX5WebViewBase instanceof X5WebViewAdapter)) {
      ((X5WebViewAdapter)paramIX5WebViewBase).switchContext(paramContext);
    }
  }
  
  public void traceBegin(int paramInt, String paramString)
  {
    MttTraceEvent.begin(paramInt, paramString);
  }
  
  public void traceEnd(int paramInt, String paramString)
  {
    MttTraceEvent.end(paramInt, paramString);
  }
  
  public void uploadWebCoreLog2Server() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreMessy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */