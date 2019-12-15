package com.tencent.tbs.core.partner.ad;

import java.io.PrintStream;

public class AdInfoUnit
  implements Cloneable
{
  public static final int AD_OPEN_TYPE_QB = 1;
  public static final int AD_OPEN_TYPE_WEBVIEW = 2;
  public static final int AD_POSITION_BOTTOM = 1;
  public static final int AD_POSITION_TOP = 2;
  public static final int AD_TYPE_BUBBLE = 4;
  public static final int AD_TYPE_BUBBLE_MINIQB = 32;
  public static final int AD_TYPE_MINIPROGRAM = 128;
  public static final int AD_TYPE_NONE = 0;
  public static final int AD_TYPE_QBICON_QQ = 16;
  public static final int AD_TYPE_SPLICE = 8;
  public static final int AD_TYPE_SPLICE_MINIQB = 2;
  public static final int AD_TYPE_SPLICE_QQ = 1;
  private String mAdId;
  private int mAdPosition;
  private String mMainUrl;
  private int mOpenStyle;
  private int mShowTime;
  private String mStatKey;
  private int mType;
  private String mUrl;
  private String mUrlMd5;
  
  public AdInfoUnit()
  {
    this.mType = 0;
    this.mStatKey = "0";
    this.mUrl = "0";
    this.mUrlMd5 = "0";
    this.mShowTime = 0;
    this.mAdId = "0";
    this.mAdPosition = 0;
    this.mOpenStyle = 0;
  }
  
  public AdInfoUnit(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4, int paramInt3, int paramInt4)
  {
    this.mType = paramInt1;
    this.mStatKey = paramString1;
    this.mUrl = paramString2;
    this.mUrlMd5 = paramString3;
    this.mShowTime = paramInt2;
    this.mAdId = paramString4;
    this.mAdPosition = paramInt3;
    this.mOpenStyle = paramInt4;
  }
  
  public static int openTypeStr2TypeId(String paramString)
  {
    if ("1".equals(paramString)) {
      return 1;
    }
    if ("2".equals(paramString)) {
      return 2;
    }
    return 0;
  }
  
  public static int posStr2posId(String paramString)
  {
    if ("bottom".equals(paramString)) {
      return 1;
    }
    if ("top".equals(paramString)) {
      return 2;
    }
    return 0;
  }
  
  public static int typeStr2TypeId(String paramString)
  {
    if ("splice".equals(paramString)) {
      return 8;
    }
    if ("bubble".equals(paramString)) {
      return 4;
    }
    return 0;
  }
  
  public Object clone()
  {
    try
    {
      AdInfoUnit localAdInfoUnit = (AdInfoUnit)super.clone();
      return localAdInfoUnit;
    }
    catch (CloneNotSupportedException localCloneNotSupportedException)
    {
      System.out.println(localCloneNotSupportedException.toString());
    }
    return null;
  }
  
  public String getAdId()
  {
    return this.mAdId;
  }
  
  public int getAdPosition()
  {
    return this.mAdPosition;
  }
  
  public String getAdPositionString()
  {
    int i = this.mAdPosition;
    if (1 == i) {
      return "bottom";
    }
    if (2 == i) {
      return "top";
    }
    return "";
  }
  
  public String getMainUrl()
  {
    return this.mMainUrl;
  }
  
  public int getOpenStyle()
  {
    return this.mOpenStyle;
  }
  
  public int getShowTime()
  {
    return this.mShowTime;
  }
  
  public String getStatKey()
  {
    return this.mStatKey;
  }
  
  public int getType()
  {
    return this.mType;
  }
  
  public String getTypeString()
  {
    int i = this.mType;
    if (8 == (i & 0x8)) {
      return "splice";
    }
    if (4 == (i & 0x4)) {
      return "bubble";
    }
    return "";
  }
  
  public String getUrl()
  {
    return this.mUrl;
  }
  
  public String getUrlMd5()
  {
    return this.mUrlMd5;
  }
  
  public void setAdPosition(int paramInt)
  {
    this.mAdPosition = paramInt;
  }
  
  public void setMainUrl(String paramString)
  {
    this.mMainUrl = paramString;
  }
  
  public void setOpenStyle(int paramInt)
  {
    this.mOpenStyle = paramInt;
  }
  
  public void setUrlMd5(String paramString)
  {
    this.mUrlMd5 = paramString;
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(this.mType);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.mStatKey);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.mUrl);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.mUrlMd5);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.mShowTime);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.mAdId);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.mAdPosition);
    localStringBuilder.append(", ");
    localStringBuilder.append(this.mOpenStyle);
    return localStringBuilder.toString();
  }
  
  public void updateAdInfo(int paramInt1, String paramString1, String paramString2, String paramString3, int paramInt2, String paramString4, int paramInt3, int paramInt4)
  {
    this.mType = paramInt1;
    this.mStatKey = paramString1;
    this.mUrl = paramString2;
    this.mUrlMd5 = paramString3;
    this.mShowTime = paramInt2;
    this.mAdId = paramString4;
    this.mAdPosition = paramInt3;
    this.mOpenStyle = paramInt4;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\ad\AdInfoUnit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */