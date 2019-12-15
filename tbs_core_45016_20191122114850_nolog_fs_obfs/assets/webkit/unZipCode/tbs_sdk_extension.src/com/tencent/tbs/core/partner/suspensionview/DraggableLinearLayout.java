package com.tencent.tbs.core.partner.suspensionview;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

public class DraggableLinearLayout
  extends LinearLayout
{
  public static final String LOGTAG = "DraggableLinearLayout";
  private int bottom;
  int[] boundys = null;
  private float endx;
  private float endy;
  private int hor;
  boolean isMove = false;
  private int left;
  int moveThreshold = 10;
  private int right;
  private float startx;
  private float starty;
  private int top;
  private int ver;
  
  private DraggableLinearLayout(Context paramContext)
  {
    super(paramContext);
  }
  
  public DraggableLinearLayout(Context paramContext, int[] paramArrayOfInt)
  {
    super(paramContext);
    this.boundys = paramArrayOfInt;
    setOrientation(1);
  }
  
  private void adjustAndLayout(boolean paramBoolean)
  {
    Object localObject = this.boundys;
    int j = localObject[0];
    int i = localObject[1];
    int k = this.left;
    if (k < 0)
    {
      this.right -= k;
      this.left = 0;
    }
    k = this.top;
    if (k < 0)
    {
      this.bottom -= k;
      this.top = 0;
    }
    k = this.right;
    if (k > j)
    {
      this.left -= k - j;
      this.right = j;
    }
    j = this.bottom;
    if (j > i)
    {
      this.top -= j - i;
      this.bottom = i;
    }
    if (paramBoolean)
    {
      if ((getLayoutParams() instanceof AbsoluteLayout.LayoutParams))
      {
        localObject = (AbsoluteLayout.LayoutParams)getLayoutParams();
        ((AbsoluteLayout.LayoutParams)localObject).x = this.left;
        ((AbsoluteLayout.LayoutParams)localObject).y = this.top;
        setLayoutParams((ViewGroup.LayoutParams)localObject);
        return;
      }
      if ((getLayoutParams() instanceof FrameLayout.LayoutParams))
      {
        localObject = (FrameLayout.LayoutParams)getLayoutParams();
        ((FrameLayout.LayoutParams)localObject).gravity = 0;
        ((FrameLayout.LayoutParams)localObject).setMargins(this.left, this.top, 0, 0);
        setLayoutParams((ViewGroup.LayoutParams)localObject);
        return;
      }
    }
    try
    {
      localObject = (ViewGroup.MarginLayoutParams)getLayoutParams();
      ((ViewGroup.MarginLayoutParams)localObject).rightMargin = (this.boundys[0] - this.left);
      ((ViewGroup.MarginLayoutParams)localObject).bottomMargin = (this.boundys[1] - this.top);
      setLayoutParams((ViewGroup.LayoutParams)localObject);
      return;
    }
    catch (Exception localException) {}
    layout(this.left, this.top, this.right, this.bottom);
    return;
  }
  
  public void expandLayout(int paramInt1, int paramInt2)
  {
    this.left = getLeft();
    this.top = getTop();
    this.right = getRight();
    this.bottom = getBottom();
    this.right += paramInt1;
    this.bottom += paramInt2;
    this.top -= paramInt2;
    this.bottom -= paramInt2;
    adjustAndLayout(true);
  }
  
  public int[] getBoundys()
  {
    return this.boundys;
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    requestFocusFromTouch();
    int i = paramMotionEvent.getAction();
    if (i != 0)
    {
      if (i != 2) {
        return false;
      }
      this.endx = paramMotionEvent.getX();
      this.endy = paramMotionEvent.getY();
      if ((Math.abs(this.endx - this.startx) <= this.moveThreshold) && (Math.abs(this.endy - this.starty) <= this.moveThreshold)) {
        return false;
      }
      this.isMove = true;
      return true;
    }
    this.startx = paramMotionEvent.getX();
    this.starty = paramMotionEvent.getY();
    this.isMove = false;
    return false;
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default: 
      break;
    case 2: 
      this.endx = paramMotionEvent.getX();
      this.endy = paramMotionEvent.getY();
      this.hor = ((int)(this.endx - this.startx));
      this.ver = ((int)(this.endy - this.starty));
      if ((this.hor != 0) || (this.ver != 0)) {
        tryLayout(this.hor, this.ver);
      }
      break;
    case 1: 
      if (this.isMove) {
        shrinkMedue(getWidth(), getHeight(), 0);
      }
      break;
    case 0: 
      this.startx = paramMotionEvent.getX();
      this.starty = paramMotionEvent.getY();
    }
    return true;
  }
  
  public void reset()
  {
    this.left = 0;
    this.top = 0;
    this.right = 0;
    this.bottom = 0;
  }
  
  public void setBoundys(int[] paramArrayOfInt)
  {
    this.boundys = paramArrayOfInt;
  }
  
  public void shrinkMedue(int paramInt1, int paramInt2, int paramInt3)
  {
    int i;
    if (getLeft() > this.boundys[0] / 2) {
      i = 1;
    } else {
      i = 0;
    }
    if (i != 0)
    {
      int[] arrayOfInt = this.boundys;
      this.left = (arrayOfInt[0] - paramInt1);
      this.right = arrayOfInt[0];
    }
    else
    {
      this.left = 0;
      this.right = paramInt1;
    }
    this.top = (getTop() + paramInt3);
    this.bottom = (getTop() + paramInt2 + paramInt3);
    adjustAndLayout(true);
  }
  
  public void tryLayout(int paramInt1, int paramInt2)
  {
    this.left = (getLeft() + paramInt1);
    this.top = (getTop() + paramInt2);
    this.right = (getRight() + paramInt1);
    this.bottom = (getBottom() + paramInt2);
    adjustAndLayout(false);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\suspensionview\DraggableLinearLayout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */