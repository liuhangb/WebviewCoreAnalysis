package com.tencent.smtt.jsApi.impl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.tencent.common.utils.UrlUtils;
import com.tencent.smtt.download.ad.b;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.smtt.jsApi.impl.utils.DownloadController;
import com.tencent.smtt.jsApi.impl.utils.IDownloadCallback;
import com.tencent.smtt.jsApi.impl.utils.ReporterUtil;
import com.tencent.smtt.jsApi.impl.utils.SystemInstallReceiver;
import com.tencent.tbs.common.beacon.X5CoreBeaconUploader;
import com.tencent.tbs.common.config.Configuration;
import com.tencent.tbs.common.download.qb.QBDownloadManager;
import com.tencent.tbs.common.utils.URLUtil;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class jsDownload
  implements IOpenJsApis, IDownloadCallback
{
  public static final String QB_URL_PRFEX1 = "http://appchannel.html5.qq.com/directdown?app=qqbrowser";
  public static final String QB_URL_PRFEX2 = "https://appchannel.html5.qq.com/directdown?app=qqbrowser";
  private Context jdField_a_of_type_AndroidContentContext = null;
  protected OpenJsHelper a;
  private DownloadController jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController;
  String jdField_a_of_type_JavaLangString = "";
  private boolean jdField_a_of_type_Boolean = false;
  private boolean b = false;
  
  public jsDownload(OpenJsHelper paramOpenJsHelper)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
    paramOpenJsHelper = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    if (paramOpenJsHelper != null) {
      this.jdField_a_of_type_AndroidContentContext = paramOpenJsHelper.getContext();
    }
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController == null) {
      this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController = new DownloadController(this);
    }
  }
  
  private String a()
  {
    return this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getDownloadFileInfo();
  }
  
  private String a(String paramString, JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return null;
    }
    String str = b(paramJSONObject, "url");
    if (!Configuration.getInstance(this.jdField_a_of_type_AndroidContentContext).isJsApiStartDownloadEnable())
    {
      ReporterUtil.getSingleton().reportUnStartMsg(this.jdField_a_of_type_AndroidContentContext, str, "-2");
      return "-2";
    }
    if ((a(281, false)) && (!a(289, true)))
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("url", this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getUrl());
      localHashMap.put("durl", str);
      X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_AndroidContentContext).upLoadToBeacon("MTT_TBS_JS_DOWNLOAD", localHashMap);
      if (isQbUrl(str))
      {
        str = getQbChannelByUrl(str);
        return this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.downloadQB(str, paramJSONObject, paramString);
      }
      if (!TextUtils.isEmpty(str))
      {
        int i = this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.downloadFile(paramJSONObject, paramString);
        if (i != 0) {
          ReporterUtil.getSingleton().reportUnStartMsg(this.jdField_a_of_type_AndroidContentContext, str, String.valueOf(i));
        }
      }
      return null;
    }
    ReporterUtil.getSingleton().reportUnStartMsg(this.jdField_a_of_type_AndroidContentContext, str, "-3");
    return "-3";
  }
  
  @SuppressLint({"NewApi"})
  private String a(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return null;
    }
    try
    {
      paramJSONObject = (String)paramJSONObject.get("url");
      if ((paramJSONObject != null) && ((paramJSONObject == null) || (!TextUtils.isEmpty(paramJSONObject))))
      {
        if (isQbUrl(paramJSONObject))
        {
          if (QBDownloadManager.getInstance().isQBDownloaded()) {
            return "FILE_HAS_DOWNLOADED";
          }
          return "FILE_HAS_NO_DOWNLOADED";
        }
        DownloadController localDownloadController = this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController;
        if (localDownloadController != null)
        {
          paramJSONObject = localDownloadController.getDownloadedFile(paramJSONObject);
          if ((paramJSONObject != null) && (paramJSONObject.exists())) {
            return "FILE_HAS_DOWNLOADED";
          }
          if (paramJSONObject != null) {
            return "FILE_HAS_DELETED";
          }
          return "FILE_HAS_NO_DOWNLOADED";
        }
        return "FUNCTION CALL ERROR";
      }
      return "FUNCTION PARAMTER ERROR";
    }
    catch (Exception paramJSONObject)
    {
      for (;;) {}
    }
    return "FUNCTION CALL ERROR";
  }
  
  private String a(JSONObject paramJSONObject, String paramString)
  {
    return this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.getUninstallAppInfo(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext(), paramJSONObject, this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getOriginUrl(), paramString);
  }
  
  private String a(JSONObject paramJSONObject, String paramString1, String paramString2)
  {
    if (paramJSONObject != null) {
      if (!paramJSONObject.has(paramString1)) {
        return paramString2;
      }
    }
    try
    {
      paramJSONObject = paramJSONObject.getString(paramString1);
      return paramJSONObject;
    }
    catch (JSONException paramJSONObject) {}
    return paramString2;
    return paramString2;
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
  
  private String b(String paramString, JSONObject paramJSONObject)
  {
    paramJSONObject = b(paramJSONObject, "url");
    if (isQbUrl(paramJSONObject))
    {
      QBDownloadManager.getInstance().resumeDownload();
      return "0";
    }
    return this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.resumeDownloadFile(paramJSONObject, paramString);
  }
  
  private String b(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return null;
    }
    try
    {
      paramJSONObject = (String)paramJSONObject.get("url");
      return Long.toString(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.getDownloadProgress(paramJSONObject));
    }
    catch (Exception paramJSONObject) {}
    return null;
  }
  
  private String b(JSONObject paramJSONObject, String paramString)
  {
    return a(paramJSONObject, paramString, null);
  }
  
  private String c(String paramString, JSONObject paramJSONObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getInstalledAppInfo: params=");
    localStringBuilder.append(String.valueOf(paramJSONObject));
    localStringBuilder.toString();
    return this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.getInstalledAppInfo(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext(), paramJSONObject, this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getOriginUrl(), paramString);
  }
  
  private String c(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return null;
    }
    try
    {
      paramJSONObject = (String)paramJSONObject.get("url");
      return this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.getDownloadStatus(paramJSONObject);
    }
    catch (JSONException paramJSONObject) {}
    return null;
  }
  
  private String d(String paramString, JSONObject paramJSONObject)
  {
    paramString = new StringBuilder();
    paramString.append("setMediaInfo: params=");
    paramString.append(String.valueOf(paramJSONObject));
    paramString.toString();
    return this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.setMediaInfo(paramJSONObject);
  }
  
  private String d(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return null;
    }
    try
    {
      paramJSONObject = (String)paramJSONObject.get("url");
      return Long.toString(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.getDownloadFileTotalSize(paramJSONObject));
    }
    catch (Exception paramJSONObject) {}
    return null;
  }
  
  private String e(JSONObject paramJSONObject)
  {
    paramJSONObject = b(paramJSONObject, "url");
    return this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.pauseDownload(paramJSONObject);
  }
  
  private String f(JSONObject paramJSONObject)
  {
    String str = b(paramJSONObject, "url");
    b(paramJSONObject, "forQB");
    return this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.cancelDownload(str);
  }
  
  public static String getQbChannelByUrl(String paramString)
  {
    if (isQbUrl(paramString)) {
      try
      {
        paramString = Uri.parse(paramString).getQueryParameter("channel");
        return paramString;
      }
      catch (Exception paramString)
      {
        paramString.printStackTrace();
      }
    }
    return null;
  }
  
  public static boolean isQbUrl(String paramString)
  {
    boolean bool2 = TextUtils.isEmpty(paramString);
    boolean bool1 = false;
    if (!bool2)
    {
      if ((paramString.startsWith("http://appchannel.html5.qq.com/directdown?app=qqbrowser")) || (paramString.startsWith("https://appchannel.html5.qq.com/directdown?app=qqbrowser"))) {
        bool1 = true;
      }
      return bool1;
    }
    return false;
  }
  
  protected boolean a(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("download.");
    localStringBuilder.append(paramString);
    paramString = localStringBuilder.toString();
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkJsApiDomain(paramString) == 1) {
      return true;
    }
    return this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkQQDomain();
  }
  
  public void destroy()
  {
    this.jdField_a_of_type_Boolean = true;
    DownloadController localDownloadController = this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController;
    if (localDownloadController == null) {
      return;
    }
    if (localDownloadController.hasAPKDownloadTask()) {
      return;
    }
    destroyimpl();
  }
  
  public boolean destroyimpl()
  {
    if (this.jdField_a_of_type_Boolean)
    {
      DownloadController localDownloadController = this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController;
      if (localDownloadController != null)
      {
        localDownloadController.destroy();
        this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController = null;
      }
      if (this.jdField_a_of_type_AndroidContentContext != null) {
        SystemInstallReceiver.getInstance().JsObjDestroyed(this.jdField_a_of_type_AndroidContentContext, this);
      }
      return true;
    }
    return false;
  }
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.setOriginUrl(paramString3);
    boolean bool = "subscribeChanged".equals(paramString1);
    paramString3 = null;
    if ((!bool) && (!a(paramString1))) {
      paramString1 = new JSONObject();
    }
    try
    {
      paramString1.put("ret", null);
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString2, paramString1);
      return null;
    }
    catch (Exception paramString1) {}
    if ("startDownload".equals(paramString1)) {
      return a(paramString2, paramJSONObject);
    }
    if ("getDownloadProgress".equals(paramString1)) {
      return b(paramJSONObject);
    }
    if ("getDownloadStatus".equals(paramString1)) {
      return c(paramJSONObject);
    }
    if ("isFileDownloaded".equals(paramString1)) {
      return a(paramJSONObject);
    }
    if ("getDownloadFileTotalSize".equals(paramString1)) {
      return d(paramJSONObject);
    }
    if ("installApp".equals(paramString1)) {
      return installApp(paramString2, paramJSONObject);
    }
    if ("subscribeChanged".equals(paramString1)) {
      return subscribeChanged(paramJSONObject);
    }
    if ("getDownloadFileInfo".equals(paramString1)) {
      return a();
    }
    if ("pauseDownload".equals(paramString1)) {
      return e(paramJSONObject);
    }
    if ("resumeDownload".equals(paramString1)) {
      return b(paramString2, paramJSONObject);
    }
    if ("cancelDownload".equals(paramString1)) {
      return f(paramJSONObject);
    }
    if ("getUninstallAppInfo".equals(paramString1)) {
      return a(paramJSONObject, paramString2);
    }
    if ("installAppWithKey".equals(paramString1)) {
      return installAppWithKey(paramString2, paramJSONObject);
    }
    if ("getInstalledAppInfo".equals(paramString1)) {
      return c(paramString2, paramJSONObject);
    }
    if ("setMediaInfo".equals(paramString1)) {
      paramString3 = d(paramString2, paramJSONObject);
    }
    return paramString3;
    return null;
  }
  
  public Context getContext()
  {
    return this.jdField_a_of_type_AndroidContentContext;
  }
  
  public String getCurrentUrl()
  {
    if (this.jdField_a_of_type_Boolean) {
      return null;
    }
    return this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getUrl();
  }
  
  public String getHostBeforeInterceptDownload()
  {
    String str = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getHistoryUrl(-1);
    if (!TextUtils.isEmpty(str)) {
      return UrlUtils.getHost(str);
    }
    return null;
  }
  
  public OpenJsHelper getOpenJsHelper()
  {
    return this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
  }
  
  public String installApp(String paramString, JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return "-1";
    }
    if (!Configuration.getInstance(this.jdField_a_of_type_AndroidContentContext).isJsApiInstallAppEnable()) {
      return "-2";
    }
    if ((a(282, false)) && (!a(313, true))) {}
    try
    {
      String str = (String)paramJSONObject.get("url");
      if ((str != null) && (!TextUtils.isEmpty(str)))
      {
        Object localObject = new HashMap();
        ((HashMap)localObject).put("url", this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getUrl());
        ((HashMap)localObject).put("durl", str);
        X5CoreBeaconUploader.getInstance(this.jdField_a_of_type_AndroidContentContext).upLoadToBeacon("MTT_TBS_JS_INSTALLAPP", (Map)localObject);
        localObject = b(paramJSONObject, "businessId");
        if (isQbUrl(str))
        {
          str = getQbChannelByUrl(str);
          this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.installQB(str, paramJSONObject, paramString);
          return "0";
        }
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("[jsapi] installApp:");
        paramJSONObject.append(paramString);
        paramJSONObject.append(", url: ");
        paramJSONObject.append(str);
        paramJSONObject.toString();
        if (TextUtils.isEmpty(str)) {
          return "-1";
        }
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("");
        paramJSONObject.append(SystemInstallReceiver.getInstance().installApkFile(this.jdField_a_of_type_AndroidContentContext, str, this, paramString, -1, (String)localObject));
        paramString = paramJSONObject.toString();
        paramJSONObject = SystemInstallReceiver.getInstance().getCurrentPackageName();
        if (!this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.isRestrictedAds(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getUrl())) {
          b.a().a(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getInternalWebView(), paramJSONObject);
        }
        return paramString;
      }
      return "-1";
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return "-1";
    return "-3";
  }
  
  public String installAppWithKey(String paramString, JSONObject paramJSONObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController.installAppWithKey(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext(), paramString, paramJSONObject));
    return localStringBuilder.toString();
  }
  
  public boolean isDownloadIntercept()
  {
    OpenJsHelper localOpenJsHelper = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    if ((localOpenJsHelper != null) && (localOpenJsHelper.getUrl() != null)) {
      return this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getUrl().startsWith("http://res.imtt.qq.com///m_download_qb/qbload.html");
    }
    return false;
  }
  
  public boolean isWebViewDestroyed()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public void notifyJSDownloadStatus(String[] paramArrayOfString)
  {
    if (!this.b) {
      return;
    }
    StringBuilder localStringBuilder;
    if (paramArrayOfString != null)
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("[jsapi] notifyJSDownloadStatus: ");
      localStringBuilder.append(paramArrayOfString[0]);
      localStringBuilder.append(", ");
      localStringBuilder.append(paramArrayOfString[1]);
      localStringBuilder.append(", ");
      localStringBuilder.append(paramArrayOfString[2]);
      localStringBuilder.append(", ");
      localStringBuilder.append(paramArrayOfString[3]);
      localStringBuilder.toString();
    }
    if ((paramArrayOfString != null) && (!TextUtils.isEmpty(paramArrayOfString[1])))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("{\"url\":\"");
      localStringBuilder.append(paramArrayOfString[1]);
      localStringBuilder.append("\",\"status\":\"");
      localStringBuilder.append(paramArrayOfString[2]);
      localStringBuilder.append("\",\"downedsize\":\"");
      localStringBuilder.append(paramArrayOfString[3]);
      localStringBuilder.append("\",\"totalsize\":\"");
      localStringBuilder.append(paramArrayOfString[4]);
      localStringBuilder.append("\"}");
      paramArrayOfString = localStringBuilder.toString();
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.fireEvent("ondownloadstatuschange", paramArrayOfString);
    }
  }
  
  public void onApkInstallSuccess(int paramInt)
  {
    if (paramInt >= 0)
    {
      DownloadController localDownloadController = this.jdField_a_of_type_ComTencentSmttJsApiImplUtilsDownloadController;
      if (localDownloadController != null) {
        localDownloadController.onApkInstallSuccess(paramInt);
      }
    }
  }
  
  public void sendSuccJsCallback(String paramString, JSONObject paramJSONObject)
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendJsCallback(paramString, paramJSONObject);
  }
  
  public void sendSuccJsCallback(String paramString, JSONObject paramJSONObject, boolean paramBoolean)
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString, paramJSONObject, paramBoolean);
  }
  
  public void sendSuccJsCallbackInFrame(String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendJsCallbackInFrame(paramString1, paramString2, paramJSONObject);
  }
  
  public void setJsApiImpl(Object paramObject) {}
  
  public String subscribeChanged(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null)
    {
      boolean bool;
      if (paramJSONObject.optInt("numHandlers") > 0) {
        bool = true;
      } else {
        bool = false;
      }
      this.b = bool;
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsDownload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */