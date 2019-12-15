package com.tencent.tbs.common.baseinfo;

import com.qq.gdt.action.qadid.QADID;
import com.tencent.mtt.ContextHolder;

public final class QAID
{
  private static volatile QAID instance;
  private String qaid = QADID.createQADID(ContextHolder.getAppContext());
  
  public static QAID getInstance()
  {
    if (instance == null) {
      try
      {
        if (instance == null) {
          instance = new QAID();
        }
      }
      finally {}
    }
    return instance;
  }
  
  public String getQAID()
  {
    return this.qaid;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\baseinfo\QAID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */