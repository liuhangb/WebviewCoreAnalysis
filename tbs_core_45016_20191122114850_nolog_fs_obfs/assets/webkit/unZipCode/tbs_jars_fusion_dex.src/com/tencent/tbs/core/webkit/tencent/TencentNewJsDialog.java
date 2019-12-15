package com.tencent.tbs.core.webkit.tencent;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.ui.e;

public class TencentNewJsDialog
  extends e
{
  private static final int TEXTSIZE_15 = 15;
  private static final int TEXTSIZE_16 = 16;
  private static final int TEXTSIZE_17 = 17;
  private static final int TEXTSIZE_18 = 18;
  private static final int X5_DIALOG_BUTTON_AREA_BUTTON_COLOR1 = -11756806;
  private static final int X5_DIALOG_BUTTON_AREA_BUTTON_COLOR1_NIGHT = -13017978;
  private static final int X5_DIALOG_BUTTON_AREA_BUTTON_COLOR2 = -14408668;
  private static final int X5_DIALOG_BUTTON_AREA_BUTTON_COLOR2_NIGHT = -9933452;
  private static final int X5_DIALOG_BUTTON_AREA_HEIGHT = 54;
  private static final int X5_DIALOG_BUTTON_AREA_LINE_COLOR = -1184275;
  private static final int X5_DIALOG_BUTTON_AREA_LINE_COLOR_NIGHT = -13552069;
  private static final int X5_DIALOG_CONTENT_AREA_PADDING = 10;
  private static final int X5_DIALOG_CONTENT_EDIT_HEIGHT = 28;
  private static final int X5_DIALOG_CONTENT_EDIT_LEFT_PADDING = 8;
  private static final int X5_DIALOG_CONTENT_EDIT_TEXT_COLOR = -10066330;
  private static final int X5_DIALOG_CONTENT_EDIT_TEXT_COLOR_NIGHT = -11512484;
  private static final int X5_DIALOG_CONTENT_EDIT_TOP_MARGIN = 8;
  private static final int X5_DIALOG_CONTENT_TEXT_COLOR = -7368817;
  private static final int X5_DIALOG_CONTENT_TEXT_COLOR_NIGHT = -12433843;
  private static final int X5_DIALOG_DEFAULT_BACKGROUND_COLOR = -1;
  private static final int X5_DIALOG_DEFAULT_BACKGROUND_COLOR_NIGHT = -14671065;
  private static final int X5_DIALOG_LINE_WIDTH = 1;
  private static final int X5_DIALOG_TITLE_AREA_BACKGROUND_COLOR = -657931;
  private static final int X5_DIALOG_TITLE_AREA_BACKGROUND_COLOR_NIGHT = -14473171;
  private static final int X5_DIALOG_TITLE_AREA_BOTTOM_PADDING = 10;
  private static final int X5_DIALOG_TITLE_AREA_HEIGHT = 69;
  private static final int X5_DIALOG_TITLE_AREA_LEFT_PADDING = 12;
  private static final int X5_DIALOG_TITLE_AREA_TEXT_COLOR1 = -14408668;
  private static final int X5_DIALOG_TITLE_AREA_TEXT_COLOR1_NIGHT = -9933452;
  private static final int X5_DIALOG_TITLE_AREA_TEXT_COLOR2 = -7368817;
  private static final int X5_DIALOG_TITLE_AREA_TEXT_COLOR2_NIGHT = -12433843;
  private static final int X5_DIALOG_TITLE_AREA_TOP_PADDING = 12;
  private static final int X5_DIALOG_WIDTH = 280;
  private static float sDensity = SmttResource.getResources().getDisplayMetrics().density;
  private TextView mSecondTitleTextView;
  
  public TencentNewJsDialog(Context paramContext, int paramInt, boolean paramBoolean)
  {
    super(paramContext, paramInt, paramBoolean);
  }
  
  private int dip2px(int paramInt)
  {
    return (int)(paramInt * sDensity);
  }
  
  public void addSubTitle(CharSequence paramCharSequence)
  {
    int i;
    if (this.mIsNight) {
      i = -11512484;
    } else {
      i = -10066330;
    }
    this.mSubTitleTextView = creatTextView(paramCharSequence, i, 15, 0, 0, 17);
    this.mContentArea.addView(this.mSubTitleTextView, 0);
  }
  
  public TextView creatButton(String paramString, int paramInt1, int paramInt2, int paramInt3)
  {
    TextView localTextView = new TextView(this.mContext);
    localTextView.setTextSize(paramInt1);
    localTextView.setTextColor(paramInt2);
    localTextView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0F));
    localTextView.setText(paramString);
    localTextView.setFocusable(true);
    localTextView.setId(paramInt3);
    if (this.mIsNight) {
      localTextView.setBackgroundColor(-14671065);
    } else {
      setViewBackground(localTextView, SmttResource.getResources().getDrawable(SmttResource.loadIdentifierResource("x5_tbs_dialog_press_background", "drawable")));
    }
    localTextView.setGravity(17);
    return localTextView;
  }
  
  public EditText creatEditTextView()
  {
    EditText localEditText = new EditText(this.mContext);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, dip2px(28));
    localLayoutParams.setMargins(0, dip2px(8), 0, 0);
    localEditText.setLayoutParams(localLayoutParams);
    localEditText.setTextSize(15.0F);
    int i;
    if (this.mIsNight) {
      i = -11512484;
    } else {
      i = -10066330;
    }
    localEditText.setTextColor(i);
    localEditText.setSingleLine(true);
    localEditText.setHorizontallyScrolling(true);
    localEditText.setPadding(dip2px(8), 0, 0, 0);
    setViewBackground(localEditText, getImageDrawable("x5_tbs_dialog_edittext_background", this.mIsNight, 3));
    return localEditText;
  }
  
  public View creatLine(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    View localView = new View(this.mContext);
    LinearLayout.LayoutParams localLayoutParams;
    if (paramBoolean) {
      localLayoutParams = new LinearLayout.LayoutParams(dip2px(1), -1);
    } else {
      localLayoutParams = new LinearLayout.LayoutParams(-1, dip2px(1));
    }
    localLayoutParams.topMargin = paramInt2;
    localView.setLayoutParams(localLayoutParams);
    localView.setBackgroundColor(paramInt1);
    return localView;
  }
  
  public String getEditTextViewText()
  {
    if (this.mEditTextView != null) {
      return this.mEditTextView.getText().toString();
    }
    return null;
  }
  
  public void init()
  {
    this.mButtonAutoDismiss = new boolean[] { 1, 1, 1 };
    this.mDialogWidth = dip2px(280);
    requestWindowFeature(1);
    getWindow().setBackgroundDrawable(getImageDrawable("x5_tbs_dialog_backgroud", this.mIsNight, 1));
    setCanceledOnTouchOutside(false);
    this.mRoot = new DialogRootView(this.mContext);
    Object localObject = new LinearLayout.LayoutParams(-1, -2);
    this.mRoot.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mRoot.setOrientation(1);
    setContentView(this.mRoot);
    this.mTitleArea = new LinearLayout(this.mContext);
    localObject = new LinearLayout.LayoutParams(-1, dip2px(69));
    this.mTitleArea.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mTitleArea.setOrientation(1);
    this.mTitleArea.setPadding(dip2px(12), dip2px(12), 0, dip2px(10));
    localObject = this.mTitleArea;
    if (this.mIsNight) {
      i = -14473171;
    } else {
      i = -657931;
    }
    ((LinearLayout)localObject).setBackgroundColor(i);
    this.mTitleArea.setGravity(17);
    boolean bool = this.mIsNight;
    int j = -14408668;
    if (bool) {
      i = -9933452;
    } else {
      i = -14408668;
    }
    this.mTitleTextView = creatTextView("", i, 17, 0, 0, 3);
    this.mTitleArea.addView(this.mTitleTextView);
    if (this.mIsNight) {
      i = -12433843;
    } else {
      i = -7368817;
    }
    this.mSecondTitleTextView = creatTextView("", i, 15, 0, 0, 3);
    this.mTitleArea.addView(this.mSecondTitleTextView);
    this.mRoot.addView(this.mTitleArea);
    this.mContentScrollView = new ScrollView(this.mContext);
    localObject = new LinearLayout.LayoutParams(-1, -2);
    ((LinearLayout.LayoutParams)localObject).gravity = 16;
    this.mContentScrollView.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mRoot.addView(this.mContentScrollView);
    this.mContentArea = new LinearLayout(this.mContext);
    localObject = new LinearLayout.LayoutParams(-1, -2);
    this.mContentArea.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mContentArea.setOrientation(1);
    int i = dip2px(10);
    this.mContentArea.setPadding(i, i, i, i);
    localObject = this.mContentArea;
    if (this.mIsNight) {
      i = -14671065;
    } else {
      i = -1;
    }
    ((LinearLayout)localObject).setBackgroundColor(i);
    this.mContentScrollView.addView(this.mContentArea);
    if (this.mType == 1)
    {
      this.mEditTextView = creatEditTextView();
      this.mContentArea.addView(this.mEditTextView);
      this.mContentArea.setFocusable(true);
      this.mContentArea.setFocusableInTouchMode(true);
    }
    this.mButtonArea = new LinearLayout(this.mContext);
    localObject = new LinearLayout.LayoutParams(-1, -2);
    this.mButtonArea.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mButtonArea.setOrientation(1);
    this.mButtonArea.setGravity(17);
    this.mRoot.addView(this.mButtonArea);
    bool = this.mIsNight;
    int k = -13552069;
    if (bool) {
      i = -13552069;
    } else {
      i = -1184275;
    }
    this.mBottonLineView = creatLine(i, false, 0);
    this.mButtonArea.addView(this.mBottonLineView);
    localObject = new LinearLayout(this.mContext);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, dip2px(54));
    ((LinearLayout)localObject).setOrientation(0);
    ((LinearLayout)localObject).setLayoutParams(localLayoutParams);
    this.mButtonArea.addView((View)localObject);
    if (this.mType == 0)
    {
      if (this.mIsNight) {
        i = -13017978;
      } else {
        i = -11756806;
      }
      this.mPositiveBtn = creatButton("", 18, i, 10000);
    }
    else
    {
      if (this.mIsNight) {
        i = -9933452;
      } else {
        i = -14408668;
      }
      this.mPositiveBtn = creatButton("", 18, i, 10000);
      i = j;
      if (this.mIsNight) {
        i = -9933452;
      }
      this.mNegativeBtn = creatButton("", 18, i, 10001);
      ((LinearLayout)localObject).addView(this.mNegativeBtn);
      if (this.mIsNight) {
        i = k;
      } else {
        i = -1184275;
      }
      ((LinearLayout)localObject).addView(creatLine(i, true, 0));
    }
    ((LinearLayout)localObject).addView(this.mPositiveBtn);
  }
  
  public void setEditText(CharSequence paramCharSequence)
  {
    setEditTextViewText(paramCharSequence);
  }
  
  public void setMessage(CharSequence paramCharSequence)
  {
    addSubTitle(paramCharSequence);
    int i = dip2px(6);
    this.mSubTitleTextView.setPadding(i, i, i, i);
  }
  
  public void setNegativeButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    setButton(10001, paramCharSequence, paramOnClickListener);
  }
  
  public void setPositiveButton(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    setButton(10000, paramCharSequence, paramOnClickListener);
  }
  
  public void setSecondTitleText(String paramString)
  {
    this.mSecondTitleTextView.setText(paramString);
  }
  
  class DialogRootView
    extends LinearLayout
  {
    public DialogRootView(Context paramContext)
    {
      super();
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if ((TencentNewJsDialog.this.mTitleArea != null) && (TencentNewJsDialog.this.mContentArea != null) && (TencentNewJsDialog.this.mButtonArea != null) && (TencentNewJsDialog.this.mContentScrollView != null))
      {
        int i = getMeasuredHeight();
        int j = View.MeasureSpec.getSize(paramInt2);
        paramInt2 = View.MeasureSpec.makeMeasureSpec(j, 0);
        TencentNewJsDialog.this.mTitleArea.measure(paramInt1, paramInt2);
        TencentNewJsDialog.this.mContentArea.measure(paramInt1, paramInt2);
        TencentNewJsDialog.this.mButtonArea.measure(paramInt1, paramInt2);
        paramInt2 = TencentNewJsDialog.this.mTitleArea.getMeasuredHeight() + TencentNewJsDialog.this.mContentArea.getMeasuredHeight() + TencentNewJsDialog.this.mButtonArea.getMeasuredHeight() + TencentNewJsDialog.this.dip2px(54);
        if ((paramInt2 > i) && (paramInt2 > j))
        {
          TencentNewJsDialog.this.mContentScrollView.getLayoutParams().height = (i - TencentNewJsDialog.this.mTitleArea.getMeasuredHeight() - TencentNewJsDialog.this.mButtonArea.getMeasuredHeight());
          paramInt2 = i;
        }
        else
        {
          TencentNewJsDialog.this.mContentScrollView.getLayoutParams().height = -2;
        }
        super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(paramInt2, Integer.MIN_VALUE));
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\webkit\tencent\TencentNewJsDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */