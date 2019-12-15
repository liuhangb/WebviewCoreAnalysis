package com.tencent.mtt.external.reader.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ImageView;
import com.tencent.common.utils.GdiMeasureImpl;
import com.tencent.tbs.common.resources.TBSResources;

public class MttFileLoading
  extends ImageView
{
  private float jdField_a_of_type_Float = 0.0F;
  private int jdField_a_of_type_Int;
  Context jdField_a_of_type_AndroidContentContext;
  private Bitmap jdField_a_of_type_AndroidGraphicsBitmap;
  private Paint jdField_a_of_type_AndroidGraphicsPaint = new Paint();
  private Drawable jdField_a_of_type_AndroidGraphicsDrawableDrawable;
  private GdiMeasureImpl jdField_a_of_type_ComTencentCommonUtilsGdiMeasureImpl;
  private a jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading$a = new a(Looper.getMainLooper());
  private String jdField_a_of_type_JavaLangString;
  private boolean jdField_a_of_type_Boolean = false;
  private int jdField_b_of_type_Int = 0;
  private Bitmap jdField_b_of_type_AndroidGraphicsBitmap;
  private String jdField_b_of_type_JavaLangString;
  private int c;
  private int d;
  private int e;
  private int f = 0;
  private int g = 255;
  private int h;
  protected int mProgressTextColor = -16777216;
  protected int mTextColor = -16777216;
  
  public MttFileLoading(Context paramContext)
  {
    super(paramContext);
    this.jdField_a_of_type_AndroidContentContext = paramContext;
    this.jdField_b_of_type_Int = TBSResources.getDimensionPixelSize("reader_dp_6");
    this.d = TBSResources.getDimensionPixelSize("reader_textsize_12");
    this.jdField_a_of_type_Int = TBSResources.getDimensionPixelSize("reader_control_loading_default");
    this.e = TBSResources.getDimensionPixelSize("reader_dp_15");
    this.jdField_a_of_type_AndroidGraphicsBitmap = TBSResources.getBitmap("reader_file_loading_fg");
    this.jdField_b_of_type_AndroidGraphicsBitmap = TBSResources.getBitmap("reader_file_loading_text");
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = TBSResources.getDrawable("reader_file_loading_bg");
  }
  
  private void a(Canvas paramCanvas)
  {
    b(paramCanvas);
    if (this.jdField_a_of_type_Boolean)
    {
      this.jdField_a_of_type_Float += 12.0F;
      this.jdField_a_of_type_Float %= 360.0F;
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading$a.removeMessages(0);
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalMttFileLoading$a.sendEmptyMessage(0);
    }
  }
  
  private void b(Canvas paramCanvas)
  {
    int i = this.f;
    this.jdField_a_of_type_AndroidGraphicsPaint.reset();
    Object localObject = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    int m = 0;
    int j;
    if (localObject != null)
    {
      j = (getWidth() - this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth()) / 2;
      localObject = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
      ((Drawable)localObject).setBounds(j, i, ((Drawable)localObject).getIntrinsicWidth() + j, this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight() + i);
      paramCanvas.save();
      paramCanvas.rotate(this.jdField_a_of_type_Float, this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicWidth() / 2 + j, this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.getIntrinsicHeight() / 2 + i);
      this.jdField_a_of_type_AndroidGraphicsDrawableDrawable.draw(paramCanvas);
      paramCanvas.restore();
    }
    else
    {
      j = 0;
    }
    localObject = this.jdField_a_of_type_AndroidGraphicsBitmap;
    if (localObject != null) {
      drawImage(paramCanvas, this.jdField_a_of_type_AndroidGraphicsPaint, j, i, (Bitmap)localObject);
    }
    if (this.jdField_b_of_type_AndroidGraphicsBitmap != null)
    {
      localObject = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
      if (localObject != null) {
        i = ((Drawable)localObject).getIntrinsicHeight();
      } else {
        i = 0;
      }
      i = i + this.f + this.jdField_b_of_type_Int;
      j = (getWidth() - this.jdField_b_of_type_AndroidGraphicsBitmap.getWidth()) / 2;
      drawImage(paramCanvas, this.jdField_a_of_type_AndroidGraphicsPaint, j, i, this.jdField_b_of_type_AndroidGraphicsBitmap);
    }
    int k = i;
    if (this.jdField_b_of_type_JavaLangString != null)
    {
      k = this.e;
      localObject = this.jdField_b_of_type_AndroidGraphicsBitmap;
      j = m;
      if (localObject != null) {
        j = ((Bitmap)localObject).getHeight();
      }
      k = i + (k + j);
      this.jdField_a_of_type_AndroidGraphicsPaint.setColor(this.mTextColor);
      this.jdField_a_of_type_AndroidGraphicsPaint.setTextSize(this.d);
      this.jdField_a_of_type_AndroidGraphicsPaint.setAlpha(this.g);
      i = (getWidth() - (this.c + this.h + this.jdField_a_of_type_Int)) / 2;
      drawText(paramCanvas, this.jdField_a_of_type_AndroidGraphicsPaint, i, k, this.jdField_b_of_type_JavaLangString);
      j = i + this.c;
    }
    if (this.jdField_a_of_type_JavaLangString != null)
    {
      this.jdField_a_of_type_AndroidGraphicsPaint.setColor(this.mTextColor);
      this.jdField_a_of_type_AndroidGraphicsPaint.setTextSize(this.d);
      this.jdField_a_of_type_AndroidGraphicsPaint.setAlpha(this.g);
      i = this.jdField_a_of_type_Int;
      drawText(paramCanvas, this.jdField_a_of_type_AndroidGraphicsPaint, j + i, k, this.jdField_a_of_type_JavaLangString);
    }
  }
  
  public static void drawImage(Canvas paramCanvas, Paint paramPaint, int paramInt1, int paramInt2, Bitmap paramBitmap)
  {
    if (paramBitmap != null)
    {
      if (paramBitmap.isRecycled()) {
        return;
      }
      paramPaint.setFilterBitmap(true);
      paramCanvas.drawBitmap(paramBitmap, null, new Rect(paramInt1, paramInt2, paramBitmap.getWidth() + paramInt1, paramBitmap.getHeight() + paramInt2), paramPaint);
      paramPaint.setFilterBitmap(false);
      return;
    }
  }
  
  public static void drawText(Canvas paramCanvas, Paint paramPaint, float paramFloat1, float paramFloat2, String paramString)
  {
    paramPaint.setAntiAlias(true);
    paramPaint.setTextAlign(Paint.Align.LEFT);
    paramPaint.setStyle(Paint.Style.FILL);
    paramPaint.getFontMetricsInt(new Paint.FontMetricsInt());
    paramCanvas.drawText(paramString, paramFloat1, paramFloat2 - paramPaint.ascent() - TBSResources.getDimensionPixelSize("reader_dp_1"), paramPaint);
    paramPaint.setAntiAlias(false);
  }
  
  public int getContentHeight()
  {
    Object localObject = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    if (localObject != null) {
      j = ((Drawable)localObject).getIntrinsicHeight();
    } else {
      j = 0;
    }
    localObject = this.jdField_b_of_type_AndroidGraphicsBitmap;
    int i = j;
    if (localObject != null) {
      i = j + ((Bitmap)localObject).getHeight();
    }
    int j = i;
    if (i != 0)
    {
      j = i;
      if (this.jdField_b_of_type_JavaLangString != null) {
        j = i + this.e + this.d;
      }
    }
    return j + this.jdField_b_of_type_Int;
  }
  
  public int getContentWidth()
  {
    return getWidth();
  }
  
  public float getCurrDegree()
  {
    return this.jdField_a_of_type_Float;
  }
  
  public boolean getIsLoading()
  {
    return this.jdField_a_of_type_Boolean;
  }
  
  public void layout()
  {
    if (this.jdField_b_of_type_JavaLangString != null)
    {
      if (this.jdField_a_of_type_ComTencentCommonUtilsGdiMeasureImpl == null) {
        this.jdField_a_of_type_ComTencentCommonUtilsGdiMeasureImpl = new GdiMeasureImpl(this.jdField_a_of_type_AndroidGraphicsPaint);
      }
      this.jdField_a_of_type_ComTencentCommonUtilsGdiMeasureImpl.setFontSize(this.d);
      this.c = this.jdField_a_of_type_ComTencentCommonUtilsGdiMeasureImpl.getStringWidth(this.jdField_b_of_type_JavaLangString);
      this.h = this.jdField_a_of_type_ComTencentCommonUtilsGdiMeasureImpl.getStringWidth(this.jdField_a_of_type_JavaLangString);
    }
    this.f = ((getHeight() - getContentHeight()) / 2);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    a(paramCanvas);
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onLayout(paramBoolean, paramInt1, paramInt2, paramInt3, paramInt4);
    layout();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = getContentHeight();
    int j = getContentWidth();
    if ((i != 0) && (j != 0))
    {
      paramInt1 = TBSResources.getDimensionPixelSize("reader_dp_20");
      setMeasuredDimension(j + paramInt1, i + paramInt1);
      return;
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void setAlpha(int paramInt)
  {
    super.setAlpha(paramInt);
    Drawable localDrawable = this.jdField_a_of_type_AndroidGraphicsDrawableDrawable;
    if (localDrawable != null) {
      localDrawable.setAlpha(paramInt);
    }
  }
  
  public void setCurrDegree(int paramInt)
  {
    this.jdField_a_of_type_Float = paramInt;
  }
  
  public void setDetailProgress(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  public void setFontSize(int paramInt)
  {
    this.d = paramInt;
  }
  
  public void setProgress(int paramInt)
  {
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(paramInt);
    localStringBuilder.append("%");
    this.jdField_a_of_type_JavaLangString = localStringBuilder.toString();
  }
  
  public void setProgressText(String paramString)
  {
    this.jdField_a_of_type_JavaLangString = paramString;
  }
  
  public void setSpaceHeight(int paramInt)
  {
    this.e = paramInt;
  }
  
  public void setText(String paramString)
  {
    this.jdField_b_of_type_JavaLangString = paramString;
  }
  
  public void setTextAlpha(int paramInt)
  {
    this.g = paramInt;
  }
  
  public void setTextColor(int paramInt)
  {
    this.mTextColor = paramInt;
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
  
  public void switchSkin()
  {
    this.jdField_a_of_type_AndroidGraphicsDrawableDrawable = TBSResources.getDrawable("reader_file_loading_bg");
    this.jdField_a_of_type_AndroidGraphicsBitmap = TBSResources.getBitmap("reader_file_loading_fg");
    this.jdField_b_of_type_AndroidGraphicsBitmap = TBSResources.getBitmap("reader_file_loading_text");
  }
  
  class a
    extends Handler
  {
    public a(Looper paramLooper)
    {
      super();
    }
    
    public void handleMessage(Message paramMessage)
    {
      MttFileLoading.this.invalidate();
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\internal\MttFileLoading.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */