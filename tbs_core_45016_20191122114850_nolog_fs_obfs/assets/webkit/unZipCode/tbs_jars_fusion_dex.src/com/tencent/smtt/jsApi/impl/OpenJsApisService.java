package com.tencent.smtt.jsApi.impl;

import android.content.Context;
import android.webkit.WebView;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.smtt.jsApi.export.IWebviewNotifyListener;
import java.util.ArrayList;

public class OpenJsApisService
{
  private OpenJsHelper a = null;
  
  public static OpenJsApisService createInstance()
  {
    return new OpenJsApisService();
  }
  
  public void destory()
  {
    OpenJsHelper localOpenJsHelper = this.a;
    if (localOpenJsHelper != null)
    {
      localOpenJsHelper.onWebViewDestroy();
      this.a = null;
    }
  }
  
  public IOpenJsApis initService(String paramString, Object paramObject1, Object paramObject2, Context paramContext1, Context paramContext2, ArrayList<IWebviewNotifyListener> paramArrayList)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("initService name:");
    localStringBuilder.append(paramString);
    localStringBuilder.toString();
    if ((paramObject1 != null) && ((paramObject1 instanceof WebView))) {
      this.a = new LiteJsHelper(paramObject1, paramContext1, paramContext2);
    } else {
      this.a = new X5JsHelper(paramObject1, paramObject2, paramContext1, paramContext2);
    }
    if ("tbs".equals(paramString)) {
      return new jsQb(this.a);
    }
    if ("package".equals(paramString)) {
      return new jsPackages(this.a);
    }
    if ("app".equals(paramString)) {
      return new jsApp(this.a);
    }
    if ("device".equals(paramString)) {
      return new jsDevice(this.a);
    }
    if ("deeplink".equals(paramString)) {
      return new jsDeeplink(this.a);
    }
    if ("download".equals(paramString)) {
      return new jsDownload(this.a);
    }
    if ("screen".equals(paramString)) {
      return new jsScreen(this.a);
    }
    if ("debug".equals(paramString)) {
      return new jsDebug(this.a, paramArrayList);
    }
    if ("network".equals(paramString)) {
      return new jsNetwork(this.a);
    }
    if ("midpage".equals(paramString)) {
      return new jsMidPage(this.a);
    }
    if ("gameplayer".equals(paramString)) {
      return new JsGame(this.a);
    }
    if ("gameframework".equals(paramString)) {
      return new jsGameFramework(this.a, paramArrayList);
    }
    if ("video".equals(paramString)) {
      return new jsVideo(this.a, paramObject2);
    }
    if ("wifi".equals(paramString)) {
      return new jsWiFi(this.a);
    }
    if ("ad".equals(paramString)) {
      return new JsAD(this.a);
    }
    if ("ug".equals(paramString)) {
      return new jsUg(this.a);
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\OpenJsApisService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */