package com.tencent.tbs.common.MTT;

import java.io.Serializable;

public final class ET_ADFILTER_ERROR_TYPE
  implements Serializable
{
  public static final ET_ADFILTER_ERROR_TYPE ET_AF_OK;
  public static final ET_ADFILTER_ERROR_TYPE ET_AF_OVERCHARGE = new ET_ADFILTER_ERROR_TYPE(2, -102, "ET_AF_OVERCHARGE");
  public static final ET_ADFILTER_ERROR_TYPE ET_AF_SERVER_ERR;
  public static final int _ET_AF_OK = 0;
  public static final int _ET_AF_OVERCHARGE = -102;
  public static final int _ET_AF_SERVER_ERR = -101;
  private static ET_ADFILTER_ERROR_TYPE[] __values = new ET_ADFILTER_ERROR_TYPE[3];
  private String __T = new String();
  private int __value;
  
  static
  {
    ET_AF_OK = new ET_ADFILTER_ERROR_TYPE(0, 0, "ET_AF_OK");
    ET_AF_SERVER_ERR = new ET_ADFILTER_ERROR_TYPE(1, -101, "ET_AF_SERVER_ERR");
  }
  
  private ET_ADFILTER_ERROR_TYPE(int paramInt1, int paramInt2, String paramString)
  {
    this.__T = paramString;
    this.__value = paramInt2;
    __values[paramInt1] = this;
  }
  
  public static ET_ADFILTER_ERROR_TYPE convert(int paramInt)
  {
    int i = 0;
    for (;;)
    {
      ET_ADFILTER_ERROR_TYPE[] arrayOfET_ADFILTER_ERROR_TYPE = __values;
      if (i >= arrayOfET_ADFILTER_ERROR_TYPE.length) {
        break;
      }
      if (arrayOfET_ADFILTER_ERROR_TYPE[i].value() == paramInt) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public static ET_ADFILTER_ERROR_TYPE convert(String paramString)
  {
    int i = 0;
    for (;;)
    {
      ET_ADFILTER_ERROR_TYPE[] arrayOfET_ADFILTER_ERROR_TYPE = __values;
      if (i >= arrayOfET_ADFILTER_ERROR_TYPE.length) {
        break;
      }
      if (arrayOfET_ADFILTER_ERROR_TYPE[i].toString().equals(paramString)) {
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ET_ADFILTER_ERROR_TYPE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */