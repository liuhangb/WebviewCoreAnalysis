package com.tencent.tbs.core.partner.menu;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import com.tencent.common.utils.MttLoader;
import com.tencent.common.utils.MttLoader.BrowserInfo;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.util.f;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import java.io.File;
import java.net.URLEncoder;
import java.util.Map;
import org.chromium.base.annotations.UsedByReflection;

public class SearchEngine
{
  public static final String DOWNLOAD_QB_URL = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=11423";
  public static final String DOWNLOAD_QB_URL_DEFAULT = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10945";
  public static final String DOWNLOAD_QB_URL_FORMM = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10604";
  public static final String DOWNLOAD_QB_URL_FORQZONE = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10944";
  public static final String POS_ID_OPEN_QB_VIA_SERACH = "2";
  public static final String QB_DOWNLOAD_FILENAME = "qqbrowser_entersearch.apk";
  private static final String SEARCH_ENGINE = "http://m.sogou.com/web/searchList.jsp?pid=sogou-clse-2996962656838a97&e=1427&g_f=123&keyword=";
  private static final String SEARCH_OPEN_QB = "ZZNS3";
  private static final String SEARCH_OPEN_QB_ACTIVE = "BZCA402";
  private static final String SEARCH_POP_MENU = "ZZNS1";
  public static final String SEARCH_TO_DOWNLOAD_QB = "ZZNS2";
  
  @UsedByReflection("WebCoreProxy.java")
  public static void doSearch(Context paramContext, String paramString1, IX5WebView paramIX5WebView, String paramString2)
  {
    Object localObject3 = getValidSearchKey(paramString1);
    if (SmttServiceProxy.getInstance().isLongPressQuickSecondMenu_QQ_ThirdApp(paramContext, paramIX5WebView))
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("qb://search?keyword=");
      ((StringBuilder)localObject1).append((String)localObject3);
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else
    {
      localObject2 = SmttServiceProxy.getInstance().getTbsWebviewSearchEngineUrl();
      if (localObject2 != null)
      {
        localObject1 = localObject2;
        if (!"".equals(localObject2)) {}
      }
      else
      {
        localObject1 = "http://m.sogou.com/web/searchList.jsp?pid=sogou-clse-2996962656838a97&e=1427&g_f=123&keyword=";
      }
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append((String)localObject3);
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    if (SmttServiceProxy.getInstance().onLongClickSearch(paramContext, (String)localObject1, paramString2, new Bundle())) {
      return;
    }
    if ((MttLoader.isBrowserInstalled(paramContext)) && (!SmttServiceProxy.getInstance().isDebugMiniQBEnv()))
    {
      localObject1 = paramContext.getApplicationContext();
      if (localObject1 == null) {
        localObject1 = paramContext;
      }
      paramContext = ((Context)localObject1).getApplicationInfo().packageName;
      if (paramContext.equals("com.tencent.mobileqq"))
      {
        paramContext = "com.tencent.mobileqq";
      }
      else if (paramContext.equals("com.tencent.mm"))
      {
        paramContext = "com.tencent.mm";
      }
      else if (paramContext.equals("com.qzone"))
      {
        paramContext = "com.qzone";
        paramString2 = "2";
      }
      else
      {
        paramContext = "";
      }
      if ((MttLoader.getBrowserInfo((Context)localObject1).ver >= 8800000) && (SmttServiceProxy.getInstance().isLongPressQuickSecondMenu_QQ_ThirdApp((Context)localObject1, paramIX5WebView)))
      {
        paramString1 = new StringBuilder();
        paramString1.append("qb://search?keyword=");
        paramString1.append((String)localObject3);
        paramString1 = paramString1.toString();
      }
      SmttServiceProxy.getInstance().userBehaviorStatistics("BZCA402");
      SmttServiceProxy.getInstance().openUrlInQQBrowserWithReport((Context)localObject1, paramString1, "fromWebviewSearch", paramContext, paramString2);
      SmttServiceProxy.getInstance().userBehaviorStatistics("ZZNS3");
      return;
    }
    Object localObject2 = new Intent();
    ((Intent)localObject2).setAction("android.intent.action.VIEW");
    ((Intent)localObject2).setData(Uri.parse((String)localObject1));
    paramString1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
    try
    {
      paramString2 = new StringBuilder();
      paramString2.append(".");
      paramString2.append(paramContext.getPackageName());
      paramString2 = new File(paramString1, paramString2.toString());
      if (!paramString2.exists())
      {
        boolean bool = paramString2.mkdir();
        if (!bool) {}
      }
      else
      {
        paramString1 = paramString2;
      }
      paramString2 = paramString1;
    }
    catch (Throwable paramString2)
    {
      for (;;) {}
    }
    paramString2 = paramString1;
    if (SmttServiceProxy.getInstance().isLongPressQuickSecondMenu_QQ_ThirdApp(paramContext, paramIX5WebView)) {
      paramString1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=11423";
    } else if (paramContext.getApplicationInfo().packageName.equals("com.qzone")) {
      paramString1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10944";
    } else if (paramContext.getApplicationInfo().packageName.equals("com.tencent.mm")) {
      paramString1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10604";
    } else {
      paramString1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10945";
    }
    Object localObject1 = SmttResource.getBrowserListIcons(paramContext, (Intent)localObject2, (X5WebViewAdapter)paramIX5WebView);
    localObject3 = new StringBuilder();
    ((StringBuilder)localObject3).append("");
    ((StringBuilder)localObject3).append(SmttServiceProxy.getInstance().isLongPressQuickSecondMenu_QQ_ThirdApp(paramContext, paramIX5WebView));
    ((Intent)localObject2).putExtra("search_direct", ((StringBuilder)localObject3).toString());
    f.a("", paramContext, (Intent)localObject2, paramString2, SmttResource.getString("x5_tbs_wechat_activity_picker_open_with_title", "string"), paramString1, "qqbrowser_entersearch.apk", "ZZNS3", "ZZNS2", "2", (Map)localObject1);
  }
  
  public static String getValidSearchKey(String paramString)
  {
    String str = paramString;
    if (paramString == null) {
      str = "";
    }
    str = str.replace('\r', ' ').replace('\n', ' ');
    paramString = str;
    try
    {
      str = URLEncoder.encode(str, "UTF-8");
      paramString = str;
      str = str.replace("%C2%A0", "%20");
      return str;
    }
    catch (Exception localException) {}
    return paramString;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\menu\SearchEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */