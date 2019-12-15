package com.tencent.tbs.core.partner.menu;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.ClipboardManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.tencent.common.utils.MttLoader;
import com.tencent.common.utils.ReflectionUtils;
import com.tencent.smtt.export.external.extension.interfaces.IX5WebViewClientExtension;
import com.tencent.smtt.export.external.interfaces.ISelectionInterface;
import com.tencent.smtt.export.external.interfaces.IX5WebViewBase.HitTestResult;
import com.tencent.smtt.export.internal.interfaces.IX5WebView;
import com.tencent.smtt.export.internal.interfaces.IX5WebView.TRANSLATE_STATE;
import com.tencent.smtt.util.f;
import com.tencent.smtt.util.j;
import com.tencent.smtt.webkit.SmttResource;
import com.tencent.smtt.webkit.WebSettingsExtension;
import com.tencent.smtt.webkit.WebViewProviderExtension;
import com.tencent.smtt.webkit.e;
import com.tencent.smtt.webkit.service.SmttServiceProxy;
import com.tencent.smtt.webkit.ui.h;
import com.tencent.tbs.core.partner.clipboard.PartnerClipboard;
import com.tencent.tbs.core.webkit.adapter.X5WebViewAdapter;
import com.tencent.tbs.core.webkit.tencent.TencentWebViewProvider;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class X5Selection
  implements ISelectionInterface
{
  public static final String DOWNLOAD_QB_URL_DEFAULT = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10945";
  public static final String DOWNLOAD_QB_URL_FORMM = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10604";
  public static final String DOWNLOAD_QB_URL_FORQZONE = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10944";
  private static final int HEIGHT_FACTORY = 3;
  public static final int MODE_CALL = 1;
  public static final int MODE_LIGHT_APP = 2;
  public static final int MODE_NORMAL = 0;
  private static final String POS_ID_OPEN_QB_VIA_LONG_PRESS_ON_TRANSLATE = "11";
  private static final String POS_ID_OPEN_QB_VIA_LONG_PRESS_ON_URL = "10";
  public static final String QB_DOWNLOAD_FILENAME = "qqbrowser_entersearch.apk";
  private static final String STAT_KEY_TRANSLATE_AFTER_LONG_PRESS = "AGNF1";
  private static final String TRANSLATE_WEBSITE_YOUDAO = "http://m.youdao.com/translate?inputtext=%s&keyfrom=%s";
  private static final float defaultSelectionDensity = 0.75F;
  private final String TAG = "X5Selection";
  private Bitmap capBmp = null;
  private String copyToPaste = SmttResource.getString("x5_copy_to_paste", "string");
  private String invalidUrl = SmttResource.getString("x5_toast_invalid_url", "string");
  private boolean isShowUpOrDownArrow = false;
  private Context mContext = null;
  protected X5CopyView mCopyView = null;
  private View.OnClickListener mCopyViewListener;
  private int mCopyViewTopOffset = 50;
  private SelectionDialog mDialog = null;
  private Rect mEndRect;
  private FrameLayout mFrameLayout = null;
  protected IX5WebViewBase.HitTestResult mHitTestType = null;
  protected int mHolderHeight = 0;
  private ISelectionHost mHost = null;
  private boolean mIsNight = false;
  private boolean mIsSelectionChangedByHandleView = false;
  private boolean mIsUseDialog = false;
  private int mMode = 0;
  int mOperate = -1;
  private Resources mRes;
  private int[] mSelectYZone = new int[2];
  private boolean mShowSosHasReportedForWX = false;
  private Rect mStartRect;
  private boolean mVisible = false;
  IX5WebView mWebView = null;
  
  public X5Selection(Context paramContext, ISelectionHost paramISelectionHost, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mRes = paramContext.getResources();
    this.mHost = paramISelectionHost;
    this.mIsNight = paramBoolean;
    InitSelectionViewifNeed();
  }
  
  public X5Selection(Context paramContext, ISelectionHost paramISelectionHost, boolean paramBoolean, IX5WebView paramIX5WebView)
  {
    if (paramIX5WebView != null) {
      this.mWebView = paramIX5WebView;
    }
    this.mContext = paramContext;
    this.mRes = paramContext.getResources();
    this.mHost = paramISelectionHost;
    this.mIsNight = paramBoolean;
    InitSelectionViewifNeed();
  }
  
  private void doTranslate()
  {
    SmttServiceProxy.getInstance().userBehaviorStatistics("CH0037");
    String str = this.mWebView.getSelectionText();
    if (TextUtils.isEmpty(str)) {
      return;
    }
    IX5WebView localIX5WebView = this.mWebView;
    if ((localIX5WebView instanceof X5WebViewAdapter)) {
      ((X5WebViewAdapter)localIX5WebView).getWebViewProvider().getExtension().translateWord(str);
    }
  }
  
  private void doTranslateInWebpage()
  {
    Object localObject3 = this.mWebView.getSelectionText();
    if (TextUtils.isEmpty((CharSequence)localObject3)) {
      return;
    }
    int i;
    for (;;)
    {
      try
      {
        SmttServiceProxy.getInstance().userBehaviorStatistics("AGNF1");
        localObject2 = this.mContext.getPackageName();
        localObject1 = "tbs_other";
        if ("com.tencent.mm".equals(localObject2)) {
          localObject1 = "tbs_wx";
        } else if ("com.tencent.mobileqq".equals(localObject2)) {
          localObject1 = "tbs_qq";
        }
        localObject3 = URLEncoder.encode((String)localObject3, "UTF-8");
        i = 0;
        localObject1 = String.format("http://m.youdao.com/translate?inputtext=%s&keyfrom=%s", new Object[] { localObject3, localObject1 });
        if ((SmttServiceProxy.getInstance().isDebugMiniQBEnv()) || (!f.a(this.mContext, (String)localObject1, "BTN_ID_TRANSLATE", "11", new Point(0, 0), new Point(0, 0)))) {
          if ((!SmttServiceProxy.getInstance().isMidPageQbTranslateOnLongPressEnabled(this.mContext)) || (!f.a(this.mContext, (String)localObject1, "AZTQB13", 2, new Point(0, 0), new Point(0, 0)))) {
            break label443;
          }
        }
        if ((i != 0) && (!SmttServiceProxy.getInstance().openUrlInQQBrowserWithReport(this.mContext, (String)localObject1, "BTN_ID_TRANSLATE", (String)localObject2, "11")))
        {
          localObject3 = new Intent();
          ((Intent)localObject3).setAction("android.intent.action.VIEW");
          ((Intent)localObject3).setData(Uri.parse((String)localObject1));
          localObject1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        }
      }
      catch (Exception localException)
      {
        Object localObject2;
        Object localObject1;
        return;
      }
      try
      {
        localObject2 = new StringBuilder();
        ((StringBuilder)localObject2).append(".");
        ((StringBuilder)localObject2).append(this.mContext.getPackageName());
        localObject2 = new File((File)localObject1, ((StringBuilder)localObject2).toString());
        if (!((File)localObject2).exists())
        {
          boolean bool = ((File)localObject2).mkdir();
          if (!bool) {}
        }
        else
        {
          localObject1 = localObject2;
        }
        localObject2 = localObject1;
      }
      catch (Throwable localThrowable) {}
    }
    localObject2 = localObject1;
    if (this.mContext.getApplicationInfo().packageName.equals("com.qzone"))
    {
      localObject1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10944";
    }
    else
    {
      if (!this.mContext.getApplicationInfo().packageName.equals("com.tencent.mm")) {
        break label448;
      }
      localObject1 = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10604";
    }
    for (;;)
    {
      HashMap localHashMap = SmttResource.getBrowserListIcons(this.mContext, (Intent)localObject3, (X5WebViewAdapter)this.mWebView);
      f.a("", this.mContext, (Intent)localObject3, (File)localObject2, SmttResource.getString("x5_tbs_wechat_activity_picker_open_with_title", "string"), (String)localObject1, "qqbrowser_entersearch.apk", " ", " ", "11", localHashMap);
      return;
      label443:
      i = 1;
      break;
      label448:
      String str = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10945";
    }
  }
  
  private Point getCopyViewPos(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Rect paramRect)
  {
    Rect localRect1 = this.mStartRect;
    Rect localRect2 = this.mEndRect;
    if (localRect2 == null) {
      return new Point(0, 0);
    }
    Point localPoint = new Point(0, 0);
    if ((this.mWebView.seletionStatus() == 2) && (paramRect != null))
    {
      int i;
      int j;
      int k;
      if (!paramRect.contains(localRect1))
      {
        i = paramRect.top;
        j = paramRect.top;
        k = localRect1.height();
        localRect1.set(localRect1.left, i, localRect1.right, j + k);
      }
      if (!paramRect.contains(localRect2))
      {
        i = paramRect.bottom;
        j = localRect2.height();
        k = paramRect.bottom;
        localRect2.set(localRect2.left, i - j, localRect2.right, k);
      }
    }
    this.mSelectYZone[0] = Math.min(localRect1.top, localRect2.top);
    this.mSelectYZone[1] = Math.max(localRect1.bottom, localRect2.bottom);
    if ((localRect1.top == localRect2.top) && (localRect1.bottom == localRect2.bottom)) {
      localPoint.x = (localRect1.left + (localRect2.left - localRect1.left) / 2 - paramInt3 / 2);
    } else {
      localPoint.x = (paramInt1 / 2 - paramInt3 / 2);
    }
    if (localPoint.x + paramInt3 > paramInt1) {
      localPoint.x = (paramInt1 - paramInt3);
    }
    if (localPoint.x < 0) {
      localPoint.x = 0;
    }
    if (!this.mIsUseDialog)
    {
      if (localRect1.top - this.mCopyViewTopOffset > paramInt4)
      {
        localPoint.y = (localRect1.top - paramInt4);
        this.isShowUpOrDownArrow = false;
        return localPoint;
      }
      if (paramInt2 - localRect2.bottom - this.mHolderHeight > paramInt4)
      {
        localPoint.y = (localRect2.bottom + this.mHolderHeight);
        this.isShowUpOrDownArrow = true;
        return localPoint;
      }
      localPoint.y = (paramInt2 / 2);
      this.isShowUpOrDownArrow = false;
    }
    return localPoint;
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
  
  private void initDialogIfNeed()
  {
    if (this.mDialog == null)
    {
      this.mDialog = new SelectionDialog(SmttResource.getThemeContextWrapper(this.mContext, "x5_MttFuncWindowTheme"), this);
      this.mDialog.requestWindowFeature(1);
      this.mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
      this.mDialog.setCanceledOnTouchOutside(true);
      this.mDialog.setCancelable(true);
      this.mDialog.setContentView(this.mCopyView);
    }
  }
  
  private void initFrameLayoutIfNeed()
  {
    if (this.mFrameLayout == null)
    {
      this.mFrameLayout = new FrameLayout(this.mContext);
      this.mFrameLayout.addView(this.mCopyView, new FrameLayout.LayoutParams(-2, -2));
      this.mFrameLayout.setVisibility(4);
      this.mHost.attachSelectionView(this.mFrameLayout);
    }
  }
  
  private void removeSelectionViewIfNeed()
  {
    if (this.mCopyView != null)
    {
      resetState(false, hasText());
      this.mCopyView.invalidate();
      if (this.mIsUseDialog)
      {
        localObject = this.mDialog;
        if (localObject != null)
        {
          ((SelectionDialog)localObject).dismiss();
          this.mDialog = null;
          this.mCopyView.removeAllViews();
          this.mCopyView = null;
        }
      }
    }
    Object localObject = this.capBmp;
    if (localObject != null)
    {
      ((Bitmap)localObject).recycle();
      this.capBmp = null;
    }
  }
  
  private void setExtentionMenu()
  {
    Object localObject = Integer.valueOf(0);
    try
    {
      Integer localInteger = (Integer)this.mWebView.getClass().getMethod("getLongPressTextExtensionMenu", (Class[])null).invoke(this.mWebView, (Object[])null);
      localObject = localInteger;
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      localNoSuchMethodException.printStackTrace();
    }
    catch (InvocationTargetException localInvocationTargetException)
    {
      localInvocationTargetException.printStackTrace();
    }
    catch (IllegalAccessException localIllegalAccessException)
    {
      localIllegalAccessException.printStackTrace();
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
    }
    this.mCopyView.setLongPressTextExtensionMenu(((Integer)localObject).intValue());
  }
  
  public void InitSelectionViewifNeed()
  {
    for (;;)
    {
      try
      {
        if (this.mCopyView == null)
        {
          if (!"com.tencent.mm".equals(this.mContext.getPackageName())) {
            break label371;
          }
          if (this.mWebView.getWebViewClientExtension() == null) {
            return;
          }
          Object localObject = new Bundle();
          localObject = this.mWebView.getWebViewClientExtension().onMiscCallBack("supportSmartPickWord", (Bundle)localObject);
          if ((localObject == null) || (!(localObject instanceof Boolean)) || (!((Boolean)localObject).booleanValue())) {
            break label371;
          }
          bool = true;
          this.mCopyView = new X5CopyView(this, this.mContext, getCopyViewListener(), this.mIsNight, bool);
          if (((this.mHost instanceof X5WebViewAdapter)) && (((X5WebViewAdapter)this.mHost).getMenuCleared())) {
            this.mCopyView.setAppMenuItemArrayList(((X5WebViewAdapter)this.mHost).getX5ActionMode().getX5MenuInflater().getX5MenuItem());
          }
          resetState(false, hasText());
          if (this.mHolderHeight == 0)
          {
            float f = SmttResource.getResources().getDisplayMetrics().density;
            double d = f;
            Double.isNaN(d);
            int i = (int)(d * 22.0D);
            this.mHolderHeight = i;
          }
        }
        if ((this.mHost.useDialogSubMenuAuto()) && (this.mHost.getHostHeight() < this.mCopyView.getCopyViewHeight() * 3) && ((this.mContext instanceof Activity)))
        {
          this.mIsUseDialog = true;
          if (this.mFrameLayout != null)
          {
            this.mFrameLayout.removeAllViews();
            if (this.mFrameLayout.getParent() != null) {
              ((ViewGroup)this.mFrameLayout.getParent()).removeView(this.mFrameLayout);
            }
            this.mFrameLayout = null;
          }
          initDialogIfNeed();
          return;
        }
        this.mIsUseDialog = false;
        if (this.mDialog != null)
        {
          if (this.mCopyView.getParent() != null) {
            ((ViewGroup)this.mCopyView.getParent()).removeView(this.mCopyView);
          }
          this.mDialog = null;
        }
        initFrameLayoutIfNeed();
        return;
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      label371:
      boolean bool = false;
    }
  }
  
  public int getCopyViewHeight()
  {
    return this.mCopyView.getCopyViewHeight();
  }
  
  public View.OnClickListener getCopyViewListener()
  {
    View.OnClickListener localOnClickListener = this.mCopyViewListener;
    if (localOnClickListener == null) {
      return new CopyViewListener(null);
    }
    return localOnClickListener;
  }
  
  public int getCopyViewWidth()
  {
    return this.mCopyView.getCopyViewWidth();
  }
  
  public ViewGroup.LayoutParams getSelectLayoutParams()
  {
    if (this.mIsUseDialog) {
      return this.mDialog.getWindow().getAttributes();
    }
    return this.mFrameLayout.getLayoutParams();
  }
  
  public int[] getSelectYZone()
  {
    return this.mSelectYZone;
  }
  
  public String getText()
  {
    return PartnerClipboard.getText(this.mContext);
  }
  
  public boolean hasText()
  {
    return ((ClipboardManager)this.mContext.getSystemService("clipboard")).hasText();
  }
  
  public void hide()
  {
    this.mVisible = false;
    Object localObject;
    if (this.mIsUseDialog)
    {
      localObject = this.mDialog;
      if (localObject != null) {
        ((SelectionDialog)localObject).hide();
      }
    }
    else
    {
      localObject = this.mFrameLayout;
      if (localObject != null) {
        ((FrameLayout)localObject).setVisibility(4);
      }
    }
  }
  
  public void hideSelectionView()
  {
    if (this.mCopyView != null)
    {
      resetState(false, hasText());
      this.mCopyView.invalidate();
    }
  }
  
  public boolean isInCopySelect()
  {
    boolean bool2 = this.mWebView.isSelectionMode();
    boolean bool1 = true;
    if (!bool2)
    {
      if (this.mWebView.seletionStatus() == 1) {
        return true;
      }
      bool1 = false;
    }
    return bool1;
  }
  
  public boolean isNightMode()
  {
    return false;
  }
  
  public boolean isSelectionViewVisible()
  {
    return this.mVisible;
  }
  
  public boolean isUseDialog()
  {
    return this.mIsUseDialog;
  }
  
  public void onHandleViewDraggingBegin()
  {
    this.mIsSelectionChangedByHandleView = true;
  }
  
  public void onRetrieveFingerSearchContextResponse(String paramString1, String paramString2, int paramInt) {}
  
  public void onSelectCancel()
  {
    removeSelectionView();
  }
  
  public void onSelectionBegin(Rect paramRect1, Rect paramRect2, int paramInt1, int paramInt2, short paramShort)
  {
    InitSelectionViewifNeed();
    resetState(false, hasText());
    Point localPoint = new Point(paramInt1, paramInt2);
    new Rect();
    switch (paramShort)
    {
    default: 
      return;
    case 1: 
      localPoint.x = paramRect2.right;
      localPoint.y = (paramRect2.top + Math.round(paramRect2.height() / 2.0F));
      paramRect1.set(paramInt1, paramRect2.top, paramRect2.width() + paramInt1, paramRect2.bottom);
      return;
    }
    localPoint.x = paramRect1.left;
    localPoint.y = (paramRect1.top + Math.round(paramRect1.height() / 2.0F));
    paramRect1.set(paramInt1, paramRect1.top, paramRect1.width() + paramInt1, paramRect1.bottom);
  }
  
  public void onSelectionBeginFailed(int paramInt1, int paramInt2) {}
  
  public void onSelectionChange(Rect paramRect1, Rect paramRect2, int paramInt1, int paramInt2, short paramShort)
  {
    InitSelectionViewifNeed();
    updateHelperWidget(paramRect1, paramRect2);
    Point localPoint = new Point();
    Rect localRect = new Rect();
    switch (paramShort)
    {
    default: 
      return;
    case 1: 
      localPoint.x = paramRect2.right;
      localPoint.y = (paramRect2.top + Math.round(paramRect2.height() / 2.0F));
      localRect.set(paramInt1, paramRect2.top, paramRect2.width() + paramInt1, paramRect2.bottom);
      return;
    }
    localPoint.x = paramRect1.left;
    localPoint.y = (paramRect1.top + Math.round(paramRect1.height() / 2.0F));
    localRect.set(paramInt1, paramRect1.top, paramRect1.width() + paramInt1, paramRect1.bottom);
  }
  
  public void onSelectionDone(Rect paramRect, boolean paramBoolean)
  {
    if (this.mCopyView == null) {
      return;
    }
    Object localObject = this.mWebView.getSelectionText();
    this.mCopyView.setSelectText((String)localObject);
    try
    {
      MttLoader.isBrowserInstalled(this.mContext);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    IX5WebViewBase.HitTestResult localHitTestResult = this.mHitTestType;
    int i;
    if ((localHitTestResult != null) && (localHitTestResult.getType() == 9)) {
      i = 1;
    } else {
      i = 0;
    }
    if ((this.mWebView.seletionStatus() != 2) && (this.mWebView.seletionStatus() != 4) && (i == 0))
    {
      if (j.a((String)localObject) != null) {
        setDisplayMode(X5CopyView.MODE_IN_NORMAL_URL, paramBoolean);
      } else {
        setDisplayMode(X5CopyView.MODE_IN_NORMAL, paramBoolean);
      }
    }
    else {
      setDisplayMode(X5CopyView.MODE_IN_INPUT, paramBoolean);
    }
    if ((this.mCopyView.getSearchInMMShow()) && ((!this.mShowSosHasReportedForWX) || (this.mIsSelectionChangedByHandleView)))
    {
      localObject = new Bundle();
      ((Bundle)localObject).putBoolean("IsCursorMove", this.mIsSelectionChangedByHandleView);
      this.mWebView.getWebViewClientExtension().onMiscCallBack("onShowSos", (Bundle)localObject);
      this.mCopyView.setSearchInMMShow(false);
      this.mShowSosHasReportedForWX = true;
    }
    paramRect = getCopyViewPos(this.mHost.getHostWidth(), this.mHost.getHostHeight(), this.mCopyView.getCopyViewWidth(), this.mCopyView.getCopyViewHeight(), paramRect);
    this.mHost.setSelectionViewPosition(paramRect);
    if (!this.mIsUseDialog) {
      setArrowVisable(this.isShowUpOrDownArrow);
    }
    this.mCopyView.requestLayout();
    resetState(true, hasText());
    if (!this.mIsUseDialog) {
      this.mCopyView.showWithAnimation();
    }
    this.mIsSelectionChangedByHandleView = false;
  }
  
  public void onSelectionEnd()
  {
    this.mShowSosHasReportedForWX = false;
  }
  
  public void onShowSosMM()
  {
    Bundle localBundle = new Bundle();
    this.mWebView.getWebViewClientExtension().onMiscCallBack("onShowSos", localBundle);
  }
  
  public void removeSelectionView()
  {
    this.mWebView.leaveSelectionMode();
    removeSelectionViewIfNeed();
  }
  
  public void requestUpdatePosition()
  {
    Object localObject;
    if (this.mIsUseDialog)
    {
      localObject = this.mDialog;
      if ((localObject != null) && (((SelectionDialog)localObject).isShowing() == true) && (isInCopySelect())) {
        this.mWebView.updateSelectionPosition();
      }
    }
    else
    {
      localObject = this.mFrameLayout;
      if ((localObject != null) && (((FrameLayout)localObject).getVisibility() == 0) && (isInCopySelect())) {
        this.mWebView.updateSelectionPosition();
      }
    }
  }
  
  public void resetState(boolean paramBoolean1, boolean paramBoolean2)
  {
    try
    {
      this.mCopyView.resetState(paramBoolean1, paramBoolean2);
      if (paramBoolean1)
      {
        show();
        return;
      }
      hide();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    catch (NoClassDefFoundError localNoClassDefFoundError)
    {
      localNoClassDefFoundError.printStackTrace();
    }
  }
  
  public void setArrowVisable(boolean paramBoolean)
  {
    this.isShowUpOrDownArrow = paramBoolean;
    this.mCopyView.updateView(this.mIsNight, paramBoolean);
  }
  
  public void setCopyViewListener(View.OnClickListener paramOnClickListener)
  {
    this.mCopyViewListener = paramOnClickListener;
    this.mCopyView.resetButtonListener(paramOnClickListener);
  }
  
  public void setDisplayMode(Byte paramByte, boolean paramBoolean)
  {
    setExtentionMenu();
    Object localObject = this.mWebView;
    if ((localObject instanceof X5WebViewAdapter))
    {
      localObject = ((X5WebViewAdapter)localObject).getWebViewProvider().getExtension().getTranslateState("");
      X5CopyView localX5CopyView = this.mCopyView;
      IX5WebView.TRANSLATE_STATE localTRANSLATE_STATE = IX5WebView.TRANSLATE_STATE.TRANLATEHELPER_NOT_INIT;
      boolean bool2 = true;
      boolean bool1;
      if (localObject != localTRANSLATE_STATE) {
        bool1 = true;
      } else {
        bool1 = false;
      }
      localX5CopyView.setIsTranslaterAvailable(bool1);
      localX5CopyView = this.mCopyView;
      if (localObject == IX5WebView.TRANSLATE_STATE.ENTER_SELECTION) {
        bool1 = bool2;
      } else {
        bool1 = false;
      }
      localX5CopyView.setHasEnterSelectionMode(bool1);
    }
    this.mCopyView.setDisplayMode(paramByte.byteValue(), paramBoolean);
  }
  
  public void setHitType(IX5WebViewBase.HitTestResult paramHitTestResult)
  {
    this.mHitTestType = paramHitTestResult;
  }
  
  public void setLayout(int paramInt1, int paramInt2)
  {
    Object localObject;
    if (this.mIsUseDialog)
    {
      localObject = this.mDialog;
      if (localObject != null) {
        ((SelectionDialog)localObject).getWindow().setLayout(paramInt1, paramInt2);
      }
    }
    else
    {
      localObject = this.mFrameLayout;
      if (localObject != null) {
        ((FrameLayout)localObject).setLayoutParams(new FrameLayout.LayoutParams(paramInt1, paramInt2));
      }
    }
  }
  
  public void setMode(int paramInt)
  {
    this.mMode = paramInt;
  }
  
  public void setOwnerWebView(IX5WebView paramIX5WebView)
  {
    if (paramIX5WebView != null)
    {
      this.mWebView = paramIX5WebView;
      return;
    }
    throw new RuntimeException("Init X5Selection Error with nullpointer!");
  }
  
  /* Error */
  public void setText(String paramString, boolean paramBoolean)
  {
    // Byte code:
    //   0: aload_1
    //   1: invokestatic 876	com/tencent/common/utils/StringUtils:isEmpty	(Ljava/lang/String;)Z
    //   4: ifne +6 -> 10
    //   7: goto +7 -> 14
    //   10: ldc_w 362
    //   13: astore_1
    //   14: iload_2
    //   15: ifeq +34 -> 49
    //   18: aload_0
    //   19: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   22: aload_1
    //   23: invokestatic 879	com/tencent/tbs/core/partner/clipboard/PartnerClipboard:setText	(Landroid/content/Context;Ljava/lang/String;)V
    //   26: aload_1
    //   27: invokestatic 212	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   30: ifne +18 -> 48
    //   33: aload_0
    //   34: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   37: aload_0
    //   38: getfield 138	com/tencent/tbs/core/partner/menu/X5Selection:copyToPaste	Ljava/lang/String;
    //   41: aload_0
    //   42: getfield 108	com/tencent/tbs/core/partner/menu/X5Selection:mIsNight	Z
    //   45: invokestatic 884	com/tencent/smtt/webkit/ui/h:a	(Landroid/content/Context;Ljava/lang/CharSequence;Z)V
    //   48: return
    //   49: invokestatic 889	com/tencent/smtt/webkit/ContextHolder:getInstance	()Lcom/tencent/smtt/webkit/ContextHolder;
    //   52: invokevirtual 892	com/tencent/smtt/webkit/ContextHolder:isTbsDevelopMode	()Z
    //   55: ifeq +100 -> 155
    //   58: iconst_2
    //   59: aload_0
    //   60: getfield 110	com/tencent/tbs/core/partner/menu/X5Selection:mOperate	I
    //   63: if_icmpne +92 -> 155
    //   66: aload_1
    //   67: invokestatic 212	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   70: ifne +84 -> 154
    //   73: aload_0
    //   74: getfield 116	com/tencent/tbs/core/partner/menu/X5Selection:mWebView	Lcom/tencent/smtt/export/internal/interfaces/IX5WebView;
    //   77: invokeinterface 895 1 0
    //   82: invokestatic 309	android/net/Uri:parse	(Ljava/lang/String;)Landroid/net/Uri;
    //   85: astore 4
    //   87: aload 4
    //   89: ifnull +50 -> 139
    //   92: ldc_w 897
    //   95: aload 4
    //   97: invokevirtual 900	android/net/Uri:getScheme	()Ljava/lang/String;
    //   100: invokevirtual 903	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   103: ifeq +36 -> 139
    //   106: ldc_w 905
    //   109: aload 4
    //   111: ldc_w 907
    //   114: invokevirtual 910	android/net/Uri:getQueryParameter	(Ljava/lang/String;)Ljava/lang/String;
    //   117: invokevirtual 903	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   120: ifeq +19 -> 139
    //   123: aload_0
    //   124: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   127: aload_1
    //   128: aload_0
    //   129: getfield 116	com/tencent/tbs/core/partner/menu/X5Selection:mWebView	Lcom/tencent/smtt/export/internal/interfaces/IX5WebView;
    //   132: ldc_w 912
    //   135: invokestatic 918	com/tencent/tbs/core/partner/menu/SearchEngine:doSearch	(Landroid/content/Context;Ljava/lang/String;Lcom/tencent/smtt/export/internal/interfaces/IX5WebView;Ljava/lang/String;)V
    //   138: return
    //   139: aload_0
    //   140: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   143: aload_1
    //   144: aload_0
    //   145: getfield 116	com/tencent/tbs/core/partner/menu/X5Selection:mWebView	Lcom/tencent/smtt/export/internal/interfaces/IX5WebView;
    //   148: ldc_w 920
    //   151: invokestatic 918	com/tencent/tbs/core/partner/menu/SearchEngine:doSearch	(Landroid/content/Context;Ljava/lang/String;Lcom/tencent/smtt/export/internal/interfaces/IX5WebView;Ljava/lang/String;)V
    //   154: return
    //   155: invokestatic 889	com/tencent/smtt/webkit/ContextHolder:getInstance	()Lcom/tencent/smtt/webkit/ContextHolder;
    //   158: invokevirtual 892	com/tencent/smtt/webkit/ContextHolder:isTbsDevelopMode	()Z
    //   161: ifeq +68 -> 229
    //   164: iconst_4
    //   165: aload_0
    //   166: getfield 110	com/tencent/tbs/core/partner/menu/X5Selection:mOperate	I
    //   169: if_icmpne +60 -> 229
    //   172: aload_0
    //   173: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   176: aload_1
    //   177: invokestatic 879	com/tencent/tbs/core/partner/clipboard/PartnerClipboard:setText	(Landroid/content/Context;Ljava/lang/String;)V
    //   180: aload_1
    //   181: invokestatic 212	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   184: ifne +44 -> 228
    //   187: new 427	android/os/Bundle
    //   190: dup
    //   191: invokespecial 428	android/os/Bundle:<init>	()V
    //   194: astore_1
    //   195: aload_1
    //   196: ldc_w 922
    //   199: aload_0
    //   200: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   203: invokestatic 676	com/tencent/tbs/core/partner/clipboard/PartnerClipboard:getText	(Landroid/content/Context;)Ljava/lang/String;
    //   206: invokevirtual 926	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   209: aload_0
    //   210: getfield 116	com/tencent/tbs/core/partner/menu/X5Selection:mWebView	Lcom/tencent/smtt/export/internal/interfaces/IX5WebView;
    //   213: invokeinterface 423 1 0
    //   218: ldc_w 928
    //   221: aload_1
    //   222: invokeinterface 434 3 0
    //   227: pop
    //   228: return
    //   229: invokestatic 889	com/tencent/smtt/webkit/ContextHolder:getInstance	()Lcom/tencent/smtt/webkit/ContextHolder;
    //   232: invokevirtual 892	com/tencent/smtt/webkit/ContextHolder:isTbsDevelopMode	()Z
    //   235: ifeq +70 -> 305
    //   238: sipush 128
    //   241: aload_0
    //   242: getfield 110	com/tencent/tbs/core/partner/menu/X5Selection:mOperate	I
    //   245: if_icmpne +60 -> 305
    //   248: aload_0
    //   249: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   252: aload_1
    //   253: invokestatic 879	com/tencent/tbs/core/partner/clipboard/PartnerClipboard:setText	(Landroid/content/Context;Ljava/lang/String;)V
    //   256: aload_1
    //   257: invokestatic 212	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   260: ifne +44 -> 304
    //   263: new 427	android/os/Bundle
    //   266: dup
    //   267: invokespecial 428	android/os/Bundle:<init>	()V
    //   270: astore_1
    //   271: aload_1
    //   272: ldc_w 922
    //   275: aload_0
    //   276: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   279: invokestatic 676	com/tencent/tbs/core/partner/clipboard/PartnerClipboard:getText	(Landroid/content/Context;)Ljava/lang/String;
    //   282: invokevirtual 926	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   285: aload_0
    //   286: getfield 116	com/tencent/tbs/core/partner/menu/X5Selection:mWebView	Lcom/tencent/smtt/export/internal/interfaces/IX5WebView;
    //   289: invokeinterface 423 1 0
    //   294: ldc_w 930
    //   297: aload_1
    //   298: invokeinterface 434 3 0
    //   303: pop
    //   304: return
    //   305: invokestatic 889	com/tencent/smtt/webkit/ContextHolder:getInstance	()Lcom/tencent/smtt/webkit/ContextHolder;
    //   308: invokevirtual 892	com/tencent/smtt/webkit/ContextHolder:isTbsDevelopMode	()Z
    //   311: ifeq +70 -> 381
    //   314: sipush 256
    //   317: aload_0
    //   318: getfield 110	com/tencent/tbs/core/partner/menu/X5Selection:mOperate	I
    //   321: if_icmpne +60 -> 381
    //   324: aload_0
    //   325: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   328: aload_1
    //   329: invokestatic 879	com/tencent/tbs/core/partner/clipboard/PartnerClipboard:setText	(Landroid/content/Context;Ljava/lang/String;)V
    //   332: aload_1
    //   333: invokestatic 212	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   336: ifne +44 -> 380
    //   339: new 427	android/os/Bundle
    //   342: dup
    //   343: invokespecial 428	android/os/Bundle:<init>	()V
    //   346: astore_1
    //   347: aload_1
    //   348: ldc_w 922
    //   351: aload_0
    //   352: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   355: invokestatic 676	com/tencent/tbs/core/partner/clipboard/PartnerClipboard:getText	(Landroid/content/Context;)Ljava/lang/String;
    //   358: invokevirtual 926	android/os/Bundle:putString	(Ljava/lang/String;Ljava/lang/String;)V
    //   361: aload_0
    //   362: getfield 116	com/tencent/tbs/core/partner/menu/X5Selection:mWebView	Lcom/tencent/smtt/export/internal/interfaces/IX5WebView;
    //   365: invokeinterface 423 1 0
    //   370: ldc_w 932
    //   373: aload_1
    //   374: invokeinterface 434 3 0
    //   379: pop
    //   380: return
    //   381: aload_0
    //   382: getfield 110	com/tencent/tbs/core/partner/menu/X5Selection:mOperate	I
    //   385: istore_3
    //   386: iconst_1
    //   387: iload_3
    //   388: if_icmpeq +9 -> 397
    //   391: bipush 8
    //   393: iload_3
    //   394: if_icmpne +33 -> 427
    //   397: aload_0
    //   398: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   401: aload_1
    //   402: invokestatic 879	com/tencent/tbs/core/partner/clipboard/PartnerClipboard:setText	(Landroid/content/Context;Ljava/lang/String;)V
    //   405: aload_1
    //   406: invokestatic 212	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   409: ifne +18 -> 427
    //   412: aload_0
    //   413: getfield 106	com/tencent/tbs/core/partner/menu/X5Selection:mContext	Landroid/content/Context;
    //   416: aload_0
    //   417: getfield 138	com/tencent/tbs/core/partner/menu/X5Selection:copyToPaste	Ljava/lang/String;
    //   420: aload_0
    //   421: getfield 108	com/tencent/tbs/core/partner/menu/X5Selection:mIsNight	Z
    //   424: invokestatic 884	com/tencent/smtt/webkit/ui/h:a	(Landroid/content/Context;Ljava/lang/CharSequence;Z)V
    //   427: aload_0
    //   428: iconst_m1
    //   429: putfield 110	com/tencent/tbs/core/partner/menu/X5Selection:mOperate	I
    //   432: return
    //   433: astore_1
    //   434: return
    //   435: astore_1
    //   436: return
    //   437: astore_1
    //   438: return
    //   439: astore_1
    //   440: return
    //   441: astore_1
    //   442: return
    //   443: astore_1
    //   444: goto -17 -> 427
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	447	0	this	X5Selection
    //   0	447	1	paramString	String
    //   0	447	2	paramBoolean	boolean
    //   385	10	3	i	int
    //   85	25	4	localUri	Uri
    // Exception table:
    //   from	to	target	type
    //   18	48	433	java/lang/Exception
    //   66	87	435	java/lang/Exception
    //   92	138	435	java/lang/Exception
    //   139	154	435	java/lang/Exception
    //   172	228	437	java/lang/Exception
    //   248	304	439	java/lang/Exception
    //   324	380	441	java/lang/Exception
    //   397	427	443	java/lang/Exception
  }
  
  public void show()
  {
    this.mVisible = true;
    if (this.mIsUseDialog)
    {
      localObject = this.mDialog;
      if (localObject != null) {
        ((SelectionDialog)localObject).show();
      }
    }
    else
    {
      localObject = this.mFrameLayout;
      if (localObject != null) {
        ((FrameLayout)localObject).setVisibility(0);
      }
    }
    Object localObject = this.mWebView;
    if (((X5WebViewAdapter)localObject != null) && (((X5WebViewAdapter)localObject).getSettingsExtension() != null))
    {
      boolean bool = true ^ ((X5WebViewAdapter)this.mWebView).getSettingsExtension().getDayOrNight();
      if (bool != this.mIsNight)
      {
        this.mIsNight = bool;
        this.mCopyView.updateView(this.mIsNight, this.isShowUpOrDownArrow);
      }
    }
    if (e.a().t())
    {
      localObject = this.mWebView;
      if ((localObject instanceof X5WebViewAdapter)) {
        ((X5WebViewAdapter)localObject).uploadLongclick();
      }
    }
  }
  
  public void showCopySelectView()
  {
    if (this.mWebView.isSelectionMode()) {
      onSelectionDone(null, true);
    }
  }
  
  public boolean supportSmartPickWordMM()
  {
    if ("com.tencent.mm".equals(this.mContext.getPackageName()))
    {
      Bundle localBundle = new Bundle();
      if (this.mWebView.getWebViewClientExtension().onMiscCallBack("supportSmartPickWord", localBundle) != null) {
        return true;
      }
    }
    return false;
  }
  
  public void updateDialogWindowsAttributes(WindowManager.LayoutParams paramLayoutParams)
  {
    if (this.mIsUseDialog)
    {
      SelectionDialog localSelectionDialog = this.mDialog;
      if (localSelectionDialog != null) {
        localSelectionDialog.onWindowAttributesChanged(paramLayoutParams);
      }
    }
  }
  
  public void updateHelperWidget(Rect paramRect1, Rect paramRect2)
  {
    InitSelectionViewifNeed();
    this.mEndRect = paramRect2;
    this.mStartRect = paramRect1;
  }
  
  private class CopyViewListener
    implements View.OnClickListener
  {
    private CopyViewListener() {}
    
    public void onClick(View paramView)
    {
      Object localObject2 = (X5CopyButton)paramView;
      Object localObject1;
      if (((X5Selection.this.mHost instanceof X5WebViewAdapter)) && (((X5WebViewAdapter)X5Selection.this.mHost).getMenuCleared()))
      {
        paramView = X5Selection.this.mContext;
        localObject1 = ((X5MenuItem)((X5WebViewAdapter)X5Selection.this.mHost).getX5ActionMode().getX5MenuInflater().getX5MenuItem().get(((X5CopyButton)localObject2).mID)).getOnClick();
        localObject2 = ((X5WebViewAdapter)X5Selection.this.mHost).getX5ActionMode().getX5MenuInflater().getX5MenuItem().get(((X5CopyButton)localObject2).mID);
        ReflectionUtils.invokeInstance(paramView, (String)localObject1, new Class[] { MenuItem.class }, new Object[] { localObject2 });
        X5Selection.this.removeSelectionView();
        return;
      }
      int i = ((X5CopyButton)localObject2).mID;
      Object localObject3;
      Object localObject4;
      if (i != 4) {
        if (i != 8) {
          if (i != 16) {
            if (i != 64) {
              if (i != 128) {
                if (i != 256) {
                  if (i != 512)
                  {
                    if (i != 1024) {
                      switch (i)
                      {
                      default: 
                        break;
                      case 2: 
                        paramView = X5Selection.this;
                        paramView.mOperate = 2;
                        paramView = paramView.mContext.getPackageName();
                        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA011");
                        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTQB88");
                        if ((X5Selection.this.mWebView instanceof X5WebViewAdapter)) {
                          ((X5WebViewAdapter)X5Selection.this.mWebView).getWebViewProvider().getExtension().executeSearchItem();
                        }
                        if ("com.tencent.mm".equals(paramView))
                        {
                          paramView = new Bundle();
                          paramView = X5Selection.this.mWebView.getWebViewClientExtension().onMiscCallBack("supportSmartPickWord", paramView);
                          if (paramView == null)
                          {
                            paramView = X5Selection.this;
                            paramView.setText(paramView.mWebView.getSelectionText(), false);
                          }
                          else if (((paramView instanceof Boolean)) && (((Boolean)paramView).booleanValue()))
                          {
                            new Thread("searchinmm")
                            {
                              public void run()
                              {
                                String str = this.val$searchText;
                                if (!TextUtils.isEmpty(str)) {}
                                try
                                {
                                  Thread.sleep(100L);
                                  Bundle localBundle = new Bundle();
                                  localBundle.putString("query", str);
                                  X5Selection.this.mWebView.getWebViewClientExtension().onMiscCallBack("jumpToSos", localBundle);
                                  return;
                                }
                                catch (Exception localException) {}
                              }
                            }.start();
                          }
                          SmttServiceProxy.getInstance().userBehaviorStatistics("BZSYS002");
                        }
                        else
                        {
                          paramView = X5Selection.this;
                          paramView.setText(paramView.mWebView.getSelectionText(), false);
                        }
                        break;
                      case 1: 
                        paramView = X5Selection.this;
                        paramView.mOperate = 1;
                        paramView.mWebView.copyText();
                        if (((X5Selection.this.mWebView instanceof X5WebViewAdapter)) && (((X5WebViewAdapter)X5Selection.this.mWebView).getQuickSelectCopy()))
                        {
                          SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA017");
                        }
                        else
                        {
                          SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA009");
                          SmttServiceProxy.getInstance().userBehaviorStatistics("AWTQB87");
                        }
                        if ((X5Selection.this.mWebView instanceof X5WebViewAdapter)) {
                          ((X5WebViewAdapter)X5Selection.this.mWebView).getWebViewProvider().getExtension().executeCopyItem();
                        }
                        paramView = X5Selection.this;
                        paramView.setText(paramView.mWebView.getSelectionText(), false);
                        if (!"com.tencent.mm".equals(X5Selection.this.mContext.getPackageName())) {
                          break;
                        }
                        paramView = new Bundle();
                        paramView.putString("text", X5Selection.this.mWebView.getSelectionText());
                        X5Selection.this.mWebView.getWebViewClientExtension().onMiscCallBack("onClickCopyBtn", paramView);
                        break;
                      }
                    }
                    SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA005");
                    paramView = X5Selection.this;
                    paramView.mOperate = 1024;
                    paramView = paramView.mWebView.getUrl();
                    localObject1 = j.a(X5Selection.this.mWebView.getSelectionText());
                    if (localObject1 != null) {
                      paramView = (View)localObject1;
                    }
                    if (SmttServiceProxy.getInstance().onOpenInBrowserClick(X5Selection.this.mContext, paramView, new Bundle())) {
                      break label1254;
                    }
                    localObject1 = new Point(X5Selection.this.mWebView.getScrollX(), X5Selection.this.mWebView.getScrollY());
                    localObject3 = new Point(X5Selection.this.mWebView.getContentWidth(), X5Selection.this.mWebView.getContentHeight());
                    if (f.a(X5Selection.this.mContext, paramView, "BDTT6", "8", (Point)localObject1, (Point)localObject3)) {
                      break label1254;
                    }
                    localObject1 = new Intent("android.intent.action.VIEW");
                    ((Intent)localObject1).addCategory("android.intent.category.BROWSABLE");
                    ((Intent)localObject1).setData(Uri.parse(paramView));
                    localObject3 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    paramView = X5Selection.this.mContext.getApplicationInfo().packageName;
                    localObject4 = X5Selection.this.getShareUrlFromWeChat();
                    if ("com.tencent.mobileqq".equals(paramView)) {
                      paramView = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10948";
                    } else if (!"com.tencent.mm".equals(paramView)) {}
                  }
                }
              }
            }
          }
        }
      }
      try
      {
        if (((String)localObject4).startsWith("http://rd.wechat.com/redirect/confirm")) {
          Uri.parse(URLDecoder.decode((String)localObject4, "UTF-8")).getQueryParameter("url");
        }
        paramView = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10947";
        break label938;
        if ("com.qzone".equals(paramView)) {
          paramView = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10949";
        } else {
          paramView = "http://mdc.html5.qq.com/d/directdown.jsp?channel_id=10825";
        }
        label938:
        localObject4 = SmttResource.getBrowserListIcons(X5Selection.this.mContext, (Intent)localObject1, (X5WebViewAdapter)X5Selection.this.mWebView);
        f.a("", X5Selection.this.mContext, (Intent)localObject1, (File)localObject3, SmttResource.getString("x5_tbs_wechat_activity_picker_open_browser_title", "string"), paramView, "qqbrowser_entersearch.apk", "BDTT6", null, "8", (Map)localObject4);
        break label1254;
        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA013");
        paramView = j.a(X5Selection.this.mWebView.getSelectionText());
        if (paramView == null)
        {
          h.a(X5Selection.this.mContext, X5Selection.this.invalidUrl, X5Selection.this.mIsNight);
          return;
        }
        X5Selection.this.removeSelectionView();
        localObject1 = X5Selection.this.mContext.getApplicationInfo().packageName;
        f.a(X5Selection.this.mContext, paramView, "none", "AZTQB29", "10", 12);
        break label1254;
        SmttServiceProxy.getInstance().userBehaviorStatistics("AWTCA015");
        paramView = X5Selection.this;
        paramView.mOperate = 256;
        paramView.doTranslate();
        break label1254;
        paramView = X5Selection.this;
        paramView.mOperate = 128;
        paramView.mWebView.copyText();
        break label1254;
        if (X5Selection.this.mWebView != null)
        {
          X5Selection.this.mWebView.enterSelectionMode(true);
          break label1254;
          paramView = X5Selection.this;
          paramView.mOperate = 16;
          paramView.mWebView.pasteText(PartnerClipboard.getText(X5Selection.this.mContext));
          break label1254;
          paramView = X5Selection.this;
          paramView.mOperate = 8;
          paramView.mWebView.cutText(null);
          break label1254;
          paramView = X5Selection.this;
          paramView.mOperate = 4;
          paramView.mWebView.copyText();
        }
        label1254:
        if (((X5CopyButton)localObject2).mID != 64) {
          X5Selection.this.removeSelectionView();
        }
        return;
      }
      catch (Exception paramView)
      {
        for (;;) {}
      }
    }
  }
  
  public static abstract interface ISelectionHost
  {
    public abstract void attachSelectionView(View paramView);
    
    public abstract void dispatchWebViewTouchEvent(MotionEvent paramMotionEvent);
    
    public abstract int getContentOffsetY();
    
    public abstract int getHostHeight();
    
    public abstract int[] getHostLocation();
    
    public abstract int getHostWidth();
    
    public abstract void setSelectionViewPosition(Point paramPoint);
    
    public abstract boolean useDialogSubMenuAuto();
  }
  
  private class SelectionDialog
    extends Dialog
  {
    private boolean mIsMovedInWebView = false;
    private X5Selection mX5Selection;
    
    public SelectionDialog(Context paramContext, X5Selection paramX5Selection)
    {
      super();
      this.mX5Selection = paramX5Selection;
    }
    
    public void cancel()
    {
      hide();
    }
    
    public void dismiss()
    {
      super.dismiss();
    }
    
    public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
    {
      int[] arrayOfInt = X5Selection.this.mHost.getHostLocation();
      boolean bool;
      if (X5Selection.this.mCopyView != null) {
        bool = new Rect(0, 0, X5Selection.this.mCopyView.getWidth(), X5Selection.this.mCopyView.getHeight()).contains((int)paramMotionEvent.getX(), (int)paramMotionEvent.getY());
      } else {
        bool = false;
      }
      if ((paramMotionEvent.getAction() == 0) || (paramMotionEvent.getAction() == 3)) {
        this.mIsMovedInWebView = false;
      }
      if (bool)
      {
        if (!this.mIsMovedInWebView) {
          return super.dispatchTouchEvent(paramMotionEvent);
        }
      }
      else if (!new Rect(arrayOfInt[0], arrayOfInt[1], arrayOfInt[0] + X5Selection.this.mHost.getHostWidth(), arrayOfInt[1] + X5Selection.this.mHost.getHostHeight()).contains((int)paramMotionEvent.getRawX(), (int)paramMotionEvent.getRawY()))
      {
        if (!this.mIsMovedInWebView)
        {
          this.mX5Selection.removeSelectionView();
          return true;
        }
      }
      else if (paramMotionEvent.getAction() == 2) {
        this.mIsMovedInWebView = true;
      } else if (paramMotionEvent.getAction() == 1) {
        this.mIsMovedInWebView = false;
      }
      WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
      paramMotionEvent.offsetLocation(localLayoutParams.x - arrayOfInt[0], localLayoutParams.y - arrayOfInt[1]);
      X5Selection.this.mHost.dispatchWebViewTouchEvent(paramMotionEvent);
      return false;
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\tbs\core\partner\menu\X5Selection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */