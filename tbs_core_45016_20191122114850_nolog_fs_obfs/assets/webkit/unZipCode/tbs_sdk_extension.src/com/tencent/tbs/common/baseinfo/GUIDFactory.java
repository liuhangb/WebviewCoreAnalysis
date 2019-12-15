package com.tencent.tbs.common.baseinfo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import com.tencent.common.utils.ByteUtils;
import com.tencent.common.utils.DesUtils;
import com.tencent.common.utils.LogUtils;
import com.tencent.common.utils.ThreadUtils;
import com.tencent.common.wup.IWUPRequestCallBack;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPResponseBase;
import com.tencent.tbs.common.MTT.GuidReq;
import com.tencent.tbs.common.MTT.GuidRsp;
import com.tencent.tbs.common.utils.DeviceUtils;
import com.tencent.tbs.common.utils.TbsInfoUtils;
import com.tencent.tbs.common.wup.WUPRequest;
import com.tencent.tbs.common.wup.WUPTaskProxy;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class GUIDFactory
  implements IWUPRequestCallBack
{
  private static final int GUID_LENGTH = 16;
  private static final byte[] GUID_VALIDATE_KEY = { 99, -41, -112, 99, 60, 14, 47, -61, 70, -17, -123, 55, 66, 31, -99, 74, 70, 61, 88, -13, -118, -107, -20, -124 };
  private static final int MAX_REQUEST_GUID_RETRY_TIME = 3;
  public static final String TAG = "GUIDFactory";
  public static GUIDFactory mInstance;
  private byte[] mByteGUID = null;
  private Object mCallBacksLock = new byte[0];
  private Context mContext = null;
  private long mGenerateTime = 0L;
  GuidLoader mGuidLoader = null;
  GuidWorkerThread mGuidWorkerThread = null;
  boolean mHasLoadGuid = false;
  Object mHasLoadGuidLock = new byte[0];
  boolean mIsGuidValidate = false;
  private Object mLoadGuidLock = new byte[0];
  GuidRsp mQbSetGuid = null;
  private ArrayList<IRequestGuidCallBack> mRequestGuidCallBacks = null;
  private int mRequestGuidRetryTime = 0;
  private WUPRequest mRetryGUIDRequest = null;
  private String mStrGUID = null;
  private byte[] mValidation = null;
  
  private GUIDFactory()
  {
    loadGuidFromFileSetting();
  }
  
  private void fireCallbacks(byte[] paramArrayOfByte, String paramString)
  {
    synchronized (this.mCallBacksLock)
    {
      if ((this.mRequestGuidCallBacks != null) && (this.mRequestGuidCallBacks.size() > 0))
      {
        String str = ByteUtils.byteToHexString(paramArrayOfByte);
        int i = this.mRequestGuidCallBacks.size() - 1;
        while (i >= 0)
        {
          ((IRequestGuidCallBack)this.mRequestGuidCallBacks.get(i)).onGetGuid(paramArrayOfByte, str, paramString);
          this.mRequestGuidCallBacks.remove(i);
          i -= 1;
        }
        return;
      }
      LogUtils.d("GUIDFactory", "got guid, fire callback, but they are all null, ignore");
      return;
    }
  }
  
  private GuidReq getGuidReq(Context paramContext, int paramInt)
  {
    GuidReq localGuidReq = new GuidReq();
    localGuidReq.sQua = TbsInfoUtils.getQUA();
    localGuidReq.sImei = DeviceUtils.getIMEI(paramContext);
    localGuidReq.sImsi = DeviceUtils.getIMSI(paramContext);
    localGuidReq.eRequestTriggeredType = paramInt;
    localGuidReq.vValidation = this.mValidation;
    localGuidReq.sMac = ByteUtils.byteToHexString(DeviceUtils.getMacAddress(paramContext));
    localGuidReq.sAdrID = DeviceUtils.getAndroidId();
    localGuidReq.sQIMEI = DeviceUtils.getQIMEI();
    paramContext = new StringBuilder();
    paramContext.append("guidReq.sQua  :");
    paramContext.append(localGuidReq.sQua);
    LogUtils.d("GUIDFactory", paramContext.toString());
    paramContext = new StringBuilder();
    paramContext.append("guidReq.sImei  :");
    paramContext.append(localGuidReq.sImei);
    LogUtils.d("GUIDFactory", paramContext.toString());
    paramContext = new StringBuilder();
    paramContext.append("guidReq.sImsi  :");
    paramContext.append(localGuidReq.sImsi);
    LogUtils.d("GUIDFactory", paramContext.toString());
    paramContext = new StringBuilder();
    paramContext.append("guidReq.eRequestTriggeredType  :");
    paramContext.append(localGuidReq.eRequestTriggeredType);
    LogUtils.d("GUIDFactory", paramContext.toString());
    paramContext = new StringBuilder();
    paramContext.append("guidReq.sMac  :");
    paramContext.append(localGuidReq.sMac);
    LogUtils.d("GUIDFactory", paramContext.toString());
    paramContext = new StringBuilder();
    paramContext.append("guidReq.sAdrID  :");
    paramContext.append(localGuidReq.sAdrID);
    LogUtils.d("GUIDFactory", paramContext.toString());
    paramContext = new StringBuilder();
    paramContext.append("guidReq.sQIMEI  :");
    paramContext.append(localGuidReq.sQIMEI);
    LogUtils.d("GUIDFactory", paramContext.toString());
    return localGuidReq;
  }
  
  public static GUIDFactory getInstance()
  {
    if (mInstance == null) {
      mInstance = new GUIDFactory();
    }
    return mInstance;
  }
  
  private boolean isGUIDValidate(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, long paramLong)
  {
    for (;;)
    {
      synchronized (this.mLoadGuidLock)
      {
        if ((isByteArrayValidate(paramArrayOfByte1)) && (isByteArrayValidate(paramArrayOfByte2)))
        {
          i = paramArrayOfByte1.length;
          if (i == 16)
          {
            StringBuilder localStringBuilder = null;
            try
            {
              paramArrayOfByte2 = DesUtils.Des3Encrypt(GUID_VALIDATE_KEY, paramArrayOfByte2, 2);
            }
            catch (Exception paramArrayOfByte2)
            {
              paramArrayOfByte2.printStackTrace();
              paramArrayOfByte2 = localStringBuilder;
            }
            if (paramArrayOfByte2 != null)
            {
              if (paramArrayOfByte2.length != 0) {
                break label194;
              }
              continue;
              if (i < 16)
              {
                if (paramArrayOfByte1[i] == paramArrayOfByte2[i]) {
                  break label200;
                }
                localStringBuilder = new StringBuilder();
                localStringBuilder.append("guid  validation is not ok. guid[i]=");
                localStringBuilder.append(paramArrayOfByte1[i]);
                localStringBuilder.append(" validation[i]=");
                localStringBuilder.append(paramArrayOfByte2[i]);
                LogUtils.d("GUIDFactory", localStringBuilder.toString());
                return false;
              }
              return paramLong > 0L;
            }
            return false;
          }
        }
        LogUtils.d("GUIDFactory", "guid or validation is not validate");
        return false;
      }
      label194:
      int i = 0;
      continue;
      label200:
      i += 1;
    }
  }
  
  public static boolean isQQBrowser()
  {
    String str = ThreadUtils.getCurrentProcessName(TbsBaseModuleShell.getCallerContext());
    if (TextUtils.isEmpty(str)) {
      return false;
    }
    if (str.startsWith(ThreadUtils.MTT_MAIN_PROCESS_NAME)) {
      return true;
    }
    return str.startsWith("com.android.browser");
  }
  
  private void loadGuidFromFileSetting()
  {
    LogUtils.d("GUIDFactory", "load Guid From File or Setting");
    this.mGuidWorkerThread.post(new Runnable()
    {
      public void run()
      {
        synchronized (GUIDFactory.this.mLoadGuidLock)
        {
          GUIDFactory.this.mGuidLoader.loadGuid();
          GuidRsp localGuidRsp = GUIDFactory.this.mGuidLoader.getGuid();
          if (localGuidRsp != null)
          {
            GUIDFactory.access$102(GUIDFactory.this, localGuidRsp.vGuid);
            GUIDFactory.access$202(GUIDFactory.this, localGuidRsp.vValidation);
            GUIDFactory.access$302(GUIDFactory.this, localGuidRsp.lGenerateTime);
          }
          synchronized (GUIDFactory.this.mHasLoadGuidLock)
          {
            GUIDFactory.this.mHasLoadGuid = true;
            GUIDFactory.this.mHasLoadGuidLock.notifyAll();
            return;
          }
        }
      }
    });
  }
  
  private void requestGuidByValidateFail()
  {
    LogUtils.d("GUIDFactory", "requestGuidByValidateFail");
    int i = this.mRequestGuidRetryTime;
    if (i < 3)
    {
      this.mRequestGuidRetryTime = (i + 1);
      WUPRequest localWUPRequest = this.mRetryGUIDRequest;
      if (localWUPRequest != null) {
        WUPTaskProxy.send(localWUPRequest);
      }
    }
  }
  
  private int saveGuid(final GuidRsp paramGuidRsp)
  {
    this.mGuidWorkerThread.post(new Runnable()
    {
      public void run()
      {
        synchronized (GUIDFactory.this.mLoadGuidLock)
        {
          GUIDFactory.this.mGuidLoader.saveCurrenAppGuid(paramGuidRsp);
          GUIDFactory.this.mGuidLoader.updateSysSettingAndSdCard(paramGuidRsp, false);
          return;
        }
      }
    });
    return 0;
  }
  
  private void setGuid(GuidRsp paramGuidRsp)
  {
    if ((paramGuidRsp.vGuid != null) && (paramGuidRsp.vGuid.length > 0))
    {
      if (!isByteArrayValidate(paramGuidRsp.vGuid)) {
        return;
      }
      if (paramGuidRsp.vValidation != null)
      {
        if (paramGuidRsp.vValidation.length <= 0) {
          return;
        }
        LogUtils.d("GUIDFactory", "guid validata pass");
        if (saveGuid(paramGuidRsp) == 0)
        {
          this.mByteGUID = paramGuidRsp.vGuid;
          this.mValidation = paramGuidRsp.vValidation;
          this.mStrGUID = ByteUtils.byteToHexString(this.mByteGUID);
          paramGuidRsp = new StringBuilder();
          paramGuidRsp.append("guid is setted:");
          paramGuidRsp.append(this.mStrGUID);
          LogUtils.d("GUIDFactory", paramGuidRsp.toString());
        }
        return;
      }
      return;
    }
  }
  
  public byte[] getByteGuid()
  {
    isGuidValidate();
    if (this.mByteGUID == null) {
      this.mByteGUID = new byte[16];
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getByteGuid:");
    localStringBuilder.append(ByteUtils.byteToHexString(this.mByteGUID));
    LogUtils.d("GUIDFactory", localStringBuilder.toString());
    return this.mByteGUID;
  }
  
  public WUPRequest getGUIDWupRequest(int paramInt)
  {
    if ((!this.mGuidLoader.isThirdPartApp()) && (!isQQBrowser()))
    {
      WUPRequest localWUPRequest = new WUPRequest("CMD_GUID", "getGuid");
      localWUPRequest.put("req", getGuidReq(this.mContext, paramInt));
      localWUPRequest.setRequestCallBack(this);
      byte b = (byte)paramInt;
      localWUPRequest.setType(b);
      if (this.mRetryGUIDRequest == null)
      {
        this.mRetryGUIDRequest = new WUPRequest("CMD_GUID", "getGuid");
        this.mRetryGUIDRequest.setRequestCallBack(this);
        this.mRetryGUIDRequest.setType(b);
      }
      this.mRetryGUIDRequest.put("req", getGuidReq(this.mContext, 2));
      return localWUPRequest;
    }
    LogUtils.d("GUIDFactory", "I am not the main tbs host, ignore");
    return null;
  }
  
  public Bundle getGuidBundle()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("guid", ByteUtils.byteToHexString(this.mByteGUID));
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(this.mGenerateTime);
    localBundle.putString("generateTime", localStringBuilder.toString());
    localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(ByteUtils.byteToHexString(this.mValidation));
    localBundle.putString("vCode", localStringBuilder.toString());
    return localBundle;
  }
  
  public String getStrGuid()
  {
    if ((TextUtils.isEmpty(this.mStrGUID)) || (!this.mStrGUID.endsWith("88cb"))) {
      this.mStrGUID = ByteUtils.byteToHexString(getByteGuid());
    }
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("getStrGuid:");
    localStringBuilder.append(ByteUtils.byteToHexString(this.mByteGUID));
    LogUtils.d("GUIDFactory", localStringBuilder.toString());
    return this.mStrGUID;
  }
  
  public boolean isByteArrayValidate(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte != null)
    {
      if (paramArrayOfByte.length <= 0) {
        return false;
      }
      int j = paramArrayOfByte.length;
      int i = 0;
      while (i < j)
      {
        if (paramArrayOfByte[i] != 0) {
          return true;
        }
        i += 1;
      }
      return false;
    }
    return false;
  }
  
  public boolean isGuidValidate()
  {
    if ((this.mIsGuidValidate) && (this.mHasLoadGuid))
    {
      LogUtils.d("GUIDFactory", "isGuidValidate called, but has already checked, return true");
      return true;
    }
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("isGuidValidate called, now mIsGuidValidate=");
    ((StringBuilder)localObject).append(this.mIsGuidValidate);
    ((StringBuilder)localObject).append(", mHasLoadGuid=");
    ((StringBuilder)localObject).append(this.mHasLoadGuid);
    LogUtils.d("GUIDFactory", ((StringBuilder)localObject).toString());
    localObject = new FutureTask(new Callable()
    {
      public Boolean call()
        throws Exception
      {
        synchronized (GUIDFactory.this.mHasLoadGuidLock)
        {
          LogUtils.d("GUIDFactory", "check guid validate begin");
          try
          {
            while (!GUIDFactory.this.mHasLoadGuid) {
              GUIDFactory.this.mHasLoadGuidLock.wait();
            }
            StringBuilder localStringBuilder;
            boolean bool;
            localObject3 = finally;
          }
          catch (InterruptedException localInterruptedException)
          {
            localInterruptedException.printStackTrace();
            synchronized (GUIDFactory.this.mLoadGuidLock)
            {
              if (!GUIDFactory.this.mIsGuidValidate) {
                GUIDFactory.this.mIsGuidValidate = GUIDFactory.this.isGUIDValidate(GUIDFactory.this.mByteGUID, GUIDFactory.this.mValidation, GUIDFactory.this.mGenerateTime);
              }
              localStringBuilder = new StringBuilder();
              localStringBuilder.append("do check guid validate ok=");
              localStringBuilder.append(GUIDFactory.this.mIsGuidValidate);
              LogUtils.d("GUIDFactory", localStringBuilder.toString());
              bool = GUIDFactory.this.mIsGuidValidate;
              return Boolean.valueOf(bool);
            }
          }
        }
      }
    });
    this.mGuidWorkerThread.post((Runnable)localObject);
    try
    {
      LogUtils.d("GUIDFactory", "begin guid validation");
      boolean bool = ((Boolean)((FutureTask)localObject).get(4000L, TimeUnit.MILLISECONDS)).booleanValue();
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("isGuidValidate return ");
      ((StringBuilder)localObject).append(bool);
      LogUtils.d("GUIDFactory", ((StringBuilder)localObject).toString());
      return bool;
    }
    catch (TimeoutException localTimeoutException)
    {
      localTimeoutException.printStackTrace();
      return false;
    }
    catch (ExecutionException localExecutionException)
    {
      localExecutionException.printStackTrace();
      return false;
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
    }
    return false;
  }
  
  public void onGetGuidValidation(GuidRsp paramGuidRsp, byte paramByte, Object paramObject, String paramString)
  {
    if (!isGUIDValidate(paramGuidRsp.vGuid, paramGuidRsp.vValidation, paramGuidRsp.lGenerateTime))
    {
      LogUtils.d("GUIDFactory", "onGetGUIDTaskCompleted GUID is not Validate");
      requestGuidByValidateFail();
      return;
    }
    this.mRetryGUIDRequest = null;
    setGuid(paramGuidRsp);
    fireCallbacks(paramGuidRsp.vGuid, paramString);
  }
  
  public void onQbSetGuid(GuidRsp paramGuidRsp)
  {
    if ((paramGuidRsp != null) && (paramGuidRsp.vGuid != null))
    {
      if ((isGuidValidate()) && (ByteUtils.isEqual(getByteGuid(), paramGuidRsp.vGuid)))
      {
        LogUtils.d("GUIDFactory", "qb set guid, but tbs's guid is validate, ignore");
        return;
      }
      setGuid(paramGuidRsp);
      return;
    }
    LogUtils.d("GUIDFactory", "qb set guid, but guid is null, ignore");
  }
  
  public void onWUPTaskFail(WUPRequestBase paramWUPRequestBase)
  {
    LogUtils.d("GUIDFactory", "========================GUIDFatory get guid from server fail========================");
    fireCallbacks(new byte[1], "");
  }
  
  public void onWUPTaskSuccess(WUPRequestBase paramWUPRequestBase, WUPResponseBase paramWUPResponseBase)
  {
    LogUtils.d("GUIDFactory", "========================GUIDFatory get guid from server succ========================");
    if (paramWUPResponseBase == null) {
      return;
    }
    GuidRsp localGuidRsp = (GuidRsp)paramWUPResponseBase.get("rsp");
    if (localGuidRsp != null)
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("guid info:guid=");
      localStringBuilder.append(ByteUtils.byteToHexString(localGuidRsp.vGuid));
      localStringBuilder.append(", checksum=");
      localStringBuilder.append(ByteUtils.byteToHexString(localGuidRsp.vValidation));
      localStringBuilder.append(", gtime=");
      localStringBuilder.append(localGuidRsp.lGenerateTime);
      LogUtils.d("GUIDFactory", localStringBuilder.toString());
      onGetGuidValidation(localGuidRsp, paramWUPRequestBase.getType(), paramWUPRequestBase.getBindObject(), paramWUPResponseBase.getContextFeature());
      return;
    }
    LogUtils.d("GUIDFactory", "GuidRsp is null");
  }
  
  public void requestGUIDFromServer(int paramInt, IRequestGuidCallBack paramIRequestGuidCallBack)
  {
    synchronized (this.mCallBacksLock)
    {
      if (this.mRequestGuidCallBacks == null) {
        this.mRequestGuidCallBacks = new ArrayList();
      }
      if (paramIRequestGuidCallBack != null) {
        this.mRequestGuidCallBacks.add(paramIRequestGuidCallBack);
      }
      if (this.mRequestGuidCallBacks.size() > 1)
      {
        LogUtils.d("GUIDFactory", "we have someone request guid already, wait for their result");
        return;
      }
      paramIRequestGuidCallBack = getGUIDWupRequest(paramInt);
      if (paramIRequestGuidCallBack != null) {
        WUPTaskProxy.send(paramIRequestGuidCallBack);
      }
      return;
    }
  }
  
  private class GuidWorkerThread
  {
    private Handler mHandler = null;
    private Looper mLooper = null;
    
    public GuidWorkerThread() {}
    
    public final boolean post(Runnable paramRunnable)
    {
      return this.mHandler.post(paramRunnable);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\GUIDFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */