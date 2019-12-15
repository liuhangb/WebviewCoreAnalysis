package org.chromium.components.web_contents_delegate_android;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.widget.Button;

public class ColorPickerMoreButton
  extends Button
{
  private Paint a;
  
  public ColorPickerMoreButton(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    a();
  }
  
  public ColorPickerMoreButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    a();
  }
  
  public void a()
  {
    this.a = new Paint();
    this.a.setStyle(Paint.Style.STROKE);
    this.a.setColor(-1);
    this.a.setStrokeWidth(1.0F);
    this.a.setAntiAlias(false);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    paramCanvas.drawRect(0.5F, 0.5F, getWidth() - 1.5F, getHeight() - 1.5F, this.a);
    super.onDraw(paramCanvas);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\components\web_contents_delegate_android\ColorPickerMoreButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */