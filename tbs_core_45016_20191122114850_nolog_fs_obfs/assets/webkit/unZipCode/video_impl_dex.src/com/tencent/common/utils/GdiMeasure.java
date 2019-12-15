package com.tencent.common.utils;

public abstract class GdiMeasure
{
  public abstract int breakText(String paramString, int paramInt, float[] paramArrayOfFloat);
  
  public abstract short getCharHeight();
  
  public abstract short getCharWidth();
  
  public abstract short getStringWidth(String paramString);
  
  public abstract void getStringWidthHeight(String paramString, QSize paramQSize);
  
  public abstract int getStringWidthInt(String paramString);
  
  public abstract void setFontSize(int paramInt);
  
  public abstract void setFontTypeface(boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void setUnderline(boolean paramBoolean);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\common\utils\GdiMeasure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */