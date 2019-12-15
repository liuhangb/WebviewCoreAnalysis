package com.tencent.common.wup.interfaces;

import com.tencent.common.manifest.annotation.Extension;
import com.tencent.common.wup.WUPRequestBase;
import com.tencent.common.wup.WUPTask;
import java.util.HashMap;

@Extension
public abstract class IWUPClientProxy
{
  public static final String KEY_ENABLE_WUP_FILE_TOKEN = "key_enable_wup_file_token";
  public static final String KEY_WUP_SERVER_EVER_FAILED = "key_wup_server_ever_failed_";
  public static final String WUP_CONFIG_ENCRYPT_TYPE = "key_wup_rsa_aes_encrypt_type";
  public static final String WUP_LAST_MODIFY_ENCRYPT_EXPIRED = "key_last_modify_wup_encrypt_time";
  public static final String WUP_SERVANT_AVAILABLE_TIME_PREFIX = "wup_server_avail_time_";
  
  public boolean getBooleanConfiguration(String paramString, boolean paramBoolean)
  {
    return paramBoolean;
  }
  
  public abstract byte[] getByteGuid();
  
  public String getCustomWupProxyAddress()
  {
    return null;
  }
  
  public int getIntConfiguration(String paramString, int paramInt)
  {
    return paramInt;
  }
  
  public long getLongConfiguration(String paramString, long paramLong)
  {
    return paramLong;
  }
  
  public abstract String getQUA(boolean paramBoolean);
  
  public abstract String getQUA2_V3();
  
  public String getStringConfiguration(String paramString1, String paramString2)
  {
    return paramString2;
  }
  
  public void reportStatInfo(String paramString, HashMap<String, String> paramHashMap) {}
  
  public void sendStatAction(WUPRequestBase paramWUPRequestBase) {}
  
  public void setBooleanConfiguration(String paramString, boolean paramBoolean) {}
  
  public void setIntConfiguration(String paramString, int paramInt) {}
  
  public void setLongConfiguration(String paramString, long paramLong) {}
  
  public void setStringConfiguration(String paramString1, String paramString2) {}
  
  public boolean shouldPendWUPResponses()
  {
    return false;
  }
  
  public void statFlow(WUPRequestBase paramWUPRequestBase, WUPTask paramWUPTask) {}
  
  public void userBehaviorStatistics(String paramString) {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\interfaces\IWUPClientProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */