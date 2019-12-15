package com.tencent.tbs.common.MTT;

import java.io.Serializable;

public final class EUIDType
  implements Serializable
{
  public static final EUIDType E_TYPE_A2 = new EUIDType(4, 4, "E_TYPE_A2");
  public static final EUIDType E_TYPE_NONE;
  public static final EUIDType E_TYPE_PCQQ;
  public static final EUIDType E_TYPE_QQ;
  public static final EUIDType E_TYPE_WX;
  public static final int _E_TYPE_A2 = 4;
  public static final int _E_TYPE_NONE = 0;
  public static final int _E_TYPE_PCQQ = 3;
  public static final int _E_TYPE_QQ = 1;
  public static final int _E_TYPE_WX = 2;
  private static EUIDType[] __values = new EUIDType[4];
  private String __T = new String();
  private int __value;
  
  static
  {
    E_TYPE_NONE = new EUIDType(0, 0, "E_TYPE_NONE");
    E_TYPE_QQ = new EUIDType(1, 1, "E_TYPE_QQ");
    E_TYPE_WX = new EUIDType(2, 2, "E_TYPE_WX");
    E_TYPE_PCQQ = new EUIDType(3, 3, "E_TYPE_PCQQ");
  }
  
  private EUIDType(int paramInt1, int paramInt2, String paramString)
  {
    this.__T = paramString;
    this.__value = paramInt2;
    __values[paramInt1] = this;
  }
  
  public static EUIDType convert(int paramInt)
  {
    int i = 0;
    for (;;)
    {
      EUIDType[] arrayOfEUIDType = __values;
      if (i >= arrayOfEUIDType.length) {
        break;
      }
      if (arrayOfEUIDType[i].value() == paramInt) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public static EUIDType convert(String paramString)
  {
    int i = 0;
    for (;;)
    {
      EUIDType[] arrayOfEUIDType = __values;
      if (i >= arrayOfEUIDType.length) {
        break;
      }
      if (arrayOfEUIDType[i].toString().equals(paramString)) {
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\EUIDType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */