package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.mtt.external.reader.base.ReaderViewCreator;
import com.tencent.mtt.external.reader.base.ReaderWebView;
import com.tencent.mtt.external.reader.export.IReaderWebViewProxy;

public class MttWebView
  extends ReaderWebView
{
  private boolean a = false;
  public IReaderWebViewProxy mWebViewProxy = null;
  
  public MttWebView(Context paramContext)
  {
    super(paramContext);
  }
  
  private void b()
  {
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    if ((localIReaderWebViewProxy != null) && (localIReaderWebViewProxy.getView() != null))
    {
      this.mWebViewProxy.setFitScreen(this.a);
      if (this.mWebViewProxy.getView().getParent() != null) {
        this.mParentLayout.removeView(this.mWebViewProxy.getView());
      }
      this.mWebViewProxy.destroy();
      this.mWebViewProxy = null;
    }
  }
  
  void a()
  {
    this.mWebViewProxy = MttViewCreator.getInstance().getWebViewProxy();
    this.mWebViewProxy.creatWebView();
    if (this.mWebViewProxy.getView() == null)
    {
      Log.e("MttWebView", "Create webview failed!");
      return;
    }
    this.a = this.mWebViewProxy.getFitScreen();
    this.mWebViewProxy.setFitScreen(false);
    this.mParentLayout.addView(this.mWebViewProxy.getView(), new FrameLayout.LayoutParams(-1, -1));
  }
  
  public void activityPause()
  {
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    if (localIReaderWebViewProxy != null) {
      localIReaderWebViewProxy.activityPause();
    }
  }
  
  public void addJSI(Object paramObject, String paramString)
  {
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    if (localIReaderWebViewProxy != null) {
      localIReaderWebViewProxy.addJavascriptInterface(paramObject, paramString);
    }
  }
  
  public void clearMatches()
  {
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    if (localIReaderWebViewProxy != null) {
      localIReaderWebViewProxy.clearMatches();
    }
  }
  
  public int create()
  {
    a();
    return 0;
  }
  
  public void destroy()
  {
    setEvent(null);
    b();
  }
  
  public void findAllAsync(String paramString)
  {
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    if (localIReaderWebViewProxy != null) {
      localIReaderWebViewProxy.findAllAsync(paramString);
    }
  }
  
  public void findNext(boolean paramBoolean)
  {
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    if (localIReaderWebViewProxy != null) {
      localIReaderWebViewProxy.findNext(paramBoolean);
    }
  }
  
  public void loadUrl(String paramString)
  {
    if (this.mWebViewProxy == null) {
      return;
    }
    if (paramString.startsWith("javascript:"))
    {
      this.mWebViewProxy.loadUrl(paramString);
      return;
    }
    IReaderWebViewProxy localIReaderWebViewProxy = this.mWebViewProxy;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append("?from_file_reader=1");
    localIReaderWebViewProxy.loadUrl(localStringBuilder.toString());
  }
  
  public void setBackgroundColor(int paramInt) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttWebView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */