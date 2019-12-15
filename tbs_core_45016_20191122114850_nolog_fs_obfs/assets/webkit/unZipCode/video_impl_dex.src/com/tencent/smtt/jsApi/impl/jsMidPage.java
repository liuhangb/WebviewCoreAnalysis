package com.tencent.smtt.jsApi.impl;

import com.tencent.smtt.jsApi.export.IOpenJsApis;
import org.json.JSONObject;

public class jsMidPage
  implements IOpenJsApis
{
  private OpenJsHelper a;
  
  public jsMidPage(OpenJsHelper paramOpenJsHelper)
  {
    this.a = paramOpenJsHelper;
  }
  
  private String a(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.a;
      a(paramString, "this api without user authorization");
      return null;
    }
    this.a.openMiniQB(paramJSONObject);
    return null;
  }
  
  private String b(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.a;
      a(paramString, "this api without user authorization");
      return null;
    }
    this.a.closeMiniQB(paramJSONObject);
    return null;
  }
  
  private String c(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.a;
      a(paramString, "this api without user authorization");
      return null;
    }
    this.a.openMidPage(paramJSONObject);
    return null;
  }
  
  private String d(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.a;
      a(paramString, "this api without user authorization");
      return null;
    }
    this.a.closeMidPage();
    return null;
  }
  
  private String e(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.a;
      a(paramString, "this api without user authorization");
      return "";
    }
    return this.a.doGetMiniqbVersion();
  }
  
  void a(String paramString1, String paramString2)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("ret", paramString2);
      this.a.sendSuccJsCallback(paramString1, localJSONObject);
      return;
    }
    catch (Exception paramString1) {}
  }
  
  protected boolean a()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("midpage");
    ((StringBuilder)localObject).append(".");
    ((StringBuilder)localObject).append(Thread.currentThread().getStackTrace()[3].getMethodName());
    localObject = ((StringBuilder)localObject).toString();
    if (this.a.checkJsApiDomain((String)localObject) == 1) {
      return true;
    }
    return this.a.checkQQDomain();
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.a.setOriginUrl(paramString3);
    if ("openMidPage".equals(paramString1)) {
      return c(paramString2, paramJSONObject);
    }
    if ("closeMidPage".equals(paramString1)) {
      return d(paramString2, paramJSONObject);
    }
    if ("openMiniQB".equals(paramString1)) {
      return a(paramString2, paramJSONObject);
    }
    if ("closeMiniQB".equals(paramString1)) {
      return b(paramString2, paramJSONObject);
    }
    if ("getMiniQBVersion".equals(paramString1)) {
      return e(paramString2, paramJSONObject);
    }
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsMidPage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */