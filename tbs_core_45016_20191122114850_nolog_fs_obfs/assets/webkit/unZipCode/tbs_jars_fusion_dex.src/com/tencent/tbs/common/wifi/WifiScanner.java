package com.tencent.tbs.common.wifi;

import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.ScanResult;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.ContextHolder;
import com.tencent.tbs.common.settings.PublicSettingManager;
import com.tencent.tbs.common.stat.TBSStatManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WifiScanner
  implements Handler.Callback, WifiEngine.ScanResultListener
{
  public static final String AUTHORITY = "mtt_wifi";
  public static final long BG_CHECK_MIN_INTERVAL = 300000L;
  public static final long BG_SCAN_AND_CHECK_INTERVAL = 300000L;
  public static final Uri CONTENT_QUERY_COUNT = Uri.parse("content://mtt_wifi/freewifi/query/count");
  public static final Uri CONTENT_QUERY_INFO = Uri.parse("content://mtt_wifi/freewifi/query/info");
  public static final Uri CONTENT_SCAN = Uri.parse("content://mtt_wifi/freewifi/scan");
  public static final long FG_CHECK_INTERVAL = 60000L;
  public static final long FG_SCAN_INTERVAL = 10000L;
  private static final String SHARE_PREFS_NAME = "ssid_req_records";
  private static final String TAG = "WifiScanner";
  private static WifiScanner sInstance;
  private final int MSG_BG_SCAN_AND_CHECK_REGULAR = 3;
  private final int MSG_BG_SCAN_AND_CHECK_UNLOCK = 1;
  private final int MSG_BG_SCAN_AND_CHECK_WIFI_ON = 2;
  private final int MSG_CHECK_SCAN_RESULTS = 7;
  private final int MSG_FG_CHECK_REGULAR = 5;
  private final int MSG_FG_SCAN_REGULAR = 4;
  private final int MSG_REMOVE_QB_SEEK_LISTENER = 31;
  private final int MSG_SEEK_FROM_NEW_QB_CACHE = 21;
  private final int MSG_SEEK_THRU_NEW_QB_CHECK = 22;
  private final int MSG_SEEK_THRU_NEW_QB_FORCE_NOTIFY = 23;
  private final long SEEK_THRU_NEW_QB_TIMEOUT = 2000L;
  private boolean mPendingCheck = false;
  private SharedPreferences mPreference;
  private SharedPreferences.Editor mPreferenceEditor;
  private List<SeekQbFreeWifiListener> mQbSeekListeners = new ArrayList();
  private ContentResolver mResolver;
  private boolean mResolverInited;
  public boolean mSkipCache = false;
  private Handler mWorkerHandler = null;
  
  private WifiScanner()
  {
    WifiEngine.getInstance().addScanResultListener(this);
  }
  
  public static WifiScanner getInstance()
  {
    if (sInstance == null) {
      sInstance = new WifiScanner();
    }
    return sInstance;
  }
  
  private void initResolverIfNeeded()
  {
    if (!this.mResolverInited)
    {
      this.mResolver = ContextHolder.getAppContext().getContentResolver();
      ContentResolver localContentResolver = this.mResolver;
      if (localContentResolver != null)
      {
        this.mResolverInited = true;
        localContentResolver.registerContentObserver(CONTENT_QUERY_COUNT, true, new ContentObserver(this.mWorkerHandler)
        {
          public void onChange(boolean paramAnonymousBoolean)
          {
            super.onChange(paramAnonymousBoolean);
            Object localObject = new StringBuilder();
            ((StringBuilder)localObject).append("onChange() selfChange: ");
            ((StringBuilder)localObject).append(paramAnonymousBoolean);
            LogUtils.d("WifiScanner", ((StringBuilder)localObject).toString());
            WifiScanner.this.mWorkerHandler.removeMessages(23);
            WifiScanner.this.mWorkerHandler.removeMessages(21);
            localObject = WifiScanner.this.mWorkerHandler.obtainMessage();
            ((Message)localObject).what = 21;
            ((Message)localObject).sendToTarget();
          }
        });
      }
    }
  }
  
  public void check(List<ScanResult> paramList)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("check() curTime: ");
    localStringBuilder.append(System.currentTimeMillis());
    LogUtils.d("time-WifiScanner", localStringBuilder.toString());
    if (paramList != null)
    {
      if (paramList.isEmpty()) {
        return;
      }
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("check() scanResults.size: ");
      localStringBuilder.append(paramList.size());
      LogUtils.d("WifiScanner", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("check() 前台请求 curTime: ");
      localStringBuilder.append(System.currentTimeMillis());
      LogUtils.d("time-WifiScanner", localStringBuilder.toString());
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("check2[");
      localStringBuilder.append(this.mSkipCache);
      localStringBuilder.append("]");
      LogUtils.d("LIST_STABLE", localStringBuilder.toString());
      WifiEngine.getInstance().requestCloudPwd(paramList, this.mSkipCache);
      this.mSkipCache = false;
      PublicSettingManager.getInstance().setWifiLastCheckTime(System.currentTimeMillis());
      this.mPendingCheck = false;
      return;
    }
  }
  
  public boolean getPendingCheck()
  {
    return this.mPendingCheck;
  }
  
  public SharedPreferences getPreference()
  {
    if (this.mPreference == null) {
      this.mPreference = ContextHolder.getAppContext().getSharedPreferences("ssid_req_records", 0);
    }
    return this.mPreference;
  }
  
  public SharedPreferences.Editor getPreferenceEditor()
  {
    if (this.mPreferenceEditor == null) {
      this.mPreferenceEditor = getPreference().edit();
    }
    return this.mPreferenceEditor;
  }
  
  public boolean handleMessage(Message paramMessage)
  {
    int i = paramMessage.what;
    if (i != 7)
    {
      Object localObject;
      if (i != 31)
      {
        int j = 0;
        switch (i)
        {
        default: 
          switch (i)
          {
          default: 
            return false;
          case 23: 
            if ((paramMessage.obj instanceof SeekQbFreeWifiListener))
            {
              localObject = (SeekQbFreeWifiListener)paramMessage.obj;
              if (this.mQbSeekListeners.contains(localObject))
              {
                LogUtils.d("WifiScanner", "handleMessage() MSG_SEEK_THRU_NEW_QB_FORCE_NOTIFY 该listener还未通知过");
                ((SeekQbFreeWifiListener)paramMessage.obj).onFreeWifiFound(0);
                TBSStatManager.getInstance().userBehaviorStatistics("AWTWF040");
              }
            }
            return true;
          case 22: 
            localObject = null;
            if ((paramMessage.obj instanceof SeekQbFreeWifiListener))
            {
              paramMessage = (SeekQbFreeWifiListener)paramMessage.obj;
              localObject = paramMessage;
              if (!this.mQbSeekListeners.contains(paramMessage))
              {
                localObject = new StringBuilder();
                ((StringBuilder)localObject).append("handleMessage() MSG_SEEK_THRU_NEW_QB_CHECK 保存SeekQbFreeWifiListener: ");
                ((StringBuilder)localObject).append(paramMessage);
                LogUtils.d("WifiScanner", ((StringBuilder)localObject).toString());
                this.mQbSeekListeners.add(paramMessage);
                localObject = paramMessage;
              }
            }
            initResolverIfNeeded();
            paramMessage = this.mResolver;
            if (paramMessage != null)
            {
              paramMessage = paramMessage.query(CONTENT_SCAN, null, null, null, null);
              if ((paramMessage != null) && (paramMessage.getCount() == -1))
              {
                LogUtils.d("WifiScanner", "handleMessage() qb的免费wifi功能未开启，直接通知");
                if (this.mQbSeekListeners.size() > 0)
                {
                  LogUtils.d("WifiScanner", "handleMessage() MSG_SEEK_THRU_NEW_QB_CHECK 回调mQbSeekListeners");
                  paramMessage = this.mQbSeekListeners.iterator();
                }
              }
              else
              {
                while (paramMessage.hasNext())
                {
                  localObject = (SeekQbFreeWifiListener)paramMessage.next();
                  if (localObject != null)
                  {
                    ((SeekQbFreeWifiListener)localObject).onFreeWifiFound(0);
                    continue;
                    if (localObject != null)
                    {
                      paramMessage = this.mWorkerHandler.obtainMessage(23);
                      paramMessage.obj = localObject;
                      this.mWorkerHandler.sendMessageDelayed(paramMessage, 2000L);
                    }
                  }
                }
              }
            }
            return true;
          }
          initResolverIfNeeded();
          localObject = this.mResolver;
          i = j;
          if (localObject != null)
          {
            localObject = ((ContentResolver)localObject).query(CONTENT_QUERY_COUNT, null, null, null, null);
            i = j;
            if (localObject != null)
            {
              i = ((Cursor)localObject).getCount();
              localObject = new StringBuilder();
              ((StringBuilder)localObject).append("handleMessage() MSG_SEEK_FROM_NEW_QB_CACHE() cursor.count: ");
              ((StringBuilder)localObject).append(i);
              LogUtils.d("WifiScanner", ((StringBuilder)localObject).toString());
            }
          }
          if ((paramMessage.obj instanceof SeekQbFreeWifiListener))
          {
            LogUtils.d("WifiScanner", "handleMessage() MSG_SEEK_FROM_NEW_QB_CACHE 回调指定的SeekQbFreeWifiListener");
            ((SeekQbFreeWifiListener)paramMessage.obj).onFreeWifiFound(i);
          }
          if (this.mQbSeekListeners.size() > 0)
          {
            LogUtils.d("WifiScanner", "handleMessage() MSG_SEEK_FROM_NEW_QB_CACHE 回调mQbSeekListeners");
            paramMessage = this.mQbSeekListeners.iterator();
            while (paramMessage.hasNext())
            {
              localObject = (SeekQbFreeWifiListener)paramMessage.next();
              if (localObject != null) {
                ((SeekQbFreeWifiListener)localObject).onFreeWifiFound(i);
              }
            }
          }
          return true;
        case 5: 
          this.mPendingCheck = true;
          WifiEngine.getInstance().scanWifi(false);
          this.mWorkerHandler.sendEmptyMessageDelayed(5, 60000L);
          return true;
        case 4: 
          boolean bool;
          if (paramMessage.arg1 == 1) {
            bool = true;
          } else {
            bool = false;
          }
          this.mPendingCheck = bool;
          paramMessage = new StringBuilder();
          paramMessage.append("handleMessage() MSG_FG_SCAN_REGULAR mPendingCheck: ");
          paramMessage.append(this.mPendingCheck);
          LogUtils.d("WifiScanner", paramMessage.toString());
          trigerScan(false);
          return true;
        case 3: 
          this.mPendingCheck = needReq();
          WifiEngine.getInstance().scanWifi(false);
          this.mWorkerHandler.sendEmptyMessageDelayed(3, 300000L);
          return true;
        case 2: 
          this.mPendingCheck = needReq();
          WifiEngine.getInstance().scanWifi(false);
          this.mWorkerHandler.sendEmptyMessageDelayed(3, 300000L);
          return true;
        }
        this.mPendingCheck = needReq();
        WifiEngine.getInstance().scanWifi(false);
        this.mWorkerHandler.sendEmptyMessageDelayed(3, 300000L);
        return true;
      }
      if ((paramMessage.obj instanceof SeekQbFreeWifiListener))
      {
        paramMessage = (SeekQbFreeWifiListener)paramMessage.obj;
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("handleMessage() MSG_REMOVE_QB_SEEK_LISTENER listener: ");
        ((StringBuilder)localObject).append(paramMessage);
        LogUtils.d("WifiScanner", ((StringBuilder)localObject).toString());
        if (this.mQbSeekListeners.contains(paramMessage)) {
          this.mQbSeekListeners.remove(paramMessage);
        }
      }
      return true;
    }
    if ((paramMessage.obj instanceof List)) {
      paramMessage = (List)paramMessage.obj;
    } else {
      paramMessage = WifiEngine.getInstance().getScanResults();
    }
    check(paramMessage);
    return true;
  }
  
  public boolean isBgCheckAvailable()
  {
    return needReq();
  }
  
  public boolean isSsidReqPrevented(String paramString)
  {
    if (!WifiUtils.isSsidValid(paramString)) {
      return false;
    }
    Object localObject = getPreference();
    if ((((SharedPreferences)localObject).contains(paramString)) && (System.currentTimeMillis() - ((SharedPreferences)localObject).getLong(paramString, 0L) <= 86400000L))
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("isSsidReqPrevented() ssid: ");
      ((StringBuilder)localObject).append(paramString);
      ((StringBuilder)localObject).append(", true");
      LogUtils.d("WifiScanner", ((StringBuilder)localObject).toString());
      return true;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("isSsidReqPrevented() ssid: ");
    ((StringBuilder)localObject).append(paramString);
    ((StringBuilder)localObject).append(", false");
    LogUtils.d("WifiScanner", ((StringBuilder)localObject).toString());
    return false;
  }
  
  public boolean needReq()
  {
    return needReq(300000L);
  }
  
  public boolean needReq(long paramLong)
  {
    return System.currentTimeMillis() - PublicSettingManager.getInstance().getWifiLastCheckTime() > paramLong;
  }
  
  public void onScanResultReceived(List<ScanResult> paramList, boolean paramBoolean)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("onScanResultReceived[");
    localStringBuilder.append(paramBoolean);
    localStringBuilder.append("][");
    localStringBuilder.append(this.mSkipCache);
    localStringBuilder.append("][");
    localStringBuilder.append(this.mPendingCheck);
    localStringBuilder.append("]");
    LogUtils.d("LIST_STABLE", localStringBuilder.toString());
    if (!this.mSkipCache) {
      this.mSkipCache = paramBoolean;
    }
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("onScanResultReceived() mPendingCheck: ");
    localStringBuilder.append(this.mPendingCheck);
    LogUtils.d("WifiScanner", localStringBuilder.toString());
    if ((this.mSkipCache) || (this.mPendingCheck)) {
      postCheckMsg(paramList);
    }
  }
  
  public void pauseBgScan()
  {
    LogUtils.d("WifiScanner", "pauseBgCheck()");
    this.mWorkerHandler.removeMessages(1);
    this.mWorkerHandler.removeMessages(2);
    this.mWorkerHandler.removeMessages(3);
  }
  
  public void postCheckMsg(List<ScanResult> paramList)
  {
    this.mWorkerHandler.removeMessages(7);
    Message localMessage = this.mWorkerHandler.obtainMessage();
    localMessage.what = 7;
    localMessage.obj = paramList;
    localMessage.sendToTarget();
  }
  
  public void recordSsid(String paramString)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("recordSsid() ssid: ");
    localStringBuilder.append(paramString);
    LogUtils.d("WifiScanner", localStringBuilder.toString());
    if (!WifiUtils.isSsidValid(paramString)) {
      return;
    }
    try
    {
      getPreferenceEditor().putLong(paramString, System.currentTimeMillis()).apply();
      return;
    }
    catch (Throwable paramString) {}
  }
  
  public void removeAllScanAndCheckMsg()
  {
    this.mWorkerHandler.removeMessages(1);
    this.mWorkerHandler.removeMessages(2);
    this.mWorkerHandler.removeMessages(3);
    this.mWorkerHandler.removeMessages(4);
    this.mPendingCheck = false;
  }
  
  public void removeQbSeekListener(SeekQbFreeWifiListener paramSeekQbFreeWifiListener)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("removeQbSeekListener() listener: ");
    localStringBuilder.append(paramSeekQbFreeWifiListener);
    LogUtils.d("WifiScanner", localStringBuilder.toString());
    paramSeekQbFreeWifiListener = Message.obtain(this.mWorkerHandler, 31, paramSeekQbFreeWifiListener);
    this.mWorkerHandler.sendMessageAtFrontOfQueue(paramSeekQbFreeWifiListener);
  }
  
  public void resumeBgRegularScan()
  {
    LogUtils.d("WifiScanner", "resumeBgRegularScan()");
    this.mWorkerHandler.sendEmptyMessageDelayed(3, 300000L);
  }
  
  public void scanWhenScreenOn()
  {
    LogUtils.d("WifiScanner", "scanWhenScreenOn()");
    this.mWorkerHandler.removeMessages(1);
    this.mWorkerHandler.removeMessages(3);
    this.mWorkerHandler.sendEmptyMessage(1);
  }
  
  public void scanWhenWifiOn()
  {
    this.mWorkerHandler.removeMessages(2);
    this.mWorkerHandler.removeMessages(3);
    this.mWorkerHandler.sendEmptyMessage(2);
  }
  
  public void seekThruNewQbCheck(SeekQbFreeWifiListener paramSeekQbFreeWifiListener)
  {
    this.mWorkerHandler.removeMessages(22);
    Message localMessage = this.mWorkerHandler.obtainMessage();
    localMessage.what = 22;
    localMessage.obj = paramSeekQbFreeWifiListener;
    localMessage.sendToTarget();
  }
  
  public void startFgRegularScan()
  {
    LogUtils.d("WifiScanner", "startFgRegularScan()");
    this.mWorkerHandler.removeMessages(4);
    this.mWorkerHandler.removeMessages(5);
    Message localMessage = Message.obtain(this.mWorkerHandler);
    localMessage.what = 4;
    localMessage.arg1 = 1;
    localMessage.sendToTarget();
    this.mWorkerHandler.sendEmptyMessageDelayed(5, 60000L);
  }
  
  public void stopBgScanWhenWifiOff()
  {
    pauseBgScan();
  }
  
  public void stopFgRegularScan()
  {
    LogUtils.d("WifiScanner", "stopFgRegularScan()");
    this.mWorkerHandler.removeMessages(4);
    this.mWorkerHandler.removeMessages(5);
  }
  
  public void trigerScan(boolean paramBoolean)
  {
    try
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("trigerScan[");
      localStringBuilder.append(paramBoolean);
      localStringBuilder.append("]");
      LogUtils.d("LIST_STABLE", localStringBuilder.toString());
      this.mWorkerHandler.removeMessages(4);
      this.mWorkerHandler.removeMessages(5);
      WifiEngine.getInstance().scanWifi(paramBoolean);
      this.mWorkerHandler.sendEmptyMessageDelayed(4, 10000L);
      return;
    }
    finally
    {
      localObject = finally;
      throw ((Throwable)localObject);
    }
  }
  
  public static abstract interface SeekQbFreeWifiListener
  {
    public abstract void onFreeWifiFound(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\WifiScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */