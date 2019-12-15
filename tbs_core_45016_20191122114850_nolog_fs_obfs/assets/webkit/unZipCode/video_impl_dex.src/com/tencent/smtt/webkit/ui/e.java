package com.tencent.smtt.webkit.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import com.tencent.smtt.listbox.a;
import com.tencent.smtt.webkit.SmttResource;
import java.util.HashMap;

public class e
  extends Dialog
  implements DialogInterface, View.OnClickListener
{
  public static final int BOTTOM_BUTTON_HEIGHT;
  public static final int BTN_ID_BASE = 10000;
  public static final int BTN_ID_NEGATIVE = 10001;
  public static final int BTN_ID_NEUTRAL = 10002;
  public static final int BTN_ID_POSITIVE = 10000;
  public static final int DIALOG_WIDTH;
  public static final int LINE_COLOR;
  public static final int LINE_NIGHT_COLOR;
  public static final int MARGIN_LEFT;
  public static final int MARGIN_PROMPT_EDITTEXT_LEFT;
  public static final int MARGIN_PROMPT_EDITTEXT_TOP;
  public static final int MARGIN_SUBTITLE_BOTTOM;
  public static final int MARGIN_SUBTITLE_TOP;
  public static final int MARGIN_TITLE_TOP;
  public static final int NIGHT_MASK_DIALOG_BACKGROUND = 1;
  public static final int NIGHT_MASK_IMAGE = 3;
  public static final int NIGHT_MASK_MENU_BACKGROUND = 2;
  public static final int PROMPT_EDITTEXT_HEIGHT;
  private static final float S_BACKGROUND_DRAWABLE_DENSITY = 3.0F;
  private static final float S_CURRENT_DENSITY;
  public static final int TEXTSIZE_T2 = 14;
  public static final int TEXTSIZE_T3 = 16;
  public static final int TEXTSIZE_T4 = 18;
  public static final int TEXT_BLACK_COLOR;
  public static final int TEXT_BLACK_NIGHT_COLOR;
  public static final int TEXT_BLUE_COLOR = SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_dialog_blue_text_color", "color"));
  public static final int TEXT_BLUE_NIGHT_COLOR;
  public static final int TEXT_GRAY_COLOR;
  public static final int TEXT_GRAY_NIGHT_COLOR;
  public static final int TEXT_RED_COLOR;
  public static final int TEXT_RED_NIGHT_COLOR;
  public static final int TITLE_LINE_SPACE;
  public static final int TYPE_ALERT = 0;
  public static final int TYPE_CONFIRM = 2;
  public static final int TYPE_PROMPT = 1;
  public static final int TYPE_THREEBUTTON = 3;
  public static final int X5_TBS_HANDLE_VIEW_PIX_HEIGHT = 66;
  public static final int X5_TBS_MENU_BACKGOUND_WITHOUT_ARROW_SHADE_FIXED_PIX_HEIGHT;
  public static final int X5_TBS_MENU_BACKGOUND_WITHOUT_ARROW_SHADE_FIXED_PIX_WIDTH;
  public static final int X5_TBS_MENU_BACKGOUND_WITHOUT_ARROW_TRANSPARENT_PIX_WIDTH;
  public static final int X5_TBS_MENU_BACKGOUND_WITH_ARROW_SHADE_FIXED_PIX_HEIGHT;
  public static final int X5_TBS_MENU_BACKGOUND_WITH_ARROW_SHADE_FIXED_PIX_WIDTH;
  private static HashMap<String, Drawable> s_x5_tbs_menu_drawable_map = new HashMap();
  public View mBottonLineView;
  public LinearLayout mButtonArea;
  public boolean[] mButtonAutoDismiss;
  public LinearLayout mContentArea;
  public ScrollView mContentScrollView;
  public Context mContext;
  public LinearLayout mCustomArea;
  public int mDialogWidth;
  public EditText mEditTextView;
  public boolean mIsNight = false;
  public TextView mNegativeBtn = null;
  public DialogInterface.OnClickListener mNegativeButtonListener = null;
  public TextView mNeutralBtn = null;
  public DialogInterface.OnClickListener mNeutralButtonListener = null;
  public TextView mPositiveBtn = null;
  public DialogInterface.OnClickListener mPositiveButtonListener = null;
  public LinearLayout mRoot;
  public TextView mSubTitleTextView;
  public LinearLayout mTitleArea;
  public TextView mTitleTextView;
  public int mType;
  
  static
  {
    TEXT_BLACK_COLOR = SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_dialog_black_text_color", "color"));
    TEXT_GRAY_COLOR = SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_dialog_gray_text_color", "color"));
    TEXT_RED_COLOR = SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_dialog_red_text_color", "color"));
    LINE_COLOR = SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_dialog_line_color", "color"));
    TEXT_BLUE_NIGHT_COLOR = SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_dialog_blue_text_night_color", "color"));
    TEXT_BLACK_NIGHT_COLOR = SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_dialog_black_text_night_color", "color"));
    TEXT_GRAY_NIGHT_COLOR = SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_dialog_gray_text_night_color", "color"));
    TEXT_RED_NIGHT_COLOR = SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_dialog_red_text_night_color", "color"));
    LINE_NIGHT_COLOR = SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_dialog_line_night_color", "color"));
    DIALOG_WIDTH = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_width", "dimen"));
    TITLE_LINE_SPACE = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_title_linespace", "dimen"));
    MARGIN_TITLE_TOP = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_title_magin_top", "dimen"));
    MARGIN_LEFT = MARGIN_TITLE_TOP / 2;
    MARGIN_SUBTITLE_TOP = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_subtitle_magin_top", "dimen"));
    MARGIN_SUBTITLE_BOTTOM = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_subtitle_magin_bottom", "dimen"));
    MARGIN_PROMPT_EDITTEXT_TOP = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_prompt_edittext_magin_top", "dimen"));
    MARGIN_PROMPT_EDITTEXT_LEFT = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_prompt_edittext_magin_left", "dimen"));
    PROMPT_EDITTEXT_HEIGHT = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_prompt_edittext_height", "dimen"));
    BOTTOM_BUTTON_HEIGHT = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_dialog_bottom_button_height", "dimen"));
    S_CURRENT_DENSITY = SmttResource.getResources().getDisplayMetrics().density;
    float f = S_CURRENT_DENSITY;
    X5_TBS_MENU_BACKGOUND_WITHOUT_ARROW_SHADE_FIXED_PIX_WIDTH = (int)(34.0F * f / 3.0F);
    X5_TBS_MENU_BACKGOUND_WITHOUT_ARROW_SHADE_FIXED_PIX_HEIGHT = (int)(f * 48.0F / 3.0F);
    X5_TBS_MENU_BACKGOUND_WITHOUT_ARROW_TRANSPARENT_PIX_WIDTH = (int)(7.0F * f / 3.0F);
    X5_TBS_MENU_BACKGOUND_WITH_ARROW_SHADE_FIXED_PIX_WIDTH = (int)(43.0F * f / 3.0F);
    X5_TBS_MENU_BACKGOUND_WITH_ARROW_SHADE_FIXED_PIX_HEIGHT = (int)(f * 48.0F / 3.0F);
  }
  
  public e(Context paramContext, int paramInt, boolean paramBoolean)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mType = paramInt;
    this.mIsNight = paramBoolean;
    init();
  }
  
  public static Drawable getImageDrawable(String paramString, boolean paramBoolean, int paramInt)
  {
    paramString = a.a(SmttResource.getResourceContext(), paramString);
    if ((paramString != null) && (paramBoolean))
    {
      switch (paramInt)
      {
      default: 
        paramInt = 1879048192;
        break;
      case 1: 
      case 2: 
        paramInt = -14012362;
      }
      paramString.setColorFilter(paramInt, PorterDuff.Mode.SRC_ATOP);
    }
    return paramString;
  }
  
  private static Drawable getMenuBgImageDrawableInternal(String paramString)
  {
    Drawable localDrawable2 = (Drawable)s_x5_tbs_menu_drawable_map.get(paramString);
    Drawable localDrawable1 = localDrawable2;
    if (localDrawable2 == null)
    {
      localDrawable2 = a.a(SmttResource.getResourceContext(), paramString);
      localDrawable1 = localDrawable2;
      if (localDrawable2 != null)
      {
        s_x5_tbs_menu_drawable_map.put(paramString, localDrawable2);
        localDrawable1 = localDrawable2;
      }
    }
    return localDrawable1;
  }
  
  public static Drawable getMenuBgWithArrowDrawable(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramBoolean1)
    {
      if (paramBoolean2) {
        return getMenuBgImageDrawableInternal("x5_tbs_menu_bg_arrow_up_night");
      }
      return getMenuBgImageDrawableInternal("x5_tbs_menu_bg_arrow_down_night");
    }
    if (paramBoolean2) {
      return getMenuBgImageDrawableInternal("x5_tbs_menu_bg_arrow_up_day");
    }
    return getMenuBgImageDrawableInternal("x5_tbs_menu_bg_arrow_down_day");
  }
  
  public static Drawable getMenuBgWithoutArrowDrawable(boolean paramBoolean)
  {
    if (paramBoolean) {
      return getMenuBgImageDrawableInternal("x5_tbs_menu_background_night");
    }
    return getMenuBgImageDrawableInternal("x5_tbs_menu_background_day");
  }
  
  public static void setViewBackground(View paramView, Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramView.setBackground(paramDrawable);
      return;
    }
    paramView.setBackgroundDrawable(paramDrawable);
  }
  
  public void addSubTitle(CharSequence paramCharSequence)
  {
    int i;
    if (this.mIsNight) {
      i = TEXT_GRAY_NIGHT_COLOR;
    } else {
      i = TEXT_GRAY_COLOR;
    }
    this.mSubTitleTextView = creatTextView(paramCharSequence, i, 14, MARGIN_LEFT, MARGIN_SUBTITLE_TOP, 17);
    this.mContentArea.addView(this.mSubTitleTextView, 0);
    if ((this.mBottonLineView != null) && (this.mType == 3)) {
      setBottomButtonMarginTop(MARGIN_SUBTITLE_BOTTOM);
    }
  }
  
  public void addToContentArea(View paramView)
  {
    LinearLayout localLinearLayout = this.mContentArea;
    if (localLinearLayout != null) {
      localLinearLayout.addView(paramView);
    }
  }
  
  public void addToContentArea(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    LinearLayout localLinearLayout = this.mContentArea;
    if (localLinearLayout != null) {
      localLinearLayout.addView(creatTextView(paramString, paramInt1, paramInt2, paramInt3, paramInt4, paramInt5));
    }
  }
  
  public void addToCustomArea(View paramView)
  {
    LinearLayout localLinearLayout = this.mCustomArea;
    if (localLinearLayout != null) {
      localLinearLayout.addView(paramView);
    }
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
      setViewBackground(localTextView, SmttResource.getResources().getDrawable(SmttResource.loadIdentifierResource("x5_tbs_dialog_press_night_background", "drawable")));
    } else {
      setViewBackground(localTextView, SmttResource.getResources().getDrawable(SmttResource.loadIdentifierResource("x5_tbs_dialog_press_background", "drawable")));
    }
    localTextView.setGravity(17);
    return localTextView;
  }
  
  public EditText creatEditTextView()
  {
    EditText localEditText = new EditText(this.mContext);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, PROMPT_EDITTEXT_HEIGHT);
    int i = MARGIN_PROMPT_EDITTEXT_LEFT;
    localLayoutParams.setMargins(i, MARGIN_PROMPT_EDITTEXT_TOP, i, 0);
    localEditText.setLayoutParams(localLayoutParams);
    localEditText.setTextSize(16.0F);
    if (this.mIsNight) {
      i = TEXT_BLACK_NIGHT_COLOR;
    } else {
      i = TEXT_BLACK_COLOR;
    }
    localEditText.setTextColor(i);
    localEditText.setSingleLine(true);
    localEditText.setHorizontallyScrolling(true);
    setViewBackground(localEditText, getImageDrawable("x5_tbs_dialog_edittext_background", this.mIsNight, 3));
    return localEditText;
  }
  
  public View creatLine(int paramInt1, boolean paramBoolean, int paramInt2)
  {
    View localView = new View(this.mContext);
    LinearLayout.LayoutParams localLayoutParams;
    if (paramBoolean) {
      localLayoutParams = new LinearLayout.LayoutParams(1, -1);
    } else {
      localLayoutParams = new LinearLayout.LayoutParams(-1, 1);
    }
    localLayoutParams.topMargin = paramInt2;
    localView.setLayoutParams(localLayoutParams);
    localView.setBackgroundColor(paramInt1);
    return localView;
  }
  
  public TextView creatTextView(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    TextView localTextView = new TextView(this.mContext);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -2);
    localLayoutParams.setMargins(paramInt3, paramInt4, paramInt3, 0);
    localTextView.setLayoutParams(localLayoutParams);
    localTextView.setTextColor(paramInt1);
    localTextView.setTextSize(paramInt2);
    localTextView.setGravity(paramInt5);
    localTextView.setText(paramCharSequence);
    return localTextView;
  }
  
  public void dismiss()
  {
    super.dismiss();
  }
  
  public int getDefaultButtonMarginTop()
  {
    switch (this.mType)
    {
    default: 
      return 0;
    case 1: 
      return MARGIN_PROMPT_EDITTEXT_TOP;
    }
    return MARGIN_TITLE_TOP;
  }
  
  public String getEditTextViewText()
  {
    EditText localEditText = this.mEditTextView;
    if (localEditText != null) {
      return localEditText.getText().toString();
    }
    return null;
  }
  
  public void init()
  {
    this.mButtonAutoDismiss = new boolean[] { 1, 1, 1 };
    this.mDialogWidth = DIALOG_WIDTH;
    requestWindowFeature(1);
    getWindow().setBackgroundDrawable(getImageDrawable("x5_tbs_dialog_backgroud", this.mIsNight, 1));
    setCanceledOnTouchOutside(false);
    this.mRoot = new b(this.mContext);
    Object localObject = new LinearLayout.LayoutParams(-1, -2);
    this.mRoot.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mRoot.setOrientation(1);
    setContentView(this.mRoot);
    this.mTitleArea = new LinearLayout(this.mContext);
    localObject = new LinearLayout.LayoutParams(-1, -2);
    this.mTitleArea.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mTitleArea.setOrientation(1);
    if (this.mIsNight) {
      i = TEXT_BLACK_NIGHT_COLOR;
    } else {
      i = TEXT_BLACK_COLOR;
    }
    int k = MARGIN_LEFT;
    int j;
    if (this.mType == 1) {
      j = MARGIN_TITLE_TOP / 2;
    } else {
      j = MARGIN_TITLE_TOP;
    }
    this.mTitleTextView = creatTextView("", i, 18, k, j, 17);
    this.mTitleTextView.setLineSpacing(TITLE_LINE_SPACE, 1.0F);
    this.mTitleArea.addView(this.mTitleTextView);
    this.mRoot.addView(this.mTitleArea);
    this.mCustomArea = new LinearLayout(this.mContext);
    localObject = new LinearLayout.LayoutParams(-1, -2);
    this.mCustomArea.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mCustomArea.setOrientation(1);
    this.mRoot.addView(this.mCustomArea);
    this.mContentScrollView = new ScrollView(this.mContext);
    localObject = new LinearLayout.LayoutParams(-1, -2);
    ((LinearLayout.LayoutParams)localObject).gravity = 16;
    this.mContentScrollView.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mRoot.addView(this.mContentScrollView);
    this.mContentArea = new LinearLayout(this.mContext);
    localObject = new LinearLayout.LayoutParams(-1, -2);
    this.mContentArea.setLayoutParams((ViewGroup.LayoutParams)localObject);
    this.mContentArea.setOrientation(1);
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
    this.mRoot.addView(this.mButtonArea);
    if (this.mIsNight) {
      i = LINE_NIGHT_COLOR;
    } else {
      i = LINE_COLOR;
    }
    this.mBottonLineView = creatLine(i, false, getDefaultButtonMarginTop());
    this.mButtonArea.addView(this.mBottonLineView);
    localObject = new LinearLayout(this.mContext);
    LinearLayout.LayoutParams localLayoutParams;
    if (this.mType != 3)
    {
      localLayoutParams = new LinearLayout.LayoutParams(-1, BOTTOM_BUTTON_HEIGHT);
      ((LinearLayout)localObject).setOrientation(0);
      ((LinearLayout)localObject).setLayoutParams(localLayoutParams);
    }
    else
    {
      i = BOTTOM_BUTTON_HEIGHT * 3;
      if (com.tencent.smtt.webkit.e.a().n()) {
        i = BOTTOM_BUTTON_HEIGHT * 2;
      }
      localLayoutParams = new LinearLayout.LayoutParams(-1, i);
      ((LinearLayout)localObject).setOrientation(1);
      ((LinearLayout)localObject).setLayoutParams(localLayoutParams);
    }
    this.mButtonArea.addView((View)localObject);
    int i = this.mType;
    if (i == 3)
    {
      if (this.mIsNight) {
        i = TEXT_RED_NIGHT_COLOR;
      } else {
        i = TEXT_RED_COLOR;
      }
      this.mNeutralBtn = creatButton("", 18, i, 10002);
      ((LinearLayout)localObject).addView(this.mNeutralBtn);
      if (this.mIsNight) {
        i = LINE_NIGHT_COLOR;
      } else {
        i = LINE_COLOR;
      }
      ((LinearLayout)localObject).addView(creatLine(i, false, 0));
      if (!com.tencent.smtt.webkit.e.a().n())
      {
        if (this.mIsNight) {
          i = TEXT_BLUE_NIGHT_COLOR;
        } else {
          i = TEXT_BLUE_COLOR;
        }
        this.mPositiveBtn = creatButton("", 18, i, 10000);
        ((LinearLayout)localObject).addView(this.mPositiveBtn);
        if (this.mIsNight) {
          i = LINE_NIGHT_COLOR;
        } else {
          i = LINE_COLOR;
        }
        ((LinearLayout)localObject).addView(creatLine(i, false, 0));
      }
      if (this.mIsNight) {
        i = TEXT_BLACK_NIGHT_COLOR;
      } else {
        i = TEXT_BLACK_COLOR;
      }
      this.mNegativeBtn = creatButton("", 18, i, 10001);
      ((LinearLayout)localObject).addView(this.mNegativeBtn);
      return;
    }
    if (i != 0)
    {
      if (this.mIsNight) {
        i = TEXT_BLACK_NIGHT_COLOR;
      } else {
        i = TEXT_BLACK_COLOR;
      }
      this.mNegativeBtn = creatButton("", 18, i, 10001);
      ((LinearLayout)localObject).addView(this.mNegativeBtn);
      if (this.mIsNight) {
        i = LINE_NIGHT_COLOR;
      } else {
        i = LINE_COLOR;
      }
      ((LinearLayout)localObject).addView(creatLine(i, true, 0));
    }
    if (this.mIsNight) {
      i = TEXT_BLUE_NIGHT_COLOR;
    } else {
      i = TEXT_BLUE_COLOR;
    }
    this.mPositiveBtn = creatButton("", 18, i, 10000);
    ((LinearLayout)localObject).addView(this.mPositiveBtn);
  }
  
  public void onClick(View paramView)
  {
    DialogInterface.OnClickListener localOnClickListener;
    switch (paramView.getId())
    {
    default: 
      break;
    case 10002: 
      localOnClickListener = this.mNeutralButtonListener;
      if (localOnClickListener != null) {
        localOnClickListener.onClick(this, -3);
      }
      break;
    case 10001: 
      localOnClickListener = this.mNegativeButtonListener;
      if (localOnClickListener != null) {
        localOnClickListener.onClick(this, -2);
      }
      break;
    case 10000: 
      localOnClickListener = this.mPositiveButtonListener;
      if (localOnClickListener != null) {
        localOnClickListener.onClick(this, -1);
      }
      break;
    }
    if ((paramView.getId() >= 10000) && (paramView.getId() <= 10002) && (this.mButtonAutoDismiss[(paramView.getId() - 10000)] != 0)) {
      dismiss();
    }
  }
  
  public void removeContents()
  {
    LinearLayout localLinearLayout = this.mContentArea;
    if (localLinearLayout != null) {
      localLinearLayout.removeAllViews();
    }
  }
  
  public void setBottomButtonMarginTop(int paramInt)
  {
    Object localObject = this.mBottonLineView;
    if (localObject != null)
    {
      localObject = (LinearLayout.LayoutParams)((View)localObject).getLayoutParams();
      ((LinearLayout.LayoutParams)localObject).topMargin = paramInt;
      this.mBottonLineView.setLayoutParams((ViewGroup.LayoutParams)localObject);
    }
  }
  
  public void setButton(int paramInt1, CharSequence paramCharSequence, int paramInt2, DialogInterface.OnClickListener paramOnClickListener)
  {
    setButton(paramInt1, paramCharSequence, paramInt2, paramOnClickListener, true);
  }
  
  public void setButton(int paramInt1, CharSequence paramCharSequence, int paramInt2, DialogInterface.OnClickListener paramOnClickListener, boolean paramBoolean)
  {
    setButton(paramInt1, paramCharSequence, paramOnClickListener);
    switch (paramInt1)
    {
    default: 
      return;
    case 10002: 
      paramCharSequence = this.mNeutralBtn;
      if (paramCharSequence != null) {
        paramCharSequence.setTextColor(paramInt2);
      }
      break;
    case 10001: 
      paramCharSequence = this.mNegativeBtn;
      if (paramCharSequence != null) {
        paramCharSequence.setTextColor(paramInt2);
      }
      break;
    case 10000: 
      paramCharSequence = this.mPositiveBtn;
      if (paramCharSequence != null) {
        paramCharSequence.setTextColor(paramInt2);
      }
      break;
    }
    this.mButtonAutoDismiss[(paramInt1 - 10000)] = paramBoolean;
  }
  
  public void setButton(int paramInt, CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
  {
    TextView localTextView;
    switch (paramInt)
    {
    default: 
      
    case 10002: 
      localTextView = this.mNeutralBtn;
      if (localTextView != null)
      {
        localTextView.setText(paramCharSequence);
        this.mNeutralBtn.setOnClickListener(this);
        this.mNeutralButtonListener = paramOnClickListener;
        return;
      }
      break;
    case 10001: 
      localTextView = this.mNegativeBtn;
      if (localTextView != null)
      {
        localTextView.setText(paramCharSequence);
        this.mNegativeBtn.setOnClickListener(this);
        this.mNegativeButtonListener = paramOnClickListener;
        return;
      }
      break;
    case 10000: 
      localTextView = this.mPositiveBtn;
      if (localTextView != null)
      {
        localTextView.setText(paramCharSequence);
        this.mPositiveBtn.setOnClickListener(this);
        this.mPositiveButtonListener = paramOnClickListener;
      }
      break;
    }
  }
  
  public void setEditTextViewText(CharSequence paramCharSequence)
  {
    EditText localEditText = this.mEditTextView;
    if (localEditText != null)
    {
      localEditText.setText(paramCharSequence);
      this.mEditTextView.clearFocus();
    }
  }
  
  public void setSubTitleText(String paramString)
  {
    TextView localTextView = this.mSubTitleTextView;
    if (localTextView != null) {
      localTextView.setText(paramString);
    }
  }
  
  public void setTitleMargin(int paramInt1, int paramInt2)
  {
    Object localObject = this.mTitleTextView;
    if (localObject != null)
    {
      localObject = (LinearLayout.LayoutParams)((TextView)localObject).getLayoutParams();
      ((LinearLayout.LayoutParams)localObject).topMargin = paramInt1;
      ((LinearLayout.LayoutParams)localObject).bottomMargin = paramInt2;
      this.mTitleTextView.setLayoutParams((ViewGroup.LayoutParams)localObject);
    }
  }
  
  public void setTitleText(CharSequence paramCharSequence)
  {
    TextView localTextView = this.mTitleTextView;
    if (localTextView != null) {
      localTextView.setText(paramCharSequence);
    }
  }
  
  public void setTitleTextGravity(int paramInt)
  {
    TextView localTextView = this.mTitleTextView;
    if (localTextView != null) {
      localTextView.setGravity(paramInt);
    }
  }
  
  public void show()
  {
    getWindow().setLayout(this.mDialogWidth, -2);
    try
    {
      super.show();
      return;
    }
    catch (Throwable localThrowable) {}
  }
  
  public static class a
  {
    private e a;
    
    public a(Context paramContext, int paramInt, boolean paramBoolean)
    {
      this.a = new e(paramContext, paramInt, paramBoolean);
    }
    
    public a a()
    {
      this.a.show();
      return this;
    }
    
    public a a(DialogInterface.OnCancelListener paramOnCancelListener)
    {
      this.a.setOnCancelListener(paramOnCancelListener);
      return this;
    }
    
    public a a(CharSequence paramCharSequence)
    {
      this.a.addSubTitle(paramCharSequence);
      return this;
    }
    
    public a a(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.a.setButton(10001, paramCharSequence, paramOnClickListener);
      return this;
    }
    
    public a a(String paramString)
    {
      this.a.setTitleText(paramString);
      return this;
    }
    
    public a a(boolean paramBoolean)
    {
      this.a.setCancelable(paramBoolean);
      return this;
    }
    
    public String a()
    {
      return this.a.getEditTextViewText();
    }
    
    public a b()
    {
      return this;
    }
    
    public a b(CharSequence paramCharSequence)
    {
      this.a.setEditTextViewText(paramCharSequence);
      return this;
    }
    
    public a b(CharSequence paramCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.a.setButton(10000, paramCharSequence, paramOnClickListener);
      return this;
    }
  }
  
  class b
    extends LinearLayout
  {
    public b(Context paramContext)
    {
      super();
    }
    
    protected void onMeasure(int paramInt1, int paramInt2)
    {
      super.onMeasure(paramInt1, paramInt2);
      if ((e.this.mTitleArea != null) && (e.this.mCustomArea != null) && (e.this.mContentArea != null) && (e.this.mButtonArea != null) && (e.this.mContentScrollView != null))
      {
        int i = getMeasuredHeight();
        int j = View.MeasureSpec.getSize(paramInt2);
        paramInt2 = View.MeasureSpec.makeMeasureSpec(j, 0);
        e.this.mTitleArea.measure(paramInt1, paramInt2);
        e.this.mCustomArea.measure(paramInt1, paramInt2);
        e.this.mContentArea.measure(paramInt1, paramInt2);
        e.this.mButtonArea.measure(paramInt1, paramInt2);
        paramInt2 = e.this.mTitleArea.getMeasuredHeight() + e.this.mCustomArea.getMeasuredHeight() + e.this.mContentArea.getMeasuredHeight() + e.this.mButtonArea.getMeasuredHeight();
        if ((paramInt2 > i) && (paramInt2 > j))
        {
          e.this.mContentScrollView.getLayoutParams().height = (i - e.this.mTitleArea.getMeasuredHeight() - e.this.mCustomArea.getMeasuredHeight() - e.this.mButtonArea.getMeasuredHeight());
          paramInt2 = i;
        }
        else
        {
          e.this.mContentScrollView.getLayoutParams().height = -2;
        }
        super.onMeasure(paramInt1, View.MeasureSpec.makeMeasureSpec(paramInt2, Integer.MIN_VALUE));
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\smtt\webkit\ui\e.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */