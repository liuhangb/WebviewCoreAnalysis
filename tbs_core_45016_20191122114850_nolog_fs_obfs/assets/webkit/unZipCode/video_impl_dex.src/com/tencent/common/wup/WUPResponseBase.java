package com.tencent.common.wup;

import android.text.TextUtils;
import android.util.Pair;
import com.taf.RequestPacket;
import com.taf.UniPacket;
import com.tencent.basesupport.FLogger;
import com.tencent.common.manifest.EventEmiter;
import com.tencent.common.manifest.EventMessage;
import java.util.Map;

public class WUPResponseBase
{
  protected static final String TAG = "WUPResponse";
  private long decodeEndTime = -1L;
  private long decodeStartTime = -1L;
  protected ClassLoader mClassLoader;
  protected String mContextFeature = "";
  protected String mEncodeName = null;
  private long mHandleEndTime = -1L;
  private long mHandleStartTime = -1L;
  private boolean mIsRespFromTestEnv = false;
  protected byte[] mOrglResponseData = null;
  private UniPacket mResPacket = null;
  protected Integer mReturnCode = null;
  private Map<String, String> mServantStatus = null;
  
  public WUPResponseBase() {}
  
  public WUPResponseBase(UniPacket paramUniPacket)
  {
    this.mResPacket = paramUniPacket;
  }
  
  public WUPResponseBase(UniPacket paramUniPacket, ClassLoader paramClassLoader)
  {
    this.mResPacket = paramUniPacket;
    this.mClassLoader = paramClassLoader;
  }
  
  private Object doGetDataWithClassLoader(String paramString, ClassLoader paramClassLoader)
  {
    UniPacket localUniPacket = this.mResPacket;
    if (localUniPacket == null) {
      return null;
    }
    if (paramClassLoader != null) {}
    try
    {
      return localUniPacket.get(paramString, false, paramClassLoader);
    }
    catch (Throwable paramString)
    {
      paramString.printStackTrace();
      paramString = new EventMessage("com.tencent.mtt.external.rqd.RQDManager.handleCatchException", new Object[] { Thread.currentThread(), paramString, "doGetDataWithClassLoader" });
      EventEmiter.getDefault().emit(paramString);
    }
    paramString = localUniPacket.get(paramString);
    return paramString;
    return null;
  }
  
  private int getIntStatus(String paramString, int paramInt)
  {
    Map localMap = getServantStatus();
    if (localMap == null) {
      return paramInt;
    }
    paramString = (String)localMap.get(paramString);
    if (TextUtils.isEmpty(paramString)) {
      return paramInt;
    }
    try
    {
      int i = Integer.valueOf(paramString).intValue();
      return i;
    }
    catch (Exception paramString)
    {
      paramString.printStackTrace();
    }
    return paramInt;
  }
  
  private Map<String, String> getServantStatus()
  {
    Object localObject = this.mServantStatus;
    if (localObject != null) {
      return (Map<String, String>)localObject;
    }
    localObject = this.mResPacket;
    if ((localObject != null) && (((UniPacket)localObject)._package != null) && (this.mResPacket._package.status != null))
    {
      this.mServantStatus = this.mResPacket._package.status;
      return this.mServantStatus;
    }
    return null;
  }
  
  public void decode()
  {
    Object localObject = this.mOrglResponseData;
    if ((localObject != null) && (localObject.length > 4) && (this.mEncodeName != null)) {
      try
      {
        this.mResPacket = new UniPacket();
        this.mResPacket.setEncodeName(this.mEncodeName);
        this.mResPacket.decode(this.mOrglResponseData);
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("UniPacket:");
        ((StringBuilder)localObject).append(this.mResPacket.toString());
        FLogger.d("WUPResponse", ((StringBuilder)localObject).toString());
        return;
      }
      catch (Throwable localThrowable)
      {
        localThrowable.printStackTrace();
        return;
      }
    }
    FLogger.d("WUPResponse", "rspData == null || mEncodeName == null");
  }
  
  public Object get(String paramString)
  {
    return getResponseData(paramString);
  }
  
  public Object get(String paramString, ClassLoader paramClassLoader)
  {
    return doGetDataWithClassLoader(paramString, paramClassLoader);
  }
  
  public String getContextFeature()
  {
    return this.mContextFeature;
  }
  
  public long getDecodeUsedTime()
  {
    long l1 = this.decodeEndTime;
    if (l1 > 0L)
    {
      long l2 = this.decodeStartTime;
      if (l2 > 0L) {
        return l1 - l2;
      }
    }
    return -1L;
  }
  
  public String getEncodeName()
  {
    return this.mEncodeName;
  }
  
  public int getErrorCode()
  {
    return getIntStatus("STATUS_RESULT_CODE", 0);
  }
  
  public String getFuncName()
  {
    return this.mResPacket.getFuncName();
  }
  
  public long getHandleUsedTime()
  {
    long l1 = this.mHandleStartTime;
    if (l1 > 0L)
    {
      long l2 = this.mHandleEndTime;
      if (l2 > 0L) {
        return l2 - l1;
      }
    }
    return -1L;
  }
  
  public byte[] getOrglResponseData()
  {
    return this.mOrglResponseData;
  }
  
  public int getPacketSize()
  {
    return this.mResPacket.getPacketSize();
  }
  
  public int getRequestId()
  {
    return this.mResPacket.getRequestId();
  }
  
  public Object getResponseData(String paramString)
  {
    return doGetDataWithClassLoader(paramString, this.mClassLoader);
  }
  
  public int getRetryFlag()
  {
    return getIntStatus("RETRY_FLAG", -1);
  }
  
  public int getRetryTime()
  {
    return getIntStatus("RETRY_TIME", -1);
  }
  
  public Integer getReturnCode()
  {
    return this.mReturnCode;
  }
  
  public String getServantName()
  {
    return this.mResPacket.getServantName();
  }
  
  public Pair<String, byte[]> getUnipackRawRespData(String paramString)
  {
    UniPacket localUniPacket = this.mResPacket;
    if (localUniPacket == null) {
      return null;
    }
    return localUniPacket.getRawResponseData(paramString);
  }
  
  public boolean isRespFromTestEnvServer()
  {
    return this.mIsRespFromTestEnv;
  }
  
  public boolean isSuccess()
  {
    Integer localInteger = this.mReturnCode;
    return (localInteger != null) && (localInteger.intValue() == 0);
  }
  
  public void setContextFeature(String paramString)
  {
    this.mContextFeature = paramString;
  }
  
  public void setDecodeEndTime(long paramLong)
  {
    this.decodeEndTime = paramLong;
  }
  
  public void setDecodeStartTime(long paramLong)
  {
    this.decodeStartTime = paramLong;
  }
  
  public void setEncodeName(String paramString)
  {
    this.mEncodeName = paramString;
  }
  
  public void setHandleEndTime(long paramLong)
  {
    this.mHandleEndTime = paramLong;
  }
  
  public void setHandleStartTime(long paramLong)
  {
    this.mHandleStartTime = paramLong;
  }
  
  public void setOrglResponseData(byte[] paramArrayOfByte)
  {
    this.mOrglResponseData = paramArrayOfByte;
  }
  
  public void setProtocolClassNamePrefs(String paramString)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      UniPacket localUniPacket = this.mResPacket;
      if (localUniPacket == null) {
        return;
      }
      localUniPacket.setProtocolClassNamePrefs(paramString);
      return;
    }
  }
  
  protected void setRespFromTestServer(boolean paramBoolean)
  {
    this.mIsRespFromTestEnv = paramBoolean;
  }
  
  public void setReturnCode(Integer paramInteger)
  {
    this.mReturnCode = paramInteger;
  }
  
  public void setUniPacket(UniPacket paramUniPacket)
  {
    this.mResPacket = paramUniPacket;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\WUPResponseBase.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */