package com.tencent.tbs.common.baseinfo;

import java.util.List;
import java.util.Vector;

public class UserInfo
{
  public static UserInfo mInstance;
  
  public static UserInfo getInstance()
  {
    if (mInstance == null) {
      mInstance = new UserInfo();
    }
    return mInstance;
  }
  
  public List<String> getConfigList()
  {
    return null;
  }
  
  public String getQAuth()
  {
    return "31045b957cf33acf31e40be2f3e71c5217597676a9729f1b";
  }
  
  public Vector<String> getWupProxyList()
  {
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\UserInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */