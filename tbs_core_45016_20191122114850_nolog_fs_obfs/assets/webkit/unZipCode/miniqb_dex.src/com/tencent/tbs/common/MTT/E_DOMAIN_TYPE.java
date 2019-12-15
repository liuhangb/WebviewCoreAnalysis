package com.tencent.tbs.common.MTT;

import java.io.Serializable;

public final class E_DOMAIN_TYPE
  implements Serializable
{
  public static final E_DOMAIN_TYPE E_DOMAIN_ADBLOCK_BLACK = new E_DOMAIN_TYPE(2, 2, "E_DOMAIN_ADBLOCK_BLACK");
  public static final E_DOMAIN_TYPE E_DOMAIN_ADBLOCK_WHITE;
  public static final E_DOMAIN_TYPE E_DOMAIN_UNKNOWN;
  public static final int _E_DOMAIN_ADBLOCK_BLACK = 2;
  public static final int _E_DOMAIN_ADBLOCK_WHITE = 1;
  public static final int _E_DOMAIN_UNKNOWN = 0;
  private static E_DOMAIN_TYPE[] __values = new E_DOMAIN_TYPE[3];
  private String __T = new String();
  private int __value;
  
  static
  {
    E_DOMAIN_UNKNOWN = new E_DOMAIN_TYPE(0, 0, "E_DOMAIN_UNKNOWN");
    E_DOMAIN_ADBLOCK_WHITE = new E_DOMAIN_TYPE(1, 1, "E_DOMAIN_ADBLOCK_WHITE");
  }
  
  private E_DOMAIN_TYPE(int paramInt1, int paramInt2, String paramString)
  {
    this.__T = paramString;
    this.__value = paramInt2;
    __values[paramInt1] = this;
  }
  
  public static E_DOMAIN_TYPE convert(int paramInt)
  {
    int i = 0;
    for (;;)
    {
      E_DOMAIN_TYPE[] arrayOfE_DOMAIN_TYPE = __values;
      if (i >= arrayOfE_DOMAIN_TYPE.length) {
        break;
      }
      if (arrayOfE_DOMAIN_TYPE[i].value() == paramInt) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public static E_DOMAIN_TYPE convert(String paramString)
  {
    int i = 0;
    for (;;)
    {
      E_DOMAIN_TYPE[] arrayOfE_DOMAIN_TYPE = __values;
      if (i >= arrayOfE_DOMAIN_TYPE.length) {
        break;
      }
      if (arrayOfE_DOMAIN_TYPE[i].toString().equals(paramString)) {
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\E_DOMAIN_TYPE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */