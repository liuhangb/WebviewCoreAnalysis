package com.tencent.common.serverconfig;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import com.tencent.basesupport.FLogger;
import com.tencent.common.http.Apn;
import com.tencent.common.wup.IUpdateIPListCallback;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPProxyHolder;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.common.wup.WUPTaskProxy;
import com.tencent.common.wup.base.MTT.JoinIPInfo;
import com.tencent.common.wup.base.MTT.RouteIPListReq;
import com.tencent.common.wup.base.MTT.RouteIPListRsp;
import com.tencent.common.wup.base.MTT.UserBase;
import com.tencent.common.wup.interfaces.IWUPClientProxy;
import com.tencent.mtt.ContextHolder;
import java.util.ArrayList;
import java.util.Iterator;

public class WupServerConfigsWrapper
{
  public static final String DEBUG_SERVER = "http://14.17.41.197:18000";
  public static final String GRAY_SERVER = "http://14.17.33.103:8080";
  public static final String PROXY_DOMAIN = WupProxyDomainRouter.a.domain;
  public static final String WUP_PROXY_DOMAIN = WupProxyDomainRouter.a.host;
  static a jdField_a_of_type_ComTencentCommonServerconfigWupServerConfigsWrapper$a;
  static a jdField_a_of_type_ComTencentCommonServerconfigA = new a();
  
  static
  {
    jdField_a_of_type_ComTencentCommonServerconfigWupServerConfigsWrapper$a = new a(null);
    try
    {
      IntentFilter localIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
      ContextHolder.getAppContext().registerReceiver(new b(), localIntentFilter);
      FLogger.d("WupServerConfigsWrapper", "addBroadcastObserver success");
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  static WUPRequestBase a(ArrayList<Integer> paramArrayList)
  {
    WUPRequestBase localWUPRequestBase = new WUPRequestBase("proxyip", "getIPListByRouter");
    RouteIPListReq localRouteIPListReq = new RouteIPListReq();
    Object localObject = a();
    ((UserBase)localObject).sAPN = Apn.getApnNameWithBSSID(Apn.getApnTypeS());
    localRouteIPListReq.stUB = ((UserBase)localObject);
    localRouteIPListReq.vIPType = paramArrayList;
    localRouteIPListReq.iSubType = IPListUtils.getConnectType(ContextHolder.getAppContext());
    if (localRouteIPListReq.iSubType == 1)
    {
      localObject = IPListUtils.getWifiBSSID(ContextHolder.getAppContext());
      paramArrayList = (ArrayList<Integer>)localObject;
      if (TextUtils.isEmpty((CharSequence)localObject)) {
        paramArrayList = "UNKNOW";
      }
      localRouteIPListReq.sExtraInfo = paramArrayList;
    }
    else
    {
      localObject = IPListUtils.getConnectExtraInfo(ContextHolder.getAppContext());
      paramArrayList = (ArrayList<Integer>)localObject;
      if (TextUtils.isEmpty((CharSequence)localObject)) {
        paramArrayList = "UNKNOW";
      }
      localRouteIPListReq.sExtraInfo = paramArrayList;
    }
    paramArrayList = new StringBuilder();
    localObject = IPListUtils.getMCC(ContextHolder.getAppContext());
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      paramArrayList.append("NULL");
    } else {
      paramArrayList.append((String)localObject);
    }
    localObject = IPListUtils.getMNC(ContextHolder.getAppContext());
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      paramArrayList.append("NULL");
    } else {
      paramArrayList.append((String)localObject);
    }
    localRouteIPListReq.sMCCMNC = paramArrayList.toString();
    localObject = IPListUtils.getConnectTypeName(ContextHolder.getAppContext());
    paramArrayList = (ArrayList<Integer>)localObject;
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      paramArrayList = "UNKNOW";
    }
    localRouteIPListReq.sTypeName = paramArrayList;
    localWUPRequestBase.put("req", localRouteIPListReq);
    localWUPRequestBase.setRequestCallBack(jdField_a_of_type_ComTencentCommonServerconfigWupServerConfigsWrapper$a);
    localWUPRequestBase.setUrl(WupProxyDomainRouter.getInstance().getWupProxyDomain());
    return localWUPRequestBase;
  }
  
  private static UserBase a()
  {
    UserBase localUserBase = new UserBase();
    IWUPClientProxy localIWUPClientProxy = WUPProxyHolder.getPublicWUPProxy();
    Object localObject;
    if (localIWUPClientProxy == null) {
      localObject = new byte[16];
    } else {
      localObject = localIWUPClientProxy.getByteGuid();
    }
    localUserBase.sGUID = ((byte[])localObject);
    localUserBase.sLC = "D70A3465D4EE4E9";
    localUserBase.iServerVer = 2;
    if (localIWUPClientProxy == null) {
      localObject = "";
    } else {
      localObject = localIWUPClientProxy.getQUA(true);
    }
    localUserBase.sQUA = ((String)localObject);
    if (localUserBase.sQUA == null) {
      localUserBase.sQUA = "";
    }
    return localUserBase;
  }
  
  public static String getNextWupProxyAddress(String paramString, IWUPClientProxy paramIWUPClientProxy)
  {
    if (paramIWUPClientProxy != null)
    {
      paramIWUPClientProxy = paramIWUPClientProxy.getCustomWupProxyAddress();
      if (!TextUtils.isEmpty(paramIWUPClientProxy))
      {
        paramString = new StringBuilder();
        paramString.append("getWupProxyAddress, return custom addr=");
        paramString.append(paramIWUPClientProxy);
        FLogger.d("WupServerConfigsWrapper", paramString.toString());
        return paramIWUPClientProxy;
      }
    }
    return jdField_a_of_type_ComTencentCommonServerconfigA.a(paramString);
  }
  
  public static int getWupAddressIndex(IWUPClientProxy paramIWUPClientProxy)
  {
    if ((paramIWUPClientProxy != null) && (!TextUtils.isEmpty(paramIWUPClientProxy.getCustomWupProxyAddress()))) {
      return -2;
    }
    return jdField_a_of_type_ComTencentCommonServerconfigA.a();
  }
  
  public static String getWupAddressReason()
  {
    return jdField_a_of_type_ComTencentCommonServerconfigA.b();
  }
  
  public static String getWupProxyAddress(IWUPClientProxy paramIWUPClientProxy)
  {
    if (paramIWUPClientProxy != null) {}
    try
    {
      paramIWUPClientProxy = paramIWUPClientProxy.getCustomWupProxyAddress();
      if (!TextUtils.isEmpty(paramIWUPClientProxy))
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("getWupProxyAddress, return custom addr=");
        localStringBuilder.append(paramIWUPClientProxy);
        FLogger.d("WupServerConfigsWrapper", localStringBuilder.toString());
        return paramIWUPClientProxy;
      }
      paramIWUPClientProxy = jdField_a_of_type_ComTencentCommonServerconfigA.a();
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getWupProxyAddress, res=");
      localStringBuilder.append(paramIWUPClientProxy);
      FLogger.d("WupServerConfigsWrapper", localStringBuilder.toString());
      return paramIWUPClientProxy;
    }
    finally {}
  }
  
  public static boolean needPullWupServer()
  {
    return (!jdField_a_of_type_ComTencentCommonServerconfigA.a()) || (jdField_a_of_type_ComTencentCommonServerconfigA.b());
  }
  
  public static void onCmdUpdateIPList(boolean paramBoolean, final IUpdateIPListCallback paramIUpdateIPListCallback)
  {
    Object localObject = new ArrayList();
    ((ArrayList)localObject).add(Integer.valueOf(1));
    localObject = a((ArrayList)localObject);
    ((WUPRequestBase)localObject).setEmergencyTask(true);
    ((WUPRequestBase)localObject).setUrl(null);
    ((WUPRequestBase)localObject).setRequestCallBack(new IWUPRequestCallBack()
    {
      public void onWUPTaskFail(WUPRequestBase paramAnonymousWUPRequestBase)
      {
        FLogger.d("WupServerConfigsWrapper", "onCmdUpdateIPList: FAIL");
        if (paramAnonymousWUPRequestBase == null) {
          return;
        }
        if (this.jdField_a_of_type_Boolean) {
          IPListDataManager.getInstance().clearServerList("wup", true);
        }
        if (paramAnonymousWUPRequestBase.getErrorCode() == 63525)
        {
          FLogger.d("WupServerConfigsWrapper", "onCmdUpdateIPList: FAIL, but we got an overload exception, ignore");
          paramAnonymousWUPRequestBase = paramIUpdateIPListCallback;
          if (paramAnonymousWUPRequestBase != null) {
            paramAnonymousWUPRequestBase.onUpdateFinished(false);
          }
          return;
        }
        if (!WupProxyDomainRouter.isWupProxyDomains(paramAnonymousWUPRequestBase.getUrl()))
        {
          FLogger.d("WupServerConfigsWrapper", "onCmdUpdateIPList: FAIL, but can retry");
          paramAnonymousWUPRequestBase.clearPath();
          paramAnonymousWUPRequestBase.setErrorCode(0);
          paramAnonymousWUPRequestBase.setFailedReason(null);
          paramAnonymousWUPRequestBase.setUrl(WupProxyDomainRouter.getInstance().getWupProxyDomain());
          WUPTaskProxy.send(paramAnonymousWUPRequestBase);
          return;
        }
        paramAnonymousWUPRequestBase = paramIUpdateIPListCallback;
        if (paramAnonymousWUPRequestBase != null) {
          paramAnonymousWUPRequestBase.onUpdateFinished(false);
        }
      }
      
      public void onWUPTaskSuccess(WUPRequestBase paramAnonymousWUPRequestBase, WUPResponseBase paramAnonymousWUPResponseBase)
      {
        FLogger.d("WupServerConfigsWrapper", "onCmdUpdateIPList: SUCC");
        if (this.jdField_a_of_type_Boolean)
        {
          IPListDataManager.getInstance().clearServerList("wup", false);
          WupServerConfigsWrapper.jdField_a_of_type_ComTencentCommonServerconfigA.b("list_update_cmd");
        }
        WupServerConfigsWrapper.jdField_a_of_type_ComTencentCommonServerconfigWupServerConfigsWrapper$a.onWUPTaskSuccess(paramAnonymousWUPRequestBase, paramAnonymousWUPResponseBase);
        paramAnonymousWUPRequestBase = paramIUpdateIPListCallback;
        if (paramAnonymousWUPRequestBase != null) {
          paramAnonymousWUPRequestBase.onUpdateFinished(true);
        }
      }
    });
    WUPTaskProxy.send((WUPRequestBase)localObject);
  }
  
  public static void saveWupProxyList(String paramString, ArrayList<String> paramArrayList)
  {
    jdField_a_of_type_ComTencentCommonServerconfigA.a(paramString, paramArrayList);
  }
  
  public static void setWupAddressReason(String paramString)
  {
    jdField_a_of_type_ComTencentCommonServerconfigA.c(paramString);
  }
  
  public static void startCheckWupDomain()
  {
    jdField_a_of_type_ComTencentCommonServerconfigA.b();
  }
  
  public static void startCheckWupIPAddr(String paramString)
  {
    jdField_a_of_type_ComTencentCommonServerconfigA.f(paramString);
  }
  
  private static class a
    implements IWUPRequestCallBack
  {
    public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
    {
      FLogger.d("wup-ip-list", "fetch WUP IP list FAIL");
    }
    
    public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
    {
      paramWUPRequestBase = paramWUPResponseBase.getReturnCode();
      if ((paramWUPRequestBase != null) && (paramWUPRequestBase.intValue() >= 0))
      {
        paramWUPRequestBase = (RouteIPListRsp)paramWUPResponseBase.get("rsp");
        if (paramWUPRequestBase == null) {
          return;
        }
        paramWUPResponseBase = paramWUPRequestBase.vIPInfos.iterator();
        while (paramWUPResponseBase.hasNext())
        {
          JoinIPInfo localJoinIPInfo = (JoinIPInfo)paramWUPResponseBase.next();
          if (localJoinIPInfo.eIPType == 1)
          {
            Object localObject = new StringBuilder();
            ((StringBuilder)localObject).append(paramWUPRequestBase.sTypeName);
            ((StringBuilder)localObject).append(paramWUPRequestBase.iSubType);
            ((StringBuilder)localObject).append(paramWUPRequestBase.sExtraInfo);
            ((StringBuilder)localObject).append(paramWUPRequestBase.sMCCMNC);
            ((StringBuilder)localObject).append("wup");
            localObject = ((StringBuilder)localObject).toString();
            WupServerConfigsWrapper.saveWupProxyList((String)localObject, localJoinIPInfo.vIPList);
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("on Receive IPList in WUP module, netinfo = ");
            localStringBuilder.append((String)localObject);
            localStringBuilder.append(", onIPList: _WUPPROXY = ");
            localStringBuilder.append(localJoinIPInfo.vIPList);
            FLogger.d("wup-ip-list", localStringBuilder.toString());
          }
        }
        IPListDataManager.getInstance().saveServerList();
      }
    }
  }
  
  static class b
    extends BroadcastReceiver
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ((paramIntent != null) && ("android.net.conn.CONNECTIVITY_CHANGE".equals(paramIntent.getAction()))) {
        WupServerConfigsWrapper.a.a(paramIntent);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\serverconfig\WupServerConfigsWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */