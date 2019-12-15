package com.tencent.mtt.video.browser.export.player;

import android.graphics.Bitmap;

public abstract interface IVideoWebViewClient
{
  public abstract boolean onOverScroll(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean);
  
  public abstract void onPageFinished(String paramString);
  
  public abstract void onPageStarted(String paramString, Bitmap paramBitmap);
  
  public abstract void onReceivedError(int paramInt, String paramString1, String paramString2);
  
  public abstract boolean shouldOverrideUrlLoading(IVideoWebViewProxy paramIVideoWebViewProxy, String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\video\browser\export\player\IVideoWebViewClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */