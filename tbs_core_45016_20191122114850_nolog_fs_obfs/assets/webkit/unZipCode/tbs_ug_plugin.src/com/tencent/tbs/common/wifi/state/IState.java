package com.tencent.tbs.common.wifi.state;

import android.os.Message;

public abstract interface IState
{
  public static final boolean HANDLED = true;
  public static final boolean NOT_HANDLED = false;
  
  public abstract void enter();
  
  public abstract void exit();
  
  public abstract String getName();
  
  public abstract boolean processMessage(Message paramMessage);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\state\IState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */