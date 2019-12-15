package com.tencent.mtt.external.reader.export;

import android.view.View;
import android.webkit.WebResourceResponse;

public abstract interface IReaderWebViewClient
{
  public abstract void onPageFinished(String paramString);
  
  public abstract void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4, View paramView);
  
  public abstract void onSingleTaped();
  
  public abstract void onWebviewCreated();
  
  public abstract boolean overScrollBy(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean);
  
  public abstract WebResourceResponse shouldInterceptRequest(String paramString);
  
  public abstract boolean shouldOverrideUrlLoading(String paramString);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\export\IReaderWebViewClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */