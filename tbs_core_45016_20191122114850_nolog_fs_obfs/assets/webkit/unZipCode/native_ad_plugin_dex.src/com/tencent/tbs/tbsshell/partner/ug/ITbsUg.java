package com.tencent.tbs.tbsshell.partner.ug;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.ValueCallback;
import java.util.Map;
import org.json.JSONObject;

public abstract interface ITbsUg
{
  public static final String UG_ARGS_SID_AQB_HB_FREQUENCY = "UG_ARGS_SID_AQB_HB_FREQUENCY";
  public static final String UG_ARGS_SID_CONTENT_DISPOSITION = "UG_ARGS_SID_CONTENT_DISPOSITION";
  public static final String UG_ARGS_SID_CONTENT_LENGTH = "UG_ARGS_SID_CONTENT_LENGTH";
  public static final String UG_ARGS_SID_COOKIE = "UG_ARGS_SID_COOKIE";
  public static final String UG_ARGS_SID_CTX = "UG_ARGS_SID_CTX";
  public static final String UG_ARGS_SID_DOWNLOAD_LISTENER = "UG_ARGS_SID_DOWNLOAD_LISTENER";
  public static final String UG_ARGS_SID_GUID_BUNDLE = "UG_ARGS_SID_GUID_BUNDLE";
  public static final String UG_ARGS_SID_METHOD = "UG_ARGS_SID_METHOD";
  public static final String UG_ARGS_SID_MIMETYPE = "UG_ARGS_SID_MIMETYPE";
  public static final String UG_ARGS_SID_QPROXY_RESP = "UG_ARGS_SID_QPROXY_RESP";
  public static final String UG_ARGS_SID_QUA = "UG_ARGS_SID_QUA";
  public static final String UG_ARGS_SID_REFERER = "UG_ARGS_SID_REFERER";
  public static final String UG_ARGS_SID_REQUEST_BODY = "UG_ARGS_SID_REQUEST_BODY";
  public static final String UG_ARGS_SID_URL = "UG_ARGS_SID_URL";
  public static final String UG_ARGS_SID_URL_BEFORE_REDIRECT = "UG_ARGS_SID_URL_BEFORE_REDIRECT";
  public static final String UG_ARGS_SID_USER_AGENT = "UG_ARGS_SID_USER_AGENT";
  public static final String UG_ARGS_SID_WEBVIEW = "UG_ARGS_SID_WEBVIEW";
  public static final String UG_ARGS_SID_WINDOWS_FOCUS = "UG_ARGS_SID_WINDOWS_FOCUS";
  
  public abstract void ActiveQBHeartBeat(Map<String, Object> paramMap);
  
  public abstract String getCrashExtraInfo();
  
  public abstract String getDownloadFileInfo(Object paramObject);
  
  public abstract String getInterceptDownloadMessage();
  
  public abstract boolean handleBrowserListDialog(Map<String, Object> paramMap);
  
  public abstract void headsupActiveQB(Map<String, Object> paramMap);
  
  public abstract void init(ITbsService paramITbsService);
  
  public abstract void interceptVideoPlay(Object paramObject, Context paramContext, Handler paramHandler);
  
  public abstract Object invokeMiscMethod(String paramString, Map<String, Object> paramMap);
  
  public abstract void monitorAppRemoved(Map<String, Object> paramMap);
  
  public abstract void onWindowFocusChanged(Map<String, Object> paramMap);
  
  public abstract boolean shouldInterceptDownload(Map<String, Object> paramMap);
  
  public abstract int showUgDialog(Context paramContext, String paramString, Bundle paramBundle, ValueCallback<Bundle> paramValueCallback);
  
  public abstract int showUgH5(Context paramContext, ValueCallback<String> paramValueCallback, String paramString, Bundle paramBundle, ValueCallback<Bundle> paramValueCallback1);
  
  public abstract int showUgView(Context paramContext, ValueCallback<View> paramValueCallback, String paramString, Bundle paramBundle, ValueCallback<Bundle> paramValueCallback1);
  
  @Deprecated
  public abstract String ugJsApiExec(String paramString1, ValueCallback<JSONObject> paramValueCallback, JSONObject paramJSONObject, String paramString2);
  
  public abstract String ugJsApiExec(String paramString1, ValueCallback<JSONObject> paramValueCallback, JSONObject paramJSONObject, String paramString2, Map<String, Object> paramMap);
  
  public abstract void uploadUgLog();
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\ug\ITbsUg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */