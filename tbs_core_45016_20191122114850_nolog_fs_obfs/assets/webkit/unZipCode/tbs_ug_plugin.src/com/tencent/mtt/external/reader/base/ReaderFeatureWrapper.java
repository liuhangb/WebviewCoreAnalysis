package com.tencent.mtt.external.reader.base;

import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.tencent.common.utils.LogUtils;
import com.tencent.mtt.external.reader.internal.ScreenLockController;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuEventObj;
import com.tencent.mtt.external.reader.internal.menuConfig.MenuManager;

public class ReaderFeatureWrapper
{
  public static final int KEEP_SCREEN_2_MINUTE = 120000;
  public static final int KEEP_SCREEN_3_MINUTE = 180000;
  Context jdField_a_of_type_AndroidContentContext = null;
  ReaderController jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController = null;
  ReaderMenuManager jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuManager = null;
  ReaderScrollView jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView = null;
  ReaderSelectView jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView = null;
  ReaderToastView jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView = null;
  ReaderWebView jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView = null;
  ScreenLockController jdField_a_of_type_ComTencentMttExternalReaderInternalScreenLockController = null;
  MenuManager jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager = null;
  boolean jdField_a_of_type_Boolean = false;
  
  public ReaderFeatureWrapper(Context paramContext)
  {
    this.jdField_a_of_type_AndroidContentContext = paramContext;
  }
  
  public boolean MenuisShow()
  {
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    int j = 0;
    int k;
    if (localObject != null) {
      k = ((MenuManager)localObject).isShow();
    } else {
      k = 0;
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController;
    int i;
    if (localObject != null) {
      i = ((ReaderController)localObject).isBarShow();
    } else {
      i = -1;
    }
    int m = k;
    if (i != -1)
    {
      if (i == 0) {
        i = j;
      } else {
        i = 1;
      }
      m = k | i;
    }
    return m;
  }
  
  public void SetToastText(int paramInt1, int paramInt2)
  {
    ReaderToastView localReaderToastView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView;
    if (localReaderToastView != null)
    {
      localReaderToastView.SetToastText(paramInt1, paramInt2);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView.showToastView(0);
    }
  }
  
  void a(ReaderController paramReaderController)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController = paramReaderController;
  }
  
  void a(ReaderMenuManager paramReaderMenuManager)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuManager = paramReaderMenuManager;
  }
  
  void a(MenuManager paramMenuManager, boolean paramBoolean)
  {
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager = paramMenuManager;
  }
  
  public void activityPause()
  {
    ReaderWebView localReaderWebView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView;
    if (localReaderWebView != null) {
      localReaderWebView.activityPause();
    }
  }
  
  public void addJSI(Object paramObject, String paramString)
  {
    ReaderWebView localReaderWebView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView;
    if (localReaderWebView != null) {
      localReaderWebView.addJSI(paramObject, paramString);
    }
  }
  
  public void clearMatches()
  {
    ReaderWebView localReaderWebView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView;
    if (localReaderWebView != null) {
      localReaderWebView.clearMatches();
    }
  }
  
  public void createChmWebview(FrameLayout paramFrameLayout, IReaderEvent paramIReaderEvent)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView == null)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView = ((ReaderWebView)ReaderViewCreator.getInstance().create(this.jdField_a_of_type_AndroidContentContext, 8));
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView.setFrameLayout(paramFrameLayout);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView.setEvent(paramIReaderEvent);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView.create();
    }
  }
  
  public void createScrollView(FrameLayout paramFrameLayout)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView == null)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView = new ReaderScrollView(this.jdField_a_of_type_AndroidContentContext);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView.setFrameLayout(paramFrameLayout);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView.create();
    }
  }
  
  public void createSelectView(FrameLayout paramFrameLayout, Bundle paramBundle)
  {
    LogUtils.d("ReaderFeatureWrapper", "createSelectView ...");
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView == null)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView = ((ReaderSelectView)ReaderViewCreator.getInstance().create(this.jdField_a_of_type_AndroidContentContext, 7));
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView.setFrameLayout(paramFrameLayout);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView.setBundle(paramBundle);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView.create();
    }
  }
  
  public void createToastView(FrameLayout paramFrameLayout)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView == null)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView = new ReaderToastView(this.jdField_a_of_type_AndroidContentContext);
      ReaderController localReaderController = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController;
      int i;
      if (localReaderController != null) {
        i = localReaderController.getTitleBarHeight();
      } else {
        i = 0;
      }
      if (i > 0)
      {
        this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView.setTilteBarHeight(i);
        this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView.setEnableMenuShowHideAnim(false);
      }
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView.setFrameLayout(paramFrameLayout);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView.create();
    }
  }
  
  public void createWebview(FrameLayout paramFrameLayout, IReaderEvent paramIReaderEvent)
  {
    if (this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView == null)
    {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView = ((ReaderWebView)ReaderViewCreator.getInstance().create(this.jdField_a_of_type_AndroidContentContext, 5));
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView.setFrameLayout(paramFrameLayout);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView.setEvent(paramIReaderEvent);
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView.create();
    }
  }
  
  public void destroy()
  {
    destroyToastView();
    destroyScrollView();
    destroyWebview();
    destroySelectView();
    a(null);
    destroyScreenLockTime();
  }
  
  public void destroyScreenLockTime()
  {
    this.jdField_a_of_type_Boolean = false;
    ScreenLockController localScreenLockController = this.jdField_a_of_type_ComTencentMttExternalReaderInternalScreenLockController;
    if (localScreenLockController != null) {
      localScreenLockController.destroy();
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalScreenLockController = null;
  }
  
  public void destroyScrollView()
  {
    ReaderScrollView localReaderScrollView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView;
    if (localReaderScrollView != null) {
      localReaderScrollView.destroy();
    }
  }
  
  public void destroySelectView()
  {
    ReaderSelectView localReaderSelectView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView;
    if (localReaderSelectView != null)
    {
      localReaderSelectView.destroy();
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView = null;
    }
  }
  
  public void destroyToastView()
  {
    ReaderToastView localReaderToastView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView;
    if (localReaderToastView != null) {
      localReaderToastView.destroy();
    }
  }
  
  public void destroyWebview()
  {
    ReaderWebView localReaderWebView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView;
    if (localReaderWebView != null) {
      localReaderWebView.destroy();
    }
  }
  
  public void enterSelect()
  {
    ReaderSelectView localReaderSelectView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView;
    if (localReaderSelectView != null) {
      localReaderSelectView.enterSelect();
    }
  }
  
  public void findAllAsync(String paramString)
  {
    ReaderWebView localReaderWebView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView;
    if (localReaderWebView != null) {
      localReaderWebView.findAllAsync(paramString);
    }
  }
  
  public void findNext(boolean paramBoolean)
  {
    ReaderWebView localReaderWebView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView;
    if (localReaderWebView != null) {
      localReaderWebView.findNext(paramBoolean);
    }
  }
  
  public MenuEventObj getSnapshotEventObj()
  {
    MenuManager localMenuManager = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    if (localMenuManager != null) {
      return localMenuManager.getSnapshotEventObj();
    }
    return null;
  }
  
  public String getSnapshotStatistics(String paramString)
  {
    MenuManager localMenuManager = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    if (localMenuManager != null) {
      return localMenuManager.getSnapshotStatistics(paramString);
    }
    return null;
  }
  
  public void hideScrollBar()
  {
    ReaderScrollView localReaderScrollView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView;
    if (localReaderScrollView != null) {
      localReaderScrollView.hideScrollBar();
    }
  }
  
  public void hideToastView()
  {
    ReaderToastView localReaderToastView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView;
    if (localReaderToastView != null) {
      localReaderToastView.hideToastView();
    }
  }
  
  public void initMenu(ReaderConfig paramReaderConfig)
  {
    if ((paramReaderConfig != null) && (!paramReaderConfig.show_menu)) {
      return;
    }
    MenuManager localMenuManager = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    if (localMenuManager != null) {
      localMenuManager.initMenu(paramReaderConfig);
    }
  }
  
  public boolean isAnimating()
  {
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView;
    if ((localObject != null) && (((ReaderToastView)localObject).isAnimating())) {
      return true;
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    if ((localObject != null) && (((MenuManager)localObject).isAnimating())) {
      return true;
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController;
    return (localObject != null) && (((ReaderController)localObject).isBarAnimation());
  }
  
  public void loadUrl(String paramString)
  {
    ReaderWebView localReaderWebView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView;
    if (localReaderWebView != null) {
      localReaderWebView.loadUrl(paramString);
    }
  }
  
  public void menuFocus()
  {
    MenuManager localMenuManager = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    if (localMenuManager != null) {
      localMenuManager.focus();
    }
  }
  
  public void menuHide(boolean paramBoolean)
  {
    MenuManager localMenuManager = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    if (localMenuManager != null) {
      localMenuManager.setMenuVisiable(false, paramBoolean);
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.doCallBackEvent(5001, null, null);
  }
  
  public void menuShow(boolean paramBoolean)
  {
    MenuManager localMenuManager = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    if (localMenuManager != null) {
      localMenuManager.setMenuVisiable(true, paramBoolean);
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderController.doCallBackEvent(5002, null, null);
  }
  
  public void menuUpdate(int paramInt, Bundle paramBundle)
  {
    if ((this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuManager != null) && (paramBundle.containsKey("menuText"))) {
      this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderMenuManager.updateMenuText(paramInt, paramBundle.getString("menuText"));
    }
  }
  
  public void notifySkinChanged()
  {
    Object localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView;
    if (localObject != null) {
      ((ReaderToastView)localObject).notifySkinChanged();
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView;
    if (localObject != null) {
      ((ReaderScrollView)localObject).notifySkinChanged();
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderWebView;
    if (localObject != null) {
      ((ReaderWebView)localObject).notifySkinChanged();
    }
    localObject = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView;
    if (localObject != null) {
      ((ReaderSelectView)localObject).notifySkinChanged();
    }
  }
  
  public void removeMenu()
  {
    MenuManager localMenuManager = this.jdField_a_of_type_ComTencentMttExternalReaderInternalMenuConfigMenuManager;
    if (localMenuManager != null) {
      localMenuManager.removeAll();
    }
  }
  
  public void resetPPTScreenTime()
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    ScreenLockController localScreenLockController = this.jdField_a_of_type_ComTencentMttExternalReaderInternalScreenLockController;
    if (localScreenLockController != null) {
      localScreenLockController.setScreenLockTime(120000);
    }
  }
  
  public void setBackgroundColor(int paramInt) {}
  
  public void setPPTScreenTime()
  {
    if (this.jdField_a_of_type_Boolean) {
      return;
    }
    ScreenLockController localScreenLockController = this.jdField_a_of_type_ComTencentMttExternalReaderInternalScreenLockController;
    if (localScreenLockController != null) {
      localScreenLockController.setScreenLockTime(180000);
    }
  }
  
  public void setScreenLockTime(int paramInt, boolean paramBoolean)
  {
    this.jdField_a_of_type_Boolean = paramBoolean;
    if (this.jdField_a_of_type_ComTencentMttExternalReaderInternalScreenLockController == null) {
      this.jdField_a_of_type_ComTencentMttExternalReaderInternalScreenLockController = new ScreenLockController(this.jdField_a_of_type_AndroidContentContext);
    }
    this.jdField_a_of_type_ComTencentMttExternalReaderInternalScreenLockController.setScreenLockTime(paramInt);
  }
  
  public void setScrollRatio(float paramFloat)
  {
    ReaderScrollView localReaderScrollView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView;
    if (localReaderScrollView != null) {
      localReaderScrollView.setScrollRatio(paramFloat);
    }
  }
  
  public void setSelectViewHidden(boolean paramBoolean)
  {
    ReaderSelectView localReaderSelectView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView;
    if (localReaderSelectView != null) {
      localReaderSelectView.setSelectViewHidden(paramBoolean);
    }
  }
  
  public void showScrollBar()
  {
    ReaderScrollView localReaderScrollView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView;
    if (localReaderScrollView != null) {
      localReaderScrollView.showScrollBar();
    }
  }
  
  public void showSelectView(Rect paramRect, String paramString)
  {
    LogUtils.d("ReaderFeatureWrapper", "showSelectView ...");
    ReaderSelectView localReaderSelectView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderSelectView;
    if (localReaderSelectView != null) {
      localReaderSelectView.showSelectView(paramRect, paramString);
    }
  }
  
  public void showToastView(int paramInt)
  {
    ReaderToastView localReaderToastView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderToastView;
    if (localReaderToastView != null) {
      localReaderToastView.showToastView(paramInt);
    }
  }
  
  public void updateScreenLockTime()
  {
    ScreenLockController localScreenLockController = this.jdField_a_of_type_ComTencentMttExternalReaderInternalScreenLockController;
    if (localScreenLockController != null) {
      localScreenLockController.setSystemTime();
    }
  }
  
  public void updateScrollView(float paramFloat)
  {
    ReaderScrollView localReaderScrollView = this.jdField_a_of_type_ComTencentMttExternalReaderBaseReaderScrollView;
    if (localReaderScrollView != null) {
      localReaderScrollView.updateScrollView(paramFloat);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\com\tencent\mtt\external\reader\base\ReaderFeatureWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */