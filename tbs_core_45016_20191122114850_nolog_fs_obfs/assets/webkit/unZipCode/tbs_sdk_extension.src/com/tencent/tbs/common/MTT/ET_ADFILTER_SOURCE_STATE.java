package com.tencent.tbs.common.MTT;

import java.io.Serializable;

public final class ET_ADFILTER_SOURCE_STATE
  implements Serializable
{
  public static final ET_ADFILTER_SOURCE_STATE ET_AFS_STATE_ADD;
  public static final ET_ADFILTER_SOURCE_STATE ET_AFS_STATE_DEL = new ET_ADFILTER_SOURCE_STATE(4, 4, "ET_AFS_STATE_DEL");
  public static final ET_ADFILTER_SOURCE_STATE ET_AFS_STATE_DIFF;
  public static final ET_ADFILTER_SOURCE_STATE ET_AFS_STATE_EQ;
  public static final ET_ADFILTER_SOURCE_STATE ET_AFS_STATE_ERR;
  public static final int _ET_AFS_STATE_ADD = 1;
  public static final int _ET_AFS_STATE_DEL = 4;
  public static final int _ET_AFS_STATE_DIFF = 2;
  public static final int _ET_AFS_STATE_EQ = 3;
  public static final int _ET_AFS_STATE_ERR = 0;
  private static ET_ADFILTER_SOURCE_STATE[] __values = new ET_ADFILTER_SOURCE_STATE[5];
  private String __T = new String();
  private int __value;
  
  static
  {
    ET_AFS_STATE_ERR = new ET_ADFILTER_SOURCE_STATE(0, 0, "ET_AFS_STATE_ERR");
    ET_AFS_STATE_ADD = new ET_ADFILTER_SOURCE_STATE(1, 1, "ET_AFS_STATE_ADD");
    ET_AFS_STATE_DIFF = new ET_ADFILTER_SOURCE_STATE(2, 2, "ET_AFS_STATE_DIFF");
    ET_AFS_STATE_EQ = new ET_ADFILTER_SOURCE_STATE(3, 3, "ET_AFS_STATE_EQ");
  }
  
  private ET_ADFILTER_SOURCE_STATE(int paramInt1, int paramInt2, String paramString)
  {
    this.__T = paramString;
    this.__value = paramInt2;
    __values[paramInt1] = this;
  }
  
  public static ET_ADFILTER_SOURCE_STATE convert(int paramInt)
  {
    int i = 0;
    for (;;)
    {
      ET_ADFILTER_SOURCE_STATE[] arrayOfET_ADFILTER_SOURCE_STATE = __values;
      if (i >= arrayOfET_ADFILTER_SOURCE_STATE.length) {
        break;
      }
      if (arrayOfET_ADFILTER_SOURCE_STATE[i].value() == paramInt) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public static ET_ADFILTER_SOURCE_STATE convert(String paramString)
  {
    int i = 0;
    for (;;)
    {
      ET_ADFILTER_SOURCE_STATE[] arrayOfET_ADFILTER_SOURCE_STATE = __values;
      if (i >= arrayOfET_ADFILTER_SOURCE_STATE.length) {
        break;
      }
      if (arrayOfET_ADFILTER_SOURCE_STATE[i].toString().equals(paramString)) {
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\ET_ADFILTER_SOURCE_STATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */