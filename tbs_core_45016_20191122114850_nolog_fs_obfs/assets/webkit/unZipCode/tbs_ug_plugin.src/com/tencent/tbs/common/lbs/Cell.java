package com.tencent.tbs.common.lbs;

public final class Cell
{
  public int iCellId = -1;
  public int iLac = -1;
  public short shMcc = -1;
  public short shMnc = -1;
  
  public Cell(short paramShort1, short paramShort2, int paramInt1, int paramInt2)
  {
    this.shMcc = paramShort1;
    this.shMnc = paramShort2;
    this.iLac = paramInt1;
    this.iCellId = paramInt2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\lbs\Cell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */