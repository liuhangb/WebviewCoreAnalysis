package com.tencent.smtt.d;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Interpolator;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Shader.TileMode;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AnimationUtils;
import java.util.ArrayList;
import java.util.Iterator;

public class b
  implements Runnable
{
  private static final float[] b;
  private static final float[] c;
  public int a;
  public long a;
  public final Interpolator a;
  public final Matrix a;
  public final Paint a;
  public Point a;
  public Shader a;
  public View a;
  public a a;
  private a a;
  public ArrayList<MotionEvent> a;
  public boolean a;
  public float[] a;
  public int b;
  public boolean b;
  public int c;
  public boolean c;
  public int d;
  public final int e;
  public final int f;
  public final int g = 700;
  public final int h;
  
  static
  {
    jdField_b_of_type_ArrayOfFloat = new float[] { 255.0F };
    jdField_c_of_type_ArrayOfFloat = new float[] { 0.0F };
  }
  
  public b(ViewConfiguration paramViewConfiguration, View paramView)
  {
    this.jdField_a_of_type_AndroidGraphicsInterpolator = new Interpolator(1, 2);
    this.jdField_a_of_type_ComTencentSmttDB$a = a.jdField_a_of_type_ComTencentSmttDB$a;
    this.jdField_a_of_type_Int = paramViewConfiguration.getScaledFadingEdgeLength();
    this.d = paramViewConfiguration.getScaledScrollBarSize();
    this.jdField_b_of_type_Int = ViewConfiguration.getScrollDefaultDelay();
    this.jdField_c_of_type_Int = ViewConfiguration.getScrollBarFadeDuration();
    this.h = paramViewConfiguration.getScaledTouchSlop();
    this.e = this.h;
    this.f = ((int)TypedValue.applyDimension(1, 2100.0F, paramView.getContext().getResources().getDisplayMetrics()));
    this.jdField_a_of_type_AndroidGraphicsPaint = new Paint();
    this.jdField_a_of_type_AndroidGraphicsMatrix = new Matrix();
    this.jdField_a_of_type_AndroidGraphicsShader = new LinearGradient(0.0F, 0.0F, 0.0F, 1.0F, -16777216, 0, Shader.TileMode.CLAMP);
    this.jdField_a_of_type_AndroidGraphicsPaint.setShader(this.jdField_a_of_type_AndroidGraphicsShader);
    this.jdField_a_of_type_AndroidGraphicsPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
    this.jdField_a_of_type_AndroidViewView = paramView;
  }
  
  public a a()
  {
    return this.jdField_a_of_type_ComTencentSmttDB$a;
  }
  
  public void a()
  {
    Object localObject = this.jdField_a_of_type_JavaUtilArrayList;
    if (localObject != null)
    {
      localObject = ((ArrayList)localObject).iterator();
      while (((Iterator)localObject).hasNext()) {
        ((MotionEvent)((Iterator)localObject).next()).recycle();
      }
      this.jdField_a_of_type_JavaUtilArrayList.clear();
      this.jdField_a_of_type_JavaUtilArrayList = null;
    }
  }
  
  public void a(a parama)
  {
    if (parama == this.jdField_a_of_type_ComTencentSmttDB$a) {
      return;
    }
    this.jdField_a_of_type_ComTencentSmttDB$a = parama;
  }
  
  public void run()
  {
    long l = AnimationUtils.currentAnimationTimeMillis();
    if (l >= this.jdField_a_of_type_Long)
    {
      int i = (int)l;
      Interpolator localInterpolator = this.jdField_a_of_type_AndroidGraphicsInterpolator;
      localInterpolator.setKeyFrame(0, i, jdField_b_of_type_ArrayOfFloat);
      localInterpolator.setKeyFrame(1, i + this.jdField_c_of_type_Int, jdField_c_of_type_ArrayOfFloat);
      a(a.c);
      this.jdField_a_of_type_AndroidViewView.invalidate();
    }
  }
  
  public static enum a
  {
    static
    {
      jdField_a_of_type_ComTencentSmttDB$a = new a("OFF", 0);
      b = new a("ON", 1);
      c = new a("FADING", 2);
      jdField_a_of_type_ArrayOfComTencentSmttDB$a = new a[] { jdField_a_of_type_ComTencentSmttDB$a, b, c };
    }
    
    private a() {}
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\d\b.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */