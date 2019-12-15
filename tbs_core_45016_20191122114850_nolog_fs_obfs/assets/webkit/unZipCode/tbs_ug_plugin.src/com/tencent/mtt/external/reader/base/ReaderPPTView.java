package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.os.Bundle;

public class ReaderPPTView
  extends ReaderDefaultView
{
  private int e;
  private final int f = 0;
  private final int g = 1;
  
  public ReaderPPTView(Context paramContext)
  {
    super(paramContext);
  }
  
  public boolean askCanGoback()
  {
    if (this.e == 1)
    {
      this.e = 0;
      if (this.mFeatureView.MenuisShow()) {
        this.mFeatureView.menuHide(true);
      }
      Bundle localBundle = new Bundle();
      doCommand(20000, null, localBundle);
      return localBundle.getBoolean("handleBack");
    }
    return false;
  }
  
  public int create()
  {
    super.create();
    this.e = 0;
    this.mFeatureView.menuFocus();
    return 0;
  }
  
  protected boolean onOtherEvent(int paramInt, Object paramObject1, Object paramObject2)
  {
    if (paramInt != 19)
    {
      if (paramInt != 5035) {
        return false;
      }
      if (this.e == 0) {
        this.mFeatureView.setPPTScreenTime();
      }
      doCommand(22, null, null);
    }
    else if (((Integer)paramObject2).intValue() == 0)
    {
      this.mFeatureView.updateScreenLockTime();
    }
    return true;
  }
  
  protected void onScaleBegin()
  {
    if (this.mFeatureView.MenuisShow()) {
      this.mFeatureView.menuHide(true);
    }
  }
  
  protected void onScrollBegin()
  {
    if (this.mFeatureView.MenuisShow()) {
      this.mFeatureView.menuHide(true);
    }
  }
  
  protected void onSingleTap(int paramInt1, int paramInt2)
  {
    if (this.mFeatureView.isAnimating()) {
      return;
    }
    if (this.mFeatureView.MenuisShow())
    {
      if (this.e == 0)
      {
        this.mFeatureView.menuHide(true);
        return;
      }
      this.mFeatureView.menuHide(true);
      return;
    }
    if (this.e == 0)
    {
      this.mFeatureView.menuShow(true);
      return;
    }
    this.mFeatureView.menuShow(true);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderPPTView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */