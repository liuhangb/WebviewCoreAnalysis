package com.tencent.smtt.jsApi.impl;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build.VERSION;
import android.os.Vibrator;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

public class jsQb
  implements IOpenJsApis
{
  public static final String ACTION_DAEMON_ACTIVE_PUSH = "com.tencent.mtt.ACTION_DAEMON_ACTIVE_PUSH";
  private Context jdField_a_of_type_AndroidContentContext;
  private OpenJsHelper jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
  private String jdField_a_of_type_JavaLangString = "com.tencent.mtt:service";
  private String b = "com.tencent.mtt";
  
  public jsQb(OpenJsHelper paramOpenJsHelper)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
    paramOpenJsHelper = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    if (paramOpenJsHelper != null) {
      this.jdField_a_of_type_AndroidContentContext = paramOpenJsHelper.getContext();
    }
  }
  
  private PackageInfo a(String paramString, Context paramContext, int paramInt)
  {
    if (paramContext != null)
    {
      if (TextUtils.isEmpty(paramString)) {
        return null;
      }
      paramContext = paramContext.getPackageManager();
    }
    try
    {
      paramString = paramContext.getPackageInfo(paramString, paramInt);
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
    return null;
  }
  
  private String a(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
      a(paramString, "this api without user authorization");
      return null;
    }
    if (paramJSONObject == null) {
      return null;
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.showNormalDialog(paramString, paramJSONObject);
    return null;
  }
  
  private boolean a(Context paramContext, String paramString)
  {
    paramContext = (ActivityManager)paramContext.getSystemService("activity");
    Object localObject;
    if (paramContext != null) {
      if (Integer.parseInt(Build.VERSION.SDK) < 21)
      {
        localObject = paramContext.getRunningAppProcesses();
        if ((localObject != null) && (!((List)localObject).isEmpty()))
        {
          int i = 0;
          while (i < ((List)localObject).size())
          {
            if (((ActivityManager.RunningAppProcessInfo)((List)localObject).get(i)).processName.equals(paramString)) {
              return false;
            }
            i += 1;
          }
        }
      }
      else
      {
        localObject = paramContext.getRunningServices(Integer.MAX_VALUE).iterator();
        while (((Iterator)localObject).hasNext())
        {
          ActivityManager.RunningServiceInfo localRunningServiceInfo = (ActivityManager.RunningServiceInfo)((Iterator)localObject).next();
          String str = localRunningServiceInfo.service.getClassName();
          if ((!TextUtils.isEmpty(paramString)) && (paramString.equals(this.b)) && ((str.equals("com.tencent.mtt.BrowserService")) || (str.equals("com.tencent.mtt.sdk.BrowserSdkService")))) {
            return false;
          }
          if (localRunningServiceInfo.process.equals(paramString)) {
            return false;
          }
        }
      }
    }
    paramContext = paramContext.getRunningTasks(1);
    if ((paramContext != null) && (paramContext.size() > 0))
    {
      localObject = (ActivityManager.RunningTaskInfo)paramContext.get(0);
      paramContext = paramContext.iterator();
      while (paramContext.hasNext()) {
        if (((ActivityManager.RunningTaskInfo)paramContext.next()).topActivity.getPackageName().equals(paramString)) {
          return false;
        }
      }
    }
    return true;
  }
  
  private String b(String paramString, JSONObject paramJSONObject)
  {
    if (!a()) {
      return null;
    }
    if (paramJSONObject == null) {
      return null;
    }
    long l2 = paramJSONObject.optLong("milliseconds");
    long l1 = l2;
    if (l2 <= 0L) {
      l1 = 500L;
    }
    ((Vibrator)this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext().getSystemService("vibrator")).vibrate(l1);
    return null;
  }
  
  private String c(String paramString, JSONObject paramJSONObject)
  {
    if (!a()) {
      return null;
    }
    if (paramJSONObject == null) {
      return null;
    }
    int i = paramJSONObject.optInt("times");
    if (i <= 0) {
      return null;
    }
    paramString = RingtoneManager.getRingtone(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext(), RingtoneManager.getDefaultUri(2));
    long l1;
    long l2;
    if (paramString != null)
    {
      l1 = 0L;
      if (l1 < i)
      {
        paramString.play();
        l2 = 5000L;
      }
    }
    for (;;)
    {
      if ((paramString.isPlaying()) && (l2 > 0L)) {
        l2 -= 100L;
      }
      try
      {
        Thread.sleep(100L);
      }
      catch (InterruptedException paramJSONObject) {}
      l1 += 1L;
      break;
      return null;
    }
  }
  
  private String d(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
      a(paramString, "this api without user authorization");
      return null;
    }
    if (paramJSONObject == null) {
      return null;
    }
    paramString = paramJSONObject.optString("text");
    int i = paramJSONObject.optInt("duration");
    Toast.makeText(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext(), paramString, i).show();
    return null;
  }
  
  private String e(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
      a(paramString, "this api without user authorization");
      return null;
    }
    if (paramJSONObject == null)
    {
      a(paramString, "invalid args!");
      return null;
    }
    if (!Configuration.getInstance(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext()).isIliveEnable())
    {
      a(paramString, "live is disabled!");
      return null;
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.startLive(paramString, paramJSONObject);
    return null;
  }
  
  private String f(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
      a(paramString, "this api without user authorization");
      return null;
    }
    if (paramJSONObject == null)
    {
      a(paramString, "invalid args!");
      return null;
    }
    if (!Configuration.getInstance(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext()).isFullScreenPlayerEnable())
    {
      a(paramString, "fullscreen player is disabled!");
      return null;
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.startFullscreenPlayer(paramString, paramJSONObject);
    return null;
  }
  
  void a(String paramString1, String paramString2)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("ret", paramString2);
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString1, localJSONObject);
      return;
    }
    catch (Exception paramString1) {}
  }
  
  protected boolean a()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("tbs");
    ((StringBuilder)localObject).append(".");
    ((StringBuilder)localObject).append(Thread.currentThread().getStackTrace()[3].getMethodName());
    localObject = ((StringBuilder)localObject).toString();
    int i = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkJsApiDomain((String)localObject);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("checkQQDomain : ");
    localStringBuilder.append((String)localObject);
    localStringBuilder.append("= ");
    localStringBuilder.append(i);
    Log.e("jsapiDomain", localStringBuilder.toString());
    if (i == 1) {
      return true;
    }
    return this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkQQDomain();
  }
  
  public String callSystemChooseWifi(String paramString, JSONObject paramJSONObject)
  {
    paramString = new Intent();
    paramString.setAction("android.settings.WIFI_SETTINGS");
    paramString.setFlags(268435456);
    try
    {
      this.jdField_a_of_type_AndroidContentContext.startActivity(paramString);
      return null;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return "error";
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.setOriginUrl(paramString3);
    if ("appVersion".equals(paramString1)) {
      return "6.5";
    }
    if ("qua".equals(paramString1)) {
      return TbsInfoUtils.getQUA();
    }
    if ("qua2".equals(paramString1)) {
      return TbsInfoUtils.getQUA2();
    }
    if ("toast".equals(paramString1)) {
      return d(paramString2, paramJSONObject);
    }
    if ("beep".equals(paramString1)) {
      return c(paramString2, paramJSONObject);
    }
    if ("vibrate".equals(paramString1)) {
      return b(paramString2, paramJSONObject);
    }
    if ("subscribeChanged".equals(paramString1)) {
      return subscribeChanged(paramJSONObject);
    }
    if ("dialog".equals(paramString1)) {
      return a(paramString2, paramJSONObject);
    }
    if ("isQBRunning".equals(paramString1)) {
      return isQBRunning(paramString2, paramJSONObject);
    }
    if ("isQBServiceRunning".equals(paramString1)) {
      return isQBServiceRunning(paramString2, paramJSONObject);
    }
    if ("runQBService".equals(paramString1)) {
      return runQBService(paramString2, paramJSONObject);
    }
    if ("getCurrConnectedWifiInfo".equals(paramString1)) {
      return getCurrConnectedWifiInfo(paramString2, paramJSONObject);
    }
    if ("callSystemChooseWifi".equals(paramString1)) {
      return callSystemChooseWifi(paramString2, paramJSONObject);
    }
    if ("startLive".equals(paramString1)) {
      e(paramString2, paramJSONObject);
    } else if ("fullscreenPlayer".equals(paramString1)) {
      f(paramString2, paramJSONObject);
    }
    return null;
  }
  
  public String getCurrConnectedWifiInfo(String paramString, JSONObject paramJSONObject)
  {
    paramString = this.jdField_a_of_type_AndroidContentContext;
    if (paramString == null) {
      return "error";
    }
    paramString = (WifiManager)paramString.getSystemService("wifi");
    try
    {
      paramString = paramString.getConnectionInfo();
      if (paramString != null)
      {
        paramJSONObject = new JSONObject();
        paramJSONObject.put("ssid", paramString.getSSID());
        paramString = paramJSONObject.toString();
        return paramString;
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return "error";
  }
  
  public String isQBRunning(String paramString, JSONObject paramJSONObject)
  {
    try
    {
      if (a(this.b, this.jdField_a_of_type_AndroidContentContext, 0) == null) {
        return "false";
      }
      if (a(this.jdField_a_of_type_AndroidContentContext, this.b)) {
        return "false";
      }
      return "true";
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return "error";
  }
  
  public String isQBServiceRunning(String paramString, JSONObject paramJSONObject)
  {
    try
    {
      if (a(this.jdField_a_of_type_AndroidContentContext, this.jdField_a_of_type_JavaLangString)) {
        return "false";
      }
      return "true";
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return "error";
  }
  
  public String runQBService(String paramString, JSONObject paramJSONObject)
  {
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
  
  public String subscribeChanged(JSONObject paramJSONObject)
  {
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsQb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */