package com.tencent.smtt.net;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Proxy;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Process;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webview.chromium.tencent.TencentWebViewDatabaseAdapter;
import com.tencent.common.http.Apn;
import com.tencent.common.utils.StringUtils;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.livelog.a;
import com.tencent.smtt.util.o;
import com.tencent.smtt.webkit.AdInfoManager;
import com.tencent.smtt.webkit.RenderMonitor;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.CookieManager;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentURLUtil;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import com.tencent.tbs.tbsshell.common.QProxyPolicies;
import org.apache.http.HttpHost;
import org.chromium.tencent.TencentAwBrowserProcess;

public class QProxyStatusReporter
{
  private static final boolean e;
  private Context jdField_a_of_type_AndroidContentContext;
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private String jdField_a_of_type_JavaLangString = null;
  private HttpHost jdField_a_of_type_OrgApacheHttpHttpHost = null;
  private boolean jdField_a_of_type_Boolean = false;
  private boolean b = true;
  private boolean c = true;
  private boolean d = false;
  
  static
  {
    boolean bool;
    if (Build.VERSION.SDK_INT >= 14) {
      bool = true;
    } else {
      bool = false;
    }
    e = bool;
  }
  
  public QProxyStatusReporter(TencentWebViewProxy paramTencentWebViewProxy)
  {
    this.jdField_a_of_type_AndroidContentContext = paramTencentWebViewProxy.getContext();
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
  }
  
  private String a()
  {
    this.jdField_a_of_type_Boolean = TencentURLUtil.isHttpsUrl(this.jdField_a_of_type_JavaLangString);
    if (this.jdField_a_of_type_Boolean) {
      return "Https连接全部直连。";
    }
    if (StringUtils.isIPAddress(this.jdField_a_of_type_OrgApacheHttpHttpHost.getHostName())) {
      return "URL中的HOST是IP地址而不是域名。";
    }
    if ((this.d) && (!SmttServiceProxy.getInstance().canUseQProxyWhenHasSystemProxy())) {
      return "用户设置了代理，因此走直连。";
    }
    if ((this.d) && (Apn.sApnTypeS != 2))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("当前接入点");
      localStringBuilder.append(getCurApnName());
      localStringBuilder.append("设置了代理，因此走直连。");
      return localStringBuilder.toString();
    }
    if (NetworkSmttService.a(this.jdField_a_of_type_JavaLangString)) {
      return "直连白名单导致走直连";
    }
    if (SmttServiceProxy.getInstance().isVPNConnected()) {
      return "客户端开启vpn走直连";
    }
    int j = getQProxyType();
    int i = 1;
    boolean bool;
    if ((j != 1) && (getQProxyType() != 2))
    {
      if (SmttServiceProxy.getInstance().isEnableDirect()) {
        return "强制走直连开关打开了，走直连。";
      }
      i = SmttServiceProxy.getInstance().getQProxyUsingFlag(this.jdField_a_of_type_JavaLangString, true, WebSettingsExtension.getQProxyEnabled(), WebSettingsExtension.getIsKidMode(), WebSettingsExtension.getIsHostUseQProxy(), null, SmttServiceProxy.getInstance().isKingCardUser());
      bool = QProxyPolicies.shouldUseQProxyAccordingToFlag(i);
    }
    else
    {
      bool = true;
    }
    if (!bool)
    {
      if ((i & 0x200) > 0) {
        return "走代理网络异常转而走了直连";
      }
      if ((0x4000000 & i) > 0) {
        return "本地暂无当前apn对应的代理列表";
      }
      if ((i & 0x400) > 0) {
        return "当前客户端处于WIFI登录验证前的阶段";
      }
      if ((i & 0x800) > 0) {
        return "当前加载的url在代理黑名单中或者直连白名单中";
      }
      if ((i & 0x1000) > 0) {
        return "客户端正在处于代理探测的流程中而未去走代理";
      }
      if ((i & 0x2000) > 0) {
        return "客户端还未登录，未获得服务器端的代理使用标志位";
      }
      if ((i & 0x4000) > 0) {
        return "后台的请求回复中的status-code为8xx，使得客户端转用直连";
      }
      if ((0x8000 & i) > 0) {
        return "后台的请求回复中的status-code为4xx或5xx，使得客户端转用直连";
      }
      if ((0x800000 & i) > 0) {
        return "服务器不允许并且不在代理白名单里";
      }
      if ((0x1000000 & i) > 0) {
        return "用户关闭代理并且不在代理白名单里";
      }
      if (!WebSettingsExtension.getQProxyEnabled()) {
        return "用户没有启用云加速，并且URL不在强制代理的白名单里。";
      }
      i = Apn.getApnTypeS();
      if (SmttServiceProxy.getInstance().getQProxyAddress(i) == null) {
        return "获取代理地址失败，走直连。";
      }
      return "此URL应当走直连，没有检测到走直连的原因。";
    }
    return "此URL应当走云加速，没有检测到走直连的原因。";
  }
  
  private void a()
  {
    int j = Apn.sApnTypeS;
    int i = -1;
    String str2;
    if (j == 4) {
      if (e)
      {
        str2 = System.getProperty("http.proxyHost");
        str1 = System.getProperty("http.proxyPort");
        if (str1 == null) {
          str1 = "-1";
        }
      }
    }
    try
    {
      j = Integer.parseInt(str1);
      str1 = str2;
      i = j;
    }
    catch (NumberFormatException localNumberFormatException1)
    {
      for (;;) {}
    }
    String str1 = str2;
    break label128;
    str1 = null;
    break label128;
    if (e)
    {
      str2 = System.getProperty("http.proxyHost");
      str1 = System.getProperty("http.proxyPort");
      if (str1 == null) {
        str1 = "-1";
      }
    }
    try
    {
      j = Integer.parseInt(str1);
      str1 = str2;
      i = j;
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      for (;;) {}
    }
    str1 = str2;
    break label128;
    str1 = Proxy.getHost(this.jdField_a_of_type_AndroidContentContext);
    i = Proxy.getPort(this.jdField_a_of_type_AndroidContentContext);
    label128:
    if ((str1 != null) && (str1.length() > 0) && (i > 0))
    {
      this.d = true;
      return;
    }
    this.d = false;
  }
  
  private boolean a()
  {
    int i = SmttServiceProxy.getInstance().convertGetToPostLevel();
    if (i == 0) {
      return false;
    }
    if (i == 3) {
      return true;
    }
    if (i == 2) {
      return true;
    }
    return i == 1;
  }
  
  @JavascriptInterface
  public void cleanPlugins()
  {
    SmttServiceProxy.getInstance().cleanPlugins();
  }
  
  @JavascriptInterface
  public void clearCookieAndCache(int paramInt)
  {
    if ((paramInt & 0x1) == 1) {
      CookieManager.getInstance().removeAllCookie();
    }
    if ((paramInt & 0x2) == 2) {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().clearCache(true);
    }
    if ((paramInt & 0x4) == 4)
    {
      AdInfoManager.getInstance().clearAllAdInfo();
      TencentWebViewDatabaseAdapter.getInstance(this.jdField_a_of_type_AndroidContentContext).clearADBlockInfos();
    }
    if ((paramInt & 0x8) == 8) {
      AwNetworkUtils.clearDNSCache();
    }
  }
  
  @JavascriptInterface
  public void clearLog()
  {
    SmttServiceProxy.getInstance().clearLog();
  }
  
  @JavascriptInterface
  public void clearMiniQBUpgradeFile()
  {
    SmttServiceProxy.getInstance().clearMiniQBUpgradeFile();
  }
  
  @JavascriptInterface
  public void clearQbSilentDownloadFile()
  {
    SmttServiceProxy.getInstance().clearQbSilentDownloadFile();
  }
  
  @JavascriptInterface
  public void clearTestAdInfo()
  {
    SmttServiceProxy.getInstance().clearTestAdInfo();
  }
  
  @JavascriptInterface
  public void closeAndUploadLogRecord()
  {
    SmttServiceProxy.getInstance().closeAndUploadLogRecord();
  }
  
  @JavascriptInterface
  public void enableMiniQBAllEntryKeys()
  {
    SmttServiceProxy.getInstance().enableMiniQBAllEntryKeys();
  }
  
  @JavascriptInterface
  public String getADFilterAddrByForce()
  {
    return SmttServiceProxy.getInstance().getAdBlockUrl();
  }
  
  @JavascriptInterface
  public int getAreaType()
  {
    return SmttServiceProxy.getInstance().getAreaType();
  }
  
  @JavascriptInterface
  public boolean getBackEndPostEncription()
  {
    return (SmttServiceProxy.getInstance().isX5ProxyRequestEncrypted()) && (a());
  }
  
  @JavascriptInterface
  public String getCurApnName()
  {
    return Apn.getApnName(Apn.sApnTypeS);
  }
  
  @JavascriptInterface
  public String getDirectConnectReason(String paramString)
  {
    if (!TencentURLUtil.isValidUrl(paramString)) {
      return "非法的URL地址，请重新输入。";
    }
    this.jdField_a_of_type_JavaLangString = TencentURLUtil.stripAnchor(paramString);
    a();
    paramString = new m(this.jdField_a_of_type_JavaLangString);
    this.jdField_a_of_type_OrgApacheHttpHttpHost = new HttpHost(paramString.b, paramString.jdField_a_of_type_Int, paramString.jdField_a_of_type_JavaLangString);
    return a();
  }
  
  @JavascriptInterface
  public boolean getDisableRenderAdFilter()
  {
    return RenderMonitor.g();
  }
  
  @JavascriptInterface
  public boolean getDisableX5pro()
  {
    return RenderMonitor.c();
  }
  
  @JavascriptInterface
  public boolean getEnableCpuRaster()
  {
    return RenderMonitor.e();
  }
  
  @JavascriptInterface
  public boolean getEnableDebugBorders()
  {
    return RenderMonitor.a();
  }
  
  @JavascriptInterface
  public boolean getEnableFpsMeter()
  {
    return RenderMonitor.d();
  }
  
  @JavascriptInterface
  public boolean getEnableInfoPanel()
  {
    return RenderMonitor.b();
  }
  
  @JavascriptInterface
  public boolean getEnablePostEncription()
  {
    return SmttServiceProxy.getInstance().getEnablePostEncription();
  }
  
  @JavascriptInterface
  public boolean getEnableSWDraw()
  {
    return RenderMonitor.f();
  }
  
  @JavascriptInterface
  public boolean getEnablevConsole()
  {
    return SmttServiceProxy.getInstance().getEnablevConsole();
  }
  
  @JavascriptInterface
  public String getEnvironmentType()
  {
    if (SmttServiceProxy.getInstance().isDebugWupEnvironment()) {
      return "测试环境";
    }
    return "正式环境";
  }
  
  @JavascriptInterface
  public String getGUID()
  {
    return SmttServiceProxy.getInstance().getGUID();
  }
  
  @JavascriptInterface
  public String getInterceptDownloadMessage()
  {
    try
    {
      String str = SmttServiceProxy.getInstance().getInterceptDownloadMessage();
      boolean bool = TextUtils.isEmpty(str);
      if (!bool) {
        return str;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      int i = SmttServiceProxy.getInstance().getDownloadInterceptStatus();
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("拦截状态:");
      ((StringBuilder)localObject1).append(i);
      ((StringBuilder)localObject1).append(": ");
      Object localObject2 = ((StringBuilder)localObject1).toString();
      if (i == 1)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("在QB中打开异常");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 2)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("在QB中打开异常2");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 3)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("配置就是不拦截的");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 4)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("没配置拦截方式");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 5)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("不是白名单，也不是手Q的APK或非APK拦截");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 6)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("禁用下载拦截");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 7)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("webview为空或者在miniQB中打开的webview");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 8)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("未知原因");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 9)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("拦截进入miniQB");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 10)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("拦截进入中间页");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 11)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("拦截进入下拉菜单");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else if (i == 12)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("QB打开");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
      else
      {
        localObject1 = localObject2;
        if (i == 13)
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append((String)localObject2);
          ((StringBuilder)localObject1).append("QB打开2");
          localObject1 = ((StringBuilder)localObject1).toString();
        }
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("\n拦截路径：");
      ((StringBuilder)localObject2).append(SmttServiceProxy.getInstance().getDownloadInterceptTrace());
      localObject1 = ((StringBuilder)localObject2).toString();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("\n是否MiniQB Debug环境：");
      localObject1 = ((StringBuilder)localObject2).toString();
      if (SmttServiceProxy.getInstance().isDebugMiniQBEnv())
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("是");
        localObject1 = ((StringBuilder)localObject2).toString();
      }
      else
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append((String)localObject1);
        ((StringBuilder)localObject2).append("否");
        localObject1 = ((StringBuilder)localObject2).toString();
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("\n拦截弹窗提示次数:");
      ((StringBuilder)localObject2).append(SmttServiceProxy.getInstance().getNotifyDownloadQBCount());
      localObject1 = ((StringBuilder)localObject2).toString();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("\n");
      ((StringBuilder)localObject2).append(SmttServiceProxy.getInstance().getDownloadInterceptKeyMessage());
      localObject1 = ((StringBuilder)localObject2).toString();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("\napk域名白名单(B):");
      ((StringBuilder)localObject2).append(SmttServiceProxy.getInstance().getDownloadInterceptApkWhiteList());
      localObject1 = ((StringBuilder)localObject2).toString();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("\napk域名黑名单(K):");
      ((StringBuilder)localObject2).append(SmttServiceProxy.getInstance().getDownloadInterceptApkBlackList());
      localObject1 = ((StringBuilder)localObject2).toString();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("\n非apk域名黑名单(D):");
      ((StringBuilder)localObject2).append(SmttServiceProxy.getInstance().getDownloadInterceptNoApkBlackList());
      localObject1 = ((StringBuilder)localObject2).toString();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("\n文件类型白名单(C):");
      ((StringBuilder)localObject2).append(SmttServiceProxy.getInstance().getDownloadInterceptFileTypeWhiteList());
      localObject1 = ((StringBuilder)localObject2).toString();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("<textarea rows=\"20\" cols=\"65\" style=\"font-size:9px;outline:none;resize:none;\">");
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("</textarea>");
      return ((StringBuilder)localObject2).toString();
    }
  }
  
  @JavascriptInterface
  public boolean getIsAdFilterEnhance()
  {
    return SmttServiceProxy.getInstance().getIsAdFilterEnhance();
  }
  
  @JavascriptInterface
  public boolean getIsInspectorEnabled()
  {
    return SmttServiceProxy.getInstance().getIsInspectorEnabled();
  }
  
  @JavascriptInterface
  public boolean getIsInspectorMiniProgramEnabled()
  {
    return SmttServiceProxy.getInstance().getIsInspectorMiniProgramEnabled();
  }
  
  @JavascriptInterface
  public boolean getIsKingCardEnable()
  {
    return SmttServiceProxy.getInstance().getIsKingCardEnableAtDebugPage();
  }
  
  @JavascriptInterface
  public boolean getIsWebARDebugEnabled()
  {
    return SmttServiceProxy.getInstance().getIsWebARDebugEnabled();
  }
  
  @JavascriptInterface
  public boolean getIsX5ContentCacheDisabled()
  {
    return SmttServiceProxy.getInstance().getIsX5ContentCacheDisabled();
  }
  
  @JavascriptInterface
  public boolean getIsX5ContentCacheLogEnabled()
  {
    return SmttServiceProxy.getInstance().getIsX5ContentCacheLogEnabled();
  }
  
  @JavascriptInterface
  public boolean getIsX5CustomFontDisabled()
  {
    return SmttServiceProxy.getInstance().getIsX5CustomFontDisabled();
  }
  
  @JavascriptInterface
  public boolean getIsX5jscoreInspectorEnabled()
  {
    return SmttServiceProxy.getInstance().getIsX5jscoreInspectorEnabled();
  }
  
  @JavascriptInterface
  public void getLocalPluginInfo()
  {
    byte[] arrayOfByte = SmttServiceProxy.getInstance().getLocalPluginInfo();
    if (arrayOfByte != null)
    {
      String str = SmttServiceProxy.getInstance().getLocalPluginPath();
      a.a().a(str, arrayOfByte);
    }
  }
  
  @JavascriptInterface
  public boolean getLogBeaconUpload()
  {
    return SmttServiceProxy.getInstance().getLogBeaconUpload();
  }
  
  @JavascriptInterface
  public String getMiniQBUA()
  {
    Object localObject1 = this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
    if ((localObject1 != null) && ((localObject1 instanceof X5WebViewAdapter)))
    {
      localObject1 = ((TencentWebViewProxy)localObject1).getSettings();
      if (localObject1 != null)
      {
        if (((X5WebViewAdapter)this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy).isMiniQB()) {
          return ((IX5WebSettings)localObject1).getUserAgent();
        }
        String str = ((IX5WebSettings)localObject1).getUserAgent();
        localObject1 = null;
        Object localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(" MiniQB/");
        ((StringBuilder)localObject2).append(SmttServiceProxy.getInstance().getMiniQBVC());
        ((StringBuilder)localObject2).append(" ");
        localObject2 = ((StringBuilder)localObject2).toString();
        if (!TextUtils.isEmpty(str))
        {
          if (str.contains("TBS/"))
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append((String)localObject2);
            ((StringBuilder)localObject1).append("TBS");
            return str.replace("TBS", ((StringBuilder)localObject1).toString());
          }
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append(str);
          ((StringBuilder)localObject1).append((String)localObject2);
          localObject1 = ((StringBuilder)localObject1).toString();
        }
        return (String)localObject1;
      }
    }
    return SmttServiceProxy.getInstance().getMiniQBUA();
  }
  
  @JavascriptInterface
  public String getMiniQBVC()
  {
    return SmttServiceProxy.getInstance().getMiniQBVC();
  }
  
  @JavascriptInterface
  public void getPluginForce()
  {
    SmttServiceProxy.getInstance().getPluginForce(this.jdField_a_of_type_AndroidContentContext);
  }
  
  @JavascriptInterface
  public String getProxyAddressType()
  {
    int i = SmttServiceProxy.getInstance().getQProxyType();
    if ((i >= 1) && (i <= 4)) {
      if ((i != 1) && (i != 3))
      {
        if ((i == 2) || (i == 4)) {
          return "HTTP2";
        }
      }
      else {
        return "QUIC";
      }
    }
    i = SmttServiceProxy.getInstance().getProxyAddressType();
    if (i == 1) {
      return "HTTP2";
    }
    if (i == 2) {
      return "QUIC";
    }
    return "NULL";
  }
  
  @JavascriptInterface
  public int getQProxyType()
  {
    return SmttServiceProxy.getInstance().getQProxyType();
  }
  
  @JavascriptInterface
  public String getQUA()
  {
    return SmttServiceProxy.getInstance().getQUA();
  }
  
  @JavascriptInterface
  public String getQUA2()
  {
    return SmttServiceProxy.getInstance().getQUA2();
  }
  
  @JavascriptInterface
  public boolean getQbInstallStatus()
  {
    return SmttServiceProxy.getInstance().getQbInstallStatus();
  }
  
  @JavascriptInterface
  public boolean getSPDYProxySign()
  {
    return SmttServiceProxy.getInstance().getSPDYProxySign();
  }
  
  @JavascriptInterface
  public boolean getShouldDisplayHideFunction()
  {
    return SmttServiceProxy.getInstance().getShouldDisplayHideFunction();
  }
  
  @JavascriptInterface
  public int getSubResDirect()
  {
    return SmttServiceProxy.getInstance().getSubResDirect();
  }
  
  @JavascriptInterface
  public String getTbsCoreVersion()
  {
    return SmttServiceProxy.getInstance().getTbsCoreVersion();
  }
  
  @JavascriptInterface
  public String getTbsSdkVersion()
  {
    return SmttServiceProxy.getInstance().getTbsSdkVersion();
  }
  
  @JavascriptInterface
  public int getTestAdDuration()
  {
    return SmttServiceProxy.getInstance().getTestAdDuration();
  }
  
  @JavascriptInterface
  public String getTestAdMainUrl()
  {
    return SmttServiceProxy.getInstance().getTestAdMainUrl();
  }
  
  @JavascriptInterface
  public int getTestAdOpenType()
  {
    return SmttServiceProxy.getInstance().getTestAdOpenType();
  }
  
  @JavascriptInterface
  public int getTestAdPos()
  {
    return SmttServiceProxy.getInstance().getTestAdPos();
  }
  
  @JavascriptInterface
  public String getTestAdStatKey()
  {
    return SmttServiceProxy.getInstance().getTestAdStatKey();
  }
  
  @JavascriptInterface
  public int getTestAdType()
  {
    return SmttServiceProxy.getInstance().getTestAdType();
  }
  
  @JavascriptInterface
  public String getTestAdUrl()
  {
    return SmttServiceProxy.getInstance().getTestAdUrl();
  }
  
  @JavascriptInterface
  public boolean getUseX5DataDirEnabled()
  {
    return TencentAwBrowserProcess.isUsingX5DataDir();
  }
  
  @JavascriptInterface
  public String getWupAddressByForce()
  {
    return SmttServiceProxy.getInstance().getWupAddressByForce();
  }
  
  @JavascriptInterface
  public String getWupProxyUrl()
  {
    return SmttServiceProxy.getInstance().getWupAddressByForce();
  }
  
  @JavascriptInterface
  public void getWupWhiteListInfo()
  {
    if (SmttServiceProxy.getInstance().getLocalConfigFilePath() != null) {
      a.a().a(SmttServiceProxy.getInstance().getLocalConfigFilePath());
    }
  }
  
  @JavascriptInterface
  public boolean isGameEngineUseSandbox()
  {
    return SmttServiceProxy.getInstance().isGameEngineUseSandbox();
  }
  
  @JavascriptInterface
  public boolean isGameFrameworkEnabled()
  {
    return SmttServiceProxy.getInstance().isGameFrameworkEnabled();
  }
  
  @JavascriptInterface
  public boolean isGameUseTestEnv()
  {
    return SmttServiceProxy.getInstance().getGameServiceEnv() != 0;
  }
  
  @JavascriptInterface
  public boolean isLogRecordEnabled()
  {
    return SmttServiceProxy.getInstance().isLogRecordOpen();
  }
  
  @JavascriptInterface
  public boolean isPerformanceLogRecordEnabled()
  {
    return SmttServiceProxy.getInstance().isPerformanceLogRecordOpen();
  }
  
  @JavascriptInterface
  public boolean isPluginDebugEnv()
  {
    Context localContext = this.jdField_a_of_type_AndroidContentContext;
    if (localContext == null) {
      return false;
    }
    return localContext.getSharedPreferences("plugin_setting", 0).getBoolean("key_plugin_debug_env", false);
  }
  
  @JavascriptInterface
  public boolean isRecordingTrace()
  {
    return SmttServiceProxy.getInstance().isRecordingTrace();
  }
  
  @JavascriptInterface
  public boolean isTestMiniQBEnv()
  {
    return SmttServiceProxy.getInstance().isTestMiniQBEnv();
  }
  
  @JavascriptInterface
  public void killX5Process()
  {
    Process.killProcess(Process.myPid());
  }
  
  @JavascriptInterface
  public String loadBKHT()
  {
    return SmttServiceProxy.getInstance().loadBKHT();
  }
  
  @JavascriptInterface
  public String loadCustomHostsList()
  {
    return SmttServiceProxy.getInstance().loadCustomHostsList();
  }
  
  @JavascriptInterface
  public String loadQProxyAddressList()
  {
    return SmttServiceProxy.getInstance().loadQProxyAddressList();
  }
  
  @JavascriptInterface
  public String logDebugExec(String paramString)
  {
    return SmttServiceProxy.getInstance().logDebugExec(paramString);
  }
  
  @JavascriptInterface
  public boolean logFirstTextAndFirstScreen()
  {
    return SmttServiceProxy.getInstance().logFirstTextAndFirstScreenEnable();
  }
  
  @JavascriptInterface
  public void openCLog() {}
  
  @JavascriptInterface
  public void openLogRecord()
  {
    SmttServiceProxy.getInstance().openLogRecord("debugx5open");
  }
  
  @JavascriptInterface
  public void recoderTrace(String paramString)
  {
    SmttServiceProxy.getInstance().recoderTrace(paramString);
  }
  
  @JavascriptInterface
  public void resetRecommend()
  {
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("type", Integer.valueOf(274496));
    localContentValues.put("value", Integer.valueOf(0));
    Uri localUri = Uri.parse("content://com.tencent.mm.storage.provider.emotion/userinfo");
    this.jdField_a_of_type_AndroidContentContext.getContentResolver().update(localUri, localContentValues, null, null);
  }
  
  @JavascriptInterface
  public void saveBKHT(String paramString)
  {
    SmttServiceProxy.getInstance().saveBKHT(paramString);
  }
  
  @JavascriptInterface
  public void saveCustomHostsList(String paramString)
  {
    AwNetworkUtils.setCustomHosts(paramString);
    SmttServiceProxy.getInstance().saveCustomHostsList(paramString);
  }
  
  @JavascriptInterface
  public void saveQProxyAddressList(String paramString)
  {
    SmttServiceProxy.getInstance().saveQProxyAddressList(paramString);
  }
  
  @JavascriptInterface
  public void sendMiniQBWupRequestForSwitch()
  {
    SmttServiceProxy.getInstance().sendMiniQBWupRequestForSwitch(this.jdField_a_of_type_AndroidContentContext);
  }
  
  @JavascriptInterface
  public void sendWupDomainRequestByForce()
  {
    SmttServiceProxy.getInstance().pullWupDomainInfoByForce();
  }
  
  @JavascriptInterface
  public void sendWupIpListRequestByForce()
  {
    SmttServiceProxy.getInstance().pullWupIpListByForce();
  }
  
  @JavascriptInterface
  public void sendWupPreferenceRequestByForce()
  {
    SmttServiceProxy.getInstance().pullWupPreferenceByForce();
  }
  
  @JavascriptInterface
  public void setADFilterAddrByForce(String paramString)
  {
    SmttServiceProxy.getInstance().setAdBlockUrl(paramString);
    AdInfoManager.getInstance().clearAllAdInfo();
    TencentWebViewDatabaseAdapter.getInstance(this.jdField_a_of_type_AndroidContentContext).clearADBlockInfos();
    AwNetworkUtils.enableADFilterAddr(paramString);
  }
  
  @JavascriptInterface
  public void setAdFitlerEnhance(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setAdFitlerEnhance(paramBoolean);
  }
  
  @JavascriptInterface
  public void setAreaType(int paramInt)
  {
    SmttServiceProxy.getInstance().setAreaType(paramInt);
  }
  
  @JavascriptInterface
  public void setDisableRenderAdFilter(boolean paramBoolean)
  {
    RenderMonitor.g(paramBoolean);
  }
  
  @JavascriptInterface
  public void setDisableX5pro(boolean paramBoolean)
  {
    RenderMonitor.c(paramBoolean);
  }
  
  @JavascriptInterface
  public void setEnableClearFrameBufferForEnd(boolean paramBoolean, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    RenderMonitor.b(paramBoolean, (int)paramLong, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  @JavascriptInterface
  public void setEnableClearFrameBufferForStart(boolean paramBoolean, long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    RenderMonitor.a(paramBoolean, (int)paramLong, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  @JavascriptInterface
  public void setEnableCpuRaster(boolean paramBoolean)
  {
    RenderMonitor.e(paramBoolean);
  }
  
  @JavascriptInterface
  public void setEnableDebugBorders(boolean paramBoolean)
  {
    RenderMonitor.a(paramBoolean);
  }
  
  @JavascriptInterface
  public void setEnableFpsMeter(boolean paramBoolean)
  {
    RenderMonitor.d(paramBoolean);
  }
  
  @JavascriptInterface
  public void setEnableInfoPanel(boolean paramBoolean)
  {
    RenderMonitor.b(paramBoolean);
  }
  
  @JavascriptInterface
  public void setEnableKeyLog(boolean paramBoolean)
  {
    RenderMonitor.h(paramBoolean);
  }
  
  @JavascriptInterface
  public void setEnablePostEncription(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setEnablePostEncription(paramBoolean);
  }
  
  @JavascriptInterface
  public void setEnableSWDraw(boolean paramBoolean)
  {
    RenderMonitor.f(paramBoolean);
  }
  
  @JavascriptInterface
  public void setEnablevConsole(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setEnablevConsole(paramBoolean);
  }
  
  @JavascriptInterface
  public void setGameSandbox(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setGameSandbox(paramBoolean);
  }
  
  @JavascriptInterface
  public void setGameTestEnv(boolean paramBoolean)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  @JavascriptInterface
  public void setIsInspectorEnabled(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setIsInspectorEnabled(paramBoolean);
  }
  
  @JavascriptInterface
  public void setIsInspectorMiniProgramEnabled(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setIsInspectorMiniProgramEnabled(paramBoolean);
  }
  
  @JavascriptInterface
  public void setIsWebARDebugEnabled(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setIsWebARDebugEnabled(paramBoolean);
  }
  
  @JavascriptInterface
  public void setIsX5ContentCacheDisabled(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setIsX5ContentCacheDisabled(paramBoolean);
  }
  
  @JavascriptInterface
  public void setIsX5ContentCacheLogEnabled(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setIsX5ContentCacheLogEnabled(paramBoolean);
  }
  
  @JavascriptInterface
  public void setIsX5CustomFontDisabled(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setIsX5CustomFontDisabled(paramBoolean);
  }
  
  @JavascriptInterface
  public void setIsX5jscoreInspectorEnabled(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setIsX5jscoreInspectorEnabled(paramBoolean);
  }
  
  @JavascriptInterface
  public void setKingCardEnable(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setKingCardEnableAtDebugPage(paramBoolean);
  }
  
  @JavascriptInterface
  public void setLogBeaconUpload(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setLogBeaconUpload(paramBoolean);
  }
  
  @JavascriptInterface
  public void setLogFirstTextAndFirstScreen(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setLogFirstTextAndFirstScreen(paramBoolean);
  }
  
  @JavascriptInterface
  public void setPerformanceLogFlag(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setPerformanceLogFlag(paramBoolean);
  }
  
  @JavascriptInterface
  public void setQProxyAddressIndex(int paramInt)
  {
    SmttServiceProxy.getInstance().setQProxyAddressIndex(paramInt);
  }
  
  @JavascriptInterface
  public void setQProxyType(int paramInt)
  {
    SmttServiceProxy.getInstance().setQProxyType(paramInt);
  }
  
  @JavascriptInterface
  public void setQbDispatchAuto()
  {
    SmttServiceProxy.getInstance().setAreaType(0);
    SmttServiceProxy.getInstance().requestServiceByAreaType(0);
  }
  
  @JavascriptInterface
  public void setQbDispatchGuangDong()
  {
    SmttServiceProxy.getInstance().setAreaType(1);
    SmttServiceProxy.getInstance().requestServiceByAreaType(1);
  }
  
  @JavascriptInterface
  public void setQbDispatchNotGuangDong()
  {
    SmttServiceProxy.getInstance().setAreaType(2);
    SmttServiceProxy.getInstance().requestServiceByAreaType(2);
  }
  
  @JavascriptInterface
  public void setQbInstallStatus(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setQbInstallStatus(paramBoolean);
  }
  
  @JavascriptInterface
  public void setSPDYProxySign(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setSPDYProxySign(paramBoolean);
  }
  
  @JavascriptInterface
  public void setShouldDisplayHideFunction(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setShouldDisplayHideFunction(paramBoolean);
  }
  
  @JavascriptInterface
  public void setSubResDirect(int paramInt)
  {
    SmttServiceProxy.getInstance().setSubResDirect(paramInt);
  }
  
  @JavascriptInterface
  public void setTbsCoreVersion(String paramString)
  {
    SmttServiceProxy.getInstance().setTbsCoreVersion(paramString);
  }
  
  @JavascriptInterface
  public void setTestAdInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    int i = StringUtils.parseInt(paramString1, 0);
    int j = StringUtils.parseInt(paramString2, 0);
    int k = StringUtils.parseInt(paramString4, 0);
    int m = StringUtils.parseInt(paramString7, 0);
    SmttServiceProxy.getInstance().setTestAdInfo(i, j, paramString3, k, paramString5, paramString6, m);
  }
  
  @JavascriptInterface
  public void setTestMiniQBEnv(boolean paramBoolean)
  {
    SmttServiceProxy.getInstance().setTestMiniQBEnv(paramBoolean);
  }
  
  @JavascriptInterface
  public void setUA(String paramString)
  {
    if (paramString.isEmpty()) {
      return;
    }
    NetworkSmttService.jdField_a_of_type_JavaLangString = paramString;
  }
  
  @JavascriptInterface
  public void setUseX5DataDirEnabled(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      TencentAwBrowserProcess.prepareUseX5DataDir();
      return;
    }
    TencentAwBrowserProcess.disableUseX5DataDir();
  }
  
  public void setWupAddressByForce(String paramString)
  {
    SmttServiceProxy.getInstance().setWupAddressByForce(paramString);
  }
  
  @JavascriptInterface
  public void setWupProxyUrl(String paramString)
  {
    SmttServiceProxy.getInstance().setWupAddressByForce(paramString);
    SmttServiceProxy.getInstance().pullWupInfoByForce();
  }
  
  @JavascriptInterface
  public void setWupSwitchInfo()
  {
    SmttServiceProxy.getInstance().copyWupSwitchesToClipBoard();
  }
  
  @JavascriptInterface
  public void startRenderLog(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString)
  {
    RenderMonitor.a(this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6, paramString);
  }
  
  @JavascriptInterface
  public void stopTrace()
  {
    SmttServiceProxy.getInstance().stopTrace("tracing.data");
  }
  
  @JavascriptInterface
  public void switchPluginDebugEnv(boolean paramBoolean)
  {
    Context localContext = this.jdField_a_of_type_AndroidContentContext;
    if (localContext == null) {
      return;
    }
    localContext.getSharedPreferences("plugin_setting", 0).edit().putBoolean("key_plugin_debug_env", paramBoolean).commit();
    SmttServiceProxy.getInstance().switchPluginDebugEnv();
  }
  
  @JavascriptInterface
  public void tryKillToolsmpProcess()
  {
    o.a().b();
  }
  
  @JavascriptInterface
  public void uploadTrace()
  {
    SmttServiceProxy.getInstance().uploadTrace();
  }
  
  @JavascriptInterface
  public void uploadUgLog()
  {
    SmttServiceProxy.getInstance().uploadUgLog();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\QProxyStatusReporter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */