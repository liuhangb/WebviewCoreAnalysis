package com.tencent.tbs.core;

import android.util.Pair;
import com.tencent.smtt.export.external.interfaces.IX5CoreNetwork;
import com.tencent.smtt.export.external.interfaces.UrlRequest;
import com.tencent.smtt.export.external.interfaces.UrlRequest.Callback;
import com.tencent.smtt.net.AwNetworkUtils;
import com.tencent.smtt.net.X5UrlRequestProvider;
import java.util.ArrayList;
import java.util.concurrent.Executor;

public class X5CoreNetwork
  implements IX5CoreNetwork
{
  private static X5CoreNetwork sInstance;
  
  public static X5CoreNetwork getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new X5CoreNetwork();
      }
      X5CoreNetwork localX5CoreNetwork = sInstance;
      return localX5CoreNetwork;
    }
    finally {}
  }
  
  public String getCanonicalUrl(String paramString)
  {
    return AwNetworkUtils.getCanonicalUrl(paramString);
  }
  
  public String getCoreExtraMessage()
  {
    return AwNetworkUtils.getCoreExtraMessage();
  }
  
  public UrlRequest getX5UrlRequestProvider(String paramString1, int paramInt, UrlRequest.Callback paramCallback, Executor paramExecutor, boolean paramBoolean, String paramString2, ArrayList<Pair<String, String>> paramArrayList, String paramString3)
  {
    return X5UrlRequestProvider.GetX5UrlRequestProvider(paramString1, paramInt, paramCallback, paramExecutor, paramBoolean, paramString2, paramArrayList, paramString3);
  }
  
  public void resetSpdySession() {}
  
  public void setPreConnect(String paramString, int paramInt)
  {
    AwNetworkUtils.setPreConnect(paramString, paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\X5CoreNetwork.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */