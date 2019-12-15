package org.chromium.tencent;

import android.net.http.SslError;
import org.chromium.android_webview.AwScrollOffsetManager;
import org.chromium.base.Callback;
import org.json.JSONObject;

public abstract interface ITencentAwContentsClient
{
  public abstract void onContentsSizeChanged(int paramInt1, int paramInt2);
  
  public abstract void onReceivedSslError(Callback<Boolean> paramCallback, SslError paramSslError, String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void onReportFramePerformanceInfo(JSONObject paramJSONObject);
  
  public abstract void onReportJSPerformanceInfo(JSONObject paramJSONObject);
  
  public abstract void onUpdateHTMLElementAssoicateNativePanel(int paramInt1, String paramString, int paramInt2, int paramInt3, int paramInt4, int paramInt5);
  
  public abstract void recordUrlAndScale(String paramString, double paramDouble);
  
  public abstract void updateScrollState(AwScrollOffsetManager paramAwScrollOffsetManager, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\ITencentAwContentsClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */