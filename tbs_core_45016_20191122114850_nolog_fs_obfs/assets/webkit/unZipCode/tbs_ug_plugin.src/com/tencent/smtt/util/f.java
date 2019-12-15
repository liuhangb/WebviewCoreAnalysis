package com.tencent.smtt.util;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient.FileChooserParams;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.WebChromeClient.FileChooserParams;
import java.io.File;
import java.util.Map;

public class f
{
  private static boolean a = false;
  
  public static void a()
  {
    SmttServiceProxy.getInstance().dismiss();
  }
  
  public static void a(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    for (;;)
    {
      Object localObject1;
      try
      {
        str = paramContext.getApplicationInfo().packageName;
        localIntent = new Intent("android.intent.action.VIEW");
        localIntent.addCategory("android.intent.category.BROWSABLE");
        localIntent.setData(Uri.parse(paramString1));
        localObject1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
      }
      catch (Throwable paramContext)
      {
        String str;
        Intent localIntent;
        Object localObject2;
        boolean bool;
        int i;
        return;
      }
      try
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(".");
        ((StringBuilder)localObject2).append(paramContext.getPackageName());
        localObject2 = new File((File)localObject1, ((StringBuilder)localObject2).toString());
        if (!((File)localObject2).exists())
        {
          bool = ((File)localObject2).mkdir();
          if (!bool) {}
        }
        else
        {
          localObject1 = localObject2;
        }
        localObject2 = localObject1;
      }
      catch (Throwable localThrowable)
      {
        continue;
        label322:
        localObject1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10825";
        continue;
        label329:
        paramInt = 1;
      }
    }
    localObject2 = localObject1;
    localObject1 = paramContext.getApplicationInfo().packageName;
    if ("com.tencent.mobileqq".equals(localObject1))
    {
      localObject1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10948";
    }
    else if ("com.tencent.mm".equals(localObject1))
    {
      localObject1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10947";
    }
    else
    {
      if (!"com.qzone".equals(localObject1)) {
        break label322;
      }
      localObject1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10949";
    }
    bool = SmttServiceProxy.getInstance().isDebugMiniQBEnv();
    i = 0;
    if ((!bool) && (SmttServiceProxy.getInstance().openUrlInQQBrowserWithReport(paramContext, paramString1, paramString2, str, paramString4)))
    {
      SmttServiceProxy.getInstance().userBehaviorStatistics(paramString2);
      paramInt = i;
    }
    else
    {
      if ((!SmttServiceProxy.getInstance().isMidPageQbOpenBrowserOnLongPressEnabled(paramContext)) || (SmttServiceProxy.getInstance().isMiniQBDisabled())) {
        break label329;
      }
      if (paramString3 != null) {
        SmttServiceProxy.getInstance().userBehaviorStatistics(paramString3);
      }
      if (SmttServiceProxy.getInstance().startQBWeb(paramContext, paramString1, null, paramInt) != 0) {
        break label329;
      }
      paramInt = i;
    }
    if (paramInt != 0) {
      a("", paramContext, localIntent, (File)localObject2, SmttResource.getString("x5_tbs_wechat_activity_picker_open_browser_title", "string"), (String)localObject1, "qqbrowser_10825.apk", paramString2, null, paramString4);
    }
  }
  
  public static void a(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    a(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, null);
  }
  
  public static void a(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap)
  {
    a(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramMap, null);
  }
  
  public static void a(String paramString1, Context paramContext, Intent paramIntent, File paramFile, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, Map<String, Drawable> paramMap, Bundle paramBundle)
  {
    SmttServiceProxy.getInstance().openBrowserListWithQBDownloader(paramString1, paramContext, paramIntent, paramFile, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramMap, paramBundle);
  }
  
  public static boolean a(Context paramContext, String paramString1, String paramString2, int paramInt, Point paramPoint1, Point paramPoint2)
  {
    boolean bool = false;
    try
    {
      if (SmttServiceProxy.getInstance().isMiniQBDisabled()) {
        return false;
      }
      String str = paramContext.getApplicationInfo().packageName;
      if (paramString2 != null) {
        SmttServiceProxy.getInstance().userBehaviorStatistics(paramString2);
      }
      paramInt = SmttServiceProxy.getInstance().startQBWebAndRecordAnchor(paramContext, paramString1, null, paramInt, paramPoint1, paramPoint2);
      if (paramInt == 0) {
        bool = true;
      }
      return bool;
    }
    catch (Throwable paramContext) {}
    return false;
  }
  
  public static boolean a(Context paramContext, String paramString1, String paramString2, String paramString3, Point paramPoint1, Point paramPoint2)
  {
    try
    {
      String str = paramContext.getApplicationInfo().packageName;
      if (SmttServiceProxy.getInstance().openUrlInQQBrowserWithReportAndRecordAnchor(paramContext, paramString1, paramString2, str, paramString3, paramPoint1, paramPoint2))
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics(paramString2);
        return true;
      }
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    return false;
  }
  
  public static boolean a(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    try
    {
      String str = paramContext.getApplicationInfo().packageName;
      if (SmttServiceProxy.getInstance().openUrlInQQBrowserWithReport(paramContext, paramString1, paramString2, str, paramString3, paramString4))
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics(paramString2);
        return true;
      }
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
    return false;
  }
  
  public static boolean a(IX5WebViewBase paramIX5WebViewBase, com.tencent.tbs.core.webkit.ValueCallback<Uri[]> paramValueCallback, WebChromeClient.FileChooserParams paramFileChooserParams, File paramFile, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, Map<String, Drawable> paramMap)
  {
    paramValueCallback = new android.webkit.ValueCallback()
    {
      public void a(Uri[] paramAnonymousArrayOfUri)
      {
        this.a.onReceiveValue(paramAnonymousArrayOfUri);
      }
    };
    paramFileChooserParams = new IX5WebChromeClient.FileChooserParams() {};
    return SmttServiceProxy.getInstance().openTBSFileChooser(paramIX5WebViewBase, paramValueCallback, paramFileChooserParams, paramFile, paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, null);
  }
  
  public static void b(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, int paramInt)
  {
    try
    {
      String str = paramContext.getApplicationInfo().packageName;
      if (SmttServiceProxy.getInstance().openUrlInQQBrowserWithReport(paramContext, paramString1, paramString2, str, paramString4))
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics(paramString2);
        return;
      }
      if (paramString3 != null) {
        SmttServiceProxy.getInstance().userBehaviorStatistics(paramString3);
      }
      SmttServiceProxy.getInstance().startQBWeb(paramContext, paramString1, null, paramInt);
      return;
    }
    catch (Throwable paramContext) {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\f.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */