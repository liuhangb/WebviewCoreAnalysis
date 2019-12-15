package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.mtt.external.reader.utils.ViewCompat;
import com.tencent.tbs.common.resources.TBSResources;

public class TBSLoadingView
  extends LinearLayout
{
  float jdField_a_of_type_Float = 0.0F;
  int jdField_a_of_type_Int = -16777216;
  Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  LinearLayout.LayoutParams jdField_a_of_type_AndroidWidgetLinearLayout$LayoutParams;
  LinearLayout jdField_a_of_type_AndroidWidgetLinearLayout = null;
  TextView jdField_a_of_type_AndroidWidgetTextView;
  MttFileLoadingView.FileLoadingCallback jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoadingView$FileLoadingCallback = null;
  a jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView$a;
  private Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      TBSLoadingView.this.forceLayout();
      TBSLoadingView localTBSLoadingView = TBSLoadingView.this;
      localTBSLoadingView.measure(TBSLoadingView.a(localTBSLoadingView), TBSLoadingView.b(TBSLoadingView.this));
      localTBSLoadingView = TBSLoadingView.this;
      localTBSLoadingView.layout(localTBSLoadingView.getLeft(), TBSLoadingView.this.getTop(), TBSLoadingView.this.getRight(), TBSLoadingView.this.getBottom());
      TBSLoadingView.this.invalidate();
    }
  };
  String jdField_a_of_type_JavaLangString;
  boolean jdField_a_of_type_Boolean = false;
  int jdField_b_of_type_Int;
  LinearLayout.LayoutParams jdField_b_of_type_AndroidWidgetLinearLayout$LayoutParams;
  TextView jdField_b_of_type_AndroidWidgetTextView;
  private boolean jdField_b_of_type_Boolean = false;
  private int jdField_c_of_type_Int;
  TextView jdField_c_of_type_AndroidWidgetTextView;
  private boolean jdField_c_of_type_Boolean = false;
  private int jdField_d_of_type_Int;
  private boolean jdField_d_of_type_Boolean;
  private boolean e;
  private boolean f;
  
  public TBSLoadingView(Context paramContext, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramContext);
    ViewCompat.setDefaultLayotuDirection(this);
    int i = TBSResources.getDimensionPixelSize("reader_uifw_control_textsize_default");
    int j = TBSResources.getDimensionPixelSize("reader_uifw_control_textsize_default");
    this.jdField_b_of_type_Int = TBSResources.getDimensionPixelSize("reader_uifw_control_loading_default");
    setGravity(17);
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView$a = new a(paramContext);
    this.jdField_a_of_type_AndroidWidgetTextView = new TextView(paramContext);
    Object localObject = this.jdField_a_of_type_AndroidWidgetTextView;
    float f1 = i;
    ((TextView)localObject).setTextSize(0, f1);
    this.jdField_a_of_type_AndroidWidgetTextView.setGravity(17);
    ViewCompat.setDefaultLayotuDirection(this.jdField_a_of_type_AndroidWidgetTextView);
    this.jdField_b_of_type_AndroidWidgetTextView = new TextView(paramContext);
    this.jdField_b_of_type_AndroidWidgetTextView.setTextSize(0, f1);
    this.jdField_b_of_type_AndroidWidgetTextView.setGravity(17);
    ViewCompat.setDefaultLayotuDirection(this.jdField_b_of_type_AndroidWidgetTextView);
    setOrientation(1);
    if (this.jdField_a_of_type_AndroidWidgetLinearLayout == null)
    {
      this.jdField_a_of_type_AndroidWidgetLinearLayout = new LinearLayout(paramContext);
      this.jdField_a_of_type_AndroidWidgetLinearLayout.setGravity(16);
      this.jdField_a_of_type_AndroidWidgetLinearLayout.setOrientation(0);
      this.jdField_a_of_type_AndroidWidgetLinearLayout.addView(this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView$a, new LinearLayout.LayoutParams(-2, -2));
      this.jdField_a_of_type_AndroidWidgetLinearLayout$LayoutParams = new LinearLayout.LayoutParams(-2, -2);
      localObject = this.jdField_a_of_type_AndroidWidgetLinearLayout$LayoutParams;
      ((LinearLayout.LayoutParams)localObject).leftMargin = this.jdField_b_of_type_Int;
      this.jdField_a_of_type_AndroidWidgetLinearLayout.addView(this.jdField_a_of_type_AndroidWidgetTextView, (ViewGroup.LayoutParams)localObject);
      this.jdField_a_of_type_AndroidWidgetLinearLayout.addView(this.jdField_b_of_type_AndroidWidgetTextView, new LinearLayout.LayoutParams(-2, -2));
    }
    addView(this.jdField_a_of_type_AndroidWidgetLinearLayout, new LinearLayout.LayoutParams(-2, -2));
    this.jdField_c_of_type_AndroidWidgetTextView = new TextView(paramContext);
    this.jdField_c_of_type_AndroidWidgetTextView.setTextSize(0, f1);
    this.jdField_c_of_type_AndroidWidgetTextView.setTextColor(TBSResources.getColor("reader_theme_common_color_b1"));
    this.jdField_c_of_type_AndroidWidgetTextView.setGravity(17);
    this.jdField_c_of_type_AndroidWidgetTextView.setText(TBSResources.getString("reader_cancel_loading"));
    this.jdField_c_of_type_AndroidWidgetTextView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (TBSLoadingView.this.a != null) {
          TBSLoadingView.this.a.handleLoadingCancel();
        }
      }
    });
    ViewCompat.setDefaultLayotuDirection(this.jdField_c_of_type_AndroidWidgetTextView);
    this.jdField_b_of_type_AndroidWidgetLinearLayout$LayoutParams = new LinearLayout.LayoutParams(-2, -2);
    paramContext = this.jdField_b_of_type_AndroidWidgetLinearLayout$LayoutParams;
    paramContext.topMargin = this.jdField_b_of_type_Int;
    addView(this.jdField_c_of_type_AndroidWidgetTextView, paramContext);
    if (!paramBoolean1) {
      this.jdField_c_of_type_AndroidWidgetTextView.setVisibility(8);
    }
    if (paramBoolean2)
    {
      this.jdField_b_of_type_AndroidWidgetTextView.setTextSize(0, j);
      this.jdField_b_of_type_AndroidWidgetTextView.setGravity(17);
    }
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
  
  public void setCallbackListener(MttFileLoadingView.FileLoadingCallback paramFileLoadingCallback)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoadingView$FileLoadingCallback = paramFileLoadingCallback;
  }
  
  public void setCancelable(boolean paramBoolean)
  {
    TextView localTextView = this.jdField_c_of_type_AndroidWidgetTextView;
    if (localTextView == null) {
      return;
    }
    int i;
    if (paramBoolean) {
      i = 0;
    } else {
      i = 8;
    }
    localTextView.setVisibility(i);
  }
  
  public void setCurrDegree(int paramInt)
  {
    this.jdField_a_of_type_Float = paramInt;
  }
  
  public void setDetailProgress(String paramString)
  {
    if (paramString == "")
    {
      this.jdField_b_of_type_AndroidWidgetTextView.setVisibility(8);
      return;
    }
    if (this.jdField_b_of_type_AndroidWidgetTextView.getVisibility() != 0) {
      this.jdField_b_of_type_AndroidWidgetTextView.setVisibility(0);
    }
    TextView localTextView = this.jdField_b_of_type_AndroidWidgetTextView;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("(");
    localStringBuilder.append(paramString);
    localStringBuilder.append(")");
    localTextView.setText(localStringBuilder.toString());
  }
  
  public void setFontSize(int paramInt)
  {
    this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(0, paramInt);
  }
  
  public void setImageVisibility(int paramInt)
  {
    this.e = true;
    a locala = this.jdField_a_of_type_ComTencentMttExternalReaderInternalTBSLoadingView$a;
    if (locala != null) {
      locala.setVisibility(paramInt);
    }
    a();
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
    TextView localTextView = this.jdField_b_of_type_AndroidWidgetTextView;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt);
    localStringBuilder.append("%");
    localTextView.setText(localStringBuilder.toString());
  }
  
  public void setProgressFontSize(int paramInt)
  {
    this.jdField_a_of_type_AndroidWidgetTextView.setTextSize(0, paramInt);
  }
  
  public void setProgressText(String paramString)
  {
    this.jdField_b_of_type_AndroidWidgetTextView.setText(paramString);
  }
  
  public void setProgressTextColor(int paramInt)
  {
    this.jdField_b_of_type_AndroidWidgetTextView.setTextColor(paramInt);
  }
  
  public void setSpaceBetween(int paramInt)
  {
    this.jdField_b_of_type_Int = paramInt;
    LinearLayout.LayoutParams localLayoutParams = (LinearLayout.LayoutParams)this.jdField_a_of_type_AndroidWidgetTextView.getLayoutParams();
    localLayoutParams.leftMargin = paramInt;
    this.jdField_a_of_type_AndroidWidgetTextView.setLayoutParams(localLayoutParams);
  }
  
  public void setSpaceHeight(int paramInt)
  {
    LinearLayout.LayoutParams localLayoutParams = this.jdField_a_of_type_AndroidWidgetLinearLayout$LayoutParams;
    localLayoutParams.leftMargin = paramInt;
    this.jdField_a_of_type_AndroidWidgetLinearLayout.updateViewLayout(this.jdField_a_of_type_AndroidWidgetTextView, localLayoutParams);
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
  
  class a
    extends ImageView
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
      if (TBSLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable != null)
      {
        TBSLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.setBounds(0, 0, TBSLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth(), TBSLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight());
        paramCanvas.save();
        paramCanvas.rotate(TBSLoadingView.this.jdField_a_of_type_Float, TBSLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth() / 2, TBSLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight() / 2);
        TBSLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.draw(paramCanvas);
        paramCanvas.restore();
      }
      if (TBSLoadingView.this.jdField_a_of_type_Boolean)
      {
        TBSLoadingView localTBSLoadingView = TBSLoadingView.this;
        localTBSLoadingView.jdField_a_of_type_Float += 8.0F;
        localTBSLoadingView = TBSLoadingView.this;
        localTBSLoadingView.jdField_a_of_type_Float %= 360.0F;
        ViewCompat.postInvalidateOnAnimation(this);
      }
      super.onDraw(paramCanvas);
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      Drawable localDrawable = TBSLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
      int j = 0;
      int i;
      if (localDrawable != null)
      {
        i = Math.max(0, TBSLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight());
        j = Math.max(0, TBSLoadingView.this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth());
      }
      else
      {
        i = 0;
      }
      if ((i != 0) && (j != 0))
      {
        setMeasuredDimension(j, i);
        return;
      }
      super.onMeasure(paramInt1, paramInt2);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\TBSLoadingView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */