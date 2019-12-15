package com.tencent.tbs.core.webkit.tencent;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import com.tencent.smtt.export.external.interfaces.IX5WebBackForwardList;
import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.jsApi.export.OpenJsApiBridge;
import com.tencent.smtt.webkit.WebViewProviderExtension;
import com.tencent.tbs.core.webkit.WebChromeClient;
import com.tencent.tbs.core.webkit.WebViewClient;
import com.tencent.tbs.core.webkit.WebViewProvider;

public abstract interface TencentWebViewProvider
  extends WebViewProvider
{
  public abstract IX5WebBackForwardList copyBackForwardListTencent();
  
  public abstract void draw(Canvas paramCanvas);
  
  public abstract WebViewProviderExtension getExtension();
  
  public abstract int[] getHitTestResultImgSize();
  
  public abstract int[] getHitTestResultPosition();
  
  public abstract IX5WebViewBase.HitTestResult getHitTestResultTencent();
  
  public abstract OpenJsApiBridge getOpenJsApiBridge();
  
  public abstract IX5WebSettings getSettingsTencent();
  
  public abstract WebChromeClient getWebChromeClient();
  
  public abstract WebViewClient getWebViewClient();
  
  public abstract boolean isFullScreen();
  
  public abstract Bitmap lastHitTestBmp();
  
  public abstract void notifyADWebviewVisiableHeight(int paramInt);
  
  public abstract void notifyFirstScreenFinished(long paramLong1, long paramLong2);
  
  public abstract IX5WebBackForwardList restoreStateTencent(Bundle paramBundle);
  
  public abstract IX5WebBackForwardList saveStateTencent(Bundle paramBundle);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentWebViewProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */