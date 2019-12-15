package com.tencent.smtt.net.http;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.tencent.common.http.Apn;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.tbsshell.common.QProxyPolicies;
import java.net.URL;
import java.util.Map;
import org.chromium.base.annotations.JNINamespace;

@JNINamespace("tencent")
public class JNISmttService
{
  private static Handler mHandler = new Handler(mHandlerThread.getLooper())
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      int i = paramAnonymousMessage.what;
    }
  };
  private static HandlerThread mHandlerThread = new HandlerThread("report-jni");
  private static JNISmttService mInstance;
  private final String mDefaultGUID = "00000000000000000000000000000000";
  
  static
  {
    mHandlerThread.start();
  }
  
  private void checkInitialized() {}
  
  /* Error */
  @org.chromium.base.annotations.CalledByNative
  public static JNISmttService getInstance()
  {
    // Byte code:
    //   0: ldc 2
    //   2: monitorenter
    //   3: getstatic 56	com/tencent/smtt/net/http/JNISmttService:mInstance	Lcom/tencent/smtt/net/http/JNISmttService;
    //   6: ifnull +10 -> 16
    //   9: getstatic 56	com/tencent/smtt/net/http/JNISmttService:mInstance	Lcom/tencent/smtt/net/http/JNISmttService;
    //   12: astore_0
    //   13: goto +15 -> 28
    //   16: new 2	com/tencent/smtt/net/http/JNISmttService
    //   19: dup
    //   20: invokespecial 57	com/tencent/smtt/net/http/JNISmttService:<init>	()V
    //   23: astore_0
    //   24: aload_0
    //   25: putstatic 56	com/tencent/smtt/net/http/JNISmttService:mInstance	Lcom/tencent/smtt/net/http/JNISmttService;
    //   28: ldc 2
    //   30: monitorexit
    //   31: aload_0
    //   32: areturn
    //   33: astore_0
    //   34: goto +13 -> 47
    //   37: astore_0
    //   38: aload_0
    //   39: invokevirtual 60	java/lang/Exception:printStackTrace	()V
    //   42: ldc 2
    //   44: monitorexit
    //   45: aconst_null
    //   46: areturn
    //   47: ldc 2
    //   49: monitorexit
    //   50: aload_0
    //   51: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   12	20	0	localJNISmttService	JNISmttService
    //   33	1	0	localObject	Object
    //   37	14	0	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   3	13	33	finally
    //   16	28	33	finally
    //   38	42	33	finally
    //   3	13	37	java/lang/Exception
    //   16	28	37	java/lang/Exception
  }
  
  public String getAPN()
  {
    Apn.getApnType();
    return Apn.getApnName(Apn.sApnTypeS);
  }
  
  public String getCurrentWebHost()
  {
    return null;
  }
  
  public int getCustomImageQuality()
  {
    checkInitialized();
    return SmttServiceProxy.getInstance().getCustomImageQuality();
  }
  
  public String getGUID()
  {
    checkInitialized();
    String str = SmttServiceProxy.getInstance().getGUID();
    if ((str != null) && (!str.equals(""))) {
      return str;
    }
    return "00000000000000000000000000000000";
  }
  
  public void getIPList(int paramInt)
  {
    checkInitialized();
  }
  
  public Map<String, String> getLbsHeaders(String paramString)
  {
    checkInitialized();
    return SmttServiceProxy.getInstance().getLbsHeaders(paramString);
  }
  
  public String getLoginUserInfo()
  {
    return null;
  }
  
  public String getMd5GUID()
  {
    checkInitialized();
    return SmttServiceProxy.getInstance().getMd5GUID();
  }
  
  public String getQAuth()
  {
    checkInitialized();
    return SmttServiceProxy.getInstance().getQAuth();
  }
  
  public URL getQProxyAddress(int paramInt)
  {
    checkInitialized();
    return SmttServiceProxy.getInstance().getQProxyAddress(paramInt);
  }
  
  public String getQProxyParams()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("PROTOCOL=");
    localStringBuffer.append(0);
    int i = WebSettingsExtension.getImageQuality();
    localStringBuffer.append(",QUALITY=");
    localStringBuffer.append(i);
    Apn.getApnType();
    localStringBuffer.append(",APN=");
    localStringBuffer.append(Apn.getApnName(Apn.sApnTypeS));
    return localStringBuffer.toString();
  }
  
  public String getQProxyType()
  {
    return "5";
  }
  
  public String getQUA2()
  {
    checkInitialized();
    return SmttServiceProxy.getInstance().getQUA2();
  }
  
  public void getSPDYIPList()
  {
    checkInitialized();
  }
  
  public boolean isIgnoreHenoUpdate(String paramString)
  {
    return false;
  }
  
  public boolean isNeedHenoPlay(String paramString)
  {
    return false;
  }
  
  public boolean isNeedHenoUpdate(String paramString1, String paramString2)
  {
    return false;
  }
  
  public boolean isNeedQHead(String paramString)
  {
    checkInitialized();
    return SmttServiceProxy.getInstance().isNeedQHead(paramString);
  }
  
  public boolean isNeedWIFILogin()
  {
    return false;
  }
  
  public boolean isNetworkAvailable()
  {
    checkInitialized();
    return Apn.isNetworkAvailable();
  }
  
  public boolean isProxySettingEnabled()
  {
    return false;
  }
  
  public boolean isUsedQProxy(String paramString1, boolean paramBoolean, String paramString2)
  {
    checkInitialized();
    return QProxyPolicies.shouldUseQProxyAccordingToFlag(SmttServiceProxy.getInstance().getQProxyUsingFlag(paramString1, paramBoolean, WebSettingsExtension.getQProxyEnabled(), WebSettingsExtension.getIsKidMode(), WebSettingsExtension.getIsHostUseQProxy(), paramString2, SmttServiceProxy.getInstance().isKingCardUser()));
  }
  
  public boolean isWifi()
  {
    Apn.getApnType();
    return Apn.sApnTypeS == 4;
  }
  
  public boolean isX5ReaderModeDisabledForURL(String paramString)
  {
    return false;
  }
  
  public boolean isX5ReaderModeEnabled()
  {
    return false;
  }
  
  public void onProxyDetectFinish(int paramInt1, int paramInt2)
  {
    checkInitialized();
    SmttServiceProxy.getInstance().onProxyDetectFinish(paramInt1, paramInt2);
  }
  
  public boolean onReceivedHeaders(String paramString, Map<String, String> paramMap)
  {
    return false;
  }
  
  public boolean reportDnsResolveResults(int paramInt1, int paramInt2, String paramString1, String paramString2, String paramString3, int paramInt3, int paramInt4)
  {
    return false;
  }
  
  public void resetIPList()
  {
    checkInitialized();
    SmttServiceProxy.getInstance().resetIPList();
  }
  
  public void setIgnoreHenoUpdate(String paramString, boolean paramBoolean) {}
  
  public void setNeedWIFILogin(boolean paramBoolean) {}
  
  public boolean setQProxyBlackDomain(String paramString)
  {
    return false;
  }
  
  public boolean setQProxyBlackUrl(String paramString)
  {
    return false;
  }
  
  public boolean setQProxyWhiteUrl(String paramString)
  {
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\net\http\JNISmttService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */