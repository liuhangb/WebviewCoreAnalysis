package com.tencent.smtt.jsApi.impl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.tencent.common.http.Apn;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import org.json.JSONObject;

public class jsNetwork
  implements IOpenJsApis
{
  private Context jdField_a_of_type_AndroidContentContext;
  private OpenJsHelper jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
  private AppSystemReceiver jdField_a_of_type_ComTencentSmttJsApiImplJsNetwork$AppSystemReceiver;
  
  public jsNetwork(OpenJsHelper paramOpenJsHelper)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
    this.jdField_a_of_type_AndroidContentContext = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getContext();
  }
  
  private void a(Context paramContext)
  {
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplJsNetwork$AppSystemReceiver == null)
    {
      IntentFilter localIntentFilter = new IntentFilter();
      localIntentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
      this.jdField_a_of_type_ComTencentSmttJsApiImplJsNetwork$AppSystemReceiver = new AppSystemReceiver(null);
      try
      {
        paramContext.registerReceiver(this.jdField_a_of_type_ComTencentSmttJsApiImplJsNetwork$AppSystemReceiver, localIntentFilter);
        return;
      }
      catch (Throwable paramContext)
      {
        paramContext.printStackTrace();
        this.jdField_a_of_type_ComTencentSmttJsApiImplJsNetwork$AppSystemReceiver = null;
      }
    }
  }
  
  private void b(Context paramContext)
  {
    AppSystemReceiver localAppSystemReceiver = this.jdField_a_of_type_ComTencentSmttJsApiImplJsNetwork$AppSystemReceiver;
    if (localAppSystemReceiver != null) {}
    try
    {
      paramContext.unregisterReceiver(localAppSystemReceiver);
      this.jdField_a_of_type_ComTencentSmttJsApiImplJsNetwork$AppSystemReceiver = null;
      return;
    }
    catch (Throwable paramContext)
    {
      for (;;) {}
    }
  }
  
  void a(String paramString1, String paramString2)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("ret", paramString2);
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString1, localJSONObject);
      return;
    }
    catch (Exception paramString1) {}
  }
  
  protected boolean a()
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("network");
    ((StringBuilder)localObject).append(".");
    ((StringBuilder)localObject).append(Thread.currentThread().getStackTrace()[3].getMethodName());
    localObject = ((StringBuilder)localObject).toString();
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkJsApiDomain((String)localObject) == 1) {
      return true;
    }
    return this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.checkQQDomain();
  }
  
  public void destroy()
  {
    b(this.jdField_a_of_type_AndroidContentContext);
  }
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.setOriginUrl(paramString3);
    if (!a())
    {
      paramString1 = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
      a(paramString2, "this api without user authorization");
      return null;
    }
    if ("type".equals(paramString1)) {
      return type();
    }
    if ("subscribeChanged".equals(paramString1)) {
      return subscribeChanged(paramJSONObject);
    }
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
  
  public String subscribeChanged(JSONObject paramJSONObject)
  {
    if (paramJSONObject != null) {
      if (paramJSONObject.optInt("numHandlers") > 0) {
        a(this.jdField_a_of_type_AndroidContentContext);
      } else {
        b(this.jdField_a_of_type_AndroidContentContext);
      }
    }
    return null;
  }
  
  public String type()
  {
    if (!Apn.isNetworkAvailable()) {
      return "none";
    }
    if (Apn.isWifiMode()) {
      return "wifi";
    }
    if (Apn.is2GMode()) {
      return "2g";
    }
    if (Apn.is3GMode()) {
      return "3g";
    }
    if (Apn.is4GMode()) {
      return "4g";
    }
    return "unkown";
  }
  
  private class AppSystemReceiver
    extends BroadcastReceiver
  {
    private AppSystemReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if (paramIntent == null) {
        return;
      }
      if (("android.net.conn.CONNECTIVITY_CHANGE".equals(paramIntent.getAction())) && (paramIntent != null) && ("android.net.conn.CONNECTIVITY_CHANGE".equals(paramIntent.getAction())))
      {
        paramContext = new StringBuilder();
        paramContext.append("{\"currentType\":\"");
        paramContext.append(jsNetwork.this.type());
        paramContext.append("\"}");
        paramContext = paramContext.toString();
        jsNetwork.a(jsNetwork.this).fireEvent("onconnectionchange", paramContext);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsNetwork.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */