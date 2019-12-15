package org.chromium.android_webview;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.widget.EdgeEffect;

public class OverScrollGlow
{
  private int jdField_a_of_type_Int;
  private View jdField_a_of_type_AndroidViewView;
  private EdgeEffect jdField_a_of_type_AndroidWidgetEdgeEffect;
  private boolean jdField_a_of_type_Boolean;
  private int jdField_b_of_type_Int;
  private EdgeEffect jdField_b_of_type_AndroidWidgetEdgeEffect;
  private EdgeEffect c;
  private EdgeEffect d;
  
  public OverScrollGlow(Context paramContext, View paramView)
  {
    this.jdField_a_of_type_AndroidViewView = paramView;
    this.jdField_a_of_type_AndroidWidgetEdgeEffect = new EdgeEffect(paramContext);
    this.jdField_b_of_type_AndroidWidgetEdgeEffect = new EdgeEffect(paramContext);
    this.c = new EdgeEffect(paramContext);
    this.d = new EdgeEffect(paramContext);
  }
  
  public void absorbGlow(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, float paramFloat)
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    if ((paramInt6 > 0) || (this.jdField_a_of_type_AndroidViewView.getOverScrollMode() == 0)) {
      if ((paramInt2 < 0) && (paramInt4 >= 0))
      {
        this.jdField_a_of_type_AndroidWidgetEdgeEffect.onAbsorb((int)paramFloat);
        if (!this.jdField_b_of_type_AndroidWidgetEdgeEffect.isFinished()) {
          this.jdField_b_of_type_AndroidWidgetEdgeEffect.onRelease();
        }
      }
      else if ((paramInt2 > paramInt6) && (paramInt4 <= paramInt6))
      {
        this.jdField_b_of_type_AndroidWidgetEdgeEffect.onAbsorb((int)paramFloat);
        if (!this.jdField_a_of_type_AndroidWidgetEdgeEffect.isFinished()) {
          this.jdField_a_of_type_AndroidWidgetEdgeEffect.onRelease();
        }
      }
    }
    if (paramInt5 > 0) {
      if ((paramInt1 < 0) && (paramInt3 >= 0))
      {
        this.c.onAbsorb((int)paramFloat);
        if (!this.d.isFinished()) {
          this.d.onRelease();
        }
      }
      else if ((paramInt1 > paramInt5) && (paramInt3 <= paramInt5))
      {
        this.d.onAbsorb((int)paramFloat);
        if (!this.c.isFinished()) {
          this.c.onRelease();
        }
      }
    }
  }
  
  public boolean drawEdgeGlows(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    int i = this.jdField_a_of_type_AndroidViewView.getScrollX();
    int j = this.jdField_a_of_type_AndroidViewView.getScrollY();
    int k = this.jdField_a_of_type_AndroidViewView.getWidth();
    int m = this.jdField_a_of_type_AndroidViewView.getHeight();
    int n;
    if (!this.jdField_a_of_type_AndroidWidgetEdgeEffect.isFinished())
    {
      n = paramCanvas.save();
      paramCanvas.translate(i, Math.min(0, j));
      this.jdField_a_of_type_AndroidWidgetEdgeEffect.setSize(k, m);
      bool2 = this.jdField_a_of_type_AndroidWidgetEdgeEffect.draw(paramCanvas) | false;
      paramCanvas.restoreToCount(n);
    }
    else
    {
      bool2 = false;
    }
    boolean bool1 = bool2;
    if (!this.jdField_b_of_type_AndroidWidgetEdgeEffect.isFinished())
    {
      n = paramCanvas.save();
      paramCanvas.translate(-k + i, Math.max(paramInt2, j) + m);
      paramCanvas.rotate(180.0F, k, 0.0F);
      this.jdField_b_of_type_AndroidWidgetEdgeEffect.setSize(k, m);
      bool1 = bool2 | this.jdField_b_of_type_AndroidWidgetEdgeEffect.draw(paramCanvas);
      paramCanvas.restoreToCount(n);
    }
    boolean bool2 = bool1;
    if (!this.c.isFinished())
    {
      paramInt2 = paramCanvas.save();
      paramCanvas.rotate(270.0F);
      paramCanvas.translate(-m - j, Math.min(0, i));
      this.c.setSize(m, k);
      bool2 = bool1 | this.c.draw(paramCanvas);
      paramCanvas.restoreToCount(paramInt2);
    }
    bool1 = bool2;
    if (!this.d.isFinished())
    {
      paramInt2 = paramCanvas.save();
      paramCanvas.rotate(90.0F);
      paramCanvas.translate(j, -(Math.max(i, paramInt1) + k));
      this.d.setSize(m, k);
      bool1 = bool2 | this.d.draw(paramCanvas);
      paramCanvas.restoreToCount(paramInt2);
    }
    return bool1;
  }
  
  public boolean isAnimating()
  {
    return (!this.jdField_a_of_type_AndroidWidgetEdgeEffect.isFinished()) || (!this.jdField_b_of_type_AndroidWidgetEdgeEffect.isFinished()) || (!this.c.isFinished()) || (!this.d.isFinished());
  }
  
  public void pullGlow(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    if (!this.jdField_a_of_type_Boolean) {
      return;
    }
    if ((paramInt3 == this.jdField_a_of_type_AndroidViewView.getScrollX()) && (paramInt4 == this.jdField_a_of_type_AndroidViewView.getScrollY()))
    {
      if (paramInt5 > 0)
      {
        paramInt1 = this.jdField_a_of_type_Int;
        paramInt2 = paramInt3 + paramInt1;
        if (paramInt2 < 0)
        {
          this.c.onPull(paramInt1 / this.jdField_a_of_type_AndroidViewView.getWidth());
          if (!this.d.isFinished()) {
            this.d.onRelease();
          }
        }
        else if (paramInt2 > paramInt5)
        {
          this.d.onPull(paramInt1 / this.jdField_a_of_type_AndroidViewView.getWidth());
          if (!this.c.isFinished()) {
            this.c.onRelease();
          }
        }
        this.jdField_a_of_type_Int = 0;
      }
      if ((paramInt6 > 0) || (this.jdField_a_of_type_AndroidViewView.getOverScrollMode() == 0))
      {
        paramInt1 = this.jdField_b_of_type_Int;
        paramInt2 = paramInt4 + paramInt1;
        if (paramInt2 < 0)
        {
          this.jdField_a_of_type_AndroidWidgetEdgeEffect.onPull(paramInt1 / this.jdField_a_of_type_AndroidViewView.getHeight());
          if (!this.jdField_b_of_type_AndroidWidgetEdgeEffect.isFinished()) {
            this.jdField_b_of_type_AndroidWidgetEdgeEffect.onRelease();
          }
        }
        else if (paramInt2 > paramInt6)
        {
          this.jdField_b_of_type_AndroidWidgetEdgeEffect.onPull(paramInt1 / this.jdField_a_of_type_AndroidViewView.getHeight());
          if (!this.jdField_a_of_type_AndroidWidgetEdgeEffect.isFinished()) {
            this.jdField_a_of_type_AndroidWidgetEdgeEffect.onRelease();
          }
        }
        this.jdField_b_of_type_Int = 0;
      }
    }
  }
  
  public void releaseAll()
  {
    this.jdField_a_of_type_AndroidWidgetEdgeEffect.onRelease();
    this.jdField_b_of_type_AndroidWidgetEdgeEffect.onRelease();
    this.c.onRelease();
    this.d.onRelease();
  }
  
  public void setOverScrollDeltas(int paramInt1, int paramInt2)
  {
    this.jdField_a_of_type_Int += paramInt1;
    this.jdField_b_of_type_Int += paramInt2;
  }
  
  public void setShouldPull(boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\android_webview\OverScrollGlow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */