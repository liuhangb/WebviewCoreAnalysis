package com.tencent.smtt.util;

import android.graphics.Rect;
import android.view.View;

public class n
{
  public static final byte[] a = { -25, -101, -115, 1, 47, 7, -27, -59, 18, -128, 123, 79, -44, 37, 46, 115 };
  
  public static int a(View paramView)
  {
    if (paramView == null) {
      return 0;
    }
    try
    {
      Rect localRect = new Rect();
      paramView.getWindowVisibleDisplayFrame(localRect);
      int i = localRect.top;
      return i;
    }
    catch (Exception paramView)
    {
      paramView.printStackTrace();
    }
    return 0;
  }
  
  public static byte[] a(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    try
    {
      paramArrayOfByte = new a().b(paramArrayOfByte, a);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte)
    {
      paramArrayOfByte.printStackTrace();
    }
    return null;
  }
  
  public static byte[] b(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      return null;
    }
    try
    {
      paramArrayOfByte = new a().a(paramArrayOfByte, a);
      return paramArrayOfByte;
    }
    catch (Exception paramArrayOfByte) {}
    return null;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\util\n.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */