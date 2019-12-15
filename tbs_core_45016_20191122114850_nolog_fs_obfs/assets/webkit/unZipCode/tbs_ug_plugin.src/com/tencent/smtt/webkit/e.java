package com.tencent.smtt.webkit;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.common.http.Apn;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.util.h;
import com.tencent.smtt.util.q;
import com.tencent.smtt.util.r;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.tencent.TencentLoadPlugin;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.chromium.base.CommandLine;
import org.chromium.tencent.SmttServiceClientProxy;

public class e
{
  private static Context jdField_a_of_type_AndroidContentContext;
  private static e jdField_a_of_type_ComTencentSmttWebkitE;
  private static List<WeakReference<WebSettingsExtension>> jdField_a_of_type_JavaUtilList = new LinkedList();
  private a jdField_a_of_type_ComTencentSmttWebkitE$a = a.g;
  private b jdField_a_of_type_ComTencentSmttWebkitE$b = b.b;
  private String jdField_a_of_type_JavaLangString = null;
  private boolean jdField_a_of_type_Boolean = false;
  private String jdField_b_of_type_JavaLangString = null;
  private boolean jdField_b_of_type_Boolean = false;
  private String jdField_c_of_type_JavaLangString = null;
  private boolean jdField_c_of_type_Boolean = false;
  private String jdField_d_of_type_JavaLangString = null;
  private boolean jdField_d_of_type_Boolean = false;
  private String jdField_e_of_type_JavaLangString = null;
  private boolean jdField_e_of_type_Boolean = true;
  private String jdField_f_of_type_JavaLangString = null;
  private boolean jdField_f_of_type_Boolean = false;
  private String jdField_g_of_type_JavaLangString = null;
  private boolean jdField_g_of_type_Boolean = true;
  private String h = null;
  private String i = null;
  private String j = null;
  
  public static e a()
  {
    try
    {
      if (jdField_a_of_type_ComTencentSmttWebkitE == null) {
        jdField_a_of_type_ComTencentSmttWebkitE = new e();
      }
      e locale = jdField_a_of_type_ComTencentSmttWebkitE;
      return locale;
    }
    finally {}
  }
  
  static void a(WebSettingsExtension paramWebSettingsExtension)
  {
    try
    {
      Iterator localIterator = jdField_a_of_type_JavaUtilList.iterator();
      while (localIterator.hasNext())
      {
        Object localObject = ((WeakReference)localIterator.next()).get();
        if (localObject == paramWebSettingsExtension) {
          return;
        }
      }
      jdField_a_of_type_JavaUtilList.add(new WeakReference(paramWebSettingsExtension));
      return;
    }
    finally {}
  }
  
  public static boolean a()
  {
    return CommandLine.getInstance().hasSwitch("enable-dead-code-detection");
  }
  
  public static boolean c()
  {
    return CommandLine.getInstance().hasSwitch("enable-auto-page-discarding");
  }
  
  public static boolean d()
  {
    return CommandLine.getInstance().hasSwitch("enable-auto-page-restoration");
  }
  
  public static boolean r()
  {
    return CommandLine.getInstance().hasSwitch("enable-switch-software-detection");
  }
  
  public String a()
  {
    return this.jdField_c_of_type_JavaLangString;
  }
  
  public void a()
  {
    for (;;)
    {
      int k;
      try
      {
        k = SmttServiceProxy.getInstance().getFrameLimitCount(-1);
        int m = 0;
        if (k == -1) {
          break label124;
        }
        if (k == 0)
        {
          k = 0;
          Iterator localIterator = jdField_a_of_type_JavaUtilList.iterator();
          if (localIterator.hasNext())
          {
            if (((WeakReference)localIterator.next()).get() == null)
            {
              jdField_a_of_type_JavaUtilList.remove(m);
              continue;
            }
            ((WebSettingsExtension)((WeakReference)jdField_a_of_type_JavaUtilList.get(m)).get()).setFrameLimitCount(k);
            m += 1;
            continue;
          }
          return;
        }
      }
      finally {}
      if (k <= 100)
      {
        k = 100;
        continue;
        label124:
        k = 100;
      }
    }
  }
  
  public void a(Context paramContext, Map<String, Object> paramMap)
  {
    if ((jdField_a_of_type_AndroidContentContext == null) && (paramContext != null))
    {
      jdField_a_of_type_AndroidContentContext = paramContext.getApplicationContext();
      try
      {
        if ((jdField_a_of_type_AndroidContentContext != null) && (jdField_a_of_type_AndroidContentContext.getPackageName() != null))
        {
          this.jdField_a_of_type_JavaLangString = jdField_a_of_type_AndroidContentContext.getPackageName();
          if (TextUtils.equals("com.tencent.mtt", jdField_a_of_type_AndroidContentContext.getPackageName())) {
            this.jdField_a_of_type_ComTencentSmttWebkitE$a = a.jdField_a_of_type_ComTencentSmttWebkitE$a;
          } else if (TextUtils.equals("com.android.browser", jdField_a_of_type_AndroidContentContext.getPackageName())) {
            this.jdField_a_of_type_ComTencentSmttWebkitE$a = a.b;
          } else if (TextUtils.equals("com.tencent.mm", jdField_a_of_type_AndroidContentContext.getPackageName())) {
            this.jdField_a_of_type_ComTencentSmttWebkitE$a = a.c;
          } else if (TextUtils.equals("com.tencent.mobileqq", jdField_a_of_type_AndroidContentContext.getPackageName())) {
            this.jdField_a_of_type_ComTencentSmttWebkitE$a = a.d;
          } else if (TextUtils.equals("com.qzone", jdField_a_of_type_AndroidContentContext.getPackageName())) {
            this.jdField_a_of_type_ComTencentSmttWebkitE$a = a.e;
          } else if (TextUtils.equals("com.tencent.tbs", jdField_a_of_type_AndroidContentContext.getPackageName())) {
            this.jdField_a_of_type_ComTencentSmttWebkitE$a = a.f;
          }
        }
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
      }
      if (paramMap != null) {
        try
        {
          paramContext = paramMap.get("app_use_scene");
          if ((paramContext != null) && ((paramContext instanceof String)))
          {
            paramContext = (String)paramContext;
            if ((a.c == this.jdField_a_of_type_ComTencentSmttWebkitE$a) && (TextUtils.equals("weapp", paramContext))) {
              this.jdField_a_of_type_ComTencentSmttWebkitE$b = b.jdField_a_of_type_ComTencentSmttWebkitE$b;
            }
          }
          paramContext = paramMap.get("weapp_id");
          if ((paramContext != null) && ((paramContext instanceof String))) {
            this.jdField_c_of_type_JavaLangString = ((String)paramContext);
          } else {
            this.jdField_c_of_type_JavaLangString = null;
          }
          paramContext = paramMap.get("weapp_name");
          if ((paramContext != null) && ((paramContext instanceof String))) {
            this.jdField_d_of_type_JavaLangString = ((String)paramContext);
          } else {
            this.jdField_d_of_type_JavaLangString = null;
          }
          paramContext = paramMap.get("data_directory_suffix");
          if ((paramContext != null) && ((paramContext instanceof String)))
          {
            this.jdField_b_of_type_JavaLangString = ((String)paramContext);
            return;
          }
          this.jdField_b_of_type_JavaLangString = null;
          return;
        }
        catch (Throwable paramContext)
        {
          paramContext.printStackTrace();
        }
      }
    }
  }
  
  public void a(WebSettingsExtension paramWebSettingsExtension, IX5WebViewBase paramIX5WebViewBase)
  {
    paramWebSettingsExtension.setMediaPlaybackRequiresUserGesture(false);
    paramWebSettingsExtension.setMixedContentMode(0);
    if ((this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.jdField_a_of_type_ComTencentSmttWebkitE$a) && (this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.b))
    {
      paramWebSettingsExtension.setUseTbsDefaultSettings(true);
      paramWebSettingsExtension.setLongClickPopupMenuExtensionEnabled(true);
      paramWebSettingsExtension.setIsLongPressMenuSelectCopyEnabled(false);
      if (SmttServiceProxy.getInstance().getContentCacheEnabled()) {
        paramWebSettingsExtension.setContentCacheEnable(true);
      }
      if (("com.tencent.mm".equalsIgnoreCase(jdField_a_of_type_AndroidContentContext.getApplicationInfo().packageName)) && (SmttServiceProxy.getInstance().isWXWholePageTranslateEnabled(jdField_a_of_type_AndroidContentContext))) {
        paramWebSettingsExtension.setWebTranslationEnabled(true);
      } else if ((o()) && (SmttServiceProxy.getInstance().isTranslateOnLongPressEnabled(jdField_a_of_type_AndroidContentContext, paramIX5WebViewBase))) {
        paramWebSettingsExtension.setWebTranslationEnabled(true);
      }
      paramWebSettingsExtension.setGifControl(true);
      paramWebSettingsExtension.setImgBrowser(true);
    }
    else
    {
      paramWebSettingsExtension.setAutoRecoredAndRestoreScaleEnabled(true);
      paramWebSettingsExtension.setTextDecorationUnlineEnabled(false);
      paramWebSettingsExtension.setAutoDetectToOpenFitScreenEnabled(true);
      paramWebSettingsExtension.setForcePinchScaleEnabled(true);
      paramWebSettingsExtension.setExitFlushBeaconConditionalEnable(true);
      paramWebSettingsExtension.setSelectionColorEnabled(false);
      paramWebSettingsExtension.setContentCacheEnable(true);
      paramWebSettingsExtension.setContentCacheCacheableQuirkEnabled(true);
      paramWebSettingsExtension.setHistoryScreenshotEnable(true);
      paramWebSettingsExtension.setBFBeforeUnloadEnabled(false);
      if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.b)
      {
        paramWebSettingsExtension.setEvaluateJavaScriptInSubFrameEnabled(true);
        paramWebSettingsExtension.setSmartFullScreenEnabled(true);
      }
      else
      {
        paramWebSettingsExtension.setWrapLineLayoutEnabled(true);
      }
      paramWebSettingsExtension.setGifControl(false);
      paramWebSettingsExtension.setImgBrowser(true);
      paramWebSettingsExtension.setSkipPageFinishEventForOverrideUrlLoading(true);
    }
    if ((this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.d) || (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.f)) {
      paramWebSettingsExtension.setSkipPageFinishEventForAborted(true);
    }
    paramIX5WebViewBase = b.jdField_a_of_type_ComTencentSmttWebkitE$b;
    paramIX5WebViewBase = this.jdField_a_of_type_ComTencentSmttWebkitE$b;
    if (SmttServiceProxy.getInstance().getContentCacheEnabled()) {
      paramWebSettingsExtension.setContentCacheEnable(true);
    }
    int k = SmttServiceProxy.getInstance().getFrameLimitCount(-1);
    if (k != -1)
    {
      if (k == 0) {
        k = 0;
      } else if (k <= 100) {
        k = 100;
      }
    }
    else {
      k = 100;
    }
    paramWebSettingsExtension.setFrameLimitCount(k);
    a(paramWebSettingsExtension);
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.d)
    {
      if (SmttServiceProxy.getInstance().getFakeLoginEnabled())
      {
        paramWebSettingsExtension.setFakeLoginEnabled(true);
        return;
      }
      paramWebSettingsExtension.setFakeLoginEnabled(false);
    }
  }
  
  public void a(CommandLine paramCommandLine)
  {
    if ((this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.jdField_a_of_type_ComTencentSmttWebkitE$a) && (this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.b))
    {
      paramCommandLine.appendSwitch("enable-webvr");
      paramCommandLine.appendSwitch("enable-v8-idle-gc");
    }
    else
    {
      paramCommandLine.appendSwitch("enable-prolong-screenshot-timeout");
      paramCommandLine.appendSwitch("disable-euser-select");
      paramCommandLine.appendSwitch("disable-create-new-window-for-link");
      paramCommandLine.appendSwitch("enable-content-cache-cacheable-quirk");
      paramCommandLine.appendSwitch("enable-phonenumber-detection");
      paramCommandLine.appendSwitch("disable-text-decoration");
      paramCommandLine.appendSwitch("enable-webvr");
    }
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.b)
    {
      paramCommandLine.appendSwitch("enable-cached-bitmap-for-render");
      paramCommandLine.appendSwitch("enable-clean-unused-texture-immediately");
      paramCommandLine.appendSwitch("disable-scroll-viewport-for-top-controls");
      paramCommandLine.appendSwitch("enable-custom-content-cache-capacity");
    }
    if (((this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.c) && (SmttServiceProxy.getInstance().getWXPCDefaultEnable())) || (SmttServiceProxy.getInstance().getWXPCEnabled())) {
      paramCommandLine.appendSwitch("enable-wxpc-dec");
    }
    if (((this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.d) && (SmttServiceProxy.getInstance().getSharpDefaultEnable())) || (SmttServiceProxy.getInstance().getSharpEnabled())) {
      paramCommandLine.appendSwitch("enable-sharpP-dec");
    }
    if (((this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a) || (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.b)) && (SmttServiceProxy.getInstance().getSharpDefaultEnable()))
    {
      TencentLoadPlugin.loadSO(TencentLoadPlugin.SoType_SharpP);
      if (TencentLoadPlugin.checkExistSO(TencentLoadPlugin.SoType_SharpP)) {
        paramCommandLine.appendSwitch("enable-sharpP-dec");
      }
    }
    if ((this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.jdField_a_of_type_ComTencentSmttWebkitE$a) && (this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.b))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(SmttServiceProxy.getInstance().getTpgEnabledType());
      ((StringBuilder)localObject).append("");
      paramCommandLine.appendSwitchWithValue("enable-tpg-dec", ((StringBuilder)localObject).toString());
    }
    else
    {
      TencentLoadPlugin.loadSO(TencentLoadPlugin.SoType_TPG);
      if (TencentLoadPlugin.checkExistSO(TencentLoadPlugin.SoType_TPG))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append(SmttServiceProxy.getInstance().getTpgEnabledType());
        ((StringBuilder)localObject).append("");
        paramCommandLine.appendSwitchWithValue("enable-tpg-dec", ((StringBuilder)localObject).toString());
      }
    }
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a) {
      r.a().a();
    }
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a) {
      com.tencent.smtt.util.p.a().a();
    }
    if ((this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a) && (Apn.isWifiMode())) {
      q.a().a();
    }
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.c) {
      paramCommandLine.appendSwitch("net_exception_for_wx");
    }
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.d) {
      paramCommandLine.appendSwitch("net_exception_for_qq");
    }
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a) {
      paramCommandLine.appendSwitch("net_exception_for_qb");
    }
    if (((this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.jdField_a_of_type_ComTencentSmttWebkitE$a) && (this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.b)) || ((SmttServiceProxy.getInstance().getAutoPageDiscardingDefaultEnabled()) || (SmttServiceProxy.getInstance().getAutoPageDiscardingEnabled()))) {
      paramCommandLine.appendSwitch("enable-auto-page-discarding");
    }
    if (((this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.jdField_a_of_type_ComTencentSmttWebkitE$a) && (this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.b)) || ((SmttServiceProxy.getInstance().getAutoPageDiscardingDefaultEnabled()) || (SmttServiceProxy.getInstance().getAutoPageDiscardingEnabled()))) {
      paramCommandLine.appendSwitch("enable-auto-page-restoration");
    }
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a)
    {
      paramCommandLine.appendSwitch("enable-dead-code-detection");
      paramCommandLine.appendSwitch("support-video-download");
      paramCommandLine.appendSwitch("enable-render-info-upload");
      paramCommandLine.appendSwitch("enable-switch-software-detection");
    }
    else if (SmttServiceProxy.getInstance().getDeadCodeDetectionEnabled())
    {
      paramCommandLine.appendSwitch("enable-dead-code-detection");
    }
    if (((this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.jdField_a_of_type_ComTencentSmttWebkitE$a) && (this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.d) && (this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.f)) || ((SmttServiceProxy.getInstance().getPrerenderSwitch() != -2) || ((!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) && (SmttServiceProxy.getInstance().isCurrentPkgEnablePrerender(this.jdField_a_of_type_JavaLangString))))) {
      paramCommandLine.appendSwitch("enable-prerender-controller");
    }
    paramCommandLine.appendSwitchWithValue("accessibility-js-url", "http://res.imtt.qq.com/qqbrowser_talkback/AndroidVox_v1.js");
    if ((Build.VERSION.SDK_INT == 14) || (Build.VERSION.SDK_INT == 15)) {
      paramCommandLine.appendSwitch("enable-touch-hover");
    }
    String str = ContextHolder.getInstance().getTbsCoreVersion();
    Object localObject = str;
    if (TextUtils.isEmpty(str)) {
      localObject = "000000";
    }
    paramCommandLine.appendSwitchWithValue("tbs_version_code", (String)localObject);
    paramCommandLine.appendSwitch("enable-content-cache");
    paramCommandLine.appendSwitch("disable-kill-after-bad-ipc");
    if (SmttServiceProxy.getInstance().getCustomDiskCacheEnabled()) {
      paramCommandLine.appendSwitch("enable-disk-cache-sdcard-path");
    }
    paramCommandLine.appendSwitch("disable-webrtc-hw-encoding");
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.jdField_a_of_type_ComTencentSmttWebkitE$a)
    {
      paramCommandLine.appendSwitch("enable-commonweal-404-errorpage");
      paramCommandLine.appendSwitch("enable-x5key");
    }
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.d)
    {
      paramCommandLine.appendSwitch("enable-x5cookie-isolation");
      paramCommandLine.appendSwitch("enable-x5key-default-domain-list");
    }
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a)
    {
      paramCommandLine.appendSwitch("enable-localdns-timeout-turnto-httpdns");
      paramCommandLine.appendSwitch("qb-excute-javascript-for-fetch");
    }
    if ((jdField_a_of_type_AndroidContentContext != null) && (SmttServiceProxy.getInstance().getBDSearchPredictEnable())) {
      paramCommandLine.appendSwitch("enable-bdsearch-predict");
    }
    new h(SmttServiceProxy.getInstance().getNativeOptSwitches()).a(paramCommandLine);
    if (SmttServiceProxy.getInstance().getCanAIAExtension()) {
      paramCommandLine.appendSwitch("enable-switch-aia-extension");
    }
    if (SmttServiceProxy.getInstance().isWasmDisabled()) {
      paramCommandLine.appendSwitch("disable_wasm");
    }
    if (SmttServiceProxy.getInstance().getMSEEnabled()) {
      paramCommandLine.appendSwitch("enable-mse");
    }
    if ((this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.c) || (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.f)) {
      paramCommandLine.appendSwitch("delete-local-storage-for-origin");
    }
    if (((this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a) || (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.b)) && (SmttServiceProxy.getInstance().getBFOptEnabled())) {
      paramCommandLine.appendSwitch("enable-back-forward-opt");
    }
    SmttServiceClientProxy.getInstance().setSmttServiceClient(new p());
  }
  
  public boolean a(IX5WebViewBase paramIX5WebViewBase)
  {
    if (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a) {
      return false;
    }
    return SmttServiceProxy.getInstance().isLongPressQuickSecondMenu_QQ_ThirdApp(jdField_a_of_type_AndroidContentContext, paramIX5WebViewBase);
  }
  
  public boolean a(boolean paramBoolean)
  {
    return (a.jdField_a_of_type_ComTencentSmttWebkitE$a == this.jdField_a_of_type_ComTencentSmttWebkitE$a) || (a.b == this.jdField_a_of_type_ComTencentSmttWebkitE$a) || (paramBoolean);
  }
  
  public String b()
  {
    return this.jdField_d_of_type_JavaLangString;
  }
  
  public void b()
  {
    for (;;)
    {
      int k;
      try
      {
        k = SmttServiceProxy.getInstance().getFrameLimitCount(-1);
        int m = 0;
        if (k == -1) {
          break label124;
        }
        if (k == 0)
        {
          k = 0;
          Iterator localIterator = jdField_a_of_type_JavaUtilList.iterator();
          if (localIterator.hasNext())
          {
            if (((WeakReference)localIterator.next()).get() == null)
            {
              jdField_a_of_type_JavaUtilList.remove(m);
              continue;
            }
            ((WebSettingsExtension)((WeakReference)jdField_a_of_type_JavaUtilList.get(m)).get()).setFrameLimitCount(k);
            m += 1;
            continue;
          }
          return;
        }
      }
      finally {}
      if (k <= 50)
      {
        k = 50;
        continue;
        label124:
        k = 50;
      }
    }
  }
  
  public void b(WebSettingsExtension paramWebSettingsExtension)
  {
    paramWebSettingsExtension.setContentCacheEnable(true);
    paramWebSettingsExtension.setContentCacheCacheableQuirkEnabled(true);
    paramWebSettingsExtension.setHistoryScreenshotEnable(true);
    paramWebSettingsExtension.setBFBeforeUnloadEnabled(false);
    paramWebSettingsExtension.setAutoRemoveAdsEnabled(true);
    paramWebSettingsExtension.setGifControl(false);
    paramWebSettingsExtension.setImgBrowser(false);
  }
  
  public boolean b()
  {
    return CommandLine.getInstance().hasSwitch("enable-sharpP-dec");
  }
  
  public boolean b(boolean paramBoolean)
  {
    return (this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.jdField_a_of_type_ComTencentSmttWebkitE$a) && (this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.b) && (!paramBoolean);
  }
  
  public String c()
  {
    return this.jdField_e_of_type_JavaLangString;
  }
  
  public String d()
  {
    return this.jdField_f_of_type_JavaLangString;
  }
  
  public String e()
  {
    return this.jdField_g_of_type_JavaLangString;
  }
  
  public boolean e()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.d;
  }
  
  public String f()
  {
    return this.h;
  }
  
  public boolean f()
  {
    return (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.b) || (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a);
  }
  
  public String g()
  {
    return this.i;
  }
  
  public boolean g()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.d;
  }
  
  public String h()
  {
    return this.j;
  }
  
  public boolean h()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a != a.jdField_a_of_type_ComTencentSmttWebkitE$a;
  }
  
  public String i()
  {
    return this.jdField_b_of_type_JavaLangString;
  }
  
  public boolean i()
  {
    return this.jdField_c_of_type_Boolean;
  }
  
  public boolean j()
  {
    return this.jdField_d_of_type_Boolean;
  }
  
  public boolean k()
  {
    return this.jdField_e_of_type_Boolean;
  }
  
  public boolean l()
  {
    return this.jdField_f_of_type_Boolean;
  }
  
  public boolean m()
  {
    return (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.b) || (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a);
  }
  
  public boolean n()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a;
  }
  
  public boolean o()
  {
    return (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.c) || (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.d) || (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.f);
  }
  
  public boolean p()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a;
  }
  
  public boolean q()
  {
    return CommandLine.getInstance().hasSwitch("enable-render-info-upload");
  }
  
  public boolean s()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.g;
  }
  
  public boolean t()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.c;
  }
  
  public boolean u()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.c;
  }
  
  public boolean v()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.c;
  }
  
  public boolean w()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a;
  }
  
  public boolean x()
  {
    return this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.c;
  }
  
  public boolean y()
  {
    return (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.jdField_a_of_type_ComTencentSmttWebkitE$a) || (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.c) || (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.d);
  }
  
  public boolean z()
  {
    return (this.jdField_a_of_type_ComTencentSmttWebkitE$a == a.d) && (SmttServiceProxy.getInstance().canUseNewJsDialog());
  }
  
  public static enum a
  {
    static
    {
      jdField_a_of_type_ComTencentSmttWebkitE$a = new a("QB_MODE", 0);
      b = new a("TMS_MODE", 1);
      c = new a("WX_MODE", 2);
      d = new a("QQ_MODE", 3);
      e = new a("QZONE_MODE", 4);
      f = new a("DEMO_MODE", 5);
      g = new a("UNKNOWN_MODE", 6);
      jdField_a_of_type_ArrayOfComTencentSmttWebkitE$a = new a[] { jdField_a_of_type_ComTencentSmttWebkitE$a, b, c, d, e, f, g };
    }
    
    private a() {}
  }
  
  public static enum b
  {
    static
    {
      jdField_a_of_type_ComTencentSmttWebkitE$b = new b("WEAPP_SCENE", 0);
      b = new b("UNKNOWN_SCENE", 1);
      jdField_a_of_type_ArrayOfComTencentSmttWebkitE$b = new b[] { jdField_a_of_type_ComTencentSmttWebkitE$b, b };
    }
    
    private b() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */