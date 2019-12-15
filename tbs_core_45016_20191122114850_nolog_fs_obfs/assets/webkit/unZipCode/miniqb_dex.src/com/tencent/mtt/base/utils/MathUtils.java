package com.tencent.mtt.base.utils;

public class MathUtils
{
  public static int ceilLog2(float paramFloat)
  {
    int i = 0;
    while (i < 31)
    {
      if (1 << i >= paramFloat) {
        return i;
      }
      i += 1;
    }
    return i;
  }
  
  public static float clamp(float paramFloat1, float paramFloat2, float paramFloat3)
  {
    if (paramFloat1 > paramFloat3) {
      return paramFloat3;
    }
    if (paramFloat1 < paramFloat2) {
      return paramFloat2;
    }
    return paramFloat1;
  }
  
  public static int clamp(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 > paramInt3) {
      return paramInt3;
    }
    if (paramInt1 < paramInt2) {
      return paramInt2;
    }
    return paramInt1;
  }
  
  public static long clamp(long paramLong1, long paramLong2, long paramLong3)
  {
    if (paramLong1 > paramLong3) {
      return paramLong3;
    }
    if (paramLong1 < paramLong2) {
      return paramLong2;
    }
    return paramLong1;
  }
  
  public static int floorLog2(float paramFloat)
  {
    int i = 0;
    while ((i < 31) && (1 << i <= paramFloat)) {
      i += 1;
    }
    return i - 1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\base\utils\MathUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */