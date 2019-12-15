package com.tencent.tbs.common.MTT;

import java.io.Serializable;

public final class ET_GET_FILE_TYPE
  implements Serializable
{
  public static final ET_GET_FILE_TYPE ET_DOWNLOAD = new ET_GET_FILE_TYPE(1, 1, "ET_DOWNLOAD");
  public static final ET_GET_FILE_TYPE ET_USE_DIRECT;
  public static final int _ET_DOWNLOAD = 1;
  public static final int _ET_USE_DIRECT = 0;
  private static ET_GET_FILE_TYPE[] __values = new ET_GET_FILE_TYPE[2];
  private String __T = new String();
  private int __value;
  
  static
  {
    ET_USE_DIRECT = new ET_GET_FILE_TYPE(0, 0, "ET_USE_DIRECT");
  }
  
  private ET_GET_FILE_TYPE(int paramInt1, int paramInt2, String paramString)
  {
    this.__T = paramString;
    this.__value = paramInt2;
    __values[paramInt1] = this;
  }
  
  public static ET_GET_FILE_TYPE convert(int paramInt)
  {
    int i = 0;
    for (;;)
    {
      ET_GET_FILE_TYPE[] arrayOfET_GET_FILE_TYPE = __values;
      if (i >= arrayOfET_GET_FILE_TYPE.length) {
        break;
      }
      if (arrayOfET_GET_FILE_TYPE[i].value() == paramInt) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public static ET_GET_FILE_TYPE convert(String paramString)
  {
    int i = 0;
    for (;;)
    {
      ET_GET_FILE_TYPE[] arrayOfET_GET_FILE_TYPE = __values;
      if (i >= arrayOfET_GET_FILE_TYPE.length) {
        break;
      }
      if (arrayOfET_GET_FILE_TYPE[i].toString().equals(paramString)) {
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ET_GET_FILE_TYPE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */