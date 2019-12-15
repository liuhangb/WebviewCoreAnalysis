package com.tencent.tbs.common.MTT;

import java.io.Serializable;

public final class ET_ADRULE_CODE
  implements Serializable
{
  public static final ET_ADRULE_CODE ET_ADRULE_SAME = new ET_ADRULE_CODE(1, 1, "ET_ADRULE_SAME");
  public static final ET_ADRULE_CODE ET_ADRULE_SUC;
  public static final int _ET_ADRULE_SAME = 1;
  public static final int _ET_ADRULE_SUC = 0;
  private static ET_ADRULE_CODE[] __values = new ET_ADRULE_CODE[2];
  private String __T = new String();
  private int __value;
  
  static
  {
    ET_ADRULE_SUC = new ET_ADRULE_CODE(0, 0, "ET_ADRULE_SUC");
  }
  
  private ET_ADRULE_CODE(int paramInt1, int paramInt2, String paramString)
  {
    this.__T = paramString;
    this.__value = paramInt2;
    __values[paramInt1] = this;
  }
  
  public static ET_ADRULE_CODE convert(int paramInt)
  {
    int i = 0;
    for (;;)
    {
      ET_ADRULE_CODE[] arrayOfET_ADRULE_CODE = __values;
      if (i >= arrayOfET_ADRULE_CODE.length) {
        break;
      }
      if (arrayOfET_ADRULE_CODE[i].value() == paramInt) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public static ET_ADRULE_CODE convert(String paramString)
  {
    int i = 0;
    for (;;)
    {
      ET_ADRULE_CODE[] arrayOfET_ADRULE_CODE = __values;
      if (i >= arrayOfET_ADRULE_CODE.length) {
        break;
      }
      if (arrayOfET_ADRULE_CODE[i].toString().equals(paramString)) {
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ET_ADRULE_CODE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */