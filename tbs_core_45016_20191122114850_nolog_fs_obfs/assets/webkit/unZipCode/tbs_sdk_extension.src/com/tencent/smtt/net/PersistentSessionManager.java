package com.tencent.smtt.net;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.webview.chromium.tencent.TencentWebViewDatabaseAdapter;
import com.tencent.smtt.util.MttLog;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class PersistentSessionManager
{
  private static String TAG = "SESSIONRESUMPTION";
  private static boolean enableLog = false;
  private static boolean enabled = false;
  protected static final char[] hexArray = "0123456789abcdef".toCharArray();
  private static boolean initialized = false;
  private static PersistentSessionManager instance;
  private Context mAppContext = null;
  protected TencentWebViewDatabaseAdapter mDataBase = null;
  private HashMap<String, SessionInfo> mSessionInfoMap = new HashMap();
  private Vector<String> mSurpportDomains = null;
  private SyncWorkerThread mSyncWorker = null;
  
  public static void SessionLog(String paramString)
  {
    String str = TAG;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("PersistentSessionManager session: ");
    localStringBuilder.append(paramString);
    MttLog.e(str, localStringBuilder.toString());
  }
  
  public static String bytesToHex(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar1 = new char[paramArrayOfByte.length * 2];
    int i = 0;
    while (i < paramArrayOfByte.length)
    {
      int j = paramArrayOfByte[i] & 0xFF;
      int k = i * 2;
      char[] arrayOfChar2 = hexArray;
      arrayOfChar1[k] = arrayOfChar2[(j >>> 4)];
      arrayOfChar1[(k + 1)] = arrayOfChar2[(j & 0xF)];
      i += 1;
    }
    return new String(arrayOfChar1);
  }
  
  @CalledByNative
  public static PersistentSessionManager getInstance()
  {
    try
    {
      if (instance == null) {
        instance = new PersistentSessionManager();
      }
      PersistentSessionManager localPersistentSessionManager = instance;
      return localPersistentSessionManager;
    }
    finally {}
  }
  
  public static byte[] hexToBytes(String paramString)
  {
    paramString = paramString.toCharArray();
    int m = paramString.length / 2;
    byte[] arrayOfByte = new byte[m];
    int i = 0;
    while (i < m)
    {
      int j = i * 2;
      int k = Character.digit(paramString[j], 16);
      k = Character.digit(paramString[(j + 1)], 16) | k << 4;
      j = k;
      if (k > 127) {
        j = k - 256;
      }
      arrayOfByte[i] = ((byte)j);
      i += 1;
    }
    return arrayOfByte;
  }
  
  void deleteAdInfo(ArrayList<String> paramArrayList)
  {
    int i = 0;
    try
    {
      while (i < paramArrayList.size())
      {
        this.mSessionInfoMap.remove(paramArrayList.get(i));
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("mSessionInfoMap.remove host=");
        localStringBuilder.append((String)paramArrayList.get(i));
        SessionLog(localStringBuilder.toString());
        i += 1;
      }
      return;
    }
    finally
    {
      paramArrayList = finally;
      throw paramArrayList;
    }
  }
  
  @CalledByNative
  public byte[] getMasterKey(SessionInfo paramSessionInfo)
  {
    return paramSessionInfo.masterKey;
  }
  
  @CalledByNative
  public int getMasterKeyLength(SessionInfo paramSessionInfo)
  {
    return paramSessionInfo.masterKeyLength;
  }
  
  @CalledByNative
  public byte[] getSessionID(SessionInfo paramSessionInfo)
  {
    return paramSessionInfo.sessionID;
  }
  
  @CalledByNative
  public int getSessionIDLength(SessionInfo paramSessionInfo)
  {
    return paramSessionInfo.sessionIDLength;
  }
  
  @CalledByNative
  public SessionInfo getSessionInfo(String paramString)
  {
    try
    {
      boolean bool = enabled;
      if (!bool) {
        return null;
      }
      if ((paramString != null) && (paramString.length() > 0))
      {
        int i = paramString.split(":").length;
        if (i <= 1) {
          return null;
        }
        long l = System.currentTimeMillis();
        if ((this.mSessionInfoMap != null) && (this.mSessionInfoMap.size() > 0))
        {
          SessionInfo localSessionInfo = (SessionInfo)this.mSessionInfoMap.get(paramString);
          StringBuilder localStringBuilder;
          if ((localSessionInfo != null) && (localSessionInfo.sessionTicketsLifeHint > l) && (localSessionInfo.dbmode != 2))
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("getSessionInfo successfully cacheKey=");
            localStringBuilder.append(paramString);
            SessionLog(localStringBuilder.toString());
            return localSessionInfo;
          }
          if ((localSessionInfo != null) && (localSessionInfo.sessionTicketsLifeHint <= l))
          {
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("getSessionInfo expired cacheKey=");
            localStringBuilder.append(paramString);
            SessionLog(localStringBuilder.toString());
            localSessionInfo.dbmode = 2;
          }
          else
          {
            paramString = new StringBuilder();
            paramString.append("getSessionInfo si=");
            paramString.append(localSessionInfo);
            SessionLog(paramString.toString());
          }
        }
        return null;
      }
      return null;
    }
    finally {}
  }
  
  @CalledByNative
  public byte[] getSessionTickets(SessionInfo paramSessionInfo)
  {
    return paramSessionInfo.sessionTickets;
  }
  
  @CalledByNative
  public int getSessionTicketsLength(SessionInfo paramSessionInfo)
  {
    return paramSessionInfo.sessionTicketsLength;
  }
  
  @CalledByNative
  public String[] getSupportedDomains()
  {
    Vector localVector = this.mSurpportDomains;
    if ((localVector != null) && (localVector.size() != 0))
    {
      localVector = this.mSurpportDomains;
      return (String[])localVector.toArray(new String[localVector.size()]);
    }
    return null;
  }
  
  @CalledByNative
  boolean init()
  {
    if ((!initialized) && (this.mAppContext != null))
    {
      enabled = SmttServiceProxy.getInstance().isSessionPersistentEnabled();
      Object localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("isSessionPersistentEnabled=");
      ((StringBuilder)localObject1).append(enabled);
      SessionLog(((StringBuilder)localObject1).toString());
      boolean bool = enabled;
      if (!bool) {
        return bool;
      }
      if (this.mDataBase == null) {
        this.mDataBase = TencentWebViewDatabaseAdapter.getInstance(this.mAppContext);
      }
      if (this.mSurpportDomains == null) {
        this.mSurpportDomains = SmttServiceProxy.getInstance().getSessionPersistentSupportedDomains();
      }
      long l = System.currentTimeMillis();
      localObject1 = this.mDataBase.getAllSessionResult();
      Object localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append("init PersistentSessionManager rets=");
      int j = 0;
      int i;
      if (localObject1 != null) {
        i = ((List)localObject1).size();
      } else {
        i = 0;
      }
      ((StringBuilder)localObject2).append(i);
      SessionLog(((StringBuilder)localObject2).toString());
      if (localObject1 != null)
      {
        i = j;
        while (i < ((List)localObject1).size())
        {
          localObject2 = (SessionInfo)((List)localObject1).get(i);
          if (((SessionInfo)localObject2).sessionTicketsLifeHint <= l) {
            ((SessionInfo)localObject2).dbmode = 2;
          }
          this.mSessionInfoMap.put(((SessionInfo)localObject2).cacheKey, localObject2);
          if (enableLog)
          {
            StringBuilder localStringBuilder = new StringBuilder();
            localStringBuilder.append("mSessionInfoMap put= ");
            localStringBuilder.append(((SessionInfo)localObject2).cacheKey);
            SessionLog(localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("init  cacheKey=");
            localStringBuilder.append(((SessionInfo)localObject2).cacheKey);
            localStringBuilder.append(" masterKeyLength=");
            localStringBuilder.append(((SessionInfo)localObject2).masterKeyLength);
            localStringBuilder.append(" masterKey=");
            localStringBuilder.append(bytesToHex(((SessionInfo)localObject2).masterKey));
            SessionLog(localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("init  sessionIDLength=");
            localStringBuilder.append(((SessionInfo)localObject2).sessionIDLength);
            localStringBuilder.append(" sessionID=");
            localStringBuilder.append(bytesToHex(((SessionInfo)localObject2).sessionID));
            SessionLog(localStringBuilder.toString());
            localStringBuilder = new StringBuilder();
            localStringBuilder.append("init  sessionTicketsLength=");
            localStringBuilder.append(((SessionInfo)localObject2).sessionTicketsLength);
            localStringBuilder.append(" sessionTickets=");
            localStringBuilder.append(bytesToHex(((SessionInfo)localObject2).sessionTickets));
            localStringBuilder.append(" sessionTicketsLifeHint=");
            localStringBuilder.append(((SessionInfo)localObject2).sessionTicketsLifeHint);
            SessionLog(localStringBuilder.toString());
          }
          i += 1;
        }
      }
    }
    try
    {
      this.mSyncWorker = new SyncWorkerThread();
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      for (;;) {}
    }
    this.mSyncWorker = null;
    initialized = true;
    return enabled;
    return enabled;
  }
  
  @CalledByNative
  public void notifyHandShakeFailed(String paramString)
  {
    try
    {
      boolean bool = enabled;
      if (!bool) {
        return;
      }
      if ((paramString != null) && (paramString.length() > 0))
      {
        Object localObject = new StringBuilder();
        ((StringBuilder)localObject).append("notifyHandkeShakeFailed cacheKey=");
        ((StringBuilder)localObject).append(paramString);
        SessionLog(((StringBuilder)localObject).toString());
        if ((this.mSessionInfoMap != null) && (this.mSessionInfoMap.size() > 0))
        {
          localObject = (SessionInfo)this.mSessionInfoMap.get(paramString);
          if (localObject != null)
          {
            ((SessionInfo)localObject).dbmode = 2;
            localObject = new StringBuilder();
            ((StringBuilder)localObject).append("notifyHandkeShakeFailed disable cacheKey=");
            ((StringBuilder)localObject).append(paramString);
            SessionLog(((StringBuilder)localObject).toString());
          }
        }
        return;
      }
      return;
    }
    finally {}
  }
  
  @CalledByNative
  public void saveSessionInfo(String paramString, int paramInt1, byte[] paramArrayOfByte1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, byte[] paramArrayOfByte3, long paramLong, boolean paramBoolean)
  {
    try
    {
      boolean bool = enabled;
      if (!bool) {
        return;
      }
      Object localObject = new StringBuilder();
      ((StringBuilder)localObject).append("saveSessionInfo cacheKey=");
      ((StringBuilder)localObject).append(paramString);
      SessionLog(((StringBuilder)localObject).toString());
      if ((paramInt1 != 0) && (paramInt2 != 0) && (paramInt3 != 0) && (paramString != null) && (paramString.length() > 0))
      {
        localObject = paramString.split(":");
        if (localObject.length <= 1)
        {
          paramString = new StringBuilder();
          paramString.append("saveSessionInfo cacheKeyArray.length=");
          paramString.append(localObject.length);
          SessionLog(paramString.toString());
          return;
        }
        long l = System.currentTimeMillis();
        if (this.mSessionInfoMap.containsKey(paramString))
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("mSessionInfoMap contains host=");
          ((StringBuilder)localObject).append(paramString);
          SessionLog(((StringBuilder)localObject).toString());
          SessionInfo localSessionInfo = (SessionInfo)this.mSessionInfoMap.get(paramString);
          localObject = localSessionInfo;
          if (!paramBoolean)
          {
            localObject = localSessionInfo;
            if (localSessionInfo != null) {
              localSessionInfo.dbmode = 2;
            }
          }
        }
        else
        {
          if (!paramBoolean) {
            return;
          }
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("mSessionInfoMap do not contains host=");
          ((StringBuilder)localObject).append(paramString);
          SessionLog(((StringBuilder)localObject).toString());
          localObject = new SessionInfo();
          this.mSessionInfoMap.put(paramString, localObject);
        }
        if (localObject == null) {
          return;
        }
        ((SessionInfo)localObject).cacheKey = paramString;
        ((SessionInfo)localObject).masterKeyLength = paramInt1;
        ((SessionInfo)localObject).sessionIDLength = paramInt2;
        ((SessionInfo)localObject).sessionTicketsLength = paramInt3;
        ((SessionInfo)localObject).sessionTicketsLifeHint = (1000L * paramLong + l);
        ((SessionInfo)localObject).masterKey = new byte[paramInt1];
        System.arraycopy(paramArrayOfByte1, 0, ((SessionInfo)localObject).masterKey, 0, ((SessionInfo)localObject).masterKeyLength);
        ((SessionInfo)localObject).sessionID = new byte[paramInt2];
        System.arraycopy(paramArrayOfByte2, 0, ((SessionInfo)localObject).sessionID, 0, ((SessionInfo)localObject).sessionIDLength);
        ((SessionInfo)localObject).sessionTickets = new byte[paramInt3];
        System.arraycopy(paramArrayOfByte3, 0, ((SessionInfo)localObject).sessionTickets, 0, ((SessionInfo)localObject).sessionTicketsLength);
        ((SessionInfo)localObject).dbmode = 0;
        if (enableLog)
        {
          paramString = new StringBuilder();
          paramString.append("after save  saveSessionInfo cacheKey=");
          paramString.append(((SessionInfo)localObject).cacheKey);
          paramString.append(" masterKeyLength=");
          paramString.append(((SessionInfo)localObject).masterKeyLength);
          paramString.append(" masterKey=");
          paramString.append(bytesToHex(((SessionInfo)localObject).masterKey));
          SessionLog(paramString.toString());
          paramString = new StringBuilder();
          paramString.append("after save  sessionIDLength=");
          paramString.append(((SessionInfo)localObject).sessionIDLength);
          paramString.append(" sessionID=");
          paramString.append(bytesToHex(((SessionInfo)localObject).sessionID));
          SessionLog(paramString.toString());
          paramString = new StringBuilder();
          paramString.append("after save  sessionTicketsLength=");
          paramString.append(((SessionInfo)localObject).sessionTicketsLength);
          paramString.append(" sessionTickets=");
          paramString.append(bytesToHex(((SessionInfo)localObject).sessionTickets));
          paramString.append(" sessionTicketsLifeHint=");
          paramString.append(paramLong);
          paramString.append(" sessionTicketsLifeHint=");
          paramString.append(((SessionInfo)localObject).sessionTicketsLifeHint);
          SessionLog(paramString.toString());
        }
        return;
      }
      return;
    }
    finally {}
  }
  
  public boolean saveSessionInfoToDatabase(SessionInfo paramSessionInfo)
  {
    if (paramSessionInfo != null)
    {
      if (this.mDataBase == null) {
        return false;
      }
      if (paramSessionInfo.dbmode != 1)
      {
        StringBuilder localStringBuilder;
        if (paramSessionInfo.dbmode == 0)
        {
          this.mDataBase.addSessionInfo(paramSessionInfo);
          paramSessionInfo.dbmode = 1;
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("addSessionInfo si=");
          localStringBuilder.append(paramSessionInfo.cacheKey);
          SessionLog(localStringBuilder.toString());
          return false;
        }
        if (paramSessionInfo.dbmode == 2)
        {
          this.mDataBase.deleteSessionInfo(paramSessionInfo);
          localStringBuilder = new StringBuilder();
          localStringBuilder.append("deleteSessionInfo si=");
          localStringBuilder.append(paramSessionInfo.cacheKey);
          SessionLog(localStringBuilder.toString());
          return true;
        }
      }
      return false;
    }
    return false;
  }
  
  public void setContext(Context paramContext)
  {
    if (paramContext != null)
    {
      if (this.mAppContext != null) {
        return;
      }
      this.mAppContext = paramContext.getApplicationContext();
      return;
    }
  }
  
  public void syncSessionInfoFromRamToFlash()
  {
    if (!enabled) {
      return;
    }
    SyncWorkerThread localSyncWorkerThread = this.mSyncWorker;
    if (localSyncWorkerThread == null) {
      return;
    }
    localSyncWorkerThread.post(new Runnable()
    {
      public void run()
      {
        Object localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append("syncSessionInfoFromRamToFlash thread=");
        ((StringBuilder)localObject1).append(Thread.currentThread().getName());
        PersistentSessionManager.SessionLog(((StringBuilder)localObject1).toString());
        long l1 = System.currentTimeMillis();
        try
        {
          localObject1 = PersistentSessionManager.this.mSessionInfoMap.values().iterator();
          ArrayList localArrayList = new ArrayList();
          while (((Iterator)localObject1).hasNext())
          {
            SessionInfo localSessionInfo = (SessionInfo)((Iterator)localObject1).next();
            if (PersistentSessionManager.this.saveSessionInfoToDatabase(localSessionInfo)) {
              localArrayList.add(localSessionInfo.cacheKey);
            }
          }
          PersistentSessionManager.this.deleteAdInfo(localArrayList);
          long l2 = System.currentTimeMillis();
          localObject1 = new StringBuilder();
          ((StringBuilder)localObject1).append("syncSessionInfoFromRamToFlash cost=");
          ((StringBuilder)localObject1).append(l2 - l1);
          PersistentSessionManager.SessionLog(((StringBuilder)localObject1).toString());
          return;
        }
        finally {}
      }
    });
  }
  
  private class SyncWorkerThread
  {
    private Handler jdField_a_of_type_AndroidOsHandler = null;
    private Looper jdField_a_of_type_AndroidOsLooper = null;
    
    public SyncWorkerThread()
    {
      this$1 = new HandlerThread("sessionSyncworker");
      PersistentSessionManager.this.start();
      this.jdField_a_of_type_AndroidOsLooper = PersistentSessionManager.this.getLooper();
      this.jdField_a_of_type_AndroidOsHandler = new Handler(this.jdField_a_of_type_AndroidOsLooper);
    }
    
    public final boolean post(Runnable paramRunnable)
    {
      return this.jdField_a_of_type_AndroidOsHandler.post(paramRunnable);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\PersistentSessionManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */