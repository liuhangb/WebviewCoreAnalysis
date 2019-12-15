package com.tencent.tbs.modulebridge;

import android.content.Context;
import android.util.Log;
import com.tencent.tbs.modulebridge.webview.IBrigeWebViewProxy;

public class TBSModuleBridgeFactory
  implements ITBSModuleBridgeFactory
{
  private static final String TAG = "TBSModuleBridgeFactory";
  private static TBSModuleBridgeFactory sInstance;
  private ITBSModuleBridgeFactory mFactoryImpl;
  
  public static TBSModuleBridgeFactory getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new TBSModuleBridgeFactory();
      }
      TBSModuleBridgeFactory localTBSModuleBridgeFactory = sInstance;
      return localTBSModuleBridgeFactory;
    }
    finally {}
  }
  
  public IBrigeWebViewProxy createWebView(Context paramContext)
  {
    ITBSModuleBridgeFactory localITBSModuleBridgeFactory = this.mFactoryImpl;
    if (localITBSModuleBridgeFactory != null) {
      return localITBSModuleBridgeFactory.createWebView(paramContext);
    }
    Log.d("TBSModuleBridgeFactory", "impl is not init");
    return null;
  }
  
  public void setImpl(ITBSModuleBridgeFactory paramITBSModuleBridgeFactory)
  {
    this.mFactoryImpl = paramITBSModuleBridgeFactory;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\modulebridge\TBSModuleBridgeFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */