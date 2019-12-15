package com.tencent.smtt.jsApi.impl;

import android.text.TextUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.smtt.jsApi.export.IWebviewNotifyListener;
import com.tencent.tbs.common.stat.TBSStatManager;
import java.util.ArrayList;
import org.json.JSONObject;

public class jsDebug
  implements IOpenJsApis, IWebviewNotifyListener
{
  private int jdField_a_of_type_Int;
  private OpenJsHelper jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
  private ArrayList<IWebviewNotifyListener> jdField_a_of_type_JavaUtilArrayList;
  private int b;
  private int c;
  
  public jsDebug(OpenJsHelper paramOpenJsHelper, ArrayList<IWebviewNotifyListener> paramArrayList)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
    this.jdField_a_of_type_JavaUtilArrayList = paramArrayList;
    if (this.jdField_a_of_type_JavaUtilArrayList != null) {
      a(this);
    }
  }
  
  private String a(String paramString, JSONObject paramJSONObject)
  {
    return null;
  }
  
  private void a(IWebviewNotifyListener paramIWebviewNotifyListener)
  {
    ArrayList localArrayList = this.jdField_a_of_type_JavaUtilArrayList;
    if ((localArrayList != null) && (!localArrayList.contains(paramIWebviewNotifyListener))) {
      this.jdField_a_of_type_JavaUtilArrayList.add(paramIWebviewNotifyListener);
    }
  }
  
  private void b(IWebviewNotifyListener paramIWebviewNotifyListener)
  {
    ArrayList localArrayList = this.jdField_a_of_type_JavaUtilArrayList;
    if (localArrayList != null) {
      localArrayList.remove(paramIWebviewNotifyListener);
    }
  }
  
  public boolean OnWebviewNotify(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    if (paramString.contains("firstScreenTime"))
    {
      if (this.jdField_a_of_type_Int > 0)
      {
        this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.fireEvent("onfirstscreen", paramString);
        return false;
      }
    }
    else if (paramString.contains("heightADWebviewVisiable"))
    {
      if (this.b > 0)
      {
        this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.fireEvent("onwebviewvalidate", paramString);
        return false;
      }
    }
    else if ((paramString.contains("miniQB")) && (this.c > 0)) {
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.fireEvent("onminiqbstatuschange", paramString);
    }
    return false;
  }
  
  public void destroy()
  {
    b(this);
  }
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.setOriginUrl(paramString3);
    if ("webviewIsValidate".equals(paramString1)) {
      return a(paramString2, paramJSONObject);
    }
    if ("subscribeChanged".equals(paramString1)) {
      return subscribeChanged(paramJSONObject);
    }
    if ("report".equals(paramString1)) {
      return report(paramJSONObject);
    }
    return null;
  }
  
  public String report(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return null;
    }
    paramJSONObject = paramJSONObject.optString("ActionID");
    if (StringUtils.isEmpty(paramJSONObject)) {
      return null;
    }
    TBSStatManager.getInstance().userBehaviorStatistics(paramJSONObject);
    return null;
  }
  
  public void setJsApiImpl(Object paramObject) {}
  
  public String subscribeChanged(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null) {
      return null;
    }
    int j = paramJSONObject.optInt("numHandlers");
    int i = j;
    if (j < 0) {
      i = 0;
    }
    paramJSONObject = paramJSONObject.optString("type");
    if (i > 0)
    {
      if ("onfirstscreen".equals(paramJSONObject))
      {
        this.jdField_a_of_type_Int = i;
        a(this);
        return null;
      }
      if ("onwebviewvalidate".equals(paramJSONObject))
      {
        this.b = i;
        a(this);
        return null;
      }
      if ("onminiqbstatuschange".equals(paramJSONObject))
      {
        this.c = i;
        a(this);
        return null;
      }
    }
    else if (i == 0)
    {
      b(this);
    }
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsDebug.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */