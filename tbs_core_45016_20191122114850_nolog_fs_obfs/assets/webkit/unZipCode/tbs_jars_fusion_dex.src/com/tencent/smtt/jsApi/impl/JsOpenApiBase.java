package com.tencent.smtt.jsApi.impl;

import org.json.JSONException;
import org.json.JSONObject;

public class JsOpenApiBase
{
  protected String a(JSONObject paramJSONObject, String paramString1, String paramString2)
  {
    if (paramJSONObject != null) {
      if (!paramJSONObject.has(paramString1)) {
        return paramString2;
      }
    }
    try
    {
      paramJSONObject = paramJSONObject.getString(paramString1);
      return paramJSONObject;
    }
    catch (JSONException paramJSONObject) {}
    return paramString2;
    return paramString2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\JsOpenApiBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */