package com.tencent.common.gatewaydetect;

import com.tencent.common.connectivity.ConnectivityAdapterHolder;
import com.tencent.common.http.Apn;

public class GatewayDetector
{
  public static final int GATEWAY_TYPE_MOBILE = 1;
  public static final int GATEWAY_TYPE_UNKNOWN = 0;
  public static final int GATEWAY_TYPE_WIFI = 2;
  private static final a a = new a();
  
  public static void detectGatewayInfo(GatewayDetectCallback paramGatewayDetectCallback)
  {
    if (paramGatewayDetectCallback == null) {
      return;
    }
    ConnectivityAdapterHolder.execute(new Runnable()
    {
      public void run()
      {
        GatewayDetector.GatewayInfo localGatewayInfo = GatewayDetector.a().b();
        if (localGatewayInfo == null)
        {
          this.a.onGatewayDetectResult(false, new GatewayDetector.GatewayInfo());
          return;
        }
        this.a.onGatewayDetectResult(true, localGatewayInfo);
      }
    });
  }
  
  public static void detectGatewayNetworkType(GatewayNetworkTypeCallback paramGatewayNetworkTypeCallback)
  {
    if (paramGatewayNetworkTypeCallback == null) {
      return;
    }
    ConnectivityAdapterHolder.execute(new Runnable()
    {
      public void run()
      {
        if (!Apn.isNetworkAvailable())
        {
          this.a.onGatewayNetworkType(0);
          return;
        }
        if (Apn.isMobileNetwork())
        {
          this.a.onGatewayNetworkType(1);
          return;
        }
        GatewayDetector.GatewayInfo localGatewayInfo = GatewayDetector.getCurrGatewayInfo();
        if ((localGatewayInfo != null) && (localGatewayInfo.type != 0))
        {
          this.a.onGatewayNetworkType(localGatewayInfo.type);
          return;
        }
        localGatewayInfo = GatewayDetector.a().b();
        if (localGatewayInfo == null)
        {
          this.a.onGatewayNetworkType(0);
          return;
        }
        this.a.onGatewayNetworkType(localGatewayInfo.type);
      }
    });
  }
  
  public static GatewayInfo getCurrGatewayInfo()
  {
    GatewayInfo localGatewayInfo = a.a();
    if (localGatewayInfo != null) {
      return localGatewayInfo;
    }
    return new GatewayInfo();
  }
  
  public static abstract interface GatewayDetectCallback
  {
    public abstract void onGatewayDetectResult(boolean paramBoolean, GatewayDetector.GatewayInfo paramGatewayInfo);
  }
  
  public static final class GatewayInfo
  {
    public String apnName = "UNKNOWN";
    public String gatewayIP = "";
    public int type = 0;
    
    public String toString()
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("apn = ");
      localStringBuilder.append(this.apnName);
      localStringBuilder.append(", type = ");
      localStringBuilder.append(this.type);
      localStringBuilder.append(", gatewayIP = ");
      localStringBuilder.append(this.gatewayIP);
      return localStringBuilder.toString();
    }
  }
  
  public static abstract interface GatewayNetworkTypeCallback
  {
    public abstract void onGatewayNetworkType(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\gatewaydetect\GatewayDetector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */