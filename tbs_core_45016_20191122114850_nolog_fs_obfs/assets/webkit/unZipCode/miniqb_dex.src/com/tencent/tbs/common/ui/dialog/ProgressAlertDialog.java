package com.tencent.tbs.common.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
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
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.common.http.Apn;
import com.tencent.tbs.common.resources.TBSResources;
import java.io.Serializable;

public class ProgressAlertDialog
{
  static final int ID_PROMPT = 5;
  static final int ID_PV = 3;
  static final int ID_PV_EXT = 4;
  static final int ID_RLTOP = 1;
  static final int ID_TEXT = 2;
  public static final String LOGTAG = "ProgressAlertDialog";
  public static final int STATUS_FAILED = 3;
  public static final int STATUS_PAUSE = 5;
  public static final int STATUS_PROGRESS = 2;
  public static final int STATUS_START = 4;
  public static final int STATUS_SUCCESS = 1;
  public static final int STATUS_UNKNOWN = 0;
  public static final int UI_TYPE_BANNER = 1;
  public static final int UI_TYPE_CUSTOM = 4;
  public static final int UI_TYPE_DEF = 0;
  public static final int UI_TYPE_EXT_BTN = 2;
  AlertDialog alertDialog;
  Button btnClose;
  private boolean isDownloadFinished;
  ImageView ivTagView;
  private boolean mAutoDownload;
  Bitmap mBitmap;
  private Context mContext;
  private String mCustomDownloadFailButtonText;
  private String mCustomDownloadSuccButtonText;
  private String mCustomDownloadingButtonText;
  private String mCustomInitButtonText;
  private View.OnClickListener mExtButtonClickListener;
  private String mExtButtonText;
  private boolean mIsButtonTextCustomized;
  IProgressViewListener mListener;
  TextView mMessage;
  private boolean mNeedShowNonWifiPrompt;
  String mTip;
  private int mType;
  ProgressView pv;
  float sDensity;
  
  public ProgressAlertDialog(Context paramContext, String paramString, int paramInt, Bitmap paramBitmap, boolean paramBoolean, IProgressViewListener paramIProgressViewListener)
  {
    boolean bool = false;
    this.mType = 0;
    this.sDensity = -1.0F;
    this.mIsButtonTextCustomized = false;
    this.mCustomInitButtonText = null;
    this.mCustomDownloadFailButtonText = null;
    this.mCustomDownloadSuccButtonText = null;
    this.mCustomDownloadingButtonText = null;
    this.mExtButtonText = null;
    this.mExtButtonClickListener = null;
    this.mContext = paramContext;
    this.mBitmap = paramBitmap;
    this.isDownloadFinished = false;
    if ((paramString != null) && (!"".equals(paramString.trim()))) {
      this.mTip = paramString.trim();
    } else {
      this.mTip = TBSResources.getString("progress_dialog_default_text");
    }
    this.mType = paramInt;
    this.mListener = paramIProgressViewListener;
    this.mAutoDownload = paramBoolean;
    paramBoolean = bool;
    if (Apn.isNetworkAvailable())
    {
      paramBoolean = bool;
      if (Apn.sApnTypeS != 4) {
        paramBoolean = true;
      }
    }
    this.mNeedShowNonWifiPrompt = paramBoolean;
  }
  
  public static boolean canShowAlertDialog(Context paramContext)
  {
    if (!(paramContext instanceof Activity))
    {
      localStringBuilder = new StringBuilder();
      localStringBuilder.append("");
      localStringBuilder.append(paramContext);
      localStringBuilder.append(" is not activity!");
      Log.e("ProgressAlertDialog", localStringBuilder.toString());
      return false;
    }
    boolean bool = ((Activity)paramContext).isFinishing() ^ true;
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append("");
    localStringBuilder.append(paramContext);
    localStringBuilder.append(" isNotFinishing: ");
    localStringBuilder.append(bool);
    localStringBuilder.toString();
    return bool;
  }
  
  public static Drawable createCornerRect(int paramInt1, int paramInt2)
  {
    GradientDrawable localGradientDrawable = new GradientDrawable();
    localGradientDrawable.setCornerRadius(paramInt2);
    localGradientDrawable.setColor(paramInt1);
    return localGradientDrawable;
  }
  
  private View createCustomLayout(Context paramContext, Window paramWindow, int paramInt)
  {
    if ((paramInt & 0x4) == 0) {
      return new View(paramContext);
    }
    paramWindow.setBackgroundDrawable(createCornerRect(-1, dip2px(2.0F)));
    Object localObject1 = (WindowManager)paramContext.getSystemService("window");
    if (((WindowManager)localObject1).getDefaultDisplay().getWidth() < ((WindowManager)localObject1).getDefaultDisplay().getHeight()) {
      i = (int)(((WindowManager)localObject1).getDefaultDisplay().getWidth() * 0.86F);
    } else {
      i = (int)(((WindowManager)localObject1).getDefaultDisplay().getHeight() * 0.86F);
    }
    paramWindow.setLayout(i, -2);
    paramWindow = new FrameLayout(paramContext);
    paramWindow.setLayoutParams(new WindowManager.LayoutParams(i, -2));
    localObject1 = new RelativeLayout(paramContext);
    Object localObject2 = new RelativeLayout(paramContext);
    RelativeLayout.LayoutParams localLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
    ((RelativeLayout)localObject1).setLayoutParams(localLayoutParams);
    ((RelativeLayout)localObject2).setLayoutParams(localLayoutParams);
    if (((paramInt & 0x1) == 1) && (this.mBitmap != null))
    {
      this.ivTagView = new ImageView(paramContext);
      localLayoutParams = new RelativeLayout.LayoutParams(-1, i * this.mBitmap.getHeight() / this.mBitmap.getWidth());
      this.ivTagView.setLayoutParams(localLayoutParams);
      this.ivTagView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
      ((RelativeLayout)localObject2).addView(this.ivTagView);
    }
    this.btnClose = new Button(paramContext);
    paramInt = dip2px(16.0F);
    localLayoutParams = new RelativeLayout.LayoutParams(paramInt, paramInt);
    localLayoutParams.addRule(11);
    localLayoutParams.setMargins(0, dip2px(8.0F), dip2px(8.0F), 0);
    this.btnClose.setLayoutParams(localLayoutParams);
    ((RelativeLayout)localObject2).addView(this.btnClose);
    ((RelativeLayout)localObject2).setId(1);
    ((RelativeLayout)localObject1).addView((View)localObject2);
    int i = dip2px(34.0F);
    int j = dip2px(16.0F);
    int k = dip2px(6.0F);
    if (this.mNeedShowNonWifiPrompt) {
      paramInt = 0;
    } else {
      paramInt = dip2px(24.0F);
    }
    this.pv = new ProgressView(paramContext, null);
    localObject2 = new RelativeLayout.LayoutParams(-1, i);
    ((RelativeLayout.LayoutParams)localObject2).addRule(3, 1);
    ((RelativeLayout.LayoutParams)localObject2).setMargins(j, k, j, paramInt);
    this.pv.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    this.pv.setId(3);
    ((RelativeLayout)localObject1).addView(this.pv);
    if ((this.mIsButtonTextCustomized) && (!TextUtils.isEmpty(this.mCustomInitButtonText))) {
      this.pv.setText(this.mCustomInitButtonText);
    }
    boolean bool = this.mNeedShowNonWifiPrompt;
    paramInt = 5;
    if (bool)
    {
      localObject2 = new TextView(paramContext);
      ((TextView)localObject2).setTextColor(Color.rgb(143, 143, 143));
      ((TextView)localObject2).setTextSize(11.0F);
      ((TextView)localObject2).setText(TBSResources.getString("x5_non_wifi_prompt"));
      ((TextView)localObject2).setGravity(17);
      localLayoutParams = new RelativeLayout.LayoutParams(-1, -2);
      localLayoutParams.addRule(3, 3);
      localLayoutParams.setMargins(0, dip2px(6.0F), 0, 0);
      ((TextView)localObject2).setLayoutParams(localLayoutParams);
      ((TextView)localObject2).setId(5);
      ((RelativeLayout)localObject1).addView((View)localObject2);
    }
    paramContext = new View(paramContext);
    localObject2 = new RelativeLayout.LayoutParams(-1, 0);
    if (!this.mNeedShowNonWifiPrompt) {
      paramInt = 3;
    }
    ((RelativeLayout.LayoutParams)localObject2).addRule(3, paramInt);
    if (this.mNeedShowNonWifiPrompt) {
      ((RelativeLayout.LayoutParams)localObject2).setMargins(0, dip2px(18.0F), 0, 0);
    }
    paramContext.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    ((RelativeLayout)localObject1).addView(paramContext);
    paramWindow.addView((View)localObject1);
    return paramWindow;
  }
  
  private View createLayout(Context paramContext, Window paramWindow, int paramInt)
  {
    if ((paramInt & 0x4) == 4) {
      return createCustomLayout(paramContext, paramWindow, paramInt);
    }
    paramWindow.setBackgroundDrawable(createCornerRect(-1, dip2px(2.0F)));
    paramWindow.setLayout((int)(((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getWidth() * 0.86F), -2);
    paramWindow = new FrameLayout(paramContext);
    RelativeLayout localRelativeLayout = new RelativeLayout(paramContext);
    Object localObject1 = new RelativeLayout(paramContext);
    Object localObject2 = new RelativeLayout.LayoutParams(-1, -2);
    localRelativeLayout.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    ((RelativeLayout)localObject1).setLayoutParams((ViewGroup.LayoutParams)localObject2);
    if (((paramInt & 0x1) == 1) && (this.mBitmap != null))
    {
      this.ivTagView = new ImageView(paramContext);
      localObject2 = new RelativeLayout.LayoutParams(-1, dip2px(154.0F));
      this.ivTagView.setLayoutParams((ViewGroup.LayoutParams)localObject2);
      this.ivTagView.setScaleType(ImageView.ScaleType.FIT_XY);
      ((RelativeLayout)localObject1).addView(this.ivTagView);
    }
    this.btnClose = new Button(paramContext);
    int i = dip2px(16.0F);
    localObject2 = new RelativeLayout.LayoutParams(i, i);
    ((RelativeLayout.LayoutParams)localObject2).addRule(11);
    ((RelativeLayout.LayoutParams)localObject2).setMargins(0, dip2px(8.0F), dip2px(8.0F), 0);
    this.btnClose.setLayoutParams((ViewGroup.LayoutParams)localObject2);
    ((RelativeLayout)localObject1).addView(this.btnClose);
    ((RelativeLayout)localObject1).setId(1);
    localRelativeLayout.addView((View)localObject1);
    localObject1 = new LinearLayout(paramContext);
    localObject2 = new RelativeLayout.LayoutParams(-1, -2);
    ((RelativeLayout.LayoutParams)localObject2).addRule(15);
    ((RelativeLayout.LayoutParams)localObject2).addRule(3, 1);
    if (paramInt == 1) {
      ((RelativeLayout.LayoutParams)localObject2).setMargins(0, dip2px(30.0F), 0, dip2px(30.0F));
    } else {
      ((RelativeLayout.LayoutParams)localObject2).setMargins(0, dip2px(36.0F), 0, dip2px(48.0F));
    }
    ((LinearLayout)localObject1).setLayoutParams((ViewGroup.LayoutParams)localObject2);
    ((LinearLayout)localObject1).setId(2);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2, 0.74F);
    localObject2 = new TextView(paramContext);
    TextView localTextView = new TextView(paramContext);
    ((TextView)localObject2).setLayoutParams(localLayoutParams);
    localTextView.setLayoutParams(localLayoutParams);
    localLayoutParams = new LinearLayout.LayoutParams(-1, -2, 0.13F);
    this.mMessage = new TextView(paramContext);
    this.mMessage.setLayoutParams(localLayoutParams);
    this.mMessage.setMaxLines(10);
    this.mMessage.setTextColor(-16777216);
    this.mMessage.setTextSize(18.0F);
    this.mMessage.setGravity(17);
    ((LinearLayout)localObject1).addView((View)localObject2);
    ((LinearLayout)localObject1).addView(this.mMessage);
    ((LinearLayout)localObject1).addView(localTextView);
    localRelativeLayout.addView((View)localObject1);
    if ((paramInt & 0x2) == 2) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    int j = dip2px(34.0F);
    int k = dip2px(16.0F);
    if (paramInt != 0) {
      i = dip2px(16.0F);
    } else {
      i = dip2px(24.0F);
    }
    this.pv = new ProgressView(paramContext, null);
    localObject1 = new RelativeLayout.LayoutParams(-1, j);
    ((RelativeLayout.LayoutParams)localObject1).addRule(3, 2);
    ((RelativeLayout.LayoutParams)localObject1).setMargins(k, 0, k, i);
    this.pv.setLayoutParams((ViewGroup.LayoutParams)localObject1);
    this.pv.setId(3);
    localRelativeLayout.addView(this.pv);
    if ((this.mIsButtonTextCustomized) && (!TextUtils.isEmpty(this.mCustomInitButtonText))) {
      this.pv.setText(this.mCustomInitButtonText);
    }
    i = paramInt;
    if (paramInt != 0)
    {
      localObject1 = new ProgressView(paramContext, 0, Color.rgb(76, 154, 250));
      ((ProgressView)localObject1).setId(4);
      localObject2 = new RelativeLayout.LayoutParams(-1, j);
      ((RelativeLayout.LayoutParams)localObject2).addRule(3, 3);
      ((RelativeLayout.LayoutParams)localObject2).setMargins(k, 0, k, dip2px(32.0F));
      ((ProgressView)localObject1).setLayoutParams((ViewGroup.LayoutParams)localObject2);
      ((ProgressView)localObject1).setText(this.mExtButtonText);
      ((ProgressView)localObject1).setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if (ProgressAlertDialog.this.mExtButtonClickListener != null) {
            ProgressAlertDialog.this.mExtButtonClickListener.onClick(paramAnonymousView);
          }
          ProgressAlertDialog.this.dismiss();
        }
      });
      localRelativeLayout.addView((View)localObject1);
      i = 1;
    }
    paramContext = new View(paramContext);
    localObject1 = new RelativeLayout.LayoutParams(-1, 0);
    if (i != 0) {
      ((RelativeLayout.LayoutParams)localObject1).addRule(3, 4);
    } else {
      ((RelativeLayout.LayoutParams)localObject1).addRule(3, 3);
    }
    paramContext.setLayoutParams((ViewGroup.LayoutParams)localObject1);
    localRelativeLayout.addView(paramContext);
    paramWindow.addView(localRelativeLayout);
    return paramWindow;
  }
  
  public static Drawable createSelector(Drawable paramDrawable1, Drawable paramDrawable2)
  {
    StateListDrawable localStateListDrawable = new StateListDrawable();
    localStateListDrawable.addState(new int[] { 16842919 }, paramDrawable1);
    localStateListDrawable.addState(new int[0], paramDrawable2);
    return localStateListDrawable;
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
  
  public int dip2px(float paramFloat)
  {
    return (int)(paramFloat * this.sDensity + 0.5F);
  }
  
  public void dismiss()
  {
    AlertDialog localAlertDialog = this.alertDialog;
    if (localAlertDialog != null) {
      localAlertDialog.dismiss();
    }
  }
  
  public void onDownloadFinish()
  {
    this.isDownloadFinished = true;
    AlertDialog localAlertDialog = this.alertDialog;
    if (localAlertDialog != null) {
      localAlertDialog.dismiss();
    }
  }
  
  public void setCustomDownloadFailButtonText(String paramString)
  {
    this.mCustomDownloadFailButtonText = paramString;
  }
  
  public void setCustomDownloadSuccButtonText(String paramString)
  {
    this.mCustomDownloadSuccButtonText = paramString;
  }
  
  public void setCustomDownloadingButtonText(String paramString)
  {
    this.mCustomDownloadingButtonText = paramString;
  }
  
  public void setCustomInitButtonText(String paramString)
  {
    this.mCustomInitButtonText = paramString;
  }
  
  public void setExtButtonClickListener(View.OnClickListener paramOnClickListener)
  {
    this.mExtButtonClickListener = paramOnClickListener;
  }
  
  public void setExtButtonClickListener(ExtButtonClickListenerSerial paramExtButtonClickListenerSerial)
  {
    if (paramExtButtonClickListenerSerial != null)
    {
      this.mExtButtonClickListener = paramExtButtonClickListenerSerial.getExtButtonClickListener();
      return;
    }
    this.mExtButtonClickListener = null;
  }
  
  public void setExtButtonText(String paramString)
  {
    this.mExtButtonText = paramString;
  }
  
  public void setIsButtonTextCustomized(boolean paramBoolean)
  {
    this.mIsButtonTextCustomized = paramBoolean;
  }
  
  public void setOnProgressViewListener(IProgressViewListener paramIProgressViewListener)
  {
    this.mListener = paramIProgressViewListener;
  }
  
  public void setStatus(int paramInt1, int paramInt2)
  {
    try
    {
      ProgressView localProgressView = this.pv;
      if (localProgressView == null) {
        return;
      }
      switch (paramInt1)
      {
      default: 
        break;
      case 3: 
        if ((this.mIsButtonTextCustomized) && (!TextUtils.isEmpty(this.mCustomDownloadFailButtonText))) {
          this.pv.setText(this.mCustomDownloadFailButtonText);
        } else {
          this.pv.setText(ProgressView.TAG_DOWNLOAD_FAILED);
        }
        this.pv.resert();
        break;
      case 2: 
        if ((this.mIsButtonTextCustomized) && (!TextUtils.isEmpty(this.mCustomDownloadingButtonText))) {
          this.pv.setText(this.mCustomDownloadingButtonText);
        } else {
          this.pv.setText(ProgressView.TAG_DOWNLOADING);
        }
        this.pv.setProgress(paramInt2);
        break;
      case 1: 
        if ((this.mIsButtonTextCustomized) && (!TextUtils.isEmpty(this.mCustomDownloadSuccButtonText))) {
          this.pv.setText(this.mCustomDownloadSuccButtonText);
        } else {
          this.pv.setText(ProgressView.TAG_INSTALL);
        }
        this.pv.setProgress(100);
      }
      return;
    }
    finally {}
  }
  
  public void show()
  {
    initDensity(this.mContext);
    if (!canShowAlertDialog(this.mContext)) {
      return;
    }
    if (this.alertDialog == null)
    {
      this.alertDialog = new AlertDialog.Builder(this.mContext).create();
      this.alertDialog.show();
      Object localObject1 = this.alertDialog.getWindow();
      ((Window)localObject1).setContentView(createLayout(this.mContext, (Window)localObject1, this.mType));
      localObject1 = this.pv;
      if ((localObject1 != null) && (this.mListener != null)) {
        ((ProgressView)localObject1).setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (ProgressAlertDialog.this.mListener != null) {
              ProgressAlertDialog.this.mListener.onInstallButtonClick();
            }
            ProgressAlertDialog.this.pv.setEnabled(false);
            if ((ProgressAlertDialog.this.mIsButtonTextCustomized) && (!TextUtils.isEmpty(ProgressAlertDialog.this.mCustomDownloadingButtonText)))
            {
              ProgressAlertDialog.this.pv.setText(ProgressAlertDialog.this.mCustomDownloadingButtonText);
              return;
            }
            ProgressAlertDialog.this.pv.setText(ProgressView.TAG_DOWNLOADING);
          }
        });
      }
      localObject1 = this.mMessage;
      if (localObject1 != null) {
        ((TextView)localObject1).setText(this.mTip);
      }
      localObject1 = this.ivTagView;
      if (localObject1 != null)
      {
        Bitmap localBitmap = this.mBitmap;
        if (localBitmap != null) {
          ((ImageView)localObject1).setImageBitmap(localBitmap);
        }
      }
      localObject1 = this.btnClose;
      if (localObject1 != null)
      {
        try
        {
          ((Button)localObject1).setBackgroundDrawable(createSelector(TBSResources.getDrawable("x5_close_progress_pressed"), TBSResources.getDrawable("x5_close_progress")));
        }
        catch (Exception localException)
        {
          Log.e("ProgressAlertDialog", localException.toString());
        }
        this.btnClose.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            if (ProgressAlertDialog.this.alertDialog != null) {
              ProgressAlertDialog.this.alertDialog.dismiss();
            }
          }
        });
      }
      this.alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener()
      {
        public void onDismiss(DialogInterface paramAnonymousDialogInterface)
        {
          if (ProgressAlertDialog.this.isDownloadFinished)
          {
            if (ProgressAlertDialog.this.mBitmap != null) {
              ProgressAlertDialog.this.mBitmap = null;
            }
            ProgressAlertDialog.access$302(ProgressAlertDialog.this, null);
          }
        }
      });
    }
    if (this.mAutoDownload)
    {
      Object localObject2 = this.mListener;
      if (localObject2 != null) {
        ((IProgressViewListener)localObject2).onInstallButtonClick();
      }
      localObject2 = this.pv;
      if (localObject2 != null)
      {
        ((ProgressView)localObject2).setEnabled(false);
        if ((this.mIsButtonTextCustomized) && (!TextUtils.isEmpty(this.mCustomDownloadingButtonText)))
        {
          this.pv.setText(this.mCustomDownloadingButtonText);
          return;
        }
        this.pv.setText(ProgressView.TAG_DOWNLOADING);
      }
    }
  }
  
  public static abstract class ExtButtonClickListenerSerial
    implements Serializable
  {
    private static final long serialVersionUID = 9006158766622533720L;
    View.OnClickListener mClickListener = null;
    
    View.OnClickListener getExtButtonClickListener()
    {
      if (this.mClickListener == null) {
        this.mClickListener = new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ProgressAlertDialog.ExtButtonClickListenerSerial.this.onClick(paramAnonymousView);
          }
        };
      }
      return this.mClickListener;
    }
    
    public abstract void onClick(View paramView);
  }
  
  public static abstract interface IProgressViewListener
  {
    public abstract void onInstallButtonClick();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\common\ui\dialog\ProgressAlertDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */