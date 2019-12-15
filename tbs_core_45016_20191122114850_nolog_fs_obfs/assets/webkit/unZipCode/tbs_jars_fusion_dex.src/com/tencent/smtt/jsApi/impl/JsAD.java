package com.tencent.smtt.jsApi.impl;

import com.tencent.smtt.download.ad.b;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.smtt.jsApi.impl.utils.ReporterUtil;
import com.tencent.tbs.common.utils.URLUtil;
import org.json.JSONException;
import org.json.JSONObject;

public class JsAD
  extends JsOpenApiBase
  implements IOpenJsApis
{
  private OpenJsHelper a;
  
  public JsAD(OpenJsHelper paramOpenJsHelper)
  {
    this.a = paramOpenJsHelper;
  }
  
  private String a(String paramString, JSONObject paramJSONObject)
  {
    paramString = a(paramJSONObject, "ifShowAd", "false");
    String str1 = a(paramJSONObject, "adType", "");
    String str2 = a(paramJSONObject, "adPos", "");
    String str3 = a(paramJSONObject, "appId", "");
    String str4 = a(paramJSONObject, "adId", "");
    String str5 = a(paramJSONObject, "adShape", "");
    String str6 = a(paramJSONObject, "adUrl", "");
    paramJSONObject = a(paramJSONObject, "adOpenType", "");
    return this.a.setAdInfo(paramString, str1, str2, str3, str4, str5, str6, paramJSONObject);
  }
  
  private String b(String paramString, JSONObject paramJSONObject)
  {
    return this.a.getAdInfo();
  }
  
  private String c(String paramString, JSONObject paramJSONObject)
  {
    paramString = a(paramJSONObject, "reportUrl", "");
    String str = a(paramJSONObject, "appUrl", "");
    paramJSONObject = a(paramJSONObject, "type", "");
    return ReporterUtil.getSingleton().sendMsgByUrl(str, paramJSONObject, paramString, this.a);
  }
  
  private String d(String paramString, JSONObject paramJSONObject)
  {
    if (URLUtil.isUrlMatchDomainList(this.a.getUrl(), 286, false)) {
      return "true";
    }
    return "false";
  }
  
  private String e(String paramString, JSONObject paramJSONObject)
  {
    localJSONObject = null;
    for (;;)
    {
      try
      {
        paramString = paramJSONObject.getString("key");
      }
      catch (JSONException paramString)
      {
        continue;
      }
      try
      {
        paramJSONObject = paramJSONObject.getString("eventType");
      }
      catch (JSONException paramJSONObject)
      {
        paramJSONObject = localJSONObject;
      }
    }
    paramString = null;
    paramJSONObject = localJSONObject;
    localJSONObject = b.a().a(paramString);
    if (localJSONObject == null) {
      return "-1";
    }
    if ("show".equals(paramJSONObject))
    {
      ReporterUtil.getSingleton().statReinstallEvent(this.a.getContext(), ReporterUtil.REINSTALL_SHOW, this.a.getUrl(), localJSONObject);
      b.a().a(localJSONObject);
    }
    else if ("close".equals(paramJSONObject))
    {
      ReporterUtil.getSingleton().statReinstallEvent(this.a.getContext(), ReporterUtil.REINSTALL_CLOSE, this.a.getUrl(), localJSONObject);
      b.a().b(paramString);
    }
    return "0";
  }
  
  private String f(String paramString, JSONObject paramJSONObject)
  {
    paramJSONObject = a(paramJSONObject, "url", null);
    paramString = paramJSONObject;
    if (paramJSONObject == null) {
      paramString = this.a.getUrl();
    }
    paramJSONObject = new StringBuilder();
    paramJSONObject.append("");
    paramJSONObject.append(this.a.preloadScreenAd(paramString));
    return paramJSONObject.toString();
  }
  
  private String g(String paramString, JSONObject paramJSONObject)
  {
    paramJSONObject = new StringBuilder();
    paramJSONObject.append("");
    paramJSONObject.append(this.a.showScreenAd(paramString));
    return paramJSONObject.toString();
  }
  
  private String h(String paramString, JSONObject paramJSONObject)
  {
    paramJSONObject = new StringBuilder();
    paramJSONObject.append("");
    paramJSONObject.append(this.a.hideScreenAd(paramString));
    return paramJSONObject.toString();
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.a.setOriginUrl(paramString3);
    if ("setAdInfo".equals(paramString1)) {
      return a(paramString2, paramJSONObject);
    }
    if ("getAdInfo".equals(paramString1)) {
      return b(paramString2, paramJSONObject);
    }
    if ("reportMsgByURL".equals(paramString1)) {
      return c(paramString2, paramJSONObject);
    }
    if ("canUseAdFeature".equals(paramString1)) {
      return d(paramString2, paramJSONObject);
    }
    if ("reinstallTipsEvent".equals(paramString1)) {
      return e(paramString2, paramJSONObject);
    }
    if ("preloadScreenAd".equals(paramString1)) {
      return f(paramString2, paramJSONObject);
    }
    if ("showScreenAd".equals(paramString1)) {
      return g(paramString2, paramJSONObject);
    }
    if ("hideScreenAd".equals(paramString1)) {
      return h(paramString2, paramJSONObject);
    }
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\JsAD.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */