package com.tencent.smtt.listbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.ui.e;
import java.util.ArrayList;
import java.util.Iterator;

public class MttScrollView
  extends ViewGroup
{
  private static final String jdField_a_of_type_JavaLangString = "MttScrollView";
  private static final int jdField_e_of_type_Int = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_control_scrollbar_min_height", "dimen"));
  private byte jdField_a_of_type_Byte;
  private float jdField_a_of_type_Float;
  private int jdField_a_of_type_Int;
  private Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  private Handler jdField_a_of_type_AndroidOsHandler;
  private VelocityTracker jdField_a_of_type_AndroidViewVelocityTracker;
  private MttScroller jdField_a_of_type_ComTencentSmttListboxMttScroller;
  protected ArrayList<MttScrollViewListener> a;
  private boolean jdField_a_of_type_Boolean = false;
  private float jdField_b_of_type_Float;
  private int jdField_b_of_type_Int = 0;
  private boolean jdField_b_of_type_Boolean = false;
  private int jdField_c_of_type_Int = 1;
  private boolean jdField_c_of_type_Boolean = false;
  private int jdField_d_of_type_Int;
  private boolean jdField_d_of_type_Boolean = true;
  private boolean jdField_e_of_type_Boolean = false;
  private int jdField_f_of_type_Int = 0;
  private boolean jdField_f_of_type_Boolean = false;
  private int g = 0;
  private int h;
  private int i;
  private int j;
  private int k;
  private int l;
  
  public MttScrollView(Context paramContext, boolean paramBoolean)
  {
    super(paramContext);
    this.jdField_a_of_type_Boolean = paramBoolean;
    this.l = ViewConfiguration.get(paramContext).getScaledMinimumFlingVelocity();
    this.jdField_a_of_type_ComTencentSmttListboxMttScroller = new MttScroller(paramContext, null);
    this.h = 0;
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = e.getImageDrawable("x5_select_list_scrollbar", this.jdField_a_of_type_Boolean, 3);
    this.i = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_control_scrollbar_width", "dimen"));
    this.j = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_control_scrollbar_left_offset", "dimen"));
    this.k = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_control_scrollbar_right_offset", "dimen"));
    d();
    b(true);
  }
  
  private void a(int paramInt, boolean paramBoolean)
  {
    int n;
    int m;
    if ((!paramBoolean) && (!this.jdField_b_of_type_Boolean))
    {
      n = this.jdField_a_of_type_Int;
      if (n - paramInt > 0)
      {
        if (!this.jdField_a_of_type_ComTencentSmttListboxMttScroller.isFinished()) {
          this.jdField_a_of_type_ComTencentSmttListboxMttScroller.forceFinished(true);
        }
        m = this.jdField_a_of_type_Int + 0;
      }
      else
      {
        m = paramInt;
        if (n - paramInt < getHeight() - this.jdField_b_of_type_Int + 0)
        {
          if (!this.jdField_a_of_type_ComTencentSmttListboxMttScroller.isFinished()) {
            this.jdField_a_of_type_ComTencentSmttListboxMttScroller.forceFinished(true);
          }
          m = this.jdField_a_of_type_Int - (getHeight() - this.jdField_b_of_type_Int + 0);
        }
      }
    }
    else
    {
      m = this.jdField_a_of_type_Int;
      n = paramInt;
      if (m - paramInt > 0) {
        n = m;
      }
      m = n;
      if (this.jdField_a_of_type_Int - n < getHeight() - this.jdField_b_of_type_Int) {
        m = this.jdField_a_of_type_Int - (getHeight() - this.jdField_b_of_type_Int);
      }
    }
    if (Math.abs(m) > 0)
    {
      if (paramBoolean)
      {
        paramInt = this.jdField_a_of_type_Int;
        if ((paramInt > 0) || (paramInt < getHeight() - this.jdField_b_of_type_Int))
        {
          paramInt = m / 3;
          b(paramInt);
          this.jdField_a_of_type_Int -= paramInt;
          break label234;
        }
      }
      b(m);
      this.jdField_a_of_type_Int -= m;
    }
    label234:
    invalidate();
    Object localObject = this.jdField_a_of_type_JavaUtilArrayList;
    if ((localObject != null) && (((ArrayList)localObject).size() > 0))
    {
      localObject = this.jdField_a_of_type_JavaUtilArrayList.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((MttScrollViewListener)((Iterator)localObject).next()).onScroll(this.jdField_a_of_type_Int);
      }
    }
  }
  
  private void a(Canvas paramCanvas)
  {
    if ((this.jdField_a_of_type_AndroidGraphicsDrawableDrawable != null) && (this.jdField_b_of_type_Int > getHeight()))
    {
      int i2 = getHeight() - this.jdField_f_of_type_Int - this.g;
      int n = getHeight() * i2 / this.jdField_b_of_type_Int;
      int i1 = jdField_e_of_type_Int;
      int m = n;
      if (n < i1) {
        m = i1;
      }
      i1 = (i2 - m) * this.jdField_a_of_type_Int / (getHeight() - this.jdField_b_of_type_Int);
      i2 = getScrollY();
      int i3 = getWidth();
      n = this.i;
      i3 = i3 - n + this.j - this.k;
      i1 = i1 + i2 + this.jdField_f_of_type_Int;
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setBounds(i3, i1, n + i3, m + i1);
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setAlpha(this.h);
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.draw(paramCanvas);
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setAlpha(255);
    }
  }
  
  private void c()
  {
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(1);
    if (!this.jdField_a_of_type_Boolean)
    {
      this.h = 255;
      return;
    }
    this.h = 255;
  }
  
  private void d()
  {
    if (this.jdField_a_of_type_AndroidOsHandler == null) {
      this.jdField_a_of_type_AndroidOsHandler = new Handler(Looper.getMainLooper())
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          if (paramAnonymousMessage.what != 1) {
            return;
          }
          if (MttScrollView.a(MttScrollView.this) > 0)
          {
            paramAnonymousMessage = MttScrollView.this;
            MttScrollView.a(paramAnonymousMessage, MttScrollView.a(paramAnonymousMessage) - 20);
            if (MttScrollView.a(MttScrollView.this) < 0) {
              MttScrollView.a(MttScrollView.this, 0);
            }
            MttScrollView.this.postInvalidate();
            MttScrollView.a(MttScrollView.this).sendEmptyMessage(1);
          }
        }
      };
    }
  }
  
  private void e()
  {
    int n = this.jdField_a_of_type_Int;
    int m = 500;
    if ((n <= 0) && (getHeight() <= this.jdField_b_of_type_Int))
    {
      if (this.jdField_a_of_type_Int < getHeight() - this.jdField_b_of_type_Int)
      {
        int i1 = getHeight();
        int i2 = this.jdField_b_of_type_Int;
        n = m;
        if (a())
        {
          n = m;
          if (MttScroller.jdField_a_of_type_Boolean) {
            n = Integer.MAX_VALUE;
          }
        }
        scrollTo(i1 - i2, n);
        return;
      }
      VelocityTracker localVelocityTracker = this.jdField_a_of_type_AndroidViewVelocityTracker;
      localVelocityTracker.computeCurrentVelocity(1000);
      n = (int)localVelocityTracker.getYVelocity();
      if (Math.abs(n) > this.l)
      {
        if (!this.jdField_a_of_type_ComTencentSmttListboxMttScroller.isFinished()) {
          this.jdField_a_of_type_ComTencentSmttListboxMttScroller.a();
        }
        this.jdField_b_of_type_Boolean = false;
        if (n < 0) {
          m = Integer.MAX_VALUE;
        } else {
          m = 0;
        }
        this.jdField_d_of_type_Int = m;
        this.jdField_c_of_type_Boolean = true;
        this.jdField_a_of_type_ComTencentSmttListboxMttScroller.a(0, m, 0, n, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        invalidate();
        return;
      }
      a();
      return;
    }
    n = m;
    if (a())
    {
      n = m;
      if (MttScroller.jdField_a_of_type_Boolean) {
        n = Integer.MAX_VALUE;
      }
    }
    scrollTo(0, n);
  }
  
  private void f()
  {
    this.jdField_b_of_type_Boolean = false;
    a();
    int n = this.jdField_a_of_type_Int;
    int m = Integer.MAX_VALUE;
    if (n > 0)
    {
      if ((!a()) || (!MttScroller.jdField_a_of_type_Boolean)) {
        m = 500;
      }
      scrollTo(0, m);
      return;
    }
    if ((n != 0) && (n < getHeight() - this.jdField_b_of_type_Int))
    {
      n = Math.min(0, getHeight() - this.jdField_b_of_type_Int);
      if ((!a()) || (!MttScroller.jdField_a_of_type_Boolean)) {
        m = 500;
      }
      scrollTo(n, m);
      return;
    }
    Object localObject = this.jdField_a_of_type_JavaUtilArrayList;
    if ((localObject != null) && (((ArrayList)localObject).size() > 0))
    {
      localObject = this.jdField_a_of_type_JavaUtilArrayList.iterator();
      while (((Iterator)localObject).hasNext()) {
        ((MttScrollViewListener)((Iterator)localObject).next()).onScrollEnd();
      }
    }
  }
  
  public void a()
  {
    this.jdField_a_of_type_AndroidOsHandler.sendEmptyMessageDelayed(1, 500L);
  }
  
  public void a(int paramInt)
  {
    scrollTo(paramInt, -1);
  }
  
  public void a(boolean paramBoolean)
  {
    if (paramBoolean != this.jdField_a_of_type_Boolean)
    {
      this.jdField_a_of_type_Boolean = paramBoolean;
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = e.getImageDrawable("x5_select_list_scrollbar", this.jdField_a_of_type_Boolean, 3);
    }
  }
  
  protected boolean a()
  {
    return false;
  }
  
  public void b()
  {
    this.jdField_a_of_type_AndroidOsHandler.removeMessages(1);
    this.h = 0;
    postInvalidate();
  }
  
  protected void b(int paramInt)
  {
    if (paramInt == 0) {
      return;
    }
    super.scrollTo(0, getScrollY() + paramInt);
  }
  
  public void b(boolean paramBoolean) {}
  
  public void computeScroll()
  {
    if (this.jdField_a_of_type_ComTencentSmttListboxMttScroller.a())
    {
      int m = this.jdField_d_of_type_Int;
      int n = this.jdField_a_of_type_ComTencentSmttListboxMttScroller.getCurrY();
      this.jdField_d_of_type_Int = this.jdField_a_of_type_ComTencentSmttListboxMttScroller.getCurrY();
      a(m - n, false);
      postInvalidate();
      return;
    }
    if (this.jdField_c_of_type_Boolean)
    {
      this.jdField_c_of_type_Boolean = false;
      f();
    }
  }
  
  protected void dispatchDraw(Canvas paramCanvas)
  {
    super.dispatchDraw(paramCanvas);
    if (this.jdField_d_of_type_Boolean) {
      a(paramCanvas);
    }
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    switch (paramMotionEvent.getAction())
    {
    default: 
      return false;
    case 2: 
      if (this.jdField_a_of_type_Byte == 0)
      {
        float f1 = Math.abs(paramMotionEvent.getX() - this.jdField_a_of_type_Float);
        float f2 = Math.abs(paramMotionEvent.getY() - this.jdField_b_of_type_Float);
        if ((Math.abs(paramMotionEvent.getY() - this.jdField_b_of_type_Float) > 10.0F) && (f2 > f1) && (!this.jdField_f_of_type_Boolean))
        {
          this.jdField_b_of_type_Float = paramMotionEvent.getY();
          this.jdField_a_of_type_Byte = 1;
          return true;
        }
      }
      else
      {
        return true;
      }
      break;
    case 1: 
    case 3: 
      this.jdField_a_of_type_Byte = 0;
      return false;
    case 0: 
      this.jdField_f_of_type_Boolean = false;
      this.jdField_a_of_type_Float = paramMotionEvent.getX();
      this.jdField_b_of_type_Float = paramMotionEvent.getY();
      this.jdField_a_of_type_Byte = 0;
      if (!this.jdField_a_of_type_ComTencentSmttListboxMttScroller.isFinished())
      {
        this.jdField_a_of_type_ComTencentSmttListboxMttScroller.forceFinished(true);
        return false;
      }
      if (this.jdField_a_of_type_AndroidViewVelocityTracker == null) {
        this.jdField_a_of_type_AndroidViewVelocityTracker = VelocityTracker.obtain();
      }
      this.jdField_a_of_type_AndroidViewVelocityTracker.addMovement(paramMotionEvent);
      return false;
    }
    return false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.jdField_e_of_type_Boolean = false;
    paramInt2 = getPaddingTop();
    int n = getChildCount();
    int i1;
    View localView;
    Object localObject;
    int i2;
    int m;
    if (this.jdField_c_of_type_Int == 1)
    {
      i1 = getPaddingLeft();
      paramInt1 = 0;
      while (paramInt1 < n)
      {
        localView = getChildAt(paramInt1);
        paramInt3 = paramInt2;
        if (localView != null)
        {
          paramInt3 = paramInt2;
          if (localView.getVisibility() != 8)
          {
            localObject = localView.getLayoutParams();
            i2 = localView.getMeasuredHeight();
            if ((localObject instanceof ViewGroup.MarginLayoutParams))
            {
              localObject = (ViewGroup.MarginLayoutParams)localObject;
              m = ((ViewGroup.MarginLayoutParams)localObject).leftMargin;
              paramInt4 = ((ViewGroup.MarginLayoutParams)localObject).topMargin;
              paramInt3 = ((ViewGroup.MarginLayoutParams)localObject).bottomMargin;
            }
            else
            {
              paramInt3 = 0;
              m = 0;
              paramInt4 = 0;
            }
            paramInt2 += paramInt4;
            m += i1;
            paramInt4 += paramInt2;
            localView.layout(m, paramInt4, localView.getMeasuredWidth() + m, localView.getMeasuredHeight() + paramInt4);
            paramInt3 = paramInt2 + (i2 + paramInt3);
          }
        }
        paramInt1 += 1;
        paramInt2 = paramInt3;
      }
      paramInt1 = paramInt2 + (getPaddingTop() + getPaddingBottom());
    }
    else
    {
      i1 = getPaddingTop();
      paramInt1 = 0;
      while (paramInt1 < n)
      {
        localView = getChildAt(paramInt1);
        paramInt3 = paramInt2;
        if (localView != null)
        {
          paramInt3 = paramInt2;
          if (localView.getVisibility() != 8)
          {
            localObject = localView.getLayoutParams();
            i2 = localView.getMeasuredWidth();
            if ((localObject instanceof ViewGroup.MarginLayoutParams))
            {
              localObject = (ViewGroup.MarginLayoutParams)localObject;
              m = ((ViewGroup.MarginLayoutParams)localObject).leftMargin;
              paramInt4 = ((ViewGroup.MarginLayoutParams)localObject).topMargin;
              paramInt3 = ((ViewGroup.MarginLayoutParams)localObject).rightMargin;
            }
            else
            {
              paramInt3 = 0;
              m = 0;
              paramInt4 = 0;
            }
            paramInt2 += m;
            m += paramInt2;
            paramInt4 += i1;
            localView.layout(m, paramInt4, localView.getMeasuredWidth() + m, localView.getMeasuredHeight() + paramInt4);
            paramInt3 = paramInt2 + (i2 + paramInt3);
          }
        }
        paramInt1 += 1;
        paramInt2 = paramInt3;
      }
      paramInt1 = paramInt2 + (getPaddingLeft() + getPaddingRight());
    }
    this.jdField_b_of_type_Int = (paramInt1 + getPaddingBottom());
    super.scrollTo(0, -this.jdField_a_of_type_Int);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.jdField_c_of_type_Int == 1)
    {
      m = View.MeasureSpec.getSize(paramInt1);
      n = 0;
    }
    else
    {
      n = View.MeasureSpec.getSize(paramInt2);
      m = 0;
    }
    int i4 = getChildCount();
    int i1 = m;
    int i2 = 0;
    int m = n;
    int n = i2;
    View localView;
    while (n < i4)
    {
      localView = getChildAt(n);
      i2 = i1;
      int i3 = m;
      if (localView != null)
      {
        localView.measure(paramInt1, paramInt2);
        if (this.jdField_c_of_type_Int == 1)
        {
          i3 = m + localView.getMeasuredHeight();
          i2 = i1;
        }
        else
        {
          i2 = i1 + localView.getMeasuredWidth();
          i3 = m;
        }
      }
      n += 1;
      i1 = i2;
      m = i3;
    }
    setMeasuredDimension(i1, m);
    i1 = getPaddingLeft();
    i2 = getPaddingRight();
    ViewGroup.LayoutParams localLayoutParams;
    ViewGroup.MarginLayoutParams localMarginLayoutParams;
    if (this.jdField_c_of_type_Int == 1)
    {
      paramInt2 = 0;
      while (paramInt2 < i4)
      {
        localView = getChildAt(paramInt2);
        if (localView != null)
        {
          localLayoutParams = localView.getLayoutParams();
          if (localLayoutParams.height > 0) {
            m = View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, Integer.MIN_VALUE);
          } else {
            m = View.MeasureSpec.makeMeasureSpec(0, 0);
          }
          if (localView.getLayoutParams().width == -1)
          {
            if ((localLayoutParams instanceof ViewGroup.MarginLayoutParams))
            {
              localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localLayoutParams;
              n = localMarginLayoutParams.leftMargin;
              n = localMarginLayoutParams.rightMargin + n;
            }
            else
            {
              n = 0;
            }
            localView.measure(getChildMeasureSpec(paramInt1, i1 + i2 + n, localLayoutParams.width), m);
          }
          else
          {
            localView.measure(View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, Integer.MIN_VALUE), m);
          }
        }
        paramInt2 += 1;
      }
    }
    paramInt1 = 0;
    while (paramInt1 < i4)
    {
      localView = getChildAt(paramInt1);
      if (localView != null)
      {
        localLayoutParams = localView.getLayoutParams();
        if (localLayoutParams.width > 0) {
          m = View.MeasureSpec.makeMeasureSpec(localLayoutParams.width, Integer.MIN_VALUE);
        } else {
          m = View.MeasureSpec.makeMeasureSpec(0, 0);
        }
        if (localView.getLayoutParams().height == -1)
        {
          if ((localLayoutParams instanceof ViewGroup.MarginLayoutParams))
          {
            localMarginLayoutParams = (ViewGroup.MarginLayoutParams)localLayoutParams;
            n = localMarginLayoutParams.topMargin;
            n = localMarginLayoutParams.bottomMargin + n;
          }
          else
          {
            n = 0;
          }
          localView.measure(m, getChildMeasureSpec(paramInt2, getPaddingTop() + getPaddingBottom() + n, localLayoutParams.height));
        }
        else
        {
          localView.measure(m, View.MeasureSpec.makeMeasureSpec(localLayoutParams.height, Integer.MIN_VALUE));
        }
      }
      paramInt1 += 1;
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.jdField_f_of_type_Boolean) {
      return super.onTouchEvent(paramMotionEvent);
    }
    if (this.jdField_a_of_type_AndroidViewVelocityTracker == null) {
      this.jdField_a_of_type_AndroidViewVelocityTracker = VelocityTracker.obtain();
    }
    this.jdField_a_of_type_AndroidViewVelocityTracker.addMovement(paramMotionEvent);
    switch (paramMotionEvent.getAction())
    {
    default: 
      return false;
    case 2: 
      if (this.jdField_a_of_type_Byte == 0)
      {
        float f1 = Math.abs(paramMotionEvent.getX() - this.jdField_a_of_type_Float);
        float f2 = Math.abs(paramMotionEvent.getY() - this.jdField_b_of_type_Float);
        if ((Math.abs(paramMotionEvent.getY() - this.jdField_b_of_type_Float) > 10.0F) && (f2 > f1)) {
          this.jdField_a_of_type_Byte = 1;
        }
      }
      if (this.jdField_a_of_type_Byte == 1)
      {
        int m = (int)(this.jdField_b_of_type_Float - paramMotionEvent.getY());
        if (Math.abs(m) > 0)
        {
          if (!this.jdField_e_of_type_Boolean)
          {
            this.jdField_e_of_type_Boolean = true;
            for (ViewParent localViewParent = getParent(); (localViewParent != null) && ((localViewParent.getParent() instanceof ViewParent)); localViewParent = localViewParent.getParent()) {}
          }
          c();
          a(m, true);
          invalidate();
          this.jdField_b_of_type_Float = paramMotionEvent.getY();
          this.jdField_a_of_type_Byte = 1;
          return true;
        }
      }
      break;
    case 1: 
    case 3: 
      if (this.jdField_a_of_type_Byte == 1) {
        e();
      }
      this.jdField_a_of_type_Byte = 0;
      paramMotionEvent = this.jdField_a_of_type_AndroidViewVelocityTracker;
      if (paramMotionEvent != null)
      {
        paramMotionEvent.recycle();
        this.jdField_a_of_type_AndroidViewVelocityTracker = null;
        return false;
      }
      break;
    case 0: 
      return true;
    }
    return false;
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    if (!paramBoolean) {
      this.jdField_f_of_type_Boolean = true;
    }
    super.onWindowFocusChanged(paramBoolean);
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    int m = this.jdField_a_of_type_Int;
    int n = m - paramInt1;
    if (paramInt2 <= 0)
    {
      b(n);
      this.jdField_a_of_type_Int -= n;
      invalidate();
      Object localObject = this.jdField_a_of_type_JavaUtilArrayList;
      if ((localObject != null) && (((ArrayList)localObject).size() > 0))
      {
        localObject = this.jdField_a_of_type_JavaUtilArrayList.iterator();
        while (((Iterator)localObject).hasNext()) {
          ((MttScrollViewListener)((Iterator)localObject).next()).onScroll(this.jdField_a_of_type_Int);
        }
      }
    }
    else if (-paramInt1 != m)
    {
      this.jdField_b_of_type_Boolean = true;
      this.jdField_c_of_type_Boolean = true;
      this.jdField_d_of_type_Int = m;
      this.jdField_a_of_type_ComTencentSmttListboxMttScroller.a(0, m, 0, -n, paramInt2);
      invalidate();
    }
  }
  
  public static abstract interface MttScrollViewListener
  {
    public abstract void onScroll(int paramInt);
    
    public abstract void onScrollEnd();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\listbox\MttScrollView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */