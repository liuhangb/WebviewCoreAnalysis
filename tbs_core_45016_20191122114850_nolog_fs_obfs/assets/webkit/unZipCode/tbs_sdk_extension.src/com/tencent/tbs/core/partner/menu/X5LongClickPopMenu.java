package com.tencent.tbs.core.partner.menu;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.ValueCallback;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.common.threadpool.BrowserExecutorSupplier;
import com.tencent.common.threadpool.BrowserExecutorSupplier.BackgroundRunable;
import com.tencent.common.utils.FileUtils;
import com.tencent.common.utils.SignatureUtil;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult.ImageData;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.export.internal.interfaces.IX5WebView.TRANSLATE_STATE;
import com.tencent.smtt.os.SMTTAdaptation;
import com.tencent.smtt.util.f;
import com.tencent.smtt.util.n;
import com.tencent.smtt.webkit.ContextHolder;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.WebViewProviderExtension;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.tbs.core.partner.clipboard.PartnerClipboard;
import com.tencent.tbs.core.partner.suspensionview.SuSpensionViewController;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProvider;
import java.io.File;
import java.net.URLDecoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class X5LongClickPopMenu
  extends Dialog
{
  private static final int BTN_ID_PASTE = 2;
  private static final int BTN_ID_SELECT = 4;
  private static final int BTN_ID_SELECT_ALL = 8;
  private static final int CONTROL_EDITTEXT_POPUP_VERTICALLY_OFFSET;
  private static final String ENTER_FEEDBACK;
  private static final String ENTER_SELECTION;
  private static final String EXPLORER;
  private static final String IMAGE_QUERY;
  private static final int LAN_DO_REVERT = 1;
  private static final int LAN_DO_TRANSLATE = 0;
  private static final int LAN_INIT = -1;
  private static final int MENU_BUTTON_HEIGHT;
  private static final int MENU_BUTTON_MARGIN_BORDER;
  private static final int MENU_BUTTON_MARGIN_LEFT_RIGHT;
  private static final int MENU_BUTTON_WIDTH;
  private static final int MENU_HORIZONTAL_BUTTON_MARGIN_BORDER = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_menu_button_margin_border", "dimen"));
  private static final int MENU_HORIZONTAL_BUTTON_WIDTH;
  private static final String NIGHT_MODE;
  private static final String OPEN_IN_BROWSER;
  private static final String PAGE_REVERT;
  private static final String PAGE_TRANSLATE;
  private static final String PASTE = SmttResource.getString("x5_pop_menu_paste", "string");
  private static final String POS_ID_OPEN_QB_VIA_LONG_PRESS_ON_BLANK = "8";
  private static final String POS_ID_OPEN_QB_VIA_LONG_PRESS_ON_BLANK_WITH_NIGHTMODE = "13";
  private static final String QB_DOWNLOAD_FILENAME = "qqbrowser_10825.apk";
  private static final String QB_DOWNLOAD_URL_DEFAULT = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10825";
  private static final String QB_DOWNLOAD_URL_FORMM = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10947";
  private static final String QB_DOWNLOAD_URL_FORQQ = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10948";
  private static final String QB_DOWNLOAD_URL_FORQZONE = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10949";
  private static final String QB_FULLSCREEN_MODE_KEY = "startFullscreenMode=true";
  private static final int QB_NIGHTMODE_ENABLE_VERSION_CODE = 611710;
  private static final String QB_NIGHTMODE_MODE_KEY = "startNightMode=true";
  private static final String QB_PACKAGE_NAME = "com.tencent.mtt";
  private static final String REFRESH;
  private static final String SAVE_PAGE_DEFAULT_NAME = "(1)";
  private static final String SAVE_PAGE_EXT = ".mht";
  private static final String SAVE_WEBPAGE;
  private static final String SCAN_IMAGE;
  private static final String SELECT = SmttResource.getString("x5_pop_menu_choose_text", "string");
  private static final String SELECT_ALL = SmttResource.getString("x5_pop_menu_select_alltext", "string");
  private static final String STAT_KEY_COPY_LINK_AFTER_LONG_PRESS_ON_BLANK = "BDTT4";
  private static final String STAT_KEY_OPEN_IN_BROWSER_AFTER_LONG_PRESS_ON_BLANK = "BDTT5";
  private static final String STAT_KEY_OPEN_IN_QB_AFTER_LONG_PRESS_ON_BLANK = "BDTT6";
  private static final String TAG = "X5LongClickPopMenu";
  private static final String TRANSLATING;
  private LinearLayout mContentLinearLayout;
  private Context mContext;
  private IX5WebViewBase.HitTestResult mHitTestResult;
  private LinearLayout mHorMenuContentLinearLayout;
  private boolean mImageTypeShowPopmenu = false;
  private boolean mIsNight;
  private boolean mIsVerticalMenu = true;
  private Bitmap mLastHitBitmap = null;
  private Point mLongClickPoint;
  private boolean mNormalTextSelect = false;
  private int mPopMenuHeight = 0;
  private int mPopMenuWidth = 0;
  private boolean mSelectable;
  private int mStatusBarHeight = 0;
  private Context mThemeContext;
  private IX5WebView mWebView;
  
  static
  {
    NIGHT_MODE = SmttResource.getString("x5_pop_menu_night_mode", "string");
    ENTER_SELECTION = SmttResource.getString("x5_pop_menu_enter_selection", "string");
    ENTER_FEEDBACK = SmttResource.getString("x5_pop_menu_feedback", "string");
    OPEN_IN_BROWSER = SmttResource.getString("x5_pop_menu_open_in_browser", "string");
    IMAGE_QUERY = SmttResource.getString("x5_pop_menu_image_query", "string");
    SAVE_WEBPAGE = SmttResource.getString("x5_pop_menu_save_webpage", "string");
    REFRESH = SmttResource.getString("x5_pop_menu_refresh", "string");
    EXPLORER = SmttResource.getString("x5_pop_menu_explorer", "string");
    SCAN_IMAGE = SmttResource.getString("x5_pop_menu_scanImage", "string");
    PAGE_TRANSLATE = SmttResource.getString("x5_pop_menu_page_translate", "string");
    PAGE_REVERT = SmttResource.getString("x5_pop_menu_page_revert", "string");
    TRANSLATING = SmttResource.getString("x5_toast_translating_page_pls_wait", "string");
    MENU_BUTTON_MARGIN_BORDER = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_menu_button_margin_border", "dimen"));
    MENU_BUTTON_WIDTH = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_menu_button_width", "dimen"));
    MENU_BUTTON_HEIGHT = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_menu_button_height", "dimen"));
    MENU_BUTTON_MARGIN_LEFT_RIGHT = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_menu_button_margin_left_right", "dimen"));
    CONTROL_EDITTEXT_POPUP_VERTICALLY_OFFSET = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_control_edittext_popup_vertically_offset", "dimen"));
    MENU_HORIZONTAL_BUTTON_WIDTH = SmttResource.getDimensionPixelSize(SmttResource.loadIdentifierResource("x5_menu_copy_button_width", "dimen"));
  }
  
  public X5LongClickPopMenu(Context paramContext1, IX5WebView paramIX5WebView, IX5WebViewBase.HitTestResult paramHitTestResult, Context paramContext2, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramContext2);
    requestWindowFeature(1);
    this.mContext = paramContext1;
    this.mThemeContext = paramContext2;
    this.mHitTestResult = paramHitTestResult;
    this.mWebView = paramIX5WebView;
    this.mIsNight = paramBoolean1;
    this.mSelectable = paramBoolean2;
    paramContext1 = this.mWebView;
    if (paramContext1 != null) {
      this.mStatusBarHeight = n.a(paramContext1.getView());
    }
    initView();
    setContentView(this.mContentLinearLayout);
    getWindow().addFlags(131072);
  }
  
  private LinearLayout createButtonWrapper(String paramString, View.OnClickListener paramOnClickListener)
  {
    return createButtonWrapper(paramString, paramOnClickListener, null);
  }
  
  private LinearLayout createButtonWrapper(String paramString1, View.OnClickListener paramOnClickListener, String paramString2)
  {
    if ((Build.VERSION.SDK_INT >= 23) && (Build.MANUFACTURER.toLowerCase().contains("huawei"))) {
      localObject = this.mContext;
    } else {
      localObject = this.mThemeContext;
    }
    LinearLayout localLinearLayout = new LinearLayout((Context)localObject);
    if (this.mIsNight) {
      com.tencent.smtt.webkit.ui.e.setViewBackground(localLinearLayout, SmttResource.getResources().getDrawable(SmttResource.loadIdentifierResource("x5_tbs_menu_press_night_background", "drawable")));
    } else {
      com.tencent.smtt.webkit.ui.e.setViewBackground(localLinearLayout, SmttResource.getResources().getDrawable(SmttResource.loadIdentifierResource("x5_tbs_menu_press_background", "drawable")));
    }
    localLinearLayout.setOnClickListener(paramOnClickListener);
    localLinearLayout.setFocusable(true);
    localLinearLayout.setClickable(true);
    Object localObject = new TextView((Context)localObject);
    if (this.mIsVerticalMenu)
    {
      if ((paramString2 != null) && (paramString2.startsWith("com.tencent.mm:appbrand")))
      {
        paramOnClickListener = new LinearLayout.LayoutParams(-1, -2);
        paramOnClickListener.setMargins(0, 0, 0, 0);
        ((TextView)localObject).setGravity(17);
        paramOnClickListener.gravity = 16;
      }
      else
      {
        paramOnClickListener = new LinearLayout.LayoutParams(MENU_BUTTON_WIDTH - MENU_BUTTON_MARGIN_LEFT_RIGHT * 2, -2);
        int i = MENU_BUTTON_MARGIN_LEFT_RIGHT;
        paramOnClickListener.setMargins(i, 0, i, 0);
        ((TextView)localObject).setGravity(3);
        paramOnClickListener.gravity = 16;
      }
    }
    else
    {
      paramOnClickListener = new LinearLayout.LayoutParams(MENU_HORIZONTAL_BUTTON_WIDTH, MENU_BUTTON_HEIGHT);
      ((TextView)localObject).setGravity(17);
      paramOnClickListener.gravity = 17;
    }
    ((TextView)localObject).setLayoutParams(paramOnClickListener);
    ((TextView)localObject).setText(paramString1);
    if (this.mIsNight) {
      ((TextView)localObject).setTextColor(SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_menu_text_color_night", "color")));
    } else {
      ((TextView)localObject).setTextColor(SmttResource.getResources().getColor(SmttResource.loadIdentifierResource("x5_menu_text_color", "color")));
    }
    ((TextView)localObject).setTextSize(16.0F);
    com.tencent.smtt.webkit.ui.e.setViewBackground((View)localObject, new ColorDrawable(0));
    localLinearLayout.addView((View)localObject);
    return localLinearLayout;
  }
  
  private void enterSelectionMode(boolean paramBoolean)
  {
    this.mWebView.enterSelectionMode(paramBoolean);
  }
  
  private String getShareUrlFromWeChat()
  {
    try
    {
      String str = (String)this.mWebView.getWebViewClientExtension().onMiscCallBack("getShareUrl", new Bundle());
      return str;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
    return null;
  }
  
  private String getUrl(IX5WebViewBase.HitTestResult paramHitTestResult)
  {
    int i = paramHitTestResult.getType();
    if (i != 5)
    {
      switch (i)
      {
      default: 
        return null;
      }
      return paramHitTestResult.getExtra();
    }
    paramHitTestResult = paramHitTestResult.getData();
    if ((paramHitTestResult instanceof IX5WebViewBase.HitTestResult.ImageData)) {
      return ((IX5WebViewBase.HitTestResult.ImageData)paramHitTestResult).mPicUrl;
    }
    return null;
  }
  
  private void initView()
  {
    this.mContentLinearLayout = new LinearLayout(this.mThemeContext);
    this.mContentLinearLayout.setOrientation(1);
    int k = 0;
    this.mImageTypeShowPopmenu = false;
    Object localObject1;
    if (this.mHitTestResult.getType() == 9)
    {
      this.mIsVerticalMenu = false;
      getWindow().setBackgroundDrawable(new ColorDrawable(0));
      this.mHorMenuContentLinearLayout = new LinearLayout(this.mThemeContext);
      this.mHorMenuContentLinearLayout.setOrientation(0);
      this.mContentLinearLayout.addView(this.mHorMenuContentLinearLayout, new LinearLayout.LayoutParams(-2, -2));
      com.tencent.smtt.webkit.ui.e.setViewBackground(this.mHorMenuContentLinearLayout, com.tencent.smtt.webkit.ui.e.getMenuBgWithArrowDrawable(this.mIsNight, true));
      localObject1 = this.mWebView;
      i = k;
      if (localObject1 != null)
      {
        localObject1 = ((IX5WebView)localObject1).getFocusCandidateText();
        i = k;
        if (!this.mWebView.inputNodeIsPasswordType())
        {
          i = k;
          if (localObject1 != null)
          {
            i = k;
            if (!TextUtils.isEmpty((CharSequence)localObject1)) {
              i = 12;
            }
          }
        }
      }
      k = i;
      if (!TextUtils.isEmpty(PartnerClipboard.getText(this.mContext))) {
        k = i | 0x2;
      }
      if (k == 0) {
        return;
      }
      if ((k & 0x2) > 0)
      {
        localObject1 = createButtonWrapper(PASTE, new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            paramAnonymousView = X5LongClickPopMenu.this;
            paramAnonymousView.pasteText(PartnerClipboard.getText(paramAnonymousView.mContext));
            X5LongClickPopMenu.this.dismiss();
          }
        });
        this.mHorMenuContentLinearLayout.addView((View)localObject1);
      }
      if ((k & 0x4) > 0)
      {
        localObject1 = createButtonWrapper(SELECT, new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            X5LongClickPopMenu.this.enterSelectionMode(false);
            X5LongClickPopMenu.this.dismiss();
          }
        });
        this.mHorMenuContentLinearLayout.addView((View)localObject1);
      }
      if ((k & 0x8) > 0)
      {
        localObject1 = createButtonWrapper(SELECT_ALL, new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            X5LongClickPopMenu.this.enterSelectionMode(true);
            X5LongClickPopMenu.this.dismiss();
          }
        });
        this.mHorMenuContentLinearLayout.addView((View)localObject1);
      }
      resetWidthAndMargins();
      return;
    }
    this.mIsVerticalMenu = true;
    getWindow().setBackgroundDrawable(com.tencent.smtt.webkit.ui.e.getMenuBgWithoutArrowDrawable(this.mIsNight));
    this.mContentLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    final String str;
    final Point localPoint1;
    final Point localPoint2;
    if ((this.mHitTestResult.getType() != 6) && (this.mHitTestResult.getType() != 5) && (this.mHitTestResult.getType() != 8))
    {
      localObject1 = this.mWebView;
      if (((localObject1 instanceof X5WebViewAdapter)) && (((X5WebViewAdapter)localObject1).getMenuCleared()))
      {
        enterSelectionMode(false);
      }
      else
      {
        ContextHolder.getInstance().getApplicationContext();
        str = this.mWebView.getUrl();
        localPoint1 = new Point(this.mWebView.getScrollX(), this.mWebView.getScrollY());
        localPoint2 = new Point(this.mWebView.getContentWidth(), this.mWebView.getContentHeight());
        if ((str != null) && (str.length() != 0)) {
          localObject3 = null;
        }
      }
    }
    try
    {
      i = Process.myPid();
      localObject4 = ((ActivityManager)this.mContext.getSystemService("activity")).getRunningAppProcesses().iterator();
      do
      {
        localObject1 = localObject3;
        if (!((Iterator)localObject4).hasNext()) {
          break;
        }
        localObject1 = (ActivityManager.RunningAppProcessInfo)((Iterator)localObject4).next();
      } while (((ActivityManager.RunningAppProcessInfo)localObject1).pid != i);
      localObject1 = ((ActivityManager.RunningAppProcessInfo)localObject1).processName;
    }
    catch (SecurityException localSecurityException)
    {
      for (;;)
      {
        Object localObject4;
        int n;
        int i1;
        boolean bool4;
        localObject2 = localObject3;
      }
    }
    if ((localObject1 != null) && (((String)localObject1).startsWith("com.tencent.mm:appbrand"))) {
      n = 1;
    } else {
      n = 0;
    }
    if ((SmttServiceProxy.getInstance().isLongPressMenuSelectCopyEnabled(this.mContext, this.mWebView)) && (this.mSelectable)) {
      i1 = 1;
    } else {
      i1 = 0;
    }
    bool4 = SmttServiceProxy.getInstance().isLongPressMenuRefreshEnabled(this.mContext, this.mWebView);
    try
    {
      localObject3 = Uri.parse(this.mWebView.getUrl());
      if ((localObject3 != null) && ("file".equalsIgnoreCase(((Uri)localObject3).getScheme())))
      {
        bool2 = "1".equalsIgnoreCase(((Uri)localObject3).getQueryParameter("from_file_reader"));
        if (bool2)
        {
          k = 1;
          break label714;
        }
      }
      k = 0;
    }
    catch (Throwable localThrowable)
    {
      boolean bool2;
      boolean bool3;
      for (;;) {}
    }
    k = 0;
    label714:
    localObject3 = getShareUrlFromWeChat();
    if ((k == 0) && (str != null) && (!str.equalsIgnoreCase("about:blank")) && (n == 0))
    {
      bool3 = SmttServiceProxy.getInstance().isLongPressMenuOpenInBrowserEnabled(this.mContext, this.mWebView);
      i = SmttServiceProxy.getInstance().saveWebPageInLongPressthreeswitchLevel(this.mContext);
      if ((i != 0) && (i != 1)) {}
    }
    try
    {
      bool2 = isQBOKForSaveOfflinePage(this.mContext);
    }
    catch (Exception localException)
    {
      int i3;
      boolean bool1;
      for (;;) {}
    }
    bool2 = false;
    break label817;
    if (i == 2) {
      bool2 = true;
    } else {
      bool2 = false;
    }
    label817:
    if (("com.tencent.mm".equals(this.mContext.getApplicationInfo().packageName)) && (TextUtils.isEmpty((CharSequence)localObject3)))
    {
      SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA018");
      bool2 = false;
      i3 = 0;
    }
    else
    {
      i3 = bool2;
      bool2 = bool3;
    }
    if (n != 0) {
      i3 = 0;
    }
    int i = SmttServiceProxy.getInstance().nightModeInLongPressthreeswitchLevel(this.mContext);
    if (SmttServiceProxy.getInstance().isDebugMiniQBEnv()) {
      i = 1;
    }
    if (i != 0)
    {
      if (i == 1)
      {
        bool1 = SmttServiceProxy.getInstance().isMiniQBDisabled() ^ true;
        break label978;
      }
      if (bool1 != true) {}
    }
    try
    {
      localObject4 = this.mContext.getPackageManager().getPackageInfo("com.tencent.mtt", 0);
      if (localObject4 != null)
      {
        j = ((PackageInfo)localObject4).versionCode;
        if (j >= 611710)
        {
          j = 1;
          break label978;
        }
      }
      j = 0;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      final int j;
      int m;
      int i4;
      int i2;
      Object localObject2;
      for (;;) {}
    }
    j = 0;
    break label978;
    j = 0;
    label978:
    if (n != 0) {
      j = 0;
    }
    bool3 = bool2;
    m = j;
    i4 = i3;
    if (SmttServiceProxy.getInstance().isTranslateOnLongPressEnabled(this.mContext, this.mWebView))
    {
      localObject4 = this.mWebView;
      bool3 = bool2;
      m = j;
      i4 = i3;
      if ((localObject4 instanceof X5WebViewAdapter))
      {
        localObject4 = ((X5WebViewAdapter)localObject4).getWebViewProvider().getExtension().getTranslateState(str);
        ContextHolder.getInstance().getTbsCoreVersion();
        if (localObject4 == IX5WebView.TRANSLATE_STATE.TRANSLATED_PAGE)
        {
          SmttServiceProxy.getInstance().userBehaviorStatistics("CH0040");
          i2 = 1;
          m = j;
          j = i2;
          break label1149;
        }
        bool3 = bool2;
        m = j;
        i4 = i3;
        if (localObject4 == IX5WebView.TRANSLATE_STATE.ENGLISH_PAGE)
        {
          SmttServiceProxy.getInstance().userBehaviorStatistics("CH0038");
          i2 = 0;
          m = j;
          j = i2;
          break label1149;
          bool3 = false;
          m = 0;
          i4 = 0;
        }
      }
    }
    j = -1;
    i3 = i4;
    bool2 = bool3;
    label1149:
    bool3 = SmttServiceProxy.getInstance().isLongPressImageScanEnabled(this.mContext, this.mWebView);
    if ((localObject1 != null) && (((String)localObject1).contains("com.tencent.mm")))
    {
      localObject1 = this.mWebView;
      if ((localObject1 instanceof X5WebViewAdapter)) {
        ((X5WebViewAdapter)localObject1).setQuickSelectCopy(true);
      }
      SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA016");
      enterSelectionMode(false);
      return;
    }
    if ((SmttServiceProxy.getInstance().isLongPressQuickSelectCopyEnabled(this.mContext, this.mWebView)) && (i1 != 0) && (!bool4) && (!bool2) && (i3 == 0) && (m == 0) && (!bool3))
    {
      localObject1 = this.mWebView;
      if ((localObject1 instanceof X5WebViewAdapter)) {
        ((X5WebViewAdapter)localObject1).setQuickSelectCopy(true);
      }
      SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA016");
      enterSelectionMode(false);
      return;
    }
    if (i1 != 0)
    {
      localObject1 = createButtonWrapper(ENTER_SELECTION, new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          if ((X5LongClickPopMenu.this.mWebView instanceof X5WebViewAdapter)) {
            ((X5WebViewAdapter)X5LongClickPopMenu.this.mWebView).setQuickSelectCopy(false);
          }
          SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA003");
          X5LongClickPopMenu.this.enterSelectionMode(false);
          X5LongClickPopMenu.this.dismiss();
        }
      }, (String)localObject1);
      this.mContentLinearLayout.addView((View)localObject1);
      SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA002");
    }
    if (bool4)
    {
      localObject1 = createButtonWrapper(REFRESH, new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          SmttServiceProxy.getInstance().userBehaviorStatistics("refresh2");
          X5LongClickPopMenu.this.mWebView.reload();
          X5LongClickPopMenu.this.dismiss();
        }
      });
      this.mContentLinearLayout.addView((View)localObject1);
      SmttServiceProxy.getInstance().userBehaviorStatistics("refresh1");
    }
    if ((k == 0) && (str != null) && (!str.equalsIgnoreCase("about:blank")) && (n == 0))
    {
      if (bool2)
      {
        localObject1 = createButtonWrapper(OPEN_IN_BROWSER, new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA005");
            SmttServiceProxy.getInstance().userBehaviorStatistics("BZCA005");
            paramAnonymousView = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            try
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append(".");
              ((StringBuilder)localObject1).append(X5LongClickPopMenu.this.mContext.getPackageName());
              localObject1 = new File(paramAnonymousView, ((StringBuilder)localObject1).toString());
              if (!((File)localObject1).exists())
              {
                boolean bool = ((File)localObject1).mkdir();
                if (!bool) {}
              }
              else
              {
                paramAnonymousView = (View)localObject1;
              }
              localView = paramAnonymousView;
            }
            catch (Throwable localThrowable)
            {
              Object localObject1;
              View localView;
              String str;
              Object localObject2;
              for (;;) {}
            }
            localView = paramAnonymousView;
            SmttServiceProxy.getInstance().userBehaviorStatistics("BDTT5");
            localObject1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10825";
            str = X5LongClickPopMenu.this.mContext.getApplicationInfo().packageName;
            localObject2 = str;
            if ("com.tencent.mobileqq".equals(str))
            {
              localObject1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10948";
              paramAnonymousView = (View)localObject2;
            }
            else if ("com.tencent.mm".equals(str))
            {
              localObject2 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10947";
              localObject1 = localObject3;
              paramAnonymousView = (View)localObject1;
            }
            try
            {
              if (((String)localObject1).startsWith("http://rd.wechat.com/redirect/confirm"))
              {
                paramAnonymousView = (View)localObject1;
                localObject1 = URLDecoder.decode((String)localObject1, "UTF-8");
                paramAnonymousView = (View)localObject1;
                localObject1 = Uri.parse((String)localObject1).getQueryParameter("url");
                paramAnonymousView = (View)localObject1;
                localObject1 = localObject2;
              }
              else
              {
                paramAnonymousView = (View)localObject1;
                localObject1 = localObject2;
              }
            }
            catch (Exception localException)
            {
              for (;;) {}
            }
            localObject1 = localObject2;
            break label239;
            paramAnonymousView = (View)localObject2;
            if ("com.qzone".equals(str))
            {
              localObject1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10949";
              paramAnonymousView = (View)localObject2;
            }
            label239:
            if ((!SmttServiceProxy.getInstance().isDebugMiniQBEnv()) && (f.a(X5LongClickPopMenu.this.mContext, paramAnonymousView, "BDTT6", "8", localPoint1, localPoint2)))
            {
              X5LongClickPopMenu.this.dismiss();
              return;
            }
            if ((SmttServiceProxy.getInstance().isMidPageQbOpenBrowserOnLongPressEnabled(X5LongClickPopMenu.this.mContext)) && (f.a(X5LongClickPopMenu.this.mContext, paramAnonymousView, "AATQB42", 3, localPoint1, localPoint2)))
            {
              X5LongClickPopMenu.this.dismiss();
              return;
            }
            localObject2 = new Intent("android.intent.action.VIEW");
            ((Intent)localObject2).addCategory("android.intent.category.BROWSABLE");
            ((Intent)localObject2).setData(Uri.parse(paramAnonymousView));
            paramAnonymousView = SmttResource.getBrowserListIcons(X5LongClickPopMenu.this.mContext, (Intent)localObject2, (X5WebViewAdapter)X5LongClickPopMenu.this.mWebView);
            f.a("", X5LongClickPopMenu.this.mContext, (Intent)localObject2, localView, SmttResource.getString("x5_tbs_wechat_activity_picker_open_browser_title", "string"), (String)localObject1, "qqbrowser_10825.apk", "BDTT6", null, "8", paramAnonymousView);
            X5LongClickPopMenu.this.dismiss();
          }
        });
        this.mContentLinearLayout.addView((View)localObject1);
        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA004");
        SmttServiceProxy.getInstance().userBehaviorStatistics("BZCA004");
      }
      k = this.mHitTestResult.getType();
      localObject1 = this.mHitTestResult;
      if (k != 5)
      {
        k = ((IX5WebViewBase.HitTestResult)localObject1).getType();
        localObject1 = this.mHitTestResult;
        if ((k != 8) && (com.tencent.smtt.webkit.e.a().g()) && (SmttServiceProxy.getInstance().LongPressMenuImageQueryEnabled(this.mContext, this.mWebView)))
        {
          localObject1 = this.mWebView;
          if ((localObject1 instanceof X5WebViewAdapter))
          {
            this.mLastHitBitmap = ((X5WebViewAdapter)localObject1).lastHitTestBmp();
            if (this.mLastHitBitmap != null)
            {
              localObject1 = createButtonWrapper(IMAGE_QUERY, new View.OnClickListener()
              {
                public void onClick(View paramAnonymousView)
                {
                  SmttServiceProxy.getInstance().userBehaviorStatistics("COREIMAGEQUERYCLICK");
                  BrowserExecutorSupplier.postForIoTasks(new BrowserExecutorSupplier.BackgroundRunable()
                  {
                    public void doRun()
                    {
                      ((X5WebViewAdapter)X5LongClickPopMenu.this.mWebView).imageQueryFromX5Core(X5LongClickPopMenu.this.mLastHitBitmap);
                    }
                  });
                  X5LongClickPopMenu.this.dismiss();
                }
              });
              this.mContentLinearLayout.addView((View)localObject1);
              SmttServiceProxy.getInstance().userBehaviorStatistics("COREIMAGEQUERYSHOW");
            }
          }
        }
      }
      if (i3 != 0)
      {
        localObject1 = createButtonWrapper(SAVE_WEBPAGE, new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            SmttServiceProxy.getInstance().userBehaviorStatistics("PAGESAVE2");
            if ("com.tencent.mm".equals(X5LongClickPopMenu.this.mContext.getApplicationInfo().packageName))
            {
              X5LongClickPopMenu.this.savePageToDisk(localObject3);
            }
            else
            {
              paramAnonymousView = X5LongClickPopMenu.this;
              paramAnonymousView.savePageToDisk(paramAnonymousView.mWebView.getUrl());
            }
            X5LongClickPopMenu.this.dismiss();
          }
        });
        this.mContentLinearLayout.addView((View)localObject1);
        SmttServiceProxy.getInstance().userBehaviorStatistics("PAGESAVE1");
      }
      if ((SmttServiceProxy.getInstance().isLongPressMenuScreenShotEnabled(this.mContext)) && (Build.VERSION.SDK_INT >= 12))
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC001");
        localObject1 = createButtonWrapper(SmttResource.getString("x5_pop_menu_snapshot_full", "string"), new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            SmttServiceProxy.getInstance().userBehaviorStatistics("BZBC002");
            new X5SnapshotMenu(X5LongClickPopMenu.this.mContext, X5LongClickPopMenu.this.mWebView).enterSnapshotMode();
            X5LongClickPopMenu.this.dismiss();
          }
        });
        this.mContentLinearLayout.addView((View)localObject1);
        SmttServiceProxy.getInstance().userBehaviorStatistics("PAGESAVE1");
      }
      if (j != -1)
      {
        if (j == 0) {
          localObject1 = PAGE_TRANSLATE;
        } else {
          localObject1 = PAGE_REVERT;
        }
        localObject1 = createButtonWrapper((String)localObject1, new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            ((X5WebViewAdapter)X5LongClickPopMenu.this.mWebView).getWebViewProvider().getExtension().doTranslateAction(j);
            X5LongClickPopMenu.this.dismiss();
            ContextHolder.getInstance().getTbsCoreVersion();
            if (j == 0)
            {
              Toast.makeText(X5LongClickPopMenu.this.mContext, X5LongClickPopMenu.TRANSLATING, 1).show();
              SmttServiceProxy.getInstance().userBehaviorStatistics("CH0039");
              return;
            }
            SmttServiceProxy.getInstance().userBehaviorStatistics("CH0041");
          }
        });
        this.mContentLinearLayout.addView((View)localObject1);
      }
      if (m != 0)
      {
        localObject1 = createButtonWrapper(NIGHT_MODE, new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA007");
            if (SmttServiceProxy.getInstance().isMidPageQbNightModeOnLongPressEnabled(X5LongClickPopMenu.this.mContext))
            {
              paramAnonymousView = X5LongClickPopMenu.this;
              paramAnonymousView.loadUrlUseSimpleQB(paramAnonymousView.mWebView.getUrl());
            }
            else
            {
              paramAnonymousView = X5LongClickPopMenu.this;
              if (paramAnonymousView.loadUrlUseQB(paramAnonymousView.mWebView.getUrl())) {
                return;
              }
              paramAnonymousView = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            }
            try
            {
              localObject1 = new StringBuilder();
              ((StringBuilder)localObject1).append(".");
              ((StringBuilder)localObject1).append(X5LongClickPopMenu.this.mContext.getPackageName());
              localObject1 = new File(paramAnonymousView, ((StringBuilder)localObject1).toString());
              if (!((File)localObject1).exists())
              {
                boolean bool = ((File)localObject1).mkdir();
                if (!bool) {}
              }
              else
              {
                paramAnonymousView = (View)localObject1;
              }
              localObject1 = paramAnonymousView;
            }
            catch (Throwable localThrowable)
            {
              Object localObject1;
              Object localObject2;
              for (;;) {}
            }
            localObject1 = paramAnonymousView;
            paramAnonymousView = X5LongClickPopMenu.this.mContext.getApplicationInfo().packageName;
            localObject2 = X5LongClickPopMenu.this.mWebView.getUrl();
            if ("com.tencent.mobileqq".equals(paramAnonymousView))
            {
              paramAnonymousView = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10948";
            }
            else if ("com.tencent.mm".equals(paramAnonymousView))
            {
              localObject2 = localObject3;
              paramAnonymousView = (View)localObject2;
            }
            try
            {
              if (((String)localObject2).startsWith("http://rd.wechat.com/redirect/confirm"))
              {
                paramAnonymousView = (View)localObject2;
                localObject2 = URLDecoder.decode((String)localObject2, "UTF-8");
                paramAnonymousView = (View)localObject2;
                localObject2 = Uri.parse((String)localObject2).getQueryParameter("url");
                paramAnonymousView = (View)localObject2;
              }
              else
              {
                paramAnonymousView = (View)localObject2;
              }
            }
            catch (Exception localException)
            {
              Object localObject3;
              for (;;) {}
            }
            localObject3 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10947";
            localObject2 = paramAnonymousView;
            paramAnonymousView = (View)localObject3;
            break label286;
            if ("com.qzone".equals(paramAnonymousView)) {
              paramAnonymousView = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10949";
            } else {
              paramAnonymousView = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10825";
            }
            label286:
            localObject3 = new Intent("android.intent.action.VIEW");
            ((Intent)localObject3).addCategory("android.intent.category.BROWSABLE");
            ((Intent)localObject3).setData(Uri.parse((String)localObject2));
            localObject2 = SmttResource.getBrowserListIcons(X5LongClickPopMenu.this.mContext, (Intent)localObject3, (X5WebViewAdapter)X5LongClickPopMenu.this.mWebView);
            f.a("", X5LongClickPopMenu.this.mContext, (Intent)localObject3, (File)localObject1, SmttResource.getString("x5_tbs_wechat_activity_picker_open_browser_title", "string"), paramAnonymousView, "qqbrowser_10825.apk", "BDTT6", null, "8", (Map)localObject2);
            X5LongClickPopMenu.this.dismiss();
          }
        });
        this.mContentLinearLayout.addView((View)localObject1);
        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA006");
      }
    }
    if ((bool3) && ((this.mWebView instanceof X5WebViewAdapter)))
    {
      procImageScanMenu();
      break label1830;
      this.mNormalTextSelect = true;
      return;
    }
    label1830:
    resetWidthAndMargins();
  }
  
  private boolean isQBOKForSaveOfflinePage(Context paramContext)
  {
    paramContext = SignatureUtil.getInstalledPKGInfo("com.tencent.mtt", paramContext, 0);
    int j;
    int i;
    if (paramContext == null)
    {
      j = 1;
      i = 0;
    }
    else if (Integer.parseInt(paramContext.versionName.split("\\.")[0]) < 7)
    {
      j = 0;
      i = 1;
    }
    else
    {
      j = 0;
      i = 0;
    }
    return (j == 0) && (i == 0);
  }
  
  private boolean loadUrlUseQB(String paramString)
  {
    Object localObject = this.mContext;
    if (localObject != null)
    {
      localObject = ((Context)localObject).getPackageName();
      return SmttServiceProxy.getInstance().openUrlInQQBrowserWithReport(this.mContext, paramString, "TBS", (String)localObject, "13", ",startNightMode=true,startFullscreenMode=true");
    }
    return false;
  }
  
  private void loadUrlUseSimpleQB(String paramString)
  {
    if ((!SmttServiceProxy.getInstance().isDebugMiniQBEnv()) && (loadUrlUseQB(paramString))) {
      return;
    }
    if (SmttServiceProxy.getInstance().startQBWebWithNightFullscreenMode(this.mContext, paramString, 4) == 0) {
      SmttServiceProxy.getInstance().userBehaviorStatistics("APTBS13");
    }
  }
  
  private void pasteText(CharSequence paramCharSequence)
  {
    this.mWebView.pasteText(paramCharSequence);
  }
  
  private void procImageScanMenu()
  {
    Object localObject1 = this.mWebView.getUrl();
    getUrl(this.mHitTestResult);
    if (SmttServiceProxy.getInstance().isInMiniProgram(this.mWebView)) {
      return;
    }
    int j;
    if (SmttServiceProxy.getInstance().isLongPressImageScanEnabled(this.mContext, this.mWebView))
    {
      if ((com.tencent.smtt.webkit.e.a().g()) && (!((X5WebViewAdapter)this.mWebView).getWebViewProvider().getExtension().getSettingsExtension().getImageScanEnable())) {
        j = 0;
      } else {
        j = 1;
      }
      Object localObject2 = SmttServiceProxy.getInstance().getMiniQbVersionName();
      i = j;
      int k;
      int m;
      if (j != 0) {
        if (localObject2 != null)
        {
          localObject2 = ((String)localObject2).split("\\.");
          i = j;
          if (localObject2.length >= 2)
          {
            k = Integer.parseInt(localObject2[0]);
            m = Integer.parseInt(localObject2[1]);
            if (k >= 2)
            {
              i = j;
              if (k == 2)
              {
                i = j;
                if (m >= 3) {}
              }
            }
            else
            {
              i = 0;
            }
          }
        }
        else
        {
          i = 0;
        }
      }
      j = i;
      if (i != 0)
      {
        j = i;
        if (this.mHitTestResult.getType() == 5) {
          try
          {
            localObject2 = this.mHitTestResult.getData();
            j = i;
            if (!(localObject2 instanceof IX5WebViewBase.HitTestResult.ImageData)) {
              break label271;
            }
            k = ((IX5WebViewBase.HitTestResult.ImageData)localObject2).mImgWidth;
            j = ((IX5WebViewBase.HitTestResult.ImageData)localObject2).mImgHeight;
            m = this.mWebView.getContentWidth();
            j = i;
            if (k * 2 >= m) {
              break label271;
            }
            j = 0;
          }
          catch (NoSuchFieldError localNoSuchFieldError)
          {
            localNoSuchFieldError.printStackTrace();
            j = i;
          }
        }
      }
    }
    else
    {
      j = 0;
    }
    label271:
    int i = j;
    if (j != 0)
    {
      i = j;
      if (SmttServiceProxy.getInstance().isImageBrowserDisableInPage((String)localObject1)) {
        i = 0;
      }
    }
    if ((i != 0) && ((this.mWebView instanceof X5WebViewAdapter)))
    {
      this.mImageTypeShowPopmenu = true;
      localObject1 = createButtonWrapper(SCAN_IMAGE, new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          ((X5WebViewAdapter)X5LongClickPopMenu.this.mWebView).onShowImageBrowser(true);
          X5LongClickPopMenu.this.dismiss();
        }
      });
      this.mContentLinearLayout.addView((View)localObject1);
    }
  }
  
  private void savePageToDisk(final String paramString)
  {
    final Object localObject1 = new File("/sdcard/QQBrowser/网页保存");
    if (!((File)localObject1).exists()) {
      ((File)localObject1).mkdirs();
    }
    String str1 = this.mWebView.getTitle();
    String str2 = ((File)localObject1).getAbsolutePath();
    localObject1 = "";
    if (TextUtils.isEmpty(str1)) {
      localObject1 = "(1)";
    }
    Object localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(str1);
    ((StringBuilder)localObject2).append((String)localObject1);
    ((StringBuilder)localObject2).append(".mht");
    localObject1 = FileUtils.renameFileIfExist(str2, ((StringBuilder)localObject2).toString()).replaceAll("%", "");
    localObject2 = new File((String)localObject1);
    int i = 1;
    while (((File)localObject2).exists())
    {
      localObject1 = new StringBuilder();
      ((StringBuilder)localObject1).append("(");
      ((StringBuilder)localObject1).append(i);
      ((StringBuilder)localObject1).append(")");
      localObject1 = ((StringBuilder)localObject1).toString();
      localObject2 = new StringBuilder();
      ((StringBuilder)localObject2).append(str1);
      ((StringBuilder)localObject2).append((String)localObject1);
      ((StringBuilder)localObject2).append(".mht");
      localObject1 = FileUtils.renameFileIfExist(str2, ((StringBuilder)localObject2).toString()).replaceAll("%", "");
      localObject2 = new File((String)localObject1);
      i += 1;
    }
    localObject2 = new StringBuilder();
    ((StringBuilder)localObject2).append(str2);
    ((StringBuilder)localObject2).append(File.separator);
    ((StringBuilder)localObject2).append((String)localObject1);
    localObject1 = ((StringBuilder)localObject2).toString();
    paramString = new ValueCallback()
    {
      public void onReceiveValue(String paramAnonymousString)
      {
        if (paramAnonymousString != null)
        {
          paramAnonymousString = X5LongClickPopMenu.this.mContext;
          String str = paramString;
          StringBuilder localStringBuilder = new StringBuilder();
          localStringBuilder.append("cmd=saveofflinepage,path=");
          localStringBuilder.append(localObject1);
          f.a(paramAnonymousString, str, "BDTT6", "8", localStringBuilder.toString());
        }
      }
    };
    localObject2 = this.mWebView;
    if ((localObject2 instanceof X5WebViewAdapter)) {
      ((X5WebViewAdapter)localObject2).savePageToDisk((String)localObject1, false, 0, paramString);
    }
  }
  
  private void vibrate()
  {
    try
    {
      this.mWebView.getView().performHapticFeedback(0);
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public LinearLayout.LayoutParams getButtonLayoutParams(boolean paramBoolean1, boolean paramBoolean2)
  {
    boolean bool = this.mIsVerticalMenu;
    int j = 0;
    int i;
    if (bool)
    {
      localLayoutParams = new LinearLayout.LayoutParams(-1, MENU_BUTTON_HEIGHT);
      int k = com.tencent.smtt.webkit.ui.e.X5_TBS_MENU_BACKGOUND_WITHOUT_ARROW_TRANSPARENT_PIX_WIDTH;
      if (paramBoolean1) {
        i = MENU_BUTTON_MARGIN_BORDER;
      } else {
        i = 0;
      }
      if (paramBoolean2) {
        j = MENU_BUTTON_MARGIN_BORDER;
      }
      localLayoutParams.setMargins(k, i, k, j);
      return localLayoutParams;
    }
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-2, -2);
    if (paramBoolean1) {
      i = MENU_HORIZONTAL_BUTTON_MARGIN_BORDER;
    } else {
      i = 0;
    }
    if (paramBoolean2) {
      j = MENU_HORIZONTAL_BUTTON_MARGIN_BORDER;
    } else {
      j = 0;
    }
    localLayoutParams.setMargins(i, 0, j, 0);
    return localLayoutParams;
  }
  
  public void resetWidthAndMargins()
  {
    LinearLayout localLinearLayout;
    if (this.mIsVerticalMenu) {
      localLinearLayout = this.mContentLinearLayout;
    } else {
      localLinearLayout = this.mHorMenuContentLinearLayout;
    }
    int j = localLinearLayout.getChildCount();
    if (j == 0) {
      return;
    }
    if (this.mIsVerticalMenu)
    {
      this.mPopMenuWidth = (MENU_BUTTON_WIDTH + com.tencent.smtt.webkit.ui.e.X5_TBS_MENU_BACKGOUND_WITHOUT_ARROW_SHADE_FIXED_PIX_WIDTH);
      this.mPopMenuHeight = (MENU_BUTTON_HEIGHT * j + MENU_BUTTON_MARGIN_BORDER * 2 + com.tencent.smtt.webkit.ui.e.X5_TBS_MENU_BACKGOUND_WITHOUT_ARROW_SHADE_FIXED_PIX_HEIGHT);
    }
    else
    {
      this.mPopMenuWidth = (MENU_HORIZONTAL_BUTTON_WIDTH * j + MENU_HORIZONTAL_BUTTON_MARGIN_BORDER * 2 + com.tencent.smtt.webkit.ui.e.X5_TBS_MENU_BACKGOUND_WITH_ARROW_SHADE_FIXED_PIX_WIDTH);
      this.mPopMenuHeight = (MENU_BUTTON_HEIGHT + com.tencent.smtt.webkit.ui.e.X5_TBS_MENU_BACKGOUND_WITH_ARROW_SHADE_FIXED_PIX_HEIGHT);
    }
    int i = 0;
    while (i < j)
    {
      View localView = localLinearLayout.getChildAt(i);
      boolean bool2 = true;
      boolean bool1;
      if (i == 0) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      if (i != j - 1) {
        bool2 = false;
      }
      localView.setLayoutParams(getButtonLayoutParams(bool1, bool2));
      i += 1;
    }
  }
  
  public void setLongClickPoint(Point paramPoint)
  {
    this.mLongClickPoint = paramPoint;
    paramPoint = getWindow().getAttributes();
    paramPoint.gravity = 51;
    if (this.mIsVerticalMenu)
    {
      int i = SMTTAdaptation.getScreenWidth(this.mContext);
      if (this.mLongClickPoint.x + this.mPopMenuWidth > i) {
        paramPoint.x = (this.mLongClickPoint.x - this.mPopMenuWidth);
      } else {
        paramPoint.x = this.mLongClickPoint.x;
      }
    }
    else
    {
      paramPoint.x = (this.mLongClickPoint.x - this.mPopMenuWidth / 2);
    }
    if (this.mIsVerticalMenu) {
      paramPoint.y = (this.mLongClickPoint.y - this.mStatusBarHeight);
    } else {
      paramPoint.y = (this.mLongClickPoint.y + CONTROL_EDITTEXT_POPUP_VERTICALLY_OFFSET);
    }
    getWindow().setLayout(this.mPopMenuWidth, this.mPopMenuHeight);
  }
  
  public void show()
  {
    int i = this.mHitTestResult.getType();
    IX5WebViewBase.HitTestResult localHitTestResult = this.mHitTestResult;
    if ((i == 5) && (!this.mImageTypeShowPopmenu)) {
      return;
    }
    if (this.mIsVerticalMenu)
    {
      if (this.mContentLinearLayout.getChildCount() != 0) {}
    }
    else if (this.mHorMenuContentLinearLayout.getChildCount() == 0) {
      return;
    }
    if (this.mNormalTextSelect == true)
    {
      enterSelectionMode(false);
      dismiss();
      return;
    }
    vibrate();
    super.show();
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\menu\X5LongClickPopMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */