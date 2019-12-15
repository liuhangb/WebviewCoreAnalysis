package com.tencent.common.wup;

public enum RequestPolicy
{
  static
  {
    NORMAL_RETYR_POLICY = new RequestPolicy("NORMAL_RETYR_POLICY", 2);
  }
  
  private RequestPolicy() {}
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\wup\RequestPolicy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */