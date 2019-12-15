package com.tencent.tbs.tbsshell.partner.webaccelerator;

import android.content.Context;
import android.content.MutableContextWrapper;
import android.os.Looper;
import android.os.MessageQueue;
import android.os.MessageQueue.IdleHandler;
import android.util.Log;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase;
import com.tencent.tbs.tbsshell.WebCoreProxy;
import java.util.ArrayList;
import java.util.List;

public class WebViewPool
{
  private static final int DEFAULT_MAX_POOL_SIZE = 0;
  private static final String TAG = "WebViewPool";
  private static List<IX5WebViewBase> sCacheWebViews;
  private static int sMaxPoolSize;
  
  public static IX5WebViewBase acquireWebView(Context paramContext)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("acquireWebView sWebViewCaches=");
    ((StringBuilder)localObject).append(sCacheWebViews);
    ((StringBuilder)localObject).append(" context=");
    ((StringBuilder)localObject).append(paramContext);
    ((StringBuilder)localObject).toString();
    try
    {
      if ((sCacheWebViews != null) && (!sCacheWebViews.isEmpty()))
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("acquireWebView cacheSize=");
        ((StringBuilder)localObject).append(sCacheWebViews.size());
        ((StringBuilder)localObject).toString();
        IX5WebViewBase localIX5WebViewBase = (IX5WebViewBase)sCacheWebViews.remove(0);
        localObject = paramContext;
        if ((paramContext instanceof MutableContextWrapper)) {
          localObject = ((MutableContextWrapper)paramContext).getBaseContext();
        }
        WebCoreProxy.switchContext(localIX5WebViewBase, (Context)localObject);
        return localIX5WebViewBase;
      }
      return WebCoreProxy.createSDKWebViewInstance(paramContext);
    }
    finally {}
  }
  
  private static boolean checkThread()
  {
    return Looper.myLooper() == Looper.getMainLooper();
  }
  
  public static void createCacheWebView(Context paramContext)
  {
    if (paramContext == null) {
      return;
    }
    if (!checkThread()) {
      return;
    }
    if (sMaxPoolSize == 0) {
      return;
    }
    try
    {
      if ((sCacheWebViews != null) && (sCacheWebViews.size() >= sMaxPoolSize)) {
        return;
      }
      Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler()
      {
        public boolean queueIdle()
        {
          try
          {
            if (WebViewPool.sCacheWebViews == null) {
              WebViewPool.access$002(new ArrayList());
            }
            Object localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("createWebViewCache IdleHandler idle cacheSize=");
            ((StringBuilder)localObject1).append(WebViewPool.sCacheWebViews.size());
            ((StringBuilder)localObject1).toString();
            if (WebViewPool.sCacheWebViews.size() >= WebViewPool.sMaxPoolSize) {
              return false;
            }
            localObject1 = WebCoreProxy.createSDKWebViewInstance(this.val$context.getApplicationContext());
            if (localObject1 == null)
            {
              Log.w("WebViewPool", "createWebViewCache IdleHandler idle createWebView Failed");
              return false;
            }
            WebViewPool.sCacheWebViews.add(localObject1);
            return false;
          }
          finally {}
        }
      });
      return;
    }
    finally {}
  }
  
  public static void setWebViewPoolSize(int paramInt)
  {
    int i;
    if (paramInt >= 0) {
      i = paramInt;
    } else {
      i = 0;
    }
    sMaxPoolSize = i;
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("setWebViewPoolSize webViewPoolSize=");
    ((StringBuilder)localObject1).append(paramInt);
    ((StringBuilder)localObject1).append(" sMaxPoolSize=");
    ((StringBuilder)localObject1).append(sMaxPoolSize);
    ((StringBuilder)localObject1).toString();
    for (;;)
    {
      try
      {
        if ((sCacheWebViews != null) && (sMaxPoolSize < sCacheWebViews.size()))
        {
          i = sCacheWebViews.size();
          int j = sMaxPoolSize;
          paramInt = 0;
          if (paramInt < i - j)
          {
            localObject1 = (IX5WebViewBase)sCacheWebViews.remove(0);
            if (localObject1 != null) {
              ((IX5WebViewBase)localObject1).destroy();
            }
          }
          else
          {
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("setWebViewPoolSize sCacheWebViews.size=");
            ((StringBuilder)localObject1).append(sCacheWebViews.size());
            ((StringBuilder)localObject1).toString();
          }
        }
        else
        {
          return;
        }
      }
      finally {}
      paramInt += 1;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\webaccelerator\WebViewPool.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */