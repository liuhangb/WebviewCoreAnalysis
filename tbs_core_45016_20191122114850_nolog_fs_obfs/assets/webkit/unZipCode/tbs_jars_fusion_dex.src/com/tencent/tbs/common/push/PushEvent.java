package com.tencent.tbs.common.push;

public class PushEvent
{
  public static final byte OP_DEREGISTER = 1;
  public static final byte OP_REGISTER = 0;
  public int appid;
  public IPushResponseCallBack callBack;
  public byte op = -1;
  public String sessionId;
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\push\PushEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */