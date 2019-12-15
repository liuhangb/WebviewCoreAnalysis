package org.chromium.content_public.browser;

import android.content.Context;
import android.os.ResultReceiver;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import org.chromium.base.VisibleForTesting;
import org.chromium.content.browser.input.ImeAdapterImpl;

public abstract class ImeAdapter
{
  @VisibleForTesting
  public static final int COMPOSITION_KEY_CODE = 229;
  
  public static InputMethodManagerWrapper createDefaultInputMethodManagerWrapper(Context paramContext)
  {
    return ImeAdapterImpl.createDefaultInputMethodManagerWrapper(paramContext);
  }
  
  public static ImeAdapter fromWebContents(WebContents paramWebContents)
  {
    return ImeAdapterImpl.fromWebContents(paramWebContents);
  }
  
  public abstract void addEventObserver(ImeEventObserver paramImeEventObserver);
  
  public abstract InputConnection getActiveInputConnection();
  
  @VisibleForTesting
  public abstract InputConnection getInputConnectionForTest();
  
  @VisibleForTesting
  public abstract ResultReceiver getNewShowKeyboardReceiver();
  
  public abstract boolean onCheckIsTextEditor();
  
  public abstract InputConnection onCreateInputConnection(EditorInfo paramEditorInfo);
  
  public abstract void setInputMethodManagerWrapper(InputMethodManagerWrapper paramInputMethodManagerWrapper);
}


/* Location:              C:\Users\Administrator\Desktop\学习资料\dex2jar\dex2jar-2.0\classes-dex2jar.jar!\org\chromium\content_public\browser\ImeAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */