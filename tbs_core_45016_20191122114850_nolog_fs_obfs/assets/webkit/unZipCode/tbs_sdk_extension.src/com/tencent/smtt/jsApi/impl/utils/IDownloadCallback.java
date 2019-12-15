package com.tencent.smtt.jsApi.impl.utils;

import android.content.Context;
import com.tencent.smtt.jsApi.impl.OpenJsHelper;
import org.json.JSONObject;

public abstract interface IDownloadCallback
{
  public abstract boolean destroyimpl();
  
  public abstract Context getContext();
  
  public abstract String getCurrentUrl();
  
  public abstract String getHostBeforeInterceptDownload();
  
  public abstract OpenJsHelper getOpenJsHelper();
  
  public abstract boolean isDownloadIntercept();
  
  public abstract boolean isWebViewDestroyed();
  
  public abstract void notifyJSDownloadStatus(String[] paramArrayOfString);
  
  public abstract void onApkInstallSuccess(int paramInt);
  
  public abstract void sendSuccJsCallback(String paramString, JSONObject paramJSONObject);
  
  public abstract void sendSuccJsCallback(String paramString, JSONObject paramJSONObject, boolean paramBoolean);
  
  public abstract void sendSuccJsCallbackInFrame(String paramString1, String paramString2, JSONObject paramJSONObject);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\utils\IDownloadCallback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */