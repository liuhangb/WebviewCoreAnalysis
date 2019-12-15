package com.tencent.tbs.common.MTT;

import java.io.Serializable;

public final class BGFLAG
  implements Serializable
{
  public static final BGFLAG BG_FETCH = new BGFLAG(4, 8, "BG_FETCH");
  public static final BGFLAG BG_HEARTBEAT;
  public static final BGFLAG BG_LOGIN;
  public static final BGFLAG BG_LOGOUT;
  public static final BGFLAG BG_NONE;
  public static final int _BG_FETCH = 8;
  public static final int _BG_HEARTBEAT = 4;
  public static final int _BG_LOGIN = 1;
  public static final int _BG_LOGOUT = 2;
  public static final int _BG_NONE = 0;
  private static BGFLAG[] __values = new BGFLAG[5];
  private String __T = new String();
  private int __value;
  
  static
  {
    BG_NONE = new BGFLAG(0, 0, "BG_NONE");
    BG_LOGIN = new BGFLAG(1, 1, "BG_LOGIN");
    BG_LOGOUT = new BGFLAG(2, 2, "BG_LOGOUT");
    BG_HEARTBEAT = new BGFLAG(3, 4, "BG_HEARTBEAT");
  }
  
  private BGFLAG(int paramInt1, int paramInt2, String paramString)
  {
    this.__T = paramString;
    this.__value = paramInt2;
    __values[paramInt1] = this;
  }
  
  public static BGFLAG convert(int paramInt)
  {
    int i = 0;
    for (;;)
    {
      BGFLAG[] arrayOfBGFLAG = __values;
      if (i >= arrayOfBGFLAG.length) {
        break;
      }
      if (arrayOfBGFLAG[i].value() == paramInt) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public static BGFLAG convert(String paramString)
  {
    int i = 0;
    for (;;)
    {
      BGFLAG[] arrayOfBGFLAG = __values;
      if (i >= arrayOfBGFLAG.length) {
        break;
      }
      if (arrayOfBGFLAG[i].toString().equals(paramString)) {
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\BGFLAG.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */