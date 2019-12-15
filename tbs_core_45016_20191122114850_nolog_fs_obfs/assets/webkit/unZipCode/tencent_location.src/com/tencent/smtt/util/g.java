package com.tencent.smtt.util;

import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.smtt.net.AwNetworkUtils;
import com.tencent.smtt.net.NetLogLoggerService;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.CookieManager;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy.InnerWebView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class g
{
  private static Context jdField_a_of_type_AndroidContentContext;
  private static final String jdField_a_of_type_JavaLangString;
  private static final String jdField_b_of_type_JavaLangString;
  private static String jdField_f_of_type_JavaLangString = "";
  private static boolean jdField_f_of_type_Boolean;
  private int jdField_a_of_type_Int = 120;
  private Handler jdField_a_of_type_AndroidOsHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      default: 
        
      case 20001: 
        g.b(g.this);
        return;
      case 20000: 
        g.a(g.this);
        if (g.a(g.this))
        {
          paramAnonymousMessage = obtainMessage();
          paramAnonymousMessage.what = 10087;
          paramAnonymousMessage.obj = g.a(g.this);
          g.a(g.this).sendMessageDelayed(paramAnonymousMessage, 2000L);
          return;
        }
        break;
      case 10090: 
        paramAnonymousMessage = (String)paramAnonymousMessage.obj;
        if ((paramAnonymousMessage != null) && (paramAnonymousMessage.length() != 0))
        {
          com.tencent.smtt.livelog.a.a().a(paramAnonymousMessage);
          return;
        }
        com.tencent.smtt.livelog.a.a().b();
        return;
      case 10089: 
        paramAnonymousMessage = new Intent();
        paramAnonymousMessage.setAction("com.tencent.x5.tcpdump.stop");
        g.a().sendBroadcast(paramAnonymousMessage);
        paramAnonymousMessage = obtainMessage();
        paramAnonymousMessage.what = 10086;
        sendMessageDelayed(paramAnonymousMessage, 200L);
        return;
      case 10087: 
        paramAnonymousMessage = (String)paramAnonymousMessage.obj;
        g.a(g.this).loadUrl(paramAnonymousMessage);
        return;
      case 10086: 
        SmttServiceProxy.getInstance().closeAndUploadLogRecord();
        SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_mttlog_stop_record", "string"), 1);
        SmttServiceProxy.getInstance().setQProxyType(0);
        g.a(false);
        if (g.b(g.this) != null)
        {
          paramAnonymousMessage = obtainMessage();
          paramAnonymousMessage.what = 10088;
          paramAnonymousMessage.obj = g.c();
          sendMessageDelayed(paramAnonymousMessage, 100L);
          return;
        }
        break;
      case 10085: 
        NetLogLoggerService.b();
        SmttServiceProxy.getInstance().openLogRecord();
        SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_mttlog_start_record", "string"), 1);
        if (g.a(g.this))
        {
          paramAnonymousMessage = obtainMessage();
          paramAnonymousMessage.what = 10087;
          paramAnonymousMessage.obj = g.a(g.this);
          g.a(g.this).sendMessageDelayed(paramAnonymousMessage, 200L);
        }
        break;
      }
    }
  };
  private TencentWebViewProxy jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy;
  private Map<String, String> jdField_a_of_type_JavaUtilMap = new HashMap();
  private boolean jdField_a_of_type_Boolean = false;
  private int jdField_b_of_type_Int = 1;
  private boolean jdField_b_of_type_Boolean = false;
  private int jdField_c_of_type_Int = -1;
  private String jdField_c_of_type_JavaLangString;
  private boolean jdField_c_of_type_Boolean = false;
  private int jdField_d_of_type_Int = 10;
  private String jdField_d_of_type_JavaLangString = "";
  private boolean jdField_d_of_type_Boolean = true;
  private int jdField_e_of_type_Int = 60;
  private String jdField_e_of_type_JavaLangString = null;
  private boolean jdField_e_of_type_Boolean = true;
  private int jdField_f_of_type_Int = 120;
  private int g = 10;
  
  static
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(MttLog.getSDCardOrPackageDir());
    localStringBuilder.append("/.mttx5debugfile.zip");
    jdField_a_of_type_JavaLangString = localStringBuilder.toString();
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("file://");
    localStringBuilder.append(MttLog.getSDCardOrPackageDir());
    localStringBuilder.append("/QQBrowser/logCat.zip");
    jdField_b_of_type_JavaLangString = localStringBuilder.toString();
    jdField_f_of_type_Boolean = false;
  }
  
  public g(TencentWebViewProxy paramTencentWebViewProxy, String paramString)
  {
    jdField_a_of_type_AndroidContentContext = ContextHolder.getInstance().getContext();
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy = paramTencentWebViewProxy;
    this.jdField_c_of_type_JavaLangString = paramString;
  }
  
  public static String a()
  {
    return jdField_f_of_type_JavaLangString;
  }
  
  private String a(String paramString)
  {
    try
    {
      Object localObject = MessageDigest.getInstance("MD5");
      ((MessageDigest)localObject).update(paramString.getBytes());
      paramString = ((MessageDigest)localObject).digest();
      paramString = new String(Base64.encode(new c().a(paramString), 0));
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(paramString.substring(2, 8));
      ((StringBuilder)localObject).append(paramString.substring(10, 13));
      paramString = ((StringBuilder)localObject).toString();
      return paramString;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return null;
  }
  
  public static void a(String paramString)
  {
    jdField_f_of_type_JavaLangString = paramString;
  }
  
  private boolean a(String paramString)
  {
    try
    {
      Object localObject = MttLog.getLocalRenderInfoDir();
      if (localObject == null) {
        return false;
      }
      localObject = new File((String)localObject, "renderinfo.log");
      if (!((File)localObject).exists()) {
        ((File)localObject).createNewFile();
      }
      localObject = new FileOutputStream((File)localObject);
      ((FileOutputStream)localObject).write(paramString.getBytes());
      ((FileOutputStream)localObject).close();
      return true;
    }
    catch (IOException paramString)
    {
      paramString.printStackTrace();
      return false;
    }
    catch (FileNotFoundException paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  public static String b()
  {
    Object localObject = Build.MODEL;
    try
    {
      String str = new String(((String)localObject).getBytes("UTF-8"), "ISO8859-1");
      localObject = str;
    }
    catch (Exception localException)
    {
      for (;;) {}
    }
    if (((String)localObject).length() > 0) {
      return (String)localObject;
    }
    return (String)localObject;
  }
  
  private void b(String paramString)
  {
    if (jdField_a_of_type_AndroidContentContext == null) {
      return;
    }
    if (Build.VERSION.SDK_INT >= 11)
    {
      android.content.ClipboardManager localClipboardManager = (android.content.ClipboardManager)jdField_a_of_type_AndroidContentContext.getSystemService("clipboard");
      paramString = new ClipData.Item(paramString);
      localClipboardManager.setPrimaryClip(new ClipData("datalabel", new String[] { "text/plain" }, paramString));
      return;
    }
    ((android.text.ClipboardManager)jdField_a_of_type_AndroidContentContext.getSystemService("clipboard")).setText(paramString);
  }
  
  private boolean b()
  {
    Object localObject1 = SmttResource.getUserGuidePageStream();
    if (localObject1 != null)
    {
      Object localObject2 = d.a((InputStream)localObject1, "UTF-8");
      String str = SmttServiceProxy.getInstance().getGUID();
      b(str);
      localObject1 = localObject2;
      if (!this.jdField_d_of_type_Boolean) {
        localObject1 = ((String)localObject2).replace("display:inline", "display:none");
      }
      localObject1 = ((String)localObject1).replace("XXXXXXXXXXXXXXXXXX", str);
      if (this.jdField_a_of_type_Boolean)
      {
        if (this.jdField_b_of_type_Int == 2) {
          localObject1 = ((String)localObject1).replace("TTTT", String.valueOf(this.jdField_a_of_type_Int)).replace("按HOME键提前结束日志并上传。", "请复现问题并等待超时自动上传。");
        } else {
          localObject1 = ((String)localObject1).replace("TTTT", "∞").replace("按HOME键提前结束日志并上传。", "按HOME键手动结束日志并上传。");
        }
      }
      else {
        localObject1 = ((String)localObject1).replace("TTTT", String.valueOf(this.jdField_f_of_type_Int));
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(this.g);
      ((StringBuilder)localObject2).append("");
      localObject1 = ((String)localObject1).replace("SHOWUSERGUID", ((StringBuilder)localObject2).toString());
      if ((this.jdField_b_of_type_Boolean) && (this.jdField_a_of_type_JavaUtilMap.containsKey("openurl")))
      {
        localObject1 = ((String)localObject1).replace("display:none", "display:block");
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("请稍候，App会自动打开");
        ((StringBuilder)localObject2).append((String)this.jdField_a_of_type_JavaUtilMap.get("openurl"));
        localObject1 = ((String)localObject1).replace("OOOOOOOO", ((StringBuilder)localObject2).toString());
      }
      else if ((this.jdField_b_of_type_Boolean) && (this.jdField_a_of_type_Boolean))
      {
        localObject1 = ((String)localObject1).replace("display:none", "display:block");
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append("请稍候，App会自动打开");
        ((StringBuilder)localObject2).append(this.jdField_d_of_type_JavaLangString);
        localObject1 = ((String)localObject1).replace("OOOOOOOO", ((StringBuilder)localObject2).toString());
      }
      else
      {
        localObject1 = ((String)localObject1).replace("OOOOOOOO", "请关闭此页面复现问题，复现完成后请等待");
      }
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().loadDataWithBaseURL("http://debugx5.qq.com/userguide.html", (String)localObject1, "text/html", "UTF-8", "http://debugx5.qq.com/userguide.html");
    }
    return true;
  }
  
  private void c()
  {
    SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_mttlog_start_record_render", "string"), 1);
    AwNetworkUtils.StartRenderMonitor(this.jdField_c_of_type_Int, this.jdField_d_of_type_Int, this.jdField_e_of_type_Int);
    if (this.jdField_b_of_type_Int == 2)
    {
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(20001);
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(20001, this.jdField_a_of_type_Int * 1000);
      SmttServiceProxy localSmttServiceProxy = SmttServiceProxy.getInstance();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(SmttResource.getString("x5_mttlog_will", "string"));
      localStringBuilder.append(this.jdField_a_of_type_Int);
      localStringBuilder.append(SmttResource.getString("x5_mttlog_shutdown", "string"));
      localSmttServiceProxy.showToast(localStringBuilder.toString(), 1);
      return;
    }
    SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_mttlog_shutdown_by_home", "string"), 1);
  }
  
  private String d()
  {
    Object localObject = new Date();
    int i = ((Date)localObject).getHours() / 2;
    localObject = new SimpleDateFormat("yyyy-MM-dd 0").format((Date)localObject);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append((String)localObject);
    localStringBuilder.append(String.valueOf(i));
    localStringBuilder.append(":11");
    return a(localStringBuilder.toString());
  }
  
  private void d()
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_mttlog_stop_record_render", "string"), 1);
    this.jdField_a_of_type_Boolean = false;
    BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
    {
      public void doRun()
      {
        g.a(g.this, this.jdField_a_of_type_JavaLangString);
        MttTimingLog.packAndUploadRenderInfo(MttLog.getLocalRenderInfoDir());
        SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_mttlog_upload_done", "string"), 1);
      }
    });
  }
  
  private static String e()
  {
    Date localDate = new Date();
    return new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss").format(localDate);
  }
  
  public void a()
  {
    if ((a() != null) && (a().startsWith("RenderUpload")) && (this.jdField_b_of_type_Int != 2))
    {
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(20001);
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(20001, 0L);
    }
  }
  
  public void a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, String paramString)
  {
    this.jdField_a_of_type_Boolean = true;
    a("RenderUpload");
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(20000);
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(20001);
    if (paramInt1 == 1) {
      this.jdField_b_of_type_Int = 1;
    } else if (paramInt1 == 2) {
      this.jdField_b_of_type_Int = 2;
    } else {
      this.jdField_b_of_type_Int = 1;
    }
    if (this.jdField_b_of_type_Int == 2)
    {
      this.jdField_a_of_type_Int = paramInt2;
      if (this.jdField_a_of_type_Int <= 0) {
        this.jdField_b_of_type_Int = 1;
      }
    }
    this.jdField_c_of_type_Int = paramInt6;
    this.jdField_d_of_type_Int = paramInt4;
    this.jdField_e_of_type_Int = paramInt5;
    this.jdField_b_of_type_Boolean = false;
    this.jdField_d_of_type_JavaLangString = paramString;
    try
    {
      new URL(this.jdField_d_of_type_JavaLangString);
      this.jdField_b_of_type_Boolean = true;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    this.jdField_b_of_type_Boolean = false;
    this.g = paramInt3;
    if (this.g < 3) {
      this.g = 3;
    }
    b();
    if (!this.jdField_a_of_type_AndroidOsHandler.hasMessages(20000)) {
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(20000, (this.g - 2) * 1000);
    }
  }
  
  public boolean a()
  {
    for (;;)
    {
      try
      {
        localObject1 = new URL(this.jdField_c_of_type_JavaLangString);
        Object localObject3 = ((URL)localObject1).getQuery().split("&");
        j = localObject3.length;
        i = 0;
        if (i < j)
        {
          localObject1 = localObject3[i];
          int k = ((String)localObject1).indexOf("=");
          if (k != -1)
          {
            String str = ((String)localObject1).substring(0, k);
            localObject2 = ((String)localObject1).substring(k + 1);
            if ((str != null) && (str.length() > 0) && (localObject2 != null) && (((String)localObject2).length() > 0))
            {
              localObject1 = localObject2;
              if ("openurl".equals(str))
              {
                localObject1 = localObject2;
                if (((String)localObject2).contains("%26")) {
                  localObject1 = ((String)localObject2).replace("%26", "&");
                }
              }
              this.jdField_a_of_type_JavaUtilMap.put(str, localObject1);
            }
          }
          i += 1;
          continue;
        }
        if ((this.jdField_a_of_type_JavaUtilMap.containsKey("homeup")) && (((String)this.jdField_a_of_type_JavaUtilMap.get("homeup")).equals("false"))) {
          this.jdField_d_of_type_Boolean = false;
        }
        if ((this.jdField_a_of_type_JavaUtilMap.containsKey("openid")) && (((String)this.jdField_a_of_type_JavaUtilMap.get("openid")).equals(d())))
        {
          if (this.jdField_a_of_type_JavaUtilMap.containsKey("openrandom")) {
            a((String)this.jdField_a_of_type_JavaUtilMap.get("openrandom"));
          }
          if (this.jdField_a_of_type_JavaUtilMap.containsKey("uploadlivelog"))
          {
            localObject2 = (String)this.jdField_a_of_type_JavaUtilMap.get("uploadlivelog");
            localObject3 = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
            ((Message)localObject3).what = 10090;
            localObject1 = localObject2;
            if ("true".equals(localObject2)) {
              localObject1 = "";
            }
            ((Message)localObject3).obj = localObject1;
            this.jdField_a_of_type_AndroidOsHandler.sendMessage((Message)localObject3);
          }
          if ((this.jdField_a_of_type_JavaUtilMap.containsKey("uploadguid")) && ("true".equals((String)this.jdField_a_of_type_JavaUtilMap.get("uploadguid")))) {
            X5LogUploadManager.a().a();
          }
          if ((this.jdField_a_of_type_JavaUtilMap.containsKey("rendermonitor")) && (((String)this.jdField_a_of_type_JavaUtilMap.get("rendermonitor")).equals("true")))
          {
            this.jdField_a_of_type_Boolean = true;
            a("RenderUpload");
            this.jdField_a_of_type_AndroidOsHandler.removeMessages(20000);
            this.jdField_a_of_type_AndroidOsHandler.removeMessages(20001);
            if (!this.jdField_a_of_type_JavaUtilMap.containsKey("stoptype")) {}
          }
        }
      }
      catch (Exception localException1)
      {
        Object localObject1;
        int j;
        int i;
        Object localObject2;
        label593:
        label1795:
        localException1.printStackTrace();
        SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_mttlog_check_url", "string"), 1);
        return false;
      }
      try
      {
        this.jdField_b_of_type_Int = Integer.parseInt((String)this.jdField_a_of_type_JavaUtilMap.get("stoptype"));
      }
      catch (NumberFormatException localNumberFormatException1) {}
    }
    this.jdField_b_of_type_Int = 1;
    if ((this.jdField_b_of_type_Int != 2) || (this.jdField_a_of_type_JavaUtilMap.containsKey("timeout"))) {}
    try
    {
      this.jdField_a_of_type_Int = Integer.parseInt((String)this.jdField_a_of_type_JavaUtilMap.get("timeout"));
    }
    catch (NumberFormatException localNumberFormatException2)
    {
      for (;;) {}
    }
    this.jdField_a_of_type_Int = 120;
    break label593;
    this.jdField_b_of_type_Int = 1;
    if (this.jdField_a_of_type_JavaUtilMap.containsKey("startmask")) {}
    try
    {
      this.jdField_c_of_type_Int = Integer.parseInt((String)this.jdField_a_of_type_JavaUtilMap.get("startmask"));
    }
    catch (NumberFormatException localNumberFormatException3)
    {
      for (;;) {}
    }
    this.jdField_c_of_type_Int = -1;
    if (this.jdField_a_of_type_JavaUtilMap.containsKey("maincount")) {}
    try
    {
      this.jdField_d_of_type_Int = Integer.parseInt((String)this.jdField_a_of_type_JavaUtilMap.get("maincount"));
    }
    catch (NumberFormatException localNumberFormatException4)
    {
      for (;;) {}
    }
    this.jdField_d_of_type_Int = 10;
    if (this.jdField_a_of_type_JavaUtilMap.containsKey("implcount")) {}
    try
    {
      this.jdField_e_of_type_Int = Integer.parseInt((String)this.jdField_a_of_type_JavaUtilMap.get("implcount"));
    }
    catch (NumberFormatException localNumberFormatException5)
    {
      for (;;) {}
    }
    this.jdField_e_of_type_Int = 60;
    if (this.jdField_a_of_type_JavaUtilMap.containsKey("openurl"))
    {
      this.jdField_b_of_type_Boolean = true;
      this.jdField_d_of_type_JavaLangString = ((String)this.jdField_a_of_type_JavaUtilMap.get("openurl"));
    }
    try
    {
      new URL(this.jdField_d_of_type_JavaLangString);
    }
    catch (Exception localException2)
    {
      for (;;) {}
    }
    this.jdField_b_of_type_Boolean = false;
    if (this.jdField_a_of_type_JavaUtilMap.containsKey("showguidetime")) {}
    try
    {
      this.g = Integer.parseInt((String)this.jdField_a_of_type_JavaUtilMap.get("showguidetime"));
    }
    catch (NumberFormatException localNumberFormatException6)
    {
      for (;;) {}
    }
    this.g = 10;
    b();
    if (!this.jdField_a_of_type_AndroidOsHandler.hasMessages(20000)) {
      this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(20000, (this.g - 2) * 1000);
    }
    return true;
    if ((this.jdField_a_of_type_JavaUtilMap.containsKey("uploaddump")) && ("true".equals((String)this.jdField_a_of_type_JavaUtilMap.get("uploaddump")))) {
      com.tencent.smtt.c.a.a().b();
    }
    if ((this.jdField_a_of_type_JavaUtilMap.containsKey("clearcache")) && (((String)this.jdField_a_of_type_JavaUtilMap.get("clearcache")).equals("true"))) {
      this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().clearCache(true);
    }
    if ((this.jdField_a_of_type_JavaUtilMap.containsKey("cleardns")) && (((String)this.jdField_a_of_type_JavaUtilMap.get("cleardns")).equals("true"))) {
      AwNetworkUtils.clearDNSCache();
    }
    if ((this.jdField_a_of_type_JavaUtilMap.containsKey("clearcookie")) && (((String)this.jdField_a_of_type_JavaUtilMap.get("clearcookie")).equals("true"))) {
      CookieManager.getInstance().removeAllCookie();
    }
    if (this.jdField_a_of_type_JavaUtilMap.containsKey("proxy"))
    {
      localObject2 = (String)this.jdField_a_of_type_JavaUtilMap.get("proxy");
      localObject1 = localObject2;
      if (((String)localObject2).equals("reverse")) {
        if (WebSettingsExtension.getQProxyEnabled()) {
          localObject1 = "false";
        } else {
          localObject1 = "true";
        }
      }
      if (((String)localObject1).equals("true")) {
        SmttServiceProxy.getInstance().setQProxyType(2);
      }
      if (((String)localObject1).equals("false")) {
        SmttServiceProxy.getInstance().setQProxyType(-1);
      }
    }
    else
    {
      SmttServiceProxy.getInstance().setQProxyType(0);
    }
    if ((this.jdField_a_of_type_JavaUtilMap.containsKey("recordlog")) && (((String)this.jdField_a_of_type_JavaUtilMap.get("recordlog")).equals("false"))) {
      this.jdField_e_of_type_Boolean = false;
    }
    if ((!this.jdField_e_of_type_Boolean) || (this.jdField_a_of_type_JavaUtilMap.containsKey("showguidetime"))) {}
    try
    {
      this.g = Integer.parseInt((String)this.jdField_a_of_type_JavaUtilMap.get("showguidetime"));
    }
    catch (NumberFormatException localNumberFormatException7)
    {
      for (;;) {}
    }
    this.g = 10;
    if (this.jdField_a_of_type_JavaUtilMap.containsKey("timeout")) {}
    try
    {
      this.jdField_f_of_type_Int = Integer.parseInt((String)this.jdField_a_of_type_JavaUtilMap.get("timeout"));
    }
    catch (NumberFormatException localNumberFormatException8)
    {
      for (;;) {}
    }
    this.jdField_f_of_type_Int = 120;
    if ((this.jdField_a_of_type_JavaUtilMap.containsKey("tcpdump")) && (((String)this.jdField_a_of_type_JavaUtilMap.get("tcpdump")).equals("true")))
    {
      this.g += 5;
      this.jdField_f_of_type_Int += 5;
      this.jdField_c_of_type_Boolean = true;
      AwNetworkUtils.closeAllSpdySessions();
      localObject1 = new Intent();
      ((Intent)localObject1).setAction("com.tencent.x5.tcpdump.start");
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("/sdcard/QQBrowser/.logTmp/x5debugtcpdump");
      ((StringBuilder)localObject2).append(e());
      ((StringBuilder)localObject2).append(".pcap");
      ((Intent)localObject1).putExtra("storepath", ((StringBuilder)localObject2).toString());
      jdField_a_of_type_AndroidContentContext.sendBroadcast((Intent)localObject1);
    }
    i = this.g;
    j = this.jdField_f_of_type_Int;
    if (i >= j)
    {
      this.jdField_f_of_type_Int = (j + 5);
      this.g = 5;
    }
    if ((this.jdField_a_of_type_JavaUtilMap.containsKey("screen")) && (((String)this.jdField_a_of_type_JavaUtilMap.get("screen")).equals("true"))) {
      jdField_f_of_type_Boolean = true;
    }
    if (this.jdField_c_of_type_Boolean)
    {
      localObject1 = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
      ((Message)localObject1).what = 10089;
      this.jdField_a_of_type_AndroidOsHandler.sendMessageDelayed((Message)localObject1, (this.jdField_f_of_type_Int - 2) * 1000);
    }
    else
    {
      localObject1 = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
      ((Message)localObject1).what = 10086;
      this.jdField_a_of_type_AndroidOsHandler.sendMessageDelayed((Message)localObject1, this.jdField_f_of_type_Int * 1000);
    }
    if (this.jdField_a_of_type_JavaUtilMap.containsKey("openurl"))
    {
      this.jdField_b_of_type_Boolean = false;
      this.jdField_d_of_type_JavaLangString = ((String)this.jdField_a_of_type_JavaUtilMap.get("openurl"));
    }
    try
    {
      localObject1 = new URL(this.jdField_d_of_type_JavaLangString).getProtocol();
      if ((localObject1 != null) && (((String)localObject1).length() != 0) && ((((String)localObject1).equals("http")) || (((String)localObject1).equals("https")))) {
        this.jdField_b_of_type_Boolean = true;
      } else {
        this.jdField_d_of_type_JavaLangString = "about:blank";
      }
    }
    catch (Exception localException3)
    {
      for (;;) {}
    }
    this.jdField_b_of_type_Boolean = false;
    localObject1 = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
    ((Message)localObject1).what = 10085;
    this.jdField_a_of_type_AndroidOsHandler.sendMessageDelayed((Message)localObject1, (this.g - 2) * 1000);
    break label1795;
    localObject1 = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
    ((Message)localObject1).what = 10085;
    this.jdField_a_of_type_AndroidOsHandler.sendMessageDelayed((Message)localObject1, 1500L);
    return b();
    localObject1 = SmttServiceProxy.getInstance().getGUID();
    b((String)localObject1);
    SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_mttlog_finish", "string"), 1);
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("<br><br><h1 align = 'center'> 操作已完成,以下内容已自动复制到剪贴板。</h1></br><h1 align = 'center'> GUID is: ");
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append("</h1>");
    localObject1 = ((StringBuilder)localObject2).toString();
    this.jdField_a_of_type_ComTencentTbsCoreWebkitTencentTencentWebViewProxy.getRealWebView().loadDataWithBaseURL("http://debugx5.qq.com/userguide.html", (String)localObject1, "text/html", "UTF-8", "http://debugx5.qq.com/userguide.html");
    return true;
    SmttServiceProxy.getInstance().showToast(SmttResource.getString("x5_mttlog_url_outofdate", "string"), 1);
    return false;
  }
  
  public void b()
  {
    if ((SmttServiceProxy.getInstance().isLogRecordOpen()) && (this.jdField_d_of_type_Boolean))
    {
      if (this.jdField_c_of_type_Boolean)
      {
        localObject = new Intent();
        ((Intent)localObject).setAction("com.tencent.x5.tcpdump.stop");
        jdField_a_of_type_AndroidContentContext.sendBroadcast((Intent)localObject);
        this.jdField_a_of_type_AndroidOsHandler.removeMessages(10089);
      }
      this.jdField_a_of_type_AndroidOsHandler.removeMessages(10086);
      if (this.jdField_e_of_type_JavaLangString != null) {
        this.jdField_a_of_type_AndroidOsHandler.removeMessages(10088);
      }
      Object localObject = this.jdField_a_of_type_AndroidOsHandler.obtainMessage();
      ((Message)localObject).what = 10086;
      this.jdField_a_of_type_AndroidOsHandler.sendMessageDelayed((Message)localObject, 1500L);
      SmttServiceProxy.getInstance().setQProxyType(0);
      jdField_f_of_type_Boolean = false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */