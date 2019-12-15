package com.tencent.smtt.jsApi.impl;

import com.tencent.smtt.jsApi.export.IOpenJsApis;
import org.json.JSONObject;

public class jsScreen
  implements IOpenJsApis
{
  private OpenJsHelper a;
  
  public jsScreen(OpenJsHelper paramOpenJsHelper)
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
    this.a.requestPageFullScreen(paramJSONObject);
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
    this.a.cancelPageFullScreen();
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
    this.a.requestScreenBacklight();
    return null;
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
    ((StringBuilder)localObject).append("screen");
    ((StringBuilder)localObject).append(".");
    ((StringBuilder)localObject).append(Thread.currentThread().getStackTrace()[3].getMethodName());
    localObject = ((StringBuilder)localObject).toString();
    if (this.a.checkJsApiDomain((String)localObject) == 1) {
      return true;
    }
    return this.a.checkQQDomain();
  }
  
  public String cancelScreenBacklight(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.a;
      a(paramString, "this api without user authorization");
      return null;
    }
    this.a.cancelScreenBacklight();
    return null;
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.a.setOriginUrl(paramString3);
    if ("requestPageFullScreen".equals(paramString1)) {
      return a(paramString2, paramJSONObject);
    }
    if ("cancelPageFullScreen".equals(paramString1)) {
      return b(paramString2, paramJSONObject);
    }
    if ("requestScreenBacklight".equals(paramString1)) {
      return c(paramString2, paramJSONObject);
    }
    if ("cancelScreenBacklight".equals(paramString1)) {
      return cancelScreenBacklight(paramString2, paramJSONObject);
    }
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsScreen.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */