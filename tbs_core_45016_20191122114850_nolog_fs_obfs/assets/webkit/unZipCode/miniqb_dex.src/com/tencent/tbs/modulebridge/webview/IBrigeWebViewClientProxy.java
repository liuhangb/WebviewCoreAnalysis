package com.tencent.tbs.modulebridge.webview;

import android.graphics.Bitmap;

public abstract interface IBrigeWebViewClientProxy
{
  public abstract void onPageFinished(IBrigeWebViewProxy paramIBrigeWebViewProxy, int paramInt1, int paramInt2, String paramString);
  
  public abstract void onPageStarted(IBrigeWebViewProxy paramIBrigeWebViewProxy, int paramInt1, int paramInt2, String paramString, Bitmap paramBitmap);
  
  public abstract void onReceivedError(IBrigeWebViewProxy paramIBrigeWebViewProxy, int paramInt, String paramString1, String paramString2);
  
  public abstract boolean shouldOverrideUrlLoading(IBrigeWebViewProxy paramIBrigeWebViewProxy, String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\modulebridge\webview\IBrigeWebViewClientProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */