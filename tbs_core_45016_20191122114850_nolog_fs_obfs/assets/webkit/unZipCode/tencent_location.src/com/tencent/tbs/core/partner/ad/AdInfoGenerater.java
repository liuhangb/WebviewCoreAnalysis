package com.tencent.tbs.core.partner.ad;

import android.text.TextUtils;
import android.util.Log;
import com.tencent.smtt.util.X5Log;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import java.security.MessageDigest;

public class AdInfoGenerater
{
  public static final String Q_PROXY_RESP_PMAD = "pmad=";
  public static final String Q_PROXY_RESP_PMU = "pmu=";
  private static int mNativeADFlag = 0;
  private static boolean mNativeADFlagInited = false;
  private AdWebViewController mWc = null;
  
  public AdInfoGenerater(AdWebViewController paramAdWebViewController)
  {
    this.mWc = paramAdWebViewController;
  }
  
  private static String DecodeAdInfo(String paramString)
  {
    try
    {
      paramString = new String(PostEncryption.DesDecrypt(PostEncryption.decryptByPublicKey(PostEncryption.hexToBytes(paramString.substring(0, 32))), PostEncryption.hexToBytes(paramString.substring(32)))).replace("(", "").replace(")", "");
      return paramString;
    }
    catch (Exception paramString)
    {
      for (;;) {}
    }
    return null;
  }
  
  private static String byteToHexString(byte[] paramArrayOfByte)
  {
    if ((paramArrayOfByte != null) && (paramArrayOfByte.length > 0))
    {
      StringBuffer localStringBuffer = new StringBuffer(paramArrayOfByte.length * 2);
      int i = 0;
      while (i < paramArrayOfByte.length)
      {
        if ((paramArrayOfByte[i] & 0xFF) < 16) {
          localStringBuffer.append("0");
        }
        localStringBuffer.append(Long.toString(paramArrayOfByte[i] & 0xFF, 16));
        i += 1;
      }
      return localStringBuffer.toString();
    }
    return null;
  }
  
  private static String getMD5(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    try
    {
      paramString = paramString.getBytes();
      MessageDigest localMessageDigest = MessageDigest.getInstance("MD5");
      localMessageDigest.update(paramString);
      paramString = byteToHexString(localMessageDigest.digest());
      return paramString;
    }
    catch (Exception paramString) {}
    return null;
  }
  
  public static int getNativeAdFlag()
  {
    if (!mNativeADFlagInited)
    {
      initNativeAdFlag();
      mNativeADFlagInited = true;
    }
    return mNativeADFlag;
  }
  
  private static void initNativeAdFlag()
  {
    if (SmttServiceProxy.getInstance().isGdtAdvertisementEnabled()) {
      setNativeADFlag(1);
    }
    if (SmttServiceProxy.getInstance().isSpliceMiniQbAdEnabled()) {
      setNativeADFlag(2);
    }
    if (SmttServiceProxy.getInstance().isBubbleAdEnabled()) {
      setNativeADFlag(4);
    }
    if (SmttServiceProxy.getInstance().isSpliceAdEnabled()) {
      setNativeADFlag(8);
    }
    if (SmttServiceProxy.getInstance().isQBiconInQQShineEnabled()) {
      setNativeADFlag(16);
    }
    if (SmttServiceProxy.getInstance().isBubbleMiniQbAdEnabled()) {
      setNativeADFlag(32);
    }
    if (X5Log.isEnableLog())
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("AdInfoGenerater--initNativeAdFlag() -- mNativeADFlag = ");
      localStringBuilder.append(mNativeADFlag);
      Log.e("NativeAdDebug", localStringBuilder.toString());
    }
  }
  
  public static void setNativeADFlag(int paramInt)
  {
    mNativeADFlag = paramInt | mNativeADFlag;
  }
  
  public boolean generateAdInfo(String paramString)
  {
    Object localObject;
    if (X5Log.isEnableLog())
    {
      localObject = new StringBuilder();
      ((StringBuilder)localObject).append("AdInfoGenerater--generateAdInfo = ");
      ((StringBuilder)localObject).append(paramString);
      Log.e("NativeAdDebug", ((StringBuilder)localObject).toString());
    }
    if ((paramString != null) && (paramString.length() > 0))
    {
      paramString = paramString.split(";");
      int i = 0;
      while (i < paramString.length)
      {
        localObject = paramString[i];
        if (((String)localObject).startsWith("pmu="))
        {
          localObject = ((String)localObject).substring(4);
          if (!"0".equals(localObject)) {
            return getAdInfoFromPMU((String)localObject);
          }
        }
        else if (((String)localObject).startsWith("pmad="))
        {
          localObject = ((String)localObject).substring(5);
          if (!"0".equals(localObject)) {
            return getAdInfoFromPMAD((String)localObject);
          }
        }
        i += 1;
      }
    }
    return false;
  }
  
  public boolean getAdInfoFromPMAD(String paramString)
  {
    if (paramString != null) {
      try
      {
        if (paramString.equals("0")) {
          return false;
        }
        paramString = DecodeAdInfo(paramString);
        if (TextUtils.isEmpty(paramString)) {
          return false;
        }
        if (X5Log.isEnableLog())
        {
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("AdInfoGenerater--getAdInfoFromPMAD() -- adInfo = ");
          localStringBuilder.append(paramString);
          Log.e("NativeAdDebug", localStringBuilder.toString());
        }
        paramString = paramString.trim().split(",");
        if (TextUtils.equals(getMD5(paramString[2]), paramString[3]))
        {
          if (X5Log.isEnableLog()) {
            Log.e("NativeAdDebug", "AdInfoGenerater--getAdInfoFromPMAD() -- MD5--success ");
          }
          this.mWc.setAdInfo(Integer.parseInt(paramString[0]), paramString[1], paramString[2], paramString[3], Integer.parseInt(paramString[4]), paramString[5], Integer.parseInt(paramString[6]), Integer.parseInt(paramString[7]));
          return true;
        }
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
      }
    }
    return false;
  }
  
  public boolean getAdInfoFromPMU(String paramString)
  {
    if (paramString != null) {}
    for (;;)
    {
      try
      {
        if (paramString.equals("0")) {
          return false;
        }
        paramString = DecodeAdInfo(paramString);
        if (TextUtils.isEmpty(paramString)) {
          return false;
        }
        if (X5Log.isEnableLog())
        {
          localObject = new StringBuilder();
          ((StringBuilder)localObject).append("AdInfoGenerater--getAdInfoFromPMU() -- adInfo = ");
          ((StringBuilder)localObject).append(paramString);
          Log.e("NativeAdDebug", ((StringBuilder)localObject).toString());
        }
        paramString = paramString.trim().split(",");
        Object localObject = getMD5(paramString[2]);
        if (TextUtils.equals((CharSequence)localObject, paramString[1]))
        {
          if (X5Log.isEnableLog()) {
            Log.e("NativeAdDebug", "AdInfoGenerater--getAdInfoFromPMU() -- MD5--success ");
          }
          AdWebViewController localAdWebViewController = this.mWc;
          if (!this.mWc.isMiniQB()) {
            break label154;
          }
          i = 2;
          localAdWebViewController.setAdInfo(i, "", paramString[2], (String)localObject, 0, "", 0, 1);
          return true;
        }
      }
      catch (Throwable paramString)
      {
        paramString.printStackTrace();
      }
      return false;
      label154:
      int i = 1;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdInfoGenerater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */