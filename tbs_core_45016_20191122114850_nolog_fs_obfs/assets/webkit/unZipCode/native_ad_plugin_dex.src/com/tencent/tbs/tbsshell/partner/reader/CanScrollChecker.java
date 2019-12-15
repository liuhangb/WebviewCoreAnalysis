package com.tencent.tbs.tbsshell.partner.reader;

import android.view.View;
import android.view.ViewGroup;

public class CanScrollChecker
{
  public static boolean canScroll(View paramView, boolean paramBoolean1, boolean paramBoolean2, int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramView instanceof ViewGroup))
    {
      ViewGroup localViewGroup = (ViewGroup)paramView;
      int j = (int)(paramView.getScrollX() + 0.5F);
      int k = (int)(paramView.getScrollY() + 0.5F);
      int i = localViewGroup.getChildCount() - 1;
      while (i >= 0)
      {
        View localView = localViewGroup.getChildAt(i);
        if (localView.getVisibility() == 0)
        {
          int m = paramInt2 + j;
          if ((m >= localView.getLeft()) && (m < localView.getRight()))
          {
            int n = paramInt3 + k;
            if ((n >= localView.getTop()) && (n < localView.getBottom()) && (canScroll(localView, true, paramBoolean2, paramInt1, m - localView.getLeft(), n - localView.getTop()))) {
              return true;
            }
          }
        }
        i -= 1;
      }
    }
    boolean bool1 = paramView instanceof CanScrollInterface;
    boolean bool2 = false;
    if (bool1)
    {
      paramView = (CanScrollInterface)paramView;
      bool1 = bool2;
      if (paramBoolean1)
      {
        if (paramBoolean2)
        {
          bool1 = bool2;
          if (!paramView.verticalCanScroll(-paramInt1)) {
            break label225;
          }
        }
        else
        {
          bool1 = bool2;
          if (!paramView.horizontalCanScroll(-paramInt1)) {
            break label225;
          }
        }
        bool1 = true;
      }
      label225:
      return bool1;
    }
    return false;
  }
  
  public static abstract interface CanScrollInterface
  {
    public abstract boolean horizontalCanScroll(int paramInt);
    
    public abstract boolean verticalCanScroll(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\tbsshell\partner\reader\CanScrollChecker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */