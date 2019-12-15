package com.tencent.smtt.webkit.ui.zoomImg;

public class b
{
  public static int a(float paramFloat)
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
  
  public static int a(int paramInt1, int paramInt2, int paramInt3)
  {
    if (paramInt1 > paramInt3) {
      return paramInt3;
    }
    if (paramInt1 < paramInt2) {
      return paramInt2;
    }
    return paramInt1;
  }
  
  public static int b(float paramFloat)
  {
    int i = 0;
    while ((i < 31) && (1 << i <= paramFloat)) {
      i += 1;
    }
    return i - 1;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\zoomImg\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */