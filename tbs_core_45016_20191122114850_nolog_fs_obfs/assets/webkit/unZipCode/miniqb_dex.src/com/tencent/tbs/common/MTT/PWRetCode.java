package com.tencent.tbs.common.MTT;

import java.io.Serializable;

public final class PWRetCode
  implements Serializable
{
  public static final PWRetCode PWRetCode_NOTFOUND;
  public static final PWRetCode PWRetCode_OK = new PWRetCode(3, 0, "PWRetCode_OK");
  public static final PWRetCode PWRetCode_PARAMS;
  public static final PWRetCode PWRetCode_SYS;
  public static final int _PWRetCode_NOTFOUND = -2;
  public static final int _PWRetCode_OK = 0;
  public static final int _PWRetCode_PARAMS = -1;
  public static final int _PWRetCode_SYS = -3;
  private static PWRetCode[] __values = new PWRetCode[4];
  private String __T = new String();
  private int __value;
  
  static
  {
    PWRetCode_SYS = new PWRetCode(0, -3, "PWRetCode_SYS");
    PWRetCode_NOTFOUND = new PWRetCode(1, -2, "PWRetCode_NOTFOUND");
    PWRetCode_PARAMS = new PWRetCode(2, -1, "PWRetCode_PARAMS");
  }
  
  private PWRetCode(int paramInt1, int paramInt2, String paramString)
  {
    this.__T = paramString;
    this.__value = paramInt2;
    __values[paramInt1] = this;
  }
  
  public static PWRetCode convert(int paramInt)
  {
    int i = 0;
    for (;;)
    {
      PWRetCode[] arrayOfPWRetCode = __values;
      if (i >= arrayOfPWRetCode.length) {
        break;
      }
      if (arrayOfPWRetCode[i].value() == paramInt) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public static PWRetCode convert(String paramString)
  {
    int i = 0;
    for (;;)
    {
      PWRetCode[] arrayOfPWRetCode = __values;
      if (i >= arrayOfPWRetCode.length) {
        break;
      }
      if (arrayOfPWRetCode[i].toString().equals(paramString)) {
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PWRetCode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */