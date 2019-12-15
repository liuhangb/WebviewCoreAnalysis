package com.tencent.smtt.webkit.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

public abstract class TbsConfirmDialog
  extends Dialog
{
  public static final int PROPERTY_ADAPTER = 6;
  public static final int PROPERTY_BUTTONS = 3;
  public static final int PROPERTY_CANCEL = 4;
  public static final int PROPERTY_DRAWABLE = 10;
  public static final int PROPERTY_INTENT = 5;
  public static final int PROPERTY_INTENT_SENDING_CALLBACK = 7;
  public static final int PROPERTY_MESSAGE = 2;
  public static final int PROPERTY_MESSAGE_ALIGN = 8;
  public static final int PROPERTY_ONKEY = 9;
  public static final int PROPERTY_TITLE = 1;
  private static float sDensity = -1.0F;
  private static float sScreenHeight = -1.0F;
  private static float sScreenWidth = -1.0F;
  public final String LOGTAG = "TbsInstallDialog";
  private boolean isVertical = true;
  public BtnClickListener listener = null;
  TriggerLinearout.OnSizeChangeCallback mSizeChangellback;
  private SharedPreferences preferfences = null;
  
  public TbsConfirmDialog(Context paramContext)
  {
    super(paramContext, 16973835);
    initDensity(paramContext.getApplicationContext());
  }
  
  private void initDensity(Context paramContext)
  {
    paramContext = (WindowManager)paramContext.getSystemService("window");
    boolean bool;
    if (paramContext.getDefaultDisplay().getOrientation() % 2 == 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.isVertical = bool;
    if (sDensity < 0.0F)
    {
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
      sDensity = localDisplayMetrics.density;
      int i;
      if (this.isVertical) {
        i = localDisplayMetrics.widthPixels;
      } else {
        i = localDisplayMetrics.heightPixels;
      }
      sScreenWidth = i;
      if (this.isVertical) {
        i = localDisplayMetrics.heightPixels;
      } else {
        i = localDisplayMetrics.widthPixels;
      }
      sScreenHeight = i;
    }
  }
  
  public View createDialogLayout(Context paramContext)
  {
    int i;
    if (this.isVertical) {
      i = 110;
    } else {
      i = 20;
    }
    int j = px2dip(sScreenWidth);
    getWindow().setBackgroundDrawable(new ColorDrawable(0));
    Object localObject = getWindow().getAttributes();
    ((WindowManager.LayoutParams)localObject).dimAmount = 0.5F;
    getWindow().setAttributes((WindowManager.LayoutParams)localObject);
    localObject = new FrameLayout(paramContext);
    this.mSizeChangellback = new TriggerLinearout.OnSizeChangeCallback()
    {
      public void onSizeChangeCallback()
      {
        TbsConfirmDialog.this.dismiss();
      }
    };
    paramContext = new TriggerLinearout(paramContext, this.mSizeChangellback);
    FrameLayout.LayoutParams localLayoutParams = new FrameLayout.LayoutParams(dip2px(j - 88), -1);
    float f = i;
    localLayoutParams.setMargins(0, dip2px(f), 0, dip2px(f));
    localLayoutParams.gravity = 17;
    paramContext.setLayoutParams(localLayoutParams);
    paramContext.setBackgroundColor(-1);
    paramContext.setOrientation(1);
    paramContext.addView(getContentView());
    ((FrameLayout)localObject).addView(paramContext);
    return (View)localObject;
  }
  
  public int dip2px(float paramFloat)
  {
    return (int)(paramFloat * sDensity + 0.5F);
  }
  
  public abstract LinearLayout getContentView();
  
  public void initUI()
  {
    setContentView(createDialogLayout(getContext()));
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    initUI();
  }
  
  public void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
  }
  
  public Bundle onSaveInstanceState()
  {
    return super.onSaveInstanceState();
  }
  
  public int px2dip(float paramFloat)
  {
    return (int)(paramFloat / sDensity + 0.5F);
  }
  
  public void setListener(BtnClickListener paramBtnClickListener)
  {
    this.listener = paramBtnClickListener;
  }
  
  public static abstract interface BtnClickListener
  {
    public abstract void onCancel();
    
    public abstract void onSure();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\TbsConfirmDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */