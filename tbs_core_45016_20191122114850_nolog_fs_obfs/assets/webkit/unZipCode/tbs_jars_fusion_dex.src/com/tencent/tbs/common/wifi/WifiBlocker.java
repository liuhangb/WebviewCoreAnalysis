package com.tencent.tbs.common.wifi;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class WifiBlocker
{
  private static final String SHARE_PREFS_NAME = "block_records";
  private static final String SHARE_PREFS_NAME_SDK = "sdk_block_records";
  private static final String TAG = "WifiBlocker";
  private static WifiBlocker sInstance;
  private String SDK_PREFER_KEY_TIME_SUF = "_time";
  private Map<String, Long> mMemCache;
  private SharedPreferences mPreference;
  private SharedPreferences mSDKPreference;
  private Map<String, Long> mSdkMemCache;
  
  public static WifiBlocker getInstance()
  {
    try
    {
      if (sInstance == null) {
        sInstance = new WifiBlocker();
      }
      WifiBlocker localWifiBlocker = sInstance;
      return localWifiBlocker;
    }
    finally {}
  }
  
  private boolean isSameDay(long paramLong1, long paramLong2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTimeInMillis(paramLong1);
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.setTimeInMillis(paramLong2);
    if (localCalendar1.get(1) != localCalendar2.get(1)) {
      return false;
    }
    if (localCalendar1.get(2) != localCalendar2.get(2)) {
      return false;
    }
    return localCalendar1.get(5) == localCalendar2.get(5);
  }
  
  private void loadPref2MemIfNeed()
  {
    if (this.mMemCache != null) {
      return;
    }
    SharedPreferences localSharedPreferences = getPreference();
    Object localObject = localSharedPreferences.getAll();
    if (localObject != null)
    {
      if (((Map)localObject).isEmpty()) {
        return;
      }
      this.mMemCache = new HashMap(((Map)localObject).size());
      localObject = ((Map)localObject).keySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((Iterator)localObject).next();
        try
        {
          long l = localSharedPreferences.getLong(str, 0L);
          this.mMemCache.put(str, Long.valueOf(l));
        }
        catch (ClassCastException localClassCastException)
        {
          localClassCastException.printStackTrace();
        }
      }
      return;
    }
  }
  
  private void loadSdkPref2MemIfNeed()
  {
    if (this.mSdkMemCache != null) {
      return;
    }
    SharedPreferences localSharedPreferences = getSDKPreference();
    Object localObject = localSharedPreferences.getAll();
    if (localObject != null)
    {
      if (((Map)localObject).isEmpty()) {
        return;
      }
      this.mSdkMemCache = new HashMap(((Map)localObject).size());
      localObject = ((Map)localObject).keySet().iterator();
      while (((Iterator)localObject).hasNext())
      {
        String str = (String)((Iterator)localObject).next();
        try
        {
          long l = localSharedPreferences.getLong(str, 0L);
          this.mSdkMemCache.put(str, Long.valueOf(l));
        }
        catch (ClassCastException localClassCastException)
        {
          localClassCastException.printStackTrace();
        }
      }
      return;
    }
  }
  
  public void block(String paramString, long paramLong)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("block[");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("][");
    ((StringBuilder)localObject).append(paramLong);
    ((StringBuilder)localObject).append("]");
    LogUtils.d("WifiBlocker", ((StringBuilder)localObject).toString());
    if (!WifiUtils.isSsidValid(paramString)) {
      return;
    }
    long l = System.currentTimeMillis();
    loadSdkPref2MemIfNeed();
    if (this.mSdkMemCache == null) {
      this.mSdkMemCache = new HashMap();
    }
    this.mSdkMemCache.put(paramString, Long.valueOf(paramLong));
    localObject = this.mSdkMemCache;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramString);
    localStringBuilder.append(this.SDK_PREFER_KEY_TIME_SUF);
    ((Map)localObject).put(localStringBuilder.toString(), Long.valueOf(l));
    try
    {
      localObject = getSDKPreference().edit().putLong(paramString, paramLong);
      localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString);
      localStringBuilder.append(this.SDK_PREFER_KEY_TIME_SUF);
      ((SharedPreferences.Editor)localObject).putLong(localStringBuilder.toString(), l).apply();
      return;
    }
    catch (Throwable paramString) {}
  }
  
  public void block(String paramString, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("block() ssid: ");
    localStringBuilder.append(paramString);
    LogUtils.d("WifiBlocker", localStringBuilder.toString());
    if (!WifiUtils.isSsidValid(paramString)) {
      return;
    }
    long l = System.currentTimeMillis();
    loadPref2MemIfNeed();
    if (this.mMemCache == null) {
      this.mMemCache = new HashMap(1);
    }
    this.mMemCache.put(paramString, Long.valueOf(l));
    try
    {
      getPreference().edit().putLong(paramString, l).apply();
      return;
    }
    catch (Throwable paramString) {}
  }
  
  public boolean checkBlocked(String paramString, long paramLong)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("checkBlocked()[");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("][");
    ((StringBuilder)localObject).append(paramLong);
    ((StringBuilder)localObject).append("]");
    LogUtils.d("WifiBlocker", ((StringBuilder)localObject).toString());
    loadSdkPref2MemIfNeed();
    localObject = this.mSdkMemCache;
    if (localObject != null)
    {
      if (((Map)localObject).isEmpty()) {
        return false;
      }
      if (this.mSdkMemCache.keySet().contains(paramString))
      {
        localObject = (Long)this.mSdkMemCache.get(paramString);
        if (localObject == null) {
          return false;
        }
        if (((Long)localObject).longValue() == paramLong)
        {
          paramLong = System.currentTimeMillis();
          localObject = this.mSdkMemCache;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append(paramString);
          localStringBuilder.append(this.SDK_PREFER_KEY_TIME_SUF);
          localObject = (Long)((Map)localObject).get(localStringBuilder.toString());
          if (localObject == null) {
            return false;
          }
          if (isSameDay(paramLong, ((Long)localObject).longValue()))
          {
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("checkBlocked() ssid: ");
            ((StringBuilder)localObject).append(paramString);
            ((StringBuilder)localObject).append(", true");
            LogUtils.d("WifiBlocker", ((StringBuilder)localObject).toString());
            return true;
          }
        }
        else
        {
          return false;
        }
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("checkBlocked() ssid: ");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(", false");
      LogUtils.d("WifiBlocker", ((StringBuilder)localObject).toString());
      return false;
    }
    return false;
  }
  
  public SharedPreferences getPreference()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getAppContext().getSharedPreferences("block_records", 0);
    }
    return this.mPreference;
  }
  
  public SharedPreferences getSDKPreference()
  {
    if (this.mSDKPreference == null) {
      this.mSDKPreference = ContextHolder.getAppContext().getSharedPreferences("sdk_block_records", 0);
    }
    return this.mSDKPreference;
  }
  
  public boolean isBlocked(String paramString)
  {
    loadPref2MemIfNeed();
    Object localObject = this.mMemCache;
    if (localObject != null)
    {
      if (((Map)localObject).isEmpty()) {
        return false;
      }
      if (this.mMemCache.keySet().contains(paramString))
      {
        long l = System.currentTimeMillis();
        localObject = (Long)this.mMemCache.get(paramString);
        if (localObject == null) {
          return false;
        }
        if (isSameDay(l, ((Long)localObject).longValue()))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("isBlocked() ssid: ");
          ((StringBuilder)localObject).append(paramString);
          ((StringBuilder)localObject).append(", true");
          LogUtils.d("WifiBlocker", ((StringBuilder)localObject).toString());
          return true;
        }
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("isBlocked() ssid: ");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(", false");
      LogUtils.d("WifiBlocker", ((StringBuilder)localObject).toString());
      return false;
    }
    return false;
  }
  
  public boolean isBlocked(String paramString, long paramLong)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("isBlocked()[");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append("][");
    ((StringBuilder)localObject).append(paramLong);
    ((StringBuilder)localObject).append("]");
    LogUtils.d("WifiBlocker", ((StringBuilder)localObject).toString());
    loadSdkPref2MemIfNeed();
    localObject = this.mSdkMemCache;
    if (localObject != null)
    {
      if (((Map)localObject).isEmpty()) {
        return false;
      }
      if (this.mSdkMemCache.keySet().contains(paramString))
      {
        localObject = (Long)this.mSdkMemCache.get(paramString);
        if (localObject != null)
        {
          if ((((Long)localObject).longValue() & paramLong) == paramLong)
          {
            paramLong = System.currentTimeMillis();
            localObject = this.mSdkMemCache;
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append(paramString);
            localStringBuilder.append(this.SDK_PREFER_KEY_TIME_SUF);
            localObject = (Long)((Map)localObject).get(localStringBuilder.toString());
            if (localObject != null)
            {
              if (isSameDay(paramLong, ((Long)localObject).longValue()))
              {
                localObject = new StringBuilder();
                ((StringBuilder)localObject).append("isBlocked() ssid: ");
                ((StringBuilder)localObject).append(paramString);
                ((StringBuilder)localObject).append(", true");
                LogUtils.d("WifiBlocker", ((StringBuilder)localObject).toString());
                return true;
              }
            }
            else {
              return false;
            }
          }
          else
          {
            return false;
          }
        }
        else {
          return false;
        }
      }
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("isBlocked() ssid: ");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(", false");
      LogUtils.d("WifiBlocker", ((StringBuilder)localObject).toString());
      return false;
    }
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiBlocker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */