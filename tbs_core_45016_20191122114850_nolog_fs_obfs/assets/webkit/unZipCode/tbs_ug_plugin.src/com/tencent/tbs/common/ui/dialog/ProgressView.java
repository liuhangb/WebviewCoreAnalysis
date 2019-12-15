package com.tencent.tbs.common.ui.dialog;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import com.tencent.tbs.common.resources.TBSResources;

public class ProgressView
  extends View
{
  public static final int MAX_PROGRESS = 100;
  public static String TAG_DOWNLOAD = "";
  public static String TAG_DOWNLOADING = "";
  public static String TAG_DOWNLOAD_FAILED = "";
  public static String TAG_INSTALL = "";
  int MAXLENGTH = 124;
  private int height;
  private Paint mPaintBackGround;
  private Paint mPaintCurrent;
  private Paint mPaintText;
  private int mProgress = 0;
  private RectF mRoundRect = null;
  String mText;
  private int marginDP = 0;
  private int progressHeightDP = 17;
  private int progressWidth;
  float sDensity = -1.0F;
  private int width;
  
  public ProgressView(Context paramContext)
  {
    super(paramContext);
  }
  
  public ProgressView(Context paramContext, int paramInt1, int paramInt2)
  {
    super(paramContext);
    initDensity(paramContext);
    this.mPaintBackGround = new Paint();
    this.mPaintBackGround.setColor(paramInt2);
    this.mPaintBackGround.setStyle(Paint.Style.STROKE);
    this.mPaintBackGround.setStrokeWidth(dip2px(1));
    this.mPaintBackGround.setAntiAlias(true);
    this.mPaintCurrent = new Paint();
    this.mPaintCurrent.setColor(paramInt1);
    this.mPaintCurrent.setAntiAlias(true);
    this.mPaintText = new Paint();
    this.mPaintText.setColor(paramInt2);
    this.mPaintText.setAntiAlias(true);
    this.mPaintText.setTextAlign(Paint.Align.CENTER);
    this.mPaintText.setTextSize(dip2px(14));
  }
  
  public ProgressView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    initDensity(paramContext);
    this.mPaintBackGround = new Paint();
    this.mPaintBackGround.setColor(Color.rgb(76, 154, 250));
    this.mPaintBackGround.setStyle(Paint.Style.STROKE);
    this.mPaintBackGround.setStrokeWidth(dip2px(1));
    this.mPaintBackGround.setAntiAlias(true);
    this.mPaintCurrent = new Paint();
    this.mPaintCurrent.setColor(Color.rgb(220, 236, 255));
    this.mPaintCurrent.setAntiAlias(true);
    this.mPaintText = new Paint();
    this.mPaintText.setColor(Color.rgb(76, 154, 250));
    this.mPaintText.setAntiAlias(true);
    this.mPaintText.setTextAlign(Paint.Align.CENTER);
    this.mPaintText.setTextSize(dip2px(14));
    TAG_DOWNLOAD = TBSResources.getString("progress_view__status_download");
    TAG_DOWNLOADING = TBSResources.getString("progress_view__status_downloading");
    TAG_DOWNLOAD_FAILED = TBSResources.getString("progress_view__status_failed");
    TAG_INSTALL = TBSResources.getString("progress_view__status_install");
    this.mText = TAG_DOWNLOAD;
  }
  
  private RectF getRoundRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    RectF localRectF = this.mRoundRect;
    if (localRectF == null)
    {
      this.mRoundRect = new RectF(paramInt1, paramInt2, paramInt3, paramInt4);
    }
    else
    {
      localRectF.left = paramInt1;
      localRectF.top = paramInt2;
      localRectF.right = paramInt3;
      localRectF.bottom = paramInt4;
    }
    return this.mRoundRect;
  }
  
  private void initDensity(Context paramContext)
  {
    if (this.sDensity < 0.0F)
    {
      paramContext = (WindowManager)paramContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
      this.sDensity = localDisplayMetrics.density;
    }
  }
  
  public int dip2px(int paramInt)
  {
    return (int)(paramInt * this.sDensity + 0.5F);
  }
  
  public int getProgress()
  {
    return this.mProgress;
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    int j = dip2px(this.marginDP);
    int i = dip2px(this.progressHeightDP);
    this.progressWidth = (this.width / 2 - j);
    j = this.progressWidth;
    int k = this.MAXLENGTH;
    if (j > k) {
      this.progressWidth = k;
    }
    j = this.progressWidth;
    if (j == this.MAXLENGTH) {
      this.progressWidth = dip2px(j);
    }
    int n = this.width;
    k = n / 2;
    j = this.progressWidth;
    k = k - j + 2;
    int i1 = this.height;
    int m = i1 / 2 - i + 2;
    n /= 2;
    i = i1 / 2 + i - 2;
    i1 = dip2px(2);
    Object localObject = getRoundRect(k, m, n + j - 2, i);
    float f = i1;
    paramCanvas.drawRoundRect((RectF)localObject, f, f, this.mPaintBackGround);
    double d1 = this.width;
    Double.isNaN(d1);
    d1 /= 2.0D;
    j = this.progressWidth;
    double d2 = j;
    Double.isNaN(d2);
    double d3 = j;
    Double.isNaN(d3);
    double d4 = this.mProgress;
    Double.isNaN(d4);
    f = (float)(d1 - d2 + d3 * 2.0D * d4 / 100.0D);
    paramCanvas.drawRect(k + 2, m + 2, f - 2.0F - 2.0F, i - 2 - 2, this.mPaintCurrent);
    localObject = this.mPaintText.getFontMetricsInt();
    i = (m + i - ((Paint.FontMetricsInt)localObject).bottom - ((Paint.FontMetricsInt)localObject).top) / 2;
    if (!TextUtils.isEmpty(this.mText)) {
      paramCanvas.drawText(this.mText, this.width / 2, i, this.mPaintText);
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    this.width = getDefaultSize(getSuggestedMinimumWidth(), paramInt1);
    this.height = getDefaultSize(getSuggestedMinimumHeight(), paramInt2);
    setMeasuredDimension(this.width, this.height);
  }
  
  public void resert()
  {
    this.mProgress = 0;
    postDelayed(new Runnable()
    {
      public void run()
      {
        ProgressView.this.mText = ProgressView.TAG_DOWNLOAD;
        ProgressView.this.setEnabled(true);
        ProgressView.this.postInvalidate();
      }
    }, 2000L);
  }
  
  public void setProgress(int paramInt)
  {
    int i = paramInt;
    if (paramInt < 0) {
      i = 0;
    }
    paramInt = i;
    if (i > 100) {
      paramInt = 100;
    }
    this.mProgress = paramInt;
    postInvalidate();
  }
  
  public void setText(String paramString)
  {
    this.mText = paramString;
    postInvalidate();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\ProgressView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */