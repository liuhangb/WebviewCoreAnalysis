package com.tencent.smtt.jsApi.impl;

import android.webkit.ValueCallback;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public class jsUg
  implements IOpenJsApis
{
  private OpenJsHelper a;
  
  public jsUg(OpenJsHelper paramOpenJsHelper)
  {
    this.a = paramOpenJsHelper;
  }
  
  private String a(String paramString1, ValueCallback<JSONObject> paramValueCallback, JSONObject paramJSONObject, String paramString2)
  {
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("context", this.a.getActivityCx());
      localHashMap.put("webView", this.a.getWebView());
      paramString1 = this.a.ugJsApiExec(paramString1, paramValueCallback, paramJSONObject, paramString2, localHashMap);
      return paramString1;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.a.setOriginUrl(paramString3);
    try
    {
      paramString1 = a(paramString1, new UgCb(this.a, paramString2), paramJSONObject, paramString3);
      return paramString1;
    }
    catch (Throwable paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
  
  private static class UgCb
    implements ValueCallback<JSONObject>
  {
    OpenJsHelper jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    String jdField_a_of_type_JavaLangString;
    
    UgCb(OpenJsHelper paramOpenJsHelper, String paramString)
    {
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
      this.jdField_a_of_type_JavaLangString = paramString;
    }
    
    public void onReceiveValue(JSONObject paramJSONObject)
    {
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendJsCallback(this.jdField_a_of_type_JavaLangString, paramJSONObject);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsUg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */