package com.tencent.tbs.tbsshell.partner.modulebridge;

import android.content.Context;
import com.tencent.tbs.modulebridge.ITBSModuleBridgeFactory;
import com.tencent.tbs.modulebridge.webview.IBrigeWebViewProxy;

public class TBSModuleBrigeFactoryImpl
  implements ITBSModuleBridgeFactory
{
  public IBrigeWebViewProxy createWebView(Context paramContext)
  {
    return new TBSModuleBridgeWebViewImpl(paramContext);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\modulebridge\TBSModuleBrigeFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */