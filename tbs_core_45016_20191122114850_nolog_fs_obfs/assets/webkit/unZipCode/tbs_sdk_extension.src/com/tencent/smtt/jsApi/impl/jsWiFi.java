package com.tencent.smtt.jsApi.impl;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.smtt.jsApi.export.IOpenJsApis;
import com.tencent.tbs.common.stat.TBSStatManager;
import com.tencent.tbs.common.utils.PermissionUtils;
import com.tencent.tbs.common.wifi.WifiApInfo;
import com.tencent.tbs.common.wifi.WifiEngine;
import com.tencent.tbs.common.wifi.WifiEngine.WifiDataChangedListener;
import com.tencent.tbs.common.wifi.WifiEngine.WifiStateChangedListener;
import com.tencent.tbs.common.wifi.WifiNetworkDetector.NetworkDetectListener;
import com.tencent.tbs.common.wifi.WifiNetworkDetectorFacade;
import com.tencent.tbs.common.wifi.WifiUtils;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine.WiFiConnectState;
import com.tencent.tbs.common.wifi.state.WiFiConnectStateMachine.WiFiConnectStateChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class jsWiFi
  implements IOpenJsApis, WifiEngine.WifiDataChangedListener, WifiEngine.WifiStateChangedListener, WifiNetworkDetector.NetworkDetectListener, WiFiConnectStateMachine.WiFiConnectStateChangeListener
{
  protected OpenJsHelper a;
  boolean a;
  
  public jsWiFi(OpenJsHelper paramOpenJsHelper)
  {
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper = paramOpenJsHelper;
    WifiEngine.getInstance().addWifiStateChangedListener(this);
    WiFiConnectStateMachine.getInstance().addListener(this);
  }
  
  private String a(String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    Object localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("[connectAp] action:");
    ((StringBuilder)localObject1).append(paramString1);
    ((StringBuilder)localObject1).append(", callbackId:");
    ((StringBuilder)localObject1).append(paramString2);
    ((StringBuilder)localObject1).append(", json:");
    ((StringBuilder)localObject1).append(paramJSONObject);
    LogUtils.d("jsWiFi", ((StringBuilder)localObject1).toString());
    localObject1 = new JSONObject();
    if (a(paramString1)) {
      try
      {
        LogUtils.d("jsWiFi", "[connectAp] enter try:");
        int i = paramJSONObject.optInt("inforfrom", 0);
        paramString1 = paramJSONObject.optJSONArray("wifiList");
        if ((paramString1 != null) && (paramString1.length() > 0))
        {
          paramString1 = paramString1.getJSONObject(0);
          paramJSONObject = paramString1.optString("mSsid", "");
          Object localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("[connectAp] after get ssid:");
          ((StringBuilder)localObject2).append(paramJSONObject);
          LogUtils.d("jsWiFi", ((StringBuilder)localObject2).toString());
          if (!TextUtils.isEmpty(paramJSONObject))
          {
            localObject2 = WiFiConnectStateMachine.getInstance().getCurState();
            String str = ((WiFiConnectStateMachine.WiFiConnectState)localObject2).getSsid();
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("[connectAp] strCurrAp:");
            localStringBuilder.append(str);
            localStringBuilder.append(", IntStateType:");
            localStringBuilder.append(((WiFiConnectStateMachine.WiFiConnectState)localObject2).getIntStateType());
            LogUtils.d("jsWiFi", localStringBuilder.toString());
            if ((!TextUtils.isEmpty(str)) && (str.equals(paramJSONObject)) && (((WiFiConnectStateMachine.WiFiConnectState)localObject2).getIntStateType() == 2))
            {
              paramString1 = new JSONObject();
              paramString1.put("ssid", ((WiFiConnectStateMachine.WiFiConnectState)localObject2).getSsid());
              paramString1.put("state", ((WiFiConnectStateMachine.WiFiConnectState)localObject2).getIntStateType());
              paramString1.put("stateString", ((WiFiConnectStateMachine.WiFiConnectState)localObject2).getName());
              if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper != null) {
                this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.callJs("onWiFiConnectStateChange", new Object[] { paramString1.toString() });
              }
              ((JSONObject)localObject1).put("resultCode", 0);
              TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-CONNECTAP-0");
            }
            else if (WifiEngine.getInstance().getWifiState() != 3)
            {
              TBSStatManager.getInstance().userBehaviorStatistics("AWTWF105");
              ((JSONObject)localObject1).put("resultCode", 1);
              TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-CONNECTAP-1");
            }
            else
            {
              localObject2 = new WifiApInfo();
              ((WifiApInfo)localObject2).mActionType = paramString1.optLong("mActionType", 0L);
              ((WifiApInfo)localObject2).mCanSharePwd = paramString1.optBoolean("mCanSharePwd", false);
              ((WifiApInfo)localObject2).mConnectFrom = paramString1.optInt("mConnectFrom", -1);
              ((WifiApInfo)localObject2).mConnectType = paramString1.optInt("mConnectType", 1);
              ((WifiApInfo)localObject2).mForceNewConfig = paramString1.optBoolean("mForceNewConfig", false);
              ((WifiApInfo)localObject2).mFrequency = paramString1.optInt("mFrequency", -1);
              ((WifiApInfo)localObject2).mHasCloudPassword = paramString1.optBoolean("mHasCloudPassword", false);
              ((WifiApInfo)localObject2).mIsSavedWifi = paramString1.optBoolean("mIsSavedWifi", false);
              ((WifiApInfo)localObject2).mLevel = paramString1.optInt("mLevel", Integer.MIN_VALUE);
              ((WifiApInfo)localObject2).mReportState = paramString1.optInt("mReportState", -1);
              ((WifiApInfo)localObject2).mSafeType = paramString1.optInt("mSafeType");
              ((WifiApInfo)localObject2).mScore = paramString1.optInt("mScore", 0);
              ((WifiApInfo)localObject2).mSdkFlag = paramString1.optInt("mSdkFlag", 0);
              ((WifiApInfo)localObject2).mTargetConfigId = paramString1.optInt("mTargetConfigId", -1);
              ((WifiApInfo)localObject2).mTargetSdk = paramString1.optInt("mTargetSdk", -1);
              ((WifiApInfo)localObject2).mUserInputPwd = paramString1.optBoolean("mUserInputPwd", false);
              ((WifiApInfo)localObject2).mWiFiType = paramString1.optInt("mWiFiType", 0);
              ((WifiApInfo)localObject2).mBrand = paramString1.optString("mBrand", "");
              ((WifiApInfo)localObject2).mBssid = paramString1.optString("mBssid", "");
              ((WifiApInfo)localObject2).mIdentity = paramString1.optString("mIdentity", "");
              ((WifiApInfo)localObject2).mPassword = paramString1.optString("mPassword", "");
              ((WifiApInfo)localObject2).mSsid = paramJSONObject;
              ((WifiApInfo)localObject2).mWeixinSchama = paramString1.optString("mWeixinSchama", "");
              paramString1 = new StringBuilder();
              paramString1.append("[connectAp INFOR_FROM_SHARE] apInfo:");
              paramString1.append(localObject2);
              LogUtils.d("jsWiFi", paramString1.toString());
              WifiEngine.getInstance().connect((WifiApInfo)localObject2, ((WifiApInfo)localObject2).mUserInputPwd);
              ((JSONObject)localObject1).put("resultCode", 0);
              TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-CONNECTAP-0");
              paramString1 = TBSStatManager.getInstance();
              paramJSONObject = new StringBuilder();
              paramJSONObject.append("AWNWF50_D-TBS-WIFI-JSAPI-CONNECTAP-FROM-");
              paramJSONObject.append(i);
              paramString1.userBehaviorStatistics(paramJSONObject.toString());
            }
          }
          else
          {
            ((JSONObject)localObject1).put("resultCode", 1);
            TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-CONNECTAP-1");
          }
        }
        else
        {
          ((JSONObject)localObject1).put("resultCode", 1);
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-CONNECTAP-1");
        }
      }
      catch (JSONException paramString1)
      {
        LogUtils.d("jsWiFi", "[connectAp] JSONException:");
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("[connectAp] e1:");
        paramJSONObject.append(paramString1.toString());
        LogUtils.d("jsWiFi", paramJSONObject.toString());
        paramString1.printStackTrace();
        try
        {
          ((JSONObject)localObject1).put("resultCode", 1);
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-CONNECTAP-1");
        }
        catch (JSONException paramString1)
        {
          paramString1.printStackTrace();
        }
      }
    } else {
      try
      {
        ((JSONObject)localObject1).put("resultCode", 1);
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-CONNECTAP-1");
      }
      catch (JSONException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString2, (JSONObject)localObject1);
    TBSStatManager.getInstance().userBehaviorStatistics("AWTWF109");
    return ((JSONObject)localObject1).toString();
  }
  
  private JSONArray a()
  {
    Object localObject1 = WifiEngine.getInstance().getScanResultList();
    if ((localObject1 != null) && (((ArrayList)localObject1).size() > 0))
    {
      JSONArray localJSONArray2 = new JSONArray();
      try
      {
        Iterator localIterator = ((ArrayList)localObject1).iterator();
        for (;;)
        {
          localObject1 = localJSONArray2;
          if (!localIterator.hasNext()) {
            break;
          }
          localObject1 = (WifiApInfo)localIterator.next();
          Object localObject2 = new StringBuilder();
          ((StringBuilder)localObject2).append("[getWifiList] apInfo:");
          ((StringBuilder)localObject2).append(localObject1);
          LogUtils.d("jsWiFi", ((StringBuilder)localObject2).toString());
          localObject2 = new JSONObject();
          ((JSONObject)localObject2).put("mActionType", ((WifiApInfo)localObject1).mActionType);
          ((JSONObject)localObject2).put("mCanSharePwd", ((WifiApInfo)localObject1).mCanSharePwd);
          ((JSONObject)localObject2).put("mConnectFrom", ((WifiApInfo)localObject1).mConnectFrom);
          ((JSONObject)localObject2).put("mConnectType", ((WifiApInfo)localObject1).mConnectType);
          ((JSONObject)localObject2).put("mForceNewConfig", ((WifiApInfo)localObject1).mForceNewConfig);
          ((JSONObject)localObject2).put("mFrequency", ((WifiApInfo)localObject1).mFrequency);
          ((JSONObject)localObject2).put("mHasCloudPassword", ((WifiApInfo)localObject1).mHasCloudPassword);
          ((JSONObject)localObject2).put("mIsSavedWifi", ((WifiApInfo)localObject1).mIsSavedWifi);
          ((JSONObject)localObject2).put("mLevel", ((WifiApInfo)localObject1).mLevel);
          ((JSONObject)localObject2).put("mReportState", ((WifiApInfo)localObject1).mReportState);
          ((JSONObject)localObject2).put("mSafeType", ((WifiApInfo)localObject1).mSafeType);
          ((JSONObject)localObject2).put("mScore", ((WifiApInfo)localObject1).mScore);
          ((JSONObject)localObject2).put("mSdkFlag", ((WifiApInfo)localObject1).mSdkFlag);
          ((JSONObject)localObject2).put("mTargetConfigId", ((WifiApInfo)localObject1).mTargetConfigId);
          ((JSONObject)localObject2).put("mTargetSdk", ((WifiApInfo)localObject1).mTargetSdk);
          ((JSONObject)localObject2).put("mUserInputPwd", ((WifiApInfo)localObject1).mUserInputPwd);
          ((JSONObject)localObject2).put("mWiFiType", ((WifiApInfo)localObject1).mWiFiType);
          ((JSONObject)localObject2).put("mBrand", ((WifiApInfo)localObject1).mBrand);
          ((JSONObject)localObject2).put("mBssid", ((WifiApInfo)localObject1).mBssid);
          ((JSONObject)localObject2).put("mIdentity", ((WifiApInfo)localObject1).mIdentity);
          ((JSONObject)localObject2).put("mPassword", ((WifiApInfo)localObject1).mPassword);
          ((JSONObject)localObject2).put("mPortal", ((WifiApInfo)localObject1).mPortal);
          ((JSONObject)localObject2).put("mSsid", ((WifiApInfo)localObject1).mSsid);
          ((JSONObject)localObject2).put("mWeixinSchama", ((WifiApInfo)localObject1).mWeixinSchama);
          localJSONArray2.put(localObject2);
        }
        localJSONArray1 = null;
      }
      catch (JSONException localJSONException)
      {
        localJSONException.printStackTrace();
        return localJSONArray2;
      }
    }
    JSONArray localJSONArray1;
    return localJSONArray1;
  }
  
  private String b(String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[getWifiList] action:");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", callbackId:");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(", json:");
    ((StringBuilder)localObject).append(paramJSONObject);
    LogUtils.d("jsWiFi", ((StringBuilder)localObject).toString());
    localObject = new JSONObject();
    if (a(paramString1)) {
      try
      {
        LogUtils.d("jsWiFi", "[getWifiList] enter try:");
        int i = paramJSONObject.optInt("type");
        paramString1 = new StringBuilder();
        paramString1.append("[getWifiList] type:");
        paramString1.append(i);
        LogUtils.d("jsWiFi", paramString1.toString());
        paramJSONObject = a();
        paramString1 = paramJSONObject;
        if (paramJSONObject == null) {
          paramString1 = new JSONArray();
        }
        ((JSONObject)localObject).put("wifiList", paramString1);
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF106");
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-GETWIFILIST-0");
      }
      catch (JSONException paramString1)
      {
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("[getWifiList] JSONException e1:");
        paramJSONObject.append(paramString1.toString());
        LogUtils.d("jsWiFi", paramJSONObject.toString());
        paramString1.printStackTrace();
        try
        {
          ((JSONObject)localObject).put("wifiList", new JSONArray());
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-GETWIFILIST-1");
        }
        catch (JSONException paramString1)
        {
          paramString1.printStackTrace();
        }
      }
    } else {
      try
      {
        ((JSONObject)localObject).put("wifiList", new JSONArray());
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-GETWIFILIST-1");
      }
      catch (JSONException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString2, (JSONObject)localObject);
    TBSStatManager.getInstance().userBehaviorStatistics("AWTWF103");
    return ((JSONObject)localObject).toString();
  }
  
  private String c(String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[scanWifiList] action:");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", callbackId:");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(", json:");
    ((StringBuilder)localObject).append(paramJSONObject);
    LogUtils.d("jsWiFi", ((StringBuilder)localObject).toString());
    localObject = new JSONObject();
    if (a(paramString1)) {
      try
      {
        LogUtils.d("jsWiFi", "[scanWifiList] enter try:");
        int i = paramJSONObject.optInt("type");
        paramString1 = new StringBuilder();
        paramString1.append("[scanWifiList] type:");
        paramString1.append(i);
        LogUtils.d("jsWiFi", paramString1.toString());
        WifiEngine.getInstance().addWifiDataChangedListener(this);
        WifiEngine.getInstance().scanWifi(true);
        ((JSONObject)localObject).put("resultCode", 0);
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-SCANWIFILIST-0");
      }
      catch (JSONException paramString1)
      {
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("[scanWifiList] JSONException e1:");
        paramJSONObject.append(paramString1.toString());
        LogUtils.d("jsWiFi", paramJSONObject.toString());
        paramString1.printStackTrace();
        try
        {
          ((JSONObject)localObject).put("resultCode", 1);
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-SCANWIFILIST-1");
        }
        catch (JSONException paramString1)
        {
          paramString1.printStackTrace();
        }
      }
    } else {
      try
      {
        ((JSONObject)localObject).put("resultCode", 1);
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-SCANWIFILIST-1");
      }
      catch (JSONException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString2, (JSONObject)localObject);
    TBSStatManager.getInstance().userBehaviorStatistics("AWTWF103");
    return ((JSONObject)localObject).toString();
  }
  
  private String d(String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[detectNetwork] action:");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", callbackId:");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(", json:");
    ((StringBuilder)localObject).append(paramJSONObject);
    LogUtils.d("jsWiFi", ((StringBuilder)localObject).toString());
    localObject = new JSONObject();
    if (a(paramString1)) {}
    for (;;)
    {
      try
      {
        LogUtils.d("jsWiFi", "[detectNetwork] enter try:");
        int i = paramJSONObject.optInt("ignoreCache", 0);
        paramString1 = new StringBuilder();
        paramString1.append("[detectNetwork] ignoreCache:");
        paramString1.append(i);
        LogUtils.d("jsWiFi", paramString1.toString());
        WifiNetworkDetectorFacade.getInstance().addDetectListener(this);
        paramString1 = WifiNetworkDetectorFacade.getInstance();
        if (i != 1) {
          break label292;
        }
        bool = true;
        paramString1.detect(bool);
        ((JSONObject)localObject).put("resultCode", 0);
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-DETECTNETWORK-0");
      }
      catch (JSONException paramString1)
      {
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("[detectNetwork] JSONException e1:");
        paramJSONObject.append(paramString1.toString());
        LogUtils.d("jsWiFi", paramJSONObject.toString());
        paramString1.printStackTrace();
        try
        {
          ((JSONObject)localObject).put("resultCode", 1);
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-DETECTNETWORK-1");
        }
        catch (JSONException paramString1)
        {
          paramString1.printStackTrace();
        }
      }
      try
      {
        ((JSONObject)localObject).put("resultCode", 1);
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-DETECTNETWORK-1");
      }
      catch (JSONException paramString1)
      {
        paramString1.printStackTrace();
      }
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString2, (JSONObject)localObject);
      return ((JSONObject)localObject).toString();
      label292:
      boolean bool = false;
    }
  }
  
  private String e(String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[getCurWiFiInfo] action:");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", callbackId:");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(", json:");
    ((StringBuilder)localObject).append(paramJSONObject);
    LogUtils.d("jsWiFi", ((StringBuilder)localObject).toString());
    paramJSONObject = new JSONObject();
    if (a(paramString1)) {
      try
      {
        LogUtils.d("jsWiFi", "[getCurWiFiInfo] enter try:");
        paramString1 = new JSONObject();
        localObject = WifiEngine.getInstance().getCurApInfo();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("[getCurWiFiInfo] apInfo:");
        localStringBuilder.append(localObject);
        LogUtils.d("jsWiFi", localStringBuilder.toString());
        if (localObject != null)
        {
          paramString1.put("mActionType", ((WifiApInfo)localObject).mActionType);
          paramString1.put("mCanSharePwd", ((WifiApInfo)localObject).mCanSharePwd);
          paramString1.put("mConnectFrom", ((WifiApInfo)localObject).mConnectFrom);
          paramString1.put("mConnectType", ((WifiApInfo)localObject).mConnectType);
          paramString1.put("mForceNewConfig", ((WifiApInfo)localObject).mForceNewConfig);
          paramString1.put("mFrequency", ((WifiApInfo)localObject).mFrequency);
          paramString1.put("mHasCloudPassword", ((WifiApInfo)localObject).mHasCloudPassword);
          paramString1.put("mIsSavedWifi", ((WifiApInfo)localObject).mIsSavedWifi);
          paramString1.put("mLevel", ((WifiApInfo)localObject).mLevel);
          paramString1.put("mReportState", ((WifiApInfo)localObject).mReportState);
          paramString1.put("mSafeType", ((WifiApInfo)localObject).mSafeType);
          paramString1.put("mScore", ((WifiApInfo)localObject).mScore);
          paramString1.put("mSdkFlag", ((WifiApInfo)localObject).mSdkFlag);
          paramString1.put("mTargetConfigId", ((WifiApInfo)localObject).mTargetConfigId);
          paramString1.put("mTargetSdk", ((WifiApInfo)localObject).mTargetSdk);
          paramString1.put("mUserInputPwd", ((WifiApInfo)localObject).mUserInputPwd);
          paramString1.put("mWiFiType", ((WifiApInfo)localObject).mWiFiType);
          paramString1.put("mBrand", ((WifiApInfo)localObject).mBrand);
          paramString1.put("mBssid", ((WifiApInfo)localObject).mBssid);
          paramString1.put("mIdentity", ((WifiApInfo)localObject).mIdentity);
          paramString1.put("mPassword", ((WifiApInfo)localObject).mPassword);
          paramString1.put("mPortal", ((WifiApInfo)localObject).mPortal);
          paramString1.put("mSsid", ((WifiApInfo)localObject).mSsid);
          paramString1.put("mWeixinSchama", ((WifiApInfo)localObject).mWeixinSchama);
        }
        paramJSONObject.put("currentWifiInfo", paramString1);
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-GETCURWIFIINFO-0");
      }
      catch (JSONException paramString1)
      {
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("[getCurWiFiInfo] JSONException e1:");
        ((StringBuilder)localObject).append(paramString1.toString());
        LogUtils.d("jsWiFi", ((StringBuilder)localObject).toString());
        paramString1.printStackTrace();
        try
        {
          paramJSONObject.put("currentWifiInfo", new JSONObject());
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-GETCURWIFIINFO-1");
        }
        catch (JSONException paramString1)
        {
          paramString1.printStackTrace();
        }
      }
    } else {
      try
      {
        paramJSONObject.put("currentWifiInfo", new JSONObject());
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-GETCURWIFIINFO-1");
      }
      catch (JSONException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString2, paramJSONObject);
    return paramJSONObject.toString();
  }
  
  private String f(String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[getWifiState] action:");
    localStringBuilder.append(paramString1);
    localStringBuilder.append(", callbackId:");
    localStringBuilder.append(paramString2);
    localStringBuilder.append(", json:");
    localStringBuilder.append(paramJSONObject);
    LogUtils.d("jsWiFi", localStringBuilder.toString());
    paramJSONObject = new JSONObject();
    if (a(paramString1)) {
      try
      {
        LogUtils.d("jsWiFi", "[getWifiState] enter try:");
        int i = WifiEngine.getInstance().getWifiState();
        paramString1 = new StringBuilder();
        paramString1.append("[getWifiState] wifiState:");
        paramString1.append(i);
        LogUtils.d("jsWiFi", paramString1.toString());
        paramJSONObject.put("wifiState", i);
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-GETWIFISTATE-0");
      }
      catch (JSONException paramString1)
      {
        localStringBuilder = new StringBuilder();
        localStringBuilder.append("[getWifiState] JSONException e1:");
        localStringBuilder.append(paramString1.toString());
        LogUtils.d("jsWiFi", localStringBuilder.toString());
        paramString1.printStackTrace();
        try
        {
          paramJSONObject.put("wifiState", 4);
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-GETWIFISTATE-1");
        }
        catch (JSONException paramString1)
        {
          paramString1.printStackTrace();
        }
      }
    } else {
      try
      {
        paramJSONObject.put("wifiState", 4);
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-GETWIFISTATE-1");
      }
      catch (JSONException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString2, paramJSONObject);
    return paramJSONObject.toString();
  }
  
  private String g(String paramString1, String paramString2, final JSONObject paramJSONObject)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[cancelConnectAp] action:");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", callbackId:");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(", json:");
    ((StringBuilder)localObject).append(paramJSONObject);
    LogUtils.d("jsWiFi", ((StringBuilder)localObject).toString());
    localObject = new JSONObject();
    if (a(paramString1)) {
      try
      {
        LogUtils.d("jsWiFi", "[cancelConnectAp] enter try:");
        paramString1 = "";
        if (paramJSONObject != null) {
          paramString1 = paramJSONObject.optString("mSsid", "");
        }
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("[cancelConnectAp] ssid:");
        paramJSONObject.append(paramString1);
        LogUtils.d("jsWiFi", paramJSONObject.toString());
        if (!TextUtils.isEmpty(paramString1))
        {
          paramJSONObject = new WifiApInfo();
          paramJSONObject.mSsid = paramString1;
          BrowserExecutorSupplier.postForBackgroundTasks(new BrowserExecutorSupplier.BackgroundRunable()
          {
            public void doRun()
            {
              WifiEngine.getInstance().cancelConnectAp(paramJSONObject, true);
            }
          });
          ((JSONObject)localObject).put("resultCode", 0);
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-CANCELCONNECTAP-0");
        }
        else
        {
          ((JSONObject)localObject).put("resultCode", 1);
        }
      }
      catch (JSONException paramString1)
      {
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("[cancelConnectAp] JSONException e1:");
        paramJSONObject.append(paramString1.toString());
        LogUtils.d("jsWiFi", paramJSONObject.toString());
        paramString1.printStackTrace();
        try
        {
          ((JSONObject)localObject).put("resultCode", 1);
          TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-CANCELCONNECTAP-1");
        }
        catch (JSONException paramString1)
        {
          paramString1.printStackTrace();
        }
      }
    } else {
      try
      {
        ((JSONObject)localObject).put("resultCode", 1);
        TBSStatManager.getInstance().userBehaviorStatistics("AWNWF50_D-TBS-WIFI-JSAPI-CANCELCONNECTAP-1");
      }
      catch (JSONException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString2, (JSONObject)localObject);
    TBSStatManager.getInstance().userBehaviorStatistics("AWTWF118");
    return ((JSONObject)localObject).toString();
  }
  
  private String h(String paramString1, String paramString2, JSONObject paramJSONObject)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[getSysAbility] action:");
    ((StringBuilder)localObject).append(paramString1);
    ((StringBuilder)localObject).append(", callbackId:");
    ((StringBuilder)localObject).append(paramString2);
    ((StringBuilder)localObject).append(", json:");
    ((StringBuilder)localObject).append(paramJSONObject);
    LogUtils.d("jsWiFi", ((StringBuilder)localObject).toString());
    localObject = new JSONObject();
    if (a(paramString1)) {
      try
      {
        LogUtils.d("jsWiFi", "[getSysAbility] enter try:");
        if (paramJSONObject == null) {
          break label308;
        }
        int i = paramJSONObject.optInt("checkOption");
        paramString1 = new StringBuilder();
        paramString1.append("[getSysAbility] checkOption:");
        paramString1.append(i);
        LogUtils.d("jsWiFi", paramString1.toString());
        if ((i & 0x1) != 0) {
          ((JSONObject)localObject).put("locationEnable", WifiUtils.isLocationProviderEnabled());
        }
        if ((i & 0x2) != 0) {
          ((JSONObject)localObject).put("hasLocationPermission", PermissionUtils.hasPermission(this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.getActivityCx(), "android.permission.ACCESS_COARSE_LOCATION"));
        }
        if ((i & 0x4) != 0)
        {
          ((JSONObject)localObject).put("deviceSdkVersion", Build.VERSION.SDK_INT);
          ((JSONObject)localObject).put("targetSdkVersion", ContextHolder.getAppContext().getApplicationInfo().targetSdkVersion);
        }
        ((JSONObject)localObject).put("resultCode", 0);
      }
      catch (JSONException paramString1)
      {
        paramJSONObject = new StringBuilder();
        paramJSONObject.append("[getSysAbility] JSONException e1:");
        paramJSONObject.append(paramString1.toString());
        LogUtils.d("jsWiFi", paramJSONObject.toString());
        paramString1.printStackTrace();
        try
        {
          ((JSONObject)localObject).put("resultCode", 1);
        }
        catch (JSONException paramString1)
        {
          paramString1.printStackTrace();
        }
      }
    } else {
      try
      {
        ((JSONObject)localObject).put("resultCode", 1);
      }
      catch (JSONException paramString1)
      {
        paramString1.printStackTrace();
      }
    }
    label308:
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.sendSuccJsCallback(paramString2, (JSONObject)localObject);
    return ((JSONObject)localObject).toString();
  }
  
  protected boolean a(String paramString)
  {
    return true;
  }
  
  public void destroy() {}
  
  public String exec(String paramString1, String paramString2, JSONObject paramJSONObject, String paramString3)
  {
    this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.setOriginUrl(paramString3);
    paramString3 = new StringBuilder();
    paramString3.append("[exec] action:");
    paramString3.append(paramString1);
    paramString3.append(", callbackId:");
    paramString3.append(paramString2);
    LogUtils.d("jsWiFi", paramString3.toString());
    try
    {
      paramString3 = new StringBuilder();
      paramString3.append("[exec] action:");
      paramString3.append(paramString1);
      paramString3.append(", callbackId:");
      paramString3.append(paramString2);
      paramString3.append(", argsJson:");
      paramString3.append(paramJSONObject);
      LogUtils.d("jsWiFi", paramString3.toString());
      if ("connectAp".equals(paramString1)) {
        return a(paramString1, paramString2, paramJSONObject);
      }
      if ("getWifiList".equals(paramString1)) {
        return b(paramString1, paramString2, paramJSONObject);
      }
      if ("scanWifiList".equals(paramString1)) {
        return c(paramString1, paramString2, paramJSONObject);
      }
      if ("detectNetwork".equals(paramString1)) {
        return d(paramString1, paramString2, paramJSONObject);
      }
      if ("getCurWiFiInfo".equals(paramString1)) {
        return e(paramString1, paramString2, paramJSONObject);
      }
      if ("getWifiState".equals(paramString1)) {
        return f(paramString1, paramString2, paramJSONObject);
      }
      if ("cancelConnectAp".equals(paramString1)) {
        return g(paramString1, paramString2, paramJSONObject);
      }
      if ("getSysAbility".equals(paramString1))
      {
        paramString1 = h(paramString1, paramString2, paramJSONObject);
        return paramString1;
      }
    }
    catch (Throwable paramString1)
    {
      paramString1.printStackTrace();
    }
    return null;
  }
  
  public void onCloudPwdRecievd()
  {
    Object localObject = a();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("[onCloudPwdRecievd] jsonArray:");
    localStringBuilder.append(localObject);
    LogUtils.d("jsWiFi", localStringBuilder.toString());
    if ((localObject != null) && (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper != null))
    {
      localObject = ((JSONArray)localObject).toString();
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("[onCloudPwdRecievd] strResultData:");
      localStringBuilder.append((String)localObject);
      LogUtils.d("jsWiFi", localStringBuilder.toString());
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.callJs("onWiFiListResult", new Object[] { localObject });
    }
  }
  
  public void onDetectStatusChanged(String paramString, int paramInt1, int paramInt2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[onDetectStatusChanged] ssid:");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(", status:");
    ((StringBuilder)localObject).append(paramInt1);
    ((StringBuilder)localObject).append(", lastStatus:");
    ((StringBuilder)localObject).append(paramInt2);
    LogUtils.d("jsWiFi", ((StringBuilder)localObject).toString());
    localObject = new JSONObject();
    try
    {
      ((JSONObject)localObject).put("ssid", paramString);
      ((JSONObject)localObject).put("status", paramInt1);
      ((JSONObject)localObject).put("lastStatus", paramInt2);
    }
    catch (JSONException paramString)
    {
      paramString.printStackTrace();
    }
    paramString = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    if (paramString != null) {
      paramString.callJs("onDetectStatusChanged", new Object[] { ((JSONObject)localObject).toString() });
    }
  }
  
  public void onListDataChanged(ArrayList<WifiApInfo> paramArrayList)
  {
    LogUtils.d("jsWiFi", "[onListDataChanged] :");
    if (paramArrayList != null)
    {
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("[onListDataChanged] scanResult size:");
      ((StringBuilder)localObject1).append(paramArrayList.size());
      LogUtils.d("jsWiFi", ((StringBuilder)localObject1).toString());
      if (paramArrayList.size() > 0)
      {
        try
        {
          localObject1 = new JSONArray();
          paramArrayList = paramArrayList.iterator();
          while (paramArrayList.hasNext())
          {
            WifiApInfo localWifiApInfo = (WifiApInfo)paramArrayList.next();
            Object localObject2 = new StringBuilder();
            ((StringBuilder)localObject2).append("[onListDataChanged] apInfo:");
            ((StringBuilder)localObject2).append(localWifiApInfo);
            LogUtils.d("jsWiFi", ((StringBuilder)localObject2).toString());
            localObject2 = new JSONObject();
            ((JSONObject)localObject2).put("mActionType", localWifiApInfo.mActionType);
            ((JSONObject)localObject2).put("mCanSharePwd", localWifiApInfo.mCanSharePwd);
            ((JSONObject)localObject2).put("mConnectFrom", localWifiApInfo.mConnectFrom);
            ((JSONObject)localObject2).put("mConnectType", localWifiApInfo.mConnectType);
            ((JSONObject)localObject2).put("mForceNewConfig", localWifiApInfo.mForceNewConfig);
            ((JSONObject)localObject2).put("mFrequency", localWifiApInfo.mFrequency);
            ((JSONObject)localObject2).put("mHasCloudPassword", localWifiApInfo.mHasCloudPassword);
            ((JSONObject)localObject2).put("mIsSavedWifi", localWifiApInfo.mIsSavedWifi);
            ((JSONObject)localObject2).put("mLevel", localWifiApInfo.mLevel);
            ((JSONObject)localObject2).put("mReportState", localWifiApInfo.mReportState);
            ((JSONObject)localObject2).put("mSafeType", localWifiApInfo.mSafeType);
            ((JSONObject)localObject2).put("mScore", localWifiApInfo.mScore);
            ((JSONObject)localObject2).put("mSdkFlag", localWifiApInfo.mSdkFlag);
            ((JSONObject)localObject2).put("mTargetConfigId", localWifiApInfo.mTargetConfigId);
            ((JSONObject)localObject2).put("mTargetSdk", localWifiApInfo.mTargetSdk);
            ((JSONObject)localObject2).put("mUserInputPwd", localWifiApInfo.mUserInputPwd);
            ((JSONObject)localObject2).put("mWiFiType", localWifiApInfo.mWiFiType);
            ((JSONObject)localObject2).put("mBrand", localWifiApInfo.mBrand);
            ((JSONObject)localObject2).put("mBssid", localWifiApInfo.mBssid);
            ((JSONObject)localObject2).put("mIdentity", localWifiApInfo.mIdentity);
            ((JSONObject)localObject2).put("mPassword", localWifiApInfo.mPassword);
            ((JSONObject)localObject2).put("mPortal", localWifiApInfo.mPortal);
            ((JSONObject)localObject2).put("mSsid", localWifiApInfo.mSsid);
            ((JSONObject)localObject2).put("mWeixinSchama", localWifiApInfo.mWeixinSchama);
            ((JSONArray)localObject1).put(localObject2);
          }
          if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper != null)
          {
            paramArrayList = ((JSONArray)localObject1).toString();
            localObject1 = new StringBuilder();
            ((StringBuilder)localObject1).append("[onListDataChanged] strResultData:");
            ((StringBuilder)localObject1).append(paramArrayList);
            LogUtils.d("jsWiFi", ((StringBuilder)localObject1).toString());
            this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.callJs("onWiFiListResult", new Object[] { paramArrayList });
          }
        }
        catch (JSONException paramArrayList)
        {
          paramArrayList.printStackTrace();
        }
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF106");
      }
    }
  }
  
  public void onStateChange(WiFiConnectStateMachine.WiFiConnectState paramWiFiConnectState)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[onStateChange] state:");
    ((StringBuilder)localObject).append(paramWiFiConnectState.getIntStateType());
    ((StringBuilder)localObject).append(", getName:");
    ((StringBuilder)localObject).append(paramWiFiConnectState.getName());
    ((StringBuilder)localObject).append(", getSsid:");
    ((StringBuilder)localObject).append(paramWiFiConnectState.getSsid());
    LogUtils.d("jsWiFi", ((StringBuilder)localObject).toString());
    localObject = "";
    int i;
    switch (paramWiFiConnectState.getIntStateType())
    {
    default: 
      i = 0;
      break;
    case 3: 
    case 6: 
      localObject = paramWiFiConnectState.getName();
      if (!this.jdField_a_of_type_Boolean) {
        TBSStatManager.getInstance().userBehaviorStatistics("AWTWF110");
      }
      if (WiFiConnectStateMachine.getInstance().getDisconnectReason() == 1) {
        i = 1;
      } else {
        i = 0;
      }
      this.jdField_a_of_type_Boolean = false;
      break;
    case 2: 
      this.jdField_a_of_type_Boolean = true;
      localObject = paramWiFiConnectState.getName();
      TBSStatManager.getInstance().userBehaviorStatistics("AWTWF111");
      i = 0;
      break;
    case 1: 
    case 5: 
      localObject = paramWiFiConnectState.getName();
      i = 0;
      break;
    case 0: 
    case 4: 
      localObject = paramWiFiConnectState.getName();
      i = 0;
    }
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("ssid", paramWiFiConnectState.getSsid());
      localJSONObject.put("state", paramWiFiConnectState.getIntStateType());
      localJSONObject.put("stateString", localObject);
      localJSONObject.put("errorCode", i);
    }
    catch (JSONException paramWiFiConnectState)
    {
      paramWiFiConnectState.printStackTrace();
    }
    if (this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper != null)
    {
      paramWiFiConnectState = new StringBuilder();
      paramWiFiConnectState.append("[onStateChange] params:");
      paramWiFiConnectState.append(localJSONObject.toString());
      LogUtils.d("jsWiFi", paramWiFiConnectState.toString());
      this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper.callJs("onWiFiConnectStateChange", new Object[] { localJSONObject.toString() });
    }
  }
  
  public void onWifiStateChanged()
  {
    int i = WifiEngine.getInstance().getWifiState();
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("[onWifiStateChanged] wifiState:");
    ((StringBuilder)localObject).append(i);
    LogUtils.d("jsWiFi", ((StringBuilder)localObject).toString());
    localObject = new JSONObject();
    try
    {
      ((JSONObject)localObject).put("wifiState", i);
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    OpenJsHelper localOpenJsHelper = this.jdField_a_of_type_ComTencentSmttJsApiImplOpenJsHelper;
    if (localOpenJsHelper != null) {
      localOpenJsHelper.callJs("onWiFiStateChanged", new Object[] { ((JSONObject)localObject).toString() });
    }
  }
  
  public void setJsApiImpl(Object paramObject) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\jsApi\impl\jsWiFi.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */