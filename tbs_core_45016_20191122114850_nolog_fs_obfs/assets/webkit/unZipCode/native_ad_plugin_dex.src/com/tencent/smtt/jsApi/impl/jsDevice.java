package com.tencent.smtt.jsApi.impl;

import android.annotation.SuppressLint;
import android.util.Log;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.tbs.common.utils.DeviceUtils;
import org.json.JSONObject;

public class jsDevice
  implements IOpenJsApis
{
  private OpenJsHelper a;
  
  public jsDevice(OpenJsHelper paramOpenJsHelper)
  {
    this.a = paramOpenJsHelper;
  }
  
  private String a(String paramString, JSONObject paramJSONObject)
  {
    return null;
  }
  
  @SuppressLint({"NewApi"})
  private String b(String paramString, JSONObject paramJSONObject)
  {
    if (!a())
    {
      paramJSONObject = this.a;
      a(paramString, "this api without user authorization");
      return null;
    }
    return DeviceUtils.getMacAddressString(this.a.getContext());
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
    ((StringBuilder)localObject).append("device");
    ((StringBuilder)localObject).append(".");
    ((StringBuilder)localObject).append(Thread.currentThread().getStackTrace()[3].getMethodName());
    localObject = ((StringBuilder)localObject).toString();
    int i = this.a.checkJsApiDomain((String)localObject);
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("checkQQDomain : ");
    localStringBuilder.append((String)localObject);
    localStringBuilder.append("= ");
    localStringBuilder.append(i);
    Log.e("minafang", localStringBuilder.toString());
    if (i == 1) {
      return true;
    }
    return this.a.checkQQDomain();
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.a.setOriginUrl(paramString3);
    if ("getIMEI".equals(paramString1)) {
      return a(paramString2, paramJSONObject);
    }
    if ("getMAC".equals(paramString1)) {
      return b(paramString2, paramJSONObject);
    }
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsDevice.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */