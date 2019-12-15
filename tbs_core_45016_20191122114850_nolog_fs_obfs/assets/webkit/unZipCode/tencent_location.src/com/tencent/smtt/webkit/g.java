package com.tencent.smtt.webkit;

import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.smtt.export.external.interfaces.IX5WebViewClient;
import java.lang.ref.WeakReference;

public class g
  implements IX5WebViewClient
{
  private WeakReference<IX5WebViewClient> a = null;
  private WeakReference<IX5WebViewBase> b = null;
  
  public g(IX5WebViewBase paramIX5WebViewBase, IX5WebViewClient paramIX5WebViewClient)
  {
    if (paramIX5WebViewClient != null) {
      this.a = new WeakReference(paramIX5WebViewClient);
    }
    if (paramIX5WebViewBase != null) {
      this.b = new WeakReference(paramIX5WebViewBase);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\g.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */