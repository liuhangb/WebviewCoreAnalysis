package com.tencent.common.utils;

public class QSize
{
  public int mHeight;
  public int mWidth;
  
  public QSize()
  {
    this.mWidth = 0;
    this.mHeight = 0;
  }
  
  public QSize(int paramInt1, int paramInt2)
  {
    this.mWidth = paramInt1;
    this.mHeight = paramInt2;
  }
  
  public void add(QSize paramQSize)
  {
    if (paramQSize == null) {
      return;
    }
    this.mWidth += paramQSize.mWidth;
    this.mHeight += paramQSize.mHeight;
  }
  
  public void add(short paramShort1, short paramShort2)
  {
    this.mWidth += paramShort1;
    this.mHeight += paramShort2;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool2 = false;
    if (paramObject == null) {
      return false;
    }
    paramObject = (QSize)paramObject;
    boolean bool1 = bool2;
    if (((QSize)paramObject).mHeight == this.mHeight)
    {
      bool1 = bool2;
      if (((QSize)paramObject).mWidth == this.mWidth) {
        bool1 = true;
      }
    }
    return bool1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\QSize.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */