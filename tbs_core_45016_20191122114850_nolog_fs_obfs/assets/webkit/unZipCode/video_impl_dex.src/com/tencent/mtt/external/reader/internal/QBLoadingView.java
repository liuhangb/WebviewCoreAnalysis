package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.mtt.external.reader.utils.ViewCompat;
import com.tencent.tbs.common.resources.TBSResources;

public class QBLoadingView
  extends LinearLayout
{
  float jdField_a_of_type_Float = 0.0F;
  int jdField_a_of_type_Int = -16777216;
  Bitmap jdField_a_of_type_AndroidGraphicsBitmap;
  Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  LinearLayout.LayoutParams jdField_a_of_type_AndroidWidgetLinearLayout$LayoutParams;
  TextView jdField_a_of_type_AndroidWidgetTextView;
  private LayoutStyle jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$LayoutStyle;
  a jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a;
  private Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      QBLoadingView.this.forceLayout();
      QBLoadingView localQBLoadingView = QBLoadingView.this;
      localQBLoadingView.measure(QBLoadingView.a(localQBLoadingView), QBLoadingView.b(QBLoadingView.this));
      localQBLoadingView = QBLoadingView.this;
      localQBLoadingView.layout(localQBLoadingView.getLeft(), QBLoadingView.this.getTop(), QBLoadingView.this.getRight(), QBLoadingView.this.getBottom());
      QBLoadingView.this.invalidate();
    }
  };
  String jdField_a_of_type_JavaLangString;
  boolean jdField_a_of_type_Boolean = false;
  int jdField_b_of_type_Int;
  LinearLayout.LayoutParams jdField_b_of_type_AndroidWidgetLinearLayout$LayoutParams;
  private boolean jdField_b_of_type_Boolean = false;
  private int jdField_c_of_type_Int;
  private boolean jdField_c_of_type_Boolean = false;
  private int jdField_d_of_type_Int;
  private boolean jdField_d_of_type_Boolean;
  private boolean e;
  private boolean f;
  
  public QBLoadingView(Context paramContext, LayoutStyle paramLayoutStyle, boolean paramBoolean)
  {
    super(paramContext);
    ViewCompat.setDefaultLayotuDirection(this);
    int i = TBSResources.getDimensionPixelSize("reader_uifw_control_textsize_default");
    int j = TBSResources.getDimensionPixelSize("reader_uifw_control_textsize_default");
    this.jdField_b_of_type_Int = TBSResources.getDimensionPixelSize("reader_uifw_control_loading_default");
    setGravity(17);
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a = new a(paramContext);
    this.jdField_a_of_type_AndroidWidgetTextView = new TextView(paramContext);
    this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(0, i);
    this.jdField_a_of_type_AndroidWidgetTextView.setGravity(17);
    ViewCompat.setDefaultLayotuDirection(this.jdField_a_of_type_AndroidWidgetTextView);
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$LayoutStyle = paramLayoutStyle;
    switch (2.a[paramLayoutStyle.ordinal()])
    {
    default: 
      break;
    case 3: 
      setOrientation(0);
      addView(this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a, new LinearLayout.LayoutParams(-2, -2));
      this.jdField_a_of_type_AndroidWidgetLinearLayout$LayoutParams = new LinearLayout.LayoutParams(-2, -2);
      paramContext = this.jdField_a_of_type_AndroidWidgetLinearLayout$LayoutParams;
      paramContext.leftMargin = this.jdField_b_of_type_Int;
      addView(this.jdField_a_of_type_AndroidWidgetTextView, paramContext);
      setGravity(17);
      break;
    case 2: 
      setOrientation(1);
      addView(this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a, new LinearLayout.LayoutParams(-2, -2));
      this.jdField_b_of_type_AndroidWidgetLinearLayout$LayoutParams = new LinearLayout.LayoutParams(-2, -2);
      paramContext = this.jdField_b_of_type_AndroidWidgetLinearLayout$LayoutParams;
      paramContext.topMargin = this.jdField_b_of_type_Int;
      addView(this.jdField_a_of_type_AndroidWidgetTextView, paramContext);
      break;
    case 1: 
      setOrientation(0);
      addView(this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a, new LinearLayout.LayoutParams(-2, -2));
      this.jdField_a_of_type_AndroidWidgetLinearLayout$LayoutParams = new LinearLayout.LayoutParams(-2, -2);
      paramContext = this.jdField_a_of_type_AndroidWidgetLinearLayout$LayoutParams;
      paramContext.leftMargin = this.jdField_b_of_type_Int;
      addView(this.jdField_a_of_type_AndroidWidgetTextView, paramContext);
      setGravity(17);
    }
    if (paramBoolean)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a.setTextSize(0, j);
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a.setGravity(17);
    }
    this.jdField_a_of_type_AndroidWidgetTextView.setVisibility(8);
    setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
  }
  
  private void a()
  {
    if (this.e)
    {
      if (!this.jdField_d_of_type_Boolean) {
        return;
      }
      this.e = false;
    }
    int i;
    if (!this.jdField_b_of_type_Boolean)
    {
      i = getMeasuredWidth();
      super.onMeasure(this.jdField_c_of_type_Int, this.jdField_d_of_type_Int);
      if (i == getMeasuredWidth()) {
        this.jdField_b_of_type_Boolean = true;
      }
    }
    if (!this.jdField_c_of_type_Boolean)
    {
      i = getMeasuredHeight();
      super.onMeasure(this.jdField_c_of_type_Int, this.jdField_d_of_type_Int);
      if (i == getMeasuredHeight()) {
        this.jdField_c_of_type_Boolean = true;
      }
    }
    if ((this.jdField_b_of_type_Boolean) && (this.jdField_c_of_type_Boolean)) {
      this.f = true;
    }
    if (this.jdField_d_of_type_Boolean) {
      requestLayout();
    }
  }
  
  public float getCurrDegree()
  {
    return this.jdField_a_of_type_Float;
  }
  
  public boolean getIsLoading()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getMode(paramInt2);
    if (i == 1073741824) {
      this.jdField_b_of_type_Boolean = true;
    }
    if (j == 1073741824) {
      this.jdField_c_of_type_Boolean = true;
    }
    this.jdField_c_of_type_Int = paramInt1;
    this.jdField_d_of_type_Int = paramInt2;
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return super.onTouchEvent(paramMotionEvent);
  }
  
  public void requestLayout()
  {
    if (this.e)
    {
      this.jdField_d_of_type_Boolean = true;
      return;
    }
    if ((this.jdField_b_of_type_Boolean) && (this.jdField_c_of_type_Boolean) && (this.f))
    {
      this.f = false;
      removeCallbacks(this.jdField_a_of_type_JavaLangRunnable);
      post(this.jdField_a_of_type_JavaLangRunnable);
      return;
    }
    this.jdField_d_of_type_Boolean = false;
    super.requestLayout();
  }
  
  public void setCurrDegree(int paramInt)
  {
    this.jdField_a_of_type_Float = paramInt;
  }
  
  public void setDetailProgress(String paramString)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a.setText(paramString);
  }
  
  public void setFontSize(int paramInt)
  {
    this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(0, paramInt);
  }
  
  public void setImageVisibility(int paramInt)
  {
    this.e = true;
    a locala = this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a;
    if (locala != null) {
      locala.setVisibility(paramInt);
    }
    a();
  }
  
  public void setLoadingBitmap(Bitmap paramBitmap)
  {
    this.jdField_a_of_type_AndroidGraphicsBitmap = paramBitmap;
  }
  
  public void setLoadingDrawable(Drawable paramDrawable)
  {
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = paramDrawable;
  }
  
  public void setLoadingIconVisible(int paramInt)
  {
    setImageVisibility(paramInt);
  }
  
  public void setProgress(int paramInt)
  {
    a locala = this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt);
    localStringBuilder.append("%");
    locala.setText(localStringBuilder.toString());
  }
  
  public void setProgressFontSize(int paramInt)
  {
    this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(0, paramInt);
  }
  
  public void setProgressText(String paramString)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a.setText(paramString);
  }
  
  public void setProgressTextColor(int paramInt)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$a.setTextColor(paramInt);
  }
  
  public void setSpaceBetween(int paramInt)
  {
    this.jdField_b_of_type_Int = paramInt;
    LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)this.jdField_a_of_type_AndroidWidgetTextView.getLayoutParams();
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$LayoutStyle == LayoutStyle.ImageLeftTextRight) {
      localLayoutParams.leftMargin = paramInt;
    } else if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$LayoutStyle == LayoutStyle.ImageTopTextBottom) {
      localLayoutParams.topMargin = paramInt;
    }
    this.jdField_a_of_type_AndroidWidgetTextView.setLayoutParams(localLayoutParams);
  }
  
  public void setSpaceHeight(int paramInt)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalQBLoadingView$LayoutStyle == LayoutStyle.ImageLeftTextRight)
    {
      localLayoutParams = this.jdField_a_of_type_AndroidWidgetLinearLayout$LayoutParams;
      localLayoutParams.leftMargin = paramInt;
      updateViewLayout(this.jdField_a_of_type_AndroidWidgetTextView, localLayoutParams);
      return;
    }
    LinearLayout.LayoutParams localLayoutParams = this.jdField_b_of_type_AndroidWidgetLinearLayout$LayoutParams;
    localLayoutParams.topMargin = paramInt;
    updateViewLayout(this.jdField_a_of_type_AndroidWidgetTextView, localLayoutParams);
  }
  
  public void setText(String paramString)
  {
    if (!TextUtils.equals(paramString, this.jdField_a_of_type_JavaLangString))
    {
      this.jdField_a_of_type_AndroidWidgetTextView.setVisibility(0);
      this.jdField_a_of_type_JavaLangString = paramString;
      this.jdField_a_of_type_AndroidWidgetTextView.setText(this.jdField_a_of_type_JavaLangString);
      this.e = true;
      a();
    }
  }
  
  public void setTextColor(int paramInt)
  {
    TextView localTextView = this.jdField_a_of_type_AndroidWidgetTextView;
    if (localTextView != null) {
      localTextView.setTextColor(paramInt);
    }
  }
  
  public void setTextColor(ColorStateList paramColorStateList)
  {
    TextView localTextView = this.jdField_a_of_type_AndroidWidgetTextView;
    if (localTextView != null) {
      localTextView.setTextColor(paramColorStateList);
    }
  }
  
  public void setTextSize(int paramInt)
  {
    this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(0, paramInt);
  }
  
  public void setTextVisibility(int paramInt)
  {
    this.e = true;
    TextView localTextView = this.jdField_a_of_type_AndroidWidgetTextView;
    if (localTextView != null) {
      localTextView.setVisibility(paramInt);
    }
    a();
  }
  
  public void startLoading()
  {
    startLoading(0.0F);
  }
  
  public void startLoading(float paramFloat)
  {
    this.jdField_a_of_type_Boolean = true;
    this.jdField_a_of_type_Float = paramFloat;
    invalidate();
  }
  
  public void stopLoading()
  {
    this.jdField_a_of_type_Boolean = false;
  }
  
  public void stopLoading(float paramFloat)
  {
    this.jdField_a_of_type_Boolean = false;
    this.jdField_a_of_type_Float = paramFloat;
    invalidate();
  }
  
  public static enum LayoutStyle
  {
    private LayoutStyle() {}
  }
  
  class a
    extends TextView
  {
    Paint jdField_a_of_type_AndroidGraphicsPaint = new Paint();
    
    public a(Context paramContext)
    {
      super();
      setBackgroundColor(0);
      ViewCompat.setDefaultLayotuDirection(this);
    }
    
    protected void onDraw(Canvas paramCanvas)
    {
      if ((QBLoadingView.this.jdField_a_of_type_AndroidGraphicsBitmap != null) && (!QBLoadingView.this.jdField_a_of_type_AndroidGraphicsBitmap.isRecycled()))
      {
        paramCanvas.save();
        paramCanvas.rotate(QBLoadingView.this.jdField_a_of_type_Float, getWidth() / 2, getHeight() / 2);
        paramCanvas.drawBitmap(QBLoadingView.this.jdField_a_of_type_AndroidGraphicsBitmap, 0.0F, 0.0F, this.jdField_a_of_type_AndroidGraphicsPaint);
        paramCanvas.restore();
      }
      if (QBLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable != null)
      {
        QBLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setBounds(0, 0, QBLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth(), QBLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight());
        paramCanvas.save();
        paramCanvas.rotate(QBLoadingView.this.jdField_a_of_type_Float, QBLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth() / 2, QBLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight() / 2);
        QBLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.draw(paramCanvas);
        paramCanvas.restore();
      }
      if (QBLoadingView.this.jdField_a_of_type_Boolean)
      {
        QBLoadingView localQBLoadingView = QBLoadingView.this;
        localQBLoadingView.jdField_a_of_type_Float += 8.0F;
        localQBLoadingView = QBLoadingView.this;
        localQBLoadingView.jdField_a_of_type_Float %= 360.0F;
        ViewCompat.postInvalidateOnAnimation(this);
      }
      super.onDraw(paramCanvas);
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      Bitmap localBitmap = QBLoadingView.this.jdField_a_of_type_AndroidGraphicsBitmap;
      int j = 0;
      int i;
      if (localBitmap != null)
      {
        j = QBLoadingView.this.jdField_a_of_type_AndroidGraphicsBitmap.getHeight();
        i = QBLoadingView.this.jdField_a_of_type_AndroidGraphicsBitmap.getWidth();
      }
      else
      {
        i = 0;
      }
      int m = i;
      int k = j;
      if (QBLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable != null)
      {
        k = Math.max(j, QBLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight());
        m = Math.max(i, QBLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth());
      }
      if ((k != 0) && (m != 0))
      {
        setMeasuredDimension(m, k);
        return;
      }
      super.onMeasure(paramInt1, paramInt2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\QBLoadingView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */