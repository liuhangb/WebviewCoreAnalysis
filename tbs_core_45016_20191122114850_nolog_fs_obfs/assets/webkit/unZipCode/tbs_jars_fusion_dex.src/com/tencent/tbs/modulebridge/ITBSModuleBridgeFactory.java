package com.tencent.tbs.modulebridge;

import android.content.Context;
import com.tencent.tbs.modulebridge.webview.IBrigeWebViewProxy;

public abstract interface ITBSModuleBridgeFactory
{
  public abstract IBrigeWebViewProxy createWebView(Context paramContext);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\modulebridge\ITBSModuleBridgeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */