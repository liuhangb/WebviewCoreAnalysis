package org.chromium.content.browser;

import android.view.MotionEvent;
import android.view.MotionEvent.PointerCoords;
import android.view.MotionEvent.PointerProperties;
import android.view.View;

public class MotionEventSynthesizer
{
  private long jdField_a_of_type_Long;
  private final View jdField_a_of_type_AndroidViewView;
  private final MotionEvent.PointerCoords[] jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords;
  private final MotionEvent.PointerProperties[] jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties;
  
  public MotionEventSynthesizer(View paramView)
  {
    if ((!jdField_a_of_type_Boolean) && (paramView == null)) {
      throw new AssertionError();
    }
    this.jdField_a_of_type_AndroidViewView = paramView;
    this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties = new MotionEvent.PointerProperties[16];
    this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords = new MotionEvent.PointerCoords[16];
  }
  
  private void a(int paramInt1, int paramInt2, long paramLong)
  {
    if ((!jdField_a_of_type_Boolean) && (paramInt2 != 1)) {
      throw new AssertionError();
    }
    int i = 9;
    if (6 == paramInt1) {
      i = 10;
    }
    if (7 == paramInt1) {
      i = 7;
    }
    MotionEvent localMotionEvent = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, i, paramInt2, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 2, 0);
    this.jdField_a_of_type_AndroidViewView.dispatchGenericMotionEvent(localMotionEvent);
    localMotionEvent.recycle();
  }
  
  public void inject(int paramInt1, int paramInt2, long paramLong)
  {
    MotionEvent localMotionEvent;
    switch (paramInt1)
    {
    default: 
      if (jdField_a_of_type_Boolean) {
        return;
      }
      break;
    case 5: 
    case 6: 
    case 7: 
      a(paramInt1, paramInt2, paramLong);
      return;
    case 4: 
      if ((!jdField_a_of_type_Boolean) && (paramInt2 != 1)) {
        throw new AssertionError();
      }
      localMotionEvent = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, 8, paramInt2, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 2, 0);
      this.jdField_a_of_type_AndroidViewView.dispatchGenericMotionEvent(localMotionEvent);
      localMotionEvent.recycle();
      return;
    case 3: 
      if (paramInt2 > 1)
      {
        if ((!jdField_a_of_type_Boolean) && (paramInt2 != 2)) {
          throw new AssertionError();
        }
        localMotionEvent = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, 262, paramInt2, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 0, 0);
        this.jdField_a_of_type_AndroidViewView.dispatchTouchEvent(localMotionEvent);
        localMotionEvent.recycle();
      }
      localMotionEvent = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, 1, 1, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 0, 0);
      this.jdField_a_of_type_AndroidViewView.dispatchTouchEvent(localMotionEvent);
      localMotionEvent.recycle();
      return;
    case 2: 
      localMotionEvent = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, 3, 1, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 0, 0);
      this.jdField_a_of_type_AndroidViewView.dispatchTouchEvent(localMotionEvent);
      localMotionEvent.recycle();
      return;
    case 1: 
      localMotionEvent = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, 2, paramInt2, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 0, 0);
      this.jdField_a_of_type_AndroidViewView.dispatchTouchEvent(localMotionEvent);
      localMotionEvent.recycle();
      return;
    case 0: 
      this.jdField_a_of_type_Long = paramLong;
      localMotionEvent = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, 0, 1, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 0, 0);
      this.jdField_a_of_type_AndroidViewView.dispatchTouchEvent(localMotionEvent);
      localMotionEvent.recycle();
      if (paramInt2 > 1)
      {
        if ((!jdField_a_of_type_Boolean) && (paramInt2 != 2)) {
          throw new AssertionError();
        }
        localMotionEvent = MotionEvent.obtain(this.jdField_a_of_type_Long, paramLong, 261, paramInt2, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties, this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords, 0, 0, 1.0F, 1.0F, 0, 0, 0, 0);
        this.jdField_a_of_type_AndroidViewView.dispatchTouchEvent(localMotionEvent);
        localMotionEvent.recycle();
      }
      return;
    }
    throw new AssertionError("Unreached");
  }
  
  public void setPointer(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if ((!jdField_a_of_type_Boolean) && ((paramInt1 < 0) || (paramInt1 >= 16))) {
      throw new AssertionError();
    }
    Object localObject = new MotionEvent.PointerCoords();
    ((MotionEvent.PointerCoords)localObject).x = paramInt2;
    ((MotionEvent.PointerCoords)localObject).y = paramInt3;
    ((MotionEvent.PointerCoords)localObject).pressure = 1.0F;
    this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords[paramInt1] = localObject;
    localObject = new MotionEvent.PointerProperties();
    ((MotionEvent.PointerProperties)localObject).id = paramInt4;
    this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerProperties[paramInt1] = localObject;
  }
  
  public void setScrollDeltas(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    setPointer(0, paramInt1, paramInt2, 0);
    this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords[0].setAxisValue(10, paramInt3);
    this.jdField_a_of_type_ArrayOfAndroidViewMotionEvent$PointerCoords[0].setAxisValue(9, paramInt4);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\MotionEventSynthesizer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */