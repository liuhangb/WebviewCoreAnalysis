package com.tencent.tbs.tbsshell.common;

public class QProxyPolicies
{
  private static final int STATE_NOT_INITIAL = -1;
  private static final int STATE_SURE_DIRECT = 0;
  private static final int STATE_SURE_QPROXY = 1;
  private static final int STATE_UNDETERMINED_DIRECT = 2;
  private static final int STATE_UNDETERMINED_QPROXY = 3;
  public static final int VALUE_DIRECT_REASON_BIG_POST = 524288;
  public static final int VALUE_DIRECT_REASON_CLIENT_RESTRICT = 536870912;
  public static final int VALUE_DIRECT_REASON_DEBUGMODE_FORCE = 2097152;
  public static final int VALUE_DIRECT_REASON_FOLLOW_REFERLIST = 1073741824;
  public static final int VALUE_DIRECT_REASON_GET_SPDY_PROXY_EXCEPTION = 262144;
  public static final int VALUE_DIRECT_REASON_HTTPS_RESTRICT = 65536;
  public static final int VALUE_DIRECT_REASON_KINGCARD_TUNNEL = 1207959552;
  public static final int VALUE_DIRECT_REASON_LOAD_BYPASS_PROXY = 268435456;
  public static final int VALUE_DIRECT_REASON_NOT_LOGIN = 8192;
  public static final int VALUE_DIRECT_REASON_NO_IPLIST = 67108864;
  public static final int VALUE_DIRECT_REASON_QPROXY_DETECTING = 4096;
  public static final int VALUE_DIRECT_REASON_QPROXY_ERROR = 512;
  public static final int VALUE_DIRECT_REASON_RESOURCE_CACHED = 33554432;
  public static final int VALUE_DIRECT_REASON_SERVER_CONTROLL_SUBRESOURCE_DIRECTLY = 131072;
  public static final int VALUE_DIRECT_REASON_SERVER_NOT_ALLOW_AND_NOT_IN_PROXY_WHITE_LIST = 8388608;
  public static final int VALUE_DIRECT_REASON_SERVER_TELL_ERROR_4XX_OR_5XX = 32768;
  public static final int VALUE_DIRECT_REASON_SERVER_TELL_ERROR_8XX = 16384;
  public static final int VALUE_DIRECT_REASON_SIM_ROAMING = 1342177280;
  public static final int VALUE_DIRECT_REASON_SUBRESOURCE_FOLLOW_REFERER_DIRECTRLY = 1048576;
  public static final int VALUE_DIRECT_REASON_SYSPROXY = 134217728;
  public static final int VALUE_DIRECT_REASON_URL_IN_DIRECT_WHITE_LIST = 2048;
  public static final int VALUE_DIRECT_REASON_USER_NOT_ALLOW_AND_NOT_IN_PROXY_WHITE_LIST = 16777216;
  public static final int VALUE_DIRECT_REASON_USE_QUIC_DIRECT = 1610612736;
  public static final int VALUE_DIRECT_REASON_VPN_ENABLED = 4194304;
  public static final int VALUE_DIRECT_REASON_WIFI_LOGIN = 1024;
  public static final int VALUE_FINAL_USE_DIRECT = 0;
  public static final int VALUE_FINAL_USE_QPROXY = 1;
  public static final int VALUE_FORCE_USE_QPROXY = 128;
  public static final int VALUE_FORCE_USE_QPROXY_DEBUGMODE = 256;
  public static final int VALUE_HOST_CHOSE_QPROXY_STATE = 32;
  public static final int VALUE_HOST_QPROXY_FLAG = 16;
  public static final int VALUE_SERVER_FORCE_CONTROL = 8;
  public static final int VALUE_SERVER_QPROXY_FLAG = 4;
  public static final int VALUE_SERVER_QPROXY_STATUS_NOT_INITED = 2;
  public static final int VALUE_USER_QPROXY_FLAG = 64;
  private static boolean result_QProxy;
  
  public static boolean get_result_QProxy()
  {
    return result_QProxy;
  }
  
  public static String proxyTypeString(int paramInt)
  {
    int i = paramInt & 0x1;
    if (i == 1)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("QPROXT STATUS:");
      ((StringBuilder)localObject1).append("proxy");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("QPROXT STATUS:");
      ((StringBuilder)localObject1).append("direct");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    Object localObject2 = localObject1;
    if ((paramInt & 0x2) == 2)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_ServerQProxyStatusNotInited");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    if ((paramInt & 0x8) == 8)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_serverForceCtrl");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_serverNoCtrl");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    if ((paramInt & 0x4) == 4)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_serverQproxy");
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_serverDirect");
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    if ((paramInt & 0x20) == 32)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_hostChoose");
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_hostNoChoose");
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    if ((paramInt & 0x10) == 16)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_hostQproxy");
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_hostDirect");
      localObject1 = ((StringBuilder)localObject2).toString();
    }
    if ((paramInt & 0x40) == 64)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_userQproxy");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    else
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_userDirect");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    Object localObject1 = localObject2;
    if ((paramInt & 0x100) == 256)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_ForceQProxyDebugMode");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    localObject2 = localObject1;
    if (i == 0)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(" && DIRECT REASON:");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    if ((paramInt & 0x200) == 512)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("QProxyError");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((paramInt & 0x4000000) == 67108864)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_NoIPlist");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((paramInt & 0x400) == 1024)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_WifiLogin");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((paramInt & 0x800) == 2048)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_InDirectWhiteList");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((paramInt & 0x1000) == 4096)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_QProxyDetecting");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((paramInt & 0x2000) == 8192)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_NotLogin");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((paramInt & 0x4000) == 16384)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_ServerError80X");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((0x8000 & paramInt) == 32768)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_ServerError40Xor50X");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((0x10000 & paramInt) == 65536)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_Https");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((0x20000000 & paramInt) == 536870912)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_ClientRestrict");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((0x20000 & paramInt) == 131072)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_ServerContorllSubresourceDirectly");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((0x40000 & paramInt) == 262144)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_SpdyProxyException");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((0x80000 & paramInt) == 524288)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_BigPost");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((0x100000 & paramInt) == 1048576)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_subresourceFollowReferGoDirect");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((paramInt & 0x80) == 128)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_ForceQProxy");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((0x200000 & paramInt) == 2097152)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_ForceDirectDebugMode");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((0x400000 & paramInt) == 4194304)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_VpnEnabled");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else if ((0x800000 & paramInt) == 8388608)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_ServerNotAllowAndNotInProxyWhiteList");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    else
    {
      localObject1 = localObject2;
      if ((0x1000000 & paramInt) == 16777216)
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject2);
        ((StringBuilder)localObject1).append("_UserNotAllowAndNotInProxyWhiteList");
        localObject1 = ((StringBuilder)localObject1).toString();
      }
    }
    localObject2 = localObject1;
    if ((paramInt & 0x2000000) == 33554432)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_Cached");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    localObject1 = localObject2;
    if ((paramInt & 0x8000000) == 134217728)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_systemProxy");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    localObject2 = localObject1;
    if ((0x10000000 & paramInt) == 268435456)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_loadByPassProxy");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    localObject1 = localObject2;
    if ((0x40000000 & paramInt) == 1073741824)
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append((String)localObject2);
      ((StringBuilder)localObject1).append("_isMainResourceInReferList");
      localObject1 = ((StringBuilder)localObject1).toString();
    }
    localObject2 = localObject1;
    if ((paramInt & 0x60000000) == 1610612736)
    {
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append("_isQuicDirect");
      localObject2 = ((StringBuilder)localObject2).toString();
    }
    return (String)localObject2;
  }
  
  public static final int setDirectReason(int paramInt1, int paramInt2)
  {
    return paramInt1 & 0xFF7E | paramInt2;
  }
  
  public static final int setFinallyUsedQProxy(int paramInt, boolean paramBoolean)
  {
    if (!paramBoolean) {
      return paramInt & 0xFFFFFFFE;
    }
    return paramInt | 0x1;
  }
  
  public static final int setForceQProxyReason(int paramInt)
  {
    return paramInt | 0x1;
  }
  
  public static final int setServerAndUserSettingSituation(int paramInt1, int paramInt2, boolean paramBoolean, byte paramByte)
  {
    if ((paramInt2 != 2) && (paramInt2 != 3))
    {
      if (paramInt2 == -1)
      {
        paramInt1 |= 0x2;
      }
      else
      {
        paramInt1 |= 0x8;
        if (paramInt2 != 1) {
          paramInt1 &= 0xFFFFFFFB;
        } else {
          paramInt1 |= 0x4;
        }
      }
    }
    else
    {
      paramInt1 &= 0xFFFFFFF7;
      if (paramInt2 == 2) {
        paramInt1 &= 0xFFFFFFFB;
      } else {
        paramInt1 |= 0x4;
      }
    }
    if (!paramBoolean) {
      paramInt1 &= 0xFFFFFFBF;
    } else {
      paramInt1 |= 0x40;
    }
    if (paramByte != 0)
    {
      paramInt1 |= 0x20;
      if (paramByte == 1) {
        return paramInt1 & 0xFFFFFFEF;
      }
      return paramInt1 | 0x10;
    }
    return paramInt1 & 0xFFFFFFDF;
  }
  
  public static final int setServerSetBit(int paramInt, boolean paramBoolean)
  {
    if (!paramBoolean) {
      return paramInt & 0xFFFFFFFB;
    }
    return paramInt | 0x4;
  }
  
  public static final int setUserSetBit(int paramInt, boolean paramBoolean)
  {
    if (!paramBoolean) {
      return paramInt & 0xFFFFFFBF;
    }
    return paramInt | 0x40;
  }
  
  public static final boolean shouldForceUseQProxy(int paramInt)
  {
    return (paramInt & 0x80) == 128;
  }
  
  public static final boolean shouldUseQProxyAccordingToFlag(int paramInt)
  {
    boolean bool = true;
    if ((paramInt & 0x1) != 1) {
      bool = false;
    }
    result_QProxy = bool;
    return result_QProxy;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\common\QProxyPolicies.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */