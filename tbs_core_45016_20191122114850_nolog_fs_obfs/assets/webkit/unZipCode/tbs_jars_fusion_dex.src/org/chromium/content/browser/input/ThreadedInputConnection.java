package org.chromium.content.browser.input;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.BaseInputConnection;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.chromium.base.Log;
import org.chromium.base.ThreadUtils;
import org.chromium.base.VisibleForTesting;

class ThreadedInputConnection
  extends BaseInputConnection
  implements ChromiumBaseInputConnection
{
  private static final TextInputState jdField_a_of_type_OrgChromiumContentBrowserInputTextInputState = new TextInputState("", new Range(0, 0), new Range(-1, -1), false, false)
  {
    public boolean shouldUnblock()
    {
      return true;
    }
  };
  private int jdField_a_of_type_Int;
  private final Handler jdField_a_of_type_AndroidOsHandler;
  private final Runnable jdField_a_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      ThreadedInputConnection.a(ThreadedInputConnection.this);
    }
  };
  private final BlockingQueue<TextInputState> jdField_a_of_type_JavaUtilConcurrentBlockingQueue = new LinkedBlockingQueue();
  private final ImeAdapterImpl jdField_a_of_type_OrgChromiumContentBrowserInputImeAdapterImpl;
  private boolean jdField_a_of_type_Boolean;
  private int jdField_b_of_type_Int;
  private final Runnable jdField_b_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      if (!ThreadedInputConnection.a(ThreadedInputConnection.this).requestTextInputStateUpdate()) {
        ThreadedInputConnection.this.unblockOnUiThread();
      }
    }
  };
  private TextInputState jdField_b_of_type_OrgChromiumContentBrowserInputTextInputState;
  private int jdField_c_of_type_Int;
  private final Runnable jdField_c_of_type_JavaLangRunnable = new Runnable()
  {
    public void run()
    {
      ThreadedInputConnection.a(ThreadedInputConnection.this).notifyUserAction();
    }
  };
  private final Runnable d = new Runnable()
  {
    public void run()
    {
      ThreadedInputConnection.b(ThreadedInputConnection.this);
    }
  };
  
  ThreadedInputConnection(View paramView, ImeAdapterImpl paramImeAdapterImpl, Handler paramHandler)
  {
    super(paramView, true);
    ImeUtils.a();
    this.jdField_a_of_type_OrgChromiumContentBrowserInputImeAdapterImpl = paramImeAdapterImpl;
    this.jdField_a_of_type_AndroidOsHandler = paramHandler;
  }
  
  private ExtractedText a(TextInputState paramTextInputState)
  {
    throw new Runtime("d2j fail translate: java.lang.RuntimeException: can not merge I and Z\r\n\tat com.googlecode.dex2jar.ir.TypeClass.merge(TypeClass.java:100)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeRef.updateTypeClass(TypeTransformer.java:174)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.copyTypes(TypeTransformer.java:311)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.fixTypes(TypeTransformer.java:226)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer$TypeAnalyze.analyze(TypeTransformer.java:207)\r\n\tat com.googlecode.dex2jar.ir.ts.TypeTransformer.transform(TypeTransformer.java:44)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:162)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:414)\r\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:42)\r\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:128)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:509)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:406)\r\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:422)\r\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:172)\r\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:272)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:108)\r\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:288)\r\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:32)\r\n");
  }
  
  private TextInputState a()
  {
    if (a()) {
      return this.jdField_b_of_type_OrgChromiumContentBrowserInputTextInputState;
    }
    c();
    ThreadUtils.postOnUiThread(this.jdField_b_of_type_JavaLangRunnable);
    return b();
  }
  
  private void a(int paramInt1, int paramInt2)
  {
    Object localObject = new StringBuilder();
    ((StringBuilder)localObject).appendCodePoint(paramInt1);
    localObject = ((StringBuilder)localObject).toString();
    this.jdField_a_of_type_OrgChromiumContentBrowserInputImeAdapterImpl.sendCompositionToNative((CharSequence)localObject, 1, true, 0);
    setCombiningAccentOnUiThread(paramInt2);
  }
  
  private void a(CharSequence paramCharSequence, int paramInt)
  {
    e();
    this.jdField_a_of_type_OrgChromiumContentBrowserInputImeAdapterImpl.sendCompositionToNative(paramCharSequence, paramInt, true, 0);
  }
  
  private void a(CharSequence paramCharSequence, int paramInt, boolean paramBoolean)
  {
    int i;
    if (paramBoolean) {
      i = this.jdField_b_of_type_Int | 0x80000000;
    } else {
      i = 0;
    }
    e();
    this.jdField_a_of_type_OrgChromiumContentBrowserInputImeAdapterImpl.sendCompositionToNative(paramCharSequence, paramInt, false, i);
  }
  
  private void a(TextInputState paramTextInputState)
  {
    if (paramTextInputState == null) {
      return;
    }
    c();
    if (this.jdField_a_of_type_Int != 0) {
      return;
    }
    Range localRange1 = paramTextInputState.selection();
    Range localRange2 = paramTextInputState.composition();
    if (this.jdField_a_of_type_Boolean)
    {
      paramTextInputState = a(paramTextInputState);
      this.jdField_a_of_type_OrgChromiumContentBrowserInputImeAdapterImpl.updateExtractedText(this.jdField_c_of_type_Int, paramTextInputState);
    }
    this.jdField_a_of_type_OrgChromiumContentBrowserInputImeAdapterImpl.updateSelection(localRange1.start(), localRange1.end(), localRange2.start(), localRange2.end());
  }
  
  private boolean a(KeyEvent paramKeyEvent)
  {
    int j = paramKeyEvent.getAction();
    int i = paramKeyEvent.getUnicodeChar();
    if (j != 0) {
      return false;
    }
    if (paramKeyEvent.getKeyCode() == 67)
    {
      setCombiningAccentOnUiThread(0);
      return false;
    }
    if ((0x80000000 & i) != 0)
    {
      i = 0x7FFFFFFF & i;
      j = this.jdField_b_of_type_Int;
      if (j != 0)
      {
        if (i == j)
        {
          a(j, 0);
          return true;
        }
        a(j, i);
        return true;
      }
      setCombiningAccentOnUiThread(i);
      return true;
    }
    j = this.jdField_b_of_type_Int;
    if ((j != 0) && (i != 0))
    {
      i = KeyEvent.getDeadChar(j, i);
      if (i != 0)
      {
        a(i, 0);
        return true;
      }
      a(this.jdField_b_of_type_Int, 0);
      f();
    }
    return false;
  }
  
  private TextInputState b()
  {
    c();
    int i = 0;
    try
    {
      for (;;)
      {
        TextInputState localTextInputState = (TextInputState)this.jdField_a_of_type_JavaUtilConcurrentBlockingQueue.take();
        if (localTextInputState.shouldUnblock()) {
          return null;
        }
        if (localTextInputState.replyToRequest())
        {
          if (i != 0) {
            a(localTextInputState);
          }
          return localTextInputState;
        }
        i = 1;
      }
      return null;
    }
    catch (InterruptedException localInterruptedException)
    {
      localInterruptedException.printStackTrace();
      ImeUtils.a(false);
    }
  }
  
  private void b()
  {
    c();
    for (;;)
    {
      TextInputState localTextInputState = (TextInputState)this.jdField_a_of_type_JavaUtilConcurrentBlockingQueue.poll();
      if (localTextInputState == null) {
        return;
      }
      if (!localTextInputState.shouldUnblock()) {
        a(localTextInputState);
      }
    }
  }
  
  private void b(TextInputState paramTextInputState)
  {
    
    try
    {
      this.jdField_a_of_type_JavaUtilConcurrentBlockingQueue.put(paramTextInputState);
      return;
    }
    catch (InterruptedException paramTextInputState)
    {
      Log.e("cr_Ime", "addToQueueOnUiThread interrupted", new Object[] { paramTextInputState });
    }
  }
  
  private void c()
  {
    boolean bool;
    if (this.jdField_a_of_type_AndroidOsHandler.getLooper() == Looper.myLooper()) {
      bool = true;
    } else {
      bool = false;
    }
    ImeUtils.a(bool);
  }
  
  private void d()
  {
    ThreadUtils.postOnUiThread(this.jdField_c_of_type_JavaLangRunnable);
  }
  
  private void e()
  {
    this.jdField_b_of_type_Int = 0;
  }
  
  private void f()
  {
    this.jdField_a_of_type_OrgChromiumContentBrowserInputImeAdapterImpl.finishComposingText();
  }
  
  void a()
  {
    ImeUtils.a();
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnection.a(ThreadedInputConnection.this, 0);
        ThreadedInputConnection.b(ThreadedInputConnection.this, 0);
        ThreadedInputConnection.c(ThreadedInputConnection.this, 0);
        ThreadedInputConnection.a(ThreadedInputConnection.this, false);
      }
    });
  }
  
  @VisibleForTesting
  protected boolean a()
  {
    return ThreadUtils.runningOnUiThread();
  }
  
  public boolean beginBatchEdit()
  {
    c();
    c();
    this.jdField_a_of_type_Int += 1;
    return true;
  }
  
  public boolean clearMetaKeyStates(int paramInt)
  {
    return false;
  }
  
  @SuppressLint({"MissingSuperCall"})
  public void closeConnection() {}
  
  public boolean commitCompletion(CompletionInfo paramCompletionInfo)
  {
    return false;
  }
  
  public boolean commitCorrection(CorrectionInfo paramCorrectionInfo)
  {
    return false;
  }
  
  public boolean commitText(final CharSequence paramCharSequence, final int paramInt)
  {
    if (paramCharSequence == null) {
      return false;
    }
    if (TextUtils.equals(paramCharSequence, "\n"))
    {
      beginBatchEdit();
      commitText("", 1);
      ThreadUtils.postOnUiThread(new Runnable()
      {
        public void run()
        {
          ThreadedInputConnection.a(ThreadedInputConnection.this).sendSyntheticKeyPress(66, 6);
        }
      });
      endBatchEdit();
      return true;
    }
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnection.a(ThreadedInputConnection.this, paramCharSequence, paramInt);
      }
    });
    d();
    return true;
  }
  
  public boolean deleteSurroundingText(final int paramInt1, final int paramInt2)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        if (ThreadedInputConnection.a(ThreadedInputConnection.this) != 0) {
          ThreadedInputConnection.b(ThreadedInputConnection.this);
        }
        ThreadedInputConnection.a(ThreadedInputConnection.this).deleteSurroundingText(paramInt1, paramInt2);
      }
    });
    return true;
  }
  
  public boolean deleteSurroundingTextInCodePoints(final int paramInt1, final int paramInt2)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        if (ThreadedInputConnection.a(ThreadedInputConnection.this) != 0) {
          ThreadedInputConnection.b(ThreadedInputConnection.this);
        }
        ThreadedInputConnection.a(ThreadedInputConnection.this).deleteSurroundingTextInCodePoints(paramInt1, paramInt2);
      }
    });
    return true;
  }
  
  public boolean endBatchEdit()
  {
    c();
    int i = this.jdField_a_of_type_Int;
    boolean bool = false;
    if (i == 0) {
      return false;
    }
    this.jdField_a_of_type_Int = (i - 1);
    if (this.jdField_a_of_type_Int == 0) {
      a(a());
    }
    if (this.jdField_a_of_type_Int != 0) {
      bool = true;
    }
    return bool;
  }
  
  public boolean finishComposingText()
  {
    ThreadUtils.postOnUiThread(this.d);
    return true;
  }
  
  public int getCursorCapsMode(int paramInt)
  {
    TextInputState localTextInputState = a();
    if (localTextInputState != null) {
      return TextUtils.getCapsMode(localTextInputState.text(), localTextInputState.selection().start(), paramInt);
    }
    return 0;
  }
  
  public ExtractedText getExtractedText(ExtractedTextRequest paramExtractedTextRequest, int paramInt)
  {
    c();
    boolean bool = true;
    int i = 0;
    if ((paramInt & 0x1) <= 0) {
      bool = false;
    }
    this.jdField_a_of_type_Boolean = bool;
    if (this.jdField_a_of_type_Boolean)
    {
      paramInt = i;
      if (paramExtractedTextRequest != null) {
        paramInt = paramExtractedTextRequest.token;
      }
      this.jdField_c_of_type_Int = paramInt;
    }
    return a(a());
  }
  
  public Handler getHandler()
  {
    return this.jdField_a_of_type_AndroidOsHandler;
  }
  
  public CharSequence getSelectedText(int paramInt)
  {
    TextInputState localTextInputState = a();
    if (localTextInputState == null) {
      return null;
    }
    return localTextInputState.getSelectedText();
  }
  
  public CharSequence getTextAfterCursor(int paramInt1, int paramInt2)
  {
    try
    {
      Object localObject = a();
      if (localObject == null) {
        return null;
      }
      localObject = ((TextInputState)localObject).getTextAfterSelection(paramInt1);
      return (CharSequence)localObject;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
      Log.e("cr_Ime", "getTextAfterCursor", new Object[] { localOutOfMemoryError });
      return null;
    }
    catch (Exception localException)
    {
      Log.e("cr_Ime", "getTextAfterCursor", new Object[] { localException });
    }
    return null;
  }
  
  public CharSequence getTextBeforeCursor(int paramInt1, int paramInt2)
  {
    TextInputState localTextInputState = a();
    if (localTextInputState == null) {
      return null;
    }
    return localTextInputState.getTextBeforeSelection(paramInt1);
  }
  
  public void onRestartInputOnUiThread() {}
  
  public boolean performContextMenuAction(final int paramInt)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnection.a(ThreadedInputConnection.this).performContextMenuAction(paramInt);
      }
    });
    return true;
  }
  
  public boolean performEditorAction(final int paramInt)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnection.a(ThreadedInputConnection.this).performEditorAction(paramInt);
      }
    });
    return true;
  }
  
  public boolean performPrivateCommand(String paramString, Bundle paramBundle)
  {
    return false;
  }
  
  public boolean reportFullscreenMode(boolean paramBoolean)
  {
    return false;
  }
  
  public boolean requestCursorUpdates(final int paramInt)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnection.a(ThreadedInputConnection.this).onRequestCursorUpdates(paramInt);
      }
    });
    return true;
  }
  
  public boolean sendKeyEvent(final KeyEvent paramKeyEvent)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        if (ThreadedInputConnection.a(ThreadedInputConnection.this, paramKeyEvent)) {
          return;
        }
        ThreadedInputConnection.a(ThreadedInputConnection.this).sendKeyEvent(paramKeyEvent);
      }
    });
    d();
    return true;
  }
  
  public boolean sendKeyEventOnUiThread(final KeyEvent paramKeyEvent)
  {
    ImeUtils.a();
    this.jdField_a_of_type_AndroidOsHandler.post(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnection.this.sendKeyEvent(paramKeyEvent);
      }
    });
    return true;
  }
  
  @VisibleForTesting
  public void setCombiningAccentOnUiThread(int paramInt)
  {
    this.jdField_b_of_type_Int = paramInt;
  }
  
  public boolean setComposingRegion(final int paramInt1, final int paramInt2)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnection.a(ThreadedInputConnection.this).setComposingRegion(paramInt1, paramInt2);
      }
    });
    return true;
  }
  
  public boolean setComposingText(CharSequence paramCharSequence, int paramInt)
  {
    if (paramCharSequence == null) {
      return false;
    }
    return updateComposingText(paramCharSequence, paramInt, false);
  }
  
  public boolean setSelection(final int paramInt1, final int paramInt2)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnection.a(ThreadedInputConnection.this).setEditableSelectionOffsets(paramInt1, paramInt2);
      }
    });
    return true;
  }
  
  @VisibleForTesting
  public void unblockOnUiThread()
  {
    ImeUtils.a();
    b(jdField_a_of_type_OrgChromiumContentBrowserInputTextInputState);
    this.jdField_a_of_type_AndroidOsHandler.post(this.jdField_a_of_type_JavaLangRunnable);
  }
  
  @VisibleForTesting
  public boolean updateComposingText(final CharSequence paramCharSequence, final int paramInt, final boolean paramBoolean)
  {
    ThreadUtils.postOnUiThread(new Runnable()
    {
      public void run()
      {
        ThreadedInputConnection.a(ThreadedInputConnection.this, paramCharSequence, paramInt, paramBoolean);
      }
    });
    d();
    return true;
  }
  
  public void updateStateOnUiThread(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4, boolean paramBoolean1, boolean paramBoolean2)
  {
    ImeUtils.a();
    this.jdField_b_of_type_OrgChromiumContentBrowserInputTextInputState = new TextInputState(paramString, new Range(paramInt1, paramInt2), new Range(paramInt3, paramInt4), paramBoolean1, paramBoolean2);
    b(this.jdField_b_of_type_OrgChromiumContentBrowserInputTextInputState);
    if (!paramBoolean2) {
      this.jdField_a_of_type_AndroidOsHandler.post(this.jdField_a_of_type_JavaLangRunnable);
    }
  }
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content\browser\input\ThreadedInputConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */