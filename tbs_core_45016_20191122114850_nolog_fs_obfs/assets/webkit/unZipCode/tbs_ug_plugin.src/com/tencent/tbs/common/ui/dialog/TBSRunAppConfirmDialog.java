package com.tencent.tbs.common.ui.dialog;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

public class TBSRunAppConfirmDialog
  extends Dialog
{
  private static final int DIVIDER_COLOR = -1710619;
  private static final float FIRST_LINE_RATIO = 0.6F;
  private static final int FIXED_HEIGHT_PX = 386;
  private static final int FIXED_WIDTH_PX = 864;
  private static final int POSITIVE_TEXT_COLOR = -10982507;
  private static final String TAG = "TBSRunAppConfirmDialog";
  private static final int TEXT_COLOR = -14408668;
  private static float sDensity = -1.0F;
  private static float sScreenHeight = -1.0F;
  private static float sScreenWidth = -1.0F;
  private int containerHeight = 0;
  private int containerWidth = 0;
  private boolean isVertical = true;
  private String message;
  private TextView messageTv;
  private TextView negativeBtn;
  private TextView positiveBtn;
  private RunAppConfirmBtnClickListener runAppConfirmBtnClickListener = null;
  
  public TBSRunAppConfirmDialog(Context paramContext, String paramString)
  {
    super(paramContext);
    this.message = paramString;
    setCanceledOnTouchOutside(false);
    initDensity(paramContext.getApplicationContext());
  }
  
  private View createDialogLayout(Context paramContext)
  {
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    float f = sScreenWidth;
    int i;
    if (f > 0.0F)
    {
      double d = f;
      Double.isNaN(d);
      i = (int)(d * 0.8D);
    }
    else
    {
      i = 864;
    }
    this.containerWidth = i;
    this.containerHeight = (this.containerWidth * 386 / 864);
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).append("createDialogLayout: containerWidth=");
    ((StringBuilder)localObject).append(this.containerWidth);
    ((StringBuilder)localObject).append(" containerHeight=");
    ((StringBuilder)localObject).append(this.containerHeight);
    ((StringBuilder)localObject).toString();
    localObject = new FrameLayout.LayoutParams(this.containerWidth, this.containerHeight);
    ((FrameLayout.LayoutParams)localObject).gravity = 17;
    localLinearLayout.setLayoutParams((ViewGroup.LayoutParams)localObject);
    localObject = new GradientDrawable();
    ((GradientDrawable)localObject).setColor(-1);
    ((GradientDrawable)localObject).setShape(0);
    ((GradientDrawable)localObject).setCornerRadius(dip2px(9.0F));
    localLinearLayout.setBackgroundDrawable((Drawable)localObject);
    localLinearLayout.setOrientation(1);
    localLinearLayout.addView(initContentView(paramContext));
    paramContext = new FrameLayout(paramContext);
    paramContext.addView(localLinearLayout);
    return paramContext;
  }
  
  private int dip2px(float paramFloat)
  {
    return (int)(paramFloat * sDensity + 0.5F);
  }
  
  @TargetApi(11)
  private View initContentView(Context paramContext)
  {
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    localLinearLayout.setOrientation(1);
    localLinearLayout.setShowDividers(2);
    Object localObject = new GradientDrawable();
    ((GradientDrawable)localObject).setSize(-1, 1);
    ((GradientDrawable)localObject).setColor(-1710619);
    localLinearLayout.setDividerDrawable((Drawable)localObject);
    localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -1);
    localLayoutParams.gravity = 17;
    localLayoutParams.height = ((int)(this.containerHeight * 0.6F));
    localLinearLayout.addView(this.messageTv, localLayoutParams);
    paramContext = new LinearLayout(paramContext);
    paramContext.setOrientation(0);
    paramContext.setShowDividers(2);
    paramContext.setGravity(17);
    ((GradientDrawable)localObject).setSize(1, -1);
    paramContext.setDividerDrawable((Drawable)localObject);
    localObject = new LinearLayout.LayoutParams(-2, -1);
    ((LinearLayout.LayoutParams)localObject).weight = 1.0F;
    paramContext.addView(this.negativeBtn, (ViewGroup.LayoutParams)localObject);
    localObject = new LinearLayout.LayoutParams(-2, -1);
    ((LinearLayout.LayoutParams)localObject).weight = 1.0F;
    paramContext.addView(this.positiveBtn, (ViewGroup.LayoutParams)localObject);
    localObject = new LinearLayout.LayoutParams(-1, -1);
    ((LinearLayout.LayoutParams)localObject).gravity = 17;
    ((LinearLayout.LayoutParams)localObject).height = ((int)(this.containerHeight * 0.39999998F));
    localLinearLayout.addView(paramContext, (ViewGroup.LayoutParams)localObject);
    return localLinearLayout;
  }
  
  private void initDensity(Context paramContext)
  {
    Object localObject = (WindowManager)paramContext.getSystemService("window");
    paramContext = new StringBuilder();
    paramContext.append("initDensity: window manager=");
    paramContext.append(localObject);
    paramContext.toString();
    if (localObject != null)
    {
      boolean bool;
      if (((WindowManager)localObject).getDefaultDisplay().getOrientation() % 2 == 0) {
        bool = true;
      } else {
        bool = false;
      }
      this.isVertical = bool;
      if (sDensity < 0.0F)
      {
        paramContext = new DisplayMetrics();
        ((WindowManager)localObject).getDefaultDisplay().getMetrics(paramContext);
        sDensity = paramContext.density;
        int i;
        if (this.isVertical) {
          i = paramContext.widthPixels;
        } else {
          i = paramContext.heightPixels;
        }
        sScreenWidth = i;
        if (this.isVertical) {
          i = paramContext.heightPixels;
        } else {
          i = paramContext.widthPixels;
        }
        sScreenHeight = i;
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("initDensity: density=");
        ((StringBuilder)localObject).append(sDensity);
        ((StringBuilder)localObject).append(" screenWidth=");
        ((StringBuilder)localObject).append(sScreenWidth);
        ((StringBuilder)localObject).append(" screenHeight=");
        ((StringBuilder)localObject).append(sScreenHeight);
        ((StringBuilder)localObject).toString();
        localObject = new StringBuilder();
        ((StringBuilder)localObject).append("initDensity: width=");
        ((StringBuilder)localObject).append(paramContext.widthPixels);
        ((StringBuilder)localObject).append(" height=");
        ((StringBuilder)localObject).append(paramContext.heightPixels);
        ((StringBuilder)localObject).toString();
      }
    }
  }
  
  private void initUI()
  {
    Object localObject = getWindow();
    if (localObject == null) {
      return;
    }
    ((Window)localObject).setBackgroundDrawable(new ColorDrawable(0));
    localObject = getWindow().getAttributes();
    ((WindowManager.LayoutParams)localObject).dimAmount = 0.5F;
    getWindow().setAttributes((WindowManager.LayoutParams)localObject);
    localObject = getContext();
    this.messageTv = new TextView((Context)localObject);
    this.messageTv.setText(this.message);
    this.messageTv.setTextColor(-14408668);
    this.messageTv.setTextSize(px2dip(52.0F));
    this.messageTv.setGravity(17);
    this.positiveBtn = new TextView((Context)localObject);
    this.positiveBtn.setText("允许");
    this.positiveBtn.setTextColor(-10982507);
    this.positiveBtn.setTypeface(Typeface.defaultFromStyle(1));
    this.positiveBtn.setTextSize(px2dip(52.0F));
    this.positiveBtn.setGravity(17);
    this.negativeBtn = new TextView((Context)localObject);
    this.negativeBtn.setText("取消");
    this.negativeBtn.setTextColor(-14408668);
    this.negativeBtn.setTypeface(Typeface.defaultFromStyle(1));
    this.negativeBtn.setTextSize(px2dip(52.0F));
    this.negativeBtn.setGravity(17);
    this.positiveBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (TBSRunAppConfirmDialog.this.runAppConfirmBtnClickListener != null) {
          TBSRunAppConfirmDialog.this.runAppConfirmBtnClickListener.onSure();
        }
      }
    });
    this.negativeBtn.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (TBSRunAppConfirmDialog.this.runAppConfirmBtnClickListener != null) {
          TBSRunAppConfirmDialog.this.runAppConfirmBtnClickListener.onCancel();
        }
      }
    });
    setContentView(createDialogLayout((Context)localObject));
  }
  
  private int px2dip(float paramFloat)
  {
    return (int)(paramFloat / sDensity + 0.5F);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    initUI();
  }
  
  public void setRunAppConfirmBtnClickListener(RunAppConfirmBtnClickListener paramRunAppConfirmBtnClickListener)
  {
    this.runAppConfirmBtnClickListener = paramRunAppConfirmBtnClickListener;
  }
  
  public static abstract interface RunAppConfirmBtnClickListener
  {
    public abstract void onCancel();
    
    public abstract void onSure();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\TBSRunAppConfirmDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */