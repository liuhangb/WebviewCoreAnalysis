package com.tencent.tbs.tbsshell;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.ValueCallback;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import java.util.Map;

public abstract interface IWebCoreReflectionPartner
{
  public abstract boolean canOpenFile(Context paramContext, String paramString);
  
  public abstract void closeFileReader();
  
  public abstract void closeMiniQB();
  
  public abstract void createCacheWebView(Context paramContext);
  
  public abstract boolean createMiniQBShortCut(Context paramContext, String paramString1, String paramString2, Bitmap paramBitmap);
  
  public abstract IX5WebViewBase createSDKWebview(Context paramContext);
  
  public abstract String getMiniQBVersion();
  
  public abstract boolean installLocalQbApk(Context paramContext, String paramString1, String paramString2, Bundle paramBundle);
  
  public abstract boolean isMiniQBShortCutExist(Context paramContext, String paramString);
  
  public abstract void onCannotUseX5();
  
  public abstract void prefetchResource(Context paramContext, String paramString, Map<String, String> paramMap, boolean paramBoolean);
  
  public abstract void setWebViewPoolSize(Context paramContext, int paramInt);
  
  public abstract int startMiniQB(Context paramContext, String paramString, Map<String, String> paramMap, ValueCallback<String> paramValueCallback);
  
  public abstract int startQBWeb(Context paramContext, String paramString1, String paramString2, Map<String, String> paramMap);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\IWebCoreReflectionPartner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */