package org.chromium.ui.display;

import android.content.Context;
import android.graphics.Point;
import java.util.Set;
import java.util.WeakHashMap;

public class DisplayAndroid
{
  private static final DisplayAndroidObserver[] jdField_a_of_type_ArrayOfOrgChromiumUiDisplayDisplayAndroid$DisplayAndroidObserver = new DisplayAndroidObserver[0];
  private float jdField_a_of_type_Float;
  private final int jdField_a_of_type_Int;
  private Point jdField_a_of_type_AndroidGraphicsPoint;
  private final WeakHashMap<DisplayAndroidObserver, Object> jdField_a_of_type_JavaUtilWeakHashMap;
  protected boolean a;
  private int b;
  protected boolean b;
  private int jdField_c_of_type_Int;
  private int d;
  
  protected DisplayAndroid(int paramInt)
  {
    this.jdField_a_of_type_Int = paramInt;
    this.jdField_a_of_type_JavaUtilWeakHashMap = new WeakHashMap();
    this.jdField_a_of_type_AndroidGraphicsPoint = new Point();
  }
  
  protected static DisplayAndroidManager a()
  {
    return DisplayAndroidManager.a();
  }
  
  private DisplayAndroidObserver[] a()
  {
    return (DisplayAndroidObserver[])this.jdField_a_of_type_JavaUtilWeakHashMap.keySet().toArray(jdField_a_of_type_ArrayOfOrgChromiumUiDisplayDisplayAndroid$DisplayAndroidObserver);
  }
  
  public static DisplayAndroid getNonMultiDisplay(Context paramContext)
  {
    paramContext = DisplayAndroidManager.a(paramContext);
    return a().a(paramContext);
  }
  
  public static void startAccurateListening()
  {
    a().a();
  }
  
  public static void stopAccurateListening()
  {
    a().b();
  }
  
  int a()
  {
    switch (getRotation())
    {
    default: 
      if (jdField_c_of_type_Boolean) {
        return 0;
      }
      break;
    case 3: 
      return 270;
    case 2: 
      return 180;
    case 1: 
      return 90;
    case 0: 
      return 0;
    }
    throw new AssertionError();
  }
  
  protected void a(Point paramPoint, Float paramFloat, Integer paramInteger1, Integer paramInteger2, Integer paramInteger3, Boolean paramBoolean1, Boolean paramBoolean2)
  {
    int i5 = 1;
    int i4 = 0;
    int j;
    if ((paramPoint != null) && (!this.jdField_a_of_type_AndroidGraphicsPoint.equals(paramPoint))) {
      j = 1;
    } else {
      j = 0;
    }
    int i;
    if ((paramFloat != null) && (this.jdField_a_of_type_Float != paramFloat.floatValue())) {
      i = 1;
    } else {
      i = 0;
    }
    int k;
    if ((paramInteger1 != null) && (this.jdField_b_of_type_Int != paramInteger1.intValue())) {
      k = 1;
    } else {
      k = 0;
    }
    int m;
    if ((paramInteger2 != null) && (this.jdField_c_of_type_Int != paramInteger2.intValue())) {
      m = 1;
    } else {
      m = 0;
    }
    int n;
    if ((paramInteger3 != null) && (this.d != paramInteger3.intValue())) {
      n = 1;
    } else {
      n = 0;
    }
    int i1;
    if ((paramBoolean1 != null) && (this.jdField_a_of_type_Boolean != paramBoolean1.booleanValue())) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    int i2;
    if ((paramBoolean2 != null) && (this.jdField_b_of_type_Boolean != paramBoolean2.booleanValue())) {
      i2 = 1;
    } else {
      i2 = 0;
    }
    int i3 = i5;
    if (j == 0)
    {
      i3 = i5;
      if (i == 0)
      {
        i3 = i5;
        if (k == 0)
        {
          i3 = i5;
          if (m == 0)
          {
            i3 = i5;
            if (n == 0)
            {
              i3 = i5;
              if (i1 == 0) {
                if (i2 != 0) {
                  i3 = i5;
                } else {
                  i3 = 0;
                }
              }
            }
          }
        }
      }
    }
    if (i3 == 0) {
      return;
    }
    if (j != 0) {
      this.jdField_a_of_type_AndroidGraphicsPoint = paramPoint;
    }
    if (i != 0) {
      this.jdField_a_of_type_Float = paramFloat.floatValue();
    }
    if (k != 0) {
      this.jdField_b_of_type_Int = paramInteger1.intValue();
    }
    if (m != 0) {
      this.jdField_c_of_type_Int = paramInteger2.intValue();
    }
    if (n != 0) {
      this.d = paramInteger3.intValue();
    }
    if (i1 != 0) {
      this.jdField_a_of_type_Boolean = paramBoolean1.booleanValue();
    }
    if (i2 != 0) {
      this.jdField_b_of_type_Boolean = paramBoolean2.booleanValue();
    }
    a().a(this);
    if (n != 0)
    {
      paramPoint = a();
      k = paramPoint.length;
      j = 0;
      while (j < k)
      {
        paramPoint[j].onRotationChanged(this.d);
        j += 1;
      }
    }
    if (i != 0)
    {
      paramPoint = a();
      j = paramPoint.length;
      i = i4;
      while (i < j)
      {
        paramPoint[i].onDIPScaleChanged(this.jdField_a_of_type_Float);
        i += 1;
      }
    }
  }
  
  public void addObserver(DisplayAndroidObserver paramDisplayAndroidObserver)
  {
    this.jdField_a_of_type_JavaUtilWeakHashMap.put(paramDisplayAndroidObserver, null);
  }
  
  int b()
  {
    return this.jdField_b_of_type_Int;
  }
  
  int c()
  {
    return this.jdField_c_of_type_Int;
  }
  
  public float getAndroidUIScaling()
  {
    return 1.0F;
  }
  
  public float getDipScale()
  {
    return this.jdField_a_of_type_Float;
  }
  
  public int getDisplayHeight()
  {
    return this.jdField_a_of_type_AndroidGraphicsPoint.y;
  }
  
  public int getDisplayId()
  {
    return this.jdField_a_of_type_Int;
  }
  
  public int getDisplayWidth()
  {
    return this.jdField_a_of_type_AndroidGraphicsPoint.x;
  }
  
  public boolean getIsWideColorGamut()
  {
    return (this.jdField_a_of_type_Boolean) && (this.jdField_b_of_type_Boolean);
  }
  
  public int getRotation()
  {
    return this.d;
  }
  
  public void removeObserver(DisplayAndroidObserver paramDisplayAndroidObserver)
  {
    this.jdField_a_of_type_JavaUtilWeakHashMap.remove(paramDisplayAndroidObserver);
  }
  
  public void updateIsDisplayServerWideColorGamut(Boolean paramBoolean)
  {
    a(null, null, null, null, null, null, paramBoolean);
  }
  
  public static abstract interface DisplayAndroidObserver
  {
    public abstract void onDIPScaleChanged(float paramFloat);
    
    public abstract void onRotationChanged(int paramInt);
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\ui\display\DisplayAndroid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */