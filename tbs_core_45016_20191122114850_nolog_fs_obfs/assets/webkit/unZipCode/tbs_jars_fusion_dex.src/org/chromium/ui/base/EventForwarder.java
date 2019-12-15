package org.chromium.ui.base;

import android.annotation.TargetApi;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.os.Build.VERSION;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import org.chromium.base.TraceEvent;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.ui.display.DisplayAndroid;

@JNINamespace("ui")
public class EventForwarder
{
  private float jdField_a_of_type_Float;
  private int jdField_a_of_type_Int;
  private long jdField_a_of_type_Long;
  private float jdField_b_of_type_Float;
  private final boolean jdField_b_of_type_Boolean;
  
  private EventForwarder(long paramLong, boolean paramBoolean)
  {
    this.jdField_a_of_type_Long = paramLong;
    this.jdField_b_of_type_Boolean = paramBoolean;
  }
  
  private float a()
  {
    return nativeGetJavaWindowAndroid(this.jdField_a_of_type_Long).getDisplay().getAndroidUIScaling();
  }
  
  @TargetApi(23)
  private int a(MotionEvent paramMotionEvent)
  {
    if (Build.VERSION.SDK_INT >= 23) {
      return paramMotionEvent.getActionButton();
    }
    return 0;
  }
  
  private static boolean a(int paramInt)
  {
    boolean bool2 = true;
    boolean bool1 = bool2;
    if (paramInt != 0)
    {
      bool1 = bool2;
      if (paramInt != 1)
      {
        bool1 = bool2;
        if (paramInt != 3)
        {
          bool1 = bool2;
          if (paramInt != 2)
          {
            bool1 = bool2;
            if (paramInt != 5)
            {
              if (paramInt == 6) {
                return true;
              }
              bool1 = false;
            }
          }
        }
      }
    }
    return bool1;
  }
  
  private boolean a(MotionEvent paramMotionEvent, boolean paramBoolean)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    TraceEvent.begin("sendTouchEvent");
    Object localObject;
    float[] arrayOfFloat1;
    float f1;
    label149:
    float[] arrayOfFloat2;
    label182:
    label197:
    float f2;
    label213:
    int i;
    label291:
    label348:
    label374:
    int j;
    try
    {
      if (paramMotionEvent.getHistorySize() > 0) {
        l1 = paramMotionEvent.getHistoricalEventTime(0);
      } else {
        l1 = paramMotionEvent.getEventTime();
      }
      localObject = paramMotionEvent;
      k = SPenSupport.a(paramMotionEvent.getActionMasked());
      boolean bool = a(k);
      if (!bool) {
        return false;
      }
      if (this.jdField_a_of_type_Float == 0.0F) {
        if (this.jdField_b_of_type_Float == 0.0F) {
          break label502;
        }
      }
      paramMotionEvent = a(paramMotionEvent);
      localObject = paramMotionEvent;
      m = paramMotionEvent.getPointerCount();
      arrayOfFloat1 = new float[2];
      arrayOfFloat1[0] = paramMotionEvent.getTouchMajor();
      if (m <= 1) {
        break label515;
      }
      f1 = paramMotionEvent.getTouchMajor(1);
      arrayOfFloat1[1] = f1;
      arrayOfFloat2 = new float[2];
      arrayOfFloat2[0] = paramMotionEvent.getTouchMinor();
      if (m <= 1) {
        break label520;
      }
      f1 = paramMotionEvent.getTouchMinor(1);
    }
    finally
    {
      long l1;
      int k;
      int m;
      float f5;
      long l2;
      int n;
      int i1;
      float f3;
      float f4;
      float f6;
      float f7;
      int i2;
      float f8;
      float f9;
      float f10;
      float f11;
      float f12;
      float f13;
      float f14;
      int i3;
      TraceEvent.end("sendTouchEvent");
    }
    if (m > 1)
    {
      f1 = paramMotionEvent.getX(1);
      if (m <= 1) {
        break label586;
      }
      f2 = paramMotionEvent.getY(1);
      f5 = a();
      l2 = this.jdField_a_of_type_Long;
      n = paramMotionEvent.getHistorySize();
      i1 = paramMotionEvent.getActionIndex();
      f3 = paramMotionEvent.getX() / f5;
      f4 = paramMotionEvent.getY() / f5;
      f6 = f1 / f5;
      f7 = f2 / f5;
      i2 = paramMotionEvent.getPointerId(0);
      if (m <= 1) {
        break label592;
      }
      i = paramMotionEvent.getPointerId(1);
      f8 = arrayOfFloat1[0] / f5;
      f9 = arrayOfFloat1[1] / f5;
      f10 = arrayOfFloat2[0] / f5;
      f11 = arrayOfFloat2[1] / f5;
      f12 = paramMotionEvent.getOrientation();
      if (m <= 1) {
        break label598;
      }
      f1 = paramMotionEvent.getOrientation(1);
      f13 = paramMotionEvent.getAxisValue(25);
      if (m <= 1) {
        break label603;
      }
      f2 = paramMotionEvent.getAxisValue(25, 1);
      f14 = paramMotionEvent.getRawX() / f5;
      f5 = paramMotionEvent.getRawY() / f5;
      i3 = paramMotionEvent.getToolType(0);
      if (m <= 1) {
        break label609;
      }
      j = paramMotionEvent.getToolType(1);
    }
    for (;;)
    {
      paramBoolean = nativeOnTouchEvent(l2, paramMotionEvent, l1, k, m, n, i1, f3, f4, f6, f7, i2, i, f8, f9, f10, f11, f12, f1, f13, f2, f14, f5, i3, j, paramMotionEvent.getButtonState(), paramMotionEvent.getMetaState(), paramBoolean);
      if (localObject != null) {
        ((MotionEvent)localObject).recycle();
      }
      TraceEvent.end("sendTouchEvent");
      return paramBoolean;
      label502:
      arrayOfFloat1 = null;
      paramMotionEvent = (MotionEvent)localObject;
      localObject = arrayOfFloat1;
      break;
      label515:
      f1 = 0.0F;
      break label149;
      label520:
      f1 = 0.0F;
      arrayOfFloat2[1] = f1;
      i = 0;
      while (i < 2)
      {
        if (arrayOfFloat1[i] < arrayOfFloat2[i])
        {
          f1 = arrayOfFloat1[i];
          arrayOfFloat1[i] = arrayOfFloat2[i];
          arrayOfFloat2[i] = f1;
        }
        i += 1;
      }
      break label182;
      f1 = 0.0F;
      break label197;
      label586:
      f2 = 0.0F;
      break label213;
      label592:
      i = -1;
      break label291;
      label598:
      f1 = 0.0F;
      break label348;
      label603:
      f2 = 0.0F;
      break label374;
      label609:
      j = 0;
    }
  }
  
  @CalledByNative
  private static EventForwarder create(long paramLong, boolean paramBoolean)
  {
    return new EventForwarder(paramLong, paramBoolean);
  }
  
  @CalledByNative
  private void destroy()
  {
    this.jdField_a_of_type_Long = 0L;
  }
  
  private boolean e(MotionEvent paramMotionEvent)
  {
    EventForwarder localEventForwarder = this;
    if ((!jdField_a_of_type_Boolean) && (localEventForwarder.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    MotionEvent localMotionEvent = a(paramMotionEvent);
    int i;
    try
    {
      i = paramMotionEvent.getActionMasked();
      if ((i == 11) || (i == 12)) {
        localEventForwarder.jdField_a_of_type_Int = paramMotionEvent.getButtonState();
      }
      if (i != 9) {
        break label263;
      }
      if (localEventForwarder.jdField_a_of_type_Int != 1) {
        break label260;
      }
      f = a();
      nativeOnMouseEvent(localEventForwarder.jdField_a_of_type_Long, paramMotionEvent.getEventTime(), 12, localMotionEvent.getX() / f, localMotionEvent.getY() / f, paramMotionEvent.getPointerId(0), paramMotionEvent.getPressure(0), paramMotionEvent.getOrientation(0), paramMotionEvent.getAxisValue(25, 0), 1, paramMotionEvent.getButtonState(), paramMotionEvent.getMetaState(), paramMotionEvent.getToolType(0));
      this.jdField_a_of_type_Int = 0;
    }
    finally
    {
      float f;
      label159:
      localMotionEvent.recycle();
    }
    f = a();
    nativeOnMouseEvent(this.jdField_a_of_type_Long, paramMotionEvent.getEventTime(), i, localMotionEvent.getX() / f, localMotionEvent.getY() / f, paramMotionEvent.getPointerId(0), paramMotionEvent.getPressure(0), paramMotionEvent.getOrientation(0), paramMotionEvent.getAxisValue(25, 0), a(paramMotionEvent), paramMotionEvent.getButtonState(), paramMotionEvent.getMetaState(), paramMotionEvent.getToolType(0));
    localMotionEvent.recycle();
    return true;
    for (;;)
    {
      localMotionEvent.recycle();
      return true;
      label260:
      label263:
      do
      {
        localMotionEvent.recycle();
        return false;
        break;
      } while ((i == 9) || (i == 10));
      if (i != 0) {
        if (i != 1) {
          break label159;
        }
      }
    }
  }
  
  private native WindowAndroid nativeGetJavaWindowAndroid(long paramLong);
  
  private native void nativeOnCancelFling(long paramLong1, long paramLong2);
  
  private native void nativeOnDragEvent(long paramLong, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, String[] paramArrayOfString, String paramString);
  
  private native boolean nativeOnGestureEvent(long paramLong1, int paramInt, long paramLong2, float paramFloat);
  
  private native void nativeOnMouseEvent(long paramLong1, long paramLong2, int paramInt1, float paramFloat1, float paramFloat2, int paramInt2, float paramFloat3, float paramFloat4, float paramFloat5, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  private native void nativeOnMouseWheelEvent(long paramLong1, long paramLong2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5);
  
  private native void nativeOnStartFling(long paramLong1, long paramLong2, float paramFloat1, float paramFloat2, boolean paramBoolean);
  
  private native boolean nativeOnTouchEvent(long paramLong1, MotionEvent paramMotionEvent, long paramLong2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, int paramInt5, int paramInt6, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, float paramFloat11, float paramFloat12, float paramFloat13, float paramFloat14, int paramInt7, int paramInt8, int paramInt9, int paramInt10, boolean paramBoolean);
  
  public MotionEvent a(MotionEvent paramMotionEvent)
  {
    paramMotionEvent = MotionEvent.obtain(paramMotionEvent);
    paramMotionEvent.offsetLocation(this.jdField_a_of_type_Float, this.jdField_b_of_type_Float);
    return paramMotionEvent;
  }
  
  public void a(long paramLong, float paramFloat1, float paramFloat2, boolean paramBoolean)
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    nativeOnStartFling(l, paramLong, paramFloat1, paramFloat2, paramBoolean);
  }
  
  public boolean a(long paramLong, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    if ((!jdField_a_of_type_Boolean) && (this.jdField_a_of_type_Long == 0L)) {
      throw new AssertionError();
    }
    float f = a();
    nativeOnMouseWheelEvent(this.jdField_a_of_type_Long, paramLong, paramFloat1 / f, paramFloat2 / f, paramFloat3, paramFloat4, paramFloat5);
    return true;
  }
  
  @TargetApi(24)
  public boolean a(DragEvent paramDragEvent, View paramView)
  {
    if (this.jdField_a_of_type_Long != 0L)
    {
      if (Build.VERSION.SDK_INT <= 23) {
        return false;
      }
      Object localObject1 = paramDragEvent.getClipDescription();
      if (localObject1 == null) {
        localObject1 = new String[0];
      } else {
        localObject1 = ((ClipDescription)localObject1).filterMimeTypes("text/*");
      }
      if (paramDragEvent.getAction() == 1) {
        return (localObject1 != null) && (localObject1.length > 0) && (this.jdField_b_of_type_Boolean);
      }
      StringBuilder localStringBuilder = new StringBuilder("");
      if (paramDragEvent.getAction() == 3)
      {
        localObject2 = paramDragEvent.getClipData();
        j = ((ClipData)localObject2).getItemCount();
        i = 0;
        while (i < j)
        {
          localStringBuilder.append(((ClipData)localObject2).getItemAt(i).coerceToStyledText(paramView.getContext()));
          i += 1;
        }
      }
      Object localObject2 = new int[2];
      paramView.getLocationOnScreen((int[])localObject2);
      int i = (int)(paramDragEvent.getX() + this.jdField_a_of_type_Float);
      int j = (int)(paramDragEvent.getY() + this.jdField_b_of_type_Float);
      int k = localObject2[0];
      int m = localObject2[1];
      float f = a();
      nativeOnDragEvent(this.jdField_a_of_type_Long, paramDragEvent.getAction(), (int)(i / f), (int)(j / f), (int)((k + i) / f), (int)((m + j) / f), (String[])localObject1, localStringBuilder.toString());
      return true;
    }
    return false;
  }
  
  public boolean a(MotionEvent paramMotionEvent)
  {
    if (paramMotionEvent.getToolType(0) == 3)
    {
      int k = Build.VERSION.SDK_INT;
      int i = paramMotionEvent.getButtonState();
      int j = 1;
      if (i == 0)
      {
        i = j;
        if (paramMotionEvent.getActionMasked() == 0) {
          break label59;
        }
        i = j;
        if (paramMotionEvent.getActionMasked() == 2) {
          break label59;
        }
        if (paramMotionEvent.getActionMasked() == 1)
        {
          i = j;
          break label59;
        }
      }
      i = 0;
      label59:
      if ((k >= 23) && (i == 0)) {
        return d(paramMotionEvent);
      }
    }
    return a(paramMotionEvent, false);
  }
  
  public boolean b(MotionEvent paramMotionEvent)
  {
    return a(paramMotionEvent, true);
  }
  
  public boolean c(MotionEvent paramMotionEvent)
  {
    TraceEvent.begin("onHoverEvent");
    try
    {
      boolean bool = e(paramMotionEvent);
      return bool;
    }
    finally
    {
      TraceEvent.end("onHoverEvent");
    }
  }
  
  public boolean d(MotionEvent paramMotionEvent)
  {
    TraceEvent.begin("sendMouseEvent");
    try
    {
      boolean bool = e(paramMotionEvent);
      return bool;
    }
    finally
    {
      TraceEvent.end("sendMouseEvent");
    }
  }
  
  public void onCancelFling(long paramLong)
  {
    long l = this.jdField_a_of_type_Long;
    if (l == 0L) {
      return;
    }
    nativeOnCancelFling(l, paramLong);
  }
  
  public void setCurrentTouchEventOffsets(float paramFloat1, float paramFloat2)
  {
    this.jdField_a_of_type_Float = paramFloat1;
    this.jdField_b_of_type_Float = paramFloat2;
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\base\EventForwarder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */