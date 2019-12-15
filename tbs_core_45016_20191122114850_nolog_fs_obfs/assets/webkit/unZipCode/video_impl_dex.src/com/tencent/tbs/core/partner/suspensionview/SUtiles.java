package com.tencent.tbs.core.partner.suspensionview;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.smtt.webkit.service.SmttServiceProxy;

public class SUtiles
{
  public static final String KEY_ACTION_SUSVIEW_DIALOG_CANCEL_CLICK = "BWNFB7";
  public static final String KEY_ACTION_SUSVIEW_DIALOG_SHOW = "BWNFB6";
  public static final String KEY_ACTION_SUSVIEW_DIALOG_SURE_CLICK = "BWNFB8";
  public static final String KEY_ACTION_SUSVIEW_EXIT_CLICK = "BWNFB5";
  public static final String KEY_ACTION_SUSVIEW_EXTENDED_SHOW = "BWNFB3";
  public static final String KEY_ACTION_SUSVIEW_FEEDBACK_CLICK = "BWNFB4";
  public static final String KEY_ACTION_SUSVIEW_FEEDBACK_MENU_CLICK = "BWNFB10";
  public static final String KEY_ACTION_SUSVIEW_FEEDBACK_MENU_SHOW = "BWNFB9";
  public static final String KEY_ACTION_SUSVIEW_SHOW = "BWNFB1";
  public static final String KEY_ACTION_SUSVIEW_TBS_CLICK = "BWNFB2";
  public static final int a1 = Color.rgb(36, 36, 36);
  public static final int a2 = Color.rgb(102, 102, 102);
  public static final int b1 = Color.rgb(76, 154, 250);
  public static final int linecolor = Color.rgb(229, 229, 229);
  static float sDensity = -1.0F;
  public static final float t2 = 14.0F;
  public static final float t4 = 18.0F;
  
  public static boolean canShowAlertDialog(Context paramContext)
  {
    if (!(paramContext instanceof Activity))
    {
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append("");
      localStringBuilder.append(paramContext);
      localStringBuilder.append(" is not activity!");
      Log.e("SuSViewController", localStringBuilder.toString());
      return false;
    }
    return ((Activity)paramContext).isFinishing() ^ true;
  }
  
  public static Drawable createCornerRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    GradientDrawable localGradientDrawable = new GradientDrawable();
    localGradientDrawable.setCornerRadius(paramInt4);
    localGradientDrawable.setColor(paramInt3);
    localGradientDrawable.setStroke(paramInt1, paramInt2);
    return localGradientDrawable;
  }
  
  private static View createLayout(AlertDialog paramAlertDialog, Context paramContext, Window paramWindow, String paramString1, String paramString2, String paramString3, String paramString4, final IInfoConformListener paramIInfoConformListener)
  {
    paramWindow.setBackgroundDrawable(createCornerRect(3, Color.rgb(254, 254, 254), Color.rgb(254, 254, 254), dip2px(paramContext, 2.0F)));
    paramWindow.setLayout((int)(((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getWidth() * 0.86F), -2);
    paramWindow = new FrameLayout(paramContext);
    LinearLayout localLinearLayout = new LinearLayout(paramContext);
    localLinearLayout.setOrientation(1);
    localLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    localLinearLayout.setGravity(1);
    int i = dip2px(paramContext, 12.0F);
    int j = dip2px(paramContext, 38.0F);
    localLinearLayout.setPadding(0, j, 0, 0);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
    localLayoutParams.setMargins(i, 0, i, i);
    Object localObject = new LinearLayout.LayoutParams(-2, -2);
    int k = i * 2;
    ((LinearLayout.LayoutParams)localObject).setMargins(k, 0, k, j);
    TextView localTextView = new TextView(paramContext);
    localTextView.setText(paramString1);
    localTextView.setTextSize(18.0F);
    localTextView.setTextColor(a1);
    localTextView.setGravity(17);
    localLinearLayout.addView(localTextView, localLayoutParams);
    paramString1 = new TextView(paramContext);
    paramString1.setText(paramString2);
    paramString1.setTextSize(14.0F);
    paramString1.setTextColor(a2);
    paramString1.setLineSpacing(i / 2, 1.0F);
    paramString1.setGravity(17);
    localLinearLayout.addView(paramString1, (ViewGroup.LayoutParams)localObject);
    localLinearLayout.addView(getRoundRect(paramContext, -1, dip2px(paramContext, 1.0F), linecolor, 1, 0, 0, 0, 0));
    paramString1 = new LinearLayout(paramContext);
    paramString1.setOrientation(0);
    paramString2 = new LinearLayout.LayoutParams(-2, -2, 1.0F);
    i = dip2px(paramContext, 19.0F) / 2;
    paramString2.setMargins(0, i, 0, i);
    localObject = new Button(paramContext);
    ((Button)localObject).setText(paramString3);
    ((Button)localObject).setTextSize(18.0F);
    ((Button)localObject).setTextColor(a1);
    ((Button)localObject).setBackgroundDrawable(null);
    ((Button)localObject).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        this.val$alertDialog.dismiss();
        paramAnonymousView = paramIInfoConformListener;
        if (paramAnonymousView != null) {
          paramAnonymousView.onCancel();
        }
      }
    });
    paramString1.addView((View)localObject, paramString2);
    paramString1.addView(getRoundRect(paramContext, dip2px(paramContext, 1.0F), -1, linecolor, 1, 0, 0, 0, 0));
    paramString3 = new Button(paramContext);
    paramString3.setText(paramString4);
    paramString3.setTextSize(18.0F);
    paramString3.setTextColor(b1);
    paramString3.setBackgroundDrawable(null);
    paramString3.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        this.val$alertDialog.dismiss();
        paramAnonymousView = paramIInfoConformListener;
        if (paramAnonymousView != null) {
          paramAnonymousView.onSure();
        }
      }
    });
    paramString1.addView(paramString3, paramString2);
    localLinearLayout.addView(paramString1);
    paramAlertDialog = new View(paramContext);
    paramAlertDialog.setLayoutParams(new RelativeLayout.LayoutParams(-1, 0));
    localLinearLayout.addView(paramAlertDialog);
    paramWindow.addView(localLinearLayout);
    return paramWindow;
  }
  
  public static Drawable createSelector(Drawable paramDrawable1, Drawable paramDrawable2)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    localStateListDrawable.addState(new int[] { 16842919 }, paramDrawable1);
    localStateListDrawable.addState(new int[0], paramDrawable2);
    return localStateListDrawable;
  }
  
  public static int dip2px(Context paramContext, float paramFloat)
  {
    if (paramContext == null) {
      return (int)paramFloat;
    }
    if (sDensity == -1.0F) {
      initDensity(paramContext.getApplicationContext());
    }
    return (int)(paramFloat * sDensity + 0.5F);
  }
  
  public static View getLineView(Context paramContext, int paramInt)
  {
    paramContext = new View(paramContext);
    paramContext.setBackgroundDrawable(createCornerRect(3, paramInt, paramInt, 1));
    return paramContext;
  }
  
  public static View getRoundRect(Context paramContext, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8)
  {
    View localView = new View(paramContext);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(paramInt1, paramInt2);
    localLayoutParams.setMargins(dip2px(paramContext, paramInt5), dip2px(paramContext, paramInt6), dip2px(paramContext, paramInt7), dip2px(paramContext, paramInt8));
    localView.setBackgroundDrawable(createCornerRect(1, paramInt3, paramInt3, dip2px(paramContext, paramInt4)));
    localView.setLayoutParams(localLayoutParams);
    return localView;
  }
  
  private static void initDensity(Context paramContext)
  {
    if (sDensity < 0.0F)
    {
      paramContext = (WindowManager)paramContext.getSystemService("window");
      DisplayMetrics localDisplayMetrics = new DisplayMetrics();
      paramContext.getDefaultDisplay().getMetrics(localDisplayMetrics);
      sDensity = localDisplayMetrics.density;
    }
  }
  
  public static void postUserbehaveAction(String paramString)
  {
    SmttServiceProxy.getInstance().userBehaviorStatistics(paramString);
  }
  
  public static void showAlertInfo(Context paramContext, String paramString1, String paramString2, String paramString3, String paramString4, IInfoConformListener paramIInfoConformListener)
  {
    if ((!TextUtils.isEmpty(paramString1)) && (!TextUtils.isEmpty(paramString2)) && (!TextUtils.isEmpty(paramString3)))
    {
      if (TextUtils.isEmpty(paramString4)) {
        return;
      }
      if (!canShowAlertDialog(paramContext)) {
        return;
      }
      AlertDialog localAlertDialog = new AlertDialog.Builder(paramContext).create();
      localAlertDialog.show();
      Window localWindow = localAlertDialog.getWindow();
      localWindow.setContentView(createLayout(localAlertDialog, paramContext, localWindow, paramString1, paramString2, paramString3, paramString4, paramIInfoConformListener));
      if (paramIInfoConformListener != null) {
        paramIInfoConformListener.onShow();
      }
      return;
    }
  }
  
  public static abstract interface IInfoConformListener
  {
    public abstract void onCancel();
    
    public abstract void onShow();
    
    public abstract void onSure();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\suspensionview\SUtiles.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */