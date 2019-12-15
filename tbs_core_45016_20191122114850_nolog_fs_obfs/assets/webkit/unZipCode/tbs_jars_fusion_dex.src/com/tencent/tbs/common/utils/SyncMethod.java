package com.tencent.tbs.common.utils;

public abstract class SyncMethod
{
  public Object mReturnValue = null;
  public String mThreadName = "SyncMethod";
  
  public abstract void methodImpl(Object... paramVarArgs);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\utils\SyncMethod.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */