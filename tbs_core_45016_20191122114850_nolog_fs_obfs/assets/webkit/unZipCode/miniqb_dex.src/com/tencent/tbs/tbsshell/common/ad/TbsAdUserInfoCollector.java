package com.tencent.tbs.tbsshell.common.ad;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Location;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.webkit.ValueCallback;
import com.tencent.common.http.Apn;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.mtt.base.utils.DeviceUtilsF;
import com.tencent.mtt.base.utils.NetworkUtils;
import com.tencent.smtt.download.ad.b;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.lbs.LbsManager;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.tbsshell.common.x5core.PageInfoCollector;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TbsAdUserInfoCollector
{
  private static final String TAG = "TbsAdUserInfoCollector";
  private static volatile TbsAdUserInfoCollector sInstance;
  private int mGenerateTbsAdUserInfoStatus = 0;
  PageInfoCollector mPageInfo = new PageInfoCollector();
  Session mSession = new Session();
  UserEnv mUserEnv = new UserEnv();
  
  public static JSONArray List2JSONArray(List<String> paramList)
  {
    JSONArray localJSONArray = new JSONArray();
    if (paramList == null) {
      return localJSONArray;
    }
    try
    {
      paramList = paramList.iterator();
      while (paramList.hasNext()) {
        localJSONArray.put((String)paramList.next());
      }
      return localJSONArray;
    }
    catch (Exception paramList) {}
    return localJSONArray;
  }
  
  public static byte[] encrypt(byte[] paramArrayOfByte, String paramString1, String paramString2)
  {
    try
    {
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("encrypt key:");
      ((StringBuilder)localObject).append(paramString1);
      ((StringBuilder)localObject).append(" iv:");
      ((StringBuilder)localObject).append(paramString2);
      ((StringBuilder)localObject).toString();
      paramString1 = new SecretKeySpec(paramString1.getBytes("utf-8"), "AES");
      paramString2 = new IvParameterSpec(paramString2.getBytes());
      localObject = Cipher.getInstance("AES/CFB/PKCS5Padding");
      ((Cipher)localObject).init(1, paramString1, paramString2);
      paramArrayOfByte = ((Cipher)localObject).doFinal(paramArrayOfByte);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      LogUtils.d("TbsAdUserInfoCollector", Log.getStackTraceString(paramArrayOfByte));
    }
    return null;
  }
  
  private String generateIvParamter()
  {
    String str2 = "$$$$$$$$$$$$$$$$";
    if (TextUtils.isEmpty(this.mUserEnv.guid)) {
      this.mUserEnv.guid = GUIDFactory.getInstance().getStrGuid();
    }
    String str3 = this.mUserEnv.guid;
    String str1 = str2;
    if (!TextUtils.isEmpty(str3)) {
      if (str3.length() < 16) {
        str1 = str2;
      } else {
        str1 = str3;
      }
    }
    return str1.substring(str1.length() - 16, str1.length());
  }
  
  private String generateKey(String paramString)
  {
    if (TextUtils.isEmpty(this.mUserEnv.guid)) {
      this.mUserEnv.guid = GUIDFactory.getInstance().getStrGuid();
    }
    String str = this.mUserEnv.guid;
    if (TextUtils.isEmpty(str)) {
      return "mvLBiZsiTbGwrfJB";
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("guid:");
    ((StringBuilder)localObject).append(str);
    ((StringBuilder)localObject).append(" domain:");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).toString();
    localObject = paramString;
    if (TextUtils.isEmpty(paramString)) {
      localObject = this.mPageInfo.getDomain();
    }
    paramString = (String)localObject;
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      paramString = "0000000000";
    }
    localObject = new StringBuilder();
    if (str.length() >= 10)
    {
      ((StringBuilder)localObject).append(str.substring(0, 10));
    }
    else
    {
      ((StringBuilder)localObject).append(str);
      ((StringBuilder)localObject).append("0000000000".substring(0, 10 - ((StringBuilder)localObject).length()));
    }
    if (paramString.length() >= 6)
    {
      ((StringBuilder)localObject).append(paramString.substring(0, 6));
    }
    else
    {
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append("0000000000".substring(0, 16 - ((StringBuilder)localObject).length()));
    }
    return ((StringBuilder)localObject).toString();
  }
  
  public static TbsAdUserInfoCollector getInstance()
  {
    if (sInstance == null) {
      try
      {
        if (sInstance == null) {
          sInstance = new TbsAdUserInfoCollector();
        }
      }
      finally {}
    }
    return sInstance;
  }
  
  private void superDebug(String paramString1, String paramString2) {}
  
  public void generateTbsAdUserInfo(IX5WebView paramIX5WebView, Point paramPoint1, Point paramPoint2)
  {
    if (paramPoint1 != null) {
      this.mPageInfo.setAdLocaton(paramPoint1);
    }
    if (paramPoint2 != null) {
      this.mPageInfo.setWebViewSize(paramPoint2);
    }
    if (paramIX5WebView == null) {
      return;
    }
    if (this.mGenerateTbsAdUserInfoStatus >= 1) {
      return;
    }
    this.mGenerateTbsAdUserInfoStatus = 1;
    updateFiled();
    updateGeolocation();
    this.mPageInfo.startCollectorAsync(paramIX5WebView, new ValueCallback()
    {
      public void onReceiveValue(Integer paramAnonymousInteger)
      {
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("startCollectorAsync ret:");
        localStringBuilder.append(paramAnonymousInteger);
        localStringBuilder.toString();
        TbsAdUserInfoCollector.access$102(TbsAdUserInfoCollector.this, 2);
      }
    });
    BrowserExecutorSupplier.forBackgroundTasks().execute(new Runnable()
    {
      public void run()
      {
        JSONArray localJSONArray = b.a().a(ContextHolder.getAppContext(), "", null, 10);
        int j = 0;
        Object localObject;
        int i;
        if (localJSONArray != null)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("getDownloadedAppInfos ");
          ((StringBuilder)localObject).append(localJSONArray.length());
          ((StringBuilder)localObject).toString();
          i = 0;
          while (i < localJSONArray.length())
          {
            localObject = localJSONArray.optJSONObject(i).optString("packageName", null);
            if (!TbsAdUserInfoCollector.this.mUserEnv.mDownloadAppList.contains(localObject)) {
              TbsAdUserInfoCollector.this.mUserEnv.mDownloadAppList.add(localObject);
            }
            i += 1;
          }
        }
        localJSONArray = b.a().a(ContextHolder.getAppContext(), "", 10);
        if (localJSONArray != null)
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("getInstalledAppInfo ");
          ((StringBuilder)localObject).append(localJSONArray.length());
          ((StringBuilder)localObject).toString();
          i = j;
          while (i < localJSONArray.length())
          {
            localObject = localJSONArray.optJSONObject(i).optString("packageName", null);
            if (!TbsAdUserInfoCollector.this.mUserEnv.mInstallAppList.contains(localObject)) {
              TbsAdUserInfoCollector.this.mUserEnv.mInstallAppList.add(localObject);
            }
            i += 1;
          }
        }
      }
    });
  }
  
  public String getEncryptString(String paramString)
  {
    try
    {
      String str1 = getJsonString();
      if (str1 == null) {
        return null;
      }
      superDebug("getEncryptString input:", str1);
      String str2 = generateIvParamter();
      paramString = encrypt(str1.getBytes("utf-8"), generateKey(paramString), str2);
      paramString = Base64.encodeToString(paramString, 2);
      superDebug("getEncryptString output:", paramString);
      return paramString;
    }
    catch (Exception paramString)
    {
      LogUtils.d("TbsAdUserInfoCollector", Log.getStackTraceString(paramString));
    }
    return null;
  }
  
  public JSONObject getJsonObject()
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("userinfo", this.mUserEnv.getJsonObject());
      localJSONObject.put("pageinfo", this.mPageInfo.getJsonObject());
      localJSONObject.put("session", this.mSession.getJsonObject());
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("getJsonObject:");
      localStringBuilder.append(localJSONObject.toString());
      localStringBuilder.toString();
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      for (;;) {}
    }
    return null;
  }
  
  public String getJsonString()
  {
    JSONObject localJSONObject = getJsonObject();
    if (localJSONObject == null) {
      return null;
    }
    return localJSONObject.toString();
  }
  
  public String getTbsAdUserInfo(IX5WebView paramIX5WebView, String paramString, Point paramPoint1, Point paramPoint2)
  {
    updateFiled();
    if (paramPoint1 != null) {
      this.mPageInfo.setAdLocaton(paramPoint1);
    }
    if (paramPoint2 != null) {
      this.mPageInfo.setWebViewSize(paramPoint2);
    }
    return getEncryptString(paramString);
  }
  
  public void onPageFinish(String paramString)
  {
    this.mPageInfo.setUrl(paramString);
  }
  
  public void onPageStart(String paramString)
  {
    if (this.mPageInfo.getUrl() != null) {
      this.mSession.addHistory(this.mPageInfo.getUrl());
    }
    this.mPageInfo.reset();
    this.mPageInfo.setUrl(paramString);
    this.mGenerateTbsAdUserInfoStatus = 0;
  }
  
  public void updateFiled()
  {
    this.mUserEnv.updateFiled();
    this.mPageInfo.updateFiled();
    this.mSession.updateFiled();
  }
  
  public void updateGeolocation()
  {
    for (;;)
    {
      try
      {
        if (Build.VERSION.SDK_INT <= 23) {
          return;
        }
        if (!PublicSettingManager.getInstance().isTbsAdEnableSpecialPermission()) {
          return;
        }
        Object localObject = ContextHolder.getAppContext();
        PackageManager localPackageManager = ((Context)localObject).getPackageManager();
        if ((localPackageManager.checkPermission("android.permission.ACCESS_FINE_LOCATION", ((Context)localObject).getPackageName()) == 0) && (localPackageManager.checkPermission("android.permission.ACCESS_COARSE_LOCATION", ((Context)localObject).getPackageName()) == 0))
        {
          bool = true;
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("updateGeolocation has_permission:");
          ((StringBuilder)localObject).append(bool);
          ((StringBuilder)localObject).toString();
          if (!bool) {
            return;
          }
          this.mUserEnv.updateGeolocationTask();
          return;
        }
      }
      catch (Exception localException)
      {
        return;
      }
      boolean bool = false;
    }
  }
  
  public static class Session
  {
    private static final int MAX_HISTORY_SIZE = 10;
    List<String> history = new ArrayList();
    String jumpFromSource;
    
    public void addHistory(String paramString)
    {
      if (TextUtils.isEmpty(paramString)) {
        return;
      }
      if (paramString.startsWith("file://")) {
        return;
      }
      if (this.history.contains(paramString)) {
        return;
      }
      this.history.add(paramString);
      if (this.history.size() > 10) {
        this.history.remove(0);
      }
    }
    
    public JSONObject getJsonObject()
    {
      try
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("jumpFromSource", this.jumpFromSource);
        localJSONObject.put("history", TbsAdUserInfoCollector.List2JSONArray(this.history));
        return localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        for (;;) {}
      }
      return null;
    }
    
    public void setHistory(List<String> paramList)
    {
      this.history = paramList;
    }
    
    public void setJumpFromSource(String paramString)
    {
      this.jumpFromSource = paramString;
    }
    
    public void updateFiled() {}
  }
  
  public class UserEnv
  {
    public String guid;
    public String imei;
    public String ip;
    public Location location = new Location(null);
    public ArrayList<String> mDownloadAppList = new ArrayList();
    public ArrayList<String> mInstallAppList = new ArrayList();
    private long mLastUpdateGeolocationTime;
    public String mac;
    public String nettype;
    public long time;
    
    public UserEnv()
    {
      updateFiled();
    }
    
    public JSONObject getJsonObject()
    {
      try
      {
        JSONObject localJSONObject = new JSONObject();
        localJSONObject.put("time", this.time);
        localJSONObject.put("guid", this.guid);
        localJSONObject.put("imei", this.imei);
        localJSONObject.put("location", this.location.getJsonObject());
        localJSONObject.put("ip", this.ip);
        localJSONObject.put("nettype", this.nettype);
        localJSONObject.put("mac", this.mac);
        localJSONObject.put("downloadApps", TbsAdUserInfoCollector.List2JSONArray(this.mDownloadAppList));
        localJSONObject.put("installApps", TbsAdUserInfoCollector.List2JSONArray(this.mInstallAppList));
        return localJSONObject;
      }
      catch (JSONException localJSONException)
      {
        for (;;) {}
      }
      return null;
    }
    
    public void init() {}
    
    public String toJsonString()
    {
      JSONObject localJSONObject = getJsonObject();
      if (localJSONObject == null) {
        return null;
      }
      return localJSONObject.toString();
    }
    
    public void updateFiled()
    {
      if (TextUtils.isEmpty(this.guid)) {
        this.guid = GUIDFactory.getInstance().getStrGuid();
      }
      this.nettype = Apn.getApnName(Apn.getApnTypeS());
      String str = this.nettype;
      if ((str != null) && (str.equals("Wlan"))) {
        this.nettype = "wifi";
      }
      if (PublicSettingManager.getInstance().isTbsAdEnableSpecialPermission())
      {
        this.ip = NetworkUtils.getIpAddress(ContextHolder.getAppContext());
        if (TextUtils.isEmpty(this.imei)) {
          this.imei = DeviceUtilsF.getIMEI();
        }
        this.mac = DeviceUtilsF.getMacAddressString();
      }
      this.time = (System.currentTimeMillis() / 1000L);
    }
    
    protected void updateGeolocationTask()
    {
      if (System.currentTimeMillis() - this.mLastUpdateGeolocationTime < 3600000L) {
        return;
      }
      this.mLastUpdateGeolocationTime = System.currentTimeMillis();
      LbsManager.getInstance().startGeolocationTask(null, new ValueCallback()new ValueCallback
      {
        public void onReceiveValue(Location paramAnonymousLocation)
        {
          TbsAdUserInfoCollector.UserEnv.this.location.mLongitude = paramAnonymousLocation.getLongitude();
          TbsAdUserInfoCollector.UserEnv.this.location.mLatitudes = paramAnonymousLocation.getLatitude();
        }
      }, new ValueCallback()
      {
        public void onReceiveValue(Bundle paramAnonymousBundle) {}
      }, false);
    }
    
    private class Location
    {
      public double mLatitudes = 0.0D;
      public double mLongitude = 0.0D;
      
      private Location() {}
      
      public JSONObject getJsonObject()
      {
        try
        {
          JSONObject localJSONObject = new JSONObject();
          localJSONObject.put("longitude", String.valueOf(this.mLongitude));
          localJSONObject.put("latitudes", String.valueOf(this.mLatitudes));
          return localJSONObject;
        }
        catch (JSONException localJSONException)
        {
          for (;;) {}
        }
        return null;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\ad\TbsAdUserInfoCollector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */