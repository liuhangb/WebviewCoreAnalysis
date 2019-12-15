package com.tencent.smtt.jsApi.impl;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.smtt.jsApi.impl.utils.ReflectionUtils;
import com.tencent.tbs.common.MTT.TBSJSApiApiNames;
import com.tencent.tbs.common.MTT.TBSJSApiWhitelistReq;
import com.tencent.tbs.common.MTT.TBSJSApiWhitelistRsp;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.baseinfo.TbsDomainWhiteListManager;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.lang.ref.WeakReference;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.json.JSONObject;

public class jsApiDomainWhiteListManager
  implements IWUPRequestCallBack
{
  private static jsApiDomainWhiteListManager jdField_a_of_type_ComTencentSmttJsApiImplJsApiDomainWhiteListManager;
  private String jdField_a_of_type_JavaLangString = null;
  private WeakReference<Object> jdField_a_of_type_JavaLangRefWeakReference = null;
  
  private static void a(WUPResponseBase paramWUPResponseBase)
  {
    if (paramWUPResponseBase != null)
    {
      paramWUPResponseBase = (TBSJSApiWhitelistRsp)paramWUPResponseBase.get("rsp");
      if (paramWUPResponseBase != null)
      {
        int i = paramWUPResponseBase.eRetCode;
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("onJsApiWhiteList rspRet: ");
        ((StringBuilder)localObject).append(i);
        Log.e("jsapiDomain", ((StringBuilder)localObject).toString());
        if (i != 0) {
          return;
        }
        localObject = paramWUPResponseBase.mHostApiNames;
        if ((localObject != null) && (((Map)localObject).size() > 0))
        {
          if (TbsUserInfo.getInstance().getJSApiDomainList() != null) {
            TbsUserInfo.getInstance().getJSApiDomainList().clear();
          }
          Log.e("jsapiDomain", "configList clear");
          TbsUserInfo.setJSApiDomainList(paramWUPResponseBase.mHostApiNames);
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("save config list: ");
          localStringBuilder.append(((Map)localObject).size());
          Log.e("jsapiDomain", localStringBuilder.toString());
          TbsDomainWhiteListManager.saveJsApiRspData(paramWUPResponseBase);
        }
      }
      else
      {
        Log.e("jsapiDomain", "packet.get(rsp) failed");
      }
    }
  }
  
  private void a(String paramString)
  {
    JSONObject localJSONObject;
    if ((!TextUtils.isEmpty(this.jdField_a_of_type_JavaLangString)) && (this.jdField_a_of_type_JavaLangRefWeakReference.get() != null)) {
      localJSONObject = new JSONObject();
    }
    try
    {
      localJSONObject.put("data", paramString);
      paramString = new StringBuilder();
      paramString.append("javascript:(");
      paramString.append(this.jdField_a_of_type_JavaLangString);
      paramString.append(".call(this,");
      paramString.append(localJSONObject.toString());
      paramString.append("));");
      paramString = paramString.toString();
      ReflectionUtils.invokeMethod(this.jdField_a_of_type_JavaLangRefWeakReference.get(), "com.tencent.tbs.core.webkit.tencent.TencentWebViewProxy", "loadUrl", new Class[] { String.class }, new Object[] { paramString });
      return;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
  }
  
  private void a(Map<String, TBSJSApiApiNames> paramMap, String paramString, WUPRequest paramWUPRequest)
  {
    if ((paramMap != null) && (paramMap.size() > 0))
    {
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        String str = (String)((Map.Entry)paramMap.next()).getKey();
        if ((!TextUtils.isEmpty(str)) && (a(paramString, str)))
        {
          if (!TbsDomainWhiteListManager.isJsApiOverValidTimeOrRange())
          {
            a("in file cached");
            Log.e("jsapiDomain", "onReady handleFileJsApiDomainList ok");
            return;
          }
          TbsDomainWhiteListManager.deleteApiDomainFile();
          Log.e("jsapiDomain", "onReady handleFileJsApiDomainList overvalide");
          WUPTaskProxy.send(paramWUPRequest);
          return;
        }
      }
      Log.e("jsapiDomain", "onReady handleFileJsApiDomainList fail 1");
      WUPTaskProxy.send(paramWUPRequest);
      return;
    }
    Log.e("jsapiDomain", "onReady handleFileJsApiDomainList fail 2");
    WUPTaskProxy.send(paramWUPRequest);
  }
  
  private static boolean a(String paramString1, String paramString2)
  {
    if (TextUtils.isEmpty(paramString1)) {
      return false;
    }
    if (paramString1.equalsIgnoreCase(paramString2)) {
      return true;
    }
    if (paramString2.startsWith("*."))
    {
      paramString2 = paramString2.substring(1, paramString2.length());
    }
    else
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(".");
      localStringBuilder.append(paramString2);
      paramString2 = localStringBuilder.toString();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(".");
    localStringBuilder.append(paramString1);
    return localStringBuilder.toString().endsWith(paramString2);
  }
  
  private boolean a(Map<String, TBSJSApiApiNames> paramMap, String paramString, WUPRequest paramWUPRequest)
  {
    if ((paramMap != null) && (paramMap.size() > 0))
    {
      paramMap = paramMap.entrySet().iterator();
      while (paramMap.hasNext())
      {
        paramWUPRequest = (String)((Map.Entry)paramMap.next()).getKey();
        if (!TextUtils.isEmpty(paramWUPRequest)) {
          return a(paramString, paramWUPRequest);
        }
      }
    }
    Log.e("jsapiDomain", "onReady handleLocalJsApiDomainList false");
    return false;
  }
  
  public static jsApiDomainWhiteListManager getInstance()
  {
    if (jdField_a_of_type_ComTencentSmttJsApiImplJsApiDomainWhiteListManager == null) {
      jdField_a_of_type_ComTencentSmttJsApiImplJsApiDomainWhiteListManager = new jsApiDomainWhiteListManager();
    }
    return jdField_a_of_type_ComTencentSmttJsApiImplJsApiDomainWhiteListManager;
  }
  
  public WUPRequest getJsApiDomainRequest(Object paramObject, String paramString1, String paramString2)
  {
    TBSJSApiWhitelistReq localTBSJSApiWhitelistReq = new TBSJSApiWhitelistReq();
    localTBSJSApiWhitelistReq.sAuth = TbsUserInfo.mJsApiSAuth;
    localTBSJSApiWhitelistReq.sGuid = GUIDFactory.getInstance().getStrGuid();
    localTBSJSApiWhitelistReq.sQua = TbsInfoUtils.getQUA2();
    localTBSJSApiWhitelistReq.sHost = paramString1;
    paramString1 = new WUPRequest("tbsjsapi", "getWhitelistApi");
    paramString1.put("req", localTBSJSApiWhitelistReq);
    paramString1.setRequestCallBack(this);
    paramString1.setType((byte)67);
    paramString1.setBindObject(paramObject);
    this.jdField_a_of_type_JavaLangString = paramString2;
    return paramString1;
  }
  
  public void onReady(Object paramObject, String paramString1, String paramString2, String paramString3, String paramString4)
  {
    Log.e("jsapiDomain", "onReady");
    this.jdField_a_of_type_JavaLangRefWeakReference = new WeakReference(paramObject);
    try
    {
      paramObject = Uri.parse(paramString2).getHost();
    }
    catch (Exception paramObject)
    {
      ((Exception)paramObject).printStackTrace();
      paramObject = null;
    }
    if (TextUtils.isEmpty((CharSequence)paramObject))
    {
      a("host error");
      return;
    }
    try
    {
      paramString1 = new JSONObject(paramString1).optString("useCachedApi");
      paramString4 = paramString1;
    }
    catch (Exception paramString1)
    {
      paramString1.printStackTrace();
    }
    paramString1 = getJsApiDomainRequest(null, (String)paramObject, paramString3);
    if (!TextUtils.isEmpty(paramString4))
    {
      if (paramString4.equalsIgnoreCase("false"))
      {
        Log.e("jsapiDomain", "onReady 1");
        WUPTaskProxy.send(paramString1);
        return;
      }
      paramString2 = TbsUserInfo.getInstance().getJSApiDomainList();
      boolean bool2 = false;
      boolean bool1 = bool2;
      if (paramString2 != null)
      {
        bool1 = bool2;
        if (paramString2.size() > 0) {
          bool1 = a(paramString2, (String)paramObject, paramString1);
        }
      }
      if (bool1 == true)
      {
        Log.e("jsapiDomain", "onReady 2");
        a("in memory cached");
        return;
      }
      paramString2 = TbsDomainWhiteListManager.loadJsApiWhiteList();
      TbsUserInfo.setJSApiDomainList(paramString2);
      a(paramString2, (String)paramObject, paramString1);
      return;
    }
    Log.e("jsapiDomain", "onReady 7");
    WUPTaskProxy.send(paramString1);
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    a("update failed!");
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    if ((paramWUPRequestBase != null) && (paramWUPRequestBase.getType() == 67))
    {
      a(paramWUPResponseBase);
      a("update domain list succ");
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsApiDomainWhiteListManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */