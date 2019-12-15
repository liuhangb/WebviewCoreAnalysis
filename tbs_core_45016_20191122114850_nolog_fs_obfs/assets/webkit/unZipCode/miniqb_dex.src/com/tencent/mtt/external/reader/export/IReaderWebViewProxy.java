package com.tencent.mtt.external.reader.export;

import android.content.Context;
import android.view.View;

public abstract interface IReaderWebViewProxy
{
  public abstract void activityPause();
  
  public abstract void addJavascriptInterface(Object paramObject, String paramString);
  
  public abstract boolean canGoBack();
  
  public abstract void clearMatches();
  
  public abstract void creatWebView();
  
  public abstract void destroy();
  
  public abstract void doSearch(Context paramContext, String paramString);
  
  public abstract void findAllAsync(String paramString);
  
  public abstract void findNext(boolean paramBoolean);
  
  public abstract void fling(int paramInt1, int paramInt2);
  
  public abstract boolean getFitScreen();
  
  public abstract View getView();
  
  public abstract void goBack();
  
  public abstract void loadUrl(String paramString);
  
  public abstract void setFitScreen(boolean paramBoolean);
  
  public abstract void setReaderWebViewClient(IReaderWebViewClient paramIReaderWebViewClient);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\export\IReaderWebViewProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */