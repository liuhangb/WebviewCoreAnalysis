package org.chromium.ui;

import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

public class HorizontalListDividerDrawable
  extends LayerDrawable
{
  protected void onBoundsChange(Rect paramRect)
  {
    int i = getDrawable(0).getIntrinsicHeight();
    setLayerInset(0, 0, paramRect.height() - i, 0, 0);
    super.onBoundsChange(paramRect);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\HorizontalListDividerDrawable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */