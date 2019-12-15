package org.chromium.tencent.content.browser.input;

import android.os.SystemClock;
import android.view.KeyEvent;
import java.util.Iterator;
import java.util.List;
import org.chromium.base.VisibleForTesting;
import org.chromium.base.annotations.JNINamespace;
import org.chromium.content.browser.ContentViewCoreImpl;
import org.chromium.content.browser.input.ChromiumBaseInputConnection;
import org.chromium.content.browser.input.ImeAdapterImpl;
import org.chromium.content_public.browser.ImeEventObserver;
import org.chromium.content_public.browser.InputMethodManagerWrapper;
import org.chromium.content_public.browser.WebContents;
import org.chromium.tencent.TencentContentViewCore;

@JNINamespace("content")
public class TencentImeAdapter
  extends ImeAdapterImpl
{
  private static final String TAG = "cr_Ime_tencent";
  private boolean mIsSelectingTextByShift = false;
  
  public TencentImeAdapter(WebContents paramWebContents)
  {
    super(paramWebContents);
  }
  
  private boolean isSelectingTextByShift(int paramInt)
  {
    if ((paramInt != 19) && (paramInt != 20) && (paramInt != 21) && (paramInt != 22)) {
      return false;
    }
    return this.mIsSelectingTextByShift;
  }
  
  public boolean deleteSurroundingText(int paramInt1, int paramInt2)
  {
    if ((paramInt1 == 1) && (paramInt2 == 0))
    {
      onImeEvent();
      if (this.mNativeImeAdapterAndroid == 0L) {
        return false;
      }
      sendSyntheticKeyPress(67, 6);
      return true;
    }
    setSelectingTextByShift(false);
    return super.deleteSurroundingText(paramInt1, paramInt2);
  }
  
  @VisibleForTesting
  public boolean finishComposingText()
  {
    if (this.mNativeImeAdapterAndroid == 0L) {
      return false;
    }
    setSelectingTextByShift(false);
    return super.finishComposingText();
  }
  
  public String getLastText()
  {
    return this.mLastText;
  }
  
  public int getLastTextLength()
  {
    return this.mLastText.length();
  }
  
  public int getSelectionEnd()
  {
    return this.mLastSelectionEnd;
  }
  
  public int getSelectionStart()
  {
    return this.mLastSelectionStart;
  }
  
  public int getTextInputType()
  {
    return this.mTextInputType;
  }
  
  public boolean performContextMenuAction(int paramInt)
  {
    setSelectingTextByShift(false);
    if (paramInt != 16908322)
    {
      switch (paramInt)
      {
      default: 
        break;
      case 16908329: 
        setSelectingTextByShift(false);
        return true;
      case 16908328: 
        setSelectingTextByShift(true);
        return true;
      }
    }
    else
    {
      ContentViewCoreImpl localContentViewCoreImpl = ContentViewCoreImpl.fromWebContents(this.mWebContents);
      if ((localContentViewCoreImpl != null) && ((localContentViewCoreImpl instanceof TencentContentViewCore)) && (((TencentContentViewCore)localContentViewCoreImpl).performContextMenuActionPaste())) {
        return true;
      }
    }
    return super.performContextMenuAction(paramInt);
  }
  
  protected void restartInput()
  {
    if (!isValid()) {
      return;
    }
    this.mInputMethodManagerWrapper.restartInput(this.mContainerView);
    setSelectingTextByShift(false);
    if (this.mInputConnection != null) {
      this.mInputConnection.onRestartInputOnUiThread();
    }
  }
  
  public boolean sendCompositionToNative(CharSequence paramCharSequence, int paramInt1, boolean paramBoolean, int paramInt2)
  {
    if (!isValid()) {
      return false;
    }
    onImeEvent();
    long l = SystemClock.uptimeMillis();
    setSelectingTextByShift(false);
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
    boolean bool = isValid();
    int j = 0;
    if (!bool) {
      return false;
    }
    int i = paramKeyEvent.getAction();
    if (i == 0)
    {
      if ((paramKeyEvent.getKeyCode() == 59) || (paramKeyEvent.getKeyCode() == 60)) {
        setSelectingTextByShift(true);
      }
      i = 8;
    }
    else
    {
      if (i != 1) {
        break label187;
      }
      if ((paramKeyEvent.getKeyCode() == 59) || (paramKeyEvent.getKeyCode() == 60)) {
        setSelectingTextByShift(false);
      }
      i = 9;
    }
    Iterator localIterator = this.mEventObservers.iterator();
    while (localIterator.hasNext()) {
      ((ImeEventObserver)localIterator.next()).onBeforeSendKeyEvent(paramKeyEvent);
    }
    onImeEvent();
    long l = this.mNativeImeAdapterAndroid;
    int k = paramKeyEvent.getMetaState();
    if (isSelectingTextByShift(paramKeyEvent.getKeyCode()) == true) {
      j = 1;
    }
    return nativeSendKeyEvent(l, paramKeyEvent, i, getModifiers(k | j), paramKeyEvent.getEventTime(), paramKeyEvent.getKeyCode(), paramKeyEvent.getScanCode(), false, paramKeyEvent.getUnicodeChar());
    label187:
    return false;
  }
  
  public boolean setEditableSelectionOffsets(int paramInt1, int paramInt2)
  {
    if (!isValid()) {
      return false;
    }
    if (paramInt1 == paramInt2) {
      setSelectingTextByShift(false);
    }
    nativeSetEditableSelectionOffsets(this.mNativeImeAdapterAndroid, paramInt1, paramInt2);
    return true;
  }
  
  public void setSelectingTextByShift(boolean paramBoolean)
  {
    this.mIsSelectingTextByShift = paramBoolean;
  }
  
  protected void showSoftKeyboard()
  {
    super.showSoftKeyboard();
    ContentViewCoreImpl localContentViewCoreImpl = ContentViewCoreImpl.fromWebContents(this.mWebContents);
    if ((localContentViewCoreImpl != null) && ((localContentViewCoreImpl instanceof TencentContentViewCore))) {
      ((TencentContentViewCore)localContentViewCoreImpl).onSoftKeyBoardShow();
    }
  }
  
  protected void updateState(int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean1, String paramString, int paramInt4, int paramInt5, int paramInt6, int paramInt7, boolean paramBoolean2)
  {
    super.updateState(paramInt1, paramInt2, paramInt3, paramBoolean1, paramString, paramInt4, paramInt5, paramInt6, paramInt7, paramBoolean2);
    paramString = ContentViewCoreImpl.fromWebContents(this.mWebContents);
    if ((paramString != null) && ((paramString instanceof TencentContentViewCore))) {
      ((TencentContentViewCore)paramString).updateHitTestEditable(this.mLastText);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\tencent\content\browser\input\TencentImeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */