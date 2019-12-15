package com.tencent.tbs.common.wifi.state;

import android.os.Message;

public class State
  implements IState
{
  public void enter() {}
  
  public void exit() {}
  
  public String getName()
  {
    String str = getClass().getName();
    return str.substring(str.lastIndexOf('$') + 1);
  }
  
  public boolean processMessage(Message paramMessage)
  {
    return false;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\wifi\state\State.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */