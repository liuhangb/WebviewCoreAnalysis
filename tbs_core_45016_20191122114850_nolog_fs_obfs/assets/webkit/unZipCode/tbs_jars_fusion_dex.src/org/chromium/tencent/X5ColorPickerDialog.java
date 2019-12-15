package org.chromium.tencent;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import org.chromium.components.web_contents_delegate_android.ColorSuggestion;
import org.chromium.components.web_contents_delegate_android.OnColorChangedListener;

public class X5ColorPickerDialog
  extends Dialog
  implements OnColorChangedListener
{
  private ColorPickerView mColorPickerView;
  private final int mInitialColor;
  private final OnColorChangedListener mListener;
  
  public X5ColorPickerDialog(Context paramContext, OnColorChangedListener paramOnColorChangedListener, int paramInt, ColorSuggestion[] paramArrayOfColorSuggestion)
  {
    super(paramContext, 0);
    this.mListener = paramOnColorChangedListener;
    this.mInitialColor = paramInt;
  }
  
  private void tryNotifyColorSet(int paramInt)
  {
    OnColorChangedListener localOnColorChangedListener = this.mListener;
    if (localOnColorChangedListener != null) {
      localOnColorChangedListener.onColorChanged(paramInt);
    }
  }
  
  public void onColorChanged(int paramInt)
  {
    tryNotifyColorSet(paramInt);
    dismiss();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    this.mColorPickerView = new ColorPickerView(getContext(), this, this.mInitialColor);
    setContentView(this.mColorPickerView);
    setOnCancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        paramAnonymousDialogInterface = X5ColorPickerDialog.this;
        paramAnonymousDialogInterface.onColorChanged(X5ColorPickerDialog.ColorPickerView.access$100(paramAnonymousDialogInterface.mColorPickerView).getColor());
      }
    });
    setTitle("Pick a Color");
  }
  
  private static class ColorPickerView
    extends View
  {
    private static final int CENTER_RADIUS = 50;
    private static final int CENTER_X = 200;
    private static final int CENTER_Y = 200;
    private static final float PI = 3.1415925F;
    private Paint mCenterPaint;
    private final int[] mColors;
    private boolean mHighlightCenter;
    private OnColorChangedListener mListener;
    private Paint mPaint;
    private boolean mTrackingCenter;
    
    ColorPickerView(Context paramContext, OnColorChangedListener paramOnColorChangedListener, int paramInt)
    {
      super();
      this.mListener = paramOnColorChangedListener;
      this.mColors = new int[] { -65536, -65281, -16776961, -16711681, -16711936, 65280, -65536 };
      paramContext = new SweepGradient(0.0F, 0.0F, this.mColors, null);
      this.mPaint = new Paint(1);
      this.mPaint.setShader(paramContext);
      this.mPaint.setStyle(Paint.Style.STROKE);
      this.mPaint.setStrokeWidth(50.0F);
      this.mCenterPaint = new Paint(1);
      this.mCenterPaint.setColor(paramInt);
      this.mCenterPaint.setStrokeWidth(10.0F);
    }
    
    private int ave(int paramInt1, int paramInt2, float paramFloat)
    {
      return paramInt1 + Math.round(paramFloat * (paramInt2 - paramInt1));
    }
    
    private int floatToByte(float paramFloat)
    {
      return Math.round(paramFloat);
    }
    
    private int interpColor(int[] paramArrayOfInt, float paramFloat)
    {
      if (paramFloat <= 0.0F) {
        return paramArrayOfInt[0];
      }
      if (paramFloat >= 1.0F) {
        return paramArrayOfInt[(paramArrayOfInt.length - 1)];
      }
      paramFloat *= (paramArrayOfInt.length - 1);
      int j = (int)paramFloat;
      paramFloat -= j;
      int i = paramArrayOfInt[j];
      j = paramArrayOfInt[(j + 1)];
      return Color.argb(ave(Color.alpha(i), Color.alpha(j), paramFloat), ave(Color.red(i), Color.red(j), paramFloat), ave(Color.green(i), Color.green(j), paramFloat), ave(Color.blue(i), Color.blue(j), paramFloat));
    }
    
    private int pinToByte(int paramInt)
    {
      if (paramInt < 0) {
        return 0;
      }
      int i = paramInt;
      if (paramInt > 255) {
        i = 255;
      }
      return i;
    }
    
    private int rotateColor(int paramInt, float paramFloat)
    {
      paramFloat = paramFloat * 180.0F / 3.1415927F;
      int i = Color.red(paramInt);
      int j = Color.green(paramInt);
      int k = Color.blue(paramInt);
      Object localObject = new ColorMatrix();
      ColorMatrix localColorMatrix = new ColorMatrix();
      ((ColorMatrix)localObject).setRGB2YUV();
      localColorMatrix.setRotate(0, paramFloat);
      ((ColorMatrix)localObject).postConcat(localColorMatrix);
      localColorMatrix.setYUV2RGB();
      ((ColorMatrix)localObject).postConcat(localColorMatrix);
      localObject = ((ColorMatrix)localObject).getArray();
      paramFloat = localObject[0];
      float f1 = i;
      float f2 = localObject[1];
      float f3 = j;
      float f4 = localObject[2];
      float f5 = k;
      i = floatToByte(paramFloat * f1 + f2 * f3 + f4 * f5);
      j = floatToByte(localObject[5] * f1 + localObject[6] * f3 + localObject[7] * f5);
      k = floatToByte(localObject[10] * f1 + localObject[11] * f3 + localObject[12] * f5);
      return Color.argb(Color.alpha(paramInt), pinToByte(i), pinToByte(j), pinToByte(k));
    }
    
    protected void onDraw(Canvas paramCanvas)
    {
      float f1 = 200.0F - this.mPaint.getStrokeWidth() * 0.5F;
      paramCanvas.translate(200.0F, 200.0F);
      float f2 = -f1;
      paramCanvas.drawOval(new RectF(f2, f2, f1, f1), this.mPaint);
      paramCanvas.drawCircle(0.0F, 0.0F, 50.0F, this.mCenterPaint);
      if (this.mTrackingCenter)
      {
        int i = this.mCenterPaint.getColor();
        this.mCenterPaint.setStyle(Paint.Style.STROKE);
        if (this.mHighlightCenter) {
          this.mCenterPaint.setAlpha(255);
        } else {
          this.mCenterPaint.setAlpha(128);
        }
        paramCanvas.drawCircle(0.0F, 0.0F, this.mCenterPaint.getStrokeWidth() + 50.0F, this.mCenterPaint);
        this.mCenterPaint.setStyle(Paint.Style.FILL);
        this.mCenterPaint.setColor(i);
      }
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      setMeasuredDimension(400, 400);
    }
    
    public boolean onTouchEvent(MotionEvent paramMotionEvent)
    {
      float f1 = paramMotionEvent.getX() - 200.0F;
      float f2 = paramMotionEvent.getY() - 200.0F;
      boolean bool;
      if (Math.sqrt(f1 * f1 + f2 * f2) <= 50.0D) {
        bool = true;
      } else {
        bool = false;
      }
      switch (paramMotionEvent.getAction())
      {
      default: 
        return true;
      case 1: 
        if (!this.mTrackingCenter) {
          break label207;
        }
        if (bool) {
          this.mListener.onColorChanged(this.mCenterPaint.getColor());
        }
        this.mTrackingCenter = false;
        invalidate();
        return true;
      case 0: 
        this.mTrackingCenter = bool;
        if (bool)
        {
          this.mHighlightCenter = true;
          invalidate();
          return true;
        }
        break;
      }
      if (this.mTrackingCenter)
      {
        if (this.mHighlightCenter != bool)
        {
          this.mHighlightCenter = bool;
          invalidate();
          return true;
        }
      }
      else
      {
        f2 = (float)Math.atan2(f2, f1) / 6.283185F;
        f1 = f2;
        if (f2 < 0.0F) {
          f1 = f2 + 1.0F;
        }
        this.mCenterPaint.setColor(interpColor(this.mColors, f1));
        invalidate();
      }
      label207:
      return true;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\X5ColorPickerDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */