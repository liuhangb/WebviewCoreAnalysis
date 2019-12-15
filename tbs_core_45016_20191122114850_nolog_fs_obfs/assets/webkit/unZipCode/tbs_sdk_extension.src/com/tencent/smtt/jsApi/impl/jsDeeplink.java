package com.tencent.smtt.jsApi.impl;

import android.util.Log;
import com.tencent.common.utils.UrlUtils;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import org.json.JSONException;
import org.json.JSONObject;

public class jsDeeplink
  implements IOpenJsApis
{
  protected OpenJsHelper a;
  
  public jsDeeplink(OpenJsHelper paramOpenJsHelper)
  {
    this.a = paramOpenJsHelper;
  }
  
  private void a(String paramString1, String paramString2)
  {
    if (!a())
    {
      b(paramString2, "{\"error\":\"Unsupported domain\"");
      return;
    }
    Object localObject = this.a;
    if (!(localObject instanceof X5JsHelper))
    {
      b(paramString2, "{\"error\":\"Not X5\"");
      return;
    }
    localObject = (X5JsHelper)localObject;
    ((X5JsHelper)localObject).isInstalled(UrlUtils.getHost(((X5JsHelper)localObject).getUrl()), paramString1, new OpenJsHelper.CallbackRunnable()
    {
      private String b;
      
      public void run()
      {
        jsDeeplink.a(this.jdField_a_of_type_ComTencentSmttJsApiImplJsDeeplink, this.jdField_a_of_type_JavaLangString, this.b);
      }
      
      public void setReturnValue(String paramAnonymousString)
      {
        this.b = paramAnonymousString;
      }
    });
  }
  
  private void b(String paramString1, String paramString2)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      paramString2 = new JSONObject(paramString2);
    }
    catch (JSONException paramString2)
    {
      paramString2.printStackTrace();
      paramString2 = localJSONObject;
    }
    this.a.sendSuccJsCallback(paramString1, paramString2);
  }
  
  protected boolean a()
  {
    return true;
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.a.setOriginUrl(paramString3);
    if ("deeplinkCheckInstallation".equals(paramString1)) {
      try
      {
        paramString1 = paramJSONObject.toString();
        Log.e("deeplink-----------", paramString1);
        a(paramString1, paramString2);
      }
      catch (Exception paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsDeeplink.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */