package com.tencent.tbs.common.beacon;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.common.wup.WUPTaskProxy;
import com.tencent.tbs.common.MTT.COMMON_ERRORCODE;
import com.tencent.tbs.common.MTT.CommonConfigReq;
import com.tencent.tbs.common.MTT.CommonConfigRsp;
import com.tencent.tbs.common.baseinfo.GUIDFactory;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.wup.WUPRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BeaconUserBehaviorManager
{
  private static final int APPID = 1005;
  private static final String CACHED_KEY_RATIO_SETTING = "x5_key_ratio_setting";
  private static final String CONFIG_ITEM_DIVIDER = ",";
  private static final String KEY_VALUE_DIVIDER = ":";
  private static final String LAST_PULL_DEVICE_TIME = "last_pull_device_time";
  private static final int RATIO_CEILING = 10000;
  private static final int RATIO_FLOOR = 0;
  public static final String TAG = "BeaconUserBehavior";
  private static final long TIME_24_HOUR = 86400000L;
  private static long lastPullTime = -1L;
  private static Map<String, Float> mKeyRatioConfigMap = null;
  private static final byte[] sBeaconLock = new byte[0];
  private static BeaconUserBehaviorManager sInstance;
  private Context mContext = null;
  
  private BeaconUserBehaviorManager(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  private void buildFromLocal()
  {
    buildRatioMap(PublicSettingManager.getInstance().getCommonConfigListContent(1005));
  }
  
  private void buildRatioMap(String paramString)
  {
    mKeyRatioConfigMap = new HashMap();
    if (isBlank(paramString)) {
      return;
    }
    paramString = trimString(paramString).split(",");
    if (paramString.length > 0)
    {
      int i = 0;
      while (i < paramString.length)
      {
        String str1 = paramString[i];
        String str2;
        float f1;
        if (!isBlank(str1))
        {
          str2 = trimString(str1);
          int j = str2.indexOf(":");
          if (j != -1)
          {
            str1 = str2.substring(0, j);
            str2 = str2.substring(j + 1);
            if ((!isBlank(str1)) && (!isBlank(str2))) {
              f1 = -1.0F;
            }
          }
        }
        try
        {
          float f2 = Float.parseFloat(str2);
          f1 = f2;
        }
        catch (Exception localException)
        {
          for (;;) {}
        }
        if (f1 > 0.0F) {
          mKeyRatioConfigMap.put(str1, Float.valueOf(f1));
        }
        i += 1;
      }
    }
    else
    {
      return;
    }
  }
  
  public static BeaconUserBehaviorManager getInstance(Context paramContext)
  {
    synchronized (sBeaconLock)
    {
      if (sInstance == null) {
        sInstance = new BeaconUserBehaviorManager(paramContext);
      }
      return sInstance;
    }
  }
  
  private boolean isBlank(String paramString)
  {
    return (paramString == null) || (paramString.replaceAll("\\s", "").length() == 0);
  }
  
  private void logConfigMapInfo()
  {
    Iterator localIterator = mKeyRatioConfigMap.keySet().iterator();
    while (localIterator.hasNext()) {
      String str = (String)localIterator.next();
    }
  }
  
  private void logErrorInfo(COMMON_ERRORCODE paramCOMMON_ERRORCODE)
  {
    if (paramCOMMON_ERRORCODE != null) {
      switch (paramCOMMON_ERRORCODE.value())
      {
      }
    }
  }
  
  private void pullConfigMapFromCloud(IWUPRequestCallBack paramIWUPRequestCallBack)
  {
    CommonConfigReq localCommonConfigReq = new CommonConfigReq();
    localCommonConfigReq.iAppId = 1005;
    localCommonConfigReq.iDBTime = PublicSettingManager.getInstance().getCommonConfigTime(1005);
    localCommonConfigReq.sContentMd5 = PublicSettingManager.getInstance().getCommonConfigMd5(1005);
    localCommonConfigReq.sGuid = GUIDFactory.getInstance().getStrGuid();
    localCommonConfigReq.sQua2 = TbsInfoUtils.getQUA2();
    localCommonConfigReq.sQua2ExInfo = "";
    WUPRequest localWUPRequest = new WUPRequest("CMD_DOMAIN_WHITE_LIST", "getConfig");
    localWUPRequest.setClassLoader(getClass().getClassLoader());
    localWUPRequest.put("req", localCommonConfigReq);
    localWUPRequest.setRequestCallBack(paramIWUPRequestCallBack);
    WUPTaskProxy.send(localWUPRequest);
  }
  
  private void pullUserBehaviorUploadRatio()
  {
    lastPullTime = System.currentTimeMillis();
    pullConfigMapFromCloud(new IWUPRequestCallBack()
    {
      public void onWUPTaskFail(WUPRequestBase paramAnonymousWUPRequestBase) {}
      
      public void onWUPTaskSuccess(WUPRequestBase paramAnonymousWUPRequestBase, WUPResponseBase paramAnonymousWUPResponseBase)
      {
        if (paramAnonymousWUPResponseBase == null) {
          return;
        }
        paramAnonymousWUPRequestBase = paramAnonymousWUPResponseBase.getResponseData("rsp");
        if ((paramAnonymousWUPRequestBase != null) && ((paramAnonymousWUPRequestBase instanceof CommonConfigRsp)))
        {
          paramAnonymousWUPRequestBase = (CommonConfigRsp)paramAnonymousWUPRequestBase;
          if (paramAnonymousWUPRequestBase.iRet == 0)
          {
            PublicSettingManager.getInstance().setCommonConfigTime(1005, paramAnonymousWUPRequestBase.iDBTime);
            PublicSettingManager.getInstance().setCommonConfigMd5(1005, paramAnonymousWUPRequestBase.sContentMd5);
            paramAnonymousWUPResponseBase = BeaconUserBehaviorManager.this.mContext;
            int i = 0;
            if (paramAnonymousWUPResponseBase != null)
            {
              paramAnonymousWUPResponseBase = BeaconUserBehaviorManager.this.mContext.getSharedPreferences("x5_key_ratio_setting", 0).edit();
              paramAnonymousWUPResponseBase.putLong("last_pull_device_time", BeaconUserBehaviorManager.lastPullTime);
              paramAnonymousWUPResponseBase.commit();
            }
            if ((paramAnonymousWUPRequestBase.vConfig != null) && (paramAnonymousWUPRequestBase.vConfig.size() > 0))
            {
              paramAnonymousWUPResponseBase = new StringBuffer();
              while (i < paramAnonymousWUPRequestBase.vConfig.size())
              {
                paramAnonymousWUPResponseBase.append((String)paramAnonymousWUPRequestBase.vConfig.get(i));
                paramAnonymousWUPResponseBase.append(",");
                i += 1;
              }
              paramAnonymousWUPRequestBase = paramAnonymousWUPResponseBase.toString();
              PublicSettingManager.getInstance().setCommonConfigListContent(1005, paramAnonymousWUPRequestBase);
              BeaconUserBehaviorManager.this.buildRatioMap(paramAnonymousWUPRequestBase);
              return;
            }
            BeaconUserBehaviorManager.this.buildFromLocal();
            return;
          }
          if (paramAnonymousWUPRequestBase.iRet == 1)
          {
            BeaconUserBehaviorManager.this.buildFromLocal();
            return;
          }
          if (paramAnonymousWUPRequestBase.iRet == 2)
          {
            PublicSettingManager.getInstance().setCommonConfigTime(1005, paramAnonymousWUPRequestBase.iDBTime);
            PublicSettingManager.getInstance().setCommonConfigMd5(1005, paramAnonymousWUPRequestBase.sContentMd5);
            PublicSettingManager.getInstance().setCommonConfigListContent(1005, ",");
            BeaconUserBehaviorManager.this.buildFromLocal();
            return;
          }
          paramAnonymousWUPRequestBase = COMMON_ERRORCODE.convert(paramAnonymousWUPRequestBase.iRet);
          BeaconUserBehaviorManager.this.logErrorInfo(paramAnonymousWUPRequestBase);
        }
      }
    });
  }
  
  private String trimString(String paramString)
  {
    if (isBlank(paramString)) {
      return "";
    }
    return paramString.replaceAll("\\s", "");
  }
  
  public void pullUserBehaviorUploadRatioIfNeeded()
  {
    long l1 = System.currentTimeMillis();
    SharedPreferences localSharedPreferences = this.mContext.getSharedPreferences("x5_key_ratio_setting", 0);
    if (lastPullTime < 0L) {
      lastPullTime = localSharedPreferences.getLong("last_pull_device_time", 0L);
    }
    if ((mKeyRatioConfigMap == null) && (lastPullTime == 0L))
    {
      buildFromLocal();
      pullUserBehaviorUploadRatio();
      return;
    }
    long l2;
    if (mKeyRatioConfigMap == null)
    {
      l2 = lastPullTime;
      if (l2 > 0L)
      {
        if (l1 - l2 >= 86400000L)
        {
          buildFromLocal();
          pullUserBehaviorUploadRatio();
          return;
        }
        buildFromLocal();
        return;
      }
    }
    if ((mKeyRatioConfigMap != null) && (lastPullTime == 0L)) {
      return;
    }
    if (mKeyRatioConfigMap != null)
    {
      l2 = lastPullTime;
      if ((l2 > 0L) && (l1 - l2 >= 86400000L)) {
        pullUserBehaviorUploadRatio();
      }
    }
  }
  
  public boolean satisfyRatioControl(float paramFloat)
  {
    int i = (int)(paramFloat * 10000.0F);
    boolean bool2 = false;
    String str = GUIDFactory.getInstance().getStrGuid();
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    int j = new BigInteger(str.substring(8, 24), 16).mod(BigInteger.valueOf(10000L)).intValue();
    boolean bool1 = bool2;
    if (j >= 0)
    {
      bool1 = bool2;
      if (j <= i + 0) {
        bool1 = true;
      }
    }
    return bool1;
  }
  
  public boolean shouldUploadToBeacon(String paramString)
  {
    if ((!TextUtils.isEmpty(paramString)) && (!TextUtils.isEmpty(paramString.trim())))
    {
      if (mKeyRatioConfigMap == null)
      {
        pullUserBehaviorUploadRatioIfNeeded();
        return true;
      }
      logConfigMapInfo();
      if (!mKeyRatioConfigMap.containsKey(paramString)) {
        return true;
      }
      return satisfyRatioControl(((Float)mKeyRatioConfigMap.get(paramString)).floatValue());
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\beacon\BeaconUserBehaviorManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */