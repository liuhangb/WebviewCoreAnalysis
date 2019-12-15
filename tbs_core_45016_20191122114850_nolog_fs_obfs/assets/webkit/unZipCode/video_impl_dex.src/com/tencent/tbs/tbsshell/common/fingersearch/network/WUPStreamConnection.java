package com.tencent.tbs.tbsshell.common.fingersearch.network;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.text.TextUtils;
import com.taf.UniPacket;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.StringUtils;
import com.tencent.tbs.common.baseinfo.TbsUserInfo;
import com.tencent.tbs.common.utils.QBUrlUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class WUPStreamConnection
  extends StreamConnection
  implements IWUPConnection, PacketObserver, StreamConnectionListener
{
  private static final String DEFAULT_SERVER = "wup.imtt.qq.com:14000";
  private static final String TAG = "WupStreamConnection";
  public static final String[] mFailLogShort = new String[0];
  private static final String[] mFailLogType = { "Network is unreachable", "Connection refused", "No route to host", "Connection reset by peer", "Host is unresolved", "Transport endpoint is not connected", "The operation timed out", "Permission denied", "Connection timed out" };
  public static int serverIndex;
  private static ArrayList<String> serverList;
  private final int MAX_RETRY_COUNT = this.singleRetryCount * 5;
  private Runnable mConnectRunnable = null;
  public int mFailedCount = 0;
  public byte mFromWhere = 0;
  private Handler mHandler = null;
  private HandlerThread mHandlerThread = null;
  public boolean mIsCanceled = false;
  private boolean mIsNeedPenndingAll = true;
  private boolean mIsReq = false;
  private WUPConnectionListener mListener = null;
  private int mMaxRetryCount = this.MAX_RETRY_COUNT;
  private ArrayList<Packet> mPenndingPacket = null;
  public int mSingleFailedCount = 0;
  public int mStartServer = 0;
  public int singleRetryCount = 1;
  
  public WUPStreamConnection()
  {
    if (serverList == null)
    {
      serverList = TbsUserInfo.getInstance().getSocketServerList();
      if (serverList == null) {
        serverList = new ArrayList();
      }
      serverList.add("wup.imtt.qq.com:14000");
    }
    this.mMaxRetryCount = Math.min(this.MAX_RETRY_COUNT, serverList.size() * this.singleRetryCount);
    Object localObject1 = getServer();
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("WUPStreamConnection serverIndex:");
    ((StringBuilder)localObject2).append((String)localObject1);
    LogUtils.d("WupStreamConnection", ((StringBuilder)localObject2).toString());
    this.mStartServer = serverIndex;
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append("WUPStreamConnection mStartServer:");
    ((StringBuilder)localObject2).append(this.mStartServer);
    LogUtils.d("WupStreamConnection", ((StringBuilder)localObject2).toString());
    localObject2 = getServerHost((String)localObject1);
    int i = getServerPort((String)localObject1);
    localObject1 = new StringBuilder();
    ((StringBuilder)localObject1).append("WUPStreamConnection port:");
    ((StringBuilder)localObject1).append(i);
    LogUtils.d("WupStreamConnection", ((StringBuilder)localObject1).toString());
    localObject1 = new ConnectionConfiguration((String)localObject2, i, 4);
    ((ConnectionConfiguration)localObject1).setConnectTimeout(10000);
    ((ConnectionConfiguration)localObject1).setReconnectTimes(this.singleRetryCount);
    setConfiguration((ConnectionConfiguration)localObject1);
    setConnectionListener(this);
    setCustomNeedHeadLen(true);
    addObserver(this);
    this.mPenndingPacket = new ArrayList();
  }
  
  private void decodeData(byte[] paramArrayOfByte)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("decodeData: ");
    ((StringBuilder)localObject).append(paramArrayOfByte);
    ((StringBuilder)localObject).append(" length = ");
    ((StringBuilder)localObject).append(paramArrayOfByte.length);
    LogUtils.d("WupStreamConnection", ((StringBuilder)localObject).toString());
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length >= 1))
    {
      localObject = new UniPacket();
      try
      {
        ((UniPacket)localObject).setEncodeName("UTF-8");
        ((UniPacket)localObject).decode(paramArrayOfByte);
        notifyReceiveData((UniPacket)localObject);
        return;
      }
      catch (Exception paramArrayOfByte)
      {
        paramArrayOfByte.printStackTrace();
        return;
      }
    }
    notifyReceiveError();
  }
  
  private boolean getSendDataStatus()
  {
    return this.mIsReq;
  }
  
  private String getServerHost(String paramString)
  {
    if (StringUtils.isEmpty(paramString)) {
      return null;
    }
    paramString = paramString.split(":");
    if ((paramString != null) && (paramString.length > 0)) {
      return paramString[0];
    }
    return null;
  }
  
  private int getServerPort(String paramString)
  {
    if (StringUtils.isEmpty(paramString)) {
      return -1;
    }
    paramString = paramString.split(":");
    if ((paramString != null) && (paramString.length > 1)) {
      try
      {
        int i = Integer.parseInt(paramString[1]);
        return i;
      }
      catch (NumberFormatException paramString)
      {
        paramString.printStackTrace();
        return -1;
      }
    }
    return -1;
  }
  
  public static String getStaticNetworkException(Throwable paramThrowable)
  {
    if (paramThrowable == null) {
      return null;
    }
    int i = 0;
    try
    {
      while (i < mFailLogType.length)
      {
        String str = mFailLogType[i];
        if (paramThrowable.getMessage().toLowerCase().contains(str.toLowerCase()))
        {
          paramThrowable = mFailLogShort[i];
          return paramThrowable;
        }
        i += 1;
      }
      return null;
    }
    catch (Exception paramThrowable) {}
    return null;
  }
  
  private void setSendDataStatus(boolean paramBoolean)
  {
    this.mIsReq = paramBoolean;
  }
  
  private void switchServer()
  {
    Object localObject = serverList;
    int i;
    if (localObject != null) {
      i = ((ArrayList)localObject).size();
    } else {
      i = 0;
    }
    serverIndex += 1;
    if (serverIndex >= i) {
      serverIndex = 0;
    }
    localObject = new StringBuilder();
    ((StringBuilder)localObject).append("switchServer: ");
    ((StringBuilder)localObject).append(serverIndex);
    LogUtils.d("WupStreamConnection", ((StringBuilder)localObject).toString());
  }
  
  private void updateConfing()
  {
    Object localObject = getServer();
    String str = getServerHost((String)localObject);
    int i = getServerPort((String)localObject);
    localObject = getConfiguration();
    ((ConnectionConfiguration)localObject).setHost(str);
    ((ConnectionConfiguration)localObject).setPort(i);
    setConfiguration((ConnectionConfiguration)localObject);
  }
  
  public void cancel()
  {
    this.mIsCanceled = true;
    HandlerThread localHandlerThread = this.mHandlerThread;
    if (localHandlerThread != null)
    {
      localHandlerThread.getLooper().quit();
      this.mHandlerThread = null;
    }
    this.mListener = null;
    setConnectionListener(null);
  }
  
  public void connectServer(long paramLong)
  {
    if (isConnected()) {
      return;
    }
    if (this.mHandlerThread == null)
    {
      this.mHandlerThread = new HandlerThread("SmartBoxReConnectThread");
      this.mHandlerThread.start();
      this.mHandler = new Handler(this.mHandlerThread.getLooper());
    }
    Runnable localRunnable = this.mConnectRunnable;
    if (localRunnable != null) {
      this.mHandler.removeCallbacks(localRunnable);
    }
    this.mConnectRunnable = new Runnable()
    {
      public void run()
      {
        try
        {
          if (!WUPStreamConnection.this.isConnected())
          {
            WUPStreamConnection.this.connect();
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    };
    this.mHandler.postDelayed(this.mConnectRunnable, paramLong);
  }
  
  public void disConnectServer()
  {
    cancel();
    if (isConnected()) {
      new Thread()
      {
        public void run()
        {
          WUPStreamConnection.this.disconnect();
        }
      }.start();
    }
  }
  
  public String getServer()
  {
    Object localObject = serverList;
    if ((localObject != null) && (((ArrayList)localObject).size() != 0) && (serverIndex < serverList.size()))
    {
      String str = QBUrlUtils.resolvValidUrl((String)serverList.get(serverIndex));
      if (StringUtils.isEmpty(str)) {
        return "wup.imtt.qq.com:14000";
      }
      localObject = str;
      if (str.startsWith("http://")) {
        localObject = str.substring(7);
      }
      return (String)localObject;
    }
    return "wup.imtt.qq.com:14000";
  }
  
  public void notifyAllConnectFailure(Exception paramException)
  {
    WUPConnectionListener localWUPConnectionListener = this.mListener;
    if (localWUPConnectionListener != null) {
      localWUPConnectionListener.onAllServersFailure(paramException);
    }
  }
  
  public void notifyConnectException(Exception paramException, int paramInt)
  {
    WUPConnectionListener localWUPConnectionListener = this.mListener;
    if (localWUPConnectionListener != null) {
      localWUPConnectionListener.onConnectException(paramException, paramInt);
    }
  }
  
  public void notifyReceiveData(UniPacket paramUniPacket)
  {
    WUPConnectionListener localWUPConnectionListener = this.mListener;
    if (localWUPConnectionListener != null) {
      localWUPConnectionListener.onReceiveData(paramUniPacket);
    }
  }
  
  public void notifyReceiveError()
  {
    WUPConnectionListener localWUPConnectionListener = this.mListener;
    if (localWUPConnectionListener != null) {
      localWUPConnectionListener.onReceiveDataError();
    }
  }
  
  public void notifySingleConnectTimeout(Exception paramException, int paramInt)
  {
    WUPConnectionListener localWUPConnectionListener = this.mListener;
    if (localWUPConnectionListener != null) {
      localWUPConnectionListener.onSingleServerTimeout(paramException, paramInt);
    }
  }
  
  public void onConnectFailed(Exception paramException)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("onConnectFailed:");
    ((StringBuilder)localObject).append(serverIndex);
    LogUtils.d("WupStreamConnection", ((StringBuilder)localObject).toString());
    this.mSingleFailedCount += 1;
    this.mFailedCount += 1;
    if (this.mFailedCount >= this.mMaxRetryCount)
    {
      notifyAllConnectFailure(paramException);
      return;
    }
    if (this.mSingleFailedCount >= getConfiguration().getReconnectTimes())
    {
      notifySingleConnectTimeout(paramException, serverIndex);
      this.mSingleFailedCount = 0;
      switchServer();
    }
    notifyConnectException(paramException, serverIndex);
    if (!this.mIsCanceled)
    {
      updateConfing();
      connectServer(1000L);
    }
    try
    {
      paramException = getStaticNetworkException(paramException);
      if (!TextUtils.isEmpty(paramException))
      {
        localObject = new HashMap();
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("");
        localStringBuilder.append(getServer());
        ((HashMap)localObject).put(localStringBuilder.toString(), paramException);
        return;
      }
    }
    catch (Exception paramException)
    {
      paramException.printStackTrace();
    }
  }
  
  public void onConnectSuccess()
  {
    LogUtils.d("WupStreamConnection", "onConnectSuccess");
    synchronized (this.mPenndingPacket)
    {
      Iterator localIterator = this.mPenndingPacket.iterator();
      while (localIterator.hasNext())
      {
        Packet localPacket = (Packet)localIterator.next();
        LogUtils.d("WupStreamConnection", "sendPendingPacket");
        if (sendPacket(localPacket))
        {
          if (this.mListener != null) {
            this.mListener.onSendSuccess(true, getSendDataStatus());
          }
        }
        else if (this.mListener != null) {
          this.mListener.onSendFailure(true, getSendDataStatus());
        }
      }
      this.mPenndingPacket.clear();
      return;
    }
  }
  
  public void packetReceived(Packet paramPacket)
  {
    paramPacket = paramPacket.toByte();
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("packetReceived decodeData: ");
    localStringBuilder.append(paramPacket);
    localStringBuilder.append(" length = ");
    localStringBuilder.append(paramPacket.length);
    LogUtils.d("WupStreamConnection", localStringBuilder.toString());
    if ((paramPacket != null) && (paramPacket.length >= 1))
    {
      decodeData(paramPacket);
      return;
    }
    notifyReceiveError();
  }
  
  public void reset(int paramInt)
  {
    this.mIsCanceled = false;
    this.mFailedCount = paramInt;
  }
  
  public void sendData(UniPacket paramUniPacket, boolean paramBoolean)
  {
    paramUniPacket = new Packet(paramUniPacket.encode());
    ??? = new StringBuilder();
    ((StringBuilder)???).append("sendData isConnected = ");
    ((StringBuilder)???).append(isConnected());
    LogUtils.d("WupStreamConnection", ((StringBuilder)???).toString());
    if (isConnected())
    {
      if (sendPacket(paramUniPacket))
      {
        paramUniPacket = this.mListener;
        if (paramUniPacket != null) {
          paramUniPacket.onSendSuccess(false, paramBoolean);
        }
      }
      else
      {
        paramUniPacket = this.mListener;
        if (paramUniPacket != null) {
          paramUniPacket.onSendFailure(false, paramBoolean);
        }
      }
    }
    else
    {
      ??? = new StringBuilder();
      ((StringBuilder)???).append("sendData --> synchronized --> pending mIsNeedPenndingAll = ");
      ((StringBuilder)???).append(this.mIsNeedPenndingAll);
      LogUtils.d("WupStreamConnection", ((StringBuilder)???).toString());
      connectServer(0L);
      synchronized (this.mPenndingPacket)
      {
        if (this.mIsNeedPenndingAll)
        {
          this.mPenndingPacket.add(paramUniPacket);
        }
        else
        {
          this.mPenndingPacket.clear();
          this.mPenndingPacket.add(paramUniPacket);
          setSendDataStatus(paramBoolean);
        }
        return;
      }
    }
  }
  
  public void setFromWhere(byte paramByte)
  {
    this.mFromWhere = paramByte;
  }
  
  public void setIsNeedPenddingAll(boolean paramBoolean)
  {
    this.mIsNeedPenndingAll = paramBoolean;
  }
  
  public void setWupConnectionListener(WUPConnectionListener paramWUPConnectionListener)
  {
    this.mListener = paramWUPConnectionListener;
  }
  
  public boolean shouldReceivePacketWithLength(int paramInt)
  {
    return (paramInt > 0) && (paramInt < 10240000);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\fingersearch\network\WUPStreamConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */