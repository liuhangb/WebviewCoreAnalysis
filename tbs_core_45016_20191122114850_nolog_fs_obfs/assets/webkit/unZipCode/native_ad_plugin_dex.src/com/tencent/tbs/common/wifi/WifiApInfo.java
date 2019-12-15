package com.tencent.tbs.common.wifi;

import android.net.wifi.ScanResult;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.MTT.ZeusDFI;
import java.util.ArrayList;

public class WifiApInfo
{
  public static final int ACTION_TYPE_COMMON = 1;
  public static final int ACTION_TYPE_DIRECT = 16;
  public static final int ACTION_TYPE_ONE_CLICK_LOGIN = 64;
  public static final int ACTION_TYPE_PORTAL = 32;
  public static final int ACTION_TYPE_WEIXIN_NORMAL = 4;
  public static final int ACTION_TYPE_WEIXIN_PSW = 8;
  public static final int ACTION_TYPE_WIWIDE = 2;
  public static final int CONNECT_FROM_LIST = 1;
  public static final int CONNECT_FROM_SHARE_BTN = 2;
  public static final int CONNECT_FROM_SHARE_HEADSUP = 3;
  public static final int CONNECT_TYPE_ADD_AP = 3;
  public static final int CONNECT_TYPE_CHECK_PWD = 2;
  public static final int CONNECT_TYPE_COMMON = 1;
  public static final int CONNECT_TYPE_USER_INPUT_PWD = 4;
  public static final int RPT_STATE_FAIL = 2;
  public static final int RPT_STATE_NONE = -1;
  public static final int RPT_STATE_REPORT = 0;
  public static final int RPT_STATE_SUCCESS = 1;
  public long mActionType = 0L;
  public String mBrand = "";
  public String mBssid = "";
  public boolean mCanSharePwd = false;
  public ArrayList<String> mCloudPassword = new ArrayList();
  boolean mConnectBySdk = false;
  public int mConnectFrom = -1;
  public int mConnectType = 1;
  public boolean mForceNewConfig = false;
  public int mFrequency = -1;
  public boolean mHasCloudPassword = false;
  public String mIdentity = "";
  public boolean mIsSavedWifi = false;
  public int mLevel = Integer.MIN_VALUE;
  public String mPassword = "";
  public String mPortal = "";
  public int mReportState = -1;
  public int mSafeType;
  public int mScore = 0;
  public int mSdkFlag = 0;
  @Deprecated
  public int mSdkId = -1;
  public String mSsid = "";
  public int mTargetConfigId = -1;
  public int mTargetSdk = -1;
  public boolean mUserInputPwd = false;
  public String mWeixinSchama;
  public int mWiFiType = 0;
  
  public WifiApInfo() {}
  
  public WifiApInfo(ScanResult paramScanResult)
  {
    if (paramScanResult != null)
    {
      this.mBssid = paramScanResult.BSSID;
      this.mSsid = paramScanResult.SSID;
      this.mSafeType = WifiUtils.getSecurity(paramScanResult);
      this.mLevel = paramScanResult.level;
      this.mFrequency = paramScanResult.frequency;
    }
  }
  
  public WifiApInfo(ZeusDFI paramZeusDFI)
  {
    if (paramZeusDFI != null)
    {
      this.mBssid = paramZeusDFI.b;
      this.mSsid = paramZeusDFI.s;
      this.mSafeType = paramZeusDFI.t;
      this.mTargetSdk = ((int)paramZeusDFI.f);
      this.mHasCloudPassword = true;
    }
  }
  
  public WifiApInfo clone()
  {
    WifiApInfo localWifiApInfo = new WifiApInfo();
    localWifiApInfo.mSdkId = this.mSdkId;
    localWifiApInfo.mBssid = this.mBssid;
    localWifiApInfo.mSsid = this.mSsid;
    localWifiApInfo.mLevel = this.mLevel;
    localWifiApInfo.mSafeType = this.mSafeType;
    localWifiApInfo.mHasCloudPassword = this.mHasCloudPassword;
    localWifiApInfo.mIsSavedWifi = this.mIsSavedWifi;
    localWifiApInfo.mFrequency = this.mFrequency;
    localWifiApInfo.mPassword = this.mPassword;
    localWifiApInfo.mIdentity = this.mIdentity;
    localWifiApInfo.mCloudPassword = this.mCloudPassword;
    localWifiApInfo.mUserInputPwd = this.mUserInputPwd;
    localWifiApInfo.mActionType = this.mActionType;
    localWifiApInfo.mBrand = this.mBrand;
    localWifiApInfo.mPortal = this.mPortal;
    localWifiApInfo.mTargetSdk = this.mTargetSdk;
    localWifiApInfo.mCanSharePwd = this.mCanSharePwd;
    localWifiApInfo.mForceNewConfig = this.mForceNewConfig;
    localWifiApInfo.mConnectType = this.mConnectType;
    localWifiApInfo.mConnectFrom = this.mConnectFrom;
    localWifiApInfo.mWeixinSchama = this.mWeixinSchama;
    localWifiApInfo.mScore = this.mScore;
    localWifiApInfo.mSdkFlag = this.mSdkFlag;
    localWifiApInfo.mWiFiType = this.mWiFiType;
    return localWifiApInfo;
  }
  
  public boolean equals(WifiApInfo paramWifiApInfo)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramWifiApInfo.toString());
    localStringBuilder.append("|||||");
    localStringBuilder.append(toString());
    localStringBuilder.append("||||");
    boolean bool;
    if ((this.mSsid.equals(paramWifiApInfo.mSsid)) && (WifiUtils.getSignalIntensity(this.mLevel) == WifiUtils.getSignalIntensity(paramWifiApInfo.mLevel))) {
      bool = true;
    } else {
      bool = false;
    }
    localStringBuilder.append(bool);
    LogUtils.d("WifiHeadPanel", localStringBuilder.toString());
    return (this.mSsid.equals(paramWifiApInfo.mSsid)) && (WifiUtils.getSignalIntensity(this.mLevel) == WifiUtils.getSignalIntensity(paramWifiApInfo.mLevel));
  }
  
  public boolean isSame(WifiApInfo paramWifiApInfo)
  {
    return (this.mBssid.equalsIgnoreCase(paramWifiApInfo.mBssid)) && (this.mSsid.equals(paramWifiApInfo.mSsid)) && (this.mSafeType == paramWifiApInfo.mSafeType);
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("WifiApInfo: ");
    localStringBuilder.append("mSsid: ");
    localStringBuilder.append(this.mSsid);
    localStringBuilder.append(", mLevel: ");
    localStringBuilder.append(this.mLevel);
    localStringBuilder.append(", mSafeType: ");
    localStringBuilder.append(this.mSafeType);
    localStringBuilder.append(", mConState: ");
    localStringBuilder.append(", mHasPassword: ");
    localStringBuilder.append(this.mHasCloudPassword);
    localStringBuilder.append(", mSdkId: ");
    localStringBuilder.append(this.mSdkId);
    return localStringBuilder.toString();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiApInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */