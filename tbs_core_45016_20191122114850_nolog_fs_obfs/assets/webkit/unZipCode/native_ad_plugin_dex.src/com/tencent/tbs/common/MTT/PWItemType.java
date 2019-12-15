package com.tencent.tbs.common.MTT;

import java.io.Serializable;

public final class PWItemType
  implements Serializable
{
  public static final PWItemType PWItemType_AD = new PWItemType(7, 107, "PWItemType_AD");
  public static final PWItemType PWItemType_Comment;
  public static final PWItemType PWItemType_Game;
  public static final PWItemType PWItemType_Headline;
  public static final PWItemType PWItemType_HotWechat;
  public static final PWItemType PWItemType_Human;
  public static final PWItemType PWItemType_News;
  public static final PWItemType PWItemType_Video;
  public static final int _PWItemType_AD = 107;
  public static final int _PWItemType_Comment = 105;
  public static final int _PWItemType_Game = 104;
  public static final int _PWItemType_Headline = 103;
  public static final int _PWItemType_HotWechat = 102;
  public static final int _PWItemType_Human = 10;
  public static final int _PWItemType_News = 106;
  public static final int _PWItemType_Video = 101;
  private static PWItemType[] __values = new PWItemType[8];
  private String __T = new String();
  private int __value;
  
  static
  {
    PWItemType_Human = new PWItemType(0, 10, "PWItemType_Human");
    PWItemType_Video = new PWItemType(1, 101, "PWItemType_Video");
    PWItemType_HotWechat = new PWItemType(2, 102, "PWItemType_HotWechat");
    PWItemType_Headline = new PWItemType(3, 103, "PWItemType_Headline");
    PWItemType_Game = new PWItemType(4, 104, "PWItemType_Game");
    PWItemType_Comment = new PWItemType(5, 105, "PWItemType_Comment");
    PWItemType_News = new PWItemType(6, 106, "PWItemType_News");
  }
  
  private PWItemType(int paramInt1, int paramInt2, String paramString)
  {
    this.__T = paramString;
    this.__value = paramInt2;
    __values[paramInt1] = this;
  }
  
  public static PWItemType convert(int paramInt)
  {
    int i = 0;
    for (;;)
    {
      PWItemType[] arrayOfPWItemType = __values;
      if (i >= arrayOfPWItemType.length) {
        break;
      }
      if (arrayOfPWItemType[i].value() == paramInt) {
        return __values[i];
      }
      i += 1;
    }
    return null;
  }
  
  public static PWItemType convert(String paramString)
  {
    int i = 0;
    for (;;)
    {
      PWItemType[] arrayOfPWItemType = __values;
      if (i >= arrayOfPWItemType.length) {
        break;
      }
      if (arrayOfPWItemType[i].toString().equals(paramString)) {
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


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\MTT\PWItemType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */