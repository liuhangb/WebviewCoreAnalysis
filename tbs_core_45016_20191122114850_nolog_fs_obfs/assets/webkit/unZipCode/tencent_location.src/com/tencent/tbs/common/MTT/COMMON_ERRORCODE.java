package com.tencent.tbs.common.MTT;

import java.io.Serializable;

public final class COMMON_ERRORCODE
  implements Serializable
{
  public static final COMMON_ERRORCODE EC_FLOW_CONTROL = new COMMON_ERRORCODE(4, -3, "EC_FLOW_CONTROL");
  public static final COMMON_ERRORCODE EC_ILLEGAL_APPID;
  public static final COMMON_ERRORCODE EC_SAME;
  public static final COMMON_ERRORCODE EC_SUC;
  public static final COMMON_ERRORCODE EC_SYSTEM_ERR;
  public static final int _EC_FLOW_CONTROL = -3;
  public static final int _EC_ILLEGAL_APPID = -2;
  public static final int _EC_SAME = 1;
  public static final int _EC_SUC = 0;
  public static final int _EC_SYSTEM_ERR = -1;
  private static COMMON_ERRORCODE[] __values = new COMMON_ERRORCODE[5];
  private String __T = new String();
  private int __value;
  
  static
  {
    EC_SAME = new COMMON_ERRORCODE(0, 1, "EC_SAME");
    EC_SUC = new COMMON_ERRORCODE(1, 0, "EC_SUC");
    EC_SYSTEM_ERR = new COMMON_ERRORCODE(2, -1, "EC_SYSTEM_ERR");
    EC_ILLEGAL_APPID = new COMMON_ERRORCODE(3, -2, "EC_ILLEGAL_APPID");
  }
  
  private COMMON_ERRORCODE(int paramInt1, int paramInt2, String paramString)
  {
    this.__T = paramString;
    this.__value = paramInt2;
    __values[paramInt1] = this;
  }
  
  public static COMMON_ERRORCODE convert(int paramInt)
  {
    int i = 0;
    for (;;)
    {
      COMMON_ERRORCODE[] arrayOfCOMMON_ERRORCODE = __values;
      if (i >= arrayOfCOMMON_ERRORCODE.length) {
        break;
      }
      if (arrayOfCOMMON_ERRORCODE[i].value() == paramInt) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public static COMMON_ERRORCODE convert(String paramString)
  {
    int i = 0;
    for (;;)
    {
      COMMON_ERRORCODE[] arrayOfCOMMON_ERRORCODE = __values;
      if (i >= arrayOfCOMMON_ERRORCODE.length) {
        break;
      }
      if (arrayOfCOMMON_ERRORCODE[i].toString().equals(paramString)) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public String toString()
  {
    return this.__T;
  }
  
  public int value()
  {
    return this.__value;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\COMMON_ERRORCODE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */