package com.tencent.tbs.common.ui.dialog;

import android.content.Context;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnKeyListener;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.tbs.common.resources.TBSResources;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBase;
import com.tencent.tbs.common.ui.dialog.interfaces.TBSDialogBase.DialogButton;
import java.util.List;
import java.util.Map;

public class TBSPlainTextDialog
  extends TBSDialogBase
{
  private LinearLayout mAllBtnContainer;
  private Button mBtnH1;
  private Button mBtnH2;
  private Button mBtnV1;
  private Button mBtnV2;
  private Button mBtnV3;
  private LinearLayout mHorizontalBtnContainer;
  private TextView mMessage;
  private LinearLayout mVerticalBtnContainer;
  private LinearLayout mVerticalSubBtnContainer;
  
  public TBSPlainTextDialog(Context paramContext)
  {
    super(paramContext);
  }
  
  private View createLayout(Context paramContext)
  {
    getWindow().setBackgroundDrawable(new ColorDrawable(0));
    Object localObject1 = getWindow().getAttributes();
    ((WindowManager.LayoutParams)localObject1).dimAmount = 0.5F;
    getWindow().setAttributes((WindowManager.LayoutParams)localObject1);
    float f = dip2px(2.0F);
    localObject1 = new FrameLayout(paramContext);
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    Object localObject2 = new FrameLayout.LayoutParams(dip2px(323.0F), -2);
    ((FrameLayout.LayoutParams)localObject2).gravity = 17;
    localLinearLayout.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    localLinearLayout.setBackgroundDrawable(TBSResources.getDrawable("x5_tbs_dialog_backgroud"));
    localLinearLayout.setOrientation(1);
    int i = dip2px(12.0F);
    localLinearLayout.setPadding(i, i, i, i);
    this.mMessage = new TextView(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, -2);
    ((LinearLayout.LayoutParams)localObject2).gravity = 17;
    this.mMessage.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    this.mMessage.setLineSpacing(dip2px(6.0F), 1.0F);
    this.mMessage.setMaxLines(10);
    this.mMessage.setPadding(dip2px(16.0F), dip2px(27.0F), dip2px(16.0F), dip2px(22.0F));
    this.mMessage.setTextColor(Color.rgb(30, 30, 30));
    this.mMessage.setTextSize(1, 16.0F);
    localLinearLayout.addView(this.mMessage);
    this.mAllBtnContainer = new LinearLayout(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, -2);
    this.mAllBtnContainer.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    this.mAllBtnContainer.setOrientation(1);
    localObject2 = new ImageView(paramContext);
    ((ImageView)localObject2).setLayoutParams(new LinearLayout.LayoutParams(-1, 1));
    ((ImageView)localObject2).setBackgroundColor(Color.rgb(219, 219, 219));
    this.mAllBtnContainer.addView((View)localObject2);
    this.mVerticalBtnContainer = new LinearLayout(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, -2);
    this.mVerticalBtnContainer.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    this.mVerticalBtnContainer.setOrientation(1);
    this.mVerticalSubBtnContainer = new LinearLayout(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, -2);
    this.mVerticalSubBtnContainer.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    this.mVerticalSubBtnContainer.setOrientation(1);
    this.mBtnV2 = new Button(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, dip2px(60.0F));
    this.mBtnV2.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    localObject2 = new StateListDrawable();
    Object localObject3 = new ColorDrawable(Color.rgb(242, 242, 242));
    ((StateListDrawable)localObject2).addState(new int[] { 16842919 }, (Drawable)localObject3);
    localObject3 = new ColorDrawable(0);
    ((StateListDrawable)localObject2).addState(new int[] { -16842919 }, (Drawable)localObject3);
    this.mBtnV2.setBackgroundDrawable((Drawable)localObject2);
    this.mBtnV2.setTextColor(Color.rgb(30, 30, 30));
    this.mBtnV2.setTextSize(1, 20.0F);
    this.mBtnV2.setContentDescription("mBtnV2");
    this.mVerticalSubBtnContainer.addView(this.mBtnV2);
    localObject2 = new ImageView(paramContext);
    ((ImageView)localObject2).setLayoutParams(new LinearLayout.LayoutParams(-1, 1));
    ((ImageView)localObject2).setBackgroundColor(Color.rgb(219, 219, 219));
    this.mVerticalSubBtnContainer.addView((View)localObject2);
    this.mBtnV3 = new Button(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, dip2px(60.0F));
    this.mBtnV3.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    localObject2 = new StateListDrawable();
    localObject3 = new ColorDrawable(Color.rgb(242, 242, 242));
    ((StateListDrawable)localObject2).addState(new int[] { 16842919 }, (Drawable)localObject3);
    localObject3 = new ColorDrawable(0);
    ((StateListDrawable)localObject2).addState(new int[] { -16842919 }, (Drawable)localObject3);
    this.mBtnV3.setBackgroundDrawable((Drawable)localObject2);
    this.mBtnV3.setTextColor(Color.rgb(30, 30, 30));
    this.mBtnV3.setTextSize(1, 20.0F);
    this.mBtnV3.setContentDescription("mBtnV3");
    this.mVerticalSubBtnContainer.addView(this.mBtnV3);
    localObject2 = new ImageView(paramContext);
    ((ImageView)localObject2).setLayoutParams(new LinearLayout.LayoutParams(-1, 1));
    ((ImageView)localObject2).setBackgroundColor(Color.rgb(219, 219, 219));
    this.mVerticalSubBtnContainer.addView((View)localObject2);
    this.mVerticalSubBtnContainer.setContentDescription("mVerticalSubBtnContainer");
    this.mVerticalBtnContainer.addView(this.mVerticalSubBtnContainer);
    this.mBtnV1 = new Button(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, dip2px(60.0F));
    this.mBtnV1.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    localObject2 = new StateListDrawable();
    Object localObject4 = new RoundRectShape(new float[] { 0.0F, 0.0F, 0.0F, 0.0F, f, f, f, f }, null, null);
    localObject3 = new ShapeDrawable((Shape)localObject4);
    ((ShapeDrawable)localObject3).getPaint().setColor(Color.rgb(242, 242, 242));
    localObject4 = new ShapeDrawable((Shape)localObject4);
    ((ShapeDrawable)localObject4).getPaint().setColor(0);
    ((StateListDrawable)localObject2).addState(new int[] { 16842919 }, (Drawable)localObject3);
    ((StateListDrawable)localObject2).addState(new int[] { -16842919 }, (Drawable)localObject4);
    this.mBtnV1.setBackgroundDrawable((Drawable)localObject2);
    this.mBtnV1.setTextColor(Color.rgb(30, 30, 30));
    this.mBtnV1.setTextSize(1, 20.0F);
    this.mBtnV1.setContentDescription("mBtnV1");
    this.mVerticalBtnContainer.addView(this.mBtnV1);
    this.mVerticalBtnContainer.setContentDescription("mVerticalBtnContainer");
    this.mAllBtnContainer.addView(this.mVerticalBtnContainer);
    this.mHorizontalBtnContainer = new LinearLayout(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, -2);
    this.mHorizontalBtnContainer.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    this.mHorizontalBtnContainer.setOrientation(0);
    this.mHorizontalBtnContainer.setWeightSum(2.0F);
    this.mBtnH1 = new Button(paramContext);
    localObject2 = new LinearLayout.LayoutParams(-1, dip2px(60.0F));
    ((LinearLayout.LayoutParams)localObject2).weight = 1.0F;
    this.mBtnH1.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    localObject2 = new StateListDrawable();
    localObject4 = new RoundRectShape(new float[] { 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, f, f }, null, null);
    localObject3 = new ShapeDrawable((Shape)localObject4);
    ((ShapeDrawable)localObject3).getPaint().setColor(Color.rgb(242, 242, 242));
    localObject4 = new ShapeDrawable((Shape)localObject4);
    ((ShapeDrawable)localObject4).getPaint().setColor(0);
    ((StateListDrawable)localObject2).addState(new int[] { 16842919 }, (Drawable)localObject3);
    ((StateListDrawable)localObject2).addState(new int[] { -16842919 }, (Drawable)localObject4);
    this.mBtnH1.setBackgroundDrawable((Drawable)localObject2);
    this.mBtnH1.setTextColor(Color.rgb(30, 30, 30));
    this.mBtnH1.setTextSize(1, 20.0F);
    this.mBtnH1.setContentDescription("mBtnH1");
    this.mHorizontalBtnContainer.addView(this.mBtnH1);
    localObject2 = new ImageView(paramContext);
    localObject3 = new LinearLayout.LayoutParams(1, -1);
    ((LinearLayout.LayoutParams)localObject3).weight = 0.0F;
    ((ImageView)localObject2).setLayoutParams((ViewGroup.LayoutParams)localObject3);
    ((ImageView)localObject2).setBackgroundColor(Color.rgb(219, 219, 219));
    this.mHorizontalBtnContainer.addView((View)localObject2);
    this.mBtnH2 = new Button(paramContext);
    paramContext = new LinearLayout.LayoutParams(-1, dip2px(60.0F));
    paramContext.weight = 1.0F;
    this.mBtnH2.setLayoutParams(paramContext);
    paramContext = new StateListDrawable();
    localObject3 = new RoundRectShape(new float[] { 0.0F, 0.0F, 0.0F, 0.0F, f, f, 0.0F, 0.0F }, null, null);
    localObject2 = new ShapeDrawable((Shape)localObject3);
    ((ShapeDrawable)localObject2).getPaint().setColor(Color.rgb(242, 242, 242));
    localObject3 = new ShapeDrawable((Shape)localObject3);
    ((ShapeDrawable)localObject3).getPaint().setColor(0);
    paramContext.addState(new int[] { 16842919 }, (Drawable)localObject2);
    paramContext.addState(new int[] { -16842919 }, (Drawable)localObject3);
    this.mBtnH2.setBackgroundDrawable(paramContext);
    this.mBtnH2.setTextColor(Color.rgb(30, 30, 30));
    this.mBtnH2.setTextSize(1, 20.0F);
    this.mBtnH2.setContentDescription("mBtnH2");
    this.mHorizontalBtnContainer.addView(this.mBtnH2);
    this.mHorizontalBtnContainer.setContentDescription("mHorizontalBtnContainer");
    this.mAllBtnContainer.addView(this.mHorizontalBtnContainer);
    localLinearLayout.addView(this.mAllBtnContainer);
    ((FrameLayout)localObject1).addView(localLinearLayout);
    return (View)localObject1;
  }
  
  public void cancel()
  {
    super.cancel();
  }
  
  public void dismiss()
  {
    super.dismiss();
  }
  
  public void initUI()
  {
    Object localObject3 = (String)this.mProperties.get(Integer.valueOf(1));
    Object localObject2 = (String)this.mProperties.get(Integer.valueOf(2));
    Object localObject1 = this.mProperties.get(Integer.valueOf(8));
    int i;
    if (localObject1 == null) {
      i = 0;
    } else {
      i = ((Integer)localObject1).intValue();
    }
    localObject1 = localObject2;
    if (localObject3 != null)
    {
      localObject1 = localObject2;
      if (!((String)localObject3).equals(""))
      {
        localObject1 = new StringBuilder();
        ((StringBuilder)localObject1).append((String)localObject3);
        ((StringBuilder)localObject1).append("\n");
        ((StringBuilder)localObject1).append((String)localObject2);
        localObject1 = ((StringBuilder)localObject1).toString();
      }
    }
    localObject2 = (DialogInterface.OnCancelListener)this.mProperties.get(Integer.valueOf(4));
    localObject3 = (DialogInterface.OnKeyListener)this.mProperties.get(Integer.valueOf(9));
    setContentView(createLayout(getContext()));
    if (localObject1 != null)
    {
      switch (i)
      {
      default: 
        this.mMessage.setGravity(17);
        break;
      case 2: 
        this.mMessage.setGravity(3);
        break;
      case 1: 
        this.mMessage.setGravity(5);
      }
      this.mMessage.setText((CharSequence)localObject1);
    }
    localObject1 = sortButtons();
    if (((List)localObject1).size() == 3)
    {
      this.mHorizontalBtnContainer.setVisibility(8);
      setButton(this.mBtnV2, (TBSDialogBase.DialogButton)((List)localObject1).get(0));
      setButton(this.mBtnV3, (TBSDialogBase.DialogButton)((List)localObject1).get(1));
      setButton(this.mBtnV1, (TBSDialogBase.DialogButton)((List)localObject1).get(2));
    }
    else if (((List)localObject1).size() == 2)
    {
      this.mVerticalBtnContainer.setVisibility(8);
      setButton(this.mBtnH2, (TBSDialogBase.DialogButton)((List)localObject1).get(0));
      setButton(this.mBtnH1, (TBSDialogBase.DialogButton)((List)localObject1).get(1));
    }
    else if (((List)localObject1).size() == 1)
    {
      this.mHorizontalBtnContainer.setVisibility(8);
      this.mVerticalSubBtnContainer.setVisibility(8);
      setButton(this.mBtnV1, (TBSDialogBase.DialogButton)((List)localObject1).get(0));
    }
    else
    {
      this.mAllBtnContainer.setVisibility(8);
    }
    setOnCancelListener((DialogInterface.OnCancelListener)localObject2);
    setOnKeyListener((DialogInterface.OnKeyListener)localObject3);
  }
  
  public void show()
  {
    super.show();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\TBSPlainTextDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */