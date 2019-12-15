package com.tencent.tbs.common.wifi.data;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextUtils;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.utils.LogUtils;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.wifi.WifiApInfo;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WifiBrandInfoHelper
  implements Handler.Callback
{
  public static final String KEY_ACTION_TYPE = "action";
  public static final String KEY_BRAND = "brand";
  public static final String KEY_PORTAL = "portal";
  public static final String KEY_SSID = "ssid";
  public static final String KEY_UPDATE_TIME = "update";
  public static final String KEY_WX_SCHAMA = "wxschema";
  public static final int MAX_CACHE_COUNT = 10;
  public static final int MSG_SAVE = 1;
  private static final String TAG = "WifiBrandInfoHelper";
  private static WifiBrandInfoHelper sInstance;
  private String mConnectedSSID = "";
  private Handler mHandler = null;
  HashMap<String, JSONObject> mInfoMap = new HashMap();
  Object mLock = new Object();
  
  private WifiBrandInfoHelper()
  {
    load();
    this.mHandler = new Handler(BrowserExecutorSupplier.getLooperForRunShortTime(), this);
    notifyDataChange();
  }
  
  public static WifiBrandInfoHelper getInstance()
  {
    if (sInstance == null) {
      try
      {
        if (sInstance == null) {
          sInstance = new WifiBrandInfoHelper();
        }
      }
      finally {}
    }
    return sInstance;
  }
  
  private HashSet<String> getSsidSet(ArrayList<WifiApInfo> paramArrayList)
  {
    HashSet localHashSet = new HashSet();
    if ((paramArrayList != null) && (!paramArrayList.isEmpty()))
    {
      paramArrayList = paramArrayList.iterator();
      while (paramArrayList.hasNext()) {
        localHashSet.add(((WifiApInfo)paramArrayList.next()).mSsid);
      }
    }
    return localHashSet;
  }
  
  private boolean isCommonType(long paramLong)
  {
    return (paramLong & 1L) != 0L;
  }
  
  private boolean isPortalWiFi(long paramLong)
  {
    return (paramLong & 0x20) != 0L;
  }
  
  private boolean needSave(WifiApInfo paramWifiApInfo, boolean paramBoolean)
  {
    boolean bool1 = true;
    boolean bool3 = true;
    boolean bool2;
    if (paramWifiApInfo != null)
    {
      if (!this.mInfoMap.containsKey(paramWifiApInfo.mSsid)) {
        return true;
      }
      JSONObject localJSONObject = (JSONObject)this.mInfoMap.get(paramWifiApInfo.mSsid);
      bool2 = bool1;
      if (localJSONObject != null)
      {
        bool2 = bool1;
        if (System.currentTimeMillis() - localJSONObject.optLong("update") < 600000L)
        {
          long l = localJSONObject.optLong("action");
          String str = localJSONObject.optString("brand");
          if (isWXType(paramWifiApInfo.mActionType))
          {
            bool1 = bool3;
          }
          else
          {
            if (isWiwideType(paramWifiApInfo.mActionType))
            {
              if (!isWXType(l))
              {
                bool1 = bool3;
                if (!isWiwideType(l)) {
                  break label196;
                }
                bool1 = paramBoolean;
                break label196;
              }
            }
            else if ((!isWXType(l)) && (!isWiwideType(l)))
            {
              bool1 = bool3;
              if (TextUtils.isEmpty(str)) {
                break label196;
              }
              paramWifiApInfo.mBrand = str;
              paramWifiApInfo.mPortal = localJSONObject.optString("portal");
              bool1 = bool3;
              break label196;
            }
            bool1 = false;
          }
          label196:
          bool2 = bool1;
          if (bool1)
          {
            bool2 = bool1;
            if (isPortalWiFi(l))
            {
              paramWifiApInfo.mActionType |= 0x20;
              return bool1;
            }
          }
        }
      }
    }
    else
    {
      bool2 = false;
    }
    return bool2;
  }
  
  public void addInfo(WifiApInfo paramWifiApInfo, boolean paramBoolean, ArrayList<WifiApInfo> paramArrayList)
  {
    LogUtils.d("WifiBrandInfoHelper", "addInfo");
    ??? = new StringBuilder();
    ((StringBuilder)???).append("fromQbServer : ");
    ((StringBuilder)???).append(paramBoolean);
    LogUtils.d("WifiBrandInfoHelper", ((StringBuilder)???).toString());
    ??? = new StringBuilder();
    ((StringBuilder)???).append("SSID : ");
    ((StringBuilder)???).append(paramWifiApInfo.mSsid);
    LogUtils.d("WifiBrandInfoHelper", ((StringBuilder)???).toString());
    ??? = new StringBuilder();
    ((StringBuilder)???).append("mBrand : ");
    ((StringBuilder)???).append(paramWifiApInfo.mBrand);
    LogUtils.d("WifiBrandInfoHelper", ((StringBuilder)???).toString());
    ??? = new StringBuilder();
    ((StringBuilder)???).append("mPortal : ");
    ((StringBuilder)???).append(paramWifiApInfo.mPortal);
    LogUtils.d("WifiBrandInfoHelper", ((StringBuilder)???).toString());
    ??? = new StringBuilder();
    ((StringBuilder)???).append("mActionType : ");
    ((StringBuilder)???).append(paramWifiApInfo.mActionType);
    LogUtils.d("WifiBrandInfoHelper", ((StringBuilder)???).toString());
    ??? = new StringBuilder();
    ((StringBuilder)???).append("mWeixinSchama : ");
    ((StringBuilder)???).append(paramWifiApInfo.mWeixinSchama);
    LogUtils.d("WifiBrandInfoHelper", ((StringBuilder)???).toString());
    synchronized (this.mLock)
    {
      if (needSave(paramWifiApInfo, paramBoolean))
      {
        Object localObject2 = new JSONObject();
        try
        {
          ((JSONObject)localObject2).put("ssid", paramWifiApInfo.mSsid);
          ((JSONObject)localObject2).put("brand", paramWifiApInfo.mBrand);
          ((JSONObject)localObject2).put("portal", paramWifiApInfo.mPortal);
          ((JSONObject)localObject2).put("action", paramWifiApInfo.mActionType);
          ((JSONObject)localObject2).put("wxschema", paramWifiApInfo.mWeixinSchama);
          ((JSONObject)localObject2).put("update", System.currentTimeMillis());
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("add : ");
          localStringBuilder.append(localObject2);
          LogUtils.d("WifiBrandInfoHelper", localStringBuilder.toString());
          this.mInfoMap.put(paramWifiApInfo.mSsid, localObject2);
          if (this.mInfoMap.size() > 10)
          {
            paramWifiApInfo = getSsidSet(paramArrayList);
            int m = this.mInfoMap.size();
            paramArrayList = new ArrayList(this.mInfoMap.values());
            Collections.sort(paramArrayList, new CacheTimeComparator());
            int j = 0;
            int i = paramArrayList.size() - 1;
            while ((j < m - 10) && (i > 0))
            {
              localObject2 = ((JSONObject)paramArrayList.get(i)).optString("ssid", "");
              int k = j;
              if (!paramWifiApInfo.contains(localObject2))
              {
                this.mInfoMap.remove(localObject2);
                k = j + 1;
              }
              i -= 1;
              j = k;
            }
          }
        }
        catch (JSONException paramWifiApInfo)
        {
          paramWifiApInfo.printStackTrace();
        }
      }
      save();
      return;
    }
  }
  
  public JSONObject get(String paramString)
  {
    try
    {
      synchronized (this.mLock)
      {
        paramString = (JSONObject)this.mInfoMap.get(paramString);
      }
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
      paramString = null;
      Object localObject1 = paramString;
      if (paramString != null)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("get : ");
        ((StringBuilder)localObject1).append(paramString);
        LogUtils.d("WifiBrandInfoHelper", ((StringBuilder)localObject1).toString());
        long l = paramString.optLong("update");
        localObject1 = paramString;
        if (System.currentTimeMillis() - l > 600000L)
        {
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("out of time  : ");
          ((StringBuilder)localObject1).append(paramString);
          LogUtils.d("WifiBrandInfoHelper", ((StringBuilder)localObject1).toString());
          localObject1 = null;
        }
      }
      return (JSONObject)localObject1;
    }
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    if (paramMessage.what != 1) {
      return true;
    }
    paramMessage = new JSONArray(this.mInfoMap.values());
    PublicSettingManager.getInstance().setWifiBrandInfo(paramMessage.toString());
    notifyDataChange();
    return true;
  }
  
  boolean isWXType(long paramLong)
  {
    return ((0x4 & paramLong) != 0L) || ((paramLong & 0x8) != 0L);
  }
  
  boolean isWiwideType(long paramLong)
  {
    return (paramLong & 0x2) != 0L;
  }
  
  public void load()
  {
    Object localObject1 = PublicSettingManager.getInstance().getWifiBrandInfo();
    ??? = new StringBuilder();
    ((StringBuilder)???).append("load : ");
    ((StringBuilder)???).append((String)localObject1);
    LogUtils.d("WifiBrandInfoHelper", ((StringBuilder)???).toString());
    JSONArray localJSONArray;
    try
    {
      synchronized (this.mLock)
      {
        localObject1 = new JSONArray((String)localObject1);
      }
      if (i >= localJSONArray.length()) {}
    }
    catch (JSONException localJSONException)
    {
      localJSONArray = new JSONArray();
      localJSONException.printStackTrace();
    }
    for (;;)
    {
      int i;
      JSONObject localJSONObject = localJSONArray.optJSONObject(i);
      if (localJSONObject != null)
      {
        String str = localJSONObject.optString("ssid");
        if (!TextUtils.isEmpty(str))
        {
          this.mInfoMap.put(str, localJSONObject);
          break label143;
          return;
          throw localJSONArray;
          i = 0;
          continue;
        }
      }
      label143:
      i += 1;
    }
  }
  
  void notifyDataChange() {}
  
  public void save()
  {
    this.mHandler.removeMessages(1);
    this.mHandler.sendEmptyMessageDelayed(1, 500L);
  }
  
  public class CacheTimeComparator
    implements Comparator<JSONObject>
  {
    public CacheTimeComparator() {}
    
    public int compare(JSONObject paramJSONObject1, JSONObject paramJSONObject2)
    {
      if (paramJSONObject1 == paramJSONObject2) {
        return 0;
      }
      if (paramJSONObject1 == null) {
        return -1;
      }
      if (paramJSONObject2 == null) {
        return 1;
      }
      long l1 = System.currentTimeMillis();
      long l2 = paramJSONObject1.optLong("update", 0L);
      if (l2 > l1) {
        return -1;
      }
      long l3 = paramJSONObject2.optLong("update", 0L);
      if (l3 > l1) {
        return 1;
      }
      return (int)(l3 - l2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\data\WifiBrandInfoHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */