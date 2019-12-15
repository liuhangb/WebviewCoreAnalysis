package com.tencent.smtt.jsApi.export;

import org.json.JSONObject;

public abstract interface IOpenJsApis
{
  public abstract void destroy();
  
  public abstract String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3);
  
  public abstract void setJsApiImpl(Object paramObject);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\export\IOpenJsApis.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */