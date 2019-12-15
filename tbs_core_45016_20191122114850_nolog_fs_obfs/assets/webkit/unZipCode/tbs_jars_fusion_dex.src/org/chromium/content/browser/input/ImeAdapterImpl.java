package org.chromium.content.browser.input;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.SuggestionSpan;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.InputConnection;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.TraceEvent;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.CalledByNative;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.WindowEventObserver;
import org.chromium.content.browser.webcontents.WebContentsUserData;
import org.chromium.content.browser.webcontents.WebContentsUserData.UserDataFactory;
import org.chromium.content_public.browser.ImeAdapter;
import org.chromium.content_public.browser.ImeEventObserver;
import org.chromium.content_public.browser.InputMethodManagerWrapper;
import org.chromium.content_public.browser.WebContents;
import org.chromium.tencent.ImeAdapterListener;
import org.chromium.tencent.X5InputDialogContainer;
import org.chromium.tencent.content.browser.input.TencentImeAdapter;
import org.chromium.ui.base.ViewUtils;

@JNINamespace("content")
public class ImeAdapterImpl
  extends ImeAdapter
  implements WindowEventObserver
{
  public static final int COMPOSITION_KEY_CODE = 229;
  protected static final boolean DEBUG_LOGS = false;
  private static final int DEFAULT_SUGGESTION_SPAN_COLOR = -2000107320;
  private static final int IME_FLAG_NO_PERSONALIZED_LEARNING = 16777216;
  private static final float SUGGESTION_HIGHLIGHT_BACKGROUND_TRANSPARENCY = 0.4F;
  private static final String TAG = "cr_Ime";
  protected View mContainerView;
  private Configuration mCurrentConfig;
  private CursorAnchorInfoController mCursorAnchorInfoController;
  protected final List<ImeEventObserver> mEventObservers = new ArrayList();
  private final Rect mFocusPreOSKViewportRect = new Rect();
  protected ChromiumBaseInputConnection mInputConnection;
  protected ChromiumBaseInputConnection.Factory mInputConnectionFactory;
  protected InputMethodManagerWrapper mInputMethodManagerWrapper;
  private boolean mIsConnected;
  protected int mLastCompositionEnd;
  protected int mLastCompositionStart;
  protected int mLastSelectionEnd;
  protected int mLastSelectionStart;
  protected String mLastText;
  ImeAdapterListener mListener;
  protected long mNativeImeAdapterAndroid;
  private boolean mNodeEditable;
  private boolean mNodePassword;
  private boolean mRestartInputOnNextStateUpdate;
  private ShowKeyboardResultReceiver mShowKeyboardResultReceiver;
  private int mTextInputFlags;
  private int mTextInputMode = 0;
  protected int mTextInputType = 0;
  protected final WebContents mWebContents;
  
  public ImeAdapterImpl(WebContents paramWebContents)
  {
    this.mWebContents = paramWebContents;
  }
  
  private void advanceFocusInForm(int paramInt)
  {
    long l = this.mNativeImeAdapterAndroid;
    if (l == 0L) {
      return;
    }
    nativeAdvanceFocusInForm(l, paramInt);
  }
  
  @CalledByNative
  private void cancelComposition()
  {
    if (this.mInputConnection != null) {
      restartInput();
    }
  }
  
  public static ImeAdapterImpl create(WebContents paramWebContents, View paramView, InputMethodManagerWrapper paramInputMethodManagerWrapper)
  {
    paramWebContents = (ImeAdapterImpl)WebContentsUserData.fromWebContents(paramWebContents, ImeAdapterImpl.class, UserDataFactoryLazyHolder.a());
    paramWebContents.init(paramView, paramInputMethodManagerWrapper);
    return paramWebContents;
  }
  
  public static InputMethodManagerWrapper createDefaultInputMethodManagerWrapper(Context paramContext)
  {
    return new InputMethodManagerWrapperImpl(paramContext);
  }
  
  private void createInputConnectionFactory()
  {
    if (this.mInputConnectionFactory != null) {
      return;
    }
    this.mInputConnectionFactory = new ThreadedInputConnectionFactory(this.mInputMethodManagerWrapper);
  }
  
  @CalledByNative
  private void destroy()
  {
    resetAndHideKeyboard();
    this.mNativeImeAdapterAndroid = 0L;
    this.mIsConnected = false;
    CursorAnchorInfoController localCursorAnchorInfoController = this.mCursorAnchorInfoController;
    if (localCursorAnchorInfoController != null) {
      localCursorAnchorInfoController.focusedNodeChanged(false);
    }
  }
  
  private boolean focusedNodeAllowsSoftKeyboard()
  {
    return (this.mTextInputType != 0) && (this.mTextInputMode != 1);
  }
  
  @CalledByNative
  private void focusedNodeChanged(boolean paramBoolean)
  {
    CursorAnchorInfoController localCursorAnchorInfoController = this.mCursorAnchorInfoController;
    if (localCursorAnchorInfoController != null) {
      localCursorAnchorInfoController.focusedNodeChanged(paramBoolean);
    }
    if ((this.mTextInputType != 0) && (this.mInputConnection != null) && (paramBoolean)) {
      this.mRestartInputOnNextStateUpdate = true;
    }
  }
  
  private boolean focusedNodeEditable()
  {
    return this.mTextInputType != 0;
  }
  
  public static ImeAdapterImpl fromWebContents(WebContents paramWebContents)
  {
    return (ImeAdapterImpl)WebContentsUserData.fromWebContents(paramWebContents, ImeAdapterImpl.class, null);
  }
  
  protected static int getModifiers(int paramInt)
  {
    if ((paramInt & 0x1) != 0) {
      j = 1;
    } else {
      j = 0;
    }
    int i = j;
    if ((paramInt & 0x2) != 0) {
      i = j | 0x4;
    }
    int j = i;
    if ((paramInt & 0x1000) != 0) {
      j = i | 0x2;
    }
    i = j;
    if ((0x100000 & paramInt) != 0) {
      i = j | 0x200;
    }
    j = i;
    if ((paramInt & 0x200000) != 0) {
      j = i | 0x400;
    }
    return j;
  }
  
  private int getUnderlineColorForSuggestionSpan(SuggestionSpan paramSuggestionSpan)
  {
    try
    {
      int i = ((Integer)SuggestionSpan.class.getMethod("getUnderlineColor", new Class[0]).invoke(paramSuggestionSpan, new Object[0])).intValue();
      return i;
    }
    catch (IllegalAccessException paramSuggestionSpan)
    {
      return -2000107320;
    }
    catch (InvocationTargetException paramSuggestionSpan)
    {
      return -2000107320;
    }
    catch (NoSuchMethodException paramSuggestionSpan) {}
    return -2000107320;
  }
  
  private void hideKeyboard()
  {
    if (!isValid()) {
      return;
    }
    Object localObject = this.mContainerView;
    if (this.mInputMethodManagerWrapper.isActive((View)localObject)) {
      this.mInputMethodManagerWrapper.hideSoftInputFromWindow(((View)localObject).getWindowToken(), 0, null);
    }
    if (!focusedNodeEditable())
    {
      localObject = this.mInputConnection;
      if (localObject != null)
      {
        restartInput();
        ((ChromiumBaseInputConnection)localObject).unblockOnUiThread();
      }
    }
    localObject = this.mListener;
    if (localObject != null) {
      ((ImeAdapterListener)localObject).hideKeyboard();
    }
  }
  
  private void init(View paramView, InputMethodManagerWrapper paramInputMethodManagerWrapper)
  {
    this.mContainerView = paramView;
    this.mInputMethodManagerWrapper = paramInputMethodManagerWrapper;
    this.mCurrentConfig = new Configuration(this.mContainerView.getResources().getConfiguration());
    if (Build.VERSION.SDK_INT >= 21) {
      this.mCursorAnchorInfoController = CursorAnchorInfoController.create(paramInputMethodManagerWrapper, new CursorAnchorInfoController.ComposingTextDelegate()
      {
        public int getComposingTextEnd()
        {
          return ImeAdapterImpl.this.mLastCompositionEnd;
        }
        
        public int getComposingTextStart()
        {
          return ImeAdapterImpl.this.mLastCompositionStart;
        }
        
        public int getSelectionEnd()
        {
          return ImeAdapterImpl.this.mLastSelectionEnd;
        }
        
        public int getSelectionStart()
        {
          return ImeAdapterImpl.this.mLastSelectionStart;
        }
        
        public CharSequence getText()
        {
          return ImeAdapterImpl.this.mLastText;
        }
      });
    } else {
      this.mCursorAnchorInfoController = null;
    }
    this.mNativeImeAdapterAndroid = nativeInit(this.mWebContents);
  }
  
  private boolean initialized()
  {
    return this.mNativeImeAdapterAndroid != 0L;
  }
  
  private boolean isHardwareKeyboardAttached()
  {
    return this.mCurrentConfig.keyboard != 1;
  }
  
  private static boolean isTextInputType(int paramInt)
  {
    return (paramInt != 0) && (!X5InputDialogContainer.isDialogInputType(paramInt));
  }
  
  private native void nativeAdvanceFocusInForm(long paramLong, int paramInt);
  
  private static native void nativeAppendBackgroundColorSpan(long paramLong, int paramInt1, int paramInt2, int paramInt3);
  
  private static native void nativeAppendSuggestionSpan(long paramLong, int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3, int paramInt4, String[] paramArrayOfString);
  
  private static native void nativeAppendUnderlineSpan(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativeDeleteSurroundingText(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativeDeleteSurroundingTextInCodePoints(long paramLong, int paramInt1, int paramInt2);
  
  private native void nativeFinishComposingText(long paramLong);
  
  private native long nativeInit(WebContents paramWebContents);
  
  private native void nativeRequestCursorUpdate(long paramLong, boolean paramBoolean1, boolean paramBoolean2);
  
  private native boolean nativeRequestTextInputStateUpdate(long paramLong);
  
  private native void nativeSetComposingRegion(long paramLong, int paramInt1, int paramInt2);
  
  @CalledByNative
  private void onConnectedToRenderProcess()
  {
    this.mIsConnected = true;
    createInputConnectionFactory();
    resetAndHideKeyboard();
  }
  
  @CalledByNative
  private void populateImeTextSpansFromJava(CharSequence paramCharSequence, long paramLong)
  {
    if (!(paramCharSequence instanceof SpannableString)) {
      return;
    }
    SpannableString localSpannableString = (SpannableString)paramCharSequence;
    paramCharSequence = (CharacterStyle[])localSpannableString.getSpans(0, paramCharSequence.length(), CharacterStyle.class);
    int j = paramCharSequence.length;
    int i = 0;
    while (i < j)
    {
      Object localObject = paramCharSequence[i];
      if ((localObject instanceof BackgroundColorSpan))
      {
        nativeAppendBackgroundColorSpan(paramLong, localSpannableString.getSpanStart(localObject), localSpannableString.getSpanEnd(localObject), ((BackgroundColorSpan)localObject).getBackgroundColor());
      }
      else if ((localObject instanceof UnderlineSpan))
      {
        nativeAppendUnderlineSpan(paramLong, localSpannableString.getSpanStart(localObject), localSpannableString.getSpanEnd(localObject));
      }
      else if ((localObject instanceof SuggestionSpan))
      {
        localObject = (SuggestionSpan)localObject;
        boolean bool;
        if ((((SuggestionSpan)localObject).getFlags() & 0x2) != 0) {
          bool = true;
        } else {
          bool = false;
        }
        if ((((SuggestionSpan)localObject).getFlags() == 1) || (bool))
        {
          int k = getUnderlineColorForSuggestionSpan((SuggestionSpan)localObject);
          int m = (int)(Color.alpha(k) * 0.4F);
          nativeAppendSuggestionSpan(paramLong, localSpannableString.getSpanStart(localObject), localSpannableString.getSpanEnd(localObject), bool, k, (0xFFFFFF & k) + (m << 24), ((SuggestionSpan)localObject).getSuggestions());
        }
      }
      i += 1;
    }
  }
  
  @CalledByNative
  private void setCharacterBounds(float[] paramArrayOfFloat)
  {
    CursorAnchorInfoController localCursorAnchorInfoController = this.mCursorAnchorInfoController;
    if (localCursorAnchorInfoController == null) {
      return;
    }
    localCursorAnchorInfoController.setCompositionCharacterBounds(paramArrayOfFloat, this.mContainerView);
  }
  
  private void setInputConnection(ChromiumBaseInputConnection paramChromiumBaseInputConnection)
  {
    ChromiumBaseInputConnection localChromiumBaseInputConnection = this.mInputConnection;
    if (localChromiumBaseInputConnection == paramChromiumBaseInputConnection) {
      return;
    }
    if (localChromiumBaseInputConnection != null) {
      localChromiumBaseInputConnection.unblockOnUiThread();
    }
    this.mInputConnection = paramChromiumBaseInputConnection;
  }
  
  @CalledByNative
  private void updateAfterViewSizeChanged()
  {
    if (!this.mFocusPreOSKViewportRect.isEmpty())
    {
      Rect localRect = new Rect();
      this.mContainerView.getWindowVisibleDisplayFrame(localRect);
      if (!localRect.equals(this.mFocusPreOSKViewportRect))
      {
        if (localRect.width() == this.mFocusPreOSKViewportRect.width()) {
          this.mWebContents.scrollFocusedEditableNodeIntoView();
        }
        this.mFocusPreOSKViewportRect.setEmpty();
      }
    }
  }
  
  @CalledByNative
  private void updateFrameInfo(float paramFloat1, float paramFloat2, boolean paramBoolean1, boolean paramBoolean2, float paramFloat3, float paramFloat4, float paramFloat5)
  {
    CursorAnchorInfoController localCursorAnchorInfoController = this.mCursorAnchorInfoController;
    if (localCursorAnchorInfoController == null) {
      return;
    }
    localCursorAnchorInfoController.onUpdateFrameInfo(paramFloat1, paramFloat2, paramBoolean1, paramBoolean2, paramFloat3, paramFloat4, paramFloat5, this.mContainerView);
  }
  
  public void addEventObserver(ImeEventObserver paramImeEventObserver)
  {
    this.mEventObservers.add(paramImeEventObserver);
  }
  
  public boolean deleteSurroundingText(int paramInt1, int paramInt2)
  {
    onImeEvent();
    if (!isValid()) {
      return false;
    }
    nativeSendKeyEvent(this.mNativeImeAdapterAndroid, null, 7, 0, SystemClock.uptimeMillis(), 229, 0, false, 0);
    nativeDeleteSurroundingText(this.mNativeImeAdapterAndroid, paramInt1, paramInt2);
    nativeSendKeyEvent(this.mNativeImeAdapterAndroid, null, 9, 0, SystemClock.uptimeMillis(), 229, 0, false, 0);
    return true;
  }
  
  boolean deleteSurroundingTextInCodePoints(int paramInt1, int paramInt2)
  {
    onImeEvent();
    if (!isValid()) {
      return false;
    }
    nativeSendKeyEvent(this.mNativeImeAdapterAndroid, null, 7, 0, SystemClock.uptimeMillis(), 229, 0, false, 0);
    nativeDeleteSurroundingTextInCodePoints(this.mNativeImeAdapterAndroid, paramInt1, paramInt2);
    nativeSendKeyEvent(this.mNativeImeAdapterAndroid, null, 9, 0, SystemClock.uptimeMillis(), 229, 0, false, 0);
    return true;
  }
  
  public boolean dispatchKeyEvent(KeyEvent paramKeyEvent)
  {
    ChromiumBaseInputConnection localChromiumBaseInputConnection = this.mInputConnection;
    if (localChromiumBaseInputConnection != null) {
      return localChromiumBaseInputConnection.sendKeyEventOnUiThread(paramKeyEvent);
    }
    return sendKeyEvent(paramKeyEvent);
  }
  
  @VisibleForTesting
  public boolean finishComposingText()
  {
    if (!isValid()) {
      return false;
    }
    nativeFinishComposingText(this.mNativeImeAdapterAndroid);
    return true;
  }
  
  public InputConnection getActiveInputConnection()
  {
    return this.mInputConnection;
  }
  
  public Rect getFocusPreOSKViewportRect()
  {
    return this.mFocusPreOSKViewportRect;
  }
  
  @VisibleForTesting
  ChromiumBaseInputConnection.Factory getInputConnectionFactoryForTest()
  {
    return this.mInputConnectionFactory;
  }
  
  @VisibleForTesting
  public InputConnection getInputConnectionForTest()
  {
    return this.mInputConnection;
  }
  
  public ImeAdapterListener getListener()
  {
    return this.mListener;
  }
  
  public ResultReceiver getNewShowKeyboardReceiver()
  {
    if (this.mShowKeyboardResultReceiver == null) {
      this.mShowKeyboardResultReceiver = new ShowKeyboardResultReceiver(this, new Handler());
    }
    return this.mShowKeyboardResultReceiver;
  }
  
  protected boolean isValid()
  {
    return (this.mNativeImeAdapterAndroid != 0L) && (this.mIsConnected);
  }
  
  protected native void nativeCommitText(long paramLong, CharSequence paramCharSequence, String paramString, int paramInt);
  
  protected native boolean nativeSendKeyEvent(long paramLong1, KeyEvent paramKeyEvent, int paramInt1, int paramInt2, long paramLong2, int paramInt3, int paramInt4, boolean paramBoolean, int paramInt5);
  
  protected native void nativeSetComposingText(long paramLong, CharSequence paramCharSequence, String paramString, int paramInt);
  
  protected native void nativeSetEditableSelectionOffsets(long paramLong, int paramInt1, int paramInt2);
  
  void notifyUserAction()
  {
    this.mInputMethodManagerWrapper.notifyUserAction();
  }
  
  public void onAttachedToWindow()
  {
    ChromiumBaseInputConnection.Factory localFactory = this.mInputConnectionFactory;
    if (localFactory != null) {
      localFactory.onViewAttachedToWindow();
    }
  }
  
  public boolean onCheckIsTextEditor()
  {
    return isTextInputType(this.mTextInputType);
  }
  
  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    WebContents localWebContents = this.mWebContents;
    boolean bool;
    if ((localWebContents != null) && (!localWebContents.isIncognito())) {
      bool = true;
    } else {
      bool = false;
    }
    return onCreateInputConnection(paramEditorInfo, bool);
  }
  
  public ChromiumBaseInputConnection onCreateInputConnection(EditorInfo paramEditorInfo, boolean paramBoolean)
  {
    paramEditorInfo.imeOptions = 301989888;
    if (!paramBoolean) {
      paramEditorInfo.imeOptions |= 0x1000000;
    }
    if (!focusedNodeEditable())
    {
      setInputConnection(null);
      return null;
    }
    ChromiumBaseInputConnection.Factory localFactory = this.mInputConnectionFactory;
    if (localFactory == null) {
      return null;
    }
    setInputConnection(localFactory.initializeAndGet(this.mContainerView, this, this.mTextInputType, this.mTextInputFlags, this.mTextInputMode, this.mLastSelectionStart, this.mLastSelectionEnd, paramEditorInfo));
    paramEditorInfo = this.mCursorAnchorInfoController;
    if (paramEditorInfo != null) {
      paramEditorInfo.onRequestCursorUpdates(false, false, this.mContainerView);
    }
    if (isValid()) {
      nativeRequestCursorUpdate(this.mNativeImeAdapterAndroid, false, false);
    }
    return this.mInputConnection;
  }
  
  public void onDetachedFromWindow()
  {
    resetAndHideKeyboard();
    ChromiumBaseInputConnection.Factory localFactory = this.mInputConnectionFactory;
    if (localFactory != null) {
      localFactory.onViewDetachedFromWindow();
    }
  }
  
  protected void onImeEvent()
  {
    Iterator localIterator = this.mEventObservers.iterator();
    while (localIterator.hasNext()) {
      ((ImeEventObserver)localIterator.next()).onImeEvent();
    }
    if (this.mNodeEditable) {
      this.mWebContents.dismissTextHandles();
    }
  }
  
  public void onKeyboardConfigurationChanged(Configuration paramConfiguration)
  {
    if (!isValid()) {
      return;
    }
    if ((this.mCurrentConfig.keyboard == paramConfiguration.keyboard) && (this.mCurrentConfig.keyboardHidden == paramConfiguration.keyboardHidden) && (this.mCurrentConfig.hardKeyboardHidden == paramConfiguration.hardKeyboardHidden)) {
      return;
    }
    this.mCurrentConfig = new Configuration(paramConfiguration);
    if (focusedNodeAllowsSoftKeyboard())
    {
      restartInput();
      showSoftKeyboard();
      return;
    }
    if (focusedNodeEditable())
    {
      restartInput();
      if (!isHardwareKeyboardAttached())
      {
        hideKeyboard();
        return;
      }
      showSoftKeyboard();
    }
  }
  
  public boolean onRequestCursorUpdates(int paramInt)
  {
    boolean bool2 = true;
    boolean bool1;
    if ((paramInt & 0x1) != 0) {
      bool1 = true;
    } else {
      bool1 = false;
    }
    if ((paramInt & 0x2) == 0) {
      bool2 = false;
    }
    if (isValid()) {
      nativeRequestCursorUpdate(this.mNativeImeAdapterAndroid, bool1, bool2);
    }
    CursorAnchorInfoController localCursorAnchorInfoController = this.mCursorAnchorInfoController;
    if (localCursorAnchorInfoController == null) {
      return false;
    }
    return localCursorAnchorInfoController.onRequestCursorUpdates(bool1, bool2, this.mContainerView);
  }
  
  public void onShowKeyboardReceiveResult(int paramInt)
  {
    ImeAdapterListener localImeAdapterListener;
    if (paramInt == 2)
    {
      this.mContainerView.getWindowVisibleDisplayFrame(this.mFocusPreOSKViewportRect);
      localImeAdapterListener = this.mListener;
      if (localImeAdapterListener != null) {
        localImeAdapterListener.showKeyboard();
      }
    }
    else
    {
      if ((ViewUtils.a(this.mContainerView)) && (paramInt == 0))
      {
        this.mWebContents.scrollFocusedEditableNodeIntoView();
        return;
      }
      if (paramInt == 1)
      {
        this.mContainerView.getWindowVisibleDisplayFrame(this.mFocusPreOSKViewportRect);
        localImeAdapterListener = this.mListener;
        if (localImeAdapterListener != null) {
          localImeAdapterListener.showKeyboard();
        }
      }
    }
  }
  
  public void onViewFocusChanged(boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!paramBoolean1) && (paramBoolean2)) {
      resetAndHideKeyboard();
    }
    ChromiumBaseInputConnection.Factory localFactory = this.mInputConnectionFactory;
    if (localFactory != null) {
      localFactory.onViewFocusChanged(paramBoolean1);
    }
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    ChromiumBaseInputConnection.Factory localFactory = this.mInputConnectionFactory;
    if (localFactory != null) {
      localFactory.onWindowFocusChanged(paramBoolean);
    }
  }
  
  public boolean performContextMenuAction(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return false;
    case 16908322: 
      this.mWebContents.paste();
      return true;
    case 16908321: 
      this.mWebContents.copy();
      return true;
    case 16908320: 
      this.mWebContents.cut();
      return true;
    }
    this.mWebContents.selectAll();
    return true;
  }
  
  public boolean performEditorAction(int paramInt)
  {
    if (!isValid()) {
      return false;
    }
    if (paramInt != 5)
    {
      if (paramInt != 7)
      {
        sendSyntheticKeyPress(66, 22);
        return true;
      }
      advanceFocusInForm(2);
      return true;
    }
    advanceFocusInForm(1);
    return true;
  }
  
  public void removeEventObserver(ImeEventObserver paramImeEventObserver)
  {
    this.mEventObservers.remove(paramImeEventObserver);
  }
  
  boolean requestTextInputStateUpdate()
  {
    if (!isValid()) {
      return false;
    }
    if (this.mInputConnection == null) {
      return false;
    }
    return nativeRequestTextInputStateUpdate(this.mNativeImeAdapterAndroid);
  }
  
  public void resetAndHideKeyboard()
  {
    this.mTextInputType = 0;
    this.mTextInputFlags = 0;
    this.mTextInputMode = 0;
    this.mRestartInputOnNextStateUpdate = false;
    hideKeyboard();
  }
  
  protected void restartInput()
  {
    if (!isValid()) {
      return;
    }
    this.mInputMethodManagerWrapper.restartInput(this.mContainerView);
    ChromiumBaseInputConnection localChromiumBaseInputConnection = this.mInputConnection;
    if (localChromiumBaseInputConnection != null) {
      localChromiumBaseInputConnection.onRestartInputOnUiThread();
    }
  }
  
  public boolean sendCompositionToNative(CharSequence paramCharSequence, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    if (!isValid()) {
      return false;
    }
    onImeEvent();
    long l = SystemClock.uptimeMillis();
    nativeSendKeyEvent(this.mNativeImeAdapterAndroid, null, 7, 0, l, 229, 0, false, paramInt2);
    if (paramBoolean) {
      nativeCommitText(this.mNativeImeAdapterAndroid, paramCharSequence, paramCharSequence.toString(), paramInt1);
    } else {
      nativeSetComposingText(this.mNativeImeAdapterAndroid, paramCharSequence, paramCharSequence.toString(), paramInt1);
    }
    nativeSendKeyEvent(this.mNativeImeAdapterAndroid, null, 9, 0, l, 229, 0, false, paramInt2);
    return true;
  }
  
  public boolean sendKeyEvent(KeyEvent paramKeyEvent)
  {
    if (!isValid()) {
      return false;
    }
    int i = paramKeyEvent.getAction();
    if (i == 0)
    {
      i = 8;
    }
    else
    {
      if (i != 1) {
        break label108;
      }
      i = 9;
    }
    Iterator localIterator = this.mEventObservers.iterator();
    while (localIterator.hasNext()) {
      ((ImeEventObserver)localIterator.next()).onBeforeSendKeyEvent(paramKeyEvent);
    }
    onImeEvent();
    return nativeSendKeyEvent(this.mNativeImeAdapterAndroid, paramKeyEvent, i, getModifiers(paramKeyEvent.getMetaState()), paramKeyEvent.getEventTime(), paramKeyEvent.getKeyCode(), paramKeyEvent.getScanCode(), false, paramKeyEvent.getUnicodeChar());
    label108:
    return false;
  }
  
  public void sendSyntheticKeyPress(int paramInt1, int paramInt2)
  {
    long l = SystemClock.uptimeMillis();
    sendKeyEvent(new KeyEvent(l, l, 0, paramInt1, 0, 0, -1, 0, paramInt2));
    sendKeyEvent(new KeyEvent(l, l, 1, paramInt1, 0, 0, -1, 0, paramInt2));
  }
  
  public boolean setComposingRegion(int paramInt1, int paramInt2)
  {
    if (!isValid()) {
      return false;
    }
    if (paramInt1 <= paramInt2) {
      nativeSetComposingRegion(this.mNativeImeAdapterAndroid, paramInt1, paramInt2);
    } else {
      nativeSetComposingRegion(this.mNativeImeAdapterAndroid, paramInt2, paramInt1);
    }
    return true;
  }
  
  public void setContainerView(View paramView)
  {
    this.mContainerView = paramView;
  }
  
  public boolean setEditableSelectionOffsets(int paramInt1, int paramInt2)
  {
    if (!isValid()) {
      return false;
    }
    nativeSetEditableSelectionOffsets(this.mNativeImeAdapterAndroid, paramInt1, paramInt2);
    return true;
  }
  
  @VisibleForTesting
  void setInputConnectionFactory(ChromiumBaseInputConnection.Factory paramFactory)
  {
    this.mInputConnectionFactory = paramFactory;
  }
  
  public void setInputMethodManagerWrapper(InputMethodManagerWrapper paramInputMethodManagerWrapper)
  {
    this.mInputMethodManagerWrapper = paramInputMethodManagerWrapper;
    CursorAnchorInfoController localCursorAnchorInfoController = this.mCursorAnchorInfoController;
    if (localCursorAnchorInfoController != null) {
      localCursorAnchorInfoController.setInputMethodManagerWrapper(paramInputMethodManagerWrapper);
    }
  }
  
  public void setListener(ImeAdapterListener paramImeAdapterListener)
  {
    this.mListener = paramImeAdapterListener;
  }
  
  protected void showSoftKeyboard()
  {
    if (!isValid()) {
      return;
    }
    this.mInputMethodManagerWrapper.showSoftInput(this.mContainerView, 0, getNewShowKeyboardReceiver());
    if (this.mContainerView.getResources().getConfiguration().keyboard != 1) {
      this.mWebContents.scrollFocusedEditableNodeIntoView();
    }
  }
  
  void updateExtractedText(int paramInt, ExtractedText paramExtractedText)
  {
    this.mInputMethodManagerWrapper.updateExtractedText(this.mContainerView, paramInt, paramExtractedText);
  }
  
  void updateSelection(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mInputMethodManagerWrapper.updateSelection(this.mContainerView, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  @CalledByNative
  protected void updateState(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, String paramString, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean2)
  {
    TraceEvent.begin("ImeAdapter.updateState");
    int i;
    try
    {
      if (!this.mRestartInputOnNextStateUpdate) {
        break label381;
      }
      this.mRestartInputOnNextStateUpdate = false;
      i = 1;
      this.mTextInputFlags = paramInt2;
      if (this.mTextInputMode == paramInt3) {
        break label395;
      }
      this.mTextInputMode = paramInt3;
      if ((paramInt3 != 1) || (isHardwareKeyboardAttached())) {
        break label387;
      }
      paramInt2 = 1;
    }
    finally
    {
      label59:
      boolean bool2;
      Iterator localIterator;
      TraceEvent.end("ImeAdapter.updateState");
    }
    paramInt3 = paramInt2;
    int j = i;
    if (this.mTextInputType != paramInt1)
    {
      this.mTextInputType = paramInt1;
      if (paramInt1 != 0) {
        break label400;
      }
      paramInt3 = 1;
      j = i;
    }
    label91:
    bool2 = focusedNodeEditable();
    boolean bool1;
    if (paramInt1 == 2)
    {
      bool1 = true;
      label108:
      if ((this.mNodeEditable != bool2) || (this.mNodePassword != bool1))
      {
        localIterator = this.mEventObservers.iterator();
        while (localIterator.hasNext()) {
          ((ImeEventObserver)localIterator.next()).onNodeAttributeUpdated(bool2, bool1);
        }
        this.mNodeEditable = bool2;
        this.mNodePassword = bool1;
      }
      if ((this.mCursorAnchorInfoController != null) && ((!TextUtils.equals(this.mLastText, paramString)) || (this.mLastSelectionStart != paramInt4) || (this.mLastSelectionEnd != paramInt5) || (this.mLastCompositionStart != paramInt6) || (this.mLastCompositionEnd != paramInt7))) {
        this.mCursorAnchorInfoController.invalidateLastCursorAnchorInfo();
      }
      this.mLastText = paramString;
      this.mLastSelectionStart = paramInt4;
      this.mLastSelectionEnd = paramInt5;
      this.mLastCompositionStart = paramInt6;
      this.mLastCompositionEnd = paramInt7;
      if (paramInt3 != 0)
      {
        hideKeyboard();
      }
      else
      {
        if (j != 0) {
          restartInput();
        }
        if ((paramBoolean1) && (focusedNodeAllowsSoftKeyboard())) {
          showSoftKeyboard();
        }
      }
      if (this.mInputConnection != null)
      {
        if ((this.mTextInputType == 14) || (this.mTextInputType == 15)) {
          break label414;
        }
        paramBoolean1 = true;
      }
    }
    for (;;)
    {
      this.mInputConnection.updateStateOnUiThread(paramString, paramInt4, paramInt5, paramInt6, paramInt7, paramBoolean1, paramBoolean2);
      TraceEvent.end("ImeAdapter.updateState");
      return;
      label381:
      i = 0;
      break;
      label387:
      paramInt2 = 0;
      i = 1;
      break label59;
      label395:
      paramInt2 = 0;
      break label59;
      label400:
      j = 1;
      paramInt3 = paramInt2;
      break label91;
      bool1 = false;
      break label108;
      label414:
      paramBoolean1 = false;
    }
  }
  
  @SuppressLint({"ParcelCreator"})
  private static class ShowKeyboardResultReceiver
    extends ResultReceiver
  {
    private final WeakReference<ImeAdapterImpl> a;
    
    public ShowKeyboardResultReceiver(ImeAdapterImpl paramImeAdapterImpl, Handler paramHandler)
    {
      super();
      this.a = new WeakReference(paramImeAdapterImpl);
    }
    
    public void onReceiveResult(int paramInt, Bundle paramBundle)
    {
      paramBundle = (ImeAdapterImpl)this.a.get();
      if (paramBundle == null) {
        return;
      }
      paramBundle.onShowKeyboardReceiveResult(paramInt);
    }
  }
  
  private static final class UserDataFactoryLazyHolder
  {
    private static final WebContentsUserData.UserDataFactory<ImeAdapterImpl> a = new WebContentsUserData.UserDataFactory()
    {
      public ImeAdapterImpl create(WebContents paramAnonymousWebContents)
      {
        return new TencentImeAdapter(paramAnonymousWebContents);
      }
    };
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\ImeAdapterImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */