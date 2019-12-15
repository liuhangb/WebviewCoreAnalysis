package com.tencent.tbs.core.webkit.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Scroller;
import android.widget.TextView;
import com.tencent.smtt.webkit.SmttResource;

public class LongClickImageFirstView
  extends LinearLayout
{
  private static float sDensity = -1.0F;
  private final String TAG = "tbs_file_chooser";
  private Context mContext;
  private int mHeightBmp = 0;
  private myImageView mImage;
  private Scroller mScroller;
  private int mWidthBmp = 0;
  private X5WebViewAdapter mX5WebViewAdapter;
  
  public LongClickImageFirstView(Context paramContext, X5WebViewAdapter paramX5WebViewAdapter, String paramString)
  {
    super(paramContext);
    this.mX5WebViewAdapter = paramX5WebViewAdapter;
    this.mContext = paramContext;
    setOrientation(1);
    setBackgroundColor(-16777216);
    paramX5WebViewAdapter = new myTextView(paramContext);
    paramX5WebViewAdapter.setLayoutParams(new LinearLayout.LayoutParams(-1, dip2px(50), 17.0F));
    paramX5WebViewAdapter.setGravity(17);
    addView(paramX5WebViewAdapter);
    this.mImage = new myImageView(paramContext);
    this.mImage.setScaleType(ImageView.ScaleType.FIT_XY);
    paramX5WebViewAdapter = Base64.decode(paramString, 0);
    paramX5WebViewAdapter = BitmapFactory.decodeByteArray(paramX5WebViewAdapter, 0, paramX5WebViewAdapter.length);
    this.mImage.setImageBitmap(paramX5WebViewAdapter);
    this.mImage.setBackgroundColor(0);
    paramX5WebViewAdapter = (WindowManager)paramContext.getSystemService("window");
    paramString = new DisplayMetrics();
    paramX5WebViewAdapter.getDefaultDisplay().getMetrics(paramString);
    int k = paramString.widthPixels;
    int i = paramString.heightPixels;
    paramX5WebViewAdapter = this.mImage.getDrawable();
    int j = paramX5WebViewAdapter.getIntrinsicWidth();
    int m = paramX5WebViewAdapter.getIntrinsicHeight();
    float f1 = k;
    float f2 = i;
    float f3 = f1 / f2;
    float f4 = j;
    float f5 = m;
    float f6 = f4 / f5;
    if (f3 > f6)
    {
      j = (int)(f4 * (f5 / f2));
    }
    else
    {
      j = k;
      if (f3 < f6)
      {
        i = (int)(f5 / (f4 / f1));
        j = k;
      }
    }
    paramX5WebViewAdapter = new LinearLayout.LayoutParams(j, i);
    this.mImage.setLayoutParams(paramX5WebViewAdapter);
    this.mWidthBmp = j;
    this.mHeightBmp = i;
    addView(this.mImage);
    paramContext = new myTextView(paramContext);
    paramContext.setLayoutParams(new LinearLayout.LayoutParams(-1, dip2px(50), 17.0F));
    paramContext.setTextColor(-1);
    paramContext.setTextSize(1, 20.0F);
    paramContext.setText(SmttResource.getString("x5_image_query", "string"));
    paramContext.setGravity(17);
    addView(paramContext);
  }
  
  private int dip2px(int paramInt)
  {
    if (sDensity < 0.0F)
    {
      WindowManager localWindowManager = (WindowManager)this.mContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      localWindowManager.getDefaultDisplay().getMetrics(localDisplayMetrics);
      sDensity = localDisplayMetrics.density;
    }
    return (int)(paramInt * sDensity);
  }
  
  private Drawable getDrawable(String paramString)
  {
    Resources localResources = SmttResource.getResources();
    try
    {
      i = SmttResource.loadIdentifierResource(paramString, "drawable");
    }
    catch (RuntimeException paramString)
    {
      int i;
      Drawable localDrawable;
      for (;;) {}
    }
    i = 0;
    paramString = new BitmapFactory.Options();
    paramString.inScaled = true;
    localDrawable = getDrawableFromBitmap(BitmapFactory.decodeResource(localResources, i, paramString));
    paramString = localDrawable;
    if (localDrawable == null) {
      paramString = localResources.getDrawable(i);
    }
    return paramString;
  }
  
  private Drawable getDrawableFromBitmap(Bitmap paramBitmap)
  {
    Resources localResources = SmttResource.getResources();
    int i = localResources.getDisplayMetrics().densityDpi;
    if (paramBitmap != null)
    {
      paramBitmap.setDensity(i);
      return new BitmapDrawable(localResources, paramBitmap);
    }
    return null;
  }
  
  public void onBack() {}
  
  public void updateImage(Bitmap paramBitmap)
  {
    this.mImage.setImageBitmap(paramBitmap);
  }
  
  class myImageView
    extends ImageView
  {
    private final int DIRECTION_EXPANSION = 1;
    private final int DIRECTION_SHRINK = 2;
    private int mDirection = 1;
    private int mOffset_LeftTop = 200;
    
    public myImageView(Context paramContext)
    {
      super();
    }
    
    private Drawable getDrawable(String paramString)
    {
      Resources localResources = SmttResource.getResources();
      try
      {
        i = SmttResource.loadIdentifierResource(paramString, "drawable");
      }
      catch (RuntimeException paramString)
      {
        int i;
        Drawable localDrawable;
        for (;;) {}
      }
      i = 0;
      paramString = new BitmapFactory.Options();
      paramString.inScaled = true;
      paramString = BitmapFactory.decodeResource(localResources, i, paramString);
      localDrawable = LongClickImageFirstView.this.getDrawableFromBitmap(paramString);
      paramString = localDrawable;
      if (localDrawable == null) {
        paramString = localResources.getDrawable(i);
      }
      return paramString;
    }
    
    protected void onDraw(Canvas paramCanvas)
    {
      super.onDraw(paramCanvas);
      int k = 0;
      for (;;)
      {
        try
        {
          i = SmttResource.loadIdentifierResource("x5_unpack_left_top", "drawable");
        }
        catch (RuntimeException localRuntimeException1)
        {
          int i;
          int j;
          int m;
          int n;
          Object localObject;
          Resources localResources;
          Bitmap localBitmap1;
          Bitmap localBitmap2;
          Bitmap localBitmap3;
          int i1;
          int i2;
          int i3;
          float f1;
          float f3;
          float f2;
          continue;
        }
        try
        {
          j = SmttResource.loadIdentifierResource("x5_unpack_right_top", "drawable");
        }
        catch (RuntimeException localRuntimeException2)
        {
          continue;
        }
        try
        {
          m = SmttResource.loadIdentifierResource("x5_unpack_left_bottom", "drawable");
        }
        catch (RuntimeException localRuntimeException3)
        {
          continue;
        }
        try
        {
          n = SmttResource.loadIdentifierResource("x5_unpack_right_bottom", "drawable");
          k = n;
        }
        catch (RuntimeException localRuntimeException4) {}
      }
      i = 0;
      j = 0;
      m = 0;
      localObject = new BitmapFactory.Options();
      ((BitmapFactory.Options)localObject).inScaled = true;
      localResources = SmttResource.getResources();
      localBitmap1 = BitmapFactory.decodeResource(localResources, i, (BitmapFactory.Options)localObject);
      localBitmap2 = BitmapFactory.decodeResource(localResources, j, (BitmapFactory.Options)localObject);
      localBitmap3 = BitmapFactory.decodeResource(localResources, m, (BitmapFactory.Options)localObject);
      localObject = BitmapFactory.decodeResource(localResources, k, (BitmapFactory.Options)localObject);
      i = LongClickImageFirstView.this.mWidthBmp / 2;
      j = this.mOffset_LeftTop;
      k = LongClickImageFirstView.this.mHeightBmp / 2;
      m = this.mOffset_LeftTop;
      n = LongClickImageFirstView.this.mWidthBmp / 2;
      i1 = this.mOffset_LeftTop;
      i2 = LongClickImageFirstView.this.mHeightBmp / 2;
      i3 = this.mOffset_LeftTop;
      f1 = i - j;
      f3 = k - m;
      paramCanvas.drawBitmap(localBitmap1, f1, f3, null);
      f2 = n + i1;
      paramCanvas.drawBitmap(localBitmap2, f2, f3, null);
      f3 = i2 + i3;
      paramCanvas.drawBitmap(localBitmap3, f1, f3, null);
      paramCanvas.drawBitmap((Bitmap)localObject, f2, f3, null);
      i = this.mDirection;
      if (i == 1) {
        this.mOffset_LeftTop += 5;
      } else if (i == 2) {
        this.mOffset_LeftTop -= 5;
      }
      i = this.mOffset_LeftTop;
      if (i > 300) {
        this.mDirection = 2;
      } else if (i < 200) {
        this.mDirection = 1;
      }
      invalidate();
    }
  }
  
  class myTextView
    extends TextView
  {
    public myTextView(Context paramContext)
    {
      super();
    }
    
    protected void onDraw(Canvas paramCanvas)
    {
      super.onDraw(paramCanvas);
      Paint localPaint = new Paint();
      localPaint.setColor(Color.rgb(229, 229, 229));
      paramCanvas.drawLine(LongClickImageFirstView.this.dip2px(20), 0.0F, getWidth() - 1, 0.0F, localPaint);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\adapter\LongClickImageFirstView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */