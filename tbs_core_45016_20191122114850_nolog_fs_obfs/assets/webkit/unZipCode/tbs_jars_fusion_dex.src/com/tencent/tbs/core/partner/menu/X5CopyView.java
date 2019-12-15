package com.tencent.tbs.core.partner.menu;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.chromium.tencent.X5NativeBitmap;
import org.chromium.tencent.X5NativeBitmap.Factory;

public class X5CopyView
  extends LinearLayout
{
  private static final String COLLECT;
  private static final String COPY;
  private static final int COPY_BUTTON_HEIGHT = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_menu_button_height", "dimen"));
  private static final int COPY_BUTTON_MARGIN_BORDER;
  private static final int COPY_BUTTON_WIDHT;
  private static final String CUT;
  public static final Byte MODE_IN_INPUT;
  public static final Byte MODE_IN_NORMAL = Byte.valueOf();
  public static final Byte MODE_IN_NORMAL_URL;
  private static final String OPEN_URL;
  private static final String PASTE;
  private static String SEARCH;
  private static final String SELECT;
  private static final String SELECT_ALL;
  private static final String SHARE;
  private static final String TRANSLATE;
  private static Drawable bgDrawable;
  private static String mCallerAppName = "";
  private static float sDensity = -1.0F;
  Animation mAnimation;
  private ArrayList<X5MenuItem> mAppMenuItemArrayList = null;
  Bitmap mBitmap = null;
  private Context mContext;
  private int mCopyViewHeight = 0;
  private int mCopyViewWidth = 0;
  private boolean mHasEnterSelectionMode = false;
  boolean mInAnimation = false;
  private boolean mIsArrowUp = false;
  private boolean mIsNight;
  private boolean mIsTranslaterAvailable = false;
  private HashMap<Integer, Integer> mItemMap = new HashMap();
  private View.OnClickListener mListener;
  private int mLongPressTextExtensionMenu = 0;
  protected X5Selection mParentView = null;
  private boolean mSearchInMMShow = false;
  private String mSelectText = null;
  private boolean mShowOpenInBrowserWhenSelectionShown = false;
  private boolean mSupportSmartPickWord = false;
  
  static
  {
    MODE_IN_INPUT = Byte.valueOf((byte)1);
    MODE_IN_NORMAL_URL = Byte.valueOf((byte)2);
    PASTE = SmttResource.getString("x5_pop_menu_paste", "string");
    COPY = SmttResource.getString("x5_pop_menu_copy", "string");
    CUT = SmttResource.getString("x5_pop_menu_cut", "string");
    SELECT = SmttResource.getString("x5_pop_menu_choose_text", "string");
    SELECT_ALL = SmttResource.getString("x5_pop_menu_select_alltext", "string");
    SEARCH = SmttResource.getString("x5_pop_extension_menu_search_text", "string");
    SHARE = SmttResource.getString("x5_pop_extension_menu_share_text", "string");
    COLLECT = SmttResource.getString("x5_pop_extension_menu_collect_text", "string");
    TRANSLATE = SmttResource.getString("x5_pop_extension_menu_translate_text", "string");
    OPEN_URL = SmttResource.getString("x5_pop_extension_menu_openurl_text", "string");
    bgDrawable = null;
    COPY_BUTTON_MARGIN_BORDER = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_menu_button_margin_border", "dimen"));
    COPY_BUTTON_WIDHT = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_menu_copy_button_width", "dimen"));
  }
  
  public X5CopyView(X5Selection paramX5Selection, Context paramContext, View.OnClickListener paramOnClickListener, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mIsNight = paramBoolean1;
    this.mParentView = paramX5Selection;
    this.mListener = paramOnClickListener;
    this.mSupportSmartPickWord = paramBoolean2;
    this.mCopyViewHeight = (COPY_BUTTON_HEIGHT + com.tencent.smtt.webkit.ui.e.X5_TBS_MENU_BACKGOUND_WITH_ARROW_SHADE_FIXED_PIX_HEIGHT);
    setOrientation(0);
    setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    com.tencent.smtt.webkit.ui.e.setViewBackground(this, com.tencent.smtt.webkit.ui.e.getMenuBgWithArrowDrawable(this.mIsNight, this.mIsArrowUp));
    init(paramOnClickListener);
  }
  
  private void clearItems()
  {
    removeAllViews();
  }
  
  private void createItem(int paramInt, String paramString, boolean paramBoolean, View.OnClickListener paramOnClickListener)
  {
    X5CopyButton localX5CopyButton = new X5CopyButton(this.mContext, paramInt);
    localX5CopyButton.setText(paramString);
    localX5CopyButton.setOnClickListener(paramOnClickListener);
    localX5CopyButton.setTextSize(16.0F);
    localX5CopyButton.setGravity(17);
    updateX5CopyButtonStyle(localX5CopyButton, this.mIsNight);
    addView(localX5CopyButton, new LinearLayout.LayoutParams(COPY_BUTTON_WIDHT, COPY_BUTTON_HEIGHT));
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
  
  private static String getCallerAppName(Context paramContext)
  {
    if (("".equals(mCallerAppName)) && (paramContext != null)) {
      mCallerAppName = paramContext.getApplicationInfo().packageName;
    }
    return mCallerAppName;
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
  
  private void initMenuContainer(View.OnClickListener paramOnClickListener)
  {
    createItem(1, COPY, false, paramOnClickListener);
  }
  
  private void outAnimation()
  {
    ScaleAnimation localScaleAnimation1 = new ScaleAnimation(0.9F, 1.025F, 0.9F, 1.025F, 1, 0.5F, 1, 0.0F);
    localScaleAnimation1.setDuration(100L);
    localScaleAnimation1.setInterpolator(new DecelerateInterpolator(1.0F));
    final ScaleAnimation localScaleAnimation2 = new ScaleAnimation(1.025F, 1.0F, 1.025F, 1.0F, 1, 0.5F, 1, 0.0F);
    localScaleAnimation2.setDuration(100L);
    localScaleAnimation2.setInterpolator(new DecelerateInterpolator(1.0F));
    localScaleAnimation1.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        localScaleAnimation2.reset();
        localScaleAnimation2.start();
        X5CopyView.this.mAnimation = localScaleAnimation2;
      }
      
      public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public void onAnimationStart(Animation paramAnonymousAnimation)
      {
        X5CopyView.this.mInAnimation = true;
      }
    });
    localScaleAnimation2.setAnimationListener(new Animation.AnimationListener()
    {
      public void onAnimationEnd(Animation paramAnonymousAnimation)
      {
        paramAnonymousAnimation = X5CopyView.this;
        paramAnonymousAnimation.mInAnimation = false;
        paramAnonymousAnimation.mAnimation = null;
        paramAnonymousAnimation.mBitmap.recycle();
        X5CopyView.this.mBitmap = null;
      }
      
      public void onAnimationRepeat(Animation paramAnonymousAnimation) {}
      
      public void onAnimationStart(Animation paramAnonymousAnimation) {}
    });
    this.mInAnimation = true;
    localScaleAnimation1.reset();
    localScaleAnimation1.start();
    this.mAnimation = localScaleAnimation1;
    invalidate();
  }
  
  private boolean prepareAnimationData()
  {
    Bitmap localBitmap = this.mBitmap;
    int i;
    int j;
    if ((localBitmap == null) || (localBitmap.isRecycled()))
    {
      i = getWidth();
      j = getHeight();
      if (i <= 0) {
        break label71;
      }
      if (j <= 0) {
        return false;
      }
    }
    try
    {
      this.mBitmap = X5NativeBitmap.nativeBitmapFactory().createBitmap(i, j, Bitmap.Config.ARGB_8888);
      localBitmap = this.mBitmap;
      return localBitmap != null;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      localOutOfMemoryError.printStackTrace();
      return false;
    }
    label71:
    return false;
  }
  
  private void updateX5CopyButtonStyle(X5CopyButton paramX5CopyButton, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      paramX5CopyButton.setTextColor(SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_menu_text_color_night", "color")));
      com.tencent.smtt.webkit.ui.e.setViewBackground(paramX5CopyButton, SmttResource.getResources().getDrawable(SmttResource.loadIdentifierResource("x5_tbs_menu_press_night_background", "drawable")));
      return;
    }
    paramX5CopyButton.setTextColor(SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_menu_text_color", "color")));
    com.tencent.smtt.webkit.ui.e.setViewBackground(paramX5CopyButton, SmttResource.getResources().getDrawable(SmttResource.loadIdentifierResource("x5_tbs_menu_press_background", "drawable")));
  }
  
  public LinearLayout.LayoutParams getButtonLayoutParams(boolean paramBoolean1, boolean paramBoolean2, int paramInt)
  {
    if (paramInt == 1024) {
      paramInt = COPY_BUTTON_WIDHT * 2;
    } else if (paramInt == 2048) {
      paramInt = 8;
    } else {
      paramInt = COPY_BUTTON_WIDHT;
    }
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(paramInt, COPY_BUTTON_HEIGHT);
    if (paramBoolean1) {
      paramInt = COPY_BUTTON_MARGIN_BORDER;
    } else {
      paramInt = 0;
    }
    int i;
    if (paramBoolean2) {
      i = COPY_BUTTON_MARGIN_BORDER;
    } else {
      i = 0;
    }
    localLayoutParams.setMargins(paramInt, 0, i, 0);
    return localLayoutParams;
  }
  
  public int getCopyViewHeight()
  {
    return this.mCopyViewHeight;
  }
  
  public int getCopyViewWidth()
  {
    return this.mCopyViewWidth;
  }
  
  public boolean getSearchInMMShow()
  {
    return this.mSearchInMMShow;
  }
  
  public void init(View.OnClickListener paramOnClickListener)
  {
    initMenuContainer(paramOnClickListener);
  }
  
  public boolean resetButtonListener(View.OnClickListener paramOnClickListener)
  {
    return true;
  }
  
  public void resetState(boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((paramBoolean1) && (!this.mItemMap.containsKey(Integer.valueOf(16)))) {}
  }
  
  public void resetWidthAndMargins()
  {
    int m = getChildCount();
    if (m == 0) {
      return;
    }
    this.mCopyViewWidth = (COPY_BUTTON_WIDHT * m + COPY_BUTTON_MARGIN_BORDER * 2 + com.tencent.smtt.webkit.ui.e.X5_TBS_MENU_BACKGOUND_WITH_ARROW_SHADE_FIXED_PIX_WIDTH);
    int i = 0;
    while (i < m)
    {
      View localView = getChildAt(i);
      boolean bool2 = true;
      boolean bool1;
      if (i == 0) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      if (i != m - 1) {
        bool2 = false;
      }
      int j;
      if ((!com.tencent.smtt.webkit.e.a().t()) && (SmttServiceProxy.getInstance().isLongPressQuickSecondMenu_QQ_ThirdApp(this.mContext, this.mParentView.mWebView)))
      {
        X5CopyButton localX5CopyButton = (X5CopyButton)localView;
        int k = localX5CopyButton.mID;
        if ((SmttServiceProxy.getInstance().isLongPressMenuOpenInBrowserEnabled(this.mContext, this.mParentView.mWebView)) && (localX5CopyButton.mID == 1024))
        {
          this.mCopyViewWidth += COPY_BUTTON_WIDHT;
          Drawable localDrawable = getDrawable("x5_open_in_browser");
          localDrawable.setBounds(dip2px(5), 0, dip2px(23), dip2px(18));
          localX5CopyButton.setCompoundDrawables(localDrawable, null, null, null);
        }
        j = k;
        if (localX5CopyButton.mID == 2048)
        {
          this.mCopyViewWidth -= COPY_BUTTON_WIDHT;
          j = k;
        }
      }
      else
      {
        j = 0;
      }
      localView.setLayoutParams(getButtonLayoutParams(bool1, bool2, j));
      if (j == 2048) {
        localView.setBackgroundColor(Color.rgb(250, 250, 250));
      }
      i += 1;
    }
  }
  
  public void setAppMenuItemArrayList(ArrayList paramArrayList)
  {
    this.mAppMenuItemArrayList = paramArrayList;
  }
  
  public void setDisplayMenuType(int paramInt)
  {
    clearItems();
    if (this.mAppMenuItemArrayList != null)
    {
      paramInt = 0;
      while (paramInt < this.mAppMenuItemArrayList.size())
      {
        createItem(paramInt, ((X5MenuItem)this.mAppMenuItemArrayList.get(paramInt)).getTitle(), false, this.mListener);
        paramInt += 1;
      }
      resetWidthAndMargins();
      return;
    }
    localX5Selection2 = null;
    try
    {
      i = Process.myPid();
      Iterator localIterator = ((ActivityManager)this.mContext.getSystemService("activity")).getRunningAppProcesses().iterator();
      do
      {
        localObject = localX5Selection2;
        if (!localIterator.hasNext()) {
          break;
        }
        localObject = (ActivityManager.RunningAppProcessInfo)localIterator.next();
      } while (((ActivityManager.RunningAppProcessInfo)localObject).pid != i);
      localObject = ((ActivityManager.RunningAppProcessInfo)localObject).processName;
    }
    catch (SecurityException localSecurityException)
    {
      for (;;)
      {
        int i;
        Object localObject;
        int j;
        X5Selection localX5Selection1 = localX5Selection2;
      }
    }
    if ((localObject != null) && (((String)localObject).startsWith("com.tencent.mm:appbrand"))) {
      i = 1;
    } else {
      i = 0;
    }
    if ((paramInt & 0x1) > 0)
    {
      createItem(1, COPY, false, this.mListener);
      localObject = this.mParentView;
      if ((localObject != null) && (((X5Selection)localObject).mWebView != null) && ((this.mParentView.mWebView instanceof X5WebViewAdapter)) && (((X5WebViewAdapter)this.mParentView.mWebView).getEventType() == 0)) {
        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA008");
      }
    }
    if ((paramInt & 0x8) > 0) {
      createItem(8, CUT, false, this.mListener);
    }
    if ((paramInt & 0x10) > 0) {
      createItem(16, PASTE, false, this.mListener);
    }
    if ((paramInt & 0x20) > 0) {
      createItem(32, SELECT, false, this.mListener);
    }
    if ((paramInt & 0x40) > 0) {
      createItem(64, SELECT_ALL, false, this.mListener);
    }
    if ((paramInt & 0x4) > 0) {
      createItem(4, SHARE, false, this.mListener);
    }
    if ((paramInt & 0x80) > 0) {
      createItem(128, COLLECT, false, this.mListener);
    }
    if (((paramInt & 0x100) > 0) && (i == 0))
    {
      if ((!com.tencent.smtt.webkit.e.a().t()) && (SmttServiceProxy.getInstance().isLongPressQuickSecondMenu_QQ_ThirdApp(this.mContext, this.mParentView.mWebView))) {
        createItem(2048, "", false, this.mListener);
      }
      createItem(256, TRANSLATE, false, this.mListener);
      localObject = this.mParentView;
      if ((localObject != null) && (((X5Selection)localObject).mWebView != null) && ((this.mParentView.mWebView instanceof X5WebViewAdapter)) && (((X5WebViewAdapter)this.mParentView.mWebView).getEventType() == 0)) {
        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA014");
      }
    }
    if (((paramInt & 0x2) > 0) && (i == 0))
    {
      if (com.tencent.smtt.webkit.e.a().t())
      {
        localObject = new Bundle();
        if ((this.mParentView.mWebView != null) && (this.mParentView.mWebView.getWebViewClientExtension() != null))
        {
          localObject = this.mParentView.mWebView.getWebViewClientExtension().onMiscCallBack("supportSmartPickWord", (Bundle)localObject);
          if ((localObject != null) && ((localObject instanceof Boolean))) {
            if (((Boolean)localObject).booleanValue()) {
              this.mSupportSmartPickWord = true;
            } else {
              this.mSupportSmartPickWord = false;
            }
          }
        }
      }
      localObject = "AWTCA010";
      if (this.mSupportSmartPickWord)
      {
        SEARCH = SmttResource.getString("x5_pop_extension_menu_search_in_mm", "string");
        localObject = "BZSYS001";
        this.mSearchInMMShow = true;
      }
      if ((!com.tencent.smtt.webkit.e.a().t()) && (SmttServiceProxy.getInstance().isLongPressQuickSecondMenu_QQ_ThirdApp(this.mContext, this.mParentView.mWebView))) {
        createItem(2048, "", false, this.mListener);
      }
      createItem(2, SEARCH, false, this.mListener);
      localX5Selection2 = this.mParentView;
      if ((localX5Selection2 != null) && (localX5Selection2.mWebView != null) && ((this.mParentView.mWebView instanceof X5WebViewAdapter)) && (((X5WebViewAdapter)this.mParentView.mWebView).getEventType() == 0)) {
        SmttServiceProxy.getInstance().userBehaviorStatistics((String)localObject);
      }
    }
    localObject = this.mParentView;
    if ((localObject != null) && (((X5Selection)localObject).mWebView != null) && ((this.mParentView.mWebView instanceof X5WebViewAdapter)) && (((X5WebViewAdapter)this.mParentView.mWebView).getEventType() == 0)) {
      paramInt = 1;
    } else {
      paramInt = 0;
    }
    localObject = this.mParentView;
    if ((localObject != null) && (((X5WebViewAdapter)((X5Selection)localObject).mWebView).getHitTestResult() != null) && (((X5WebViewAdapter)this.mParentView.mWebView).getHitTestResult().getType() == 9)) {
      i = 1;
    } else {
      i = 0;
    }
    if ((!com.tencent.smtt.webkit.e.a().t()) && (SmttServiceProxy.getInstance().isLongPressMenuOpenInBrowserEnabled(this.mContext, this.mParentView.mWebView)) && (SmttServiceProxy.getInstance().isLongPressQuickSecondMenu_QQ_ThirdApp(this.mContext, this.mParentView.mWebView))) {
      j = 1;
    } else {
      j = 0;
    }
    if (paramInt != 0)
    {
      if (i == 0)
      {
        if (j != 0)
        {
          SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA004");
          createItem(2048, "", false, this.mListener);
          createItem(1024, SmttResource.getString("x5_pop_menu_open_in_browser", "string"), false, this.mListener);
          this.mShowOpenInBrowserWhenSelectionShown = true;
        }
        else
        {
          this.mShowOpenInBrowserWhenSelectionShown = false;
        }
      }
      else {
        this.mShowOpenInBrowserWhenSelectionShown = false;
      }
    }
    else if (this.mShowOpenInBrowserWhenSelectionShown)
    {
      SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA004");
      createItem(2048, "", false, this.mListener);
      createItem(1024, SmttResource.getString("x5_pop_menu_open_in_browser", "string"), false, this.mListener);
    }
    resetWidthAndMargins();
  }
  
  public void setDisplayMode(int paramInt, boolean paramBoolean)
  {
    if (paramInt == MODE_IN_INPUT.byteValue())
    {
      paramInt = 25;
      if (!paramBoolean) {
        paramInt = 89;
      }
    }
    else
    {
      int j = 1;
      int i = j;
      if (SmttServiceProxy.getInstance().isTranslateOnLongPressEnabled(this.mContext, this.mParentView.mWebView))
      {
        i = j;
        if (this.mIsTranslaterAvailable)
        {
          j = 257;
          i = j;
          if (this.mHasEnterSelectionMode)
          {
            SmttServiceProxy.getInstance().userBehaviorStatistics("CH0036");
            i = j;
          }
        }
      }
      j = i;
      if (SmttServiceProxy.getInstance().isSearchOnLongPressEnabled(this.mContext, this.mParentView.mWebView)) {
        j = i | 0x2;
      }
      if ((paramInt == MODE_IN_NORMAL_URL.byteValue()) && (SmttServiceProxy.getInstance().isOpenUrlOnLongPressEnabled(this.mContext, this.mParentView.mWebView))) {
        i = j | 0x200;
      } else {
        i = j;
      }
      paramInt = i;
      if (ContextHolder.getInstance().isTbsDevelopMode())
      {
        paramInt = i;
        if ((this.mLongPressTextExtensionMenu & 0x2) == 2) {
          paramInt = i | 0x4;
        }
        i = paramInt;
        if ((this.mLongPressTextExtensionMenu & 0x4) == 4) {
          i = paramInt | 0x80;
        }
        paramInt = i;
        if ((this.mLongPressTextExtensionMenu & 0x8) == 8) {
          paramInt = i | 0x100;
        }
      }
    }
    setDisplayMenuType(paramInt);
  }
  
  public void setHasEnterSelectionMode(boolean paramBoolean)
  {
    this.mHasEnterSelectionMode = paramBoolean;
  }
  
  public void setIsTranslaterAvailable(boolean paramBoolean)
  {
    this.mIsTranslaterAvailable = paramBoolean;
  }
  
  public void setLongPressTextExtensionMenu(int paramInt)
  {
    this.mLongPressTextExtensionMenu = paramInt;
  }
  
  public void setSearchInMMShow(boolean paramBoolean)
  {
    this.mSearchInMMShow = paramBoolean;
  }
  
  public void setSelectText(String paramString)
  {
    this.mSelectText = paramString;
  }
  
  public void showWithAnimation()
  {
    if (!prepareAnimationData()) {
      return;
    }
    outAnimation();
  }
  
  public void updateView(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool = this.mIsNight;
    int j = 1;
    int k = 0;
    int i;
    if (bool != paramBoolean1) {
      i = 1;
    } else {
      i = 0;
    }
    if (this.mIsArrowUp == paramBoolean2) {
      j = 0;
    }
    this.mIsNight = paramBoolean1;
    this.mIsArrowUp = paramBoolean2;
    if ((i != 0) || (j != 0)) {
      com.tencent.smtt.webkit.ui.e.setViewBackground(this, com.tencent.smtt.webkit.ui.e.getMenuBgWithArrowDrawable(this.mIsNight, this.mIsArrowUp));
    }
    if (i != 0)
    {
      j = getChildCount();
      i = k;
      while (i < j)
      {
        updateX5CopyButtonStyle((X5CopyButton)getChildAt(i), this.mIsNight);
        i += 1;
      }
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\menu\X5CopyView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */