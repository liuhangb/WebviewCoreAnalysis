package org.chromium.content.browser.selection;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.ClipData.Item;
import android.content.ClipDescription;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.ParagraphStyle;
import android.text.style.UpdateAppearance;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.view.WindowManager.BadTokenException;
import android.view.textclassifier.TextClassifier;
import java.lang.ref.WeakReference;
import java.util.List;
import org.chromium.base.ApiCompatibilityUtils;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.base.metrics.RecordUserAction;
import org.chromium.content.browser.ContentClassFactory;
import org.chromium.content.browser.RenderCoordinates;
import org.chromium.content.browser.WindowAndroidChangedObserver;
import org.chromium.content.browser.WindowEventObserver;
import org.chromium.content.browser.webcontents.WebContentsImpl;
import org.chromium.content.browser.webcontents.WebContentsUserData;
import org.chromium.content.browser.webcontents.WebContentsUserData.UserDataFactory;
import org.chromium.content_public.browser.ActionModeCallbackHelper;
import org.chromium.content_public.browser.ImeEventObserver;
import org.chromium.content_public.browser.SelectionClient;
import org.chromium.content_public.browser.SelectionClient.Result;
import org.chromium.content_public.browser.SelectionClient.ResultCallback;
import org.chromium.content_public.browser.SelectionPopupController;
import org.chromium.content_public.browser.WebContents;
import org.chromium.tencent.content.browser.TencentSelectionPopupControllerImpl;
import org.chromium.ui.base.WindowAndroid;
import org.chromium.ui.base.WindowAndroid.IntentCallback;

@JNINamespace("content")
@TargetApi(23)
public class SelectionPopupControllerImpl
  extends ActionModeCallbackHelper
  implements WindowAndroidChangedObserver, WindowEventObserver, ImeEventObserver, SelectionPopupController
{
  private static final int MAX_SHARE_QUERY_LENGTH = 100000;
  private static final int MENU_ITEM_ORDER_TEXT_PROCESS_START = 100;
  private static final int SHOW_DELAY_MS = 300;
  private static final String TAG = "SelectionPopupCtlr";
  private MenuDescriptor mActionMenuDescriptor;
  private ActionMode mActionMode;
  private int mAllowedMenuItems;
  private ActionMode.Callback mCallback;
  private boolean mCanEditRichly;
  private boolean mCanSelectAllForPastePopup;
  private SelectionClient.Result mClassificationResult;
  private Context mContext;
  private boolean mEditable;
  private SelectionInsertionHandleObserver mHandleObserver;
  protected boolean mHasSelection;
  private boolean mHidden;
  private boolean mInitialized;
  protected boolean mIsInsertionForTesting;
  private boolean mIsPasswordType;
  private String mLastSelectedText;
  private int mLastSelectionOffset;
  private ActionMode.Callback mNonSelectionCallback;
  private PastePopupMenu mPastePopupMenu;
  private Runnable mRepeatingHideRunnable;
  private SelectionClient.ResultCallback mResultCallback;
  private boolean mScrollInProgress;
  private SelectionClient mSelectionClient;
  private SmartSelectionMetricsLogger mSelectionMetricsLogger;
  private final Rect mSelectionRect = new Rect();
  protected boolean mUnselectAllOnDismiss;
  private View mView;
  private boolean mWasPastePopupShowingOnInsertionDragStart;
  private WebContentsImpl mWebContents;
  private WindowAndroid mWindowAndroid;
  
  public SelectionPopupControllerImpl(WebContents paramWebContents)
  {
    this.mWebContents = ((WebContentsImpl)paramWebContents);
  }
  
  private boolean canPaste()
  {
    return ((ClipboardManager)this.mContext.getSystemService("clipboard")).hasPrimaryClip();
  }
  
  public static SelectionPopupControllerImpl create(Context paramContext, WindowAndroid paramWindowAndroid, WebContents paramWebContents, View paramView)
  {
    paramWebContents = (SelectionPopupControllerImpl)WebContentsUserData.fromWebContents(paramWebContents, SelectionPopupControllerImpl.class, UserDataFactoryLazyHolder.a());
    paramWebContents.init(paramContext, paramWindowAndroid, paramView, true);
    return paramWebContents;
  }
  
  private void createActionMenu(ActionMode paramActionMode, Menu paramMenu)
  {
    initializeMenu(this.mContext, paramActionMode, paramMenu);
    this.mActionMenuDescriptor = createActionMenuDescriptor();
    this.mActionMenuDescriptor.apply(paramMenu);
    setPasteAsPlainTextMenuItemTitle(paramMenu);
    if (hasSelection())
    {
      if (isSelectionPassword()) {
        return;
      }
      initializeTextProcessingMenu(paramMenu);
      return;
    }
  }
  
  private MenuDescriptor createActionMenuDescriptor()
  {
    MenuDescriptor localMenuDescriptor = new MenuDescriptor();
    updateAssistMenuItem(localMenuDescriptor);
    return localMenuDescriptor;
  }
  
  private void createAndShowPastePopup()
  {
    if (this.mView.getParent() != null)
    {
      if (this.mView.getVisibility() != 0) {
        return;
      }
      if ((!supportsFloatingActionMode()) && (!canPaste()) && (this.mNonSelectionCallback == null)) {
        return;
      }
      destroyPastePopup();
      PastePopupMenu.PastePopupMenuDelegate local2 = new PastePopupMenu.PastePopupMenuDelegate()
      {
        public boolean canPaste()
        {
          return SelectionPopupControllerImpl.this.canPaste();
        }
        
        public boolean canPasteAsPlainText()
        {
          return SelectionPopupControllerImpl.this.canPasteAsPlainText();
        }
        
        public boolean canSelectAll()
        {
          return SelectionPopupControllerImpl.this.canSelectAll();
        }
        
        public void paste()
        {
          SelectionPopupControllerImpl.this.paste();
          SelectionPopupControllerImpl.this.mWebContents.dismissTextHandles();
        }
        
        public void pasteAsPlainText()
        {
          SelectionPopupControllerImpl.this.pasteAsPlainText();
          SelectionPopupControllerImpl.this.mWebContents.dismissTextHandles();
        }
        
        public void selectAll()
        {
          SelectionPopupControllerImpl.this.selectAll();
        }
      };
      Context localContext = (Context)this.mWindowAndroid.getContext().get();
      if (localContext == null) {
        return;
      }
      if (supportsFloatingActionMode()) {
        this.mPastePopupMenu = new FloatingPastePopupMenu(localContext, this.mView, local2, this.mNonSelectionCallback);
      } else {
        this.mPastePopupMenu = new LegacyPastePopupMenu(localContext, this.mView, local2);
      }
      showPastePopup();
      return;
    }
  }
  
  public static SelectionPopupControllerImpl createForTesting(Context paramContext, WindowAndroid paramWindowAndroid, WebContents paramWebContents, View paramView)
  {
    paramWebContents = new SelectionPopupControllerImpl(paramWebContents);
    paramWebContents.init(paramContext, paramWindowAndroid, paramView, false);
    return paramWebContents;
  }
  
  @TargetApi(23)
  private static Intent createProcessTextIntent()
  {
    return new Intent().setAction("android.intent.action.PROCESS_TEXT").setType("text/plain");
  }
  
  @TargetApi(23)
  private Intent createProcessTextIntentForResolveInfo(ResolveInfo paramResolveInfo)
  {
    boolean bool = isFocusedNodeEditable();
    return createProcessTextIntent().putExtra("android.intent.extra.PROCESS_TEXT_READONLY", bool ^ true).setClassName(paramResolveInfo.activityInfo.packageName, paramResolveInfo.activityInfo.name);
  }
  
  @CalledByNative
  private static Rect createRect(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    return new Rect(paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  public static SelectionPopupControllerImpl fromWebContents(WebContents paramWebContents)
  {
    return (SelectionPopupControllerImpl)WebContentsUserData.fromWebContents(paramWebContents, SelectionPopupControllerImpl.class, null);
  }
  
  private int getActionType(int paramInt)
  {
    if (paramInt == 16908353) {
      return 105;
    }
    return 108;
  }
  
  @CalledByNative
  private Context getContext()
  {
    return this.mContext;
  }
  
  private long getDefaultHideDuration()
  {
    if (supportsFloatingActionMode()) {
      return ViewConfiguration.getDefaultActionModeHideDuration();
    }
    return 2000L;
  }
  
  private float getDeviceScaleFactor()
  {
    return this.mWebContents.getRenderCoordinates().getDeviceScaleFactor();
  }
  
  private Rect getSelectionRectRelativeToContainingView()
  {
    float f = getDeviceScaleFactor();
    Rect localRect = new Rect((int)(this.mSelectionRect.left * f), (int)(this.mSelectionRect.top * f), (int)(this.mSelectionRect.right * f), (int)(this.mSelectionRect.bottom * f));
    localRect.offset(0, (int)this.mWebContents.getRenderCoordinates().getContentOffsetYPix());
    return localRect;
  }
  
  private boolean hasStyleSpan(Spanned paramSpanned)
  {
    Class[] arrayOfClass = new Class[3];
    arrayOfClass[0] = CharacterStyle.class;
    arrayOfClass[1] = ParagraphStyle.class;
    arrayOfClass[2] = UpdateAppearance.class;
    int j = arrayOfClass.length;
    int i = 0;
    while (i < j)
    {
      Class localClass = arrayOfClass[i];
      if (paramSpanned.nextSpanTransition(-1, paramSpanned.length(), localClass) < paramSpanned.length()) {
        return true;
      }
      i += 1;
    }
    return false;
  }
  
  private void hideActionMode(boolean paramBoolean)
  {
    if (!isFloatingActionMode()) {
      return;
    }
    if (this.mHidden == paramBoolean) {
      return;
    }
    this.mHidden = paramBoolean;
    if (this.mHidden)
    {
      this.mRepeatingHideRunnable.run();
      return;
    }
    this.mView.removeCallbacks(this.mRepeatingHideRunnable);
    hideActionModeTemporarily(300L);
  }
  
  private void hideActionModeTemporarily(long paramLong)
  {
    if ((Build.VERSION.SDK_INT >= 23) && (isActionModeValid())) {
      this.mActionMode.hide(paramLong);
    }
  }
  
  private void init(Context paramContext, WindowAndroid paramWindowAndroid, View paramView, boolean paramBoolean)
  {
    this.mContext = paramContext;
    this.mWindowAndroid = paramWindowAndroid;
    this.mView = paramView;
    this.mAllowedMenuItems = 7;
    this.mRepeatingHideRunnable = new Runnable()
    {
      public void run()
      {
        if ((!jdField_a_of_type_Boolean) && (!SelectionPopupControllerImpl.this.mHidden)) {
          throw new AssertionError();
        }
        long l = SelectionPopupControllerImpl.this.getDefaultHideDuration();
        SelectionPopupControllerImpl.this.mView.postDelayed(SelectionPopupControllerImpl.this.mRepeatingHideRunnable, l - 1L);
        SelectionPopupControllerImpl.this.hideActionModeTemporarily(l);
      }
    };
    this.mResultCallback = new SmartSelectionCallback(null);
    this.mLastSelectedText = "";
    this.mHandleObserver = ContentClassFactory.get().createHandleObserver(paramView);
    if (paramBoolean) {
      nativeInit(this.mWebContents);
    }
    this.mInitialized = true;
  }
  
  public static void initializeMenu(Context paramContext, ActionMode paramActionMode, Menu paramMenu) {}
  
  private void initializeTextProcessingMenu(Menu paramMenu) {}
  
  private boolean initialized()
  {
    return this.mInitialized;
  }
  
  private boolean isActionModeSupported()
  {
    return this.mCallback != EMPTY_CALLBACK;
  }
  
  private boolean isFloatingActionMode()
  {
    return (supportsFloatingActionMode()) && (isActionModeValid()) && (this.mActionMode.getType() == 1);
  }
  
  private boolean isIncognito()
  {
    return this.mWebContents.isIncognito();
  }
  
  private boolean isSelectActionModeAllowed(int paramInt)
  {
    int i = this.mAllowedMenuItems;
    boolean bool3 = false;
    boolean bool1;
    if ((i & paramInt) != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if (paramInt == 1)
    {
      boolean bool2 = bool3;
      if (bool1)
      {
        bool2 = bool3;
        if (isShareAvailable()) {
          bool2 = true;
        }
      }
      return bool2;
    }
    return bool1;
  }
  
  private boolean isShareAvailable()
  {
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.setType("text/plain");
    return this.mContext.getPackageManager().queryIntentActivities(localIntent, 65536).size() > 0;
  }
  
  private native void nativeInit(WebContents paramWebContents);
  
  private boolean needsActionMenuUpdate()
  {
    return createActionMenuDescriptor().equals(this.mActionMenuDescriptor) ^ true;
  }
  
  @CalledByNative
  private void onSelectWordAroundCaretAck(boolean paramBoolean, int paramInt1, int paramInt2)
  {
    SelectionClient localSelectionClient = this.mSelectionClient;
    if (localSelectionClient != null) {
      localSelectionClient.selectWordAroundCaretAck(paramBoolean, paramInt1, paramInt2);
    }
  }
  
  @CalledByNative
  private void onSelectionChanged(String paramString)
  {
    if ((paramString.length() == 0) && (hasSelection()))
    {
      localObject = this.mSelectionMetricsLogger;
      if (localObject != null) {
        ((SmartSelectionMetricsLogger)localObject).logSelectionAction(this.mLastSelectedText, this.mLastSelectionOffset, 107, null);
      }
    }
    this.mLastSelectedText = paramString;
    Object localObject = this.mSelectionClient;
    if (localObject != null) {
      ((SelectionClient)localObject).onSelectionChanged(paramString);
    }
  }
  
  @CalledByNative
  private void onShowUnhandledTapUIIfNeeded(int paramInt1, int paramInt2)
  {
    if ((paramInt1 >= 0) && (paramInt2 >= 0) && (this.mView.getWidth() >= paramInt1))
    {
      if (this.mView.getHeight() < paramInt2) {
        return;
      }
      SelectionClient localSelectionClient = this.mSelectionClient;
      if (localSelectionClient != null) {
        localSelectionClient.showUnhandledTapUIIfNeeded(paramInt1, paramInt2);
      }
      return;
    }
  }
  
  private void processText(Intent paramIntent)
  {
    RecordUserAction.record("MobileActionMode.ProcessTextIntent");
    String str = sanitizeQuery(getSelectedText(), 1000);
    if (TextUtils.isEmpty(str)) {
      return;
    }
    paramIntent.putExtra("android.intent.extra.PROCESS_TEXT", str);
    try
    {
      this.mWindowAndroid.showIntent(paramIntent, new WindowAndroid.IntentCallback()
      {
        public void onIntentCompleted(WindowAndroid paramAnonymousWindowAndroid, int paramAnonymousInt, Intent paramAnonymousIntent)
        {
          SelectionPopupControllerImpl.this.onReceivedProcessTextResult(paramAnonymousInt, paramAnonymousIntent);
        }
      }, null);
      return;
    }
    catch (ActivityNotFoundException paramIntent) {}
  }
  
  public static String sanitizeQuery(String paramString, int paramInt)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (paramString.length() < paramInt) {
        return paramString;
      }
      StringBuilder localStringBuilder = new StringBuilder();
      localStringBuilder.append(paramString.substring(0, paramInt));
      localStringBuilder.append("…");
      return localStringBuilder.toString();
    }
    return paramString;
  }
  
  @TargetApi(26)
  public static void setPasteAsPlainTextMenuItemTitle(Menu paramMenu) {}
  
  private void showPastePopup()
  {
    try
    {
      this.mPastePopupMenu.show(getSelectionRectRelativeToContainingView());
      return;
    }
    catch (WindowManager.BadTokenException localBadTokenException) {}
  }
  
  private void updateAssistMenuItem(MenuDescriptor paramMenuDescriptor) {}
  
  @VisibleForTesting
  public boolean canPasteAsPlainText()
  {
    if (Build.VERSION.SDK_INT < 26) {
      return false;
    }
    if (!this.mCanEditRichly) {
      return false;
    }
    Object localObject1 = (ClipboardManager)this.mContext.getSystemService("clipboard");
    if (!((ClipboardManager)localObject1).hasPrimaryClip()) {
      return false;
    }
    Object localObject2 = ((ClipboardManager)localObject1).getPrimaryClip();
    localObject1 = ((ClipData)localObject2).getDescription();
    localObject2 = ((ClipData)localObject2).getItemAt(0).getText();
    if ((((ClipDescription)localObject1).hasMimeType("text/plain")) && ((localObject2 instanceof Spanned)) && (hasStyleSpan((Spanned)localObject2))) {
      return true;
    }
    return ((ClipDescription)localObject1).hasMimeType("text/html");
  }
  
  @VisibleForTesting
  public boolean canSelectAll()
  {
    return this.mCanSelectAllForPastePopup;
  }
  
  public void clearSelection()
  {
    if (this.mWebContents != null)
    {
      if (!isActionModeSupported()) {
        return;
      }
      this.mWebContents.collapseSelection();
      this.mClassificationResult = null;
      return;
    }
  }
  
  @VisibleForTesting
  public void copy()
  {
    this.mWebContents.copy();
  }
  
  @VisibleForTesting
  public void cut()
  {
    this.mWebContents.cut();
  }
  
  public void destroyActionModeAndKeepSelection()
  {
    this.mUnselectAllOnDismiss = false;
    finishActionMode();
  }
  
  public void destroyActionModeAndUnselect()
  {
    this.mUnselectAllOnDismiss = true;
    finishActionMode();
  }
  
  public void destroyPastePopup()
  {
    if (isPastePopupShowing())
    {
      this.mPastePopupMenu.hide();
      this.mPastePopupMenu = null;
    }
  }
  
  public void destroySelectActionMode()
  {
    finishActionMode();
  }
  
  @VisibleForTesting
  void doAssistAction()
  {
    Object localObject = this.mClassificationResult;
    if (localObject != null)
    {
      if (!((SelectionClient.Result)localObject).a()) {
        return;
      }
      if (this.mClassificationResult.jdField_a_of_type_AndroidViewView$OnClickListener != null)
      {
        this.mClassificationResult.jdField_a_of_type_AndroidViewView$OnClickListener.onClick(this.mView);
        return;
      }
      if (this.mClassificationResult.jdField_a_of_type_AndroidContentIntent != null)
      {
        localObject = (Context)this.mWindowAndroid.getContext().get();
        if (localObject == null) {
          return;
        }
        ((Context)localObject).startActivity(this.mClassificationResult.jdField_a_of_type_AndroidContentIntent);
        return;
      }
      return;
    }
  }
  
  public void finishActionMode()
  {
    this.mHidden = false;
    View localView = this.mView;
    if (localView != null) {
      localView.removeCallbacks(this.mRepeatingHideRunnable);
    }
    if (isActionModeValid())
    {
      this.mActionMode.finish();
      this.mActionMode = null;
      this.mActionMenuDescriptor = null;
    }
  }
  
  public ActionModeCallbackHelper getActionModeCallbackHelper()
  {
    return this;
  }
  
  public SelectionClient.Result getClassificationResult()
  {
    return this.mClassificationResult;
  }
  
  public TextClassifier getCustomTextClassifier()
  {
    SelectionClient localSelectionClient = getSelectionClient();
    if (localSelectionClient == null) {
      return null;
    }
    return localSelectionClient.getCustomTextClassifier();
  }
  
  public SelectionClient.ResultCallback getResultCallback()
  {
    return this.mResultCallback;
  }
  
  public String getSelectedText()
  {
    return this.mLastSelectedText;
  }
  
  public SelectionClient getSelectionClient()
  {
    return this.mSelectionClient;
  }
  
  public TextClassifier getTextClassifier()
  {
    SelectionClient localSelectionClient = getSelectionClient();
    if (localSelectionClient == null) {
      return null;
    }
    return localSelectionClient.getTextClassifier();
  }
  
  public boolean hasSelection()
  {
    return this.mHasSelection;
  }
  
  public void invalidateContentRect()
  {
    if ((supportsFloatingActionMode()) && (isActionModeValid())) {
      this.mActionMode.invalidateContentRect();
    }
  }
  
  public boolean isActionModeValid()
  {
    return this.mActionMode != null;
  }
  
  public boolean isFocusedNodeEditable()
  {
    return this.mEditable;
  }
  
  @VisibleForTesting
  public boolean isInsertionForTesting()
  {
    return this.mIsInsertionForTesting;
  }
  
  @VisibleForTesting
  public boolean isPastePopupShowing()
  {
    return this.mPastePopupMenu != null;
  }
  
  public boolean isSelectActionBarShowing()
  {
    return isActionModeValid();
  }
  
  @VisibleForTesting
  public boolean isSelectionPassword()
  {
    return this.mIsPasswordType;
  }
  
  public boolean onActionItemClicked(ActionMode paramActionMode, MenuItem paramMenuItem)
  {
    return true;
  }
  
  public void onAttachedToWindow() {}
  
  public void onBeforeSendKeyEvent(KeyEvent paramKeyEvent) {}
  
  public void onCreateActionMode(ActionMode paramActionMode, Menu paramMenu)
  {
    paramActionMode.setSubtitle(null);
    createActionMenu(paramActionMode, paramMenu);
  }
  
  public void onDestroyActionMode()
  {
    this.mActionMode = null;
    this.mActionMenuDescriptor = null;
    if (this.mUnselectAllOnDismiss)
    {
      this.mWebContents.dismissTextHandles();
      clearSelection();
    }
  }
  
  public void onDetachedFromWindow() {}
  
  @VisibleForTesting
  @CalledByNative
  protected void onDragUpdate(float paramFloat1, float paramFloat2)
  {
    if (this.mHandleObserver != null)
    {
      float f1 = getDeviceScaleFactor();
      float f2 = this.mWebContents.getRenderCoordinates().getContentOffsetYPix();
      this.mHandleObserver.handleDragStartedOrMoved(paramFloat1 * f1, paramFloat2 * f1 + f2);
    }
  }
  
  public void onGetContentRect(ActionMode paramActionMode, View paramView, Rect paramRect)
  {
    paramRect.set(getSelectionRectRelativeToContainingView());
  }
  
  public void onImeEvent() {}
  
  public void onNodeAttributeUpdated(boolean paramBoolean1, boolean paramBoolean2)
  {
    updateSelectionState(paramBoolean1, paramBoolean2);
  }
  
  public boolean onPrepareActionMode(ActionMode paramActionMode, Menu paramMenu)
  {
    createActionMenu(paramActionMode, paramMenu);
    return true;
  }
  
  public void onReceivedProcessTextResult(int paramInt, Intent paramIntent)
  {
    if ((this.mWebContents != null) && (paramInt == -1))
    {
      if (paramIntent == null) {
        return;
      }
      if (hasSelection())
      {
        if (!isFocusedNodeEditable()) {
          return;
        }
        paramIntent = paramIntent.getCharSequenceExtra("android.intent.extra.PROCESS_TEXT");
        if (paramIntent != null) {
          this.mWebContents.replace(paramIntent.toString());
        }
        return;
      }
      return;
    }
  }
  
  @VisibleForTesting
  @CalledByNative
  protected void onSelectionBoundsEvent(int paramInt, Rect paramRect1, Rect paramRect2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4) {}
  
  @VisibleForTesting
  @CalledByNative
  protected void onSelectionEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
    int i = paramInt4;
    if (paramInt2 == paramInt4) {
      i = paramInt4 + 1;
    }
    paramInt4 = paramInt5;
    if (paramInt3 == paramInt5) {
      paramInt4 = paramInt5 + 1;
    }
    Object localObject;
    switch (paramInt1)
    {
    default: 
      break;
    case 10: 
      if (this.mWasPastePopupShowingOnInsertionDragStart) {
        this.mWebContents.showContextMenuAtTouchHandle(this.mSelectionRect.left, this.mSelectionRect.bottom);
      }
      this.mWasPastePopupShowingOnInsertionDragStart = false;
      localObject = this.mHandleObserver;
      if (localObject != null) {
        ((SelectionInsertionHandleObserver)localObject).handleDragStopped();
      }
      break;
    case 9: 
      this.mWasPastePopupShowingOnInsertionDragStart = isPastePopupShowing();
      destroyPastePopup();
      break;
    case 8: 
      destroyPastePopup();
      this.mIsInsertionForTesting = false;
      if (!hasSelection()) {
        this.mSelectionRect.setEmpty();
      }
      break;
    case 7: 
      if (this.mWasPastePopupShowingOnInsertionDragStart) {
        destroyPastePopup();
      } else {
        this.mWebContents.showContextMenuAtTouchHandle(this.mSelectionRect.left, this.mSelectionRect.bottom);
      }
      this.mWasPastePopupShowingOnInsertionDragStart = false;
      break;
    case 6: 
      this.mSelectionRect.set(paramInt2, paramInt3, i, paramInt4);
      if ((!this.mScrollInProgress) && (isPastePopupShowing())) {
        showPastePopup();
      } else {
        destroyPastePopup();
      }
      break;
    case 5: 
      this.mSelectionRect.set(paramInt2, paramInt3, i, paramInt4);
      this.mIsInsertionForTesting = true;
      break;
    case 4: 
      this.mWebContents.showContextMenuAtTouchHandle(paramInt2, paramInt4);
      localObject = this.mHandleObserver;
      if (localObject != null) {
        ((SelectionInsertionHandleObserver)localObject).handleDragStopped();
      }
      break;
    case 3: 
      hideActionMode(true);
      break;
    case 2: 
      this.mLastSelectedText = "";
      this.mLastSelectionOffset = 0;
      this.mHasSelection = false;
      this.mUnselectAllOnDismiss = false;
      this.mSelectionRect.setEmpty();
      localObject = this.mSelectionClient;
      if (localObject != null) {
        ((SelectionClient)localObject).cancelAllRequests();
      }
      finishActionMode();
      break;
    case 1: 
      this.mSelectionRect.set(paramInt2, paramInt3, i, paramInt4);
      invalidateContentRect();
    }
    if (this.mSelectionClient != null)
    {
      float f = getDeviceScaleFactor();
      paramInt2 = (int)(this.mSelectionRect.left * f);
      paramInt3 = (int)(this.mSelectionRect.bottom * f);
      this.mSelectionClient.onSelectionEvent(paramInt1, paramInt2, paramInt3);
    }
  }
  
  public void onWindowAndroidChanged(WindowAndroid paramWindowAndroid)
  {
    this.mWindowAndroid = paramWindowAndroid;
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    if ((supportsFloatingActionMode()) && (isActionModeValid())) {
      this.mActionMode.onWindowFocusChanged(paramBoolean);
    }
  }
  
  @VisibleForTesting
  public void paste()
  {
    this.mWebContents.paste();
  }
  
  @VisibleForTesting
  void pasteAsPlainText()
  {
    this.mWebContents.pasteAsPlainText();
  }
  
  public void restoreSelectionPopupsIfNecessary()
  {
    if ((hasSelection()) && (!isActionModeValid())) {
      showActionModeOrClearOnFailure();
    }
  }
  
  @VisibleForTesting
  public void search()
  {
    RecordUserAction.record("MobileActionMode.WebSearch");
    String str = sanitizeQuery(getSelectedText(), 1000);
    if (TextUtils.isEmpty(str)) {
      return;
    }
    Intent localIntent = new Intent("android.intent.action.WEB_SEARCH");
    localIntent.putExtra("new_search", true);
    localIntent.putExtra("query", str);
    localIntent.putExtra("com.android.browser.application_id", this.mContext.getPackageName());
    localIntent.addFlags(268435456);
    try
    {
      this.mContext.startActivity(localIntent);
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException) {}
  }
  
  @VisibleForTesting
  public void selectAll()
  {
    this.mWebContents.selectAll();
    this.mClassificationResult = null;
    if (isFocusedNodeEditable())
    {
      RecordUserAction.record("MobileActionMode.SelectAllWasEditable");
      return;
    }
    RecordUserAction.record("MobileActionMode.SelectAllWasNonEditable");
  }
  
  public void setActionModeCallback(ActionMode.Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }
  
  public void setAllowedMenuItems(int paramInt)
  {
    this.mAllowedMenuItems = paramInt;
  }
  
  public void setContainerView(View paramView)
  {
    if (isActionModeValid()) {
      finishActionMode();
    }
    this.mUnselectAllOnDismiss = true;
    destroyPastePopup();
    this.mView = paramView;
  }
  
  public void setNonSelectionActionModeCallback(ActionMode.Callback paramCallback)
  {
    this.mNonSelectionCallback = paramCallback;
  }
  
  public void setScrollInProgress(boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mScrollInProgress = paramBoolean2;
    hideActionMode(paramBoolean1);
  }
  
  public void setSelectionClient(@Nullable SelectionClient paramSelectionClient)
  {
    this.mSelectionClient = paramSelectionClient;
    paramSelectionClient = this.mSelectionClient;
    if (paramSelectionClient != null) {
      this.mSelectionMetricsLogger = ((SmartSelectionMetricsLogger)paramSelectionClient.getSelectionMetricsLogger());
    }
    this.mClassificationResult = null;
  }
  
  @VisibleForTesting
  void setSelectionInsertionHandleObserver(@Nullable SelectionInsertionHandleObserver paramSelectionInsertionHandleObserver)
  {
    this.mHandleObserver = paramSelectionInsertionHandleObserver;
  }
  
  public void setTextClassifier(TextClassifier paramTextClassifier)
  {
    SelectionClient localSelectionClient = getSelectionClient();
    if (localSelectionClient != null) {
      localSelectionClient.setTextClassifier(paramTextClassifier);
    }
  }
  
  @VisibleForTesting
  public void share()
  {
    RecordUserAction.record("MobileActionMode.Share");
    Object localObject = sanitizeQuery(getSelectedText(), 100000);
    if (TextUtils.isEmpty((CharSequence)localObject)) {
      return;
    }
    Intent localIntent = new Intent("android.intent.action.SEND");
    localIntent.setType("text/plain");
    localIntent.putExtra("android.intent.extra.TEXT", (String)localObject);
    try
    {
      localObject = Intent.createChooser(localIntent, "");
      ((Intent)localObject).setFlags(268435456);
      this.mContext.startActivity((Intent)localObject);
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException) {}
  }
  
  public void showActionModeOrClearOnFailure()
  {
    if (isActionModeSupported())
    {
      if (!hasSelection()) {
        return;
      }
      if ((!isActionModeValid()) || (isFloatingActionMode())) {}
    }
    try
    {
      this.mActionMode.invalidate();
      hideActionMode(false);
      return;
      destroyActionModeAndKeepSelection();
      ActionMode localActionMode;
      if (supportsFloatingActionMode()) {
        localActionMode = startFloatingActionMode();
      } else {
        localActionMode = this.mView.startActionMode(this.mCallback);
      }
      if (localActionMode != null) {
        LGEmailActionModeWorkaround.runIfNecessary(this.mContext, localActionMode);
      }
      this.mActionMode = localActionMode;
      this.mUnselectAllOnDismiss = true;
      if (!isActionModeValid()) {
        clearSelection();
      }
      return;
      return;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;) {}
    }
  }
  
  @VisibleForTesting
  @CalledByNative
  public void showSelectionMenu(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, boolean paramBoolean1, boolean paramBoolean2, String paramString, int paramInt6, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5, int paramInt7)
  {
    int i = paramInt4;
    if (supportsFloatingActionMode()) {
      i = paramInt4 + paramInt5;
    }
    this.mSelectionRect.set(paramInt1, paramInt2, paramInt3, i);
    this.mEditable = paramBoolean1;
    this.mLastSelectedText = paramString;
    this.mLastSelectionOffset = paramInt6;
    boolean bool;
    if (paramString.length() != 0) {
      bool = true;
    } else {
      bool = false;
    }
    this.mHasSelection = bool;
    this.mIsPasswordType = paramBoolean2;
    this.mCanSelectAllForPastePopup = paramBoolean3;
    this.mCanEditRichly = paramBoolean4;
    this.mUnselectAllOnDismiss = true;
    if (hasSelection())
    {
      paramInt1 = ApiCompatibilityUtils.isDeviceProvisioned(this.mContext) ^ true | isIncognito();
      if (paramInt1 == 0)
      {
        paramString = this.mSelectionMetricsLogger;
        if ((paramString != null) && (paramInt7 != 7)) {
          switch (paramInt7)
          {
          default: 
            paramString.logSelectionStarted(this.mLastSelectedText, this.mLastSelectionOffset, paramBoolean1);
            break;
          case 10: 
            paramString.logSelectionAction(this.mLastSelectedText, this.mLastSelectionOffset, 201, null);
            break;
          case 9: 
            paramString.logSelectionModified(this.mLastSelectedText, this.mLastSelectionOffset, this.mClassificationResult);
          }
        }
      }
      if ((paramInt1 == 0) && (paramInt7 == 9))
      {
        showActionModeOrClearOnFailure();
        return;
      }
      if (paramInt1 == 0)
      {
        paramString = this.mSelectionClient;
        if ((paramString != null) && (paramString.requestSelectionPopupUpdates(paramBoolean5))) {}
      }
      else
      {
        showActionModeOrClearOnFailure();
      }
    }
    else
    {
      createAndShowPastePopup();
    }
  }
  
  @TargetApi(23)
  protected ActionMode startFloatingActionMode()
  {
    return this.mView.startActionMode(new FloatingActionModeCallback(this, this.mCallback), 1);
  }
  
  public boolean supportsFloatingActionMode()
  {
    return Build.VERSION.SDK_INT >= 23;
  }
  
  public void updateSelectionState(boolean paramBoolean1, boolean paramBoolean2)
  {
    if (!paramBoolean1) {
      destroyPastePopup();
    }
    if ((paramBoolean1 != isFocusedNodeEditable()) || (paramBoolean2 != isSelectionPassword()))
    {
      this.mEditable = paramBoolean1;
      this.mIsPasswordType = paramBoolean2;
      if (isActionModeValid()) {
        this.mActionMode.invalidate();
      }
    }
  }
  
  private class SmartSelectionCallback
    implements SelectionClient.ResultCallback
  {
    private SmartSelectionCallback() {}
    
    public void onClassified(SelectionClient.Result paramResult)
    {
      if (!SelectionPopupControllerImpl.this.hasSelection())
      {
        SelectionPopupControllerImpl.access$902(SelectionPopupControllerImpl.this, null);
        return;
      }
      if ((paramResult.a <= 0) && (paramResult.b >= 0))
      {
        SelectionPopupControllerImpl.access$902(SelectionPopupControllerImpl.this, paramResult);
        if ((paramResult.a == 0) && (paramResult.b == 0))
        {
          if (SelectionPopupControllerImpl.this.mSelectionMetricsLogger != null) {
            SelectionPopupControllerImpl.this.mSelectionMetricsLogger.logSelectionModified(SelectionPopupControllerImpl.this.mLastSelectedText, SelectionPopupControllerImpl.this.mLastSelectionOffset, SelectionPopupControllerImpl.this.mClassificationResult);
          }
          SelectionPopupControllerImpl.this.showActionModeOrClearOnFailure();
          return;
        }
        SelectionPopupControllerImpl.this.mWebContents.adjustSelectionByCharacterOffset(paramResult.a, paramResult.b, true);
        return;
      }
      SelectionPopupControllerImpl.access$902(SelectionPopupControllerImpl.this, null);
      SelectionPopupControllerImpl.this.showActionModeOrClearOnFailure();
    }
  }
  
  private static final class UserDataFactoryLazyHolder
  {
    private static final WebContentsUserData.UserDataFactory<SelectionPopupControllerImpl> a = new WebContentsUserData.UserDataFactory()
    {
      public SelectionPopupControllerImpl create(WebContents paramAnonymousWebContents)
      {
        return new TencentSelectionPopupControllerImpl(paramAnonymousWebContents);
      }
    };
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\selection\SelectionPopupControllerImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */