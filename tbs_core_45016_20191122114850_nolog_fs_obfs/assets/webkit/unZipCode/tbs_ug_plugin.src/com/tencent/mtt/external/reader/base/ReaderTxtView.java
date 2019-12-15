package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.widget.FrameLayout;

public class ReaderTxtView
  extends ReaderDefaultView
{
  private int e = 1001;
  
  public ReaderTxtView(Context paramContext)
  {
    super(paramContext);
  }
  
  public int create()
  {
    super.create();
    doCommand(2, Boolean.valueOf(false), null);
    doCommand(9, Integer.valueOf(this.mReaderConfig.font_size), null);
    doCommand(23, Boolean.valueOf(this.mReaderConfig.scroll_mode), null);
    return 0;
  }
  
  protected boolean onOtherEvent(int paramInt, Object paramObject1, Object paramObject2)
  {
    if (paramInt != 5015) {
      return false;
    }
    if (this.mReaderController != null) {
      this.mReaderController.b(null);
    }
    return true;
  }
  
  protected void onSingleTap(int paramInt1, int paramInt2)
  {
    if (!this.mFeatureView.isAnimating())
    {
      if (this.mReaderController.isBarAnimation()) {
        return;
      }
      if (this.mReaderConfig.scroll_mode)
      {
        super.onSingleTap(paramInt1, paramInt2);
        return;
      }
      if (this.mFeatureView.MenuisShow())
      {
        this.mFeatureView.showToastView(2);
        this.mFeatureView.menuHide(true);
        return;
      }
      int i = getFrameLayout().getWidth() / 3;
      int j = getFrameLayout().getHeight() / 3;
      if (paramInt1 > i)
      {
        int k = i * 2;
        if ((paramInt1 > k) || (paramInt2 >= j))
        {
          if ((paramInt1 < k) && ((paramInt1 < i) || (paramInt2 <= j * 2)))
          {
            super.onSingleTap(paramInt1, paramInt2);
            return;
          }
          doCommand(17, null, null);
          return;
        }
      }
      doCommand(18, null, null);
      return;
    }
  }
  
  public void setMenuId(int paramInt)
  {
    this.e = paramInt;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderTxtView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */