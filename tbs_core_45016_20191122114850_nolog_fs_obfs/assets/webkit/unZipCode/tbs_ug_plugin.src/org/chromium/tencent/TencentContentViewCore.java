package org.chromium.tencent;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.content.browser.ContentViewCoreImpl;
import org.chromium.content.browser.JoystickHandler;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content.browser.TapDisambiguator;
import org.chromium.content.browser.input.ImeAdapterImpl;
import org.chromium.content.browser.input.SelectPopup;
import org.chromium.content.browser.selection.SelectionPopupControllerImpl;
import org.chromium.content.browser.webcontents.WebContentsImpl;
import org.chromium.content_public.browser.ContentViewCore.InternalAccessDelegate;
import org.chromium.content_public.browser.WebContents;
import org.chromium.tencent.content.browser.TencentSelectionPopupControllerImpl;
import org.chromium.tencent.content.browser.input.TencentImeAdapter;
import org.chromium.ui.base.ViewAndroidDelegate;
import org.chromium.ui.base.WindowAndroid;

public class TencentContentViewCore
  extends ContentViewCoreImpl
{
  private static final String TAG = "TencentContentViewCore";
  private static boolean gIsCommandLineInited = false;
  private static boolean isWebKitSharedTimersSuspended = false;
  private ContentViewClientExtension mContentViewClientExtension;
  
  public TencentContentViewCore(Context paramContext, String paramString)
  {
    super(paramContext, paramString);
  }
  
  @CalledByNative
  private void RenderFrameDeleted(long paramLong)
  {
    if (paramLong == this.mNativeSelectPopupSourceFrame) {
      this.mNativeSelectPopupSourceFrame = 0L;
    }
  }
  
  public static boolean isCommandLineInited()
  {
    return gIsCommandLineInited;
  }
  
  public static boolean isWebKitSharedTimersSuspended()
  {
    return isWebKitSharedTimersSuspended;
  }
  
  @CalledByNative
  private void onNoDomPropertyChangedInEvent(int paramInt)
  {
    ContentViewClientExtension localContentViewClientExtension = this.mContentViewClientExtension;
    if (localContentViewClientExtension != null)
    {
      boolean bool;
      if (paramInt > 0) {
        bool = true;
      } else {
        bool = false;
      }
      localContentViewClientExtension.onShowImageBrowser(bool);
    }
  }
  
  public static void setCommandLineInited()
  {
    gIsCommandLineInited = true;
  }
  
  public static void setWebKitSharedTimersSuspended(boolean paramBoolean)
  {
    isWebKitSharedTimersSuspended = paramBoolean;
  }
  
  public void closeDisambiguationPopup()
  {
    TapDisambiguator.fromWebContents(this.mWebContents).hidePopup(false);
  }
  
  protected boolean filterTapOrPressEvent(int paramInt1, int paramInt2, int paramInt3)
  {
    if ((paramInt1 == 5) && (offerLongPressToEmbedder())) {
      return true;
    }
    Object localObject = TapDisambiguator.fromWebContents(this.mWebContents);
    if (!((TapDisambiguator)localObject).isShowing()) {
      ((TapDisambiguator)localObject).setLastTouch(paramInt2, paramInt3);
    }
    if ((paramInt1 != 16) && (hasSelection()))
    {
      if (this.mWebContents != null) {
        this.mWebContents.collapseSelection();
      }
      localObject = this.mContentViewClientExtension;
      if (localObject != null) {
        ((ContentViewClientExtension)localObject).unselect();
      }
      resetSelection();
      return true;
    }
    return false;
  }
  
  public void focusTtsNode(int paramInt, boolean paramBoolean)
  {
    if (this.mNativeContentViewCore != 0L) {
      nativeFocusTtsNode(this.mNativeContentViewCore, paramInt, paramBoolean);
    }
  }
  
  public ContentViewClientExtension getContentViewClientExtension()
  {
    return this.mContentViewClientExtension;
  }
  
  public float getMaxPageScaleFactor()
  {
    return this.mRenderCoordinates.getMaxPageScaleFactor();
  }
  
  public float getMinPageScaleFactor()
  {
    return this.mRenderCoordinates.getMinPageScaleFactor();
  }
  
  public float getPageScaleFactor()
  {
    return this.mRenderCoordinates.getPageScaleFactor();
  }
  
  public TencentImeAdapter getTencentImeAdapter()
  {
    ImeAdapterImpl localImeAdapterImpl = ImeAdapterImpl.fromWebContents(this.mWebContents);
    if ((localImeAdapterImpl != null) && ((localImeAdapterImpl instanceof TencentImeAdapter))) {
      return (TencentImeAdapter)localImeAdapterImpl;
    }
    return null;
  }
  
  public TencentSelectionPopupControllerImpl getTencentSelectionPopupController()
  {
    SelectionPopupControllerImpl localSelectionPopupControllerImpl = SelectionPopupControllerImpl.fromWebContents(this.mWebContents);
    if ((localSelectionPopupControllerImpl != null) && ((localSelectionPopupControllerImpl instanceof TencentSelectionPopupControllerImpl))) {
      return (TencentSelectionPopupControllerImpl)localSelectionPopupControllerImpl;
    }
    return null;
  }
  
  public void getTtsTextAsync(int paramInt)
  {
    if (this.mNativeContentViewCore != 0L) {
      nativeGetTtsTextAsync(this.mNativeContentViewCore, paramInt);
    }
  }
  
  public String getUrl()
  {
    return null;
  }
  
  @VisibleForTesting
  public boolean hasSelection()
  {
    TencentSelectionPopupControllerImpl localTencentSelectionPopupControllerImpl = getTencentSelectionPopupController();
    if (localTencentSelectionPopupControllerImpl != null) {
      return localTencentSelectionPopupControllerImpl.hasSelection();
    }
    return false;
  }
  
  public void initialize(ViewAndroidDelegate paramViewAndroidDelegate, ContentViewCore.InternalAccessDelegate paramInternalAccessDelegate, WebContents paramWebContents, WindowAndroid paramWindowAndroid)
  {
    super.initialize(paramViewAndroidDelegate, paramInternalAccessDelegate, paramWebContents, paramWindowAndroid);
    paramViewAndroidDelegate = getTencentSelectionPopupController();
    if (paramViewAndroidDelegate != null) {
      paramViewAndroidDelegate.cloneRenderCoordinates(this.mRenderCoordinates);
    }
  }
  
  public boolean isNightMode()
  {
    ContentViewClientExtension localContentViewClientExtension = this.mContentViewClientExtension;
    if (localContentViewClientExtension != null) {
      return localContentViewClientExtension.isNightMode();
    }
    return false;
  }
  
  public boolean isPopupZoomerView(View paramView)
  {
    TapDisambiguator localTapDisambiguator = TapDisambiguator.fromWebContents(this.mWebContents);
    return (localTapDisambiguator.isShowing()) && (localTapDisambiguator.isPopupZoomerView(paramView));
  }
  
  public boolean isSelectionEditable()
  {
    TencentSelectionPopupControllerImpl localTencentSelectionPopupControllerImpl = getTencentSelectionPopupController();
    boolean bool = false;
    if (localTencentSelectionPopupControllerImpl != null)
    {
      if (hasSelection()) {
        bool = localTencentSelectionPopupControllerImpl.isFocusedNodeEditable();
      }
      return bool;
    }
    return false;
  }
  
  public void onFocusChanged(boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((this.mHasViewFocus != null) && (this.mHasViewFocus.booleanValue() == paramBoolean1)) {
      return;
    }
    if ((paramBoolean1) && (this.mPaused)) {
      return;
    }
    this.mHasViewFocus = Boolean.valueOf(paramBoolean1);
    if (this.mWebContents == null) {
      return;
    }
    ImeAdapterImpl.fromWebContents(this.mWebContents).onViewFocusChanged(paramBoolean1, paramBoolean2);
    JoystickHandler localJoystickHandler = JoystickHandler.fromWebContents(this.mWebContents);
    if ((paramBoolean1) && (!getTencentSelectionPopupController().isFocusedNodeEditable())) {
      paramBoolean2 = true;
    } else {
      paramBoolean2 = false;
    }
    localJoystickHandler.setScrollEnabled(paramBoolean2);
    if ((!getTencentSelectionPopupController().hasSelection()) && (!paramBoolean1))
    {
      cancelRequestToScrollFocusedEditableNodeIntoView();
      if (this.mPreserveSelectionOnNextLossOfFocus)
      {
        this.mPreserveSelectionOnNextLossOfFocus = false;
        hidePopupsAndPreserveSelection();
      }
      else
      {
        hidePopupsAndClearSelection();
        getTencentSelectionPopupController().clearSelection();
      }
    }
    if (this.mNativeContentViewCore != 0L) {
      nativeSetFocus(this.mNativeContentViewCore, paramBoolean1);
    }
  }
  
  public void onHide()
  {
    hidePopupsAndPreserveSelection();
    this.mWebContents.onHide();
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        if (TencentContentViewCore.this.mContentViewClientExtension != null) {
          TencentContentViewCore.this.mContentViewClientExtension.onHide();
        }
      }
    });
  }
  
  public void onRotationChanged(int paramInt)
  {
    super.onRotationChanged(paramInt);
    ImeAdapterImpl localImeAdapterImpl = ImeAdapterImpl.fromWebContents(this.mWebContents);
    if (localImeAdapterImpl != null) {
      localImeAdapterImpl.resetAndHideKeyboard();
    }
  }
  
  public void onShow()
  {
    this.mWebContents.onShow();
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        if (TencentContentViewCore.this.mContentViewClientExtension != null) {
          TencentContentViewCore.this.mContentViewClientExtension.onShow();
        }
      }
    });
  }
  
  public void onSoftKeyBoardShow()
  {
    this.mContentViewClientExtension.onSoftKeyBoardShow();
  }
  
  public boolean performContextMenuActionPaste()
  {
    ContentViewClientExtension localContentViewClientExtension = this.mContentViewClientExtension;
    if (localContentViewClientExtension != null) {
      return localContentViewClientExtension.performContextMenuActionPaste();
    }
    return false;
  }
  
  public void resetSelection()
  {
    TencentSelectionPopupControllerImpl localTencentSelectionPopupControllerImpl = getTencentSelectionPopupController();
    if (localTencentSelectionPopupControllerImpl != null) {
      localTencentSelectionPopupControllerImpl.resetSelection();
    }
  }
  
  public void selectPopupMenuItems(int[] paramArrayOfInt)
  {
    if (this.mNativeSelectPopupSourceFrame != 0L) {
      super.selectPopupMenuItems(paramArrayOfInt);
    }
  }
  
  public void setContentViewClientExtension(ContentViewClientExtension paramContentViewClientExtension)
  {
    this.mContentViewClientExtension = paramContentViewClientExtension;
    ImeAdapterImpl localImeAdapterImpl = ImeAdapterImpl.fromWebContents(this.mWebContents);
    if ((localImeAdapterImpl != null) && (localImeAdapterImpl.getListener() != null)) {
      localImeAdapterImpl.getListener().setContentViewClientExtension(paramContentViewClientExtension);
    }
  }
  
  public void setImeAdapterListener(ImeAdapterListener paramImeAdapterListener)
  {
    ImeAdapterImpl localImeAdapterImpl = ImeAdapterImpl.fromWebContents(this.mWebContents);
    if (localImeAdapterImpl != null) {
      localImeAdapterImpl.setListener(paramImeAdapterListener);
    }
  }
  
  public void setSiteSecurityInfo(String paramString1, String paramString2)
  {
    if (this.mNativeContentViewCore != 0L) {
      nativesetSiteSecurityInfo(this.mNativeContentViewCore, paramString1, paramString2);
    }
  }
  
  public void showInputMethodExtBar(boolean paramBoolean, int paramInt)
  {
    ImeAdapterImpl localImeAdapterImpl = ImeAdapterImpl.fromWebContents(this.mWebContents);
    if ((localImeAdapterImpl != null) && (localImeAdapterImpl.getListener() != null)) {
      localImeAdapterImpl.getListener().showInputMethodExtBar(paramBoolean, paramInt);
    }
  }
  
  protected void showSelectPopup(View paramView, long paramLong, String[] paramArrayOfString, int[] paramArrayOfInt1, boolean paramBoolean1, int[] paramArrayOfInt2, boolean paramBoolean2)
  {
    if ((this.mContainerView.getParent() != null) && (this.mContainerView.getVisibility() == 0))
    {
      this.mSelectPopup = new EmptySelectPopup();
      this.mNativeSelectPopupSourceFrame = paramLong;
      this.mSelectPopup.show();
      try
      {
        this.mContentViewClientExtension.onShowSelectPopup(null, paramArrayOfString, paramArrayOfInt1, paramBoolean1, paramArrayOfInt2);
        return;
      }
      catch (Exception paramView)
      {
        paramArrayOfString = new StringBuilder();
        paramArrayOfString.append("showSelectPopup exception: ");
        paramArrayOfString.append(paramView);
        Log.e("TencentContentViewCore", paramArrayOfString.toString());
        paramArrayOfString = SmttServiceClientProxy.getInstance();
        paramArrayOfInt1 = new StringBuilder();
        paramArrayOfInt1.append("Resource not found: ");
        paramArrayOfInt1.append(paramView);
        paramArrayOfString.reportTbsError(3004, paramArrayOfInt1.toString());
        return;
      }
    }
    this.mNativeSelectPopupSourceFrame = paramLong;
    selectPopupMenuItems(null);
  }
  
  protected void updateFrameInfo(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, float paramFloat7, float paramFloat8, float paramFloat9, float paramFloat10, boolean paramBoolean1, boolean paramBoolean2)
  {
    float f = this.mRenderCoordinates.getPageScaleFactor();
    int k = 1;
    int i;
    if (paramFloat3 != f) {
      i = 1;
    } else {
      i = 0;
    }
    int j = k;
    if (i == 0)
    {
      j = k;
      if (paramFloat1 == this.mRenderCoordinates.getScrollX()) {
        if (paramFloat2 != this.mRenderCoordinates.getScrollY()) {
          j = k;
        } else {
          j = 0;
        }
      }
    }
    Object localObject = ImeAdapterImpl.fromWebContents(this.mWebContents);
    if ((localObject != null) && (((ImeAdapterImpl)localObject).getListener() != null) && (((ImeAdapterImpl)localObject).getListener().softKeyBoardOnHiding()))
    {
      ((ImeAdapterImpl)localObject).getListener().setSoftKeyBoardOnHiding(false);
      localObject = this.mContentViewClientExtension;
      if (localObject != null) {
        ((ContentViewClientExtension)localObject).onSoftKeyBoardIsShowing(false);
      }
    }
    if (j != 0)
    {
      localObject = this.mContentViewClientExtension;
      if (localObject != null) {
        ((ContentViewClientExtension)localObject).onScrollChanged();
      }
    }
    localObject = getTencentSelectionPopupController();
    if (localObject != null) {
      ((TencentSelectionPopupControllerImpl)localObject).cloneRenderCoordinates(this.mRenderCoordinates);
    }
    localObject = this.mContentViewClientExtension;
    if (localObject != null) {
      ((ContentViewClientExtension)localObject).onTopControlsChanged(paramFloat10);
    }
    super.updateFrameInfo(paramFloat1, paramFloat2, paramFloat3, paramFloat4, paramFloat5, paramFloat6, paramFloat7, paramFloat8, paramFloat9, paramFloat10, paramBoolean1, paramBoolean2);
    localObject = this.mContentViewClientExtension;
    if (localObject != null) {
      ((ContentViewClientExtension)localObject).updateForceScaleStatus(this.mRenderCoordinates.getMinPageScaleFactor(), this.mRenderCoordinates.getMaxPageScaleFactor());
    }
  }
  
  public void updateHitTestEditable(String paramString)
  {
    ContentViewClientExtension localContentViewClientExtension = this.mContentViewClientExtension;
    if (localContentViewClientExtension != null) {
      localContentViewClientExtension.updateHitTestEditable(paramString);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\TencentContentViewCore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */