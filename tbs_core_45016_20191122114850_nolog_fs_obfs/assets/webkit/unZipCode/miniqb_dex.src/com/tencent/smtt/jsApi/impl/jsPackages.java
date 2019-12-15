package com.tencent.smtt.jsApi.impl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.SignatureUtil;
import com.tencent.smtt.download.ad.b;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.tbs.common.baseinfo.TbsBaseModuleShell;
import com.tencent.tbs.common.baseinfo.TbsDomainListDataProvider;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.ui.dialog.TBSRunAppConfirmDialog;
import com.tencent.tbs.common.ui.dialog.TBSRunAppConfirmDialog.RunAppConfirmBtnClickListener;
import com.tencent.tbs.common.utils.QBUrlUtils;
import com.tencent.tbs.common.utils.TBSIntentUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.utils.URLUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class jsPackages
  implements IOpenJsApis
{
  public static final int PKG_CHECK_RSLT_UNINSTALLED = -1;
  private Context jdField_a_of_type_AndroidContentContext;
  private OpenJsHelper jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
  
  public jsPackages(OpenJsHelper paramOpenJsHelper)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
    paramOpenJsHelper = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    if (paramOpenJsHelper != null) {
      this.jdField_a_of_type_AndroidContentContext = paramOpenJsHelper.getContext();
    }
  }
  
  private int a(JSONObject paramJSONObject)
  {
    try
    {
      paramJSONObject = paramJSONObject.getString("packagename");
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("pkgName:");
      localStringBuilder.append(paramJSONObject);
      localStringBuilder.toString();
      paramJSONObject = SignatureUtil.getInstalledPKGInfo(paramJSONObject, this.jdField_a_of_type_AndroidContentContext, 0);
      if (paramJSONObject != null) {
        return 1;
      }
    }
    catch (Exception paramJSONObject)
    {
      paramJSONObject.printStackTrace();
    }
    return -1;
  }
  
  private String a(String paramString)
  {
    String str = "";
    Object localObject = str;
    if (!TextUtils.isEmpty(paramString))
    {
      paramString = SignatureUtil.getInstalledPKGInfo(paramString, this.jdField_a_of_type_AndroidContentContext, 64);
      localObject = str;
      if (paramString != null) {
        localObject = new JSONObject();
      }
    }
    try
    {
      ((JSONObject)localObject).put("packagename", paramString.packageName);
      ((JSONObject)localObject).put("versionname", paramString.versionName);
      ((JSONObject)localObject).put("versioncode", paramString.versionCode);
      ((JSONObject)localObject).put("signature", SignatureUtil.getHainaSignMd5(paramString));
      localObject = ((JSONObject)localObject).toString();
      return (String)localObject;
    }
    catch (JSONException paramString) {}
    return "";
  }
  
  private void a(final int paramInt)
  {
    HashMap local1 = new HashMap() {};
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("upload2Beacon: MTT_TBS_JS_RUN_APK_REPORT data=");
    localStringBuilder.append(local1);
    localStringBuilder.toString();
    X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_AndroidContentContext).upLoadToBeacon("MTT_TBS_JS_RUN_APK_REPORT", local1);
  }
  
  private void a(JSONObject paramJSONObject)
  {
    Object localObject1 = new HashMap();
    ((HashMap)localObject1).put("url", this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getUrl());
    X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_AndroidContentContext).upLoadToBeacon("MTT_TBS_JS_RUNAPP", (Map)localObject1);
    String str2 = paramJSONObject.optString("url");
    String str1 = paramJSONObject.optString("packagename");
    String str3 = paramJSONObject.optString("PosID");
    String str4 = paramJSONObject.optString("windowType");
    String str5 = paramJSONObject.optString("extraInfo");
    Object localObject2 = paramJSONObject.optString("cookie");
    String str7 = paramJSONObject.optString("filename");
    String str6 = paramJSONObject.optString("mimetype");
    String str8 = paramJSONObject.optString("from");
    String str9 = paramJSONObject.optString("tbs_download_scene");
    paramJSONObject = null;
    try
    {
      localObject1 = this.jdField_a_of_type_AndroidContentContext.getApplicationContext();
      if (localObject1 != null) {
        paramJSONObject = ((Context)localObject1).getApplicationInfo().packageName;
      }
      localObject1 = paramJSONObject;
    }
    catch (Exception paramJSONObject)
    {
      paramJSONObject.printStackTrace();
      localObject1 = null;
    }
    try
    {
      if (!TextUtils.isEmpty(str2))
      {
        if ((!TextUtils.isEmpty(str9)) && (str1.equalsIgnoreCase("com.tencent.mtt")))
        {
          paramJSONObject = TBSIntentUtils.getOpenUrlAndDownloadInQQBrowserWithReport(this.jdField_a_of_type_AndroidContentContext, true, "qb://home", str2, (String)localObject2, str7, str6, str8, (String)localObject1, str3, str9);
        }
        else
        {
          localObject2 = new Intent("android.intent.action.VIEW");
          ((Intent)localObject2).setPackage(str1);
          ((Intent)localObject2).setData(Uri.parse(str2));
          if (TextUtils.equals(this.jdField_a_of_type_AndroidContentContext.getApplicationContext().getPackageName(), "com.tencent.mobileqq")) {
            ((Intent)localObject2).putExtra("mimetype", str6);
          }
          paramJSONObject = (JSONObject)localObject2;
          if (!TextUtils.isEmpty(str1))
          {
            paramJSONObject = (JSONObject)localObject2;
            if (str1.equalsIgnoreCase("com.tencent.mtt"))
            {
              if (!TextUtils.isEmpty(str3)) {
                ((Intent)localObject2).putExtra("PosID", str3);
              }
              if (!TextUtils.isEmpty((CharSequence)localObject1)) {
                ((Intent)localObject2).putExtra("ChannelID", (String)localObject1);
              }
              if (!TextUtils.isEmpty(str4)) {
                ((Intent)localObject2).putExtra("windowType", str4);
              }
              if (!TextUtils.isEmpty(str5)) {
                ((Intent)localObject2).putExtra("extraInfo", str5);
              }
              paramJSONObject = new StringBuilder();
              paramJSONObject.append("intent params:url=");
              paramJSONObject.append(str2);
              paramJSONObject.append(",posID=");
              paramJSONObject.append(str3);
              paramJSONObject.append(", channelID=");
              paramJSONObject.append((String)localObject1);
              paramJSONObject.append(",windowType=");
              paramJSONObject.append(str4);
              LogUtils.d("jsapi", paramJSONObject.toString());
              paramJSONObject = (JSONObject)localObject2;
            }
          }
        }
      }
      else {
        paramJSONObject = this.jdField_a_of_type_AndroidContentContext.getPackageManager().getLaunchIntentForPackage(str1);
      }
      if (paramJSONObject != null)
      {
        this.jdField_a_of_type_AndroidContentContext.startActivity(paramJSONObject);
        if ((!TextUtils.isEmpty(str1)) && (str1.equalsIgnoreCase("com.tencent.mtt")) && ((this.jdField_a_of_type_AndroidContentContext instanceof Activity))) {
          ((Activity)this.jdField_a_of_type_AndroidContentContext).overridePendingTransition(0, 0);
        }
        b.a().a(str1);
        return;
      }
    }
    catch (Exception paramJSONObject)
    {
      a(6);
      paramJSONObject.printStackTrace();
    }
  }
  
  private boolean a(int paramInt, boolean paramBoolean)
  {
    String str2 = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getOriginUrl();
    String str1 = str2;
    if (TextUtils.isEmpty(str2)) {
      str1 = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getUrl();
    }
    return URLUtil.isUrlMatchDomainList(str1, paramInt, paramBoolean);
  }
  
  private boolean a(String paramString)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("inNoDialogPackageList: package name=");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    localObject = TbsDomainListDataProvider.getInstance(TbsBaseModuleShell.REQ_SRC_TBS).getDomainList(375);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("inNoDialogPackageList: list=");
    localStringBuilder.append(localObject);
    localStringBuilder.toString();
    localObject = ((ArrayList)localObject).iterator();
    while (((Iterator)localObject).hasNext()) {
      if (paramString.equalsIgnoreCase((String)((Iterator)localObject).next())) {
        return true;
      }
    }
    return false;
  }
  
  private String b(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      paramString = SignatureUtil.getInstalledPKGInfo(paramString, this.jdField_a_of_type_AndroidContentContext, 0);
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getAppName: package info=");
      localStringBuilder.append(paramString);
      localStringBuilder.toString();
      if (paramString != null)
      {
        paramString = paramString.applicationInfo;
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("getAppName: application info=");
        localStringBuilder.append(paramString);
        localStringBuilder.toString();
        if (paramString != null)
        {
          paramString = paramString.loadLabel(this.jdField_a_of_type_AndroidContentContext.getPackageManager()).toString();
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("getAppName: appname=");
          localStringBuilder.append(paramString);
          localStringBuilder.toString();
          return paramString;
        }
      }
    }
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
    ((StringBuilder)localObject).append("package");
    ((StringBuilder)localObject).append(".");
    ((StringBuilder)localObject).append(Thread.currentThread().getStackTrace()[3].getMethodName());
    localObject = ((StringBuilder)localObject).toString();
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkJsApiDomain((String)localObject) == 1) {
      return true;
    }
    return this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkQQDomain();
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.setOriginUrl(paramString3);
    if ("isApkInstalled".equals(paramString1)) {
      return isApkInstalled(paramString2, paramJSONObject);
    }
    if ("getApkInfo".equals(paramString1)) {
      return getApkInfo(paramString2, paramJSONObject);
    }
    if ("runApp".equals(paramString1)) {
      return runApk(paramString2, paramJSONObject);
    }
    return null;
  }
  
  public String getApkInfo(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
      a(paramString, "this api without user authorization");
      return "";
    }
    paramString = paramJSONObject.optString("packagename");
    if (TextUtils.isEmpty(paramString)) {
      return "";
    }
    return a(paramString);
  }
  
  public String isApkInstalled(String paramString, JSONObject paramJSONObject)
  {
    int i = -1;
    if (paramJSONObject == null) {
      return Integer.toString(-1);
    }
    if (a())
    {
      i = a(paramJSONObject);
    }
    else
    {
      paramJSONObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
      a(paramString, "this api without user authorization");
    }
    return Integer.toString(i);
  }
  
  public String runApk(final String paramString, final JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return null;
    }
    a(1);
    if (a())
    {
      if (!a(325, true))
      {
        LogUtils.d("jsapi", "has no permission to run app!");
        a(3);
        return null;
      }
      Object localObject3 = paramJSONObject.optString("packagename");
      if (TextUtils.isEmpty((CharSequence)localObject3))
      {
        a(9);
        a(paramString, "failed for illegal package name");
        return null;
      }
      final Object localObject1 = TbsInfoUtils.getQUA2();
      final Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("runApk: qua2=");
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).toString();
      if (a((String)localObject3))
      {
        a(paramJSONObject);
        a(7);
        return null;
      }
      if ((!TextUtils.isEmpty((CharSequence)localObject1)) && (!((String)localObject1).contains("PP=com.tencent.mm")))
      {
        a(paramJSONObject);
        a(7);
        return null;
      }
      localObject1 = this.jdField_a_of_type_AndroidContentContext.getApplicationInfo();
      if (localObject1 != null) {
        localObject1 = (String)((ApplicationInfo)localObject1).loadLabel(this.jdField_a_of_type_AndroidContentContext.getPackageManager());
      } else {
        localObject1 = null;
      }
      localObject2 = localObject1;
      if (TextUtils.isEmpty((CharSequence)localObject1)) {
        localObject2 = "当前应用";
      }
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("即将离开");
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("，打开");
      localObject1 = ((StringBuilder)localObject1).toString();
      localObject2 = b((String)localObject3);
      if (!TextUtils.isEmpty((CharSequence)localObject2))
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append((String)localObject1);
        ((StringBuilder)localObject3).append("“");
        ((StringBuilder)localObject3).append((String)localObject2);
        ((StringBuilder)localObject3).append("”");
        localObject1 = ((StringBuilder)localObject3).toString();
      }
      else
      {
        localObject3 = new StringBuilder();
        ((StringBuilder)localObject3).append((String)localObject1);
        ((StringBuilder)localObject3).append("其他应用");
        localObject1 = ((StringBuilder)localObject3).toString();
      }
      localObject1 = new TBSRunAppConfirmDialog(this.jdField_a_of_type_AndroidContentContext, (String)localObject1);
      ((TBSRunAppConfirmDialog)localObject1).setRunAppConfirmBtnClickListener(new TBSRunAppConfirmDialog.RunAppConfirmBtnClickListener()
      {
        public void onCancel()
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("onCancel: app name=");
          localStringBuilder.append(localObject2);
          localStringBuilder.toString();
          localObject1.dismiss();
          jsPackages.this.a(paramString, "false");
          jsPackages.a(jsPackages.this, 4);
        }
        
        public void onSure()
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("onSure: app name ");
          localStringBuilder.append(localObject2);
          localStringBuilder.toString();
          jsPackages.a(jsPackages.this, paramJSONObject);
          localObject1.dismiss();
          jsPackages.this.a(paramString, "true");
          jsPackages.a(jsPackages.this, 5);
        }
      });
      ((TBSRunAppConfirmDialog)localObject1).show();
      return null;
    }
    a(2);
    paramJSONObject = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    a(paramString, "this api without user authorization");
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsPackages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */