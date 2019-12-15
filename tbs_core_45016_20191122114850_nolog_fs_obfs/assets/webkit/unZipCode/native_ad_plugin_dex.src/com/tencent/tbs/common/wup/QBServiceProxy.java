package com.tencent.tbs.common.wup;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.push.IPushResponseCallBack;
import com.tencent.tbs.common.push.PushEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

public class QBServiceProxy
{
  public static final String ACTION_WUP = "com.tencent.com.tencent.tbs.common.MTT.ACTION_WUP";
  private static QBServiceProxy sInstance;
  private int MAX_CACHE_MSG_LEN = 4096;
  private Object mActionLock = new byte[0];
  private Context mApplicationContext = null;
  private ReentrantLock mConnectingLock = new ReentrantLock();
  private QBServiceConnection mConnection = null;
  private Object mEventLock = new byte[0];
  boolean mIsServiceConnecting = false;
  private ArrayList<Integer> mPendingActions = null;
  private HashMap<String, Map<String, String>> mPendingDebugEvents = null;
  private HashMap<String, Map<String, String>> mPendingEvents = null;
  private ArrayList<PushEvent> mPendingPushEvents = null;
  private ArrayList<String> mPendingUserBehaviors = null;
  private IQBService mQBService = null;
  private Object mUBLock = new byte[0];
  private Object mWupLock = new byte[0];
  private String mpendingReportMsg = "";
  private ArrayList<WUPRequest> mpendingRequest = null;
  
  private QBServiceProxy(Context paramContext)
  {
    this.mApplicationContext = paramContext.getApplicationContext();
    this.mpendingRequest = new ArrayList();
    this.mConnection = new QBServiceConnection(null);
  }
  
  private void bindQBService(Context paramContext)
  {
    if (this.mQBService != null) {
      return;
    }
    this.mConnectingLock.lock();
    if (this.mIsServiceConnecting)
    {
      this.mConnectingLock.unlock();
      return;
    }
    this.mConnectingLock.unlock();
    this.mConnectingLock.lock();
    try
    {
      Intent localIntent = new Intent("com.tencent.com.tencent.tbs.common.MTT.ACTION_WUP");
      paramContext.getApplicationContext().startService(localIntent);
      if (!paramContext.getApplicationContext().bindService(localIntent, this.mConnection, 1)) {
        this.mIsServiceConnecting = false;
      } else {
        this.mIsServiceConnecting = true;
      }
    }
    catch (Exception paramContext)
    {
      paramContext.printStackTrace();
      this.mIsServiceConnecting = false;
    }
    this.mConnectingLock.unlock();
  }
  
  private void checkServiceNullPointer()
  {
    if (this.mQBService != null) {
      return;
    }
    throw new RuntimeException("WupService is null, reason: 1.bind failed, 2.set this service impl failed");
  }
  
  public static QBServiceProxy getInstance(Context paramContext)
  {
    if (sInstance == null) {
      sInstance = new QBServiceProxy(paramContext);
    }
    return sInstance;
  }
  
  private void notifyPendingAction()
  {
    LogUtils.d("wup-service", "notifyPendingAction");
    if (this.mQBService == null) {
      return;
    }
    synchronized (this.mActionLock)
    {
      if ((this.mPendingActions != null) && (this.mPendingActions.size() > 0))
      {
        Iterator localIterator = this.mPendingActions.iterator();
        while (localIterator.hasNext()) {
          addUserAction(((Integer)localIterator.next()).intValue());
        }
        this.mPendingActions.clear();
        return;
      }
      return;
    }
  }
  
  private void notifyPendingEvent()
  {
    LogUtils.d("wup-service", "notifyPendingEvent");
    if (this.mQBService == null) {
      return;
    }
    synchronized (this.mEventLock)
    {
      Iterator localIterator;
      Map.Entry localEntry;
      if ((this.mPendingEvents != null) && (this.mPendingEvents.size() > 0))
      {
        localIterator = this.mPendingEvents.entrySet().iterator();
        while (localIterator.hasNext())
        {
          localEntry = (Map.Entry)localIterator.next();
          uploadToBeacon((String)localEntry.getKey(), (Map)localEntry.getValue());
        }
      }
      if (this.mPendingEvents != null) {
        this.mPendingEvents.clear();
      }
      if ((this.mPendingDebugEvents != null) && (this.mPendingDebugEvents.size() > 0))
      {
        localIterator = this.mPendingDebugEvents.entrySet().iterator();
        while (localIterator.hasNext())
        {
          localEntry = (Map.Entry)localIterator.next();
          statDebugEvent((String)localEntry.getKey(), (Map)localEntry.getValue());
        }
      }
      if (this.mPendingDebugEvents != null) {
        this.mPendingDebugEvents.clear();
      }
      return;
    }
  }
  
  private void notifyPendingUserBehavor()
  {
    LogUtils.d("wup-service", "notifyPendingUserBehavor");
    if (this.mQBService == null) {
      return;
    }
    synchronized (this.mUBLock)
    {
      if ((this.mPendingUserBehaviors != null) && (this.mPendingUserBehaviors.size() > 0))
      {
        Iterator localIterator = this.mPendingUserBehaviors.iterator();
        while (localIterator.hasNext()) {
          userBehaviorStatistics((String)localIterator.next());
        }
        this.mPendingUserBehaviors.clear();
        return;
      }
      return;
    }
  }
  
  private void notifyPendingWup()
  {
    synchronized (this.mWupLock)
    {
      Iterator localIterator = this.mpendingRequest.iterator();
      while (localIterator.hasNext())
      {
        WUPRequest localWUPRequest = (WUPRequest)localIterator.next();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("send pending: ");
        localStringBuilder.append(localWUPRequest);
        localStringBuilder.toString();
        send(localWUPRequest);
      }
      this.mpendingRequest.clear();
      return;
    }
  }
  
  public void addPenddingPushEvent(int paramInt, String paramString, IPushResponseCallBack paramIPushResponseCallBack, byte paramByte)
  {
    if (this.mPendingPushEvents == null) {
      this.mPendingPushEvents = new ArrayList();
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("send: ");
    ((StringBuilder)localObject).append(paramString);
    LogUtils.d("wup-service", ((StringBuilder)localObject).toString());
    localObject = this.mPendingPushEvents.iterator();
    while (((Iterator)localObject).hasNext())
    {
      PushEvent localPushEvent = (PushEvent)((Iterator)localObject).next();
      if ((localPushEvent.appid == paramInt) && (StringUtils.isStringEqual(localPushEvent.sessionId, paramString)))
      {
        localPushEvent.op = paramByte;
        localPushEvent.callBack = paramIPushResponseCallBack;
        return;
      }
    }
    localObject = new PushEvent();
    ((PushEvent)localObject).appid = paramInt;
    ((PushEvent)localObject).sessionId = paramString;
    ((PushEvent)localObject).callBack = paramIPushResponseCallBack;
    ((PushEvent)localObject).op = paramByte;
    this.mPendingPushEvents.add(localObject);
  }
  
  public boolean addUserAction(int paramInt)
  {
    if (this.mQBService == null)
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("pending action: ");
      ((StringBuilder)???).append(paramInt);
      LogUtils.d("wup-service", ((StringBuilder)???).toString());
      synchronized (this.mActionLock)
      {
        if (this.mPendingActions == null) {
          this.mPendingActions = new ArrayList();
        }
        this.mPendingActions.add(Integer.valueOf(paramInt));
        if (!ThreadUtils.isQQBrowserProcess(this.mApplicationContext)) {
          bindQBService(this.mApplicationContext);
        }
        return true;
      }
    }
    try
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("addUserAction action=");
      ((StringBuilder)???).append(paramInt);
      LogUtils.d("wup-service", ((StringBuilder)???).toString());
      this.mQBService.addUserAction(paramInt);
      return true;
    }
    catch (RemoteException localRemoteException)
    {
      localRemoteException.printStackTrace();
    }
    return false;
  }
  
  public void deRegisterPushCallBack(int paramInt, String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    IQBService localIQBService = this.mQBService;
    if (localIQBService == null)
    {
      addPenddingPushEvent(paramInt, paramString, null, (byte)1);
      if (!ThreadUtils.isQQBrowserProcess(this.mApplicationContext)) {
        bindQBService(this.mApplicationContext);
      }
      return;
    }
    try
    {
      localIQBService.deRegisterPushCallBack(paramInt, paramString);
      return;
    }
    catch (RemoteException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public void registerPushCallBack(int paramInt, String paramString, IPushResponseCallBack paramIPushResponseCallBack)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramIPushResponseCallBack == null) {
        return;
      }
      IQBService localIQBService = this.mQBService;
      if (localIQBService == null)
      {
        addPenddingPushEvent(paramInt, paramString, paramIPushResponseCallBack, (byte)0);
        if (!ThreadUtils.isQQBrowserProcess(this.mApplicationContext)) {
          bindQBService(this.mApplicationContext);
        }
        return;
      }
      try
      {
        localIQBService.registerPushCallBack(paramInt, paramString, paramIPushResponseCallBack);
        return;
      }
      catch (RemoteException paramString)
      {
        paramString.printStackTrace();
        return;
      }
    }
  }
  
  public boolean send(WUPRequest paramWUPRequest)
  {
    if (paramWUPRequest == null) {
      return false;
    }
    return WUPTaskProxy.send(paramWUPRequest);
  }
  
  public void setLoacalService(IQBService paramIQBService)
  {
    if (paramIQBService == null) {
      return;
    }
    this.mQBService = paramIQBService;
  }
  
  public void statBehavior(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return;
    }
    Object localObject = this.mQBService;
    if (localObject == null)
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("send wating: ");
      ((StringBuilder)localObject).append(paramString);
      LogUtils.d("wup-service", ((StringBuilder)localObject).toString());
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append(this.mpendingReportMsg);
      ((StringBuilder)localObject).append(paramString);
      this.mpendingReportMsg = ((StringBuilder)localObject).toString();
      if (this.mpendingReportMsg.length() > this.MAX_CACHE_MSG_LEN)
      {
        paramString = this.mpendingReportMsg;
        this.mpendingReportMsg = paramString.substring(paramString.length() - this.MAX_CACHE_MSG_LEN / 2);
      }
      if (!ThreadUtils.isQQBrowserProcess(this.mApplicationContext)) {
        bindQBService(this.mApplicationContext);
      }
      return;
    }
    try
    {
      ((IQBService)localObject).recordDebugAction(paramString);
      return;
    }
    catch (RemoteException paramString)
    {
      paramString.printStackTrace();
    }
  }
  
  public boolean statDebugEvent(String paramString, Map<String, String> paramMap)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramMap == null) {
        return false;
      }
      if (this.mQBService == null)
      {
        ??? = new StringBuilder();
        ((StringBuilder)???).append("pending statDebugEvent: ");
        ((StringBuilder)???).append(paramMap.toString());
        LogUtils.d("wup-service", ((StringBuilder)???).toString());
        synchronized (this.mEventLock)
        {
          if (this.mPendingDebugEvents == null) {
            this.mPendingDebugEvents = new HashMap();
          }
          this.mPendingDebugEvents.put(paramString, paramMap);
          if (!ThreadUtils.isQQBrowserProcess(this.mApplicationContext)) {
            bindQBService(this.mApplicationContext);
          }
          return true;
        }
      }
      try
      {
        ??? = new StringBuilder();
        ((StringBuilder)???).append("statDebugEvent: ");
        ((StringBuilder)???).append(paramMap.toString());
        LogUtils.d("wup-service", ((StringBuilder)???).toString());
        this.mQBService.statDebugEvent(paramString, paramMap);
        return true;
      }
      catch (RemoteException paramString)
      {
        paramString.printStackTrace();
        return false;
      }
    }
    return false;
  }
  
  public boolean uploadToBeacon(String paramString, Map<String, String> paramMap)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramMap == null) {
        return false;
      }
      ??? = this.mQBService;
      if (??? == null)
      {
        ??? = new StringBuilder();
        ((StringBuilder)???).append("pending uploadToBeacon: ");
        ((StringBuilder)???).append(paramMap.toString());
        LogUtils.d("wup-service", ((StringBuilder)???).toString());
        synchronized (this.mEventLock)
        {
          if (this.mPendingEvents == null) {
            this.mPendingEvents = new HashMap();
          }
          this.mPendingEvents.put(paramString, paramMap);
          if (!ThreadUtils.isQQBrowserProcess(this.mApplicationContext)) {
            bindQBService(this.mApplicationContext);
          }
          return true;
        }
      }
      try
      {
        ((IQBService)???).uploadToBeacon(paramString, paramMap);
        return true;
      }
      catch (RemoteException paramString)
      {
        paramString.printStackTrace();
        return false;
      }
    }
    return false;
  }
  
  public boolean userBehaviorStatistics(String paramString)
  {
    if (TextUtils.isEmpty(paramString)) {
      return false;
    }
    if (this.mQBService == null)
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("pending userBehaviorStatistics: ");
      ((StringBuilder)???).append(paramString);
      LogUtils.d("wup-service", ((StringBuilder)???).toString());
      synchronized (this.mUBLock)
      {
        if (this.mPendingUserBehaviors == null) {
          this.mPendingUserBehaviors = new ArrayList();
        }
        this.mPendingUserBehaviors.add(paramString);
        if (!ThreadUtils.isQQBrowserProcess(this.mApplicationContext)) {
          bindQBService(this.mApplicationContext);
        }
        return true;
      }
    }
    try
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("userBehaviorStatistics action=");
      ((StringBuilder)???).append(paramString);
      LogUtils.d("wup-service", ((StringBuilder)???).toString());
      this.mQBService.userBehaviorStatistics(paramString);
      return true;
    }
    catch (RemoteException paramString)
    {
      paramString.printStackTrace();
    }
    return false;
  }
  
  private class QBServiceConnection
    implements ServiceConnection
  {
    private QBServiceConnection() {}
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder) {}
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      QBServiceProxy.access$102(QBServiceProxy.this, null);
      QBServiceProxy.this.mConnectingLock.lock();
      paramComponentName = QBServiceProxy.this;
      paramComponentName.mIsServiceConnecting = false;
      paramComponentName.mConnectingLock.unlock();
    }
  }
  
  class WupRequestCallBack
    implements IWUPRequestCallBack
  {
    private IWUPRequestCallBack mRealCallback = null;
    
    WupRequestCallBack() {}
    
    public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
    {
      LogUtils.d("wup-service", "WupRequestCallBack: onWUPTaskFail");
      IWUPRequestCallBack localIWUPRequestCallBack = this.mRealCallback;
      if (localIWUPRequestCallBack != null) {
        localIWUPRequestCallBack.onWUPTaskFail(paramWUPRequestBase);
      }
    }
    
    public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
    {
      LogUtils.d("wup-service", "WupRequestCallBack: onWUPTaskSuccess");
      IWUPRequestCallBack localIWUPRequestCallBack = this.mRealCallback;
      if (localIWUPRequestCallBack != null) {
        localIWUPRequestCallBack.onWUPTaskSuccess(paramWUPRequestBase, paramWUPResponseBase);
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wup\QBServiceProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */