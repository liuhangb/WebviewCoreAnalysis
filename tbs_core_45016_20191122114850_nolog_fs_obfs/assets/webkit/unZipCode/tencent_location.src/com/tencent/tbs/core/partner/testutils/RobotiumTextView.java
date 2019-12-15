package com.tencent.tbs.core.partner.testutils;

import android.content.Context;
import android.widget.TextView;

class RobotiumTextView
  extends TextView
{
  private int locationX = 0;
  private int locationY = 0;
  
  public RobotiumTextView(Context paramContext)
  {
    super(paramContext);
  }
  
  public RobotiumTextView(Context paramContext, String paramString, int paramInt1, int paramInt2)
  {
    super(paramContext);
    setText(paramString);
    setLocationX(paramInt1);
    setLocationY(paramInt2);
  }
  
  public void getLocationOnScreen(int[] paramArrayOfInt)
  {
    paramArrayOfInt[0] = this.locationX;
    paramArrayOfInt[1] = this.locationY;
  }
  
  public void setLocationX(int paramInt)
  {
    this.locationX = paramInt;
  }
  
  public void setLocationY(int paramInt)
  {
    this.locationY = paramInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\testutils\RobotiumTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */